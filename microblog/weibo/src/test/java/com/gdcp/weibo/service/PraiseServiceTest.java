package com.gdcp.weibo.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.dto.PraiseExecution;
import com.gdcp.weibo.entity.Praise;
import com.gdcp.weibo.entity.User;

public class PraiseServiceTest extends BaseTest{

	@Autowired
	private PraiseService praiseService;
	
	/*@Test
	public void testGetPraiseListOfWeibo() {
//		Praise praise=new Praise();
//		User user=new User();
//		user.setUserId(24L);
//		praise.setUser(user);
//		praise.setWeiboId(59L);
		PraiseExecution pe=praiseService.getPraiseListOfWeibo(23);
		System.out.println(pe.getStateInfo());
		System.out.println(pe.getPraiseList());
		System.out.println(pe.getPraiseList().get(0).getUser().getNickName());
		System.out.println(pe.getWeiboList().get(0).getContent());
	}*/
	
	
	@Ignore
	@Test
	public void testAddPraise() {
		Praise praise=new Praise();
		User user=new User();
		user.setUserId(24L);
		praise.setUser(user);
		praise.setWeiboId(59L);
		PraiseExecution pe=praiseService.addPraiseOfWeibo(praise);
		System.out.println(pe.getStateInfo());
		System.out.println(pe.getPraise());
	}
}
