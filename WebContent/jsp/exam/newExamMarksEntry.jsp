<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<title>:: CMS ::</title>
<SCRIPT>

	function getExamName(examType) {
		getExamNameByExamType("examMap", examType, "examNameId", updateExamName);

	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examNameId", "- Select -");
		updateCurrentExam(req, "examNameId");
		getCourse(document.getElementById("examNameId").value);
	}
	function getCourse(examName) {
		document.getElementById("scheme").value="";
		document.getElementById("sCodeName_1").value="";
		document.getElementById("sCodeName_2").value="";
		document.getElementById("subject").value="";
		document.getElementById("subjectType").value="";
		document.getElementById("section").value="";
		
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
		if (subjectNo != "") {
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
		var examType=0;
		if(document.getElementById("int").checked || document.getElementById("intRedo").checked){
			examType=1;
		}
		updateOptionsFromMap2(req, "answerScriptType", "- Select -","ast","answerScriptType",examType);
	}
	function updateEvaluator(req) {
		var examType=0;
		if(document.getElementById("int").checked || document.getElementById("intRedo").checked){
			examType=1;
		}
		updateOptionsFromMap2(req, "evaluatorType", "- Select -","et","evaluatorType",examType);
		
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
		}
	}
	function getSectionByScheme(schemeId) {
		document.getElementById("sCodeName_1").checked = true;
		var ScodeName=document.getElementById("sCodeName_1").value;
		getSCodeName(ScodeName);
		var courseId = document.getElementById("course").value;
		var examId =  document.getElementById("examNameId").value;
		getSectionByExamIdCourseIdSchemeId("sectionMap", courseId, schemeId,examId,
				"section", updateSection);
	}
	function updateSection(req) {
		updateOptionsFromMap(req, "section", "--Select--");
	}
	function displayEvaluAndAnswerScript(subjectType){
		var subjectId = document.getElementById("subject").value;
		var courseId = document.getElementById("course").value;
		var schemeId = document.getElementById("scheme").value;
		getAnswerScript(subjectId, subjectType, courseId, schemeId);
		getEvaluatorType(subjectId, subjectType, courseId, schemeId);
	}
	function getExamsByExamTypeAndYear() {
		document.getElementById("scheme").value="";
		document.getElementById("sCodeName_1").value="";
		document.getElementById("sCodeName_2").value="";
		document.getElementById("subject").value="";
		document.getElementById("subjectType").value="";
		document.getElementById("section").value="";
		
		if(document.getElementById("examId").checked==true)
			var examType=document.getElementById("examId").value;
		 else if(document.getElementById("sup").checked==true)
			var examType=document.getElementById("sup").value;
		if(document.getElementById("int").checked==true)
			var examType=document.getElementById("int").value;
		else if(document.getElementById("intRedo").checked==true)
			var examType=document.getElementById("intRedo").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examNameId", updateExamName);
	}

	function getSCodeName(sCName) {

		var courseId = document.getElementById("course").value;
		getAnswerScript("", "", courseId, schemeId);
		getEvaluatorType("", "", courseId, schemeId);
		var examId = document.getElementById("examNameId").value;
		var schemeId = document.getElementById("scheme").value;
		getSubjectsCodeNameByCourseSchemeExamId("schemeMap", sCName, courseId, "subject",
				updateToSubject, schemeId,examId);
	}
	function updateToSubject(req) {
		var subco ="subco";
		updateOptionsFromMap(req, "subject", "- Select -");
	}
	
</SCRIPT>

</head>


