<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>



<script type="text/javascript">
	function set(target) {
		document.getElementById("method").value = target;
	}
	function resetValue() {
		document.location.href = "ExamSecuredMarksEntry.do?method=initExamSecuredMarksEntry";

	}

	function getExamName(examType) {
		getExamNameByExamTypeWithoutCurrentExam("examMap", examType, "examName", updateExamName);
		if (examType == "Internal") {
			document.getElementById("evaluatorType").disabled = true;
			document.getElementById("answerScriptType").disabled = true;
			document.getElementById("validationAST").value = "no";
			document.getElementById("validationET").value = "no";
		} 
		if (examType == "Supplementary") {
			document.getElementById("showScheme").style.display = "block";
		}
		else{
			document.getElementById("showScheme").style.display = "none";
			
		}
		resetOption("subject");
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}

	function getSCodeName(sCName) {
		examId=document.getElementById("examName").value;
		getSubjectCodeName("subjectMap", sCName, "subject",
				updateToSubject,  parseInt(examId));
		
	}

	function updateToSubject(req) {
		var subco ="subco";
		updateOptionsFromMap(req, "subject", "- Select -");
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
		examId=document.getElementById("examName").value;
		getEvalvatorType(subject, subjectType,examId);
		get_answerScript_type(subject, subjectType,examId);
	}
	function getEvalvatorType(subject, subjectType,examId) {

		var args = "method=getEvaluatorTypeBySubject&subjectId=" + subject
				+ "&subjectType=" + subjectType+"&examName="+examId;
	
		var url = "AjaxRequest.do";
		requestOperation(url, args, updateEvaluator);
	}
	function get_answerScript_type(subject, subjectType,examId) {
		
		var args = "method=get_answerScript_type&subjectId=" + subject
				+ "&subjectType=" + subjectType+"&examName="+examId;

		var url = "AjaxRequest.do";
		requestOperation(url, args, updateAnswerScript);
	}
	function updateAnswerScript(req) {
		updateOptionsFromMap1(req, "answerScriptType", "- Select -","ast","answerScriptType");
	}
	function updateEvaluator(req) {
		updateOptionsFromMap1(req, "evaluatorType", "- Select -","et","evaluatorType");
		
	}
	function getSubjects(){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}
		else
		{
			sCName = document.getElementById("sCodeName_2").value;
		}
		getSubjectCodeName("subjectMap", sCName, "subject",
				updateToSubject,  null);
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



