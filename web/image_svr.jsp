<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta name="referrer" content="no-referrer" /><!--页面头部添加-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ADRW</title>
<!--css-->
<link href="${pageContext.request.contextPath }/css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<link href="${pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/swipebox.css" type="text/css" media="all">

<!--css-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Conjugal  Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, Sony Ericsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--timer-->
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery.countdown.css" />
<!--timer-->
<!--js-->
<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
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

<!--timer--><!--js--><script src="js/jquery.min.js"></script><script src="js/bootstrap.min.js"></script><!--js--> <!--webfonts--><link href='//fonts.googleapis.com/css?family=Racing+Sans+One' rel='stylesheet' type='text/css'>
<!-- 
<link rel="import" href="top.html" id="top"/>
-->
<link href='//fonts.googleapis.com/css?family=Open+Sans:400,800italic,800,700italic,700,600italic,600,400italic,300italic,300' rel='stylesheet' type='text/css'><!--webfonts--><!--startsmothscrolling--><script type="text/javascript" src="js/move-top.js"></script><script type="text/javascript" src="js/easing.js"></script> <script type="text/javascript">		jQuery(document).ready(function($) {			$(".scroll").click(function(event){						event.preventDefault();				$('html,body').animate({scrollTop:$(this.hash).offset().top},1200);			});		});
</script><!--endsmothscrolling-->
<style type="text/css">
        .contant:hover{
                text-overflow: ellipsis;
                white-space: nowrap;
                
        }
        .contant{
                text-overflow: ellipsis;
                white-space: nowrap;
                display: inline-block;
                width:100px;
                border:1px solid red;
                overflow:hidden;

        }

</style>

<script type="text/javascript">
function imgSugg()
{
    var qry = document.getElementById("query").value
    var ckid = document.getElementById("ckid").value
    if(ckid==""){
        alert("输入不可为空");
        return;
    }
    var container = document.getElementById("table_lab_body")
    var url = 'data/imgSugg';
    $.ajax({
		type: 'POST',
		url: url,
		data:{query:qry+'', query_org:qry+'', ckid:ckid+''},
		success: function fun(data){
		    if(data['ret'] == '-1'){
			    //window.location.href = data['url'];
			    return;
			}
		    html_str = data['html_str'];
		    container.innerHTML = html_str
		}
	});
}

function imgSample()
{
    var qry = document.getElementById("query").value
    var ckid = document.getElementById("ckid").value
    var url = 'data/getRandSample';
    $.ajax({
		type: 'POST',
		url: url,
		data:{},
		success: function fun(data){
		    if(data['ret'] == '-1'){
			    //window.location.href = data['url'];
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


<div class="page">
<div class="container">	  

<!--Forms-->
     <!--
    <div class="short-heading"><h3 class="tittle">ImageServer</h3></div>
    -->
    <div class="page-header"><h3>ImageServer</h3></div>
     
    <div class="bs-example animated wow fadeInRight" data-wow-duration="1000ms" data-example-id="simple-horizontal-form">
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
             <button type="button" class="btn btn-1 btn-success" onclick="imgSugg();">查询</button>
             <button type="button" class="btn btn-1 btn-success" onclick="imgSample();">采样</button>
             
         </div><br><br>
         </div>	
    </div>
    
    		
    
    
    
    
    
    
    <div class="table-responsive" style="overflow-xy: scroll; max-height:70%; width:100%">
		 <table id="table_lab" class="table table-bordered table-striped animated wow fadeInUp" data-wow-duration="1000ms" >
			<tbody id="table_lab_body">${html_str }</tbody>
		 </table>
	</div>
</div>
</div>

<div class="content">
			<!--photos-->
				<div class="photos">
					<div class="container">
						<h2 class="tittle">Our Photos</h2>
						
						<!--  -->
						
						<div class="photos-grids">
							<div class="col-md-4 gal-left">
								<div class="content-grid-effect slow-zoom vertical text-center">
									<a href="images/g1.jpg" class="b-link-stripe b-animate-go  swipebox">
										<div class="img-box">
											<img src="images/g1.jpg" alt="image" class="img-responsive zoom-img">	
										</div>
								
										<div class="info-box">
											<div class="info-content">
												<h4>Wedding</h4>
												<span class="separator"></span>
												<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam</p>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-md-4 gal-left">
								<div class="content-grid-effect slow-zoom vertical text-center">
									<a href="images/g2.jpg" class="b-link-stripe b-animate-go  swipebox">
										<div class="img-box">
											<img src="images/g2.jpg" alt="image" class="img-responsive zoom-img">	
										</div>
										<div class="info-box">
											<div class="info-content">
												<h4>Wedding</h4>
												<span class="separator"></span>
												<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam</p>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-md-4 gal-left">
								<div class="content-grid-effect slow-zoom vertical text-center">
									<a href="images/g3.jpg" class="b-link-stripe b-animate-go  swipebox">
										<div class="img-box">
											<img src="images/g3.jpg" alt="image" class="img-responsive zoom-img">	
										</div>
										<div class="info-box">
											<div class="info-content">
												<h4>Wedding</h4>
												<span class="separator"></span>
												<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam</p>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-md-4 gal-left gal_mar">
								<div class="content-grid-effect slow-zoom vertical text-center">
									<a href="images/g4.jpg" class="b-link-stripe b-animate-go  swipebox">
										<div class="img-box">
											<img src="images/g4.jpg" alt="image" class="img-responsive zoom-img">	
										</div>
									
										<div class="info-box">
											<div class="info-content">
												<h4>Wedding</h4>
												<span class="separator"></span>
												<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam</p>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-md-4 gal-left gal_mar">
								<div class="content-grid-effect slow-zoom vertical text-center">
									<a href="images/g5.jpg" class="b-link-stripe b-animate-go  swipebox">
										<div class="img-box">
											<img src="images/g5.jpg" alt="image" class="img-responsive zoom-img">	
										</div>
									
										<div class="info-box">
											<div class="info-content">
												<h4>Wedding</h4>
												<span class="separator"></span>
												<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam</p>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-md-4 gal-left gal_mar">
								<div class="content-grid-effect slow-zoom vertical text-center">
									<a href="images/g6.jpg" class="b-link-stripe b-animate-go  swipebox">
										<div class="img-box">
											<img src="images/g6.jpg" alt="image" class="img-responsive zoom-img">	
										</div>
									
										<div class="info-box">
											<div class="info-content">
												<h4>Wedding</h4>
												<span class="separator"></span>
												<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam</p>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-md-4 gal-left gal_mar">
								<div class="content-grid-effect slow-zoom vertical text-center">
									<a href="images/g7.jpg" class="b-link-stripe b-animate-go  swipebox">
										<div class="img-box">
											<img src="images/g7.jpg" alt="image" class="img-responsive zoom-img">	
										</div>
									
										<div class="info-box">
											<div class="info-content">
												<h4>Wedding</h4>
												<span class="separator"></span>
												<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam</p>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-md-4 gal-left gal_mar">
								<div class="content-grid-effect slow-zoom vertical text-center">
									<a href="images/g8.jpg" class="b-link-stripe b-animate-go  swipebox">
										<div class="img-box">
											<img src="images/g8.jpg" alt="image" class="img-responsive zoom-img">	
										</div>
									
										<div class="info-box">
											<div class="info-content">
												<h4>Wedding</h4>
												<span class="separator"></span>
												<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam</p>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-md-4 gal-left gal_mar">
								<div class="content-grid-effect slow-zoom vertical text-center">
									<a href="images/g1.jpg" class="b-link-stripe b-animate-go  swipebox">
										<div class="img-box">
											<img src="images/g1.jpg" alt="image" class="img-responsive zoom-img">	
										</div>
									
										<div class="info-box">
											<div class="info-content">
												<h4>Wedding</h4>
												<span class="separator"></span>
												<p>Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam</p>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				        
				</div>
			<!--photos-->
		</div>





</body></html>
