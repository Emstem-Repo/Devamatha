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
nextfield = "box1"; // name of first box on page
netscape = "";
ver = navigator.appVersion; len = ver.length;
for(iln = 0; iln < len; iln++) if (ver.charAt(iln) == "(") break;
netscape = (ver.charAt(iln+1).toUpperCase() != "C");

function keyDown(DnEvents) { // handles keypress
k = (netscape) ? DnEvents.which : window.event.keyCode;
if (k == 13) {
if (nextfield == 'box20')
	{
	return true;
	} 
else {
	return false;
      }
   }
}
document.onkeydown = keyDown; 
if (netscape) document.captureEvents(Event.KEYDOWN|Event.KEYUP);




	function resetValue() {
		document.location.href = "ExamSecuredMarksVerification.do?method=initExamSecuredMarksVerification";

		
	}
var c=0;
	function getDecryptRegNo(registerNo,id,type) {
		var url = "AjaxRequest.do";
		c=id;
		examId=document.getElementById("examId").value;
		subjectId=document.getElementById("subject").value;
		evaluatorId=document.getElementById("evaluatorType").value;
		answerScriptId=document.getElementById("answerScriptType").value;
		if (registerNo.length != 0) {
			var args = "method=getDecryptRegNo&appRegRollno=" + registerNo+"&examName="+examId+"&subjectId="+subjectId+"&evaluatorId="+evaluatorId+"&answerScriptId="+answerScriptId+"&type="+type;
					requestOperation(url, args, updateRegNo);
		}
		
	}
	function updateRegNo(req) {
		updateDecryptRegNo(req, c);
	}
	function donotEnter() {
		return true;
		//if (document.getElementById("boxCheck").value == "true") {
		//	return false;
		//} else {
		//		return true;
		//}
	}
	function getMarksByRegNo() {
		var registerNo = document.getElementById("registerNo").value;

		if (registerNo != '') {
			var args = "method=getMarksByRegNo&marksForReg=" + registerNo;
			var url = "AjaxRequest.do";
			requestOperation(url, args, updateClass);
		}
	}
	function updateClass(req) {
		updateMarksByRegNO(req, "registerNo");
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
		if (keynum == 13) {
			var abc=count;
			var ghi=abc.substring(6);
			var jkl=parseInt(ghi)+1;
			var mno="regNo_"+jkl;
			if(mno == "regNo_10"){
				document.getElementById('formbutton').focus();
			}
			else
			{	
				eval(document.getElementById(mno)).focus();
			}	
			return false;
		}
			
	}
	function submitForm(){
		alert("Successfully Verified.");
		document.ExamSecuredMarksVerificationForm.submit();
	}

	function resetStudentList()
	{
		for(var i=0;i<10;i++)
		{
			document.getElementById("regNo_"+i).value="";
			document.getElementById("theoryMarks_"+i).value="";
			document.getElementById("practicalMarks_"+i).value="";
			document.getElementById("display_"+i).innerHTML="";
		}
		document.getElementById("error").innerHTML="";		
	}	
