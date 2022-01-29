<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link href="../css/styles.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	function theoryPracticalInternal() {

		document.getElementById("method").value ="Add";
		document.ExamSubjectRuleSettingsForm.submit();

	}
	function theoryESE() {

		document.getElementById("method").value = "theoryESE";
		document.ExamSubjectRuleSettingsForm.submit();

	}

	function practicalESE() {
	
		document.getElementById("method").value = "practicalESE";
		document.ExamSubjectRuleSettingsForm.submit();

	}
	function subjectFinal() {

		document.getElementById("method").value = "subjectFinal";
		document.ExamSubjectRuleSettingsForm.submit();

	}
	function practicalInternal() {

		document.getElementById("method").value = "practicalInternal";
		document.ExamSubjectRuleSettingsForm.submit();

	}
	function isValidNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}

	function onlyDecimalNumber(val, e) {
		var keynum;
		var keychar;
		var numcheck;
		if(e.keyCode==46 || e.keyCode>=37 &&  e.keyCode<=40)
		{
			return true;
		}
		var charCode = (e.which) ? e.which : e.keyCode
		if (charCode > 31 && (charCode < 48 || charCode > 57))
			return false;

		return true;

		if (window.event) // IE
		{
			keynum = e.keyCode;
		} else if (e.which) // Netscape/Firefox/Opera
		{
			keynum = e.which;
		}
		if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
			return true;
		}
		if (keynum == 190) {
			var contain = val.indexOf('.');
			if (contain == -1) {
				return true;
			}
		}
		if (charCode > 31 && (charCode < 48 || charCode > 57))
			return false;
		keychar = String.fromCharCode(keynum);
		numcheck = /\d/;
		return numcheck.test(keychar);
	}
	function setValue(id) {
		var a = new Array();
		a = id.split("_");
		document.getElementById("finalMaxMarks_" + a[1]).value = document
				.getElementById("maxMark_" + a[1]).value;
	}
	function setFinalEntry() {

		document.getElementById("finalEntryMaximumMarks").value = document
				.getElementById("finalInternalMaximumMarks").value;
	}

	function checkIfChecked() {
		getCheckForSubjectRuleSetting("leaveAttendance");
		getCheckForSubjectRuleSetting("coCurricularAttendance");
		getCheckForSubjectRuleSetting("subInternalChecked");
		getCheckForSubjectRuleSetting("attendanceChecked");
		getCheckForSubjectRuleSetting("assignmentChecked");
		
		
		
	}
</script>

