
/**
 * meetingList保存当前状态为1的会议
 */
var meetingList = null;
var attendeeUserId=[];
var roomId=[];
var hostUserId=[];
var VIPUserId=[];
//_flag用于控制选择房间后显示的房间表格
var _flag=true;

$(function(){
	
	
	
	$("#roomtable").hide();
	
	$("#chooseRoom").click(chooseRoomAction);
	$('#unlimited').click(isUnlimitedAction);
	
	
	$("#myMeeting").click(function(){
		$("#meetingMessage").show();
		$("#schedule").hide();
	});
	$("#calendarMeeting").click(function(){
		$("#meetingMessage").hide();
		$("#schedule").show();
	});
	$("#backMeeting").click(function(){
		location.reload();
	});
	$("#order").click(orderMeetingAction);
	$("#addMeeting").click(function(){
//		console.log($("#meetingFound input").not("#meetingFound input:button"))
		
		$("#meetingMessage").hide();
		$("#meetingFound").show();
		$("#meetingFound input").not("#meetingFound input:button").val("");
		$("span").not("#attendee").html("");
		$("#attendee").html( localStorage.getItem("userName"));
		$("#order").val("提     交");
//		$("#meetingFound #no").attr("checked","true");
		attendeeUserId=[ localStorage.getItem("userId")];
		hostUserId=[];
		VIPUserId=[];
		/**
		 * 
		 */
		roomId=[];
	});
	
	$("#roomtable").mouseover(function(){_flag=true});
	$("#roomtable").mouseout(function(){_flag=false});
	$("body").click(function(){
		if(!_flag){
	       $("#roomtable").hide();
	       /**
	        * 
	        */
	       roomId=currentRoomId;
	    }
	});
	meetingCalendar();
	
	//监控meetingList是否增加
	setInterval(selectMeetingAction,30000);
	
	
	//会议时间监控
//	setInterval(controlAction,1000);
	
	
});


/**
 * 这个全局变量只用于保存所有房间，
 * 当执行meetingCalender的ajax时被赋值
 */
