<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function cancelButton() {
	document.location.href = "HostelStudentInfo.do?method=getSearchedHostelStudents";
}
</script>
<html:form action="/HostelStudentInfo.do">
	<html:hidden property="method" styleId="method" value="getRemarksPage" />
	<table width="98%" height="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs">Hostel <span
				class="Bredcrumbs">&gt;&gt;Hostel Student Detail View&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td height="1451">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Hostel Student
					Detail View </strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">
					<table width="100%" height="100%" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;Student
							Details</td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" align="left" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="22" align="left" valign="top">
									<table width="100%" height="22" border="0" cellpadding="0">
										<tr class="row-white">
											<td colspan="4" width="50%" height="101" align="center" class="row-even"><img
												src='<%=request.getContextPath()%>/DisplinaryPhotoServlet'
												height="150Px" width="150Px" /></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="22"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td width="100%" align="left" background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="100%" height="28" border="0" align="center"
								cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" height="28" background="images/left.gif"></td>
									<td width="100%" height="28" valign="middle">
									<table width="100%" height="26" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-even">
											<td width="50%" class="row-odd">
											<div align="right">Course:</div>
											<div align="right"></div>
											</td>
											<td width="50%" height="24" align="left" class="row-even">&nbsp;<span
												class="style7"> <span class="style24"><bean:write
												name="hostelStudentDetailsForm" property="course" /></span></span></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="28"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td width="100%" background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
						
						<tr>
							<td align="center" valign="top" class="heading">
							<table width="100%" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="25" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Hostel Name:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style24"><bean:write
												name="hostelStudentDetailsForm" property="hostelName" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Block:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style24"><bean:write
												name="hostelStudentDetailsForm" property="block" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Unit:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style24"><bean:write
												name="hostelStudentDetailsForm" property="unit" /> </span></td>
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
							<td align="center" valign="top" class="heading">
							<table width="100%" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="25" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="50%" height="35" align="right" class="row-odd">Room No:</td>
											<td width="50%" height="35"  align="left" class="row-even">
											<p class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="roomNo" /></p>
											</td>
										</tr>
										<tr>
											<td width="50%" height="35" align="right" class="row-odd">Floor:</td>
											<td width="50%" height="35"  align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="floorNo" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" align="right" class="row-odd">Bed:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="bedNo" /></span></td>
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
							<td colspan="2" class="heading" align="left">&nbsp;Personal
							Data</td>
						</tr>

						<tr>
							<td align="center" valign="top" class="heading">
							<table width="100%" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="25" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="50%" height="33" class="row-odd">
											<div align="right">Name:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style24"><bean:write
												name="hostelStudentDetailsForm" property="name" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="25" class="row-odd">
											<div align="right">Date of Birth:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style24"><bean:write
												name="hostelStudentDetailsForm" property="dateOfBirth" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="25" class="row-odd">
											<div align="right">Gender:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style24"><bean:write
												name="hostelStudentDetailsForm" property="gender" /> </span></td>
										</tr>
										<tr>
											<td width="50%" height="25" class="row-odd">
											<div align="right">Email:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style24"><bean:write
												name="hostelStudentDetailsForm" property="eMail" /> </span></td>
										</tr>
										<tr>
											<td height="4" class="row-odd">
											<div align="right">Mobile Number :</div>
											</td>
											<td height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="mobNumber" /></span></td>
										</tr>
										<tr>
											<td height="4" class="row-odd">
											<div align="right">Phone Number :</div>
											</td>
											<td height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="phoneNo" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="7" class="row-odd">
											<div align="right">Nationality:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="nationality" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="8" align="right" class="row-odd">Date
											Of Admission:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="dateOfAddmission" /></span></td>
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
							<td align="center" valign="top" class="heading">
							<table width="100%" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="25" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="50%" height="3" align="right" class="row-odd">Current
											Address:</td>
											<td width="50%" height="53" align="left" class="row-even">
											<p class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="currentAddress" /></p>
											</td>
										</tr>
										<tr>
											<td width="50%" height="1" align="right" class="row-odd">Permanent
											Address:</td>
											<td width="50%" height="53" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="permanentAddress" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="0" align="right" class="row-odd">Passport
											Number:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="passportNumber" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="1" align="right" class="row-odd">Issueing
											Country:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="issueCountry" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="-2" align="right" class="row-odd">Valid
											up to:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="validUpTo" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="0" align="right" class="row-odd">Resident
											Permit No:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="resedentPermitNo" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="2" align="right" class="row-odd">Obtained
											from Commissioner of Police, Dated:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="obtainedDate" /></span></td>
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
											<td height="25" class="row-odd" width="10%">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td height="25" class="row-odd" width="10%" align="center"><bean:message
												key="knowledgepro.admission.qualification" /></td>
											<td class="row-odd">Exam Name</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.universityBoard" /></div>
											</td>
											<td class="row-odd" width="10%">
											<div align="center"><bean:message
												key="knowledgepro.admission.instituteName" /></div>
											</td>
											<td class="row-odd" width="10%">
											<div align="left"><bean:message
												key="admissionForm.education.State.label" /></div>
											</td>
											<td class="row-odd" width="10%" align="center"><bean:message
												key="knowledgepro.admission.passingYear" /></td>
											<td class="row-odd" align="center"><bean:message
												key="knowledgepro.applicationform.passingmonth" /></td>
											<td class="row-odd" width="10%">
											<div align="center"><bean:message
												key="knowledgepro.admission.marksObtained" /></div>
											</td>
											<td class="row-odd" width="10%">
											<div align="center"><bean:message
												key="knowledgepro.admission.maxMark" /></div>
											</td>
											<td class="row-odd" width="10%">
											<div align="center"><bean:message
												key="knowledgepro.admission.attempts" /></div>
											</td>
											<td class="row-odd" width="10%" align="center"><bean:message
												key="knowledgepro.applicationform.prevregno.label" /></td>
										</tr>
										<c:set var="temp" value="0" />
										<logic:notEmpty property="eduList" name="hostelStudentDetailsForm">
										<nested:iterate property="eduList" id="ednQualList"
											indexId="count">

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
															<nested:write property="selectedExamName" />
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
														<td width="22%" class="row-even">
														<div align="left"><bean:write name="ednQualList"
															property="stateName" /></div>
														</td>
														<td width="16%" class="row-even" align="center"><bean:write
															name="ednQualList" property="yearPassing" /></td>
														<td width="16%" class="row-even" align="center"><logic:equal
															name="ednQualList" property="monthPassing" value="1">JANUARY</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="2">FEBRUARY</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="3">MARCH</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="4">APRIL</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="5">MAY</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="6">JUNE</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="7">JULY</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="8">AUGUST</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="9">SEPTEMBER</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="10">OCTOBER</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="11">NOVEMBER</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="12">DECEMBER</logic:equal></td>
														<logic:equal value="true" property="semesterWise"
															name="ednQualList">
															<logic:equal value="false" property="consolidated"
																name="ednQualList">
																<td class="row-even" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getSemesterMarkDetails('<bean:write name="ednQualList" property="id"/>')"><bean:message
																	key="admissionForm.approveview.prereq.semmark.viewlink" /></a></div>
																</td>
															</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated"
															name="ednQualList">
															<logic:equal value="false" property="semesterWise"
																name="ednQualList">
																<td class="row-even" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getDetailsMark('<bean:write name="ednQualList" property="id"/>')"><bean:message
																	key="admissionForm.approveview.prereq.detmark.viewlink" /></a></div>
																</td>
															</logic:equal>
														</logic:equal>

														<logic:notEqual value="true" property="semesterWise"
															name="ednQualList">
															<logic:notEqual value="false" property="consolidated"
																name="ednQualList">
																<td class="row-even">
																<div align="center"><nested:write
																	property="marksObtained"></nested:write></div>
																</td>
																<td class="row-even">
																<div align="center"><nested:write
																	property="totalMarks"></nested:write></div>
																</td>
															</logic:notEqual>
														</logic:notEqual>

														<td width="9%" class="row-even">
														<div align="center"><bean:write name="ednQualList"
															property="noOfAttempts" /></div>
														</td>
														<td width="9%" class="row-even">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="lastExam">
															<nested:write property="previousRegNo" />
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
														<td height="25" class="row-white"><bean:write
															name="ednQualList" property="docName" /></td>
														<td class="row-white">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="examConfigured">
															<nested:write property="selectedExamName" />
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
														<td width="22%" class="row-even">
														<div align="left"><bean:write name="ednQualList"
															property="stateName" /></div>
														</td>
														<td class="row-white" align="center"><bean:write
															name="ednQualList" property="yearPassing" /></td>
														<td class="row-white"><logic:equal name="ednQualList"
															property="monthPassing" value="1">JANUARY</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="2">FEBRUARY</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="3">MARCH</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="4">APRIL</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="5">MAY</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="6">JUNE</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="7">JULY</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="8">AUGUST</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="9">SEPTEMBER</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="10">OCTOBER</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="11">NOVEMBER</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="12">DECEMBER</logic:equal>
														</td>

														<logic:equal value="true" property="semesterWise"
															name="ednQualList">
															<logic:equal value="false" property="consolidated"
																name="ednQualList">
																<td class="row-white" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getSemesterMarkDetails('<bean:write name="ednQualList" property="id"/>')"><bean:message
																	key="admissionForm.approveview.prereq.semmark.viewlink" /></a></div>
																</td>
															</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated"
															name="ednQualList">
															<logic:equal value="false" property="semesterWise"
																name="ednQualList">
																<td class="row-white" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getDetailsMark('<bean:write name="ednQualList" property="id"/>')"><bean:message
																	key="admissionForm.approveview.prereq.detmark.viewlink" /></a></div>
																</td>
															</logic:equal>
														</logic:equal>

														<logic:notEqual value="true" property="semesterWise"
															name="ednQualList">
															<logic:notEqual value="false" property="consolidated"
																name="ednQualList">
																<td class="row-white">
																<div align="center"><nested:write
																	property="marksObtained"></nested:write></div>
																</td>
																<td class="row-white">
																<div align="center"><nested:write
																	property="totalMarks"></nested:write></div>
																</td>
															</logic:notEqual>
														</logic:notEqual>

														<td class="row-white">
														<div align="center"><bean:write name="ednQualList"
															property="noOfAttempts" /></div>
														</td>
														<td class="row-white">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="lastExam">
															<nested:write property="previousRegNo" />
														</logic:equal></div>
														</td>
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</nested:iterate>
										</logic:notEmpty>
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
							<td colspan="2" class="heading" align="left">&nbsp;Parent
							Details</td>
						</tr>
						<tr>
							<td width="50%">
							<table width="100%" border="0" align="center" cellpadding="0"
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
											<td width="50%" height="35" class="row-odd">
											<div align="right">
											<div align="right">Father's Name:</div>
											</div>
											</td>
											<td width="55%" height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="fatherName" /></span></td>
										</tr>
										<tr class="row-odd">
											<td width="50%" height="35" class="row-odd">
											<div align="right">Education:</div>
											</td>
											<td width="55%" height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="fatherEducation" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Income Currency:</div>
											</td>
											<td height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="fatherIncomeCurrency" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Income:</div>
											</td>
											<td height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="fatherIncome" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Occupation:</div>
											</td>
											<td height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="fatherOccupaction" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="17" class="row-odd">
											<div align="right">e-mail:</div>
											</td>
											<td height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="fatheremail" /></span></td>
										</tr>
										<tr>
											<td height="4" class="row-odd">
											<div align="right">Phone/Mobile Number :</div>
											</td>
											<td height="35" align="left" class="row-even" align="left"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="fatherPhone" /></span></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" height="175"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td width="50%">
							<table width="100%" border="0" align="center" cellpadding="0"
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
											<td width="50%" height="35" class="row-odd">
											<div align="right">
											<div align="right">Mother's Name:</div>
											</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="motherName" /></span></td>
										</tr>
										<tr class="row-odd">
											<td width="50%" height="35" class="row-odd">
											<div align="right">Education:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="motherEducation" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Income Currency:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="motherincomeCurrency" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Income:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="motherIncome" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Occupation:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm"
												property="motheroccupaction" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="17" class="row-odd">
											<div align="right">e-mail:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="motherEmail" /></span></td>
										</tr>
										<tr>
											<td height="4" class="row-odd">
											<div align="right">Phone/Mobile Number :</div>
											</td>
											<td height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="hostelStudentDetailsForm" property="motherPhone" /></span></td>
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
	
		<td colspan="2" align="left"><span class="heading">&nbsp;Academic Details </span></td>
	</tr>
	<tr>
	
			<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" height="91" valign="top">
						<table width="100%" cellspacing="1" cellpadding="2" style="border: 1px solid black;" rules="all">
							<tr>
							<td width="150" height="20" align="center" class="row-odd">Class</td>
							<td width="300" height="20" align="center" class="row-odd">Subject</td>
							<td width="80" height="20" align="center" class="row-odd">Absent</td>
							<td width="80" height="20" class="row-odd"  align="center">Attendance%</td>
			<c:set var="found" value="0" />
			<%int examCount = 0; %>
			<logic:notEmpty name="hostelStudentDetailsForm"	property="listCourseDetails">
			<logic:iterate id="markHead" name="hostelStudentDetailsForm"
				property="listCourseDetails" indexId="count">
				<c:if test="${found == 0}">
					<logic:notEmpty name="markHead"
						property="examMarksEntryDetailsTOList">
						<logic:iterate id="markHead1" name="markHead"
							property="examMarksEntryDetailsTOList">
						<logic:notEmpty name="markHead1" property="examName">
							<td height="25" class="row-odd" width="10%">
							<table >
										
								<tr>
									<td>
									
									<div align="center"><bean:write name="markHead1"
										property="examName"/></div>
										
									<c:set var="found" value="1" /> <%examCount = examCount + 1; %>
									</td>
								</tr>
								<tr>
									<td width="50%" align="left">T</td>
									<td width="50%" align="right">P</td>
								</tr>
						
							</table>
							
							</td>
							</logic:notEmpty>
						</logic:iterate>
			
					</logic:notEmpty>
				</c:if>
			</logic:iterate>
			</logic:notEmpty>
				
			</tr>
			<tr>
			<logic:notEmpty name="hostelStudentDetailsForm" property="listCourseDetails">
				<logic:iterate id="details" name="hostelStudentDetailsForm"
				type="com.kp.cms.to.admission.CourseDetailsTO"
				property="listCourseDetails" indexId="count">
				
			<tr class="row-white">
					
				<td><bean:write property="className" name="details" /></td>
				<td><bean:write property="subject" name="details" /></td>
				<td>
				<logic:equal name="details" property="flag" value="false">
			<A
							HREF="javascript:winOpen('<bean:write name="details" property="subjectID" />',
			'<bean:write name="details" property="studentId" />',
			'<bean:write name="details" property="absent" />','<bean:write name="details" property="cocurricularLeave" />');">
				<bean:write name="details" property="absent" /></A>
			</logic:equal>
			<logic:equal name="details" property="flag" value="true">
			<A HREF="javascript:activityOpens('<bean:write name="details" property="subjectID" />',
			'<bean:write name="details" property="studentId" />','<bean:write name="details" property="absent" />',
			'<bean:write name="details" property="subject" />','<bean:write name="details" property="cocurricularLeave" />');">
			<bean:write name="details" property="absent" /></A>
			</logic:equal>
			</td>
				<td><bean:write property="attendence" name="details" /></td>
				<logic:notEmpty name="details"
					property="examMarksEntryDetailsTOList">
					<logic:iterate id="mark" name="details"
						property="examMarksEntryDetailsTOList">
					<td height="25" >
						<table width="100%">
							<tr>
								<td width="50%" align="left"><bean:write
									name="mark" property="theoryMarks" /></td>
								<td width="50%" align="right"><bean:write
									name="mark" property="practicalMarks" /></td>
							</tr>
						 </table>
					  </td>
					</logic:iterate>
				</logic:notEmpty>
				<logic:empty name="details"	property="examMarksEntryDetailsTOList">
					<%if(examCount > 0){
						for(int i=1;i<=examCount; i++){%>
							<td width="10%" height="25" align="center">&nbsp;</td>
					<%} } %>
				</logic:empty>
			</logic:iterate>
			</logic:notEmpty>
						
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
	
		<td colspan="2" align="left"><span class="heading">&nbsp;Hostel Attendance</span></td>
	</tr>				
	<tr>
	
			<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" height="91" valign="top">
						<table width="100%" cellspacing="1" cellpadding="2" style="border: 1px solid black;" rules="all">
							<tr>
							<td width="50%" height="20" align="center" class="row-odd">Morning Punch</td>
							<td width="50%" height="20" align="center" class="row-odd">Evening Punch</td>
     			<logic:notEmpty name="hostelStudentDetailsForm" property="hostelAttendanceList">
				<logic:iterate id="details" name="hostelStudentDetailsForm"
				type="com.kp.cms.to.hostel.HlAdmissionTo"
				property="hostelAttendanceList" indexId="count">
				
			<tr class="row-white">
				<td><bean:write property="morningPunch" name="details" /></td>
				<td><bean:write property="eveningPunch" name="details" /></td>
			</logic:iterate>
			</logic:notEmpty>
					</tr>
				</table>
				</td>
				<td background="images/right.gif" width="5" height="175"></td>
			</tr>
			<tr>
			    <td width="5" height="1" background="images/left.gif"></td>
				<td colspan="1" align="left"><span class="heading">&nbsp;Not Signed</span></td>
				<td background="images/right.gif" width="5" height="1"></td>
			</tr>
				<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" height="91" valign="top">
						<table width="100%" cellspacing="1" cellpadding="2" style="border: 1px solid black;" rules="all">
							<tr>
							<td width="50%" height="20" align="center" class="row-odd">Date</td>
							<td width="50%" height="20" align="center" class="row-odd">Session</td>
     			<logic:notEmpty name="hostelStudentDetailsForm" property="hostelAttendanceList">
				<logic:iterate id="details" name="hostelStudentDetailsForm"
				type="com.kp.cms.to.hostel.HlAdmissionTo"
				property="hostelAttendanceList" indexId="count">
				
			<tr class="row-white">
				<td><bean:write property="hostelDate" name="details" /></td>
				<td><bean:write property="hostelSession" name="details" /></td>
			</logic:iterate>
			</logic:notEmpty>
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
	
		<td colspan="2" align="left"><span class="heading">&nbsp;Holiday/Vacation</span></td>
	</tr>				
	<tr>
	
			<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" height="91" valign="top">
						<table width="100%" cellspacing="1" cellpadding="2" style="border: 1px solid black;" rules="all">
							<tr>
							<td width="33%" height="20" align="center" class="row-odd">Type</td>
							<td width="33%" height="20" align="center" class="row-odd">From Date</td>
							<td width="33%" height="20" align="center" class="row-odd">To Date</td>
     			<logic:notEmpty name="hostelStudentDetailsForm" property="hostelHolidayOrVacationList">
				<logic:iterate id="details" name="hostelStudentDetailsForm"
				type="com.kp.cms.to.hostel.HlAdmissionTo"
				property="hostelHolidayOrVacationList" indexId="count">
				
			<tr class="row-white">
				<td><bean:write property="holidayType" name="details" /></td>
				<td><bean:write property="leaveFrom" name="details" /></td>
				<td><bean:write property="leaveTo" name="details" /></td>
			</logic:iterate>
			</logic:notEmpty>
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
	
		<td colspan="2" align="left"><span class="heading">&nbsp;Hostel Leave Details </span></td>
	</tr>
	<tr>
	
			<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" height="91" valign="top">
						<table width="100%" cellspacing="1" cellpadding="2" style="border: 1px solid black;" rules="all">
							<tr>
							<td width="33%" height="20" align="center" class="row-odd">Leave Form</td>
							<td width="33%" height="20" align="center" class="row-odd">Leave To</td>
							<td width="33%" height="20" align="center" class="row-odd">Academic Year</td>
			<logic:notEmpty name="hostelStudentDetailsForm" property="listLeaveDetails">
				<logic:iterate id="details" name="hostelStudentDetailsForm"
				type="com.kp.cms.bo.admin.HlLeave"
				property="listLeaveDetails" indexId="count">
				
			<tr class="row-white">
					
				<td><bean:write property="leaveFrom" name="details" /></td>
				<td><bean:write property="leaveTo" name="details" /></td>
				
				<td><bean:write property="academicYear" name="details" /></td>
			</logic:iterate>
			</logic:notEmpty>
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
	
		<td colspan="2" align="left"><span class="heading">&nbsp;Disciplinary Action </span></td>
	</tr>
	<tr>
	
			<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" height="91" valign="top">
						<table width="100%" cellspacing="1" cellpadding="2" style="border: 1px solid black;" rules="all">
							<tr>
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
	
		<td colspan="2" align="left"><span class="heading">&nbsp;Fine Details </span></td>
	</tr>
	<tr>
	
			<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" height="91" valign="top">
						<table width="100%" cellspacing="1" cellpadding="2" style="border: 1px solid black;" rules="all">
							<tr>
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
	
		<td colspan="2" align="left"><span class="heading">&nbsp;Hostel Fee Details</span></td>
	</tr>
	<tr>
	
			<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" height="91" valign="top">
						<table width="100%" cellspacing="1" cellpadding="2" style="border: 1px solid black;" rules="all">
							<tr>
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




