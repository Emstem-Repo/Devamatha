<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link href="../css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/common.js"></script>

<script type="text/javascript">

function editSubjectRule(id,subjectName,subjectCode)
{
	
	//document.location.href = "ExamSubjectRuleSettings.do?method=setEdittedSubRuleDetails&id="+id;
	document.getElementById("method").value ="setEdittedSubRuleDetails";
	document.getElementById("id").value =id;
	document.getElementById("subjectName").value=subjectName+"("+subjectCode+")";
	document.ExamSubjectRuleSettingsForm.submit();
}

function deletSubjectRule(id)
{
	document.getElementById("method").value ="deleteSubRuleDetails";
	document.getElementById("id").value =id;
	document.ExamSubjectRuleSettingsForm.submit();
}
</script>

<html:form action="/ExamSubjectRuleSettings.do" method="POST">

	
	<html:hidden property="formName" value="ExamSubjectRuleSettingsForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value=" " styleId="method"/>
	<html:hidden property="id" styleId="id"/>
	<html:hidden property="subjectName" styleId="subjectName"/>
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><a href="main.html"
				class="Bredcrumbs"><bean:message key="knowledgepro.exam.exam" /></a>
			<span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.exam.subjectrulesettings" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.subjectrulesettings" /></strong></td>
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
									<td width="22%" class="row-odd" height="28">
									<div align="right"><bean:message
										key="knowledgepro.petticash.academicYear" /></div>
									</td>
									<td width="34%" class="row-even" colspan="2"><bean:write
										name="ExamSubjectRuleSettingsForm" property="academicYearName" />
									<html:hidden name="ExamSubjectRuleSettingsForm"
										property="academicYearName" /></td>
									<td class="row-even"></td>

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.programtype" /> :</div>
									</td>
									<td width="25%" height="25" class="row-even"><bean:write
										name="ExamSubjectRuleSettingsForm" property="programName" />
									<html:hidden name="ExamSubjectRuleSettingsForm"
										property="programName" /></td>
									<td height="25" class="row-odd">Course:</td>

									<td class="row-even"><nested:iterate
										name="ExamSubjectRuleSettingsForm" property="listCourseName"
										id="listCourses" type="com.kp.cms.to.exam.ExamCourseUtilTO">

										<nested:write name="listCourses" property="display" />
                                        &nbsp;&nbsp;&nbsp;

									</nested:iterate></td>
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
					<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
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
								<tr>
									<td height="25" class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td height="25" class="row-odd">Course</td>
									<td  class="row-odd">Scheme</td>
									<td class="row-odd">Subject Name</td>
									<td class="row-odd">
									<div align="center">Edit</div>
									</td>
									<td class="row-odd">
									<div align="center">Delete</div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<nested:iterate  property="edittedSubRuleList" indexId="count">
								<c:choose>
							   <c:when test="${temp == 0}">
									<tr>
										<td width="6%" height="25" class="row-even"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td width="36%" height="25" class="row-even" align="left"><nested:write  property="courseName" /></td>
										<td width="3%" height="25" class="row-even" align="left"><nested:write  property="schemeName" /></td>
										<td width="14%" height="25" class="row-even" align="left"><nested:write  property="subjectName" />(
										<nested:write  property="subjectCode" />)</td>
										<td width="10%" height="25" class="row-even"><div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editSubjectRule('<nested:write  property="subjectRuleSettingsId"/>','<nested:write  property="subjectName" />','<nested:write  property="subjectCode" />')"></div></td>
										<td width="10%" height="25" class="row-even"><div align="center"><img src="images/delete_icon.gif" width="16" height="18" style="cursor: pointer" onclick="deletSubjectRule('<nested:write  property="subjectRuleSettingsId"/>')"></div></td>
									</tr>
								<c:set var="temp" value="1" />
									</c:when>
									<c:otherwise>
									<tr>
                                        <td width="6%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td width="36%" height="25" class="row-white" align="left"><nested:write  property="courseName" /></td>
										<td width="3%" height="25" class="row-white" align="left"><nested:write  property="schemeName" /></td>
										<td width="14%" height="25" class="row-white" align="left"><nested:write  property="subjectName" />(<nested:write  property="subjectCode" /> )
										</td>
										<td width="10%" height="25" class="row-white"><div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editSubjectRule('<nested:write  property="subjectRuleSettingsId"/>','<nested:write  property="subjectName" />','<nested:write  property="subjectCode" />')"></div></td>
										<td width="10%" height="25" class="row-white"><div align="center"><img src="images/delete_icon.gif" width="16" height="18" style="cursor: pointer" onclick="deletSubjectRule('<nested:write  property="subjectRuleSettingsId"/>')"></div></td>
									</tr>
								  <c:set var="temp" value="0" />
							      </c:otherwise>
					      </c:choose>
				       </nested:iterate>
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
					<td class="heading">&nbsp;</td>
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
