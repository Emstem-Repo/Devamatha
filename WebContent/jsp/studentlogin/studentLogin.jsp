<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="css/CJCStudentLogin.css"  />
<html:form action="/StudentLoginAction">
	<html:hidden property="method" styleId="method" value="studentLoginAction" />
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="pageType" value="1" />
	<body>
	<!--Start Body  -->
	<div id="container">
	<div id="wrapper">
	<div id="LoginFormDiv">
	<!--Start Login form -->
	<div id="st_login">Student Login
	</div>
	<div id="errorMessage" align="center">
             <FONT color="red"><html:errors/></FONT>
             <FONT color="green">
					<html:messages id="msg" property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
					</html:messages>
			 </FONT>
	</div>
	<center>
	<form>
	<p align="center" style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold; color: #636161;">User Name:<html:text property="userName" styleId="username" size="30" maxlength="50" styleClass="text"></html:text></p>
	<p align="center" style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold; color: #636161;">Password:&nbsp;&nbsp;<html:password property="password" styleId="password" size="30" styleClass="password" maxlength="200"></html:password></p>
	<html:submit property="" styleClass="buttonsubmit" value="Login"/>
    <html:button property="" styleClass="buttonreset" value="Reset" onclick="resetFieldAndErrMsgs()"/>
	<!-- <input type="submit" value="Login"  /> <input type="reset" value="Reset" />-->
	<br />
	</form>
	<br />
	</center>
	<p align="right" >
	 <% String path= request.getContextPath(); %>
	<a href='<%=path %>/ForgotPasswordServlet' style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold; color: blue;"> Forgot Password</a></p>
	<!--End login form -->
	<div id="News">
	<marquee behavior="scroll" direction="up"
			scrollamount="2" width=220 height=150
		    style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold; color: black"
			onmouseover="this.setAttribute('scrollamount', 0, 0);"
			onmouseout="this.setAttribute('scrollamount', 1, 0);">
			<c:out
			value='${loginform.description}' escapeXml='false'  />													
			</marquee>
		</div>
	
		</div>
	</div>
	
	</div>
	
	
	
	
	
	
	
	<!--End of body -->
	
	</body>
</html:form>
<SCRIPT language="JavaScript">
var browserName=navigator.appName; 
 if (browserName=="Microsoft Internet Explorer")
 {
	 alert(" If Your using InternetExplorer Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling  or Use Mozilla FireFox");
 }
</SCRIPT>