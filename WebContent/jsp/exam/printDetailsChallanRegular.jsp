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
function cancelAction(){
	document.location.href="StudentLoginAction.do?method=returnHomePage";
		}
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
<html:form action="/newSupplementaryImpApp">
	<html:hidden property="formName" value="newSupplementaryImpApplicationForm" />
	<html:hidden property="pageType" value="4" />
	<html:hidden property="method" styleId="method" value="calculateAmount" />
	
	
	<table width="100%" >
	
	<tr>
	<td width="32%"></td>
	<td width="2%"></td>
	<td width="32%"></td>
	<td width="2%"></td>
	<td width="32%" style="font-size: 12px;" align="right"><bean:write name="newSupplementaryImpApplicationForm" property="date" /></td>
	</tr>
	
	<tr>
	
	
	<td width="32%">
	
	<table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;">
			
		<tr>
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Bank Copy</b></td>
		</tr>			
			
		<tr>
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Examination Fee Payment Voucher</b></td>
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
		A/c Name : SACRED HEART COLLEGE PRINCIPAL EXAMINATIONS 
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		A/c No: 917010058248166
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		Challan No: <bean:write name="newSupplementaryImpApplicationForm" property="chalanNo"/>
		</td>
		</tr>
		
		 <tr>
		 <td colspan="2">Name of the Candidate:
			<bean:write name="newSupplementaryImpApplicationForm" property="nameOfStudent" /></td>
		 </tr>
		 
		 <tr>
		 <td style="font-size: 14px;" colspan="2" width="40%">Register No:
			
		 <bean:write name="newSupplementaryImpApplicationForm" property="regNo" /></td>
		 
		 </tr>
		
		 <tr>
		 <td style="font-size: 14px;" colspan="2" width="40%">Course:
			
		 <bean:write name="newSupplementaryImpApplicationForm" property="courseName" /></td>
		 
		 </tr>			
		<logic:equal value="false" property="isSupp" name="newSupplementaryImpApplicationForm">		
		<tr>
		<td width="50%">
		Examination Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="examFees"/>
		</td>
		</tr>
		</logic:equal>
		<logic:equal value="true" property="isSupp" name="newSupplementaryImpApplicationForm">
		<tr>
		<td width="50%">
		Registration Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="registrationFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		Paper Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalPaperFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		CV Camp Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalCvCampFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		MarkList Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="marksListFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		Application Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="applicationFees"/>
		</td>
		</tr>
		</logic:equal>
		
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
		   <logic:notEqual value="0.0" property="fineFees" name="newSupplementaryImpApplicationForm">
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="fineFees"/>
		  </logic:notEqual>
		</td>
		</tr>
		
		<tr>
		
		<td align="right" width="50%">
		<b>Total</b>
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalFees"/>
		</td>
		</tr>
		
		<tr>
		<td colspan="2" >
		[Rs.In words] &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalFeesInWords"/>
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
		Challan No:
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
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Examination Fee Payment Voucher</b></td>
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
		A/c Name : SACRED HEART COLLEGE PRINCIPAL EXAMINATIONS
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		A/c No: 917010058248166
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		Challan No: <bean:write name="newSupplementaryImpApplicationForm" property="chalanNo"/> 
		</td>
		</tr>
		
		 <tr>
		 <td colspan="2">Name of the Candidate:
			<bean:write name="newSupplementaryImpApplicationForm" property="nameOfStudent" /></td>
		 </tr>
		 
		 <tr>
		 <td style="font-size: 14px;" colspan="2" width="40%">Register No:
			
		 <bean:write name="newSupplementaryImpApplicationForm" property="regNo" /></td>
		 
		 </tr>
		
		 <tr>
		 <td style="font-size: 14px;" colspan="2" width="40%">Course:
			
		 <bean:write name="newSupplementaryImpApplicationForm" property="courseName" /></td>
		 
		 </tr>			
				
		<logic:equal value="false" property="isSupp" name="newSupplementaryImpApplicationForm">		
		<tr>
		<td width="50%">
		Examination Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="examFees"/>
		</td>
		</tr>
		</logic:equal>
		<logic:equal value="true" property="isSupp" name="newSupplementaryImpApplicationForm">
		<tr>
		<td width="50%">
		Registration Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="registrationFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		Paper Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalPaperFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		CV Camp Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalCvCampFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		MarkList Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="marksListFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		Application Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="applicationFees"/>
		</td>
		</tr>
		</logic:equal>
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
		<logic:notEqual value="0.0" property="fineFees" name="newSupplementaryImpApplicationForm">
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="fineFees"/>
		  </logic:notEqual>
		</td>
		</tr>
		
		<tr>
		
		<td align="right" width="50%">
		<b>Total</b>
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalFees"/>
		</td>
		</tr>
		
		<tr>
		<td colspan="2" >
		[Rs.In words]&nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalFeesInWords"/>
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
		Challan No:
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
		<td align="center" colspan="2" style="text-align: center; font-size: 14px;"><b>Examination Fee Payment Voucher</b></td>
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
		A/c Name : SACRED HEART COLLEGE PRINCIPAL EXAMINATIONS
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		A/c No: 917010058248166
		</td>
		</tr>
		<tr>
		<td colspan="2" >
		Challan No: <bean:write name="newSupplementaryImpApplicationForm" property="chalanNo"/> 
		</td>
		</tr>
		
		 <tr>
		 <td colspan="2">Name of the Candidate:
			<bean:write name="newSupplementaryImpApplicationForm" property="nameOfStudent" /></td>
		 </tr>
		 
		 <tr>
		 <td style="font-size: 14px;" colspan="2" width="40%">Register No:
			
		 <bean:write name="newSupplementaryImpApplicationForm" property="regNo" /></td>
		 
		 </tr>
		
		 <tr>
		 <td style="font-size: 14px;" colspan="2" width="40%">Course:
			
		 <bean:write name="newSupplementaryImpApplicationForm" property="courseName" /></td>
		 
		 </tr>			
				
		<logic:equal value="false" property="isSupp" name="newSupplementaryImpApplicationForm">		
		<tr>
		<td width="50%">
		Examination Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="examFees"/>
		</td>
		</tr>
		</logic:equal>
		<logic:equal value="true" property="isSupp" name="newSupplementaryImpApplicationForm">
		<tr>
		<td width="50%">
		Registration Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="registrationFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		Paper Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalPaperFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		CV Camp Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalCvCampFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		MarkList Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="marksListFees"/>
		</td>
		</tr>
		<tr>
		<td width="50%">
		Application Fee:  
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="applicationFees"/>
		</td>
		</tr>
		</logic:equal>
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
		<logic:notEqual value="0.0" property="fineFees" name="newSupplementaryImpApplicationForm">
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="fineFees"/>
		  </logic:notEqual>
		</td>
		</tr>
		
		<tr>
		
		<td align="right" width="50%">
		<b>Total</b>
		</td>
		<td >
		  &nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalFees"/>
		</td>
		</tr>
		
		<tr>
		<td colspan="2" >
		[Rs.In words]&nbsp;<bean:write name="newSupplementaryImpApplicationForm" property="totalFeesInWords"/>
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
		Challan No:
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
	<td width="32%" style="font-size: 10px;"></td>
	</tr>
	</table>
		
		

	
	
</html:form>


</body>
</html>
<script type="text/javascript"> window.print();</script>