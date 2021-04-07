<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript">
function editDocExam(id){
	document.location.href = "assignCertificateCourse.do?method=editAssignCertificateCourse&id="+ id;
}
function deleteDocExam(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "assignCertificateCourse.do?method=deleteAssignCertificateCourse&id="+ id;
	}
}
function reActivate(){
	document.location.href = "assignCertificateCourse.do?method=reactivateAssignCertificateCourse";
}
function resetFormFields(){	
	resetFieldAndErrMsgs();
}
function getCertificateCourses() {
	document.getElementById("method").value = "setCertificateCourses";
	document.assignCertificateCourseForm.submit();
}
function getCourses(programTypeID) {
	resetOption("course");
	getCoursesByProgramType1("coursesMap", programTypeID, "course", updateCourses);
}
function updateCourses(req) {
	updateOptionsFromMap(req, "course", "- Select -");
}
function setProgramTypeName() {
	document.getElementById("programTypeName").value = document
			.getElementById("programTypeId").options[document
			.getElementById("programTypeId").selectedIndex].text;
}
</script>
<html:form action="/assignCertificateCourse">	
	<c:choose>
		<c:when test="${operation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateAssignCertificateCourse" />
		</c:when>
		<c:otherwise>
	<html:hidden property="method" styleId="method" value="addAssignCertificateCourse" />
	</c:otherwise>
	</c:choose>
	
	<html:hidden property="formName" value="assignCertificateCourseForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.assignCertificateCourse.displayName"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admission.assignCertificateCourse.displayName"/> Entry</strong></td>

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
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
								<td height="25" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
			                				<td height="25" class="row-even">
			                				<html:select property="programTypeId" styleId="programType" onchange="getCourses(this.value),setProgramTypeName()" styleClass="combo">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:optionsCollection name="assignCertificateCourseForm" property="programTypeList" label="programTypeName" value="programTypeId" />
											</html:select> 
			                				</td>
									<td  class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td  class="row-even">
									<html:select property="courseId" styleId="course" styleClass="comboLarge">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="assignCertificateCourseForm" property="courseList">
												<html:optionsCollection name="assignCertificateCourseForm" property="courseList" label="name" value="id" />
										</logic:notEmpty>
									</html:select>
									 </td>
								</tr>
								<tr>
								
								<td  class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.sem.type" />:</div>
									</td>
									<td  class="row-even">
									<html:select property="semType" styleId="semType" styleClass="combo" onchange="getCertificateCourses()">
										<html:option value="ODD">ODD</html:option>
										<html:option value="EVEN">EVEN</html:option>
									</html:select>
									 </td>
								
									 <td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.fee.academicyear.col"/>:</div>
									</td>
									<td class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="assignCertificateCourseForm" property="academicYear"/>" />
									<html:select
										property="academicYear" styleId="year"
										styleClass="combo" onchange="getCertificateCourses()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>	
									<td height="25" class="row-odd">
									<div align="right">
										</div>
									</td>
									<td class="row-even" valign="top">
									</td>	
								</tr>
								
								<tr>
									<td colspan="4" class="heading"> Certificate Courses</td>
								</tr>
								<tr>
									<td colspan="4">
										<table width="100%">
											<logic:notEmpty name="assignCertificateCourseForm" property="assignCertificateCourseDetailsTOs">
												<nested:iterate id="assignCertificateCourseDetailsTOs" name="assignCertificateCourseForm" property="assignCertificateCourseDetailsTOs" indexId="count">
												<c:if test="${count%3 == 0}">
													<tr class="row-even">
												</c:if>
												<td width="8%">
												 <input type="hidden" name="assignCertificateCourseDetailsTOs[<c:out value='${count}'/>].tempChecked"
																	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='assignCertificateCourseDetailsTOs' property='tempChecked'/>" />
																	<input
																	type="checkbox"
																	name="assignCertificateCourseDetailsTOs[<c:out value='${count}'/>].checked"
																	id="<c:out value='${count}'/>" />
																	<script type="text/javascript">
																		var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																		if(studentId == "true") {
																			document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
												</td>
												<td><nested:write name="assignCertificateCourseDetailsTOs" property="certificateCourseName"/> </td>
												</nested:iterate>
											</logic:notEmpty>
											<tr></tr>
										</table>
									</td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
									<c:choose>
							<c:when test="${operation == 'edit'}">
							<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:cancel value="Reset" styleClass="formbutton" ></html:cancel></td>
							</c:when>
							<c:otherwise>
								<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="submit"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button></td>
							</c:otherwise>
							</c:choose>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td width="7%" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
										  <td width="29%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.admin.course.with.col" /></div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.admission.sem.type" /></div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.admin.year" /></div>
											</td>
											<td width="9%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.edit" /></div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<logic:notEmpty name="assignCertificateCourseForm" property="assignCertificateCourseTOs">
										<logic:iterate id="dList" name="assignCertificateCourseForm" property="assignCertificateCourseTOs"
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
											<td align="center"><bean:write name="dList"
												property="courseName" /></td>
											<td align="center"><bean:write name="dList"
												property="semType" /></td>
											<td align="center"><bean:write name="dList"
												property="academicYear" /></td>
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editDocExam('<bean:write name="dList" property="id" />')" /></div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteDocExam('<bean:write name="dList" property="id" />')" /></div>
											</td>
											</tr>	
										</logic:iterate>
										</logic:notEmpty>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>			
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

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