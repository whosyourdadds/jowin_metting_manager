// scripts/edit.js
/*
 * 笔记编辑界面的 js 脚本 
 */

//模型对象
var model = {
	notebooks:[], 		//笔记本列表
	currentNotebook:{}, //当前选定的笔记本
	notebookIndex:0,	//当前选择的笔记本索引号
	notes:[], 			//当前笔记本的笔记列表
	currentNote:{}, 	//当前正在编辑的笔记
	noteIndex:0			//当前选择的笔记索引号
};

$(function(){
	//页面加载以后，可以获取登录用户的id
	var userId = getCookie("userId");
	//console.log(userId);
	listNotebooksAction(userId);
	//绑定点击保存笔记按钮时候的事件
	$('#save_note').click(saveNoteAction);
	//绑定添加事件
	$('#add_note').click(addNoteAction);

});

function addNoteAction(){
	//打开添加对话框
	var url=baseUrl+'/alert/alert_note.jsp?t='+(new Date()).getTime();
	$('#can').load(url,
		function(response,status,xhr){
		if(status=="success"){
			//绑定按钮事件
			$('#can .cancel, #can .close').click(function(){
				//console.log("click");
				$('#can > div').hide(200, function(){
					$('#can').empty();
				});
			});
			$('#can .sure').click(saveNewNoteAction);
			//$('#can').show(500);
			$('#can > div').show(200);
		}else{
			$('#can').empty();
		}
	});
}

function saveNewNoteAction(){
	console.log("saveNewNoteAction");
	//ajax 
	//请求成功以后，返回Note对象
	// 将note对象插入到 notes 的第一个
	// 更改当前笔记对象
	// 刷新界面：paintNotes() 
	//          paintCurrentNote()
	
	var title = $('#input_note').val();
	var notebookId = model.currentNotebook.id;
	var userId = getCookie('userId');
	
	var data = {title:title,
			notebookId:notebookId,
			userId:userId};
	var url = baseUrl+"/notebook/addNote.do";
	$.post(url, data, function(result){
		if(result.state==SUCCESS){
			var note = result.data;
			model.currentNote=note;
			model.noteIndex = 0;
			var ary = [{id:note.id,
				title:note.title}];
			model.notes=ary.concat(model.notes);
			paintNotes();
			paintCurrentNote();
			$('#can > div').hide(200, function(){
				$('#can').empty();
			});			
		}else{
			alert(result.message);
		}
	});
}

function saveNoteAction(){
	//获取笔记编辑区的内容
	var body = um.getContent();
	var title = $('#input_note_title').val();
	if(body==model.currentNote.body &&
		title==model.currentNote.title){
		//如果笔记内容没有被编辑，就不向
		//服务器发送ajax请求
		return;
	}
	
	
	var data={'noteId':model.currentNote.id,
			'title':title,
			'body':body};
	var url=baseUrl+"/notebook/updateNote.do";
	//将笔记信息发送到服务器
	//ajax ...
	$('#save_note').html('保存中...')
		.attr('disabled','disabled');
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			//console.log(result.message);
			$('#save_note').html('保存笔记')
			.removeAttr('disabled');
			var note = result.data;
			model.currentNote = note;
			//listCurrentNotesAction();
			model.notes[model.noteIndex].title=note.title;
			paintNotes();
			paintCurrentNote();
		}else{
			alert(result.message);
		}
	});
	
}

function listNotebooksAction(userId){
	var url = baseUrl+"/notebook/list.do?userId="+userId;
	$.getJSON(url, function(result){
		if(result.state==SUCCESS){
			var list = result.data;
			//for(var i=0; i<list.length; i++){
				//console.log(list[i]);
			//}
			//更新数据模型
			model.notebooks=list;
			//刷新笔记本列表的显示
			paintNotebooks();
		}else{
			alert(result.message); 
		}
	});
//	$.ajax({
//		url:baseUrl+"/notebook/list.do?userId="+userId,
//		type:"get",
//		dataType:"json",
//		success:function(result){
//			if(result.state==SUCCESS){
//				var list=result.data;
//				for(var i=0; i<list.length; i++){
//					console.log(list[i]);
//				}
//			}else{
//				alert(result.message);
//			}
//		}
//	});
}