<!--	mehaboob......	-->	


<tr>
<td colspan="3">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>	
	<td colspan="3"><logic:notEmpty name="SEMLIST" scope="session">
	<br>
	<logic:equal value="false" name="hostelStudentDetailsForm" property="isCjc">
	<span class="heading"><br></br>&nbsp;Attendance and Internal Mark Details (Previous Semesters)</span>
	</logic:equal>
	
													
	
		<logic:iterate id="PreviousSem" name="SEMLIST" scope="session"	type="com.kp.cms.to.exam.ExamStudentPreviousClassTo">
			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<tr>					
                   			      <%int SemNo= PreviousSem.getSchemeNo();%>
									<td width="5%" height="21" align="left"><img src="images/bullet_img.gif" width="14" height="9" /></td>
									<td height="21" class="navmenu">
									<div class="<%= SemNo%>">
									<a href="#"	class="navmenu" onclick="getAttendance(<%= PreviousSem.getSchemeNo()%>,<%= PreviousSem.getClassId() %>); return false;">
															Class Name- <%= PreviousSem.getClassName() %> </a></div></td>
				</tr>
			</table>
			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
									
				<tr>					
									<td height="21" colspan="3">
									<div id="attendance_s<%= PreviousSem.getSchemeNo() %>_<%= PreviousSem.getClassId() %>"></div>
									</td>
					</tr>
				</table>
	</logic:iterate>
