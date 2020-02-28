package com.gdcp.weibo.dao;


import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.entity.Collection;
import com.gdcp.weibo.entity.User;

public class CollectionDaoTest extends BaseTest{
   @Autowired
   private CollectionDao collectionDao; 
   
   @Test
   @Ignore
   public void testInserCollection() {
	   Collection collection=new Collection();
	   collection.setUserId(23L);
	   collection.setState(1);
	   collection.setWeboId(59L);
	   collection.setCreateTime(new Date());
	   collection.setLastEditTime(new Date());
	   int effectedNum = collectionDao.insertCollection(collection);
	   System.out.println(effectedNum);
   }
   
   @Test
//   @Ignore
   public void testQueryCollectionListAndCount() {
	   Collection collection=new Collection();
	   collection.setUserId(23L);
	   collection.setState(1);
	   collection.setWeboId(59L);
	   List<Collection> collections=collectionDao.queryCollectionList(collection);
	   int count=collectionDao.queryCollectionCount(collection);
	   System.out.println(collections.size());
	   System.out.println(count);
   }
   
   @Test
//   @Ignore
   public void testdeletectionCollection() {
	   Collection collection=new Collection();
	   collection.setUserId(23L);
	   collection.setState(1);
	   collection.setWeboId(59L);
	   int effectedNum = collectionDao.deleteCollection(collection);
	   System.out.println(effectedNum);
   }
 
}
