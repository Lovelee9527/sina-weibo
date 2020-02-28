package com.gdcp.weibo.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.Weibo;

public class WeiboDaoTest extends BaseTest{
	@Autowired
	private WeiboDao weiboDao;
	
	@Test
	@Ignore
	public void testUpdateWeibo() {
		Weibo weibo=new Weibo();
		weibo.setWeiboId(69L);
		weibo.setPraiseCount(3);
		int eff=weiboDao.updateWeibo(weibo);
		System.out.println(eff);
//		System.out.println(weibo.getAuthor().getUserId());
//		int i=10/2;
//		System.out.println(i);
	}
	
	@Test
	@Ignore
	public void testQueryWeibo() {
		Weibo weibo=weiboDao.queryWeiboById(59L);
		System.out.println(weibo.getAuthor());
		System.out.println(weibo.getAuthor().getUserId());
//		int i=10/2;
//		System.out.println(i);
	}
	
	@Test
	@Ignore
	public void testDeleteWeiboById() {
//		int effectedNum=weiboDao.deleteWeiboById(23, 5);
//		System.out.println(effectedNum);
		Weibo weibo=weiboDao.queryWeiboById(23L);
		System.out.println(weibo.getContent());
	}
	
//	@Ignore
	@Test
	public void testQueryWeiboListAndCount() {
		Weibo weiboCondition=new Weibo();
		User author=new User();
//		author.setNickName("微博nay");
//		author.setUserId(23L);
//		weiboCondition.setAuthor(author);
//		weiboCondition.setContent("微博nay");
//		weiboCondition.setAuthor(author);
		List<Weibo> weiboList=weiboDao.queryWeiboList(weiboCondition, 0, 10);
//		System.out.println(weiboList.size());
//		System.out.println(weiboList.get(0).getContent());
//		System.out.println(weiboList.get(0).getAuthor().getNickName());
//		System.out.println(weiboList.get(1).getWeiboImgList().get(0).getImgAddr());
//		System.out.println("微博按最新时间为先时间查询顺序");
//		System.out.println(weiboList.get(0).getPublishTime());
//		System.out.println(weiboList.get(1).getPublishTime());
		int weiboCount=weiboDao.queryWeiboCount(weiboCondition);
		System.out.println("作者Id为23的微博总数为"+weiboCount);
	}
	
	@Test
	@Ignore
	public void testInsertWeibo() {
		Weibo weibo=new Weibo();
		User author=new User();
		author.setUserId(23L);
		weibo.setAuthor(author);
		weibo.setContent("今天熱搜沙发我坐定哒~");
		weibo.setPublishTime(new Date());
		int effectedNum=weiboDao.insertWeibo(weibo);
		System.out.println(effectedNum);
	}

}
