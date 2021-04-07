
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<title>:: CMS ::</title>
<script type="text/javascript" language="javascript">
	// Functions for AJAX  first Method
	

	//for second method
	
	function getScheme(courseId) {
	   var year= document.getElementById("academicYear").value;
	  
	   getSchemeNoByCourseId("schemeMap", courseId, "schemeId", updateScheme);
	}
	
	function updateScheme(req) {
		updateOptionsFromMap(req, "schemeId", "--Select--");
		
	}
	function resetErrMsgs() {
		document.getElementById("errorMessage").innerHTML = "";
		document.getElementById("message").innerHTML = "";
		
	}
	
</script>

<html:form action="/ExamSpecializationSubjectgroup.do">

	<html:hidden property="method" styleId="method"
		value="setExamSplSubGroup" />
	<html:hidden property="formName"
		value="ExamSpecializationSubjectGroupForm" />
	<html:hidden property="pageType" value="1" />


	<html:hidden property="academicYear_value" styleId="academicYear_value" />
	<html:hidden property="courseName" styleId="courseName" />
	<html:hidden property="schemeName" styleId="schemeName" />


	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.ExamSpecializationSubjectgroup" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">

			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.ExamSpecializationSubjectgroup" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">

					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6">
							<div align="right" style="color: red"><span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT></div>
							<FONT color="green" id="message"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT>
						</tr>

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
									<td height="25" class="row-odd" width="25%">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" />:</div>
									</td>
									<td class="row-even" width="25%"><html:select
										property="academicYear" styleId="academicYear"
										styleClass="combo" onclick="resetErrMsgs()">

										<cms:renderYear></cms:renderYear>
									</html:select></td>

									<td width="25%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="25%" class="row-even"><span class="row-even">
									<html:select name="ExamSpecializationSubjectGroupForm"
										property="courseId" styleId="courseId" styleClass="combo"
										onchange="getScheme(this.value)" onclick="resetErrMsgs()" style="width:300px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamSpecializationSubjectGroupForm"
											property="listCourses">
											<html:optionsCollection property="listCourses"
												name="ExamSpecializationSubjectGroupForm" label="display"
												value="id" />
										</logic:notEmpty>

									</html:select> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd" align="right" width="25%">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamSpecializationSubjectgroup.SchemeNo" /> :</div>
									</td>
									<td width="25%" class="row-even" colspan="3"><html:select
										name="ExamSpecializationSubjectGroupForm" property="schemeId"
										styleId="schemeId" styleClass="combo" onclick="resetErrMsgs()">
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
													test="${ExamSpecializationSubjectGroupForm.courseId != null && ExamSpecializationSubjectGroupForm.courseId != ''}">
													<c:set var="schemeMap"
														value="${baseActionForm.collectionMap['schemeMap']}" />
													<c:if test="${schemeMap != null}">
														<html:optionsCollection name="schemeMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
										<logic:notEmpty name="ExamSpecializationSubjectGroupForm"
											property="schemeNOMap">
											<html:optionsCollection property="schemeNOMap"
												name="ExamSpecializationSubjectGroupForm" label="value"
												value="key" />
										</logic:notEmpty>
									</html:select></td>





								</tr>
							</table>
							</td>
							<td width="5" height="54" background="images/right.gif"></td>
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleId="submitButton"
								styleClass="formbutton" value="Search" /></div>
							</td>
							<td width="2%"></td>
							<td width="40%"><input type="Reset"
								class="formbutton" value="Reset" /></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>

