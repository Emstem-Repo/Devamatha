<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
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
	document.location.href = "BulkMail.do?method=initStudentSearch";
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

</script>
<html:form action="/BulkMail" focus="programType">

	<html:hidden property="method" styleId="method"
		value="getStudentSearchResults" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="bulkMailForm" />

<html:hidden property="programTypeName" styleId="programTypeName" value=""/>
<html:hidden property="programName" styleId="programName" value=""/>
<html:hidden property="courseName" styleId="courseName" value=""/>
<html:hidden property="previousInterViewType" styleId="previousInterViewType" value=""/>
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.bulkmail.studentsearch" />  &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.admin.bulkmail.studentsearch" /> </strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">

					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
							 <div align="right"> <span class='MandatoryMark'>* Mandatory fields</span></div>
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
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>
									<td width="17%" height="25" class="row-even"><label><span
										class="row-white"> 
									<html:select property="programTypeId"
										 styleId="programType" styleClass="combo"
										onchange="getPrograms(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="programTypeList"
											label="programTypeName" value="programTypeId" />
									</html:select> 
									
									</span></label></td>
									<td width="15%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td width="18%" class="row-even"><span class="row-white"> 
									
									<html:select
										property="programId"  styleId="program" styleClass="combo"
										onchange="getCourses(this.value) ">
										<html:option value="">- Select -</html:option>
										<c:if
											test="${bulkMailForm.programTypeId != null && bulkMailForm.programTypeId != ''}">
											<c:set var="programMap"
												value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select>
									
									
									 </span></td>
									<td width="15%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="16%" class="row-even"><span class="row-white">
									
									<html:select property="courseId" styleClass="combo"
										styleId="course" onchange="getinterviewType()">
										<html:option value="">- Select -</html:option>

										<c:if
											test="${bulkMailForm.programId != null && bulkMailForm.programId != ''}">
											<c:set var="coursesMap"
												value="${baseActionForm.collectionMap['coursesMap']}" />
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select>
									
									 </span></td>
								</tr>
								<tr>

									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.year" />:</div>
									</td>
									<td height="25" class="row-even"><span class="row-white"> 
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="bulkMailForm" property="year"/>" />
									<html:select property="year" styleId="year" styleClass="combo" onchange="getinterviewType()">
										<html:option value=" ">- Select -</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select>

								
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.nationality.label" /></div>
									</td>

									<td height="25" class="row-even"><span class="row-white">
									<html:text property="nationalityId" styleId="nationality"></html:text>
									</span></td>

									<td class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.residentcatg.label" /></div>
									</td>

									<td class="row-even"><span class="row-white"> <html:text
										property="residentCategoryId" styleId="residentCategory"></html:text>
									</span></td>



								</tr>

								<tr>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.religion" />:</div>
									</td>
									<td class="row-even"><span class="row-white"> <html:text
										property="religionId" styleId="religion"></html:text> </span></td>

									<td class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.subreligion.label" /></div>
									</td>

									<td class="row-even"><span class="row-white"> <html:text
										property="subReligionId" styleId="subReligion"></html:text> </span></td>



									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.casteentry" />:</div>
									</td>
									<td height="25" class="row-even"><span class="row-white">
									<html:text property="casteCategoryId" styleId="castCategoty"></html:text>
									</span></td>

								</tr>

								<tr>

									<td width="15%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.belongsto" />:</div>
									</td>
									<td width="16%" class="row-even"><span class="row-white">
									<html:select property="belongsTo" styleClass="combo"
										styleId="belongsTo">
										<html:option value="">- Select -</html:option>
										<html:option value="R">
											<bean:message key="knowledgepro.admission.rural" />
										</html:option>
										<html:option value="U">
											<bean:message key="knowledgepro.admission.urban" />
										</html:option>
									</html:select> </span></td>

									<td width="15%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.gender" />:</div>
									</td>
									<td width="16%" class="row-even"><span class="row-white">
									<html:select property="gender" styleClass="combo"
										styleId="gender">
										<html:option value="">- Select -</html:option>
										<html:option value="male">
											<bean:message key="knowledgepro.admission.male" />
										</html:option>
										<html:option value="female">
											<bean:message key="knowledgepro.admission.female" />
										</html:option>
									</html:select> </span></td>

									<td width="15%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.bloodgroup" />:</div>
									</td>
									<td width="16%" class="row-even"><span class="row-white">
									<html:select property="bloodGroup" styleClass="combo"
										styleId="bloodGroup">
										<html:option value="">- Select -</html:option>
										<html:option value="O+ve">O+ve</html:option>
										<html:option value="A+ve">A+ve</html:option>
										<html:option value="B+ve">B+ve</html:option>
										<html:option value="AB+ve">AB+ve</html:option>
										<html:option value="O-ve">O-ve</html:option>
										<html:option value="A-ve">A-ve</html:option>
										<html:option value="B-ve">B-ve</html:option>
										<html:option value="AB-ve">AB-ve</html:option>
									</html:select> </span></td>
								</tr>

								<tr>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.totalmarks" />:</div>
									</td>
									<td class="row-even"><span class="row-white"> <html:text
										property="marksObtained"  maxlength="5"
										styleId="marksObtained"></html:text> </span></td>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.weightage" />:</div>
									</td>
									<td class="row-even"><span class="row-white"> <html:text
										property="weightage"  maxlength="5"
										styleId="weightage"></html:text> </span></td>
									<td class="row-odd">

									<div align="right"><bean:message
										key="knowledgepro.admin.university" />:</div>
									</td>
									<td class="row-even"><span class="row-white"> <html:text
										property="university" styleId="university"></html:text> </span></td>
								</tr>

								<tr>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.institute" />:</div>
									</td>
									<td class="row-even"><span class="row-white"> <html:text
										property="institute" styleId="institute"></html:text> </span></td>

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
									<td class="row-even"><span class="row-white"> <html:text
										property="birthCountry" styleId="birthCountry"></html:text> </span></td>
								</tr>

								<tr>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.stateofbirth" />:</div>
									</td>
									<td class="row-even"><span class="row-white"> <html:text
										property="birthState" styleId="birthState"></html:text> </span></td>
									
									<td width="15%" class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.admission.interviewType" />:</div>
									</td>
									<td width="16%" class="row-even"><span class="row-white">
									
									<html:select property="interviewType" styleClass="combo"
										styleId="interviewType" onchange="selectPreviousInterviewType(this.value)">
										<html:option value="">- Select -</html:option>

										<c:if
											test="${bulkMailForm.interviewType != null && bulkMailForm.interviewType != ''}">
											<c:set var="interviewMap"
												value="${baseActionForm.collectionMap['interviewMap']}" />
											<c:if test="${interviewMap != null}">
												<html:optionsCollection name="interviewMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select>
									
									 </span></td>
									<td class="row-odd">&nbsp;</td>
									<td class="row-even">&nbsp;</td>
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
</script>