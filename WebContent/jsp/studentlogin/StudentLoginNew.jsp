<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<title>Knowledge Pro | Login</title>
<!--starts Css links -->
<link rel="stylesheet" href="css/Kprostyle.css"  />
<!--End Css links -->
<style>
#LoginFormDiv
{
background-color:rgba(255, 255, 255, 0.6);
height:290px;
width:260px;
display:block;
z-index:999;
position:relative;
float:left;
margin-left:20px;
margin-top:200px;
border: #333 1px threedface;
border-radius: 3px;
-moz-box-shadow: 0 0 8px 3px #666;
box-shadow: inset 0 0 1em gray;
box-shadow: 0 0 0 0 #666;
}
#LoginFormDiv2
{
background-color:rgba(255, 255, 255, 0.6);
height:280px;
width:249px;
display:block;
z-index:999;
position:relative;
float:right;
margin-right:20px;
margin-top:200px;
border: #333 1px threedface;
border-radius: 3px;
-moz-box-shadow: 0 0 8px 3px #666;
-webkit-box-shadow: 0 0 5px 3px #666;
box-shadow: 0 0 0 0 #635f6f
}
#st_login
{
height:30px;
width:220px;
margin:0 auto;
margin-top:15px;
border: #333 1px solid;
border-radius: 3px;
box-shadow: inset 0 0 1em gray;
text-align:center;
font-size:16px;
font-weight:bold;
color: rgb(0, 15, 94);
font-family:Verdana, Geneva, sans-serif;
line-height:29px;
}
@media only screen and (max-width: 600px) {
  #LoginFormDiv2
	{
		margin-top:50%;
	}
	#LoginFormDiv
	{
		margin-top:50%;
	}
}
</style>
</head>
<body style='background-size: 100% 99.9%; background-image: url("images/studentLogin/studentLogin1.jpg"); background-repeat: no-repeat; background-position: center center; background-attachment: fixed;'>
<script src="jquery/js/jquery-1.7.1.min.js" type="text/javascript"></script>

<script type="text/javascript">
$(window).on('load', function () { 	
	var i = 0;
    var images = ["studentLogin1.jpg", "studentLogin1.jpg", "studentLogin1.jpg", "studentLogin1.jpg", "studentLogin1.jpg", "studentLogin1.jpg"];
    var image = $('#wrapper');
    setInterval(function(){			
		
		image.css('background-image', 'url(images/studentLogin/' + images [i++] +')');
		image.css('margin-top','100px');
		image.css('height','540px');
		image.css('width','960px');
		image.css('display','block');
		image.css('background-repeat','no-repeat');
		
		if(i == images.length)
			i = 0;
	}, 10000);            
});

</script>
<html:form action="/StudentLoginAction">
	<html:hidden property="method" styleId="method" value="studentLoginAction" />
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="pageType" value="1" />
	<body onload="resetFieldAndErrMsgs()" >
	<!--Start Body  -->
<!--	<div id="header" align="left">-->
<!--	<img src="images/header-logo.png" alt="Logo not available" class="img-responsive" width="250" align="right" height="100">-->
<!--    </div>-->
	<div>
		<div >
		<div id="LoginFormDiv">
		<div id="News" style="padding: 10px; height: 245px">
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
		<p align="center" style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold; color: black;">User Name:<html:text property="userName" styleId="username" size="30" maxlength="50" styleClass="text"></html:text></p>
		<p align="center" style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold; color: black;">Password:&nbsp;&nbsp;<html:password property="password" styleId="password" size="30" styleClass="password" maxlength="200"></html:password></p>
		<html:button property="" styleClass="buttonsubmit" value="Login" onclick="submitLogin()"/>
	    <html:button property="" styleClass="buttonreset" value="Reset" onclick="resetFieldAndErrMsgs()"/>
		<!-- <input type="submit" value="Login"  /> <input type="reset" value="Reset" />-->
		<br />
		</center>
		<p align="right">
		<% String path= request.getContextPath(); %>
		<a  href='<%=path %>/ForgotPasswordServlet' style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 12px;font-weight: bold; color: blue;"> Forgot Password</a>
		</p>
		<br />
		<!--End login form -->
			</div></div>
			
			
			<div id="LoginFormDiv2">
		<div id="News" style="height: 250px; width: 230px">
		<table width="246" border="0" cellspacing="0" cellpadding="3" height="180">
                  <tr><td class="navmenu1">
					<marquee behavior="scroll" direction="up"
						scrollamount="1" width="200" 
						style="padding: top 10px;"
						onmouseover="this.setAttribute('scrollamount', 0, 0);"
					onmouseout="this.setAttribute('scrollamount', 1, 0);"
					class="navmenu1">
					<c:out	value='${loginform.description}' escapeXml='false' />
                    </marquee>
					</td></tr>
        </table>
		</div>
			</div>
		</div>	
	</div>
	<!--End of body -->
	
	</body>
<script type="text/javascript">
var browserName=navigator.appName; 
if (browserName=="Microsoft Internet Explorer")
{
	 alert(" If Your using InternetExplorer Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling  or Use Mozilla FireFox");
}

/*var unameId=document.getElementById("username");
unameId.onfocus=function()
{
	if(unameId.value=="User Name")
	{
		unameId.value="";
	}
}
unameId.onblur=function()
{
	if(unameId.value.length==0 )
	{
		unameId.value="User Name";
	}
}
var passId=document.getElementById("password");
passId.onfocus=function()
{
	if(passId.value=="password")
	{
		passId.value="";
	}
}
passId.onblur=function()
{
	if(passId.value.length==0)
	{
		passId.value="password";
	}
}*/
function submitLogin()
{
	document.getElementById("method").value="studentLoginAction";
	document.loginform.submit();
}
function resetFieldAndErrMsgs()
{
	document.getElementById("username").value="";
	document.getElementById("password").value="";
	//document.getElementById("msg").value="";
}
var image = $('#wrapper');
image.css('background-image', 'url(images/studentLogin/studentLogin1.jpg)');
image.css('margin-top','100px');
image.css('height','550px');
image.css('width','960px');
image.css('display','block');
image.css('background-repeat','no-repeat');
</script>
</html:form>
</body>
