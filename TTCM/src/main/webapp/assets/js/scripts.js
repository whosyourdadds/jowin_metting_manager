
jQuery(document).ready(function() {

    $('.page-container #loginForm').submit(function(){
        var username = $('.username').val();
        var password = $('.password').val();
        if(username == '') {
//        	console.log($(this));
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '27px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.username').focus();
            });
            return false;
        }
        if(password == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '96px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.password').focus();
            });
            return false;
        }
    });

    $('.page-container #loginForm .username, .page-container #loginForm .password').keyup(function(){
        $(this).parent().find('.error').fadeOut('fast');
    });
//    console.log("30")
    
    	
    $('#regist #company #cName, #regist #company #cAddress ,'+
    		'#regist #company #cPhone , #regist #company #cEmail ,'+
    		'#regist #company #cFax ').keyup(function(){
        $(this).parent().find('.error1').fadeOut('fast');
        $(this).css("border","1px solid rgba(255,255,255,.15)")
        $("#chint").html("");
    });
    
    $('#regist #admin #adminName,'+
    		'#regist #admin #adminPassword, #regist #admin #adminEmail ,'+
    		'#regist #admin #adminModile ,#regist #admin #adminPhone').keyup(function(){
        $(this).parent().find('.error2').fadeOut('fast');
        $(this).css("border","1px solid rgba(255,255,255,.15)")
        $("#ahint").html("");
    });
    
    $('#regist #room #roomPhone,'+
    		'#regist #room #roomPassword').keyup(function(){
        $(this).parent().find('.error3').fadeOut('fast');
        $(this).css("border","1px solid rgba(255,255,255,.15)")
        $("#rhint").html("");
    });
    
    $('#regist #user #uCompanyName,#regist #user #uName, '+
			'#regist #user #uPassword,#regist #user #uEmail,'+
			'#regist #user #uModile,#regist #user #uPhone,'+
			'#regist #user #uJob').keyup(function(){
		$(this).parent().find('.error4').fadeOut('fast');
		$(this).css("border","1px solid rgba(255,255,255,.15)")
		$("#uhint").html("");
	});
    
    
    
    
});
