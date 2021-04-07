<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.kp.cms.constants.KPPropertiesConfiguration"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<link rel="stylesheet" href="css/admission/BootstrapApp.css">
<link rel="stylesheet" href="js/loginPage Design/font-awesome/css/font-awesome.min.css">
<script type="text/javascript" src="jquery/collaps/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
 <!--<link rel="stylesheet" href="css/admission/formElements.css">
-->

<script language="JavaScript" src="js/admission/OnlineDetailsAppCreation.js"></script>
 <script type="text/javascript">
		setStartDate();
		setInterval ( "checkSession()", 300000 );
</script>
 <style type="text/css">
 input[type="radio"]:focus, input[type="radio"]:active {
    -webkit-box-shadow:inset 2px 1px 1px , 1px 1px 3px #008000;
    -moz-box-shadow:inset 2px 1px 1px #008000, 1px 1px 3px #008000;
    box-shadow:inset 2px 1px 1px #008000, 1px 1px 3px #008000;
}
 </style>
 
 <script type="text/javascript">
	javascript:window.history.forward(1);
</script>
 
 
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
<html:hidden property="programId" styleId="programId" name="onlineApplicationForm"/>
<html:hidden property="programYear" styleId="programYear" name="onlineApplicationForm"/>
<html:hidden property="courseId" styleId="courseId" name="onlineApplicationForm"/>
<html:hidden property="focusValue" styleId="focusValue" name="onlineApplicationForm"/>
<html:hidden property="onlineApply" styleId="onlineApply" name="onlineApplicationForm"/>
<html:hidden property="pageType" value="18" />
<html:hidden property="marksNoEntry" styleId="marksNoEntry" name="onlineApplicationForm"/>
<html:hidden property="indianCandidate" styleId="indianCandidate" name="onlineApplicationForm"/>

 			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			 <tr>
		       <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
		        <td width="1271" background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.applicationform.online.label"/></strong></td>
		        <td width="15"><img src="images/Tright_1_01.gif" width="9" height="29"/></td>
		      </tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					
					
					
					<div align="center">
					<table width="100%" border="0" cellpadding="1" cellspacing="2" class="table table-striped table-bordered table-condensed">
						
						
						<tr >
							<td  align="left">
							<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
							<div id="errorMessage">
							<FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages></FONT>
							</div>
							<div id="errorMessage1" style="font-size: 11px; color: red"></div>
							</td>
						</tr>
						
						
						
						
						
						<tr class="row-even">
							<td class="heading">
							
							<table width="97%" border="0" align="center" cellpadding="1" cellspacing="1">
								
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								
								
								
								
								
								
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%"  valign="top">
									
									
									<!-- raghu starting things started -->
									
									<table width="100%"  border="0" cellpadding="0"
										cellspacing="0">
										<tr class="row-even">
											<td height="20" class="row-even" width="50%"><div align="left"><bean:message key="knowledgepro.admission.programtype"/>:</div>
											</td>
											<td height="20" class="row-even" align="left" width="50%">&nbsp;
												<bean:write property="pgmTypeName" name="onlineApplicationForm"/>
											</td>
										</tr>
										<tr>
											<td class="row-even" width="50%">
											<div align="left"><bean:message key="knowledgepro.admission.program"/>:</div></td>
											<td class="row-even" align="left" width="50%">&nbsp;
												<bean:write property="pgmName" name="onlineApplicationForm"/>
											</td>
										</tr>
										<tr>
											<td height="20" class="row-even" width="50%">
											<div align="left"><bean:message key="admissionForm.edit.firstpref.label"/></div></td>
											<td height="20" class="row-even" align="left" width="50%">&nbsp;
											<bean:write property="courseName1" name="onlineApplicationForm" />
 					
											</td>
										</tr>
										
										
										
										
										
										
<logic:equal value="false" property="prerequisitesValidated" name="onlineApplicationForm">				
<logic:equal value="true" property="preRequisiteExists" name="onlineApplicationForm">		
      
	<logic:notEmpty property="coursePrerequisites" name="onlineApplicationForm"> 	
			
            
            					<tr> 
									<td valign="top" colspan="2">
									<table width="100%" border="0" align="center" cellpadding="4" cellspacing="0">
										<tr class="row-odd">
										  	<td  align="center" >
												<bean:message key="admissionForm.approveview.prereq.label"/>
											</td>
										</tr>
									</table>
									</td>
									</tr>
            
            <tr class="row-even">
            <td valign="top" class="heading" colspan="2" >
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">				
				 <tr><!--
				        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
				        --><td  valign="top" class="news">
						 <table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
						    <tr>
				                <td  height="25"  align="left" style="font-size: 14px;font-weight: bolder;color: #000;">
				                 Have you pursued Undergraduate studies at Christ University/College, Bangalore&nbsp;&nbsp;&nbsp;&nbsp;
				                 <html:radio property="christStudent" styleId="christYes" value="Yes" name="onlineApplicationForm">Yes</html:radio>
				                 <html:radio property="christStudent" styleId="christNo" value="No" name="onlineApplicationForm">No</html:radio>
				                 </td>
							</tr>
						 </table>
						</td>
					</tr>
				
				<bean:define id="requisites" property="coursePrerequisites" name="onlineApplicationForm" type="java.util.ArrayList"></bean:define>
				<nested:iterate property="coursePrerequisites" name="onlineApplicationForm" indexId="count" id="prereq">
					
				      <tr>
				        <td  valign="top" class="news">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				          <tr>
				            <td valign="top"><table width="100%" height="53" border="0" cellpadding="0" cellspacing="1" align="center">
				                <tr>
				                  <td class="row-even" width="50%" align="left"><span class='Mandatory'>*</span><nested:write property="prerequisiteTO.name"/>:</td>
				                  <td class="row-even" width="50%" align="left">
				                    <nested:text property="userMark" styleClass="textboxMediam"  maxlength="6" onblur="checkForEmpty(this);isValidNumber(this)" onkeypress="return isDecimalNumberKey(this.value,event)" onfocus="clearField(this)"></nested:text>
				                  </td>
								</tr>
				                <tr>
									<td class="row-even" width="50%" align="left"><span class='Mandatory'>*</span><bean:message key="knowledgepro.applicationform.prereq.roll.label"/></td>
									<td class="row-even" width="50%" align="left"><nested:text property="rollNo" styleClass="textboxMediam" maxlength="20"></nested:text>
									</td>
				                </tr>
				                <tr> 
				                  <td class="row-even" width="50%" align="left"><span class='Mandatory'>*</span><bean:message key="knowledgepro.applicationform.prereq.passmonth.label"/></td>
				                  <td class="row-even" width="50%" align="left">
				                  <%String monthId="examMonth_"+count; %>
				                  <%String method = "searchYearMonthWise("+count+")"; %>
				                  <nested:select property="examMonth" styleId='<%=monthId %>' styleClass="comboMedium" onchange="<%=method %>" >
									  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										<logic:notEmpty property="monthMap" name="onlineApplicationForm">
										<nested:optionsCollection name="onlineApplicationForm" property="monthMap" label="value" value="key"/>
									 </logic:notEmpty>
								</nested:select>
								</td>
							</tr>
				            <tr> 
							<td class="row-even" width="50%" align="left"><span class='Mandatory'>*</span><bean:message key="knowledgepro.applicationform.prereq.passyear.label"/></td>
				            <td class="row-even" width="50%" align="left">
							<%String tempyearId="tempyear_"+count; %>
							<%String examYearId="examYear_"+count; %>
					        <input type="hidden" id='<%=tempyearId %>' name='<%=tempyearId %>' value="<nested:write property="examYear" />" />
								<nested:select property="examYear" styleId='<%=examYearId %>' styleClass="comboMedium">
									  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										<logic:notEmpty property="yearMap" name="onlineApplicationForm">
										<nested:optionsCollection name="onlineApplicationForm" property="yearMap" label="value" value="value"/>
									 </logic:notEmpty>
								</nested:select>
								<script type="text/javascript">
									var d = new Date();
									var currentYear = d.getFullYear();
									document.getElementById("year_<c:out value='${count}'/>").value = currentYear;
									var year = document.getElementById("tempyear_<c:out value='${count}'/>").value;
									if (year.length != 0) {
										document.getElementById("year_<c:out value='${count}'/>").value = year;
									}
								</script>
							</td>
							</tr>
				            
				            </table></td>
				          
				          </tr>
				        </table></td>
				      </tr>
				<%
				List prerequisites=(ArrayList)requisites;
					if(prerequisites.size()>1 && count!=(prerequisites.size()-1)){
				%>
			      <tr>
			        <td height="25" class="news" ><div align="center"><b>OR</b></div></td>
			      </tr>
				<%
					}
				%>
				</nested:iterate>
				<logic:equal value="false" property="prerequisitesValidated" name="onlineApplicationForm">
				 <tr>
            		<td align="center">
            		
            			 <html:button property=""  styleClass="buttons" value="Check Eligiblity To Proceed" styleId="checkEligiblityToProceed" ></html:button>
               			<!--  
               			 <html:button property="" onclick="submitAdmissionForm('submitPreRequisiteApply')" styleClass="buttons" value="Check Eligiblity To Proceed"></html:button>
               			 -->
               			 <html:button	property="" styleClass="buttons" onclick="cancel()" value="Close"></html:button>
            		</td>
            	</tr>
            	</logic:equal>
            </table>
		</td>
	</tr>
</logic:notEmpty>	
</logic:equal>	
</logic:equal>





<logic:equal value="true" property="prerequisitesValidated" name="onlineApplicationForm">	
	<logic:equal value="true" property="preRequisiteExists" name="onlineApplicationForm">
	
									<tr> 
									<td valign="top" colspan="2">
									<table width="100%" border="0" align="center" cellpadding="4" cellspacing="0">
										<tr class="row-odd">
										  	<td  align="center" >
												<bean:message key="admissionForm.approveview.prereq.label"/>
											</td>
										</tr>
									</table>
									</td>
									</tr>
					
	                <tr class="row-even">
	                  <td colspan="2" class="heading"><table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
	                    <tr>
	                      <td width="100%" valign="top">
							
								<table width="100%" border="0" cellpadding="0" cellspacing="1">
		                      <tr>
		                        <td width="50%" height="23" class="row-even" align="left"><span class="Mandatory">*</span>
									<bean:message	key="knowledgepro.admin.pre.requisite.mark" /></td>
								<td width="50%" height="23" class="row-even" align="left"><bean:write property="applicantDetails.preRequisiteObtMarks" name="onlineApplicationForm"/></td>
							</tr>
		                     <tr>	
								<td width="50%" class="row-even" align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.prereq.roll.label" /> </td>
								<td width="50%" class="row-even" align="left">
								<bean:write property="applicantDetails.preRequisiteRollNo" name="onlineApplicationForm"/>
								</td>
		                     </tr>
		                     <tr>
		                        <td width="50%" height="23" class="row-even" align="left"><span class="Mandatory">*</span>
									<bean:message	key="knowledgepro.applicationform.prereq.passmonth.label" /></td>
								<td width="50%" height="23" class="row-even" align="left">
								<bean:write property="applicantDetails.preRequisiteExamMonthDisplay" name="onlineApplicationForm" />
								<!--<html:select property="applicantDetails.preRequisiteExamMonth" styleId='examMonth' disabled="true" styleClass="comboMedium" onchange="searchYearMonthWise();">
						 			 <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										<logic:notEmpty property="monthMap" name="onlineApplicationForm">
											<html:optionsCollection name="onlineApplicationForm" property="monthMap" label="value" value="key" />
										 </logic:notEmpty>
								</html:select>
								-->
								</td>
							</tr>
		                     <tr>
								<td width="50%" class="row-even" align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.prereq.passyear.label" /> </td>
								<td width="50%" class="row-even" align="left">
								<bean:write property="applicantDetails.preRequisiteExamYear" name="onlineApplicationForm" />
									<!--<html:select property="applicantDetails.preRequisiteExamYear" disabled="true" styleId='examYear' styleClass="comboMedium" >
										  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
											<logic:notEmpty property="yearMap" name="onlineApplicationForm">
											<html:optionsCollection name="onlineApplicationForm" property="yearMap" label="value" value="value"/>
										 </logic:notEmpty>
									</html:select>				
								--></td>
		                     </tr>
	 					</table>
						</td>
	                    </tr>
	                    
	                  </table></td>
	                </tr>
	</logic:equal>
</logic:equal>
										
										
										
										
									</table>
									
									
									
								</td>
								
								
									<td background="images/right.gif" width="5"></td>
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
		   
				
				
	<!--raghu  starting things closed -->			
				
				
	
	
	<!--raghu  main things started here -->

