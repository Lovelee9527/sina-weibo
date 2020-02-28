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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdcp.weibo.dao.UserDao;
import com.gdcp.weibo.dto.ImageHolder;
import com.gdcp.weibo.dto.NotifyExecution;
import com.gdcp.weibo.dto.UserExecution;
import com.gdcp.weibo.dto.UserRelationExecution;
import com.gdcp.weibo.dto.WeiboExecution;
import com.gdcp.weibo.entity.Notify;
import com.gdcp.weibo.entity.User;
import com.gdcp.weibo.entity.UserRelation;
import com.gdcp.weibo.entity.Weibo;
import com.gdcp.weibo.enums.NotifyStateEnum;
import com.gdcp.weibo.enums.UserRelationStateEnum;
import com.gdcp.weibo.enums.UserStateEnum;
import com.gdcp.weibo.enums.WeiboStateEnum;
import com.gdcp.weibo.exceptions.UserOperationException;
import com.gdcp.weibo.exceptions.UserRelationOperationException;
import com.gdcp.weibo.exceptions.WeiboOperationException;
import com.gdcp.weibo.service.NotifyService;
import com.gdcp.weibo.service.UserRelationService;
import com.gdcp.weibo.service.UserService;
import com.gdcp.weibo.service.WeiboService;
import com.gdcp.weibo.util.HttpServletRequestUtil;
import com.gdcp.weibo.util.ListSortUtil;
import com.gdcp.weibo.util.ReplaceUtil;
import com.mysql.jdbc.jdbc2.optional.MysqlPooledConnection;

@Controller
@RequestMapping("/frontend")
@ResponseBody
public class WeiboManagementController {
	@Autowired
	private WeiboService weiboService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRelationService userRelationService;
	@Autowired
	private NotifyService notifyService;

