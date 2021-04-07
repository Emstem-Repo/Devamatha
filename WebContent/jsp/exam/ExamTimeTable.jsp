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

function getPrograms(ProgramTypeId) {
	getProgramsByType("programMap", ProgramTypeId, "program",updatePrograms);
	resetOption("program");		
}
function updatePrograms(req) {
	updateOptionsFromMap(req, "program", "- Select -");
}

function resetPage()
{
	document.location.href="ExamTimeTable.do?method=initExamTimeTable";
}
function getExamName(examType)
{
	getExamNameByExamTypeId("schemeMap", examType, "examName", update);
	
}
function update(req)
{
	updateOptionsFromMap(req, "examName", "- Select -");
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
<style>
<!--
body {
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
	margin-top: 5px;
}

.hide {
	display: none
}

.hide1 {
	display: none
}

.hide1 {
	display: none
}
-->
</style>
<html:form action="/ExamTimeTable.do" method="POST"
	enctype="multipart/form-data">
	<html:hidden property="formName" value="ExamTimeTableForm"
		styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />

	<html:hidden property="method" styleId="method"
		value="add" />

	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.ExamTimeTable" /> &gt;&gt;</span></span></td>
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
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif">

					</td>

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
									<td width="20%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="30%" class="row-even" valign="top" >
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="ExamTimeTableForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
							        <td width="20%" class="row-odd"></td>
							        <td width="30%" class="row-even"></td>
							</tr>
								<tr>
									<td width="20%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.ExamTimeTableType" /> <span
										class="star"></span></div>
									</td>
									<td width="30%" class="row-even"><span class="star">
									<html:select property="examType" styleClass="body"
										styleId="examType" onchange="getExamsByExamTypeAndYear()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection property="examTypeMap"
											name="ExamTimeTableForm" label="value" value="key" />
									</html:select> </span></td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.ExamName" /> <span class="star"></span></div>
									</td>
									<td width="30%" height="25" class="row-even"><span
										class="star"> <html:select property="examName"
										styleClass="body" styleId="examName">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
									<logic:notEmpty name="ExamTimeTableForm" property="examNameMap">
											<html:optionsCollection name="ExamTimeTableForm" property="examNameMap" label="value"
													value="key" /></logic:notEmpty>

									</html:select> </span></td>


								</tr>
							<tr>
							 <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.program.Type" /></div></td>
							 <td width="30%" height="25" class="row-even"><span class="star">
							 <html:select property="programTypeId" styleClass="comboLarge" styleId="programTypeId" onchange="getPrograms(this.value)">
							 <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							 <html:optionsCollection name="programTypeList" label="programTypeName" value="programTypeId" /></html:select></span></td>
							  <td width="20%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.prog" /></div></td>
							  <td width="30%" height="25" class="row-even"><span class="star">
							  <html:select property="programId" styleClass="comboLarge" styleId="program">
							  <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									<c:if test="${programMap != null}">
										<html:optionsCollection name="programMap" label="value" value="key" />
									</c:if>
							 </html:select> </span></td>
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
							<td width="49%" height="35" align="right"><input
								name="Submit2" type="submit" class="formbutton" value="Submit" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								name="Submit2" type="button" class="formbutton" value="Reset" onclick="resetPage()"/></td>
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

<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>