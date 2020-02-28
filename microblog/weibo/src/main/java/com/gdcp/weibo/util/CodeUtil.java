package com.gdcp.weibo.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
	public static boolean checkVerigyCode(HttpServletRequest request) {
		// 网页图片上的验证码
		String verigyCodeExpected = (String) request.getSession()
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		// 网页传回来的参数(所填的验证码)
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		//填入的验证码为空或与与之不相同时返回false
		if (verifyCodeActual == null || !verifyCodeActual.equalsIgnoreCase(verigyCodeExpected)) {
			return false;
		}
		return true;
	}
}
