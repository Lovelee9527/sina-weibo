package com.gdcp.weibo.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.dto.NotifyExecution;
import com.gdcp.weibo.entity.Notify;

public class NotifyServiceTest extends BaseTest{

	@Autowired
	private NotifyService notifyService;
	
//	@Ignore
	@Test
	public void testGetNotifysByTargetId() {
//		NotifyExecution ne=notifyService.getNotifysByTargetId(24);
		Notify notifyCondition=new Notify();
		notifyCondition.setTargetId(24L);
		NotifyExecution ne=notifyService.getNotifyList(notifyCondition);
		System.out.println(ne.getState());
		System.out.println(ne.getStateInfo());
		System.out.println(ne.getNotifyList());
		System.out.println(ne.getNotifyList().size());
	}
	
	@Ignore
	@Test
	public void testAddNotifyOfAt() {
		String atNickName="@honay";
		Notify notify=new Notify();
		notify.setWeiboId(42L);
		notify.setSenderId(24L);
		notify.setSenderType(1);
		NotifyExecution ne=notifyService.addNotifyOfWeiboAt(atNickName, notify);
		System.out.println(ne.getState());
		System.out.println(ne.getStateInfo());
	}
}
