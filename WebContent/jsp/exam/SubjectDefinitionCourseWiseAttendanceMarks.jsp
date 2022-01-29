<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<link href="../css/styles.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	function editAttMaster(id) {
		var subjectCode = document.getElementById("subjectCode").value;
		var subjectName = document.getElementById("subjectName").value;
		var subId = document.getElementById("subId").value;
		var courseId = document.getElementById("courseId").value;
		//document.location.href = "ExamSubjectDefinitionCourseWise.do?method=editAttendenceDetail&id="+id;

		document.location.href = "ExamSubjectDefinitionCourseWise.do?method=editAttendenceDetail&subjectName="
				+ subjectName
				+ "&subjectCode="
				+ subjectCode
				+ "&subId="
				+ subId + "&id=" + id + "&courseId=" + courseId;
		document.getElementById("submit").value = "Update";
		//resetErrMsgs();
	}

	function deleteAttendance(id, name) {
		var subjectCode = document.getElementById("subjectCode").value;
		var subjectName = document.getElementById("subjectName").value;
		var subId = document.getElementById("subId").value;
		var courseId = document.getElementById("courseId").value;
		deleteConfirm = confirm("Are you sure to delete this entry?");

		if (deleteConfirm) {

			document.location.href = "ExamSubjectDefinitionCourseWise.do?method=deleteAttendance&subjectName="
					+ subjectName
					+ "&subjectCode="
					+ subjectCode
					+ "&subId="
					+ subId + "&id=" + id + "&courseId=" + courseId;
		}
	}

	function isValidNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}

	function resetMessages() {

		document.getElementById("marks").value = "";
		document.getElementById("startPercentage").value = "";
		document.getElementById("endPercentage").value = "";
		resetErrMsgs();

	}
	function goT0MainPage() {
		document.location.href = "ExamSubjectDefinitionCourseWise.do?method=initExamSubDefCourseWise";
	}

	function isNumberKey(evt) {
		var charCode = (evt.which) ? evt.which : event.keyCode
		if (charCode > 31 && (charCode < 48 || charCode > 57))
			return false;

		return true;

	}
	function cancelAction(){
		document.location.href = "ExamSubjectDefinitionCourseWise.do?method=editSubjectInfo";
	}
