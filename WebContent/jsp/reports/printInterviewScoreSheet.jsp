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
<html:form action="/interviewScoreSheet">
<html:hidden property="formName" value="interviewScoreSheetForm"/>
<html:hidden property="pageType" value="3"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top">
			<table width="100%" cellspacing="1" cellpadding="2" border="0" style="height:100%"  >
			<tr>
				<td colspan="2">
					<logic:notEmpty property="messageList" name = "interviewScoreSheetForm">
											<table width="100%" cellspacing="1" cellpadding="2">
												<logic:iterate id="message" property="messageList" name= "interviewScoreSheetForm">
													<tr><td><c:out value="${message}" escapeXml="false"></c:out></td></tr>
												</logic:iterate>
											</table>
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