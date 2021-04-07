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
function resetAll()
{

document.location.href = "ExamTimeTable.do?method=update";
}
function cancelAll()
{

document.location.href = "ExamTimeTable.do?method=goToSecondPage&examNameId="+document.getElementById("examNameId").value+"&examTypeId="+document.getElementById("examTypeId").value;
}
</script>
<link rel="stylesheet" href="calendar.css">

<html:form action="/ExamTimeTable.do">
	<html:hidden property="formName" value="ExamTimeTableForm"
		styleId="formName" />
	<html:hidden property="pageType" value="3" styleId="pageType" />
	<html:hidden property="method" styleId="method" value="update" />
	<html:hidden property="examNameId" styleId="examNameId" />
	<html:hidden property="examTypeId" styleId="examTypeId" />
	<html:hidden property="id" />

	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Time Table &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Exam
					Time Table</td>
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
									<div align="right">Academic Year:</div>
									</td>
									<td width="23%" height="25" class="row-even"><bean:write property="examTimeTableTO.academicyear" name="ExamTimeTableForm" /></td>
									<td width="28%" class="row-odd"></td>
									<td width="26%" class="row-even"></td>
								</tr>
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right">Batch:</div>
									</td>
									<td width="23%" height="25" class="row-even"><bean:write
										property="examTimeTableTO.batch" name="ExamTimeTableForm" /></td>
									<td width="28%" class="row-odd">
									<div align="right">
									<DIV align="right">Program.:</DIV>
									</div>
									</td>
									<td width="26%" class="row-even"><bean:write
										property="examTimeTableTO.program" name="ExamTimeTableForm" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Course:</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="examTimeTableTO.course" name="ExamTimeTableForm" /></td>
									<td class="row-odd">
									<div align="right">Scheme :</div>
									</td>
									<td class="row-even"><bean:write
										property="examTimeTableTO.schemeNo" name="ExamTimeTableForm" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="examName" name="ExamTimeTableForm" /></td>
									<td height="25" class="row-odd">
									<div align="right">Exam Type :</div>
									</td>
									<td height="25" colspan="3" class="row-even"><bean:write
										property="examTimeTableTO.examType" name="ExamTimeTableForm" /><nested:hidden
										property="examId" name="ExamTimeTableForm" /></td>
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
									<td height="25" class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Subject Code</td>
									<td class="row-odd">Subject Name</td><!--
									<td class="row-odd">Date</td>
									<td class="row-odd">Start Time(in 24hrs format)</td>
									<td class="row-odd">End Time(in 24hrs format)</td>
									<td class="row-odd">Session</td>
								--></tr>

								<nested:iterate property="examTimeTableTO.listSubjects"
									indexId="count" id="list"
									type="com.kp.cms.to.exam.ExamSubjectTimeTableTO">
									<c:choose>
										<c:when test="${count%2==0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<%
										String styleDate1 = "datePick" + count;
														String styleSH = "sh" + count;
									%>

									<td height="25">
									<div align="center"><c:out value="${count+1}"></c:out><nested:hidden
										property="id" /></div>
									</td>
									<td><nested:write property="subjectCode" name="list" /></td>
									<td><nested:write property="subjectName" name="list" /></td>
									<!--<td><nested:text styleId='<%=styleDate1%>' property="date"
										size="10" maxlength="10" /> <script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'ExamTimeTableForm',
								// input name
								'controlname' :'<%=styleDate1%>'
									});
								</script></td>
									<td width="149"><nested:text property="startTimeHour"
										size="2" maxlength="2" onkeypress="return isNumberKey(event)"
										onblur="checkNumber(this)" /> : <nested:text
										property="startTimeMin" maxlength="2" size="2"
										onkeypress="return isNumberKey(event)"
										onblur="checkNumber(this)"
										onfocus="clearField(this)" /></td>
									<td width="149"><nested:text property="endTimeHour"
										maxlength="2" size="2" onkeypress="return isNumberKey(event)"
										onblur="checkNumber(this)"
										onfocus="clearField(this)" /> : <nested:text
										property="endTimeMin" maxlength="2" size="2"
										onkeypress="return isNumberKey(event)"
										onblur="checkNumber(this)"
										onfocus="clearField(this)" /></td>
									<td>
										<nested:select property="sessionId">
											<logic:notEmpty name="ExamTimeTableForm" property="sessionMap">
						                      <option value="">-Select-</option>
						                      <html:optionsCollection name="ExamTimeTableForm" property="sessionMap" label="value" value="key" />
						                     </logic:notEmpty>
										</nested:select>
									</td>-->
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
					<td colspan="3" class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="center">
							<input name="Submit2" type="submit" class="formbutton" value="Submit" />
							  &nbsp;&nbsp;&nbsp;
							<html:cancel styleClass="formbutton" onclick="resetAll()">Reset</html:cancel>
                              &nbsp;&nbsp;&nbsp;
							<input name="Submit2" type="button" class="formbutton" value="Cancel" onclick="cancelAll()"/>
							</div>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
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
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="/images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>