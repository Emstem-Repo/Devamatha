<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>
<script type="text/javascript">
function getDepartments(year)
{
	getDepartmentsForFacultyEvaluationReportPrincipal("departmentMap", year, "departmentId", updateDepartments);
}
function updateDepartments(req) {
	updateOptionsFromMap(req, "departmentId", "- Select -");
}
function getTeachers(year)
{
	getTeachersForFacultyEvaluationReportPrincipal("teacherMap", year, "teacherId", updateTeachers);
}
function updateTeachers(req) {
	updateOptionsFromMap(req, "teacherId", "- Select -");
}
function getClasses(teacherId) {
	var year = document.getElementById("academicYear").value;
	getClassesForFacultyEvaluationReportPrincipal("classMap", teacherId, year, "class", updateClasses);
}
function updateClasses(req) {
	updateOptionsFromMap(req, "class", "- Select -");
}
function getSubjects(classId) {
	var year = document.getElementById("academicYear").value;	
	var teacherId = document.getElementById("teacherId").value;	
	getSubjectsForFacultyEvaluationReportPrincipal("subjectMap", classId, year, teacherId, "subject", updateSubjects);
}
function updateSubjects(req) {
	updateOptionsFromMap(req, "subject", "- Select -");
}

function printReport()
{
		var year = document.getElementById("academicYear").value;
		var classId = document.getElementById("class").value;
		var subject = document.getElementById("subject").value;
		var teacherId = document.getElementById("teacherId").value;
		var url="facultyEvaluationReportPrincipal.do?method=printReport&academicYear="+year+"&classId="+classId+"&subjectId="+subject+"&teacher="+teacherId;
		myRef = window.open(url,"FacultyEvaluationReportPrincipal","left=20,top=20,width=600,height=500,toolbar=10,resizable=0,scrollbars=1");	
}
function validate()
{
	var error="";
	if(document.getElementById("academicYear").value=="")
		error="Academic Year required <br>";
	if(document.getElementById("teacherId").value=="")
		error="Teacher is required <br>";
	if(document.getElementById("class").value=="")
		error+="Class is required<br>";	
	if(document.getElementById("subject").value=="")
		error+="Subject is required<br>";	
	if(error=="")
	{
		return true;
	}
	else
	{
		document.getElementById("err").innerHTML=error;
		return false;
	}	
}
function printReportDepartmentWise(){
	var year = document.getElementById("academicYear").value;
	var departmentId = document.getElementById("departmentId").value;
	var schemeNo = document.getElementById("schemeNo").value;
	var url="facultyEvaluationReportPrincipal.do?method=printReportDepartmentWise&academicYear="+year+"&departmentId="+departmentId+"&schemeNo="+schemeNo;
	myRef = window.open(url,"FacultyEvaluationReportPrincipal","left=20,top=20,width=600,height=500,toolbar=10,resizable=0,scrollbars=1");	
}
</script>
</head>
<html:form action="/facultyEvaluationReportPrincipal" method="POST">
<html:hidden property="formName" value="facultyEvaluationReportPrincipalForm" />
<html:hidden property="pageType" value="1" />
<table width="99%" border="0">  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.facultyEvaluation"/> 
    <span class="Bredcrumbs">&gt;&gt; 
    <bean:message key="knowledgepro.facultyEvaluation.facultyEvaluationReportPrincipal"/> &gt;&gt;</span> </span></td>
  </tr>  
  <tr>
	<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
				<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.facultyEvaluation.facultyEvaluationReportPrincipal"/></strong></td>

				<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
			</tr>
			<tr>
				<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
				<td valign="top" class="news">
				<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
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
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td  class="row-even"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="facultyEvaluationReportPrincipalForm" property="academicYear"/>' />
									<html:select property="academicYear" styleClass="combo" styleId="academicYear"
										onchange="getDepartments(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
																		<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Department</div>
								</td>	
								<td class="row-even">
									<input type="hidden" name="c1" id="c1" value='<bean:write name="facultyEvaluationReportPrincipalForm" property="departmentName"/>' />
									<html:select property="departmentId" styleClass="comboLarge"
										styleId="departmentId">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>	
											<c:choose>
											<c:when test="${departmentMap != null}">
												<html:optionsCollection name="departmentMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${facultyEvaluationReportPrincipalForm.departmentId != null && facultyEvaluationReportPrincipalForm.departmentId != ''}">
													<c:set var="departmentMap"
														value="${baseActionForm.collectionMap['departmentId']}" />
													<c:if test="${departmentId != null}">
														<html:optionsCollection name="departmentMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>									
									</html:select></td>						
								</tr>	
								<tr>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.facultyEvaluation.semester" /></div>
									</td>
									<td class="row-even">
									<input type="hidden" name="c1" id="c1" value='<bean:write name="facultyEvaluationReportPrincipalForm" property="schemeNo"/>' />
									<html:select property="schemeNo" styleClass="combo"
										styleId="schemeNo">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>	
											<html:option value="1">1</html:option>		
											<html:option value="2">2</html:option>																		
											<html:option value="3">3</html:option>																		
											<html:option value="4">4</html:option>																		
											<html:option value="5">5</html:option>																		
											<html:option value="6">6</html:option>							
									</html:select></td><!--				
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.selectedSubjects" /></div>
									</td>
									<td   class="row-even"><input type="hidden" name="c1" id="c1" value='<bean:write name="facultyEvaluationReportPrincipalForm" property="subjectId"/>' />
									<html:select property="subjectId" styleClass="comboLarge" styleId="subject">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
											<c:choose>
											<c:when test="${subjectMap != null}">
												<html:optionsCollection name="subjectMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${facultyEvaluationReportPrincipalForm.subjectId != null && facultyEvaluationReportPrincipalForm.subjectId != ''}">
													<c:set var="subjectMap"
														value="${baseActionForm.collectionMap['subjectMap']}" />
													<c:if test="${subjectMap != null}">
														<html:optionsCollection name="subjectMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>											
									</html:select></td>	
										
								--></tr>								
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

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center"><html:button property=""
								styleClass="formbutton" value="Print" onclick="printReportDepartmentWise()"></html:button></td>
						</tr>
						<tr><td height="35" align="center">&nbsp;</td></tr>
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