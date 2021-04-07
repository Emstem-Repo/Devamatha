<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" >
function generateOtp(){
	document.getElementById("method").value = "initCreateUserAfterOtp";
	document.createUserForm.submit();
}
</script>
<html:form action="/CreateUserInfo" method="post" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="initCreateUser" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="formName" value="createUserForm" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.usermanagement.userinfo.usermanagement" /> <span class="Bredcrumbs">&gt;&gt;
				<bean:message key="knowledgepro.userinfo" />
				&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.userinfo" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellpadding="1" cellspacing="2">
						<tr>
							<td  align="left">
							<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages></FONT></div>
							</td>
						</tr>
						
						
						<tr>
							<td width="100%" class="heading">
							<table width="98%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
									
										<tr>
											<td height="25" class="row-odd">
											<div align="right">Enter OTP: </div>
											</td>
											<td height="25" class="row-even" align="left">
										    <html:password property="password" styleId="password" size="15" maxlength="10"></html:password>
											</td>
										</tr>
										
										
									
										
										<!-- added by venkat -->
										
									<tr>
											<td colspan="4">
											<div align="center">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="47%" height="21"  align="center">
													<html:submit property="" styleClass="formbutton" value="Submit"
															onclick="generateOtp()"></html:submit>
													</td>
													
												</tr>
											</table></div>
											</td>
									</tr>	
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td width="52%" valign="top" class="heading">
							</td>
						</tr>
					</table>
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
</html:form>
<script  language="JavaScript">

 var both=document.getElementById("others").checked;
 		if(both)
 		{
 			disableBoth();
 		}
  var guest=document.getElementById("guest").checked;
 		if(guest)
 		{
 			getGuest();
 		}
 var emp=document.getElementById("employee").checked;
 		if(emp)
 		{
 			getEmployee();
 		}
 				

</script>