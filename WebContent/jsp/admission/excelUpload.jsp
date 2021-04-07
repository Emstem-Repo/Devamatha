<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<head>
		<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
</head>
<script language="JavaScript" >

function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);		
	resetOption("course");
	resetOption("semister");	
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");
}

function getCourses(programId) {
	getCoursesByProgram("coursesMap",programId,"course",updateCourses);	
	resetOption("semister");
}

function updateCourses(req) {
	updateOptionsFromMap(req,"course","- Select -");
}

function getSemester() {
	resetOption("semister");
	var year = document.getElementById("appliedYear").options[document.getElementById("appliedYear").selectedIndex].value;
	var courseId = document.getElementById("course").options[document.getElementById("course").selectedIndex].value;
	
		if(courseId.length >0) {
			getSemistersOnYearAndCourseScheme("semisterMap",courseId,"semister",year,updateSemister);		
			document.getElementById("courseName").value =	document.getElementById("course").options[document.getElementById("course").selectedIndex].text	
		}
}

function updateSemister(req) {
	updateOptionsFromMap(req,"semister","- Select -");
}

function uploadINResult(){
	document.getElementById("termNo").value= document.getElementById("semister").options[document.getElementById("semister").selectedIndex].text;
	document.getElementById("method").value="saveUploadData";
}

function resetMessages() {
	document.getElementById("programType").selectedIndex = 0;
	document.getElementById("program").selectedIndex = 0;
	document.getElementById("course").selectedIndex = 0;
	document.getElementById("semister").selectedIndex = 0;
	
	resetErrMsgs();
}	
</script>
<html:form action="/excelUpload" method="POST" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="saveUploadData" />
	<html:hidden property="formName" value="excelDataForm" />
	<html:hidden property="courseName" styleId="courseName" value=""/>
	<html:hidden property="pageType" value="1" />
	<html:hidden property="termNo" styleId="termNo" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.RegRollNOUpload" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message key="knowledgepro.admin.RegRollNOUpload" /></strong></div></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right" style="color:red"> <span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg" property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr class="row-white">
			                				<td height="25" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
			                				<td height="25" class="row-even">
			                				<html:select property="programTypeId" styleId="programType" onchange="getPrograms(this.value)" styleClass="combo">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId" />
											</html:select> 
			                				</td>
			                				
											<td height="25" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.program"/>:</div></td>
			                				<td height="25" class="row-even">
			                				<html:select property="programId"  styleId="program" onchange="getCourses(this.value)" styleClass="combo">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<c:if test="${excelDataForm.programTypeId != null && excelDataForm.programTypeId != ''}">
													<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}" />
													<c:if test="${programMap != null}">
														<html:optionsCollection name="programMap" label="value" value="key" />
													</c:if>
												</c:if>
											</html:select>
											</td>
										</tr>
										<tr class="row-white">
			                				<td width="15%" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.course" />:</div></td>
											<td width="16%" class="row-even">
											<html:select property="courseId" styleId="course" styleClass="combo" onchange="getSemester()">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<c:if test="${excelDataForm.programId != null && excelDataForm.programId != ''}">
													<c:set var="coursesMap" value="${baseActionForm.collectionMap['coursesMap']}" />
													<c:if test="${coursesMap != null}">
														<html:optionsCollection name="coursesMap" label="value" value="key" />
													</c:if>
												</c:if>
											</html:select>
											</td>
											
											<td class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.year" />:</div></td>
											<td class="row-even">
											<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="excelDataForm" property="applicationYear"/>" />
											<html:select name="excelDataForm" property="applicationYear" styleId="appliedYear" styleClass="combo" onchange="getSemester()">
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
												<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
											</html:select></td>
										</tr>
										<tr>
											<td height="25" class="row-odd" width="25%">
												<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.file" />:</div></td>
											<td height="25" class="row-even"><label>
												<html:file property="thefile" styleId="thefile" size="15" maxlength="30"/></label>
											</td>
											<td height="25" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory"></span><bean:message key="knowledgepro.fee.semister"/>:</div></td>
											<td height="25" class="row-even">
											<input type="hidden" id="sem" name="sem" value='<bean:write name="excelDataForm" property="semister"/>' />
											<html:select property="semister" styleId="semister" name="excelDataForm" styleClass="combo">
                       	   				 	<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
                       	   				 		<c:if test="${ semisterMap != null}">
 													<html:optionsCollection name="semisterMap" label="value" value="key"/>
 												</c:if>	
                       	 			 		</html:select>	
											</td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="22"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5" height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" height="106" border="0" cellpadding="1" cellspacing="2">
						<tr>
							<td width="100%" height="46" class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="center"><html:submit styleClass="formbutton" onclick="uploadINResult()">
									<bean:message key="knowledgepro.submit" /></html:submit>
									<html:button property="" styleClass="formbutton" onclick="resetMessages()">
									<bean:message key="knowledgepro.admin.reset" /></html:button></div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="10"></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>

<script type="text/javascript">
	
	var schemeId = document.getElementById("sem").value;
	
	if (schemeId != null && schemeId.length != 0) {
		document.getElementById("semister").value = schemeId;
	}
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("appliedYear").value = year;
	}
</script>