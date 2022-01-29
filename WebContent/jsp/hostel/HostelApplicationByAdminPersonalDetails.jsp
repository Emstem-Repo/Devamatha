<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<SCRIPT type="text/javascript">	
</SCRIPT>



<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="css/styles.css" rel="stylesheet" type="text/css">
	</head>
	
	<body>
    	<html:form action="/HostelApplicationByAdmin">
			<html:hidden property="formName" value="hostelApplicationByAdminForm" />
			<html:hidden property="pageType" value="1" />
    
    		<c:choose>
      			<c:when test="${empty(hostelApplicationByAdminForm.staffId)}" >   
     			<table width="98%" border="0">		
					<tr>	           
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="10"><img src="images/Tright_03_01.gif"></td>
									<td width="100%" background="images/Tcenter.gif" class="body">
										<div align="left"><strong class="boxheader"><bean:message
										key="knowledgepro.admission.viewapplicantdetails" /></strong></div>
									</td>
									<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
								</tr>
                               <tr>
								<td valign="top" background="images/Tright_03_03.gif"></td>	           
						        <td>
                                <table  border="0" cellpadding="1" cellspacing="2">                             
								<tr>
									
									<td valign="top" class="news">
										<table border="0" cellpadding="1" cellspacing="2">
											<tr>
												<td colspan="3" class="heading" align="left">&nbsp;<bean:message
													key="knowledgepro.admission.studentInfo" /></td>
											</tr>
											<tr>
												<td width="48%" class="heading">
													<table width="98%" border="0" cellpadding="0" cellspacing="0">
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
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.firstName" />&nbsp;
																		<bean:write name="hostelApplicationByAdminForm"
																			property="personalDataTO.middleName" />&nbsp;
																		<bean:write name="hostelApplicationByAdminForm"
																		property="personalDataTO.lastName" /></td>
																	</tr>
																	<tr>
																		<td height="25" class="row-odd">
																			<div align="right"><bean:message
																			key="knowledgepro.attendanceentry.class" /></div>
																		</td>
																		<td height="30" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.studentClass" /></td>
																	</tr>
																	<tr>
																		<td height="25" class="row-odd">
																			<div align="right"><bean:message
																			key="admissionForm.studentedit.roll.label" /></div>
																		</td>
																		<td height="30" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.rollNo" /></td>
																	</tr>
                                                                    <tr>
																		<td height="25" class="row-odd">
																			<div align="right"><bean:message
																			key="knowledgepro.hostel.reservation.registerNo" /></div>
																		</td>
																		<td height="30" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.registerNo" /></td>
																	</tr>
																    
						 											<tr>
																		<td height="25" class="row-odd">
																			<div align="right"><bean:message
																			key="admissionForm.studentinfo.dob.label" /></div>
																		</td>
																		<td height="30" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.dob" /></td>
																	</tr>
																	<tr>
																		<td height="25" class="row-odd">
																			<div align="right"><bean:message
																			key="admissionForm.studentinfo.birthplace.label" /></div>
																		</td>
																		<td height="30" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.birthPlace" /></td>
																	</tr>
																	<tr>
																		<td height="25" class="row-odd">
																			<div align="right"><bean:message
																			key="admissionForm.studentinfo.birthstate.label" /></div>
																		</td>
																		<td height="35" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.stateOfBirth" /></td>
																	</tr>
																	<tr>
																		<td height="22" class="row-odd">
																			<div align="right"><bean:message
																			key="admissionForm.studentinfo.birthcountry.label" /></div>
																		</td>
																		<td height="22" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.countryOfBirth" /></td>
																	</tr>
																	<tr>
																		<td height="25" class="row-odd">
																		<div align="right"><bean:message
																			key="admissionForm.studentinfo.nationality.label" /></div>
																		</td>
																		<td height="35" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.citizenship" /></td>
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
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.residentCategoryName" /></td>
																	</tr>
																	<tr>
																		<td height="20" class="row-odd">
																		<div align="right"><bean:message
																			key="admissionForm.studentinfo.religion.label" /></div>
																		</td>
																		<td height="20" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.religionName" /></td>
																	</tr>
																	<tr>
																		<td height="20" class="row-odd">
																		<div align="right"><bean:message
																			key="admissionForm.studentinfo.subreligion.label" /></div>
																		</td>
																		<td height="20" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.subregligionName" /></td>
																	</tr>
																	<tr>
																		<td height="20" class="row-odd">
																		<div align="right"><bean:message
																			key="admissionForm.studentinfo.castcatg.label" /></div>
																		</td>
																		<td height="20" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.casteCategory" /></td>
																	</tr>
																	<tr>
																		<td height="17" class="row-odd">
																		<div align="right"><bean:message
																			key="admissionForm.studentinfo.belongsto.label" /></div>
																		</td>
																		<td height="17" class="row-even" align="left">&nbsp;<bean:write
																			name="hostelApplicationByAdminForm"
																			property="personalDataTO.belongsTo" />
																		</td>
																	</tr>
																<tr>
																	<td height="17" class="row-odd">
																	<div align="right"><bean:message
																		key="knowledgepro.admin.gender" />:</div>
																	</td>
																	<td height="17" class="row-even" align="left">&nbsp;<bean:write
																		name="hostelApplicationByAdminForm"
																		property="personalDataTO.gender" /></td>
																</tr>
																<tr>
																	<td height="20" class="row-odd">
																	<div align="right"><bean:message
																		key="admissionForm.studentinfo.bloodgroup.label" /></div>
																	</td>
																	<td height="20" class="row-even" align="left">&nbsp;<bean:write
																		name="hostelApplicationByAdminForm"
																			property="personalDataTO.bloodGroup" /></td>
																</tr>
										
																<tr>
																	<td height="20" class="row-odd">
																	<div align="right"><bean:message
																		key="admissionForm.studentinfo.phone.label" /></div>
																	</td>
																	<td height="20" class="row-even" align="left">&nbsp;<bean:write
																		name="hostelApplicationByAdminForm"
																		property="personalDataTO.landlineNo" />
																	&nbsp;</td>
																</tr>
																<tr>
																	<td height="20" class="row-odd">
																	<div align="right"><bean:message
																		key="admissionForm.studentinfo.mobile.label" /></div>
																	</td>
																	<td height="20" class="row-even" align="left">&nbsp;<bean:write
																		name="hostelApplicationByAdminForm"
																		property="personalDataTO.mobileNo" /></td>
																</tr>
																<tr>
																	<td height="20" class="row-odd">
																		<div align="right"><bean:message
																		key="admissionForm.studentinfo.email.label" /></div>
																	</td>
																	<td height="20" class="row-even" align="left">&nbsp;<bean:write
																		name="hostelApplicationByAdminForm"
																		property="personalDataTO.email" /><br />
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
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.permanentAddressLine1" /></td>
																<td width="224" class="row-odd">
																	<div align="right"><bean:message
																		key="admissionForm.studentinfo.addrs2.label" /></div>
																</td>
																<td width="244" class="row-even" align="left">&nbsp;<bean:write
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.permanentAddressLine2" /></td>
															</tr>
															<tr class="row-even">
																<td height="20" class="row-odd">
																	<div align="right"><bean:message
																	key="knowledgepro.admin.city" />:</div>
																</td>
																<td height="20" class="row-white" align="left">&nbsp;<bean:write
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.permanentCityName" /></td>
																<td class="row-odd">
																	<div align="right"><bean:message
																	key="knowledgepro.admin.state" /></div>
																</td>
																<td class="row-white" align="left">&nbsp;<bean:write
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.permanentStateName" /></td>
															</tr>
															<tr class="row-even">
																<td height="20" class="row-odd">
																	<div align="right"><bean:message
																	key="knowledgepro.admin.country" /></div>
																</td>
																<td height="20" class="row-even" align="left">&nbsp;<bean:write
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.permanentCountryName" /></td>
																<td class="row-odd">
																	<div align="right"><bean:message
																	key="knowledgepro.admission.zipCode" /></div>
																</td>
																<td class="row-even" align="left">&nbsp;<bean:write
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.permanentAddressZipCode" /></td>
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
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.currentAddressLine1" /></td>
																<td width="224" class="row-odd">
																	<div align="right"><bean:message
																	key="admissionForm.studentinfo.addrs2.label" /></div>
																</td>
																<td width="244" class="row-even" align="left">&nbsp;<bean:write
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.currentAddressLine2" /></td>
															</tr>
															<tr class="row-even">
																<td height="20" class="row-odd">
																	<div align="right"><bean:message
																	key="knowledgepro.admin.city" />:</div>
																</td>
																<td height="20" class="row-white" align="left">&nbsp;<bean:write
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.currentCityName" /></td>
																<td class="row-odd">
																	<div align="right"><bean:message
																	key="knowledgepro.admin.state" /></div>
																</td>
																<td class="row-white" align="left">&nbsp;<bean:write
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.currentStateName" /></td>
															</tr>
															<tr class="row-even">
																<td height="20" class="row-odd">
																	<div align="right"><bean:message
																	key="knowledgepro.admin.country" /></div>
																</td>
																<td height="20" class="row-even" align="left">&nbsp;<bean:write
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.currentCountryName" /></td>
																<td class="row-odd">
																	<div align="right"><bean:message
																	key="knowledgepro.admission.zipCode" /></div>
																</td>
																<td class="row-even" align="left">&nbsp;<bean:write
																	name="hostelApplicationByAdminForm"
																	property="personalDataTO.currentAddressZipCode" /></td>
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
								</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="heading" align="left">&nbsp;<bean:message
												key="knowledgepro.admission.parentInfo" /></td>
					</tr>

						<tr>
							<td  class="heading">
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
												key="knowledgepro.admission.fatherName" /></div>
											</td>
											<td width="236" height="20" class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.fatherName" /></td>
											</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
												<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.fatherEducation" /></td>
										</tr>
								        <tr>
											<td height="45" class="row-odd">
												<div align="right"><bean:message
												key="knowledgepro.admission.income" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.fatherIncome" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
												<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.fatherOccupation" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
												<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.fatherEmail" /></td>
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
															name="hostelApplicationByAdminForm"
															property="personalDataTO.motherName" /></td>
													</tr>
													<tr class="row-odd">
														<td width="45%" height="36" class="row-odd">
														<div align="right"><bean:message
															key="knowledgepro.admission.education" /></div>
														</td>
														<td width="55%" height="36" class="row-even" align="left">&nbsp;<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.motherEducation" /></td>
													</tr>
													<tr>
														<td height="45" class="row-odd">
														<div align="right"><bean:message
															key="knowledgepro.admission.income" /></div>
														</td>
														<td height="25" class="row-even" align="left">&nbsp;<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.motherIncome" /></td>
													</tr>
													<tr>
														<td height="25" class="row-odd">
														<div align="right"><bean:message
															key="knowledgepro.admin.occupation" />:</div>
														</td>
														<td height="30" class="row-even" align="left">&nbsp;<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.motherOccupation" /></td>
													</tr>
													<tr>
														<td height="25" class="row-odd">
														<div align="right"><bean:message
															key="admissionForm.studentinfo.email.label" /></div>
														</td>
														<td height="30" class="row-even" align="left">&nbsp;<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.motherEmail" /></td>
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
								<td colspan="2" class="heading">&nbsp;<bean:message
									key="knowledgepro.admission.parentAddress" /></td>
							</tr>
							<tr>
								<td colspan="2">
									<table width="99%" border="0"  cellpadding="0"
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
															name="hostelApplicationByAdminForm"
															property="personalDataTO.parentAddressLine1" />
														</td>
														<td width="100" height="20" class="row-odd">
															<div align="right"><bean:message
															key="knowledgepro.admin.city" />:</div>
														</td>
														<td width="190" height="20" class="row-even" align="left">&nbsp;<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.parentCityName" />
														</td>
														<td width="122" class="row-odd">
															<div align="right"><bean:message
																key="knowledgepro.admission.zipCode" /></div>
														</td>
														<td width="206" class="row-even" align="left">&nbsp;<bean:write
																name="hostelApplicationByAdminForm"
																property="personalDataTO.parentAddressZipCode" />
														</td>
													</tr>
													<tr class="row-even">
														<td height="20" class="row-odd">
															<div align="right"><bean:message
															key="admissionForm.studentinfo.addrs2.label" /></div>
														</td>
														<td height="20" class="row-white" align="left">&nbsp;<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.parentAddressLine2" />
														</td>
														<td class="row-odd">
															<div align="right"><bean:message
															key="knowledgepro.admin.state" />
															</div>
														</td>
														<td class="row-white" align="left">&nbsp;<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.parentStateName" />
														</td>
														<td height="20" class="row-odd">
															<div align="right"><bean:message
																key="admissionForm.studentinfo.phone.label" /></div>
														</td>
														<td height="20" class="row-white" align="left">&nbsp;<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.parentPhone" />
														</td>
													</tr>
													<tr class="row-even">
														<td height="20" class="row-odd">
														<div align="right"><bean:message key="admissionForm.studentinfo.addrs3.label" /></div>
														</td>
														<td height="20" class="row-even" align="left">&nbsp;<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.parentAddressLine3" />
														</td>
														<td height="20" class="row-odd">
														<div align="right"><bean:message key="knowledgepro.admin.country" /></div>
														</td>
														<td height="20" align="left">&nbsp;<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.parentCountryName" />
														</td>
														<td class="row-odd">
														<div align="right"><bean:message
															key="admissionForm.studentinfo.mobile.label" /></div>
														</td>
														<td height="20" align="left">&nbsp;<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.parentMobile" />
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
								<td colspan="2" class="heading" align="left">&nbsp;<bean:message key="knowledgepro.applicationform.guardianaddr.label" /></td>
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
														<td width="178" height="20" class="row-even" align="left">&nbsp;
															<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.guardianName" />
														</td>
														<td width="113" height="20" class="row-odd">
															<div align="right"><bean:message
															key="admissionForm.studentinfo.addrs1.label" /></div>
														</td>
														<td width="178" height="20" class="row-even" align="left">&nbsp;
															<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.guardianAddressLine1" />
														</td>
														<td width="100" height="20" class="row-odd">
															<div align="right"><bean:message
															key="knowledgepro.admin.city" />:</div>
														</td>
														<td width="190" height="20" class="row-even" align="left">&nbsp;
														<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.cityByGuardianAddressCityId" />
														</td>
														<td width="122" class="row-odd">
														<div align="right"><bean:message
															key="knowledgepro.admission.zipCode" /></div>
														</td>
														<td width="206" class="row-even" align="left">&nbsp;
															<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.guardianAddressZipCode" />
														</td>
													</tr>
													<tr class="row-even">
														<td height="20" class="row-odd">
														<div align="right"><bean:message
															key="admissionForm.studentinfo.addrs2.label" /></div>
														</td>
														<td height="20" class="row-white" align="left">&nbsp;
														<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.guardianAddressLine2" />
														</td>
														<td class="row-odd">
														<div align="right"><bean:message
															key="knowledgepro.admin.country" />
														</div>
														</td>
														<td class="row-white" align="left">
														<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.guardianCountryName" />
														</td>
														<td height="20" class="row-odd">
														<div align="right"><bean:message
															key="admissionForm.studentinfo.phone.label" /></div>
														</td>
														<td height="20" class="row-white" align="left">&nbsp;
															<bean:write name="hostelApplicationByAdminForm" property="personalDataTO.guardianPh1" />
															<bean:write name="hostelApplicationByAdminForm" property="personalDataTO.guardianPh2" />
															<bean:write name="hostelApplicationByAdminForm" property="personalDataTO.guardianPh3" />
														</td>
													</tr>
													<tr class="row-even">
														<td height="20" class="row-odd">
														<div align="right"><bean:message
															key="admissionForm.studentinfo.addrs3.label" /></div>
														</td>
														<td height="20" class="row-even" align="left">&nbsp;
														<bean:write
															name="hostelApplicationByAdminForm"
															property="personalDataTO.guardianAddressLine3" />
														</td>
														<td height="20" class="row-odd">
														<div align="right"><bean:message key="knowledgepro.admin.state" /></div>
														</td>
														<td height="20" align="left">&nbsp;
															<bean:write name="hostelApplicationByAdminForm" property="personalDataTO.guardianStateName" />	
														</td>
														<td class="row-odd">
															<div align="right"><bean:message
															key="admissionForm.studentinfo.mobile.label" /></div>
														</td>
														<td height="20" align="left">&nbsp;
															<bean:write name="hostelApplicationByAdminForm" property="personalDataTO.guardianMob1" />
															<bean:write name="hostelApplicationByAdminForm" property="personalDataTO.guardianMob2" />
															<bean:write name="hostelApplicationByAdminForm" property="personalDataTO.guardianMob3" />
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
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="47%" height="21">
												<div align="center"><input type="button" class="formbutton" value="Close" onclick="javascript:self.close();"/></div>
											</td>								
										</tr>
									</table>
								</td>
							</tr>
							
						</table>
					
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
   </c:when> 
    
   <c:otherwise>
    <table width="98%" border="0">		
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.hostel.applicationByAdmin.employee.details" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
                    
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%"  >
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="knowledgepro.hostel.applicationByAdmin.employeeInfo" /></td>
						</tr>
						<tr>
							<td  class="heading">
							<table  border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td  valign="top">
									<table width="100%" >
										<tr>
											<td  class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.hostel.applicationByAdmin.employeeName" />:</div>
											</td>
											<td  class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.firstName" />&nbsp;
											<bean:write name="hostelApplicationByAdminForm"
												property="personalDataTO.middleName" />&nbsp;
											<bean:write name="hostelApplicationByAdminForm"
												property="personalDataTO.lastName" /></td>
										</tr>
                                        <tr>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.sec.Designation"/>:</div>
											</td>
											<td class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.employeDesignation" /></td>
										</tr>
                                        <tr>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.hostel.applicationByAdmin.employee.department"/>:</div>
											</td>
											<td  class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.employeeDepartment" /></td>
										</tr>
										<tr>
											<td  class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.dob.label" /></div>
											</td>
											<td  class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.dob" /></td>
										</tr>
										
                                         <tr>
											<td  class="row-odd">
											<div align="right"><bean:message
											key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td  class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.landlineNo" />
											&nbsp;</td>
										</tr>
										<tr>
											<td class="row-odd">
											<div align="right"><bean:message
											key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td  class="row-even" align="left">&nbsp;<bean:write
										        name="hostelApplicationByAdminForm"
												property="personalDataTO.mobileNo" /></td>
										</tr>
										<tr>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td  class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.email" /><br />
											</td>
										</tr>
 										<tr>
											<td  class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.hostel.applicationByAdmin.employee.workEmail" />:</div>
											</td>
											<td class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.workEmail" /><br />
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
							<td width="52%">
							<table width="97%">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td>
									<table width="99%" cellspacing="1" cellpadding="2">
										
									 <tr>			
										<td  class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.gender" />:</div>
											</td>
											<td  class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.gender" /></td>
										</tr>
                                        <tr>
											<td  class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.castcatg.label" /></div>
											</td>
											<td  class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.casteCategory" /></td>
										</tr>
                                        <tr>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.nationality.label" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.citizenship" /></td>
										</tr>
										<tr>
											<td  class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.bloodgroup.label" /></div>
											</td>
											<td  class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.bloodGroup" /></td>
										</tr>
                                       <tr>
											<td  class="row-odd">
											<div align="right"><bean:message
												key="admissionFormForm.fatherName" /></div>
											</td>
											<td  class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.fatherName" /></td>
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
									<table width="100%" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-even">
											<td width="212" height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="236" height="20" class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.permanentAddressLine1" /></td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.permanentAddressLine2" /></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.permanentCityName" /></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.permanentStateName" /></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.permanentCountryName" /></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.permanentAddressZipCode" /></td>
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
									<table width="100%">
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.currentAddressLine1" /></td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td  class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.currentAddressLine2" /></td>
										</tr>
										<tr class="row-even">
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td  class="row-white" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.currentCityName" /></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td class="row-white" >&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.currentStateName" /></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.currentCountryName" /></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;<bean:write
												name="hostelApplicationByAdminForm"
												property="personalDataTO.currentAddressZipCode" /></td>
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
						</table>
						</td>
						<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
					</tr>
						<tr>
								<td colspan="2">&nbsp;</td>
							</tr>						
						<tr>
							<td valign="top" background="images/Tright_03_03.gif"></td>
							<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="47%" height="21">
									<div align="center"><input type="button" class="formbutton" value="Close" onclick="javascript:self.close();"/></div>
									</td>					

			
								</tr>
							</table>
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
   </c:otherwise>
   </c:choose>
	</html:form>
	</body>
</html>