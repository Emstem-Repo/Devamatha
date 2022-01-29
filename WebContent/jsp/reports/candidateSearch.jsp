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
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");	
	document.getElementById("programTypeName").value =	document.getElementById("programType").options[document.getElementById("programType").selectedIndex].text
}

function getCourses(programId) {
	getCoursesByProgram("coursesMap",programId,"course",updateCourses);	
}

function updateCourses(req) {
	updateOptionsFromMap(req,"course","- Select -");
	document.getElementById("programName").value =	document.getElementById("program").options[document.getElementById("program").selectedIndex].text

}


function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}

function getinterviewType() {
	var year = document.getElementById("year").options[document
			.getElementById("year").selectedIndex].value;
	var courseId = document.getElementById("course").options[document
			.getElementById("course").selectedIndex].value;

	if (courseId.length > 0) {
		getInterviewTypeByCourse("interviewMap", courseId, year,
				"interviewType", updateInterviewType);
		document.getElementById("courseName").value = document
				.getElementById("course").options[document
				.getElementById("course").selectedIndex].text
	}
	
}
function updateInterviewType(req) {
	updateOptionsFromMap(req, "interviewType", "- Select -");
}
function selectPreviousInterviewType(interviewTypeId) {
	if(interviewTypeId != '' )
	document.getElementById("previousInterViewType").value =	document.getElementById("interviewType").options[document.getElementById("interviewType").selectedIndex-1].value	
	
}
function showInterviewType() {
	document.getElementById("interviewResultid").style.display = "block";
	document.getElementById("interviewResult").style.display = "block";
	var tempval =  document.getElementById("interviewType").value;	
	if(tempval == ""){
		document.getElementById("interviewResultid").style.display = "none";
		document.getElementById("interviewResult").style.display = "none";
				
		}
}
</script>
<html:form action="/anyStageInAdmission" focus="programType">

	<html:hidden property="method" styleId="method"
		value="submitCandidateSearch" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="anyStageInAdmissionForm" />

