var company;
var admin;


$(function(){
	$("#companyRegist").click(function (){
							$("#login").hide(500);
							$("#company").show(500);
							$("#regist input").val("");
						});
	$("#cback").click(function(){
		$("#company").hide(500);$("#login").show(500);
	});
	
	
	$("#cSave").click(cSaveAction);//公司创建页面，点击下一步
	$("#adminRegist").click(adminRegistAction);//管理员创建页面，点击注册
	$("#adminCancel").click(adminCancelAction);//管理员创建页面，点击取消
	$("#roomFound").click(roomFoundAction);//房间创建页面，点击创建
	$("#roomCancel").click(function(){
		$("#room").hide(500);
		$("#login").show(500);
	});
	
	//以上是公司注册的代码
	//下面写用户澄清的代码
	$("#userRegist").click(function(){
		$("#regist input").val("");
		$("#login").hide(500);
		$("#user").show(500);
	});
	$("#cancel").click(function(){
		$("#login").show(500);
		$("#user").hide(500);
	});
	$("#applyFor").click(userRegistAction);
	
	
	
	
	
});


//公司注册过程
function cSaveAction(){
//	console.log("进入公司注册")
	var cName = $('#cName').val();
	var cAddress = $('#cAddress').val();
	var cPhone = $('#cPhone').val();
	var cEmail = $('#cEmail').val();
	var cFax = $('#cFax').val();
	var cWeb = $("#cWeb").val();
	var emailReg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})?(\.[a-zA-Z]{2,})+$/;
	//电话和传真是一个正则
	var faxReg = /^(\d{3,4}-)?\d{7,8}$/;
//	console.log(cEmail);
//	console.log(!emailReg.test(cEmail));
//	console.log(cName);	
	//error出现叉
