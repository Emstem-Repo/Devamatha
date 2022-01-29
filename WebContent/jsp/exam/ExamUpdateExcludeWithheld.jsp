
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<title>:: CMS ::</title>
<script type="text/javascript" language="javascript">
	var i=false;

	function changeVar(){
		if(document.getElementById("oldScheme").value!=''){
		i=true;
		}else{
			i=false;
			}
		}

	// Functions for AJAX  first Method
	function getCourses(examName) {
		getCourseByExamName("coursesMap", examName, "courseId", updateCourses);
	}
	function updateCourses(req) {
		updateOptionsFromMap(req, "courseId", "--Select--");
	}

	//for second method
	
	function getScheme(courseId) {
		getSchemeNoByCourseId("schemeMap", courseId, "schemeNo", updateScheme);
		getSchemeNoByCourseId("schemeMap", courseId, "oldScheme", updateScheme);
	}
	
	function updateScheme(req) {
		updateOptionsFromMap(req, "schemeNo", "--Select--");
		updateOptionsFromMap(req, "oldScheme", "--Select--");
	}
	
	function getNames() {
		document.getElementById("examNameId_value").value = document
				.getElementById("examNameId").options[document.getElementById("examNameId").selectedIndex].text;
		
		document.getElementById("courseName").value = document
				.getElementById("courseId").options[document
				.getElementById("courseId").selectedIndex].text;
		
		document.getElementById("schemeName").value = document
		.getElementById("schemeNo").options[document
		.getElementById("schemeNo").selectedIndex].text;
	}

	
	function resetMessages() {
		
		document.location.href = "ExamUpdateExcludeWithheld.do?method=init";
		resetErrMsgs();
	}

	function assignWithheldScheme(schemeNo){
		if(i==false){
			document.getElementById("oldScheme").value = schemeNo; 
		}
	}
	function getExamsByExamTypeAndYear() {
		var examType=document.getElementById("examType").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examNameId", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examNameId", "- Select -");
		updateCurrentExam(req, "examNameId");
	}
</script>

<html:form action="/ExamUpdateExcludeWithheld.do">

	<html:hidden property="method" styleId="method"
		value="getUpdateExcludeWithheld" />
	<html:hidden property="formName" value="ExamUpdateExcludeWithheldForm" />
	<html:hidden property="pageType" value="1" />


	<html:hidden property="examNameId_value" styleId="examNameId_value" />
	<html:hidden property="courseName" styleId="courseName" />
	<html:hidden property="schemeName" styleId="schemeName" />


	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.UpdateExcludeWithheld" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">

			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.UpdateExcludeWithheld" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">

					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6">
							<div align="right" style="color: red"><span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT>
						</tr>

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
									<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="ExamUpdateExcludeWithheldForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
							<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examType" /> :</div></td>
								<td height="25" class="row-even"><html:select
										property="examType" styleClass="combo"  styleId="examType" onchange="getExamsByExamTypeAndYear()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="examTypeList"
											name="ExamUpdateExcludeWithheldForm">
											<html:optionsCollection property="examTypeList"
												name="ExamUpdateExcludeWithheldForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									</tr>
								<tr>
									<td height="25" class="row-odd" width="25%">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.UpdateExcludeWithheld.examName" /> :</div>
									</td>
									<td class="row-even" width="25%"><html:select
										property="examNameId" styleClass="combo" styleId="examNameId"
										name="ExamUpdateExcludeWithheldForm"
										onchange="getCourses(this.value)" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamUpdateExcludeWithheldForm"
											property="examNameMap">
											<html:optionsCollection property="examNameMap"
												name="ExamUpdateExcludeWithheldForm" label="value"
												value="key" />
										</logic:notEmpty>
									</html:select></td>

									<td width="25%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.course" /> :</div>
									</td>
									<td width="25%" class="row-even"><span class="row-even">
									<html:select name="ExamUpdateExcludeWithheldForm"
										property="courseId" styleId="courseId" styleClass="combo"
										onchange="getScheme(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${ExamSplSubGroupOperation == 'edit'}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${ExamUpdateExcludeWithheldForm.academicYear != null && ExamUpdateExcludeWithheldForm.academicYear != ''}">
													<c:set var="courseMap"
														value="${baseActionForm.collectionMap['coursesMap']}" />
													<c:if test="${courseMap != null}">
														<html:optionsCollection name="courseMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>

									</html:select> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd" align="right" width="25%">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.scheme.col" /></div>
									</td>
									<td width="25%" class="row-even" ><html:select
										name="ExamUpdateExcludeWithheldForm" property="schemeNo"
										styleId="schemeNo" styleClass="combo" onchange="assignWithheldScheme(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${ExamSplSubGroupOperation == 'edit'}">
												<html:optionsCollection name="schemeMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${ExamUpdateExcludeWithheldForm.courseId != null && ExamUpdateExcludeWithheldForm.courseId != ''}">
													<c:set var="schemeMap"
														value="${baseActionForm.collectionMap['schemeMap']}" />
													<c:if test="${schemeMap != null}">
														<html:optionsCollection name="schemeMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>
									<td height="25" class="row-odd" align="right" width="25%">
									<div align="right"><span class="Mandatory">*</span>Exclude/With held for Scheme:</div>
									</td>
									<td width="25%" class="row-even" ><html:select
										name="ExamUpdateExcludeWithheldForm" property="oldScheme"
										styleId="oldScheme" styleClass="combo" onchange="changeVar()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${ExamSplSubGroupOperation == 'edit'}">
												<html:optionsCollection name="schemeMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${ExamUpdateExcludeWithheldForm.courseId != null && ExamUpdateExcludeWithheldForm.courseId != ''}">
													<c:set var="schemeMap"
														value="${baseActionForm.collectionMap['schemeMap']}" />
													<c:if test="${schemeMap != null}">
														<html:optionsCollection name="schemeMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>





								</tr>
							</table>
							</td>
							<td width="5" height="54" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>


					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>


				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleId="submitButton"
								styleClass="formbutton" value="Submit" /></div>
							</td>
							<td width="2%"></td>
							<td width="40%"><input type="Reset" class="formbutton"
								value="Reset" onclick="resetMessages()"/></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>
