package com.gdcp.weibo.entity;

import java.util.Date;
import java.util.List;

//微博信息
public class Weibo {
	// Id
	private Long weiboId;
	// 作者Id (外键)
	private User author;
	// 微博内容
	private String content;
	// 微博图片
	private List<WeiboImg> weiboImgList;
	// 发表时间
	private Date publishTime;
	// 评论数 默认为零
	private Integer commentCount;
	// 点赞数 默认为零
	private Integer praiseCount;
	// 转发数 默认为零
	private Integer fowardCount;

	public Long getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(Long weiboId) {
		this.weiboId = weiboId;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<WeiboImg> getWeiboImgList() {
		return weiboImgList;
	}

	public void setWeiboImgList(List<WeiboImg> weiboImgList) {
		this.weiboImgList = weiboImgList;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public Integer getFowardCount() {
		return fowardCount;
	}

	public void setFowardCount(Integer fowardCount) {
		this.fowardCount = fowardCount;
	}

}
