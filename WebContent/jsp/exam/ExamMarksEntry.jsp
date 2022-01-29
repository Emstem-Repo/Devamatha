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
<title>:: CMS ::</title>
<SCRIPT>
	function fun() {
		document.location.href = "ExamMarksEntry.do?method=initExamMarksEntry";
	}

	function getExamName(examType) {
		getExamNameByExamType("examMap", examType, "examNameId", updateExamName);

	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examNameId", "- Select -");
		updateCurrentExam(req, "examNameId");
		getCourse(document.getElementById("examNameId").value);
	}
	function getCourse(examName) {
		getCourseByExamName("schemeMap", examName, "course", updateToCourse);

	}
	function updateToCourse(req) {
		updateOptionsFromMap(req, "course", "- Select -");

	}

	function getScheme(courseId) {
		examId = document.getElementById("examNameId").value;
		getSchemeNoByExamIdCourseId("schemeMap", examId, courseId, "scheme",
				updateToScheme);

	}
	function updateToScheme(req) {
		updateOptionsFromMap(req, "scheme", "- Select -");

	}
	function getSubject(schemeId) {
		var courseId = document.getElementById("course").value;
		getAnswerScript("", "", courseId, schemeId);
		getEvaluatorType("", "", courseId, schemeId);
		var examId = document.getElementById("examNameId").value;
		getSubjectsByCourseSchemeExamId("schemeMap", courseId, "subject",
				updateToSubject, schemeId,examId);
	}

	function updateToSubject(req) {
		updateOptionsFromMap(req, "subject", "- Select -");

	}

	function getSubjectType(subjectNo) {
		if (subjectNo != '') {
			var args = "method=getSubjectsTypeBySubjectId&subjectId="
					+ subjectNo;
			var url = "AjaxRequest.do";
			requestOperation(url, args, updateClass);
		}
	}
	function updateClass(req) {
		updateSubjectsTypeBySubjectIdForMarks(req, "subjectType");
		var subjectId = document.getElementById("subject").value;
		var subjectType = document.getElementById("subjectType").value;
		var courseId = document.getElementById("course").value;
		var schemeId = document.getElementById("scheme").value;
		getAnswerScript(subjectId, subjectType, courseId, schemeId);
		getEvaluatorType(subjectId, subjectType, courseId, schemeId);
	}

	function getEvaluatorType(subjectId, subjectType, courseId, schemeId) {
		examName = document.getElementById("examNameId").value;
	
		var args = "method=getEvaluatorTypeMap&subjectId=" + subjectId
				+ "&subjectType=" + subjectType + "&courseId=" + courseId
				+ "&schemeId=" + schemeId+"&examName="+examName;
		var url = "AjaxRequest.do";

		requestOperation(url, args, updateEvaluator);

	}
	function getAnswerScript(subjectId, subjectType, courseId, schemeId) {
		examName = document.getElementById("examNameId").value;
		var args = "method=getAnswerScriptTypeMap&subjectId=" + subjectId
				+ "&subjectType=" + subjectType + "&courseId=" + courseId
				+ "&schemeId=" + schemeId+"&examName="+examName;
		var url = "AjaxRequest.do";
		requestOperation(url, args, updateAnswerScript);

	}
	function updateAnswerScript(req) {
		updateOptionsFromMap1(req, "answerScriptType", "- Select -","ast","answerScriptType");
	}
	function updateEvaluator(req) {
		updateOptionsFromMap1(req, "evaluatorType", "- Select -","et","evaluatorType");
		
	}
	function populateOptionForSup(isSup){
		var destination = document.getElementById("subjectType");
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		if(isSup){
			destination.options[0] = new Option("Select", "");
			destination.options[1] = new Option("Theory", 1);
			destination.options[2] = new Option("Practical", 0);
		}
		else{
			destination.options[0] = new Option("Select", "");
			destination.options[1] = new Option("Theory", 1);
			destination.options[2] = new Option("Practical", 0);
			destination.options[3] = new Option("Theory and Practical", 11);
		}
	}
	function getSectionByScheme(schemeId) {

		var courseId = document.getElementById("course").value;
		var examId =  document.getElementById("examNameId").value;
		getSectionByExamIdCourseIdSchemeId("sectionMap", courseId, schemeId,examId,
				"section", updateSection);
	}
	function updateSection(req) {
		updateOptionsFromMap(req, "section", "--Select--");
	}
	
</SCRIPT>

</head>


<html:form action="/ExamMarksEntry.do" method="POST"
	enctype="multipart/form-data" >

	<html:hidden property="method" styleId="method"
		value="getStudentMarksEntry" />
	<html:hidden property="formName" value="ExamMarksEntryForm"
		styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
