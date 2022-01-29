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
var latestId;
var chk=true;
var idq;
var dif=true;
function eToT(e, count){

subT=document.getElementById("subjectType_"+count).value;

if(e.keyCode==13){
	
	if(subT == "Theory"){
		 e.keyCode=9;
		 document.getElementById("theoryMarks_"+count).focus();


	}
	else{ 
		e.keyCode=9;
	 document.getElementById("practicalMarks_"+count).focus();
	}

}
	
}

function check(countId)
{
var t = new Array();
t = countId.split("_");
if(document.getElementById("registerNo_"+t[1]).value.length>0)
{
	return true;
}
else
{
	return false;
}

}
function resetValue() {
		document.location.href = "ExamSecuredMarksEntry.do?method=initExamSecuredMarksEntry";
	}
	var d1=0;
	function getMarksDifference(marks,element)	{
		var url = "AjaxRequest.do";
		d1=element;
		examId = document.getElementById("examId").value;
	subjectId = document.getElementById("subject").value;
	evaluatorId = null;
	if(document.getElementById("evaluatorId")!=null){
		evaluatorId=document.getElementById("evaluatorId").value;
		}
	answerScriptId = null;
	if(document.getElementById("answerScriptId")!=null){
		answerScriptId=document.getElementById("answerScriptId").value;
		}
	subjectType = document.getElementById("subjectType").value;
	registerNo=document.getElementById("registerNo_"+element).value;
		examType = document.getElementById("examType").value;
	if (registerNo.length != 0 && marks.length !=0) {
			var args = "method=getMarksDifference&appRegRollno=" + registerNo
					+ "&examName=" + examId + "&subjectId=" + subjectId
					+ "&evaluatorId=" + evaluatorId + "&answerScriptId="
					+ answerScriptId + "&subjectType=" + subjectType+"&marks="+marks+"&examType="+examType;
			requestOperationProgram(url, args, updateMarksDifference);
		}
		else
		{
			document.getElementById("marksErrorHidden_"+ d1).value = "";
			document.getElementById("marksError_" + d1).innerHTML="";
		}
	}
	function updateMarksDifference(req)
	{
		dif=false;
		updateMarks(req,d1);
	}
	var c = 0;
	function getStudentDetails(registerNo, id, type) {
		var url = "AjaxRequest.do";
		c = id;
		examId = document.getElementById("examId").value;
		subjectId = document.getElementById("subject").value;
		evaluatorId = null;
		if(document.getElementById("evaluatorId")!=null){
			evaluatorId=document.getElementById("evaluatorId").value;
			}
		answerScriptId = null;
		if(document.getElementById("answerScriptId")!=null){
			answerScriptId=document.getElementById("answerScriptId").value;
			}
		subjectType = document.getElementById("subjectType").value;
		isPreviousExam = document.getElementById("isPreviousExam").value;
		examType = document.getElementById("examType").value ;
		schemeNo = document.getElementById("schemeNo").value ;
		
		if (registerNo.length != 0) {
			var args = "method=getStudentDetails&appRegRollno=" + registerNo
					+ "&examName=" + examId + "&subjectId=" + subjectId
					+ "&evaluatorId=" + evaluatorId + "&answerScriptId="
					+ answerScriptId + "&subjectType=" + subjectType + "&isPreviousExam=" + isPreviousExam +"&examType="+examType + "&schemeNo=" + schemeNo;
			requestOperation(url, args, updateRegNo);
			if (subjectType == 'theory' || subjectType == 'Theory') {
				marks=document.getElementById("theoryMarks_" +id).value;

			} else if (subjectType == 'practical' || subjectType == 'practical') {
				marks=document.getElementById("practicalMarks_" + id).value;

			}
		if(marks.length!=0)
		{
			getMarksDifference(marks,id);
		}
			
		}
		else
		{
			document.getElementById("error_"+ id).innerHTML = "";
			document.getElementById("registerError_"+ id).value = "";
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
		subjectType = document.getElementById("subjectType").value;
		updateStudentDetails(req, c,subjectType);
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


		subT=document.getElementById("subjectType").value;
		
		
		if (keynum == 13) {
			var abc=count;
			var ghi=abc.substring(11);
			var jkl=ghi;
			if(subT == "Theory"){
				var mno="theoryMarks_"+jkl;
				
			}
			else
			{
				var mno="practicalMarks_"+jkl;
				
			}
			latestId=mno;
			idq=jkl;
			chk=false;
			
		//	document.getElementById("registerNo_"+ghi).setAttribute("autocomplete","off");
			eval(document.getElementById(mno)).focus();
			return false;
		}else{
		
			var abc=count;
			var ghi=abc.substring(11);
			var jkl=ghi;
			if(subT == "Theory"){
				var mno="theoryMarks_"+jkl;
				
			}
			else
			{
				var mno="practicalMarks_"+jkl;
				
			}
			latestId=mno;
			idq=jkl;
			chk=true;
			
			return false;
		}
	
		}

	function movenextMark(val, e, count) {
		
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
			var ghi=abc.substring(12);
			var jkl=parseInt(ghi)+1;
			var mno="registerNo_"+jkl;
			if(mno == "registerNo_10"){
				if(dif){
					getMarksDifference(document.getElementById(latestId).value,idq);
				}
				document.getElementById('formbutton').focus();
			}else{
				eval(document.getElementById(mno)).focus();
			}
			chk=false;
			idq=ghi;
			return false;
		}else{
			var abc=count;
			var ghi=abc.substring(12);
			chk=true;
			idq=ghi;
			return false;
		}

	
	}
	function movenextPractical(val, e, count) {
		
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
			var ghi=abc.substring(15);
			var jkl=parseInt(ghi)+1;
			var mno="registerNo_"+jkl;
			
			if(mno == "registerNo_10"){
				if(dif){
					getMarksDifference(document.getElementById(latestId).value,idq);
				}
				document.getElementById('formbutton').focus();
			}else{
				eval(document.getElementById(mno)).focus();
			}
			chk=false;
			return false;
		}else{
			var abc=count;
			var ghi=abc.substring(15);
			chk=true;
			idq=ghi;
			return false;
		}
			
	}

	


	function submitForm(){
		var size = parseInt(document.getElementById("registerNoCount").value);
		var pos = -1;
		for ( var count = 0; count <= size - 1; count++) {
			var regNo = document.getElementById("registerNo_" + count).value;
			if(regNo == null || trim(regNo) == ""){
				pos = count;
				break;
			}			
		}		
		if(pos < 0){
			//getMarksDifference(document.getElementById(latestId).value,idq);
			document.ExamSecuredMarksEntryForm.submit();	
		}else{
			submitConfirm = confirm("You have not entered all 10 numbers. Are you sure the entry is correct");
			if (submitConfirm) {
				document.ExamSecuredMarksEntryForm.submit();
			}
			else{
				document.getElementById("registerNo_"+pos).focus();
			}
		}
	}
