<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript"> 
function getDateTime(examId){
	//getExamDateTime("examMap", examId, "examNameId", update);
	var args = "method=getExamDateTimeByExamId&examName=" + examId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, update);
}
function update(req)
{
	updateExamDateTime(req, "date");
}
function getExamsByExamTypeAndYear() {
	var examType=document.getElementById("examType").value;
	var year=document.getElementById("year").value;
	getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
}
function updateExamName(req) {
	updateOptionsFromMap(req, "examName", "- Select -");
	updateCurrentExam(req, "examName");
}
</script>

<html:form action="AssignExaminer" method="post">
	<html:hidden property="method" styleId="method" value="assignExaminer" />
	<html:hidden property="formName" value="ExamAssignExaminerForm" />
	<html:hidden property="pageType" value="1" />
	<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
	<link href="../css/styles.css" rel="stylesheet" type="text/css">
	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.exam" /><span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.exam.assignExaminer" /> &gt;&gt;</span></span></td>

		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td colspan="2" background="images/Tcenter.gif"
						class="heading_white"><bean:message
						key="knowledgepro.exam.assignExaminer" /></td>

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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>

					<td height="42" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
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
							<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
							<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="ExamAssignExaminerForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
							<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examType" /> :</div></td>
								<td height="25" class="row-even"><html:select
										property="examType" styleClass="combo"  styleId="examType" onchange="getExamsByExamTypeAndYear()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="examTypeList"
											name="ExamAssignExaminerForm">
											<html:optionsCollection property="examTypeList"
												name="ExamAssignExaminerForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
							</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examName" /> :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="examName" styleClass="combo" styleId="examName" onchange="getDateTime(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamAssignExaminerForm" property="examNameMap">
										<html:optionsCollection name="ExamAssignExaminerForm" property="examNameMap" label="value"
													value="key" /></logic:notEmpty>
									</html:select></td>

									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.assignExaminer.date" /> :</div>
									</td>
									<td class="row-even"><html:text
										name="ExamAssignExaminerForm" property="date" styleId="date"
										size="10" maxlength="16" /> <script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'ExamAssignExaminerForm',
								// input name
								'controlname' :'date'
							});
						</script></td>

									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.assignExaminer.time" /> :</div>
									</td>
									<td class="row-even"><html:text
										name="ExamAssignExaminerForm" property="hr"
										styleClass="Timings" styleId="hr" size="2"
										maxlength="2" onfocus="clearField(this)"
										onblur="checkForEmpty(this)"
										onkeypress="return isNumberKey(event)" /> : <html:text
										name="ExamAssignExaminerForm" property="min"
										styleClass="Timings" styleId="min" size="2"
										maxlength="2" onfocus="clearField(this)"
										onblur="checkForEmpty(this)"
										onkeypress="return isNumberKey(event)" /></td>

								</tr>
								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.assignExaminer.defaultInvDuty" /></div>
									</td>
									<td height="25" class="row-even" colspan="5"><html:select
										property="invigilatorId" styleClass="combo"
										styleId="invigilatorId">

										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>

										<logic:notEmpty property="invigilatorList"
											name="ExamAssignExaminerForm">
											<html:optionsCollection property="invigilatorList"
												name="ExamAssignExaminerForm" label="display" value="id" />
										</logic:notEmpty>
									</html:select></td>

								</tr>

							</table>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr></tr>

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
					<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="center"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.continue" />
							</html:submit></div>
							</td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>

