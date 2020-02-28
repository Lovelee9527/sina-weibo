$(function() {
	$.toast({
		text : "欢迎，快注册吧",
		position : 'top-center',//"bottom-left", "bottom-right", "top-right", "top-left", "bottom-center", "top-center","mid-center"
		icon:"info"
	});
	
	$('#submit').click(function() {
		var user={};
		var formData = new FormData();
		user.userName = $('#userName').val();
		user.password = $('#password').val(); 
		user.birthday = $('#birthday').val();
		if($.trim(user.userName)==""){
	    	$.toast({text:"用户名不能空",icon:"error"});
	    	return;
	    }
		if($.trim(user.password)==""){
	    	$.toast({text:"密码不能为空",icon:"error"});
	    	return;
	    }
		if($.trim(user.password).length<6){
	    	$.toast({text:"密码不可小于六位呢"});
	    	return;
	    }
		if($.trim(user.birthday)==""){
	    	$.toast({text:"生日不能为空",icon:"error"});
	    	return;
	    }
		var verifyCodeActual = $('#j_captcha').val();
		formData.append('verifyCodeActual',verifyCodeActual);
		if (!verifyCodeActual) {
			$.toast({text:'请输入验证码'});
			return;
		}
		formData.append('userStr',JSON.stringify(user));
		 $.ajax({
	            url:'/weibo/frontend/registeruser',
	            data:formData,
	            type:'post',
	            //ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
	            contentType:false,
	            //取消帮我们格式化数据，是什么就是什么
	            processData:false,
	            success:function(data){
//	                console.log(data);
					if (data.success) {
						/*$(".alert-delete").show();
						$(".tips-text").text("注册成功 ~ 要去登录吗？");
						$('#captcha-img').click();*/
						$.toast({
							text:"注册成功！！！",
							icon:"info"
					    }); 
						setTimeout(function(){
							window.location.href='/weibo/frontend/index?userName='+user.userName;
						},500);
					} else {
						$.toast({
							text:data.errMsg,
					    }); 
					}
	            }
	        });
	});
	
	/*$(".delete-yes").click(function(){
		window.location.href='/weibo/frontend/index';
	})*/
	
});