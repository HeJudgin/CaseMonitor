<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta name="referrer" content="no-referrer" /><!--页面头部添加-->
<base href="<%=basePath%>">
<title>ADRW IMAGE SERVER</title>
<!--css-->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="css/swipebox.css" type="text/css" media="all">
<!--css-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="IMAGES" />
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
<!-- swipe box js -->
<script src="js/jquery.swipebox.min.js"></script> 
	<script type="text/javascript">
		jQuery(function($) {
			$(".swipebox").swipebox();
		});
</script>
<!-- //swipe box js -->
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
function imgSugg()
{
    var qry = document.getElementById("query").value
    var ckid = document.getElementById("ckid").value
    var kwd = document.getElementById("kwd").value
    if(ckid=="" && kwd==""){
        alert("CKID OR KWD 输入不可为空");
        return;
    }
    var container = document.getElementById("image_grid")
    var url = 'data/imgSugg';
    $.ajax({
		type: 'POST',
		url: url,
		data:{query:qry+'', query_org:qry+'', kwd:kwd+'', ckid:ckid+''},
		success: function fun(data){
		    if(data['ret'] == '-1'){
			    window.location.href = data['url'];
			    return;
			}
		    html_str = data['html_str'];
		    container.innerHTML = html_str
		    container.scrollTo(0,0)
		}
	});
}

function imgSample(type)
{
    var qry = document.getElementById("query").value
    var ckid = document.getElementById("ckid").value
    var kwd = ""
    if(type == "kwd"){
        kwd = document.getElementById("kwd").value    
    }
    var url = 'data/getRandSample';
    $.ajax({
		type: 'POST',
		url: url,
		data:{kwd:kwd+''},
		success: function fun(data){
		    if(data['ret'] == '-1'){
			    window.location.href = data['url'];
			    return;
			}
		    document.getElementById("ckid").value = data['ckid']
		    document.getElementById("query").value = data['query']
		    document.getElementById("kwd").value = data['kwd']
		    
		}
	});
}
</script>

</head>
<body>
<div class="page" style="padding:0px; margin_top:0px; margin_bottom:0px; ">
    <div class="container" style="padding:0px; margin_top:0px; margin_bottom:0px; ">	  

    <!--Forms-->
     <!--
    <div class="short-heading"><h3 class="tittle">ImageServer</h3></div>
    -->
    <div class="page-header" style="padding:0px; margin_top:0px; margin_bottom:0px; "><h3>ImageServer</h3></div>
	        <div class="bs-example animated wow fadeInRight" data-wow-duration="1000ms" data-example-id="simple-horizontal-form" style="padding:0px; margin_top:0px; margin_bottom:0px; ">
        <div class="form-group">
            <label for="query" class="col-sm-2 control-label">QUERY</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="query" id="query" placeholder="">
            </div><br/><br/>
            <label for="query" class="col-sm-2 control-label">KWD</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="kwd" id="kwd" placeholder="">
            </div><br/><br/>
            <label for="ckid" class="col-sm-2 control-label">CKID</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="ckid" id="ckid" placeholder="" value="10001207791">
            </div><br/><br/>
         <div class="col-sm-10" align=center>
             <button type="button" class="btn btn-1 btn-success" onclick="imgSugg();">广告查询</button>
             <button type="button" class="btn btn-1 btn-success" onclick="imgSample();">随机采样</button>
             <button type="button" class="btn btn-1 btn-success" onclick="imgSample('kwd');">关键词采样</button>
             
         </div>
         </div>	
    </div>
  </div>
</div>	    
<!--header-->		
<div class="content" style="padding:0px; margin_top:0px; margin_bottom:0px; ">
	<!--photos-->
	<div class="photos" style="padding:0px; margin_top:0px; margin_bottom:0px; ">
		<div class="container" style="padding:0px; margin_top:0px; margin_bottom:0px; ">
			<div class="photos-grids" id="image_grid" style="overflow-y: scroll; max-height:500px; width:100%; padding-bottom:300px;" >
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!--photos-->
</div>
		
</body>
</html>
