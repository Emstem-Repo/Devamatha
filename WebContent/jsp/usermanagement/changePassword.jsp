<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<html:form action="/ChangePassword">
	<html:hidden property="method" styleId="method"	value="updatePassword" />
	<html:hidden property="formName" value="changePasswordForm" />
	<html:hidden property="pageType" value="1" />

	<table width="50%" border="0" align="center">
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.usermanagement.change.password"/></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2">
							<div align="right"><FONT color="red"></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>


						<tr>
							<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
			
								<tr>
									<td width="46%" height="25"  class="heading">
									<div align="right"><span class="Mandatory">*</span>Date of Birth: &nbsp;  </div>
									</td>
									<td width="54%" height="25" ><span
										class="star"> <html:text property="dob" styleId="dob" size="11" maxlength="11"></html:text>
											<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'changePasswordForm',
													// input name
													'controlname' :'dob'
												});
											</script> </span></td>
								</tr>
								<tr>
			
									<td width="46%" height="25" class="heading">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.userManagement.existingPwd.required"/>: &nbsp;  </div>
									</td>
									<td width="54%" height="25" ><span
										class="star"> <html:password property="existingPwd"
										styleClass="TextBox" styleId="existingPwd" size="20"
										maxlength="200" /> </span></td>
								</tr>
								<tr>
			
									<td width="46%" height="25"  class="heading">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.userManagement.newPwd.required"/>: &nbsp; </div>
									</td>
									<td width="54%" height="25" ><span
										class="star"> <html:password property="newPwd"
										styleClass="TextBox" styleId="newPwd" size="20"
										maxlength="200" /> </span></td>
								</tr>
								<tr>
			
									<td width="46%" height="25"  class="heading">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.userManagement.reTypeNewPwd.required"/>: &nbsp; </div>
									</td>
									<td width="54%" height="25" ><span
										class="star"> <html:password property="reTypeNewPwd"
										styleClass="TextBox" styleId="reTypeNewPwd" size="20"
										maxlength="200" /> </span></td>
								</tr>
			
			
								
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

				<tr>
					<td height="25" colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right">
							<html:submit property="" styleClass="formbutton" value="Change" styleId="submitbutton">
							</html:submit>
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button></td>
						</tr>
					</table>
					</td>
				</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>

				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>

			</table>
			</td>
		</tr>
	</table>
</html:form>
