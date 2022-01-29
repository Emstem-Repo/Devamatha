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
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
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
<script>

function cancel() {
	document.location.href = "LoginAction.do?method=loginAction";
}

function OnSubmit(){
	var applicationNo = document.getElementById("applicationNO").value;
	
	if(applicationNo!= null && applicationNo!='')
	{ 
		
		document.getElementById("method").value="loadResumeEmployeeInfo";
		document.EmployeeInfoFormNew.submit();
	}
	
	else if(applicationNo == null || applicationNo == '')
	{
		var x=window.confirm("Do u want to add new Employee Information without Application no?");
		if(x)
		{
			document.getElementById("method").value="initEmployeeInfo";
			document.EmployeeInfoFormNew.submit();
		}
	}
}
</script>

<body>
<html:form action="/EmployeeInfoDisplay" enctype="multipart/form-data" method="POST">
<html:hidden property="method" value="initEmployeeInfoAdd" styleId="method"/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="formName" value="EmployeeInfoFormNew"/>
<html:hidden property="flag" styleId="flag" name="EmployeeInfoFormNew"/>
	<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.employee"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.employee.info.label" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.employee.info.label" /></strong></td>
        <td width="5" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
	  <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
<div id="errorMessage">
      						<html:errors/><FONT color="red">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					</div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="100%" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
                  <tr class="row-white">
                    <td width="161" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.employee.applicationNo"/>:</div></td>
                    <td width="164" class="row-even"><html:text property="applicationNO" styleId="applicationNO"/></td>
                    
             </tr>
              </table></td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35"><div align="Center">
               <html:button property="" onclick="OnSubmit()" styleClass="formbutton" value="Continue" ></html:button>
                <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button></div></td>
            
          </tr>
        </table></td>
        
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</html:html>
