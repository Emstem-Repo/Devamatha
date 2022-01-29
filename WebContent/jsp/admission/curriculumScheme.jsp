<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="javaScript" type="text/javascript">
	function getPrograms(programTypeId) {
		getProgramsByType("programMap", programTypeId, "program",
				updatePrograms);
		resetOption("course");
	}

	function updatePrograms(req) {
		updateOptionsFromMap(req, "program", "- Select -");
	}

	function getCourses(programId) {
		getCoursesByProgram("coursesMap", programId, "course", updateCourses);
	}

	function updateCourses(req) {
		updateOptionsFromMap(req, "course", "- Select -");
	}

	function redirectControl() {
		document.location.href = "CurriculumScheme.do?method=initCurriculumScheme";
	}

	function deleteCurriculumScheme(id) {
		deleteConfirm = confirm("Are you sure want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "CurriculumScheme.do?method=deleteCurriculumScheme&id="
					+ id;
		}
	}
	
	function getAdmittedYear(year) {
		document.getElementById("year").value = year;
		document.getElementById("method").value = "FilterListToYear";
		document.curriculumSchemeForm.submit();
	}

	function editCurriculumScheme(id) {
		document.location.href = "CurriculumScheme.do?method=editCurriculumScheme&id="
				+ id;
	}
	function resetMessages() {
		document.getElementById("programType").selectedIndex = 0;
		document.getElementById("program").selectedIndex = 0;
		document.getElementById("course").selectedIndex = 0;
		document.getElementById("noOfScheme").selectedIndex = null;
		document.getElementById("schemeId").selectedIndex = 0;
		//document.getElementById("year").value = resetYear();
		resetErrMsgs();
		resetOption("course");
		resetOption("program");
	}
	function loadCurriculumDetailsForUpdate(){	
		document.getElementById("programTypeName").value = document
		.getElementById("programType").options[document
		.getElementById("programType").selectedIndex].text;
		document.getElementById("programName").value = document
		.getElementById("program").options[document
		.getElementById("program").selectedIndex].text;
		document.getElementById("courseName").value = document
		.getElementById("course").options[document
		.getElementById("course").selectedIndex].text;
		document.getElementById("yearName").value = document
		.getElementById("year").options[document
		.getElementById("year").selectedIndex].text;
		document.getElementById("noOfSchemeName").value = document
		.getElementById("noOfScheme").options[document
		.getElementById("noOfScheme").selectedIndex].text;
		document.getElementById("schemeIdName").value = document
		.getElementById("schemeId").options[document
		.getElementById("schemeId").selectedIndex].text;		
		document.getElementById("method").value = "loadCurriculumDetailsForUpdate";
	}
	function showCurriculumSchemeOnCourse(){
		document.getElementById("programTypeName").value = document
		.getElementById("programType").options[document
		.getElementById("programType").selectedIndex].text;
		document.getElementById("programName").value = document
		.getElementById("program").options[document
		.getElementById("program").selectedIndex].text;
		document.getElementById("courseName").value = document
		.getElementById("course").options[document
		.getElementById("course").selectedIndex].text;
		document.getElementById("yearName").value = document
		.getElementById("year").options[document
		.getElementById("year").selectedIndex].text;
		document.getElementById("noOfSchemeName").value = document
		.getElementById("noOfScheme").options[document
		.getElementById("noOfScheme").selectedIndex].text;
		document.getElementById("schemeIdName").value = document
		.getElementById("schemeId").options[document
		.getElementById("schemeId").selectedIndex].text;	
		document.getElementById("method").value = "showCurriculumSchemeOnCourse";
	}
