package com.gdcp.weibo.service;

import com.gdcp.weibo.dto.CommentExecution;
import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.exceptions.CommentOperationException;
import com.gdcp.weibo.exceptions.WeiboOperationException;

public interface CommentService {
	
	/**
	 * 根据用户Id获取评论列表
	 * @param userId
	 * @return
	 */
	CommentExecution getCommentListByUserId(long userId);
	
	/**
	 * 发表评论的返回类型，ShopExecution记录并返回给Controller处理
	 * */
	CommentExecution addComment(Comment comment);
	
	/**
	 * 根据weiboId获取评论列表
	 * */
	CommentExecution getCommentListByWeiboId(long weiboId);
	
	/**
	 * 根据评论Id查找评论
	 * @param commentId
	 * @return
	 */
	Comment getCommentByCommentId(Long commentId);
	
	/**
	 * 删除评论
	 * @param comment
	 * @return
	 */
	CommentExecution deleteCommentById(Long userId,Long commentId);
}
