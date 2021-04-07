<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
		<script language="JavaScript" src="js/admission/admissionform.js"></script>
</head>

<script language="javaScript" type="text/javascript">
// Code By Mary Job 
	function getPrograms(programTypeId) {
		getProgramsByType("programMap", programTypeId, "program",
				updatePrograms);
		resetOption("course");
	}

	function updatePrograms(req) {
		updateOptionsFromMap(req, "program", "- Select -");
	}

	function getCourses(programId) {
		getCoursesByProgram("coursesMap", programId, "course", updateCourses);
	}

	function updateCourses(req) {
		updateOptionsFromMap(req, "course", "- Select -");
	}
	</script>

<html:form action="/CourseChange">
	<html:hidden property="method" value="updateCourse" />
	<html:hidden property="formName" value="courseChangeForm" />
	<html:hidden property="pageType" value="2" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; Change Course &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> Change Course</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"> <span class='MandatoryMark'>
							<bean:message key="knowledgepro.mandatoryfields"/></span></div>
							<div id="errorMessage">
							<FONT color="red"><html:errors/></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT>
							</div>
							</td>
						</tr>
						
						<tr>
							<td width="21%" height="25" class="row-odd">
							<div align="right">Application No.:</div>
							</td>
							<td width="29%" height="25" class="row-even">
							<bean:write name="courseChangeForm" property="applicationNumber"/>
							</td>
							<td width="21%" height="25" class="row-odd">
								<div align="right">Name:</div>
							</td>
							<td width="29%" height="25" class="row-even">
								<bean:write name="courseChangeForm" property="nameOfStudent"/>
							</td>							
												
						</tr>
						<tr>
							<bean:define id="year1" property="applicationYear" name="courseChangeForm" type="java.lang.String"></bean:define>
							<% int yearInt = Integer.parseInt(year1); %>
							<td width="21%" height="25" class="row-odd">
								<div align="right">Academic Year:</div>
							</td>
							<td width="29%" height="25" class="row-even">
								<bean:write name="courseChangeForm" property="applicationYear"/>-<%=yearInt+1 %>
							</td>			
							<td width="21%" height="25" class="row-odd">
								<div align="right">Applied Course:</div>
							</td>
							<td width="29%" height="25" class="row-even">
								<bean:write name="courseChangeForm" property="appliedCourse"/>
							</td>						
						</tr>
						
						<!-- Code By Mary Job -->
						
						<tr>				
						
							<td width="19%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.admin.program.Type" /></div>
							</td>

							<td width="19%" class="row-even"><input type="hidden"
								name="prgTypeId" id="prgTypeId"
								value='<bean:write name="courseChangeForm" property="programType"/>' />
							<label><html:select property="programType"
								styleClass="comboLarge" onchange="getPrograms(this.value)"
								styleId="programType">
								<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
								<cms:renderProgramTypes></cms:renderProgramTypes>
							</html:select></label>
							
							</td>
							
						
						<td width="17%" class="row-odd">
						
						<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
								key="knowledgepro.admin.prog" /></div>
							</td>
							<td width="17%" height="25" class="row-even"><label>
							<html:select property="program" styleClass="comboLarge"
								styleId="program" onchange="getCourses(this.value)">
								<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
								<c:choose>
											<c:when test="${operation == 'edit'}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
								<c:if
									test="${courseChangeForm.programType != null && courseChangeForm.programType != ''}">
									<c:set var="programMap"
										value="${baseActionForm.collectionMap['programMap']}" />
									<c:if test="${programMap != null}">
										<html:optionsCollection name="programMap" label="value"
											value="key" />
									</c:if>
								</c:if>
								</c:otherwise>
								</c:choose>
							</html:select></label>
							</td>
						</tr>
						<tr>
							<td width="21%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Change to Course:</div>
						</td>							
						<td width="15%" class="row-even"><html:select
								property="course" styleClass="comboLarge" styleId="course">
								<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
								<c:choose>
											<c:when test="${operation == 'edit'}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
								<c:if
									test="${courseChangeForm.program != null && courseChangeForm.program != ''}">
									<c:set var="courseMap"
										value="${baseActionForm.collectionMap['coursesMap']}" />
									<c:if test="${courseMap != null}">
										<html:optionsCollection name="courseMap" label="value"
											value="key" />
									</c:if>
								</c:if>
								</c:otherwise>
								</c:choose>
							</html:select>
							</td>
					
						<td width="21%" height="25" class="row-odd">
							<div align="right">Selected Course:</div>
						</td>						
						<td width="29%" height="25" class="row-even">
							<bean:write name="courseChangeForm" property="fromCourse"/>
							</td>
								
						</tr>
					</table>
					<div align="center">
					<table width="100%" height="106" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td width="100%" height="46" class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="center"><html:submit styleClass="formbutton">
										<bean:message key="knowledgepro.submit" />
									</html:submit><html:button property="" styleClass="formbutton"
										onclick="resetFieldAndErrMsgs()">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button></div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="10"></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
