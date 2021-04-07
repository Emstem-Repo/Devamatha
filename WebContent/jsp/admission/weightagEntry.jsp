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

	}

	function getCourses(programId) {
		getCoursesByProgram("coursesMap", programId, "course", updateCourses);

	}

	function updateCourses(req) {
		updateOptionsFromMap(req, "course", "- Select -");

	}

	function updateWeightage() {

		var programTypeId = document.getElementById("programType").options[document
				.getElementById("programType").selectedIndex].value;
		var programId = document.getElementById("program").options[document
				.getElementById("program").selectedIndex].value;
		var courseId = document.getElementById("course").options[document
				.getElementById("course").selectedIndex].value;
		var year = document.getElementById("year").options[document
				.getElementById("year").selectedIndex].value;

		document.location.href = "WeightageDefenition.do?method=updateWeightageDefenition&programTypeId="
				+ programTypeId
				+ "&programId="
				+ programId
				+ "&courseId="
				+ courseId
				+ "&year="
				+ year
				+ "&formName=weightageDefenitionForm" + "&pageType=1";

	}
</script>
<html:form action="/WeightageDefenition" focus="programType">
	<html:hidden property="method" styleId="method"
		value="initWeightageDefenition" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="weightageDefenitionForm" />

	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.admission.weightagedefenition" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.admission.weightagedefenition" /> </strong></div>
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
									<td width="20%" height="25" class="row-even"><label><span
										class="row-white"> <html:select
										property="programTypeId" styleId="programType"
										styleClass="combo" onchange="getPrograms(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="programTypeList"
											label="programTypeName" value="programTypeId" />
									</html:select> </span></label></td>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td width="20%" class="row-even"><span class="row-white">
									<html:select property="programId" styleClass="combo"
										styleId="program" onchange="getCourses(this.value) ">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${weightageDefenitionForm.programTypeId != null && weightageDefenitionForm.programTypeId != ''}">
											<c:set var="programMap"
												value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select> </span></td>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="30%" class="row-even"><span class="row-white">
									<html:select property="courseId" styleClass="combo"
										styleId="course" onchange="setCourseName()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>

										<c:if
											test="${weightageDefenitionForm.programId != null && weightageDefenitionForm.programId != ''}">
											<c:set var="coursesMap"
												value="${baseActionForm.collectionMap['coursesMap']}" />
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select> </span></td>
								</tr>
								<tr>
									<td width="10%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.year" />:</div>
									</td>
									<td width="20%" height="25" class="row-even"><span class="row-white">
									<input type="hidden" id="tempyear" name="tempyear"
										value="<bean:write name="weightageDefenitionForm" property="year"/>" />
									<html:select property="year" styleId="year">
										<html:option value=" ">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select> </span></td>
									<td width="10%" class="row-odd">&nbsp;</td>
									<td width="20%" class="row-even">&nbsp;</td>
									<td width="10%" class="row-odd">&nbsp;</td>
									<td width="30%" class="row-even">&nbsp;</td>
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
						<tr align="center">
							<td height="35"><html:button property=""
								styleClass="formbutton" value="Update Defined Weightage"
								onclick="updateWeightage()"></html:button> <html:submit
								styleClass="formbutton">
								<bean:message key="knowledgepro.admission.defineweightage" />
							</html:submit> <html:button property="" styleClass="formbutton" value="Reset"
								onclick="resetFieldAndErrMsgs()"></html:button></td>

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