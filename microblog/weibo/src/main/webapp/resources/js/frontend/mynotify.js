$(function() {
	var action = getQueryString('action');
	var userId = getQueryString('userId');
	var nickName = '';
	var headImg = '';
	// 获取mine页面数据路径
	var getMineInfoUrl = '/weibo/frontend/getmineinfo?userId=' + userId;
	// 获取当前用户的通知列表路径
	var getNotifysUrl = '/weibo/frontend/getnotifysbytargetid';
	// 将通知消息的未读改为已读(isRead变为1)
	var modifyNotifyUrl = '/weibo/frontend/modifynotifyisread';
	// 获取我的数据
	var getMyNotifyInfoUrl = '/weibo/frontend/getmynotifyinfo' + '?userId='
			+ userId; 
	// 获取微博内容
	var getWeiboContentUrl = '/weibo/frontend/getweibocontent' + '?weiboId=';
	// 将通知消息的未读改为已读(isRead变为1)
	var modifyNotifyUrl = '/weibo/frontend/modifynotifyisread' + '?action=';
	//获取评论内容
	var getCommentContentUrl = '/weibo/frontend/getcommentcontent' + '?commentId=';

	var user = getMineInfo();
	getMyNotifyOfAction(action);

	function getMyNotifyOfAction(tempAction) {
		var url = getMyNotifyInfoUrl + '&action=' + tempAction;
		$.getJSON(url, function(data) {
            $(".tips-mynotify").hide();
			if (data.success) {
				if (tempAction == 1) {
					showAtMyHtml(data.weiboList, tempAction);
				}else if (tempAction == 3) {
					showReplyHtml(data.replyList, tempAction);
				}else if (tempAction == 2) {
					showCommentHtml(data.commentList, tempAction);
				}else if(tempAction == 6){
					showCommentToHtml(data.commentList1, tempAction);
				}else if (tempAction == 5) {
					showPraiseHtml(data, tempAction);
				}else if(tempAction == 4){
					showRelationHtml(data.relationList, tempAction);
				}
			}else {
				$(".tips-mynotify").show();
				if (tempAction == 1) {
					$(".tips-mynotify-content").text("还没有@你的微博，要加油哦！");
				}else if (tempAction == 2) {
					$(".tips-mynotify-content").text("还没有关于你的评论，要加油哦！");
				}else if (tempAction == 3) {
					$(".tips-mynotify-content").text("还没有人回复你呢，要加油哦！");
				}else if (tempAction == 5) {
					$(".tips-mynotify-content").text("还没有人赞你的微博，要加油哦！");
				}else if(tempAction == 4){
					$(".tips-mynotify-content").text("还没有关注你的消息，要加油哦！");
				}else if(tempAction == 6){
					$(".tips-mynotify-content").text("你还没有评论过什么呢，要加油哦！");
				}else{
					$(".tips-mynotify-content").text("还没有关于你的消息，要加油哦！");
				}
			}
		});
	}
	
	function showRelationHtml(list,tempAction){
		var relationHtml='';
		list
		.map(function(item, index) {
			relationHtml += `<!-- 每条 -->
				<div style="width: 600px; min-height: 100px; background-color: #fff; margin-top: 15px;
					 padding-top: 20px; position: relative; margin-left: 250px;">
					<a href="homepage?userId=${userId}&toUserId=${item.fans.userId}">
					<img src="${item.fans.headImg}"
						style="width: 50px; height: 50px; border-radius: 50%; margin-left: 20px;"></a>
					<div style="display: inline-block; margin-left: 5px;">
						<a href="homepage?userId=${userId}&toUserId=${item.fans.userId}" class="support-user" 
						style="top:22px;font-size: 15px;font-weight: bold;color: #333;text-decoration: none;">
						${item.fans.nickName}</a>
						<p style="color: #808080; font-size: 12px; 
						margin-top: -5px;position: absolute;top:55px;">
						在 ${FormatAllDateToYMDHMS(item.lastEditTime)}</p>
						<span style="text-decoration: none; color: #333;font-size: 13px;
						position:absolute;top:75px;background-color: #f2f2f5;
						width: 500px;min-height: 30px;margin-left: 0px;line-height:30px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关注了你</span>
					</div></div> <!-- 每条 -->`;
		});
		$("#relation-list").html(relationHtml);
		// 页面加载后将通知消息的未读改为已读(isRead变为1)
		modifyNotify(tempAction);
	}
	
	/**
	 * 展示收到评论我的页面
	 */
	function showCommentHtml(list, tempAction) {
		var commentHtml = '';
		list
		.map(function(item, index) {
			commentHtml += '<!-- 每条收到的评论 --><div id="comment-from1" class="other-comment" style="width: 100%; min-height: 165px; background-color: #fff; margin-top: 15px; padding-top: 18px;">'
							  + '<a href="homepage?userId='+userId+'&toUserId='+item.user.userId+'" style="cursor:pointer;"><img src="'
							  + item.user.headImg
							  + '"	style="width: 50px; height: 50px; border-radius: 50%; margin-left: 20px;"></a>'
							  + '<div style="display: inline-block; margin-left: 5px;"><p style="font-size: 13px; font-weight: bold;cursor:poiner">'
							  +'<a href="homepage?userId='+userId+'&toUserId='+item.user.userId+'" style="text-decoration:none;color:#333">'
							  + item.user.nickName
							  +'</a>'
							  + '</p>'
							  + '<p style="color: #808080; font-size: 12px; margin-top: -5px;">'
							  + FormatAllDateToYMDHMS(item.time)
							  + '</p></div>'
							  + '<div style="font-size: 13px; margin-left: 20px; min-height: 20px; line-height: 20px;'
							  + ' margin-left: 80px; margin-top: -5px; width: 80%;">评论&nbsp;'
							  + '<span><a href="homepage?userId='+userId+'&toUserId=" style="text-decoration: none;cursor:poiner; font-size: 12px; color: #eb7350;">@'
							  + nickName
							  + '&nbsp;</a>:&nbsp;</span>'
							  + '<span style="font-size: 13px; width: 80%; word-break: break-word;">'
							  + item.content
							  + '</span></div>'
							  + '<div style="cursor: pointer; width: 500px; min-height: 30px; background-color: #f2f2f5; margin-top: 10px; margin-left: 78px;">'
							  + '<span style="font-size: 13px; color: #808080; margin-left: 15px;">评论我的微博&nbsp;:&nbsp;</span>'
							  + '<a href="weibodetail?userId='+userId+'&weiboId='+item.weibo.weiboId+'&commentId='+item.commentId+'" style="text-decoration: none; color: #333; font-size: 13px; word-wrap: break-word;">'+item.weibo.content+'</a></div>'
							  + '<div class="other-comment-footer" style="padding-bottom: 5px; border-bottom: 1px solid #d9d9d9; width: 100%; display: flex;'
							  + 'margin-top: 10px; border-top: 1px solid #f2f2f5; font-size: 13px; padding-top: 4px;">'
							  + '<div><a style="text-decoration:none;color: #808080;" href="weibodetail?userId='+userId+'&weiboId='+item.weibo.weiboId+'&commentId='+item.commentId+'">'
							  +'<span class="iconfont">&#xe62d;</span> 查看原对话</a></div><div class="comment-word reply-show" data-id='+item.time+'><span class="iconfont">&#xe62f;</span> 回复</div>'
							  +'<div><a style="text-decoration:none;color:#808080;font-size: 12px;" href="weibodetail?userId='+userId+'&weiboId='+item.weibo.weiboId+'">'
							  +'<span class="iconfont">&#xe62d;</span>查看微博</a></div>'
							  +'</div>'
							  + '<div class="comment-switch reply-switch'+item.time+' reply-hide'+item.commentId+'" style="display: none; width: 600px; height: 90px; background-color: #f2f2f5; position: relative;">'
							  + '<img src="'+headImg+'" style="width: 32px; height: 32px; margin: 15px auto auto 25px;">'
							  + '<div style="display: inline-block; position: absolute; top: 15px; margin-left: 10px;">'
							  + '<input type="text" style="width: 500px; height: 24px;" class="receive-input" placeholder="回复&nbsp;'+item.user.nickName+'" data-id="'+item.commentId+'" type="text" id="content2'+item.commentId+'"/></div>'
							  + '<div><button type="button" style="border: none; background-color: #FA7D3C; color: #fff; '
							  + 'width: 50px; height: 25px; float: right; margin-right: 30px; margin-top: 5px;" id="reply" '
							  +' data-id="'+item.commentId+'" data-userid="'+item.user.userId+'">回复</button></div>'
							  + '</div></div><!-- 每条收到的评论 -->'
				            });
		         $("#comment-me-list").html(commentHtml);
		// 页面加载后将通知消息的未读改为已读(isRead变为1)
		modifyNotify(tempAction);
	}
	
	$(document).on("click",".reply-show",function(e){
		var switchname=".reply-switch"+e.currentTarget.dataset.id;
		$(switchname).toggle();
	});
	/*$(document).on("click",".reply2",function(e){
		var switchname=".reply-switch"+e.currentTarget.dataset.time;
		$.toast({text:"s:"+switchname});
		$(switchname).hide();
	});*/

	/**
	 * 展示自己评论他人的页面
	 */
	function showCommentToHtml(list, tempAction) {
		var commentHtml1 = '';
		list
		.map(function(item, index) {
			commentHtml1 += '<!-- 每条发出的评论 --><div id="comment-to1" class="atmy-comment" style="width: 100%; min-height: 120px; background-color: #fff; margin-top: 15px; padding-top: 18px;'
				             +'padding-bottom: 25px;">'
							  + '<a href="homepage?userId='+userId+'&toUserId='+item.user.userId+'" style="cursor:pointer;"><img src="'
							  + item.user.headImg
							  + '"	style="width: 50px; height: 50px; border-radius: 50%; margin-left: 20px;"></a>'
							  + '<div style="display: inline-block; margin-left: 5px;"><p style="font-size: 13px; font-weight: bold;cursor:poiner">'
							  +'<a href="homepage?userId='+userId+'&toUserId='+item.user.userId+'" style="text-decoration:none;color:#333">'
							  + item.user.nickName
							  +'</a>'
							  + '</p>'
							  + '<p style="color: #808080; font-size: 12px; margin-top: -5px;">'
							  + FormatAllDateToYMDHMS(item.time)
							  + '</p></div>'
							  + '<div style="font-size: 13px; margin-left: 20px; min-height: 20px; line-height: 20px;'
							  + ' margin-left: 80px; margin-top: -5px; width: 80%;">回复&nbsp;'
							  + '<span><a href="homepage?userId='+userId+'&toUserId='+item.weibo.author.userId+'" style="text-decoration: none;cursor:poiner; font-size: 12px; color: #eb7350;">@'
							  + item.weibo.author.nickName
							  + '</a>&nbsp;:&nbsp;</span>'
							  + '<span style="font-size: 13px; width: 80%; word-break: break-word;">'
							  + item.content
							  + '</span></div>'
							  + '<div style="cursor: pointer; width: 500px; min-height: 30px; background-color: #f2f2f5; margin-top: 10px; margin-left: 78px;">'
							  + '<span style="font-size: 13px; color: #808080; margin-left: 15px;">评论的微博:&nbsp;</span>'
							  + '<a href="weibodetail?userId='+userId+'&weiboId='+item.weibo.weiboId+'&commentId='+item.commentId+'"	style="text-decoration: none; color: #333; font-size: 13px; word-wrap: break-word;">'+item.weibo.content+'</a></div>'
//							  + '<div class="other-comment-footer" style="padding-bottom: 5px; border-bottom: 1px solid #d9d9d9; width: 100%; display: flex;'
//							  + 'margin-top: 10px; border-top: 1px solid #f2f2f5; font-size: 13px; padding-top: 4px;">'
//							  + '<div><span class="iconfont">&#xe62d;</span> 查看对话</div><div class="comment-word"><span class="iconfont">&#xe62f;</span> 回复</div>'
//							  + '<div><span class="iconfont">&#xe639;</span> 赞</div></div>'
//							  + '<div class="comment-switch" style="display: none; width: 600px; height: 90px; background-color: #f2f2f5; position: relative;">'
//							  + '<img src="" style="width: 32px; height: 32px; margin: 15px auto auto 25px;">'
//							  + '<div style="display: inline-block; position: absolute; top: 15px; margin-left: 10px;">'
//							  + '<input type="text" style="width: 500px; height: 24px;" /></div>'
//							  + '<div><button type="button" style="border: none; background-color: #FA7D3C; color: #fff; '
//							  + 'width: 50px; height: 25px; float: right; margin-right: 30px; margin-top: 5px;">评论</button></div>'
//							  + '</div>'
							  +'</div><!-- 每条发出的评论 -->'
				            });
		         $("#comment-to-list").html(commentHtml1);
		// 页面加载后将通知消息的未读改为已读(isRead变为1)
		modifyNotify(tempAction);
	}
	
	/**
	 * 展示回复我的页面
	 */
	function showReplyHtml(list, tempAction) {
		var replyHtml = '';
		list
		.map(function(item, index) {
			replyHtml += '<!-- 每条收到的回复 --><div class="atmy-comment" style="width: 100%; min-height: 165px; background-color: #fff; margin-top: 15px; padding-top: 18px;">'
				  + '<a href="" style="cursor:pointer;"><img src="'
				  + item.fromUser.headImg
				  + '"	style="width: 50px; height: 50px; border-radius: 50%; margin-left: 20px;"></a>'
				  + '<div style="display: inline-block; margin-left: 5px;"><p style="font-size: 13px; font-weight: bold;cursor:poiner">'
				  +'<a href="" style="text-decoration:none;color:#333">'
				  + item.fromUser.nickName
				  +'</a>'
				  + '</p>'
				  + '<p style="color: #808080; font-size: 12px; margin-top: -5px;">'
				  + FormatAllDateToYMDHMS(item.createTime)
				  + '</p></div>'
				  + '<div style="font-size: 13px; margin-left: 20px; min-height: 20px; line-height: 20px;'
				  + ' margin-left: 80px; margin-top: -5px; width: 80%;">回复&nbsp;'
				  + '<span><a href="" style="text-decoration: none;cursor:poiner; font-size: 12px; color: #eb7350;">@'
				  + nickName
				  + '</a>&nbsp;:&nbsp;</span>'
				  + '<span style="font-size: 13px; width: 80%; word-break: break-word;">'
				  + item.content
				  + '</span></div>'
				  + '<div style="cursor: pointer; width: 500px; min-height: 30px; background-color: #f2f2f5; margin-top: 10px; margin-left: 78px;">'
				  + '<span style="font-size: 13px; color: #808080; margin-left: 15px;">回复我的评论&nbsp;:&nbsp;</span>'
				  + '<a href="weibodetail?userId='+userId+'&weiboId='+item.comment.weibo.weiboId+'&commentId='+item.comment.commentId+'" style="text-decoration: none; color: #333; font-size: 13px; word-wrap: break-word;">'+item.comment.content+'</a></div>'
				  + '<div class="other-comment-footer" style="padding-bottom: 5px; border-bottom: 1px solid #d9d9d9; width: 100%; display: flex;'
				  + 'margin-top: 10px; border-top: 1px solid #f2f2f5; font-size: 13px; padding-top: 4px;">'
				  + '<div><a style="text-decoration:none;color: #808080;" href="weibodetail?userId='+userId+'&weiboId='+item.comment.weibo.weiboId+'&commentId='+item.comment.commentId+'">'
				  +'<span class="iconfont">&#xe62d;</span> 查看原对话</a></div><div class="comment-word reply-show" data-id='+item.createTime+'><span class="iconfont">&#xe62f;</span> 回复</div>'
				   +'<div><a style="text-decoration:none;color:#808080;font-size: 12px;" href="weibodetail?userId='+userId+'&weiboId='+item.comment.weibo.weiboId+'">'
				  +'<span class="iconfont">&#xe62d;</span>查看微博</a></div>'
				  +'</div>'
				  + '<div class="comment-switch reply-switch'+item.createTime+' reply-hide'+item.comment.commentId+'" style="display: none; width: 600px; height: 90px; background-color: #f2f2f5; position: relative;">'
				  + '<img src="'+headImg+'" style="width: 32px; height: 32px; margin: 15px auto auto 25px;">'
				  + '<div style="display: inline-block; position: absolute; top: 15px; margin-left: 10px;">'
				  + '<input type="text" style="width: 500px; height: 24px;" class="receive-input input-reply2'+item.replyId+'" placeholder="回复&nbsp;'+item.fromUser.nickName+'" data-id="'+item.commentId+'" type="text" id="content2'+item.commentId+'"/></div>'
				  + '<div><button type="button" style="border: none; background-color: #FA7D3C; color: #fff; '
				  + 'width: 50px; height: 25px; float: right; margin-right: 30px; margin-top: 5px;" id="reply2" data-id="'
					+ item.replyId
					+ '"data-commentid="'
						+ item.comment.commentId
						+ '"'
						+ 'data-toid="'
						+ item.fromUser.userId+'" class=".reply2" data-time="'+item.createTime+'"'
				  +'>回复</button></div>'
//				  + '<div><span class="iconfont">&#xe62d;</span> 查看对话</div><div class="comment-word"><span class="iconfont">&#xe62f;</span> 回复</div>'
//				  + '<div><span class="iconfont">&#xe639;</span> 赞</div></div>'
//				  + '<div class="comment-switch" style="display: none; width: 600px; height: 90px; background-color: #f2f2f5; position: relative;">'
//				  + '<img src="" style="width: 32px; height: 32px; margin: 15px auto auto 25px;">'
//				  + '<div style="display: inline-block; position: absolute; top: 15px; margin-left: 10px;">'
//				  + '<input type="text" style="width: 500px; height: 24px;" /></div>'
//				  + '<div><button type="button" style="border: none; background-color: #FA7D3C; color: #fff; '
//				  + 'width: 50px; height: 25px; float: right; margin-right: 30px; margin-top: 5px;">评论</button></div>'
				  + '</div></div><!-- 每条收到的回复 -->'
				      });
		         $("#reply-me").html(replyHtml);
		// 页面加载后将通知消息的未读改为已读(isRead变为1)
		modifyNotify(tempAction);
	}
	
	
/*	  `<div style="display: inline-block; position: absolute; top: 15px; margin-left: 10px;">
	 <input type="text" style="width: 500px; height: 24px;" />${123}</div>
	 <div><button type="button" style="border: none; background-color: #FA7D3C; color: #fff; 
	width: 50px; height: 25px; float: right; margin-right: 30px; margin-top: 5px;">评论</button></div>`*/

	function showPraiseHtml(data, tempAction) {
		var html = '';
		data.notifyList
				.map(function(item, index) {
					// 获取优化后的内容
//					var contentHtml = initContentHtml(item.weiboId,
//							"#wpraise-content", item.content, 80);
					
					//获取发送者的用户
					var sender=getUserById(item.senderId);
					
					html += '<div style="width: 600px; min-height: 130px; background-color: #fff; margin-top: 15px; padding-top: 18px; position: relative; margin-left: 250px;padding-bottom:15px;">'
							+ '<a href="homepage?userId='+userId+'&toUserId='+item.senderId+'"><img src="'
							+ sender.headImg
							+ '"'
							+ ' style="width: 50px; height: 50px; border-radius: 50%; margin-left: 20px;"></a>'
							+ '<div style="display: inline-block; margin-left: 10px;">'
							+ '<a href="homepage?userId='+userId+'&toUserId='+item.senderId+'" class="support-user">'
							+ item.nickName
							+ '</a>'
							+ '<p style="color: #808080; font-size: 12px; margin-top: -5px;">'
							+ FormatAllDateToYMDHMS(item.createTime)
							+ '</p>'
							+ '</div>'
							+ '<div style="font-size: 13px; margin-left: 20px; min-height: 20px; line-height: 20px; margin-left: 80px; margin-top: -5px; width: 80%;">'
							+ '<span style="font-size: 13px; width: 80%; word-break: break-word;">'
							+ '<span style="text-decoration: none; color: #333;">赞了你的微博</span> </span>'
							+ '</div>'
							+ '<div style="cursor: pointer; width: 500px; min-height: 30px; background-color: #f2f2f5; margin-top: 10px; margin-left: 78px;">'
							+ '<a href="homepage?userId='+userId+'&toUserId="' 
							+'style="font-size: 13px; color: #FA7D3C; margin-left: 15px;text-decoration: none;">@'
							+ data.user.nickName
							+ '</a>'
							+ '<a href="weibodetail?userId='+userId+'&weiboId='+item.weiboId+'" class="support-source" > '
//							+'<div id="wpraise-content'+item.weiboId+'" '
//							+'style="font-size:13px; display:inline-block;" >'
//							+ contentHtml
							+ item.content
//							+'</div>'
							+ '</a>'
							+ '</div>'
							+ '</div>'
							/*+ '<div class="support-response replydiv" data-id="'
							+ item.createTime
							+ '">'
							+ '<span class="iconfont">&#xe62f;</span> 回复'
							+ '</div>'
							+ '<div class="support-switch" id="support-switch'
							+ item.createTime
							+ '" '
							+ ' style="display: none; width: 600px; height: 90px; background-color: #f2f2f5; position: relative; margin-left: 250px;">'
							+ '<img src="'
							+ data.user.headImg
							+ '"'
							+ 'style="width: 32px; height: 32px; margin: 15px auto auto 25px;">'
							+ '<div style="display: inline-block; position: absolute; top: 15px; margin-left: 10px;">'
							+ '<input type="text" style="width: 500px; height: 24px;" />'
							+ '</div>'
							+ '<div>'
							+ '<button type="button" style="border: none; background-color: #FA7D3C; color: #fff; width: 50px; height: 25px; float: right; margin-right: 30px; margin-top: 5px;">评论</button>'
							+ '</div>'*/
							+ '</div>'
				});
		$("#receive-praise-list").html(html);
		// 页面加载后将通知消息的未读改为已读(isRead变为1)
		modifyNotify(tempAction);
	}
	
	/**
	 * 赞我的微博模块的回复显示隐藏点击事件 
	 */
	$(document).on("click",".replydiv",function(e){
		var id=e.currentTarget.dataset.id;
		var divid = "#support-switch"+id;
		$(divid).toggle();
	});

	/**
	 * 展开全文与收起全文
	 */
	$(document).on("click", "#openContent", function(e) {
		var id = e.currentTarget.dataset.id;
		var contentId="#wpraise-content"+id;
		$(contentId).find('#openContent').remove();
		openContentClick(e, "#wpraise-content", getWeiboContentUrl);
	});
	$(document).on("click", "#closeContent", function(e) {
		closeContentClick(e, "#wpraise-content", getWeiboContentUrl, 80);
	});

	/**
	 * 更改通知为已读
	 */
	function modifyNotify(tempAction) {
		var url = modifyNotifyUrl + tempAction;
		$.getJSON(url, function(data) {
			if (data.success) {
				getNotifys();
			}
		});
	}

	function showAtMyHtml(list, tempAction) {
		var weiboHtml = '';
		list
				.map(function(item, index) {
					// 获取@用户昵称与内容赋给不同div
					var str = item.content.substr(item.content.indexOf("\@"));
					var atNickName = str.substring(0, str.indexOf("\ "));

					// 获取优化后的内容
					var contentHtml = initContentHtml(item.weiboId,
							"#atmy-weibocontent", item.content, 60, 1);
					
					// 获取微博点赞的状态并给对应div设置点赞效果
					shwoWeiboPraise(item.weiboId);
					
					// 获取微博收藏的状态并给对应div设置效果
					shwoWeiboCollection(item.weiboId);

					weiboHtml += `<!-- 每条@我的微博 --><div class="other-comment" style="width: 100%;min-height: 100px;background-color: #fff;margin-top: 15px;padding-top: 18px;position: relative;">
							 <a href="homepage?userId=${userId}&toUserId=${item.author.userId}">
							 <img src="${item.author.headImg}" style="width: 50px;height: 50px;border-radius: 50%;margin-left: 20px;"></a>
							 <div style="display: inline-block;margin-left: 5px;">
							 <a href="homepage?userId=${userId}&toUserId=${item.author.userId}" class="callme-username" style="margin-left:5px">
							 ${item.author.nickName}
							 </a>
							 <p style="color: #808080;font-size: 12px;margin-top: -5px;margin-left:5px;">
							 ${FormatAllDateToYMDHMS(item.publishTime)}
							 </p></div>
							 <div style="width:90%;margin:0 auto;">
							 <div style="width:80%;">
							 <div style="margin-left:50px;">
							 <span class="callme-detail" style="font-size:14px;word-break: break-word;width:70%;">
							 <a href="homepage?userId=${userId}&toUserId=" class="callmelink" style="margin-left:0px">
							 ${atNickName}
							 </a>
							 <span id="atmy-weibocontent${item.weiboId}">
							 ${contentHtml}
							 </span>
							 </span>
							 </div></div>
							 <div class="article-context-img" style="width:60%;margin-left:45px;margin-top:10px;display:flex;flex-wrap: wrap;">
							 ${showWeiboImg(item.weiboImgList,item.weiboId)}
							 </div></div>
							 ${getBigImgTipHtml(item.weiboId)}
							 <div class="callme-footer" style="padding-bottom: 5px;border-bottom: 1px solid #d9d9d9;width: 100%;display: flex;margin-top: 10px;border-top: 1px solid #f2f2f5;font-size: 13px;padding-top: 4px ;">
							 <div id="checkCollection" data-id="${item.weiboId}" 
							class="checkCollection${item.weiboId}"><span class="iconfont">&#xe641;</span><span 
							class="collection-text${item.weiboId}">收藏</span></div>
							 <div><a style="text-decoration:none;color:#808080;" href="weibodetail?userId=${userId}&weiboId=${item.weiboId}">
							 <span class="iconfont">&#xe62d;</span>查看微博</a></div>
							 <div class="callme-word btn-comment" id="btn-weibo-comment" data-id="${item.weiboId}"><span class="iconfont">&#xe62f;</span>评论&nbsp;${showWeiboCommentCount(item.weiboId)}</div>
							 <div id="weibo-praise" data-id="${item.weiboId}" class="weibo-praise${item.weiboId}">
							<span class="iconfont">&#xe639;</span><span class="wpraise-count${item.weiboId}">0</span></div>
							 </div>
							 
							 <!-- 评论模块 -->
							<div class="comment comment-div${item.weiboId}" style="display: none;background-color: #f2f2f5;">
							<div class="comment-first callme-switch" style="margin-bottom:-10px;width: 600px;height:90px ;background-color: #f2f2f5;position: relative;">
							<div><img src="${user.headImg}" style="width: 32px;height: 32px;margin: 15px auto auto 18px;">
							</div><div style="display: inline-block;position: absolute;top:15px;margin-left: 10px;">
							<input class="comment-input" type="text" style="height: 27px; 
							width: 510px; margin-left: 50px;" 
							id="content1${item.weiboId}"/></div><!--disabled--><div><button 
							class="comment-button" id="comment" 
							data-id="${item.weiboId}" type="button" style="border: none;background-color: #FA7D3C;color: #fff;width: 50px;
							height: 25px;float: right;margin-right: 30px;margin-top: 5px;">评论</button></div></div><div class="comment-list comment-list${item.weiboId}" style="min-height:0px;display:none;">
							<!-- 评论列表 -->
							<div class="comment-list-context" 
							id="commentItem${item.weiboId}" style="padding-bottom: 15px;"></div> </div> </div>
							<!-- 评论模块结束 --></div>
							 <!-- 每条@我的微博 -->`;
				});
		$("#atmy-weibo-list").html(weiboHtml);
		$(".article-context-img img").attr("style","height:200px;")
		// 页面加载后将通知消息的未读改为已读(isRead变为1)
		modifyNotify(tempAction);
	}
	
	/*<！-- <div id="div-comment${item.weiboId}" class="callme-switch" style="display: none;width: 600px;height:90px ;background-color: #f2f2f5;position: relative;">
	 <img src="" style="width: 32px;height: 32px;margin: 15px auto auto 25px;">
	 <div style="display: inline-block;position: absolute;top:15px;margin-left: 10px;"><input type="text" style="width:500px;height: 24px;" /></div>
	 <div><button type="button" style="border: none;background-color: #FA7D3C;color: #fff;width: 50px;
	 	height: 25px;float: right;margin-right: 30px;margin-top: 5px;">评论</button></div>
	 </div>  </div>  -->*/

/**
	 * 微博卡片的点赞事件
	 */
	$(document).on("click", "#weibo-praise", function(e) {
		praiseWeibo(e);
	});
	
	/**
	 * 微博卡片的收藏事件
	 */
	$(document).on("click", "#checkCollection", function(e) {
		checkCollection(e);
	});

	/**
	 * 展开全文\收起全文
	 */
	$(document).on("click", "#openContent", function(e) {
		openContentClick(e, "#atmy-weibocontent", getWeiboContentUrl, 1);
	});
	$(document).on("click", "#closeContent", function(e) {
		closeContentClick(e, "#atmy-weibocontent", getWeiboContentUrl, 60, 1);
	});

	/**
	 * 显示微博图片
	 */
/*
	function showWeiboImg(imgList) {
		var weiboImgHtml = '';
		if (imgList <= 0) {
			return '';
		}
		imgList
				.map(function(item, index) {
					if (index < 9) {
						weiboImgHtml += '<img src="'
								+ item.imgAddr
								+ '"id="'
								+ 'weiboImg'
								+ (index + 1)
								+ '" style="width:100px;height:100px;margin-left:5px;margin-top:5px;"/>'
					}
				});
		return weiboImgHtml;
	}*/

	function getMineInfo() {
		$.ajax({
			url : getMineInfoUrl,
			data : {},
			type : 'get',
			async : false,
			success : function(data) {
				if (data.success) {
					user = data.user;
					nickName = user.nickName;
					headImg = user.headImg;
					/*
					 * $("#notify-atMy").attr('href', 'mynotify?userId=' +
					 * user.userId+'&'+'action='+1);
					 */
					// 把该页面的用户Id赋给userid
					// $('#publish').attr('data-userid',user.userId);
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
		return user;
	}

	if (action == 1) {
		showAt();
	} else if (action == 2) {
		showComment();
	} 
	else if (action == 3) {
		showReplyMe();
	}
	else if (action == 5) {
		showPraise();
	}else if (action == 4) {
		showRelation();
	}else if(action==6){
		showCommentTo();
	}

	/**
	 * 左边点击切换事件
	 */
	$(".p1").click(function() {
		showAt();
		getMyNotifyOfAction(1);
	});
	// @我的微博
	$(".receive").click(function() {
		showAtWeibo();
		getMyNotifyOfAction(1);
	});
	// 收到的回复
	$(".publish").click(function() {
		showReplyMe();
		getMyNotifyOfAction(3);
	});
	$(".p2").click(function() {
		showComment();
		getMyNotifyOfAction(2);
	});
	// 收到的评论
	$("#comment-from").click(function() {
		showCommentFrom();
		getMyNotifyOfAction(2);
	});
	// 发出的评论
	$("#comment-to").click(function() {
		showCommentTo();
		getMyNotifyOfAction(6);
	});
	$(".p3").click(function() {
		showPraise();
		getMyNotifyOfAction(5);
	});
	$(".p4").click(function() {
		showRelation();
		getMyNotifyOfAction(4);
	});
	function showAt() {
		$(".div1").show();
		$(".div2").hide();
		$(".div3").hide();
		$(".div4").hide();
		$(".atmy-comment").hide();
		$(".p1").css("background-color", "rgba(0,0,0,.08)");
		$(".p2").css("background-color", "");
		$(".p3").css("background-color", "");
		$(".p4").css("background-color", "");
	}
	function showComment() {
		$(".div4").hide();
		$(".div1").hide();
		$(".div2").show();
		$(".div3").hide();
		$(".atmy-comment").hide();
		$(".p4").css("background-color", "");
		$(".p3").css("background-color", "");
		$(".p2").css("background-color", "rgba(0,0,0,.08)");
		$(".p1").css("background-color", "");
	}
	function showRelation() {
		$(".div4").show();
		$(".div1").hide();
		$(".div2").hide();
		$(".div3").hide();
		$(".atmy-comment").hide();
		$(".p4").css("background-color", "rgba(0,0,0,.08)");
		$(".p3").css("background-color", "");
		$(".p2").css("background-color", "");
		$(".p1").css("background-color", "");
	}
	function showPraise() {
		$(".div4").hide();
		$(".div1").hide();
		$(".div2").hide();
		$(".div3").show();
		$(".atmy-comment").hide();
		$(".p3").css("background-color", "rgba(0,0,0,.08)");
		$(".p4").css("background-color", "");
		$(".p2").css("background-color", "");
		$(".p1").css("background-color", "");
	}
	function showAtWeibo() {
		$(".other-comment").show();
		$(".atmy-comment").hide();
		$(".receive").css("font-weight", "bold");
		$(".publish").css("font-weight", "normal");
	}
	function showReplyMe() {
		$(".other-comment").hide();
		$(".atmy-comment").show();
		$(".receive").css("font-weight", "normal");
		$(".publish").css("font-weight", "bold");
	}
	function showCommentFrom() {
		$("#comment-from1").show();
		$("#comment-to1").hide();
		$("#comment-from").css("font-weight", "bold");
		$("#comment-to").css("font-weight", "normal");
	}
	function showCommentTo() {
		$("#comment-to1").show();
		$("#comment-from1").hide();
		$("#comment-from").css("font-weight", "normal");
		$("#comment-to").css("font-weight", "bold");
	}
});