<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript"><!--
	function addEmpType() {
		document.getElementById("method").value = "addEmpType";
		document.empTypeForm.submit();
	}

	function editEmpType(id) {
		document.location.href = "empType.do?method=editEmpType&id="+id;
	}

	function deleteEmpType(id){
		document.location.href = "empType.do?method=deleteEmpType&id="+id;
	}

	function resetFields() {
		document.getElementById("method").value = "initEmpType";
		document.empTypeForm.submit();
	}

	function clearField(field){
		if(field.value == "00")
			field.value = "";
	}
	function checkForEmpty(field){
		if(field.value.length == 0)
			field.value="00";
	}
	function reActivate() {
		document.location.href="empType.do?method=resetEmpType";
	}
</script>
<html:form action="/empType" method="post">
	<html:hidden property="formName" value="empTypeForm" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admin.sec.EmployeeCategory" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.employee.employeeType" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.employee.employeeType" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;
					<div align="right" style="color: red"><span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news">
					</td>
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
								<td colspan="4"><bean:message key="knowledgepro.empType.time"/></td>
							</tr>
								<tr>
									<td class="row-odd" width="150"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.employee.employeeType"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="name" ></html:text>
									</td>
									<td class="row-odd" width="150"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledge.empType.leave.initial.month"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<nested:radio property="leaveInitializeMonth" value="January" name="empTypeForm"><bean:message key="knowledgepro.admin.month.january"/></nested:radio>
										<nested:radio property="leaveInitializeMonth" value="June" name="empTypeForm"><bean:message key="knowledgepro.admin.month.june"/></nested:radio>
									</td>
								</tr>
								
								<tr>
									<td class="row-odd" width="150"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.hostel.timein"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="timeIn"  styleClass="Timings"  styleId="timeIn" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
										<html:text property="timeInMin"  styleClass="Timings"  styleId="timeInMin" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
									<td class="row-odd" width="150"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.hostel.timeinends"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="timeInEnds" styleClass="Timings"  styleId="timeInEnds" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
										<html:text property="timeInEndMIn" styleClass="Timings"  styleId="timeInEndMIn" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
								</tr>
								
								<tr>
									<td class="row-odd" width="75"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.hostel.timeout"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="timeOut" styleClass="Timings"  styleId="timeOut" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
										<html:text property="timeOutMin" styleClass="Timings"  styleId="timeOutMin" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
									<td class="row-odd" width="75"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.hostel.saturday.timeout"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="saturdayTimeOut" styleClass="Timings"  styleId="saturdayTimeOut" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
										<html:text property="saturdayTimeOutMin" styleClass="Timings"  styleId="saturdayTimeOutMin" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
								</tr>
								
								<tr>
									<td class="row-odd" width="75"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.hostel.halfday.starttime"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="halfDayStartTime" styleClass="Timings"  styleId="halfDayStartTime" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
										<html:text property="halfDayStartTimeMin" styleClass="Timings"  styleId="halfDayStartTimeMin" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
									<td class="row-odd" width="75"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.hostel.halfday.endtime"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="halfDatyEndTime" styleClass="Timings"  styleId="halfDatyEndTime" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>:
										<html:text property="halfDayEndTimeMin" styleClass="Timings"  styleId="halfDayEndTimeMin" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this)" onkeypress="return isNumberKey(event)"/>
									</td>
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
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<html:hidden property="button" name="empTypeForm" styleId="bid"/>
						<tr>
							<td width="45%" height="35">
								<div align="right"><c:choose>
								<c:when test="${editType != null && editType == 'edit'}">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="button" onclick="addEmpType()">
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="button" onclick="addEmpType()">
									</html:submit>
								</c:otherwise>
							</c:choose></div>
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
									<div align="center"><bean:message
										key="employee.info.job.emptype" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:notEmpty name="empTypeForm" property="empTypeToList">
									<logic:iterate id="empTypeTo" name="empTypeForm" property="empTypeToList" indexId="count">
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
											name="empTypeTo" property="name" /></td>
										
										
										<td width="7%" height="25">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor: pointer"
											onclick="editEmpType('<bean:write name="empTypeTo" property="id" />')" /></div>
										</td>
										<td width="10%" height="25" align="center">
										<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
										onclick="deleteEmpType('<bean:write name="empTypeTo" property="id"/>')">
									</div>
									</td>
										
										</tr>
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
			</table>
			</td>
		</tr>
	</table>
</html:form>

