$(function() {
	$.toast({
		text : "请登录 - ",
		position:"top-center",
		icon:"info"
	});
//	var userName = $('#userName').val("you");
//	var password = $('#password').val("123456");
	var userName = getQueryString('userName');
	if(userName){
		$('#userName').val(userName);
	}
	// 在输入框内点击回车键进行登录
	/*$(".form-bgc").keypress(function(event) {
		var keynum = (event.keyCode ? event.keyCode : event.which);
		if (keynum == '13') {
//			loginUser();
		}
	});*/
	$('#submit').click(function() {
	     loginUser();
	});
	/**
	 * 用户登录
	 */
	function loginUser(){
		var userName = $('#userName').val();
		var password = $('#password').val(); 
		if($.trim(userName)==""){
	    	$.toast({text:"用户名为空",icon:"error"});
	    	return;
	    }
		if($.trim(password)==""){
	    	$.toast({text:"密码为空",icon:"error"});
	    	return;
	    }
		$.ajax({
			url : "/weibo/frontend/login",
			async:false,//async为异步传输。false则为同步传输。
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password
			},
			success : function(data) {
				if (data.success) {
					$.toast({
						text:'登录成功',
						icon:"success"
				    }); 
					setTimeout(function(){
						window.location.href='/weibo/frontend/mine';
					},500);
				} else {
					$.toast({
						text:'用户名或密码错误'
				    }); 
				}
			}
		});
	}
});