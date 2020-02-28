package com.gdcp.weibo.dto;

import java.util.List;

import com.gdcp.weibo.entity.Reply;
import com.gdcp.weibo.enums.ReplyStateEnum;

public class ReplyExecution {
	        // 结果状态
			private int state;

			// 状态标识
			private String stateInfo;

			// 回复评论数量
			private int count;

			// 操作的reply（增删改回复评论的时候用）
			private Reply reply;

			// 获取的reply列表(查询回复评论列表的时候用)
			private List<Reply> replyList;

			public int getState() {
				return state;
			}

			public void setState(int state) {
				this.state = state;
			}

			public String getStateInfo() {
				return stateInfo;
			}

			public void setStateInfo(String stateInfo) {
				this.stateInfo = stateInfo;
			}

			public int getCount() {
				return count;
			}

			public void setCount(int count) {
				this.count = count;
			}

			public Reply getReply() {
				return reply;
			}

			public void setReply(Reply reply) {
				this.reply = reply;
			}

			public List<Reply> getReplyList() {
				return replyList;
			}

			public void setReplyList(List<Reply> replyList) {
				this.replyList = replyList;
			}
			
			// 回复评论失败的构造器
			public ReplyExecution(ReplyStateEnum stateEnum) {
				this.state = stateEnum.getState();
				this.stateInfo = stateEnum.getStateInfo();
			}

			// 回复评论成功的构造器
			public ReplyExecution(ReplyStateEnum stateEnum,
					Reply reply) {
				this.state = stateEnum.getState();
				this.stateInfo = stateEnum.getStateInfo();
				this.reply = reply;
			}

			// 回复评论成功的构造器
			public ReplyExecution(ReplyStateEnum stateEnum,List<Reply> replyList) {
				this.state = stateEnum.getState();
				this.stateInfo = stateEnum.getStateInfo();
				this.replyList = replyList;
			}
			
			// 回复评论成功的构造器
			public ReplyExecution(ReplyStateEnum stateEnum,
								List<Reply> replyList,int count) {
							this.state = stateEnum.getState();
							this.stateInfo = stateEnum.getStateInfo();
							this.replyList = replyList;
							this.count=count;
			}
			
}
