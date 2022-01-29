<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script type="text/javascript">
	function getClasses(year) {
		getClassesByYear("classMap", year, "class", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "class", "- Select -");
	}

	function getPeriodFrom(classSchemaId) {

		getPeriodsByClassSchemewiseId("periodMap", classSchemaId, "fromPeriod",
				updatePeriods);

	}
	function updatePeriods(req) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById("fromPeriod");
		var destination2 = document.getElementById("toPeriod");
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		for (x1 = destination2.options.length - 1; x1 > 0; x1--) {
			destination2.options[x1] = null;
		}

		var childNodes = responseObj.childNodes;
		destination.options[0] = new Option("-Select-", "");
		destination2.options[0] = new Option("-Select-", "");
		var items = responseObj.getElementsByTagName("option");

		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i + 1] = new Option(label, value);

		}

		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination2.options[i + 1] = new Option(label, value);

		}

		//updateOptionsFromMap(req, "fromPeriod", "- Select -");
	}

	function resetErrorMsgs() {
		resetErrMsgs();
		document.location.href="approveLeave.do?method=resetLeave";

	}

	function backToFirstPage(){
		document.location.href="approveLeave.do?method=initModifyLeave";
	}	
</script>
<html:form action="/approveLeave">
	<html:hidden property="method" styleId="method" value="updateApproveLeave" />
	<html:hidden property="pageType" value="8" />
	<html:hidden property="formName" value="approveleaveForm" />
	<input type="hidden" id="id" name="id" value="<bean:write name="approveleaveForm" property="id"/>"/>  <!-- usefull while edit -->
	
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><bean:message key="knowledgepro.attendanceentry.attendance"/><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.attendance.leavemodify.modifyleave"/> </span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.attendance.leavemodify.modifyleave"/></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">

						<tr>
							<td align="center">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								
								<tr>

									<td height="20" colspan="6" align="left">
							       <div align="right" style="color:red" class="heading"> <span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields"/></span></div>
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

											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.admin.year" />:</div>
											</td>
											<td align="left" height="25" class="row-even"><input
												type="hidden" id="tempyear" name="tempyear"
												value="<bean:write name="approveleaveForm" property="year"/>" />
											<html:select property="year" styleId="year"
												onchange="getClasses(this.value)" styleClass="combo">
												<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
												<cms:renderAcademicYear></cms:renderAcademicYear>
											</html:select></td>

											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.attendance.activityattendence.class" />:</div>
											</td>
											<td align="left" width="30%" class="row-even"><html:select
												property="classSchemewiseId" styleClass="combo"
												styleId="classSchemewiseId"
												onchange="getPeriodFrom(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.select" />-</html:option>
														<html:optionsCollection name="approveleaveForm" property="classMap" label="value"
															value="key" />
											</html:select></td>

										</tr>
										
										<tr>											
											<td width="20%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.attendance.activityattendence.leavetype" />:</div>
											</td>
											<td align="left" width="29%" class="row-even"><html:select
												property="leaveType" styleClass="combo"
												styleId="leaveType">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
												<html:optionsCollection property="leaveMap" name="approveleaveForm" label="value" value="key" />
											</html:select></td>
											<td class="row-odd">
											<td class="row-even">
										</tr>
									
										<tr>
											<td width="21%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.attendance.activityattendence.regno" />:</div>
											</td>
											<td align="left" colspan="3" class="row-even"><label>
											<html:textarea property="registerNoEntry"
												styleId="registerNoEntry" style="width: 83%" rows="3"></html:textarea>
											</label></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.admission.fromdate" />:</div>
											</td>
											<td align="left" class="row-even">
											<table width="82" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="60"><html:text property="fromDate"
														styleId="fromDate" size="10" maxlength="10"></html:text></td>
													<td width="40"><script language="JavaScript">
													new tcal( {
														// form name
														'formname' :'approveleaveForm',
														// input name
														'controlname' :'fromDate'
													});
												</script></td>
												</tr>
											</table>
											</td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.attendance.activityattendence.fromperiod" />
											:</div>
											</td>
											<td align="left" class="row-even"><html:select
												property="fromPeriod" styleClass="combo"
												styleId="fromPeriod">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select"/></html:option>
													<html:optionsCollection name="approveleaveForm" property="periodMap" label="value"	value="key" />
											</html:select></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.admission.todate" />:</div>
											</td>
											<td align="left" class="row-even">
											<table width="82" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="60"><html:text property="toDate" size="10"
														styleId="toDate" maxlength="10"></html:text></td>
													<td width="40"><script language="JavaScript">
													new tcal( {
														// form name
														'formname' :'approveleaveForm',
														// input name
														'controlname' :'toDate'
													});
												</script></td>
												</tr>
											</table>
											</td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendanceentry.toperiod"/> :</div>
											</td>
											<td align="left" class="row-even"><html:select
												property="toPeriod" styleClass="combo" styleId="toPeriod">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select"/></html:option>
																<html:optionsCollection name="approveleaveForm" property="periodMap" label="value"	value="key" />
											</html:select></td>
										</tr>
									</table>

									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>

						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						<tr>
							<td height="35" align="center"><html:submit
								styleClass="formbutton" value="Update"></html:submit>&nbsp;&nbsp;&nbsp;<html:button
								property="" styleClass="formbutton" value="Reset"
								onclick="resetErrorMsgs()"></html:button>
								&nbsp;&nbsp;&nbsp;<html:button
								property="" styleClass="formbutton" value="Cancel"
								onclick="backToFirstPage()"></html:button></td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td background="images/Tright_03_03.gif" height="19" valign="top"></td>
					<td class="heading"></td>
					<td background="images/Tright_3_3.gif" valign="top"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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