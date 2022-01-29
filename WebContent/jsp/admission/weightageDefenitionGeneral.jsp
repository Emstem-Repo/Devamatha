<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<SCRIPT type="text/javascript">
	function cancelAction() {
		document.location.href = "WeightageDefenition.do?method=cancelWeightageEntry";
	}
	function updateSelection() {
		document.getElementById("selectionType").value = document
				.getElementById("weightageType").options[document
				.getElementById("weightageType").selectedIndex].value
		var selectionValue = document.getElementById("selectionType").value;
		if (selectionValue != "") {
			document.location.href = "WeightageDefenition.do?method=selectGeneralWeightageDefenition&selectionType="
					+ selectionValue;

		} else {
			document.location.href = "WeightageDefenition.do?method=initGeneralWeightageDefenition";

		}
	}

	function checkForEmpty(field) {

		if (field.value.length == 0) {
			field.value = "0";
		}
	}

	function isValidNumber(field) {
		if (isNaN(field.value)) {
			field.value = "0";
		}
	}
</SCRIPT>

<html:form action="/WeightageDefenition" focus="programType">
	<html:hidden property="method" styleId="method"
		value="submitGeneralWeightageDefenition" />
	<html:hidden property="pageType" value="3" />
	<html:hidden property="formName" value="weightageDefenitionForm" />
	<html:hidden property="selectionType" styleId="selectionType" />
	<table width="98%" border="0">

		<c:set var="selectedValue" value="${selectionType}" scope="request" />
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif"></td>
					<td colspan="2" background="images/Tcenter.gif" class="body">
					<div align="left"><span class="Bredcrumbs"><a
						href="Admission_Intro.html" class="boxheader"><bean:message
						key="knowledgepro.admission.generalweightage" /> </a></span></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="46" colspan="2" class="heading"><img
						src="images/Weighage_General_Tab.jpg" border="0" usemap="#Map">
					<map name="Map">
						<area shape="rect" coords="7,5,150,30"
							href="WeightageDefenition.do?method=initWeightageDefenition">
						<area shape="rect" coords="274,5,386,29"
							href="WeightageDefenition.do?method=initEducationWeightageDefenition">
						<area shape="rect" coords="391,6,503,30"
							href="WeightageDefenition.do?method=initInterviewWeightageDefenition">
						<area shape="rect" coords="157,6,265,28"
							href="WeightageDefenition.do?method=initPrerequisiteWeightageDefenition">
					</map></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="52" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6" class="body" align="left">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
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
									<td width="45%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.weightagetype" />:</div>
									</td>

									<td width="55%" height="25" class="row-even"><html:select
										property="weightageType" styleId="weightageType"
										onchange="updateSelection()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="weightageDefenitionForm"
											property="weightageTypeMap" label="value" value="key" />

									</html:select></td>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="52" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td height="91" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<logic:equal name="weightageDefenitionForm"
									property="selectionType" value="1">
									<c:set var="temp" value="0" />
									<nested:iterate name="weightageDefenitionForm"
										property="ruralUrbanWeightageList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td height="25" class="row-even">
													<div align="center"><nested:write
														property="ruralUrban" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><nested:message key="ruralUrban" />
													</div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:write
														property='weightagePercentage' /> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:otherwise>
										</c:choose>


									</nested:iterate>
								</logic:equal>
								<logic:equal name="weightageDefenitionForm"
									property="selectionType" value="2">
									<c:set var="temp" value="0" />
									<nested:iterate name="weightageDefenitionForm"
										property="casteWeightageList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td height="25" class="row-even">
													<div align="center"><nested:write
														property="casteName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><nested:message key="casteName" />
													</div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:write
														property='weightagePercentage' /> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:equal>

								<logic:equal name="weightageDefenitionForm"
									property="selectionType" value="3">
									<c:set var="temp" value="0" />
									<nested:iterate name="weightageDefenitionForm"
										property="religionSectionWeightageList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td height="25" class="row-even">
													<div align="center"><nested:write
														property="religionSectionName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><nested:message
														key="religionSectionName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:write
														property='weightagePercentage' /> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:equal>

								<logic:equal name="weightageDefenitionForm"
									property="selectionType" value="4">

									<c:set var="temp" value="0" />
									<nested:iterate name="weightageDefenitionForm"
										property="countryWeightageList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td height="25" class="row-even">
													<div align="center"><nested:write
														property="countryName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><nested:message key="countryName" />
													</div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:write
														property='weightagePercentage' /> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:equal>

								<logic:equal name="weightageDefenitionForm"
									property="selectionType" value="5">
									<c:set var="temp" value="0" />
									<nested:iterate name="weightageDefenitionForm"
										property="genderWeightageList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td height="25" class="row-even">
													<div align="center"><nested:write property="getnder" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><nested:message key="getnder" />
													</div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:write
														property='weightagePercentage' /> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:equal>
								<logic:equal name="weightageDefenitionForm"
									property="selectionType" value="6">

									<c:set var="temp" value="0" />
									<nested:iterate name="weightageDefenitionForm"
										property="instituteWeightageList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td height="25" class="row-even">
													<div align="center"><nested:write
														property="instituteName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><nested:message
														key="instituteName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:write
														property='weightagePercentage' /> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:equal>

								<logic:equal name="weightageDefenitionForm"
									property="selectionType" value="7">
									<c:set var="temp" value="0" />
									<nested:iterate name="weightageDefenitionForm"
										property="nationalityWeightageList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td height="25" class="row-even">
													<div align="center"><nested:write
														property="nationalityName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><nested:message
														key="nationalityName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:write
														property='weightagePercentage' /> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:equal>
								<logic:equal name="weightageDefenitionForm"
									property="selectionType" value="8">

									<c:set var="temp" value="0" />
									<nested:iterate name="weightageDefenitionForm"
										property="religionWeightageList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td height="25" class="row-even">
													<div align="center"><nested:write
														property="religionName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><nested:message
														key="religionName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:write
														property='weightagePercentage' /> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:equal>
								<logic:equal name="weightageDefenitionForm"
									property="selectionType" value="9">
									<c:set var="temp" value="0" />
									<nested:iterate name="weightageDefenitionForm"
										property="residentCategoryWeightageList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td height="25" class="row-even">
													<div align="center"><nested:write
														property="reseidentCategoryName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><nested:message
														key="reseidentCategoryName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:write
														property='weightagePercentage' /> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:equal>
								<logic:equal name="weightageDefenitionForm"
									property="selectionType" value="10">


									<c:set var="temp" value="0" />
									<nested:iterate name="weightageDefenitionForm"
										property="universityWeightageList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td height="25" class="row-even">
													<div align="center"><nested:write
														property="universityName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>

											</c:when>
											<c:otherwise>
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><nested:message
														key="universityName" /></div>
													</td>
													<td height="25" class="row-even"><span
														class="bodytext"> <nested:write
														property='weightagePercentage' /> <nested:text
														property="weightagePercentage" styleClass="TextBox"
														size="17" maxlength="6"
														onkeypress="return isDecimalNumberKey(this.value,event)"
														onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
													</span></td>
												</tr>
											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:equal>
        <logic:equal name="weightageDefenitionForm" property="selectionType" value="11">
		<c:set var="temp" value="0" />
		<nested:iterate id="prevQualification" name="weightageDefenitionForm"
			property="previousQualificationWeightageList" indexId="count">
			<c:choose>
				<c:when test="${temp == 0}">
					<tr>
					   <td height="15" class="row-odd">
					     <bean:write name="prevQualification" property="docTypeName"/>    
					   </td>
					   
						<td height="15" class="row-even">
						<div align="center"><nested:write
							property="examName" /></div>
						</td>
						<td height="15" class="row-even"><span
							class="bodytext"> <nested:text
							property="weightagePercentage" styleClass="TextBox"
							size="17" maxlength="6"
							onkeypress="return isDecimalNumberKey(this.value,event)"
							onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
						</span></td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
					    <td height="15" class="row-odd">
					    <bean:write name="prevQualification" property="docTypeName"/>	
					    </td>
						<td height="15" class="row-even">
						<div align="center"><nested:message
							key="examName" /></div>
						</td>
						<td height="15" class="row-even"><span
							class="bodytext"> <nested:write
							property='weightagePercentage' /> <nested:text
							property="weightagePercentage" styleClass="TextBox"
							size="17" maxlength="6"
							onkeypress="return isDecimalNumberKey(this.value,event)"
							onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
						</span></td>
					</tr>

				</c:otherwise>
			</c:choose>
		</nested:iterate>
	</logic:equal>
    <logic:equal name="weightageDefenitionForm"
	property="selectionType" value="12">
	 <c:set var="temp" value="0" />
	 <nested:iterate name="weightageDefenitionForm"
		property="workExperienceWeightageList" indexId="count">
		<c:choose>
			<c:when test="${temp == 0}">
				<tr>
					<td height="25" class="row-even">
					<div align="center"><nested:write
						property="experienceName" /></div>
					</td>
					<td height="25" class="row-even"><span
						class="bodytext"> <nested:text
						property="weightagePercentage" styleClass="TextBox"
						size="17" maxlength="6"
						onkeypress="return isDecimalNumberKey(this.value,event)"
						onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
					</span></td>
				</tr>

			</c:when>
			   <c:otherwise>
				<tr>
					<td height="25" class="row-odd">
					<div align="center"><nested:message key="rural" />
					</div>
					</td>
					<td height="25" class="row-even"><span
						class="bodytext"> <nested:write
						property='weightagePercentage' /> <nested:text
						property="weightagePercentage" styleClass="TextBox"
						size="17" maxlength="6"
						onkeypress="return isDecimalNumberKey(this.value,event)"
						onblur="checkForEmpty(this);isValidNumber(this)"></nested:text>
					</span></td>
				    </tr>
			     </c:otherwise>
		       </c:choose>
	         </nested:iterate>
			</logic:equal>

							</table>
							</td>
							<td background="images/right.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.submit" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:button property=""
								styleClass="formbutton" value="Cancel" onclick="cancelAction()"></html:button></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td colspan="2" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<SCRIPT type="text/javascript">
   hook=false;
	var selection = document.getElementById("selectionType").value;

	if (selection != "") {

		document.getElementById("weightageType").value = selection;
	} else {
		document.getElementById("weightageType").value = ""
	}
</SCRIPT>