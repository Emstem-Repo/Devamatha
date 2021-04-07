<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">
	function getPrograms(programTypeId) {
		getProgramsByType("programMap", programTypeId, "program",
				updatePrograms);
		resetOption("course");
	}

	function updatePrograms(req) {
		updateOptionsFromMap(req, "program", "- Select -");
	}

	function getCourses(programId) {
		getCoursesByProgram("courseMap", programId, "course", updateCourses);
	}

	function updateCourses(req) {
		updateOptionsFromMap(req, "course", "- Select -");
	}
	function addGuidelinesEntry() {
		document.getElementById("method").value = "addGuidelinesEntry";
		document.guidelinesEntryForm.submit();
	}
	function deleteGuidelinesEntry(id) {
		deleteConfirm = confirm("Are you sure want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "GuidelinesEntry.do?method=deleteGuidelinesEntry&id="
					+ id;
		}
	}
	function editGuidelinesEntry(programTypeId, programId, courseId, id) {
		document.location.href = "GuidelinesEntry.do?method=editGuidelinesEntry&course="
				+ courseId
				+ "&programType="
				+ programTypeId
				+ "&program="
				+ programId + "&id=" + id;
	}
	function updateGuidelinesEntry() {
		document.getElementById("method").value = "updateGuidelinesEntry";
		document.guidelinesEntryForm.submit();
	}
	function downloadFile(selectedId) {
		document.location.href = "guidelineDownloadActionId.do?selectedId="
				+ selectedId;
	}
	function redirectControl() {
		document.location.href = "GuidelinesEntry.do?method=resetAll";
	}
	function reActivate() {
		var course = document.getElementById("course").value;
		var year = document.getElementById("year").value;
		document.location.href = "GuidelinesEntry.do?method=reActivateGuidelinesEntry&course="
				+ course + "&year=" + year;
	}
	function resetinAddMode(){
		document.getElementById("programType").selectedIndex = 0;
		document.getElementById("program").selectedIndex = 0;
		document.getElementById("course").selectedIndex = 0;
		document.getElementById("thefile").value = null;
		document.getElementById("year").value = resetYear();
		resetErrMsgs();
		resetOption("program");
		resetOption("course");
	}
