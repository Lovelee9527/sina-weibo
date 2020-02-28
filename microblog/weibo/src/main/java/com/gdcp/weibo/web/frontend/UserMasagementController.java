package com.gdcp.weibo.web.frontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.soap.MTOMFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdcp.weibo.dao.UserDao;
import com.gdcp.weibo.dao.WeiboDao;
import com.gdcp.weibo.dto.ImageHolder;
import com.gdcp.weibo.dto.UserExecution;
import com.gdcp.weibo.dto.UserRelationExecution;
import com.gdcp.weibo.dto.WeiboExecution;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.UserRelation;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.UserRelationStateEnum;
import com.gdcp.weibo.enums.UserStateEnum;
import com.gdcp.weibo.enums.WeiboStateEnum;
import com.gdcp.weibo.exceptions.UserOperationException;
import com.gdcp.weibo.exceptions.UserRelationOperationException;
import com.gdcp.weibo.service.UserRelationService;
import com.gdcp.weibo.service.UserService;
import com.gdcp.weibo.service.WeiboService;
import com.gdcp.weibo.util.CodeUtil;
import com.gdcp.weibo.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
@ResponseBody
public class UserMasagementController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private WeiboService weiboService;
	@Autowired
	private UserRelationService userRelationService;

	@RequestMapping(value = "/getuserdatacompleteness", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserDataCompleteness(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User user=new User();
		Long userId = HttpServletRequestUtil.getLong(request, "userId");
		Long toUserId=HttpServletRequestUtil.getLong(request, "toUserId");
		if (toUserId > 0) {
			user=userService.getUserById(toUserId);
		} else {
			if(userId>0) {
				user=userService.getUserById(userId);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg","无效userId");
			}
		}
		if (user != null&&user.getUserId()>0) {
				int completeness = 100;
				if (user.getHeadImg()==null||"".equals(user.getHeadImg().trim())) {
					completeness -= 10;
				}
				if (user.getNickName()==null||"".equals(user.getNickName().trim())) {
					completeness -= 10;
				}
				if (user.getProvince()==null||"".equals(user.getProvince().trim())) {
					completeness -= 10;
				}
				if (user.getCity()==null||"".equals(user.getCity().trim())) {
					completeness -= 10;
				}
				if (user.getProfile()==null||"".equals(user.getProfile().trim())) {
					completeness -= 10;
				}
				if (user.getSecretAnswer()==null||"".equals(user.getSecretAnswer())) {
					completeness -= 10;
				}
				if (user.getSex()==null||"".equals(user.getSex())) {
					completeness -= 10;
				}
				modelMap.put("success", true);
				modelMap.put("completeness",completeness);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "无效参数");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getuserbynickname", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserByNickName(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String nickName = HttpServletRequestUtil.getString(request, "nickName");
		Long userId = HttpServletRequestUtil.getLong(request, "userId");
		if (userId > 0) {
			User user = userService.getUserById(userId);
			if (user != null && user.getUserId() > 0) {
				modelMap.put("success", true);
				modelMap.put("user", user);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "系统内部出错");
			}
		} else if (nickName.trim() != null || !nickName.trim().equals(' ')) {
			User user = userDao.queryUserByNickName(nickName);
			if (user != null && user.getUserId() > 0) {
				modelMap.put("success", true);
				modelMap.put("user", user);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "系统内部出错");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "无效参数");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getnicknamelist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNickNameList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("currentUser");
		if (user == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "您还未登录");
			return modelMap;
		}
		try {
			List<User> userList = userService.getUserList();
			List<String> nickNameList = new ArrayList<String>();
			if (userList != null && userList.size() > 0) {
				for (User tempUser : userList) {
					if (tempUser.getNickName() != null && !user.getNickName().equals(tempUser.getNickName())) {
						nickNameList.add(tempUser.getNickName());
					}
				}
				modelMap.put("success", true);
				modelMap.put("nickNameList", nickNameList);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "当前没有其他用户");
			}
		} catch (UserOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyuserpassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyUserPassword(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long userId = HttpServletRequestUtil.getLong(request, "userId");
		String password = HttpServletRequestUtil.getString(request, "password");
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		if (userId != null && password != null && newPassword != null) {
			try {
				UserExecution ue = userService.modifyUserPsw(userId, password, newPassword);
				if (ue.getState() == UserStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else if (ue.getState() == UserStateEnum.ERROR_PASSWORD.getState()) {
					modelMap.put("success", false);
					modelMap.put("errMsg", ue.getStateInfo());
				}
			} catch (UserOperationException e) {
				modelMap.put("errMsg", e.getMessage());
				modelMap.put("success", false);
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户信息为空");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getuserinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long userId = HttpServletRequestUtil.getLong(request, "userId");
		if (userId != null) {
			User user = userService.getUserById(userId);
			modelMap.put("user", user);
			modelMap.put("success", true);
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyuser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyUser(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String userStr = HttpServletRequestUtil.getString(request, "userStr");
		User user = new User();

		ObjectMapper mapper = new ObjectMapper();
		try {
			user = mapper.readValue(userStr, User.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		if (user.getUserId() != null && user != null) {
			try {
				ImageHolder thumbnail = new ImageHolder(null, null);
				UserExecution ue = userService.modifyUser(user, thumbnail);
				if (ue.getState() == UserStateEnum.SUCCESS.getState()) {
					modelMap.put("user", ue.getUser());
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ue.getStateInfo());
				}
			} catch (UserOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyuserheadimg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyUserHeadImg(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("currentUser");
		/* 头像获取与处理 */
		CommonsMultipartFile headImg = null;
		// 获取本次会话的上下文（前端传来的文件流）
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			// 从文件流中获取"headImg"中的流,并强转成可以处理的文件流CommonsMultipartFile
			headImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("headImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "更改的头像不能为空");
			return modelMap;
		}
		if (headImg != null && user.getUserId() != null && user != null) {
			// int effectedNum=userService.motifyUser()
			try {
				ImageHolder thumbnail = new ImageHolder(headImg.getOriginalFilename(), headImg.getInputStream());
				UserExecution ue = userService.modifyUser(user, thumbnail);
				if (ue.getState() == UserStateEnum.SUCCESS.getState()) {
					modelMap.put("headImg", ue.getUser().getHeadImg());
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ue.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		return modelMap;
	}

	@RequestMapping(value = "/getmineinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMineInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User user = new User();
		Long userId = HttpServletRequestUtil.getLong(request, "userId");
		Long toUserId = HttpServletRequestUtil.getLong(request, "toUserId");
		if (toUserId > 0) {
			user = userService.getUserById(toUserId);
		} else {
			if (userId > 0) {
				user = userService.getUserById(userId);
				request.getSession().setAttribute("currentUser", user);
			} else {
				user = (User) request.getSession().getAttribute("currentUser");
			}
		}
		try {
			if (user.getUserId() != null && user != null) {
				modelMap.put("user", user);
				modelMap.put("success", true);
				Weibo weiboCondition = new Weibo();
				weiboCondition.setAuthor(user);
				WeiboExecution we = weiboService.getWeiboList(weiboCondition, 1, 1);
				if (we.getState() == WeiboStateEnum.SUCCESS.getState()) {
					modelMap.put("weiboCount", we.getCount());
				}
				UserRelation relationCondition1 = new UserRelation();
				relationCondition1.setStars(user);
				UserRelationExecution ure1 = userRelationService.getUserRelationList(relationCondition1);
				if (ure1.getState() == UserRelationStateEnum.SUCCESS.getState()) {
					modelMap.put("fansCount", ure1.getCount());
				}
				UserRelation relationCondition2 = new UserRelation();
				relationCondition2.setFans(user);
				UserRelationExecution ure2 = userRelationService.getUserRelationList(relationCondition2);
				if (ure2.getState() == UserRelationStateEnum.SUCCESS.getState()) {
					modelMap.put("starsCount", ure2.getCount());
				}
				return modelMap;
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户信息为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
	}

	@RequestMapping(value = "/loginout", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loginOut(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		request.getSession().setAttribute("currentUser", null);
		User crrentUser = (User) request.getSession().getAttribute("currentUser");
		if (crrentUser == null) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
		}
		return modelMap;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		User user = null;
		if (userName != null && !" ".equals(userName) && password != null && !" ".equals(password)) {
			try {
				UserExecution ue = userService.getUserByUnAndPw(userName, password);
				if (ue.getState() == UserStateEnum.SUCCESS.getState()) {
					request.getSession().setAttribute("currentUser", ue.getUser());
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ue.getStateInfo());
					return modelMap;
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("essMsg", "该用户为空");
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("essMsg", "用户名或密码为空");
			return modelMap;
		}
		return modelMap;
	}

	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerUser(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerigyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		String userStr = HttpServletRequestUtil.getString(request, "userStr");
		User user = new User();

		ObjectMapper mapper = new ObjectMapper();
		try {
			user = mapper.readValue(userStr, User.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// 判断唯一的用户名是否已存在
		boolean flag = false;
		if (user.getUserName() != null) {
			List<User> userList = userService.getUserList();
			for (User tempUser : userList) {
				if (user.getUserName() != null && user.getUserName().equals(tempUser.getUserName())) {
					flag = true;
				}
			}
		}
		if (flag) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "该用户名已存在");
			return modelMap;
		}
		// 2.注册用户 返回结果
		if (user != null && !user.getUserName().equals("") && !user.getPassword().equals("")) {
			// 从Session中获取user
			UserExecution ue;
			try {
				// 获取随机9位数,给新注册用户随机用户昵称
				Random r = new Random();
				int rannum = r.nextInt(899999999) + 100000000;
				user.setNickName("用户" + rannum);
				ue = userService.addUser(user);
				if (ue.getState() == UserStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ue.getStateInfo());
				}
			} catch (UserOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入用户信息");
			return modelMap;
		}
	}
}
