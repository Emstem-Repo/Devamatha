<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.kp.cms.constants.KPPropertiesConfiguration"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<link rel="stylesheet"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link rel="stylesheet" type="text/css" href="css/sdmenu.css" />
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript" src="jquery/collaps/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css"
	href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>

<script language="JavaScript" src="js/admission/admissionform.js"></script>

<link rel="stylesheet" href="css/admission/css/stylesheet-pure-css.css" />
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript"
	src="js/admission/onlineApplicationFunctions.js"></script>
<script language="JavaScript" src="js/admission/personalDataEdit.js"></script>
<script language="JavaScript"
	src="js/admission/OnlineDetailsAppCreation.js"></script>
<script type="text/javascript">
	function funcOtherShowHide(id, destid) {
		var selectedVal = document.getElementById(id).value;
		if (selectedVal == "Other") {
			showOther(id, destid);
		} else {
			hideOther(id, destid);
		}
	}
	function isNumberKey(evt) {

		var charCode = (evt.which) ? evt.which : event.keyCode;
		if ((evt.ctrlKey)) {
			return true;
		}
		if ((charCode > 47 && charCode < 58) || charCode == 8) {
			return true;
		}
		return false;
	}
	function goToHomePage() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}

	function submitform() {
		  document.getElementById("myForm").submit();
		}
</script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
<style>
body {
	font-size: 150.5%;
}

