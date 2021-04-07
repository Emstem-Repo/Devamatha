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
function initPage(){
	document.location.href = "empViewMyAttendance.do?method=initViewEmployeeAttendance";
}
function viewPreviousMonthAttendance(){
	var url="empViewMyAttendance.do?method=getPreviousMonthsAttnDetails";
	myRef = window .open(url, "View My Attendance", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</SCRIPT>

<html:form action="/empViewMyAttendance">
	<html:hidden property="formName" value="viewMyAttendanceForm" />
	<html:hidden property="method" styleId="method" value=""/>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.employee" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.employee.attendance" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.employee.attendance" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
					<tr>
					<td colspan="4" class="heading" align="left">&nbsp;<div align="left"><bean:message
								key="knowledgepro.employee.viewAttendance.label" /></div>
					<div align="right"><a href="#" onclick="viewPreviousMonthAttendance()">View Previous Months Attendance</a></div></td>
					</tr>
					<tr>
					<td colspan="4" align="left" ><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
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
													<bean:write name="viewMyAttendanceForm" property="fingerPrintId" /></td>
													<td class="row-odd" align="center" width="16%"><bean:message
														key="employee.info.reportto.empnm" /></td>

													<td class="row-even" align="center" width="16%">
													<bean:write name="viewMyAttendanceForm" property="empName" /></td>
													<td class="row-odd" align="center" width="16%"><bean:message
														key="employee.info.code" />
													</td>
													<td class="row-even" align="center" width="16%">
													<bean:write name="viewMyAttendanceForm" property="empCode" /></td>
													
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
													<td height="25" class="row-odd" align="center" width="13%">
													<bean:message key="knowledgepro.employee.manualAttendanceEntry.attendanceDate" /></td>
													<td class="row-odd" align="center" width="13%"><bean:message
														key="knowledgepro.employee.manualAttendanceEntry.inTime" /></td>

													<td class="row-odd" align="center" width="13%"><bean:message
														key="knowledgepro.employee.manualAttendanceEntry.outTime" /></td>
												</tr>
												<logic:notEmpty name="viewMyAttendanceForm" property="empAttTo">
												<c:set var="temp" value="0" />
												<logic:iterate name="viewMyAttendanceForm"
													property="empAttTo" id="empList" >
													<c:choose>
												<c:when test="${temp == 0}">
															<tr>
																<td height="25" class="row-even" align="center" width="13%">
																<bean:write name="empList" property="attendanceDate" /></td>
																<td class="row-even" align="center" width="13%"><bean:write
																	name="empList" property="inTime" /></td>
																<td class="row-even" align="center" width="13%"><bean:write
																	name="empList" property="outTime" /></td>
															<c:set var="temp" value="1" /></tr>
												</c:when>
												<c:otherwise>
													<tr>
																<td height="25" class="row-white" align="center" width="13%">
																<bean:write name="empList" property="attendanceDate" /></td>
																<td class="row-white" align="center" width="13%"><bean:write
																	name="empList" property="inTime" /></td>
																<td class="row-white" align="center" width="13%"><bean:write
																	name="empList" property="outTime" /></td>
															<c:set var="temp" value="0" /></tr>
															</c:otherwise>
															</c:choose>
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
            <logic:equal name="viewMyAttendanceForm" property="viewEmpAttendance" value="true">
          			<html:button  value="Cancel" styleClass="formbutton" onclick="initPage()" property=""></html:button>
				</logic:equal>
				<logic:equal name="viewMyAttendanceForm" property="viewEmpAttendance" value="false">
          			<html:button  value="Cancel" styleClass="formbutton" onclick="loginPage()" property=""></html:button>
				</logic:equal>
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
