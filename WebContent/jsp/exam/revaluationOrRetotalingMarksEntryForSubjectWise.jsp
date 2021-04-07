<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	
	
	function getExamName() {
		var year=document.getElementById("year").value;
		var examType="";
		if(document.getElementById("examId").checked){
			examType=document.getElementById("examId").value;
		}else if(document.getElementById("sup").checked){
			examType=document.getElementById("sup").value;
		}
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
		resetOption("subject");
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
	function getSCodeName(sCName) {
		examId=document.getElementById("examName").value;
		getSubjectFromRevaluationOrRetotalling("subjectMap", sCName, "subject",
				updateToSubject,  parseInt(examId));
		
	}

	function updateToSubject(req) {
		var subco ="subco";
		updateOptionsFromMap(req, "subject", "- Select -");
	}
	function getSubjectsType(subjectNo) {
		subjectType = document.getElementById("subjectType1").value;
		subject = document.getElementById("subject").value;
		examId=document.getElementById("examName").value;
		getEvalvatorType(subjectNo, subjectType,examId);
	}
	function getEvalvatorType(subject, subjectType,examId) {

		var args = "method=getEvaluatorTypeBySubject&subjectId=" + subject
				+ "&subjectType=" + subjectType+"&examName="+examId;
	
		var url = "AjaxRequest.do";
		requestOperation(url, args, updateEvaluator);
	}
	function updateEvaluator(req) {
		var examType=0;
		updateOptionsFromMap3(req, "evaluatorType", "- Select -","et","evaluatorType",examType);
	}
	
	function getSubjectsByExamName(examId){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}
		else
		{
			sCName = document.getElementById("sCodeName_2").value;
		}
		getSubjectFromRevaluationOrRetotalling("subjectMap", sCName, "subject",
				updateToSubject,  examId);
	}

	function cancelAction() {
		document.location.href = "RevaluationOrRetotalingForSubject.do?method=initRevaluationOrRetotalingMarksEntryForSubject";
	}

	function getcandidates() {
		 if(document.getElementById("revaluation").checked){
				document.getElementById("method").value="getCandidates";
				document.revaluationOrRetotalingMarksEntryForSubjectWiseForm.submit();
				
				}else if(document.getElementById("thirdevaluation").checked){
							document.getElementById("method").value="getCandidatesForThirdEvaluation";
							document.revaluationOrRetotalingMarksEntryForSubjectWiseForm.submit();
						}			
	}
	
	
</SCRIPT>



