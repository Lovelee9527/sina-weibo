package com.gdcp.weibo.dto;

import java.util.List;

import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.enums.CommentStateEnum;

public class CommentExecution {
	    // 结果状态
		private int state;

		// 状态标识
		private String stateInfo;

		// 评论数量
		private int count;

		// 操作的commet（增删改评论的时候用）
		private Comment comment;

		// 获取的comment列表(查询评论列表的时候用)
		private List<Comment> commentList;

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

		public Comment getComment() {
			return comment;
		}

		public void setComment(Comment comment) {
			this.comment = comment;
		}

		public List<Comment> getCommentList() {
			return commentList;
		}

		public void setCommentList(List<Comment> commentList) {
			this.commentList = commentList;
		}

		public CommentExecution() {
		}

		// 评论失败的构造器
		public CommentExecution(CommentStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}

		// 评论成功的构造器
		public CommentExecution(CommentStateEnum stateEnum,
				Comment comment) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.comment = comment;
		}

		// 评论成功的构造器
		public CommentExecution(CommentStateEnum stateEnum,
				List<Comment> commentList) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.commentList = commentList;
		}
		// 评论成功的构造器
		public CommentExecution(CommentStateEnum stateEnum,
				List<Comment> commentList,int count) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.commentList = commentList;
			this.count=count;
				}
		
}
