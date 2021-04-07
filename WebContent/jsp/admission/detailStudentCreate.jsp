<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>


<%@page import="com.kp.cms.constants.CMSConstants"%><script type="text/javascript">
function onLoadAddrCheck()
{
	var sameAddr= document.getElementById("sameAddr").checked;

	if(sameAddr==true){
		disableTempAddress();
	}
	if(sameAddr==false){
		enableTempAddress();
	}	
}
	function detailSubmit(count)
	{
		document.getElementById("countID").value=count;
		document.studentEditForm.method.value="initDetailMarkPage";
		document.studentEditForm.submit();
	}
	function detailSemesterSubmit(count)
	{
		document.getElementById("countID").value=count;
		document.studentEditForm.method.value="initSemesterMarkPage";
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
		document.studentEditForm.method.value="initlateralEntryPage";
		document.studentEditForm.submit();
	}
	function detailTransferSubmit()
	{
		document.studentEditForm.method.value="initTransferEntryPage";
		document.studentEditForm.submit();
	}
	function getDetailForEdit(applicationNumber,appliedYear,method) {
		document.studentEditForm.method.value=method;
		document.studentEditForm.detailsView.value=false;
		document.studentEditForm.selectedAppNo.value=applicationNumber;
		document.studentEditForm.selectedYear.value=appliedYear;
		document.studentEditForm.submit();
	}
	function clearApplnNOField(field){
		if(field.value == "0")
			field.value = "";
	}
	function checkForEmptyAppNO(field){
		if(field.value.length == 0)
			field.value="0";
		if(isNaN(field.value)) {
			field.value="0";
		}
	}
