<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="com.kp.cms.constants.CMSConstants"%><SCRIPT type="text/javascript"><!--

function validateDetentionDetails()
{
	
	
	/*if(document.getElementById("detentiondetailsRadio").checked == true) {
		var currentTime = new Date();
		var month = currentTime.getMonth() + 1;
		var day = currentTime.getDate();
		var year = currentTime.getFullYear();
		document.getElementById("detentiondetailsDate").value=day+ "/" + month + "/" + year;
	}
	if(document.getElementById("detentiondetailsRadio1").checked == true){
		document.getElementById("detentiondetailsDate").value="";
		document.getElementById("detantiondetailReasons").value="";
		document.getElementById("detentiondetailsDate").value="";
	}*/
	if(document.getElementById("detentiondetailsRadio1").checked == true){
		document.getElementById("detentiondetailsDate").value="";
		document.getElementById("detantiondetailReasons").value="";
		document.getElementById("detentiondetailsDate").value="";
	}
	

}

function validateDiscontinuedDetails()
{
	   
	
	/*if(document.getElementById("discontinuedDetailsRadio").checked == true){
		var currentTime = new Date();
		var month = currentTime.getMonth() + 1;
		var day = currentTime.getDate();
		var year = currentTime.getFullYear();
		document.getElementById("discontinuedDetailsDate").value=day+ "/" + month + "/" + year;
	}*/
	if(document.getElementById("discontinuedDetailsRadio1").checked == true){
		document.getElementById("discontinuedDetailsDate").value="";
		document.getElementById("discontinuedDetailsReasons").value="";
		document.getElementById("discontinuedDetailsDate").value="";
	}
}

function clearRejoinDate()
{
	document.getElementById("rejoinDetailsDate").value="";
}
function imposeMaxLength(evt, desc) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 255;
	return (desc.length < MaxLen);
}

	function downloadFile(documentId) {
		document.location.href = "DocumentDownloadAction.do?documentId="
				+ documentId;
	}
	
