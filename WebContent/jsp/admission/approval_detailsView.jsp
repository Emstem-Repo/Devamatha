<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<SCRIPT type="text/javascript">
	function downloadFile(documentId) {
		document.location.href = "DocumentDownloadAction.do?documentId="
				+ documentId;
	}
	function getSemesterMarkDetails(qualId) {
		var url  = "admissionFormSubmit.do?method=viewSemesterMarkPage&editcountID="+qualId;
    	myRef = window.open(url,"ViewSemesterMarkDetails","left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
   }
	function getDetailsMark(qualId) {
		var url  = "admissionFormSubmit.do?method=viewDetailMarkPage&editcountID="+qualId;
    	myRef = window.open(url,"ViewDetailsMark","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
   }
	function detailLateralSubmit()
	{
		var url  = "admissionFormSubmit.do?method=viewLateralEntryPage";
    	myRef = window.open(url,"ViewLateralDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
	function detailTransferSubmit()
	{
		var url  = "admissionFormSubmit.do?method=viewTransferEntryPage";
    	myRef = window.open(url,"ViewTransferDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
	}
</SCRIPT>

<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<html:form action="/admissionFormSubmit" method="POST">
	<html:hidden property="method" value="updateApproval" />
	<html:hidden property="formName" value="admissionFormForm" />
	<html:hidden property="pageType" value="16" />
	<input type="hidden" name="applicationId"
		value='<bean:write name="admissionFormForm" property="applicantDetails.applicationId" />' />
	<input type="hidden" name="courseId" value='<bean:write	name="admissionFormForm" property="applicantDetails.course.id" />' />
	<div style="overflow: auto; width: 800px;">
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="admissionForm.approveview.main.label" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message key="admissionForm.approveview.main.label" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
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
												name="admissionFormForm"
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
									<td height="22" valign="top">
									<table width="100%" height="22" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											

											<td colspan="3" height="20" class="row-even" align="right"><img src='<%=request.getContextPath()%>/PhotoServlet'  height="150Px" width="150Px" /></td>
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
											<td width="168" height="23" class="row-even" align="left">&nbsp;
											<bean:write
												name="admissionFormForm"
												property="applicantDetails.course.programTypeCode" />
											</td>
											<td width="99" height="23" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.program" />:</div>
											</td>
											<td width="188" height="23" class="row-even" align="left">&nbsp;
											<bean:write
												name="admissionFormForm"
												property="applicantDetails.course.programCode" />
											</td>
											<td width="121" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.course" />:</div>
											</td>
											<td width="208" class="row-even" align="left">&nbsp;
											<bean:write
												name="admissionFormForm"
												property="applicantDetails.course.code" />	
											</td>
										</tr>
										<tr class="row-even">
											<td height="24" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.application.challan.label" /></div>
											</td>
											<td height="24" class="row-white" align="left">&nbsp;
												<nested:write property="applicantDetails.challanRefNo"></nested:write>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.journalNo" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;
											<nested:write property="applicantDetails.journalNo"></nested:write>
											</td>
											<td height="24" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.application.date.label" /></div>
											</td>
											<td height="24" class="row-white" align="left">&nbsp;
											<nested:write property="applicantDetails.challanDate"></nested:write>
 					
											</td>
										</tr>
										<tr class="row-even">
											<td height="19" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.amount" /></div>
											</td>
											<td height="19" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.amount"></nested:write>
											</td>
											<td height="19" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.branchCode"/></div></td>
											<td height="19"><nested:write property="applicantDetails.bankBranch"></nested:write></td>
											<td class="row-odd"><div align="right"><bean:message key="admissionForm.studentedit.admissiondate.label"/></div></td>
											<td height="19"><div align="left">&nbsp;<nested:write property="applicantDetails.admissionDate"></nested:write></div></td>
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
											<div align="right"><bean:message
												key="knowledgepro.admission.candidateName" />:</div>
											</td>
											<td width="55%" height="25" class="row-even" align="left">
											<nested:write property="applicantDetails.personalData.firstName"></nested:write>&nbsp;
				                            <nested:write property="applicantDetails.personalData.middleName"></nested:write>&nbsp;
				                            <nested:write property="applicantDetails.personalData.lastName"></nested:write>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.dob.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">
											<nested:write property="applicantDetails.personalData.dob"></nested:write>
					                             
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthplace.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">
												<nested:write property="applicantDetails.personalData.birthPlace"></nested:write>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthcountry.label" /></div>
											</td>
											<td height="35" class="row-even" align="left">
												<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.countryOfBirth" />
											</td>
										</tr>
										<tr>
											<td height="22" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthstate.label" /></div>
											</td>
											<td height="22" class="row-even" align="left">
												&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.stateOfBirth" />
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.nationality.label" /></div>
											</td>
											<td height="35" class="row-even" align="left">
												<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.citizenship" />
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
											<td width="56%" height="20" class="row-even" align="left">
												&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.residentCategoryName" />
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.religion.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.religionName" />
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.subreligion.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.subregligionName" />
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.castcatg.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.casteCategory" />
											</td>
										</tr>
										<tr>
											<td height="17" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.belongsto.label" /></div>
											</td>
									<td height="17" class="row-even" align="left">
											<logic:equal name="admissionFormForm" property="applicantDetails.personalData.areaType" value='R'><bean:message key="admissionForm.studentinfo.belongsto.rural.text"/></logic:equal>
											<logic:equal name="admissionFormForm" property="applicantDetails.personalData.areaType" value='U'><bean:message key="admissionForm.studentinfo.belongsto.urban.text"/></logic:equal></td>
										</tr>
										<tr>
											<td height="17" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.gender" />:</div>
											</td>
											
								<td height="17" class="row-even" align="left"><logic:equal name="admissionFormForm" property="applicantDetails.personalData.gender" value="MALE"><bean:message key="admissionForm.studentinfo.sex.male.text"/></logic:equal>
								<logic:equal name="admissionFormForm" property="applicantDetails.personalData.gender" value="FEMALE"><bean:message key="admissionForm.studentinfo.sex.female.text"/></logic:equal></td>
										</tr>
										<tr><%String dynaStyle3="display:none;"; %>
												<logic:equal value="true" property="applicantDetails.personalData.sportsPerson" name="admissionFormForm">
												<%dynaStyle3="display:block;"; %>
											</logic:equal>
                           					<td height="17" class="row-odd" width="40%"><div align="right"><bean:message key="admissionForm.edit.sportsperson.label"/></div></td>
                           					<td height="17" class="row-even" align="left"><logic:equal name="admissionFormForm" property="applicantDetails.personalData.sportsPerson" value="true"><bean:message key="knowledgepro.applicationform.yes.label"/></logic:equal>
											<logic:equal name="admissionFormForm" property="applicantDetails.personalData.sportsPerson" value="false"><bean:message key="knowledgepro.applicationform.No.label"/></logic:equal>
											<div id="sports_description" style="<%=dynaStyle3 %>">
												<nested:write property="applicantDetails.personalData.sportsDescription"></nested:write>
											</div></td>
                        				</tr>
 										<tr><%String dynaStyle4="display:none;"; %>
											<logic:equal value="true" property="applicantDetails.personalData.handicapped" name="admissionFormForm">
											<%dynaStyle4="display:block;"; %>
											</logic:equal>
                           					<td height="17" class="row-odd" width="40%"><div align="right"><bean:message key="admissionForm.edit.handicapped.label"/></div></td>
                           					<td height="17" class="row-even" align="left"><logic:equal name="admissionFormForm" property="applicantDetails.personalData.handicapped" value="true"><bean:message key="knowledgepro.applicationform.yes.label"/></logic:equal>
											<logic:equal name="admissionFormForm" property="applicantDetails.personalData.handicapped" value="false"><bean:message key="knowledgepro.applicationform.No.label"/></logic:equal>
											<div id="handicapped_description" style="<%=dynaStyle4 %>">
												<nested:write property="applicantDetails.personalData.hadnicappedDescription"></nested:write>
											</div>
											</td>
                        				</tr>
                        				<logic:equal  name="admissionFormForm" property="viewextradetails" value="true">
                        				 <tr>
						 
								
					        <td height="17" class="row-odd" width="40%"><div align="right">Holder of NCC  Certificate</div></td>
                           <td height="17" class="row-even" align="left"><logic:equal name="admissionFormForm" property="applicantDetails.personalData.ncccertificate" value="true"><bean:message key="knowledgepro.applicationform.yes.label"/></logic:equal>
											<logic:equal name="admissionFormForm" property="applicantDetails.personalData.ncccertificate" value="false"><bean:message key="knowledgepro.applicationform.No.label"/></logic:equal>
											<logic:equal value="true" property="applicantDetails.personalData.ncccertificate" name="admissionFormForm"><div>grade:
												<nested:write property="applicantDetails.personalData.nccgrades"></nested:write></div>
												</logic:equal>
												</td>
												
						

                        </tr>
		
                        				 <tr>
                           					<td height="17" class="row-odd" width="40%"><div align="right">Holder of NSS Certificate</div></td>
                           					<td height="17" class="row-even" align="left"><logic:equal name="admissionFormForm" property="applicantDetails.personalData.nsscertificate" value="true" ><bean:message key="knowledgepro.applicationform.yes.label"/></logic:equal>
											<logic:equal name="admissionFormForm" property="applicantDetails.personalData.nsscertificate" value="false" ><bean:message key="knowledgepro.applicationform.No.label"/></logic:equal>
											
											
											</td>
                        				</tr>
                        				 <tr>
                           					<td height="17" class="row-odd" width="40%"><div align="right">If an ex-service-man or Widow or child of a  jawan</div></td>
                           					<td height="17" class="row-even" align="left"><logic:equal  name="admissionFormForm" property="applicantDetails.personalData.exservice" value="true" ><bean:message key="knowledgepro.applicationform.yes.label"/></logic:equal>
											<logic:equal  name="admissionFormForm"  property="applicantDetails.personalData.exservice" value="false" ><bean:message key="knowledgepro.applicationform.No.label"/></logic:equal>
												
											
											</td>
                        				</tr>
                        				</logic:equal>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.bloodgroup.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
													<nested:write property="applicantDetails.personalData.bloodGroup"></nested:write>

											</td>
										</tr>
										<logic:equal value="true" property="displaySecondLanguage" name="admissionFormForm">
										 <tr >
				                          <td height="20" class="row-odd" width="40%"><div align="right"><bean:message key="knowledgepro.applicationform.secLang.label"/></div></td>
					                      <td height="20" class="row-even" align="left">
				                          <nested:write property="applicantDetails.personalData.secondLanguage"></nested:write>
										 </td>
				                        </tr>
										</logic:equal>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
											<nested:write property="applicantDetails.personalData.phNo1"></nested:write>-
                                 			<nested:write property="applicantDetails.personalData.phNo2"></nested:write>-
                                 			<nested:write property="applicantDetails.personalData.phNo3"></nested:write>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
											<nested:write property="applicantDetails.personalData.mobileNo1"></nested:write>-
			                                <nested:write property="applicantDetails.personalData.mobileNo2"></nested:write>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
												<nested:write property="applicantDetails.personalData.email"></nested:write> <br />
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
				<logic:equal value="true" property="displayExtraDetails" name="admissionFormForm">		
				<tr>
                  <td colspan="2" class="heading">&nbsp;<bean:message key="knowledgepro.applicationform.extradetails.label"/></td>
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
                      <td valign="top">
						
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
	                          <tr class="row-white">
	                            <logic:equal value="true" property="displayMotherTongue" name="admissionFormForm">
								<td height="23" class="row-even"><div align="center"><bean:message key="knowledgepro.applicationform.mothertongue.label"/></div></td>
	                            <td height="23" class="row-even" align="left">
									<nested:write property="applicantDetails.personalData.motherTongue"></nested:write>
										
								</td>
								</logic:equal>
								<logic:equal value="true" property="displayHeightWeight" name="admissionFormForm">
								 <td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.height.label"/>&nbsp;
									<nested:write property="applicantDetails.personalData.height"></nested:write>
								</td>
								<td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.weight.label"/>&nbsp;
									<nested:write property="applicantDetails.personalData.weight"></nested:write>
								</td>
								</logic:equal>
	                          </tr>
							<logic:equal value="true" property="displayLanguageKnown" name="admissionFormForm">
							<tr class="row-white">
	                            <td height="23" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.language.label"/></div></td>
	                            <td height="23" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.speaklanguage.label"/>&nbsp;
									<nested:write property="applicantDetails.personalData.languageByLanguageSpeak"></nested:write>
								</td>
								<td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.readlanguage.label"/>&nbsp;
									<nested:write property="applicantDetails.personalData.languageByLanguageRead"></nested:write>
								</td>
								<td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.writelanguage.label"/>&nbsp;
									<nested:write property="applicantDetails.personalData.languageByLanguageWrite"></nested:write>
								</td>
	                          </tr>
							</logic:equal>

							<logic:equal value="true" property="displayTrainingDetails" name="admissionFormForm">
							<tr class="row-white">
	                            <td height="23" colspan="4" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.training.label"/></div></td>
							</tr>
							<tr class="row-white">
	                            <td height="23" class="row-even" align="center"><bean:message key="knowledgepro.applicationform.trainingprog.label"/></td>
								 <td height="23" class="row-even" align="left">
									<nested:write property="applicantDetails.personalData.trainingProgName"></nested:write>
								</td>
								<td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.trainingduration.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:write property="applicantDetails.personalData.trainingDuration"></nested:write>
										
								</td>
	                          </tr>
								<tr class="row-white">
	                           
								<td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.traininginst.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:write property="applicantDetails.personalData.trainingInstAddress"></nested:write>
								</td>
								
								<td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.trainingpurpose.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:write property="applicantDetails.personalData.trainingPurpose"></nested:write>
										
								</td>
	                         	 </tr>
							</logic:equal>
							
							<logic:equal value="true" property="displayAdditionalInfo" name="admissionFormForm">
							<tr class="row-white">
	                            <td height="23" colspan="4" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.addninfo.label"/></div></td>
							</tr>
							<tr class="row-white">
	                            <td height="23" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.addninfo.label1"/><B><bean:write property="organizationName" name="admissionFormForm"/></B>:</td>
								 <td height="23" class="row-even" align="left">
									<nested:write property="applicantDetails.personalData.courseKnownBy" ></nested:write>
								</td>
								<td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.strength.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:write property="applicantDetails.personalData.strength"></nested:write>
										
								</td>
	                          </tr>
								<tr class="row-white">
	                           
								<td height="23" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.addninfo.label2"/> <B><bean:write property="applicantDetails.course.name" name="admissionFormForm"/> </B>:</td>
								<td height="23" class="row-even" align="left">
									<nested:write property="applicantDetails.personalData.courseOptReason"></nested:write>
								</td>
								
								<td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.weakness.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:write property="applicantDetails.personalData.weakness"></nested:write>
										
								</td>
	                         	 </tr>
								<tr class="row-white">
	                           
								<td height="23" colspan="3" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.addninfo.label3"/> <B><bean:write property="applicantDetails.course.name" name="admissionFormForm"/></B>:</td>
								<td height="23" class="row-even" align="left">
									<nested:write property="applicantDetails.personalData.otherAddnInfo"></nested:write>
								</td>
								
	                         	 </tr>
							</logic:equal>
							<logic:equal value="true" property="displayExtracurricular" name="admissionFormForm">
							<tr class="row-white">
	                            <td height="23" colspan="4" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.extracurr.label"/></div></td>
							</tr>
							<tr class="row-white">
	                            <td height="23" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.extracurr.label2"/></td>
								 <td height="23" class="row-even" align="left">
									<nested:write property="applicantDetails.personalData.extracurricularNames"></nested:write>
								</td>
								 <td height="23" class="row-even" align="left">&nbsp;</td>
								 <td height="23" class="row-even" align="left">&nbsp;</td>
	                          </tr>
								
							</logic:equal>
	                      </table>
					
						
					</td>
                      <td  background="images/right.gif" width="5" height="57"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
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
												key="knowledgepro.admission.passportNo" />
											</div>
											</td>
											<td width="236" height="23" class="row-even" align="left"><nested:write property="applicantDetails.personalData.passportNo"></nested:write></td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.issuingCountry" />
											</div>
											</td>
											<td width="244" class="row-even" align="left">
												&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.passportIssuingCountry" />
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.validUpto" /></div>
											</td>
											<td height="20" class="row-white" align="left">
											<nested:write property="applicantDetails.personalData.passportValidity"></nested:write>
									
											</td>
											<td class="row-odd">
											<div align="left"><bean:message key="knowledgepro.applicationform.residentpermit.label"/></div>
											</td>
											<td class="row-white">
											<nested:write property="applicantDetails.personalData.residentPermitNo"></nested:write>
											</td>
										</tr>
										<tr class="row-white">
											<td width="212" height="23" colspan="2" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.policedate.label"/>
											</div>
											</td>
											<td width="236" height="23" class="row-even" align="left">
											<nested:write property="applicantDetails.personalData.residentPermitDate"></nested:write>
												
											</td>
											
											<td width="244" class="row-even" align="left">
												
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
                  <td colspan="2" class="heading" align="left"><bean:message key="admissionForm.edit.vehicledetails.label"/></td>
                </tr>
                <tr>
                  <td colspan="2" class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td width="100%" height="29" valign="top"><table width="100%" height="24" border="0" cellpadding="0" cellspacing="1">
                          <tr class="row-white">
                            <td width="212" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.edit.vehicletypes.label"/></div></td>
                            <td width="236" height="25" class="row-even" align="left">
								<nested:write property="applicantDetails.vehicleDetail.vehicleType"></nested:write>
							</td>
                            <td width="224" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.edit.vehicleno.label"/></div></td>
                            <td width="244" height="25" class="row-even" align="left"><nested:write property="applicantDetails.vehicleDetail.vehicleNo"></nested:write></td>
                          </tr>
                          
                      </table></td>
                      <td  background="images/right.gif" width="5" height="29"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
                </tr>

				<tr>
                  <td colspan="2" class="heading" align="left"><bean:message key="admissionForm.edit.admissiondetails.label"/> </td>
                </tr>
				<tr>
                  <td colspan="2" class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td width="100%" height="29" valign="top"><table width="100%" height="24" border="0" cellpadding="0" cellspacing="1">
                          <tr class="row-white">
                            <td width="212" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.admitted.through"/></div></td>
                           <td width="236" height="25" class="row-even" align="left">
											<nested:select property="applicantDetails.admittedThroughId"
												styleId="admittedThroughId" styleClass="combo">
												<html:option value="">-Select-</html:option>
												<nested:optionsCollection property="admittedThroughList"
													label="name" value="id" />

											</nested:select></td>
                           
                           
                            <!--<td width="236" height="25" class="row-even" align="left">
								<nested:write property="applicantDetails.admittedThroughName" name="admissionFormForm"/>	
							</td>
                            --><td width="224" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.edit.subgrp.label"/> </div></td>
                            <td width="244" height="25" class="row-even" align="left">
                            	<nested:write property="applicantDetails.subjectGroupNames" name="admissionFormForm"/>	
							</td>
                          </tr>
                           <tr class="row-white">
                            <td width="212" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.edit.freeship.label"/></div></td>
                            <td width="236" height="25" class="row-even" align="left">
								<logic:equal name="admissionFormForm" property="applicantDetails.isFreeShip" value="true"><bean:message key="knowledgepro.applicationform.yes.label"/></logic:equal>
								<logic:equal name="admissionFormForm" property="applicantDetails.isFreeShip" value="false"><bean:message key="knowledgepro.applicationform.No.label"/></logic:equal>
							</td>
                            <td width="224" height="25" class="row-odd" align="right">Is-LIG: </td>
                            <td width="244" height="25" class="row-even" align="left">
								<logic:equal name="admissionFormForm" property="applicantDetails.isLIG" value="true"><bean:message key="knowledgepro.applicationform.yes.label"/></logic:equal>
								<logic:equal name="admissionFormForm" property="applicantDetails.isLIG" value="false"><bean:message key="knowledgepro.applicationform.No.label"/></logic:equal>
							</td>
                          </tr>
						
                      </table></td>
                      <td  background="images/right.gif" width="5" height="29"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
                </tr> 

  				<tr>
                  <td colspan="2" class="heading" align="left"><bean:message key="admissionForm.approveview.prereq.label" /></td>
                </tr>
                <tr>
                  <td colspan="2" class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td width="100%" height="29" valign="top"><table width="100%" height="24" border="0" cellpadding="0" cellspacing="1">
						<tr class="row-white">
                            <td height="25" class="row-odd"><div align="left"><bean:message key="knowledgepro.admission.prerequisite" /></div></td>
                            <td height="25" class="row-odd" align="left"><bean:message key="knowledgepro.admin.prereqdef.percentage" /></td>
							<td height="25" class="row-odd" align="left"><bean:message key="knowledgepro.attendance.rollno" /></td>
                            <td height="25" class="row-odd"><div align="left"><bean:message key="admissionForm.approveview.prereq.exammonth.label" /></div></td>
                            <td height="25" class="row-odd" align="left"><bean:message key="admissionForm.approveview.prereq.examyear.label" /></td>
                          </tr>
						
						<logic:iterate id="prereq" property="applicantDetails.prerequisiteTos" name="admissionFormForm">
                          <tr class="row-white">
                            <td height="25" class="row-even"><div align="left"><nested:write property="prerequisiteName" name="prereq"/> </div></td>
                            <td height="25" class="row-even" align="left"><nested:write property="prerequisiteMarksObtained" name="prereq"/></td>
							<td height="25" class="row-even" align="left"><nested:write property="rollNo" name="prereq"/></td>
                            <td height="25" class="row-even"><div align="left">

                      				<logic:equal name="prereq" property="examMonth" value="1">JANUARY</logic:equal>
					              	<logic:equal name="prereq" property="examMonth" value="2">FEBRUARY</logic:equal>
									<logic:equal name="prereq" property="examMonth" value="3">MARCH</logic:equal>
									<logic:equal name="prereq" property="examMonth" value="4">APRIL</logic:equal>
									<logic:equal name="prereq" property="examMonth" value="5">MAY</logic:equal>
									<logic:equal name="prereq" property="examMonth" value="6">JUNE</logic:equal>
									<logic:equal name="prereq" property="examMonth" value="7">JULY</logic:equal>
									<logic:equal name="prereq" property="examMonth" value="8">AUGUST</logic:equal>
									<logic:equal name="prereq" property="examMonth" value="9">SEPTEMBER</logic:equal>
									<logic:equal name="prereq" property="examMonth" value="10">OCTOBER</logic:equal>
									<logic:equal name="prereq" property="examMonth" value="11">NOVEMBER</logic:equal>
									<logic:equal name="prereq" property="examMonth" value="12">DECEMBER</logic:equal>

							</div></td>
                            <td height="25" class="row-even" align="left"><nested:write property="examYear" name="prereq"/></td>
                          </tr>
                          </logic:iterate>
                      </table></td>
                      <td  background="images/right.gif" width="5" height="29"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
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
											
										</tr>
										
										<logic:notEmpty name="admissionFormForm" property="applicantDetails.preference.firstprefCourseName">
										<tr class="row-white">
											<td width="212" height="27" class="row-even">
											<div align="center"><bean:message
												key="knowledgepro.admission.firstpreference" />:</div>
											</td>
											<td width="236" height="27" class="row-even">
											<div align="center"><bean:write
												name="admissionFormForm"
												property="applicantDetails.preference.firstprefPgmTypeName" /></div>
											</td>
											<td width="224" class="row-even">
											<div align="center"><bean:write
												name="admissionFormForm"
												property="applicantDetails.preference.firstprefPgmName" /></div>
											</td>
											<td width="244" class="row-even">
											<div align="center"><bean:write
												name="admissionFormForm"
												property="applicantDetails.preference.firstprefCourseName" /></div>
											</td>
											
										</tr>
										</logic:notEmpty>
										<logic:notEmpty name="admissionFormForm" property="applicantDetails.preference.secondprefCourseName">
										<tr class="row-white">
											<td height="27" class="row-white">
											<div align="center"><bean:message
												key="knowledgepro.admission.secondpreference" />:</div>
											</td>
											<td height="27" class="row-white">
											<div align="center"><bean:write
												name="admissionFormForm"
												property="applicantDetails.preference.secondprefPgmTypeName" /></div>
											</td>
											<td class="row-white">
											<div align="center"><bean:write
												name="admissionFormForm"
												property="applicantDetails.preference.secondprefPgmName" /></div>
											</td>
											<td class="row-white">
											<div align="center"><bean:write
												name="admissionFormForm"
												property="applicantDetails.preference.secondprefCourseName" /></div>
											</td>
											
										</tr>
										</logic:notEmpty>
										<logic:notEmpty name="admissionFormForm" property="applicantDetails.preference.thirdprefCourseName">
										<tr class="row-white">
											<td height="27" class="row-even">
											<div align="center"><bean:message
												key="knowledgepro.admission.thirdpreference" />:</div>
											</td>
											<td height="27" class="row-even">
											<div align="center"><bean:write
												name="admissionFormForm"
												property="applicantDetails.preference.thirdprefPgmTypeName" /></div>
											</td>
											<td class="row-even">
											<div align="center"><bean:write
												name="admissionFormForm"
												property="applicantDetails.preference.thirdprefPgmName" /></div>
											</td>
											<td class="row-even">
											<div align="center"><bean:write
												name="admissionFormForm"
												property="applicantDetails.preference.thirdprefCourseName" /></div>
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

	<logic:equal value="true" property="workExpNeeded" name="admissionFormForm">
				 <tr>
                  <td colspan="2" class="heading" align="left"><bean:message key="knowledgepro.applicationform.workexp.label"/> </td>
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
                            <td height="27" class="row-odd"><div align="left"><bean:message key="knowledgepro.applicationform.orgName.label"/></div></td>
                            <td height="27" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.jobdesc.label"/></div></td>
                            <td class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.fromdt.label"/></div></td>
                            <td class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.todt.label"/></div></td>
							<td height="27" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.lastsal.label"/></div></td>
							<td height="27" class="row-odd"><div align="center"> <bean:message key="knowledgepro.applicationform.reportto.label"/></div></td>
                          </tr>
						<nested:iterate property="applicantDetails.workExpList" id="exp" name="admissionFormForm" type="com.kp.cms.to.admin.ApplicantWorkExperienceTO" indexId="count">
                          <tr class="row-white">
                            <td height="27" class="row-even"><div align="left">
								<nested:write property="organization"/>
                            </div></td>
                            <td height="27" class="row-even"><div align="center">
								<nested:write property="position"/>
                            </div></td>
                            <td width="224" class="row-even"><div align="center">
							
                             <nested:write property="fromDate"/>
                            
                            </div></td>
                            <td class="row-even"><div align="center">
                             <nested:write property="toDate" />
                              
                            </div></td>
							<td  height="27" class="row-even"><div align="center">
								<nested:write property="salary"/>
                            </div></td>
 							<td  height="27" class="row-even"><div align="center">
								<nested:write property="reportingTo"/>
                            </div></td>
                          </tr>
                          </nested:iterate>
                          
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
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="236" height="20" class="row-even" align="left">&nbsp;
												<nested:write property="applicantDetails.personalData.currentAddressLine1"></nested:write>
											</td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp;
												<nested:write property="applicantDetails.personalData.currentAddressLine2"></nested:write>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.currentCityName"></nested:write>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td class="row-white" align="left">
												&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.currentCountryName" />
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;
											<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.currentStateName" />
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;<nested:write property="applicantDetails.personalData.currentAddressZipCode"></nested:write>
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
											<td width="236" height="20" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.permanentAddressLine1"></nested:write>
											</td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.permanentAddressLine2"></nested:write>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.permanentCityName"></nested:write>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td class="row-white" align="left">
											&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.permanentCountryName" />
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;
												&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.permanentStateName" />
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.permanentAddressZipCode"></nested:write>
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
											<div align="left"><bean:message
												key="admissionForm.education.State.label" /></div>
											</td>
											<td class="row-odd"><bean:message
												key="knowledgepro.admission.passingYear" /></td>
											<td class="row-odd"><bean:message key="knowledgepro.applicationform.passingmonth"/></td>
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
											<td class="row-odd"><bean:message key="knowledgepro.applicationform.prevregno.label"/></td>
											
										</tr>
										<c:set var="temp" value="0" />
										<nested:iterate property="applicantDetails.ednQualificationList" id="ednQualList" indexId="count">
										
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td width="5%" height="25" class="row-even">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td width="11%" height="25" class="row-even"><bean:write
															name="ednQualList" property="docName" /></td>
														<td width="9%" class="row-even">
														<div align="center">
															<logic:equal value="true" name="ednQualList" property="examConfigured">
																<nested:write property="selectedExamName"/>
															</logic:equal>
														</div>
														</td>
														<td width="20%" class="row-even">
														<div align="left">
														<bean:write name="ednQualList"
															property="universityName" />
														</div>
														</td>
														<td width="22%" class="row-even">
														<div align="left">
														<bean:write name="ednQualList"
															property="institutionName" />
														</div>
														</td>
														<td width="22%" class="row-even">
														<div align="left">
														<bean:write name="ednQualList"
															property="stateName" />
														</div>
														</td>
														<td width="16%" class="row-even">
															<bean:write
															name="ednQualList" property="yearPassing" />
														</td>
														<td width="16%" class="row-even">
															<logic:equal name="ednQualList" property="monthPassing" value="1">JANUARY</logic:equal>
											              	<logic:equal name="ednQualList" property="monthPassing" value="2">FEBRUARY</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="3">MARCH</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="4">APRIL</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="5">MAY</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="6">JUNE</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="7">JULY</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="8">AUGUST</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="9">SEPTEMBER</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="10">OCTOBER</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="11">NOVEMBER</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="12">DECEMBER</logic:equal>
														</td>
														<logic:equal value="true" property="semesterWise" name="ednQualList">
														<logic:equal value="false" property="consolidated" name="ednQualList">
														<td class="row-even" colspan="2">
														<div align="center"><a href="javascript:void(0)" onclick="getSemesterMarkDetails('<bean:write name="ednQualList" property="id"/>')"><bean:message key="admissionForm.approveview.prereq.semmark.viewlink" /></a></div>
														</td>
														</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated" name="ednQualList">
														<logic:equal value="false" property="semesterWise" name="ednQualList">
														<td class="row-even" colspan="2">
														<div align="center"><a href="javascript:void(0)" onclick="getDetailsMark('<bean:write name="ednQualList" property="id"/>')"><bean:message key="admissionForm.approveview.prereq.detmark.viewlink" /></a></div>
														</td>
														</logic:equal>
														</logic:equal>
														
														<logic:notEqual value="true" property="semesterWise" name="ednQualList">
														<logic:notEqual value="false" property="consolidated" name="ednQualList">
														<td class="row-even">
														<div align="center">
														
														<nested:write property="marksObtained"></nested:write>
														</div>
														</td>
														<td class="row-even">
														<div align="center">
														<nested:write property="totalMarks"></nested:write>
														</div>
														</td>
														</logic:notEqual>
														</logic:notEqual>

														<td width="9%" class="row-even">
														<div align="center">
														<bean:write name="ednQualList"
															property="noOfAttempts" />
														</div>
														</td>
														<td width="9%" class="row-even">
														<div align="center">
															<logic:equal value="true" name="ednQualList" property="lastExam">
															<nested:write property="previousRegNo"/></logic:equal>
														</div>
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
														<td  class="row-white">
														<div align="center">
															<logic:equal value="true" name="ednQualList" property="examConfigured">
																<nested:write property="selectedExamName"/>
															</logic:equal>
														</div>
														</td>
														<td class="row-white">
														<div align="left">
														<bean:write name="ednQualList"
															property="universityName" />
														</div>
														</td>
														<td class="row-white">
														<div align="left">
														<bean:write name="ednQualList"
															property="institutionName" />
														</div>
														</td>
														<td class="row-white">
														<div align="left">
														<bean:write name="ednQualList"
															property="stateName" />
														</div>
														</td>
														<td class="row-white">
														<bean:write
															name="ednQualList" property="yearPassing" />
														</td>
														<td class="row-white">
															<logic:equal name="ednQualList" property="monthPassing" value="1">JANUARY</logic:equal>
											              	<logic:equal name="ednQualList" property="monthPassing" value="2">FEBRUARY</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="3">MARCH</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="4">APRIL</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="5">MAY</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="6">JUNE</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="7">JULY</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="8">AUGUST</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="9">SEPTEMBER</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="10">OCTOBER</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="11">NOVEMBER</logic:equal>
															<logic:equal name="ednQualList" property="monthPassing" value="12">DECEMBER</logic:equal>
														</td>
														
													<logic:equal value="true" property="semesterWise" name="ednQualList">
														<logic:equal value="false" property="consolidated" name="ednQualList">
														<td class="row-white" colspan="2">
														<div align="center"><a href="javascript:void(0)" onclick="getSemesterMarkDetails('<bean:write name="ednQualList" property="id"/>')"><bean:message key="admissionForm.approveview.prereq.semmark.viewlink" /></a></div>
														</td>
														</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated" name="ednQualList">
														<logic:equal value="false" property="semesterWise" name="ednQualList">
														<td class="row-white" colspan="2">
														<div align="center"><a href="javascript:void(0)" onclick="getDetailsMark('<bean:write name="ednQualList" property="id"/>')"><bean:message key="admissionForm.approveview.prereq.detmark.viewlink" /></a></div>
														</td>
														</logic:equal>
														</logic:equal>
														
														<logic:notEqual value="true" property="semesterWise" name="ednQualList">
														<logic:notEqual value="false" property="consolidated" name="ednQualList">
														<td class="row-white">
														<div align="center">
														
														<nested:write property="marksObtained"></nested:write>
														</div>
														</td>
														<td class="row-white">
														<div align="center">
														<nested:write property="totalMarks"></nested:write>
														</div>
														</td>
														</logic:notEqual>
														</logic:notEqual>
													
														<td class="row-white">
														<div align="center">
														<bean:write name="ednQualList"
															property="noOfAttempts" />
														</div>
														</td>
														<td class="row-white">
														<div align="center">
															<logic:equal value="true" name="ednQualList" property="lastExam">
															<nested:write property="previousRegNo"/></logic:equal>
														</div>
														</td>
														
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</nested:iterate>
										<logic:equal value="true" property="displayTCDetails" name="admissionFormForm">
									<tr>
											<td height="25" class="row-odd">
											<div align="center"><bean:message key="admissionForm.education.TCNO.label"/></div>
											</td>
											<td height="25" class="row-even"><nested:write property="applicantDetails.tcNo"></nested:write></td>
											<td class="row-odd">
											<div align="left"><bean:message key="admissionForm.education.TCDate.label"/></div>
											</td>
											<td class="row-even" colspan="2">
											<div align="left"><nested:write property="applicantDetails.tcDate"></nested:write>
											</div>
											</td>
											
											<td class="row-odd"><bean:message key="admissionForm.education.markcard.label"/></td>
											<td class="row-even">
											<div align="center"><nested:write property="applicantDetails.markscardNo"></nested:write></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message key="admissionForm.education.markcarddate.label"/></div>
											</td>
											<td class="row-even" colspan="2">
											<div align="left"><nested:write property="applicantDetails.markscardDate"></nested:write>
												</div>
											</td>
										</tr>
								</logic:equal>
							<logic:equal value="true" property="displayLateralTransfer"	name="admissionFormForm">
											<tr>
												<td class="row-even">
												<div align="center">&nbsp;</div>
												</td>
												<td height="25" colspan="2" class="row-even"><logic:equal
													value="true" property="displayLateralDetails"
													name="admissionFormForm">
													<div align="center"><a href="#"
														onclick="detailLateralSubmit()"><bean:message key="admissionForm.education.laterallink.label"/></a></div>

												</logic:equal></td>
												<td height="25" colspan="2" class="row-even">
												<div align="center">&nbsp;</div>
												</td>
												<td class="row-even" colspan="2"><logic:equal
													value="true" property="displayTransferCourse"
													name="admissionFormForm">
													<div align="center"><a href="#" onclick="detailTransferSubmit()"><bean:message key="admissionForm.education.transferlink.label"/></a></div>
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
						<logic:equal value="true" property="displayEntranceDetails" name="admissionFormForm">

					<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message key="admissionForm.education.entrancedetails.label"/></td>
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
										<tr >
            								<td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.education.entrance.label"/></div></td>
												<td width="16%" height="25" class="row-even"><div align="left">
													<nested:write property="applicantDetails.entranceDetail.entranceName"/>
													</div></td>
											<td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.totalMarks"/>:</div></td>
											<td width="16%" height="25" class="row-even"><div align="left"><nested:write property="applicantDetails.entranceDetail.totalMarks"/></div></td>
											<td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.education.markObtained.label"/>:</div></td>
											<td width="16%" height="25" class="row-even"><div align="left"><nested:write property="applicantDetails.entranceDetail.marksObtained"></nested:write></div></td>
           								 </tr>
										<tr >
            								<td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.prereq.roll.label"/></div></td>
											<td height="25" class="row-even"><div align="left"><nested:write property="applicantDetails.entranceDetail.entranceRollNo"></nested:write></div></td>
											<td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.passingmonth"/>:</div></td>
											<td height="25" class="row-even"><div align="left">
											
															<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="1">JANUARY</logic:equal>
											              	<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="2">FEBRUARY</logic:equal>
															<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="3">MARCH</logic:equal>
															<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="4">APRIL</logic:equal>
															<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="5">MAY</logic:equal>
															<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="6">JUNE</logic:equal>
															<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="7">JULY</logic:equal>
															<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="8">AUGUST</logic:equal>
															<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="9">SEPTEMBER</logic:equal>
															<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="10">OCTOBER</logic:equal>
															<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="11">NOVEMBER</logic:equal>
															<logic:equal property="applicantDetails.entranceDetail.monthPassing" name="admissionFormForm" value="12">DECEMBER</logic:equal>

											</div></td>
											<td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.passingYear"/>:</div></td>
											<td height="25" class="row-even"><div align="left">
											<nested:write property="applicantDetails.entranceDetail.yearPassing"></nested:write>
												
											</div></td>
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
											<nested:write property="applicantDetails.personalData.fatherName"></nested:write>
											</td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
												<nested:write property="applicantDetails.personalData.fatherEducation"></nested:write>
											</td>
										</tr>
									
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="admissionFormForm.fatherIncome" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.fatherIncome" />
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.fatherOccupation" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.fatherEmail" />
											</td>
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
											<nested:write property="applicantDetails.personalData.motherName"></nested:write>
											</td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.motherEducation"></nested:write>
											</td>
										</tr>
										
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="admissionFormForm.fatherIncome" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.motherIncome" />
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;
												 &nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.motherOccupation" />
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.motherEmail"></nested:write>
											</td>
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

								<logic:equal value="true" property="displayFamilyBackground" name="admissionFormForm">
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
											<div align="right"><bean:message key="knowledgepro.applicationform.brothername.label"/></div>
											</div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.brotherName"></nested:write>
											</td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
												<nested:write property="applicantDetails.personalData.brotherEducation"></nested:write>
											</td>
										</tr>
										
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.income" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.brotherIncome"></nested:write>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">
											<nested:write property="applicantDetails.personalData.brotherOccupation"></nested:write></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.age.label"/></div>
											</td>
											<td height="30" class="row-even" align="left">
											<nested:write property="applicantDetails.personalData.brotherAge"></nested:write>
											</td>
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
											<div align="right"><bean:message key="knowledgepro.applicationform.sistername.label"/></div>
											</div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.sisterName"></nested:write>
											</td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.sisterEducation"></nested:write>
											</td>
										</tr>
										
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.income" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.sisterIncome"></nested:write>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;
												  <nested:write property="applicantDetails.personalData.sisterOccupation"></nested:write>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.age.label"/></div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.sisterAge"></nested:write>
											</td>
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
											<nested:write property="applicantDetails.personalData.parentAddressLine1"></nested:write>
											</td>
											<td width="100" height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td width="190" height="20" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.parentCityName"></nested:write>
											</td>
											<td width="122" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td width="206" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.parentAddressZipCode"></nested:write>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.parentAddressLine2"></nested:write>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" />
											</div>
											</td>
											<td class="row-white" align="left">
											&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.parentCountryName" />
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.parentPh1"></nested:write>-
											<nested:write property="applicantDetails.personalData.parentPh2"></nested:write>-
											<nested:write property="applicantDetails.personalData.parentPh3"></nested:write>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs3.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:write property="applicantDetails.personalData.parentAddressLine3"></nested:write>
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" align="left">&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.parentStateName" />
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.parentMob1"></nested:write>
											<nested:write property="applicantDetails.personalData.parentMob2"></nested:write>
											<nested:write property="applicantDetails.personalData.parentMob3"></nested:write>
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
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message key="knowledgepro.applicationform.guardianaddr.label"/></td>
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
											<nested:write property="applicantDetails.personalData.guardianAddressLine1"></nested:write>
											</td>
											<td width="100" height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td width="190" height="20" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.cityByGuardianAddressCityId" ></nested:write>
											</td>
											<td width="122" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td width="206" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.guardianAddressZipCode"></nested:write>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.guardianAddressLine2"></nested:write>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" />
											</div>
											</td>
											<td class="row-white" align="left">
											&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.guardianCountryName" />
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.guardianPh1"></nested:write>
											<nested:write property="applicantDetails.personalData.guardianPh2"></nested:write>
											<nested:write property="applicantDetails.personalData.guardianPh3"></nested:write>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs3.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:write property="applicantDetails.personalData.guardianAddressLine3"></nested:write>
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" align="left">
											&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.personalData.guardianStateName" />
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" align="left">&nbsp;
											<nested:write property="applicantDetails.personalData.guardianMob1"></nested:write>
											<nested:write property="applicantDetails.personalData.guardianMob2"></nested:write>
											<nested:write property="applicantDetails.personalData.guardianMob3"></nested:write>
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
											<td height="25" class="row-odd">
											<div align="center"><bean:message key="admissionForm.edit.hardcopy.label"/></div>
											</td>
											<td height="25" class="row-odd"><bean:message
												key="knowledgepro.admission.documents" /></td>
											<td class="row-odd">&nbsp;</td>
											<td class="row-odd"><bean:message
												key="knowledgepro.admission.uploadDocs" /></td>
										</tr>
										<c:set var="temp" value="0" />
										<nested:iterate name="admissionFormForm"
											property="applicantDetails.editDocuments" indexId="count" id="docList">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td width="8%" height="25" class="row-even">
														<div align="center">
														 <nested:write name='docList' property='temphardSubmitted'/>
														
														</div>
														</td>
														<td width="18%" height="25" class="row-even"><nested:write
															property="printName" /></td>
														<td width="28%" class="row-even"><nested:equal
															value="true" property="documentPresent">
															<a
																href="javascript:downloadFile('<nested:write property="id"/>')"><bean:message key="knowledgepro.view" /></a>
														</nested:equal></td>
														<td width="46%" class="row-even">
															
														</td>
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td height="25" class="row-white">
														<div align="center"><nested:write name='docList' property='temphardSubmitted'/>
														</div>
														</td>
														<td height="25" class="row-white"><nested:write
															property="printName" /></td>
														<td class="row-white"><nested:equal value="true"
															property="documentPresent">
															<span class="row-even"> <a
																href="javascript:downloadFile('<nested:write property="id"/>')"><bean:message key="knowledgepro.view" /></a></span>
														</nested:equal></td>
														<td class="row-white">
															<span class="row-even"></span>
														</td>
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</nested:iterate>
										<tr>
											<td height="25" class="row-odd">
											<div align="center"><bean:message key="admissionForm.approveview.submitdt.label" /></div>
											</td>
											<td height="25" class="row-even"><nested:write property="submitDate"/>
                            
								</td>
											<td class="row-odd"><bean:message key="admissionForm.approveview.remark.label" /></td>
											<td class="row-even"><div align="left"><nested:write property="applicantDetails.remark"/></div></td>
										</tr>
										

										<tr>
											<td height="25" class="row-odd">
											<div align="center"><bean:message key="admissionForm.approveview.appremark.label" /></div>
											</td>
											
											<td class="row-even" colspan="3"><div align="left"><nested:text property="applicantDetails.approvalRemark" size="100" maxlength="150"/></div></td>
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
									<td width="48%" height="21">
									<div align="right"><html:submit styleClass="formbutton">
										APPROVE
									</html:submit></div>
									</td>
									<td width="1%"><div align="center"></div></td>
									<td width="51%" height="45" align="left"><html:button
										property="" styleClass="formbutton"
										onclick="submitApproveCancelButton()">
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
	</table></div>
</html:form>