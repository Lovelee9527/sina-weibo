package com.gdcp.weibo.dao;


import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;

import com.gdcp.weibo.entity.Praise;
import com.gdcp.weibo.entity.User;

public class PraiseDaoTest extends BaseTest{
   @Autowired
   private PraiseDao praiseDao;   
   
   @Test
// @Ignore
 public void testDeletePraise() {
	   Praise praise=new Praise();
	   praise.setState(-1);
	   User user=new User();
	   user.setUserId(23L);
	   praise.setUser(user);
	   praise.setWeiboId(59L);
	   int effectedNum=praiseDao.deletePraise(praise);
	   System.out.println(effectedNum);
 }
   
   @Test
//   @Ignore
   public void testUpdatePraise() {
	   Praise praise=new Praise();
	   praise.setState(-1);
	   praise.setLastEditTime(new Date());
	   User user=new User();
	   user.setUserId(23L);
	   praise.setUser(user);
	   praise.setWeiboId(59L);
	   int effectedNum=praiseDao.updatePraise(praise);
	   System.out.println(effectedNum);
   }
   
   @Test
   @Ignore
   public void testQueryPraiseListAndCount() {
	   Praise praiseCondition=new Praise();
	   praiseCondition.setState(1);
	   User user=new User();
	   user.setUserId(23L);
	   praiseCondition.setUser(user);
//	   praiseCondition.setWeiboId(59L);
	   List<Praise> list=praiseDao.queryPraiseList(praiseCondition);
	   int count=praiseDao.queryPraiseCount(praiseCondition);
	   System.out.println(list.size());
	   System.out.println(count);
   }
   
   @Test
   @Ignore
   public void testInserPraise() {
	   User user = new User();
	   user.setUserId(23L);
	  Praise praise=new Praise();
	   praise.setUser(user);
	   praise.setWeiboId(2L);
	   praise.setCreateTime(new Date());
	   praise.setState(1);
	   int effectedNum = praiseDao.insertPraise(praise);
	   System.out.println(effectedNum);
   }
 
}
