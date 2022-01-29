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
	document.location.href = "employeeOnlineLeave.do?method=initAuthorizationLeave";
}
function returnEmployeeLeave(){
	document.getElementById("method").value="returnedPendingAuthorization";
	document.employeeOnlineLeaveForm.submit();
}
function clarifyEmployeeLeave(){
	document.getElementById("method").value="requestDocAuthorization";
	document.employeeOnlineLeaveForm.submit();
}
function displayReason(){
	document.getElementById("displayId").style.display = "block";
	document.getElementById("requestDoc").style.display = "none";
}
function requestDoc(){
	document.getElementById("requestDoc").style.display = "block";
	document.getElementById("displayId").style.display = "none";
}
function viewEmployeeLeaves(employeeId,empTypeId){
	document.location.href = "employeeOnlineLeave.do?method=viewReturnedReqPendingAuthorization&empId="+employeeId+"&empTypeId="+empTypeId;
}
</SCRIPT>

<html:form action="/employeeOnlineLeave">
	<html:hidden property="method" styleId="method" value="authorizedPendingAuthorization"/>
	<html:hidden property="formName" value="employeeOnlineLeaveForm" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.employee" /> <span class="Bredcrumbs">&gt;&gt;
			View Returned/Request Document&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> View Returned/Request Document</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
					<tr>
					<td height="20" colspan="2">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
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
													<td height="25" class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.admin.subject.subject.s1no" /></div>
													</td>
													<td height="25" class="row-odd" align="center">Select</td>
													<td height="25" class="row-odd" align="center">
													<bean:message key="knowledgepro.employee.employeeId" /></td>
													<td height="25" class="row-odd" align="center">
													<bean:message key="employee.info.reportto.empnm" /></td>
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
													<td class="row-odd">
													<div align="center">Approved By</div>
													</td>
													<td class="row-odd">
														<div align="center">View Leaves</div>
													</td>
												</tr>
												<logic:notEmpty name="employeeOnlineLeaveForm" property="applyLeaveTo">
												<nested:iterate name="employeeOnlineLeaveForm"
													property="applyLeaveTo" id="leaveList" indexId="count">
													
															<tr>
																<td height="25" class="row-even" width="3%">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td height="25" class="row-even" width="4%">
																<nested:checkbox property="checked"></nested:checkbox></td>
																<td height="25" class="row-even" align="center" width="5%">
																<bean:write name="leaveList" property="employeeId" /></td>
																<td height="25" class="row-even" align="center" width="12%">
																<bean:write name="leaveList" property="employeeName" /></td>
																<td height="25" class="row-even" align="center" width="10%">
																<bean:write name="leaveList" property="empLeaveType" /></td>
																<td class="row-even" align="center" width="8%"><bean:write
																	name="leaveList" property="fromDate" /></td>
																<td class="row-even" align="center" width="8%"><bean:write
																	name="leaveList" property="toDate" /></td>
																<td class="row-even" align="center" width="6%"><bean:write
																	name="leaveList" property="isHalfDay" /></td>
																<td width="5%" height="13" width="6%" class="row-even" align="center">
																<bean:write
																	name="leaveList" property="isAm" />
																</td>
																<td width="5%" height="25" class="row-even" align="center">
																<bean:write
																	name="leaveList" property="noOfDays" />
																</td>
																<td width="12%" height="25" class="row-even" align="center">
																<bean:write
																	name="leaveList" property="reason" />
																</td>
																<td width="12%" height="25" class="row-even" align="center">
																<bean:write
																	name="leaveList" property="status" />
																</td>
																<td width="15%" height="25" class="row-even" align="center">
																<bean:write
																	name="leaveList" property="approvedBy" />
																</td>
																<td width="5%" height="25" class="row-even" align="center">
																	<a href="javascript:void(0)" onclick="viewEmployeeLeaves('<bean:write name="leaveList" property="empId" />','<bean:write name="leaveList" property="empTypeId" />')">
										                   				View
											                   		</a>
																</td>
															</tr>
												</nested:iterate>
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
							<td height="35" align="center">
								<html:submit  value="Authorize" styleClass="formbutton"  property="" onclick="approveEmployeeLeave()"></html:submit>
								&nbsp;&nbsp;&nbsp;<html:button  value="Return" styleClass="formbutton" property="" onclick="displayReason()"></html:button>
								&nbsp;&nbsp;&nbsp;<html:button  value="Request Document" styleClass="formbutton" property="" onclick="requestDoc()"></html:button>
								&nbsp;&nbsp;&nbsp;<html:button  value="Close" styleClass="formbutton" onclick="loginPage()" property=""></html:button>
							</td>
				          </tr>
				          <tr height="50"></tr>
				          <tr>
				          	<td colspan="2">
				          		<div id="displayId">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
											 <tr>
									          	<td height="25" class="row-odd" width="40%"><div align="right">Reason:</div></td>
																        <td height="25" class="row-even"><html:textarea property="rejectedReason"></html:textarea> </td>
									          </tr>
									          <tr>
									          		<td height="35" align="right"> <html:button  value="Submit" styleClass="formbutton" property="" onclick="returnEmployeeLeave()"></html:button></td>
									          </tr>
									</table>
								</div>
								<div id="requestDoc">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
											 <tr>
									          	<td height="25" class="row-odd" width="40%"><div align="right">Reason:</div></td>
																        <td height="25" class="row-even"><html:textarea property="requestDocReason"></html:textarea> </td>
									          </tr>
									          <tr>
									          		<td height="35" align="right"> <html:button  value="Submit" styleClass="formbutton" property="" onclick="clarifyEmployeeLeave()"></html:button></td>
									          </tr>
									</table>
								</div>
				          	</td>
				          </tr>
		          		<logic:notEmpty name="details1" scope="request">
		          		   <tr>
				          	<td colspan="2">
								<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						              <tr>
						                <td ><img src="images/01.gif" width="5" height="5" /></td>
						                <td width="914" background="images/02.gif"></td>
						                <td><img src="images/03.gif" width="5" height="5" /></td>
						              </tr>
						              <tr>
						                <td width="5"  background="images/left.gif"></td>
						                <td valign="top">
						         			<table width="100%" cellpadding="1" cellspacing="2">
												    <tr class="row-odd" height="25">
														<td> Employee Leave Details</td>
														<td> Employee Name: &nbsp;&nbsp;<bean:write name="employeeOnlineLeaveForm" property="currentEmployeeName"></bean:write></td>
														<td>Employee Id: &nbsp;&nbsp;<bean:write name="employeeOnlineLeaveForm" property="currentFingerPrintId"></bean:write></td>
														<td></td>
													</tr>
													<tr class="row-odd" height="25">
														<td> Leave Type</td>
														<td> Leaves Allocated</td>
														<td> Leaves Sanctioned</td>
														<td> Leaves Remaining</td>
													</tr>
													<logic:iterate id="to"name="details1" scope="request">
														<tr class="row-even" height="25">
															<td><bean:write name="to" property="empLeaveTypeName"/> </td>
															<td><bean:write name="to" property="leavesAllocated"/> </td>
															<td><bean:write name="to" property="leavesSanctioned"/> </td>
															<td><bean:write name="to" property="leavesRemaining"/> </td>
														</tr>
													</logic:iterate>
												
										</table>
							            </td>
							               <td width="5" height="30"  background="images/right.gif"></td>
							              </tr>
							              <tr>
							                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							                <td background="images/05.gif"></td>
							                <td><img src="images/06.gif" /></td>
							              </tr>
						            </table>
				          	
				          	</td>
				          </tr>
						</logic:notEmpty>		
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
<script type="text/javascript">
document.getElementById("displayId").style.display = "none";
document.getElementById("requestDoc").style.display = "none";
</script>