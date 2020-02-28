package com.gdcp.weibo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdcp.weibo.dto.ImageHolder;
import com.gdcp.weibo.dto.WeiboExecution;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.exceptions.WeiboOperationException;

public interface WeiboService {
	WeiboExecution updateWeibo(Weibo weibo);
	
	/**
	 * 分页查询热门微博列表
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	WeiboExecution getHotWeiboList(int pageIndex,int pageSize);
	
	/**
	 * 删除相应用户Id下的相应微博
	 * @param authorId
	 * @param weiboId
	 * @return
	 */
	WeiboExecution deleteWeiboById(long authorId,long weiboId);
	
	/**
	 * 分页查询 按作者 发表时间最新的优先
	 * @param weiboCondition
	 * @return
	 */
	WeiboExecution getWeiboList(Weibo weiboCondition,int pageIndex,int pageSize);
	
	/**
	 * 新增微博以及图片处理
	 * @param weibo
	 * @param weiboImgHolderList
	 * @return
	 * @throws WeiboOperationException
	 */
	WeiboExecution addWeibo(Weibo weibo,List<ImageHolder> weiboImgHolderList)
	      throws WeiboOperationException;
	
	Weibo getWeiboByWeiboId(Long weiboId);
	
	/**
	 * 查询所以@我的微博
	 * @param targetId
	 * @return
	 */
	WeiboExecution getAtMyWeibosByTargetId(long targetId);
}
