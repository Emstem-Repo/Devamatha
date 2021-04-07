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
	function getView() {
		document.getElementById("method").value = "viewSecuredMarks";
		document.newSecuredMarksVerficationForm.submit();
	}
	function resetValue() {
		document.location.href = "securedMarksEntry.do?method=initSecuredMarksEntry";
	}
	function getExamName() {
		var year=document.getElementById("year").value;
		var examType="";
		if(document.getElementById("examId").checked){
			examType=document.getElementById("examId").value;
		}else if(document.getElementById("sup").checked){
			examType=document.getElementById("sup").value;
		}else if(document.getElementById("int").checked){
			examType=document.getElementById("int").value;
		} 
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
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
		var examType=0;
		if(document.getElementById("int").checked){
			examType=1;
		}
		updateOptionsFromMap2(req, "answerScriptType", "- Select -","ast","answerScriptType",examType);
	}
	function updateEvaluator(req) {
		var examType=0;
		if(document.getElementById("int").checked){
			examType=1;
		}
		updateOptionsFromMap2(req, "evaluatorType", "- Select -","et","evaluatorType",examType);
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
	/*function getSubjectsByExamName(examId){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}
		else
		{
			sCName = document.getElementById("sCodeName_2").value;
		}
		getSubjectCodeName("subjectMap", sCName, "subject",
				updateToSubject,  examId);
	}*/
	function getSubjectsByExamName(examId){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}else{
			sCName = document.getElementById("sCodeName_2").value;
		}
		var examType="";
		if(document.getElementById("examId").checked){
			examType=document.getElementById("examId").value;
		}else if(document.getElementById("sup").checked){
			examType=document.getElementById("sup").value;
		}else if(document.getElementById("int").checked){
			examType=document.getElementById("int").value;
		} 
		getSubjectCodeNameYearWise(examType,examId,document.getElementById("year").value, sCName, "subject", updateToSubject);
	}
	function cancelAction() {
		document.location.href = "securedMarksVerification.do?method=initSecuredMarksVerification";
	}	
	function getCheck(checkBox1) {
		if (document.getElementById("check").checked) {
			document.getElementById("check").value = "on";
		} else {
			document.getElementById("check").value = "off";
		}

	}
	function displayEvaluAndAnswerScript(subjectType){
		var subject = document.getElementById("subject").value;
		var exam=document.getElementById("examName").value;
		getEvalvatorType(subject, subjectType,exam);
		get_answerScript_type(subject, subjectType,exam);
	}	
</SCRIPT>



