package com.gdcp.weibo.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.service.UserService;

@Controller
@RequestMapping("/testuser")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/getuserlist",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserList(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		List<User> userList= userService.getUserList();
		if (userList.size()<=0) {
			modelMap.put("success", false);
			return modelMap;
		}
		modelMap.put("userList", userList);
		modelMap.put("success", true);
		return modelMap;
	}
}
