<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<head>

</head>
<script type="text/javascript">
function getProfessionalExperienceList(){
	document.getElementById("method").value="getProfessionalExperienceList";
	document.OnlineResumerSubmissionForm.submit();
}

function getAchievementsList(){
	document.getElementById("method").value="getAchievementsList";
	document.OnlineResumerSubmissionForm.submit();
}
function saveOnlineResume(){
	document.getElementById("method").value="saveOnlineResume";
	document.OnlineResumerSubmissionForm.submit();
}
	var education="education";
	function getEducation(qualificationId, educationId) {
	getEducationByQualificationId("educationMap", qualificationId, "educationId" , updateEducation);
	}
	function updateEducation(req) {
		updateOptionsFromMap(req, "educationId", "--Select--");
		
	}
	function getAge(date){
		var date1=date.split("/");
		if(date1.length>1)
		displayage(date1[2], date1[1],date1[0], "years", 0, "rounddown");
	}
	function getStates(country) {
		getStatesByCountry("stateMap",country,"stateId" , updateStates);
		}
	function updateStates(req) {
		updateOptionsFromMapWithOther(req, "stateId", "--Select--");
			
		}
	function showOther(srcid,destid){
			 document.getElementById(destid).style.display = "block";
		}
	function hideOther(id,destid){
			 document.getElementById(destid).style.display = "none";
		}
	function funcOtherShowHide(id,destid){
			var selectedVal=document.getElementById(id).value;
			if(selectedVal=="Other"){
				showOther(id,destid);
			}else{
				hideOther(id,destid);
			}
		}
