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
		document.location.href = "ExamStudentMarksCorrection.do?method=initExamStudentMarksCorrection";
	}

	function viewOldMarks(studentId, examMasterId) {
		examNameId=document.getElementById("examNameId").value;
		subjectId=document.getElementById("subjectId").value;
		markType=document.getElementById("markType").value;
		courseId=document.getElementById("courseId").value;
		scheme=document.getElementById("scheme").value;
		var url="ExamStudentMarksCorrection.do?method=viewOldMarksOverall&examNameId="+examNameId+"&studentId="+studentId+"&subjectId="+subjectId+"&markType="+markType+"&courseId="+courseId+"&scheme="+scheme + "&examMasterId=" + examMasterId;
		window.open(url,'Student Marks Correction');
	
	
	}
</script>

<title>:: CMS ::</title>

<html:form action="/ExamStudentMarksCorrection.do" method="POST"
	enctype="multipart/form-data">

	<html:hidden property="method" styleId="method"
		value="addOrUpdateAllStudentMarks" />
	<html:hidden property="formName" value="ExamStudentMarksCorrectionForm"
		styleId="formName" />
	<html:hidden property="pageType" value="3" styleId="pageType" />
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Marks Entry&gt;&gt;</span></span>
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
									<td width="23%" height="25" class="row-odd">
									<div align="right">Course :</div>
									</td>
									<td width="23%" height="25" class="row-even">
									<html:hidden
										property="courseId" styleId="courseId"
										name="ExamStudentMarksCorrectionForm" />
									<bean:write
										property="course" name="ExamStudentMarksCorrectionForm" /></td>
									<td width="28%" class="row-odd">
									<div align="right">Scheme :</div>
									</td>
									<td width="26%" class="row-even">
									<html:hidden
										property="scheme" styleId="scheme"
										name="ExamStudentMarksCorrectionForm" />
									<bean:write
										property="scheme" name="ExamStudentMarksCorrectionForm" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Subject:</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="subject" name="ExamStudentMarksCorrectionForm" /></td>
									<td height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>
									<td height="25" class="row-even"><html:hidden
										property="examNameId" styleId="examNameId"
										name="ExamStudentMarksCorrectionForm" /> <bean:write
										property="examName" name="ExamStudentMarksCorrectionForm" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Final/Internal Exam Type :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="examType" name="ExamStudentMarksCorrectionForm" /></td>
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
							<table width="100%" cellspacing="1" cellpadding="1">
								<tr>
									<td height="25" class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Roll No.</td>
									<td height="25" class="row-odd">Register No.</td>
									<td class="row-odd">Student Name</td>
									<td class="row-odd">Theory</td>
									<td class="row-odd">Practical</td>
									<td class="row-odd">&nbsp;</td>
									<td class="row-odd">Retest</td>
									<td class="row-odd">Mistake</td>
									<td class="row-odd">Comments</td>
								</tr>
								<nested:iterate property="originalSingleSubjFor_AllStudents" />
								<nested:iterate property="singleSubjFor_AllStudents"
									indexId="count">
									<tr>
										<td width="4%" height="25" class="row-even">
										<div align="center"><c:out value="${count+1}"></c:out></div>
										</td>
										<nested:hidden property="marksEntryId" />
										
										<td width="9%" class="row-even"><nested:write
											property="rolNo" /></td>
										<td width="9%" height="25" class="row-even"><nested:write
											property="regNo" /></td>
										<td width="24%" class="row-even"><nested:write
											property="studentName" /></td>
										<td width="7%" class="row-even"><span class="row-white">
										<nested:notEqual property="isTheoryPractical" value="P">
											<nested:text property="theoryMarks" maxlength="3"
												onkeypress="return isNumberKey(event)" />
										</nested:notEqual></span></td>
										<td width="9%" class="row-even"><span class="row-white"><nested:notEqual
											property="isTheoryPractical" value="T">
											<nested:text property="practicalMarks" maxlength="3"
												onkeypress="return isNumberKey(event)" />
										</nested:notEqual> </span></td>

										<td width="16%" align="center" class="row-even"><nested:equal
											property="oldMarks" value="true">

											<u
												onclick="viewOldMarks('<nested:write
													property="studentId" />', '<nested:write
													property="marksEntryId" />')" style="CURSOR:hand">View
											Old Marks</u>
										</nested:equal></td>

										<td width="5%" align="center" class="row-even"><nested:checkbox
											property="retest" disabled="true" /></td>
										<td width="5%" align="center" class="row-even"><nested:checkbox
											property="mistake" disabled="true" /></td>
										<td width="16%" align="center" class="row-even"><nested:textarea
											property="comments" cols="10" rows="2" /></td>
										<html:hidden property="studentId" styleId="studentId" />

									</tr>
								</nested:iterate>
								<html:hidden property="subjectId" styleId="subjectId" />
								
								<nested:hidden property="markType" styleId="markType" />
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
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="29"><img src="images/Tright_03_05.gif" width="9"
						height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>


