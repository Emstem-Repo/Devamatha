<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="javascript" type="text/javascript"
	src="js/datetimepicker.js"></script>
<script language="javascript" type="text/javascript"
	src="js/calendar.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function submitInstallMentPaymentDetails() {
		document.getElementById("method").value = "submitInstallMentPaymentDetails";
		document.installmentPaymentForm.submit();
	}
	function calculateTotal(cnt) {
		var size = parseInt(document.getElementById("length").value);
		var total = 0;
		for ( var count = 0; count <= size - 1; count++) {
			var paidAmount = parseFloat(document
					.getElementById("paymentTOList[" + cnt + "].accountList[" + count + "].paidAmount").value);	
			if (isNaN(paidAmount) || paidAmount == null) {
				paidAmount = 0;
			}
			total = total + paidAmount;
		}
		document.getElementById("paymentTOList_" + cnt + "_totalAmount").value = total;
	}
</script>
<html:form action="/installmentPayment" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="installmentPaymentForm" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.fee"/> &gt;&gt; <bean:message key="knowledgepro.fee.installment.payment"/> &gt;&gt;</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message key="knowledgepro.fee.installment.payment"/></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatorymark"/> </div>
					<div id="errorMessage"><FONT color="green"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td width="34%">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="23%" class="row-odd">
											<div align="right"><c:choose>
												<c:when test="${installmentPaymentForm.searchBy == '1'}">  
	           									Application No.:
	           									</c:when>
												<c:when test="${installmentPaymentForm.searchBy == '2'}">  
	           									<bean:message key="knowledgepro.fee.reg.no.col"/>
	           									</c:when>
												<c:when test="${installmentPaymentForm.searchBy == '3'}">  
	           									<bean:message key="admissionForm.studentedit.roll.label"/>
	           									</c:when>
											</c:choose></div>
											</td>
											<td class="row-even" colspan="3"><bean:write
												name="installmentPaymentForm" property="searchByValue" /></td>
											<td width="20%" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.admission.date"/> </div>
											</td>
											<td width="30%" height="25" class="row-even">
											<table width="82" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="60"><html:text property="date"
														readonly="true" size="10" maxlength="10" styleId="date"></html:text></td>
													<td width="40"><script language="JavaScript">
										new tcal( {
										// form name
										'installmentPaymentForm' :'installmentPaymentForm',
										// input name
										'controlname' :'date'
										});
										</script></td>
												</tr>
											</table>
											</td>
											<td width="60" class="row-even"></td>
										</tr>
										<tr>
											<td width="23%" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.admin.name.colon"/> </div>
											</td>
											 <c:set var="globalcount" value="0"/>
											<td class="row-even" colspan="3"><bean:write
												name="installmentPaymentForm" property="studentName" /></td>
											<td width="20%" class="row-odd">
											<div align="right"></div>
											</td>
											<td class="row-even" colspan="3"></td>
										</tr>
										<logic:notEmpty name="installmentPaymentForm"
											property="paymentTOList">
											<nested:iterate id="install" name="installmentPaymentForm"
												property="paymentTOList" indexId="cnt">

												<tr>
													<td width="20%" class="row-odd">
													<div align="right"><bean:message key="knowledgepro.fee.given.amount.col"/> </div>
													</td>
													<td class="row-even" colspan="3"><nested:write
														name="install" property="installMentAmount" /></td>
													<td width="20%" class="row-odd">
													<div align="right"></div>
													</td>
													<td class="row-even" colspan="3"></td>
												</tr>
												<tr>
													<td width="23%" class="row-odd">
													<div align="right"><bean:message key="knowledgepro.admission.date"/></div>
													</td>
													<td class="row-odd" colspan="3">
													<div align="right"><bean:message key="knowledgepro.adimin.feeaccount.name"/> </div>
													</td>
													<td width="20%" class="row-odd" colspan="2">
													<div align="center"><bean:message key="knowledgepro.admission.amt"/> </div>
													</td>
													<td width="20%" class="row-odd" colspan="2">
													</td>
												</tr>
												<logic:notEmpty name="install" property="feePaidDateTOList">
													<nested:iterate name="install" property="feePaidDateTOList"
														id="date">
														<tr>
															<td width="23%" rowspan="3" class="row-even">
															<div align="right"><nested:write name="date"
																property="paidDate" /></div>
															</td>
															<logic:notEmpty name="date" property="accountList">
																<nested:iterate name="date" property="accountList"
																	id="account">
																	<tr>
																		<td class="row-even" colspan="3">
																		<div align="right"><nested:write name="account"
																			property="accName" /></div>
																		</td>
																		<td width="20%" class="row-even" colspan="3">
																		<div align="center"><nested:write name="account"
																			property="amount" /></div>
																		</td>
																		<td width="20%" class="row-even" colspan="3"></td>
																	</tr>
																</nested:iterate>
															</logic:notEmpty>
														</tr>
													</nested:iterate>
												</logic:notEmpty>
												<tr>
													<td width="20%" class="row-odd">
													<div align="right"><bean:message key="knowledgepro.fee.payment.type.col"/> </div>
													</td>
													<td class="row-even"><nested:select
														property="payMentTypeId" styleClass="combo"
														styleId="payMentType">
														<html:option value="">
															<bean:message key="knowledgepro.admin.select" />
														</html:option>
														<logic:notEmpty name="installmentPaymentForm"
															property="paymentTypeList">
															<html:optionsCollection property="paymentTypeList"
																name="installmentPaymentForm" label="name" value="id" />
														</logic:notEmpty>
													</nested:select></td>
													<td width="23%" class="row-even">
													<div align="right"></div>
													</td>
													<td class="row-even" colspan="3"></td>
													<td class="row-even" colspan="3"></td>
												</tr>
												<tr>
													<td width="20%" class="row-odd">
													<div align="right"><bean:message key="knowledgepro.fee.concession.slip.book.year.col"/> </div>
													</td>
													<td class="row-even" colspan="3"><nested:select
														property="financialYear" styleClass="combo" styleId="year">
														<html:option value="">
															<bean:message key="knowledgepro.admin.select" />
														</html:option>
														<cms:renderFinancialYear>
														</cms:renderFinancialYear>
													</nested:select></td>
													<td class="row-odd">
													<div align="right"><bean:message key="knowledgepro.fee.reference.no.col"/> </div>
													</td>
													<td class="row-even" colspan="2"><nested:text
														property="referenceNo" maxlength="15" size="15"
														styleClass="TextBox"></nested:text></td>
												</tr>

												<tr>
													<td class="row-odd" colspan="4">
													<div align="Center"><bean:message key="knowledgepro.adimin.feeaccount.name"/> </div>
													</td>
													<td width="20%" class="row-odd" colspan="2">
													<div align="center"><bean:message key="knowledgepro.fee.balance.amount"/> </div>
													</td>
													<td width="20%" class="row-odd" colspan="2">
													<div align="center"><bean:message key="knowledgepro.fee.paid.amount"/> </div>
													</td>
												</tr>	
												<c:set var="temp" value="0" />											
												<logic:notEmpty name="install" property="accountList">
													<nested:iterate name="install" property="accountList"
														indexId="count" id="acc">
														<c:set var="innerCount" value="${count}"/>
														<tr>
															<td class="row-even" colspan="4">
															<div align="center"><nested:write
																property="accName" /></div>
															</td>
															<td class="row-even" colspan="2">
															<div align="center"><nested:write
																property="balanceAmount" /></div>
															</td>
															<td class="row-even" colspan="2">
															<div align="center">
															
															<input type="text" name="paymentTOList[<c:out value="${globalcount}"/>].accountList[<c:out value="${innerCount}"/>].paidAmount" id="paymentTOList[<c:out value="${globalcount}"/>].accountList[<c:out value="${innerCount}"/>].paidAmount" 
																value="<bean:write name="acc" property="paidAmount"/>" maxlength="8" size="8" class="TextBox" onblur="calculateTotal(<%= cnt %>)">															
																</div>
															</td>
														</tr>
														<c:set var="innerCount" value="${innerCount + 1}"/>
														<c:set var="temp" value="${temp+1}" />
													</nested:iterate>
												</logic:notEmpty>
												<tr>
													<td class="row-even" colspan="4">
													<div align="right"><strong><bean:message key="knowledgepro.admin.total"/> </strong></div>
													</td>
													<td class="row-even" colspan="2" align="center">
													<div align="center"></div>
													</td>
													<td class="row-even" colspan="2">
													<div align="center">
													<input
												type="hidden" name="length" id="length"
												value="<c:out value="${temp}"/>" />
													<nested:text
														property="totalAmount" maxlength="8" size="8" styleClass="TextBox" styleId="<%="paymentTOList_"+cnt+"_totalAmount"%>"></nested:text></div>
													</td>
												</tr>
												<c:set var="globalcount" value="${globalcount + 1}"/>
											</nested:iterate>
										</logic:notEmpty>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
			<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="53%" height="35" align="right"><html:button
								property="" styleClass="formbutton"
								onclick="submitInstallMentPaymentDetails()">
								<bean:message key="knowledgepro.submit" />
							</html:button></td>
							<td width="28%" height="35" align="center"></td>
							<td width="19%" height="35" align="left">&nbsp;</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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