<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文件上载</title>
<style type="text/css">

#selected img, #selected_photos img{
	display: block;
	width: 160px;
	padding: 5px;
	border: 1px solid #ddd;
	margin: 5px;
	float: left;
}

</style>
</head>
<body>
	<h1>文件上载</h1>
	
	<h2>表单上载</h2>
	<form enctype="multipart/form-data" 
		action="upload.do" method="post">
		姓名:<input type="text" name="username"><br> 
		照片:<input name="userfile1" type="file"><br>
	   <input type="submit" value="上载">
	</form>
	
	<h2>ajax 上载</h2>
	<div>
		<!-- 指定 multiple="multiple" 属性，可以选择
		多个文件 -->
		<label>选择图片</label>
		<input type="file" id="images"  
			multiple="multiple"><br>
		<input id="ajax_upload" type="button" 
			value="ajax上载">
	</div>
	<h3>选择了：</h3>
	<div id="selected">
		
	</div>
	
	<h2>JQuery 上载</h2>
	<div>
		<label>选择图片</label>
		<input type="file" id="photos"  
			multiple="multiple"><br>
		<input id="jquery_upload" type="button" 
			value="jquery上载">
	</div>
	<h3>选择了：</h3>
	<div id="selected_photos">
	</div>

</body>
<script type="text/javascript" 
	src="../js/jquery.min.js"></script>
<script type="text/javascript">
var photos=$("#photos");
var selectedPhotos=$("#selected_photos");
var ajaxBtn=$("#jquery_upload");
photos.change(function(){
	var files=this.files;
	selectedPhotos.empty();
	for(var i=0; i<files.length; i++){
		var f = files[i];
		var url = window.URL.createObjectURL(f);
		var img = $("<img src='"+url+"'>");
		selectedPhotos.append(img);
	}
});

ajaxBtn.click(function(){
	var url="uploadImages.do";
	var data=new FormData();
	
	var files = photos[0].files;
	for(var i=0; i<files.length; i++){
		var f = files[i];
		data.append("images", f, f.name);
	}
	ajaxBtn.val("上载中...");
	$.ajax({
		url:url,
		type:"POST",
		data:data,
		dataType:"json",
		processData:false, //不要处理 data数据！！！
		contentType:false, //不要有JQuery设定ContentType
		success:function(json){
			ajaxBtn.val("JQuery 上载");
			selectedPhotos.html(json.message);
		}
	});
	
});


</script>
	
<script type="text/javascript">
	var images = document.getElementById("images");
	var selected=document.getElementById("selected");
	images.onchange=function(){
		var files = this.files;
		selected.innerHTML="";
		for(var i=0; i<files.length; i++){
			var f = files[i];
			var url = window.URL.createObjectURL(f);
			var img = new Image();
			img.src = url;
			selected.appendChild(img);
		}
	};
	
	var btn=document.getElementById("ajax_upload");
	btn.onclick=function(){
		var files = images.files;
		var frm = new FormData();//空白表单
		//将文件添加到frm中
		for(var i=0; i<files.length; i++){
			var f = files[i];
			frm.append("images", f, f.name);
		}
		//发起Ajax请求
		btn.value = "上载中...";
		var xhr = new XMLHttpRequest();
		xhr.open("post", "uploadImages.do");
		xhr.onreadystatechange = function(){
			if(xhr.readyState==4&&xhr.status==200){
				var json=JSON.parse(xhr.responseText);
				console.log(json);
				selected.innerHTML=json.message;
				btn.value = "ajax上载";
			}
		};
		xhr.send(frm); 
	};	

</script>

</html>








