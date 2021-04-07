<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<SCRIPT type="text/javascript">

	function setTextValues(field) {
		if(isNaN(field.value)) {
			field.value= document.getElementById("hidden_"+field.id).value;
		}
	}
function cancelAction() {
	document.location.href = "MonthlyAttendanceEntry.do?method=initMonthlyAttendanceEntry&operation=editAttendance";
}

function validNumber(field) {
	if(isNaN(field.value)) {
		field.value="";
	}
}

function submitMonthlyAttendanceEntry() {
	if(validateMonthlyAttendance()) {
		document.getElementById("method").value = "updateMonthlyAttendanceList";
		document.monthlyAttendanceEntryForm.submit();
	}

}

function validateMonthlyAttendance() {
	var errorMessage = "";
	var isRecordedAdded = false;
	for ( var i = 0; i < document.forms[0].elements.length; i++) {
		var e = document.forms[0].elements[i];
		if ((e.type == 'text')) {
			var splitId = e.id.substr(0, 7);
			if (splitId == "student") {
				var dayId = e.id.substr(8);
				var dayValue = document.getElementById(dayId).value;
				var fieldValue = e.value;
				if (dayValue == "" && fieldValue != "") {
					errorMessage = errorMessage
							+ "Enter value for hours held for day  "
							+ (parseInt(dayId) + 1);
					document.getElementById("err").innerHTML = errorMessage;
					return false;
				} else if (fieldValue != "") {
					isRecordedAdded = true;
					if (parseInt(dayValue) < parseInt(fieldValue)) {
						errorMessage = errorMessage
								+ "Hours attended cannot be greater then Hours held for day  "
								+ (parseInt(dayId) + 1);
						document.getElementById("err").innerHTML = errorMessage;
						return false;
					}
				} else if (fieldValue == "" && dayValue != "") {
					isRecordedAdded = true;
				}

			}

		}
	}

	if (!isRecordedAdded) {
		errorMessage = errorMessage
				+ "Please add Hours Held and HoursAttended values.  ";
		document.getElementById("err").innerHTML = errorMessage;
		return false;
	}
	document.getElementById("err").innerHTML = errorMessage;
	return true;
}
	
</script>
<html:form action="/MonthlyAttendanceEntry" method="post">
	<html:hidden property="formName" value="monthlyAttendanceEntryForm" />
	<html:hidden property="method" styleId="method"
		value="" />
	<html:hidden property="pageType" value="1" />
	<div style="overflow: auto; width: 900px;">
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.attendanceentry.attendance" /> <span
				class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.attendanceentry.updatemonthlyattendance" /></span></span></td>
		</tr>
		<tr>
			<td>

			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.attendanceentry.updatemonthlyattendance" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td>
