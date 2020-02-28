package com.gdcp.weibo.web.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdcp.weibo.dao.CommentDao;
import com.gdcp.weibo.dao.WeiboDao;
import com.gdcp.weibo.dto.CommentExecution;
import com.gdcp.weibo.dto.NotifyExecution;
import com.gdcp.weibo.dto.ReplyExecution;
import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.entity.Reply;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.CommentStateEnum;
import com.gdcp.weibo.enums.NotifyStateEnum;
import com.gdcp.weibo.enums.ReplyStateEnum;
import com.gdcp.weibo.exceptions.CommentOperationException;
import com.gdcp.weibo.exceptions.ReplyOperationException;
import com.gdcp.weibo.exceptions.WeiboOperationException;
import com.gdcp.weibo.service.CommentService;
import com.gdcp.weibo.service.NotifyService;
import com.gdcp.weibo.service.ReplyService;
import com.gdcp.weibo.service.UserService;
import com.gdcp.weibo.service.WeiboService;
import com.gdcp.weibo.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
@ResponseBody
public class CommentManagementController {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private UserService userService;
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private WeiboService weiboService;

	@RequestMapping(value = "/getcommentandreplycount", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCommentAndReplyCount(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long weiboId = HttpServletRequestUtil.getLong(request, "weiboId");
		Long commentId = HttpServletRequestUtil.getLong(request, "commentId");
		if (weiboId>0&&commentId<=0) {
			try {
				int count=0;
				int replyCount=0;
				CommentExecution ce = commentService.getCommentListByWeiboId(weiboId);
				if (ce.getState() == CommentStateEnum.SUCCESS.getState()) {
					for(Comment comment:ce.getCommentList()) {
						ReplyExecution re = replyService.getReplyListByCommentId(comment.getCommentId());
						if (re.getState()==ReplyStateEnum.SUCCESS.getState()) {
							replyCount +=re.getReplyList().size();
						}
						count =ce.getCommentList().size()+replyCount;
					}
					modelMap.put("count",count);
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ce.getStateInfo());
				}
			} catch (CommentOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else if (weiboId<=0&&commentId>0) {
			try {
				ReplyExecution re = replyService.getReplyListByCommentId(commentId);
				if (re.getState() == CommentStateEnum.SUCCESS.getState()) {
					modelMap.put("count", re.getReplyList().size());
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", re.getStateInfo());
				}
			} catch (ReplyOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getcommentinfoonmine", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCommentInfoOnMine(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User user = new User();
		Long userId = HttpServletRequestUtil.getLong(request, "userId");
		Long weiboId = HttpServletRequestUtil.getLong(request, "weiboId");
		List<Comment> commentList = new ArrayList<Comment>();
		int commentcount = 0;
		if (userId > 0) {
			user = userService.getUserById(userId);
			request.getSession().setAttribute("currentUser", user);
			modelMap.put("user", user);
		} else {
			user = (User) request.getSession().getAttribute("currentUser");
			modelMap.put("user", user);
			modelMap.put("weiboId",weiboId);
		}
		if (user.getUserId() != null && user != null) {
			modelMap.put("user", user);
			Comment comment = new Comment();
			// 在我的主页显示本用户的评论
			comment.setUser(user);
			try {
				CommentExecution ce = commentService.getCommentListByWeiboId(weiboId);
				if (ce.getState() == CommentStateEnum.SUCCESS.getState()) {
					if (ce.getCommentList().size() > 0) {
						commentList.addAll(ce.getCommentList());
						commentcount += ce.getCommentList().size();
						modelMap.put("commentList", commentList);
						modelMap.put("commentCount", commentcount);
						modelMap.put("weiboId", weiboId);
						modelMap.put("success", true);
					}else {
						modelMap.put("success", false);
						modelMap.put("errMsg","当前微博没有评论");
					}
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ce.getStateInfo());
				}
			} catch (CommentOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "微博为空");
		}
		return modelMap;
	}

	/**
	 * 添加评论
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addcomment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addComment(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 接收前端参数的变量初始化
		String commentStr = HttpServletRequestUtil.getString(request,"commentStr");
		Comment comment = null;
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		long userId=HttpServletRequestUtil.getLong(request,"userId");
		User user = new User();
		ObjectMapper mapper = new ObjectMapper();
		try {
			comment = mapper.readValue(commentStr, Comment.class);
			if (userId>0) {
				user.setUserId(userId);
			}else{
				user.setUserId(currentUser.getUserId());
			}
			comment.setUser(user);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		if (comment.getContent() == null && "".equals(comment.getContent())) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "评论内容不能为空");
			return modelMap;
		}
		if (comment != null && comment.getUser().getUserId() != null) {
			try {
				//执行添加评论操作
				CommentExecution ce = commentService.addComment(comment);
				if (ce.getState() == CommentStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);					
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ce.getStateInfo());
				}
			} catch (CommentOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "评论不能为空");
			return modelMap;
		}
		return modelMap;
	}
	
	/**
	 * 删除评论
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deletecomment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteComment(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		// 接收前端参数的变量初始化
		long commentId = HttpServletRequestUtil.getLong(request, "commentId");
		Comment comment = new Comment();
		comment.setUser(currentUser);
		try {
			if (currentUser.getUserId()>0&&commentId > 0) {
				CommentExecution ce = commentService.deleteCommentById(currentUser.getUserId(), commentId);
				if (ce.getState() == CommentStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);				
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", CommentStateEnum.ERROR);
				}
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}

	/**
	 * 根据评论Id获取评论内容
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getcommentcontent", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCommentContent(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long commentId = HttpServletRequestUtil.getLong(request, "commentId");
		// 通过评论Id获取评论内容
		if (commentId > 0) {
			try {
				Comment comment = commentService.getCommentByCommentId(commentId);
				if (comment != null) {
					modelMap.put("content", comment.getContent());
					modelMap.put("success", true);
				} else {
					modelMap.put("errMsg", "内部系统出错");
					modelMap.put("success", false);
				}
			} catch (CommentOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "评论Id无效");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getreplyinfoonmine", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getReplyInfoOnMine(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User fromUser = (User) request.getSession().getAttribute("currentUser");
		Long fromId = HttpServletRequestUtil.getLong(request, "fromId");
		/*User toUser = (User) request.getSession().getAttribute("currentUser2");
		Long toId = HttpServletRequestUtil.getLong(request, "toId");*/
		Long commentId = HttpServletRequestUtil.getLong(request, "commentId");
		if (fromUser.getUserId() != null && fromUser != null) {
			modelMap.put("fromUser", fromUser);
			try {
				ReplyExecution re = replyService.getReplyListByCommentId(commentId);
				if (re.getState() == CommentStateEnum.SUCCESS.getState()) {
					modelMap.put("replyList", re.getReplyList());
					modelMap.put("count", re.getCount());
					modelMap.put("commentId", commentId);
					modelMap.put("success", true);
					return modelMap;
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", re.getStateInfo());
					return modelMap;
				}
			} catch (ReplyOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		} else if (fromId > 0) {
			User currentfromUser = userService.getUserById(fromId);
			request.getSession().setAttribute("currentUser", currentfromUser);
			modelMap.put("user", currentfromUser);
			modelMap.put("success", true);
			return modelMap;
		}
		modelMap.put("success", false);
		modelMap.put("errMsg", "回复评论为空");
		return modelMap;
	}
	/**
	 * 添加回复
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addreply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addReply(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 接收前端参数的变量初始化
		String replyStr = HttpServletRequestUtil.getString(request, "replyStr");
		Reply reply = null;
		User fromUser = (User) request.getSession().getAttribute("currentUser");
		ObjectMapper mapper = new ObjectMapper();
		try {
			reply = mapper.readValue(replyStr, Reply.class);
//				reply.setFromId(fromUser.getUserId());	
			fromUser.setUserId(fromUser.getUserId());
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		if (reply.getContent() == null && "".equals(reply.getContent())) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "回复评论内容不能为空");
			return modelMap;
		}
		if (reply != null && fromUser.getUserId() != null) {
			try {
				ReplyExecution re = replyService.addReply(reply, fromUser);
				if (re.getState() == ReplyStateEnum.SUCCESS.getState()) {
					modelMap.put("fromNickName", fromUser.getNickName());
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", re.getStateInfo());
				}
			} catch (ReplyOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "回复评论不能为空");
			return modelMap;
		}
		return modelMap;
	}
	
	/**
	 * 删除回复
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deletereply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteReply(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//当前登录的用户
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		// 接收前端参数的变量初始化
		long replyId = HttpServletRequestUtil.getLong(request, "replyId");
		Reply reply = new Reply();
		reply.setFromUser(currentUser);
		try {
			if (currentUser.getUserId()>0 && replyId > 0) {
				ReplyExecution re = replyService.deleteReplyById(currentUser.getUserId(), replyId);
				if (re.getState() == CommentStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);				
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ReplyStateEnum.ERROR);
				}
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
}