--></SCRIPT>
<script type="text/javascript">
function submitHistory(){
	document.studentEditForm.method.value="viewSubjectGroupHistoryDetails";
	document.studentEditForm.submit();
	
}
	function detailSubmit(count)
	{
		document.getElementById("editcountID").value=count;
		document.studentEditForm.method.value="initDetailMarkEditPage";
		document.studentEditForm.submit();
	}
	function enterRemark()
	{
		document.studentEditForm.method.value="initRemarkEntry";
		document.studentEditForm.submit();
	}
	function detailSemesterSubmit(count)
	{
		document.getElementById("editcountID").value=count;
		document.studentEditForm.method.value="initSemesterMarkEditPage";
		document.studentEditForm.submit();
	}

	function showSportsDescription(){
		document.getElementById("sports_description").style.display = "block";
	}

	function hideSportsDescription(){
		document.getElementById("sports_description").style.display = "none";
		document.getElementById("sportsDescription").value = "";
	}

	function showHandicappedDescription(){
		document.getElementById("handicapped_description").style.display = "block";
	}

	function hideHandicappedDescription(){
		document.getElementById("handicapped_description").style.display = "none";
		document.getElementById("hadnicappedDescription").value = "";
	}
	function detailLateralSubmit()
	{
		document.studentEditForm.method.value="initlateralEntryEditPage";
		document.studentEditForm.submit();
	}
	function detailTransferSubmit()
	{
		document.studentEditForm.method.value="initTransferEntryEditPage";
		document.studentEditForm.submit();
	}
	function getDetailForEdit(applicationNumber,appliedYear,method) {
		document.studentEditForm.method.value=method;
		document.studentEditForm.detailsView.value=false;
		document.studentEditForm.selectedAppNo.value=applicationNumber;
		document.studentEditForm.selectedYear.value=appliedYear;
		document.studentEditForm.submit();
	}
	function imposeMaxLength(evt, Object) {
		var keynum = (evt.which) ? evt.which : event.keyCode;
		if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
			return true;
		}
		var MaxLen = 200;
		return (Object.value.length < MaxLen);
	}
	function winOpen() {
		var url = "studentEdit.do?method=viewHistory_detentionDetails";
		myRef = window
				.open(url, "viewHistoryDetention",
						"left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	function winOpenDiscontinue() {
		var url = "studentEdit.do?method=viewHistory_discontinueDetails";
		myRef = window
				.open(url, "viewHistoryDiscontinue",
						"left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	function winOpenRejoin() {
		var url = "studentEdit.do?method=viewHistory_rejoin";
		myRef = window
				.open(url, "viewHistoryRejoin",
						"left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1");
	}

	function getClasses() {
		var year = document.getElementById("batch").options[document
		                                       			.getElementById("batch").selectedIndex].value;
		var courseId = document.getElementById("courseId").value;
		getclassesByYearAndCourse("rejoinClassMap", courseId, year, "readmittedClass", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "readmittedClass", "- Select -");
	}
</script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script language="JavaScript" src="js/admission/prcadmissionform.js"></script>
<script language="JavaScript" src="js/admission/prcstudentdetails.js"></script>

<html:form action="/PRCstudentEdit" enctype="multipart/form-data"
	method="POST">
	<html:hidden property="method" value="updateApplicationEdit" />
	<html:hidden property="formName" value="studentEditForm" />
	<html:hidden property="selectedAppNo" value="" />
	<html:hidden property="selectedYear" value=""/>
	<html:hidden property="detailsView" value="false" />
	<html:hidden property="pageType" value="21" />
	<html:hidden property="originalStudent.id" styleId="originalStudent" />
	<input type="hidden" name="applicationId"
		value='<bean:write name="studentEditForm" property="applicantDetails.applicationId" />' />
	<input type="hidden" name="courseId"
		value='<bean:write	name="studentEditForm" property="applicantDetails.course.id" />' id="courseId"/>
	<div style="overflow: auto; width: 800px;">
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="admissionForm.studentedit.mainedit.label" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="admissionForm.studentedit.mainedit.label" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">
					<table width="100%" height="1334" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="admissionForm.studentedit.mainedit.label" /></td>
						</tr>
						<tr>
							<td colspan="2" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages></FONT></div>
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
									<td height="22" valign="top">
									<table width="100%" height="22" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.applicationnumber" />:</div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="studentEditForm" property="applicantDetails.applnNo" /></td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.applicationform.prereq.roll.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:text
												name="studentEditForm" property="rollNo" size="10"
												maxlength="10"></nested:text></td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentedit.regno.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:text
												name="studentEditForm" property="regNo" size="10"
												maxlength="10"></nested:text></td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentedit.admissiondate.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:text
												property="applicantDetails.admissionDate" styleId="admDate"
												size="10" maxlength="10"></nested:text> <script
												language="JavaScript">
													new tcal( {
														// form name
														'formname' :'studentEditForm',
														// input name
														'controlname' :'admDate'
													});
												</script></td>
												
												<td height="20" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.inventory.vendor.bank.ac.no" /></div>
												</td>
												<td height="20" class="row-even" align="left">&nbsp;<nested:text
													property="bankAccNo" name="studentEditForm" styleId="admDate"
													size="10" maxlength="15"></nested:text> </td>
												<td class="row-odd"></td>
												<td class="row-even"></td>
												
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
									<td height="22" valign="top">
									<table width="100%" height="22" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">


											<td colspan="3" height="20" class="row-even" align="right"><img
												src='<%=request.getContextPath()%>/PhotoServlet'
												height="150Px" width="150Px" /></td>
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
									<td height="76" valign="top">
									<table width="100%" height="76" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="121" height="23" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.programtype" />:</div>
											</td>
											<td width="168" height="23" class="row-even" align="left">&nbsp;
											<bean:write
												name="studentEditForm"
												property="applicantDetails.selectedCourse.programTypeCode" /></td>
											<td width="99" height="23" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.program" />:</div>
											</td>
											<td width="188" height="23" class="row-even" align="left">&nbsp;
											<bean:write
												name="studentEditForm"
												property="applicantDetails.selectedCourse.programCode" /></td>
											<td width="121" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.course" />:</div>
											</td>
											<td width="208" class="row-even" align="left">&nbsp;
											<bean:write
												name="studentEditForm"
												property="applicantDetails.selectedCourse.code" />	
											 </td>
										</tr>

									</table>
									</td>
									<td background="images/right.gif" width="5" height="76"></td>
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
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="knowledgepro.admission.studentInfo" /></td>
						</tr>
						<tr>
							<td width="48%" class="heading">
							<table width="98%" border="0" align="top" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="206" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="45%" height="45" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.candidateName" />:</div>
											</td>
											<td width="55%" height="25" class="row-even" align="left">
											<%--<table width="100%" border="0" cellpadding="0" cellspacing="0">
					                          		<tr><td><nested:text property="applicantDetails.personalData.firstName" size="20" maxlength="30"></nested:text></td><td><I>First</I></td></tr>
					                              <tr><td><nested:text property="applicantDetails.personalData.middleName" size="20" maxlength="30"></nested:text></td><td><I>Middle</I></td></tr>
					                             <tr><td><nested:text property="applicantDetails.personalData.lastName" size="20" maxlength="30"></nested:text></td><td><I>Last</I></td></tr>
												</table>--%> <nested:text
												property="applicantDetails.personalData.firstName" size="30"
												maxlength="90"></nested:text></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.dob.label" /></div>
											</td>
											<td height="30" class="row-even" align="left"><nested:text
												property="applicantDetails.personalData.dob"
												styleId="dateOfBirth" size="11" maxlength="11"></nested:text>
											<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'studentEditForm',
													// input name
													'controlname' :'dateOfBirth'
												});
											</script></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthplace.label" /></div>
											</td>
											<td height="30" class="row-even" align="left"><nested:text
												property="applicantDetails.personalData.birthPlace"
												size="15" maxlength="50"></nested:text></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthcountry.label" /></div>
											</td>
											<td height="35" class="row-even" align="left"><nested:select
												property="applicantDetails.personalData.birthCountry"
												styleClass="combo" styleId="birthCountry"
												onchange="getStates(this.value,'birthState');">
												<option value=""><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="countries" label="name"
													value="id" />
											</nested:select></td>
										</tr>
										<tr>
											<td height="22" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthstate.label" /></div>
											</td>
											<td height="22" class="row-even" align="left">
											<%
												String dynastyle = "";
											%>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td><logic:notEqual value="Other"
														property="applicantDetails.personalData.birthState"
														name="studentEditForm">
														<nested:select
															property="applicantDetails.personalData.birthState"
															styleClass="combo" styleId="birthState"
															onchange="funcOtherShowHide('birthState','otherBirthState')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<c:if
																test="${studentEditForm.applicantDetails.personalData.birthCountry != null && studentEditForm.applicantDetails.personalData.birthCountry != ''}">
																<c:set var="birthStateMap"
																	value="${baseActionForm.collectionMap['birthStateMap']}" />
																<c:if test="${birthStateMap != null}">
																	<html:optionsCollection name="birthStateMap"
																		label="value" value="key" />
																	<html:option value="Other">Other</html:option>
																</c:if>
															</c:if>
															<logic:notEmpty property="editStates"
																name="studentEditForm">
																<nested:optionsCollection property="editStates"
																	label="name" value="id" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select>
														<%
															dynastyle = "display:none;";
														%>
													</logic:notEqual> <logic:equal value="Other"
														property="applicantDetails.personalData.birthState"
														name="studentEditForm">
														<nested:select
															property="applicantDetails.personalData.birthState"
															styleClass="combo" styleId="birthState"
															onchange="funcOtherShowHide('birthState','otherBirthState')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<c:if
																test="${studentEditForm.applicantDetails.personalData.birthCountry != null && studentEditForm.applicantDetails.personalData.birthCountry != ''}">
																<c:set var="birthStateMap"
																	value="${baseActionForm.collectionMap['birthStateMap']}" />
																<c:if test="${birthStateMap != null}">
																	<html:optionsCollection name="birthStateMap"
																		label="value" value="key" />
																	<html:option value="Other">Other</html:option>
																</c:if>
															</c:if>
															<logic:notEmpty property="editStates"
																name="studentEditForm">
																<nested:optionsCollection property="editStates"
																	label="name" value="id" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select>
														<%
															dynastyle = "display:block;";
														%>
													</logic:equal></td>
												</tr>
												<tr>
													<td><nested:text
														property="applicantDetails.personalData.stateOthers"
														size="10" maxlength="30" styleId="otherBirthState"
														style="<%=dynastyle %>"></nested:text></td>
												</tr>
											</table>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.nationality.label" /></div>
											</td>
											<td height="35" class="row-even" align="left"><nested:select
												property="applicantDetails.personalData.nationality"
												styleClass="combo" styleId="nationality">
												<option value=""><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="nationalities"
													label="name" value="id" />
											</nested:select></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td width="52%" valign="top" class="heading">
							<table width="97%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="206" valign="top">
									<table width="99%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="44%" height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.residentcatg.label" /></div>
											</td>
											<td width="56%" height="20" class="row-even" align="left">
											<nested:select
												property="applicantDetails.personalData.residentCategory"
												styleClass="combo" styleId="residentCategory">
												<option value=""><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="residentTypes"
													label="name" value="id" />
											</nested:select></td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.religion.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td><logic:notEqual value="Other"
														property="applicantDetails.personalData.religionId"
														name="studentEditForm">
														<%
															dynastyle = "display:none;";
														%>
														<nested:select
															property="applicantDetails.personalData.religionId"
															styleClass="combo" styleId="religions"
															onchange="getSubReligion(this.value,'Subreligions');funcOtherShowHide('religions','otherReligion')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<nested:optionsCollection property="religions"
																label="religionName" value="religionId" />
															<html:option value="Other">Other</html:option>
														</nested:select>
													</logic:notEqual> <logic:equal value="Other"
														property="applicantDetails.personalData.religionId"
														name="studentEditForm">
														<%
															dynastyle = "display:block;";
														%>
														<nested:select
															property="applicantDetails.personalData.religionId"
															styleClass="combo" styleId="religions"
															onchange="getSubReligion(this.value,'Subreligions');funcOtherShowHide('religions','otherReligion')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<nested:optionsCollection property="religions"
																label="religionName" value="religionId" />
															<html:option value="Other">Other</html:option>
														</nested:select>

													</logic:equal></td>
												</tr>
												<tr>
													<td><nested:text
														property="applicantDetails.personalData.religionOthers"
														size="10" maxlength="30" styleId="otherReligion"
														style="<%=dynastyle %>"></nested:text></td>
												</tr>
											</table>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.subreligion.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td><logic:notEqual value="Other"
														property="applicantDetails.personalData.subReligionId"
														name="studentEditForm">
														<%
															dynastyle = "display:none;";
														%>
														<nested:select
															property="applicantDetails.personalData.subReligionId"
															styleClass="combo" styleId="Subreligions"
															onchange="funcOtherShowHide('Subreligions','otherSubReligion')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<c:if
																test="${studentEditForm.applicantDetails.personalData.religionId != null && studentEditForm.applicantDetails.personalData.religionId != ''}">
																<c:set var="subReligionMap"
																	value="${baseActionForm.collectionMap['subReligionMap']}" />
																<c:choose>
																	<c:when test="${subReligionMap != null}">
																		<html:optionsCollection name="subReligionMap"
																			label="value" value="key" />
																		<html:option value="Other">
																			<bean:message key="knowledgepro.admin.Other" />
																		</html:option>
																	</c:when>
																	<c:otherwise>
																		<logic:notEmpty property="subReligions"
																			name="studentEditForm">
																			<nested:optionsCollection property="subReligions"
																				label="name" value="id" />
																		</logic:notEmpty>
																		<html:option value="Other">Other</html:option>
																	</c:otherwise>
																</c:choose>
															</c:if>
														</nested:select>
													</logic:notEqual> <logic:equal value="Other"
														property="applicantDetails.personalData.subReligionId"
														name="studentEditForm">
														<%
															dynastyle = "display:block;";
														%>
														<nested:select
															property="applicantDetails.personalData.subReligionId"
															styleClass="combo" styleId="Subreligions"
															onchange="funcOtherShowHide('Subreligions','otherSubReligion')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<html:option value="Other">Other</html:option>
														</nested:select>

													</logic:equal></td>
												</tr>
												<tr>
													<td><nested:text
														property="applicantDetails.personalData.religionSectionOthers"
														size="10" maxlength="30" styleId="otherSubReligion"
														style="<%=dynastyle %>"></nested:text></td>
												</tr>
											</table>
											</td>
										</tr>
										<!--<tr>
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.castcatg.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td><logic:notEqual value="Other"
														property="applicantDetails.personalData.casteId"
														name="studentEditForm">
														<%
															dynastyle = "display:none;";
														%>
														<nested:select
															property="applicantDetails.personalData.casteId"
															styleClass="combo" styleId="castes"
															onchange="funcOtherShowHide('castes','otherCastCatg')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<nested:optionsCollection property="casteList"
																label="casteName" value="casteId" />
															<html:option value="Other">Other</html:option>
														</nested:select>
													</logic:notEqual> <logic:equal value="Other"
														property="applicantDetails.personalData.casteId"
														name="studentEditForm">
														<%
															dynastyle = "display:block;";
														%>
														<nested:select
															property="applicantDetails.personalData.casteId"
															styleClass="combo" styleId="castes"
															onchange="funcOtherShowHide('castes','otherCastCatg')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<nested:optionsCollection property="casteList"
																label="casteName" value="casteId" />
															<html:option value="Other">Other</html:option>
														</nested:select>

													</logic:equal></td>
												</tr>
												<tr>
													<td><nested:text
														property="applicantDetails.personalData.casteOthers"
														size="10" maxlength="30" styleId="otherCastCatg"
														style="<%=dynastyle %>"></nested:text></td>
												</tr>
											</table>
											</td>
										</tr>
										<tr>
											<td height="17" class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="admissionForm.studentinfo.belongsto.label" /></div>
											</td>
											<td height="17" class="row-even" align="left"><nested:radio
												property="applicantDetails.personalData.areaType" value='R'>
												<bean:message
													key="admissionForm.studentinfo.belongsto.rural.text" />
											</nested:radio> <nested:radio
												property="applicantDetails.personalData.areaType" value='U'>
												<bean:message
													key="admissionForm.studentinfo.belongsto.urban.text" />
											</nested:radio></td>
										</tr>
										--><tr>
											<td height="17" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.gender" />:</div>
											</td>

											<td height="17" class="row-even" align="left"><nested:radio
												property="applicantDetails.personalData.gender" value="MALE">
												<bean:message key="admissionForm.studentinfo.sex.male.text" />
											</nested:radio> <nested:radio
												property="applicantDetails.personalData.gender"
												value="FEMALE">
												<bean:message
													key="admissionForm.studentinfo.sex.female.text" />
											</nested:radio></td>
										</tr>
										<tr>
											<%
												String dynaStyle3 = "display:none;";
											%>
											<logic:equal value="true"
												property="applicantDetails.personalData.sportsPerson"
												name="studentEditForm">
												<%
													dynaStyle3 = "display:block;";
												%>
											</logic:equal>
											<td height="17" class="row-odd" width="40%">
											<div align="right"><bean:message
												key="admissionForm.edit.sportsperson.label" /></div>
											</td>
											<td height="17" class="row-even" align="left"><nested:radio
												property="applicantDetails.personalData.sportsPerson"
												value="true" onclick="showSportsDescription()">
												<bean:message key="knowledgepro.applicationform.yes.label" />
											</nested:radio> <nested:radio
												property="applicantDetails.personalData.sportsPerson"
												value="false" onclick="hideSportsDescription()">
												<bean:message key="knowledgepro.applicationform.No.label" />
											</nested:radio>
											<div id="sports_description" style="<%=dynaStyle3%>"><nested:text
												styleId="sportsDescription"
												property="applicantDetails.personalData.sportsDescription"
												maxlength="80" size="15"></nested:text></div>
											</td>
										</tr>
										<tr>
											<%
												String dynaStyle4 = "display:none;";
											%>
											<logic:equal value="true"
												property="applicantDetails.personalData.handicapped"
												name="studentEditForm">
												<%
													dynaStyle4 = "display:block;";
												%>
											</logic:equal>
											<td height="17" class="row-odd" width="40%">
											<div align="right"><bean:message
												key="admissionForm.edit.handicapped.label" /></div>
											</td>
											<td height="17" class="row-even" align="left"><nested:radio
												property="applicantDetails.personalData.handicapped"
												value="true" onclick="showHandicappedDescription()">
												<bean:message key="knowledgepro.applicationform.yes.label" />
											</nested:radio> <nested:radio
												property="applicantDetails.personalData.handicapped"
												value="false" onclick="hideHandicappedDescription()">
												<bean:message key="knowledgepro.applicationform.No.label" />
											</nested:radio>
											<div id="handicapped_description" style="<%=dynaStyle4%>">
											<nested:text styleId="hadnicappedDescription"
												property="applicantDetails.personalData.hadnicappedDescription"
												maxlength="80" size="15"></nested:text></div>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.bloodgroup.label" /></div>
											</td>
											<td height="20" class="row-even" align="left"><nested:select
												property="applicantDetails.personalData.bloodGroup"
												styleClass="combo" styleId="bgType">

												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:option value="O+VE">
													<bean:message key="knowledgepro.admission.report.opositive" />
												</html:option>
												<html:option value="A+VE">
													<bean:message key="knowledgepro.admission.report.apositive" />
												</html:option>
												<html:option value="B+VE">
													<bean:message key="knowledgepro.admission.report.bpositive" />
												</html:option>
												<html:option value="AB+VE">
													<bean:message
														key="knowledgepro.admission.report.abpositive" />
												</html:option>
												<html:option value="O-VE">
													<bean:message key="knowledgepro.admission.report.onegitive" />
												</html:option>
												<html:option value="A-VE">
													<bean:message key="knowledgepro.admission.report.anegitive" />
												</html:option>
												<html:option value="B-VE">
													<bean:message key="knowledgepro.admission.report.bnegitive" />
												</html:option>
												<html:option value="AB-VE">
													<bean:message
														key="knowledgepro.admission.report.abnegitive" />
												</html:option>
												<html:option value="NOT KNOWN">
													<bean:message key="knowledgepro.admission.report.unknown" />
												</html:option>
											</nested:select></td>
										</tr>
										<logic:equal value="true" property="displaySecondLanguage"
											name="studentEditForm">
											<tr>
												<td height="20" class="row-odd" width="40%">
												<div align="right"><bean:message
													key="knowledgepro.applicationform.secLang.label" /></div>
												</td>
												<td height="20" class="row-even" align="left"><html:select
														property="applicantDetails.personalData.secondLanguage" styleClass="body"
														styleId="secondLanguage">
														<html:option value="">
															<bean:message key="knowledgepro.admin.select" />
														</html:option>
														<html:optionsCollection property="secondLanguageList"
															label="value" value="value" />
													</html:select></td>
											</tr>
										</logic:equal>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td><bean:message
														key="admissionForm.phone.cntcode.label" /></td>
													<td><nested:text
														property="applicantDetails.personalData.phNo1" size="3"
														maxlength="4"></nested:text></td>
												</tr>
												<tr>
													<td><bean:message
														key="admissionForm.phone.areacode.label" /></td>
													<td><nested:text
														property="applicantDetails.personalData.phNo2" size="5"
														maxlength="7"></nested:text></td>
												</tr>
												<tr>
													<td><bean:message key="admissionForm.phone.main.label" /></td>
													<td><nested:text
														property="applicantDetails.personalData.phNo3" size="10"
														maxlength="10"></nested:text></td>
												</tr>
											</table>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td><bean:message
														key="admissionForm.phone.cntcode.label" /></td>
													<td><nested:text
														property="applicantDetails.personalData.mobileNo1"
														size="4" maxlength="4"></nested:text></td>
												</tr>
												<tr>
													<td><bean:message key="admissionForm.mob.no.label" />
													</td>
													<td><nested:text
														property="applicantDetails.personalData.mobileNo2"
														size="10" maxlength="10"></nested:text></td>
												</tr>
											</table>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="20" class="row-even" align="left"><nested:text
												property="applicantDetails.personalData.email" size="15"
												maxlength="50"></nested:text> <br />
											</td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="10" height="3"></td>
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
						<logic:equal value="true" property="displayExtraDetails"
							name="studentEditForm">
							<tr>
								<td colspan="2" class="heading">&nbsp;<bean:message
									key="knowledgepro.applicationform.extradetails.label" /></td>
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
												<logic:equal value="true" property="displayMotherTongue"
													name="studentEditForm">
													<td height="23" class="row-even">
													<div align="center"><bean:message
														key="knowledgepro.applicationform.mothertongue.label" /></div>
													</td>
													<td height="23" class="row-even" align="left"><nested:select
														property="applicantDetails.personalData.motherTongue"
														styleClass="combo">
														<html:option value="">-Select-</html:option>
														<nested:optionsCollection property="mothertongues"
															name="studentEditForm" label="languageName"
															value="languageId" />
													</nested:select></td>
												</logic:equal>
												<logic:equal value="true" property="displayHeightWeight"
													name="studentEditForm">
													<td height="23" class="row-even"><bean:message
														key="knowledgepro.applicationform.height.label" />&nbsp;
													<nested:text
														property="applicantDetails.personalData.height" size="5"
														maxlength="5"></nested:text></td>
													<td height="23" class="row-even"><bean:message
														key="knowledgepro.applicationform.weight.label" />&nbsp;
													<nested:text
														property="applicantDetails.personalData.weight" size="6"
														maxlength="6"></nested:text></td>
												</logic:equal>
											</tr>
											<logic:equal value="true" property="displayLanguageKnown"
												name="studentEditForm">
												<tr class="row-white">
													<td height="23" class="row-odd">
													<div align="right"><bean:message
														key="knowledgepro.applicationform.language.label" /></div>
													</td>
													<td height="23" class="row-even" align="left"
														style="width: 180px;"><bean:message
														key="knowledgepro.applicationform.speaklanguage.label" />&nbsp;
													<nested:text
														property="applicantDetails.personalData.languageByLanguageSpeak"
														size="10" maxlength="50"></nested:text></td>
													<td height="23" class="row-even" style="width: 180px;"><bean:message
														key="knowledgepro.applicationform.readlanguage.label" />&nbsp;
													<nested:text
														property="applicantDetails.personalData.languageByLanguageRead"
														size="10" maxlength="50"></nested:text></td>
													<td height="23" class="row-even" style="width: 180px;"><bean:message
														key="knowledgepro.applicationform.writelanguage.label" />&nbsp;
													<nested:text
														property="applicantDetails.personalData.languageByLanguageWrite"
														size="10" maxlength="50"></nested:text></td>
												</tr>
											</logic:equal>

											<logic:equal value="true" property="displayTrainingDetails"
												name="studentEditForm">
												<tr class="row-white">
													<td height="23" colspan="4" class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.applicationform.training.label" /></div>
													</td>
												</tr>
												<tr class="row-white">
													<td height="23" class="row-even" align="center"><bean:message
														key="knowledgepro.applicationform.trainingprog.label" /></td>
													<td height="23" class="row-even" align="left"><nested:text
														property="applicantDetails.personalData.trainingProgName"
														size="10" maxlength="50"></nested:text></td>
													<td height="23" class="row-even"><bean:message
														key="knowledgepro.applicationform.trainingduration.label" /></td>
													<td height="23" class="row-even" align="left"><nested:text
														property="applicantDetails.personalData.trainingDuration"
														size="10" maxlength="10"></nested:text></td>
												</tr>
												<tr class="row-white">

													<td height="23" class="row-even"><bean:message
														key="knowledgepro.applicationform.traininginst.label" /></td>
													<td height="23" class="row-even" align="left"><nested:textarea
														property="applicantDetails.personalData.trainingInstAddress"
														cols="25" rows="4"></nested:textarea></td>

													<td height="23" class="row-even"><bean:message
														key="knowledgepro.applicationform.trainingpurpose.label" /></td>
													<td height="23" class="row-even" align="left"><nested:textarea
														property="applicantDetails.personalData.trainingPurpose"
														cols="25" rows="4"></nested:textarea></td>
												</tr>
											</logic:equal>

											<logic:equal value="true" property="displayAdditionalInfo"
												name="studentEditForm">
												<tr class="row-white">
													<td height="23" colspan="4" class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.applicationform.addninfo.label" /></div>
													</td>
												</tr>
												<tr class="row-white">
													<td height="23" class="row-even" align="right"><bean:message
														key="knowledgepro.applicationform.addninfo.label1" /> <B><bean:write
														property="organizationName" name="studentEditForm" /></B>:</td>
													<td height="23" class="row-even" align="left"><nested:text
														property="applicantDetails.personalData.courseKnownBy"
														size="20" maxlength="50"></nested:text></td>
													<td height="23" class="row-even" align="right"><bean:message
														key="knowledgepro.applicationform.strength.label" /></td>
													<td height="23" class="row-even" align="left"><nested:text
														property="applicantDetails.personalData.strength"
														size="20" maxlength="100"></nested:text></td>
												</tr>
												<tr class="row-white">

													<td height="23" class="row-even" align="right"><bean:message
														key="knowledgepro.applicationform.addninfo.label2" /><B><bean:write
														property="applicantDetails.course.name"
														name="studentEditForm" /> </B>:</td>
													<td height="23" class="row-even" align="left"><nested:text
														property="applicantDetails.personalData.courseOptReason"
														size="20" maxlength="100"></nested:text></td>

													<td height="23" class="row-even" align="right"><bean:message
														key="knowledgepro.applicationform.weakness.label" /></td>
													<td height="23" class="row-even" align="left"><nested:text
														property="applicantDetails.personalData.weakness"
														size="20" maxlength="100"></nested:text></td>
												</tr>
												<tr class="row-white">

													<td height="23" colspan="3" class="row-even" align="right"><bean:message
														key="knowledgepro.applicationform.addninfo.label3" /> <B><bean:write
														property="applicantDetails.course.name"
														name="studentEditForm" /></B>:</td>
													<td height="23" class="row-even" align="left"><nested:textarea
														property="applicantDetails.personalData.otherAddnInfo"
														cols="25" rows="4"></nested:textarea></td>

												</tr>
											</logic:equal>
											<logic:equal value="true" property="displayExtracurricular"
												name="studentEditForm">
												<logic:notEmpty
													property="applicantDetails.personalData.studentExtracurricularsTos"
													name="studentEditForm">
													<tr class="row-white">
														<td height="23" colspan="4" class="row-odd">
														<div align="center"><bean:message
															key="knowledgepro.applicationform.extracurr.label" /></div>
														</td>
													</tr>
													<tr class="row-white">
														<td height="23" class="row-even" align="right"><bean:message
															key="knowledgepro.applicationform.extracurr.label2" /></td>
														<td height="23" class="row-even" align="left"><nested:select
															property="applicantDetails.personalData.extracurricularIds"
															styleClass="row-even" multiple="true">
															<nested:optionsCollection
																property="applicantDetails.personalData.studentExtracurricularsTos"
																name="studentEditForm" label="name" value="id" />
														</nested:select></td>
														<td height="23" class="row-even" align="left">&nbsp;</td>
														<td height="23" class="row-even" align="left">&nbsp;</td>
													</tr>
												</logic:notEmpty>
											</logic:equal>
										</table>


										</td>
										<td background="images/right.gif" width="5" height="57"></td>
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
						</logic:equal>
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="knowledgepro.admission.passportDetails" /></td>
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
									<td height="57" valign="top">
									<table width="100%" height="57" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="212" height="23" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.passportNo" /></div>
											</td>
											<td width="236" height="23" class="row-even" align="left"><nested:text
												property="applicantDetails.personalData.passportNo"
												maxlength="10"></nested:text></td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.issuingCountry" /></div>
											</td>
											<td width="244" class="row-even" align="left"><nested:select
												property="applicantDetails.personalData.passportCountry"
												styleClass="combo" styleId="passportCountry">
												<option value="0"><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="countries" label="name"
													value="id" />
											</nested:select></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.validUpto" /></div>
											</td>
											<td height="25" class="row-white" align="left"><nested:text
												property="applicantDetails.personalData.passportValidity"
												styleId="passportValidity" styleClass="row-white" size="15"
												maxlength="15"></nested:text> <script language="JavaScript">
									new tcal( {
										// form name
										'formname' :'studentEditForm',
										// input name
										'controlname' :'passportValidity'
									});
								</script></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.applicationform.residentpermit.label" /></div>
											</td>
											<td class="row-white" align="left"><html:text
												property="applicantDetails.personalData.residentPermitNo"
												styleId="residentPermit" styleClass="row-white" size="10"
												maxlength="15"></html:text></td>
										</tr>
										<tr class="row-white">
											<td width="350" height="23" colspan="2" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.applicationform.policedate.label" /></div>
											</td>
											<td width="200" height="23" class="row-even" align="left">
											<html:text
												property="applicantDetails.personalData.residentPermitDate"
												styleId="permitDate" size="10" maxlength="10"></html:text> <script
												language="JavaScript">
												new tcal( {
													// form name
													'formname' :'studentEditForm',
													// input name
													'controlname' :'permitDate'
												});
												</script></td>

											<td width="244" class="row-even" align="left"></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="57"></td>
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
						<tr>
							<td colspan="2" class="heading" align="left"><bean:message
								key="admissionForm.edit.vehicledetails.label" /></td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="29" valign="top">
									<table width="100%" height="24" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="212" height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.edit.vehicletypes.label" /></div>
											</td>
											<td width="236" height="25" class="row-even" align="left">
											<nested:text
												property="applicantDetails.vehicleDetail.vehicleType"
												styleId="vehicleType" maxlength="15"></nested:text></td>
											<td width="224" height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.edit.vehicleno.label" /></div>
											</td>
											<td width="244" height="25" class="row-even" align="left"><nested:text
												property="applicantDetails.vehicleDetail.vehicleNo"
												styleId="vehicleNo" maxlength="15"></nested:text></td>
										</tr>

									</table>
									</td>
									<td background="images/right.gif" width="5" height="29"></td>
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
						<tr>
							<td colspan="2" class="heading" align="left"><bean:message
								key="admissionForm.edit.admissiondetails.label" /></td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="29" valign="top">
									<table width="100%" height="24" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="212" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.admitted.through" /></div>
											</td>
											<td width="236" height="25" class="row-even" align="left">
											<nested:select property="applicantDetails.admittedThroughId"
												styleId="admittedThroughId" styleClass="combo">
												<html:option value="">-Select-</html:option>
												<nested:optionsCollection property="admittedThroughList"
													label="name" value="id" />

											</nested:select></td>
											<td width="224" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.edit.subgrp.label" /></div>
											</td>
											<td width="244" height="25" class="row-even" align="left">
											<nested:select property="applicantDetails.subjectGroupIds"
												styleId="subjectGroupId" styleClass="row-even"
												multiple="multiple" size="4" style="width:200px;height:100px">
												<logic:notEmpty name="studentEditForm" property="subGroupList">
												<nested:optionsCollection property="subGroupList"
													label="name" value="id" />
													</logic:notEmpty>
											</nested:select>
											<p><logic:equal value="present"
												property="subjectGroupsDetailsLink" name="studentEditForm">
												<a
													href="studentEdit.do?method=viewHistory_SubjectGroupDetails&viewStudentId=<bean:write property="originalStudent.id" name="studentEditForm"/>"
													target="_blank">View History</a>


											</logic:equal></p>
											<a href="javascript:void(0)" onclick="submitHistory()">Update History</a>
											</td>
										</tr>
										<tr class="row-white">
											<td width="212" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.edit.freeship.label" /></div>
											</td>
											<td width="236" height="25" class="row-even" align="left">
											<nested:radio property="applicantDetails.isFreeShip"
												value="true">
												<bean:message key="knowledgepro.applicationform.yes.label" />
											</nested:radio> <nested:radio property="applicantDetails.isFreeShip"
												value="false">
												<bean:message key="knowledgepro.applicationform.No.label" />
											</nested:radio></td>
											<td width="224" height="25" class="row-odd" align="right"><span
												class="Mandatory">*</span><bean:message
												key="knowledgepro.attendance.class.col" /></td>
											<td width="244" height="25" class="row-even" align="left">
											<nested:select property="classSchemeId"
												styleId="classSchemeId" styleClass="combo">
												<html:option value="0">-Select-</html:option>
												<logic:notEmpty name="studentEditForm" property="classesMap">
												<nested:optionsCollection property="classesMap"
													label="value" value="key" />
													</logic:notEmpty>
											</nested:select></td>
										</tr>
										<tr class="row-white">
											<td width="212" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.edit.lig.label" /></div>
											</td>
											<td width="236" height="25" class="row-even" align="left">
											<nested:radio property="applicantDetails.isLIG" value="true">
												<bean:message key="knowledgepro.applicationform.yes.label" />
											</nested:radio> <nested:radio property="applicantDetails.isLIG"
												value="false">
												<bean:message key="knowledgepro.applicationform.No.label" />
											</nested:radio></td>
											<td width="224" height="25" class="row-odd" align="right"></td>
											<td width="244" height="25" class="row-even" align="left">

											</td>
										</tr>

									</table>
									</td>
									<td background="images/right.gif" width="5" height="29"></td>
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


						<logic:equal value="true" property="workExpNeeded"
							name="studentEditForm">
							<tr>
								<td colspan="2" class="heading" align="left"><bean:message
									key="knowledgepro.applicationform.workexp.label" /></td>
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
										<td height="31" valign="top">
										<table width="100%" border="0" cellpadding="0" cellspacing="1">
											<tr class="row-white">
												<td height="27" class="row-odd" width="15%">
												<div align="left"><bean:message
													key="knowledgepro.applicationform.orgName.label" /></div>
												</td>
												<td height="27" class="row-odd" width="15%">
												<div align="center"><bean:message
													key="knowledgepro.applicationform.jobdesc.label" /></div>
												</td>
												<td class="row-odd" width="15%">
												<div align="center"><bean:message
													key="knowledgepro.applicationform.fromdt.label" /></div>
												</td>
												<td class="row-odd" width="15%">
												<div align="center"><bean:message
													key="knowledgepro.applicationform.todt.label" /></div>
												</td>
												<td height="27" class="row-odd" width="15%">
												<div align="center"><bean:message
													key="knowledgepro.applicationform.lastsal.label" /></div>
												</td>
												<td height="27" class="row-odd" width="15%">
												<div align="center"><bean:message
													key="knowledgepro.applicationform.reportto.label" /></div>
												</td>
											</tr>
											<nested:iterate property="applicantDetails.workExpList"
												id="exp" name="studentEditForm"
												type="com.kp.cms.to.admin.ApplicantWorkExperienceTO"
												indexId="count">
												<tr class="row-white">
													<td height="27" class="row-even">
													<div align="left"><nested:text
														property="organization" styleClass="textbox" size="15"
														maxlength="100" /></div>
													</td>
													<td height="27" class="row-even">
													<div align="center"><nested:text property="position"
														styleClass="textbox" size="10" maxlength="30" /></div>
													</td>
													<td width="224" class="row-even">
													<div align="center">
													<%
														String fromid = "expFromdate" + count;
																	String toid = "expTodate" + count;
													%> <nested:text property="fromDate" styleId="<%=fromid%>"
														size="10" maxlength="10" /> <script language="JavaScript">
								new tcal ({
									// form name
									'formname': 'studentEditForm',
									// input name
									'controlname': '<%=fromid%>'
								});</script></div>
													</td>
													<td class="row-even">
													<div align="center"><nested:text property="toDate"
														styleId="<%=toid%>" size="10" maxlength="10" /> <script
														language="JavaScript">
							new tcal ({
								// form name
								'formname': 'studentEditForm',
								// input name
								'controlname': '<%=toid%>'
							});</script></div>
													</td>
													<td height="27" class="row-even">
													<div align="center"><nested:text property="salary"
														styleClass="textbox" size="10" maxlength="10" /></div>
													</td>
													<td height="27" class="row-even">
													<div align="center"><nested:text
														property="reportingTo" styleClass="textbox" size="10"
														maxlength="50" /></div>
													</td>
												</tr>
											</nested:iterate>

										</table>
										</td>
										<td background="images/right.gif" width="5" height="31"></td>
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
						</logic:equal>
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="admissionForm.studentinfo.currAddr.label" /></td>
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
									<td height="91" valign="top">
									<table width="100%" height="90" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="212" height="20" class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="236" height="20" class="row-even" align="left">&nbsp;
											<nested:text
												property="applicantDetails.personalData.currentAddressLine1"
												size="15" maxlength="100"></nested:text></td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.currentAddressLine2"
												size="15" maxlength="100"></nested:text></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.currentCityName"
												size="15" maxlength="30"></nested:text></td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td class="row-white" align="left">&nbsp; <nested:select
												property="applicantDetails.personalData.currentCountryId"
												styleClass="combo" styleId="currentCountryName"
												onchange="getTempAddrStates(this.value,'currentStateName');">
												<option value="0"><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="countries" label="name"
													value="id" />
											</nested:select></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td><logic:notEqual value="Other"
														property="applicantDetails.personalData.currentStateId"
														name="studentEditForm">
														<%
															dynastyle = "display:none;";
														%>
											&nbsp;&nbsp;<nested:select
															property="applicantDetails.personalData.currentStateId"
															styleClass="combo" styleId="currentStateName"
															onchange="funcOtherShowHide('currentStateName','otherTempAddrState')">
															<option value="0"><bean:message
																key="knowledgepro.admin.select" /></option>
															<c:if
																test="${studentEditForm.applicantDetails.personalData.currentCountryId != null && studentEditForm.applicantDetails.personalData.currentCountryId!= ''}">
																<c:set var="tempAddrStateMap"
																	value="${baseActionForm.collectionMap['tempAddrStateMap']}" />
																<c:if test="${tempAddrStateMap != null}">
																	<html:optionsCollection name="tempAddrStateMap"
																		label="value" value="key" />
																	<html:option value="Other">Other</html:option>
																</c:if>
															</c:if>
															<logic:notEmpty property="editCurrentStates"
																name="studentEditForm">
																<nested:optionsCollection property="editCurrentStates"
																	label="name" value="id" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select>
													</logic:notEqual> <logic:equal value="Other"
														property="applicantDetails.personalData.currentStateId"
														name="studentEditForm">
														<%
															dynastyle = "display:block;";
														%>
														<nested:select
															property="applicantDetails.personalData.currentStateId"
															styleClass="combo" styleId="currentStateName"
															onchange="funcOtherShowHide('currentStateName','otherTempAddrState')">
															<option value="0"><bean:message
																key="knowledgepro.admin.select" /></option>
															<c:if
																test="${studentEditForm.applicantDetails.personalData.currentCountryId != null && studentEditForm.applicantDetails.personalData.currentCountryId!= ''}">
																<c:set var="tempAddrStateMap"
																	value="${baseActionForm.collectionMap['tempAddrStateMap']}" />
																<c:if test="${tempAddrStateMap != null}">
																	<html:optionsCollection name="tempAddrStateMap"
																		label="value" value="key" />
																	<html:option value="Other">Other</html:option>
																</c:if>
															</c:if>
															<logic:notEmpty property="editCurrentStates"
																name="studentEditForm">
																<nested:optionsCollection property="editCurrentStates"
																	label="name" value="id" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select>

													</logic:equal></td>
												</tr>
												<tr>
													<td><nested:text
														property="applicantDetails.personalData.currentAddressStateOthers"
														size="10" maxlength="30" styleId="otherTempAddrState"
														style="<%=dynastyle %>"></nested:text></td>
												</tr>
											</table>
											</td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;<nested:text
												property="applicantDetails.personalData.currentAddressZipCode"
												size="10" maxlength="10"></nested:text></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
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
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="admissionForm.studentinfo.permAddr.label" /></td>
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
									<td height="91" valign="top">
									<table width="100%" height="90" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="212" height="20" class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="236" height="20" class="row-even" align="left">&nbsp;
											<nested:text
												property="applicantDetails.personalData.permanentAddressLine1"
												size="15" maxlength="100"></nested:text></td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.permanentAddressLine2"
												size="15" maxlength="100"></nested:text></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.permanentCityName"
												size="15" maxlength="30"></nested:text></td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td class="row-white" align="left">&nbsp; <nested:select
												property="applicantDetails.personalData.permanentCountryId"
												styleClass="combo" styleId="permanentCountryName"
												onchange="getPermAddrStates(this.value,'permanentStateName');">
												<option value="0"><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="countries" label="name"
													value="id" />
											</nested:select></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td><logic:notEqual value="Other"
														property="applicantDetails.personalData.permanentStateId"
														name="studentEditForm">
														<%
															dynastyle = "display:none;";
														%>
											&nbsp;&nbsp;<nested:select
															property="applicantDetails.personalData.permanentStateId"
															styleClass="combo" styleId="permanentStateName"
															onchange="funcOtherShowHide('permanentStateName','otherPermAddrState')">
															<option value="0"><bean:message
																key="knowledgepro.admin.select" /></option>
															<c:if
																test="${studentEditForm.applicantDetails.personalData.permanentCountryId != null && studentEditForm.applicantDetails.personalData.permanentCountryId!= ''}">
																<c:set var="permAddrStateMap"
																	value="${baseActionForm.collectionMap['permAddrStateMap']}" />
																<c:if test="${permAddrStateMap != null}">
																	<html:optionsCollection name="permAddrStateMap"
																		label="value" value="key" />
																	<html:option value="Other">Other</html:option>
																</c:if>
															</c:if>
															<logic:notEmpty property="editPermanentStates"
																name="studentEditForm">
																<nested:optionsCollection property="editPermanentStates"
																	label="name" value="id" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select>
													</logic:notEqual> <logic:equal value="Other"
														property="applicantDetails.personalData.permanentStateId"
														name="studentEditForm">
														<%
															dynastyle = "display:block;";
														%>
														<nested:select
															property="applicantDetails.personalData.permanentStateId"
															styleClass="combo" styleId="permanentStateName"
															onchange="funcOtherShowHide('permanentStateName','otherPermAddrState')">
															<option value="0"><bean:message
																key="knowledgepro.admin.select" /></option>
															<c:if
																test="${studentEditForm.applicantDetails.personalData.permanentCountryId != null && studentEditForm.applicantDetails.personalData.permanentCountryId!= ''}">
																<c:set var="permAddrStateMap"
																	value="${baseActionForm.collectionMap['permAddrStateMap']}" />
																<c:if test="${permAddrStateMap != null}">
																	<html:optionsCollection name="permAddrStateMap"
																		label="value" value="key" />
																	<html:option value="Other">Other</html:option>
																</c:if>
															</c:if>
															<logic:notEmpty property="editPermanentStates"
																name="studentEditForm">
																<nested:optionsCollection property="editPermanentStates"
																	label="name" value="id" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select>

													</logic:equal></td>
												</tr>
												<tr>
													<td><nested:text
														property="applicantDetails.personalData.permanentAddressStateOthers"
														size="10" maxlength="30" styleId="otherPermAddrState"
														style="<%=dynastyle %>"></nested:text></td>
												</tr>
											</table>
											</td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory"></span><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.permanentAddressZipCode"
												size="10" maxlength="10"></nested:text></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
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

						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="knowledgepro.admission.educationalInfo" /></td>
						</tr>
						<tr>
							<td colspan="2" valign="top">
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
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td height="25" class="row-odd"><bean:message
												key="knowledgepro.admission.qualification" /></td>
											<td class="row-odd">Exam Name</td>
											<td class="row-odd">
											<div align="left"><bean:message
												key="knowledgepro.admission.universityBoard" /></div>
											</td>
											<td class="row-odd">
											<div align="left"><bean:message
												key="knowledgepro.admission.instituteName" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="admissionForm.education.State.label" /></div>
											</td>
											<td class="row-odd"><bean:message
												key="knowledgepro.admission.passingYear" /></td>
											<td class="row-odd"><bean:message
												key="knowledgepro.applicationform.passingmonth" /></td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.marksObtained" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.totalMarks" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.attempts" /></div>
											</td>
											<td class="row-odd"><bean:message
												key="knowledgepro.applicationform.prevregno.label" /></td>
											<td class="row-odd">
											<div align="center"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.prereqdef.percentage" /></div>
											</td>
										</tr>
										<c:set var="temp" value="0" />
										<logic:notEmpty name="studentEditForm" property="applicantDetails.ednQualificationList">
										<nested:iterate
											property="applicantDetails.ednQualificationList"
											id="ednQualList" indexId="count">
											<%
												String dynaid = "UniversitySelect" + count;
														String otheruniversityid = "OtherUniversity" + count;
														String otherinstituteid = "OtherInstitute" + count;
														String dynaYearId = "YOP" + count;
														String dynaStateId = "State" + count;
														String dynaMonthId = "Month" + count;
														String dynaExamId = "Exam" + count;
														String dynaAttemptId = "Attempt" + count;
														String instituteId = count + "Institute";
														String collegeJsMethod = "getColleges('Map" + count
																+ "',this," + count + ");";
														String insthide = "funcOtherShowHide('" + instituteId
																+ "','" + otherinstituteid + "')";
											%>
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td width="5%" height="25" class="row-even">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td width="11%" height="25" class="row-even"><bean:write
															name="ednQualList" property="docName" /></td>

														<td width="9%" class="row-even">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="examConfigured">
															<c:set var="dexmid"><%=dynaExamId%></c:set>
															<nested:select property="selectedExamId"
																styleClass="comboSmall" styleId='<%=dynaExamId %>'>
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<logic:notEmpty name="ednQualList" property="examTos">
																	<html:optionsCollection name="ednQualList"
																		property="examTos" label="name" value="id" />
																</logic:notEmpty>
															</nested:select>
															<script type="text/javascript">
																var exmid= '<nested:write property="selectedExamId"/>';
																document.getElementById("<c:out value='${dexmid}'/>").value = exmid;
															</script>
														</logic:equal></div>
														</td>


														<td width="20%" class="row-even">
														<div align="left">
														<%
															String dynahide = collegeJsMethod
																					+ "funcOtherShowHide('" + dynaid + "','"
																					+ otheruniversityid + "')";
														%>
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td><logic:notEqual value="Other"
																	property="universityId" name="ednQualList">
																	<%
																		dynastyle = "display:none;";
																	%>
																	<nested:select property="universityId"
																		styleClass="combo" styleId="<%=dynaid %>"
																		onchange='<%=dynahide %>'>
																		<option value=""><bean:message
																			key="knowledgepro.admin.select" /></option>
																		<logic:notEmpty property="universityList"
																			name="ednQualList">
																			<nested:optionsCollection property="universityList"
																				name="ednQualList" label="name" value="id" />
																		</logic:notEmpty>
																		<logic:empty property="universityList"
																			name="ednQualList">
																			<nested:optionsCollection property="universities"
																				name="studentEditForm" label="name" value="id" />
																		</logic:empty>
																		<html:option value="Other">Other</html:option>
																	</nested:select>
																</logic:notEqual> <logic:equal value="Other" property="universityId"
																	name="ednQualList">
																	<%
																		dynastyle = "display:block;";
																	%>
																	<nested:select property="universityId"
																		styleClass="combo" styleId="<%=dynaid %>"
																		onchange='<%=dynahide %>'>
																		<option value=""><bean:message
																			key="knowledgepro.admin.select" /></option>
																		<nested:optionsCollection property="universities"
																			name="studentEditForm" label="name" value="id" />
																		<html:option value="Other">Other</html:option>
																	</nested:select>

																</logic:equal></td>
															</tr>
															<tr>
																<td><nested:text property="universityOthers"
																	size="10" maxlength="10"
																	styleId="<%=otheruniversityid %>"
																	style="<%=dynastyle %>"></nested:text></td>
															</tr>
														</table>
														</div>
														</td>
														<td width="22%" class="row-even">
														<div align="left">
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td><logic:notEqual value="Other"
																	property="institutionId" name="ednQualList">
																	<%
																		dynastyle = "display:none;";
																	%>
																	<c:set var="tempUniversity">
																		<nested:write property="universityId" />
																	</c:set>
																	<nested:select property="institutionId"
																		styleClass="combo" styleId='<%=instituteId %>'
																		onchange='<%=insthide %>'>
																		<option value=""><bean:message
																			key="knowledgepro.admin.select" /></option>
																		<c:set var="tempKey">Map<c:out
																				value="${count}" />
																		</c:set>
																		<c:if
																			test="${tempUniversity != null && tempUniversity != '' && tempUniversity != 'Other'}">
																			<c:set var="Map"
																				value="${baseActionForm.collectionMap[tempKey]}" />
																			<c:if test="${Map != null}">
																				<html:optionsCollection name="Map" label="value"
																					value="key" />
																				<html:option value="Other">Other</html:option>
																			</c:if>
																		</c:if>

																		<logic:notEmpty property="instituteList"
																			name="ednQualList">
																			<nested:optionsCollection property="instituteList"
																				name="ednQualList" label="name" value="id" />
																		</logic:notEmpty>
																		<html:option value="Other">Other</html:option>


																	</nested:select>
																</logic:notEqual> <logic:equal value="Other" property="institutionId"
																	name="ednQualList">
																	<%
																		dynastyle = "display:block;";
																	%>
																	<nested:select property="institutionId"
																		styleClass="combo" styleId='<%=instituteId %>'
																		onchange='<%=insthide %>'>
																		<option value=""><bean:message
																			key="knowledgepro.admin.select" /></option>
																		<html:option value="Other">Other</html:option>
																	</nested:select>

																</logic:equal></td>
															</tr>
															<tr>
																<td><nested:text property="otherInstitute"
																	size="10" maxlength="45"
																	styleId="<%=otherinstituteid %>"
																	style="<%=dynastyle %>"></nested:text></td>
															</tr>
														</table>
														</div>
														</td>
														<td width="16%" class="row-even"><c:set
															var="dstateid"><%=dynaStateId%></c:set> <nested:select
															property="stateId" styleClass="combo"
															styleId='<%=dynaStateId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<logic:notEmpty name="studentEditForm"
																property="ednStates">
																<nested:optionsCollection name="studentEditForm"
																	property="ednStates" label="name" value="id" />
															</logic:notEmpty>
															<option value="OUTSIDEINDIA"><bean:message
																key="admissionForm.education.outsideindia.label" /></option>
														</nested:select> <script type="text/javascript">
																var stid= '<nested:write property="stateId"/>';
																document.getElementById("<c:out value='${dstateid}'/>").value = stid;
															</script></td>
														<td width="16%" class="row-even"><c:set var="dyopid"><%=dynaYearId%></c:set>
														<nested:select property="yearPassing"
															styleClass="comboSmall" styleId='<%=dynaYearId %>'>
															<option value="0"><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderYear normalYear="true"></cms:renderYear>
														</nested:select> <script type="text/javascript">
																var yopid= '<nested:write property="yearPassing"/>';
																document.getElementById("<c:out value='${dyopid}'/>").value = yopid;
															</script></td>
														<td width="16%" class="row-even"><c:set var="dmonid"><%=dynaMonthId%></c:set>
														<nested:select property="monthPassing"
															styleClass="comboSmall" styleId='<%=dynaMonthId %>'>
															<html:option value="0">
																<bean:message key="knowledgepro.admin.select" />
															</html:option>
															<html:option value="1">JAN</html:option>
															<html:option value="2">FEB</html:option>
															<html:option value="3">MAR</html:option>
															<html:option value="4">APR</html:option>
															<html:option value="5">MAY</html:option>
															<html:option value="6">JUN</html:option>
															<html:option value="7">JUL</html:option>
															<html:option value="8">AUG</html:option>
															<html:option value="9">SEPT</html:option>
															<html:option value="10">OCT</html:option>
															<html:option value="11">NOV</html:option>
															<html:option value="12">DEC</html:option>
														</nested:select> <script type="text/javascript">
																var monid= '<nested:write property="monthPassing"/>';
																document.getElementById("<c:out value='${dmonid}'/>").value = monid;
															</script></td>
														<bean:define id="qualId" property="id" name="ednQualList"></bean:define>
														<input type="hidden" id="editcountID" name="editcountID">
														<logic:equal value="true" property="semesterWise"
															name="ednQualList">
															<logic:equal value="false" property="consolidated"
																name="ednQualList">
																<%
																	String semsterMarkLink = "studentEdit.do?method=initSemesterMarkEditPage&editcountID="
																									+ qualId;
																%>
																<td class="row-even" colspan="2">
																<div align="center"><a href="#"
																	onclick="detailSemesterSubmit('<%=qualId%>')">Semester
																Marks Edit</a></div>
																</td>

															</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated"
															name="ednQualList">
															<logic:equal value="false" property="semesterWise"
																name="ednQualList">
																<td class="row-even" colspan="2">
																<%
																	String detailMarkLink = "studentEdit.do?method=initDetailMarkEditPage&editcountID="
																									+ qualId;
																%>
																<div align="center"><a href="#"
																	onclick="detailSubmit('<%=qualId%>')">Detailed
																Marks Edit</a></div>
																</td>

															</logic:equal>
														</logic:equal>
														<logic:notEqual value="true" property="semesterWise"
															name="ednQualList">
															<logic:notEqual value="false" property="consolidated"
																name="ednQualList">
																<td class="row-even">
																<div align="center">
																<nested:text property="marksObtained" size="5" maxlength="9"></nested:text>
																</div>
																</td>
																<td class="row-even">
																<div align="center"><nested:text property="totalMarks" size="5" maxlength="9"></nested:text></div>
																</td>
															</logic:notEqual>
														</logic:notEqual>
														<td width="9%" class="row-even">
														<div align="center"><nested:select
															property="noOfAttempts" styleClass="comboSmall"
															styleId="noOfAttempts">
															<html:option value="1">1</html:option>
															<html:option value="2">2</html:option>
															<html:option value="3">3</html:option>
															<html:option value="4">4</html:option>
															<html:option value="5">5</html:option>
															<html:option value="6">6</html:option>
														</nested:select></div>
														</td>
														<td width="9%" class="row-even">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="lastExam">
															<nested:text property="previousRegNo" size="10"
																maxlength="15" />
														</logic:equal></div>
														</td>
														<td width="8%" class="row-even"><div align="center">
											              <nested:text property="percentage" size="5" maxlength="8" onkeypress="return isDecimalNumberKey(this.value,event)"></nested:text>
											            </div></td>
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td height="25" class="row-white">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td height="25" class="row-white"><bean:write
															name="ednQualList" property="docName" /></td>
														<td class="row-white">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="examConfigured">
															<c:set var="dexmid"><%=dynaExamId%></c:set>
															<nested:select property="selectedExamId"
																styleClass="comboSmall" styleId='<%=dynaExamId %>'>
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<logic:notEmpty name="ednQualList" property="examTos">
																	<html:optionsCollection name="ednQualList"
																		property="examTos" label="name" value="id" />
																</logic:notEmpty>
															</nested:select>
															<script type="text/javascript">
																exmid= '<nested:write property="selectedExamId"/>';
																document.getElementById("<c:out value='${dexmid}'/>").value = exmid;
															</script>
														</logic:equal></div>
														</td>

														<td class="row-white">
														<div align="left">
														<%
															String dynahide = collegeJsMethod
																					+ "funcOtherShowHide('" + dynaid + "','"
																					+ otheruniversityid + "')";
														%>
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td><logic:notEqual value="Other"
																	property="universityId" name="ednQualList">
																	<%
																		dynastyle = "display:none;";
																	%>
																	<nested:select property="universityId"
																		styleClass="combo" styleId='<%=dynaid %>'
																		onchange='<%=dynahide %>'>
																		<option value=""><bean:message
																			key="knowledgepro.admin.select" /></option>
																			<nested:optionsCollection property="universities"
																			name="studentEditForm" label="name" value="id" />
																		<html:option value="Other">Other</html:option>
																	</nested:select>
																</logic:notEqual> <logic:equal value="Other" property="universityId"
																	name="ednQualList">
																	<%
																		dynastyle = "display:block;";
																	%>
																	<nested:select property="universityId"
																		styleClass="combo" styleId="<%=dynaid %>"
																		onchange='<%=dynahide %>'>
																		<option value=""><bean:message
																			key="knowledgepro.admin.select" /></option>
																		<logic:notEmpty property="universityList"
																			name="ednQualList">
																			<nested:optionsCollection property="universityList"
																				name="ednQualList" label="name" value="id" />
																		</logic:notEmpty>
																		<logic:empty property="universityList"
																			name="ednQualList">
																			<nested:optionsCollection property="universities"
																				name="studentEditForm" label="name" value="id" />
																		</logic:empty>
																		<html:option value="Other">Other</html:option>
																	</nested:select>

																</logic:equal></td>
															</tr>
															<tr>
																<td><nested:text property="universityOthers"
																	size="10" maxlength="10"
																	styleId="<%=otheruniversityid %>"
																	style="<%=dynastyle %>"></nested:text></td>
															</tr>
														</table>
														</div>
														</td>
														<td class="row-white">
														<div align="left">
														<table width="100%" border="0" cellpadding="0"
															cellspacing="0">
															<tr>
																<td><logic:notEqual value="Other"
																	property="institutionId" name="ednQualList">
																	<%
																		dynastyle = "display:none;";
																	%>
																	<c:set var="tempUniversity">
																		<nested:write property="universityId" />
																	</c:set>
																	<nested:select property="institutionId"
																		styleClass="combo" styleId='<%=instituteId %>'
																		onchange='<%=insthide %>'>
																		<option value=""><bean:message
																			key="knowledgepro.admin.select" /></option>
																		<c:set var="tempKey">Map<c:out
																				value="${count}" />
																		</c:set>
																		<c:if
																			test="${tempUniversity != null && tempUniversity != '' && tempUniversity != 'Other'}">
																			<c:set var="Map"
																				value="${baseActionForm.collectionMap[tempKey]}" />
																			<c:if test="${Map != null}">
																				<html:optionsCollection name="Map" label="value"
																					value="key" />
																				<html:option value="Other">Other</html:option>
																			</c:if>

																		</c:if>

																		<logic:notEmpty property="instituteList"
																			name="ednQualList">
																			<nested:optionsCollection property="instituteList"
																				name="ednQualList" label="name" value="id" />
																		</logic:notEmpty>
																		<html:option value="Other">Other</html:option>


																	</nested:select>
																</logic:notEqual> <logic:equal value="Other" property="institutionId"
																	name="ednQualList">
																	<%
																		dynastyle = "display:block;";
																	%>
																	<nested:select property="institutionId"
																		styleClass="combo" styleId='<%=instituteId %>'
																		onchange='<%=insthide %>'>
																		<option value=""><bean:message
																			key="knowledgepro.admin.select" /></option>
																		<html:option value="Other">Other</html:option>
																	</nested:select>

																</logic:equal></td>
															</tr>
															<tr>
																<td><nested:text property="otherInstitute"
																	size="10" maxlength="45"
																	styleId="<%=otherinstituteid %>"
																	style="<%=dynastyle %>"></nested:text></td>
															</tr>
														</table>
														</div>
														</td>
														<td class="row-white"><c:set var="dstateid"><%=dynaStateId%></c:set>
														<nested:select property="stateId" styleClass="combo"
															styleId='<%=dynaStateId %>'>
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<logic:notEmpty name="studentEditForm"
																property="ednStates">
																<nested:optionsCollection name="studentEditForm"
																	property="ednStates" label="name" value="id" />
															</logic:notEmpty>
															<option value="OUTSIDEINDIA"><bean:message
																key="admissionForm.education.outsideindia.label" /></option>
														</nested:select> <script type="text/javascript">
																var stid= '<nested:write property="stateId"/>';
																document.getElementById("<c:out value='${dstateid}'/>").value = stid;
															</script></td>
														<td class="row-white"><c:set var="dyopid"><%=dynaYearId%></c:set>
														<nested:select property="yearPassing"
															styleClass="comboSmall" styleId='<%=dynaYearId %>'>
															<option value="0"><bean:message
																key="knowledgepro.admin.select" /></option>
															<cms:renderYear normalYear="true"></cms:renderYear>
														</nested:select> <script type="text/javascript">
																yopid= '<nested:write property="yearPassing"/>';
																document.getElementById("<c:out value='${dyopid}'/>").value = yopid;
															</script></td>
														<td class="row-white"><c:set var="dmonid"><%=dynaMonthId%></c:set>
														<nested:select property="monthPassing"
															styleClass="comboSmall" styleId='<%=dynaMonthId %>'>
															<html:option value="0">
																<bean:message key="knowledgepro.admin.select" />
															</html:option>
															<html:option value="1">JAN</html:option>
															<html:option value="2">FEB</html:option>
															<html:option value="3">MAR</html:option>
															<html:option value="4">APR</html:option>
															<html:option value="5">MAY</html:option>
															<html:option value="6">JUN</html:option>
															<html:option value="7">JUL</html:option>
															<html:option value="8">AUG</html:option>
															<html:option value="9">SEPT</html:option>
															<html:option value="10">OCT</html:option>
															<html:option value="11">NOV</html:option>
															<html:option value="12">DEC</html:option>
														</nested:select> <script type="text/javascript">
																monid= '<nested:write property="monthPassing"/>';
																document.getElementById("<c:out value='${dmonid}'/>").value = monid;
															</script></td>
														<bean:define id="qualId" property="id" name="ednQualList"></bean:define>
														<input type="hidden" id="countID" name="countID">
														<logic:equal value="true" property="semesterWise"
															name="ednQualList">
															<logic:equal value="false" property="consolidated"
																name="ednQualList">
																<td class="row-white" colspan="2">
																<%
																	String semsterMarkLink = "studentEdit.do?method=initSemesterMarkEditPage&editcountID="
																									+ qualId;
																%>
																<div align="center"><a href="#"
																	onclick="detailSemesterSubmit('<%=qualId%>')">Semester
																Marks Edit</a></div>
																</td>

															</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated"
															name="ednQualList">
															<logic:equal value="false" property="semesterWise"
																name="ednQualList">
																<td class="row-white" colspan="2">
																<%
																	String detailMarkLink = "studentEdit.do?method=initDetailMarkEditPage&editcountID="
																									+ qualId;
																%>
																<div align="center"><a href="#"
																	onclick="detailSubmit('<%=qualId%>')">Detailed
																Marks Edit</a></div>
																</td>

															</logic:equal>
														</logic:equal>
														<logic:notEqual value="true" property="semesterWise"
															name="ednQualList">
															<logic:notEqual value="false" property="consolidated"
																name="ednQualList">
																<td class="row-white">
																<div align="center"><nested:text
																	property="marksObtained" size="5" maxlength="9"></nested:text></div>
																</td>
																<td class="row-white">
																<div align="center"><nested:text
																	property="totalMarks" size="5" maxlength="9"></nested:text></div>
																</td>
															</logic:notEqual>
														</logic:notEqual>
														<td class="row-white">
														<div align="center"><nested:select
															property="noOfAttempts" styleClass="comboSmall"
															styleId="noOfAttempts">
															<option value="0"><bean:message
																key="knowledgepro.admin.select" /></option>
															<html:option value="1">1</html:option>
															<html:option value="2">2</html:option>
															<html:option value="3">3</html:option>
															<html:option value="4">4</html:option>
															<html:option value="5">5</html:option>
															<html:option value="6">6</html:option>
														</nested:select></div>
														</td>
														<td class="row-white">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="lastExam">
															<nested:text property="previousRegNo" size="10"
																maxlength="15" />
														</logic:equal></div>
														</td>
														<td width="8%" class="row-white"><div align="center">
											              <nested:text property="percentage" size="5" maxlength="8" onkeypress="return isDecimalNumberKey(this.value,event)"></nested:text>
											            </div></td>
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</nested:iterate>
										</logic:notEmpty>
										<logic:equal value="true" property="displayTCDetails"
											name="studentEditForm">
											<tr>
												<td height="25" class="row-odd">
												<div align="center"><bean:message
													key="admissionForm.education.TCNO.label" /></div>
												</td>
												<td height="25" class="row-even"><nested:text
													property="applicantDetails.tcNo" size="6" maxlength="20"></nested:text></td>
												<td class="row-odd">
												<div align="left"><bean:message
													key="admissionForm.education.TCDate.label" /></div>
												</td>
												<td class="row-even" colspan="2">
												<div align="left"><nested:text
													property="applicantDetails.tcDate" styleId="tcdate"
													size="10" maxlength="10"></nested:text><script
													language="JavaScript">
												new tcal( {
													// form name
													'formname' :'studentEditForm',
													// input name
													'controlname' :'tcdate'
												});
											</script></div>
												</td>

												<td class="row-odd"><bean:message
													key="admissionForm.education.markcard.label" /></td>
												<td class="row-even">
												<div align="center"><nested:text
													property="applicantDetails.markscardNo" size="6"
													maxlength="10"></nested:text></div>
												</td>
												<td class="row-odd">
												<div align="center"><bean:message
													key="admissionForm.education.markcarddate.label" /></div>
												</td>
												<td class="row-even" colspan="2">
												<div align="left"><nested:text
													property="applicantDetails.markscardDate"
													styleId="markscardDate" size="10" maxlength="10"></nested:text>
												<script language="JavaScript">
														new tcal( {
															// form name
															'formname' :'studentEditForm',
															// input name
															'controlname' :'markscardDate'
														});
													</script></div>
												</td>
												<td class="row-even">
												<div align="center">&nbsp;</div>
												</td>
											</tr>


										</logic:equal>
										<logic:equal value="true" property="displayLateralTransfer"
											name="studentEditForm">
											<tr>
												<td class="row-even">
												<div align="center">&nbsp;</div>
												</td>
												<td height="25" colspan="2" class="row-even"><logic:equal
													value="true" property="displayLateralDetails"
													name="studentEditForm">
													<div align="center"><a href="#"
														onclick="detailLateralSubmit()"><bean:message
														key="admissionForm.education.laterallink.label" /></a></div>

												</logic:equal></td>
												<td height="25" colspan="2" class="row-even">
												<div align="center">&nbsp;</div>
												</td>
												<td class="row-even" colspan="2"><logic:equal
													value="true" property="displayTransferCourse"
													name="studentEditForm">
													<div align="center"><a href="#"
														onclick="detailTransferSubmit()"><bean:message
														key="admissionForm.education.transferlink.label" /></a></div>
												</logic:equal></td>
												<td class="row-even" colspan="2">
												<div align="center">&nbsp;</div>
												</td>

												<td class="row-even">
												<div align="center">&nbsp;</div>
												</td>
												<td class="row-even">
												<div align="center">&nbsp;</div>
												</td>
											</tr>

										</logic:equal>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
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

						<logic:equal value="true" property="displayEntranceDetails"
							name="studentEditForm">

							<tr>
								<td colspan="2" class="heading" align="left">&nbsp;<bean:message
									key="admissionForm.education.entrancedetails.label" /></td>
							</tr>
							<tr>
								<td colspan="2" valign="top">
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
										<table width="100%" cellspacing="1" cellpadding="2">
											<tr>
												<td width="16%" height="25" class="row-odd">
												<div align="right"><bean:message
													key="admissionForm.education.entrance.label" /></div>
												</td>
												<td width="16%" height="25" class="row-even">
												<div align="left"><nested:select
													property="applicantDetails.entranceDetail.entranceId"
													styleClass="comboLarge">
													<html:option value="">-Select-</html:option>
													<logic:notEmpty property="entranceList"
														name="studentEditForm">
														<html:optionsCollection property="entranceList"
															name="studentEditForm" label="name" value="id" />
													</logic:notEmpty>

												</nested:select></div>
												</td>
												<td width="16%" height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.totalMarks" />:</div>
												</td>
												<td width="16%" height="25" class="row-even">
												<div align="left"><nested:text
													property="applicantDetails.entranceDetail.totalMarks"
													size="20" maxlength="8"></nested:text></div>
												</td>
												<td width="16%" height="25" class="row-odd">
												<div align="right"><bean:message
													key="admissionForm.education.markObtained.label" />:</div>
												</td>
												<td width="16%" height="25" class="row-even">
												<div align="left"><nested:text
													property="applicantDetails.entranceDetail.marksObtained"
													size="20" maxlength="8"></nested:text></div>
												</td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.applicationform.prereq.roll.label" /></div>
												</td>
												<td height="25" class="row-even">
												<div align="left"><nested:text
													property="applicantDetails.entranceDetail.entranceRollNo"
													size="25" maxlength="25"></nested:text></div>
												</td>
												<td height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.applicationform.passingmonth" />:</div>
												</td>
												<td height="25" class="row-even">
												<div align="left"><nested:select
													property="applicantDetails.entranceDetail.monthPassing"
													styleClass="comboMedium">
													<html:option value="0">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<html:option value="1">JAN</html:option>
													<html:option value="2">FEB</html:option>
													<html:option value="3">MAR</html:option>
													<html:option value="4">APR</html:option>
													<html:option value="5">MAY</html:option>
													<html:option value="6">JUN</html:option>
													<html:option value="7">JUL</html:option>
													<html:option value="8">AUG</html:option>
													<html:option value="9">SEPT</html:option>
													<html:option value="10">OCT</html:option>
													<html:option value="11">NOV</html:option>
													<html:option value="12">DEC</html:option>
												</nested:select></div>
												</td>
												<td height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.passingYear" />:</div>
												</td>
												<td height="25" class="row-even">
												<div align="left"><nested:select
													property="applicantDetails.entranceDetail.yearPassing"
													styleId='entranceyear' styleClass="comboMedium">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<cms:renderYear normalYear="true"></cms:renderYear>
												</nested:select> <script type="text/javascript">
													var entyopid= '<nested:write property="applicantDetails.entranceDetail.yearPassing"/>';
													document.getElementById("entranceyear").value = entyopid;
												</script></div>
												</td>
											</tr>



										</table>
										</td>
										<td background="images/right.gif" width="5" height="5"></td>
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


						</logic:equal>
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="admissionForm.edit.qualifydetails.label" /></td>
						</tr>
						<tr>
							<td colspan="2" valign="top">
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
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="16%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.edit.qualifydetails.subjects.label" /></div>
											</td>
											<td colspan="2" height="25" class="row-even">
											<div align="left"><nested:text
												property="applicantDetails.qualifyDetailsTo.optionalSubjects"
												size="30" maxlength="50"></nested:text></div>
											</td>
											<logic:equal value="true" property="displaySecondLanguage"
												name="studentEditForm">
												<td width="16%" height="25" class="row-odd">
												<div align="right"><bean:message
													key="admissionForm.edit.qualifydetails.seclang.label" /></div>
												</td>
												<td width="16%" colspan="2" height="25" class="row-even">
												<div align="left"><nested:text property="applicantDetails.qualifyDetailsTo.secondLanguage" size="10" maxlength="10"/>
												</div>
												</td>
											</logic:equal>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.totalMarks" /></div>
											</td>
											<td height="25" class="row-even">
											<div align="left"><nested:text
												property="applicantDetails.qualifyDetailsTo.totalMarks"
												size="10" maxlength="10" readonly="true"></nested:text></div>
											</td>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.marksObtained" /></div>
											</td>
											<td height="25" class="row-even">
											<div align="left"><nested:text
												property="applicantDetails.qualifyDetailsTo.obtainedMarks"
												size="10" maxlength="10" readonly="true"></nested:text></div>
											</td>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.edit.qualifydetails.perc.label" /></div>
											</td>
											<td height="25" class="row-even">
											<div align="left"><nested:text
												property="applicantDetails.qualifyDetailsTo.percentage"
												size="6" maxlength="6" readonly="true"></nested:text></div>
											</td>
										</tr>



									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
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
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="knowledgepro.admission.parentInfo" /></td>
						</tr>
						<tr>
							<td>
							<table width="97%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="883" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="175" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="45%" height="36" class="row-odd">
											<div align="right">
											<div align="right"><bean:message
												key="knowledgepro.admission.fatherName" /></div>
											</div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:text
												property="applicantDetails.personalData.fatherName"
												size="15" maxlength="50"></nested:text></td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:text
												property="applicantDetails.personalData.fatherEducation"
												size="15" maxlength="50"></nested:text></td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.parentinfo.currency.label" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp; <nested:select
												property="applicantDetails.personalData.fatherCurrencyId"
												styleClass="combo" styleId="fatherCurrency">
												<option value=""><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="currencyList"
													label="name" value="id" />
											</nested:select></td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="admissionFormForm.fatherIncome" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp; <nested:select
												property="applicantDetails.personalData.fatherIncomeId"
												styleClass="combo" styleId="fatherIncomeRange">
												<option value=""><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="incomeList"
													label="incomeRange" value="id" />
											</nested:select></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;<nested:select
												property="applicantDetails.personalData.fatherOccupationId"
												styleClass="combo" styleId="motherOccupation">
												<html:option value="">- Select -</html:option>
												<html:optionsCollection name="studentEditForm"
													property="occupations" label="occupationName"
													value="occupationId" />
											</nested:select></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.fatherEmail"
												size="15" maxlength="50"></nested:text></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="175"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td>
							<table width="97%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="883" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="175" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="45%" height="36" class="row-odd">
											<div align="right">
											<div align="right"><bean:message
												key="knowledgepro.admission.motherName" /></div>
											</div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:text
												property="applicantDetails.personalData.motherName"
												size="15" maxlength="50"></nested:text></td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:text
												property="applicantDetails.personalData.motherEducation"
												size="15" maxlength="50"></nested:text></td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.parentinfo.currency.label" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp; <nested:select
												property="applicantDetails.personalData.motherCurrencyId"
												styleClass="combo" styleId="motherCurrency">
												<option value=""><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="currencyList"
													label="name" value="id" />
											</nested:select></td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="admissionFormForm.fatherIncome" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp; <nested:select
												property="applicantDetails.personalData.motherIncomeId"
												styleClass="combo" styleId="motherIncomeRange">
												<option value=""><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="incomeList"
													label="incomeRange" value="id" />
											</nested:select></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp; <nested:select
												property="applicantDetails.personalData.motherOccupationId"
												styleClass="combo" styleId="motherOccupation">
												<html:option value="">- Select -</html:option>
												<html:optionsCollection name="studentEditForm"
													property="occupations" label="occupationName"
													value="occupationId" />
											</nested:select></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.motherEmail"
												size="15" maxlength="50"></nested:text></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="175"></td>
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

						<logic:equal value="true" property="displayFamilyBackground"
							name="studentEditForm">
							<tr>
								<td>
								<table width="97%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td><img src="images/01.gif" width="5" height="5"></td>
										<td width="883" background="images/02.gif"></td>
										<td><img src="images/03.gif" width="5" height="5"></td>
									</tr>
									<tr>
										<td width="5" background="images/left.gif"></td>
										<td height="175" valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
											<tr>
												<td width="45%" height="36" class="row-odd">
												<div align="right">
												<div align="right"><bean:message
													key="knowledgepro.applicationform.brothername.label" /></div>
												</div>
												</td>
												<td width="55%" height="36" class="row-even" align="left">&nbsp;
												<nested:text
													property="applicantDetails.personalData.brotherName"
													size="15" maxlength="100"></nested:text></td>
											</tr>
											<tr class="row-odd">
												<td width="45%" height="36" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.education" /></div>
												</td>
												<td width="55%" height="36" class="row-even" align="left">&nbsp;
												<nested:text
													property="applicantDetails.personalData.brotherEducation"
													size="15" maxlength="100"></nested:text></td>
											</tr>

											<tr>
												<td height="45" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.income" /></div>
												</td>
												<td height="25" class="row-even" align="left">&nbsp; <nested:text
													property="applicantDetails.personalData.brotherIncome"
													size="15" maxlength="100"></nested:text></td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admin.occupation" />:</div>
												</td>
												<td height="30" class="row-even" align="left"><nested:text
													property="applicantDetails.personalData.brotherOccupation"
													size="15" maxlength="100"></nested:text></td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.applicationform.age.label" /></div>
												</td>
												<td height="30" class="row-even" align="left"><nested:text
													property="applicantDetails.personalData.brotherAge"
													size="15" maxlength="50"></nested:text></td>
											</tr>
										</table>
										</td>
										<td background="images/right.gif" width="5" height="175"></td>
									</tr>
									<tr>
										<td height="5"><img src="images/04.gif" width="5"
											height="5"></td>
										<td background="images/05.gif"></td>
										<td><img src="images/06.gif"></td>
									</tr>
								</table>
								</td>
								<td>
								<table width="97%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td><img src="images/01.gif" width="5" height="5"></td>
										<td width="883" background="images/02.gif"></td>
										<td><img src="images/03.gif" width="5" height="5"></td>
									</tr>
									<tr>
										<td width="5" background="images/left.gif"></td>
										<td height="175" valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
											<tr>
												<td width="45%" height="36" class="row-odd">
												<div align="right">
												<div align="right"><bean:message
													key="knowledgepro.applicationform.sistername.label" /></div>
												</div>
												</td>
												<td width="55%" height="36" class="row-even" align="left">&nbsp;
												<nested:text
													property="applicantDetails.personalData.sisterName"
													size="15" maxlength="100"></nested:text></td>
											</tr>
											<tr class="row-odd">
												<td width="45%" height="36" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.education" /></div>
												</td>
												<td width="55%" height="36" class="row-even" align="left">&nbsp;
												<nested:text
													property="applicantDetails.personalData.sisterEducation"
													size="15" maxlength="100"></nested:text></td>
											</tr>

											<tr>
												<td height="45" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.income" /></div>
												</td>
												<td height="25" class="row-even" align="left">&nbsp; <nested:text
													property="applicantDetails.personalData.sisterIncome"
													size="15" maxlength="100"></nested:text></td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admin.occupation" />:</div>
												</td>
												<td height="30" class="row-even" align="left">&nbsp; <nested:text
													property="applicantDetails.personalData.sisterOccupation"
													size="15" maxlength="100"></nested:text></td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.applicationform.age.label" /></div>
												</td>
												<td height="30" class="row-even" align="left">&nbsp; <nested:text
													property="applicantDetails.personalData.sisterAge"
													size="15" maxlength="50"></nested:text></td>
											</tr>
										</table>
										</td>
										<td background="images/right.gif" width="5" height="175"></td>
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
						</logic:equal>

						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="knowledgepro.admission.parentAddress" /></td>
						</tr>
						<tr>
							<td colspan="2">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="91" valign="top">
									<table width="100%" height="107" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="113" height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="178" height="20" class="row-even" align="left">&nbsp;
											<nested:text
												property="applicantDetails.personalData.parentAddressLine1"
												size="15" maxlength="100"></nested:text></td>
											<td width="100" height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td width="190" height="20" class="row-even" align="left">&nbsp;
											<nested:text
												property="applicantDetails.personalData.parentCityName"
												size="15" maxlength="30"></nested:text></td>
											<td width="122" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td width="206" class="row-even" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.parentAddressZipCode"
												size="10" maxlength="10"></nested:text></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.parentAddressLine2"
												size="15" maxlength="100"></nested:text></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td class="row-white" align="left"><nested:select
												property="applicantDetails.personalData.parentCountryId"
												styleClass="combo" styleId="parentCountryName"
												onchange="getParentAddrStates(this.value,'parentStateName')">
												<option value="0"><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="countries" label="name"
													value="id" />
											</nested:select></td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.parentPh1" size="3"
												maxlength="10"></nested:text> <nested:text
												property="applicantDetails.personalData.parentPh2" size="3"
												maxlength="10"></nested:text> <nested:text
												property="applicantDetails.personalData.parentPh3" size="3"
												maxlength="10"></nested:text></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs3.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:text
												property="applicantDetails.personalData.parentAddressLine3"
												size="15" maxlength="100"></nested:text></td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" align="left">&nbsp;
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td><logic:notEqual value="Other"
														property="applicantDetails.personalData.parentStateId"
														name="studentEditForm">
														<%
															dynastyle = "display:none;";
														%>
														<nested:select
															property="applicantDetails.personalData.parentStateId"
															styleClass="combo" styleId="parentStateName"
															onchange="funcOtherShowHide('parentStateName','OtherParentState')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<c:if
																test="${studentEditForm.applicantDetails.personalData.parentCountryId != null && studentEditForm.applicantDetails.personalData.parentCountryId!= ''}">
																<c:set var="parentAddrStateMap"
																	value="${baseActionForm.collectionMap['parentAddrStateMap']}" />
																<c:if test="${parentAddrStateMap != null}">
																	<html:optionsCollection name="parentAddrStateMap"
																		label="value" value="key" />
																	<html:option value="Other">Other</html:option>
																</c:if>
															</c:if>
															<logic:notEmpty name="studentEditForm"
																property="editParentStates">
																<nested:optionsCollection property="editParentStates"
																	label="name" value="id" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select>
													</logic:notEqual> <logic:equal value="Other"
														property="applicantDetails.personalData.parentStateId"
														name="studentEditForm">
														<%
															dynastyle = "display:block;";
														%>
														<nested:select
															property="applicantDetails.personalData.parentStateId"
															styleClass="combo" styleId="parentStateName"
															onchange="funcOtherShowHide('parentStateName','OtherParentState')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<c:if
																test="${studentEditForm.applicantDetails.personalData.parentCountryId != null && studentEditForm.applicantDetails.personalData.parentCountryId!= ''}">
																<c:set var="parentAddrStateMap"
																	value="${baseActionForm.collectionMap['parentAddrStateMap']}" />
																<c:if test="${parentAddrStateMap != null}">
																	<html:optionsCollection name="parentAddrStateMap"
																		label="value" value="key" />
																	<html:option value="Other">Other</html:option>
																</c:if>
															</c:if>
															<logic:notEmpty name="studentEditForm"
																property="editParentStates">
																<nested:optionsCollection property="editParentStates"
																	label="name" value="id" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select>

													</logic:equal></td>
												</tr>
												<tr>
													<td><nested:text
														property="applicantDetails.personalData.parentAddressStateOthers"
														size="10" maxlength="50" styleId="OtherParentState"
														style="<%=dynastyle %>"></nested:text></td>
												</tr>
											</table>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.parentMob1" size="3"
												maxlength="10"></nested:text> <nested:text
												property="applicantDetails.personalData.parentMob2" size="3"
												maxlength="10"></nested:text> <nested:text
												property="applicantDetails.personalData.parentMob3" size="3"
												maxlength="10"></nested:text></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
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


						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="knowledgepro.applicationform.guardianaddr.label" /></td>
						</tr>
						<tr>
							<td colspan="2">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="91" valign="top">
									<table width="100%" height="107" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="113" height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.applicationform.guardianname.label" /></div>
											</td>
											<td width="468" height="20" class="row-even" align="left"
												colspan="3">&nbsp; <nested:text
												property="applicantDetails.personalData.guardianName"
												size="50" maxlength="100"></nested:text></td>

											<td width="122" class="row-odd"></td>
											<td width="206" class="row-even" align="left"></td>
										</tr>
										<tr class="row-white">
											<td width="113" height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="178" height="20" class="row-even" align="left">&nbsp;
											<nested:text
												property="applicantDetails.personalData.guardianAddressLine1"
												size="15" maxlength="100"></nested:text></td>
											<td width="100" height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td width="190" height="20" class="row-even" align="left">&nbsp;
											<nested:text
												property="applicantDetails.personalData.cityByGuardianAddressCityId"
												size="15" maxlength="30"></nested:text></td>
											<td width="122" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td width="206" class="row-even" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.guardianAddressZipCode"
												size="10" maxlength="10"></nested:text></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.guardianAddressLine2"
												size="15" maxlength="100"></nested:text></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td class="row-white" align="left"><nested:select
												property="applicantDetails.personalData.countryByGuardianAddressCountryId"
												styleClass="combo" styleId="guardianCountryName"
												onchange="getParentAddrStates(this.value,'guardianStateName')">
												<option value="0"><bean:message
													key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection property="countries" label="name"
													value="id" />
											</nested:select></td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.guardianPh1"
												size="3" maxlength="10"></nested:text> <nested:text
												property="applicantDetails.personalData.guardianPh2"
												size="3" maxlength="10"></nested:text> <nested:text
												property="applicantDetails.personalData.guardianPh3"
												size="3" maxlength="10"></nested:text></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs3.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:text
												property="applicantDetails.personalData.guardianAddressLine3"
												size="15" maxlength="100"></nested:text></td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" align="left">&nbsp;
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
													<td><logic:notEqual value="Other"
														property="applicantDetails.personalData.stateByGuardianAddressStateId"
														name="studentEditForm">
														<%
															dynastyle = "display:none;";
														%>
														<nested:select
															property="applicantDetails.personalData.stateByGuardianAddressStateId"
															styleClass="combo" styleId="guardianStateName"
															onchange="funcOtherShowHide('guardianStateName','OtherGuardianState')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<c:if
																test="${studentEditForm.applicantDetails.personalData.countryByGuardianAddressCountryId != null && studentEditForm.applicantDetails.personalData.countryByGuardianAddressCountryId!= ''}">
																<c:set var="guardianAddrStateMap"
																	value="${baseActionForm.collectionMap['guardianAddrStateMap']}" />
																<c:if test="${guardianAddrStateMap != null}">
																	<html:optionsCollection name="guardianAddrStateMap"
																		label="value" value="key" />
																	<html:option value="Other">Other</html:option>
																</c:if>
															</c:if>
															<logic:notEmpty name="studentEditForm"
																property="editGuardianStates">
																<nested:optionsCollection property="editGuardianStates"
																	label="name" value="id" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select>
													</logic:notEqual> <logic:equal value="Other"
														property="applicantDetails.personalData.stateByGuardianAddressStateId"
														name="studentEditForm">
														<%
															dynastyle = "display:block;";
														%>
														<nested:select
															property="applicantDetails.personalData.stateByGuardianAddressStateId"
															styleClass="combo" styleId="guardianStateName"
															onchange="funcOtherShowHide('guardianStateName','OtherGuardianState')">
															<option value=""><bean:message
																key="knowledgepro.admin.select" /></option>
															<c:if
																test="${studentEditForm.applicantDetails.personalData.countryByGuardianAddressCountryId != null && studentEditForm.applicantDetails.personalData.countryByGuardianAddressCountryId!= ''}">
																<c:set var="parentAddrStateMap"
																	value="${baseActionForm.collectionMap['guardianAddrStateMap']}" />
																<c:if test="${guardianAddrStateMap != null}">
																	<html:optionsCollection name="parentAddrStateMap"
																		label="value" value="key" />
																	<html:option value="Other">Other</html:option>
																</c:if>
															</c:if>
															<logic:notEmpty name="studentEditForm"
																property="editGuardianStates">
																<nested:optionsCollection property="editGuardianStates"
																	label="name" value="id" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select>

													</logic:equal></td>
												</tr>
												<tr>
													<td><nested:text
														property="applicantDetails.personalData.guardianAddressStateOthers"
														size="10" maxlength="50" styleId="OtherGuardianState"
														style="<%=dynastyle %>"></nested:text></td>
												</tr>
											</table>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" align="left">&nbsp; <nested:text
												property="applicantDetails.personalData.guardianMob1"
												size="3" maxlength="10"></nested:text> <nested:text
												property="applicantDetails.personalData.guardianMob2"
												size="3" maxlength="10"></nested:text> <nested:text
												property="applicantDetails.personalData.guardianMob3"
												size="3" maxlength="10"></nested:text></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
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
						<tr>
							<td colspan="2" align="left"><span class="heading">&nbsp;Recommended By</span></td>
						</tr>
						<tr>
							<td colspan="2" >
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="91" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
										<td height="25" class="row-odd" width="15%">
											<div align="center"><bean:message key="knowledgepro.admission.details"/></div>
											</td>
											<td height="25" class="row-even" width="15%">
											<div align="center"><html:textarea property="applicantDetails.personalData.recommendedBy" styleId="desc1" cols="30" rows="4" onkeypress="return imposeMaxLength(event,this)"/></div>
											</td>
											
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
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
						<tr>
							<td colspan="2" align="left"><span class="heading">&nbsp;<bean:message
								key="knowledgepro.admission.documents" /></span></td>
						</tr>
						<tr>
							<td colspan="2">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="91" valign="top">
									
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td>
												<table width="100%">
													<tr>
														<td height="25" class="row-odd" width="15%">
														<div align="center"><bean:message key="admissionForm.edit.hardcopy.label"/></div>
														</td>
														<td height="25" class="row-odd" width="18%">
														<div align="center"><bean:message key="admissionForm.edit.na.label"/> </div>
														</td>
														<td height="25" class="row-odd" width="12%" align="center"><bean:message
															key="knowledgepro.admission.documents" /></td>
														<td class="row-odd" width="20%" align="center">&nbsp;</td>
														<td class="row-odd" width="35%" align="center"><bean:message
															key="knowledgepro.admission.uploadDocs" /></td>
													</tr>
												</table>
											</td>
										
										</tr>
										<c:set var="temp" value="0" />
										<% String sty=""; %>
										<nested:iterate name="studentEditForm"
											property="applicantDetails.editDocuments" indexId="count1" id="docList">
											<c:choose>
												<c:when test="${temp == 0}">
													<%sty="row-even"; %>
													<c:set var="temp" value="1" />
												</c:when>	
												<c:otherwise>
													<%sty="row-white"; %>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>	
													<tr>
													  <td>
													  	<table width="100%">
													  		<tr>
													  			<td width="15%" height="25" class='<%=sty %>'>
																<div align="center">
																 <input type="hidden" id="selected_<c:out value='${count1}'/>" name="selected_<c:out value='${count1}'/>" value="<nested:write name='docList' property='temphardSubmitted'/>"/>
																<%--<nested:checkbox property="hardSubmitted"></nested:checkbox>--%>
																<input type="checkbox" id="selected1_<c:out value='${count1}'/>" name="applicantDetails.editDocuments[<c:out value='${count1}'/>].hardSubmitted" id="select_<c:out value='${count1}'/>" onclick="unselectApplicable('<c:out value="${count1}"/>')"/>
																<script type="text/javascript">
																	var selectedId = document.getElementById("selected_<c:out value='${count1}'/>").value;
																	if(selectedId == "true") {
																			document.getElementById("selected1_<c:out value='${count1}'/>").checked = true;
																	}		
																</script>
																</div>
																</td>
																	<td width="18%" height="25" class='<%=sty %>'>
																<div align="center">
																<%String met="unselectHardSubmit("+count1+")"; %>
																<c:if test="${docList.needToProduceSemWiseMC=='true'}">
																	<%met=met+",disableChekcBox("+count1+")"; %>
																</c:if>
																 <input type="hidden" id="selected_not_applicable_<c:out value='${count1}'/>" name="selected_<c:out value='${count1}'/>" value="<nested:write name='docList' property='tempNotApplicable'/>"/>
																<%--<nested:checkbox property="hardSubmitted"></nested:checkbox>--%>
																<input type="checkbox" id="selected1_not_applicable_<c:out value='${count1}'/>" name="applicantDetails.editDocuments[<c:out value='${count1}'/>].notApplicable" onclick="<%=met %>"/>
																<script type="text/javascript">
																	var selected_not_applicable_Id = document.getElementById("selected_not_applicable_<c:out value='${count1}'/>").value;
																	if(selected_not_applicable_Id == "true") {
																			document.getElementById("selected1_not_applicable_<c:out value='${count1}'/>").checked = true;
																	}		
																</script>
																</div>
																</td>
																<td width="12%" height="25" class='<%=sty %>' align="center"><nested:write
																	property="printName" /></td>
																<td width="20%" class='<%=sty %>' align="center"><nested:equal
																	value="true" property="documentPresent">
																	<a href="javascript:downloadFile('<nested:write property="id"/>')"><bean:message key="knowledgepro.view" /></a>
																</nested:equal></td>
																<td width="35%" class='<%=sty %>'>
																	<nested:file property="editDocument"></nested:file>
																</td>
													  		</tr>
													  		<c:if test="${docList.needToProduceSemWiseMC=='true'}">
													  		<%
													  			String semId="semisterNo_"+count1;
													  			String semMethod="checkTheFields("+count1+")";
													  			String semTypeID="semType_"+count1;
													  		%>
														  		 <tr height="25" class='<%=sty %>'>
														  		    <td>
														  		    	Sem No:<nested:select property="semisterNo" styleClass="combo"  styleId='<%=semId %>' onchange='<%=semMethod %>'>
																			<option value=""><bean:message key="knowledgepro.admin.select"/></option>
																			<html:option value="1">1</html:option>
																			<html:option value="2">2</html:option>
																			<html:option value="3">3</html:option>
																			<html:option value="4">4</html:option>
																			<html:option value="5">5</html:option>
																			<html:option value="6">6</html:option>
																			<html:option value="7">7</html:option>
																			<html:option value="8">8</html:option>
																			<html:option value="9">9</html:option>
																			<html:option value="10">10</html:option>
																			<html:option value="11">11</html:option>
																			<html:option value="12">12</html:option>
																		</nested:select>
														  		    </td>
														  		    <td> 
														  		    	Type:<nested:select property="semType" styleClass="combo" styleId='<%=semTypeID %>' >
																			<html:option value="sem">sem</html:option>
																			<html:option value="year">year</html:option>
																		</nested:select>
														  		    </td>
														  		    <td colspan="3">
														  		    	<table>
														  		    	<tr>
														  		    		<logic:notEmpty name="docList" property="docDetailsList"> 
														  		    		<nested:iterate id="doc" property="docDetailsList" indexId="count2">
														  		    			<td>
														  		    				<div align="center">
														  		    					<%
													  										String checkId="check_"+count1+"_"+count2;
														  		    						String checkMethod="checkBoxField("+count1+","+count2+")";
													  										%>
														  		    					<bean:write name="doc" property="semNo"/> 
														  		    					
														  		    					<nested:checkbox property="checked" styleId='<%=checkId %>' onclick='<%=checkMethod %>'></nested:checkbox>
														  		    					
																						<script type="text/javascript">
																							var check = "<bean:write name='doc' property='checked'/>";
																							if(check == "yes") {
																			                        document.getElementById("check_<c:out value='${count1}'/>_<c:out value='${count2}'/>").checked = true;
																							}	
																							if(check == "no") {
																		                        document.getElementById("check_<c:out value='${count1}'/>_<c:out value='${count2}'/>").checked = false;
																						}
														  		    					</script>
														  		    				
														  		    				
																					</div>
														  		    			</td>
														  		    		</nested:iterate>
														  		   			</logic:notEmpty>
														  		    	</tr>
														  		    </table>
														  		    
														  		    </td>
														  		 </tr>   
														  		</c:if>
													  	</table>
													  </td>
													</tr>
										</nested:iterate>
									</table>
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>

								<tr>
									<td height="19" valign="top"></td>
									<td class="heading">&nbsp;</td>

									<td valign="top"></td>
								</tr>


								<tr>
									<td height="19" valign="top"></td>
									<td class="heading">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td><img src="images/01.gif" width="5" height="5" /></td>
											<td width="926" background="images/02.gif"></td>
											<td><img src="images/03.gif" width="5" height="5" /></td>

										</tr>
										<tr>
											<td width="5" background="images/left.gif"></td>
											<td valign="top">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td width="32%" height="25" class="row-odd">
													<div align="right">Student Specialisation :</div>
													</td>
													<td width="32%" class="row-even">
													<div align="left"><html:select
														property="specialisationId" styleClass="body"
														styleId="specialisationId" multiple="true">
														<html:optionsCollection property="specialisationList"
															label="display" value="id" />
													</html:select></div>
													</td>
													<td class="row-even" colspan="2"></td>
												</tr>
												<tr>
													<td height="25" class="row-odd">
													<div align="right">Consolidated Marks Card No. :</div>
													</td>

													<td class="row-even">
													<div align="left"><html:text
														property="consolidateMarksNo" maxlength="49" /></div>
													</td>
													<td width="23%" class="row-even">
																										</td>
													<td width="13%" class="row-even"></td>
												</tr>
												<tr>
													<td height="25" class="row-odd">
													<div align="right">Course Name For Marks Card:</div>
													</td>
													<td class="row-even" colspan="3">
													<div align="left"><html:text
														property="courseNameForMarkscard" maxlength="99" /></div>
													</td>

												</tr>
											</table>
											</td>
											<td width="5" background="images/right.gif"></td>

										</tr>
										<tr>
											<td height="5"><img src="images/04.gif" width="5"
												height="5" /></td>
											<td background="images/05.gif"></td>
											<td><img src="images/06.gif" /></td>
										</tr>
									</table>
									</td>
									<td valign="top"></td>
								</tr>



								<tr>

									<td height="19" valign="top"></td>
									<td class="heading">&nbsp;</td>
									<td valign="top"></td>
								</tr>











								<tr>
									<td height="19" valign="top"></td>
									<td class="heading">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td><img src="images/01.gif" width="5" height="5" /></td>

											<td width="910" background="images/02.gif"></td>
											<td><img src="images/03.gif" width="5" height="5" /></td>
										</tr>
										<tr>
											<td width="5" background="images/left.gif"></td>
											<td valign="top">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td colspan="2" class="heading">Detention Details</td>

													<td colspan="2" class="heading"><html:radio
														property="detentiondetailsRadio" value="yes"
														styleId="detentiondetailsRadio"/> Yes<html:radio
														property="detentiondetailsRadio" value="no"
														styleId="detentiondetailsRadio1" onclick="validateDetentionDetails()"/>No</td>
													<td class="heading"></td>
													<td colspan="3" class="heading">
													<div align="right"></div>
													</td>
												</tr>
												<tr>
													<td height="39" class="row-odd" colspan="2">
													<div align="right">Date:</div>
													</td>

													<td height="25" class="row-even" colspan="2">
													<table width="82" border="0" cellspacing="0"
														cellpadding="0">

														<tr>
															<td width="60">
															<div align="left"><html:text
																property="detentiondetailsDate"
																styleId="detentiondetailsDate" maxlength="10" ></html:text></div>
															</td>
															<td width="40"><script language="JavaScript">
													new tcal( {
														// form name
														'formname' :'studentEditForm',
														// input name
														'controlname' :'detentiondetailsDate'
													});
										</script></td>
														</tr>
													</table>
													</td>
													<td width="27%" class="row-odd">
													<div align="right">Reason:</div>
													</td>
													<td width="21%" class="row-even"><html:textarea
														property="detantiondetailReasons"
														styleId="detantiondetailReasons"
														onkeypress="return imposeMaxLength(event,this.value)"></html:textarea>
													<br />

													<logic:notEmpty property="detentionDetHistoryList" name="studentEditForm">
													
													<A HREF="javascript:winOpen();">
													View History </A>
													</logic:notEmpty>
													</td>



												</tr>
											</table>
											</td>
											<td width="5" background="images/right.gif"></td>
										</tr>
										<tr>
											<td height="5"><img src="images/04.gif" width="5"
												height="5" /></td>
											<td background="images/05.gif"></td>
											<td><img src="images/06.gif" /></td>
										</tr>

									</table>
									<p>&nbsp;</p>
									</td>
									<td valign="top"></td>
								</tr>





								<tr>
									<td height="19" valign="top"></td>
									<td class="heading">&nbsp;</td>
									<td valign="top"></td>
								</tr>





								<tr>
									<td height="19" valign="top"></td>
									<td class="heading">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td><img src="images/01.gif" width="5" height="5" /></td>
											<td width="910" background="images/02.gif"></td>
											<td><img src="images/03.gif" width="5" height="5" /></td>
										</tr>
										<tr>

											<td width="5" background="images/left.gif"></td>
											<td valign="top">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td colspan="2" class="heading">Discontinued Details</td>
													<td colspan="2" class="heading"><html:radio
														property="discontinuedDetailsRadio" value="yes"
														styleId="discontinuedDetailsRadio" /> Yes<html:radio
														property="discontinuedDetailsRadio" value="no"
														styleId="discontinuedDetailsRadio1" onclick="validateDiscontinuedDetails()" />No</td>
													<td class="heading"></td>
													<td colspan="5" class="heading">&nbsp;</td>

												</tr>
												<tr>
													<td height="39" class="row-odd" colspan="2">
													<div align="right">Date:</div>
													</td>

													<td height="25" class="row-even" colspan="2">
													<table width="82" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															<td>
															<div align="left"><html:text
																property="discontinuedDetailsDate"
																styleId="discontinuedDetailsDate"/></div>
															</td>
															<td width="40"><script language="JavaScript">
													new tcal( {
														// form name
														'formname' :'studentEditForm',
														// input name
														'controlname' :'discontinuedDetailsDate'
													});
										</script></td>
														</tr>

													</table>
													</td>
													<td width="27%" class="row-odd">
													<div align="right">Reason:</div>
													</td>
													<td width="21%" class="row-even"><html:textarea
														property="discontinuedDetailsReasons"
														styleId="discontinuedDetailsReasons"
														onkeypress="return imposeMaxLength(event,this.value)"></html:textarea><br />


													<logic:notEmpty property="discontinuedDetHistoryList" name="studentEditForm">
													
													<A HREF="javascript:winOpenDiscontinue();">
													View History </A>
													</logic:notEmpty>
												</tr>
											</table>
											</td>
											<td width="5" background="images/right.gif"></td>

										</tr>
										<tr>
											<td height="5"><img src="images/04.gif" width="5"
												height="5" /></td>
											<td background="images/05.gif"></td>
											<td><img src="images/06.gif" /></td>
										</tr>
									</table>
									<p>&nbsp;</p>
									</td>
									<td valign="top"></td>

								</tr>

		<tr>
									<td height="114" valign="top"></td>
									<td class="heading">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td><img src="images/01.gif" width="5" height="5" /></td>
											<td width="910" background="images/02.gif"></td>
											<td><img src="images/03.gif" width="5" height="5" /></td>
										</tr>

										<tr>
											<td width="5" background="images/left.gif"></td>
											<td valign="top">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td width="12%" class="heading">Rejoin Details</td>
													<td class="heading"></td>
													<td class="heading"></td>
													<td class="heading"></td>

													<td class="heading"></td>
													<td class="heading"></td>
													<td class="heading"></td>
													<td colspan="5" class="heading">
													<div align="right"></div>
													</td>
												</tr>
												<tr>
													<td height="42" class="row-odd">
													<div align="right">Date:</div>
													</td>

													<td height="25" class="row-even" colspan="2">
													<table width="82" border="0" cellspacing="0"
														cellpadding="0">
														<tr>
															<td width="60"><html:text
																property="rejoinDetailsDate" styleId="rejoinDetailsDate"
																onblur="clearRejoinDate()" /></td>
															<td width="40"><script language="JavaScript">
													new tcal( {
														// form name
														'formname' :'studentEditForm',
														// input name
														'controlname' :'rejoinDetailsDate'
													});
													/*var currentTime = new Date()
													var month = currentTime.getMonth() + 1
													var day = currentTime.getDate()
													var year = currentTime.getFullYear()
													document.getElementById("rejoinDetailsDate").value=day+ "/" + month + "/" + year;*/
										</script></td>
														</tr>
													</table>
													</td>
													<td width="10%" class="row-odd">
													<div align="right">Batch:</div>
													</td>
													<td width="10%" class="row-even"><html:select
														property="batch" styleClass="body" styleId="batch">
													<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
													</html:select></td>
													<td width="10%" class="row-odd">
													<div align="right">Readmitted Class:</div>
													</td>
													<td width="10%" class="row-even"><html:select
														property="readmittedClass" styleClass="body"
														styleId="readmittedClass">
														<html:option value="">
															<bean:message key="knowledgepro.admin.select" />
														</html:option>
													<logic:notEmpty name="studentEditForm" property="rejoinClassMap">
														<nested:optionsCollection property="rejoinClassMap"
														label="value" value="key" />
													</logic:notEmpty>
													</html:select></td>
													<td width="9%" class="row-odd" colspan="2">
													<div align="right">Remark:</div>
													</td>
													<td width="13%" class="row-even"><html:textarea
														property="reMark"
														onkeypress="return imposeMaxLength(event,this.value)"></html:textarea>
													<br />
													<logic:equal value="present" property="rejoinDetailsLink"
														name="studentEditForm">
														<a
															href="studentEdit.do?method=viewHistory_rejoin&viewStudentId=<bean:write property="originalStudent.id" name="studentEditForm"/>"
															target="_blank">View History</a>
													</logic:equal>
													<logic:notEmpty property="rejoinDetHistoryList" name="studentEditForm">
													
													<A HREF="javascript:winOpenRejoin();">
													View History </A>
													</logic:notEmpty>
													
													</td>


												</tr>
											</table>
											</td>
											<td width="5" background="images/right.gif"></td>
										</tr>
										<tr>
											<td height="5"><img src="images/04.gif" width="5"
												height="5" /></td>

											<td background="images/05.gif"></td>
											<td><img src="images/06.gif" /></td>
										</tr>
									</table>
									<p>&nbsp;</p>
									</td>
									<td valign="top"></td>
								</tr>
								 <tr>
			<td colspan="2" class="heading" align="left">&nbsp;TC Details</td>
		</tr>
		<tr>
							<td colspan="2" class="heading">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="29" valign="top">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
										<td class="row-odd">Tc Date: </td>
										<td class="row-even">
										<nested:text
												property="applicantDetails.tcDate" styleId="tcDate"
												size="10" maxlength="10"></nested:text> <script
												language="JavaScript">
													new tcal( {
														// form name
														'formname' :'studentEditForm',
														// input name
														'controlname' :'tcDate'
													});
												</script>
										
													</td>
										<td class="row-odd"> TC Number:</td>
										<td class="row-even">
										<nested:text 	property="applicantDetails.tcNo" styleId="tcNo"
												size="10" maxlength="20"></nested:text>
										</td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="29"></td>
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
						
						<tr>
			<td colspan="2" class="heading" align="left">&nbsp;Board Details</td>
		</tr>
		<tr>
							<td colspan="2" class="heading">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="29" valign="top">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
										<td class="row-odd">Exam Register No: </td>
										<td class="row-even">
										<nested:text
												name="studentEditForm" property="examRegNo" size="10"
												maxlength="15"></nested:text> 
										
													</td>
										<td class="row-odd"> Student No:</td>
										<td class="row-even">
										<nested:text
												name="studentEditForm" property="studentNo" size="10"
												maxlength="15"></nested:text>
										</td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="29"></td>
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
							</td>

						</tr>
						
						<tr>
							<td colspan="2">
							
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
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
		</tr>
	</table>
	</td>
	</tr>
	<logic:equal value="true" property="accessRemarks"
		name="studentEditForm">
		<tr>
			<td colspan="2" align="left"><span class="heading">&nbsp;Remark
			Entry</span></td>
		</tr>
		<tr>
			<td colspan="2" class="row-white">
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
					<div align="center"><a href="#" onclick="enterRemark()">Remark
					Entry</a></div>
					</td>
					<td background="images/right.gif" width="5" height="5"></td>
				</tr>
				<tr>
					<td height="5"><img src="images/04.gif" width="5" height="5"></td>
					<td background="images/05.gif"></td>
					<td><img src="images/06.gif"></td>
				</tr>
			</table>
			</td>
		</tr>
		
	</logic:equal> <bean:define id="appno" name="studentEditForm"
		property="applicantDetails.applnNo"></bean:define> <bean:define
		id="appyear" name="studentEditForm"
		property="applicantDetails.appliedYear"></bean:define> <%
 	String resetmethod = "getDetailForEdit('" + appno + "','"
 				+ appyear + "','getStudentDetails')";
 %>

	<tr>
		<td colspan="2">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="48%" height="21">
				<div align="right"><html:submit styleClass="formbutton">
					<bean:message key="knowledgepro.submit" />
				</html:submit></div>

				</td>
				<td width="1%">
				<div align="center"><html:button property=""
					styleClass="formbutton" onclick="<%=resetmethod%>">
					<bean:message key="knowledgepro.admin.reset" />
				</html:button></div>
				</td>
				<td width="51%" height="45" align="left"><html:button
					property="" styleClass="formbutton"
					onclick="cancelMe('initStudentEdit')">
					<bean:message key="knowledgepro.cancel" />
				</html:button></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="2">&nbsp;</td>
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
	</div>
</html:form>
<script type="text/javascript">



function unselectApplicable(count) {
	document.getElementById("selected1_not_applicable_"+count).checked = false;
}	

function unselectHardSubmit(count){
	document.getElementById("selected1_"+count).checked = false;
}	
</script>