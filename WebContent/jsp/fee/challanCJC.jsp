<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.fee.feerecipt"/></title>
</head>
<script type="text/javascript">
function printMe()
{
	window.print();
}
function closeMe()
{
	window.close();
}
</script>
<body>
<html:form action="/FeePayment">

<html:hidden property="method" styleId="method" value="initPrintChallen"/>
<html:hidden property="formName" value="feePaymentForm"/>
<html:hidden property="pageType" value="3"/>
<logic:notEmpty name="feePaymentForm" property="bulkChallanList" >                
<logic:iterate id="student" name="feePaymentForm" property="bulkChallanList" indexId="cnt">
<% for(int i=0;i<3;i++ ){%>
<table width="100%" border="0" >
	<tr>
		<logic:notEmpty name="student" property="printChalanList" >                
        	<logic:iterate id="feeAccount" name="student" property="printChalanList" indexId="counter">
            	<td width="49%" valign="top">
            		<table width="100%" cellspacing="1" cellpadding="2">
                    	<bean:define id="count" name="feeAccount" property="count" type="java.lang.Integer"></bean:define>
                    	<tr height="130px">
							<td colspan="4">
								&nbsp;			
							</td>
                      	</tr>
						<tr>
							<td height="25" colspan="4"  >
								<div align="center">
									<span class="heading">
										<bean:message key="knowledgepro.fee.feerecipt"/>
										<i>&nbsp;
										<%if(i == 0)
										{ %>
	               							- Bank Copy
										<% 
										}
										else if (i == 1)
										{
										%>
	                						- Student Copy
										<%
										}
										else if (i == 2)
										{ %>
	                						- Institution Copy
										<%
										}
										%>
										</i>
									</span>
								</div>
							</td>
						</tr>
						<tr height="30px">
							<td colspan="4">
							
							</td>
						</tr>
						<tr>
                        	<td nowrap="nowrap"><bean:message key="knowledgepro.feepays.billno"/>:</td>
                        	<td nowrap="nowrap"><bean:write name="student" property="billNo"/></td>
                        	<td nowrap="nowrap"><b> A/c. No.&nbsp;:</b></td>
                        	<td nowrap="nowrap"><b><bean:write name="feeAccount" property="printAccountName"/></b></td>
                      	</tr>
                      	<tr>
                        	<td>Date:</td>
                        	<td><bean:write name="student" property="challanPrintDate"/> </td>
                        	<td>Time:</td>
                        	<td><bean:write name="student" property="chalanCreatedTime"/> </td>
                      	</tr>
                      	<tr>
                        	<td>Name:</td>
                        	<td colspan="3"><b> <bean:write name="student" property="studentName"/></b></td>
                        </tr>
                        <tr>	
                        	<td nowrap="nowrap">Reg No/ App No:</td>
                        	<td colspan="3" ><b> <bean:write name="student" property="applicationId"/></b> </td>
                      	</tr>
                      	<tr>
                        	<td>Class:</td>
                        	<td colspan="3"><bean:write name="student" property="className"/> </td>
                        </tr>
                      	<tr>
                        	<td height="266" colspan="4" valign="top"  >
                        		<table width="100%"  align="center" style="border: 1px solid black;" rules="cols">
                            		<tr>
                                    	<td style="border-bottom : 1px solid black;" height="25" colspan="2" rowspan="2" width="76%"><div align="center" class="heading">Descriptions</div></td>
                                    	<td style="border-bottom : 1px solid black;" height="17" colspan="2" width="24%"><div align="center">Amount</div></td>
                                  	</tr>
                                  	<tr>
                                    	<td style="border-bottom : 1px solid black;" width="35%" height="14" colspan="2" valign="bottom" align="right"><img src="images/rupee.jpg" height="12px" width="12px"> </td>
                                  	</tr>
									<tr height="160px">
										<td valign="top" height="150px" width="76%" colspan="2">
											<c:choose>
												<c:when test="${counter == 0}">
													<logic:notEmpty  name = "feeAccount" property="descList">
														<logic:iterate id="desc" name = "feeAccount" property="descList">
															 <bean:write name="desc"/>
														</logic:iterate>
													</logic:notEmpty>
												</c:when>
												<c:otherwise>	
													<logic:notEmpty name = "feeAccount" property="desc2List">
														<logic:iterate id="desc2" name = "feeAccount" property="desc2List">
															 <bean:write name="desc2"/>
														</logic:iterate>
													</logic:notEmpty>
												</c:otherwise>
											</c:choose>	
										</td>
										<td valign = "middle" align="right" height="150" width="24%" colspan="2">
											<bean:write name="feeAccount" property="nonAdditionalAmount"/>
										</td>
									</tr>
									<c:set var="addlAmt"><bean:write name = "feeAccount" property="additionalAMount"/></c:set>		
									<tr>
										<td colspan="2" width="76%" align="center"  style="border: 1px solid black;">
											<div align="center" class="heading"> Additional Fees</div>
										</td>
									</tr>
									<c:if test="${addlAmt > 0}">
                                  		<logic:notEmpty name = "feeAccount" property="additionalList">
											<logic:iterate id="addl" name = "feeAccount" property="additionalList" indexId="addcount">
	                                  			<tr>
	                                  				<td align="right"  colspan="2">
														<bean:write name="addl" property="feeGroupName"/>
													</td>
													<td align="right"  colspan="2">
														<bean:write name="addl" property="amount"/>
													</td>		
												</tr>
											</logic:iterate>
										</logic:notEmpty>
										<tr height="1px">
											<td colspan="2" style="border-bottom: 1px solid black;">&nbsp;</td>
											<td colspan="2" >&nbsp;</td>
										</tr>
									</c:if>
									<c:if test="${addlAmt <= 0}">
										<tr height="80px">
											<td colspan="2" style="border: 1px solid black;">&nbsp;</td>
											<td colspan="2" >&nbsp;</td>
										</tr>
									</c:if>
									<c:set var="isConcession"><bean:write name="feeAccount" property="isConcession"/></c:set>
									<c:set var="isInstallment"><bean:write name="feeAccount" property="isInstallment"/></c:set>
									<c:set var="isScholarShipAmt"><bean:write name="feeAccount" property="isScholarShipAmt"/></c:set>
									<tr>
                                    	<td height="20" align="right"  colspan="2">
                                    		Total
                                    	</td>
                                    	<td style="border: 1px solid black;" colspan="2" align="right">	
											<bean:write name="feeAccount" property="totalAmount"/>
                                    	</td>
                                  	</tr>
                                  	<c:if test="${isConcession == true}">
                                  	<tr>
                                    	<td height="20" align="right"  colspan="2">
                                    		Fee Concession
                                    	</td>
                                    	<td style="border: 1px solid black;" colspan="2" align="right">	
											<bean:write name="feeAccount" property="concessionAmt"/>
                                    	</td>
                                  	</tr>
                                  	</c:if>
                                  	<c:if test="${isInstallment == true}">
                                  	<tr>
                                    	<td height="20" align="right"  colspan="2">
                                    		Installment
                                    	</td>
                                    	<td style="border: 1px solid black;" colspan="2" align="right">	
											<bean:write name="feeAccount" property="installmentAmt"/>
                                    	</td>
                                  	</tr>
                                  	</c:if>
                                  	<c:if test="${isScholarShipAmt == true}">
                                  	<tr>
                                    	<td height="20" align="right"  colspan="2">
                                    		Scholarship
                                    	</td>
                                    	<td style="border: 1px solid black;" colspan="2" align="right">	
											<bean:write name="feeAccount" property="scholarShipAmt"/>
                                    	</td>
                                  	</tr>
                                  	</c:if>
                                  	<tr>
                                    	<td height="20" align="right"  colspan="2">
                                    		Net Amount
                                    	</td>
                                    	<td style="border: 1px solid black;" colspan="2" align="right">	
											<bean:write name="feeAccount" property="netAmount"/>
                                    	</td>
                                  	</tr>
								</table>
							</td>
						</tr>	
					    <tr>
                        	<td height="25" colspan="4"  > Billed By: &nbsp;&nbsp;<%=session.getAttribute("username")%></td>
                      	</tr>
					    <tr>
                        	<td height="25" colspan="4"  ><b>For Bank Use</b></td>
                      	</tr>
                      	<tr >
                        	<td height="25" colspan="4"  >Amount Received : &nbsp;&nbsp;<b><bean:write name="feeAccount" property="netAmount"/> &nbsp;&nbsp; (<bean:write name="student" property="currencyCode"/>)</b></td>
                      	</tr>
                      	<tr >
                        	<td height="25" colspan="4"  >Amount Received in Words: &nbsp; &nbsp;<bean:write name = "feeAccount" property="amountInWord"/></td>
                      	</tr>
						<c:choose>
						<c:when test="${counter == 1}">
                      	<tr >
                        	<td height="25" colspan="4"  align="left"><b>Grand Total:&nbsp;&nbsp; <bean:write name = "student" property="accwiseTotalPrintString"/> (<bean:write name="student" property="currencyCode"/>)</b></td>
                      	</tr>
						</c:when>
						<c:otherwise>
                      	<tr >
                        	<td height="25" colspan="4"  align="right">&nbsp;</td>
                      	</tr>
						</c:otherwise>
						</c:choose>
                      	<tr height="40">
                        	<td colspan="4" >&nbsp;</td>
                      	</tr>	
						<tr>
                        	<td height="25" colspan="4"  align="left"><i>Signature of the Receiving authority</i> </td>
                        </tr>
                        <tr>	
                        	<td colspan="4"><i> Bank seal with date</i></td>
                      	</tr>
                      	<tr>	
                        	<td colspan="4">(Amount to be paid in South Indian Bank,Christ University Branch,Bangalore-560029)</td>
                      	</tr>
					</table>
				</td>
				<c:if test="${counter == 0}">
				<td width="2%">
					&nbsp;
				</td>	
				</c:if>
			</logic:iterate> 
		</logic:notEmpty>
	</tr>
</table>

	<table class="break">
	</table>
<%
} %>
</logic:iterate>
<script type="text/javascript">
	window.print();
</script>

</logic:notEmpty>
<logic:empty name="feePaymentForm" property="bulkChallanList" >
<table width="100%" border="0" height="100%">
	<tr>
		<td valign="middle">
			No Records Found
		</td>
	</tr>
</table>	
</logic:empty>
</html:form>
</body>