<logic:equal value="true" property="prerequisitesValidated" name="onlineApplicationForm">
					
					<tr class="row-even">
							<td class="heading">
							
							
							<table width="97%" border="0" align="center" cellpadding="1"
								cellspacing="1">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								
								
						<!-- raghu main information code close here -->	
								
								
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%"  valign="top">
									
									
									<table width="100%"  border="0" cellpadding="0" cellspacing="0">
									
									<!-- raghu main information code close here -->	
						
									
									<!-- raghu student information code start here -->	
					
									<tr> 
									<td valign="top" colspan="2">
									<table width="100%" border="0" align="center" cellpadding="4" cellspacing="0">
										<tr class="row-odd">
										  	<td  align="center" >
												<bean:message key="admissionForm.studentinfo.main.label"/>
											</td>
										</tr>
									</table>
									</td>
									</tr>
										
									<tr height="30">
			                          	<td class="row-even" width="50%"><div align="left"><bean:message key="knowledgepro.applicationform.candidateName"/><span class="Mandatory">*</span></div></td>
			                          	<td class="row-even" width="50%"><div align="left">
											<nested:text property="applicantDetails.personalData.firstName" styleId="firstNameId" name="onlineApplicationForm"  maxlength="90" styleClass="textbox"></nested:text></div>
										</td>
                        			</tr>
                        			
									<tr >
                         			 <td height="25" class="row-even" width="50%"><div align="left"><bean:message key="admissionForm.studentinfo.dob.label"/><bean:message key="admissionForm.application.dateformat.label"/><span class="Mandatory">*</span>
		                         		 <br/><bean:message key="knowledgepro.applicationform.dob.format"/></div></td>
		                         		 <td  class="row-even" align="left" width="50%"><nested:text name="onlineApplicationForm" property="applicantDetails.personalData.dob" styleId="dateOfBirth"  maxlength="11" styleClass="textbox"></nested:text>
							                              <script language="JavaScript">
														new tcal( {
															// form name
															'formname' :'onlineApplicationForm',
															// input name
															'controlname' :'dateOfBirth'
														});
													</script></td>
                        			</tr>
										
						<tr >
                          <td  class="row-even" width="50%"><div align="left"><bean:message key="admissionForm.studentinfo.nationality.label"/><span class="Mandatory">*</span></div></td>
                          <td  class="row-even" align="left" width="50%">
                          <input type="hidden" id="nationalityhidden" name="nationality" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.nationality"/>"/>
                          
							<nested:select property="applicantDetails.personalData.nationality" styleClass="comboExtraLarge" styleId="nationality" name="onlineApplicationForm">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
								<logic:iterate id="option" property="nationalities" name="onlineApplicationForm">
									<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_NATIONALITY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
									<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_NATIONALITY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
									<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
								</logic:iterate>
						</nested:select>					
						</td>
                        </tr>
                        
                        <tr>
						
                           <td height="17" class="row-even" width="50%"><div align="left"><bean:message key="admissionForm.studentinfo.sex.label"/><span class="Mandatory">*</span></div></td>
                           <td height="17" class="row-even" align="left" width="50%">
                           <table border="0">
                           <tr>
                           <td id="errorGenderDetails" >
	                           <nested:radio property="applicantDetails.personalData.gender" styleId="MALE" name="onlineApplicationForm" value="MALE"></nested:radio><bean:message key="admissionForm.studentinfo.sex.male.text"/>
							   <nested:radio property="applicantDetails.personalData.gender" name="onlineApplicationForm" styleId="FEMALE" value="FEMALE"><bean:message key="admissionForm.studentinfo.sex.female.text"/></nested:radio>
							</td>
							</tr>
							</table>
							</td>
							
                        </tr>
                        
						
						 <tr>
						   <%String dynaStyle3="display:none;"; %>
									<logic:equal value="true" property="sportsPerson" name="onlineApplicationForm">
										<%dynaStyle3="display:block;"; %>
									</logic:equal>
					        <td height="17" class="row-even" width="40%"><div align="left"><bean:message key="knowledgepro.applicationform.sports.label"/> </div></td>
                           <td height="17" class="row-even" align="left">
                           <input type="hidden" id="hiddenSportsPerson" name="hiddenSportsPerson" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.sportsPerson"/>'/>
                           <nested:radio property="applicantDetails.personalData.sportsPerson" styleId="sportsPersonYes" name="onlineApplicationForm" value="true" onclick="showSportsDescription()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
											<nested:radio property="applicantDetails.personalData.sportsPerson" styleId="sportsPersonNo" name="onlineApplicationForm" value="false" onclick="hideSportsDescription()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
											<div id="sports_description" style="<%=dynaStyle3 %>">
												<nested:text styleId="sportsDescription" property="applicantDetails.personalData.sportsDescription" name="onlineApplicationForm"  maxlength="80" styleClass="textbox" ></nested:text>
											</div>				
								</td>

                        </tr>
                        
                        
 						<tr>
 						   <%String dynaStyle4="display:none;"; %>
									<logic:equal value="true" property="handicapped" name="onlineApplicationForm">
										<%dynaStyle4="display:block;"; %>
									</logic:equal>
                           <td height="17" class="row-even" width="40%"><div align="left"><bean:message key="knowledgepro.applicationform.physical.label"/></div></td>
                           <td height="17" class="row-even" align="left">
                    <input type="hidden" id="hiddenHandicaped" name="hiddenHandicaped" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.handicapped"/>'/>
                           <nested:radio property="applicantDetails.personalData.handicapped" styleId="handicappedYes" name="onlineApplicationForm" value="true" onclick="showHandicappedDescription()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
                           
											<nested:radio property="applicantDetails.personalData.handicapped" styleId="handicappedNo" name="onlineApplicationForm" value="false" onclick="hideHandicappedDescription()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
											<div id="handicapped_description" style="<%=dynaStyle4 %>">
												<nested:text styleId="hadnicappedDescription" property="applicantDetails.personalData.hadnicappedDescription" name="onlineApplicationForm" maxlength="80" styleClass="textbox"></nested:text>
											</div>
								</td>
                        </tr>
						
                        <tr >
                          <td height="20" class="row-even" width="40%"><div align="left"><bean:message key="admissionForm.studentinfo.bloodgroup.label"/><span class="Mandatory">*</span></div></td>
	                      <td height="20" class="row-even" align="left"><input type="hidden" id="BGType" name="BGType" value='<bean:write name="onlineApplicationForm" property="bloodGroup"/>'/>
                         <nested:select property="applicantDetails.personalData.bloodGroup" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="bgType">
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
						
						
						<logic:equal value="true" property="displaySecondLanguage" name="onlineApplicationForm">
						 <tr >
                          <td height="20" class="row-even" width="40%"><div align="left"><bean:message key="knowledgepro.applicationform.secLang.label"/><span class="Mandatory">*</span></div></td>
	                      <td height="20" class="row-even" align="left">
                          <html:select property="applicantDetails.personalData.secondLanguage" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="secondLanguage">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection property="secondLanguageList"
									label="value" value="value" name="onlineApplicationForm"/>
							</html:select>
						 </td>
                        </tr>
						</logic:equal>
						
						<tr>
							<td  class="row-even" align="right" colspan="2" >
					      		<html:button property="" styleClass="buttons" value="Save Draft"  styleId="saveDraft1" onclick="submitSaveDraft('birthPlace'); return false;"></html:button>
							</td>
						</tr>
					
					
					<!-- raghu student information code close here -->	
						
					<!-- raghu student birth details code stat here -->	
					
					<tr> 
						<td valign="top" colspan="2">
						<table width="100%" border="0" align="center" cellpadding="4" cellspacing="0">
							<tr class="row-odd">
							  	<td  align="center" >
									<bean:message key="admissionForm.studentinfo.birthdetails.main.label"/>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					
						<tr height="30">
                          	<td  class="row-even" width="50%"><div align="left"><bean:message key="admissionForm.studentinfo.birthdetails.place.label"/><span class="Mandatory">*</span></div></td>
                          	<td  class="row-even" align="left" width="50%"><nested:text property="applicantDetails.personalData.birthPlace"  styleId="birthPlace" name="onlineApplicationForm"  maxlength="50" styleClass="textbox"></nested:text></td>
                       	</tr>
						
						<tr >
                          <td height="25" class="row-even" width="50%"><div align="left"><bean:message key="admissionForm.studentinfo.birthdetails.country.label"/><span class="Mandatory">*</span></div></td>
                          <td height="35" class="row-even" align="left" width="50%">
                          <input type="hidden" id="birthCountryhidden" name="birthCountry" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.birthCountry"/>"/>
								<nested:select property="applicantDetails.personalData.birthCountry" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="birthCountry" onchange="getStates(this.value,'birthState');">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="onlineApplicationForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
												</nested:select>
						</td>
                        </tr>
						
						<tr >
							<%String dynaStyle=""; %>
									<logic:equal value="Other" property="birthState" name="onlineApplicationForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="birthState" name="onlineApplicationForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                          <td height="25" class="row-even" width="50%"><div align="left"><bean:message key="admissionForm.studentinfo.birthdetails.state.label"/><span class="Mandatory">*</span></div></td>
                          <td height="35" class="row-even" align="left" width="50%"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                          	<tr><td class="row-even">
                          	<input type="hidden" id="birthState1" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.birthState"/>">
                          	<nested:select property="applicantDetails.personalData.birthState" name="onlineApplicationForm"
														styleId="birthState" styleClass="comboExtraLarge"
														onchange="funcOtherShowHide('birthState','otherBirthState')">
														<html:option value="">- Select -</html:option>
														
												<logic:notEmpty property="stateMap" name="onlineApplicationForm">
												<html:optionsCollection name="onlineApplicationForm" property="stateMap"
																	label="value" value="key" />
												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
													</nested:select></td></tr>
							<tr><td class="row-even"><nested:text property="applicantDetails.personalData.stateOthers"  name="onlineApplicationForm"
														maxlength="30" styleId="otherBirthState"
														style="<%=dynaStyle %>" styleClass="textbox"></nested:text></td></tr>
														
						<tr>
							<td  class="row-even" align="right" colspan="2">
					      		<html:button property="" styleClass="buttons" value="Save Draft"  styleId="saveDraft2" onclick="submitSaveDraft('residentCategory');"></html:button>
							</td>
						</tr>
							
				</table>
					</td>
                   </tr>
                 
                 
                 <!-- raghu student birth details code close here -->	
                 
					
					<!-- raghu student resedentials details code stat here -->	
						
					
					
					<tr> 
						<td valign="top"  class="row-odd" colspan="2">
						<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
							<tr>
							  	<td class="row-odd" align="center" >
									<bean:message key="admissionForm.studentinfo.residentinfo.title"/>
								</td>
							</tr>
						</table>
						</td>
					</tr>
						
						
						<tr height="30">
                              <td width="50%" height="20" class="row-even">
								<div align="left"><bean:message key="admissionForm.studentinfo.residentcatg.label"/><BR><bean:message key="admissionForm.studentinfo.residentcatg.label2"/><span class="Mandatory">*</span>
								</div></td>
                              <td width="50%" height="20" class="row-even" align="left">
                              <input type="hidden" id="tempResidentCategory" value="<nested:write property="applicantDetails.personalData.residentCategory" name="onlineApplicationForm" />">
							<nested:select property="applicantDetails.personalData.residentCategory" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="residentCategory" disabled="true">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection name="onlineApplicationForm" property="residentTypes" label="name" value="id"/>
												</nested:select>
							</td>
                         </tr>
                            
                            <tr>
									<logic:equal value="Other" property="religionId" name="onlineApplicationForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="religionId" name="onlineApplicationForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                              <td height="20" class="row-even"><div align="left" ><bean:message key="admissionForm.studentinfo.religion.label"/><span class="Mandatory">*</span></div></td>
                              <td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr><td class="row-even">
													
												<input type="hidden" id="religionType" name="religionType" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.religionId"/>'/>
												<logic:notEqual value="Other" property="applicantDetails.personalData.religionId" name="onlineApplicationForm">
													<%dynaStyle="display:none;"; %>
												<nested:select property="applicantDetails.personalData.religionId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="religions" onchange="getSubReligion(this.value,'subreligion');funcOtherShowHide('religions','otherReligion')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection name="onlineApplicationForm" property="religions" label="religionName" value="religionId"/>
													<html:option value="Other">Other</html:option>
												</nested:select>
												</logic:notEqual>
												<logic:equal value="Other" property="applicantDetails.personalData.religionId" name="onlineApplicationForm">
													<%dynaStyle="display:block;"; %>												
												<nested:select property="applicantDetails.personalData.religionId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="religions" onchange="getSubReligion(this.value,'subreligion');funcOtherShowHide('religions','otherReligion')">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection name="onlineApplicationForm" property="religions" label="religionName" value="religionId"/>
													<html:option value="Other">Other</html:option>
												</nested:select>
												
												</logic:equal></td></tr>
												<tr><td class="row-even"><nested:text property="applicantDetails.personalData.religionOthers" name="onlineApplicationForm" styleClass="textbox"  maxlength="30" styleId="otherReligion" style="<%=dynaStyle %>"></nested:text></td></tr>
												</table>
							</td>
                            </tr>
							
							<logic:equal value="true" property="casteDisplay" name="onlineApplicationForm">
	                            <tr >
	                              <td height="20" class="row-even"><div align="left"><bean:message key="admissionForm.studentinfo.subreligion.label"/></div></td>
	                              <td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
	                              <tr><td class="row-even"><nested:select property="applicantDetails.personalData.subReligionId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="subreligion" onchange="funcOtherShowHide('subreligion','otherSubReligion')">
										<html:option value="">- Select -</html:option>
										<c:if test="${onlineApplicationForm.applicantDetails.personalData.religionId != null && onlineApplicationForm.applicantDetails.personalData.religionId != ''}">
				                           					<c:set var="subReligionMap" value="${baseActionForm.collectionMap['subReligionMap']}"/>
			                            		    	 	<c:if test="${subReligionMap != null}">
			                            		    	 		<html:optionsCollection name="subReligionMap" label="value" value="key" />
																<html:option value="Other">Other</html:option>
			                            		    	 	</c:if> 
				                        </c:if>
								</nested:select></td></tr>
								<tr><td class="row-even"><nested:text property="applicantDetails.personalData.religionSectionOthers" styleClass="textbox" name="onlineApplicationForm"  maxlength="30" styleId="otherSubReligion" style="<%=dynaStyle %>"></nested:text></td></tr>
								</table>
								</td>
	                            </tr>
							</logic:equal>
                            
                            <logic:equal value="false" property="isPresidance" name="onlineApplicationForm">
                            <tr>
                              <td height="20" class="row-even"><div align="left"><bean:message key="admissionForm.studentinfo.castcatg.label"/><span class="Mandatory">*</span></div></td>
                              <td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              		<tr><td class="row-even">
									<input type="hidden" id="casteType" name="casteType" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.casteId"/>'/>
									<logic:equal value="Other" property="applicantDetails.personalData.casteId" name="onlineApplicationForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="applicantDetails.personalData.casteId" name="onlineApplicationForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                              		<nested:select property="applicantDetails.personalData.casteId" name="onlineApplicationForm" styleId="castCatg" styleClass="comboExtraLarge" onchange="funcOtherShowHide('castCatg','otherCastCatg')">
									<option value="">-Select-</option>
										<html:optionsCollection property="casteList" name="onlineApplicationForm" label="casteName" value="casteId"/>
										
									<html:option value="Other">Other</html:option>
								</nested:select></td></tr>
								<tr><td class="row-even"><nested:text property="applicantDetails.personalData.casteOthers" name="onlineApplicationForm"  styleClass="textbox" maxlength="30" styleId="otherCastCatg" style="<%=dynaStyle %>"></nested:text></td></tr>
								</table>
							</td>
                            </tr>
                            <tr>
                              <td height="17" class="row-even"><div align="left"><bean:message key="admissionForm.studentinfo.belongsto.label"/><span class="Mandatory">*</span></div></td>
                              <td height="17" class="row-even" align="left">
                               <table border="0">
		                           <tr>
		                           <td id="errorareaTypeDetails" >
			                              <nested:radio name="onlineApplicationForm" property="applicantDetails.personalData.areaType" styleId="areaTypeR" value='R'><bean:message key="admissionForm.studentinfo.belongsto.rural.text"/></nested:radio>
										  <nested:radio property="applicantDetails.personalData.areaType" name="onlineApplicationForm" styleId="areaTypeU" value='U'><bean:message key="admissionForm.studentinfo.belongsto.urban.text"/></nested:radio>
			                         </td>
									</tr>
								</table>
                              </tr>
                            </logic:equal>
                            
                            <tr>
                              <td>
                            	 <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            	  <tr>
                             		 <td height="20" class="row-even"><div align="left"><bean:message key="admissionForm.studentinfo.phone.label"/><span class="Mandatory">*</span></div></td>
                             		  <td height="20" class="row-even" align="left">
                             		 	<table width="100%" border="0" cellpadding="0" cellspacing="0" align="right">
													<tr><td align="right" height="25" class="row-even"><bean:message key="admissionForm.phone.cntcode.label"/></td></tr>
													<tr><td align="right" height="25" class="row-even"><bean:message key="admissionForm.phone.areacode.label"/></td></tr>
													<tr><td align="right" height="25" class="row-even"><bean:message key="admissionForm.phone.main.label"/></td></tr>
										</table>
									</td>
								  </tr>
								 </table>
								</td>
                              <td height="20" class="row-even" align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr><td class="row-even"><nested:text styleClass="textbox" property="applicantDetails.personalData.phNo1" name="onlineApplicationForm"  maxlength="4" onkeypress="return isNumberKey(event)"></nested:text></td></tr>
													<tr><td class="row-even"><nested:text styleClass="textbox" property="applicantDetails.personalData.phNo2" name="onlineApplicationForm"  maxlength="7" onkeypress="return isNumberKey(event)"></nested:text></td></tr>
													<tr><td class="row-even"><nested:text styleClass="textbox" property="applicantDetails.personalData.phNo3" name="onlineApplicationForm" styleId="applicantphNo3" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text></td></tr>
								</table>
                              </td>
                            </tr>
                           
                           
                            <tr>
                            	 <td>
                            	 <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            	  <tr>
                             		 <td height="20" class="row-even"><div align="left"><bean:message key="admissionForm.studentinfo.mobile.label"/><span class="Mandatory">*</span></div></td>
                             		  <td height="20" class="row-even" align="left">
                             		 	<table width="100%" border="0" cellpadding="0" cellspacing="0" align="right">
                             		 			<tr><td align="right" height="25" class="row-even"><bean:message key="admissionForm.phone.cntcode.label"/></td></tr>
			                                    <tr><td align="right" height="25" class="row-even"><bean:message key="admissionForm.mob.no.label"/> </td></tr>
                             		 	</table>
									</td>
								  </tr>
								 </table>
								</td>
                              <td height="20" class="row-even" align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
			                              	  <tr><td class="row-even"><nested:text styleClass="textbox" property="applicantDetails.personalData.mobileNo1" name="onlineApplicationForm"  maxlength="4" onkeypress="return isNumberKey(event)" ></nested:text></td></tr>
			                                  <tr><td class="row-even"><nested:text styleClass="textbox" property="applicantDetails.personalData.mobileNo2" name="onlineApplicationForm" styleId="applicantMobileNo" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text></td></tr>
											</table>
                               </td>
                            </tr>
                           
                           
                            <tr>
                              <td height="20" class="row-even" ><div align="left"><bean:message key="admissionForm.studentinfo.email.label"/><span class="Mandatory">*</span></div></td>
                              <td height="20" class="row-even" align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
												 <tr><td class="row-even"><nested:text property="applicantDetails.personalData.email" styleId="applicantEmail" name="onlineApplicationForm" styleClass="textbox"  maxlength="50"></nested:text> </td></tr>
												<tr><td>(e.g. name@yahoo.com)</td></tr>
                                  				</table>
                              </td>
                            </tr>
							
							
							<tr>
                              <td height="20" class="row-even" ><div align="left"><bean:message key="admissionForm.studentinfo.confirmemail.label"/><span class="Mandatory">*</span></div></td>
                              <td height="20" class="row-even" align="left"><p>
			                      <nested:text property="applicantDetails.personalData.confirmEmail" styleClass="textbox" name="onlineApplicationForm" styleId="confirmEmailId" maxlength="50" onmousedown="noCopyMouse(event)" onkeydown= "noCopyKey(event)" onkeyup="noCopyKey(event)" ></nested:text>
			                                  <br/>
			                              </p></td>
                            </tr>
                            
                         <tr>
							<td  class="row-even" align="right" colspan="2">
					      		<html:button property="" styleClass="buttons" value="Save Draft"  styleId="saveDraft3" onclick="submitSaveDraft('confirmEmailId'); return false;"></html:button>
							</td>
						</tr>	
										
										
			<!-- raghu student resedentials details code close here -->	
				
			<!-- raghu student extra details code start here -->										
										
			<logic:equal value="true" property="displayExtraDetails" name="onlineApplicationForm">				
			<html:hidden property="isDisplayExtraDetails" styleId="isDisplayExtraDetails" name="onlineApplicationForm" value="true" />			
			
		<tr> 
			<td valign="top" colspan="2">
			<table width="100%" border="0" align="center" cellpadding="4" cellspacing="0">
				<tr class="row-odd">
				  	<td  align="center" >
					<bean:message key="knowledgepro.applicationform.extradetails.label"/>
					</td>
				</tr>
			</table>
			</td>
		</tr>					
							
			<tr height="30">
            <logic:equal value="true" property="displayMotherTongue" name="onlineApplicationForm">
				<td height="23" class="row-even" width="50%"><div align="left"><bean:message key="knowledgepro.applicationform.mothertongue.label"/><span class="Mandatory">*</span></div></td>
	             <td height="23" class="row-even" align="left" width="50%">
					<nested:select property="applicantDetails.personalData.motherTongue" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="motherTongue">
						<html:option value="">-Select-</html:option>
						<nested:optionsCollection  property="mothertongues" name="onlineApplicationForm" label="languageName" value="languageId"/>
					</nested:select>
				</td>
			</logic:equal>
			</tr>
						<logic:equal value="true" property="displayHeightWeight" name="onlineApplicationForm">
		                     <tr>
									
									 <td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.height.label"/>
									 </td>
									 <td height="23" class="row-even" align="left" width="50%">
										<nested:text property="applicantDetails.personalData.height" styleClass="textbox" name="onlineApplicationForm" maxlength="5"></nested:text>
									</td>
							</tr>
		                    <tr>
									<td height="23" class="row-even" width="50%"><bean:message key="knowledgepro.applicationform.weight.label"/>
									</td>
									 <td height="23" class="row-even" align="left" width="50%">
										<nested:text property="applicantDetails.personalData.weight" styleClass="textbox" name="onlineApplicationForm" maxlength="6"></nested:text>
									</td>
									
		                      </tr>
	                       </logic:equal>
						<logic:equal value="true" property="displayLanguageKnown" name="onlineApplicationForm">
							<tr>
	                            <td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.speaklanguage.label"/>
	                            </td>
								<td height="23" class="row-even" align="left" width="50%">
									<nested:text property="applicantDetails.personalData.languageByLanguageSpeak" styleClass="textbox"  name="onlineApplicationForm" maxlength="50"></nested:text>
								</td>
							 </tr>
	                     	<tr>
								<td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.readlanguage.label"/>
								</td>
								<td height="23" class="row-even" align="left" width="50%">
									<nested:text property="applicantDetails.personalData.languageByLanguageRead" styleClass="textbox" name="onlineApplicationForm" maxlength="50"></nested:text>
								</td>
							 </tr>
	                    	 <tr>
								<td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.writelanguage.label"/>
								</td>
								<td height="23" class="row-even" align="left" width="50%">	
									<nested:text property="applicantDetails.personalData.languageByLanguageWrite" styleClass="textbox" name="onlineApplicationForm" maxlength="50"></nested:text>
								</td>
	                          </tr>
						</logic:equal>

						<logic:equal value="true" property="displayTrainingDetails" name="onlineApplicationForm">
							<tr>
	                            <td class="row-odd" colspan="2"><div align="center"><bean:message key="knowledgepro.applicationform.training.label"/></div></td>
							</tr>
							<tr>
	                            <td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.trainingprog.label"/></td>
								 <td height="23" class="row-even" align="left" width="50%">
									<nested:text property="applicantDetails.personalData.trainingProgName" styleClass="textbox" name="onlineApplicationForm" maxlength="50" ></nested:text>
								</td>
							 </tr>
	                     <tr>
								<td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.trainingduration.label"/></td>
								<td height="23" class="row-even" align="left" width="50%">
									<nested:text property="applicantDetails.personalData.trainingDuration" styleClass="textbox" name="onlineApplicationForm" maxlength="10" ></nested:text>
								</td>
	                          </tr>
						<tr>
	                           
								<td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.traininginst.label"/></td>
								<td  class="row-even" align="left" width="50%">
									<nested:textarea property="applicantDetails.personalData.trainingInstAddress" name="onlineApplicationForm" cols="25" rows="4" ></nested:textarea>
								</td>
						</tr>
	                     <tr>
								<td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.trainingpurpose.label"/></td>
								<td  class="row-even" align="left" width="50%">
									<nested:textarea property="applicantDetails.personalData.trainingPurpose"  name="onlineApplicationForm" cols="25" rows="4"></nested:textarea>
								</td>
	                      </tr>
							</logic:equal>
							
					<logic:equal value="true" property="displayAdditionalInfo" name="onlineApplicationForm">
							<tr>
	                            <td class="row-odd" colspan="2"><div align="center"><bean:message key="knowledgepro.applicationform.addninfo.label"/></div></td>
							</tr>
							<tr height="30">
	                            <td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.addninfo.label1"/> <B><bean:write property="organizationName" name="onlineApplicationForm"/></B>:</td>
								 <td height="23" class="row-even" align="left" width="50%">
									<nested:text property="applicantDetails.personalData.courseKnownBy" styleClass="textbox" name="onlineApplicationForm" maxlength="50" ></nested:text>
								</td>
							 </tr>
	                     <tr>
								<td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.strength.label"/></td>
								<td height="23" class="row-even" align="left" width="50%">
									<nested:text property="applicantDetails.personalData.strength" styleClass="textbox" name="onlineApplicationForm" maxlength="100" ></nested:text>
										
								</td>
								 
	                          </tr>
								<tr>
	                           
								<td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.addninfo.label2"/><B><bean:write property="applicantDetails.course.name" name="onlineApplicationForm"/> </B>:</td>
								<td height="23" class="row-even" align="left" width="50%">
									<nested:text property="applicantDetails.personalData.courseOptReason" styleClass="textbox" name="onlineApplicationForm" maxlength="100"  ></nested:text>
								</td>
								 </tr>
	                     <tr>
								<td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.weakness.label"/></td>
								<td height="23" class="row-even" align="left" width="50%">
									<nested:text property="applicantDetails.personalData.weakness"  styleClass="textbox" name="onlineApplicationForm" maxlength="100"></nested:text>
										
								</td>
	                         	 </tr>
								<tr>
	                           
								<td class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.addninfo.label3"/> <B><bean:write property="applicantDetails.course.name" name="onlineApplicationForm"/></B>:</td>
								<td class="row-even" align="left" width="50%">
									<nested:textarea property="applicantDetails.personalData.otherAddnInfo"  name="onlineApplicationForm" style="width:58%;height=50% "  cols="10" rows="4" onclick="len_display(this,200)" onkeypress="return imposeMaxLength(this, 0);" onkeyup="len_display(this,200)"  ></nested:textarea>
									<input type="text" id="otherAddnInfoTextDisply" value="200" class="len" size="2" readonly="readonly" style="border: none; background-color:rgba(250,245,250,1);
																	   background: -moz-linear-gradient(top, rgba(250,245,250,1) 0%, rgba(199,199,199,1) 44%, rgba(245,245,245,1) 100%);
																		background: -webkit-gradient(left top, left bottom, color-stop(0%, rgba(250,245,250,1)), color-stop(44%, rgba(199,199,199,1)), color-stop(100%, rgba(245,245,245,1)));
																		background: -webkit-linear-gradient(top, rgba(250,245,250,1) 0%, rgba(199,199,199,1) 44%, rgba(245,245,245,1) 100%);
																																																																																																																				filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#faf5fa', endColorstr='#f5f5f5', GradientType=0 );; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif">
									
									</td>
	                        </tr>
							</logic:equal>
							<logic:equal value="true" property="displayExtracurricular" name="onlineApplicationForm">
							<logic:notEmpty property="applicantDetails.personalData.studentExtracurricularsTos" name="onlineApplicationForm">
							<tr >
	                            <td class="row-odd" colspan="2"><div align="center"><bean:message key="knowledgepro.applicationform.extracurr.label"/></div></td>
							</tr>
							<tr >
	                            <td height="23" class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.extracurr.label2"/></td>
								 <td height="23" class="row-even" align="left" width="50%">
									
									<nested:select property="applicantDetails.personalData.extracurricularIds" styleClass="row-even" multiple="true" name="onlineApplicationForm">
										<nested:optionsCollection property="applicantDetails.personalData.studentExtracurricularsTos" name="onlineApplicationForm" label="name" value="id"/>
									</nested:select>
									
								</td>
	                        </tr>
								</logic:notEmpty>
							</logic:equal>				
			</logic:equal>	
						
			<logic:notEqual value="true" property="displayExtraDetails" name="onlineApplicationForm">	
				<html:hidden property="isDisplayExtraDetails" styleId="isDisplayExtraDetails" name="onlineApplicationForm" value="false" />			
			</logic:notEqual>		
			
			
			<!-- raghu student extra details code close here -->	
					
			
			
			<!-- raghu student passport details code stat here -->	
					
			
					<tr> 
						<td valign="top"  class="row-odd" colspan="2">
						<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
							<tr>
							  	<td class="row-odd" align="center" >
									<bean:message key="knowledgepro.admission.passportDetails"/>
								</td>
							</tr>
						</table>
						</td>
					</tr>
					
					
					
										
									<tr >
											<td  height="23" class="row-even" align="left" width="50%">
											<bean:message
												key="knowledgepro.admission.passportNo" />
											</td>
											<td  height="23" class="row-even" align="left" width="50%"><nested:text property="applicantDetails.personalData.passportNo" styleId="passportNo" styleClass="textbox"  name="onlineApplicationForm" maxlength="15"></nested:text></td>
									</tr>
	                    			
	                    			<tr>
											<td  class="row-even" align="left" width="50%">
											<bean:message
												key="knowledgepro.admission.issuingCountry" />
											
											</td>
											
											<td class="row-even" align="left" width="50%">
											<input type="hidden" id="hiddenpassportCountry"  name="onlineApplicationForm" 
												   value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.passportCountry"/>"/>
											
												<nested:select property="applicantDetails.personalData.passportCountry" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="passportCountry">
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="onlineApplicationForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
												</nested:select>
											</td>
										</tr>
										
										<tr class="row-even">
											<td height="20" class="row-even" width="50%">
											<div align="left"><bean:message
												key="knowledgepro.admission.validUpto" /><BR><bean:message key="admissionForm.application.dateformat.label"/></div>
											</td>
											<td height="25" class="row-even" align="left" width="50%">
											<nested:text property="applicantDetails.personalData.passportValidity" styleClass="textbox" name="onlineApplicationForm" styleId="passportValidity"  maxlength="15" ></nested:text>
									<script language="JavaScript">
									new tcal( {
										// form name
										'formname' :'onlineApplicationForm',
										// input name
										'controlname' :'passportValidity'
									});
								</script>  
											</td>
											 </tr>
											 
											 
											 
	                     <tr>
											<td class="row-even" width="50%">
											<div align="left"><bean:message key="knowledgepro.applicationform.residentpermit.label"/></div>
											</td>
											<td class="row-even" align="left" width="50%">
											<html:text property="applicantDetails.personalData.residentPermitNo" styleClass="textbox" name="onlineApplicationForm" styleId="residentPermit"  maxlength="10" ></html:text>
											</td>
										</tr>
										
										
										
										<tr class="row-odd">
											<td height="23" class="row-even" width="50%">
											<div align="left"><bean:message key="knowledgepro.applicationform.policedate.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/>
											</div>
											</td>
										
											<td  height="23" class="row-even" align="left" width="50%">
											<html:text property="applicantDetails.personalData.residentPermitDate" styleClass="textbox" name="onlineApplicationForm" styleId="permitDate" maxlength="10"></html:text>
												<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'onlineApplicationForm',
													// input name
													'controlname' :'permitDate'
												});
												</script>
											</td>
										</tr>			
										
	<!-- raghu student passport details code close here -->	
			
					
	<!-- raghu student interview details code start here -->	
					
					
	<html:hidden property="isInterviewSelectionSchedule" styleId="isInterviewSelectionScheduled" name="onlineApplicationForm" />				
		<logic:equal value="true"  property="isInterviewSelectionSchedule"  name="onlineApplicationForm">									
					
					
						<tr> 
						<td valign="top"  class="row-odd" colspan="2">
						<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
							<tr>
							  	<td class="row-odd" align="center" >
									<bean:message key="knowledgepro.admission.entrance.selection"/>
								</td>
							</tr>
						</table>
						</td>
					</tr>				
										
				<tr >
						<td width="50%" class="row-even" align="left"><bean:message key="knowledgepro.admission.entrance.Venue"/><span class="Mandatory">*</span></td>
						 <td width="50%" height="25" class="row-even" align="left">
							<html:select property="interviewVenue" styleId='interviewVenue' name="onlineApplicationForm" styleClass="comboExtraLarge" onchange="getDateByVenueselection(this.value)">
							    <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									<logic:notEmpty property="interviewVenueSelection" name="onlineApplicationForm">
										<html:optionsCollection property="interviewVenueSelection" label="value" value="key" name="onlineApplicationForm"/>
									</logic:notEmpty>
							</html:select>			
					    </td>
					     </tr>
	                     <tr>				
						<td width="50%" height="23" class="row-even" align="left"><bean:message key="knowledgepro.admission.entrance.Date" /><span class="Mandatory">*</span></td>
						<td width="50%" height="23" class="row-even" align="left">
							<input type="hidden" id="tempDate" name="tempDate" value='<bean:write name="onlineApplicationForm" property="interviewSelectionDate"/>' />
							<html:select property="interviewSelectionDate" name="onlineApplicationForm" styleId='interviewSelectionDate' styleClass="comboExtraLarge" >
							<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
							<logic:notEmpty property="interviewSelectionSchedule" name="onlineApplicationForm">
								<html:optionsCollection property="interviewSelectionSchedule" label="value" value="key" name="onlineApplicationForm"/>
							</logic:notEmpty>
							</html:select>
						</td>
									
						</tr>
						<tr>
							<td colspan="2">
							<br>I accept that the date opted is acceptable and binding on me and that <%= CMSConstants.ORGANISATION_COLLEGE_NAME %> is not liable to offer me an alternate date in case I am not able to attend the selection process on the date
							</td>
						</tr>
						<tr>
							<td class="heading"> &nbsp;</td>
						</tr>
						<tr>
							<td class="heading">
										<input type="hidden" name="tempChecked" id="tempChecked" value="<bean:write name="onlineApplicationForm" property="acceptAll"/>"/>
										<input type="checkbox" name="acceptAll" id="acceptAll">&nbsp;&nbsp;I Agree
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
						
						 
									
								
	</logic:equal>	
	
	<!-- raghu student interview details code close here -->	
	
	
	
	<!-- raghu student preferences details code stat here -->	
			
	
									
						
		<!--raghu write here newly  -->			
					
		<tr> 
			<td valign="top"  class="row-odd" colspan="2">
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
				<tr>
				  	<td class="row-odd" align="center" >
						<bean:message key="knowledgepro.admission.preference"/>
					</td>
				</tr>
			</table>
			</td>
	 	</tr>				
					
		<nested:iterate id="admissionpreference" name="onlineApplicationForm" property="prefcourses" indexId="count">
					<tr>
							
                    <td width="50%" class="row-even"><div align="right"><span class="Mandatory">*</span><bean:write name="admissionpreference" property="prefName"></bean:write>:</div></td>
                    <td width="50%" class="row-even"><div class="mainDisplay">
                    <bean:define id="prefNo" name="admissionpreference" property="prefNo"></bean:define>
                    <nested:select  property="id" styleClass="comboLarge"  styleId="coursePreference1" onchange='<%="addCourseId(this.value,"+prefNo+")"%>'>
					
					
					<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					<html:optionsCollection name="onlineApplicationForm" property="courseMap" label="value" value="key"/>	
					</nested:select>
					</div></td>
							
               		 </tr>
       </nested:iterate>	
       
       			
		<tr>
                   <td width="50%" class="row-even"><div align="right">&nbsp;<font size="3" color="red">*</font><font size="2" color="blue"> (To add more preferences click here) </font></div></td>
					
                 <td width="50%" class="row-even"><div align="left">
			         <html:submit property="" styleClass="formbutton" value="Add"  onclick="submitAddMorePreferences('addPrefereneces'); return false;"></html:submit>
			         <c:if test="${onlineApplicationForm.preferenceSize>1}">
			         <html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitAddMorePreferences('removePreferences'); return false;"></html:submit>
			         </c:if>
			     </div></td> 
									
       </tr>		
       
       
       <!-- raghu student preferences details code close here -->	
	
       				
		
		 <!-- raghu student work experience details code start here -->	
							
	<html:hidden property="workExpNeeded" styleId="workExpNeeded" name="onlineApplicationForm" />
	<logic:equal value="true" property="workExpNeeded" name="onlineApplicationForm">								
		<tr> 
			<td valign="top"  class="row-odd" colspan="2">
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
				<tr>
				  	<td class="row-odd" align="center" >
						<bean:message key="knowledgepro.applicationform.workexp.label"/>
					</td>
				</tr>
			</table>
			</td>
	 </tr>									
									
	 <logic:equal value="false" property="showWorkExp" name="onlineApplicationForm">
          <tr>
           <td class="row-even" width="50%" align="left">Do you have any work experience?</td>
           <td class="row-even" width="50%" align="left">
            <html:radio styleId="yesRadio" property="applicantDetails.hasWorkExp" value="true" name="onlineApplicationForm">Yes</html:radio> &nbsp;&nbsp;&nbsp;&nbsp;
            <html:radio styleId="noRadio" property="applicantDetails.hasWorkExp" value="false" name="onlineApplicationForm">No</html:radio>
            </td>
          </tr>
      </logic:equal>
                         <% String workExpListSize= session.getAttribute("workExpListSize").toString();  %> 
                         
                         <html:hidden property="workExpListSize" styleId="workExpListSize" name="onlineApplicationForm" value="<%=workExpListSize %>"/>
						<nested:iterate property="applicantDetails.workExpList" id="exp" indexId="count" name="onlineApplicationForm">
							<%String fromid="expFromdate"+count; 
								String toid="expTodate"+count;
								String occId="occupation"+count;
								String dropOccId="occupationId"+count;
								String occupationShowHide="funcOtherOccupationShowHide('"+dropOccId+"','"+occId+"','"+count+"');";
								String organization="organization"+count;
								
								
							%>
						 <tr>
                          	 <td class="row-even" width="50%" align="left">Sl No:</td>
                            <td height="25" class="row-even" align="left" width="50%"><b><%=count+1 %></b></td>
                          </tr>
                          <tr>
                          	 <td class="row-even" width="50%" align="left">
                           
                            <bean:message key="knowledgepro.applicationform.orgName.label"/>
                            <html:hidden property="workExpMandatory" styleId="workExpMandatory" name="onlineApplicationForm" />
                             <logic:equal value="true" property="workExpMandatory" name="onlineApplicationForm">
                             <% if(count==0){ %>
                            	<span class="Mandatory">*</span>
                            	<% } %>
                            	
                            </logic:equal>
                            </td>
                            <td height="27" class="row-even"><div align="left">
								<nested:text property="organization" styleId="<%=organization %>" styleClass="textbox" maxlength="100" />
                            </div></td>
                            
                           </tr>
                           <tr>
                           		<td height="27" class="row-even" width="15%" align="left"><bean:message key="knowledgepro.address"/></td>
                            	<td height="27" class="row-even" align="left">
									<nested:text property="address"  styleClass="textbox" maxlength="40" />
                            	</td>
                            
                            </tr>
                           <tr> 
                           		<td height="27" class="row-even" width="15%" align="left"><bean:message key="admissionForm.phone.main.label"/></td>
                           		 <td  height="27" class="row-even" align="left">
									<nested:text property="phoneNo"  styleClass="textbox" maxlength="15" onkeypress="return isNumberKey(event)"/>
                            	</td>
                           </tr>
                           <tr>
                            <td height="27" class="row-even" width="15%" align="left"><bean:message key="knowledgepro.applicationform.jobdesc.label"/></td>
                            <td height="27" class="row-even" align="left">
                            <logic:equal value="Other" name="exp" property="occupationId">
                            <nested:select property="occupationId" styleClass="comboExtraLarge"  styleId="<%=dropOccId %>" onchange="<%=occupationShowHide %>">
								<html:option value="">- Select -</html:option>
								<html:optionsCollection  property="occupations" label="occupationName" value="occupationId" name="exp"/>
								<option value="Other" selected="selected">Other</option>
							</nested:select>
		                	<%dynaStyle="display: block;" ;%>
							</logic:equal>
							<logic:notEqual value="Other" name="exp" property="occupationId" >
		                  	<nested:select property="occupationId" styleClass="comboExtraLarge" styleId="<%=dropOccId %>" onchange="<%=occupationShowHide %>">
								<html:option value="">- Select -</html:option>
								<html:optionsCollection property="occupations" label="occupationName" value="occupationId" name="onlineApplicationForm"/>
								<option value="Other">Other</option>
							</nested:select>
		                  	<%dynaStyle="display: none;" ;%>
							</logic:notEqual>
								<nested:text property="position" styleClass="textbox"  maxlength="30" styleId='<%=occId %>' style='<%=dynaStyle %>'/>
                            </td>
                        </tr>
                         <tr>
                         	 <td class="row-even" width="15%" align="left">
                            <bean:message key="knowledgepro.applicationform.fromdt.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/>
                            <logic:equal value="true" property="workExpMandatory" name="onlineApplicationForm">
                            	 <% if(count==0){ %>
                            	<span class="Mandatory">*</span>
                            	<% } %>
                            </logic:equal> 
                            </td>
                            <td width="224" class="row-even" align="left">
                             <nested:text property="fromDate"  styleId="<%=fromid%>" maxlength="10"  />
                              <script language="JavaScript">
								new tcal ({
									// form name
									'formname': 'onlineApplicationForm',
									// input name
									'controlname': '<%=fromid%>'
								});</script>
                            </td>
                        </tr>
                         <tr>
                         <td class="row-even" width="15%" align="left">
                           
                            <bean:message key="knowledgepro.applicationform.todt.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/>
                             <logic:equal value="true" property="workExpMandatory" name="onlineApplicationForm">
                            	 <% if(count==0){ %>
                            	<span class="Mandatory">*</span>
                            	<% } %>
                            </logic:equal>
                            </td>
                            <td class="row-even" align="left">
                             <nested:text property="toDate"  styleId="<%=toid%>" maxlength="10"  />
                              <script language="JavaScript">
							new tcal ({
								// form name
								'formname': 'onlineApplicationForm',
								// input name
								'controlname': '<%=toid%>'
							});</script>
                            </td>
                        </tr>
                        <tr>
                        <td height="27" class="row-even" width="15%" align="left"><bean:message key="knowledgepro.applicationform.lastsal.label"/></td>
					<td  height="27" class="row-even" align="left">
								<nested:text property="salary" styleClass="textbox" maxlength="10" />
                            </td>
                         </tr>
                        <tr><td>&nbsp;</td></tr>
                       
            </nested:iterate>								
	</logic:equal>	
	
	
	
	 <!-- raghu student work experience details code close here -->	
	
								
	
	 <!-- raghu student address details code start here -->	
													
	<tr> 
			<td valign="top"  class="row-odd" colspan="2">
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
				<tr>
				  	<td class="row-odd" align="center" >
						<bean:message key="admissionForm.studentinfo.currAddr.label"/>
					</td>
				</tr>
			</table>
			</td>
	 </tr>									
			
			
			
	<tr >
		<td colspan="2">
		<table width="97%" border="0" align="center" cellpadding="1" cellspacing="1">
					
					<tr>
						<td width="100%" height="91" valign="top" colspan="2">
						<table width="100%" height="90" border="0" cellpadding="0"
							cellspacing="1">
							<tr>
				<td width="50%" height="20" class="row-even" align="left">
				<div align="left"><bean:message
					key="admissionForm.studentinfo.addrs1.label" /><span class="Mandatory">*</span></div>
				</td>
				<td width="50%" height="20" class="row-even" align="left">
					<nested:text property="applicantDetails.personalData.currentAddressLine1" styleId="currentAddressLine1" styleClass="textbox" name="onlineApplicationForm" maxlength="35"></nested:text>
				</td>
				</tr>
			<tr>
				<td width="50%" class="row-even" align="left">
				<div align="left"><bean:message
					key="admissionForm.studentinfo.addrs2.label" /></div>
				</td>
				<td width="50%" class="row-even" align="left">
					<nested:text property="applicantDetails.personalData.currentAddressLine2" styleClass="textbox" name="onlineApplicationForm" maxlength="40"></nested:text>
				</td>
			</tr>
			<tr>
				<td width="50%" height="20" class="row-even">
				<div align="left"><bean:message
					key="knowledgepro.admin.city" /><span class="Mandatory">*</span>:</div>
				</td>
				<td width="50%" class="row-even" align="left">
				<nested:text property="applicantDetails.personalData.currentCityName" styleId="currentCityName" styleClass="textbox" name="onlineApplicationForm" maxlength="30"></nested:text>
				</td>
			</tr>
			<tr>
				<td class="row-even" width="50%" align="left">
				<div align="left"><bean:message
					key="knowledgepro.admin.country" /><span class="Mandatory">*</span></div>
				</td>
				<td class="row-even" width="50%" align="left">
						<input type="hidden" id="currentCountryNamehidden" name="nationality" name="onlineApplicationForm" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.currentCountryId"/>"/>
				
					<nested:select property="applicantDetails.personalData.currentCountryId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="currentCountryName" onchange="getTempAddrStates(this.value,'tempAddrstate');">
					<option value=""><bean:message key="knowledgepro.admin.select"/></option>
						<%String selected=""; %>
						<logic:iterate id="option" property="countries" name="onlineApplicationForm">
							<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
							<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
							<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
						</logic:iterate>
				</nested:select>
				</td>
			</tr>
			<tr class="row-even">
				<td  class="row-even" width="50%" align="left">
				<div align="left"><bean:message
					key="knowledgepro.admin.state" /><span class="Mandatory">*</span></div>
				</td>
				<td class="row-even" width="50%" align="left">
				<input type="hidden" id="currentStateId1" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.currentStateId"/>">
				<logic:equal value="Other" property="applicantDetails.personalData.currentStateId" name="onlineApplicationForm">
			<%dynaStyle="display:block;"; %>
		</logic:equal>
		<logic:notEqual value="Other" property="applicantDetails.personalData.currentStateId" name="onlineApplicationForm">
			<%dynaStyle="display:none;"; %>
		</logic:notEqual>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
                    				<tr><td width="50%" align="left" class="row-even"><nested:select property="applicantDetails.personalData.currentStateId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="tempAddrstate" onchange="funcOtherShowHide('tempAddrstate','otherTempAddrState')">
		<html:option value="">- Select -</html:option>
		<!--<c:if test="${onlineApplicationForm.applicantDetails.personalData.currentCountryId != null && onlineApplicationForm.applicantDetails.personalData.currentCountryId!= ''}">
                       					<c:set var="tempAddrStateMap" value="${baseActionForm.collectionMap['tempAddrStateMap']}"/>
                       		    	 	<c:if test="${tempAddrStateMap != null}">
                       		    	 		<html:optionsCollection name="tempAddrStateMap" label="value" value="key"/>
								<html:option value="Other">Other</html:option>
                       		    	 	</c:if> 
                    </c:if>
		-->
		<logic:notEmpty property="stateMap" name="onlineApplicationForm">
					<html:optionsCollection name="onlineApplicationForm" property="curAddrStateMap"
										label="value" value="key" />
									
					</logic:notEmpty>
					<html:option value="Other">Other</html:option>
		</nested:select></td></tr>
