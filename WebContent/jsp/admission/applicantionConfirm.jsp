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
function funcReligionShowHide(val){
	
	//alert('============'+val);
    		var checkReligionId=document.getElementById("checkReligionId").value;
    		
    		if(checkReligionId==val){
    			document.getElementById("dioces_description").style.display = "block";
    			document.getElementById("parish_description").style.display = "block";
    		}else{
    		document.getElementById("dioces_description").style.display = "none";
    			document.getElementById("parish_description").style.display = "none";
    		}
    	}



    	function getParishValueByDiose(dioid){
        	
    		getParishByDiose(dioid,updateParish);
    	}
    	function updateParish(req){
    			updateOptionsFromMap(req,"parish","-Select-");
    		
    	}
function getDateByVenueselectionOffline(selectedVenue){
    if(selectedVenue!=null && selectedVenue!=""){
        var programId=document.getElementById("programId").value;
        var programYear=document.getElementById("programYear").value;
   		
        getDateByVenueSelectionOffline(selectedVenue,updateDateMap, programId, programYear);
    }	
}
function updateDateMap(req){
	updateOptionsFromMap(req,"interviewSelectionDate","-Select-");
}




/*	function getVenueByInterviewDateselection(selectedDate){
		if(selectedDate!=null && selectedDate!=""){
		var firstPrefCourse=document.getElementById("courseId").value;
    	if(firstPrefCourse!=null && (firstPrefCourse==158 || firstPrefCourse==280)){
        	getVenueBySelectionDateWithPref(selectedDate,updateVenueMap);
    	}else
    	{
			getVenueBySelectionDate(selectedDate,updateVenueMap);
		}
	  }	
	}
	function updateVenueMap(req){
		updateOptionsFromMap(req,"interviewVenue","-Select-");
	}*/

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
	function disableAddress()
	{
		document.getElementById("currLabel").style.display="none";
		document.getElementById("currTable").style.display="none";
	}
	function enableAddress()
	{
		document.getElementById("currLabel").style.display="block";
		document.getElementById("currTable").style.display="block";
	}

	function showNcccertificate(){
		document.getElementById("ncccertificate_description").style.display = "block";
	}

	function hideNcccertificate(){
		document.getElementById("ncccertificate_description").style.display = "none";
		document.getElementById("ncccertificateDescription").value = "";
	}



	function SubmitForm()
	{
		
		var isInterviewSelectionSchedule=document.getElementById("isInterviewSelectionSchedule").value;
		if(isInterviewSelectionSchedule!=null && isInterviewSelectionSchedule!="" && isInterviewSelectionSchedule=="true"){
			var result=CheckAgreed();
			if(result)
    			{
				document.getElementById("method").value="saveConfirmedApplication";
	    		document.admissionFormForm.submit();
				}
		}else
		{
			document.getElementById("method").value="saveConfirmedApplication";
    		document.admissionFormForm.submit();	
		}
    	
	}
	function CheckAgreed(){
		if (document.getElementById("acceptAll").checked) {
    		return true;
		} else {
			alert("Please Accept Terms & Conditions in Interview/Entrance Selection");
			return false;
    		}
	}
	
    	
	
</script>
<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<html:form action="/admissionFormSubmit" enctype="multipart/form-data" method="POST">
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="formName" value="admissionFormForm" />
	<html:hidden property="pageType" value="17" />
	<html:hidden property="isInterviewSelectionSchedule" styleId="isInterviewSelectionSchedule" name="admissionFormForm"/>
	<html:hidden property="reviewed" styleId="reviewed" name="admissionFormForm"/>
	<html:hidden property="applicationConfirm" value="true" />
	<html:hidden property="programId" styleId="programId" name="admissionFormForm"/>
	<html:hidden property="programYear" styleId="programYear" name="admissionFormForm"/>
	<html:hidden property="checkReligionId" styleId="checkReligionId" name="admissionFormForm"/>
	
<%String dynaMandate=""; %>
 <logic:equal value="true" property="onlineApply" name="admissionFormForm">
	<%dynaMandate="<span class='Mandatory'>*</span>"; %>
