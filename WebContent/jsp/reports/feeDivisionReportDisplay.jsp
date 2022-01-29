<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function resetData() {
//	document.getElementById("billNo").value = "";
	resetErrMsgs();
}
function printDetails() {	
	window.print();
}
</script>
<html:form action="/FeeDivisionReport">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" value="divisionReportForm"/>
<html:hidden property="pageType" value="3"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top">
			<table width="100%" cellspacing="1" cellpadding="2" border="0"  >
			<tr>
				<td colspan="2">
					<logic:notEmpty name = "divisionList">
						<bean:define id="maxCount" name="divisionReportForm" property="maxAccCount"></bean:define>
						<% int maxAccCount = Integer.parseInt(maxCount.toString()); %>
						<logic:iterate id="div" name= "divisionList" indexId="count">
							<c:choose>
							<c:when test="${count == 0}">
								<table width="100%" cellspacing="1" cellpadding="2" border="0">
								<tr>
						      		<td align="center" class="heading"><bean:write name="divisionReportForm" property="organizationName"/></td>
						         </tr>
								  <tr>
						      	    <td align="center"> <span class="heading">DIVISIONS</span></td> 
						          </tr>
								</table>
								<table width="100%" cellspacing="0" cellpadding="0" border="1" >
								<tr>
									<td align="center" width = "5%">Sl No.</td>
									<td align="center" width = "20%">Division Name</td>
									<%for (int i = 0; i< maxAccCount; i++) {
										if(i == 0){%>
											<td align="center">Acc No.</td>
										<%}else {%>
											<td align="center"><font size="1.5">&nbsp;</font></td>
										<%} %>
									<%} %>
								</tr>
								<tr>
								<td align="center"><font size="1.5">  <c:out value="${count + 1}"></c:out> </font></td>
								<td align="center"><font size="1.5"><bean:write name="div" property="name"/></font></td>
								<%int singleCount = 0; %>
								<logic:iterate name="div" property="feeAccountList" id = "acc">
									<td align="center" width="10%"><font size="1.5"><bean:write name="acc" property="code"/>&nbsp;</font></td>
									<%singleCount++; %>
								</logic:iterate>
								<%for(int i = singleCount; i < maxAccCount; i++){%>
										<td align="center" width="10%"><font size="1.5">&nbsp;</font></td>	
								<%} %>
								</tr></table>
							</c:when>
							<c:otherwise>
							<c:if test="${(count) % 69 == 0 }">
								<table class="break">
								</table>
								<table width="100%" cellspacing="1" cellpadding="2" border="0">
									<tr>
							      		<td align="center" class="heading"><bean:write name="divisionReportForm" property="organizationName"/></td>
							         </tr>
									  <tr>
							      	    <td align="center"> <span class="heading">DIVISIONS</span></td> 
							          </tr>
								</table>
								<table width="100%" cellspacing="0" cellpadding="0" border="1" >
								<tr>
									<td align="center" width = "5%">Sl No.</td>
									<td align="center" width = "20%">Division Name</td>
									<%for (int i = 0; i< maxAccCount; i++) {
										if(i == 0){%>
											<td align="center">Acc No.</td>
										<%}else {%>
											<td align="center"><font size="1.5">&nbsp;</font></td>
										<%} %>
									<%} %>
								</tr>
								</table>
							</c:if>
								<tr>
								<td align="center"><font size="1.5">  <c:out value="${count + 1}"></c:out> </font></td>
								<td align="center"><font size="1.5"><bean:write name="div" property="name"/></font></td>
								<%int singleCount = 0; %>
								<logic:iterate name="div" property="feeAccountList" id = "acc">
									<td align="center" width="10%"><font size="1.5"><bean:write name="acc" property="code"/>&nbsp;</font></td>
									<%singleCount++; %>
								</logic:iterate>
								<%for(int i = singleCount; i < maxAccCount; i++){%>
										<td align="center" width="10%"><font size="1.5">&nbsp;</font></td>	
								<%} %>
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