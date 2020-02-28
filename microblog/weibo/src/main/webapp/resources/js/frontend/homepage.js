$(function() {
	var state = getQueryString('state');
	var userId = getQueryString('userId');
	var toUserId = getQueryString('toUserId');
	var type = "mine";
	if (userId == toUserId) {
		toUserId = '';
	}
	// 获取homepage页面数据路径
	var getHomePageInfoUrl = '/weibo/frontend/getmineinfo?userId=' + userId
			+ '&toUserId=' + toUserId;
	// 更换头像路径
	var modifyHeadImgUrl = '/weibo/frontend/modifyuserheadimg'
	// 获取该页面的的微博列表
	var getWeiboOfHomePageUrl = '/weibo/frontend/getweiboofhomepage'
			+ '?userId=' + userId + '&toUserId=' + toUserId;
	// 分页允许最大条数，超过此数则禁止访问后台
	var maxItems = 999;
	// 一页加载最大条数
	var pageSize = 3;
	// 页码
	var pageNum = 1;
	// 取消关注
	var cancelRelationUrl = '/weibo/frontend/canceluserrelation';
	// 添加关注
	var addRelationUrl = '/weibo/frontend/adduserrelation';
	// 删除微博
	var deleteWeiboUrl = '/weibo/frontend/deleteweibo';
	// 获取微博内容
	var getWeiboContentUrl = '/weibo/frontend/getweibocontent' + '?weiboId=';
	// 实现微博点赞
	var addPraiseOfWeiboUrl = '/weibo/frontend/addpraiseofweibo';
	// 获取点赞状态
	var getPraiseListUrl = '/weibo/frontend/getpraiseList';
	// 获取该用户昵称的用户Id
	var getUserByNickNameUrl = '/weibo/frontend/getuserbynickname';
	// 获取用户关注列表路径(state=0)||粉丝列表则不用state
	var getUserRelationListUrl = '/weibo/frontend/getuserrelationlist'
			+ '?userId=' + userId + '&toUserId=' + toUserId;
	// 判断用户关系
	var getRelationUrl = '/weibo/frontend/getuserrelation?starsId=';
	// 获取粉丝关注微博count路径
	var getCountByUserIdUrl = '/weibo/frontend/getmineinfo?userId=';
	// 计算个人资料完成度
	var getUserDataCompletenessUrl = '/weibo/frontend/getuserdatacompleteness?userId='
			+ userId + '&toUserId=' + toUserId;

	/**
	 * 判断是我的页面还是ta人的主页
	 */
	if ($.trim(toUserId) == '') {
		// 我的主页
		type = "mine";
	} else {
		// ta的主页
		type = "his";
		$('.tips-stars-content').text("还没有人关注 Ta 呢！");
		$('.tips-fans-content').text(" Ta 还没有粉丝呢！");
	}
	
	/**
	 * 初始化mine页面数据，包括Id,放在最前
	 */
	getHomePageInfo();
	
	if (state === "stars") {
		getStarsOfHomePage();
		$(".weibo-contentlist").hide();
		$(".follow-content").show();
		$(".fans-content").hide();
	} else if (state === "fans") {
		getFansOfHomePage();
		$(".weibo-contentlist").hide();
		$(".fans-content").show();
		$(".follow-content").hide();
	}else{
		getHomePageInfo();
		initAddWeiboItems();
	}
	
	/**
	 * 让addWeiboItems(pageNum, pageSize);回到初始状态
	 */
	function initAddWeiboItems(){
		pageNum = 1;
		$("#weiboItem").empty();
		addWeiboItems(pageNum, pageSize);
	}
	
	// 切换到微博
	$(".weibo-switch").click(function(event){
		event.preventDefault();
		$(".weibo-contentlist").show();
		$(".fans-content").hide();
		$(".follow-content").hide();
		initAddWeiboItems();
	});

	function getHomePageInfo() {
			$.ajax({
				url : getHomePageInfoUrl,
				data : {
				},
				type : 'get',
				async : false,
				success : function(data) {
			if (data.success) {
				 var user= data.user;
				$('title').text(user.nickName + " 的微博主页");
				$(".profile-a").attr("href",'changeuser?userId='+user.userId);
				
				/**
				 * 判断是我的页面还是ta人的主页
				 */
				if (type == "mine") {
					// 我的主页
					if (user.headImg) {
						$('#my-headImg').attr("src", user.headImg);
					}
					$("#mid-center").text("我的主页");
					$('#changeUser').attr('href',
							'changeuser?userId=' + user.userId);
					if (user.profile) {
						$("#profile").text(user.profile);
					}
				} else {
					// ta人的主页
					// 关注的关系
					$("#relation-private").show();
					showHtmlByIsRelation(userId, toUserId);

					if (user.headImg) {
						$('#his-headImg').attr("src", user.headImg);
					}
					// $("#personal-data").hide();
					$(".edit-info").hide();
					if (user.province) {
						$("#province").text(user.province);
					}
					if (user.city) {
						$("#city").text(user.city);
					}
					$("#birthday").text(FormatDateToYMH(user.birthday));
					if (user.profile) {
						$("#introduce-profile").text(user.profile);
					}
					if (user.sex == 0) {
						$("#introduce-sex").text("女");
					} else {
						$("#introduce-sex").text("男");
					}
					$("#mid-center").text("ta的主页");
					if (user.profile) {
						$("#profile").text(user.profile);
					} else {
						$("#profile").text("这个人很懒，还没说过什么呢..");
					}
				}
				/**
				 * 用户部分
				 */
				if (user.nickName) {
					$('#nickName').text(user.nickName);
				}
				if (user.sex == 0) {
					$('.sex').text(' ♀ ');
					$('.sex').attr("style", "color:#f691bb");
					$(".mid-sex").text("女");
				} else {
					$('.sex').text(' ♂ ');
					$('.sex').attr("style", "color:#4ebdf9");
					$(".mid-sex").text("男");
				}
				
				if (data.starsCount > 0) {
					$('#starsCount').text(data.starsCount);
				} else {
					$('#starsCount').text('0');
				}
				if (data.fansCount > 0) {
					$('#fansCount').text(data.fansCount);
				} else {
					$('#fansCount').text('0');
				}
				if (data.weiboCount) {
					$('#weiboCount').text(data.weiboCount);
				} else {
					$('#weiboCount').text('0');
				}
				// 计算个人资料完成度
				getUserDataCompleteness();
			} else {
				$(".alert-delete").show();
				$(".tips-text").text("你还未登录呢，去登录？");
				$(".delete-yes").text("去登录");
				$(document).on("click",".delete-yes",function(){
					window.location.href='/weibo/frontend/index';
			    });
			}
				}
		});
	}

	
	/**
	 * 个人资料完成度
	 */
	function getUserDataCompleteness() {
		$.getJSON(getUserDataCompletenessUrl, function(data) {
			if (data.success) {
				var completeness = data.completeness;
				$('#completeness-data').text(completeness + "%");
				var width = completeness + '%';
				$('#completeness-width').width(width);
			}
		});
	}

	/**
	 * 切换到关注
	 */
	$(".follow-switch").click(function(event) {
		event.preventDefault();
		getStarsOfHomePage();
		$(".weibo-contentlist").hide();
		$(".follow-content").show();
		$(".fans-content").hide();

	});
	function getStarsOfHomePage() {
		var url = getUserRelationListUrl + '&state=0';
		$.getJSON(url, function(data) {
			if (data.success) {
				$('.tips-stars').hide();
				var starsList = data.userRelationList;
				showStarsListHtml(starsList);
			} else {
				$('#stars-list').empty();
				$('.tips-stars').show();
// $.toast({
// text : "请先登录呢"
// })
			}
		});
	}
	function showStarsListHtml(starsList) {
		var starsHtml = '';
		starsList
				.map(function(item, index) {
					if ($.trim(item.stars.headImg) == ''
							|| item.stars.headImg == null) {
						var headImg = "../resources/images/user.jpg";
					} else {
						var headImg = item.stars.headImg;
					}
					if ($.trim(item.stars.profile) == ''
							|| item.stars.profile == null) {
						var profile = "这个人很懒，还没说过什么呢..";
					} else {
						var profile = item.stars.profile;
					}
					var state = '';
					var cancelHtml = '';
					if (type == "mine") {
						if ($.trim(item.state) == -1 || item.state == 0) {
							state = " ✔已关注 ";
						} else {
							state = " ⇌互相关注 ";
						}
						cancelHtml = `<div style="border-radius: 4px;cursor: pointer;color: #333;background-color: #fff;height: 30px;border: 1px solid #ccc;
								font-size: 12px;text-align: center;width: 70px;line-height: 30px;margin-left: 0;margin-top: 5px;"
								data-id="${item.stars.userId}" id="cancel-relation">✘ </div>`;
					} else {
						if (userId == item.stars.userId) {
							cancelHtml = `<div style="border-radius: 4px;cursor: pointer;color: #333;background-color: #fff;height: 30px;border: 1px solid #ccc;
									font-size: 12px;text-align: center;width: 70px;line-height: 30px;margin-left: 0;margin-top: 5px;"
									>本人</div>`;
						} else {
							if (isUserRelation(userId, item.stars.userId)) {
								cancelHtml = `<div style="border-radius: 4px;cursor: pointer;color: #333;background-color: #fff;height: 30px;border: 1px solid #ccc;
										font-size: 12px;text-align: center;width: 70px;line-height: 30px;margin-left: 0;margin-top: 5px;"
										>✔已关注</div>`;
							} else {
								cancelHtml = `<div style="border-radius: 4px;cursor: pointer;color: #333;background-color: #fff;height: 30px;border: 1px solid #ccc;
										font-size: 12px;text-align: center;width: 70px;line-height: 30px;margin-left: 0;margin-top: 5px;" 
										data-id="${item.stars.userId}" id="hisstars-add-relation">+ 关注</div>`
							}

						}
					}

					starsHtml += `<!-- 一个关注 -->
							<div class="all-follows-item">
							<div style="width: 30%;height: 100%;border-right: 1px solid #d9d9d9;box-sizing: border-box;">
							<a href="homepage?userId=
							${userId}&toUserId=
							${item.fans.userId}
							"><img src="
							 ${headImg}
							" style="width: 50px;height: 50px;
							border-radius: 50%;margin: 25px auto 5px 8px;" ></a></div>
							<div style="width: 60%;height: 100%;padding-left: 10px;">
							<a href="homepage?userId=
							${userId}&toUserId=
							${item.stars.userId}"class="all-follows-item-name">
							${item.stars.nickName}</a>
							<!-- 自行判断关注状态，互相关注则显示     ⇌互相关注   -->
							<span style="font-size: 12px;color: #333;display: block;margin-top: 4px;">
							 ${state}
							</span><span style="color: #808080;font-size: 11px;display: block;
							margin-top: 5px;overflow: hidden;width: 80%;text-overflow: ellipsis;white-space: nowrap;z-index: 999;">
							${profile}
							</span>
							${cancelHtml}
							</div></div><!-- 一个关注 -->`
				});
		$('#stars-list').html(starsHtml);
	}

	/**
	 * 切换到粉丝
	 */
	$(".fans-switch").click(function(event) {
		event.preventDefault();
		getFansOfHomePage();
		$(".weibo-contentlist").hide();
		$(".fans-content").show();
		$(".follow-content").hide();
	});
	
	function getFansOfHomePage() {
		$.getJSON(getUserRelationListUrl, function(data) {
			if (data.success) {
				$('.tips-fans').hide();
				var fansList = data.userRelationList;
				$('#span-fansCount').text(data.userRelationCount);
				showFansListHtml(fansList);
			} else {
				$('#fans-list').empty();
				$('.tips-fans').show();
			}
		});
	}

	function showFansListHtml(fansList) {
		var fansHtml = '';
		fansList
				.map(function(item, index) {

					if ($.trim(item.fans.headImg) == ''
							|| item.fans.headImg == null) {
						var headImg = "../resources/images/user.jpg";
					} else {
						var headImg = item.fans.headImg;
					}
					if ($.trim(item.fans.profile) == ''
							|| item.fans.profile == null) {
						var profile = "这个人很懒，还没说过什么呢..";
					} else {
						var profile = item.fans.profile;
					}
					if ($.trim(item.fans.province) == ''
							|| item.fans.province == null) {
						var province = "其他";
					} else {
						var province = item.fans.province;
					}
					if ($.trim(item.fans.city) == '' || item.fans.city == null) {
						var city = "其他";
					} else {
						var city = item.fans.city;
					}
					if (province == "其他" && city == "其他") {
						province = "其他";
						city = '';
					}
					var sexHtml = '';
					if ($.trim(item.fans.sex) == '' || item.fans.sex == null) {
						sex = "其他";
					} else {
						if (item.fans.sex == 0) {
							sexHtml = '<span style="font-weight: bolder;color: #f691bb;font-size: 15px;">'
									+ ' ♀ ' + '</span>'
						} else {
							sexHtml = '<span style="font-weight: bolder;color: #4ebdf9;font-size: 15px;">'
									+ ' ♂ ' + '</span>'
						}
					}
					var stateHtml = '';
					var stateStyle=`cursor: pointer;margin-top: 10px;margin-right: 10px;float: right;width: 80px;height: 25px;
						text-align: center;line-height: 25px;font-size: 13px;border-radius: 2px;color: #333;border: 1px solid #ccc;`;
					if (type == "mine") {
						stateHtml = `<span data-id="${item.fans.userId}" class="delete-fans" 
							style="width:20px;cursor: pointer;margin-top: 10px;margin-right:10px;float:right;height: 25px;
								text-align: center;line-height: 25px;font-size: 13px;border-radius: 2px;color: #333;border: 1px solid #ccc;">
											X </span>`;
						if ($.trim(item.state) == -1 || item.state == 0) {
							stateHtml += `<div id="add-relation" data-id="${item.fans.userId}" class="all-fans-switch" style="${stateStyle}">
									 + 关注 </div>`;
						} else {
							stateHtml += `<div id="fans-cancel-relation" data-id="${item.fans.userId}" class="all-fans-switch" style="${stateStyle}">
									 ⇌互相关注 </div>`;
						}
	
					} else {
						if (userId == item.stars.userId) {
							stateHtml = '';
						} else {
							if (isUserRelation(userId, item.stars.userId)) {
								stateHtml = `<div data-id="${item.fans.userId}" class="all-fans-switch" style="${stateStyle}">
										✔已关注</div>`;
							} else {
								stateHtml = `<div id="add-relation" data-id="${item.fans.userId}" class="all-fans-switch" style="${stateStyle}">
										 + 关注 </div>`;
							}
						}
					}

					// 获取该用户的粉丝微博关注count
					var count = getCountByUserId(item.fans.userId);
					if (count.starsCount) {
						var starsCount = count.starsCount;
					} else {
						var starsCount = 0;
					}
					if (count.fansCount) {
						var fansCount = count.fansCount;
					} else {
						var fansCount = 0;
					}
					if (count.weiboCount) {
						var weiboCount = count.weiboCount;
					} else {
						var weiboCount = 0;
					}

					fansHtml += `<div style="display: flex;width: 100%;height: 105px;" class="fans-item">
							<div style="width: 12%;">
							<a href="homepage?userId=${userId}&toUserId=${item.fans.userId}">
							<img src="${headImg}" style="width: 50px;height: 50px;
							border-radius: 50%;margin-left: 10px;margin-top: 10px;" ></a>
							</div>
							<div style="margin-top: 10px;">
							<a class="fans-username" href="homepage?userId=${userId}&toUserId=${item.fans.userId}">${item.fans.nickName}</a>
							${sexHtml}<div class="all-fans-info" ><div>关注<a href="#">
							${starsCount}</a></div><div>粉丝<a href="#">
							${fansCount}
							</a></div>
							<div>微博<a href="#">
							${weiboCount}
							</a></div>
							</div>
							<div style="font-size: 12px;color: #808080;margin-top: 5px;">地址:<span style="color: #333;">&nbsp
							${province}
							&nbsp
							${city}
							</span></div>
							<div style="margin-top: 5px;font-size: 12px;color: #808080;width: 100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">简介:<span style="color: #333;
							">&nbsp
							${profile}
							</span></div>
							</div>
							<div style="flex: 1;"> 
							${stateHtml}
							</div></div>`;
				});
		$('#fans-list').html(fansHtml);
	}
	
	// 获取该用户的粉丝微博关注count
	function getCountByUserId(tempId) {
		var count = {};
		var url = getCountByUserIdUrl + tempId;
		$.ajax({
			url : url,
			data : {},
			type : 'get',
			async : false,
			success : function(data) {
				if (data.success) {
					count = data;
				}
			}
		});
		return count;
	}

	var stop = true;// 触发开关，防止多次调用事件
	$(window).scroll(
			function() {
				// 当内容滚动到底部时加载新的内容 100当距离最底部100个像素时开始加载.
				if ($(this).scrollTop() + $(window).height() + 50 >= $(
						document).height()
						&& $(this).scrollTop() > 50) {
					if (stop == true) {
						stop = false;
						// 加载提示信息
						$(".ajaxtips").show();
						addWeiboItems(pageNum, pageSize);
					}
				}
			});
	/**
	 * 微博卡片的无限滚动加载
	 */
	function addWeiboItems(pageIndex, pageSize) {
		var url = getWeiboOfHomePageUrl + '&pageIndex=' + pageIndex + '&pageSize='
				+ pageSize;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.weiboCount;
				// 获取当前已经加载的所有微博总数
				var total = $("#weiboItem").find(".article").length;
				if (total >= maxItems) {
					$(".ajaxtips").hide();
					$(".load-end").show();
					return;
				} else {
					$(".ajaxtips").show();
				}
				var weiboList = data.weiboList;
				var user=data.user;
				showWeiboList(weiboList,user);
	
				pageNum++;// 当前要加载的页码
				stop = true;
			} else {
				/*$.toast({
					text : "后台获取微博卡片出错"
				})*/
			}
		});
	}
	
	function showWeiboList(list,user) {
		var weiboHtml = '';

		list
				.map(function(item, index) {
					var str = item.content.substr(item.content.indexOf("\@"));
					var atNickName = str.substring(0, str.indexOf("\ "));

					// 获取优化后的内容
					var contentHtml = initContentHtml(item.weiboId,
							"#weibo-content", item.content, 70, 1);

					// 查询该用户昵称的所属用户Id
					var targetId = getUserIdByNickName(str, atNickName);

					// 判断userId与toUserId是否不同，不同才赋值两个否则一个
					var toUserId = item.author.userId;
					if (userId == toUserId) {
						toUserId = '';
					}

					// 获取微博点赞的状态并给对应div设置点赞效果
					shwoWeiboPraise(item.weiboId);
					
					// 获取微博收藏的状态并给对应div设置效果
					shwoWeiboCollection(item.weiboId);
					
					// 调用自定义方法获取微博列表的Html
					weiboHtml += `<!-- 一条微博 --><div class="article">
							<div class="article-header">
							<!-- 点击图片跳转到页面 --><div>
							<a href="homepage?userId=${userId}&toUserId=${item.author.userId}">
							<img src="${item.author.headImg}" /></a>
							</div>
							<div class="article-info">
							<div class="article-user">
							<a href="homepage?userId=${userId}&toUserId=${item.author.userId}">
							${item.author.nickName}
							</a></div>
							<div class="article-time">
							<a href="homepage?userId=${userId}&toUserId=${item.author.userId}">
							${FormatAllDateToYMDHMS(item.publishTime)}
							</a>
							</div></div>
							<div class="arrow" data-id="${item.weiboId}">
							<span class="iconfont" id="arrow">&#xe625;</span>
							</div>
							<div class="arrow-context" id="arrow-context${item.weiboId}">
							<ul>${getArrowHtml(item.weiboId)}</ul>
							
							</div></div>
							<!-- 文章部分 -->
							<div class="article-context">
							<div class="article-context-title">
							<a href="homepage?userId=${userId}&toUserId=${targetId}" style="color: #FA7D3C;
							text-decoration: none;">
							${atNickName}</a>
							<span id="weibo-content${item.weiboId}">
							${contentHtml}</span></div>
							<div class="article-context-img">
							${showWeiboImg(item.weiboImgList,item.weiboId)}
							</div>
							</div>
						    ${getBigImgTipHtml(item.weiboId)}
							<!-- 底部 -->
							<div class="article-footer"><div id="checkCollection" data-id="${item.weiboId}" 
							class="checkCollection${item.weiboId}">
							<span class="iconfont">&#xe641;</span><span 
							class="footer-font collection-text${item.weiboId}">收藏</span></div>
							<div><a style="text-decoration:none;color:#808080;font-size: 12px;" href="weibodetail?userId=${userId}&weiboId=${item.weiboId}">
							 <span class="iconfont">&#xe62d;</span>查看微博</a></div>
							<div id="comment-icon" class="btn-comment" data-id="${item.weiboId}">
							<span class="iconfont">&#xe62f;</span><span class="footer-font">评论&nbsp;${showWeiboCommentCount(item.weiboId)}</span>
							</div>
							<!-- 点赞模块 -->
							<div id="weibo-praise" data-id="${item.weiboId}" class="weibo-praise${item.weiboId}">
							<span class="iconfont" >&#xe639;</span><span class="footer-font" id="wpraise-count${item.weiboId}">4999</span></div>
							</div>
							<!-- 评论模块 -->
							<div class="comment comment-div${item.weiboId}">
							<div class="comment-first"><div><img src="${user.headImg}" style="width: 32px; height: 32px; margin-top: 15px;z-index:9999; margin-left: 18px;">
							</div><div><input class="comment-input" type="text" style="margin-top: 15px; height: 27px; 
							width: 518px; margin-left: 10px;" 
							id="content1${item.weiboId}"/></div><!--disabled--><div><button 
							class="comment-button" id="comment" 
							data-id="${item.weiboId}">评论</button></div></div><div class="comment-list comment-list${item.weiboId}" style="min-height:0px;display:none;">
							<!-- 评论列表 -->
							<div class="comment-list-context" 
							id="commentItem${item.weiboId}"></div> </div> </div>
							<!-- 评论模块结束 --> </div>`;
				});
		$(".ajaxtips").hide();
		$('#weiboItem').append(weiboHtml);

	}

	// 判断用户关系，返回不同的关注html值
	function getIsRelationHtml(userId, weiboId) {
		var getRelationUrl = '/weibo/frontend/getuserrelation?starsId='
				+ userId;
		var html = '';
		$.ajax({
			url : getRelationUrl,
			data : {},
			type : 'post',
			async : false,
			success : function(data) {
				var cancelHtml = '<li><a id="cancelRelation" data-id="' + userId
						+ '" style="cursor: pointer;">取消关注</a></li>';
				var addHtml = '<li><a id="addRelation" data-id="' + userId
						+ '" style="cursor: pointer;">添加关注</a></li>';
				if (data.success) {
					var state = data.state;
					var id = "#relation-li" + weiboId;
					if (state == 0 || state == 1) {
						// $(id).html(cancelHtml);
						html = cancelHtml;
					} else if (state == -1 || state == null) {
						html = addHtml;
					} 
				} else {
					$.toast({
						text : 'errMsg :' + data.errMsg
					});
				}
			}
		});
		return html;
	}
	
	/**
	 * 通过type获取arrowhtml
	 */
	function getArrowHtml(weiboId) {
		var html = '';
		if (type == "mine") {
			html = '<li><a id="deleteWeibo" data-id="' + weiboId
					+ '" style="cursor: pointer;">删除微博</a></li>';
		} else {
			html=getIsRelationHtml(toUserId,weiboId);
		}
		return html;
	}
	
	/**
	 * 微博卡片的收藏事件
	 */
	$(document).on("click", "#checkCollection", function(e) {
		checkCollection(e);
	});
	
	/**
	 * 查询用户昵称的所属用户（可封装）
	 */
	function getUserIdByNickName(str, atNickName) {
		if ($.trim(atNickName) == '') {
			return '';
		}
		var nickName = atNickName.substring(1, str.indexOf("\ "));
		var tempId = '';
		$.ajax({
			url : getUserByNickNameUrl,
			data : {
				nickName : nickName
			},
			type : 'get',
			async : false,
			success : function(data) {
				if (data.success) {
					var user = data.user;
					tempId = user.userId;
				} else {
					tempId = '';
				}
			}
		});
		return tempId;
	}

	/*
	 * 获取微博点赞的状态并给对应div设置点赞效果
	 */
	function shwoWeiboPraise(weiboId) {
		var url = getPraiseListUrl + '?weiboId=' + weiboId + '&userId='
				+ userId;
		var divClass = ".weibo-praise" + weiboId;
		var spanId = "#wpraise-count" + weiboId;
		$.getJSON(url, function(data) {
			if (data.success) {
				if (data.count > 0) {
					$(spanId).text(data.count);
				} else {
					$(spanId).text('');
				}
				if (data.state == 1) {
					$(divClass).attr("style", "color: #FA7D3C;");
				} else {
					$(divClass).attr("style", "color:;");
				}
			} else {
				$(spanId).text('');
			}
		});
	}

	/**
	 * 微博卡片的点赞事件
	 */
	$('#weiboItem').on("click", "#weibo-praise", function(e) {
		var weiboId = e.currentTarget.dataset.id;
		var divClass = ".weibo-praise" + weiboId;
		var spanId = "#wpraise-count" + weiboId;
		var url = addPraiseOfWeiboUrl + '?weiboId=' + weiboId;
		$.getJSON(url, function(data) {
			if (data) {
				shwoWeiboPraise(weiboId);
			} else {
				$.toast({
					text : "点赞失败",
					icon : "error",
					position : "mid-center"
				});
			}
		});
	});

	/**
	 * 展开全文与收起全文
	 */
	$(document).on("click", "#openContent", function(e) {
		openContentClick(e, "#weibo-content", getWeiboContentUrl, 1);
	});
	$(document).on("click", "#closeContent", function(e) {
		closeContentClick(e, "#weibo-content", getWeiboContentUrl, 70, 1);
	});

	/**
	 * 微博卡片中的点击事件
	 */
	$(document).on("click", ".arrow", function(event) {
		var weiboId = event.currentTarget.dataset.id;
		event.stopPropagation();
		var id = "#arrow-context" + weiboId
		$(id).toggle();
	});
	/* 评论部分 */
	$(document).on("click", "#comment-icon", function(e) {
		var weiboId = e.currentTarget.dataset.id;
		var id = "#comment-div" + weiboId
		$(id).slideToggle();
	});

	// 判断两用户关系，返回顺向关注则ture，反之则反
	function isUserRelation(tempUserId, starsId) {
		var url = getRelationUrl + starsId;
		var flag = false;
		$.ajax({
			url : url,
			data : {
				userId : tempUserId
			},
			type : 'post',
			async : false,
			success : function(data) {
				if (data.success) {
					var state = data.state;
					if (state == 0 || state == 1) {
						flag = true;
					} else {
						flag = false;
					}
				}
			}
		});
		return flag;
	}

	// 判断用户关系，返回不同的关注html值
	function showHtmlByIsRelation(tempUserId, tempToUserId) {
		var url = getRelationUrl + tempToUserId;
		var html = '';
		$.ajax({
			url : url,
			data : {
				userId : tempUserId
			},
			type : 'post',
			async : false,
			success : function(data) {
				if (data.success) {
					var state = data.state;
					if (state == 0) {
						$(".follow").text("✔已关注");
						$(".follow").css("background-color", "#676c76")
						$(".follow").attr("id", "cancelRelation");
					} else if (state == 1) {
						$(".follow").text("互相关注");
						$(".follow").css("background-color", "#676c76")
						$(".follow").attr("id", "cancelRelation");
					} else if (state == 2) {

					} else {
						$(".follow").text("+关注");
						$(".follow").css("background-color", "#FA7D3C")
						$(".follow").attr("id", "addRelation");
					}
				} else {
					$.toast({
						text : 'errMsg :' + data.errMsg
					});
				}
			}
		});
		return html;
	}
	// 为取消关注赋予明星Id
	var starsIdOfMy=0;
	var starsIdOfTa=0;
	var starsIdOfFans=0;
	// 为移除粉丝赋予粉丝Id
	var fansId=0;	
	//删除微博
	var deleteWeiboId=0;
	$(".alert-remove").click(function() {
		emptyIdAndHide();
	})
	$(".delete-cancel").click(function() {
		emptyIdAndHide();
	});
	function emptyIdAndHide(){
		starsIdOfMy=0;
		starsIdOfTa=0;
		starsIdOfFans=0;
		fansId=0;	
		deleteWeiboId=0;
		$(".alert-delete").hide();
	}
	/**
	 * 关注页面中-我对我关注的用户的取消关注
	 */
	$(document).on('click', "#cancel-relation", function(e) {
		    starsIdOfMy = e.currentTarget.dataset.id;
		    showTipsContent("确定要取消关注吗？");
	});
	
	/**
	 * 我对他人主页的取消关注
	 */
	$(document).on('click', "#cancelRelation", function(e) {
			if ($.trim(toUserId) != '') {
				starsIdOfTa = toUserId;
			} else {
				starsIdOfTa = e.currentTarget.dataset.id;
			}
			showTipsContent("确定要取消关注吗？");
	});
	/**
	 * 粉丝页面中-我对我关注的用户的取消关注
	 */
	$(document).on('click', "#fans-cancel-relation", function(e) {
			starsIdOfFans = e.currentTarget.dataset.id;
			showTipsContent("确定要取消关注吗？");
	});
	/**
	 * 移除粉丝弹框事件
	 */
	$(document).on("click",".delete-fans",function(e){
		fansId = e.currentTarget.dataset.id;
		showTipsContent("确定要移除该粉丝吗？");
	});
	/**
	 * 删除微博
	 */
	$(document).on('click', "#deleteWeibo", function(e) {
			deleteWeiboId = e.currentTarget.dataset.id;
			showTipsContent("确定要删除该微博吗？");
	});
	/**
	 * 显示弹框事件
	 */
	function showTipsContent(tipsContent){
		$(".alert-delete").show();
		$(".tips-text").text(tipsContent);
	}
	/**
	 * 粉丝页面中-弹框的yes事件
	 */
	$(document).on("click",".delete-yes",function(){
		if(fansId>0){
			var url = cancelRelationUrl + '?userId=' + userId+'&fansId='+fansId;
			cancelRelation(0, url);
		}else if(starsIdOfTa>0){
			cancelRelation(starsIdOfTa, cancelRelationUrl);
		}else if(starsIdOfFans>0){
			var url = cancelRelationUrl + '?userId=' + userId;
			cancelRelation(starsIdOfFans, url);
		}else if(starsIdOfMy>0){
			var url = cancelRelationUrl + '?userId=' + userId;
			cancelRelation(starsIdOfMy, url);
		}else if(deleteWeiboId>0){
			deleteWeibo(deleteWeiboId);
		}
		emptyIdAndHide();
	});
	/**
	 * 粉丝页面中-我对我的粉丝用户的添加关注
	 */
	$(document).on('click', "#add-relation", function(e) {
		var starsId = e.currentTarget.dataset.id;
		var url = addRelationUrl + '?userId=' + userId;
		addRelation(starsId, url);
	});
	/**
	 * 关注页面中-我对他人主页用户的添加关注
	 */
	$(document).on('click', "#hisstars-add-relation", function(e) {
		var starsId = e.currentTarget.dataset.id;
		var url = addRelationUrl + '?userId=' + userId;
		addRelation(starsId, url);
	});

	function cancelRelation(starsId, url) {
		var formData = new FormData();
		formData.append('starsId', starsId);
		$.ajax({
			url : url,
			type : 'post',
			data : formData,
			contentType : false,
			processData : false,
			success : function(data) {
				if (data.success) {
					$.toast({
						text : "取消成功",
						icon : "success"
					});
					getStarsOfHomePage();
					getFansOfHomePage();
					getHomePageInfo();
					initAddWeiboItems();
				} else {
					$.toast({
						text : "取消失败",
						icon : "error"
					});
				}
			}
		});
	}

	/**
	 * 添加关注
	 */
	$(document).on('click', "#addRelation", function(e) {
		if ($.trim(toUserId) != '') {
			var starsId = toUserId;
		} else {
			var starsId = e.currentTarget.dataset.id;
		}
		addRelation(starsId, addRelationUrl);
	});
	function addRelation(starsId, url) {
		var formData = new FormData();
		formData.append('starsId', starsId);
		$.ajax({
			url : url,
			type : 'post',
			data : formData,
			contentType : false,
			processData : false,
			success : function(data) {
				if (data.success) {
					$.toast({
						text : "关注成功",
						icon : "success"
					});
					getStarsOfHomePage();
					getFansOfHomePage();
					getHomePageInfo();
					initAddWeiboItems();
				} else {
					$.toast({
						text : "关注失败",
						icon : "error"
					});
				}
			}
		});
	}

	/**
	 * 头像更换
	 */
	$('#file0').change(function() {
		// 使用JQ的ajax
		// 结合formData实现图片预览
		var formData = new FormData();
		formData.append('headImg', this.files[0]);
		$.ajax({
			url : modifyHeadImgUrl,
			data : formData,
			type : 'post',
			// ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
			contentType : false,
			// 取消帮我们格式化数据，是什么就是什么
			processData : false,
			success : function(data) {
				// console.log(data);
				if (data.success) {
					$.toast({
						text : '头像更改成功 ~',
						icon : 'success'
					});
					$('#my-headImg').attr("src", data.headImg);
					initAddWeiboItems();
				} else {
					$.toast({
						text : '头像更改失败 ~' + data.errMsg,
						icon : 'error'
					});
				}
			}
		});
	});

	
	/**
	 * 删除微博
	 */
	function deleteWeibo(weiboId){
		var formData = new FormData();
		formData.append('weiboId', weiboId);
		$.ajax({
			url : deleteWeiboUrl,
			type : 'post',
			data : formData,
			contentType : false,
			processData : false,
			success : function(data) {
				if (data.success) {
					$.toast({
						text : "删除成功",
						icon : "success",
						position : "mid-center"
					});
					initAddWeiboItems();
					getHomePageInfo();
				} else {
					$.toast({
						text : "删除失败",
						icon : "error"
					});
				}
			}
		});
	}

});