<html:form action="/RevaluationOrRetotalingForSubject">
	<html:hidden property="formName" value="revaluationOrRetotalingMarksEntryForSubjectWiseForm" styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="displayET" styleId="displayET"/>
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="validationET" styleId="validationET"/>
	<input type="hidden" id="typeExam" name="type" value='<bean:write name="revaluationOrRetotalingMarksEntryForSubjectWiseForm" property="examType"/>' />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Revaluation/Retotaling Marks Entry For Subject Wise &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Revaluation/Retotaling 
					Marks Entry For Subject Wise
					</td>
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
									<td height="25"  colspan="4" class="row-even">
									<div align="Center"><html:radio property="examType"
										styleId="examId" value="Regular"
										onclick="getExamName()"></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamName()"></html:radio>
									Supplementary</div>
									</td>
								</tr>
								<tr>
								 <td width="25%" height="25" class="row-odd" >
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.fee.academicyear.col"/></div>
									</td>
									<td width="25%" class="row-even" valign="top" >
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="revaluationOrRetotalingMarksEntryForSubjectWiseForm" property="academicYear"/>" />
									<html:select
										property="academicYear" styleId="year"
										styleClass="combo" onchange="getExamName()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td class="row-odd" width="25%" >
									<div align="right"><span class="Mandatory">*</span>
									ExamName :</div>
									</td>

									<td class="row-even" width="25%" ><html:select
										name="revaluationOrRetotalingMarksEntryForSubjectWiseForm" property="examId"
										styleId="examName" styleClass="combo" onchange="getSubjectsByExamName(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
											<logic:notEmpty name="revaluationOrRetotalingMarksEntryForSubjectWiseForm"
												property="examNameList">
												<html:optionsCollection property="examNameList"
													name="revaluationOrRetotalingMarksEntryForSubjectWiseForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" colspan="4" class="row-even">
									<div align="left">
									<html:radio property="displaySubType" styleId="sCodeName_1" value="sCode" onclick="getSCodeName(this.value)">Subject Code</html:radio>
									 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 <html:radio property="displaySubType" styleId="sCodeName_2" value="sName" onclick="getSCodeName(this.value)">Subject Name</html:radio>
									</div>
									</td>
								</tr>
								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject:</div>
									</td>
									<td width="25%" height="25" class="row-even"><html:select
										property="subjectId" styleClass="combo" styleId="subject"
										name="revaluationOrRetotalingMarksEntryForSubjectWiseForm" style="width:200px"
										onchange="getSubjectsType(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="revaluationOrRetotalingMarksEntryForSubjectWiseForm"
											property="subjectList">
											<html:optionsCollection property="subjectList"
												name="revaluationOrRetotalingMarksEntryForSubjectWiseForm" label="display" value="id" />
										</logic:notEmpty>
									</html:select></td>
									<td class="row-odd" width="25%">
									<div align="right"><span class="Mandatory">*</span>Subject Type :</div>
									</td>
									<td class="row-even" width="25%">
									 <input type="hidden" id="subType" value="<bean:write property="subjectType" name="revaluationOrRetotalingMarksEntryForSubjectWiseForm" />"/>
									<html:select
										property="subjectType" styleClass="combo"
										styleId="subjectType" name="revaluationOrRetotalingMarksEntryForSubjectWiseForm"
										style="width:90px" >
										
										<html:option value="T" styleId="subjectType1">Theory</html:option>
										</html:select></td>
								</tr>
								<tr>
									<td height="25"  colspan="4" class="row-even">
									<div align="Center"><html:radio property="revaluation"
										styleId="revaluation" value="Revaluation" ></html:radio>
										
									Revaluation/Retotaling &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="revaluation" 
										styleId="thirdevaluation" value="Thirdevaluation" ></html:radio>
										
									 Third Evaluation</div>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd" width="25%" colspan="1">
									<div align="right" id="et">Evaluator Type :</div>
									</td>
									<td height="25" class="row-even" colspan="3"><html:select
										property="evaluatorType" styleClass="combo"
										styleId="evaluatorType" name="revaluationOrRetotalingMarksEntryForSubjectWiseForm">
											<html:option value="">
												<bean:message key="knowledgepro.select" />
											</html:option>
												<logic:notEmpty name="revaluationOrRetotalingMarksEntryForSubjectWiseForm"
													property="evaluatorTypeMap">
													<html:optionsCollection property="evaluatorTypeMap"
														name="revaluationOrRetotalingMarksEntryForSubjectWiseForm" label="value" value="key" />
												</logic:notEmpty>
									</html:select></td>
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
							<td width="20%" height="35" align="center">
							<input type="button" class="formbutton" value="Submit"
								onclick="getcandidates()" />
							&nbsp;&nbsp;
							<input type="button" class="formbutton" value="Reset"
								onclick="cancelAction()" />
							</td>
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
	var showET = document.getElementById("displayET").value;
	if(showET.length != 0 && showET == "yes") {
		document.getElementById("et").style.display = "block";
		document.getElementById("evaluatorType").style.display = "block";
	}else{
		document.getElementById("et").style.display = "none";
		document.getElementById("evaluatorType").style.display = "none";
		
	}
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
	}
	var subjectType = document.getElementById("subjectType").value;
	if (subjectType != null && subjectType.length != 0) {
		document.getElementById("subjectType").value = subjectType;
	}
</script>
</html:form>

