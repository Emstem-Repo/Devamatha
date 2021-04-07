	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	    pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
	<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
	<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
	<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
	<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
	<%@page import="com.kp.cms.constants.CMSConstants"%>
	<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="css/styles.css" rel="stylesheet" type="text/css">
	<style type="text/css">
			input {
			border:0;
			}
			</style>
	<script type="text/javascript" src="js/jquery.js">
		</script>
	</head>
	<body>
	<html:form action="/valuationChallan">
	<html:hidden property="formName" value="valuationChallanForm" styleId="formName" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="10%"></td>
				<td width="80%">
					<table width="80%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" class="row-white" >
							<div align="left"><img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available" ></div><br></br></td>
						</tr>
						<tr>
							<td align="center" colspan="5">EXAMINATION DEPARTMENT-PAPER VALUATION UNIT</td>
							<td align="right">I-NO: &nbsp;&nbsp;<bean:write name="valuationChallanForm" property="challanNo"></bean:write></td>
						</tr>
						<tr>
						<td height="4px" colspan="6"></td>
						</tr>
						<!-- <tr>
							<td align="center" colspan="5">PAPER VALUATION UNIT</td>
							<td></td>
						</tr>-->
						
						
						
						<tr>
			<td valign="top" colspan="6">
				<table style='border:1px solid #000000' rules='all' width="100%"  >
					<tr>
						<td align="center"><bean:message key="knowledgepro.slno"/></td>
						<td align="center">Name Of The Subject</td>
						<td align="center">No.of scripts</td>
						<td align="center">Rate</td>
						<td align="center">Total</td>
					</tr>
					<tr>
						<td></td>
						<td align="left"> No of Board meetings</td>
						<td align="center"><bean:write name="valuationChallanForm" property="totalBoardMeetings"/></td>
						<td align="center"><!--<bean:write name="valuationChallanForm" property="boardMeeetingRate"/>--></td>
						<td align="center"><bean:write name="valuationChallanForm" property="totalBoardMeetingCharge"/></td>
					</tr>
					<%int examCount = 0; %>
					<logic:notEmpty name="valuationChallanForm" property="map">
					 	<logic:iterate id="to" name="valuationChallanForm" property="map" indexId="counterMain">
					 	
					 			<tr>
									<td></td>
									<td align="left"><bean:write name="to" property="key"/></td>
									<td align="center">******</td>
									<td align="center">******</td>
								</tr>
								<logic:iterate id="to1" name="to" property="value" indexId="count">
					               	<tr>
						                <td align="center"><div align="center"><c:out value="${count + 1}"/></div></td>
						                <td align="left"><bean:write name="to1" property="value.subjectName"/></td>
						                <td align="center"><bean:write name="to1" property="value.answerScripts"/></td>
						                <td align="center"><bean:write name="to1" property="value.rate"/></td>
						                <td align="center"><bean:write name="to1" property="value.totalAmount"/></td>
					                </tr>
								</logic:iterate>
								<%examCount = counterMain + 1; %>
				       </logic:iterate>
				    </logic:notEmpty>
				    <logic:equal value="true" name="valuationChallanForm" property="printconveyence">
					<tr>
						<td></td>
						<td>Conveyance Charge</td>
						<td align="center"><bean:write name="valuationChallanForm" property="totalNoOfConveyance"/> </td>
						<td align="center"><bean:write name="valuationChallanForm" property="conveyanceCharge"/> </td>
						<td align="center"><bean:write name="valuationChallanForm" property="totalConveyanceCharge"/> </td>
					</tr>
					</logic:equal>
					<logic:equal value="true" name="valuationChallanForm" property="printTa">
					<tr>
						<td></td>
						<td>Travel Allowance</td>
						<td align="center">--</td>
						<td align="center">--</td>
						<td align="center"><bean:write name="valuationChallanForm" property="ta"/> </td>
					</tr>
					</logic:equal>
					<logic:equal value="true" name="valuationChallanForm" property="printDa">
					<tr>
						<td></td>
						<td> Dearness Allowance</td>
						<td align="center">--</td>
						<td align="center">--</td>
						<td align="center"><bean:write name="valuationChallanForm" property="da"/> </td>
					</tr>
					</logic:equal>
					<logic:equal value="true" name="valuationChallanForm" property="printOther">
					<tr>
						<td></td>
						<td> Others</td>
						<td align="center">--</td>
						<td align="center">--</td>
						<td align="center"><bean:write name="valuationChallanForm" property="otherCost"/> </td>
					</tr>
					</logic:equal>
					<tr>
						<td></td>
						<td> Total</td>
						<td align="center">******</td>
						<td align="center">******</td>
						<td align="center"><bean:write name="valuationChallanForm" property="grandTotal"/> </td>
					</tr>
					
				</table>
			</td>
		</tr>
					 <tr>
						<td height="6px" colspan="6"></td>
					</tr>	
					<tr>
							<td align="left" colspan="6">Received with thanks from Paper Valuation Unit, Christ University, Bangalore - 560 029, a sum of Rs. <bean:write name="valuationChallanForm" property="grandTotal"></bean:write></td>
						</tr>
						<tr>
						<td height="6px" colspan="6"></td>
						</tr>
						
						<tr>
							<td align="left" colspan="6">(Rupees:....<bean:write name="valuationChallanForm" property="totalInWords"></bean:write>....) being the amount towards valuation/review of Papers completed as on <bean:write name="valuationChallanForm" property="currentDate"/></td>
						</tr>	
						 <tr>
						<td height="6px" colspan="6"></td>
						</tr>
						<logic:equal name="valuationChallanForm" property="displayGuest" value="User">
						<tr>
						<td align="left"   colspan="3">Name: <bean:write name="valuationChallanForm" property="employeeName"/></td>
						<td align="left"  colspan="3">South Indian Bank A/c No: <bean:write name="valuationChallanForm" property="accountNo"/></td>
						
						</tr>
						<tr>
						<td align="left"  colspan="3">Dept/ID No: <bean:write name="valuationChallanForm" property="departmentName"/>/<bean:write name="valuationChallanForm" property="fingerPrintId"/></td>
						<td align="left" colspan="3">Pan#: <bean:write name="valuationChallanForm" property="panNo"/></td>
						
						</tr>
						</logic:equal>
						<logic:equal name="valuationChallanForm" property="displayGuest" value="Guest">
						<tr>
						<td align="left"   colspan="3">Name: <bean:write name="valuationChallanForm" property="employeeName"/></td>
						<td align="left"  colspan="3">Department: <bean:write name="valuationChallanForm" property="departmentName"/></td>
						</tr>
						<tr>
						<td align="left" colspan="6">Address: <bean:write name="valuationChallanForm" property="address"/></td>
						</tr> 
						<tr>
						<td align="left"  colspan="3">Bank A/c No: <bean:write name="valuationChallanForm" property="accountNo"/><br>
						&nbsp;
								<bean:write name="valuationChallanForm" property="bankBranch"/>&nbsp;
								<logic:notEmpty name="valuationChallanForm" property="bankIfscCode">
								(IFSC Code- <bean:write name="valuationChallanForm" property="bankIfscCode"/>)
								</logic:notEmpty>
						
						</td>
						<td align="left" colspan="3">Pan#: <bean:write name="valuationChallanForm" property="panNo"/></td>
						
						</tr>
						</logic:equal>
						
						<tr height="10px"></tr>
						<tr>
							<td align="left" colspan="2">Place:</td><td align="left" colspan="1">Date:</td><td align="left" colspan="3">SIGNATURE</td>
							
						</tr>
						<!-- <tr>
						<td colspan="6"></td>
						</tr>-->
						<tr>
						<td colspan="6">----------------------------------------------------------------------------------------------------------------------------------------------</td>
							<!-- <td>-----------------------</td><td>--------------------</td>
							<td>-----------------------</td><td>---------------------</td>
							<td>-----------------------</td><td>---------------------</td>-->
						</tr>
						<tr>
							
							<td align="center" colspan="6">PASSED FOR PAYMENTS</td>
							
							
						</tr>
						<tr>
						<td height="5px" colspan="6"></td>
						</tr>
						<tr>
							<td align="left" colspan="3">VICE CHANCELLOR</td><td align="right" colspan="3">CHIEF FINANCE OFFICER </td>
						</tr>
						<tr height="50px"></tr>
					</table>
				</td>
			</tr>
		</table>
		
			<p style="page-break-after:always;"> </p>			
		
		
 	<%-- 	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr height="40px"></tr>
		<tr>
			<td align="center" colspan="4"><strong>Christ University, Bangalore</strong></td>
		</tr>
		<tr>
			<td align="center" colspan="4"><strong>Valuation Unit-Remuneration Form</strong></td>
		</tr>		
		<!-- <tr>
			<td align="center" colspan="4"><strong>Remuneration Form</strong></td>
		</tr>-->
		<tr>
			<td align="left" colspan="2">Name:  &nbsp;&nbsp;&nbsp; <bean:write name="valuationChallanForm" property="employeeName"/></td>
			<td align="left" colspan="2">Date:  &nbsp;&nbsp;&nbsp;<bean:write name="valuationChallanForm" property="currentDate"/></td>
		</tr>		
		<tr>
			<td align="left" colspan="2">Department: &nbsp;&nbsp;&nbsp;<bean:write name="valuationChallanForm" property="departmentName"/></td>
			<td align="left" colspan="2"></td>
		</tr>		
		<tr>
			<td valign="top" colspan="4">
			
				<table style='border:1px solid #000000' rules='all' width="100%"  >
					<tr>
						<td align="center"><bean:message key="knowledgepro.slno"/></td>
						<td align="center">Name Of The Subject</td>
						<td align="center">No.of scripts</td>
						<td align="center">Rate</td>
						<td align="center">Total</td>
					</tr>
					<tr>
						<td></td>
						<td align="left"> No of Board meetings</td>
						<td align="center"><bean:write name="valuationChallanForm" property="totalBoardMeetings"/></td>
						<td align="center"><!--<bean:write name="valuationChallanForm" property="boardMeeetingRate"/>--></td>
						<td align="center"><bean:write name="valuationChallanForm" property="totalBoardMeetingCharge"/></td>
					</tr>
					<logic:notEmpty name="valuationChallanForm" property="map">
					 	<logic:iterate id="to" name="valuationChallanForm" property="map">
					 			<tr>
									<td></td>
									<td align="left"><bean:write name="to" property="key"/></td>
									<td align="center">******</td>
									<td align="center">******</td>
								</tr>
								<logic:iterate id="to1" name="to" property="value" indexId="count">
					               	<tr>
						                <td align="center"><div align="center"><c:out value="${count + 1}"/></div></td>
						                <td align="left"><bean:write name="to1" property="value.subjectName"/></td>
						                <td align="center"><bean:write name="to1" property="value.answerScripts"/></td>
						                <td align="center"><bean:write name="to1" property="value.rate"/></td>
						                <td align="center"><bean:write name="to1" property="value.totalAmount"/></td>
					                </tr>
								</logic:iterate>
				       </logic:iterate>
				    </logic:notEmpty>
				    <logic:equal value="true" name="valuationChallanForm" property="printconveyence">
					<tr>
						<td></td>
						<td>Conveyance Charge</td>
						<td align="center"><bean:write name="valuationChallanForm" property="totalNoOfConveyance"/> </td>
						<td align="center"><bean:write name="valuationChallanForm" property="conveyanceCharge"/> </td>
						<td align="center"><bean:write name="valuationChallanForm" property="totalConveyanceCharge"/> </td>
					</tr>
					</logic:equal>
					<logic:equal value="true" name="valuationChallanForm" property="printTa">
					<tr>
						<td></td>
						<td>Travel Allowance</td>
						<td align="center">--</td>
						<td align="center">--</td>
						<td align="center"><bean:write name="valuationChallanForm" property="ta"/> </td>
					</tr>
					</logic:equal>
					<logic:equal value="true" name="valuationChallanForm" property="printDa">
					<tr>
						<td></td>
						<td> Dearness Allowance</td>
						<td align="center">--</td>
						<td align="center">--</td>
						<td align="center"><bean:write name="valuationChallanForm" property="da"/> </td>
					</tr>
					</logic:equal>
					<logic:equal value="true" name="valuationChallanForm" property="printOther">
					<tr>
						<td></td>
						<td> Others</td>
						<td align="center">--</td>
						<td align="center">--</td>
						<td align="center"><bean:write name="valuationChallanForm" property="otherCost"/> </td>
					</tr>
					</logic:equal>
					<tr>
						<td></td>
						<td> Total</td>
						<td align="center">******</td>
						<td align="center">******</td>
						<td align="center"><bean:write name="valuationChallanForm" property="grandTotal"/> </td>
					</tr>
				</table>
				
			</td>
		</tr>
		<tr>
			<td colspan="4"> Total Amount In words: <bean:write name="valuationChallanForm" property="totalInWords"/></td>
		</tr>
		<tr height="40px"></tr>
		<tr>
			<td colspan="3" align="left"> Valuer's/reviewer's Signature</td>
			<td align="center">Custodian</td>
		</tr>
		<tr height="40px"></tr>
		</table>
		--%> 
		
		
		
<script type="text/javascript">
	window.print();
</script>
	</html:form>
	</body>
</html>
