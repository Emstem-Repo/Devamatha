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

        <title>Knowledge Pro | Student Login</title>

        <link href="dist/css/style.default.css" rel="stylesheet">

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->
		
		<script>
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
    </head>
     
    <body>
        
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
