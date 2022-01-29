<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="employee.info.title" /></title>
<script language="JavaScript" src="calendar_us.js"></script>
<link rel="stylesheet" href="calendar.css">
<script language="JavaScript" src="js/employee/employeeInfo.js"></script>
<script language="JavaScript" src="js/common.js"></script>
<script type="text/javascript">
	function achieveSubmit()
	{
		document.employeeInfoForm.method.value="addAchievements";
		document.employeeInfoForm.submit();
	}

	function updateLeaveRemained(input)
	{
		var leavesAlloted=input.value-0;
		var leavesSanctioned=document.getElementById("leavesSanctioned"+input.id).value-0;
		if(leavesAlloted!=0)
			document.getElementById("leavesRemaining"+input.id).value=leavesAlloted-leavesSanctioned;
		else
			document.getElementById("leavesRemaining"+input.id).value=0;
	}

	function isNumber(event,id)
	{
		if(!isNumberKey(event))
		{
			alert("Enter Only Numbers");
			id.value=0;
		}			
	}			
	</script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<html:form action="/employeeInfoSubmit">
	<html:hidden property="method" value="" />
	<html:hidden property="pageType" value="7" />
	<html:hidden property="formName" value="employeeInfoForm" />
	<%
		String readonly = "";
	%>
	<logic:equal value="false" name="employeeInfoForm" property="adminUser">
		<%
			readonly = "readonly";
		%>
	</logic:equal>

	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="employee.info.title" /><span class="Bredcrumbs"></span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td colspan="3" background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="employee.info.title" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" colspan="3" class="news">
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="3" valign="top" class="news">
					<div id="errorMessage"><html:errors /><FONT color="green">
					<html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="3" valign="top" class="news">
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
									<td width="27%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="employee.info.code" /></div>
									</td>
									<td class="row-even" colspan="3"><span class="star">
									<html:text property="employeeDetail.code" styleClass="TextBox"
										size="10" maxlength="10" readonly="true"></html:text> </span></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="employee.info.firstName" /></div>
									</td>
									<td width="22%" class="row-even"><span class="star">
									<html:text property="employeeDetail.firstName"
										styleClass="TextBox" size="15" maxlength="30" readonly="true"></html:text>
									</span></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="employee.info.lastName" /></div>
									</td>
									<td width="29%" class="row-even"><span class="star">
									<html:text property="employeeDetail.lastName"
										styleClass="TextBox" size="15" maxlength="30" readonly="true"></html:text>

									</span></td>
								</tr>
								<tr>
									<td width="27%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="employee.info.midName" /></div>
									</td>
									<td class="row-even"><html:text
										property="employeeDetail.middleName" styleClass="TextBox"
										size="15" maxlength="20" readonly="true"></html:text></td>
									<td width="22%" class="row-odd">
									<div align="right"><bean:message
										key="employee.info.nickName" /></div>
									</td>
									<td class="row-even"><html:text
										property="employeeDetail.nickName" styleClass="TextBox"
										size="15" maxlength="30" readonly="true"></html:text></td>
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
					<td height="8" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="3" valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="3" valign="top" class="news">
					<table width="650">
						<tr>
							<td width="7%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeePersonalInfo"><img
								src="images/personal.gif" border="0"><br />
							<bean:message key="employee.info.link.Personal" /> </a></td>
							<td width="7%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeeContact"><img
								src="images/contact.gif" border="0"><br />
							<bean:message key="employee.info.link.Contact" /> </a></td>
							<td width="10%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeeEmergContact"><img
								src="images/emergency_contact.gif" border="0"><br />
							<bean:message key="employee.info.link.EmergContact" /> </a></td>
							<td width="10%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeeDependentInfo"><img
								src="images/dependants.gif" border="0"><br />
							<bean:message key="employee.info.link.Dependents" /> </a></td>
							<td width="10%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeeImmigration"><img
								src="images/immigration.gif" border="0"> <br />
							<bean:message key="employee.info.link.Immigration" /> </a></td>
							<td width="8%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeeJobInfo"><img
								src="images/job.gif" border="0"> <br />
							<bean:message key="employee.info.link.Job" /> </a></td>
							<td width="8%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeeReportTo"><img
								src="images/report-to.gif" border="0"><br />
							<bean:message key="employee.info.link.ReportTo" /> </a></td>
							<td width="9%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeeExperience"><img
								src="images/tax.gif" border="0"><br />
							<bean:message key="employee.info.link.WorkExp" /> </a></td>
							<td width="8%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeeEducationInfo"><img
								src="images/education.gif" border="0"> <br />
							<bean:message key="employee.info.link.Education" /> </a></td>
							<td width="7%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeeSkills"><img
								src="images/skills.gif" border="0"> <br />
							<bean:message key="employee.info.link.Skills" /> </a></td>
							<td width="16%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeeLanguageInfo"><img
								src="images/languages.gif" border="0"><br />
							<bean:message key="employee.info.link.Languages" /> </a></td>
							<td width="25%" class="row-white"><a
								href="employeeInfoSubmit.do?method=forwardEmployeeUserInfo"><img
								src="images/languages.gif" border="0"><br />
							User Info </a></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>


				<logic:notEmpty property="employeeDetail.jobs"
					name="employeeInfoForm">
					<nested:iterate property="employeeDetail.jobs"
						name="employeeInfoForm" id="job" indexId="count">



						<tr>

							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news">
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
									<table width="100%" height="69" border="0" cellpadding="0"
										cellspacing="1">
										<tr>
											<td width="212" height="20" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.title" /></div>
											</td>
											<td width="236" height="20" class="row-even"><logic:equal
												value="true" name="employeeInfoForm" property="adminUser">
												<nested:text property="jobTitle" styleClass="TextBox"
													size="20" maxlength="30"></nested:text>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="jobTitle" />
											</logic:equal></td>
											<td width="224" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.empStatus" /></div>
											</td>
											<td width="244" class="row-even"><logic:equal
												value="true" name="employeeInfoForm" property="adminUser">
												<nested:text property="employmentStatus"
													styleClass="TextBox" size="20" maxlength="20"></nested:text>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="employmentStatus" />
											</logic:equal></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.spec" /></div>
											</td>
											<td height="20" class="row-even"><logic:equal
												value="true" name="employeeInfoForm" property="adminUser">
												<nested:text property="jobSpecification"
													styleClass="TextBox" size="20" maxlength="30"></nested:text>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="jobSpecification" />
											</logic:equal></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.duties" /></div>
											</td>
											<td class="row-even"><logic:equal value="true"
												name="employeeInfoForm" property="adminUser">
												<nested:text property="jobDuties" styleClass="TextBox"
													size="20" maxlength="30"></nested:text>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="jobDuties" />
											</logic:equal></td>
										</tr>
										<tr class="row-even">
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.joinDt" /></div>
											</td>
											<td><logic:equal value="true" name="employeeInfoForm"
												property="adminUser">
												<nested:text property="joiningDate" styleId="joindate"
													styleClass="TextBox" size="10" maxlength="10"></nested:text>
												<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'employeeInfoForm',
											// input name
											'controlname' :'joindate'
										});
									</script>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="joiningDate" />
											</logic:equal></td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.emptype" /></div>
											</td>
											<td class="row-even"><logic:equal value="true"
												name="employeeInfoForm" property="adminUser">
												<html:select property="employeeDetail.employeeType"
													styleClass="combo" styleId="employeeType"
													name="employeeInfoForm" style="width:200px">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<logic:notEmpty name="employeeInfoForm"
														property="listEmployeeType">
														<html:optionsCollection property="listEmployeeType"
															name="employeeInfoForm" label="name" value="id" />
													</logic:notEmpty>
												</html:select>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="employementType" />
											</logic:equal></td>
										</tr>
										<tr class="row-even">
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.dept" /></div>
											</td>
											<td><logic:equal value="true" name="employeeInfoForm"
												property="adminUser">
												<html:select property="employeeDetail.departmentId"
													styleClass="combo" styleId="departmentType"
													name="employeeInfoForm" style="width:200px">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<logic:notEmpty name="employeeInfoForm"
														property="listDepartment">
														<html:optionsCollection property="listDepartment"
															name="employeeInfoForm" label="name" value="id" />
													</logic:notEmpty>
												</html:select>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="departmentType" />
											</logic:equal></td>

											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.sec.Designation" /> :</div>
											</td>
											<td><logic:equal value="true" name="employeeInfoForm"
												property="adminUser">
												<html:select property="employeeDetail.designationId"
													styleClass="combo" styleId="departmentType"
													name="employeeInfoForm" style="width:200px">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<logic:notEmpty name="employeeInfoForm"
														property="listDesignation">
														<html:optionsCollection property="listDesignation"
															name="employeeInfoForm" label="name" value="id" />
													</logic:notEmpty>
												</html:select>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="departmentType" />
											</logic:equal></td>

										</tr>
										<tr>
											<td class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.retireDt" /></div>
											</td>
											<td class="row-even">
												<logic:equal value="true" name="employeeInfoForm" property="adminUser">
													<nested:text property="dateOfRetirement"
														styleId="retiredate" styleClass="TextBox" size="10"
														maxlength="10"></nested:text>
														<script language="JavaScript">
														new tcal( {
															// form name
															'formname' :'employeeInfoForm',
															// input name
															'controlname' :'retiredate'
															});
															</script>
												</logic:equal>
												<logic:equal value="false" name="employeeInfoForm"
													property="adminUser">
													<nested:write property="dateOfRetirement" />
												</logic:equal>
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.rejoin.date" /></div>
											</td>
											<td class="row-even"><logic:equal value="true"
												name="employeeInfoForm" property="adminUser">
												<nested:text property="dateOfRejoin"
													styleId="dateOfRejoin" styleClass="TextBox" size="10"
													maxlength="10"></nested:text>
												<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'employeeInfoForm',
											// input name
											'controlname' :'dateOfRejoin'
										});
									</script>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="dateOfRejoin" />
											</logic:equal></td>
										</tr>

										<!-- lohith -->
										<tr class="row-even">
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.stream" /></div>
											</td>
											<td><logic:equal value="true" name="employeeInfoForm"
												property="adminUser">
												<html:select property="employeeDetail.stream"
													styleClass="combo" styleId="stream" name="employeeInfoForm"
													style="width:200px">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<logic:notEmpty name="employeeInfoForm" property="streamTO">
														<html:optionsCollection property="streamTO"
															name="employeeInfoForm" label="name" value="id" />
													</logic:notEmpty>
												</html:select>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="departmentType" />
											</logic:equal></td>

											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.workLocationName" /></div>
											</td>
											<td><logic:equal value="true" name="employeeInfoForm"
												property="adminUser">
												<html:select property="employeeDetail.workLocationId"
													styleClass="combo" styleId="workLocationId"
													name="employeeInfoForm" style="width:200px">
													<html:option value="">
														<bean:message key="knowledgepro.admin.select" />
													</html:option>
													<logic:notEmpty name="employeeInfoForm"
														property="listWorkLocation">
														<html:optionsCollection property="listWorkLocation"
															name="employeeInfoForm" label="name" value="id" />
													</logic:notEmpty>
												</html:select>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="departmentType" />
											</logic:equal></td>

										</tr>




										<!-- lohith -->



									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>
							<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news"></td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>

							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news">
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
									<table width="100%" height="69" border="0" cellpadding="0"
										cellspacing="1">
										<tr>
											<td class="heading"><bean:message
												key="employee.info.job.paytable.title" /></td>
										</tr>
										<%
											String dynaJS = "";
										%>
										<logic:notEmpty property="allowanceTos" name="employeeInfoForm">
											<bean:define id="listsize" property="allowanceSize"
												name="employeeInfoForm"></bean:define>
											<%
												dynaJS = "updateGrossPay('" + listsize + "')";
											%>
										</logic:notEmpty>
										<tr>
											<td width="50%" height="20" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.basicPay" /></div>
											</td>
											<td width="50%" height="20" class="row-even"><logic:equal
												value="true" name="employeeInfoForm" property="adminUser">
												<nested:text property="basicPay" styleId="basicPay"
													styleClass="TextBox" size="9" maxlength="9"
													onblur="<%=dynaJS %>"></nested:text>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="basicPay" />
											</logic:equal></td>
										</tr>
										<tr class="row-even">
											<td height="20" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.allowance" /></div>
											</td>
											<td height="20" class="row-odd"><bean:message
												key="employee.info.job.allowance.amnt" /></td>
										</tr>
										<logic:notEmpty property="allowanceTos"
											name="employeeInfoForm">

											<nested:iterate property="allowances"
												name="employeeInfoForm" id="allowance" indexId="alwCount">
												<%
													String dynaStyle = "Allowance" + alwCount;
												%>
												<tr class="row-even">
													<td height="25" class="row-odd">
													<div align="right"><nested:write property="empAllowanceName"
														name="allowance" /></div>
													</td>
													<td><logic:equal value="true" name="employeeInfoForm"
														property="adminUser">
														<nested:text property="amount" styleId="<%=dynaStyle %>"
															styleClass="TextBox" size="9" maxlength="9"
															onblur="<%=dynaJS %>" />
													</logic:equal> <logic:equal value="false" name="employeeInfoForm"
														property="adminUser">
														<nested:write property="amount" />
													</logic:equal></td>
												</tr>
											</nested:iterate>
										</logic:notEmpty>


										<tr class="row-even">
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.grosspay" /></div>
											</td>
											<td><logic:equal value="true" name="employeeInfoForm"
												property="adminUser">
												<nested:text property="grossPay" styleId="grosspay"
													styleClass="TextBox" size="10" maxlength="10"
													readonly="true"></nested:text>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="grossPay" />
											</logic:equal></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>
							<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news"></td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

						<tr>

							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="55" valign="top">
									<table width="100%" height="48" border="0" cellpadding="0"
										cellspacing="1">
										<tr>
											<td class="heading" height="25"><bean:message
												key="employee.info.job.achievements" /></td>
											<td class="heading" height="25">&nbsp;</td>
											<td class="row-white" height="25"><logic:equal
												value="true" name="employeeInfoForm" property="adminUser">
												<a href="#" onclick="achieveSubmit()">Add Achievements</a>
											</logic:equal></td>
										</tr>
										<tr>
											<td width="33%" height="20" class="row-odd"><bean:message
												key="employee.info.job.achievement.name" /></td>
											<td width="33%" class="row-odd"><bean:message
												key="employee.info.job.achievement.desc" /></td>
											<td width="33%" height="20" class="row-odd"><bean:message
												key="employee.info.job.achievement.status" /></td>
										</tr>
										<logic:notEmpty name="employeeInfoForm"
											property="achievementTOs">
											<nested:iterate name="employeeInfoForm"
												property="achievementTOs" id="achievements">
												<tr class="row-even">
													<td height="25" class="row-even"><nested:write
														property="acheivementName" /></td>
													<td><nested:write property="details" /></td>
													<td><nested:write property="status" /></td>
												</tr>
											</nested:iterate>
										</logic:notEmpty>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="55"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>









						<tr>
							<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news"></td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>


						<tr>

							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="55" valign="top">
									<table width="100%" height="48" border="0" cellpadding="0"
										cellspacing="1">
										<tr>
											<td width="33%" height="25" class="heading"><bean:message
												key="employee.info.job.resign.title" /></td>
											<td width="33%" height="25" class="heading">&nbsp;</td>
										</tr>
										<tr class="row-even">
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.resign.date" /></div>
											</td>
											<td><logic:equal value="true" name="employeeInfoForm"
												property="adminUser">
												<nested:text property="dateOfResign" styleId="resigndate"
													styleClass="TextBox" size="10" maxlength="10"></nested:text>
												<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'employeeInfoForm',
											// input name
											'controlname' :'resigndate'
										});
									</script>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="dateOfResign" />
											</logic:equal></td>
										</tr>
										<tr class="row-even">
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.resign.reason" /></div>
											</td>
											<td><logic:equal value="true" name="employeeInfoForm"
												property="adminUser">
												<nested:text property="resignationReason"
													styleClass="TextBox" size="50" maxlength="100"></nested:text>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="resignationReason" />
											</logic:equal></td>
										</tr>
										<tr class="row-even">
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="employee.info.job.leave.date" /></div>
											</td>
											<td><logic:equal value="true" name="employeeInfoForm"
												property="adminUser">
												<nested:text property="dateOfLeaving" styleId="leavedate"
													styleClass="TextBox" size="10" maxlength="10"></nested:text>
												<script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'employeeInfoForm',
											// input name
											'controlname' :'leavedate'
										});
									</script>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="dateOfLeaving" />
											</logic:equal></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="55"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>
							<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news"></td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>









						<tr>

							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="55" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td class="heading" colspan="2"><bean:message
												key="employee.info.job.leave.title" /></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td class="row-odd"><bean:message
												key="knowledgepro.attendance.activityattendence.leavetype" />
											</td>
											<td class="row-odd"><bean:message
												key="employee.info.job.leave.allocated" /></td>
											<td class="row-odd"><bean:message
												key="employee.info.job.leave.sanctioned" /></td>
											<td height="25" class="row-odd"><bean:message
												key="employee.info.job.leave.remain" /></td>
										</tr>

										<logic:notEmpty name="employeeInfoForm" property="leaveTOs">
											<nested:iterate name="employeeInfoForm" property="leaveTOs"
												id="leave" indexId="count1">

												<tr>
													<td width="5%" height="25" class="row-even">
													<div align="center"><%=count1 + 1%></div>
													</td>
													<td width="77%" class="row-even"><nested:write
														property="empLeaveTypeName" /></td>
													<td width="77%" class="row-even"><logic:equal
														value="true" name="employeeInfoForm" property="adminUser">
														<nested:text property="leavesAllocated"
															styleClass="TextBox" size="3" maxlength="3"
															styleId="<%=""+count1%>"
															onkeypress="isNumber(event,this)"
															onblur="updateLeaveRemained(this)"></nested:text>
													</logic:equal> <logic:equal value="false" name="employeeInfoForm"
														property="adminUser">
														<nested:text property="leavesAllocated"
															styleClass="TextBox" size="3" maxlength="3"
															readonly="true"></nested:text>
													</logic:equal></td>
													<td width="77%" class="row-even"><nested:text
														property="leavesSanctioned" readonly="true"
														styleClass="TextBox" size="3" maxlength="3"
														styleId="<%="leavesSanctioned"+count1%>"></nested:text></td>
													<td width="77%" height="25" class="row-even"><nested:text
														property="leavesRemaining" readonly="true"
														styleClass="TextBox" size="3" maxlength="3"
														styleId="<%="leavesRemaining"+count1%>"></nested:text></td>
												</tr>
											</nested:iterate>
										</logic:notEmpty>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="55"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>
							<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news"></td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>



						<tr>

							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="55" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">


										<tr>

											<td width="77%" height="25" class="row-even"><bean:message
												key="employee.info.job.finance.title" /></td>

										</tr>
										<tr>

											<td width="77%" height="25" class="row-even"><span
												class="row-white"> <logic:equal value="true"
												name="employeeInfoForm" property="adminUser">
												<nested:textarea property="financialAssisstance"
													styleClass="TextBox" cols="100" rows="4"></nested:textarea>
											</logic:equal> <logic:equal value="false" name="employeeInfoForm"
												property="adminUser">
												<nested:write property="financialAssisstance" />
											</logic:equal> </span></td>

										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="55"></td>
								</tr>

								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

						<tr>
							<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
							<td colspan="3" valign="top" class="news"></td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

					</nested:iterate>
				</logic:notEmpty>





				<logic:equal value="true" name="employeeInfoForm"
					property="adminUser">
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">

							<tr>
								<td height="35">
								<div align="right"><html:button property=""
									styleClass="formbutton" value="Save"
									onclick="submitEmployeeInfo('saveEmployeeJobInfo')" /></div>
								</td>
								<td width="10"><html:button property=""
									styleClass="formbutton" value="Reset"
									onclick="submitEmployeeInfo('resetJobInfo')" /></td>
								<td>
								<div align="left"><html:button property=""
									styleClass="formbutton" value="Close"
									onclick="submitEmployeeInfo('getEmployeeDetailsClose')" /></div>
								</td>
							</tr>
						</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
				</logic:equal>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td colspan="3" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
