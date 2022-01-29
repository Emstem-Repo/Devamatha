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
function printDetails() {	
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
<html:form action="/ConcSlipBookReport">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" value="concessionSlipBookReportForm"/>
<html:hidden property="pageType" value="3"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top">
			<table width="100%" cellspacing="1" cellpadding="2" border="0"  >
			<tr>
				<td colspan="2">
					<logic:notEmpty name = "concessionBookList">
						<logic:iterate id="book" name= "concessionBookList" indexId="count">
							<c:choose>
							<c:when test="${count == 0}">
								<table width="100%" cellspacing="1" cellpadding="2" border="0">
								<tr>
						      		<td align="center" class="heading"><bean:write name="concessionSlipBookReportForm" property="organizationName"/></td>
						         </tr>
								  <tr>
						      	    <td align="center"> <span class="heading">CONCESSION SLIP BOOKS</span></td> 
						          </tr>
								</table>
								<table width="100%" cellspacing="0" cellpadding="0" border="1" >
								<tr>
									<td align="center" width = "20%">Type</td>
									<td align="center" width = "10%">Year</td>
									<td align="center" width = "20%">Book No.</td>
									<td align="center" width = "25%">Starting Voucher No.</td>
									<td align="center" width = "25%">Ending Voucher No.</td>
								</tr>
								<tr>
								<td align="center"><font size="1.5"><bean:write name="book" property="type"/></font></td>
								<td align="center"><font size="1.5"><bean:write name="book" property="year"/>&nbsp;</font></td>
								<td align="center"><font size="1.5"><bean:write name="book" property="bookNo"/>&nbsp;</font></td>
								<td align="center"><font size="1.5"><bean:write name="book" property="startVoucherNo"/>&nbsp;</font></td>
								<td align="center"><font size="1.5"><bean:write name="book" property="endVoucherNo"/>&nbsp;</font></td>
								</tr>
							</c:when>
							<c:otherwise>
							<c:if test="${(count) % 69 == 0 }">
								<table class="break">
								<br>
								</table>
								<table width="100%" cellspacing="1" cellpadding="2" border="0">
									<tr>
							      		<td align="center" class="heading"><bean:write name="concessionSlipBookReportForm" property="organizationName"/></td>
							         </tr>
									  <tr>
							      	    <td align="center"> <span class="heading">CONCESSION SLIP BOOKS</span></td> 
							          </tr>
								</table>
								<table width="100%" cellspacing="0" cellpadding="0" border="1" >
								<tr>
									<td align="center" width = "20%">Type</td>
									<td align="center" width = "10%">Year</td>
									<td align="center" width = "20%">Book No.</td>
									<td align="center" width = "25%">Starting Voucher No.</td>
									<td align="center" width = "25%">Ending Voucher No.</td>
								</tr>
							</c:if>
								<tr>
								<td align="center"><font size="1.5"><bean:write name="book" property="type"/></font></td>
								<td align="center"><font size="1.5"><bean:write name="book" property="year"/>&nbsp;</font></td>
								<td align="center"><font size="1.5"><bean:write name="book" property="bookNo"/>&nbsp;</font></td>
								<td align="center"><font size="1.5"><bean:write name="book" property="startVoucherNo"/>&nbsp;</font></td>
								<td align="center"><font size="1.5"><bean:write name="book" property="endVoucherNo"/>&nbsp;</font></td>
								</tr>
							</c:otherwise>
							</c:choose>
						</logic:iterate>
					</logic:notEmpty>
				</td>
			</tr>
			
			</table>
		</td>
	</tr>
</table>
</html:form>


</body>
</html>
<script type="text/javascript">printDetails();</script>