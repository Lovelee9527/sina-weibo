package com.gdcp.weibo.exceptions;

public class UserRelationOperationException extends RuntimeException{
	/**
	 * 可以抛出异常 并把与之相关操作事物的数据失效
	 */
	private static final long serialVersionUID = -1613730264887802413L;

	public UserRelationOperationException(String msg) {
		super(msg);
	}
}
