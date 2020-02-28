package com.gdcp.weibo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcp.weibo.dao.PraiseDao;
import com.gdcp.weibo.dao.WeiboDao;
import com.gdcp.weibo.dto.NotifyExecution;
import com.gdcp.weibo.dto.PraiseExecution;
import com.gdcp.weibo.dto.WeiboExecution;
import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.entity.Praise;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.NotifyStateEnum;
import com.gdcp.weibo.enums.PraiseStateEnum;
import com.gdcp.weibo.enums.WeiboStateEnum;
import com.gdcp.weibo.exceptions.PraiseOperationExeption;
import com.gdcp.weibo.service.NotifyService;
import com.gdcp.weibo.service.PraiseService;
import com.gdcp.weibo.service.WeiboService;

@Service
public class PraiseServiceImpl implements PraiseService{

	@Autowired
	private PraiseDao praiseDao;
	@Autowired
	private WeiboDao weiboDao;
	@Autowired
	private WeiboService weiboService;
	@Autowired
	private NotifyService notifyService;
	
	@Override
	@Transactional
	public PraiseExecution addPraiseOfWeibo(Praise praise) 
			throws PraiseOperationExeption{
		if (praise==null && praise.getWeiboId()==null&&praise.getUser()==null&&praise.getUser().getUserId()<0) {
			return new PraiseExecution(PraiseStateEnum.EMPTY);
		}else {
			try {
				Weibo weibo=new Weibo();
				List<Praise> praiseList=praiseDao.queryPraiseList(praise);
				praise.setState(1);
				if (praiseList.size()==1) {
					if (praiseList.get(0).getState()==0) {
						praise.setLastEditTime(new Date());
						int effectedNum=praiseDao.updatePraise(praise);
						if (effectedNum>0) {
							List<Praise> tempPraiseList=praiseDao.queryPraiseList(praise);
							//给微博点赞新增通知消息
							NotifyExecution ne=notifyService.addNotifyOfPraise(praise);
							if (ne.getState()!=NotifyStateEnum.SUCCESS.getState()) {
								return new PraiseExecution(PraiseStateEnum.INNER_ERROR);
							}
							weibo.setWeiboId(praise.getWeiboId());
							WeiboExecution we=weiboService.updateWeibo(weibo);
							if (ne.getState()!=WeiboStateEnum.SUCCESS.getState()) {
								return new PraiseExecution(PraiseStateEnum.INNER_ERROR);
							}
							return new PraiseExecution(PraiseStateEnum.SUCCESS,tempPraiseList.get(0));
						}else {
							return new PraiseExecution(PraiseStateEnum.INNER_ERROR);
						}
					}else if (praiseList.get(0).getState()==1){
						praise.setState(0);
						praise.setLastEditTime(new Date());
						int effectedNum=praiseDao.updatePraise(praise);
						if (effectedNum>0) {
							List<Praise> tempPraiseList=praiseDao.queryPraiseList(praise);
							//给微博点赞通知消息设置成无效
							NotifyExecution ne=notifyService.modifyNotifyState(praise);
							if (ne.getState()!=NotifyStateEnum.SUCCESS.getState()) {
								return new PraiseExecution(PraiseStateEnum.INNER_ERROR);
							}
							weibo.setWeiboId(praise.getWeiboId());
							WeiboExecution we=weiboService.updateWeibo(weibo);
							if (ne.getState()!=WeiboStateEnum.SUCCESS.getState()) {
								return new PraiseExecution(PraiseStateEnum.INNER_ERROR);
							}
							return new PraiseExecution(PraiseStateEnum.SUCCESS,tempPraiseList.get(0));
						}else {
							return new PraiseExecution(PraiseStateEnum.INNER_ERROR);
						}
					}else {
						return new PraiseExecution(PraiseStateEnum.ERROR_WEIBO);
					}
				}else if (praiseList.size()==0) {
					praise.setCreateTime(new Date());
					praise.setLastEditTime(new Date());
					int effectedNum =praiseDao.insertPraise(praise);
					if (effectedNum>0) {
						//给微博点赞新增通知消息
						NotifyExecution ne=notifyService.addNotifyOfPraise(praise);
						if (ne.getState()!=NotifyStateEnum.SUCCESS.getState()) {
							return new PraiseExecution(PraiseStateEnum.INNER_ERROR);
						}
						List<Praise> tempPraiseList=praiseDao.queryPraiseList(praise);
						weibo.setWeiboId(praise.getWeiboId());
						WeiboExecution we=weiboService.updateWeibo(weibo);
						if (ne.getState()!=WeiboStateEnum.SUCCESS.getState()) {
							return new PraiseExecution(PraiseStateEnum.INNER_ERROR);
						}
						return new PraiseExecution(PraiseStateEnum.SUCCESS,tempPraiseList.get(0));
					}else {
						return new PraiseExecution(PraiseStateEnum.INNER_ERROR);
					}
				}else {
					return new PraiseExecution(PraiseStateEnum.NULL_Praise);
				}
			} catch (Exception e) {
				throw new PraiseOperationExeption("addPraiseOfWeibo error: "+e.toString());
			}
		}
	}


