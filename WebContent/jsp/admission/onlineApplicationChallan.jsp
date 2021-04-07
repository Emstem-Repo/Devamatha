<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript">

</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<style type="text/css" media="print">
  @page { size: landscape; }
</style>
</head>
<body>
<html:form action="/uniqueIdRegistration">
	<html:hidden property="formName" value="uniqueIdRegistrationForm" />
	<html:hidden property="pageType" value="" />
	<html:hidden property="method" styleId="method" value="forwardChallanTemplate" />
	
	
	<table width="100%" >
	
	<tr>
	<td width="32%"></td>
	<td width="2%"></td>
	<td width="32%"></td>
	<td width="2%"></td>
	<td width="32%" style="font-size: 12px;" align="right"></td>
	</tr>
	
	<tr>
	
	
	<td width="32%">
	
	<table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;">
			
		<tr>
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Bank Copy</b></td>
		</tr>			
			
		<tr>
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Admission Fee Payment Voucher</b></td>
		</tr>
							
		<tr>
		
		<td  align="center" colspan="2">
		<img src="images/header-logo.png" alt="Logo not available" height="70" width="200" ></img>
		</td>
		
		</tr>
		
		
		
		
		<tr>
		<td colspan="2" >
		A/c  Name: Principal and Controller of Admission. 
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		A/c No: 0842.53.121 
		</td>
		</tr>
		
		 <tr>
		 <td colspan="2">Name of the Candidate:<bean:write name="uniqueIdRegistrationForm" property="applicantName"/>
			</td>
		 </tr>
		 
		
		
				
				
		<tr>
		<td width="50%"  align="right">
		Admission Fee:   
		</td>
		<td >
		  <bean:write name="uniqueIdRegistrationForm" property="applicationAmount"/>
		</td>
		</tr>
		
		
		  
		
		
		<tr>
		
		<td align="right" width="50%">
		<b>Total</b>
		</td>
		<td >
		  <bean:write name="uniqueIdRegistrationForm" property="applicationAmount"/>
		</td>
		</tr>
		
		<tr>
		<td colspan="2" >
		[Rs.In words] <bean:write name="uniqueIdRegistrationForm" property="applicationAmountWords"/> only
		 
		</td>
		</tr>
		 
		<tr>
		<td colspan="2" style="font-size: 14px;">
		<b>Signature of Remitter:</b>  
		</td>
		</tr>
		 
		<tr>
		<td colspan="2" style="text-align: center; font-size: 14px;">
		For Office  Use  
		</td>
		</tr>
		
		<tr>
		<td colspan="2" style="font-size: 14px;">
		<b>Office Seal and Signature</b>  
		<br></br>  
		<br></br>  
		<br></br>
		Challan No:<bean:write name="uniqueIdRegistrationForm" property="challanRefNo"/> 
		<br></br>
		NB:Branch should mention DC Code on the student copy.
		</td>
		</tr>
		
		
		</table>
		
		
	</td>
	
	
	<td width="2%">
	
	
	</td>
	
	
	
	
	
	
	<td width="32%">
	
	<table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;">
			
		<tr>
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Candidate Copy</b></td>
		</tr>			
			
		<tr>
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Admission Fee Payment Voucher</b></td>
		</tr>
							
		<tr>
		
		<td  align="center" colspan="2">
		<img src="images/header-logo.png" alt="Logo not available" height="70" width="200" ></img>
		</td>
		
		</tr>
		
		
		
		
		<tr>
		<td colspan="2" >
		A/c  Name: Principal and Controller of Admission. 
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		A/c No: 0842.53.121 
		</td>
		</tr>
		
		 <tr>
		 <td colspan="2">Name of the Candidate:<bean:write name="uniqueIdRegistrationForm" property="applicantName"/>
			
		</td>
		 </tr>
		 
		
		
				
				
		<tr>
		<td width="50%"  align="right">
		Admission Fee:   
		</td>
		<td >
		  <bean:write name="uniqueIdRegistrationForm" property="applicationAmount"/>
		</td>
		</tr>
		
		
		  
		
		
		<tr>
		
		<td align="right" width="50%">
		<b>Total</b>
		</td>
		<td >
		  <bean:write name="uniqueIdRegistrationForm" property="applicationAmount"/>
		</td>
		</tr>
		
		<tr>
		<td colspan="2" >
		[Rs.In words] <bean:write name="uniqueIdRegistrationForm" property="applicationAmountWords"/> only
		 
		</td>
		</tr>
		 
		<tr>
		<td colspan="2" style="font-size: 14px;">
		<b>Signature of Remitter:</b>  
		</td>
		</tr>
		 
		<tr>
		<td colspan="2" style="text-align: center; font-size: 14px;">
		For Office  Use  
		</td>
		</tr>
		
		<tr>
		<td colspan="2" style="font-size: 14px;">
		<b>Office Seal and Signature</b>  
		<br></br>  
		<br></br>  
		<br></br>
		Challan No:<bean:write name="uniqueIdRegistrationForm" property="challanRefNo"/> 
		<br></br>
		NB:Branch should mention DC Code on the student copy.
		</td>
		</tr>
		
		
		</table>
		
		
	</td>
	
	
	<td width="2%">
	
	</td>
	
	
	
	
	<td width="32%">
	
	<table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;">
			
		<tr>
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Office Copy</b></td>
		</tr>			
			
		<tr>
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Admission Fee Payment Voucher</b></td>
		</tr>
							
		<tr>
		
		<td  align="center" colspan="2">
		<img src="images/header-logo.png" alt="Logo not available" height="70" width="200" ></img>
		</td>
		
		</tr>
		
		
		
		
		<tr>
		<td colspan="2" >
		A/c  Name: Principal and Controller of Admission. 
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		A/c No: 0842.53.121 
		</td>
		</tr>
		
		 <tr>
		 <td colspan="2">Name of the Candidate:<bean:write name="uniqueIdRegistrationForm" property="applicantName"/>
			</td>
		 </tr>
		 
		
		
		 		
				
		<tr>
		<td width="50%"  align="right">
		Admission Fee:   
		</td>
		<td >
		  <bean:write name="uniqueIdRegistrationForm" property="applicationAmount"/>
		</td>
		</tr>
		
		
		  
		
		
		<tr>
		
		<td align="right" width="50%">
		<b>Total</b>
		</td>
		<td >
		  <bean:write name="uniqueIdRegistrationForm" property="applicationAmount"/>
		</td>
		</tr>
		
		<tr>
		<td colspan="2" >
		[Rs.In words] <bean:write name="uniqueIdRegistrationForm" property="applicationAmountWords"/> only
		 
		</td>
		</tr>
		<tr>
		<td colspan="2" style="font-size: 14px;">
		<b>Signature of Remitter:</b>  
		</td>
		</tr>
		 
		<tr>
		<td colspan="2" style="text-align: center; font-size: 14px;">
		For Office  Use  
		</td>
		</tr>
		
		<tr>
		<td colspan="2" style="font-size: 14px;">
		<b>Office Seal and Signature</b>  
		<br></br>  
		<br></br>  
		<br></br>
		Challan No:<bean:write name="uniqueIdRegistrationForm" property="challanRefNo"/> 
		<br></br>
		NB:Branch should mention DC Code on the student copy.
		</td>
		</tr>
		
		
		</table>
		
	
			
	</td>
	
	
	</tr>
	<tr>
	<td width="32%"></td>
	<td width="2%"></td>
	<td width="32%"></td>
	<td width="2%"></td>
	<td width="32%" style="font-size: 10px;"></td>
	</tr>
	</table>
		
		

	
	
</html:form>


</body>
</html>
<script type="text/javascript"> window.print();</script>