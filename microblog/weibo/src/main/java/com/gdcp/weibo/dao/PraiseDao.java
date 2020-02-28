package com.gdcp.weibo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdcp.weibo.entity.Praise;

public interface PraiseDao {

	/**
	 * 新增点赞
	 * @param praise
	 * @return
	 */
	int insertPraise(Praise praise);
	
	/**
	 * 通过输入条件查询点赞
	 * @param praise
	 * @return
	 */
	List<Praise> queryPraiseList(@Param("praiseCondition")Praise praiseCondition);
	
	/**
	 * 通过输入条件查询点赞总数
	 * @param praise
	 * @return
	 */
	int queryPraiseCount(@Param("praiseCondition")Praise praiseCondition);
	
	/**
	 * 更改点赞状态
	 * @param praise
	 * @return
	 */
	int updatePraise(@Param("praise")Praise praise);
	
	/**
	 * 删除点赞
	 * @param praise
	 * @return
	 */
	int deletePraise(@Param("praise")Praise praise);
}