</script>
<html:form action="/CurriculumScheme" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="curriculumSchemeForm" />
	<html:hidden property="programTypeName" styleId="programTypeName" value="" />
	<html:hidden property="programName" styleId="programName" value="" />
	<html:hidden property="courseName" styleId="courseName" value="" />
	<html:hidden property="academicYear" styleId="yearName" value="" />
	<html:hidden property="noOfSchemeName" styleId="noOfSchemeName" value="" />
	<html:hidden property="schemeName" styleId="schemeIdName" value="" />	
	<html:hidden property="pageType" value="1" />
	<c:choose>
	<c:when test="${operation == 'edit'}">
	<html:hidden property="pageType" value="1" />
	</c:when>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.curriculumscheme" /> &gt;&gt;</span></span></td>
		</tr>
			<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admission.curriculumscheme"/></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
							<td width="15%" class="row-odd">
							
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.admitted.Year" /></div>
							</td>
												
							
							
							<td width="20%" height="25" class="row-even"><span
								class="star"><input type="hidden" id="yr" name="yr"
								value='<bean:write name="curriculumSchemeForm" property="year"/>' />
							<html:select property="year" styleClass="combo" 
							styleId="year" name="curriculumSchemeForm"  onchange="getAdmittedYear(this.value)">
								<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
								<cms:renderYear></cms:renderYear>
							</html:select></span>
							</td>
							<td width="15%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.admin.program.Type" /></div>
							</td>

							<td width="30%" class="row-even"><input type="hidden"
								name="prgTypeId" id="prgTypeId"
								value='<bean:write name="curriculumSchemeForm" property="programType"/>' />
							<label><html:select property="programType"
								styleClass="comboLarge" onchange="getPrograms(this.value)"
								styleId="programType">
								<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
								<cms:renderProgramTypes></cms:renderProgramTypes>
							</html:select></label>
							
							</td>
							
						</tr>
						<tr>
						<td width="15%" class="row-odd">
						
						<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.admin.prog" /></div>
							</td>
							<td width="20%" height="25" class="row-even"><label>
							<html:select property="program" styleClass="comboLarge"
								styleId="program" onchange="getCourses(this.value)">
								<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
								<c:choose>
											<c:when test="${operation == 'edit'}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
								<c:if
									test="${curriculumSchemeForm.programType != null && curriculumSchemeForm.programType != ''}">
									<c:set var="programMap"
										value="${baseActionForm.collectionMap['programMap']}" />
									<c:if test="${programMap != null}">
										<html:optionsCollection name="programMap" label="value"
											value="key" />
									</c:if>
								</c:if>
								</c:otherwise>
								</c:choose>
							</html:select></label>
							</td>
							<td width="15%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.interview.Course" /></div>
							</td>
							<td width="30%" class="row-even"><html:select
								property="course" styleClass="comboLarge" styleId="course">
								<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
								<c:choose>
											<c:when test="${operation == 'edit'}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
								<c:if
									test="${curriculumSchemeForm.program != null && curriculumSchemeForm.program != ''}">
									<c:set var="courseMap"
										value="${baseActionForm.collectionMap['coursesMap']}" />
									<c:if test="${courseMap != null}">
										<html:optionsCollection name="courseMap" label="value"
											value="key" />
									</c:if>
								</c:if>
								</c:otherwise>
								</c:choose>
							</html:select>
							</td>
						</tr>
						<tr>
							<td width="15%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.admission.scheme/course.col" /></div>
							</td>
							<td width="20%" height="25" class="row-even"><span
								class="star"><input type="hidden" id="schemeorcourse"
								name="schemeorcourse"
								value='<bean:write name="curriculumSchemeForm" property="noOfScheme"/>' />
							<html:select property="noOfScheme" styleClass="combo"
								styleId="noOfScheme">
								<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
								<cms:renderSchemeOrCourse></cms:renderSchemeOrCourse>
							</html:select></span>
							
							</td>
							<td width="15%" height="25" class="row-odd">
							
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.admission.scheme.col" /></div>
							</td>
							<td width="30%" height="25" class="row-even"><input type="hidden"
								id="scheme" name="scheme"
								value='<bean:write name="curriculumSchemeForm" property="schemeId"/>' />
							<html:select name="curriculumSchemeForm" property="schemeId"
								styleClass="comboLarge" styleId="schemeId">
								<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
								<logic:notEmpty name="curriculumSchemeForm"	property="courseSchemeList">
								<html:optionsCollection name="curriculumSchemeForm"
									property="courseSchemeList" label="courseSchemeName"
									value="courseSchemeId" />
									</logic:notEmpty>
							</html:select></td>		
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
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<c:choose>
							<c:when test="${operation == 'edit'}">   
								<tr>
									<td width="45%" height="35">
									<div align="right"><html:submit styleClass="formbutton"
										value="Update" onclick="loadCurriculumDetailsForUpdate()"/></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><html:cancel styleClass="formbutton" value="Reset" onclick="resetMessages()" />
									</td>
								</tr>
								</c:when>
								<c:otherwise>
								<tr>
									<td width="45%" height="35">
									<div align="right"><html:submit styleClass="formbutton"
										value="Submit" onclick="showCurriculumSchemeOnCourse()" /></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><html:button property=""
										styleClass="formbutton" value="Reset"
										onclick="resetMessages()" /></td>
								</tr>
								</c:otherwise>
								</c:choose>
							</table>
					
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
									<td height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.programtype" /></div>
									</td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.program" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admin.course" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.admitted.Year" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
									<c:set var="temp" value="0" />
								<logic:notEmpty name="curriculumSchemeForm"
									property="schemeDetails">
									<nested:iterate id="curriculumscheme"
										name="curriculumSchemeForm" property="schemeDetails"
										type="com.kp.cms.to.admission.CurriculumSchemeTO"
										indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<bean:define id="year1" property="year"
														name="curriculumscheme" type="java.lang.Integer"></bean:define>
													<% year1= year1.intValue(); %>
													<td width="8%" height="25" class="row-even">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td align="center" width="14%" height="25" class="row-even"><nested:write
														name="curriculumscheme" property="programTypeName" /></td>
													<td align="center" width="14%" height="25" class="row-even"><nested:write
														name="curriculumscheme" property="programName" /></td>
													<td align="center" width="14%" height="25" class="row-even"><nested:write
														name="curriculumscheme" property="courseName" /></td>

													<td align="center" width="13%" class="row-even"><nested:write
														name="curriculumscheme" property="year" />-<%=year1+1 %></td>
													<td width="8%" height="25" class="row-even">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editCurriculumScheme('<bean:write name="curriculumscheme" property="id"/>')"></div>
													</td>
													<td width="7%" height="25" class="row-even">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deleteCurriculumScheme('<nested:write name="curriculumscheme" property="id"/>')"></div>
													</td>
												</tr>
												<c:set var="temp" value="1" />
											</c:when>
											<c:otherwise>
												<tr>
													<bean:define id="year1" property="year"
														name="curriculumscheme" type="java.lang.Integer"></bean:define>
													<% year1= year1.intValue(); %>
													<td width="8%" height="25" class="row-white">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td align="center" width="14%" height="25"
														class="row-white"><nested:write
														name="curriculumscheme" property="programTypeName" /></td>
													<td align="center" width="14%" height="25"
														class="row-white"><nested:write
														name="curriculumscheme" property="programName" /></td>
													<td align="center" width="14%" height="25"
														class="row-white"><nested:write
														name="curriculumscheme" property="courseName" /></td>

													<td align="center" width="13%" class="row-white"><nested:write
														name="curriculumscheme" property="year" />-<%=year1+1 %></td>
													<td width="8%" height="25" class="row-white">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editCurriculumScheme('<bean:write name="curriculumscheme" property="id"/>')"></div>
													</td>
													<td width="7%" height="25" class="row-white">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deleteCurriculumScheme('<nested:write name="curriculumscheme" property="id"/>')"></div>
													</td>
												</tr>
												<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:notEmpty>		
										
										
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
	var schemeId = document.getElementById("scheme").value;
	if (schemeId != null && schemeId.length != 0) {
		document.getElementById("schemeId").value = schemeId;
	}

	var schemeorcourseId = document.getElementById("schemeorcourse").value;
	if (schemeorcourseId != null && schemeorcourseId.length != 0) {
		document.getElementById("noOfScheme").value = schemeorcourseId;
	}
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}

	</script>