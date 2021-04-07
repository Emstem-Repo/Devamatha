<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Map,java.util.HashMap"%>

<script type="text/javascript" language="javascript">


	// Functions for AJAX 
	function updateCourses(req) {
		updateOptionsFromMap(req, "course", "--Select--");
	}

	function updateScheme(req) {
		updateOptionsFromMap(req, "scheme", "--Select--");
	}

	function getAllScheme(courseId) {

		var academicYear = document.getElementById("academicYear").value;
		var course = document.getElementById("course").value;
		
	    getSchemeNoByCourseId("schemeMap", course, "scheme",updateScheme)

	}

	function add(){
        document.attendanceCondonationForm.mode.value="add";
        document.attendanceCondonationForm.method.value="getSearchedStudents";
        document.attendanceCondonationForm.submit();  
	}

	function edit(){
        
        document.attendanceCondonationForm.mode.value="edit";
        document.attendanceCondonationForm.method.value="getSearchedStudents";
        document.attendanceCondonationForm.submit();  
	}

	function resetMessages() {
		document.getElementById("academicYear").selectedIndex = 0;
		document.getElementById("scheme").selectedIndex = 0;
		document.getElementById("course").selectedIndex = 0;
		document.getElementById("programType").value ="";
		document.getElementById("classId").selectedIndex = 0;
		document.getElementById("classId").selectedIndex = 0;
		
		resetErrMsgs();
		document.location.href="attendanceCondonation.do?method=initAttendanceCondonation";
	}
	function getPrograms(ProgramTypeId) {
		getProgramsByType("programMap", ProgramTypeId, "program",
				updatePrograms);
		resetOption("course");
	}

	function updatePrograms(req) {
		updateOptionsFromMap(req, "program", "- Select -");
	}

	function getCourse(programId) {
		getCourseByPrograminOrder("courseMap", programId, "course", updateCourse);
	}
	function updateCourse(req) {
		updateOptionsFromMap(req, "course", " Select ");
	}

	

	function getClass(semester) {
  		var academicYear = document.getElementById("academicYear").value;
		var program = document.getElementById("program").value;
		var programtype = document.getElementById("programType").value;
		var course = document.getElementById("course").value;
		getClassesByCourseSemesterAndAcademicYear("classMap", semester,"classId", updateClass,academicYear, programtype, program, course);
	}
	function updateClass(req) {
		updateOptionsFromMap(req, "classId", " Select ");
	}


	function validate(evt) {
		  var theEvent = evt || window.event;
		  var key = theEvent.keyCode || theEvent.which;
		  key = String.fromCharCode( key );
		  var regex = /[0-9]|\./;
		  if( !regex.test(key) ) {
			  if (theEvent.keyCode!=8){
		    theEvent.returnValue = false;
		    if(theEvent.preventDefault) theEvent.preventDefault();
			  }
		  }
		}
	
</script>

