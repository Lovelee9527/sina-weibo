package com.gdcp.weibo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jaxen.function.CeilingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcp.weibo.dao.CommentDao;
import com.gdcp.weibo.dao.UserDao;
import com.gdcp.weibo.dto.CommentExecution;
import com.gdcp.weibo.dto.NotifyExecution;
import com.gdcp.weibo.dto.PraiseExecution;
import com.gdcp.weibo.dto.WeiboExecution;
import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.CommentStateEnum;
import com.gdcp.weibo.enums.NotifyStateEnum;
import com.gdcp.weibo.enums.PraiseStateEnum;
import com.gdcp.weibo.enums.WeiboStateEnum;
import com.gdcp.weibo.exceptions.CommentOperationException;
import com.gdcp.weibo.service.CommentService;
import com.gdcp.weibo.service.NotifyService;
import com.gdcp.weibo.service.WeiboService;
import com.gdcp.weibo.util.ListSortUtil;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private WeiboService weiboService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CommentService commentService;

	/**
	 * 添加评论
	 * */
	@Override
	@Transactional
	public CommentExecution addComment(Comment comment) throws CommentOperationException{
	    //空值判断 
		if (comment!=null && comment.getUser()!=null && comment.getUser().getUserId()!=null) {
			   try {
					comment.setTime(new Date());
					//添加评论信息
					int effectedNum = commentDao.insertComment(comment);
					if (effectedNum <= 0) {
						throw new CommentOperationException("评论失败");
					}else {
						Weibo weibo=weiboService.getWeiboByWeiboId(comment.getWeibo().getWeiboId());
//						Comment tempComment = commentDao.queryComment(comment);
						Comment tempComment = commentDao.queryCommentByCommentId(comment.getCommentId());
						User tempUser = userDao.queryUserById(comment.getUser().getUserId());
						Notify notify = new Notify();
						notify.setWeiboId(weibo.getWeiboId());
						notify.setCommentId(tempComment.getCommentId());
						notify.setTargetId(weibo.getAuthor().getUserId());
						notify.setSenderId(tempUser.getUserId());
						notify.setNickName(tempUser.getNickName());
						notify.setAction(2);
						//给微博作者添加评论通知
						NotifyExecution ne = notifyService.addNotify(notify);
						if (ne.getState() == NotifyStateEnum.SUCCESS.getState()) {
						  return new CommentExecution(CommentStateEnum.SUCCESS);
						}
						//如果评论用户Id等于微博作者Id,则设置消息为已读
						if (tempUser.getUserId()==weibo.getAuthor().getUserId()) {
							NotifyExecution ne1 = notifyService.modifyNotifyIsRead(weibo.getAuthor().getUserId(), 2);
						}
						return new CommentExecution(CommentStateEnum.SUCCESS,comment);
					}
			} catch (Exception e) {
				throw new CommentOperationException("addComment error:" + e.getMessage());
		}
	 }else {
		 return new CommentExecution(CommentStateEnum.EMPTY);
	 }
  }

	/**
	 * 根据(comment.user.userId=weibo.Author.userId)获取评论列表
	 * */
	@Override
	public CommentExecution getCommentListByUserId(long userId) {
		if (userId<=0) {
			return new CommentExecution(CommentStateEnum.ERROR);
		}else {
			try {
				List<Comment> commentList=new ArrayList<Comment>();
				int count=0;
				Weibo weiboCondition=new Weibo();
				User author=new User();
				author.setUserId(userId);
				weiboCondition.setAuthor(author);
				WeiboExecution we=weiboService.getWeiboList(weiboCondition, 0, 999);
				if (we.getState()==WeiboStateEnum.SUCCESS.getState()) {
					for (Weibo weibo:we.getWeiboList()) {			
						CommentExecution ce = commentService.getCommentListByWeiboId(weibo.getWeiboId());
						if (ce.getState()==CommentStateEnum.SUCCESS.getState()&&ce.getCount()>0) {
							commentList.addAll(ce.getCommentList());
							count +=ce.getCount();
						}
					}
				}
				return new CommentExecution(CommentStateEnum.SUCCESS,ListSortUtil.ListSortOfComments(commentList),count);	
			} catch (Exception e) {
				throw new CommentOperationException("getCommentListById error :"+e.toString());
			}
		}	
	}
	/**
	 * 根据WeiboId获取评论列表
	 * */
	@Override
	public CommentExecution getCommentListByWeiboId(long weiboId) {
		if (weiboId<=0) {
			return new CommentExecution(CommentStateEnum.ERROR);
		}else {
			try {
				List<Comment> commentList = commentDao.queryCommentListByWeiboId(weiboId);
				int count=commentDao.queryCommentCountByWeiboId(weiboId);
				return new CommentExecution(CommentStateEnum.SUCCESS,commentList,count);	
			} catch (Exception e) {
				throw new CommentOperationException("getCommentListById error :"+e.toString());
			}
		}	
	}

	/**
	 * 删除评论
	 */
	@Override
	public CommentExecution deleteCommentById(Long userId,Long commentId)  {
		if(userId<0||commentId<0) {
			return new CommentExecution(CommentStateEnum.EMPTY);
		}else {
			try {
				int effectedNum = commentDao.deleteCommentById(userId, commentId);
				if (effectedNum<=0) {
					throw new CommentOperationException("删除失败");
				}else {			
					//若有评论的对象，则删除通知表对用的目标用户通知
					Notify notify = new Notify();
					notify.setAction(2);			
					NotifyExecution ne = notifyService.deleteNotify(notify);
					if (ne.getState() == NotifyStateEnum.SUCCESS.getState()) {
					  return new CommentExecution(CommentStateEnum.SUCCESS);
					}
					return new CommentExecution(CommentStateEnum.SUCCESS);
				}				
			} catch (Exception e) {
				throw new CommentOperationException("deleteComment err:"+e.toString());
			}
		}
	}
	
	/**
	 * 根据评论Id查找评论
	 */
	@Override
	public Comment getCommentByCommentId(Long commentId) {
		return commentDao.queryCommentByCommentId(commentId);
	}

}
