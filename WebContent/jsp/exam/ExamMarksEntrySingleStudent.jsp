<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<script>
	function checkForEmpty(values) {
		cardDate = document.getElementById("marksCardDate").value;
		if (values.length != 0) {
			if (cardDate.length < 4) {
				var currentTime = new Date()
				var month = currentTime.getMonth() + 1
				var day = currentTime.getDate()
				var year = currentTime.getFullYear()
				document.getElementById("marksCardDate").value = day + "/"
						+ month + "/" + year;
			}
		}
	}
	function set(target) {
		document.getElementById("method").value = target;
	}
	function goToFirstPage() {
		document.location.href = "ExamMarksEntry.do?method=initExamMarksEntry";
	}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamMarksEntry.do">
	<html:hidden property="formName" value="ExamMarksEntryForm" />
	<html:hidden property="pageType" value="2" />

	<html:hidden property="courseId" styleId="courseId" />
	<html:hidden property="examNameId" styleId="examNameId" />
	<html:hidden property="schemeId" styleId="schemeId" />
	<html:hidden property="subjectId" styleId="subjectId" />
	<html:hidden property="subjectTypeId" styleId="subjectTypeId" />
	<nested:hidden property="evaluatorType" name="ExamMarksEntryForm" />
	<nested:hidden property="answerScriptType" name="ExamMarksEntryForm" />
<html:hidden property="examType" name="ExamMarksEntryForm"/>
	<c:choose>
		<c:when
			test="${examMarksEntryOperation != null && examMarksEntryOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="getStudentDetails" />
		</c:when>

	</c:choose>

	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam.exam" /> &gt;&gt; <bean:message
				key="knowledgepro.exam.ExamMarksEntry" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.ExamMarksEntry.student" /></td>
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
									<div align="right"><bean:message
										key="knowledgepro.exam.ExamMarksEntry.examCode" /> :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="ExamMarksEntryForm" property="examName" /></td>
									<td class="row-odd">&nbsp;</td>
									<td class="row-even">&nbsp;</td>
								</tr>
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.course" /> :</div>
									</td>
									<td width="23%" height="25" class="row-even"><bean:write
										name="ExamMarksEntryForm" property="course" /></td>
									<td width="28%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.scheme" /> :</div>
									</td>
									<td width="26%" class="row-even"><bean:write
										name="ExamMarksEntryForm" property="scheme" /></td>
								</tr>

								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.ExamMarksEntry.subject" /> :</div>
									</td>
									<td width="23%" height="25" class="row-even"></td>
									<td width="28%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.subjectsDefinition.subjectType" />:</div>
									</td>
									<td width="26%" class="row-even"></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.ExamMarksEntry.evaluatorType" /> :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="ExamMarksEntryForm" property="evaluatorType" /></td>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.ExamMarksEntry.answerScriptType" /> :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="ExamMarksEntryForm" property="answerScriptType" /></td>
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
									<td width="14%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.checkin.regno" /></div>
									</td>
									<td width="15%" height="25" class="row-even"><html:text
										property="regNo" styleId="regNo" maxlength="10"
										styleClass="TextBox" size="10" /></td>
									<td width="18%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.checkin.rollNo" /></div>
									</td>
									<td width="16%" class="row-even"><html:text
										property="rollNo" styleId="rollNo" maxlength="10"
										styleClass="TextBox" size="10" /></td>
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
							<td width="48%" height="35">
							<div align="right"><input name="button" type="submit"
								class="formbutton" value="Submit"
								onclick="set('getStudentDetails')" /></div>
							</td>
							<td width="3%" align="center">&nbsp;</td>
							<td width="49%"><input type="button" class="formbutton"
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

				<c:choose>
					<c:when test="${ExamMarksEntryForm.listStudentDetailsSize != 0 }">


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
									<td valign="top"><nested:hidden property="studentId" /> <nested:hidden
										property="id" />
									<table width="100%" cellspacing="1" cellpadding="0">
										<tr>
											<td class="heading"><bean:message
												key="knowledgepro.exam.ExamMarksEntry.name" />:<bean:write
												property="studentName" name="ExamMarksEntryForm" /></td>
											<td class="heading"><bean:message
												key="knowledgepro.exam.ExamMarksEntry.classCode" />: <bean:write
												property="classcode" name="ExamMarksEntryForm" /></td>
											<td class="heading">&nbsp;</td>
										</tr>
										<tr>
											<td height="25" class="row-odd"><bean:message
												key="knowledgepro.exam.ExamMarksEntry.subject" /></td>
											<td class="row-odd"><bean:message
												key="knowledgepro.exam.ExamMarksEntry.theoryMarks" /></td>
											<td class="row-odd"><bean:message
												key="knowledgepro.exam.ExamMarksEntry.practicalMarks" /></td>
										</tr>


										<nested:iterate property="examMarksEntryDetailsTO"
											indexId="count">
											<tr>
												<td width="40%" height="25" class="row-even"><nested:write
													property="subjectName" /></td>

												<td width="25%" class="row-even"><nested:notEqual
													property="isTheoryPractical" value="P">
													<nested:text property="theoryMarks" maxlength="6" />
												</nested:notEqual></td>
												<td width="25%" class="row-even"><nested:notEqual
													property="isTheoryPractical" value="T">
													<nested:text property="practicalMarks" maxlength="6" />
												</nested:notEqual></td>
												<nested:hidden property="id" />
												<nested:hidden property="examMasterId" />
												<nested:hidden property="subjectId" />
											</tr>
										</nested:iterate>

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
											<td width="9%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.exam.ExamMarksEntry.marksCardNo" /> :</div>
											</td>
											<td width="15%" height="25" class="row-even"><nested:text
												property="marksCardNo" maxlength="49"
												onblur="checkForEmpty(this.value)"></nested:text></td>
											<td width="14%" height="25" class="row-odd">
											<div align="right">Marks Card Date :</div>
											</td>
											<td width="9%" height="25" class="row-even"><html:text
												property="marksCardDate" styleId="marksCardDate" size="15" />
											<script language="JavaScript">
	new tcal( {
		// form name
		'formname' :'ExamMarksEntryForm',
		// input name
		'controlname' :'marksCardDate'

	});
</script></td>
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
							<td class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="43%" height="35">
									<div align="right"><input name="Submit" type="submit"
										class="formbutton" value="Submit" onclick="set('Submit')" /></div>
									</td>
									<td width="2%"></td>
									<td width="6%"><input type="button" class="formbutton"
										value="Cancel" onclick="goToFirstPage()" /></td>
									<td width="2%"></td>
									<td width="48%">&nbsp;</td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif"></td>
						</tr>


					</c:when>

				</c:choose>


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