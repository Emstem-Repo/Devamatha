<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<SCRIPT>
	function goToFirstPage() {
		document.location.href = "ExamStudentMarksCorrection.do?method=initExamStudentMarksCorrection";
	}

	function getExamName(examType) {
		getExamNameByExamType("examMap", examType, "examName", updateExamName);

	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
	}
	function getCourse(examName) {
		getCourseByExamName("schemeMap", examName, "course", updateToCourse);

	}
	function updateToCourse(req) {
		updateOptionsFromMap(req, "course", "- Select -");

	}

	function getScheme(courseId) {
		examId = document.getElementById("examName").value;
		getSchemeNoByExamIdCourseId("schemeMap", examId, courseId, "scheme",
				updateToScheme);

	}
	function updateToScheme(req) {
		updateOptionsFromMap(req, "scheme", "- Select -");

	}
	function getSubject(schemeId) {
		var courseId = document.getElementById("course").value;
		examId = document.getElementById("examName").value;
		getAnswerScript("", "", courseId, schemeId);
		getEvaluatorType("", "", courseId, schemeId);
		getSubjectsByCourseSchemeExamId("schemeMap", courseId, "subject",
				updateToSubject, schemeId,examId);
	}

	function updateToSubject(req) {
		updateOptionsFromMap(req, "subject", "- Select -");
	}
	function getMarkType(examId) {
		getMarkTypeByExamTypeId("markTypeMap", examId, "markType",
				updateToMarkType);
	}
	function updateToMarkType(req) {
		updateOptionsFromMap(req, "markType", "- Select -");

	}
	function getEvaluatorType(subjectId, subjectType, courseId, schemeId) {
		var examName = document.getElementById("examName").value;
	
		var args = "method=getEvaluatorTypeMap&subjectId=" + subjectId
				+ "&subjectType=" + subjectType + "&courseId=" + courseId
				+ "&schemeId=" + schemeId+"&examName="+examName;
		
		var url = "AjaxRequest.do";

		requestOperation(url, args, updateEvaluator);

	}
	function getAnswerScript(subjectId, subjectType, courseId, schemeId) {
		
	var	examName = document.getElementById("examName").value;
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
	
	function getEvaluator(subjectId) {
		var courseId = document.getElementById("course").value;
		var schemeId = document.getElementById("scheme").value;
		getAnswerScript(subjectId, "", courseId, schemeId);
		getEvaluatorType(subjectId, "", courseId, schemeId);
	}

	function getEvaluatorForMarkType(mode){
		
		if(mode == "Internal overAll" || mode == "Regular overAll"){
		document.getElementById("validationAST").value = "no";
		document.getElementById("validationET").value = "no";
		document.getElementById("ast").style.display = "none";
		document.getElementById("answerScriptType").style.display = "none";
		document.getElementById("et").style.display = "none";
		document.getElementById("evaluatorType").style.display = "none";
	}
	}
	function enableCourse(){
		if(document.getElementById("marksEntryFor").value == 'Single Student'){
			document.getElementById("course").disabled = true;
			document.getElementById("course").value = null;
			document.getElementById("singleStudent").style.display = "block";
			document.getElementById("allStudents").style.display = "none";
		}
		else{
			document.getElementById("singleStudent").style.display = "none";
			document.getElementById("allStudents").style.display = "block";
			document.getElementById("course").disabled = false;
		}	
	}
	function getEvaluatorAndAnswerScript(schemeNo){
		getAnswerScript("", "", "", schemeNo);
		getEvaluatorType("", "", "", schemeNo);
	}

	function displayEvaluAndAnswerScript(subjectType){
		var subjectId = document.getElementById("subject").value;
		var courseId = document.getElementById("course").value;
		var schemeId = document.getElementById("scheme").value;
		getAnswerScript(subjectId, subjectType, courseId, schemeId);
		getEvaluatorType(subjectId, subjectType, courseId, schemeId);
	}
</SCRIPT>


<html:form action="/ExamStudentMarksCorrection.do" method="POST"
	enctype="multipart/form-data">

	<html:hidden property="method" styleId="method" value="getSubjects" />
	<html:hidden property="formName" value="ExamStudentMarksCorrectionForm"
		styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="validationAST" styleId="validationAST" name="ExamStudentMarksCorrectionForm"  />
	<html:hidden property="validationET" styleId="validationET" name="ExamStudentMarksCorrectionForm" />
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Student Marks Edit&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>

					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Student
					Marks Correction</td>
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
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Exam
									Type :</div>
									</td>
									<td class="row-even"><html:select property="examType"
										styleId="examType"
										onchange="getExamName(this.value),getMarkType(this.value)"
										style="width:200px">
										<html:option value="">Select</html:option>
										<html:option value="Regular">Regular</html:option>
										<html:option value="Supplementary">Supplementary</html:option>
										<html:option value="Internal">Internal</html:option>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> Exam
									Name :</div>
									</td>
									<td class="row-even"><html:select property="examName"
										styleClass="comboLarge" styleId="examName"
										name="ExamStudentMarksCorrectionForm"
										onchange="getCourse(this.value)" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamStudentMarksCorrectionForm"
											property="listExamName">
											<html:optionsCollection property="listExamName"
												name="ExamStudentMarksCorrectionForm" label="display"
												value="id" />
										</logic:notEmpty>
										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamStudentMarksCorrectionForm"
												property="examNameList">
												<html:optionsCollection property="examNameList"
													name="ExamStudentMarksCorrectionForm" label="value"
													value="key" />
											</logic:notEmpty>
										</c:if>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Marks
									Entry For :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="marksEntryFor" styleId="marksEntryFor"
										style="width:200px" onchange="enableCourse()">
										<html:option value="">Select</html:option>
										<html:option value="Single Student">Single Student</html:option>
									</html:select></td>
									
									<td width="23%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									Course:</div>
									</td>
									<td width="23%" height="25" class="row-even"><html:select
										property="course" styleClass="combo" styleId="course"
										onchange="getScheme(this.value)" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										
										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamStudentMarksCorrectionForm"
												property="courseNameList">
												<html:optionsCollection property="courseNameList"
													name="ExamStudentMarksCorrectionForm" label="value"
													value="key" />
											</logic:notEmpty>
										</c:if>


									</html:select></td>
								</tr>
								<tr>
								<td width="28%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									Scheme:</div>
									</td>
						                <td class="row-even">
						                	<div id="singleStudent">
											<nested:select property="schemeId" styleId="scheme1" styleClass="combo" onchange="getEvaluatorAndAnswerScript(this.value)">
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:option value="1">1</html:option>
												<html:option value="2">2</html:option>
												<html:option value="3">3</html:option>
												<html:option value="4">4</html:option>
												<html:option value="5">5</html:option>
												<html:option value="6">6</html:option>
												<html:option value="7">7</html:option>
												<html:option value="8">8</html:option>
												<html:option value="9">9</html:option>
												<html:option value="10">10</html:option>
											</nested:select>
											</div>
											<div id="allStudents">
											<nested:select
											property="scheme" styleClass="combo" styleId="scheme"
											onchange="getSubject(this.value)" style="width:200px">
											<html:option value="">
												<bean:message key="knowledgepro.select" />
											</html:option>
											<c:if
												test="${ExamStudentMarksCorrectionForm.course != null && ExamStudentMarksCorrectionForm.course != ''}">
												<c:if test="${schemeMap != null}">
													<html:optionsCollection name="schemeMap" label="value"
														value="key" />
												</c:if>
											</c:if>
										</nested:select>
											</div>
										</td>
									<td height="25" class="row-odd">
									<div align="right">Subject :</div>
									</td>
									<td height="25" colspan="3" class="row-even"><html:select
										property="subject" styleClass="comboExtraLarge" styleId="subject"
										onchange="getEvaluator(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamStudentMarksCorrectionForm"
												property="subjectNameList">
												<html:optionsCollection property="subjectNameList"
													name="ExamStudentMarksCorrectionForm" label="value"
													value="key" />
											</logic:notEmpty>
										</c:if>

									</html:select></td>

								</tr>

								<tr>
									<td height="25" class="row-odd">
									<div align="right">Mark Type :</div>
									</td>

									<td height="25" class="row-even" colspan="3"><html:select
										property="markType" styleClass="combo" styleId="markType"
											onchange="getEvaluatorForMarkType(this.value)"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<c:if
											test="${ExamStudentMarksCorrectionForm.examName != null && ExamStudentMarksCorrectionForm.examName != ''}">
											<c:set var="markTypeMap"
												value="${baseActionForm.collectionMap['markTypeMap']}" />
											<c:if test="${markTypeMap != null}">
												<html:optionsCollection name="markTypeMap" label="value"
													value="key" />
											</c:if>
										</c:if>
										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamStudentMarksCorrectionForm"
												property="markTypeList">
												<html:optionsCollection property="markTypeList"
													name="ExamStudentMarksCorrectionForm" label="value"
													value="key" />
											</logic:notEmpty>
										</c:if>
									</html:select></td>

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right" id="et">Evaluator
									Type :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="evaluatorType" styleClass="combo"
										styleId="evaluatorType" name="ExamStudentMarksCorrectionForm"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${ExamStudentMarksCorrectionForm.scheme != null && ExamStudentMarksCorrectionForm.scheme != ''}">
											<c:set var="evaluatorMap"
												value="${baseActionForm.collectionMap['evaluatorList']}" />
											<c:if test="${evaluatorMap != null}">
												<html:optionsCollection name="evaluatorMap" label="value"
													value="key" />
											</c:if>
										</c:if>
										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamStudentMarksCorrectionForm"
												property="evaluatorMap">
												<html:optionsCollection property="evaluatorMap"
													name="ExamStudentMarksCorrectionForm" label="value"
													value="key" />
											</logic:notEmpty>
										</c:if>
									</html:select></td>
									<td height="25" class="row-odd">
									<div align="right" id="ast">
									Answer Script Type :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="answerScriptType" styleClass="comboLarge"
										styleId="answerScriptType"
										name="ExamStudentMarksCorrectionForm" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${ExamStudentMarksCorrectionForm.scheme != null && ExamStudentMarksCorrectionForm.scheme != ''}">
											<c:set var="answerScriptTypeMap"
												value="${baseActionForm.collectionMap['answerScriptTypeMap']}" />
											<c:if test="${answerScriptType != null}">
												<html:optionsCollection name="answerScriptTypeMap"
													label="value" value="key" />
											</c:if>
										</c:if>
										<c:if test="${retainValues=='yes'}">
											<logic:notEmpty name="ExamStudentMarksCorrectionForm"
												property="answerScriptTypeMap">
												<html:optionsCollection property="answerScriptTypeMap"
													name="ExamStudentMarksCorrectionForm" label="value"
													value="key" />
											</logic:notEmpty>
										</c:if>
									</html:select></td>
								</tr>
								
								<tr>
									<td width="13%" height="25" class="row-odd">
										<div align="right">Is Previous Exam</div>
										</td>
										<td width="19%" height="25" class="row-even">
										<input type="radio" name="isPrevExam" id="isPrevExam_1" value="true"/> <bean:message key="knowledgepro.yes"/>
			                    		<input type="radio" name="isPrevExam" id="isPrevExam_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
			    						<script type="text/javascript">
										var isPreviousExam = "<bean:write name='ExamStudentMarksCorrectionForm' property='isPrevExam'/>";
											if(isPreviousExam == "true") {
							                	document.getElementById("isPrevExam_1").checked = true;
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
						<tr>
							<td width="49%" height="35" align="right"><input
								name="Submit7" type="submit" class="formbutton" value="Submit" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								name="Submit8" type="button" class="formbutton" value="Cancel"
								onclick="goToFirstPage()" /></td>
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

<script>
	document.getElementById("marksEntryFor").value = "Single Student";
	document.getElementById("course").value = null;
	document.getElementById("course").disabled = true;
	document.getElementById("singleStudent").style.display = "block";
	document.getElementById("allStudents").style.display = "none";
</script>