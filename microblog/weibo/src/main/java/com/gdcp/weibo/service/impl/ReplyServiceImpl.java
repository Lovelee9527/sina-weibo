package com.gdcp.weibo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.gdcp.weibo.dao.CommentDao;
import com.gdcp.weibo.dao.ReplyDao;
import com.gdcp.weibo.dao.UserDao;
import com.gdcp.weibo.dto.CommentExecution;
import com.gdcp.weibo.dto.NotifyExecution;
import com.gdcp.weibo.dto.ReplyExecution;
import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.entity.Reply;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.CommentStateEnum;
import com.gdcp.weibo.enums.NotifyStateEnum;
import com.gdcp.weibo.enums.ReplyStateEnum;
import com.gdcp.weibo.exceptions.CommentOperationException;
import com.gdcp.weibo.exceptions.ReplyOperationException;
import com.gdcp.weibo.service.NotifyService;
import com.gdcp.weibo.service.ReplyService;
import com.gdcp.weibo.service.WeiboService;

@Service
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	private ReplyDao replyDao;
	@Autowired
	private UserDao userDao;
	@Autowired
    private WeiboService weiboService;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private NotifyService notifyService;
	
	/**
	 * 添加回复
	 * */
	@Override
	public ReplyExecution addReply(Reply reply,User fromUser) throws ReplyOperationException {
		//空值判断 
		if (reply == null) {
			return new ReplyExecution(ReplyStateEnum.EMPTY);
		}
	     try {
	    	if (fromUser!=null&&fromUser.getUserId()>0&&fromUser.getNickName()==null||fromUser.getNickName().equals(' ')||
	    			fromUser.getHeadImg().equals(' ')||fromUser.getHeadImg()==null) {
	    		fromUser =userDao.queryUserById(fromUser.getUserId());
			}
	    	User toUser=userDao.queryUserById(reply.getToId());
	    	reply.setFromUser(fromUser);
	    	fromUser.setUserId(fromUser.getUserId());
	    	fromUser.setNickName(fromUser.getNickName());
	    	fromUser.setHeadImg(fromUser.getHeadImg());
	    	reply.setToHeadImg(toUser.getHeadImg());
	    	reply.setToNickName(toUser.getNickName());
	    	reply.setToId(toUser.getUserId());
			reply.setCreateTime(new Date());
			//添加评论信息
			int effectedNum = replyDao.insertReply(reply);
			if (effectedNum <= 0) {
				throw new ReplyOperationException("回复评论失败");
			}else {
				Comment tempComment = commentDao.queryCommentByCommentId(reply.getComment().getCommentId());
				Notify notify = new Notify();
				notify.setCommentId(tempComment.getCommentId());
				notify.setTargetId(reply.getToId());
				notify.setSenderId(fromUser.getUserId());
				notify.setNickName(toUser.getNickName());
				notify.setAction(3);
				//给评论作者添加回复通知
				NotifyExecution ne = notifyService.addNotify(notify);
				if (ne.getState() == NotifyStateEnum.SUCCESS.getState()) {
					return new ReplyExecution(ReplyStateEnum.SUCCESS);
				}
				//如果回复用户Id等于评论用户Id,则设置消息为已读
				if (fromUser.getUserId() == tempComment.getUser().getUserId()) {
					NotifyExecution ne1 = notifyService.modifyNotifyIsRead(tempComment.getUser().getUserId(), 3);
				}
			}
			} catch (Exception e) {
			throw new ReplyOperationException("addReply error:" + e.getMessage());
		}
		return new ReplyExecution(ReplyStateEnum.SUCCESS,reply);
	}
	
	/**
	 * 根据commentId获取回复列表
	 * */
	@Override
	public ReplyExecution getReplyListByCommentId(long commentId) {
			if (commentId<=0) {
				return new ReplyExecution(ReplyStateEnum.ERROR);
			}else {
				try {
					List<Reply> replyList = replyDao.queryReplyListByCommentId(commentId);
					return new ReplyExecution(ReplyStateEnum.SUCCESS,replyList,replyList.size());			
				} catch (Exception e) {
					throw new ReplyOperationException("getReplyListById error :"+e.toString());
				}
			}	
	}

	/**
	 * 删除回复ById
	 */
	@Override
	public ReplyExecution deleteReplyById(long fromId, long replyId) {
		if (fromId<0||replyId<0) {
			return new ReplyExecution(ReplyStateEnum.EMPTY);
		}else {
			try {
				int effectedNum = replyDao.deleteReplyById(fromId, replyId);
				if (effectedNum<=0) {
					throw new ReplyOperationException("删除失败");
				}else {
					return new ReplyExecution(ReplyStateEnum.SUCCESS);
				}
			} catch (Exception e) {
				throw new ReplyOperationException("delete err:" +e.getMessage());
			}
		}
	}

	@Override
	public ReplyExecution getReplyListByToId(long toUserId) {
		if (toUserId<0) {
			return new ReplyExecution(ReplyStateEnum.EMPTY);
		}else {
			try {
				List<Reply> replyList = replyDao.queryReplyListByToId(toUserId);
				for(Reply reply:replyList) {
					Comment comment=commentDao.queryCommentByCommentId(reply.getComment().getCommentId());
					reply.setComment(comment);
				}
				return new ReplyExecution(ReplyStateEnum.SUCCESS,replyList);
			} catch (Exception e) {
				throw new ReplyOperationException("getReplyListByToId err:" +e.getMessage());
			}
		}
	}


	
}
