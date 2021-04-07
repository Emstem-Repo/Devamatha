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
<script language="JavaScript" src="js/admission/admissionBiodata.js"></script>
<script language="JavaScript" src="js/admission/studentBiodata.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function openDocList() {
	var applicationNumber=document.getElementById("applicationNumber").value;
	var applicationYear=document.getElementById("applicationYear").value;
	var url="admissionFormSubmit.do?method=forwardDoclist&applicationNumber="+applicationNumber+"&applicationYear="+applicationYear;
	window.open(url,'OriginalDocumentlist','left=20,top=20,width=500,height=500,toolbar=0,resizable=0,scrollbars=1');		
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

<body>
<html:form action="/studentBiodata" method="POST">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="17"/>
<html:hidden property="formName" value="StudentBiodataForm"/>
<html:hidden property="applicantDetails.applnNo" styleId="applicationNumber"/>
<html:hidden property="academicYear" styleId="applicationYear"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt; <bean:message key="admissionForm.studentadd.main.label"/></span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="admissionForm.studentadd.main.label"/></strong></td>
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
			<bean:message key="admissionForm.newstudentsucess.main.label"/><br>
		<bean:message key="admissionForm.admissionformsucess.confirm.label1" /><a href="#" onclick="openDocList()"><bean:message key="admissionForm.admissionform.click.label" /></a> <bean:message key="admissionForm.admissionformsucess.confirm.label2" />
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="48%" height="35"><div align="right">
                <html:button property="" onclick="cancelMe('initStudentCreation')" styleClass="formbutton" value="Cancel"></html:button>
            </div></td>
            <td width="1%"></td>
            <td width="51%"></td>
          </tr>
        </table></td>
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