var rooms=[];
function meetingCalendar(){

	var userId= localStorage.getItem("userId");
	var date = new Date();
	var month = DateDigit ((date.getMonth()+1));
	var day = DateDigit (date.getDate());
	
	var currentDate = date.getFullYear()+"-"+month+"-"+day; 
	$("#J-xl").val(currentDate);
	var companyId =  localStorage.getItem("companyId");
	$("#calendar").empty();
	
	$.ajax({
		url:"http://120.55.94.85:8088/TTCM/menus/findRoom.do",
		type:"POST",
		data:{
			companyId:companyId
		},
		dataType:"JSON",
		success:function(result){
			console.log(result);
			if(result.state==SUCCESS){
				rooms = result.data;
				selectMeetingAction();
				setGray();
				var tr= '<tr id="room">'+
						'	<th style="width: 90px;height: 30px;">房间/时间</th>'+
						'</tr>';
				$("#calendar").append(tr);
				//遍历时间，打印第一行
				for(var i = 0; i<24;i++){
					var td= '	<td style="width: 20px;">'+i+'</td>';
					$("#room").append(td);
				}
				//遍历房间，打印时间行
				for(var i = 0; i<result.data.length;i++){
					var tr= '	<tr id="rooms'+i+'">'+
							'	<td>'+result.data[i].roomName+'</td>';
					
					for(var j = 0 ; j<24;j++){
						tr=tr+'	<td id="time'+j+'"></td>';
					}
					tr=tr+'</tr>';
					$("#calendar").append(tr); 
				}
				
//废弃
//				//遍历24小时，打印时间行
//				for(var i = 0; i<24;i++){
////					var j = i+1;
////					if(i==23){
////						j=0;
////					}
//					tr =' <tr id="time'+i+'">'+
//							'	<td>'+i+'</td>';
//					for(var k = 0 ; k<result.data.length;k++){
//						tr=tr+'	<td id="'+result.data[k].roomId+'"></td>';
//					}
//					tr=tr+'</tr>';
//					$("#calendar").append(tr); 
//				}
				/**
				 * 这里要延长，因为可能用户会议室太多
				 */
				var height = $("#calendar").height();
				var contentH = $("#content").height();
//				console.log(height);
//				console.log(contentH);
				if(height>contentH){
					$("#content").css("height",""+(height+100)+"px")
				}
				
				
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
	
	
	
	
}

//补齐数位
function DateDigit (num){
    return num < 10 ? '0' + (num|0) : num;
};

//有会议就变灰
function setGray(){
	var date = $("#J-xl").val();
	
	var userId= localStorage.getItem("userId");
//	console.log(date);
	/*	setDate() 	设置 Date 对象中月的某一天 (1 ~ 31)。
		setMonth() 	设置 Date 对象中月份 (0 ~ 11)。
		setFullYear()
		setHours() 	设置 Date 对象中的小时 (0 ~ 23)。
		setMinutes() 	设置 Date 对象中的分钟 (0 ~ 59)。
		setSeconds() 	设置 Date 对象中的秒钟 (0 ~ 59)。
		setMilliseconds()
	 	*/
	var dateTime = new Date();
	dateTime.setFullYear(date.slice(0,4));
	dateTime.setMonth(parseInt(date.slice(5,7))-1);
	dateTime.setDate(date.slice(8));
	dateTime.setHours(0);
	dateTime.setMinutes(0);
	dateTime.setSeconds(0);
	dateTime.setMilliseconds(0);
	
	//由于优先级问题只能一个一个改
	for(var i = 0 ; i<rooms.length;i++){
		for(var j = 0;j<24;j++){
			
			$("#rooms"+i+" #time"+j).css("background-color","#FFF");
		}
	}
	
//	console.log(dateTime.getTime());
	
	
	$.ajax({
		
//		async: false, 
		url:"http://120.55.94.85:8088/TTCM/menus/MeetingSchedule.do",
		type:"POST",
		data:{
			userId:userId,
			date:dateTime.getTime()
		},
		dataType:"JSON",
		success:function(result){
			
			if(result.state==SUCCESS){
				var mList=result.data
				console.log(mList);
				for(var i = 0 ; i<mList.length;i++){
					var startTime = new Date(mList[i].startTime);
					var delayTime = new Date(mList[i].delayTime);
					
					var startDate = startTime.getFullYear()+"-"+DateDigit ((startTime.getMonth()+1))+"-"+DateDigit (startTime.getDate());
					var delayDate = delayTime.getFullYear()+"-"+DateDigit ((delayTime.getMonth()+1))+"-"+DateDigit (delayTime.getDate());
//					console.log(date == startDate);
					
					var index ;
					for(var k = 0;k<rooms.length;k++){
						if(rooms[k].roomId==mList[i].roomId){
							$(" #rooms"+k+" #time"+startTime.getHours()).css("background-color","#D8D8D8");
							console.log(k);
							index = k;
							break;
						}
					}
					
					if(date == startDate){
						
						console.log(date == delayDate);
						//判断结束时间是否为当天
						if(startDate == delayDate){
							for(var j = startTime.getHours(); j<=delayTime.getHours();j++){
								$(" #rooms"+index+" #time"+j).css("background-color","#D8D8D8");
							}
							
						}else{
							for(var j = startTime.getHours(); j<24;j++){
								console.log(delayTime.getHours());
								$(" #rooms"+index+" #time"+j).css("background-color","#D8D8D8");
							}
						}
						
					}
					
					//判断会议时间是否超过一天
//					console.log(startTime<dateTime);
					if(startTime<dateTime && dateTime<delayTime){
						
						if(date == delayDate && date != startDate){
							
							for(var j = delayTime.getHours() ; j>=0 ; j--){
								$(" #rooms"+index+" #time"+j).css("background-color","#D8D8D8");
							}
						}else if(date != startDate && date != delayDate){
							
							for(var j = 0 ; j<24 ; j++){
								$(" #rooms"+index+" #time"+j).css("background-color","#D8D8D8");
							}
						}
						
					}
//					console.log(meetingList[i].subject+"----"+startTime.getHours());
//					console.log(.toLocaleDateString());
				}
				
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
			
		}
		
	});
	
	
}

function selectMeetingAction(){
	var userId= localStorage.getItem("userId");
	$.ajax({
		url:"http://120.55.94.85:8088/TTCM/menus/findMeeting.do",
		type:"POST",
		data:{
			userId:userId
		},
		dataType:"JSON",
		success:function(result){
			if(result.state==SUCCESS){
				meetingList = result.data[0];
//				console.log(meetingList);
//				console.log(result.data[1]);
//				console.log(result.data[1].length);
				if(result.data[1].length==0){
					paintMeeting2();
					return false;
				}
//				if(result.data[1].isEmpty())
				paintMeeting(result.data[1]);
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
}

function paintMeeting2(){
	var tr = "<tr><td>您目前没有会议</td></tr>";
	var table = $("#meeting").empty();
	var table2 = $("#meeting2").empty();
	table.append(tr);		
	table2.append(tr);
	
}

function paintMeeting(meetingIdList){

	
//	console.log(meetingIdList.join(","));
//	console.log(JSON.stringify(meetingIdList));
	var list  = [];
	if(meetingIdList!=null){
		$.ajax({
			async: false, 
			url:"http://120.55.94.85:8088/TTCM/menus/findAttendee.do",
			type:"POST",
			data:{
				meetingIdList:meetingIdList.join(",")
			},
			dataType:"JSON",
			success:function(result){
				console.log(result);
				if(result.state==SUCCESS){
					list=result.data;
//					console.log(result.data.1);
				}else{
					console.log(result.message);
					zeroModal.error(result.message);
				}
			}
		});
	}
	
	
	
	var table = $("#meeting").empty();
	var table2 = $("#meeting2").empty();
	var tr= '<tr class="title">'+
				'<td style="width: 150px;" align="center" valign="middle">主题</td>'+
				'<td align="center" valign="middle">主持人</td>'+
				'<td style="width: 150px;" align="center" valign="middle">房间名称</td>'+
				'<td style="width: 150px;" align="center" valign="middle">参会人</td>'+
				'<td align="center" valign="middle">开始时间</td>'+
				'<td align="center" valign="middle">结束时间</td>'+
				'<td style="width: 50px;" align="center" valign="middle">操作</td>'+
			'</tr>';
	table.append(tr);		
	table2.append(tr);
	var flag = 0;
	var flag2 = 0;
	for(var i=0;i<meetingList.length;i++){
		var meeting = meetingList[i];
//		console.log(meeting);
		var data;
		var index;
		for(var j=0;j<rooms.length;j++){
			if(meeting.roomId==rooms[j].roomId){
				index=j;
				break;
			}
		}
		var currentList = list[i];
		var startDate = new Date(meeting.startTime);
		var endDate = new Date(meeting.endTime);
		var sDate = startDate.getFullYear()+"-"+DateDigit((startDate.getMonth()+1))+"-"+DateDigit(startDate.getDate());
		var sTime = DateDigit(startDate.getHours())+":"+DateDigit(startDate.getMinutes());
		var eDate = endDate.getFullYear()+"-"+DateDigit((endDate.getMonth()+1))+"-"+DateDigit(endDate.getDate());
		var eTime = DateDigit(endDate.getHours())+":"+DateDigit(endDate.getMinutes());
		
		var userId =  localStorage.getItem("userId");
		if(userId == meeting.userId){
			tr= '<tr class="meetingContent">'+
			'	<td align="center" valign="middle" id="subject'+i+'">'+meeting.subject+'</td>'+
			'	<td align="center" valign="middle" id="host'+i+'">'+currentList[1]+'</td>'+
			'	<td align="center" valign="middle" id="roomName'+i+'">'+rooms[index].roomName+'</td>'+
			'	<td align="center" valign="middle" vip = "'+currentList[2]+'" id="attendee'+i+'">'+currentList[0]+'</td>'+
			'	<td align="center" valign="middle" id="startTime'+i+'">'+sDate+'<br>'+sTime+'</td>'+
			'	<td align="center" valign="middle" id="endTime'+i+'">'+eDate+'<br>'+eTime+'</td>'+
			'	<td align="center" valign="middle">'+
			'		<input type="button" value="修 改" style="margin-top: 6px;" id="showModifyMsg'+i+'" onclick="showModifyMsg(this.id)"><br>'+
			'		<input type="button" value="取 消" style="margin-bottom: 6px;" id="deleteMeeting'+i+'" onclick="deleteMeeting(this.id)">'+
			'	</td>'+
			'</tr>'
			table.append(tr);
			flag++;
		}else{
			tr= '<tr class="meetingContent">'+
			'	<td align="center" valign="middle" id="subject'+i+'">'+meeting.subject+'</td>'+
			'	<td align="center" valign="middle" id="host'+i+'">'+currentList[1]+'</td>'+
			'	<td align="center" valign="middle" id="roomName'+i+'">'+rooms[index].roomName+'</td>'+
			'	<td align="center" valign="middle" id="attendee'+i+'">'+currentList[0]+'</td>'+
			'	<td align="center" valign="middle" id="startTime'+i+'">'+sDate+'<br>'+sTime+'</td>'+
			'	<td align="center" valign="middle" id="endTime'+i+'">'+eDate+'<br>'+eTime+'</td>'+
			'	<td align="center" valign="middle">'+
			'		<input type="button" value="拒 绝" style="margin-top: 6px;" id="rejectMeeting'+i+'" onclick="rejectMeeting(this.id)"><br>'+
			'	</td>'+
			'</tr>'
			table2.append(tr);
			flag2++;
		}
	}
	
	if(flag==0){
		tr = "<tr><td>您目前没有会议</td></tr>";
		table = $("#meeting").empty();
		table.append(tr);		
		
	}
	if(flag2==0){
		tr = "<tr><td>您目前没有会议</td></tr>";
		table2 = $("#meeting2").empty();
		table2.append(tr);
	}
	
}


function paintUser(id){
	
	$("#hint").empty();
	var userId =  localStorage.getItem("userId");
	var text;
	if(id=="addAttendee"){
		 text = $("#attendee").html();
		 if(text2.capital().length==""||text2.capital().length==null){
			 $("#hint").html("请输入用户信息");
			 return;
		 }
		 for(var i = 0 ;i<attendeeUserId.length;i++){
			 if(attendeeUserId[i]==text2.id()){
				 $("#hint").html("已添加到参会列表");
				 return ;
			 }
		 }
		
		if(text == null || text == ""){
			$("#attendee").append(text2.capital());
		}else{
			$("#attendee").append(","+text2.capital());
		}
		attendeeUserId.push(text2.id());
//		attendeeUserId["attendeeUserId["+e+"]"]=text2.id();
	}else if(id=="setHost"){
		 text = $("#host").html();
		 if(text2.capital().length==""||text2.capital().length==null){
			 $("#hint").html("请输入用户信息");
			 return;
		 }
		 for(var i = 0 ;i<hostUserId.length;i++){
			 if(hostUserId[i]==text2.id()){
				 $("#hint").html("已添加到主持人列表");
				 return ;
			 }
		 }
		 for(var i = 0 ;i<attendeeUserId.length;i++){
			 if(attendeeUserId[i]==text2.id()){
				 break;
			 }else if(i==attendeeUserId.length-1){
				 $("#hint").html("请先添加到参会人列表");
				 return;
			 }
		 }
		if(text == null || text == ""){
			$("#host").append(text2.capital());
			
		}else{
			$("#host").append(","+text2.capital());
		}
		hostUserId.push(text2.id());
//		hostUserId["hostUserId["+j+"]"]=text2.id();
	}else if(id=="setVIP"){
		 text = $("#vip").html();
		 if(text2.capital().length==""||text2.capital().length==null){
			 $("#hint").html("请输入用户信息");
			 return;
		 }
		 for(var i = 0 ;i<VIPUserId.length;i++){
			 if(VIPUserId[i]==text2.id()){
				 $("#hint").html("已添加到重要成员列表");
				 return ;
			 }
		 }
		 for(var i = 0 ;i<attendeeUserId.length;i++){
			 if(attendeeUserId[i]==text2.id()){
				 break;
			 }else if(i==attendeeUserId.length-1){
				 $("#hint").html("请先添加到参会人列表");
				 return;
			 }
		 }
		 
		 
		if(text == null || text == ""){
			$("#vip").append(text2.capital());
		}else{
			$("#vip").append(","+text2.capital());
		}
		VIPUserId.push(text2.id());
//		VIPUserId["VIPUserId["+k+"]"]=text2.id();
	}else{
			var attendees = $("#attendee").text().split(",")
			var hosts = $("#host").text().split(",")
			var VIPs = $("#vip").text().split(",")
			console.log(attendees);
			 for(var i = 0 ;i<attendeeUserId.length;i++){
				 if(attendeeUserId[i]==text2.id()){
					 attendeeUserId.splice(i,1);
					 for(var j = 0;j<attendees.length;j++){
						 if(text2.capital()==attendees[j]){
							 attendees.splice(j,1);
						 }
					 }
					 $("#attendee").text(attendees.join(","));
				 }
				 if(i<VIPUserId.length){
					 if(VIPUserId[i]==text2.id()){
						 VIPUserId.splice(i,1);
						 for(var j = 0;j<VIPs.length;j++){
							 if(text2.capital()==VIPs[j]){
								 VIPs.splice(j,1);
							 }
						 }
						 
						 $("#vip").text(VIPs.join(","));
					 }
				 }
				 if(i<hostUserId.length){
					 if(hostUserId[i]==text2.id()){
						 hostUserId.splice(i,1);
//						 hosts.splice(i,1);
						 for(var j = 0;j<hosts.length;j++){
							 if(text2.capital()==hosts[j]){
								 hosts.splice(j,1);
							 }
						 }
						 $("#host").text(hosts.join(","));
					 } 
				 }
			 }
		 
		 
	}
	
}

function chooseRoomAction(){
	$("#startDateHint").empty();
	$("#endDateHint").empty();
	$("#timeHint").empty();
	
	var startDate = $("#startDate").val();
	var startTime = $("#startTime").val();
	var endDate = $("#endDate").val();
	var endTime = $("#endTime").val();
	var delayTime = $("#delay").val();
	var index = delayTime.lastIndexOf("分钟");
	if(index ==-1){
		return ;
	}
	delayTime=delayTime.substring(0,index);
	
	//判断日期是否为空
	if(startDate==null||startDate==""){
		$("#startDateHint").html("请输入会议开始日期")
		return;
	}
	if(endDate==null||endDate==""){
		$("#endDateHint").html("请输入会议结束日期")
		return;
	}
	
	if(startDate==endDate){
		//判断会议开始时间晚于或相同结束时间
		if(startTime==endTime){
			$("#timeHint").html("会议开始时间和结束时间不能相同")
			return;
		}

		var hour = parseInt(startTime.substring(0,2));
		var min = parseInt(startTime.substring(3));
		var hour2 = parseInt(endTime.substring(0,2));
		var min2 = parseInt(endTime.substring(3));
		
		if(hour > hour2){
			$("#timeHint").html("会议结束时间不能早于会议开始时间");
			return;
		}else if(hour == hour2){
			if(min > min2){
				$("#timeHint").html("会议结束时间不能早于会议开始时间");
				return;
			}
		}
		
	}else{
		//判断开始日期是否比结束日期晚
		var nubStartDate = 
			parseInt(
					(startDate.substring(0,4)+
					startDate.substring(5,7)+
					startDate.substring(8)));
		var nubEndDate = 
			parseInt(
					(endDate.substring(0,4)+
					endDate.substring(5,7)+
					endDate.substring(8)));
		
		if(nubStartDate > nubEndDate){
			$("#endDateHint").html("会议结束日期不能早于会议开始日期");
			return;
		}
	
	}
	
	/*
	 * 当时间符合条件，用户点击选择房间，查询出当前时间段空闲的房间，并遍历显示
	 */
	findRoomInTime(startDate,startTime,endDate,endTime,delayTime);
	
	
}


var currentRoomId;

function findRoomInTime(startDate,startTime,endDate,endTime,delayTime){
	var companyId= localStorage.getItem("companyId");
	$("#roomHint").empty();
	/**
	 * 
	 */
	
	startDate = startDate+"  "+startTime;
	endDate = endDate+"  "+endTime;
//	console.log(endDate);
//	console.log(startDate)
	$.ajax({
		url:"http://120.55.94.85:8088/TTCM/menus/findInTime.do",
		type:"POST",
		data:{
			companyId:companyId,
			startDate:startDate,
			endDate:endDate,
			delayTime:delayTime
			},
		dataType:"JSON",
		success:function(result){
//			console.log(result);
			if(result.state==SUCCESS){
				var roomList = result.data;
				$(".roomrow").remove();
				$("#roomtable").show();
				if(result.data.length == 0){
					$("#roomHint").html("该时间段没有房间空闲，请修改会议时间");
					return ;
				}
				console.log(roomList);
				roomId=[];
				for(var i =0;i<roomList.length;i++){
					var room = roomList[i];
					/**
					 * 这里是roomId的唯一一次赋值位置；
					 */
					roomId.push(room.roomId);
//					console.log(room.roomId);
					var tr = '<tr id="roomrow'+i+'" class="roomrow" onclick="selectedRoomAction(this.id);">'+
								'<td>'+room.roomName+'</td>'+
								'<td>'+room.roomPhone+'</td>';
					$("#roomtable").append(tr);
					
				}
				
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
}



function selectedRoomAction(id){
	$("#roomtable").hide();
	var roomName = $("#"+id+" td").html();
	var roomPhone = $("#"+id+" td").next().html();
	/**
	 * 
	 */
//	console.log(id);
//	console.log(roomId[2]);
	var index = parseInt(id.slice(7)) ;
	if(roomId.length>0){
		currentRoomId = roomId[index];
		roomId = roomId[index];
//		console.log(index);
//		console.log(currentRoomId);
	}
	
//	console.log(roomId);
	$("#roomMessage").html("<b>房间名:</b>"+roomName+"  <b>电话:</b>"+roomPhone);
}

function isUnlimitedAction(){
	var a = $('#unlimited');
	if(a[0].checked){
		$("#limit").attr("disabled",true); 
	}else{
		$("#limit").attr("disabled",false); 
	}
}

function orderMeetingAction(){
	//这个是必须获取的公共参数
	var startDate = $("#startDate").val()+"  "+$("#startTime").val();
	var endDate = $("#endDate").val()+"  "+$("#endTime").val();
	var GMT = $("#GMT").val();
	var repeat = $("input[name='repeat']:checked").val();
	
	var subject = $("#subject").val();
	var description = $("#description").val();
	var delayTime = $("#delay").val();
	var index = delayTime.lastIndexOf("分钟");
	var attendee = $("#attendee").val();
	var host = $("#host").val();
	
	
	if(index ==-1){
		return ;
	}
	delayTime=delayTime.substring(0,index);
	var limit = 0;
	if(! $('#unlimited')[0].checked){
		limit = $("#limit").val();
	}
	var meetingFile = $("#meetingFile").val();
	//如果用户点击删除按钮时，提交按钮为修改则当前为修改方法attendeeUserId为空
	//这里判断是创建还是修改
	if($("#order").val()=="提     交"){
		//保存会议Action
		/**
		 * 
		 */
		if(roomId==null||roomId.length==0){
			$("#roomHint").html("请选择房间");
			return ;
		}
		if(currentRoomId==null){
			$("#roomHint").html("请选择房间");
			return ;
		}
		console.log("lastCheck1");
//		console.log(roomId);
		var roomList = lastCheck(startDate,endDate,delayTime);
		
	//	console.log(delayTime);
		
	//	console.log("  |attendeeUserId:"+attendeeUserId+"  |hostUserId:"+hostUserId+
	//			"  |VIPUserId:"+VIPUserId+"  |startDate:"+startDate+"  |endDate:"+endDate+
	//			"  |GMT:"+GMT+"  |repeat:"+repeat+"  |subject:"+subject+
	//			"  |description:"+description+"  |delayTime:"+delayTime+
	//			"  |meetingFile:"+meetingFile+"  |limit:"+limit+"  |roomList:"+roomList[0].roomId);
	//	console.log(roomList[0].roomId);
		if(roomList==null || roomList.length==0){
			$("#roomHint").html("该时间段已有会议预约，请重新选择房间或时间！");
			return ;
		}
		
		
		$.ajax({
	//		traditional: true, 
			url:"http://120.55.94.85:8088/TTCM/menus/addMeeting.do",
			type:"POST",
	//		contentType:"application/json",  
	//		async: false, 
			data:{
				attendeeUserId:JSON.stringify(attendeeUserId),
				hostUserId:JSON.stringify(hostUserId),
				VIPUserId:JSON.stringify(VIPUserId),
				startDate:startDate,
				endDate:endDate,
				GMT:GMT,
				repeat:repeat,
				subject:subject,
				description:description,
				delayTime:delayTime,
				meetingFile:meetingFile,
				limit:limit,
				roomId:roomList[0].roomId
			},
			dataType:"JSON",
			success:function(result){
				if(result.state==SUCCESS){
					console.log(result.data)
					console.log("成功");
					$("#meetingFound").hide();
					$("#meetingMessage").show();
					selectMeetingAction();
					
				}else{
					$("#roomHint").html(result.message);
					console.log(result.message);
				}
			}
		});
	}else{
	//修改会议action
		
		
		
		
		//最后查询出所以符合时间条件的room集合
		var roomList=lastCheck(startDate,endDate,delayTime,meetingList[inx].meetingId);
		//如果没有该房间，就检查当前时间是否不和条件，如果该了房间就重新检查。
//		console.log(meetingList[inx]);
//		console.log(roomId);
		console.log(roomList)
		if(roomList==null){
			return;
		}
//		console.log(meetingList[inx].roomId==roomId);
		/**
		 * 这里是修改房间信息最后检查这个房间是否和其他会议有时间冲突，防止并发
		 */
		var flag = null;
		for(var i = 0 ; i<roomList.length;i++){
			
//			console.log(roomList[i].roomId==currentRoomId)
			if(roomList[i].roomId==currentRoomId){
				flag=1;
				break;
			}
		}
		console.log(flag==null);
		if(flag==null){
			$("#roomHint").html("该时间段已有会议预约，请重新选择房间或时间！");
			return ;
		}
		
		
//		console.log("  |attendeeUserId:"+attendeeUserId+"  |hostUserId:"+hostUserId+
//							"  |VIPUserId:"+VIPUserId+"  |startDate:"+startDate+"  |endDate:"+endDate+
//							"  |GMT:"+GMT+"  |repeat:"+repeat+"  |subject:"+subject+
//							"  |description:"+description+"  |delayTime:"+delayTime+
//							"  |meetingFile:"+meetingFile+"  |limit:"+limit+"  |roomList:"+roomList[0].roomId);
//					console.log(roomList[0].roomId);
		
		
		$.ajax({
//			traditional: true, 
			url:"http://120.55.94.85:8088/TTCM/menus/updateMeeting.do",
			type:"POST",
	//		contentType:"application/json",  
	//		async: false, 
			data:{
				attendeeUserId:JSON.stringify(attendeeUserId),
				hostUserId:JSON.stringify(hostUserId),
				VIPUserId:JSON.stringify(VIPUserId),
				startDate:startDate,
				endDate:endDate,
				GMT:GMT,
				repeat:repeat,
				subject:subject,
				description:description,
				delayTime:delayTime,
				meetingFile:meetingFile,
				limit:limit,
				roomId:roomList[0].roomId,
				meetingId:meetingList[inx].meetingId
			},
			dataType:"JSON",
			success:function(result){
				if(result.state==SUCCESS){
					console.log(result.data)
					console.log("成功");
					$("#meetingFound").hide();
					$("#meetingMessage").show();
					selectMeetingAction();
				}else{
					$("#roomHint").html(result.message);
					console.log(result.message);
				}
			}
		});
		
		
		
		
		
	}
}
function lastCheck(startDate,endDate,delayTime,meetingId){
	var roomList = null;
//	console.log(delayTime);
	console.log(currentRoomId);
//	console.log(JSON.stringify(currentRoomId));
	$.ajax({
		async: false, 
		url:"http://120.55.94.85:8088/TTCM/menus/checkMeeting.do",
		type:"POST",
		data:{
			roomId:currentRoomId,
			startDate:startDate,
			endDate:endDate,
			delayTime:delayTime,
			meetingId:meetingId
		},
		dataType:"JSON",
		success:function(result){
			if(result.state==SUCCESS){
				if(result.data.length==0){
					$("#roomHint").html("您选的房间被占用，请重选！");
					return ;
				}
				roomList = result.data;
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});
	return roomList;
}

//function updateMeetingCheck(startDate,endDate,delayTime){
//	
//	
//	
//	console.log(meetingList[inx]);
//	var meetingId = meetingList[inx].meetingId;
//	var sDate = meetingList[inx].startTime;
//	var eDate = meetingList[inx].endTime;
//	var dDate = meetingList[inx].delayTime;
//	
//	
//}














