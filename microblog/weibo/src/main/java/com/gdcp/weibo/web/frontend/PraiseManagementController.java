package com.gdcp.weibo.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdcp.weibo.dto.PraiseExecution;
import com.gdcp.weibo.entity.Praise;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.enums.PraiseStateEnum;
import com.gdcp.weibo.exceptions.PraiseOperationExeption;
import com.gdcp.weibo.service.PraiseService;
import com.gdcp.weibo.service.UserService;
import com.gdcp.weibo.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
@ResponseBody
public class PraiseManagementController {

	@Autowired
	private PraiseService praiseService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/getpraiselistbyme",method=RequestMethod.GET)
	public Map<String,Object> getPraiseListByMe(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User user=(User)request.getSession().getAttribute("currentUser");
//		User user=new User();
//		user.setUserId(23L);
		if (user!=null&&user.getUserId()>0) {
			PraiseExecution pe=praiseService.getPraiseListByMe(user.getUserId());
			if (pe.getState()==PraiseStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
				modelMap.put("praiseList",pe.getPraiseList());
				modelMap.put("count", pe.getCount());
				modelMap.put("weiboList", pe.getWeiboList());
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg",pe.getStateInfo());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","你还未登录");
		}
	    return modelMap;	
	}
	
	@RequestMapping(value="/getpraiseList",method=RequestMethod.GET)
	public Map<String,Object> getPraiseList(HttpServletRequest request) {
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
		
		if (weiboId>0&&user!=null&&user.getUserId()>0) {
			try {
				Praise praiseCondition=new Praise();
				praiseCondition.setWeiboId(weiboId);
				User tempUser=new User();
				tempUser.setUserId(user.getUserId());
				praiseCondition.setUser(tempUser);
				PraiseExecution pe=praiseService.getPraiseStateAndCountOfWeibo(praiseCondition);
				if (pe.getState()==PraiseStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					if (pe.getPraise()!=null&&pe.getPraise().getState()!=null) {
						modelMap.put("state", pe.getPraise().getState());
					}else {
						modelMap.put("state", 0);
					}
					modelMap.put("count", pe.getCount());
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (PraiseOperationExeption e) {
				modelMap.put("errMsg", e.getMessage());
				modelMap.put("success", false);
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "还未登录");
		}
		return modelMap;
	}
	
	@RequestMapping(value="/addpraiseofweibo",method=RequestMethod.GET)
	public Map<String,Object> addPraiseOfWeibo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long weiboId=HttpServletRequestUtil.getLong(request, "weiboId");
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
		if (weiboId>0&&user!=null&&user.getUserId()>0) {
			try {
				Praise praise=new Praise();
				praise.setWeiboId(weiboId);
				User tempUser=new User();
				tempUser.setUserId(user.getUserId());
				praise.setUser(tempUser);
				PraiseExecution pe=praiseService.addPraiseOfWeibo(praise);
				if (pe.getState()==PraiseStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					modelMap.put("state", pe.getPraise().getState());
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (PraiseOperationExeption e) {
				modelMap.put("errMsg", e.getMessage());
				modelMap.put("success", false);
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "还未登录");
		}
		return modelMap;
	}
}
