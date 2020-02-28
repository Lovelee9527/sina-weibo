/**
 * 实现验证码的点击切换
 */
function changeVerifyCode(img) {
	// 提交到servlet(Kaptcha)去生成新验证码
	img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}

/**
 * 按name获取值 如?shopId=1 shopId就是name , 该方法会返回1
 * 
 * @param name
 * @returns
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURIComponent(r[2]);
	}
	return '';
}

/**
 * 传入图片流返回路径
 */
function getObjectURL(file) {
	var url = null;
	if (window.createObjectURL != undefined) {
		url = window.createObjectURL(file);
	} else if (window.URL != undefined) { // mozilla(firefox)  
		url = window.URL.createObjectURL(file);
	} else if (window.webkitURL != undefined) { // webkit or chrome  
		url = window.webkitURL.createObjectURL(file);
	}
	return url;
}

/**
 * 展开全文与收起全文
 * 
 * //获取优化后的内容 var contentHtml =
 * initContentHtml(item.weiboId,"#weibo-content",item.content,70);
 * $(document).on("click", "#openContent", function(e) {
 * openContentClick(e,"#weibo-content",getWeiboContentUrl); });
 * $(document).on("click","#closeContent",function(e){
 * closeContentClick(e,"#weibo-content",getWeiboContentUrl,70); });
 * //需要去除@与昵称的话在调用三个函数的最后都加个参数“1” //css:{ #openContent,#closeContent {
 * text-decoration: none; color:#60a4e2; cursor: pointer; } #closeContent{
 * position:relative; } #closeContent span{ font-size:25px; position: absolute;
 * top:-2px; } }
 */
/**
 * 展开全文与收起全文 id：data-set的id newContentId:内容的初始Id content:需要设置的内容 maxwidth :
 * 设置最多显示的字数
 */
function initContentHtml(id, newContentId, content, maxwidth, type) {
	var html = '';
	var contentId = newContentId + id;
	initContent(contentId);
	function initContent(contentId) {
		$(contentId).find('a').remove();
		var text = content;
		if (type != null) {
			var str = content.substr(content.indexOf("\@"));
			var atNickName = str.substring(0, str.indexOf("\ "));
			if(!($.trim(atNickName)=='')){
				text = str.substring(atNickName.length, content.length);
			}
		}
		if (text.length > maxwidth) {
			html = text.substring(0, maxwidth)
					+ " ... "
					+ '<a id="openContent" data-id="'
					+ id
					+ '"'
					+ 'style="text-decoration: none;color:#60a4e2; cursor: pointer;">展开全文<span class="iconfont" '
					+ '>&#xe625;</span></a>';
		} else {
			html = text;
		}
	}
	return html;
}
/**
 * 展开全文的点击事件
 */
function openContentClick(e, newContentId, getContentUrl, type) {
	var id = e.currentTarget.dataset.id;
	var url = getContentUrl + id;
	$
			.getJSON(
					url,
					function(data) {
						if (data) {
							var content = data.content;
							if (type != null) {
								var str = data.content.substr(data.content
										.indexOf("\@"));
								var atNickName = str.substring(0, str
										.indexOf("\ "));
								if(!($.trim(atNickName)=='')){
									content = str.substring(atNickName.length,
											data.content.length);
								}
							}
							var contentId = newContentId + id;
							$(contentId).find('a').remove();
							var html = content
									+ '<a data-id="'
									+ id
									+ '" id="closeContent" '
									+ 'style="position:relative;text-decoration: none;color:#60a4e2;cursor: pointer;">  收起全文<span'
									+ 'style="font-size:25px;position: absolute;top:-2px;">^</span></a>';
							$(contentId).html(html);
						}
					});
}
/**
 * 收起全文的点击事件
 */
function closeContentClick(e, newContentId, getContentUrl, maxwidth, type) {
	var id = e.currentTarget.dataset.id;
	var url = getContentUrl + id;
	$
			.getJSON(
					url,
					function(data) {
						if (data) {
							var content = data.content;
							if (type != null) {
								var str = data.content.substr(data.content
										.indexOf("\@"));
								var atNickName = str.substring(0, str
										.indexOf("\ "));
								if(!($.trim(atNickName)=='')){
									content = str.substring(atNickName.length,
											data.content.length);
								}
							}
							var contentId = newContentId + id;
							$(contentId).find('a').remove();
							if (content.length > maxwidth) {
								html = content.substring(0, maxwidth)
										+ " ... "
										+ '<a data-id="'
										+ id
										+ '" id="openContent"'
										+ 'style="text-decoration: none;color:#60a4e2; cursor: pointer;"> 展开全文<span class="iconfont" '
										+ '>&#xe625;</span></a>';
							} else {
								html = content;
							}
							$(contentId).html(html);
						}
					});
}

/**
 * //格式化后日期为：yyyy-MM-dd
 * 
 * @param sDate
 * @returns
 */
function FormatDateToYMH(sDate) {
	var date = new Date(sDate);
	var seperator1 = "-";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	// 月
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	// 日
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	// 格式化后日期为：yyyy-MM-dd
	var currentdate = date.getFullYear() + seperator1 + month + seperator1
			+ strDate;
	return currentdate;
}

/**
 * //格式化后日期为：yyyy-MM-dd HH:mm:ss
 * 
 * @param sDate
 * @returns
 */
function FormatAllDateToYMDHMS(sDate) {
	var date = new Date(sDate);
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	var hours = date.getHours();
	var minutes = date.getMinutes();
	var seconds = date.getSeconds();
	// 月
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	// 日
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	// 时
	if (hours >= 0 && hours <= 9) {
		hours = "0" + hours;
	}
	// 分
	if (minutes >= 0 && minutes <= 9) {
		minutes = "0" + minutes;
	}
	// 秒
	if (seconds >= 0 && seconds <= 9) {
		seconds = "0" + seconds;
	}
	// 格式化后日期为：yyyy-MM-dd HH:mm:ss
	var currentdate = date.getFullYear() + seperator1 + month + seperator1
			+ strDate + " " + hours + seperator2 + minutes + seperator2
			+ seconds;
	return currentdate;
}