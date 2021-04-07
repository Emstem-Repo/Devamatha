<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<title>:: CMS ::</title>
<script>
	function set(target) {
		document.getElementById("method").value = target;
	}
	function checkSecured() {
		if (document.getElementById("checkbox").checked)
			return false;
		else
			return true;
	}

	

	function goToFirstPage() {
		document.location.href = "ExamStudentMarksCorrection.do?method=initExamStudentMarksCorrection";
	}

	
	function viewOldMarks(subjectId, examMasterId) {
		examNameId=document.getElementById("examNameId").value;
		studentId=document.getElementById("studentId").value;
		markType=document.getElementById("markType").value;
		courseId=document.getElementById("courseId").value;
		scheme=document.getElementById("schemeId").value;
		var url="ExamStudentMarksCorrection.do?method=viewOldMarksOverall&examNameId="+examNameId+"&studentId="+studentId+"&subjectId="+subjectId+"&markType="+markType+"&courseId="+courseId+"&scheme="+scheme + "&examMasterId=" + examMasterId;
		window.open(url,'Student Marks Correction');
	}
</script>

<html:form action="/ExamStudentMarksCorrection.do" method="POST"
	enctype="multipart/form-data">

	<html:hidden property="formName" value="ExamStudentMarksCorrectionForm"
		styleId="formName" />
	<html:hidden property="pageType" value="2" styleId="pageType" />
	<html:hidden property="method" styleId="method"
		value="addSingleStudent" />
	<c:choose>
		<c:when
			test="${examMarksEntryOperation != null && examMarksEntryOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="getStudentDetails" />
		</c:when>

	</c:choose>

	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Marks Entry&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>

					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Student
					Marks Entry - Single Student All Subjects</td>
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
							<table width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right">Course :</div>
									</td>
									<td width="23%" height="25" class="row-even"><html:hidden
										property="courseId" styleId="courseId" name="ExamStudentMarksCorrectionForm" />

									<bean:write name="ExamStudentMarksCorrectionForm"
										property="course" /></td>
									<td width="28%" class="row-odd">
									<div align="right">Scheme :</div>
									</td>
									<td width="26%" class="row-even"><html:hidden
										property="schemeId" styleId="schemeId" name="ExamStudentMarksCorrectionForm" />

									<bean:write name="ExamStudentMarksCorrectionForm"
										property="scheme" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Subject Type :</div>
									</td>
									<td height="25" class="row-even"></td>
									<td height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>
									<td height="25" class="row-even"><html:hidden
										property="examNameId" styleId="examNameId"
										name="ExamStudentMarksCorrectionForm" /> <bean:write
										name="ExamStudentMarksCorrectionForm" property="examName" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Final/Internal Exam Type :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="ExamStudentMarksCorrectionForm" property="examType" /></td>
									<td height="25" class="row-odd">&nbsp;</td>
									<td height="25" class="row-even">&nbsp;</td>
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
									<td width="20%" height="25" class="row-odd">
									<div align="right">Register No. :</div>
									</td>
									<td width="16%" height="25" class="row-even"><html:text
										property="regNo" styleId="regNo" maxlength="10"
										styleClass="TextBox" size="10"/></td>
									<td width="11%" class="row-odd">
									<div align="right">Roll No. :</div>
									</td>
									<td width="20%" class="row-even"><html:text
										property="rollNo" styleId="rollNo" maxlength="10"
										styleClass="TextBox" size="10"/></td>
									<td width="20%" class="row-odd">
									<div align="right">Student Name. :</div>
									</td>
									<td width="13%" class="row-even"><bean:write
										property="studentName" name="ExamStudentMarksCorrectionForm" /></td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="35" class="heading">
					<div align="center"><input type="submit" class="formbutton"
						value="Search" onclick="set('getStudentDetails')" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<nested:notEmpty property="singleStuFor_AllSubjects">
					<c:choose>
						<c:when
							test="${ExamStudentMarksCorrectionForm.listStudentDetailsSize != 0 }">
	
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
												<td height="25" class="row-odd">Subject</td>
												<td class="row-odd">Theory</td>
												<td class="row-odd">Practical</td>
												<td class="row-odd">&nbsp;</td>
												<td class="row-odd">Mistake</td>
												<td class="row-odd">Retest</td>
												<td class="row-odd">Comments</td>
											</tr>
	
											<nested:iterate property="singleStuFor_AllSubjects"
												indexId="count">
												<tr>
													<td width="23%" height="25" class="row-even"><nested:write
														property="subjectCode" /> - <nested:write
														property="subjectName" /></td>
													<td width="15%" class="row-even">
													<div align="right"><nested:notEqual
														property="isTheoryPractical" value="P">
														<nested:text property="theoryMarks" maxlength="3"/>
													</nested:notEqual></div>
													</td>
													<td width="14%" class="row-even">
													<div align="right"><nested:notEqual
														property="isTheoryPractical" value="T">
														<nested:text property="practicalMarks" maxlength="3" />
													</nested:notEqual></div>
													</td>
	
													<td width="16%" align="center" class="row-even"><nested:equal
														property="oldMarks" value="true">
												<a href="javascript:viewOldMarks('<nested:write
														property="subjectId" />', '<nested:write
														property="examMasterId" />')" >View
														Old Marks</a>
														
													</nested:equal></td>
	
													<td width="6%" align="center" class="row-even"><nested:checkbox
														property="mistake" disabled="true" /></td>
													<td width="6%" align="center" class="row-even"><nested:checkbox
														property="retest" disabled="true" /></td>
													<td width="20%" align="center" class="row-even"><nested:textarea
														property="comments"></nested:textarea></td>
													<nested:hidden property="id" />
													<nested:hidden property="examMasterId" />
													<nested:hidden property="subjectId" />
												</tr>
											</nested:iterate>
											<tr>
												<td height="25" class="row-white">&nbsp;</td>
												<td class="row-white">&nbsp;</td>
												<td class="row-white">&nbsp;</td>
												<td align="center" class="row-white">&nbsp;</td>
												<td align="center" class="row-white">&nbsp;</td>
												<td align="center" class="row-white">&nbsp;</td>
												<td align="center" class="row-white style1">&nbsp;</td>
											</tr>
										</table>
										</td>
										<td width="5" background="images/right.gif"></td>
									</tr>
									<tr>
										<td height="5"><img src="images/04.gif" width="5"
											height="5" /></td>
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
												<td width="23%" height="25" class="row-odd">
												<div align="right">Marks Card No. :</div>
												</td>
												<td width="77%" height="25" class="row-even"><nested:text
													property="marksCardNo" maxlength="10" /></td>
												<html:hidden property="studentId" styleId="studentId" />
												
												<nested:hidden property="markType" styleId="markType" />
	
											</tr>
										</table>
										</td>
										<td width="5" background="images/right.gif"></td>
									</tr>
									<tr>
										<td height="5"><img src="images/04.gif" width="5"
											height="5" /></td>
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
										<td width="45%" height="35">
										<div align="right"><input name="button2" type="submit"
											class="formbutton" value="Submit" /></div>
										</td>
										<td width="2%"></td>
										<td width="53%"><input type="button" class="formbutton"
											value="Cancel" onclick="goToFirstPage()" /></td>
									</tr>
								</table>
								</td>
								<td valign="top" background="images/Tright_3_3.gif"></td>
							</tr>
	
						</c:when>
	
					</c:choose>
				</nested:notEmpty>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
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

