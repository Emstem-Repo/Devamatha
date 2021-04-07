<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link rel="stylesheet" href="css/AdminLoginForCJC.css"  />

<script src="js/md5.js" type="text/javascript"></script>

<script type="text/javascript" src="js/common.js">
</script>
<html:form action="/LoginAction">
	<html:hidden property="method" styleId="method" value="loginAction" />
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="homePage" styleId="homePage" name="loginform" />
<body>	
	<!--Start Body  -->
	<div id="container">
	<div id="wrapper">
	<div id="LoginFormDiv">
	<!--Start Login form -->
	<div id="st_login">Login
	</div>
	<font color="red" size="3"><div id="errorMessage" align="center">
             <FONT color="red"><html:errors/></FONT>
             <FONT color="green">
					<html:messages id="msg" property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
					</html:messages>
			 </FONT>
	</div></font>
	<center>
	<form>
	<p align="center" style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold; color: #4572e0;">User Name:&nbsp;<html:text property="userName" styleId="username" size="30" maxlength="50" styleClass="text"></html:text></p>
	<p align="center" style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold; color: #4572e0;">Password:&nbsp;&nbsp;<html:password property="password" styleId="password" size="30" styleClass="password" maxlength="200"></html:password></p>
	<html:submit property="" styleClass="buttonsubmit" value="Login"  styleId="submit"/>
    <html:button property="" styleClass="buttonreset" value="Reset" onclick="resetFieldAndErrMsgs()"/>
	<!-- <input type="submit" value="Login"  /> <input type="reset" value="Reset" />-->
	<br />
	</form>
	<br />
	</center>
	<p align="right" >
	 <% String path= request.getContextPath(); %>
	<a href='<%=path %>/AdminForgotPasswordServlet' style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold; color: blue;"> Forgot Password</a></p>
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
 document.getElementById("homePage").value="yes";
  
</SCRIPT>