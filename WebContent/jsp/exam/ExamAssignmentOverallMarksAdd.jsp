<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<head>
<title>:: CMS ::</title>
<SCRIPT>
function fun()
{
	document.location.href="ExamAssignmentOverallMarks.do?method=initAssignmentOverallMarks";
}
function isValidNumber(field) {
	if (isNaN(field.value)) {
		field.value = "";
	}
}

var textid;



function getMaxPracticalMarcks(marcks,id){
	textid=id;
	var myArray = new Array();
	myArray = id.split("_");
	var stuId=myArray[0];
	stuId=stuId+myArray[1];
	var studentId=document.getElementById(stuId).value;
	
	 var courseId=document.getElementById("courseId").value;
	 var schemeNo=document.getElementById("schemeNo").value;
	 var subject=document.getElementById("subjectId").value;
	 var subjectType=document.getElementById("subjectType").value;
	var assignmentOverall= document.getElementById("assignmentOverall").value;
	var type=document.getElementById("type").value;
	 
	if (marcks != '' ) {
		var args = "method=getMaxTheoryMarcks&marksForReg=" + marcks+"&courseId="+courseId+"&schemeNo="+schemeNo+"&subjectId="+subject+"&subjectType="+subjectType+"&baseStudentId="+studentId+"&assignmentOverallType="+assignmentOverall+"&type="+type;
		var url = "AjaxRequest.do";
		requestOperation(url, args, updateClass);
	}
}

function getMaxTheoryMarcks(marcks,id){
	
	textid=id;
	var myArray = new Array();
	myArray = id.split("_");
	var stuId=myArray[0];
	stuId=stuId+myArray[1];
	var studentId=document.getElementById(stuId).value;
	
	 var courseId=document.getElementById("courseId").value;
	 var schemeNo=document.getElementById("schemeNo").value;
	 var subject=document.getElementById("subjectId").value;
	 var subjectType=document.getElementById("subjectType").value;
		var assignmentOverall= document.getElementById("assignmentOverall").value;
		var type=document.getElementById("type").value;
	 	if (marcks != '' ) {
	 
		var args = "method=getMaxTheoryMarcks&marksForReg=" + marcks+"&courseId="+courseId+"&schemeNo="+schemeNo+"&subjectId="+subject+"&subjectType="+subjectType+"&baseStudentId="+studentId+"&assignmentOverallType="+assignmentOverall+"&type="+type;
		var url = "AjaxRequest.do";
		requestOperation(url, args, updateClass);
	}
}

function updateClass(req) {
	getTheoryMarks(req, textid);
}
function submitForm(){
	document.ExamAssignmentOverallMarksForm.submit();	
}
function movenext(val,e,count) {
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
	var myArray = new Array();
	myArray = count.split("_");
	var tId=myArray[0];
	var noId=myArray[1];
	var jkl=parseInt(noId)+1;
	var mno=tId+"_"+jkl;
	//please check whether the control is found ....then move to next
	eval(document.getElementById(mno)).focus();
	//pqr.focus();
			return true;
		}
		
	}
</SCRIPT>

</head>