<html:form action="/attendanceCondonation" method="POST" >

	<html:hidden property="method" styleId="method" value="getSearchedStudents" />
	<html:hidden property="formName" value="attendanceCondonationForm" />
	<html:hidden property="mode" value="add"/>
	<html:hidden property="pageType" value="1" />


	<html:hidden property="academicYear_value" styleId="academicYear_value" />
	<html:hidden property="courseName" styleId="courseName" />
	<html:hidden property="schemeName" styleId="schemeName" />


	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.att.Attendance" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.att.condon" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">

			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.att.condon" /></strong></div>
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
								class='MandatoryMark'>* Mandatory fields</span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
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
									<td class="row-even" width="25%"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="attendanceCondonationForm" property="academicYear"/>' />
									<html:select property="academicYear" styleId="academicYear"
										styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>
									<td width="21%" height="25" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message
									key="knowledgepro.admin.program.type" /></div>
								</td>
								<td width="24%" height="25" class="row-even"><html:select
									property="programTypeId" styleClass="comboLarge"
									styleId="programType" onchange="getPrograms(this.value)">
									<html:option value="">
										<bean:message key="knowledgepro.admin.select" />
									</html:option>
									<html:optionsCollection name="attendanceCondonationForm" property="programTypeList"
										label="programTypeName" value="programTypeId" />
								</html:select> <span class="star"></span></td>
								</tr>
							<tr>
							<td width="25%" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message
									key="knowledgepro.admin.prog" /></div>
								</td>
								<td width="30%" class="row-even"><span class="star">
								<html:select name="attendanceCondonationForm" property="programId"
									styleId="program" styleClass="comboLarge"
									onchange="getCourse(this.value)">
									<html:option value="">- Select -</html:option>
								
										<c:if test="${programMap != null}">
											<html:optionsCollection name="programMap" label="value"
												value="key" />
										</c:if>
								</html:select> </span></td>
								<td width="25%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td width="25%" class="row-even"><span class="row-even">
									<html:select name="attendanceCondonationForm" property="course"
										styleId="course" styleClass="combo"
										onchange="getAllScheme(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
																
										<!--<logic:notEmpty name="attendanceCondonationForm"
												property="courseNameList">
												<html:optionsCollection property="courseNameList"
													name="attendanceCondonationForm" label="value" value="key" />
											</logic:notEmpty>-->
										<c:if test="${courseMap != null}">
											<html:optionsCollection name="courseMap" label="value"
												value="key" />
										</c:if>
									</html:select> </span></td>
							</tr>
								
								<tr>
									<td height="25" class="row-odd" align="right" width="25%">
									<div align="right"><span class="Mandatory">*</span>Semester</div>
									</td>
									<td width="25%" class="row-even" ><html:select
										name="attendanceCondonationForm" property="scheme"
										styleId="scheme" styleClass="combo" onchange="getClass(this.value)"	>
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${attendanceCondonationForm.courseId != null && attendanceCondonationForm.courseId != ''}">
											<c:set var="schemeMap"
												value="${baseActionForm.collectionMap['schemeMap']}" />
											<c:if test="${schemeMap != null}">
												<html:optionsCollection name="schemeMap" label="value"
													value="key" />
												</c:if>
											</c:if>	
											<logic:notEmpty name="attendanceCondonationForm"
														property="schemeMapList">
														<html:optionsCollection property="schemeMapList"
															name="attendanceCondonationForm" label="value" value="key" />
											</logic:notEmpty>

												
											
										
									</html:select></td>
									
									<td height="25" class="row-odd" align="right" width="25%">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.att.condon.class" /></div>
									</td>
									<td width="25%" class="row-even" colspan="3"><html:select
										name="attendanceCondonationForm" property="classId"
										styleId="classId" styleClass="combo"	>
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										
											<logic:notEmpty name="attendanceCondonationForm"
														property="classMap">
														<html:optionsCollection property="classMap"
															name="attendanceCondonationForm" label="value" value="key" />
											</logic:notEmpty>

												
											
										
									</html:select></td>

								</tr>
								
								<tr>
								  <td height="25" class="row-odd" align="right" width="25%">
									Cut Off Percentage
								  </td>
								  
								  <td height="25" class="row-even"  width="25%">
									<html:text property="cutoff" name="attendanceCondonationForm" onkeypress='validate(event)'></html:text>
								  </td>
								
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
					<table align="center" width="30%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35">
							<div><html:submit styleId="submitButton"
								styleClass="formbutton" value="ADD" onclick="add()"/></div>
							</td>
							<td height="35">
							<div><html:submit styleId="submitButton1"
								styleClass="formbutton" value="EDIT" onclick="edit()"/></div>
							</td>
							<td></td>
							<td><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetMessages()">
								<bean:message key="knowledgepro.cancel" />
							</html:button></td>
						</tr>
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
<script type="text/javascript" language="javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
	</script>
</html:form>

