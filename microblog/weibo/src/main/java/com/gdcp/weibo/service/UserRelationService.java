package com.gdcp.weibo.service;

import com.gdcp.weibo.dto.UserRelationExecution;
import com.gdcp.weibo.entity.UserRelation;
import com.gdcp.weibo.exceptions.UserRelationOperationException;

public interface UserRelationService {

	/**
	 * 查询用户粉丝或明星列表
	 * 
	 * @param relationCondition
	 * @return
	 * @throws UserRelationOperationException
	 */
	UserRelationExecution getUserRelationList(UserRelation relationCondition) throws UserRelationOperationException;

	/**
	 * 查询用户关系
	 * @param userId
	 * @param starsId
	 * @return
	 */
	UserRelationExecution getUserRelation(long userId, long starsId);

	/**
	 * 取消关注
	 * 
	 * @param userId
	 * @param starsId
	 * @return
	 */
	UserRelationExecution cancelUserRelation(long userId, long starsId);

	/**
	 * 添加关注
	 * 
	 * @param userRelation
	 * @return
	 */
	UserRelationExecution addUserRelation(UserRelation userRelation);
}
