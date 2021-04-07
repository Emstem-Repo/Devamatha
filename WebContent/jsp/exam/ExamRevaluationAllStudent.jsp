<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript">
	function goToFirstPage() {
		document.location.href = "ExamRevaluation.do?method=initExamRevaluation";
	}
	function isNumberKey(evt) {
		var charCode = (evt.which) ? evt.which : event.keyCode
		if (charCode > 31 && (charCode < 48 || charCode > 57)) {
			if (charCode == 46)
				return true;
			return false;
		}

		return true;

	}
</script>

<html:form action="/ExamRevaluation.do" method="POST"
	enctype="multipart/form-data">
	<html:hidden property="formName" value="ExamRevaluationForm"
		styleId="formName" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method"
		value="addAllStudentMarks" />
	<nested:hidden property="examNameId" name="ExamRevaluationForm" />
	<nested:hidden property="schemeId" name="ExamRevaluationForm" />
	<nested:hidden property="subjectTypeId" name="ExamRevaluationForm" />
	<nested:hidden property="evaluatorTypeId" name="ExamRevaluationForm" />
	<nested:hidden property="answerScriptTypeId" name="ExamRevaluationForm" />
	<nested:hidden property="courseId" name="ExamRevaluationForm" />
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt;Exam Revaluation All Student&gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Student
					Marks Entry - All Students Single Subject</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>

					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
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
									<td height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>

									<td height="25" class="row-even" colspan="3"><bean:write
										property="examName" name="ExamRevaluationForm"></bean:write></td>
								</tr>
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right">Course :</div>
									</td>
									<td width="23%" height="25" class="row-even"><bean:write
										property="course" name="ExamRevaluationForm"></bean:write></td>
									<td width="28%" class="row-odd">
									<div align="right">Scheme :</div>
									</td>
									<td width="26%" class="row-even"><bean:write
										property="scheme" name="ExamRevaluationForm"></bean:write></td>

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Subject Name:</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="subject" name="ExamRevaluationForm"></bean:write></td>
									<td height="25" class="row-odd">
									<div align="right">Subject Type :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="subjectType" name="ExamRevaluationForm"></bean:write></td>



								</tr>

							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>

					</table>
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

									<td class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td height="25" class="row-odd">Register No.</td>
									<td class="row-odd">Roll No.</td>
									<td class="row-odd">Student Name</td>
									<logic:notEqual property="subjectType" value="Practical"
										name="ExamRevaluationForm">
										<td class="row-odd">Previous Theory Marks</td>
									</logic:notEqual>
									<logic:notEqual property="subjectType" value="Theory"
										name="ExamRevaluationForm">
										<td class="row-odd">Previous Practical Marks</td>
									</logic:notEqual>
									<logic:notEqual property="subjectType" value="Practical"
										name="ExamRevaluationForm">
										<td class="row-odd">Theory Marks</td>
									</logic:notEqual>
									<logic:notEqual property="subjectType" value="Theory"
										name="ExamRevaluationForm">
										<td class="row-odd">Practical Marks</td>
									</logic:notEqual>
								</tr>
								<nested:iterate property="examRevaluationStudentTO"
									indexId="count">
									<tr>
										<td width="7%" class="row-even">
										<div align="center"><c:out value="${count+1}"></c:out></div>
										</td>
										<td width="18%" class="row-even"><nested:write
											property="regNo" /></td>
										<td width="15%" class="row-even"><nested:write
											property="rolNo" /></td>
										<td width="20%" class="row-even"><nested:write
											property="studentName" /></td>
										<logic:notEqual property="subjectType" value="Practical"
											name="ExamRevaluationForm">
											<td width="18%" class="row-even"><nested:write
												property="previousTheoryMarks" /></td>
										</logic:notEqual>
										<logic:notEqual property="subjectType" value="Theory"
											name="ExamRevaluationForm">
											<td width="18%" class="row-even"><nested:write
												property="previousPracticalMarks" /></td>
										</logic:notEqual>
										<logic:notEqual property="subjectType" value="Practical"
											name="ExamRevaluationForm">
											<td width="18%" class="row-even"><nested:text
												property="currentTheoryMarks" maxlength="6"
												onkeypress="return isNumberKey(event)" /></td>
										</logic:notEqual>
										<logic:notEqual property="subjectType" value="Theory"
											name="ExamRevaluationForm">
											<td width="18%" class="row-even"><nested:text
												property="currentPracticalMarks" maxlength="6"
												onkeypress="return isNumberKey(event)" /></td>
										</logic:notEqual>
										<nested:hidden property="marksEntryId"></nested:hidden>
										<nested:hidden property="detailId"></nested:hidden>
										<nested:hidden property="studentId"></nested:hidden>
									</tr>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><input name="button2" type="submit"
								class="formbutton" value="Submit" /></div>
							</td>
							<td width="2%"></td>
							<td width="6%"><input type="button" class="formbutton"
								value="Cancel" onclick="goToFirstPage()" /></td>
							<td width="2%"></td>
							<td width="46%">&nbsp;</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>						
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
</html:form>
