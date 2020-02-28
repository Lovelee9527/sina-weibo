package com.gdcp.weibo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdcp.weibo.entity.UserRelation;

public interface UserRelationDao {
	
	/**
	 * 查询用户的关系列表
	 * @param relationContion
	 * @return
	 */
	List<UserRelation> queryUserRelationList(@Param("relationCondition")UserRelation relationCondition);
	
	
	/**
	 * 与上同等条件下的查询数量
	 * @param relationCondition
	 * @return
	 */
	int queryUserRelationCount(@Param("relationCondition")UserRelation relationCondition);
	
	/**
	 * 查询用户的关系
	 * @param userRelation
	 * @return
	 */
	UserRelation queryUserRelation(@Param("userRelation")UserRelation userRelation);
	
	/**
	 * 当两者已不存在任何关系时，则删除该行关系
	 * @param userRelation
	 * @return
	 */
	int deleteUserRelationById(@Param("fansId")long fansId,@Param("starsId")long starsId);
	
	
	/**
	 * 当两者已经存在时，更新state,改为state=1
	 * @param userRelation
	 * @return
	 */
	int updateUserRelation(@Param("userRelation")UserRelation userRelation);
	
	/**
	 * 新增关注
	 * @param userRelation
	 * @return
	 */
	int insertUserRelation(UserRelation userRelation);
}
