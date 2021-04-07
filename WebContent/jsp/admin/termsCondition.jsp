<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	var tempProgramId;
	var tempCourseId;
	function getPrograms(ProgramTypeId) {
		getProgramsByType("programMap", ProgramTypeId, "program",
				updatePrograms);
		resetOption("course");
	}

	function updatePrograms(req) {
		updateOptionsFromMap(req, "program", "- Select -");
	}

	function getCourse(programId) {
		getCoursesByProgram("courseMap", programId, "course", updateCourse);
	}
	function updateCourse(req) {
		updateOptionsFromMap(req, "course", " Select ");
	}

	function saveTermsAndConditions() {
		document.termsConditionForm.submit();
	}

	function editTermsAndConditions(id) {
		document.location.href = "TermsCondition.do?method=editTermsAndConditions&id="
				+ id;

	}
	function deleteTermsAndCondtion(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm == true) {
			document.location.href = "TermsCondition.do?method=deleteTermsCondition&id="
					+ id;
		}
	}

	function updateTermsAndConditions() {
		resetErrMsgs();
		document.getElementById("method").value = "updateTermsCondition";
		document.termsConditionForm.submit();
	}
	function addTermsAndConditions() {
		document.getElementById("courseName").value = document
				.getElementById("course").options[document
				.getElementById("course").selectedIndex].text;

		document.getElementById("method").value = "addTermsCondition";
		document.termsConditionForm.submit();
	}
	function resetTermsAndConditions() {
		document.getElementById("description").value = "";
		document.getElementById("programType").selectedIndex = 0;
		document.getElementById("program").selectedIndex = 0;
		document.getElementById("course").selectedIndex = 0;
		document.getElementById("description").value = "";
		resetErrMsgs();
		resetOption("program");
		resetOption("course");
		document.getElementById("year").value = resetYear(); 
	}

	function imposeMaxLength(evt, Object) {
		var keynum = (evt.which) ? evt.which : event.keyCode;
		if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
			return true;
		}
		var MaxLen = 2000;
		return (Object.value.length < MaxLen);
	}
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "TermsCondition.do?method=activateTermsAndConditions&id="
				+ id;
	}
	function resetTCEdit() {
		document.getElementById("description").value = "";
		resetFieldAndErrMsgs();
		document.getElementById("tempyear").value = document
				.getElementById("origYear").value;
	}
	function viewTermsAndConditions(id) {
		var url = "TermsCondition.do?method=viewTermsAndConditions&id=" + id;
		myRef = window
				.open(url, "viewDescription",
						"left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	function detailedTermsCondition() {
		document.location.href = "TermsCondition.do?method=detailedTC";
	}
	
</script>
<html:form action="/TermsCondition" method="post">
	<c:choose>
		<c:when test="${conditionsOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateTermsCondition" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addTermsCondition" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="termsConditionForm" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="courseName" styleId="courseName" value="" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origYear" styleId="origYear" />
	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" /><span
				class="Bredcrumbs">&gt;&gt;  <bean:message
						key="knowledgepro.admin.termsandconditions" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>

					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">   <bean:message
						key="knowledgepro.admin.termsandconditions" /> </strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'>* Mandatory fields</span></FONT></div>
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
							<td colspan="2" align="left">
							<div align="right">
							<html:button property="" styleClass="formbutton" value="T & C Checklist"
										onclick="detailedTermsCondition()"></html:button>
							</div>
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



						<tr>
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
								<html:optionsCollection name="programTypeList"
									label="programTypeName" value="programTypeId" />
							</html:select> <span class="star"></span></td>
							<td width="25%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.prog" /></div>
							</td>
							<td width="30%" class="row-even"><span class="star">
							<html:select name="termsConditionForm" property="programId"
								styleId="program" styleClass="comboLarge"
								onchange="getCourse(this.value)">
								<html:option value="">- Select -</html:option>
								<c:choose>
									<c:when test="${conditionsOperation == 'edit'}">
										<c:if
											test="${termsConditionForm.programTypeId != null && termsConditionForm.programTypeId != ''}">
											<html:optionsCollection name="programMap" label="value"
												value="key" />
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if
											test="${termsConditionForm.programTypeId != null && termsConditionForm.programTypeId != ''}">
											<c:set var="programMap"
												value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</c:otherwise>
								</c:choose>
							</html:select> </span></td>
						</tr>

						<tr>
							<td height="25" valign="top" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.course.with.col" /></div>
							</td>
							<td height="25" valign="top" class="row-even"><html:select
								name="termsConditionForm" property="courseId" styleId="course"
								styleClass="comboLarge">
								<html:option value="">- Select -</html:option>
								<c:choose>
									<c:when test="${conditionsOperation == 'edit'}">
										<c:if
											test="${termsConditionForm.programId != null && termsConditionForm.programId != ''}">
											<html:optionsCollection name="courseMap" label="value"
												value="key" />
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if
											test="${termsConditionForm.programId != null && termsConditionForm.programId != ''}">
											<c:set var="courseMap"
												value="${baseActionForm.collectionMap['courseMap']}" />
											<c:if test="${courseMap != null}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</c:otherwise>
								</c:choose>
							</html:select></td>
							<td class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.year" /></div>
							</td>
							<td class="row-even"><span class="star"> <input
								type="hidden" id="tempyear" name="tempyear"
								value="<bean:write name="termsConditionForm" property="years"/>" />
							<html:select name="termsConditionForm" property="years"
								styleId="year" styleClass="combo">
								<html:option value="">- Select -</html:option>
								<cms:renderYear></cms:renderYear>
							</html:select> </span></td>
						</tr>
						<tr>
							<td valign="top" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.desc.with.col" /></div>
							</td>
							<td colspan="3" class="row-even"><html:textarea
								property="description" style="width: 93%" cols="80" rows="8" styleId="description"
								onkeypress="return imposeMaxLength(event,this)"></html:textarea></td>

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
										<c:when test="${conditionsOperation == 'edit'}">
											<html:button property="" styleClass="formbutton"
												value="Update" onclick="updateTermsAndConditions()"></html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Submit" onclick="addTermsAndConditions()"></html:button>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><c:choose>
										<c:when test="${conditionsOperation == 'edit'}">
											<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetTermsAndConditions()"></html:button>
										</c:otherwise>
									</c:choose></td>
								</tr>
							</table>
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


						<tr>
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.programtype" /></td>

									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.admin.program" /></div>
									</td>
									<td class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.course" /></td>
									<td class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.year" /></td>
									<td class="row-odd" align="center">
									<div align="center">View</div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>

								</tr>
								<logic:iterate id="termsconditions" name="termsConditionList"
									indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td width="4%" height="25" align="center">
									<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td width="17%" height="25" align="center"><bean:write
										name="termsconditions"
										property="courseTo.programTo.programTypeTo.programTypeName" /></td>
									<td width="18%" height="25" align="center"><bean:write
										name="termsconditions" property="courseTo.programTo.name" /></td>
									<td width="16%" align="center"><bean:write
										name="termsconditions" property="courseTo.name" /></td>
									<td width="13%" align="center"><bean:write
										name="termsconditions" property="year" />-<bean:write
										name="termsconditions" property="endYear" /></td>
									<td width="8%" height="25" align="center">
									<div align="center"><img src="images/View_icon.gif"
										width="16" height="18" style="cursor:pointer" 
										onclick="viewTermsAndConditions('<bean:write name="termsconditions" property="id"/>')"></div>
									</td>
									<td width="8%" height="25" align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor:pointer" 
										onclick="editTermsAndConditions('<bean:write name="termsconditions" property="id"/>')"></div>
									</td>
									<td width="8%" height="25" align="center">
									<div align="center"><img src="images/delete_icon.gif" style="cursor:pointer" 
										width="16" height="16"
										onclick="deleteTermsAndCondtion('<bean:write name="termsconditions" property="id"/>')">
									</div>
									</td>
									</tr>
								</logic:iterate>
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



					</table>
					<div align="center"></div>
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

<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
</script>