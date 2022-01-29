<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
    pageEncoding="ISO-8859-1" buffer="64kb"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<tiles:importAttribute scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<title>Knowledge Pro</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to Knowledge Pro</title>
<!--<link rel="stylesheet" href="css/student/student.css">
<link rel="stylesheet" href="css/calstyle.css">
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" type="text/css" href="css/displaytag.css">
<script type="text/javascript" src="js/basiccalendar.js"></script>
<script type="text/javascript" src="js/clock.js"></script>
<script type="text/javascript" src="js/common.js"></script>
-->
<link rel="stylesheet" href="css/StudentLayout1Styles.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
 <script type="text/javascript" src="js/ajax/Ajax.js"></script>
 <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
 <!--<script type="text/javascript" src="js/leftSwitchMenu.js"></script>
<script type="text/javascript" src="js/sdmenu/sdMenuStudLogin.js"></script>
-->
<script type="text/javascript" src="js/StudenLayoutCommon1.js"></script>
</head>
<script type="text/javascript">
	javascript:window.history.forward(1);
</script>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
		
        <!-- Include header.jsp -->
      
        <tr><td colspan="3">
       		   <tiles:insert attribute="header" flush="false"/>
         </td></tr><!--
         //commented by giri
         <c:if test="${pendingCount >= 1 && showCertCourse}">
         	<tr ><td colspan="3" align="center" class="HomePagerow-white"><font color="red"> Need to complete 2 mandatory certificate course within first 4 semesters
         	<br/>( Already Completed : <%= session.getAttribute("completedSubCount")%> & Pending : <%= session.getAttribute("pendingCount")%>)
         	</font></td>
       	</tr>	
       	 </c:if>
       	 //end by giri
        --><!-- End of include.jsp -->		
		<!-- Content Pane Begins-->
       	
		<tr>
			<td width="3%">&nbsp;</td>
			<td width="8%" height="500" valign="top" style="padding-left: 5px; padding-top: 1px ;">
			<div style="overflow: auto; height:500px">
                <tiles:insert attribute="left" flush="false"/></div>
            </td>
            <td valign="top">
					<tiles:insert attribute="body" flush="true"/>
			</td>
		</tr>
		
		<!-- Footer  -->
		<tr>
		<td colspan="3" >
       		   <tiles:insert attribute="footer" flush="false"/>
         </td></tr>
		<!-- Content Pane Ends-->
	</table>

</body>
</html>
