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
	window.close();
}

</SCRIPT>

</head>


<html:form action="/ExamAssignmentOverallMarks.do">
	<html:hidden property="formName" value="ExamAssignmentOverallMarksForm" />
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Assignment/Overall marks &gt;&gt;</span></span>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Assignment/Overall
					marks</td>
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
								<logic:equal value="'T'" property="linkType"
									name="ExamAssignmentOverallMarksForm">

									<tr>
										<td width="28%" height="25" class="row-odd">
										<div align="right">Sub Internal:</div>
										</td>
										<td width="26%" height="25" class="row-even"><bean:write
											name="ExamAssignmentOverallMarksForm"
											property="subInternalTheory" /></td>
										<td width="30%" height="25" class="row-odd">
										<div align="right">Attendance:</div>
										</td>
										<td width="16%" height="25" class="row-even"><bean:write
											name="ExamAssignmentOverallMarksForm"
											property="attendanceTheory" /></td>



									</tr>
									<tr>
										<td height="25" class="row-odd">
										<div align="right"><span class="Mandatory"></span>Assignment
										:</div>
										</td>
										<td height="25" class="row-even" colspan="3"><bean:write
											name="ExamAssignmentOverallMarksForm"
											property="assignmentTheory" /></td>

									</tr>
								</logic:equal>

								<logic:equal value="'P'" property="linkType"
									name="ExamAssignmentOverallMarksForm">
									<tr>
										<td width="28%" height="25" class="row-odd">
										<div align="right">Sub Internal:</div>
										</td>
										<td width="26%" height="25" class="row-even"><bean:write
											name="ExamAssignmentOverallMarksForm"
											property="subInternalPractical" /></td>
										<td width="30%" height="25" class="row-odd">
										<div align="right">Attendance:</div>
										</td>
										<td width="16%" height="25" class="row-even"><bean:write
											name="ExamAssignmentOverallMarksForm"
											property="attendancePractical" /></td>



									</tr>
									<tr>
										<td height="25" class="row-odd">
										<div align="right"><span class="Mandatory"></span>Assignment
										:</div>
										</td>
										<td height="25" class="row-even" colspan="3"><bean:write
											name="ExamAssignmentOverallMarksForm"
											property="assignmentPractical" /></td>

									</tr>
								</logic:equal>
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
							<td width="49%" height="35" align="right">&nbsp;</td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								name="Submit8" type="button" class="formbutton" value="Cancel"
								onclick="fun()" /></td>
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