</SCRIPT>
<html:form action="/ExamSecuredMarksEntry.do" styleId="secureForm" focus="registerNo_0">
	<html:hidden property="formName" value="ExamSecuredMarksEntryForm"
		styleId="formName" />
	<html:hidden property="method" styleId="method" value="onSubmit" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="subjectCode" />
	<html:hidden property="subject" styleId="subject" />
	<html:hidden property="examId" styleId="examId" />
	<html:hidden property="subjectType" styleId="subjectType" />
	<html:hidden property="subjectName" />
	<html:hidden property="examName" />
	<html:hidden property="answerScriptType" styleId="answerScriptId" />
	<html:hidden property="examType" styleId="examType" />
	<html:hidden property="isPreviousExam" styleId="isPreviousExam" />
	<html:hidden property="schemeNo" styleId="schemeNo" />

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
					<td class="heading">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div align="center"><FONT color="red" size="3"> <bean:write name="ExamSecuredMarksEntryForm" property="regCount"/></FONT></div>
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
										<td class="row-even" width="23%" height="25">&nbsp;
										<c:if test="${ExamSecuredMarksEntryForm.evaluatorType!= null && ExamSecuredMarksEntryForm.evaluatorType!= ''}">
											<html:select
											property="evaluatorType" styleClass="combo"
											styleId="evaluatorId" name="ExamSecuredMarksEntryForm"
											style="width:120px">
											<html:option value="">
												<bean:message key="knowledgepro.select" />
												<logic:notEmpty name="ExamSecuredMarksEntryForm"
													property="listEvaluatorType">
													<html:optionsCollection property="listEvaluatorType"
														name="ExamSecuredMarksEntryForm" label="value" value="key" />
												</logic:notEmpty>
											</html:option>
										</html:select>
									</c:if>
									</td>
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
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr>
									<td height="25" class="row-odd">Sl No.</td>
									<logic:equal value="true" property="regNoOrRollNumber"
										name="ExamSecuredMarksEntryForm">
										<td height="25" class="row-odd">Register No.</td>
									</logic:equal>
									<logic:equal value="false" property="regNoOrRollNumber"
										name="ExamSecuredMarksEntryForm">
										<td height="25" class="row-odd">Roll No.</td>
									</logic:equal>
									<logic:equal value="Theory" name="ExamSecuredMarksEntryForm"
										property="subjectType">
										<td class="row-odd">Theory Marks</td>
									</logic:equal>
									<logic:equal value="Practical" name="ExamSecuredMarksEntryForm"
										property="subjectType">
										<td class="row-odd">Practical Marks</td>
									</logic:equal>
									<logic:notEmpty property="evaluatorType"
										name="ExamSecuredMarksEntryForm">
										<td class="row-odd">Previous Evaluator Marks</td>
									</logic:notEmpty>
									<td class="row-odd">Mistake</td>
									<td class="row-odd">Re test</td>
								</tr>
								<nested:hidden property="subjectType"
									name="ExamSecuredMarksEntryForm" styleId="subjectType" />
								<% int registerNoCount = 0; %>
								<nested:iterate property="listSingleStudents" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
										<td width="10%" height="25" align="center"><%=count+1%> </td>
										<td width="27%" height="25" >
										<%
											String s3 = "getStudentDetails(this.value, "
																+ count.toString() + ",'password')";
										String s11="return eToT(event, " + count.toString() + ")"; 
										String registerStyleId="registerNo_"+count;
									
										%> <nested:password property="registerNo" size="15"
											styleId='<%=registerStyleId%>'
											onkeydown="movenext(this.name, event, this.id)"
											onblur='<%=s3%>'></nested:password><FONT color="red"><nested:write
											property="registerNoError" /> <strong
											id='<%="error_" + count%>'></strong></FONT></td>
										<nested:hidden property="registerNoError"
											styleId='<%="registerError_" + count%>' />
										<%
											String s4 = "getMarksDifference(this.value, "
																+ count.toString() + ")";
										%>


										<td width="18%" ><logic:equal
											value="Theory" property="subjectType"
											name="ExamSecuredMarksEntryForm">

											<nested:text property="theoryMarks"
												styleId='<%="theoryMarks_" + count%>' size="15" maxlength="4"
												onkeydown="movenextMark(this.name, event, this.id)"
												onblur='<%=s4%>' onkeypress=" return check(this.id)"  />

											<FONT color="red"><nested:write property="marksError" />
											<strong id='<%="marksError_" + count%>'></strong></FONT>
											<nested:hidden property="marksError"
												styleId='<%="marksErrorHidden_" + count%>' />
										</logic:equal><logic:equal value="Practical" property="subjectType"
											name="ExamSecuredMarksEntryForm">

											<nested:text property="practicalMarks"
												styleId='<%="practicalMarks_" + count%>' size="15" maxlength="4"
												onkeydown="movenextPractical(this.name, event, this.id)"
												onblur='<%=s4%>' onkeypress=" return check(this.id)" />
											<FONT color="red"><nested:write property="marksError" />
											<strong id='<%="marksError_" + count%>'></strong></FONT>
											<nested:hidden property="marksError"
												styleId='<%="marksErrorHidden_" + count%>' />
										</logic:equal></td>

										<nested:hidden property="marksEntryId"
											styleId='<%="marksEntryId_" + count%>' />


										<nested:hidden property="detailId"
											styleId='<%="detailId_" + count%>' />
										<logic:notEmpty property="evaluatorType"
											name="ExamSecuredMarksEntryForm">
											<td width="5%" align="center" ><font
												color="red" id='<%="evaluatorMarks_" + count%>'></font></td>
										</logic:notEmpty>
										<nested:hidden property="previousEvaluatorMarks"
											styleId='<%="evaluatorMarks_" + count%>' />
										<td width="12%" align="center" >
										<div align="center"><nested:checkbox
											styleId='<%="mistake_" + count%>' property="mistake"
											disabled="true" /></div>
										<nested:hidden styleId='<%="mistakeHidden_" + count%>'
											property="mistake" /></td>
										<td width="11%" align="center" >
										<div align="center"><nested:checkbox
											styleId='<%="retest_" + count%>' property="retest"
											disabled="true" /><nested:hidden
											styleId='<%="retestHidden_" + count%>' property="retest" /></div>



										</td>
									</tr>
									<%registerNoCount = registerNoCount + 1; %>
								</nested:iterate>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
							<td>
							 <input
								type="hidden" name="registerNoCount" id="registerNoCount"
								value='<%=registerNoCount%>' />
							</td>
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
							<html:button property=""
								onmousedown="submitForm()"  styleClass="formbutton" value="Submit" styleId="formbutton"></html:button>
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
