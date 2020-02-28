package com.gdcp.weibo.dto;

import java.util.List;

import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.enums.UserStateEnum;

public class UserExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 用户数量
	private int count;

	// 操作的关系user(增删改user的时候用到)
	private User user;

	// user列表(查询user列表的时候使用)
	private List<User> userList;

	public UserExecution() {

	}

	// 店铺操作失败的时候使用的构造器
	public UserExecution(UserStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 店铺操作成功的时候使用的构造器
	public UserExecution(UserStateEnum stateEnum, User user) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.user = user;
	}

	// 店铺操作成功的时候使用的构造器 ShopList
	public UserExecution(UserStateEnum stateEnum, List<User> userList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userList = userList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
