<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>

</head>
<script type="text/javascript">
function getEmployeeLeavDetails(){
	var year = document.getElementById("academicYear").value;
	var fingerPrintId = document.getElementById("fingerPrintId").value;
	var empCode = document.getElementById("empCode").value;
	document.location.href = "ModifyEmployeeLeave.do?method=EmployeeLeaveDetails&academicYear="+year+"&fingerPrintId="+fingerPrintId+"&empCode="+empCode;
	
}
function resetFields(){
	resetFieldAndErrMsgs();
}

function cancelEmpLeave(id) {
	document.location.href = "ModifyEmployeeLeave.do?method=cancelEmployeeLeaveDetails&applyLeaveId="+id;
}
</script>
<html:form action="/ModifyEmployeeLeave" method="post">
<html:hidden property="pageType" value="3" />
<html:hidden property="formName" value="ModifyEmployeeLeaveForm" />
<html:hidden property="employeeId" styleId="employeeId" name="ModifyEmployeeLeaveForm" />

<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; Cancel Employee  Leave &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Cancel Employee  Leave</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield">*Mandatory fields</div>
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2"><tr><td width="34%" class="row-even">
            <table width="100%" cellspacing="1" cellpadding="2">
			<tr>
		 		<td width="20%"  height="25" class="row-odd"><div align="right" id="regLabel"><bean:message key="knowledgepro.employee.leave.employeeId"/>:</div></td>
		        <td width="20%"  height="25" class="row-even" align="left"><span class="star">
		        <html:text property="fingerPrintId" styleId="fingerPrintId" name="ModifyEmployeeLeaveForm" maxlength="10" onblur="getEmpDetails()"/>
		        </span></td>				
				<td width="10%" height="25" class="row-even" align="center">OR</td>
				<td width="20%"  height="25" class="row-odd"><div align="right"><bean:message key="employee.info.code"/>:</div></td>
		        <td width="20%"  height="25" class="row-even" align="left">
		           <span class="star">
		             <html:text property="empCode" styleId="empCode" name="ModifyEmployeeLeaveForm" maxlength="9" onblur="getEmpDetails()"/>
		           </span></td>		
              <tr>
                <td width="20%"  class="row-odd" ><div align="right"> Year:</div></td>
                <td width="20%" class="row-even">
                     <html:select  property="academicYear" styleId="academicYear" styleClass="combo" > 
                     	<cms:renderYear></cms:renderYear>
					</html:select>
					</td>
				<td width="10%"  class="row-even" ><div align="right"></div></td>
                <td width="20%" class="row-odd"></td>
				 <td width="20%"  class="row-even" ><div align="right"></div></td>
              </tr>
            </table></td>
                  </tr>
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right">
									<html:button property="" styleId="button" styleClass="formbutton" value="Submit"
										onclick="getEmployeeLeavDetails()"></html:button>
										
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<div align="left">
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetFields()"></html:button>
							</div>
							</td>
						</tr>
			</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      <tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">


							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" height="25" class="row-odd">
									<div align="center">Employee Name</div>
									</td>
									<td align="center" height="25" class="row-odd">
									<div align="center">Start Date</div>
									</td>
									<td class="row-odd">
									<div align="center">End Date</div>
									</td>
									<td class="row-odd">
									<div align="center">Leave Type</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.cancel" /></div>
									</td>
								</tr>
								<logic:notEmpty property="modifyEmployeeLeaveTOs" name="ModifyEmployeeLeaveForm">
									
									<logic:iterate id="leaveDetails" property="modifyEmployeeLeaveTOs" name="ModifyEmployeeLeaveForm">
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td width="4%" height="25">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td align="center" width="18%" height="25"><bean:write
											name="leaveDetails" property="employeeName" /></td>
										<td align="center" width="18%" height="25"><bean:write
											name="leaveDetails" property="startDate" /></td>
										<td align="center" width="18%" height="25"><bean:write
											name="leaveDetails" property="endDate" /></td>
										<td align="center" width="18%" height="25"><bean:write
											name="leaveDetails" property="leaveTypeId" /></td>
										
										<td width="7%" height="25">
										<div align="center"><img src="images/delete_icon.gif"
											width="16" height="18" style="cursor: pointer"
											onclick="cancelEmpLeave('<bean:write name="leaveDetails" property="id" />')" /></div>
										</td>
									</logic:iterate>
									
								</logic:notEmpty>
								
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
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="34" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
      
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>

</html:form>