<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">

function addMenu() {

	document.getElementById("method").value = "addMenuScreen";
}

function editMenu(id) {

	document.getElementById("MID").value=id;
	document.location.href = "reportMenuScreen.do?method=editMenus&menuId="+id;
}

function updateMenu(){
	document.getElementById("method").value = "updateMenus"; 
}

function deleteMenu(id) {
	
	deleteConfirm =confirm("Are you sure to delete this entry?");
	document.getElementById("MID").value=id;
	if(deleteConfirm)
	document.location.href = "reportMenuScreen.do?method=deleteMenus&menuId="+id;
}

function reActivate() {
	
	var oldMID = document.getElementById("module").value;	
	var seq = document.getElementById("seq").value;
	document.location.href="reportMenuScreen.do?method=reActivateMenus&sequence="+seq+"&oldModuleId="+oldMID;
}

function assignMenu(menuId,moduleId,menuName) {
	document.getElementById("MID").value=menuId;
	document.getElementById("moduleId").value=moduleId;
	document.getElementById("menuName").value=menuName;
	document.getElementById("method").value="reportsAssignPrivilegeRole";
	document.reportsScreenMasterForm.submit();
}
</script>
<html:form action="/reportMenuScreen" method="POST">

<c:choose>
	<c:when test="${menuOperation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateMenus"/>
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addMenuScreen"/>
	</c:otherwise>
</c:choose>
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="reportsScreenMasterForm" />
	<html:hidden property="menuId" styleId="MID"  />
	<html:hidden property="oldModuleId" styleId="oldMID"  />	
	<html:hidden property="menuName" styleId="menuName" />
	<html:hidden property="moduleId" styleId="moduleId"  />	

	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.usermanagement.userinfo.usermanagement" /><span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.usermanagement.reportmenuScreenMaster" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="954" background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.usermanagement.reportmenuScreenMaster" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"><br>
					<div align="right"><span class='MandatoryMark'><bean:message
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
							<table width="100%" height="27" border="0" cellpadding="0"
								cellspacing="1">
								<tr class="row-white">
									<td width="50%" height="25" align="right" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.usermanagement.moduleName" />:</div>
									</td>
									
									<td width="50%" class="row-even"><html:select
										property="module" styleId="module" styleClass="combo">
										<logic:notEmpty property="moduleToList" name="reportsScreenMasterForm">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<html:optionsCollection property="moduleToList" label="name"
											value="id" />
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
					<td valign="top" class="news">&nbsp;</td>
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
							<table width="100%" height="53" border="0" cellpadding="0"
								cellspacing="1">
								<tr class="row-white">
									<td width="20%" height="25" align="right" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.usermanagement.screenName" />:</div>
									</td>
									<td width="25%" class="row-even"><html:text
										property="screenName" styleId="SCName" maxlength="50"/></td>
									<td width="20%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.usermanagement.path" />:</div>
									</td>
									<td width="35%" class="row-even"><html:text
										property="path" styleId="path" size="30" maxlength="250"/></td>
								</tr>
								<tr class="row-white">
									<td height="25" align="right" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.usermanagement.sequence"/>:</div>
									</td>
									<td class="row-even"><html:text property="sequence"
										styleId="seq" maxlength="2"/></td>
									<td class="row-odd">&nbsp;</td>
									<td class="row-even">&nbsp;</td>
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
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="48%" height="35">
							<div align="right"><c:choose>
								<c:when test="${menuOperation == 'edit'}">
									<html:submit property="" styleClass="formbutton"
										onclick="updateMenu()">
										<bean:message key="knowledgepro.update" />
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton"
										onclick="addMenu()">
										<bean:message key="knowledgepro.submit" />
									</html:submit>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="3%"></td>
							<td width="49%"><c:choose>
								<c:when test="${menuOperation == 'edit'}">
									<html:reset property="" styleClass="formbutton">
										<bean:message key="knowledgepro.admin.reset" />
									</html:reset>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="6%" height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td width="11%" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.usermanagement.moduleName" /></div>
									</td>
									<td width="18%" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.usermanagement.screenName" /></div>
									</td>
									<td width="25%" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.usermanagement.path" /></div>
									</td>
									<td width="10%" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.usermanagement.sequence" /></div>
									</td>
									<td width="10%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.usermanagement.assignprivilege" /></div>
									</td>
									<td width="10%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td width="10%" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:notEmpty name="reportsScreenMasterForm" 	property="menusList">
								<tr>
								<logic:iterate id="menu" name="reportsScreenMasterForm" property="menusList" indexId="count">
								
								     <c:choose>
										<c:when test="${count%2 == 0}"><tr class="row-even"></c:when>
										<c:otherwise><tr class="row-white"></c:otherwise>
					 				 </c:choose>
					 				 
									<td height="25"><div align="center"><c:out value="${count + 1}" /></div></td>
									<td height="25" align="center"><bean:write name="menu" property="moduleTO.name" /></td>
									<td height="25" align="center"><bean:write name="menu" property="name" /></td>
									<td height="25" align="center" ><bean:write name="menu" property="url" /></td>
									<td height="25" align="center"><bean:write name="menu" property="position" /></td>
									<td align="center"><div align="center"><img src="images/AssignPrivilege.jpg" style="cursor:pointer" 
									 width="16" height="18" onclick="assignMenu('<bean:write name="menu" property="id"/>','<bean:write name="menu" property="moduleTO.id"/>','<bean:write name="menu" property="name"/>')"></div></td> 
									<td height="25"><div align="center"><img src="images/edit_icon.gif" style="cursor:pointer" 
									 width="16" height="18" onclick="editMenu('<bean:write name="menu" property="id"/>')"></div></td>
									<td height="25"><div align="center"><img src="images/delete_icon.gif" style="cursor:pointer" 
									width="16" height="16" onclick="deleteMenu('<bean:write name="menu" property="id"/>')"></div></td>
								 </logic:iterate>
								</tr>
								</logic:notEmpty>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
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