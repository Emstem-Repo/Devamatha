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
</head>
<body>
<html:form action="/newSupplementaryImpApp">
	<html:hidden property="formName" value="newSupplementaryImpApplicationForm" />
	<html:hidden property="pageType" value="4" />
	<html:hidden property="method" styleId="method" value="calculateAmount" />
	
	<nested:iterate property="mainList" indexId="count2"  name="newSupplementaryImpApplicationForm">
			
		<table width="100%" >
							
		<tr>
		
		<td width="70%" align="center" colspan="2">
		<img src="images/header-logo.png" alt="Logo not available" height="110" width="440" ></img>
		</td>
		<td>
		
		<table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;font-size: 12px;">
		 
		 
		 <tr>
		 <td align="center" style="border:none">Details of Fee Remitted
		 </td>
		 </tr>
		<tr>
		 <td  style="border:none"> <div align="left">Rs &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; :..................</div>
		 </td>
		 </tr>
		<tr>
		 <td  style="border:none"> <div align="left">Challan No:..........</div>
		 </td>
		 </tr>
		<tr>
		 <td  style="border:none"> <div align="left">Date &nbsp; &nbsp; &nbsp; &nbsp; :................</div>
		 </td>
		 </tr>
		</table>
		
		</td>
		</tr>
		
		<tr>
		<td colspan="3"></td>
		</tr>
		
		<tr>
		<td colspan="3" style="text-align: center; font-size: 17px;">
		<b> APPLICATION FOR REGISTRATION OF <bean:write name="newSupplementaryImpApplicationForm" property="examName" />,<bean:write name="newSupplementaryImpApplicationForm" property="month" />-<bean:write name="newSupplementaryImpApplicationForm" property="year" /> </b>
		</td>
		</tr>
		<tr>
		<td colspan="3"></td>
		</tr>
		 
		
		<tr>
		 <td colspan="3">
		 
		 <table width="100%" >
		 
		  <tr>
		 <td style="padding-left: 70px; font-size: 16px;" colspan="2" width="40%"><b>Register Number: &nbsp; &nbsp; <bean:write name="newSupplementaryImpApplicationForm" property="regNo" /></b>
			
		 </td><!--
		 <td style="padding-left: 10px;font-size: 16px;"><b><bean:write name="newSupplementaryImpApplicationForm" property="regNo" /></b></td>
		 
		 --></tr>
		</table>
		 </td>
		 </tr>
		 
		 
		 
		 
		 <tr>
		 <td colspan="3">
		 
		 <table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;font-size: 12px;">
		<!--<tr>
		 <td align="center" >1</td>
		 <td >Name of the Department</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="courseDep" /></td>
		 </tr>
		 --><tr>
		 <td align="center" >1</td>
		 <td >Name of the Programme</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="courseName" /></td>
		 </tr>
		 <tr>
		 <td align="center" >2</td>
		 <td >Semester</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="schemeNo" /></td>
		 </tr>
		 <tr>
		 <td align="center" width="5%" >3</td>
		 <td width="35%" >Examination appearing for  (Regular/ Supplementary/ Improvement)</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="examType" /></td>
		 </tr>
		
		 <tr>
		 <td height="25%" align="center" >4</td>
		 <td >Name of the Candidate
																	<br>[as in Xth or SSLC records]
		 </td>
		 <td height="25%" colspan="2" style="text-transform: uppercase;padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="nameOfStudent" /></td>
		 </tr>
		 
		 <tr>
		 <td height="25%" align="center" >5</td>
		 <td height="25%" >Address for Communication with Telephone Number & E-mail id</td>
		 <td height="25%" colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="address" />
		 <bean:write name="newSupplementaryImpApplicationForm" property="mobileNo" /> & 
		 <bean:write name="newSupplementaryImpApplicationForm" property="email" />
		 </td>
		 </tr>
		 <tr>
		 <td align="center" >6</td>
		 <td >Date of Birth</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="originalDob" /></td>
		 </tr>
		 <tr>
		 <td align="center" >7</td>
		 <td >Whether eligible for fee concession, If eligible, state the category</td>
		 <td width="15%" style=" padding-left: 10px;">YES/NO</td>
		 <td  style=" padding-left: 10px;">Category:....................................</td>
		 </tr>
		 
		  
		 <tr>
		 <td align="center" >8</td>
		 <td >Whether the attendance of the Candidate is satisfactory at the time of submitting the application/ shortage will be condoned.</td>
		  <td width="15%" style=" padding-left: 10px;">YES</td>
		 <td  style=" padding-left: 10px;">Applied for condonation<br>
		 (Specify):....................................</br></td>
		 </tr>
		 
		 
		 
		 </table>
		 
		 
		
		 </td>
		 
		 
		 
		 </tr>
		  <tr>
		 <td colspan="3"><br></br><br></br><br></br>		 </td>
		 </tr>
		 
		 <tr>
		 <td colspan="3">
		 <p align="center" style="font-size: 14px;"><br><b>Details of Courses to which Candidate is Appearing</b></p>
		 <table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;font-size: 12px;">
		<tr style="border-collapse: collapse;border: 1px solid balck;">
		 <td width="10%" style="text-align: center;">Type</td>
		 <td width="60%" style="padding-left: 10px">Title</td>
		 </tr>	
		 
		 <nested:notEmpty property="supSubjectList" name="newSupplementaryImpApplicationForm">
		 <nested:iterate property="supSubjectList" id="testType" indexId="count" name="newSupplementaryImpApplicationForm">
		 <c:if test="${testType.subjectType=='T'}">
		 <tr>
		 <td style="text-align: center;">Theory</td>
		 <td ><nested:write  property="subjectName" /></td>
		 
		 
		 </tr>
		
		 </c:if>
		 </nested:iterate>
		 
		  <tr style="border-collapse: collapse;border:none;padding: 2px">
		 <td colspan="2" ><br></br></td>
		 </tr>
		 
		
		 
		  <nested:iterate property="supSubjectList" id="testType" indexId="count" name="newSupplementaryImpApplicationForm">
		 <c:if test="${testType.subjectType=='P'}">
		   <tr>
		 <td style="text-align: center;">Practical</td>
		 <td ><nested:write  property="subjectName" /></td>
		 
		 
		 </tr>
		
		 <tr style="border-collapse: collapse;border:none;">
		 <td colspan="2" align="right"> Attested by Class Teacher </td>
		 
		 </tr>
		                                                                                                     
		 </c:if>
		 </nested:iterate>
		 </nested:notEmpty>
		 
		 </table>
		 <!--  
		  *  T-Theory , P-Practical and V-Viva Voce <br></br>
		 --> 
		 </td>
		 
		 
		 
		 </tr>
		  <tr><td colspan="3">
		  <table width="100%" >
		 
		  <tr>
		 
		 <td  width="35%" style="height: 100px;font-size: 12px;">Place:		<br></br>							
			Date:	 
				
		</td>
		<td width="30%" style="height: 100px;text-align: right;font-size: 12px;"><b>Signature of Student	</b> 	</td>
		 <td width="35%" style="height: 100px;text-align: right;font-size: 12px;"><b>Signature of the H.O.D	</b> </td>
		 
		 
		 
		 </tr>
		
		 
		 <!--  
		 <tr>
		 
		 <td width="35%" style="font-size: 12px;">	<b>Signature of Class Teacher/Mentor</b> </td>
		 <td width="30%">	 </td>
		 <td width="35%" style="text-align: right;font-size: 12px;"><b>Seal & Signature of the H.O.D</b>	 </td>
		 
		 
		 </tr>
		 -->
		 
		 <!--  
		 <tr>
		 <td colspan="3">
		 <table width="100%" border="0" style="border-collapse: collapse;font-size: 12px;">
		 <tr style="border-collapse: collapse;">
		 <td >Application Fee - 25</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Practical Paper - 50*1=50</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Marklist Fee - 30</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >CV Fee - 150</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Online Service Charge - 20</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Total=775</td>
		 </tr>
		 
		 
		  <tr style="border-collapse: collapse;">
		 
		 <td > <br></br> </td>
		 </tr>
		 
		 <tr style="border-collapse: collapse;">
		 
		 <td >NB:The students who are eligible for fee concession are required to remit R.S 20 only.</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >*  Attach the copy of the challan </td>
		 </tr>
		 </table>
		 		
		 </td>
		 </tr>
	
		-->
		 
		 </table>
		</td>
		</tr>
	
		
		</table>
		
		
		
	<table width="100%" >
							
							<tr height="10px">
							<td colspan="3" class="row-print-desc">
							<logic:notEmpty name="newSupplementaryImpApplicationForm" property="description">
								<c:out value="${newSupplementaryImpApplicationForm.description}" escapeXml="false"></c:out>
							</logic:notEmpty>
							</td>
							</tr>
	</table>	
	
</nested:iterate>
	
	
</html:form>


</body>
</html>
<script type="text/javascript"> window.print();</script>
