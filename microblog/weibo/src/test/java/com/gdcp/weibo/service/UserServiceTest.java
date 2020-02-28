package com.gdcp.weibo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdcp.weibo.BaseTest;
import com.gdcp.weibo.dto.ImageHolder;
import com.gdcp.weibo.dto.UserExecution;
import com.gdcp.weibo.entity.User;

public class UserServiceTest extends BaseTest {

	@Autowired
	private UserService userService;

	@Test
	public void testModifyUserPsw() {
//		UserExecution ue=userService.modifyUserPsw(25L, "65432", "123456");
//		System.out.println(ue.getStateInfo());
		User userCondition=new User();
		userCondition.setNickName("h");
		UserExecution ue=userService.getUserList(userCondition);
		System.out.println(ue.getStateInfo());
		System.out.println(ue.getUserList().get(0).getNickName());
	}
	
	@Ignore
	@Test
	public void testUpdateUser() throws FileNotFoundException {
		User user = new User();
		// user.setUserName("正式后的用户名");
		// user.setPassword("正式后的密码");
		// user.setNickName("正式后的昵称");
		user.setUserId(23L);
		File headImg = new File("E:/microblog/images/7.jpg");
		InputStream headImgis = new FileInputStream(headImg);
		ImageHolder thumbnail = new ImageHolder(headImg.getName(), headImgis);
		UserExecution ue = userService.modifyUser(user, thumbnail);
		System.out.println(ue.getStateInfo());
	}

	@Test
	@Ignore
	public void testAddUser() throws FileNotFoundException {
		User user = new User();
		user.setUserName("添加用户service测试");
		user.setPassword("123456");
		user.setNickName("addUser");
		UserExecution ue = userService.addUser(user);
		System.out.println(ue.getStateInfo());
	}

	@Test
	@Ignore
	public void testGetUserList() {
		System.out.println("==start===");
		List<User> userList = userService.getUserList();
		System.out.println("debug1");
		System.out.println(userList.get(1).getUserName());
		System.out.println("debug2");
		System.out.println("==end===");
	}
}
