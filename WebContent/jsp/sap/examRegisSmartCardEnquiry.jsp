<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="css/custom-button.css">
<script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
<script type="text/javascript" src="js/leftSwitchMenu.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">
	function cancelAction() {
		document.location.href = "examRegistrationDetails.do?method=initExamRegistrationDetails";
	}
	function saveCertCourse() {
		if (document.getElementById("acceptAll").checked) {
			document.getElementById("main").style.display="none";
			document.getElementById("loading").style.display="block";
			document.examRegDetailsForm.submit();
		} else {
			document.getElementById("errorMessages").style.display = "block";
			document.getElementById("err").innerHTML = "Please Accept Terms & Conditions";
		}
	}
</script>
<html:form action="/examRegistrationDetails" method="post">
	<html:hidden property="method" styleId="method" value="verifyAndSaveExamRegistration" />
	<html:hidden property="formName" value="examRegDetailsForm" />
	<html:hidden property="pageType" value="0" />
	<table width="98%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">SAP Exam Registration </strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="70%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessages" class="ui-widget">
							<div class="ui-state-error ui-corner-all"
								style="padding: 0 .7em;">
							<p><span class="ui-icon ui-icon-alert"
								style="float: left; margin-right: .3em;"></span> <strong>Alert:</strong>
							<span id="err"><html:errors /></span></p>
							</div>
							</div>

							<div id="messages">
							<div class="display-info"><span id="msg"><html:messages
								id="message" property="messages" message="true">
								<c:out value="${message}" escapeXml="false"></c:out>
								<br>
							</html:messages></span></div>
							</div>
							<script type="text/javascript">
							if (document.getElementById("msg") == null
									|| document.getElementById("msg").innerHTML == '') {
								document.getElementById("messages").style.display = "none";
							}
							if (document.getElementById("err").innerHTML == '') {
								document.getElementById("errorMessages").style.display = "none";
							}
						</script></td>
						</tr>
						<tr>
							<td colspan="6" align="left">
							<div align="right">
							Contact Us: support@christuniversity.in
							</div>
							</td>
						</tr>
						<tr>
							<td colspan="6">&nbsp;</td>
						</tr>

						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>

						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table id="loading">
								<tr>
									<td><img alt="Image Not Found" src="images/loading_bar_animated.gif"> </td>
								</tr>
								<tr>
									<td>
									Please wait...<br/>
							Kindly do not Refresh or press the back button until the transaction is completed
									</td>
								</tr>
							</table>
							<script type="text/javascript">
								document.getElementById("loading").style.display="none";
							</script>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2"
								align="center" id="main">

								<tr>
									<td class="heading">I hereby authorize Christ University
									to debit my South Indian Bank Christ University branch Account
									towards the prescribed fee amount</td>
								</tr>
								<tr>
									<td class="heading"> &nbsp;</td>
								</tr>
								<tr>
									<td class="heading"><input type="checkbox"
										name="acceptAll" id="acceptAll">I Agree</td>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
						<tr class="row-white">
							<td colspan="2">
							<div align="center"><html:button property=""
								styleClass="buttom" value="Confirm" onclick="saveCertCourse()"></html:button>
							&nbsp; <html:button property="" styleClass="buttom" value="Cancel"
								onclick="cancelAction()"></html:button></div>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	$(".display-info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400)
			.fadeOut(400).fadeIn(400);
</script>