<tr><td align="left" class="row-even"><nested:text property="applicantDetails.personalData.currentAddressStateOthers" styleClass="textbox" name="onlineApplicationForm" maxlength="30" styleId="otherTempAddrState" style="<%=dynaStyle %>"></nested:text></td></tr>
</table>
				</td>
		</tr>
	<tr class="row-even">
				<td class="row-even" align="left" width="50%">
				<div align="left"><bean:message
					key="knowledgepro.admission.zipCode" /><span class="Mandatory">*</span></div>
				</td>
				<td class="row-even" align="left" width="50%"><nested:text styleClass="textbox" property="applicantDetails.personalData.currentAddressZipCode" styleId="zipCode" name="onlineApplicationForm"  maxlength="10"></nested:text>
				</td>
			</tr>
						
					</table>
				</td>
			</tr>
			
		</table>
	</td>
</tr>		


								
		<tr>
		<td  class="heading">
		<table width="97%" border="0" align="center" cellpadding="1" cellspacing="1">
		<tr>
                  	<td  class="heading">&nbsp;&nbsp;<bean:message key="knowledgepro.applicationform.sameaddr.label"/>
                      <html:radio property="sameTempAddr" styleId="sameAddr" name="onlineApplicationForm" value="true" onclick="disableTempAddress()"><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
						<html:radio property="sameTempAddr" styleId="DiffAddr" name="onlineApplicationForm" value="false" onclick="enableTempAddress()"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio></td>
                	</tr>
               </table>
              </td>
         </tr>	
         
         
         					
	<tr> 
			<td valign="top"  class="row-odd" colspan="2">
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
				<tr>
				  	<td class="row-odd" align="center" >
						<bean:message key="admissionForm.studentinfo.permAddr.label"/>
					</td>
				</tr>
			</table>
			</td>
	 </tr>	
	 
	 
	 						
								
	<tr id="currTable">
							<td colspan="2">
							<table width="97%" border="0" align="center" cellpadding="1"
								cellspacing="1">
								<tr>
									<td width="100%" height="91" valign="top" colspan="2">
									<table width="100%" height="90" border="0" cellpadding="0"
										cellspacing="1">
										<tr>
										<td  class="row-even" align="left" width="50%">
											<div align="left"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /><span class="Mandatory">*</span></div>
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:text property="applicantDetails.personalData.permanentAddressLine1" styleId="permanentAddressLine1" styleClass="textbox" name="onlineApplicationForm"  maxlength="35"></nested:text>
											</td>
										</tr>
										<tr>
											<td width="50%" class="row-even" align="left">
												<bean:message key="admissionForm.studentinfo.addrs2.label" />
											</td>
											<td width="50%" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.permanentAddressLine2" styleClass="textbox"  name="onlineApplicationForm"  maxlength="40"></nested:text>
											</td>
										</tr>
										<tr>
											<td width="50%" class="row-even" align="left">
											<div align="left"><bean:message
												key="knowledgepro.admin.city" /><span class="Mandatory">*</span>:</div>
											</td>
											<td width="50%" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.permanentCityName" styleId="permanentCityName" styleClass="textbox" name="onlineApplicationForm"  maxlength="30"></nested:text>
											</td>
										</tr>
										<tr>
											<td class="row-even" align="left" width="50%">
											<bean:message
												key="knowledgepro.admin.country" /><span class="Mandatory">*</span>
											</td>
											<td class="row-even" align="left" width="50%">
											<input type="hidden" id="permanentCountryNamehidden" name="nationality" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.permanentCountryId"/>"/>
											
											<nested:select property="applicantDetails.personalData.permanentCountryId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="permanentCountryName" onchange="getPermAddrStates(this.value,'permAddrstate');">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="onlineApplicationForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
											</nested:select>
											</td>
										</tr>
										<tr>
											<td class="row-even" width="50%" align="left">
											<div align="left"><bean:message
												key="knowledgepro.admin.state" /><span class="Mandatory">*</span></div>
											</td>
											<td class="row-even" align="left" width="50%">
											<input type="hidden" id="permanentState" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.permanentStateId"/>">
												<logic:equal value="Other" property="applicantDetails.personalData.permanentStateId" name="onlineApplicationForm">
													<%dynaStyle="display:block;"; %>
												</logic:equal>
												<logic:notEqual value="Other" property="applicantDetails.personalData.permanentStateId" name="onlineApplicationForm">
													<%dynaStyle="display:none;"; %>
												</logic:notEqual>
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
                           						 <tr><td class="row-even"><nested:select property="applicantDetails.personalData.permanentStateId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="permAddrstate" onchange="funcOtherShowHide('permAddrstate','otherPermAddrState')">
													<html:option value="">- Select -</html:option>
									
												<logic:notEmpty property="stateMap" name="onlineApplicationForm" >
													<html:optionsCollection name="onlineApplicationForm" property="perAddrStateMap" label="value" value="key" />
													
												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
												</nested:select></td></tr>
												<tr><td class="row-even"><nested:text property="applicantDetails.personalData.permanentAddressStateOthers" name="onlineApplicationForm" styleClass="textbox" maxlength="30" styleId="otherPermAddrState" style="<%=dynaStyle %>"></nested:text></td></tr>
												</table>
											</td>
						</tr>
						<tr class="row-even">
											<td class="row-even">
											<div align="left"><bean:message
												key="knowledgepro.admission.zipCode" /><span class="Mandatory">*</span></div>
											</td>
											<td class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.permanentAddressZipCode" styleId="permanentAddressZipCode" styleClass="textbox" name="onlineApplicationForm" maxlength="10"></nested:text>
											</td>
										</tr>
									
								</table>
							</td>
						</tr>
						
					</table>
				</td>
				</tr>	
				
				
				
				
			
				
				
	 <tr>
		<td  class="row-even" align="right" colspan="2">
      		<html:button property="" styleClass="buttons" value="Save Draft"  styleId="saveDraft5" onclick="submitSaveDraft('zipCode'); return false;"></html:button>
		</td>
	</tr>			
			
			<!-- raghu student  address details code close here -->	
		
			
			
			
			<!-- raghu student  education details code start here -->	
		
		
	<tr> 
			<td valign="top"  class="row-odd" colspan="2">
			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
				<tr>
				  	<td class="row-odd" align="center" >
						<bean:message key="knowledgepro.admission.educationalInfo"/>
					</td>
				</tr>
			</table>
			</td>
	 </tr>		
	 
	 		
										
	<tr>
	<td colspan="2" valign="top">
	<table width="97%" border="0" align="center" cellpadding="1" cellspacing="1">
		<tr>					
		<td width="100%" valign="top">
		<table width="100%" cellspacing="1" cellpadding="2" border=0>
		
 <% String qualificationListSize= session.getAttribute("eduQualificationListSize").toString();  %> 
                         
 <html:hidden property="ednQualificationListSize" styleId="ednQualificationListSize" name="onlineApplicationForm" value="<%=qualificationListSize %>"/>		
		
	<logic:notEmpty name="onlineApplicationForm" property="applicantDetails.ednQualificationList">
										
        <nested:iterate  name="onlineApplicationForm" property="applicantDetails.ednQualificationList" indexId="count" id="qualDoc">
			
			
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
				String courseSettingsJsMethod="getMarkEntryAvailable(this,'"+count+"');";
				String collegeJsMethod=courseSettingsJsMethod+"getColleges('Map"+count+"',this,"+count+");";
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
				
				String isExamConfigured="isExamConfigured_"+count;
				String blockedMarsks="blockedMarks_"+count;
				String showHideBlockMarsk="showHideExamPassYearMonth_"+count;
				
				String marksObtained="marksObtained_"+count;
				String maxMarks="maxMarks_"+count;
				String lastExam="lastExam_"+count;
				String consolidated="consolidated_"+count;
				String checkAlertMarksObtained="checkAlertMarksObtained("+count+")";
				String checkAlertMaxMarks="checkAlertMaxMarks("+count+")";
				String checkAlertMarksObtainedBySemisterWise="checkAlertMarksObtainedBySemisterWise("+count+")";
				String checkAlertMaxMarksSemisterWise="checkAlertMaxMarksSemisterWise("+count+")";
				
			%>
			
			<bean:define id="countIndex" name="qualDoc" property="countId"></bean:define>
			<input type="hidden" id="countID" name="countID" >
			
			<!--
          <tr>
         	 <td height="25" class="row-even" align="left" width="50%"><b><bean:message key="knowledgepro.slno" /></b></td>
            <td height="25" class="row-even" align="left" width="50%"><b><%=count+1 %></b></td>
           </tr>
           -->
           
           
           <!-- raghu doc name -->	
		
           
       <tr>
           <td height="25" class="row-even" align="left" width="50%"><b><bean:message key="knowledgepro.admission.qualification" /></b></td>
            <td height="25" class="row-even" align="left" width="50%" ><b><nested:write property="docName" name="qualDoc"/></b></td>
		</tr>
		
		
		<!-- raghu exam name -->	
		
		<logic:equal value="true" name="qualDoc" property="examConfigured">
		<html:hidden property="isExamConfigured" styleId="<%=isExamConfigured %>" name="onlineApplicationForm" value="true"/>		
          
          
           <tr>
           <td class="row-even" align="left" width="50%">Exam Name<span class="Mandatory">*</span></td>
			<td class="row-even" align="left" width="50%">
					
					<c:set var="dexmid"><%=dynaExamId %></c:set>
				<nested:select property="selectedExamId" styleClass="comboExtraLarge" styleId='<%=dynaExamId %>'>
					<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					<logic:notEmpty name="qualDoc" property="examTos">
						<html:optionsCollection name="qualDoc" property="examTos" label="name" value="id"/>
					</logic:notEmpty>	
				</nested:select>
					<script type="text/javascript">
						var exmid= '<nested:write property="selectedExamId"/>';
						document.getElementById("<c:out value='${dexmid}'/>").value = exmid;
					</script>
				</td>
		</tr>
		
		
		</logic:equal>
		
		
		
		<logic:notEqual value="true" name="qualDoc" property="examConfigured">
			<html:hidden property="isExamConfigured" styleId="<%=isExamConfigured %>" name="onlineApplicationForm" value="false"/>
		</logic:notEqual>
		
		
		<!-- raghu university name -->	
		
		
       <tr>
			<td class="row-even" align="left" width="50%">
			<bean:message key="knowledgepro.admission.universityBoard" /><span class="Mandatory">*</span></td>
            <td class="row-even" align="left" width="50%" >
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
				<td class="row-even">
               <c:set var="dunid"><%=dynaid %></c:set>
               <nested:select property="universityId" styleId="<%=dynaid %>" styleClass="comboExtraLarge" onchange="<%=showhide %>">
					<option value="">Select</option>
					<logic:notEmpty name="qualDoc" property="universityList">
					<logic:iterate id="option"  name="qualDoc" property="universityList">
						<option value='<bean:write name="option" property="id"/>'><bean:write name="option" property="name"/> </option>
					</logic:iterate>
					</logic:notEmpty>
						<option value="Other">Other</option>
               </nested:select>
					<script type="text/javascript">
					var id= '<nested:write property="universityId"/>';
					document.getElementById("<c:out value='${dunid}'/>").value = id;
					</script>
            	</td>
				</tr>
				<tr>
					
                  <td class="row-even">
					<logic:equal value="Other" name="qualDoc" property="universityId">
                	<%dynaStyle="display: block;" ;%>
					</logic:equal>
					<logic:notEqual value="Other" name="qualDoc" property="universityId">
                  	<%dynaStyle="display: none;" ;%>
					</logic:notEqual>
  					<nested:text property="universityOthers" size="10" maxlength="50" style='<%=dynaStyle %>' styleId='<%=dynarow1 %>'></nested:text>
                  </td>
                  
                </tr>
			</table></td>
	</tr>
	
	
	
	<!-- raghu institution name -->	
		
	
     <tr>
		<td class="row-even" align="left" width="50%">
			<bean:message key="knowledgepro.admission.instituteName" /><span class="Mandatory">*</span></td>
            <td class="row-even" align="left" width="50%" ><table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
				<td class="row-even">
				<c:set var="dinid"><%=instituteId %></c:set>
                <c:set var="temp"><nested:write property="universityId"/></c:set>
	                <nested:select property="institutionId" styleClass="comboExtraLarge" styleId='<%=instituteId %>' onchange='<%=instituteJsMethod %>' >
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
                  <td class="row-even">
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
	</tr>
	
	
	
	
	<!-- raghu edn state name -->	
		
	
    <tr>
    
		<td class="row-even" align="left" width="50%"><bean:message key="admissionForm.education.State.label"/><span class="Mandatory">*</span></td>
        <td class="row-even" align="left" width="50%" >
				<c:set var="dstateid"><%=dynaStateId %></c:set>
			<nested:select property="stateId" styleClass="comboExtraLarge" styleId='<%=dynaStateId %>'>
				<option value=""><bean:message key="knowledgepro.admin.select"/></option>
				<logic:notEmpty name="onlineApplicationForm" property="ednStates">
				 <nested:optionsCollection name="onlineApplicationForm" property="ednStates" label="name" value="id"/>
				</logic:notEmpty>
				<option value="OUTSIDEINDIA"><bean:message key="admissionForm.education.outsideindia.label"/></option>
			</nested:select>
				<script type="text/javascript">
					var stid= '<nested:write property="stateId"/>';
					document.getElementById("<c:out value='${dstateid}'/>").value = stid;
				</script>
			</td>
    </tr>
    
    
    
    
    
    <tr>
		<td width="50%" colspan="2">
		<div id="<%=showHideBlockMarsk%>">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
		
		
		<!-- raghu passing year and month details name -->	
		
    <tr>
    		<td class="row-even" align="left" width="50%">
    		<bean:message key="knowledgepro.admission.passingYear"/><span class="Mandatory">*</span></td>
            <td class="row-even" align="left" width="50%" >
				<c:set var="dyopid"><%=dynaYearId %></c:set>
			<nested:select property="yearPassing" styleId='<%=dynaYearId %>' styleClass="comboExtraLarge">
				<html:option value="">Select</html:option>
              	<cms:renderYear normalYear="true"></cms:renderYear>
			</nested:select>
				<script type="text/javascript">
					var yopid= '<nested:write property="yearPassing"/>';
					if(yopid!=0)
					document.getElementById("<c:out value='${dyopid}'/>").value = yopid;
				</script>
             </td>
       </tr>
       
      
       <tr>
      	<td class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.passingmonth"/><span class="Mandatory">*</span></td>
            <td class="row-even" align="left" width="50%" >
				<c:set var="dmonid"><%=dynamonthId %></c:set>
			<nested:select property="monthPassing" styleId='<%=dynamonthId %>' styleClass="comboExtraLarge">
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
           </td>
       </tr>
       
       </table>
       </div></td>
       </tr>
       
       
       
       <!-- raghu exam attempts details name -->	
		
       <tr>
      		 <td class="row-even" align="left" width="50%">
      		 	<bean:message key="knowledgepro.admission.attempts"/><span class="Mandatory">*</span></td>
        	<td class="row-even" align="left" width="50%" >
				<c:set var="dAttemptid"><%=dynaAttemptId %></c:set>
                <nested:select property="noOfAttempts" styleId='<%=dynaAttemptId %>' styleClass="comboExtraLarge">
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
           </td>
		</tr>
		
		
		
		<nested:hidden property="blockedMarks" styleId="<%=blockedMarsks %>"></nested:hidden>
		<!-- raghu marks details name -->	
		
		<logic:equal value="true" name="qualDoc" property="lastExam">
		<tr>
		<td width="50%" colspan="2">
		<div id="showHideBlockMarks">
		<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
		      <tr>
	        	<td class="row-even" align="left" width="50%"><bean:message key="knowledgepro.applicationform.prevregno.label"/><span class="Mandatory">*</span></td>
	            <td class="row-even" align="left" width="50%" ><nested:text property="previousRegNo" size="10" maxlength="15"/></td>
         	</tr>
         <nested:equal value="false"  property="consolidated" name="qualDoc">
         
         <!-- raghu semster marks details name -->	
		
				<nested:equal value="true"  property="semesterWise" name="qualDoc">
					<%String detailSemesterLink="studentEdit.do?method=initSemesterMarkPage&countID="+countIndex; %>
          		  	<tr id="HideMarks1">
          		  	 <td class="row-even" align="center" colspan="2"><span class="Mandatory">*</span><a href="#" onclick="detailSemesterSubmit('<%=countIndex %>')"><FONT size="3" color="blue"><bean:message key="knowledgepro.applicationform.semsestermark.link"/></FONT></a></td>
					</tr>
				
				</nested:equal>
				
				<!-- raghu doc marks details name -->	
		
				<nested:equal value="false"  property="semesterWise" name="qualDoc">
				<%String detailMarkLink="studentEdit.do?method=initDetailMarkPage&countID="+countIndex; %>	
				<!-- raghu -->
				<c:choose>
				
				<c:when test="${qualDoc.docName=='DEG'}">
				<tr id="HideMarks1">
				<td class="row-even" align="center" colspan="2"><span class="Mandatory">*</span><a href="#" onclick="detailSubmitDegree('<%=countIndex %>')" ><FONT size="3" color="blue"><bean:message key="knowledgepro.applicationform.detailmark.link"/>DEGREE</FONT></a></td>
				</tr>
				</c:when>
				
				<c:when test="${qualDoc.docName=='Class 12'}">
				<tr id="HideMarks1">
				<td class="row-even" align="center" colspan="2"><span class="Mandatory">*</span><a href="#" onclick="detailSubmitClass12('<%=countIndex %>')" ><FONT size="3" color="blue"><bean:message key="knowledgepro.applicationform.detailmark.link"/>CLASS 12</FONT></a></td>
				</tr>
				</c:when>
				
				<c:otherwise>
	          		<tr id="HideMarks1">
	          		   <td class="row-even" align="center" colspan="2"><span class="Mandatory">*</span><a href="#" onclick="detailSubmit('<%=countIndex %>')" ><FONT size="3" color="blue"><bean:message key="knowledgepro.applicationform.detailmark.link"/>CLASS 10</FONT></a></td>
					</tr>
				</c:otherwise>
				
				</c:choose>
				
				</nested:equal>
			</nested:equal>
			
			<!-- raghu cosolidate marks details name -->	
		
			<nested:equal value="true" property="consolidated" name="qualDoc">
			<tr id="HideMarks1">
				<td class="row-even" align="left" width="50%">
					<bean:message key="knowledgepro.admission.marksObtained" /><span class="Mandatory">*</span>
			    </td>
          	 	<td class="row-even" align="left" width="50%" >
              		<nested:text  property="marksObtained" size="5" maxlength="8" ></nested:text>
              </td>
            </tr>
            <tr id="HideMarks2">
            <td class="row-even" align="left" width="50%" >
				
				<bean:message key="knowledgepro.admission.maxMark"/><span class="Mandatory">*</span></td>
          	 <td class="row-even" align="left" width="50%" >
              	<nested:text property="totalMarks" size="5" maxlength="8" ></nested:text>
           	</td>
           </tr>
         </nested:equal>
         </table>
		</div>
		</td>
		</tr>
		
		<tr id="showBlockedMarksMsg">
          	<td style=font-style:normal;font-weight:bolder;color:red align="center" colspan="2"> "The <nested:write property="docName" name="qualDoc"/> Exam Register No, Month of pass, 
          	Year of pass, Max. mark and obtained marks to be submitted within 3 days after the Board Exam results are announced."</td>
          
        </logic:equal>
        
        <!-- raghu marks details name -->	
		
        
        <logic:equal value="false" name="qualDoc" property="lastExam">
				<nested:equal value="false"  property="consolidated" name="qualDoc">
				
				<!-- raghu semester marks details name -->	
		
				<nested:equal value="true"  property="semesterWise" name="qualDoc">
					<%String detailSemesterLink="studentEdit.do?method=initSemesterMarkPage&countID="+countIndex; %>
          		  	<tr>
          		  	 <td class="row-even" align="center" width="50%" colspan="2"><span class="Mandatory">*</span><a href="#" onclick="detailSemesterSubmit('<%=countIndex %>')"><FONT size="3" color="blue"><bean:message key="knowledgepro.applicationform.semsestermark.link"/></FONT></a></td>
					</tr>
				
				</nested:equal>
				
				<!-- raghu doc marks details name -->	
		
				<nested:equal value="false"  property="semesterWise" name="qualDoc">
				
				
				<%String detailMarkLink="studentEdit.do?method=initDetailMarkPage&countID="+countIndex; %>
	          		<tr>
	          		   <td class="row-even" align="center" width="50%" colspan="2"><span class="Mandatory">*</span><a href="#" onclick="detailSubmit('<%=countIndex %>')" ><FONT size="3" color="blue"><bean:message key="knowledgepro.applicationform.detailmark.link"/></FONT></a></td>
					</tr>
					
					
				<!-- raghu -->
				<c:choose>
				
				<c:when test="${qualDoc.docName=='DEG'}">
				<tr id="HideMarks1">
				<td class="row-even" align="center" width="50%" colspan="2"><span class="Mandatory">*</span><a href="#" onclick="detailSubmitDegree('<%=countIndex %>')" ><FONT size="3" color="blue"><bean:message key="knowledgepro.applicationform.detailmark.link"/>DEGREE</FONT></a></td>
				
				</tr>
				</c:when>
				
				<c:when test="${qualDoc.docName=='Class 12'}">
				<tr id="HideMarks1">
				<td class="row-even" align="center" width="50%" colspan="2"><span class="Mandatory">*</span><a href="#" onclick="detailSubmitClass12('<%=countIndex %>')" ><FONT size="3" color="blue"><bean:message key="knowledgepro.applicationform.detailmark.link"/>CLASS 12</FONT></a></td>
					
				</tr>
				</c:when>
				
				<c:otherwise>
	          		<tr id="HideMarks1">
	          		   <td class="row-even" align="center" width="50%" colspan="2"><span class="Mandatory">*</span><a href="#" onclick="detailSubmit('<%=countIndex %>')" ><FONT size="3" color="blue"><bean:message key="knowledgepro.applicationform.detailmark.link"/></FONT></a></td>
					</tr>
				</c:otherwise>
				
				</c:choose>
					
				</nested:equal>
			</nested:equal>
			
			<!-- raghu consolidate marks name -->	
		
			
			<nested:equal value="true" property="consolidated" name="qualDoc">
			<tr>
				<td class="row-even" align="left" width="50%">
					<bean:message key="knowledgepro.admission.marksObtained" /><span class="Mandatory">*</span>
			    </td>
          	 	<td class="row-even" align="left" width="50%" >
              		<nested:text  property="marksObtained" size="5" maxlength="8" ></nested:text>
              </td>
            </tr>
            <tr>
            <td class="row-even" align="left" width="50%" >
				
				<bean:message key="knowledgepro.admission.maxMark"/><span class="Mandatory">*</span></td>
          	 <td class="row-even" align="left" width="50%" >
              	<nested:text property="totalMarks" size="5" maxlength="8" ></nested:text>
           	</td>
           </tr>
           </nested:equal>
           
        </logic:equal>
        
        
        
           <tr><td>&nbsp;</td></tr>
			
			<%-- 
		  	</nested:iterate>
		</logic:notEmpty>
			--%>
		
		<!-- raghu student  tc details code start here -->	
		
		
		<logic:equal value="true" property="displayTCDetails" name="onlineApplicationForm">
					<tr>
											<td height="25" class="row-even" width="50%" align="left">
											<bean:message key="admissionForm.education.TCNO.label"/>
											</td>
											<td height="25" class="row-even" width="50%" align="left">
											<nested:text property="applicantDetails.tcNo" size="6" maxlength="10" name="onlineApplicationForm"></nested:text></td>
									</tr>
									<tr>
											<td class="row-even" width="50%">
											<div align="left"><bean:message key="admissionForm.education.TCDate.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/></div>
											</td>
											<td class="row-even" width="50%">
											<div align="left"><nested:text property="applicantDetails.tcDate" styleId="tcdate" size="10" maxlength="10" name="onlineApplicationForm"></nested:text>
											<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'onlineApplicationForm',
													// input name
													'controlname' :'tcdate'
												});
											</script> </div>
											</td>
								</tr>
								<tr>
											<td class="row-even" width="50%" align="left"><bean:message key="admissionForm.education.markcard.label"/></td>
											<td class="row-even" width="50%">
											<div align="left"><nested:text property="applicantDetails.markscardNo" size="6" maxlength="10" name="onlineApplicationForm"></nested:text></div>
											</td>
								</tr>
								<tr>
											<td class="row-even" width="50%" align="left">
											<bean:message key="admissionForm.education.markcarddate.label"/><BR><bean:message key="admissionForm.application.dateformat.label"/>
											</td>
											<td class="row-even" width="50%">
											<div align="left"><nested:text property="applicantDetails.markscardDate" styleId="markscardDate" size="10" maxlength="10" name="onlineApplicationForm"></nested:text>
												<script language="JavaScript">
														new tcal( {
															// form name
															'formname' :'onlineApplicationForm',
															// input name
															'controlname' :'markscardDate'
														});
													</script></div>
											</td>
							</tr>
			</logic:equal>
			
			
			<!-- raghu student  literal details code start here -->	
		
			
			<logic:equal value="true" property="displayLateralTransfer"	name="onlineApplicationForm">
											<tr>
												<td class="row-even" width="50%">
												<div align="left">&nbsp;</div>
												</td>
												<td height="25" class="row-even" width="50%">
												<logic:equal value="true" property="displayLateralDetails"	name="onlineApplicationForm">
													<div align="left"><a href="#"
														onclick="detailLateralSubmit()"><bean:message key="admissionForm.education.laterallink.label"/></a></div>

												</logic:equal></td>
											</tr>
											<tr>
												<td height="25" class="row-even" width="50%">
												<div align="left">&nbsp;</div>
												</td>
												<td class="row-even" width="50%">
												<logic:equal
													value="true" property="displayTransferCourse"
													name="onlineApplicationForm">
													<div align="left"><a href="#" onclick="detailTransferSubmit()"><bean:message key="admissionForm.education.transferlink.label"/></a></div>
												</logic:equal></td>
										</tr>

			 </logic:equal>
								
								
								<!-- raghu backlogs name -->	
		
								
									<tr>
										<td align="left" class="row-even" width="50%">Backlogs in previous semesters/years to be cleared</td>
										<td class="row-even" width="50%" align="left" >
											<html:radio property="backLogs" name="onlineApplicationForm" styleId="backLogs" value="true"><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
											<html:radio property="backLogs" name="onlineApplicationForm" styleId="backLogs" value="false"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio>
										</td>
									</tr>
									
									</table>
									</div>
									
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								
								
								
								</nested:iterate>
								</logic:notEmpty>
			
								
								
								
					<tr>
							<td  class="row-even" align="right" colspan="2">
					      		<html:button property="" styleClass="buttons" value="Save Draft"  styleId="saveDraft6" onclick="submitSaveDraft('backLogs'); return false;"></html:button>
							</td>
				</tr>	
								
							</table>
							</td>
						</tr>							
			
			
			<!-- raghu student  education details code over here -->	
						
				
				
		<!-- raghu student  entrance details code start here -->	
				
				
								
		<logic:equal value="true" property="displayEntranceDetails" name="onlineApplicationForm">								
										
			<tr> 
					<td valign="top"  class="row-odd" colspan="2">
					<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
						<tr>
						  	<td class="row-odd" align="center" >
								<bean:message key="admissionForm.education.entrancedetails.label"/>
							</td>
						</tr>
					</table>
					</td>
			 </tr>							
			<tr>
		<td colspan="2" valign="top">
			<table width="97%" border="0" align="center" cellpadding="1" cellspacing="1">
								<tr>
									<td width="100%" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr >
            								<td width="50%" height="25" class="row-even"><div align="left"><bean:message key="admissionForm.education.entrance.label"/></div></td>
												<td width="50%" height="25" class="row-even"><div align="left">
												<nested:select property="applicantDetails.entranceDetail.entranceId" styleClass="comboExtraLarge" name="onlineApplicationForm">
													<html:option value="">-Select-</html:option>
													<logic:notEmpty property="entranceList" name="onlineApplicationForm">
													<html:optionsCollection property="entranceList" name="onlineApplicationForm" label="name" value="id"/>
													</logic:notEmpty>
										
												</nested:select>
													</div></td>
										</tr>
										<tr>
											<td width="50%" height="25" class="row-even"><div align="left"><bean:message key="knowledgepro.admission.totalMarks"/>:</div></td>
											<td width="50%" height="25" class="row-even"><div align="left"><nested:text property="applicantDetails.entranceDetail.totalMarks" styleClass="textbox"  name="onlineApplicationForm"  maxlength="8"></nested:text></div></td>
										</tr>
										<tr>
											<td width="50%" height="25" class="row-even"><div align="left"><bean:message key="admissionForm.education.markObtained.label"/>:</div></td>
											<td width="50%" height="25" class="row-even"><div align="left"><nested:text property="applicantDetails.entranceDetail.marksObtained" styleClass="textbox"  name="onlineApplicationForm"  maxlength="8"></nested:text></div></td>
           								 </tr>
										<tr>
            								<td width="50%" height="25" class="row-even"><div align="left"><bean:message key="knowledgepro.applicationform.prereq.roll.label"/></div></td>
											<td width="50%" height="25" class="row-even"><div align="left"><nested:text property="applicantDetails.entranceDetail.entranceRollNo" styleClass="textbox" name="onlineApplicationForm"  maxlength="25"></nested:text></div></td>
										</tr>
										<tr>
											<td width="50%" height="25" class="row-even"><div align="left"><bean:message key="knowledgepro.applicationform.passingmonth"/>:</div></td>
											<td width="50%" height="25" class="row-even"><div align="left">
											<nested:select property="applicantDetails.entranceDetail.monthPassing"  styleClass="comboExtraLarge"  name="onlineApplicationForm">
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
										</tr>
										<tr>
											<td width="50%" height="25" class="row-even"><div align="left"><bean:message key="knowledgepro.admission.passingYear"/>:</div></td>
											<td width="50%" height="25" class="row-even"><div align="left">
											<nested:select property="applicantDetails.entranceDetail.yearPassing" styleId='entranceyear' styleClass="comboExtraLarge"  name="onlineApplicationForm">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
								              	<cms:renderYear normalYear="true"></cms:renderYear>
											</nested:select>
												<script type="text/javascript">
													var entyopid= '<nested:write property="applicantDetails.entranceDetail.yearPassing"  name="onlineApplicationForm"/>';
													document.getElementById("entranceyear").value = entyopid;
												</script>
											</div></td>
            							</tr>
            							
            	<tr>
							<td  class="row-even" align="right" colspan="2">
					      		<html:button property="" styleClass="buttons" value="Save Draft"  styleId="saveDraft7" onclick="submitSaveDraft('entranceyear'); return false;"></html:button>
							</td>
				</tr>
									</table>
									</td>
									
								</tr>
								
							</table>
							</td>
						</tr>							
								
		</logic:equal>						
				
				
				<!-- raghu student  entrance details code over here -->	
		
				
				
				
				<!-- raghu student  parent details code start here -->	
		
						
	<tr> 
					<td valign="top"  class="row-odd" colspan="2">
					<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
						<tr>
						  	<td class="row-odd" align="center" >
								<bean:message key="knowledgepro.admission.parentInfo"/>
							</td>
						</tr>
					</table>
					</td>
 </tr>		
 
 
 					
						
									<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="knowledgepro.admission.fatherName" /><span class="Mandatory">*</span>
											</td>
											<td width="50%"class="row-even" align="left">
											<nested:select property="applicantDetails.titleOfFather" styleId='titleOfFather' name="onlineApplicationForm" styleClass="comboExtraLarge" style="max-width:15%;" onchange="fatherIncomeMandatory()">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
								              	<html:option value="Mr">Mr.</html:option>
								              	<html:option value="Late">Late.</html:option>
											</nested:select>
											<nested:text property="applicantDetails.personalData.fatherName" styleId="fatherName" name="onlineApplicationForm" styleClass="textboxsmall" maxlength="50"></nested:text>
											</td>
										</tr>
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="knowledgepro.admission.education" />
											</td>
											<td align="left" width="50%"  class="row-even" >
												<nested:text property="applicantDetails.personalData.fatherEducation" name="onlineApplicationForm" styleClass="textbox" maxlength="50"></nested:text>
											</td>
										</tr>
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message key="admissionForm.parentinfo.currency.label"/>
											</td>
											<td align="left" width="50%" class="row-even" >
											<nested:select property="applicantDetails.personalData.fatherCurrencyId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="fatherCurrency">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection name="onlineApplicationForm" property="currencyMap" label="value" value="key"  />

											</nested:select>
											</td>
										</tr>
										<tr>
											<td align="left" width="50%" class="row-even">
											<div id="incomeMandatory"><bean:message
												key="admissionFormForm.fatherIncome" /></div>
											</td>
											<td align="left" width="50%" class="row-even" >
											<nested:select property="applicantDetails.personalData.fatherIncomeId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="fatherIncomeRange">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection name="onlineApplicationForm" property="incomeList" label="incomeRange" value="id"/>
											</nested:select>
											</td>
										</tr>
										
										<tr>
											<td align="left" width="50%"class="row-even">
											<bean:message
												key="knowledgepro.admin.occupation" />:
											</td>
											
											
											
											<td align="left" width="50%" class="row-even">
											<input type="hidden" id="hiddenFatherOccupationId"  name="onlineApplicationForm" 
												   value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.fatherOccupationId"/>"/>
											<nested:select name="onlineApplicationForm" property="applicantDetails.personalData.fatherOccupationId" styleClass="comboExtraLarge" styleId="fatherOccupation" onchange="displayOtherForFather(this.value)">
													<html:option value="">- Select -</html:option>
													<html:optionsCollection name="onlineApplicationForm" property="occupations" label="occupationName" value="occupationId"/>
													<html:option value="other">Other</html:option>
												</nested:select><br/>
												 <div id="displayFatherOccupation">
														 &nbsp;<nested:text property="applicantDetails.personalData.otherOccupationFather" name="onlineApplicationForm" styleClass="textbox" maxlength="50" styleId="otherOccupationFather"/>
												 </div>
												
												</td>
										</tr>
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message key="admissionForm.studentinfo.email.label"/></td>
											<td align="left" width="50%" class="row-even"><table width="100%" border="0" cellpadding="0" cellspacing="0">
												 <tr><td class="row-even"><nested:text property="applicantDetails.personalData.fatherEmail" name="onlineApplicationForm" styleClass="textbox" maxlength="50"></nested:text></td></tr>
												<tr><td>(e.g. name@yahoo.com)</td></tr>
                               			  		 </table>
											</td>
										</tr>	
														
						
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="knowledgepro.admission.motherName" /><span class="Mandatory">*</span>
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:select property="applicantDetails.titleOfMother" styleId='titleOfMother' name="onlineApplicationForm" styleClass="comboExtraLarge" style="max-width:15%;" onchange="motherIncomeMandatory()">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
								              	<html:option value="Mrs">Mrs.</html:option>
								              	<html:option value="Late">Late.</html:option>
											</nested:select>
											<nested:text property="applicantDetails.personalData.motherName" styleId="motherName"  name="onlineApplicationForm" styleClass="textboxsmall" maxlength="50"></nested:text>
											</td>
										</tr>
										
										
										<tr>
											<td align="left" width="50%" class="row-even">
												<bean:message key="knowledgepro.admission.education"/>
											</td>
											<td align="left" width="50%" class="row-even">
												<nested:text property="applicantDetails.personalData.motherEducation" name="onlineApplicationForm" styleClass="textbox" maxlength="50"></nested:text>
											</td>
										</tr>
										
										
										<tr>
											<td align="left" width="50%" class="row-even">
												<bean:message key="admissionForm.parentinfo.currency.label"/>
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:select property="applicantDetails.personalData.motherCurrencyId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="motherCurrency">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
													<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<nested:optionsCollection name="onlineApplicationForm" property="currencyMap" label="value" value="key"  />
											</nested:select>
											</td>
										</tr>
										
										
										<tr>
											<td align="left" width="50%" class="row-even">
											<div id="incomeMandatory1"><bean:message
												key="admissionFormForm.fatherIncome" /></div>
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:select property="applicantDetails.personalData.motherIncomeId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="motherIncomeRange">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection name="onlineApplicationForm" property="incomeList" label="incomeRange" value="id"/>
											</nested:select>
											</td>
										</tr>
										
										
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="knowledgepro.admin.occupation" />:
											</td>
											<td align="left" width="50%" class="row-even">
												  <nested:select property="applicantDetails.personalData.motherOccupationId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="motherOccupation" onchange="displayOtherForMother(this.value)">
													<html:option value="">- Select -</html:option>
													<html:optionsCollection name="onlineApplicationForm" property="occupations" label="occupationName" value="occupationId"/>
													<html:option value="other">Other</html:option>
												 </nested:select><br/>
												 <div id="displayMotherOccupation">
														&nbsp;<nested:text property="applicantDetails.personalData.otherOccupationMother" styleClass="textbox" name="onlineApplicationForm"  maxlength="50" styleId="otherOccupationMother"/>
												 </div>
											</td>
										</tr>
										
										
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="admissionForm.studentinfo.email.label" />
											</td>
											<td align="left" width="50%" class="row-even"><table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr><td class="row-even"><nested:text property="applicantDetails.personalData.motherEmail" styleId="motherEmail" styleClass="textbox" name="onlineApplicationForm"  maxlength="50"></nested:text></td></tr>
											<tr><td>(e.g. name@yahoo.com)</td></tr>
                                 			 </table>
											</td>
										</tr>					
						
						
						
				<tr>
					<td  class="row-even" align="right" colspan="2">
			      		<html:button property="" styleClass="buttons" value="Save Draft"  styleId="saveDraft8" onclick="submitSaveDraft('motherEmail'); return false;"></html:button>
					</td>
				</tr>		
			
			
				<!-- raghu student  parent details code close here -->	
				
						
						
				<!-- raghu student  parent background details code start here -->	
				
	<logic:equal value="true" property="displayFamilyBackground" name="onlineApplicationForm">					
						
		<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message key="knowledgepro.applicationform.brothername.label"/>
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:text property="applicantDetails.personalData.brotherName" name="onlineApplicationForm" styleClass="textbox" maxlength="100"></nested:text>
											</td>
										</tr>
										<tr >
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="knowledgepro.admission.education" />
											</td>
											<td align="left" width="50%" class="row-even">
												<nested:text property="applicantDetails.personalData.brotherEducation" name="onlineApplicationForm" styleClass="textbox" maxlength="100"></nested:text>
											</td>
										</tr>
										
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="knowledgepro.admission.income" />
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:text property="applicantDetails.personalData.brotherIncome" name="onlineApplicationForm" styleClass="textbox" maxlength="100"></nested:text>
											</td>
										</tr>
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message key="knowledgepro.admin.occupation" />:
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:text property="applicantDetails.personalData.brotherOccupation" name="onlineApplicationForm" styleClass="textbox" maxlength="100"></nested:text></td>
										</tr>
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message key="knowledgepro.applicationform.age.label"/>
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:text property="applicantDetails.personalData.brotherAge" styleId="brotherAge" name="onlineApplicationForm" styleClass="textbox" maxlength="50"></nested:text>
											</td>
										</tr>			
					
					
					<tr>
											<td align="left" width="50%" class="row-even">
											
											<div align="left"><bean:message key="knowledgepro.applicationform.sistername.label"/></div>
											</td>
											<td align="left" width="50%" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.sisterName" name="onlineApplicationForm" styleClass="textbox"  maxlength="100"></nested:text>
											</td>
										</tr>
										<tr>
											<td align="left" width="50%" class="row-even">
										      <bean:message
												key="knowledgepro.admission.education" />
											</td>
											<td align="left" width="50%" class="row-even" >
											<nested:text property="applicantDetails.personalData.sisterEducation" name="onlineApplicationForm" styleClass="textbox" maxlength="100"></nested:text>
											</td>
										</tr>
										
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="knowledgepro.admission.income" />
											</td>
											<td align="left" width="50%" class="row-even" >
											<nested:text property="applicantDetails.personalData.sisterIncome" name="onlineApplicationForm" styleClass="textbox"  maxlength="100"></nested:text>
											</td>
										</tr>
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="knowledgepro.admin.occupation" />:
											</td>
											<td align="left" width="50%" class="row-even">
												  <nested:text property="applicantDetails.personalData.sisterOccupation" name="onlineApplicationForm" styleClass="textbox" maxlength="100"></nested:text>
											</td>
										</tr>
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message key="knowledgepro.applicationform.age.label"/>
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:text property="applicantDetails.personalData.sisterAge" name="onlineApplicationForm" styleClass="textbox" maxlength="50"></nested:text>
											</td>
										</tr>
					
					
					
					
					
	</logic:equal>		
	
	
				<!-- raghu student  parent background details code close here -->	
		
					
				<!-- raghu student  parent adress code start here -->	
			
				<tr> 
					<td valign="top"  class="row-odd" colspan="2">
					<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
						<tr>
						  	<td class="row-odd" align="center" >
								<bean:message key="knowledgepro.admission.parentAddress"/>
							</td>
						</tr>
					</table>
					</td>
			 </tr>					
					
				<tr>
					<td align="left" width="50%" class="row-even">
					<bean:message
						key="admissionForm.studentinfo.addrs1.label" />
					</td>
					<td align="left" width="50%" class="row-even">
					<nested:text property="applicantDetails.personalData.parentAddressLine1" name="onlineApplicationForm" styleClass="textbox" maxlength="100" ></nested:text>
					</td>
				</tr>
										<tr >
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="admissionForm.studentinfo.addrs2.label" />
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:text property="applicantDetails.personalData.parentAddressLine2" name="onlineApplicationForm" styleClass="textbox" maxlength="100"></nested:text>
											</td>
										</tr>
										<tr>
											<td width="50%" align="left" class="row-even">
											<bean:message
												key="admissionForm.studentinfo.addrs3.label" />
											</td>
											<td width="50%" align="left" class="row-even"><nested:text property="applicantDetails.personalData.parentAddressLine3" styleClass="textbox" name="onlineApplicationForm"  maxlength="100"></nested:text>
											</td>
									</tr>
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="knowledgepro.admin.city" />:
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:text property="applicantDetails.personalData.parentCityName" name="onlineApplicationForm" styleClass="textbox" maxlength="30"></nested:text>
											</td>
									</tr>
									
										
										<tr>
											<td class="row-even" align="left" width="50%">
											<bean:message
												key="knowledgepro.admin.country" />
											</td>
											
											<td align="left" width="50%" class="row-even">
												<input type="hidden" id="hiddenParentCountryId" name="nationality" name="onlineApplicationForm" 
												   value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.parentCountryId"/>"/>
											<nested:select property="applicantDetails.personalData.parentCountryId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="parentCountryName" onchange="getParentAddrStates(this.value,'parentState')">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="onlineApplicationForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
											</nested:select>
											</td>
										</tr>
										<tr>
											<td width="50%" align="left" class="row-even">
													<bean:message key="knowledgepro.admin.state" />
													</td>
													<td width="50%" align="left" class="row-even">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
					                           		<tr><td class="row-even">
													<%String dynastyle=""; %>
													<logic:equal value="Other" property="applicantDetails.personalData.parentStateId" name="onlineApplicationForm">
															<%dynastyle="display:block;"; %>
														</logic:equal>
														<logic:notEqual value="Other" property="applicantDetails.personalData.parentStateId" name="onlineApplicationForm">
															<%dynastyle="display:none;"; %>
														</logic:notEqual>
					                  					<nested:select property="applicantDetails.personalData.parentStateId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="parentState" onchange="funcOtherShowHide('parentState','otherParentAddrState');">
															<html:option value="">- Select -</html:option>
															<logic:notEmpty property="parentStateMap" name="onlineApplicationForm">
																		<html:optionsCollection name="onlineApplicationForm" property="parentStateMap" label="value" value="key" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select></td></tr>
														<tr><td class="row-even"><nested:text property="applicantDetails.personalData.parentAddressStateOthers" styleClass="textbox" name="onlineApplicationForm" maxlength="30" styleId="otherParentAddrState" style="<%=dynastyle %>"></nested:text></td></tr>
														</table>
											</td>
								</tr>
										<tr>
											<td align="left" width="50%" class="row-even">
											<bean:message
												key="knowledgepro.admission.zipCode" />
											</td>
											<td align="left" width="50%" class="row-even">
											<nested:text property="applicantDetails.personalData.parentAddressZipCode" name="onlineApplicationForm" styleClass="textbox" maxlength="10"></nested:text>
											</td>
										</tr>
										<tr>
											<td class="row-even">
			                            	 <table width="100%" border="0" cellpadding="0" cellspacing="0">
			                            	  <tr>
			                             		 <td  class=row-even><div align="left"><bean:message key="admissionForm.studentinfo.phone.label"/></div></td>
			                             		  <td  class="row-even">
			                             		 	<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr><td align="right" height="25" class="row-even">
																
																<div id="hideParentMandatorySymbols"><span class="Mandatory">*</span>
																<bean:message key="admissionForm.phone.cntcode.label"/></div>
																</td></tr>
																<tr><td align="right" height="25" class="row-even">
																<div id="hideParentMandatorySymbols1"><span class="Mandatory">*</span>
																<bean:message key="admissionForm.phone.areacode.label"/></div>
																
																</td></tr>
																<tr><td align="right" height="25" class="row-even">
																<div id="hideParentMandatorySymbols2"><span class="Mandatory">*</span>
																<bean:message key="admissionForm.phone.main.label"/></div>
																
																</td></tr>
													</table>
												</td>
											  </tr>
											 </table>
											</td>
											
											<td align="left" width="50%" class="row-even">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr><td class="row-even"><nested:text property="applicantDetails.personalData.parentPh1" styleClass="textbox" styleId="personalDataParentPh1" name="onlineApplicationForm"  maxlength="4" onkeypress="return isNumberKey(event)" onkeydown="hideGuardenPhoneNumber()"  ></nested:text></td></tr>
													<tr><td class="row-even"><nested:text property="applicantDetails.personalData.parentPh2" styleClass="textbox" styleId="personalDataParentPh2" name="onlineApplicationForm"  maxlength="7" onkeypress="return isNumberKey(event)" onkeydown="hideGuardenPhoneNumber()" ></nested:text></td></tr>
													<tr><td class="row-even"><nested:text property="applicantDetails.personalData.parentPh3" styleId="parentPh3"  styleClass="textbox" name="onlineApplicationForm"  maxlength="10" onkeypress="return isNumberKey(event)" onkeydown="hideGuardenPhoneNumber()"  ></nested:text></td></tr>
												</table>
											</td>
										</tr>
										
								
										<tr>
											
											<td class="row-even" width="50%">
			                            	 <table width="100%" border="0" cellpadding="0" cellspacing="0">
			                            	  <tr>
			                             		 <td class="row-even">
													<bean:message key="admissionForm.studentinfo.mobile.label"/>
			                             		 </td>
			                             		  <td class="row-even">
			                             		 	<table width="100%" border="0" cellpadding="0" cellspacing="0">
																<tr><td align="right" height="25" class="row-even">
																 <div id="hideParentMandatorySymbols3"><span class="Mandatory">*</span>
																<bean:message key="admissionForm.phone.cntcode.label"/></div>
																
																</td></tr>
																<tr>
																<td align="right" height="25" class="row-even">
																<div id="hideParentMandatorySymbols4"><span class="Mandatory">*</span>
																<bean:message key="admissionForm.mob.no.label"/></div>
																</td></tr>
												</table>
												</td>
											  </tr>
											 </table>
											</td>
											
											
											<td width="50%" align="left" class="row-even">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
				                              	 <tr><td class="row-even"><nested:text styleClass="textbox" property="applicantDetails.personalData.parentMob1" styleId="personalDataParentMob1" name="onlineApplicationForm"  maxlength="4" onkeypress="return isNumberKey(event)" onkeydown="hideGuardenPhoneNumber()"  ></nested:text></td></tr>
				                                  <tr><td class="row-even"><nested:text styleClass="textbox" property="applicantDetails.personalData.parentMob2"  styleId="parentMob2" name="onlineApplicationForm"  maxlength="10" onkeypress="return isNumberKey(event)" onkeydown="hideGuardenPhoneNumber()"  ></nested:text></td></tr>
					</table>

											</td>
										</tr>
										
										
										<tr>
									<td  class="row-even" align="right" colspan="2">
							      		<html:button property="" styleClass="buttons" value="Save Draft"  styleId="saveDraft8" onclick="submitSaveDraft('motherEmail'); return false;"></html:button>
									</td>
								</tr>					
					
					
					
					<!-- raghu student  parent adress code close here -->	
			
					
					
					<!-- raghu student  guardian adress code start here -->	
			
	<tr> 
					<td valign="top"  class="row-odd" colspan="2">
					<table width="100%" border="0" align="center" cellpadding="1" cellspacing="2">
						<tr>
						  	<td class="row-odd" align="center" >
								<bean:message key="knowledgepro.applicationform.guardianaddr.label"/>
							</td>
						</tr>
					</table>
					</td>
			 </tr>					
					
					
					
										<tr>
											<td width="50%" class="row-even" align="left">
											<bean:message key="knowledgepro.applicationform.guardianname.label" /></td>
											<td width="50%" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.guardianName" styleId="guardianName" name="onlineApplicationForm" styleClass="textbox"  maxlength="100" ></nested:text>
											</td>
										</tr>
										<tr>
											<td width="50%" class="row-even" align="left">
											<div align="left"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="50%" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.guardianAddressLine1" styleClass="textbox" name="onlineApplicationForm"  maxlength="100" ></nested:text>
											</td>
										</tr>
										<tr>
										<td width="50%" class="row-even" align="left">
											<div align="left"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td width="50%" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.guardianAddressLine2" styleClass="textbox" name="onlineApplicationForm"  maxlength="100"></nested:text>
											</td>
										</tr>
										<tr>
											<td width="50%" class="row-even" align="left">
											<div align="left"><bean:message
												key="admissionForm.studentinfo.addrs3.label" /></div>
											</td>
											<td width="50%" class="row-even" align="left"><nested:text styleClass="textbox" property="applicantDetails.personalData.guardianAddressLine3" name="onlineApplicationForm"  maxlength="100"></nested:text>
											</td>
									</tr>
										<tr>
											<td width="50%" class="row-even" align="left">
											<bean:message key="knowledgepro.admin.city" />:
											</td>
											<td width="50%" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.cityByGuardianAddressCityId" styleClass="textbox" name="onlineApplicationForm"  maxlength="30"></nested:text>
											</td>
										</tr>
										
										<tr>
											
											<td width="50%" class="row-even" align="left">
											<bean:message key="knowledgepro.admin.country" />
											
											</td>
											<td width="50%" class="row-even" align="left">
											<input type="hidden" id="hiddenCountryByGuardianAddressCountryId" name="nationality" name="onlineApplicationForm" 
												   value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.countryByGuardianAddressCountryId"/>"/>
											<nested:select property="applicantDetails.personalData.countryByGuardianAddressCountryId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="guardianCountryName" onchange="getGuardianAddrStates(this.value,'guardianState')">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
													<%String selected=""; %>
													<logic:iterate id="option" property="countries" name="onlineApplicationForm">
														<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
														<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
														<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
													</logic:iterate>
											</nested:select>
											</td>
										</tr>
									<tr>
											<td width="50%" class="row-even" align="left">
											<div align="left"><bean:message key="knowledgepro.admin.state" /></div>
											</td>
											<td width="50%" class="row-even" align="left">
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
				                            <tr><td class="row-even">
											<%String dynaStyle1=""; %>
													<logic:equal value="Other" property="applicantDetails.personalData.stateByGuardianAddressStateId" name="onlineApplicationForm">
														<%dynaStyle1="display:block;"; %>
													</logic:equal>
													<logic:notEqual value="Other" property="applicantDetails.personalData.stateByGuardianAddressStateId" name="onlineApplicationForm">
														<%dynaStyle1="display:none;"; %>
													</logic:notEqual>
				                  			<nested:select property="applicantDetails.personalData.stateByGuardianAddressStateId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="guardianState" onchange="funcGuardianOtherShowHide('guardianState','otherGuardianAddrState');">
													<html:option value="">- Select -</html:option>
													<logic:notEmpty property="guardianStateMap" name="onlineApplicationForm">
																<html:optionsCollection name="onlineApplicationForm" property="guardianStateMap" label="value" value="key" />
													</logic:notEmpty>
													<html:option value="Other">Other</html:option>
											</nested:select></td></tr>
											<tr><td class="row-even"><nested:text property="applicantDetails.personalData.guardianAddressStateOthers" styleClass="textbox" name="onlineApplicationForm"  maxlength="30" styleId="otherGuardianAddrState" style="<%=dynaStyle1 %>"></nested:text></td></tr>
											</table>
															</td>
															
										</tr>
										<tr>
											<td width="50%" class="row-even" align="left">
												<bean:message key="knowledgepro.admission.zipCode" />
											</td>
											<td width="50%" class="row-even" align="left">
											<nested:text property="applicantDetails.personalData.guardianAddressZipCode" styleClass="textbox" name="onlineApplicationForm"  maxlength="10"></nested:text>
											</td>
										</tr>
										<tr>
											<td class="row-even">
			                            	 <table width="100%" border="0" cellpadding="0" cellspacing="0">
			                            	  <tr>
			                             		 <td class="row-even"><div align="left"><bean:message key="admissionForm.studentinfo.phone.label"/></div></td>
			                             		  <td  class="row-even">
			                             		 	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
																			<tr>
																	<td align="right" height="25" class="row-even">
																	
																	<div id="hideGuardenMandatorySymbols"><span class="Mandatory">*</span> <bean:message key="admissionForm.phone.cntcode.label"/> </div>
																	</td>
																</tr>
																<tr><td align="right" height="25" class="row-even">
																<div id="hideGuardenMandatorySymbols1"><span class="Mandatory">*</span> <bean:message key="admissionForm.phone.areacode.label"/></div></td></tr>
																<tr><td align="right" height="25" class="row-even">
																<div id="hideGuardenMandatorySymbols2"><span class="Mandatory">*</span> <bean:message key="admissionForm.phone.main.label"/></div></td></tr>
										</table>
												</td>
											  </tr>
											 </table>
											</td>
											<td width="50%" class="row-even" align="left">
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr><td class="row-even"><nested:text styleClass="textbox" property="applicantDetails.personalData.guardianPh1" styleId="personalDataGuardianPh1" name="onlineApplicationForm"  maxlength="4" onkeypress="return isNumberKey(event)" onkeydown="hideParentPhoneNumber()"  ></nested:text></td></tr>
													<tr><td class="row-even"><nested:text styleClass="textbox" property="applicantDetails.personalData.guardianPh2" styleId="personalDataGuardianPh2" name="onlineApplicationForm"  maxlength="7" onkeypress="return isNumberKey(event)" onkeydown="hideParentPhoneNumber()"  ></nested:text></td></tr>
													<tr><td class="row-even"><nested:text styleClass="textbox" property="applicantDetails.personalData.guardianPh3" styleId="guardianPh3" name="onlineApplicationForm"  maxlength="10" onkeypress="return isNumberKey(event)" onkeydown="hideParentPhoneNumber()"  ></nested:text></td></tr>
												</table>

											</td>
										</tr>
										<tr>
											
											<td class="row-even">
			                            	 <table width="100%" border="0" cellpadding="0" cellspacing="0">
			                            	  <tr>
			                             		 <td height="20" class="row-even"><div align="left"><bean:message key="admissionForm.studentinfo.mobile.label"/></div></td>
			                             		  <td height="20" class="row-even">
			                             		 	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
																<tr><td align="right" height="25" class="row-even">
																<div id="hideGuardenMandatorySymbols3"><span class="Mandatory">*</span> <bean:message key="admissionForm.phone.cntcode.label"/></div></td></tr>
																<tr><td align="right" height="25" class="row-even">
																<div id="hideGuardenMandatorySymbols4"><span class="Mandatory">*</span> <bean:message key="admissionForm.mob.no.label"/></div></td></tr>
												</table>
												</td>
											  </tr>
											 </table>
											</td>
															<td  align="left" class="row-even">
																<table width="100%" border="0" cellpadding="0" cellspacing="0">
								                              	  <tr>
								                              	   <td class="row-even"><nested:text property="applicantDetails.personalData.guardianMob1" styleId="guardianMob1" styleClass="textbox" name="onlineApplicationForm" maxlength="4" onkeypress="return isNumberKey(event)" onkeydown="hideParentPhoneNumber()"  ></nested:text>
								                              	  </td></tr>
								                                  <tr>
								                                  <td class="row-even"><nested:text property="applicantDetails.personalData.guardianMob2" styleId="guardianMob2" styleClass="textbox" name="onlineApplicationForm"  maxlength="10" onkeypress="return isNumberKey(event)" onkeydown="hideParentPhoneNumber()"  ></nested:text>
								                             </td></tr>
															</table>
															</td>
										</tr>
										
								<tr>
									<td  class="row-even" align="right" colspan="2">
							      		<html:button property="" styleClass="buttons" value="Save Draft"  styleId="saveDraft8" onclick="submitSaveDraft('motherEmail'); return false;"></html:button>
									</td>
								</tr>	
					
					
				
				<!-- raghu student  guardian adress code close here -->	
			
			
			<!-- raghu student  know about institution code start here -->	
			
					
							<tr>
                              <td class="row-even" align="left" width="50%">How did you know about this institution?<span class="Mandatory">*</span></td>

						
                              <td class="row-even" align="left" width="50%">
                              <input type="hidden" id="tempApplicantFeedbackId" value="<nested:write name="onlineApplicationForm" property="applicantDetails.applicantFeedbackId"/>">
							<nested:select property="applicantDetails.applicantFeedbackId" name="onlineApplicationForm" styleClass="comboExtraLarge" styleId="applicantFeedbackId">
								<option value=""><bean:message key="knowledgepro.admin.select"/></option>
								<logic:notEmpty name="onlineApplicationForm" property="applicantFeedbackList">
								<nested:optionsCollection  name="onlineApplicationForm" property="applicantFeedbackList" label="name" value="id"/>
								</logic:notEmpty>
							</nested:select>

			                    </td>
                            </tr>		
						
						
					<!-- raghu student  know about institution code over here -->	
				
						
						
						<!-- raghu student  attachments code start here -->	
			
						
						<tr>
							<td colspan="2" align="left"><span class="heading">&nbsp;<bean:message
								key="knowledgepro.admission.documents" /></span></td>
						</tr>					
						
						
						
						
		
										<tr>
											
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.documents" /></td>
											<td class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.uploadDocs" /></td>
											<logic:equal value="true" property="reviewWarned" name="onlineApplicationForm">
											<td class="row-odd" width="15%" align="center">&nbsp;</td>
											</logic:equal>
										</tr>
										
										<c:set var="temp" value="0" />
								<nested:iterate name="onlineApplicationForm" property="applicantDetails.editDocuments" indexId="count" id="docList" type="com.kp.cms.to.admin.ApplnDocTO" >
											<c:choose>
												<c:when test="${temp == 0}">
										<nested:equal value="true" property="photo" name="docList">
										<tr>
										<td height="25"  class="row-even"  colspan="2">
										<table>
											<tr>
														
														<td height="25" width="25%" class="row-even" align="center"><nested:write name="docList" property="printName" /></td>
														<logic:equal value="true" property="reviewWarned" name="onlineApplicationForm">
															<td width="15%" class="row-even" align="center">
															<nested:equal
																value="false" property="photo" name="docList">
															<nested:equal
																value="true" property="documentPresent" name="docList">
																<a
																	href="javascript:downloadFile('<nested:write name="docList" property="docTypeId"/>')"><bean:message key="knowledgepro.view.image" /></a>
															</nested:equal>
															</nested:equal>
															</td>
														</logic:equal>

														<td  class="row-even" width="60%">
														<nested:equal value="true" property="photo" name="docList">
															<nested:file property="editDocument" ></nested:file>
															</nested:equal>
															<nested:equal
																value="true" property="documentPresent" name="docList">
																<a
																	href="javascript:downloadFile('<nested:write name="docList" property="id"/>')"><bean:message key="knowledgepro.view.image" /></a>
															</nested:equal>
														</td>
													</tr>
										
										
										
										</table>
										</td>
										</tr>
										
										
													
													
													
													
													
													<tr>
													<td height="25"  class="row-even" align="left" colspan="2" style="font-style: italic;">(35mm x 45mm white background photograph in JPEG format less than 100kb)
													</td>
													</tr>
											</nested:equal>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
												<nested:equal value="true" property="photo" name="docList">
												
												<tr>
										<td height="25"  class="row-even"  colspan="2">
										<table>
												<tr>
	
														<td height="25"  align="center"><nested:write name="docList"
															property="printName" /></td>
														<logic:equal value="true" property="reviewWarned" name="onlineApplicationForm">
															<td width="15%"  align="center">
															<nested:equal
																value="false" property="photo" name="docList">
															<nested:equal
																value="true" property="documentPresent" name="docList">
																<a
																	href="javascript:downloadFile('<nested:write name="docList" property="docTypeId"/>')"><bean:message key="knowledgepro.view.image" /></a>
															</nested:equal>
															</nested:equal>
															</td>
														</logic:equal>

													
														<td  width="60%">
															<span class="row-even"> <nested:equal value="true" property="photo" name="docList">
															<nested:file property="editDocument"  ></nested:file>
															</nested:equal></span>
															<nested:equal
																value="true" property="documentPresent" name="docList">
																<a
																	href="javascript:downloadFile('<nested:write name="docList" property="id"/>')"><bean:message key="knowledgepro.view.image" /></a>
															</nested:equal>
														</td>
													</tr>
												
												
											</table>
											</td>
											</tr>	
													
													<tr>
													<td height="25"  class="row-even" align="left" colspan="2" style="font-style: italic;">(35mm x 45mm white background photograph in JPEG format less than 100kb)
													</td>
													</tr>
												</nested:equal>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</nested:iterate>	
						
						
						<!-- raghu student  attachments code over here -->	
			
						
										
					</table>
				</td>
			<td background="images/right.gif" width="5"></td>
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
					
					
	<!-- raghu main information code close here -->				
					
					
					
					
						
		
						<tr>
							<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="48%" height="21">
									<div align="right">
									<html:button property="" styleClass="buttons" styleId="SubmitDetailPage"  > <bean:message key="knowledgepro.submit" />
									</html:button></div>
		
									</td>
									<!--<td width="7%"><div align="center"><html:button property="" styleClass="formbutton"
										onclick="submitAdmissionForm('initApplicantCreationDetail')">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button></div></td>
									--><td width="45%" height="45" align="left"><html:button
										property="" styleClass="buttons"
										onclick="cancel()">
										<bean:message key="knowledgepro.cancel" />
									</html:button></td>
								</tr>
							</table>
					</td>
				</tr>
	
		
						
					</table>
					
					</logic:equal>
					<!--  main things closed here -->
					
					
					
					
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
				
			</table>
			</div>
			
			</td>
		</tr>
	</table>
	
	
	
	<script language="JavaScript" src="js/admission/OnlineDetailsAppCreation.js"></script>
