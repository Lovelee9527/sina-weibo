package com.gdcp.weibo.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.util.ReplaceUtil;

public class NotifyDaoTest extends BaseTest {

	@Autowired
	private NotifyDao notifyDao;

	@Ignore
	@Test
	public void testQueryNotifyListAndCount() {
		Notify notifyCondition=new Notify();
		notifyCondition.setTargetId(23L);
//		notifyCondition.setIsRead(1);
		notifyCondition.setAction(4);
		List<Notify> notifieList = notifyDao.queryNotifyList(notifyCondition);
		int count=notifyDao.queryNotifyCount(notifyCondition);
		System.out.println(notifieList.size());
		System.out.println(count);
		System.out.println(notifieList.get(0).getTargetId());
		System.out.println(count);
		System.out.println(count);
	}
	
	@Ignore
	@Test
	public void testDelateNotify() {
		Notify notify=new Notify();
		notify.setAction(4);
		notify.setTargetId(25L);
		notify.setSenderId(23L);
		int effectedNum=notifyDao.deleteNotify(notify);
		System.out.println(effectedNum);
	}
	
	@Test
//	@Ignore
	public void testUpdateNotify() {
		Notify notify=new Notify();
		notify.setAction(5);
		notify.setTargetId(24L);
		notify.setState(1);
		int effectedNum=notifyDao.updateNotify(notify);
		System.out.println(effectedNum);
	}

	@Ignore
	@Test
	public void testQueryNotifysTargetId() {
		List<Notify> notifieList = notifyDao.queryNotifysByTargetId(23L);
		System.out.println(notifieList.size());
		System.out.println(notifieList.get(0).getSenderId());
	}

	@Ignore
	@Test
	public void testInsertNotify() {
		Notify notify = new Notify();
		notify.setCreateTime(new Date());
		notify.setContent("这是一条测试通知");
		notify.setIsRead(0);
		notify.setAction(1);
		notify.setTargetId(23L);
		notify.setUserId(25L);
		notify.setSenderId(25L);
		notify.setSenderType(1);
		notify.setType(1);
		int effectedNum = notifyDao.insertNotify(notify);
		System.out.println("影响的行数有：" + effectedNum);
	}
}
