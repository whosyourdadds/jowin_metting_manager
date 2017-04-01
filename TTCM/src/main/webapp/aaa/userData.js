var userList = null;
$(function() {

	$.ajax({
		url: "http://120.55.94.85:8088/TTCM/menus/userData.do",
		type: "POST",
		data: {

		},
		dataType: "JSON",
		success: function(result) {
			if(result.state == SUCCESS) {
				userList = result.data
				
			} else {
				console.log(result.message);
				zeroModal.error(result.message);
			}
		}
	});

});

var text2;
$(document).ready(function() {
	text2 = $("#Text2").tautocomplete({
		width: "400px",
		columns: ['手&nbsp;机', '公&nbsp;司', '姓&nbsp;名'],
		data: function() {
			
			//
//			var userAllData=[{}];
			try {
				/*
				 * 这里找不到修改的地方，所以是移植过来的参数
				 * country:手机号
				 * code：公司
				 * capital：姓名
				 */

				var data = [{ "id": 1, "country": "Afghanistan", "code": "测试", "capital": "Kabul" }, { "id": 2, "country": "考试", "code": "ALB", "capital": "Tirane" }, 
				{ "id": 3, "country": "Algeria", "code": "DZA", "capital": "Algiers" }, { "id": 4, "country": "Andorra", "code": "AND", "capital": "Andorra la Vella" }, 
				{ "id": 196, "country": "Zimbabwe", "code": "ZMB", "capital": "Harare" }];
				for(var i = 0; i < userList.length; i++) {
					var user = userList[i];
//					userAllData.push({ "id": user.id, "country": user.modile, "code": user.companyName, "capital": user.name });
					var userData = { "id": user.id, "country": user.modile, "code": user.companyName, "capital": user.name };
					//.substring(0, 6) + "*****"
					var length = data.push(userData);
				}
				
			} catch(e) {
				alert(e)
			}
			var filterData = [];

			var searchData = eval("/" + text2.searchdata() + "/gi");
//			console.log(searchData);
			$.each(data, function(i, v) {
				if(v.country.search(new RegExp(searchData)) != -1) {
					//v.country打印出的是187021*****，于是我将他变成了完整的手机号，现在要将不完整的手机号输入到
					//v.country不能直接赋值，因为直接赋值会影响后续的自动完成。所以需要找到替代的值
					var value = { "id": v.id, "country": v.country.substring(0, 6) + "*****", "code": v.code, "capital": v.capital };
//					console.log(v.country);
//					console.log(value.country);
					filterData.push(value);//这里要不完整手机号
				}
			});
			return filterData;
		},
		onchange: function() {
			//            $("#host").append(text2.capital());
			//            $("#attendee").append(text2.capital());
		}
	});
});
