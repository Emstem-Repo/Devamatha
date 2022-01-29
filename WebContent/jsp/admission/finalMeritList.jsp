<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Map,java.util.HashMap"%>
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

	function setCourseName() {
		document.getElementById("courseName").value = document
				.getElementById("course").options[document
				.getElementById("course").selectedIndex].text
	}

	function getSubReligions(religionId) {
		getSubReligionByReligion("subReligionMap", religionId, "subReligion",
				updateSubreligion);
	}

	function updateSubreligion(req) {
		updateOptionsFromMap(req, "subReligion", "- Select -");
	}

	function getInstitute(universityId) {

		getCollegeByUniversity("instituteMap", universityId, "institute",
				updateInstitute);
	}

	function updateInstitute(req) {
		updateOptionsFromMap(req, "institute", "- Select -");
		document.getElementById("univercityName").value = document
				.getElementById("university").options[document
				.getElementById("university").selectedIndex].text

	}

	function setInstituteName() {
		document.getElementById("instituteName").value = document
				.getElementById("institute").options[document
				.getElementById("institute").selectedIndex].text

	}

	function resetErrorMsgs() {
		resetErrMsgs();

		document.location.href = "FinalMeritList.do?method=initFinalMeritList";
	}

	function isValidNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
</script>
<html:form action="/FinalMeritList" focus="programType">

	<html:hidden property="method" styleId="method"
		value="getFinalMeritList" />
	<html:hidden property="programTypeName" styleId="programTypeName" />
	<html:hidden property="programName" styleId="programName" />
	<html:hidden property="courseName" styleId="courseName" />
	<html:hidden property="instituteName" styleId="instituteName" />
	<html:hidden property="univercityName" styleId="univercityName" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="finalMeritListForm" />

	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.finalmeritlist" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.admission.finalmeritlist" /> </strong></div>
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
										property="programTypeId" styleId="programType"
										styleClass="combo" onchange="getPrograms(this.value)">
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
										property="programId" styleClass="combo" styleId="program"
										onchange="getCourses(this.value) ">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${finalMeritListForm.programTypeId != null && finalMeritListForm.programTypeId != ''}">
											<c:set var="programMap"
												value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select></td>
									<td width="10%" class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="30%" class="row-even"><html:select
										property="courseId" styleClass="combo" styleId="course"
										onchange="setCourseName()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>

										<c:if
											test="${finalMeritListForm.programId != null && finalMeritListForm.programId != ''}">
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
									<div align="right"><bean:message
										key="knowledgepro.admin.casteentry" />:</div>
									</td>
									<td width="20%" height="25" class="row-even"><html:select
										property="casteCategoryId" styleClass="combo"
										styleId="castCategoty">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="casteCategoryList"
											label="casteName" value="casteId" />

									</html:select></td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.religion" />:</div>
									</td>
									<td width="20%" class="row-even"><html:select property="religionId"
										styleId="religion" styleClass="combo"
										onchange="getSubReligions(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="religionList"
											label="religionName" value="religionId" />/>
							</html:select></td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.subreligion.label" /></div>
									</td>
									<td width="30%" class="row-even"><html:select property="subReligionId"
										styleClass="combo" styleId="subReligion">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${finalMeritListForm.religionId != null && finalMeritListForm.religionId != ''}">
											<c:set var="subReligionMap"
												value="${baseActionForm.collectionMap['subReligionMap']}" />
											<c:if test="${subReligionMap != null}">
												<html:optionsCollection name="subReligionMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select></td>
								</tr>
								<tr>
									<td width="10%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.nationality.label" /></div>
									</td>
									<td width="20%" height="25" class="row-even"><html:select
										property="nationalityId" styleClass="combo"
										styleId="nationality">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="countryList" label="name"
											value="id" />/>
							</html:select></td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.residentcatg.label" /></div>
									</td>
									<td width="20%" class="row-even"><html:select
										property="residentCategoryId" styleClass="combo"
										styleId="residentCategory">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="residentCategoryList"
											label="name" value="id" />
									</html:select></td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.belongsto" />:</div>
									</td>
									<td width="30%" class="row-even"><html:radio property="belongsTo"
										value="R" styleId="belongsToRural">
										<bean:message
											key="admissionForm.studentinfo.belongsto.rural.text" />
									</html:radio> <html:radio property="belongsTo" value="U"
										styleId="belongsToUrban">
										<bean:message
											key="admissionForm.studentinfo.belongsto.urban.text" />
									</html:radio></td>
								</tr>

								<tr>
									<td width="10%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.gender" />:</div>
									</td>
									<td width="20%" height="25" class="row-even"><html:radio
										property="gender" value="male" styleId="genderMale">
										<bean:message key="admissionForm.studentinfo.sex.male.text" />
									</html:radio> <html:radio property="gender" value="female"
										styleId="genderFemale">
										<bean:message key="admissionForm.studentinfo.sex.female.text" />
									</html:radio></td>

									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.university" />:</div>
									</td>
									<td width="20%" class="row-even"><html:select property="universityId"
										styleClass="combo" styleId="university"
										onchange="getInstitute(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="univercityList" label="name"
											value="id" />
									</html:select></td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.institute" />:</div>
									</td>
									<td width="30%" class="row-even"><html:select property="instituteId"
										styleClass="combo" styleId="institute"
										onchange="setInstituteName()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${finalMeritListForm.universityId != null && finalMeritListForm.universityId != ''}">
											<c:set var="instituteMap"
												value="${baseActionForm.collectionMap['instituteMap']}" />
											<c:if test="${instituteMap != null}">
												<html:optionsCollection name="instituteMap" label="value"
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
										id="tempyear" name="tempyear"
										value="<bean:write name="finalMeritListForm" property="year"/>" />
									<html:select property="year" styleId="year" styleClass="combo">
										<html:option value=" ">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
									<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select></td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.weightagefrom" />:</div>
									</td>
									<td width="20%" class="row-even"><html:text property="weightageFrom"
										size="10" maxlength="6" styleId="weightagefrom"
										onkeypress="return isDecimalNumberKey(this.value,event)"
										onblur="isValidNumber(this)"></html:text></td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.weightageto" />:</div>
									</td>
									<td width="30%" class="row-even"><html:text property="weightageTo"
										size="10" maxlength="6" styleId="weightageto"
										onkeypress="return isDecimalNumberKey(this.value,event)"
										onblur="isValidNumber(this)"></html:text></td>

								</tr>
								<tr>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.sports" />:</div>
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
										key="knowledgepro.admission.physicallychallenged" />:</div>
									</td>
									<td width="20%" class="row-even" align="left"><html:radio
										property="handicapped" value="true" styleId="isHandicapped">
										<bean:message key="knowledgepro.yes" />
									</html:radio> <html:radio property="handicapped" value="false"
										styleId="notHandicapped">
										<bean:message key="knowledgepro.no" />
									</html:radio></td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.fee.applicationno" />:</div>
									</td>
									<td width="30%" class="row-even"><html:text property="applnNo" size="10" maxlength="9" styleId="applnNo"></html:text></td>
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
