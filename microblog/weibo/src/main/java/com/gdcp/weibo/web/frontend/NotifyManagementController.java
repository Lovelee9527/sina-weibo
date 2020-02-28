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

import com.gdcp.weibo.dao.CommentDao;
import com.gdcp.weibo.dao.ReplyDao;
import com.gdcp.weibo.dto.CommentExecution;
import com.gdcp.weibo.dto.NotifyExecution;
import com.gdcp.weibo.dto.PraiseExecution;
import com.gdcp.weibo.dto.ReplyExecution;
import com.gdcp.weibo.dto.UserRelationExecution;
import com.gdcp.weibo.dto.WeiboExecution;
import com.gdcp.weibo.entity.Comment;
import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.entity.Reply;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.UserRelation;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.CommentStateEnum;
import com.gdcp.weibo.enums.NotifyStateEnum;
import com.gdcp.weibo.enums.PraiseStateEnum;
import com.gdcp.weibo.enums.ReplyStateEnum;
import com.gdcp.weibo.enums.UserRelationStateEnum;
import com.gdcp.weibo.enums.WeiboStateEnum;
import com.gdcp.weibo.exceptions.CommentOperationException;
import com.gdcp.weibo.exceptions.NotifyOperationExeption;
import com.gdcp.weibo.exceptions.PraiseOperationExeption;
import com.gdcp.weibo.exceptions.ReplyOperationException;
import com.gdcp.weibo.exceptions.UserRelationOperationException;
import com.gdcp.weibo.exceptions.WeiboOperationException;
import com.gdcp.weibo.service.CommentService;
import com.gdcp.weibo.service.NotifyService;
import com.gdcp.weibo.service.PraiseService;
import com.gdcp.weibo.service.ReplyService;
import com.gdcp.weibo.service.UserRelationService;
import com.gdcp.weibo.service.UserService;
import com.gdcp.weibo.service.WeiboService;
import com.gdcp.weibo.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
@ResponseBody
public class NotifyManagementController {
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private WeiboService weiboService;
	@Autowired
	private PraiseService praiseService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRelationService userRelationService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private ReplyDao replyDao;
	@Autowired
	private ReplyService replyService;

	@RequestMapping(value = "/getmynotifyinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMyNotifyInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long userId = HttpServletRequestUtil.getLong(request, "userId");
		int action = HttpServletRequestUtil.getInt(request, "action");
		if (userId > 0 && action > 0) {
			if (action == 1) {
				try {
					WeiboExecution we = weiboService.getAtMyWeibosByTargetId(userId);
					if (we.getState() == WeiboStateEnum.SUCCESS.getState()) {
						modelMap.put("success", true);
						modelMap.put("weiboList", we.getWeiboList());
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", we.getStateInfo());
					}
				} catch (WeiboOperationException e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
					return modelMap;
				}
			} else if (action == 5) {
				try {
					Notify notifyCondition=new Notify();
					notifyCondition.setAction(action);
					notifyCondition.setTargetId(userId);
					NotifyExecution ne = notifyService.getNotifyList(notifyCondition);
					if (ne.getState() == PraiseStateEnum.SUCCESS.getState()) {
						User user=userService.getUserById(userId);
						modelMap.put("success", true);
						modelMap.put("notifyList", ne.getNotifyList());
						modelMap.put("user",user);
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", ne.getStateInfo());
					}
				} catch (PraiseOperationExeption e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
					return modelMap;
				}
			}else if (action==4) {
				try {
					UserRelation relationCondition=new UserRelation();
					User stars=new User();
					stars.setUserId(userId);
					relationCondition.setStars(stars);
					UserRelationExecution ure=userRelationService.getUserRelationList(relationCondition);
					if (ure.getState() == UserRelationStateEnum.SUCCESS.getState()) {
						modelMap.put("success", true);
						modelMap.put("relationList", ure.getUserRelationList());
						modelMap.put("count", ure.getCount());
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", ure.getStateInfo());
					}
				} catch (UserRelationOperationException e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
					return modelMap;
				}
			}else if(action == 2){
				try {
					//如果Comment.user.userId = Weibo.author.userId
					CommentExecution ce=commentService.getCommentListByUserId(userId);				
					if (ce.getState()==CommentStateEnum.SUCCESS.getState()) {
						if (ce.getCommentList().size()>0) {
						modelMap.put("success", true);
						modelMap.put("commentList", ce.getCommentList());
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
			}else if (action == 3) {
				try {
					//根据toId获取回复我的列表
					ReplyExecution re=replyService.getReplyListByToId(userId);
					if (re.getState()==ReplyStateEnum.SUCCESS.getState()&&re.getReplyList().size()>0) {
						modelMap.put("success", true);
						modelMap.put("replyList", re.getReplyList());
					}else {
						modelMap.put("success", false);
						modelMap.put("errMsg", "获取回复我的列表失败");
					}
				} catch (ReplyOperationException e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
					return modelMap;
				}
			}else if (action == 6) {
				try {
					//如果Comment.user.userId = User.userId
					List<Comment> commentList1 = commentDao.queryCommentListByUserId(userId);
					for (Comment comment:commentList1) {
						Weibo weibo=weiboService.getWeiboByWeiboId(comment.getWeibo().getWeiboId());
						comment.setWeibo(weibo);
					}
					if (commentList1.size()>0) {
						modelMap.put("success", true);
						modelMap.put("commentList1", commentList1);
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", "获取评论他人列表失败");
					}
				} catch (CommentOperationException e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
					return modelMap;
				}				
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "参数无效");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifynotifyisread", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> modifyNotifyIsRead(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("currentUser");
		int action = HttpServletRequestUtil.getInt(request, "action");
		if (user != null && user.getUserId() > 0 && action > 0) {
			try {
				long targetId = user.getUserId();
				NotifyExecution ne = notifyService.modifyNotifyIsRead(targetId, action);
				if (ne.getState() == NotifyStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ne.getStateInfo());
				}
			} catch (NotifyOperationExeption e) {
				modelMap.put("errMsg", e.getMessage());
				modelMap.put("success", false);
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户未登录");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getnotifysbytargetid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNotifysByTargetId(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("currentUser");
		try {
			if (user != null && user.getUserId() > 0) {
				modelMap.put("success", true);
				Notify notifyCondition = new Notify();
				notifyCondition.setTargetId(user.getUserId());
				notifyCondition.setIsRead(0);
				notifyCondition.setAction(1);
				NotifyExecution neAt = notifyService.getNotifyList(notifyCondition);
				modelMap.put("atCount", neAt.getCount());

				notifyCondition.setAction(2);
				NotifyExecution neCm = notifyService.getNotifyList(notifyCondition);
				modelMap.put("commentCount", neCm.getCount());

				notifyCondition.setAction(3);
				NotifyExecution neRe = notifyService.getNotifyList(notifyCondition);
				modelMap.put("replyCount", neRe.getCount());

				notifyCondition.setAction(4);
				NotifyExecution neRl = notifyService.getNotifyList(notifyCondition);
				modelMap.put("relationCount", neRl.getCount());

				notifyCondition.setAction(5);
				NotifyExecution nePr = notifyService.getNotifyList(notifyCondition);
				modelMap.put("praiseCount", nePr.getCount());
				notifyCondition.setAction(null);
				NotifyExecution ne = notifyService.getNotifyList(notifyCondition);
				modelMap.put("notifyCount", ne.getCount());
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "还未登录");
			}
		} catch (NotifyOperationExeption e) {
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
}
