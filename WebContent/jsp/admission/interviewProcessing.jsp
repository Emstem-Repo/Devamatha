<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
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
	document.getElementById("program").selectedIndex = 0;
	document.getElementById("course").selectedIndex = 0;
	document.getElementById("applicationNumber").value = " ";
	
	resetErrMsgs();
}
</script>
<html:form action="/InterviewResultEntry">
	<html:hidden property="method" value="getApplicantDetails" />
	<html:hidden property="formName" value="interviewResultEntryForm" />
	<html:hidden property="pageType" value="1" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.admission.interviewprocessing" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.admission.interviewprocessing" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
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
			                				<td width="15%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
			                				<td width="20%" height="25" class="row-even">
			                				<html:select property="programTypeId" styleId="programType" onchange="getPrograms(this.value)" styleClass="combo">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId" />
											</html:select> 
			                				</td>
			                				<td width="10%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.program"/>:</div></td>
			                				<td  width="20%" height="25" class="row-even">
			                				<html:select property="programId"  styleId="program" onchange="getCourses(this.value)" styleClass="combo">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<c:if test="${interviewResultEntryForm.programTypeId != null && interviewResultEntryForm.programTypeId != ''}">
													<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}" />
													<c:if test="${programMap != null}">
														<html:optionsCollection name="programMap" label="value" value="key" />
													</c:if>
												</c:if>
											</html:select>
											</td>
			                				<td width="10%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.course"/>:</div></td>
			                				<td width="30%" class="row-even">
			                				<html:select property="courseId" styleId="course" styleClass="combo">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<c:if test="${interviewResultEntryForm.programId != null && interviewResultEntryForm.programId != ''}">
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
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.applicationnumber" />:</div>
											</td>
											<td width="20%" height="25" class="row-even"><label>
											<html:text property="applicationNumber"
												styleId="applicationNumber" size="15" maxlength="30">
											</html:text> </label></td>
											<td width="10%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.year" />:</div>
											</td>
											<td width="20%" class="row-even">
											<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="interviewResultEntryForm" property="applicationYear"/>" />
											<html:select name="interviewResultEntryForm" property="applicationYear" styleId="applicationYear" styleClass="combo">
												<html:option value=" ">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
											</html:select></td>
											<td width="10%" height="25" class="row-odd">
											</td>
											<td width="30%" height="25" class="row-even"><label>
											</label></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="22"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" height="106" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td width="100%" height="46" class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="center"><html:submit styleClass="formbutton">
										<bean:message key="knowledgepro.submit" />
									</html:submit><html:button property="" styleClass="formbutton"
										onclick="resetMessages()">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button></div>
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
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("applicationYear").value=year;
}
</script>