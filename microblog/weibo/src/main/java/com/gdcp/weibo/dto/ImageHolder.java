package com.gdcp.weibo.dto;

import java.io.InputStream;

/**
 * 包装图片流与图片名
 * @author 刚刚好
 *
 */
public class ImageHolder {

	private String imageName;
	private InputStream image;
	
	public ImageHolder(String imageName,InputStream image) {
		this.image=image;
		this.imageName=imageName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}
	
}
