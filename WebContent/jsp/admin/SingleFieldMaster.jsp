<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript"><!--
	function editSingleFieldMaster(id, name) {
		document.getElementById("method").value = "updateSingleFieldMaster";
		document.getElementById("id").value = id;
		document.getElementById("name").value = name;
		document.getElementById("name").focus();
		document.getElementById("originalValue").value = name;
		document.getElementById("submitbutton").value = "Update";
		resetErrMsgs();
	}
	function editSingleFieldMasterOrderType(id, name,ordertype) {
		document.getElementById("submitbutton").value = "Update";
		document.location.href = "SingleFieldMasterEntry.do?method=editSingleFieldMasterEntry&id="
			+ id+"&name=" + name + "&order=" + ordertype;
		
		//document.getElementById("method").value = "updateSingleFieldMaster";
		//document.getElementById("id").value = id;
		//document.getElementById("name").value = name;
		//document.getElementById("order").value = ordertype;
		//document.getElementById("name").focus();
		//document.getElementById("originalValue").value = name;
		//document.getElementById("submitbutton").value = "Update";
		//resetErrMsgs();
	}
	function deleteSingleFieldMaster(id, name) {
		var operation = document.getElementById("operation").value;
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm == true) {
			document.location.href = "SingleFieldMasterEntry.do?method=deleteSingleFieldMaster&id="
					+ id + "&name=" + name + "&operation=" + operation;
		}
	}
	function reActivate() {
		var id = document.getElementById("reactivateid").value;
		var operation = document.getElementById("operation").value;
		document.location.href = "SingleFieldMasterEntry.do?method=activateSingleFieldMaster&reactivateid="
				+ id + "&operation=" + operation;
	}
	function resetValues() {
		document.getElementById("name").value = "";
		if (document.getElementById("method").value == "updateSingleFieldMaster") {
			document.getElementById("name").value = document
					.getElementById("originalValue").value;
		}
	}
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
</script>

