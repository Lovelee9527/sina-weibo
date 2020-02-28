package com.gdcp.weibo.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.entity.Reply;

public class ReplyDaoTest extends BaseTest {
	@Autowired
	private ReplyDao replyDao;

//	@Test
//	public void testQueryReplyListById() {
//		List<Reply> replyList = replyDao.queryReplyListById(13L);
//		System.out.println(replyList.get(0).getReplyId());
//		System.out.println(replyList.get(0).getContent());
//		System.out.println(replyList.get(0).getFromId());
//		System.out.println(replyList.get(0).getToId());	
//		System.out.println(replyList.get(0).getFromNickName());
//		System.out.println(replyList.get(0).getToNickName());
//		System.out.println(replyList.get(0).getCreateTime());
//	}
//	@Test
////	@Ignore
//	public void testInserReply() {
//		Comment comment = new Comment();
//		Reply reply = new Reply();
//		comment.setCommentId(3L);
//		reply.setComment(comment);
//		reply.setFromId(23L);
//		reply.setToId(26L);
//		reply.setContent("这是第一条回复!");
//		reply.setCreateTime(new Date());
//		int effectedNum = replyDao.insertReply(reply);
//		System.out.println(effectedNum);
//	}
}
