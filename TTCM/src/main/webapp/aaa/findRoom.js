var roomList ={};

$(function(){
	selectFindRoomAction();
	$("#updateRoom").hide();
	$("#add").click(function (){window.location="roomRegist.html"});
});

function selectFindRoomAction(){
	var companyId= localStorage.getItem("companyId");
	$.ajax({
		url:"http://120.55.94.85:8088/TTCM/menus/findRoom.do",
		type:"POST",
		data:{
			companyId:companyId
		},
		dataType:"JSON",
		success:function(result){
			if(result.state==SUCCESS){
//				console.log(result.data);
				roomList = result.data;
				paintRoom();
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
}



function paintRoom(){
	var table = $("#room").empty();
	var tr= '<tr class="title">'+
				'<td style="width: 60px;" align="center" valign="middle">状&nbsp;态</td>'+
				'<td  style="width: 150px;" align="center" valign="middle" >房间名</td>'+
				'<td align="center" valign="middle">电&nbsp;话</td>'+
				'<td style="width: 60px;" align="center" valign="middle">室&nbsp;温</td>'+
				'<td align="center" valign="middle" style="width: 150px;">地&nbsp;址</td>'+
				'<td align="center" valign="middle">&nbsp;&nbsp;&nbsp;</td>'+
			'</tr>'
	table.append(tr);			
	for(var i=0;i<roomList.length;i++){
		var room = roomList[i];
		console.log(room);
		tr= '<tr>'+
				'<td align="center" valign="middle" id="roomStatus'+i+'">'+
					room.roomStatus+'</td>'+
				'<td align="center" valign="middle" id="roomName'+i+'">'+
					room.roomName+'</td>'+
				'<td align="center" valign="middle" id="roomPhone'+i+'">'+
					room.roomPhone+'</td>'+
				'<td align="center" valign="middle" id="temperature'+i+'">'+
					room.temperature+'</td>'+
				'<td align="center" valign="middle" id="roomAddress'+i+'">'+
					room.roomAddress+'</td>'+
				'<td align="center" valign="middle" >'+
					'<input type="button" id="delete'+i+'" value="删&nbsp;除" onclick="deleteRoomAction(this.id)"> '+
					'<input type="button" id="updata'+i+'" value="修&nbsp;改" onclick="updateRoomAction(this.id)">'+
				' </td>'+'</tr>';
		table.append(tr);
	}
}



function deleteRoomAction(id){
	var index=id.substring(6);
	var roomId = roomList[index].roomId;
	if(confirm("您确定要删除该房间？删除后，该房间的相关会议会被一并删除哦~")){
		$.ajax({
			url:"http://120.55.94.85:8088/TTCM/menus/deleteRoom.do",
			type:"POST",
			data:{
				roomId:roomId
			},
			dataType:"JSON",
			success:function(result){
				if(result.state==SUCCESS){
//					console.log(result.data);
					roomList[index] = result.data;
//					paintRoom();
					selectFindRoomAction();
				}else{
					console.log(result.message);
					zeroModal.error(result.message);
				}
			}
		});
	}else{
		
	}
	
}

function updateRoomAction(id){
	$("#room").hide();
	$("#updateRoom").show();
	var index=id.substring(6);
	var isCancel = null;
	
	
	
	$("#rUpdateCancel").click(function(){
		$("#room").show();
		$("#updateRoom").hide();
		isCancel = true;
	});
	$("#rUpdate").click(function(){
		var roomId = roomList[index].roomId;
		var roomName = $("#updateRoomName").val();
		var roomPhone = $("#updateRoomPhone").val();
		var ciscoIp = $("#updateRoomCiscoIP").val();
		var roomPassword = $("#updateRoomPassword").val();
		var roomAddress = $("#updateRoomAddress").val();
		if(isCancel == true ){
			return;
		}
		$.ajax({
			url:"http://120.55.94.85:8088/TTCM/menus/updateRoom.do",
			type:"POST",
			data:{
				roomId:roomId,
				roomName:roomName,
				roomPhone:roomPhone,
				maxUser:ciscoIp,
				roomPassword:roomPassword,
				roomAddress:roomAddress
			},
			dataType:"JSON",
			success:function(result){
				if(result.state==SUCCESS){
					console.log(result.data);
					selectFindRoomAction();
					$("#room").show();
					$("#updateRoom").hide();
					isCancel=true;
				}else{
					console.log(result.message);
					zeroModal.error(result.message);
				}
			}
		});
	});
}