/**
 * 将数据模型中的数据更新显示到界面上
 * <li class="online">
   <a  class='checked'>
   <i class="fa fa-book" title="online" rel="tooltip-bottom"></i> 
   默认笔记本</a></li>
 */
function paintNotebooks(){
	var list = model.notebooks;
	var ul=$('#notebooks');
	ul.empty();
	for(var i=0;i<list.length;i++){
		var notebook = list[i];
		var li =
			'<li class="online">'+
			  '<a>'+
			    '<i class="fa fa-book" title="online" rel="tooltip-bottom"></i>'+
			     notebook.name+
			  '</a>'+
			'</li>';
		//绑定 序号
		li = $(li).data("index", i);
		//监听单击事件
		li.click(function(){
			//处理选定效果
			$(this).parent().find('a')
				.removeClass('checked');
			$(this).children("a").addClass('checked');
			//获取绑定的 序号index
			var index=$(this).data("index");
			//console.log(index);
			//找到选定的笔记本对象
			var notebook=model.notebooks[index];
			//将数据更新到模型
			model.currentNotebook=notebook;
			model.notebookIndex = index;
			//console.log(notebook);
			listCurrentNotesAction();
		});
		ul.append(li);
	}
}

//控制器方法：获取数据，更新模型
function listCurrentNotesAction(){
	var nid=model.currentNotebook.id;
	//发起异步请求获取notes 
	//异步请成功以后，更新model.notes
	// 调用 paintNotes()
	//console.log(nid);
	var url=baseUrl+"/notebook/notes.do?notebookId="+nid;
	$.getJSON(url, function(result){
		if(result.state==SUCCESS){
			var notes = result.data;
			//console.log(result);
			//将服务器上获取的notes赋值到模型
			//更新模型中的笔记列表
			model.notes=notes;
			//绘制笔记列表
			paintNotes();
		}
	});
}

//将数据模型中的notes 显示到界面
/*
 * 	<li class="online">
		<a  class='checked'>
			<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> 
			使用Java操作符
			<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>
		</a>
		<div class="note_menu" tabindex='-1'>
			<dl>
				<dt><button type="button" class="btn btn-default btn-xs btn_move" title='移动至...'><i class="fa fa-random"></i></button></dt>
				<dt><button type="button" class="btn btn-default btn-xs btn_share" title='分享'><i class="fa fa-sitemap"></i></button></dt>
				<dt><button type="button" class="btn btn-default btn-xs btn_delete" title='删除'><i class="fa fa-times"></i></button></dt>
			</dl>
		</div>
	</li>
 * 
 */
function paintNotes(){
	var notes=model.notes;
	var ul=$('#notes').empty();
	for(var i=0; i<notes.length; i++){
		var note=notes[i];
		var li = 
		  '<li class="online">'+
			'<a >'+
			  '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+
			  note.title+
			  '<button disabled="disabled" type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>'+
			'</a>'+
			'<div class="note_menu" tabindex="-1">'+
			'<dl>'+
				'<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>'+
				'<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'+
				'<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'+
			'</dl>'+
			'</div>'+
		  '</li>';
		li = $(li).data('index', i);
		ul.append(li);
		
		li.children('a').click(function(){
			$(this).parent().parent()
				.find('a').removeClass('checked');
			$(this).parent().parent()
				 .find('li>a>button')
				 .attr('disabled','disabled');
			$(this).find('button').removeAttr("disabled");
			$(this).parent().parent()
				.find('.note_menu').slideUp(10);
			$(this).addClass('checked');
			var index = $(this).parent().data('index');
			//console.log(index);
			var note = model.notes[index];
			model.noteIndex = index;
			//console.log(note);
			//从服务器端获取笔记详细信息
			loadNoteAction(note.id);
		});
		
		//绑定笔记菜单事件
		li.children('a').children('button')
		.click(function(){
			console.log("HI");
			$(this).parent().parent()
			.children('.note_menu')
			.slideToggle(200);
			return false;
		});
		
		li.find('.btn_delete')
		.click(deleteNoteAction);

		li.find('.btn_move')
		.click(moveNoteAction);
		
		if(model.currentNote.id){
			if(note.id==model.currentNote.id){
				li.children('a').addClass('checked');
			}
		}
		
	}
}

