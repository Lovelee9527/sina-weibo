package com.gdcp.weibo.util;

import org.slf4j.Logger;

/**
 * 
 * @author 刚刚好
 * @return basePath + imagePath
 */
public class PathUtil {
	//获取系统路径分隔符
	private static String separator = System.getProperty("file.separator");

	/**
	 * 返回项目图片的根路径
	 * @return basePath
	 */
	public static String getImgBasePath() {
		//获取系统名字（类别）
		String os = System.getProperty("os.name");
		String basePath = "";
        //判断是什么系统，存放到指定路径
		if (os.toLowerCase().startsWith("win")) {
			basePath = "E:/microblog/images";
		} else {
			basePath = "/home/ayou/microblog/images";
		}
		//获取到的分隔符separator全部替换成"/",以保证路径有效
		basePath = basePath.replace("/", separator);
		return basePath;
	}
	
	/**
	 * 返回项目用户头像的子路径
	 * @return imagePath
	 */
	public static String getUserImagePath(long userId) {
		String imagePath= "/upload/user/headimg/" + userId+"/";
		//获取到的分隔符separator全部替换成"/",以保证路径有效
		String img=imagePath.replace("/", separator);
		return imagePath.replace("/", separator);
	}
	
	/**
	 * 返回项目微博图片的子路径
	 * @return imagePath
	 */
	public static String getWeiboImagePath(long userId,long weiboId) {
		String imagePath= "/upload/weibo/author"+userId+"/"+ weiboId+"/";
		//获取到的分隔符separator全部替换成"/",以保证路径有效
		String img=imagePath.replace("/", separator);
		return imagePath.replace("/", separator);
	}
}
