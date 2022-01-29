<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script src="js/md5.js" type="text/javascript"></script>

<script type="text/javascript" src="js/common.js">

		</script>

<html:form action="/forgotPassword">
	<html:hidden property="method" styleId="method" value="resetAdminForgotPassword" />
	<html:hidden property="formName" value="studentForgotPasswordForm" />
	<html:hidden property="pageType" value="2" />
	<table width="95%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td width="9" background="images/leftShadow.gif">&nbsp;</td>
			<td width="1158" valign="top">
			<table bgcolor="#ffffff" border="0" cellpadding="0" cellspacing="0"
				width="100%">
				<tbody>
				
					<tr>
						<td>
						<table width="98%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tbody>
								<tr>
									<td height="87" colspan="3" valign="top">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="20%" height="25" rowspan="2" class="row-white">
											<div align="left"><img
												src='<%=request.getContextPath()%>/TopBarServlet'
												alt="Logo not available" width="238" height="100"></div>
											</td>
											<td width="80%">
											<div align="right"><img
												src="images/OnlineHeaderFinal.jpg" width="772" height="100"></div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>
									
									<td width="100%" background="images/Orang_CurveBG.gif">
									<div align="left">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="69%" height="19" class="heading_white"></td>
										</tr>
									</table>
									</div>
									</td>
									
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
					<tr>
						<td>
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tbody>
								<tr>
									<td width="19%" valign="top">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tbody>
											<tr>
												<td height="209" valign="top">&nbsp;</td>
											</tr>
										</tbody>
									</table>
									</td>
									<td width="0%">&nbsp;</td>
									<td valign="top" width="58%">
									<div align="center">
									<table border="0" cellpadding="3" cellspacing="4" width="94%">
										<tbody>
											<tr>
												<td height="40" class="heading">
												<div align="center"><bean:message key="knowledgepro.welcome"/> </div>
												</td>
											</tr>
											<tr>
												<td valign="top">
												<table align="center" bgcolor="#ffffff" border="0"
													cellpadding="0" cellspacing="0" width="100%">
													<tbody>
														<tr>
															<td></td>
														</tr>
														<tr>
															<td>
															<table align="center" bgcolor="#ffffff" border="0"
																cellpadding="0" cellspacing="0" width="100%">
																<tbody>
																	<tr>
																		<td width="57%" height="198" valign="top">
																		<table width="36%" border="0" align="center"
																			cellpadding="0" cellspacing="0">
																			<tr>
																				<td width="11"><img
																					src="images/Tright_03_01.gif" width="9" height="29"></td>
																				<td width="327" background="images/Tcenter.gif"
																					class="heading_white">
																				<div align="left"><bean:message key="knowledgepro.admin.forget.password"/> </div>
																				</td>
																				<td width="93"><img
																					src="images/Tright_1_01.gif" width="9" height="29"></td>
																			</tr>
																			<td background="images/Tright_03_03.gif"></td>
																			<td valign="top">

																			<table width="323" border="0" cellpadding="0"
																				cellspacing="0">
																				<tr>

																					<td height="10" colspan="3">
																					<div id="errorMessage" align="center"><FONT
																						color="red"><html:errors /></FONT> <FONT
																						color="green"> <html:messages id="msg"
																						property="messages" message="true">
																						<c:out value="${msg}" escapeXml="false"></c:out>
																						<br>
																					</html:messages> </FONT></div>
																					</td>
																				</tr>
																				<tr height="40px">
																					<td width="120">
																					<div align="center" class="heading"><bean:message key="knowledgepro.admin.employeeid"/>:</div>
																					</td>
																					<td><html:text property="employeeid"
																						styleId="employeeid" styleClass="TextBox" size="30"
																						maxlength="16" ></html:text></td>
																				</tr>
																				<tr bgcolor="#FFFFFF" height="40px">
																					<td class="body">
																					<div align="center" class="heading">
																					<bean:message key="knowledgepro.admin.dateofbirth"/>:</div>
																					</td>
																					<td>
																						<html:text name="studentForgotPasswordForm" property="dob" styleId="dob" size="10" maxlength="16"/>
																							<script
																							language="JavaScript">
																							new tcal( {
																								// form name
																								'formname' :'studentForgotPasswordForm',
																								// input name
																								'controlname' :'dob'
																							});
																						</script>
																					</td>
																				</tr>
																				<tr height="40px">
																					<td width="40%" align="right"><html:submit
																						styleClass="formbutton"> <bean:message key="knowledgepro.submit"/></html:submit>
																					</td>
																					<td width="40%" align="left"><html:button
																						property="" styleClass="formbutton" value="Reset"
																						onclick="resetFieldAndErrMsgs()"></html:button></td>
																				</tr>
																				<tr>
																					<td width="40%" align="right"> </td>
																					  <% String path= request.getContextPath(); %>
            																			<td colspan="2" align="center" class="heading"> <a href='<%=path %>/Login.do'> Back To Login Page</a> </td>
																				</tr>
																				<tr>
																					<td height="10" class="body"></td>
																					<td height="10" bgcolor="#FFFFFF"></td>
																				</tr>
																			</table>

																			</td>
																			<td width="93" valign="top"
																				background="images/Tright_3_3.gif"></td>
																			</tr>
																			<tr>
																				<td height="1" colspan="3"
																					background="images/Tcenter.gif"></td>
																			</tr>
																		</table>
																		</td>
																	</tr>
																</tbody>
															</table>
															</td>
														</tr>

													</tbody>
												</table>
												</td>
											</tr>
										</tbody>
									</table>
									</div>
									</td>
									<td width="1%" valign="top" class="verLine">&nbsp;</td>
									<td width="22%" height="421" valign="top">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tbody>
											<tr>
												<td height="209" valign="top">
												</td>
											</tr>
										</tbody>
									</table>
									</td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
					<tr>
						<td>
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tbody>
								<tr bgcolor="#ffffff">
									<td align="center" bgcolor="#e1e1e1">
									<center></center>
									</td>
								</tr>
								<tr bgcolor="#ffffff">
									<td height="18" align="left" valign="middle" bgcolor="#efefef">
									<div align="left" class="news"><bean:message key="knowledgepro.admin.copyrights"/> </div>
									</td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
			<td width="9" background="images/rightShadow.gif">&nbsp;</td>
		</tr>
		<tr>
			<td></td>
			<td height="7" valign="baseline"
				background="images/shadow_bottom.gif"></td>
			<td></td>
		</tr>
	</table>
</html:form>
<SCRIPT language="JavaScript">
var browserName=navigator.appName; 
 if (browserName=="Microsoft Internet Explorer")
 {
	 alert(" If Your using InternetExplorer Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling  or Use Mozilla FireFox");
 }
</SCRIPT>