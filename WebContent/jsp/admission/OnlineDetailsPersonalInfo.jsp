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
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
 
<script language="JavaScript" src="js/admission/admissionform.js"></script>

<link rel="stylesheet" href="css/admission/css/stylesheet-pure-css.css"/>  
 
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




<html:hidden property="focusValue" styleId="focusValue" name="onlineApplicationForm"/>
<html:hidden property="onlineApply" styleId="onlineApply" name="onlineApplicationForm"/>
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
 	<html:hidden property="checkRCSCId" styleId="checkRCSCId" />
 	<html:hidden property="categoryRCSCId" styleId="categoryRCSCId" />
	<html:hidden property="dateExpired" name="onlineApplicationForm" styleId="dateExpired"/>
 		
<table width="80%" style="background-color: #F0F8FF" align="center" border="0" cellpadding="0" cellspacing="0">
   
	
	<%-- 
	<tr>
    <td >
	<table width="100%" align="center" border="0">
	<tr>
	<td align="center">
	<div id="nav-menu">
	<ul>
	<li class="ac">Terms & Conditions</li>
	<li class="ac">Payment</li>
     <li class="current">Personal Details</li>
     <li class="">Course Options</li>
     <li class="">Education Dtails</li>
	 <li  class="">Upload Photo</li>
  	 </ul>
   	</div>
  	 </td>
   	</tr>
    </table>
    </td>
  </tr>
  --%>
  <tr ><td height="20"></td></tr>
   
   <!-- errors display -->
  <tr >
	<td  align="center">
							<div id="errorMessage" align="center">
							<FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"	property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages>
							</FONT>
							</div>
							<div id="errorMessage1" style="font-size: 11px; color: red"></div>
	</td>
	</tr>
	
 
   
  
      
   
  
 		<tr ><td height="30"></td></tr>
  
	
	
  <tr>
  
  
    <td width="100%"><table align="center" width="100%" border="0" style="border-collapse:collapse">
      <tr>
        <td height="30" align="center" class="subheading">Student Details</td>
      </tr>
    </table></td>
  </tr>
  
  

      <tr>
        <td>
        <table  width="100%" border="0" cellpadding="4"  align="center" class="subtable"  >
         
         
          <tr>
           
            <td width="25%" align="right"><bean:message key="knowledgepro.applicationform.candidateName"/><span class="Mandatory">*</span>
            <br/>(as printed on CLASS X / SSLC Records)
            </td>
            <td width="25%" valign="top">
            <nested:text readonly="false" property="applicantDetails.personalData.firstName" styleId="firstNameId" name="onlineApplicationForm"  maxlength="90" styleClass="textbox"></nested:text>						
            
            <a href="#" title="Enter Your Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
             </td>
           
             
            <td width="25%" align="right"><bean:message key="admissionForm.studentinfo.bloodgroup.label"/><span class="Mandatory">*</span>
           
             </td>
            <td width="25%">
			<input type="hidden" id="BGType" name="BGType" value='<bean:write name="onlineApplicationForm" property="bloodGroup"/>'/>
                         <nested:select property="applicantDetails.personalData.bloodGroup" name="onlineApplicationForm" styleClass="dropdown" styleId="bgType">
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
             <nested:text readonly="false" name="onlineApplicationForm" property="applicantDetails.personalData.dob" styleId="dateOfBirth"  maxlength="11" styleClass="textbox"></nested:text>
							
           <a href="#" title="Select Your Date of Birth" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            </td>
            
            
             <td align="right"><bean:message key="admissionForm.studentinfo.residentcatg.label2"/><span class="Mandatory">*</span>
             
             </td>
            <td><div align="left">
            <input type="hidden" id="tempResidentCategory" value="<nested:write property="applicantDetails.personalData.residentCategory" name="onlineApplicationForm" />">
			<nested:select property="applicantDetails.personalData.residentCategory" name="onlineApplicationForm" styleClass="dropdown" styleId="residentCategory" disabled="false">
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
             <fieldset style="border: 0px">
              
             <nested:radio disabled="false" property="applicantDetails.personalData.gender" styleId="MALE" name="onlineApplicationForm" value="MALE"></nested:radio>
             <label for="MALE"><span><span></span></span><bean:message key="admissionForm.studentinfo.sex.male.text"/></label> 
			
			
			 <nested:radio disabled="false" property="applicantDetails.personalData.gender" name="onlineApplicationForm" styleId="FEMALE" value="FEMALE"></nested:radio>
			 <label for="FEMALE"><span><span></span></span><bean:message key="admissionForm.studentinfo.sex.female.text"/></label> 
			 <a href="#" title="Select Your Gender" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
			 </fieldset>	
			 <fieldset style="border: 0px">	
			 <nested:radio disabled="true" property="applicantDetails.personalData.gender" name="onlineApplicationForm" styleId="Trans Gender" value="TRANS GENDER"></nested:radio>
			 <label for="Trans Gender"><span><span></span></span>Transgender</label> 
			 </fieldset>		
			</td>
            
            
             <td align="right"><bean:message key="admissionForm.studentinfo.nationality.label"/><span class="Mandatory">*</span></td>
            <td>
            <div align="left">
            <input type="hidden" id="nationalityhidden" name="nationality" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.nationality"/>"/>
                          
            <nested:select property="applicantDetails.personalData.nationality" styleClass="dropdown" styleId="nationality" name="onlineApplicationForm">
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
          
          
          
          <td align="right"><bean:message key="admissionForm.studentinfo.phone.label"/></td>
            <td>
            
            <nested:text styleClass="textboxsmall" property="applicantDetails.personalData.phNo2" name="onlineApplicationForm" styleId="applicantphNo2" maxlength="7" size="7" onkeypress="return isNumberKey(event)" />
			
           <nested:text styleClass="textboxmedium" property="applicantDetails.personalData.phNo3" name="onlineApplicationForm" styleId="applicantphNo3" maxlength="10" size="10" onkeypress="return isNumberKey(event)"/>
			 <a href="#" title="Enter Your Phone Code and Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            </td>
          
          
           
            <td align="right"> <bean:message key="admissionForm.studentinfo.birthcountry.label"/><span class="Mandatory">*</span></td>
            
            
            <td>
			<div align="left">
 				<input type="hidden" id="birthCountryhidden" name="birthCountry" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.birthCountry"/>"/>
				<nested:select property="applicantDetails.personalData.birthCountry" name="onlineApplicationForm" styleClass="dropdown" styleId="birthCountry" onchange="getStates(this.value,'birthState');">
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
			
            <td align="right"><bean:message key="admissionForm.studentinfo.birthstate.label"/><span class="Mandatory">*</span></td>
            
            
            <td>
             
           
            
           
            <div align="left">
            <input type="hidden" id="birthState1" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.birthState"/>">
            <nested:select property="applicantDetails.personalData.birthState" name="onlineApplicationForm"	styleId="birthState" styleClass="dropdown"
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
            <nested:text property="applicantDetails.personalData.stateOthers"  name="onlineApplicationForm"	maxlength="30" styleId="otherBirthState"
						style="<%=dynaStyle %>" styleClass="textbox" onkeypress="IsAlpha(event)"></nested:text>
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
          
            <td align="right"><bean:message key="admissionForm.studentinfo.birthplace.label"/><span class="Mandatory">*</span>
           </td>
            <td><nested:text property="applicantDetails.personalData.birthPlace"  styleId="birthPlace" name="onlineApplicationForm"  maxlength="50" styleClass="textbox" onkeypress="IsAlpha(event)"></nested:text>
             <a href="#" title="Enter Your BirthPlace" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            </td>
            
          
            
          </tr>

          <tr>
				<td align="right"><bean:message key="admissionForm.studentinfo.aadhaarNumber"/>:</td>
				<td>
					<nested:text property="applicantDetails.personalData.aadhaarCardNumber" styleId="applicantadhaarNo" name="onlineApplicationForm" styleClass="textbox"  maxlength="12" onkeypress="return isNumberKey(event)"></nested:text>
					<a href="#" title="Enter Your Aadhaar Card Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
				</td>
				<td align="right"><bean:message key="knowledgepro.applicationform.mothertongue.label"/></td>
				<td>
					<nested:select property="applicantDetails.personalData.motherTongueId" styleId="studentMotherTongueId" name="onlineApplicationForm" styleClass="dropdownmedium">
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
            <td width="25%"><nested:text property="applicantDetails.personalData.currentAddressLine1" styleId="currentAddressLine1" styleClass="textbox" name="onlineApplicationForm" maxlength="35"></nested:text>
			<a href="#" title="Enter Your House Name/House Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
			
			
            <td align="right" width="25%">Country:<span class="Mandatory">*</span></td>
            <td width="25%">
            <div align="left">
            <input type="hidden" id="currentCountryNamehidden" name="nationality" name="onlineApplicationForm" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.currentCountryId"/>"/>
				
			<nested:select property="applicantDetails.personalData.currentCountryId" name="onlineApplicationForm" styleClass="dropdown" styleId="currentCountryName" onchange="getMobileNo();getTempAddrStates(this.value,'tempAddrstate');" >
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
            <td><nested:text property="applicantDetails.personalData.currentAddressLine2" styleId="currentAddressLine2" styleClass="textbox" name="onlineApplicationForm" maxlength="40" onkeypress="IsAlpha(event)"></nested:text>
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
		
		 <nested:select property="applicantDetails.personalData.currentStateId" name="onlineApplicationForm"  styleClass="dropdown" styleId="tempAddrstate" onchange="funcOtherShowHide('tempAddrstate','otherTempAddrState');getTempAddrDistrict(this.value,'tempAddrdistrict');">
		<html:option value="">- Select -</html:option>
		
		<logic:notEmpty property="stateMap" name="onlineApplicationForm">
					<html:optionsCollection name="onlineApplicationForm" property="curAddrStateMap"
										label="value" value="key" />
									
					</logic:notEmpty>
					<html:option value="Other">Other</html:option>
		</nested:select>
		<a href="#" title="Enter Your Current State" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
		</div>
		
		
	   <div align="left"><nested:text property="applicantDetails.personalData.currentAddressStateOthers" styleClass="textbox" name="onlineApplicationForm" maxlength="30" styleId="otherTempAddrState" style="<%=dynaStyle %>" onkeypress="IsAlpha(event)"></nested:text></div>
	
	   
            
           
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
			<div align="left"><nested:select property="applicantDetails.personalData.currentDistricId" name="onlineApplicationForm" styleClass="dropdown" styleId="tempAddrdistrict" onchange="funcOtherShowHide('tempAddrdistrict','otherTempAddrDistrict');">
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
				
							
			<div align="left"><nested:text property="applicantDetails.personalData.currentAddressDistrictOthers" name="onlineApplicationForm" styleClass="textbox" size="10" maxlength="30" styleId="otherTempAddrDistrict" style="<%=dynaStyle %>" onkeypress="IsAlpha(event)"></nested:text></div>
							
			
            
            </td>
          </tr>
          
          
          <tr>
            
            <td align="right">City:<span class="Mandatory">*</span></td>
            <td><nested:text property="applicantDetails.personalData.currentCityName" styleId="currentCityName" styleClass="textbox" name="onlineApplicationForm" maxlength="30" onkeypress="IsAlpha(event)"></nested:text>
			<a href="#" title="Enter Your City Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
            
            
            <td align="right"> Pin Code:<span class="Mandatory">*</span></td>
            <td><nested:text styleClass="textbox" property="applicantDetails.personalData.currentAddressZipCode" styleId="currentAddressZipCode" name="onlineApplicationForm"  maxlength="6" onkeypress="return isNumberKey(event)"/>
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
       				 	  
            <fieldset style="border: 0px">
             <html:radio property="sameTempAddr" styleId="sameAddr" value="true" onclick="disableTempAddress();"></html:radio>
					     <label for="sameAddr"><span><span></span></span><bean:message key="knowledgepro.applicationform.yes.label"/></label> 
			
			
			 <html:radio property="sameTempAddr" styleId="DiffAddr" value="false" onclick="enableTempAddress();"></html:radio>
					<label for="DiffAddr"><span><span></span></span><bean:message key="knowledgepro.applicationform.No.label"/></label> 
			 <a href="#" title="Select Parent Address if same as Current Address " class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            </fieldset>
            
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
            <td width="25%"><nested:text property="applicantDetails.personalData.permanentAddressLine1" styleId="permanentAddressLine1" styleClass="textbox" name="onlineApplicationForm"  maxlength="35"></nested:text>
			<a href="#" title="Enter Your House Number and House Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
			
			
            <td align="right" width="25%">Country:<span class="Mandatory">*</span></td>
            <td width="25%">
            <div align="left">
            <input type="hidden" id="permanentCountryNamehidden" name="nationality" value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.permanentCountryId"/>"/>
											
			<nested:select property="applicantDetails.personalData.permanentCountryId" name="onlineApplicationForm" styleClass="dropdown" styleId="permanentCountryName" onchange="getPermAddrStates(this.value,'permAddrstate');">
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
           <nested:text property="applicantDetails.personalData.permanentAddressLine2" styleClass="textbox"  styleId="permanentAddressLine2" name="onlineApplicationForm"  maxlength="40" onkeypress="IsAlpha(event)" ></nested:text>
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
              <nested:select property="applicantDetails.personalData.permanentStateId" name="onlineApplicationForm" styleClass="dropdown" styleId="permAddrstate" onchange="funcOtherShowHide('permAddrstate','otherPermAddrState');getPermAddrDistrict(this.value,'permAddrdistrict');">
						<html:option value="">- Select -</html:option>
									
						<logic:notEmpty property="stateMap" name="onlineApplicationForm" >
						<html:optionsCollection name="onlineApplicationForm" property="perAddrStateMap" label="value" value="key" />
													
						</logic:notEmpty>
						<html:option value="Other">Other</html:option>
			</nested:select>
			<a href="#" title="Enter Your Permanenet State" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>
			<div align="left"><nested:text property="applicantDetails.personalData.permanentAddressStateOthers" name="onlineApplicationForm" styleClass="textbox" maxlength="30" styleId="otherPermAddrState" style="<%=dynaStyle %>" onkeypress="IsAlpha(event)"></nested:text>
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
                     <nested:select property="applicantDetails.personalData.permanentDistricId" name="onlineApplicationForm" styleClass="dropdown" styleId="permAddrdistrict" onchange="funcOtherShowHide('permAddrdistrict','otherPermAddrDistrict');">
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
				  <div align="left"><nested:text property="applicantDetails.personalData.permanentAddressDistrictOthers" name="onlineApplicationForm" size="10" maxlength="30" styleClass="textbox" styleId="otherPermAddrDistrict" style="<%=dynaStyle %>" onkeypress="IsAlpha(event)"></nested:text>
					</div>
            </td>
          </tr>
          
          
          <tr>
          
          <td align="right">City:<span class="Mandatory">*</span></td>
            <td>
            <nested:text property="applicantDetails.personalData.permanentCityName" styleId="permanentCityName" styleClass="textbox" name="onlineApplicationForm"  maxlength="30" onkeypress="IsAlpha(event)"></nested:text>
			<a href="#" title="Enter Your Permanent City" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</td>
            
           <td align="right"> Pin Code:<span class="Mandatory">*</span></td>
            <td><nested:text styleClass="textbox" property="applicantDetails.personalData.permanentAddressZipCode" styleId="permanentAddressZipCode" name="onlineApplicationForm"  maxlength="6" onkeypress="return isNumberKey(event)"/>
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
            				<nested:select property="applicantDetails.titleOfFather" styleId='titleOfFather' name="onlineApplicationForm" styleClass="dropdownsmall" onchange="fatherIncomeMandatory()">
							<!--<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>-->
							<html:option value="Mr">Mr.</html:option>
							<html:option value="Late">Late.</html:option>
							</nested:select>
							
			<nested:text property="applicantDetails.personalData.fatherName" styleId="fatherName" name="onlineApplicationForm" styleClass="textboxmedium" maxlength="50" onkeypress="IsAlphaDot(event)"></nested:text>
			<a href="#" title="Enter Title of Father and Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>								
			</td>
			
            <td align="right" width="25%">
				<bean:message	key="knowledgepro.admission.motherName" /><span class="Mandatory">*</span>
			</td>
			<td width="25%"><div align="left">
				<nested:select property="applicantDetails.titleOfMother" styleId='titleOfMother' name="onlineApplicationForm" styleClass="dropdownsmall" onchange="motherIncomeMandatory()">
				<!--<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>-->
				<html:option value="Mrs">Mrs.</html:option>
				<html:option value="Late">Late.</html:option>
				</nested:select>
				<nested:text property="applicantDetails.personalData.motherName" styleId="motherName"  name="onlineApplicationForm" styleClass="textboxmedium" maxlength="50" onkeypress="IsAlphaDot(event)"></nested:text>
			<a href="#" title="Enter Title of Mother and Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div></td>
			
          </tr>
          
          
          
          <tr>
          
          	
				
            <td  align="right">
            <div align="right">
				Occupation:<span class="Mandatory">*</span>
				</div>
			</td>
												
			<td width="25%" >
			
			<div align="left">
			<input type="hidden" id="hiddenFatherOccupationId"  name="onlineApplicationForm" 
						value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.fatherOccupationId"/>"/>
				<nested:select property="applicantDetails.personalData.fatherOccupationId" name="onlineApplicationForm" styleClass="dropdown" styleId="fatherOccupation" onchange="displayOtherForFather(this.value)">
				<html:option value="">- Select -</html:option>
				<html:optionsCollection name="onlineApplicationForm" property="occupations" label="occupationName" value="occupationId"/>
				<html:option value="Other">Other</html:option>
				</nested:select>
				<a href="#" title="Select Father Occupation" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>
										
			<div align="left" id="displayFatherOccupation" >
			
					<nested:text property="applicantDetails.personalData.otherOccupationFather" name="onlineApplicationForm" styleClass="textbox" maxlength="50" styleId="otherOccupationFather"/>
			</div>
			</td>
												
			 <logic:equal value="Other" property="applicantDetails.personalData.motherOccupationId" name="onlineApplicationForm">
						<%dynaStyle="display:block;"; %>
			</logic:equal>
			
			<logic:notEqual value="Other" property="applicantDetails.personalData.motherOccupationId" name="onlineApplicationForm">
					<%dynaStyle="display:none;"; %>
			</logic:notEqual>
												
            <td  align="right">Occupation:</td>
            <td width="25%">
            
			<div align="left">
			 <nested:select property="applicantDetails.personalData.motherOccupationId" name="onlineApplicationForm" styleClass="dropdown" styleId="motherOccupation" onchange="displayOtherForMother(this.value)">
													<html:option value="">- Select -</html:option>
													<html:optionsCollection name="onlineApplicationForm" property="occupations" label="occupationName" value="occupationId"/>
				 									<html:option value="Other">Other</html:option>
			</nested:select>
			<a href="#" title="Select Mother Occupation" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
		   </div>
			
			<div align="left" id="displayMotherOccupation" >
			
					<nested:text property="applicantDetails.personalData.otherOccupationMother" name="onlineApplicationForm" styleClass="textbox"   maxlength="50"  styleId="otherOccupationMother"/>
			</div>
			
            </td>
          </tr>
          
          
         <tr >
						
						
						
			<td align="right">Family Annual Income: <span class="Mandatory">*</span></td>
			
            <td ><div align="left">
            <nested:select style="display:none" property="applicantDetails.personalData.fatherCurrencyId" name="onlineApplicationForm"  styleId="fatherCurrency">
												
											
           		 <%String selected=""; %>
				<logic:iterate id="option" property="currencyList" name="onlineApplicationForm">
				<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
				<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
				<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
				</logic:iterate>
			</nested:select>	
            <nested:select property="applicantDetails.personalData.fatherIncomeId" name="onlineApplicationForm" styleClass="dropdown" styleId="fatherIncomeRange">
				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
				<nested:optionsCollection name="onlineApplicationForm" property="incomeList" label="incomeRange" value="id"/>
										
				
			</nested:select>
			 <a href="#" title="Select Your Family Annual Income" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           		
			</div>
			</td>
						
						
			
			 <td  >
			<div align="right"><bean:message	key="admissionForm.studentinfo.mobile.label" /></div>
			</td>
			<td   align="left">
			<nested:text readonly="false" styleId="motherMobile" property="applicantDetails.personalData.parentMob1" name="onlineApplicationForm" size="4" maxlength="4" styleClass="textboxsmall" onkeypress="return isNumberKey(event)"></nested:text> <nested:text styleId="motherMobile1" property="applicantDetails.personalData.motherMobile" name="onlineApplicationForm" size="15" maxlength="10" styleClass="textboxmedium" onkeypress="return isNumberKey(event)"></nested:text>
			<a href="#" title="Enter Mother Mobile Code and Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            									
             </td>
                     		  
                        
           </tr>
						
          
          <tr>
          
           <td  >
			<div align="right"><bean:message key="admissionForm.studentinfo.mobile.label" /><span class="Mandatory">*</span></div>
			</td>
			<td  align="left">
			<nested:text readonly="false" styleId="fatherMobile" property="applicantDetails.personalData.parentMob1" name="onlineApplicationForm" size="4" maxlength="4" styleClass="textboxsmall" onkeypress="return isNumberKey(event)"></nested:text> <nested:text styleId="fatherMobile1" property="applicantDetails.personalData.fatherMobile" name="onlineApplicationForm" size="15" maxlength="10" styleClass="textboxmedium" onkeypress="return isNumberKey(event)"></nested:text>
			<a href="#" title="Enter Father Mobile Code and Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            									
            </td>
            
           
            <td width="25%" align="right"><bean:message key="admissionForm.studentinfo.email.label" /></td>
            <td>
            <div align="left"><nested:text property="applicantDetails.personalData.motherEmail" styleId="motherEmail" styleClass="textbox" name="onlineApplicationForm"  maxlength="50"></nested:text>
            <a href="#" title="Enter Mother Mail" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
            </div>
			<div align="left">(e.g. name@yahoo.com)</div>
            </td>
           
             
          </tr>
          
           <tr>
             <td width="25%" align="right">
			<bean:message key="admissionForm.studentinfo.email.label"/></td>
			<td >
			<div align="left"><nested:text property="applicantDetails.personalData.fatherEmail" name="onlineApplicationForm" styleId="fatherEmail" styleClass="textbox" maxlength="50"></nested:text>
			<a href="#" title="Enter Father Mail" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
			</div>
		    <div align="left">(e.g. name@yahoo.com)</div>
            
			</td>
			<td width="25%" align="right">
			Official Adress:
			</td>
			<td width="25%" align="right">
			<div align="left"><nested:text property="applicantDetails.personalData.motherOccupationAddress" name="onlineApplicationForm" styleId="motherAddress" styleClass="textbox" maxlength="50"></nested:text>
			<a href="#" title="Enter Mother Occupation Address" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            </div>
			</td>
           
          </tr>
          
           <tr>
           
			<td width="25%" align="right">
			Official Adress:
			</td>
			<td width="25%" align="right">
			<div align="left"><nested:text property="applicantDetails.personalData.fatherOccupationAddress" name="onlineApplicationForm" styleId="fatherAddress" styleClass="textbox" maxlength="50"></nested:text>
			<a href="#" title="Enter Father Occupation Address" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            </div>
			</td>			
           
          </tr>
          
          <tr>
          	<td width="25%" align="right">
				PAN Card Number :
			</td>
			<td width="25%">
				<div align="left">
					<nested:text property="applicantDetails.personalData.fatherPANNumber" name="onlineApplicationForm" styleId="fatherPANNumber" styleClass="textbox" maxlength="10" style="text-transform:uppercase"></nested:text>
					<a href="#" title="Enter Father's PAN Card Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
				</div>
			</td>
          </tr>
          
          <tr>
          	<td width="25%" align="right">
				Aadhaar Card Number :
			</td>
			<td width="25%">
				<div align="left">
					<nested:text property="applicantDetails.personalData.fatherAadhaarNumber" name="onlineApplicationForm" styleId="fatherAadhaarNumber" styleClass="textbox" maxlength="12"  onkeypress="return isNumberKey(event)"></nested:text>
					<a href="#" title="Enter Father's AadhaarNumber Card Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
				</div>
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
       				    <bean:message key="knowledgepro.applicationform.sameaddrparent.label"/> &nbsp;
       				 	
            <fieldset style="border: 0px">
             <html:radio property="sameParentAddr" styleId="sameParAddr" value="true" onclick="disablePermAddress()"></html:radio>
					<label for="sameParentAddr"><span><span></span></span><bean:message key="knowledgepro.applicationform.yes.label"/></label> 
			
			
			 <html:radio property="sameParentAddr" styleId="DiffParAddr" value="false" onclick="enablePermAddress()"></html:radio>
					<label for="sameParentAddr"><span><span></span></span><bean:message key="knowledgepro.applicationform.No.label"/></label> 
			 <a href="#" title="Select Parent Address Yes, if same as Current Address  " class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            </fieldset>
						</td>
            
     				 </tr>
    				</table>
    				</td>
  					</tr>
					
					<tr>
   
    				<td width="100%"><div id="permLabel">
    				<table align="center" width="40%" border="0" style="border-collapse:collapse">
      				<tr>
       				 <td height="30" align="center" class="subheading">
       				 Parent Address</td>
     				 </tr>
    				</table>
    				</div>
    				</td>
  					</tr>
  					
						
					<tr>
                       <td>
                       <div id="permTable">
                       <table width="100%"  align="center" cellpadding="4" class="subtable"  >
        
							<tr >
								<td width="25%"   >
								<div align="right">House Name/House Number:<span class="Mandatory">*</span></div>
								</td>
								<td  width="25%"   align="left">
								<nested:text styleId="parentAddressLine1" property="applicantDetails.personalData.parentAddressLine1" styleClass="textbox" name="onlineApplicationForm" size="15" maxlength="100" ></nested:text>
								<a href="#" title="Enter Your Parent House Name or Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
								</td>
								
								
								<td width="25%"  align="right"><bean:message key="admissionForm.studentinfo.mobile.label"/><span class="Mandatory">*</span></td>
            				<td width="25%" >
           					 <div align="left">
           					 <nested:text readonly="false" styleClass="textboxsmall" property="applicantDetails.personalData.parentMob1" styleId="parentMobile" name="onlineApplicationForm"  maxlength="4" size="4" onkeypress="return isNumberKey(event)"   ></nested:text>
				   			 <nested:text styleClass="textboxmedium" property="applicantDetails.personalData.parentMob2"  styleId="parentMobile1" name="onlineApplicationForm"  maxlength="10" size="10" onkeypress="return isNumberKey(event)"   ></nested:text>
					 		<a href="#" title="Enter Your Parent Mobile Code and Number" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
					 		</div>
							</td>
							
								
								
							</tr>
										
										
										
							<tr >
								<td  >
								<div align="right">Post Office Name:<span class="Mandatory">*</span></div>
								</td>
								<td  >
								<nested:text styleId="parentAddressLine2" property="applicantDetails.personalData.parentAddressLine2" styleClass="textbox" name="onlineApplicationForm" size="15" maxlength="100" onkeypress="IsAlpha(event)"></nested:text>
								<a href="#" title="Enter Your Parent Post Office Name" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
								</td>
											
								
								<td align="right"><div align="right">
								<bean:message key="knowledgepro.admin.country" /><span class="Mandatory">*</span>
								</div>
								</td>
								<td ><div align="left">
								<input type="hidden" id="hiddenParentCountryId" name="nationality" name="onlineApplicationForm" 
												   value="<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.parentCountryId"/>"/>
											
								<nested:select property="applicantDetails.personalData.parentCountryId" name="onlineApplicationForm" styleClass="dropdown" styleId="parentCountryName" onchange="getParentAddrStates(this.value,'parentState')">
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
							<div align="right"><bean:message key="knowledgepro.admin.city" />:<span class="Mandatory">*</span></div>
								
							</td>
							<td   align="left">
								<nested:text styleId="parentCityName" property="applicantDetails.personalData.parentCityName" styleClass="textbox" name="onlineApplicationForm" size="15" maxlength="30" onkeypress="IsAlpha(event)"></nested:text>
								<a href="#" title="Enter Your Parent City" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
							
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
					                  	<nested:select property="applicantDetails.personalData.parentStateId" name="onlineApplicationForm" styleClass="dropdown" styleId="parentState" onchange="funcOtherShowHide('parentState','otherParentAddrState');">
											<html:option value="">- Select -</html:option>
											<logic:notEmpty property="parentStateMap" name="onlineApplicationForm">
											<html:optionsCollection name="onlineApplicationForm" property="parentStateMap" label="value" value="key" />
											</logic:notEmpty>
											<html:option value="Other">Other</html:option>
										</nested:select>
										<a href="#" title="Enter Your Parent State" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            
										</div>				
							<div align="left">
							<nested:text property="applicantDetails.personalData.parentAddressStateOthers" styleClass="textbox" name="onlineApplicationForm" maxlength="30" styleId="otherParentAddrState" style="<%=dynastyle %>" onkeypress="IsAlpha(event)"></nested:text>
							</div>
							</td>
											
											
							</tr>
							
							 <tr>
            				<td ><div align="right">
            				<bean:message	key="knowledgepro.admission.zipCode" /><span class="Mandatory">*</span>
							
            				</div>
								</td>
								<td  width="25%"   align="left">
								<nested:text styleId="parentAddressZipCode" property="applicantDetails.personalData.parentAddressZipCode" styleClass="textbox" name="onlineApplicationForm" size="10" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text>
								<a href="#" title="Select Your Parent ZIP Code" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           	
								</td>
			
           				 	<td></td>
            				<td></td>
         					 </tr>					
									
						 </table>
						 </div>
          				</td>
      					</tr>
      
      
      
      
      
       <tr ><td height="30"></td></tr>
      
      
      
      
      

   		<tr>
       		<td width="100%">
       			<table align="center" width="40%" border="0" style="border-collapse:collapse">
      				<tr>
        				<td height="30" align="center" class="subheading">Community Details</td>
      				</tr>      				
    			</table>
    		</td>
  		</tr>
      	<tr>
        	<td>
        		<table align="center" cellpadding="4" class="subtable">
          			<tr>
            			<td valign="top" align="right" width="25%"><bean:message key="admissionForm.studentinfo.religion.label"/><span class="Mandatory">*</span></td>
            			<td width="25%" valign="top">
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
									<nested:select property="applicantDetails.personalData.religionId" name="onlineApplicationForm" styleClass="dropdown" styleId="religions" onchange="getSubCaste(this.value,'castCatg');funcOtherShowHide('religions','otherReligion');funcReligionShowHide(this.value);">
										<option value=""><bean:message key="knowledgepro.admin.select"/></option>
										<nested:optionsCollection name="onlineApplicationForm" property="religions" label="religionName" value="religionId"/>
										<html:option value="Other">Other</html:option>
									</nested:select>
									<a href="#" title="Select Your Religion,If You are a Christian, Select Dioceses and Parish Category also" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>				           	
								</logic:notEqual>
								<logic:equal value="Other" property="applicantDetails.personalData.religionId" name="onlineApplicationForm">
									<%dynaStyle="display:block;"; %>												
									<nested:select property="applicantDetails.personalData.religionId" name="onlineApplicationForm" styleClass="dropdown" styleId="religions" onchange="getSubCaste(this.value,'castCatg');funcOtherShowHide('religions','otherReligion');funcReligionShowHide(this.value);">
										<option value=""><bean:message key="knowledgepro.admin.select"/></option>
										<nested:optionsCollection name="onlineApplicationForm" property="religions" label="religionName" value="religionId"/>
										<html:option value="Other">Other</html:option>
									</nested:select>
									<a href="#" title="Select Your Religion in options,If you are not find,select other option and write your religion name, If You are a Christian, write Dioceses and Parish Category names." class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
							  </logic:equal>
			 			 	</div>
							<div align="left"><nested:text property="applicantDetails.personalData.religionOthers" styleClass="textbox"  name="onlineApplicationForm"   maxlength="30" styleId="otherReligion" style="<%=dynaStyle %>"></nested:text></div>
							<logic:equal name="onlineApplicationForm" property="applicantDetails.personalData.religionId" value="3">
								<%dynaStyle="display:block;"; %>	
							</logic:equal>
			 				<div align="left" id="parish_description" style="display: none;" >
                    			Diocese:<nested:text property="applicantDetails.personalData.dioceseOthers" styleClass="textboxmedium"  name="onlineApplicationForm" size="10" maxlength="30" styleId="otherDiocese" onkeypress="IsAlpha(event)"></nested:text>
			 				</div>
              				<div align="left" id="dioces_description" style="display: none;" >
                     			&nbsp;  &nbsp;  Parish:<nested:text property="applicantDetails.personalData.parishOthers" styleClass="textboxmedium"  name="onlineApplicationForm" size="10" maxlength="30" styleId="otherParish" onkeypress="IsAlpha(event)"></nested:text>
							</div>
            			</td>
            			<%String dynaStyle4="display:none;"; %>
						<logic:equal value="true" property="handicapped" name="onlineApplicationForm">
							<%dynaStyle4="display:block;"; %>
						</logic:equal>
						<logic:equal value="false" property="dateExpired" name="onlineApplicationForm">
							<td align="right" width="25%" valign="top"><bean:message key="knowledgepro.applicationform.physical.label"/></td>
	         				<td width="25%" >
	         					<div align="left">
	                				<input type="hidden" id="hiddenHandicaped" name="hiddenHandicaped" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.handicapped"/>'/>
	           	    				<fieldset style="border: 0px">
	             						<nested:radio property="applicantDetails.personalData.handicapped" styleId="handicappedYes" name="onlineApplicationForm" value="true" onclick="showHandicappedDescription();"></nested:radio>
	                           			<label for="handicappedYes"><span><span></span></span><bean:message key="knowledgepro.applicationform.yes.label"/></label> 
				 						<nested:radio property="applicantDetails.personalData.handicapped" styleId="handicappedNo" name="onlineApplicationForm" value="false" onclick="hideHandicappedDescription()"></nested:radio>
					           			<label for="handicappedNo"><span><span></span></span><bean:message key="knowledgepro.applicationform.No.label"/></label> 
				 						<a href="#" title="Select Yes If You are Physically Challenged Person and write description on it and its percentage." class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
	           						</fieldset>
								</div>
								<div align="left" id="handicapped_description" style="<%=dynaStyle4 %>">
									<span class="Mandatory">Percentage of diasbilty :</span> <nested:text styleId="hadnicappedDescription" property="applicantDetails.personalData.hadnicappedDescription" name="onlineApplicationForm" maxlength="80" styleClass="textbox"></nested:text>
								</div>
			 				</td>
						</logic:equal>         				
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
                 				<nested:select property="applicantDetails.personalData.casteId" name="onlineApplicationForm" styleId="castCatg" styleClass="dropdown" onchange="funcOtherShowHide('castCatg','otherCastCatg');funcRCSCShowHide(this.value);">
				  					<option value="">-Select-</option>	
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
								<a href="#" title="Select Your Caste in options ,if it is not there select other option and write your caste name." class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
							</div>
							<div align="left"><nested:text property="applicantDetails.personalData.casteOthers" name="onlineApplicationForm" size="10" maxlength="30" styleId="otherCastCatg" styleClass="textbox" style="<%=dynaStyle %>"></nested:text></div>
            			</td>
            			<logic:equal value="false" property="dateExpired" name="onlineApplicationForm">
            				<td align="right"><div align="right">Participation in Cultural Activities:</div></td>
		                    <td align="left">
		                    	<div align="left">
			                    	<input type="hidden" id="arts" name="ats" value='<bean:write name="onlineApplicationForm" property="arts"/>'/>
		                         	<nested:select property="applicantDetails.personalData.arts" name="onlineApplicationForm" styleClass="dropdown" styleId="arts1" onchange="showArtsParticipate(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:option value="Kerala School Kalolsavam">Kerala School Kalolsavam</html:option>
										<html:option value="Revenue District level youth Festival">Revenue District level youth Festival</html:option>
									</nested:select>
									<a href="#" title="Select If You are Participate In any Cultural Activities" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
								</div>
							</td>
            			</logic:equal>             			
           			</tr>
		   			<tr>
 		    			<td align="right"><bean:message key="admissionForm.studentinfo.castcatg.label"/><span class="Mandatory">*</span></td>
            			<td>
            				<div align="left">
	        					<nested:select  property="applicantDetails.personalData.subReligionId" name="onlineApplicationForm" styleClass="dropdown" styleId="subreligion" onchange="funcOtherShowHide('subreligion','otherSubReligion')">
									<html:option value="">- Select -</html:option>
		    						<html:optionsCollection property="subReligionMap" name="onlineApplicationForm" label="value" value="key"/>
								</nested:select>
								<a href="#" title="Select your Caste Category" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
							</div>
            			</td>
            			<logic:equal value="false" property="dateExpired" name="onlineApplicationForm">
            				<td align="right" ><div align="right">If Yes Achievement In Cultural Activities:</div></td>
		                    <td align="left">
		                    	<div align="left">
		                    		<input type="hidden" id="artsParticipate" name="artsParticipate" value='<bean:write name="onlineApplicationForm" property="artsParticipate"/>'/>
	                         		<nested:select disabled="true" property="applicantDetails.personalData.artsParticipate" name="onlineApplicationForm" styleClass="dropdown" styleId="artsParticipate1">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:option value="A">A</html:option>
										<html:option value="B">B</html:option>
										<html:option value="C">C</html:option>
										<html:option value="Participated">Participated</html:option>
									</nested:select>
									<a href="#" title="Select If You are Achieve In any Cultural Activities" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
							 	</div>
							</td>
            			</logic:equal>            			
                 	</tr>
					<tr>
						<td width="25%" align="right" ><div align="right">Whether Dependent of Service/Ex-Service Man?:</div></td>
            			<td width="25%">
            				<div align="left">
           						<fieldset style="border: 0px">
             						<nested:radio styleId="exservice" property="applicantDetails.personalData.exservice" name="onlineApplicationForm" value="true" ></nested:radio>
						  			<label for="exservice"><span><span></span></span><bean:message key="knowledgepro.applicationform.yes.label"/></label> 
			 						<nested:radio styleId="exservice1" property="applicantDetails.personalData.exservice" name="onlineApplicationForm" value="false" ></nested:radio>
						   			<label for="exservice1"><span><span></span></span><bean:message key="knowledgepro.applicationform.No.label"/></label> 
			 						<a href="#" title="Select Who Your family are Dependent of Service/Ex-Service Man" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           						</fieldset>
							</div>					
						</td>
						<logic:equal value="false" property="dateExpired" name="onlineApplicationForm">
							<td align="right" ><div align="right">Participation in Sports:</div></td>
		            		<td align="left">
		            			<div align="left">
		            				<input type="hidden" id="sports" name="sports" value='<bean:write name="onlineApplicationForm" property="sports"/>'/>
	                				<nested:select property="applicantDetails.personalData.sports" name="onlineApplicationForm" styleClass="dropdown" styleId="sports1" onchange="showSportsParticipate(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:option value="Senior/Junior International Competition">Senior/Junior International Competition</html:option>
										<html:option value="Senior/Junior National Competition">Senior/Junior National Competition</html:option>
										<html:option value="School nationals (including CISCE, CBSE, HSE, and VHSE)">School nationals (including CISCE, CBSE, HSE, and VHSE)</html:option>
										<html:option value="Zone and Cluster levels (including ICSE, CBSE, HSE, VHSE)">Zone and Cluster levels (including ICSE, CBSE, HSE, VHSE)</html:option>
										<html:option value="Inter Collegiate/Senior/Junior State Championship (Inter District)">Inter Collegiate/Senior/Junior State Championship (Inter District)</html:option>
										<html:option value="Sub District Level">Sub District Level</html:option>
			 	 					</nested:select>
			 	 					<a href="#" title="Select If You are Participte in any Sports" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
				 				</div> 
				 			</td>
						</logic:equal>								
					</tr>
					<tr>
				   		<logic:equal name="onlineApplicationForm" property="programTypeId" value="1">
							<%String dynaStyle5="display:none;"; %>
							<logic:equal value="true" property="ncccertificate" name="onlineApplicationForm">
								<%dynaStyle5="display:block;"; %>
							</logic:equal>							
					        <td align="right" >Holder of Plus Two Level NCC Certificate:</td>
                           	<td >
                           		<div>
                            		<input type="hidden" id="hiddenncccertificate" name="hiddenncccertificate" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.ncccertificate"/>'/>
           				 			<fieldset style="border: 0px">
             							<nested:radio styleId="ncccertificate" property="applicantDetails.personalData.ncccertificate" name="onlineApplicationForm" value="true" onclick="showNcccertificate()"></nested:radio>
						   				<label for="ncccertificate"><span><span></span></span><bean:message key="knowledgepro.applicationform.yes.label"/></label> 
			 							<nested:radio styleId="ncccertificate1" property="applicantDetails.personalData.ncccertificate" name="onlineApplicationForm" value="false" onclick="hideNcccertificate()"></nested:radio>
										<label for="ncccertificate1"><span><span></span></span><bean:message key="knowledgepro.applicationform.No.label"/></label> 
			 							<a href="#" title="Select Holder of Plus Two Level NCC Certificate and grade also." class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           				 			</fieldset>
								</div>
								<div id="ncccertificate_description" style="<%=dynaStyle5 %>">
									Grade of Certificate:
	                       			<input type="hidden" id="nccgrade" name="nccgrade" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.nccgrades"/>'/>
                          			<nested:select property="applicantDetails.personalData.nccgrades" styleClass="dropdownmedium" name="onlineApplicationForm" styleId="nccgrade1">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<!--<html:option value="A">A</html:option>-->
										<html:option value="B">B</html:option>
										<!--<html:option value="C">C</html:option>-->
									</nested:select>
								</div>
							</td>
                        </logic:equal>
						<logic:equal name="onlineApplicationForm" property="programTypeId" value="2">
						    <%String dynaStyle6="display:none;"; %>
							<logic:equal value="true" property="ncccertificate" name="onlineApplicationForm">
								<%dynaStyle6="display:block;"; %>
							</logic:equal>
				        	<td align="right" >Holder of UG Level NCC Certificate:</td>
                          		<td>
                          			<div align="left">
                          				<input type="hidden" id="hiddenncccertificate" name="hiddenncccertificate" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.ncccertificate"/>'/>
         				 				<fieldset style="border: 0px">
            								<nested:radio styleId="ncccertificate" property="applicantDetails.personalData.ncccertificate" name="onlineApplicationForm" value="true" onclick="showNcccertificate()"></nested:radio>
					   					<label for="ncccertificate"><span><span></span></span><bean:message key="knowledgepro.applicationform.yes.label"/></label> 
		 								<nested:radio styleId="ncccertificate1" property="applicantDetails.personalData.ncccertificate" name="onlineApplicationForm" value="false" onclick="hideNcccertificate()"></nested:radio>
										<label for="ncccertificate1"><span><span></span></span><bean:message key="knowledgepro.applicationform.No.label"/></label> 
		 								<a href="#" title="Select Holder of UG Level NCC Certificate and grade also." class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
         					 			</fieldset>
								</div>
								<div id="ncccertificate_description" style="<%=dynaStyle6 %>">
									Grade of Certificate:
	                      			<input type="hidden" id="nccgrade" name="nccgrade" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.nccgrades"/>'/>
                          			<nested:select property="applicantDetails.personalData.nccgrades" name="onlineApplicationForm" styleClass="dropdownmedium" styleId="nccgrade1">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:option value="A">A</html:option>
										<html:option value="B">B</html:option>
										<html:option value="C">C</html:option>
						 			</nested:select>
						 		</div>
						 	</td>
                        </logic:equal>
                        <logic:equal value="false" property="dateExpired" name="onlineApplicationForm">
                        	<td align="right" ><div align="right">If Yes Achievement In Sports:</div></td>
							<td align="left">
								<div align="left">
		                    		<input type="hidden" id="sportsParticipate" name="sportsParticipate" value='<bean:write name="onlineApplicationForm" property="sportsParticipate"/>'/>
	                        		<nested:select disabled="true" property="applicantDetails.personalData.sportsParticipate" name="onlineApplicationForm" styleClass="dropdown" styleId="sportsParticipate1">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:option value="1 Prize">1 Prize</html:option>
										<html:option value="2 Prize">2 Prize</html:option>
										<html:option value="3 Prize">3 Prize</html:option>
										<html:option value="Participated">Participated</html:option>
									</nested:select>
									<a href="#" title="Select You Achieve any thing in Sports" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
								 </div>
							</td>
                        </logic:equal>                        
					</tr>
                 	<logic:equal name="onlineApplicationForm" property="programTypeId" value="1">
						  <%String dynaStyle7="display:none;"; %>
						<logic:equal value="true" property="nsscertificate" name="onlineApplicationForm">
								<%dynaStyle7="display:block;"; %>
						</logic:equal>
				        <tr>
							<td align="right" >Holder of Plus Two Level NSS Certificate:</td>
                         	<td>
                         		<div align="left">
                          				<input type="hidden" id="hiddennsscertificate" name="hiddennsscertificate" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.nsscertificate"/>'/>
           							<fieldset style="border: 0px">
             							<nested:radio styleId="nsscertificate" property="applicantDetails.personalData.nsscertificate" name="onlineApplicationForm" value="true" onclick="showNsscertificate()"></nested:radio>
							 			<label for="nsscertificate"><span><span></span></span><bean:message key="knowledgepro.applicationform.yes.label"/></label> 
			 							<nested:radio styleId="nsscertificate1" property="applicantDetails.personalData.nsscertificate" name="onlineApplicationForm" value="false" onclick="hideNsscertificate()"></nested:radio>
										<label for="nsscertificate1"><span><span></span></span><bean:message key="knowledgepro.applicationform.No.label"/></label> 
			 							<a href="#" title="Holder of Plus Two Level NSS Certificate" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           							</fieldset>
								</div>
								<div id="nsscertificate_description" style="<%=dynaStyle7 %>">
									Grade of Certificate:
	                       			<input type="hidden" id="nssgrade" name="nssgrade" value='<bean:write name="onlineApplicationForm" property="applicantDetails.personalData.nssgrades"/>'/>
                          			<nested:select property="applicantDetails.personalData.nssgrades" styleClass="dropdownmedium" name="onlineApplicationForm" styleId="nssgrade1">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<!--<html:option value="A">A</html:option>-->
										<html:option value="B">B</html:option>
										<!--<html:option value="C">C</html:option>-->
									</nested:select>
								</div>
							</td>
							<td><div align="left"></div></td>
							<td ><div align="left"></div></td>				
                        </tr>
					</logic:equal>
					<logic:equal name="onlineApplicationForm" property="programTypeId" value="2">
						<tr>									
							<td align="right" >Holder of UG Level NSS Certificate:</td>
                           	<td >
                           		<div align="left">
           							<fieldset style="border: 0px">
             							<nested:radio styleId="nsscertificate" property="applicantDetails.personalData.nsscertificate" name="onlineApplicationForm" value="true" ></nested:radio>
							 			<label for="nsscertificate"><span><span></span></span><bean:message key="knowledgepro.applicationform.yes.label"/></label> 
			 							<nested:radio styleId="nsscertificate1" property="applicantDetails.personalData.nsscertificate" name="onlineApplicationForm" value="false" ></nested:radio>
										<label for="nsscertificate1"><span><span></span></span><bean:message key="knowledgepro.applicationform.No.label"/></label> 
			 							<a href="#" title="Select Holder of UG Level NSS Certificate" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
            						</fieldset>
								</div>				
							</td>
							<td ><div align="left"></div></td>
							<td ><div align="left"></div></td>
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
                    
           
             <fieldset style="border: 0px">
             <nested:radio property="applicantDetails.personalData.sportsPerson" styleId="sportsPersonYes" name="onlineApplicationForm" value="true" onclick="showSportsDescription()"></nested:radio>
				<label for="sportsPersonYes"><span><span></span></span><bean:message key="knowledgepro.applicationform.yes.label"/></label> 
			
			
			 <nested:radio property="applicantDetails.personalData.sportsPerson" styleId="sportsPersonNo" name="onlineApplicationForm" value="false" onclick="hideSportsDescription()"></nested:radio>
				<label for="sportsPersonNo"><span><span></span></span><bean:message key="knowledgepro.applicationform.No.label"/></label> 
			 <a href="#" title="Select Yes If you are a Sports Person and write description." class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
             </fieldset>
           
					</div>
					
					<div align="left" id="sports_description" style="<%=dynaStyle3 %>">
					<nested:text styleId="sportsDescription" property="applicantDetails.personalData.sportsDescription" name="onlineApplicationForm"  maxlength="80" styleClass="textbox" ></nested:text>
					</div>	
								
					</td>		
											
											
           </tr>
					
		  
        </table>
        </td>
      </tr>
      
      	<logic:equal name="onlineApplicationForm" property="programId" value="9">
			<tr>
	       		<td width="100%">
	       			<table align="center" width="40%" border="0" style="border-collapse:collapse">
	      				<tr>
	        				<td height="30" align="center" class="subheading">Entrance Exam Details</td>
	      				</tr>      				
	    			</table>
	    		</td>
	  		</tr>
							
			<tr>
				<td>
        			<table align="center" cellpadding="4" class="subtable">
        				<tr>
        					<td align="right"><span class="Mandatory">*</span>MBA Entrance exam :</td>
							<td>
								<html:select name="onlineApplicationForm" property="applicantDetails.personalData.mbaEntranceExamId" styleId="mbaEntranceExamId" styleClass="dropdownmedium">
									<html:option value="">-Select-</html:option>
									<html:optionsCollection name="onlineApplicationForm" property="mbaEntranceExamMap" value="key" label="value"/>
								</html:select>
							</td>
							<td align="right"><span class="Mandatory">*</span>Marks secured :</td>
							<td>
								<html:text name="onlineApplicationForm" property="applicantDetails.personalData.entranceMarksSecured" styleId="entranceMarksSecured" styleClass="textbox"></html:text>
							</td>
        				</tr>
        			</table>
        		</td>
			</tr>
		</logic:equal>
   