	@RequestMapping(value = "/getnewweibolist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNewWeiboList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		User user=(User)request.getSession().getAttribute("currentUser");
		if (user==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请登录");
			return modelMap;
		}
		try {
//			List<Weibo> weiboList=new ArrayList<>();
			WeiboExecution we=weiboService.getWeiboList(new Weibo(), pageIndex, pageSize);
			if (we.getState()==WeiboStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
				modelMap.put("newWeiboList",we.getWeiboList());
				modelMap.put("weiboCount",we.getCount());
			}else {
				modelMap.put("success", false);
			}
		} catch (WeiboOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/gethotweibolist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getHotWeiboList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		User user=(User)request.getSession().getAttribute("currentUser");
		if (user==null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请登录");
			return modelMap;
		}
		try {
//			List<Weibo> weiboList=new ArrayList<>();
			WeiboExecution we=weiboService.getHotWeiboList(pageIndex,pageSize);
			if (we.getState()==WeiboStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
				modelMap.put("hotWeiboList",we.getWeiboList());
				modelMap.put("hotWCount",we.getWeiboList().size());
				modelMap.put("weiboCount",we.getCount());
			}else {
				modelMap.put("success", false);
			}
		} catch (WeiboOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getweiboofweibodetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getWeiboOfWeiboDetail(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long userId = HttpServletRequestUtil.getLong(request, "userId");
		Long weiboId = HttpServletRequestUtil.getLong(request, "weiboId");
		if (userId>0) {
			try {
			User user=userService.getUserById(userId);
			if (user!=null) {
				modelMap.put("user", user);
			}
			if (weiboId>0) {
				Weibo weibo=weiboService.getWeiboByWeiboId(weiboId);
				if (weibo!=null) {
					modelMap.put("success", true);
					modelMap.put("weibo", weibo);
				}
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "不存在该微博或已删除");
			}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "你还未登录，请先登录");
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getsearchcontentbykey", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSearchContentByKey(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		String searchKey = HttpServletRequestUtil.getString(request, "searchKey");
		System.out.println("关键字为：" + searchKey);

		List<String> searchWeibos = new ArrayList<>();
		List<String> searchNickNames = new ArrayList<>();

		int weiboCount = 0;
		int nickNameCount = 0;
		try {
			if (searchKey != null && !searchKey.trim().equals("")) {
				// 查询微博内容含有输入的内容的微博列表
				Weibo weiboCondition = new Weibo();
				weiboCondition.setContent(searchKey);
				WeiboExecution we = weiboService.getWeiboList(weiboCondition, 1, 7);
				if (we.getState() == WeiboStateEnum.SUCCESS.getState() && we.getCount() > 0) {
					for (Weibo weibo : we.getWeiboList()) {
						// 判断字符串长度，超出则只传递规定长度
						if (weibo.getContent().length() >= 20) {
							searchWeibos.add(weibo.getContent().substring(0, 20));
						} else {
							searchWeibos.add(weibo.getContent());
						}
					}
					weiboCount += we.getCount();
				}
				// 查询用户昵称含有输入的内容的昵称列表
				// searchContents.add("搜索"+'"'+searchKey+'"'+"相关用户>");
				User userCondition = new User();
				userCondition.setNickName(searchKey);
				UserExecution ue = userService.getUserList(userCondition);
				if (ue.getState() == UserStateEnum.SUCCESS.getState()) {
					for (User user : ue.getUserList()) {
						searchNickNames.add(user.getNickName());
					}
					nickNameCount += ue.getCount();
				}
				// 按时间顺序返回所有含有输入内容的微博列表
				modelMap.put("success", true);
				// modelMap.put("contentCount", contentCount);
				modelMap.put("weiboCount", weiboCount);
				modelMap.put("nickNameCount", nickNameCount);
				// modelMap.put("searchContents",searchContents);
				modelMap.put("searchNickNames", searchNickNames);
				modelMap.put("searchWeibos", searchWeibos);

			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "输入的搜索内容为空");
			}
		} catch (WeiboOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}

	@RequestMapping(value = "/getweiboofhomepage", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getWeiboOfHomePage(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取一页需要显示的条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		User author = new User();
		Long userId = HttpServletRequestUtil.getLong(request, "userId");
		Long toUserId = HttpServletRequestUtil.getLong(request, "toUserId");
		if (toUserId > 0) {
			author = userService.getUserById(toUserId);
		} else {
			if (userId > 0) {
				author = userService.getUserById(userId);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "无效userId");
			}
		}
		List<Weibo> weiboList = new ArrayList<Weibo>();
		int weibocount = 0;
		if (author != null && author.getUserId() > 0 && pageIndex > -1 && pageSize > -1) {
			// modelMap.put("user", author);
			Weibo weiboCondition = new Weibo();

			// 在我的主页显示本用户的微博
			weiboCondition.setAuthor(author);
			try {
				WeiboExecution we = weiboService.getWeiboList(weiboCondition, pageIndex, pageSize);
				if (we.getState() == WeiboStateEnum.SUCCESS.getState()) {
					if (we.getCount() > 0) {
						weiboList.addAll(we.getWeiboList());
						weibocount = we.getCount();
					}
				}
			} catch (WeiboOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			// 前面都成功才会到这
			// 调用自定义好的ListSortUtil工具类，对获得的微博列表按发布时间降序排序
			// List<Weibo> sortWeiboList = ListSortUtil.ListSort(weiboList);
			User user = userService.getUserById(userId);
			modelMap.put("weiboCount", weibocount);
			modelMap.put("weiboList", weiboList);
			modelMap.put("user", user);
			modelMap.put("success", true);
			return modelMap;

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请登录呢");
			return modelMap;
		}
	}

	@RequestMapping(value = "/getweibocontent", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getWeiboContent(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long weiboId = HttpServletRequestUtil.getLong(request, "weiboId");
		// 通过微博Id获取微博内容
		if (weiboId > 0) {
			try {
				Weibo weibo = weiboService.getWeiboByWeiboId(weiboId);
				if (weibo != null) {
					modelMap.put("content", weibo.getContent());
					modelMap.put("success", true);
				} else {
					modelMap.put("errMsg", "内部系统出错");
					modelMap.put("success", false);
				}
			} catch (WeiboOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "微博Id无效");
		}
		return modelMap;
	}

	@RequestMapping(value = "/deleteweibo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteWeibo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long weiboId = HttpServletRequestUtil.getLong(request, "weiboId");
		User user = (User) request.getSession().getAttribute("currentUser");
		try {
			if (weiboId > 0 && user.getUserId() > 0) {
				WeiboExecution we = weiboService.deleteWeiboById(user.getUserId(), weiboId);
				if (we.getState() == UserRelationStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					try {
						// 若有@的对象,则需删除通知表通知对应目标用户
						Notify notify = new Notify();
						notify.setSenderId(user.getUserId());
						notify.setAction(1);
						notify.setWeiboId(weiboId);
						NotifyExecution ne = notifyService.deleteNotify(notify);
					} catch (Exception e) {
						return modelMap;
					}
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", UserRelationStateEnum.stateInfoOf(we.getState()));
				}
			}
		} catch (UserOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}

	@RequestMapping(value = "/getweibolistbysearch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getWeiboListBySearch(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取一页需要显示的条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		String weiboConditionStr = HttpServletRequestUtil.getString(request, "weiboConditionStr");
		Weibo weiboCondition = new Weibo();
		ObjectMapper mapper = new ObjectMapper();
		List<Weibo> weiboList = new ArrayList<>();

		int count = 0;
		try {
			weiboCondition = mapper.readValue(weiboConditionStr, Weibo.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			if (weiboCondition != null && weiboCondition.getContent() != null && pageIndex > -1 && pageSize > -1) {
				int tempPageSize = 2;
				String nickName = weiboCondition.getContent();
				int strSize = weiboCondition.getContent().length();

				// 如果搜索关键字中含有"搜索相关微博"
				if (weiboCondition.getContent().indexOf("相关微博") != -1) {
					// "只要test.indexOf('This')返回的值不是-1说明test字符串中包含字符串'This',相反如果包含返回的值必定是-1"
					int tempSize = strSize - 6;
					weiboCondition.setContent(weiboCondition.getContent().substring(4, tempSize));
					Map<String, Object> modelMap1 = searchWeibo(weiboCondition, pageIndex, tempPageSize, weiboList, count);
					return modelMap1;
					// System.out.println("存在包含关系(相关微博)，因为返回的值不等于-1");
				} else if (nickName.indexOf("相关用户") != -1) {
					int tempSize = strSize - 12;
					nickName = nickName.substring(4, tempSize);
					Map<String, Object> modelMap1 = searchNickName(nickName, pageIndex, tempPageSize, weiboList, count);
					return modelMap1;
					// System.out.println("存在包含关系(相关用户)，因为返回的值不等于-1");
				} else {
					System.out.println("不存在包含关系，因为返回的值等于-1");
					Map<String, Object> modelMap1 = searchAll(weiboCondition, nickName, pageIndex, tempPageSize, weiboList, count);
				    return modelMap1;
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "输入的搜索内容为空");
			}
		} catch (WeiboOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}

	/**
	 * 查询微博内容与用户昵称
	 */
	private Map<String,Object> searchAll(Weibo weiboCondition,String nickName,int pageIndex, int tempPageSize,
			List<Weibo> weiboList, int count) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 查询微博内容含有输入的内容的微博列表
		WeiboExecution we = weiboService.getWeiboList(weiboCondition, pageIndex, tempPageSize);
		if (we.getState() == WeiboStateEnum.SUCCESS.getState() && we.getCount() > 0) {
			weiboList.addAll(we.getWeiboList());
			count += we.getCount();
			if (we.getCount() < 1) {
				tempPageSize++;
			}
		}
		// 查询用户昵称含有输入的内容的微博列表
		Weibo tempCondition = new Weibo();
		User author = new User();
		author.setNickName(nickName);
		tempCondition.setAuthor(author);
		WeiboExecution tempwe = weiboService.getWeiboList(tempCondition, pageIndex, tempPageSize);
		if (tempwe.getState() == WeiboStateEnum.SUCCESS.getState() && tempwe.getCount() > 0) {
			weiboList.addAll(tempwe.getWeiboList());
			count += tempwe.getCount();
		}
		// 按时间顺序返回所有含有输入内容的微博列表
		if (count > 0) {
			modelMap.put("success", true);
			modelMap.put("weiboCount", count);
			modelMap.put("weiboList", ListSortUtil.ListSort(weiboList));
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "searchAll error");
		}
		return modelMap;
	}
	
	/**
	 * 查询微博内容
	 */
	private Map<String, Object> searchWeibo(Weibo weiboCondition,int pageIndex, int tempPageSize,
			List<Weibo> weiboList, int count) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 查询微博内容含有输入的内容的微博列表
		WeiboExecution we = weiboService.getWeiboList(weiboCondition, pageIndex, tempPageSize);
		if (we.getState() == WeiboStateEnum.SUCCESS.getState() && we.getCount() > 0) {
			weiboList.addAll(we.getWeiboList());
			count += we.getCount();
			if (we.getCount() < 1) {
				tempPageSize++;
			}
		}
		// 按时间顺序返回所有含有输入内容的微博列表
		if (count > 0) {
			modelMap.put("success", true);
			modelMap.put("weiboCount", count);
			modelMap.put("weiboList", ListSortUtil.ListSort(weiboList));
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "searchWeibo error");
			return modelMap;
		}
	}

	/**
	 * 查询用户昵称
	 */
	private Map<String, Object> searchNickName(String nickName, int pageIndex, int tempPageSize, List<Weibo> weiboList,
			int count) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 查询用户昵称含有输入的内容的微博列表
		Weibo tempCondition = new Weibo();
		User author = new User();
		author.setNickName(nickName);
		tempCondition.setAuthor(author);
		WeiboExecution tempwe = weiboService.getWeiboList(tempCondition, pageIndex, tempPageSize);
		if (tempwe.getState() == WeiboStateEnum.SUCCESS.getState() && tempwe.getCount() > 0) {
			weiboList.addAll(tempwe.getWeiboList());
			count += tempwe.getCount();
		}
		// 按时间顺序返回所有含有输入内容的微博列表
		if (count > 0) {
			modelMap.put("success", true);
			modelMap.put("weiboCount", count);
			modelMap.put("weiboList", ListSortUtil.ListSort(weiboList));
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "searchNickName error");
			return modelMap;
		}
	}

	@RequestMapping(value = "/getweiboinfoonmine", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getWeiboInfoOnMine(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		// 获取一页需要显示的条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		User user = new User();
		Long userId = HttpServletRequestUtil.getLong(request, "userId");
		if (userId > 0) {
			user = userService.getUserById(userId);
			request.getSession().setAttribute("currentUser", user);
			modelMap.put("user", user);
		} else {
			user = (User) request.getSession().getAttribute("currentUser");
			modelMap.put("user", user);
		}
		List<Weibo> weiboList = new ArrayList<Weibo>();
		int weibocount = 0;
		if (user.getUserId() != null && user != null && pageIndex > -1 && pageSize > -1) {
			// 声明每个用户一页显示条数
			int tempPageSize = 0;
			modelMap.put("user", user);
			Weibo weiboCondition = new Weibo();

			// 在我的主页显示本用户所关注的用户的微博
			try {
				UserRelation relationCondition = new UserRelation();
				relationCondition.setFans(user);
				UserRelationExecution ure = userRelationService.getUserRelationList(relationCondition);
				int relationCount = ure.getCount();
				tempPageSize = pageSize / (relationCount + 1) > 1 ? (pageSize / (relationCount + 1)) : 1;
				if (ure.getState() == UserRelationStateEnum.SUCCESS.getState()) {
					List<UserRelation> relationList = ure.getUserRelationList();
					for (UserRelation relation : relationList) {
						try {
							User author = new User();
							author.setUserId(relation.getStars().getUserId());
							weiboCondition.setAuthor(author);
							WeiboExecution relationWe = weiboService.getWeiboList(weiboCondition, pageIndex,
									tempPageSize);
							if (relationWe.getState() == UserRelationStateEnum.SUCCESS.getState()) {
								if (relationWe.getCount() > 0) {
									weiboList.addAll(relationWe.getWeiboList());
									weibocount += relationWe.getCount();
								}
							}
						} catch (UserRelationOperationException e) {
							modelMap.put("success", false);
							modelMap.put("errMsg", e.getMessage());
							return modelMap;
						}
					}
				}
			} catch (WeiboOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			// 在我的主页显示本用户的微博
			weiboCondition.setAuthor(user);
			try {
				WeiboExecution we = weiboService.getWeiboList(weiboCondition, pageIndex, tempPageSize);
				if (we.getState() == WeiboStateEnum.SUCCESS.getState()) {
					if (we.getCount() > 0) {
						weiboList.addAll(we.getWeiboList());
						weibocount += we.getCount();
					}
				}
			} catch (WeiboOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			// 前面都成功才会到这
			// 调用自定义好的ListSortUtil工具类，对获得的微博列表按发布时间降序排序
			List<Weibo> sortWeiboList = ListSortUtil.ListSort(weiboList);
			modelMap.put("weiboCount", weibocount);
			modelMap.put("weiboList", sortWeiboList);
			modelMap.put("success", true);
			return modelMap;

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户信息为空");
			return modelMap;
		}
	}

	// 支持上传微博图片图的最大数量
	private static final int IMAGEMAXCOUNT = 9;

	@RequestMapping(value = "/publishweibo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> publishWeibo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 接收前端参数(@的用户昵称)
		String atNickName = HttpServletRequestUtil.getString(request, "atNickName");
		// 接收前端参数的变量初始化，包括微博，微博图列表实体
		String weiboStr = HttpServletRequestUtil.getString(request, "weiboStr");
		// 登录的用户
		User user = (User) request.getSession().getAttribute("currentUser");

		// 判断atNickName用户昵称是否存在
		boolean flag = false;
		if (atNickName != null) {
			List<User> userList = userService.getUserList();
			for (User tempUser : userList) {
				if (tempUser.getNickName() != null && atNickName.equals('@' + tempUser.getNickName())) {
					flag = true;
				}
			}
			if (!flag) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "@的用户名不存在");
				return modelMap;
			}
		}
		Weibo weibo = null;
		List<ImageHolder> weiboImgHolderList = new ArrayList<ImageHolder>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			for (int i = 0; i < IMAGEMAXCOUNT; i++) {
				CommonsMultipartFile weiboImgFile = HttpServletRequestUtil.getCommonsMultipartFile(request,
						"weiboImg" + i);
				if (weiboImgFile != null) {
					// 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
					ImageHolder weiboImg = new ImageHolder(weiboImgFile.getOriginalFilename(),
							weiboImgFile.getInputStream());
					weiboImgHolderList.add(weiboImg);
				} else {
					// 若取出的第i个详情图片文件流为空，则终止循环
					break;
				}
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			weibo = mapper.readValue(weiboStr, Weibo.class);
			User author = new User();
			author.setUserId(user.getUserId());
			weibo.setAuthor(author);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		if (weibo.getContent() == null && "".equals(weibo.getContent())) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "微博内容不能为空");
			return modelMap;
		}
		if (weibo != null && weibo.getAuthor().getUserId() != null) {
			try {
				// 执行添加操作
				WeiboExecution we = weiboService.addWeibo(weibo, weiboImgHolderList);
				if (we.getState() == WeiboStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					// @的昵称不为空,则需插入通知表通知对应目标用户
					if (atNickName != null) {
						Notify notify = new Notify();
						notify.setWeiboId(we.getWeibo().getWeiboId());
						notify.setSenderId(user.getUserId());
						notify.setNickName(user.getNickName());
						notify.setHeadImg(user.getHeadImg());
						NotifyExecution ne = notifyService.addNotifyOfWeiboAt(atNickName, notify);
						if (ne.getState() == NotifyStateEnum.SUCCESS.getState()) {
							modelMap.put("success", true);
						} else {
							modelMap.put("success", false);
							modelMap.put("errMsg", ne.getStateInfo());
						}
					}
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", we.getStateInfo());
				}
			} catch (WeiboOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "发布的微博不能为空");
			return modelMap;
		}
		return modelMap;
	}
}
