<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<head>
<script type="text/javascript">
function cancel() {
	<%--document.location.href = "internalMarksEntry.do?method=initMarksEntry";--%>
	document.location.href = "newCIAMarksEntry.do?method=initInternalMarksEntry";
}
function cancel1() {
	document.location.href = "internalMarksEntry.do?method=viewTeacherExamDetails";
}
</script>
</head>
<html:form action="/internalMarksEntry" method="POST" enctype="multipart/form-data">
	<html:hidden property="formName" value="internalMarksEntryForm"	styleId="formName" />
<table align="center">
<logic:notEqual value="HOD" property="empType" name="internalMarksEntryForm">
 <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
 <tr align="center">
<td height="35" align="center">
<html:button property="" styleClass="formbutton" value="Close" onclick="cancel()"></html:button></td>
</tr> 
<tr><td style="height: 10px" align="center" colspan="1"></td></tr>

</logic:notEqual>
<logic:equal value="HOD" property="empType" name="internalMarksEntryForm">
  <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
 <tr align="center">
<td height="35" align="center">
<html:button property="" styleClass="formbutton" value="Close" onclick="cancel1()"></html:button></td>
</tr> 
<tr><td style="height: 10px" align="center" colspan="1"></td></tr>

</logic:equal>
</table>
</html:form>
