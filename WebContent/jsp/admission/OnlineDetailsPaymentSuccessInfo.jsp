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

 <style type="text/css">
 input[type="radio"]:focus, input[type="radio"]:active {
    -webkit-box-shadow:inset 2px 1px 1px , 1px 1px 3px #008000;
    -moz-box-shadow:inset 2px 1px 1px #008000, 1px 1px 3px #008000;
    box-shadow:inset 2px 1px 1px #008000, 1px 1px 3px #008000;
}
 </style>
 
 
 
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/studentdetails.js"></script>

 			
 			
<table width="80%" style="background-color: #F0F8FF" align="center">
 
    
	<%-- 	
	<tr>
    <td >
	
	<table width="100%" align="center" border="0">
	<tr>
	<td align="center">
	<div id="nav-menu">
	<ul>
	<li class="ac">Terms & Conditions</li>
	<li class="current">Payment</li>
     <li class="">Personal Details</li>
     <li class="">Course Options</li>
     <li class="">Education Dtails</li>
	 <li  class="">Upload Photo</li>
   </ul>
    </div></td>
    </tr>
    </table>
    
    </td>
   </tr>
  --%>
  
    
  <tr ><td height="10"></td></tr>
   
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
	
  
     <logic:equal value="true" name="onlineApplicationForm" property="paymentSuccess">
      <tr>
     <td>
    <table width="100%"  align="center" cellpadding="4" class="subtable w"  >
     
            <tr>
            <td colspan="2" height="30" height="25" class="pay"><div align="center"><b>Your earlier online transaction for application fee was successful. Below are the details of your transaction</b> </div></td>
            </tr>
            
            <tr height="30"><td colspan="2"></td></tr>
           
            <tr>
            		  <td width="50%" height="30" ><div align="right"><b>Transaction Amount:</b></div></td>
                      <td width="50%" height="30" ><div align="left">
                      <bean:write name="onlineApplicationForm" property="txnAmt" />
                      </div></td>
                     
            </tr>
            
             <tr>
                       <td width="50%" height="30" ><div align="right"><b>Transaction Ref No:</b></div></td>
                      <td width="50%" height="30" ><div align="left">
                      <bean:write name="onlineApplicationForm" property="txnRefNo" />
                      </div></td>
             </tr>
             
             <tr>
                      <td width="50%" height="30" ><div align="right"><b>Transaction Date:</b></div></td>
                      <td width="50%" height="30" ><div align="left">
                      <bean:write name="onlineApplicationForm" property="txnDate" />
                      </div></td>
            </tr>
            </table>
            </td>
            </tr>
            
            <tr ><td height="20"></td></tr>
            
		    <tr>
  		   <td  width="100%" align="center">
   		   <html:button property="" onclick="submitAdmissionForm('submitPaymentSucess')" styleClass="cntbtn" value="Continue"></html:button>
 		   </td>
 		   </tr>
            </logic:equal>
            
     <logic:equal value="false" name="onlineApplicationForm" property="paymentSuccess">
            
     <logic:equal value="Online" name="onlineApplicationForm" property="mode">
     
      <tr>
        <td>
        <table width="100%" border="0" cellpadding="0"  align="center" class=""   >
          <tr>
            <td width="100%" align="center" style="color:#C60000; font-weight:bold">Your Online Payment was Failed, please enter correct details, logout and login again. </td>
		 </tr>
        </table>
        </td>
      </tr>
       <tr>
  		   <td  width="100%" align="center">
   		   <html:button property="" onclick="cancel2()" styleClass="cancelbtn" value="Logout"></html:button>
   		   </td>
 		   </tr>
     </logic:equal>
     </logic:equal>
     
     
            
     <logic:equal value="false" name="onlineApplicationForm" property="applicantDetails.isChallanRecieved">
            
     <logic:equal value="Challan" name="onlineApplicationForm" property="mode">
     
      <tr>
        <td>
        <table width="100%" border="0" cellpadding="0"  align="center" class=""   >
          <tr>
            <td width="100%" align="center" style="color:#C60000; font-weight:bold">Your Payment Details are under Verification ,Please Click on Continue to Fill up the Application.</td>
		 </tr>
        </table>
        </td>
      </tr>
       <tr>
  		   <td  width="100%" align="center">
  		   <html:button property="" onclick="submitAdmissionForm('submitPaymentSucess')" styleClass="cntbtn" value="Continue"></html:button>
 			&nbsp; &nbsp; &nbsp;
   		   <html:button property="" onclick="cancel2()" styleClass="cancelbtn" value="Logout"></html:button>
   		   </td>
 		   </tr>
     </logic:equal>
     </logic:equal>
     
     <!-- neft -->
      <logic:equal value="false" name="onlineApplicationForm" property="applicantDetails.isChallanRecieved">
            
     <logic:equal value="NEFT" name="onlineApplicationForm" property="mode">
     
      <tr>
        <td>
        <table width="100%" border="0" cellpadding="0"  align="center" class=""   >
          <tr>
            <td width="100%" align="center" style="color:#C60000; font-weight:bold">Your Payment Details are under Verification ,Please Click on Continue to Fill up the Application.</td>
		 </tr>
        </table>
        </td>
      </tr>
       <tr>
  		   <td  width="100%" align="center">
  		   <html:button property="" onclick="submitAdmissionForm('submitPaymentSucess')" styleClass="cntbtn" value="Continue"></html:button>
 			&nbsp; &nbsp; &nbsp;
   		   <html:button property="" onclick="cancel2()" styleClass="cancelbtn" value="Logout"></html:button>
   		   
   		   </td>
 		   </tr>
     </logic:equal>
     </logic:equal>
    
    <%-- 
      <logic:equal value="true" name="onlineApplicationForm" property="applicantDetails.isChallanRecieved">
     
      <tr>
        <td>
        <table width="100%" border="0" cellpadding="0"  align="center" class=""   >
          <tr>
            <td width="100%" align="center" style="color:#C60000; font-weight:bold">Your Payment Details are Verified Successfully , Please Click on Continue to Fill up the Application.</td>
		 </tr>
		 <tr ><td height="20"></td></tr>
		  <tr>
  		<td  width="100%" align="center">
   		<html:button property="" onclick="submitAdmissionForm('submitPaymentSucess')" styleClass="cntbtn" value="Continue"></html:button>
 		</td>
 		 </tr>
        </table>
        </td>
      </tr>
     </logic:equal>
    --%> 
     
      
  
  
  
   
   <tr ><td height="50px"></td></tr>
   
   
</table>
		
	


  