package com.gdcp.weibo.exceptions;

//用户活动抛出的异常（RuntimeException类型）
public class UserOperationException extends RuntimeException{
	
	/**
	 * 可以抛出异常 并把与之相关操作事物的数据失效
	 */
	private static final long serialVersionUID = 5648080270538598234L;

	public UserOperationException(String msg) {
		super(msg);
	}
}

