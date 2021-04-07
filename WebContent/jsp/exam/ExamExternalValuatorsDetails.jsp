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
	<html:form action="/valuationRemuPayment">
	<html:hidden property="formName" value="examValuationRemuPaymentForm" styleId="formName" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="10%"></td>
				<td width="80%">
					<table width="80%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" height="15" class="row-white" >
							<div align="left"><img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available" ></div><br></br></td>
						</tr>
						<tr>
							<td align="center" colspan="5">EXAMINATION DEPARTMENT-PAPER VALUATION UNIT</td>
							<td align="right">EX-NO: &nbsp;&nbsp;<bean:write name="examValuationRemuPaymentForm" property="challanNo"></bean:write></td>
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
			
			<table style='border:1px solid #000000' rules='all' width="100%">
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
						<td align="center"><bean:write name="examValuationRemuPaymentForm" property="totalBoardMeetings"/></td>
						<td align="center"><!--<bean:write name="examValuationRemuPaymentForm" property="boardMeeetingRate"/>--></td>
						<td align="center"><bean:write name="examValuationRemuPaymentForm" property="totalBoardMeetingCharge"/></td>
					</tr>
					<%int examCount = 0; %>
					<logic:notEmpty name="examValuationRemuPaymentForm" property="map">
					 	<logic:iterate id="to" name="examValuationRemuPaymentForm" property="map" indexId="counterMain">
					 		<%examCount = counterMain + 1; %>
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
				    <logic:equal value="true" name="examValuationRemuPaymentForm" property="printconveyence">
					<tr>
						<td></td>
						<td>Conveyance Charge</td>
						<td align="center"><bean:write name="examValuationRemuPaymentForm" property="totalNoOfConveyance"/> </td>
						<td align="center"><bean:write name="examValuationRemuPaymentForm" property="conveyanceCharge"/> </td>
						<td align="center"><bean:write name="examValuationRemuPaymentForm" property="totalConveyanceCharge"/> </td>
					</tr>
					</logic:equal>
					<logic:equal value="true" name="examValuationRemuPaymentForm" property="printTa">
					<tr>
						<td></td>
						<td>Travel Allowance</td>
						<td align="center">--</td>
						<td align="center">--</td>
						<td align="center"><bean:write name="examValuationRemuPaymentForm" property="ta"/> </td>
					</tr>
					</logic:equal>
					<logic:equal value="true" name="examValuationRemuPaymentForm" property="printDa">
					<tr>
						<td></td>
						<td> Dearness Allowance</td>
						<td align="center">--</td>
						<td align="center">--</td>
						<td align="center"><bean:write name="examValuationRemuPaymentForm" property="da"/> </td>
					</tr>
					</logic:equal>
					<logic:equal value="true" name="examValuationRemuPaymentForm" property="printOther">
					<tr>
						<td></td>
						<td> Others</td>
						<td align="center">--</td>
						<td align="center">--</td>
						<td align="center"><bean:write name="examValuationRemuPaymentForm" property="otherCost"/> </td>
					</tr>
					</logic:equal>
					<tr>
						<td></td>
						<td> Total</td>
						<td align="center">******</td>
						<td align="center">******</td>
						<td align="center"><bean:write name="examValuationRemuPaymentForm" property="grandTotal"/> </td>
					</tr>
				</table>
				
			</td>
		</tr>
					<tr>
						<td height="6px" colspan="6"></td>
					</tr>	
					<tr>
							<td align="left" colspan="6">Received with thanks from Paper Valuation Unit, Christ University, Bangalore - 560 029, a sum of Rs. <bean:write name="examValuationRemuPaymentForm" property="grandTotal"></bean:write></td>
						</tr>
						
						<tr>
							<td align="left" colspan="6">(Rupees:....<bean:write name="examValuationRemuPaymentForm" property="totalInWords"></bean:write>....) being the amount towards valuation/review of Papers completed as on <bean:write name="examValuationRemuPaymentForm" property="currentDate"/></td>
						</tr>	
						<tr height="6px"></tr>
						
						<tr>
						<td align="left" colspan="3">Name: <bean:write name="examValuationRemuPaymentForm" property="employeeName"/></td>
						<td align="left"  colspan="3">Department: <bean:write name="examValuationRemuPaymentForm" property="departmentName"/></td>
						</tr> 
						<tr>
						<td align="left" colspan="6">Address: <bean:write name="examValuationRemuPaymentForm" property="address"/></td>
						</tr> 
						<tr>
						<td align="left" colspan="3">Bank A/c NO: <bean:write name="examValuationRemuPaymentForm" property="accountNo"/> <br/> 
								<bean:write name="examValuationRemuPaymentForm" property="bankName"/>,&nbsp;
								<bean:write name="examValuationRemuPaymentForm" property="bankBranch"/>&nbsp;
								<logic:notEmpty name="examValuationRemuPaymentForm" property="bankIfscCode">
								(IFSC Code- <bean:write name="examValuationRemuPaymentForm" property="bankIfscCode"/>)
								</logic:notEmpty>
						</td>
						<td align="left" colspan="3">Pan#: <bean:write name="examValuationRemuPaymentForm" property="panNo"/></td>
						</tr>
						<tr>
						<td height="5px" colspan="6"></td>
						</tr>
						<tr>
							<td align="left" colspan="2">Place:</td><td align="left" colspan="1">Date:</td><td align="left" colspan="3">SIGNATURE</td>
						</tr>
						
						<!-- <tr>
						<td height="30px" colspan="6"></td>
						</tr>-->
						<tr>
							<td colspan="6">----------------------------------------------------------------------------------------------------------------------------------------------</td>
							<!-- <td>-----------------------</td><td>---------------------</td>
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
	
<script type="text/javascript">
	window.print();
</script>
	</html:form>
	</body>
</html>
