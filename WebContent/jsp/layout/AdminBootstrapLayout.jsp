<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" buffer="64kb"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>


<tiles:importAttribute scope="request"/>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Knowledge Pro | Admin Login</title>

        <link href="dist/css/style.default.css" rel="stylesheet">

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->
		
		<!--<script>
		javascript:window.history.forward(1);
		function setDefaultImage(source) {
        var badImg = new Image();
        badImg.src = "dist/images/photos/profile.png";
        var cpyImg = new Image();
        cpyImg.src = source.src;
        if (!cpyImg.width) {
        source.src = badImg.src
           }
        }

		function onImgError(source) {
			source.src = "dist/images/photos/profile.png";
			source.onerror = "";
			return true
		}
		</script>
    -->
     <style>
 /*   .body {
    font-size: 13px;
    color: #636E7B;
    background-color: #e91ec721;
}*/
.panel-primary
{

    /*height: 58px;
    width: 1159px;*/
     margin-top: -20;
    color: #2196f3e3;
}
.panel{
/*width:1159px;*/
color: #0c0c0ce3;
}
.panel panel-primary
{
/*width: 1159px;*/
color: #2196f3e3;

 
}
.navbar navbar-default{
 
    width: 1204px;
      background-color: #ffc107db;
    
}
.leftpanel {
    width: 230px;
  background-color: #99ebff;
      top: -137px;
    left: 0;
   margin-top: 70px;

}
.navbar-default .navbar-nav>li>a {
    color: #0f1010ed;
}

.panel-primary>.panel-heading {
    color: #fff;
    background-color:  #2196f3e3;
    border-color: #2196f3e3;
}
.leftpanel .nav > li > a i {
    width: 16px;
    margin-right: 5px;
    color: #080808;
    font-size: 15px;
    top: 1px;
    position: relative;
    text-align: center;
}
    </style>
    
    </head>
  <!--     <script type="text/javascript">
	javascript:window.history.forward(1);

/* start when they close browser without logout doing session invalidate*/
 
var hook = true;
$(function() {
	 $("a,.formbutton").click(function(){
		hook =false;
	  });
});
$(document).keydown(function(event) {
    var keycode = event.keyCode;
    if(keycode == '116') {
       return false;
    }
});


window.onbeforeunload = confirmExit;
  function confirmExit()
  {
	if(hook){
			var deleteConfirm = confirm("You have attempted to leave this page.  If you continue, the current session will be inactive?");
			if (deleteConfirm == true) {
				document.location.href = "LogoutAction.do?method=logout";
				return true;	
			}else{
				return false;
			}
		}else{
			hook =true;
		}
  }
  /* end when they close browser without logout doing session invalidate*/
</script>-->
    
    
   
     
    <body style="background-color: 	#ffffff; margin-top: 0px; ">
        
        <!-- start header -->

			<tiles:insert attribute="header" flush="false"/>
            
        <!--  end header -->        
        <section>
            <div class="mainwrapper">
               
               
               
               <!-- left panel start -->
               		<tiles:insert attribute="left" flush="false"/>	
               <!-- left panel end  -->
                
                <div class="mainpanel">
                 <tiles:insert attribute="body" flush="true"/>
                    
                    
                </div>
                
            </div><!-- mainwrapper -->
        </section>
        <script src="dist/js/jquery-1.11.1.min.js"></script>
        <script src="dist/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="dist/js/bootstrap.min.js"></script>
        <script src="dist/js/modernizr.min.js"></script>
        <script src="dist/js/pace.min.js"></script>
        <script src="dist/js/retina.min.js"></script>
        <script src="dist/js/jquery.cookies.js"></script>
        <script src="dist/js/custom.js"></script>
        <script  src="js/ajax/Ajax.js"></script>
        <script src="js/ajax/AjaxUtil.js"></script>
        <script  src="js/common1.js"></script>
     	<script src="js/common.js"></script>
    </body>
</html>
