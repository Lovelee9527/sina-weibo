package com.gdcp.weibo.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.dto.ImageHolder;
import com.gdcp.weibo.dto.WeiboExecution;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.entity.WeiboImg;
import com.gdcp.weibo.exceptions.WeiboOperationException;
import com.gdcp.weibo.util.ReplaceUtil;

public class WeiboServiceTest extends BaseTest {

	@Autowired
	private WeiboService weiboService;

	@Test
	public void test() {
		String string="搜索 “微博” 相关微博";
		System.out.println("result:"+string.substring(4, 6));
	}
	
	@Ignore
	@Test
	public void testDeleteWeiboById() {
//		WeiboExecution we=weiboService.deleteWeiboById(23, 52);
		WeiboExecution we=weiboService.getAtMyWeibosByTargetId(23);
		System.out.println(we.getStateInfo());
		System.out.println(we.getWeiboList().get(0).getContent());
	}
	
	@Ignore
	@Test
	public void testGetWeiboList() {
		Weibo weiboCondition=new Weibo();
		User author=new User();
//		author.setUserId(23L);
//		author.setNickName("nay");
		weiboCondition.setContent("微博");
		weiboCondition.setAuthor(author);
		WeiboExecution we=weiboService.getWeiboList(weiboCondition, 0, 5);
		System.out.println(we.getStateInfo());
		System.out.println(we.getCount());
		System.out.println(we.getWeiboList().get(1).getWeiboImgList().get(0).getImgAddr());
		
	}
	
	
	@Test
	@Ignore
	public void testAddWeibo() throws WeiboOperationException, FileNotFoundException {
		Weibo weibo = new Weibo();
		User author = new User();
		author.setUserId(23L);
		weibo.setAuthor(author);
		weibo.setContent("今天熱搜沙发我坐定哒~");
		List<ImageHolder> weiboImgHolderList = new ArrayList<ImageHolder>();
		// 创建两个微博图文件流并将他们添加到详情图列表中
//		File weiboImg1 = new File("E:/microblog/images/5.jpg");
//		InputStream is1 = new FileInputStream(weiboImg1);
//		File weiboImg2 = new File("E:/microblog/images/6.jpg");
//		InputStream is2 = new FileInputStream(weiboImg2);
//		ImageHolder thumbnail1 = new ImageHolder(weiboImg1.getName(), is1);
//		ImageHolder thumbnail2 = new ImageHolder(weiboImg2.getName(), is2);
//		weiboImgHolderList.add(thumbnail1);
//		weiboImgHolderList.add(thumbnail2);
		//发布微博并验证
		WeiboExecution we = weiboService.addWeibo(weibo, weiboImgHolderList);
		System.out.println(we.getStateInfo());
	}
}