</SCRIPT>
<html:form action="/ExamSecuredMarksVerification.do" focus="regNo_0">
	<html:hidden property="formName"
		value="ExamSecuredMarksVerificationForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="onSubmit" />

	<html:hidden property="pageType" value="2" styleId="pageType" />

	<html:hidden property="subjectCode" />
	<html:hidden property="subject" styleId="subject" />
	<html:hidden property="examId" styleId="examId" />
	<html:hidden property="subjectType" />
	<html:hidden property="subjectName" />
	<html:hidden property="examName" />
	<html:hidden property="evaluatorTypeId" styleId="evaluatorId" />
	<html:hidden property="answerScriptTypeId" styleId="answerScriptId" />
	<html:hidden property="boxCheck" styleId="boxcheck" />
	<html:hidden property="checkBox" styleId="checkBox" />

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
										name="ExamSecuredMarksVerificationForm" property="subjectCode" /></td>


									<td width="28%" height="25" class="row-odd">
									<div align="right">Subject Name :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksVerificationForm" property="subjectName" /></td>
								</tr>
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right">Subject Type :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksVerificationForm" property="subjectType" /></td>

									<td width="28%" height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>
									<td class="row-even" width="23%" height="25"><bean:write
										name="ExamSecuredMarksVerificationForm" property="examName" /></td>
								</tr>		
									<tr>
										<td height="25" class="row-odd">
										<div align="right">Evaluator Type :</div>
										</td>
										<td class="row-even" width="23%" height="25">
											<logic:notEmpty name="ExamSecuredMarksVerificationForm" property="listEvaluatorType">
												<html:select property="evaluatorType" styleClass="combo" styleId="evaluatorType" name="ExamSecuredMarksVerificationForm" style="width:120px" onchange="resetStudentList();">
													<html:optionsCollection name="ExamSecuredMarksVerificationForm" property="listEvaluatorType" label="value" value="key" />
												</html:select>
											</logic:notEmpty>
											<logic:empty name="ExamSecuredMarksVerificationForm" property="listEvaluatorType">
												<html:hidden property="evaluatorType" styleId="evaluatorType" value=""/>
											</logic:empty>
										</td>
										<td height="25" class="row-odd">
										<div align="right">Answer Script Type :</div>
										</td>
										<td class="row-even" width="23%" height="25">
										<logic:notEmpty name="ExamSecuredMarksVerificationForm" property="listAnswerScriptType">
											<html:select  property="answerScriptType" styleClass="combo" styleId="answerScriptType" name="ExamSecuredMarksVerificationForm" style="width:120px" onchange="resetStudentList();">
												<html:optionsCollection property="listAnswerScriptType" name="ExamSecuredMarksVerificationForm" label="value" value="key" />
											</html:select>	
										</logic:notEmpty>
										<logic:empty name="ExamSecuredMarksVerificationForm" property="listAnswerScriptType">
												<html:hidden property="answerScriptType" styleId="answerScriptType" value=""/>
										</logic:empty>
										</td>
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
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr>
									<td height="25" class="row-odd">Sl No.</td>
									<logic:equal value="true" property="regNoOrRollNumber"
										name="ExamSecuredMarksVerificationForm">
										<td height="25" class="row-odd">Register No.</td>
									</logic:equal>
									<logic:equal value="false" property="regNoOrRollNumber"
										name="ExamSecuredMarksVerificationForm">
										<td height="25" class="row-odd">Roll No.</td>
									</logic:equal>
									<td class="row-odd">Theory Marks</td>
									<td class="row-odd">Practical Marks</td>
									<td class="row-odd">Mistake</td>
									<td class="row-odd">Re test</td>
								</tr>
								<nested:iterate property="listSingleStudents" indexId="count">
									<tr>
									<td width="5%" height="25" class="row-even" align="center">
									<%=count + 1%>
									</td>
									<%String id = "regNo_"+count; %>
										<c:if
											test="${ExamSecuredMarksVerificationForm.boxCheck == true}">
											<td width="27%" height="25" class="row-even">
											<%
											String s3 = "getDecryptRegNo(this.value, "+ count.toString() + ",'password')";
											%> <nested:password property="regNo" onblur='<%=s3%>' onfocus='<%="nextfield ='box"+(count+1)+"';"%>'
												size="15" onkeypress="return donotEnter()" onkeydown="movenext(this.name, event, this.id)" styleId='<%=id %>' /><strong
												id='<%="display_" + count%>'></strong></td>
										</c:if>
										<c:if
											test="${ExamSecuredMarksVerificationForm.boxCheck ==false}">
											<td width="27%" height="25" class="row-even">
											<%
												String s3 = "getDecryptRegNo(this.value, "
																		+ count.toString() + ",'text')";
											%> <nested:text property="regNo" size="15" onblur='<%=s3%>' onfocus='<%="nextfield ='box"+(count+1)+"';"%>'styleId='<%=id %>' onkeydown="movenext(this.name, event, this.id)" />
											<strong id='<%="display_" + count%>'></strong></td>
										</c:if>


										<td width="18%" class="row-even"><STRONG><nested:text
											property="theoryMarks" styleId='<%="theoryMarks_" + count%>'
											size="15" disabled="true" /></STRONG></td>

										<td width="18%" class="row-even"><nested:text
											property="practicalMarks"
											styleId='<%="practicalMarks_" + count%>' size="15"
											disabled="true" /></td>
										<nested:hidden property="theoryMarks"
											styleId='<%="theoryMarksHidden_" + count%>' />
										<nested:hidden property="practicalMarks"
											styleId='<%="practicalMarksHidden_" + count%>' />
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



										</td>
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
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35">
							<div align="right">
							<html:button property="" onclick="submitForm()" styleClass="formbutton" styleId="formbutton" value="Submit"></html:button>
							</div>
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
<script>
hook=false;
</script>
