<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<script type="text/javascript">
function closeWindow(){
	document.getElementById("method").value="initExamDefinition";
	document.ExamDefinitionForm.submit();
	
}

	function getInternalExamMap(academicYear) {

		getInternalExamByAcademicYear("mapInternalExam", academicYear,
				"internalExamId", updateInternalExam);
	}
	function updateInternalExam(req) {
		updateOptionsFromMapMultiselect(req, "internalExamId", "- Select -");
	}
	function ale() {
		if (document.getElementById("checkedActive").checked) {
			document.getElementById("checkedActive").value = "true";
		} else {
			document.getElementById("checkedActive").value = null;
		}
	}

	function checkCheckBox(odd_even) {
		var courseCount = document.getElementById("courseCount").value;
		var schemeCount = 0;
		for ( var i = 0; i < courseCount; i++) {
			schemeCount = document.getElementById("scheme_" + i).value;
			for ( var j = 0; j < schemeCount; j++) {
				if (odd_even == "Even") {
					if (j % 2 != 0) {
						document.getElementById("checkbox_" + i + "_" + j).checked = true;
					} else {
						document.getElementById("checkbox_" + i + "_" + j).checked = false;
					}
				} else if (odd_even == "Odd") {
					if (j % 2 == 0) {
						document.getElementById("checkbox_" + i + "_" + j).checked = true;
					} else {
						document.getElementById("checkbox_" + i + "_" + j).checked = false;
					}
				}else if (odd_even == "Both") {
					document.getElementById("checkbox_" + i + "_" + j).checked = true;
				}
				 else {
					document.getElementById("checkbox_" + i + "_" + j).checked = false;
				}
			}
		}
	}
	function getChkBoxValues() {
		var finalValue = "";
		var unselectedCheckBox="";
		var courseCount = document.getElementById("courseCount").value;
		var schemeCount = 0;
		var courseScheme = "";
		for ( var i = 0; i < courseCount; i++) 
		{
			schemeCount = document.getElementById("scheme_" + i).value;
			for ( var j = 0; j < schemeCount; j++) 
			{
				if (document.getElementById("checkbox_" + i + "_" + j).checked == true)
				{
					courseScheme = document.getElementById("hidden_" + i + "_"+ j).value;
					finalValue = finalValue + courseScheme + ",";
				}
				else
				{
					courseScheme = document.getElementById("hidden_" + i + "_"+ j).value;
					unselectedCheckBox = unselectedCheckBox + courseScheme + ",";
				}	
			}
		}
		finalValue = finalValue.replace(/\s/g, "");
		unselectedCheckBox=unselectedCheckBox.replace(/\s/g,"");
		document.getElementById("courseSchemeSelected").value = finalValue;
		document.getElementById("courseSchemeUnSelected").value = unselectedCheckBox;
	}

	function reset() {
		resetErrMsgs();
	}
	function resetMessages() {
		document.getElementById("year").value = document.getElementById("yr").value;

		document.getElementById("month").value = document
				.getElementById("month1").value;

		document.getElementById("academicYear").value = document
				.getElementById("ay").value;
		document.getElementById("joiningBatchYear").value = document
				.getElementById("jby").value;

		document.getElementById("examTypeId").value = document
				.getElementById("origExamTypeId").value;
		document.getElementById("examName").value = document
				.getElementById("origExamName").value;
		document.getElementById("examCode").value = document
				.getElementById("origExamCode").value;
		resetErrMsgs();
	}
	function showRegularDropDown(examType) {
		var academicYear = document.getElementById("academicYear").value;
		if(examType == '5') {	//	only for redo
			document.getElementById("regularExamsRow").style.display = "table-row";
			getExamNameByExamTypeAndYear("regularExams", "1", academicYear, "regularExamIdForRedo", updateExamName, null);	//	fetch only regular
		}
		else {
			document.getElementById("regularExamsRow").style.display = "none";
		}
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "regularExamIdForRedo", "- Select -");
	}
</script>

