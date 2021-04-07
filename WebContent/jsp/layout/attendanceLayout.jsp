<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" buffer="64kb"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
<tiles:importAttribute scope="request"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Knowledge Pro</title>
<head>
	<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu/sdmenu.css"/>
	<link rel="stylesheet" type="text/css" href="css/calstyle.css">
	<link rel="stylesheet" type="text/css" href="css/displaytag.css">
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
    <script type="text/javascript" src="js/basiccalendar.js"></script>
    <script type="text/javascript" src="js/clockh.js"></script>
 	<script type="text/javascript" src="js/clockp.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
</head>
<script type="text/javascript">
	javascript:window.history.forward(1);
</script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

div.sdmenu {
	width: 165px;
	font-family: verdana, sans-serif;
	font-size: 11px;
	padding-bottom: 10px;
	background: url(images/sdmenu/bottom.gif) no-repeat  right bottom;
	color: #fff;
}
div.sdmenu div {
	background: url(images/sdmenu/title.gif) repeat-x;
	overflow: hidden;
}
div.sdmenu div:first-child {
	background: url(images/sdmenu/toptitle.gif) no-repeat;
}
div.sdmenu div.collapsed {
	height: 25px;
}
div.sdmenu div span {
	display: block;
	padding: 5px 25px;
	font-weight: bold;
	color: white;
	background: url(images/sdmenu/expanded.gif) no-repeat 10px center;
	cursor: default;
	border-bottom: 1px solid #ddd;
}
div.sdmenu div.collapsed span {
	background-image: url(images/sdmenu/collapsed.gif);
}
div.sdmenu div a {
	padding: 5px 10px;
	background: #eee;
	display: block;
	border-bottom: 1px solid #ddd;
	color: #0033CC;
	text-decoration: none;
}
div.sdmenu div a.current {
	background : #ccc;
}
div.sdmenu div a:hover {
	background : #474848 url(images/sdmenu/linkarrow.gif) no-repeat right center;
	color: #fff;
	text-decoration: none;
}
div.selected {
	background-color: #CC3366;
}
</style>
<body>
<div >    
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		
        <!-- Include header.jsp -->
        <tr><td colspan="2" valign="top">
       		   <tiles:insert attribute="header" flush="false"/>
         </td></tr>
        <!-- End of include.jsp -->		
		<!-- Content Pane Begins-->
       		
		<tr>
            <td align="left" valign="top" height="436">
                <div >
					<tiles:insert attribute="body" flush="true"/>
                </div>
			</td>
		</tr>
		<!-- Footer  -->
		<tr><td colspan="2" valign="top">
       		   <tiles:insert attribute="footer" flush="false"/>
         </td></tr>
		<!-- Content Pane Ends-->
	</table>
</div>
</body>
</html>