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
function getBlocks(hostal){
	getBlocksByHostel(hostal, "block", updateBlocks);
}
function updateBlocks(req){
	updateOptionsFromMap(req, "block", "- Select -");
}
function getUnits(blockId){
	getUnitsByBlocks(blockId, "unit", updateUnits);
}
function updateUnits(req){
	updateOptionsFromMap(req, "unit", "- Select -");
}
function resetDetails(){
	 resetFieldAndErrMsgs();
}
function cancelAction(){
	document.location.href = "LoginAction.do?method=loginAction";
}
function editBiometric(id){
	document.location.href = "biometric.do?method=editBiometricDetails&id="+id;
}
function deleteBiometric(id){
	document.location.href = "biometric.do?method=deleteBiometricDetails&id="+id;
}
</SCRIPT>

<html:form action="/biometric">
	<html:hidden property="formName" value="bioMetricForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
		<c:when test="${Operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateBiometricDetails" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addBiometricDetails" />
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.biometric" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.hostel.biometric" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
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
										<tr >
	                    						<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.entry.hostel.name"/></div></td>
	                    						<td width="25%" height="25" class="row-even">
	                    						<% boolean disable=false;%>
												<logic:equal value="true" name="bioMetricForm" property="flag">
												<% disable=true;%>
												</logic:equal>
	                    							<html:select property="hostelId" styleClass="comboLarge" styleId="hostelId" disabled='<%=disable%>' onchange="getBlocks(this.value)">
														<html:option value="">
															<bean:message key="knowledgepro.admin.select" />
														</html:option>
														<logic:notEmpty name="bioMetricForm" property="hostelList">
															<html:optionsCollection name="bioMetricForm" property="hostelList" label="name" value="id" />
														</logic:notEmpty>
													</html:select>
												</td>
												<td width="25%" class="row-odd"><div align="right"><span class="Mandatory">*</span>Block</div></td>
	                    						<td width="25%" class="row-even">
								                    <html:select name="bioMetricForm" property="block" styleId="block" disabled='<%=disable%>' styleClass="combo" onchange="getUnits(this.value)">
														<html:option value="">- Select -</html:option>
														<logic:notEmpty name="bioMetricForm" property="blockMap">
															<html:optionsCollection name="bioMetricForm" property="blockMap" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
	                  						</tr>
										<tr>
	                  							<td width="25%" class="row-odd"><div align="right"><span class="Mandatory">*</span>Unit</div></td>
	                    						<td width="25%" class="row-even">
								                    <html:select name="bioMetricForm" property="unit" disabled='<%=disable%>' styleId="unit" styleClass="combo" >
														<html:option value="">- Select -</html:option>
														<logic:notEmpty name="bioMetricForm" property="unitMap">
															<html:optionsCollection name="bioMetricForm" property="unitMap" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
												<td width="25%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.biometric.id" /></div></td>
	                    						<td width="25%" class="row-even">
								                    <html:text property="machineId"></html:text>
												</td>
	                  						</tr>
	                  						<tr>
	                  							<td width="25%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.biometric.ip" /></div></td>
	                    						<td width="25%" class="row-even">
								                  <html:text property="machineIp"></html:text>  
												</td>
												<td width="25%" class="row-odd"><div align="right">Machine name</div></td>
	                    						<td width="25%" class="row-even">
								                    <html:text property="machineName"></html:text>
												</td>
	                  						</tr>
	                  						<tr>
											<td height="25" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="employee.info.job.workLocationName" /> :</div>
											</td>
											<td class="row-even" width="25%"><html:text name="bioMetricForm"
												property="worklocation" styleId="worklocation"
												styleClass="TextBox" size="16" maxlength="10" /></td>
												<td height="25" class="row-odd" width="25%">
												<div align="right"> </div>
												</td>
												<td height="25" class="row-even" width="25%">
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
						<tr>
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${Operation == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="submitbutton">
											</html:submit>
											
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton">
											</html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetDetails()"></html:button> </td>

									<td width="53%">
                                       <input name="Submit" type="reset" class="formbutton" value="Cancel" onclick="cancelAction()" />
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
											<td height="25" colspan="4">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td height="25" class="row-odd" width="5%">
													<div align="center"><bean:message
														key="knowledgepro.admin.subject.subject.s1no" /></div>
													</td>
													<td height="25" class="row-odd" width="20%">
													<div align="center"><bean:message key="knowledgepro.hostel" /></div>
													</td>
													<td height="25" class="row-odd" width="15%">
													<div align="center"><bean:message key="knowledgepro.hostel.blocks" /></div>
													</td>
													<td height="25" class="row-odd" width="15%">
													<div align="center"><bean:message key="knowledgepro.hostel.units" /></div>
													</td>
													<td height="25" class="row-odd" align="center" width="5%"><bean:message
														key="knowledgepro.employee.biometric.id" /></td>
													<td class="row-odd" align="center" width="12%"><bean:message
														key="knowledgepro.employee.biometric.ip" /></td>
													<td class="row-odd" align="center" width="8%">Machine Name</td>	
													<td class="row-odd" align="center" width="8%"><bean:message
														key="employee.info.job.workLocationName" /></td>	
													<td class="row-odd" width="10%">
													<div align="center"><bean:message
														key="knowledgepro.edit" /></div>
													</td>
													<td class="row-odd" width="10%">
													<div align="center"><bean:message
														key="knowledgepro.delete" /></div>
													</td>
												</tr>
												<logic:notEmpty name="bioMetricForm" property="biometricList">
												<logic:iterate name="bioMetricForm"	property="biometricList" id="biometric" indexId="count">
															<tr>
																<td height="10" class="row-even" width="5%">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td height="25" class="row-even" align="center" width="20%"><bean:write
																	name="biometric" property="hostelName" /></td>
																<td height="25" class="row-even" align="center" width="15%"><bean:write
																	name="biometric" property="blockName" /></td>
																<td height="25" class="row-even" align="center" width="15%"><bean:write
																	name="biometric" property="unitName" /></td>
																<td height="25" class="row-even" align="center" width="5%"><bean:write
																	name="biometric" property="machineId" /></td>
																<td class="row-even" align="center" width="12%"><bean:write
																	name="biometric" property="machineIp" /></td>
																<td class="row-even" align="center" width="8%"><bean:write
																	name="biometric" property="machinName" /></td>
																	<td class="row-even" align="center" width="12%"><bean:write
																	name="biometric" property="workLocation" /></td>
																<td height="25" class="row-even" width="10%">
																<div align="center"><img
																	src="images/edit_icon.gif" width="25%" height="16"
																	style="cursor: pointer"
																	onclick="editBiometric('<bean:write name="biometric" property="id" />')" /></div>
																</td>
																<td width="10%" height="25" class="row-even">
																<div align="center"><img
																	src="images/delete_icon.gif" width="25%" height="16"
																	style="cursor: pointer"
																	onclick="deleteBiometric('<bean:write name="biometric" property="id" />')" /></div>
																</td>
															</tr>
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
