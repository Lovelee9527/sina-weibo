package com.gdcp.weibo.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class HttpServletRequestUtil {
	
	public static CommonsMultipartFile getCommonsMultipartFile(HttpServletRequest request,String key) {
		/*图片流获取与处理*/
		CommonsMultipartFile commonsMultipartFile=null;
		//获取本次会话的上下文（前端传来的文件流）
		@SuppressWarnings("unused")
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
				request.getSession().getServletContext());
			try {
				MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
				// 从文件流中获取key中的流,并强转成可以处理的文件流CommonsMultipartFile
				commonsMultipartFile=(CommonsMultipartFile) multipartHttpServletRequest.getFile(key);
				return commonsMultipartFile;
			} catch (Exception e) {
				return null;
			}
	}
	
	public static int getInt(HttpServletRequest request, String key) {
		try {
			return Integer.decode(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}

	public static long getLong(HttpServletRequest request, String key) {
		try {
			return Long.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1L;
		}
	}

	public static Double getDouble(HttpServletRequest request, String key) {
		try {
			return Double.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1d;
		}
	}

	public static boolean getBoolean(HttpServletRequest request, String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return false;
		}
	}

	public static String getString(HttpServletRequest request, String key) {
		try {
			String result = request.getParameter(key);
			if (result != null) {
				// 将result左右两边空格去掉
				result = result.trim();
			}
			if ("".equals(result)) {
				result = null;
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
