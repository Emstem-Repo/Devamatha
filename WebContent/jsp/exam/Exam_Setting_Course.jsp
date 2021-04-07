<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript" src="js/common.js"></script>
<%@page import="java.util.Map,java.util.HashMap"%>
<script language="JavaScript">
	function deleteExamSettingCourse(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "ExamSettingCourse.do?method=deleteExamSettingCourse&id="
					+ id;
		}
	}
	function editExamSettingCourse(id) {
		document.location.href = "ExamSettingCourse.do?method=editExamSettingCourse&id="
				+ id;

	}
		

	function x(){
		var id = document.getElementById("programType");
		
		var selectedArray = new Array();
		var count = 0;
		
		for ( var i = 0; i < id.options.length; i++) {
			if (id.options[i].selected) {
			selectedArray[count] = id.options[i].value;
				count++;
			}
		}
		
		return selectedArray.toString();

	}
	function getCourses() {
		var programTypeID=x();
		getCoursesByProgramTypes("coursesMap", programTypeID, "course",
				updateCourses);

	}

	function updateCourses(req) {
		updateOptionsFromMapMultiselect(req, "course", "- Select -");
	}

	function setCourseName() {

		document.getElementById("courseName").value = document
				.getElementById("course").options[document
				.getElementById("course").selectedIndex].text;
	}

	function isNumberKey(evt) {
		var charCode = (evt.which) ? evt.which : event.keyCode;
		if (charCode > 31 && (charCode < 48 || charCode > 57))
			return false;

		return true;
	}

	function isValidNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}

	function reActivate(id) {
		document.location.href = "ExamSettingCourse.do?method=reactivate&id="
			+ id;
	}
