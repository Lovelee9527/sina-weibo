package com.gdcp.weibo.dao;


import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.entity.User;

public class UserDaoTest extends BaseTest{
   @Autowired
   private UserDao userDao;   
   
//   @Ignore
   @Test
   public void testQueryUserById() {
	   Long userId=23L;
//	   User user = userDao.queryUserById(userId);
	   User user=userDao.queryUserByNickName("honay");
	   System.out.println(user.getNickName());
	   System.out.println(user.getUserId());
   }
   
   @Test
   @Ignore
   public void testUpdateUser() {
	   User user=new User();
//	   user.setUserName("更改后的用户名");
//	   user.setPassword("更改后的密码");
	   user.setNickName("youNickName");
	   user.setHeadImg("test123");
	   user.setUserId(23L);
	   int effectedNum=userDao.updateUser(user);
	   System.out.println(effectedNum);
   }
   
   @Test
   @Ignore
   public void testInserUser() {
	   User user = new User();
	   user.setUserName("testdao");
	   user.setPassword("123456");
	   user.setBirthday(new Date());
	   int effectedNum = userDao.inserUser(user);
	   System.out.println(effectedNum);
   }
   
   @Test
   @Ignore
   public void testQueryUser() {
	   System.out.println(111);
	   List<User> userList = userDao.queryUser();
	   System.out.println(222);
//	   assertEquals(2,userList.size());
	   System.out.println(userList.size());
	   System.out.println(333);
   }
   
   @Test
   @Ignore
   public void testQueryUserByUnAndPw() {
	   String userName="you";
	   String password="123456";
	   User user = userDao.queryUserByUmAndPw(userName, password);
	   System.out.println(user.getNickName());
   }
}
