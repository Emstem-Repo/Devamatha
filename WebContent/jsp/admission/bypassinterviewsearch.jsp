<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript">
	function getPrograms(programTypeId) {
		getProgramsByType("programMap", programTypeId, "program",
				updatePrograms);
		resetOption("course");
	}

	function updatePrograms(req) {
		updateOptionsFromMap(req, "program", "- Select -");
		document.getElementById("programTypeName").value = document
				.getElementById("programType").options[document
				.getElementById("programType").selectedIndex].text
	}

	function getCourses(programId) {
		getCoursesByProgram("coursesMap", programId, "course", updateCourses);
	}

	function updateCourses(req) {
		updateOptionsFromMap(req, "course", "- Select -");
		document.getElementById("programName").value = document
				.getElementById("program").options[document
				.getElementById("program").selectedIndex].text

	}

	function resetErrorMsgs() {
		document.location.href = "BypassInterview.do?method=initBypassInterviewSearch";
	}

	function getinterviewType() {
		var year = document.getElementById("year").options[document
				.getElementById("year").selectedIndex].value;
		var courseId = document.getElementById("course").options[document
				.getElementById("course").selectedIndex].value;

		if (courseId.length > 0) {
			getInterviewTypeByCourse("interviewMap", courseId, year,
					"interviewType", updateInterviewType);
			document.getElementById("courseName").value = document
					.getElementById("course").options[document
					.getElementById("course").selectedIndex].text
		}

	}
	function updateInterviewType(req) {
		updateOptionsFromMap(req, "interviewType", "- Select -");
	}
	function selectPreviousInterviewType(interviewTypeId) {
		if (interviewTypeId != '')
			document.getElementById("previousInterViewType").value = document
					.getElementById("interviewType").options[document
					.getElementById("interviewType").selectedIndex - 1].value

	}

	function isValidNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
