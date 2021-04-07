<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
	<link rel="stylesheet" type="text/css" href="css/custom-button.css">
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	function downloadHallTicket(){
		var url= "examRegistrationDetails.do?method=printHallticket";
		myRef = window.open(url, " ",
				"left=10,top=10,width=800,height=900,toolbar=0,resizable=1,scrollbars=1");
	}
	</script>
</head>
<body>
<html:form action="/examRegistrationDetails" >
<html:hidden property="formName" value="examRegDetailsForm"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">SAP Exam Registration </strong></td>

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
			        	 <td class="heading" align="center"> Exam Registration has been done successfully. 
			        	  Click here to <a href="#" onclick="downloadHallTicket()"><font size="2px">download</font></a> hallticket.</td> 
         			</tr>
          <tr class="row-white" >
			                   <td colspan="3" height="50"><div align="center">
								 <html:button property="" value="Close" styleClass="buttom" onclick="cancelAction()"></html:button>
								</div></td>
			                 </tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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

</html:form>

</body>
</html>