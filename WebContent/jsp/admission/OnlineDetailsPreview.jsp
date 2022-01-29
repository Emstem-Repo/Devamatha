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
<script type="text/javascript" src="jquery/collaps/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
 

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
 
 
 
<style type="text/css">
	
		.tooltip{
   			display: inline;
    		position: relative;
		}
		
		.tooltip:hover:after{
    		background: #333;
    		background: rgba(0,0,0,.8);
    		border-radius: 5px;
    		bottom: 26px;
    		color: #fff;
    		content: attr(title);
    		left: 20%;
    		padding: 5px 15px;
    		position: absolute;
    		z-index: 98;
    		width: 220px;
		}
		
		.tooltip:hover:before{
    		border: solid;
    		border-color: #333 transparent;
    		border-width: 6px 6px 0 6px;
    		bottom: 20px;
    		content: "";
    		left: 50%;
    		position: absolute;
    		z-index: 99;
		}
	
	</style>
	
 <!-- for cache controling with html code-->
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
 
 <!-- for cache controling with jsp code-->
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>

<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
<html:hidden property="programId" styleId="programId" name="onlineApplicationForm"/>
<html:hidden property="programYear" styleId="programYear" name="onlineApplicationForm"/>
<html:hidden property="courseId" styleId="courseId" name="onlineApplicationForm"/>
<html:hidden property="focusValue" styleId="focusValue" name="onlineApplicationForm"/>
<html:hidden property="onlineApply" styleId="onlineApply" name="onlineApplicationForm"/>
<html:hidden property="pageType" value="18" />
<html:hidden property="indianCandidate" styleId="indianCandidate" name="onlineApplicationForm"/>

 			
	<html:hidden property="singlePageAppln" value="true"/>
 	<html:hidden property="selectedAppNo" value="" />
	<html:hidden property="selectedYear" value="" />
	<html:hidden property="detailsView" value="false" />
	<html:hidden property="pageType" value="18" />
	<html:hidden property="reviewed" styleId="reviewed" name="onlineApplicationForm"/>
	<input type="hidden" name="courseId" id="courseId" value='<bean:write	name="onlineApplicationForm" property="applicantDetails.course.id" />' />
	<html:hidden property="checkReligionId" styleId="checkReligionId" />
 	<html:hidden property="secondLanguage" styleId="secondLanguage" />
	 			
 			
 		
