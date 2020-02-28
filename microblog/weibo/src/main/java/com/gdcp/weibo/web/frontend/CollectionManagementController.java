package com.gdcp.weibo.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdcp.weibo.dto.CollectionExecution;
import com.gdcp.weibo.dto.PraiseExecution;
import com.gdcp.weibo.entity.Collection;
import com.gdcp.weibo.entity.Praise;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.enums.CollectionStateEnum;
import com.gdcp.weibo.enums.PraiseStateEnum;
import com.gdcp.weibo.exceptions.CollectionOperationExeption;
import com.gdcp.weibo.exceptions.PraiseOperationExeption;
import com.gdcp.weibo.service.CollectionService;
import com.gdcp.weibo.service.PraiseService;
import com.gdcp.weibo.service.UserService;
import com.gdcp.weibo.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
@ResponseBody
public class CollectionManagementController {

	@Autowired
	private UserService userService;
	@Autowired
	private CollectionService collectionService;
	
	@RequestMapping(value="/checkcollection",method=RequestMethod.GET)
	public Map<String,Object> deleteCollection(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long weiboId=HttpServletRequestUtil.getLong(request, "weiboId");
		User user=getUserByRequest(request);
		if (user!=null&&user.getUserId()>0) {
			Collection collection=new Collection();
			collection.setUserId(user.getUserId());
			collection.setWeboId(weiboId);
			CollectionExecution ce=collectionService.checkCollection(collection);
			if (ce.getState()==PraiseStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg",ce.getStateInfo());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","你还未登录");
		}
	    return modelMap;	
	}
	
	@RequestMapping(value="/getcollectionlist",method=RequestMethod.GET)
	public Map<String,Object> getCollectionList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long weiboId=HttpServletRequestUtil.getLong(request, "weiboId");
		long userId=HttpServletRequestUtil.getLong(request, "userId");
		User user=new User();
		if (userId>0) {
			user=userService.getUserById(userId);
			if (user==null||user.getUserId()<0) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "无效userId");
				return modelMap;
			}
		}else {
			user=(User)request.getSession().getAttribute("currentUser");
		}
		
		if (user!=null&&user.getUserId()>0) {
			try {
				Collection collectionCondition=new Collection();
				collectionCondition.setUserId(user.getUserId());
				if (weiboId>0) {
					collectionCondition.setWeboId(weiboId);
				}
				CollectionExecution ce=collectionService.getCollectionList(collectionCondition);
				if (ce.getState()==PraiseStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("weiboList", ce.getWeiboList());
					modelMap.put("collectionList", ce.getCollectionList());
					modelMap.put("count", ce.getCount());
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ce.getStateInfo());
				}
			} catch (CollectionOperationExeption e) {
				modelMap.put("errMsg", e.getMessage());
				modelMap.put("success", false);
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "你还未登录");
		}
		return modelMap;
	}
	
	private User getUserByRequest(HttpServletRequest request) {
		long userId=HttpServletRequestUtil.getLong(request, "userId");
		User user=new User();
		if (userId>0) {
			user=userService.getUserById(userId);
		}else {
			user=(User)request.getSession().getAttribute("currentUser");
		}
		return user;
	}
}
