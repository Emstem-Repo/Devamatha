<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script>
	function correctPending(){
		document.getElementById("method").value="getPendingOnlineApp";
		document.admissionFormForm.submit();
	}
	function cancelCorrection(){
		document.getElementById("method").value="initPendingOnlineApp";
		document.admissionFormForm.submit();
	}
</script>
<style>
.btnc {
	background-color: #008CBA;
	border: none;
	color: white;
	padding: 10px 18px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 14px;
	border-radius: 12px;
}

.btnc:hover{
	cursor: pointer;
}
#data {
	width: 50%;
	margin-left: 25%;
	margin-right: 25%;
	border: 1px solid white;
	background-color: #BCDEFF;
	border-radius: 12px;
	margin-top: 10%
}
#data table td{
	padding:10px;
}
</style>

<html:form action="/admissionFormSubmit">
	<html:hidden styleId="method" property="method" value="getAppplicantDetails" />
	<html:hidden property="formName" value="admissionFormForm" />
	<html:hidden property="pageType" value="27" />
	<html:hidden property="pageTogle" styleId="togle" />
	<div id="main">
		<table width="98%" border="0">
			<tr>
				<td><span class="Bredcrumbs"><bean:message
							key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;
							Online Payment Correction &gt;&gt;</span></span></td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="10"><img src="images/Tright_03_01.gif"></td>
							<td width="100%" background="images/Tcenter.gif" class="body">
								<div align="left">
									<strong class="boxheader"> Online Payment Correction </strong>
								</div>
							</td>
							<td width="10"><img src="images/Tright_1_01.gif" width="9"
								height="29"></td>
						</tr>
						<tr>
							<td valign="top" background="images/Tright_03_03.gif"></td>
							<td valign="top" class="news">
								<table width="100%" cellspacing="1" cellpadding="2">
									<tr bgcolor="#FFFFFF">
										<td height="20" colspan="4">
											<div align="right">
												<span class='MandatoryMark'><bean:message
														key="knowledgepro.mandatoryfields" /></span>
											</div>
											<div id="errorMessage">
												<FONT color="red"><html:errors /></FONT> <FONT
													color="green"> <html:messages id="msg"
														property="messages" message="true">
														<c:out value="${msg}" escapeXml="false"></c:out>
													</html:messages>
												</FONT>
											</div>
										</td>
									</tr>
									<tr>
										<td height="41" valign="top"
											background="images/Tright_03_03.gif"></td>
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
																<td align="right" class="row-odd" width=50%><span
																	class="Mandatory">*</span>Correction for :</td>
																<td class="row-even" width=50%>
																	<table>
																		<tr>
																			<td><html:radio property="correctionFor"
																					styleId="onlineAppRadioId"
																					value="Online Application">Online Application</html:radio>
																			</td>
																		</tr>
																		<%-- <tr>
																		<td>
																			<html:radio property="correctionFor" value="Regular Exam">Regular Exam</html:radio>		
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<html:radio property="correctionFor" value="Supplementary/Improvement Exam">Supplementary/Improvement Exam</html:radio>		
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<html:radio property="correctionFor" value="Miscellaneous fees">Miscellaneous fees</html:radio>		
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<html:radio property="correctionFor" value="Revaluation/Scrutiny fees">Revaluation/Scrutiny fees</html:radio>		
																		</td>
																	</tr> --%>
																	</table>
																</td>
															</tr>
															<tr>
																<td align="right" width="21%" height="25"
																	class="row-odd"><span class="Mandatory">*</span> <bean:message
																		key="knowledgepro.admission.applicationrefno" />:</td>
																<td width="29%" height="25" class="row-even"><html:text
																		property="candidateRefNo" styleId="candidateRefNo"
																		size="20" maxlength="20" name="admissionFormForm"></html:text></td>
															</tr>
															<%-- <tr>
															<td align="right" width="20%" class="row-odd">
																<span class="Mandatory">*</span><bean:message key="knowledgepro.admission.transactionrefno"/>:
															</td>
															<td width="30%" class="row-even">
																<html:text property="transactionRefNO" styleId="transactionRefNO" size="20" maxlength="20" value=""></html:text>
															</td>
														</tr> --%>
															<tr id="dateRow" style="display: none;">
																<td align="right" width="20%" class="row-odd"><span
																	class="Mandatory">*</span>Date:</td>
																<td width="30%" class="row-even"><html:text
																		property="txnDate" styleId="txnDate" name="admissionFormForm" size="20"
																		maxlength="20"></html:text> <script
																		language="JavaScript">
																			new tcal(
																					{
																						'formname' : 'admissionFormForm',
																						'controlname' : 'txnDate'
																					});
																		</script></td>
															</tr>
															<%-- <tr id="amountRow" style="display: none;">
															<td align="right" width="20%" class="row-odd">
																<span class="Mandatory">*</span>Amount:
															</td>
															<td width="30%" class="row-even">
																<html:text property="amount" styleId="amount" size="20" maxlength="20" value=""></html:text>
															</td>
														</tr> --%>
														</table>
													</td>
													<td width="5" height="29" background="images/right.gif"></td>
												</tr>
												<tr>
													<td height="5"><img src="images/04.gif" width="5"
														height="5" /></td>
													<td background="images/05.gif"></td>
													<td><img src="images/06.gif" /></td>
												</tr>
											</table>
										</td>
										<td valign="top" background="images/Tright_3_3.gif"
											class="news"></td>
									</tr>
								</table>
								<div align="center">
									<table width="100%" height="106" border="0" cellpadding="1"
										cellspacing="2">
										<tr>
											<td width="100%" height="46" class="heading">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td width="45%" height="35">
															<div align="center">
																<html:submit styleClass="formbutton">
																	<bean:message key="knowledgepro.submit" />
																</html:submit>
																<html:button property="" styleClass="formbutton"
																	onclick="resetFieldAndErrMsgs()">
																	<bean:message key="knowledgepro.admin.reset" />
																</html:button>
															</div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="10"></td>
										</tr>
									</table>
								</div>
							</td>
							<td width="10" valign="top" background="images/Tright_3_3.gif"
								class="news"></td>
						</tr>
						<tr>
							<td><img src="images/Tright_03_05.gif"></td>
							<td background="images/TcenterD.gif"></td>
							<td><img src="images/Tright_02.gif" width="9" height="29"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="data">
		<table style="width: 100%">
			<logic:notEmpty name="admissionFormForm" property="firstName">
				<tr>
					<td>Applicant name:</td>
					<td><bean:write name="admissionFormForm" property="firstName" />
					</td>
				</tr>
			</logic:notEmpty>
			<logic:notEmpty name="admissionFormForm" property="firstName">
				<tr>
					<td>Amount:</td>
					<td><bean:write name="admissionFormForm"
							property="applicationAmount" /></td>
				</tr>
			</logic:notEmpty>
			<tr>
				<td colspan="2" style="text-align: center;">
				<input type="button" value="submit"
					class="btnc" onclick="correctPending()">
				<input type="button" value="cancel"
					class="btnc" onclick="cancelCorrection()">
					</td>
			</tr>
		</table>
	</div>
</html:form>
<script>
	document.getElementById("onlineAppRadioId").checked = true;
	var rad = document.admissionFormForm.correctionFor;
	var prev = null;
	var tog=document.getElementById("togle").value;
	for (var i = 0; i < rad.length; i++) {
		rad[i].onclick = function() {
			if (document.getElementById("onlineAppRadioId").checked) {
				document.getElementById("dateRow").style.display = "table-row";
				document.getElementById("amountRow").style.display = "table-row";
			} else {
				document.getElementById("dateRow").style.display = "none";
				document.getElementById("amountRow").style.display = "none";
			}
		};
	}
	if (document.getElementById("onlineAppRadioId").checked) {
		document.getElementById("dateRow").style.display = "table-row";
		//document.getElementById("amountRow").style.display = "table-row";
	} else {
		document.getElementById("dateRow").style.display = "none";
		document.getElementById("amountRow").style.display = "none";
	}
	if (tog=='first') {
		document.getElementById("main").style.display = "block";
		document.getElementById("data").style.display = "none";
	}
	if (tog=='confirm') {
		document.getElementById("data").style.display = "block";
		document.getElementById("main").style.display = "none";
	}
	console.log('<bean:write name="admissionFormForm" property="firstName" />');
</script>