</logic:equal>
	<input type="hidden" name="applicationId"
		value='<bean:write name="admissionFormForm" property="applicantDetails.applicationId" />' />
	<input type="hidden" name="courseId" id="courseId" value='<bean:write	name="admissionFormForm" property="applicantDetails.course.id" />' />
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
					<div align="left"><strong class="boxheader"> <bean:message key="knowledgepro.applicationform"/></strong></div>
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
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message key="admissionForm.edit.maindetails.label"/> </td>
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
											<nested:select property="applicantDetails.selectedCourse.programTypeId" styleId="programtypecode" styleClass="combo" disabled="true">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection property="editProgramtypes" name="admissionFormForm" label="programTypeName" value="programTypeId"/>
											</nested:select>
											</td>
											<td width="99" height="23" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.program" />:</div>
											</td>
											<td width="188" height="23" class="row-even" align="left">&nbsp;
											<nested:select property="applicantDetails.selectedCourse.programId" styleId="programcode" styleClass="combo" disabled="true">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection property="editprograms" name="admissionFormForm" label="name" value="id"/>
											</nested:select>
											</td>
											<td width="121" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.course" />:</div>
											</td>
											<td width="208" class="row-even" align="left">&nbsp;
											<nested:select property="applicantDetails.selectedCourse.id" styleId="coursecode" styleClass="combo" disabled="true">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection property="editcourses" name="admissionFormForm" label="name" value="id"/>
											</nested:select>	
											</td>
										</tr>
										<tr class="row-even">
											<td height="24" class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="admissionForm.application.challan.label" /></div>
											</td>
											<td height="24" class="row-white" align="left">&nbsp;
												<nested:text property="applicantDetails.challanRefNo" size="15" maxlength="30"></nested:text>
											</td>
											<td class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="knowledgepro.admission.journalNo" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;
											<nested:text property="applicantDetails.journalNo" size="15" maxlength="30"></nested:text>
											</td>
											<td height="24" class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="admissionForm.application.date.label" /></div>
											</td>
											<td height="24" class="row-white" align="left">&nbsp;
											<nested:text property="applicantDetails.challanDate" styleId="challanDate" size="15" maxlength="15" readonly="true"></nested:text>
 					<script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'admissionFormForm',
								// input name
								'controlname' :'challanDate'
							});
						</script><bean:message key="admissionForm.application.dateformat.label"/>
											</td>
										</tr>
										<tr class="row-even">
											<td height="19" class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="knowledgepro.admission.amount" /></div>
											</td>
											<td height="19" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.amount" size="8" maxlength="8"></nested:text>
											</td>
											<td height="19" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.branchCode"/></div></td>
											<td height="19" align="left"><nested:text property="applicantDetails.bankBranch"  size="15" maxlength="20"></nested:text></td>
											<td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.mode"/></div></td>
											<td height="19" align="left">
												<nested:select property="mode" name="admissionFormForm" styleClass="combo" styleId="modeId">
													<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
													<html:option value="Pdf">Pdf</html:option>
													<html:option value="Direct">Direct</html:option>
												</nested:select>

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
												</table>--%>
													<nested:text property="applicantDetails.personalData.firstName" size="30" maxlength="90"></nested:text>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.dob.label"/><bean:message key="admissionForm.application.dateformat.label"/>
                          <br/><bean:message key="knowledgepro.applicationform.dob.format"/></div>
											</td>
											<td height="30" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.dob" styleId="dateOfBirth" size="11" maxlength="11" ></nested:text>
					                              <script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'admissionFormForm',
													// input name
													'controlname' :'dateOfBirth'
												});
											</script><bean:message key="admissionForm.application.dateformat.label"/>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="admissionForm.studentinfo.birthplace.label" /></div>
											</td>
											<td height="30" class="row-even" align="left">
												<nested:text property="applicantDetails.personalData.birthPlace" size="15" maxlength="50"></nested:text>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="admissionForm.studentinfo.birthcountry.label" /></div>
											</td>
											<td height="35" class="row-even" align="left">
												<nested:select property="applicantDetails.personalData.birthCountry" styleClass="combo" styleId="birthCountry" onchange="getStates(this.value,'birthState');">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="countries" label="name" value="id"/>
												</nested:select>
											</td>
										</tr>
										<tr>
											<td height="22" class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="admissionForm.studentinfo.birthstate.label" /></div>
											</td>
											<td height="22" class="row-even" align="left">
												<%String dynastyle=""; %>
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr><td><logic:notEqual value="Other" property="applicantDetails.personalData.birthState" name="admissionFormForm">
												<input type="hidden" id="tempState" name="tempState" value='<bean:write name="admissionFormForm" property="applicantDetails.personalData.birthState"/>' />
												<nested:select property="applicantDetails.personalData.birthState" styleClass="combo" styleId="birthState" onchange="funcOtherShowHide('birthState','otherBirthState')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<c:if test="${admissionFormForm.applicantDetails.personalData.birthCountry != null && admissionFormForm.applicantDetails.personalData.birthCountry != ''}">
			                           					<c:set var="birthStateMap" value="${baseActionForm.collectionMap['birthStateMap']}"/>
		                            		    	 	<c:if test="${birthStateMap != null}">
		                            		    	 		<html:optionsCollection name="birthStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                        				</c:if>
													<logic:notEmpty property="editStates" name="admissionFormForm">
													<nested:optionsCollection property="editStates" label="name" value="id"/>
													</logic:notEmpty>
													<html:option value="Other">Other</html:option>
												</nested:select>
												<%dynastyle="display:none;"; %>
												</logic:notEqual>
												<logic:equal value="Other" property="applicantDetails.personalData.birthState" name="admissionFormForm">
												<nested:select property="applicantDetails.personalData.birthState" styleClass="combo" styleId="birthState" onchange="funcOtherShowHide('birthState','otherBirthState')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<c:if test="${admissionFormForm.applicantDetails.personalData.birthCountry != null && admissionFormForm.applicantDetails.personalData.birthCountry != ''}">
			                           					<c:set var="birthStateMap" value="${baseActionForm.collectionMap['birthStateMap']}"/>
		                            		    	 	<c:if test="${birthStateMap != null}">
		                            		    	 		<html:optionsCollection name="birthStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                        				</c:if>
													<logic:notEmpty property="editStates" name="admissionFormForm">
													<nested:optionsCollection property="editStates" label="name" value="id"/>
													</logic:notEmpty>
													<html:option value="Other">Other</html:option>
												</nested:select>
													<%dynastyle="display:block;"; %>
												</logic:equal></td></tr>
												<tr><td><nested:text property="applicantDetails.personalData.stateOthers" size="10" maxlength="30" styleId="otherBirthState" style="<%=dynastyle %>"></nested:text></td></tr>
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
												key="admissionForm.studentinfo.residentcatg.label" /><BR><bean:message key="admissionForm.studentinfo.residentcatg.label2"/></div>
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
												<logic:notEqual value="Other" property="applicantDetails.personalData.religionId" name="admissionFormForm">
													<%dynastyle="display:none;"; %>
												<nested:select property="applicantDetails.personalData.religionId" styleClass="combo" styleId="religions" onchange="getSubReligion(this.value,'Subreligions');funcOtherShowHide('religions','otherReligion');funcReligionShowHide(this.value);">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="religions" label="religionName" value="religionId"/>
													<html:option value="Other">Other</html:option>
												</nested:select>
												</logic:notEqual>
												<logic:equal value="Other" property="applicantDetails.personalData.religionId" name="admissionFormForm">
													<%dynastyle="display:block;"; %>												
												<nested:select property="applicantDetails.personalData.religionId" styleClass="combo" styleId="religions" onchange="getSubReligion(this.value,'Subreligions');funcOtherShowHide('religions','otherReligion');funcReligionShowHide(this.value);">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="religions" label="religionName" value="religionId"/>
													<html:option value="Other">Other</html:option>
												</nested:select>
												
												</logic:equal>
													</td></tr>
												<tr><td><nested:text property="applicantDetails.personalData.religionOthers" size="10" maxlength="30" styleId="otherReligion" style="<%=dynastyle %>"></nested:text></td></tr>
												</table>
											</td>
										</tr>
										<logic:equal value="true" property="casteDisplay" name="admissionFormForm">
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.subreligion.label" /></div>
											</td>
											<td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr><td>
											<logic:notEqual value="Other" property="applicantDetails.personalData.subReligionId" name="admissionFormForm">
											<%dynastyle="display:none;"; %>
											<nested:select property="applicantDetails.personalData.subReligionId" styleClass="combo" styleId="Subreligions"  onchange="funcOtherShowHide('Subreligions','otherSubReligion')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<c:if test="${admissionFormForm.applicantDetails.personalData.religionId != null && admissionFormForm.applicantDetails.personalData.religionId != ''}">
			                           					<c:set var="subReligionMap" value="${baseActionForm.collectionMap['subReligionMap']}"/>
		                            		    	 	<c:choose>
		                            		    	 	<c:when test="${subReligionMap != null}">
		                            		    	 		<html:optionsCollection name="subReligionMap" label="value" value="key"/>
															<html:option value="Other"><bean:message key="knowledgepro.admin.Other"/></html:option>
		                            		    	 	</c:when> 
														<c:otherwise>
															<logic:notEmpty property="subReligions" name="admissionFormForm">
															<nested:optionsCollection property="subReligions" label="name" value="id"/></logic:notEmpty>
															<html:option value="Other">Other</html:option>	
														</c:otherwise>
														</c:choose>
			                      					 </c:if>
													
													
												</nested:select>
											</logic:notEqual>
											<logic:equal value="Other" property="applicantDetails.personalData.subReligionId" name="admissionFormForm">
											<%dynastyle="display:block;"; %>
											<nested:select property="applicantDetails.personalData.subReligionId" styleClass="combo" styleId="Subreligions" onchange="funcOtherShowHide('Subreligions','otherSubReligion')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<html:option value="Other">Other</html:option>
												</nested:select>
												
											</logic:equal></td></tr>
												<tr><td><nested:text property="applicantDetails.personalData.religionSectionOthers" size="10" maxlength="30" styleId="otherSubReligion" style="<%=dynastyle %>"></nested:text></td></tr>
												</table>
											</td>
										</tr>
										</logic:equal>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.castcatg.label" /></div>
											</td>
											<td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr><td>
												<logic:notEqual value="Other" property="applicantDetails.personalData.casteId" name="admissionFormForm">
												<%dynastyle="display:none;"; %>
												<nested:select property="applicantDetails.personalData.casteId" styleClass="combo" styleId="castes" onchange="funcOtherShowHide('castes','otherCastCatg')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="casteList" label="casteName" value="casteId"/>
													<html:option value="Other">Other</html:option>
												</nested:select>
												</logic:notEqual>
												<logic:equal value="Other" property="applicantDetails.personalData.casteId" name="admissionFormForm">
												<%dynastyle="display:block;"; %>
												<nested:select property="applicantDetails.personalData.casteId" styleClass="combo" styleId="castes" onchange="funcOtherShowHide('castes','otherCastCatg')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection property="casteList" label="casteName" value="casteId"/>
													<html:option value="Other">Other</html:option>
												</nested:select>
												
												</logic:equal></td></tr>
												<tr><td><nested:text property="applicantDetails.personalData.casteOthers" size="10" maxlength="30" styleId="otherCastCatg" style="<%=dynastyle %>"></nested:text></td></tr>
												</table>
											</td>
										</tr>
				<%--	<logic:equal value="true" property="viewextradetails" name="admissionFormForm">
					
   					<tr>
   					 <td colspan="2">
   					 
   					 
   					 <table id="parish_description" style="display:none;" width="100%">
                          <tr >
                              <td height="20" width="50%" class="row-odd">Dioceses</td>
                             <td height="20" width="200%" class="row-even" align="left">
                              <nested:select property="applicantDetails.personalData.dioceseId" styleId="diocese" styleClass="comboLarge" onchange="getParishValueByDiose(this.value);">
									<option value="">-Select-</option>
										<html:optionsCollection property="dioceseList" name="admissionFormForm" label="name" value="id"/>
										
									
								</nested:select></td>
						</tr>
	                      <tr >
                               <td height="20" width="50%" class="row-odd">Parish</td>
                              <td height="20" width="200%" class="row-even" align="left">
                              <nested:select property="applicantDetails.personalData.parishId" styleId="parish" styleClass="comboLarge">
									<option value="">-Select-</option>
										<html:optionsCollection property="parishList" name="admissionFormForm" label="name" value="id"/>
										
									
								</nested:select></td>
						</tr>
						</table>
						
						</td>
						</tr>
		
		</logic:equal>	--%>	
		<logic:equal value="true" property="viewextradetails" name="admissionFormForm">
						<tr id="dioces_description" style="display:none;" >
                              <td height="20" width="100%" class="row-odd"><div align="right">Dioceses</div></td>
                             <td height="20" width="100%" class="row-even" align="left">
                              <nested:select property="applicantDetails.personalData.dioceseId" styleId="diocese" styleClass="comboLarge" onchange="getParishValueByDiose(this.value);">
									<option value="">-Select-</option>
										<html:optionsCollection property="dioceseList" name="admissionFormForm" label="name" value="id"/>
										
									
								</nested:select></td>
						</tr>
	                      <tr id="parish_description" style="display:none;" >
                               <td height="20" width="100%" class="row-odd"><div align="right">Parish</div></td>
                              <td height="20" width="100%" class="row-even" align="left">
                              <nested:select property="applicantDetails.personalData.parishId" styleId="parish" styleClass="comboLarge">
									<option value="">-Select-</option>
										<html:optionsCollection property="parishList" name="admissionFormForm" label="name" value="id"/>
										
									
								</nested:select></td>
						</tr>
	
		</logic:equal>
								
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
												<logic:equal value="true" property="applicantDetails.personalData.sportsPerson" name="admissionFormForm">
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
											<logic:equal value="true" property="applicantDetails.personalData.handicapped" name="admissionFormForm">
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
                        				 <logic:equal value="true" property="viewextradetails" name="admissionFormForm">
                        <tr>
						    <%String dynaStyle5="display:none;"; %>
									<logic:equal value="true" property="ncccertificate" name="admissionFormForm">
										<%dynaStyle5="display:block;"; %>
									</logic:equal>
					        <td height="17" class="row-odd" width="40%"><div align="right">Holder of NCC  Certificate</div></td>
                           <td height="17" class="row-even" align="left">
                           <!-- <input type="hidden" id="hiddenncccertificate" name="hiddenncccertificate" value='<bean:write name="admissionFormForm" property="applicantDetails.personalData.ncccertificate"/>'/>
                           --> <nested:radio property="applicantDetails.personalData.ncccertificate" value="true" onclick="showNcccertificate()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
							<nested:radio property="applicantDetails.personalData.ncccertificate" value="false" onclick="hideNcccertificate()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
							<div id="ncccertificate_description" style="<%=dynaStyle5 %>">
							Grade of Certificate
	                      <input type="hidden" id="nccgrade" name="nccgrade" value='<bean:write name="admissionFormForm" property="applicantDetails.personalData.nccgrades"/>'/>
                         <nested:select property="applicantDetails.personalData.nccgrades" styleClass="combo" styleId="nccgrade">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<html:option value="A">A</html:option>
											<html:option value="B">B</html:option>
											<html:option value="C">C</html:option>
									</nested:select>
						
								

                        </tr>
						
						
										
										
                        				
                        				 <tr>
                           					<td height="17" class="row-odd" width="40%"><div align="right">Holder of NSS Certificate</div></td>
                           					<td height="17" class="row-even" align="left"><nested:radio property="applicantDetails.personalData.nsscertificate" value="true" ><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
											<nested:radio property="applicantDetails.personalData.nsscertificate" value="false" ><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
											
											
											</td>
                        				</tr>
                        				 <tr>
                           					<td height="17" class="row-odd" width="40%"><div align="right">If an ex-service-man or Widow or child of a  jawan</div></td>
                           					<td height="17" class="row-even" align="left"><nested:radio property="applicantDetails.personalData.exservice" value="true" ><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
											<nested:radio property="applicantDetails.personalData.exservice" value="false" ><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
												
											
											</td>
                        				</tr>
                        				</logic:equal>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
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
										<logic:equal value="true" property="displaySecondLanguage" name="admissionFormForm">
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
											<div align="right"><%=dynaMandate%><bean:message
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
											<td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              	  			<tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><nested:text property="applicantDetails.personalData.mobileNo1" size="4" maxlength="4"></nested:text></td></tr>
			                                 <tr><td> <bean:message key="admissionForm.mob.no.label"/> </td><td><nested:text property="applicantDetails.personalData.mobileNo2" size="10" maxlength="10"></nested:text></td></tr>
											</table>
			                                
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="admissionForm.studentinfo.email.label" /></div>
											</td>
											<td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr><td><nested:text property="applicantDetails.personalData.email" size="15" maxlength="50"></nested:text></td></tr>
											<tr><td>(e.g. name@yahoo.com)</td></tr>
                                 			 </table>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="admissionForm.studentinfo.confirmemail.label" /></div>
											</td>
											<td height="20" class="row-even" align="left">
												<nested:text property="confirmEmailId" size="15" maxlength="50"></nested:text> <br />
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
									<nested:select property="applicantDetails.personalData.motherTongue" styleClass="combo" >
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
	                           
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.addninfo.label2"/> <B><bean:write property="applicantDetails.course.name" name="admissionFormForm"/> </B>:</td>
								<td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.courseOptReason" size="20" maxlength="100"  ></nested:text>
								</td>
								
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.weakness.label"/></td>
								<td height="23" class="row-even" align="left">
									<nested:text property="applicantDetails.personalData.weakness"  size="20" maxlength="100"></nested:text>
										
								</td>
	                         	 </tr>
								<tr class="row-white">
	                           
								<td height="23" colspan="3" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.addninfo.label3"/> <B><bean:write property="applicantDetails.course.name" name="admissionFormForm"/></B>:</td>
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
	                            <td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.extracurr.label2"/></td>
								 <td height="23" class="row-even" align="left">
									
									<nested:select property="applicantDetails.personalData.extracurricularIds" styleClass="row-even" multiple="true" style="width:150px">
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
													<nested:optionsCollection property="countries" label="name" value="id"/>
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
										'formname' :'admissionFormForm',
										// input name
										'controlname' :'passportValidity'
									});
								</script>  <bean:message key="admissionForm.application.dateformat.label"/>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.residentpermit.label"/></div>
											</td>
											<td class="row-white" align="left">
											<html:text property="applicantDetails.personalData.residentPermitNo" styleId="residentPermit" styleClass="row-white" size="10" maxlength="15" ></html:text>
											</td>
										</tr>
										<tr class="row-white">
											<td width="370" height="23" colspan="2" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.applicationform.policedate.label"/>
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
												</script><bean:message key="admissionForm.application.dateformat.label"/>
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
	
	<logic:equal value="true" property="isInterviewSelectionSchedule"  name="admissionFormForm">
		<tr>
			<td colspan="2" class="heading" align="left">&nbsp;<bean:message key="knowledgepro.admission.entrance.selection"/></td>
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
									<td width="100%" height="57" valign="top">
									<table width="100%" height="57" border="0" cellpadding="0"
										cellspacing="1">
											<tr class="row-white">
						<td width="212" class="row-odd" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.entrance.Venue" /></td>
						 <td width="236" height="25" class="row-even" align="left">
						 
											<html:select property="interviewVenue" styleId='interviewVenue' styleClass="comboMedium" onchange="getDateByVenueselectionOffline(this.value)">
											  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
											 <logic:notEmpty property="interviewVenueSelection" name="admissionFormForm">
												<html:optionsCollection property="interviewVenueSelection" label="value" value="key"/>
											</logic:notEmpty>
											</html:select>			
					    </td>				
						<td width="224" height="23" class="row-odd" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.entrance.Date" /></td>
						<td width="236" height="23" class="row-even" align="left">
							<input type="hidden" id="tempDate" name="tempDate" value='<bean:write name="admissionFormForm" property="interviewSelectionDate"/>' />
							<html:select property="interviewSelectionDate" styleId='interviewSelectionDate' styleClass="comboMedium" >
							<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
							<logic:notEmpty property="interviewSelectionSchedule" name="admissionFormForm">
								<html:optionsCollection property="interviewSelectionSchedule" label="value" value="key"/>
							</logic:notEmpty>
							</html:select>
						</td>
									
						</tr>
							<tr>
						<td colspan="4"><br>I accept that the date opted is acceptable and binding on me and that Mount Carmel College is not liable to offer me an alternate date in case I am not able to attend the selection process on the date
						</td>
						</tr>
						<tr>
									<td class="heading"> &nbsp;</td>
						</tr>
						<tr>
									<td class="heading">
										<input type="hidden" name="tempChecked" id="tempChecked" value="<bean:write name="admissionFormForm" property="acceptAll"/>"/>
										<input type="checkbox" name="acceptAll" id="acceptAll">I Agree
										<script type="text/javascript">
												if(document.getElementById("tempChecked").value==true || document.getElementById("tempChecked").value=="true") {
													document.getElementById("acceptAll").checked=true;
												}
												else
												{
													document.getElementById("acceptAll").checked=false;
												}
												
												</script>
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
				</logic:equal>		
								 
				 
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
										
										<nested:iterate property="preferenceList" id="prefTo" indexId="count">
										<tr class="row-white">
											<%	String dynaJsMethod="getDynaUniquePreference('Map3',this.value,'coursePref3')";
												
											%>
											
											<td  height="27" class="row-odd" align="right" width="48%">
											<div align="right">
											<logic:equal value="1" property="prefNo" name="prefTo"><span class="Mandatory">*</span>
											<bean:message key="admissionForm.edit.firstpref.label"/></logic:equal>
											<logic:equal value="2" property="prefNo" name="prefTo"><bean:message key="admissionForm.edit.secpref.label"/></logic:equal>
											<logic:equal value="3" property="prefNo" name="prefTo"><bean:message key="admissionForm.edit.thirdpref.label"/></logic:equal>
											</div>
											</td>
											
											<td  class="row-even" align="left" width="50%">
											<div align="left">
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
												<c:set var="tempKey">Map3</c:set>	
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
										</tr>
										</nested:iterate>
								
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
	<%String dynaStyle=""; %>
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
								<html:optionsCollection name="admissionFormForm" property="occupations" label="occupationName" value="occupationId"/>
								<option value="Other" selected="selected">Other</option>
							</nested:select>
		                	<%dynaStyle="display: block;" ;%>
							</logic:equal>
							<logic:notEqual value="Other" name="exp" property="occupationId">
		                  	<nested:select property="occupationId" styleClass="combo" styleId="<%=dropOccId %>" onchange="<%=occupationShowHide %>">
								<html:option value="">- Select -</html:option>
								<html:optionsCollection name="admissionFormForm" property="occupations" label="occupationName" value="occupationId"/>
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
												<nested:text property="applicantDetails.personalData.currentAddressLine1"  size="45" maxlength="35"></nested:text>
											</td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp;
												<nested:text property="applicantDetails.personalData.currentAddressLine2" size="55" maxlength="40"></nested:text>
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
											<td class="row-white" align="left">
												<nested:select property="applicantDetails.personalData.currentCountryId" styleClass="combo" styleId="currentCountryName" onchange="getTempAddrStates(this.value,'currentStateName');">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection property="countries" label="name" value="id"/>
											</nested:select>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr><td>
											<logic:notEqual value="Other" property="applicantDetails.personalData.currentStateId" name="admissionFormForm">
											<%dynastyle="display:none;"; %>
											<input type="hidden" id="tempState1" name="tempState1" value='<bean:write name="admissionFormForm" property="applicantDetails.personalData.currentStateId"/>' />
											<nested:select property="applicantDetails.personalData.currentStateId" styleClass="combo" styleId="currentStateName" onchange="funcOtherShowHide('currentStateName','otherTempAddrState')">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<c:if test="${admissionFormForm.applicantDetails.personalData.currentCountryId != null && admissionFormForm.applicantDetails.personalData.currentCountryId!= ''}">
			                           					<c:set var="tempAddrStateMap" value="${baseActionForm.collectionMap['tempAddrStateMap']}"/>
		                            		    	 	<c:if test="${tempAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="tempAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                        			</c:if>
												<logic:notEmpty property="editCurrentStates" name="admissionFormForm">
												<nested:optionsCollection property="editCurrentStates" label="name" value="id"/>
												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
											</nested:select>
											</logic:notEqual>
											<logic:equal value="Other" property="applicantDetails.personalData.currentStateId" name="admissionFormForm">
											<%dynastyle="display:block;"; %>
											<nested:select property="applicantDetails.personalData.currentStateId" styleClass="combo" styleId="currentStateName" onchange="funcOtherShowHide('currentStateName','otherTempAddrState')">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<c:if test="${admissionFormForm.applicantDetails.personalData.currentCountryId != null && admissionFormForm.applicantDetails.personalData.currentCountryId!= ''}">
			                           					<c:set var="tempAddrStateMap" value="${baseActionForm.collectionMap['tempAddrStateMap']}"/>
		                            		    	 	<c:if test="${tempAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="tempAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                        			</c:if>
												<logic:notEmpty property="editCurrentStates" name="admissionFormForm">
												<nested:optionsCollection property="editCurrentStates" label="name" value="id"/>
												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
											</nested:select>
											
											</logic:equal></td></tr>
												<tr><td><nested:text property="applicantDetails.personalData.currentAddressStateOthers" size="10" maxlength="30" styleId="otherTempAddrState" style="<%=dynastyle %>"></nested:text></td></tr>
												</table>
											</td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.zipCode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;<nested:text property="applicantDetails.personalData.currentAddressZipCode"></nested:text>
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
							<td colspan="2" class="heading" align="left">&nbsp;<div id="currLabel">
						<bean:message
								key="admissionForm.studentinfo.permAddr.label" /></div></td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0"  id="currTable">
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
											<div align="right"><%=dynaMandate%><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="236" height="20" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.permanentAddressLine1" size="45" maxlength="35"></nested:text>
											</td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="244" class="row-even" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.permanentAddressLine2" size="55" maxlength="40"></nested:text>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td height="20" class="row-white" align="left">&nbsp;
											<nested:text property="applicantDetails.personalData.permanentCityName" size="15" maxlength="30"></nested:text>
											</td>
											<td class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td class="row-white" align="left">
											<nested:select property="applicantDetails.personalData.permanentCountryId" styleClass="combo" styleId="permanentCountryName" onchange="getPermAddrStates(this.value,'permanentStateName');">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection property="countries" label="name" value="id"/>
											</nested:select>
											</td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td height="20" class="row-even" align="left">&nbsp;
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr><td>
											<logic:notEqual value="Other" property="applicantDetails.personalData.permanentStateId" name="admissionFormForm">
											<%dynastyle="display:none;"; %>
											<input type="hidden" id="tempState2" name="tempState2" value='<bean:write name="admissionFormForm" property="applicantDetails.personalData.permanentStateId"/>' />
											<nested:select property="applicantDetails.personalData.permanentStateId" styleClass="combo" styleId="permanentStateName" onchange="funcOtherShowHide('permanentStateName','otherPermAddrState')">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<c:if test="${admissionFormForm.applicantDetails.personalData.permanentCountryId != null && admissionFormForm.applicantDetails.personalData.permanentCountryId!= ''}">
			                           					<c:set var="permAddrStateMap" value="${baseActionForm.collectionMap['permAddrStateMap']}"/>
		                            		    	 	<c:if test="${permAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="permAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                        			</c:if>
												<logic:notEmpty property="editPermanentStates" name="admissionFormForm">
												<nested:optionsCollection property="editPermanentStates" label="name" value="id"/>
												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
											</nested:select>
											</logic:notEqual>
											<logic:equal value="Other" property="applicantDetails.personalData.permanentStateId" name="admissionFormForm">
											<%dynastyle="display:block;"; %>
											<nested:select property="applicantDetails.personalData.permanentStateId" styleClass="combo" styleId="permanentStateName" onchange="funcOtherShowHide('permanentStateName','otherPermAddrState')">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
													<c:if test="${admissionFormForm.applicantDetails.personalData.permanentCountryId != null && admissionFormForm.applicantDetails.personalData.permanentCountryId!= ''}">
			                           					<c:set var="permAddrStateMap" value="${baseActionForm.collectionMap['permAddrStateMap']}"/>
		                            		    	 	<c:if test="${permAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="permAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                       					 </c:if>
												<logic:notEmpty property="editPermanentStates" name="admissionFormForm">
												<nested:optionsCollection property="editPermanentStates" label="name" value="id"/>
												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
											</nested:select>
											
											</logic:equal></td></tr>
												<tr><td><nested:text property="applicantDetails.personalData.permanentAddressStateOthers" size="10" maxlength="30" styleId="otherPermAddrState" style="<%=dynastyle %>"></nested:text></td></tr>
												</table>
											</td>
											<td class="row-odd">
											<div align="right"><%=dynaMandate%><bean:message
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
										


									<tr>
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td height="25" class="row-odd" align="center"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.qualification" /></td>
											<td class="row-odd">Exam Name</td>
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
											<td class="row-odd">
											<div align="center"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.marksObtained" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.maxMark" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.attempts" /></div>
											</td>
											<td class="row-odd"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.prevregno.label"/></td>
											
										</tr>
										<c:set var="temp" value="0" />
										<nested:iterate property="applicantDetails.ednQualificationList" id="ednQualList" indexId="count">
										<%
											
											String dynaid="UniversitySelect"+count;
											String otheruniversityid="OtherUniversity"+count;
											String otherinstituteid="OtherInstitute"+count;
											String dynaYearId="YOP"+count;
											String dynaMonthId="Month"+count;
											String dynaExamId="Exam"+count;
											String dynaAttemptId="Attempt"+count;
											String dynaStateId="State"+count;
											String instituteId=count+"Institute";
											String collegeJsMethod="getColleges('Map"+count+"',this,"+count+");";
											String insthide="funcOtherShowHide('"+instituteId+"','"+otherinstituteid+"')";
											
											
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
														<div align="center">
															<logic:equal value="true" name="ednQualList" property="examConfigured">
															<c:set var="dexmid"><%=dynaExamId %></c:set>
														<nested:select property="selectedExamId" styleClass="comboSmall" styleId='<%=dynaExamId %>'>
															<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
															<logic:notEmpty name="ednQualList" property="examTos">
																<html:optionsCollection name="ednQualList" property="examTos" label="name" value="id"/>
															</logic:notEmpty>	
														</nested:select>
															<script type="text/javascript">
																var exmid= '<nested:write property="selectedExamId"/>';
																document.getElementById("<c:out value='${dexmid}'/>").value = exmid;
															</script>
															</logic:equal>
														</div>
														</td>


														<td width="20%" class="row-even">
														<div align="left">
														<%
															String dynahide=collegeJsMethod+"funcOtherShowHide('"+dynaid+"','"+otheruniversityid+"')";
														%>
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr><td>
														<logic:notEqual value="Other" property="universityId" name="ednQualList">
														<%dynastyle="display:none;"; %>
														<nested:select property="universityId" styleClass="combo"  styleId="<%=dynaid %>" onchange='<%=dynahide %>'>
															<option value=""><bean:message key="knowledgepro.admin.select"/></option>
															<nested:optionsCollection property="universityList" name="ednQualList" label="name" value="id"/>
															<html:option value="Other">Other</html:option>
														</nested:select>
														</logic:notEqual>
														<logic:equal value="Other" property="universityId" name="ednQualList">
														<%dynastyle="display:block;"; %>
														<nested:select property="universityId" styleClass="combo" styleId="<%=dynaid %>" onchange='<%=dynahide %>'>
															<option value=""><bean:message key="knowledgepro.admin.select"/></option>
															
															<html:option value="Other">Other</html:option>
														</nested:select>
														
														</logic:equal></td></tr>
														<tr><td><nested:text property="universityOthers" size="10" maxlength="50" styleId="<%=otheruniversityid %>" style="<%=dynastyle %>"></nested:text></td></tr>
														</table>
														</div>
														</td>
														<td width="22%" class="row-even">
														<div align="left">
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr><td>
														<logic:notEqual value="Other" property="institutionId" name="ednQualList">
														<%dynastyle="display:none;"; %>
               										 	<c:set var="tempUniversity"><nested:write property="universityId"/></c:set>
														<nested:select property="institutionId" styleClass="combo" styleId='<%=instituteId %>' onchange='<%=insthide %>'>
															<option value=""><bean:message key="knowledgepro.admin.select"/></option>
															<c:set var="tempKey">Map<c:out value="${count}"/></c:set>	
															<c:if test="${tempUniversity != null && tempUniversity != '' && tempUniversity != 'Other'}">
                            								 	<c:set var="Map" value="${baseActionForm.collectionMap[tempKey]}"/>
																<c:if test="${Map != null}">
																<html:optionsCollection name="Map" label="value" value="key"/>
																<html:option value="Other">Other</html:option>
																</c:if>
															</c:if>
															<logic:notEmpty property="instituteList" name="ednQualList">
															<nested:optionsCollection property="instituteList" name="ednQualList" label="name" value="id"/>
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														
															
														</nested:select>
														</logic:notEqual>
														<logic:equal value="Other" property="institutionId" name="ednQualList">
														<%dynastyle="display:block;"; %>
														<nested:select property="institutionId" styleClass="combo" styleId='<%=instituteId %>' onchange='<%=insthide %>'>
															<option value=""><bean:message key="knowledgepro.admin.select"/></option>
															<html:option value="Other">Other</html:option>
														</nested:select>
														
														</logic:equal></td></tr>
														<tr><td><nested:text property="otherInstitute" size="10" maxlength="50" styleId="<%=otherinstituteid %>" style="<%=dynastyle %>"></nested:text></td></tr>
														</table>
														</div>
														</td>
														<td width="16%" class="row-even">
															<c:set var="dstateid"><%=dynaStateId %></c:set>
														<nested:select property="stateId" styleClass="combo" styleId='<%=dynaStateId %>'>
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
															
														<td width="16%" class="row-even">
															<c:set var="dyopid"><%=dynaYearId %></c:set>
														<nested:select property="yearPassing" styleClass="comboSmall" styleId='<%=dynaYearId %>'>
															<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
															<cms:renderYear normalYear="true"></cms:renderYear>
														</nested:select>
															<script type="text/javascript">
																var yopid= '<nested:write property="yearPassing"/>';
																document.getElementById("<c:out value='${dyopid}'/>").value = yopid;
															</script>
														</td>
														<td width="16%" class="row-even">
															<c:set var="dmonid"><%=dynaMonthId %></c:set>
														<nested:select property="monthPassing" styleClass="comboSmall" styleId='<%=dynaMonthId %>'>
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
															<script type="text/javascript">
																var monid= '<nested:write property="monthPassing"/>';
																document.getElementById("<c:out value='${dmonid}'/>").value = monid;
															</script>
														</td>
															<bean:define id="countIndex" name="ednQualList" property="countId"></bean:define>
															<input type="hidden" id="countID" name="countID" >
														
														<logic:equal value="true" property="semesterWise" name="ednQualList">
														<logic:equal value="false" property="consolidated" name="ednQualList">
														<%String semsterMarkLink="admissionFormSubmit.do?method=initSemesterMarkPage&countID="+countIndex; %>
														<td class="row-even" colspan="2">
														<div align="center"><a href="#" onclick="detailSemesterSubmit('<%=countIndex %>')">Semester Marks Entry</a></div>
														</td>
														
														</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated" name="ednQualList">
														<logic:equal value="false" property="semesterWise" name="ednQualList">
														<td class="row-even" colspan="2">
														
														<%String detailMarkLink="admissionFormSubmit.do?method=initDetailMarkPage&countID="+countIndex; %>
														<div align="center"><a href="#" onclick="detailSubmit('<%=countIndex %>')">Detailed Marks Entry</a></div>
														</td>
														
														</logic:equal>
														</logic:equal>
														<logic:notEqual value="true" property="semesterWise" name="ednQualList">
														<logic:notEqual value="false" property="consolidated" name="ednQualList">
														<td class="row-even">
														<div align="center"><nested:text property="marksObtained" size="5" maxlength="9"></nested:text></div>
														</td>
														<td class="row-even">
														<div align="center"><nested:text property="totalMarks" size="5" maxlength="9"></nested:text></div>
														</td>
														</logic:notEqual>
														</logic:notEqual>
														<td width="9%" class="row-even">
														<div align="center">
														<nested:select property="noOfAttempts" styleClass="comboSmall" styleId="noOfAttempts">
															<html:option value="1">1</html:option>
															<html:option value="2">2</html:option>
															<html:option value="3">3</html:option>
															<html:option value="4">4</html:option>
															<html:option value="5">5</html:option>
															<html:option value="6">6</html:option>
														</nested:select>
														</div>
														</td>
														<td width="9%" class="row-even">
														<div align="center">
															<logic:equal value="true" name="ednQualList" property="lastExam">
															<nested:text property="previousRegNo" size="10" maxlength="15"/></logic:equal>
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
															<td class="row-white">
														<div align="center">
															<logic:equal value="true" name="ednQualList" property="examConfigured">
															<c:set var="dexmid"><%=dynaExamId %></c:set>
														<nested:select property="selectedExamId" styleClass="comboSmall" styleId='<%=dynaExamId %>'>
															<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
															<logic:notEmpty name="ednQualList" property="examTos">
																<html:optionsCollection name="ednQualList" property="examTos" label="name" value="id"/>
															</logic:notEmpty>	
														</nested:select>
															<script type="text/javascript">
																exmid= '<nested:write property="selectedExamId"/>';
																document.getElementById("<c:out value='${dexmid}'/>").value = exmid;
															</script>
															</logic:equal>
														</div>
														</td>

														<td class="row-white">
														<div align="left">
														<%
															String dynahide=collegeJsMethod+"funcOtherShowHide('"+dynaid+"','"+otheruniversityid+"')";
														%>
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr><td>
														<logic:notEqual value="Other" property="universityId" name="ednQualList">
														<%dynastyle="display:none;"; %>
														<nested:select property="universityId" styleClass="combo"  styleId='<%=dynaid %>' onchange='<%=dynahide %>'>
															<option value=""><bean:message key="knowledgepro.admin.select"/></option>
															<nested:optionsCollection property="universityList" name="ednQualList" label="name" value="id"/>
															<html:option value="Other">Other</html:option>
														</nested:select>
														</logic:notEqual>
														<logic:equal value="Other" property="universityId" name="ednQualList">
														<%dynastyle="display:block;"; %>
														<nested:select property="universityId" styleClass="combo" styleId="<%=dynaid %>" onchange='<%=dynahide %>'>
															<option value=""><bean:message key="knowledgepro.admin.select"/></option>
															
															<html:option value="Other">Other</html:option>
														</nested:select>
														
														</logic:equal></td></tr>
														<tr><td><nested:text property="universityOthers" size="10" maxlength="50" styleId="<%=otheruniversityid %>" style="<%=dynastyle %>"></nested:text></td></tr>
														</table>
														</div>
														</td>
														<td class="row-white">
														<div align="left">
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr><td>
														<logic:notEqual value="Other" property="institutionId" name="ednQualList">
														<%dynastyle="display:none;"; %>
														<c:set var="tempUniversity"><nested:write property="universityId"/></c:set>
														<nested:select property="institutionId" styleClass="combo" styleId='<%=instituteId %>' onchange='<%=insthide %>'>
															<option value=""><bean:message key="knowledgepro.admin.select"/></option>
															<c:set var="tempKey">Map<c:out value="${count}"/></c:set>	
															<c:if test="${tempUniversity != null && tempUniversity != '' && tempUniversity != 'Other'}">
                            								 	<c:set var="Map" value="${baseActionForm.collectionMap[tempKey]}"/>
																<c:if test="${Map != null}">
																<html:optionsCollection name="Map" label="value" value="key"/>
																<html:option value="Other">Other</html:option>
																</c:if>
							
															</c:if>
															<logic:notEmpty property="instituteList" name="ednQualList">
															<nested:optionsCollection property="instituteList" name="ednQualList" label="name" value="id"/>
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
															
															
														</nested:select>
														</logic:notEqual>
														<logic:equal value="Other" property="institutionId" name="ednQualList">
														<%dynastyle="display:block;"; %>
														<nested:select property="institutionId" styleClass="combo" styleId='<%=instituteId %>' onchange='<%=insthide %>'>
															<option value=""><bean:message key="knowledgepro.admin.select"/></option>
															<html:option value="Other">Other</html:option>
														</nested:select>
														
														</logic:equal></td></tr>
														<tr><td><nested:text property="otherInstitute" size="10" maxlength="50" styleId="<%=otherinstituteid %>" style="<%=dynastyle %>"></nested:text></td></tr>
														</table>
														</div>
														</td>
														<td class="row-white">
														<c:set var="dstateid"><%=dynaStateId %></c:set>
														<nested:select property="stateId" styleClass="combo" styleId='<%=dynaStateId %>'>
															<option value=""><bean:message key="knowledgepro.admin.select"/></option>
															<logic:notEmpty name="admissionFormForm" property="ednStates">
															 <nested:optionsCollection name="admissionFormForm" property="ednStates" label="name" value="id"/>
															</logic:notEmpty>
															<option value="OUTSIDEINDIA"><bean:message key="admissionForm.education.outsideindia.label"/></option>
														</nested:select>
															<script type="text/javascript">
																stid= '<nested:write property="stateId"/>';
																document.getElementById("<c:out value='${dstateid}'/>").value = stid;
															</script>
														</td>
														<td class="row-white">
														<c:set var="dyopid"><%=dynaYearId %></c:set>
														<nested:select property="yearPassing" styleClass="comboSmall" styleId='<%=dynaYearId %>'>
															<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
															<cms:renderYear normalYear="true"></cms:renderYear>
														</nested:select>
															<script type="text/javascript">
																yopid= '<nested:write property="yearPassing"/>';
																document.getElementById("<c:out value='${dyopid}'/>").value = yopid;
															</script>
														</td>
														<td class="row-white">
															<c:set var="dmonid"><%=dynaMonthId %></c:set>
														<nested:select property="monthPassing" styleClass="comboSmall" styleId='<%=dynaMonthId %>'>
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
															<script type="text/javascript">
																monid= '<nested:write property="monthPassing"/>';
																document.getElementById("<c:out value='${dmonid}'/>").value = monid;
															</script>
														</td>
														<bean:define id="countIndex" name="ednQualList" property="countId"></bean:define>
															<input type="hidden" id="countID" name="countID" >
														<logic:equal value="true" property="semesterWise" name="ednQualList">
														<logic:equal value="false" property="consolidated" name="ednQualList">
														<td class="row-white" colspan="2">
														<%String semsterMarkLink="admissionFormSubmit.do?method=initSemesterMarkEditPage&editcountID="+countIndex; %>
														<div align="center"><a href="#" onclick="detailSemesterSubmit('<%=countIndex %>')">Semester Marks Edit</a></div>
														</td>
														
														</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated" name="ednQualList">
														<logic:equal value="false" property="semesterWise" name="ednQualList">
														<td class="row-white" colspan="2">
														
														<%String detailMarkLink="admissionFormSubmit.do?method=initDetailMarkEditPage&editcountID="+countIndex; %>
														<div align="center"><a href="#" onclick="detailSubmit('<%=countIndex %>')">Detailed Marks Edit</a></div>
														</td>
														
														</logic:equal>
														</logic:equal>
														<logic:notEqual value="true" property="semesterWise" name="ednQualList">
														<logic:notEqual value="false" property="consolidated" name="ednQualList">
														<td class="row-white">
														<div align="center"><nested:text property="marksObtained" size="5" maxlength="9"></nested:text></div>
														</td>
														<td class="row-white">
														<div align="center"><nested:text property="totalMarks" size="5" maxlength="9"></nested:text></div>
														</td>
														</logic:notEqual>
														</logic:notEqual>
														<td class="row-white">
														<div align="center">
														<nested:select property="noOfAttempts" styleClass="comboSmall" styleId="noOfAttempts">
															<html:option value="1">1</html:option>
															<html:option value="2">2</html:option>
															<html:option value="3">3</html:option>
															<html:option value="4">4</html:option>
															<html:option value="5">5</html:option>
															<html:option value="6">6</html:option>
														</nested:select>
														</div>
														</td>
														<td class="row-white">
														<div align="center">
															<logic:equal value="true" name="ednQualList" property="lastExam">
															<nested:text property="previousRegNo" size="10" maxlength="15"/></logic:equal>
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
											<td height="25" class="row-even"><nested:text property="applicantDetails.tcNo" size="6" maxlength="10"></nested:text></td>
											<td class="row-odd">
											<div align="left"><bean:message key="admissionForm.education.TCDate.label"/></div>
											</td>
											<td class="row-even" colspan="2">
											<div align="left"><nested:text property="applicantDetails.tcDate" styleId="tcdate" size="10" maxlength="10"></nested:text><script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'admissionFormForm',
													// input name
													'controlname' :'tcdate'
												});
											</script><bean:message key="admissionForm.application.dateformat.label"/> </div>
											</td>
											
											<td class="row-odd"><bean:message key="admissionForm.education.markcard.label"/></td>
											<td class="row-even">
											<div align="center"><nested:text property="applicantDetails.markscardNo" size="6" maxlength="10"></nested:text></div>
											</td>
											<td class="row-odd">
											<div align="left"><bean:message key="admissionForm.education.markcarddate.label"/></div>
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
													</script><bean:message key="admissionForm.application.dateformat.label"/></div>
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
									<tr>
										<td colspan="5" align="right" class="row-odd">Backlogs in previous semesters/years to be cleared</td>
										<td class="row-even" colspan="6">
											<html:radio property="backLogs" name="admissionFormForm" styleId="backLogs" value="true"><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
											<html:radio property="backLogs" name="admissionFormForm" styleId="backLogs" value="false"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio>
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
											<div align="right"><%=dynaMandate%><bean:message
												key="knowledgepro.admission.fatherName" /></div>
											</div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:select property="applicantDetails.titleOfFather" styleId='titleOfFather' styleClass="comboMedium" style="max-width:15%;">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
								              	<html:option value="Mr">Mr.</html:option>
								              	<html:option value="Late">Late.</html:option>
											</nested:select>
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
											<div align="right"><%=dynaMandate%><bean:message
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
											<td height="30" class="row-even" align="left">&nbsp;<nested:select property="applicantDetails.personalData.fatherOccupationId" styleClass="combo" styleId="fatherOccupation" onchange="displayOtherForFather(this.value)">
													<html:option value="">- Select -</html:option>
													<html:optionsCollection name="admissionFormForm" property="occupations" label="occupationName" value="occupationId"/>
													<html:option value="other">Other</html:option>
												</nested:select><br/>
												 <div id="displayFatherOccupation">
														 &nbsp;<nested:text property="applicantDetails.personalData.otherOccupationFather" size="20" maxlength="50" styleId="otherOccupationFather"/>
												 </div></td>
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
											<div align="right"><%=dynaMandate%><bean:message
												key="knowledgepro.admission.motherName" /></div>
											</div>
											</td>
											<td width="55%" height="36" class="row-even" align="left">&nbsp;
											<nested:select property="applicantDetails.titleOfMother" styleId='titleOfMother' styleClass="comboMedium" style="max-width:15%;" >
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
								              	<html:option value="Mrs">Mrs.</html:option>
								              	<html:option value="Late">Late.</html:option>
											</nested:select>
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
											<div align="right"><%=dynaMandate%><bean:message
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
												  <nested:select property="applicantDetails.personalData.motherOccupationId" styleClass="combo" styleId="motherOccupation" onchange="displayOtherForMother(this.value)">
													<html:option value="">- Select -</html:option>
													<html:optionsCollection name="admissionFormForm" property="occupations" label="occupationName" value="occupationId"/>
													<html:option value="other">Other</html:option>
												</nested:select><br/>
												 <div id="displayMotherOccupation">
														&nbsp;<nested:text property="applicantDetails.personalData.otherOccupationMother" size="20" maxlength="50" styleId="otherOccupationMother"/>
												 </div>
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
											<nested:select property="applicantDetails.personalData.parentCountryId" styleClass="combo" styleId="parentCountryName" onchange="getParentAddrStates(this.value,'parentStateName')">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection property="countries" label="name" value="id"/>
											</nested:select>
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr><td class="row-white"><bean:message key="admissionForm.phone.cntcode.label"/></td><td class="row-white"><nested:text property="applicantDetails.personalData.parentPh1" size="3" maxlength="4"></nested:text></td></tr>
													<tr><td class="row-white"><bean:message key="admissionForm.phone.areacode.label"/></td><td class="row-white"><nested:text property="applicantDetails.personalData.parentPh2" size="5" maxlength="7"></nested:text></td></tr>
													<tr><td class="row-white"><bean:message key="admissionForm.phone.main.label"/></td><td class="row-white"><nested:text property="applicantDetails.personalData.parentPh3" size="10" maxlength="10"></nested:text></td></tr>
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
											<logic:notEqual value="Other" property="applicantDetails.personalData.parentStateId" name="admissionFormForm">
											<%dynastyle="display:none;"; %>
											<input type="hidden" id="tempState3" name="tempState3" value='<bean:write name="admissionFormForm" property="applicantDetails.personalData.parentStateId"/>' />
											<nested:select property="applicantDetails.personalData.parentStateId" styleClass="combo" styleId="parentStateName" onchange="funcOtherShowHide('parentStateName','OtherParentState')">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<c:if test="${admissionFormForm.applicantDetails.personalData.parentCountryId != null && admissionFormForm.applicantDetails.personalData.parentCountryId!= ''}">
			                           					<c:set var="parentAddrStateMap" value="${baseActionForm.collectionMap['parentAddrStateMap']}"/>
		                            		    	 	<c:if test="${parentAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="parentAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                       					 </c:if>
												<logic:notEmpty name="admissionFormForm" property="editParentStates">
												<nested:optionsCollection property="editParentStates" label="name" value="id"/>
												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
											</nested:select>
											</logic:notEqual>
											<logic:equal value="Other" property="applicantDetails.personalData.parentStateId" name="admissionFormForm">
											<%dynastyle="display:block;"; %>
											<nested:select property="applicantDetails.personalData.parentStateId" styleClass="combo" styleId="parentStateName" onchange="funcOtherShowHide('parentStateName','OtherParentState')">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<c:if test="${admissionFormForm.applicantDetails.personalData.parentCountryId != null && admissionFormForm.applicantDetails.personalData.parentCountryId!= ''}">
			                           					<c:set var="parentAddrStateMap" value="${baseActionForm.collectionMap['parentAddrStateMap']}"/>
		                            		    	 	<c:if test="${parentAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="parentAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                       					 </c:if>
												<logic:notEmpty name="admissionFormForm" property="editParentStates">
												<nested:optionsCollection property="editParentStates" label="name" value="id"/>
												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
											</nested:select>
											
											</logic:equal></td></tr>
												<tr><td><nested:text property="applicantDetails.personalData.parentAddressStateOthers" size="10" maxlength="50" styleId="OtherParentState" style="<%=dynastyle %>"></nested:text></td></tr>
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
											<nested:select property="applicantDetails.personalData.countryByGuardianAddressCountryId" styleClass="combo" styleId="guardianCountryName" onchange="getParentAddrStates(this.value,'guardianStateName')">
												<option value="0"><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection property="countries" label="name" value="id"/>
											</nested:select>
											</td>
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="20" class="row-white" align="left">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr><td class="row-white"><bean:message key="admissionForm.phone.cntcode.label"/></td><td class="row-white"><nested:text property="applicantDetails.personalData.guardianPh1" size="3" maxlength="4"></nested:text></td></tr>
													<tr><td class="row-white"><bean:message key="admissionForm.phone.areacode.label"/></td><td class="row-white"><nested:text property="applicantDetails.personalData.guardianPh2" size="5" maxlength="7"></nested:text></td></tr>
													<tr><td class="row-white"><bean:message key="admissionForm.phone.main.label"/></td><td class="row-white"><nested:text property="applicantDetails.personalData.guardianPh3" size="10" maxlength="10"></nested:text></td></tr>
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
											<logic:notEqual value="Other" property="applicantDetails.personalData.stateByGuardianAddressStateId" name="admissionFormForm">
											<%dynastyle="display:none;"; %>
											<input type="hidden" id="tempState4" name="tempState4" value='<bean:write name="admissionFormForm" property="applicantDetails.personalData.stateByGuardianAddressStateId"/>' />
											<nested:select property="applicantDetails.personalData.stateByGuardianAddressStateId" styleClass="combo" styleId="guardianStateName" onchange="funcOtherShowHide('guardianStateName','OtherGuardianState')">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<c:if test="${admissionFormForm.applicantDetails.personalData.countryByGuardianAddressCountryId != null && admissionFormForm.applicantDetails.personalData.countryByGuardianAddressCountryId!= ''}">
			                           					<c:set var="guardianAddrStateMap" value="${baseActionForm.collectionMap['guardianAddrStateMap']}"/>
		                            		    	 	<c:if test="${guardianAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="guardianAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                       					 </c:if>
												<logic:notEmpty name="admissionFormForm" property="editGuardianStates">
												<nested:optionsCollection property="editGuardianStates" label="name" value="id"/>
												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
											</nested:select>
											
											</logic:notEqual>
											<logic:equal value="Other" property="applicantDetails.personalData.stateByGuardianAddressStateId" name="admissionFormForm">
											<%dynastyle="display:block;"; %>
											<nested:select property="applicantDetails.personalData.stateByGuardianAddressStateId" styleClass="combo" styleId="guardianStateName" onchange="funcOtherShowHide('guardianStateName','OtherGuardianState')">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<c:if test="${admissionFormForm.applicantDetails.personalData.countryByGuardianAddressCountryId != null && admissionFormForm.applicantDetails.personalData.countryByGuardianAddressCountryId!= ''}">
			                           					<c:set var="parentAddrStateMap" value="${baseActionForm.collectionMap['guardianAddrStateMap']}"/>
		                            		    	 	<c:if test="${guardianAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="parentAddrStateMap" label="value" value="key"/>
															<html:option value="Other">Other</html:option>
		                            		    	 	</c:if> 
			                       					 </c:if>
												<logic:notEmpty name="admissionFormForm" property="editGuardianStates">
												<nested:optionsCollection property="editGuardianStates" label="name" value="id"/>
												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
											</nested:select>
											
											</logic:equal></td></tr>
												<tr><td><nested:text property="applicantDetails.personalData.guardianAddressStateOthers" size="10" maxlength="50" styleId="OtherGuardianState" style="<%=dynastyle %>"></nested:text></td></tr>
												</table>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="20" align="left">
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


						<!--<tr>
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
											
											<td height="25" class="row-odd" width="25%" align="center"><bean:message
												key="knowledgepro.admission.documents" /></td>
											<td class="row-odd" width="15%" align="center">&nbsp;</td>
											<td class="row-odd" width="60%" align="center"><bean:message
												key="knowledgepro.admission.uploadDocs" /></td>
										</tr>
										<c:set var="temp" value="0" />
										<nested:iterate name="admissionFormForm"
											property="applicantDetails.editDocuments" indexId="count" id="docList">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														
														<td width="25%" height="25" class="row-even" align="center"><nested:write
															property="printName" /></td>
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
														<td width="60%" class="row-even">
															<nested:file property="editDocument"></nested:file>
														</td>
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														
														<td height="25" width="25%" class="row-white" align="center"><nested:write
															property="printName" /></td>
														<td width="15%" class="row-white" align="center">
															<nested:equal
															value="false" property="photo">
															<nested:equal value="true"
															property="documentPresent">
															<span class="row-even"> <a
																href="javascript:downloadFile('<nested:write property="docTypeId"/>')"><bean:message key="knowledgepro.view" /></a></span>
														</nested:equal>
														</nested:equal>
														</td>
														<td width="60%" class="row-white">
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
						--><tr>
							<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="48%" height="21">
									<div align="right">
									<html:button property="" styleClass="formbutton" onclick="SubmitForm()"> <bean:message key="knowledgepro.confirm" />
									</html:button></div>
									</td>
									<td width="1%"><div align="center"></div></td>
									<td width="51%" height="45" align="left">
									<logic:equal value="false" property="onlineApply" name="admissionFormForm">
									<html:button
										property="" styleClass="formbutton"
										onclick="submitAdmissionForm('initOfflineApplicationForm')">
										<bean:message key="knowledgepro.cancel" />
									</html:button>
									</logic:equal>
									<logic:equal value="true" property="onlineApply" name="admissionFormForm">
									<html:button
										property="" styleClass="formbutton"
										onclick="submitAdmissionForm('initOnlineApply')">
										<bean:message key="knowledgepro.cancel" />
									</html:button>
									</logic:equal>
									</td>
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
<script type="text/javascript"/>
	
	var sameAddr= document.getElementById("sameAddr").checked;

			if(sameAddr==true){
				disableAddress();
			}
			if(sameAddr==false){
				enableAddress();
			}
	
	var destID;
	var birthState="birthState";

	var country= document.getElementById("birthCountry").value;
	getStatesByCountry("birthStateMap",country,birthState,updatebirthState);	
	
	function updatebirthState(req) {
	updateOptionsFromMapWithOther(req,birthState,"-Select-"); 
	setData1()
}
	function setData1(){
				stateId=document.getElementById("tempState").value;
				document.getElementById('birthState').value=stateId;
			}
			
	var currentStateName="currentStateName";
	var currentCountryName=document.getElementById("currentCountryName").value;
	getStatesByCountry("tempAddrStateMap",currentCountryName,currentStateName,updatecurrentState);	