</script>
<html:form action="ExamSettingCourse.do" focus="programType">
	
	<html:hidden property="college" styleId="college" />
	<html:hidden property="formName" value="ExamSettingCourseForm" />
	<c:choose>
		<c:when test="${ExamSettingCourseOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateExamSettingCourse" />
			<html:hidden property="courseId" styleId="courseId" />

		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addExamSettingCourse" />
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${ExamSettingCourseForm.college == 'cjc'}">
			<html:hidden property="pageType" value="2" />
		</c:when>
		<c:otherwise>
			<html:hidden property="pageType" value="1" />
		</c:otherwise>
	</c:choose>



	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.examSettingCourse" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.examSettingCourse" /> </strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<div align="right"><span class='MandatoryMark'><bean:message
							key="knowledgepro.mandatoryfields" /></span></div>
						<tr>

							<td height="20" colspan="6" class="body" align="left">

							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg" 
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">


							<table width="100%" cellspacing="1" cellpadding="2">

								<tr>
									<td width="10%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td height="25" class="row-even"><nested:select
										property="programTypeId" styleClass="body" multiple="multiple"
										size="5" onchange="getCourses()" styleId="programType"
										style="width:200px">
										<nested:optionsCollection name="ExamSettingCourseForm"
											property="programTypeList" label="display" value="id"
											styleClass="comboBig" />
									</nested:select></td>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td class="row-even"><nested:select
										property="selectedCourse" styleClass="body"
										multiple="multiple" size="5" styleId="course"
										style="width:450px" onchange="setCourseName()">
											<c:if
													test="${ExamSettingCourseForm.coursesMap != null && ExamSettingCourseForm.coursesMap != ''}">
													

														<nested:optionsCollection property="coursesMap"
															label="value" value="key" styleClass="comboBig" />


													
												</c:if>
										
												<c:if
													test="${ExamSettingCourseForm.programTypeId != null && ExamSettingCourseForm.programTypeId != ''}">
													<c:set var="coursesMap"
														value="${baseActionForm.collectionMap['coursesMap']}" />
													<c:if test="${coursesMap != null}">

														<nested:optionsCollection property="coursesMap"
															label="value" value="key" styleClass="comboBig" />


													</c:if>
												</c:if>
											

									</nested:select></td>
								</tr>
							</table>
						
							<c:if test="${ExamSettingCourseForm.college != 'cjc'}">
								<table width="100%">
									<tr>
										<td width="10"></td>
										<td width="100%" class="body" background="images/0r.gif">
										<div align="left"><strong class="heading"><bean:message
											key="knowledgepro.exam.examSettingCourse.marksCalculationMethod" />
										</strong></div>
										</td>
										<td width="10"></td>
									</tr>
								</table>
								<table width="100%">
									<tr>
										<td valign="top" width="100%">
										<table width="100%">
											<logic:notEmpty name="ExamSettingCourseForm"
												property="revaluationTypeList">
												<tr>
													<nested:iterate name="ExamSettingCourseForm"
														property="revaluationTypeList" id="rList"
														type="com.kp.cms.to.exam.ExamRevaluationTO" indexId="count">
	
														<td width="25%" height="25" class="row-odd" align="right">
	
														<span class="Mandatory">*</span>&nbsp;<nested:write
															property="revaluationType" name="rList" />:</td>
														<td width="25%" height="25" class="row-even"><nested:select
															styleId="optionValue_<c:out value='${count}'/>"
															 property="optionValue" styleClass="combo">
															<html:option value="">
																<bean:message key="knowledgepro.admin.select" />
															</html:option>
	
															<html:option value="Latest Marks">
																<bean:message
																	key="knowledgepro.exam.examSettingCourse.latestMarks" />
															</html:option>
															<html:option value="Highest Marks">
																<bean:message
																	key="knowledgepro.exam.examSettingCourse.highestMarks" />
															</html:option>
														</nested:select></td>
	
														<c:if test="${(count + 1) % 3 == 0}">
															<tr>
														</c:if>
													</nested:iterate>
											</logic:notEmpty>
	
										</table>
										</td>
	
									</tr>
	
	
								</table>
								<table width="100%" align="center">
									<tr>
										<td height="25" width="50%" class="row-odd">
										<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
											key="knowledgepro.exam.examSettingCourse.improvement" />:</div>
										</td>
										<td height="25" width="50%" class="row-even"><html:select
											property="improvement" styleClass="combo"
											styleId="improvement">
											<html:option value="">
												<bean:message key="knowledgepro.admin.select" />
											</html:option>
											<html:option value="Latest Marks">
												<bean:message
													key="knowledgepro.exam.examSettingCourse.latestMarks" />
											</html:option>
											<html:option value="Highest Marks">
												<bean:message
													key="knowledgepro.exam.examSettingCourse.highestMarks" />
											</html:option>
										</html:select></td>
									</tr>
								</table>
								<br>
								<table width="100%" align="center">
									<tr>
										<td height="25" width="50%" class="row-odd">
										<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
											key="knowledgepro.exam.examSettingCourse.supplementaryForFailedSubject" />:</div>
										</td>
										<td height="25" width="50%" class="row-even"><html:select
											property="supplementaryForFailedSubject" styleClass="combo"
											styleId="supplementaryForFailedSubject">
											<html:option value="">
												<bean:message key="knowledgepro.admin.select" />
											</html:option>
											<html:option value="Only Final Exam">
												<bean:message
													key="knowledgepro.exam.examSettingCourse.onlyFinalExam" />
											</html:option>
											<html:option value="Both Internal And Final Exam">
												<bean:message
													key="knowledgepro.exam.examSettingCourse.bothInternalAndFinalExam" />
											</html:option>
										</html:select></td>
									</tr>
									<tr>
										<td height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
											key="knowledgepro.exam.examSettingCourse.minimumRequiredAttendanceWithoutFine" /></div>
										</td>
										<td height="25" class="row-even"><html:text
											property="minReqAttendanceWithoutFine" size="10" maxlength="6"
											styleId="minReqAttendanceWithoutFine"
											onkeypress="return isDecimalNumberKey(this.value,event)"
											onblur="isValidNumber(this)"></html:text></td>
									</tr>
									<tr>
										<td height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
											key="knowledgepro.exam.examSettingCourse.minimumRequiredAttendanceWithFine" /></div>
										</td>
										<td height="25" class="row-even"><html:text
											property="minReqAttendanceWithFine" size="10" maxlength="6"
											styleId="minReqAttendanceWithFine"
											onkeypress="return isDecimalNumberKey(this.value,event)"
											onblur="isValidNumber(this)"></html:text></td>
									</tr>
								</table>
							</c:if>
							<table width="100%">
								<tr>
									<td width="10"></td>
									<td width="100%" class="body">
									<div align="left"><strong class="heading"><bean:message
										key="knowledgepro.exam.examSettingCourse.passCriteria" /> </strong></div>
									</td>
									<td width="10"></td>
								</tr>
							</table>
							<table width="100%">
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.exam.examSettingCourse.aggregatePass" /></div>
									</td>
									<td height="25" class="row-even"><html:text
										property="aggregatePass" size="10" maxlength="6"
										styleId="aggregatePass"
										onkeypress="return isDecimalNumberKey(this.value,event)"
										onblur="isValidNumber(this)"></html:text></td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.exam.examSettingCourse.individualPass" /></div>
									</td>
									<td height="25" class="row-even"><html:text
										property="individualPass" size="10" maxlength="6"
										styleId="individualPass"
										onkeypress="return isDecimalNumberKey(this.value,event)"
										onblur="isValidNumber(this)"></html:text></td>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<c:choose>
									<c:when test="${ExamSettingCourseOperation == 'edit'}">
										<td width="45%" height="35">
														<div align="right"><html:submit styleClass="formbutton">
															<bean:message key="knowledgepro.update" />
														</html:submit></div>
														</td>
									</c:when>
									<c:otherwise>
										<td width="45%" height="35">
														<div align="right"><html:submit styleClass="formbutton">
															<bean:message key="knowledgepro.submit" />
														</html:submit></div>
														</td>
									</c:otherwise>
								</c:choose>
							
							<td width="2%"></td>
							<td width="53">
							<input type="reset" value="Reset" class="formbutton" onclick="resetErrMsgs()()"/>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
			




				<tr>
					<td height="97" valign="top" background="images/Tright_03_03.gif"></td>
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
							<table width="100%" height="86" border="0" cellpadding="0"
								cellspacing="1">
								<tr class="row-odd">
									<td width="64" class="bodytext">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" class="bodytext"><bean:message
										key="knowledgepro.admin.program" /></td>

									<td align="center" width="163" height="25" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.admin.course" /></div>
									</td>
									<nested:iterate name="ExamSettingCourseForm"
										property="revaluationTypeList" id="rListx"
										type="com.kp.cms.to.exam.ExamRevaluationTO" indexId="countx">

										<td align="center" width="140" height="25" class="bodytext">
										<div align="center"><nested:write
											property="revaluationType" name="rListx" /></div>
										</td>

									</nested:iterate>
									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examSettingCourse.improvement" /></div>
									</td>
									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examSettingCourse.supplementaryForFailedSubject" /></div>
									</td>
									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examSettingCourse.minimumRequiredAttendanceWithoutFine" /></div>
									</td>
									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examSettingCourse.minimumRequiredAttendanceWithFine" /></div>
									</td>


									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examSettingCourse.passCriteria" /></div>
									</td>

									<td>
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td>
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:iterate name="ExamSettingCourseForm"
									property="settingCourseList" id="eSCList" indexId="count"
									type="com.kp.cms.to.exam.ExamSettingCourseTO">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td class="bodytext">
									<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td width="13%" align="center" class="bodytext"><bean:write
										name="eSCList" property="programType" /></td>
									<td width="13%" align="center" class="bodytext"><bean:write
										name="eSCList" property="programCourse" /></td>

									<logic:iterate name="eSCList" property="revaluationList"
										id="rList" indexId="count1"
										type="com.kp.cms.to.exam.DisplayValueTO">
										<td width="22%" align="center" class="bodytext"><bean:write
											name="rList" property="value" /></td>
									</logic:iterate>
									
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eSCList"
										property="improvement" /></div>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eSCList"
										property="supplementaryForFailedSubject" /></div>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eSCList"
										property="minReqAttendanceWithoutFine" /></div>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eSCList"
										property="minReqAttendanceWithFine" /></div>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eSCList"
										property="passCriteria" /></div>
									</td>

									<td width="50" height="24">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor: pointer"
										onclick="editExamSettingCourse(
										'<bean:write name="eSCList" property="id"/>')" />

									</div>
									</td>
									<td width="54" height="24">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor: pointer"
										onclick="deleteExamSettingCourse('<bean:write name="eSCList" property="id"/>')" />
									</div>
									</td>
									</tr>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>

				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>