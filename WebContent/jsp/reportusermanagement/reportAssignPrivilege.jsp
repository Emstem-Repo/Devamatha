<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function addPrivilege() {
		document.getElementById("method").value = "addPrivilege";
		document.reportAssignPrivilegeForm.submit();
	}
	function deletePrivilege(roleId) {
		deleteConfirm = confirm("Are you sure want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "ReportAssignPrivilege.do?method=deletePrivilege&roleId="
					+ roleId;
		}
	}
	function reActivate() {
		var roleId = document.getElementById("rId").value;
		document.location.href = "ReportAssignPrivilege.do?method=reActivatePrivilege&roleId="
				+ roleId;
	}

	function viewAllonRole(roleId) {

		var url = "ReportAssignPrivilege.do?method=getModuleMenuOnRole&roleId="
				+ roleId;
		myRef = window
				.open(url, "viewDescription",
						"left=20,top=20,width=600,height=400,toolbar=1,resizable=0,scrollbars=1");
	}
	function displayOnId(roleId){
		document.location.href = "ReportAssignPrivilege.do?method=editPrivilege&roleId="
			+ roleId;
	}

	function updatePrivilege(){
		document.getElementById("method").value = "updatePrivilege";
		document.reportAssignPrivilegeForm.submit();
	}
	
	function selectMenus(obj, id, count) {

		if (obj.checked) {
			for ( var i = 0; i < count; i++) {
				document.getElementById(id + "_" + i).checked = true;
			}

		} else {
			for ( var j = 0; j < count; j++) {
				document.getElementById(id + "_" + j).checked = false;
			}
		}

	}

	function selectModule(thisobj, id, totalCount) {
		var selectedcount = 0;
		for ( var i = 0; i < totalCount; i++) {
			if (document.getElementById(id + "_" + i).checked) {
				selectedcount++;
			}
		}
		
		if (selectedcount == totalCount) {
			document.getElementById(id).checked = true;
		} else {
			document.getElementById(id).checked = false;
		}

	}
