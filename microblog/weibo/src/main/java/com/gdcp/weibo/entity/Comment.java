package com.gdcp.weibo.entity;

import java.util.Date;

//评论
public class Comment {
	// Id
	private Long commentId;
	// 对应的微博id (外键)
	private Weibo weibo;
	// 评论内容
	private String content;
	// 评论时间
	private Date time;
	// 评论用户 (外键)
	private User user;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Weibo getWeibo() {
		return weibo;
	}

	public void setWeibo(Weibo weibo) {
		this.weibo = weibo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
