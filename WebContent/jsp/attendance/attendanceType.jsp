<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function saveAttendanceType() {
		document.getElementById("method").value = "saveAttendanceType";
		document.attendanceTypeForm.submit();
	}
	function deleteAttendanceType(id) {
		deleteConfirm = confirm("Are you sure want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "AttendanceType.do?method=deleteAttendanceType&id="
					+ id;
		}
	}
	function editAttendanceType(id) {
		document.location.href = "AttendanceType.do?method=editAttendanceType&id="
				+ id;
	}
	function updateAttendanceType() {
		var obj= document.getElementById("mandatory").selectedIndex;
		if(obj == -1){
			document.getElementById("selectedIndex").value = -1;
		}else
			document.getElementById("selectedIndex").value = 1;

		document.getElementById("method").value = "updateAttendanceType";
		document.attendanceTypeForm.submit();

	}
	function resetFields(){
		document.location.href = "AttendanceType.do?method=initAttendanceType";
	}
</script>
<html:form action="/AttendanceType" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="selectedIndex" styleId="selectedIndex"/>
	<html:hidden property="formName" value="attendanceTypeForm" />
	<html:hidden property="id" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.attendance" /><span
				class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.attendance.attendancetype" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.attendance.attendancetype" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.attendance.type.colon" /></div>
									</td>
									<td class="row-even"><html:text property="attendanceType"
										styleId="attendanceType" size="20" maxlength="20"></html:text>
									<td width="13%" rowspan="2" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.attendance.mandatory.colon" /></div>
									</td>
									<td width="19%" rowspan="2" class="row-even"><html:select
										property="mandatory" multiple="multiple" size="6"
										styleId="mandatory">
										<html:option value="Class"></html:option>
										<html:option value="Subject"></html:option>
										<html:option value="Period"></html:option>
										<html:option value="Teacher"></html:option>
										<html:option value="Batch name"></html:option>
										<html:option value="Activity Type"></html:option>
									</html:select></td>
								</tr>
								<tr>
									<td width="18%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.attendance.default.colon" /></div>
									</td>
									<td width="20%" class="row-even">
									<table width="90%" border="0" cellspacing="0" cellpadding="0"
										class="row-even">
										<tr>
											<html:radio property="defaultValue" styleId="need"
												value="true">
												<bean:message key="knowledgepro.yes" />
											</html:radio>
											<html:radio property="defaultValue" styleId="need"
												value="false">
												<bean:message key="knowledgepro.no" />
											</html:radio>
										</tr>
									</table>
									<label></label></td>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="48%" height="35">
							<div align="right"><c:choose>
								<c:when test="${operation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateAttendanceType()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="saveAttendanceType()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="1%"></td>
							<td width="49%"><c:choose>
								<c:when test="${operation == 'edit'}">
									<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetFields()"></html:button>
								</c:otherwise>
							</c:choose></td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td height="54" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td align="center" width="25%" class="row-odd"><bean:message
										key="knowledgepro.attendance.type" /></td>
									<td align="center" width="15%" class="row-odd"><bean:message
										key="knowledgepro.attendance.default" /></td>
									<td align="center" width="40%" height="25" class="row-odd"><bean:message
										key="knowledgepro.attendance.mandatory" /></td>
									<td align="center" width="10%" class="row-odd"><bean:message
										key="knowledgepro.edit" /></td>
									<td align="center" width="10%" class="row-odd"><bean:message
										key="knowledgepro.delete" /></td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="attendanceTypeForm"
									property="attendanceTypeList">
									<nested:iterate name="attendanceTypeForm" id="att"
										property="attendanceTypeList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td align="center" width="210" class="row-even"><nested:write
														name="att" property="attendanceTypeName" /></td>
													<td align="center" width="170" class="row-even"><nested:write
														name="att" property="defaultValue" /></td>
													<td align="center" height="25" align="center"
														class="row-even"><label> <logic:notEmpty
														name="att" property="attendanceTypeMandatoryTOList">
														<nested:iterate name="att" id="mandatory"
															property="attendanceTypeMandatoryTOList">
															<nested:write name="mandatory" property="name" />
														</nested:iterate>
													</logic:notEmpty> </label></td>
													<td align="center" class="row-even"><img
														src="images/edit_icon.gif" width="16" height="18"
														style="cursor: pointer"
														onclick="editAttendanceType('<nested:write name="att" property="id"/>')"></td>
													<td align="center" class="row-even"><img
														src="images/delete_icon.gif" width="16" height="16"
														style="cursor: pointer"
														onclick="deleteAttendanceType('<nested:write name="att" property="id"/>')"></td>
												</tr>
												<c:set var="temp" value="1" />
											</c:when>
											<c:otherwise>
												<tr>
													<td align="center" width="210" class="row-white"><nested:write
														name="att" property="attendanceTypeName" /></td>
													<td align="center" width="170" class="row-white"><nested:write
														name="att" property="defaultValue" /></td>
													<td align="center" height="25" align="center"
														class="row-white"><logic:notEmpty name="att"
														property="attendanceTypeMandatoryTOList">
														<nested:iterate name="att" id="mandatory"
															property="attendanceTypeMandatoryTOList">
															<nested:write name="mandatory" property="name" />
														</nested:iterate>
													</logic:notEmpty></td>
													<td align="center" class="row-white"><span
														class="row-even"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editAttendanceType('<nested:write name="att" property="id"/>')"></span></td>
													<td align="center" class="row-white"><span
														class="row-white"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deleteAttendanceType('<nested:write name="att" property="id"/>')"></span></td>
												</tr>
												<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:notEmpty>
							</table>
							</td>
							<td background="images/right.gif" width="5" height="54"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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