<html:form action="ExamDefinition2.do">
	<html:hidden property="pageType" value="2" />
	<html:hidden property="formName" value="ExamDefinitionForm" />
	<html:hidden property="courseSchemeSelected" styleId="courseSchemeSelected" />
	<html:hidden property="courseSchemeUnSelected" styleId="courseSchemeUnSelected" />
	<html:hidden property="programIds" name="ExamDefinitionForm"
		styleId="programIds" />
	<c:choose>
		<c:when test="${ExamDefinitionOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateExamDefinition" />
			<html:hidden property="origExamName" styleId="origExamName" />
			<html:hidden property="origExamCode" styleId="origExamCode" />
			<html:hidden property="origMonth" styleId="origMonth" />
			<html:hidden property="origYear" styleId="origYear" />
			<html:hidden property="origExamTypeId" styleId="origExamTypeId" />

		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addExamDefinitionDetails" />
		</c:otherwise>
	</c:choose>
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.examDefinition.examName" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.examDefinition.examName" /> </strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<div align="right"><span class='MandatoryMark'><bean:message
							key="knowledgepro.mandatoryfields" /></span></div>
						<tr>

							<td height="20" colspan="6" class="body" align="left">

							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">

							<table width="100%" cellspacing="1" cellpadding="2">

								<tr>
									<td width="15%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>
									<td width="35%" height="15" class="row-even"><html:hidden
										name="ExamDefinitionForm" property="listProgramTypes" />
										<table width="100%" >
										<nested:iterate property="prgTypeList" id="as" >
										<tr>
										<nested:write  name="as" /><br><br></nested:iterate>
										</table>
										</td>
									<td width="15%" height="15" class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td width="35%" height="15" colspan="3" class="row-even">
									<html:hidden name="ExamDefinitionForm" property="listPrograms" />
										
							<table width="100%" >
										<nested:iterate property="prgList" id="asp" >
										<tr>
										<nested:write  name="asp" /><br><br></nested:iterate>
										</table>
										</td>


								</tr>

							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<br>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">

							<table width="100%" cellspacing="1" cellpadding="2">

								<tr>
									<td height="25" class="row-odd" width="15%">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.exam.examDefinition.academicYear" />:</div>
									</td>
									<td height="25" class="row-even" width="35%"><input type="hidden"
										id="ay" name="ay"
										value='<bean:write name="ExamDefinitionForm" property="academicYear"/>' /><html:select
										property="academicYear" styleId="academicYear"
										name="ExamDefinitionForm" styleClass="combo"
										onchange="getInternalExamMap(this.value)" disabled="true">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>

									<td height="25" class="row-odd" width="15%">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.exam.examDefinition.examType" />:</div>
									</td>
									<td height="25" class="row-even" width="35%"><span class="star">
									<html:select property="examTypeId" styleClass="body" styleId="examTypeId" onchange="showRegularDropDown(this.value)">
										<html:option value="0">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<html:optionsCollection name="ExamDefinitionForm"
											property="examTypeList" label="display" value="id" />
									</html:select> </span></td>
								</tr>
								<tr>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examName" />:</div>
									</td>
									<td height="25" class="row-even"><label></label>
									<span class="star"> <html:text property="examName"
										styleId="examName" size="50" maxlength="100" /> </span></td>

									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examCode" />:</div>
									</td>
									<td height="25" class="row-even"><label></label>
									<span class="star"> <html:text property="examCode"
										styleId="examCode" size="20" maxlength="50" /> </span></td>

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.exam.examDefinition.month" />:</div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										id="month1" name="month1"
										value='<bean:write name="ExamDefinitionForm" property="month"/>' /><html:select
										property="month" styleId="month" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderMonths normalMonth="true"></cms:renderMonths>
									</html:select></td>

									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.exam.examDefinition.year" />:</div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="ExamDefinitionForm" property="year"/>' /><html:select
										property="year" styleId="year" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderFutureYear normalYear="true"></cms:renderFutureYear>
									</html:select></td>

								</tr>
								<tr>


									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.examDefinition.joiningBatch" />:</div>
									</td>

									<td height="25" class="row-even"><span class="star">
									<input type="hidden" id="jby" name="jby"
										value='<bean:write name="ExamDefinitionForm" property="joiningBatchYear"/>' />
									<html:select property="joiningBatchYear" styleClass="body"
										styleId="joiningBatchYear">
										<html:option value="-1">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select> </span></td>

									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.examDefinition.maxFailedSubjects" />:</div>
									</td>
									<td height="25" class="row-even"><span class="star">
									<html:text property="maxFailedSubjects"
										styleId="maxFailedSubjects" size="20" maxlength="2" /> </span></td>
								</tr>
								<tr>


									<td height="25" class="row-odd">
									
									</td>

									<td height="25" class="row-even"></td>

									<td height="25" class="row-odd">
									<div align="right">Exam alias:</div>
									</td>
									<td height="25" class="row-even"><span class="star">
									<html:text property="alias"
										styleId="maxFailedSubjects" size="20" maxlength="2" /> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.examDefinition.current" />:</div>
									</td>

									<td height="25" class="row-even"><nested:hidden
										styleId="currentDummy" property="currentDummy" /> <nested:checkbox
										styleId="current" property="current" /> <script
										type="text/javascript">
	var v = document.getElementById("currentDummy").value;

	if (v == 'true') {
		document.getElementById("current").checked = true;
	}
