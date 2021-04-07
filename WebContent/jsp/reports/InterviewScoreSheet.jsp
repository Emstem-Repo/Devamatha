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
	
	var courseId = document.getElementById("course").options[document.getElementById("course").selectedIndex].value;
	var year=document.getElementById("year").value;
		if(courseId.length >0) {
			getInterviewTypeByCourse("interviewMap",courseId,year,"interviewType",updateInterviewType);		
			document.getElementById("courseName").value =	document.getElementById("course").options[document.getElementById("course").selectedIndex].text	
		}
		if (courseId.length == 0) {
			
			getinterviewTypeByProgram();
		}
}

function getinterviewTypeByProgram() {
	
	var programId = document.getElementById("program").options[document
			.getElementById("program").selectedIndex].value;
	var year=document.getElementById("year").value;


		getInterviewTypeByProgram("interviewMap",programId,year,"interviewType",updateInterviewTypeProgram);
	

}
function updateInterviewTypeProgram(req) {
	
	updateOptionsFromMapForMultiSelect(req,"interviewType");
}
function updateInterviewType(req) {
	updateOptionsFromMapForMultiSelect(req,"interviewType");
}

function clearField(field){
	if(field.value == "00")
		field.value = "";
}
function checkForEmpty(field){
	if(field.value.length == 0)
		field.value="00";
}
function getCourseandInterview(){
	var programId = document.getElementById("program").options[document.getElementById("program").selectedIndex].value;
	var year=document.getElementById("year").value;
	getCourses(programId);
	getinterviewTypeByProgram();
}
function resetMessages() {
	document.getElementById("programType").selectedIndex = 0;
	document.getElementById("interviewStartDate").value = "";
	document.getElementById("interviewEndDate").value = "";
	document.getElementById("startingTimeHours").value = "00";	
	document.getElementById("startingTimeMins").value = "00";
	document.getElementById("endingTimeHours").value = "00";	
	document.getElementById("endingTimeMins").value = "00";
	document.getElementById("year").value = resetYear();
	document.getElementById("studentPerGroup").value = "";
	resetOption("program");
	resetOption("course");
	resetOption("interviewType");
	var destination5 = document.getElementById("interviewType");
	for (x1=destination5.options.length-1; x1>=0; x1--) {
		destination5.options[x1]=null;
	}
	resetErrMsgs();
}
</script>
<html:form action="/interviewScoreSheet" focus="programType">
	<html:hidden property="method" styleId="method" value="getCandidates" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="interviewScoreSheetForm" />
	<html:hidden property="programTypeName" styleId="programTypeName" value=""/>

	<html:hidden property="courseName" styleId="courseName" value=""/>
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.reports" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.reports.interviewscoresheetentry" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.reports.interviewscoresheetentry" /></strong></div>
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
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>
									<td width="16%" height="25" class="row-even"><label>
									<html:select property="programTypeId" styleId="programType" onchange="getPrograms(this.value)" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId" />
									</html:select> 
									</label></td>
									<td width="16%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td width="16%" class="row-even">
									<html:select property="programId"  styleId="program"  onchange="getCourses(this.value),getinterviewTypeByProgram()" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<c:if test="${interviewScoreSheetForm.programTypeId != null && interviewScoreSheetForm.programTypeId != ''}">
											<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value" value="key" />
											</c:if>
										</c:if>
									</html:select>
									</td>
									<td width="16%" class="row-odd">
									<div align="right"><span class="Mandatory"></span><bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="16%" class="row-even">
									<html:select property="courseId" styleId="course" onchange="getinterviewType()" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value" value="key" />
											</c:if>

									</html:select>
									 </td>
								</tr>
								<tr>
									
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admission.interviewType" />:</div>
									</td>
									<td class="row-even">
									<html:select property="interviewType" styleId="interviewType" styleClass="combo" multiple="multiple" style="width:200px;height:80px">
										<c:if test="${interviewMap != null}">
											<html:optionsCollection name="interviewMap" label="value"
													value="key" />
										</c:if>
									</html:select>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admission.interviewstartDate" />:</div>
									</td>
									<td class="row-even">
									<html:text name="interviewScoreSheetForm" property="interviewStartDate" styleId="interviewStartDate" size="10" maxlength="16"/>
									<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'interviewScoreSheetForm',
											// input name
											'controlname' :'interviewStartDate'
										});
									</script>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admission.interviewendDate" />:</div>
									</td>
									<td height="25" class="row-even">
									<html:text property="interviewEndDate" styleId="interviewEndDate" size="11" maxlength="11"></html:text>
									<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'interviewScoreSheetForm',
											// input name
											'controlname' :'interviewEndDate'
										});
									</script>
									</td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right">&nbsp;<bean:message key="knowledgepro.interview.STime" /></div>
									</td>
									<td class="row-even">
									<html:text name="interviewScoreSheetForm" property="startingTimeHours" styleClass="Timings" value="00" styleId="startingTimeHours" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
									<html:text name="interviewScoreSheetForm" property="startingTimeMins" styleClass="Timings" value="00" styleId="startingTimeMins" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
									<td class="row-odd">
									<div align="right">&nbsp;<bean:message key="knowledgepro.interview.EndTime" /></div>
									</td>
									<td class="row-even">
									<html:text name="interviewScoreSheetForm" property="endingTimeHours" styleClass="Timings" value="00" styleId="endingTimeHours" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
									<html:text name="interviewScoreSheetForm" property="endingTimeMins" styleClass="Timings" value="00" styleId="endingTimeMins" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
									<td width="46%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.fee.academicyear.col"/>:</div>
									</td>
									<td class="row-even" valign="top">
									<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="interviewScoreSheetForm" property="year"/>" />
									<html:select
										property="year" styleId="year"
										styleClass="combo" onchange="getCourseandInterview()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>	
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
var print = "<c:out value='${interviewScoreSheetForm.print}'/>";
if(print.length != 0 && print == "true") {
	var url ="interviewScoreSheet.do?method=printScoreSheetResult";
	myRef = window.open(url,"challan_details","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
var year = document.getElementById("tempyear").value;
if (year.length != 0) {
	document.getElementById("year").value = year;
}
</script>