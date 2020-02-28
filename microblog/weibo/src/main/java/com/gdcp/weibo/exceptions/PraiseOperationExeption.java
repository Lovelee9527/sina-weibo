package com.gdcp.weibo.exceptions;

public class PraiseOperationExeption extends RuntimeException{

	/**
	 * 可以抛出异常 并把与之相关操作事物的数据失效
	 */
	private static final long serialVersionUID = -8344521883176170920L;

	public PraiseOperationExeption(String msg) {
		super(msg);
	}
}
