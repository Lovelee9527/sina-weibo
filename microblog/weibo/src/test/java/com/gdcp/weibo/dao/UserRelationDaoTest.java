package com.gdcp.weibo.dao;


import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.UserRelation;

public class UserRelationDaoTest extends BaseTest{
   @Autowired
   private UserRelationDao userRelationDao;   
   
   @Ignore
   @Test
   public void testDeleteUserRelationBy() {
	   int effectedNum=userRelationDao.deleteUserRelationById(26, 23);
	   System.out.println(effectedNum);
   }
   
   @Test
   @Ignore
   public void testQueryUserRelation() {
	   UserRelation userRelation=new UserRelation();
	   User fans=new User();
	   fans.setUserId(24L);
	   userRelation.setFans(fans);
	   User stars=new User();
	   stars.setUserId(25L);
	   userRelation.setStars(stars);
//	   userRelation.setUserRelationId(2L);
	   UserRelation newRelation=userRelationDao.queryUserRelation(userRelation);
	   System.out.println(newRelation.getStars().getNickName());
	   System.out.println(newRelation.getFans().getNickName());
//	   System.out.println(relationList.get(0).getFans().getNickName());
//	   System.out.println(relationList.get(0).getFans().getHeadImg());
//	   System.out.println(relationList.get(1).getStars().getNickName());
//	   System.out.println(relationList.get(0).getFans().getHeadImg());
//	   System.out.println(relationList.get(0).getCreateTime());
//	   System.out.println(relationList.get(0).getState());
//	   System.out.println(relationList.get(0).getUserRelationId());
   }
   
   @Test
   @Ignore
   public void testUpdateUserRelation() {
	   UserRelation userRelation=new UserRelation();
	   userRelation.setState(0);
	   User fans=new User();
	   User stars=new User();
	   fans.setUserId(23L);
	   stars.setUserId(24L);
	   userRelation.setFans(fans);
	   userRelation.setStars(stars);
	   int effectedNum=userRelationDao.updateUserRelation(userRelation);
	   System.out.println(effectedNum);
   }

   @Ignore
   @Test
   public void testQueryUserRelationList() {
	   UserRelation relationCondition=new UserRelation();
	   User fans=new User();
	   fans.setUserId(24L);
	   relationCondition.setFans(fans);
	   List<UserRelation> relationList=userRelationDao.queryUserRelationList(relationCondition);
	   int count=userRelationDao.queryUserRelationCount(relationCondition);
//	   System.out.println(relationList.get(0).getStars().getNickName());
//	   System.out.println(relationList.get(0).getStars().getUserId());
//	   System.out.println(relationList.get(0).getFans().getNickName());
//	   System.out.println(relationList.get(0).getFans().getHeadImg());
//	   System.out.println(relationList.get(1).getStars().getNickName());
//	   System.out.println(relationList.get(0).getFans().getHeadImg());
//	   System.out.println(relationList.get(0).getCreateTime());
//	   System.out.println(relationList.get(0).getState());
	   System.out.println("同等条件下的数量"+count+relationList.size());
//	   System.out.println(relationList.get(0).getUserRelationId());
   }
   
   @Ignore
   @Test
   public void testInserUserRelation() {
	   User fans = new User();
	   User stars=new User();
	   UserRelation userRelation=new UserRelation();
	   fans.setUserId(26L);
	   stars.setUserId(23L);
	   userRelation.setFans(fans);
	   userRelation.setStars(stars);
	   userRelation.setState(0);
	   userRelation.setCreateTime(new Date());
	   int effectedNum = userRelationDao.insertUserRelation(userRelation);
	   System.out.println(effectedNum);
   }
 
}
