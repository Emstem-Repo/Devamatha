<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function addClassEntry() {
		document.getElementById("semesterNo").value = document
				.getElementById("termNo").options[document
				.getElementById("termNo").selectedIndex].text;
		document.getElementById("method").value = "addClassEntryEntry";
		document.classEntryForm.submit();
	}

	function deleteClassEntry(id) {
		deleteConfirm = confirm("Are you sure want to delete subject name?");
		if (deleteConfirm == true) {
			document.location.href = "ClassesEntry.do?method=deleteClassEntry&id="
					+ id;
		}
	}
	function editClassEntry(id) {
		document.getElementById("classId").value = id;
		document.getElementById("method").value = "editClassEntry";
		document.classEntryForm.submit();
	}

	function updateClassEntry() {
		document.getElementById("semesterNo").value = document
				.getElementById("termNo").options[document
				.getElementById("termNo").selectedIndex].text;
		document.getElementById("method").value = "updateClassEntry";
		document.classEntryForm.submit();
	}

	function reActivate() {
		var id = document.getElementById("activateId").value;
		document.location.href = "ClassesEntry.do?method=activateClassEntry&id="
				+ id;
	}
	function getSemistersByCourse(courseId) {
		var academicYear = document.getElementById("academicYear").value;
		getSemistersOnYearAndCourseScheme("semisterMap", courseId, "termNo",
				academicYear, updateSemisters);
	}
	function getSemisters(year) {
		var courseId = document.getElementById("course").value;
		getSemistersOnYearAndCourseScheme("semisterMap", courseId, "termNo",
				year, updateSemisters);
	}
	function updateSemisters(req) {
		updateOptionsFromMap(req, "termNo", "- Select -");
	}

	function getClasses(year) {
		document.getElementById("academicYear").value = year;
		document.getElementById("method").value = "setClassEntry";
		document.classEntryForm.submit();
	}

	function resetFields() {
		document.getElementById("course").selectedIndex = 0;
		document.getElementById("courseGroupCodeId").selectedIndex = 0;
		document.getElementById("termNo").selectedIndex = 0;
		document.getElementById("sectionName").value = "";
		document.getElementById("className").value = "";
		resetErrMsgs();
	}
</script>
<html:form action="/ClassesEntry" method="post">

	<input type="hidden" id="classId" name="id"
		value="<bean:write name="classEntryForm" property="id"/>" />
	<!-- usefull while edit -->
	<input type="hidden" id="classSchemewiseId" name="classSchemewiseId"
		value="<bean:write name="classEntryForm" property="classSchemewiseId"/>" />
	<!-- usefull while edit -->
	<input type="hidden" id="activateId" name="activateId"
		value="<bean:write name="classEntryForm" property="activateId"/>" />
	<!-- usefull while activate -->
	<input type="hidden" id="semesterNo" name="semesterNo" />
	<html:hidden property="formName" value="classEntryForm" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.attendance" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.attendance.classentry" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.attendance.classentry" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;
					<div align="right" style="color: red"><span
						class='MandatoryMark'><bean:message
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
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course.with.col" /></div>
									</td>
									<td height="25" class="row-even"><html:select
										property="courseId" styleClass="combo" styleId="course"
										name="classEntryForm"
										onchange="getSemistersByCourse(this.value)"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="classEntryForm" property="courseList">
											<html:optionsCollection property="courseList"
												name="classEntryForm" label="name" value="id" />
										</logic:notEmpty>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.fee.academicyear" />:</div>
									</td>
									<td class="row-even" align="left"><input type="hidden"
										id="tempyear" name="tempyear"
										value="<bean:write name="classEntryForm" property="year"/>" />
									<html:select property="year" styleId="academicYear"
										name="classEntryForm" styleClass="combo"
										onchange="getClasses(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.fee.semister" />:</div>
									</td>
									<td class="row-even"><html:select property="termNo"
										styleId="termNo" name="classEntryForm" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if test="${semisterMap != null}">
											<html:optionsCollection name="semisterMap" label="value"
												value="key" />
										</c:if>


									</html:select></td>
									<td height="25" class="row-odd">
									<div align="right">&nbsp;<bean:message
										key="knowledgepro.attendance.sectionname" />:</div>
									</td>
									<td class="row-even"><html:text property="sectionName"
										styleId="sectionName" name="classEntryForm" maxlength="25" />
									</td>

								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.classname" />:</div>
									</td>
									<td class="row-even"><html:text property="className"
										styleId="className" name="classEntryForm" maxlength="30" /></td>
									
									<!-- as per changes in new  UC-->
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.courseGroupCode" /></div>
									</td>
									<td width="19%" height="25" class="row-even"><span
										class="star"> <html:select property="courseGroupCodeId"
										styleClass="body" styleId="courseGroupCodeId">
										<html:option value="-1">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<logic:notEmpty name="classEntryForm" property="courseGroupCodeList">
										<html:optionsCollection property="courseGroupCodeList"
											label="display" value="id" />
										</logic:notEmpty>
										
										
										
										
									</html:select> </span></td>
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
								<c:when test="${ClassEntryOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateClassEntry()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addClassEntry()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><c:choose>
								<c:when test="${ClassEntryOperation == 'edit'}">
									<html:reset value="Reset" styleClass="formbutton"></html:reset>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetFields()"></html:button>
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
										key="knowledgepro.admin.course" /></td>
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.fee.academicyear" /></div>
									</td>
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.fee.semister" /></div>
									</td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.attendance.sectionname" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.attendance.classname" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:notEmpty name="classEntryForm" property="classesList">
									<logic:iterate id="classes" name="classEntryForm"
										property="classesList" indexId="count">
										<bean:define id="year1" property="year" name="classes"
											type="java.lang.Integer"></bean:define>
										<% year1= year1.intValue(); %>
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
											name="classes" property="courseTo.name" /></td>
										<td align="center" width="14%" height="25">
										<div align="center"><bean:write name="classes"
											property="year" />-<%=year1+1 %></div>
										</td>
										<td align="center" width="14%" height="25">
										<div align="center"><bean:write name="classes"
											property="termNo" /></div>
										</td>
										<td align="center" width="18%"><bean:write name="classes"
											property="sectionName" /></td>

										<td align="center" width="18%"><bean:write name="classes"
											property="className" /></td>
										<td width="7%" height="25">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor: pointer"
											onclick="editClassEntry('<nested:write name="classes" property="id" />')" /></div>
										</td>
										<td width="7%" height="25">
										<div align="center"><img src="images/delete_icon.gif"
											width="16" height="16" style="cursor: pointer"
											onclick="deleteClassEntry('<nested:write name="classes" property="id" />')" /></div>
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
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("academicYear").value = year;
	}
</script>
