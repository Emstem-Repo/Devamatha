<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link href="../css/styles.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
var destID;
function resetAll() {
	document.location.href = "ExamUniversityRegisterNumberEntry.do?method=initUniversityRegisterNumberEntry";
	
	}
	
	function updateProgram(req) {
		updateOptionsFromMap(req, "programName", "--Select--");
	}
	function getProgramName(academicYear) {
		getProgramNameByAcademicYear("programMap", academicYear, "programName",
				updateProgram);
	}
	function getCourseByProgram(programId) {
		getCoursesByProgram("courseMap", programId, "courseName", updateCourse);
	}
	function updateCourse(req) {
		updateOptionsFromMap(req, "courseName", "--Select--");
	}
	function getAllScheme(courseId) {
		var academicYear = document.getElementById("academicYear").value;
		getSchemeNo_SchemeIDByCourseIdAcademicId("schemeMap", courseId, academicYear,
				"scheme", updateScheme);

		//getSchemeNoByCourseId("schemeMap", courseId, "scheme", updateScheme);
	}
	function updateScheme(req) {
		updateOptionsFromMapMultiselect(req, "scheme", "--Select--");
	}
	
	
</script>

<html:form action="/ExamUniversityRegisterNumberEntry.do">



	<html:hidden property="formName"
		value="ExamUniversityRegisterNumberEntryForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.exam.UnvRegEntry"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9" valign="top" background="images/Tright_03_03.gif"></td>
					<td background="images/Tcenter.gif" class="body">
					<p><strong class="boxheader"><bean:message key="knowledgepro.exam.UnvRegEntry"/></strong></p>
					</td>
					<td width="10"><img src="/images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="/images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.exam.studentEligibilityEntry.academicYear"/> :</div>
									</td>
									<td class="row-even" width="25%"><html:select
										property="academicYear" styleId="academicYear"
										styleClass="combo" onchange="getProgramName(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>

									<td width="21%" height="26" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Program:</div>
									</td>
									<td width="29%" class="row-even"><html:select
										property="programName" styleId="programName" styleClass="combo"
										onchange="getCourseByProgram(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>


										<c:if
											test="${ExamUniversityRegisterNumberEntryForm.mapProgram != null && ExamUniversityRegisterNumberEntryForm.mapProgram != ''}">
											
										
												<html:optionsCollection property="mapProgram" name="ExamUniversityRegisterNumberEntryForm" label="value"
													value="key" />
											
										</c:if>
										<c:if
											test="${ExamUniversityRegisterNumberEntryForm.academicYear != null && ExamUniversityRegisterNumberEntryForm.academicYear != ''}">
											<c:set var="programMap"
												value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select></td>
								</tr>
								<tr>
									<td width="27%" height="25" class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.interview.Course" /></div>
									</td>
									<td class="row-even"><html:select styleClass="combo" 
										 property="courseName" 
										style="width:150px" onchange="getAllScheme(this.value)" styleId="courseName">

										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>

											<c:if
											test="${ExamUniversityRegisterNumberEntryForm.mapCourse != null && ExamUniversityRegisterNumberEntryForm.mapCourse != ''}">
											
												<html:optionsCollection property="mapCourse"  name="ExamUniversityRegisterNumberEntryForm" label="value"
													value="key" />
											</c:if>
										



										<c:if
											test="${ExamUniversityRegisterNumberEntryForm.programId != null && ExamUniversityRegisterNumberEntryForm.programId != ''}">
											<c:set var="courseMap"
												value="${baseActionForm.collectionMap['courseMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.semister"/>:</div>
									</td>
									<td width="25%" class="row-even"><html:select
										property="scheme" styleClass="combo" styleId="scheme"
										onchange="getId(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>

										<c:if
											test="${ExamUniversityRegisterNumberEntryForm.courseId != null && ExamUniversityRegisterNumberEntryForm.courseId != ''}">
											<c:set var="schemeMap"
												value="${baseActionForm.collectionMap['schemeMap']}" />
											<c:if test="${schemeMap != null}">
												<html:optionsCollection name="schemeMap" label="value"
													value="key" />
											</c:if>
										</c:if>

									</html:select></td>

								</tr>
								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right">Specilisation :</div>
									</td>
									<td width="25%" class="row-even"><html:select
										styleId="specName" property="specName" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>

										<logic:notEmpty name="ExamUniversityRegisterNumberEntryForm"
											property="listSpecialization">
											<html:optionsCollection property="listSpecialization"
												name="ExamUniversityRegisterNumberEntryForm" label="display"
												value="id" />
										</logic:notEmpty>
									</html:select></td>



									<!--  secondLanguage-->

									<td width="21%" height="26" class="row-odd">
									<div align="right">Second Language:</div>
									</td>
									<td width="29%" class="row-even"><html:select
										property="langName" styleClass="combo" styleId="langName"
										name="ExamUniversityRegisterNumberEntryForm"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamUniversityRegisterNumberEntryForm"
											property="secondLanguage">
											<html:optionsCollection property="secondLanguage"
												name="ExamUniversityRegisterNumberEntryForm" label="display"
												value="id" />
										</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><bean:message key="knowledgepro.exam.UnvRegEntry.display"/>:</div>
									</td>
									<td class="row-even" colspan="3"><html:radio
										property="displayOrder" styleId="radio" value="rollno"
										disabled="false" /> <bean:message
										key="knowledgepro.exam.radio.rollno" /> <html:radio
										property="displayOrder" styleId="radio" value="name" /> <bean:message
										key="knowledgepro.exam.radio.name" /></td>
								</tr>
							</table>
							</td>
							<td width="5" height="30" background="/images/right.gif"></td>
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
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35" align="right"><html:submit
								property="method" styleClass="formbutton" value="Search" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="45%" align="left"><html:reset value="Reset"
								styleClass="formbutton" onclick="resetAll()"/></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
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
</tr></table>
			</html:form>