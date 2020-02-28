package com.gdcp.weibo.dao;

import java.util.List;

import com.gdcp.weibo.entity.WeiboImg;

public interface WeiboImgDao {

	/**
	 * 通过微博Id查询微博图片
	 * @param weiboId
	 * @return
	 */
	List<WeiboImg> queryByWeiboId(long weiboId);
	
	/**
	 * 删除该微博下的图片
	 * @param weiboId
	 * @return
	 */
	int deleteImgByWeiboId(long weiboId);
	
	/**
	 * 查询同等条件下的微博图片数
	 * @param weiboId
	 * @return
	 */
	int queryCountByWeiboId(long weiboId);
	/**
	 * 批量添加微博图片
	 * @param weibo
	 * @return
	 */
	int batchInsertWeiboImg(List<WeiboImg> weiboImgList);
}
