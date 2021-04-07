<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>



<SCRIPT>
	function resetValue() {
		document.location.href = "ExamSecuredMarksEntry.do?method=initExamSecuredMarksEntry";

		
	}
var c=0;
	function getDecryptRegNo(registerNo,id,type) {
		var url = "AjaxRequest.do";
		c=id;
		examId=document.getElementById("examId").value;
		subjectId=document.getElementById("subject").value;
		evaluatorId=document.getElementById("evaluatorId").value;
		answerScriptId=document.getElementById("answerScriptId").value;
		subjectType=document.getElementById("subjectType").value;
		if (registerNo.length != 0) {
			var args = "method=getDecryptRegNoForSecuredEntry&appRegRollno=" + registerNo+"&examName="+examId+"&subjectId="+subjectId+"&evaluatorId="+evaluatorId+"&answerScriptId="+answerScriptId+"&type="+type+"&subjectType="+subjectType;
			requestOperation(url, args, updateRegNo);
		}
		else
		{
			document.getElementById("display_"+ id).innerHTML = "";
			if(subjectType=="Theory")
			{
			document.getElementById("theoryMarks_" + id).disabled = false;
			}
			else
			{
				document.getElementById("practicalMarks_" + id).disabled = false;
			}
			
		}
		
		
	}
	function updateRegNo(req) {
		subjectType=document.getElementById("subjectType").value;
		evaluatorType=document.getElementById("evaluatorId").value;
		updateDecryptRegNoForSecuredEntry(req, c,subjectType,evaluatorType);
	}
	function donotEnter() {

		
			return true;
		
	}
	
</SCRIPT>
<html:form action="/ExamSecuredMarksEntry.do">
	<html:hidden property="formName" value="ExamSecuredMarksEntryForm"
		styleId="formName" />
	<html:hidden property="method" styleId="method" value="viewUpdate" />

	<html:hidden property="pageType" value="2" styleId="pageType" />

	<html:hidden property="subjectCode" />
	<html:hidden property="subject" styleId="subject" />
	<html:hidden property="examId" styleId="examId" />
	<html:hidden property="subjectType" styleId="subjectType" />
	<html:hidden property="subjectName" />
	<html:hidden property="examName" />
	<html:hidden property="evaluatorType" styleId="evaluatorId" />
	<html:hidden property="answerScriptType" styleId="answerScriptId" />
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
					Marks Entry - Single Student Single Subject</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>

					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"><FONT color="red">
					<div id="error"></div>
					</FONT>
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
									<td width="23%" height="25" class="row-odd">
									<div align="right">Subject Code :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksEntryForm" property="subjectCode" /></td>


									<td width="28%" height="25" class="row-odd">
									<div align="right">Subject Name :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksEntryForm" property="subjectName" /></td>
								</tr>
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right">Subject Type :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksEntryForm" property="subjectType" /></td>

									<td width="28%" height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksEntryForm" property="examName" /></td>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Evaluator Type :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksEntryForm" property="evaluatorType" /></td>
									<td height="25" class="row-odd">
									<div align="right">Answer Script Type :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksEntryForm" property="answerScriptType" /></td>
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
									<logic:equal value="true" property="regNoOrRollNumber"
										name="ExamSecuredMarksEntryForm">
										<td height="25" class="row-odd">Register No.</td>
									</logic:equal>
									<logic:equal value="false" property="regNoOrRollNumber"
										name="ExamSecuredMarksEntryForm">
										<td height="25" class="row-odd">Roll No.</td>
									</logic:equal>
									<logic:equal value="Theory" property="subjectType"
										name="ExamSecuredMarksEntryForm">
										<td class="row-odd">Theory Marks</td>
									</logic:equal>
									<logic:equal value="Practical" property="subjectType"
										name="ExamSecuredMarksEntryForm">
										<td class="row-odd">Practical Marks</td>
									</logic:equal>
									<logic:notEmpty property="evaluatorType"
										name="ExamSecuredMarksEntryForm">
										<td class="row-odd">Previous Evaluator Marks</td>
									</logic:notEmpty>
									<td class="row-odd">Mistake</td>
									<td class="row-odd">Re test</td>
								</tr>
								<logic:notEmpty property="listSingleStudentsView"
									name="ExamSecuredMarksEntryForm">
									<nested:iterate property="listSingleStudentsView"
										indexId="count">
										<tr>


											<td width="27%" height="25" class="row-even">
											<%
												String s3 = "getDecryptRegNo(this.value, "
																		+ count.toString() + ",'text')";
											%> <nested:password property="registerNo" size="15"
												onblur='<%=s3%>' onkeypress="return donotEnter()" /> <strong
												id='<%="display_" + count%>'></strong></td>




											<logic:equal value="Theory" property="subjectType"
												name="ExamSecuredMarksEntryForm">
												<td width="18%" class="row-even"><nested:text
													property="theoryMarks"
													styleId='<%="theoryMarks_" + count%>' size="15"
													disabled="true" readonly="true"/></td>
												<nested:hidden property="theoryMarks"
													styleId='<%="theoryMarksHidden_" + count%>' />
											</logic:equal>
											<logic:equal value="Practical" property="subjectType"
												name="ExamSecuredMarksEntryForm">
												<td width="18%" class="row-even"><nested:text
													property="practicalMarks"
													styleId='<%="practicalMarks_" + count%>' size="15"
													disabled="true" readonly="true"/></td>
												<nested:hidden property="practicalMarks"
													styleId='<%="practicalMarksHidden_" + count%>' />
											</logic:equal>
											<logic:notEmpty property="evaluatorType"
												name="ExamSecuredMarksEntryForm">
												<td width="5%" align="center" class="row-even style1"><font
													color="red" id='<%="evaluatorMarks_" + count%>'></font></td>
											</logic:notEmpty>
											<nested:hidden property="marksEntryId"
												styleId='<%="marksEntryId_" + count%>' />
											<nested:hidden property="detailId"
												styleId='<%="detailId_" + count%>' />
											<td width="12%" align="center" class="row-even">
											<div align="center"><nested:checkbox
												styleId='<%="mistake_" + count%>' property="mistake" /></div>
											<nested:hidden styleId='<%="mistakeHidden_" + count%>'
												property="mistake" /></td>
											<td width="11%" align="center" class="row-even">
											<div align="center"><nested:checkbox
												styleId='<%="retest_" + count%>' property="retest"
												disabled="true" /></div>
											<nested:hidden styleId='<%="retestHidden_" + count%>'
												property="retest" /></td>
										</tr>
									</nested:iterate>
								</logic:notEmpty>
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
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35">
							<div align="right"><html:submit property="submit"
								styleClass="formbutton" /></div>
							</td>
							<td width="1%"></td>
							<td width="7%"><input type="button" class="formbutton"
								value="Cancel" onclick="resetValue()" /></td>
							<td width="1%"></td>
							<td width="52%">&nbsp;</td>

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
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
