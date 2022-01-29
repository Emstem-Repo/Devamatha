<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<link href="../css/styles.css" rel="stylesheet" type="text/css">


<script type="text/javascript">
	function goT0MainPage() {
		document.location.href = "SubjectDefinitionCertificateCourse.do?method=initSubDefCertificateCourseWise";
	}
	function isNumberKey(evt) {
		var charCode = (evt.which) ? evt.which : event.keyCode;
		if (charCode > 31 && ( (charCode < 48) || (charCode > 57)))
			return false;

		return true;

	}

	function cancelAction(){
		document.location.href = "SubjectDefinitionCertificateCourse.do?method=subDefCertificateCourseWiseCancel";
	}
</script>


<html:form action="/SubjectDefinitionCertificateCourse" method="POST">

	<html:hidden property="method" styleId="method"
		value="submitEdittedData" />
	<html:hidden property="formName" value="ExamSubjectDefCourseForm" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="subjectCode" styleId="subjectCode"
		name="ExamSubjectDefCourseForm" />
	<html:hidden property="subjectName" styleId="subjectName"
		name="ExamSubjectDefCourseForm" />
	<html:hidden property="subId" styleId="subId"
		name="ExamSubjectDefCourseForm" />
	<html:hidden property="courseId" styleId="courseId" />
	<html:hidden property="id" styleId="id" name="ExamSubjectDefCourseForm" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.subjectDefinitionCerTificateCourseWise" />
			&gt;&gt;</span></span></td>

		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.exam.subjectDefinitionCerTificateCourseWise" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
								<td width="28%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.subjectDefinitionCourseWise.SubjectSection" />:</div>
									</td>
									<td class="row-even" ><html:select
										property="subjectSection" styleId="subjectSection"
										styleClass="combo">

										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<logic:notEmpty name="ExamSubjectDefCourseForm"
											property="listSubjectSection">
											<html:optionsCollection property="listSubjectSection"
												name="ExamSubjectDefCourseForm" label="display" value="id" />
										</logic:notEmpty>

									</html:select></td>
								 <td width="25%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.subjectsDefinition.Uni.SubjectCode" />:</div>
									</td>
									<td width="27%" height="25" class="row-even"><html:text
										property="universitySubjectCode"
										styleId="universitySubjectCode" maxlength="30" /></td>
								</tr>
								<tr>
									<c:if
										test="${ExamSubjectDefCourseForm.isTheoryOrPractical != null && ExamSubjectDefCourseForm.isTheoryOrPractical != '' && ExamSubjectDefCourseForm.isTheoryOrPractical=='t'}">
										<td width="28%" class="row-odd">
									    <div align="right">Theory Hours :</div>
									    </td>
										<td height="25" class="row-even"><html:text
										
											property="theoryHours" size="3" styleId="theoryHours"
											maxlength="3" onkeypress="return isNumberKey(event)"></html:text>
										<bean:message
											key="knowledgepro.exam.subjectDefinitionCourseWise.credit" />
										<html:text property="theoryCredit" size="3"
											styleId="practHours" maxlength="3"
											onkeypress="return isNumberKey(event)"></html:text></td>

										
									</c:if>

                                    <c:if
										test="${ExamSubjectDefCourseForm.isTheoryOrPractical != null && ExamSubjectDefCourseForm.isTheoryOrPractical != '' && ExamSubjectDefCourseForm.isTheoryOrPractical=='p'}">
										      
     									<td width="25%" class="row-odd">
										<div align="right">Practical Hours :</div>
										</td>
										<td height="25" class="row-even"><html:text
											property="practHours" size="3" styleId="practHours"
											maxlength="3" onkeypress="return isNumberKey(event)" ></html:text>
										<bean:message
											key="knowledgepro.exam.subjectDefinitionCourseWise.credit" />
										<html:text property="practCredit" size="3"
											styleId="practHours" maxlength="3"
											onkeypress="return isNumberKey(event)"></html:text></td>
									</c:if>

                                   <c:if
                                        test="${ExamSubjectDefCourseForm.isTheoryOrPractical != null && ExamSubjectDefCourseForm.isTheoryOrPractical != '' && ExamSubjectDefCourseForm.isTheoryOrPractical=='b'}">
                                  				<td width="28%" class="row-odd">
									    <div align="right">Theory Hours :</div>
									    </td>
										<td height="25" class="row-even"><html:text
										property="theoryHours" size="3" styleId="theoryHours"
										maxlength="3" onkeypress="return isNumberKey(event)"></html:text>
									<bean:message
										key="knowledgepro.exam.subjectDefinitionCourseWise.credit" />
									<html:text property="theoryCredit" size="3"
										styleId="practHours" maxlength="3"
										onkeypress="return isNumberKey(event)"></html:text></td>

									<td width="25%" class="row-odd">
									<div align="right" >Practical Hours :</div>
									</td>
									<td height="25" class="row-even"><html:text
										property="practHours" size="3" styleId="practHours"
										maxlength="3" onkeypress="return isNumberKey(event)"></html:text>
									<bean:message
										key="knowledgepro.exam.subjectDefinitionCourseWise.credit" />
									<html:text property="practCredit" size="3" styleId="practHours"
										maxlength="3" onkeypress="return isNumberKey(event)"></html:text></td>
                                  
                                  </c:if>
									<td width="25%"  height="25" class="row-odd" ></td>
									<td width="25%" height="25" class="row-even" ></td>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>


				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td class="row-odd">Do not Show:</td>
									<td height="25" class="row-odd">&nbsp;</td>
								</tr>
								<tr>
									<td width="12%" class="row-even">Subject Type:</td>
									<td width="88%" height="25" class="row-even">
									<input type="hidden" name="st" id="subjectType1" value="<nested:write name='ExamSubjectDefCourseForm' property='dupsubjectType'/>" />
									<html:checkbox property="subjectType" styleId="subjectType"></html:checkbox>
									<script type="text/javascript">
										var studentId = document.getElementById("subjectType1").value;
										if(studentId == "on") {
											document.getElementById("subjectType").checked = true;
										}		
									</script>
									</td>
								</tr>
								<tr>
									<td class="row-white">Max Marks:</td>
									<td height="25" class="row-white">
									<input type="hidden" name="st" id="maxMarks1" value="<nested:write name='ExamSubjectDefCourseForm' property='dupmaxMarks'/>" />
									<html:checkbox property="maxMarks" styleId="maxMarks"></html:checkbox>
									<script type="text/javascript">
										var studentId = document.getElementById("maxMarks1").value;
										if(studentId == "on") {
											document.getElementById("maxMarks").checked = true;
										}		
									</script>
									</td>
								</tr>
								<tr>
									<td width="12%" class="row-even">Min Marks:</td>
									<td width="88%" height="25" class="row-even">
									<input type="hidden" name="st" id="minMarks1" value="<nested:write name='ExamSubjectDefCourseForm' property='dupminMarks'/>" />
									<html:checkbox property="minMarks" styleId="minMarks"></html:checkbox>
									<script type="text/javascript">
										var studentId = document.getElementById("minMarks1").value;
										if(studentId == "on") {
											document.getElementById("minMarks").checked = true;
										}		
									</script>
									
									</td>
								</tr>
								<tr>
									<td class="row-white">Attendance Marks:</td>
									<td height="25" class="row-white">
									<input type="hidden" name="st" id="attendanceMarks1" value="<nested:write name='ExamSubjectDefCourseForm' property='dupattendanceMarks'/>" />
									<html:checkbox property="attendanceMarks" styleId="attendanceMarks"></html:checkbox>
									<script type="text/javascript">
										var studentId = document.getElementById("attendanceMarks1").value;
										if(studentId == "on") {
											document.getElementById("attendanceMarks").checked = true;
										}		
									</script>
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
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
									<td width="28%" height="25" class="row-odd">
									<div align="right">Do not Add in Total marks and in Class
									declaration:</div>
									</td>
									<td width="4%" class="row-even">
									<input type="hidden" name="st" id="doNotAdd1" value="<nested:write name='ExamSubjectDefCourseForm' property='dupdoNotAdd'/>" />
									<html:checkbox property="doNotAdd" styleId="doNotAdd"></html:checkbox>
									<script type="text/javascript">
										var studentId = document.getElementById("doNotAdd1").value;
										if(studentId == "on") {
											document.getElementById("doNotAdd").checked = true;
										}		
									</script>
									
									</td>
									<td width="35%" class="row-odd">
									<div align="right">Do not consider failure in total
									result :</div>
									</td>
									<td width="3%" class="row-even">
									<input type="hidden" name="st" id="doNotConsider1" value="<nested:write name='ExamSubjectDefCourseForm' property='dupdoNotConsider'/>" />
									<html:checkbox property="doNotConsider" styleId="doNotConsider"></html:checkbox>
									<script type="text/javascript">
										var studentId = document.getElementById("doNotConsider1").value;
										if(studentId == "on") {
											document.getElementById("doNotConsider").checked = true;
										}		
									</script>
									
									</td>
								</tr>
								<tr>
									<td width="25%" class="row-odd">
									<div align="right">Show Internal &amp; Final Marks added
									together in marks card :</div>
									</td>
									<td width="5%" class="row-even">
									<input type="hidden" name="st" id="showInternalMarks1" value="<nested:write name='ExamSubjectDefCourseForm' property='dupshowInternalMarks'/>" />
									<html:checkbox
										property="showInternalMarks" styleId="showInternalMarks"></html:checkbox>
									<script type="text/javascript">
										var studentId = document.getElementById("showInternalMarks1").value;
										if(studentId == "on") {
											document.getElementById("showInternalMarks").checked = true;
										}		
									</script>
									</td>
									<td width="28%" class="row-odd">
									<div align="right">Show only Grade in Marks Card :</div>
									</td>
									<td height="25" class="row-even">
									<input type="hidden" name="st" id="showOnlyGrade1" value="<nested:write name='ExamSubjectDefCourseForm' property='dupshowOnlyGrade'/>" />
									<html:checkbox
										property="showOnlyGrade" styleId="showOnlyGrade"></html:checkbox>
									<script type="text/javascript">
										var studentId = document.getElementById("showOnlyGrade1").value;
										if(studentId == "on") {
											document.getElementById("showOnlyGrade").checked = true;
										}		
									</script>
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
						<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
				  </tr>
					<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr align="center">
					<td height="50" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading" colspan="7">
					<table>
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td align="center"><input name="button2" type="submit" class="formbutton" value="Submit" /></td>
							<td></td>
							<td align="center"><input type="button" class="formbutton" value="Cancel" onclick="cancelAction()" /></td>
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
</html:form>

