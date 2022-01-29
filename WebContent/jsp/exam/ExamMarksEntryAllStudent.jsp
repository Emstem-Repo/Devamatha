<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script>
	function goToFirstPage() {
		var val=document.getElementById("showExamType").value;
		if(val=="ShowAll")
			document.location.href = "ExamMarksEntry.do?method=initExamMarksEntry";
		else
		if(val=="ShowRegularAndSupply")
			document.location.href = "ExamMarksEntry.do?method=initExamMarksEntryForRegular";
		else	
		if(val=="ShowInternal")
			document.location.href = "ExamMarksEntry.do?method=initExamMarksEntryForInternal";	
			
	}
	function movenext(val, e, count) {
		var keynum;
		var keychar;
		var numcheck;

		if (window.event) // IE
		{
			keynum = e.keyCode;
		} else if (e.which) // Netscape/Firefox/Opera
		{
			keynum = e.which;
		}
		if (keynum == 40 ) {
				



		var abc=count;

		var ghi=abc.substring(5);

		var jkl=parseInt(ghi)+1;
		var mno="test_"+jkl;
		
		//please check whether the control is found ....then move to next
		eval(document.getElementById(mno)).focus();
		//pqr.focus();
				return true;
			}
			
		}


	
</script>

<html:form action="/ExamMarksEntry.do" method="POST"
	enctype="multipart/form-data">
	<html:hidden property="formName" value="ExamMarksEntryForm"
		styleId="formName" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" styleId="method"
		value="addAllStudentMarks" />
	<html:hidden property="examNameId" name="ExamMarksEntryForm" />
	<html:hidden property="schemeId" name="ExamMarksEntryForm" />
	<html:hidden property="subjectTypeId" name="ExamMarksEntryForm" />
	<html:hidden property="evaluatorType" name="ExamMarksEntryForm" />
	<html:hidden property="answerScriptType" name="ExamMarksEntryForm" />
	<html:hidden property="courseId" name="ExamMarksEntryForm" />
	<html:hidden property="examType" name="ExamMarksEntryForm"/>
	<html:hidden property="showExamType" styleId="showExamType" name="ExamMarksEntryForm"/>
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			>> Exam Marks Entry&gt;&gt;</span></span></td>
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
									<td height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>

									<td height="25" class="row-even" colspan="3"><bean:write
										property="examName" name="ExamMarksEntryForm"></bean:write></td>
								</tr>
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right">Course :</div>
									</td>
									<td width="23%" height="25" class="row-even"><bean:write
										property="course" name="ExamMarksEntryForm"></bean:write></td>
									<td width="28%" class="row-odd">
									<div align="right">Scheme :</div>
									</td>
									<td width="26%" class="row-even"><bean:write
										property="scheme" name="ExamMarksEntryForm"></bean:write></td>

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Subject Name:</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="subject" name="ExamMarksEntryForm"></bean:write></td>
									<td height="25" class="row-odd">
									<div align="right">Subject Type :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="subjectType" name="ExamMarksEntryForm"></bean:write></td>



								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Evaluator Type :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="evaluatorType" name="ExamMarksEntryForm"></bean:write></td>
									<td height="25" class="row-odd">
									<div align="right">Answer Script Type :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="answerScriptType" name="ExamMarksEntryForm"></bean:write></td>
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
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr>

									<td class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Student Name</td>
									<td class="row-odd">Roll No.</td>
									<td height="25" class="row-odd">Register No.</td>
									<logic:notEqual value="Practical" property="subjectType" name="ExamMarksEntryForm">
									<td class="row-odd">Theory Marks</td>
									</logic:notEqual>
									<logic:notEqual value="Theory" property="subjectType" name="ExamMarksEntryForm">
										<td class="row-odd">Practical Marks</td>
									</logic:notEqual>
								
									

								</tr>
								<nested:iterate property="examMarksEntryStudentTO" id="examMarksEntryStudentTO"
									indexId="count">
									<tr>
										<td width="7%" class="row-even">
										<div align="center"><c:out value="${count+1}"></c:out></div>
										</td>
										<td width="20%" class="row-even"><nested:write
											property="studentName" /></td>
										
										<td width="15%" class="row-even"><nested:write
											property="rolNo" /></td>
										<td width="18%" class="row-even"><nested:write
											property="regNo" /></td>
									<logic:notEqual value="Practical" property="subjectType" name="ExamMarksEntryForm">
									<td width="18%" class="row-even">
									<%String id="test_"+count; %>
									<logic:equal value="ShowRegularAndSupply" property="showExamType" name="ExamMarksEntryForm">
										<logic:equal value="false" property="isTheoryMarksFromDb" name="examMarksEntryStudentTO">
											<nested:text property="theoryMarks" maxlength="6" styleId= '<%=id %>' onkeydown="movenext(this.name, event, this.id)"/>
										</logic:equal>
										<logic:equal value="true" property="isTheoryMarksFromDb" name="examMarksEntryStudentTO">
											<nested:text property="theoryMarks" maxlength="6" styleId= '<%=id %>' disabled="true"/>
										</logic:equal>
									</logic:equal>
									<logic:equal value="ShowInternal" property="showExamType" name="ExamMarksEntryForm">
										<logic:equal value="false" property="isTheoryMarksFromDb" name="examMarksEntryStudentTO">
											<nested:text property="theoryMarks" maxlength="6" styleId= '<%=id %>' onkeydown="movenext(this.name, event, this.id)"/>
										</logic:equal>
										<logic:equal value="true" property="isTheoryMarksFromDb" name="examMarksEntryStudentTO">
											<nested:text property="theoryMarks" maxlength="6" styleId= '<%=id %>' disabled="true"/>
										</logic:equal>
									</logic:equal>
									<logic:equal value="ShowAll" property="showExamType" name="ExamMarksEntryForm">
										<nested:text property="theoryMarks" maxlength="6" styleId= '<%=id %>' onkeydown="movenext(this.name, event, this.id)"/>
									</logic:equal>
									</td>
									</logic:notEqual>
									<logic:notEqual value="Theory" property="subjectType" name="ExamMarksEntryForm">
									<td width="18%" class="row-even">
									<%String id="test_"+count; %>
										<logic:equal value="ShowRegularAndSupply" property="showExamType" name="ExamMarksEntryForm">
											<logic:equal value="false" property="isPracticalMarksFromDb" name="examMarksEntryStudentTO">
												<nested:text property="practicalMarks" maxlength="6" styleId= '<%=id %>' onkeydown="movenext(this.name, event, this.id)"/>
											</logic:equal>
											<logic:equal value="false" property="isPracticalMarksFromDb" name="examMarksEntryStudentTO">
												<nested:text property="practicalMarks" maxlength="6" styleId= '<%=id %>' disabled="true"/>
											</logic:equal>
										</logic:equal>
										<logic:equal value="ShowInternal" property="showExamType" name="ExamMarksEntryForm">
											<logic:equal value="false" property="isPracticalMarksFromDb" name="examMarksEntryStudentTO">
												<nested:text property="practicalMarks" maxlength="6" styleId= '<%=id %>' onkeydown="movenext(this.name, event, this.id)"/>
											</logic:equal>
											<logic:equal value="false" property="isPracticalMarksFromDb" name="examMarksEntryStudentTO">
												<nested:text property="practicalMarks" maxlength="6" styleId= '<%=id %>' disabled="true"/>
											</logic:equal>
										</logic:equal>
										<logic:equal value="ShowAll" property="showExamType" name="ExamMarksEntryForm">
											<nested:text property="practicalMarks" maxlength="6" styleId= '<%=id %>' onkeydown="movenext(this.name, event, this.id)"/>
										</logic:equal>
									
									</td></logic:notEqual>	
										
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
