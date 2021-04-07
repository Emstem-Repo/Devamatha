<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="css/styles.css">
<script type="text/javascript">
	function submitEvent(){
		document.getElementById("method").value = "submitEvent";
		document.empLeaveTypeForm.submit();
	}
	function resetEvent(){
		document.getElementById("method").value = "initEmpLeaveType";
		document.empLeaveTypeForm.submit();
	}
	function cancelEvent(){
		document.getElementById("method").value = "initEmpLeaveType";
		document.empLeaveTypeForm.submit();
	}
	function editDetails(id){
		document.location.href = "empLeaveType.do?method=editEmpLeaveType&id="+id;
	}
	function deleteDetails(id){
		document.location.href = "empLeaveType.do?method=deleteEvent&id="+id;
	}
	function reActivate() {
		document.location.href="empLeaveType.do?method=resetEventType";
	}
	
</script>
<html:form action="/empLeaveType" method="post">
	<html:hidden property="formName" value="empLeaveTypeForm" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admin.sec.EmployeeCategory" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.attendance.activityattendence.leavetype" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				
				<tr>
			        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
			        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.attendance.activityattendence.leavetype"/></td>
			        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
			   </tr>
				
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
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
								<td class="row-odd" width="20%">
								<div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendance.activityattendence.leavetype"/>:</div>
								</td>
								<td class="row-even" width="20%" align="left">
									<html:text property="leaveType"></html:text>
								</td>
								<td class="row-odd" width="20%">
								<div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.admin.code"/>:</div>
								</td>
								<td class="row-even" width="20%" align="left">
									<html:text property="code" size="20"></html:text>
								</td>
								</tr>
								<%-- <tr>
								<td class="row-odd" width="20%">
								<div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.employee.is.leave"/>:</div>
								</td>
								<td class="row-even" width="20%" align="left">
									<nested:radio property="isLeave" value="yes">Yes</nested:radio>
									<nested:radio property="isLeave" value="no">No</nested:radio>
								</td>
								<td class="row-odd" width="20%">
								<div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.employee.is.exemption"/>:</div>
								</td>
								<td class="row-even" width="20%" align="left">
									<nested:radio property="isException" value="yes">Yes</nested:radio>
									<nested:radio property="isException" value="no">No</nested:radio>
								</td>
								</tr>
								<tr>
								<td width="20%"  class="row-odd"><div align="right" ><span class="Mandatory">*</span>Continuous Days:</div>
								<div align="right" >(Including Week-Ends And Holidays)</div></td>
								<td width="20%" class="row-even">
					           <html:radio property="continuousdays" name="empLeaveTypeForm" value="true" styleId="fixed">Yes</html:radio>
					           <html:radio property="continuousdays"  name="empLeaveTypeForm" value="false" styleId="fixed">No</html:radio>
					            </td>
					            <td width="20%"  class="row-odd"><div align="right" ><span class="Mandatory">*</span>Can Apply Online:</div></td>
								<td width="20%" class="row-even">
					           <html:radio property="canapplyonline" name="empLeaveTypeForm" value="true" styleId="fixed">Yes</html:radio>
					           <html:radio property="canapplyonline"  name="empLeaveTypeForm" value="false" styleId="fixed">No</html:radio>
					            </td>
								</tr> --%>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr height="20">
					</tr>
						<tr>
						
						<td width="48%" height="35">
							<div align="right"><c:choose>
								<c:when test="${eventEdit == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="submitEvent()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="submitEvent()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td align="left">
								<html:button property="" styleId="Reset" styleClass="formbutton" value="Reset" onclick="resetEvent()"></html:button>
								<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelEvent()"></html:button>
							</td>
						</tr>
						
						
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				
				
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" height="25" class="row-odd"><bean:message
										key="knowledgepro.attendance.activityattendence.leavetype" /></td>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.admin.code" /></div>
									</td>
									<%-- <td align="center" height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.employee.is.leave" /></div>
									</td>
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.employee.is.exemption" /></div>
									</td>
									<td align="center" height="25" class="row-odd">Continuous Days</td>
									<td align="center" height="25" class="row-odd">Can Apply Online</td> --%>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:notEmpty name="empLeaveTypeForm" property="empLeaveTypeTo">
									<logic:iterate id="empLeaveTo" name="empLeaveTypeForm"
										property="empLeaveTypeTo" indexId="count">
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td width="25%" height="25">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td align="center" width="25%" height="25"><bean:write
											name="empLeaveTo" property="leaveType" /></td>
										 <td align="center" width="14%" height="25">
										<div align="center"><bean:write name="empLeaveTo"
											property="code" /></div></td>
										<%--<td align="center" width="14%" height="25">
										<div align="center"><bean:write name="empLeaveTo"
											property="isLeave" /></div>
										</td>
										<td align="center" width="14%" height="25">
										<div align="center"><bean:write name="empLeaveTo"
											property="isExemption" /></div>
										</td>
										<td align="center" width="14%" height="25">
										<div align="center"><bean:write name="empLeaveTo"
											property="continuousdays" /></div>
										</td>
										<td align="center" width="14%" height="25">
										<div align="center"><bean:write name="empLeaveTo"
											property="canapplyonline" /></div>
										</td> --%>
										<td width="20%" height="25">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor: pointer"
											onclick="editDetails('<bean:write name="empLeaveTo" property="id" />')" /></div>
										</td>
										<td width="20%" height="25" align="center">
										<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
										onclick="deleteDetails('<bean:write name="empLeaveTo" property="id"/>')">
									</div>
									</td>
										
										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
							
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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

