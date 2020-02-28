$(function() {
	var userId = getQueryString('userId');
	var user = '';
	// 获取mine页面数据路径
	var getMineInfoUrl = '/weibo/frontend/getmineinfo?userId=' + userId;
	// 发布微博路径
	var publishWeiboUrl = '/weibo/frontend/publishweibo';
	// 获取我的微博列表
	var getWeiboInfoUrl = '/weibo/frontend/getweiboinfoonmine';
	// 分页允许最大条数，超过此数则禁止访问后台
	var maxItems = 200;
	// 一页加载最大条数
	var pageSize = 10;
	// 页码
	var pageNum = 1;
	// 搜索查询微博路径
	var searchUrl = '/weibo/frontend/getweibolistbysearch';
	// 取消关注
	var cancelRelationUrl = '/weibo/frontend/canceluserrelation';
	// 添加关注
	var addRelationUrl = '/weibo/frontend/adduserrelation';
	// 删除微博
	var deleteWeiboUrl = '/weibo/frontend/deleteweibo';
/*
 * // 获取微博内容 var getWeiboContentUrl = '/weibo/frontend/getweibocontent' +
 * '?weiboId=';
 */
	// 实现微博点赞
	/* var addPraiseOfWeiboUrl = '/weibo/frontend/addpraiseofweibo'; */
	/*
	 * // 获取点赞状态 var getPraiseListUrl = '/weibo/frontend/getpraiseList'; //
	 * 获取该用户昵称的用户Id var getUserByNickNameUrl =
	 * '/weibo/frontend/getuserbynickname'
	 */
	// 将搜索的微博定义为全局，方便分页，即查询第二页及后面时
	var weibo = {};
	// 判断是否为搜索
	var type="weibo";

	/**
	 * 初始化mine页面数据，包括Id,放在最前
	 */
	getMineInfo();
	/**
	 * 获取微博列表信息
	 */
	var frist=true;
	initAddWeiboItems();

	/**
	 * 让addWeiboItems(pageNum, pageSize);回到初始状态
	 */
	function initAddWeiboItems(){
//		$.toast({text:"11type:"+type});
		emptyAddWeiboDiv();
		if(type=="weibo"){
			pageNum = 1;
			addWeiboItems(pageNum, pageSize);
		}else if(type=="search"){
			initAddWeiboItemsOfSearch();
		}else if(type=="hotweibo"){
			initAddHotWeiboItems();
		}else if(type=="newweibo"){
			initAddNewWeiboItems();
		}
	}
	
	function emptyAddWeiboDiv(){
		$("#weiboItem").empty();
		$("#hotweibo").empty();
		$("#newweibo").empty();
		$("#collectionItem").empty();
		$("#myPraiseItem").empty();
	}
	
	function initAddWeiboItemsOfSearch(){
		pageNum=1;
		emptyAddWeiboDiv();
		addWeiboItemsOfSearch(pageNum, pageSize);
	}
	
	function getMineInfo() {
		$.ajax({
			url : getMineInfoUrl,
			data : {},
			type : 'get',
			async : false,
			success : function(data) {
				if (data.success) {
					user = data.user;
					// 将当前userId赋给全局
					userId = user.userId;
					/**
					 * 用户部分
					 */
					if (user.nickName) {
						$('#nav-nickName').text(user.nickName);
						$('#bottom-nickName').text(user.nickName);
					}
					$('#nav-nickName').attr('href',
							'homepage?userId=' + user.userId);
					$('#bottom-nickName').attr('href',
							'homepage?userId=' + user.userId);
					$('#starsCount').text(data.starsCount);
					$('#fansCount').text(data.fansCount);
					$('#weiboCount').text(data.weiboCount);
					if (user.headImg) {
						$('#img0').attr("src", user.headImg);
					}
					$("#a-headImg").attr("href",
							"homepage?userId=" + user.userId);
					$("#starsCount").attr("href",
							"homepage?userId=" + user.userId + '&state=stars');
					$("#fansCount").attr("href",
							"homepage?userId=" + user.userId + '&state=fans');
					$("#weiboCount").attr("href",
							"homepage?userId=" + user.userId);

					$("#notify-atMy").attr(
							'href',
							'mynotify?userId=' + user.userId + '&' + 'action='
									+ 1);
				} else {
//					$.toast({
//						text : "获取用户数据出错"
//					});
				}
			}
		});
	}

	$(".btn-frontend").click(function(){
		$("#mid-frontend").show();
		$("#mid-collection").hide();
		$("#mid-mypraise").hide();
		$(".mid-hotweibo").hide();
		$(".mid-newweibo").hide();
		frist=false;
		type="weibo";
		initAddWeiboItems();
	});
	
	$(".btn-mypraise").click(function(){
		$("#mid-frontend").hide();
		$("#mid-collection").hide();
		$("#mid-mypraise").show();
// $('#weiboItem').empty();
		$(".mid-hotweibo").hide();
		$(".mid-newweibo").hide();
		frist=false;
		getPraiseListByMe();
	});
	
	$(".btn-collection").click(function(){
		$("#mid-frontend").hide();
		$("#mid-collection").show();
		$("#mid-mypraise").hide();
		$(".mid-hotweibo").hide();
		$(".mid-newweibo").hide();
		frist=false;
		getCollectionList();
	});
	
	$(".btn-hotweibo").click(function(){
		frist=false;
		getInItGetHotWeiboList();
	});
	
	function getInItGetHotWeiboList(){
		$("#mid-frontend").hide();
		$("#mid-collection").hide();
		$("#mid-mypraise").hide();
		$(".mid-hotweibo").show();
		$(".mid-newweibo").hide();
		frist=false;
		initAddHotWeiboItems();
	}
	
	function initAddNewWeiboItems(){
		emptyAddWeiboDiv();
		pageNum=1;
		type="newweibo";
		addNewWeiboItems(pageNum,pageSize);
	}
	
	function initAddHotWeiboItems(){
		emptyAddWeiboDiv();
		pageNum=1;
		type="hotweibo";
		addHotWeiboItems(pageNum,pageSize);
	}
	
	$(".btn-newweibo").click(function(){
		frist=false;
		getInItGetNewWeiboList();
	});
	function getInItGetNewWeiboList(){
		$("#mid-frontend").hide();
		$("#mid-collection").hide();
		$("#mid-mypraise").hide();
		$(".mid-hotweibo").hide();
		$(".mid-newweibo").show();
		frist=false;
		initAddNewWeiboItems();
	}
	
	
	/**
	 * 显示热门微博
	 *//*
		 * function getHotWeiboList(pageIndex,pageSize){ var getHotWeiboListUrl =
		 * '/weibo/frontend/gethotweibolist'+'?pageIndex='+pageIndex+'&pageSize='+pageSize;
		 * $.getJSON(getHotWeiboListUrl, function(data) { if (data.success) {
		 * var hotWeiboHtml=getWeiboListHtml(data.hotWeiboList);
		 * $("#hotweibo").appden(hotWeiboHtml); } }); }
		 */
	
	
	/**
	 * 显示我收藏的微博
	 */
	function getCollectionList(){
		type="collection";
		var getCollectionListUrl = '/weibo/frontend/getcollectionlist';
		$.getJSON(getCollectionListUrl, function(data) {
			if (data.success) {
				var weiboHtml=getWeiboListHtml(data.weiboList,"collection");
				emptyAddWeiboDiv();
				$("#collectionItem").html(weiboHtml);
				$(".collection-count").text(data.count);
			} else {
				$.toast({
					text : "后台获取微博卡片出错"
				});
			}
		});
	}
	
	/**
	 * 显示我赞过的微博
	 */
	function getPraiseListByMe(){
		type="praise";
		var getPraiseListByMeUrl = '/weibo/frontend/getpraiselistbyme';
		$.getJSON(getPraiseListByMeUrl, function(data) {
			if (data.success) {
				var weiboHtml=getWeiboListHtml(data.weiboList,"praise");
				emptyAddWeiboDiv();
				$("#myPraiseItem").html(weiboHtml);
			} else {
				$.toast({
					text : "后台获取微博卡片出错"
				})
			}
		});
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
						if(type=="search"){
							addWeiboItemsOfSearch(pageNum, pageSize);
						}else if(type=="hotweibo"){
							addHotWeiboItems(pageNum, pageSize);
						}else if(type=="newweibo"){
							addNewWeiboItems(pageNum, pageSize);
						}else{
							addWeiboItems(pageNum, pageSize);
						}
					}
				}
			});
	/**
	 * 热门微博卡片的无限滚动加载
	 */
	function addHotWeiboItems(pageIndex, pageSize) {
		var getHotWeiboListUrl = '/weibo/frontend/gethotweibolist'+'?pageIndex='+pageIndex+'&pageSize='+pageSize; 
		$.getJSON(getHotWeiboListUrl, function(data) {
			if (data.success) {
				maxItems = data.weiboCount;
				// 获取当前已经加载的所有微博总数
				var total = $("#hotweibo").find(".article").length;
				if (total >= maxItems) {
					$(".ajaxtips").hide();
					$(".load-end").show();
					return;
				}else if(total==0){
					$(".load-end").show();
				}else{
					$(".ajaxtips").show();
				}
				var weiboHtml =getWeiboListHtml(data.hotWeiboList);

				$('#hotweibo').append(weiboHtml);
				
				pageNum++;// 当前要加载的页码
				stop = true;
			} else {
// $.toast({
// text : "后台获取微博卡片出错"
// })
			}
		});
	}
	
	/**
	 * 最新微博卡片的无限滚动加载
	 */
	function addNewWeiboItems(pageIndex, pageSize) {
		var getNewWeiboListUrl = '/weibo/frontend/getnewweibolist'+'?pageIndex='+pageIndex+'&pageSize='+pageSize; 
		$.ajax({
			url : getNewWeiboListUrl,
			data : {},
			type : 'get',
			async : false,
			success : function(data) {
				if (data.success) {
				maxItems = data.weiboCount;
				// 获取当前已经加载的所有微博总数
				
				var total = $("#newweibo").find(".article").length;
				if (total >= maxItems) {
					$(".ajaxtips").hide();
					$(".load-end").show();
					return;
				}else if(total==0){
					$(".load-end").show();
				}else{
					$(".ajaxtips").show();
				}
				var weiboHtml =getWeiboListHtml(data.newWeiboList);

				$('#newweibo').append(weiboHtml);
				
				pageNum++;// 当前要加载的页码
				stop = true;
			} else {
// $.toast({
// text : "后台获取微博卡片出错"
// })
			}
			}
		});
	}
	
	/**
	 * 显示热门微博
	 */
	function getHotWeiboList(pageIndex,pageSize){
			
			$.getJSON(getHotWeiboListUrl, function(data) {
				if (data.success) {
					var hotWeiboHtml=getWeiboListHtml(data.hotWeiboList);
					$("#hotweibo").appden(hotWeiboHtml);
				}
			});
		}
	/**
	 * 微博卡片的无限滚动加载
	 */
	function addWeiboItems(pageIndex, pageSize) {
		var url = getWeiboInfoUrl + '?pageIndex=' + pageIndex + '&pageSize='
				+ pageSize;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.weiboCount;
				if(maxItems==0&&frist){
					frist=false;
					$.toast({text:"您的首页还没有微博，以下是热门微博",icon:"info"});
					getInItGetHotWeiboList();
					return;
				}
				// 获取当前已经加载的所有微博总数
				var total = $("#weiboItem").find(".article").length;
				if (total >= maxItems) {
					$(".ajaxtips").hide();
					$(".load-end").show();
					return;
				}else if(total==0){
					$(".load-end").show();
				}else{
					$(".ajaxtips").show();
				}
				var weiboList = data.weiboList;
				// 初始微博为小于等于三条的话添加条件重新查询
// if(pageNum==1&&weiboList.length<3){
// var tempSize = pageSize+3;
// addWeiboItems(pageNum,tempSize);
// return;
// }
				var weiboHtml	=getWeiboListHtml(weiboList);
				$('#weiboItem').append(weiboHtml);
				
				pageNum++;// 当前要加载的页码
				stop = true;
			} else {
// $.toast({
// text : "后台获取微博卡片出错"
// })
				
			}
		});
	}

	function getWeiboListHtml(list,type) {
		var weiboHtml = '';

		list
				.map(function(item, index) {
					var str = item.content.substr(item.content.indexOf("\@"));
					var atNickName = str.substring(0, str.indexOf("\ "));

					// 获取优化后的内容
					var contentHtml = contentHtml = initContentHtml(
							item.weiboId, "#weibo-content", item.content, 70, 1);
					// 查询该用户昵称的所属用户Id
					var targetId = getUserIdByNickName(str, atNickName);

					// 获取微博点赞的状态并给对应div设置点赞效果
					shwoWeiboPraise(item.weiboId);
					
					// 获取微博收藏的状态并给对应div设置效果
					shwoWeiboCollection(item.weiboId);

					
					// 判断userId与toUserId是否不同，不同才赋值两个否则一个
					var toUserId = item.author.userId;
					if (userId == toUserId) {
						toUserId = '';
					}

					// 调用自定义方法获取微博列表的Html
					weiboHtml += `<div class="article" data-id="${item.weiboId}">
					        <div class="article-header">
							<!-- 点击图片跳转到页面 --><div><a href="homepage?userId=${userId}&toUserId=${toUserId}">
							<img src="${item.author.headImg}" /></a>
							</div><div class="article-info"><div class="article-user">
							<a href="homepage?userId=${userId}&toUserId=${toUserId}">
							 ${item.author.nickName} 
							</a></div><div class="article-time">
							<a  id="weibo-publishTime">
							 ${FormatAllDateToYMDHMS(item.publishTime)}
							</a>
							</div></div><div class="arrow" data-id="${item.weiboId}"><span class="iconfont" 
							>&#xe625;</span></div><div class="arrow-context" 
							id="arrow-context${item.weiboId}">
							<ul>
							<li id="relation-li">
							<!--查詢關係返回相應的html-->
							 ${getIsRelationHtml(item.author.userId,
									item.weiboId)}
							</li>
							</ul></div></div><!-- 文章部分 -->
							<div class="article-context">						
							<div class="article-context-title" >
							<a href="homepage?userId=${userId}&toUserId=${targetId}" 
							style="color: #FA7D3C;text-decoration: none;">
							 ${atNickName}
							</a>
							<span id="weibo-content${item.weiboId}">
							 ${contentHtml}
							</span>
							</div>
							<div class="article-context-img">
							 ${showWeiboImg(item.weiboImgList,item.weiboId)}
							</div></div>
							 ${getBigImgTipHtml(item.weiboId)}
							<!-- 底部 --><div class="article-footer">
							<div id="checkCollection" data-id="${item.weiboId}" 
							class="checkCollection${item.weiboId}" data-type=${type}>
							<span class="iconfont">&#xe641;</span>
							<span class="footer-font collection-text${item.weiboId}">
							收藏</span></div><div><a style="text-decoration:none;color:#808080;font-size: 12px;" href="weibodetail?userId=${userId}&weiboId=${item.weiboId}">
							 <span class="iconfont">&#xe62d;</span>查看微博</a></div>
							<div class="btn-comment" data-id="${item.weiboId}">
							<span class="iconfont">&#xe62f;</span>
							<span class="footer-font" >评论&nbsp;${showWeiboCommentCount(item.weiboId)}</span></div>
							<!-- 点赞模块 -->
							<div id="weibo-praise" data-id="${item.weiboId}" data-type="${type}"
							class="weibo-praise${item.weiboId}">
							<span class="iconfont" >&#xe639;</span><span 
							class="footer-font wpraise-count${item.weiboId}">4999</span></div>
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
							<!-- 评论模块结束 --> </div>
						    `;
				});
        $(".ajaxtips").hide();
        return weiboHtml;

	}

	/**
	 * 微博卡片的收藏事件
	 */
	$(document).on("click", "#checkCollection", function(e) {
		checkCollection(e);
//		console.log(e.currentTarget.dataset.type);
		if(e.currentTarget.dataset.type=="collection"){
				setTimeout(function(){
					getCollectionList();
				},100);
		}
	});
	
	
	/**
	 * 微博卡片的点赞事件
	 */
	$(document).on("click", "#weibo-praise", function(e) {
		praiseWeibo(e);
		console.log(e.currentTarget.dataset.type);
		if(e.currentTarget.dataset.type=="praise"){
			setTimeout(function(){
				getPraiseListByMe();
			},100);
	  }
	});
	
	/**
	 * 搜索微博
	 */
	// 尝试获取从别的页面传过来的关键字
	var searchKey = getQueryString('searchKey');
	if(!$.trim(searchKey) == ''){
		$('#autocomplete').val(searchKey);
		getWeiboBySearch();
	}
	// 在输入框内点击回车键进行搜索
	$("#autocomplete").keypress(function(event) {
		var keynum = (event.keyCode ? event.keyCode : event.which);
		if (keynum == '13') {
			getWeiboBySearch();
		}
	});

	// 点击弹出框的内容
	$(document).on("click",".ui-menu .ui-menu-item a",function(){
		getWeiboBySearch();
	});
	// 点击搜索键进行搜索
	$('#search-btn').click(function() {
		getWeiboBySearch();
	});
	function getWeiboBySearch() {
		if (!$.trim($('#autocomplete').val()) == '') {
			// 先让首页显示
			$("#mid-frontend").show();
			$("#mid-collection").hide();
			$("#mid-mypraise").hide();
			
			weibo.content = $('#autocomplete').val();

			// 清空输入框内容
// $('#autocomplete').val('');
			pageNum=1;
			emptyAddWeiboDiv();
			addWeiboItemsOfSearch(pageNum,pageSize);
			// 设置为搜索
			type="search";
		} else {
			/* $.toast({text:"请输入搜索内容",icon:"info",position:"mid-center"}); */
		}
	}
	function addWeiboItemsOfSearch(pageIndex,pageSize){
		var formData = new FormData();
		formData.append("weiboConditionStr", JSON.stringify(weibo));
		$.ajax({
			url : searchUrl+ '?pageIndex=' + pageIndex + '&pageSize='
			+ pageSize,
			data : formData,
			type : 'post',
			contentType : false,
			processData : false,
			success : function(data) {
				if (data.success) {
					var weiboCount = data.weiboCount;
					if (weiboCount <= 0) {
						$.toast({
							text : "没有任何相关微博呢",
							icon : "info",
							position : "mid-center"
						});
						$(".load-end").show();

						return;
					}
					
					maxItems = weiboCount;
					// 获取当前已经加载的所有微博总数
					var total = $("#weiboItem").find(".article").length;
					if (total >= maxItems) {
						$(".ajaxtips").hide();
						$(".load-end").show();
						return;
					}else if(total==0){
						$(".load-end").show();
					}else{
						$(".ajaxtips").show();
					}
					var weiboList = data.weiboList;
					// 初始微博为小于等于三条的话添加条件重新查询
					/*
					 * if(pageNum==1&&weiboList.length<=3){ var
					 * size=pageSize+1; addWeiboItemsOfSearch(pageNum,size);
					 * return; }
					 */
					var weiboHtml=getWeiboListHtml(weiboList);
		            $("#weiboItem").append(weiboHtml);
					
					pageNum++;// 当前要加载的页码
					stop = true;
				} else {
					$.toast({
						text : "没有任何相关微博呢",
						icon : "error",
						position : "mid-center"
					});
					emptyAddWeiboDiv();
				}
			}
		});
	}
	
	/**
	 * 展开全文与收起全文
	 */
	$(document).on("click", "#openContent", function(e) {
		openContentClick(e, "#weibo-content", getWeiboContentUrl, 1);
	});
	$(document).on("click", "#closeContent", function(e) {
		closeContentClick(e, "#weibo-content", getWeiboContentUrl, 70, 1);
	});

	// 针对微博图片控件组，若该控件组的最后一个元素发送变化（即上传了图片(change)），
	// 且控件总数未达到9个(length<9)，则生成新的一个文件上传控件
	/*
	 * $('.content-middle-all').on( 'change', '.publish-img:last-child',
	 * function() { if ($('.publish-img').length < 9) {
	 * $('#detail-imgs').append( '<input type="file" class="publish-img">'); }
	 * });
	 */

	/**
	 * 微博发布
	 */
	$('#publish').click(
			function(e) {
				var weibo = {};
				var formData = new FormData();
				weibo.content = $('#content').val();
				// 获取用户昵称（@至第一个空之间的内容）
				var str = weibo.content.substr(weibo.content.indexOf("\@"));
				var atNickName = str.substring(0, str.indexOf("\ "));
				formData.append('atNickName', atNickName);
				// $.toast({text:"内容："+atNickName});

				if ($.trim(weibo.content) == ''&&$('.weibo-img')[0].files.length<=0) {
					$.toast({
						text : "微博内容或图片不能同时为空",
						icon : "info",
						position : "mid-center"
					});
					if ($.toast != null) {
						setTimeout(function() {
							$(".jq-toast-wrap").remove();
						}, 2500);
					}
					return;
				}else if($.trim(weibo.content) == ''&&$('.weibo-img')[0].files.length>0){
					weibo.content="分享图片";
				}
				// 遍历微博图控件，获取里面的文件流
				$('.weibo-img').map(
						function(index, item) {
							// 判断该控件是否已选择了文件
							if ($('.weibo-img')[index].files.length > 0) {
								// 将第i个文件流赋值给key为weiboImgi的表单键值对里
								formData.append('weiboImg' + index,
										$('.weibo-img')[index].files[0]);
							}
						});
				formData.append('weiboStr', JSON.stringify(weibo));
				// 发送成功后把内容清除
				$('.all-upimg').html(
						`<span class="all-upimg-title">本地上传<span class="all-upimg-delete">x</span></span>
					
						<label for="upimg-1" id="label-1">
							<div>
								<span class="delete-img" data-id="1">x</span>
								<div class="special-img2">+</div>
								<img src="" class="upimg-1">
							</div>
						</label>
						<input type="file" id="upimg-1" class="weibo-img" data-id="1"/>`);
				$('#content').val('');
				$(".all-upimg").hide();
				
				$.ajax({
					url : publishWeiboUrl,
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
								text : '微博发布成功 ~',
								icon : 'success'
							});
							getMineInfo();
							initAddWeiboItems();
						} else {
							$.toast({
								text : data.errMsg,
								icon : 'error'
							});
						}
					}
				});
			});
	
	/**
	 * 关注时
	 */
	function initWeiboItemsOfClick(starsId,rtype){
//		$.toast({text:"type:"+type});
		frist=false;
		if(type=="weibo"){
			emptyAddWeiboDiv();
			pageNum = 1;
			addWeiboItems(pageNum, pageSize);
		}else{
			if(rtype=="addRelation"){
				var cancelHtml = '<a id="cancelRelation" data-id="' + starsId
				+ '" style="cursor: pointer;">取消关注</a>';
				$("#relation-li").html(cancelHtml);
			}else{
				var addHtml = '<a id="addRelation" data-id="' + starsId
				+ '" style="cursor: pointer;">添加关注</a>';
				$("#relation-li").html(addHtml);
			}
			
		}
	}

	/**
	 * 添加关注
	 */
	$(document).on('click', "#addRelation", function(e) {
		var starsId = e.currentTarget.dataset.id;
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
					getMineInfo();
//					initAddWeiboItems();
					initWeiboItemsOfClick(starsId,"addRelation");
				} else {
					$.toast({
						text : "关注失败",
						icon : "error"
					});
				}
			}
		});
	});

	/**
	 * 弹框事件
	 */
	var cancelStarsId =0;
	var deleteWeiboId=0;
	$(".alert-remove").click(function() {
		emptyIdAndHide();
	})
	$(".delete-cancel").click(function() {
		emptyIdAndHide();
	});
	function emptyIdAndHide(){
		deleteWeiboId=0;
		cancelStarsId=0;	
		$(".alert-delete").hide();
	}
	
	/**
	 * 删除微博
	 */
	$(document).on('click', "#deleteWeibo", function(e) {
		    $(".alert-delete").show();
		    $(".tips-text").text("确定要删除该微博吗？");
		    deleteWeiboId = e.currentTarget.dataset.id;
	});
	/**
	 * 取消关注
	 */
	$(document).on('click', "#cancelRelation", function(e) {
		 $(".alert-delete").show();
		 $(".tips-text").text("确定要取消关注吗？");
		 cancelStarsId = e.currentTarget.dataset.id;
	});
	
	/**
	 * 弹框的确定事件
	 */
	$(document).on("click",".delete-yes",function(){
		if(deleteWeiboId>0){
			deleteWeibo(deleteWeiboId);
		}else if(cancelStarsId>0){
			cancelRelation(cancelStarsId)
		}
		emptyIdAndHide();
	});
	
	function cancelRelation(starsId){
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
					getMineInfo();
//					initAddWeiboItems();
					initWeiboItemsOfClick(starsId,"cancelRelation");
				} else {
					$.toast({
						text : "取消失败",
						icon : "error"
					});
				}
			}
		});
	}
	
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
					getMineInfo();
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