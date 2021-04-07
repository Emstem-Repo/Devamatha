<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link href="styles.css" rel="stylesheet" type="text/css"/>
<SCRIPT type="text/javascript">
function loginPage(){
	document.location.href = "LoginAction.do?method=loginAction";
}

</SCRIPT>

<html:form action="/employeeOnlineLeave">
	<html:hidden property="formName" value="employeeOnlineLeaveForm" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.employee" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.employee.leave" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.employee.leave" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
					<tr>
					<td colspan="4" class="heading" align="left">&nbsp; Online Leave Application Status</td>
					</tr>
						<tr>
							<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
								   <td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="1310" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
								    <td width="5" background="images/left.gif"></td>
								    <td height="25" colspan="4">
								        <table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td height="25" class="row-odd" width="16%">
													<div align="center"><bean:message
														key="knowledgepro.employee.employeeId" /></div>
													</td>
													<td height="25" class="row-even" align="center" width="16%">
													<bean:write name="employeeOnlineLeaveForm" property="fingerPrintId" /></td>
													<td class="row-odd" align="center" width="16%"><bean:message
														key="employee.info.reportto.empnm" /></td>

													<td class="row-even" align="center" width="16%">
													<bean:write name="employeeOnlineLeaveForm" property="employeeName" /></td>
													<td class="row-odd" align="center" width="16%"><bean:message
														key="employee.info.code" />
													</td>
													<td class="row-even" align="center" width="16%">
													<bean:write name="employeeOnlineLeaveForm" property="empCode" /></td>
													
												</tr>
										</table>
								    </td>
								    <td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif" ></td>
									<td><img src="images/06.gif" /></td>
								</tr>
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="1310" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									
									<td height="25" colspan="4">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.admin.subject.subject.s1no" /></div>
													</td>
													<td height="25" class="row-odd" align="center">
													<bean:message key="knowledgepro.attendance.activityattendence.leavetype" /></td>
													<td class="row-odd" align="center"><bean:message
														key="knowledgepro.feepays.startdate" /></td>

													<td class="row-odd" align="center"><bean:message
														key="knowledgepro.feepays.enddate" /></td>


													<td class="row-odd" align="center"><bean:message
														key="knowledgepro.employee.halfday" />
													</td>
													<td class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.employee.attendance.am/pm" /></div>
													</td>
													<td class="row-odd" >
													<div align="center"><bean:message
														key="knowledgepro.employee.days" /></div>
													</td>
													<td class="row-odd" >
													<div align="center"><bean:message
														key="knowledgepro.feepays.Reason" /></div>
													</td>
													<td class="row-odd">
													<div align="center">Status</div>
													</td>
												</tr>
												<logic:notEmpty name="employeeOnlineLeaveForm" property="applyLeaveTo">
												<logic:iterate name="employeeOnlineLeaveForm"
													property="applyLeaveTo" id="leaveList" indexId="count">
													
															<tr>
																<td height="25" class="row-even" width="5%">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td height="25" class="row-even" align="center" width="10%">
																<bean:write name="leaveList" property="empLeaveType" /></td>
																<td class="row-even" align="center" width="10%"><bean:write
																	name="leaveList" property="fromDate" /></td>
																<td class="row-even" align="center" width="10%"><bean:write
																	name="leaveList" property="toDate" /></td>
																<td class="row-even" align="center" width="8%"><bean:write
																	name="leaveList" property="isHalfDay" /></td>
																<td height="13" width="6%" class="row-even" align="center">
																<bean:write
																	name="leaveList" property="isAm" />
																</td>
																<td width="8%" height="25" class="row-even" align="center">
																<bean:write
																	name="leaveList" property="noOfDays" />
																</td>
																<td width="15%" height="25" class="row-even" align="center">
																<bean:write
																	name="leaveList" property="reason" />
																</td>
																<td width="15%" height="25" class="row-even" align="center">
																<bean:write
																	name="leaveList" property="status" />
																</td>
															</tr>
												</logic:iterate>
												</logic:notEmpty>
											</table>
											</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif" ></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						
						<tr>
							<td valign="top" class="news">
							
								<div align="center">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
				            <td width="49%" height="35" align="center">
								<html:button  value="Cancel" styleClass="formbutton" onclick="loginPage()" property=""></html:button>
							</td>
				          </tr>
						</table>
					</div>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
