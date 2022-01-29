<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<title>:: CMS ::</title>
<SCRIPT>
function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);		
	resetOption("course");
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");	
	
}

function getCourses(programId) {
	
	getCoursesByProgram("coursesMap",programId,"course",updateCourses);	
}

function updateCourses(req) {
	updateOptionsFromMap(req,"course","- Select -");
}
function cancelAction() {
	document.location.href = "blockHallTicketProcess.do?method=initBlockHallTicketProcess";
}
function getExamsByExamTypeAndYear() {
	var examType=document.getElementById("examType").value;
	var year=document.getElementById("year").value;
	getExamNameByExamTypeAndYear("examMap", examType,year, "examNameId", updateExamName);
}
function updateExamName(req) {
	updateOptionsFromMap(req, "examNameId", "- Select -");
	updateCurrentExam(req, "examNameId");
}
</SCRIPT>
</head>
<html:form action="/blockHallTicketProcess" method="POST" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="getCandidates" />
	<html:hidden property="formName" value="blockHallTicketProcessForm" styleId="formName" />
	<html:hidden property="examType" value="Regular" styleId="examType" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt;Block Hall Ticket Process&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Block Hall Ticket Process</td>
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
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
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
							<td height="25" class="row-odd" colspan="2"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td  class="row-even" valign="top" colspan="2">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="blockHallTicketProcessForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td class="row-odd"></td>
									<td class="row-even"></td>
							</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> Exam
									Name :</div>
									</td>

									<td class="row-even">
									<html:select property="examId" styleClass="combo" styleId="examNameId" name="blockHallTicketProcessForm" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="blockHallTicketProcessForm" property="examNameList">
											<html:optionsCollection property="examNameList" name="blockHallTicketProcessForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
									</td>
									<td height="25" class="row-odd" align="right">
										<span class="Mandatory">*</span>Required Percentage:
									</td>
									<td height="25" class="row-even">
									<nested:text  property="reqPercentage" styleId="reqPercentage" maxlength="3" size="10" onkeypress="return isDecimalNumberKey(this.value,event)"></nested:text>
									</td>
								</tr>
								<tr>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>
									<td  height="25" class="row-even"><label>
									<html:select property="programTypeId" styleId="programType" styleClass="combo" onchange="getPrograms(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId" />
									</html:select> 
									</label></td>
									<td height="25" class="row-odd"><div align="right">Scheme No</div></td>
						                <td class="row-even">
										<html:select property="schemeNo" styleId="schemeNo" styleClass="combo">
											<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
											<html:option value="1">1</html:option>
											<html:option value="2">2</html:option>
											<html:option value="3">3</html:option>
											<html:option value="4">4</html:option>
											<html:option value="5">5</html:option>
											<html:option value="6">6</html:option>
											<html:option value="7">7</html:option>
											<html:option value="8">8</html:option>
											<html:option value="9">9</html:option>
											<html:option value="10">10</html:option>
										</html:select>
										</td>
								</tr>
								<tr>
								<td class="row-odd">
									<div align="right"><span class="Mandatory"></span>&nbsp;<bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td  class="row-even">
									<html:select property="programId"  styleId="program"  onchange="getCourses(this.value)" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="blockHallTicketProcessForm" property="programMap">
											<html:optionsCollection property="programMap" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
									</td>
									<td  class="row-odd">
									<div align="right"><bean:message key="knowledgepro.admin.course" />:</div>
									</td>
									<td  class="row-even">
									<html:select property="courseId" styleId="course" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="blockHallTicketProcessForm" property="courseMap">
											<html:optionsCollection property="courseMap" label="value" value="key" />
										</logic:notEmpty>

									</html:select>
									 </td>
								
								</tr>
								<tr>
									<td height="25" class="row-odd" align="right"  colspan="2">
										<span class="Mandatory">*</span>Reason:
									</td>
									<td height="25" class="row-even" colspan="2">
									<nested:textarea  property="comments" styleId="comments" cols="50" rows="3"></nested:textarea>
									</td>
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
								name="Submit7" type="submit" class="formbutton" value="Submit" /></td>

							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								type="button" class="formbutton" value="Cancel"
								onclick="cancelAction()" /></td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"></td>
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
<script>
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>
