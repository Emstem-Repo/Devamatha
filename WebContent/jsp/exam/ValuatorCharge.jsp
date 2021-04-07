<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function addValuator() {

		document.getElementById("method").value = "AddValuatorCharges";
	}

	function editValuator(id) {
		document.location.href = "valuatorCharges.do?method=editValuatorCharges&id="
				+ id;
	}

	function updateValuator() {
		document.getElementById("method").value = "updatevaluatorCharges";
	}

	function deleteValuator(id) {
		deleteConfirm = confirm("Are you sure to delete this entry?");
		if (deleteConfirm)
			document.location.href = "valuatorCharges.do?method=deleteValuatorCharges&id="+ id;
	}

	function reActivate() {
		document.location.href = "valuatorCharges.do?method=reactivateValuatorCharges";
	}
</script>
<html:form action="/valuatorCharges" method="POST">
	<html:hidden property="formName" value="valuatorChargesForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
		<c:when test="${valuatorCharges == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updatevaluatorCharges" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="AddValuatorCharges" />
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.ValuatorCharges" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.ValuatorCharges" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2">
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
						</tr>


						<tr>
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
											<td width="20%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.program.Type" /></div>
											</td>
											<td width="26%" height="25" class="row-even"><span
												class="star"> <html:select property="programTypeId"
												styleClass="comboLarge" styleId="programTypeId"
												onchange="getPrograms(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection name="programTypeList"
													label="programTypeName" value="programTypeId" />
											</html:select></span></td>

											<td width="26%" height="25" class="row-odd">
											<div align="right">Valuator Amount Per Script</div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text property="valuatorcharge"
												styleClass="TextBox" styleId="valuatorcharge" size="20"
												maxlength="30" name="valuatorChargesForm" /> </span></td>
										</tr>
										<tr>
											<td width="26%" height="25" class="row-odd">
											<div align="right">Reviewer Amount Per Script</div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text property="reviewercharge"
												styleClass="TextBox" styleId="reviewercharge" size="20"
												maxlength="30" name="valuatorChargesForm" /> </span></td>
											<td width="26%" height="25" class="row-odd">
											<div align="right">Project Evaluation Minor Amount Per Script</div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text
												property="projectevaluationminor" styleClass="TextBox"
												styleId="projectevaluationminor" size="20" maxlength="30"
												name="valuatorChargesForm" /></span></td>
										</tr>
										<tr>
											<td width="26%" height="25" class="row-odd">
											<div align="right">Project Evaluation Major Amount Per Script</div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text
												property="projectevaluationmajor" styleClass="TextBox"
												styleId="projectevaluationmajor" size="20" maxlength="30"
												name="valuatorChargesForm" /> </span></td>
											<td height="25" class="row-odd" align="right"> Board Meeting Charges</td>
											<td height="25" class="row-even"> <html:text
												property="boardMeetingCharge" styleClass="TextBox"
												styleId="boardMeetingCharge" size="20" maxlength="30"
												name="valuatorChargesForm" /></td>
										</tr>
										<tr>
											<td>
												Minimum Scripts
											</td>
										</tr>
										<tr>
											<td width="26%" height="25" class="row-odd">
											<div align="right">Minimum Number of Scripts</div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text property="minimumScripts"
												styleId="minimumScripts" size="20" onkeypress="return isNumberKey(event)"
												maxlength="30" name="valuatorChargesForm" /> </span></td>
											
											<td width="26%" height="25" class="row-odd">
											<div align="right">Minimum Valuator Amount: </div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text property="minimumvaluatorcharge"
												styleClass="TextBox" styleId="minimumvaluatorcharge" size="20"
												maxlength="30" name="valuatorChargesForm" /> </span></td>
										</tr>
										<tr>
											<td width="26%" height="25" class="row-odd">
											<div align="right">Minimum Reviewer Amount: </div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text property="minimumreviewercharge"
												styleClass="TextBox" styleId="minimumreviewercharge" size="20"
												maxlength="30" name="valuatorChargesForm" /> </span></td>
											<td width="26%" height="25" class="row-odd">
											<div align="right">Minimum Project Evaluation Minor Amount: </div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text
												property="minimumprojectevaluationminor" styleClass="TextBox"
												styleId="minimumprojectevaluationminor" size="20" maxlength="30"
												name="valuatorChargesForm" /></span></td>
										</tr>
										<tr>
											<td width="26%" height="25" class="row-odd">
											<div align="right">Minimum Project Evaluation Major Amount: </div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text
												property="minimumprojectevaluationmajor" styleClass="TextBox"
												styleId="minimumprojectevaluationmajor" size="20" maxlength="30"
												name="valuatorChargesForm" /> </span></td>
											<td height="25" class="row-odd" align="right"></td>
											<td height="25" class="row-even"> </td>
										</tr>

									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>

								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

						<tr>
							<td height="25" colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when
											test="${valuatorCharges != null && valuatorCharges == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												onclick="updateValuator()">
												<bean:message key="knowledgepro.update" />
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												onclick="addValuator()">
												<bean:message key="knowledgepro.submit" />
											</html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><c:choose>
										<c:when
											test="${valuatorCharges != null && valuatorCharges == 'edit'}">
											<html:reset property="" styleClass="formbutton">
												<bean:message key="knowledgepro.admin.reset" />
											</html:reset>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetFieldAndErrMsgs()">
												<bean:message key="knowledgepro.cancel" />
											</html:button>
										</c:otherwise>
									</c:choose></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
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
											<td height="25" colspan="2">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td height="25" class="row-odd" align="center">
													<div align="center"><bean:message
														key="knowledgepro.slno" /></div>
													</td>
													<td height="25" class="row-odd" align="center">Program
													Type</td>
													<td height="25" class="row-odd" align="center">Valuator
													Amount Per script</td>
													<td height="25" class="row-odd" align="center">Reviewer
													Amount per script</td>
													<td height="25" class="row-odd" align="center">Project
													Evaluation Minor Amount per script</td>
													<td height="25" class="row-odd" align="center">Project
													Evaluation Major Amount per script</td>
													<td height="25" class="row-odd" align="center">Board
													Meeting Charge</td>
													<td height="20" class="row-odd" align="center">
													<div align="center"><bean:message
														key="knowledgepro.edit" /></div>
													</td>
													<td height="20" class="row-odd" align="center">
													<div align="center"><bean:message
														key="knowledgepro.delete" /></div>
													</td>
												</tr>

												<c:set var="temp" value="0" />
												<logic:notEmpty name="valuatorChargesForm"
													property="valuatorChargeList">
													<logic:iterate name="valuatorChargesForm"
														id="valuatorCharge" property="valuatorChargeList"
														indexId="count">
														<c:choose>
															<c:when test="${temp == 0}">
																<tr>
																	<td width="5%" height="25" class="row-even"
																		align="center">
																	<div align="center"><c:out value="${count+1}" /></div>
																	</td>
																	<td width="13%" height="25" class="row-even"
																		align="center"><bean:write name="valuatorCharge"
																		property="programTypeId" /></td>
																	<td width="13%" height="25" class="row-even"
																		align="center"><bean:write name="valuatorCharge"
																		property="valuatorcharge" /></td>
																	<td width="13%" height="25" class="row-even"
																		align="center"><bean:write name="valuatorCharge"
																		property="reviewercharge" /></td>
																	<td width="13%" height="25" class="row-even"
																		align="center"><bean:write name="valuatorCharge"
																		property="projectevaluationminor" /></td>
																	<td width="13%" height="25" class="row-even"
																		align="center"><bean:write name="valuatorCharge"
																		property="projectevaluationmajor" /></td>
																	<td width="10%" height="25" class="row-even"
																		align="center"><bean:write name="valuatorCharge"
																		property="boardMeetingCharge" /></td>
																	<td width="8%" height="20" class="row-even"
																		align="center">
																	<div align="center"><img
																		src="images/edit_icon.gif" width="12" height="18"
																		style="cursor: pointer"
																		onclick="editValuator('<bean:write name="valuatorCharge" property="id"/>')">
																	</div>
																	</td>
																	<td width="8%" height="20" class="row-even"
																		align="center">
																	<div align="center"><img
																		src="images/delete_icon.gif" width="16" height="16"
																		style="cursor: pointer"
																		onclick="deleteValuator('<bean:write name="valuatorCharge" property="id"/>')">
																	</div>

																	</td>
																</tr>
																<c:set var="temp" value="1" />
															</c:when>
															<c:otherwise>
																<tr>
																	<td height="25" class="row-white" align="center">
																	<div align="center"><c:out value="${count+1}" /></div>
																	</td>

																	<td height="20" class="row-white" align="center"><bean:write
																		name="valuatorCharge" property="programTypeId" /></td>
																	<td height="25" class="row-white"
																		align="center"><bean:write name="valuatorCharge"
																		property="valuatorcharge" /></td>
																	<td height="25" class="row-white"
																		align="center"><bean:write name="valuatorCharge"
																		property="reviewercharge" /></td>
																	<td height="25" class="row-white"
																		align="center"><bean:write name="valuatorCharge"
																		property="projectevaluationminor" /></td>
																	<td height="25" class="row-white"
																		align="center"><bean:write name="valuatorCharge"
																		property="projectevaluationmajor" /></td>
																	<td height="25" class="row-white"
																		align="center"><bean:write name="valuatorCharge"
																		property="boardMeetingCharge" /></td>
																	<td height="20" class="row-white" align="center">
																	<div align="center"><img
																		src="images/edit_icon.gif" width="16" height="18"
																		style="cursor: pointer"
																		onclick="editValuator('<bean:write name="valuatorCharge" property="id"/>')">
																	</div>
																	</td>
																	<td height="20" class="row-white" align="center">
																	<div align="center"><img
																		src="images/delete_icon.gif" width="16" height="16"
																		style="cursor: pointer"
																		onclick="deleteValuator('<bean:write name="valuatorCharge" property="id"/>')"></div>
																	</td>
																</tr>
																<c:set var="temp" value="0" />
															</c:otherwise>
														</c:choose>
													</logic:iterate>
												</logic:notEmpty>

											</table>
											</td>
										</tr>



									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>

								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
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