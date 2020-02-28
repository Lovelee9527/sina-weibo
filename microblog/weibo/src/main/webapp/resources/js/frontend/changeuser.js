$(function(){
	var userId = getQueryString('userId'); 
	var getUserInfoUrl='/weibo/frontend/getuserinfo?userId='+userId;
	var modifyUserUrl = '/weibo/frontend/modifyuser'
	var modifyUserPasswordUrl = '/weibo/frontend/modifyuserpassword'

	$.toast({text:"您可以在该页面修改信息和密码",icon:"info"});	
	
	getUserInfo();
	function getUserInfo(){
		$.getJSON(getUserInfoUrl,function(data){
			if(data.success){
				var user=data.user;
				if(user!=null){
					$('title').text(user.nickName+"个人资料的编辑 微博"); 
					$('#userName').text(user.userName);
					$('#profile').text(user.profile);
					$('#userName').attr("href","homepage?userId="+userId);
					$('#nickName').val(user.nickName);
					if(!user.sex){
						$('#female').attr("checked","checked");
					}else{
						$('#male').attr("checked","checked");
					}
					$('#province-option').text(user.province);
					$('#city-option').text(user.city);
					//格式化后日期为：yyyy-MM-dd
					$('#birthday').val(FormatDateToYMH(user.birthday));
				}else{
					$(".alert-delete").show();
					$(".tips-text").text("你还未登录呢，去登录？");
					$(".delete-yes").text("去登录");
					$(document).on("click",".delete-yes",function(){
						window.location.href='/weibo/frontend/index';
				    });
				}
			}else{
				$(".alert-delete").show();
				$(".tips-text").text("你还未登录呢，去登录？");
				$(".delete-yes").text("去登录");
				$(document).on("click",".delete-yes",function(){
					window.location.href='/weibo/frontend/index';
			    });
			}
		});
	}
	
	$('#submitPassword').click(function(){
	    if(!($('#password').val()&&$('#newPassword').val())){
	    	$.toast({text:"密码不能为空呢"});
	    	return;
	    }
	    if($.trim($('#password').val())==$.trim($('#newPassword').val())){
	    	$.toast({text:"新密码与原密码不可以相同呢"});
	    	return;
	    }
	    if($.trim($('#newPassword').val()).length<6){
	    	$.toast({text:"密码不可小于六位呢"});
	    	return;
	    }
	    password=$('#password').val();
	    newPassword=$('#newPassword').val();
	    var formData=new FormData();
	    formData.append('userId',userId);
	    formData.append('password',password);
	    formData.append('newPassword',newPassword);
	    $.ajax({
            url:modifyUserPasswordUrl,
            data:formData,
            type:'post',
            //ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
            contentType:false,
            //取消帮我们格式化数据，是什么就是什么
            processData:false,
            success:function(data){
            	if(data.success){
            		$.toast({text:'修改成功 请重新登录',icon:'success',position:"mid-center"});
            		setTimeout(function(){ 
            			window.location.href='/weibo/frontend/index';
            			}, 2000);
//            		if(confirm("修改成功 请重新登录")){
//            			window.location.href='/weibo/frontend/index';
//            		}
            	}else{
            		$.toast({text:'修改失败 ~'+data.errMsg,icon:'error'});
            	}
            }
        });
	});
	
	$(".delete-yes").click(function(){
		window.location.href='/weibo/frontend/homepage?userId='+userId;
	});
	
	$('#submitUser').click(function(){
		var user={};
		user.userId = userId;
	    if(!$('#nickName').val()){
	    	$.toast({text:"昵称不能为空欧"});
	    	return;
	    }
	    user.nickName=$('#nickName').val();
	    user.profile =$('#profile').val();
	    user.sex = $('#wrap-sex input[name="sex"]:checked ').val();
        if('省份'!=$.trim($('#province').val())){
        	user.province = $('#province').val();
        }
        if('城市'!=$.trim($('#city').val())){
        	user.city = $('#city').val();
        }
	    user.birthday = $('#birthday').val();
	    var formData=new FormData();
	    formData.append('userStr',JSON.stringify(user));
	    $.ajax({
            url:modifyUserUrl,
            data:formData,
            type:'post',
            //ajax2.0可以不用设置请求头，但是jq帮我们自动设置了，这样的话需要我们自己取消掉
            contentType:false,
            //取消帮我们格式化数据，是什么就是什么
            processData:false,
            success:function(data){
            	if(data.success){
//            		$.toast({text:'修改成功 可以点击您的登录名返回',icon:'success'});
            		getUserInfo();
            		$(".alert-delete").show();
					$(".tips-text").text("修改成功 需要返回您的主页？");
            	}else{
            		$.toast({text:'修改失败 '+data.errMsg,icon:'error'});
            	}
            }
        });
	});
	
});