</script>
<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
<html:form action="/studentEdit" enctype="multipart/form-data" method="POST">
	<html:hidden property="method" value="submitStudentCreation" />
	<html:hidden property="formName" value="studentEditForm" />
	<html:hidden property="selectedAppNo" value="" />
	<html:hidden property="selectedYear" value="" />
	<html:hidden property="detailsView" value="false" />
	<html:hidden property="pageType" value="14" />

	<input type="hidden" name="courseId" value='<bean:write	name="studentEditForm" property="applicantDetails.course.id" />' />
	<div style="overflow: auto; width: 800px;">
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="admissionForm.studentedit.main.label"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="admissionForm.studentedit.main.label"/></strong></div>
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
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message key="admissionForm.studentedit.main.label"/></td>
						</tr>
						<tr>
							<td colspan="2" align="left">
							<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
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
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.applicationnumber" />:</div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:text
												name="studentEditForm"
												property="applicantDetails.applnNo" size="9" maxlength="9" onkeypress="return isNumberKey(event)" onfocus="clearApplnNOField(this)" onblur="checkForEmptyAppNO(this)"></nested:text></td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.prereq.roll.label"/> </div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:text
												name="studentEditForm"
												property="rollNo" size="10" maxlength="10"></nested:text></td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message key="admissionForm.studentedit.regno.label"/> </div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:text
												name="studentEditForm"
												property="regNo" size="10" maxlength="10"></nested:text></td>
											
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
											<td height="24" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.application.challan.label" /></div>
											</td>
											<td height="24" class="row-white" align="left">&nbsp;
												<nested:text property="applicantDetails.challanRefNo" size="15" maxlength="30"></nested:text>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.journalNo" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;
											<nested:text property="applicantDetails.journalNo" size="15" maxlength="30"></nested:text>
											</td>
											<td height="24" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.application.date.label" /></div>
											</td>
											<td height="24" class="row-white" align="left">&nbsp;
											<nested:text property="applicantDetails.challanDate" styleId="challanDate" size="15" maxlength="15" readonly="true"></nested:text>
 					<script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'studentEditForm',
								// input name
								'controlname' :'challanDate'
							});
						</script>
											</td>
										</tr>
										<tr class="row-even">
											<td height="19" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.amount" /></div>
											</td>
											<td height="19" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.amount" size="8" maxlength="8"></nested:text>
											</td>
											<td height="19" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.branchCode"/></div></td>
											<td height="19" class="row-even"><div align="left">&nbsp;&nbsp;<nested:text property="applicantDetails.bankBranch"  size="15" maxlength="20"></nested:text></div></td>
											<td class="row-odd" class="row-even"><div align="right"><bean:message key="admissionForm.studentedit.admissiondate.label"/></div></td>
											<td height="19"><div align="left">&nbsp;&nbsp;<nested:text property="applicantDetails.admissionDate" styleId="admDate" size="10" maxlength="10"></nested:text>
												<script language="JavaScript">
													new tcal( {
														// form name
														'formname' :'studentEditForm',
														// input name
														'controlname' :'admDate'
													});
												</script>
									</div></td>
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
												</table>--%>
													<nested:text property="applicantDetails.personalData.firstName" size="30" maxlength="90"></nested:text>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.dob.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.dob" styleId="dateOfBirth" size="11" maxlength="11" ></nested:text>
					                              <script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'studentEditForm',
													// input name
													'controlname' :'dateOfBirth'
												});
											</script>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthplace.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">
												<nested:text property="applicantDetails.personalData.birthPlace" size="15" maxlength="50"></nested:text>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthcountry.label" /></div>
											</td>
											<td height="35" class="row-even" align="left">
												<nested:select property="applicantDetails.personalData.birthCountry" styleClass="combo" styleId="birthCountry" onchange="getStates(this.value,'birthState');">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="studentEditForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
												</nested:select>
											</td>
										</tr>
										<tr>
											<td height="22" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.birthstate.label" /></div>
											</td>
											<td height="22" class="row-even" align="left">
									<%String dynastyle=""; %>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr>
									
									<logic:equal value="Other" property="applicantDetails.personalData.birthState" name="studentEditForm">
										<%dynastyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="applicantDetails.personalData.birthState" name="studentEditForm">
										<%dynastyle="display:none;"; %>
									</logic:notEqual>
													<td><nested:select property="applicantDetails.personalData.birthState"
														styleId="birthState" styleClass="combo"
														onchange="funcOtherShowHide('birthState','otherBirthState')">
														<html:option value="">- Select -</html:option>
														<c:if
															test="${studentEditForm.applicantDetails.personalData.birthCountry!= null && studentEditForm.applicantDetails.personalData.birthCountry != ''}">
															<c:set var="birthStateMap"
																value="${baseActionForm.collectionMap['birthStateMap']}" />
															<c:if test="${birthStateMap != null}">
																<html:optionsCollection name="birthStateMap"
																	label="value" value="key" />
																<html:option value="Other">Other</html:option>
															</c:if>
														</c:if>

													</nested:select></td>
												</tr>
												<tr>
													<td><nested:text property="applicantDetails.personalData.stateOthers" size="10"
														maxlength="30" styleId="otherBirthState"
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
											<td height="35" class="row-even" align="left">
												<nested:select property="applicantDetails.personalData.nationality" styleClass="combo" styleId="nationality">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="nationalities" label="name" value="id"/>
												</nested:select>
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
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.residentcatg.label" /></div>
											</td>
											<td width="56%" height="20" class="row-even" align="left">
												<nested:select property="applicantDetails.personalData.residentCategory" styleClass="combo" styleId="residentCategory">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="residentTypes" label="name" value="id"/>
												</nested:select>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.religion.label" /></div>
											</td>
											<td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr><td>
													
												<input type="hidden" id="religionType" name="religionType" value='<bean:write name="studentEditForm" property="applicantDetails.personalData.religionId"/>'/>
												<logic:notEqual value="Other" property="applicantDetails.personalData.religionId" name="studentEditForm">
													<%dynastyle="display:none;"; %>
												<nested:select property="applicantDetails.personalData.religionId" styleClass="combo" styleId="religions" onchange="getSubReligion(this.value,'subreligion');funcOtherShowHide('religions','otherReligion')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="religions" label="religionName" value="religionId"/>
													<html:option value="Other">Other</html:option>
												</nested:select>
												</logic:notEqual>
												<logic:equal value="Other" property="applicantDetails.personalData.religionId" name="studentEditForm">
													<%dynastyle="display:block;"; %>												
												<nested:select property="applicantDetails.personalData.religionId" styleClass="combo" styleId="religions" onchange="getSubReligion(this.value,'subreligion');funcOtherShowHide('religions','otherReligion')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="religions" label="religionName" value="religionId"/>
													<html:option value="Other">Other</html:option>
												</nested:select>
												
												</logic:equal></td></tr>
												<tr><td><nested:text property="applicantDetails.personalData.religionOthers" size="10" maxlength="30" styleId="otherReligion" style="<%=dynastyle %>"></nested:text></td></tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.subreligion.label" /></div>
											</td>
											<td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              <tr><td><nested:select property="applicantDetails.personalData.subReligionId" styleClass="combo" styleId="subreligion" onchange="funcOtherShowHide('subreligion','otherSubReligion')">
									<html:option value="">- Select -</html:option>
									<c:if test="${studentEditForm.applicantDetails.personalData.religionId != null && studentEditForm.applicantDetails.personalData.religionId != ''}">
			                           					<c:set var="subReligionMap" value="${baseActionForm.collectionMap['subReligionMap']}"/>
		                            		    	 	<c:if test="${subReligionMap != null}">
		                            		    	 		<html:optionsCollection name="subReligionMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                        </c:if>
							</nested:select></td></tr>
							<tr><td><nested:text property="applicantDetails.personalData.religionSectionOthers" size="10" maxlength="30" styleId="otherSubReligion" style="<%=dynastyle %>"></nested:text></td></tr>
							</table>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.castcatg.label" /></div>
											</td>
											<td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              		<tr><td>
									<input type="hidden" id="casteType" name="casteType" value='<bean:write name="studentEditForm" property="applicantDetails.personalData.casteId"/>'/>
									<logic:equal value="Other" property="applicantDetails.personalData.casteId" name="studentEditForm">
										<%dynastyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="applicantDetails.personalData.casteId" name="studentEditForm">
										<%dynastyle="display:none;"; %>
									</logic:notEqual>
                              		<nested:select property="applicantDetails.personalData.casteId" styleId="castCatg" styleClass="combo" onchange="funcOtherShowHide('castCatg','otherCastCatg')">
									<option value="">-Select-</option>
										<html:optionsCollection property="casteList" name="studentEditForm" label="casteName" value="casteId"/>
										
									<html:option value="Other">Other</html:option>
								</nested:select></td></tr>
								<tr><td><nested:text property="applicantDetails.personalData.casteOthers" size="10" maxlength="30" styleId="otherCastCatg" style="<%=dynastyle %>"></nested:text></td></tr>
								</table>
											</td>
										</tr>
										<tr>
											<td height="17" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.belongsto.label" /></div>
											</td>
									<td height="17" class="row-even" align="left"><nested:radio property="applicantDetails.personalData.areaType" value='R'><bean:message key="admissionForm.studentinfo.belongsto.rural.text"/></nested:radio>
											<nested:radio property="applicantDetails.personalData.areaType" value='U'><bean:message key="admissionForm.studentinfo.belongsto.urban.text"/></nested:radio></td>
										</tr>
										<tr>
											<td height="17" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.gender" />:</div>
											</td>
											
								<td height="17" class="row-even" align="left"><nested:radio property="applicantDetails.personalData.gender" value="MALE"><bean:message key="admissionForm.studentinfo.sex.male.text"/></nested:radio>
								<nested:radio property="applicantDetails.personalData.gender" value="FEMALE"><bean:message key="admissionForm.studentinfo.sex.female.text"/></nested:radio></td>
										</tr>
										<tr><%String dynaStyle3="display:none;"; %>
												<logic:equal value="true" property="applicantDetails.personalData.sportsPerson" name="studentEditForm">
												<%dynaStyle3="display:block;"; %>
											</logic:equal>
                           					<td height="17" class="row-odd" width="40%"><div align="right"><bean:message key="admissionForm.edit.sportsperson.label"/></div></td>
                           					<td height="17" class="row-even" align="left"><nested:radio property="applicantDetails.personalData.sportsPerson" value="true" onclick="showSportsDescription()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
											<nested:radio property="applicantDetails.personalData.sportsPerson" value="false" onclick="hideSportsDescription()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
											<div id="sports_description" style="<%=dynaStyle3 %>">
												<nested:text styleId="sportsDescription" property="applicantDetails.personalData.sportsDescription" maxlength="80" size="15"></nested:text>
											</div></td>
                        				</tr>
 										<tr><%String dynaStyle4="display:none;"; %>
											<logic:equal value="true" property="applicantDetails.personalData.handicapped" name="studentEditForm">
											<%dynaStyle4="display:block;"; %>
											</logic:equal>
                           					<td height="17" class="row-odd" width="40%"><div align="right"><bean:message key="admissionForm.edit.handicapped.label"/></div></td>
                           					<td height="17" class="row-even" align="left"><nested:radio property="applicantDetails.personalData.handicapped" value="true" onclick="showHandicappedDescription()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
											<nested:radio property="applicantDetails.personalData.handicapped" value="false" onclick="hideHandicappedDescription()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
											<div id="handicapped_description" style="<%=dynaStyle4 %>">
												<nested:text styleId="hadnicappedDescription" property="applicantDetails.personalData.hadnicappedDescription" maxlength="80" size="15"></nested:text>
											</div>
											</td>
                        				</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.bloodgroup.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
									<nested:select property="applicantDetails.personalData.bloodGroup" styleClass="combo" styleId="bgType">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<html:option value="O+VE"><bean:message key="knowledgepro.admission.report.opositive"/></html:option>
											<html:option value="A+VE"><bean:message key="knowledgepro.admission.report.apositive"/></html:option>
											<html:option value="B+VE"><bean:message key="knowledgepro.admission.report.bpositive"/></html:option>
											<html:option value="AB+VE"><bean:message key="knowledgepro.admission.report.abpositive"/></html:option>
											<html:option value="O-VE"><bean:message key="knowledgepro.admission.report.onegitive"/></html:option>
											<html:option value="A-VE"><bean:message key="knowledgepro.admission.report.anegitive"/></html:option>
											<html:option value="B-VE"><bean:message key="knowledgepro.admission.report.bnegitive"/></html:option>
											<html:option value="AB-VE"><bean:message key="knowledgepro.admission.report.abnegitive"/></html:option>
											<html:option value="NOT KNOWN"><bean:message key="knowledgepro.admission.report.unknown"/></html:option>
									</nested:select>
											</td>
										</tr>
										<logic:equal value="true" property="displaySecondLanguage" name="studentEditForm">
										 <tr >
				                          <td height="20" class="row-odd" width="40%"><div align="right"><bean:message key="knowledgepro.applicationform.secLang.label"/></div></td>
					                      <td height="20" class="row-even" align="left">
				                           <html:select property="applicantDetails.personalData.secondLanguage" styleClass="body" styleId="secondLanguage">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection property="secondLanguageList"
									label="value" value="value" />
							</html:select>
										 </td>
				                        </tr>
										</logic:equal>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><nested:text property="applicantDetails.personalData.phNo1" size="3" maxlength="4"></nested:text></td></tr>
													<tr><td><bean:message key="admissionForm.phone.areacode.label"/></td><td><nested:text property="applicantDetails.personalData.phNo2" size="5" maxlength="7"></nested:text></td></tr>
													<tr><td><bean:message key="admissionForm.phone.main.label"/></td><td><nested:text property="applicantDetails.personalData.phNo3" size="10" maxlength="10"></nested:text></td></tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
			                              	  <tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><nested:text property="applicantDetails.personalData.mobileNo1" size="4" maxlength="4"></nested:text></td></tr>
			                                  <tr><td> <bean:message key="admissionForm.mob.no.label"/> </td><td><nested:text property="applicantDetails.personalData.mobileNo2" size="10" maxlength="10"></nested:text></td></tr>
											</table>
			                                
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
												 <tr><td><nested:text property="applicantDetails.personalData.email" size="15" maxlength="50"></nested:text> </td></tr>
												<tr><td>(e.g. name@yahoo.com)</td></tr>
                                  				</table>
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
	<logic:equal value="true" property="displayExtraDetails" name="studentEditForm">		
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
	                            <logic:equal value="true" property="displayMotherTongue" name="studentEditForm">
								<td height="23" class="row-even"><div align="center"><bean:message key="knowledgepro.applicationform.mothertongue.label"/></div></td>
	                            <td height="23" class="row-even" align="left">
									<nested:select property="applicantDetails.personalData.motherTongue" styleClass="combo" >
										<html:option value="">-Select-</html:option>
										<nested:optionsCollection  property="mothertongues" name="studentEditForm" label="languageName" value="languageId"/>
									</nested:select>
								</td>
								</logic:equal>
								<logic:equal value="true" property="displayHeightWeight" name="studentEditForm">
								 <td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.height.label"/>&nbsp;
									<nested:text property="applicantDetails.personalData.height" size="5" maxlength="5"></nested:text>
								</td>
								<td height="23" class="row-even" ><bean:message key="knowledgepro.applicationform.weight.label"/>&nbsp;
									<nested:text property="applicantDetails.personalData.weight" size="6" maxlength="6"></nested:text>
								</td>
								</logic:equal>
	                          </tr>
							<logic:equal value="true" property="displayLanguageKnown" name="studentEditForm">
							<tr class="row-white">
	                            <td height="23" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.language.label"/></div></td>
	                            <td height="23" class="row-even" align="left" style="width:180px;"><bean:message key="knowledgepro.applicationform.speaklanguage.label"/>&nbsp;
									<nested:text property="applicantDetails.personalData.languageByLanguageSpeak"  size="10" maxlength="50"></nested:text>
								</td>
								<td height="23" class="row-even" style="width:180px;"><bean:message key="knowledgepro.applicationform.readlanguage.label"/>&nbsp;
									<nested:text property="applicantDetails.personalData.languageByLanguageRead" size="10" maxlength="50"></nested:text>
								</td>
								<td height="23" class="row-even" style="width:180px;"><bean:message key="knowledgepro.applicationform.writelanguage.label"/>&nbsp;
									<nested:text property="applicantDetails.personalData.languageByLanguageWrite" size="10" maxlength="50"></nested:text>
								</td>
	                          </tr>
							</logic:equal>

							<logic:equal value="true" property="displayTrainingDetails" name="studentEditForm">
							<tr class="row-white">
	                            <td height="23" colspan="4" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.training.label"/></div></td>
							</tr>
							<tr class="row-white">
	                            <td height="23" class="row-even" align="center"><bean:message key="knowledgepro.applicationform.trainingprog.label"/></td>
								 <td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.trainingProgName" size="10" maxlength="50" ></nested:text>
								</td>
								<td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.trainingduration.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.trainingDuration" size="10" maxlength="10" ></nested:text>
										
								</td>
	                          </tr>
								<tr class="row-white">
	                           
								<td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.traininginst.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:textarea property="applicantDetails.personalData.trainingInstAddress" cols="25" rows="4" ></nested:textarea>
								</td>
								
								<td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.trainingpurpose.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:textarea property="applicantDetails.personalData.trainingPurpose" cols="25" rows="4"></nested:textarea>
										
								</td>
	                         	 </tr>
							</logic:equal>
							
							<logic:equal value="true" property="displayAdditionalInfo" name="studentEditForm">
							<tr class="row-white">
	                            <td height="23" colspan="4" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.addninfo.label"/></div></td>
							</tr>
							<tr class="row-white">
	                            <td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.addninfo.label1"/> <B><bean:write property="organizationName" name="studentEditForm"/></B>:</td>
								 <td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.courseKnownBy" size="20" maxlength="50" ></nested:text>
								</td>
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.strength.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.strength" size="20" maxlength="100" ></nested:text>
										
								</td>
	                          </tr>
								<tr class="row-white">
	                           
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.addninfo.label2"/><B><bean:write property="applicantDetails.course.name" name="studentEditForm"/> </B>:</td>
								<td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.courseOptReason" size="20" maxlength="100"  ></nested:text>
								</td>
								
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.weakness.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.weakness"  size="20" maxlength="100"></nested:text>
										
								</td>
	                         	 </tr>
								<tr class="row-white">
	                           
								<td height="23" colspan="3" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.addninfo.label3"/> <B><bean:write property="applicantDetails.course.name" name="studentEditForm"/></B>:</td>
								<td height="23" class="row-even" align="left">
									<nested:textarea property="applicantDetails.personalData.otherAddnInfo" cols="25" rows="4" ></nested:textarea>
								</td>
								
	                         	 </tr>
							</logic:equal>
							<logic:equal value="true" property="displayExtracurricular" name="studentEditForm">
							<logic:notEmpty property="applicantDetails.personalData.studentExtracurricularsTos" name="studentEditForm">
							<tr class="row-white">
	                            <td height="23" colspan="4" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.extracurr.label"/></div></td>
							</tr>
							<tr class="row-white">
	                            <td height="23" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.extracurr.label2"/></td>
								 <td height="23" class="row-even" align="left">
									
									<nested:select property="applicantDetails.personalData.extracurricularIds" styleClass="row-even" multiple="true" >
										<nested:optionsCollection property="applicantDetails.personalData.studentExtracurricularsTos" name="studentEditForm" label="name" value="id"/>
									</nested:select>
									
								</td>
								 <td height="23" class="row-even" align="left">&nbsp;</td>
								 <td height="23" class="row-even" align="left">&nbsp;</td>
	                          </tr>
								</logic:notEmpty>
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
											<td width="236" height="23" class="row-even" align="left"><nested:text property="applicantDetails.personalData.passportNo" maxlength="15"></nested:text></td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.issuingCountry" />
											</div>
											</td>
											<td width="244" class="row-even" align="left">
												<nested:select property="applicantDetails.personalData.passportCountry" styleClass="combo" styleId="passportCountry">
													<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="studentEditForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
												</nested:select>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.validUpto" /></div>
											</td>
											<td height="25" class="row-white" align="left">
											<nested:text property="applicantDetails.personalData.passportValidity" styleId="passportValidity" styleClass="row-white" size="15" maxlength="15" ></nested:text>
									<script language="JavaScript">
									new tcal( {
										// form name
										'formname' :'studentEditForm',
										// input name
										'controlname' :'passportValidity'
									});
								</script>  
											</td>
											<td class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.residentpermit.label"/></div>
											</td>
											<td class="row-white" align="left">
											<html:text property="applicantDetails.personalData.residentPermitNo" styleId="residentPermit" styleClass="row-white" size="10" maxlength="15" ></html:text>
											</td>
										</tr>
										<tr class="row-white">
											<td width="360" height="23" colspan="2" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.policedate.label"/>
											</div>
											</td>
											<td width="200" height="23" class="row-even" align="left">
											<html:text property="applicantDetails.personalData.residentPermitDate" styleId="permitDate" size="10" maxlength="10"></html:text>
												<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'studentEditForm',
													// input name
													'controlname' :'permitDate'
												});
												</script>
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
								<nested:text property="applicantDetails.vehicleDetail.vehicleType" styleId="vehicleType" maxlength="15"></nested:text>
							</td>
                            <td width="224" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.edit.vehicleno.label"/> </div></td>
                            <td width="244" height="25" class="row-even" align="left"><nested:text property="applicantDetails.vehicleDetail.vehicleNo" styleId="vehicleNo" maxlength="15"></nested:text></td>
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
                            <td width="212" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.admitted.through"/></div></td>
                            <td width="236" height="25" class="row-even" align="left">
								<nested:select property="applicantDetails.admittedThroughId" styleId="admittedThroughId" styleClass="combo">
									<html:option value="">-Select-</html:option>
									<nested:optionsCollection property="admittedThroughList" label="name" value="id"/>
										
								</nested:select>
							</td>
                            <td width="224" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.edit.subgrp.label"/> </div></td>
                            <td width="244" height="25" class="row-even" align="left">
                            	<nested:select property="applicantDetails.subjectGroupIds" styleId="subjectGroupId" styleClass="row-even" multiple="multiple" size="4" style="width:125px">
									<nested:optionsCollection property="subGroupList" label="name" value="id"/>	
								</nested:select>
							</td>
                          </tr>
                           <tr class="row-white">
                            <td width="212" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.edit.freeship.label"/></div></td>
                            <td width="236" height="25" class="row-even" align="left">
								<nested:radio property="applicantDetails.isFreeShip" value="true"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
								<nested:radio property="applicantDetails.isFreeShip" value="false"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
							</td>
                            <td width="224" height="25" class="row-odd" align="right"><bean:message key="knowledgepro.attendance.class.col"/> </td>
                            <td width="244" height="25" class="row-even" align="left">
								<nested:select property="classSchemeId" styleId="classSchemeId" styleClass="combo">
									<html:option value="0">-Select-</html:option>
									<nested:optionsCollection property="classesMap" label="value" value="key"/>	
								</nested:select>
							</td>
                          </tr>
							<tr class="row-white">
                            <td width="212" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.edit.lig.label"/></div></td>
                            <td width="236" height="25" class="row-even" align="left">
								<nested:radio property="applicantDetails.isLIG" value="true"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
								<nested:radio property="applicantDetails.isLIG" value="false"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
							</td>
                            <td width="224" height="25" class="row-odd"><div align="right"></td>
                            <td width="244" height="25" class="row-even" align="left">
								
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
						

	<logic:equal value="true" property="workExpNeeded" name="studentEditForm">
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
                          <%String dynaStyle=""; %>
                          <tr class="row-white">
                            <td height="27" class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.applicationform.orgName.label"/></div></td>
                            <td height="27" class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.address"/></div></td>
                            <td height="27" class="row-odd" width="15%"><div align="center"><bean:message key="admissionForm.phone.main.label"/></div></td>
                            <td height="27" class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.applicationform.jobdesc.label"/></div></td>
                            <td class="row-odd" width="15%"><div align="center"> <bean:message key="knowledgepro.applicationform.fromdt.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/></div></td>
                            <td class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.applicationform.todt.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/></div></td>
							<td height="27" class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.applicationform.lastsal.label"/></div></td>
							
                          </tr>
						<nested:iterate property="applicantDetails.workExpList" id="exp" indexId="count">
							<%String fromid="expFromdate"+count; 
								String toid="expTodate"+count;
								String occId="occupation"+count;
								String dropOccId="occupationId"+count;
								String occupationShowHide="funcOtherOccupationShowHide('"+dropOccId+"','"+occId+"','"+count+"');";
								
							%>
                          <tr class="row-white">
                            <td height="27" class="row-even"><div align="left">
								<nested:text property="organization" styleClass="textbox" size="15" maxlength="100" />
                            </div></td>
                            <td height="27" class="row-even"><div align="center">
								<nested:text property="address" styleClass="textbox" size="10" maxlength="40" />
                            </div></td>
                            <td  height="27" class="row-even"><div align="center">
								<nested:text property="phoneNo" styleClass="textbox" size="10" maxlength="15" />
                            </div></td>
                            <td height="27" class="row-even"><div align="center">
                            
                            <logic:equal value="Other" name="exp" property="occupationId">
                            <nested:select property="occupationId" styleClass="combo" styleId="<%=dropOccId %>" onchange="<%=occupationShowHide %>">
								<html:option value="">- Select -</html:option>
								<html:optionsCollection name="studentEditForm" property="occupations" label="occupationName" value="occupationId"/>
								<option value="Other" selected="selected">Other</option>
							</nested:select>
		                	<%dynaStyle="display: block;" ;%>
							</logic:equal>
							<logic:notEqual value="Other" name="exp" property="occupationId">
		                  	<nested:select property="occupationId" styleClass="combo" styleId="<%=dropOccId %>" onchange="<%=occupationShowHide %>">
								<html:option value="">- Select -</html:option>
								<html:optionsCollection name="studentEditForm" property="occupations" label="occupationName" value="occupationId"/>
								<option value="Other">Other</option>
							</nested:select>
		                  	<%dynaStyle="display: none;" ;%>
							</logic:notEqual>
								<nested:text property="position" styleClass="textbox" size="10" maxlength="30" styleId='<%=occId %>' style='<%=dynaStyle %>'/>
                            </div></td>
                            <td width="224" class="row-even"><div align="center">
                             <nested:text property="fromDate" styleId="<%=fromid%>" size="10" maxlength="10"  />
                              <script language="JavaScript">
								new tcal ({
									// form name
									'formname': 'studentEditForm',
									// input name
									'controlname': '<%=fromid%>'
								});</script>
                            </div></td>
                            <td class="row-even"><div align="center">
                             <nested:text property="toDate" styleId="<%=toid%>" size="10" maxlength="10"  />
                              <script language="JavaScript">
							new tcal ({
								// form name
								'formname': 'studentEditForm',
								// input name
								'controlname': '<%=toid%>'
							});</script>
                            </div></td>
							<td  height="27" class="row-even"><div align="center">
								<nested:text property="salary" styleClass="textbox" size="10" maxlength="10" />
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
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="236" height="20" class="row-even" align="left">&nbsp;
												<nested:text property="applicantDetails.personalData.currentAddressLine1" size="15" maxlength="100"></nested:text>
											</td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp;
												<nested:text property="applicantDetails.personalData.currentAddressLine2" size="15" maxlength="100"></nested:text>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.currentCityName" size="15" maxlength="30"></nested:text>
											</td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;
												<nested:select property="applicantDetails.personalData.currentCountryId" styleClass="combo" styleId="currentCountryName" onchange="getTempAddrStates(this.value,'tempAddrstate');">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="studentEditForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
											</nested:select>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;
											<logic:equal value="Other" property="applicantDetails.personalData.currentStateId" name="studentEditForm">
										<%dynastyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="applicantDetails.personalData.currentStateId" name="studentEditForm">
										<%dynastyle="display:none;"; %>
									</logic:notEqual>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td>&nbsp;&nbsp;<nested:select property="applicantDetails.personalData.currentStateId" styleClass="combo" styleId="tempAddrstate" onchange="funcOtherShowHide('tempAddrstate','otherTempAddrState')">
									<html:option value="">- Select -</html:option>
									<c:if test="${studentEditForm.applicantDetails.personalData.currentCountryId != null && studentEditForm.applicantDetails.personalData.currentCountryId!= ''}">
			                           					<c:set var="tempAddrStateMap" value="${baseActionForm.collectionMap['tempAddrStateMap']}"/>
		                            		    	 	<c:if test="${tempAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="tempAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                        </c:if>
									</nested:select></td></tr>
							<tr><td><nested:text property="applicantDetails.personalData.currentAddressStateOthers" size="10" maxlength="30" styleId="otherTempAddrState" style="<%=dynastyle %>"></nested:text></td></tr>
							</table>
											</td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;&nbsp;<nested:text property="applicantDetails.personalData.currentAddressZipCode" size="10" maxlength="10"></nested:text>
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
                  	<td colspan="2" class="heading">&nbsp;&nbsp;<bean:message key="knowledgepro.applicationform.sameaddr.label"/>
                      <html:radio property="sameTempAddr" styleId="sameAddr" value="true" onclick="disableTempAddress()"><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
						<html:radio property="sameTempAddr" styleId="DiffAddr" value="false" onclick="enableTempAddress()"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio></td>
                	</tr>
						<tr>
							<td colspan="2" class="heading" align="left" >&nbsp;
							<div id="currLabel">
							<bean:message
								key="admissionForm.studentinfo.permAddr.label" />
							</div>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0" id="currTable">
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
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="236" height="20" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.permanentAddressLine1" size="15" maxlength="100"></nested:text>
											</td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.permanentAddressLine2" size="15" maxlength="100"></nested:text>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.permanentCityName" size="15" maxlength="30"></nested:text>
											</td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;
											<nested:select property="applicantDetails.personalData.permanentCountryId" styleClass="combo" styleId="permanentCountryName" onchange="getPermAddrStates(this.value,'permAddrstate');">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="studentEditForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
											</nested:select>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;
												<logic:equal value="Other" property="applicantDetails.personalData.permanentStateId" name="studentEditForm">
												<%dynastyle="display:block;"; %>
												</logic:equal>
											<logic:notEqual value="Other" property="applicantDetails.personalData.permanentStateId" name="studentEditForm">
												<%dynastyle="display:none;"; %>
												</logic:notEqual>
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td>&nbsp;&nbsp;<nested:select property="applicantDetails.personalData.permanentStateId" styleClass="combo" styleId="permAddrstate" onchange="funcOtherShowHide('permAddrstate','otherPermAddrState')">
									<html:option value="">- Select -</html:option>
									<c:if test="${studentEditForm.applicantDetails.personalData.permanentCountryId != null && studentEditForm.applicantDetails.personalData.permanentCountryId!= ''}">
			                           					<c:set var="permAddrStateMap" value="${baseActionForm.collectionMap['permAddrStateMap']}"/>
		                            		    	 	<c:if test="${permAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="permAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                        </c:if>
							</nested:select></td></tr>
							<tr><td><nested:text property="applicantDetails.personalData.permanentAddressStateOthers" size="10" maxlength="30" styleId="otherPermAddrState" style="<%=dynastyle %>"></nested:text></td></tr>
							</table>
											</td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.permanentAddressZipCode" size="10" maxlength="10"></nested:text>
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
									<logic:notEmpty name="studentEditForm" property="applicantDetails.ednQualificationList">
										<tr>
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.qualification" /></td>
											<td class="row-odd">Exam Name</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.universityBoard" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.instituteName" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="admissionForm.education.State.label" /></div>
											</td>
											<td class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.passingYear" /></td>
											<td class="row-odd" align="center"><bean:message key="knowledgepro.applicationform.passingmonth"/></td>
											
											<td class="row-odd" align="center">
											<div align="center"><bean:message
												key="knowledgepro.admission.attempts" /></div>
											</td>
											<td class="row-odd"><bean:message key="knowledgepro.applicationform.prevregno.label"/></td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.marksObtained" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.maxMark" /></div>
											</td>
											
										</tr>
										</logic:notEmpty>
        <nested:iterate  name="studentEditForm" property="applicantDetails.ednQualificationList" indexId="count" id="qualDoc">
			<%
				String dynamicStyle="";
				String oppStyle="";
				String dynaid="UniversitySelect"+count;
				String dynaYearId="YOP"+count;
				String dynamonthId="Month"+count;
				String dynaExamId="Exam"+count;
				String dynaAttemptId="Attempt"+count;
				String dynaStateId="State"+count;
				String dynarow1="University"+count;
				String dynarow2="Institute"+count;
				String instituteId=count+"Institute";
				String collegeJsMethod="getColleges('Map"+count+"',this,"+count+");";
				String instituteJsMethod="funcOtherInstituteShowHide('"+instituteId+"','"+dynarow2+"','"+count+"');";
				String showhide=collegeJsMethod+"funcOtherUniversityShowHide('"+dynarow1+"','"+dynaid+"','"+dynarow2+"','"+instituteId +"');";
				//String dateproperty="qualifications["+count+"].yearPassing";
				if(count%2!=0){
					dynamicStyle="row-white";
					oppStyle="row-even";
				}else{
					dynamicStyle="row-even";
					oppStyle="row-white";
				}
				String dynaMap="Map"+count;
				
			%>
			<bean:define id="countIndex" name="qualDoc" property="countId"></bean:define>
			<input type="hidden" id="countID" name="countID" >
          <tr >
            <td height="25" class='<%=dynamicStyle %>'><div align="center"><%=count+1 %></div></td>
            <td height="25" class='<%=dynamicStyle %>'><nested:write property="docName"/> </td>
			<td width="9%" class="<%=dynamicStyle %>"><span class='<%=oppStyle %>'>
				
					<logic:equal value="true" name="qualDoc" property="examConfigured">
					<c:set var="dexmid"><%=dynaExamId %></c:set>
				<nested:select property="selectedExamId" styleClass="comboSmall" styleId='<%=dynaExamId %>'>
					<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					<logic:notEmpty name="qualDoc" property="examTos">
						<html:optionsCollection name="qualDoc" property="examTos" label="name" value="id"/>
					</logic:notEmpty>	
				</nested:select>
					<script type="text/javascript">
						var exmid= '<nested:write property="selectedExamId"/>';
						document.getElementById("<c:out value='${dexmid}'/>").value = exmid;
					</script>
					</logic:equal>
				
				</span></td>

            <td class='<%=dynamicStyle %>' >
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
				<td  style=" padding-top: 30Px;">
               <c:set var="dunid"><%=dynaid %></c:set>
               <nested:select property="universityId" styleId="<%=dynaid %>" styleClass="combo" onchange="<%=showhide %>">
					<option value="">Select</option>
					<logic:iterate id="option" property="universities" name="studentEditForm">
						<option value='<bean:write name="option" property="id"/>'><bean:write name="option" property="name"/> </option>
					</logic:iterate>
						<option value="Other">Other</option>
               </nested:select>
					<script type="text/javascript">
					var id= '<nested:write property="universityId"/>';
					document.getElementById("<c:out value='${dunid}'/>").value = id;
					</script>
            	</td>
				</tr>
				<tr>
					<%String dynaStyle="" ;%>
                  <td width="123" height="30">
					<logic:equal value="Other" name="qualDoc" property="universityId">
                	<%dynaStyle="display: block;" ;%>
					</logic:equal>
					<logic:notEqual value="Other" name="qualDoc" property="universityId">
                  	<%dynaStyle="display: none;" ;%>
					</logic:notEqual>
  					<nested:text property="universityOthers" size="10" maxlength="50" style='<%=dynaStyle %>' styleId='<%=dynarow1 %>'></nested:text>
                  </td>
                  <td width="217">&nbsp;</td>
                </tr>
			</table></td>
            <td class='<%=dynamicStyle %>' ><table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
				<td  style=" padding-top: 30Px;">
				<c:set var="dinid"><%=instituteId %></c:set>
                <c:set var="temp"><nested:write property="universityId"/></c:set>
	                <nested:select property="institutionId" styleClass="combo" styleId='<%=instituteId %>' onchange='<%=instituteJsMethod %>' >
					<option value="">-Select-</option>
						<c:set var="tempKey">Map<c:out value="${count}"/></c:set>	
						<c:if test="${temp != null && temp != '' && temp != 'Other'}">
                             <c:set var="Map" value="${baseActionForm.collectionMap[tempKey]}"/>
							<html:optionsCollection name="Map" label="value" value="key"/>
							
						</c:if>
						<html:option value="Other">Other</html:option>
					</nested:select>
					<script type="text/javascript">
					var inId= '<nested:write property="institutionId"/>';
					document.getElementById("<c:out value='${dinid}'/>").value = inId;
					</script>
	           </td>
				</tr>
			
				 <tr >
                  <td width="150" height="30">
					<logic:equal value="Other" name="qualDoc" property="institutionId">
                  	<%dynaStyle="display: block;" ;%>
					</logic:equal>
					<logic:notEqual value="Other" name="qualDoc" property="institutionId">
                  	<%dynaStyle="display: none;" ;%>
					</logic:notEqual>
					<nested:text property="otherInstitute" size="10" maxlength="50" style='<%=dynaStyle %>' styleId='<%=dynarow2 %>'></nested:text>
                  </td>
                
                </tr>
            </table>
			</td>
			<td width="16%" class='<%=dynamicStyle %>'>
				<c:set var="dstateid"><%=dynaStateId %></c:set>
			<nested:select property="stateId" styleClass="combo" styleId='<%=dynaStateId %>'>
				<option value=""><bean:message key="knowledgepro.admin.select"/></option>
				<logic:notEmpty name="studentEditForm" property="ednStates">
				 <nested:optionsCollection name="studentEditForm" property="ednStates" label="name" value="id"/>
				</logic:notEmpty>
				<option value="OUTSIDEINDIA"><bean:message key="admissionForm.education.outsideindia.label"/></option>
			</nested:select>
				<script type="text/javascript">
					var stid= '<nested:write property="stateId"/>';
					document.getElementById("<c:out value='${dstateid}'/>").value = stid;
				</script>
			</td>

            <td class='<%=dynamicStyle %>'  ><span class='<%=oppStyle %>'>
				<c:set var="dyopid"><%=dynaYearId %></c:set>
			<nested:select property="yearPassing" styleId='<%=dynaYearId %>' styleClass="comboSmall">
				<html:option value="">Select</html:option>
              	<cms:renderYear normalYear="true"></cms:renderYear>
			</nested:select>
				<script type="text/javascript">
					var yopid= '<nested:write property="yearPassing"/>';
					document.getElementById("<c:out value='${dyopid}'/>").value = yopid;
				</script>
             </span></td>
			 <td class='<%=dynamicStyle %>'  ><span class='<%=oppStyle %>'>
				<c:set var="dmonid"><%=dynamonthId %></c:set>
			<nested:select property="monthPassing" styleId='<%=dynamonthId %>' styleClass="comboSmall">
				<html:option value="0">Select</html:option>
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
			</nested:select>
				<script type="text/javascript">
					var monid= '<nested:write property="monthPassing"/>';
					document.getElementById("<c:out value='${dmonid}'/>").value = monid;
				</script>
             </span></td>
            <td class='<%=dynamicStyle %>' ><div align="center"><span class='<%=oppStyle %>'>
				<c:set var="dAttemptid"><%=dynaAttemptId %></c:set>
                <nested:select property="noOfAttempts" styleId='<%=dynaAttemptId %>' styleClass="comboSmall">
					 <option value="">Select</option>
					 <option value="1">1</option>
					 <option value="2">2</option>
					 <option value="3">3</option>
					 <option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
                </nested:select>
				<script type="text/javascript">
					var dAttemid= '<nested:write property="noOfAttempts"/>';
					document.getElementById("<c:out value='${dAttemptid}'/>").value = dAttemid;
				</script>
            </span></div></td>
			
				 <td class='<%=dynamicStyle %>' ><div align="center"><span class='<%=oppStyle %>'>
			<logic:equal value="true" name="qualDoc" property="lastExam">
				<nested:text property="previousRegNo" size="10" maxlength="15"/></logic:equal>
            </span></div></td>
				<nested:equal value="false"  property="consolidated">
				<nested:equal value="true"  property="semesterWise">
					<%String detailSemesterLink="studentEdit.do?method=initSemesterMarkPage&countID="+countIndex; %>
          		  	<td colspan="2" class='<%=dynamicStyle %>' ><div align="center" class="body"><span class="Mandatory">*</span><a href="#" onclick="detailSemesterSubmit('<%=countIndex %>')"><bean:message key="knowledgepro.applicationform.semsestermark.link"/></a></div></td>
				</nested:equal>
				<nested:equal value="false"  property="semesterWise">
				<%String detailMarkLink="studentEdit.do?method=initDetailMarkPage&countID="+countIndex; %>
	          		  <td colspan="2" class='<%=dynamicStyle %>' ><div align="center" class="body"><span class="Mandatory">*</span><a href="#" onclick="detailSubmit('<%=countIndex %>')" ><bean:message key="knowledgepro.applicationform.detailmark.link"/></a></div></td>
				</nested:equal>
			</nested:equal>
			
			<nested:equal value="true" property="consolidated">
          	<td width="9%" class='<%=dynamicStyle %>'><div align="center">
              <nested:text  property="marksObtained" size="5" maxlength="8"></nested:text>
            </div></td>
            <td width="8%" class='<%=dynamicStyle %>'><div align="center">
              <nested:text property="totalMarks" size="5" maxlength="8"></nested:text>
            </div></td>
			</nested:equal>
			
          </tr>
			
		</nested:iterate>
									<logic:equal value="true" property="displayTCDetails" name="studentEditForm">
									<tr>
											<td height="25" class="row-odd">
											<div align="center"><bean:message key="admissionForm.education.TCNO.label"/></div>
											</td>
											<td height="25" class="row-even"><nested:text property="applicantDetails.tcNo" size="6" maxlength="10"></nested:text></td>
											<td class="row-odd">
											<div align="left"><bean:message key="admissionForm.education.TCDate.label"/></div>
											</td>
											<td class="row-even" colspan="2">
											<div align="left"><nested:text property="applicantDetails.tcDate" styleId="tcdate" size="10" maxlength="10"></nested:text><script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'studentEditForm',
													// input name
													'controlname' :'tcdate'
												});
											</script> </div>
											</td>
											
											<td class="row-odd"><bean:message key="admissionForm.education.markcard.label"/></td>
											<td class="row-even">
											<div align="center"><nested:text property="applicantDetails.markscardNo" size="6" maxlength="10"></nested:text></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message key="admissionForm.education.markcarddate.label"/></div>
											</td>
											<td class="row-even" colspan="2">
											<div align="left"><nested:text property="applicantDetails.markscardDate" styleId="markscardDate" size="10" maxlength="10"></nested:text>
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
								<logic:equal value="true" property="displayLateralTransfer"	name="studentEditForm">
											<tr>
												<td class="row-even">
												<div align="center">&nbsp;</div>
												</td>
												<td height="25" colspan="2" class="row-even"><logic:equal
													value="true" property="displayLateralDetails"
													name="studentEditForm">
													<div align="center"><a href="#"
														onclick="detailLateralSubmit()"><bean:message key="admissionForm.education.laterallink.label"/></a></div>

												</logic:equal></td>
												<td height="25" colspan="2" class="row-even">
												<div align="center">&nbsp;</div>
												</td>
												<td class="row-even" colspan="2"><logic:equal
													value="true" property="displayTransferCourse"
													name="studentEditForm">
													<div align="center"><a href="#" onclick="detailTransferSubmit()"><bean:message key="admissionForm.education.transferlink.label"/></a></div>
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

				<logic:equal value="true" property="displayEntranceDetails" name="studentEditForm">

					<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message key="admissionForm.education.entrancedetails.label"/> </td>
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
												<nested:select property="applicantDetails.entranceDetail.entranceId" styleClass="comboLarge">
													<html:option value="">-Select-</html:option>
													<logic:notEmpty property="entranceList" name="studentEditForm">
													<html:optionsCollection property="entranceList" name="studentEditForm" label="name" value="id"/>
													</logic:notEmpty>
										
												</nested:select>
													</div></td>
											<td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.totalMarks"/>:</div></td>
											<td width="16%" height="25" class="row-even"><div align="left"><nested:text property="applicantDetails.entranceDetail.totalMarks" size="20" maxlength="8"></nested:text></div></td>
											<td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.education.markObtained.label"/>:</div></td>
											<td width="16%" height="25" class="row-even"><div align="left"><nested:text property="applicantDetails.entranceDetail.marksObtained" size="20" maxlength="8"></nested:text></div></td>
           								 </tr>
										<tr >
            								<td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.prereq.roll.label"/></div></td>
											<td height="25" class="row-even"><div align="left"><nested:text property="applicantDetails.entranceDetail.entranceRollNo" size="25" maxlength="25"></nested:text></div></td>
											<td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.passingmonth"/>:</div></td>
											<td height="25" class="row-even"><div align="left">
											<nested:select property="applicantDetails.entranceDetail.monthPassing"  styleClass="comboMedium">
												<html:option value="0"><bean:message key="knowledgepro.admin.select"/></html:option>
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
											</nested:select>
											</div></td>
											<td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.passingYear"/>:</div></td>
											<td height="25" class="row-even"><div align="left">
											<nested:select property="applicantDetails.entranceDetail.yearPassing" styleId='entranceyear' styleClass="comboMedium">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
								              	<cms:renderYear normalYear="true"></cms:renderYear>
											</nested:select>
												<script type="text/javascript">
													var entyopid= '<nested:write property="applicantDetails.entranceDetail.yearPassing"/>';
													document.getElementById("entranceyear").value = entyopid;
												</script>
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
											<nested:text property="applicantDetails.personalData.fatherName" size="15" maxlength="50"></nested:text>
											</td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
												<nested:text property="applicantDetails.personalData.fatherEducation" size="15" maxlength="50"></nested:text>
											</td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message key="admissionForm.parentinfo.currency.label"/></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											<nested:select property="applicantDetails.personalData.fatherCurrencyId" styleClass="combo" styleId="fatherCurrency">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection property="currencyList" label="name" value="id"/>
											</nested:select>
											</td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="admissionFormForm.fatherIncome" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											<nested:select property="applicantDetails.personalData.fatherIncomeId" styleClass="combo" styleId="fatherIncomeRange">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection property="incomeList" label="incomeRange" value="id"/>
											</nested:select>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;<nested:select property="applicantDetails.personalData.fatherOccupationId" styleClass="combo" styleId="motherOccupation">
													<html:option value="">- Select -</html:option>
													<html:optionsCollection name="studentEditForm" property="occupations" label="occupationName" value="occupationId"/>
												</nested:select></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="30" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
												 <tr><td><nested:text property="applicantDetails.personalData.fatherEmail" size="15" maxlength="50"></nested:text></td></tr>
												<tr><td>(e.g. name@yahoo.com)</td></tr>
                               			  		 </table>
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
											<nested:text property="applicantDetails.personalData.motherName" size="15" maxlength="50"></nested:text>
											</td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.motherEducation" size="15" maxlength="50"></nested:text>
											</td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message key="admissionForm.parentinfo.currency.label"/></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											<nested:select property="applicantDetails.personalData.motherCurrencyId" styleClass="combo" styleId="motherCurrency">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection property="currencyList" label="name" value="id"/>
											</nested:select>
											</td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="admissionFormForm.fatherIncome" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											<nested:select property="applicantDetails.personalData.motherIncomeId" styleClass="combo" styleId="motherIncomeRange">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection property="incomeList" label="incomeRange" value="id"/>
											</nested:select>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;
												  <nested:select property="applicantDetails.personalData.motherOccupationId" styleClass="combo" styleId="motherOccupation">
													<html:option value="">- Select -</html:option>
													<html:optionsCollection name="studentEditForm" property="occupations" label="occupationName" value="occupationId"/>
												</nested:select>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="30" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr><td><nested:text property="applicantDetails.personalData.motherEmail" size="15" maxlength="50"></nested:text></td></tr>
											<tr><td>(e.g. name@yahoo.com)</td></tr>
                                 			 </table>
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

								<logic:equal value="true" property="displayFamilyBackground" name="studentEditForm">
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
											<nested:text property="applicantDetails.personalData.brotherName" size="15" maxlength="100"></nested:text>
											</td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
												<nested:text property="applicantDetails.personalData.brotherEducation" size="15" maxlength="100"></nested:text>
											</td>
										</tr>
										
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.income" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.brotherIncome" size="15" maxlength="100"></nested:text>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.brotherOccupation" size="15" maxlength="100"></nested:text></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.age.label"/></div>
											</td>
											<td height="30" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.brotherAge" size="15" maxlength="50"></nested:text>
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
											<nested:text property="applicantDetails.personalData.sisterName" size="15" maxlength="100"></nested:text>
											</td>
										</tr>
										<tr class="row-odd">
											<td width="45%" height="36" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.education" /></div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.sisterEducation" size="15" maxlength="100"></nested:text>
											</td>
										</tr>
										
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.income" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.sisterIncome" size="15" maxlength="100"></nested:text>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.occupation" />:</div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;
												  <nested:text property="applicantDetails.personalData.sisterOccupation" size="15" maxlength="100"></nested:text>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.age.label"/></div>
											</td>
											<td height="30" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.sisterAge" size="15" maxlength="50"></nested:text>
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
											<nested:text property="applicantDetails.personalData.parentAddressLine1" size="15" maxlength="100" ></nested:text>
											</td>
											<td width="100" height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td width="190" height="20" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.parentCityName" size="15" maxlength="30"></nested:text>
											</td>
											<td width="122" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td width="206" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.parentAddressZipCode" size="10" maxlength="10"></nested:text>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.parentAddressLine2" size="15" maxlength="100"></nested:text>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" />
											</div>
											</td>
											<td class="row-white" align="left">
											<nested:select property="applicantDetails.personalData.parentCountryId" styleClass="combo" styleId="parentCountryName" onchange="getParentAddrStates(this.value,'parentState')">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="studentEditForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
											</nested:select>
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><nested:text property="applicantDetails.personalData.parentPh1" size="3" maxlength="4"></nested:text></td></tr>
												<tr><td><bean:message key="admissionForm.phone.areacode.label"/></td><td><nested:text property="applicantDetails.personalData.parentPh2" size="5" maxlength="7"></nested:text></td></tr>
												<tr><td><bean:message key="admissionForm.phone.main.label"/></td><td><nested:text property="applicantDetails.personalData.parentPh3" size="10" maxlength="10"></nested:text></td></tr>
											</table>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs3.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:text property="applicantDetails.personalData.parentAddressLine3" size="15" maxlength="100"></nested:text>
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" align="left">&nbsp;
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td>
							<%String dynaStyle=""; %>
								<logic:equal value="Other" property="applicantDetails.personalData.parentStateId" name="studentEditForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="applicantDetails.personalData.parentStateId" name="studentEditForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                  	<nested:select property="applicantDetails.personalData.parentStateId" styleClass="combo" styleId="parentState" onchange="funcOtherShowHide('parentState','otherParentAddrState');">
									<html:option value="">- Select -</html:option>
									<c:if test="${studentEditForm.applicantDetails.personalData.parentCountryId != null && studentEditForm.applicantDetails.personalData.parentCountryId!= ''}">
			                           					<c:set var="parentAddrStateMap" value="${baseActionForm.collectionMap['parentAddrStateMap']}"/>
		                            		    	 	<c:if test="${parentAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="parentAddrStateMap" label="value" value="key"/>
		                            		    	 	</c:if> 
			                        </c:if>
							</nested:select></td></tr>
							<tr><td><nested:text property="applicantDetails.personalData.parentAddressStateOthers" size="10" maxlength="30" styleId="otherParentAddrState" style="<%=dynaStyle %>"></nested:text></td></tr>
							</table>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" align="left" class="row-even">
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
			                              	  <tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><nested:text property="applicantDetails.personalData.parentMob1" size="4" maxlength="4"></nested:text></td></tr>
			                                  <tr><td> <bean:message key="admissionForm.mob.no.label"/> </td><td><nested:text property="applicantDetails.personalData.parentMob2" size="10" maxlength="10"></nested:text></td></tr>
											</table>
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
												key="knowledgepro.applicationform.guardianname.label" /></div>
											</td>
											<td width="468" height="20" class="row-even" align="left" colspan="3">&nbsp;
											<nested:text property="applicantDetails.personalData.guardianName" size="50" maxlength="100" ></nested:text>
											</td>
											
											<td width="122" class="row-odd">
											</td>
											<td width="206" class="row-even" align="left">
											</td>
										</tr>
										<tr class="row-white">
											<td width="113" height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="178" height="20" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.guardianAddressLine1" size="15" maxlength="100" ></nested:text>
											</td>
											<td width="100" height="20" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td width="190" height="20" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.cityByGuardianAddressCityId" size="15" maxlength="30"></nested:text>
											</td>
											<td width="122" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td width="206" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.guardianAddressZipCode" size="10" maxlength="10"></nested:text>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.guardianAddressLine2" size="15" maxlength="100"></nested:text>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" />
											</div>
											</td>
											<td class="row-white" align="left">
											<nested:select property="applicantDetails.personalData.countryByGuardianAddressCountryId" styleClass="combo" styleId="guardianCountryName" onchange="getParentAddrStates(this.value,'guardianState')">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="studentEditForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
											</nested:select>
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><nested:text property="applicantDetails.personalData.guardianPh1" size="3" maxlength="4"></nested:text></td></tr>
												<tr><td><bean:message key="admissionForm.phone.areacode.label"/></td><td><nested:text property="applicantDetails.personalData.guardianPh2" size="5" maxlength="7"></nested:text></td></tr>
												<tr><td><bean:message key="admissionForm.phone.main.label"/></td><td><nested:text property="applicantDetails.personalData.guardianPh3" size="10" maxlength="10"></nested:text></td></tr>
											</table>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs3.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;<nested:text property="applicantDetails.personalData.guardianAddressLine3" size="15" maxlength="100"></nested:text>
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" align="left">&nbsp;
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td>
							<%String dynaStyle1=""; %>
								<logic:equal value="Other" property="applicantDetails.personalData.stateByGuardianAddressStateId" name="studentEditForm">
										<%dynaStyle1="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="applicantDetails.personalData.stateByGuardianAddressStateId" name="studentEditForm">
										<%dynaStyle1="display:none;"; %>
									</logic:notEqual>
                  	<nested:select property="applicantDetails.personalData.stateByGuardianAddressStateId" styleClass="combo" styleId="guardianState" onchange="funcGuardianOtherShowHide('guardianState','otherGuardianAddrState');">
									<html:option value="">- Select -</html:option>
									<c:if test="${studentEditForm.applicantDetails.personalData.countryByGuardianAddressCountryId != null && studentEditForm.applicantDetails.personalData.countryByGuardianAddressCountryId!= ''}">
			                           					<c:set var="guardianAddrStateMap" value="${baseActionForm.collectionMap['guardianAddrStateMap']}"/>
		                            		    	 	<c:if test="${guardianAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="guardianAddrStateMap" label="value" value="key"/>
		                            		    	 	</c:if> 
			                        </c:if>
							</nested:select></td></tr>
							<tr><td><nested:text property="applicantDetails.personalData.guardianAddressStateOthers" size="10" maxlength="30" styleId="otherGuardianAddrState" style="<%=dynaStyle1 %>"></nested:text></td></tr>
							</table>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" align="left" class="row-even">
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
			                              	  <tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><nested:text property="applicantDetails.personalData.guardianMob1" size="4" maxlength="4"></nested:text></td></tr>
			                                  <tr><td> <bean:message key="admissionForm.mob.no.label"/> </td><td><nested:text property="applicantDetails.personalData.guardianMob2" size="10" maxlength="10"></nested:text></td></tr>
											</table>
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
											<td height="25" class="row-odd">
											<div align="center"><bean:message key="admissionForm.edit.na.label"/> </div>
											</td>
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.documents" /></td>
											<td class="row-odd">&nbsp;</td>
											<td class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.uploadDocs" /></td>
										</tr>
										<c:set var="temp" value="0" />
										<nested:iterate name="studentEditForm"
											property="applicantDetails.editDocuments" indexId="count" id="docList">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td width="8%" height="25" class="row-even">
														<div align="center">
														 <input type="hidden" id="selected_<c:out value='${count}'/>" name="selected_<c:out value='${count}'/>" value="<nested:write name='docList' property='temphardSubmitted'/>"/>
														<%--<nested:checkbox property="hardSubmitted"></nested:checkbox>--%>
														<input type="checkbox" id="selected1_<c:out value='${count}'/>" name="applicantDetails.editDocuments[<c:out value='${count}'/>].hardSubmitted" onclick="unselectApplicable('<c:out value="${count}"/>')"/>
														<script type="text/javascript">
															var selectedId = document.getElementById("selected_<c:out value='${count}'/>").value;
															if(selectedId == "true") {
																	document.getElementById("selected1_<c:out value='${count}'/>").checked = true;
															}		
														</script>
														</div>
														</td>
															<td width="8%" height="25" class="row-even">
														<div align="center">
														 <input type="hidden" id="selected_not_applicable_<c:out value='${count}'/>" name="selected_<c:out value='${count}'/>" value="<nested:write name='docList' property='tempNotApplicable'/>"/>
														<%--<nested:checkbox property="hardSubmitted"></nested:checkbox>--%>
														<input type="checkbox" id="selected1_not_applicable_<c:out value='${count}'/>" name="applicantDetails.editDocuments[<c:out value='${count}'/>].notApplicable" onclick="unselectHardSubmit('<c:out value="${count}"/>')"/>
														<script type="text/javascript">
															var selected_not_applicable_Id = document.getElementById("selected_not_applicable_<c:out value='${count}'/>").value;
															if(selected_not_applicable_Id == "true") {
																	document.getElementById("selected1_not_applicable_<c:out value='${count}'/>").checked = true;
																	document.getElementById("selected1_<c:out value='${count}'/>").checked = false;
															}		
														</script>
														</div>
														</td>
														<td width="18%" height="25" class="row-even" align="center"><nested:write
															property="name" /></td>
														<td width="28%" class="row-even"><nested:equal
															value="true" property="documentPresent">
															<a
																href="javascript:downloadFile('<nested:write property="id"/>')"><bean:message key="knowledgepro.view" /></a>
														</nested:equal></td>
														<td width="46%" class="row-even">
															<nested:file property="editDocument"></nested:file>
														</td>
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td height="25" class="row-white">
														<div align="center"> <input type="hidden" id="selected_<c:out value='${count}'/>" name="selected_<c:out value='${count}'/>" value="<nested:write name='docList' property='temphardSubmitted'/>"/>
														<%--<nested:checkbox property="hardSubmitted"></nested:checkbox>--%>
														<input type="checkbox" id="selected1_<c:out value='${count}'/>" name="applicantDetails.editDocuments[<c:out value='${count}'/>].hardSubmitted" onclick="unselectApplicable('<c:out value="${count}"/>')"/>
														<script type="text/javascript">
															selectedId = document.getElementById("selected_<c:out value='${count}'/>").value;
															if(selectedId == "true") {
																	document.getElementById("selected1_<c:out value='${count}'/>").checked = true;
															}		
														</script></div>
														</td>
														<td height="25" class="row-white">
														<div align="center">
														 <input type="hidden" id="selected_not_applicable_<c:out value='${count}'/>" name="selected_<c:out value='${count}'/>" value="<nested:write name='docList' property='tempNotApplicable'/>"/>
														<%--<nested:checkbox property="hardSubmitted"></nested:checkbox>--%>
														<input type="checkbox" id="selected1_not_applicable_<c:out value='${count}'/>" name="applicantDetails.editDocuments[<c:out value='${count}'/>].notApplicable" onclick="unselectHardSubmit('<c:out value="${count}"/>')"/>
														<script type="text/javascript">
															selected_not_applicable_Id = document.getElementById("selected_not_applicable_<c:out value='${count}'/>").value;
															if(selected_not_applicable_Id == "true") {
																	document.getElementById("selected1_not_applicable_<c:out value='${count}'/>").checked = true;
																	
															}
														</script>
														</div>
														</td>
														<td height="25" class="row-white" align="center"><nested:write
															property="name" /></td>
														<td class="row-white"></td>
														<td class="row-white">
															<span class="row-even"> <nested:file property="editDocument"></nested:file></span>
														</td>
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
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
		
						<tr>
							<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="48%" height="21">
									<div align="right"><html:submit styleClass="formbutton">
										<bean:message key="knowledgepro.submit" />
									</html:submit></div>
		
									</td>
									<td width="7%"><div align="center"><html:button property="" styleClass="formbutton"
										onclick="resetFieldAndErrMsgs()">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button></div></td>
									<td width="45%" height="45" align="left"><html:button
										property="" styleClass="formbutton"
										onclick="cancelMe('initStudentCreation')">
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
<script type="text/javascript">
	onLoadAddrCheck();
	var relgId = document.getElementById("religionType").value;
	if(relgId != null && relgId.length != 0) {
		document.getElementById("religionId").value = relgId;
	}

	
		var casteId = document.getElementById("casteType").value;
		if(casteId != null && casteId.length != 0) {
			document.getElementById("castCatg").value = casteId;
		}

	function unselectApplicable(count) {
		document.getElementById("selected1_not_applicable_"+count).checked = false;
	}	
	
	function unselectHardSubmit(count){
		document.getElementById("selected1_"+count).checked = false;
	}	
	
	if(document.getElementById("birthState").value==""){
	setTimeout("getStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','birthState')",2000);
	}
	if(document.getElementById("permAddrstate").value==""){
	setTimeout("getPermAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','permAddrstate')",4000);
	}
	if(document.getElementById("tempAddrstate").value==""){
	setTimeout("getTempAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','tempAddrstate')",6000);
	}
	if(document.getElementById("parentState").value==""){
		setTimeout("getParentAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','parentState')",8000);
	}
	if(document.getElementById("guardianState").value==""){
		setTimeout("getGuardianAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','guardianState')",9000);
	}
		
</script>