package com.gdcp.weibo.service;

import com.gdcp.weibo.dto.CollectionExecution;
import com.gdcp.weibo.entity.Collection;

public interface CollectionService {

	/**
	 * 添加或删除微博收藏
	 * @param collection
	 * @return
	 */
	CollectionExecution checkCollection(Collection collection);
	
	
	/**
	 * 查询的微博的收藏列表
	 * @param collectionCondition
	 * @return
	 */
	CollectionExecution getCollectionList(Collection collectionCondition);
}
