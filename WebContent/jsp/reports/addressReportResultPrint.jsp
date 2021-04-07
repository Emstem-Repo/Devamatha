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
<!--<style>
checkprint
{
table {page-break-after:always}
}
</style>
--></head>
<body>
<html:form action="/addressReport" method="post">


		
<logic:notEmpty name ="addressReportForm" property="studentAddressList">
<logic:iterate id="student" name="addressReportForm" property="studentAddressList" indexId="count">
		<c:choose>
		<c:when test="${count == 0}">
			<table width="100%" cellspacing="1" cellpadding="2" border="0">
			<tr>
	      		<td align="center" class="heading"></td>
	         </tr>
			  
				<tr>
				<td>&nbsp;</td>
			</table>
			<table width="100%" cellspacing="0" cellpadding="0" border="0" >
			<tr >
				<td align="left" width="20"></td>
				 <td width="10"></td>
				<td align="left" width="10"></td>
				<td width="10"></td>
				<td align="left" width="20"></td>
				
			</tr>
			<tr>
			  <td height="20"></td>
			  <td></td>
			  <td></td>
			</tr>
			<tr valign="top">
			<td align="left"><font size="2"><bean:write name="student" property="totalContent1"/></font></td>
			<td></td>
			<td align="left"><font size="2"><bean:write name="student" property="totalContent2"/></font></td>
			<td></td>
			<td align="left"><font size="2"><bean:write name="student" property="totalContent3"/></font></td>
			
			</tr>
			<tr>
			  <td height="20"></td>
			  <td></td>
			  <td></td>
			</tr>
		</c:when>
		<c:otherwise>
		
		<c:if test="${(count) % 40 == 0 }">
			
			<table width="100%" cellspacing="0" cellpadding="0" border="0" >
			<tr>
				<td align="left" width="20"></td>
				<td width="10"></td>
				<td align="left" width="10"></td>
				<td width="10"></td>
				<td align="left" width="20"></td>
				
			</tr>
			<tr>
			  <td height="20"></td>
			  <td></td>
			  <td></td>
			</tr>
		</c:if>
			<tr valign="top">
			<td align="left"><font size="2"><bean:write name="student" property="totalContent1"/></font></td>
			<td></td>
			<td align="left"><font size="2"><bean:write name="student" property="totalContent2"/>&nbsp;</font></td>
			<td></td>
			<td align="left" ><font size="2"><bean:write name="student" property="totalContent3"/>&nbsp;</font></td>
			</tr>
			<tr>
			  <td height="20"></td>
			  <td></td>
			  <td></td>
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
</table>

</html:form>
</body>
</html>
<script type="text/javascript">printPass();</script>