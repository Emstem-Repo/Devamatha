<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >

function getClasses(year) {
	getClassesByYear("classMap", year, "class", updateClasses);
	var destination5 = document.getElementById("subject");
	for (x1=destination5.options.length-1; x1>=0; x1--) {
		destination5.options[x1]=null;
	}
}
function updateClasses(req) {
	updateOptionsFromMap(req, "class", "- Select -");
}

function getSubjects(classSchemewiseId) {
	getSubjectsByClass("subjectMap", classSchemewiseId, "subject",
			updateSubjects);
}
function updateSubjects(req) {
	updateOptionsFromMapMultiselect(req, "subject", "- Select -");
}

function resetMessages() {
	var destination5 = document.getElementById("subject");
	for (x1=destination5.options.length-1; x1>=0; x1--) {
		destination5.options[x1]=null;
	}
	resetFieldAndErrMsgs();
}
</script>
<html:form action="/AttendanceRemove">
	<html:hidden property="method" styleId="method" value="initAttendanceRemoveSecondPage" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="removeAttendanceForm" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.attendance.remove.label" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.attendance.remove.label" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td  class="row-even"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="removeAttendanceForm" property="year"/>' />
									<html:select property="year" styleClass="combo" styleId="year"
										onchange="getClasses(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.class.col" /></div>
									</td>
									<td  class="row-even"><input type="hidden"
										name="c1" id="c1"
										value='<bean:write name="removeAttendanceForm" property="classId"/>' />
									<html:select property="classSchemewiseId" styleClass="comboLarge"
										styleId="class" onchange="getSubjects(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<c:choose>
											<c:when test="${classMap != null}">
												<html:optionsCollection name="classMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${removeAttendanceForm.classSchemewiseId != null && removeAttendanceForm.classSchemewiseId != ''}">
													<c:set var="classMap"
														value="${baseActionForm.collectionMap['classMap']}" />
													<c:if test="${classMap != null}">
														<html:optionsCollection name="classMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>
								</tr>
								<tr>
									<td  class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.mail.subject" /></div>
									</td>
									<td  class="row-even">
									<html:select
										property="subjects" styleClass="comboLarge" size="5" styleId="subject" style="width:200px;height:80px" multiple="multiple">
										<c:choose>
											<c:when test="${subjectMap != null}">
												<html:optionsCollection name="subjectMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
													<c:if test="${subjectMap != null}">
														<html:optionsCollection name="subjectMap" label="value"
															value="key" />
													</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>
									<td class="row-odd">
										<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.reservation.registerNo" /></div>
									</td>
									<td class="row-even">
										<html:text property="regNo" styleId="regNo" maxlength="10" size="10"></html:text>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" onclick="resetMessages()">
								<bean:message key="knowledgepro.admin.reset" />
								</html:button> 
							</td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>

<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var classId = document.getElementById("c1").value;
	if (classId != null && classId.length != 0) {
		document.getElementById("class").value = classId;
	}
</script>
