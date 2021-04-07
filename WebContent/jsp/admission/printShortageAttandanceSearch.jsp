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

function resetMessages() {
	document.getElementById("programType").selectedIndex = 0;
	resetOption("program");
	resetOption("course");
	resetFieldAndErrMsgs();
}
</script>
<html:form action="/printShortageAttandance" focus="programType">
	<html:hidden property="method" styleId="method" value="getCandidates" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="printShortageAttendanceForm" />
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.attendanceShortage.label" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admission.attendanceShortage.label" /></strong></div>
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
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>
									<td  height="25" class="row-even"><label>
									<html:select property="programTypeId" styleId="programType" onchange="getPrograms(this.value)" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId" />
									</html:select> 
									</label></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td  class="row-even">
									<html:select property="programId"  styleId="program"  onchange="getCourses(this.value)" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value" value="key" />
											</c:if>
									</html:select>
									</td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.admitted.Yearnocol"/>:</div>
									</td>
									<td class="row-even" valign="top"><html:select
										property="year" styleId="year"
										styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>	
								</tr>
								<tr>
								<td  class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td class="row-even">
									<html:select property="courseId" styleId="course" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value" value="key" />
											</c:if>
									</html:select>
									 </td>
									 <td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admission.percentagefrom"/></div>
									</td>	
									<td class="row-even">
									<html:text property="percentageFrom" maxlength="3" onkeypress="return isNumberKey(event)"/>
									</td>
									 <td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admission.percentageto"/></div>
									</td>	
									<td class="row-even">
									<html:text property="percentageTo" maxlength="3" onkeypress="return isNumberKey(event)"/>
									</td>
								</tr>
								<tr>
								<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.feepays.startdate" />:</div>
									</td>
									<td class="row-even">
									<html:text name="printShortageAttendanceForm" property="startDate" styleId="startDate" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'printShortageAttendanceForm',
										// input name
										'controlname' :'startDate'
									});
								</script>
									</td>
								<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.feepays.enddate" />:</div>
									</td>
									<td class="row-even">
									<html:text name="printShortageAttendanceForm" property="endDate" styleId="endDate" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'printShortageAttendanceForm',
										// input name
										'controlname' :'endDate'
									});
								</script>
									</td>
									<td class="row-odd"></td>
									<td class="row-even"></td>
									
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><bean:message key="knowledgepro.admission.RegNofrom"/></div>
									</td>	
									<td class="row-even">
									<html:text property="regNoFrom" maxlength="10" />
									</td>
									 <td class="row-odd">
									<div align="right"><bean:message key="knowledgepro.admission.RegNoTo"/></div>
									</td>	
									<td class="row-even">
									<html:text property="regNoTo" maxlength="10" />
									</td>
									<td class="row-odd"> </td>
									<td class="row-even"></td>
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
							<div align="right">
							<html:submit value="Submit" styleClass="formbutton"></html:submit>
							</div>
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
<script language="JavaScript" >
var print = "<c:out value='${printShortageAttendanceForm.print}'/>";
if(print.length != 0 && print == "true") {
	var url ="printShortageAttandance.do?method=printCandidates";
	myRef = window.open(url,"challan_details","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	
}
</script>