<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	function getclassesByCourse(courseId) {
		var year=document.getElementById("year").value;
		getClassesByCourseAndYear("classMap", courseId, "class",year, updateClasses);
		resetOption("subject");
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "class", "- Select -");
	}
	function getPracticalBatch() {
		document.getElementById("className").value = document
				.getElementById("class").options[document
				.getElementById("class").selectedIndex].text;
		document.getElementById("courseName").value = document
		.getElementById("course").options[document.getElementById("course").selectedIndex].text;
		document.getElementById("programName").value = document
		.getElementById("program").options[document.getElementById("program").selectedIndex].text;                				
		document.getElementById("method").value = "getCandidates";
		document.boardDetailsForm.submit();
	}
	function getCourses(year) {
		getCourseByYear("courseMap", year, "course", updateCourses);
		resetOption("subject");
	}
	function updateCourses(req) {
		updateOptionsFromMap(req, "course", "- Select -");
	}
	function getCoursesByProgramId(programId){
		getCoursesByProgram("courseMap",programId,"course",updateCourses);
		resetOption("subject");		
	}
</script>
<html:form action="/boardDetails" method="post">
	<html:hidden property="method" styleId="method" value="getCandidates" />
	<html:hidden property="formName" value="boardDetailsForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="className" styleId="className" />
	<html:hidden property="courseName" styleId="courseName" />
	<html:hidden property="programName" styleId="programName" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.boardDetails.label" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.admission.boardDetails.label" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /> </span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
										value='<bean:write name="boardDetailsForm" property="year"/>' />
									<html:select property="year" styleClass="combo" styleId="year">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									
									
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.program" /></div>
									</td>
									<td   class="row-even"><input type="hidden"
										name="cId" id="cId"
										value='<bean:write name="boardDetailsForm" property="programId"/>' />
									<html:select property="programId" styleClass="combo"
										styleId="program" onchange="getCoursesByProgramId(this.value)">
										<html:option value="">
											-<bean:message key="knowledgepro.select" />-</html:option>
											<logic:notEmpty name="boardDetailsForm" property="programMap">
										<html:optionsCollection name="boardDetailsForm" label="value" value="key" property="programMap"/>
										</logic:notEmpty>
									</html:select></td>
									
									</tr>
									
									<tr>
									
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.courses.report" /></div>
									</td>
									<td   class="row-even"><input type="hidden"
										name="cId" id="cId"
										value='<bean:write name="boardDetailsForm" property="courseId"/>' />
									<html:select property="courseId" styleClass="combo"
										styleId="course" onchange="getclassesByCourse(this.value)">
										<html:option value="">
											-<bean:message key="knowledgepro.select" />-</html:option>
											<logic:notEmpty name="boardDetailsForm" property="courseMap">
										<html:optionsCollection name="boardDetailsForm" label="value" value="key" property="courseMap"/>
										</logic:notEmpty>
									</html:select></td>
									
									<td  height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.attendance.class.col" /></div>
									</td>
									<td   class="row-even"><input type="hidden"
										name="c1" id="c1"
										value='<bean:write name="boardDetailsForm" property="classId"/>' />
									<html:select property="classId" styleClass="combo"
										styleId="class">
										<html:option value="">
											-<bean:message key="knowledgepro.select" />-</html:option>
											<logic:notEmpty name="boardDetailsForm" property="classMap">
										<html:optionsCollection name="boardDetailsForm" label="value" value="key" property="classMap"/></logic:notEmpty>
									</html:select></td>
									
								</tr>
								<tr>
								
								<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.examDefinition.year" /></div>
									</td>
									
									<td   class="row-even">
									<html:select property="years" styleClass="combo"
										styleId="years" name="boardDetailsForm">
										<html:option value="">-
											<bean:message key="knowledgepro.select" />-</html:option>
									        <html:optionsCollection name="boardDetailsForm" label="value" value="key" property="yearMap"/>
									</html:select>
									</td>
									
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span><bean:message
										key="knowledgepro.hostel.reservation.registerNo" /></div>
									</td>
									<td   class="row-even">
									<html:text property="registerNo" styleId="registerNo" maxlength="10" size="10"></html:text>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center"><html:button property=""
								styleClass="formbutton" value="Next"
								onclick="getPracticalBatch()"></html:button></td>
						</tr>
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
<script type="text/javascript"><!--
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var classId = document.getElementById("c1").value;
	if (classId != null && classId.length != 0) {
		document.getElementById("class").value = classId;
	}
	/*var courseId = document.getElementById("cId").value;
	if(courseId !=null && courseId.length !=0){
		document.getElementById("course").value = courseId;
	}*/
	document.getElementById("course").value = null;
	document.getElementById("years").value = null;
	document.getElementById("program").value =null;
</script>