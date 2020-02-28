$(function() {
	var state = getQueryString('state');
	var userId = getQueryString('userId');
	var weiboId = getQueryString('weiboId');

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
	
	
	/**
	 * 初始化mine页面数据，包括Id,放在最前
	 */
		initAddWeiboItems();
	
	/**
	 * 让addWeiboItems(pageNum, pageSize);回到初始状态
	 */
	function initAddWeiboItems(){
		$("#weiboItem").empty();
		addWeiboItems();
	}
	
	/**
	 * 微博卡片的无限滚动加载
	 */
	function addWeiboItems() {
		var url = '/weibo/frontend/getweiboofweibodetail'
				+ '?userId=' + userId + '&weiboId='+weiboId;;
		$.getJSON(url, function(data) {
			if (data.success) {
				showWeiboList(data.weibo,data.user);
			} else {
				$(".alert-delete").show();
				$(".tips-text").text("该微博不存在或已删除,返回首页？");
				$(document).on("click",".delete-yes",function(){
					window.location.href='/weibo/frontend/mine?userId='+userId;
			    });
			}
		});
	}
	
	var total = $("#weiboItem").find(".article").length;
	if (total >= 1) {
		$(".ajaxtips").hide();
		$(".load-end").show();
	} else {
		$(".ajaxtips").show();
	}
	
	function showWeiboList(weibo,user) {
		var weiboHtml = '';
					var str = weibo.content.substr(weibo.content.indexOf("\@"));
					var atNickName = str.substring(0, str.indexOf("\ "));

					// 获取优化后的内容
					var contentHtml = initContentHtml(weibo.weiboId,
							"#weibo-content", weibo.content, 70, 1);

					// 查询该用户昵称的所属用户Id
					var targetId = getUserIdByNickName(str, atNickName);

					// 判断userId与toUserId是否不同，不同才赋值两个否则一个
					var toUserId = weibo.author.userId;
					if (userId == toUserId) {
						toUserId = '';
					}

					// 获取微博点赞的状态并给对应div设置点赞效果
					shwoWeiboPraise(weibo.weiboId);
					
					// 获取微博收藏的状态并给对应div设置效果
					shwoWeiboCollection(weibo.weiboId);
					
					// 调用自定义方法获取微博列表的Html
					weiboHtml += `<!-- 一条微博 --><div class="article">
							<div class="article-header">
							<!-- 点击图片跳转到页面 --><div>
							<a href="homepage?userId=${userId}&toUserId=${weibo.author.userId}">
							<img src="${weibo.author.headImg}" /></a>
							</div>
							<div class="article-info">
							<div class="article-user">
							<a href="homepage?userId=${userId}&toUserId=${weibo.author.userId}">
							${weibo.author.nickName}
							</a></div>
							<div class="article-time">
							<a href="homepage?userId=${userId}&toUserId=${weibo.author.userId}">
							${FormatAllDateToYMDHMS(weibo.publishTime)}
							</a>
							</div></div>
							<div class="arrow" data-id="${weibo.weiboId}">
							<span class="iconfont" id="arrow">&#xe625;</span>
							</div>
							<div class="arrow-context" id="arrow-context${weibo.weiboId}">
							<ul>${getArrowHtml(weibo.weiboId,weibo.author.userId)}</ul>
							</div></div>
							<!-- 文章部分 -->
							<div class="article-context">
							<div class="article-context-title">
							<a href="homepage?userId=${userId}&toUserId=${targetId}" style="color: #FA7D3C;
							text-decoration: none;">
							${atNickName}</a>
							<span id="weibo-content${weibo.weiboId}">
							${contentHtml}</span></div>
							<div class="article-context-img">
							${showWeiboImg(weibo.weiboImgList,weibo.weiboId)}
							</div>
							</div>
						    ${getBigImgTipHtml(weibo.weiboId)}
							<!-- 底部 -->
							<div class="article-footer"><div id="checkCollection" data-id="${weibo.weiboId}" 
							class="checkCollection${weibo.weiboId}">
							<span class="iconfont">&#xe641;</span><span 
							class="footer-font collection-text${weibo.weiboId}">收藏</span></div>
							<div><a style="text-decoration:none;color:#808080;font-size: 12px;" href="weibodetail?userId=${userId}&weiboId=${weibo.weiboId}">
							 <span class="iconfont">&#xe62d;</span>查看微博</a></div>
							<div id="comment-icon" class="btn-comment" data-id="${weibo.weiboId}">
							<span class="iconfont">&#xe62f;</span><span class="footer-font">评论&nbsp;${showWeiboCommentCount(weibo.weiboId)}</span>
							</div>
							<!-- 点赞模块 -->
							<div id="weibo-praise" data-id="${weibo.weiboId}" class="weibo-praise${weibo.weiboId}">
							<span class="iconfont" >&#xe639;</span><span class="footer-font" id="wpraise-count${weibo.weiboId}">4999</span></div>
							</div>
							<!-- 评论模块 -->
							<div class="comment comment-div${weibo.weiboId}">
							<div class="comment-first"><div><img src="${user.headImg}" style="width: 32px; height: 32px; margin-top: 15px;z-index:9999; margin-left: 18px;">
							</div><div><input class="comment-input" type="text" style="margin-top: 15px; height: 27px; 
							width: 518px; margin-left: 10px;" 
							id="content1${weibo.weiboId}"/></div><!--disabled--><div><button 
							class="comment-button" id="comment" 
							data-id="${weibo.weiboId}">评论</button></div></div><div class="comment-list comment-list${weibo.weiboId}" style="min-height:0px;display:none;">
							<!-- 评论列表 -->
							<div class="comment-list-context" 
							id="commentItem${weibo.weiboId}"></div> </div> </div>
							<!-- 评论模块结束 --> </div>`;
		$(".ajaxtips").hide();
		$('#weiboItem').append(weiboHtml);

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
	function getArrowHtml(weiboId,authorId) {
		var html = '';
		if (userId == authorId) {
			html = '<li><a id="deleteWeibo" data-id="' + weiboId
					+ '" style="cursor: pointer;">删除微博</a></li>';
		} else {
			html=getIsRelationHtml(authorId,weiboId);
		}
		return html;
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


	//删除微博
	var deleteWeiboId=0;
	var starsIdOfTa=0;
	$(".alert-remove").click(function() {
		emptyIdAndHide();
	})
	$(".delete-cancel").click(function() {
		emptyIdAndHide();
	});
	function emptyIdAndHide(){
		deleteWeiboId=0;
		starsIdOfTa=0;
		$(".alert-delete").hide();
	}

	/**
	 * 我对他人微博上的的取消关注
	 */
	$(document).on('click', "#cancelRelation", function(e) {
		    starsIdOfTa = e.currentTarget.dataset.id;
			showTipsContent("确定要取消关注吗？");
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
		if(deleteWeiboId>0){
			deleteWeibo(deleteWeiboId);
		}else if(starsIdOfTa>0){
			cancelRelation(starsIdOfTa);
		}
		emptyIdAndHide();
	});
	
	function cancelRelation(starsId) {
		// 取消关注
		var cancelRelationUrl = '/weibo/frontend/canceluserrelation';
		var formData = new FormData();
		formData.append('starsId', starsId);
		$.ajax({
			url : cancelRelationUrl,
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
					initWeiboItemsOfClick(starsId,"cancelRelation");
//					initAddWeiboItems();
//					setTimeout(function(){
//						if(weiboId>0){
//							var id = ".comment-div" + weiboId;
//							$(id).slideToggle();
//						}
//					},50);
					
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
		var starsId = e.currentTarget.dataset.id;
		addRelation(starsId);
	});
	function addRelation(starsId) {
		// 添加关注
		var addRelationUrl = '/weibo/frontend/adduserrelation';
		var formData = new FormData();
		formData.append('starsId', starsId);
		$.ajax({
			url : addRelationUrl,
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
					initWeiboItemsOfClick(starsId,"addRelation");
//					initAddWeiboItems();
//					setTimeout(function(){
//						if(weiboId>0){
//							var id = ".comment-div" + weiboId;
//							$(id).slideToggle();
//						}
//					},50);
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
	 * 关注时
	 */
	function initWeiboItemsOfClick(starsId,rtype){
//		$.toast({text:"type:"+type});
			if(rtype=="addRelation"){
				var cancelHtml = '<ul><li><a id="cancelRelation" data-id="' + starsId
				+ '" style="cursor: pointer;">取消关注</a></ul></li>';
				$(".arrow-context").html(cancelHtml);
			}else{
				var addHtml = '<ul><li><a id="addRelation" data-id="' + starsId
				+ '" style="cursor: pointer;">添加关注</a></ul></li>';
				$(".arrow-context").html(addHtml);
			}

	}
	
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