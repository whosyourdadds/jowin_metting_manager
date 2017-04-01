var roomList ={};

$(function(){
	selectFindRoomAction();
//	$("#updateRoom").hide();
	$("#add").click(function (){
		$('#content').fadeTo('slow', 0, function(){
			$("#rMsg").hide();
			$("#updateRoom").hide();
			$(".RRegist").show();
		}).fadeTo('slow', 1);
	});
	$("#manage").click(function(){
		$('#content').fadeTo('slow', 0, function(){
			window.location="roomManage.html";
		}).fadeTo('slow', 1);
	});
});

function selectFindRoomAction(){
	var companyId= getCookie("companyId");
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
				'<th  align="center" valign="middle">状&nbsp;态</th>'+
				'<th  align="center" valign="middle" >房间名</th>'+
				'<th  align="center" valign="middle">电&nbsp;话</th>'+
				'<th  align="center" valign="middle">室&nbsp;温</th>'+
				'<th  align="center" valign="middle" >地&nbsp;址</th>'+
				'<th  align="center" valign="middle">操&nbsp;作</th>'+
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
					'<input class="btn btn-info btn-sm" type="button" id="updata'+i+'" value="修&nbsp;改" onclick="updateRoomAction(this.id)">'+
					'<input class="btn btn-sm" type="button" id="delete'+i+'" value="删&nbsp;除" onclick="deleteRoomAction(this.id)"> '+
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
	$('#content').fadeTo('slow', 0, function(){
		$("#rMsg").hide();
		$("#updateRoom").show();
		$(".RRegist").hide();
	}).fadeTo('slow', 1);
	var index=id.substring(6);
	
	
	
	
//	$("#rUpdateCancel").click(function(){
//		$("#room").show();
//		$("#updateRoom").hide();
//		isCancel = true;
//	});
	$("#rUpdate").click(function(){
		var roomId = roomList[index].roomId;
		var roomName = $("#updateRoomName").val();
		var roomPhone = $("#updateRoomPhone").val();
		var ciscoIp = $("#updateRoomCiscoIP").val();
		var roomPassword = $("#updateRoomPassword").val();
		var roomAddress = $("#updateRoomAddress").val();
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
					$('#content').fadeTo('slow', 0, function(){
						window.location="roomManage.html";
					}).fadeTo('slow', 1);
				}else{
					console.log(result.message);
					zeroModal.error(result.message);
				}
			}
		});
	});
}
