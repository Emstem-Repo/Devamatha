<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="css/custom-button.css">
<script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
<script type="text/javascript" src="js/leftSwitchMenu.js"></script>
<script type="text/javascript">
function cancelAction(){
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
		}
function downloadHallTicket(){
	var url= "examRegistrationDetails.do?method=printHallticket";
	myRef = window.open(url, " ",
			"left=10,top=10,width=800,height=900,toolbar=0,resizable=1,scrollbars=1");
}
</script>
<html:form action="/examRegistrationDetails" method="post">
	<html:hidden property="formName" value="examRegDetailsForm" />
	<table width="98%" border="0">
		<tr>
			<td></td>
		</tr>
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
				<tr height="15%">
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr height="15%">
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="70%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr >
							<td colspan="6" align="left">
							<logic:empty property="errorMessage" name="examRegDetailsForm">
							<div id="errorMessage" class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
							<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							<strong>Alert:</strong>
							<span id="errorMessages"><html:errors/>
							Click here to <a href="#" onclick="downloadHallTicket()"><font size="2px" color="blue"> download</font></a> hallticket.</span>
							</p>
							</div>
							</div>
							</logic:empty>
							</td>
						</tr>
						
					</table>
					
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr height="25%">
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr height="15%">
				<logic:notEmpty property="errorMessage" name="examRegDetailsForm">
						<tr >
						<td class="heading" align="center" colspan="3"><font color="red" >You have already completed the SAP course</font></td>
						</tr>
					 </logic:notEmpty>
				
				</tr>
				<tr height="10%">
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr height="10%">
				<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
				<td align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" height="20">
					
					<tr class="row-white" height="20">
                   <td colspan="2"><div align="center">
					 <html:button property=""
								styleClass="buttom" value="Cancel"
								onclick="cancelAction()"></html:button>
					</div></td>
                 </tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr height="10%">
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
