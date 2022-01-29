<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="com.kp.cms.constants.KPPropertiesConfiguration"%>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<link rel='stylesheet' type='text/css' href="css/auditorium/start/jquery-ui.css" />
<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>

 
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

<%--
<LINK REL=StyleSheet HREF= "css/admission/AdmissionResponsiveTabs.css" TYPE="text/css">
<link rel="stylesheet" href="css/admission/OnlineApplicationFormNew.css"/>
<LINK REL=StyleSheet HREF= "css/admission/style.css" TYPE="text/css">
 --%>


<html:html>
<head>

<script type="text/javascript">



</script>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</head>

<html:form action="/uniqueIdRegistration" method="post">
<html:hidden property="formName" value="uniqueIdRegistrationForm" />
<html:hidden property="method" styleId="method" value="" />

<html:hidden property="applicationNo" styleId="applicationNo" name="uniqueIdRegistrationForm"/>
<html:hidden property="appliedYear" styleId="appliedYear" name="uniqueIdRegistrationForm"/>
<input type="hidden" id="mode">
<html:hidden property="accoId" styleId="accoId"/>
<html:hidden property="displayName" styleId="displayName" name="uniqueIdRegistrationForm"/>
<html:hidden property="displayMode" styleId="displayMode" name="uniqueIdRegistrationForm"/>
<html:hidden property="onlineAcknowledgement" styleId="onlineAcknowledgement" name="uniqueIdRegistrationForm"/>
<html:hidden property="onlineApplicationUniqueId" styleId="onlineApplicationUniqueId" name="uniqueIdRegistrationForm"/>
<html:hidden property="offlinePage" name="uniqueIdRegistrationForm"/>
<html:hidden property="aided" name="uniqueIdRegistrationForm"/>

