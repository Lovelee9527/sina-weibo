package com.gdcp.weibo.dto;

import java.util.List;

import com.gdcp.weibo.entity.UserRelation;
import com.gdcp.weibo.enums.UserRelationStateEnum;

public class UserRelationExecution{
//结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 用户数量
	private int count;

	// 操作的关系userRelation(增删改userRelation的时候用到)
	private UserRelation userRelation;

	// user列表(查询user列表的时候使用)
	private List<UserRelation> userRelationList;

	public UserRelationExecution() {

	}

	// 店铺操作失败的时候使用的构造器
	public UserRelationExecution(UserRelationStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 店铺操作成功的时候使用的构造器
	public UserRelationExecution(UserRelationStateEnum stateEnum, UserRelation userRelation) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userRelation = userRelation;
	}

	// 店铺操作成功的时候使用的构造器 ShopList
	public UserRelationExecution(UserRelationStateEnum stateEnum, List<UserRelation> userRelationList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userRelationList = userRelationList;
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

	public UserRelation getUserRelation() {
		return userRelation;
	}

	public void setUserRelation(UserRelation userRelation) {
		this.userRelation = userRelation;
	}

	public List<UserRelation> getUserRelationList() {
		return userRelationList;
	}

	public void setUserRelationList(List<UserRelation> userRelationList) {
		this.userRelationList = userRelationList;
	}
}