td {
	padding: 7px;
}
<!--
-->
</style>
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setHeader("Expires", "0"); // Proxies.
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/StudentLoginAction" styleId="myForm">
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="checkReligionId" styleId="checkReligionId" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="dateExpired" name="loginform"
		styleId="dateExpired" />
	<html:hidden property="focusValue" styleId="focusValue"
		name="loginform" />
	<div class="pageheader">
		<div class="media">
			<div class="Container">
				<ul class="breadcrumb">
					<li><a href="#"><i class="glyphicon glyphicon-log-in"></i></a></li>
					<li><a href="#">Student Login</a></li>
					<li>Personal Details</li>
				</ul>

			</div>
		</div>
		<!-- media -->
	</div>
	<!-- pageheader -->


	<div class="panel-body">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h1 class="panel-title">Personal Details</h1>
			</div>
		</div>
		<div class="col-sm-12"
			style="border-style: solid; border-color: #29953b;">
			<div class="form-group">
				<div class="col-sm-4">


					<div id="errorMessage">
						<FONT color="red"> <html:errors />
						</FONT> <FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages>
						</FONT>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12">
					<tr>
						<td>
							<table width="100%" border="0" cellpadding="4" align="center"
								class="subtable">

								<tr height="15" align="center">
									<td colspan="4"><u><b>STUDENT DETAILS</b></u></td>
								</tr>

								<tr height="15" align="center">
									<td colspan="4"></td>
								</tr>
								<tr height="15" align="center">
									<td colspan="4"></td>
								</tr>

								<tr>
									<td colspan="2" width="50%" align="right"><img
										src='<%=request.getContextPath()+"/PhotoServlet?"%>' alt=""
										onerror='onImgError(this);' onLoad='setDefaultImage(this);'
										height="100" width="100" align="top" /></td>
									<%--  <td colspan="2" width="50%" valign="middle" align="left"><html:file property="studentPhoto" styleId="editDocument"></html:file></td> --%>
								</tr>
								<tr height="15" align="center">
									<td colspan="4"></td>
								</tr>
								<tr height="15" align="center">
									<td colspan="4"></td>
								</tr>
								<tr>

									<td width="25%" align="right"><bean:message
											key="knowledgepro.applicationform.candidateName" /><span
										class="Mandatory">*</span></td>
									<td width="25%" valign="top"><nested:text readonly="false"
											property="personalData.firstName" styleId="firstNameId"
											name="loginform" maxlength="90" styleClass="textbox"
											disabled="true"></nested:text></td>


									<td width="25%" align="right"><bean:message
											key="admissionForm.studentinfo.bloodgroup.label" /><span
										class="Mandatory">*</span></td>
									<td width="25%"><input type="hidden" id="BGType"
										name="BGType"
										value='<bean:write name="loginform" property="bloodGroup"/>' />
										<nested:select property="personalData.bloodGroup" disabled="true"
															name="loginform" styleClass="dropdown" styleId="bgType">
															<html:option value="">
																<bean:message key="knowledgepro.admin.select" />
															</html:option>
															<html:option value="O+">
																<bean:message
																	key="knowledgepro.admission.report.opositive" />
															</html:option>
															<html:option value="A+">
																<bean:message
																	key="knowledgepro.admission.report.apositive" />
															</html:option>
															<html:option value="B+">
																<bean:message
																	key="knowledgepro.admission.report.bpositive" />
															</html:option>
															<html:option value="AB+">
																<bean:message
																	key="knowledgepro.admission.report.abpositive" />
															</html:option>
															<html:option value="O-">
																<bean:message
																	key="knowledgepro.admission.report.onegitive" />
															</html:option>
															<html:option value="A-">
																<bean:message
																	key="knowledgepro.admission.report.anegitive" />
															</html:option>
															<html:option value="B-">
																<bean:message
																	key="knowledgepro.admission.report.bnegitive" />
															</html:option>
															<html:option value="AB-">
																<bean:message
																	key="knowledgepro.admission.report.abnegitive" />
															</html:option>
															<html:option value="NOT KNOWN">
																<bean:message
																	key="knowledgepro.admission.report.unknown" />
															</html:option>
														</nested:select></td>
								</tr>
								<tr>
									<td align="right"><bean:message
											key="admissionForm.studentinfo.dob.label" /> <bean:message
											key="admissionForm.application.dateformat.label" /><span
										class="Mandatory">*</span></td>
									<td><nested:text readonly="false" name="loginform" disabled="true"
											property="personalData.dob" styleId="dateOfBirth"
											maxlength="11" styleClass="textbox"></nested:text></td>
									<td align="right"><bean:message
											key="admissionForm.studentinfo.residentcatg.label2" /><span
										class="Mandatory">*</span></td>
									<td><div align="left">
											<input type="hidden" id="tempResidentCategory"
												value="<nested:write property="personalData.residentCategory" name="loginform" />">
											<nested:select property="personalData.residentCategory" disabled="true"
												name="loginform" styleClass="dropdown"
												styleId="residentCategory" >
												<option value=""><bean:message
														key="knowledgepro.admin.select" /></option>
												<nested:optionsCollection name="loginform"
													property="residentTypes" label="name" value="id" />
											</nested:select>

										</div></td>
								</tr>
								<tr>
									<td align="right"><bean:message
											key="admissionForm.studentinfo.sex.label" /><span
										class="Mandatory">*</span></td>
									<td>
										<fieldset style="border: 0px">

											<nested:radio disabled="true" property="personalData.gender"
												styleId="MALE" name="loginform" value="MALE"></nested:radio>
											<label for="MALE"><span><span></span></span> <bean:message
													key="admissionForm.studentinfo.sex.male.text" /></label>


											<nested:radio disabled="true" property="personalData.gender"
												name="loginform" styleId="FEMALE" value="FEMALE"></nested:radio>
											<label for="FEMALE"><span><span></span></span> <bean:message
													key="admissionForm.studentinfo.sex.female.text" /></label>
										</fieldset>
										<fieldset style="border: 0px">
											<nested:radio disabled="true" property="personalData.gender"
												name="loginform" styleId="Trans Gender" value="TRANS GENDER"></nested:radio>
											<label for="Trans Gender"><span><span></span></span>Transgender</label>
										</fieldset>
									</td>

									<td align="right"><bean:message
											key="admissionForm.studentinfo.nationality.label" /><span
										class="Mandatory">*</span></td>
									<td>
										<div align="left">
											<input type="hidden" id="nationalityhidden"
												name="nationality"
												value="<bean:write name="loginform" property="personalData.nationality"/>" />

											<nested:select property="personalData.nationality" disabled="true" 
												styleClass="dropdown" styleId="nationality" name="loginform">
												<option value=""><bean:message
														key="knowledgepro.admin.select" /></option>
												<%
																	String selected = "";
																%>
												<logic:iterate id="option" property="nationalities"
													name="loginform">
													<logic:equal
														value='<%=String.valueOf(CMSConstants.KP_DEFAULT_NATIONALITY_ID)%>'
														name="option" property="id">
														<%
																			selected = "selected";
																		%>
													</logic:equal>
													<logic:notEqual
														value='<%=String.valueOf(CMSConstants.KP_DEFAULT_NATIONALITY_ID)%>'
														name="option" property="id">
														<%
																			selected = "";
																		%>
													</logic:notEqual>
													<option value='<bean:write name="option" property="id"/>'
														<%=selected%>><bean:write name="option"
															property="name" />
													</option>
												</logic:iterate>
											</nested:select>

										</div>
									</td>

								</tr>
								<tr>
									<td align="right"><bean:message
											key="admissionForm.studentinfo.phone.label" /></td>
									<td><nested:text styleClass="textboxsmall" disabled="true"
											property="personalData.phNo2" name="loginform"
											styleId="applicantphNo2" maxlength="7" size="7"
											onkeypress="return isNumberKey(event)" /> <nested:text disabled="true"
											styleClass="textboxmedium" property="personalData.phNo3"
											name="loginform" styleId="applicantphNo3" maxlength="10"
											size="10" onkeypress="return isNumberKey(event)" /></td>


									<td align="right"><bean:message
											key="admissionForm.studentinfo.birthcountry.label" /><span
										class="Mandatory">*</span></td>


									<td>
										<div align="left">
											<input type="hidden" id="birthCountryhidden"
												name="birthCountry"
												value="<bean:write name="loginform" property="personalData.birthCountry"/>" />
											<nested:select property="personalData.birthCountry" disabled="true"
												name="loginform" styleClass="dropdown"
												styleId="birthCountry"
												onchange="getStates(this.value,'birthState');">
												<option value=""><bean:message
														key="knowledgepro.admin.select" /></option>
												<%
																	String selected = "";
																%>
												<logic:iterate id="option" property="countries"
																	name="loginform">
																	<%-- <logic:empty name="loginform" property="personalData.birthCountry">
																	<logic:equal
																		value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID)%>'
																		name="option" property="id">
																		<%
																			selected = "selected";
																		%>
																	</logic:equal>
																	<logic:notEqual
																		value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID)%>'
																		name="option" property="id">
																		<%
																			selected = "";
																		%>
																	</logic:notEqual>
																	</logic:empty> --%>
																	<option
																		value='<bean:write name="option" property="id"/>'
																		<%=selected%>><bean:write name="option"
																			property="name" />
																	</option>
																	<script >
               														   			var sl='<bean:write name="loginform" property="personalData.birthCountry"/>';
               														   		var element = document.getElementById("birthCountry");
               														   		element.value = sl;
               														   		</script>
																</logic:iterate>
											</nested:select>

										</div>
									</td>


								</tr>
								<tr>
									<td align="right"><bean:message
											key="admissionForm.studentinfo.mobile.label" /><span
										class="Mandatory">*</span></td>

									<td><nested:text styleClass="textboxsmall" disabled="true"
											property="personalData.mobileNo1" name="loginform"
											styleId="applicantMobileCode" maxlength="4" size="4"
											onkeypress="return isNumberKey(event)"></nested:text> <nested:text disabled="true"
											styleClass="textboxmedium" property="personalData.mobileNo2"
											name="loginform" styleId="applicantMobileNo" maxlength="10"
											size="10" onkeypress="return isNumberKey(event)"></nested:text></td>

									<%
														String dynaStyle = "";
													%>
									<logic:equal value="Other" property="birthState"
										name="loginform">
										<%
															dynaStyle = "display:block;";
														%>
									</logic:equal>
									<logic:notEqual value="Other" property="birthState"
										name="loginform">
										<%
															dynaStyle = "display:none;";
														%>
									</logic:notEqual>

									<td align="right"><bean:message
											key="admissionForm.studentinfo.birthstate.label" /><span
										class="Mandatory">*</span></td>


									<td>




										<div align="left">
											<input type="hidden" id="birthState1"
												value="<bean:write name="loginform" property="personalData.birthState"/>">
											<nested:select property="personalData.birthState" disabled="true"
												name="loginform" styleId="birthState" styleClass="dropdown"
												onchange="funcOtherShowHide('birthState','otherBirthState')">
												<html:option value="">- Select -</html:option>

												<logic:notEmpty property="stateMap" name="loginform">
													<html:optionsCollection name="loginform"
														property="stateMap" label="value" value="key" />
												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
											</nested:select>

										</div>
										<div align="left">
											<nested:text property="personalData.stateOthers"
												name="loginform" maxlength="30" styleId="otherBirthState" disabled="true"
												style="<%=dynaStyle%>" styleClass="textbox"
												onkeypress="IsAlpha(event)"></nested:text>
										</div>





									</td>


								</tr>
								<tr>
									<td align="right"><bean:message
											key="admissionForm.studentinfo.email.label" /><span
										class="Mandatory">*</span></td>
									<td><div align="left">
											<nested:text property="personalData.email" disabled="true"
												styleId="applicantEmail" name="loginform"
												styleClass="textbox" maxlength="50"></nested:text>

											<br />(e.g. name@yahoo.com)
										</div></td>

									<td align="right"><bean:message
											key="admissionForm.studentinfo.birthplace.label" /><span
										class="Mandatory">*</span></td>
									<td><nested:text property="personalData.birthPlace" disabled="true"
											styleId="birthPlace" name="loginform" maxlength="50"
											styleClass="textbox" onkeypress="IsAlpha(event)"></nested:text>

									</td>
								</tr>

								<tr>
									<td align="right"><bean:message
											key="admissionForm.studentinfo.aadhaarNumber" />:</td>
									<td><nested:text property="personalData.aadhaarCardNumber" disabled="true"
											styleId="applicantadhaarNo" name="loginform"
											styleClass="textbox" maxlength="12"
											onkeypress="return isNumberKey(event)"></nested:text></td>
									<td align="right"><bean:message
											key="knowledgepro.applicationform.mothertongue.label" /></td>
									<td><nested:select property="personalData.motherTongueId" disabled="true"
											styleId="studentMotherTongueId" name="loginform"
											styleClass="dropdownmedium">
											<option value=""><bean:message
													key="knowledgepro.admin.select" /></option>
											<nested:notEmpty property="personalData.motherTongues"
												name="loginform">
												<nested:optionsCollection name="loginform"
													property="personalData.motherTongues" label="value"
													value="key" />
											</nested:notEmpty>
										</nested:select></td>
								</tr>

								<%--Address--%>

								<tr height="15" align="center">
									<td colspan="4"><u><b>CURRENT ADDRESS</b></u></td>
								</tr>


								<tr>

									<td align="right" width="25%">House Name/House Number:<span
										class="Mandatory">*</span></td>
									<td width="25%"><nested:text
											property="personalData.currentAddressLine1" disabled="true"
											styleId="currentAddressLine1" styleClass="textbox"
											name="loginform" maxlength="35"></nested:text></td>


									<td align="right" width="25%">Country:<span
										class="Mandatory">*</span></td>
									<td width="25%">
										<div align="left">
											<input type="hidden" id="currentCountryNamehidden"
												name="nationality" name="loginform"
												value="<bean:write name="loginform" property="personalData.currentCountryId"/>" />

											<nested:select property="personalData.currentCountryId" disabled="true"
												name="loginform" styleClass="dropdown"
												styleId="currentCountryName"
												onchange="getMobileNo();getTempAddrStates(this.value,'tempAddrstate');">
												<option value=""><bean:message
														key="knowledgepro.admin.select" /></option>
												<%
																	String selected = "";
																%>
												<logic:iterate id="option" property="countries"
													name="loginform">
													<logic:equal
														value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID)%>'
														name="option" property="id">
														<%
																			selected = "selected";
																		%>
													</logic:equal>
													<logic:notEqual
														value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID)%>'
														name="option" property="id">
														<%
																			selected = "";
																		%>
													</logic:notEqual>
													<option value='<bean:write name="option" property="id"/>'
														<%=selected%>><bean:write name="option"
															property="name" />
													</option>
												</logic:iterate>
											</nested:select>

										</div>

									</td>
								</tr>


								<tr>
									<td align="right">Post Office Name:<span class="Mandatory">*</span></td>
									<td><nested:text
											property="personalData.currentAddressLine2" disabled="true"
											styleId="currentAddressLine2" styleClass="textbox"
											name="loginform" maxlength="40" onkeypress="IsAlpha(event)"></nested:text></td>

									<td align="right">State:<span class="Mandatory">*</span></td>

									<td>
										<div align="left">

											<input type="hidden" id="currentStateId1"
												value="<bean:write name="loginform" property="personalData.currentStateId"/>">
											<logic:equal value="Other"
												property="personalData.currentStateId" name="loginform">
												<%
																	dynaStyle = "display:block;";
																%>
											</logic:equal>
											<logic:notEqual value="Other"
												property="personalData.currentStateId" name="loginform">
												<%
																	dynaStyle = "display:none;";
																%>
											</logic:notEqual>

											<nested:select property="personalData.currentStateId" disabled="true"
												name="loginform" styleClass="dropdown"
												styleId="tempAddrstate"
												onchange="funcOtherShowHide('tempAddrstate','otherTempAddrState');getTempAddrDistrict(this.value,'tempAddrdistrict');">
												<html:option value="">- Select -</html:option>

												<logic:notEmpty property="stateMap" name="loginform">
													<html:optionsCollection name="loginform"
														property="curAddrStateMap" label="value" value="key" />

												</logic:notEmpty>
												<html:option value="Other">Other</html:option>
											</nested:select>

										</div>


										<div align="left">
											<nested:text
												property="personalData.currentAddressStateOthers" disabled="true"
												styleClass="textbox" name="loginform" maxlength="30"
												styleId="otherTempAddrState" style="<%=dynaStyle%>"
												onkeypress="IsAlpha(event)"></nested:text>
										</div>




									</td>
								</tr>


								<tr>
									<td align="right">Street:<span class="Mandatory">*</span></td>
									<td><nested:text property="personalData.currentStreet" disabled="true"
											styleId="currentStreet" styleClass="textbox" name="loginform"
											maxlength="30" onkeypress="IsAlpha(event)"></nested:text></td>


									<td align="right">District:<span class="Mandatory">*</span></td>
									<td><logic:equal value="Other"
											property="personalData.currentDistricId" name="loginform">
											<%
																dynaStyle = "display:block;";
															%>
										</logic:equal> <logic:notEqual value="Other"
											property="personalData.currentDistricId" name="loginform">
											<%
																dynaStyle = "display:none;";
															%>
										</logic:notEqual>
										<div align="left">
											<nested:select property="personalData.currentDistricId"
												name="loginform" styleClass="dropdown" disabled="true"
												styleId="tempAddrdistrict"
												onchange="funcOtherShowHide('tempAddrdistrict','otherTempAddrDistrict');">
												<html:option value="">- Select -</html:option>
												<c:if
													test="${loginform.personalData.currentStateId != null && loginform.personalData.currentStateId!= ''}">
													<c:set var="tempAddrDistrictMap"
														value="${baseActionForm.collectionMap['tempAddrDistrictMap']}" />
													<c:if test="${tempAddrDistrictMap != null}">
														<html:optionsCollection name="tempAddrDistrictMap"
															label="value" value="key" />

													</c:if>
												</c:if>

												<logic:notEmpty property="editCurrentDistrict"
													name="loginform">

												</logic:notEmpty>
												<html:option value="Other">Other</html:option>

											</nested:select>

										</div>


										<div align="left">
											<nested:text
												property="personalData.currentAddressDistrictOthers" disabled="true"
												name="loginform" styleClass="textbox" size="10"
												maxlength="30" styleId="otherTempAddrDistrict"
												style="<%=dynaStyle%>" onkeypress="IsAlpha(event)"></nested:text>
										</div></td>
								</tr>


								<tr>

									<td align="right">City:<span class="Mandatory">*</span></td>
									<td><nested:text property="personalData.currentCityName" disabled="true"
											styleId="currentCityName" styleClass="textbox"
											name="loginform" maxlength="30" onkeypress="IsAlpha(event)"></nested:text></td>


									<td align="right">Pin Code:<span class="Mandatory">*</span></td>
									<td><nested:text styleClass="textbox" disabled="true"
											property="personalData.currentAddressZipCode"
											styleId="currentAddressZipCode" name="loginform"
											maxlength="6" onkeypress="return isNumberKey(event)" /></td>

								</tr>



								<tr>
									<td height="30" align="center" class="subheading" colspan="4"><bean:message
											key="knowledgepro.applicationform.sameaddr.label" /> &nbsp;

										<fieldset style="border: 0px">
											<html:radio property="sameTempAddr" styleId="sameAddr" disabled="true"
												value="true" onclick="disableTempAddress();"></html:radio>
											<label for="sameAddr"><span><span></span></span> <bean:message
													key="knowledgepro.applicationform.yes.label" /></label>


											<html:radio property="sameTempAddr" styleId="DiffAddr" disabled="true"
												value="false" onclick="enableTempAddress();"></html:radio>
											<label for="DiffAddr"><span><span></span></span> <bean:message
													key="knowledgepro.applicationform.No.label" /></label>
										</fieldset></td>
								</tr>



								<tr>

									<td width="100%" colspan="4">
										<div id="currLabel">
											<table align="center" width="100%" border="0"
												style="border-collapse: collapse">
												<tr>
													<td height="30" align="center" class="subheading"><u><b>PERMANENT
																ADDRESS</b></u></td>
												</tr>
											</table>
										</div>
									</td>
								</tr>



								<tr>
									<td colspan="4" width="100%"><div id="currTable">
											<table align="center" cellpadding="4" class="subtable"
												width="100%">
												<tr>


													<td align="right" width="25%">House Name/House Number:<span
														class="Mandatory">*</span>
													</td>
													<td width="25%"><nested:text disabled="true"
															property="personalData.permanentAddressLine1"
															styleId="permanentAddressLine1" styleClass="textbox"
															name="loginform" maxlength="35"></nested:text></td>


													<td align="right" width="25%">Country:<span
														class="Mandatory">*</span></td>
													<td width="25%">
														<div align="left">
															<input type="hidden" id="permanentCountryNamehidden"
																name="nationality"
																value="<bean:write name="loginform" property="personalData.permanentCountryId"/>" />

															<nested:select property="personalData.permanentCountryId"
																name="loginform" styleClass="dropdown"
																styleId="permanentCountryName" disabled="true"
																onchange="getPermAddrStates(this.value,'permAddrstate');">
																<option value=""><bean:message
																		key="knowledgepro.admin.select" /></option>
																<%
																					String selected = "";
																				%>
																<logic:iterate id="option" property="countries"
																	name="loginform">
																	<logic:equal
																		value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID)%>'
																		name="option" property="id">
																		<%
																							selected = "selected";
																						%>
																	</logic:equal>
																	<logic:notEqual
																		value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID)%>'
																		name="option" property="id">
																		<%
																							selected = "";
																						%>
																	</logic:notEqual>
																	<option
																		value='<bean:write name="option" property="id"/>'
																		<%=selected%>><bean:write name="option"
																			property="name" />
																	</option>
																</logic:iterate>
															</nested:select>

														</div>
													</td>
												</tr>


												<tr>
													<td align="right">Post Office Name:<span
														class="Mandatory">*</span></td>
													<td><nested:text disabled="true"
															property="personalData.permanentAddressLine2"
															styleClass="textbox" styleId="permanentAddressLine2"
															name="loginform" maxlength="40"
															onkeypress="IsAlpha(event)"></nested:text></td>

													<td align="right">State:<span class="Mandatory">*</span></td>

													<td><input type="hidden" id="permanentState"
														value="<bean:write name="loginform" property="personalData.permanentStateId"/>">
														<logic:equal value="Other"
															property="personalData.permanentStateId" name="loginform">
															<%
																				dynaStyle = "display:block;";
																			%>
														</logic:equal> <logic:notEqual value="Other"
															property="personalData.permanentStateId" name="loginform">
															<%
																				dynaStyle = "display:none;";
																			%>
														</logic:notEqual>

														<div align="left">
															<nested:select property="personalData.permanentStateId"
																name="loginform" styleClass="dropdown"
																styleId="permAddrstate" disabled="true"
																onchange="funcOtherShowHide('permAddrstate','otherPermAddrState');getPermAddrDistrict(this.value,'permAddrdistrict');">
																<html:option value="">- Select -</html:option>

																<logic:notEmpty property="stateMap" name="loginform">
																	<html:optionsCollection name="loginform"
																		property="perAddrStateMap" label="value" value="key" />

																</logic:notEmpty>
																<html:option value="Other">Other</html:option>
															</nested:select>

														</div>
														<div align="left">
															<nested:text disabled="true"
																property="personalData.permanentAddressStateOthers"
																name="loginform" styleClass="textbox" maxlength="30"
																styleId="otherPermAddrState" style="<%=dynaStyle%>"
																onkeypress="IsAlpha(event)"></nested:text>
														</div></td>
												</tr>


												<tr>
													<td align="right">Street:<span class="Mandatory">*</span></td>
													<td><nested:text disabled="true"
															property="personalData.permanentStreet"
															styleId="permanentStreet" styleClass="textbox"
															name="loginform" maxlength="30"
															onkeypress="IsAlpha(event)"></nested:text></td>


													<td align="right">District:<span class="Mandatory">*</span></td>
													<td><logic:equal value="Other"
															property="personalData.permanentDistricId"
															name="loginform">
															<%
																				dynaStyle = "display:block;";
																			%>
														</logic:equal> <logic:notEqual value="Other"
															property="personalData.permanentDistricId"
															name="loginform">
															<%
																				dynaStyle = "display:none;";
																			%>
														</logic:notEqual>

														<div align="left">
															<nested:select property="personalData.permanentDistricId"
																name="loginform" styleClass="dropdown"
																styleId="permAddrdistrict" disabled="true"
																onchange="funcOtherShowHide('permAddrdistrict','otherPermAddrDistrict');">
																<html:option value="">- Select -</html:option>
																<c:if
																	test="${loginform.personalData.permanentStateId != null && loginform.personalData.permanentStateId!= ''}">
																	<c:set var="permAddrDistrictMap"
																		value="${baseActionForm.collectionMap['permAddrDistrictMap']}" />
																	<c:if test="${permAddrDistrictMap != null}">
																		<html:optionsCollection name="permAddrDistrictMap"
																			label="value" value="key" />


																	</c:if>
																</c:if>
																<logic:notEmpty property="editPermanentDistrict"
																	name="loginform">
																	<html:optionsCollection name="loginform"
																		property="editPermanentDistrict" label="name"
																		value="id" />

																</logic:notEmpty>
																<html:option value="Other">Other</html:option>
															</nested:select>

														</div>
														<div align="left">
															<nested:text disabled="true"
																property="personalData.permanentAddressDistrictOthers"
																name="loginform" size="10" maxlength="30"
																styleClass="textbox" styleId="otherPermAddrDistrict"
																style="<%=dynaStyle%>" onkeypress="IsAlpha(event)"></nested:text>
														</div></td>
												</tr>


												<tr>

													<td align="right">City:<span class="Mandatory">*</span></td>
													<td><nested:text disabled="true"
															property="personalData.permanentCityName"
															styleId="permanentCityName" styleClass="textbox"
															name="loginform" maxlength="30"
															onkeypress="IsAlpha(event)"></nested:text></td>

													<td align="right">Pin Code:<span class="Mandatory">*</span></td>
													<td><nested:text styleClass="textbox" disabled="true"
															property="personalData.permanentAddressZipCode"
															styleId="permanentAddressZipCode" name="loginform"
															maxlength="6" onkeypress="return isNumberKey(event)" />

													</td>



												</tr>
											</table>
										</div></td>
								</tr>




								<tr>
									<td width="100%" colspan="4"><table align="center"
											width="40%" border="0" style="border-collapse: collapse">
											<tr>
												<td height="30" align="center" class="subheading"><u><b>PARENT
															INFORMATION</b></u></td>
											</tr>
										</table></td>
								</tr>


								<tr>
									<td width="100%" colspan="4"><table width="100%"
											align="center" cellpadding="4" class="subtable">

											<tr>
												<td align="right" width="25%"><bean:message
														key="knowledgepro.admission.fatherName" /><span
													class="Mandatory">*</span></td>

												<td width="25%"><div align="left">
														<nested:select property="personalData.titleOfFather"
															styleId='titleOfFather' name="loginform"
															styleClass="dropdownsmall" disabled="true"
															onchange="fatherIncomeMandatory()">
															<html:option value="Mr">Mr.</html:option>
															<html:option value="Late">Late.</html:option>
														</nested:select>

														<nested:text property="personalData.fatherName"
															styleId="fatherName" name="loginform" disabled="true"
															styleClass="textboxmedium" maxlength="50"
															onkeypress="IsAlphaDot(event)"></nested:text>

													</div></td>

												<td align="right" width="25%"><bean:message
														key="knowledgepro.admission.motherName" /><span
													class="Mandatory">*</span></td>
												<td width="25%"><div align="left">
														<nested:select property="personalData.titleOfMother"
															styleId='titleOfMother' name="loginform"
															styleClass="dropdownsmall" disabled="true"
															onchange="motherIncomeMandatory()">
															<!--<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>-->
															<html:option value="Mrs">Mrs.</html:option>
															<html:option value="Late">Late.</html:option>
														</nested:select>
														<nested:text property="personalData.motherName"
															styleId="motherName" name="loginform" disabled="true"
															styleClass="textboxmedium" maxlength="50"
															onkeypress="IsAlphaDot(event)"></nested:text>

													</div></td>

											</tr>



											<tr>



												<td align="right">
													<div align="right">
														Occupation:<span class="Mandatory">*</span>
													</div>
												</td>

												<td width="25%">

													<div align="left">
														<input type="hidden" id="hiddenFatherOccupationId"
															name="loginform"
															value="<bean:write name="loginform" property="personalData.fatherOccupationId"/>" />
														<nested:select property="personalData.fatherOccupationId"
															name="loginform" styleClass="dropdown"
															styleId="fatherOccupation" disabled="true"
															onchange="displayOtherForFather(this.value)">
															<html:option value="">- Select -</html:option>
															<html:optionsCollection name="loginform"
																property="occupations" label="occupationName"
																value="occupationId" />
															<html:option value="Other">Other</html:option>
														</nested:select>

													</div>

													<div align="left" id="displayFatherOccupation">

														<nested:text property="personalData.otherOccupationFather"
															name="loginform" styleClass="textbox" maxlength="50" disabled="true"
															styleId="otherOccupationFather" />
													</div>
												</td>

												<logic:equal value="Other"
													property="personalData.motherOccupationId" name="loginform">
													<%
																		dynaStyle = "display:block;";
																	%>
												</logic:equal>

												<logic:notEqual value="Other"
													property="personalData.motherOccupationId" name="loginform">
													<%
																		dynaStyle = "display:none;";
																	%>
												</logic:notEqual>

												<td align="right">Occupation:</td>
												<td width="25%">

													<div align="left">
														<nested:select property="personalData.motherOccupationId" disabled="true"
															name="loginform" styleClass="dropdown"
															styleId="motherOccupation"
															onchange="displayOtherForMother(this.value)">
															<html:option value="">- Select -</html:option>
															<html:optionsCollection name="loginform"
																property="occupations" label="occupationName"
																value="occupationId" />
															<html:option value="Other">Other</html:option>
														</nested:select>

													</div>

													<div align="left" id="displayMotherOccupation">

														<nested:text property="personalData.otherOccupationMother" disabled="true"
															name="loginform" styleClass="textbox" maxlength="50"
															styleId="otherOccupationMother" />
													</div>

												</td>
											</tr>


											<tr>



												<td align="right">Family Annual Income: <span
													class="Mandatory">*</span></td>

												<td><div align="left">
														<nested:select style="display:none"
															property="personalData.fatherCurrencyId" name="loginform"
															styleId="fatherCurrency">


															<%
																				String selected = "";
																			%>
															<logic:iterate id="option" property="currencyList"
																name="loginform">
																<logic:equal
																	value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID)%>'
																	name="option" property="id">
																	<%
																						selected = "selected";
																					%>
																</logic:equal>
																<logic:notEqual
																	value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID)%>'
																	name="option" property="id">
																	<%
																						selected = "";
																					%>
																</logic:notEqual>
																<option
																	value='<bean:write name="option" property="id"/>'
																	<%=selected%>><bean:write name="option"
																		property="name" />
																</option>
															</logic:iterate>
														</nested:select>
														<nested:select property="personalData.fatherIncomeId"
															name="loginform" styleClass="dropdown" disabled="true"
															styleId="fatherIncomeRange">
															<html:option value="">
																<bean:message key="knowledgepro.admin.select" />
															</html:option>
															<nested:optionsCollection name="loginform"
																property="incomeList" label="incomeRange" value="id" />


														</nested:select>

													</div></td>



												<td>
													<div align="right">
														<bean:message key="admissionForm.studentinfo.mobile.label" />
													</div>
												</td>
												<td align="left"><nested:text readonly="true"
														styleId="motherMobile" property="personalData.parentMob1"
														name="loginform" size="4" maxlength="4"
														styleClass="textboxsmall"
														onkeypress="return isNumberKey(event)"></nested:text> <nested:text disabled="true"
														styleId="motherMobile1"
														property="personalData.motherMobile" name="loginform"
														size="15" maxlength="10" styleClass="textboxmedium"
														onkeypress="return isNumberKey(event)"></nested:text></td>


											</tr>


											<tr>

												<td>
													<div align="right">
														<bean:message key="admissionForm.studentinfo.mobile.label" />
														<span class="Mandatory">*</span>
													</div>
												</td>
												<td align="left"><nested:text readonly="true"
														styleId="fatherMobile" property="personalData.parentMob1"
														name="loginform" size="4" maxlength="4"
														styleClass="textboxsmall"
														onkeypress="return isNumberKey(event)"></nested:text> <nested:text disabled="true"
														styleId="fatherMobile1"
														property="personalData.fatherMobile" name="loginform"
														size="15" maxlength="10" styleClass="textboxmedium"
														onkeypress="return isNumberKey(event)"></nested:text></td>


												<td width="25%" align="right"><bean:message
														key="admissionForm.studentinfo.email.label" /></td>
												<td>
													<div align="left">
														<nested:text property="personalData.motherEmail" disabled="true"
															styleId="motherEmail" styleClass="textbox"
															name="loginform" maxlength="50"></nested:text>

													</div>
													<div align="left">(e.g. name@yahoo.com)</div>
												</td>


											</tr>

											<tr>
												<td width="25%" align="right"><bean:message
														key="admissionForm.studentinfo.email.label" /></td>
												<td>
													<div align="left">
														<nested:text property="personalData.fatherEmail" disabled="true"
															name="loginform" styleId="fatherEmail"
															styleClass="textbox" maxlength="50"></nested:text>

													</div>
													<div align="left">(e.g. name@yahoo.com)</div>

												</td>
												<td width="25%" align="right">Official Adress:</td>
												<td width="25%" align="right">
													<div align="left">
														<nested:text disabled="true"
															property="personalData.motherOccupationAddress"
															name="loginform" styleId="motherAddress"
															styleClass="textbox" maxlength="50"></nested:text>
													</div>
												</td>

											</tr>

											<tr>

												<td width="25%" align="right">Official Adress:</td>
												<td width="25%" align="right">
													<div align="left">
														<nested:text disabled="true"
															property="personalData.fatherOccupationAddress"
															name="loginform" styleId="fatherAddress"
															styleClass="textbox" maxlength="50"></nested:text>
													</div>
												</td>

											</tr>

											<tr>
												<td width="25%" align="right">PAN Card Number :</td>
												<td width="25%">
													<div align="left">
														<nested:text property="personalData.fatherPANNumber"
															name="loginform" styleId="fatherPANNumber"
															styleClass="textbox" maxlength="10" disabled="true"
															style="text-transform:uppercase"></nested:text>
													</div>
												</td>
											</tr>

											<tr>
												<td width="25%" align="right">Aadhaar Card Number :</td>
												<td width="25%">
													<div align="left">
														<nested:text property="personalData.fatherAadhaarNumber"
															name="loginform" styleId="fatherAadhaarNumber"
															styleClass="textbox" maxlength="12" disabled="true"
															onkeypress="return isNumberKey(event)"></nested:text>
													</div>
												</td>
											</tr>

										</table></td>
								</tr>


								<tr>

									<td width="100%" colspan="4">
										<table align="center" width="100%" border="0"
											style="border-collapse: collapse">
											<tr>
												<td height="30" align="center" class="subheading"><bean:message
														key="knowledgepro.applicationform.sameaddrparent.label" />
													&nbsp;

													<fieldset style="border: 0px">
														<html:radio property="sameParentAddr" disabled="true"
															styleId="sameParAddr" value="true"
															onclick="disablePermAddress()"></html:radio>
														<label for="sameParentAddr"><span><span></span></span>
															<bean:message
																key="knowledgepro.applicationform.yes.label" /></label>
														<html:radio property="sameParentAddr" disabled="true"
															styleId="DiffParAddr" value="false"
															onclick="enablePermAddress()"></html:radio>
														<label for="sameParentAddr"><span><span></span></span>
															<bean:message key="knowledgepro.applicationform.No.label" /></label>
													</fieldset></td>

											</tr>
										</table>
									</td>
								</tr>




								<tr>

									<td width="100%" colspan="4">
										<div id="permLabel">
											<table align="center" width="100%" border="0"
												style="border-collapse: collapse">
												<tr>
													<td height="30" align="center" class="subheading"><u><b>PARENT
																ADDRESS</b></u></td>
												</tr>
											</table>
										</div>
									</td>
								</tr>

								<tr>
									<td colspan="4" width="100%"><div id="permTable">
											<table align="center" cellpadding="4" class="subtable"
												width="100%">
												<tr>

													<td width="25%">
														<div align="right">
															House Name/House Number:<span class="Mandatory">*</span>
														</div>
													</td>
													<td width="25%" align="left"><nested:text
															styleId="parentAddressLine1" disabled="true"
															property="personalData.parentAddressLine1"
															styleClass="textbox" name="loginform" size="15"
															maxlength="100"></nested:text></td>


													<td width="25%" align="right"><bean:message
															key="admissionForm.studentinfo.mobile.label" /><span
														class="Mandatory">*</span></td>
													<td width="25%">
														<div align="left">
															<nested:text readonly="false" styleClass="textboxsmall"
																property="personalData.parentMob1" disabled="true"
																styleId="parentMobile" name="loginform" maxlength="4"
																size="4" onkeypress="return isNumberKey(event)"></nested:text>
															<nested:text styleClass="textboxmedium"
																property="personalData.parentMob2" disabled="true"
																styleId="parentMobile1" name="loginform" maxlength="10"
																size="10" onkeypress="return isNumberKey(event)"></nested:text>

														</div>
													</td>



												</tr>



												<tr>
													<td>
														<div align="right">
															Post Office Name:<span class="Mandatory">*</span>
														</div>
													</td>
													<td><nested:text styleId="parentAddressLine2" disabled="true"
															property="personalData.parentAddressLine2"
															styleClass="textbox" name="loginform" size="15"
															maxlength="100" onkeypress="IsAlpha(event)"></nested:text>

													</td>


													<td align="right"><div align="right">
															<bean:message key="knowledgepro.admin.country" />
															<span class="Mandatory">*</span>
														</div></td>
													<td><div align="left">
															<input type="hidden" id="hiddenParentCountryId"
																name="nationality" name="loginform"
																value="<bean:write name="loginform" property="personalData.parentCountryId"/>" />

															<nested:select property="personalData.parentCountryId"
																name="loginform" styleClass="dropdown"
																styleId="parentCountryName" disabled="true"
																onchange="getParentAddrStates(this.value,'parentState')">
																<option value=""><bean:message
																		key="knowledgepro.admin.select" /></option>
																<%
																					String selected = "";
																				%>
																<logic:iterate id="option" property="countries"
																	name="loginform">
																	<logic:equal
																		value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID)%>'
																		name="option" property="id">
																		<%
																							selected = "selected";
																						%>
																	</logic:equal>
																	<logic:notEqual
																		value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID)%>'
																		name="option" property="id">
																		<%
																							selected = "";
																						%>
																	</logic:notEqual>
																	<option
																		value='<bean:write name="option" property="id"/>'
																		<%=selected%>><bean:write name="option"
																			property="name" />
																	</option>
																</logic:iterate>
															</nested:select>

														</div></td>



												</tr>



												<tr>
													<td align="right">
														<div align="right">
															<bean:message key="knowledgepro.admin.city" />
															:<span class="Mandatory">*</span>
														</div>

													</td>
													<td align="left"><nested:text styleId="parentCityName"
															property="personalData.parentCityName" disabled="true"
															styleClass="textbox" name="loginform" size="15"
															maxlength="30" onkeypress="IsAlpha(event)"></nested:text>


													</td>


													<td>
														<div align="right">
															<bean:message key="knowledgepro.admin.state" />
															<span class="Mandatory">*</span>
														</div>
													</td>

													<td><div align="left">
															<%
																				String dynastyle = "";
																			%>
															<logic:equal value="Other"
																property="personalData.parentStateId" name="loginform">
																<%
																					dynastyle = "display:block;";
																				%>
															</logic:equal>
															<logic:notEqual value="Other"
																property="personalData.parentStateId" name="loginform">
																<%
																					dynastyle = "display:none;";
																				%>
															</logic:notEqual>
															<nested:select property="personalData.parentStateId"
																name="loginform" styleClass="dropdown"
																styleId="parentState" disabled="true"
																onchange="funcOtherShowHide('parentState','otherParentAddrState');">
																<html:option value="">- Select -</html:option>
																<logic:notEmpty property="parentStateMap"
																	name="loginform">
																	<html:optionsCollection name="loginform"
																		property="parentStateMap" label="value" value="key" />
																</logic:notEmpty>
																<html:option value="Other">Other</html:option>
															</nested:select>

														</div>
														<div align="left">
															<nested:text disabled="true"
																property="personalData.parentAddressStateOthers"
																styleClass="textbox" name="loginform" maxlength="30"
																styleId="otherParentAddrState" style="<%=dynastyle%>"
																onkeypress="IsAlpha(event)"></nested:text>
														</div></td>


												</tr>

												<tr>
													<td><div align="right">
															<bean:message key="knowledgepro.admission.zipCode" />
															<span class="Mandatory">*</span>

														</div></td>
													<td width="25%" align="left"><nested:text
															styleId="parentAddressZipCode" disabled="true"
															property="personalData.parentAddressZipCode"
															styleClass="textbox" name="loginform" size="10"
															maxlength="10" onkeypress="return isNumberKey(event)"></nested:text>

													</td>

													<td></td>
													<td></td>
												</tr>

											</table>
										</div></td>
								</tr>

								<tr>

									<td width="100%" colspan="4">
										<table align="center" width="100%" border="0"
											style="border-collapse: collapse">
											<tr>
												<td height="30" align="center" class="subheading"><u><b>COMMUNITY
															DETAILS</b></u></td>
											</tr>
										</table>
									</td>
								</tr>


								<tr>
									<td width="100%" colspan="4"><table width="100%"
											align="center" cellpadding="4" class="subtable">
											<tr>
												<td valign="top" align="right" width="25%"><bean:message
														key="admissionForm.studentinfo.religion.label" /><span
													class="Mandatory">*</span></td>
												<td width="25%" valign="top"><logic:equal value="Other"
														property="religionId" name="loginform">
														<%
																			dynaStyle = "display:block;";
																		%>
													</logic:equal> <logic:notEqual value="Other" property="religionId"
														name="loginform">
														<%
																			dynaStyle = "display:none;";
																		%>
													</logic:notEqual>
													<div align="left">
														<input type="hidden" id="religionType" name="religionType"
															value='<bean:write name="loginform" property="personalData.religionId"/>' />
														<logic:notEqual value="Other"
															property="personalData.religionId" name="loginform">
															<%
																				dynaStyle = "display:none;";
																			%>
															<nested:select property="personalData.religionId"
																name="loginform" styleClass="dropdown"
																styleId="religions" disabled="true"
																onchange="getSubCaste(this.value,'castCatg');funcOtherShowHide('religions','otherReligion');funcReligionShowHide(this.value);">
																<option value=""><bean:message
																		key="knowledgepro.admin.select" /></option>
																<nested:optionsCollection name="loginform"
																	property="religions" label="religionName"
																	value="religionId" />
																<html:option value="Other">Other</html:option>
															</nested:select>
														</logic:notEqual>
														<logic:equal value="Other"
															property="personalData.religionId" name="loginform">
															<%
																				dynaStyle = "display:block;";
																			%>
															<nested:select property="personalData.religionId"
																name="loginform" styleClass="dropdown"
																styleId="religions" disabled="true"
																onchange="getSubCaste(this.value,'castCatg');funcOtherShowHide('religions','otherReligion');funcReligionShowHide(this.value);">
																<option value=""><bean:message
																		key="knowledgepro.admin.select" /></option>
																<nested:optionsCollection name="loginform"
																	property="religions" label="religionName"
																	value="religionId" />
																<html:option value="Other">Other</html:option>
															</nested:select>
														</logic:equal>
													</div>
													<div align="left">
														<nested:text property="personalData.religionOthers" disabled="true"
															styleClass="textbox" name="loginform" maxlength="30"
															styleId="otherReligion" style="<%=dynaStyle%>"></nested:text>
													</div> <logic:equal name="loginform"
														property="personalData.religionId" value="3">
														<%
																			dynaStyle = "display:block;";
																		%>
													</logic:equal>
													<div align="left" id="parish_description"
														style="display: none;">
														Dioceses:
														<nested:text property="personalData.dioceseOthers" disabled="true"
															styleClass="textboxmedium" name="loginform" size="10"
															maxlength="30" styleId="otherDiocese"
															onkeypress="IsAlpha(event)"></nested:text>
													</div>
													<div align="left" id="dioces_description"
														style="display: none;">
														&nbsp; &nbsp; Parish:
														<nested:text property="personalData.parishOthers" disabled="true"
															styleClass="textboxmedium" name="loginform" size="10"
															maxlength="30" styleId="otherParish"
															onkeypress="IsAlpha(event)"></nested:text>
													</div></td>
												<%
																	String dynaStyle4 = "display:none;";
																%>
												<logic:equal value="true" property="handicapped"
													name="loginform">
													<%
																		dynaStyle4 = "display:block;";
																	%>
												</logic:equal>
												<logic:equal value="false" property="dateExpired"
													name="loginform">
													<td align="right" width="25%" valign="top"><bean:message
															key="knowledgepro.applicationform.physical.label" /></td>
													<td width="25%">
														<div align="left">
															<input type="hidden" id="hiddenHandicaped"
																name="hiddenHandicaped"
																value='<bean:write name="loginform" property="personalData.handicapped"/>' />
															<fieldset style="border: 0px">
																<nested:radio property="personalData.handicapped" disabled="true"
																	styleId="handicappedYes" name="loginform" value="true"
																	onclick="showHandicappedDescription();"></nested:radio>
																<label for="handicappedYes"><span><span></span></span>
																	<bean:message
																		key="knowledgepro.applicationform.yes.label" /></label>
																<nested:radio property="personalData.handicapped" disabled="true"
																	styleId="handicappedNo" name="loginform" value="false"
																	onclick="hideHandicappedDescription()"></nested:radio>
																<label for="handicappedNo"><span><span></span></span>
																	<bean:message
																		key="knowledgepro.applicationform.No.label" /></label>
															</fieldset>
														</div>
														<div align="left" id="handicapped_description"
															style="<%=dynaStyle4%>">
															<span class="Mandatory">Percentage of diasbilty :</span>
															<nested:text styleId="hadnicappedDescription" disabled="true"
																property="personalData.hadnicappedDescription"
																name="loginform" maxlength="80" styleClass="textbox"></nested:text>
														</div>
													</td>
												</logic:equal>
											</tr>
											<tr>
												<td align="right" width="25%"><bean:message
														key="admissionForm.studentinfo.subreligion.label" /><span
													class="Mandatory">*</span></td>
												<td width="25%">
													<div align="left">
														<input type="hidden" id="casteType" name="casteType"
															value='<bean:write name="loginform" property="personalData.casteId"/>' />
														<logic:equal value="Other" property="personalData.casteId"
															name="loginform">
															<%
																				dynaStyle = "display:block;";
																			%>
														</logic:equal>
														<logic:notEqual value="Other"
															property="personalData.casteId" name="loginform">
															<%
																				dynaStyle = "display:none;";
																			%>
														</logic:notEqual>
														<nested:select property="personalData.casteId" disabled="true"
															name="loginform" styleId="castCatg" styleClass="dropdown"
															onchange="funcOtherShowHide('castCatg','otherCastCatg');funcRCSCShowHide(this.value);">
															<option value="">-Select-</option>
															<c:if
																test="${loginform.personalData.religionId != null && loginform.personalData.religionId != ''}">
																<c:set var="subCasteMap"
																	value="${baseActionForm.collectionMap['subCasteMap']}" />
																<c:if test="${subCasteMap != null}">
																	<html:optionsCollection name="subCasteMap"
																		label="value" value="key" />
																</c:if>
															</c:if>
															<logic:notEmpty property="subCasteMap" name="loginform">
																<html:optionsCollection name="loginform"
																	property="subCasteMap" label="value" value="key" />
															</logic:notEmpty>
															<html:option value="Other">Other</html:option>
														</nested:select>
													</div>
													<div align="left">
														<nested:text property="personalData.casteOthers" disabled="true"
															name="loginform" size="10" maxlength="30"
															styleId="otherCastCatg" styleClass="textbox"
															style="<%=dynaStyle%>"></nested:text>
													</div>
												</td>
												<logic:equal value="false" property="dateExpired"
													name="loginform">
													<td align="right"><div align="right">Participation
															in Cultural Activities:</div></td>
													<td align="left">
														<div align="left">
															<input type="hidden" id="arts" name="ats"
																value='<bean:write name="loginform" property="arts"/>' />
															<nested:select property="personalData.arts" disabled="true"
																name="loginform" styleClass="dropdown" styleId="arts1"
																onchange="showArtsParticipate(this.value)">
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<html:option value="Kerala School Kalolsavam">Kerala School Kalolsavam</html:option>
																<html:option
																	value="Revenue District level youth Festival">Revenue District level youth Festival</html:option>
															</nested:select>
														</div>
													</td>
												</logic:equal>
											</tr>
											<tr>
												<td align="right"><bean:message
														key="admissionForm.studentinfo.castcatg.label" /><span
													class="Mandatory">*</span></td>
												<td>
													<div align="left">
														<nested:select property="personalData.subReligionId"
															name="loginform" styleClass="dropdown"
															styleId="subreligion" disabled="true"
															onchange="funcOtherShowHide('subreligion','otherSubReligion')">
															<html:option value="">- Select -</html:option>
															<html:optionsCollection property="subReligionMap"
																name="loginform" label="value" value="key" />
														</nested:select>
													</div>
												</td>
												<logic:equal value="false" property="dateExpired"
													name="loginform">
													<td align="right"><div align="right">If Yes
															Achievement In Cultural Activities:</div></td>
													<td align="left">
														<div align="left">
															<input type="hidden" id="artsParticipate"
																name="artsParticipate"
																value='<bean:write name="loginform" property="artsParticipate"/>' />
															<nested:select disabled="true"
																property="personalData.artsParticipate" name="loginform"
																styleClass="dropdown" styleId="artsParticipate1">
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<html:option value="A">A</html:option>
																<html:option value="B">B</html:option>
																<html:option value="C">C</html:option>
																<html:option value="Participated">Participated</html:option>
															</nested:select>
														</div>
													</td>
												</logic:equal>
											</tr>
											<tr>
												<td width="25%" align="right"><div align="right">Whether
														Dependent of Service/Ex-Service Man?:</div></td>
												<td width="25%">
													<div align="left">
														<fieldset style="border: 0px">
															<nested:radio styleId="exservice" disabled="true"
																property="personalData.exservice" name="loginform"
																value="true"></nested:radio>
															<label for="exservice"><span><span></span></span>
																<bean:message
																	key="knowledgepro.applicationform.yes.label" /></label>
															<nested:radio styleId="exservice1"
																property="personalData.exservice" name="loginform"
																value="false"></nested:radio>
															<label for="exservice1"><span><span></span></span>
																<bean:message
																	key="knowledgepro.applicationform.No.label" /></label>
														</fieldset>
													</div>
												</td>
												<logic:equal value="false" property="dateExpired"
													name="loginform">
													<td align="right"><div align="right">Participation
															in Sports:</div></td>
													<td align="left">
														<div align="left">
															<input type="hidden" id="sports" name="sports"
																value='<bean:write name="loginform" property="sports"/>' />
															<nested:select property="personalData.sports" disabled="true"
																name="loginform" styleClass="dropdown" styleId="sports1"
																onchange="showSportsParticipate(this.value)">
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<html:option
																	value="Senior/Junior International Competition">Senior/Junior International Competition</html:option>
																<html:option value="Senior/Junior National Competition">Senior/Junior National Competition</html:option>
																<html:option
																	value="School nationals (including CISCE, CBSE, HSE, and VHSE)">School nationals (including CISCE, CBSE, HSE, and VHSE)</html:option>
																<html:option
																	value="Zone and Cluster levels (including ICSE, CBSE, HSE, VHSE)">Zone and Cluster levels (including ICSE, CBSE, HSE, VHSE)</html:option>
																<html:option
																	value="Inter Collegiate/Senior/Junior State Championship (Inter District)">Inter Collegiate/Senior/Junior State Championship (Inter District)</html:option>
																<html:option value="Sub District Level">Sub District Level</html:option>
															</nested:select>
														</div>
													</td>
												</logic:equal>
											</tr>
											<tr>
												<logic:equal name="loginform" property="programTypeId"
													value="1">
													<%
																		String dynaStyle5 = "display:none;";
																	%>
													<logic:equal value="true" property="ncccertificate"
														name="loginform">
														<%
																			dynaStyle5 = "display:block;";
																		%>
													</logic:equal>
													<td align="right">Holder of Plus Two Level NCC
														Certificate:</td>
													<td>
														<div>
															<input type="hidden" id="hiddenncccertificate"
																name="hiddenncccertificate"
																value='<bean:write name="loginform" property="personalData.ncccertificate"/>' />
															<fieldset style="border: 0px">
																<nested:radio styleId="ncccertificate" disabled="true"
																	property="personalData.ncccertificate" name="loginform"
																	value="true" onclick="showNcccertificate()"></nested:radio>
																<label for="ncccertificate"><span><span></span></span>
																	<bean:message
																		key="knowledgepro.applicationform.yes.label" /></label>
																<nested:radio styleId="ncccertificate1" disabled="true"
																	property="personalData.ncccertificate" name="loginform"
																	value="false" onclick="hideNcccertificate()"></nested:radio>
																<label for="ncccertificate1"><span><span></span></span>
																	<bean:message
																		key="knowledgepro.applicationform.No.label" /></label>
															</fieldset>
														</div>
														<div id="ncccertificate_description"
															style="<%=dynaStyle5%>">
															Grade of Certificate: <input type="hidden" id="nccgrade"
																name="nccgrade"
																value='<bean:write name="loginform" property="personalData.nccgrades"/>' />
															<nested:select property="personalData.nccgrades" disabled="true"
																styleClass="dropdownmedium" name="loginform"
																styleId="nccgrade1">
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<!--<html:option value="A">A</html:option>-->
																<html:option value="B">B</html:option>
																<!--<html:option value="C">C</html:option>-->
															</nested:select>
														</div>
													</td>
												</logic:equal>
												<logic:equal name="loginform" property="programTypeId"
													value="2">
													<%
																		String dynaStyle6 = "display:none;";
																	%>
													<logic:equal value="true" property="ncccertificate"
														name="loginform">
														<%
																			dynaStyle6 = "display:block;";
																		%>
													</logic:equal>
													<td align="right">Holder of UG Level NCC Certificate:</td>
													<td>
														<div align="left">
															<input type="hidden" id="hiddenncccertificate"
																name="hiddenncccertificate"
																value='<bean:write name="loginform" property="personalData.ncccertificate"/>' />
															<fieldset style="border: 0px">
																<nested:radio styleId="ncccertificate" disabled="true"
																	property="personalData.ncccertificate" name="loginform"
																	value="true" onclick="showNcccertificate()"></nested:radio>
																<label for="ncccertificate"><span><span></span></span>
																	<bean:message
																		key="knowledgepro.applicationform.yes.label" /></label>
																<nested:radio styleId="ncccertificate1" disabled="true"
																	property="personalData.ncccertificate" name="loginform"
																	value="false" onclick="hideNcccertificate()"></nested:radio>
																<label for="ncccertificate1"><span><span></span></span>
																	<bean:message
																		key="knowledgepro.applicationform.No.label" /></label>
															</fieldset>
														</div>
														<div id="ncccertificate_description"
															style="<%=dynaStyle6%>">
															Grade of Certificate: <input type="hidden" id="nccgrade"
																name="nccgrade"
																value='<bean:write name="loginform" property="personalData.nccgrades"/>' />
															<nested:select property="personalData.nccgrades" disabled="true"
																name="loginform" styleClass="dropdownmedium"
																styleId="nccgrade1">
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<html:option value="A">A</html:option>
																<html:option value="B">B</html:option>
																<html:option value="C">C</html:option>
															</nested:select>
														</div>
													</td>
												</logic:equal>
												<!--                        <logic:equal value="false" property="dateExpired" name="loginform">-->
												<!--                        	<td align="right" ><div align="right">If Yes Achievement In Sports:</div></td>-->
												<!--							<td align="left">-->
												<!--								<div align="left">-->
												<!--		                    		<input type="hidden" id="sportsParticipate" name="sportsParticipate" value='<bean:write name="loginform" property="sportsParticipate"/>'/>-->
												<!--	                        		<nested:select disabled="true" property="personalData.sportsParticipate" name="loginform" styleClass="dropdown" styleId="sportsParticipate1">-->
												<!--										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>-->
												<!--										<html:option value="1 Prize">1 Prize</html:option>-->
												<!--										<html:option value="2 Prize">2 Prize</html:option>-->
												<!--										<html:option value="3 Prize">3 Prize</html:option>-->
												<!--										<html:option value="Participated">Participated</html:option>-->
												<!--									</nested:select>-->
												<!--								 </div>-->
												<!--							</td>-->
												<!--                        </logic:equal>                        -->
											</tr>
											<logic:equal name="loginform" property="programTypeId"
												value="1">
												<%String dynaStyle7="display:none;"; %>
												<logic:equal value="true" property="nsscertificate"
													name="loginform">
													<%dynaStyle7="display:block;"; %>
												</logic:equal>
												<tr>
													<td align="right">Holder of Plus Two Level NSS
														Certificate:</td>
													<td>
														<div align="left">
															<input type="hidden" id="hiddennsscertificate"
																name="hiddennsscertificate"
																value='<bean:write name="loginform" property="personalData.nsscertificate"/>' />
															<fieldset style="border: 0px">
																<nested:radio styleId="nsscertificate" disabled="true"
																	property="personalData.nsscertificate" name="loginform"
																	value="true" onclick="showNsscertificate()"></nested:radio>
																<label for="nsscertificate"><span><span></span></span>
																	<bean:message
																		key="knowledgepro.applicationform.yes.label" /></label>
																<nested:radio styleId="nsscertificate1" disabled="true"
																	property="personalData.nsscertificate" name="loginform"
																	value="false" onclick="hideNsscertificate()"></nested:radio>
																<label for="nsscertificate1"><span><span></span></span>
																	<bean:message
																		key="knowledgepro.applicationform.No.label" /></label>
															</fieldset>
														</div>
														<div id="nsscertificate_description"
															style="<%=dynaStyle7 %>">
															Grade of Certificate: <input type="hidden" id="nssgrade"
																name="nssgrade"
																value='<bean:write name="loginform" property="personalData.nssgrades"/>' />
															<nested:select property="personalData.nssgrades" disabled="true"
																styleClass="dropdownmedium" name="loginform"
																styleId="nssgrade1">
																<html:option value="">
																	<bean:message key="knowledgepro.admin.select" />
																</html:option>
																<!--<html:option value="A">A</html:option>-->
																<html:option value="B">B</html:option>
																<!--<html:option value="C">C</html:option>-->
															</nested:select>
														</div>
													</td>
													<td><div align="left"></div></td>
													<td><div align="left"></div></td>
												</tr>
											</logic:equal>
											<logic:equal name="loginform" property="programTypeId"
												value="2">
												<tr>
													<td align="right">Holder of UG Level NSS Certificate:</td>
													<td>
														<div align="left">
															<fieldset style="border: 0px">
																<nested:radio styleId="nsscertificate" disabled="true"
																	property="personalData.nsscertificate" name="loginform"
																	value="true"></nested:radio>
																<label for="nsscertificate"><span><span></span></span>
																	<bean:message
																		key="knowledgepro.applicationform.yes.label" /></label>
																<nested:radio styleId="nsscertificate1" disabled="true"
																	property="personalData.nsscertificate" name="loginform"
																	value="false"></nested:radio>
																<label for="nsscertificate1"><span><span></span></span>
																	<bean:message
																		key="knowledgepro.applicationform.No.label" /></label>
															</fieldset>
														</div>
													</td>
													<td><div align="left"></div></td>
													<td><div align="left"></div></td>
												</tr>
											</logic:equal>





											<!--  this one we are not using -->
											<tr style="display: none;">

												<%String dynaStyle3="display:none;"; %>
												<logic:equal value="true" property="sportsPerson"
													name="loginform">
													<%dynaStyle3="display:block;"; %>
												</logic:equal>
												<td align="right"><bean:message
														key="knowledgepro.applicationform.sports.label" /></td>

												<td><div align="left">
														<input type="hidden" id="hiddenSportsPerson"
															name="hiddenSportsPerson"
															value='<bean:write name="loginform" property="personalData.sportsPerson"/>' />


														<fieldset style="border: 0px">
															<nested:radio property="personalData.sportsPerson" disabled="true"
																styleId="sportsPersonYes" name="loginform" value="true"
																onclick="showSportsDescription()"></nested:radio>
															<label for="sportsPersonYes"><span><span></span></span>
																<bean:message
																	key="knowledgepro.applicationform.yes.label" /></label>


															<nested:radio property="personalData.sportsPerson" disabled="true"
																styleId="sportsPersonNo" name="loginform" value="false"
																onclick="hideSportsDescription()"></nested:radio>
															<label for="sportsPersonNo"><span><span></span></span>
																<bean:message
																	key="knowledgepro.applicationform.No.label" /></label>
														</fieldset>

													</div>

													<div align="left" id="sports_description"
														style="<%=dynaStyle3 %>">
														<nested:text styleId="sportsDescription" disabled="true"
															property="personalData.sportsDescription"
															name="loginform" maxlength="80" styleClass="textbox"></nested:text>
													</div></td>


											</tr>
											<tr><td colspan="4" style="text-align: center;"><div class="btn btn-danger ">
						<span class="form-controler" onclick="goToHomePage()">Cancel</span>
					</div></td> </tr>
										</table></td>
								</tr>

							</table>

						</td>
					</tr>
				</div>
			</div>
		</div>

	</div>


</html:form>