</script>
<html:form action="/GuidelinesEntry" enctype="multipart/form-data"
	method="post">
	<html:hidden property="id" />
	<html:hidden property="formName" value="guidelinesEntryForm" />
	<html:hidden property="id" styleId="guidelinesId" />
	<html:hidden property="method" styleId="method" />
	<c:choose>
		<c:when test="${guidelinesOperation == 'edit'}">
			<html:hidden property="pageType" value="2" />
		</c:when>
		<c:otherwise>
			<html:hidden property="pageType" value="1" />
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.guidelinesentry" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.guidelinesentry" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news">
					</td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program.Type" /></div>
									</td>
									<td width="27%" height="25" class="row-even"><input
										type="hidden" name="prgTypeId" id="prgTypeId"
										value='<bean:write name="guidelinesEntryForm" property="programType"/>' />
									<html:select property="programType" styleClass="comboLarge" styleId="programType"
										onchange="getPrograms(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<cms:renderProgramTypes></cms:renderProgramTypes>
									</html:select> <span class="star"></span></td>
									<td width="29%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.prog" /></div>
									</td>
									<td width="24%" class="row-even"><input type="hidden"
										name="prgTypeId" id="prgTypeId"
										value='<bean:write name="guidelinesEntryForm" property="programType"/>' />
									<html:select property="program" styleClass="comboLarge" 
										styleId="program" onchange="getCourses(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<c:choose>
											<c:when test="${guidelinesOperation == 'edit'}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${guidelinesEntryForm.programType != null && guidelinesEntryForm.programType != ''}">
													<c:set var="programMap"
														value="${baseActionForm.collectionMap['programMap']}" />
													<c:if test="${programMap != null}">
														<html:optionsCollection name="programMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course.with.col" /></div>
									</td>
									<td height="25" class="row-even"><html:select
										property="course" styleClass="comboLarge" styleId="course">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<c:choose>
											<c:when test="${guidelinesOperation == 'edit'}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${guidelinesEntryForm.program != null && guidelinesEntryForm.program != ''}">
													<c:set var="courseMap"
														value="${baseActionForm.collectionMap['courseMap']}" />
													<c:if test="${courseMap != null}">
														<html:optionsCollection name="courseMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.fee.academicyear.col" /></div>
									</td>
									<td class="row-even"><span class="star"> <input
										type="hidden" id="yr" name="yr"
										value='<bean:write name="guidelinesEntryForm" property="year"/>' />
									<html:select property="year" styleClass="combo" styleId="year">
										<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
										<cms:renderYear>
										</cms:renderYear>
									</html:select> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.fileupload" /></div>
									</td>
									<td height="25" colspan="3" class="row-even">
									<c:choose>
										<c:when test="${guidelinesOperation == 'edit'}">
											<nested:file property="thefile" styleId="thefile"></nested:file>
											<a href="#"
												onclick="downloadFile('<bean:write name="guidelinesEntryForm" property="selectedId"/>')">
												<bean:message key="knowledgepro.guidelines.view"/> </a>
										</c:when>
										<c:otherwise>
											<nested:file property="thefile" styleId="thefile"></nested:file>
										</c:otherwise>
									</c:choose></td>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><c:choose>
								<c:when test="${guidelinesOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateGuidelinesEntry()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addGuidelinesEntry()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><c:choose>
								<c:when test="${guidelinesOperation == 'edit'}">
									<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetinAddMode()"></html:button>
								</c:otherwise>
							</c:choose></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" height="25" class="row-odd"><bean:message
										key="knowledgepro.admin.programtype" /></td>
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.program" /></div>
									</td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.course" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.fee.academicyear" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.file" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="guidelinesEntryForm"
									property="guideLinesDetails">
									<nested:iterate id="guidelines" name="guidelinesEntryForm"
										property="guideLinesDetails"
										type="com.kp.cms.to.admin.GuidelinesEntryTO" indexId="count">
										<bean:define id="year1" property="year" name="guidelines"
											type="java.lang.Integer"></bean:define>
										<% year1= year1.intValue(); %>
										<c:choose>
											<c:when test="${temp == 0}">
										<tr>
											<td width="4%" height="25" class="row-even">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="18%" height="25" class="row-even"><nested:write
												name="guidelines"
												property="courseTO.programTo.programTypeTo.programTypeName" /></td>
											<td align="center" width="14%" height="25" class="row-even">
											<div align="center"><nested:write name="guidelines"
												property="courseTO.programTo.name" /></div>
											</td>
											<td align="center" width="18%" class="row-even"><nested:write
												name="guidelines" property="courseTO.name" /></td>
											<td align="center" width="14%" class="row-even"><nested:write
												name="guidelines" property="year" />-<%=year1+1 %></td>
											<td align="center" width="18%" class="row-even"><nested:write
												name="guidelines" property="fileName" /></td>
											<td width="7%" height="25" class="row-even">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editGuidelinesEntry('<nested:write name="guidelines" property="courseTO.programTo.programTypeTo.programTypeId" />',
												'<nested:write name="guidelines" property="courseTO.programTo.id" />',
												'<nested:write name="guidelines" property="courseTO.id" />',
												'<nested:write name="guidelines" property="id" />')" /></div>
											</td>
											<td width="7%" height="25" class="row-even">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteGuidelinesEntry('<bean:write name="guidelines" property="id"/>')"></div>
											</td>
										</tr>
										<c:set var="temp" value="1" />
											</c:when>
											<c:otherwise>
											<tr>
											<td width="4%" height="25" class="row-white">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="18%" height="25" class="row-white"><nested:write
												name="guidelines"
												property="courseTO.programTo.programTypeTo.programTypeName" /></td>
											<td align="center" width="14%" height="25" class="row-white">
											<div align="center"><nested:write name="guidelines"
												property="courseTO.programTo.name" /></div>
											</td>
											<td align="center" width="18%" class="row-white"><nested:write
												name="guidelines" property="courseTO.name" /></td>
											<td align="center" width="14%" class="row-white"><nested:write
												name="guidelines" property="year" />-<%=year1+1 %></td>
											<td align="center" width="18%" class="row-white"><nested:write
												name="guidelines" property="fileName" /></td>
											<td width="7%" height="25" class="row-white">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editGuidelinesEntry('<nested:write name="guidelines" property="courseTO.programTo.programTypeTo.programTypeId" />',
												'<nested:write name="guidelines" property="courseTO.programTo.id" />',
												'<nested:write name="guidelines" property="courseTO.id" />',
												'<nested:write name="guidelines" property="id" />')" /></div>
											</td>
											<td width="7%" height="25" class="row-white">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteGuidelinesEntry('<bean:write name="guidelines" property="id"/>')"></div>
											</td>
										</tr>
										<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:notEmpty>
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
					<td height="34" valign="top" background="images/Tright_03_03.gif"></td>
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
<script type="text/javascript">
	var programType = document.getElementById("prgTypeId").value;
	if (programType.length != 0) {
		document.getElementById("programType").value = programType;
	}
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
</script>
