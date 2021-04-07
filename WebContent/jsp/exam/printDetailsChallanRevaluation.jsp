<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<!--  
<style type="text/css" media="print">
  @page { size: landscape; }
</style>
-->

</head>
<body>
<html:form action="/newSupplementaryImpApp">
	<html:hidden property="formName" value="newSupplementaryImpApplicationForm" />
	<html:hidden property="pageType" value="4" />
	<html:hidden property="method" styleId="method" value="calculateAmount" />
	
	
	<table width="100%" >
	
	<tr>
	
	<td width="32%">
	
	<table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;">
			
		<tr>
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Bank Copy</b></td>
		</tr>			
			
		<tr>
				<logic:equal value="scrutiny" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Scrutiny Fee Payment Voucher</b></td></logic:equal>
			<logic:equal value="revaluation" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Revaluation Fee Payment Voucher</b></td></logic:equal>
		
		<logic:equal value="PG" name="newSupplementaryImpApplicationForm" property="programTypeName">
		
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Challenge Valuation Fee Payment Voucher</b></td></logic:equal>
		</tr>
							
		<tr>
		
		<td  align="center" colspan="2">
		<img src="images/header-logo.png" alt="Logo not available" height="70" width="200" ></img>
		</td>
		
		</tr>
		
		
		
		<tr>
		<td colspan="2" >
		Name of Examination: <bean:write name="newSupplementaryImpApplicationForm" property="examName" /> 
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		A/c  Name: Principal and Controller of Examination, SH College, Thevara,Cochin.
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		A/c No:  917010058248166
		</td>
		</tr>
		
			<tr>
		<td colspan="2" >
				Challan No:  <bean:write property="challanNo" name="newSupplementaryImpApplicationForm"/>
 
		</td>
		
		</tr>
		
		 <tr>
		 <td colspan="2">Name of the Candidate:
			<bean:write name="newSupplementaryImpApplicationForm" property="nameOfStudent" /></td>
		 </tr>
		 
		   <c:choose>
		   <c:when test="${newSupplementaryImpApplicationForm.regularAppAvailable==true}">
		    <tr>
			 <td style="font-size: 14px;" colspan="2" width="40%">Class No:
			
		 	<bean:write name="newSupplementaryImpApplicationForm" property="rollNo" /></td>
		 
		 	</tr>
		
		   </c:when>
		   <c:otherwise>
		    <tr>
		 	<td style="font-size: 14px;" colspan="2" width="40%">Register No:
			
		 	<bean:write name="newSupplementaryImpApplicationForm" property="registerNo" /></td>
		 
			 </tr>
		   </c:otherwise>
		   
		   </c:choose>
		
		
		 <tr>
		 <td style="font-size: 14px;" colspan="2" width="40%">Course:
			
		 <bean:write name="newSupplementaryImpApplicationForm" property="courseName" /></td>
		 
		 </tr>
			
				
		<tr>
		<logic:equal value="revaluation" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		<td width="50%">
		Revaluation Fee:  
		</td>
		</logic:equal>
		<logic:equal value="scrutiny" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		<td width="50%">
		Scrutiny Fee:  
		</td>
		</logic:equal>
		<logic:equal value="PG" name="newSupplementaryImpApplicationForm" property="programTypeName">
		<td width="50%">
		Challenge Valuation Fee:  
		</td>
		</logic:equal>
		<td >
		 <bean:write property="totalFees" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		<tr>
		<td width="50%">
		Application Fee:  
		</td>
		<td >
		  <bean:write property="applicationFees" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		<tr>
		<td width="50%">
		Condonation Fee:  
		</td>
		<td >
		  
		</td>
		</tr>
		
		<tr>
		<td width="50%">
		Fine [ if any]  
		</td>
		<td >
		   <bean:write property="fineFees" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		
		<tr>
		
		<td align="right" width="50%">
		<b>Total</b>
		</td>
		<td >
		   <bean:write property="applicationAmount" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		
		<tr>
		<td colspan="2" >
		[Rs.In words] <bean:write property="totalFeesInWords" name="newSupplementaryImpApplicationForm"/> 
		<br></br>  
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
	    <br>Challan No:<bean:write property="challanNo" name="newSupplementaryImpApplicationForm"/></br>
		<br></br>
		NB:Payable only at Axis Bank Ltd, Pandit Karuppan road, Thevara.
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
				<logic:equal value="scrutiny" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Scrutiny Fee Payment Voucher</b></td></logic:equal>
			<logic:equal value="revaluation" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Revaluation Fee Payment Voucher</b></td></logic:equal>
		
				<logic:equal value="PG" name="newSupplementaryImpApplicationForm" property="programTypeName">
		
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Challenge Valuation Fee Payment Voucher</b></td></logic:equal>
		</tr>
							
		<tr>
		
		<td  align="center" colspan="2">
		<img src="images/header-logo.png" alt="Logo not available" height="70" width="200" ></img>
		</td>
		
		</tr>
		
		
		
		<tr>
		<td colspan="2" >
		Name of Examination: <bean:write name="newSupplementaryImpApplicationForm" property="examName" /> 
		</td>
		</tr>
				<tr>
		<td colspan="2" >
		A/c  Name: Principal and Controller of Examination, SH College, Thevara,Cochin.
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		A/c No:  917010058248166
		</td>
		</tr>
		
			<tr>
		<td colspan="2" >
				Challan No:  <bean:write property="challanNo" name="newSupplementaryImpApplicationForm"/>
 
		</td>
		
		</tr>
		
		 <tr>
		 <td colspan="2">Name of the Candidate:
			<bean:write name="newSupplementaryImpApplicationForm" property="nameOfStudent" /></td>
		 </tr>
		 
		   <c:choose>
		   <c:when test="${newSupplementaryImpApplicationForm.regularAppAvailable==true}">
		    <tr>
			 <td style="font-size: 14px;" colspan="2" width="40%">Class No:
			
		 	<bean:write name="newSupplementaryImpApplicationForm" property="rollNo" /></td>
		 
		 	</tr>
		
		   </c:when>
		   <c:otherwise>
		    <tr>
		 	<td style="font-size: 14px;" colspan="2" width="40%">Register No:
			
		 	<bean:write name="newSupplementaryImpApplicationForm" property="registerNo" /></td>
		 
			 </tr>
		   </c:otherwise>
		   
		   </c:choose>
		
		
		 <tr>
		 <td style="font-size: 14px;" colspan="2" width="40%">Course:
			
		 <bean:write name="newSupplementaryImpApplicationForm" property="courseName" /></td>
		 
		 </tr>	
		 
		
				
		<tr>
		<logic:equal value="revaluation" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		<td width="50%">
		Revaluation Fee:  
		</td>
		</logic:equal>
		<logic:equal value="scrutiny" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		<td width="50%">
		Scrutiny Fee:  
		</td>
		</logic:equal>
				<logic:equal value="PG" name="newSupplementaryImpApplicationForm" property="programTypeName">
		<td width="50%">
		Challenge Valuation Fee:  
		</td>
		</logic:equal>
		<td >
		 <bean:write property="totalFees" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		
		<tr>
		<td width="50%">
		Application Fee:  
		</td>
		<td >
		  <bean:write property="applicationFees" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		
		<tr>
		<td width="50%">
		Condonation Fee:  
		</td>
		<td >
		  
		</td>
		</tr>
		<tr>
		<td width="50%">
		Fine [ if any]  
		</td>
		<td >
		   <bean:write property="fineFees" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		
		<tr>
		
		<td align="right" width="50%">
		<b>Total</b>
		</td>
		<td >
		   <bean:write property="applicationAmount" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		
		<tr>
		<td colspan="2" >
		[Rs.In words] <bean:write property="totalFeesInWords" name="newSupplementaryImpApplicationForm"/> 
		<br></br>  
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
	    <br>Challan No:<bean:write property="challanNo" name="newSupplementaryImpApplicationForm"/></br>
		<br></br>
		NB:Payable only at Axis Bank Ltd, Pandit Karuppan road, Thevara.
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
				<logic:equal value="scrutiny" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Scrutiny Fee Payment Voucher</b></td></logic:equal>
			<logic:equal value="revaluation" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Revaluation Fee Payment Voucher</b></td></logic:equal>
		
		<logic:equal value="PG" name="newSupplementaryImpApplicationForm" property="programTypeName">
		
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Challenge Valuation Fee Payment Voucher</b></td></logic:equal>
		</tr>
							
		<tr>
		
		<td  align="center" colspan="2">
		<img src="images/header-logo.png" alt="Logo not available" height="70" width="200" ></img>
		</td>
		
		</tr>
		
		
		
		<tr>
		<td colspan="2" >
		Name of Examination: <bean:write name="newSupplementaryImpApplicationForm" property="examName" /> 
		</td>
		</tr>
				<tr>
		<td colspan="2" >
		A/c  Name: Principal and Controller of Examination, SH College, Thevara,Cochin.
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		A/c No:  917010058248166
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		Challan No:  <bean:write property="challanNo" name="newSupplementaryImpApplicationForm"/>
		</td>
		
		</tr>
		
		
		 <tr>
		 <td colspan="2">Name of the Candidate:
			<bean:write name="newSupplementaryImpApplicationForm" property="nameOfStudent" /></td>
		 </tr>
		 
		   <c:choose>
		   <c:when test="${newSupplementaryImpApplicationForm.regularAppAvailable==true}">
		    <tr>
			 <td style="font-size: 14px;" colspan="2" width="40%">Class No:
			
		 	<bean:write name="newSupplementaryImpApplicationForm" property="rollNo" /></td>
		 
		 	</tr>
		
		   </c:when>
		   <c:otherwise>
		    <tr>
		 	<td style="font-size: 14px;" colspan="2" width="40%">Register No:
			
		 	<bean:write name="newSupplementaryImpApplicationForm" property="registerNo" /></td>
		 
			 </tr>
		   </c:otherwise>
		   
		   </c:choose>
		
		
		 <tr>
		 <td style="font-size: 14px;" colspan="2" width="40%">Course:
			
		 <bean:write name="newSupplementaryImpApplicationForm" property="courseName" /></td>
		 
		 </tr>	
		 

		<tr>
		<logic:equal value="revaluation" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		<td width="50%">
		Revaluation Fee:  
		</td>
		</logic:equal>
		<logic:equal value="scrutiny" property="isRevaluation" name="newSupplementaryImpApplicationForm">
		<td width="50%">
		Scrutiny Fee:  
		</td>
		</logic:equal>
				 <logic:equal value="PG" name="newSupplementaryImpApplicationForm" property="programTypeName">
		<td width="50%">
		Challenge Valuation Fee:  
		</td>
		</logic:equal>
		<td >
		 <bean:write property="totalFees" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		
		<tr>
		<td width="50%">
		Application Fee:  
		</td>
		<td >
		  <bean:write property="applicationFees" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		
		<tr>
		<td width="50%">
		Condonation Fee:  
		</td>
		<td >
		  
		</td>
		</tr>
		<tr>
		<td width="50%">
		Fine [ if any]  
		</td>
		<td >
		   <bean:write property="fineFees" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		
		<tr>
		
		<td align="right" width="50%">
		<b>Total</b>
		</td>
		<td >
		   <bean:write property="applicationAmount" name="newSupplementaryImpApplicationForm"/> 
		</td>
		</tr>
		
		<tr>
		<td colspan="2" >
		[Rs.In words] <bean:write property="totalFeesInWords" name="newSupplementaryImpApplicationForm"/> 
		<br></br>  
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
	    <br>Challan No:<bean:write property="challanNo" name="newSupplementaryImpApplicationForm"/></br>
		<br></br>
		NB:Payable only at Axis Bank Ltd, Pandit Karuppan road, Thevara.
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
	<td width="32%" style="font-size: 10px;">This copy to be attached along with the application form</td>
	</tr>
	</table>
		
		

	
	
</html:form>


</body>
</html>
<script type="text/javascript"> window.print();</script>