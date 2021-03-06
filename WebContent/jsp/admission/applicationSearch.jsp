<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="javaScript" type="text/javascript">
	function searchIssuedandReceivedApplications() {
		document.getElementById("method").value = "searchIssuedandReceivedApplications";
		document.applicationReportForm.submit();
	}
</script>
<html:form action="/ApplicationReport" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="applicationReportForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message key="knowledgepro.reports"/> <span
				class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.admission.applicationreport" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="99%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.admission.applicationreport" /></strong></div>
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
							<td width="25%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.admin.course.with.col" /></div>
							</td>
							<td width="25%" class="row-even"><input type="hidden"
								name="cId" id="cId"
								value='<bean:write name="applicationReportForm" property="courseId"/>' />
							<html:select property="courseId" styleClass="comboLarge"
								styleId="course">
								<html:option value=""><bean:message
								key="knowledgepro.admin.select" /></html:option>
								<logic:notEmpty name="applicationReportForm" property="courseList">
								<html:optionsCollection name="applicationReportForm"
									property="courseList" label="name" value="id" />
								</logic:notEmpty>
							</html:select></td>
							<td width="25%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.fee.academicyear.col" /></div>
							</td>
							<td width="25%" class="row-even"><span class="star"> <input
								type="hidden" id="yr" name="yr"
								value='<bean:write name="applicationReportForm" property="year"/>' />
							<html:select property="year" styleClass="combo" styleId="year">
								<html:option value="">
								<bean:message key="knowledgepro.admin.select" />
								</html:option>
							<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
							</html:select> </span></td>
						</tr>
						<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="48%" height="35">
									<div align="right"><html:button property=""
										styleClass="formbutton" value="Search"
										onclick="searchIssuedandReceivedApplications()"></html:button>
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

<script type="text/javascript">
	var courseId = document.getElementById("cId").value;
	if (courseId.length != 0) {
		document.getElementById("courseId").value = courseId;
	}
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
</script>