<html:form action="/ExamSubjectRuleSettings.do" method="POST"
	styleId="ExamSubjectRuleSettingsForm">


	<html:hidden property="method" value="theoryESE" styleId="method" />
	<html:hidden property="formName" value="ExamSubjectRuleSettingsForm" />
	<html:hidden property="pageType" value="1" />


	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><a href="main.html"
				class="Bredcrumbs"><bean:message key="knowledgepro.exam.exam" /></a>
			<span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.exam.subjectrulesettings" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.subjectrulesettings" /></strong></td>
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
					<td class="heading">
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
									<td width="22%" class="row-odd" height="28">
									<div align="right"><bean:message
										key="knowledgepro.petticash.academicYear" />:</div>
									</td>
									<td width="34%" class="row-even"><bean:write
										name="ExamSubjectRuleSettingsForm" property="academicYearName" />
									<html:hidden name="ExamSubjectRuleSettingsForm"
										property="academicYearName" /></td>
									<logic:notEqual value="" property="subjectName" name="ExamSubjectRuleSettingsForm">
										<td height="25" class="row-odd" align="right">
											Subject Name : 
										</td>
										<td class="row-even">
											<bean:write name="ExamSubjectRuleSettingsForm" property="subjectName" />
											<html:hidden name="ExamSubjectRuleSettingsForm" styleId="subjectName" property="subjectName" />
										</td>
									</logic:notEqual>
									<logic:equal value=""  property="subjectName" name="ExamSubjectRuleSettingsForm">
										<td class="row-even" colspan="2"></td>
									</logic:equal>

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.programtype" /> :</div>
									</td>
									<td width="25%" height="25" class="row-even"><bean:write
										name="ExamSubjectRuleSettingsForm" property="programName" />
									<html:hidden name="ExamSubjectRuleSettingsForm"
										property="programName" /></td>
									<td height="25" class="row-odd" align="right">Course:</td>

									<td class="row-even"><logic:iterate
										name="ExamSubjectRuleSettingsForm" property="listCourseName"
										id="listCourses" type="com.kp.cms.to.exam.ExamCourseUtilTO">

										<nested:write name="listCourses" property="display" />
                                        &nbsp;&nbsp;&nbsp;

									</logic:iterate></td>
								</tr>
								
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
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
					<td class="heading"><img src="images/exams_internal.gif"
						alt="CMS" width="790" height="33" border="0" usemap="#Map">
					<map name="Map">
						<area shape="rect" coords="240,6,358,26"
							onclick="practicalInternal()">
						<area shape="rect" coords="375,5,475,28" onclick="practicalESE()">
						<area shape="rect" coords="128,5,228,28" onclick="theoryESE()">
						<area shape="rect" coords="10,5,115,28"
							onclick="theoryPracticalInternal()">
						<area shape="rect" coords="490,4,590,27" onclick="subjectFinal()">
					</map></td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">Theory : Internal</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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
									<td height="25" colspan="4" class="row-white"><strong>Sub
									Internal</strong></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">Type</td>
									<td class="row-odd">Minimum Marks</td>
									<td class="row-odd">Maximum Marks</td>
									<td class="row-odd"><span class="style1">(Marks
									entered in)</span>Entry Maximum mark</td>
								</tr>


								<c:set var="temp" value="0" />

								<nested:iterate property="subInternalList" indexId="count">
									<c:choose>
										<c:when test="${count%2==0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>

									<td height="25" align="left"><nested:write property="type" />
									<nested:hidden property="isTheoryPractical" value="t" /> <nested:hidden
										property="id" /></td>
									<td height="25" align="left"><nested:text
										property="minimumMarks" styleId="minimumMarks" maxlength="3"
										onkeypress="return onlyDecimalNumber(this.value, event)"></nested:text>
									</td>
									<%
										String max = "maxMark_" + count;
													String finalMax = "finalMaxMarks_" + count;
									%>
									<td height="25" align="left"><nested:text
										property="maximumMarks" styleId="<%=max%>" maxlength="3"
										onkeypress="return onlyDecimalNumber(this.value, event)"></nested:text></td>
									<td height="25" align="left"><nested:text
										property="entryMaximumMarks" styleId="<%=finalMax%>"
										maxlength="3"
										onkeypress="return onlyDecimalNumber(this.value, event)"></nested:text>
									</td>
									</tr>

								</nested:iterate>

								<tr>
									<td class="row-odd">
									<div align="right">Total :</div>
									</td>
									<td class="row-odd"><html:text
										property="subTO.totalMinimummumMarks"
										styleId="entryMaximumMarks" maxlength="3"
										onkeypress="return onlyDecimalNumber(this.value, event)"></html:text></td>
									<td class="row-odd"><html:text
										property="subTO.totalMaximumMarks" styleId="entryMaximumMarks"
										maxlength="3"
										onkeypress="return onlyDecimalNumber(this.value, event)"></html:text></td>
									<td class="row-odd"><html:text
										property="subTO.totalentryMaximumMarks"
										styleId="entryMaximumMarks" maxlength="3"
										onkeypress="return onlyDecimalNumber(this.value, event)"></html:text></td>
								</tr>
								<tr>
									<td height="25" colspan="4" class="row-white"><strong>Select
									the best of <html:text property="subTO.selectTheBest"
										styleId="selectTheBest" maxlength="3"
										onkeypress="return onlyDecimalNumber(this.value, event)" /> </strong></td>
								</tr>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
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
					<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">Attendance</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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
									<div align="right">Attendance Type :</div>
									</td>
									<td width="20%" height="15" class="row-even"><html:hidden
										property="attTO.isTheoryPractical" value="t" /> <html:select
										property="attTO.attendanceType" styleClass="body"
										styleId="attendanceType">
										<html:option value="">
													--<bean:message key="knowledgepro.select" />--
												</html:option>
										<logic:notEmpty name="ExamSubjectRuleSettingsForm"
											property="listCourses">
											<html:optionsCollection property="attTO.attendanceTypeList"
												label="value" value="key" />
										</logic:notEmpty>

									</html:select></td>
								</tr>

								<tr>
									<td height="25" colspan="4" class="row-white"><strong>Consider
									the following for Attendance Marks</strong></td>
								</tr>
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right">Leave :</div>
									</td>
									<td width="23%" height="25" class="row-even"><nested:hidden
										styleId="checkedActiveDummyLeave"
										property="attTO.checkedActiveDummyLeave" />
									<input type="checkbox" name="attTO.leaveAttendance" id="leaveAttendance">
									</td>
									<td width="28%" class="row-odd">
									<div align="right">
									<DIV align="right">Co-Curricular :</DIV>
									</div>
									</td>
									<td width="26%" class="row-even"><nested:hidden
										styleId="checkedActiveDummyCoCurr"
										property="attTO.checkedActiveDummyCoCurr" /> 
							<input type="checkbox" name="attTO.coCurricularAttendance" id="coCurricularAttendance">
									
									
<script type="text/javascript">
	var v1 = document.getElementById("checkedActiveDummyCoCurr").value;
	var v2 = document.getElementById("checkedActiveDummyLeave").value;

	if (v1 == 'true') {
		document.getElementById("coCurricularAttendance").checked = "checked";
	}
	else
	{
		document.getElementById("coCurricularAttendance").checked = false;
	}	
	if (v2 == 'true') {
		document.getElementById("leaveAttendance").checked = "checked";
	}
	else{
		document.getElementById("leaveAttendance").checked = false;
	}
