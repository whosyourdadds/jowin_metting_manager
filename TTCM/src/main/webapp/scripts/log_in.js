/*
 * scripts/log_in.js
 * 登录界面中执行的脚本程序
 */ 
//console.log("呵呵");
//网页加载以后执行

//var SUCCESS=0;  移动到 const.js 中
//var ERROR=1;

$(function(){
	//为login按钮绑定事件
	$('#login').click(loginAction);
	
	$('#regist_button').click(registAction);
	
	$('#code').blur(function(){
		var code=$(this).val();
		var url=baseUrl+"/account/checkCode.do?code="+code;
		$.getJSON(url, function(result){
			if(result.state==SUCCESS){
				$('#code').removeClass('error');
			}else{
				$('#code').addClass('error');
			}
		});
	});
	
	$('#codeImg').click(function(){
		var url=baseUrl+"/account/code.do?"+Math.random();
		$(this).attr('src', url);
	});

});

function registAction(){
//	/console.log("registAction click");
	var name = $('#regist_username').val();
	var nick = $('#nickname').val();
	var password = $('#regist_password').val();
	var confirm=$('#final_password').val();
	var pass=true;
	var nameReg=/^\w{3,10}$/;
	var nickReg=/^.{3,10}$/;
	$('input').removeClass("error");
	$('.warning').hide();
	if(! nameReg.test(name)){
		pass=false;
		$('#regist_username').addClass("error");
		$('#warning_name>span')
		.html("3~10个单词字符").parent().show();
	}
	if(! nickReg.test(nick)){
		pass = false;
		$('#nickname').addClass("error");
		$('#warning_nick>span')
		.html("3~10个字符").parent().show();
	}
	if(! nameReg.test(password)){
		pass=false;
		$('#regist_password')
		.addClass("error")
		.next().show()
		.html('<span>3~10单词字符</span>');
	}
	if(! nameReg.test(confirm)){
		pass=false;
		$('#final_password')
		.addClass("error")
		.next().show()
		.html('<span>3~10单词字符</span>');
	}
	if( password != confirm){
		pass=false;
		$('#final_password')
		.addClass("error")
		.next().show()
		.html('<span>密码不一致</span>');
	}
	if(! pass){
		return false;
	}
	$.ajax({
		url: 'account/regist.do',
		type:'POST',
		data:{'name':name, 'password':password, 'nick':nick},
		dataType:'json',
		success:function(result){
			if(result.state == SUCCESS){
				console.log(result);
				$('input[type=text], input[type=password]').val('');
				$('#count').val(result.data.name);
				$('#password').focus();//移动输入焦点
				$('#back').click();
			}else{
				//alert(result.message);
				$('#regist_username').addClass("error");
				$('#warning_name>span')
				.html(result.message).parent().show();
			}
		}
	});
}

function loginAction(){
	//console.log("login click");
	//检查表单数据的正确性
	//将表单数据发送到服务器，
	//利用Callback 处理返回结果
	//如果成功就跳转到主页
	//如果失败显示错误消息
	var name = $('#count').val();
	var password=$('#password').val();
	var code=$('#code').val();
	//console.log(name+", "+ password);
	var reg = /^\w{3,10}$/;
	$('#count').removeClass('error');
	$('#password').removeClass('error');
	var pass = true;
	if(! reg.test(name)){
		$('#count').addClass('error');
		pass=false;
	}
	if(! reg.test(password)){
		$('#password').addClass('error');
		pass=false;
	}
	if(! pass){
		return false;
	}
	//已经获得了正确数据
	var data = {'name':name,
		'password':password, 'code':code};
	//console.log(data);
	$.ajax({
		url:"account/login.do",
		type:"post",
		dataType:"JSON",
		data:data,
		success:function(result){
			//console.log(result);
			if(result.state==SUCCESS){
				var user=result.data;
				setCookie("userId",user.id);
				setCookie("userName",user.name);
				setCookie("userNick",user.nick);
				window.location="edit.html";
			}else{
				alert(result.message);
			}
		}
	});
}




