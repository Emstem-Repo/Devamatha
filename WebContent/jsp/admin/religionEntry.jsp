<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<SCRIPT type="text/javascript">
	function editReligion(religionId, religionName) {
		document.getElementById("religionId").value = religionId;
		document.getElementById("religionName").value = religionName;
		document.getElementById("editedField").value = religionName;
		document.getElementById("religionName").select();
		document.getElementById("button").value="Update";
		document.getElementById("method").value = "editReligion";
		resetErrMsgs();
	}
	function deleteReligion(religionId,religionName) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "ReligionEntry.do?method=deleteReligion&religionId="
				+ religionId+"&religionName="+religionName;
		}
	}
	function resetValues()
			{
				document.getElementById("religionName").value ="";
				if(document.getElementById("method").value == "editReligion")
				{
					document.getElementById("religionName").value =document.getElementById("editedField").value;
				}
				resetErrMsgs();
		}
</SCRIPT>
<html:form action="/ReligionEntry">
	<c:choose>
		<c:when test="${Update == null}">

			<html:hidden property="method" styleId="method" value="addReligion" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="editReligion" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="religionId" styleId="religionId" />
	<html:hidden property="editedField" styleId="editedField" />
	<html:hidden property="formName" value="religionForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td class="heading"><html:link href="AdminHome.do"
				styleClass="Bredcrumbs">
				<bean:message key="knowledgepro.admin" />
			</html:link> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.religion" /> &gt;&gt;</span></td>
		</tr>
		<tr>
			<td>
			<table width="101%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admin.religionentry" /></strong></td>
					<td width="11"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT></div></td>
						</tr>
						<tr>
							<td width="46%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.religionname" /></div>
							</td>
							<td width="54%" height="25" class="row-even"><html:text
								property="religionName" styleId="religionName"
								styleClass="TextBox" size="20" maxlength="20" /><span
								class="star"></span></td>
						</tr>
						<tr>
							<td height="45" colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${Update!=null}">
											<html:submit styleClass="formbutton" styleId="button"
												value="Update"></html:submit>
										</c:when>
										<c:otherwise>
											<html:submit styleClass="formbutton" styleId="button"
												value="Add"></html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><html:button property="" styleClass="formbutton" onclick="resetValues()">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button></td>
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
									<td height="25" class="row-odd"><bean:message key="knowledgepro.admin.religionname" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:iterate name="religionForm" property="religionList"
									id="religionList" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="8%" height="25" class="row-even">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="74%" height="25" class="row-even"><bean:write
													name="religionList" property="religionName" /></td>
												<td width="10%" height="25" class="row-even">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editReligion('<bean:write name="religionList" property="religionId" />','<bean:write name="religionList" property="religionName" />')" /></div>
												</td>
												<td width="8%" height="25" class="row-even">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteReligion('<bean:write name="religionList" property="religionId" />','<bean:write name="religionList" property="religionName" />')" /></div>
												</td>
											</tr>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-white">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td height="25" class="row-white"><bean:write
													name="religionList" property="religionName" /></td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editReligion('<bean:write name="religionList" property="religionId" />','<bean:write name="religionList" property="religionName" />')" /></div>
												</td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteReligion('<bean:write name="religionList" property="religionId" />','<bean:write name="religionList" property="religionName" />')" /></div>
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
					<div align="center"></div>
					</td>
					<td width="11" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="31"><img src="images/Tright_03_05.gif" width="9"
						height="29"></td>
					<td width="985" background="images/Tcenter.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
