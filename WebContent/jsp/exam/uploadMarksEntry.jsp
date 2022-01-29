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
		document.location.href = "uploadMarksEntry.do?method=initMarksUpload";
	}

	function getExamName(examType) {
		getExamNameByExamType("examMap", examType, "examName", updateExamName);

	}
	/*function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
	}*/
	function getProgram(examName) {
		getProgramsByExamName("programMap", examName, "programId", updatePrograms);

	}
	function updatePrograms(req) {
		updateOptionsFromMap(req, "programId", "- Select -");

	}

	function getCourses(programId)
	{
		getCoursesByProgram("courseMap",programId,"courseId",updateCourses);
	}

	function updateCourses(req)
	{
		updateOptionsFromMap(req, "courseId", "- Select -");
	}	
	function getScheme(courseId) {
		examId = document.getElementById("examName").value;
		getSchemeNoByExamIdCourseId("schemeMap", examId, courseId, "schemeId",
				updateToScheme);

	}
	function updateToScheme(req) {
		updateOptionsFromMap(req, "schemeId", "- Select -");

	}
	function getSubject(schemeId) {
		var courseId = document.getElementById("courseId").value;
		var examId = document.getElementById("examName").value;
		getSubjectsByCourseSchemeExamId("subjectMap", courseId, "subjectId",
				updateToSubject, schemeId,examId);
	}

	function updateToSubject(req) {
		updateOptionsFromMap(req, "subjectId", "- Select -");
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
		
	}
	/*function getExamsByExamTypeAndYear() {
		var examType=document.getElementById("examType").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
	}*/
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}

