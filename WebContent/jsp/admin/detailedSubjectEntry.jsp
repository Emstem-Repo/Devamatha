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

	function addDetiledSubjectEntry() {
		document.getElementById("method").value = "addDetailedSubjectEntry";
		document.detailedSubjectsForm.submit();
	}

	function deleteDetailedSubject(id) {
		deleteConfirm = confirm("Are you sure want to delete subject name?");
		if (deleteConfirm) {
			document.location.href = "DetailedSubjects.do?method=deleteDetailedSubject&id="+id;
		}
	}
	function editDetailedSubject(id) {
		
		document.getElementById("detailedSubjectId").value = id;
		document.getElementById("method").value = "editDetailedSubject";
		document.detailedSubjectsForm.submit();
	}

	function updateDetailedSubjectEntry() {
		document.getElementById("method").value = "updateDetailedSubjectEntry";
		document.detailedSubjectsForm.submit();
	}

	function reActivate() {
		var id = document.getElementById("activationId").value;
		document.location.href = "DetailedSubjects.do?method=activateDetailedSubject&activationId="+id;
	}
	function resetMessages() {
		document.getElementById("programType").selectedIndex = 0;
		document.getElementById("program").selectedIndex = 0;
		document.getElementById("course").selectedIndex = 0;
		document.getElementById("subjectName").value = "";
		resetErrMsgs();
	}
</script>
<html:form action="/DetailedSubjects" method="post">

	<input type="hidden" id="detailedSubjectId" name="id" value="<bean:write name="detailedSubjectsForm" property="id"/>"/>  <!-- usefull while edit -->
	<input type="hidden" id="activationId" name="activationId" value="<bean:write name="detailedSubjectsForm" property="activationId"/>"/>  <!-- usefull in activation -->
	<html:hidden property="formName" value="detailedSubjectsForm" />
	<c:choose>
		<c:when test="${detailedSubjectsOperation == 'edit'}">
				<html:hidden property="method" styleId="method" value="updateDetailedSubjectEntry"/>
		</c:when>
		<c:otherwise>
				<html:hidden property="method" styleId="method" value="addDetailedSubjectEntry"/>					
		</c:otherwise>
	</c:choose>
	
	
	<html:hidden property="pageType" value="1"/>
	
	<table width="100%" border="0">
	<tr>
    <td><span class="Bredcrumbs">Admin
    <span class="Bredcrumbs">&gt;&gt; Detailed Subject Entry </span></span></td>
  </tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Detailed Subject Entry</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;
					<div align="right" style="color:red"> <span class='MandatoryMark'>* Mandatory fields</span></div>
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
										value='<bean:write name="detailedSubjectsForm" property="programType"/>' />
									<html:select property="programType" styleClass="comboLarge" styleId="programType"
										onchange="getPrograms(this.value)">
										<html:option value="">- Select -</html:option>
										<c:choose>
											<c:when test="${detailedSubjectsOperation == 'edit'}">
												<html:optionsCollection name="programTypeList" label="programTypeName" value="programTypeId"/>
											</c:when>
											<c:otherwise>
												<cms:renderProgramTypes></cms:renderProgramTypes>
										</c:otherwise>
										</c:choose>
									</html:select> <span class="star"></span></td>
									<td width="29%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.prog" /></div>
									</td>
									<td width="24%" class="row-even"><input type="hidden"
										name="prgTypeId" id="prgTypeId"
										value='<bean:write name="detailedSubjectsForm" property="programType"/>' />
									<html:select property="program" styleClass="comboLarge"
										styleId="program" onchange="getCourses(this.value)">
										<html:option value="">- Select -</html:option>
										<c:choose>
											<c:when test="${detailedSubjectsOperation == 'edit'}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${detailedSubjectsForm.programType != null && detailedSubjectsForm.programType != ''}">
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
										<html:option value="">- Select -</html:option>
										<c:choose>
											<c:when test="${detailedSubjectsOperation == 'edit'}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${detailedSubjectsForm.program != null && detailedSubjectsForm.program != ''}">
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
									<div align="right"><span class="Mandatory">*</span>Subject Name:</div>
									</td>
									<td class="row-even"><html:text property="subjectName" styleId="subjectName" name="detailedSubjectsForm" maxlength="45"/> </td>
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
								<c:when test="${detailedSubjectsOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateDetailedSubjectEntry()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addDetiledSubjectEntry()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><c:choose>
								<c:when test="${detailedSubjectsOperation == 'edit'}">
									<html:cancel styleClass="formbutton"><bean:message key="knowledgepro.admin.reset" /></html:cancel>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetMessages();"></html:button>
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
									<td align="center" class="row-odd">Subject Name</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:notEmpty name="detailedSubjectsForm"
									property="detailedSubjectLists">
									<logic:iterate id="detailedSubject" name="detailedSubjectsForm"
										property="detailedSubjectLists" indexId="count">
										
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										
											<td width="4%" height="25">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="18%" height="25"><bean:write
												name="detailedSubject"	property="courseTo.programTo.programTypeTo.programTypeName" /></td>
											<td align="center" width="14%" height="25">
											<div align="center"><bean:write name="detailedSubject"
												property="courseTo.programTo.name" /></div>
											</td>
											<td align="center" width="18%"><bean:write
												name="detailedSubject" property="courseTo.name" /></td>
											
											<td align="center" width="18%">
											<bean:write name="detailedSubject" property="subjectName"/>
											</td>
											<td width="7%" height="25">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editDetailedSubject('<nested:write name="detailedSubject" property="id" />')"/></div>
											</td>
											<td width="7%" height="25">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteDetailedSubject('<nested:write name="detailedSubject" property="id" />')"/></div>
											</td>
										</tr>
									</logic:iterate>
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
</script>