<script type="text/javascript">

	onLoadAddrCheck();
	var relgId = document.getElementById("religionType").value;
	if(relgId != null && relgId.length != 0) {
		document.getElementById("religions").value = relgId;
	}
	var sameAddr= document.getElementById("sameAddr").checked;

	if(sameAddr==false){
		var permanentCountryNamehidden = document.getElementById("permanentCountryNamehidden").value;
		if(permanentCountryNamehidden != null && permanentCountryNamehidden.length != 0) {
			document.getElementById("permanentCountryName").value = permanentCountryNamehidden;
		}	
	}
	var currentCountryNamehidden = document.getElementById("currentCountryNamehidden").value;
	if(currentCountryNamehidden != null && currentCountryNamehidden.length != 0) {
		document.getElementById("currentCountryName").value = currentCountryNamehidden;
	}
	
	var nationality = document.getElementById("nationalityhidden").value;
	if(nationality != null && nationality.length != 0) {
		document.getElementById("nationality").value = nationality;
	}
	var birthCountryhidden = document.getElementById("birthCountryhidden").value;
	if(birthCountryhidden != null && birthCountryhidden.length != 0) {
		document.getElementById("birthCountry").value = birthCountryhidden;
	}
	var showHandi = document.getElementById("hiddenHandicaped").value;
	if(showHandi != null && showHandi.length != 0 && showHandi=='true') {
		showHandicappedDescription();	
	}else{
		hideHandicappedDescription();
	}
	var showSport = document.getElementById("hiddenSportsPerson").value;
	if(showSport != null && showSport.length != 0 && showSport=='true') {
		showSportsDescription();	
	}else{
		hideSportsDescription();
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

	var confirm = "<c:out value='${onlineApplicationForm.reviewWarned}'/>";
	if(confirm.length != 0 && confirm == "true") {
		alert("WARNING:: Please verify the application form details before submission.");
	}



	
	if(document.getElementById("birthCountry").value==""){
	setTimeout("getStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','birthState')",2000);
	}
	if(document.getElementById("permanentCountryName").value==""){
	setTimeout("getPermAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','permAddrstate')",4000);
	}
	if(document.getElementById("currentCountryName").value==""){
	setTimeout("getTempAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','tempAddrstate')",6000);
	}
	if(document.getElementById("parentCountryName").value==""){
		setTimeout("getParentAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','parentState')",8000);
	}
	if(document.getElementById("guardianCountryName").value==""){
		setTimeout("getGuardianAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','guardianState')",9000);
	}


	
	if(document.getElementById("motherOccupation").value=='' || document.getElementById("motherOccupation").value== null){
		
		document.getElementById("displayMotherOccupation").style.display = "none";
	}else{
		if(document.getElementById("motherOccupation").value=='other'){
			document.getElementById("displayMotherOccupation").style.display = "block";
		}else{
			document.getElementById("displayMotherOccupation").style.display = "none";
		}
		
	}
	
	if(document.getElementById("hiddenFatherOccupationId").value==null  || document.getElementById("hiddenFatherOccupationId").value==''){
		document.getElementById("displayFatherOccupation").style.display = "none";
	}else{
		if(document.getElementById("hiddenFatherOccupationId").value=='other'){
			document.getElementById("displayFatherOccupation").style.display = "block";
		}else{
			document.getElementById("displayFatherOccupation").style.display = "none";
		}
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
	function fatherIncomeMandatory(){
		var fatherTitle = document.getElementById("titleOfFather").value;
		if(fatherTitle == "Mr"){
			document.getElementById("incomeMandatory").innerHTML = "<span class='Mandatory'>*</span> Income per annum:";
		}else{
			document.getElementById("incomeMandatory").innerHTML = " Income per annum:";
		}
	}
	var fatherTitle = document.getElementById("titleOfFather").value;
	if(fatherTitle !='' && fatherTitle == "Mr"){
		document.getElementById("incomeMandatory").innerHTML = "<span class='Mandatory'>*</span> Income per annum:";
	}else{
		document.getElementById("incomeMandatory").innerHTML = " Income per annum:";
	}
	function motherIncomeMandatory(){
		var titleOfMother = document.getElementById("titleOfMother").value;
		if(titleOfMother == "Mrs"){
			document.getElementById("incomeMandatory1").innerHTML = "<span class='Mandatory'>*</span> Income per annum:";
		}else{
			document.getElementById("incomeMandatory1").innerHTML = " Income per annum:";
		}
	}
	var titleOfMother = document.getElementById("titleOfMother").value;
	if(titleOfMother !='' && titleOfMother == "Mrs"){
		document.getElementById("incomeMandatory1").innerHTML = "<span class='Mandatory'>*</span> Income per annum:";
	}else{
		document.getElementById("incomeMandatory1").innerHTML = " Income per annum:";
	}
	var bState =  document.getElementById("birthState1").value;
	if(bState!=null && bState.length != 0){
		document.getElementById("birthState").value=bState;
	}
	var tempAppFeedbackId =  document.getElementById("tempApplicantFeedbackId").value;
	if(tempAppFeedbackId !=null && tempAppFeedbackId.length!=0){
		document.getElementById("applicantFeedbackId").value=tempAppFeedbackId;
	}
	//var tempCategoryId = document.getElementById("tempResidentCategory").value;
	//alert(tempCategoryId);
	//if(tempCategoryId!=null && tempCategoryId.length!=0){
	//	alert("Inside");
	//	document.getElementById("residentCategory").value=tempCategoryId;
	//}
	$(document).ready(function() {	
		var otherBirthState =  document.getElementById("otherBirthState").value;
		
		if(otherBirthState!=''){
			document.getElementById("birthState").value='Other';
			$("#otherBirthState").show();
			
		}
	
		var otherTempAddrState =  document.getElementById("otherTempAddrState").value;
		
		if(otherTempAddrState!=''){
			document.getElementById("tempAddrstate").value='Other';
			$("#otherTempAddrState").show();
			
		}

	var otherPermAddrState =  document.getElementById("otherPermAddrState").value;
		
		if(otherPermAddrState!=''){
			document.getElementById("permAddrstate").value='Other';
			$("#otherPermAddrState").show();
			
		}

		var hiddenParentCountryId = document.getElementById("hiddenParentCountryId").value;
		if(hiddenParentCountryId != null && hiddenParentCountryId.length != 0) {
			document.getElementById("parentCountryName").value = hiddenParentCountryId;
		}

		var otherParentAddrState =  document.getElementById("otherParentAddrState").value;
		if(otherParentAddrState!=''){
			document.getElementById("parentState").value='Other';
			$("#otherParentAddrState").show();
			
		}

		var hiddenCountryByGuardianAddressCountryId = document.getElementById("hiddenCountryByGuardianAddressCountryId").value;
		if(hiddenCountryByGuardianAddressCountryId != null && hiddenCountryByGuardianAddressCountryId != 0) {
			document.getElementById("guardianCountryName").value = hiddenCountryByGuardianAddressCountryId;
		}

		var otherGuardianAddrState =  document.getElementById("otherGuardianAddrState").value;
		if(otherGuardianAddrState!=''){
			document.getElementById("guardianState").value='Other';
			$("#otherGuardianAddrState").show();
			
		}

		var hiddenpassportCountry = document.getElementById("hiddenpassportCountry").value;
		if(hiddenpassportCountry != null && hiddenpassportCountry != 0) {
			document.getElementById("passportCountry").value = hiddenpassportCountry;
		}

		
	});
	
	var savedDraftAlertMsg = document.getElementById("savedDraftAlertMsg").value;
	if(savedDraftAlertMsg!='' && savedDraftAlertMsg =='true'){

		$.confirm({
			'message'	: 'Applications saved as draft. Please complete the application within 24 hours.',
			'buttons'	: {
				'Ok'	: {
					'class'	: 'blue',
					'action': function(){
						$.confirm.hide();
						
					}
				}
			}
		});
	}
</script>