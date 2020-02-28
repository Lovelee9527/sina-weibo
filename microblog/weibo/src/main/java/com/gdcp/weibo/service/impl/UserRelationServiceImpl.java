package com.gdcp.weibo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcp.weibo.dao.UserDao;
import com.gdcp.weibo.dao.UserRelationDao;
import com.gdcp.weibo.dto.UserRelationExecution;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.UserRelation;
import com.gdcp.weibo.enums.UserRelationStateEnum;
import com.gdcp.weibo.exceptions.UserRelationOperationException;
import com.gdcp.weibo.service.UserRelationService;
import com.gdcp.weibo.util.ListSortUtil;

@Service
public class UserRelationServiceImpl implements UserRelationService {

	@Autowired
	private UserRelationDao userRelationDao;
	@Autowired
	private UserDao userDao;

	/**
	 * 取消关注
	 * 
	 */
	@Override
	@Transactional
	public UserRelationExecution cancelUserRelation(long userId, long starsId) throws UserRelationOperationException{
		UserRelationExecution ure=new UserRelationExecution();
		if (userId<=0||starsId<=0) {
			return new UserRelationExecution(UserRelationStateEnum.NULL_USERRELATIONID);
		}else {
			try {
				UserRelation userRelation=new UserRelation();
				User user=new User();
				User stars=new User();
				user.setUserId(userId);
				stars.setUserId(starsId);
				userRelation.setFans(user);
				userRelation.setStars(stars);
				UserRelation userRelation1=userRelationDao.queryUserRelation(userRelation);
				if (userRelation1!=null&&userRelation1.getState()==0) {
					int effectedNum=userRelationDao.deleteUserRelationById(userId, starsId);
					//判断执行是否成功，返回对应stateInfo
					isEffectedNum(effectedNum,ure);
				}else if(userRelation1!=null&&userRelation1.getState()==1){
					userRelation.setState(-1);
					userRelation.setLastEditTime(new Date());
					int effectedNum = userRelationDao.updateUserRelation(userRelation);
					//判断执行是否成功，返回对应stateInfo
					isEffectedNum(effectedNum,ure);
				}else if(userRelation1==null){
					userRelation.setFans(stars);
					userRelation.setStars(user);
					UserRelation userRelation2=userRelationDao.queryUserRelation(userRelation);
					if (userRelation2!=null&&userRelation2.getState()==-1) {
						int effectedNum=userRelationDao.deleteUserRelationById(starsId,userId);
						//判断执行是否成功，返回对应stateInfo
						isEffectedNum(effectedNum,ure);
					}else if (userRelation2!=null&&userRelation2.getState()==1) {
						userRelation.setState(0);
						userRelation.setLastEditTime(new Date());
						int effectedNum=userRelationDao.updateUserRelation(userRelation);
						//判断执行是否成功，返回对应stateInfo
						isEffectedNum(effectedNum,ure);
					}else {
						return new UserRelationExecution(UserRelationStateEnum.NULL_USERRELATION);
					}
				}else {
					return new UserRelationExecution(UserRelationStateEnum.NULL_USERRELATION);
				}
			} catch (Exception e) {
				throw new UserRelationOperationException("cancelUserRelation error :" +e.toString());
			}
		}
		return ure;
	}
	
	/**
	 * 判断执行是否成功，返回对应stateInfo
	 * @param effectedNum
	 * @return
	 */
	public UserRelationExecution isEffectedNum(int effectedNum,UserRelationExecution ure) {
		if (effectedNum<=0) {
			ure.setState(UserRelationStateEnum.INNER_ERROR.getState());
			return ure;
		}else {
			ure.setState(UserRelationStateEnum.SUCCESS.getState());
			return ure;
		}
	}
	
