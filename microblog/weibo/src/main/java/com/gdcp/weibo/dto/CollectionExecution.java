package com.gdcp.weibo.dto;

import java.util.List;

import com.gdcp.weibo.entity.Collection;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.CollectionStateEnum;

public class CollectionExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 用户数量
	private int count;

	// 操作的关系collection(增删改collection的时候用到)
	private Collection collection;

	// notifyList列表(查询notifyList列表的时候使用)
	private List<Collection> collectionList;
	
	//微博列表，查询赞我的微博时用到
	private List<Weibo> weiboList;

	public CollectionExecution() {

	}

	// 店铺操作失败的时候使用的构造器
	public CollectionExecution(CollectionStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 店铺操作成功的时候使用的构造器
	public CollectionExecution(CollectionStateEnum stateEnum, Collection collection) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.collection = collection;
	}

	// 店铺操作成功的时候使用的构造器 collectionList
	public CollectionExecution(CollectionStateEnum stateEnum, List<Collection> collectionList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.collectionList = collectionList;
	}

	// 店铺操作成功的时候使用的构造器 collectionList
	public CollectionExecution(CollectionStateEnum stateEnum, List<Collection> collectionList, int count) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.collectionList = collectionList;
		this.count = count;
	}
	
	// 店铺操作成功的时候使用的构造器 collectionList
	public CollectionExecution(CollectionStateEnum stateEnum, List<Collection> collectionList, int count,List<Weibo> weiboList) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.collectionList = collectionList;
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

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public List<Collection> getCollectionList() {
		return collectionList;
	}

	public void setCollectionList(List<Collection> collectionList) {
		this.collectionList = collectionList;
	}

	public List<Weibo> getWeiboList() {
		return weiboList;
	}

	public void setWeiboList(List<Weibo> weiboList) {
		this.weiboList = weiboList;
	}



}
