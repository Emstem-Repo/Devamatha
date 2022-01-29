<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function cancelPage(){
	document.location.href="ModifyEmployeeLeave.do?method=EmployeeLeaveDetails";
}


</script>
<html:form action="/ModifyEmployeeLeave">
	<html:hidden property="method" styleId="method" value="cancelLeave" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="ModifyEmployeeLeaveForm" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; Cancel Employee  Leave &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Cancel Employee  Leave</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
		                             <td height="25" class="row-odd"><div align="right" id="regLabel"><bean:message key="knowledgepro.employee.leave.employeeId"/>:</div></td>
		                             <td height="25" class="row-even" align="left"><span class="star">
		                              <bean:write name="ModifyEmployeeLeaveForm" property="fingerPrintId"/> 
		                             </span></td>
		                             <td class="row-odd" align="right">
							  			Employee Name:
							  		</td>
							  		<td class="row-even" align="left">
							  				<bean:write name="ModifyEmployeeLeaveForm" property="employeeName"/>
							  		</td>
	                            </tr>
							  	<tr height="25">
							  		
							  		<td class="row-odd" align="right">
							  			Department Name:
							  		</td>
							  		<td class="row-even" align="left">
							  			
							  				<bean:write name="ModifyEmployeeLeaveForm" property="departmentName"/>
							  			
							  		</td>
							  		<td class="row-odd"  align="right">
							  			Designation:
							  		</td>
							  		<td class="row-even" align="left">
							  			
							  				<bean:write name="ModifyEmployeeLeaveForm" property="designationName"/>
							  			
							  		</td>
							  	</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>&nbsp;<bean:message key="knowledgepro.employee.holidays.startDate" />:</div>
									</td>
									<td class="row-even">
									<bean:write name="ModifyEmployeeLeaveForm" property="startDate"/>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>&nbsp;<bean:message key="knowledgepro.employee.holidays.endDate" />:</div>
									</td>
									<td height="25" class="row-even">
									<bean:write name="ModifyEmployeeLeaveForm" property="endDate"/>
									</td>
								</tr>
								<tr>
									<td class="row-even" align="right">
									Cancel Reason
									</td>
									<td class="row-even" colspan="2" align="left">
										<html:textarea property="cancelReason" cols="25" rows="4" ></html:textarea>
									
									</td>
									<td class="row-even"></td>
								</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton" onclick="cancelLeave()">
								<bean:message key="knowledgepro.submit" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" value="Back" onclick="cancelPage()">
								</html:button> 
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