<html:form action="/marksEntry" method="POST" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="getCandidates" />
	<html:hidden property="formName" value="newExamMarksEntryForm" styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="validationAST" styleId="validationAST" />
	<html:hidden property="validationET" styleId="validationET"/>
	<html:hidden property="displayAST" styleId="displayAST"/>
	<html:hidden property="displayET" styleId="displayET"/>
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
										<%  boolean disableInternal=false; 
											boolean disableRegular=false;
										%>
										<c:if test="${newExamMarksEntryForm.regular==false && newExamMarksEntryForm.internal==true}">
											<%   disableInternal=false; 
												 disableRegular=true;
											%>
										</c:if>
										<c:if test="${newExamMarksEntryForm.regular==true && newExamMarksEntryForm.internal==false}">
											<%   disableInternal=true; 
												 disableRegular=false;
											%>
										</c:if>
										<table width="100%">
											<tr>
												<td width="12.5%"></td>
												<td width="12.5%"></td>
												<td align="center" class="row-even" width="12.5%">
													<html:radio property="examType"
																styleId="examId" 
																value="Regular"
																onclick="getExamsByExamTypeAndYear(), populateOptionForSup(false)" 
																disabled="<%=disableRegular %>"></html:radio> Regular
												</td>
												<td align="center" class="row-even" width="20%">
													<html:radio property="examType" 
																value="Supplementary"
																styleId="sup"
																onclick="getExamsByExamTypeAndYear(), populateOptionForSup(true)" 
																disabled="<%=disableRegular %>"></html:radio> Supplementary
												</td>
												<td align="center" class="row-even" width="10%">
													<html:radio property="examType" 
																value="Internal" 
																styleId="int"
																onclick="getExamsByExamTypeAndYear(), populateOptionForSup(false)" 
																disabled="<%=disableInternal %>"></html:radio> Internal
												</td>
												<td align="center" class="row-even" width="15%">
													<html:radio property="examType" 
																value="5" 
																styleId="intRedo"
																onclick="getExamsByExamTypeAndYear(), populateOptionForSup(false)" 
																disabled="true"></html:radio> Internal Redo
												</td>
												<td width="10%"></td>
												<td width="10%"></td>
											</tr>
										</table>
									</td>

								</tr>
								<tr>
								     <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="newExamMarksEntryForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> Exam
									Name :</div>
									</td>

									<td class="row-even"><html:select property="examId"
										styleClass="combo" styleId="examNameId"
										name="newExamMarksEntryForm" onchange="getCourse(this.value)"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm"
											property="examNameList">
											<html:optionsCollection property="examNameList"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>


									<td width="23%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Course:</div>
									</td>
									<td width="23%" height="25" class="row-even"><html:select
										property="courseId" styleClass="body" styleId="course"
										onchange="getScheme(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm"
											property="courseMap">
											<html:optionsCollection property="courseMap"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>

									<td width="28%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									Scheme:</div>
									</td>
								<!--  	<td width="26%" class="row-even"><html:select
										property="schemeNo" styleClass="body" styleId="scheme"
										onchange="getSubject(this.value), getSectionByScheme(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm"
											property="schemeMap">
											<html:optionsCollection property="schemeMap"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td> -->
									<td width="26%" class="row-even"><html:select
										property="schemeNo" styleClass="body" styleId="scheme"
										onchange="getSectionByScheme(this.value)" > 
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm"
											property="schemeMap">
											<html:optionsCollection property="schemeMap"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
								<td height="25" colspan="2" class="row-even">
									<div align="center">
									<html:radio property="displaySubType" styleId="sCodeName_1" value="sCode" onchange="getSCodeName(this.value)">Subject Code</html:radio>
									 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 <html:radio property="displaySubType" styleId="sCodeName_2" value="sName" onchange="getSCodeName(this.value)">Subject Name</html:radio>
									</div>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>Subject
									:</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="subjectId" styleClass="body" styleId="subject"
										onchange="getSubjectType(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
									<!--  	<logic:notEmpty name="newExamMarksEntryForm"
											property="subjectMap">
											<html:optionsCollection property="subjectMap"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>-->
										<%--<logic:notEmpty name="newExamMarksEntryForm"
											property="subjectList">
											<html:optionsCollection property="setSubjectCodeNameMap"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>--%>
										<logic:notEmpty name="newExamMarksEntryForm"
											property="subjectCodeNameMap">
											<html:optionsCollection property="subjectCodeNameMap"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									<td height="25" class="row-odd">
									<div align="right">Subject Type:</div>
									</td>

									<td height="25" class="row-even"><html:select
										property="subjectType" styleId="subjectType" onchange="displayEvaluAndAnswerScript(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm"
											property="subjectTypeMap">
											<html:optionsCollection property="subjectTypeMap"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right" id="et"><span class="Mandatory">*</span>Evaluator
									Type :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="evaluatorType" styleClass="combo"
										styleId="evaluatorType" name="newExamMarksEntryForm"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm"
											property="evaluatorList">
											<html:optionsCollection property="evaluatorList"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>

									<td height="25" class="row-odd">
									<div align="right" id="ast"><span class="Mandatory">*</span>Answer
									Script Type :</div>
									</td>

									<td height="25" class="row-even" colspan="1"><html:select
										property="answerScriptType" styleClass="combo"
										styleId="answerScriptType" name="newExamMarksEntryForm"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm" property="answerScriptTypeList">
											<html:optionsCollection property="answerScriptTypeList"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>

								</tr>
								<tr>
									<td class="row-odd">
									
									</td>
									<td class="row-even"></td>
									<td width="22%" class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="admissionForm.studentinfo.castcatg.label" /></div>
									</td>
									<td width="34%" class="row-even"><html:select
										property="section" styleClass="combo" styleId="section">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm" property="sectionMap">
										<html:optionsCollection name="newExamMarksEntryForm" property="sectionMap" label="value" value="key" />
										</logic:notEmpty>
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="49%" height="35" align="right"><input
								name="Submit7" type="submit" class="formbutton" value="Submit" /></td>

							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left">
								<html:reset styleClass="formbutton"></html:reset>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
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
<script type="text/javascript">
var year = getExamsByExamTypeAndYear();
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
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>