<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>

<script type="text/javascript">
	var programId;
	function getPrograms(ProgramTypeId) {
		getProgramsByType("programMap", ProgramTypeId, "program",
				updatePrograms);
		resetOption("program");		
	}
	function updatePrograms(req) {
		updateOptionsFromMap(req, "program", "- Select -");
	}

	function editCertificateCourseTeacher(certificateCourseTeaId) {
		document.location.href = "certificateCourseTeacherEntry.do?method=editCertificateCourseTeacher&certificateCourseTeaId=" + certificateCourseTeaId;
	}

	function addCertificateCourseTeacher() {
		document.getElementById("method").value = "addCertificateCourseTeacher";
		document.certificateCourTeacherForm.submit();
	}

	function updateCertificateCourseTeacher() {
		document.getElementById("method").value = "updateCertificateCourseTeacher";
		resetErrMsgs();
		document.certificateCourTeacherForm.submit();
	}
	function deleteCertificateCourseTeacher(certificateCourseTeaId) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "certificateCourseTeacherEntry.do?method=deleteCertificateCourseTeacher&certificateCourseTeaId=" + certificateCourseTeaId ;
		}
	}

	function calladd() {
		var total = 0;
		var size = parseInt(document.getElementById("length").value);
		for ( var count = 0; count <= size - 1; count++) {
			var curValue = parseFloat(document
					.getElementById("seatAllocationList[" + count
							+ "].noofSeats").value);

			if (isNaN(curValue) || (curValue == null)) {
				curValue = 0;
			}
			total = total + curValue;
		}
		document.getElementById("total").value = total;
	}

	function clearField(field) {
		if (field.value == "00"){
			field.value = "";
		}
		if(field.value == "0"){
			field.value = "";
		}
	}
	function checkForEmpty(field) {
		if (field.value.length == 0)
			field.value = "00";
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
	function resetEveryFields() {
		document.getElementById("enhtime").value = "";
		document.getElementById("enmtime").value = "";
		document.getElementById("venue").value = "";
		document.getElementById("shtime").value = "";
		document.getElementById("smtime").value = "";
		document.getElementById("teacherId").selectedIndex = 0;
		document.getElementById("certificateCourseId").selectedIndex = 0;
		resetErrMsgs();

//		resetFieldAndErrMsgs();
	}
	function reActivate() {
		document.location.href = "certificateCourseTeacherEntry.do?method=reActiveCertificateCourseTeacher";
	}
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
</script>

<html:form action="certificateCourseTeacherEntry" method="post">
	<html:hidden property="formName" value="certificateCourTeacherForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
		<c:when test="${courseOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateCourse" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addCourse" />
		</c:otherwise>
	</c:choose>

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.certificate.course.teacher" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admission.certificate.course.teacher" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="452" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<FONT color="blue">* Note: Please Enter Time in 24 Hours Format </FONT>
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>

						<tr>
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
				                <tr >
	
	                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admission.courseName"/> </div></td>
	                  <td class="row-even" >
	                  
	                  <html:select property="certificateCourseId" name="certificateCourTeacherForm" styleId="certificateCourseId" styleClass="comboLarge">
	                  <logic:notEmpty name="certificateCourTeacherForm" property="courseList">
	                  <html:option value="">--Select--</html:option>
	             	  <html:optionsCollection property="courseList" label="courseName" value="id"/>
	                  </logic:notEmpty>
	                  </html:select>
	                  
					  </td>
	                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attn.teacherclass.teacher.name"/> </div></td>
	                  <td class="row-even" >
	                  <logic:notEmpty name="certificateCourTeacherForm" property="teachersMap">
	                  <html:select property="teacherId" name="certificateCourTeacherForm" styleId="teacherId" styleClass="comboLarge">
	                  <html:option value="">--Select--</html:option>
	                  <html:optionsCollection property="teachersMap" name="certificateCourTeacherForm" label="value" value="key"/>
	                  </html:select>
	                  </logic:notEmpty>
	                  </td>
	                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.interview.Venue"/> </div></td>
	                  <td class="row-even" >
	                  <html:textarea property="venue" styleClass="TextBox" cols="18" rows="2" styleId="venue"></html:textarea>
						</td>
	                </tr>
							<tr >
	                      <td width="18%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key = "knowledgepro.attn.period.from.time"/></div></td>
	                      <td width="20%" class="row-even" align="left" ><html:text name="certificateCourTeacherForm" property="startHours" styleClass="Timings" styleId="shtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>:
 						  <html:text name="certificateCourTeacherForm" property="startMins" styleClass="Timings" styleId="smtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/></td>
	                      <td width="13%" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key = "knowledgepro.attn.period.to.time"/></div></td>
	                      <td width="19%" class="row-even" align="left"><html:text name="certificateCourTeacherForm" property="endHours" styleClass="Timings" styleId="enhtime" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>:
 						  <html:text name="certificateCourTeacherForm" property="endMins" styleClass="Timings" styleId="enmtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/></td>
	                      <td class="row-odd" ><div align="right">&nbsp;</div></td>
	                      <td class="row-even" align="left">&nbsp;</td>
	                      </tr>
							
							
							
							
							</table>
									
									
									
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:button property="" styleClass="formbutton"
												value="Update" onclick="updateCertificateCourseTeacher()"></html:button>
										</c:when>
										<c:otherwise>
											<html:button property="method" styleClass="formbutton"
												value="Submit" onclick="addCertificateCourseTeacher()"></html:button>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:cancel value="Reset" styleClass="formbutton" onclick="updateCertificateCourseTeacher()"></html:cancel>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetEveryFields()"></html:button>
										</c:otherwise>
									</c:choose></td>
								</tr>
							</table>
							</td>
						</tr>

					<tr>
						<td height="25" colspan="6">
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
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.attendanceentry.teacher" /></td>

									<td class="row-odd" align="center"><bean:message key="knowledgepro.admission.certificate.course.name" /></td>
									<td class="row-odd" align="center"><bean:message key="knowledgepro.attn.period.from.time" /></td>
									<td class="row-odd" align="center"><bean:message key="knowledgepro.attn.period.to.time" /></td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:notEmpty name="certificateCourTeacherForm" property="certificateCourseTeacherList">
										<logic:iterate id="cList" name="certificateCourTeacherForm" property="certificateCourseTeacherList"
											indexId="count">
										<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center"><bean:write name="cList"
												property="usersName" /></td>
											<td align="center"><bean:write name="cList"
												property="certificateCourseName" /></td>
											<td align="center"><bean:write name="cList" property="startTime"/>
											</td>
											<td align="center"><bean:write name="cList" property="endTime"/>
											</td>
											<td>
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													 onclick="editCertificateCourseTeacher('<bean:write name="cList" property="id" />')"/></div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteCertificateCourseTeacher('<bean:write name="cList" property="id" />')"/></div>
											</td>
											</tr>	
										</logic:iterate>
										</logic:notEmpty>
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


					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>