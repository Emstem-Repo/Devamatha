<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">
	function editSubReligion(subReligionId, subRelName, ReligionId) {
		document.getElementById("method").value = "updateSubReligion";
		document.getElementById("subReligionId").value = subReligionId;
		document.getElementById("subReligionName").value = subRelName;
		document.getElementById("religion").value = ReligionId;
		document.getElementById("origSubRelName").value = subRelName;
		document.getElementById("origRelId").value = ReligionId;
		document.getElementById("submitbutton").value = "Update";
	}

	function deleteSubReligion(subReligionId, subRelName) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm == true) {
			document.location.href = "SubReligion.do?method=deleteSubReligion&subReligionId="
					+ subReligionId + "&subReligionName=" + subRelName;
		}
	}
	function addSubReligion() {
		document.getElementById("method").value = "addSubReligion";
		document.subReligionForm.submit();
	}
	function reActivate() {
		document.location.href = "SubReligion.do?method=activateSubReligion";
	}
	function resetValues() {
		document.getElementById("subReligionName").value = "";
		document.getElementById("religion").value = "";
		if (document.getElementById("method").value == "updateSubReligion") {
			document.getElementById("subReligionName").value = document
					.getElementById("origSubRelName").value;
			document.getElementById("religion").value = document
			.getElementById("origRelId").value;
		}
		resetErrMsgs();
	}
	
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/SubReligion">
	<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${subRelOperation != null && subRelOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateSubReligion" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addSubReligion" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="subReligionId" styleId="subReligionId" />

	<html:hidden property="formName" value="subReligionForm" />
	<html:hidden property="origSubRelName" styleId ="origSubRelName" />
	<html:hidden property="origRelId" styleId ="origRelId" />
	<html:hidden property="formName" value="subReligionForm" />
	<html:hidden property="pageType" value="2" />

	<table width="100%" border="0">
		<tr>
			<td class="Bredcrumbs">
				<bean:message key="knowledgepro.admin" />
			 <span class="Bredcrumbs">&gt;&gt; Section Entry
			 &gt;&gt;</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Section Entry</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'>* Mandatory Fields</span></FONT></div>
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

							<%--<td width="16%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.religion.name" /></div>
							</td>
							<td width="32%" height="25" class="row-even"><label>
							<html:select property="religionId" styleClass="combo"
								styleId="religion">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="religionList" label="religionName"
									value="religionId" />
							</html:select> </label> <span class="star"></span></td>
							
							--%>
							
							<td width="17%" height="25" class="row-odd" colspan="2">
							<div align="right"><span class="Mandatory">*</span>Section:</div>
							</td>
							
							<td width="35%" class="row-even" colspan="2">
							<div align="left"><span class="star"> <html:text
								property="subReligionName" styleClass="TextBox"
								styleId="subReligionName" size="26" maxlength="50" /> </span></div>
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
											test="${subRelOperation != null && subRelOperation == 'edit'}">
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<!--<td height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.religionname" /></td>
										-->
									<td height="25" class="row-odd">
									<div align="center">Section</div>
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
								<logic:iterate name="subReligionList" id="subreligion"
									type="com.kp.cms.to.admin.ReligionSectionTO" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="8%" height="25" class="row-even">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<%--<td width="31%" height="25" class="row-even" align="center"><bean:write
													name="subreligion" property="religionTO.religionName" /></td>
													--%>
												<td width="41%" height="25" class="row-even" align="center"><bean:write
													name="subreligion" property="name" />
												<div align="right"></div>
												</td>

												<td width="11%" height="25" class="row-even">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer"
													onclick="editSubReligion('<bean:write name="subreligion" property="id"/>','<bean:write name="subreligion" property="name"/>',0)">
												</div>
												</td>
												<td width="9%" height="25" class="row-even">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer"
													onclick="deleteSubReligion('<bean:write name="subreligion" property="id"/>','<bean:write name="subreligion" property="name"/>')"></div>
												</td>
											</tr>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-white">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<%--<td height="25" class="row-white" align="center"><bean:write
													name="subreligion" property="religionTO.religionName" /></td>
													--%>
												<td height="25" class="row-white" align="center"><bean:write
													name="subreligion" property="name" /></td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer"
													onclick="editSubReligion('<bean:write name="subreligion" property="id"/>','<bean:write name="subreligion" property="name"/>',0)">
												</div>
												</td>

												<td height="25" class="row-white">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer"
													onclick="deleteSubReligion('<bean:write name="subreligion" property="id"/>','<bean:write name="subreligion" property="name"/>')"></div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
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