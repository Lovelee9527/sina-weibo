package com.gdcp.weibo.dto;

import java.util.List;

import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.WeiboStateEnum;

public class WeiboExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 用户数量
	private int count;

	// 操作的Weibo(增删改Weibo的时候用到)
	private Weibo weibo;

	// Weibo列表(查询Weibo列表的时候使用)
	private List<Weibo> weiboList;

	public WeiboExecution() {

	}

	// 微博操作失败的时候使用的构造器
	public WeiboExecution(WeiboStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 微博操作成功的时候使用的构造器
	public WeiboExecution(WeiboStateEnum stateEnum, Weibo weibo) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.weibo = weibo;
	}

	// 微博操作成功的时候使用的构造器 WeiboList
	public WeiboExecution(WeiboStateEnum stateEnum, List<Weibo> weiboList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.weiboList = weiboList;
	}
	
	// 微博操作成功的时候使用的构造器 WeiboList
		public WeiboExecution(WeiboStateEnum stateEnum, List<Weibo> weiboList,int count) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.weiboList = weiboList;
			this.count=count;
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

	public Weibo getWeibo() {
		return weibo;
	}

	public void setWeibo(Weibo weibo) {
		this.weibo = weibo;
	}

	public List<Weibo> getWeiboList() {
		return weiboList;
	}

	public void setWeiboList(List<Weibo> weiboList) {
		this.weiboList = weiboList;
	}

}
