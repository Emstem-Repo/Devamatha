<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function resetAll() {
		document.location.href = "ExamStudentSpecialization.do?method=initExamStudentSpl";

	}
	function getScheme(courseId) {
		getSchemeNameByCourseId("schemeMap", courseId, "scheme", updateScheme);
	}

	function getSectionByScheme(schemeId) {

		var courseId = document.getElementById("course").value;
		var academicYear=document.getElementById("academicYear").value;
		getSectionByCourseIdSchemeId("sectionMap", courseId, schemeId,academicYear,
				"section", updateSection);
	}
	function updateSection(req) {
		updateOptionsFromMap(req, "section", "--Select--");
	}

	function getMethodValue(method) {
		document.getElementById("method").value = method;
	}
	function getAllScheme(courseId) {

		var academicYear = document.getElementById("academicYear").value;
		getSchemeNo_SchemeIDByCourseIdAcademicId("schemeMap", courseId,
				academicYear, "scheme", updateScheme);
	}
	function updateScheme(req) {
		updateOptionsFromMap(req, "scheme", "--Select--");
	}
</script>


<html:form action="/assignSecondLanguage">

	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="assignSecondLanguageForm" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.assignSecondLanguage" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="98%" border="0" cellpadding="0" cellspacing="0">

				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.admission.assignSecondLanguage" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" class="news">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>

							</html:messages></FONT></div>

							<div align="right" style="color: red"><span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							</td>
						</tr>

						<tr bgcolor="#FFFFFF">
							<td height="20">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">

								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<!-- Academic year -->
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>

											<td class="row-odd" width="22%">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.petticash.academicYear" />:</div>
											</td>

											<td class="row-even"><input type="hidden"
												id="yr" name="yr"
												value='<bean:write name="assignSecondLanguageForm" property="academicYear"/>' />
											<html:select property="year" styleClass="combo"
												styleId="academicYear">
												<cms:renderAcademicYear></cms:renderAcademicYear>
											</html:select></td>

											<!-- Course -->
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.interview.Course" /></div>
											</td>
											<td height="25" class="row-even"><span
												class="row-even"> 
												<html:select property="courseId" styleClass="combo" styleId="course"
												onchange="getAllScheme(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.select" />
												</html:option>
												<logic:notEmpty name="assignSecondLanguageForm"
													property="courseList">
													<html:optionsCollection property="courseList" name="assignSecondLanguageForm" label="name"
														value="id" />
												</logic:notEmpty>
											</html:select></span></td>
										</tr>
										<!--  Scheme -->
										<tr>
											<td class="row-odd" align="right">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.admission.scheme.col" /></div>
											</td>
											<td class="row-even"><html:select
												property="schemeNo" styleClass="combo" styleId="scheme"
												onchange="getSectionByScheme(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.select" />
												</html:option>
												<c:if
													test="${assignSecondLanguageForm.courseId != null && assignSecondLanguageForm.courseId != ''}">
													<c:set var="schemeMap"
														value="${baseActionForm.collectionMap['schemeMap']}" />
													<c:if test="${schemeMap != null}">
														<html:optionsCollection name="schemeMap" label="value"
															value="key" />
													</c:if>
												</c:if>
												<c:if test="${reatain=='yes'}">
													<logic:notEmpty name="assignSecondLanguageForm"
														property="schemeNameList">
														<html:optionsCollection property="schemeNameList"
															name="assignSecondLanguageForm" label="value"
															value="key" />
													</logic:notEmpty>
												</c:if>

											</html:select></td>


											<!-- section -->
											<td width="22%" class="row-odd">
											<div align="right">&nbsp;<bean:message
												key="admissionForm.studentinfo.castcatg.label" /></div>
											</td>
											<td width="34%" class="row-even"><html:select
												property="section" styleClass="combo" styleId="section">
												<html:option value="0">
													<bean:message key="knowledgepro.select" />
												</html:option>
												<c:set var="sectionMap"
													value="${baseActionForm.collectionMap['sectionMap']}" />
												<c:if test="${sectionMap != null}">
													<html:optionsCollection name="sectionMap" label="value"
														value="key" />
												</c:if>
												<c:if test="${reatain=='yes'}">
													<logic:notEmpty name="assignSecondLanguageForm"
														property="sectionList">
														<html:optionsCollection property="sectionList"
															name="assignSecondLanguageForm" label="display"
															value="id" />
													</logic:notEmpty>
												</c:if>


											</html:select></td>

										</tr>
										<!-- search specialization -->
										<tr>
											<td height="25" width="29%" class="row-odd">
											<div align="right">&nbsp;<bean:message
												key="knowledgepro.admission.subjectsGroupEntry.searchSecondLanguage" />:</div>
											</td>
											<td width="27%" height="25" class="row-even" colspan="3">
											<html:select property="secondLanguageId" styleClass="combo"
												styleId="schemeId">
												<html:option value=""><bean:message key="knowledgepro.select" /></html:option>

												<logic:notEmpty name="assignSecondLanguageForm"
													property="secondLanguageList">
													<html:optionsCollection property="secondLanguageList"
														name="assignSecondLanguageForm" label="value"
														value="key" />
												</logic:notEmpty>

											</html:select></td>
										</tr>
									</table>
									</td>

									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="25" class=" heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="49%" height="35">
									<div align="right"><html:submit styleClass="formbutton"
										value="Search" onclick="getMethodValue('searchAssignSecondLanguage')" /></div>
									</td>
									<td width="1%"></td>
									<td width="7%" height="35"><html:submit
										styleClass="formbutton" value="Assign"
										onclick="getMethodValue('Assign')" /></td>
									<td width="2%"></td>
									<td width="41%"><html:reset styleClass="formbutton"
										value="Reset" onclick="resetAll()" /></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" height="10  border="
						0" cellpadding="0
						cellspacing="0">

						<tr>
							<td></td>
						</tr>
					</table>
					</div>
					</td>
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
	<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
</script>
</html:form>