
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<SCRIPT type="text/javascript">
function editRoomMaster(id) {
	document.location.href = "ExamRoomMaster.do?method=editExamRoomMaster&id=" + id;

}
	function deleteRoomMaster(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "ExamRoomMaster.do?method=deleteExamRoomMaster&id="
					+ id;
		}
	}
	function reActivate(id) {
		document.location.href = "ExamRoomMaster.do?method=reactivateExamRoomMaster&id="
			+ id;
	}

	function imposeMaxLength(evt, comments) {
		var keynum = (evt.which) ? evt.which : event.keyCode;
		if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
			return true;
		}
		var MaxLen = 255;
		return (comments.length < MaxLen);
	}
</SCRIPT>

<html:form action="ExamRoomMaster.do" method="POST"
	enctype="multipart/form-data">
	<html:hidden property="formName" value="ExamRoomMasterForm"
		styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	
	<c:choose>
		<c:when test="${Update!=null}">
			<html:hidden property="method" styleId="method"
				value="updateExamRoomMaster" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addExamRoomMaster" />
		</c:otherwise>
	</c:choose>


	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.roomMaster" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top" class="news"></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29" /></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.roomMaster" /></strong></div>
					</td>
					<td width="13"><img src="images/Tright_1_01.gif" width="9"
						height="29" /></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td width="100%" valign="top" class="news">
					<table width="100%" height="211" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="25" colspan="6" class="mandatoryfield"></td>
						</tr>
						<tr>
							<td></td>
							<td>
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'>* Mandatory fields</span> </FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td height="35" colspan="6" valign="top" class="body">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="1">

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
											<td width="13%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span> <bean:message
												key="knowledgepro.exam.roomTypeName" /> <span class="star"></span>:</div>
											</td>
											<td width="19%" height="25" class="row-even"><span
												class="star"> <html:select property="roomTypeId"
												styleClass="body" styleId="roomTypeId" >
												<html:option value="-1">
													<bean:message key="knowledgepro.select" />
												</html:option>
												
												<html:optionsCollection name="roomTypeList" label="name"
													value="id" />
											</html:select> </span></td>

											<td colspan="2" width="19%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.roomNo" /><span class="star"></span>:</div>
											</td>
											<td width="24%" class="row-even"><span class="star">
											<html:text property="roomNo" maxlength="50" size="16"
												styleId="roomNo" styleClass="TextBox" /> </span></td>

										</tr>
										<tr>
											<td width="13%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span> <bean:message
												key="knowledgepro.exam.roomCapacity" /><span class="star"></span>:</div>
											</td>
											<td width="19%" height="25" class="row-even"><span
												class="star"> <html:text property="roomCapacity"
												styleClass="TextBox" styleId="roomCapacity" size="16"
												maxlength="20"/> </span></td>
											<td colspan="2" width="19%" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.exam.comments" /> <span class="star"></span>:</div>
											</td>
											<td width="24%" class="row-even"><span class="star">
											<html:textarea property="comments" styleId="comments"
												cols="20" rows="5"
												onkeypress="return imposeMaxLength(event,this.value)" /> </span></td>
										</tr>
										<tr>
											<td height="24" width="19%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span> <bean:message
												key="knowledgepro.exam.roomCapacityForExams" /></div>
											</td>
											<td class="row-even"><html:text
												property="roomCapacityForExams" styleClass="TextBox"
												styleId="roomCapacityForExams" size="16" maxlength="20" />
											</td>

											<td class="row-odd" colspan="2">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.roomCapacityForInternalExams" /><span
												class="star"></span>:</div>
											</td>
											<td class="row-even"><html:text
												property="roomCapacityForInternalExams" styleClass="TextBox"
												styleId="roomCapacityForInternalExams" size="16"
												maxlength="20" /></td>
										</tr>
										<tr>
											<td height="24" width="19%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.hostel.floorno" /><span
												class="star"></span>:</div>
											</td>
											<td class="row-even"><html:text property="floorNo"
												styleClass="TextBox" styleId="floorNo" size="16"
												maxlength="20" /></td>

											<td class="row-odd" colspan="2">
											<div align="right"><span class="Mandatory">*</span>Block No<span
												class="star"></span>:</div>
											</td>
											<td class="row-even"><html:text
												property="blockNo" styleClass="TextBox"
												styleId="blockNo" size="16"
												maxlength="15" /></td>
										</tr>

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
						<tr>
							<td height="35" colspan="6" valign="top" class="body">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
								<tr>
									<td width="47%" height="35">
									<div align="right"><c:choose>
										<c:when test="${Update!=null}">
											<html:submit styleClass="formbutton" styleId="button">
												<bean:message key="knowledgepro.update" />
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit styleClass="formbutton" styleId="button">
												<bean:message key="knowledgepro.submit" />
											</html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="1%"></td>
									<td width="52%"><html:reset styleClass="formbutton"
										styleId="Reset">
										<bean:message key="knowledgepro.admin.reset" />
									</html:reset></td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
							</table>
							</td>
						</tr>
						<tr>
							<td height="35" colspan="6" valign="top" class="body">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td height="25" class="row-odd" width="3%">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td height="25" class="row-odd" width="12%" align="center">
											<bean:message key="knowledgepro.exam.roomType" /></td>
											<td class="row-odd" width="10%" align="center"><bean:message
												key="knowledgepro.exam.roomNo" /></td>
											<td class="row-odd" width="10%" align="center"><bean:message
												key="knowledgepro.exam.roomCapacity" /></td>
											<td class="row-odd" width="15%" align="center"><bean:message
												key="knowledgepro.exam.comments" /></td>
											<td class="row-odd" width="15%" align="center"><bean:message
												key="knowledgepro.exam.roomCapacityForExams" /></td>
											
											<td class="row-odd" width="10%" align="center">Floor No</td>
											<td class="row-odd" width="10%" align="center">Block No</td>
											<td class="row-odd" width="7%">
											<div align="center"><bean:message
												key="knowledgepro.edit" /></div>
											</td>
											<td class="row-odd" width="6%">
											<div align="center"><bean:message
												key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<logic:iterate name="ExamRoomMasterForm"
											property="roomMasterList" id="RMList" indexId="count"
											type="com.kp.cms.to.exam.ExamRoomMasterTO">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td width="3%" height="25" class="row-even">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td width="12%" height="25" class="row-even"
															align="center"><bean:write name="RMList"
															property="roomType" /></td>
														<td width="10%" class="row-even" align="center"><bean:write
															name="RMList" property="roomNo" /></td>
														<td width="10%" class="row-even" align="center"><bean:write
															name="RMList" property="roomCapacity" /></td>
														<td width="15%" class="row-even" align="center"><bean:write
															name="RMList" property="comments" /></td>
														<td width="15%" class="row-even" align="center"><bean:write
															name="RMList" property="examCapacity" /></td>
														<td width="10%" class="row-even" align="center"><bean:write
															name="RMList" property="floorNo" /></td>
														<td width="10%" class="row-even" align="center"><bean:write
															name="RMList" property="blockNo" /></td>
														<td width="7%" height="25" class="row-even">
														<div align="center"><img src="images/edit_icon.gif"
																width="16" height="18" style="cursor: pointer"
																onclick="editRoomMaster
																('<bean:write name="RMList" property="id" />')" /> </div>
														</td>
														<td width="6%" height="25" class="row-even">
														<div align="center"> <img src="images/delete_icon.gif" style="cursor: pointer"
															width="16" height="16"
															onclick="deleteRoomMaster('<bean:write name="RMList" property="id" />')"/></div></td>
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td height="25" class="row-white">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td width="12%" height="25" class="row-white"
															align="center"><bean:write name="RMList"
															property="roomType" /></td>
														<td width="10%" class="row-white" align="center"><bean:write
															name="RMList" property="roomNo" /></td>
														<td width="10%" class="row-white" align="center"><bean:write
															name="RMList" property="roomCapacity" /></td>
														<td width="15%" class="row-white" align="center"><bean:write
															name="RMList" property="comments" /></td>
														<td width="15%" class="row-white" align="center"><bean:write
															name="RMList" property="examCapacity" /></td>
														<td width="10%" class="row-white" align="center"><bean:write
															name="RMList" property="floorNo" /></td>
														<td width="10%" class="row-white" align="center"><bean:write
															name="RMList" property="blockNo" /></td>
														<td width="7%" height="25" class="row-white">
														<div align="center"><img src="images/edit_icon.gif"
															style="cursor: pointer" width="16" height="18"
															onclick="editRoomMaster
															('<bean:write name="RMList" property="id" /> ')" /></div>
														</td>
														<td width="6%" height="25" class="row-white">
														<div align="center"><img
															src="images/delete_icon.gif" style="cursor: pointer"
															width="16" height="16"
															onclick="deleteRoomMaster('<bean:write	name="RMList" property="id" />')" /></div>
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
							</table>
							</td>

						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td width="100%" background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
