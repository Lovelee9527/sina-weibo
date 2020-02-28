package com.gdcp.weibo.dao;


import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.Weibo;

public class CommentDaoTest extends BaseTest{
   @Autowired
   private CommentDao commentDao;   
   
   @Test
   public void testQueryCommentById() {
	   Comment comment=commentDao.queryCommentByCommentId(11L);
	   System.out.println(comment.getWeibo().getWeiboId());
//	   User user = new User();
//	   Comment comment=new Comment();
//	   user.setUserId(23L);
//	   comment.setUser(user);
//	   Weibo weibo=new Weibo();
//	   weibo.setWeiboId(9L);
//	   comment.setWeibo(weibo);
//	   comment.setContent("这是第一条评论内容");
//	   comment.setTime(new Date());
//	   List<Comment> commentList=commentDao.queryCommentListById(63L);
//	   System.out.println(commentList.get(0).getContent());
//	   System.out.println(commentList.get(0).getTime());
//	   System.out.println(commentList.get(0).getUser().getHeadImg());
//	   System.out.println(commentList.get(0).getUser().getNickName());
//	   System.out.println(commentList.get(0).getCommentId());
   } 
   
   @Test
   @Ignore
   public void testInserComment() {
	   User user = new User();
	   Comment comment=new Comment();
	   user.setUserId(23L);
	   comment.setUser(user);
	   Weibo weibo=new Weibo();
	   weibo.setWeiboId(63L);
	   comment.setWeibo(weibo);
	   comment.setContent("这是第一条评论内容");;
	   comment.setTime(new Date());
	   int effectedNum = commentDao.insertComment(comment);
	   System.out.println(effectedNum);
   } 
}