<%--
    <tr ><td height="30"></td></tr>
    	<logic:equal value="true" property="mgquota" name="onlineApplicationForm">
    		<tr>
	    		<td width="100%">
	    			<table align="center" width="40%" border="0" style="border-collapse:collapse">
	      				<tr><td height="30" align="center" class="subheading">Recommentedation details</td></tr>
	    			</table>
	    		</td>
	  		</tr>
	      	<tr>
	        	<td align="center">
	        		<table width="100%"  align="center" cellpadding="4" class="subtable">
	         			<tr>
	              			<td align="right" width="50%" valign="top"><div align="right"><span class="Mandatory">*</span>Recommented by Name: </div></td>
				            <td width="50%" align="left">
				            	<nested:text styleId="recommentedBy" property="applicantDetails.personalData.recommentedBy" name="onlineApplicationForm"  maxlength="80" styleClass="textboxmedium" ></nested:text>
				            	<a href="#" title="Specify the name of the person who recommented you." class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
							</td>
	       				</tr>
	          			<tr>
	            			<td align="right" width="50%" valign="top"><div align="right"><span class="Mandatory">*</span>Designation of the recommented person: </div></td>
				            <td width="50%" align="left">
				            	<nested:text styleId="recommentedPersonDesignation" property="applicantDetails.personalData.recommentedPersonDesignation" name="onlineApplicationForm"  maxlength="80" styleClass="textboxmedium" ></nested:text>
				            	<a href="#" title="Specify the name of the person's designation who recommented you." class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
							</td>
	          			</tr>
	          			<tr>
	            			<td align="right" width="50%" valign="top"><div align="right"><span class="Mandatory">*</span>Mobile number of the recommented person: </div></td>
				            <td width="50%" align="left">
				            	<nested:text styleId="recommentedPersonMobile" property="applicantDetails.personalData.recommentedPersonMobile" name="onlineApplicationForm"  maxlength="12" styleClass="textboxmedium" onkeypress="return isNumberKey(event)"></nested:text>
				            	<a href="#" title="Specify the name of the person's mobile number who recommented you." class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
							</td>
	          			</tr>
	        		</table>
	        	</td>
	      	</tr>
    	</logic:equal>--%>
  		
   	<tr><td height="20"></td></tr>
   <tr>
  <td>
  <table width="100%">
      <tr>
        <td  width="100%" align="center">
        <html:button property="" styleClass="cntbtn" styleId="SubmitPersonalDetailPage" value="Save & Continue to Education Details" > </html:button>
							
         </td>
      </tr>
  </table>
  </td>
  </tr>
  
   <tr>
  <td>
  <table width="100%">
      <tr>
        <td  width="100%" align="center">
        <br/>
        <html:button property="" value="Clear" styleClass="btn1" onclick="resetPersonalForm();" /> 
		 &nbsp; <html:button property="" onclick="cancel()" styleClass="cancelbtn" value="Logout"></html:button>
  							
         </td>
      </tr>
  </table>
  </td>
  </tr>
  
   <tr ><td height="30"></td></tr>
   
 
   
</table>
		
 			
 			
	
	
	
	<script language="JavaScript" src="js/admission/OnlineDetailsPersonalInfo.js"></script>
	<script type="text/javascript">
	
	onLoadAddrCheck();
	getMobileNo();
	
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

	var sameParAddr= document.getElementById("sameParAddr").checked;

	if(sameParAddr==false){
		var hiddenParentCountryId = document.getElementById("hiddenParentCountryId").value;
		if(hiddenParentCountryId != null && hiddenParentCountryId.length != 0) {
			document.getElementById("parentCountryName").value = hiddenParentCountryId;
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
		
			var showNss = document.getElementById("hiddennsscertificate").value;
			if(showNss != null && showNss.length != 0 && showNss=='true') {
				showNsscertificate();	
			}else{
				hideNsscertificate();
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