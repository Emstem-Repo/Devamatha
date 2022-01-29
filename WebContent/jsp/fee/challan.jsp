<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<%@page import="com.kp.cms.utilities.CommonUtil"%>
<%@page import="com.kp.cms.forms.fee.FeePaymentForm"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<style type="text/css">
body
{
	font-family: sans-serif;
	font-size: 12;
	
}

</style>
<style type="text/css" media="print">
  	 @media print {
	  	 @page { size: landscape; } 
	 	 
	  	 #controls, .footer, .footerarea{ display: none; }
	  	 html, body {
	    	/*changing width to 100% causes huge overflow and wrap*/    	 
		    overflow: hidden;
		    background: #FFF; 
		    font-size: 9.5pt;
	  	 }
	  	.template { width: auto; left:0; top:0; }
	  	
	}
</style>
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
<%
	FeePaymentForm feePaymentForm = (FeePaymentForm) request
			.getAttribute("feePaymentForm");
%>

<html:form action="/FeePayment">
<table border="0" width="100%" style="border-collapse: collapse">
<tr>
<%
	for(int i=0; i<3; i++){
%>
<td  class="row-pri-odd">
<logic:notEmpty name="feePaymentForm" property="feePaymentDetails" > 
<% int totCount = 0;
	int totalAmount = 0;
	int amountPaid = 0;
	int balance = 0;
	int slno = 0;
%>

<logic:iterate id="feeAccountTest" name="feePaymentForm" property="feePaymentDetails" indexId="counter">
<% totCount = totCount + 1;
%>
</logic:iterate>
<logic:iterate id="feeAccount" name="feePaymentForm" property="feePaymentDetails" indexId="counter" >
<table border="1" style="border-collapse: collapse;page-break-after: always">
<tr>
<td>
<table border="0" width="100%" rules="rows">	
	<tr>
	<% if(i==0) {%>
	<td align="center" style="font-size:14px; font-family:Times New Roman;" colspan="2"><b>Bank Copy</b></td>
	<% } %>
	<% if(i==1) {%>
	<td align="center" style="font-size:14px; font-family:Times New Roman;" colspan="2"><b>Candidate Copy</b></td>
	<% } %>
	<% if(i==2) {%>
	<td align="center" style="font-size:14px; font-family:Times New Roman;" colspan="2"><b>Office Copy</b></td>
	<% } %>
	</tr>
	<tr>
		<td align="center" colspan="2" style="font-size:12px; font-family:Times New Roman;"><b>College & University Fee Payment Challan</b></td>
	</tr>
	<tr>
		<td  align="center" colspan="2">
		<img src="images/header-logo.png" alt="Logo not available" height="70" width="200" ></img>
		</td>
		
	</tr>	
	
	
	<tr>
		<td colspan="2" class="row-pri-odd" style="font-size:12px; font-family:Times New Roman;">
		A/c Name: SACRED HEART COLLEGE, THEVARA 
		</td>
	</tr>
	<tr>
		<td colspan="2" class="row-pri-odd" style="font-size:12px; font-family:Times New Roman;">
		A/c No:  
		<logic:iterate id="accname" name="feeAccount" property="value" indexId="c">
		<c:choose>
			<c:when test="${c == 0}">
			<bean:write name="accname" property="printAccountName"/>	
			</c:when>
		</c:choose>
		</logic:iterate>
		</td>
	</tr>
		<tr>	<td class="row-pri-odd" style="font-size:12px; font-family:Times New Roman;" >Challan No:&nbsp;<bean:write name="feePaymentForm" property="billNo"/></td>
	</tr>
	
	
	<%--<logic:iterate id="accname" name="feeAccount" property="value" indexId="c">
		<c:choose>
			<c:when test="${c == 0}">
			<tr>	
			<td class="row-pri-odd" align="center" colspan="2"><font style="font-family: monospace;"> <bean:write name="accname" property="printAccountName"/></font></td>	
			</tr>
			<tr><td colspan="2"></td></tr>				
			<tr><td colspan="2"></td></tr>				
			
			</c:when>
		</c:choose>
	</logic:iterate>
	--%>
	
	<tr>
		<td colspan="2" class="row-pri-even" style="font-size:12px; font-family:Times New Roman;">Name of the Candidate:&nbsp;<bean:write name="feePaymentForm" property="studentName"/></td>
	</tr>
		  
	<tr>
		<td colspan="2" class="row-pri-even" style="font-size:12px; font-family:Times New Roman;">Register No:&nbsp;
		<bean:write name="feePaymentForm" property="registrationNo" /></td>
	</tr>
	
	<tr>
	<td class="row-pri-even" colspan="2" style="font-size:12px; font-family:Times New Roman;">Course:&nbsp;<bean:write name="feePaymentForm" property="courseName"/></td>
	</tr><%--	<tr>
	<td class="row-pri-odd" style="font-size:12px; font-family:Times New Roman;"> Receipt No:&nbsp; <bean:write name="feePaymentForm" property="billNo"/></td>
	<td class="row-pri-odd" style="font-size:12px; font-family:Times New Roman;">Date: &nbsp;<bean:write name="feePaymentForm" property="challanPrintDate"/></td>
	</tr>
 --%>
	<tr>
<%--	<td class="row-pri-even" align="left" width="70%" style="font-size:12px; font-family:Times New Roman;" >Register Number & App.No:&nbsp; <bean:write name="feePaymentForm" property="applicationId"/></td>--%>
	<td colspan="2" class="row-pri-even" style="font-size:12px; font-family:Times New Roman;">Category:&nbsp;<bean:write name="feePaymentForm" property="feeCategoryName"/></td>
	</tr>
	<tr>
	<td colspan="2" class="row-pri-even" style="font-size:12px; font-family:Times New Roman;">Fee for the Year:&nbsp;<bean:write name="feePaymentForm" property="selectFeePayYear"/></td>
	
<%--	<td class="row-pri-even" align="left" width="30%" style="font-size:12px; font-family:Times New Roman;"  >Caste:&nbsp;<bean:write name="feePaymentForm" property="casteName"/></td>--%>
	</tr>
	
<%--	<tr><td class="row-pri-even" width="50%" align="left" ><font style="font-family: monospace; font-size: 11px"> Language:&nbsp;<bean:write name="feePaymentForm" property="secondLanguage"/></font></td></tr>--%>
		
	<%-- 
	<%String height="375px"; %>
	 <logic:equal value="3" name="feeAccount" property="key">
		<%height="330px"; %>
	</logic:equal> --%>

	<tr>
	<td colspan="2"><table border="1" width="100%" style="border-collapse: collapse">
	
		<%
		int total = 0;
	%>
	<c:set var="present" value="0"/>
	<logic:notEmpty name="feeAccount" property="value">
	<tr>
	<td></td>
	<td class="row-pri-odd"  style="font-size:12px; font-family:Times New Roman;">Item</td>
	<td class="row-pri-odd"  style="font-size:12px; font-family:Times New Roman;">Amount in Rupees</td>
	<td class="row-pri-odd"  style="font-size:12px; font-family:Times New Roman;">Bank Seal</td>
	</tr>
	<logic:iterate id="feeHeading" name="feeAccount" property="value" indexId="count">
	<bean:define id="count1" name="feeHeading" property="count"/>
								<bean:define id="amount" name="feeHeading" property="amountPaid" />
								<bean:define id="totamount" name="feeHeading" property="totalAmount" />
								<%
									slno = slno + 1;
									total = total+ Integer.parseInt(amount.toString());
									amountPaid = amountPaid+ Integer.parseInt(amount.toString());
									totalAmount = totalAmount + Integer.parseInt(totamount.toString());
								%>
	<tr>
	<td class="row-pri-odd"  style="font-size:12px; font-family:Times New Roman;"><%=slno %></td>
	<td class="row-pri-odd"  style="font-size:12px; font-family:Times New Roman;">
		<bean:write name="feeHeading" property="feeGroup"/>&nbsp;&nbsp;
	</td>
	<td class="row-pri-even" style="font-size:12px; font-family:Times New Roman;"><bean:write name="feeHeading" property="amountPaid"/>.00&nbsp;&nbsp;&nbsp;</td>
	<td></td>
	</tr>
	</logic:iterate>
	</logic:notEmpty>
	<%balance = totalAmount - amountPaid; %>
	
	<tr>
	<td></td>
		<td class="row-pri-odd" style="font-size:12px; font-family:Times New Roman; font-weight: bold;" align="right">Total</td>	
		<td class="row-pri-even" style="font-size:12px; font-family:Times New Roman;" > <%=total%>.00&nbsp;&nbsp;&nbsp;</td>
		<td></td>
	</tr>
	
	
	</table></td>
	</tr>
	<tr>
		<td colspan="2" class="row-pri-odd" style="font-size:12px; font-family:Times New Roman;">[Rs.In words] <%=CommonUtil.numberToWord(total)%></td>
	</tr>
	
	<%-- <c:choose>
	<c:when test="${counter == 1}">
	<tr>
		<td colspan="2" class="row-pri-even"   align="left"><font style="font-size:13;font-weight: 5px; font-family: monospace;"> Grand Total: (Recpt 1 + 2):
		&nbsp;&nbsp;<bean:write name="feePaymentForm" property="totalPaidAmt"/>0
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Balance:&nbsp;&nbsp;<%=balance %>.00</font> </td>
	</tr>
	</c:when>
	</c:choose>
	--%>
	<tr height="30">
		<td style="font-size:12px; font-family:Times New Roman;">
		<b>Signature of Remitter:</b>  
		</td>
		<td></td>
	</tr>
	<tr><td colspan="2" align="center" style="font-size:12px; font-family:Times New Roman;">For Office Use </td></tr>
	<tr height="30"><td><table><tr>
	<td colspan="2" style="font-size:12px; font-family:Times New Roman;">
		</td></tr>
		<tr height="50"><td colspan="2" style="font-size:12px; font-family:Times New Roman;">
		<b>Office Seal and Signature</b>  
		<br></br>  
		<br></br>  
	    <br>Challan No:&nbsp;<bean:write property="billNo" name="feePaymentForm"/>
		<br></br>
		NB:Payable only at 
		<logic:iterate id="accname1" name="feeAccount" property="value" indexId="c1">
		<c:choose>
			<c:when test="${c1 == 0}">
			<bean:write name="accname1" property="bankInfo"/>	
			</c:when>
		</c:choose>
		</logic:iterate>
		</td>	
		</tr>		
	</table></td>
	</tr>
	
	
</table>
</td></tr>
</table>
</logic:iterate>
</logic:notEmpty>
</td>
<%
	}
%>
</tr>
</table>

</html:form>
<script type="text/javascript">
window.print();
</script>
