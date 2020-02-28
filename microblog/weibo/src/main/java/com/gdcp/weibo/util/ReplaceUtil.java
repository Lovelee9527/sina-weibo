package com.gdcp.weibo.util;

public class ReplaceUtil {

	public static String stringReplaceAll(String str) {
		String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
		String temp= "";
		String newString = str.replaceAll(regEx,temp);//不想保留原来的字
		String targetStr=newString.trim();//去除空格
		return targetStr;
	}
	
	public static String atReplaceAll(String str) {
		String regEx="[@]";
		String temp= "";
		String newString = str.replaceAll(regEx,temp);//不想保留原来的字
		String targetStr=newString.trim();//去除空格
		return targetStr;
	}
	
}
