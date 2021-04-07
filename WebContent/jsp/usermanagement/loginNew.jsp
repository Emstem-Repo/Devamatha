<!DOCTYPE html>
<html lang="en">
	<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
	<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
	<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
	<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
	<%@page import="com.kp.cms.constants.CMSConstants"%>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<head>
		<title>Knowledge Pro | Login</title>
		<!-- CSS -->
		<link rel="stylesheet" href="dist/css/bootstrap.min.css">
		<link rel="stylesheet" href="dist/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/form-elements.css">
		<link rel="stylesheet" href="css/style.css">
		<link href="css/style.css" rel='stylesheet' type='text/css' />
		<link rel="stylesheet" href="css/webticker.css" />
		<link rel='stylesheet' href="css/auditorium/start/jquery-ui.css" />
		<link rel="stylesheet" href="jquery/Zebra/css/default/zebra_dialog.css" />
		<script type="text/javascript" src="js/jquery.webticker.js"></script>
		<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
	   <script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
		<script type='text/javascript' src="jquery/Zebra/javascript/zebra_dialog.js"></script>
	
		<style type="text/css">
		.btn {
			width: 25%;
			margin-left: auto;
		    margin-right: auto;
		    text-align: center;
		}
		.logo {
			border-color: white;
			margin-top: 10px;
			margin-bottom: 2px;
			border: 0px solid rgba(0, 0, 0, 0.5);
		}
		
		.form-box {
			background: rgba(0, 0, 0, 0.35) none repeat scroll 0 0;
			border-radius: 10px;
			box-shadow: 0 0 2px #aaa;
			border-image: none;
			  margin-left: 0px;
			  width: 254px;
			  margin-top: 120px

		}
		body{
		 	background-repeat: no-repeat;
		 	background-size: cover;	
		 	margin: auto;
		 	overflow: hidden;
		}
		</style>
	</head>

	<body style='background-size: 100% 99.9%; background-image: url("images/AdminLogin/Devamatha-background.JPG?"); background-repeat: no-repeat; background-position: center center; background-attachment: fixed;'>
	
		<html:form action="/LoginAction">
			<html:hidden property="method" styleId="method" value="" />
			<html:hidden property="formName" value="loginform" />
			<html:hidden property="pageType" value="1" />
			<html:hidden property="homePage" styleId="homePage" name="loginform" />
			<html:hidden property="serverDownMessage" styleId="serverDownMessage" name="loginform" />
		
			<!-- Top content -->
			<div class="top-content">
				<div class="inner-bg">
					<div class="container">
						<div id="timer" align="center">
							<font color="#FF0000">Your last session was terminated incorrectly or is currently in use.Please try after 15 minutes.</font>
							<input id="minutes"
								   style="width: 25px; border: none; background-color: none; font-size: 16px; font-weight: bold;"
								   readonly="readonly">
							<blink>:</blink>
							<input id="seconds"
								   style="width: 26px; border: none; background-color: none; font-size: 16px; font-weight: bold;"
								   readonly="readonly">
						</div>
						<div id="timer1">
							<font color="#FF0000">
								The User Already Logged In!!
								<img src="images/user-login-icon.png" style="width: 50px; height: 50px;" class="img-responsive"></img>
							</font>
						</div>
						<div class="row">
		    				<div class="col-sm-4"></div>
					   		<p>&nbsp;</p>
					        <p>&nbsp;</p>
		    				<div class="col-sm-8" >
		      					<div class="col-sm-8 col-sm-offset-6 form-box">
									<div class="logo">
										<table width="100%">
											<tr height="70">
												<td align="center" style="background-color:white;-webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;">
												<img src='images/header-logo.png'
													alt="Logo not available" class="img-responsive" width="50"
													height="15">
												</td>
											</tr>
										</table>
									</div>
									<div style="height:24px;">
										<table>
											<tr>
												<td>
													<div id="errorMessage" align="center" style="color: red; font-size: 17px;">
														<FONT color="red" ><html:errors /></FONT>
														<FONT color="green">
															<html:messages id="msg" property="messages" message="true">
																<c:out value="${msg}" escapeXml="false"></c:out>
															</html:messages>
														</FONT>
													</div>
												</td>
											</tr>
										</table>
									</div>
									<div>
										<div class="login-form" style="line-height: 70px;">
											<div class="form-group">
												<label class="sr-only" for="form-username">Username</label>
												<html:text property="userName"
														   styleId="username" 
														   size="20" 
														   maxlength="50"
														   styleClass="form-username form-control" style="height: 40px;"></html:text>
											</div>
											<div class="form-group">
												<label class="sr-only" for="form-password">Password</label>
												<html:password property="password" 
															   styleId="password" 
															   size="20"													
															   styleClass="form-password form-control" 
															   maxlength="200"
															   style="background: #eee ;height: 40px;"></html:password>
											</div>
											<div>
												<table width="100%">
													<tr>
														<td align="right">
														<button type="submit" class="btn"style="background: #ff6600;padding: 5px;padding-top: 0;" id="submit"><b>Login</b></button>
														</td>
													</tr>
												</table>
											</div>
										</div>
									</div>
								</div>
		    				</div>
						</div>
					</div>
					<br></br>
					<div class="col-sm-12 text" align="left"></div>
				</div>
			</div>
		</html:form>
	</body>
	<script src="js/loginPageNew.js"></script>