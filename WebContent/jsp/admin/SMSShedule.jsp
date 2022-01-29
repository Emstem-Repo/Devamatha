<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">
	function getClasses(year) {
		getClassesByYearInMuliSelect("classMap", year, "selectedClasses",
				updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMapForMultiSelect(req, "selectedClasses");
	}
</script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<html:form action="/sendSMSShedule" method="post">
	<html:hidden property="formName" value="sheduledForm" />
	<html:hidden property="method" styleId="method"
		value="sheduledSMS" />
	<html:hidden property="pageType" value="1" />
	<table width="98%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.admin" /><span
				class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.smsSendForAClass.lable" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.admission.smsSendForAClass.lable" /></strong></div>
					</td>
					<td width="13"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">
					<table width="100%" height="185" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6" align="left">
							<div align="right" style="color: red"><span
								class='MandatoryMark'>* Mandatory fields</span></div>
							<div id="err"
								style="color: red; font-family: arial; font-size: 11px;"></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td height="35" colspan="6" class="body">
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
											<td height="25" class="row-odd" valign="top">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.interview.Year" /></div>
											</td>
											<td class="row-even" align="left" valign="top"><input
												type="hidden" id="tempyear" name="tempyear"
												value='<bean:write name="sheduledForm" property="year"/>' />
											<html:select property="year" styleClass="combo"
												styleId="year" onchange="getClasses(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.select" />-</html:option>
												<cms:renderAcademicYear></cms:renderAcademicYear>
											</html:select></td>

											<td height="25" class="row-odd" valign="top">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.attendance.class.col" /></div>
											</td>
											<td class="row-even" align="left"><html:select
												property="classIds" styleId="selectedClasses"
												styleClass="body" multiple="multiple" size="6" style="width:350px">
												<html:option value=""><bean:message key="knowledgepro.pettycash.Select"/> </html:option>
												<html:optionsCollection name="sheduledForm"
													property="classMap" label="value" value="key" />
											</html:select></td>
										</tr>
										<tr>
											<td height="23" class="row-odd" align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.mail.message" /></td>
											<td height="23" class="row-even" align="left"><nested:textarea
												property="message" cols="25" rows="4"></nested:textarea></td>
											<td class="row-odd" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.interview.Timings"/>:</td>
											<td class="row-even">
											Date :<html:text property="date" name="sheduledForm" styleId="date" size="10" maxlength="10"></html:text>
											 <script
												language="JavaScript">
													new tcal( {
														// form name
														'formname' :'sheduledForm',
														// input name
														'controlname' :'date'
													});
												</script>
											Time <html:text property="hours" name="sheduledForm" size="1" maxlength="2"></html:text> :
											<html:text property="min" name="sheduledForm" size="1" maxlength="2"/></td>	
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
						<tr>
							<td height="35" colspan="6" class="body">
							<table width="100%" height="27" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="45%">
									<div align="right"><html:submit property=""
										styleClass="formbutton" value="Save"></html:submit></div>
									</td>
									<td width="2%"></td>
									<td width="53%" height="45" align="left"><html:button
										property="" styleClass="formbutton" value="Reset"
										onclick="resetFieldAndErrMsgs()"></html:button></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>