	@Override
	public PraiseExecution deletePraise(Praise praise) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PraiseExecution getPraiseStateAndCountOfWeibo(Praise praiseCondition) {
		if (praiseCondition==null ||praiseCondition.getUser()==null||praiseCondition.getUser().getUserId()<0) {
			return new PraiseExecution(PraiseStateEnum.EMPTY);
		}else {
			try {
				PraiseExecution pe=new PraiseExecution();
				List<Praise> praiseList=praiseDao.queryPraiseList(praiseCondition);
				if (praiseList.size()>0) {
					pe.setPraise(praiseList.get(0));
				}
				Praise tempPraise=new Praise();
				tempPraise.setState(1);
				tempPraise.setWeiboId(praiseCondition.getWeiboId());
				int count= praiseDao.queryPraiseCount(tempPraise);
				if (count>0) {
					pe.setCount(count);
				}
				pe.setState(PraiseStateEnum.SUCCESS.getState());
				return pe;
			} catch (Exception e) {
				throw new PraiseOperationExeption("getPraiseStateAndCountOfWeibo error: "+e.toString());
			}
		}
	}


	@Override
	public PraiseExecution getPraiseList(Praise praiseCondition) {
		if (praiseCondition==null) {
			return new PraiseExecution(PraiseStateEnum.EMPTY);
		}else {
			try {
				PraiseExecution pe=new PraiseExecution();
				List<Praise> praiseList=praiseDao.queryPraiseList(praiseCondition);
				int count= praiseDao.queryPraiseCount(praiseCondition);
				return new PraiseExecution(PraiseStateEnum.SUCCESS,praiseList,count);
			} catch (Exception e) {
				throw new PraiseOperationExeption("getPraiseList error: "+e.toString());
			}
		}
	}


	@Override
	public PraiseExecution getPraiseListByMe(long userId) {
		if (userId<=0) {
			return new PraiseExecution(PraiseStateEnum.EMPTY);
		}else {
			try {
				List<Weibo> weiboList=new ArrayList<>();
				Praise praiseCondition=new Praise();
				User user=new User();
				user.setUserId(userId);
				praiseCondition.setUser(user);
				praiseCondition.setState(1);
				PraiseExecution pe=getPraiseList(praiseCondition);
				if (pe.getState()==PraiseStateEnum.SUCCESS.getState()) {
					for(Praise praise:pe.getPraiseList()) {
						Weibo weibo=weiboService.getWeiboByWeiboId(praise.getWeiboId());
						if (weibo.getAuthor().getUserId()!=userId) {
							weiboList.add(weibo);
						}
					}
					return new PraiseExecution(PraiseStateEnum.SUCCESS,pe.getPraiseList(),pe.getCount(),weiboList);
				}else {
					return new PraiseExecution(PraiseStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new PraiseOperationExeption("getPraiseList error: "+e.toString());
			}
		}
	}

	/*@Override
	public PraiseExecution getPraiseListOfWeibo(long authorId) {
		if (authorId<0) {
			return new PraiseExecution(PraiseStateEnum.EMPTY);
		}else {
			try {
				List<Weibo> weiboList=new ArrayList<Weibo>();
				User author=new User();
				author.setUserId(authorId);
				Praise praiseCondition=new Praise();
				praiseCondition.setUser(author);
				praiseCondition.setState(1);;
				List<Praise> praiseList=praiseDao.queryPraiseList(praiseCondition);
				int count=praiseDao.queryPraiseCount(praiseCondition);
				if (praiseList.size()>0&&count>0) {
					for(Praise praise: praiseList) {
						if (praise.getWeiboId()>0) {
							Weibo weibo =weiboDao.queryWeiboById(praise.getWeiboId());
							weiboList.add(weibo);
						}
					}
					return new PraiseExecution(PraiseStateEnum.SUCCESS,praiseList,count,weiboList);
				}else {
					return new PraiseExecution(PraiseStateEnum.NULL_Praise);
				}
			} catch (Exception e) {
				throw new PraiseOperationExeption("getPraiseListOfWeibo error :"+e.toString());
			}
		}
	}*/

}
