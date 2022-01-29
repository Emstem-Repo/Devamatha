<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.fee.feerecipt"/></title>
</head>
<script type="text/javascript">

function resetData() {
//	document.getElementById("billNo").value = "";
	resetErrMsgs();
}
function printPass() {	
	window.print();
}

</script>
<html:form action="/PhdStudentReminderation">
<html:hidden property="formName" value="phdStudentReminderationForm"/>
<logic:notEmpty property="messageList" name = "phdStudentReminderationForm">
<% int i=0; %>
<logic:iterate id="message" property="messageList" name= "phdStudentReminderationForm">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top">
			<table width="100%" cellspacing="1" cellpadding="2" border="0" style="height:100%"  >
			<tr>
				<td colspan="2">
					
											<table width="100%" cellspacing="1" cellpadding="2">
												
													<tr><td><c:out value="${message}" escapeXml="false"></c:out></td></tr>
												
											</table>
										
				</td>
			</tr>
			
			</table>
		</td>
	</tr>
</table>
<%  i=1; %>
<%if( i !=0){ %>
	<table class="break">
	</table>
<%} %>

</logic:iterate>
</logic:notEmpty>
</html:form>

<script type="text/javascript">printPass();</script>