</script>
<html:form action="/BypassInterview" focus="programType">

	<html:hidden property="method" styleId="method"
		value="getBypassInterviewSearchResults" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="formName" value="studentSearchForm" />

	<html:hidden property="programTypeName" styleId="programTypeName"
		value="" />
	<html:hidden property="programName" styleId="programName" value="" />
	<html:hidden property="courseName" styleId="courseName" />
	<html:hidden property="previousInterViewType"
		styleId="previousInterViewType" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.bypassinterview" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.admission.bypassinterview" /> </strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">

					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<div align="right"><span class='MandatoryMark'><bean:message
							key="knowledgepro.mandatoryfields" /></span></div>
						<tr>
							<td height="20" colspan="6" class="body" align="left">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>

						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">

							<table width="100%" cellspacing="1" cellpadding="2">

								<tr>
									<td width="10%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>
									<td width="20%" height="25" class="row-even"><html:select
										property="programTypeId" styleClass="combo"
										styleId="programType" onchange="getPrograms(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="programTypeList"
											label="programTypeName" value="programTypeId" />
									</html:select></td>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>

									<td width="20%" class="row-even"><html:select
										property="programId" styleId="program" styleClass="combo"
										onchange="getCourses(this.value) ">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${studentSearchForm.programTypeId != null && studentSearchForm.programTypeId != ''}">
											<c:set var="programMap"
												value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select></td>

									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="30%" class="row-even"><html:select
										property="courseId" styleClass="combo" styleId="course"
										onchange="getinterviewType()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>

										<c:if
											test="${studentSearchForm.programId != null && studentSearchForm.programId != ''}">
											<c:set var="coursesMap"
												value="${baseActionForm.collectionMap['coursesMap']}" />
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select></td>

								</tr>
								<tr>

									<td width="10%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.year" />:</div>
									</td>
									<td width="20%" height="25" class="row-even"><input type="hidden"
										id="tempyear" name="appliedYear"
										value="<bean:write name="studentSearchForm" property="year"/>" />
									<html:select property="year" styleClass="combo" styleId="year"
										onchange="getinterviewType()">
										<html:option value=" ">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select>
									</td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.name" />:</div>
									</td>
									<td width="20%" class="row-even"><html:text property="applicantName"
										styleId="applicantName"></html:text></td>

									<td width="10%" class="row-odd" align="right">Interview Date</td>
									<td width="30%" class="row-even">
									<html:text name="studentSearchForm" property="interviewDate" styleId="interviewDate" size="10" maxlength="16"/>
										<script
										language="JavaScript">
										new tcal( {
											// form name
											'formname' :'studentSearchForm',
											// input name
											'controlname' :'interviewDate'
										});
									</script>
									
									</td>

								</tr>

								<tr>

									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.religion" />:</div>
									</td>
									<td width="20%" class="row-even"><html:text property="religionId"
										styleId="religion"></html:text></td>

									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.subreligion.label" /></div>
									</td>

									<td width="20%" class="row-even"><html:text property="subReligionId"
										styleId="subReligion"></html:text></td>



									<td width="10%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.casteentry" />:</div>
									</td>
									<td width="30%" height="25" class="row-even"><html:text
										property="casteCategoryId" styleId="castCategoty"></html:text>
									</td>

								</tr>

								<tr>

									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.belongsto" />:</div>
									</td>
									<td width="20%" class="row-even"><html:select
										property="belongsTo" styleClass="combo" styleId="belongsTo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:option value="R">
											<bean:message key="knowledgepro.admission.rural" />
										</html:option>
										<html:option value="U">
											<bean:message key="knowledgepro.admission.urban" />
										</html:option>
									</html:select></td>

									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.gender" />:</div>
									</td>
									<td width="20%" class="row-even"><html:select
										property="gender" styleClass="combo" styleId="gender">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:option value="male">
											<bean:message key="knowledgepro.admission.male" />
										</html:option>
										<html:option value="female">
											<bean:message key="knowledgepro.admission.female" />
										</html:option>
									</html:select></td>

									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.bloodgroup" />:</div>
									</td>
									<td width="30%" class="row-even"><html:select
										property="bloodGroup" styleClass="combo" styleId="bloodGroup">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:option value="O+ve">
											<bean:message key="knowledgepro.admission.report.opositive" />
										</html:option>
										<html:option value="A+ve">
											<bean:message key="knowledgepro.admission.report.apositive" />
										</html:option>
										<html:option value="B+ve">
											<bean:message key="knowledgepro.admission.report.bpositive" />
										</html:option>
										<html:option value="AB+ve">
											<bean:message key="knowledgepro.admission.report.abpositive" />
										</html:option>
										<html:option value="O-ve">
											<bean:message key="knowledgepro.admission.report.onegitive" />
										</html:option>
										<html:option value="A-ve">
											<bean:message key="knowledgepro.admission.report.anegitive" />
										</html:option>
										<html:option value="B-ve">
											<bean:message key="knowledgepro.admission.report.bnegitive" />
										</html:option>
										<html:option value="AB-ve">
											<bean:message key="knowledgepro.admission.report.abnegitive" />
										</html:option>
									</html:select></td>
								</tr>

								<tr>


									<td width="10%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.nationality.label" /></div>
									</td>

									<td width="20%" height="25" class="row-even"><html:text
										property="nationalityId" styleId="nationality"></html:text></td>

									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.residentcatg.label" /></div>
									</td>

									<td width="20%" class="row-even"><html:text
										property="residentCategoryId" styleId="residentCategory"></html:text>
									</td>


									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.weightage" />:</div>
									</td>
									<td width="30%" class="row-even"><html:text property="weightage"
										maxlength="6" styleId="weightage"
										onkeypress="return isDecimalNumberKey(this.value,event)"
										onblur="isValidNumber(this)"></html:text></td>

								</tr>

								<tr>
									<td width="10%" class="row-odd">

									<div align="right"><bean:message
										key="knowledgepro.admin.university" />:</div>
									</td>
									<td width="20%" class="row-even"><html:text property="university"
										styleId="university"></html:text></td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.institute" />:</div>
									</td>
									<td width="20%" class="row-even"><html:text property="institute"
										styleId="institute"></html:text></td>


									<td width="10%" class="row-odd">

									<div align="right"><bean:message
										key="knowledgepro.admission.countryofbirth" />:</div>
									</td>
									<td width="30%" class="row-even"><html:text property="birthCountry"
										styleId="birthCountry"></html:text></td>
								</tr>

								<tr>

									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.stateofbirth" />:</div>
									</td>
									<td width="20%" class="row-even"><html:text property="birthState"
										styleId="birthState"></html:text></td>

									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.percentagefrom" />:</div>
									</td>
									<td width="20%" class="row-even"><html:text property="percentageFrom"
										maxlength="6" styleId="percentageFrom"
										onkeypress="return isDecimalNumberKey(this.value,event)"
										onblur="isValidNumber(this)"></html:text></td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.percentageto" />:</div>
									</td>
									<td width="30%" class="row-even"><html:text property="percentageTo"
										maxlength="5" styleId="percentageTo"
										onkeypress="return isDecimalNumberKey(this.value,event)"
										onblur="isValidNumber(this)"></html:text></td>

								</tr>



								<tr>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.edit.sportsperson.label" /></div>
									</td>
									<td width="20%" class="row-even" align="left"><html:radio
										styleId="isSportsPerson" property="sportsPerson" value="true">
										<bean:message key="knowledgepro.yes" />
									</html:radio> <html:radio property="sportsPerson" value="false"
										styleId="notSportsPerson">
										<bean:message key="knowledgepro.no" />
									</html:radio></td>

									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.edit.handicapped.label" /></div>
									</td>
									<td width="20%" class="row-even" align="left"><html:radio
										property="handicapped" value="true" styleId="isHandicapped">
										<bean:message key="knowledgepro.yes" />
									</html:radio> <html:radio property="handicapped" value="false"
										styleId="notHandicapped">
										<bean:message key="knowledgepro.no" />
									</html:radio></td>
									<td width="10%" class="row-odd"></td>
									<td width="30%" class="row-even"></td>
								</tr>

							</table>

							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>

					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetErrorMsgs()"></html:button></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
</script>