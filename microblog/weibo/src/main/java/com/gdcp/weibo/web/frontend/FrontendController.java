package com.gdcp.weibo.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/frontend",method=RequestMethod.GET)
public class FrontendController {
	@RequestMapping(value = "/index")
	private String index() {
		return "frontend/index";
	}
	
	@RequestMapping(value = "/hot")
	private String hot() {
		return "frontend/hot";
	}
	
	@RequestMapping(value = "/register",method=RequestMethod.POST)
	private String showRegister() {
		return "frontend/register";
	}
	
	@RequestMapping(value = "/changeuser")
	private String showChangeUser() {
		return "frontend/changeuser";
	}
	
	@RequestMapping(value = "/mine")
	private String showMine() {
		return "frontend/mine";
	}
	
	@RequestMapping(value = "/test")
	private String showTest() {
		return "frontend/test";
	}
	
	@RequestMapping(value = "/mynotify")
	private String showMyNotify() {
		return "frontend/mynotify";
	}
	
	@RequestMapping(value = "/head")
	private String showHead() {
		return "frontend/head";
	}
	
	@RequestMapping(value = "/homepage")
	private String showHomePage() {
		return "frontend/homepage";
	}
	
	@RequestMapping(value = "/weibodetail")
	private String showWeiboDetail() {
		return "frontend/weibodetail";
	}

}
