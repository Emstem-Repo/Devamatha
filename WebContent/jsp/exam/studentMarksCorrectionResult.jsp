<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/freeFoodJqueryConfirm.css" />
  <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>

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
		document.location.href = "studentMarksCorrection.do?method=initStudentMarksCorrection";
	}

	function viewOldMarks(subjectId, studentId,count) {
		var url="studentMarksCorrection.do?method=viewOldMarks&studentId="+studentId+"&subjectId="+subjectId+"&count="+count;
		window.open(url,'Student Marks Correction');
	}
	function clearTheData(){
		document.getElementById("method").value = "getStudentDetails";
		document.newStudentMarksCorrectionForm.submit();
	}			
</script>

<html:form action="/studentMarksCorrection" method="POST" enctype="multipart/form-data" focus="regNo">

	<html:hidden property="formName" value="newStudentMarksCorrectionForm" styleId="formName" />
	<html:hidden property="pageType" value="2" styleId="pageType" />
	<html:hidden property="method" styleId="method"	value="getStudentDetails" />
	<input type="hidden"  id="verifyGracing" name="verifyGracing" value="<bean:write name="newStudentMarksCorrectionForm" property="verifyGracing"/>"/>
	<input type="hidden"  id="subjectname" name="subjectname" value="<bean:write name="newStudentMarksCorrectionForm" property="subjectname"/>"/>
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
									<td height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="newStudentMarksCorrectionForm" property="examName" /></td>
									<td width="28%" class="row-odd">
									<div align="right">Scheme :</div>
									</td>
									<td width="26%" class="row-even">
									<bean:write name="newStudentMarksCorrectionForm"
										property="schemeNo" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Final/Internal Exam Type :</div>
									</td>
									<td height="25" colspan="3" class="row-even"><bean:write
										name="newStudentMarksCorrectionForm" property="examType" /></td>
										
								</tr>
								<c:if test="${newStudentMarksCorrectionForm.markType != 'Internal overAll' && newStudentMarksCorrectionForm.markType != 'Regular overAll' }">
									<tr>
									<td height="25" class="row-odd">
										<div align="right" id="ast">
										Answer Script Type :</div>
										</td>
										<td height="25" class="row-even"><html:select
											property="answerScript" styleClass="comboLarge"
											styleId="answerScriptType"
											name="newStudentMarksCorrectionForm" style="width:200px" onchange="clearTheData()">
											<html:option value="">
												<bean:message key="knowledgepro.admin.select" />
											</html:option>
											<logic:notEmpty name="newStudentMarksCorrectionForm" property="answerScriptType">
												<html:optionsCollection name="newStudentMarksCorrectionForm" property="answerScriptType" label="value" value="key" />
											</logic:notEmpty>
										</html:select></td>
												<td height="25" class="row-odd">
												<div align="right" id="et">Evaluator
												Type :</div>
												</td>
												<td height="25" class="row-even"><html:select
													property="evaluatorType" styleClass="combo"
													styleId="evaluatorType" name="newStudentMarksCorrectionForm"
													style="width:200px" onchange="clearTheData()">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<logic:notEmpty name="newStudentMarksCorrectionForm" property="evaluatorMap">
															<html:optionsCollection name="newStudentMarksCorrectionForm" property="evaluatorMap" label="value" value="key" />
													</logic:notEmpty>
												</html:select></td>
									</tr>
								</c:if>
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
										styleClass="TextBox" size="10" /></td>
									<td width="20%" class="row-odd">
									<div align="right">Student Name :</div>
									</td>
									<td width="13%" class="row-even"><bean:write
										property="studentName" name="newStudentMarksCorrectionForm" /></td>
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><input  type="submit"
								class="formbutton" value="Search" onclick="set('getStudentDetails')" /></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><input type="button" class="formbutton"
								value="Cancel" onclick="goToFirstPage()" /></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<nested:notEmpty property="marksList">
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
											<td class="row-odd">Gracing</td>
											<td class="row-odd">Comments</td>
										</tr>

										<nested:iterate property="marksList" indexId="count" id="list">
											<tr>
												<td width="23%" height="25" class="row-even"><nested:write
													property="subjectCode" /> - <nested:write
													property="subjectName" /></td>
												<td width="15%" class="row-even">
												<div align="right"><nested:equal
													property="isTheory" value="true">
													<nested:text property="theoryMarks" maxlength="10"/>
												</nested:equal></div>
												</td>
												<td width="14%" class="row-even">
												<div align="right"><nested:equal property="isPractical" value="true">
													<nested:text property="practicalMarks" maxlength="10"/>
												</nested:equal></div>
												</td>
												<c:choose>
												<c:when test="${newStudentMarksCorrectionForm.markType != 'Internal overAll'}">
													<td width="16%" align="center" class="row-even"><nested:equal name="list"
														property="isOldMarks" value="true">
	
														<u
															onclick="viewOldMarks('<nested:write
														property="subjectId" />', '<nested:write
														property="studentId" />','1')" style="CURSOR:hand">View
														Old Marks</u>
													</nested:equal></td>
												</c:when>
												<c:when test="${newStudentMarksCorrectionForm.markType != 'Regular overAll' }">
													<td width="16%" align="center" class="row-even"><nested:equal name="list"
														property="isOldMarks" value="true">
	
														<u
															onclick="viewOldMarks('<nested:write
														property="subjectId" />', '<nested:write
														property="studentId" />','2')" style="CURSOR:hand">View
														Old Marks</u>
													</nested:equal></td>
												</c:when>
												<c:otherwise>
													<td width="16%" align="center" class="row-even"><nested:equal name="list"
															property="isOldMarks" value="true">
		
															<u
																onclick="viewOldMarks('<nested:write
															property="subjectId" />', '<nested:write
															property="studentId" />','3')" style="CURSOR:hand">View
															Old Marks</u>
														</nested:equal></td>
												</c:otherwise>
											</c:choose>
												<td width="6%" align="center" class="row-even"><nested:checkbox
													property="mistake"/></td>
												<td width="6%" align="center" class="row-even"><nested:checkbox
													property="retest"/></td>
												<td width="6%" align="center" class="row-even"><nested:checkbox
												property="gracing"  /></td>
												<td width="20%" align="center" class="row-even"><nested:textarea
													property="comments"></nested:textarea></td>
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
											<td width="77%" height="25" class="row-even">   
												<nested:text name="newStudentMarksCorrectionForm" property="marksCardNo" maxlength="10" />
											</td>
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
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
								<tr>
									<td width="45%" height="35">
									<div align="right"><input name="button2" type="submit"
										class="formbutton" value="Submit" onclick="set('saveChangedMarks')"/></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><input type="button" class="formbutton"
										value="Cancel" onclick="goToFirstPage()" /></td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif"></td>
						</tr>
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
<script type="text/javascript">
var isVerifyGracing = document.getElementById("verifyGracing").value;
var subjectname= document.getElementById("subjectname").value;
if(isVerifyGracing=='true'){
	$.confirm({
		'message'	: 'Marks Has been Altered for this '+subjectname+'  gracing is not selected.Are you sure that entered marks is not for gracing',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					$.confirm.hide();
					document.getElementById("method").value="saveChangedMarksAfterVerifiedGracing";
					document.newStudentMarksCorrectionForm.submit();
				}
			},
      'Cancel'	:  {
				'class'	: 'gray',
				'action': function(){
					$.confirm.hide();
					
				}
			}
		}
	});




	
}
</script>