</logic:notEmpty>
</td>

</tr>



										<!-- Code By Mary for links of previous semesters ends -->
										<!-- Code By Mary for Exam Marks starts -->



					

						<tr>
							<td colspan="2" align="left"><span class="heading">&nbsp;Detention/Discontinue
							Details </span></td>
						</tr>


						<tr>
							<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="91" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="15%" height="25" align="center" class="row-odd"></td>
											<td width="25%" height="25" align="center" class="row-odd">Detention</td>
											<td width="25%" align="center" class="row-odd">Rejoin</td>
											<td width="25%" align="center" class="row-odd">Discontinued
											</td>
										</tr>
										<tr>
											<td width="15%" height="25" align="center" class="row-odd">Date</td>
											<td width="25%" height="25" align="center" class="row-even">
											<bean:write name="hostelStudentDetailsForm"
												property="detentionDate" /></td>
											<td width="25%" height="25" align="center" class="row-even">
											<bean:write name="hostelStudentDetailsForm"
												property="rejoinDate" /></td>
											<td width="25%" height="25" align="center" class="row-even">
											<bean:write name="hostelStudentDetailsForm"
												property="discontinuedDate" /></td>
										</tr>
										<tr>
											<td width="15%" height="25" align="center" class="row-odd">Reason</td>
											<td width="25%" height="25" align="center" class="row-even">
											<bean:write name="hostelStudentDetailsForm"
												property="detentionReason" /></td>
											<td width="25%" height="25" align="center" class="row-even">
											<bean:write name="hostelStudentDetailsForm"
												property="rejoinReason" /></td>
											<td width="25%" height="25" align="center" class="row-even">
											<bean:write name="hostelStudentDetailsForm"
												property="discontinuedReason" /></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<%--  <tr>
                                <td background="images/right.gif" width="5" height="5"></td>
                              </tr>--%>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
                       <tr><td>&nbsp;</td></tr>
						<tr>
							<td colspan="2" align="left"><span class="heading">&nbsp;Remarks</span></td>
						</tr>
						 <tr><td>&nbsp;</td></tr>
						<tr>
							<td width="50%">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
							<td colspan="2" align="left"><span class="heading">&nbsp;Remarks From University</span></td>
						</tr>
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="91" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="34%" height="25" align="center" class="row-odd">Date</td>
											<td width="33%" align="center" class="row-odd">Details</td>
											<td width="33%" align="center" class="row-odd">Entered
											By</td>
										</tr>
										<logic:notEmpty name="hostelStudentDetailsForm" property="listRemarcks">
										<logic:iterate id="details" name="hostelStudentDetailsForm"
											type="com.kp.cms.to.admission.RemarcksTO"
											property="listRemarcks" indexId="count">
											<c:choose>
												<c:when test="${count%2==0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td width="136"><bean:write property="date"
												name="details" /></td>
											<td width="161"><bean:write property="details"
												name="details" /></td>
											<td width="113"><bean:write property="enteredBy"
												name="details" /></td>

										</logic:iterate>
										</logic:notEmpty>
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
							<td width="50%">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
							<td colspan="2" align="left"><span class="heading">&nbsp;Remarks From Hostel</span></td>
						</tr>
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="91" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="34%" height="25" align="center" class="row-odd">Date</td>
											<td width="33%" align="center" class="row-odd">Details</td>
											<td width="33%" align="center" class="row-odd">Entered
											By</td>
										</tr>
										<logic:notEmpty name="hostelStudentDetailsForm" property="listRemarcks">
										<logic:iterate id="details" name="hostelStudentDetailsForm"
											type="com.kp.cms.to.admission.RemarcksTO"
											property="listRemarcks" indexId="count">
											<c:choose>
												<c:when test="${count%2==0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td width="136"><bean:write property="date"
												name="details" /></td>
											<td width="161"><bean:write property="details"
												name="details" /></td>
											<td width="113"><bean:write property="enteredBy"
												name="details" /></td>

										</logic:iterate>
										</logic:notEmpty>
										
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
							<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="50%" height="45">
									<div align="right"><c:if
										test="${hostelStudentDetailsForm.isAddRemarks=='1'}">
										<input name="Submit" type="submit" class="formbutton"
											value="Add Remarks">
									</c:if></div>
									</td>
									<td width="50%" height="45" align="left" ></td>
									<td width="50%" height="45" align="left" ><input
										type="button" name="" value="Cancel" onClick="cancelButton()"
										class="formbutton" align="bottom"></td>
								</tr>
							</table>
							</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					</table>
					</td>
				</tr>
			</table>
</div>
			</td>
			 <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
		</tr>
<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
					<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

	</table>

</td>
</tr>
</table>

</html:form>