


function controlAction(){
	
	var currentDate = new Date();
	for(var i = 0 ; i<meetingList.length;i++){
		var startDate = new Date(meetingList[i].startTime);
		var endDate = new Date(meetingList[i].endTime);
		var meeting  = meetingList[i];
		//当 当前时间超过开始时间前半小时则进入，发送提示邮件，如果发过邮件就不发了
		if(currentDate >= (startDate.getTime()-1800000) && (meeting.isEmail!="1"&&meeting.isEmail!="2")){
			$.ajax({
				url:"../../menus/remind.do",
				type:"POST",
				dataType:"JSON",
				contentType : "application/json;charset=UTF-8",
				data:JSON.stringify({
						meeting:meeting,
						tail:1
				}),
//				JSON.stringify(meeting),
				success:function(result){
					console.log(result);
					if(result.state==SUCCESS){
						
					}else{
						console.log(result.message);
						zeroModal.error(result.message);
					}
				}
					
			});
			
		}
		
		
		//当 当前时间超过结束时间前半小时则进入，发送提醒邮件，确认是否,如果发过消息就不发了
		if(currentDate >= (endDate.getTime()-1800000) && meeting.isEmail!="2"){
			
			$.ajax({
				
				contentType:"application/json",
				url:"../../menus/remind.do",
				type:"POST",
				data:{
					meeting:JSON.stringify(meeting),
					tail:2
				},
				dataType:"JSON",
				success:function(result){
					console.log(result);
					if(result.state==SUCCESS){
						
					}else{
						console.log(result.message);
						zeroModal.error(result.message);
					}
				}
					
			});
			
		}
		
		
		//当 当前时间超过结束时间则进入，修改记录为2
		if(currentDate >=endDate ){
			
			$.ajax({
				url:"../../menus/updateRecordMeeting.do",
				type:"POST",
				data:{
					meetingId:meetingList[i].meetingId,
					record:2
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
		}
		
		
		
	}
}