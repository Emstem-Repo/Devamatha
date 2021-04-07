<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<SCRIPT type="text/javascript">
	function downloadFile(documentId) {
		document.location.href = "DocumentDownloadAction.do?documentId="
				+ documentId;
	}
	function cancel() {
		document.location.href = "InterviewResultEntry.do?method=getProgramType";
	}
	function getSemesterMarkDetails(qualId) {
		var url  = "InterviewResultEntry.do?method=initSemesterMarkEditPage&editcountID="+qualId;
    	myRef = window.open(url,"ViewSemesterMarkDetails","left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
   }
	function getDetailsMark(qualId) {
		var url  = "InterviewResultEntry.do?method=initDetailMarkEditPage&editcountID="+qualId;
    	myRef = window.open(url,"ViewDetailsMark","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
   }
	function getinterviewSubrounds(interviewTypeId) {
		var applicationId = document.getElementById("applicationId").value;
		if(interviewTypeId.length != 0){
		getInterviewSubroundsByApplicationId("interviewSubroundsMap",interviewTypeId,applicationId,"interviewSubrounds",updateInterviewSubrounds);
		}else{
			resetOption("interviewSubrounds");
		}
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
</SCRIPT>
<html:form action="/InterviewResultEntry" enctype="multipart/form-data">
	<html:hidden property="method" value="interviewResultEntry" />
	<html:hidden property="formName" value="interviewResultEntryForm" />
	<input type="hidden" name="applicationId" id="applicationId"
		value='<bean:write name="interviewResultEntryForm" property="applicantDetails.applicationId" />' />
	<html:hidden property="pageType" value="2" />
	<input type="hidden" name="courseId" value='<bean:write	name="interviewResultEntryForm"	property="applicantDetails.course.id" />' />
	<html:hidden property="subroundCount" styleId="subroundCount" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.admission.interviewprocessing" />&gt;&gt;</span></span></td>
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
					<div align="center">
					<table width="100%" height="1334" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="knowledgepro.admission.admissionFormDetails" /></td>
						</tr>
						<tr>
							<td colspan="2" align="left">
							<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage">
							<FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages></FONT>
							</div>
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
											<td width="394" height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.applicationnumber" />:</div>
											</td>
											<td width="515" height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.applnNo" /></td>
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
											<div align="right"><bean:message
												key="knowledgepro.admin.programtype" />:</div>
											</td>
											<td width="168" height="23" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.course.programTypeCode" /></td>
											<td width="99" height="23" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.program" />:</div>
											</td>
											<td width="188" height="23" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.course.programCode" /></td>
											<td width="121" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.course" />:</div>
											</td>
											<td width="208" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.course.code" /></td>
										</tr>
										<tr class="row-even">
											<td height="24" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.challanNo" /></div>
											</td>
											<td height="24" class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.challanRefNo" /></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.journalNo" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.journalNo" /></td>
											<td height="24" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.date" /></div>
											</td>
											<td height="24" class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.challanDate" /></td>
										</tr>
										<tr class="row-even">
											<td height="19" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.amount" /></div>
											</td>
											<td height="19" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.amount" /></td>
											<td height="19" class="row-odd">&nbsp;</td>
											<td height="19">&nbsp;</td>
											<td class="row-odd">&nbsp;</td>
											<td height="19">&nbsp;</td>
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
							<table width="98%" border="0" align="center" cellpadding="0"
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
											<div align="right"><bean:message
												key="knowledgepro.admission.candidateName" />:</div>
											</td>
											<td width="55%" height="25" class="row-even" align="left">&nbsp;
											
											<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.firstName" />&nbsp;
											<logic:notEmpty name="interviewResultEntryForm" property="applicantDetails.personalData.middleName">
											<bean:write name="interviewResultEntryForm"
												property="applicantDetails.personalData.middleName" />
											</logic:notEmpty>&nbsp;
											<bean:write name="interviewResultEntryForm"
												property="applicantDetails.personalData.lastName" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.dob.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.dob" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthplace.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.birthPlace" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthstate.label" /></div>
											</td>
											<td height="35" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.stateOfBirth" /></td>
										</tr>
										<tr>
											<td height="22" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthcountry.label" /></div>
											</td>
											<td height="22" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.countryOfBirth" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.nationality.label" /></div>
											</td>
											<td height="35" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.citizenship" /></td>
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
											<div align="right"><bean:message
												key="admissionForm.studentinfo.residentcatg.label" /></div>
											</td>
											<td width="56%" height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.residentCategoryName" /></td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.religion.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.religionName" /></td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.subreligion.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.subregligionName" /></td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.castcatg.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.casteCategory" /></td>
										</tr>
										<tr>
										<logic:equal  name="interviewResultEntryForm" property="viewextradetails" value="true">
<logic:equal  name="interviewResultEntryForm" property="viewParish" value="true">
												<tr>
											<td height="20" class="row-odd">
											<div align="right">Diocese</div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.dioceseName" /></td>
										</tr>
											
											
												<tr>
											<td height="20" class="row-odd">
											<div align="right">Parish</div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.parishName" /></td>
										</tr>
											</logic:equal></logic:equal>
											<td height="17" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.belongsto.label" /></div>
											</td>
											<td height="17" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.belongsTo" />
											</td>
										</tr>
										<tr>
											<td height="17" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.gender" />:</div>
											</td>
											<td height="17" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.gender" /></td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.bloodgroup.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.bloodGroup" /></td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.landlineNo" />
											&nbsp;</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.mobileNo" /></td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.email" /> <br />
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
											<td width="236" height="23" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.passportNo" /></td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.issuingCountry" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.passportIssuingCountry" /></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.validUpto" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.passportValidity" /></td>
											<td class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.residentpermit.label" /></div>
											</td>
											<td class="row-white">
											<p>&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.residentPermitNo" /></p>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.policedate.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.residentPermitDate" /></td>
											<td class="row-odd">
											<div align="right"></div>
											</td>
											<td class="row-even">
											<p>&nbsp;</p>
											</td>
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
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="knowledgepro.admission.preference" /></td>
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
									<table width="100%" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td height="27" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.preferences" /></div>
											</td>
											<td height="27" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admin.programtype" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admin.program" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admin.course" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.selectedPreference" /></div>
											</td>
										</tr>
											<c:set var="selectedPrefId" scope="page"><bean:write name="interviewResultEntryForm" property="applicantDetails.preference.selectedPrefId"/></c:set>
										<logic:notEmpty name="interviewResultEntryForm" property="applicantDetails.preference.firstprefCourseName">
										<tr class="row-white">
											<td width="212" height="27" class="row-even">
											<div align="center"><bean:message
												key="knowledgepro.admission.firstpreference" />:</div>
											</td>
											<td width="236" height="27" class="row-even">
											<div align="center"><bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.firstprefPgmTypeName" /></div>
											</td>
											<td width="224" class="row-even">
											<div align="center"><bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.firstprefPgmName" /></div>
											</td>
											<td width="244" class="row-even">
											<div align="center"><bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.firstprefCourseName" /></div>
											</td>
											<td width="244" class="row-even">
											<c:set var="firstPrefId"><bean:write name="interviewResultEntryForm" property="applicantDetails.preference.firstPrefCourseId" /></c:set>
											<c:choose>
											<c:when test="${selectedPrefId == firstPrefId}">
											<div align="center"><input type="radio"
												name="selectedPrefId"
												value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.firstPrefCourseId" />' readonly="readonly" checked="checked"/></div>
											</c:when>
											<c:otherwise>
											<div align="center"><input type="radio"
												name="selectedPrefId"
												value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.firstPrefCourseId" />' readonly="readonly"/></div>
											</c:otherwise>
											</c:choose>	
											</td>
										</tr>
										</logic:notEmpty>
										<logic:notEmpty name="interviewResultEntryForm" property="applicantDetails.preference.secondprefCourseName">
										<tr class="row-white">
											<td height="27" class="row-white">
											<div align="center"><bean:message
												key="knowledgepro.admission.secondpreference" />:</div>
											</td>
											<td height="27" class="row-white">
											<div align="center"><bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.secondprefPgmTypeName" /></div>
											</td>
											<td class="row-white">
											<div align="center"><bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.secondprefPgmName" /></div>
											</td>
											<td class="row-white">
											<div align="center"><bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.secondprefCourseName" /></div>
											</td>
											<td class="row-white">
											<c:set var="secondPrefId"><bean:write name="interviewResultEntryForm" property="applicantDetails.preference.secondPrefCourseId" /></c:set>
											<c:choose>
											<c:when test="${selectedPrefId == secondPrefId}">
											<div align="center"><input type="radio"
												name="selectedPrefId"
												value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.secondPrefCourseId" />' readonly="readonly" checked="checked" /></div>
											</c:when>
											<c:otherwise>
											<div align="center"><input type="radio"
												name="selectedPrefId"
												value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.secondPrefCourseId" />' readonly="readonly" /></div>
											</c:otherwise>
											</c:choose>	
											</td>
										</tr>
										</logic:notEmpty>
										<logic:notEmpty name="interviewResultEntryForm" property="applicantDetails.preference.thirdprefCourseName">
										<tr class="row-white">
											<td height="27" class="row-even">
											<div align="center"><bean:message
												key="knowledgepro.admission.thirdpreference" />:</div>
											</td>
											<td height="27" class="row-even">
											<div align="center"><bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.thirdprefPgmTypeName" /></div>
											</td>
											<td class="row-even">
											<div align="center"><bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.thirdprefPgmName" /></div>
											</td>
											<td class="row-even">
											<div align="center"><bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.thirdprefCourseName" /></div>
											</td>
											<td class="row-even">
											<c:set var="thirdPrefId"><bean:write name="interviewResultEntryForm" property="applicantDetails.preference.thirdPrefCourseId" /></c:set>
											<c:choose>
											<c:when test="${selectedPrefId == thirdPrefId}">
											<div align="center"><input type="radio"
												name="selectedPrefId"
												value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.thirdPrefCourseId" />' readonly="readonly" checked="checked" /></div>
											</c:when>
											<c:otherwise>
											<div align="center"><input type="radio"
												name="selectedPrefId"
												value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.thirdPrefCourseId" />' readonly="readonly" /></div>
											</c:otherwise>
											</c:choose>	
											</td>
										</tr>
										</logic:notEmpty>
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
						<logic:notEmpty name="interviewResultEntryForm" property="applicantDetails.workExperienceList">
						<tr>
		                 	<td colspan="2" class="heading" align="left"><bean:message key="knowledgepro.admission.workexperience" /></td>
		                </tr>
		                <tr>
		                  <td colspan="2" class="heading"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
		                    <tr>
		                      <td ><img src="images/01.gif" width="5" height="5"></td>
		                      <td width="914" background="images/02.gif"></td>
		                      <td><img src="images/03.gif" width="5" height="5"></td>
		                    </tr>
		                    <tr>
		                      <td width="5"  background="images/left.gif"></td>
		                      <td height="31" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="1">
		                          <tr class="row-white">
		                            <td height="27" class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.organisationname" /></div></td>
		                            <td class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.designation" /></div></td>
		                            <td class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.fromdate" /></div></td>
		                            <td class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.todate" /></div></td>
		                            <td class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.lastsal.label" /></div></td>
		                            <td class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.reportto.label" /></div></td>
		                          </tr>
								<logic:iterate name="interviewResultEntryForm" property="applicantDetails.workExperienceList" id="workExpList" indexId="count">
								<c:choose>
									<c:when test="${count%2 == 0}">
										<tr class="row-even">
									</c:when>
									<c:otherwise>
										<tr class="row-white">
									</c:otherwise>
								</c:choose>
								    <td width="212" height="27"><div align="center">
		                            	<bean:write name="workExpList" property="organization" />
		                            </div>
		                            </td>
		                            <td width="236" height="27"><div align="center">
		                            	<bean:write name="workExpList" property="position" />
		                            </div>
		                            </td>
		                            <td width="224"><div align="center">
		                            	<bean:write name="workExpList" property="fromDate" />
									</div>
									</td>
		                            <td width="244"><div align="center">
		                            	<bean:write name="workExpList" property="toDate" />
		                            </div>
		                            </td>
		                            <td width="224"><div align="center">
		                            	<bean:write name="workExpList" property="salary" />
									</div>
									</td>
		                            <td width="244"><div align="center">
		                            	<bean:write name="workExpList" property="reportingTo" />
		                            </div>
		                            </td>
		                          </tr>
		                        </logic:iterate>
		                      </table></td>
		                      <td  background="images/right.gif" width="5" height="31"></td>
		                    </tr>
		                    <tr>
		                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
		                      <td background="images/05.gif"></td>
		                      <td><img src="images/06.gif" ></td>
		                    </tr>
		                  </table></td>
		                </tr>
		                </logic:notEmpty>
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
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="236" height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.permanentAddressLine1" /></td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.permanentAddressLine2" /></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.permanentCityName" /></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.permanentStateName" /></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.permanentCountryName" /></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.permanentAddressZipCode" /></td>
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
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="236" height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.currentAddressLine1" /></td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.currentAddressLine2" /></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.currentCityName" /></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.currentStateName" /></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.currentCountryName" /></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.currentAddressZipCode" /></td>
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
											<td class="row-odd">
											<div align="center">Exam Name</div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.universityBoard" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.instituteName" /></div>
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
												key="knowledgepro.admission.maxMark" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.attempts" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message key="knowledgepro.applicationform.prevregno.label" /></div>
											</td>
											
										</tr>
										<c:set var="temp" value="0" />
										<logic:iterate name="interviewResultEntryForm" property="applicantDetails.ednQualificationList"
											id="ednQualList" indexId="count">
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
														<td width="5%" height="25">
															<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td width="11%" height="25">
															<bean:write name="ednQualList" property="docName" />
														</td>
														<td>
															<div align="center">
															<logic:equal value="true" name="ednQualList" property="examConfigured">
																<bean:write name="ednQualList" property="selectedExamName" />
															</logic:equal>
															</div>
														</td>
														<td width="20%">
															<div align="center"><bean:write name="ednQualList" property="universityName" /></div>
														</td>
														<td width="22%">
															<div align="center"><bean:write name="ednQualList" property="institutionName" /></div>
														</td>
														<td width="16%"><bean:write name="ednQualList" property="yearPassing" /></td>
														<td width="16%">
															<logic:equal name="ednQualList" property="monthPassing" value="1"><bean:message key="knowledgepro.admin.month.january" /></logic:equal>
											              	<logic:equal name="ednQualList" property="monthPassing" value="2"><bean:message key="knowledgepro.admin.month.february" /></logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="3"><bean:message key="knowledgepro.admin.month.march" /></logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="4"><bean:message key="knowledgepro.admin.month.april" /></logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="5"><bean:message key="knowledgepro.admin.month.may" /></logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="6"><bean:message key="knowledgepro.admin.month.june" /></logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="7"><bean:message key="knowledgepro.admin.month.july" /></logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="8"><bean:message key="knowledgepro.admin.month.august" /></logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="9"><bean:message key="knowledgepro.admin.month.september" /></logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="10"><bean:message key="knowledgepro.admin.month.october" /></logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="11"><bean:message key="knowledgepro.admin.month.november" /></logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="12"><bean:message key="knowledgepro.admin.month.december" /></logic:equal>
														</td>		
														<logic:equal value="true" property="semesterWise" name="ednQualList">
														<logic:equal value="false" property="consolidated" name="ednQualList">
														<td colspan="2">
															<div align="center"><a href="javascript:void(0)" onclick="getSemesterMarkDetails('<bean:write name="ednQualList" property="id"/>')"><bean:message key="knowledgepro.admission.semester.view" /></a></div>
														</td>
														</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated" name="ednQualList">
														<logic:equal value="false" property="semesterWise" name="ednQualList">
														<td colspan="2">
															<div align="center"><a href="javascript:void(0)" onclick="getDetailsMark('<bean:write name="ednQualList" property="id"/>')"><bean:message key="knowledgepro.admission.detailed.view" /></a></div>
														</td>
														</logic:equal>
														</logic:equal>
														<logic:notEqual value="true" property="semesterWise" name="ednQualList">
														<logic:notEqual value="false" property="consolidated" name="ednQualList">
														<td>
															<div align="center"><bean:write name="ednQualList" property="marksObtained" /></div>
														</td>
														<td>
															<div align="center"><bean:write name="ednQualList" property="totalMarks" /></div>
														</td>
														</logic:notEqual>
														</logic:notEqual>
														<td width="9%">
															<div align="center"><bean:write name="ednQualList" property="noOfAttempts" /></div>
														</td>
														<td >
															<div align="center">
															<logic:equal value="false" name="ednQualList" property="lastExam">
																<bean:write name="ednQualList" property="previousRegNo" />
															</logic:equal>
															</div>
														</td>
														
													</tr>
										</logic:iterate>
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
											<td width="55%" height="36" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.fatherName" /></td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.fatherEducation" /></td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.income" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.fatherIncome" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.fatherOccupation" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.fatherEmail" /></td>
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
											<td width="55%" height="36" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.motherName" /></td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.motherEducation" /></td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.income" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.motherIncome" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.motherOccupation" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.motherEmail" /></td>
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
											<td width="178" height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.parentAddressLine1" />
											</td>
											<td width="100" height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td width="190" height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.parentCityName" />
											</td>
											<td width="122" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td width="206" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.parentAddressZipCode" />
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.parentAddressLine2" />
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.state" />
											</div>
											</td>
											<td class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.parentStateName" />
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.parentPhone" />
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs3.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.parentAddressLine3" />
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.admin.country" /></div>
											</td>
											<td height="20" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.parentCountryName" />
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.parentMobile" />
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
						<nested:notEmpty name="interviewResultEntryForm" property="applicantDetails.documentsList">
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
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.verifieddocuments" /></div>
											</td>
											<td height="25" class="row-odd"><bean:message
												key="knowledgepro.admission.documents" /></td>
											<td class="row-odd">&nbsp;</td>
											<td class="row-odd"><bean:message
												key="knowledgepro.admission.uploadDocs" /></td>
										</tr>
										<c:set var="temp" value="0" />
										<nested:iterate name="interviewResultEntryForm"
											property="applicantDetails.documentsList">
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
														<td width="8%" height="25">
															<div align="center"><nested:checkbox property="verified"></nested:checkbox></div>
														</td>
														<td width="18%" height="25"><nested:write property="name" /></td>
														<td width="28%">
															<nested:equal value="true" property="documentPresent">
																<a href="javascript:downloadFile('<nested:write property="id"/>')"><bean:message key="knowledgepro.view" /></a>
															</nested:equal>
														</td>
														<td width="46%">
															<nested:equal value="true" property="needToProduce">
																<nested:file property="document"></nested:file>
															</nested:equal>
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
							</table>
							</td>
						</tr>
						</nested:notEmpty>
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="knowledgepro.admission.interviewresult" /></td>
						</tr>
						<logic:iterate name="interviewResultEntryForm"
							property="applicantDetails.interviewResultList" id="iResultList">
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
										<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
											<tr>
												<td height="15" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.interviewType" />:</div>
												</td>
												<td height="15" class="row-even" align="left"><bean:write
													name="iResultList" property="interviewType" /></td>
												<td height="15" class="row-odd">
												<div align="right"><bean:message key="knowledgepro.admin.interviewsubround.subround"/>:</div>
												</td>
												<td height="15" class="row-even" align="left"><bean:write
													name="iResultList" property="subroundName" /></td>
											</tr>
											<tr>
												<td width="24%" height="15" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.status" />:</div>
												</td>
												<td width="25%" height="10" class="row-even" align="left">&nbsp;<bean:write
													name="iResultList" property="interviewStatus" /></td>
												<td height="15" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.gradeObtained" />:</div>
												</td>
												<td height="15" class="row-even" align="left">&nbsp;<bean:write
													name="iResultList" property="marksObtained" /></td>
											</tr>
											<tr>
												<td height="15" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.recommendedby" />:</div>
												</td>
												<td height="10" class="row-even" align="left">&nbsp;<bean:write
													name="iResultList" property="referredBy" />
												</td>
												<td width="23%" height="15" rowspan="2" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.comment" />:</div>
												</td>
												<td width="28%" height="15" rowspan="2" class="row-even"
													align="left"><bean:write
													name="iResultList" property="comments" />
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
						</logic:iterate>
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
											<td height="15" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.interviewType" />:</div>
											</td>
											<td height="15" class="row-even" align="left">&nbsp;<html:select
												property="interviewTypeId" onchange="getinterviewSubrounds(this.value)" styleClass="combo">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
												<html:optionsCollection property="interviewTypes"
													label="value" value="key" />
											</html:select></td>
											<td class="row-odd">
												<div align="right" id="subround">&nbsp;<bean:message key="knowledgepro.admin.interviewsubround.subround"/>:</div>
												</td>
												<td class="row-even">
												<html:select property="interviewSubroundId" styleId="interviewSubrounds" styleClass="combo">
													<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
													<c:if test="${interviewResultEntryForm.interviewTypeId != null && interviewResultEntryForm.interviewTypeId != ''}">
														<c:set var="interviewSubroundsMap" value="${baseActionForm.collectionMap['interviewSubroundsMap']}" />
														<c:if test="${interviewSubroundsMap != null}">
															<html:optionsCollection name="interviewSubroundsMap" label="value" value="key" />
														</c:if>
													</c:if>
												</html:select>
											</td>
										</tr>
										<tr>
											<td width="24%" height="15" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.status" />:</div>
											</td>
											<td width="25%" height="10" class="row-even" align="left">&nbsp;<html:select
												property="interviewStatusId" styleClass="combo">
												<html:optionsCollection property="interviewStatus"
													label="value" value="key" />
											</html:select></td>
											
											<td height="15" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.gradeObtained" />:</div>
											</td>
											<td height="15" class="row-even" align="left"><span
												class="row-white">&nbsp;<html:select
												property="gradeObtainedId" styleClass="combo">
												<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
												<html:optionsCollection property="grades"
													label="value" value="key" />
											</html:select></span></td>
										</tr>
										<tr>
											<td height="15" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.recommendedby" />:</div>
											</td>
											<td height="10" class="row-even" align="left">&nbsp;<html:select
												property="referredById" styleClass="combo">
												<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
												<html:optionsCollection property="referredBy" label="value"
													value="key" />
											</html:select></td>
											<td width="23%" height="15" rowspan="2" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.comment" />:</div>
											</td>
											<td width="28%" height="15" rowspan="2" class="row-even"
												align="left"><span class="row-white"><html:textarea
												property="comments" styleId="comments" cols="30" rows="2"></html:textarea>
											</span></td>
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
							<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="47%" height="21">
									<div align="right"><html:submit styleClass="formbutton">
										<bean:message key="knowledgepro.submit" />
									</html:submit></div>
									</td>
									<td width="2%">&nbsp;&nbsp;<html:button property="" styleClass="formbutton"
										onclick="resetFieldAndErrMsgs()">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button>&nbsp;&nbsp;</td>
									<td width="51%" height="45" align="left"><html:button
										property="" styleClass="formbutton"
										onclick="javascript:cancel()">
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
</html:form>