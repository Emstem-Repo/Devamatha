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
	function resetValue() {
		document.location.href = "ExamSecuredMarksVerification.do?method=initExamSecuredMarksVerification";
	}

	function getSCodeName(sCName) {
		//examId=document.getElementById("examName").value;
		getSubjectCodeName("subjectMap", sCName, "subject",
				updateToSubject,  null);
		//getSubjectCodeName("subjectMap", sCName, "subject", updateToSubject);

	}
	function updateToSubject(req) {
		var subco = "subco";
		updateOptionsFromMap(req, "subject", "- Select -");
	}
	function getExamName(examType) {

		getExamNameByExamTypeWithoutCurrentExam("examMap", examType, "examName", updateExamName);
		if (examType == "Internal") {
			document.getElementById("evaluatorType").disabled = true;
			document.getElementById("answerScriptType").disabled = true;
		} else {
			document.getElementById("evaluatorType").disabled = false;
			document.getElementById("answerScriptType").disabled = false;
		}
		resetOption("subject");
	}

	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}

	function getSubjectsType(subjectNo) {
		if (subjectNo != '') {
			var args = "method=getSubjectsTypeBySubjectIdAndCollection&subjectId="
					+ subjectNo;
			var url = "AjaxRequest.do";
			requestOperation(url, args, updateClass);
		}
	}
	function updateClass(req) {
		updateOptionsFromMap(req, "subjectType", "- Select -");
		updateSubjectsTypeBySubjectId(req, "subjectType");
		subjectType = document.getElementById("subjectType").value;
		subject = document.getElementById("subject").value;
		examName = document.getElementById("examName").value;
		getEvalvatorType(subject, subjectType, examName);
		get_answerScript_type(subject, subjectType, examName);
	}
	function getEvalvatorType(subject, subjectType, examName) {
		var args = "method=getEvaluatorTypeBySubject&subjectId=" + subject
				+ "&subjectType=" + subjectType + "&examName=" + examName + "&propertyName=" + "evaluatorTypeMap";
		var url = "AjaxRequest.do";
		requestOperation(url, args, updateEvaluator);
	}
	function get_answerScript_type(subject, subjectType, examName) {
		var args = "method=get_answerScript_type&subjectId=" + subject
				+ "&subjectType=" + subjectType + "&examName=" + examName ;
		var url = "AjaxRequest.do";
		requestOperation(url, args, updateAnswerScript);
	}
	function updateAnswerScript(req) {
		updateOptionsFromMap1(req, "answerScriptType", "- Select -", "ast",
				"answerScriptType");
	}
	function updateEvaluator(req) {
		updateOptionsFromMap1(req, "evaluatorType", "- Select -", "et",
				"evaluatorType");
	}
	function getCheck(checkBox1) {
		if (document.getElementById("check").checked) {
			document.getElementById("check").value = "on";
		} else {
			document.getElementById("check").value = null;
		}

	}
	function getSubjectsByExamName(examId){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}
		else
		{
			sCName = document.getElementById("sCodeName_2").value;
		}
		getSubjectCodeName("subjectMap", sCName, "subject",
				updateToSubject,  examId);
	}
	
</SCRIPT>



<html:form action="/ExamSecuredMarksVerification.do">


	<html:hidden property="formName"
		value="ExamSecuredMarksVerificationForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="onSubmitGrid" />
	<html:hidden property="validationAST" styleId="validationAST" />
	<html:hidden property="validationET" styleId="validationET" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Student Marks Verification &gt;&gt;</span></span></td>

		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Student
					Marks Verification</td>
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
					<td class="heading">&nbsp;</td>
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

									<td height="25" colspan="8" class="row-even">
									<div align="Center">
									<input type="radio" name="examType" id="examType" value="Regular" checked="checked" onchange="getExamName(this.value)"/>
		    						<script type="text/javascript">
										var examType = "<bean:write name='ExamSecuredMarksVerificationForm' property='examType'/>";
										if(examType == "true") {
						                        document.getElementById("examType").checked = true;
										}	
									</script>										
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="examType" id="examType" value="Supplementary"  onchange="getExamName(this.value)"/>
									Supplementary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="examType" id="examType" value="Internal"  onchange="getExamName(this.value)"/>Internal
									</td>


								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									ExamName :</div>
									</td>

									<td class="row-even" colspan="2"><html:select
										name="ExamSecuredMarksVerificationForm" property="examId"
										styleId="examName" styleClass="combo" onchange="getSubjectsByExamName(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${ExamSplSubGroupOperation == 'edit'}">
												<html:optionsCollection name="examMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${ExamSecuredMarksVerificationForm.examType != null && ExamSecuredMarksVerificationForm.examType != ''}">
													<c:if test="${examMap != null}">
														<html:optionsCollection name="examMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
										<c:if test="${retainValues=='retain'}">
											<logic:notEmpty name="ExamSecuredMarksVerificationForm"
												property="examNameList">
												<html:optionsCollection property="examNameList"
													name="ExamSecuredMarksVerificationForm" label="value"
													value="key" />
											</logic:notEmpty>

										</c:if>
									</html:select></td>
									<td class="row-odd">
									<div align="right">Secured:</div>
									</td>
									<td class="row-even"><html:hidden property="dummyCheckBox"
										styleId="dummyCheckBox" /> <html:checkbox property="checkBox"
										styleId="check" onclick="getCheck(this.value)" /></td>
									<script>
	var v = document.getElementById("dummyCheckBox").value
	if (v == 'true') {
		document.getElementById("check").checked = true;
	}
