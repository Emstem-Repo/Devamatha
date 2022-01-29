<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%-- 
<LINK REL=StyleSheet HREF= "css/admission/AdmissionResponsiveTabs.css" TYPE="text/css">

<link rel="stylesheet" href="css/admission/OnlineApplicationFormNew.css"/>
--%>

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

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>


<title>Online Application Form</title>
</head>
<body id="wrapper">

<html:form action="/uniqueIdRegistration" method="post">
<html:hidden property="method" styleId="method" value="initOnlineApplicationLogin" />
	<html:hidden property="formName" value="uniqueIdRegistrationForm" />
	<html:hidden property="mode" name="uniqueIdRegistrationForm" styleId="mode" />
	<html:hidden property="pageType" name="uniqueIdRegistrationForm" styleId="pageType" />
	<html:hidden property="offlinePage"  styleId="offlinePage" name="uniqueIdRegistrationForm"/>
	
	
	<table width="80%" style="background-color: #F0F8FF" align="center">

	
	
   <tr > <td height="20px"></td></tr>
   
   <logic:equal value="register" property="mode" name="uniqueIdRegistrationForm">
   <tr><td align="center" class="subheading">REGISTRATION SUCCESSFUL </td></tr>
   </logic:equal>
   
   <tr>
    <td><table width="100%" border="0" align="center" cellpadding="10" class="maintable" >
      <tr>
        <td><table width="100%" border="0" cellpadding="0"  align="center" class="profiletable inst"  >
         <!-- password generation success message -->
       	 <logic:equal value="register" property="mode" name="uniqueIdRegistrationForm">
		
       	 
       	 
       	  <c:set var="uniqueId"><bean:write name="uniqueIdRegistrationForm" property="onlineApplicationUniqueId"/></c:set>  
           <c:set var="applicantMobileNo"><bean:write name="uniqueIdRegistrationForm" property="applicantMobileNo"/></c:set>  
			<c:set var="applicantMobileCode"><bean:write name="uniqueIdRegistrationForm" property="applicantMobileCode"/></c:set>  
			<c:set var="applicantEmailId"><bean:write name="uniqueIdRegistrationForm" property="applicantEmailId"/></c:set>
			<c:set var="dob"><bean:write name="uniqueIdRegistrationForm" property="dob"/></c:set>  
			<c:set var="applicantName"><bean:write name="uniqueIdRegistrationForm" property="applicantName"/></c:set>  
			<c:set var="residentCategoryId"><bean:write name="uniqueIdRegistrationForm" property="residentCategoryId"/></c:set>
			<c:set var="programTypeId"><bean:write name="uniqueIdRegistrationForm" property="programTypeId"/></c:set>  
        	<c:set var="gender"><bean:write name="uniqueIdRegistrationForm" property="gender"/></c:set>  
            <c:set var="subReligion"><bean:write name="uniqueIdRegistrationForm" property="subReligionId"/></c:set>  
        	<c:set var="challanRefNo"><bean:write name="uniqueIdRegistrationForm" property="challanRefNo"/></c:set>
        	
           <% 
           
           session.setAttribute("UNIQUE_ID",null);
           session.setAttribute("APPLICANT_NAME",null);
           session.setAttribute("APPLICANT_MOBILENO",null);
           session.setAttribute("APPLICANT_MOBILECODE", null);
           session.setAttribute("APPLICANT_EMAIL",null);
           session.setAttribute("APPLICANT_DOB",null);
           session.setAttribute("APPLICANT_PROGRAMTYPE",null);
           session.setAttribute("APPLICANT_RESIDENT_CATEGORY_ID",null);
           session.setAttribute("GENDER",null);
           session.setAttribute("SubReligion",null);
           session.setAttribute("CHALLAN_NO",null);
           
 			String uniqueId =(String)pageContext.getAttribute("uniqueId");   
			session.setAttribute("UNIQUE_ID",uniqueId); 
			
			String applicantMobileNo =(String)pageContext.getAttribute("applicantMobileNo");   
			session.setAttribute("APPLICANT_MOBILENO",applicantMobileNo);

			String applicantMobileCode = (String) pageContext .getAttribute("applicantMobileCode");
			session.setAttribute("APPLICANT_MOBILECODE", applicantMobileCode);

			String applicantEmailId =(String)pageContext.getAttribute("applicantEmailId");   
			session.setAttribute("APPLICANT_EMAIL",applicantEmailId);
			
			String dob =(String)pageContext.getAttribute("dob");   
			session.setAttribute("APPLICANT_DOB",dob);

			String programTypeId =(String)pageContext.getAttribute("programTypeId");   
			session.setAttribute("APPLICANT_PROGRAMTYPE",programTypeId);
			
			String residentCategoryId =(String)pageContext.getAttribute("residentCategoryId");   
			session.setAttribute("APPLICANT_RESIDENT_CATEGORY_ID",residentCategoryId);

			String applicantName =(String)pageContext.getAttribute("applicantName");   
			session.setAttribute("APPLICANT_NAME",applicantName);
		
			String gender =(String)pageContext.getAttribute("gender");   
			session.setAttribute("GENDER",gender);
			
			String subReligion =(String)pageContext.getAttribute("subReligion");   
			session.setAttribute("SubReligion",subReligion);
			
			String challanRefNo =(String)pageContext.getAttribute("challanRefNo");   
			session.setAttribute("CHALLAN_NO",challanRefNo);
			
           %>
       	 
       	 <tr > <td height="10px"></td></tr>
       	 
       	 <tr>
           <td width="100%" style="text-align:center" colspan="2">
       		 Thank you for Registering with <%= CMSConstants.COLLEGE_NAME %>.
          </td>
		 </tr>
		 
           <tr>
            <td width="50%" height="30px" ><div align="right">User ID is your date of birth : </div></td>
            <td width="50%" height="30px" ><div>&nbsp;<font color="red"><bean:write name="uniqueIdRegistrationForm" property="dob"/></font></div></td>
            
		 </tr>
		 
		  <tr>
            <td width="50%" height="30px" style="text-align:right">Password is :  </td>
            <td width="50%" height="30px" style="text-align:left">&nbsp;<font color="red"><bean:write name="uniqueIdRegistrationForm" property="uniqueId"/></font></td>
           
		 </tr>
		 
		 
		 
		 <tr>
		 <td colspan="2" width="100%" style="text-align:center">For future reference Password has been sent to your Email ID.</td>
		 </tr>
		 
          <tr>
		 <td colspan="2" width="100%" style="text-align:center">Please don't share your User ID & Password.</td>
		 </tr>
		 
		  <tr>
            <td colspan="2" height="20"> </td>
          </tr>
          
         <tr>
		 <td colspan="2" width="100%" style="text-align:center">
		
		 If you plan to pay application fee using Debit Card/Credit Card/NetBanking
		 <html:button property="" onclick="loginPage()" value="click here to login" styleClass="btn"></html:button>
  		
		 </td>
		 </tr> 
         
        <tr>
		<td colspan="2" width="100%" style="text-align:center">If you plan to pay application fee through Challan of <%= CMSConstants.BANK_NAME %>, Please 
		<html:button property="" onclick="downloadChallanTieupBank();" value=" click here " styleClass="btn"></html:button>to print challan for <%= CMSConstants.BANK_NAME %>.
  
		</td>
		</tr>
		
		<tr>
		<td colspan="2" width="100%" style="text-align:center">If you plan to pay application fee through Challan of Other Banks other than <%= CMSConstants.BANK_NAME %>, Please 
		<html:button property="" onclick="downloadChallanOtherBank();" value=" click here " styleClass="btn"></html:button>to print challan for other bank.
  
		</td>
		</tr>
		
		
		
		 <tr>
		<td colspan="2" width="100%" style="text-align:center">After printing the challan, Remmit money at the bank and then Relogin to continue with application. 
		
		</td>
		</tr>
		 <tr>
            <td colspan="2" height="20"> </td>
          </tr>
		 	
         
         
         <tr>
            <td colspan="2" height="20"> </td>
          </tr>
        
   <tr>
  <td  colspan="2" width="100%" align="center">
  <html:button property="" onclick="loginPage()" value="Logout" styleClass="btn"></html:button>
  <%-- 
  <html:button property="" onclick="proceed()" value="Proceed for Payment" styleClass="button"></html:button>                
  --%>
  </td>
  </tr>
   	 
  </logic:equal>
   	 
   	 
   	 
   	 
   	 <!-- forgot password message -->
   	<logic:equal value="forgotUniqueId" property="mode" name="uniqueIdRegistrationForm">
   
   <tr >
         <td colspan="2" height="40" width="100%" align="center" >
           Your Password is <b>: <bean:write name="uniqueIdRegistrationForm" property="uniqueId"/></b> 
		</td>
   	</tr>
   	
    <tr>
		 <td colspan="2" height="40" width="100%" style="text-align:center">For future reference Password has been sent to your Email ID.</td>
		 </tr>
		 
   	
     <tr>
      <td colspan="2" height="20"> </td>
      
     </tr>
         
   <tr>
  <td  colspan="2" width="100%" align="center">
  
  <html:button property="" onclick="loginPage()" value="Click to Login" styleClass="btn2"></html:button>
                  
  
  </td>
  </tr> 
       
   </logic:equal>
	
        </table></td>
      </tr>
    </table></td>
  </tr>
  
 
 
            
 
   <tr height="20px"><td></td></tr>
   
   <tr height="20px"><td></td></tr>
   
</table>
	
	

</html:form>
</body>
<script src="js/admission/UniqueIdRegistrationMsgPage.js" type="text/javascript"></script>
</html>
