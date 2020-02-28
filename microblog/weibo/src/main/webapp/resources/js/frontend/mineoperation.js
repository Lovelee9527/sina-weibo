$(function() {
	var userId = getQueryString('userId');
	var user={};
	var pageNum = 1;
	var pageSize= 9;
	
	getMineInfo()
	function getMineInfo(){
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
				}
			}
		});
	}
	
	getNickNames();
	function getNickNames() {
	// 获取除当前用户外的用户昵称路径
	var getNickNameListUrl = '/weibo/frontend/getnicknamelist'
		$.getJSON(getNickNameListUrl, function(data) {
			if (data.success) {
				// 监听符号@ 出现时查询用户昵称显示在@后面
				$('#content').atwho({
					at : "@",
					data : data.nickNameList
				})
			}
		});
	}

	var flag=false;
	getHotWeiboList(pageNum,pageSize);
	function getHotWeiboList(pageIndex,pageSize){
		var getHotWeiboListUrl = '/weibo/frontend/gethotweibolist'+'?pageIndex='+pageIndex+'&pageSize='+pageSize; 
		$.getJSON(getHotWeiboListUrl, function(data) {
			if (data.success) {
				var hotWeiboHtml='';
				if(data.hotWCount <= 0){
					hotWeiboHtml = `<a href="mine">
					    <li>该页没有微博了哦</li>
					     </a>`;
					flag=true;
				}else{
					flag=false;
					data.hotWeiboList.map(function(item,index){
						var num =(pageIndex-1)*pageSize+(index+1);
						var content = num+'.'+' #';
						content += item.content.substring(0,9);
						if(item.content.length>9){
							content +="..."
						}
						
						hotWeiboHtml += `<a href="weibodetail?userId=${userId}&weiboId=${item.weiboId}">
										    <li>${content}#</li>
									     </a>`;
					});
				}
				$(".hot-weibo-list").html(hotWeiboHtml);
			}
		});
	}
	
	$(".previou-page").click(function(){
		if(pageNum>1){
			pageNum--;
			getHotWeiboList(pageNum,pageSize);
		}
	});
	
    $(".next-page").click(function(){
    	if(!flag){
    		pageNum++;
    		getHotWeiboList(pageNum,pageSize);
		}
	});

});