<html:form action="/SingleFieldMasterEntry" focus="name">
	<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${admOperation != null && admOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateSingleFieldMaster" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addSingleFieldMaster" />
		</c:otherwise>
	</c:choose>

	<html:hidden property="id" styleId="id" />
	<html:hidden property="reactivateid" styleId="reactivateid" />
	<html:hidden property="originalValue" styleId="originalValue" />
	<html:hidden property="formName" value="singleFieldMasterForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="operation" styleId="operation" />
	<html:hidden property="displayName" styleId="displayName" />
	<html:hidden property="module" styleId="module" />

	<%String Operation=request.getParameter("operation"); 
	String key="knowledgepro.admin."+Operation;
	String key1="knowledgepro.admin.sec."+Operation;
	%>
	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:write
				name="singleFieldMasterForm" property="module" /> <span
				class="Bredcrumbs">&gt;&gt; <bean:write
				name="singleFieldMasterForm" property="displayName" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:write
						name="singleFieldMasterForm" property="displayName" /> Entry</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
									<td width="46%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:write
										name="singleFieldMasterForm" property="displayName" />:</div>
									</td>
									<bean:define id="oper" name="singleFieldMasterForm"
										property="operation" type="java.lang.String"></bean:define>
									<%if(oper.equalsIgnoreCase("EmpAgeofRetirement")) {%>
									<td width="54%" height="25" class="row-even"><span
										class="star"> <html:text property="name"
										styleClass="TextBox" styleId="name" size="15" maxlength="2"
										onkeypress="return isNumberKey(event)"
										onblur="checkNumber(this)" /> </span></td>
									<%} else if (oper.equalsIgnoreCase("PcBankAccNumber")){%>
									<td width="54%" height="25" class="row-even"><span
										class="star"> <html:text property="name"
										styleClass="TextBox" styleId="name" size="28" maxlength="10" />
									</span></td>
									<%}else if (oper.equalsIgnoreCase("SubjectType")){%>
									<td width="54%" height="25" class="row-even"><span
									class="star"> <html:text property="name"
									styleClass="TextBox" styleId="name" size="28" maxlength="10" />
								</span></td>
								<%}else if (oper.equalsIgnoreCase("ExamSubjectTypeMaster")){%>
								<td width="25%" height="25" class="row-even"><span
								class="star"> <html:text property="name"
								styleClass="TextBox" styleId="name" size="28" maxlength="20" />
							</span></td>
							<%}else if(oper.equalsIgnoreCase("University")){ %>
									<td width="54%" height="25" class="row-even"><span
										class="star"> <html:text property="name"
										styleClass="TextBox" styleId="name" size="28" maxlength="70" />
									</span></td>
									<%}else if(oper.contains("_Exam_")){ %>
									<td width="54%" height="25" class="row-even"><span
										class="star"><html:text property="name"
										styleClass="TextBox" styleId="name" size="28" maxlength="50" />
									</span></td>
									<%}else if(oper.contains("ApplicationStatus")){ %>
									<td width="54%" height="25" class="row-even"><span
										class="star"><html:text property="name"
										styleClass="TextBox" styleId="name" size="50" maxlength="250" />
									</span></td>
									<%}else if(oper.contains("ApplicantFeedback")){ %>
									<td width="54%" height="25" class="row-even"><span
										class="star"><html:text property="name"
										styleClass="TextBox" styleId="name" size="50" maxlength="100" />
									</span></td>
									<%}else if(oper.contains("Discipline")){ %>
									<td width="54%" height="25" class="row-even"><span
									class="star"><html:text property="name"
									styleClass="TextBox" styleId="name" size="50" maxlength="100" />
									</span></td> 
									<%}else if(oper.contains("PhdResearchPublication")){ %>
									<td width="54%" height="25" class="row-even"><span
									class="star"><html:text property="name"
									styleClass="TextBox" styleId="name" size="50" maxlength="100" />
									</span></td> 
									<%}
									else if(oper.contains("Location")){ %>
									<td width="54%" height="25" class="row-even"><span
									class="star"><html:text property="name"
									styleClass="TextBox" styleId="name" size="50" maxlength="100" />
									</span></td> 
									<%}
									else if(oper.contains("ClassGroup")){ %>
									<td width="54%" height="25" class="row-even"><span
									class="star"><html:text property="name"
									styleClass="TextBox" styleId="name"  maxlength="10" />
									</span></td> 
									<%} 
									else if(oper.contains("ExamInviligatorExemption")){ %>
									<td width="54%" height="25" class="row-even"><span
									class="star"><html:text property="name"
									styleClass="TextBox" styleId="name"  maxlength="50" />
									</span></td> 
									<%} 
									else if(oper.contains("Services")){ %>
									<td width="54%" height="25" class="row-even"><span
									class="star"><html:text property="name"
									styleClass="TextBox" styleId="name"  size="25"  maxlength="50" />
									</span></td> 
									<%} 
									else {%>
									<td width="50%" height="25" class="row-even"><span
										class="star"> <html:text property="name"
										styleClass="TextBox" styleId="name" size="20"  maxlength="50" />
									</span></td>
									<% }%>
									<%if(oper.equalsIgnoreCase("ExamSubjectTypeMaster")) {%>
									<td width="25%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Order:</div>
							</td>
							<td width="25%" height="25" class="row-even">
							<html:text property="order"  styleClass="TextBox"/><span class="star"></span></td>

									<%}%>
									
									
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
						<tr><td style="height: 10px" align="center" colspan="4"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><c:choose>
								<c:when test="${admOperation != null && admOperation == 'edit'}">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
									</html:submit>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="5%"><html:button property=""
								styleClass="formbutton" value="Reset"
								onclick="resetErrMsgs(),resetValues()"></html:button></td>
							<logic:notEmpty name="singleFieldMasterForm" property="mainPageExists">
							<td><html:button property="" styleClass="formbutton"> Go To Main Page"
 							onclick="goToMainPage('<bean:write name="singleFieldMasterForm" property="mainPageExists" scope="session"/>')"</html:button>
							</td></logic:notEmpty>
							<logic:empty name="singleFieldMasterForm" property="mainPageExists"><td></td></logic:empty>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="4"></td></tr>
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
									<td height="25" class="row-odd" align="center"><bean:write
										name="singleFieldMasterForm" property="displayName" /></td>
										<%if(oper.equalsIgnoreCase("ExamSubjectTypeMaster")) {%>
									<td height="25" class="row-odd">
									<div align="center">Order</div>
									</td>

									<%}%>
										
									<c:if
										test="${singleFieldMasterForm.operation != 'EmpAgeofRetirement'}">
										<td class="row-odd" align="center">
										<div align="center"><bean:message
											key="knowledgepro.edit" /></div>
										</td>
									</c:if>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:iterate name="singlefieldmasterlist"
									id="singlefieldmaster"
									type="com.kp.cms.to.admin.SingleFieldMasterTO" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="9%" height="25" class="row-even">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="60%" height="25" class="row-even" align="center"><bean:write
													name="singlefieldmaster" property="name" /></td>
													<%if(oper.equalsIgnoreCase("ExamSubjectTypeMaster")) {%>
									<td width="72%" height="25" class="row-even" align="center"><bean:write
													name="singlefieldmaster" property="orderType" /></td>
													

									<%}%>
													
													<c:if
													test="${singleFieldMasterForm.operation != 'EmpAgeofRetirement' && singleFieldMasterForm.operation == 'ExamSubjectTypeMaster'}">
													<td width="10%" height="25" class="row-even" align="center">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editSingleFieldMasterOrderType('<bean:write name="singlefieldmaster" property="id"/>','<bean:write name="singlefieldmaster" property="name"/>','<bean:write name="singlefieldmaster" property="orderType"/>')">
													</div>
													</td>
												</c:if>

												<c:if
													test="${singleFieldMasterForm.operation != 'EmpAgeofRetirement' && singleFieldMasterForm.operation != 'ExamSubjectTypeMaster'}">
													<td width="10%" height="25" class="row-even" align="center">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editSingleFieldMaster('<bean:write name="singlefieldmaster" property="id"/>','<bean:write name="singlefieldmaster" property="name"/>')">
													</div>
													</td>
												</c:if>
												<td width="9%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor: pointer"
													onclick="deleteSingleFieldMaster('<bean:write name="singlefieldmaster" property="id"/>','<bean:write name="singlefieldmaster" property="name"/>')">
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

												<td height="25" class="row-white" align="center"><bean:write
													name="singlefieldmaster" property="name" /></td>
													<%if(oper.equalsIgnoreCase("ExamSubjectTypeMaster")) {%>
									<td width="72%" height="25" class="row-white" align="center"><bean:write
													name="singlefieldmaster" property="orderType" /></td>
													

									<%}%>
									<c:if
													test="${singleFieldMasterForm.operation != 'EmpAgeofRetirement' && singleFieldMasterForm.operation == 'ExamSubjectTypeMaster'}">
													<td width="10%" height="25" class="row-even" align="center">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editSingleFieldMasterOrderType('<bean:write name="singlefieldmaster" property="id"/>','<bean:write name="singlefieldmaster" property="name"/>','<bean:write name="singlefieldmaster" property="orderType"/>')">
													</div>
													</td>
												</c:if>
												<c:if
													test="${singleFieldMasterForm.operation != 'EmpAgeofRetirement' && singleFieldMasterForm.operation != 'ExamSubjectTypeMaster'}">
													<td height="25" class="row-white" align="center">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editSingleFieldMaster('<bean:write name="singlefieldmaster" property="id"/>','<bean:write name="singlefieldmaster" property="name"/>')">
													</div>
													</td>
												</c:if>
												<td height="25" class="row-white" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													style="cursor: pointer"
													onclick="deleteSingleFieldMaster('<bean:write name="singlefieldmaster" property="id"/>','<bean:write name="singlefieldmaster" property="name"/>')"></div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
								</logic:iterate>
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