	/**
	 * 查询粉丝列表或明星列表
	 */
	@Override
	@Transactional
	public UserRelationExecution getUserRelationList(UserRelation relationCondition)
			throws UserRelationOperationException {
		UserRelationExecution ure = new UserRelationExecution();
		if (relationCondition == null ) {
			return new UserRelationExecution(UserRelationStateEnum.EMPTY);
		} else {
			try {
				//查询该用户已关注的明星
				//1.查询用户(fans)所在关系表中对应的stars用户列表1
				//2.查询用户(stars)为所在关系表中state为1时对应的fans列表2
				//3.最终所关注的用户列表为 最后列表1 + 列表2
				List<UserRelation> userRelationList=new ArrayList<UserRelation>();
				if (relationCondition.getFans()!=null && relationCondition.getFans().getUserId()!=null) {
					User user = userDao.queryUserById(relationCondition.getFans().getUserId());
					if (user != null) {
						//1.查询用户(fans)所在关系表中对应的stars用户列表1
						relationCondition.setState(0);
						int count1 = userRelationDao.queryUserRelationCount(relationCondition);
						List<UserRelation> userRelationList1 = userRelationDao.queryUserRelationList(relationCondition);
						
						relationCondition.setState(1);
						int count2 = userRelationDao.queryUserRelationCount(relationCondition);
						List<UserRelation> userRelationList2 = userRelationDao.queryUserRelationList(relationCondition);
						
						//2.查询用户(stars)为所在关系表中state为1时对应的fans列表2
						UserRelation tempCondition=new UserRelation();
						tempCondition.setStars(relationCondition.getFans());
						tempCondition.setState(1);
						List<UserRelation> userRelationList3 = userRelationDao.queryUserRelationList(tempCondition);
						int count3 = userRelationDao.queryUserRelationCount(tempCondition);
						
						tempCondition.setState(-1);
						List<UserRelation> userRelationList4 = userRelationDao.queryUserRelationList(tempCondition);
						int count4 = userRelationDao.queryUserRelationCount(tempCondition);
						
						//追加到总列表
						userRelationList.addAll(userRelationList1);
						userRelationList.addAll(userRelationList2);
						userRelationList.addAll(userRelationList3);
						userRelationList.addAll(userRelationList4);
						List<UserRelation> tempRelationList=ListSortUtil.ListSortOfUserRelation(userRelationList);
						if ((count1+count2+count3+count4)>0) {
							ure.setCount((count1+count2+count3+count4));
							ure.setUserRelationList(tempRelationList);
							ure.setState(UserRelationStateEnum.SUCCESS.getState());
							return ure;
						}else {
							return new UserRelationExecution(UserRelationStateEnum.NULL_USERRELATION);
						}
					} else {
						return new UserRelationExecution(UserRelationStateEnum.OFFLINE_USER);
					}
					//查询该用户被关注的粉丝列表
					//1.查询用户(stars)所在关系表中对应的fans用户列表1
					//2.查询用户(fans)为所在关系表中state为-1时对应的stars列表2
					//3.最终所关注的用户列表为 最后列表1 + 列表2
				}else if (relationCondition.getStars()!=null && relationCondition.getStars().getUserId()!=null) {
					User user = userDao.queryUserById(relationCondition.getStars().getUserId());
					if (user != null) {
						//1.查询用户(stars)所在关系表中对应的fans用户列表1
						relationCondition.setState(0);
						int count1 = userRelationDao.queryUserRelationCount(relationCondition);
						List<UserRelation> userRelationList1 = userRelationDao.queryUserRelationList(relationCondition);
						
						relationCondition.setState(1);
						int count2 = userRelationDao.queryUserRelationCount(relationCondition);
						List<UserRelation> userRelationList2 = userRelationDao.queryUserRelationList(relationCondition);
						
						//2.查询用户(fans)为所在关系表中state为-1时对应的stars列表2
						UserRelation tempCondition=new UserRelation();
						tempCondition.setFans(relationCondition.getStars());
						tempCondition.setState(-1);
						List<UserRelation> userRelationList3 = userRelationDao.queryUserRelationList(tempCondition);
						int count3 = userRelationDao.queryUserRelationCount(tempCondition);
						
						tempCondition.setState(1);
						List<UserRelation> userRelationList4 = userRelationDao.queryUserRelationList(tempCondition);
						int count4 = userRelationDao.queryUserRelationCount(tempCondition);
						//追加到总列表
						userRelationList.addAll(userRelationList1);
						userRelationList.addAll(userRelationList2);
						userRelationList.addAll(userRelationList3);
						userRelationList.addAll(userRelationList4);
						List<UserRelation> tempRelationList=ListSortUtil.ListSortOfUserRelation(userRelationList);
						if ((count1+count2+count3+count4)>0) {
							ure.setCount((count1+count2+count3+count4));
							ure.setUserRelationList(tempRelationList);
							ure.setState(UserRelationStateEnum.SUCCESS.getState());
							return ure;
						}else {
							return new UserRelationExecution(UserRelationStateEnum.NULL_USERRELATION);
						}
					} else {
						return new UserRelationExecution(UserRelationStateEnum.OFFLINE_USER);
					}
				}else {
					return new UserRelationExecution(UserRelationStateEnum.EMPTY);
				}
			} catch (Exception e) {
				throw new UserRelationOperationException("getStarsRelationByFans error :"+e.toString());
			}
		}
	}

