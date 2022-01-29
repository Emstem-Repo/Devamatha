<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>

<script type="text/javascript">
	function addExamRoomType() {
		document.getElementById("method").value = "addERT";
	}

	function editExamRoomType(Id, name, desc) {
		document.getElementById("method").value = "updateERT";
		document.getElementById("examRoomTypeId").value = Id;
		document.getElementById("examRoomTypeName").value = name;
		document.getElementById("examRoomTypeDesc").value = desc;
		document.getElementById("button").value = "Update";
		document.getElementById("button1").value = "Cancel";
		document.getElementById("origERTName").value = name;
		document.getElementById("origERTDesc").value = desc;

	}
	function getPrev() {

		var x = document.getElementById("origERTName").value;
		x = x + "";
		document.getElementById("examRoomTypeName").value = x;

		var y = document.getElementById("origERTDesc").value;
		y = y + "";
		document.getElementById("examRoomTypeDesc").value = y;
	}

	function deleteExamRoomType(id) {
		deleteConfirm = confirm("Are you sure to delete this entry?");
		document.getElementById("examRoomTypeId").value = id;
		if (deleteConfirm)
			document.location.href = "ExamRoomType.do?method=deleteERT&examRoomTypeId="
					+ id;
	}
	function reActivate(id) {
		document.location.href = "ExamRoomType.do?method=reActivateERT&id="
				+ id;
	}

	function imposeMaxLength(evt, desc) {
		var keynum = (evt.which) ? evt.which : event.keyCode;
		if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
			return true;
		}
		var MaxLen = 255;
		return (desc.length < MaxLen);
	}
	function resetAll(id) {

		document.location.href = "ExamRoomType.do?method=initExamRoomType&id="
				+ id;

	}
</script>
</head>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="ExamRoomType.do" method="POST">

	<c:choose>
		<c:when
			test="${examRoomTypeOperation != null && examRoomTypeOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateERT" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addERT" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="examRoomTypeId" styleId="examRoomTypeId" />
	<html:hidden property="formName" value="ExamRoomTypeForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origERTName" styleId="origERTName" />
	<html:hidden property="origERTDesc" styleId="origERTDesc" />

	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.roomType" /> &gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.roomType" /></td>
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
										key="knowledgepro.exam.roomTypeName" />:</div>
									</td>
									<td width="32%" height="25" class="row-even"><span
										class="star"> <html:text property="examRoomTypeName"
										styleId="examRoomTypeName" size="20" maxlength="50" /> </span></td>
									<td width="30%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.roomTypeDesc" />:</div>
									</td>
									<td width="35%" class="row-even">
									<div align="left"><span class="star"> <html:textarea
										property="examRoomTypeDesc" styleId="examRoomTypeDesc"
										cols="30" rows="5"
										onkeypress="return imposeMaxLength(event,this.value)" />
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
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
								<tr>
									<td width="45%" height="35">
									<div align="right"><html:submit styleClass="formbutton"
										styleId="button">
									<c:choose>	<c:when
											test="${examRoomTypeOperation != null && examRoomTypeOperation == 'edit'}">
											<bean:message key="knowledgepro.update"/>				
										</c:when>
										<c:otherwise>
										<bean:message key="knowledgepro.submit" /></c:otherwise>
										</c:choose>
									</html:submit></div>
									</td>
									<td width="2%"></td>

									<td width="53%"><c:choose>
										<c:when
											test="${examRoomTypeOperation != null && examRoomTypeOperation == 'edit'}">
											<html:button property="button1" styleClass="formbutton"
												styleId="button1" onclick="getPrev();">
												<bean:message key="knowledgepro.cancel" />
											</html:button>
										</c:when>
										<c:otherwise>
											<html:button property="button1" styleClass="formbutton"
												styleId="button1" onclick="resetAll();">
												<bean:message key="knowledgepro.admin.reset" />
											</html:button>
										</c:otherwise>
									</c:choose></td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
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
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.exam.roomTypeName" /></td>
											<td height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.exam.roomTypeDesc" /></td>
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
										<logic:notEmpty name="ExamRoomTypeForm"
											property="roomTypeList">
											<logic:iterate id="RTList" name="ExamRoomTypeForm"
												property="roomTypeList" indexId="count">
												<c:choose>
													<c:when test="${temp == 0}">
														<tr class="row-even">
															<td width="6%" height="25">
															<div align="center"><c:out value="${count + 1}" /></div>
															</td>
															<td width="41%" height="25" align="center"><bean:write
																name="RTList" property="name" /></td>
															<td width="30%" class="bodytext" align="center"><bean:write
																name="RTList" property="desc" /></td>
															<td width="6%" height="25">
															<div align="center"><img src="images/edit_icon.gif"
																width="16" height="18" style="cursor: pointer"
																onclick="editExamRoomType('<bean:write name="RTList" property="id"/>',
																'<bean:write name="RTList" property="name"/>',
																'<bean:write name="RTList" property="desc"/>')"></div>
															</td>
															<td width="6%" height="25">
															<div align="center"><img
																src="images/delete_icon.gif" width="16" height="16"
																style="cursor: pointer"
																onclick="deleteExamRoomType('<bean:write name="RTList" property="id"/>')"></div>
															</td>
														</tr>
														<c:set var="temp" value="1" />
													</c:when>
													<c:otherwise>
														<tr class="row-white">
															<td width="6%" height="25">
															<div align="center"><c:out value="${count + 1}" /></div>
															</td>
															<td width="41%" height="25" align="center"><bean:write
																name="RTList" property="name" /></td>
															<td width="41%" class="bodytext" align="center"><bean:write
																name="RTList" property="desc" /></td>
															<td width="6%" height="25">
															<div align="center"><img src="images/edit_icon.gif"
																width="16" height="18" style="cursor: pointer"
																onclick="editExamRoomType('<bean:write name="RTList" property="id"/>','<bean:write name="RTList" property="name"/>','<bean:write name="RTList" property="desc"/>')"></div>
															</td>
															<td width="6%" height="25">
															<div align="center"><img
																src="images/delete_icon.gif" width="16" height="16"
																style="cursor: pointer"
																onclick="deleteExamRoomType('<bean:write name="RTList" property="id"/>')"></div>
															</td>
														</tr>
														<c:set var="temp" value="0" />
													</c:otherwise>
												</c:choose>
											</logic:iterate>

										</logic:notEmpty>
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