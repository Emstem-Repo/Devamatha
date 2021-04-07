<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">
	function editState(stateId, stateName, countryId,bankStateId) {
		document.getElementById("method").value = "updateState";
		document.getElementById("stateId").value = stateId;
		document.getElementById("stateName").value = stateName;
		document.getElementById("country").value = countryId;
		document.getElementById("submitbutton").value = "Update";
		document.getElementById("origStateName").value = stateName;
		document.getElementById("editedCountryId").value = countryId;
		document.getElementById("bankStateId").value = bankStateId;
		document.getElementById("origBankStateId").value = bankStateId;
		resetErrMsgs();
	}

	function deleteState(stateId, stateName) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "StateEntry.do?method=deleteState&stateId="
					+ stateId + "&stateName=" + stateName;
		}
	}
	function reActivate() {
		document.location.href = "StateEntry.do?method=activateState";
	}

	function resetValues() {
		document.getElementById("stateName").value = "";
		document.getElementById("country").value = "";
		document.getElementById("bankStateId").value = "";
		if (document.getElementById("method").value == "updateState") {
			document.getElementById("stateName").value = document.getElementById("origStateName").value;
			document.getElementById("country").value = document.getElementById("editedCountryId").value;
			document.getElementById("bankStateId").value = document.getElementById("origBankStateId").value;
		}
		resetErrMsgs();
	}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/StateEntry">
	<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${stateOperation != null && stateOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateState" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addState" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="origStateName" styleId="origStateName" />
	<html:hidden property="stateId" styleId="stateId"/>
	<html:hidden property="editedCountryId" styleId="editedCountryId" />
	<html:hidden property="formName" value="countryStateCity" />
	<html:hidden property="origBankStateId" styleId="origBankStateId" />
	<html:hidden property="pageType" value="2" />

	<table width="100%" border="0">
		<tr>
			<td class="Bredcrumbs">
				<bean:message key="knowledgepro.admin" />
			 <span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.admin.stateentry" /> &gt;&gt;</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.stateentry" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr> 
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
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
							<td width="25%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.country" /></div>
							</td>
							<td width="25%" height="25" class="row-even"><html:select
								property="countryId" styleClass="combo" styleId="country">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="countriesMap" label="value"
									value="key" />
							</html:select> <span class="star"></span></td>
							<td width="25%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.state" /></div>
							</td>
							<td width="25%" class="row-even"><span class="star">
							<html:text property="stateName" styleClass="TextBox"
								styleId="stateName" size="16" maxlength="50" /> </span></td>
						</tr>
						
                        <tr>
							<td width="25%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.state.bankstateid" /></div>
							</td>
							<td width="25%" height="25" class="row-even"><span class="star">
							<html:text property="bankStateId" styleClass="TextBox"
								styleId="bankStateId" size="16" maxlength="50" /> </span></td>
							<td width="25%" class="row-odd"></td>
							<td width="25%" class="row-even"></td>
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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when
											test="${stateOperation != null && stateOperation == 'edit'}">
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
									<td width="2%"></td>
									<td width="53%"><html:button property=""
										styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
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
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" />
									</div>
									</td>
									<td height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.countryname" /></td>
									<td class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.statename" /></td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:iterate name="statesList" id="state"
									type="com.kp.cms.to.admin.StateTO" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="8%" height="25" class="row-even" align="center">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="34%" height="25" class="row-even" align="center"><bean:write
													name="state" property="countryName"  /></td>
												<td width="37%" class="row-even" align="center"><bean:write
													name="state" property="name" /></td>
												<td width="11%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer"
													onclick="editState('<bean:write name="state" property="id"/>','<bean:write name="state" property="name"/>','<bean:write name="state" property="countryId"/>','<bean:write name="state" property="bankStateId"/>')"></div>
												</td>
												<td width="10%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer"
													onclick="deleteState('<bean:write name="state" property="id"/>','<bean:write name="state" property="name"/>')"></div>
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
													name="state" property="countryName" /></td>
												<td height="25" class="row-white" align="center"><bean:write
													name="state" property="name" /></td>
												<td height="25" class="row-white" align="center">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer"
													onclick="editState('<bean:write name="state" property="id"/>','<bean:write name="state" property="name"/>','<bean:write name="state" property="countryId"/>','<bean:write name="state" property="bankStateId"/>')"></div>
												</td>
												<td height="25" class="row-white" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer"
													onclick="deleteState('<bean:write name="state" property="id"/>','<bean:write name="state" property="name"/>')"></div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
								</logic:iterate>
								<tr>
									<td height="25" colspan="5">&nbsp;</td>
								</tr>
							</table>
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
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
              <td  valign="top" background="images/Tright_03_03.gif"></td>
              <td height="20" valign="top" ></td>
              <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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