</script></td>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.examDefinition.internalExamOverAll" />:</div>
									</td>
									<td height="25" class="row-even"><nested:select
										property="internalExamId" styleClass="body"
										multiple="multiple" size="6" styleId="internalExamId"
										style="width:400px">
											<logic:notEmpty name="ExamDefinitionForm"
											property="mapInternalExam">

											<nested:optionsCollection property="mapInternalExam"
												label="value" value="key" styleClass="comboBig" />

										</logic:notEmpty>
									</nested:select></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.examDefinition.internalExamType" />:</div>
									</td>
									<td height="25" class="row-even"><span class="star">
									<html:select property="internalExamTypeId" styleClass="body"
										styleId="internalExamTypeId">
										<html:option value="-1">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<html:optionsCollection name="ExamDefinitionForm"
											property="internalExamTypeList" label="display" value="id" />
									</html:select> </span></td>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.examDefinition.isActive" />:</div>
									</td>
									<td height="25" class="row-even"><html:hidden
										styleId="checkedActiveDummy" property="checkedActiveDummy" />
									<html:checkbox styleId="checkedActive" property="checkedActive"
										onchange="ale()" /> <script type="text/javascript">
	var v1 = document.getElementById("checkedActiveDummy").value;
	if (v1 == 'true') {

		document.getElementById("checkedActive").checked = true;
	}