function updatecurrentState(req) {
	updateOptionsFromMapWithOther(req,currentStateName,"-Select-"); 
	setData2()
}
function setData2()
	{
		stateId=document.getElementById("tempState1").value;
		document.getElementById('currentStateName').value=stateId;
	}

	var permanentStateName="permanentStateName";
	var permanentCountryName =document.getElementById("permanentCountryName").value;
	getStatesByCountry("permAddrStateMap",permanentCountryName,permanentStateName,updatepermanentState);	

	function updatepermanentState(req) {
	updateOptionsFromMapWithOther(req,permanentStateName,"-Select-"); 
	setData3()
}
	function setData3()
	{
		stateId=document.getElementById("tempState2").value;
		document.getElementById('permanentStateName').value=stateId;
	}
	
	var parentStateName="parentStateName";
	var parentCountryName= document.getElementById("parentCountryName").value;
	getStatesByCountry("parentAddrStateMap",parentCountryName,parentStateName,updateparentStateName);	

function updateparentStateName(req) {
	updateOptionsFromMapWithOther(req,parentStateName,"-Select-"); 
	setData4();
}
function setData4()
	{
		stateId=document.getElementById("tempState3").value;
		document.getElementById('parentStateName').value=stateId;
	}

var guardianStateName="guardianStateName";
	var guardianCountryName= document.getElementById("guardianCountryName").value;
	getStatesByCountry("parentAddrStateMap",guardianCountryName,guardianStateName,updateguardianStateName);	

