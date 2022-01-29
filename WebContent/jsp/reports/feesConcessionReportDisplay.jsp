<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link href="css/styles.css" rel="stylesheet" type="text/css">

<script type="text/javascript">

function resetData() {
//	document.getElementById("billNo").value = "";
	resetErrMsgs();
}
function printPass() {	
	window.print();
}

</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<style>
checkprint
{
table {page-break-after:always}
}
</style>
</head>
<body>
<html:form action="/FeeConcessionReport">
<html:hidden property="method" styleId="method" value="printPassword"/>
<html:hidden property="formName" value="feesConcessionReportForm"/>
<html:hidden property="pageType" value="1"/>
<table width="100%" cellspacing="1" cellpadding="2" border="0"  >
<c:set var="isClasswise"><bean:write name="feesConcessionReportForm" property="classOrDate"/></c:set>
	<tr>
		<td align="center" class="heading"><bean:write name="feesConcessionReportForm" property="organizationName"/></td>
	 </tr>
	<tr>
		<td align="center" >FEE CONCESSION - DEGREE</font></td>
	</tr>
	<tr>
		<td >
		<% double totFee = 0; double totConc = 0; int lineNo = 0; %>
			<logic:notEmpty  name = "feesConcessionReportForm" property="classFeeConcessionList">
			<bean:define id="isClass" name="feesConcessionReportForm" property="classOrDate"></bean:define>
			<c:set var = "division"><bean:write name="feesConcessionReportForm" property="divisionName"/></c:set> 
			<table width="100%" cellspacing="0" cellpadding="2" border="1"  >
				<logic:iterate id="student"  name = "feesConcessionReportForm" property="classFeeConcessionList" indexId="count">
				<c:if test="${feesConcessionReportForm.divisionName != null && feesConcessionReportForm.divisionName != '' && count == 0}">
					<table width="100%" cellspacing="0" cellpadding="2" border="0"  >
					<tr>
					<td align="center" ><bean:write name="feesConcessionReportForm" property="divisionName"/></font></td>
					</tr>
					<%lineNo++; %>
					</table>
				</c:if>
				<%if(isClass.toString() == "true") {%>
					<table width="100%" cellspacing="0" cellpadding="2" border="0"  >
					<%if(isClass.toString() == "true"){ %>
						<tr>
							<td align="center" ><bean:write name="student" property="className"/></font></td>
						</tr>
						<%lineNo++; }%>
					</table>
				  <%} %>
					<%if(isClass.toString() == "true" || count == 0 ) {%>
						<% totFee = 0; totConc = 0; %>
					<table width="100%" cellspacing="0" cellpadding="0" border="1" >
						<tr>
							<td align="center" width="5%">Sl.No</font></td>
							<td align="center" width="10%">Date</td>
							<td align="center" width="20%">Name</td>
							<td align="center" width="10%">Appln Number</td>
							<td align="center" width="10%">Register Number</td>
							<td align="center" width="10%">Class</td>
							<td align="center" width="10%">Voucher No.</td>
							<td align="center" width="10%">Total Fees</td>
							<td align="center" width="10%">Concession Amount</td>
							<td align="center" width="5%">Cancelled</td>
						</tr>
						<%lineNo++; %>
					<%} %>
					<logic:iterate id="studentList" name= "student" property="concessionList" indexId="count" type="com.kp.cms.to.reports.FeeConcessionReportTO">
							<%boolean ejected = false;%>

							<%if((lineNo)%67 == 0 && count > 0) {%>
									<tr>
										<td align="center" ><font size="1.5">Total</font></td>
										<td align="center" ><font size="1.5">&nbsp;</font></td>
										<td align="center" ><font size="1.5">&nbsp;</font></td>
										<td align="center" ><font size="1.5">&nbsp;</font></td>
										<td align="center" ><font size="1.5">&nbsp;</font></td>
										<td align="center" ><font size="1.5">&nbsp;</font></td>
										<td align="center" ><font size="1.5">&nbsp;</font></td>
										<td align="center"><font size="1.5"><%=totFee%> </font></td>
										<td align="center" ><font size="1.5"><%=totConc%></font></td>
										<td align="center" ><font size="1.5">&nbsp;</font></td>
										<%ejected = true;%>
									</tr>
									</table>
									<table class="break">
									<br>
									</table>
									<table width="100%" cellspacing="0" cellpadding="0" border="1" >	
										<tr>
											<td align="center" width="5%">Sl.No</font></td>
											<td align="center" width="10%">Date</td>
											<td align="center" width="25%">Name</td>
											<td align="center" width="10%">Appln Number</td>
											<td align="center" width="10%">Register Number</td>
											<td align="center" width="10%">Class</td>
											<td align="center" width="10%">Voucher No.</td>
											<td align="center" width="10%">Total Fees</td>
											<td align="center" width="10%">Concession Amount</td>
											<td align="center" width="5%">Cancelled</td>
										</tr>
							<%lineNo++;} %>

							<%if(ejected){ %>
								<tr>
									<td align="center" ><font size="1.5">Total</font></td>
									<td align="center" ><font size="1.5">&nbsp;</font></td>
									<td align="center" ><font size="1.5">&nbsp;</font></td>
									<td align="center" ><font size="1.5">&nbsp;</font></td>
									<td align="center" ><font size="1.5">&nbsp;</font></td>
									<td align="center" ><font size="1.5">&nbsp;</font></td>
									<td align="center" ><font size="1.5">&nbsp;</font></td>
									<td align="center"><font size="1.5"><%=totFee%> </font></td>
									<td align="center" ><font size="1.5"><%=totConc%></font></td>
									<td align="center" ><font size="1.5">&nbsp;</font></td>
									<%ejected = false;%>
								</tr>
							<%lineNo++; } %>
							<tr>
							<td align="center"><font size="1.5"><bean:write name="studentList" property="slNo"/></font></td>
							<td align="center"><font size="1.5"><bean:write name="studentList" property="date"/>&nbsp;</font></td>
							<td align="center"><font size="1.5"><bean:write name="studentList" property="studentName"/>&nbsp;</font></td>
							<td align="center"><font size="1.5"><bean:write name="studentList" property="applNo"/>&nbsp;</font></td>
							<td align="center"><font size="1.5"><bean:write name="studentList" property="regNo"/>&nbsp;</font></td>
							<td align="center"><font size="1.5"><bean:write name="studentList" property="className"/>&nbsp;</font></td>
							<td align="center"><font size="1.5"><bean:write name="studentList" property="voucherNo"/>&nbsp;</font></td>
							<td align="center"><font size="1.5"><bean:write name="studentList" property="totalFees"/>&nbsp;</font></td>
							<td align="center"><font size="1.5"><bean:write name="studentList" property="concessionAmt"/>&nbsp;</font></td>
							<td align="center"><font size="1.5"><bean:write name="studentList" property="isCancelled"/>&nbsp;</font></td>
							</tr>
							
							<%
								lineNo++;
								totFee = totFee + studentList.getTotalFees();
								totConc = totConc + studentList.getConcessionAmt();
							%>
					
					</logic:iterate>
				<%if(isClass.toString() == "true") {%>
						<tr>
							<td align="center" ><font size="1.5">Total</font></td>
							<td align="center" ><font size="1.5">&nbsp;</font></td>
							<td align="center" ><font size="1.5">&nbsp;</font></td>
							<td align="center" ><font size="1.5">&nbsp;</font></td>
							<td align="center" ><font size="1.5">&nbsp;</font></td>
							<td align="center" ><font size="1.5">&nbsp;</font></td>
							<td align="center" ><font size="1.5">&nbsp;</font></td>
							<td align="center"><font size="1.5"><%=totFee%> </font></td>
							<td align="center" ><font size="1.5"><%=totConc%></font></td>
							<td align="center" ><font size="1.5">&nbsp;</font></td>
						</tr>
						<% lineNo++;%>
					</table>
					<%} %>
			</logic:iterate>
				<%if(isClass.toString() == "false") {%>
					<tr>
						<td align="center" ><font size="1.5">Total</font></td>
						<td align="center" ><font size="1.5">&nbsp;</font></td>
						<td align="center" ><font size="1.5">&nbsp;</font></td>
						<td align="center" ><font size="1.5">&nbsp;</font></td>
						<td align="center" ><font size="1.5">&nbsp;</font></td>
						<td align="center" ><font size="1.5">&nbsp;</font></td>
						<td align="center" ><font size="1.5">&nbsp;</font></td>
						<td align="center"><font size="1.5"><%=totFee%> </font></td>
						<td align="center" ><font size="1.5"><%=totConc%></font></td>
						<td align="center" ><font size="1.5">&nbsp;</font></td>
					</tr>

				<%lineNo++;} %>
			</table>
				
			</logic:notEmpty>
			</td>
	</tr>
</table>
</html:form>


</body>
</html>
<script type="text/javascript">printPass();
</script>