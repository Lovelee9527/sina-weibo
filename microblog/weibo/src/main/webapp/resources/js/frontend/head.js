$(function(){
//获取mine页面数据路径
var userId = getQueryString('userId');
var getMineInfoUrl = '/weibo/frontend/getmineinfo?userId=' + userId;

if(!(window.location.pathname=="/weibo/frontend/index")){
	getMineInfo();
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
				userId = user.userId;
			} else {
				/*$.toast({
					text : "你还未登录呢，请去登录"
				});*/
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
});
// 获取当前用户的通知列表路径
	var getNotifysUrl = '/weibo/frontend/getnotifysbytargetid';
/**
 * 查询有无未读的通知，有则显示
 */
function getNotifys() {
	$.getJSON(getNotifysUrl, function(data) {
		if (data.success) {
			if (data.notifyCount > 0) {
				$('#new-notify').text(data.notifyCount);
				$('#new-notify').show();
			} else {
				$('#new-notify').hide();
			}
			if (data.atCount > 0) {
				$('#atCount').show();
				$('#atCount').text(data.atCount);
			} else {
				$('#atCount').hide();
			}
			if (data.commentCount > 0) {
				$('#commentCount').show();
				$('#commentCount').text(data.commentCount);
			} else {
				$('#commentCount').hide();
			}
			if (data.replyCount > 0) {
				$('#replyCount').show();
				$('#replyCount').text(data.replyCount);
			} else {
				$('#replyCount').hide();
			}
			if (data.relationCount > 0) {
				$('#relationCount').show();
				$('#relationCount').text(data.relationCount);
			} else {
				$('#relationCount').hide();
			}
			if (data.praiseCount > 0) {
				$('#praiseCount').show();
				$('#praiseCount').text(data.praiseCount);
			} else {
				$('#praiseCount').hide();
			}
		} else {
			$('#new-notify').hide();
		}
	});
}

$(function head() {
	var userId = getQueryString('userId');
	var toUserId = getQueryString('toUserId');
	// 获取mine页面数据路径
	var getMineInfoUrl = '/weibo/frontend/getmineinfo?userId=' + userId;
	// 退出登录
	var loginOutUrl = '/weibo/frontend/loginout';
	// 模糊查询显示出相关的微博内容或用户名
	var getSearchContentByKeyUrl = '/weibo/frontend/getsearchcontentbykey';

	getMineInfo();
	getNotifys();
	
//	 getSearchContentByInputKey();
	
	// 设置页面2000毫秒查询一次通知表，有新消息则通知
	// var t = setInterval(function() {
	// getNotifys();
	// }, 2000);
	
	/**
	 * 监听搜索框，模糊查询显示出相关的微博内容或用户名
	 */
//	function getSearchContentByInputKey() {
//	$(document).on("change","#autocomplete",function(){
//		if($.trim($("#autocomplete").val()!=="")){
//			$.toast({text:"进来了。。"+$("#autocomplete").val()})
//		}
	$("input[name='search']").bind('input propertychange',function () {
		 if($.trim($(this).val()!=="")){
					var searchKey = $(this).val();
						$.ajax({
							url : getSearchContentByKeyUrl,
							data : {searchKey:searchKey},
							type : 'get',
							async : false,	
							success : function(data) {
//								var searchHtml=`<span style="color:#FA7D3C;">${searchKey}</span>`;
								var weiboArr = ["搜索 “"+searchKey+"” 相关微博"];
//								console.log(weiboArr);

								var tempWeiboArr = weiboArr.concat(data.searchWeibos);
								var nickNameArr = ["搜索 “"+searchKey+"” 相关用户的微博 >>"];
								$(".ui-menu .ui-menu-item a:first-child").attr("style","color: #F7691D;");
								var tempNickNameArr = nickNameArr.concat(data.searchNickNames);

								if (data.success) {
									var availableTags =tempWeiboArr.concat(tempNickNameArr) ;
									console.log(availableTags);
									$("#autocomplete").autocomplete({
										source : availableTags
									});
								}
							}
						});
			}	
	 });
//	});
//	}
	 
	
	function getMineInfo() {
		$.ajax({
			url : getMineInfoUrl,
			data : {},
			type : 'get',
			async : false,
			success : function(data) {
				if (data.success) {
					var user = data.user;
					userId = user.userId;
					if (user.nickName) {
						$('#nav-nickName').text(user.nickName);
					}
					$('#nav-nickName').attr('href',
							'homepage?userId=' + user.userId);

					$("#list-special").attr("href","mine?userId="+user.userId);
					
					/**
					 * 当前为首页则给首页设置color
					 */
//					$.toast({text:"page:"+window.location.pathname})
					if($.trim(window.location.pathname)=='/weibo/frontend/mine'){
						$("#list-special").attr("style","color: #FA7D3C;");
					}else{
						$("#list-special").attr("style","color: #696e78;");
					}
					
					
					// 把该页面的用户Id赋给userid
					// $('#publish').attr('data-userid',user.userId);
				} else {
//					$.toast({
//						text : "加载出错"
//					});
				}
			}
		});
	}

		
	/**
	 * 跳转到消息页面
	 */
	function toMynotify(action) {
		var url = '/weibo/frontend/mynotify' + '?' + 'userId=' + userId + '&'
				+ 'action=' + action;
		window.location.href = url;
	}
	$('#div-atMy').click(function() {
		// var url = modifyNotifyUrl + '?action=' + 1;
		// modifyNotify(url);
		toMynotify(1);
	});
	$('#div-comment').click(function() {
		toMynotify(2);
	});
	$('#div-reply').click(function() {
		toMynotify(3);
	});
	$('#div-relation').click(function() {
		toMynotify(4);
	});
	$('#div-praise').click(function() {
		toMynotify(5);
	});

	// 沒有新的通知時設置隱藏
	$('#new-notify').hide();

	// 实现微博右上角鼠标移开时隐藏
	$(document).on("mouseleave", ".arrow-context", function() {
		$(".arrow-context").hide();
	});
	// 实现微博右上角鼠标点击其他地方时隐藏
	$(window).click(function() {
		$(".arrow-context").hide();
	});

	// 搜索框
	$("#autocomplete").focus(function() {
		$("#autocomplete").css("background-color", "#fff")
	});
	$("#autocomplete").blur(function() {
		$("#autocomplete").css("background-color", "#f2f2f5")
	});

	// 信封内容
	$("#letter-icon").click(function(event) {
		event.stopPropagation();
		$(".letter-context").toggle();
	});
	$(".letter-context").click(function(event) {
		event.stopPropagation();
		$(".letter-context").show();
	});
	// 实现信封鼠标移开时隐藏
	$(document).on("mouseleave", ".letter-context", function() {
		$(".letter-context").hide();
	});
	// 实现信封鼠标点击其他地方时隐藏
	$(window).click(function() {
		$(".letter-context").hide();
	});

	// 设置内容
	$("#set-icon").click(function(event) {
		event.stopPropagation();
		$(".set-context").toggle();
	});
	$(".set-context").click(function(event) {
		event.stopPropagation();
		$(".set-context").show();
	});
	// 实现设置鼠标移开时隐藏
	$(document).on("mouseleave", ".set-context", function() {
		$(".set-context").hide();
	});
	// 实现设置鼠标点击其他地方时隐藏
	$(window).click(function() {
		$(".set-context").hide();
	});

	// 退出登录
	$('#loginOut').click(function() {
		$(".alert-delete").show();
		$(".tips-text").text("确定要退出吗？");
		$(".delete-yes").click(function(){
			$.getJSON(loginOutUrl, function(data) {
				if (data.success) {
					$.toast({
						text : '退出成功 ~',
						icon : 'success'
					});
					setTimeout(function() {
						window.location.href = '/weibo/frontend/index';
					}, 200);
				} else {
					$.toast({
						text : '退出失败 ~' + data.errMsg,
						icon : 'error'
					});
				}
			});
		})
	});
	
		/**
		 * 搜索微博
		 */
		// 在输入框内点击回车键进行搜索
		$("#autocomplete").keypress(function(event) {
			var keynum = (event.keyCode ? event.keyCode : event.which);
			if (keynum == '13') {
//				toFrontendPageForSearch();
			}
		});
		
		$(document).on("focus",".input-search",function(){
			toFrontendPageForSearch();
		});

		//点击弹出框的内容
		$(document).on("click",".ui-menu .ui-menu-item a",function(){
			toFrontendPageForSearch();
		});
		// 点击搜索键进行搜索
		$('#search-btn').click(function() {
			toFrontendPageForSearch();
		});
		//index页面中
		$("#iconfont-search").click(function(){
			toFrontendPageForSearch();
		});
		
	function toFrontendPageForSearch(){
			var pathName = window.location.pathname;
			if(pathName==="/weibo/frontend/index"){
				$(".alert-delete").show();
				$(".tips-text").text("登录后才能搜索");
			}else if(pathName==="/weibo/frontend/homepage"||pathName==="/weibo/frontend/mynotify"||pathName==="/weibo/frontend/weibodetail"){
				$(".alert-delete").show();
				$(".tips-text").text("搜索会跳转到首页进行哦");
				$(document).on("click",".delete-yes",function(){
					var searchKey = $("#autocomplete").val();
					  setTimeout(function(){
							window.location.href='/weibo/frontend/mine'+'?searchKey='+searchKey;
						},300);
			    });
			}
			
	}
	
	$(".alert-remove").click(function(){
		$(".alert-delete").hide();
	});
    $(".delete-cancel").click(function(){
    	$(".alert-delete").hide();
	}); 
	
    //微博发现a标签符上href
    var url="mine?userId="+userId;
    $(".find-weibo").attr("href",url);
});