<html:form action="/securedMarksVerification">
	<html:hidden property="formName" value="newSecuredMarksVerficationForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="getCandidates" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="validationAST" styleId="validationAST" />
	<html:hidden property="validationET" styleId="validationET"/>
	<html:hidden property="displayAST" styleId="displayAST"/>
	<html:hidden property="displayET" styleId="displayET"/>
	<input type="hidden" id="typeExam" name="type" value='<bean:write name="newSecuredMarksVerficationForm" property="examType"/>' />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Secured Marks Verification &gt;&gt;</span></span></td>
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

									<td height="25" colspan="4" class="row-even">
									<div align="Center"><html:radio property="examType"
										styleId="examId" value="Regular"
										onclick="getExamName()"></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamName()"></html:radio>
									Supplementary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Internal" styleId="int"
										onclick="getExamName()"></html:radio>
									Internal</div>
									</td>
								</tr>
								<tr>
								 <td height="25" class="row-odd" colspan="1">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.fee.academicyear.col"/>:</div>
									</td>
									<td class="row-even" valign="top" colspan="3">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="newSecuredMarksVerficationForm" property="academicYear"/>" />
									<html:select
										property="academicYear" styleId="year"
										styleClass="combo" onchange="getExamName()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									ExamName :</div>
									</td>

									<td class="row-even" ><html:select
										name="newSecuredMarksVerficationForm" property="examId"
										styleId="examName" styleClass="combo" onchange="getSubjectsByExamName(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
											<logic:notEmpty name="newSecuredMarksVerficationForm"
												property="examNameList">
												<html:optionsCollection property="examNameList"
													name="newSecuredMarksVerficationForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select></td>
									<td class="row-odd">
									<div align="right">IS Secured:</div>
									</td>
									<td class="row-even"> 
										
										<html:radio name="newSecuredMarksVerficationForm" property="checkBox" styleId="check" value="yes">Yes</html:radio>
										<html:radio name="newSecuredMarksVerficationForm" property="checkBox" styleId="check_1" value="no">No</html:radio>
									</td>									
								</tr>
								<tr>
									<td height="25" colspan="4" class="row-even">
									<div align="left">
									<html:radio property="displaySubType" styleId="sCodeName_1" value="sCode" onclick="getSCodeName(this.value)">Subject Code</html:radio>
									 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 <html:radio property="displaySubType" styleId="sCodeName_2" value="sName" onclick="getSCodeName(this.value)">Subject Name</html:radio>
									</div>
									</td>
									<td colspan="2">
		    						 	<div id = "showScheme">
		    						 	<table width="100%" height="100%">
		    						 	<tr>
			    						<td height="25" class="row-odd"><div align="right">Scheme No</div></td>
						                <td class="row-even">
										<html:select property="schemeNo" styleId="schemeNo" styleClass="combo">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
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
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject:</div>
									</td>
									<td height="12" class="row-even"><html:select
										property="subjectId" styleClass="combo" styleId="subject"
										name="newSecuredMarksVerficationForm" style="width:450px"
										onchange="getSubjectsType(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="newSecuredMarksVerficationForm"
											property="subjectMap">
											<html:optionsCollection property="subjectMap"
												name="newSecuredMarksVerficationForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject Type :</div>
									</td>
									<td class="row-even"><html:select
										property="subjectType" styleClass="combo"
										styleId="subjectType" name="newSecuredMarksVerficationForm"
										style="width:90px" onchange="displayEvaluAndAnswerScript(this.value)">
										<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
									<logic:notEmpty name="newSecuredMarksVerficationForm"	property="subjectTypeList">
										<html:optionsCollection property="subjectTypeList" name="newSecuredMarksVerficationForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right" id="et">Evaluator Type :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="evaluatorType" styleClass="combo"
										styleId="evaluatorType" name="newSecuredMarksVerficationForm"
										style="width:120px">
											<html:option value="">
												<bean:message key="knowledgepro.select" />
											</html:option>
												<logic:notEmpty name="newSecuredMarksVerficationForm"
													property="evaluatorTypeMap">
													<html:optionsCollection property="evaluatorTypeMap"
														name="newSecuredMarksVerficationForm" label="value" value="key" />
												</logic:notEmpty>
									</html:select></td>
									<td height="25" class="row-odd">
									<div align="right" id="ast">Answer Script Type :</div>
									</td>
									<td height="25" class="row-even">
									<html:select property="answerScriptType" styleClass="combo" styleId="answerScriptType" name="newSecuredMarksVerficationForm"
										style="width:120px">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
												<logic:notEmpty name="newSecuredMarksVerficationForm"
													property="answerScriptTypeMap">
													<html:optionsCollection property="answerScriptTypeMap"
														name="newSecuredMarksVerficationForm" label="value" value="key" />
												</logic:notEmpty>
										</html:option>
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
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						<tr> 
							<td width="20%" height="35" align="center">
							<html:submit value="Add" styleClass="formbutton"></html:submit>
							&nbsp;&nbsp;
							<input type="button" class="formbutton" value="Reset"
								onclick="cancelAction()" />
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
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
	if (examtype == "Supplementary") {
		document.getElementById("showScheme").style.display = "block";
	}else{
		document.getElementById("showScheme").style.display = "none";
	}
	if (examtype == "Internal") {
		document.getElementById("evaluatorType").disabled = true;
		document.getElementById("answerScriptType").disabled = true;
	} else {
		document.getElementById("evaluatorType").disabled = false;
		document.getElementById("answerScriptType").disabled = false;
	}
	var showET = document.getElementById("displayET").value;
	if(showET.length != 0 && showET == "yes") {
		document.getElementById("et").style.display = "block";
		document.getElementById("evaluatorType").style.display = "block";
	}else{
		document.getElementById("et").style.display = "none";
		document.getElementById("evaluatorType").style.display = "none";
		
	}
	var showAT = document.getElementById("displayAST").value;
	if(showAT.length != 0 && showAT == "yes") {
		document.getElementById("ast").style.display = "block";
		document.getElementById("answerScriptType").style.display = "block";
	}else{
		document.getElementById("ast").style.display = "none";
		document.getElementById("answerScriptType").style.display = "none";
	}
</script>
</html:form>