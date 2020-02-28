package com.gdcp.weibo.dto;

import java.util.List;

import com.gdcp.weibo.entity.Praise;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.PraiseStateEnum;

public class PraiseExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 用户数量
	private int count;

	// 操作的关系praise(增删改praise的时候用到)
	private Praise praise;

	// notifyList列表(查询notifyList列表的时候使用)
	private List<Praise> praiseList;
	
	//微博列表，查询赞我的微博时用到
	private List<Weibo> weiboList;

	public PraiseExecution() {

	}

	// 店铺操作失败的时候使用的构造器
	public PraiseExecution(PraiseStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 店铺操作成功的时候使用的构造器
	public PraiseExecution(PraiseStateEnum stateEnum, Praise praise) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.praise = praise;
	}

	// 店铺操作成功的时候使用的构造器 praiseList
	public PraiseExecution(PraiseStateEnum stateEnum, List<Praise> praiseList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.praiseList = praiseList;
	}

	// 店铺操作成功的时候使用的构造器 praiseList
	public PraiseExecution(PraiseStateEnum stateEnum, List<Praise> praiseList, int count) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.praiseList = praiseList;
		this.count = count;
	}
	
	// 店铺操作成功的时候使用的构造器 praiseList
	public PraiseExecution(PraiseStateEnum stateEnum, List<Praise> praiseList, int count,List<Weibo> weiboList) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.praiseList = praiseList;
			this.count = count;
			this.setWeiboList(weiboList);
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

	public Praise getPraise() {
		return praise;
	}

	public void setPraise(Praise praise) {
		this.praise = praise;
	}

	public List<Praise> getPraiseList() {
		return praiseList;
	}

	public void setPraiseList(List<Praise> praiseList) {
		this.praiseList = praiseList;
	}

	public List<Weibo> getWeiboList() {
		return weiboList;
	}

	public void setWeiboList(List<Weibo> weiboList) {
		this.weiboList = weiboList;
	}

}
