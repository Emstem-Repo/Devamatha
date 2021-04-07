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
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript">

</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
<html:form action="/uniqueIdRegistration">
	<html:hidden property="formName" value="uniqueIdRegistrationForm" />
	<html:hidden property="pageType" value="" />
	<html:hidden property="method" styleId="method" value="forwardChallanTemplate" />
	
	
	<table width="100%" >
	
	<tr>
	<td height="10px"></td>
	</tr>
	
	<tr>
	<td>
	
	<table width="100%" border="0" style="border-collapse: collapse;border: 1px solid balck;">
			
							
		<tr>
		
		<td  align="center" colspan="2">
		
		<img src="images/admission/images/header-logo.png" width="600" height="120" align="top"/>
		
		</td>
		
		</tr>
		
		<tr>
		<td width="20%" height="30%" >
		<br></br>
		</td>
	
		</tr>
	
		
		
		<tr>
		<td colspan="2" height="30%" style="font-size: 14px;">
		Dear <bean:write name="uniqueIdRegistrationForm" property="applicantName"/>, please make  your NEFT transaction  at any bank (except South Indian Bank) by using following details.  
		</td>
		</tr>
		
		
		<tr>
		<td width="20%" height="30%" >
		<br></br>
		</td>
	
		</tr>
	
		<tr>
		<td width="20%" height="30%">College Name
		</td>
	
		<td height="30%">: <%= CMSConstants.COLLEGE_NAME %> Kuravilangad
		</td>
		</tr>
		
		 <tr>
		 <td height="30%">Destination  Bank</td>
		 <td height="30%">: <%= CMSConstants.BANK_NAME %>
			</td>
		 </tr>
		
		
		<tr>
		 <td height="30%">Bank Branch</td>
		 <td height="30%">: <%= CMSConstants.BANK_BRANCH %>
			</td>
		 </tr>
		 
		
		<tr>
		 <td height="30%">IFSC Code</td>
		 <td height="30%">: <%= CMSConstants.IFSC_CODE %>
			</td>
		 </tr>
		
				
		<tr>
		 <td height="30%">Beneficiary Account Number</td>
		 <td height="30%">: <%= CMSConstants.OTHERBANK_ACNO %><bean:write name="uniqueIdRegistrationForm" property="challanRefNo"/>
			</td>
		 </tr>
				
		<tr>
		<td height="30%">
		Amount
		</td>
		<td height="30%">
		  : <bean:write name="uniqueIdRegistrationForm" property="applicationAmount"/>
		</td>
		</tr>
		
		<tr>
		<td colspan="2" height="30%">
		<br></br>
		</td>
		</tr>
		  
		<tr>
		<td colspan="2" height="30%">
		Note:
		</td>
		</tr>
		<tr>
		<td colspan="2" height="30%">
		<table>
		<tr valign="top">
		<td height="30%">1.<br></td>
		<td rowspan="1">Please pay your Online Registration Fee  via NEFT  transaction to the above Account Number and IFS Code.</td>
		</tr>
		<tr valign="top">
		<td height="30%">2.</td>
		<td rowspan="1">For continuing the online registration process after payment, please use last 9 alphanumeric characters (<bean:write name="uniqueIdRegistrationForm" property="challanRefNo"/>)  as your Challan Number.</td>
		</tr>
		
		<tr valign="top">
		<td height="30%">3.</td>
		<td rowspan="1">If you wish to make payment through South Indian Bank then download the Challan provided in the Admission Portal</td>
		</tr>
		</table>
		</td>
		</tr>
		
		
		
		<tr>
		<td colspan="2" height="30%">
		<br></br>
		</td>
		</tr>
		
		
		<tr>
		<td colspan="2" >
		<table width="100%">
		<tr>
		<td width="50%">
		Thank You.
		</td>
		<td align="right" width="50%" style="padding-right: 100px;">
		Date:&nbsp; &nbsp; &nbsp; &nbsp;
		</td>
		</tr>
		</table>
		</td>
		</tr>
		
		
		</table>
		
		
	</td>
	

	</tr>
	
	
	
	<tr>
	<td height="10px"></td>
	</tr>
	
	
	</table>
		
		

	
	
</html:form>


</body>
</html>
<script type="text/javascript"> window.print();</script>