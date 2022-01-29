<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<SCRIPT type="text/javascript">
	function getSemesterMarkDetails(qualId) {
		var url  = "InterviewResultEntry.do?method=initSemesterMarkEditPage&editcountID="+qualId;
    	myRef = window.open(url,"ViewSemesterMarkDetails","left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
   	}
	function getDetailsMark(qualId) {
		var url  = "InterviewResultEntry.do?method=initDetailMarkEditPage&editcountID="+qualId;
    	myRef = window.open(url,"ViewDetailsMark","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
   	}
	function detailLateralSubmit()
	{
		var url  = "InterviewResultEntry.do?method=viewLateralEntryPage";
    	myRef = window.open(url,"ViewLateralDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function detailTransferSubmit()
	{
		var url  = "InterviewResultEntry.do?method=viewTransferEntryPage";
    	myRef = window.open(url,"ViewTransferDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
</SCRIPT>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title>:: <bean:message
	key="knowledgepro.admission.viewapplicantdetails" /> ::</title>
</head>
<body>
<html:form action="/ApplicantDetails">
	<html:hidden property="method" styleId="method"
		value="updateSelectedPreference" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="interviewResultEntryForm" />

	<table width="98%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="5">
					<div align="left">
					<div id="err" style="color: red;"></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</div>
					</td>
				</tr>
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.admission.viewapplicantdetails" /></strong></div>
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
								key="admissionForm.application.main.label" /></td>
						</tr>
						<tr>
							<td colspan="2" class="heading" align="left"></td>
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
											<td class="row-even">
											<c:if test="${interviewResultEntryForm.applicantDetails.personalData.isRecommended=='true'}">
												<b>Recommended</b>
											</c:if>
											
											</td>	
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
										<tr class="row-even">
											<td width="16%" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.programtype" />:</div>
											</td>
											<td width="16%" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.course.programTypeCode" /></td>
											<td width="16%" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.program" />:</div>
											</td>
											<td width="16%" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.course.programCode" /></td>
											<td width="16%" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.course" />:</div>
											</td>
											<td width="16%" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.course.code" /></td>
										</tr>
										<tr class="row-even">
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.challanNo" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.challanRefNo" /></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.journalNo" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.journalNo" /></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.date" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.challanDate" /></td>
										</tr>
										<tr class="row-even">
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.amount" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.amount" /></td>
											<td class="row-odd" align="right">&nbsp;<bean:message
												key="knowledgepro.applicationform.branchCode" /></td>
											<td>&nbsp;<bean:write name="interviewResultEntryForm"
												property="applicantDetails.bankBranch" /></td>
											<td class="row-odd" align="right">Interview Date:</td>
											<td>&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.interviewDate" /></td>
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
											<td width="55%" height="25" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.firstName" />&nbsp;
											<bean:write name="interviewResultEntryForm"
												property="applicantDetails.personalData.middleName" />&nbsp;
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
											<td height="17" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.belongsto.label" /></div>
											</td>
											<td height="17" class="row-even" align="left">&nbsp;<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.belongsTo" /></td>
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
											<%String dynaStyle3="display:none;"; %>
											<logic:equal value="true"
												property="applicantDetails.personalData.sportsPerson"
												name="interviewResultEntryForm">
												<%dynaStyle3="display:block;"; %>
											</logic:equal>
											<td height="17" class="row-odd" width="40%">
											<div align="right">Sports person:</div>
											</td>
											<td height="17" class="row-even" align="left"><logic:equal
												value="true"
												property="applicantDetails.personalData.sportsPerson"
												name="interviewResultEntryForm">Yes</logic:equal> <logic:equal
												value="false"
												property="applicantDetails.personalData.sportsPerson"
												name="interviewResultEntryForm">No</logic:equal>
											<div id="sports_description" style="<%=dynaStyle3 %>">
											<bean:write name="interviewResultEntryForm"
												property="applicantDetails.personalData.sportsDescription" />
											</div>
											</td>
										</tr>
										<tr>
											<%String dynaStyle4="display:none;"; %>
											<logic:equal value="true"
												property="applicantDetails.personalData.handicapped"
												name="interviewResultEntryForm">
												<%dynaStyle4="display:block;"; %>
											</logic:equal>
											<td height="17" class="row-odd" width="40%">
											<div align="right">Handicapped:</div>
											</td>
											<td height="17" class="row-even" align="left"><logic:equal
												value="true"
												property="applicantDetails.personalData.handicapped"
												name="interviewResultEntryForm">Yes</logic:equal> <logic:equal
												value="false"
												property="applicantDetails.personalData.handicapped"
												name="interviewResultEntryForm">No</logic:equal>
											<div id="handicapped_description" style="<%=dynaStyle4 %>">
											<bean:write name="interviewResultEntryForm"
												property="applicantDetails.personalData.hadnicappedDescription" />
											</div>
											</td>
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
										<logic:equal value="true"
											property="applicantDetails.displaySecondLanguage"
											name="interviewResultEntryForm">
											<tr>
												<td height="20" class="row-odd" width="40%">
												<div align="right"><bean:message
													key="knowledgepro.applicationform.secLang.label" /></div>
												</td>
												<td height="20" class="row-even" align="left"><bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.personalData.secondLanguage" />
												</td>
											</tr>
										</logic:equal>
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
												property="applicantDetails.personalData.email" /><br />
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
						<logic:equal value="true"
							property="applicantDetails.displayExtraDetails"
							name="interviewResultEntryForm">
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
										<td width="100%" background="images/02.gif"></td>
										<td><img src="images/03.gif" width="5" height="5"></td>
									</tr>
									<tr>
										<td width="5" background="images/left.gif"></td>
										<td valign="top">
										<table width="100%" border="0" cellpadding="0" cellspacing="1">
											<tr class="row-white">
												<td height="23" width="25%" class="row-even">
												<div align="center"><b><bean:message
													key="knowledgepro.applicationform.mothertongue.label" /></b></div>
												</td>
												<td height="23" width="25%" class="row-even" align="left">&nbsp;<bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.personalData.motherTongue" /></td>
												<td height="23" width="25%" class="row-even"><b><bean:message
													key="knowledgepro.applicationform.height.label" /></b>&nbsp; <bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.personalData.height" /></td>
												<td height="23" width="25%" class="row-even"><b><bean:message
													key="knowledgepro.applicationform.weight.label" /></b>&nbsp; <bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.personalData.weight" /></td>
											</tr>
											<logic:equal value="true"
												property="applicantDetails.displayLanguageKnown"
												name="interviewResultEntryForm">
												<tr class="row-white">
													<td height="23" colspan="4" class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.applicationform.language.label" /></div>
													</td>
												</tr>
												<tr class="row-white">
													<td height="23" width="33%" class="row-even" align="left"><b><bean:message
														key="knowledgepro.applicationform.speaklanguage.label" /></b>:&nbsp;
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.personalData.languageByLanguageSpeak" />
													</td>
													<td height="23" width="33%" class="row-even" align="left"><b><bean:message
														key="knowledgepro.applicationform.readlanguage.label" /></b>:&nbsp;
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.personalData.languageByLanguageRead" />
													</td>
													<td height="23" width="33%" class="row-even" align="left"><b><bean:message
														key="knowledgepro.applicationform.writelanguage.label" /></b>:&nbsp;
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.personalData.languageByLanguageWrite" />
													</td>
													<td height="23" class="row-even">&nbsp;</td>
												</tr>
											</logic:equal>
											<logic:equal value="true"
												property="applicantDetails.displayTrainingDetails"
												name="interviewResultEntryForm">
												<tr class="row-white">
													<td height="23" colspan="4" class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.applicationform.training.label" /></div>
													</td>
												</tr>
												<tr class="row-white">
													<td height="23" width="25%" class="row-even" align="right"><b><bean:message
														key="knowledgepro.applicationform.trainingprog.label" /></b></td>
													<td height="23" width="25%" class="row-even" align="left">&nbsp;
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.personalData.trainingProgName" />
													</td>
													<td height="23" width="25%" class="row-even" align="right"><b><bean:message
														key="knowledgepro.applicationform.trainingduration.label" /></b></td>
													<td height="23" width="25%" class="row-even" align="left">&nbsp;
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.personalData.trainingDuration" />
													</td>
												</tr>
												<tr class="row-white">
													<td height="23" width="25%" class="row-even" align="right"><b><bean:message
														key="knowledgepro.applicationform.traininginst.label" /></b></td>
													<td height="23" width="25%" class="row-even" align="left">&nbsp;
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.personalData.trainingInstAddress" />
													</td>
													<td height="23" width="25%" class="row-even" align="right"><b><bean:message
														key="knowledgepro.applicationform.trainingpurpose.label" /></b></td>
													<td height="23" width="25%" class="row-even" align="left">&nbsp;
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.personalData.trainingPurpose" />
													</td>
												</tr>
											</logic:equal>
											<logic:equal value="true"
												property="applicantDetails.displayAdditionalInfo"
												name="interviewResultEntryForm">
												<tr class="row-white">
													<td height="23" colspan="4" class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.applicationform.addninfo.label" /></div>
													</td>
												</tr>
												<tr class="row-white">
													<td height="23" width="25%" class="row-even" align="right">How
													did you know about this course?</td>
													<td height="23" width="25%" class="row-even" align="left">&nbsp;
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.personalData.courseKnownBy" />
													</td>
													<td height="23" width="25%" class="row-even" align="right"><b><bean:message
														key="knowledgepro.applicationform.strength.label" /></b></td>
													<td height="23" width="25%" class="row-even" align="left">&nbsp;
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.personalData.strength" /></td>
												</tr>
												<tr class="row-white">
													<td height="23" width="25%" class="row-even" align="right"><bean:message
														key="knowledgepro.applicationform.addninfo.label2" /> <B><bean:write
														property="applicantDetails.course.name"
														name="interviewResultEntryForm" /></B>:</td>
													<td height="23" width="25%" class="row-even" align="left">&nbsp;
													<bean:write
														property="applicantDetails.personalData.courseOptReason"
														name="interviewResultEntryForm" /></td>
													<td height="23" width="25%" class="row-even" align="right"><b><bean:message
														key="knowledgepro.applicationform.weakness.label" /></b></td>
													<td height="23" width="25%" class="row-even" align="left">&nbsp;
													<bean:write
														property="applicantDetails.personalData.weakness"
														name="interviewResultEntryForm" /></td>
												</tr>
												<tr class="row-white">
													<td height="23" colspan="3" class="row-even" align="right"><bean:message
														key="knowledgepro.applicationform.addninfo.label3" /> <B><bean:write
														property="applicantDetails.course.name"
														name="interviewResultEntryForm" /></B>:</td>
													<td height="23" class="row-even" align="left">&nbsp; <bean:write
														property="applicantDetails.personalData.otherAddnInfo"
														name="interviewResultEntryForm" /></td>
												</tr>
											</logic:equal>
											<tr class="row-white">
												<td height="23" colspan="4" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.applicationform.extracurr.label" /></div>
												</td>
											</tr>
											<tr class="row-white">
												<td height="23" width="25%" class="row-even" align="right">Selected
												extra curricular:</td>
												<td height="23" width="25%" class="row-even" align="left">&nbsp;
												<bean:write
													property="applicantDetails.personalData.extracurricularNames"
													name="interviewResultEntryForm" /></td>
												<td height="23" width="25%" class="row-even" align="left">&nbsp;</td>
												<td height="23" width="25%" class="row-even" align="left">&nbsp;</td>
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
										<tr class="row-even">
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
											<div align="right"><bean:message
												key="knowledgepro.applicationform.residentpermit.label" /></div>
											</td>
											<td class="row-white">
											<p>&nbsp;<bean:write name="interviewResultEntryForm"
												property="applicantDetails.personalData.residentPermitNo" /></p>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.applicationform.policedate.label" /></div>
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
						<c:if
							test="${interviewResultEntryForm.applicantDetails.vehicleDetail != null}">
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
												<td width="236" height="25" class="row-even" align="left">&nbsp;
												<bean:write name="interviewResultEntryForm"
													property="applicantDetails.vehicleDetail.vehicleType" /></td>
												<td width="224" height="25" class="row-odd">
												<div align="right"><bean:message
													key="admissionForm.edit.vehicleno.label" /></div>
												</td>
												<td width="244" height="25" class="row-even" align="left">&nbsp;
												<bean:write name="interviewResultEntryForm"
													property="applicantDetails.vehicleDetail.vehicleNo" /></td>
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
						</c:if>
						<logic:notEmpty name="interviewResultEntryForm"
							property="applicantDetails.prerequisiteTos">
							<tr>
								<td colspan="2" class="heading" align="left"><bean:message
									key="admissionForm.approveview.prereq.label" /></td>
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
												<td height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admin.pre.requisite" /></div>
												</td>
												<td height="25" class="row-odd" align="center"><bean:message
													key="knowledgepro.admission.totalmarks" /></td>
												<td height="25" class="row-odd" align="center"><bean:message
													key="knowledgepro.attendance.rollno" /></td>
												<td height="25" class="row-odd">
												<div align="center"><bean:message
													key="admissionForm.approveview.prereq.exammonth.label" /></div>
												</td>
												<td height="25" class="row-odd" align="center"><bean:message
													key="admissionForm.approveview.prereq.examyear.label" /></td>
											</tr>

											<logic:iterate id="prereq"
												property="applicantDetails.prerequisiteTos"
												name="interviewResultEntryForm">
												<tr class="row-white">
													<td height="25" class="row-even">
													<div align="center"><bean:write
														property="prerequisiteName" name="prereq" /></div>
													</td>
													<td height="25" class="row-even" align="center"><bean:write
														property="prerequisiteMarksObtained" name="prereq" /></td>
													<td height="25" class="row-even" align="center"><bean:write
														property="rollNo" name="prereq" /></td>
													<td height="25" class="row-even">
													<div align="center"><logic:equal name="prereq"
														property="examMonth" value="1">
														<bean:message key="knowledgepro.admin.month.january" />
													</logic:equal> <logic:equal name="prereq" property="examMonth" value="2">
														<bean:message key="knowledgepro.admin.month.february" />
													</logic:equal> <logic:equal name="prereq" property="examMonth" value="3">
														<bean:message key="knowledgepro.admin.month.march" />
													</logic:equal> <logic:equal name="prereq" property="examMonth" value="4">
														<bean:message key="knowledgepro.admin.month.april" />
													</logic:equal> <logic:equal name="prereq" property="examMonth" value="5">
														<bean:message key="knowledgepro.admin.month.may" />
													</logic:equal> <logic:equal name="prereq" property="examMonth" value="6">
														<bean:message key="knowledgepro.admin.month.june" />
													</logic:equal> <logic:equal name="prereq" property="examMonth" value="7">
														<bean:message key="knowledgepro.admin.month.july" />
													</logic:equal> <logic:equal name="prereq" property="examMonth" value="8">
														<bean:message key="knowledgepro.admin.month.august" />
													</logic:equal> <logic:equal name="prereq" property="examMonth" value="9">
														<bean:message key="knowledgepro.admin.month.september" />
													</logic:equal> <logic:equal name="prereq" property="examMonth" value="10">
														<bean:message key="knowledgepro.admin.month.october" />
													</logic:equal> <logic:equal name="prereq" property="examMonth" value="11">
														<bean:message key="knowledgepro.admin.month.november" />
													</logic:equal> <logic:equal name="prereq" property="examMonth" value="12">
														<bean:message key="knowledgepro.admin.month.december" />
													</logic:equal></div>
													</td>
													<td height="25" class="row-even" align="center"><bean:write
														property="examYear" name="prereq" /></td>
												</tr>
											</logic:iterate>
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
						</logic:notEmpty>
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
										<c:set var="selectedPrefId" scope="page">
											<bean:write name="interviewResultEntryForm"
												property="applicantDetails.preference.selectedPrefId" />
										</c:set>
										<logic:notEmpty name="interviewResultEntryForm"
											property="applicantDetails.preference.firstprefCourseName">
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
												<td width="244" class="row-even"><c:set
													var="firstPrefId">
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.preference.firstPrefCourseId" />
												</c:set> <c:choose>
													<c:when test="${selectedPrefId == firstPrefId}">
														<div align="center"><input type="radio"
															name="selectedPrefId"
															value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.firstPrefCourseId" />'
															checked="checked" /></div>
													</c:when>
													<c:otherwise>
														<div align="center"><input type="radio"
															name="selectedPrefId"
															value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.firstPrefCourseId" />' /></div>
													</c:otherwise>
												</c:choose></td>
											</tr>
										</logic:notEmpty>
										<logic:notEmpty name="interviewResultEntryForm"
											property="applicantDetails.preference.secondprefCourseName">
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
												<td class="row-white"><c:set var="secondPrefId">
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.preference.secondPrefCourseId" />
												</c:set> <c:choose>
													<c:when test="${selectedPrefId == secondPrefId}">
														<div align="center"><input type="radio"
															name="selectedPrefId"
															value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.secondPrefCourseId" />'
															checked="checked" /></div>
													</c:when>
													<c:otherwise>
														<div align="center"><input type="radio"
															name="selectedPrefId"
															value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.secondPrefCourseId" />' /></div>
													</c:otherwise>
												</c:choose></td>
											</tr>
										</logic:notEmpty>
										<logic:notEmpty name="interviewResultEntryForm"
											property="applicantDetails.preference.thirdprefCourseName">
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
												<td class="row-even"><c:set var="thirdPrefId">
													<bean:write name="interviewResultEntryForm"
														property="applicantDetails.preference.thirdPrefCourseId" />
												</c:set> <c:choose>
													<c:when test="${selectedPrefId == thirdPrefId}">
														<div align="center"><input type="radio"
															name="selectedPrefId"
															value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.thirdPrefCourseId" />'
															checked="checked" /></div>
													</c:when>
													<c:otherwise>
														<div align="center"><input type="radio"
															name="selectedPrefId"
															value='<bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.preference.thirdPrefCourseId" />' /></div>
													</c:otherwise>
												</c:choose></td>
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
						<logic:notEmpty name="interviewResultEntryForm"
							property="applicantDetails.workExperienceList">
							<tr>
								<td colspan="2" class="heading" align="left"><bean:message
									key="knowledgepro.admission.workexperience" /></td>
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
												<td height="27" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admission.organisationname" /></div>
												</td>
												<td class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admission.designation" /></div>
												</td>
												<td class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admission.fromdate" /></div>
												</td>
												<td class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admission.todate" /></div>
												</td>
												<td class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.applicationform.lastsal.label" /></div>
												</td>
												<td class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.applicationform.reportto.label" /></div>
												</td>
											</tr>
											<logic:iterate name="interviewResultEntryForm"
												property="applicantDetails.workExperienceList"
												id="workExpList" indexId="count">
												<tr class="row-white">
													<td width="212" height="27" class="row-even">
													<div align="center"><bean:write name="workExpList"
														property="organization" /></div>
													</td>
													<td width="236" height="27" class="row-even">
													<div align="center"><bean:write name="workExpList"
														property="position" /></div>
													</td>
													<td width="224" class="row-even">
													<div align="center"><bean:write name="workExpList"
														property="fromDate" /></div>
													</td>
													<td width="244" class="row-even">
													<div align="center"><bean:write name="workExpList"
														property="toDate" /></div>
													</td>
													<td width="224" class="row-even">
													<div align="center"><bean:write name="workExpList"
														property="salary" /></div>
													</td>
													<td width="244" class="row-even">
													<div align="center"><bean:write name="workExpList"
														property="reportingTo" /></div>
													</td>
												</tr>
											</logic:iterate>
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
										<tr class="row-even">
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
										<tr class="row-even">
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
											<td class="row-odd">Exam Name</td>
											<td class="row-odd">
											<div align="left"><bean:message
												key="knowledgepro.admission.universityBoard" /></div>
											</td>
											<td class="row-odd">
											<div align="left"><bean:message
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
												key="knowledgepro.admission.totalMarks" /></div>
											</td>
											<td class="row-odd">
											<div align="center">Percentage</div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.attempts" /></div>
											</td>
											<td class="row-odd"><bean:message
												key="knowledgepro.applicationform.prevregno.label" /></td>
											
										</tr>
										<c:set var="temp" value="0" />
										<logic:iterate name="interviewResultEntryForm"
											property="applicantDetails.ednQualificationList"
											id="ednQualList" indexId="count">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td width="5%" height="25" class="row-even">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td width="11%" height="25" class="row-even">
														<div align="center"><bean:write name="ednQualList"
															property="docName" /></div>
														</td>
														<td width="9%" class="row-even">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="examConfigured">
															<bean:write name="ednQualList" property="selectedExamName" />
														</logic:equal></div>
														</td>
														<td width="20%" class="row-even">
														<div align="center"><bean:write name="ednQualList"
															property="universityName" /></div>
														</td>
														<td width="22%" class="row-even">
														<div align="center"><bean:write name="ednQualList"
															property="institutionName" /></div>
														</td>
														<td width="16%" class="row-even"><bean:write
															name="ednQualList" property="yearPassing" /></td>
														<td width="16%" class="row-even"><logic:equal
															name="ednQualList" property="monthPassing" value="1">
															<bean:message key="knowledgepro.admin.month.january" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="2">
															<bean:message key="knowledgepro.admin.month.february" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="3">
															<bean:message key="knowledgepro.admin.month.march" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="4">
															<bean:message key="knowledgepro.admin.month.april" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="5">
															<bean:message key="knowledgepro.admin.month.may" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="6">
															<bean:message key="knowledgepro.admin.month.june" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="7">
															<bean:message key="knowledgepro.admin.month.july" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="8">
															<bean:message key="knowledgepro.admin.month.august" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="9">
															<bean:message key="knowledgepro.admin.month.september" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="10">
															<bean:message key="knowledgepro.admin.month.october" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="11">
															<bean:message key="knowledgepro.admin.month.november" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="12">
															<bean:message key="knowledgepro.admin.month.december" />
														</logic:equal></td>
														<logic:equal value="true" property="semesterWise"
															name="ednQualList">
															<logic:equal value="false" property="consolidated"
																name="ednQualList">
																<td class="row-even" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getSemesterMarkDetails('<bean:write name="ednQualList" property="id"/>')">View
																Semester Marks</a></div>
																</td>
															</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated"
															name="ednQualList">
															<logic:equal value="false" property="semesterWise"
																name="ednQualList">
																<td class="row-even" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getDetailsMark('<bean:write name="ednQualList" property="id"/>')">View
																Detailed Marks</a></div>
																</td>
															</logic:equal>
														</logic:equal>
														<logic:notEqual value="true" property="semesterWise"
															name="ednQualList">
															<logic:notEqual value="false" property="consolidated"
																name="ednQualList">
																<td class="row-even">
																<div align="center"><bean:write name="ednQualList"
																	property="marksObtained" /></div>
																</td>
																<td class="row-even">
																<div align="center"><bean:write name="ednQualList"
																	property="totalMarks" /></div>
																</td>
															</logic:notEqual>
														</logic:notEqual>
														<td width="9%" class="row-even">
														<div align="center"><bean:write name="ednQualList"
															property="percentage" /></div>
														</td>
														<td width="9%" class="row-even">
														<div align="center"><bean:write name="ednQualList"
															property="noOfAttempts" /></div>
														</td>
														
														<td width="9%" class="row-even">
														<div align="center"><logic:equal value="false"
															name="ednQualList" property="lastExam">
															<bean:write name="ednQualList" property="previousRegNo" />
														</logic:equal></div>
														</td>
														
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td height="25" class="row-white">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td height="25" class="row-white">
														<div align="center"><bean:write name="ednQualList"
															property="docName" /></div>
														</td>
														<td width="9%" class="row-white">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="examConfigured">
															<bean:write name="ednQualList" property="selectedExamName" />
														</logic:equal></div>
														</td>
														<td class="row-white">
														<div align="center"><bean:write name="ednQualList"
															property="universityName" /></div>
														</td>
														<td class="row-white">
														<div align="center"><bean:write name="ednQualList"
															property="institutionName" /></div>
														</td>
														<td class="row-white"><bean:write name="ednQualList"
															property="yearPassing" /></td>
														<td width="16%" class="row-white"><logic:equal
															name="ednQualList" property="monthPassing" value="1">
															<bean:message key="knowledgepro.admin.month.january" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="2">
															<bean:message key="knowledgepro.admin.month.february" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="3">
															<bean:message key="knowledgepro.admin.month.march" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="4">
															<bean:message key="knowledgepro.admin.month.april" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="5">
															<bean:message key="knowledgepro.admin.month.may" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="6">
															<bean:message key="knowledgepro.admin.month.june" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="7">
															<bean:message key="knowledgepro.admin.month.july" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="8">
															<bean:message key="knowledgepro.admin.month.august" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="9">
															<bean:message key="knowledgepro.admin.month.september" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="10">
															<bean:message key="knowledgepro.admin.month.october" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="11">
															<bean:message key="knowledgepro.admin.month.november" />
														</logic:equal> <logic:equal name="ednQualList" property="monthPassing"
															value="12">
															<bean:message key="knowledgepro.admin.month.december" />
														</logic:equal></td>
														<logic:equal value="true" property="semesterWise"
															name="ednQualList">
															<logic:equal value="false" property="consolidated"
																name="ednQualList">
																<td class="row-white" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getSemesterMarkDetails('<bean:write name="ednQualList" property="id"/>')">View
																Semester Marks</a></div>
																</td>
															</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated"
															name="ednQualList">
															<logic:equal value="false" property="semesterWise"
																name="ednQualList">
																<td class="row-white" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getDetailsMark('<bean:write name="ednQualList" property="id"/>')">View
																Detailed Marks</a></div>
																</td>
															</logic:equal>
														</logic:equal>
														<logic:notEqual value="true" property="semesterWise"
															name="ednQualList">
															<logic:notEqual value="false" property="consolidated"
																name="ednQualList">
																<td class="row-white">
																<div align="center"><bean:write name="ednQualList"
																	property="marksObtained" /></div>
																</td>
																<td class="row-white">
																<div align="center"><bean:write name="ednQualList"
																	property="totalMarks" /></div>
																</td>
															</logic:notEqual>
														</logic:notEqual>
														<td width="9%" class="row-white">
														<div align="center"><bean:write name="ednQualList"
															property="percentage" /></div>
														</td>
														<td class="row-white">
														<div align="center"><bean:write name="ednQualList"
															property="noOfAttempts" /></div>
														</td>
														<td width="9%" class="row-white">
														<div align="center"><logic:equal value="false"
															name="ednQualList" property="lastExam">
															<bean:write name="ednQualList" property="previousRegNo" />
														</logic:equal></div>
														</td>
														
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</logic:iterate>
										<logic:equal value="true"
											property="applicantDetails.displayTCDetails"
											name="interviewResultEntryForm">
											<tr>
												<td height="25" class="row-odd">
												<div align="center"><bean:message
													key="admissionForm.education.TCNO.label" /></div>
												</td>
												<td height="25" class="row-even"><bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.tcNo" /></td>
												<td class="row-odd">
												<div align="left"><bean:message
													key="admissionForm.education.TCDate.label" /></div>
												</td>
												<td class="row-even" colspan="2">
												<div align="left"><bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.tcDate" /></div>
												</td>

												<td class="row-odd"><bean:message
													key="admissionForm.education.markcard.label" /></td>
												<td class="row-even">
												<div align="center"><bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.markscardNo" /></div>
												</td>
												<td class="row-odd">
												<div align="center"><bean:message
													key="admissionForm.education.markcarddate.label" /></div>
												</td>
												<td class="row-even" colspan="2">
												<div align="left"><bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.markscardDate" /></div>
												</td>
											</tr>
										</logic:equal>
										<logic:equal value="true"
											property="applicantDetails.displayLateralTransfer"
											name="interviewResultEntryForm">
											<tr>
												<td class="row-even">
												<div align="center">&nbsp;</div>
												</td>
												<td height="25" colspan="2" class="row-even"><logic:equal
													value="true"
													property="applicantDetails.displayLateralDetails"
													name="interviewResultEntryForm">
													<div align="center"><a href="javascript:void(0)"
														onclick="detailLateralSubmit()">Lateral Course Details</a></div>
												</logic:equal></td>
												<td height="25" colspan="2" class="row-even">
												<div align="center">&nbsp;</div>
												</td>
												<td class="row-even" colspan="2"><logic:equal
													value="true"
													property="applicantDetails.displayTransferDetails"
													name="interviewResultEntryForm">
													<div align="center"><a href="javascript:void(0)"
														onclick="detailTransferSubmit()">Transfer Course
													Details</a></div>
												</logic:equal></td>
												<td class="row-even" colspan="2">
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
						<c:if
							test="${interviewResultEntryForm.applicantDetails.entranceDetail != null}">
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
	            								<td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.prereq.roll.label" /></div></td>
												<td height="25" class="row-even"><div align="left">
													<bean:write name="interviewResultEntryForm" property="applicantDetails.entranceDetail.entranceRollNo" />
												</div></td>
												<td height="25" class="row-odd"><div align="right">Year of passing</div></td>
												<td height="25" class="row-even"><div align="left">
													<bean:write name="interviewResultEntryForm" property="applicantDetails.entranceDetail.yearPassing" />
												</div></td>
												<td height="25" class="row-odd"><div align="right">Month of passing</div></td>
												<td height="25" class="row-even"><div align="left">
													<bean:write name="interviewResultEntryForm" property="applicantDetails.entranceDetail.monthPassing" />
												</div></td>
												
	            							</tr>
	            							<tr>
	            								<td height="25" class="row-odd"><div align="right">Marks Obtained</div></td>
												<td height="25" class="row-even"><div align="left">
													<bean:write name="interviewResultEntryForm" property="applicantDetails.entranceDetail.marksObtained" />
												</div></td>
												<td height="25" class="row-odd"><div align="right">Total Marks</div></td>
												<td height="25" class="row-even"><div align="left">
													<bean:write name="interviewResultEntryForm" property="applicantDetails.entranceDetail.totalMarks" />
												</div></td>
												<td height="25" class="row-odd"></td>
												<td height="25" class="row-even"></td>
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
						</c:if>
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
						<logic:equal value="true"
							property="applicantDetails.displayFamilyBackground"
							name="interviewResultEntryForm">
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
												<bean:write name="interviewResultEntryForm"
													property="applicantDetails.personalData.brotherName" /></td>
											</tr>
											<tr class="row-odd">
												<td width="45%" height="36" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.education" /></div>
												</td>
												<td width="55%" height="36" class="row-even" align="left">&nbsp;
												<bean:write name="interviewResultEntryForm"
													property="applicantDetails.personalData.brotherEducation" />
												</td>
											</tr>

											<tr>
												<td height="45" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.income" /></div>
												</td>
												<td height="25" class="row-even" align="left">&nbsp; <bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.personalData.brotherIncome" />
												</td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admin.occupation" />:</div>
												</td>
												<td height="30" class="row-even" align="left"><bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.personalData.brotherOccupation" />
												</td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.applicationform.age.label" /></div>
												</td>
												<td height="30" class="row-even" align="left"><bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.personalData.brotherAge" /></td>
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
												<bean:write name="interviewResultEntryForm"
													property="applicantDetails.personalData.sisterName" /></td>
											</tr>
											<tr class="row-odd">
												<td width="45%" height="36" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.education" /></div>
												</td>
												<td width="55%" height="36" class="row-even" align="left">&nbsp;
												<bean:write name="interviewResultEntryForm"
													property="applicantDetails.personalData.sisterEducation" />
												</td>
											</tr>

											<tr>
												<td height="45" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.income" /></div>
												</td>
												<td height="25" class="row-even" align="left">&nbsp; <bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.personalData.sisterIncome" /></td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admin.occupation" />:</div>
												</td>
												<td height="30" class="row-even" align="left">&nbsp; <bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.personalData.sisterOccupation" />
												</td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.applicationform.age.label" /></div>
												</td>
												<td height="30" class="row-even" align="left">&nbsp; <bean:write
													name="interviewResultEntryForm"
													property="applicantDetails.personalData.sisterAge" /></td>
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
										<tr class="row-even">
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
												key="knowledgepro.admin.state" /></div>
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
												property="applicantDetails.personalData.parentPhone" /></td>
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
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
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
												property="applicantDetails.personalData.parentMobile" /></td>
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
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="178" height="20" class="row-even" align="left">&nbsp;
											<bean:write name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianAddressLine1" />
											</td>
											<td width="100" height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td width="190" height="20" class="row-even" align="left">&nbsp;
											<bean:write name="interviewResultEntryForm"
												property="applicantDetails.personalData.cityByGuardianAddressCityId" />
											</td>
											<td width="122" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td width="206" class="row-even" align="left">&nbsp; <bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianAddressZipCode" />
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp; <bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianAddressLine2" />
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td class="row-white" align="left"><bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianCountryName" />
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp; <bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianPh1" /> <bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianPh2" /> <bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianPh3" /></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs3.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp; <bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianAddressLine3" />
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" align="left">&nbsp; <bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianStateName" />
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" align="left">&nbsp; <bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianMob1" /> <bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianMob2" /> <bean:write
												name="interviewResultEntryForm"
												property="applicantDetails.personalData.guardianMob3" /></td>
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
						<logic:notEmpty name="interviewResultEntryForm"
							property="applicantDetails.interviewResultList">
							<tr>
								<td colspan="2" class="heading" align="left">&nbsp;<bean:message
									key="knowledgepro.admission.interviewresult" /></td>
							</tr>
						</logic:notEmpty>
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
												<div align="right"><bean:message
													key="knowledgepro.admin.interviewsubround.subround" />:</div>
												</td>
												<td height="15" class="row-even" align="left">&nbsp;<bean:write
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
													name="iResultList" property="referredBy" /></td>
												<td width="23%" height="15" rowspan="2" class="row-odd">
												<div align="right"><bean:message
													key="knowledgepro.admission.comment" />:</div>
												</td>
												<td width="28%" height="15" rowspan="2" class="row-even"
													align="left"><bean:write name="iResultList"
													property="comments" /></td>
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
												key="knowledgepro.fee.feeaccount.verifiedby.label" />:</div>
											</td>
											<td width="515" height="20" class="row-even" align="left">&nbsp;<nested:text
												name="interviewResultEntryForm"
												property="applicantDetails.verifiedBy" /></td>
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
							<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><html:submit styleClass="formbutton">
										<bean:message
											key="knowledgepro.admin.preferences.updatepreference" />
									</html:submit></div>
									</td>
									<td width="2%"></td>
									<td width="53"><input type="button" class="formbutton"
										value="Close" onclick="javascript:self.close();" /></td>

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
</body>
</html:html>