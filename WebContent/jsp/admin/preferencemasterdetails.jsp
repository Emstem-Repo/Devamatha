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
		resetOption("prefProgramId");		
		resetOption("prefCourseId");		
	}

	function updatePrograms(req) {
		updateOptionsFromMap(req, "programId", "- Select -");
	}

	function getCourse(programId) {
		getCoursesByProgram("coursesMap", programId, "course", updateCourses);
	}

	function updateCourses(req) {
		updateOptionsFromMap(req, "course", "- Select -");
	}

	function getPrefCourses(programId) {
		getCoursesByProgram("prefcoursesMap", programId, "prefCourseId",
				updatePrefCourses);
	}

	function updatePrefCourses(req) {
		updateOptionsFromMap(req, "prefCourseId", "- Select -");
	}

	function addPreferences() {
		document.getElementById("method").value = "savePreferencesOnCourse";
		document.preferencesForm.submit();
	}
	function editPreferences(prefCourseid, prefProgId, prefId, courseId, programTypeId, programId) {
		document.location.href = "preferencemaster.do?method=editPreferencesForCourse&prefCourseId="
				+ prefCourseid
				+ "&prefProgramId="
				+ prefProgId
				+ "&prefId="
				+ prefId
				+ "&origCourseId="
				+ courseId
				+ "&origPrefCourseid="
				+ prefCourseid
				+"&programTypeId="
				+ programTypeId
				+ "&programId="
				+ programId 
				+ "&courseId=" +
				courseId;
	}
	function updatePreferences() {
		document.getElementById("method").value = "updatePreferencesOnCourse";
		document.preferencesForm.submit();
	}
	function deletePreferences(prefId) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm == true) {
			document.location.href = "preferencemaster.do?method=deletePreferenceForCourse&prefId="
					+ prefId;
		}
	}
	function returnToFirstPage() {
		document.getElementById("method").value = "initPreferencesMaster";
		document.preferencesForm.submit();
	}
	function resetPreferences(){
		resetOption("prefCourseId");
		resetFieldAndErrMsgs();		
	}
	
</script>

