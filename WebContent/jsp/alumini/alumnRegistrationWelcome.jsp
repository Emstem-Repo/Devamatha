<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Knowledge Pro</title>
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
				background-color:  rgb(255,218,185, 0.7);
				height:320px;
				width:270px;
				border-bottom-right-radius:12px;
				border-bottom-left-radius:12px;
			}
			
			.blink_me {
			  	animation: blinker 1s linear infinite;
			}
			
			@keyframes blinker {
			  	50% {
			    	opacity: 0;
			  	}
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
	</head>
	<body>
		<html:form action="alumnRegistration">
			<html:hidden property="formName" value="aluminiRegistrationForm"/>
			<html:hidden property="method" styleId="method" value="registerAlumnRegistration"/>
			
			<table cellpadding="0" cellspacing="0" align="center" width="80%">
				<tr>
					<td class="logTag" width="80%" align="center" valign="top">
						<div align="right">
							<FONT color="red">
								 <span class='MandatoryMark'>
								 	<bean:message key="knowledgepro.mandatoryfields"/>
								 </span>
							</FONT>
						</div>
						<div id="errorMessage">
							<FONT color="red">
								<html:errors/>
							</FONT>
							<FONT color="green">
								 <html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out>
									<br>
								</html:messages>
						    </FONT>
					    </div>
						<h1>Welcome to Alumni Registration</h1>
						<br></br>
						<br></br>
						<h3>
							<a href="javascript:void(0)" style="color: green;" class="blink_me" onclick="detailsEntry('registerAlumnRegistration')">Click here to register your details</a>
						</h3>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
	<script type="text/javascript" src="js/alumini/alumnRegistrationWelcome.js"></script>
</html>