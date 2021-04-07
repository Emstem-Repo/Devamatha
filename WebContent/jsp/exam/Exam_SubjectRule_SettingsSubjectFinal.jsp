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

		 document.getElementById("method").value="Add";
		 document.ExamSubjectRuleSettingsForm.submit();

	}
	function theoryESE() {

		document.getElementById("method").value="theoryESE";
		document.ExamSubjectRuleSettingsForm.submit();

	}

	function practicalESE() {
		
		document.getElementById("method").value="practicalESE";
		document.ExamSubjectRuleSettingsForm.submit();

	}
	function subjectFinal() {
	
		 document.getElementById("method").value="subjectFinal";
		 document.ExamSubjectRuleSettingsForm.submit();

	}
	function practicalInternal() {

		document.getElementById("method").value="practicalInternal";
		document.ExamSubjectRuleSettingsForm.submit();

	}
	function onlyDecimalNumber(val, e) {
		var keynum;
		var keychar;
		var numcheck;
		if(e.keyCode==46 || e.keyCode>=37 &&  e.keyCode<=40)
		{
			return true;
		}	
		var charCode = (e.which) ? e.which : e.keyCode;
				if (charCode > 31 && (charCode < 48 || charCode > 57) )
					return false;
				

				return true;
				
		if (window.event) 
		{
			keynum = e.keyCode;
		} else if (e.which) 
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

</script>
<html:form action="/ExamSubjectRuleSettings.do" method="POST">
	<html:hidden property="formName" value="ExamSubjectRuleSettingsForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="submittedSubjectFinal" styleId="method"/>
	
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">&gt;&gt;Exams
			&gt;&gt;Subjects Definition&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Subjects
					Definition</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
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
					<td class="heading"><img src="images/subject_final.gif"
						alt="CMS" width="790" height="33" border="0" usemap="#Map">
					<map name="Map">
						<area shape="rect" coords="243,6,361,26"
							onclick="practicalInternal()">
						<area shape="rect" coords="491,6,591,29" onclick="subjectFinal()">
						<area shape="rect" coords="374,6,474,29" onclick="practicalESE()">
						<area shape="rect" coords="283,31,383,54" href="#">
						<area shape="rect" coords="128,5,228,28" onclick="theoryESE()">
						<area shape="rect" coords="10,5,115,28"
							onclick="theoryPracticalInternal()">
						<area shape="rect" coords="491,6,591,29" href="#">

						<area shape="rect" coords="128,5,228,28" href="#">
						<area shape="rect" coords="11,5,111,28" href="#">
					</map> <map name="Map">
						<area shape="rect" coords="491,6,591,29" href="#">
						<area shape="rect" coords="368,6,468,29" href="#">
						<area shape="rect" coords="245,5,345,28" href="#">
						<area shape="rect" coords="128,5,228,28" href="#">
						<area shape="rect" coords="11,5,111,28" href="#">
					</map></td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">Practical : Final</td>
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
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="25" class="row-white">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
											<table width="100%" align="center" cellpadding="2"
												cellspacing="1">
												<tr>
													<td width="50%" height="28" class="row-odd">
													<div align="right">Theory Exam :</div>
													</td>
													<td width="50%" height="25" class="row-even">
													<logic:notEmpty property="subjectFinalTO" name="ExamSubjectRuleSettingsForm">
													<nested:hidden	styleId="isSubjectFinalTheoryExamChecked" property="subjectFinalTO.isSubjectFinalTheoryExamChecked" />
													</logic:notEmpty>
													<html:checkbox
														property="subjectFinalTheoryExamChecked"
														styleId="subjectFinalTheoryExamChecked" /></td>
												</tr>
												<tr>
													<td width="42%" height="28" class="row-odd">
													<div align="right">Practical Exam :</div>
													</td>
													<td height="25" class="row-even">
													<logic:notEmpty property="subjectFinalTO" name="ExamSubjectRuleSettingsForm">
													<nested:hidden	styleId="isSubjectFinalPracticalExamChecked" property="subjectFinalTO.isSubjectFinalPracticalExamChecked" />
													</logic:notEmpty>
													<html:checkbox
														property="subjectFinalPracticalExamChecked"
														styleId="subjectFinalPracticalExamChecked" /></td>
												</tr>
												<tr>
													<td height="28" class="row-odd">
													<div align="right">Internal Exam :</div>
													</td>
													<td height="25" class="row-even">
													<logic:notEmpty property="subjectFinalTO" name="ExamSubjectRuleSettingsForm">
													<nested:hidden	styleId="isSubjectFinalInternalExamChecked" property="subjectFinalTO.isSubjectFinalInternalExamChecked" />
													</logic:notEmpty>
													<html:checkbox
														property="subjectFinalInternalExamChecked"
														styleId="subjectFinalInternalExamChecked" /></td>
												</tr>
												<tr>
													<td height="28" class="row-odd">
													<div align="right">Attendance :</div>
													</td>
													<td height="25" class="row-even">
													<logic:notEmpty property="subjectFinalTO" name="ExamSubjectRuleSettingsForm">
													<nested:hidden	styleId="isSubjectFinalAttendanceChecked" property="subjectFinalTO.isSubjectFinalAttendanceChecked" />
													</logic:notEmpty>
													<html:checkbox
														property="subjectFinalAttendanceChecked"
														styleId="subjectFinalAttendanceChecked" /></td>
												</tr>
											</table>
											</td>
											<td>
											<table width="100%" align="center" cellpadding="2"
												cellspacing="1">
												<tr>
													<td width="50%" class="row-odd">
													<div align="right">Minimum :</div>
													</td>
													<td width="50%" height="32" class="row-even"><html:text
														property="minimumSubjectFinal" size="4" maxlength="3"
														onkeypress="return onlyDecimalNumber(this.value, event)" /></td>
												</tr>
												<tr>
													<td width="48%" class="row-odd">
													<div align="right">Maximum :</div>
													</td>
													<td height="26" class="row-even"><html:text
														property="maximumSubjectFinal" size="4" maxlength="3"
														onkeypress="return onlyDecimalNumber(this.value, event)" />
													</td>
												</tr>
												<tr>
													<td class="row-odd">
													<div align="right">Valuated :</div>
													</td>
													<td height="31" class="row-even"><html:text
														property="valuatedSubjectFinal" size="4" maxlength="3"
														onkeypress="return onlyDecimalNumber(this.value, event)" /></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</td>
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
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="49%" height="35" align="right"><input name="Submit2" type="submit" class="formbutton" value="Submit" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" align="left"><input type="submit"
								name="back" value=Back class="formbutton"
								onclick="practicalESE()"></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
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
	var v1 = document.getElementById("isSubjectFinalTheoryExamChecked").value;
	var v2 = document.getElementById("isSubjectFinalPracticalExamChecked").value;
	var v3 = document.getElementById("isSubjectFinalInternalExamChecked").value;
	var v4 = document.getElementById("isSubjectFinalAttendanceChecked").value;
		if (v1 == 'true') {
		document.getElementById("subjectFinalTheoryExamChecked").checked = true;

	}
		else{
			document.getElementById("subjectFinalTheoryExamChecked").checked = false;
		}
	if (v2 == 'true') {
		document.getElementById("subjectFinalPracticalExamChecked").checked = true;
	}
	else{
		document.getElementById("subjectFinalPracticalExamChecked").checked = false;
	}
	if (v3 == 'true') {
		document.getElementById("subjectFinalInternalExamChecked").checked = true;
	}
	else{
		document.getElementById("subjectFinalInternalExamChecked").checked = false;
	}
	if (v4 == 'true') {
		document.getElementById("subjectFinalAttendanceChecked").checked = true;
	}
	else{
		document.getElementById("subjectFinalAttendanceChecked").checked = false;
	}
</script>
</html:form>