<html:hidden property="programTypeName" styleId="programTypeName" value=""/>
<html:hidden property="programName" styleId="programName" value=""/>
<html:hidden property="courseName" styleId="courseName" value=""/>
<html:hidden property="previousInterViewType" styleId="previousInterViewType" value=""/>
	<table width="98%" border="0">
		<tr>
    		<td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.report.any.stage.admission"/>&gt;&gt;</span> </td>
  		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message key="knowledgepro.report.any.stage.admission"/> </strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">

					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
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
									<td width="19%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>
									<td width="17%" height="25" class="row-even"><label> 
									<html:select property="programTypeId"
										 styleId="programType"
										onchange="getPrograms(this.value)" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="programTypeList"
											label="programTypeName" value="programTypeId" />
									</html:select> 
									
									</label></td>
									<td width="15%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td width="18%" class="row-even"> 
									
									<html:select
										property="programId"  styleId="program"
										onchange="getCourses(this.value) " styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<c:if
											test="${anyStageInAdmissionForm.programTypeId != null && anyStageInAdmissionForm.programTypeId != ''}">
											<c:set var="programMap"
												value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select>
									
									
									 </td>
									<td width="15%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="16%" class="row-even">
									
									<html:select property="courseId"
										styleId="course" styleClass="combo" onchange="getinterviewType()">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>

										<c:if
											test="${anyStageInAdmissionForm.programId != null && anyStageInAdmissionForm.programId != ''}">
											<c:set var="coursesMap"
												value="${baseActionForm.collectionMap['coursesMap']}" />
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select>
									
									 </td>
								</tr>
								<tr>

									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.year" />:</div>
									</td>
									<td height="25" class="row-even">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="anyStageInAdmissionForm" property="year"/>" />
									<html:select property="year" styleId="year" styleClass="combo" onchange="getinterviewType()">
										<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
										<cms:renderYear></cms:renderYear>
									</html:select>
									</td>
								
									<td height="25" class="row-odd">
										<div align="right"><bean:message
										key="knowledgepro.admission.interviewType" />:</div>
									
								
									</td>

									<td height="25" class="row-even">
									<html:select property="interviewType" styleClass="combo"
										styleId="interviewType"
										onchange="showInterviewType()">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>

										<c:if
											test="${anyStageInAdmissionForm.interviewType != null && anyStageInAdmissionForm.interviewType != ''}">
											<c:set var="interviewMap"
												value="${baseActionForm.collectionMap['interviewMap']}" />
											<c:if test="${interviewMap != null}">
												<html:optionsCollection name="interviewMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select> 

									</td>
									<td class="row-odd"><div id="interviewResultid" align="right"><bean:message key="knowledgepro.admission.report.interviewresult" /></div></td>
									<td class="row-even">
										<html:select property="interviewResult" 
											styleId="interviewResult" styleClass="combo">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>											
											<html:option value="1"><bean:message key="knowledgepro.admission.report.pass"/></html:option>
											<html:option value="2"><bean:message key="knowledgepro.admission.report.fail"/></html:option>
										</html:select>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd"><div align="right"><bean:message
										key="knowledgepro.admission.interviewstartDate" />:</div></td>
									<td height="25" class="row-even"><html:text name="anyStageInAdmissionForm" 
										property="interviewStartDate" styleId="interviewStartDate" 
										size="15" maxlength="15" />
											<script
											language="JavaScript">
											new tcal( {
												// form name
												'formname' :'anyStageInAdmissionForm',
												// input name
												'controlname' :'interviewStartDate'
											});
										</script>
									</td>
									<td height="25" class="row-odd"><div align="right"><bean:message
										key="knowledgepro.admission.interviewendDate" />:</div></td>
									<td height="25" class="row-even"><html:text name="anyStageInAdmissionForm" 
										property="interviewEndDate" styleId="interviewEndDate" 
										size="15" maxlength="15" />
											<script
											language="JavaScript">
											new tcal( {
												// form name
												'formname' :'anyStageInAdmissionForm',
												// input name
												'controlname' :'interviewEndDate'
											});
										</script>
									</td>

									<td height="25" class="row-odd">&nbsp;</td>
									<td height="25" class="row-even">&nbsp;</td>

								</tr> 
								<tr>
									<td height="25" class="row-odd">
										<div align="right">Status</div>
									</td>
									<td height="25" class="row-even">
										<html:select property="status" 
											styleId="status" styleClass="combo">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>											
											<html:option value="selected"><bean:message key="knowledgepro.admission.report.selected"/></html:option>
											<html:option value="rejected"><bean:message key="knowledgepro.admission.report.notselected"/></html:option>
											<html:option value="canceled"><bean:message key="knowledgepro.admission.report.canceled"/></html:option>
											<html:option value="admitted"><bean:message key="knowledgepro.admission.report.admittedapproved"/></html:option>
										</html:select>

									</td>
									<td class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.residentcatg.label" /></div>
									</td>

									<td class="row-even"> <html:text
										property="residentCategoryId" styleId="residentCategory"></html:text>
									</td>
									<td height="25" class="row-odd">
										<div align="right"><bean:message
										key="admissionForm.studentinfo.nationality.label" /></div>

									</td>
									<td height="25" class="row-even">
										<html:text property="nationalityId" styleId="nationality"></html:text>	
									</td>

								</tr>
								<tr>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.religion" />:</div>
									</td>
									<td class="row-even"> <html:text
										property="religionId" styleId="religion"></html:text></td>

									<td class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.subreligion.label" /></div>
									</td>

									<td class="row-even"> <html:text
										property="subReligionId" styleId="subReligion"></html:text> </td>



									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.casteentry" />:</div>
									</td>
									<td height="25" class="row-even">
									<html:text property="casteCategoryId" styleId="castCategoty"></html:text>
									</td>

								</tr>

								<tr>

									<td width="15%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.belongsto" />:</div>
									</td>
									<td width="16%" class="row-even">
									<html:select property="belongsTo" 
										styleId="belongsTo" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:option value="R">
											<bean:message key="knowledgepro.admission.rural" />
										</html:option>
										<html:option value="U">
											<bean:message key="knowledgepro.admission.urban" />
										</html:option>
									</html:select> </td>

									<td width="15%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.gender" />:</div>
									</td>
									<td width="16%" class="row-even">
									<html:select property="gender" 
										styleId="gender" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:option value="male">
											<bean:message key="knowledgepro.admission.male" />
										</html:option>
										<html:option value="female">
											<bean:message key="knowledgepro.admission.female" />
										</html:option>
									</html:select> </td>

									<td width="15%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.bloodgroup" />:</div>
									</td>
									<td width="16%" class="row-even">
									<html:select property="bloodGroup" 
										styleId="bloodGroup" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:option value="O+ve"><bean:message key="knowledgepro.admission.report.opositive"/></html:option>
										<html:option value="A+ve"><bean:message key="knowledgepro.admission.report.apositive"/></html:option>
										<html:option value="B+ve"><bean:message key="knowledgepro.admission.report.bpositive"/></html:option>
										<html:option value="AB+ve"><bean:message key="knowledgepro.admission.report.abpositive"/></html:option>
										<html:option value="O-ve"><bean:message key="knowledgepro.admission.report.onegitive"/></html:option>
										<html:option value="A-ve"><bean:message key="knowledgepro.admission.report.anegitive"/></html:option>
										<html:option value="B-ve"><bean:message key="knowledgepro.admission.report.bnegitive"/></html:option>
										<html:option value="AB-ve"><bean:message key="knowledgepro.admission.report.abnegitive"/></html:option>
										<html:option value="NOT KNOWN"><bean:message key="knowledgepro.admission.report.unknown"/></html:option>
									</html:select> </td>
								</tr>

								<tr>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.percentagefrom" />:</div>
									</td>
									<td class="row-even"> <html:text
										property="marksObtained"  maxlength="5"
										styleId="marksObtained"></html:text> </td>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.percentageto" />:</div>
									</td>
									<td class="row-even"> <html:text
										property="marksObtainedTO"  maxlength="5"
										styleId="marksObtainedTO"></html:text> </td>
									<td class="row-odd">

									<div align="right"><bean:message
										key="knowledgepro.admin.university" />:</div>
									</td>
									<td class="row-even"> <html:text
										property="university" styleId="university"></html:text> </td>
								</tr>

								<tr>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.institute" />:</div>
									</td>
									<td class="row-even"><html:text
										property="institute" styleId="institute"></html:text> </td>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.name" />:</div>
									</td>
									<td class="row-even"><span class="row-white"> <html:text
										property="applicantName" styleId="applicantName"></html:text>
									</span></td>
									<td class="row-odd">

									<div align="right"><bean:message
										key="knowledgepro.admission.countryofbirth" />:</div>
									</td>
									<td class="row-even"><html:text
										property="birthCountry" styleId="birthCountry"></html:text> </td>
								</tr>

								<tr>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.stateofbirth" />:</div>
									</td>
									<td class="row-even"> <html:text
										property="birthState" styleId="birthState"></html:text></td>
									
									<td width="15%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.weightagefrom" />:</div>
									</td>
									<td width="16%" class="row-even">
										<html:text property="weightage"  maxlength="5" 
										styleId="weightage"></html:text>										
									 </td>
									<td class="row-odd">
											<div align="right"><bean:message
										key="knowledgepro.admin.weightageto" />:</div>
									</td>
									<td class="row-even">
										<html:text property="weightageTO"  maxlength="5" 
										styleId="weightageto"></html:text>										
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
							<td width="2%"></td>
							<td width="53"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetErrorMsgs()"></html:button></td>
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
 	document.getElementById("year").value=year;
}

	 document.getElementById("interviewResultid").style.display = "none";
	 document.getElementById("interviewResult").style.display = "none";

</script>