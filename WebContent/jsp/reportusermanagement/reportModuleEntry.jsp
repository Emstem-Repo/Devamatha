<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">
	function editModules(modId, modName, pos) {
		document.getElementById("method").value = "editModules";
		document.getElementById("modId").value = modId;
		document.getElementById("modulename").value = modName;
		document.getElementById("position").value = pos;
		document.getElementById("origName").value = modName;
		document.getElementById("origPos").value = pos;
		document.getElementById("submitbutton").value = "Update";
		resetErrMsgs();
	}
	function deleteModule(modId, modName) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm == true) {
			document.location.href = "ReportModules.do?method=deleteModule&id="
					+ modId + "&name=" + modName;
		}
	}
	function resetValues() {
		resetFieldAndErrMsgs();
		if (document.getElementById("method").value == "editModules") {
			document.getElementById("modulename").value = document
					.getElementById("origName").value;
			document.getElementById("position").value = document
					.getElementById("origPos").value;
		}
	}
	
	function reActivate() {
		document.location.href = "ReportModules.do?method=ActivateModule";
		document.getElementById("submitbutton").value = "Submit";
	}
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ReportModules" method="post" focus="modulename">
	<c:choose>
		<c:when test="${moduleOperation != null && moduleOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="editModules" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addModule" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="id" styleId="modId" />
	<html:hidden property="origName" styleId="origName" />
	<html:hidden property="origPos" styleId="origPos" />
	<html:hidden property="formName" value="reportModuleForm" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.usermanagement.userinfo.usermanagement" /> <span
				class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.usermanagement.module.entry"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.usermanagement.module.entry"/></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
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
							<td width="13%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.usermanagement.module.name.col"/></div>
							</td>
							<td width="19%" height="25" class="row-even"><html:text
								property="name" styleClass="TextBox" styleId="modulename"
								size="25" maxlength="50" /> <span class="star"></span></td>
							<td width="22%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.usermanagement.position.col"/></div>
							</td>
							<td width="16%" class="row-even"><span class="star">
							<html:text
								property="position" styleClass="TextBox" styleId="position"
								size="16" maxlength="9" onkeypress="return isNumberKey(event)"
								onblur="checkNumber(this)" /> </span></td>
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
											test="${moduleOperation != null && moduleOperation == 'edit'}">
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
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>

									<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.usermanagement.module.name"/></td>
									<td class="row-odd" align="center"><bean:message key="knowledgepro.usermanagement.module.position"/></td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:iterate name="moduleList" id="moduleList"
									type="com.kp.cms.to.usermanagement.ModuleTO" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td width="8%" height="25"  align="center">
									<div align="center"><c:out value="${count+1}" /></div>
									</td>

									<td width="25%" height="25" align="center"><bean:write
										name="moduleList" property="name" /></td>
									<td width="25%" align="center"><bean:write
										name="moduleList" property="position" /></td>
									<td width="11%" height="25"  align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor: pointer"
										onclick="editModules('<bean:write name="moduleList" property="id"/>','<bean:write name="moduleList" property="name"/>','<bean:write name="moduleList" property="position"/>')">
									</div>
									</td>
									<td width="10%" height="25"  align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
										onclick="deleteModule('<bean:write name="moduleList" property="id"/>','<bean:write name="moduleList" property="name"/>')">
									</div>
									</td>
									</tr>
								</logic:iterate>
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
					<td height="71" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>

					<td width="0" background="images/Tcenter.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>