</script></td>

								</tr>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
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
					<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">Assignment</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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
									<td height="25" class="row-odd">Assignment</td>
									<td class="row-odd">Minimum Marks</td>
									<td class="row-odd">Maximum Marks</td>
								</tr>


								<c:set var="temp" value="0" />

								<nested:iterate property="assignmentList" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>

												<td height="25" class="row-even" align="left"><nested:write
													property="name" /> <nested:hidden property="id" /> <nested:hidden
													property="isTheoryPractical" value="t" /></td>

												<td height="25" class="row-even" align="left"><nested:text
													property="minimumAssignMarks" styleId="minimumAssignMarks"
													maxlength="3"
													onkeypress="return onlyDecimalNumber(this.value, event)"></nested:text>
												</td>
												<td height="25" class="row-even" align="left"><nested:text
													property="maximumAssignMarks" styleId="maximumAssignMarks"
													maxlength="3"
													onkeypress="return onlyDecimalNumber(this.value, event)"></nested:text></td>
											</tr>

											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-white" align="left"><nested:write
													property="name" /><nested:hidden property="id" /> <nested:hidden
													property="isTheoryPractical" value="t" /></td>
												<td height="25" class="row-white" align="left"><nested:text
													property="minimumAssignMarks" styleId="minimumAssignMarks"
													maxlength="3"
													onkeypress="return onlyDecimalNumber(this.value, event)"></nested:text>
												</td>
												<td height="25" class="row-white" align="left"><nested:text
													property="maximumAssignMarks" maxlength="3"
													styleId="maximumAssignMarks"
													onkeypress="return onlyDecimalNumber(this.value, event)"></nested:text></td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
								</nested:iterate>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
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
					<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">Final Internal Marks</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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
									<td width="17%" height="27" class="row-odd">
									<div align="right">Sub Internal:</div>
									</td>
									<td width="6%" height="27" class="row-even">
									<nested:hidden	styleId="checkedActiveDummySubInt" property="theoryIntTO.checkedActiveDummySubInt"/>
									<html:checkbox
										property="theoryIntTO.subInternalChecked"
										styleId="subInternalChecked"></html:checkbox></td>

									<td width="35%" height="27" class="row-odd">
									<div align="right">Attendance :</div>
									</td>
									<td width="14%" height="27" class="row-even">
									<nested:hidden	styleId="checkedActiveDummyAttendance" property="theoryIntTO.checkedActiveDummyAttendance" />
									<html:checkbox
										property="theoryIntTO.attendanceChecked"
										styleId="attendanceChecked"></html:checkbox></td>

									<td height="27" class="row-odd">
									<div align="right">Assignment :</div>
									</td>
									<td width="7%" height="27" class="row-even">
									<nested:hidden	styleId="checkedActiveDummyAssignment" property="theoryIntTO.checkedActiveDummyAssignment" />
									<html:checkbox
										property="theoryIntTO.assignmentChecked"
										styleId="assignmentChecked" ></html:checkbox>
																						
										
								  </td>
								</tr>

								<tr>
									<td width="17%" class="row-odd">
									<div align="right">Minimum :</div>
									</td>
									<td width="6%" height="25" class="row-even"><html:text
										property="theoryIntTO.finalInternalMinimumMarks" maxlength="3"
										styleId="finalInternalMinimumMarks"
										onkeypress="return onlyDecimalNumber(this.value, event)"></html:text></td>

									<td width="35%" class="row-odd">
									<div align="right"><span class="style1">(Marks
									entered in)</span>Entry Maximum mark :</div>
									</td>
									<td height="17%" class="row-even"><html:text
										property="theoryIntTO.finalEntryMaximumMarks" maxlength="3"
										styleId="finalEntryMaximumMarks"
										onkeypress="return onlyDecimalNumber(this.value, event)"></html:text></td>

									<td width="21%" class="row-odd">
									<div align="right">Maximum Mark:</div>
									</td>
									<td height="17%" class="row-even"><html:text
										property="theoryIntTO.finalInternalMaximumMarks" maxlength="3"
										styleId="finalInternalMaximumMarks"
										onkeypress="return onlyDecimalNumber(this.value, event)"></html:text></td>
								</tr>

							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
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
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" height="35" align="right"><input
								name="Submit2" type="submit" class="formbutton" value="Continue"
								onfocus="checkIfChecked()" /></td>
							<td width="49%" align="left">
								<html:cancel value="Close" styleClass="formbutton"></html:cancel>
							</td>
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
<script type="text/javascript">
	var v1 = document.getElementById("checkedActiveDummySubInt").value;
	var v2 = document.getElementById("checkedActiveDummyAttendance").value;
	var v3 = document.getElementById("checkedActiveDummyAssignment").value;
	if (v1 == 'true') {
		document.getElementById("subInternalChecked").checked = true;

	}
	else{
		document.getElementById("subInternalChecked").checked = false;
	}
	if (v2 == 'true') {
		document.getElementById("attendanceChecked").checked = true;
	}
	else{
		document.getElementById("attendanceChecked").checked = false;
	}
	if (v3 == 'true') {
		document.getElementById("assignmentChecked").checked = true;
	}
	else{
		document.getElementById("assignmentChecked").checked  = false;
	}
	</script>
</html:form>
