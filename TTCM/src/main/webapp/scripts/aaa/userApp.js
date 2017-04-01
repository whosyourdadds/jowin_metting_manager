var model = {
		users:{},
		user:{}
}


$(function(){
	selectUserRegistAction();
});

function selectUserRegistAction(){
	var userId =  getCookie("userId");
	var companyId= getCookie("companyId");
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
				'<th align="center" valign="middle">姓&nbsp;名</th>'+
				'<th align="center" valign="middle">手&nbsp;机</th>'+
				'<th align="center" valign="middle">操&nbsp;作</th>'+
			'</tr>';
	table.append(tr);
	for(var i=0;i<model.users.length;i++){
		var user = model.users[i];
		tr='<tr>'+
		'<td align="center" valign="middle" id="uName'+i+'">'+user.name+'</td>'+
		'<td align="center" valign="middle" id="uModile'+i+'">'+user.modile+'</td>'+
		'<td align="center" valign="middle">'+
		'<input id="agree'+i+'" class="btn btn-info" type="button" value="同意"  onclick="AgreeOrRefuseAction(this.value,this.id)">&nbsp;'+
		'<input id="refuse'+i+'" class="btn " type="button" value="拒绝"  onclick="AgreeOrRefuseAction(this.value,this.id)">'+
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
		if(confirm("您确定要拒绝该用户么？")){
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
//						alert(result.data);
					}else{
						console.log(result.message);
						zeroModal.error(result.message);
					}
						
				}
			});
		}else{
			
		}
		
		
	}
	
}