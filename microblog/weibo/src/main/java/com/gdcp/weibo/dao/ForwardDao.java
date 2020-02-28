package com.gdcp.weibo.dao;

import com.gdcp.weibo.entity.Forward;

public interface ForwardDao {

	/**
	 * 新增转发
	 * @param forward
	 * @return
	 */
	int insertForward(Forward forward);
}
