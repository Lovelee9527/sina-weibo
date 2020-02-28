package com.gdcp.weibo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdcp.weibo.entity.Reply;

public interface ReplyDao {
	/**
	 * 插入评论
	 * @param Reply
	 * @return
	 */
	int insertReply(Reply reply);
	
	/**
	 * 通过commentId查回复
	 * @return
	 */
    List<Reply> queryReplyListByCommentId(@Param("commentId")long commentId);
    
    /**
     * 根据toId查回复列表
     * @param toId
     * @return
     */
    List<Reply> queryReplyListByToId(@Param("toId")long toId);
    
    /**
     * 通过输入条件查询回复总数
     * @param replyCondition
     * @return
     */
    int queryReplyCount(@Param("praiseCondition")Reply replyCondition);
    
  	/**
  	 * 删除回复
  	 * @param userId
  	 * @param commentId
  	 * @return
  	 */
	int deleteReplyById(@Param("fromId")long fromId,@Param("replyId")long replyId);
}
