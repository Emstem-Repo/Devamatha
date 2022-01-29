<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function submitMe(method) {
		document.reGeneratePasswordForm.method.value = method;
		document.reGeneratePasswordForm.submit();
	}
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>

<body>
<html:form action="/reGeneratePassword">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="reGeneratePasswordForm" />

	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admin" /><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.regeneratepassword.label" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif"></td>
					<td width="935" colspan="2" background="images/Tcenter.gif"
						class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.regeneratepassword.label" /></strong></div>
					</td>
					<td width="9"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="52" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td height="30" valign="top">
							<table width="100%" height="30" border="0" cellpadding="0"
								cellspacing="1">
								<tr class="row-white">
								<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.attendance.activityattendence.regno" />:</div>
											</td>
											<td align="left" class="row-even"><label> <html:textarea
												property="registerNoEntry" styleId="registerNoEntry"
												rows="3"></html:textarea> </label></td>
								<td>
								</tr>
								<tr>
									<td valign="top" class="news" colspan="2">
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
													<td colspan="4" height="25" class="row-odd" align="center">
													<bean:message key="admin.generatepassword.config.label" />
													</td>
												</tr>
												<tr>
													<td width="25%" height="25" class="row-odd">
													<div align="right"><bean:message
														key="admin.generatepassword.studentemail.label" /></div>
													</td>
													<td width="25%" height="25" class="row-even"><input
														type="radio" name="studentMailid" id="stMail_1"
														value="true" /> <bean:message key="knowledgepro.yes" />
													<input type="radio" name="studentMailid" id="stMail_2"
														value="false" checked="checked" /> <bean:message
														key="knowledgepro.no" /><script type="text/javascript">
	var mailid = "__tag_185$22_";
	if (mailid == "true") {
		document.getElementById("stMail_1").checked = true;
	}
</script></td>
													<td width="25%" class="row-odd">
													<div align="right"><bean:message
														key="admin.generatepassword.studentroll.label" /></div>
													</td>
													<td width="25%" class="row-even"><input type="radio"
														name="studentRollNo" id="stRoll_1" value="true" /><bean:message
														key="knowledgepro.yes" /><input type="radio"
														name="studentRollNo" id="stRoll_2" value="false"
														checked="checked" /><bean:message key="knowledgepro.no" />
													<script type="text/javascript">
	var rollid = "__tag_200$22_";
	if (rollid == "true") {
		document.getElementById("stRoll_1").checked = true;
	}
</script></td>
												</tr>
												<tr>
													<td width="25%" height="25" class="row-odd">
													<div align="right"><bean:message
														key="admin.generatepassword.studentreg.label" /></div>
													</td>
													<td width="25%" height="25" class="row-even"><input
														type="radio" name="studentRegNo" id="stReg_1" value="true" />
													<bean:message key="knowledgepro.yes" /> <input
														type="radio" name="studentRegNo" id="stReg_2"
														value="false" checked="checked" /><bean:message
														key="knowledgepro.no" /><script type="text/javascript">
	var regid = "__tag_219$22_";
	if (regid == "true") {
		document.getElementById("stRoll_1").checked = true;
	}
</script></td>
													<td width="25%" class="row-odd">
													<div align="right"><bean:message
														key="admin.generatepassword.select.mail" /></div>
													</td>
													<td width="25%" class="row-even"><input type="radio"
														name="sendMail" id="stmail_1" value="student" /><bean:message
														key="admin.generatepassword.select.mail.student" /><input type="radio"
														name="sendMail" id="stmail_2" value="parent"/><bean:message key="admin.generatepassword.select.mail.parent"/>
														<input type="radio"
														name="sendMail" id="stmail_3" value="both" checked="checked"/><bean:message
														key="admin.generatepassword.select.mail.both" />
													<script type="text/javascript">
	var mailid = "__tag_220$22_";
	if (mailid == "true") {
		document.getElementById("stmail_1").checked = true;
	}
</script></td>
												</tr>
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
								</tr>
							</table>
							</td>
							<td background="images/right.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>


				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<div align="center">
					<table width="100%" height="27" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="10">
								<tr>
									<td  height="21">
									<div align="right"><html:button property=""
										onclick="submitMe('reGeneratePasswordAndSendMail')" styleClass="formbutton"
										value="Re Generate Password"></html:button></div>
									</td>
									<td  height="21">
									<div align="left"><html:button property="" styleClass="formbutton"
										value="Reset" onclick="submitMe('initReGeneratePassword')"></html:button></div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="9" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td colspan="2" background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html:html>