<html:form action="/ExamSecuredMarksEntry.do">


	<html:hidden property="formName" value="ExamSecuredMarksEntryForm"
		styleId="formName" />
	<html:hidden property="method" styleId="method" value="onSubmitGrid" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="validationAST" styleId="validationAST" />
	<html:hidden property="validationET" styleId="validationET" />
	<c:choose>
		<c:when test="${Operation=='view'}">
			<html:hidden property="method" styleId="method" value="add" />
			<html:hidden property="opView" value="View" />
		</c:when>
		<c:otherwise>

			<html:hidden property="method" styleId="method" value="onSubmitGrid" />
		</c:otherwise>
	</c:choose>


	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Student Marks Entry &gt;&gt;</span></span></td>

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

					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
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
									<input type="radio" name="examType" id="examType" value="Regular" checked="checked" onchange="getExamName(this.value)"/>Regular
		    						<script type="text/javascript">
										var examType = "<bean:write name='ExamSecuredMarksEntryForm' property='examType'/>";
										if(examType == "true") {
						                        document.getElementById("examType").checked = true;
										}	
									</script>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										onchange="getExamName(this.value)"></html:radio>
									Supplementary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Internal"
										onchange="getExamName(this.value)"></html:radio> Internal</div>
									</td>


								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									ExamName :</div>
									</td>

									<td class="row-even" colspan="2"><html:select
										name="ExamSecuredMarksEntryForm" property="examId"
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
													<c:if test="${examMap != null}">
														<html:optionsCollection name="examMap" label="value"
															value="key" />
													</c:if>
											</c:otherwise>
										</c:choose>
										<c:if test="${retainValues=='retain'}">
											<logic:notEmpty name="ExamSecuredMarksEntryForm"
												property="examNameList">
												<html:optionsCollection property="examNameList"
													name="ExamSecuredMarksEntryForm" label="value" value="key" />
											</logic:notEmpty>

										</c:if>
									</html:select></td>
									<td class="row-even" colspan="3"></td>

								</tr>

								<tr>

									<td height="25" colspan="8" class="row-even">
									<div align="left">
									
									<input type="radio" name="sCodeName" id="sCodeName_1" value="sCode" checked="checked" onclick="getSCodeName(this.value)" /> Subject Code
									 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                    		<input type="radio" name="sCodeName" id="sCodeName_2" value="sName" onclick="getSCodeName(this.value)"/> Subject Name
		    						<script type="text/javascript">
										var sCodeName = "<bean:write name='ExamSecuredMarksEntryForm' property='sCodeName'/>";
										if(sCodeName == "sCode") {
						                        document.getElementById("sCodeName_1").checked = true;
										}	
									</script>
									
									</div>
									</td>


								</tr>


								<tr>
									<td height="25" rowspan="2" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject
									:</div>
									</td>
									<td width="16%" height="12" class="row-even">&nbsp;</td>
									<td width="10%" rowspan="2" class="row-even">
									<div align="right"><a href="#"></a></div>
									</td>
									<td rowspan="2" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject
									Type :</div>
									</td>

									<td rowspan="2" class="row-even"><html:select
										property="subjectType" styleClass="combo"
										styleId="subjectType" name="ExamSecuredMarksEntryForm"
										style="width:90px">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
											<c:if
												test="${ExamSecuredMarksEntryForm.subject != null && ExamSecuredMarksEntryForm.subject != ''}">
												
												<c:set var="evaluatorTypeMap"
													value="${baseActionForm.collectionMap['optionMap']}" />
												<c:if test="${evaluatorTypeMap != null}">
													<html:optionsCollection name="evaluatorTypeMap"
														label="value" value="key" />
												</c:if>
											</c:if>
											<c:if test="${retainValues=='retain'}">
												<logic:notEmpty name="ExamSecuredMarksEntryForm"
													property="subjectTypeList">
													<html:optionsCollection property="subjectTypeList"
														name="ExamSecuredMarksEntryForm" label="value" value="key" />
												</logic:notEmpty>

											</c:if>
										</html:option>
									</html:select></td>
								</tr>
								<tr>

									<td height="12" class="row-even"><html:select
										property="subject" styleClass="combo" styleId="subject"
										name="ExamSecuredMarksEntryForm" style="width:450px"
										onchange="getSubjectsType(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamSecuredMarksEntryForm"
											property="subjectList">
											<html:optionsCollection property="subjectList"
												name="ExamSecuredMarksEntryForm" label="display" value="id" />
										</logic:notEmpty>

										<c:if test="${subco =='subco'}">

											<c:set var="subjectMap"
												value="${baseActionForm.collectionMap['optionMap']}" />
											<c:if test="${subjectMap != null}">
												<html:optionsCollection name="subjectMap" label="value"
													value="key" />

											</c:if>

										</c:if>
										<c:if test="${retainValues=='retain'}">
											<logic:notEmpty name="ExamSecuredMarksEntryForm"
												property="subjectList">
												<html:optionsCollection property="subjectList"
													name="ExamSecuredMarksEntryForm" label="display" value="id" />
											</logic:notEmpty>

										</c:if>
									</html:select></td>
								</tr>

								<tr>
									<td height="25" class="row-odd">
									<div align="right" id="et">Evaluator Type :</div>
									</td>
									<html:hidden styleId="typeExam" property="examType" />
									<td height="25" class="row-even" colspan="2"><html:select
										property="evaluatorType" styleClass="combo"
										styleId="evaluatorType" name="ExamSecuredMarksEntryForm"
										style="width:120px">
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
												<logic:notEmpty name="ExamSecuredMarksEntryForm"
													property="listEvaluatorType">
													<html:optionsCollection property="listEvaluatorType"
														name="ExamSecuredMarksEntryForm" label="value" value="key" />
												</logic:notEmpty>

											</c:if>
										</html:option>
									</html:select></td>
									<td height="25" class="row-odd">
									<div align="right" id="ast">Answer Script Type :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="answerScriptType" styleClass="combo"
										styleId="answerScriptType" name="ExamSecuredMarksEntryForm"
										style="width:120px">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
											<c:if
												test="${ExamSecuredMarksEntryForm.subject != null && ExamSecuredMarksEntryForm.subject != ''}">
												<c:set var="answerScriptType"
													value="${baseActionForm.collectionMap['answerScriptType']}" />
												<c:if test="${answerScriptType != null}">
													<html:optionsCollection name="answerScriptType"
														label="value" value="key" />
												</c:if>
											</c:if>
											<c:if test="${retainValues=='retain'}">
												<logic:notEmpty name="ExamSecuredMarksEntryForm"
													property="listAnswerScriptType">
													<html:optionsCollection property="listAnswerScriptType"
														name="ExamSecuredMarksEntryForm" label="value" value="key" />
												</logic:notEmpty>

											</c:if>
										</html:option>
									</html:select></td>
								</tr>
								<tr>
									<td width="13%" height="25" class="row-odd">
										<div align="right">Exam</div>
										</td>
										<td width="19%" height="25" class="row-even">
										<input type="radio" name="isPreviousExam" id="isPreviousExam_1" value="true" checked="checked"/> Previous
			                    		<input type="radio" name="isPreviousExam" id="isPreviousExam_2" value="false" /> Current
			    						<script type="text/javascript">
											var isPreviousExam = "<bean:write name='ExamSecuredMarksEntryForm' property='isPreviousExam'/>";
											if(isPreviousExam == "true") {
							                        document.getElementById("isPreviousExam_1").checked = true;
											}	
										</script>
		    						</td>
		    						 	<td>
		    						 	<div id = "showScheme">
		    						 	<table>
		    						 	<tr>
			    						<td height="25" class="row-odd"><div align="right">Scheme No</div></td>
						                <td class="row-even">
										<html:select property="schemeNo" styleId="schemeNo" styleClass="combo">
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
										</html:select>
										</td>
										</tr>
										</table>
										 </div>
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
								name="Submit" type="submit" class="formbutton" value="Add"
								onclick="set('add')" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								name="Submit" type="submit" class="formbutton" value="View"
								onclick=
	set('view');
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
							<td width="20%" height="35" align="center"><input
								name="Submit3" type="button" class="formbutton" value="SS - SS"
								onClick=
	location.href = 'ss_ss_secured.html';
style="background: green; color: #fff; visibility: hidden;"
								disabled /></td>
							<td width="20%" align="center"><input name="Button"
								type="button" class="formbutton" value="SS - AS"
								onClick=
	location.href = 'ss_as_secured.html';
style="background: green; color: #fff; visibility: hidden;"
								disabled /></td>


							<td width="20%" align="center"><input name="Submit5"
								type="button" class="formbutton" value="AS - AS"
								onClick=
	location.href = 'as_as_secured.html';
style="background: green; color: #fff; visibility: hidden;"
								disabled /></td>
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
	if (examType == "Supplementary") {
		document.getElementById("showScheme").style.display = "block";
	}
	else{
		document.getElementById("showScheme").style.display = "none";
		
	}
</script>
</html:form>

