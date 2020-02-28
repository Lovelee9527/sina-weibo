package com.gdcp.weibo.service;

import java.util.List;

import com.gdcp.weibo.dto.ImageHolder;
import com.gdcp.weibo.dto.UserExecution;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.exceptions.UserOperationException;

public interface UserService {

	public List<User> getUserList();
	
	public UserExecution getUserList(User userCondition);
	
	/**
	 * 注册用户
	 * @param user
	 * @param thumbnail
	 * @return UserExecution
	 */
	public UserExecution addUser(User user)throws UserOperationException;
	
	public UserExecution getUserByUnAndPw(String userName,String password);
	
	User getUserById(long userId);
	
	/**
	 * 修改密码
	 * @param userId
	 * @param password
	 * @param newPassword
	 * @return
	 */
	public UserExecution modifyUserPsw(Long userId,String password,String newPassword);
	
	/**
	 * 更改用户信息
	 * @param user
	 * @return
	 */
	public UserExecution modifyUser(User user,ImageHolder thumbnail);
	
}
