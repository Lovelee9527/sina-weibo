package com.gdcp.weibo.entity;

import java.util.Date;

//点赞
public class Praise {
	// Id
	private Long praiseId;
	// 点赞此信息的用户 (外键)
	private User user;
	// 点赞状态 (-1已删除,0 取消点赞或没点赞,1 点赞)
	private Integer state;
	// 点赞微博
	private Long weiboId;
	// 创建时间
	private Date createTime;
	// 修改时间
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

	public Long getPraiseId() {
		return praiseId;
	}

	public void setPraiseId(Long praiseId) {
		this.praiseId = praiseId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(Long weiboId) {
		this.weiboId = weiboId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	/*public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getCommentReplyId() {
		return commentReplyId;
	}

	public void setCommentReplyId(Long commentReplyId) {
		this.commentReplyId = commentReplyId;
	}*/

}
