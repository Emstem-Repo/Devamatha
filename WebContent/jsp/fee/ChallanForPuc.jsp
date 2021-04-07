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
<% for(int i=0;i<3;i++ ){%>
<table width="100%">
<tr><td>
	<logic:notEmpty name="feePaymentForm" property="printChalanList" >                
            <logic:iterate id="feeAccount" name="feePaymentForm" property="printChalanList" indexId="counter">
            <table width="100%" style="border-bottom: 1px dotted #000000;">
            	 <tr>
			<td>
			<div align="left">
			<bean:define id="count" name="feeAccount" property="count" type="java.lang.Integer"></bean:define>
			<img src='<%=request.getContextPath()%>/printChalanLogoServlet?count=<%=count%>' alt="Image not found"  height="50"/>
			</div>
			</td>
			<td>
			<div align="right">
			<span class="heading"><bean:message key="knowledgepro.fee.feerecipt"/> <i> &nbsp;
			<i>
			<%if(i == 0){ %>
			                   - Bank Copy
			<% }else if (i == 1){ %>
			                    - Student Copy
			<%} else if (i == 2){ %>
			                    - College Copy</i>
			<%} %>
			</i></span></div>
			</td>
        </tr>
		<tr>
			<td colspan="2">
				<table width="100%">
				<tr>
					<td height="10" align="right"><font size="2px"> <bean:message key="knowledgepro.feepays.billno"/>:</font></td>
					<td height="10"><font size="2px"><bean:write name="feePaymentForm" property="billNo"/></font> </td>
					<td height="10" align="right"><font size="2px"> Date:</font> </td>
					<td height="10"><font size="2px"><bean:write name="feePaymentForm" property="challanPrintDate"/></font></td>
					<td height="10" align="right" ><font size="2px"> Time: </font></td>
					<td height="10"><font size="2px"><bean:write name="feePaymentForm" property="chalanCreatedTime"/></font></td>
					<td height="10" align="right"><font size="2px">A/c.No.:</font> </td>
					<td height="10"><font size="2px"><B><bean:write name="feeAccount" property="printAccountName"/></B></font> </td>
				</tr>
				<tr>
					<td height="10" align="right"><font size="2px">Name:</font></td>
					<td height="10" colspan="3"><font size="2px"><bean:write name="feePaymentForm" property="studentName"/></font></td>
					<td height="10"><font size="2px">Reg.No./Appl.No.:</font></td>
					<td height="10"><font size="2px"><bean:write name="feePaymentForm" property="applicationId"/></font></td>
					<td height="10" align="right"><font size="2px">Class:</font></td>
					<td height="10"><font size="2px"><bean:write name="feePaymentForm" property="className"/></font></td>
				</tr>
				<tr>
				<td height="10" colspan="8" align="left"><b><font size="2px">DESCRIPTIONS</font> </b> </td>
				</tr>
				<tr>
				<td colspan="7" align="left">
					<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
						<logic:notEmpty  name = "feeAccount" property="descList">
						<logic:iterate id="desc" name = "feeAccount" property="descList">
						<tr>
						<td>
							<font size="2px"> <bean:write name="desc"/></font>
						</td>
						</tr>
						</logic:iterate>
						</logic:notEmpty>
					</table>
				</td>
				<td>
				<font size="2px"><bean:write name = "feeAccount" property="nonAdditionalAmount"/></font>
				</td>
				</tr>
				<c:set var="addlAmt"><bean:write name = "feeAccount" property="additionalAMount"/></c:set>		
				<c:if test="${addlAmt > 0}">
				<tr>
				<td colspan="7" align="left">
					<logic:notEmpty name = "feeAccount" property="additionalList">
					<logic:iterate id="addl" name = "feeAccount" property="additionalList" indexId="addcount">
						 <bean:write name="addl" property="feeGroupName"/>,
					</logic:iterate>
					</logic:notEmpty>
				</td>
				<td>
				<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
					<logic:notEmpty name = "feeAccount" property="additionalList">
						<logic:iterate id="addl" name = "feeAccount" property="additionalList" indexId="addcount">
						<tr> 
						<td>
							<font size="2px"><bean:write name="addl" property="amount"/></font>
						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>
				</table>
				</td>
				</tr>
				</c:if>
				<c:set var="isScholarShipAmt"><bean:write name="feeAccount" property="isScholarShipAmt"/></c:set>
				<c:set var="isInstallment"><bean:write name="feeAccount" property="isInstallment"/></c:set>
				<tr>
				<c:set var="isConcession"><bean:write name="feeAccount" property="isConcession"/></c:set>
				<td  align="left">Billed By </td>
				<td colspan="5" align="center"><br/><br/><b><font size="2px"> For Bank Use</font> </b></td>
				<td><font size="2px"><b> Total <br/>
				<c:if test="${isConcession == true}">
                    Fee Concession <br/>
				</c:if>
				<c:if test="${isScholarShipAmt == true}">
                    ScholarShip Amt <br/>
				</c:if>
				<c:if test="${isInstallment == true}">
					Installment Amt <br/>
				</c:if>
				  Net Amount</b></font></td>
				<td><font size="2px"><b> <bean:write name="feeAccount" property="totalAmount"/></b></font>
				<br/>
				<c:if test="${isConcession == true}">
                    <font size="2px"> <bean:write name="feeAccount" property="concessionAmt"/></font><br/>
				</c:if>
				<c:if test="${isScholarShipAmt == true}">
                    <font size="2px"> <bean:write name="feeAccount" property="scholarShipAmt"/></font><br/>
				</c:if>
				<c:if test="${isInstallment == true}">
                    <font size="2px"> <bean:write name="feeAccount" property="installmentAmt"/></font><br/>
				</c:if>
				<font size="2px"><B><bean:write name="feeAccount" property="netAmount"/></B></font>
				</td>
				</tr>
				<tr>
				<td colspan="8">
				<table>
					<tr>
					<td height="10"> <font size="2px"><b>Received Rs:</b></font></td>
					<td height="10"><font size="2px"><bean:write name="feeAccount" property="netAmount"/></font>&nbsp;&nbsp; </td>
					<td height="10"><font size="2px"> <b>In Words:</b></font> </td>
					<td height="10" ><font size="2px"><bean:write name = "feeAccount" property="amountInWord"/></font> </td>
					</tr>
					<bean:define id="last" name="feePaymentForm" property="lastNo"></bean:define>
					<c:if test="${counter == last}">
                      <tr >
                        <td height="10" colspan="4" align="right"><font size="2px"><b>Grand Total: &nbsp; <bean:write name = "feePaymentForm" property="accwiseTotalPrintString"/></b></font></td>
                      </tr>
					</c:if>
				</table>
				</td>
				</tr>
				<tr>
				 <td height="10" colspan="6" align="left"><i><font size="2px">Signature of the Receiving authority</font> </i>
				 </td><td colspan="2" align="right">
                    <i><font size="2px"> Bank seal with date</font> </i></td>
				</tr>
				</table>
			</td>
		</tr>
            </table>
            </logic:iterate>
            </logic:notEmpty>
            </td>
</tr>
</table>
<%if( i != 2){ %>
	<table class="break">
	</table>
<%} %>
<%} %>
</html:form>
</body>
<script type="text/javascript">
	window.print();
</script>
