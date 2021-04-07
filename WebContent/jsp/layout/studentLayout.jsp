<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" buffer="64kb"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>


<tiles:importAttribute scope="request"/>

<!--<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
-->
<!DOCTYPE HTML>
<html >
<title>Knowledge Pro</title>
<head>
 <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to Knowledge Pro</title>
<!--<link rel="stylesheet" href="css/student/student.css">
<link rel="stylesheet" href="css/calstyle.css">
<script type="text/javascript" src="js/basiccalendar.js"></script>
<script type="text/javascript" src="js/clock.js"></script>
<script type="text/javascript" src="js/common.js"></script>
-->
<link rel="stylesheet" href="css/StudentLayoutStyles.css">
<script type="text/javascript" src="js/StudentLayoutCommon.js"></script>
<style>
body{margin:0; padding:0;}
.heads
{
font-family:Verdana, Arial, Helvetica, sans-serif;
color:#3292BA;
font-weight: bold;
font-size:11px;
}
.nav{font-family:Verdana, Arial, Helvetica, sans-serif;
color:#FFFFFF;
font-size:11px;
font-weight: bold;}
.navmenu{font-family:Verdana, Arial, Helvetica, sans-serif;color:#3292BA;font-size:12px; text-decoration:none}
.navmenu:hover{font-family:Verdana, Arial, Helvetica, sans-serif;color:#3292BA;font-size:12px; text-decoration:underline;}
.td{
border-left:1px solid #45B6E5;
border-right:1px solid #45B6E5
}
.navmenu1{font-family:Verdana, Arial, Helvetica, sans-serif;
color:#3292BA;
font-size:11px;}

.copyright { font-family:Verdana, Arial, Helvetica, sans-serif; color:#fff; font-size:11px;}
 
</style>
</head>

<script type="text/javascript">
	javascript:window.history.forward(1);
</script>

<body>

<tiles:insert attribute="header" flush="false"/>
         
<tiles:insert attribute="body" flush="false"/>	
                
<tiles:insert attribute="footer" flush="false"/>

</body>
</html>
