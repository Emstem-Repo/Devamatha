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
		 <td  style="border:none"> <div align="left">Rs:<bean:write name="newSupplementaryImpApplicationForm" property="applicationAmount" /></div>
		 </td>
		 </tr>
		<tr>
		 <td  style="border:none"> <div align="left">Challan No:<bean:write name="newSupplementaryImpApplicationForm" property="challanNo" /></div>
		 </td>
		 </tr>
		<tr>
		 <td  style="border:none"> <div align="left">Date:<bean:write name="newSupplementaryImpApplicationForm" property="dateOfApplication" /></div>
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
		 <c:if test="${newSupplementaryImpApplicationForm.isRevaluation=='revaluation'}" >
		<b> APPLICATION FOR THE REVALUATION <bean:write name="newSupplementaryImpApplicationForm" property="examName" /> </b></c:if>
		 <c:if test="${newSupplementaryImpApplicationForm.isRevaluation=='scrutiny'}" >
		<b> APPLICATION FOR THE SCRUTINY OF <bean:write name="newSupplementaryImpApplicationForm" property="examName" /> </b></c:if>
		<logic:equal value="PG" name="newSupplementaryImpApplicationForm" property="programTypeName">
		<b> APPLICATION FOR THE CHALLENGE VALUATION OF <bean:write name="newSupplementaryImpApplicationForm" property="examName" /> </b></logic:equal>
		</td>
		</tr>
		<tr>
		<td colspan="3"></td>
		</tr>
		
		<!--  
		<tr>
		 <td colspan="3">
		 
		 <table width="100%" >
		 
		  <tr>
		 <td style="padding-left: 20px; font-size: 16px;" colspan="2" width="40%"><b>Permanent Register Number:</b>
			
		 </td>
		 <td style="padding-left: 10px;font-size: 16px;"><b><bean:write name="newSupplementaryImpApplicationForm" property="regNo" /></b></td>
		 
		 </tr>
		</table>
		 </td>
		 </tr>
		 
		 -->
		 
		 <tr>
		 <td colspan="3">
		 
		 <table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;font-size: 12px;">
		 
		
		  <tr>
		 <td height="25%" align="center" >1</td>
		 <td >Name of the Candidate
																	<br>[as in Xth or SSLC records]
		</td>
		 <td height="25%" colspan="2" style="text-transform: uppercase;padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="nameOfStudent" /></td>
		 </tr>
		 <tr>
		 <td align="center" >2</td>
		 <td >Name of the Programme</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="courseName" /></td>
		 </tr>
		 <tr>
		 <td align="center" width="5%" >3</td>
		 <td width="35%" >Class No</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="rollNo" /></td>
		 </tr>
		 <tr>
		 <td align="center" width="5%" >4</td>
		 <td width="35%" >Register No</td>
		 <td colspan="2" style=" padding-left: 10px;"><bean:write name="newSupplementaryImpApplicationForm" property="registerNo" /></td>
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
		 <td >Purpose of the application</td>
		 <td colspan="2" style="text-transform: uppercase;padding-left: 10px;">
		 <c:if test="${newSupplementaryImpApplicationForm.isRevaluation=='revaluation'}" >
		 Revaluation
		 </c:if>
		  <c:if test="${newSupplementaryImpApplicationForm.isRevaluation=='scrutiny'}" >
		 Scrutiny
		 </c:if>
		 <logic:equal value="PG" name="newSupplementaryImpApplicationForm" property="programTypeName">
		 Challenge Valuation
		 </logic:equal>
		 
		 </td>
		 
		 </tr>
		
		 
		 </table>
		 
		
		 </td>
		 
		 
		 
		 </tr>
		 
		 <tr>
		 <td  colspan="3"><br></br>
		 <p style="font-size: 12px">8. Write down the details of the papers for <c:if test="${newSupplementaryImpApplicationForm.isRevaluation=='revaluation'}" >
		 Revaluation
		 </c:if>
		  <c:if test="${newSupplementaryImpApplicationForm.isRevaluation=='scrutiny'}" >
		 Scrutiny
		 </c:if>
		 <logic:equal value="PG" name="newSupplementaryImpApplicationForm" property="programTypeName">
		 Challenge Revaluation </logic:equal></p>
		 <table width="100%" border="1" style="border-collapse: collapse;border: 1px solid balck;font-size: 12px;">
		 <tr style="border-collapse: collapse;border: 1px solid balck;">
		 <td width="5%" style="text-align: center;">S.I.NO</td>
		 <td width="20%" style="text-align: center;">Course Code</td>
		 <td width="65%" style="text-align: center;">Course Title</td>
		 <%-- 
		 <td width="10%" style="text-align: center;">Marks Scored</td>
		 --%>
		 </tr>
		 <%
		 int total=0;
		 int subTotal=0;
		 int theotyTotal=0;
		 int i=1;
		 %>
		 <nested:notEmpty property="examList">
		 <nested:iterate property="examList" indexId="count1">
		 <nested:notEmpty property="toList">
		 <nested:iterate property="toList" indexId="count">
		<nested:equal value="true" property="tempChecked">
		
		 <tr>
		 <td style="text-transform: uppercase; text-align: center;"><%= i++ %></td>
		 <td style="text-transform: uppercase; text-align: center;"><nested:write  property="subjectCode" /></td>
		 <td ><nested:write  property="subjectName" /></td>
		 <%-- 
		 <td ><nested:write  property="marks" /></td>
		 --%>
		 </tr>
		  <%
		
		 	subTotal++;
		 
		 %>
		 </nested:equal>
		 </nested:iterate>
		 </nested:notEmpty>
		 </nested:iterate>
		 </nested:notEmpty>
		
		 
		 </table>
		 
		  
		 </td>
		 
		 
		 
		 </tr>
		 <tr> <td colspan="3">
		  <table width="100%" >
		  
		 <tr><td colspan="3" style="height: 50px;font-size: 12px">
		 9. Total amount of fees remitted: <b><bean:write name="newSupplementaryImpApplicationForm" property="applicationAmount" /></b>
		 </td></tr>
		 
		  <tr>
		 
		 <td  width="35%" style="font-size: 12px;">Place:		<br></br>							
			Date:	 
			
		</td>
		<td width="30%">	 </td>
		 <td width="35%" style="text-align: right;font-size: 12px;"><b>Signature of Student	</b> </td>
		 
		 
		 
		 </tr>
		 
		 
		 
		 
		 <!--  
		 <tr>
		 <td width="35%" style="font-size: 12px;">	<b>Signature of Class Teacher/Mentor</b> </td>
		 <td width="30%">	 </td>
		 <td width="35%" style="text-align: right;font-size: 12px;"><b>Seal & Signature of the H.O.D</b>	 </td>
		 
		 
		 </tr>
		 -->
		 
		 <%-- 
		 <%theotyTotal=150*subTotal; %>
		<% total=theotyTotal+50+150+20+25;%>
		 <tr>
		 <td colspan="3">
		 <table width="100%" border="0" style="border-collapse: collapse;font-size: 12px;">
		 <tr style="border-collapse: collapse;">
		 <td >Application Fee - 25</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Theory Paper - 150*<%=subTotal %> = <%=theotyTotal%></td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Marklist Fee - 50</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >CV Fee - 150</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Online Service Charge - 20</td>
		 </tr>
		 <tr style="border-collapse: collapse;">
		 
		 <td >Total=<%=total%></td>
		 </tr>
		 
		 
		  <tr style="border-collapse: collapse;">
		 
		 <td ><br></br> </td>
		 </tr>
		 
		<tr style="border-collapse: collapse;">
		 
		 <td >*  Attach the copy of the challan </td>
		 </tr>
		 </table>
		 		
		 </td>
		 </tr>
		
		--%>
		
		<%-- 
		<tr>
		 <td colspan="3">
		 <div align="center" style="font-size: 13px"> <b> FOR REVALUATION / SCRUTINY / THE XEROX COPY OF THE ANSWER SCRIPTS </b></div>
				<div align="left" style="font-size: 12px"> Application for the Revaluation or Scrutiny or for the Xerox copy of the answer scripts will be accepted in the college office from 31/08/2016 onwards</div>
				<div align="left" style="font-size: 12px"> Details of fees</div>
				<div align="left" style="font-size: 12px"> Revaluation 	- Rs 600 per paper</div>
				<div align="left" style="font-size: 12px"> Scrutiny 		- Rs 50 per paper</div>
				<div align="left" style="font-size: 12px"> Xerox copy of the answer scripts	- Rs 150 per paper</div>
				<div align="left" style="font-size: 12px"> Application Fee	- Rs 25</div>

				<div align="left" style="font-size: 12px"> Required fee should be remitted in the South Indian Bank, Devagiri College Branch in the prescribed fee challan available in the student portal of </div>
				<div align="left" style="font-size: 12px"> college website. Duly filled application form along with the college copy of the fee challan and downloaded mark list should be submitted in the </div>
				<div align="left" style="font-size: 12px"> college office on or before 15/09/2016</div>
				
		</td>
		</tr>
		
		--%>
		
		
		 </table>
		</td>
		</tr>
			
		
		</table>
		
		

</nested:iterate>	
	
</html:form>


</body>
</html>
<script type="text/javascript"> window.print();</script>
