package com.gdcp.weibo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcp.weibo.dao.UserDao;
import com.gdcp.weibo.dto.ImageHolder;
import com.gdcp.weibo.dto.UserExecution;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.enums.UserStateEnum;
import com.gdcp.weibo.exceptions.UserOperationException;
import com.gdcp.weibo.service.UserService;
import com.gdcp.weibo.util.ImageUtil;
import com.gdcp.weibo.util.PathUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		return userDao.queryUser();
	}
	
	/**
	 * 更改用户信息包括图片处理
	 */
	@Override
	@Transactional
	public UserExecution modifyUser(User user,ImageHolder thumbnail) 
	   throws UserOperationException{
		if (user==null||user.getUserId()==null) {
			return new UserExecution(UserStateEnum.NULL_USER);
		}else {
			//1.判断图片是否需要处理
			try {
			if (thumbnail.getImage()!=null && thumbnail.getImageName()!=null&&!"".equals(thumbnail.getImageName())) {
				User tempUser=userDao.queryUserById(user.getUserId());
				if(tempUser.getHeadImg()!=null) {
					ImageUtil.delectFileOrPath(tempUser.getHeadImg());
				}
				//不小心把传入参数写成tempUser，此处不小心导致巨大bug
				addUserHeadImg(user, thumbnail);
			}
			//2.更新用户信息
			user.setLastEditTime(new Date());
			int effectedNum=userDao.updateUser(user);
			if (effectedNum<=0) {
				return new UserExecution(UserStateEnum.INNER_ERROR);
			}else {
				//更新成功后获取最新的user返回给前台
				user=userDao.queryUserById(user.getUserId());
				return new UserExecution(UserStateEnum.SUCCESS,user);
			}
			} catch (Exception e) {
				throw new UserOperationException("modifyUser error: "+e.getMessage());
			}
		}
	}
	
	/**
	 * 添加头像图片地址
	 * @param user
	 * @param thumbnail
	 */
	private void addUserHeadImg(User user, ImageHolder thumbnail) {
		//获取shop图片目录的相对值路径
		String dest=PathUtil.getUserImagePath(user.getUserId());
		String headImgAddr= ImageUtil.generateThumbnail(thumbnail,dest);
        user.setHeadImg(headImgAddr); 
	}


	@Override
	@Transactional
	public UserExecution getUserByUnAndPw(String userName, String password)
			throws UserOperationException {
		if (userName==null||password==null) {
			return new UserExecution(UserStateEnum.NULL_USER);
		}else {
			try {
				User user=userDao.queryUserByUmAndPw(userName, password);
				if (user.getUserId()<=0) {
					return new UserExecution(UserStateEnum.ERROR_PASSWORD);
				}else {
					return new UserExecution(UserStateEnum.SUCCESS,user);
				}
			} catch (Exception e) {
				throw new UserOperationException("getUserByUnAndPw error : "+e.toString());
			}
		}
	}


	@Override
	public User getUserById(long userId) {
		// TODO Auto-generated method stub
		return userDao.queryUserById(userId);
	}

	@Override
	@Transactional
	public UserExecution addUser(User user) throws UserOperationException {
		//新增用户不能判断user.getUserId()==null？,因为还没有Id
		if (user==null) {
			return new UserExecution(UserStateEnum.NULL_USER);
		}else {
			//1.判断图片是否需要处理
			try {
			//2.更新用户信息
			user.setHeadImg("../resources/images/user.jpg");
			user.setCreateTime(new Date());
			int effectedNum=userDao.inserUser(user);
			if (effectedNum<=0) {
				return new UserExecution(UserStateEnum.INNER_ERROR);
			}else {
				//更新成功后获取最新的user返回给前台
				user=userDao.queryUserById(user.getUserId());
				return new UserExecution(UserStateEnum.SUCCESS,user);
			}
			} catch (Exception e) {
				throw new UserOperationException("modifyUser error: "+e.getMessage());
			}
		}
	}

	/**
	 * 修改用户密码
	 */
	@Override
	@Transactional
	public UserExecution modifyUserPsw(Long userId, String password, String newPassword) {
		if (userId==null) {
			return new UserExecution(UserStateEnum.NULL_USERID);
		}else {
			User user=userDao.queryUserById(userId);
			if (user.getPassword().equals(password)) {
				User newuser=new User();
				newuser.setUserId(userId);
				newuser.setPassword(newPassword);
				newuser.setLastEditTime(new Date());
				int effectedNum=userDao.updateUser(newuser);
				if (effectedNum<=0) {
					return new UserExecution(UserStateEnum.INNER_ERROR);
				}else {
					//更新成功后获取最新的user返回给前台
					User newUserLast=userDao.queryUserById(newuser.getUserId());
					return new UserExecution(UserStateEnum.SUCCESS,newUserLast);
				}
			}else {
				return new UserExecution(UserStateEnum.ERROR_PASSWORD);
			}
		}
	}

	@Override
	public UserExecution getUserList(User userCondition) {
		if (userCondition==null) {
			return new UserExecution(UserStateEnum.NULL_USER);
		}else {
			List<User> users=userDao.queryUserList(userCondition);
			if (users.size()>0) {
				return new UserExecution(UserStateEnum.SUCCESS,users);
			}else {
				return new UserExecution(UserStateEnum.INNER_ERROR);
			}
		}
	}


}
