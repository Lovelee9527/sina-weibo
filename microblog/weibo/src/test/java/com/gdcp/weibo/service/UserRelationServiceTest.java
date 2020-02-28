package com.gdcp.weibo.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.dto.UserRelationExecution;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.UserRelation;
import com.gdcp.weibo.enums.UserRelationStateEnum;

public class UserRelationServiceTest extends BaseTest {

	@Autowired
	private UserRelationService userRelationService;

	@Test
	public void testGetUserRelations() {
		UserRelation relationCondition=new UserRelation();
		User stars=new User();
		stars.setUserId(23L);
		relationCondition.setStars(stars);
		UserRelationExecution ure=userRelationService.getUserRelationList(relationCondition);
		System.out.println(ure.getState());
		System.out.println(ure.getUserRelationList().get(0).getFans().getHeadImg());
		System.out.println(ure.getUserRelationList().get(0).getFans().getNickName());
		System.out.println(ure.getUserRelationList().get(0).getLastEditTime());
		System.out.println(ure.getUserRelationList().get(0).getFans().getHeadImg());
	}
	
	@Test
	@Ignore
	public void testUserRelationById() {
		UserRelationExecution ure=userRelationService.getUserRelation(25, 23);
		System.out.println(ure.getStateInfo());
	}
	
	@Test
	@Ignore
	public void testCancelUserRelationById() {
		UserRelationExecution ure=userRelationService.cancelUserRelation(25, 24);
		System.out.println(UserRelationStateEnum.stateInfoOf(ure.getState()));
	}
	
	@Ignore
	@Test
	public void testGetUserRelationList() {
		UserRelation relationCondition = new UserRelation();
//		userRelation.setState(0);
		User fans = new User();
		User stars = new User();
//		fans.setUserId(24L);
		stars.setUserId(25L);
//		relationCondition.setFans(fans);
		relationCondition.setStars(stars);
		UserRelationExecution ure =userRelationService.getUserRelationList(relationCondition);
		System.out.println(UserRelationStateEnum.stateInfoOf(ure.getState()));
		System.out.println(ure.getState());
		System.out.println(ure.getCount());
		System.out.println(ure.getUserRelationList().get(0).getFans().getUserId());
		System.out.println(ure.getUserRelationList().get(0).getStars().getUserId());
		System.out.println(ure.getUserRelationList().get(1).getFans().getUserId());
		System.out.println(ure.getUserRelationList().get(1).getStars().getUserId());
	}
	
	@Ignore
	@Test
	public void testAddUserRelation() {
		UserRelation userRelation = new UserRelation();
//		userRelation.setState(0);
		User fans = new User();
		User stars = new User();
		fans.setUserId(25L);
		stars.setUserId(23L);
		userRelation.setFans(fans);
		userRelation.setStars(stars);
		System.out.println(userRelation.getFans().getUserId());
		System.out.println(userRelation.getStars().getUserId());
		UserRelationExecution ure =userRelationService.addUserRelation(userRelation);
		System.out.println(ure.getStateInfo());
		System.out.println(ure.getState());
	}
}
