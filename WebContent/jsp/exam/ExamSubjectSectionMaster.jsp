<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<head>
<script type="text/javascript">
	function editSubjectSectionMaster(id) {
		document.location.href = "SubjectSectionMaster.do?method=editSSM&id="
				+ id;
		document.getElementById("submit").value = "Update";

	}
	function deleteSubjectSectionMaster(id, name) {
		deleteConfirm = confirm("Are you sure to delete " + name
				+ " this entry?");
		if (deleteConfirm) {
			document.location.href = "SubjectSectionMaster.do?method=deleteSSM&id="
					+ id;
		}
	}
	//SubjectSection
	function addSubjectSectionMaster() {
		document.getElementById("method").value = "addSubjectSectionMasterEntry";
	}

	function updateSubjectSectionMaster() {
		document.getElementById("method").value = "updateSubjectSectionMaster";
	}

	function reActivate() {
		var name = document.getElementById("name").value;
		document.location.href = "SubjectSectionMaster.do?method=reActivateSSMaster&name="
				+ name;
	}

	function resetMessages() {
		document.getElementById("name").value = document
				.getElementById("orgName").value;

		if (document.getElementById("orgIsinitialise").value == "on") {
			document.getElementById("isinitialise").checked = true;
		} else {

			document.getElementById("isinitialise").checked = false;
		}
	}
	function resetErrMsgs(id) {
		document.location.href = "SubjectSectionMaster.do?method=initSubjectSectionMaster&id="
				+ id;
	}
</script>
</head>
<html:form action="SubjectSectionMaster.do">

	<html:hidden property="formName" value="ExamSubjectSectionForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
		<c:when test="${examSSMOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateSubjectSectionMaster" />
			<html:hidden property="orgName" styleId="orgName" />
			<html:hidden property="orgIsinitialise" styleId="orgIsinitialise" />

			<html:hidden property="id" styleId="id" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addSubjectSectionMasterEntry" />
		</c:otherwise>
	</c:choose>

	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.subjectSectionMaster" />
			&gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.subjectSectionMaster" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
					<td height="44" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.sectionName" />:</div>
									</td>
									<td width="32%" height="25" class="row-even"><label></label>
									<span class="star"> <html:text property="name"
										styleId="name" maxlength="50" styleClass="TextBox" size="20" />

									</span></td>
									<td width="30%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.initialiseSlNo" />:</div>
									</td>
									<td width="35%" class="row-even"><html:checkbox
										property="isinitialise" styleId="isinitialise"></html:checkbox>

									</td>
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
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td width="100%" height="20" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="4"></td></tr>
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when
											test="${examSSMOperation != null && examSSMOperation == 'edit'}">

											<input name="submit" type="submit" class="formbutton"
												value="Update" />
										</c:when>
										<c:otherwise>
											<input name="submit" type="submit" class="formbutton"
												value="Submit" />

										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><c:choose>
										<c:when
											test="${examSSMOperation != null && examSSMOperation == 'edit'}">
											<html:button property="" styleClass="formbutton"
												onclick="resetMessages();" styleId="reset">
												<bean:message key="knowledgepro.admin.reset" />
											</html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												onclick="resetErrMsgs();" styleId="reset">
												<bean:message key="knowledgepro.admin.reset" />
											</html:button>
										</c:otherwise>
									</c:choose></td>
									<td width="2%"></td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="4"></td></tr>								
							</table>
							</td>
						</tr>
						<tr>
							<td height="45" colspan="4">
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
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.sectionName" /></td>
											<td height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.initialiseSlNo" /></td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.edit" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<c:set var="temp" value="0" />


										<logic:iterate name="ExamSubjectSectionForm"
											property="listOfSubjectSection" id="listOfSubjectSection"
											type="com.kp.cms.to.exam.ExamSubjectSectionMasterTO"
											indexId="count">


											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td width="6%" height="25" class="row-even">
														<div align="center"><c:out value="${count + 1}" /></div>
														</td>
														<td width="41%" height="25" class="row-even" align="left"><bean:write
															name="listOfSubjectSection" property="name" /></td>
														<td width="41%" class="row-even" align="left"><logic:equal
															name="listOfSubjectSection" property="isinitialise"
															value="0">
															<bean:message key="knowledgepro.exam.subjectSection.no" />

														</logic:equal> <logic:equal name="listOfSubjectSection"
															property="isinitialise" value="1">
															<bean:message key="knowledgepro.exam.subjectSection.yes" />
														</logic:equal></td>
														<td width="6%" height="25" class="row-even">
														<div align="center"><img src="images/edit_icon.gif"
															width="16" height="18" style="cursor: pointer"
															onclick="editSubjectSectionMaster('<bean:write name="listOfSubjectSection" property="id"/>')"></div>
														</td>
														<td width="6%" height="25" class="row-even">
														<div align="center"><img
															src="images/delete_icon.gif" width="16" height="16"
															style="cursor: pointer"
															onclick="deleteSubjectSectionMaster('<bean:write name="listOfSubjectSection" property="id"/>','<bean:write name="listOfSubjectSection" property="name"/>')"></div>
														</td>
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td width="6%" height="25" class="row-white">
														<div align="center"><c:out value="${count + 1}" /></div>
														</td>
														<td width="41%" height="25" class="row-white" align="left"><bean:write
															name="listOfSubjectSection" property="name" /></td>
														<td width="41%" class="row-white" align="left"><logic:equal
															name="listOfSubjectSection" property="isinitialise"
															value="0">
															<bean:message key="knowledgepro.exam.subjectSection.no" />

														</logic:equal> <logic:equal name="listOfSubjectSection"
															property="isinitialise" value="1">
															<bean:message key="knowledgepro.exam.subjectSection.yes" />
														</logic:equal></td>
														<td width="6%" height="25" class="row-white">
														<div align="center"><img src="images/edit_icon.gif"
															width="16" height="18" style="cursor: pointer"
															onclick="editSubjectSectionMaster('<bean:write name="listOfSubjectSection" property="id"/>')"></div>
														</td>
														<td width="6%" height="25" class="row-white">
														<div align="center"><img
															src="images/delete_icon.gif" width="16" height="16"
															style="cursor: pointer"
															onclick="deleteSubjectSectionMaster('<bean:write name="listOfSubjectSection" property="id"/>','<bean:write name="listOfSubjectSection" property="name"/>')"></div>
														</td>
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</logic:iterate>

									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
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
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>



	</table>
</html:form>