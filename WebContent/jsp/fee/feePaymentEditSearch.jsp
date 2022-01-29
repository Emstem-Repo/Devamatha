<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function resetMessages() {	
		document.getElementById("bno").value= "";
		document.getElementById("year").value = resetYear();
		resetErrMsgs();
	}	
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
</script>
<html:form action="/FeePayment" method="post">
	<html:hidden property="method" styleId="method" value="feePaymentEdit"/>
	<html:hidden property="formName" value="feePaymentForm" />
	<html:hidden property="pageType" value="3" />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading">
				<bean:message key="knowledgepro.fee" />
			<span class="Bredcrumbs">&gt;&gt; Fee Payment Edit &gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Fee Payment Edit</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="44" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Fee Bill No.:</div>
									</td>
									<td width="32%" height="25" class="row-even"><label></label>
									<span class="star"><html:text property="billNo" styleId="bno" size="10" maxlength="10"
									onkeypress="return isNumberKey(event)" onblur="checkNumber(this)" /> </span></td>
									<td width="30%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.concession.slip.book.year.col"/> </div>
									</td>
									<td width="35%" class="row-even">
									<div align="left"><span class="star">
									<input type="hidden" id="yr" name="yr" value='<bean:write name="feePaymentForm" property="academicYear"/>' />
									<html:select property="financialYearId" styleClass="combo" styleId="year">
										<html:option value="">-<bean:message key="knowledgepro.select" />-</html:option>
										<cms:renderFinancialYear>
										</cms:renderFinancialYear>
									</html:select>
									</span>
									</div>
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
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td width="100%" height="20" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
								<tr>
									<td width="45%" height="35">
									<div align="right">
										<html:submit property="" styleClass="formbutton" value="Search"></html:submit>
									</div>
									</td>
									<td width="2%"></td>
									<td width="53%">
										<html:button property="" styleClass="formbutton" value="Reset" onclick="resetMessages()">
											<bean:message key="knowledgepro.cancel" />
										</html:button>
									</td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
							</table>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
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