<html:form action="/ExamAssignmentOverallMarks.do">

	<html:hidden property="method" styleId="method"
		value="addStudentAssignmentOverallMarks" />
	<html:hidden property="formName" value="ExamAssignmentOverallMarksForm"
		styleId="formName" />
	<html:hidden property="pageType" value="2" styleId="pageType" />
	<html:hidden property="subjectTypeId" styleId="subjectTypeId" />

	<html:hidden property="courseId" styleId="courseId" />
	<html:hidden property="schemeNo" styleId="schemeNo" />
	<html:hidden property="subjectId" styleId="subjectId" />
	<html:hidden property="subjectType" styleId="subjectType" />
	<html:hidden property="assignmentOverall" styleId="assignmentOverall" />
	<html:hidden property="optionNo" styleId="type" />
	
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> &gt;&gt; <bean:message
				key="knowledgepro.exam.assignmentOverallMarks" /> &gt;&gt;</span></span>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.assignmentOverallMarks" /></td>
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

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.ExamName" /> :</div>
									</td>
									<td class="row-even" colspan="3"><bean:write
										name="ExamAssignmentOverallMarksForm" property="selectedExam" /></td>
								</tr>



								<tr>
									<td width="28%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.ExamInternalMark.course" />:</div>
									</td>
									<td width="26%" height="25" class="row-even"><bean:write
										name="ExamAssignmentOverallMarksForm" property="courseName" /></td>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.ExamInternalMark.Scheme" /> :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="ExamAssignmentOverallMarksForm" property="schemeId" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span><bean:message
										key="knowledgepro.exam.ExamInternalMark.subjectType" /> :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="ExamAssignmentOverallMarksForm" property="subject" /></td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span><bean:message
										key="knowledgepro.exam.ExamMarksEntry.subject1" />:</div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="ExamAssignmentOverallMarksForm" property="subjectType" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.footerAggreement.type" /> :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="ExamAssignmentOverallMarksForm" property="type" /></td>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.reJoin.joiningBatch" />:</div>
									</td>
									<td class="row-even"><bean:write
										name="ExamAssignmentOverallMarksForm" property="joiningBatch" /></td>
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
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
									<td height="25" align="center" class="row-odd"><bean:message
										key="knowledgepro.exam.slNo" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.blockUnblock.regNo" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.blockUnblock.rolNo" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.UpdateExcludeWithheld.studentName" /></td>


									<c:choose>
										<c:when
											test="${ExamAssignmentOverallMarksForm.subjectTypeId == '1'}">
											<td class="row-odd"><bean:message
												key="knowledgepro.admin.attendance.TMarks" /></td>
										</c:when>
										<c:when
											test="${ExamAssignmentOverallMarksForm.subjectTypeId == '0'}">
											<td class="row-odd"><bean:message
												key="knowledgepro.admin.attendance.PMarks" /></td>
										</c:when>
										<c:when
											test="${ExamAssignmentOverallMarksForm.subjectTypeId == '11'}">
											<td class="row-odd"><bean:message
												key="knowledgepro.admin.attendance.TMarks" /></td>
											<td class="row-odd"><bean:message
												key="knowledgepro.admin.attendance.PMarks" /></td>
										</c:when>
									</c:choose>
									</td>
								</tr>
								<nested:iterate property="listOfStudent" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td class="bodytext">
									<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td align="left" class="bodytext"><nested:write
										property="regNo" /></td>
									<td align="left" class="bodytext"><nested:write
										property="rollNo" /></td>
									<td align="left" class="bodytext"><nested:write
										property="studentName" /></td>

									<nested:equal property="isTheoryPractical" value="T">
										<td align="left" class="bodytext">
										<%
									  		String studentid="theoryMarks"+count;%> <nested:hidden
											property="studentId" styleId='<%=studentid %>' />
										<div align="center">
										<nested:text property="theoryMarks"
											styleId='<%="theoryMarks_" + count%>'
											onkeypress="return isNumberKey(event)" maxlength="3"
											onblur="return getMaxTheoryMarcks(this.value,this.id)"
											size="10"  onkeydown="movenext(this.name, event, this.id)" /><logic:equal
											name="ExamAssignmentOverallMarksForm" property="type"
											value="Internal Overall">

											<logic:equal name="ExamAssignmentOverallMarksForm"
												property="displayAssignOverall" value="Display">
												<a
													href="ExamAssignmentOverallMarks.do?method=viewInternalDetails&courseId=<nested:write property='courseId' />&schemeNo=<nested:write property='schemeNo' />&subject=<nested:write property='subjectId' />&subjectType=<nested:write property='isTheoryPractical' />&studentId=<nested:write property='studentId' />&isPreviousExam=<nested:write property='isPreviousExam' name="ExamAssignmentOverallMarksForm"/>&linkType='T'"
													target="_blank">View Theory Internal details</a>
											</logic:equal>
										</logic:equal></div>
										<p></p>
										</td>

									</nested:equal>

									<nested:equal property="isTheoryPractical" value="P">
										<td align="left" class="bodytext">
										<%
									 		String studentid="practicalMarks"+count;%> <nested:hidden
											property="studentId" styleId='<%=studentid %>' />
										<div align="center"><nested:text
											property="practicalMarks"
											styleId='<%="practicalMarks_" + count%>'
											onkeypress="return isNumberKey(event)"
											onblur="getMaxPracticalMarcks(this.value,this.id)"
											maxlength="3" size="10" onkeydown="movenext(this.name, event, this.id)"/><logic:equal
											name="ExamAssignmentOverallMarksForm" property="type"
											value="Internal Overall">

											<logic:equal name="ExamAssignmentOverallMarksForm"
												property="displayAssignOverall" value="Display">
												<a
													href="ExamAssignmentOverallMarks.do?method=viewInternalDetails&courseId=<nested:write property='courseId' />&schemeNo=<nested:write property='schemeNo' />&subject=<nested:write property='subjectId' />&subjectType=<nested:write property='isTheoryPractical' />&studentId=<nested:write property='studentId' />&isPreviousExam=<nested:write property='isPreviousExam' name="ExamAssignmentOverallMarksForm"/>&linkType='P'"
													target="_blank">View Practical Internal details</a>
											</logic:equal>
										</logic:equal></div>
										</td>
									</nested:equal>

									<nested:equal property="isTheoryPractical" value="B">
										<td align="left" class="bodytext">
										<%
									     String studentid="theoryMarks"+count;%> <nested:hidden
											property="studentId" styleId='<%=studentid %>' />
										<div align="center"><nested:text property="theoryMarks"
											styleId='<%="theoryMarks1_" + count%>'
											onkeypress="return isNumberKey(event)"
											onblur="getMaxTheoryMarcks(this.value,this.id)" maxlength="3"
											size="10" onkeydown="movenext(this.name, event, this.id)"/></div>

										<div align="center"><logic:equal
											name="ExamAssignmentOverallMarksForm" property="type"
											value="Internal Overall">

											<logic:equal name="ExamAssignmentOverallMarksForm"
												property="displayAssignOverall" value="Display">
												<a
													href="ExamAssignmentOverallMarks.do?method=viewInternalDetails&courseId=<nested:write property='courseId' />&schemeNo=<nested:write property='schemeNo' />&subject=<nested:write property='subjectId' />&subjectType=<nested:write property='isTheoryPractical' />&studentId=<nested:write property='studentId' />&isPreviousExam=<nested:write property='isPreviousExam' name="ExamAssignmentOverallMarksForm"/>&linkType='T'"
													target="_blank">View Theory Internal details</a>
											</logic:equal>
										</logic:equal></div>

										</td>
										<td align="left" class="bodytext">
										<%
									  studentid="practicalMarks"+count;%> <nested:hidden
											property="studentId" styleId='<%=studentid %>' />
										<div align="center"><nested:text
											property="practicalMarks"
											styleId='<%="practicalMarks1_" + count%>'
											onkeypress="return isNumberKey(event)"
											onblur="getMaxPracticalMarcks(this.value,this.id)"
											maxlength="3" size="10" onkeydown="movenext(this.name, event, this.id)"/></div>
											<div align="center"><logic:equal
											name="ExamAssignmentOverallMarksForm" property="type"
											value="Internal Overall">

											<logic:equal name="ExamAssignmentOverallMarksForm"
												property="displayAssignOverall" value="Display">
												<a
													href="ExamAssignmentOverallMarks.do?method=viewInternalDetails&courseId=<nested:write property='courseId' />&schemeNo=<nested:write property='schemeNo' />&subject=<nested:write property='subjectId' />&subjectType=<nested:write property='isTheoryPractical' />&studentId=<nested:write property='studentId' />&isPreviousExam=<nested:write property='isPreviousExam' name="ExamAssignmentOverallMarksForm"/>&linkType='P'"
													target="_blank">View Practical Internal details</a>
											</logic:equal>
										</logic:equal>
											</div>
										</td>
									</nested:equal>

									</tr>



								</nested:iterate>












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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="49%" height="35" align="right"><html:button property=""
								onmousedown="submitForm()"  styleClass="formbutton" 
								value="Submit" styleId="formbutton"></html:button></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input type="reset"
								class="formbutton" value="Cancel" onclick="fun();" /></td>
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
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>

</html:form>
