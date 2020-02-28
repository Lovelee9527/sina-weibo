var userId = getQueryString('userId');
var user = '';
//获取mine页面数据路径
var getMineInfoUrl = '/weibo/frontend/getmineinfo?userId=' + userId;
// 获取微博内容
var getWeiboContentUrl = '/weibo/frontend/getweibocontent' + '?weiboId=';

getMineInfo();
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
			}
		}
	});
}

// 获取该用户昵称的用户Id
var getUserByNickNameUrl = '/weibo/frontend/getuserbynickname'
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

/**
 * 微博卡片中的点击事件
 */
$(document).on("click", ".arrow", function(event) {
	var weiboId = event.currentTarget.dataset.id;
	event.stopPropagation();
	var id = "#arrow-context" + weiboId
	$(id).toggle();
});

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
			var cancelHtml = '<a id="cancelRelation" data-id="' + userId
					+ '" style="cursor: pointer;">取消关注</a>';
			var addHtml = '<a id="addRelation" data-id="' + userId
					+ '" style="cursor: pointer;">添加关注</a>';
			var deleteHtml = '<a id="deleteWeibo" data-id="' + weiboId
					+ '" style="cursor: pointer;">删除微博</a>';
			if (data.success) {
				var state = data.state;
				var id = "#relation-li" + weiboId;
				if (state == 0 || state == 1) {
					// $(id).html(cancelHtml);
					html = cancelHtml;
				} else if (state == -1 || state == null) {
					html = addHtml;
				} else if (state == 2) {
					html = deleteHtml;
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


