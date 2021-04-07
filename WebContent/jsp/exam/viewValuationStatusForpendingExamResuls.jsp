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
<title><bean:message key="knowledgepro.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

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
</style>
<script type="text/javascript">
	function closeMe() {
		window.close();
	}

	function backToMe() {
		document.getElementById("method").value = "displayValuationStatus";
		document.pendingExamResultsForm.submit();
	}
</script>
</head>

<body>
<html:form action="/PendingExamResults" method="POST">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="pendingExamResultsForm" styleId="formName" />
	<html:hidden property="pageType" value="5" />

	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs">&gt;&gt;Exams
			&gt;&gt;Student Valuation</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Valuation Status</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr height="25">
									<td class="row-odd" align="center" colspan="4">Subject Name: &nbsp;&nbsp;&nbsp;
										
										 &nbsp;&nbsp;&nbsp; <bean:write name="pendingExamResultsForm" property="subjectName"/>
									</td>
								</tr>
								<tr height="25">
									<td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
									<td class="row-odd" align="center">Class
										</td>
										<td class="row-odd" align="center">Register Number
									</td>
									<td class="row-odd" align="center">Student Name
									</td>
								</tr>
									<logic:notEmpty name="pendingExamResultsForm" property="students">
										<nested:iterate name="pendingExamResultsForm" property="students" id="tos" indexId="count">
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even" height="25">
												</c:when>
												<c:otherwise>
													<tr class="row-even" height="25">
												</c:otherwise>
											</c:choose>
											<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
											<td align="center"><nested:write property="className" name="tos"></nested:write></td>
											<td align="center"><nested:write property="registerNo" name="tos"/></td>
											<td align="center"><nested:write property="studentName" name="tos"/></td>
										</nested:iterate>
									</logic:notEmpty>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>


				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="33%" height="35" align="right">&nbsp;</td>
							<td width="9%" height="35" align="center"><input
								name="Submit2" type="button" class="formbutton" value="Back"
								onclick="backToMe()" />&nbsp;</td>
							<td width="58%" height="35" align="left"><input
								name="Submit2" type="button" class="formbutton" value="Close"
								onclick="closeMe()" /></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>

</html:html>