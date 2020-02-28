package com.gdcp.weibo.service;

import java.util.Date;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.dto.CommentExecution;
import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.exceptions.CommentOperationException;

public class CommentServiceTest extends BaseTest {
	@Autowired
	private CommentService commentService;

	@Test
//	@Ignore
	public void testAddComment() throws CommentOperationException {
		User user = new User();
		Comment comment = new Comment();
		user.setUserId(23L);
		comment.setUser(user);
		Weibo weibo = new Weibo();
		weibo.setWeiboId(69L);
		comment.setWeibo(weibo);
		comment.setContent("删掉，让我讲!");
		comment.setTime(new Date());
		CommentExecution ce = commentService.addComment(comment);
		System.out.println(ce);
	}

	@Test
	public void testGetCommentListById() throws CommentOperationException {
		Comment comment = new Comment();
		User user = new User();
		comment.setContent("最新");
		comment.setUser(user);
//		CommentExecution ce = commentService.getCommentListById(1L);
////		System.out.println(ce.getCommentList().get(0).getCommentId());
////		System.out.println(ce.getCommentList().get(2).getContent());
//		System.out.println(ce.getStateInfo());
//		System.out.println(ce.getCommentList().size());
	}
}