<table width="80%" style="background-color: #F0F8FF" align="center">
 
   
	
	
	
	
  <tr ><td height="30"></td></tr>
	
	
	 <tr>
    <td width="100%"><table align="center" width="40%" border="0" style="border-collapse:collapse">
      <tr>
        <td height="23" align="center" class="subheading">Course Preferences</td>
      </tr>
    </table></td>
  </tr>
  
	
	 <tr>
        <td><div align="center">
        <table width="100%" border="0" cellpadding="7"  align="center" class="subtable"  >
        
        <nested:iterate id="admissionpreference" name="onlineApplicationForm" property="prefcourses" indexId="count">
	
          <tr>
            <td width="40%" align="right"><span class="Mandatory">*</span>
            <bean:write name="admissionpreference" property="prefName"></bean:write>:
            </td>
             <td width="60%">
                    <nested:select  disabled="true" property="id" styleClass="dropdown"  styleId="coursePreference1" >
					
					<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					<html:optionsCollection name="onlineApplicationForm" property="courseMap" label="value" value="key"/>	
					</nested:select>
				
            </td>
			
		 </tr>
		
		 <logic:equal name="onlineApplicationForm" property="courseId" value="18">
   <tr >
   <td colspan="2" align="center">
   <table width="100%" style="display: block;" id="corecourse">
   <tr>
   <td align="center">
   			<fieldset style="border: 0px">
              <span class="Mandatory">*</span>No of English Core Courses: 
             <nested:radio disabled="true" property="applicantDetails.personalData.noofenglishCoreCourses" styleId="Less than 14" name="onlineApplicationForm" value="Less than 14"></nested:radio>
             <label for="Less than 14"><span><span></span></span>Less than 14</label> 
			
			
			 <nested:radio disabled="true" property="applicantDetails.personalData.noofenglishCoreCourses" name="onlineApplicationForm" styleId="14 or more" value="14 or more"></nested:radio>
			 <label for="14 or more"><span><span></span></span>14 or more</label> 
			 <a href="#" title="Choose no of English Core Courses" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
			 </fieldset>	
   </td>
   </tr>
   </table>
   			
   
   </td>
   </tr>
   </logic:equal>
   
		</nested:iterate> 
		 
		
        </table>
        </div>	
        </td>
      </tr>
      
      
      
      <tr ><td height="30"></td></tr>
      
	
	
   <tr>
  
  
    <td width="100%"><table align="center" width="100%" border="0" style="border-collapse:collapse">
      <tr>
        <td height="30" align="center" class="subheading">Personal Details</td>
      </tr>
    </table></td>
  </tr>
  
  

      <tr>
        <td>
        <table  width="100%" border="0" cellpadding="4"  align="center" class="subtable"  >
         
         
          <tr>
           
            <td width="25%" align="right"><bean:message key="knowledgepro.applicationform.candidateName"/><span class="Mandatory">*</span>
            <br/>(as printed on CLASS 10(SSLC) Records)
            </td>
            <td width="25%" valign="top">
            <nested:text readonly="true" property="applicantDetails.personalData.firstName" styleId="firstNameId" name="onlineApplicationForm"  maxlength="90" styleClass="textbox"></nested:text>						
            
            <a href="#" title="Enter Your Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
             </td>
           
             
            <td width="25%" align="right"><bean:message key="admissionForm.studentinfo.bloodgroup.label"/><span class="Mandatory">*</span>
           
             </td>
            <td width="25%">
			<input type="hidden" id="BGType" name="BGType" value='<bean:write name="onlineApplicationForm" property="bloodGroup"/>'/>
                         <nested:select disabled="true" property="applicantDetails.personalData.bloodGroup" name="onlineApplicationForm" styleClass="dropdown" styleId="bgType">
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
			 <a href="#" title="Select Your BloodGroup" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            			
			</td>
			
          </tr>
          
         
          
          <tr>
          
            <td align="right">
            <bean:message key="admissionForm.studentinfo.dob.label"/>
            <bean:message key="admissionForm.application.dateformat.label"/><span class="Mandatory">*</span>
            </td>
            
            <td>
             <nested:text readonly="true" name="onlineApplicationForm" property="applicantDetails.personalData.dob" styleId="dateOfBirth"  maxlength="11" styleClass="textbox"></nested:text>
							
              <a href="#" title="Select Your Date of Birth" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            </td>
            
            
             <td align="right"><bean:message key="admissionForm.studentinfo.residentcatg.label"/><span class="Mandatory">*</span>
             
             </td>
            <td><div align="left">
            <input type="hidden" id="tempResidentCategory" value="<nested:write property="applicantDetails.personalData.residentCategory" name="onlineApplicationForm" />">
			<nested:select disabled="true" property="applicantDetails.personalData.residentCategory" name="onlineApplicationForm" styleClass="dropdown" styleId="residentCategory" >
			<option value=""><bean:message key="knowledgepro.admin.select"/></option>
			<nested:optionsCollection name="onlineApplicationForm" property="residentTypes" label="name" value="id"/>
			</nested:select>
			<a href="#" title="Select Your Residentcategory" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>
            </td>
			
			
          </tr>
          
          
      
		
          
          <tr>
            
            
            <td align="right"><bean:message key="admissionForm.studentinfo.sex.label"/><span class="Mandatory">*</span></td>
            <td>
             
                <nested:radio disabled="true" property="applicantDetails.personalData.gender" styleId="MALE" name="onlineApplicationForm" value="MALE"></nested:radio><bean:message key="admissionForm.studentinfo.sex.male.text"/>
				
				<nested:radio disabled="true" property="applicantDetails.personalData.gender" name="onlineApplicationForm" styleId="FEMALE" value="FEMALE"><bean:message key="admissionForm.studentinfo.sex.female.text"/></nested:radio>
				
				<nested:radio disabled="true" property="applicantDetails.personalData.gender" name="onlineApplicationForm" styleId="Trans Gender" value="TRANS GENDER">TransGender</nested:radio>
				<a href="#" title="Select Your Gender" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
            
            
             <td align="right"><bean:message key="admissionForm.studentinfo.nationality.label"/><span class="Mandatory">*</span></td>
            <td>
            <div align="left">
            <input type="hidden" id="nationalityhidden" name="nationality" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.nationality"/>"/>
                          
            <nested:select disabled="true" property="applicantDetails.personalData.nationality" styleClass="dropdown" styleId="nationality" name="onlineApplicationForm">
			<option value=""><bean:message key="knowledgepro.admin.select"/></option>
			<%String selected=""; %>
			<logic:iterate id="option" property="nationalities" name="onlineApplicationForm">
			<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_NATIONALITY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
			<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_NATIONALITY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
			<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
			</logic:iterate>
			</nested:select>
			 <a href="#" title="Select Your Nationality" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>
            </td>
            
			</tr>
            
            
            
          
          
          
          <tr>
          
          
          
          <td align="right"><bean:message key="admissionForm.studentinfo.phone.label"/> </td>
            <td>
            
            <nested:text readonly="true" styleClass="textboxsmall" property="applicantDetails.personalData.phNo2" name="onlineApplicationForm"  maxlength="7" size="7" onkeypress="return isNumberKey(event)" />
			
           <nested:text readonly="true" styleClass="textboxmedium" property="applicantDetails.personalData.phNo3" name="onlineApplicationForm" styleId="applicantphNo3" maxlength="10" size="10" onkeypress="return isNumberKey(event)"/>
			 <a href="#" title="Enter Your Phone Code and Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            </td>
          
          
           
            <td align="right">Birth <bean:message key="admissionForm.studentinfo.birthdetails.country.label"/><span class="Mandatory">*</span></td>
            
            
            <td>
			<div align="left">
 				<input type="hidden" id="birthCountryhidden" name="birthCountry" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.birthCountry"/>"/>
				<nested:select disabled="true" property="applicantDetails.personalData.birthCountry" name="onlineApplicationForm" styleClass="dropdown" styleId="birthCountry" onchange="getStates(this.value,'birthState');">
				<option value=""><bean:message key="knowledgepro.admin.select"/></option>
				<%String selected=""; %>
				<logic:iterate id="option" property="countries" name="onlineApplicationForm">
				<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
				<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
				<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
				</logic:iterate>
				</nested:select>
				 <a href="#" title="Select Bitrh Country" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>
			</td>
            
            
          </tr>
          
          
          
          <tr>
          
          
          
          <td align="right"> 
            <bean:message key="admissionForm.studentinfo.mobile.label"/><span class="Mandatory">*</span>
             </td>
            
            <td>
            
              <nested:text readonly="true" styleClass="textboxsmall"  property="applicantDetails.personalData.mobileNo1" name="onlineApplicationForm" styleId="applicantMobileCode" maxlength="4" size="4" onkeypress="return isNumberKey(event)" ></nested:text>
              <nested:text readonly="true" styleClass="textboxmedium" property="applicantDetails.personalData.mobileNo2" name="onlineApplicationForm" styleId="applicantMobileNo" maxlength="10" size="10" onkeypress="return isNumberKey(event)"></nested:text>
              <a href="#" title="Enter Your Mobile Code and Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
           </td>
          
          
			
			<%String dynaStyle=""; %>
			<logic:equal value="Other" property="birthState" name="onlineApplicationForm">
			<%dynaStyle="display:block;"; %>
			</logic:equal>
			<logic:notEqual value="Other" property="birthState" name="onlineApplicationForm">
			<%dynaStyle="display:none;"; %>
			</logic:notEqual>
			
            <td align="right">Birth <bean:message key="admissionForm.studentinfo.birthdetails.state.label"/><span class="Mandatory">*</span></td>
            
            
            <td>
             
           
            
           
            <div align="left">
            <input type="hidden" id="birthState1" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.birthState"/>">
            <nested:select disabled="true" property="applicantDetails.personalData.birthState" name="onlineApplicationForm"	styleId="birthState" styleClass="dropdown"
						onchange="funcOtherShowHide('birthState','otherBirthState')">
			<html:option value="">- Select -</html:option>
														
			<logic:notEmpty property="stateMap" name="onlineApplicationForm">
			<html:optionsCollection name="onlineApplicationForm" property="stateMap" label="value" value="key" />
			</logic:notEmpty>
			<html:option value="Other">Other</html:option>
			</nested:select>
			 <a href="#" title="Select Your BirthState" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>
           <div align="left">
            <nested:text readonly="true" property="applicantDetails.personalData.stateOthers"  name="onlineApplicationForm"	maxlength="30" styleId="otherBirthState"
						style="<%=dynaStyle %>" styleClass="textbox"></nested:text>
            </div>
            
           
            
          
           
            </td>
            
            
          </tr>
          
          
          
          <tr>
          
          
          <td align="right"><bean:message key="admissionForm.studentinfo.email.label"/><span class="Mandatory">*</span></td>
            <td><div align="left">
            <nested:text readonly="true" property="applicantDetails.personalData.email" styleId="applicantEmail" name="onlineApplicationForm" styleClass="textbox"  maxlength="50"></nested:text>
             <a href="#" title="Enter Your Mail" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            <br/>(e.g. name@yahoo.com)
            </div>
            </td>
          
            <td align="right">Birth <bean:message key="admissionForm.studentinfo.birthdetails.place.label"/><span class="Mandatory">*</span>
           </td>
            <td><nested:text readonly="true" property="applicantDetails.personalData.birthPlace"  styleId="birthPlace" name="onlineApplicationForm"  maxlength="50" styleClass="textbox"></nested:text>
             <a href="#" title="Enter Your BirthPlace" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            </td>  
          </tr>
          
          <tr>
				<td align="right"><bean:message key="admissionForm.studentinfo.aadhaarNumber"/>:</td>
				<td>
					<nested:text readonly="true" property="applicantDetails.personalData.aadhaarCardNumber" styleId="applicantadhaarNo" name="onlineApplicationForm" styleClass="textbox"  maxlength="12" onkeypress="return isNumberKey(event)"></nested:text>
					<a href="#" title="Enter Your Aadhaar Card Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
				</td>
				<td align="right"><bean:message key="knowledgepro.applicationform.mothertongue.label"/></td>
				<td>
					<nested:select disabled="true" property="applicantDetails.personalData.motherTongueId" styleId="studentMotherTongueId" name="onlineApplicationForm" styleClass="dropdownmedium">
						<option value=""><bean:message key="knowledgepro.admin.select"/></option>
						<nested:notEmpty property="applicantDetails.personalData.motherTongues" name="onlineApplicationForm">
							<nested:optionsCollection name="onlineApplicationForm" property="applicantDetails.personalData.motherTongues" label="value" value="key" />
						</nested:notEmpty>						
					</nested:select>
					<a href="#" title="Enter Your Mother Tongue" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
				</td>
          </tr>
          
          
          </table>
       </td>
      </tr>
      
      
      
       <tr ><td height="30"></td></tr>




  <tr>
   
    <td width="100%"><table align="center" width="40%" border="0" style="border-collapse:collapse" >
      <tr>
        <td height="30" align="center" class="subheading">Current Address </td>
      </tr>
    </table>
    </td>
  </tr>
   

 
      <tr>
        <td><table  align="center" cellpadding="4" class="subtable"  >
          <tr>
          
            <td align="right" width="25%">House Name/House Number:<span class="Mandatory">*</span></td>
            <td width="25%"><nested:text readonly="true" property="applicantDetails.personalData.currentAddressLine1" styleId="currentAddressLine1" styleClass="textbox" name="onlineApplicationForm" maxlength="35"></nested:text>
			<a href="#" title="Enter Your House Name/House Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
			
			
            <td align="right" width="25%">Country:<span class="Mandatory">*</span></td>
            <td width="25%">
            <div align="left">
            <input type="hidden" id="currentCountryNamehidden" name="nationality" name="onlineApplicationForm" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.currentCountryId"/>"/>
				
			<nested:select disabled="true" property="applicantDetails.personalData.currentCountryId" name="onlineApplicationForm" styleClass="dropdown" styleId="currentCountryName" onchange="getTempAddrStates(this.value,'tempAddrstate');">
					<option value=""><bean:message key="knowledgepro.admin.select"/></option>
						<%String selected=""; %>
						<logic:iterate id="option" property="countries" name="onlineApplicationForm">
							<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
							<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
							<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
						</logic:iterate>
			</nested:select>
			<a href="#" title="Enter Your Country" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           
            </div>
             
            </td>
          </tr>
          
          
          <tr>
            <td align="right"> Post Office Name:<span class="Mandatory">*</span></td>
            <td><nested:text readonly="true" property="applicantDetails.personalData.currentAddressLine2" styleId="currentAddressLine2" styleClass="textbox" name="onlineApplicationForm" maxlength="40"></nested:text>
			<a href="#" title="Enter Your Post Office Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
			
            <td align="right">State:<span class="Mandatory">*</span></td>
           
        	<td>
             <div align="left">
            
        <input type="hidden" id="currentStateId1" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.currentStateId"/>">
		<logic:equal value="Other" property="applicantDetails.personalData.currentStateId" name="onlineApplicationForm">
			<%dynaStyle="display:block;"; %>
		</logic:equal>
		<logic:notEqual value="Other" property="applicantDetails.personalData.currentStateId" name="onlineApplicationForm">
			<%dynaStyle="display:none;"; %>
		</logic:notEqual>
		
		 <nested:select disabled="true" property="applicantDetails.personalData.currentStateId" name="onlineApplicationForm"  styleClass="dropdown" styleId="tempAddrstate" onchange="funcOtherShowHide('tempAddrstate','otherTempAddrState');getTempAddrDistrict(this.value,'tempAddrdistrict');">
		<html:option value="">- Select -</html:option>
		
		<logic:notEmpty property="stateMap" name="onlineApplicationForm">
					<html:optionsCollection name="onlineApplicationForm" property="curAddrStateMap"
										label="value" value="key" />
									
					</logic:notEmpty>
					<html:option value="Other">Other</html:option>
		</nested:select>
		<a href="#" title="Enter Your Current State" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
		</div>
		
		
	   <div align="left"><nested:text readonly="true" property="applicantDetails.personalData.currentAddressStateOthers" styleClass="textbox" name="onlineApplicationForm" maxlength="30" styleId="otherTempAddrState" style="<%=dynaStyle %>"></nested:text></div>
	
	   
            
           
     </td>
     </tr>
     
     
          <tr>
          <td align="right">Street:<span class="Mandatory">*</span></td>
            <td><nested:text property="applicantDetails.personalData.currentStreet" styleId="currentStreet" styleClass="textbox" name="onlineApplicationForm" maxlength="30" onkeypress="IsAlpha(event)"></nested:text>
			<a href="#" title="Enter Your Street Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
            
            
            <td align="right">District:<span class="Mandatory">*</span></td>
            <td>
            
            <logic:equal value="Other" property="applicantDetails.personalData.currentDistricId" name="onlineApplicationForm">
										<%dynaStyle="display:block;"; %>
			</logic:equal>
			<logic:notEqual value="Other" property="applicantDetails.personalData.currentDistricId" name="onlineApplicationForm">
										<%dynaStyle="display:none;"; %>
			</logic:notEqual>
			<div align="left"><nested:select disabled="true" property="applicantDetails.personalData.currentDistricId" name="onlineApplicationForm" styleClass="dropdown" styleId="tempAddrdistrict" onchange="funcOtherShowHide('tempAddrdistrict','otherTempAddrDistrict');">
									<html:option value="">- Select -</html:option>
									<c:if test="${onlineApplicationForm.applicantDetails.personalData.currentStateId != null && onlineApplicationForm.applicantDetails.personalData.currentStateId!= ''}">
			                           					<c:set var="tempAddrDistrictMap" value="${baseActionForm.collectionMap['tempAddrDistrictMap']}"/>
		                            		    	 	<c:if test="${tempAddrDistrictMap != null}">
		                            		    	 		<html:optionsCollection name="tempAddrDistrictMap" label="value" value="key"/>
															
		                            		    	 	</c:if> 
			                        </c:if>
			                        
			                        <logic:notEmpty property="editCurrentDistrict" name="onlineApplicationForm">
									<html:optionsCollection name="onlineApplicationForm" property="editCurrentDistrict" label="name" value="id" />
									
									</logic:notEmpty>
									<html:option value="Other">Other</html:option>
			
				</nested:select>
				<a href="#" title="Enter Your Current District" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
				</div>
				
							
			<div align="left"><nested:text readonly="true" property="applicantDetails.personalData.currentAddressDistrictOthers" name="onlineApplicationForm" styleClass="textbox" size="10" maxlength="30" styleId="otherTempAddrDistrict" style="<%=dynaStyle %>"></nested:text></div>
							
			
            
            </td>
          </tr>
          
          
          <tr>
            <td align="right">City:<span class="Mandatory">*</span></td>
            <td><nested:text readonly="true" property="applicantDetails.personalData.currentCityName" styleId="currentCityName" styleClass="textbox" name="onlineApplicationForm" maxlength="30"></nested:text>
			<a href="#" title="Enter Your City Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
            
             <td align="right"> Pin Code:<span class="Mandatory">*</span></td>
            <td><nested:text readonly="true" styleClass="textbox" property="applicantDetails.personalData.currentAddressZipCode" styleId="currentAddressZipCode" name="onlineApplicationForm"  maxlength="10"/>
            <a href="#" title="Enter Your Current PinCode" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            </td>
           
          </tr>
          
          
		   	
            
		  
        </table>
        </td>
      </tr>
      
      
      
 		 <tr ><td height="30"></td></tr>
 		 
 		 
              
					<tr>
   
    				<td >
    				<table align="center" width="100%" border="0" style="border-collapse:collapse">
      				<tr>
       				 <td height="30" align="center" class="subheading">
       				 <bean:message key="knowledgepro.applicationform.sameaddr.label"/> &nbsp; 
       				 	 <html:radio disabled="true" property="sameTempAddr" styleId="sameAddr" value="true" onclick="disableTempAddress();"><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
					     <html:radio disabled="true" property="sameTempAddr" styleId="DiffAddr" value="false" onclick="enableTempAddress();"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio>
					<a href="#" title="Select Parent Address if same as Current Address " class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
					</td>
     				 </tr>
    				</table>
    				</td>
  					</tr>






 
     
          
          
      <tr >
   
      <td width="100%"><div id="currLabel"><table align="center" width="100%" border="0" style="border-collapse:collapse" >
      <tr>
        <td height="30" align="center" class="subheading">Permanent Address </td>
      </tr>
    </table>
    </div>
    </td>
    </tr>
   

 
      <tr >
        <td><div id="currTable"><table  align="center" cellpadding="4" class="subtable"  >
          <tr>
     
          
            <td align="right" width="25%">House Name/House Number:<span class="Mandatory">*</span></td>
            <td width="25%"><nested:text readonly="true" property="applicantDetails.personalData.permanentAddressLine1" styleId="permanentAddressLine1" styleClass="textbox" name="onlineApplicationForm"  maxlength="35"></nested:text>
			<a href="#" title="Enter Your House Number and House Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
			
			
            <td align="right" width="25%">Country:<span class="Mandatory">*</span></td>
            <td width="25%">
            <div align="left">
            <input type="hidden" id="permanentCountryNamehidden" name="nationality" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.permanentCountryId"/>"/>
											
			<nested:select disabled="true" property="applicantDetails.personalData.permanentCountryId" name="onlineApplicationForm" styleClass="dropdown" styleId="permanentCountryName" onchange="getPermAddrStates(this.value,'permAddrstate');">
					<option value=""><bean:message key="knowledgepro.admin.select"/></option>
					<%String selected=""; %>
					<logic:iterate id="option" property="countries" name="onlineApplicationForm">
					<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
					<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
					<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
					</logic:iterate>
			</nested:select>
			<a href="#" title="Selcet your Permanent Country" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            </div>
            </td>
          </tr>
          
          
          <tr>
            <td align="right"> Post Office Name:<span class="Mandatory">*</span></td>
            <td>
           <nested:text readonly="true" property="applicantDetails.personalData.permanentAddressLine2" styleClass="textbox"  name="onlineApplicationForm"  maxlength="40"></nested:text>
			<a href="#" title="Enter Your Permanent Post Office Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
			
            <td align="right">State:<span class="Mandatory">*</span></td>
           
        	<td>
            
       		<input type="hidden" id="permanentState" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.permanentStateId"/>">
			<logic:equal value="Other" property="applicantDetails.personalData.permanentStateId" name="onlineApplicationForm">
						<%dynaStyle="display:block;"; %>
			</logic:equal>
			<logic:notEqual value="Other" property="applicantDetails.personalData.permanentStateId" name="onlineApplicationForm">
						<%dynaStyle="display:none;"; %>
			</logic:notEqual>
			
			<div align="left">
              <nested:select disabled="true" property="applicantDetails.personalData.permanentStateId" name="onlineApplicationForm" styleClass="dropdown" styleId="permAddrstate" onchange="funcOtherShowHide('permAddrstate','otherPermAddrState');getPermAddrDistrict(this.value,'permAddrdistrict');">
						<html:option value="">- Select -</html:option>
									
						<logic:notEmpty property="stateMap" name="onlineApplicationForm" >
						<html:optionsCollection name="onlineApplicationForm" property="perAddrStateMap" label="value" value="key" />
													
						</logic:notEmpty>
						<html:option value="Other">Other</html:option>
			</nested:select>
			<a href="#" title="Enter Your Permanenet State" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>
			<div align="left"><nested:text readonly="true" property="applicantDetails.personalData.permanentAddressStateOthers" name="onlineApplicationForm" styleClass="textbox" maxlength="30" styleId="otherPermAddrState" style="<%=dynaStyle %>"></nested:text>
			</div>
        </td>
        </tr>
     
     
          <tr>
          <td align="right">Street:<span class="Mandatory">*</span></td>
            <td><nested:text property="applicantDetails.personalData.permanentStreet" styleId="permanentStreet" styleClass="textbox" name="onlineApplicationForm" maxlength="30" onkeypress="IsAlpha(event)"></nested:text>
			<a href="#" title="Enter Your Street Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
            
            
            <td align="right">District:<span class="Mandatory">*</span></td>
            <td>
           
           <logic:equal value="Other" property="applicantDetails.personalData.permanentDistricId" name="onlineApplicationForm">
						<%dynaStyle="display:block;"; %>
			</logic:equal>
			
			<logic:notEqual value="Other" property="applicantDetails.personalData.permanentDistricId" name="onlineApplicationForm">
					<%dynaStyle="display:none;"; %>
			</logic:notEqual>
			
				<div align="left">
                     <nested:select disabled="true" property="applicantDetails.personalData.permanentDistricId" name="onlineApplicationForm" styleClass="dropdown" styleId="permAddrdistrict" onchange="funcOtherShowHide('permAddrdistrict','otherPermAddrDistrict');">
									<html:option value="">- Select -</html:option>
									<c:if test="${onlineApplicationForm.applicantDetails.personalData.permanentStateId != null && onlineApplicationForm.applicantDetails.personalData.permanentStateId!= ''}">
			                           					<c:set var="permAddrDistrictMap" value="${baseActionForm.collectionMap['permAddrDistrictMap']}"/>
		                            		    	 	<c:if test="${permAddrDistrictMap != null}">
		                            		    	 		<html:optionsCollection name="permAddrDistrictMap" label="value" value="key"/>
															
		                            		    	 	
		                            		    	 	</c:if> 
			                        </c:if>
			                         <logic:notEmpty property="editPermanentDistrict" name="onlineApplicationForm">
									<html:optionsCollection name="onlineApplicationForm" property="editPermanentDistrict" label="name" value="id" />
									
									</logic:notEmpty>
									<html:option value="Other">Other</html:option>
				   </nested:select>
				   <a href="#" title="Enter Your Permanent District" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
				   </div>
				  <div align="left"><nested:text readonly="true" property="applicantDetails.personalData.permanentAddressDistrictOthers" name="onlineApplicationForm" size="10" maxlength="30" styleClass="textbox" styleId="otherPermAddrDistrict" style="<%=dynaStyle %>"></nested:text>
					</div>
            </td>
          </tr>
          
          
          <tr>
            <td align="right">City:<span class="Mandatory">*</span></td>
            <td>
            <nested:text readonly="true" property="applicantDetails.personalData.permanentCityName" styleId="permanentCityName" styleClass="textbox" name="onlineApplicationForm"  maxlength="30"></nested:text>
			<a href="#" title="Enter Your Permanent City" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
            
            <td align="right"> Pin Code:<span class="Mandatory">*</span></td>
            <td><nested:text readonly="true" styleClass="textbox" property="applicantDetails.personalData.currentAddressZipCode" styleId="permanentAddressZipCode" name="onlineApplicationForm"  maxlength="10"/>
            <a href="#" title="Enter Your Permanent Pincode" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            </td>
            
          </tr>
          
          
		 	
           
		  
        </table>
        </div>
        </td>
      </tr>
      
      
   				           	  
       <tr ><td height="30"></td></tr>
       
       
   <tr>
    <td width="100%"><table align="center" width="40%" border="0" style="border-collapse:collapse"  >
      <tr>
        <td height="30" align="center" class="subheading">Parent Information</td>
      </tr>
    </table>
    </td>
  </tr>

  
      <tr>
        <td><table width="100%"  align="center" cellpadding="4" class="subtable"  >
        
          <tr>
            <td align="right" width="25%"><bean:message key="knowledgepro.admission.fatherName" /><span class="Mandatory">*</span></td>
           
            <td width="25%"><div align="left">
            				<nested:select disabled="true" property="applicantDetails.titleOfFather" styleId='titleOfFather' name="onlineApplicationForm" styleClass="dropdownsmall" onchange="fatherIncomeMandatory()">
							<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
							<html:option value="Mr">Mr.</html:option>
							<html:option value="Late">Late.</html:option>
							</nested:select>
							
			<nested:text readonly="true" property="applicantDetails.personalData.fatherName" styleId="fatherName" name="onlineApplicationForm" styleClass="textboxmedium" maxlength="50"></nested:text>
			<a href="#" title="Enter Title of Father and Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>								
			</td>
			
            <td align="right" width="25%">
				<bean:message	key="knowledgepro.admission.motherName" /><span class="Mandatory">*</span>
			</td>
			<td width="25%"><div align="left">
				<nested:select disabled="true" property="applicantDetails.titleOfMother" styleId='titleOfMother' name="onlineApplicationForm" styleClass="dropdownsmall" onchange="motherIncomeMandatory()">
				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
				<html:option value="Mrs">Mrs.</html:option>
				<html:option value="Late">Late.</html:option>
				</nested:select>
				<nested:text readonly="true" property="applicantDetails.personalData.motherName" styleId="motherName"  name="onlineApplicationForm" styleClass="textboxmedium" maxlength="50"></nested:text>
			<a href="#" title="Enter Title of Mother and Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div></td>
			
          </tr>
          
          
          
          <tr>
          
          	
				
            <td width="25%" align="right">
				<bean:message key="knowledgepro.admin.occupation"/>:
			</td>
												
			<td width="25%" >
			
			<div align="left">
			<input type="hidden" id="hiddenFatherOccupationId"  name="onlineApplicationForm" 
						value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.fatherOccupationId"/>"/>
				<nested:select disabled="true" property="applicantDetails.personalData.fatherOccupationId" name="onlineApplicationForm" styleClass="dropdown" styleId="fatherOccupation" onchange="displayOtherForFather(this.value)">
				<html:option value="">- Select -</html:option>
				<html:optionsCollection name="onlineApplicationForm" property="occupations" label="occupationName" value="occupationId"/>
				<html:option value="Other">Other</html:option>
				</nested:select>
				<a href="#" title="Select Father Occupation" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>
										
			<div align="left" id="displayFatherOccupation" >
			
					<nested:text readonly="true" property="applicantDetails.personalData.otherOccupationFather" name="onlineApplicationForm" styleClass="textbox" maxlength="50" styleId="otherOccupationFather"/>
			</div>
			</td>
												
			 <logic:equal value="Other" property="applicantDetails.personalData.motherOccupationId" name="onlineApplicationForm">
						<%dynaStyle="display:block;"; %>
			</logic:equal>
			
			<logic:notEqual value="Other" property="applicantDetails.personalData.motherOccupationId" name="onlineApplicationForm">
					<%dynaStyle="display:none;"; %>
			</logic:notEqual>
												
            <td width="25%" align="right"><bean:message	key="knowledgepro.admin.occupation"/>:</td>
            <td width="25%">
            
			<div align="left">
			 <nested:select disabled="true" property="applicantDetails.personalData.motherOccupationId" name="onlineApplicationForm" styleClass="dropdown" styleId="motherOccupation" onchange="displayOtherForMother(this.value)">
													<html:option value="">- Select -</html:option>
													<html:optionsCollection name="onlineApplicationForm" property="occupations" label="occupationName" value="occupationId"/>
				 									<html:option value="Other">Other</html:option>
			</nested:select>
			<a href="#" title="Select Mother Occupation" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
		   </div>
			
			<div align="left" id="displayMotherOccupation" >
			
					<nested:text readonly="true" property="applicantDetails.personalData.otherOccupationMother" name="onlineApplicationForm" styleClass="textbox"   maxlength="50"  styleId="otherOccupationMother"/>
			</div>
			
            </td>
          </tr>
          
          
         
          
          <tr>
            <td  >
			<div align="right"><bean:message key="admissionForm.studentinfo.mobile.label" /><span class="Mandatory">*</span></div>
			</td>
			<td  align="left">
			<nested:text readonly="true" styleId="fatherMobile" property="applicantDetails.personalData.parentMob1" name="onlineApplicationForm" size="4" maxlength="4" styleClass="textboxsmall" ></nested:text> <nested:text readonly="true" styleId="fatherMobile1" property="applicantDetails.personalData.fatherMobile" name="onlineApplicationForm" size="15" maxlength="10" styleClass="textboxmedium"></nested:text>
			<a href="#" title="Enter Father Mobile Code and Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            									
            </td>
			
            <td  >
			<div align="right"><bean:message	key="admissionForm.studentinfo.mobile.label" /></div>
			</td>
			<td   align="left">
			<nested:text readonly="true" styleId="motherMobile" property="applicantDetails.personalData.parentMob1" name="onlineApplicationForm" size="4" maxlength="4" styleClass="textboxsmall"></nested:text> <nested:text readonly="true" styleId="motherMobile1" property="applicantDetails.personalData.motherMobile" name="onlineApplicationForm" size="15" maxlength="10" styleClass="textboxmedium"></nested:text>
			<a href="#" title="Enter Mother Mobile Code and Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            									
             </td>
             
          </tr>
          
           <tr>
            <td width="25%" align="right">
			<bean:message key="admissionForm.studentinfo.email.label"/></td>
			<td >
			<div align="left"><nested:text readonly="true" property="applicantDetails.personalData.fatherEmail" name="onlineApplicationForm" styleClass="textbox" maxlength="50"></nested:text>
			<a href="#" title="Enter Father Mail" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>
		    <div align="left">(e.g. name@yahoo.com)</div>
            
			</td>
			
            <td width="25%" align="right"><bean:message key="admissionForm.studentinfo.email.label" /></td>
            <td>
            <div align="left"><nested:text readonly="true" property="applicantDetails.personalData.motherEmail" styleId="motherEmail" styleClass="textbox" name="onlineApplicationForm"  maxlength="50"></nested:text>
            <a href="#" title="Enter Mother Mail" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            </div>
			<div align="left">(e.g. name@yahoo.com)</div>
            </td>
          </tr>
		 
		  <tr>
            <td width="25%" align="right">
			Official Adress:
			</td>
			<td width="25%" align="right">
			<div align="left"><nested:text readonly="true" property="applicantDetails.personalData.fatherOccupationAddress" name="onlineApplicationForm" styleId="fatherAddress" styleClass="textbox" maxlength="50"></nested:text>
			<a href="#" title="Enter Father Occupation Address" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            </div>
			</td>
			
			
			<td width="25%" align="right">
			Official Adress:
			</td>
			<td width="25%" align="right">
			<div align="left"><nested:text readonly="true" property="applicantDetails.personalData.motherOccupationAddress" name="onlineApplicationForm" styleId="motherAddress" styleClass="textbox" maxlength="50"></nested:text>
			<a href="#" title="Enter Mother Occupation Address" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            </div>
			</td>
           
          </tr>
          
          <tr>
          	<td width="25%" align="right">
				PAN Card Number :
			</td>
			<td width="25%" align="left">
				<nested:text readonly="true" property="applicantDetails.personalData.fatherPANNumber" name="onlineApplicationForm" styleId="fatherPANNumber" styleClass="textbox" maxlength="10" style="text-transform:uppercase"></nested:text>
				<a href="#" title="Enter Father's PAN Card Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
			</td>
          </tr>
          
           <tr>
           
          </table>
          </td>
      </tr>
      
      
      
       <tr ><td height="30"></td></tr>
      
     				
					<tr>
   
    				<td >
    				<table align="center" width="100%" border="0" style="border-collapse:collapse">
      				<tr>
       				 <td height="30" align="center" class="subheading">
       				    <bean:message key="knowledgepro.applicationform.sameaddrparent.label"/> &nbsp;
       				 	<html:radio disabled="true" property="sameParentAddr" styleId="sameParAddr" value="true" onclick="setParentAddress();"><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
						<html:radio disabled="true"  property="sameParentAddr" styleId="DiffParAddr" value="false" onclick="disParentAddress();"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio>
						<a href="#" title="Select Parent Address Yes, if same as Current Address  " class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
						</td>
            
     				 </tr>
    				</table>
    				</td>
  					</tr>
					
					<tr>
   
    				<td width="100%">
    				<table align="center" width="40%" border="0" style="border-collapse:collapse">
      				<tr>
       				 <td height="30" align="center" class="subheading">
       				 Parent Address</td>
     				 </tr>
    				</table>
    				</td>
  					</tr>
  					
						
					<tr>
                       <td><table width="100%"  align="center" cellpadding="4" class="subtable"  >
        
							<tr >
								<td width="25%"   >
								<div align="right">House Name/House Number:<span class="Mandatory">*</span></div>
								</td>
								<td  width="25%"   align="left">
								<nested:text readonly="true" styleId="parentAddressLine1" property="applicantDetails.personalData.parentAddressLine1" styleClass="textbox" name="onlineApplicationForm" size="15" maxlength="100" ></nested:text>
								<a href="#" title="Enter Your Parent House Name or Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
								</td>
								
								
								<td width="25%"  align="right"><bean:message key="admissionForm.studentinfo.mobile.label"/><span class="Mandatory">*</span></td>
            				<td width="25%" >
           					 <div align="left">
           					 <nested:text readonly="true" styleClass="textboxsmall" property="applicantDetails.personalData.parentMob1" styleId="parentMobile" name="onlineApplicationForm"  maxlength="4" size="4" onkeypress="return isNumberKey(event)" onkeydown="hideGuardenPhoneNumber()"  ></nested:text>
				   			 <nested:text readonly="true" styleClass="textboxmedium" property="applicantDetails.personalData.parentMob2"  styleId="parentMobile1" name="onlineApplicationForm"  maxlength="10" size="10" onkeypress="return isNumberKey(event)" onkeydown="hideGuardenPhoneNumber()"  ></nested:text>
					 		<a href="#" title="Enter Your Parent Mobile Code and Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
					 		</div>
							</td>
							
								
								
							</tr>
										
										
										
							<tr >
								<td  >
								<div align="right">Post Office Name:<span class="Mandatory">*</span></div>
								</td>
								<td  >
								<nested:text readonly="true" styleId="parentAddressLine2" property="applicantDetails.personalData.parentAddressLine2" styleClass="textbox" name="onlineApplicationForm" size="15" maxlength="100"></nested:text>
								<a href="#" title="Enter Your Parent Post Office Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
								</td>
											
								
								<td align="right">
								<bean:message key="knowledgepro.admin.country" /><span class="Mandatory">*</span>
								
								</td>
								<td ><div align="left">
								<input type="hidden" id="hiddenParentCountryId" name="nationality" name="onlineApplicationForm" 
												   value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.parentCountryId"/>"/>
											
								<nested:select disabled="true" property="applicantDetails.personalData.parentCountryId" name="onlineApplicationForm" styleClass="dropdown" styleId="parentCountryName" onchange="getParentAddrStates(this.value,'parentState')">
								<option value=""><bean:message key="knowledgepro.admin.select"/></option>
								<%String selected=""; %>
								<logic:iterate id="option" property="countries" name="onlineApplicationForm">
								<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
								<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
								<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
								</logic:iterate>
								</nested:select>
								<a href="#" title="Enter Your Parent Country" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
								</div>
								</td>
											
			

							</tr>
										
										
										
							<tr >
							<td  align="right">
							<bean:message	key="knowledgepro.admission.zipCode" /><span class="Mandatory">*</span>
							</td>
							<td   align="left">
							<nested:text readonly="true" styleId="parentAddressZipCode" property="applicantDetails.personalData.parentAddressZipCode" styleClass="textbox" name="onlineApplicationForm" size="10" maxlength="10"></nested:text>
							<a href="#" title="Select Your Parent ZIP Code" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           	
							</td>
											
							
							<td  >
							<div align="right"><bean:message key="knowledgepro.admin.state" /><span class="Mandatory">*</span></div>
							</td>
											
							<td  ><div align="left">
							<%String dynastyle=""; %>
										<logic:equal value="Other" property="applicantDetails.personalData.parentStateId" name="onlineApplicationForm">
										<%dynastyle="display:block;"; %>
										</logic:equal>
										<logic:notEqual value="Other" property="applicantDetails.personalData.parentStateId" name="onlineApplicationForm">
										<%dynastyle="display:none;"; %>
										</logic:notEqual>
					                  	<nested:select disabled="true" property="applicantDetails.personalData.parentStateId" name="onlineApplicationForm" styleClass="dropdown" styleId="parentState" onchange="funcOtherShowHide('parentState','otherParentAddrState');">
											<html:option value="">- Select -</html:option>
											<logic:notEmpty property="parentStateMap" name="onlineApplicationForm">
											<html:optionsCollection name="onlineApplicationForm" property="parentStateMap" label="value" value="key" />
											</logic:notEmpty>
											<html:option value="Other">Other</html:option>
										</nested:select>
										<a href="#" title="Enter Your Parent State" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
										</div>				
							<div align="left">
							<nested:text readonly="true" property="applicantDetails.personalData.parentAddressStateOthers" styleClass="textbox" name="onlineApplicationForm" maxlength="30" styleId="otherParentAddrState" style="<%=dynastyle %>"></nested:text>
							</div>
							</td>
											
											
							</tr>
							
							 <tr>
            				<td  width="25%" >
								<div align="right"><bean:message key="knowledgepro.admin.city" />:<span class="Mandatory">*</span></div>
								</td>
								<td  width="25%"   align="left">
								<nested:text readonly="true" styleId="parentCityName" property="applicantDetails.personalData.parentCityName" styleClass="textbox" name="onlineApplicationForm" size="15" maxlength="30"></nested:text>
								<a href="#" title="Enter Your Parent City" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
								</td>
			
           				 	<td></td>
            				<td></td>
         					 </tr>					
									
						 </table>
          				</td>
      					</tr>
      
      
      
      
      
       <tr ><td height="30"></td></tr>
      
      
      
      
      

   <tr>
   
    <td width="100%"><table align="center" width="40%" border="0" style="border-collapse:collapse">
      <tr>
        <td height="30" align="center" class="subheading">Reservation Details</td>
      </tr>
    </table></td>
  </tr>




      <tr>
        <td><table align="center" cellpadding="4" class="subtable"  >
          <tr>
            <td valign="top" align="right" width="25%"><bean:message key="admissionForm.studentinfo.religion.label"/><span class="Mandatory">*</span></td>
            <td width="25%">
           
            <logic:equal value="Other" property="religionId" name="onlineApplicationForm">
				<%dynaStyle="display:block;"; %>
			</logic:equal>
			<logic:notEqual value="Other" property="religionId" name="onlineApplicationForm">
				<%dynaStyle="display:none;"; %>
			</logic:notEqual>
               <div align="left">
													
				<input type="hidden" id="religionType" name="religionType" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.religionId"/>'/>
				
				<logic:notEqual value="Other" property="applicantDetails.personalData.religionId" name="onlineApplicationForm">
					<%dynaStyle="display:none;"; %>
				<nested:select disabled="true" property="applicantDetails.personalData.religionId" name="onlineApplicationForm" styleClass="dropdown" styleId="religions" onchange="getSubCaste(this.value,'castCatg');funcOtherShowHide('religions','otherReligion');funcReligionShowHide(this.value);">
				<option value=""><bean:message key="knowledgepro.admin.select"/></option>
				<nested:optionsCollection name="onlineApplicationForm" property="religions" label="religionName" value="religionId"/>
				<html:option value="Other">Other</html:option>
				</nested:select>
				<a href="#" title="Select Your Religion,If You are a Christian, Select Dioceses and Parish Category also" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           	
				</logic:notEqual>
				
				<logic:equal value="Other" property="applicantDetails.personalData.religionId" name="onlineApplicationForm">
				<%dynaStyle="display:block;"; %>												
				<nested:select disabled="true" property="applicantDetails.personalData.religionId" name="onlineApplicationForm" styleClass="dropdown" styleId="religions" onchange="getSubCaste(this.value,'castCatg');funcOtherShowHide('religions','otherReligion');funcReligionShowHide(this.value);">
				<option value=""><bean:message key="knowledgepro.admin.select"/></option>
				<nested:optionsCollection name="onlineApplicationForm" property="religions" label="religionName" value="religionId"/>
				<html:option value="Other">Other</html:option>
				</nested:select>
				<a href="#" title="Select Your Religion,If You are a Christian, Select Dioceses and Parish Category also" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           	
												
			  </logic:equal>
			  </div>
			  
			  		
			
			<div align="left"><nested:text readonly="true" property="applicantDetails.personalData.religionOthers" styleClass="textbox"  name="onlineApplicationForm"   maxlength="30" styleId="otherReligion" style="<%=dynaStyle %>"></nested:text></div>
			
			
			 <logic:equal name="onlineApplicationForm" property="applicantDetails.personalData.religionId" value="3">
				<%dynaStyle="display:block;"; %>	
			</logic:equal>
			
			               
			 <div align="left" id="parish_description" style="display: none;" >
                    Dioceses:<nested:text readonly="true" property="applicantDetails.personalData.dioceseOthers" styleClass="textboxmedium"  name="onlineApplicationForm" size="10" maxlength="30" styleId="otherDiocese" ></nested:text>
			 </div>
			  	 
              <div align="left" id="dioces_description" style="display: none;" >
                     &nbsp;  &nbsp;  Parish:<nested:text readonly="true" property="applicantDetails.personalData.parishOthers" styleClass="textboxmedium"  name="onlineApplicationForm" size="10" maxlength="30" styleId="otherParish" ></nested:text>
			</div>
	
			
            </td>
            
            
            
            
            <%String dynaStyle4="display:none;"; %>
			<logic:equal value="true" property="handicapped" name="onlineApplicationForm">
			<%dynaStyle4="display:block;"; %>
			</logic:equal>
         <td  align="right" width="25%"><bean:message key="knowledgepro.applicationform.physical.label"/></td>
                           
         <td width="25%" ><div align="left">
                <input type="hidden" id="hiddenHandicaped" name="hiddenHandicaped" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.handicapped"/>'/>
                <nested:radio disabled="true" property="applicantDetails.personalData.handicapped" styleId="handicappedYes" name="onlineApplicationForm" value="true" onclick="showHandicappedDescription()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
                           
				<nested:radio disabled="true" property="applicantDetails.personalData.handicapped" styleId="handicappedNo" name="onlineApplicationForm" value="false" onclick="hideHandicappedDescription()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
				<a href="#" title="Select Yes If You are Phisycally Chalenged Person" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           	
				</div>
				<div align="left" id="handicapped_description" style="<%=dynaStyle4 %>">
				<nested:text readonly="true" styleId="hadnicappedDescription" property="applicantDetails.personalData.hadnicappedDescription" name="onlineApplicationForm" maxlength="80" styleClass="textbox"></nested:text>
				</div>
			
		 </td>
						 
			
          </tr>
            
            
            
            
          <tr>
          
          
          <td align="right" width="25%"><bean:message key="admissionForm.studentinfo.subreligion.label"/><span class="Mandatory">*</span></td>
            <td width="25%">
            
           <div align="left">
				<input type="hidden" id="casteType" name="casteType" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.casteId"/>'/>
				<logic:equal value="Other" property="applicantDetails.personalData.casteId" name="onlineApplicationForm">
				<%dynaStyle="display:block;"; %>
				</logic:equal>
				<logic:notEqual value="Other" property="applicantDetails.personalData.casteId" name="onlineApplicationForm">
				<%dynaStyle="display:none;"; %>
				</logic:notEqual>
                 <nested:select disabled="true" property="applicantDetails.personalData.casteId" name="onlineApplicationForm" styleId="castCatg" styleClass="dropdown" onchange="funcOtherShowHide('castCatg','otherCastCatg')">
				  <option value="">-Select-</option>
				<%-- 
				<html:optionsCollection property="casteList" name="onlineApplicationForm" label="casteName" value="casteId"/>
				--%>	
				  <c:if test="${onlineApplicationForm.applicantDetails.personalData.religionId != null && onlineApplicationForm.applicantDetails.personalData.religionId != ''}">
						<c:set var="subCasteMap" value="${baseActionForm.collectionMap['subCasteMap']}"/>
			              <c:if test="${subCasteMap != null}">
			               <html:optionsCollection name="subCasteMap" label="value" value="key"/>
						  
			             </c:if> 
				  </c:if>
					
			<logic:notEmpty property="subCasteMap" name="onlineApplicationForm">
			<html:optionsCollection name="onlineApplicationForm" property="subCasteMap" label="value" value="key" />
			
			</logic:notEmpty>
							<html:option value="Other">Other</html:option>
				</nested:select>
				<a href="#" title="Select Your Caste" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           	
				</div>
				<div align="left"><nested:text readonly="true" property="applicantDetails.personalData.casteOthers" name="onlineApplicationForm" size="10" maxlength="30" styleId="otherCastCatg" styleClass="textbox" style="<%=dynaStyle %>"></nested:text></div>
				
				
            
            </td>
           
           
             <td align="right" ><div align="right">Participation in Cultural Activities:</div></td>
	                      <td    align="left"><div align="left">
	                      <input type="hidden" id="arts" name="ats" value='<bean:write name="onlineApplicationForm" property="arts"/>'/>
                         <nested:select disabled="true" property="applicantDetails.personalData.arts" name="onlineApplicationForm" styleClass="dropdown" styleId="arts1">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<html:option value="International Level">International Level</html:option>
											<html:option value="National Level">National Level</html:option>
											<html:option value="State Level">State Level</html:option>
											<html:option value="Participated">Inter college Level</html:option>
											<html:option value="District Level">District Level</html:option>
											
						</nested:select>
						<a href="#" title="Select If You are Participate In any Cultural Activities" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           	
						</div>
						
						 </td>
            
			
			
           </tr>
		   
		   
		   
		   <tr >
						   
 		    <td align="right"><bean:message key="admissionForm.studentinfo.castcatg.label"/><span class="Mandatory">*</span></td>
            
            <td>
            <div align="left">
	        <nested:select disabled="true" property="applicantDetails.personalData.subReligionId" name="onlineApplicationForm" styleClass="dropdown" styleId="subreligion" onchange="funcOtherShowHide('subreligion','otherSubReligion')">
			<html:option value="">- Select -</html:option>
					          
			<%-- 
										<c:if test="${onlineApplicationForm.applicantDetails.personalData.religionId != null && onlineApplicationForm.applicantDetails.personalData.religionId != ''}">
				                           					<c:set var="subReligionMap" value="${baseActionForm.collectionMap['subReligionMap']}"/>
			                            		    	 	<c:if test="${subReligionMap != null}">
			                            		    	 		<html:optionsCollection name="subReligionMap" label="value" value="key"/>
																<!--<html:option value="Other">Other</html:option>-->
			                            		    	 	</c:if> 
				                        </c:if>
				 --%> 
				                       
		    <html:optionsCollection property="subReligionMap" name="onlineApplicationForm" label="value" value="key"/>
																	
				                        
			</nested:select>
			<a href="#" title="Select your Caste Category" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           	
			</div>
			<%-- 
			<div align="left"><nested:text readonly="true" property="applicantDetails.personalData.religionSectionOthers" name="onlineApplicationForm" size="10" maxlength="30" styleId="otherSubReligion" style="<%=dynaStyle %>"></nested:text></div>
			--%>
			
            </td>
            
            <td  align="right" ><div align="right">If Yes Achievement In Cultural Activities:</div></td>
	                      <td   align="left">
	                      <div align="left"><input type="hidden" id="artsParticipate" name="artsParticipate" value='<bean:write name="onlineApplicationForm" property="artsParticipate"/>'/>
                         <nested:select disabled="true" property="applicantDetails.personalData.artsParticipate" name="onlineApplicationForm" styleClass="dropdown" styleId="artsParticipate1">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<html:option value="1 Prize">1 Prize</html:option>
											<html:option value="2 Prize">2 Prize</html:option>
											<html:option value="3 Prize">3 Prize</html:option>
											<html:option value="Participated">Participated</html:option>
											<html:option value="None">None</html:option>
						</nested:select>
						<a href="#" title="Select If You are Achieve In any Cultural Activities" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           	
						 </div>
						  
						 </td>
                       
 				
						 
						 
						 
										
                 </tr>
                 
                 
                 
                 
                 
                 
                 
                 	
						
			<tr >
						
						
						
			<td align="right">Annual Income: <span class="Mandatory">*</span></td>
			
            <td ><div align="left">
            <nested:select style="display:none" property="applicantDetails.personalData.fatherCurrencyId" name="onlineApplicationForm"  styleId="fatherCurrency">
												
											
           		 <%String selected=""; %>
				<logic:iterate id="option" property="currencyList" name="onlineApplicationForm">
				<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
				<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
				<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
				</logic:iterate>
			</nested:select>	
            <nested:select disabled="true" property="applicantDetails.personalData.fatherIncomeId" name="onlineApplicationForm" styleClass="dropdown" styleId="fatherIncomeRange">
				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
				<nested:optionsCollection name="onlineApplicationForm" property="incomeList" label="incomeRange" value="id"/>
										
				
			</nested:select>
			 <a href="#" title="Select Your Family Annual Income" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           		
			</div>
			</td>
						
						
				<td  align="right" ><div align="right">Participation in Sports:</div></td>
	            <td   align="left"><div align="left">
	            <input type="hidden" id="sports" name="sports" value='<bean:write name="onlineApplicationForm" property="sports"/>'/>
                <nested:select disabled="true" property="applicantDetails.personalData.sports" name="onlineApplicationForm" styleClass="dropdown" styleId="sports1">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<html:option value="International Level">International Level</html:option>
											<html:option value="National Level">National Level</html:option>
											<html:option value="State Level">State Level</html:option>
											<html:option value="Participated">Inter college Level</html:option>
											<html:option value="District Level">District Level</html:option>
											
		 	 </nested:select>
		 	  <a href="#" title="Select If You are Participte in any Sports" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           	
			 </div> 
						
			 </td>
                     		  
                        
                        </tr>
						
						
						
						
						
						 <tr >
						 <td width="25%" align="right" ><div align="right">Whether Dependent of Service/Ex-Service Man:</div></td>
            			 <td width="25%"><div align="left"><nested:radio disabled="true" property="applicantDetails.personalData.exservice" name="onlineApplicationForm" value="true" ><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
						<nested:radio disabled="true" property="applicantDetails.personalData.exservice" name="onlineApplicationForm" value="false" ><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
						<a href="#" title="Select Whether Your family are Dependent of Service/Ex-Service Man" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           				
						</div>					
											
						</td>
						
						
                           <td  align="right" ><div align="right">If Yes Achievement In Sports:</div></td>
	                      <td align="left"><div align="left">
	                      <input type="hidden" id="sportsParticipate" name="sportsParticipate" value='<bean:write name="onlineApplicationForm" property="sportsParticipate"/>'/>
                         <nested:select disabled="true" property="applicantDetails.personalData.sportsParticipate" name="onlineApplicationForm" styleClass="dropdown" styleId="sportsParticipate1">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<html:option value="1 Prize">1 Prize</html:option>
											<html:option value="2 Prize">2 Prize</html:option>
											<html:option value="3 Prize">3 Prize</html:option>
											<html:option value="Participated">Participated</html:option>
											<html:option value="None">None</html:option>
											
						</nested:select>
						<a href="#" title="Select You Achieve any thing in Sports" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           				
						 </div></td>
                        
                       
                          
                        </tr>
                 
                 
                 
                 
                 
                 
                 <logic:equal name="onlineApplicationForm" property="programTypeId" value="1">
				<tr>									
						    <%String dynaStyle5="display:none;"; %>
							<logic:equal value="true" property="ncccertificate" name="onlineApplicationForm">
							<%dynaStyle5="display:block;"; %>
							</logic:equal>
					        <td align="right" >Holder of Plus Two Level NCC Certificate:</td>
					        
                           <td ><div>
                           <input type="hidden" id="hiddenncccertificate" name="hiddenncccertificate" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.ncccertificate"/>'/>
                           <nested:radio disabled="true" property="applicantDetails.personalData.ncccertificate" name="onlineApplicationForm" value="true" onclick="showNcccertificate()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
							<nested:radio disabled="true" property="applicantDetails.personalData.ncccertificate" name="onlineApplicationForm" value="false" onclick="hideNcccertificate()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
							<a href="#" title="Holder of Plus Two Level NCC Certificate" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           				
							</div>
							<div id="ncccertificate_description" style="<%=dynaStyle5 %>">
							Grade of Certificate:
	                       <input type="hidden" id="nccgrade" name="nccgrade" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.nccgrades"/>'/>
                          <nested:select disabled="true" property="applicantDetails.personalData.nccgrades" styleClass="dropdownsmall" name="onlineApplicationForm" styleId="nccgrade">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<html:option value="A">A</html:option>
											<html:option value="B">B</html:option>
											<html:option value="C">C</html:option>
							</nested:select>
							
							</div>
							</td>
									
									
									
									
						<td align="right" >Holder of Plus Two Level NSS Certificate:</td>
                         <td ><div align="left"><nested:radio disabled="true" property="applicantDetails.personalData.nsscertificate" name="onlineApplicationForm" value="true" ><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
						<nested:radio disabled="true" property="applicantDetails.personalData.nsscertificate" name="onlineApplicationForm" value="false" ><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
							<a href="#" title="Holder of Plus Two Level NSS Certificate" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           				
						</div>
						</td>
											
											
                        	</tr>
                        
                        
                        </logic:equal>
                        
						
						
						
						
					<logic:equal name="onlineApplicationForm" property="programTypeId" value="2">
	
					<tr>									
						    <%String dynaStyle6="display:none;"; %>
							<logic:equal value="true" property="ncccertificate" name="onlineApplicationForm">
							<%dynaStyle6="display:block;"; %>
							</logic:equal>
					        <td align="right" >Holder of UG Level NCC Certificate:</td>
                           <td ><div align="left">
                           <input type="hidden" id="hiddenncccertificate" name="hiddenncccertificate" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.ncccertificate"/>'/>
                           <nested:radio disabled="true" property="applicantDetails.personalData.ncccertificate" name="onlineApplicationForm" value="true" onclick="showNcccertificate()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
						    <nested:radio disabled="true" property="applicantDetails.personalData.ncccertificate" name="onlineApplicationForm" value="false" onclick="hideNcccertificate()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
							<a href="#" title="Select Holder of UG Level NCC Certificate" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           
							</div>
							<div id="ncccertificate_description" style="<%=dynaStyle6 %>">
							Grade of Certificate:
	                      <input type="hidden" id="nccgrade" name="nccgrade" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.nccgrades"/>'/>
                          <nested:select disabled="true" property="applicantDetails.personalData.nccgrades" name="onlineApplicationForm" styleClass="dropdownsmall" styleId="nccgrade">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<html:option value="A">A</html:option>
											<html:option value="B">B</html:option>
											<html:option value="C">C</html:option>
											
						 </nested:select>
						 
						 </div>
						 
						 </td>
									
							<td align="right" >Holder of UG Level NSS Certificate:</td>
                           	<td ><div align="left"><nested:radio disabled="true" property="applicantDetails.personalData.nsscertificate" name="onlineApplicationForm" value="true" ><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
							<nested:radio disabled="true" property="applicantDetails.personalData.nsscertificate" name="onlineApplicationForm" value="false" ><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
							<a href="#" title="Select Holder of UG Level NSS Certificate" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           
							</div>				
											
							</td>
                        </tr>
                        
                        </logic:equal>
						
										
							
                        	
		   
		   <!--  this one we are not using -->
		    <tr style="display: none;">
                           				
					<%String dynaStyle3="display:none;"; %>
					<logic:equal value="true" property="sportsPerson" name="onlineApplicationForm">
					<%dynaStyle3="display:block;"; %>
					</logic:equal>
					<td  align="right"><bean:message key="knowledgepro.applicationform.sports.label"/> </td>
                           
                   	<td ><div align="left">
                    <input type="hidden" id="hiddenSportsPerson" name="hiddenSportsPerson" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.sportsPerson"/>'/>
                    <nested:radio disabled="true" property="applicantDetails.personalData.sportsPerson" styleId="sportsPersonYes" name="onlineApplicationForm" value="true" onclick="showSportsDescription()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
					<nested:radio disabled="true" property="applicantDetails.personalData.sportsPerson" styleId="sportsPersonNo" name="onlineApplicationForm" value="false" onclick="hideSportsDescription()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
					<a href="#" title="Select Yes If you are a Sports Person" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           
					</div>
					<div align="left" id="sports_description" style="<%=dynaStyle3 %>">
					<nested:text readonly="true" styleId="sportsDescription" property="applicantDetails.personalData.sportsDescription" name="onlineApplicationForm"  maxlength="80" styleClass="textbox" ></nested:text>
					</div>	
								
					</td>		
											
											
           </tr>
					
		  
        </table>
        </td>
      </tr>
      
   
   
   
    <tr ><td height="30"></td></tr>
    
    <%-- 
  <tr>
   
    <td width="100%">
    <table align="center" width="40%" border="0" style="border-collapse:collapse">
      <tr>
        <td height="30" align="center" class="subheading">Miscellaneous Details</td>
      </tr>
    </table>
    </td>
  </tr>
  
 	

  
      <tr>
        <td>
        <table width="100%"  align="center" cellpadding="4" class="subtable"  >
        
         <tr>
              <td align="right" width="20%"><div align="right">Is Hostel Admission Required: </div></td>
             
              <td width="30%" align="left"><div align="left">
			  <nested:radio disabled="true" styleId="hosteladmissiondescription" name="onlineApplicationForm" property="applicantDetails.personalData.hosteladmission" value="true" onclick="showHostelAdmissionDescription()"><bean:message key="knowledgepro.applicationform.yes.label"/></nested:radio>
			  <nested:radio disabled="true" styleId="hosteladmissiondescription1" name="onlineApplicationForm" property="applicantDetails.personalData.hosteladmission" value="false" onclick="hideHostelAdmissionDescription();hideHostelAdmissionDescription1()"><bean:message key="knowledgepro.applicationform.No.label"/></nested:radio>
			  <a href="#" title="Select Yes if you take Hostel Admission" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           
			  </div>
			 								
			  <div align="left" id="hosteladmission_description" style="display: none">
												
			 <html:checkbox disabled="true" name="onlineApplicationForm" property="hostelcheck" styleId="hostelcheck" /><h4>I confirm the truth of all statements made by me in this application, and agree to
				all of the terms, conditions of hostel</h4>
												
			 </div>
			
			
			 
			</td>
			
			<td width="20%">&nbsp;</td>
            <td width="30%">&nbsp;</td>
       </tr>
       
          
          
          <tr>
            <td width="20%">&nbsp;</td>
            <td width="30%">&nbsp;</td>
            <td width="20%">&nbsp;</td>
            <td width="30%">&nbsp;</td>
          </tr>
          
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          
         
          
        </table>
        </td>
      </tr>
   
   --%>
   <tr ><td height="20"></td></tr>

  
  
  
  
  
  
 
  
  <tr>
  
  
    <td width="100%"><table align="center" width="100%" border="0" style="border-collapse:collapse">
      <tr>
        <td height="23" align="center" class="subheading">Education Qualification Details</td>
      </tr>
    </table></td>
  </tr>
  
  
 	<tr ><td height="30"></td></tr>
  
	
   
    
    
    
    <% String qualificationListSize= session.getAttribute("eduQualificationListSize").toString();  %> 
    <%dynaStyle=""; %>
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
				String collegeJsMethod="getColleges('Map"+count+"',this,"+count+");";
				//String collegeJsMethod=courseSettingsJsMethod+"getColleges('Map"+count+"',this,"+count+");";
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
			
			
			
			 <!-- doc name -->
		 <tr>
         <td ><table align="center" width="100%" border="0" style="border-collapse:collapse">
         <tr>
         <c:choose>
				
				<c:when test="${qualDoc.docTypeId==6}">
				<td height="23" align="center" class="subheading">Degree</td>
       
				</c:when>
				<c:otherwise>
				<td height="23" align="center" class="subheading"><nested:write property="docName" name="qualDoc"/></td>
       
				</c:otherwise>
		</c:choose>
        </tr>
       </table>
       </td>
  		</tr>	
			
	<tr ><td >	
			
		<bean:define id="countIndex" name="qualDoc" property="countId"></bean:define>
		<input type="hidden" id="countID" name="countID" >
 
 
    
   <table   align="center" cellpadding="4" class="subtable"  >
        	 
		
		 
         
         
         <!-- raghu exam name -->	
         <logic:equal value="true" name="qualDoc" property="examConfigured">
		 <html:hidden property="isExamConfigured" styleId="<%=isExamConfigured %>" name="onlineApplicationForm" value="true"/>		
          
          
           <tr>
           <td align="right" width="40%">Exam Name<span class="Mandatory">*</span></td>
			
			<td width="50%">
					
				<c:set var="dexmid"><%=dynaExamId %></c:set>
				<nested:select disabled="true" property="selectedExamId" styleClass="dropdown" styleId='<%=dynaExamId %>'>
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
		
		
		<!-- raghu university/board name -->	
		
        
		    <tr>
			<td align="right"  width="40%">
			<bean:message key="knowledgepro.admission.universityBoard" /><span class="Mandatory">*</span></td>
           
            
            <td width="50%"> 
            <div align="left">
				<table width="100%" align="left" border="0" cellpadding="0" cellspacing="0">
                <tr>
				<td align="left"><div align="left">
               <c:set var="dunid"><%=dynaid %></c:set>
               <nested:select disabled="true" property="universityId" styleId="<%=dynaid %>" styleClass="dropdown" onchange="<%=showhide %>">
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
					</div>
            	</td>
				</tr>
				<tr>
					
                  <td align="left">
					<logic:equal value="Other" name="qualDoc" property="universityId">
                	<%dynaStyle="display: block;" ;%>
					</logic:equal>
					<logic:notEqual value="Other" name="qualDoc" property="universityId">
                  	<%dynaStyle="display: none;" ;%>
					</logic:notEqual>
					<div align="left" >
  					<nested:text readonly="true" styleClass="textbox" property="universityOthers" size="10" maxlength="50" style='<%=dynaStyle %>' styleId='<%=dynarow1 %>'></nested:text>
                	 </div>
                  </td>
                  
                </tr>
			</table>
			</div>
			
			</td>
			
			
			
			</tr>
			
			
        
        <!-- raghu institution name -->	
		
	
     <tr>
		   <td align="right" width="40%">
			<bean:message key="knowledgepro.admission.instituteName" /><span class="Mandatory">*</span>
			</td>
            
            <td >
            <div align="left">
            <table align="left" width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
				<td align="left" >
				<div align="left">
				<c:set var="dinid"><%=instituteId %></c:set>
                <c:set var="temp"><nested:write property="universityId"/></c:set>
	                <nested:select disabled="true" property="institutionId" styleClass="dropdown" styleId='<%=instituteId %>' onchange='<%=instituteJsMethod %>' >
					<option value="">-Select-</option>
						<c:set var="tempKey">Map<c:out value="${count}"/></c:set>	
						<c:if test="${temp != null && temp != '' && temp != 'Other'}">
                             <c:set var="Map" value="${baseActionForm.collectionMap[tempKey]}"/>
							<c:if test="${Map!=null}">
							<html:optionsCollection name="Map" label="value" value="key"/>
							</c:if>
						</c:if>
						<logic:notEmpty property="instituteList" name="qualDoc">
								<nested:optionsCollection property="instituteList" name="qualDoc" label="name" value="id"/>
						</logic:notEmpty>
						
						<html:option value="Other">Other</html:option>
					</nested:select>
					<script type="text/javascript">
					var inId= '<nested:write property="institutionId"/>';
					document.getElementById("<c:out value='${dinid}'/>").value = inId;
					</script>
					</div>
	             </td>
				</tr>
			
				 <tr >
                  <td align="left">
					<logic:equal value="Other" name="qualDoc" property="institutionId">
                  	<%dynaStyle="display: block;" ;%>
					</logic:equal>
					<logic:notEqual value="Other" name="qualDoc" property="institutionId">
                  	<%dynaStyle="display: none;" ;%>
					</logic:notEqual>
					<div align="left">
					<nested:text readonly="true" styleClass="textbox" property="otherInstitute" size="10" maxlength="50" style='<%=dynaStyle %>' styleId='<%=dynarow2 %>'></nested:text>
                  	</div>
                  </td>
                </tr>
            </table>
            </div>
			</td>
	</tr>
	
	
		
         <!-- raghu edn state name -->	
		
	
    <tr>
    
		<td align="right" width="40%"><bean:message key="admissionForm.education.State.label"/><span class="Mandatory">*</span></td>
        
        <td >
			<c:set var="dstateid"><%=dynaStateId %></c:set>
			<nested:select disabled="true" property="stateId" styleClass="dropdown" styleId='<%=dynaStateId %>'>
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
          
          
            
		<!-- raghu passing year  details name -->	
		
      <tr>
    		<td align="right" width="40%">
    		<bean:message key="knowledgepro.admission.passingYear"/><span class="Mandatory">*</span></td>
           
            <td >
			<c:set var="dyopid"><%=dynaYearId %></c:set>
			<nested:select disabled="true" property="yearPassing" styleId='<%=dynaYearId %>' styleClass="dropdown">
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
       
       
       <!-- raghu passing  month details name -->	
          
        <tr>
      	    <td align="right" width="40%"><bean:message key="knowledgepro.applicationform.passingmonth"/><span class="Mandatory">*</span></td>
           
            <td >
			<c:set var="dmonid"><%=dynamonthId %></c:set>
			<nested:select disabled="true" property="monthPassing" styleId='<%=dynamonthId %>' styleClass="dropdown">
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
          
          
          
          <!-- raghu exam attempts details name -->	
		
		
		
		<c:choose>
				
		<c:when test="${qualDoc.docTypeId==2}">
				
		<!-- for tenth no need -->
       </c:when>
		<c:otherwise>
		<tr>
      		 <td align="right" width="40%">
      		 	<bean:message key="knowledgepro.admission.attempts"/><span class="Mandatory">*</span></td>
        	
        	<td >
				<c:set var="dAttemptid"><%=dynaAttemptId %></c:set>
                <nested:select disabled="true" property="noOfAttempts" styleId='<%=dynaAttemptId %>' styleClass="dropdown">
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
				
				<a href="#" title="Select no of attempts in exam" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        
           </td>
           
		</tr>
		
		</c:otherwise>
		</c:choose>
		
		
       
		<!-- raghu exam pre register no details name -->	 
		<logic:equal value="true" name="qualDoc" property="lastExam">
		 <tr>
	        	<td align="right" width="40%"><bean:message key="knowledgepro.applicationform.prevregno.label"/><span class="Mandatory">*</span></td>
	            <td  ><nested:text readonly="true" styleClass="textbox" property="previousRegNo" size="10" maxlength="15"/></td>
         </tr>
         </logic:equal>
		 
          
          
          <!-- raghu cosolidate marks details name -->	
		
		<nested:equal value="true" property="consolidated" name="qualDoc">
			<%-- 
			<tr>
				<td align="right" width="40%">
					<bean:message key="knowledgepro.admission.marksObtained" /><span class="Mandatory">*</span>
			    </td>
			    
          	 	<td>
              		<nested:text readonly="true" styleClass="textbox" property="marksObtained" size="5" maxlength="8" ></nested:text>
              </td>
            </tr>
            
            <tr>
            <td align="right" width="40%">
				<bean:message key="knowledgepro.admission.maxMark"/><span class="Mandatory">*</span></td>
          	 
          	 <td>
              	<nested:text readonly="true" styleClass="textbox"  property="totalMarks" size="5" maxlength="8" ></nested:text>
           	</td>
           </tr>
           --%>
           <tr>
            <td align="right" width="40%">
				<bean:message key="knowledgepro.admission.percentage"/><span class="Mandatory">*</span></td>
          	 
          	 <td>
              	<nested:text readonly="true" property="percentage" styleId='<%=maxMarks %>' styleClass="textbox" size="5" maxlength="8" ></nested:text>
              	<a href="#" title="Enter your percentage" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
        
           	</td>
           </tr>
           
         </nested:equal>
         
         <tr ><td colspan="2" height="20"></td></tr>
  
         
         <nested:equal value="false"  property="consolidated" name="qualDoc">
         
         <!-- raghu semster marks details name -->	
		
				<nested:equal value="true"  property="semesterWise" name="qualDoc">
					
					<tr >
          		  	 <td align="center" colspan="2"><span class="Mandatory">*</span><a href="#" style=" color:#FF0000;font-size:large;text-decoration:none; font-family:Arial, Helvetica, sans-serif" onclick="detailSemesterSubmit('<%=countIndex %>')"><bean:message key="knowledgepro.applicationform.semsestermark.link"/></a></td>
					</tr>
				
				</nested:equal>
				
				<!-- raghu doc marks details name -->	
		
				<nested:equal value="false"  property="semesterWise" name="qualDoc">
				<!-- raghu -->
				<c:choose>
				 
				<c:when  test="${qualDoc.docTypeId==6}">
				<tr >
				<td  align="center" colspan="2"><div align="center"><span class="Mandatory">*</span><a href="#" style=" color:#FF0000;font-size:medium;text-decoration:none; font-family:Arial, Helvetica, sans-serif" onclick="detailSubmitDegreeView('<%=countIndex %>')" >Click <bean:message key="knowledgepro.applicationform.detailmark.link"/> for DEGREE</a></div></td>
				</tr>
				</c:when>
				
				<c:when  test="${qualDoc.docTypeId==9}">
				<tr >
				<td  align="center" colspan="2"><div align="center"><span class="Mandatory">*</span><a href="#" style=" color:#FF0000;font-size:medium;text-decoration:none; font-family:Arial, Helvetica, sans-serif" onclick="detailSubmitClass12View('<%=countIndex %>')" >Click <bean:message key="knowledgepro.applicationform.detailmark.link"/> for CLASS 12</a></div></td>
				</tr>
				</c:when>
				
				<c:otherwise>
	          		<tr >
	          		   <td  align="center" colspan="2"><div align="center"><span class="Mandatory">*</span><a href="#" style=" color:#FF0000;font-size:medium;text-decoration:none; font-family:Arial, Helvetica, sans-serif" onclick="detailSubmitView('<%=countIndex %>')" >Click <bean:message key="knowledgepro.applicationform.detailmark.link"/></a></div></td>
					</tr>
				</c:otherwise>
				
				</c:choose>
				
				</nested:equal>
				
			</nested:equal>
			
         
        </table>
        </td>
      </tr>
      
  
    <tr ><td height="30"></td></tr>
    
    </nested:iterate>
   </logic:notEmpty>
   
    
    
  
  
  
 
      <tr>
    <td><table   align="center" cellpadding="4" class="subtable"  >
        
  	 <tr>
		<td align="right" width="50%">Backlogs in previous semesters/years to be cleared:</td>
		<td  width="50%" >
			<html:radio disabled="true" property="backLogs" name="onlineApplicationForm" styleId="backLogs" value="true"><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
			<html:radio disabled="true" property="backLogs" name="onlineApplicationForm" styleId="backLogs" value="false"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio>
		</td>
  	</tr>
  	
  	
	
	
	<tr>
		<td align="right">Whether SAY(Save A Year) Pass Out in same academic year or not:</td>
		
		<td >
		<html:radio disabled="true" property="isSaypass" name="onlineApplicationForm" styleId="isSaypass" value="true"><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
		<html:radio disabled="true" property="isSaypass" name="onlineApplicationForm" styleId="isSaypass" value="false"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio>
		</td>
	</tr>
	 
	<logic:equal name="onlineApplicationForm" property="programTypeId" value="1">
	
	</logic:equal>		                        
	
    
    
                         
    <%-- 
    <c:if test="${onlineApplicationForm.programTypeId != null && onlineApplicationForm.programTypeId== '2'}">
	</c:if>   
	--%>
	
	<logic:equal name="onlineApplicationForm" property="programTypeId" value="2">
			                        
	<tr>
         <td align="right">
		<span class="Mandatory">*</span>Qualifying Under Graduate Program:
		</td>
        <td >
		<nested:select disabled="true" property="applicantDetails.personalData.ugcourse" name="onlineApplicationForm" styleClass="dropdownmedium" styleId="ugcourse">
		<option value=""><bean:message key="knowledgepro.admin.select"/></option>
		<html:optionsCollection property="ugcourseList" name="onlineApplicationForm" label="name" value="id"/>
		</nested:select>
		</td>
    </tr>
    
    </logic:equal>
    
    <logic:equal name="onlineApplicationForm" property="programTypeId" value="1">
	
	<tr>
         <td align="right">
		Stream Under Class 12:<span class="Mandatory">*</span>
		</td>
        <td >
		<nested:select disabled="true" property="applicantDetails.personalData.stream" name="onlineApplicationForm" styleClass="dropdownmedium" styleId="stream">
		<option value=""><bean:message key="knowledgepro.admin.select"/></option>
		<html:optionsCollection property="streamMap" name="onlineApplicationForm" label="value" value="key"/>
		</nested:select>
		 <a href="#" title="Select Stream in Class 12" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           
		</td>
    </tr>
	</logic:equal>	
	
     <logic:equal name="onlineApplicationForm" property="programTypeId" value="1">
			<tr>
            <td align="right"><bean:message key="knowledgepro.applicationform.secLang.label"/><span class="Mandatory">*</span> </td>
            <td><div align="left">
            <html:select disabled="true" property="applicantDetails.personalData.secondLanguage" name="onlineApplicationForm" styleClass="dropdownmedium" styleId="secondLanguage">
			<html:option value="">
			<bean:message key="knowledgepro.admin.select" />
			</html:option>
			<html:optionsCollection property="secondLanguageList" label="value" value="value" name="onlineApplicationForm"/>
			</html:select>
			 <a href="#" title="Select Your Language" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            </div>
			</td>
			
            <td> </td>
            <td></td>
                          
          </tr>
	</logic:equal>
	
	
                           
    </table>
   
    </td>
  </tr>
   
   
   
   
   
   
   
   
   <!-- raghu student  entrance details code start here -->	
				
				
								
		<logic:equal value="true" property="displayEntranceDetails" name="onlineApplicationForm">								
										
			 
	<tr>
   
    <td width="100%"><table align="center" width="40%" border="0" style="border-collapse:collapse">
      <tr>
        <td height="23" align="center" class="subheading"><bean:message key="admissionForm.education.entrancedetails.label"/></td>
      </tr>
    </table></td>
  </tr>					
		
		<tr>
        <td>
        
        
        <table align="center" cellpadding="4" class="subtable"  >
     	<tr >
            	<td align="right" width="40%" height="25" ><div align="right"><bean:message key="admissionForm.education.entrance.label"/></div></td>
				<td width="50%" height="25" ><div align="left">
				
				<nested:select disabled="true" property="applicantDetails.entranceDetail.entranceId" styleClass="dropdown" name="onlineApplicationForm">
				<html:option value="">-Select-</html:option>
				<logic:notEmpty property="entranceList" name="onlineApplicationForm">
				<html:optionsCollection property="entranceList" name="onlineApplicationForm" label="name" value="id"/>
				</logic:notEmpty>
										
			   </nested:select>
			   </div></td>
				</tr>
				
				<tr>
				<td align="right" width="40%" height="25" ><div align="right"><bean:message key="knowledgepro.admission.totalMarks"/>:</div></td>
				<td width="50%" height="25" ><div align="left"><nested:text readonly="true" property="applicantDetails.entranceDetail.totalMarks" styleClass="textbox"  name="onlineApplicationForm"  maxlength="8"></nested:text></div></td>
				</tr>
				
				<tr>
				<td align="right" width="40%" height="25" ><div align="right"><bean:message key="admissionForm.education.markObtained.label"/>:</div></td>
				<td width="50%" height="25" ><div align="left"><nested:text readonly="true" property="applicantDetails.entranceDetail.marksObtained" styleClass="textbox"  name="onlineApplicationForm"  maxlength="8"></nested:text></div></td>
           		</tr>
           		
				<tr>
            	<td align="right" width="40%" height="25" ><div align="right"><bean:message key="knowledgepro.applicationform.prereq.roll.label"/></div></td>
				<td width="50%" height="25" ><div align="left"><nested:text readonly="true" property="applicantDetails.entranceDetail.entranceRollNo" styleClass="textbox" name="onlineApplicationForm"  maxlength="25"></nested:text></div></td>
				</tr>
										
										
				<tr>
					<td align="right" width="40%" height="25" ><div align="right"><bean:message key="knowledgepro.applicationform.passingmonth"/>:</div></td>
					<td width="50%" height="25" ><div align="left">
					<nested:select disabled="true" property="applicantDetails.entranceDetail.monthPassing"  styleClass="dropdown"  name="onlineApplicationForm">
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
				</div>
				</td>
				</tr>
										
				<tr>
					<td align="right" width="40%" height="25" ><div align="right"><bean:message key="knowledgepro.admission.passingYear"/>:</div></td>
					<td width="50%" height="25" ><div align="left">
					<nested:select disabled="true" property="applicantDetails.entranceDetail.yearPassing" styleId='entranceyear' styleClass="dropdown"  name="onlineApplicationForm">
					<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
				    <cms:renderYear normalYear="true"></cms:renderYear>
					</nested:select>
					<script type="text/javascript">
							var entyopid= '<nested:write property="applicantDetails.entranceDetail.yearPassing"  name="onlineApplicationForm"/>';
							document.getElementById("entranceyear").value = entyopid;
					</script>
					</div>
					</td>
            	</tr>
            							
            	
			</table>
								
			</td>
			</tr>							
								
		</logic:equal>						
				
				
				<!-- raghu student  entrance details code over here -->	
		
   
   
   
   <tr ><td height="30"></td></tr>
   
   
  
  
  
  
   <tr><td align="center" class="subheading">Photo View</td></tr>
   
   
  
      <tr>
        <td>
        <table  border="0" cellpadding="4"  align="center" class="subtable w"  >
        
        						<nested:iterate name="onlineApplicationForm" property="applicantDetails.editDocuments" indexId="count" id="docList" type="com.kp.cms.to.admin.ApplnDocTO" >
								<nested:equal value="true" property="photo" name="docList">
			
          						<tr height="80">
								<td height="25" width="25%"  align="center"><nested:write name="docList" property="printName" /></td>
														
								<td   >
								
							   <nested:equal value="true" property="documentPresent" name="docList">
								<a	href="javascript:downloadFile('<nested:write name="docList" property="id"/>')"><bean:message key="knowledgepro.view.image" /></a>
								</nested:equal>
								</td>
														
								<td >
								 <img src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/PhotoServlet") %>'  height="150px" width="150px" />
          
								
								</td>
		 						</tr>
		 
		 						</nested:equal>	
								</nested:iterate>
		 
		 
		
		 
         
		 
        </table>
        </td>
      </tr>
   
  
  <tr ><td height="20"></td></tr>
  
   <tr>
  <td  width="100%" align="center">
  
  	 <html:button property="" onclick="submitAdmissionForm('backToConfirmPage')" styleClass="cntbtn" value="Back"></html:button>
 
  
  </td>
  </tr>
  
  <tr ><td height="30"></td></tr>
  
