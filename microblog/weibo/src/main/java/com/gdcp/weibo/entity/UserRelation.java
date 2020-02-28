package com.gdcp.weibo.entity;

import java.util.Date;

//用户联系实体类
public class UserRelation {
	// 联系Id
	private Long userRelationId;
	// 需要加关注的人 (外键)
	private User fans;
	// 被加关注的人 (外键)
	private User stars;
	// 关系状态 (-1逆向关注,0 顺向关注,2 互相关注)
	private Integer state;
	//创建时间
	private Date createTime;
	//修改时间
	private Date lastEditTime;

	
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

	public Long getUserRelationId() {
		return userRelationId;
	}

	public void setUserRelationId(Long userRelationId) {
		this.userRelationId = userRelationId;
	}

	public User getFans() {
		return fans;
	}

	public void setFans(User fans) {
		this.fans = fans;
	}

	public User getStars() {
		return stars;
	}

	public void setStars(User stars) {
		this.stars = stars;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
