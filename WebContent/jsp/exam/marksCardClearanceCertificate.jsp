<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script language="JavaScript" >
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=initMarksCard";
}
function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}
function printPass(){
	var url = "StudentLoginAction.do?method=printMarksCardClearanceCertificate";
	myRef = window
			.open(url, "Clearance Certificate", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");

}
</script>
<html:form action="/StudentLoginAction" >
<html:hidden property="method" value="getHallTicket"/>
<html:hidden property="formName" value="loginform"/>
<html:hidden property="pageType" value="1"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Clearance Certificate</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%">
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>

							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
							<div style="background-image:url(images/waterMark.jpg);">
								<table width="100%">
									<tr>
										<td align="center" width="100%">
										 	<h2>CHRIST UNIVERSITY, BANGALORE</h2>
										</td>
									</tr>
									<tr>	
										<td align="center" width="100%">
										 	<h4>CLEARANCE CERTIFICATE (University copy)</h4>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%">
												<tr>
													<td align="right"><b>Name:</b></td>
													<td align="left"><b>  
														<bean:write name="loginform" property="cto.name"/></b>
													</td>
													<td align="right"><b>Register No:</b></td>
													<td align="left">  <b>
														<bean:write name="loginform" property="cto.registerNo"/></b>
													</td>
													<td align="right"><b>Class Name:</b></td>
													<td align="left">  <b>
														<bean:write name="loginform" property="cto.className"/></b>
													</td>
												</tr>
											</table>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%" style="border: 1px solid black; " rules="all">
												<tr> 
													<td width="50%" align="center"> 
														<h4>Marks Card blocked reason</h4>
													</td>
													<td width="50%" align="center"> 
														<h4>Signature & Seal</h4>
													</td>
												</tr>
												<logic:notEmpty name="loginform" property="cto.comments">
													<logic:iterate id="to" name="loginform" property="cto.comments" indexId="count">
														<tr  height="90"> 
															<td width="50%"> 
																<b><c:out value="${count + 1}" /></b>
																<b><bean:write name="to"/></b>
															</td>
															<td width="50%" align="center"> 
																
															</td>
														</tr>
													</logic:iterate>
												</logic:notEmpty>
											</table>
										</td>
									</tr>
									<tr height="25">
										<td></td>
									</tr>
									<tr>
										<td><img width="100%" src="images/dot.gif"></td>
									</tr>
									<tr>
										<td align="center" width="100%">
										 	<h2>CHRIST UNIVERSITY, BANGALORE</h2>
										</td>
										</tr>
									<tr>
										<td align="center" width="100%">
										 	<h4>CLEARANCE CERTIFICATE (Student copy)</h4>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%">
												<tr>
													<td align="right"><b>Name:</b></td>
													<td align="left">  <b>
														<bean:write name="loginform" property="cto.name"/></b>
													</td>
													<td align="right"><b>Register No:</b></td>
													<td align="left">  <b>
														<bean:write name="loginform" property="cto.registerNo"/></b>
													</td>
													<td align="right"><b>Class Name:</b></td>
													<td align="left"><b>  
														<bean:write name="loginform" property="cto.className"/></b>
													</td>
												</tr>
											</table>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%" style="border: 1px solid black; " rules="all">
												<tr> 
													<td width="50%" align="center"> 
														<h4>Marks Card blocked reason</h4>
													</td>
													<td width="50%" align="center"> 
														<h4>Signature & Seal</h4>
													</td>
												</tr>
												
												
												<logic:notEmpty name="loginform" property="cto.comments">
													<tr height="100">
														<td>
															<logic:iterate id="to" name="loginform" property="cto.comments" indexId="count">
																		<b><c:out value="${count + 1}" /></b>
																		<b><bean:write name="to"/><br/></b>
															</logic:iterate>
														</td>
														<td valign="bottom">
															<b>Received Seal & Signature with date and time</b>
														</td>
													</tr>
												</logic:notEmpty>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<ul>
											    <li><b>After getting the clearance from the concerned Offices/Departments, submit the university copy at <strong> IPM, Central Block (Between 8.30 am to 5.00 pm)</strong></b></li>
				                                <li><b>Marks Card will be released within 30 minutes after submission.</b></li>
			                                </ul>
										</td>									
									</tr>									
								</table></div>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>

							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:button property="" styleClass="formbutton" onclick="printPass()">
								Print
							</html:button></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""  styleClass="formbutton" value="Close" onclick="goToHomePage()"></html:button> 
							</td>
						</tr>
					</table>
	
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>

</html:form>
