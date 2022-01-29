<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);		
	resetOption("course");
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");	
}

function getCourses(programId) {
	getCoursesByProgram("coursesMap",programId,"course",updateCourses);	
}

function updateCourses(req) {
	updateOptionsFromMap(req,"course","- Select -");
}

function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}

function getinterviewType() {
	var year = document.getElementById("appliedYear").options[document.getElementById("appliedYear").selectedIndex].value;
	var courseId = document.getElementById("course").options[document.getElementById("course").selectedIndex].value;
	
		if(courseId.length >0) {
			getInterviewTypeByCourse("interviewMap",courseId,year,"interviewType",updateInterviewType);		
			document.getElementById("courseName").value =	document.getElementById("course").options[document.getElementById("course").selectedIndex].text	
		}
}
function updateInterviewType(req) {
	updateOptionsFromMap(req,"interviewType","- Select -");
}

function getinterviewSubrounds(interviewTypeId) {
	getInterviewSubroundsByInterviewtype("interviewSubroundsMap",interviewTypeId,"interviewSubrounds",updateInterviewSubrounds);	
	
}

function updateInterviewSubrounds(req) {
	updateOptionsFromMap(req,"interviewSubrounds","- Select -");
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("option");
	if(items.length>=1){
		document.getElementById("subroundCount").value = items.length;
		document.getElementById("subround").innerHTML = "<span class='Mandatory'>*</span>&nbsp;Subround:";
	} else{
		document.getElementById("subroundCount").value = 0;
		document.getElementById("subround").innerHTML = "Subround:";
	}			
}

function clearField(field){
	if(field.value == "00")
		field.value = "";
}
function checkForEmpty(field){
	if(field.value.length == 0)
		field.value="00";
}

function resetMessages() {
	document.getElementById("programType").selectedIndex = 0;
	document.getElementById("program").selectedIndex = 0;
	document.getElementById("course").selectedIndex = 0;
	document.getElementById("interviewType").value = null;
	document.getElementById("interviewSubrounds").value = null;
	document.getElementById("interviewDate").value = "";
	document.getElementById("startingTimeHours").value = '00';
	document.getElementById("startingTimeMins").value = '00';
	document.getElementById("endingTimeHours").value = '00';
	document.getElementById("endingTimeMins").value = '00';
	resetErrMsgs();
}
</script>
<html:form action="/InterviewBatchEntry" focus="programType">
	<html:hidden property="method" styleId="method" value="getSelectedCandidates" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="interviewBatchEntryForm" />
	<html:hidden property="programTypeName" styleId="programTypeName" value=""/>
	<html:hidden property="programName" styleId="programName" value=""/>
	<html:hidden property="courseName" styleId="courseName" value=""/>
	<html:hidden property="subroundCount" styleId="subroundCount"/>
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.batchentry" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admission.batchentry" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
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
									<td width="10%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>
									<td width="20%" height="25" class="row-even"><label>
									<html:select property="programTypeId" styleId="programType" onchange="getPrograms(this.value)" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId" />
									</html:select> 
									</label></td>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td width="20%" class="row-even">
									<html:select property="programId"  styleId="program" onchange="getCourses(this.value)" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<c:if test="${interviewBatchEntryForm.programTypeId != null && interviewBatchEntryForm.programTypeId != ''}">
											<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value" value="key" />
											</c:if>
										</c:if>
									</html:select>
									</td>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="30%" class="row-even">
									<html:select property="courseId" styleId="course" onchange="getinterviewType()" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<c:if test="${interviewBatchEntryForm.programId != null && interviewBatchEntryForm.programId != ''}">
											<c:set var="coursesMap" value="${baseActionForm.collectionMap['coursesMap']}" />
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value" value="key" />
											</c:if>
										</c:if>
									</html:select>
									 </td>
								</tr>
								<tr>
									<td width="10%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.year" />:</div>
									</td>
									<td width="20%" height="25" class="row-even"> 
									<input type="hidden" id="tempyear" name="tempyear"
										value="<bean:write name="interviewBatchEntryForm" property="appliedYear"/>" />
									<html:select property="appliedYear" styleId="appliedYear" onchange="getinterviewType()" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									  <cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select>
									</td>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admission.interviewType" />:</div>
									</td>
									<td width="20%" class="row-even">
									<html:select property="interviewTypeId" styleId="interviewType" onchange="getinterviewSubrounds(this.value)" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<c:if
											test="${interviewBatchEntryForm.courseId != null && interviewBatchEntryForm.courseId != ''}">
											<c:set var="interviewMap"
												value="${baseActionForm.collectionMap['interviewMap']}" />
											<c:if test="${interviewMap != null}">
												<html:optionsCollection name="interviewMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select>
									</td>
									<td width="10%" class="row-odd">
									<div align="right" id="subround">&nbsp;<bean:message
										key="knowledgepro.admin.interviewsubround.subround" />:</div>
									</td>
									<td width="30%" class="row-even">
									<html:select property="interviewSubroundId" styleId="interviewSubrounds" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<c:if
											test="${interviewBatchEntryForm.interviewTypeId != null && interviewBatchEntryForm.interviewTypeId != ''}">
											<c:set var="interviewSubroundsMap"
												value="${baseActionForm.collectionMap['interviewSubroundsMap']}" />
											<c:if test="${interviewSubroundsMap != null}">
												<html:optionsCollection name="interviewSubroundsMap" label="value" value="key" />
											</c:if>
										</c:if>
									</html:select>
									</td>
								</tr>
								<tr>
									<td width="10%" height="25" class="row-odd">
									<div align="right">&nbsp;<bean:message key="knowledgepro.interview.interviewdate" />:</div>
									</td>
									<td width="20%" height="25" class="row-even">
									<html:text property="interviewDate" styleId="interviewDate" size="11" maxlength="11"></html:text>
									<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'interviewBatchEntryForm',
											// input name
											'controlname' :'interviewDate'
										});
									</script>
									</td>
									<td width="10%" class="row-odd">
									<div align="right">&nbsp;<bean:message key="knowledgepro.interview.STime" /></div>
									</td>
									<td width="20%" class="row-even">
									<html:text name="interviewBatchEntryForm" property="startingTimeHours" styleClass="Timings" value="00" styleId="startingTimeHours" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>							
									:
									<html:text name="interviewBatchEntryForm" property="startingTimeMins" styleClass="Timings" value="00" styleId="startingTimeMins" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
									<td width="10%" class="row-odd">
									<div align="right">&nbsp;<bean:message key="knowledgepro.interview.EndTime" /></div>
									</td>
									<td width="30%" class="row-even">
									<html:text name="interviewBatchEntryForm" property="endingTimeHours" styleClass="Timings" value="00" styleId="endingTimeHours" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>							
									:
									<html:text name="interviewBatchEntryForm" property="endingTimeMins" styleClass="Timings" value="00" styleId="endingTimeMins" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" onclick="resetMessages()">
								<bean:message key="knowledgepro.admin.reset" />
								</html:button> 
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("appliedYear").value=year;
}
</script>