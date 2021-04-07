<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>

<%@page import="com.kp.cms.constants.CMSConstants"%><html>
<head>
<meta content="utf-8" http-equiv="encoding">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 
<%-- 
<link rel="stylesheet" href="css/admission/OnlineApplicationFormNew.css"/>
<LINK REL=StyleSheet HREF= "css/admission/AdmissionResponsiveTabs.css" TYPE="text/css">

--%>

<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<link rel="stylesheet" href="css/admission/css/style.css"/>

<script src="js/AC_RunActiveContent.js" type="text/javascript"></script>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
<script type="text/javascript" src="js/common.js"> </script>
<script type='text/javascript' src="jquery/Zebra/javascript/zebra_dialog.js"></script>
<link rel="stylesheet" href="jquery/Zebra/css/default/zebra_dialog.css"  />
<style type="text/css">
.ui-datepicker {
        font-family:Garamond;
        font-size: 14px;
        margin-left:10px
     }
</style>

<script type="text/javascript">


function resetLogin(){   
	document.getElementById("loginDateOfBirth").value = "";
	document.getElementById("uniqueId").value = "";
	
}
function myCompany() {
    window.open("http://www.google.com");
}
</script>
 
 
<script>
  function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
</script>

<style type="text/css">
	
		.tooltip{
   			display: inline;
    		position: relative;
		}
		
		.tooltip:hover:after{
    		background: #333;
    		background: rgba(0,0,0,.8);
    		border-radius: 5px;
    		bottom: 26px;
    		color: #fff;
    		content: attr(title);
    		left: 20%;
    		padding: 5px 15px;
    		position: absolute;
    		z-index: 98;
    		width: 140px;
    		height: 80px
    		
		}
		
		.tooltip:hover:before{
    		border: solid;
    		border-color: #333 transparent;
    		border-width: 6px 6px 0 6px;
    		bottom: 20px;
    		content: "";
    		left: 50%;
    		position: absolute;
    		z-index: 99;
		}
	
	
.text-label{
	color:#cdcdcd;
	font-weight:bold;
	}
	.logTag{
		background-color: rgba(255, 255, 255, 0.60);
		height:290px;
		width:270px;
		border-bottom-right-radius:12px;
		border-bottom-left-radius:12px;
		color: rgb(0, 15, 94);
	}
	
	</style>
	

 
 <!-- for cache controling with html code-->
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

 <!-- for cache controling with jsp code-->
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>
 
 
<title>Online Application Form</title>
</head>

<body style='background-size: 100% 99.9%; background-image: url("images/admission/images/DevaMatha.jpg"); background-repeat: no-repeat; background-position: center center; background-attachment: fixed;'>
<html:form action="/uniqueIdRegistration" method="post">
<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="uniqueIdRegistrationForm" />
	<html:hidden property="mode" name="uniqueIdRegistrationForm" styleId="mode" />
	<html:hidden property="pageType" name="uniqueIdRegistrationForm" styleId="pageType" />
	<html:hidden property="offlinePage" styleId="offlinePage" name="uniqueIdRegistrationForm"/>
	<html:hidden property="serverDownMessage" styleId="serverDownMessage" name="uniqueIdRegistrationForm" />	

	<table cellpadding="0" cellspacing="0" align="center" style="float: left;">
	 	<tr>
	 		<td width="60%">
	 			<table style="border-collapse: collapse; margin-top: 150px;margin-left: 30px">
	 				<tr>
	 					<td class="st_login" style="background-color: rgba(255, 255, 255, 0.60); color: rgb(0, 15, 94)">CANDIDATE LOGIN</td>
	 				</tr>
	 				<tr style="margin: 40%">
	 					<td>
	 						<div class="logTag">
				  				<div style="height:10px"></div>	  
				  				<!-- errors display -->
			       				<div align="center" class="subheading" >
									<div id="errorMessage" align="center">
										<FONT color="red"><html:errors /></FONT>
									</div>
				   					<div id="errorMessage1" style="font-size: 13px; color: red"></div>
				  				</div>	  
				  				<br/>
			         			<div class="login_input">  
			         				&nbsp;&nbsp;User Id: 
			         				<html:text name="uniqueIdRegistrationForm" 
			         						   property="loginDateOfBirth" 
			         				 		   styleId="loginDateOfBirth" 
			         			  			   size="10" 
			         						   maxlength="10" 
					   						   styleClass="textboxmedium"/> 
					 				<script language="JavaScript">
										$(function(){
											var d = new Date();
											var year = d.getFullYear() - 15 ;
											  $("#loginDateOfBirth").datepicker({dateFormat:"dd/mm/yy",
												changeMonth: true,
												changeYear: true,
												yearRange: '1940:' + year, 
												defaultDate: new Date(year, 0, 1),
												reverseYearRange: true
												});
										});
									</script> 
			         				<a href="#" title="If Already Registered, Enter your date of birth as your User ID" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/tooltip_icon1.png"/></span></a>
			        			</div>
			      				<br/>
			      				<div class="login_input">Password:
			      					<html:password  property="uniqueId" name="uniqueIdRegistrationForm" styleClass="textboxmedium" styleId="uniqueId" />
			     					<a href="#" title="Please enter the password sent to you as SMS or Email " class="tooltip"><span title="Help"><img alt="" src="images/admission/images/tooltip_icon1.png"/></span></a>        
			      				</div>     
			      				<br/>
			      				<div align="center">
			       				<html:submit value="Login"  styleClass="formbutton" styleId="login_validate"/>
			  		 			&nbsp;&nbsp; &nbsp; 
			   	  				<html:button property="" value="Clear" onclick="resetLogin()" styleClass="formbutton"/> 
						      	<br/><br/>
				 				<html:button property="" value="Forgot Password" onclick="forgotUniqueId()" styleClass="formbutton"/> 
			      				<br/><br/><br/>
				  				<html:button property="" value="Registration"  styleClass="cntbtn" onclick="register()" /></div>
			      			</div>
	 					</td>
	 				</tr>
	 			</table>
	  		</td>
	 	</tr>
	</table>
</html:form>
</body>

<script src="js/admission/UniqueIdRegistrationLoginPage.js" type="text/javascript"></script>

 <script type="text/javascript">

	 $("#loginDateOfBirth").attr("placeholder","dd/mm/yyyy"); 
 
 </script>

</html>
