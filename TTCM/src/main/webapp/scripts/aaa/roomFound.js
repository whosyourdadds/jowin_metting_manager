$(function(){
	$("#rFound").click(addRoomAction);
});

function addRoomAction(){
	var companyId =  getCookie("companyId");
	var userId =  getCookie("userId");
	
	var roomName = $("#rName").val();
	var roomPhone = $("#rPhone").val();
	var ciscoIp = $("#rCiscoIP").val();
	var roomPassword = $("#rPassword").val();
	var roomAddress = $("#rAddress").val();
	console.log(roomAddress);
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
			roomAddress:roomAddress
		},
		dataType:"JSON",
		success:function(result){
			if(result.state==SUCCESS){
				$('#content').fadeTo('slow', 0, function(){
					window.location="roomManage.html";
				}).fadeTo('slow', 1);
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
			
	});
}