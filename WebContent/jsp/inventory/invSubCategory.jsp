<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function addSubCategory() {

	document.getElementById("method").value = "addSubCategory";
}

function editSubCategory(id) {
	document.location.href = "ItemSubCategory.do?method=editSubCategory&id="+ id;
}

function updateSubCategory() {
	document.getElementById("method").value = "updateSubCategory";
}

function deleteSubCategory(id) {
	deleteConfirm = confirm("Are you sure to delete this entry?");
	if (deleteConfirm)
		document.location.href = "ItemSubCategory.do?method=deleteSubCategory&id="+ id;
}

function reActivate() {
	document.location.href = "ItemSubCategory.do?method=reactivateSubCategory";
}
</script>
<html:form action="/ItemSubCategory" method="post">
<html:hidden property="formName" value="invSubCategoryForm" />
<html:hidden property="pageType" value="1"/>
<c:choose>
<c:when test="${invSubCategory == 'edit'}">
<html:hidden property="method" styleId="method" value="updateInvSubCategory"/>
</c:when>
<c:otherwise>
<html:hidden property="method" styleId="method" value="addSubCategory"/>					
</c:otherwise>
</c:choose>
		
<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.inventory" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.inventory.itemsubcategory" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.inventory.itemsubcategory" /></strong></td>

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
												key="knowledgepro.inventory.itemcategory" /></div>
											</td>
											<td width="26%" height="25" class="row-even"><span
												class="star"> <html:select property="invItemCategory"
												styleClass="comboLarge" styleId="invItemCategory">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection name="categoryList"
													label="categoryName" value="id" />
											</html:select></span></td>

											<td width="26%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.inventory.itemsubcategory" /></div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text property="subCategoryName"
												styleClass="TextBox" styleId="subCategoryName" size="20"
												maxlength="100" name="invSubCategoryForm" /> </span></td>
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
											test="${invSubCategory != null && invSubCategory == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												onclick="updateSubCategory()">
												<bean:message key="knowledgepro.update" />
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												onclick="addSubCategory()">
												<bean:message key="knowledgepro.submit" />
											</html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="5%"><c:choose>
										<c:when
											test="${invSubCategory != null && invSubCategory == 'edit'}">
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
									<logic:notEmpty name="invSubCategoryForm" property="mainPage">
							<td><html:button property="" styleClass="formbutton"> Go To Main Page"
 							onclick="goToMainPage('<bean:write name="invSubCategoryForm" property="mainPage" scope="session"/>')"</html:button>
							</td></logic:notEmpty>
							<logic:empty name="invSubCategoryForm" property="mainPage"><td></td></logic:empty>
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
													<div align="center"><bean:message key="knowledgepro.slno" /></div></td>
													<td height="25" class="row-odd" align="center">
													<div align="center"><bean:message key="knowledgepro.inventory.itemcategory" /></div></td>
													<td height="25" class="row-odd" align="center">
													<div align="center"><bean:message key="knowledgepro.inventory.itemsubcategory" /></div></td>
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
												<logic:notEmpty name="invSubCategoryForm" 	property="subCategoryList">
													<logic:iterate name="invSubCategoryForm" id="subCategory" property="subCategoryList" indexId="count">
														<c:choose>
															<c:when test="${temp == 0}">
																<tr>
																	<td width="5%" height="25" class="row-even"
																		align="center">
																	<div align="center"><c:out value="${count+1}" /></div>
																	</td>
																	<td width="15%" height="25" class="row-even"
																		align="center"><bean:write name="subCategory"
																		property="invItemCategory" /></td>
																	<td width="15%" height="25" class="row-even"
																		align="center"><bean:write name="subCategory"
																		property="subCategoryName" /></td>
																	<td width="8%" height="20" class="row-even"
																		align="center">
																	<div align="center"><img
																		src="images/edit_icon.gif" width="16" height="18"
																		style="cursor: pointer"
																		onclick="editSubCategory('<bean:write name="subCategory" property="id"/>')">
																	</div>
																	</td>
																	<td width="8%" height="20" class="row-even"
																		align="center">
																	<div align="center"><img
																		src="images/delete_icon.gif" width="16" height="16"
																		style="cursor: pointer"
																		onclick="deleteSubCategory('<bean:write name="subCategory" property="id"/>')">
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
																		name="subCategory" property="invItemCategory" /></td>
																	<td height="25" class="row-white"
																		align="center"><bean:write name="subCategory"
																		property="subCategoryName" /></td>
																	<td height="20" class="row-white" align="center">
																	<div align="center"><img
																		src="images/edit_icon.gif" width="16" height="18"
																		style="cursor: pointer"
																		onclick="editSubCategory('<bean:write name="subCategory" property="id"/>')">
																	</div>
																	</td>
																	<td height="20" class="row-white" align="center">
																	<div align="center"><img
																		src="images/delete_icon.gif" width="16" height="16"
																		style="cursor: pointer"
																		onclick="deleteSubCategory('<bean:write name="subCategory" property="id"/>')"></div>
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