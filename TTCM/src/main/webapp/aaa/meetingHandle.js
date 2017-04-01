//该变量用于保存下标;
var inx = [];



function deleteMeeting(id){
	 //利用对话框返回的值 （true 或者 false）
    if(confirm("您确定要取消该会议么？")){
       //如果是true ，那么就把页面转向thcjp.cnblogs.com
    	var index = id.slice(13);
    	var meetingId = meetingList[index].meetingId;
    	//3为删除
    	var record = 3;
    	alert(index);
    	$.ajax({
    		url:"http://120.55.94.85:8088/TTCM/menus/updateRecordMeeting.do",
    		type:"POST",
    		data:{
    			meetingId:meetingId,
    			record:record
    			
    		},
    		dataType:"JSON",
    		success:function(result){
    			console.log(result);
    			if(result.state==SUCCESS){
    				selectMeetingAction();
    			}else{
    				console.log(result.message);
    				zeroModal.error(result.message);
    			}
    		}
    	});
    	
    	
     } else{
       
    }
}

function rejectMeeting(id){
	
	if(confirm("您确定要拒绝该会议么？")){
		var index =  id.slice(13);
		var meeting = meetingList[index];
		var userId =  localStorage.getItem("userId");
		$.ajax({
			url:"http://120.55.94.85:8088/TTCM/menus/rejectMeeting.do",
			type:"POST",
			data:{
				userId:userId,
				meetingId:meeting.meetingId
			},
			dataType:"JSON",
			success:function(result){
				console.log(result);
				if(result.state==SUCCESS){
					selectMeetingAction();
				}else{
					console.log(result.message);
					zeroModal.error(result.message);
				}
			}
			
		});
		
		
		
		
		
		
	}else{
		
	}
	
}

function showModifyMsg(id){
	attendeeUserId=[];
	hostUserId=[];
	VIPUserId=[];
	var index =  id.slice(13);
	
	inx=index;
	$("#meetingMessage").hide();
	$("#meetingFound").show();
	$("#order").val("修     改");
	console.log(meetingList[index]);
	var endDate= $("#"+id+"").parent().prev().text();
	var startDate = $("#"+id+"").parent().prev().prev().text();
	var startTime = startDate.slice(10);
	var endTime = endDate.slice(10);
	endDate = endDate.slice(0,10);
	startDate=startDate.slice(0,10);
	$("#startDate").val(startDate);
	$("#startTime").val(startTime);
	$("#endDate").val(endDate);
	$("#endTime").val(endTime);
	$("#GMT").val(meetingList[index].timezone);
//	console.log(meetingList[index].recurrence);
	
	
	var repeat= $(".repeat");
	var rep=document.getElementsByName("repeat");  
	if(meetingList[index].recurrence==="否"){
		repeat.eq(1).attr("checked","checked");
//		repeat.eq(1).attr("checked","true");
		
//		repeat.eq(1).attr("checked","false");
	}else{
		repeat.eq(0).attr("checked","true");
//		console.log(repeat);
//		console.log(meetingList[index].recurrence);
	}
	
	$("#subject").val(meetingList[index].subject);
	$("#description").val(meetingList[index].description);
	var delay=(((meetingList[index].delayTime)-(meetingList[index].endTime))/1000/60);
	console.log(delay);
	$("#delay").val(delay+"分钟");
	$("#attendee").text($("#attendee"+index).text());
	$("#host").text($("#host"+index).text());
	$("#vip").text($("#attendee"+index).attr("vip"));
	$.ajax({
		url:"http://120.55.94.85:8088/TTCM/menus/findMessage.do",
		type:"POST",
		data:{
			roomId:meetingList[index].roomId,
			meetingId:meetingList[index].meetingId
		},
		dataType:"JSON",
		success:function(result){
//			console.log(result);
			if(result.state==SUCCESS){
				//找出会议房间的信息，data[0]是room的信息，1是会议关系表的信息（用来显示参会者）
				$("#roomMessage").html("<b>房间名:</b>"+result.data[0].roomName+
						"  <b>电话:</b>"+result.data[0].roomPhone);
				var mrList = result.data[1];
//				console.log(roomId);
//				/**
//				 * 遍历roomId集合，判断当前roomId是否存在与roomId集合中
//				 * 如果，存在
//				 */
//				if(roomId.length>0){
//					for(var i = 0 ; i <roomId.length;i++){
//						if(!(roomId[i]==result.data[0].roomId)){
//							roomId.push(result.data[0].roomId);
//						}
//					}
//				}else{
//					roomId.push(result.data[0].roomId);
//				}
				currentRoomId=result.data[0].roomId;
				console.log("---------------");
//				console.log(inx);
//				console.log(meetingList[inx]);
				for(var i = 0 ; i<mrList.length;i++){
					attendeeUserId.push(mrList[i].userId);
					
					if(mrList[i].hostUser != null){
						hostUserId.push(mrList[i].hostUser)
					}
					if(mrList[i].vipUser != null){
						VIPUserId.push(mrList[i].vipUser)
					}
				}
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
	
//	attendeeUserId=[];
	
	
	
}




































