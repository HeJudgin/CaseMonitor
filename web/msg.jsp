<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Happy Every Day</title>
<!--css-->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!--css-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Conjugal  Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, Sony Ericsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--timer-->
<link rel="stylesheet" href="css/jquery.countdown.css" />
<!--timer-->
<!--js-->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<!--js--> 
<!--webfonts-->
<link href='//fonts.googleapis.com/css?family=Racing+Sans+One' rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Open+Sans:400,800italic,800,700italic,700,600italic,600,400italic,300italic,300' rel='stylesheet' type='text/css'>
<!--webfonts-->
<!--startsmothscrolling-->
<script type="text/javascript" src="js/move-top.js"></script>
<script type="text/javascript" src="js/easing.js"></script>
 <script type="text/javascript">
		jQuery(document).ready(function($) {
			$(".scroll").click(function(event){		
				event.preventDefault();
				$('html,body').animate({scrollTop:$(this.hash).offset().top},1200);
			});
		});
	</script>
<!--endsmothscrolling-->

<script type="text/javascript">
window.setInterval(function(){ 
      onGetMessage("HY", "HJJ", "æäºæ¶åï¼å¦å¼åèå¤©ç¨åºï¼ï¼æä»¬éè¦å°å°æ»å¨æ¡ï¼scrollbarï¼ä¿æå¨æåºé¨ï¼æ¯å¦èå¤©çªå£ï¼ææ°ååºåæ¶å°çä¿¡æ¯è¦æ¾ç¤ºå¨æä¸æ¹ï¼å¦æè¦çå°æä¸æ¹çåå®¹ï¼å°±å¿é¡»ä¿è¯æ»å¨æ¡ä¿æå¨æåºé¨"); 
}, 1000);

function onGetMessage(sender, user, msg) 
{
    var container = document.getElementById("msg_body");
    var msg_end = document.getElementById("msg_end");
    if (msg_end != null){  msg_end.parentNode.removeChild(msg_end); }
    
    end="<div><a id=\"msg_end\" name=\"1\" href=\"#1\">&nbsp</a></div>"
    var item_send = "<div class=\"alert alert-success\" role=\"alert\"><strong>"+sender+":</strong> "+msg+"</div>";
	var item_recive = "<div class=\"alert alert-info\" role=\"alert\"><strong>"+sender+":</strong> "+msg+"</div>";
	if(item_send!=item_recive){
	    container.innerHTML += item_recive;
	}else{
	    container.innerHTML += item_send;
	}
	container.innerHTML += end;
	var msg_end = document.getElementById("msg_end");
    container.scrollTop = container.scrollHeight;
} 


function flushMsg()
{
    var url = '${pageContext.request.contextPath }/user/delUserById';
	$.ajax({
		type: 'POST',
		url: url,
		data:{id:''+id},
		success: function fun(data){
		if(data['status']=='success'){
				
			}
		}
	});
}
</script>
</head>
<body>
	<div class="header" id="head">
		<div class="container">
			<div class="header-menu">
				<nav class="navbar navbar-default">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
						  <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						  </button>
							<div class="navbar-brand logo">
								<h1><a href="index.html">Conjugal</a></h1>
							</div>
						</div>
						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav">
								<li><a href="#" data-hover="Home">Home </a></li>
								<li><a data-hover="Photos" href="photos.html">Photos</a></li>
							</ul>
						</div><!-- /.navbar-collapse -->
					</div><!-- /.container-fluid -->
				</nav>
			<div class="clearfix"></div>
			</div>	
		</div> 
	</div>
		<!--header-->		
		<div class="content">
		<div class="page">
			<div class="container">
				<div class="short-heading">
					<h2 class="tittle">CAHT </h2>
				</div>
				<!--button-->
				<div class="grid_3 grid_4">
					<div class="page-header">
			  </div>
		  
				 
			   <!--alerts-->
			   <div class="page-header animated wow fadeInLeft" data-wow-duration="1000ms">
				<h3>MSG</h3>
			  </div>
			  
			  
             <div id="msg_body" class="table-responsive"  style="overflow-y:auto;height:280px;"></div>
			  
			  
			 
			   
			  
			 
			<!--Forms-->
			<div class="page-header">
				<h3>Forms</h3>
			</div>
				<div class="bs-example animated wow fadeInRight" data-wow-duration="1000ms" data-example-id="simple-horizontal-form">
					<form class="form-horizontal">
					  <div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">MSG</label>
						<div class="col-sm-10">
						  <input type="email" class="form-control" id="inputEmail3" placeholder="msg">
						  
						</div>
					  </div>
					  <div class="form-group">
						<div class="col-sm-offset-2 col-sm-10"> 
						  <button type="submit" class="btn btn-default">SEND</button>
						</div>
					  </div>
					</form>
				</div>
			
			<!--forms-->
			</div>
		</div>
	</div>
		
</body>
</html>
