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

	function cancelAction() {
		document.location.href = "marksEntry.do?method=initExamMarksEntryForAllInternals";
	}

	
	function getCourse() {
		var year = document.getElementById("year").value;

		var isRoleIdForDeveloper=document.getElementById("isRoleIdForDeveloper").value;
		//var roleId = document.getElementById("roleId").value;
		//var roleIdForTeacher = document.getElementById("roleIdForTeacher").value;
		if(isRoleIdForDeveloper=="true"){	
			getCoursesByAcademicYear("schemeMap",year, "course", updateToCourse);
		
		}else{

		getCourseByTeacher("schemeMap",year, "course", updateToCourse);
		}
				
		
	}
	
	function updateToCourse(req) {
		updateOptionsFromMap(req, "course", "- Select -");

	}

	function getScheme(courseId) {
		var year = document.getElementById("year").value;
		//var roleId = document.getElementById("roleId").value;
		//var roleIdForTeacher = document.getElementById("roleIdForTeacher").value;	
		var isRoleIdForDeveloper=document.getElementById("isRoleIdForDeveloper").value;
		if(isRoleIdForDeveloper=="true"){
			getSchemeNoByAcademicYear("schemeMap",courseId,year, "scheme",updateToScheme);	
		}else{
			getSchemeNoByCourseIdByTeacher("schemeMap",courseId,year, "scheme",updateToScheme);
		}	
	}
	
	function updateToScheme(req) {
		updateOptionsFromMap(req, "scheme", "- Select -");

	}

	function getSectionByScheme(schemeId) {
		document.getElementById("sCodeName_1").checked = true;
		var ScodeName=document.getElementById("sCodeName_1").value;
		getSCodeName(ScodeName);
				
	}
	
	function getSCodeName(sCName) {

		var courseId = document.getElementById("course").value;
		var schemeId = document.getElementById("scheme").value;
		var year = document.getElementById("year").value;
		//var roleId = document.getElementById("roleId").value;
		//var roleIdForTeacher = document.getElementById("roleIdForTeacher").value;
		var isRoleIdForDeveloper=document.getElementById("isRoleIdForDeveloper").value;
		if(isRoleIdForDeveloper=="true"){
			getSubjectsByCourseTermYear("schemeMap", courseId, year , schemeId,  "subject",
				updateToSubject);	

		}else{
		
			getSubjectsCodeNameByCourseSchemeIdByTeacher("schemeMap", sCName, courseId, "subject",
					updateToSubject, schemeId);
		}
		
	}
	
	function updateToSubject(req) {
		var subco ="subco";
		updateOptionsFromMap(req, "subject", "- Select -");
	}

	function getSubjectType(subjectNo) {
		if (subjectNo != "") {
			var args = "method=getSubjectsTypeBySubjectId&subjectId="
					+ subjectNo;
			var url = "AjaxRequest.do";
			requestOperation(url, args, updateSubjectType);
		}
	}
	
	function updateSubjectType(req) {
		updateSubjectsTypeBySubjectIdForMarks(req, "subjectType");
	}
	
</SCRIPT>

</head>


<html:form action="/marksEntry" method="POST" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="getCandidatesForAllInternals" />
	<html:hidden property="formName" value="newExamMarksEntryForm" styleId="formName" />
	<html:hidden property="pageType" value="3" styleId="pageType" />
	<html:hidden property="validationAST" styleId="validationAST" />
	<html:hidden property="validationET" styleId="validationET"/>
	<html:hidden property="displayAST" styleId="displayAST"/>
	<html:hidden property="displayET" styleId="displayET"/>
	<html:hidden property="roleIdForTeacher" styleId="roleIdForTeacher"/>
	<html:hidden property="isRoleIdForDeveloper" styleId="isRoleIdForDeveloper"/>
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt;Teachers Internals Marks Entry&gt;&gt;</span></span></td>

		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Teachers Internals Marks Entry </td>
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
							
							
								
								<tr >
								     <td height="27" width="50%" class="row-odd" valign="middle"><div align="right"><span class="Mandatory">*</span> <font style="font-size: 11px"><bean:message
										key="knowledgepro.admin.year" /> :</font></div></td>
									<td height="27" width="50%" class="row-even" valign="middle">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="newExamMarksEntryForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getCourse()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									
								</tr>
								
								
								<tr>


									<td width="50%" height="27" class="row-odd" valign="middle">
									<div align="right"><span class="Mandatory">*</span><font style="font-size: 11px">Course:</font></div>
									</td>
									<td width="50%" height="27" class="row-even" valign="middle"><html:select
										property="courseId" styleClass="body" styleId="course"
										onchange="getScheme(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm"
											property="courseMap">
											<html:optionsCollection property="courseMap"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>

									
								</tr>
								
								<tr>
								
								<td height="27" width="50%" class="row-odd" valign="middle">
									<div align="right"><span class="Mandatory">*</span>
									<font style="font-size: 11px">Semester:</font></div>
									</td>
										<td height="27" width="50%" class="row-even" valign="middle"><html:select
										property="schemeNo" styleClass="body" styleId="scheme"
										onchange="getSectionByScheme(this.value)" > 
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm"
											property="schemeMap">
											<html:optionsCollection property="schemeMap"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
								
								
								</tr>
								
								
								<tr>
								
								
								<td height="27" colspan="2" class="row-even" valign="middle">
									<div align="center">
									<html:radio property="displaySubType" styleId="sCodeName_1" value="sCode" onchange="getSCodeName(this.value)"><font style="font-size: 11px">Subject Code</font></html:radio>
									 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 <html:radio property="displaySubType" styleId="sCodeName_2" value="sName" onchange="getSCodeName(this.value)"><font style="font-size: 11px">Subject Name</font></html:radio>
									</div>
									</td>
									
									
								</tr>
								
								
								
								
								
								<tr>
								<td height="27" width="50%" class="row-odd" valign="middle">
									<div align="right"><span class="Mandatory"></span><font style="font-size: 11px">Subject
									:</font></div>
									</td>
									<td height="27" width="50%" class="row-even" valign="middle"><html:select
										property="subjectId" styleClass="body" styleId="subject"
										onchange="getSubjectType(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm" property="subjectCodeNameMap">
											<html:optionsCollection property="subjectCodeNameMap"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
								</tr>
								
								
								<tr>
									
									<td height="27" width="50%" class="row-odd" valign="middle">
									<div align="right"><font style="font-size: 11px">Subject Type:</font></div>
									</td>

									<td height="27" width="50%" class="row-even" valign="middle"><html:select
										property="subjectType" styleId="subjectType" onchange="displayEvaluAndAnswerScript(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<logic:notEmpty name="newExamMarksEntryForm"
											property="subjectTypeMap">
											<html:optionsCollection property="subjectTypeMap"
												name="newExamMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>

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
						<tr>
							<td width="49%" height="35" align="right"><input
								name="Submit7" type="submit" class="formbutton" value="Submit" /></td>

							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								type="button" class="formbutton" value="Cancel"
								onclick="cancelAction()" /></td>
						</tr>
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
<script type="text/javascript">

var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>