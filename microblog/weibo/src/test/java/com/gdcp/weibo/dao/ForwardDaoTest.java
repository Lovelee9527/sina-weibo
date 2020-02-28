package com.gdcp.weibo.dao;


import java.util.Date;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.entity.Forward;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.Weibo;

public class ForwardDaoTest extends BaseTest{
   @Autowired
   private ForwardDao forwardDao;   
   
   
   @Test
   public void testInserUser() {
	   User user = new User();
	   user.setUserId(23L);
	   Weibo weibo=new Weibo();
	   weibo.setWeiboId(2L);
	  Forward forward=new Forward();
	   forward.setForwardMsg("这是我第一次转发的微博");
	   forward.setUser(user);
	   forward.setWeibo(weibo);
	   forward.setTime(new Date());
	   int effectedNum = forwardDao.insertForward(forward);
	   System.out.println(effectedNum);
   }
 
}
