<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>


<%@page import="com.kp.cms.constants.CMSConstants"%>
<SCRIPT type="text/javascript">
	function downloadFile(documentId) {
		document.location.href = "ReviewDocDownloadAction.do?documentId="
				+ documentId;
	}
</SCRIPT>
<script type="text/javascript">

function noCopyMouse(e) {
    var isRight = (e.button) ? (e.button == 2) : (e.which == 3);
    if(isRight) {
    	alert('Please write the re confimation mail');
		document.getElementById("confirmEmailId").value="";
        return false;
    }
    return true;
}

function noCopyKey(e) {
    var isCtrl;
isCtrl = e.ctrlKey
if(isCtrl) {
		document.getElementById("confirmEmailId").value="";
    		return false;
    		
      	}
	
	if(e.keyCode == 17){
		document.getElementById("confirmEmailId").value="";
	return false;
	}

	//noCopyMouse(e);
    	return true;
}



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
		document.admissionFormForm.method.value="initDetailMarkConfirmPage";
		document.admissionFormForm.submit();
	}
	function detailSemesterSubmit(count)
	{
		document.getElementById("countID").value=count;
		document.admissionFormForm.method.value="initSemesterMarkConfirmPage";
		document.admissionFormForm.submit();
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
		document.admissionFormForm.method.value="initlateralEntryConfirmPage";
		document.admissionFormForm.submit();
	}
	function detailTransferSubmit()
	{
		document.admissionFormForm.method.value="initTransferEntryConfirmPage";
		document.admissionFormForm.submit();
	}
	function getDetailForEdit(applicationNumber,appliedYear,method) {
		document.admissionFormForm.method.value=method;
		document.admissionFormForm.detailsView.value=false;
		document.admissionFormForm.selectedAppNo.value=applicationNumber;
		document.admissionFormForm.selectedYear.value=appliedYear;
		document.admissionFormForm.submit();
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
<script type="text/javascript">
   		var startDate;
   		var tenMinAlert=false;
   		var oneMinAlert=false;
   		var finalAlert=false;
   		function setStartDate()
   		{
			startDate=new Date();
   		}	

       	function checkSession()
       	{
       		var fourtyFiveMin = 1000 * 60*45;
			var fourtySixMin=1000*60*46;	
       		var fiftyFourMin = 1000 *60* 54;
       		var fiftyFive=1000*60*55;
       		var fiftySeven=1000*60*57;
       		var fiftyEight=1000*60*58;
       		var sixtyMin = 1000 * 60*59;
       		var currentDate=new Date();
       		// Convert both dates to milliseconds
       		var date1_ms = startDate.getTime();
       		var date2_ms = currentDate.getTime();

       		// Calculate the difference in milliseconds
       		var difference_ms = Math.abs(date1_ms - date2_ms);
       		
       		if(difference_ms>=fourtyFiveMin && difference_ms<=fourtySixMin && !tenMinAlert)
       		{
				alert("your session is about to expire in 10 min");
				tenMinAlert=true;	
       		}
       		else
       		if(difference_ms>=fiftyFourMin && difference_ms<=fiftyFive && !oneMinAlert)
       		{
				alert("your session is about to expire in 2 minute");
				oneMinAlert=true;
       		}
       		else
       		if(difference_ms>=fiftySeven && difference_ms<=fiftyEight && !finalAlert)
           	{
       			finalAlert=true;
				alert("Your Session Expired. Clcik on Ok to continue within 30 seconds.");
				currentDate=new Date();
	       		date2_ms = currentDate.getTime();
       			difference_ms = Math.abs(date1_ms - date2_ms);
       			if(difference_ms>=sixtyMin)	
				{
    				window.location="sessionTimeOutPage.jsp";	
				}
       			else
       			{
       				document.admissionFormForm.method.value="sessionExpired";
					document.admissionFormForm.submit();
       			}		
           	}
       		if(difference_ms>=sixtyMin)	
			{
				window.location="sessionTimeOutPage.jsp";	
			}				
       		
       	}	
</script>
 
<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/prcadmissionform.js"></script>
<script language="JavaScript" src="js/admission/studentdetails.js"></script>

<html:form action="/presidanceadmissionFormSubmit" enctype="multipart/form-data" method="POST">
	<script type="text/javascript">
		setStartDate();
		setInterval ( "checkSession()", 1000 );
	</script>
	<html:hidden property="method" value="submitApplicantCreation" />
	<html:hidden property="singlePageAppln" value="true"/>
	<html:hidden property="formName" value="admissionFormForm" />
	<html:hidden property="selectedAppNo" value="" />
	<html:hidden property="selectedYear" value="" />
	<html:hidden property="detailsView" value="false" />
	<html:hidden property="pageType" value="21" />

	<input type="hidden" name="courseId" value='<bean:write	name="admissionFormForm" property="applicantDetails.course.id" />' />

	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.applicationform"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.applicationform"/></strong></div>
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
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message key="knowledgepro.applicationform"/></td>
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
											
											<td  colspan="3" height="20"class="row-even" align="right"><img src='<%=request.getContextPath()%>/PhotoServlet'  height="150Px" width="150Px" /></td>
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
						<!--<logic:equal value="false" property="isPresidance" name="admissionFormForm">
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
												<nested:write property="applicantDetails.challanRefNo"/>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.journalNo" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;
											<nested:write property="applicantDetails.journalNo" />
											</td>
											<td height="24" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.application.date.label" /></div>
											</td>
											<td height="24" class="row-white" align="left">&nbsp;
											<nested:write property="applicantDetails.challanDate" />
 					
											</td>
										</tr>
										<tr class="row-even">
											<td height="19" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admission.amount" /></div>
											</td>
											<td height="19" class="row-even" align="left">&nbsp;
											<nested:write property="applicantDetails.amount" />
											</td>
											<td height="19" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.branchCode"/></div></td>
											<td height="19" class="row-even"><div align="left">&nbsp;&nbsp;<nested:write property="applicantDetails.bankBranch"/></div></td>
											<td class="row-odd" class="row-even"><div align="right">&nbsp;</div></td>
											<td height="19"><div align="left">&nbsp;&nbsp;</div></td>
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
						</logic:equal>

				--><tr>
                  <td valign="top" class="heading">&nbsp;<bean:message key="admissionForm.studentinfo.main.label"/></td>
                  <td valign="top" class="heading">&nbsp;&nbsp;&nbsp;<bean:message key="admissionForm.studentinfo.residentinfo.title"/></td>
                </tr>
			
                <tr>
                  <td width="49%" valign="top" class="heading"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="404" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>

                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="206" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                       
                        <tr >
                          <td height="45" class="row-odd" width="40%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.candidateName"/> </div></td>
                          <td height="25" class="row-even" ><div align="left">
							
								<nested:text property="applicantDetails.personalData.firstName" size="30" maxlength="90"></nested:text></div>
							</td>
                        </tr>
                        <tr >
                          <td height="25" class="row-odd" width="40%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.dob.label"/><bean:message key="admissionForm.application.dateformat.label"/></div></td>
                          <td height="30" class="row-even" align="left" ><nested:text property="applicantDetails.personalData.dob" styleId="dateOfBirth" size="11" maxlength="11" ></nested:text>
					                              <script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'admissionFormForm',
													// input name
													'controlname' :'dateOfBirth'
												});
											</script></td>
                        </tr>
                       

                        <tr >
                          <td height="25" class="row-odd" width="40%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.nationality.label"/></div></td>
                          <td height="35" class="row-even" align="left">
							<nested:select property="applicantDetails.personalData.nationality" styleClass="comboLarge" styleId="nationality">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
								<logic:iterate id="option" property="nationalities" name="admissionFormForm">
									<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_NATIONALITY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
									<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_NATIONALITY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
									<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
								</logic:iterate>
													
												</nested:select>
						</td>
                        </tr>
                        <tr>
                           <td height="17" class="row-odd" width="40%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.sex.label"/></div></td>
                           <td height="17" class="row-even" align="left"><nested:radio property="applicantDetails.personalData.gender" value="MALE"><bean:message key="admissionForm.studentinfo.sex.male.text"/></nested:radio>
								<nested:radio property="applicantDetails.personalData.gender" value="FEMALE"><bean:message key="admissionForm.studentinfo.sex.female.text"/></nested:radio></td>
                        </tr>
						
						 <tr>
						   <%String dynaStyle3="display:none;"; %>
									<logic:equal value="true" property="sportsPerson" name="admissionFormForm">
										<%dynaStyle3="display:block;"; %>
									</logic:equal>
					        <td height="17" class="row-odd" width="40%"><div align="right"><bean:message key="knowledgepro.applicationform.sports.label"/> </div></td>
                           <td height="17" class="row-even" align="left"><nested:radio property="applicantDetails.personalData.sportsPerson" value="true" onclick="showSportsDescription()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
											<nested:radio property="applicantDetails.personalData.sportsPerson" value="false" onclick="hideSportsDescription()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
											<div id="sports_description" style="<%=dynaStyle3 %>">
												<nested:text styleId="sportsDescription" property="applicantDetails.personalData.sportsDescription" maxlength="80" size="15"></nested:text>
											</div>				
								</td>

                        </tr>
						
							
						
 						<tr>
 						   <%String dynaStyle4="display:none;"; %>
									<logic:equal value="true" property="handicapped" name="admissionFormForm">
										<%dynaStyle4="display:block;"; %>
									</logic:equal>
                           <td height="17" class="row-odd" width="40%"><div align="right"><bean:message key="knowledgepro.applicationform.physical.label"/></div></td>
                           <td height="17" class="row-even" align="left"><nested:radio property="applicantDetails.personalData.handicapped" value="true" onclick="showHandicappedDescription()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
											<nested:radio property="applicantDetails.personalData.handicapped" value="false" onclick="hideHandicappedDescription()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
											<div id="handicapped_description" style="<%=dynaStyle4 %>">
												<nested:text styleId="hadnicappedDescription" property="applicantDetails.personalData.hadnicappedDescription" maxlength="80" size="15"></nested:text>
											</div>
								</td>
                        </tr>
						
                        <tr >
                          <td height="20" class="row-odd" width="40%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.bloodgroup.label"/></div></td>
	                      <td height="20" class="row-even" align="left"><input type="hidden" id="BGType" name="BGType" value='<bean:write name="admissionFormForm" property="bloodGroup"/>'/>
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
						<logic:equal value="true" property="displaySecondLanguage" name="admissionFormForm">
						 <tr >
                          <td height="20" class="row-odd" width="40%"><div align="right"><bean:message key="knowledgepro.applicationform.secLang.label"/></div></td>
	                      <td height="20" class="row-even" align="left">
                          <html:select property="applicantDetails.personalData.secondLanguage" styleClass="comboLarge" styleId="secondLanguage">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection property="secondLanguageList"
									label="value" value="value" />
							</html:select>
						 </td>
                        </tr>
						</logic:equal>

						<tr >
                          <td height="25" class="heading" colspan="2"><div align="center"><bean:message key="admissionForm.studentinfo.birthdetails.main.label"/> </div></td>
                        </tr>
						<tr >
                          	<td height="30" class="row-even" colspan="2" align="left" >
								<table width="100%" cellspacing="1" cellpadding="2">
									<tr >
                          				<td height="25" class="row-odd" width="45%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.birthdetails.place.label"/></div></td>
                          				<td height="30" class="row-even" align="left" ><nested:text property="applicantDetails.personalData.birthPlace" size="15" maxlength="50"></nested:text></td>
                       				 </tr>
										 <tr >
                          <td height="25" class="row-odd" width="45%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.birthdetails.country.label"/></div></td>
                          <td height="35" class="row-even" align="left" >
								<nested:select property="applicantDetails.personalData.birthCountry" styleClass="comboExtraLarge" styleId="birthCountry" onchange="getStates(this.value,'birthState');">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="admissionFormForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
												</nested:select>
						</td>
                        </tr>
						<tr >
							<%String dynaStyle=""; %>
									<logic:equal value="Other" property="birthState" name="admissionFormForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="birthState" name="admissionFormForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                          <td height="25" class="row-odd" width="45%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.birthdetails.state.label"/></div></td>
                          <td height="35" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                          	<tr><td><nested:select property="applicantDetails.personalData.birthState"
														styleId="birthState" styleClass="comboExtraLarge"
														onchange="funcOtherShowHide('birthState','otherBirthState')">
														<html:option value="">- Select -</html:option>
														<c:if
															test="${admissionFormForm.applicantDetails.personalData.birthCountry!= null && admissionFormForm.applicantDetails.personalData.birthCountry != ''}">
															<c:set var="birthStateMap"
																value="${baseActionForm.collectionMap['birthStateMap']}" />
															<c:if test="${birthStateMap != null}">
																<html:optionsCollection name="birthStateMap"
																	label="value" value="key" />
																<html:option value="Other">Other</html:option>
															</c:if>
														</c:if>

													</nested:select></td></tr>
							<tr><td><nested:text property="applicantDetails.personalData.stateOthers" size="10"
														maxlength="30" styleId="otherBirthState"
														style="<%=dynaStyle %>"></nested:text></td></tr>
							</table>
						</td>
                        </tr>
								</table>
							</td>
                        </tr>

                      </table></td>
                      <td  background="images/right.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table>
                   </td>
                  <td width="51%" valign="top" class="heading"><div align="right">
                    <table width="97%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td ><img src="images/01.gif" width="5" height="5"></td>
                        <td width="450" background="images/02.gif"></td>
                        <td><img src="images/03.gif" width="5" height="5"></td>
                      </tr>
                      <tr>
                        <td width="5"  background="images/left.gif"></td>
                        <td height="206" valign="top"><table width="99%" cellspacing="1" cellpadding="2">
                            <tr >
                              <td width="44%" height="20" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.residentcatg.label"/><BR><bean:message key="admissionForm.studentinfo.residentcatg.label2"/>
								</div></td>
                              <td width="56%" height="20" class="row-even" align="left">
							<nested:select property="applicantDetails.personalData.residentCategory" styleClass="comboLarge" styleId="residentCategory">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="residentTypes" label="name" value="id"/>
												</nested:select>
							</td>
                            </tr>
                            <tr>
									<logic:equal value="Other" property="religionId" name="admissionFormForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="religionId" name="admissionFormForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                              <td height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.religion.label"/></div></td>
                              <td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr><td>
													
												<input type="hidden" id="religionType" name="religionType" value='<bean:write name="admissionFormForm" property="applicantDetails.personalData.religionId"/>'/>
												<logic:notEqual value="Other" property="applicantDetails.personalData.religionId" name="admissionFormForm">
													<%dynaStyle="display:none;"; %>
												<nested:select property="applicantDetails.personalData.religionId" styleClass="comboLarge" styleId="religions" onchange="getSubReligion(this.value,'subreligion');funcOtherShowHide('religions','otherReligion')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="religions" label="religionName" value="religionId"/>
													<html:option value="Other">Other</html:option>
												</nested:select>
												</logic:notEqual>
												<logic:equal value="Other" property="applicantDetails.personalData.religionId" name="admissionFormForm">
													<%dynaStyle="display:block;"; %>												
												<nested:select property="applicantDetails.personalData.religionId" styleClass="comboLarge" styleId="religions" onchange="getSubReligion(this.value,'subreligion');funcOtherShowHide('religions','otherReligion')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="religions" label="religionName" value="religionId"/>
													<html:option value="Other">Other</html:option>
												</nested:select>
												
												</logic:equal></td></tr>
												<tr><td><nested:text property="applicantDetails.personalData.religionOthers" size="10" maxlength="30" styleId="otherReligion" style="<%=dynaStyle %>"></nested:text></td></tr>
												</table>
							</td>
                            </tr>
							<logic:equal value="true" property="casteDisplay" name="admissionFormForm">
	                            <tr >
	                              <td height="20" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.subreligion.label"/></div></td>
	                              <td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
	                              <tr><td><nested:select property="applicantDetails.personalData.subReligionId" styleClass="comboLarge" styleId="subreligion" onchange="funcOtherShowHide('subreligion','otherSubReligion')">
										<html:option value="">- Select -</html:option>
										<c:if test="${admissionFormForm.applicantDetails.personalData.religionId != null && admissionFormForm.applicantDetails.personalData.religionId != ''}">
				                           					<c:set var="subReligionMap" value="${baseActionForm.collectionMap['subReligionMap']}"/>
			                            		    	 	<c:if test="${subReligionMap != null}">
			                            		    	 		<html:optionsCollection name="subReligionMap" label="value" value="key"/>
																<html:option value="Other">Other</html:option>
			                            		    	 	</c:if> 
				                        </c:if>
								</nested:select></td></tr>
								<tr><td><nested:text property="applicantDetails.personalData.religionSectionOthers" size="10" maxlength="30" styleId="otherSubReligion" style="<%=dynaStyle %>"></nested:text></td></tr>
								</table>
								</td>
	                            </tr>
							</logic:equal>
                            <!--<logic:equal value="false" property="isPresidance" name="admissionFormForm">
                            <tr>
                              <td height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.castcatg.label"/></div></td>
                              <td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              		<tr><td>
									<input type="hidden" id="casteType" name="casteType" value='<bean:write name="admissionFormForm" property="applicantDetails.personalData.casteId"/>'/>
									<logic:equal value="Other" property="applicantDetails.personalData.casteId" name="admissionFormForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="applicantDetails.personalData.casteId" name="admissionFormForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                              		<nested:select property="applicantDetails.personalData.casteId" styleId="castCatg" styleClass="comboLarge" onchange="funcOtherShowHide('castCatg','otherCastCatg')">
									<option value="">-Select-</option>
										<html:optionsCollection property="casteList" name="admissionFormForm" label="casteName" value="casteId"/>
										
									<html:option value="Other">Other</html:option>
								</nested:select></td></tr>
								<tr><td><nested:text property="applicantDetails.personalData.casteOthers" size="10" maxlength="30" styleId="otherCastCatg" style="<%=dynaStyle %>"></nested:text></td></tr>
								</table>
							</td>
                            </tr>
                            <tr >
                              <td height="17" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.belongsto.label"/></div></td>
                              <td height="17" class="row-even" align="left"><nested:radio property="applicantDetails.personalData.areaType" value='R'><bean:message key="admissionForm.studentinfo.belongsto.rural.text"/></nested:radio>
											<nested:radio property="applicantDetails.personalData.areaType" value='U'><bean:message key="admissionForm.studentinfo.belongsto.urban.text"/></nested:radio></td>
                            </tr>
                            </logic:equal>
                            --><tr>
                              <td height="20" class="row-odd"><div align="right"><span class="Mandatory"></span><bean:message key="admissionForm.studentinfo.phone.label"/></div></td>
                              <td height="20" class="row-even" align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><nested:text property="applicantDetails.personalData.phNo1" size="3" maxlength="4"></nested:text></td></tr>
													<tr><td><bean:message key="admissionForm.phone.areacode.label"/></td><td><nested:text property="applicantDetails.personalData.phNo2" size="5" maxlength="7"></nested:text></td></tr>
													<tr><td><bean:message key="admissionForm.phone.main.label"/></td><td><nested:text property="applicantDetails.personalData.phNo3" size="10" maxlength="10"></nested:text></td></tr>
												</table>
                              </td>
                            </tr>
                            <tr>
                              <td height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.mobile.label"/></div></td>
                              <td height="20" class="row-even" align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
			                              	  <tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><nested:text property="applicantDetails.personalData.mobileNo1" size="4" maxlength="4" onkeypress="return isNumberKey(event)"></nested:text></td></tr>
			                                  <tr><td> <bean:message key="admissionForm.mob.no.label"/> </td><td><nested:text property="applicantDetails.personalData.mobileNo2" size="10" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text></td></tr>
											</table>
                               </td>
                            </tr>
                            <tr>
                              <td height="20" class="row-odd" ><div align="right"><span class="Mandatory"></span><bean:message key="admissionForm.studentinfo.email.label"/></div></td>
                              <td height="20" class="row-even" align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
												 <tr><td><nested:text property="applicantDetails.personalData.email" size="15" maxlength="50"></nested:text> </td></tr>
												<tr><td>(e.g. name@yahoo.com)</td></tr>
                                  				</table>
                              </td>
                            </tr>
							<tr>
                              <td height="20" class="row-odd" ><div align="right"><span class="Mandatory"></span><bean:message key="admissionForm.studentinfo.confirmemail.label"/></div></td>
                              <td height="20" class="row-even" align="left"><p>
			                                  <nested:text property="applicantDetails.personalData.confirmEmail" styleId="confirmEmailId" size="15" maxlength="50" onmousedown="noCopyMouse(event)" onkeydown= "noCopyKey(event)" onkeyup="noCopyKey(event)" ></nested:text>
			                                  <br />
			                              </p></td>
                            </tr>
                        </table></td>
                        <td  background="images/right.gif" width="10" height="3"></td>
                      </tr>
                      <tr>
                        <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                        <td background="images/05.gif"></td>
                        <td><img src="images/06.gif" ></td>
                      </tr>
                    </table>
                  </div></td>
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
									<nested:select property="applicantDetails.personalData.motherTongue" styleClass="comboLarge" >
										<html:option value="">-Select-</html:option>
										<nested:optionsCollection  property="mothertongues" name="admissionFormForm" label="languageName" value="languageId"/>
									</nested:select>
								</td>
								</logic:equal>
								<logic:equal value="true" property="displayHeightWeight" name="admissionFormForm">
								 <td height="23" class="row-even"><bean:message key="knowledgepro.applicationform.height.label"/>&nbsp;
									<nested:text property="applicantDetails.personalData.height" size="5" maxlength="5"></nested:text>
								</td>
								<td height="23" class="row-even" ><bean:message key="knowledgepro.applicationform.weight.label"/>&nbsp;
									<nested:text property="applicantDetails.personalData.weight" size="6" maxlength="6"></nested:text>
								</td>
								</logic:equal>
	                          </tr>
							<logic:equal value="true" property="displayLanguageKnown" name="admissionFormForm">
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

							<logic:equal value="true" property="displayTrainingDetails" name="admissionFormForm">
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
							
							<logic:equal value="true" property="displayAdditionalInfo" name="admissionFormForm">
							<tr class="row-white">
	                            <td height="23" colspan="4" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.addninfo.label"/></div></td>
							</tr>
							<tr class="row-white">
	                            <td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.addninfo.label1"/> <B><bean:write property="organizationName" name="admissionFormForm"/></B>:</td>
								 <td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.courseKnownBy" size="20" maxlength="50" ></nested:text>
								</td>
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.strength.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.strength" size="20" maxlength="100" ></nested:text>
										
								</td>
	                          </tr>
								<tr class="row-white">
	                           
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.addninfo.label2"/><B><bean:write property="applicantDetails.course.name" name="admissionFormForm"/> </B>:</td>
								<td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.courseOptReason" size="20" maxlength="100"  ></nested:text>
								</td>
								
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.weakness.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.weakness"  size="20" maxlength="100"></nested:text>
										
								</td>
	                         	 </tr>
								<tr class="row-white">
	                           
								<td height="23" colspan="3" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.addninfo.label3"/> <B><bean:write property="applicantDetails.course.name" name="admissionFormForm"/></B>:</td>
								<td height="23" class="row-even" align="left">
									<nested:textarea property="applicantDetails.personalData.otherAddnInfo" cols="25" rows="4" ></nested:textarea>
								</td>
								
	                         	 </tr>
							</logic:equal>
							<logic:equal value="true" property="displayExtracurricular" name="admissionFormForm">
							<logic:notEmpty property="applicantDetails.personalData.studentExtracurricularsTos" name="admissionFormForm">
							<tr class="row-white">
	                            <td height="23" colspan="4" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.extracurr.label"/></div></td>
							</tr>
							<tr class="row-white">
	                            <td height="23" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.extracurr.label2"/></td>
								 <td height="23" class="row-even" align="left">
									
									<nested:select property="applicantDetails.personalData.extracurricularIds" styleClass="row-even" multiple="true" >
										<nested:optionsCollection property="applicantDetails.personalData.studentExtracurricularsTos" name="admissionFormForm" label="name" value="id"/>
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
											<td width="212" height="23" class="row-odd" align="right">
											<bean:message
												key="knowledgepro.admission.passportNo" />
											</td>
											<td width="236" height="23" class="row-even" align="left"><nested:text property="applicantDetails.personalData.passportNo" maxlength="15"></nested:text></td>
											<td width="224" class="row-odd" align="right">
											<bean:message
												key="knowledgepro.admission.issuingCountry" />
											
											</td>
											<td width="244" class="row-even" align="left">
												<nested:select property="applicantDetails.personalData.passportCountry" styleClass="comboLarge" styleId="passportCountry">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="admissionFormForm">
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
												key="knowledgepro.admission.validUpto" /><BR><bean:message key="admissionForm.application.dateformat.label"/></div>
											</td>
											<td height="25" class="row-white" align="left">
											<nested:text property="applicantDetails.personalData.passportValidity" styleId="passportValidity" styleClass="row-white" size="15" maxlength="15" ></nested:text>
									<script language="JavaScript">
									new tcal( {
										// form name
										'formname' :'admissionFormForm',
										// input name
										'controlname' :'passportValidity'
									});
								</script>  
											</td>
											<td class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.residentpermit.label"/></div>
											</td>
											<td class="row-white" align="left">
											<html:text property="applicantDetails.personalData.residentPermitNo" styleId="residentPermit" styleClass="row-white" size="10" maxlength="10" ></html:text>
											</td>
										</tr>
										<tr class="row-white">
											<td width="360" height="23" colspan="2" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.policedate.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/>
											</div>
											</td>
											<td width="200" height="23" class="row-even" align="left">
											<html:text property="applicantDetails.personalData.residentPermitDate" styleId="permitDate" size="10" maxlength="10"></html:text>
												<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'admissionFormForm',
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
						 
				 		<%boolean pref = true; %>
						<logic:notEmpty property="preferenceList" name="admissionFormForm" >
							<logic:iterate property="preferenceList" id="prefFound"  name="admissionFormForm" indexId="count" >
								<c:if test="${count!= 0}"></c:if>
								<logic:empty name="prefFound" property="prefcourses">
									<%pref = false; %> 
								</logic:empty>
							</logic:iterate>
						</logic:notEmpty>					
						<%if(pref){ %>
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
											<td height="27" class="row-odd" colspan="6">
											<div align="center"><bean:message
												key="knowledgepro.admission.preferences" /></div>
											</td>
											
										</tr>
										
										<logic:notEmpty name="admissionFormForm" property="preferenceList">
										
										<tr class="row-white">
										<nested:iterate property="preferenceList" id="prefTo" >
											<%	String dynaJsMethod="getDynaUniquePreference('Map5',this.value,'coursePref3')";
												
											%>
											<td  height="27" class="row-even">
											<div align="center">
											<logic:equal value="1" property="prefNo" name="prefTo"><span class="Mandatory">*</span>
											<bean:message key="admissionForm.edit.firstpref.label"/></logic:equal>
											<logic:equal value="2" property="prefNo" name="prefTo"><bean:message key="admissionForm.edit.secpref.label"/></logic:equal>
											<logic:equal value="3" property="prefNo" name="prefTo"><bean:message key="admissionForm.edit.thirdpref.label"/></logic:equal>
											</div>
											</td>
											
											<td  class="row-even">
											<div align="center">
											<logic:equal value="1" property="prefNo" name="prefTo">
												<bean:write property="coursName" name="prefTo"/>
											</logic:equal>
											<logic:equal value="2" property="prefNo" name="prefTo">
											<c:set var="temp"><nested:write property="coursId" name="prefTo"/></c:set>
											<nested:select property="coursId" styleClass="comboLarge" styleId="coursePref2" onchange='<%=dynaJsMethod %>'>
												<option value="">-Select-</option>
												<nested:optionsCollection property="prefcourses" name="prefTo" label="name" value="id"/>
											</nested:select>
											</logic:equal>
											<logic:equal value="3" property="prefNo" name="prefTo">
											<nested:select property="coursId" styleClass="comboLarge" styleId="coursePref3">
												<option value="">-Select-</option>
												<c:set var="tempKey">Map5</c:set>	
												<c:if test="${temp != null && temp != ''}">
						                             <c:set var="Map" value="${baseActionForm.collectionMap[tempKey]}"/>
													<c:choose>
													<c:when test="${Map!=null}">
													<html:optionsCollection name="Map" label="value" value="key"/>
													</c:when>
													<c:otherwise>
														<nested:optionsCollection property="prefcourses" name="prefTo" label="name" value="id"/>
													</c:otherwise>
													</c:choose>
												</c:if>
											</nested:select>
											</logic:equal>
												</div>
											</td>
										</nested:iterate>
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
						<%} %>



						

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
                            <td height="27" class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.applicationform.orgName.label"/></div></td>
                            <td height="27" class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.applicationform.jobdesc.label"/></div></td>
                            <td class="row-odd" width="15%"><div align="center"> <bean:message key="knowledgepro.applicationform.fromdt.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/></div></td>
                            <td class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.applicationform.todt.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/></div></td>
							<td height="27" class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.applicationform.lastsal.label"/></div></td>
							<td height="27" class="row-odd" width="15%"><div align="center"><bean:message key="knowledgepro.applicationform.reportto.label"/></div></td>
                          </tr>
						<nested:iterate property="applicantDetails.workExpList" id="exp" name="admissionFormForm" type="com.kp.cms.to.admin.ApplicantWorkExperienceTO" indexId="count">
                          <tr class="row-white">
                            <td height="27" class="row-even"><div align="left">
								<nested:text property="organization" styleClass="textbox" size="15" maxlength="100" />
                            </div></td>
                            <td height="27" class="row-even"><div align="center">
								<nested:text property="position" styleClass="textbox" size="10" maxlength="30" />
                            </div></td>
                            <td width="224" class="row-even"><div align="center">
							<%String fromid="expFromdate"+count; 
								String toid="expTodate"+count; 
							%>
                             <nested:text property="fromDate" styleId="<%=fromid%>" size="10" maxlength="10"  />
                              <script language="JavaScript">
								new tcal ({
									// form name
									'formname': 'admissionFormForm',
									// input name
									'controlname': '<%=fromid%>'
								});</script>
                            </div></td>
                            <td class="row-even"><div align="center">
                             <nested:text property="toDate" styleId="<%=toid%>" size="10" maxlength="10"  />
                              <script language="JavaScript">
							new tcal ({
								// form name
								'formname': 'admissionFormForm',
								// input name
								'controlname': '<%=toid%>'
							});</script>
                            </div></td>
							<td  height="27" class="row-even"><div align="center">
								<nested:text property="salary" styleClass="textbox" size="10" maxlength="10" />
                            </div></td>
 							<td  height="27" class="row-even"><div align="center">
								<nested:text property="reportingTo" styleClass="textbox" size="10" maxlength="50" />
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
											<td width="212" height="20" class="row-odd" align="right">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="236" height="20" class="row-even" align="left">&nbsp;
												<nested:text property="applicantDetails.personalData.currentAddressLine1" size="15" maxlength="100"></nested:text>
											</td>
											<td width="224" class="row-odd" align="right">
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
												<nested:select property="applicantDetails.personalData.currentCountryId" styleClass="comboExtraLarge" styleId="currentCountryName" onchange="getTempAddrStates(this.value,'tempAddrstate');">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="admissionFormForm">
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
											<logic:equal value="Other" property="applicantDetails.personalData.currentStateId" name="admissionFormForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="applicantDetails.personalData.currentStateId" name="admissionFormForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td>&nbsp;&nbsp;<nested:select property="applicantDetails.personalData.currentStateId" styleClass="comboExtraLarge" styleId="tempAddrstate" onchange="funcOtherShowHide('tempAddrstate','otherTempAddrState')">
									<html:option value="">- Select -</html:option>
									<c:if test="${admissionFormForm.applicantDetails.personalData.currentCountryId != null && admissionFormForm.applicantDetails.personalData.currentCountryId!= ''}">
			                           					<c:set var="tempAddrStateMap" value="${baseActionForm.collectionMap['tempAddrStateMap']}"/>
		                            		    	 	<c:if test="${tempAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="tempAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                        </c:if>
									</nested:select></td></tr>
							<tr><td><nested:text property="applicantDetails.personalData.currentAddressStateOthers" size="10" maxlength="30" styleId="otherTempAddrState" style="<%=dynaStyle %>"></nested:text></td></tr>
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
											<nested:select property="applicantDetails.personalData.permanentCountryId" styleClass="comboExtraLarge" styleId="permanentCountryName" onchange="getPermAddrStates(this.value,'permAddrstate');">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="admissionFormForm">
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
												<logic:equal value="Other" property="applicantDetails.personalData.permanentStateId" name="admissionFormForm">
												<%dynaStyle="display:block;"; %>
												</logic:equal>
											<logic:notEqual value="Other" property="applicantDetails.personalData.permanentStateId" name="admissionFormForm">
												<%dynaStyle="display:none;"; %>
												</logic:notEqual>
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td>&nbsp;&nbsp;<nested:select property="applicantDetails.personalData.permanentStateId" styleClass="comboExtraLarge" styleId="permAddrstate" onchange="funcOtherShowHide('permAddrstate','otherPermAddrState')">
									<html:option value="">- Select -</html:option>
									<c:if test="${admissionFormForm.applicantDetails.personalData.permanentCountryId != null && admissionFormForm.applicantDetails.personalData.permanentCountryId!= ''}">
			                           					<c:set var="permAddrStateMap" value="${baseActionForm.collectionMap['permAddrStateMap']}"/>
		                            		    	 	<c:if test="${permAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="permAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                        </c:if>
							</nested:select></td></tr>
							<tr><td><nested:text property="applicantDetails.personalData.permanentAddressStateOthers" size="10" maxlength="30" styleId="otherPermAddrState" style="<%=dynaStyle %>"></nested:text></td></tr>
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
									<logic:notEmpty name="admissionFormForm" property="applicantDetails.ednQualificationList">
										<tr>
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.qualification" /></td>
											<td class="row-odd"><span class="Mandatory">*</span>Exam Name</td>
											
											<td class="row-odd">
											<div align="center"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.universityBoard" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.instituteName" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="admissionForm.education.State.label" /></div>
											</td>
											<td class="row-odd" align="center"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.passingYear" /></td>
											<td class="row-odd" align="center"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.passingmonth"/></td>
											
											<td class="row-odd" align="center">
											<div align="center"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.attempts" /></div>
											</td>
											<td class="row-odd"><span class="Mandatory"></span><bean:message key="knowledgepro.applicationform.prevregno.label"/></td>
											<td class="row-odd">
											<div align="center"><span class="Mandatory"></span><bean:message
												key="knowledgepro.admission.marksObtained" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><span class="Mandatory"></span><bean:message
												key="knowledgepro.admission.totalMarks" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.prereqdef.percentage" /></div>
											</td>
										</tr>
										</logic:notEmpty>
        <nested:iterate  name="admissionFormForm" property="applicantDetails.ednQualificationList" indexId="count" id="qualDoc">
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
					<logic:iterate id="option"  name="qualDoc" property="universityList">
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
							<c:if test="${Map!=null}">
							<html:optionsCollection name="Map" label="value" value="key"/>
							</c:if>
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
			<nested:select property="stateId" styleClass="comboExtraLarge" styleId='<%=dynaStateId %>'>
				<option value=""><bean:message key="knowledgepro.admin.select"/></option>
				<logic:notEmpty name="admissionFormForm" property="ednStates">
				 <nested:optionsCollection name="admissionFormForm" property="ednStates" label="name" value="id"/>
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
					if(yopid!=0)
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
					if(dAttemid!=0)
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
			<td width="8%" class='<%=dynamicStyle %>'><div align="center">
              <nested:text property="percentage" size="5" maxlength="8" onkeypress="return isDecimalNumberKey(this.value,event)"></nested:text>
            </div></td>
          </tr>
			
		</nested:iterate>
									<logic:equal value="true" property="displayTCDetails" name="admissionFormForm">
									<tr>
											<td height="25" class="row-odd">
											<div align="center"><bean:message key="admissionForm.education.TCNO.label"/></div>
											</td>
											<td height="25" class="row-even"><nested:text property="applicantDetails.tcNo" size="6" maxlength="10"></nested:text></td>
											<td class="row-odd">
											<div align="left"><bean:message key="admissionForm.education.TCDate.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/></div>
											</td>
											<td class="row-even" colspan="2">
											<div align="left"><nested:text property="applicantDetails.tcDate" styleId="tcdate" size="10" maxlength="10"></nested:text><script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'admissionFormForm',
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
											<div align="center"><bean:message key="admissionForm.education.markcarddate.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/></div>
											</td>
											<td class="row-even" colspan="2">
											<div align="left"><nested:text property="applicantDetails.markscardDate" styleId="markscardDate" size="10" maxlength="10"></nested:text>
												<script language="JavaScript">
														new tcal( {
															// form name
															'formname' :'admissionFormForm',
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
													<logic:notEmpty property="entranceList" name="admissionFormForm">
													<html:optionsCollection property="entranceList" name="admissionFormForm" label="name" value="id"/>
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
											<div align="right"><span class="Mandatory">*</span><bean:message
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
											<nested:select property="applicantDetails.personalData.fatherCurrencyId" styleClass="comboLarge" styleId="fatherCurrency">
												

												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
													<%String selected=""; %>
												<logic:iterate id="option" property="currencyList" name="admissionFormForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
												</logic:iterate>

											</nested:select>
											</td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionFormForm.fatherIncome" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											<nested:select property="applicantDetails.personalData.fatherIncomeId" styleClass="comboLarge" styleId="fatherIncomeRange">
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
											<td height="30" class="row-even" align="left">&nbsp;<nested:select property="applicantDetails.personalData.fatherOccupationId" styleClass="comboLarge" styleId="motherOccupation">
													<html:option value="">- Select -</html:option>
													<html:optionsCollection name="admissionFormForm" property="occupations" label="occupationName" value="occupationId"/>
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
											<div align="right"><span class="Mandatory">*</span><bean:message
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
											<nested:select property="applicantDetails.personalData.motherCurrencyId" styleClass="comboLarge" styleId="motherCurrency">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
													<%String selected=""; %>
												<logic:iterate id="option" property="currencyList" name="admissionFormForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
												</logic:iterate>
											</nested:select>
											</td>
										</tr>
										<tr>
											<td height="45" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionFormForm.fatherIncome" /></div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											<nested:select property="applicantDetails.personalData.motherIncomeId" styleClass="comboLarge" styleId="motherIncomeRange">
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
												  <nested:select property="applicantDetails.personalData.motherOccupationId" styleClass="comboLarge" styleId="motherOccupation">
													<html:option value="">- Select -</html:option>
													<html:optionsCollection name="admissionFormForm" property="occupations" label="occupationName" value="occupationId"/>
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
											<nested:select property="applicantDetails.personalData.parentCountryId" styleClass="comboLarge" styleId="parentCountryName" onchange="getParentAddrStates(this.value,'parentState')">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="admissionFormForm">
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
											<td height="20" class="row-white" align="left">
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
							<%String dynastyle=""; %>
								<logic:equal value="Other" property="applicantDetails.personalData.parentStateId" name="admissionFormForm">
										<%dynastyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="applicantDetails.personalData.parentStateId" name="admissionFormForm">
										<%dynastyle="display:none;"; %>
									</logic:notEqual>
                  	<nested:select property="applicantDetails.personalData.parentStateId" styleClass="comboExtraLarge" styleId="parentState" onchange="funcOtherShowHide('parentState','otherParentAddrState');">
									<html:option value="">- Select -</html:option>
									<c:if test="${admissionFormForm.applicantDetails.personalData.parentCountryId != null && admissionFormForm.applicantDetails.personalData.parentCountryId!= ''}">
			                           					<c:set var="parentAddrStateMap" value="${baseActionForm.collectionMap['parentAddrStateMap']}"/>
		                            		    	 	<c:if test="${parentAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="parentAddrStateMap" label="value" value="key"/>
		                            		    	 	</c:if> 
			                        </c:if>
							</nested:select></td></tr>
							<tr><td><nested:text property="applicantDetails.personalData.parentAddressStateOthers" size="10" maxlength="30" styleId="otherParentAddrState" style="<%=dynastyle %>"></nested:text></td></tr>
							</table>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" align="left">
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
											<nested:select property="applicantDetails.personalData.countryByGuardianAddressCountryId" styleClass="comboExtraLarge" styleId="guardianCountryName" onchange="getParentAddrStates(this.value,'guardianState')">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="admissionFormForm">
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
											<td height="20" class="row-white" align="left">
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
								<logic:equal value="Other" property="applicantDetails.personalData.stateByGuardianAddressStateId" name="admissionFormForm">
										<%dynaStyle1="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="applicantDetails.personalData.stateByGuardianAddressStateId" name="admissionFormForm">
										<%dynaStyle1="display:none;"; %>
									</logic:notEqual>
                  	<nested:select property="applicantDetails.personalData.stateByGuardianAddressStateId" styleClass="comboExtraLarge" styleId="guardianState" onchange="funcGuardianOtherShowHide('guardianState','otherGuardianAddrState');">
									<html:option value="">- Select -</html:option>
									<c:if test="${admissionFormForm.applicantDetails.personalData.countryByGuardianAddressCountryId != null && admissionFormForm.applicantDetails.personalData.countryByGuardianAddressCountryId!= ''}">
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
											<td height="20" align="left">&nbsp;
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
						<logic:equal value="true" property="examCenterRequired" name="admissionFormForm">
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
                              <td class="row-odd" ><div align="right"><span class="Mandatory">*</span>Preferred Entrance Exam Center:</div></td>

						
                              <td class="row-even" align="left">
							<nested:select property="applicantDetails.examCenterId" styleClass="comboLarge" styleId="examCenterId">
								<option value=""><bean:message key="knowledgepro.admin.select"/></option>
								<nested:optionsCollection property="examCenters" label="center" value="id"/>
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
						</tr>
						</logic:equal>


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
											
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.documents" /></td>
											<td class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.uploadDocs" /></td>
											<logic:equal value="true" property="reviewWarned" name="admissionFormForm">
											<td class="row-odd" width="15%" align="center">&nbsp;</td>
											</logic:equal>
										</tr>
										<c:set var="temp" value="0" />
										<nested:iterate name="admissionFormForm"
											property="applicantDetails.editDocuments" indexId="count" id="docList">
											<c:choose>
												<c:when test="${temp == 0}">
												<nested:equal value="true" property="photo">
													<tr>
														
														<td height="25" width="25%" class="row-even" align="center"><nested:write
															property="printName" /></td>
														<logic:equal value="true" property="reviewWarned" name="admissionFormForm">
															<td width="15%" class="row-even" align="center">
															<nested:equal
																value="false" property="photo">
															<nested:equal
																value="true" property="documentPresent">
																<a
																	href="javascript:downloadFile('<nested:write property="docTypeId"/>')"><bean:message key="knowledgepro.view" /></a>
															</nested:equal>
															</nested:equal>
															</td>
														</logic:equal>

														<td  class="row-even" width="60%">
														<nested:equal value="true" property="photo">
															<nested:file property="editDocument"></nested:file>
															</nested:equal>
														</td>
													</tr>
													</nested:equal>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
												<nested:equal value="true" property="photo">
													<tr>
	
														<td height="25" class="row-white" align="center"><nested:write
															property="printName" /></td>
														<logic:equal value="true" property="reviewWarned" name="admissionFormForm">
															<td width="15%" class="row-white" align="center">
															<nested:equal
																value="false" property="photo">
															<nested:equal
																value="true" property="documentPresent">
																<a
																	href="javascript:downloadFile('<nested:write property="docTypeId"/>')"><bean:message key="knowledgepro.view" /></a>
															</nested:equal>
															</nested:equal>
															</td>
														</logic:equal>

													
														<td class="row-white" width="60%">
															<span class="row-even"> <nested:equal value="true" property="photo">
															<nested:file property="editDocument"></nested:file>
															</nested:equal></span>
														</td>
													</tr>
													</nested:equal>
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
										onclick="submitAdmissionForm('initApplicantCreationDetail')">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button></div></td>
									<td width="45%" height="45" align="left"><html:button
										property="" styleClass="formbutton"
										onclick="submitAdmissionForm('initOutsideSinglePageAccess')">
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
<script type="text/javascript">
	onLoadAddrCheck();
	var relgId = document.getElementById("religionType").value;
	if(relgId != null && relgId.length != 0) {
		document.getElementById("religions").value = relgId;
	}
	if(document.getElementById("casteType")!=null){
	var casteId = document.getElementById("casteType").value;
	if(casteId != null && casteId.length != 0) {
		document.getElementById("castCatg").value = casteId;
	}
	}
	function unselectApplicable(count) {
		document.getElementById("selected1_not_applicable_"+count).checked = false;
	}	
	function unselectHardSubmit(count){
		document.getElementById("selected1_"+count).checked = false;
	}	

	var confirm = "<c:out value='${admissionFormForm.reviewWarned}'/>";
	if(confirm.length != 0 && confirm == "true") {
		alert("WARNING:: Please verify the application form details before submission.");
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