</table>
 		
 		
 			
 			
	
	
	<script language="JavaScript" src="js/admission/OnlineDetailsPersonalInfo.js"></script>
	<script language="JavaScript" src="js/admission/OnlineDetailsEducationInfo.js"></script>	
	
	
	<script type="text/javascript">

	onLoadAddrCheck();
	
	var relgId = document.getElementById("religionType").value;
	if(relgId != null && relgId.length != 0) {
		document.getElementById("religions").value = relgId;
	}

	//this for parish and diosis
	if(relgId != null && relgId=='3') {
		document.getElementById("dioces_description").style.display = "block";
		document.getElementById("parish_description").style.display = "block";
		
	}
	//alert(document.getElementById("hosteladmissiondescription").checked);
	//this for hostel admission
	//if(document.getElementById("hosteladmissiondescription").checked) {
		//document.getElementById("hosteladmission_description").style.display = "block";
		//document.getElementById("hostelcheck").checked="true";
		
	//}
	
	
	
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
	


	
		
	var bState =  document.getElementById("birthState1").value;
	if(bState!=null && bState.length != 0){
		document.getElementById("birthState").value=bState;
	}
	
	
	var tempCategoryId = document.getElementById("tempResidentCategory").value;
	//alert(tempCategoryId);
	if(tempCategoryId!=null && tempCategoryId.length!=0){
	//	alert("Inside");
		document.getElementById("residentCategory").value=tempCategoryId;
	}
	
	
	
	
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

	var arts =  document.getElementById("arts1").value;
			if(arts!=""){
				document.getElementById("artsParticipate1").disabled=false;
			}else{
				document.getElementById("artsParticipate1").disabled=true;
			}
			
		

	   	var sports =  document.getElementById("sports1").value;
			if(sports!=""){
				document.getElementById("sportsParticipate1").disabled=false;
			}else{
				document.getElementById("sportsParticipate1").disabled=true;
			}
			

		var showNcc = document.getElementById("hiddenncccertificate").value;
		if(showNcc != null && showNcc.length != 0 && showNcc=='true') {
			showNcccertificate();	
		}else{
			hideNcccertificate();
		}


		


		
	});




	if(document.getElementById("motherOccupation").value=='' || document.getElementById("motherOccupation").value== null){
		
		document.getElementById("displayMotherOccupation").style.display = "none";
	}else{
		if(document.getElementById("motherOccupation").value=='Other'){
			document.getElementById("displayMotherOccupation").style.display = "block";
		}else{
			document.getElementById("displayMotherOccupation").style.display = "none";
		}
		
	}
	
	if(document.getElementById("hiddenFatherOccupationId").value==null  || document.getElementById("hiddenFatherOccupationId").value==''){
		document.getElementById("displayFatherOccupation").style.display = "none";
	}else{
		if(document.getElementById("hiddenFatherOccupationId").value=='Other'){
			document.getElementById("displayFatherOccupation").style.display = "block";
		}else{
			document.getElementById("displayFatherOccupation").style.display = "none";
		}
	}
	
	function displayOtherForMother(occpation){
		if(document.getElementById("motherOccupation").value=="Other"){
			document.getElementById("displayMotherOccupation").style.display = "block";
		}else{
			document.getElementById("displayMotherOccupation").style.display = "none";
			document.getElementById("otherOccupationMother").value = "";
		}
	}

	
	function displayOtherForFather(occpation){
		if(document.getElementById("fatherOccupation").value=="Other"){
			document.getElementById("displayFatherOccupation").style.display = "block";
		}else{
			document.getElementById("displayFatherOccupation").style.display = "none";
			document.getElementById("otherOccupationFather").value = "";
		}
	}
		
	
	
</script>