//	$("#company").hide(500);
//	$("#admin").show(500);
//	return false;
	if(cName == ''||cName == null) {
        $("#company").find('.error1').fadeOut('fast', function(){
            $(this).css('top', '27px');
        });
        $("#company").find('.error1').fadeIn('fast', function(){
            $(this).parent().find('#cName').focus();
        });
        return false;
    }
	
	if(cAddress == ''||cAddress == null) {
        $("#company").find('.error1').fadeOut('fast', function(){
            $(this).css('top', '96px');
        });
        $("#company").find('.error1').fadeIn('fast', function(){
            $(this).parent().find('#cAddress').focus();
        });
        return false;
    }
	if(!faxReg.test(cPhone)){
		$("#company").find('.error1').fadeOut('fast', function(){
            $(this).css('top', '165px');
        });
		 $("#company").find('.error1').fadeIn('fast', function(){
            $(this).parent().find('#cPhone').focus();
        });
		 if(cPhone == ''|| cPhone == null) {
			 return false;
		 }
		 $("#chint").html("电话格式为:XXXX-12345678");
		 $("#cPhone").css("border","1px solid rgba(255,0,0,.6)");
        return false;
	}
	
	if(!emailReg.test(cEmail)){
		$("#company").find('.error1').fadeOut('fast', function(){
            $(this).css('top', '234px');
        });
		 $("#company").find('.error1').fadeIn('fast', function(){
            $(this).parent().find('#cEmail').focus();
        });
		 if(cEmail == ''|| cEmail == null) {
			 return false;
		 }
		 $("#cEmail").css("border","1px solid rgba(255,0,0,.6)");
		$("#chint").html("邮箱格式错误");
        return false;
	}
	
	if(!faxReg.test(cFax)){
		$("#company").find('.error1').fadeOut('fast', function(){
            $(this).css('top', '303px');
        });
		 $("#company").find('.error1').fadeIn('fast', function(){
            $(this).parent().find('#cFax').focus();
        });
		 if(cFax == ''|| cFax == null) {
			 return false;
		 }
		 $("#cFax").css("border","1px solid rgba(255,0,0,.6)");
		 $("#chint").html("传真格式为:XXXX-12345678");
		 
        return false;
	}
	
	
	
	//如果都不为空，继续下一步执行
	
	$.ajax({
		url:"http://120.55.94.85:8088/companyRegist/company.do",
		type:"POST",
		data:{
			companyName:cName,
			companyAddress:cAddress,
			companyPhone:cPhone,
			companyEmail:cEmail,
			companyFax:cFax,
			companyWebsite:cWeb
		},
		dataType:"JSON",
		success:function(result){
			console.log(result.data);
			if(result.state == SUCCESS){
				company=result.data;
				$("#company").hide(500);
				$("#admin").show(500);
				
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
}


//管理员注册过程
function adminRegistAction (){
//	console.log("进入管理员注册");
	var companyId= company.companyId;
	console.log(companyId);
	var name=$("#adminName").val();
	var pwd=$("#adminPassword").val();
	var email=$("#adminEmail").val();
	var modile=$("#adminModile").val();
	var phone=$("#adminPhone").val();
	var job = $("#aJob").val();
	//字母开头，允许字母、数字和-.2个特殊字符
	var pwdReg=/^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){5,15}$/;   
	var emailReg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})?(\.[a-zA-Z]{2,})+$/;
	var mReg = /^1[0-9]{10}$/;
	//电话
	var phoneReg = /^(\d{3,4}-)?\d{7,8}$/;
	
	
//	$("#admin").hide(500);
//	$("#room").show(500);
//	return false;
	if(name == ''||name == null) {
        $("#admin").find('.error2').fadeOut('fast', function(){
            $(this).css('top', '27px');
        });
        $("#admin").find('.error2').fadeIn('fast', function(){
            $(this).parent().find('#adminName').focus();
        });
        return false;
    }
	
	if(!pwdReg.test(pwd)){
		$("#admin").find('.error2').fadeOut('fast', function(){
            $(this).css('top', '96px');
        });
		 $("#admin").find('.error2').fadeIn('fast', function(){
            $(this).parent().find('#adminPassword').focus();
        });
		 if(pwd == ''||pwd == null) {
			 return false;
		 }
		 $("#ahint").html("密码格式:长度6-16，字母开头，允许_和.");
		 $("#adminPassword").css("border","1px solid rgba(255,0,0,.6)");
        return false;
	}
	
	if(!emailReg.test(email)){
		$("#admin").find('.error2').fadeOut('fast', function(){
            $(this).css('top', '165px');
        });
		 $("#admin").find('.error2').fadeIn('fast', function(){
            $(this).parent().find('#adminEmail').focus();
        });
		 if(email == ''||email == null){
			 return false;
		 }
		 $("#ahint").html("邮箱格式错误");
		 $("#adminEmail").css("border","1px solid rgba(255,0,0,.6)");
        return false;
	}
	
	if(!mReg.test(modile)){
		$("#admin").find('.error2').fadeOut('fast', function(){
            $(this).css('top', '234px');
        });
		 $("#admin").find('.error2').fadeIn('fast', function(){
            $(this).parent().find('#adminModile').focus();
        });
		 if(modile == ''||modile == null){
			 return false;
		 }
		 $("#ahint").html("手机格式错误");
		 $("#adminModile").css("border","1px solid rgba(255,0,0,.6)");
        return false;
	}
	
	//因为是选填的，所以为空就跳过，不为空则检查格式
	if(!(phone=="" || phone==null)  ){
//		console.log(phone);
//		console.log(phone!="" || phone!=null );
		if(!phoneReg.test(phone)){
			$("#admin").find('.error2').fadeOut('fast', function(){
	            $(this).css('top', '303px');
	        });
			 $("#admin").find('.error2').fadeIn('fast', function(){
	            $(this).parent().find('#adminPhone').focus();
	        });
			 
			 $("#ahint").html("电话格式为:XXXX-12345678");
			 $("#adminPhone").css("border","1px solid rgba(255,0,0,.6)");
	        return false;
		}
	}
	
	
	
	$.ajax({
		url:"http://120.55.94.85:8088/TTCM/companyRegist/admin.do",
		type:"POST",
		data:{
			name:name,
			email:email,
			modile:modile,
			phone:phone,
			password:pwd,
			companyId:companyId,
			job:job
		},
		dataType:"JSON",
		success:function(result){
			if(result.state == SUCCESS){
				console.log(result.data.companyId);
				$("#admin").hide(500);
				$("#room").show(500);
				admin=result.data;
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
}

function adminCancelAction(){
	var companyId = company.companyId;
	$.ajax({
		url:"http://120.55.94.85:8088/TTCM/companyRegist/adminCancel.do",
		type:"POST",
		data:{
			companyId:companyId},
		dataType:"JSON",
		success:function(result){
			console.log(result)
			if(result.state == SUCCESS){
				window.location="companyRegist.html";
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
}

function roomFoundAction(){
	
	var roomName = $("#roomName").val();
	
	var roomPhone = $("#roomPhone").val();
	var ciscoIp = $("#ciscoIP").val();
	var roomPassword = $("#roomPassword").val();
	var roomAddress = $("#roomAddress").val();
//	console.log(roomAddress);
	var companyId = company.companyId;
	var userId = admin.id;
	//字母开头，允许字母、数字和-.2个特殊字符
	var pwdReg=/^(\w{3,10})$/;  
	var phoneReg = /^(\d{3,4}-)?\d{7,8}$/;
	
	
	if(roomName == ''||roomName == null) {
       roomName = "未命名";
    }
//	console.log(!(roomPhone=="" || roomPhone==null))
	if(!(roomPhone=="" || roomPhone==null)  ){
//		console.log(phone);
//		console.log(phone!="" || phone!=null );
		if(!phoneReg.test(roomPhone)){
			$("#room").find('.error3').fadeOut('fast', function(){
	            $(this).css('top', '96px');
	        });
			 $("#room").find('.error3').fadeIn('fast', function(){
	            $(this).parent().find('#roomPhone').focus();
	        });
			 
			 $("#rhint").html("电话格式为:XXXX-12345678");
			 $("#roomPhone").css("border","1px solid rgba(255,0,0,.6)");
	        return false;
		}
	}
	
	if(!pwdReg.test(roomPassword)){
		$("#room").find('.error3').fadeOut('fast', function(){
            $(this).css('top', '234px');
        });
		 $("#room").find('.error3').fadeIn('fast', function(){
            $(this).parent().find('#roomPassword').focus();
        });
		 if(roomPassword == ''||roomPassword == null) {
			 return false;
		 }
		 $("#rhint").html("密码格式:长度3-10，不允许有特殊字符");
		 $("#roomPassword").css("border","1px solid rgba(255,0,0,.6)");
        return false;
	}
	console.log(roomName);
	
	$.ajax({
		url:"http://120.55.94.85:8088/TTCM/companyRegist/roomFound.do",
		type:"POST",
		data:{
			companyId:companyId,
			userId:userId,
			roomName:roomName,
			roomPhone:roomPhone,
			maxUser:ciscoIp,
			roomPassword:roomPassword,
			roomAddress:roomAddress},
		dataType:"JSON",
		success:function(result){
			if(result.state == SUCCESS){
				$("#room").hide(500);
				$("#login").show(500);
				console.log(result.data);
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
}

//以上是公司注册的代码
//下面写用户澄清的代码
function userRegistAction(){
	var companyName = $("#uCompanyName").val();
	var name=$("#uName").val();
	var pwd=$("#uPassword").val();
	var email = $("#uEmail").val();
	var modile= $("#uModile").val();
	var phone = $("#uPhone").val();
	var job = $("#uJob").val();
	
	
	var pwdReg=/^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){5,15}$/;   
	var emailReg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})?(\.[a-zA-Z]{2,})+$/;
	var mReg = /^1[0-9]{10}$/;
	//电话
	var phoneReg = /^(\d{3,4}-)?\d{7,8}$/;
	
	if(companyName == ''||companyName == null) {
        $("#user").find('.error4').fadeOut('fast', function(){
            $(this).css('top', '27px');
        });
        $("#user").find('.error4').fadeIn('fast', function(){
            $(this).parent().find('#uCompanyName').focus();
        });
        return false;
    }
	
	if(name == ''||name == null) {
        $("#user").find('.error4').fadeOut('fast', function(){
            $(this).css('top', '96px');
        });
        $("#user").find('.error4').fadeIn('fast', function(){
            $(this).parent().find('#uName').focus();
        });
        return false;
    }
	
	if(!pwdReg.test(pwd)){
		$("#user").find('.error4').fadeOut('fast', function(){
            $(this).css('top', '165px');
        });
		 $("#user").find('.error4').fadeIn('fast', function(){
            $(this).parent().find('#uPassword').focus();
        });
		 if(pwd == ''||pwd == null) {
			 return false;
		 }
		 $("#uhint").html("密码格式:长度6-16，字母开头，允许_和.");
		 $("#uPassword").css("border","1px solid rgba(255,0,0,.6)");
        return false;
	}
	
	if(!emailReg.test(email)){
		$("#user").find('.error4').fadeOut('fast', function(){
            $(this).css('top', '234px');
        });
		 $("#user").find('.error4').fadeIn('fast', function(){
            $(this).parent().find('#uEmail').focus();
        });
		 if(email == ''||email == null){
			 return false;
		 }
		 $("#uhint").html("邮箱格式错误");
		 $("#uEmail").css("border","1px solid rgba(255,0,0,.6)");
        return false;
	}
	
	if(!mReg.test(modile)){
		$("#user").find('.error4').fadeOut('fast', function(){
            $(this).css('top', '303px');
        });
		 $("#user").find('.error4').fadeIn('fast', function(){
            $(this).parent().find('#uModile').focus();
        });
		 if(modile == ''||modile == null){
			 return false;
		 }
		 $("#uhint").html("手机格式错误");
		 $("#uModile").css("border","1px solid rgba(255,0,0,.6)");
        return false;
	}
	
	//因为是选填的，所以为空就跳过，不为空则检查格式
	if(!(phone=="" || phone==null)  ){
//		console.log(phone);
//		console.log(phone!="" || phone!=null );
		if(!phoneReg.test(phone)){
			$("#user").find('.error4').fadeOut('fast', function(){
	            $(this).css('top', '372px');
	        });
			 $("#user").find('.error4').fadeIn('fast', function(){
	            $(this).parent().find('#uPhone').focus();
	        });
			 
			 $("#uhint").html("电话格式为:XXXX-12345678");
			 $("#uPhone").css("border","1px solid rgba(255,0,0,.6)");
	        return false;
		}
	}
	
	
//	console.log("ajax");
	$.ajax({
		url:"http://120.55.94.85:8088/TTCM/user/regist.do",
		async: false, 
		type:"POST",
		data:{
			companyName:companyName,
			name:name,
			password:pwd,
			email:email,
			modile:modile,
			phone:phone,
			job:job
		},
		dataType:"JSON",
		success:function(result){
			console.log(result.data);
			if(result.state==SUCCESS){
				
				zeroModal.show({
		            title: '注意',
		            content: '等待管理员同意，请稍后。。。',
		            dragHandle: 'container',
		            ok: true
		        });
//				console.log("跳");
				$("#login").show(500);
				$("#user").hide(500);
				
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
	 
	
}


function login(){
	
	var modile = $("#username").val();
	var password = $("#password").val();
//	console.log(modile+"||"+password);
	
	$.ajax({
		async: false, 
//		url:"http://120.55.94.85:8088/TTCM/login/check.do",
		url:"login/check.do",
		type:"POST",
		data:{modile:modile,password:password},
		dataType:"JSON",
		success:function(result){
			if(result.state == SUCCESS){
				var user=result.data;
				console.log(user)
				setCookie("user",user);
				setCookie("userId",user.id);
				setCookie("userName",user.name);
				setCookie("powerId",user.powerId);
				setCookie("companyId",user.companyId);
				setCookie("userModile",user.modile);
//				localStorage.setItem(
//				setCookie(
//				setCookie(
//				setCookie(
////				setCookie("meetingId",user.meetingId);
//				setCookie(
//				setCookie(
//				setCookie(
//				console.log(localStorage.getItem('companyId'));
//				console.log(localStorage.getItem('userName'));
//				alert(user.name);
				window.location="homepage/order/order.html";
//				window.location="http://120.55.94.85:8088/TTCM/homepage/order/order.html";
				return true;
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
	return false;
	
}










