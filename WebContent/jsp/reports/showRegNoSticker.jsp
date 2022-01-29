<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link href="css/styles.css" rel="stylesheet" type="text/css">

<script type="text/javascript">

function resetData() {
//	document.getElementById("billNo").value = "";
	resetErrMsgs();
}
function printPass() {	
	window.print();
}

</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>

<html:form action="/RegNoRollNoStick">
<html:hidden property="method" styleId="method" value="printPassword"/>
<html:hidden property="formName" value="stickerForm"/>
<html:hidden property="pageType" value="2"/>
<logic:notEmpty property="studentList" name = "stickerForm">
	<table width="900"  border = "0" cellspacing="0" cellpadding="0" >
	<%int n = -1; %>
	<logic:iterate id="student" property="studentList" name= "stickerForm" indexId="count">
		<c:if test="${(count) % 4 == 0}">
			<c:if test="${(count) != 0}">
				</tr>
			<tr>
			<td>&nbsp;</td> <td>&nbsp;</td> <td>&nbsp;</td><td>&nbsp;</td>
			</tr>
			<tr>
			<td>&nbsp;</td> <td>&nbsp;</td> <td>&nbsp;</td><td>&nbsp;</td>
			</tr>
			<tr>
			<td>&nbsp;</td> <td>&nbsp;</td> <td>&nbsp;</td> <td>&nbsp;</td>
			</tr>
			<tr>
			<td>&nbsp;</td> <td>&nbsp;</td> <td>&nbsp;</td><td>&nbsp;</td>
			</tr>
			</c:if>
			<tr>
		</c:if>
		<td width="25%" align="center" valign="middle"><font size="5"><b><bean:write name="student" property="registerNo"/></b></font></td>
		<% n = count; %>
	</logic:iterate>

		<%if(n < 3){
			for(int i = n; i<3; i++){
		%>
		<td width="25%" align="center" valign="middle"><font size="5"><b>&nbsp;</b></font></td>		
		<%}} %>
	</tr>
	</table>
</logic:notEmpty>

</html:form>


</body>
</html>
<script type="text/javascript">printPass();</script>