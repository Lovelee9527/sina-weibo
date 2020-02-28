package com.gdcp.weibo.entity;

//微博图片
public class WeiboImg {
	// Id
	private Long weiboImgId;
	// 图片路径
	private String imgAddr;
	// 优先级
	private Integer priority;
	// 微博信息Id (外键)
	private Long weiboId;

	public Long getWeiboImgId() {
		return weiboImgId;
	}

	public void setWeiboImgId(Long weiboImgId) {
		this.weiboImgId = weiboImgId;
	}

	public String getImgAddr() {
		return imgAddr;
	}

	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Long getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(Long weiboId) {
		this.weiboId = weiboId;
	}

}