<html:hidden property="validationAST" styleId="validationAST"/>
<html:hidden property="validationET" styleId="validationET"/>
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Marks Entry&gt;&gt;</span></span></td>

		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Student
					Marks Entry</td>
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

									<td height="25" colspan="8" class="row-even">
									<div align="Center">
										<logic:equal value="ShowAll" property="showExamType" name="ExamMarksEntryForm">
											<html:radio property="examType" styleId="examId" value="Regular" onclick="getExamName(this.value), populateOptionForSup(false)"></html:radio>
												Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<script type="text/javascript">
													document.getElementById("examId").checked = true;
												</script>
											<html:radio property="examType" value="Supplementary" styleId="sup" onclick="getExamName(this.value), populateOptionForSup(true)" ></html:radio>
												Supplementary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<html:radio property="examType" value="Internal" styleId="int" onclick="getExamName(this.value), populateOptionForSup(false)"></html:radio> Internal
										</logic:equal>
										<logic:equal value="ShowRegularAndSupply" property="showExamType" name="ExamMarksEntryForm">
											<html:radio property="examType" styleId="examId" value="Regular" onclick="getExamName(this.value), populateOptionForSup(false)"></html:radio>
												Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<script type="text/javascript">
													document.getElementById("examId").checked = true;
												</script>
											<html:radio property="examType" value="Supplementary" styleId="sup" onclick="getExamName(this.value), populateOptionForSup(true)" ></html:radio>
												Supplementary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<html:radio property="examType" disabled="true" value="Internal" styleId="int"></html:radio> Internal										
										</logic:equal>
										<logic:equal value="ShowInternal" property="showExamType" name="ExamMarksEntryForm">
											<html:radio property="examType" styleId="examId" value="Regular" disabled="true"></html:radio>
												Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<html:radio property="examType" value="Supplementary" styleId="sup" disabled="true"></html:radio>
												Supplementary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<html:radio property="examType" value="Internal" styleId="int" onclick="getExamName(this.value), populateOptionForSup(false)"></html:radio> Internal
											<script type="text/javascript">
													document.getElementById("int").checked = true;
												</script>
										</logic:equal>
									</div>
									</td>

								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> Exam
									Name :</div>
									</td>

									<td class="row-even"><html:select property="examName"
										styleClass="combo" styleId="examNameId"
										name="ExamMarksEntryForm" onchange="getCourse(this.value)"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<!--<logic:notEmpty name="ExamMarksEntryForm"
											property="examNameList">
											<html:optionsCollection property="examNameList"
												name="ExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>-->
										<c:if test="${retainValues!='yes'}">
											<logic:notEmpty name="ExamMarksEntryForm"
												property="examNameList">
												<html:optionsCollection property="examNameList"
													name="ExamMarksEntryForm" label="value" value="key" />
											</logic:notEmpty>
										</c:if>
										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamMarksEntryForm"
												property="examNameList">
												<html:optionsCollection property="examNameList"
													name="ExamMarksEntryForm" label="value" value="key" />
											</logic:notEmpty>
										</c:if>
									</html:select></td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Marks
									Entry For :</div>
									</td>

									<td height="25" class="row-even"><html:select
										property="marksEntryFor" styleId="marksEntryFor">
										<html:option value="">Select</html:option>
										<html:option value="All Students">All Students</html:option>
									</html:select></td>

								</tr>
								<tr>


									<td width="23%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Course:</div>
									</td>
									<td width="23%" height="25" class="row-even"><html:select
										property="course" styleClass="body" styleId="course"
										onchange="getScheme(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<c:if
											test="${ExamMarksEntryForm.examName != null && ExamMarksEntryForm.examName != ''}">
											<c:set var="schemeMap"
												value="${baseActionForm.collectionMap['schemeMap']}" />
											<c:if test="${schemeMap != null}">
												<html:optionsCollection name="schemeMap" label="value"
													value="key" />
											</c:if>
										</c:if>
										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamMarksEntryForm"
												property="courseNameList">
												<html:optionsCollection property="courseNameList"
													name="ExamMarksEntryForm" label="value" value="key" />
											</logic:notEmpty>
										</c:if>



									</html:select></td>

									<td width="28%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									Scheme:</div>
									</td>
									<td width="26%" class="row-even"><html:select
										property="scheme" styleClass="body" styleId="scheme"
										onchange="getSubject(this.value), getSectionByScheme(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<c:if
											test="${ExamMarksEntryForm.course != null && ExamMarksEntryForm.course != ''}">
											<c:set var="schemeMap"
												value="${baseActionForm.collectionMap['schemeMap']}" />
											<c:if test="${schemeMap != null}">
												<html:optionsCollection name="schemeMap" label="value"
													value="key" />
											</c:if>
										</c:if>
										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamMarksEntryForm"
												property="schemeNameList">
												<html:optionsCollection property="schemeNameList"
													name="ExamMarksEntryForm" label="value" value="key" />
											</logic:notEmpty>
										</c:if>


									</html:select></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>Subject
									:</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="subject" styleClass="body" styleId="subject"
										onchange="getSubjectType(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<c:if
											test="${ExamMarksEntryForm.scheme != null && ExamMarksEntryForm.scheme != ''}">
											<c:set var="schemeMap"
												value="${baseActionForm.collectionMap['schemeMap']}" />
											<c:if test="${schemeMap != null}">
												<html:optionsCollection name="schemeMap" label="value"
													value="key" />
											</c:if>
										</c:if>

										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamMarksEntryForm"
												property="subjectNameList">
												<html:optionsCollection property="subjectNameList"
													name="ExamMarksEntryForm" label="value" value="key" />
											</logic:notEmpty>
										</c:if>
									</html:select></td>
									<td height="25" class="row-odd">
									<div align="right">Subject Type:</div>
									</td>

									<td height="25" class="row-even"><html:select
										property="subjectType" styleId="subjectType">
										<html:option value="">Select</html:option>
										<html:option value="1">Theory</html:option>
										<html:option value="0">Practical</html:option>
										<html:option value="11">Theory and Practical</html:option>
									</html:select></td>

								</tr>

								<tr>



									<td height="25" class="row-odd">
									<div align="right" id="et"><span class="Mandatory">*</span>Evaluator Type :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="evaluatorType" styleClass="combo"
										styleId="evaluatorType" name="ExamMarksEntryForm"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${ExamMarksEntryForm.scheme != null && ExamMarksEntryForm.scheme != ''}">
											<c:set var="evaluatorMap"
												value="${baseActionForm.collectionMap['evaluatorList']}" />
											<c:if test="${evaluatorMap != null}">
												<html:optionsCollection name="evaluatorMap" label="value"
													value="key" />
											</c:if>
										</c:if>
										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamMarksEntryForm"
												property="evaluatorMap">
												<html:optionsCollection property="evaluatorMap"
													name="ExamMarksEntryForm" label="value" value="key" />
											</logic:notEmpty>
										</c:if>
										
									</html:select></td>

									<td height="25" class="row-odd" >
									<div align="right" id="ast"><span class="Mandatory">*</span>Answer Script Type :</div>
									</td>

									<td height="25" class="row-even" colspan="1" ><html:select
										property="answerScriptType" styleClass="combo"
										styleId="answerScriptType" name="ExamMarksEntryForm"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${ExamMarksEntryForm.scheme != null && ExamMarksEntryForm.scheme != ''}">
											<c:set var="answerScriptTypeMap"
												value="${baseActionForm.collectionMap['answerScriptTypeMap']}" />
											<c:if test="${answerScriptType != null}">
												<html:optionsCollection name="answerScriptTypeMap"
													label="value" value="key" />
											</c:if>
										</c:if>
										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamMarksEntryForm"
												property="answerScriptTypeMap">
												<html:optionsCollection property="answerScriptTypeMap"
													name="ExamMarksEntryForm" label="value" value="key" />
											</logic:notEmpty>
										</c:if>
											
									</html:select></td>
									
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right">Display Order:</div>
									</td>
									<td class="row-even"><html:radio
										property="rollOrReg" value="register" styleId="reg" />
									Register Number <html:radio property="rollOrReg" value="roll" />
									Roll Number <script type="text/javascript">
	document.getElementById("reg").checked = true;
</script>
										<td width="22%" class="row-odd">
											<div align="right">&nbsp;<bean:message
												key="admissionForm.studentinfo.castcatg.label" /></div>
											</td>
											<td width="34%" class="row-even"><html:select
												property="sectionId" styleClass="combo" styleId="section">
												<html:option value="0">
													<bean:message key="knowledgepro.select" />
												</html:option>
												<c:set var="sectionMap"
													value="${baseActionForm.collectionMap['sectionMap']}" />
												<c:if test="${sectionMap != null}">
													<html:optionsCollection name="sectionMap" label="value"
														value="key" />
												</c:if>
											</html:select></td>
								
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
							<td width="49%" height="35" align="right"><input
								name="Submit7" type="submit" class="formbutton" value="Submit" /></td>

							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								type="button" class="formbutton" value="Cancel" onclick="fun()" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"></td>
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
</html:form>
