<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript">
	function winOpen(employeeId) 
	{
		var url = "EmployeeApproveLeave.do?method=showAvailableLeaves&employeeId=" + employeeId;
		myRef = window.open(url, "viewAvailableLeaves","left=20,top=20,width=800,height=400,toolbar=1,resizable=0,scrollbars=1");
	}
</script>
<html:form action="/EmployeeApproveLeave">
	<html:hidden property="method" styleId="method" value="updateCancelStatus" />
	<html:hidden property="formName" value="employeeApproveLeaveForm" />
	<html:hidden property="pageType" value="2" />
	<table width="100%" border="0">
	  <tr>
	
	    <td><span class="heading"><bean:message key="knowledgepro.employee.approve.cancel.leave.request"/></span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.attendance.cancelLeave"/> </strong></td>
	
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
			<tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out><br>
						</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
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
	            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	
	              <tr >
	                <td class="row-odd" ><bean:message key="employee.info.reportto.empnm"/></td>
	                <td class="row-odd" ><bean:message key="knowledgepro.employee.approve.leave.applied.on"/></td>
	                <td class="row-odd" ><bean:message key="knowledgepro.attendance.leavemodify.fromdate"/></td>
	                <td class="row-odd" ><bean:message key="knowledgepro.attendance.leavemodify.todate"/></td>
	                <td class="row-odd" ><bean:message key="knowledgepro.attendance.activityattendence.leavetype"/></td>
		            <td class="row-odd" ><bean:message key="knowledgepro.feepays.Reason"/></td>
		            <td class="row-odd" ><bean:message key="knowledgepro.emp.applyLeave.noOfDays"/></td>
	                <td class="row-odd" ><bean:message key="knowledgepro.admission.status"/></td>
	                <td class="row-odd" >&nbsp;</td>
	                <td height="25" class="row-odd" ><bean:message key="knowledgepro.employee.approve.leave.remarks"/></td>
	                </tr>
				<nested:notEmpty name="employeeApproveLeaveForm" property="leaveApprovalList">
					<nested:iterate name="employeeApproveLeaveForm" property="leaveApprovalList" id="leaveApprovalList" type="com.kp.cms.to.employee.EmployeeApproveLeaveTO" indexId="count">
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>
			                <td width="18%" > <nested:write property="employeeName"/>  </td>
			                <td width="19%" ><nested:write property="appliedOnDate"/> </td>
			                <td width="19%"><nested:write property="fromDate"/>  </td>
			                <td width="19%"> <nested:write property="toDate"/> </td>
			                <td width="15%"> <nested:write property="leaveType"/> </td>
			                <td width="18%">  <nested:write property="cancelReason"/> </td>
			                <td width="18%">  <nested:write property="noOfDays"/> </td>
			                <td width="15%"> 
			                <nested:select property="status" styleClass="comboLarge">
			                    <html:option value="Cancellation Pending"></html:option>
			                    <html:option value="Canceled">Cancellation Approved</html:option>
			                    <html:option value="Cancellation On Hold"></html:option>
			                    <html:option value="Cancellation Rejected"></html:option>
							</nested:select>
			                </td>
			                <td width="18%" class="row-even" >
							<a href="javascript:winOpen('<bean:write name = "leaveApprovalList" property="employeeId" />');">
							Available Leave </a>
							</td>
							<td width="18%"><nested:text property="cancelRemarks" maxlength="100"></nested:text></td>
			                </tr>
					</nested:iterate>
				</nested:notEmpty>
	            </table></td>
	            <td width="5" height="30"  background="images/right.gif"></td>
	          </tr>
	
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" /></td>
	          </tr>
	        </table></td>
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="49%" height="35" align="right"><html:submit property="" styleClass="formbutton" value="Save" /></td>
	            <td width="2%" height="35" align="center">&nbsp;</td>
	            <td width="49%" height="35" align="left"><html:cancel styleClass="formbutton" value="Reset" /></td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	
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
	    </table>
		</td>
		</tr>
	</table>
</html:form>
