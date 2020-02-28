package com.gdcp.weibo.service;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.dto.ReplyExecution;
import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.entity.Reply;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.exceptions.ReplyOperationException;

public class ReplyServiceTest extends BaseTest{
	@Autowired
	private ReplyService replyService;
	
	@Test
	public void getReplyListTest() throws ReplyOperationException {
//		ReplyExecution re = replyService.getReplyListById(3L);
//		System.out.println(re.getReplyList().get(0).getContent());
//		System.out.println(re.getReplyList().get(0).getFromNickName());
//		System.out.println(re.getReplyList().get(0).getToNickName());
	}

	@Test
	@Ignore
	public void addReplyTest() throws ReplyOperationException {
		Comment comment =  new Comment();
		User fromUser = new User();
		Reply reply = new Reply();		
		comment.setCommentId(3L);
		reply.setComment(comment);
		fromUser.setUserId(23L);
		reply.setToId(26L);
		reply.setContent("回复第一条评论的内容");
		reply.setCreateTime(new Date());
		ReplyExecution re = replyService.addReply(reply, fromUser);
		System.out.println(re.getStateInfo());
		System.out.println(re.getReply().getContent());
	}
}
