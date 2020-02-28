package com.gdcp.weibo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdcp.weibo.entity.User;

//UserDao查询的接口
public interface UserDao {
	
	List<User> queryUserList(@Param("userCondition")User userCondition);
	
	/**
	 * 更改用户信息
	 * @param user
	 * @return
	 */
	int updateUser(User user);
	
	/**
	 * 通过用户Id查询用户信息
	 * @param userId
	 * @return
	 */
	User queryUserById(long userId);
	
	/**
	 * 通过用户昵称查询用户信息
	 * @param nickName
	 * @return
	 */
	User queryUserByNickName(String nickName);
	
	/*
	 * 列出用户列表 
	 * @return userlist
	 * (由于mybatis不需要dao去写实现类，而直接是在mybatis自动去实现，只需在mapper配置就行)
	 */
	List<User> queryUser();
	
	//新增用户
	int inserUser(User user);
	
	/**
	 * 通过用户名与密码查询用户
	 * @param userName
	 * @param password
	 * @return
	 */
	User queryUserByUmAndPw(@Param("userName")String userName,@Param("password")String password);
}
