package com.gdcp.weibo.service;

import com.gdcp.weibo.dto.PraiseExecution;
import com.gdcp.weibo.entity.Praise;

public interface PraiseService {

	/**
	 * 添加微博点赞
	 * @param praise
	 * @return
	 */
	PraiseExecution addPraiseOfWeibo(Praise praise);
	
	PraiseExecution deletePraise(Praise praise);
	
	/**
	 * 查询条件下的微博的点赞与总数
	 * @param praiseCondition
	 * @return
	 */
	PraiseExecution getPraiseStateAndCountOfWeibo(Praise praiseCondition);
	
	/**
	 * 查询的微博的点赞列表
	 * @param praiseCondition
	 * @return
	 */
	PraiseExecution getPraiseList(Praise praiseCondition);
	
	/**
	 * 查询我赞的微博
	 * @param userId
	 * @return
	 */
	PraiseExecution getPraiseListByMe(long userId);
	
	/**
	 * 查询条件下的微博的点赞列表
	 * @param praiseCondition
	 * @return
	 *//*
	PraiseExecution getPraiseListOfWeibo(long authorId);*/
}
