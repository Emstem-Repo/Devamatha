<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
	<html>
	<head>
<SCRIPT
	type="text/javascript">
	function downloadFile(documentId) {
		document.location.href = "DocumentDownloadAction.do?documentId="
				+ documentId;
	}
	function getSemesterMarkDetails(qualId) {
		var url = "admissionFormSubmit.do?method=viewSemesterMarkPage&editcountID="
				+ qualId;
		myRef = window
				.open(url, "ViewSemesterMarkDetails",
						"left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
	}
	function getDetailsMark(qualId) {
		var url = "admissionFormSubmit.do?method=viewDetailMarkPage&editcountID="
				+ qualId;
		myRef = window
				.open(url, "ViewDetailsMark",
						"left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function detailLateralSubmit() {
		var url = "admissionFormSubmit.do?method=viewLateralEntryPage";
		myRef = window
				.open(url, "ViewLateralDetails",
						"left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function detailTransferSubmit() {
		var url = "admissionFormSubmit.do?method=viewTransferEntryPage";
		myRef = window
				.open(url, "ViewTransferDetails",
						"left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function forwardQualifySeparate() {
		var url = "admissionFormSubmit.do?method=forwardQualifyExamPrint";
		myRef = window
				.open(url, "ViewTransferDetails",
						"left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function printMe() {
		window.print();
	}
	function closeMe() {
		window.close();
	}
</SCRIPT>

<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
</head>
<body>
<html:form action="/studentEdit" method="POST">
	<html:hidden property="method" value="updateApproval" />
	<html:hidden property="formName" value="studentEditForm" />
	<html:hidden property="pageType" value="16" />
	<input type="hidden" name="applicationId"
		value='<bean:write name="studentEditForm" property="applicantDetails.applicationId" />' />
	<input type="hidden" name="courseId"
		value='<bean:write	name="studentEditForm" property="applicantDetails.course.id" />' />
	<table width="600" border="0" bordercolor="#E0DFDB">
		<tr>
			<td>
				<table width="600" border="1" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
					<tr>
						<td valign="top" class="news">
							<div align="center">
								<table width="600" border="0" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
									<tr>
										<td align="left">
											<div id="errorMessage">
												<FONT color="red"><html:errors /></FONT>
												<FONT color="green">
													<html:messages id="msg" property="messages" message="true">
														<c:out value="${msg}" escapeXml="false"></c:out>
													</html:messages>
												</FONT>
											</div>
										</td>
									</tr>
									<tr>
										<td class="heading">
											<table width="600" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
												<tr>
													<td height="22" valign="top">
														<table width="640" height="22" border="0" cellpadding="0" cellspacing="1">
															<tr>
																<td height="25" rowspan="2" class="row-white" valign="top" style="width: 500px;">
																	<div align="left">
																		<img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available" width="210" height="100">
																	</div>
																</td>				
																<bean:define id="prgCode" name="studentEditForm" 	property="applicantDetails.course.programCode"></bean:define>
																<%
																	String ProgramCode = prgCode.toString().trim();
																%>
																<td height="23" width="50%" class="row-odd">
																	<table>
																		<!--<tr>
																			<bean:define id="applNo" name="studentEditForm" property="applicantDetails.applnNo"></bean:define>
																			<%
																				String barCodeImg = "BarCode/" + applNo + ".jpeg";
																			%>
																			<td align="center"><img src='<%=barCodeImg%>'></td>
																		</tr>
																		--><tr>
																			<td height="23" width="100%" class="row-odd" colspan="2">
																			
																				<div align="center" style="width: 100%">
																					<b>&nbsp;<%=ProgramCode%></b><b>
																				</b>
																				</div>
																				
																				<div align="center" style="width: 100%">
																					<b>&nbsp;&nbsp;APPLICATION:
																					<bean:write name="studentEditForm" property="applicantDetails.applnNo" /></b>
																				</div>
																				
																			</td>																			
																		</tr>
																	</table>
																</td>
																<td height="25" class="row-white" style="width: 140px;">
																	<div align="right">
																		<img src='<%=request.getAttribute("STUDENT_IMAGE")%>?time=<%=System.currentTimeMillis() %>' height="150Px" width="150Px" />
										</div>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="heading">
											<table width="640" border="1" align="left" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
												<tr>
													<td width="106" height="10" class="row-odd">
														<div align="left">
															<i>Course:</i>
														</div>
													</td>
													<td width="106" height="10" class="row-even" align="left">
														<logic:notEmpty name="studentEditForm" property="applicantDetails.course.programCode">
															&nbsp;<bean:write name="studentEditForm" property="applicantDetails.course.programCode" />
														</logic:notEmpty>
													</td>
														<td width="106" height="10" class="row-odd">
															<div align="left">
																<i>Programme/Subject:</i>
															</div>
														</td>
														<td width="106" height="10" class="row-even" align="left">
															<logic:notEmpty name="studentEditForm" property="applicantDetails.course.name">
																&nbsp;<bean:write name="studentEditForm" property="applicantDetails.course.name" />
															</logic:notEmpty>
														</td>
														<td width="106" height="10" class="row-odd">
															<div align="left">
																<i>Class No:</i>
															</div>
														</td>
														<td width="110" height="10" class="row-even" align="left">
															<logic:notEmpty name="studentEditForm" property="rollNo">
																&nbsp;<bean:write name="studentEditForm" property="rollNo" />
															</logic:notEmpty>
														</td>
													</tr>	
												</table>
											</td>
										</tr>	
										<tr>
											<td width="200" class="heading">
												<table width="640" border="1" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
													<tr>
														<td width="100" height="10" class="row-odd">
															<div align="left">
																<i><bean:message key="admissionForm.studentedit.name.label" /></i>
															</div>
														</td>
														<td width="550" height="10" class="row-even" align="left">&nbsp;
															<B> 
																<nested:write property="applicantDetails.personalData.firstName"></nested:write>&nbsp;
																<nested:write property="applicantDetails.personalData.middleName"></nested:write>&nbsp;
																<nested:write property="applicantDetails.personalData.lastName"></nested:write>
															</B>
														</td>
														<td width="100" height="10" class="row-odd">
															<div align="left">
																<i><bean:message key="knowledgepro.admin.gender" />:</i>
															</div>
														</td>
														<td width="100" height="10" class="row-even" align="left">&nbsp;
															<logic:equal name="studentEditForm" property="applicantDetails.personalData.gender" value="MALE">
																<bean:message key="admissionForm.studentinfo.sex.male.text" />
															</logic:equal>
															<logic:equal name="studentEditForm" property="applicantDetails.personalData.gender" value="FEMALE">
																<bean:message key="admissionForm.studentinfo.sex.female.text" />
															</logic:equal>
														</td>
													</tr>
													<tr>
														<td width="100" height="10" class="row-odd">
															<div align="left">
																<i><bean:message key="admissionForm.studentinfo.email.label" /></i>
															</div>
														</td>
														<td width="500" height="10" class="row-even" align="left">
															<nested:write property="applicantDetails.personalData.email"></nested:write>
														</td>
														<td width="100" height="10" class="row-odd">
															<div align="left"><i>Category:</i></div>
														</td>
														<td width="250" height="10" class="row-even" align="left">
															<bean:write name="studentEditForm" property="applicantDetails.personalData.residentCategoryName" />
														</td>
													</tr>
													<tr>
														<td width="100" height="10" class="row-odd">
															<div align="left">
																<i>Nature of Admission:</i>
															</div>
														</td>
														<td width="500" height="10" class="row-even" align="left">
															<nested:write property="applicantDetails.admittedThroughName"></nested:write>
														</td>
														<td width="100" height="10" class="row-odd">
															<div align="left"><i>Blood Group</i></div>
														</td>
														<td width="250" height="10" class="row-even" align="left">
															<bean:write name="studentEditForm" property="applicantDetails.personalData.bloodGroup" />
														</td>
													</tr>
													<tr>
														<td width="100" height="10" class="row-odd">
															<div align="left">
																<i>Height</i>
															</div>
														</td>
														<td width="500" height="10" class="row-even" align="left">
															<nested:write property="applicantDetails.personalData.height"></nested:write>
														</td>
														<td width="100" height="10" class="row-odd">
															<div align="left"><i>Weight</i></div>
														</td>
														<td width="250" height="10" class="row-even" align="left">
															<bean:write name="studentEditForm" property="applicantDetails.personalData.weight" />
														</td>
													</tr>
													<tr>
														<td width="100" height="10" class="row-odd">
															<div align="left">
																<i>Eligible for fee concession</i>
															</div>
														</td>
														<td width="500" height="10" class="row-even" align="left">
															<nested:write property="applicantDetails.ligPrint"></nested:write>
														</td>
														<td width="100" height="10" class="row-odd">
															<div align="left"><i>Mother Tongue</i></div>
														</td>
														<td width="250" height="10" class="row-even" align="left">
															<bean:write name="studentEditForm" property="applicantDetails.personalData.motherTongue" />
														</td>
													</tr>
											</table>
											</td>
										</tr>
										<tr>
											<td width="200" class="heading">
												<table width="640" border="1" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
													<tr>
														<td width="106" height="10" class="row-odd" valign="top">
															<div align="center"><i><bean:message key="admissionForm.studentinfo.dob.label" /></i></div>
															<div align="center"><nested:write property="applicantDetails.personalData.dob"></nested:write></div>
														</td>
														<td width="106" height="10" class="row-odd" valign="top">
															<div align="center"><i><bean:message key="admissionForm.studentinfo.birthplace.label" /></i></div>
															<div align="center"><nested:write property="applicantDetails.personalData.birthPlace"></nested:write></div>
														</td>
														<td width="106" height="10" class="row-odd" valign="top">
															<div align="center">
																<i><bean:message key="admissionForm.studentinfo.nationality.label" /></i>
															</div>
															<div align="center"><bean:write name="studentEditForm" property="applicantDetails.personalData.citizenship" /></div>
														</td>
														<td width="106" height="10" class="row-odd" valign="top">
															<div align="center"><i><bean:message key="admissionForm.studentinfo.religion.label" /></i></div>
															<div align="center"><bean:write name="studentEditForm" property="applicantDetails.personalData.religionName" /></div>
														</td>
														
														<td width="110" height="10" class="row-odd" valign="top">
															<div align="center"><i><bean:message key="admissionForm.studentinfo.castcatg.label" /></i></div>
															<div align="center"><bean:write name="studentEditForm" property="applicantDetails.personalData.casteCategory" /></div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td class="heading" align="center">&nbsp;<B><bean:message key="knowledgepro.educational.qualification.col" /></B></td>
										</tr>
										<tr>
											<td width="640" class="heading">
												<table width="640" border="1" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
													<tr>
														<td width="91" height="10" class="row-odd" valign="top">
															<div align="center"><i><bean:message key="knowledgepro.qualification.col" /></i></div>
														</td>
														<td width="111" height="10" class="row-odd" valign="top">
															<div align="center"><i>Board/Uni</i></div>
														</td>
														<td width="111" height="10" class="row-odd" valign="top">
															<div align="center"><i><bean:message key="knowledgepro.institution.col" /></i></div>
														</td>
														<td width="91" height="10" class="row-odd" valign="top">
															<div align="center"><i><bean:message key="knowledgepro.admin.state" /></i></div>
														</td>
														<td width="71" height="10" class="row-odd" valign="top">
															<div align="center"><i>%</i></div>
														</td>
														<td width="91" height="10" class="row-odd" valign="top">
															<div align="center"><i>Y/M of Pass</i></div>
														</td>
														<td width="75" height="10" class="row-odd" valign="top">
															<div align="center"><i>Attmp</i></div>
														</td>
													</tr>
													<nested:iterate property="applicantDetails.ednQualificationList" id="ednQualList" indexId="count">
													<tr>
														<td width="91" height="10" class="row-even" align="center">&nbsp;
															<c:choose>
																<c:when test="${ednQualList.selectedExamName!= null && ednQualList.selectedExamName!= ''}">
																	<bean:write name="ednQualList" property="selectedExamName" />
																</c:when>
																<c:otherwise>
																	<bean:write name="ednQualList" property="docName" />
																</c:otherwise>
															</c:choose>
														</td>
														<td width="111" height="10" class="row-even" align="center">&nbsp;
															<bean:write name="ednQualList" property="universityName" />
														</td>
														<td width="111" height="10" class="row-even" align="center">
															
															<logic:notEmpty name="ednQualList" property="institutionName">
															<bean:define id="inst" name="ednQualList" property="institutionName"></bean:define>
															<%String institute = inst.toString();
																if(institute.length() > 20){
																	institute = institute.substring(0, 19);
																}
															%>
															<%=institute%>
															</logic:notEmpty>
														</td>
														<td width="91" height="10" class="row-even" align="center">
															<bean:write name="ednQualList" property="stateName" />
														</td>
														<td width="71" height="10" class="row-even" align="center">
															<bean:write name="ednQualList" property="percentage" />
														</td>
														<td width="91" height="10" class="row-even" align="center">
															<bean:write name="ednQualList" property="yearPassing" />, 
															<logic:equal name="ednQualList" property="monthPassing" value="1">
																<bean:message key="knowledgepro.admin.month.january" />
															</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="2">
																<bean:message key="knowledgepro.admin.month.february" />
															</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="3">
																<bean:message key="knowledgepro.admin.month.march" />
															</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="4">
																<bean:message key="knowledgepro.admin.month.april" />
															</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="5">
																<bean:message key="knowledgepro.admin.month.may" />
															</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="6">
																<bean:message key="knowledgepro.admin.month.june" />
															</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="7">
																<bean:message key="knowledgepro.admin.month.july" />
															</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="8">
																<bean:message key="knowledgepro.admin.month.august" />
															</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="9">
																<bean:message key="knowledgepro.admin.month.september" />
															</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="10">
																<bean:message key="knowledgepro.admin.month.october" />
															</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="11">
																<bean:message key="knowledgepro.admin.month.november" />
															</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="12">
																<bean:message key="knowledgepro.admin.month.december" />
															</logic:equal>
														</td>
														<td width="75" height="10" class="row-even" valign="top">
															<div align="center"><bean:write name="ednQualList" property="noOfAttempts" /></div>
														</td>
													</tr>
													</nested:iterate>
													
													<logic:notEmpty  property="applicantDetails.examCenterName" name="studentEditForm">
													<tr>
														<td height="10" class="row-even" align="left" colspan="3">
															&nbsp;Center Preference: &nbsp;&nbsp; <bean:write
															name="studentEditForm"
															property="applicantDetails.examCenterName" />
														</td>
														<logic:notEmpty  property="applicantDetails.interviewDate" name="studentEditForm">
														<td  height="10" class="row-even" align="left" colspan="4">
															&nbsp;Selection Process Date: &nbsp;&nbsp; <bean:write name="studentEditForm" property="applicantDetails.interviewDate"/>
														</td>
														</logic:notEmpty>
													</tr>
													</logic:notEmpty>
												</table>
											</td>
										</tr><!--
										
										<nested:iterate property="applicantDetails.ednQualificationList" id="ednQualList" indexId="count">
										<logic:equal value="true" name="ednQualList" property="lastExam">
										<logic:equal value="true" property="semesterWise" name="ednQualList">
										<logic:equal value="false" property="consolidated" name="ednQualList">
											<tr>
												<td class="heading" align="center">&nbsp;<B><bean:message
													key="knowledgepro.qualifying.markdetails.col" /></B>
												</td>
											</tr>
											<tr>
												<td width="640" class="heading">
													<table width="640" border="1" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
														<tr>
															<td width="160" height="10" class="row-odd"
																valign="middle">
															</td>
															<td width="160" height="10" class="row-odd"
																valign="middle">
															</td>
															<td width="160" height="10" class="row-even"
																align="center" valign="top">&nbsp; <i><bean:message
																key="knowledgepro.totalmarks.col" /></i>
															</td>
															<td width="160" height="10" class="row-odd" align="center"
																valign="top"><i><bean:message
																key="knowledgepro.obtainedmarks.col" /></i>
															</td>
														</tr>
														<logic:notEmpty name="ednQualList" property="semesterList">
														<nested:iterate property="semesters" id="semId" type="com.kp.cms.to.admin.ApplicantMarkDetailsTO" name="ednQualList">
															<tr>
																<td colspan="2">
																	<div align="center"><nested:write
																		property="semesterName" name="semId"></nested:write></div>
																</td>
																<td>
																	<div align="center"><nested:write
																		property="maxMarks" name="semId"></nested:write></div>
																</td>
																<td>
																	<div align="center"><nested:write
																	property="marksObtained" name="semId"></nested:write></div>
																</td>
															</tr>
														</nested:iterate>
														</logic:notEmpty>
													</table>
												</td>
											</tr>
										</logic:equal>
										</logic:equal>
										<logic:equal value="false" property="consolidated" name="ednQualList">
										<logic:equal value="false" property="semesterWise" name="ednQualList">
											<tr>
												<td class="heading" align="center">&nbsp;<B><bean:message
													key="knowledgepro.qualifying.markdetails.col" /></B></td>
											</tr>
											<tr>
												<td width="640" class="heading">
												<table width="640" border="1" cellpadding="0"
													cellspacing="0" bordercolor="#E0DFDB">
													<tr>
														<td width="160" height="10" class="row-odd"
															valign="middle"></td>
														<td width="160" height="10" class="row-odd"
															valign="middle"></td>
														<td width="160" height="10" class="row-even"
															align="center" valign="top">&nbsp; <i><bean:message
															key="knowledgepro.totalmarks.col" /></i></td>
														<td width="160" height="10" class="row-odd" align="center"
															valign="top"><i><bean:message
															key="knowledgepro.obtainedmarks.col" /></i></td>
													</tr>


													<%
														for (int i = 1; i <= CMSConstants.MAX_CANDIDATE_SUBJECTS; i++) {
																					String propertyName = "detailmark.subject"
																							+ i;
																					String totalMarkprop = "detailmark.subject"
																							+ i + "TotalMarks";
																					String obtainMarkprop = "detailmark.subject"
																							+ i + "ObtainedMarks";
													%>
													<tr>

														<td colspan="2"><nested:write
															property="<%=propertyName %>"></nested:write></td>
														<td align="center"><nested:write
															property="<%=totalMarkprop %>" name="ednQualList"></nested:write></td>
														<td align="center"><nested:write
															property="<%=obtainMarkprop %>" name="ednQualList"></nested:write></td>

													</tr>
													<%
														}
													%>




												</table>
												</td>
											</tr>
										</logic:equal>
										</logic:equal>
										</logic:equal>
										</nested:iterate>
										
										--><logic:equal value="true" property="workExpNeeded" name="studentEditForm">
											<tr height="25">
											<td class="heading">
												Work Experience In No of Years:<bean:write name="studentEditForm" property="totalYearOfExp"/>
											</td>
											</tr>
										</logic:equal>
										<tr>
											<td class="heading" align="center"><B><bean:message
												key="knowledgepro.other.parentdetails.col" /></B></td>
										</tr>
										<tr>
											<td width="640" class="heading">
												<table width="640" border="1" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
													<tr>
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left"><i><bean:message
															key="knowledgepro.relationship.col" /></i></div>
														</td>
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left"><i><bean:message
																key="admissionForm.studentedit.name.label" /></i></div>
														</td>
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left"><i><bean:message
																key="admissionForm.parentinfo.occupation.label" /></i></div>
														</td>
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left"><i><bean:message
																key="knowledgepro.admission.income" /></i></div>
														</td>
													</tr>
													<tr>
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left"><i><bean:message
																key="knowledgepro.father" /></i></div>
														</td>
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left">
															<nested:write property="applicantDetails.titleOfFather"></nested:write>.
															<nested:write property="applicantDetails.personalData.fatherName"></nested:write></div>
														</td>
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left">&nbsp;<bean:write	
															name="studentEditForm"
																property="applicantDetails.personalData.fatherOccupation" /></div>
														</td>
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left">&nbsp;<bean:write
																name="studentEditForm"
																property="applicantDetails.personalData.fatherIncome" /></div>
														</td>
													</tr>
													<tr>
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left"><i><bean:message
															key="knowledgepro.mother" /></i></div>
														</td>	
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left">
															<nested:write property="applicantDetails.titleOfMother"></nested:write>.
															<nested:write property="applicantDetails.personalData.motherName"></nested:write></div>
														</td>
														
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left">&nbsp;<bean:write
																name="studentEditForm"
																property="applicantDetails.personalData.motherOccupation" /></div>
														</td>
														<td width="160" height="10" class="row-odd" valign="middle">
															<div align="left">&nbsp;<bean:write
																name="studentEditForm"
																property="applicantDetails.personalData.motherIncome" /></div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td class="heading">
												<table width="640" border="1" align="left" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
													<tr>
														<td width="100" height="15" class="row-odd">
															<div align="left"><i><bean:message
															key="admissionForm.studentinfo.currAddr.label" /></i></div>
														</td>
														<td width="540" height="15" class="row-even" align="left">&nbsp;
															<nested:write
																property="applicantDetails.personalData.currentAddressLine1"></nested:write>,
															<nested:write
																property="applicantDetails.personalData.currentAddressLine2"></nested:write>,
															<nested:write
																property="applicantDetails.personalData.currentCityName"></nested:write>,
															<bean:write name="studentEditForm"
																property="applicantDetails.personalData.currentStateName" />,
															<bean:write name="studentEditForm"
																property="applicantDetails.personalData.currentCountryName" />,
															<nested:write	
																property="applicantDetails.personalData.currentAddressZipCode"></nested:write>
														</td>
													</tr>
													<tr>
														<td width="100" height="15" class="row-odd">
															<div align="left"><i><bean:message
															key="admissionForm.studentinfo.permAddr.label" /></i></div>
														</td>	
														<td width="540" height="15" class="row-even" align="left">&nbsp;
															<nested:write
																property="applicantDetails.personalData.permanentAddressLine1"></nested:write>,
															<nested:write
																property="applicantDetails.personalData.permanentAddressLine2"></nested:write>,
															<nested:write
																property="applicantDetails.personalData.permanentCityName"></nested:write>,
															<bean:write name="studentEditForm"
																property="applicantDetails.personalData.permanentStateName" />,
															<bean:write name="studentEditForm"
																property="applicantDetails.personalData.permanentCountryName" />,
															<nested:write
																property="applicantDetails.personalData.permanentAddressZipCode"></nested:write>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td class="heading">
												<table width="640" border="1" align="left" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
													<tr>
														<td width="160" height="10" class="row-odd">
															<div align="left"><i><bean:message
																key="knowledgepro.phone.number.col" /></i></div>
														</td>
														<td width="160" height="10" class="row-odd">
															<div align="left"><bean:write name="studentEditForm"
																property="applicantDetails.personalData.parentPh3" /></div>
														</td>
														<td width="160" height="10" class="row-odd">
															<div align="left"><i><bean:message
																key="knowledgepro.mobile.number.col" /></i></div>
														</td>
														<td width="160" height="10" class="row-odd">
															<div align="left"><bean:write name="studentEditForm"
																property="applicantDetails.personalData.parentMob2" /></div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										
										
									</table>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
		<p style="page-break-after:always;"> </p>	
		<table width="600" border="1" cellpadding="0" cellspacing="0" bordercolor="#E0DFDB">
			
		</table>
		
	</html:form>
	</body>
	<script type="text/javascript">
		window.print();
	</script>
