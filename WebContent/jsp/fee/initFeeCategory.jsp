<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function editFeeCategory(feeCategoryId, feeCategoryName) {
	document.getElementById("method").value = "updateFeeCategory";
	document.getElementById("feeCategoryId").value = feeCategoryId;
	document.getElementById("origFeeCategoryId").value = feeCategoryId;
	document.getElementById("feeCategoryName").value = feeCategoryName;
	document.getElementById("origFeeCategoryName").value = feeCategoryName;	
	document.getElementById("submitbutton").value="Update";
}
function deleteFeeCategory(feeCategoryId, feeCategoryName) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "feeCategory.do?method=deleteFeeCategory&feeCategoryId="
				+ feeCategoryId+"&feeCategoryName="+feeCategoryName;
	}
}
function reActivate(){
	document.location.href = "feeCategory.do?method=reActivateFeeCategory";
}
function resetValues(){	
	document.getElementById("feeCategoryName").value = null;	
	resetErrMsgs();	
}
</script>
<html:form action="/feeCategory">
	<c:choose>
		<c:when test="${operation != null && operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateFeeCategory" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addFeeCategory" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="id" styleId="id" />
	<html:hidden property="formName" value="feeCategoryForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="feeCategoryId" styleId="feeCategoryId" />
	<html:hidden property="origFeeCategoryId" styleId="origFeeCategoryId" />
	<html:hidden property="origFeeCategoryName" styleId="origFeeCategoryName" />
	
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><a href="main.html"
				class="Bredcrumbs"><bean:message key="knowledgepro.fee" /></a> <span
				class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.admin.feeCategory" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.feeCategory" /></strong></td>
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
												key="knowledgepro.admin.feeCategory" />:</div>
											</td>
											<td width="30%" height="25" class="row-even"><span
												class="star"><html:text property="feeCategoryName"
												styleId="feeCategoryName" styleClass="TextBox" size="16"
												maxlength="20" /></span></td>
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
										<c:when test="${operation != null && operation == 'edit'}">
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
								
								<logic:notEmpty property="feeCategoryList" name="feeCategoryForm">
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td height="25" colspan="6">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.slno" /></div>
													</td>
													<td height="25" class="row-odd" align="center"><bean:message
														key="knowledgepro.admin.feeCategory" /></td>													
													<td class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.edit" /></div>
													</td>
													<td class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.delete" /></div>
													</td>
												</tr>
										<logic:iterate id="cList" property="feeCategoryList" name="feeCategoryForm"
											 indexId="count">
											<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center"><bean:write name="cList"
												property="feeCategoryName" /></td>
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editFeeCategory('<bean:write name="cList" property="feeCategoryId" />','<bean:write name="cList" property="feeCategoryName" />')" /></div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteFeeCategory('<bean:write name="cList" property="feeCategoryId" />','<bean:write name="cList" property="feeCategoryName" />')" /></div>
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
								</logic:notEmpty>
								
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
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/Tcenter.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>