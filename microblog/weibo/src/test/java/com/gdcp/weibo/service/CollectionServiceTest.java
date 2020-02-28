package com.gdcp.weibo.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.dto.CollectionExecution;
import com.gdcp.weibo.dto.PraiseExecution;
import com.gdcp.weibo.entity.Collection;
import com.gdcp.weibo.entity.Praise;
import com.gdcp.weibo.entity.User;

public class CollectionServiceTest extends BaseTest{

	@Autowired
	private CollectionService collectionService;
	
//	@Ignore
	@Test
	public void testGetCollectionList() {
		Collection collection=new Collection();
		collection.setUserId(23L);
//		collection.setWeiboId(60L);
		CollectionExecution ce=collectionService.getCollectionList(collection);
		System.out.println(ce.getStateInfo());
		System.out.println(ce.getWeiboList());
		System.out.println(ce.getCount());
	}
}