	/**
	 * 添加用户关系（关注）
	 */
	@Override
	@Transactional
	public UserRelationExecution addUserRelation(UserRelation userRelation) throws UserRelationOperationException{
		if (userRelation == null ||userRelation.getFans()==null||userRelation.getStars()==null) {
			return new UserRelationExecution(UserRelationStateEnum.EMPTY);
		} else {
			try {
				User fans = userDao.queryUserById(userRelation.getFans().getUserId());
				User stars = userDao.queryUserById(userRelation.getStars().getUserId());
				if (fans != null&&stars!=null) {
					//判断当前两个用户是否已存在联系，
					//如果存在关系方向一样的话，就直接返回不执行操作（isUserRelation1）
					//如果存在关系方向相反的话就把state便为1（互相关注）（isUserRelation2）
					//不存在侧重新插入
					UserRelation isUserRelation1 =userRelationDao.queryUserRelation(userRelation);
					UserRelation tempRelation=new UserRelation();
					tempRelation.setFans(userRelation.getStars());
					tempRelation.setStars(userRelation.getFans());
					UserRelation isUserRelation2 =userRelationDao.queryUserRelation(tempRelation);
					if (isUserRelation1!=null) {
						if (isUserRelation1.getState()==-1) {
							userRelation.setState(1);
							userRelation.setLastEditTime(new Date());
							int effectedNum=userRelationDao.updateUserRelation(userRelation);
							if (effectedNum>0) {
								return new UserRelationExecution(UserRelationStateEnum.SUCCESS); 
							} else {
								return new UserRelationExecution(UserRelationStateEnum.INNER_ERROR);
							}
						}
						return new UserRelationExecution(UserRelationStateEnum.REPEAT_ERROR);
					}else if (isUserRelation2!=null) {
						if (isUserRelation2.getState()==1||isUserRelation2.getState()==-1) {
							return new UserRelationExecution(UserRelationStateEnum.REPEAT_ERROR);
						}
						UserRelation updateUserRelation=new UserRelation();
						updateUserRelation.setState(1);
						updateUserRelation.setLastEditTime(new Date());
						updateUserRelation.setFans(tempRelation.getFans());
						updateUserRelation.setStars(tempRelation.getStars());;
						int effectedNum=userRelationDao.updateUserRelation(updateUserRelation);
						if (effectedNum>0) {
							return new UserRelationExecution(UserRelationStateEnum.SUCCESS); 
						} else {
							return new UserRelationExecution(UserRelationStateEnum.INNER_ERROR);
						}
					} else {
						    userRelation.setCreateTime(new Date());
						    userRelation.setLastEditTime(new Date());
						    userRelation.setState(0);
							int insertEffectedNum=userRelationDao.insertUserRelation(userRelation);
							if (insertEffectedNum>0) {
								return new UserRelationExecution(UserRelationStateEnum.SUCCESS); 
							} else {
								return new UserRelationExecution(UserRelationStateEnum.INNER_ERROR);
							}
					}
				} else {
					return new UserRelationExecution(UserRelationStateEnum.OFFLINE_USER);
				}
			} catch (Exception e) {
				throw new UserRelationOperationException("addUserRelation error :"+e.toString());
			}
		}
	}

	/**
	 * 查询用户关系
	 */
	@Override
	public UserRelationExecution getUserRelation(long userId, long starsId) throws UserRelationOperationException{
		if (userId<=0||starsId<=0) {
			return new UserRelationExecution(UserRelationStateEnum.NULL_USERRELATIONID);
		}else {
			try {
				UserRelationExecution ure=new UserRelationExecution();
				UserRelation userRelation=new UserRelation();
				User user=new User();
				User stars=new User();
				user.setUserId(userId);
				stars.setUserId(starsId);
				userRelation.setFans(user);
				userRelation.setStars(stars);
				UserRelation tempRelation=userRelationDao.queryUserRelation(userRelation);
				if (tempRelation!=null&&tempRelation.getState()==0) {
					return new UserRelationExecution(UserRelationStateEnum.FORWARD_RELATION);
				}else if(tempRelation!=null&&tempRelation.getState()==1){
					return new UserRelationExecution(UserRelationStateEnum.MUTUAL_RELATION);
				}else if(tempRelation!=null&&tempRelation.getState()==-1){
					return new UserRelationExecution(UserRelationStateEnum.BACKWARD_RELATION);
				}else{
					userRelation.setFans(stars);
					userRelation.setStars(user);
					UserRelation tempRelation2=userRelationDao.queryUserRelation(userRelation);
					if (tempRelation2!=null&&tempRelation2.getState()==0) {
						return new UserRelationExecution(UserRelationStateEnum.BACKWARD_RELATION);
					}else if(tempRelation2!=null&&tempRelation2.getState()==1){
						return new UserRelationExecution(UserRelationStateEnum.MUTUAL_RELATION);
					}else if(tempRelation2!=null&&tempRelation2.getState()==-1){
						return new UserRelationExecution(UserRelationStateEnum.FORWARD_RELATION);
					}else {
						return new UserRelationExecution(UserRelationStateEnum.NULL_USERRELATION);
					}
				}
			} catch (Exception e) {
				throw new UserRelationOperationException("getUserRelation error :" + e.toString());
			}
		}
	}

}
