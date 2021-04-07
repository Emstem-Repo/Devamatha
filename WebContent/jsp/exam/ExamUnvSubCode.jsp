
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>
<title>:: CMS ::</title>
<script type="text/javascript" language="javascript">
	// Functions for AJAX 
	function getCourses(academicYear) {

		getCoursesByAcademicYear("coursesMap", academicYear, "course",
				updateCourses);
	}
	function updateCourses(req) {
		updateOptionsFromMap(req, "course", "- Select -");
	}
	function getScheme(courseId) {
		getSchemeByCourseId("schemeMap", courseId, "scheme", updateScheme);
	}
	function updateScheme(req) {
		updateOptionsFromMap(req, "scheme", "- Select -");
	}
	function getNames() {
		document.getElementById("academicYear").value = document
				.getElementById("year").options[document.getElementById("year").selectedIndex].text;
		document.getElementById("courseName").value = document
				.getElementById("course").options[document
				.getElementById("course").selectedIndex].text;
		document.getElementById("schemeName").value = document
				.getElementById("scheme").options[document
				.getElementById("scheme").selectedIndex].text;
	}

	function resetMessages() {

		document.location.href = "ExamUnvSubCode.do?method=initExamUnvSubCode";
		//	document.getElementById("scheme").selectedIndex = 0;
		//document.getElementById("course").selectedIndex = 0;
		resetErrMsgs();
	}
</script>
</head>
<html:form action="ExamUnvSubCode.do" method="POST">

	<html:hidden property="method" styleId="method"
		value="setExamUnvSubCode" />
	<html:hidden property="formName" value="ExamUNVSubCodeForm" />
	<html:hidden property="pageType" value="1" />


	<html:hidden property="pageType" value="1" />
	<html:hidden property="academicYear_value" styleId="academicYear" />
	<html:hidden property="courseName" styleId="courseName" />
	<html:hidden property="schemeName" styleId="schemeName" />


	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.UnvSubCode" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">

			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.UnvSubCode" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">

					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							<div align="right" style="color: red"><span
								class='MandatoryMark'>* Mandatory fields</span></div>
							</td>
						</tr>

						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd" width="25%">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" />:</div>
									</td>
									<td class="row-even" width="25%"><html:select
										property="academicYear" styleId="year" styleClass="combo"
										onclick="getCourses(this.value)">
										<html:option value=" ">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>

									<td width="25%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="25%" class="row-even"><span class="row-even">
									<html:select name="ExamUNVSubCodeForm" property="course"
										styleId="course" styleClass="combo"
										onchange="getScheme(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${UNVSubCodeOperation == 'edit'}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${checkListForm.program != null && checkListForm.program != ''}">
													<c:set var="courseMap"
														value="${baseActionForm.collectionMap['coursesMap']}" />
													<c:if test="${courseMap != null}">
														<html:optionsCollection name="courseMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
										<c:if test="${values=='yes'}">
											<logic:notEmpty name="ExamUNVSubCodeForm"
												property="courseMap">
												<html:optionsCollection property="courseMap"
													name="ExamUNVSubCodeForm" label="value" value="key" />
											</logic:notEmpty>
										</c:if>
										
									</html:select> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd" align="right" width="25%">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamSpecializationSubjectgroup.SchemeNo" /></div>
									</td>
									<td width="25%" class="row-even" colspan="3"><html:select
										name="ExamUNVSubCodeForm" property="scheme" styleId="scheme"
										styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${UNVSubCodeOperation == 'edit'}">
												<html:optionsCollection name="schemeMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${ExamUNVSubCodeForm.programTypeId != null && ExamUNVSubCodeForm.programTypeId != ''}">
													<c:set var="schemeMap"
														value="${baseActionForm.collectionMap['schemeMap']}" />
													<c:if test="${schemeMap != null}">
														<html:optionsCollection name="schemeMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
										<c:if test="${values=='yes'}">
											<logic:notEmpty name="ExamUNVSubCodeForm"
												property="schemeNameList">
												<html:optionsCollection property="schemeNameList"
													name="ExamUNVSubCodeForm" label="value" value="key" />
											</logic:notEmpty>
										</c:if>
									</html:select></td>
								</tr>
							</table>
							</td>
							<td width="5" height="54" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>


					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>


				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit property="submit"
								onclick="getNames()" styleId="submitButton"
								styleClass="formbutton" value="Search" /></div>
							</td>
							<td width="2%"></td>
							<td width="40%"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetMessages()">
								<bean:message key="knowledgepro.cancel" />
							</html:button></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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