function updateguardianStateName(req) {
	updateOptionsFromMapWithOther(req,guardianStateName,"-Select-"); 
	setData5();
}
function setData5()
	{
		stateId=document.getElementById("tempState4").value;
		document.getElementById('guardianStateName').value=stateId;
	}
	
	
	
	
	
	
	var dynarow2id;
function getColleges(propertyName,university,destId) {
	count=destId;
	globalID=destId+"Institute";
	if(university.value=="Other"){
		var colleges = document.getElementById(globalID);
		 for (x1=colleges.options.length-1; x1>0; x1--)
		 {
			 colleges.options[x1]=null;
		 }
		 colleges.options[0]=new Option("-Select-","");
		 colleges.options[1] = new Option("Other","Other");
		 
		return;
	}
	if(university.value != '0') {
		var args = "method=getCollegeByUniversity&universityId="+university.value+"&propertyName="+propertyName;
	  	var url ="AjaxRequest.do";
	  	// make an request to server passing URL need to be invoked and arguments.
		requestOperation(url,args,updateColleges);
	} else {
		 var colleges = document.getElementById(globalID);
		 for (x1=colleges.options.length-1; x1>0; x1--)
		 {
			 colleges.options[x1]=null;
		 }
	}	
}

function updateColleges(req) {
	updateOptionsFromMapValues(req,globalID,count,"-Select-"); 
}
function updateOptionsFromMapValues(req,destinationOption,count,defaultSelectValue) {
	var responseObj = req.responseXML.documentElement;

	var destination = document.getElementById(destinationOption);
	for (x1=destination.options.length-1; x1>0; x1--) {
		destination.options[x1]=null;
	}
	var childNodes = responseObj.childNodes;
	destination.options[0]=new Option(defaultSelectValue,"");
	 var items = responseObj.getElementsByTagName("option");
	
	 var label,value;
	 for (var i = 0 ; i < items.length ; i++) {
        label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination.options[i+1] = new Option(label,value);		
	 }
	 destination.options[items.length+1] = new Option("Other","Other");
		  
}
	
	
	
	
	
	
	
	
	
	


	if(document.getElementById("motherOccupation").value=="" || document.getElementById("motherOccupation").value== null){
		document.getElementById("displayMotherOccupation").style.display = "none";
	}else{
		document.getElementById("displayMotherOccupation").style.display = "block";
	}
	if(document.getElementById("fatherOccupation").value=="" || document.getElementById("fatherOccupation").value== null){
		document.getElementById("displayFatherOccupation").style.display = "none";
	}else{
		document.getElementById("displayFatherOccupation").style.display = "block";
	}
	function displayOtherForMother(occpation){
		if(document.getElementById("motherOccupation").value=="other"){
			document.getElementById("displayMotherOccupation").style.display = "block";
		}else{
			document.getElementById("displayMotherOccupation").style.display = "none";
			document.getElementById("otherOccupationMother").value = "";
		}
	}
	function displayOtherForFather(occpation){
		if(document.getElementById("fatherOccupation").value=="other"){
			document.getElementById("displayFatherOccupation").style.display = "block";
		}else{
			document.getElementById("displayFatherOccupation").style.display = "none";
			document.getElementById("otherOccupationFather").value = "";
		}
	}
	
	var selectionVenue=document.getElementById("interviewVenue").value;
	if(selectionVenue!=null && selectionVenue!=""){
			var programId=document.getElementById("programId").value;
			var programYear=document.getElementById("programYear").value;
    		getDateByVenueSelectionOffline(selectionVenue,updateVenueMap, programId, programYear);

	}

			
		function updateVenueMap(req){
			updateOptionsFromMap(req,"interviewSelectionDate","-Select-");
			setVenue();
		}
		
		function setVenue(){
					dateId=document.getElementById("tempDate").value;
					document.getElementById('interviewSelectionDate').value=dateId;
				}
		document.getElementById("ncccertificate_description").style.display = "none";
	
	
	
</script>