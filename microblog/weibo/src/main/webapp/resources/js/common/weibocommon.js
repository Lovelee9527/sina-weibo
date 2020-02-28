/**
 * 通過userId查詢頭像路徑
 */
	function getUserById(userId) {
		// 获取该用户昵称的用户Id
		var getUserByNickNameUrl = '/weibo/frontend/getuserbynickname';
		var user = '';
		$.ajax({
			url : getUserByNickNameUrl,
			data : {
				userId : userId
			},
			type : 'get',
			async : false,
			success : function(data) {
				if (data.success) {
					user = data.user;
				} else {
					user = '';
				}
			}
		});
		return user;
	}


/**
 * 显示微博图片
 */
function showWeiboImg(imgList,weiboId) {
		var weiboImgHtml = '';
		if (imgList <= 0) {
			return '';
		}else{
			var imgNum = imgList.length;
		}
		 if(imgNum==1){
			imgList.map(function(item, index) {
					weiboImgHtml += `<img data-weiboid="${weiboId}" src="${item.imgAddr}"
					 style="width:200px;">`
			});
		}else if(imgNum==2){
			imgList.map(function(item, index) {
					weiboImgHtml += `<img data-weiboid="${weiboId}" src="${item.imgAddr}"
					 style="width:140px;">`
			});
		}else if(imgNum>=3 && imgNum< 9){
			imgList.map(function(item, index) {
					weiboImgHtml += `<img data-weiboid="${weiboId}" src="${item.imgAddr}">`
			});
		}
		return weiboImgHtml;
}

/**
 * 获取放大图片的Html
 */ 
function getBigImgTipHtml(id){
	var html = `<!-- 放大图片 -->
    <div class="bigimg-tip bigimg-tip${id}">
   <div class="small-img" ><img src="" class="weiboImg${id}"></div></div>
    <!-- 放大图片 -->`
    
    return html;
}
/**
 * 放大图片js
 */
// 放大缩小图片
$(document).on("click",".article-context-img img",function(e){
	var weiboid=e.currentTarget.dataset.weiboid
	var tempImgClass=".weiboImg"+weiboid;
	var bigImgid=".bigimg-tip"+weiboid;
	$(bigImgid).show();
	$(tempImgClass).attr("src",$(this).attr("src"));
	e.stopPropagation();
});
$(".bigimg-tip").click(function(e){
	$(this).hide();
});
$(window).click(function(){
	$(".bigimg-tip").hide();
});

// 收藏事件
function checkCollection(e){
	var weiboId = e.currentTarget.dataset.id;
	var url = '/weibo/frontend/checkcollection' + '?weiboId=' + weiboId;
	$.getJSON(url, function(data) {
		if (data) {
			shwoWeiboCollection(weiboId);
			$.toast({
				text : "更新收藏成功，可在首页查看",
				icon : "info"
			});
		} else {
			$.toast({
				text : "收藏失败",
				icon : "error"
			});
		}
	});
}

/**
 * 获取微博评论的总数并给对应div设置效果
 */
function showWeiboCommentCount(weiboId){
	var url ='/weibo/frontend/getcommentandreplycount'+'?weiboId='+weiboId;
	var count='';
	$.ajax({
		url : url,
		data : {
		},
		type : 'get',
		async : false,
		success : function(data) {
		if (data.success) {
			count=data.count;
		} 
		}
	});
	return count;
}
/**
 * 获取微博回复的总数并给对应div设置效果
 */
function showWeiboReplyCount(commentId){
	var url ='/weibo/frontend/getcommentandreplycount'+'?commentId='+commentId;
	var count=0;
	$.ajax({
		url : url,
		data : {
		},
		type : 'get',
		async : false,
		success : function(data) {
		if (data.success) {
			count=data.count;
		}
		}
	});
	return count;
}

/*
 * 获取微博收藏的状态并给对应div设置效果
 */
function shwoWeiboCollection(weiboId) {
	var getCollectionListUrl='/weibo/frontend/getcollectionlist';
	var url = getCollectionListUrl + '?weiboId=' + weiboId;
	var divClass = ".checkCollection" + weiboId;
	var spanId = ".collection-text" + weiboId;
	$.getJSON(url, function(data) {
		if (data.success) {
			if (data.count > 0) {
				$(spanId).text("已收藏");
				$(divClass).attr("style", "color: #FA7D3C;");
			} else {
				$(spanId).text("收藏");
				$(divClass).attr("style", "color:;");
			}
		} else {
// $(spanId).text('');
		}
	});
}

/*
 * 获取微博点赞的状态并给对应div设置点赞效果
 */
function shwoWeiboPraise(weiboId) {
	var url = '/weibo/frontend/getpraiseList' + '?weiboId=' + weiboId;
	var divClass = ".weibo-praise" + weiboId;
	var spanId = ".wpraise-count" + weiboId;
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

// 微博的点赞
function praiseWeibo(e){
	var weiboId = e.currentTarget.dataset.id;
	var url = '/weibo/frontend/addpraiseofweibo' + '?weiboId=' + weiboId;
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
}


