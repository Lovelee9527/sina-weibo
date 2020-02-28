package com.gdcp.weibo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdcp.weibo.entity.Notify;

public interface NotifyDao {

	int insertNotify(Notify notify);
	
	int updateNotify(@Param("notify")Notify notify);
	
	int deleteNotify(@Param("notify")Notify notify);
	
	/**
	 * 通过目标用户Id查询通知消息列表
	 * @param targetId
	 * @return
	 */
	List<Notify> queryNotifysByTargetId(long targetId);
	
	/**
	 * 通过输入notify的条件查询通知列表
	 * @param notifyCondition
	 * @return
	 */
	List<Notify> queryNotifyList(@Param("notifyCondition")Notify notifyCondition);
	
	/**
	 * 通过输入notify的条件查询通知列表总数
	 * @param notifyCondition
	 * @return
	 */
	int queryNotifyCount(@Param("notifyCondition")Notify notifyCondition);
}
