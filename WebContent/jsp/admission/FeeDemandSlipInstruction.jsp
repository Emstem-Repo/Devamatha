<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<script type="text/javascript">
	function addDetails() {
		document.getElementById("method").value = "addSlipInstruction";
		document.demandSlipInstructionForm.submit();
	}
	function deleteInstruction(id) {
		deleteConfirm = confirm("Are you sure want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "demandSlipInstruction.do?method=deleteSlipInstruction&id="+id;
		}
	}

	function editInstruction(id) {
		document.location.href = "demandSlipInstruction.do?method=editSlipInstruction&id="
				+ id;
	}
	function updateDetails(id) {
	document.getElementById("method").value = "updateSlipInstruction";
	document.demandSlipInstructionForm.submit();
	}
	function resetFields() {
		resetFieldAndErrMsgs();
	}
	function reActivate(id)
	{
		document.location.href = "demandSlipInstruction.do?method=reActivateSlipInstruction&id="+id;
	}			
</script>
</head>
<html:form action="/demandSlipInstruction" method="post">
	<html:hidden property="method" styleId="method"/>
	<html:hidden property="formName" value="demandSlipInstructionForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.demandSlip" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admission.demandSlip" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>

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
									<td width="21%" height="25" class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td height="25" class="row-even">
										<html:select property="courseId" styleClass="combo" styleId="courseId">
											<html:option value="">-<bean:message key="knowledgepro.select" />-</html:option>
											<html:optionsCollection name="demandSlipInstructionForm" property="courseList" label="name" value="id"/>
										</html:select>
									</td>
									<td height="25" class="row-odd">
									<div align="right"></span>
									<bean:message key="knowledgepro.admission.demandSlip.scheme" />:</div>
									</td>
									<td width="29%" height="25" class="row-even"><input type="hidden" id="schemeNoId" name="schemeNo1" value='<bean:write name="demandSlipInstructionForm" property="schemeNo"/>'/>
									<html:select property="schemeNo" styleClass="combo" styleId="schemeNo">
										<html:option value="">-<bean:message key="knowledgepro.select" />-</html:option>
										<cms:renderSchemeOrCourse></cms:renderSchemeOrCourse>
									</html:select>
									</td>
								</tr>
								<tr>
									<td width="21%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admission.instruction" />:</div>
									</td>
									<td height="25" class="row-even" colspan="3">
										<html:textarea property="instruction" styleClass="TextBox" rows="10" style="width:500px;height:100px"></html:textarea>
									</td>
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
							<c:choose>
							<c:when test="${operation == 'edit'}">							
							<html:button property=""
								styleClass="formbutton" value="Update"
								onclick="updateDetails()"></html:button>
								</c:when>
								<c:otherwise>
								<html:button property=""
								styleClass="formbutton" value="Submit"
								onclick="addDetails()"></html:button>
								</c:otherwise>
								</c:choose>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<c:choose>
							<c:when test="${operation == 'edit'}">		
							<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
								</c:when>
								<c:otherwise>
								<html:button property=""
								styleClass="formbutton" value="Reset"
								onclick="resetFields()"></html:button>
								</c:otherwise>
								</c:choose>
								</td>
								</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
									<td align="center" height="25" class="row-odd"><bean:message
										key="knowledgepro.admin.course" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admission.demandSlip.scheme" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admission.instruction" /></td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:notEmpty name="demandSlipInstructionForm"
									property="deTosList">
									<nested:iterate id="deList" name="demandSlipInstructionForm"
										property="deTosList" indexId="count">
												<tr>
													<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
													</c:choose>
													<td width="5%" height="25">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td align="center" width="20%" height="25"><nested:write
														name="deList" property="courseName" /></td>
													<td align="center" width="20%" height="25"><nested:write
														name="deList" property="schemeNo" /></td>	
													<td align="center" width="21%">
														<nested:write name="deList" property="instruction" />
													</td>
													<td width="7%" height="25">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="editInstruction('<bean:write name="deList" property="id"/>')"></div>
													</td>
													<td width="7%" height="25">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deleteInstruction('<bean:write name="deList" property="id"/>')"></div>
													</td>
												</tr>
									</nested:iterate>
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

<script type="text/javascript">

var schemeNo = document.getElementById("schemeNoId").value;
if(schemeNo != null && schemeNo.length != 0) {
	document.getElementById("schemeNo").value = schemeNo;
}
	

</script>