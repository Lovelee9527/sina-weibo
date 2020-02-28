package com.gdcp.weibo.service;

import java.util.List;

import com.gdcp.weibo.dto.ReplyExecution;
import com.gdcp.weibo.entity.Reply;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.exceptions.ReplyOperationException;

public interface ReplyService {
	/**
	 * 回复评论的返回类型，ShopExecution记录并返回给Controller处理
	 * @param reply
	 * @param fromUser
	 */
    ReplyExecution addReply(Reply reply,User fromUser);

    /**
     * 根据commentId获取回复列表
     * @param commentId
     * @return
     */
	ReplyExecution getReplyListByCommentId(long commentId);
	
	/**
	 * 删除评论ById
	 * @param fromId
	 * @param replyId
	 * @return
	 */
	ReplyExecution deleteReplyById(long fromId, long replyId);
	
	/**
	 * 查询回复我的回复列表
	 * @param toUserId
	 * @return
	 */
	ReplyExecution getReplyListByToId(long toUserId);
}
