package com.gdcp.weibo.entity;

import java.util.Date;

//通知消息表
public class Notify {
	// 通知Id
	private Long notifyId;
	// 消息内容 公告和私信类型的消息使用
	private String content;
	// 通知类型 1、提醒 2、公告  3、私信
	private Integer type;
	// 目标id（如post的id）
	private Long targetId;
	//  1、艾特@ 2、评论 3、回复 4、关注 5、点赞
	private Integer action;
	// 发送者id
	private Long senderId;
	// 发送者类型 1普通用户 2管理员
	private Integer senderType;
	// 消息所属者，如post动态发布者
	private Long userId;
	// 消息是否已读 0未读 1已读
	private Integer isRead;
	// 创建时间
	private Date createTime;
	// 发送者昵称
	private String nickName;
	// 发送者头像
	private String headImg;
	// 评论id 评论的时候存储
	private Long commentId;
	// 评论回复id 回复的时候存储
	private Long commentReplyId;
	// 微博id @艾特的时候存储
	private Long weiboId;
	//该通知的状态(0无效通知,1有效通知)
	private Integer state;
 
	public Long getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Long notifyId) {
		this.notifyId = notifyId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Integer getSenderType() {
		return senderType;
	}

	public void setSenderType(Integer senderType) {
		this.senderType = senderType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(Long weiboId) {
		this.weiboId = weiboId;
	}

	public Long getCommentReplyId() {
		return commentReplyId;
	}

	public void setCommentReplyId(Long commentReplyId) {
		this.commentReplyId = commentReplyId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