</script>
								</tr>
								<tr>

									<td height="25" colspan="8" class="row-even">
									<div align="left">
									<input type="radio" name="sCodeName" id="sCodeName_1" value="sCode" checked="checked" onclick="getSCodeName(this.value)" />
									
									Subject
									Code&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="sCodeName" id="sCodeName_2" value="sName" onclick="getSCodeName(this.value)"/> 
										Subject
									Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</div>
									</td>


								</tr>

								<tr>
									<td height="25" rowspan="2" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject
									:</div>
									</td>
									<td class="row-even"><html:select property="subject"
										styleClass="combo" styleId="subject"
										name="ExamSecuredMarksVerificationForm"
										onchange="getSubjectsType(this.value)" style="width:350px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamSecuredMarksVerificationForm"
											property="subjectList">
											<html:optionsCollection property="subjectList"
												name="ExamSecuredMarksVerificationForm" label="display"
												value="id" />
										</logic:notEmpty>
										<c:if test="${subco =='subco'}">

											<c:set var="subjectMap"  value="${baseActionForm.collectionMap['optionMap']}" />
											<c:if test="${subjectMap != null}">
												<html:optionsCollection name="subjectMap" label="value"
													value="key" />

											</c:if>

										</c:if>
										<c:if test="${retainValues=='retain'}">
											<logic:notEmpty name="ExamSecuredMarksVerificationForm"
												property="subjectList">
												<html:optionsCollection property="subjectList"
													name="ExamSecuredMarksVerificationForm" label="display"
													value="id" />
											</logic:notEmpty>

										</c:if>
									</html:select></td>
									<td class="row-even">&nbsp;</td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject
									Type :</div>
									</td>

									<td rowspan="2" class="row-even"><html:select
										property="subjectType" styleClass="combo"
										styleId="subjectType" name="ExamSecuredMarksVerificationForm"
										style="width:90px">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
											<c:if
												test="${ExamSecuredMarksEntryForm.subject != null && ExamSecuredMarksEntryForm.subject != ''}">
												<c:set var="examMap"
													value="${baseActionForm.collectionMap['subjectMap']}" />
												<c:if test="${examMap != null}">
													<html:optionsCollection name="examMap" label="value"
														value="key" />
												</c:if>
											</c:if>
											<c:if test="${retainValues=='retain'}">
												<logic:notEmpty name="ExamSecuredMarksVerificationForm"
													property="subjectTypeList">
													<html:optionsCollection property="subjectTypeList"
														name="ExamSecuredMarksVerificationForm" label="value"
														value="key" />
												</logic:notEmpty>

											</c:if>
										</html:option>
									</html:select></td>
								</tr>
								<tr>


								</tr>

								<tr>
										<td height="25" class="row-odd">
											<div align="right" id="et">Evaluator Type :</div>
										</td>
										<html:hidden styleId="typeExam" property="examType" />
										<td height="25" class="row-even" colspan="2"><html:select
											property="evaluatorType" styleClass="combo"
											styleId="evaluatorType"
											name="ExamSecuredMarksVerificationForm" style="width:120px">
											<html:option value="">
												<bean:message key="knowledgepro.admin.select" />
											</html:option>
											<c:set var="evaluatorTypeMap"
												value="${baseActionForm.collectionMap['optionMap']}" />
												<c:choose>
													<c:when test="${evMap != null}">
														<html:optionsCollection name="evMap" label="value"
															value="key" />
													</c:when>
												<c:otherwise>
													<c:if test="${evaluatorTypeMap != null}">
														<html:optionsCollection name="evaluatorTypeMap" label="value"
															value="key" />
													</c:if>	
												</c:otherwise>
												</c:choose>
										</html:select></td>
										
									
									<td height="25" class="row-odd">
									<div align="right" id="ast">Answer Script Type :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="answerScriptType" styleClass="combo"
										styleId="answerScriptType"
										name="ExamSecuredMarksVerificationForm" style="width:120px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if test="${retainValues=='retain'}">
											<logic:notEmpty name="ExamSecuredMarksVerificationForm"
												property="listAnswerScriptType">
												<html:optionsCollection property="listAnswerScriptType"
													name="ExamSecuredMarksVerificationForm" label="value"
													value="key" />
											</logic:notEmpty>

										</c:if>
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
							<td width="49%" height="35" align="right"><input
								name="Submit" type="submit" class="formbutton" value="Submit" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								name="Submit" type="reset" class="formbutton" value="Cancel"
								onclick=
	resetValue();;;;;;;;;;;;;;;;;;;;
/></td>
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
							<td width="20%" height="35" align="center"></td>
							<td width="20%" align="center"></td>


							<td width="20%" align="center"></td>
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
	var examtype = document.getElementById("typeExam").value;
	if (examtype == "Internal") {
		document.getElementById("evaluatorType").disabled = true;
		document.getElementById("answerScriptType").disabled = true;
	} else {
		document.getElementById("evaluatorType").disabled = false;
		document.getElementById("answerScriptType").disabled = false;
	}
	hook=false;
</script>
</html:form>