<div id="err" style=" color:red; font-family: verdana; font-size: 10px"></div>
					<div id="errorMessage"
						style="font-family: verdana; font-size: 10px"><FONT
						color="red"><html:errors /></FONT> <FONT color="green"> <html:messages
						id="msg" property="messages" message="true">
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
									<td width="10%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.attendance.activityattendence.class" />:</div>
									</td>
									<td width="18%" class="row-even"><c:out
										value="${monthlyAttendanceEntryForm.attendanceClass}" /></td>
									<td width="16%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.selectedSubjects" />:</div>
									</td>
									<td width="18%" class="row-even"><bean:write
										name="monthlyAttendanceEntryForm" property="attendanceSubject" /></td>
									<td width="15%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.attendanceentry.teacher" />:</div>
									</td>
									<td width="23%" class="row-even"><bean:write
										name="monthlyAttendanceEntryForm" property="attendanceTeacher" /></td>
								</tr>

								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.month" />  & Year:</div>
									</td>
									<td class="row-even"><bean:write
										name="monthlyAttendanceEntryForm" property="acaYear" /> & <bean:write
										name="monthlyAttendanceEntryForm" property="year1" /> </td>
									<td class="row-odd">
									<div align="right">Academic Year:</div>
									</td>
									<td class="row-even"><bean:write
										name="monthlyAttendanceEntryForm" property="academicYear" /></td>
									<td class="row-odd">&nbsp;</td>
									<td class="row-even">&nbsp;</td>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
							<table width="99%" border="0" align="center" cellpadding="0"
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

											<td width="30" align="left" class="row-odd"><bean:message
												key="knowledgepro.attendance.students" /></td>
											<td width="30" align="right" class="row-odd"><bean:message
												key="knowledgepro.attendanceentry.hoursheld" /></td>

											<nested:iterate name="monthlyAttendanceEntryForm"
												property="daysList" id="id" indexId="count">
												<td width="1" height="25" class="row-even">
												<div align="center">
												<%String style =  String.valueOf(count);%> 
												<%String hiddenstyle = "hidden_"+  String.valueOf(count);%> 
												
												<nested:hidden styleId="<%=hiddenstyle%>" property="hoursHeldOld"></nested:hidden>
												
												<nested:text
													styleClass="TextBox" style="width: 10px"  maxlength="1"
													styleId="<%=style %>" property="hoursHeld"
													onblur="setTextValues(this)"></nested:text></div>
												</td>
											</nested:iterate>
										</tr>

										<tr>
											<td width="30" class="row-odd" align="center"><c:choose>


												<c:when
													test="${monthlyAttendanceEntryForm.regNoDisplay == true}">
													<bean:message key="knowledgepro.attendanceentry.regno" />
												</c:when>
												<c:otherwise>
													<bean:message key="knowledgepro.attendanceentry.rollno" />
												</c:otherwise>
											</c:choose></td>
											<td width="30" class="row-odd" align="center"><bean:message
												key="knowledgepro.admin.name" /></td>
											<nested:iterate name="monthlyAttendanceEntryForm"
												property="noOfDaysList" id="id" indexId="count">

												<td width="2" height="25" class="row-odd" align="center">
												<c:out value="${count+1}"></c:out> &nbsp;</td>
											</nested:iterate>
										</tr>


										<c:set var="temp" value="0" />
										<nested:iterate name="monthlyAttendanceEntryForm"
											property="monthlyAttendanceTOList" indexId="count">

											<c:choose>

												<c:when test="${temp == 0}">

													<tr>
														<td width="1%" height="25" class="row-even">
														<div align="center"><nested:write
															property="registerNo" />&nbsp;</div>
														</td>
														<td width="1%" height="25" class="row-even">
														<div align="center"><nested:write
															property="studentName" />&nbsp;</div>
														</td>
														<nested:iterate property="hoursHeldTOList"
															indexId="count1"
															type="com.kp.cms.to.attendance.HourseHeldTO">
															<%String abc = "student_" + count1;%>
															<td width="1%" height="25" class="row-even">
															<div align="center"><span class="bodytext">

																<nested:text property="hourseHeld" styleId="<%=abc %>"
																	styleClass="TextBox" style="width: 10px"  maxlength="1"
																	onblur="validNumber(this)"></nested:text> </span> &nbsp;</div>
															</td>

														</nested:iterate>

													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td width="1%" height="25" class="row-white">
														<div align="center"><nested:write
															property="registerNo" />&nbsp;</div>
														</td>
														<td width="1%" height="25" class="row-white">
														<div align="center"><nested:write
															property="studentName" />&nbsp;</div>
														</td>
														<nested:iterate property="hoursHeldTOList"
															indexId="count1"
															type="com.kp.cms.to.attendance.HourseHeldTO">

															<td width="1%" height="25" class="row-white">
															<%String abc = "student_" + count1;%>
															<div align="center"><span class="bodytext">

	<nested:text property="hourseHeld" styleId="<%=abc %>"
																	styleClass="TextBox" style="width: 10px"  maxlength="1"
																	onblur="validNumber(this)"></nested:text>

															 </span> &nbsp;</div>
															</td>

														</nested:iterate>
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>

										</nested:iterate>


									</table>
									</td>

									<td background="images/right.gif" width="5" height="54"></td>

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
					</td>

					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center">
							<html:button	property="" styleClass="formbutton" onclick="submitMonthlyAttendanceEntry()">
								<bean:message key="knowledgepro.update" />
							</html:button> &nbsp;&nbsp;&nbsp; <html:button property=""
								styleClass="formbutton" onclick="cancelAction()">
								<bean:message key="knowledgepro.cancel" />
							</html:button></td>

						</tr>
					</table>
					</td>
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
	</div>
</html:form>