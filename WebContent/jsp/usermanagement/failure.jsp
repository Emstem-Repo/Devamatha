<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css" />

<script src="js/md5.js" type="text/javascript"></script>

<script type="text/javascript" src="js/common.js">

		</script>

<html:form action="/Failure">
	<html:hidden property="method" styleId="method" value="loginAction" />
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="pageType" value="1" />
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
							<table border="0" cellpadding="0" cellspacing="0" align="center">
							<tr height="55">
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
																				<td width="9"><img
																					src="images/Tright_03_01.gif" width="9" height="29"></td>
																				<td width="327" background="images/Tcenter.gif"
																					class="heading_white">
																				<div align="left">ERROR OCCURED </div>
																				</td>
																				<td width="9"><img
																					src="images/Tright_1_01.gif" width="9" height="29"></td>
																			</tr>
																			
																			<tr>
																			<td background="images/Tright_03_03.gif"></td>
																			<td valign="top">
																				<table border="0" cellpadding="0"
																				cellspacing="0">
																				<tr>
																					<td height="10" >
																					<div id="errorMessage" align="center"><FONT
																						color="red"><html:errors /></FONT> <FONT
																						color="green"> <html:messages id="msg"
																						property="messages" message="true">
																						<c:out value="${msg}" escapeXml="false"></c:out>
																						<br>
																					</html:messages> </FONT></div>
																					</td>
																				</tr>
																				<tr>
																					<td height="47">
																					<bean:message key="knowledgepro.failure.display"/>
																					</td>
																				</tr>
																				
																				
																				</table>
																			</td>
																			<td
																				background="images/Tright_3_3.gif"></td>
																			</tr>
																			<tr>
																				<td height="1" colspan="3"
																					background="images/Tcenter.gif"></td>
																			</tr>
							<tr height="55">
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							
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