<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<head>
</head>
<html:form action="/HostelPaymentSlip">
	<html:hidden property="method" styleId="method" value="getHostelPaymentDetail" />
	<html:hidden property="formName" value="hostelPaymentSlipForm" />
	<html:hidden property="pageType" value="4" />
	<table width="99%" border="0">
		<tr>
			<td>
				<span class="heading"><bean:message	key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
				<bean:message key="knowledgepro.hostel.paymentSlip" />&gt;&gt;</span> </span>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9" 	height="29"></td>
						<td background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.hostel.paymentSlip" /></td>
						<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td colspan="6" align="left">
										<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
										<div id="errorMessage">
											<FONT color="red"><html:errors /></FONT>
											<FONT color="green">
												<html:messages id="msg" property="messages" message="true">
												<c:out value="${msg}" escapeXml="false"></c:out>1
												<br>
												</html:messages>
											</FONT>
										</div>
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
												<td width="46%" class="row-odd">
													<div align="right">Bill Number :</div>
												</td>
												<td width="41%" class="row-even">
													<html:select property="hlApplicationFormId" styleClass="TextBox" styleId="hlApplicationFormId" name="hostelPaymentSlipForm">
														<logic:notEmpty property="billNumberList" name="hostelPaymentSlipForm">
															<html:optionsCollection property="billNumberList" label="slipNo" value="hlApplicationFormId" name="hostelPaymentSlipForm" />
														</logic:notEmpty>
													</html:select>
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
						<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" align="center" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="38%" height="35" align="center">
										<html:submit property="" styleClass="formbutton" value="Continue"></html:submit>
									</td>
								</tr>
							</table>
						</td>
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

