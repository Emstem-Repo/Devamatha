<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	function getPrograms(ProgramTypeId) {
		getProgramsByType("programMap", ProgramTypeId, "program",
				updatePrograms);
		resetOption("program");
	}

	function updatePrograms(req) {
		updateOptionsFromMap(req, "program", "- Select -");
	}
	function deleteEntrance(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "EntranceDetails.do?method=deleteEntranceDetails&id="
					+ id;
		}
	}

	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "EntranceDetails.do?method=activateEntranceDetails&id="
				+ id;
	}
	function editEntrance(id) 
	{
		document.location.href = "EntranceDetails.do?method=editEntranceDetails&id="
			+ id;

	}	

	function resetValues()
	{
		document.getElementById("programType").selectedIndex = 0;
		document.getElementById("program").selectedIndex = 0;
		document.getElementById("name").value = "";
		resetOption("program");
		resetErrMsgs();
	}	
</script>
<html:form action="/EntranceDetails" method="post">
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateEntranceDeatils" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addEntranceDeatils" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="entranceDetailsForm" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" /><span
				class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.entrance.details.entry"/>
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>

					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admin.entrance.details.entry"/></strong></td>
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
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
							<td width="21%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.program.type" /></div>
							</td>
							<td width="24%" height="25" class="row-even"><html:select
								property="programTypeId" styleClass="comboLarge"
								styleId="programType" onchange="getPrograms(this.value)">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="programTypeList"
									label="programTypeName" value="programTypeId" />
							</html:select> <span class="star"></span></td>
							<td width="25%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.prog" /></div>
							</td>
							<td width="30%" class="row-even"><span class="star">
							<html:select name="entranceDetailsForm" property="programId"
								styleId="program" styleClass="comboLarge">
								<html:option value="">- Select -</html:option>
								<c:choose>
									<c:when test="${operation == 'edit'}">
										<c:if
											test="${entranceDetailsForm.programTypeId != null && entranceDetailsForm.programTypeId != ''}">
											<html:optionsCollection name="programMap" label="value"
												value="key" />
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if
											test="${entranceDetailsForm.programTypeId != null && entranceDetailsForm.programTypeId != ''}">
											<c:set var="programMap"
												value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</c:otherwise>
								</c:choose>
							</html:select> </span></td>
						</tr>

						<tr>
							<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.admin.entrance.details.name"/></div>
							</td>
						<td  height="25" class="row-even"><span
							class="star"> <html:text property="name"
							styleClass="TextBox" styleId="name" size="65"
							maxlength="75" /> </span></td>
							<td height="25" class="row-odd">&nbsp;
							</td>
							<td height="25" class="row-even">&nbsp;
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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
									<c:when
										test="${operation != null && operation == 'edit'}">
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
									<td width="53%"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
										</c:when>
										<c:otherwise>
											<html:button property=""
											styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button>
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
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.programtype" /></td>

									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.admin.program" /></div>
									</td>
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.admin.entrance.name"/></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>

								</tr>
								<logic:iterate id="entrance" name="enDetailsList"
									indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td width="4%" height="25" align="center">
									<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td width="17%" height="25" align="center"><bean:write
										name="entrance"
										property="programTO.programTypeTo.programTypeName" /></td>
									<td width="18%" height="25" align="center"><bean:write
										name="entrance" property="programTO.name" /></td>
									<td width="18%" height="25" align="center"><bean:write
										name="entrance" property="name" /></td>
									<td width="8%" height="25" align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor:pointer" 
										onclick="editEntrance('<bean:write name="entrance" property="id"/>')"></div>
									</td>
									<td width="8%" height="25" align="center">
									<div align="center"><img src="images/delete_icon.gif" style="cursor:pointer" 
										width="16" height="16"
										onclick="deleteEntrance('<bean:write name="entrance" property="id"/>')">
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
					<div align="center"></div>
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

