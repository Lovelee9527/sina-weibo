package com.gdcp.weibo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.entity.User;
public interface CommentDao {

	/**
	 * 新增评论
	 * @param comment
	 * @return
	 */
	int insertComment(Comment comment);
	
	/**
	 * 按评论Id查询评论
	 * @param commentId
	 * @return
	 */
	Comment queryCommentByCommentId(Long commentId);

	/**
	 * 通过微博Id查询评论列表
	 * @return
	 */
	List<Comment> queryCommentListByWeiboId(long weiboId);
	
	/**
	 * 通过userId查询评论列表
	 * @return
	 */
	List<Comment> queryCommentListByUserId(long userId);

	/**
	 * 按照微博Id查评论数量
	 * @param weiboId
	 * @return
	 */
	int queryCommentCountByWeiboId(long weiboId);
	/**
	 * 通过输入条件查询评论总数
	 * @param commentCondition
	 * @return
	 */
	int queryCommentCount(@Param("commentCondition")Comment commentCondition);
	
	/**
	 * 删除评论
	 * @param comment
	 * @return
	 */
	int deleteCommentById(@Param("userId")long userId,@Param("commentId")long commentId);
	
	/**
	 * 通过输入条件查评论
	 * @param userId
	 * @return
	 */
	Comment queryComment(@Param("commentCondition")Comment commentCondition);
	
}
