<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
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

	function editCourse(courseId, programTypeId, progId) {
		document.location.href = "CourseEntry.do?method=editCourse&courseId="
				+ courseId;
	}

	function addCourse() {
		document.getElementById("method").value = "addCourse";
		document.courseForm.submit();
	}

	function updateCourse() {
		document.getElementById("method").value = "updateCourse";
		resetErrMsgs();
		document.courseForm.submit();
	}
	function deleteCourse(courseId) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "CourseEntry.do?method=deleteCourse&courseId="
					+ courseId ;z
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
		if (field.value == "0.0"){
			field.value = "";
		}
		if(field.value == "0"){
			field.value = "";
		}
	}
	function checkForEmpty(field) {
		if (field.value.length == 0)
			field.value = "0.0";
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
	function resetCourse() {
		document.getElementById("courseCode").value = "";
		document.getElementById("courseName").value = "";
		document.getElementById("maxIntake").value = "";
		document.getElementById("payCode").value = "";
		document.getElementById("amount").value = "";
		document.getElementById("commencementdate").value = "";
		document.getElementById("applicationFees").value = "";
		document.getElementById("currencyList").value = "";
		document.getElementById("currencyId").value = "";
		document.getElementById("program").selectedIndex = 0;
		document.getElementById("programTypeId").selectedIndex = 0;
		document.getElementById("isAutonomous_2").checked = true;
		document.getElementById("isWorkExperienceRequired_2").checked = true;
		document.getElementById("isDetailMarkPresent_2").checked = true;
		document.getElementById("isWorkExpMandatory_2").checked = true;
		document.getElementById("isAppearInOnline_1").checked = true;
		document.getElementById("isApplicationProcessSms_1").checked = true;
		document.getElementById("onlyForApplication_2").checked = true;
		document.getElementById("bankIncludeSection_1").checked = true;
		document.getElementById("dateTime").value = "";
		document.getElementById("generalFee").value = "";
		document.getElementById("casteFee").value = "";
		
		resetOption("program");
		
				
		
		var size = parseInt(document.getElementById("length").value);
		for ( var count = 0; count <= size - 1; count++) {
			document.getElementById("seatAllocationList[" + count
					+ "].noofSeats").value = "0";

		}
		document.getElementById("total").value = "0";
		resetErrMsgs();

//		resetFieldAndErrMsgs();
	}
	function reActivate() {
		document.location.href = "CourseEntry.do?method=activateCourse";
	}
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
	function assignDept(id) {
		document.location.href = "CourseEntry.do?method=getDepartments&courseid="+id;
	}
	
	
</script>

<html:form action="CourseEntry" method="post">
	<html:hidden property="formName" value="courseForm" />
	<html:hidden property="pageType" value="5" />
	<html:hidden property="courseId" styleId="courseId" name="courseForm" />
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
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.courseentry" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.courseentry" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="452" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
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

						<tr>

							<td width="20%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.program.Type" /></div>
							</td>
							<td width="26%" height="25" class="row-even"><span
								class="star"> <html:select property="programTypeId"
								styleClass="comboLarge" styleId="programTypeId"
								onchange="getPrograms(this.value)">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="programTypeList"
									label="programTypeName" value="programTypeId" />



							</html:select></span></td>
							<td width="20%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.prog" /></div>
							</td>

							<td width="26%" class="row-even"><span class="star">
							<html:select property="programId" styleClass="comboLarge"
								styleId="program">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<c:choose>
									<c:when test="${courseOperation == 'edit'}">
									<c:if test="${programMap != null}">
										<html:optionsCollection name="programMap" label="value"
											value="key" />
									</c:if>
									</c:when>
									<c:otherwise>
										<c:if
											test="${courseForm.programTypeId != null && courseForm.programTypeId != ''}">
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
							<td height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.course.code" /></div>
							</td>
							<td height="25" class="row-even"><span class="star">
							<html:text property="courseCode" styleClass="TextBox"
								styleId="courseCode" size="16" maxlength="15" name="courseForm" />
							</span></td>
							<td class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.course.name" /></div>
							</td>
							<td class="row-even"><span class="star"> <html:text
								property="courseName" styleClass="TextBox" styleId="courseName"
								size="30" maxlength="100" name="courseForm" /></span></td>
						</tr>

						<tr>
							<%-- <td height="25" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.admin.autonomous" /></div>
							</td>
							<td height="25" class="row-even"><html:radio
								property="isAutonomous" value="Yes" styleId="isAutonomous_1">
								<bean:message key="knowledgepro.yes" />
							</html:radio> <c:choose>
								<c:when test="${courseOperation == 'edit'}">
									<html:radio property="isAutonomous" value="No" styleId="isAutonomous_2"></html:radio>
								</c:when>
								<c:otherwise>
									<input type="radio" name="isAutonomous" value="No" id ="isAutonomous_2" checked>
								</c:otherwise>
							</c:choose> <bean:message key="knowledgepro.no" /></td>--%>
							<td class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.max.intake" /></div>
							</td>
							<td class="row-even"><span class="star"> <html:text
								property="maxIntake" styleClass="TextBox" styleId="maxIntake"
								name="courseForm" size="16" maxlength="10"
								onkeypress="return isNumberKey(event)"
								onblur="checkNumber(this)" /></span></td>
							<td class="row-odd">
							<div align="right"><span class="Mandatory"></span><bean:message
								key="knowledgepro.admin.coursename.certificate" /></div>
							</td>
							<td class="row-even"><span class="star"> <html:text
								property="certificateCourseName" styleClass="TextBox" styleId="certificateCourse"
								name="courseForm" size="35" maxlength="149" 
								 /></span></td>
						</tr>
							<tr>
							<td height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.course.PayCode.col"/></div>
							</td>
							<td height="25" class="row-even"><html:text
								property="payCode" styleClass="TextBox" styleId="payCode"
								size="16" maxlength="10" name="courseForm" /></td>
							<td class="row-odd">
							<div align="right"><span class="Mandatory"></span><bean:message
								key="knowledgepro.admin.marksCard" /></div>
							</td>
							<td class="row-even"><span class="star"> <html:text
								property="courseMarksCard" styleClass="TextBox" styleId="courseMarksCard"
								name="courseForm" size="35" maxlength="149" 
								 /></span></td>
						</tr>
						<tr>
							<td class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.fee"/></div></td>
							<td class="row-even"><span class="star"> <html:text
								property="amount" styleClass="TextBox" styleId="amount"
								size="16" maxlength="10" name="courseForm"
								onkeypress="return isDecimalNumberKey(this.value,event)"
								onkeyup="onlyTwoFractions(this,event)"
								onfocus="clearField(this)" onblur="checkForEmpty(this)" /></span></td>
							<td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.fee"/></div>
							<div align="right">(International)</div></td>
							<td class="row-even">
							<span class="Mandatory">*</span>
							<span class="star"><html:select property="currencyId" styleClass="combo" styleId="currencyId"><html:option value=""><bean:message key="knowledgepro.admin.select" />
				            </html:option><html:optionsCollection name="currencyList" label="currencyCode" value="id" /></html:select></span>
				          	<span class="Mandatory">*</span>						
							<span class="star"> <html:text
								property="intApplicationFees" styleClass="TextBox" styleId="intApplicationFees"
								size="15" maxlength="10" name="courseForm"
								onkeypress="return isDecimalNumberKey(this.value,event)"
								onkeyup="onlyTwoFractions(this,event)"
								onfocus="clearField(this)" onblur="checkForEmpty(this)" /></span>
							</td>
						</tr>
						<tr>
							<td class="row-odd" width="25%">
							<div align="right"><bean:message key="knowledgepro.admin.course.work.exp.col"/></div>
							</td>
							<td class="row-even"><span class="star"> <html:radio
								property="isWorkExperienceRequired" value="Yes" styleId="isWorkExperienceRequired_1"></html:radio> <bean:message
								key="knowledgepro.yes" /> <c:choose>
								<c:when test="${courseOperation == 'edit'}">
									<html:radio property="isWorkExperienceRequired" value="No" styleId="isWorkExperienceRequired_2"></html:radio>
								</c:when>
								<c:otherwise>
									<input type="radio" name="isWorkExperienceRequired" value="No"
										id = "isWorkExperienceRequired_2" checked="checked">
								</c:otherwise>
							</c:choose> <bean:message key="knowledgepro.no" /> </span></td>
							<td class="row-odd" width="25%">
							<div align="right"><bean:message key="knowledgepro.admin.course.detl.mark.col"/></div>
							</td>
							<td class="row-even"><span class="star"> <html:radio
								property="isDetailMarkPresent" value="Yes" styleId="isDetailMarkPresent_1"></html:radio> <bean:message
								key="knowledgepro.yes" /> <c:choose>
								<c:when test="${courseOperation == 'edit'}">
									<html:radio property="isDetailMarkPresent" value="No" styleId="isDetailMarkPresent_2"></html:radio>
								</c:when>
								<c:otherwise>
									<input type="radio" name="isDetailMarkPresent" value="No"
										id = "isDetailMarkPresent_2" checked="checked">
								</c:otherwise>
							</c:choose> <bean:message key="knowledgepro.no" /> </span></td>
						</tr>
						<tr>
							<td class="row-odd" width="25%">
							<div align="right"><bean:message key="knowledgepro.admin.course.exp.mandatory"/></div>
							</td>
							<td class="row-even">
							<html:radio property="isWorkExpMandatory" styleId="isWorkExpMandatory_1" value="true">Yes</html:radio>
							<html:radio property="isWorkExpMandatory" styleId="isWorkExpMandatory_2" value="false">No</html:radio>
							</td>
							<td class="row-odd" width="25%">
							<div align="right"><bean:message key="knowledgepro.admin.course.appear.online"/>
							 </div>
							</td>
							<td class="row-even">
							<html:radio property="isAppearInOnline" styleId="isAppearInOnline_1" value="true">Yes</html:radio>
							<html:radio property="isAppearInOnline" styleId="isAppearInOnline_2" value="false">No</html:radio>
							</td>
						</tr>
							<tr>
							<td class="row-odd" width="25%">
							<div align="right"><bean:message key="knowledgepro.admin.course.application.process.sms"/></div>
							</td>
							<td class="row-even">
							<html:radio property="isApplicationProcessSms" styleId="isApplicationProcessSms_1" value="true">Yes</html:radio>
							<html:radio property="isApplicationProcessSms" styleId="isApplicationProcessSms_2" value="false">No</html:radio>
							</td>
							<td class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.admin.course.onlyForApplication"/></div></td>
							<td class="row-even">
							<html:radio property="onlyForApplication" styleId="onlyForApplication_1" value="true">Yes</html:radio>
							<html:radio property="onlyForApplication" styleId="onlyForApplication_2" value="false">No</html:radio> 
							</td>
						</tr>
						<tr>
							<td height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.bankName"/></div>
							<div align="right">(For Smart Card)</div>
							</td>
							<td height="25" class="row-even"><html:text
								property="bankName" styleClass="TextBox" styleId="bankName"
								size="16" maxlength="35" name="courseForm" /></td>
							<td class="row-odd">
							<div align="right"><span class="Mandatory"></span><bean:message
								key="knowledgepro.admin.bankName.bankNameFull" /></div>
								<div align="right">(For Smart Card)</div>
							</td>
							<td class="row-even"><span class="star"> <html:text
								property="bankNameFull" styleClass="TextBox" styleId="bankNameFull"
								name="courseForm" size="25" maxlength="25" 
								 /></span></td>
						</tr>
						<tr>
							<td height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.bankIncludeSection"/></div>
							<div align="right">(For Smart Card)</div>
							</td>
							<td class="row-even">
							<html:radio property="bankIncludeSection" styleId="bankIncludeSection_1" value="true">Yes</html:radio>
							<html:radio property="bankIncludeSection" styleId="bankIncludeSection_2" value="false">No</html:radio>
							</td>
							<td class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.commencementdate"/></div></td>
	                           <td class="row-even" align="left" colspan="9">
	                           		<html:text styleId="commencementDate" property="commencementDate" readonly="true" styleClass="TextBox"/>
										<script	language="JavaScript">
											new tcal( {
												// form name
												'formname' :'courseForm',
												// input name
												'controlname' :'commencementDate'
											});
										</script>
	                           </td>
						</tr>
						<tr>
							 <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.exam.room.availability.campus" />:</div></td>
							 <td width="25%" height="25" class="row-even">
								 <html:select property="locationId" styleId="locationId">
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="workLocationMap" name="courseForm">
						   							<html:optionsCollection property="workLocationMap" label="value" value="key"/>
						   						</logic:notEmpty>
						   					</html:select>
							 </td>
							 <td class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.course.noofmidsem.attempts" />:</div></td>
							  <td class="row-even">
							  <html:select property="noOfMidSemAttempts" styleId="noOfMidSemAttempts" style="width:50px;">
							  <html:option value="1">1</html:option>
							  <html:option value="2">2</html:option>
							  <html:option value="3">3</html:option>
							  <html:option value="4">4</html:option>
							  <html:option value="5">5</html:option>
							  </html:select>
							  </td>
						</tr>
												<tr>
							<td align="right" height="25" class="row-odd">
							Admission Date & Time :
							</td>
							<td height="25" class="row-even"><span class="star">
							<html:text property="dateTime" styleClass="TextBox"
								styleId="dateTime" size="30" maxlength="50" name="courseForm" />
							</span></td>
							<td class="row-odd">
							<div align="right">General Admission Fee</div>
							</td>
							<td class="row-even"><span class="star"> <html:text
								property="generalFee" styleClass="TextBox" styleId="generalFee"
								size="15" maxlength="50" name="courseForm" /></span></td>
						</tr>
						
						<tr>
							<td height="25" class="row-odd" align="right">
							Caste Admission Fee :
							</td>
							<td height="25" class="row-even"><span class="star">
							<html:text property="casteFee" styleClass="TextBox"
								styleId="casteFee" size="15" maxlength="50" name="courseForm" />
							</span></td>
							<td height="25" class="row-odd" align="right">
								<span class="Mandatory">*</span><bean:message key="knowledgepro.fee.aidedUnaided"/> :
							</td>
							<td height="25" class="row-even"><span class="star">
								<html:radio property="aided" value="true">Aided</html:radio>
								<html:radio property="aided" value="false">Un-aided</html:radio>
							</span></td>
						</tr>
						
						<tr>
							<td height="20" colspan="4" valign="bottom"><span
								class="heading"><bean:message
								key="knowledgepro.admin.seat.allocation" /></span></td>
						</tr>
						<tr>
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.admittedthrough" /></td>

									<td class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.noof.seats" /></td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:iterate name="courseForm" property="seatAllocationList"
									id="seatAllocationList"
									type="com.kp.cms.to.admin.SeatAllocationTO" indexId="adm">
									<c:choose>
										<c:when test="${adm%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td width="8%" height="25">

									<div align="center"><c:out value="${adm+1}" /></div>
									</td>
									<td width="38%" height="25" align="center"><bean:write
										name="seatAllocationList" property="admittedThroughTO.name" /></td>
									<td width="54%" align="center"><span class="star"> <input
										type="hidden"
										name="seatAllocationList[<c:out value="${adm}"/>].admittedThroughId"
										value="<bean:write name='seatAllocationList' property="admittedThroughId"/>">
									<input type="text"
										name="seatAllocationList[<c:out value="${adm}"/>].noofSeats"
										id="seatAllocationList[<c:out value="${adm}"/>].noofSeats"
										value="<bean:write name='seatAllocationList' property="noofSeats"/>"
										size="11" maxlength="11" onblur="calladd()"
										onkeypress="return isNumberKey(event)" onfocus="clearField(this)"></td>
									</tr>
									<c:set var="temp" value="${temp+1}" />
								</logic:iterate>
								<tr class="row-even">
									<td height="25" class="row-odd">&nbsp;</td>
									<td height="25" class="row-odd">
									<div align="right"><strong> <bean:message
										key="knowledgepro.admin.total" /> </strong></div>
									</td>
									<td class="row-odd" align="center"><span class="star"> <input
										type="hidden" name="length" id="length"
										value="<c:out value="${temp}"/>" /> <html:text size="11"
										property="total" name="courseForm" styleId="total"
										readonly="true"></html:text> </span></td>
								</tr>

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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${courseOperation == 'edit'}">
											<html:button property="" styleClass="formbutton"
												value="Update" onclick="updateCourse()"></html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Submit" onclick="addCourse()"></html:button>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><c:choose>
										<c:when test="${courseOperation == 'edit'}">
											<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetCourse()"></html:button>
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

									<td width="5%" height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td width="9%" height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.programtype" /></td>
									<td width="9%" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.program" /></td>
									<td width="4%" class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.admin.coursename" /></div>
									</td>
									<td width="7%" class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.admin.maxIntake" /></div>
									</td>

									<td width="4%" class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.admin.Autonomus.view" /></div>
									</td>


									<td width="7%" class="row-odd" align="center"> 
									<div align="center"><bean:message key="knowledgepro.admin.course.work.experiance.req"/></div>
									</td>
									<td width="7%" class="row-odd" align="center"> 
									<div align="center"><bean:message key="knowledgepro.admin.course.detl.mark.col"/></div>
									</td>
									<td width="7%" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.admin.course.PayCode"/></div>
									</td>
									<td width="7%" class="row-odd" align="center"> 
									<div align="center"><bean:message key="knowledgepro.applicationform.fees"/></div>
									</td>
                                   <td width="8%" class="row-odd" align="center"> 
									<div align="center"><bean:message key="knowledgepro.applicationform.fees"/></div>
									<div align="center">(International)</div>
									</td>

									<!--<td width="18%" class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.admin.admittedthrough" /></div>
									</td>
									<td width="7%" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.noofseats" /></td>
									-->
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.assign.dept" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>

								<c:set var="slnocount" value="0" />
								<logic:iterate name="courseList" id="course"
									type="com.kp.cms.to.admin.CourseTO" indexId="count">

									<c:set var="slnocount" value="${slnocount + 1 }" />
									<c:choose>
										<c:when test="${slnocount%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>

									<td width="5%" height="25" align="center">
									<div align="center"><c:out value="${count+1}" /></div>
									</td>
									<td width="9%" height="25" align="center"><bean:write
										name="course"
										property="programTo.programTypeTo.programTypeName" /></td>
									<td width="9%" align="center"><bean:write name="course"
										property="programTo.name" /></td>
									<td width="14%" align="center"><bean:write name="course"
										property="name" /></td>
									<td width="7%" align="center"><bean:write name="course"
										property="maxInTake" /></td>
									<td width="4%" align="center"><bean:write name="course"
										property="isAutonomous" /></td>
									<td width="7%" align="center"><bean:write name="course"
										property="isWorkExperienceRequired" /></td>
									<td width="7%" align="center"><bean:write name="course"
										property="isDetailMarkPrint" /></td>
									<td width="7%" align="center"><bean:write name="course"
										property="payCode" /></td>
									<td width="10%" align="center"><bean:write name="course"
										property="amount" /></td>
									<td width="10%" align="center"><bean:write name="course"
										property="intApplicationFees" /></td>
									<!--<td>
									<table width="100%" cellspacing="1" cellpadding="2">
										<logic:iterate id="seatAllocate" name="course"
											property="seatAllocation"
											type="com.kp.cms.to.admin.SeatAllocationTO"
											indexId="alloccount">
											<c:choose>
												<c:when test="${slnocount%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td width="18%" align="center"><bean:write
												name="seatAllocate" property="admittedThroughTO.name" /></td>
											</tr>
										</logic:iterate>
										</tr>
									</table>
									</td>
									<td>
									<table width="100%" cellspacing="1" cellpadding="2">
										<logic:iterate id="seatAllocate" name="course"
											property="seatAllocation"
											type="com.kp.cms.to.admin.SeatAllocationTO"
											indexId="alloccount">
											<c:choose>
												<c:when test="${slnocount%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td width="12%" align="center"><bean:write
												name="seatAllocate" property="noofSeats" /></td>
											</tr>
										</logic:iterate>
										</tr>
									</table>
									</td>
									-->
									<td width="5%" height="25" align="center">
									<div align="center"><img src="images/accept.png"
										width="25" height="25"  style="cursor:pointer" 
										onclick="assignDept('<bean:write name="course" property="id"/>')">
									</div>
									</td>
									<td width="5%" height="25" align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="editCourse('<bean:write name="course" property="id"/>','<bean:write name="course" property="programTo.programTypeTo.programTypeId"/>'), '<bean:write name="course" property="programTo.id"/>'">
									</div>
									</td>
									<td width="5%" height="25" align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer" 
										onclick="deleteCourse('<bean:write name="course" property="id"/>')">
									</div>
									</td>

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