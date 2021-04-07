<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<head>
<SCRIPT>
	function fun() {
		document.location.href = "ExamAssignmentOverallMarks.do?method=initAssignmentOverallMarks";
	}

	function isValidNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}

	function getCourses(academicYear) {
		getCoursesByAcademicYear("courseMap", academicYear, "courseId",
				updateCourse);

	}

	function getCourse(examName) {
		getCourseByExamName("courseMap", examName, "courseId", updateCourse);
	}
	function updateCourse(req) {
		updateOptionsFromMap(req, "courseId", "--Select--");
	}
	//for second method
	function getScheme(courseId) {
		if (document.getElementById("assignmentOverall1").checked == true) {
			var examId = document.getElementById("examName").value;
			getSchemeNoByExamIdCourseId("schemeMap", examId, courseId,
					"schemeNo", updateScheme);
		}
		if (document.getElementById("assignmentOverall2").checked == true) {
			var academicYear = document.getElementById("joiningBatch").value;
			getSchemeNo_SchemeIDByCourseIdAcademicId("schemeMap", courseId,
					academicYear, "schemeNo", updateScheme);
		}
	}

	function updateScheme(req) {
		updateOptionsFromMap(req, "schemeNo", "--Select--");
	}
	function getSubjects(schemeNo) {
		var courseId = document.getElementById("courseId").value;
		var examId = document.getElementById("examName").value;
		var jby= document.getElementById("joiningBatch").value;
		getSubjectsByCourseSchemeExamIdJBY("subjectMap", courseId, "subject",
				updateSubject, schemeNo,examId,jby);
	}
	function updateSubject(req) {
		updateOptionsFromMap(req, "subject", "--Select--");
	}
	function getSubjectsType(subjectNo) {
		if (subjectNo != '') {
			var args = "method=getSubjectsTypeBySubjectId&subjectId="
					+ subjectNo;
			var url = "AjaxRequest.do";
			requestOperation(url, args, updateClass);
		}
	}
	function updateClass(req) {
		updateSubjectsTypeBySubjectId(req, "subjectType");
	}

	function getType(type) {
		if (type == "overall") {
			document.getElementById("joiningBatch").disabled = true;
			document.getElementById("examName").disabled = false;
			document.getElementById("isPreviousExam_1").disabled = false;
			document.getElementById("isPreviousExam_2").disabled = false;
			document.getElementById("isPreviousExam_1").checked = true;
			document.location.href = "ExamAssignmentOverallMarks.do?method=resetValues&assignmentOverall="
					+ type;
		}
		if (type == "Assignment") {
			document.getElementById("joiningBatch").disabled = false;
			document.getElementById("examName").disabled = true;
			document.getElementById("isPreviousExam_1").disabled = true;
			document.getElementById("isPreviousExam_2").disabled = true;
			document.getElementById("isPreviousExam_2").checked = true;
			var jbatch = document.getElementById("joiningBatch").value;
			document.location.href = "ExamAssignmentOverallMarks.do?method=resetValues&assignmentOverall="
					+ type + "&joiningBatch=" + jbatch;
		}
	}
	function updateType(req) {
		updateOptionsFromMap(req, "type", "--Select--");
	}
	function populateOptionForSup(isSup){
		var destination = document.getElementById("subjectType");
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		if(isSup){
			destination.options[0] = new Option("Select", "");
			destination.options[1] = new Option("Theory", "T");
			destination.options[2] = new Option("Practical", "P");
		}
		else{
			destination.options[0] = new Option("Select", "");
			destination.options[1] = new Option("Theory", "T");
			destination.options[2] = new Option("Practical", "P");
			destination.options[3] = new Option("Theory and Practical", "B");
		}
	}
	function add(){
		document.getElementById("selectedExam").value = document.getElementById("examName").options[document.getElementById("examName").selectedIndex].text;
		document.ExamAssignmentOverallMarksForm.submit();
	}
	function getExamsByExamTypeAndYear() {
		var examType=document.getElementById("examType").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
</SCRIPT>

</head>

<body>
<html:form action="/ExamAssignmentOverallMarks.do" method="POST"
	enctype="multipart/form-data">

	<html:hidden property="method" styleId="method"
		value="getStudentAssignmentOverallMarks" />
	<html:hidden property="formName" value="ExamAssignmentOverallMarksForm"
		styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="selectedExam" styleId="selectedExam" value="" />


	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> &gt;&gt; <bean:message
				key="knowledgepro.exam.assignmentOverallMarks" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.assignmentOverallMarks" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
									<td class="row-even" colspan="2">
									<div align="right"><html:radio
										property="assignmentOverall" styleId="assignmentOverall1"
										value="overall" onclick="getType(this.value)" /> <bean:message
										key="knowledgepro.exam.assignmentOverallMarks.overall" /> <html:radio
										property="assignmentOverall" value="Assignment"
										styleId="assignmentOverall2" onclick="getType(this.value)" />
									<bean:message
										key="knowledgepro.exam.assignmentOverallMarks.assignment" /></div>
									</td>
									<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="ExamAssignmentOverallMarksForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examType" /> :</div></td>
								<td height="25" class="row-even"><html:select
										property="examType" styleClass="combo"  styleId="examType" onchange="getExamsByExamTypeAndYear()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="examTypeList"
											name="ExamAssignmentOverallMarksForm">
											<html:optionsCollection property="examTypeList"
												name="ExamAssignmentOverallMarksForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									<td class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.ExamName" /> :</div>
									</td>
									<td class="row-even"><html:select property="examName"
										styleClass="combo" styleId="examName"
										name="ExamAssignmentOverallMarksForm"
										onchange="getCourse(this.value)" style="width:200px">

										<logic:notEmpty name="ExamAssignmentOverallMarksForm"
											property="examNameMap">
											<html:option value="">select</html:option>
											<html:optionsCollection property="examNameMap"
												name="ExamAssignmentOverallMarksForm" label="value"
												value="key" />
										</logic:notEmpty>
									</html:select></td>
								</tr>



								<tr>
									<td width="28%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamInternalMark.course" /> :</div>
									</td>
									<td width="26%" height="25" class="row-even"><html:select
										name="ExamAssignmentOverallMarksForm" property="courseId"
										styleId="courseId" styleClass="combo"
										onchange="getScheme(this.value)" style="width:225px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>

										<c:if
											test="${ExamAssignmentOverallMarksForm.listCourseMap != null && ExamAssignmentOverallMarksForm.listCourseMap != ''}">

											<html:optionsCollection property="listCourseMap"
												name="ExamAssignmentOverallMarksForm" label="value"
												value="key" />

										</c:if>

										<c:if
											test="${ExamAssignmentOverallMarksForm.examNameId != null && ExamAssignmentOverallMarksForm.examNameId != ''}">
											<c:set var="coursesMap"
												value="${baseActionForm.collectionMap['coursesMap']}" />
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value"
													value="key" />
											</c:if>
										</c:if>



									</html:select></td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.ExamInternalMark.Scheme" />:</div>
									</td>
									<td height="25" class="row-even"><html:select
										name="ExamAssignmentOverallMarksForm" property="schemeNo"
										styleId="schemeNo" styleClass="combo"
										onchange="getSubjects(this.value)" style="width:225px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>

										<c:if
											test="${ExamAssignmentOverallMarksForm.mapScheme != null && ExamAssignmentOverallMarksForm.mapScheme != ''}">

											<html:optionsCollection property="mapScheme"
												name="ExamAssignmentOverallMarksForm" label="value"
												value="key" />

										</c:if>
										<c:if
											test="${ExamAssignmentOverallMarksForm.courseId != null && ExamAssignmentOverallMarksForm.courseId != ''}">
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
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span> <span
										class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamInternalMark.subjectType" /> :</div>
									</td>
									<td height="25" class="row-even"><html:select
										name="ExamAssignmentOverallMarksForm" property="subject"
										styleId="subject" styleClass="combo"
										onchange="getSubjectsType(this.value)" style="width:225px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${ExamAssignmentOverallMarksForm.mapSubject != null && ExamAssignmentOverallMarksForm.mapSubject != ''}">

											<html:optionsCollection property="mapSubject"
												name="ExamAssignmentOverallMarksForm" label="value"
												value="key" />

										</c:if>
										<c:if
											test="${ExamAssignmentOverallMarksForm.schemeNo != null && ExamAssignmentOverallMarksForm.schemeNo != ''}">
											<c:set var="subjectMap"
												value="${baseActionForm.collectionMap['subjectMap']}" />
											<c:if test="${subjectMap != null}">
												<html:optionsCollection name="subjectMap" label="value"
													value="key" />
											</c:if>
										</c:if>

									</html:select></td>
									
								</tr>
								
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span> <span
										class="Mandatory">*</span>Supplementary:</div>
									</td>
									<td height="25" class="row-even">
									<html:radio property="examType" value="supyes" styleId="yes"
											onclick="populateOptionForSup(true)" ></html:radio>
										Yes
									<html:radio property="examType" value="supno" styleId="no"
											onclick="populateOptionForSup(false)" ></html:radio> No
											<script type="text/javascript">document.getElementById("no").checked = true;</script>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span> <span
										class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamMarksEntry.subjectType" />:</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="subjectType" styleId="subjectType">
										<html:option value="">Select</html:option>
										<html:option value="T">Theory</html:option>
										<html:option value="P">Practical</html:option>
										<html:option value="B">Theory and Practical</html:option>
									</html:select></td>
									
								</tr>
								<tr>



									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.footerAggreement.type" /> :</div>
									</td>
									<td height="25" class="row-even"><html:select
										name="ExamAssignmentOverallMarksForm" property="type"
										styleId="type" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>

										<c:if
											test="${ExamAssignmentOverallMarksForm.typeMap != null && ExamAssignmentOverallMarksForm.typeMap != ''}">

											<html:optionsCollection property="typeMap"
												name="ExamAssignmentOverallMarksForm" label="value"
												value="key" />

										</c:if>
										<c:if
											test="${ExamAssignmentOverallMarksForm.assignmentOverall != null && ExamAssignmentOverallMarksForm.assignmentOverall != ''}">
											<c:set var="typeMap"
												value="${baseActionForm.collectionMap['typeMap']}" />
											<c:if test="${typeMap != null}">
												<html:optionsCollection name="typeMap" label="value"
													value="key" />
											</c:if>
										</c:if>

									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.reJoin.joiningBatch" />:</div>
									</td>
									<td class="row-even"><html:select property="joiningBatch"
										styleId="joiningBatch" styleClass="combo"
										onchange="getCourses(this.value)">

										<cms:renderYear></cms:renderYear>
									</html:select></td>
								</tr>
								<tr>
									<td width="13%" height="25" class="row-odd">
										<div align="right">Exam:</div>
										</td>
										<td width="19%" height="25" class="row-even">
										<input type="radio" name="isPreviousExam" id="isPreviousExam_1" value="true" checked="checked"/> Previous
			                    		<input type="radio" name="isPreviousExam" id="isPreviousExam_2" value="false" /> Current
			    						<script type="text/javascript">
											var isPreviousExam = "<bean:write name='ExamAssignmentOverallMarksForm' property='isPreviousExam'/>";
											if(isPreviousExam == "true") {
							                        document.getElementById("isPreviousExam_1").checked = true;
											}	
										</script>
		    						</td>
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="49%" height="35" align="right">
							<html:button property=""
										styleClass="formbutton" value="Submit"
										onclick="add()"></html:button>
						
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input type="reset"
								class="formbutton" value="Cancel" onclick=fun();;
/></td>
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
	<script>
	var type = document.getElementById("assignmentOverall1").checked;

	if (type) {
		document.getElementById("joiningBatch").disabled = true;
		document.getElementById("examName").disabled = false;
		document.getElementById("isPreviousExam_1").disabled = false;
		document.getElementById("isPreviousExam_2").disabled = false;
		document.getElementById("isPreviousExam_1").checked = true;
	} else {
		document.getElementById("joiningBatch").disabled = false;
		document.getElementById("examName").disabled = true;
		document.getElementById("isPreviousExam_1").disabled = true;
		document.getElementById("isPreviousExam_2").disabled = true;
		document.getElementById("isPreviousExam_2").checked = true;
	}
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
	}
</script>
</html:form>