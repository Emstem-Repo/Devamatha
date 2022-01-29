<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function editRegion(regionId, RegionName) {
		document.getElementById("method").value = "editRegionCategory";
		document.getElementById("regionId").value = regionId;
		document.getElementById("name").value = RegionName;
		document.getElementById("origName").value = RegionName;
		document.getElementById("submitbutton").value = "Update";
	}
	function deleteRegion(regionId, RegionName) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "RegionCategory.do?method=deleteRegion&regionId="
					+ regionId + "&name=" + RegionName;
		}
	}
	/*
	function addregionCategory() {
		document.getElementById("method").value = "addRegionCategory";
		document.regionCategoryForm.submit();
	}
	*/
	function reActivate() {
		var id = document.getElementById("regionId").value;
		document.location.href = "RegionCategory.do?method=activateRegion&=regionId"
				+ id;
	}
	
</script>

<html:form action="/RegionCategory" focus="name">
	<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${regionOperation != null && regionOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="editRegionCategory" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addRegionCategory" />
		</c:otherwise>
	</c:choose>


	<html:hidden property="regionId" styleId="regionId" />
	<html:hidden property="formName" value="regionCategoryForm" />
	<html:hidden property="origName" styleId="origName" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>

			<td><span class="heading"><html:link href="AdminHome.do"
				styleClass="Bredcrumbs">
				<bean:message key="knowledgepro.admin" />
			</html:link><span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.admin.regioncategoryentry" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.regioncategoryentry" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT></td>
						</tr>
						<tr>

							<td width="45%" height="25" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.admin.region.category" /></div>
							</td>
							<td width="55%" class="row-even"><span class="star">
							<html:text property="name" styleClass="TextBox" styleId="name"
								size="26" maxlength="50" /> </span></td>
						</tr>
						<tr>
							<td height="25" colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>

									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when
											test="${regionOperation != null && regionOperation == 'edit'}">
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
									<td width="1%"></td>
									<td width="54%"><html:button property=""
										styleClass="formbutton" value="Reset"
										onclick="resetFieldAndErrMsgs()"></html:button></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>

							<td height="25" colspan="2">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd"><bean:message
										key="knowledgepro.admin.regioncategory" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:iterate name="regionCategoryList" id="region"
									type="com.kp.cms.to.admin.RegionTO" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="9%" height="25" class="row-even">
												<div align="center"><c:out value="${count + 1}" /></div>
												</td>
												<td width="73%" height="25" class="row-even"><bean:write
													name="region" property="name" /></td>
												<td width="10%" height="25" class="row-even">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editRegion('<bean:write name="region" property="id"/>','<bean:write name="region" property="name"/>')">
												</div>
												</td>
												<td width="8%" height="25" class="row-even">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteRegion('<bean:write name="region" property="id"/>','<bean:write name="region" property="name"/>')">
												</div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-white">
												<div align="center"><c:out value="${count + 1}" /></div>
												</td>

												<td height="25" class="row-white"><bean:write
													name="region" property="name" /></td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editRegion('<bean:write name="region" property="id"/>','<bean:write name="region" property="name"/>')">
												</div>
												</td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteRegion('<bean:write name="region" property="id"/>','<bean:write name="region" property="name"/>')">
												</div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
								</logic:iterate>
								<tr>
									<td height="25" colspan="4">&nbsp;</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>

				</tr>
				<tr>
					<td height="14" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
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