//mary code......................................................................
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
	function displayEvaluAndAnswerScript(subjectType){
		var subjectId = document.getElementById("subject").value;
		var courseId = document.getElementById("course").value;
		var schemeId = document.getElementById("scheme").value;
		getAnswerScript(subjectId, subjectType, courseId, schemeId);
		getEvaluatorType(subjectId, subjectType, courseId, schemeId);
	}
	function getExamsByExamTypeAndYear() {
		if(document.getElementById("reg").checked==true)
		var examType=document.getElementById("reg").value;
		else if(document.getElementById("sup").checked==true)
			var examType=document.getElementById("sup").value;
		else if(document.getElementById("int").checked==true)
			var examType=document.getElementById("int").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
	}
	function getExamName(examType) {
		getExamNameByExamType("examMap", examType, "examNameId", updateExamName);

	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
		getCourse(document.getElementById("examName").value);
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

	
</SCRIPT>


<html:form action="/uploadMarksEntry.do" method="POST" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="uploadMarks" />
	<html:hidden property="formName" value="uploadMarksEntryForm" styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="validateMsg" styleId="validateMsg" name="uploadMarksEntryForm"/>
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam	&gt;&gt; Upload Marks &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
						<td background="images/Tcenter.gif" class="heading_white">Upload Marks</td>
						<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
							<div id="errorMessage">
								<FONT color="red"><html:errors /></FONT>
								<FONT color="green">
									<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages>
								</FONT>
							</div>
							<div align="right" class="mandatoryfield">
								<bean:message key="knowledgepro.mandatoryfields" />
							</div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
									<%  boolean disableInternal=false; 
										boolean disableRegular=false;
									%>
									<c:if test="${uploadMarksEntryForm.regular==false && uploadMarksEntryForm.internal==true}">
										<%   disableInternal=false; 
											 disableRegular=true;
										%>
									</c:if>
									<c:if test="${uploadMarksEntryForm.regular==true && uploadMarksEntryForm.internal==false}">
										<%   disableInternal=true; 
											 disableRegular=false;
										%>
									</c:if>
									<html:radio property="examType"
										styleId="reg" value="Regular"
										onclick="getExamsByExamTypeAndYear(), populateOptionForSup(false)" disabled="<%=disableRegular %>"></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamsByExamTypeAndYear(), populateOptionForSup(true)" disabled="<%=disableRegular %>"></html:radio>
									Supplementary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Internal" styleId="int"
										onclick="getExamsByExamTypeAndYear(), populateOptionForSup(false)" disabled="<%=disableInternal %>"></html:radio>
									Internal
									
									</div>
									</td>

					</tr>
					<tr>
							<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="uploadMarksEntryForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
												<td class="row-odd">
												</td>
												<td class="row-even" >
												</td>
											</tr>
											<tr>
												<td class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowldgepro.exam.upload.marks.examname"/> :</div>
												</td>
												<td class="row-even">
													<html:select property="examId" styleClass="comboLarge" styleId="examName" name="uploadMarksEntryForm" onchange="getProgram(this.value)" style="width:200px">
														<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
														<logic:notEmpty name="uploadMarksEntryForm" property="listExamName">
															<html:optionsCollection property="listExamName" name="uploadMarksEntryForm" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
												<td height="25" class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowldgepro.exam.upload.marks.Program"/> :</div>
												</td>
												<td height="25" class="row-even">
													<html:select property="programId" styleId="programId" style="width:200px" onchange="getCourses(this.value);">
														<html:option value="">Select</html:option>
														<logic:notEmpty name="uploadMarksEntryForm" property="programList">
															<html:optionsCollection property="programList" name="uploadMarksEntryForm" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowldgepro.exam.upload.marks.Course"/> :</div>
												</td>
												<td height="25" class="row-even">
													<html:select property="courseId" styleId="courseId" style="width:200px" onchange="getScheme(this.value);">
														<html:option value="">Select</html:option>
														<logic:notEmpty name="uploadMarksEntryForm" property="courseList">
															<html:optionsCollection property="courseList" name="uploadMarksEntryForm" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
												<td height="25" class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowldgepro.exam.upload.marks.Scheme"/> :</div>
												</td>
												<td height="25" class="row-even">
													<html:select property="schemeId" styleId="schemeId" style="width:200px" onchange="getSubject(this.value);">
														<html:option value="">Select</html:option>
														<logic:notEmpty name="uploadMarksEntryForm" property="schemeList">
															<html:optionsCollection property="schemeList" name="uploadMarksEntryForm" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
											</tr>
											<tr>
												<td width="23%" height="25" class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowldgepro.exam.upload.marks.subject"/>:</div>
												</td>
												<td width="23%" height="25" class="row-even">
													<html:select property="subjectId" styleClass="combo" styleId="subjectId" style="width:200px" onchange="getSubjectsType(this.value);">
														<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
														<logic:notEmpty name="uploadMarksEntryForm" property="subjectList">
															<html:optionsCollection property="subjectList" name="uploadMarksEntryForm" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
												<td width="28%" class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowldgepro.exam.upload.marks.subjetcType"/> :</div>
												</td>
						                		<td class="row-even">
						                			<html:select property="subjectType" styleClass="combo" styleId="subjectType" style="width:200px" onchange="displayEvaluAndAnswerScript(this.value)">
														<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
														<logic:notEmpty name="uploadMarksEntryForm" property="subjectTypeList">
															<html:optionsCollection property="subjectTypeList" name="uploadMarksEntryForm" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
											</tr>
											<tr>
												<td width="28%" class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowldgepro.exam.upload.marks.isPrevious"/> :</div>
												</td>
						                		<td class="row-even">
						                			<html:radio property="isPrevious" value="Yes" name="uploadMarksEntryForm">Yes</html:radio>
						                			<html:radio property="isPrevious" value="No" name="uploadMarksEntryForm">No</html:radio>
												</td>
												<td height="25" class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowldgepro.exam.upload.marks.file"/> :</div>
												</td>
												<td height="25" class="row-even" colspan="1">
													<html:file property="uploadedFile"></html:file>
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
										<input name="Submit7" type="submit" class="formbutton" value="Submit" />
									</td>
									<td width="2%" height="35" align="center">&nbsp;</td>
									<td width="49%" height="35" align="left">
										<input name="Submit8" type="button" class="formbutton" value="Cancel" onclick="goToFirstPage()" />
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
<script language="JavaScript" >
	var msg=document.getElementById("validateMsg").value;
	if(msg!=null && msg != ""){
		var deleteConfirm = confirm(msg);
		if (deleteConfirm) {
			document.getElementById("method").value="saveMarks";
			document.uploadMarksEntryForm.submit();
		}
	}
	
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
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

