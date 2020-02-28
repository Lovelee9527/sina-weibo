package com.gdcp.weibo.service;

import com.gdcp.weibo.dto.NotifyExecution;
import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.entity.Praise;

public interface NotifyService {

	 NotifyExecution getNotifysByTargetId(long targetId);
	/**
	 * 根据targetId与action修改isRead为1
	 * @param targetId
	 * @param action
	 * @return
	 */
	NotifyExecution modifyNotifyIsRead(long targetId,int action);
	
	/**
	 * 添加用户的通知
	 * @param notify
	 * @return
	 */
	NotifyExecution addNotify(Notify notify);
	
	/**
	 * 删除用户的通知
	 * @param notify
	 * @return
	 */
	NotifyExecution deleteNotify(Notify notify);
	
	/**
	 * 添加@用户的通知
	 * @param atNickName
	 * @param notify
	 * @return
	 */
	NotifyExecution addNotifyOfWeiboAt(String atNickName,Notify notify);
	
	/**
	 * 通过输入notifyCondition条件查询通知列表与总数
	 * @param notifyCondition
	 * @return
	 */
	NotifyExecution getNotifyList(Notify notifyCondition);

	/**
	 * 新增点赞通知消息
	 * @param praise
	 */
	NotifyExecution addNotifyOfPraise(Praise praise);
	NotifyExecution modifyNotifyState(Praise praise);

}
