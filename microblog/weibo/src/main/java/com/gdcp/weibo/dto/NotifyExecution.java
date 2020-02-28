package com.gdcp.weibo.dto;

import java.util.List;

import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.enums.NotifyStateEnum;

public class NotifyExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 用户数量
	private int count;

	// 操作的关系notify(增删改notify的时候用到)
	private Notify notify;

	// notifyList列表(查询notifyList列表的时候使用)
	private List<Notify> notifyList;

	public NotifyExecution() {

	}

	// 店铺操作失败的时候使用的构造器
	public NotifyExecution(NotifyStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 店铺操作成功的时候使用的构造器
	public NotifyExecution(NotifyStateEnum stateEnum, Notify notify) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.notify = notify;
	}

	// 店铺操作成功的时候使用的构造器 ShopList
	public NotifyExecution(NotifyStateEnum stateEnum, List<Notify> notifyList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.notifyList = notifyList;
	}
	// 店铺操作成功的时候使用的构造器 ShopList
	public NotifyExecution(NotifyStateEnum stateEnum, List<Notify> notifyList,int count) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.notifyList = notifyList;
			this.count =count;
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

	public Notify getNotify() {
		return notify;
	}

	public void setNotify(Notify notify) {
		this.notify = notify;
	}

	public List<Notify> getNotifyList() {
		return notifyList;
	}

	public void setNotifyList(List<Notify> notifyList) {
		this.notifyList = notifyList;
	}

}
