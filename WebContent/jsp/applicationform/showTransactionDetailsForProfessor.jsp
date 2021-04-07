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
	function cancel(){
		document.location.href = "applicationFormRegistration.do?method=initApplicationRegistration";
	}
    function print(method,mode){
    	document.getElementById("method").value=method;
    	document.getElementById("mode").value=mode;
    	document.applicationRegistrationForm.submit();
    }

</script>

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

 			
 	<html:form styleClass="well form-horizontal" action="/applicationFormRegistration"
	method="post">
	<html:hidden property="formName" value="applicationRegistrationForm" />
	<html:hidden property="method" value="" styleId="method" />		
	<html:hidden property="mode" styleId="mode" value="" />
<table width="80%" style="background-color: #F0F8FF" align="center">
  
    
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
	
  
     <logic:equal value="true" name="applicationRegistrationForm" property="paymentSuccess">
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
                      <bean:write name="applicationRegistrationForm" property="txnAmt" />
                      </div></td>
                     
            </tr>
            
             <tr>
                       <td width="50%" height="30" ><div align="right"><b>Transaction Id:</b></div></td>
                      <td width="50%" height="30" ><div align="left">
                      <bean:write name="applicationRegistrationForm" property="txnRefNo" />
                      </div></td>
             </tr>
             
             <tr>
                       <td width="50%" height="30" ><div align="right"><b>Transaction Date:</b></div></td>
                      <td width="50%" height="30" ><div align="left">
                      <bean:write name="applicationRegistrationForm" property="txnDate" />
                      </div></td>
             </tr>
             
             
            </table>
            </td>
            </tr>
            
            <tr ><td height="20"></td></tr>
            </logic:equal>
            
     <logic:equal value="false" name="applicationRegistrationForm" property="paymentSuccess">
            
     <logic:equal value="Online" name="applicationRegistrationForm" property="mode">
     
      <tr>
        <td>
        <table width="100%" border="0" cellpadding="0"  align="center" class=""   >
          <tr>
            <td width="100%" align="center" style="color:#C60000; font-weight:bold">Your Online Payment was Failed, please enter correct details, logout and login again. </td>
		 </tr>
        </table>
        </td>
      </tr>
       
     </logic:equal>
     </logic:equal>
            
     
      <tr>
      <td align="center">
       <logic:equal value="true" name="applicationRegistrationForm" property="paymentSuccess">
         
            <html:submit property=""  value="Print Application"
		styleId="submit"
		onclick="print('printApplicationForm','print'); return false;"></html:submit>
		   </logic:equal>
   		   <html:button property="" onclick="cancel()" styleClass="cancelbtn" value="Close"></html:button>
   		   </td>
 		   </tr>
  
  
  
   
   <tr ><td height="50px"></td></tr>
   
   
</table>
</html:form>
  