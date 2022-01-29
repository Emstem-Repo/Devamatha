<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html:html>

<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function openPrintPage() {
	var url="admissionFormSubmit.do?method=forwardPrintWindow";
	window.open(url,'PrintApplication','left=20,top=20,width=800,height=1000,toolbar=1,resizable=0,scrollbars=1');		
}
function fnUnloadHandler() {  
	document.location.href = "LogoutAction.do?method=logout";	
}  

function submitFORCANCEL(method){
	document.location.href= method;
}

function submitFORWindow(method){
	var url=method;
	window.open(url,'OnlineStatus','left=20,top=20,width=800,height=1000,toolbar=1,resizable=0,scrollbars=1');
}
function printPDF(){
	document.location.href = "admissionFormSubmit.do?method=printApplnPDF";
	
}
</script>

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
</style></head>

<html:form action="/LoginAction" method="POST">
<html:hidden property="method" value="checkUserOTP"/>
<html:hidden property="pageType" value=""/>
<html:hidden property="formName" value="loginform"/>
<table width="100%" border="0">
  
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><strong class="boxheader"> OTP Generation</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      
      
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
		<tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        
         <td valign="top" class="news">
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
        <td height="45" align="center"><b>OTP: </b> <html:text property="userOTP" styleId="userOTP" styleClass="TextBox" size="15" maxlength="6" />
										
		</td>
		</tr>
        </table>
        </td>
       
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
        <td height="45" align="center"><html:submit	property="" styleClass="formbutton" value="Submit"	/>
										
		</td>
		</tr>
        </table>
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
     
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</html:html>