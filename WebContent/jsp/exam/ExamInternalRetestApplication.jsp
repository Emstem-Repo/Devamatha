<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript">

function resetAll() {
	
	document.location.href = "ExamInternalRetestApplication.do?method=initInternalRetestApplication";
	document.getElementById("regNo").value="";
	document.getElementById("rollNo").value="";
	
	}

</script>
<script type="text/javascript" language="javascript">
	// Functions for AJAX  first Method
function set(target) {
		
document.getElementById("method").value=target;
}
	
	function updateCourses(req) {
		updateOptionsFromMap(req, "classId", "--Select--");
	}
	//for second method
	function getExamNames(academicYear)
	{
     getExamNameByAcademicYear("listExamName",academicYear,"examNameId",updateExamNames);
	}
	function updateExamNames(req)
	{
		
		updateOptionsFromMap(req, "examNameId", "--Select--");
	}
	function getCourseByExamName(){
		var examName=document.getElementById("examNameId").value;
		var regNo=document.getElementById("regNo").value;
		var rollNo=document.getElementById("rollNo").value;
		getClassByExamNameRegNoRollNo("coursesMap", examName,regNo,rollNo,  "classId", updateCourses);
	}
	
		
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamInternalRetestApplication.do" method="POST"
	enctype="multipart/form-data">

	<html:hidden property="method" styleId="method" value="search" />
	<html:hidden property="formName"
		value="ExamInternalRetestApplicationForm" styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			Internal Retest Application&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Internal Retest Application</strong></td>
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
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.studentEligibilityEntry.academicYear" />
									:</div>
									</td>
									<td class="row-even" width="25%" colspan="2"><html:select
										property="academicYear" styleId="academicYear"
										styleClass="combo" onchange="getExamNames(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>

									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.revaluationApplication.examName" /> :</div>
									</td>
									<td class="row-even" colspan="4"><html:select
										property="examNameId" styleClass="combo" styleId="examNameId"
										
										onchange="getCourseByExamName()" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
										<c:when test="${operation!=null && operation=='val'}">
										
										<logic:notEmpty name="ExamInternalRetestApplicationForm"
											property="listExamName">
											<html:optionsCollection property="listExamName"
												label="value"
												value="key" />
										</logic:notEmpty>
										</c:when>
										<c:otherwise>
										<c:if
											test="${ExamInternalRetestApplicationForm.examNameId != null && ExamInternalRetestApplicationForm.examNameId != ''}">
											<c:set var="coursesMap"
												value="${baseActionForm.collectionMap['coursesMap']}" />
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value"
													value="key" />
											</c:if>
										</c:if>
										
										
										
										
										</c:otherwise>
										</c:choose>
									</html:select></td>

								</tr>
								<tr>
									<td width="262" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.regNo" />:</div>
									</td>
									<td width="206" class="row-even"><html:text
										property="regNo" styleId="regNo" maxlength="50"
										styleClass="TextBox" size="20" onblur="getCourseByExamName()" /></td>

									<td width="47" class="row-even"><bean:message
										key="knowledgepro.admin.or" /></td>
									<td width="222" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.rollNo" />:</div>
									</td>
									<td width="250" class="row-even"><html:text
										property="rollNo" styleId="rollNo" maxlength="50"
										styleClass="TextBox" size="20" onblur="getCourseByExamName()" /></td>
								</tr>
								<tr>
									<td height="26" class="row-odd">
									<div align="right">Class Name :</div>
									</td>
									<td colspan="2" class="row-even" style="width: 195px"><html:select
										name="ExamInternalRetestApplicationForm" property="classId"
										styleId="classId" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>



										<c:if
											test="${ExamInternalRetestApplicationForm.examNameId != null && ExamInternalRetestApplicationForm.examNameId != ''}">
											<c:set var="coursesMap"
												value="${baseActionForm.collectionMap['coursesMap']}" />
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value"
													value="key" />
											</c:if>
										</c:if>


									</html:select></td>
									<td width="194" class="row-even"></td>
									<td width="222" class="row-even"></td>
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
								value="Search" name="method" onclick="set('search')" /></div>
							</td>
							<td width="2%"></td>
							<td width="6%"><input type="submit" class="formbutton"
								value="Add" name="method" onclick="set('add')" /></td>
							<td width="1%"></td>
							<td width="38%"><input type="button" class="formbutton"
								value="Reset" onclick="resetAll()"/></td>
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