</script>
<html:form action="/ExamSubjectDefinitionCourseWise.do" method="POST">

	<html:hidden property="formName" value="ExamSubjectDefCourseForm" />
	<html:hidden property="pageType" value="4" />
	<html:hidden property="subjectCode" styleId="subjectCode"
		name="ExamSubjectDefCourseForm" />
	<html:hidden property="subjectName" styleId="subjectName"
		name="ExamSubjectDefCourseForm" />
	<html:hidden property="subId" styleId="subId"
		name="ExamSubjectDefCourseForm" />
	<html:hidden property="courseId" styleId="courseId" />

	<c:choose>
		<c:when
			test="${examAttOperation != null && examAttOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateAttendance" />
			<html:hidden property="id" styleId="id" />
			<html:hidden property="subId" styleId="subId" />

			<html:hidden property="orgStartPercentage"
				styleId="orgStartPercentage" />
			<html:hidden property="orgEndPercentage" styleId="orgEndPercentage" />
			<html:hidden property="orgMarks" styleId="orgMarks" />
			<html:hidden property="subjectCode" styleId="subjectCode"
				name="ExamSubjectDefCourseForm" />
			<html:hidden property="subjectName" styleId="subjectName"
				name="ExamSubjectDefCourseForm" />
			<html:hidden property="subId" styleId="subId"
				name="ExamSubjectDefCourseForm" />



		</c:when>
		<c:otherwise>
			<html:hidden property="subjectCode" styleId="subjectCode"
				name="ExamSubjectDefCourseForm" />
			<html:hidden property="subjectName" styleId="subjectName"
				name="ExamSubjectDefCourseForm" />
			<html:hidden property="subId" styleId="subId"
				name="ExamSubjectDefCourseForm" />

			<html:hidden property="pageType" value="4" />
			<html:hidden property="courseId" styleId="courseId" />

			<html:hidden property="method" styleId="method"
				value="addAtttendance" />
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.subjectDefinitionCourseWise" />&gt;&gt;</span></span></td>

		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.exam.subjectDefinitionCourseWise" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
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
									<td width="25%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.subjectsDefinition.subjectCode" />:</div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="ExamSubjectDefCourseForm" property="subjectCode" /></td>
									<td class="row-odd" height="25">
									<div align="right"><bean:message
										key="knowledgepro.admin.detailsubject.subjectname" />:</div>
									</td>
									<td width="20%" class="row-even"><bean:write
										name="ExamSubjectDefCourseForm" property="subjectName" /></td>
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
					<td valign="top" class="news">&nbsp;</td>
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
									<div align="right"><span class="Mandatory">*</span>Attendance
									Marks :</div>
									</td>
									<td height="25" class="row-even"><html:text
										property="marks" maxlength="10" styleId="marks"
										onblur="isValidNumber(this)" /></td>
									<td class="row-odd">&nbsp;</td>
									<td class="row-even">&nbsp;</td>
								</tr>
								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>From %
									:</div>
									</td>
									<td width="25%" height="25" class="row-even"><html:text
										property="startPercentage" maxlength="10"
										styleId="startPercentage" onblur="isValidNumber(this)" /></td>
									<td width="28%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>To %:</div>
									</td>
									<td width="26%" class="row-even"><html:text
										property="endPercentage" maxlength="10"
										styleId="endPercentage" onblur="isValidNumber(this)" /></td>
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="49%" height="35" align="right"><c:choose>
								<c:when
									test="${examAttOperation != null && examAttOperation == 'edit'}">

									<input name="submit" type="submit" class="formbutton"
										value="Update" />
								</c:when>
								<c:otherwise>
									<input name="submit" type="submit" class="formbutton"
										value="Add" />

								</c:otherwise>
							</c:choose></td>
							<td width="2%" align="center">&nbsp;</td>
							<td width="49%" align="left"><c:choose>
								<c:when
									test="${examAttOperation != null && examAttOperation == 'edit'}">

									<input type="button" class="formbutton" value="Reset"
										onclick="resetMessages()" />
								</c:when>
								<c:otherwise>
									<input type="Reset" class="formbutton" value="Reset"
										onclick="resetErrMsgs()" />

								</c:otherwise>
							</c:choose>
							<input type="button"
								class="formbutton" value="Cancel" onclick="cancelAction()"/>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
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
									<td width="20%" height="25" class="row-odd" align="left"><bean:message
										key="knowledgepro.exam.attendanceMarks" /></td>
									<td width="10%" height="25" class="row-odd" align="left"><bean:message
										key="knowledgepro.exam.fromPercentage" /> %</td>
									<td width="10%" height="25" class="row-odd" align="left"><bean:message
										key="knowledgepro.exam.toPercentage" /> %</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>



								<c:set var="temp" value="0" />

								<logic:iterate name="ExamSubjectDefCourseForm"
									property="listAttendanceDetails" id="examAttList"
									type="com.kp.cms.to.exam.ExamSubCoursewiseAttendanceMarksTO"
									indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td height="25" class="row-even">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td height="25" class="row-even" align="left"><bean:write
													name="examAttList" property="attendanceMarks" /></td>
												<td height="25" class="row-even" align="left"><bean:write
													name="examAttList" property="fromPrcntgAttndnc" /></td>
												<td height="25" class="row-even" align="left"><bean:write
													name="examAttList" property="toPrcntgAttndnc" /></td>
												<td width="11%" height="25" class="row-even">
												<div align="center"><img src="images/edit_icon.gif"
													height="18" style="cursor: pointer"
													onclick="editAttMaster('<bean:write name="examAttList" property="id"/>')">
												</div>
												</td>
												<td height="25" class="row-even">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor: pointer"
													onclick="deleteAttendance('<bean:write name="examAttList" property="id"/>','<bean:write name="examAttList" property="subjectId"/>')"></div>
												</td>
											</tr>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-white">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td height="25" class="row-white" align="left"><bean:write
													name="examAttList" property="attendanceMarks" /></td>
												<td height="25" class="row-white" align="left"><bean:write
													name="examAttList" property="fromPrcntgAttndnc" /></td>
												<td height="25" class="row-white" align="left"><bean:write
													name="examAttList" property="toPrcntgAttndnc" /></td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/edit_icon.gif"
													height="18" style="cursor: pointer"
													onclick="editAttMaster('<bean:write name="examAttList" property="id"/>')">
												</div>
												</td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor: pointer"
													onclick="deleteAttendance('<bean:write name="examAttList" property="id"/>','<bean:write name="examAttList" property="subjectId"/>')"></div>
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
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>

							<td width="2%"></td>

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

