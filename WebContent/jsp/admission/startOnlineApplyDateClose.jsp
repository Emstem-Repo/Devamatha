<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script type='text/javascript' src="jquery/Zebra/javascript/zebra_dialog.js"></script>
<link rel="stylesheet" href="jquery/Zebra/css/default/zebra_dialog.css"  />
 <script src="http://code.jquery.com/jquery-latest.js"></script>
<link rel="stylesheet" href="css/calendar.css">


<link href="css/styles.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">
var jq=$.noConflict();

function noCopyMouse(e) {
    var isRight = (e.button) ? (e.button == 2) : (e.which == 3);
    if(isRight) {
    	alert('Please write the re confimation mail');
		document.getElementById("confirmEmailId").value="";
        return false;
    }
    return true;
}

function noCopyKey(e) {
    var isCtrl;
isCtrl = e.ctrlKey
if(isCtrl) {
		document.getElementById("confirmEmailId").value="";
    		return false;
    		
      	}
	
	if(e.keyCode == 17){
		document.getElementById("confirmEmailId").value="";
	return false;
	}

	//noCopyMouse(e);
    	return true;
}

jq(document).ready(function(){
	
	if(document.getElementById("paymentSuccess").value==true || document.getElementById("paymentSuccess").value=="true"){
		 jq(".mainDisplay :input").attr( 'disabled', 'disabled'); 
		 jq("#mainButton").hide();
	}
	else{
		jq("#mainButton").show();
		jq(".mainDisplay :input").removeAttr('disabled');
	}
	});
function resetData(){
	resetFieldAndErrMsgs();
}

</script>
<script type="text/javascript">
function submitAddMorePreferences(method,mode){
	
	document.getElementById("method").value=method;
	document.getElementById("mode").value=mode;
	document.admissionFormForm.submit();
}
var blinkLength = 3000;
function blink1(){
	document.getElementById('div3').style.display = "block";
	t1=setTimeout("blink2()",blinkLength);
}
function blink2(){
	document.getElementById('div3').style.display = "none";
	t2=setTimeout("blink1()",500);
}
function validateEditCourse(){
	
	 var programTypeId=document.getElementById("programTypeId").value;
	 var courseId=document.getElementById("coursePref1").value;
	alert(courseId);
	document.location.href = "admissionFormSubmit.do?method=getPreferences&programTypeId="+programTypeId+"&courseId="+courseId;
	
	
}
function getCoursesByProgramType(ProgramTypeId) {
	//alert('000000000000000000');
	getCoursesByProgramTypes("courseMap", ProgramTypeId, "coursePref1",
			updateCourses1);
		
}
function updateCourses1(req) {
	updateOptionsFromMap(req,"coursePref1","- Select -");
}
function getCoursesByProgramType1(ProgramTypeId) {
	//alert('000000000000000000');
	getCoursesByProgramTypes("courseMap", ProgramTypeId, "coursePreference1",
			updateCourses11);
		
}
function updateCourses11(req) {
	updateOptionsFromMap(req,"coursePreference1","- Select -");
}

</script>

</head>

<body onload="blink1()">
<html:form action="/admissionFormSubmit">
<html:hidden property="method" value="" styleId="method"/>
<html:hidden property="pageType" value="26"/>
<html:hidden property="formName" value="admissionFormForm"/>
<html:hidden property="onlineApply" value="true"/>
<html:hidden property="paymentSuccess" styleId="paymentSuccess"/>
<html:hidden property="nativeCountry" styleId="nativeCountry" name="admissionFormForm"/>
<html:hidden property="serverDownMessage" styleId="serverDownMessage" name="admissionFormForm" />
<%
	String submitjsmethod=null;
%>
<logic:notEmpty name="transactionstatus" scope="request">
	<%
		submitjsmethod="#";
	%>
</logic:notEmpty>
<logic:empty name="transactionstatus" scope="request">
	<%
		submitjsmethod="submitAdmissionForm('submitOnlineApply')";
	%>
</logic:empty>
	<table align="center" width="80%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.applicationform.online.label"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table align="center" width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="954" background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.applicationform.online.label"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		
		
        

      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center"> <span class='MandatoryMark'><font size="3"> ONLINE Registration is Closed. </font></span></div>
		<div id="errorMessage">
      						<html:errors/><FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					</div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
                    
				
      
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
      <tr height="10">
      <td colspan="3"></td>
      </tr>
    
  
	

    </table></td>
  </tr>
</table>
</html:form>
</html:html>
<script type="text/javascript">

var programTypeId = document.getElementById("programType").value;
if(programTypeId != null && programTypeId.length != 0) {
	document.getElementById("programTypeId").value = programTypeId;
}

var browserName=navigator.appName; 
 if (browserName=="Microsoft Internet Explorer")
 {
	 document.getElementById("message").innerHTML="<b style='color:red'>Note:</b> Use Mozilla Firefox for better Performance and view  or Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling.";
	 alert(" If Your using InternetExplorer Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling  or Use Mozilla FireFox");
 }

 function getMobileNo(){
		var residentId = document.getElementById("residentCategory").value;
		var nativeCountry = document.getElementById("nativeCountry").value;
		var  nativeCountrys = nativeCountry.split(",");
		var indian=false;
		for ( var i = 0; i < nativeCountrys.length; i++) {
			if(nativeCountrys[i]==residentId){
				indian = true;
			}
		}
		if(indian){
			document.getElementById("mobileNo1").value="91";
		}else{
			document.getElementById("mobileNo1").value="";
		}			
}
 var serverDownMessage=$('#serverDownMessage').val();
 if(serverDownMessage!=null && serverDownMessage!=""){
 	 $(".ZebraDialog_Title a.ZebraDialog_Close").hide();
 	$.Zebra_Dialog('<strong>'+serverDownMessage+'</strong>',{
 		'title':    'Alert',
 		'buttons':  ['Hide'],
 		'keyboard':false,
 		'overlay_close':false,
 		'show_close_button':false
 	});
 }
</SCRIPT>