</script>
<html:form action="/ReportAssignPrivilege" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="reportAssignPrivilegeForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
		<c:when test="${privilegeoperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="editPrivilege" />
		</c:when>
		</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.usermanagement.userinfo.usermanagement" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.usermanagement.assignprivilege" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="954" background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.usermanagement.assignprivilege" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="50%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" height="30" border="0" cellpadding="0"
								cellspacing="1">
								<tr class="row-white">
									<td width="50%" height="25" align="right" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.usermanagement.role.colon" /></div>
									</td>
									<td width="50%" class="row-even">
									<% boolean disable=false;%>
									<logic:equal value="true" name="reportAssignPrivilegeForm" property="flag">
									<% disable=true;%>
									</logic:equal>
									<html:select
										name="reportAssignPrivilegeForm" property="roleId"
										styleClass="combo" styleId="rId" disabled='<%=disable%>'>
										<html:option value="">- <bean:message
												key="knowledgepro.select" /> -</html:option>
										<logic:notEmpty name="reportAssignPrivilegeForm" property="roleList">		
										<html:optionsCollection name="reportAssignPrivilegeForm"
											property="roleList" label="name" value="id" />
											</logic:notEmpty>
									</html:select></td>
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
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"><bean:message
						key="knowledgepro.usermanagement.areasforprivilege" /></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
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
							<table width="100%" height="79" border="0" cellpadding="0"
								cellspacing="1">
								<logic:notEmpty name="reportAssignPrivilegeForm" property="moduleList">
									<nested:iterate name="reportAssignPrivilegeForm" id="privilege"
										property="moduleList" indexId="moduleCount">
										<tr class="row-white">
											<c:set var="moduleId">
												<nested:write name="privilege" property="id" />
											</c:set>
											<c:set var="totalMenu">
												<nested:write name="privilege" property="menuCount" />
											</c:set>
											<td width="3%" height="25" align="right" class="row-odd">
											
											<input
												type="hidden"
												name="moduleList[<c:out value='${moduleCount}'/>].chooseTemp"
												id="hidden_<c:out value='${moduleId}'/>"
												value="<nested:write name='privilege' property='chooseTemp'/>" />
											
											<input
												type="checkbox"
												name="moduleList[<c:out value='${moduleCount}'/>].choosed"
												id="<c:out value='${moduleId}'/>"
												onclick="selectMenus(this,'<c:out value="${moduleId}"/>','<c:out value="${totalMenu}"/>')" />
												
												<script type="text/javascript">
													var moduleId = document.getElementById("hidden_<c:out value='${moduleId}'/>").value;
													if(moduleId == "true") {
															document.getElementById("<c:out value='${moduleId}'/>").checked = true;
													}		
												</script>
												</td>
											<td colspan="5" class="heading"><nested:write
												name="privilege" property="name" /></td>
										</tr>
										<tr class="row-white">
											<nested:iterate name="privilege" id="menu" property="menusTO"
												indexId="menuCount">
												<td width="3%" height="25" align="right" class="row-odd">
												
												<input type="hidden"
													name="moduleList[<c:out value='${moduleCount}'/>].menusTO[<c:out value='${menuCount}'/>].chooseTemp"
													id="menuhidden_<c:out value='${moduleId}'/>_<c:out value='${menuCount}'/>"
													value="<nested:write name='menu' property='chooseTemp'/>" />
												<input type="checkbox"
													name="moduleList[<c:out value='${moduleCount}'/>].menusTO[<c:out value='${menuCount}'/>].choosed"
													id="<c:out value='${moduleId}'/>_<c:out value='${menuCount}'/>"
													onclick="selectModule(this,'<c:out value="${moduleId}"/>','<c:out value="${totalMenu}"/>')" />
												
												<script type="text/javascript">
													var menuId = document.getElementById("menuhidden_<c:out value='${moduleId}'/>_<c:out value='${menuCount}'/>").value;
													if(menuId == "true") {
															document.getElementById("<c:out value='${moduleId}'/>_<c:out value='${menuCount}'/>").checked = true;
													}		
												</script>
												</td>
												<td width="30%" class="row-even"><nested:write
													name="menu" property="name" /></td>
												<c:if test="${(menuCount + 1) % 3 == 0}">
										</tr>
										<tr class="row-white">
											</c:if>
									</nested:iterate>
									</tr>
									<tr class="row-white">
										<td colspan="6" class="heading">&nbsp;</td>
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
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="48%" height="35">
							<div align="right">
							
							<c:choose>
								<c:when test="${privilegeoperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updatePrivilege()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addPrivilege()"></html:button>
								</c:otherwise>
							</c:choose>
							</td>
							<td width="3%"></td>
							<td width="49%">
							<c:choose>
								<c:when test="${privilegeoperation == 'edit'}">
									<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
								</c:when>

								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetFieldAndErrMsgs()"></html:button>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td>
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
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.sec.Roles" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.view" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="reportAssignPrivilegeForm"
									property="privilegeList">
									<nested:iterate id="prev" name="reportAssignPrivilegeForm"
										property="privilegeList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td width="6%" height="25" class="row-even">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td width="76%" class="row-even" align="center"><nested:write
														name="prev" property="roleName" /></td>
													<td width="6%" class="row-even">
													<div align="center"><img src="images/View_icon.gif"
														alt="View" width="24" height="21" border="0"
														style="cursor: pointer"
														onclick="viewAllonRole('<nested:write name="prev" property="roleId"/>')"></div>
													</td>
													<td height="25" class="row-even">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="displayOnId('<nested:write name="prev" property="roleId"/>')"></div>
													</td>
													<td width="6%" class="row-even">
													<div align="center">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deletePrivilege('<nested:write name="prev" property="roleId"/>')"></div>
													</div>
													</td>
												</tr>
												<c:set var="temp" value="1" />
											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-white">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td class="row-white" align="center"><nested:write name="prev"
														property="roleName" /></td>
													<td class="row-white">
													<div align="center"><img src="images/View_icon.gif"
														alt="View" width="24" height="21" border="0"
														style="cursor: hand"
														onclick="viewAllonRole('<nested:write name="prev" property="roleId"/>')"></div>
													</td>
													<td height="25" class="row-white">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="displayOnId('<nested:write name="prev" property="roleId"/>')"></div>
													</td>
													<td class="row-white">
													<div align="center">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deletePrivilege('<nested:write name="prev" property="roleId"/>')"></div>
													</div>
													</td>
												</tr>
												<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
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
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td>&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>