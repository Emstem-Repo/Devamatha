<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script language="JavaScript">

function searchStudent(){
	document.getElementById("method").value="searchStudentList";
    }
function cancel(){
	document.location.href = "PhdDocumentSubmission.do?method=initPhdDocumentSubmission";
	}
function editSaveDocumentSubmission() {
	    document.getElementById("method").value="editSaveDocumentSubmission";
	    document.documentSubmissionScheduleForm.submit();
                            }
function getCurrentDate(count) {
	var currentDate = new Date();
	var day = currentDate.getDate();
	var month = currentDate.getMonth() + 1 ;
	var year = currentDate.getFullYear() ;
    var date=(day + "/" + month + "/" + year);
	var check=document.getElementById("datePick"+count).value;
	if(check!=null && check!=""){
	   document.getElementById("datePick"+count).value=null;
	}else{
		document.getElementById("datePick"+count).value=date;
	}
}
</script>
<html:form action="/PhdDocumentSubmission" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="documentSubmissionScheduleForm" />
	<html:hidden property="pageType" value="2" />
	<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
	<link href="../css/styles.css" rel="stylesheet" type="text/css">
	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.phd" /><span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.phd.documentsubmission" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td colspan="2" background="images/Tcenter.gif"
						class="heading_white"><bean:message
						key="knowledgepro.phd.documentsubmission" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" class="news">
					<div align="right"><FONT color="red"><span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" colspan="2" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
									<td height="25" class="row-odd" width="25%">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.allocation.regno" /></div>
									</td>
									<td width="32%" height="25" class="row-even"><label></label>
									<span class="star"> <c:choose>
										<c:when
											test="${documentSubmissionScheduleForm.studentName == null}">
											<html:text property="registerNo" styleId="registerNo"
												size="20" maxlength="50" />
										</c:when>
										<c:otherwise>
											<html:text property="registerNo" styleId="registerNo"
												size="20" maxlength="50" disabled="true" />
										</c:otherwise>
									</c:choose> </span></td>
								</tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
						</tr>
						<logic:notEmpty property="studentName"
							name="documentSubmissionScheduleForm">
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
									<tr>
										<td height="25" class="row-odd" width="25%">
										<div align="right"><bean:message
											key="knowledgepro.fee.studentname" /></div>
										</td>
										<td width="32%" height="25" class="row-even"><label></label>
										<span class="star"> <bean:write
											name="documentSubmissionScheduleForm" property="studentName" />
										</span></td>
									</tr>
									<tr>
										<td height="25" class="row-odd" width="25%">
										<div align="right"><bean:message
											key="knowledgepro.admission.courseName" /></div>
										</td>
										<td width="32%" height="25" class="row-even"><label></label>
										<span class="star"> <bean:write
											name="documentSubmissionScheduleForm" property="courseName" />
										</span></td>
									</tr>
								</table>
								</td>
								<td width="5" height="29" background="images/right.gif"></td>
							</tr>
						</logic:notEmpty>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" colspan="2" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">

						<tr>
							<td width="45%" height="35">
							<div align="center"><html:submit property=""
								styleClass="formbutton" onclick="searchStudent()">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" colspan="2" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<logic:notEmpty name="documentSubmissionScheduleForm"
							property="studentDetailsList">
							<tr>
								<td height="45" colspan="4">
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
												<div align="center"><bean:message
													key="knowledgepro.slno" /></div>
												</td>
												<td height="25" class="row-odd" align="center"><bean:message
													key="knowledgepro.phd.document.name" /></td>
												<td height="25" class="row-odd" align="center"><bean:message
													key="knowledgepro.phd.Submission.Schedule.assigndate" /></td>
												<td height="25" class="row-odd" align="center"><bean:message
													key="knowledgepro.phd.isreminder.mailrequired" /></td>
												<td height="25" class="row-odd" align="center"><bean:message
													key="knowledgepro.phd.guide.feerequired" /></td>
												<td height="25" class="row-odd" align="center"><bean:message
													key="knowledgepro.phd.can.submit.online" /></td>
												<td height="25" class="row-odd" align="center"><bean:message
													key="knowledgepro.admin.ducuments.submitted" /></td>
												<td height="25" class="row-odd" align="center">
												<div align="center"><bean:message
													key="knowledgepro.phd.Guide.submitted_date" /></div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
											<nested:iterate id="CME"
												name="documentSubmissionScheduleForm"
												property="studentDetailsList" indexId="count">
												<%
													String styleDate1 = "datePick" + count;
												%>
												<tr>
													<td width="6%" height="20" class="row-even">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td width="15%" height="20" class="row-even" align="center"><bean:write
														name="CME" property="documentName" /></td>
													<td width="10%" height="20" class="row-even" align="center"><bean:write
														name="CME" property="assignDate" /></td>
													<td width="15%" height="20" class="row-even" align="center"><bean:write
														name="CME" property="isReminderMail" /></td>
													<td width="10%" height="20" class="row-even" align="center"><bean:write
														name="CME" property="guidesFee" /></td>
													<td width="20%" height="20" class="row-even" align="center"><bean:write
														name="CME" property="canSubmitOnline" /></td>
													<td width="10%" height="20" class="row-even" align="center">
													<input type="hidden"
														name="studentDetailsList[<c:out value='${count}'/>].tempChecked"
														id="hidden_<c:out value='${count}'/>"
														value="<nested:write name='CME' property='tempChecked'/>" />

													<input type="checkbox"
														name="studentDetailsList[<c:out value='${count}'/>].checked"
														id="<c:out value='${count}'/>"
														onchange="getCurrentDate('<c:out value='${count}'/>')" />

													<script type="text/javascript">
						                              var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
						                               if(studentId == "on") {
					                                 	document.getElementById("<c:out value='${count}'/>").checked = true;
						}		
						</script></td>
													<td height="20" class="row-even">
													<div align="center"><nested:text
														styleId='<%=styleDate1%>' property="submittedDate"
														size="10" maxlength="10" /> <script language="JavaScript">
							 new tcal( {
								// form name
								'formname' :'documentSubmissionScheduleForm',
								// input name
								'controlname' :'<%=styleDate1%>'
							});
						</script></div>
													</td>
												</tr>
											</nested:iterate>
										</table>
										</td>
										<td width="5" height="30" background="images/right.gif"></td>
									</tr>
									<tr>
										<td height="5"><img src="images/04.gif" width="5"
											height="5" /></td>
										<td background="images/05.gif"></td>
										<td><img src="images/06.gif" /></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td width="45%" height="35">
								<div align="center"><html:submit property=""
									styleClass="formbutton" onclick="editSaveDocumentSubmission()">
									<bean:message key="knowledgepro.submit" />
								</html:submit>&nbsp;&nbsp;&nbsp; <html:button property=""
									styleClass="formbutton" onclick="cancel()">
									<bean:message key="knowledgepro.cancel" />
								</html:button></div>
								</td>
							</tr>
						</logic:notEmpty>
					</table>
					</td>
					<td width="10" valign="top" colspan="2"
						background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td colspan="2" background="images/TcenterD.gif"></td>

					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>
