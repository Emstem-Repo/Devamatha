<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function isValidNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
	function cancelPage()
	{
		document.location.href = "ExamRevaluationApplication.do?method=initRevaluationApplication";	
	}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamRevaluationApplication.do">
	<html:hidden property="formName" value="ExamRevaluationApplicationForm" />
	<html:hidden property="pageType" value="3" />

	<html:hidden property="method" styleId="method"
		value="updateRevaluationApplication" />
	<html:hidden property="examNameId_value" styleId="examNameId_value" />
	<html:hidden property="courseId" styleId="courseId" />
	<html:hidden property="schemeNo" styleId="schemeNo" />
	<html:hidden name="ExamRevaluationApplicationForm" property="regNo" />
	<html:hidden name="ExamRevaluationApplicationForm" property="rollNo" />
	<html:hidden property="studentId" styleId="studentId" />
	<html:hidden property="id" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.revaluationApplication" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td height="294">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.revaluationApplication" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
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

									<td width="28%" height="28" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.examName" /> :</div>
									</td>
									<td class="row-even" colspan="3"><bean:write
										name="ExamRevaluationApplicationForm" property="examName" /></td>
								</tr>
								<tr>
									<td class="row-odd" height="28">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.studentName" />:</div>
									</td>
									<td width="26%" class="row-even"><bean:write
										name="ExamRevaluationApplicationForm" property="studentName" />
									</td>
									<td width="24%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.regNo" />:</div>
									</td>
									<td width="22%" class="row-even"><bean:write
										name="ExamRevaluationApplicationForm" property="regNo" /></td>

								</tr>
								<tr>
									<td class="row-odd" height="28">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.course" />:</div>
									</td>
									<td class="row-even"><bean:write
										name="ExamRevaluationApplicationForm" property="courseName" /></td>
									<td class="row-odd" height="28">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.scheme" />:</div>
									</td>
									<td class="row-even"><bean:write
										name="ExamRevaluationApplicationForm" property="schemeName" /></td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.revaluationApplication.revaluationType" />:</div>
									</td>
									<td class="row-even"><html:select
										property="revaluationType" styleClass="combo"
										styleId="revaluationType"
										name="ExamRevaluationApplicationForm" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamRevaluationApplicationForm"
											property="listRevaluationType">
											<html:optionsCollection property="listRevaluationType"
												name="ExamRevaluationApplicationForm" label="display"
												value="id" />
										</logic:notEmpty>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.revaluationApplication.date" />:</div>
									</td>
									<td height="25" class="row-even">
									<table width="82" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="60"><html:text property="revaluationDate"
												styleId="revaluationDate" maxlength="10"
												styleClass="TextBox" size="10" /></td>
											<td width="40"><script language="JavaScript">
	new tcal( {
		// form name
		'formname' : 'ExamRevaluationApplicationForm',
		// input name
		'controlname' : 'revaluationDate'
	});
</script></td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>


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
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									<td width="8%" class="row-odd">&nbsp;</td>
									<td width="47%" class="row-odd">&nbsp;</td>
									<td width="45%" height="25" class="row-odd">&nbsp;</td>
								</tr>


								<nested:iterate property="listSubject" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>


									<td>
									<%
										String s1 = "hidden1_" + count;
												String s2 = "check1_" + count;
									%>

									<div align="center"><nested:hidden property="dummyValue"
										styleId='<%=s1%>' /> <nested:checkbox property="value"
										styleId='<%=s2%>' /> <script>
	var v = document.getElementById("hidden1_<c:out value='${count}'/>").value
	if (v == "true") {
		document.getElementById("check1_<c:out value='${count}'/>").checked = true;
	}
</script></div>
									</td>
									<td>
									<div><nested:write property="subjectCode" /></div>
									</td>
									<td>
									<div><nested:write property="subjectName" /></div>
									</td>

									</tr>
								</nested:iterate>
								<tr>
									<td width="8%" class="row-odd">&nbsp;</td>
									<td width="47%" class="row-odd">&nbsp;</td>
									<td width="45%" height="25" class="row-odd"><span
										class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.revaluationApplication.amount" />: <html:text
										property="revaluationAmount" styleId="revaluationAmount"
										maxlength="8" styleClass="TextBox" size="15"
										onblur="isValidNumber(this)" /></td>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><input name="button2" type="Submit"
								class="formbutton" value="Submit" /></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><input type="button" class="formbutton"
								value="Cancel" onclick="cancelPage()" /></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
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
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>