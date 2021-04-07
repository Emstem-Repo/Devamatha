<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script language="JavaScript" >
function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);		
	resetOption("course");
	resetOption("interviewType");
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
	document.getElementById("programType").selectedIndex = 0;
	resetOption("program");
	resetOption("course");
	resetOption("interviewType");
	document.getElementById("interviewDate").value = "";
	document.getElementById("startingTimeHours").value = "00";	
	document.getElementById("startingTimeMins").value = "00";
	document.getElementById("endingTimeHours").value = "00";	
	document.getElementById("endingTimeMins").value = "00";
	resetErrMsgs();
}

function getinterviewType() {
	var year = document.getElementById("appliedYear").options[document.getElementById("appliedYear").selectedIndex].value;
	var courseId = document.getElementById("course").options[document.getElementById("course").selectedIndex].value;
	
		if(courseId.length >0) {
			getInterviewTypeByCourse("interviewMap",courseId,year,"interviewType",updateInterviewType);		
			document.getElementById("courseName").value =	document.getElementById("course").options[document.getElementById("course").selectedIndex].text	
		}
		else
		{
			getinterviewTypeByProgram();
		}	
	
}

function updateInterviewType(req) {
	updateOptionsFromMapForMultiSelect(req,"interviewType");
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

function getinterviewTypeByProgram() {
	var year = document.getElementById("appliedYear").options[document.getElementById("appliedYear").selectedIndex].value;
	var program = document.getElementById("program").options[document.getElementById("program").selectedIndex].value;
	getInterviewTypeByProgram("interviewMap",program,year,"interviewType",updateInterviewType);		
	
}
</script>
<html:form action="interviewTimeChange" method="POST">
	<html:hidden property="method" styleId="method" value="getSelectedInterCandidates" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="interviewTimeChangeForm" />
	<html:hidden property="programTypeName" styleId="programTypeName" value=""/>
	<html:hidden property="programName" styleId="programName" value=""/>
	<html:hidden property="courseName" styleId="courseName" value=""/>
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.interview.schedule" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.interview.schedule" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">

					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
						<tr>
							<td height="20" colspan="6" class="body" align="left">
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
									<html:select property="programId"  styleId="program" onchange="getCourses(this.value);getinterviewTypeByProgram()" styleClass="combo">
										<html:option value="">- Select -</html:option>
										<c:if test="${interviewTimeChangeForm.programTypeId != null && interviewTimeChangeForm.programTypeId != ''}">
											<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value" value="key" />
											</c:if>
										</c:if>
									</html:select>
									</td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="30%" class="row-even">
									<html:select property="courseId" styleId="course" onchange="getinterviewType()" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value" value="key" />
											</c:if>
									</html:select>
									 </td>
								</tr>
								<tr>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.year" />:</div>
									</td>
									<td width="20%" class="row-even"> 
									<input type="hidden" id="tempyear" name="tempyear"
										value="<bean:write name="interviewTimeChangeForm" property="appliedYear"/>" />
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
									<html:select property="interviewType" multiple="multiple" size="3" styleId="interviewType" style="width:200px">
										<c:if test="${interviewMap == null}">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										</c:if>	
										<c:if test="${interviewMap != null}">
											<html:optionsCollection name="interviewMap" label="value" value="key" />
										</c:if>
									</html:select>
									</td>
									<td width="10%" class="row-odd">
									<div align="right" >&nbsp;</div>
									</td>
									<td width="30%" class="row-even">&nbsp;
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
											'formname' :'interviewTimeChangeForm',
											// input name
											'controlname' :'interviewDate'
										});
									</script>
									</td>
									<td width="10%" height="25" class="row-odd">
									<div align="right">&nbsp;<bean:message key="knowledgepro.interview.STime" />:</div>
									</td>
									<td width="20%" height="25" class="row-even">
									<input type="hidden" id="stHours" name="stHours" value="<bean:write name="interviewTimeChangeForm" property="startingTimeHours"/>"/>
									<input type="hidden" id="stMins" name="stMins" value="<bean:write name="interviewTimeChangeForm" property="startingTimeMins"/>"/>

									<html:text name="interviewTimeChangeForm" property="startingTimeHours" styleClass="Timings" value="00" styleId="startingTimeHours" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>							
									:
									<html:text name="interviewTimeChangeForm" property="startingTimeMins" styleClass="Timings" value="00" styleId="startingTimeMins" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
									<td width="10%" class="row-odd">
									<div align="right">&nbsp;<bean:message key="knowledgepro.interview.EndTime" />:</div>
									</td>
									<td width="30%" class="row-even">
									<input type="hidden" id="enHours" name="enHours" value="<bean:write name="interviewTimeChangeForm" property="endingTimeHours"/>"/>
									<input type="hidden" id="enMins" name="enMins" value="<bean:write name="interviewTimeChangeForm" property="endingTimeMins"/>"/>

									<html:text name="interviewTimeChangeForm" property="endingTimeHours" styleClass="Timings" value="00" styleId="endingTimeHours" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>							
									:
									<html:text name="interviewTimeChangeForm" property="endingTimeMins" styleClass="Timings" value="00" styleId="endingTimeMins" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
								</tr>
								<tr>
								<td width="10%" height="25" class="row-odd">
									<div align="right">&nbsp;<bean:message key="knowledgepro.admission.interview.schedule.appNoFOrm" />:</div>
									</td>
									<td width="20%" height="25" class="row-even">
									<html:text name="interviewTimeChangeForm" property="appNoForm" styleId="appNoForm" size="11" maxlength="11" onkeypress="return isNumberKey(event)"></html:text>
									</td>
									<td width="10%" height="25" class="row-odd">
									<div align="right">&nbsp;<bean:message key="knowledgepro.admission.interview.schedule.appNoTo" />:</div>
									</td>
									<td width="20%" height="25" class="row-even">
									<html:text name="interviewTimeChangeForm" property="appNoTo" styleId="appNoTo" size="11" maxlength="11" onkeypress="return isNumberKey(event)"></html:text>							
									</td>
									<td width="10%" class="row-odd">
									</td>
									<td width="20%" class="row-even">
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
							<td width="45%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"><html:button property=""
								styleClass="formbutton" onclick="resetErrorMsgs()">
								<bean:message key="knowledgepro.admin.reset" />
								</html:button> 
							</td>
							<td width="53"></td>
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
var shour = document.getElementById("stHours").value; 
if(shour.length!=0){
	document.getElementById("startingTimeHours").value=shour;
}
var smin = document.getElementById("stMins").value; 
if(smin.length!=0){
	document.getElementById("startingTimeMins").value=smin;
}
var ehour = document.getElementById("enHours").value; 
if(ehour.length!=0){
	document.getElementById("endingTimeHours").value=ehour;
}
var emin = document.getElementById("enMins").value; 
if(emin.length!=0){
	document.getElementById("endingTimeMins").value=emin;
}

</script>