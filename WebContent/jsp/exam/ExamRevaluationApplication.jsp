<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript">

function resetAll() {
	document.location.href = "ExamRevaluationApplication.do?method=initRevaluationApplication";
	
	}

</script>
<script type="text/javascript" language="javascript">
	// Functions for AJAX  first Method
	
	function updateCourses(req) {
		updateOptionsFromMap(req, "courseId", "--Select--");
	}
	//for second method
	function getScheme(courseId) {
		getSchemeNoByCourseId("schemeMap", courseId, "schemeNo", updateScheme);
	}
	
	function updateScheme(req) {
		updateOptionsFromMap(req, "schemeNo", "--Select--");
	}
	function getCourseByExamName(){
		var examName=document.getElementById("examNameId").value;
		var regNo=document.getElementById("regNo").value;
		var rollNo=document.getElementById("rollNo").value;
		getCourseByExamNameRegNoRollNo("coursesMap", examName,regNo,rollNo,  "courseId", updateCourses);
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
	
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamRevaluationApplication.do">
	<html:hidden property="formName" value="ExamRevaluationApplicationForm" />
	<html:hidden property="pageType" value="1" />

	<html:hidden property="examNameId_value" styleId="examNameId_value" />
	<html:hidden property="courseName" styleId="courseName" />
	<html:hidden property="schemeName" styleId="schemeName" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.revaluationApplication" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
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
									<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="ExamRevaluationApplicationForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
							<td height="25" class="row-odd" colspan="2"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examType" /> :</div></td>
								<td height="25" class="row-even"><html:select
										property="examType" styleClass="combo"  styleId="examType" onchange="getExamsByExamTypeAndYear()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="examTypeList"
											name="ExamRevaluationApplicationForm">
											<html:optionsCollection property="examTypeList"
												name="ExamRevaluationApplicationForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									</tr>
								<tr>

									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.revaluationApplication.examName" /> :</div>
									</td>
									<td class="row-even" colspan="4"><html:select
										property="examNameId" styleClass="combo" styleId="examNameId"
										name="ExamRevaluationApplicationForm"
										onchange="getCourseByExamName()" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamRevaluationApplicationForm"
											property="examNameMap">
											<html:optionsCollection property="examNameMap"
												name="ExamRevaluationApplicationForm" label="value"
												value="key" />
										</logic:notEmpty>
									</html:select></td>

								</tr>
								<tr>
									<td width="262" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.regNo" /></div>
									</td>
									<td width="206" class="row-even"><html:text
										property="regNo" styleId="regNo" maxlength="50"
										styleClass="TextBox" size="20" onblur="getCourseByExamName()" /></td>

									<td width="47" class="row-even"><bean:message
										key="knowledgepro.admin.or" /></td>
									<td width="194" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.rollNo" />:</div>
									</td>
									<td width="222" class="row-even"><html:text
										property="rollNo" styleId="rollNo" maxlength="50"
										styleClass="TextBox" size="20" onblur="getCourseByExamName()" /></td>
								</tr>
								<tr>
									<td height="26" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.revaluationApplication.course" /> :</div>
									</td>
									<td colspan="2" class="row-even" style="width: 195px"><html:select
										name="ExamRevaluationApplicationForm" property="courseId"
										styleId="courseId" styleClass="combo"
										onchange="getScheme(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${ExamSplSubGroupOperation == 'edit'}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${ExamRevaluationApplicationForm.examNameId != null && ExamRevaluationApplicationForm.examNameId != ''}">
													<c:set var="coursesMap"
														value="${baseActionForm.collectionMap['coursesMap']}" />
													<c:if test="${coursesMap != null}">
														<html:optionsCollection name="coursesMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
										<logic:notEmpty name="ExamRevaluationApplicationForm"
											property="courseMap">
											<html:optionsCollection property="courseMap" label="value"
												value="key" />
										</logic:notEmpty>
									</html:select></td>
									<td width="194" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.revaluationApplication.scheme" /> :</div>
									</td>
									<td width="222" class="row-even"><html:select
										name="ExamRevaluationApplicationForm" property="schemeNo"
										styleId="schemeNo" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${ExamSplSubGroupOperation == 'edit'}">
												<html:optionsCollection name="schemeMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${ExamRevaluationApplicationForm.courseId != null && ExamRevaluationApplicationForm.courseId != ''}">
													<c:set var="schemeMap"
														value="${baseActionForm.collectionMap['schemeMap']}" />
													<c:if test="${schemeMap != null}">
														<html:optionsCollection name="schemeMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
										<logic:notEmpty name="ExamRevaluationApplicationForm"
											property="schemeMap">
											<html:optionsCollection property="schemeMap" label="value"
												value="key" />
										</logic:notEmpty>
									</html:select></td>
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
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>
						<tr>
							<td width="52%" height="35">
							<div align="right"><input type="submit" class="formbutton"
								value="Search" name="method" /></div>
							</td>
							<td width="2%"></td>
							<td width="6%"><input type="submit" class="formbutton"
								value="Add" name="method" /></td>
							<td width="1%"></td>
							<td width="38%"><input type="Reset" class="formbutton"
								value="Reset" onclick="resetAll()" /></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>						
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