</script>
<html:form action="/onlineResumerSubmission"
	enctype="multipart/form-data">
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="OnlineResumerSubmissionForm" />
<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.employee.onlineresume.label" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.employee.onlineresume.label" /></strong></div>
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
							<table width="100%" height="819" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="57" valign="top">
									<table width="100%" height="192" border="0" cellpadding="0"
										cellspacing="1">
										<tr>
											<td width="24%" height="23" class="row-odd" valign="top">
											<div align="right"><span class="Mandatory">*</span>Name.:</div>
											</td>
											<td height="23" class="row-even" valign="top"><html:text
												property="name" styleClass="TextBox" size="35"
												maxlength="50"  /></td>
											<td width="20%" height="20" class="row-odd" valign="top">
											<div align="right">Address:</div>
											</td>
											<td width="34%" class="row-even">
											<p><html:text property="addressLine1"
												styleClass="TextBox" size="35" maxlength="100"
												 /></p>
											<p><html:text property="addressLine2"
												styleClass="TextBox" size="35" maxlength="100"
												 /></p>
											<p><html:text property="addressLine3"
												styleClass="TextBox" size="35" maxlength="100"
												 /></p>
											</td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right">Nationality:</div>
												</td>
												<td height="25" class="row-even"><nested:select
													property="nationalityId" styleClass="combo">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<logic:notEmpty name="OnlineResumerSubmissionForm"
														property="listNationalityMap">
														<html:optionsCollection name="OnlineResumerSubmissionForm"
															property="listNationalityMap" label="value" value="key" />
													</logic:notEmpty>
												</nested:select></td>
												<td height="23" class="row-odd">
												<div align="right">Zip Code:</div>
												</td>
												<td height="23" class="row-even"><html:text
													property="zipCode" styleClass="TextBox" size="10"
													maxlength="10"  onkeypress="return isNumberKey(event)"/></td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right">Gender:</div>
												</td>
												<td height="25" class="row-even">
												<html:radio property="gender" value="Male"/> Male&nbsp; 
												<html:radio property="gender" value="Female"/>
												Female</td>
												<td height="23" class="row-odd">
												<div align="right"><span class="Mandatory">*</span>Country:</div>
												</td>
												<td height="23" class="row-even"><nested:select
													property="countryId" styleId="country" styleClass="combo" onchange="getStates(this.value)">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<logic:notEmpty name="OnlineResumerSubmissionForm"
														property="listCountryMap">
														<html:optionsCollection name="OnlineResumerSubmissionForm"
															property="listCountryMap" label="value" value="key" />
													</logic:notEmpty>
												</nested:select></td>
											</tr>
											<tr class="row-even">
												<td height="25" class="row-odd">
													<div align="right">Marital Status:</div>
												</td>
												<td height="25" class="row-even">
													<nested:select property="maritalStatus" styleId="maritalStatus" styleClass="combo">
														<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
														<html:option value="Single">Single</html:option>
														<html:option value="Married">Married</html:option>
													</nested:select>
												</td>
												<td  height="25" class="row-odd">
													<div align="right"><bean:message
													key="knowledgepro.admin.state" /></div>
												</td>
												<td class="row-even" align="left">
													<%String dynaStyle=""; %>
													<logic:equal value="Other" property="state" name="OnlineResumerSubmissionForm">
														<%dynaStyle="display:block;"; %>
													</logic:equal>
													<logic:notEqual value="Other" property="state" name="OnlineResumerSubmissionForm">
														<%dynaStyle="display:none;"; %>
													</logic:notEqual>
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
	                            					<tr>
	                            						<td><html:select property="state" styleClass="combo" styleId="stateId" onchange="funcOtherShowHide('stateId','otherStateId')">
															<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
													
															<c:if test="${OnlineResumerSubmissionForm.countryId != null && OnlineResumerSubmissionForm.countryId != ''}">
																<c:set var="stateMap" value="${baseActionForm.collectionMap['stateMap']}" />
																<c:if test="${stateMap != null}">
																	<html:optionsCollection name="stateMap" label="value" value="key" />
																</c:if>
															</c:if>
	
													
															<html:option value="Other"><bean:message key="knowledgepro.admin.Other" /></html:option>
															</html:select>
														</td>
													</tr>
												<tr><td><html:text property="otherState" size="10" maxlength="30" styleId="otherStateId" style="<%=dynaStyle %>"></html:text></td></tr>
												</table>
												</td>
										</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right"><span class="Mandatory">*</span>Date
												of Birth:</div>
												</td>
												<td width="23%" height="25" class="row-even">
												<table width="82" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="60"><html:text property="dateOfBirth"
															styleId="dateOfBirth" styleClass="TextBox" size="10"
															maxlength="10"   onchange="getAge(this.value)"/></td>
														<td width="40"><script language="JavaScript">
															new tcal ({
																// form name
																'formname': 'OnlineResumerSubmissionForm',
																// input name
																'controlname': 'dateOfBirth'
															});</script></td>
														<td></td>
													</tr>
												</table>
												</td>
												<td height="23" class="row-odd">
												<div align="right">City:</div>
												</td>
												<td height="23" class="row-even"><html:text
													property="city" styleClass="TextBox" size="15"
													maxlength="50"  /></td>
											</tr>
											<tr>
												<td height="25" class="row-odd">
												<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.onlineresume.age"/> </div>
												</td>
												<td width="22%" height="25" class="row-even"><html:text styleId="age"
													property="age" styleClass="TextBox" size="10" maxlength="3"
													 readonly="true"/></td>
												<td height="20" class="row-odd">
												<div align="right"><span class="mandatoryfield">*</span>Phone:</div>
												</td>
												<td height="20" class="row-even"><html:text
													property="phone1" styleClass="TextBox" size="8"
													maxlength="4"  onkeypress="return isNumberKey(event)"/> <html:text
													property="phone2" styleClass="TextBox" size="8"
													maxlength="10"  onkeypress="return isNumberKey(event)"/>
												<p><html:text property="phone3" styleClass="TextBox"
													size="15" maxlength="10" onkeypress="return isNumberKey(event)" />
												</td>
											</tr>
											<tr>
												<td height="20" class="row-odd">
												<div align="right"><span class="mandatoryfield">*</span>E-mail:</div>
												</td>
												<td height="20" class="row-even"><html:text
													property="email" styleClass="TextBox" size="15"
													maxlength="50"  /></td>
												<td height="20" class="row-odd">
												<div align="right">Mobile:</div>
												</td>
												<td height="20" class="row-even"><html:text
													property="mobPhone1" styleClass="TextBox" size="3"
													maxlength="4"  onkeypress="return isNumberKey(event)"/> <html:text
													property="mobPhone2" styleClass="TextBox" size="12"
													maxlength="10"  onkeypress="return isNumberKey(event)"/>
												</td>
											</tr>
											
									</table>
									</td>
									<td background="images/right.gif" width="5" height="57"></td>
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
							<td colspan="2" class="heading">Desired Job Structure</td>
						</tr>
						<tr>
							<td class="heading">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="25" valign="top">
									<table width="100%" height="150" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="37%" height="33" class="row-odd">
											<div align="right">Job Type:</div>
											</td>
											<td width="63%" height="33" class="row-even">
											<html:select property="jobType" styleId="jobType" styleClass="combo">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
												<html:optionsCollection property="listOfJobType" label="name" value="id"/>
											</html:select>
											</td>
										</tr>
										<tr class="row-white">
											<td height="20" class="row-odd">
											<div align="right">Employment Status:</div>
											</td>
											<td width="63%" height="20" class="row-even"><html:radio
												property="employmentStatus" value="Full Time" /> Full Time
											<html:radio property="employmentStatus" value="Part Time" />
											Part Time <html:radio property="employmentStatus"
												value="Either" /> Either</td>
										</tr>
										<tr class="row-white">
											<td height="30" class="row-odd">
											<div align="right">Expected salary(Per Annum):</div>
											</td>
											<td height="30" class="row-even">
											<input type="hidden" id="expectedSalary" name="expectedSalary" 
											 value='<bean:write name="OnlineResumerSubmissionForm" property="expectedSalaryLack"/>' />
											 <input type="hidden" id="esThousands" name="esThousands" 
											 value='<bean:write name="OnlineResumerSubmissionForm" property="expectedSalaryThousands"/>' />
											<html:select
												property="expectedSalaryLack" styleId="expectedSalaryLack"
												name="OnlineResumerSubmissionForm" styleClass="combo">
												<html:option value="">lacks</html:option>
												<cms:experienceMonth expLimit="45" lack="true"></cms:experienceMonth>
											</html:select>Lacks <html:select property="expectedSalaryThousands" styleId="expectedSalaryThousands"
												name="OnlineResumerSubmissionForm" styleClass="combo">
												<option value="">thousands</option>
												<cms:experienceMonth expLimit="100" thousands="true"></cms:experienceMonth>
											</html:select>Thousands</td>
										</tr>
										<tr class="row-white">
											<td width="37%" height="27" class="row-odd">
											<div align="right">Desired Post :</div>
											</td>
											<td width="63%" height="27" class="row-even"><nested:text property="desiredPost" size="30" /></td>
										</tr>
										<tr class="row-white">
											<td width="37%" height="24" class="row-odd">
											<div align="right">Department Applied For:</div>
											</td>
											<td width="63%" height="24" class="row-even"><nested:select
												property="departmentAppliedFor" styleClass="combo">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="OnlineResumerSubmissionForm"
													property="listDepartmentMap">
													<html:optionsCollection name="OnlineResumerSubmissionForm"
														property="listDepartmentMap" label="value" value="key" />
												</logic:notEmpty>
											</nested:select></td>
										</tr>
										<tr class="row-white">
											<td width="37%" height="24" class="row-odd">
											<div align="right">Date of Joining(if Selected):</div>
											</td>
											<td width="23%" height="25" class="row-even">
											<table width="82" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="60"><html:text property="dateOfJoining"
														styleClass="TextBox" size="10" maxlength="10"
														 /></td>
													<td width="40"><script language="JavaScript">
								new tcal ({
									// form name
									'formname': 'OnlineResumerSubmissionForm',
									// input name
									'controlname': 'dateOfJoining'
								});</script></td>
												</tr>
											</table>
											</td>
										</tr>
										<tr class="row-white">
											<td width="37%" height="27" class="row-odd">
											<div align="right">How did you get the information
											about the vacancy available at Christ University:</div>
											</td>
											<td width="63%" height="27" class="row-even"><html:text
												property="vacancyType" styleClass="TextBox" size="10"
												maxlength="50"  /></td>
										</tr>
										<tr class="row-white">
											<td width="37%" height="27" class="row-odd">
											<div align="right">Recommended By:</div>
											</td>
											<td width="63%" height="27" class="row-even"><html:text
												property="recommendedBy" styleClass="TextBox" size="30"
												maxlength="10"  /></td>
										</tr>

									</table>
									</td>
									<td background="images/right.gif" width="5" height="31"></td>
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
							<td colspan="2" class="heading">Educational Details</td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="31" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td class="row-odd">Qualification</td>
											<td height="25" class="row-odd">Course</td>
											<td class="row-odd">Year of Passing</td>
											<td class="row-odd">
											<div align="center">Grade %</div>
											</td>
											<td width="21%" class="row-odd">
											<div align="left">Name of the Institute/University</div>
											</td>
										</tr>
										<nested:iterate property="listOfEdicationDetails" id="listOfEdicationDetails" indexId="edcount">
											<c:choose>
												<c:when test="${edcount%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>

											<td align="center" class="bodytext">
											<bean:write
												name="listOfEdicationDetails" property="courseName" /></td>
											<td align="center" class="bodytext">
											<nested:text  property="course" size="20" /></td>

											<td align="center" class="bodytext">
											<%String dynaYearId="YOP"+edcount; %>
											<c:set var="dyopid"><%=dynaYearId %></c:set>
											<nested:select property="yearOfPassing" styleId="<%=dynaYearId %>" styleClass="comboSmall">
												<html:option value="">Select</html:option>
								              	<cms:renderYear normalYear="true"></cms:renderYear>
											</nested:select>
											<script type="text/javascript">
												var yopid= '<nested:write property="yearOfPassing"/>';
												if(yopid!=null && yopid!='')
												document.getElementById("<c:out value='${dyopid}'/>").value = yopid;
											</script>
											</td>
											<td align="center" class="bodytext">
											<nested:text  property="grade" size="10" maxlength="10" onkeypress="return isNumberKey(event)"/></td>
											<td align="center" class="bodytext">
											<nested:text  property="instituteUniversity" size="30" /></td>
											</tr>
										</nested:iterate>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="31"></td>
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
							<td class="heading">&nbsp;Professional Experience</td>

						</tr>
						<logic:notEmpty property="listOfProfessionalExperience"
							name="OnlineResumerSubmissionForm">
							<nested:iterate property="listOfProfessionalExperience" indexId="count">
								<tr>
									<td class="heading">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td><img src="images/01.gif" width="5" height="5"></td>
											<td width="914" background="images/02.gif"></td>
											<td><img src="images/03.gif" width="5" height="5"></td>
										</tr>
										<tr>
											<td width="5" background="images/left.gif"></td>
											<td height="57" valign="top">
											<table width="100%" height="57" border="0" cellpadding="0"
												cellspacing="1">
												<tr class="row-white">
													<td width="25%" height="23" class="row-odd">
													<div align="right">Currently Working:</div>
													</td>
													<td width="21%" height="23" class="row-even"><nested:radio
														property="currentlyWorking" value="Yes" /> Yes&nbsp; <nested:radio
														property="currentlyWorking" value="No" /> No</td>
													<td width="25%" height="20" class="row-odd">
													<div align="right">Teaching Experience:</div>
													</td>
													<td width="25%" height="20" class="row-even">
													<%
										            	String s1 = "texpYear_" + count;
														String s2 = "texpYear1_" + count;
													%>
													<input type="hidden" id='<%=s1%>' name="expectedSalary" 
											            value='<nested:write property="teachingExperienceYear"/>' />
													<nested:select
														property="teachingExperienceYear" styleId='<%=s2%>' styleClass="combo">
														<option value="">Select</option>
														<cms:experienceMonth expLimit="30" year="true"></cms:experienceMonth>
													</nested:select> 
													
													<script type="text/javascript">
													  var tey = document.getElementById("texpYear_<c:out value='${count}'/>").value;
														if (tey != null && tey.length != 0) {
															document.getElementById("texpYear1_<c:out value='${count}'/>").value = tey;
														}
													</script>
													Years 
													<%
										            	String s3 = "texpMonth_" + count;
														String s4 = "texpMonth1_" + count;
													%>
													<input type="hidden" id='<%=s3%>' name="expectedSalary" 
											            value='<nested:write property="teachingExperienceMonth"/>' />
													<nested:select property="teachingExperienceMonth" styleId='<%=s4%>'
														styleClass="combo">
														<option value="">Select</option>
														<cms:experienceMonth expLimit="12" month="true"></cms:experienceMonth>
													</nested:select> Months
													<script type="text/javascript">
													 var teMonth = document.getElementById("texpMonth_<c:out value='${count}'/>").value;
														if (teMonth != null && teMonth.length != 0) {
															document.getElementById("texpMonth1_<c:out value='${count}'/>").value =teMonth;
														}
													</script>
													</td>
												</tr>
												<tr>
													<td width="25%" height="23" class="row-odd">
													<div align="right">Qualification Level:</div>
													</td>
													<td width="21%" height="23" class="row-even">
													<%
														String qualificationLeve = "getEducation(this.value, "
																			+ count.toString() + ")";
													String education = "education" + count;
													String qualifact="qualification"+count;
													%> <nested:select
														property="qualificationLevel" styleClass="combo"
														onchange="<%=qualificationLeve %>" styleId="<%=qualifact %>">
														<option value="">Select</option>
														<logic:notEmpty name="OnlineResumerSubmissionForm"
															property="listQualificationMap">
															<nested:optionsCollection
																name="OnlineResumerSubmissionForm"
																property="listQualificationMap" label="value"
																value="key" />
														</logic:notEmpty>
													</nested:select></td>
													<td width="25%" height="23" class="row-odd">
													<div align="right">Industry Experience:</div>
													</td>
													<td height="20" class="row-even">
													<%
										            	String ie1 = "ieYear_" + count;
														String ie2 = "ieYear1_" + count;
													%>
													<input type="hidden" id='<%=ie1%>' name="expectedSalary" 
											            value='<nested:write property="industryExperienceYear"/>' />
													<nested:select
														property="industryExperienceYear" styleId='<%=ie2%>' styleClass="combo">
														<option value="">Select</option>
														<cms:experienceMonth expLimit="30" year="true"></cms:experienceMonth>
													</nested:select>
													<script type="text/javascript">
													   var iey = document.getElementById("ieYear_<c:out value='${count}'/>").value;
														if (iey != null && iey.length != 0) {
															document.getElementById("ieYear1_<c:out value='${count}'/>").value = iey;
														}
													</script>
													 Years 
													 <%
										            	String ie3 = "ieMonth_" + count;
														String ie4 = "ieMonth1_" + count;
													%>
													<input type="hidden" id='<%=ie3%>' name="expectedSalary" 
											            value='<nested:write property="industryExperienceMonth"/>' />
													 <nested:select property="industryExperienceMonth" styleId='<%=ie4%>'
														styleClass="combo">
														<option value="">Select</option>
														<cms:experienceMonth expLimit="12" month="true"></cms:experienceMonth>
													</nested:select> Months
													<script type="text/javascript">
													   var iem = document.getElementById("ieMonth_<c:out value='${count}'/>").value;
														if (iem != null && iem.length != 0) {
															document.getElementById("ieMonth1_<c:out value='${count}'/>").value = iem;
														}
													</script>
													</td>
												</tr>
												<tr class="row-white">
													<td width="25%" height="23" class="row-odd">
													<div align="right">Education:</div>
													</td>
													<td width="21%" height="23" class="row-even">
													
													<nested:select property="education" styleId="educationId" styleClass="combo">
														<c:if test="${educationMap != null}">
															<html:optionsCollection name="educationMap" label="value"
																value="key" />
														</c:if>
													</nested:select>
													<script type="text/javascript">
													var id=document.getElementById("qualification<c:out value='${count}'/>").value;
													if(id!=null && id!=''){
														getEducation(id,'<c:out value="${count}"/>');
														}
													</script>
													<script type="text/javascript">
														var eid= '<nested:write property="education"/>';
													</script>
													</td>
													<td height="20" class="row-odd">
													<div align="right"><span class="mandatoryfield">*</span>Total
													Experience in:</div>
													</td>
													<td height="20" class="row-even">
													 <%
										            	String te1 = "teYear_" + count;
														String te2 = "teYear1_" + count;
													%>
													<input type="hidden" id='<%=te1%>' name="expectedSalary" 
											            value='<nested:write property="totalExperienceYear"/>' />
													<nested:select
														property="totalExperienceYear" styleClass="combo" styleId="<%=te2 %>">
														<option value="">Select</option>
														<cms:experienceMonth expLimit="30" year="true"></cms:experienceMonth>
													</nested:select> Years 
													<script type="text/javascript">
													   var tey = document.getElementById("teYear_<c:out value='${count}'/>").value;
														if (tey != null && tey.length != 0) {
															document.getElementById("teYear1_<c:out value='${count}'/>").value = tey;
														}
													</script>
													 <%
										            	String totalexp3 = "totalexpMonth_" + count;
														String totalexp4 = "totalexpMonth1_" + count;
													%>
													<input type="hidden" id='<%=totalexp3%>' name="expectedSalary" 
											            value='<nested:write property="totalExperienceMonth"/>' />
													<nested:select property="totalExperienceMonth"
														styleClass="combo" styleId="<%=totalexp4 %>">
														<option value="">Select</option>
														<cms:experienceMonth expLimit="12" month="true"></cms:experienceMonth>
													</nested:select> Months
													<script type="text/javascript">
													   var texpm = document.getElementById("totalexpMonth_<c:out value='${count}'/>").value;
														if (texpm != null && texpm.length != 0) {
															document.getElementById("totalexpMonth1_<c:out value='${count}'/>").value = texpm;
														}
													</script>
													</td>
												</tr>
												<tr>
													<td width="25%" height="23" class="row-odd">
													<div align="right"><span class="mandatoryfield">*</span>Functional
													Area:</div>
													</td>
													<td width="21%" height="23" class="row-even"><nested:select
														property="functionalArea" styleClass="combo">
														<option value="">Select</option>
														<logic:notEmpty name="OnlineResumerSubmissionForm"
															property="listfunctionalAreaMap">
															<nested:optionsCollection
																name="OnlineResumerSubmissionForm"
																property="listfunctionalAreaMap" label="value"
																value="key" />
														</logic:notEmpty>
													</nested:select></td>
													<td class="row-odd">
													<div align="right">Current designation:</div>
													</td>
													<td class="row-even"><nested:text
														property="currentDesignation" size="25" /></td>
												</tr>
												<tr>
													<td width="25%" height="23" class="row-odd">
													<div align="right"><span class="mandatoryfield">*</span>Organisation
													Currently Working:</div>
													</td>
													<td width="21%" height="23" class="row-even"><nested:text
														property="currentOrganisation" size="25" /></td>
													<td class="row-odd">
													<div align="right">Current Salary(Per Annum):</div>
													</td>
													<td class="row-even">
													<%
										            	String csal1 = "csalLack_" + count;
														String csal2= "csalLack1_" + count;
													%>
													<input type="hidden" id='<%=csal1%>' name="expectedSalary" 
											            value='<nested:write property="currentSalaryLack"/>' />
													<nested:select
														property="currentSalaryLack" styleClass="combo" styleId='<%=csal2 %>'>
														<option value="">Lacks</option>
														<cms:experienceMonth expLimit="30" lack="true"></cms:experienceMonth>
													</nested:select>Lacks 
													<script type="text/javascript">
													  var csaly = document.getElementById("csalLack_<c:out value='${count}'/>").value;
														if (csaly != null && csaly.length != 0) {
															document.getElementById("csalLack1_<c:out value='${count}'/>").value = csaly;
														}
													</script>
													<%
										            	String csal3 = "csalThosound_" + count;
														String csal4= "csalThosound1_" + count;
													%>
													<input type="hidden" id='<%=csal3%>' name="expectedSalary" 
											            value='<nested:write property="currentSalaryThosound"/>' />
													<nested:select property="currentSalaryThosound"
														styleClass="combo" styleId='<%=csal4 %>'>
														<option value="">thousands</option>
														<cms:experienceMonth expLimit="100" thousands="true"></cms:experienceMonth>
													</nested:select>Thousands
													<script type="text/javascript">
													  var csalm = document.getElementById("csalThosound_<c:out value='${count}'/>").value;
														if (csalm != null && csalm.length != 0) {
															document.getElementById("csalThosound1_<c:out value='${count}'/>").value = csalm;
														}
													</script>
													</td>

												</tr>
												<tr>
													<td width="25%" height="23" class="row-odd">
													<div align="right">Previous Organisation Worked:</div>
													</td>
													<td height="23" class="row-even" colspan="3"><nested:text
														property="previousOrganisation" size="30" /></td>
												</tr>
											</table>
											</td>
											<td background="images/right.gif" width="5" height="57"></td>
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
							</nested:iterate>
						</logic:notEmpty>

						<tr>
							<td class="heading">&nbsp;Achievements</td>
						</tr>


						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>

								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="31" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td height="25" class="row-odd" colspan="2">
											<div align="center">Achievements</div>
											</td>
											<td height="25" colspan="2" class="row-odd">
											<div align="center">Details</div>
											</td>
										</tr>

										<logic:notEmpty property="listOfAchievements"
											name="OnlineResumerSubmissionForm">
											<nested:iterate property="listOfAchievements"
												name="OnlineResumerSubmissionForm" id="achive"
												indexId="account">
												<tr>
													<td height="25" class="row-even" colspan="2"><nested:text
														property="achievements" size="30" /></td>
													<td height="25" class="row-even" colspan="2"><nested:text
														property="details" size="50" /></td>
												</tr>
											</nested:iterate>
										</logic:notEmpty>

										<tr>
											<td height="23" class="row-even" colspan="3"></td>
											<td height="23" class="row-even">
											<div align="right" onclick="getAchievementsList()" style="cursor: pointer">+Click
											to add more rows</div>
											</td>

										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="31"></td>
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
							<td colspan="2" class="heading">Upload Documents</td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>

								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="31" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">



										<tr>
											<td width="22%" height="25" class="row-odd">
											<div align="right">Upload Photo:</div>
											</td>
											<td width="35%" height="25" colspan="3" class="row-even"><html:file
												property="photo" styleId="photo" /></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="31"></td>
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
							<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="49%" height="35">
									<div align="right"><input name="button" type="submit"
										class="formbutton" value="Submit" onclick="saveOnlineResume()" /></div>
									</td>
									<td width="2%"></td>
									<td width="49%"><input type="button" class="formbutton" onclick="resetFieldAndErrMsgs()"
										value="Reset" /></td>
								</tr>
							</table>
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
<script type="text/javascript">
	var expectedSalary = document.getElementById("expectedSalary").value;
	if (expectedSalary != null && expectedSalary.length != 0) {
		document.getElementById("expectedSalaryLack").value = expectedSalary;
	}
	
	var esThousands = document.getElementById("esThousands").value;
	if (esThousands != null && esThousands.length != 0) {
		document.getElementById("expectedSalaryThousands").value = esThousands;
	}
function getAge(date){
	var date1=date.split("/");
	if(date1.length>1)
	displayage(date1[2], date1[1],date1[0], "years", 0, "rounddown");
}
if(eid!=null && eid!='')
	document.getElementById("educationId").value = eid;

if(document.getElementById("comSt")!=null && document.getElementById("comSt").value!=""){
	document.getElementById("stateId").value=document.getElementById("comSt").value;
	}
</script>
</html:form>