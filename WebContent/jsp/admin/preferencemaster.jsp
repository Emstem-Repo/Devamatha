<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>


<script type="text/javascript">
	function getPrograms(programTypeId) {
		getProgramsByType("programMap", programTypeId, "programId",
				updatePrograms);
		resetOption("course");		
	}

	function updatePrograms(req) {
		updateOptionsFromMap(req, "programId", "- Select -");
	}

	function getCourses(programId) {
		getCoursesByProgram("coursesMap", programId, "course", updateCourses);
	}

	function updateCourses(req) {
		updateOptionsFromMap(req, "course", "- Select -");
	}

	function addPreferences() {
		document.getElementById("programTypeName").value = document
				.getElementById("programtype").options[document
				.getElementById("programtype").selectedIndex].text;
		document.getElementById("programName").value = document
				.getElementById("programId").options[document
				.getElementById("programId").selectedIndex].text;
		document.getElementById("courseName").value = document
				.getElementById("course").options[document
				.getElementById("course").selectedIndex].text;

		document.preferencesForm.submit();
	}
	function editPreferences(courseId, programId, programTypeId, prefId, progTypeName, progName, courseName) {
		document.location.href = "preferencemaster.do?method=editPreferences&courseId="
				+ courseId + "&programId=" + programId + "&programTypeId=" + programTypeId + "&prefId=" + prefId +
				"&editCourse=" + courseId + "&editProgram=" + programId + "&editProgramType=" + programTypeId + "&programTypeName=" +
				progTypeName + "&programName=" + progName + "&courseName=" + courseName;
	}
	function deleteAllPreferences(courseId) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "preferencemaster.do?method=deleteAllPreferenceForACourse&courseId="
					+ courseId;
		}
	}
	function resetPreferences(){
		resetFieldAndErrMsgs();		
		resetOption("programId");
		resetOption("course");
	}
	
</script>

<body>
<html:form action="preferencemaster" method="post">
	<html:hidden property="method" styleId="method"
		value="showPreferencesOnCourse" />
	<html:hidden property="programTypeName" styleId="programTypeName"
		value="" />
	<html:hidden property="courseName" styleId="courseName" value="" />
	<html:hidden property="programName" styleId="programName" value="" />
	<html:hidden property="pageType" value="1" />

	<table width="98%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td class="Bredcrumbs"><bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.admin.preference.master"/> &gt;&gt;</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td width="100%" background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admin.preference.master"/></strong></td>
					<td><img src="images/Tright_1_01.gif" width="9" height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatorymark"/></span></FONT></div>
								<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td height="49" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" height="30" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="161" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.interview.ProgramType" /></div>
											</td>
											<td width="164" class="row-even"><html:select
												name="preferencesForm" property="programTypeId"
												styleId="programtype" styleClass="combo"
												onchange="getPrograms(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<cms:renderProgramTypes></cms:renderProgramTypes>
											</html:select></td>
											<td width="140" class="row-odd">
											<div align="right" class="row-odd"><span
												class="Mandatory">*</span><bean:message
												key="knowledgepro.interview.Program" /></div>
											</td>
											<td width="167" class="row-even"><html:select
												name="preferencesForm" property="programId"
												styleId="programId" styleClass="combo"
												onchange="getCourses(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<c:if
													test="${preferencesForm.programTypeId != null && preferencesForm.programTypeId != ''}">
													<c:set var="programMap"
														value="${baseActionForm.collectionMap['programMap']}" />
													<c:if test="${programMap != null}">
														<html:optionsCollection name="programMap" label="value"
															value="key" />
													</c:if>
												</c:if>

											</html:select></td>
											<td width="127" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.interview.Course" /></div>
											</td>
											<td width="160" class="row-even"><html:select
												name="preferencesForm" property="courseId" styleId="course"
												styleClass="combo">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>

												<c:if
													test="${preferencesForm.programId != null && preferencesForm.programId != ''}">
													<c:set var="courseMap"
														value="${baseActionForm.collectionMap['coursesMap']}" />
													<c:if test="${courseMap != null}">
														<html:optionsCollection name="courseMap" label="value"
															value="key" />
													</c:if>
												</c:if>

											</html:select></td>
										</tr>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>

						<tr>
							<td height="45" colspan="6">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="46%" height="35">
									<div align="right"><html:button property=""
										styleClass="formbutton" value="Submit"
										onclick="addPreferences()"></html:button></div>
									</td>
									<td width="2%"></td>
									<td width="52%"><html:button property=""
										styleClass="formbutton" value="Reset"
										onclick="resetPreferences()"></html:button></td>
								</tr>
							</table>
							</td>
						</tr>


						<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td width="7%" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="29%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.admin.programtype" /></div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.admin.program" /></div>
											</td>
											<td width="29%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.admin.course" /></div>
											</td>
											<td width="9%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.edit" /></div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<logic:iterate id="preferencesList" name="preferencesList"
											indexId="count">
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
											<td align="center"><bean:write name="preferencesList"
												property="courseTO.programTo.programTypeTo.programTypeName" /></td>
											<td align="center"><bean:write name="preferencesList"
												property="courseTO.programTo.name" /></td>
											<td align="center"><bean:write name="preferencesList"
												property="courseTO.name" /></td>
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor:pointer" 
												onclick="editPreferences('<bean:write name="preferencesList" property="courseId"/>',
   											    '<bean:write name="preferencesList" property="courseTO.programTo.id"/>', 
												'<bean:write name="preferencesList" property="courseTO.programTo.programTypeTo.programTypeId"/>',
												'<bean:write name="preferencesList" property="prefId"/>', '<bean:write name="preferencesList" property="courseTO.programTo.programTypeTo.programTypeName"/>',
												'<bean:write name="preferencesList" property="courseTO.programTo.name"/>', '<bean:write name="preferencesList" property="courseTO.name"/>')">
											</div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor:pointer" 
												onclick="deleteAllPreferences('<bean:write name="preferencesList" property="courseTO.id"/>')">
											</div>
											</td>
											</tr>
										</logic:iterate>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<div align="center"></div>
					</td>
					<td width="5" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="931" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