function moveNoteAction(){
	//显示移动到窗口
	var url = baseUrl+'/alert/alert_move.html?'+Math.random();
	$('#can').load(url,function(){
		var note = model.currentNote;
		$('#can h4').html('移动笔记：'+note.title);
		//在下拉列表中显示全部的笔记本
		var notebooks = model.notebooks;
		var list=$('#can #moveSelect').empty();
		for(var i=0;i<notebooks.length;i++){
			var notebook = notebooks[i];
			var opt=$('<option></option>').val(notebook.id).text(notebook.name);
			if(note.notebookId == notebook.id){
				opt.attr('selected','selected');
			}
			list.append(opt);
		}
		//绑定点击确定按钮的事件
		$('#can .sure').click(moveNoteAjaxAction);
		//绑定点击取消按钮的事件
		$('#can .close, #can .cancel').click(function(){
			$('#can>div').hide(function(){
				$('#can').empty();
			});
		});
		//显示对话框
		$('#can>div').show();
	});
	//阻止冒泡事件传播
	return false;
}

/*
 * 调用控制器完成 移动笔记功能
 */
function moveNoteAjaxAction(){
	var noteId = model.currentNote.id;
	var notebookId = $('#moveSelect').val();
	var data={noteId:noteId,notebookId:notebookId};
	console.log(data);
	//利用异步清除处理笔记本移动的操作
}

function deleteNoteAction(){
	var index = model.noteIndex;
	var note = model.currentNote;
	var url = baseUrl + "/notebook/deleteNote.do?noteId="+note.id;
	$.getJSON(url, function(result){
		if(result.state==SUCCESS){
			//更改笔记列表，将当前笔记删除
			var index = model.noteIndex;
			var note = model.currentNote;
			//下一个笔记的位置
			var next = index;//默认情况
			if(index+1==model.notes.length){
				next--;
			}
			if(model.notes.length==1){
				next=null;
			}
			var notes = model.notes;
			//for(var i=index; i<notes.length-1; i++){
			//	notes[i]=notes[i+1];
			//}
			//notes.pop();
			notes.splice(index,1);
			model.noteIndex = next;
			if(next==null){
				model.currentNote={};
				$('#input_note_title').val('');
				um.setContent('');
			}else{
				model.currentNote=notes[next];
				loadNoteAction(notes[next].id);
			}
			paintNotes();
			
		}else{
			alert(result.message);
		}
	});
	return false;
}

/*
 * 从服务器加载note
 */
function loadNoteAction(noteId){
	var url=baseUrl+"/notebook/load.do?noteId="+noteId;
	$('#input_note_title').val('笔记加载中...')
	$.getJSON(url, function(result){
		if(result.state==SUCCESS){
			model.currentNote=result.data;
			paintCurrentNote();
		}else{
			alert(result.message);
		}
	});
}

/*
 * 将当前笔记 显示到笔记编辑区
 */
function paintCurrentNote(){
	var note = model.currentNote;
	$('#input_note_title').val(note.title);
	//um 就是笔记本编辑区: ueditor 
	//setContent 可以改变笔记编辑区的内容
	um.setContent(note.body);
}












