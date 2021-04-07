<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function clearField(field) {
		if (field.value == "00")
			field.value = "";
	}
	function checkForEmpty(field) {
		if (field.value.length == 0) {
			field.value = "00";
		}
	}

	function addWorkTimeEntry() {
		document.getElementById("methodId").value = "addWorkTimeEntry";
	}
	function editWorkTimeEntry(id) {

		document.location.href = "WorkTimeEntry.do?method=editWorkTimeEntry&id="
				+ id;
	}
	function deleteWorkTimeEntry(id) {
		deleteConfirm = confirm("Are you sure want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "WorkTimeEntry.do?method=deleteWorkTimeEntry&id="
					+ id;
		}
	}
	function updateWorkTimeEntry() {
		document.getElementById("methodId").value = "updateWorkTimeEntry";
	}
	function reActivate() {
		document.location.href = "WorkTimeEntry.do?method=reActivateWorkTimeEntry";
	}
</script>

<html:form action="/WorkTimeEntry">
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="methodId"
				value="updateWorkTimeEntry" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="methodId"
				value="addWorkTimeEntry" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="workTimeEntryForm" />
	<html:hidden property="id" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admin.sec.EmployeeCategory" />&gt;&gt; <bean:message
				key="knowledgepro.employee.work.time.entry" /> &gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.employee.work.time.entry" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
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
									<td width="34%" class="row-even">
									<table width="100%" cellspacing="1" cellpadding="2">
									<tr><td width="50%"><div align="left"> <span class='heading'><FONT color="red" size="1">
										<bean:message key="knowledgepro.message" /></FONT></span></div></td></tr>
										<tr>
											<td class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="employee.info.job.emptype" /></div>
											</td>

											<td class="row-even"><html:select
												name="workTimeEntryForm" property="name"
												styleClass="TextBox">
												<html:option value="">
													<bean:message key="knowledgepro.pettycash.Select" />
												</html:option>
												<logic:notEmpty name="workTimeEntryForm"
													property="listEmployeeType">
												<html:optionsCollection name="workTimeEntryForm"
													property="listEmployeeType" label="name" value="id" />
													</logic:notEmpty>
											</html:select></td>
										</tr>
										
										<tr>
											<td colspan="2">
											<table width="100%">
												<tr>
													<td class="row-odd">Day</td>
													<td class="row-odd"><bean:message
														key="knowledgepro.attn.period.from.time" /></td>
													<td class="row-odd"><bean:message
														key="knowledgepro.attn.period.to.time" /></td>
													<td class="row-odd"><bean:message
														key="knowledgepro.employee.work.time.out.time" /></td>
													<td class="row-odd"><bean:message
														key="knowledgepro.attn.period.to.time" /></td>
													<td class="row-odd">Is Holiday</td>
												</tr>
												<logic:notEmpty name="workTimeEntryForm" property="workList">
													<nested:iterate id="wto" name="workTimeEntryForm"
														property="workList" indexId="count">
														<tr>
															<td class="row-even"><bean:write name="wto"
																property="name" /></td>
															<td class="row-even"><nested:text
																property="inTimeFromHr" styleClass="Timings"
																styleId="inTimeFromHr" size="2" maxlength="2"
																onfocus="clearField(this)"
																onblur="checkForEmpty(this), checkNumber(this)"
																onkeypress="return isNumberKey(event)" /> : <nested:text
																property="inTimeFromMins" styleClass="Timings"
																styleId="inTimeFromMins" size="2" maxlength="2"
																onfocus="clearField(this)"
																onblur="checkForEmpty(this), checkNumber(this)"
																onkeypress="return isNumberKey(event)" /> &nbsp;&nbsp;</td>

															<td class="row-even"><nested:text
																property="inTimeToHrs" styleClass="Timings"
																styleId="inTimeToHrs" size="2" maxlength="2"
																onfocus="clearField(this)"
																onblur="checkForEmpty(this), checkNumber(this)"
																onkeypress="return isNumberKey(event)" /> : <nested:text
																property="inTimeToMins" styleClass="Timings"
																styleId="inTimeToMins" size="2" maxlength="2"
																onfocus="clearField(this)"
																onblur="checkForEmpty(this), checkNumber(this)"
																onkeypress="return isNumberKey(event)" /></td>
															<td class="row-even"><nested:text
																property="outTimeFromHrs" styleClass="Timings"
																styleId="outTimeFromHrs" size="2" maxlength="2"
																onfocus="clearField(this)"
																onblur="checkForEmpty(this), checkNumber(this)"
																onkeypress="return isNumberKey(event)" /> : <nested:text
																property="outTimeFromMins" styleClass="Timings"
																styleId="outTimeFromMins" size="2" maxlength="2"
																onfocus="clearField(this)"
																onblur="checkForEmpty(this), checkNumber(this)"
																onkeypress="return isNumberKey(event)" /> &nbsp;&nbsp;</td>
															<td class="row-even"><nested:text
																property="outTimeToHrs" styleClass="Timings"
																styleId="outTimeToHrs" size="2" maxlength="2"
																onfocus="clearField(this)"
																onblur="checkForEmpty(this), checkNumber(this)"
																onkeypress="return isNumberKey(event)" /> : <nested:text
																property="outTimeToMins" styleClass="Timings"
																styleId="outTimeToMins" size="2" maxlength="2"
																onfocus="clearField(this)"
																onblur="checkForEmpty(this), checkNumber(this)"
																onkeypress="return isNumberKey(event)" /></td>
															<td class="row-even">
															  <input
																	type="hidden"
																	name="workList[<c:out value='${count}'/>].tempChecked"
																	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='wto' property='tempChecked'/>" />
																	<input
																	type="checkbox"
																	name="workList[<c:out value='${count}'/>].checked"
																	id="<c:out value='${count}'/>" />
																	<script type="text/javascript">
																		var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																		if(studentId == "true") {
																			document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
															</td>
														</tr>
													</nested:iterate>
												</logic:notEmpty>
											</table>
											</td>
										</tr>
									</table>
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
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><c:choose>
								<c:when test="${operation == 'edit'}">
									<html:submit property="" styleClass="formbutton"
										>
										<bean:message key="knowledgepro.update" />
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton"
										 >
										<bean:message key="knowledgepro.submit" />
									</html:submit>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><c:choose>
								<c:when test="${operation == 'edit'}">
									<html:cancel styleClass="formbutton">
										<bean:message key="knowledgepro.admin.reset" />
									</html:cancel>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetFieldAndErrMsgs()">
										<bean:message key="knowledgepro.cancel" />
									</html:button>
								</c:otherwise>
							</c:choose></td>

						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.employee.employeeType" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>

									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="workTimeEntryForm" property="workList">
									<logic:iterate id="time" name="workTimeEntryForm"
										property="empWorkTimeToList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td width="6%" height="25" class="row-even">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td width="41%" class="row-even">
													<div align="center"><bean:write name="time"
														property="name" /></div>
													</td>
													<td width="6%" height="25" class="row-even">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editWorkTimeEntry('<bean:write name="time" property="id" />')" />
													</div>
													</td>
													<td width="6%" height="25" class="row-even">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deleteWorkTimeEntry('<bean:write name="time" property="id"/>')" />
													</div>
													</td>
												</tr>
												<c:set var="temp" value="1" />
											</c:when>
											<c:otherwise>
												<tr>
													<td width="6%" height="25" class="row-white">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td width="41%" class="row-white">
													<div align="center"><bean:write name="time"
														property="name" /></div>
													</td>
													<td width="6%" height="25" class="row-white">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editWorkTimeEntry('<bean:write name="time" property="id" />')" />
													</div>
													</td>
													<td width="6%" height="25" class="row-white">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deleteWorkTimeEntry('<bean:write name="time" property="id"/>')" />
													</div>
													</td>
												</tr>
												<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
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
