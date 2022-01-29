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
function resetMessages() {
	document.getElementById("toAM").checked =false;
	document.getElementById("fromAM").checked =false;
	document.getElementById("toAM1").checked =false;
	document.getElementById("fromAM1").checked =false;
	resetFieldAndErrMsgs();
}
function imposeMaxLength(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 50;
	return (Object.value.length < MaxLen);
}
function cancelDirect(id)
{
	document.getElementById("id").value=id;
	document.getElementById("method").value="cancelLeave";
	document.applyLeaveForm.submit();
}

function cancelWithApproval(id)
{
	document.getElementById("id").value=id;
	document.getElementById("method").value="initCancelLeave";
	document.applyLeaveForm.submit();
}

</script>
<html:form action="/empApplyLeave">	
	<html:hidden property="method" styleId="method" value="addEmpLeaves" />
	<html:hidden property="formName" value="applyLeaveForm" />
	<html:hidden property="id" styleId="id"/>
	 <c:choose>
		 <c:when test="${applyLeaveForm.isOndutyLeave}">
			 <html:hidden property="pageType" value="2" />
		</c:when>
		<c:otherwise>
			<html:hidden property="pageType" value="1" />
		</c:otherwise>		 
	 </c:choose>
<table width="100%" border="0">
 <c:choose>
		 <c:when test="${applyLeaveForm.isOndutyLeave}">
			 <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.emp.ondutyLeave.display" /> &gt;&gt;</span></span></td>
		</tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.emp.ondutyLeave.display"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		</c:when>
		<c:otherwise>
			<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.emp.applyLeave.display" /> &gt;&gt;</span></span></td>
		</tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.emp.applyLeave.display"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		</c:otherwise>		 
	 </c:choose>
  
      <tr>
		<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
		<td valign="top" class="news">
		<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
		<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
		<FONT color="green"> <html:messages id="msg"
			property="messages" message="true">
			<c:out value="${msg}" escapeXml="false"></c:out>
			<br>
		</html:messages> </FONT></div>
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
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2"><tr><td width="34%" class="row-even"><table width="100%" cellspacing="1" cellpadding="2">
            <c:if test="${applyLeaveForm.isOndutyLeave}">
            <tr>
                <td width="26%"  class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="employee.info.reportto.empnm"/>:</div></td>
                <td class="row-even" colspan="3">
                <html:select name="applyLeaveForm" property="employeeId" styleId="employeeId" styleClass="combo">
					<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
					<html:optionsCollection name="applyLeaveForm" property="employeeMap" label="value" value="key"/>
				</html:select> 
                
                </td><td>&nbsp;</td>
              </tr>
              </c:if>
              <tr>
                <td width="26%"  class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.activityattendence.leavetype"/>:</div></td>
                <td class="row-even" colspan="3">
                <html:select name="applyLeaveForm" property="leaveId" styleId="leaveId" styleClass="combo">
					<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
					<html:optionsCollection name="applyLeaveForm" property="leaveList" label="name" value="id" />
				</html:select> 
                <!-- "allotedLeaveList" -->
                
                </td><td>&nbsp;</td>
              </tr>
              <tr>
                <td width="26%"  class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.leavemodify.fromdate"/>:</div></td>
                <td width="23%" height="25" class="row-even" ><table width="189" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="60">
                      <html:text name="applyLeaveForm" property="fromDate" styleId="fromDate" size="10" maxlength="16"/> 
                      </td>
                      <td width="2"><script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'applyLeaveForm',
							// input name
							'controlname': 'fromDate'
						});</script>
					</td>
					<td width="126">
					<html:radio name="applyLeaveForm" property="fromAM" styleId="fromAM" value="AM"><font size="-3">AM</font></html:radio>
					<html:radio name="applyLeaveForm" property="fromAM" styleId="fromAM1" value="PM"><font size="-3">PM</font></html:radio>
					</td>
                    </tr>
                  </table>
                  </td>
                  </tr>
                <tr>
                <td width="20%" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.leavemodify.todate"/>:</div></td>
                <td class="row-even"><table width="188" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="60"><html:text name="applyLeaveForm" property="toDate" styleId="toDate" size="10" maxlength="16"/> </td>
                    <td width="2"><script language="JavaScript">
					new tcal ({
						// form name
						'formname': 'applyLeaveForm',
						// input name
						'controlname': 'toDate'
					});</script>
					</td>
					<td width="126">
					<html:radio name="applyLeaveForm" property="toAM" styleId="toAM" value="AM"><font size="-3">AM</font></html:radio>
					<html:radio name="applyLeaveForm" property="toAM" styleId="toAM1" value="PM"><font size="-3">PM</font></html:radio>
					</td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td width="26%"  class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.feepays.Reason"/>:</div></td>
                <td class="row-even" colspan="3"><span class="row-white">
                <html:textarea property="reason" name="applyLeaveForm" cols="28" rows="4" onkeypress="return imposeMaxLength(event,this)"></html:textarea>
                </span></td><td>&nbsp;</td>
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
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
			<td width="46%" height="35">
			<div align="right"><html:submit styleClass="formbutton">
				<bean:message key="knowledgepro.emp.applyLeave.display" />
			</html:submit></div>
			</td>
			<td width="2%"></td>
			<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" onclick="resetMessages()" styleId="reset">
								<bean:message key="knowledgepro.admin.reset" />
								</html:button> 
							</td>
		</tr>
        </table>
        </td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
		<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
			<td valign="top" class="news">
				<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
					<tr>
						<td><img src="images/01.gif" width="5" height="5"></td>
						<td width="914" background="images/02.gif"></td>
						<td><img src="images/03.gif" width="5" height="5"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">

								<tr>
									<td width="4%" height="25" class="row-odd">
										<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td width="15%" class="row-odd">
										<div align="center"><bean:message
												key="knowledgepro.hostel.adminmessage.leaveType" /></div>
									</td>
									<td width="10%" class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.emp.exceptionDetails.fromDate" /></div>
									</td>
									<td width="10%" class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.emp.exceptionDetails.toDate" /></div>
									</td>
									<td width="10%" class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.admission.status" /></div>
									</td>
									<td width="10%" class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.emp.applyLeave.noOfDays" /></div>
									</td>
									<td width="10%" class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.emp.applyLeave.cancelLeave" /></div>
									</td>
								</tr>
								<logic:notEmpty name="applyLeaveForm" property="applyLeaveList">
									<logic:iterate id="applyList" name="applyLeaveForm" property="applyLeaveList" indexId="count">
									<tr>
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td height="25">
										<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td align="center"><bean:write name="applyList"
										property="leaveType" /></td>
									<td align="center"><bean:write name="applyList"
										property="fromDate" /></td>
									<td align="center"><bean:write name="applyList"
										property="toDate" /></td>
									<td align="center"><bean:write name="applyList"
										property="status" /></td>
									<td align="center"><bean:write name="applyList"
										property="noOfDays" /></td>
									<td align="center">
										<logic:equal value="Applied" property="status" name="applyList">
											<img src="images/delete_icon.gif" width="16" height="16" onclick="cancelDirect('<bean:write name="applyList" property="id" />')" />
										</logic:equal>
										<logic:equal value="Approved" property="status" name="applyList">
											<img src="images/delete_icon.gif" width="16" height="16" onclick="cancelWithApproval('<bean:write name="applyList" property="id" />')" />
										</logic:equal>		
									</td>				
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</table>
						</td>
						<td width="5" height="30" background="images/right.gif"></td>
					</tr>
					<tr>
						<td height="5"><img src="images/04.gif" width="5"
							height="5"></td>
						<td background="images/05.gif"></td>
						<td><img src="images/06.gif"></td>
					</tr>
				</table>
				</td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
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