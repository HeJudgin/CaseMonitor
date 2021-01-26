<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Case标注Demo</title>
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

$(function(){
getWelMsg();
});

function getWelMsg(){
    var url = 'user/getSessionAttr';
	$.ajax({
		type: 'POST',
		url: url,
		data:{},
		success: function fun(data){
		    if(data['ret'] == '-1'){
			    window.location.href = data['url'];
			    return;
			}
		    document.getElementById("welcome_msg").innerHTML = data['username'];
		}
		    
	});
}


</script>
</head>
<body>
	
		<!--header-->		
		<div class="page">
			<div class="container">
			<!-- 
				<div class="short-heading">
					<h2 class="tittle">Case Annotations </h2>
				</div>
				
				<div class="grid_3 grid_4">
					<div class="page-header">
			  </div>
		  
			 -->
			  
			
			 <!-- 
             <div class="bs-example animated wow fadeInLeft" data-wow-duration="1000ms" data-example-id="simple-table">
              --> 
             
			
			<div class="bs-example animated wow fadeInLeft" data-wow-duration="1000ms" data-example-id="contextual-table" style="border: 1px solid #eee">
				 
				 <a href="${pageContext.request.contextPath}/utils.html">${ msg }</a>
				 <br>
				 <br>
				 
				 <a href="${ url }">${ url }</a>
				 
				 
			</div>
			  
			  
			 
			   
			  
			 <br>
		
     
		
					
			
		</div>
	</div>
		
</body>
</html>
