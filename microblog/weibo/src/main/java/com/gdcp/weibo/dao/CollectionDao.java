package com.gdcp.weibo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdcp.weibo.entity.Collection;

public interface CollectionDao {

	/**
	 * 新增收藏
	 * @param collection
	 * @return
	 */
	int insertCollection(Collection collection);
	
	/**
	 * 查询收藏列表
	 * @param collection
	 * @return
	 */
	List<Collection> queryCollectionList(@Param("collectionCondition")Collection collectionCondition);
	
	/**
	 * 查询收藏列表数量
	 * @param collection
	 * @return
	 */
	int queryCollectionCount(@Param("collectionCondition")Collection collectionCondition);
	
	/**
	 * 删除收藏
	 * @param collection
	 * @return
	 */
	int deleteCollection(@Param("collection")Collection collection);
}
