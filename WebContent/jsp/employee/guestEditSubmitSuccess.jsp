<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<html:html>

<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
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
</style></head>
<script type="text/javascript">
	function cancel() {
		document.location.href ="GuestFaculty.do?method=initEmpSearch";
	}
</script>
<body>
<html:form action="/GuestFaculty" method="POST">
	<html:hidden property="method"  value="" />
	<html:hidden property="formName" value="GuestFacultyInfoForm" />
<table width="100%" border="0">
  <tr>
   
	<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" /><span class="Bredcrumbs">&gt;&gt;
			Guest Information &gt;&gt;</span></span></td>  
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="guest.submit.success" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
     
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news"><span class="heading"></span></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
		<tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">
			<bean:message key="guest.submit.success" /><br>
			
		</td>
		
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
       
      </tr>
       <tr> 
       <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
     <td width="100%" colspan="3" align="center"><html:button property="" styleClass="formbutton" value="Close" onclick="cancel()"></html:button></td>
       <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr> 
     <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
</body>

</html:html>