package com.gdcp.weibo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcp.weibo.dao.NotifyDao;
import com.gdcp.weibo.dao.UserDao;
import com.gdcp.weibo.dao.WeiboDao;
import com.gdcp.weibo.dto.NotifyExecution;
import com.gdcp.weibo.dto.PraiseExecution;
import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.entity.Praise;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.NotifyStateEnum;
import com.gdcp.weibo.enums.PraiseStateEnum;
import com.gdcp.weibo.exceptions.NotifyOperationExeption;
import com.gdcp.weibo.exceptions.PraiseOperationExeption;
import com.gdcp.weibo.service.NotifyService;
import com.gdcp.weibo.util.ReplaceUtil;

@Service
public class NotifyServiceImpl implements NotifyService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private WeiboDao weiboDao;
	@Autowired
	private NotifyDao notifyDao;

	@Override
	@Transactional
	public NotifyExecution addNotify(Notify notify) throws NotifyOperationExeption {
		if (notify == null) {
			return new NotifyExecution(NotifyStateEnum.EMPTY);
		} else {
			try {
				if (notify.getSenderId()>0) {
					User sender=userDao.queryUserById(notify.getSenderId());
					notify.setNickName(sender.getNickName());
					notify.setHeadImg(sender.getHeadImg());
				}
				notify.setCreateTime(new Date());
				notify.setType(1);
				notify.setState(1);
				notify.setSenderType(1);
				notify.setIsRead(0);
				if (notify.getSenderId() == notify.getTargetId()) {
					return new NotifyExecution(NotifyStateEnum.ERROR_NICKNAME);
				}
				int effectedNum = notifyDao.insertNotify(notify);
				if (effectedNum > 0) {
					return new NotifyExecution(NotifyStateEnum.SUCCESS);
				} else {
					return new NotifyExecution(NotifyStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new NotifyOperationExeption("addNotify error :" + e.toString());
			}
		}
	}

	@Override
	@Transactional
	public NotifyExecution addNotifyOfPraise(Praise praise) {
		try {
			Weibo weibo = weiboDao.queryWeiboById(praise.getWeiboId());
			Notify notify = new Notify();
			notify.setWeiboId(weibo.getWeiboId());
			notify.setAction(5);
			notify.setTargetId(weibo.getAuthor().getUserId());
			notify.setSenderId(praise.getUser().getUserId());
			notify.setContent(weibo.getContent());
			//若是本人点赞自己的微博则不用通知
			if (weibo.getAuthor().getUserId()==praise.getUser().getUserId()) {
				return new NotifyExecution(NotifyStateEnum.SUCCESS);
			}
			//若该通知已存在且无效则设为有效
			List<Notify> tempNotifys=notifyDao.queryNotifyList(notify);
			if (tempNotifys.size()>0) {
				if (tempNotifys.get(0).getState()==0) {
					notify.setState(1);
					int effectNum = notifyDao.updateNotify(notify);
					if (effectNum > 0) {
						return new NotifyExecution(NotifyStateEnum.SUCCESS);
					} else {
						return new NotifyExecution(NotifyStateEnum.INNER_ERROR);
					}
				}
			}
			NotifyExecution ne = addNotify(notify);
			return ne;
		} catch (Exception e) {
			throw new NotifyOperationExeption("addNotifyOfPraise" + e.toString());
		}
	}

	@Override
	@Transactional
	public NotifyExecution modifyNotifyState(Praise praise) {
		try {
			Weibo weibo = weiboDao.queryWeiboById(praise.getWeiboId());
			Notify notify = new Notify();
			notify.setWeiboId(weibo.getWeiboId());
			notify.setAction(5);
			notify.setContent(weibo.getContent());
			notify.setTargetId(weibo.getAuthor().getUserId());
			notify.setSenderId(praise.getUser().getUserId());
			notify.setState(0);
			notify.setCreateTime(new Date());
			int effectNum = notifyDao.updateNotify(notify);
			if (effectNum > 0) {
				return new NotifyExecution(NotifyStateEnum.SUCCESS);
			} else {
				return new NotifyExecution(NotifyStateEnum.INNER_ERROR);
			}
		} catch (Exception e) {
			throw new NotifyOperationExeption("modifyNotifyState error : " + e.toString());
		}
	}
	
	/*@Override
	public NotifyExecution getPraiseListOfWeibo(long authorId) {
		if (authorId<0) {
			return new NotifyExecution(NotifyStateEnum.EMPTY);
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
					return new NotifyExecution(NotifyStateEnum.SUCCESS,praiseList,count);
				}else {
					return new NotifyExecution(NotifyStateEnum.NULL_NOTIFY);
				}
			} catch (Exception e) {
				throw new PraiseOperationExeption("getPraiseListOfWeibo error :"+e.toString());
			}
		}
	}*/


	@Override
	@Transactional
	public NotifyExecution addNotifyOfWeiboAt(String atNickName, Notify notify) throws NotifyOperationExeption {
		if (atNickName == null || notify == null) {
			return new NotifyExecution(NotifyStateEnum.EMPTY);
		} else {
			try {
				// 去除@符号与空格
				String targetNickName = ReplaceUtil.atReplaceAll(atNickName);
				// 根据昵称查出相应用户给通知taigetId赋值
				User targetUser = userDao.queryUserByNickName(targetNickName);
				if (targetUser != null && targetUser.getUserId() > 0) {
					notify.setTargetId(targetUser.getUserId());
				} else {
					return new NotifyExecution(NotifyStateEnum.ERROR_NICKNAME);
				}
				notify.setCreateTime(new Date());
				notify.setType(1);
				notify.setState(1);
				notify.setAction(1);
				notify.setSenderType(1);
				notify.setIsRead(0);
				if (notify.getSenderId() == targetUser.getUserId()) {
					return new NotifyExecution(NotifyStateEnum.ERROR_NICKNAME);
				}
				int effectedNum = notifyDao.insertNotify(notify);
				if (effectedNum > 0) {
					return new NotifyExecution(NotifyStateEnum.SUCCESS);
				} else {
					return new NotifyExecution(NotifyStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new NotifyOperationExeption("addNotifyOfWeiboAt error :" + e.toString());
			}
		}
	}

	@Override
	public NotifyExecution getNotifysByTargetId(long targetId) {
		if (targetId < 0) {
			return new NotifyExecution(NotifyStateEnum.EMPTY);
		} else {
			try {
				List<Notify> notifieList = notifyDao.queryNotifysByTargetId(targetId);
				return new NotifyExecution(NotifyStateEnum.SUCCESS, notifieList);
			} catch (Exception e) {
				throw new NotifyOperationExeption("getNotifysByTargetId error :" + e.toString());
			}
		}
	}

	@Override
	public NotifyExecution getNotifyList(Notify notifyCondition) {
		if (notifyCondition == null) {
			return new NotifyExecution(NotifyStateEnum.EMPTY);
		} else {
			try {
				notifyCondition.setState(1);
				List<Notify> notifieList = notifyDao.queryNotifyList(notifyCondition);
				int count = notifyDao.queryNotifyCount(notifyCondition);
				return new NotifyExecution(NotifyStateEnum.SUCCESS, notifieList, count);
			} catch (Exception e) {
				throw new NotifyOperationExeption("getNotifysByTargetId error :" + e.toString());
			}
		}
	}

	@Override
	@Transactional
	public NotifyExecution modifyNotifyIsRead(long targetId, int action) {
		if (targetId < 0 || action < 0) {
			return new NotifyExecution(NotifyStateEnum.EMPTY);
		} else {
			try {
				Notify notify = new Notify();
				notify.setAction(action);
				notify.setState(1);
				notify.setTargetId(targetId);
				notify.setIsRead(1);
				int effectNum = notifyDao.updateNotify(notify);
				if (effectNum > 0) {
					return new NotifyExecution(NotifyStateEnum.SUCCESS);
				} else {
					return new NotifyExecution(NotifyStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new NotifyOperationExeption("modifyNotifyIsRead error : " + e.toString());
			}
		}
	}

	@Override
	@Transactional
	public NotifyExecution deleteNotify(Notify notify) {
		if (notify == null) {
			return new NotifyExecution(NotifyStateEnum.EMPTY);
		} else {
			try {
				int effectedNum = notifyDao.deleteNotify(notify);
				if (effectedNum > 0) {
					return new NotifyExecution(NotifyStateEnum.SUCCESS);
				} else {
					return new NotifyExecution(NotifyStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new NotifyOperationExeption("deleteNotify error : " + e.toString());
			}

		}
	}

}
