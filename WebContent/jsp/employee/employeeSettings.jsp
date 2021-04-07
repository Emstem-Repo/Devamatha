<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function editSettings(id){
	 document.location.href = "employeeSettings.do?method=editEmployeeSettings&id="+id;
}
/*function editCaste(casteId, casteName,isFeeExcemption) {
	document.getElementById("method").value = "updateCaste";
	document.getElementById("casteId").value = casteId;
	document.getElementById("origCasteId").value = casteId;
	document.getElementById("casteName").value = casteName;
	document.getElementById("origCasteName").value = casteName;
	if(isFeeExcemption == "yes") {
            document.getElementById("feeExemption_1").checked = true;
            document.getElementById("origisFeeExcemption").value="yes";
	}	
	if(isFeeExcemption == "no") {
        document.getElementById("feeExemption_2").checked = true;
        document.getElementById("origisFeeExcemption").value="no";
	}
	document.getElementById("submitbutton").value="Update";
}
function deleteCaste(casteId,casteName) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm) {
		document.location.href = "CasteEntry.do?method=deleteCaste&casteId="
				+ casteId+"&casteName="+casteName;
	}
}
function reActivate(){
	document.location.href = "CasteEntry.do?method=reActivateCaste";
}*/
function resetFormFields(){	
	document.getElementById("retirement").value = null;
	document.getElementById("smsAlert1").checked=false;
	document.getElementById("smsAlert2").checked=false;
	document.getElementById("absent_check1").checked=false;
	document.getElementById("absent_check2").checked=false;
	document.getElementById("empLeaveType").value = null;
	document.getElementById("appNo").value = null;
	document.getElementById("INpunch").value = null;
	document.getElementById("OUTpunch").value = null;
	if (document.getElementById("method").value == "updateEmpSettings") {
		var absence=document.getElementById("origAbsenceCheck").value;
		if(absence == "yes") {
            document.getElementById("absent_check1").checked = true;
		}	
		if(absence == "no") {
      	  document.getElementById("absent_check2").checked = true;
		}
		var smsAlert=document.getElementById("origSmsAlert").value;
		if(smsAlert == "on") {
            document.getElementById("smsAlert1").checked = true;
		}	
		if(smsAlert == "off") {
      	  document.getElementById("smsAlert2").checked = true;
		}
		document.getElementById("retirement").value = document.getElementById("origAgeOfRetire").value;
		document.getElementById("empLeaveType").value = document.getElementById("origEmpLeave").value;
		document.getElementById("appNo").value = document.getElementById("origAppNo").value;
		document.getElementById("INpunch").value = document.getElementById("origMachineINpunch").value;
		document.getElementById("OUTpunch").value = document.getElementById("origMachineOUTpunch").value;
	}	
}
</script>
<html:form action="/employeeSettings">	
	<html:hidden property="formName" value="employeeSettingsForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origAgeOfRetire"	styleId="origAgeOfRetire" name="employeeSettingsForm"/>
	<html:hidden property="origSmsAlert" styleId="origSmsAlert" name="employeeSettingsForm"/>
	<html:hidden property="origAbsenceCheck"	styleId="origAbsenceCheck" name="employeeSettingsForm"/>
	<html:hidden property="origEmpLeave"	styleId="origEmpLeave" name="employeeSettingsForm"/>
	<html:hidden property="origAppNo"	styleId="origAppNo" name="employeeSettingsForm"/>
	<html:hidden property="origMachineINpunch"	styleId="origMachineINpunch" name="employeeSettingsForm"/>
	<html:hidden property="origMachineOUTpunch"	styleId="origMachineOUTpunch" name="employeeSettingsForm"/>
	<c:choose>
		<c:when test="${Operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateEmpSettings" />
		</c:when>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.employee"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.employee.settings"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.employee.settings"/></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
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
							<td width="25%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.employee.settings.age.retirement" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
							<html:text
								property="ageOfRetirement" styleId="retirement" styleClass="TextBox"
								size="20" maxlength="30" /><span class="star"></span></td>
							<td width="25%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.settings.smsAlert"/>:</div>
							</td>
							<td width="25%" class="row-even">
							<input type="hidden" id="alert" name="sms" value="<bean:write name="employeeSettingsForm" property="smsAlert"/>"/>
							 <input type="radio" name="smsAlert" id="smsAlert1" value="on"/> <bean:message key="knowledgepro.employee.settings.on"/>
                   			 <input type="radio" name="smsAlert" id="smsAlert2" value="off"/> <bean:message key="knowledgepro.employee.settings.off"/>
							<script type="text/javascript">
								var smsAlert = document.getElementById("alert").value;
								if(smsAlert == "on") {
				                        document.getElementById("smsAlert1").checked = true;
								}	
								else if(smsAlert == "off") {
			                        document.getElementById("smsAlert2").checked = true;
							}
							</script>
							</td>	
							</tr>
							<tr>
							<td width="25%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.settings.absenceChecking"/>:</div>
							</td>
							<td width="25%" class="row-even">
							<input type="hidden" id="absence" name="absence" value="<bean:write name="employeeSettingsForm" property="absenceChecking"/>" />
							 <input type="radio" name="absenceChecking" id="absent_check1" value="yes"/> <bean:message key="knowledgepro.yes"/>
                   			 <input type="radio" name="absenceChecking" id="absent_check2" value="no"/> <bean:message key="knowledgepro.no"/>
							<script type="text/javascript">
								var absentcheck = document.getElementById("absence").value;
								if(absentcheck == "yes") {
				                        document.getElementById("absent_check1").checked = true;
								}	
								else if(absentcheck == "no") {
			                        document.getElementById("absent_check2").checked = true;
							}
							</script>
							</td>
							
                            <td width="25%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.employee.settings.empLeveType"/>:</div>
							</td>
							<td class="row-even">
                						<html:select name="employeeSettingsForm" property="accumulateLeaveType" styleId="empLeaveType" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="employeeSettingsForm" property="empLeave">
										<html:optionsCollection name="employeeSettingsForm"  property="empLeave" label="value" value="key"/>
										</logic:notEmpty>
										</html:select> 
                					 </td>	
							</tr>
							<tr>
							<td width="25%" height="25" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.employee.settings.currentApplicationNo" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
							<html:text
								property="currentApplicationNo" styleId="appNo" styleClass="TextBox"
								size="20" maxlength="30" /><span class="star"></span></td>
								<td width="25%" height="25" class="row-odd">
								<div align="right"><bean:message
								key="knowledgepro.employee.settings.machine.id.IN" />:</div>
								</td>
								<td width="25%" height="25" class="row-even">
								<html:text
								property="machineIdForOfflineINPunch" styleId="INpunch" styleClass="TextBox"
								size="20" maxlength="6" />
								</td>
							</tr>
							<tr>
							<td width="25%" height="25" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.employee.settings.machine.id.OUT" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
							<html:text
								property="machineIdForOfflineOUTPunch" styleId="OUTpunch" styleClass="TextBox"
								size="20" maxlength="6" /><span class="star"></span></td>
								<td width="25%" height="25" class="row-odd"></td>
								<td width="25%" height="25" class="row-even"></td>
							</tr>
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
						<tr>
							<td width="45%" height="35">
							<div align="right">
							
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<c:choose>
		                         <c:when test="${Operation == 'edit'}">
									<html:submit property="" styleClass="formbutton" value="Update" styleId="submitbutton"> </html:submit>
									<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFormFields()"></html:button>
							     </c:when>
							</c:choose>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				<tr>
							<td height="25" colspan="6">
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
											<td width="15%" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="23%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.employee.settings.age.retirement" /></div>
											</td>
											<td width="23%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.employee.settings.smsAlert" /></div>
											</td>
											<td width="23%" class="row-odd">
											<div align="center">
											<bean:message key="knowledgepro.employee.settings.absenceChecking"/>
											</div>
											</td>
											<td width="15%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.edit" /></div>
											</td>
											
										</tr>
										<logic:notEmpty name="employeeSettingsForm" property="empSettListTO">
										<logic:iterate id="empList" name="employeeSettingsForm" property="empSettListTO"
											indexId="count">
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
											<td align="center"><bean:write name="empList"
												property="ageOfRetirement" /></td>
											<td align="center"><bean:write name="empList"
												property="smsAlert" /></td>
												<td align="center"><bean:write name="empList"
												property="absenceChecking" /></td>
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editSettings('<bean:write name="empList" property="id" />')" /></div>
											</td>
											</tr>
										</logic:iterate></logic:notEmpty>
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
