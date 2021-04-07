<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script language="javaScript" type="text/javascript">
	function getStudents() {
		document.getElementById("method").value = "getCerificateDueStudents";
		document.certificateDueReportForm.submit();
	}
</script>
<html:form action="/certificateDue" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="certificateDueReportForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message key="knowledgepro.reports"/> <span
				class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.reports.certificate.due.report"/>&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="99%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.reports.certificate.due.report"/> </strong></div>
					</td>
					<td width="16"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div align="right"><span class='MandatoryMark'> <bean:message
							key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="green"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.reports.due.date.col"/> </div>
									</td>
									<td class="row-odd"><html:text property="dueDate"
										readonly="false" size="10" maxlength="10"
										styleId="duedate"></html:text> 
					<script	language="JavaScript">
						new tcal ({
						// form name
						'formname': 'certificateDueReportForm',
						// input name
						'controlname': 'duedate'
						});</script></td>
						</tr>
						<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="48%" height="35">
									<div align="right"><html:button property=""
										styleClass="formbutton" value="Search"
										onclick="getStudents()"></html:button>
									</div>
									</td>
									<td width="1%"></td>
									<td width="49%"><html:button property=""
										styleClass="formbutton" value="Reset"
										onclick="resetFieldAndErrMsgs()"></html:button></td>
								</tr>
							</table>
						</tr>
					</table>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>