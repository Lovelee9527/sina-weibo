package com.gdcp.weibo.entity;

import java.util.Date;

public class Reply {
	//评论回复Id
	private Long replyId;
	
	//评论主表Id(外键)
	private Comment comment;
	
	//评论者Id(外键)
	private User fromUser;
	
	//评论者昵称
//	private String fromNickName;
	
	//评论者头像
//	private String fromHeadImg;
	
	//被评论者Id(外键)
	private Long toId;
	
	//被评论者昵称
	private String toNickName;
	
	//被评论者头像
	private String toHeadImg;
	
	//评论内容
	private String content;
	
	private Date createTime;

	public Long getReplyId() {
		return replyId;
	}

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public String getToNickName() {
		return toNickName;
	}

	public void setToNickName(String toNickName) {
		this.toNickName = toNickName;
	}

	public String getToHeadImg() {
		return toHeadImg;
	}

	public void setToHeadImg(String toHeadImg) {
		this.toHeadImg = toHeadImg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
