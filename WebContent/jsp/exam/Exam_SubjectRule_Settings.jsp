<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link href="../css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">


	function getCourses(programTypeID) {

		getCoursesByProgramType1("coursesMap", programTypeID, "course",
				updateCourses);

	}
	function updateCourses(req) {
		updateOptionsFromMapMultiselect(req, "course", "- Select -");
	}
	function setCourseName() {

		document.getElementById("courseName").value = document
				.getElementById("course").options[document
				.getElementById("course").selectedIndex].text;
	}
	function resetValues() {
		document.location.href = "ExamSubjectRuleSettings.do?method=initSubjectRuleSet";

	}

	function deleteSubjectRuleSettings() {
		deleteConfirm =confirm("Are you sure to delete  this entry?");
		if(deleteConfirm)
		{
		 		
			document.getElementById("delete").value ="Delete";
		  
		}
		

	}
	function reActivate(id) {
		document.getElementById("submit").value ="Reactivate";
		document.forms["ExamSubjectRuleSettingsForm"].submit();
		//document.location.href = "ExamSubjectRuleSettings.do?method=reActivateSubjectRuleMaster&id="+id;
	}
	function addValue() {

		
		var obj1= document.getElementById("course").selectedIndex;
		document.getElementById("coursesSelectedIndex").value=obj1;
		document.getElementById("method").value ="Add";
		document.forms["ExamSubjectRuleSettingsForm"].submit();
	}


</script>
<html:form action="/ExamSubjectRuleSettings.do" method="POST">

<html:hidden property="formName" value="ExamSubjectRuleSettingsForm" />
	<html:hidden property="pageType" value="1" />


	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><a href="main.html"
				class="Bredcrumbs"><bean:message key="knowledgepro.exam.exam" /></a>
			<span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.exam.subjectrulesettings" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.subjectrulesettings" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.studentEligibilityEntry.academicYear" />
									:</div>
									</td>
									<td class="row-even" width="25%"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="ExamSubjectRuleSettingsForm" property="academicYear"/>' />

									<html:select
										property="academicYear" styleId="academicYear"
										styleClass="combo" onclick="resetErrMsgs()">
										<html:option value="0">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>
								</tr>
								<tr>
									<td width="22%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>

									<td width="20%" height="15" class="row-even"><html:select
										property="selectedProgramType" styleClass="body"
										styleId="programType" onchange="getCourses(this.value)" onclick="resetErrMsgs()">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<html:optionsCollection name="ExamSubjectRuleSettingsForm"
											property="programTypeList" label="display" value="id" />

									</html:select></td>
									<td width="15%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="16%" class="row-even"><nested:select
										property="selectedCourse" styleClass="body"
										multiple="multiple" size="5" styleId="course"
										style="width:400px" onchange="setCourseName()">

										<c:if
											test="${ExamSubjectRuleSettingsForm.mapCourse != null && ExamSubjectRuleSettingsForm.mapCourse != ''}">
											 <nested:optionsCollection property="mapCourse" name="ExamSubjectRuleSettingsForm"
													label="value" value="key" styleClass="comboBig" />

											</c:if>

										<c:if
											test="${ExamSubjectRuleSettingsForm.programTypeId != null && ExamSubjectRuleSettingsForm.programTypeId != ''}">
											<c:set var="coursesMap"
												value="${baseActionForm.collectionMap['coursesMap']}" />
											<c:if test="${coursesMap != null}">

												<nested:optionsCollection property="coursesMap"
													label="value" value="key" styleClass="comboBig" />

											</c:if>
										</c:if>


									</nested:select></td>
								</tr>
								<tr>
									<td width="15%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.exam.examDefinition.schemeType" />:</div>
									</td>
									<td class="row-even" colspan="3"><html:select
										property="selectSchemeType" styleId="scheme"
										styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<html:option value="1">Odd</html:option>
										<html:option value="2">Even</html:option>
										<html:option value="3">Both</html:option>

									</html:select>
								</tr>
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
						<tr>
							<td width="42%" height="35" align="right">
							<html:submit property="method" value="Add" styleClass="formbutton" styleId="submit"></html:submit>
							</td>
							<td width="2%"></td>
							<td width="6%" height="35" align="center"><html:button
								property="submit" value="Reset" styleClass="formbutton"
								onclick="resetValues()"></html:button></td>
							<td width="2%"></td>
							<td width="5%" align="left"><html:submit property="method"
								value="Edit" styleClass="formbutton"/></td>
							<td width="2%"></td>
							<td width="6%" height="35" align="center"><html:submit
								property="method" value="Delete"  styleId="delete" styleClass="formbutton" onclick="deleteSubjectRuleSettings()"/></td>
							<td width="2%"></td>
							<td width="41%" height="35" align="left"><html:submit
								property="method" value="Copy" styleClass="formbutton"/></td>
						</tr>
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
<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
	</script>
</html:form>