<html:form action="preferencemaster" method="post">
	<c:choose>
		<c:when test="${prefOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updatePreferencesOnCourse" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="savePreferencesOnCourse" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="prefId" styleId="prefId" />
	<html:hidden property="pageType" value="2" />

	<table width="99%" border="0" cellpadding="2" cellspacing="1">
		<tr>

			<td class="Bredcrumbs"><bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.preference.master"/> &gt;&gt;</span>
			<bean:message key="knowledgepro.admin.preferences.preferences"/></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><img src="images/Tright_03_01.gif" width="9" height="29"></td>

					<td width="100%" background="images/Tcenter.gif"
						class="heading_white"><bean:message key="knowledgepro.admin.preferences.preferences"/></td>
					<td><img src="images/Tright_1_01.gif" width="9" height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr>
							<td height="45" colspan="6">
							<table width="100%" cellspacing="1" cellpadding="2">
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
								<tr bgcolor="#FFFFFF">
									<td height="20" colspan="6">
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
													<td width="12%" height="25" class="row-odd">
													<div align="right"><span class="Mandatory">*</span>
													<bean:message key="knowledgepro.admin.program.type"/></div>
													</td>
													<td width="20%" class="row-even"><c:choose>
														<c:when test="${prefOperation == 'edit'}">
															<c:choose>
															<c:when test="${prefOperationfirst == 'editfirstpage'}">
															<html:select property="programTypeId" styleClass="combo" disabled="true"
																styleId="programType" onchange="getPrograms(this.value)">
																<html:option value="">
																	<bean:message key="knowledgepro.select" />
																</html:option>
																<html:optionsCollection name="programTypeList"
																	label="programTypeName" value="programTypeId" />
															</html:select>
															</c:when>
															<c:otherwise>
															<html:select property="programTypeId" styleClass="combo"
																styleId="programType" onchange="getPrograms(this.value)">
																<html:option value="">
																	<bean:message key="knowledgepro.select" />
																</html:option>
																<html:optionsCollection name="programTypeList"
																	label="programTypeName" value="programTypeId" />
															</html:select>																	
															</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<bean:write name="preferencesForm"
																property="programTypeName" />
														</c:otherwise>
													</c:choose></td>
													<td width="14%" class="row-odd">
													<div align="right"><span class="Mandatory">*</span>Program:</div>
													</td>
													<td width="22%" class="row-even"><c:choose>
														<c:when test="${prefOperation == 'edit'}">
															<c:choose>
															<c:when test="${prefOperationfirst == 'editfirstpage'}">
															<html:select name="preferencesForm" property="programId"
																styleId="programId" styleClass="combo" disabled="true"
																onchange="getCourse(this.value)">
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<c:if
																	test="${preferencesForm.programTypeId != null && preferencesForm.programTypeId != ''}">
																	<html:optionsCollection name="programMap" label="value"
																		value="key" />
																</c:if>
															</html:select>
															</c:when>
															<c:otherwise>
																<html:select name="preferencesForm" property="programId"
																styleId="programId" styleClass="combo"
																onchange="getCourse(this.value)">
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<c:if
																	test="${preferencesForm.programTypeId != null && preferencesForm.programTypeId != ''}">
																	<html:optionsCollection name="programMap" label="value"
																		value="key" />
																</c:if>
															</html:select>																	
															</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<bean:write name="preferencesForm" property="programName" />
														</c:otherwise>
													</c:choose></td>
													<td width="14%" class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.course.with.col"/></div>
													</td>

													<td width="18%" class="row-even"><c:choose>
														<c:when test="${prefOperation == 'edit'}">
															<c:choose>
															<c:when test="${prefOperationfirst == 'editfirstpage'}">
															<html:select name="preferencesForm" property="courseId"
																styleId="course" styleClass="combo" disabled="true">
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<c:if
																	test="${preferencesForm.programId != null && preferencesForm.programId != ''}">
																	<html:optionsCollection name="courseMap" label="value"
																		value="key" />
																</c:if>
															</html:select>
															</c:when>
															<c:otherwise>
															<html:select name="preferencesForm" property="courseId"
																styleId="course" styleClass="combo" >
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<c:if
																	test="${preferencesForm.programId != null && preferencesForm.programId != ''}">
																	<html:optionsCollection name="courseMap" label="value"
																		value="key" />
																</c:if>
															</html:select>
															</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<bean:write name="preferencesForm" property="courseName" />
														</c:otherwise>
													</c:choose></td>
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
									<td height="45" colspan="6">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td><img src="images/01.gif" width="5" height="5"></td>
											<td width="914" background="images/02.gif"></td>
											<td><img src="images/03.gif" width="5" height="5"></td>

										</tr>
										<tr>
											<td width="5" background="images/left.gif"></td>
											<td width="100%" height="27" valign="top">
											<table width="100%" height="22" border="0" cellpadding="0"
												cellspacing="1">
												<tr class="row-white">
													<td width="193" height="25" class="row-odd">
													<div align="right" class="row-odd"><span
														class="Mandatory">*</span><bean:message key="knowledgepro.admin.prog"/></div>
													</td>
													<td width="245" class="row-even"><c:choose>
														<c:when test="${prefOperationfirst == 'editfirstpage'}">
															<html:select name="preferencesForm"
																property="prefProgramId" styleId="prefProgramId"
																styleClass="combo" disabled="true" onchange="getPrefCourses(this.value)">
																<html:option value="">- Select -</html:option>
																<c:if
																	test="${preferencesForm.programTypeId != null && preferencesForm.programTypeId != ''}">
																	<html:optionsCollection name="PrefProgramMap"
																		label="value" value="key" />
																</c:if>
															</html:select>
														</c:when>
														<c:otherwise>
															<html:select name="preferencesForm"
																property="prefProgramId" styleId="prefProgramId"
																styleClass="combo" onchange="getPrefCourses(this.value)">
																<html:option value="">- Select -</html:option>
																<c:if
																	test="${preferencesForm.programTypeId != null && preferencesForm.programTypeId != ''}">
																	<html:optionsCollection name="PrefProgramMap"
																		label="value" value="key" />
																</c:if>
															</html:select>
														</c:otherwise>
															</c:choose>
														</td>
													<td width="227" class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.course.with.col"/></div>
													</td>
													<td width="261" class="row-even">
													<c:choose>
													<c:when test="${prefOperationfirst == 'editfirstpage'}">
													<html:select
														name="preferencesForm" property="prefCourseId"
														styleId="prefCourseId" styleClass="combo" disabled="true">
														<html:option value="">- Select -</html:option>
														<c:if
															test="${preferencesForm.prefProgramId != null && preferencesForm.prefProgramId != ''}">
															<html:optionsCollection name="prefCourseMap"
																label="value" value="key" />
														</c:if>
													</html:select>
													</c:when>
													<c:otherwise>
													<html:select
														name="preferencesForm" property="prefCourseId"
														styleId="prefCourseId" styleClass="combo">
														<html:option value="">- Select -</html:option>
														<c:if
															test="${preferencesForm.prefProgramId != null && preferencesForm.prefProgramId != ''}">
															<html:optionsCollection name="prefCourseMap"
																label="value" value="key" />
														</c:if>
													</html:select>
													</c:otherwise>
													</c:choose>
													</td>
												</tr>
											</table>
											</td>
											<td width="5" height="27" background="images/right.gif"></td>

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
											<div align="right"><c:choose>
												<c:when test="${prefOperation == 'edit'}">
													<html:button property="" styleClass="formbutton"
														value="Update" onclick="updatePreferences()"></html:button>
												</c:when>
												<c:otherwise>
													<html:button property="" styleClass="formbutton"
														value="Submit" onclick="addPreferences()"></html:button>
												</c:otherwise>
											</c:choose></div>
											</td>
											<td width="2%"></td>
											<td width="52%"><c:choose>
												<c:when test="${prefOperation == 'edit'}">
													<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
												</c:when>
												<c:otherwise>
													<html:button property="" styleClass="formbutton"
														value="Reset" onclick="resetPreferences()"></html:button>
												</c:otherwise>
											</c:choose></td>
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
													<td width="5%" height="25" class="row-odd">
													<div align="center"><bean:message key="knowledgepro.slno" /></div>
													</td>
													<td width="42%" class="row-odd">
													<div align="center"><bean:message key="knowledgepro.admin.program"/></div>
													</td>
													<td width="47%" class="row-odd">
													<div align="center"><bean:message key="knowledgepro.admin.course"/></div>
													</td>
													<c:if test="${prefOperation == 'edit'}">
														<td width="6%" height="25" class="row-odd">
														<div align="center"><bean:message key="knowledgepro.edit"/></div>
														</td>
													</c:if>
													<td width="6%" height="25" class="row-odd">
													<div align="center"><bean:message key="knowledgepro.delete"/></div>
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
														property="prefCourseTO.programTo.name" /></td>
													<td align="center"><bean:write name="preferencesList"
														property="prefCourseTO.name" /></td>
													<c:if test="${prefOperation == 'edit'}">
														<td height="25" align="center">
														<div align="center"><img src="images/edit_icon.gif"
															width="16" height="18" style="cursor:pointer" 
															onclick="editPreferences('<bean:write name="preferencesList" property="prefCourseId"/>',
   											    			'<bean:write name="preferencesList" property="prefCourseTO.programTo.id"/>',
															'<bean:write name="preferencesList" property="prefId"/>', '<bean:write name="preferencesList" property="courseTO.id"/>',
															'<bean:write name="preferencesList" property="courseTO.programTo.programTypeTo.programTypeId"/>',
															'<bean:write name="preferencesList" property="courseTO.programTo.id"/>')">
														</div>
														</td>
													</c:if>
													<td height="25">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor:pointer" 
														onclick="deletePreferences('<bean:write name="preferencesList" property="prefId"/>')">
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
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr></tr>

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
