var model = {
		users:{},
		user:{}
}


$(function(){
	selectUserRegistAction();
});

function selectUserRegistAction(){
	var userId =  localStorage.getItem("userId");
	var companyId= localStorage.getItem("companyId");
	$.ajax({
		url:"http://120.55.94.85:8088/TTCM/menus/userApp.do",
		type:"POST",
		data:{
			companyId:companyId
		},
		dataType:"JSON",
		success:function(result){
			if(result.state==SUCCESS){
				model.users=result.data;
				paintUser();
			}else{
				console.log(result.message);
				zeroModal.error(result.message);
			}
			
		}
	});
}

function paintUser (){
	var table = $("#sq").empty();
	var tr='<tr>'+
				'<td align="center" valign="middle"><b>姓&nbsp;名</b></td>'+
				'<td align="center" valign="middle"><b>手&nbsp;机</b></td>'+
				'<td align="center" valign="middle">&nbsp;&nbsp;&nbsp;</td>'+
			'</tr>';
	table.append(tr);
	for(var i=0;i<model.users.length;i++){
		var user = model.users[i];
		tr='<tr>'+
		'<td align="center" valign="middle" id="uName'+i+'">'+user.name+'</td>'+
		'<td align="center" valign="middle" id="uModile'+i+'">'+user.modile+'</td>'+
		'<td align="center" valign="middle">'+
		'<input id="agree'+i+'" type="button" value="同意"  onclick="AgreeOrRefuseAction(this.value,this.id)">&nbsp;'+
		'<input id="refuse'+i+'" type="button" value="拒绝"  onclick="AgreeOrRefuseAction(this.value,this.id)">'+
		'</td>'+
		'</tr>';
		table.append(tr);
	}
}
function AgreeOrRefuseAction(val,id){
	if(val=="同意"){
		var index=id.substring(5);
		var user = model.users[index];
		var userId=user.id;
		$.ajax({
			url:"http://120.55.94.85:8088/TTCM/menus/agree.do",
			type:"POST",
			data:{
				userId:userId
			},
			dataType:"JSON",
			success:function(result){
				if(result.state==SUCCESS){
					selectUserRegistAction();
				}else{
					console.log(result.message);
					zeroModal.error(result.message);
				}
					
			}
		});
	}else{
		var index = id.substring(6);
		var user = model.users[index];
		var userId=user.id;
		$.ajax({
			url:"http://120.55.94.85:8088/TTCM/menus/refuse.do",
			type:"POST",
			data:{
				userId:userId
			},
			dataType:"JSON",
			success:function(result){
				console.log(result.state);
				console.log(result.data);
				if(result.state==SUCCESS){
					selectUserRegistAction();
//					alert(result.data);
				}else{
					console.log(result.message);
					zeroModal.error(result.message);
				}
					
			}
		});
		
	}
	
}