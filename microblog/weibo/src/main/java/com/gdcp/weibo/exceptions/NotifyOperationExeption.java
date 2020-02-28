package com.gdcp.weibo.exceptions;

public class NotifyOperationExeption extends RuntimeException{

	/**
	 * 可以抛出异常 并把与之相关操作事物的数据失效
	 */
	private static final long serialVersionUID = 3089300016830581950L;

	public NotifyOperationExeption(String msg) {
		super(msg);
	}
}
