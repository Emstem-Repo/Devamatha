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
<style>
checkprint
{
table {page-break-after:always}
}
</style>
</head>
<body>
<html:form action="/StudListForSign">
<html:hidden property="method" styleId="method" value="printPassword"/>
<html:hidden property="formName" value="studentListForSignatureForm"/>
<html:hidden property="pageType" value="3"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top">
			<table width="100%" cellspacing="1" cellpadding="2" border="0"  >
			<tr>
				<td colspan="2">
					<logic:notEmpty name = "studentList">
						<logic:iterate id="student" name= "studentList" indexId="count">
							<c:choose>
							<c:when test="${count == 0}">
								<table width="100%" cellspacing="1" cellpadding="2" border="0">
								<tr>
						      		<td align="center" class="heading"><bean:write name="studentListForSignatureForm" property="organizationName"/></td>
						         </tr>
								  <tr>
						      	    <td align="center"> <span class="heading">List of <bean:write property="className" name="studentListForSignatureForm"/> Students
						          </tr>
									<tr>
									<td>&nbsp;</td>
								</table>
								<table width="100%" cellspacing="0" cellpadding="0" border="1" >
								<tr>
									<td align="center" width = "10%">Sl.No</font></td>
									<td align="center" width = "20%">Roll No/Reg No</font></td>
									<td align="center" width = "40%">Name</td>
									<td align="center" width = "30%"><bean:write name="studentListForSignatureForm" property="columnHeading"/></td>
								</tr>
								<tr>
								<td align="center" width = "10%"><font size="1.5"><bean:write name="student" property="slNo"/></font></td>
								<td align="center" width = "20%"><font size="1.5"><bean:write name="student" property="rollOrRegNo"/>&nbsp;</font></td>
								<td align="center" width = "50%"><font size="1.5"><bean:write name="student" property="studentName"/>&nbsp;</font></td>
								<td align="center" width = "30%">&nbsp;</td>
								</tr>
							</c:when>
							<c:otherwise>
							<c:if test="${(count) % 40 == 0 }">
								<table class="break">
								<br>
								</table>
								<table width="100%" cellspacing="0" cellpadding="0" border="1" >
								<tr>
									<td align="center" width = "10%">Sl.No</font></td>
									<td align="center" width = "20%">Roll No/Reg No</font></td>
									<td align="center" width = "40%">Name</td>
									<td align="center" width = "30%"><bean:write name="studentListForSignatureForm" property="columnHeading"/></td>
								</tr>
							</c:if>
								<tr>
								<td align="center" width = "10%"><font size="1.5"><bean:write name="student" property="slNo"/></font></td>
								<td align="center" width = "20%"><font size="1.5"><bean:write name="student" property="rollOrRegNo"/>&nbsp;</font></td>
								<td align="center" width = "50%"><font size="1.5"><bean:write name="student" property="studentName"/>&nbsp;</font></td>
								<td align="center" width = "30%">&nbsp;</td>
								</tr>
							</c:otherwise>
							</c:choose>
						</logic:iterate>
					</logic:notEmpty>
				</td>
			</tr>
			
			</table>
		</td>
	</tr>
</table></html:form>


</body>
</html>
<script type="text/javascript">printPass();</script>