<c:set var="uniqueId"><bean:write name="uniqueIdRegistrationForm" property="onlineApplicationUniqueId"/></c:set>  
<c:set var="applicantName"><bean:write name="uniqueIdRegistrationForm" property="applicantName"/></c:set>  
<c:set var="applicantMobileNo"><bean:write name="uniqueIdRegistrationForm" property="applicantMobileNo"/></c:set>  
<c:set var="applicantMobileCode"><bean:write name="uniqueIdRegistrationForm" property="applicantMobileCode"/></c:set>  
<c:set var="applicantEmailId"><bean:write name="uniqueIdRegistrationForm" property="applicantEmailId"/></c:set>
<c:set var="dob"><bean:write name="uniqueIdRegistrationForm" property="dob"/></c:set>  
<c:set var="residentCategoryId"><bean:write name="uniqueIdRegistrationForm" property="residentCategoryId"/></c:set>
<c:set var="displayMode1"><bean:write name="uniqueIdRegistrationForm" property="displayMode"/></c:set>  
<c:set var="appLnNO1"><bean:write name="uniqueIdRegistrationForm" property="applicationNo"/></c:set> 
<c:set var="programTypeId"><bean:write name="uniqueIdRegistrationForm" property="programTypeId"/></c:set>  
<c:set var="gender"><bean:write name="uniqueIdRegistrationForm" property="gender"/></c:set>  
<c:set var="subReligion"><bean:write name="uniqueIdRegistrationForm" property="subReligionId"/></c:set>  
<c:set var="challanRefNo"><bean:write name="uniqueIdRegistrationForm" property="challanRefNo"/></c:set>
        	
        
			<%
 				session.setAttribute("UNIQUE_ID", null);
 				session.setAttribute("APPLICANT_NAME", null);
 				session.setAttribute("APPLICANT_MOBILENO", null);
 				session.setAttribute("APPLICANT_MOBILECODE", null);
 				session.setAttribute("APPLICANT_EMAIL", null);
 				session.setAttribute("APPLICANT_DOB", null);
 				session.setAttribute("APPLICANT_RESIDENT_CATEGORY_ID", null);
 				session.setAttribute("APPLICATION_NUMBER", null);
 				session.setAttribute("APPLICANT_PROGRAMTYPE",null);
 				session.setAttribute("GENDER",null);
 				session.setAttribute("SubReligion",null);
 				session.setAttribute("CHALLAN_NO",null);
 				
 				
 				String dlyName = (String) pageContext .getAttribute("displayMode1");
 				if (dlyName != null && !dlyName.equalsIgnoreCase("applnNo")) {
 					String uniqueId = (String) pageContext .getAttribute("uniqueId");
 					session.setAttribute("UNIQUE_ID", uniqueId);
 					session.setAttribute("APPLICATION_NUMBER", null);
 				} else {
 					session.setAttribute("UNIQUE_ID", null);
 					String applnNo = (String) pageContext .getAttribute("appLnNO1");
 					session.setAttribute("APPLICATION_NUMBER", applnNo);
 				}

 				String applicantName = (String) pageContext .getAttribute("applicantName");
 				session.setAttribute("APPLICANT_NAME", applicantName);

 				String applicantMobileNo = (String) pageContext .getAttribute("applicantMobileNo");
 				session.setAttribute("APPLICANT_MOBILENO", applicantMobileNo);

 				String applicantMobileCode = (String) pageContext .getAttribute("applicantMobileCode");
 				session.setAttribute("APPLICANT_MOBILECODE", applicantMobileCode);

 				String applicantEmailId = (String) pageContext .getAttribute("applicantEmailId");
 				session.setAttribute("APPLICANT_EMAIL", applicantEmailId);

 				String dob = (String) pageContext.getAttribute("dob");
 				session.setAttribute("APPLICANT_DOB", dob);

 				String residentCategoryId = (String) pageContext .getAttribute("residentCategoryId");
 				session.setAttribute("APPLICANT_RESIDENT_CATEGORY_ID", residentCategoryId);
 				
 				String programTypeId =(String)pageContext.getAttribute("programTypeId");   
 				session.setAttribute("APPLICANT_PROGRAMTYPE",programTypeId);
 				
 				String gender =(String)pageContext.getAttribute("gender");   
 				session.setAttribute("GENDER",gender);
 				
 				String subReligion =(String)pageContext.getAttribute("subReligion");   
 				session.setAttribute("SubReligion",subReligion);
 			
 				String challanRefNo =(String)pageContext.getAttribute("challanRefNo");   
 				session.setAttribute("CHALLAN_NO",challanRefNo);
 	%>
 	
 	
 	
   
  
      
    <table width="80%" style="background-color: #F0F8FF" align="center">

    
    <tr ><td height="10px"></td></tr>
    
  
	
	<tr>
    <td >
	<table width="100%" align="center" border="0">
	<tr ><td  align="right" height="20px" style="color:#FF0000;font-size:small">*Click on tabs for getting details</td></tr>
   
	<tr>
	
	<td align="center">
	
	
	
	<div id="nav1-menu" align="center">
	
	<ul>
	
	<%--  <li onclick="getDetails('dashBoard')" id="profile" class="ac" style="color:#FF0000;">Profile</li> 
	--%>
   
    <li onclick="getDetails('applyCourses')" id="applyCourses" class="ac" style="">Application Form</li>
    <li onclick="getDetails('status')" id="status" class="ac" style="">Status</li>
    <li onclick="getDetails('alerts')" id="alerts" class="ac" style="">Prints & Downloads</li>
    <li onclick="getDetails('sms')" id="sms" class="ac" style="">Helpdesk</li>
    <li onclick="onlineApplicationStatus()" class="ac" style="">Logout</li>
    
	</ul>
	
   </div>
   </td></tr>
    </table></td>
  </tr>
  
  
  
 
 
  
  
   <tr ><td height="20px"></td></tr>
   
   
    <tr>
        <td>
        
        <!-- profile here -->
   <%-- 
        <table id="DashBoardMessage" width="100%" border="0" align="center" cellpadding="0" class="profiletable" >
   
          <tr>
           <td align="left" width="20%" height="30%">&nbsp;&nbsp; NAME</td>
           <td width="20%"><div align="left"><bean:write name="uniqueIdRegistrationForm" property="applicantName"/></div></td>
           <td width="10%"></td>
           <td rowspan="6" align="right">
           <div align="center">
             <logic:equal value="true" property="isPhoto" name="uniqueIdRegistrationForm">
              <img src='<%= request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()).concat("/PhotoServlet") %>'   height="150px" width="150px" />
             </logic:equal>
             
           <logic:equal value="false" property="isPhoto" name="uniqueIdRegistrationForm">
              <img src='images/admission/images/passport.png'   height="150px" width="150px" />
            </logic:equal>
            
          </div>
          &nbsp;&nbsp;&nbsp;&nbsp;
           </td>
		 </tr>
		 
		 
		
		 <tr>
           <td align="left" height="30%">&nbsp;&nbsp; GENDER</td><td><bean:write name="uniqueIdRegistrationForm" property="gender"/></td><td></td><td></td>
		 </tr>
		 <tr>
           <td align="left"height="30%">&nbsp;&nbsp; DATE OF BIRTH</td><td><bean:write name="uniqueIdRegistrationForm" property="loginDateOfBirth"/></td><td></td><td></td>
		 </tr>
		  <tr>
           <td align="left" height="30%">&nbsp;&nbsp; EMAIL ID</td><td><bean:write name="uniqueIdRegistrationForm" property="applicantEmailId"/></td><td></td><td></td>
		 </tr>
		  <tr>
          <td align="left" width="30%" height="30%">&nbsp;&nbsp; MOBILE</td>
          <td><bean:write name="uniqueIdRegistrationForm" property="applicantMobileCode"/> - <bean:write name="uniqueIdRegistrationForm" property="applicantMobileNo"/></td><td></td><td></td>
		 </tr>
		 
        </table>
        
        --%>
        
        
        
         <!-- application edit here -->
    
				
    <table id="displayApplyCourses" width="100%" border="0" align="center" cellpadding="0" class="profiletable" >
    
    
    <tr><td  align="center"><u>APPLICATION STATUS</u></td></tr>

    <tr><td height="20">
		 
		 </td></tr>
    <tr> <td  style="text-align:center"> 



	<logic:notEmpty property="incompleteApplication" name="uniqueIdRegistrationForm">
	<logic:equal value="1" name="uniqueIdRegistrationForm" property="programTypeId">
	<%-- <logic:equal value="0" name="uniqueIdRegistrationForm" property="isCommuQuota"> --%>
	<logic:notEmpty property="admissionStatusTOList" name="uniqueIdRegistrationForm">
	<nested:iterate name="uniqueIdRegistrationForm" id="toList" property="admissionStatusTOList" indexId="count">
		
		<a href="javascript:void(0)" style="color:#FF0000;font-size:large"
	   onclick="completeApplication('<bean:write name="toList" property="id"/>','<bean:write name="toList" property="applnMode"/>')">Click here to Complete Application</a><br></br>
	
	</nested:iterate>
	</logic:notEmpty>
	<%-- </logic:equal> --%>
	</logic:equal>		
	</logic:notEmpty>
	
	<logic:notEmpty property="incompleteApplication" name="uniqueIdRegistrationForm">
	<logic:equal value="2" name="uniqueIdRegistrationForm" property="programTypeId">
	<logic:notEmpty property="admissionStatusTOList" name="uniqueIdRegistrationForm">
	<nested:iterate name="uniqueIdRegistrationForm" id="toList" property="admissionStatusTOList" indexId="count">
		
		<a href="javascript:void(0)" style="color:#FF0000;font-size:large"
	   onclick="completeApplication('<bean:write name="toList" property="id"/>','<bean:write name="toList" property="applnMode"/>')">Click here to Complete Application</a><br></br>
	
	</nested:iterate>
	</logic:notEmpty>
	</logic:equal>
			
	</logic:notEmpty>

	<logic:equal value="1" name="uniqueIdRegistrationForm" property="programTypeId">
	<%-- <logic:equal value="0" name="uniqueIdRegistrationForm" property="isCommuQuota"> --%>
	<logic:empty property="admissionStatusTOList" name="uniqueIdRegistrationForm">
	<a id="newCourseLink" href="javascript:void(0)" onclick="newCourse()" id="applyCourses" style="color:red;font-size:large;">Click here to Apply Programme</a>
	</logic:empty>
	<%-- </logic:equal> --%>
	</logic:equal>
	<logic:equal value="2" name="uniqueIdRegistrationForm" property="programTypeId">
	<logic:empty property="admissionStatusTOList" name="uniqueIdRegistrationForm">
	<a id="newCourseLink" href="javascript:void(0)" onclick="newCourse()" id="applyCourses" style="color:red;font-size:large;">Click here to Apply Programme</a>
	</logic:empty>	
	</logic:equal>
	
	<logic:notEmpty property="admissionStatusTOList" name="uniqueIdRegistrationForm">
	
	<logic:empty property="incompleteApplication" name="uniqueIdRegistrationForm">
	Application Successfully Submitted.
	<br/>
	Click on Print & Downloads and print your Application. 
	<br/>
	</logic:empty>
	</logic:notEmpty>
	</td>
		  
    </tr>
	
	  <tr ><td height="20px"></td></tr>
	
    </table>
    
   
   
   
        
        
         <!-- status here -->
   			
    <table id="displayStatus" width="100%" border="0" cellpadding="0"  align="center" class="profiletable"  >
    <tr><td  align="center"><u>ADMISSION STATUS</u></td></tr>
    <tr><td height="20">
		 
		 </td></tr>
   <!--  <tr> <td  style="text-align:center"> Publication of provisional rank list 08/06/2017. Please visit website <span style="color: #0000FF">devamatha.ac.in</span></td>
		  
    </tr> -->
	<tr> <td  style="text-align:center">
	<%--<logic:notEmpty property="admissionStatusTOList" name="uniqueIdRegistrationForm">
	
	<logic:empty property="incompleteApplication" name="uniqueIdRegistrationForm">
	
	 <a  href="javascript:void(0)" style=" color:#FF0000;font-size:large" href="" onclick="getStatus();" >Click here to Check Status</a>
	
	</logic:empty>
	</logic:notEmpty>--%>
	 </td>
	</tr>	 
	 <tr ><td height="20px"></td></tr>
    </table>
    
    
    
    
    
    <!-- downloads here -->
    
   <table id="displayAlerts" width="100%" border="0" cellpadding="0"  align="center" class="profiletable"  >
        <tr><td  align="center" ><u>PRINT & DOWNLOADS</u></td></tr>  
  <tr><td height="20">
		 
		 </td></tr>
		   <tr>
		 	<td  height="20" style=""><center>
		 	<a href="" onclick="downloadChallanTieupBank()" style=" color:#FF0000;font-size:large" target="_blank"><font color="red" size="3">Click here to Download Challan for <%= CMSConstants.BANK_NAME %>.</font></a><br><br>
		 	<a href="" onclick="downloadChallanOtherBank()" style=" color:#FF0000;font-size:large" target="_blank"><font color="red" size="3"></font>Click here to Download Challan for Other Bank</font></a>
		 	</center>	
		 	</td>
		  </tr>
		  <logic:equal value="false" name="uniqueIdRegistrationForm" property="aided">
			  <tr>
			 	<td style="text-align:center" height="20%">		 			 											   
		   			<!-- <br><a href="downloadForms/ManagementQuotaForm.pdf" style=" color:#FF0000;font-size:large" target="_blank"><font color="red" size="3">Click here to Download Management Quota Form</font></a> 	  -->  				    	   
			 	</td>
			  </tr>
		  </logic:equal>
		  
		   
		  <tr>
		 	<td  height="40">
		 	
		 	</td>
		
		  </tr>
		 <logic:notEmpty property="admissionStatusTOList" name="uniqueIdRegistrationForm">
	
		 <logic:empty property="incompleteApplication" name="uniqueIdRegistrationForm">
	
         <tr>
		 <td  style="text-align:center">
		 <a href="javascript:void(0)" style=" color:#FF0000;font-size:15px;text-decoration:none" href="" onclick="openPrintPageNew();">Print Application &nbsp; </a><img src="images/admission/images/print.png" width="30" height="30"/>
		&nbsp; &nbsp; &nbsp;
		 <!-- <a href="javascript:void(0)" style=" color:#FF0000;font-size:15px;text-decoration:none" href="" onclick="printPDFNew();">Save as PDF &nbsp; </a><img src="images/admission/images/PDF-Icon.png" width="30" height="30"/> --></td>
		 </tr>
		 </logic:empty>
		 </logic:notEmpty>
		 
		  <tr ><td height="20px"></td></tr>
        </table>
     <!-- helpdesk here -->
    
   <table id="displaySMS" width="100%" border="0" cellpadding="0"  align="center" class="profiletable"  >
         <tr>
		 <td style="text-align:center"><u>COLLEGE DETAILS</u></td>
		 </tr>
		 <tr><td height="20">
		  
		 </td></tr>
		 <tr>
		 <td style="text-align:center"><%=CMSConstants.COLLEGE_NAME %> Details</td>
		 </tr>
		 <tr>
		 <td style="text-align:center">Kindly text your feedbacks and queries to <span style="color: #0000FF"> admissions@devamatha.ac.in</span></td>
		 </tr>
		  <tr>
		 <!-- <td style="text-align:justify;">Please refer the admission prospectus or contact Admissions Officers in 9447777787, 9447213350, 9447212846 during office hours. For enquiries on Management Quota, call 9446383377.</td> -->
		 </tr>
		  
		  <tr ><td height="20px"></td></tr>
        </table>
    
    
    <div id="DashBoardMessage"></div>
    <div id="dashBoard"></div>
    <div id="showMsg"></div>
    
    <div id="mail"></div>
    <div id="displayMail"></div>
    <div id="programmesOffered"></div>
    <div id="displayProgrammesOffered"></div>
    <div id="onlinePayment"></div>
    <div id="displayOnlinePayment"></div>
    
      </td>
      </tr>
   <tr ><td height="50px"></td></tr>
              
                </table>
		</html:form>
		
</html:html>

<script src="js/admission/UniqueIdRegistrationSecondPage.js" type="text/javascript"></script>
