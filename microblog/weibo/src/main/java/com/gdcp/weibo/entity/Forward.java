package com.gdcp.weibo.entity;

import java.util.Date;

//转发
public class Forward {
	// Id
	private Long forwardId;
	// 转发人
	private User user;
	// 转发的微博
	private Weibo weibo;
	// 转发感言
	private String forwardMsg;
	//转发时间
	private Date time;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getForwardId() {
		return forwardId;
	}

	public void setForwardId(Long forwardId) {
		this.forwardId = forwardId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Weibo getWeibo() {
		return weibo;
	}

	public void setWeibo(Weibo weibo) {
		this.weibo = weibo;
	}

	public String getForwardMsg() {
		return forwardMsg;
	}

	public void setForwardMsg(String forwardMsg) {
		this.forwardMsg = forwardMsg;
	}

}
