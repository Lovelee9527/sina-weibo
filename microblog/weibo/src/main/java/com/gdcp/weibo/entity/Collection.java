package com.gdcp.weibo.entity;

import java.util.Date;

//收藏
public class Collection {
	// Id
	private Long collectionId;
	// 收藏此信息的用户 (外键)
	private long userId;
	// 点赞状态 (0 取消收藏或没收藏,1 收藏)
	private Integer state;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date lastEditTime;
	// 收藏微博
	private Long weiboId;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public Long getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(Long weiboId) {
		this.weiboId = weiboId;
	}

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public Long getWeboId() {
		return weiboId;
	}

	public void setWeboId(Long weiboId) {
		this.weiboId = weiboId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
