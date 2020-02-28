package com.gdcp.weibo.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdcp.weibo.dao.UserDao;
import com.gdcp.weibo.dto.NotifyExecution;
import com.gdcp.weibo.dto.UserRelationExecution;
import com.gdcp.weibo.dto.WeiboExecution;
import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.UserRelation;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.NotifyStateEnum;
import com.gdcp.weibo.enums.UserRelationStateEnum;
import com.gdcp.weibo.enums.WeiboStateEnum;
import com.gdcp.weibo.exceptions.UserOperationException;
import com.gdcp.weibo.exceptions.UserRelationOperationException;
import com.gdcp.weibo.exceptions.WeiboOperationException;
import com.gdcp.weibo.service.NotifyService;
import com.gdcp.weibo.service.UserRelationService;
import com.gdcp.weibo.service.UserService;
import com.gdcp.weibo.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
@ResponseBody
public class UserRelationController {

	@Autowired
	private UserRelationService userRelationService;
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/getuserrelationlist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserRelationList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User user=new User();
		int state=HttpServletRequestUtil.getInt(request, "state");
		Long userId = HttpServletRequestUtil.getLong(request, "userId");
		Long toUserId=HttpServletRequestUtil.getLong(request, "toUserId");
		if (toUserId > 0) {
			user.setUserId(toUserId);
		} else {
			if(userId>0) {
				user.setUserId(userId);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg","无效userId");
			}
		}
		if (user != null&&user.getUserId()>0) {

			// 在我的主页显示本用户所关注的用户(明星)
			try {
				UserRelation relationCondition=new UserRelation();
				if (state==0) {
					relationCondition.setFans(user);
				}else {
					relationCondition.setStars(user);
				}
				UserRelationExecution ure=userRelationService.getUserRelationList(relationCondition);
				if (ure.getState() == WeiboStateEnum.SUCCESS.getState()) {
					if (ure.getCount() > 0) {
						modelMap.put("userRelationCount", ure.getCount());
						modelMap.put("userRelationList", ure.getUserRelationList());
						modelMap.put("success", true);
					}
				}
			} catch (WeiboOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			// 前面都成功才会到这
			// 调用自定义好的ListSortUtil工具类，对获得的微博列表按发布时间降序排序
//			List<Weibo> sortWeiboList = ListSortUtil.ListSort(weiboList);
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请登录呢");
			return modelMap;
		}
	}
	
	@RequestMapping(value = "/getuserrelation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getUserRelation(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long starsId=HttpServletRequestUtil.getLong(request, "starsId");
		long userId=HttpServletRequestUtil.getLong(request, "userId");
		User user=new User();
		if (userId>0) {
			user=userService.getUserById(userId);
			if (user==null) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "无效的userId");
				return modelMap;
			}
		}else {
			user=(User)request.getSession().getAttribute("currentUser");
		}
		try {
			if (user!=null&&user.getUserId()==starsId) {
				modelMap.put("success", true);
				modelMap.put("state",2);
				return modelMap;
			}else if (user!=null&&starsId>0&&user.getUserId()>0) {
				UserRelationExecution ure=userRelationService.getUserRelation(user.getUserId(), starsId);
				if (ure.getState()==3) {
					modelMap.put("success", true);
					modelMap.put("state",0);
				}else if (ure.getState()==4) {
					modelMap.put("success", true);
					modelMap.put("state",-1);
				}else if (ure.getState()==5) {
					modelMap.put("success", true);
					modelMap.put("state",1);
				}else {
					modelMap.put("success", true);
					modelMap.put("state", null);
					return modelMap;
				}
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg","传入参数为空");
				return modelMap;
			}
		} catch (UserRelationOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/canceluserrelation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelUserRelation(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long starsId=HttpServletRequestUtil.getLong(request, "starsId");
		long fansId=HttpServletRequestUtil.getLong(request, "fansId");
		User user=new User();
		long userId=HttpServletRequestUtil.getLong(request, "userId");
		if (userId>0) {
			user=userService.getUserById(userId);
		}else {
			user=(User) request.getSession().getAttribute("currentUser");
		}
		try {
			long tempUserId=0;
			long tempStarsId=0;
			if (starsId>0&&user.getUserId()>0&&fansId<=0) {
				tempUserId=user.getUserId();
			    tempStarsId=starsId;
			}else if (fansId>0&&user.getUserId()>0&&starsId<=0) {
				tempUserId=fansId;
			    tempStarsId=user.getUserId();
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "参数无效");
				return modelMap;
			}
			UserRelationExecution ure=userRelationService.cancelUserRelation(tempUserId, tempStarsId);
			if (ure.getState()==UserRelationStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
				//若关注的对象不为空,则需删除通知表通知对应目标用户
					Notify notify=new Notify();
					notify.setSenderId(tempUserId);
					notify.setAction(4);
					notify.setTargetId(tempStarsId);
					NotifyExecution ne=notifyService.deleteNotify(notify);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", UserRelationStateEnum.stateInfoOf(ure.getState()));
			}
		} catch (UserOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/adduserrelation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addUserRelation(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long starsId=HttpServletRequestUtil.getLong(request, "starsId");
		User user=new User();
		Long userId=HttpServletRequestUtil.getLong(request, "userId");
		if (userId>0) {
			user=userService.getUserById(userId);
		}else {
			user=(User) request.getSession().getAttribute("currentUser");
		}
		try {
			if (user!=null&&starsId>0&&user.getUserId()>0) {
				UserRelation userRelation=new UserRelation();
				User stars=new User();
				userRelation.setFans(user);
				stars.setUserId(starsId);
				userRelation.setStars(stars);
				UserRelationExecution ure=userRelationService.addUserRelation(userRelation);
				if (ure.getState()==UserRelationStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					//若关注的对象不为空,则需插入通知表通知对应目标用户
					if (starsId>0) {
						Notify notify=new Notify();
						notify.setSenderId(user.getUserId());
						notify.setAction(4);
						notify.setTargetId(starsId);
						notify.setNickName(user.getNickName());
						notify.setHeadImg(user.getHeadImg());
						NotifyExecution ne=notifyService.addNotify(notify);
						if (ne.getState()==NotifyStateEnum.SUCCESS.getState()) {
							modelMap.put("success", true);
						}else {
							modelMap.put("success", false);
							modelMap.put("errMsg", ne.getStateInfo());
						}
					}
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", UserRelationStateEnum.stateInfoOf(ure.getState()));
				}
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "传入参数无效");
			}
		} catch (UserOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
}
