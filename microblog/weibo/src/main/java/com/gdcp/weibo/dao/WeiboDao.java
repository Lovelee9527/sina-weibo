package com.gdcp.weibo.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.gdcp.weibo.entity.Weibo;

public interface WeiboDao {
    int updateWeibo(@Param("weibo")Weibo weibo);
	
	/**
	 * 新增微博
	 * @param weibo
	 * @return
	 */
	int insertWeibo(Weibo weibo);
	
	/**
	 * 删除对应作者下的微博
	 * @param authorId
	 * @param weiboId
	 * @return
	 */
	int deleteWeiboById(@Param("authorId")long authorId,@Param("weiboId")long weiboId);
	
	/**
	 * 分页查询热门微博
	 * @param weiboCondition
	 * @param rowIndex
	 *      从第几行开始取数据
	 * @param pageSize
	 *      返回的行数
	 * @return
	 */
	List<Weibo> queryHotWeiboList(@Param("weiboCondition") Weibo weiboCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
	
	/**
	 * 分页查询微博，可输入的条件有:微博名(模糊)、作者、
	 * @param weiboCondition
	 * @param rowIndex
	 *      从第几行开始取数据
	 * @param pageSize
	 *      返回的行数
	 * @return
	 */
	List<Weibo> queryWeiboList(@Param("weiboCondition") Weibo weiboCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
	/**
	 * 同等条件下查询微博条数
	 * @param weiboCondition
	 * @return
	 */
	int queryWeiboCount(@Param("weiboCondition") Weibo weiboCondition);
	
	Weibo queryWeiboById(Long weiboId);
	
}