</script></td>
								</tr>
								<tr id="regularExamsRow" style="display: none;">
									<td class="row-odd" align="right">
										<span class="Mandatory">*</span>Regular exam corresponds to Redo:
									</td>
									<td class="row-even">										
										<html:select property="regularExamIdForRedo" styleClass="body" styleId="regularExamIdForRedo">
											<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
											<logic:notEmpty name="ExamDefinitionForm" property="regularExams">
												<html:optionsCollection name="ExamDefinitionForm" property="regularExams" label="value" value="key" />
											</logic:notEmpty>											
										</html:select>
									</td>
									<td class="row-odd"></td>
									<td class="row-even"></td>
								</tr>
								<tr>
									<td height="20" colspan="6" class="body" align="left"><FONT
										color="red"><bean:message
										key="knowledgepro.exam.examDefinition.note" /></FONT></td>
								</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>

					</table>
					<br>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">

							<table width="100%" cellspacing="1" cellpadding="2">

								<tr>

									<td height="10" colspan="6" class="heading">
									<input type="hidden" id="courseCount" value='<bean:write name="ExamDefinitionForm" property="examCourseSchemeListSize"/>'>
									<bean:message key="knowledgepro.exam.examDefinition.schemeType" />: 
									<html:select property="schemeType" styleId="schemeType" styleClass="combo"  onchange="checkCheckBox(this.value)">
										<html:option value="">Select</html:option>
										<html:option value="Odd">Odd</html:option>
										<html:option value="Even">Even</html:option>
										<html:option value="Both">Both</html:option>
									</html:select> 
									</td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>
							<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
							<td valign="top" class="news">&nbsp;</td>
							<td width="13" valign="top" background="images/Tright_3_3.gif"
								class="news"></td>

						</tr>

					</table>
					<br>

					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							
							<table width="100%" cellspacing="1" cellpadding="2">

								<nested:iterate name="ExamDefinitionForm"
									property="examCourseSchemeList" id="eCSList" indexId="count"
									type="com.kp.cms.to.exam.ExamCourseSchemeDetailsTO">
									<input type="hidden"
										value='<bean:write name="eCSList" 
								property="schemeCount"/>'
										id='scheme_<c:out value='${count}'/>'>
									<tr>
										<td height="10" colspan="6" class="heading"><bean:message
											key="knowledgepro.admin.course.with.col" /><bean:write
											name="eCSList" property="course" /></td>

									</tr>


									<tr>
										<td width="15%" height="20" class="row-white"><bean:message
											key="knowledgepro.admission.scheme" />: <bean:write
											name="eCSList" property="schemeName" /></td>
									</tr>

									<tr>
										<td><nested:iterate id="listDisplay"
											property="listDisplay" indexId="count1" name="eCSList"
											type="com.kp.cms.to.exam.ExamSchemeTO">
											<c:if test="${(count1)%6 == 0}">
												<tr class="row-even">
											</c:if>
													<c:if test="${listDisplay.selected == 'on'}">
														<td height="20">
														<div align="right"><input type="checkbox"
															name="selected"
															id="checkbox_<c:out value='${count}'/>_<c:out value='${count1}'/>"
															checked="checked" /></div>

														<input type="hidden" value='<bean:write name="eCSList" property="courseId" />_<nested:write property="schemeNo" name="listDisplay" />_<nested:write property="schemeId" name="eCSList" />_<bean:write name="eCSList" property="programId" />' id="hidden_<c:out value='${count}'/>_<c:out value='${count1}'/>" />
														</td>

														<td><nested:write property="scheme"
															name="listDisplay" /></td>
													</c:if>
													<c:if test="${listDisplay.selected == 'off'}">
														<td height="20">
														<div align="right"><input type="checkbox"
															name="selected"
															id="checkbox_<c:out value='${count}'/>_<c:out value='${count1}'/>" /></div>

														<input type="hidden"
															value='<bean:write
											name="eCSList" property="courseId" />_<nested:write property="schemeNo" name="listDisplay" />_<nested:write property="schemeId" name="eCSList" />_<bean:write
											name="eCSList" property="programId" />'
															id="hidden_<c:out value='${count}'/>_<c:out value='${count1}'/>" />
														</td>

														<td><nested:write property="scheme"
															name="listDisplay" /></td>
													</c:if>

											</nested:iterate></td>

									</tr>
								</nested:iterate>
							</table>
							</td>

							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr height="35">
							&nbsp;&nbsp;
						</tr>
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						<tr>
							<td></td>
							<td align="center" width="100%"><html:submit
								styleClass="formbutton" value="Submit"
								onclick="getChkBoxValues();" /> &nbsp;&nbsp;
							<c:choose>
							<c:when test="${ExamDefinitionOperation == 'edit'}">
							
									<html:button styleClass="formbutton" property=""
										onclick="resetMessages()" value="Reset">
										
									</html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="reset()">
										
									</html:button>
									
								</c:otherwise>
							</c:choose>
							<html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button></td>
							<td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
					</table>
					</td>
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
	<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var monthId = document.getElementById("month1").value;
	if (monthId != null && monthId.length != 0) {
		document.getElementById("month").value = monthId;
	}
	var ayId = document.getElementById("ay").value;
	if (ayId != null && ayId.length != 0) {
		document.getElementById("academicYear").value = ayId;
	}
	var jbyId = document.getElementById("jby").value;
	if (jbyId != null && jbyId.length != 0) {
		document.getElementById("joiningBatchYear").value = jbyId;
	}
	document.getElementById("internalExamId").focus();
	var examTypeId = document.getElementById("examTypeId").value;
	if(examTypeId == '5') {
		document.getElementById("regularExamsRow").style.display = "table-row";
	}
</script>
</html:form>