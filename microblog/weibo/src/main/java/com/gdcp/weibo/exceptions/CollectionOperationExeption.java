package com.gdcp.weibo.exceptions;

public class CollectionOperationExeption extends RuntimeException{

	/**
	 * 可以抛出异常 并把与之相关操作事物的数据失效
	 */
	private static final long serialVersionUID = 1260432742369869569L;

	public CollectionOperationExeption(String msg) {
		super(msg);
	}
}
