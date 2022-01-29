 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<style type="text/css" media="print">
  @page { size: landscape; }
 
</style>
<style>
 .label{
  font-size: 14px;
  font-weight: bold;
  }
</style>
</head>
<body>
<html:form action="/uniqueIdRegistration">
	<html:hidden property="formName" value="uniqueIdRegistrationForm" />
	<html:hidden property="pageType" value="" />
	<html:hidden property="method" styleId="method" value="forwardChallanTemplate" />
	
	
	<table width="100%" >
	
	<tr>
	<td width="28%"></td>
	<td width="2%"></td>
	<td width="28%"></td>
	<td width="2%"></td>
	<td width="46%" style="font-size: 12px;" align="right"></td>
	</tr>
	
	<tr>
	
	
	<td width="28%" align="center">
	<b>REMIT THROUGH SOUTH INDIAN BANK BRANCHES ONLY</b>
	<table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;">
			
		<tr>
	     <td colspan="3">
	     <div style="float: left; width: 70%;">
		<img src="images/admission/images/sib.jpg" alt="Logo not available" height="75" width="200" ></img>
		</div>
		<div style="text-align: left; font-size: 14px; float: right;width: 30%"><b>College Copy</b></div>
	     
	     </td>
		</tr>	
		<tr>
		<td colspan="3" height="40px"><div style="float: left;">Branch: ................................</div> <div style="float: right;">    Date...........................</div></td>
		
		</tr>		
		<tr>
		<td colspan="2" class="label">
		College Name: DEVA MATHA COLLEGE KURAVILANGAD
		</td>
		</tr>	
		<tr>
		<td colspan="2" class="label">
		A/c No: <%= CMSConstants.TIEUPBANK_ACNO %> 
		</td>
		</tr>
		<tr>
		<td colspan="2" class="label">
		A/c  Name: DEVA MATHA CHARITABLE TRUST 
		</td>
		</tr>
		
		<tr>
		<td class="label">Amount in figures </td>
		<td><bean:write name="uniqueIdRegistrationForm" property="applicationAmount"/></td>
		</tr>
		<tr>
		<td class="label">Amount in words </td>
		<td><bean:write name="uniqueIdRegistrationForm" property="applicationAmountWords"/></td>
		</tr>
		
		<tr>
		<td class="label">Application Id.</td>
		<td><%= CMSConstants.OTHERBANK_ACNO %><bean:write name="uniqueIdRegistrationForm" property="challanRefNo"/></td>
		</tr>
		
		 <tr>
		 <td class="label">Name of the Student:</td>
		 <td><bean:write name="uniqueIdRegistrationForm" property="applicantName"/></td>
		 </tr>
		 
		<tr>
		<td height="50" class="label">
		<b>Signature of Remitter:</b>  
		</td>
		<td></td>
		</tr>
		<tr>
		<td class="label">
		<b>Mobile Number</b>  
		</td>
		<td><bean:write name="uniqueIdRegistrationForm" property="applicantMobileNo"/></td>
		</tr>
		 
		<tr>
		<td colspan="2">
		<table width="100%">
		<tr>
		<td height="50" width="50%" class="label"><b>Entered By </b>  </td>
		<td width="50%">Authorised Signature</td>
		</tr>
		<tr height="30px"><td></td></tr>
		</table>
		</td>
		</tr>
				
		</table>
		
		
	</td>
	
	
	<td width="2%">
	
	
	</td>
	
	
	
	
	
	
	<td width="28%" align="center">
	<b>REMIT THROUGH SOUTH INDIAN BANK BRANCHES ONLY</b>
	<table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;">
			
		<tr>
	     <td colspan="3">
	     <div style="float: left; width: 70%;">
		<img src="images/admission/images/sib.jpg" alt="Logo not available" height="75" width="200" ></img>
		</div>
		<div style="text-align: left; font-size: 14px; float: right;width: 30%"><b>Student's Copy</b></div>
	     
	     </td>
		</tr>	
		<tr>
		<td colspan="3" height="40px"><div style="float: left;">Branch: ................................</div> <div style="float: right;">    Date...........................</div></td>
		
		</tr>	
		<tr>
		<td colspan="2" class="label">
		College Name: DEVA MATHA COLLEGE KURAVILANGAD
		</td>
		</tr>	
		<tr>
		<td colspan="2" class="label">
		A/c No: <%= CMSConstants.TIEUPBANK_ACNO %> 
		</td>
		
		</tr>
		<tr>
		<td colspan="2" class="label">
		A/c  Name: DEVA MATHA CHARITABLE TRUST
		</td>
		</tr>					
			
		 		
		
		<tr>
		<td class="label">Amount in figures </td>
		<td><bean:write name="uniqueIdRegistrationForm" property="applicationAmount"/></td>
		</tr>
		<tr>
		<td class="label">Amount in words </td>
		<td><bean:write name="uniqueIdRegistrationForm" property="applicationAmountWords"/></td>
		</tr>
		
		<tr>
		<td class="label">Application Id.</td>
		<td><%= CMSConstants.OTHERBANK_ACNO %><bean:write name="uniqueIdRegistrationForm" property="challanRefNo"/></td>
		</tr>
		
		 <tr>
		 <td class="label">Name of the Student:</td>
		 <td><bean:write name="uniqueIdRegistrationForm" property="applicantName"/></td>
		 </tr>
		 
		<tr>
		<td height="50" class="label">
		<b>Signature of Remitter:</b>  
		</td>
		<td></td>
		</tr>
		<tr>
		<td class="label">
		<b>Mobile Number</b>  
		</td>
		<td><bean:write name="uniqueIdRegistrationForm" property="applicantMobileNo"/></td>
		</tr>
		 
		<tr>
		<td colspan="2">
		<table width="100%">
		<tr>
		<td height="50" width="50%" class="label"><b>Entered By </b>  </td>
		<td width="50%">Authorised Signature</td>
		</tr>
		<tr height="30px"><td></td></tr>
		</table>
		</td>
		</tr>
				
		</table>
		
		
	</td>
	
	
	<td width="2%">
	
	</td>
	
	
	
	
	<td width="40%" height="100%" align="center">
	<b>REMIT THROUGH INFORMATION BANK - THIRD PARTY COLLECTIONS - VAN BRANCH ENTRY MODULE</b>
	<table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;">
			
		<tr>
	     <td colspan="3">
	     <div style="float: left; width: 70%;">
		<img src="images/admission/images/sib.jpg" alt="Logo not available" height="75" width="200" ></img>
		</div>
		<div style="text-align: left; font-size: 14px; float: right;width: 30%"><b>Bank Copy</b></div>
	     
	     </td>
		</tr>	
		<tr>
		<td colspan="3" height="40px"><div style="float: left;">Branch: ................................</div> <div style="float: right;">    Date...........................</div></td>
		
		</tr>	
		<tr>
		<td colspan="3" class="label">
		College Name: DEVA MATHA COLLEGE KURAVILANGAD
		</td>
		</tr>
		<tr>
		<td colspan="2" class="label">
		A/c No: <%= CMSConstants.TIEUPBANK_ACNO %> 
		</td>
		
		<%-- Denomination table --%>
		<td height="100%" width="8%" rowspan="12">
		  
		    <table border="1" height="100%" width="100%" style="border-collapse: collapse;border: 1px solid balck;">
		       <tr><td>Denom.</td><td>Pieces</td><td>Rs.&nbsp;&nbsp;&nbsp;</td><td>Ps</td></tr>
		       <tr><td>2000X</td><td></td><td></td><td></td></tr>
		       <tr><td>500X</td><td></td><td></td><td></td></tr>
		       <tr><td>200X</td><td></td><td></td><td></td></tr>
		       <tr><td>100X</td><td></td><td></td><td></td></tr>
		       <tr><td>50X</td><td></td><td></td><td></td></tr>
		       <tr><td>20X</td><td></td><td></td><td></td></tr>
		       <tr><td>10X</td><td></td><td></td><td></td></tr>
		       <tr><td>5X</td><td></td><td></td><td></td></tr>
		       <tr><td>Coins</td><td></td><td></td><td></td></tr>
		       <tr><td>Total</td><td></td><td></td><td></td></tr>
		      
		    </table>
		
		
		
		
		
		
		
		</td>
		
		
		</tr>
		<tr>
		<td colspan="2" class="label">
		A/c  Name: DEVA MATHA CHARITABLE TRUST
		</td>
		</tr>					
		 		
		
		<tr>
		<td class="label">Amount in figures </td>
		<td><bean:write name="uniqueIdRegistrationForm" property="applicationAmount"/></td>
		</tr>
		<tr>
		<td class="label">Amount in words </td>
		<td><bean:write name="uniqueIdRegistrationForm" property="applicationAmountWords"/></td>
		</tr>
		
		<tr>
		<td class="label">Application Id.</td>
		<td><%= CMSConstants.OTHERBANK_ACNO %><bean:write name="uniqueIdRegistrationForm" property="challanRefNo"/></td>
		</tr>
		
		 <tr>
		 <td class="label">Name of the Student:</td>
		 <td><bean:write name="uniqueIdRegistrationForm" property="applicantName"/></td>
		 </tr>
		 
		<tr>
		<td height="50" class="label">
		<b>Signature of Remitter:</b>  
		</td>
		<td></td>
		</tr>
		<tr>
		<td class="label">
		<b>Mobile Number</b>  
		</td>
		<td><bean:write name="uniqueIdRegistrationForm" property="applicantMobileNo"/></td>
		</tr>
		 
		<tr>
		<td colspan="2">
		<table width="100%">
		<tr>
		<td height="50" width="50%" class="label"><b>Entered By </b>  </td>
		<td width="50%">Authorised Signature</td>
		</tr>
		<tr height="30px"><td></td></tr>
		</table>
		</td>
		</tr>
				
		</table>
		
		
	</td>
	
	
	</tr>
	<tr>
	<td width="28%"></td>
	<td width="2%"></td>
	<td width="28%"></td>
	<td width="2%"></td>
	<td width="40%" style="font-size: 10px;"></td>
	</tr>
	</table>
		
		

	
	
</html:form>


</body>
</html>
<script type="text/javascript"> window.print();</script>