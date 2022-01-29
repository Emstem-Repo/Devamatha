<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>

<script type="text/javascript" language="javascript">
	// Functions for AJAX  first Method
	
	function resetAllValues() {
		document.location.href = "newSupplementaryImpApp.do?method=initSupplementaryImpApplicationForAll";
	}
	
	function getCourses(examName) {
		getCourseByExamName("coursesMap", examName, "courseId", updateCourses);
	}
	function updateCourses(req) {
		updateOptionsFromMap(req, "courseId", "--Select--");
	}

	//for second method
	
	function getScheme(courseId) {

		getSchemeNoByCourseId("schemeMap", courseId, "schemeId", updateScheme);
	}
	
	function updateScheme(req) {
		updateOptionsFromMap(req, "schemeId", "--Select--");
	}
	
	function getCourseByExamName(){
		var examName=document.getElementById("examName").value;
		var regNo=document.getElementById("regNo").value;
		var rollNo=document.getElementById("rollNo").value;
		getCourseByExamNameRegNoRollNo("coursesMap", examName,regNo,rollNo,  "courseId", updateCourses);
	}
	function addMethod(){
		document.getElementById("method").value="getStudent";
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

	function getAllStudents() {

		document.getElementById("method").value='getAllStudents';
		document.newSupplementaryImpApplicationForm.submit();
	}
	
	function checkUpdateProcessForSupp() {

		document.getElementById("method").value='checkUpdateProcessForSupp';
		document.newSupplementaryImpApplicationForm.submit();
	}
	
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/newSupplementaryImpApp">
	<html:hidden property="formName" value="newSupplementaryImpApplicationForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="examType" styleId="examType" value="Suppl" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			Add Students To Improvement Exam &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Add Students To Improvement Exam</strong></td>
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
									<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="newSupplementaryImpApplicationForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
							<td height="25" class="row-odd"></td>
								<td height="25" class="row-even"></td>
									</tr>
								<tr>
									<td height="30" class="row-odd">
									<div align="right"><span class="mandatoryfield">*</span>Improvement :
									</div>
									</td>
									<td height="30" class="row-even"><html:select
										property="supplementaryImprovement"
										styleId="supplementaryImprovement">
										<html:option value="">Select</html:option>
										<html:option value="Improvement">Improvement</html:option>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.revaluationApplication.examName" /> :</div>
									</td>
									<td class="row-even"><html:select property="examId"
										styleClass="combo" styleId="examName"
										name="newSupplementaryImpApplicationForm"
										onchange="getCourseByExamName()" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="newSupplementaryImpApplicationForm"
											property="examNameMap">
											<html:optionsCollection property="examNameMap"
												name="newSupplementaryImpApplicationForm" label="value"
												value="key" />
										</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr style="display: none;">
									<td width="262" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.revaluationApplication.regNo" /></div>
									</td>
									<td width="206" class="row-even"><html:text
										property="registerNo" styleId="regNo" maxlength="50"
										styleClass="TextBox" size="20" onblur="getCourseByExamName()" /></td>

									<td width="194" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.revaluationApplication.rollNo" />:</div>
									</td>
									<td width="222" class="row-even"><html:text
										property="rollNo" styleId="rollNo" maxlength="50"
										styleClass="TextBox" size="20" onblur="getCourseByExamName()" /></td>
								</tr>
								<tr>
									<td height="26" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.course" /> :</div>
									</td>
									<td class="row-even" style="width: 195px">
									<c:choose>
									<c:when test="${newSupplementaryImpApplicationForm.add}">
									<html:select
										name="newSupplementaryImpApplicationForm" property="courseId"
										styleId="courseId" styleClass="combo"
										onchange="getScheme(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="newSupplementaryImpApplicationForm" property="courseList">
										<html:optionsCollection name="newSupplementaryImpApplicationForm" property="courseList" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
									</c:when>
									<c:when test="${newSupplementaryImpApplicationForm.continue1}">
									<html:select
										name="newSupplementaryImpApplicationForm" property="courseId"
										styleId="courseId" styleClass="combo"
										onchange="getScheme(this.value)" disabled="true">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="newSupplementaryImpApplicationForm" property="courseList">
										<html:optionsCollection name="newSupplementaryImpApplicationForm" property="courseList" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
									</c:when>
									</c:choose>
									</td>
									<td width="194" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.revaluationApplication.scheme" /> :</div>
									</td>
									<td width="222" class="row-even">
									<c:choose>
									<c:when test="${newSupplementaryImpApplicationForm.add}">
									<html:select
										name="newSupplementaryImpApplicationForm" property="schemeNo"
										styleId="schemeId" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="newSupplementaryImpApplicationForm" property="schemeList">
										<html:optionsCollection name="newSupplementaryImpApplicationForm" property="schemeList" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
									</c:when>
									<c:when test="${newSupplementaryImpApplicationForm.continue1}">
									<html:select
										name="newSupplementaryImpApplicationForm" property="schemeNo"
										styleId="schemeId" styleClass="combo" disabled="true">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="newSupplementaryImpApplicationForm" property="schemeList">
										<html:optionsCollection name="newSupplementaryImpApplicationForm" property="schemeList" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
									</c:when>
									</c:choose>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="52%" height="35">
							<div align="right"></div>
							</td>
							<td width="2%"></td>
							<c:choose>
							<c:when test="${newSupplementaryImpApplicationForm.add}">
							
							<td width="6%"><input type="submit" class="formbutton"
								value="Add" onclick="checkUpdateProcessForSupp()"/></td>
							
							
							</c:when>
							<c:when test="${newSupplementaryImpApplicationForm.continue1}">
							<td width="6%"><input type="submit" class="formbutton"
								value="Continue" onclick="getAllStudents()"/></td>
							</c:when>
							
							</c:choose>
								
							<td width="2%"></td>
							<td width="38%"><input type="Reset" class="formbutton"
								value="Clear" onclick="resetAllValues()" /></td>
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