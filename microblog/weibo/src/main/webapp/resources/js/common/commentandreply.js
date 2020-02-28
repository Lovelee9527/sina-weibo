$(function() {
	var userId = getQueryString('userId');
	// 添加评论路径
	var commentUrl = '/weibo/frontend/addcomment';
	// 删除评论路径
	var deleteCommentUrl = '/weibo/frontend/deletecomment'
	// 获取评论内容路径
	var getCommentContentUrl = '/weibo/frontend/getcommentcontent'
			+ '?commentId=';
	// 获取评论列表路径
	var getCommentInfoUrl = '/weibo/frontend/getcommentinfoonmine';
	// 获取回复
	var getReplyInfoUrl = '/weibo/frontend/getreplyinfoonmine';
	// 添加回复路径
	var replyUrl = '/weibo/frontend/addreply';
	// 删除回复路径
	var deleteReplyUrl = '/weibo/frontend/deletereply';

	/**
	 * 微博详情页模块
	 */
	var weiboId = getQueryString('weiboId');
	var commentId = getQueryString('commentId');
	setTimeout(function(){
		if(weiboId>0){
			var id = ".comment-div" + weiboId;
			// 显示对应微博下的评论列表
			showCommentList(weiboId);
			$(id).slideToggle();
		}
	    if(commentId>0){
	    	 var id = "#replyItem" + commentId;
	    	 // 显示对应微博下的评论列表
	    	 showReplyList(commentId);
	    	 $(id).slideToggle();
		}
	},50);
	
	
	/**
	 * 评论回复模块
	 */
	/**
	 * 评论按钮点击事件
	 */
	$(document).on("click", ".btn-comment", function(e) {
		var weiboId = e.currentTarget.dataset.id;
		var id = ".comment-div" + weiboId;
		// 显示对应微博下的评论列表
		showCommentList(weiboId);
		$(id).slideToggle();
	});

	/**
	 * 展开回复点击事件
	 */
	 $(document).on("click", ".showReply-button", function(e) {
	 var commentId = e.currentTarget.dataset.id;
	 var id = "#replyItem" + commentId;
	 // 显示对应微博下的评论列表
	 showReplyList(commentId);
	 $(id).slideToggle();
	 });
	
	/**
	 * 显示评论列表
	 */
	 var user={};
	function showCommentList(weiboId) {
		var url = getCommentInfoUrl + '?' + 'weiboId=' + weiboId;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {

								var commentList = data.commentList;
								user = data.user;
								var commentId = data.commentId;
								var weibo = data.weibo;
								var commentsHtml = '';
								// 1刷新评论列表前先把之前动态添加的script全部清除，以免重复添加出错
								$('#script-arrow').find('script').remove();
								if (commentList != null) {
									commentList
											.map(function(item, index) {

												var isCommentDeleteHtml='';
												if(user.userId==item.user.userId){
													isCommentDeleteHtml =`<span style="cursor:pointer; text-decoration: none; color: #333; font-size: 11px;"
													data-id="${item.commentId}" id="btn-delete" data-weiboid="${item.weibo.weiboId}">删除</span>`
												}
												
												var isOpenReplyHtml='';
												var replycount=showWeiboReplyCount(item.commentId);
												if(replycount>0){
													isOpenReplyHtml = `<span style="cursor:pointer;text-decoration:none; color: #333;
														 font-size: 11px;" data-id="${item.commentId}"
														 class="showReply-button">展开回复&nbsp;<span style="color:#FA7D3C;">(共${replycount}条)</span></span>&nbsp;`;
												}

												commentsHtml += `<div>
														 <div class="hot" style="width: 600px; min-height: 50px; margin: 0 auto; margin-top: 10px; display: flex; position: relative; ">
														 <a href="homepage?userId=${user.userId}&toUserId=${item.user.userId}"><img src="${item.user.headImg}" 
														 data-id="${item.user.userId}"
														 style="width: 32px; height: 32px; margin-left: 18px; margin-right: 10px;" id="user-headImg"></a>
														 <div><div id="user-nickName" style="padding-left: 7px;">
														<a href="homepage?userId=${user.userId}&toUserId=${item.user.userId}" style="font-size: 14px; text-decoration: none; color: #FA7D3C; ">
														 ${item.user.nickName}
														 </a><span id="comment-content" style="position: ; left: 135px; top: 0px; height: 20px; width: 350px;
														 word-wrap: break-word; overflow: hidden; font-size: 13px;margin-left:5px;line-height:26px;">:&nbsp;&nbsp;
														 ${item.content}
														 </span></div><div style="color: #808080; font-size: 12px;margin-left: 7px;margin-top:5px;">
														 ${FormatAllDateToYMDHMS(item.time)}
														 </div>
														 </div>
														 <div style="position: absolute; right: 20px; bottom: 5px;">
														 ${isOpenReplyHtml}
														 ${isCommentDeleteHtml}
														 &nbsp;
														 <span style="cursor:pointer; text-decoration: none; color: #333; font-size: 11px;" 
														 data-id="${item.commentId}"
														 id="btn-reply">回复</span>&nbsp;&nbsp;
														 <span class="iconfont" style="cursor: pointer; margin-left: 5px; color: #515152;"></span>&nbsp;<span
														 style="font-size: 13px;"></span></div>
														 </div>
														 <!-- 回复框模块 -->
														 <div class="receive-detail" style="display: none; width: 100%; height: 120px;background-color:#f2f2f5;" 
															 id="reply-div${item.commentId}">
														 <div style="width: 85%;height: 100px;margin: auto auto auto 70px;background-color: #fff;">
														 <input class="receive-input" range="0&14" style="font-size: 13px;color: #808080;
														 height:25px;width: 90%;margin: 20px auto auto 25px;" placeholder="回复${item.user.nickName}"
														 data-id="${item.commentId}" type="text" id="content2${item.commentId}"/>
														 <button style="margin-right: 22px;float: right;border: none;color: #fff;width: 70px;height: 28px;
														 background-color: #ff8140;line-height: 28px;border-radius: 2px;margin-top: 10px;" type="button" id="reply"
														 data-id="${item.commentId}"
														 data-userid="${item.user.userId}">回复</button></div>
														 </div>
														 <!-- 回复框模块end -->
														 <!-- 回复列表 -->
														 <div id="replyItem${item.commentId}">
														 </div>
														 <!-- 回复列表end -->
												<!--showReplyList(item.commentId)-->
														 </div>
														 <!-- 回复模块end -->`;
											});
								}
								var id = "#commentItem" + data.weiboId;
								var classlist=".comment-list"+data.weiboId;
								$(classlist).show();
								$(id).html(commentsHtml);
							}else{
								var id = "#commentItem" + data.weiboId;
								var classlist=".comment-list"+data.weiboId;
								$(classlist).show();
								$(id).empty();
							}
						});
	}
	/**
	 * 发表评论
	 */
	$(document).on('click', "#comment", function(e) {
		var comment = {};
		var id = "#content1" + e.currentTarget.dataset.id;
		var weiboId = e.currentTarget.dataset.id;
		comment.weibo = {
			weiboId : weiboId
		}
		comment.content = $(id).val();
		if ($.trim(comment.content) == '') {
			$.toast({
				text : "评论内容不能为空",
				icon : "error"
			})
			return;
		}
		var formData = new FormData();
		formData.append('commentStr', JSON.stringify(comment));
		$.ajax({
			url : commentUrl+'?userId='+userId,
			data : formData,
			type : 'post',
			// ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
			contentType : false,
			// 取消帮我们格式化数据，是什么就是什么
			processData : false,
			success : function(data) {
				if (data.success) {
					showCommentList(weiboId);
//					$.toast({
//						text : '评论成功 ~',
//						icon : 'success'
//					});
					// 发送成功后把内容清除
					$(id).val('');
				} else {
					$.toast({
						text : '评论失败 ~' + data.errMsg,
						icon : 'error'
					});
				}
			}
		});
	});

	/**
	 * 展示回复列表
	 */
	function showReplyList(commentId) {
		var url = getReplyInfoUrl + '?' + 'commentId=' + commentId;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								var replyList = data.replyList;
								var fromUser = data.fromUser;
								var toUser = data.toUser;
								var replyId = data.replyId;
								var comment = data.comment;
								var replyHtml = '';
								if (replyList != null) {
									replyList
											.map(function(item, index) {
												
												var isReplyDeleteHtml='';
												if(user.userId==item.fromUser.userId){
													isReplyDeleteHtml = `<span class="receive-detail-switch" style="font-size:11px; color:#515152; cursor:pointer;" 
														data-id="${item.replyId}" id="btn-delete2" data-commentid="${item.comment.commentId}">删除</span>`;
												}

												
												replyHtml += '<div style="width: 85%;margin-left: 60px; border-bottom: 1px solid #d9d9d9;">'
														+ '<div style="width:100%;min-height:40px ;background-color: #eaeaec;'
														+ 'padding:6px auto auto 8px;margin:10px auto auto auto;position:relative;"><div style="margin-left:10px;margin-right:10px;">'
														+ '<a href="homepage?userId='+user.userId+'&toUserId='+item.fromUser.userId+'" style="font-size: 12px; color: #FA7D3C;text-decoration:none;" id="from-nickName" data-id="'
														+ item.replyId
														+ '">'
														+ item.fromUser.nickName
														+ '</a>'
														+ '<span style="font-size:10px;" data-id="'
														+ item.replyId
														+ '">回复&nbsp;&nbsp;&nbsp;</span>'
														+ '<a href="homepage?userId='+user.userId+'&toUserId='+item.toId+'" style="font-size: 12px;color: #FA7D3C;text-decoration: none;" id="to-nickName" data-id="'
														+ item.replyId
														+ '">@'
														+ item.toNickName
														+ ':</a>'
														+ '<span style="font-size: 12px; word-break: break-word;line-height:20px;margin-left:10px;" id="reply-content" data-id="'
														+ item.replyId
														+ '">'
														+ item.content
														+ '</span></div>'
														+ '<div style="color: #808080; font-size: 10px;margin:5px auto auto 10px;padding-bottom:10px;">'
														+ FormatAllDateToYMDHMS(item.createTime)
														+ '</div>'
														+ '<div style="position:absolute;bottom:10px;right:20px;float:right; margin: 8px 8px auto auto;">'
														+isReplyDeleteHtml
														+ '<span class="receive-detail-switch" style="font-size:11px; color:#515152; cursor:pointer;" data-id="'
														+ item.replyId
														+ '" id="btn-reply2">回复</span>&nbsp;'
														+ '<span class="iconfont" style="margin-left: 5px;color: #515152; cursor: pointer;"></span>'
														+ '<span style="font-size: 11px; cursor: pointer;"></span>'
														+ '</div>'
														+ '</div>'
														+ '<!--回复的回复框模块-->'
														+ '<div class="receive-detail-last"style="display:none;width: 101.6%;height: 105px;background-color:#eaeaec;" id="reply-div2'
														+ item.replyId
														+ '">'
														+ '<div style="width: 90%;height: 100px;margin: 5px 30px auto 0;background-color: #fff;">'
														+ '<input class="receive-input input-reply2'+item.replyId+'" style="font-size: 13px;color: #808080;height:25px;width: 90%;margin: 20px auto auto 15px;" '
														+ 'type="text" placeholder="回复'
														+ item.fromUser.nickName
														+ '" />'
														+ '<button style="margin-right: 22px;float: right;border: none;color: #fff;width: 70px;height: 28px;'
														+ 'background-color: #ff8140;line-height: 28px;border-radius: 2px;margin-top: 10px;" type="button" id="reply2" data-id="'
														+ item.replyId
														+ '"data-commentid="'
															+ item.comment.commentId
															+ '"'
															+ 'data-toid="'
															+ item.fromUser.userId+'">评论</button></div></div>'
														+ '<!--回复的回复框模块end-->'
														+ '<!-- 回复的回复列表 -->'
														+ '<div id="replyItem1'
														+ item.replyId
														+ '">'
														+ '</div>'
														+ '<!-- 回复的回复列表end -->'
														+ '</div>'
											});
								}
								var id = "#replyItem" + data.commentId;
//								$(id).empty();
								$(id).html(replyHtml);
							}
						});
	}
	
	/**
	 * 回复按钮点击事件
	 */
	$(document).on("click", "#btn-reply", function(e) {
		var commentId = e.currentTarget.dataset.id;
		var id = "#reply-div" + commentId;
		$(id).slideToggle();
	});

	/**
	 * 回复的回复按钮点击事件
	 */
	$(document).on("click", "#btn-reply2", function(e) {
		var replyId = e.currentTarget.dataset.id;
		var id = "#reply-div2" + replyId;
		$(id).slideToggle();
	});
	
	/**
	 * 回复他人的评论
	 */
	$(document).on('click', "#reply", function(e) {
		var reply = {};
		var id = "#content2" + e.currentTarget.dataset.id;
		var commentId = e.currentTarget.dataset.id;
		var divid = "#reply-div" + commentId;
		reply.comment = {
			commentId : commentId
		}
		reply.toId = e.currentTarget.dataset.userid
		reply.content = $(id).val();
		if ($.trim(reply.content) == '') {
			$.toast({
				text : "回复内容不能为空",
				icon : "error"
			})
			return;
		}
		$(id).val('');
		replyTa(reply,commentId);
		var divhide=".reply-hide"+commentId;
		$(divhide).slideToggle();
		$(divid).slideToggle();
	});
	
	/**
	 * 回复他人的回复
	 */
	$(document).on('click', "#reply2", function(e) {
		var reply = {};
		var replyId = e.currentTarget.dataset.id;
		var id = ".input-reply2" + replyId;
		var commentId = e.currentTarget.dataset.commentid;
		var divid = "#reply-div2" + replyId;
		reply.comment = {
			commentId : commentId
		}
		reply.toId = e.currentTarget.dataset.toid
		reply.content = $(id).val();
		if ($.trim(reply.content) == '') {
			$.toast({
				text : "回复内容不能为空",
				icon : "error"
			})
			return;
		}
		$(id).val('');
		replyTa(reply,commentId);
		var divhide=".reply-hide"+commentId;
		$(divhide).slideToggle();
		$(divid).slideToggle();
//		var switchname=".reply-switch"+e.currentTarget.dataset.time;
//		$.toast({text:"s:"+switchname});
//		$(switchname).hide();
	});
	
	//回复他人
	function replyTa(reply,commentId){
		var formData = new FormData();
		formData.append('replyStr', JSON.stringify(reply));
		$.ajax({
			url : replyUrl,
			data : formData,
			type : 'post',
			// ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
			contentType : false,
			// 取消帮我们格式化数据，是什么就是什么
			processData : false,
			success : function(data) {
				if (data.success) {
					showReplyList(commentId);
					$.toast({
						text : '回复成功 ~',
						icon : 'success'
					});
				} else {
					$.toast({
						text : '回复失败 ~' + data.errMsg,
						icon : 'error'
					});
				}
			}
		});
	}
	
	/**
	 * 弹框事件
	 */
	var deleteReplyCId =0;
	var deleteReplyId=0;
	var deleteCommentId=0;
	var deleteCommentWId=0;
	function checkAndShow(content){
		 $(".alert-delete").show();
		 $(".tips-text").text(content);
	}
	function emptyIdAndHide(){
		deleteReplyCId=0;
		deleteReplyId=0;
		deleteCommentId=0;
		deleteCommentWId=0;
		$(".alert-delete").hide();
	}
	
	/**
	 * 删除回复
	 */
	$(document).on('click', "#btn-delete2", function(e) {
			checkAndShow("确认删除该回复？");
			deleteReplyCId = e.currentTarget.dataset.commentid;
			deleteReplyId = e.currentTarget.dataset.id;
	});
	
	/**
	 * 删除评论
	 */
	$(document).on('click', "#btn-delete", function(e) {
		    checkAndShow("确认删除该评论？");
			deleteCommentWId = e.currentTarget.dataset.weiboid;
			deleteCommentId = e.currentTarget.dataset.id;
	});

	/**
	 * 弹框的确定事件
	 */
	$(document).on("click",".delete-yes",function(){
		if(deleteReplyCId >0 && deleteReplyId >0){
			deleteReply(deleteReplyId,deleteReplyCId);
		}else if(deleteCommentId >0 && deleteCommentWId>0){
			deleteComment(deleteCommentId,deleteCommentWId);
		}
		emptyIdAndHide();
	});
	
    //删除评论
	function deleteComment(commentId,weiboId){
		var formData = new FormData();
		formData.append('commentId', commentId);
		$.ajax({
			url : deleteCommentUrl,
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
					showCommentList(weiboId);
				} else {
					$.toast({
						text : "删除失败",
						icon : "error"
					});
				}
			}
		});
	}
	
	//删除回复函数
	function deleteReply(replyId,commentId){
		var formData = new FormData();
		formData.append('replyId', replyId);
		$.ajax({
			url : deleteReplyUrl,
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
					showReplyList(commentId);
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