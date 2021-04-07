<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<html:html>
<head>
<title>:: CMS ::</title>
<link href="css/styles.css" rel="stylesheet" type="text/css">
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
<script language='JavaScript'>
	function prePrint() {
		document.getElementById("printme").style.visibility = "hidden";
	}
	function postPrint() {
		document.getElementById("printme").style.visibility = "visible";
	}

	function printICard() {
		prePrint();
		window.print();
		postPrint();
	}
		function closeWin() {
			window.close();
		}
</script>
</head>
<body>
<html:form action="interviewprocess" method="post">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td valign="top">
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="5"><img src="../images/01.gif" width="5"
						height="5" /></td>
					<td width="914" background="../images/02.gif"></td>
					<td width="5"><img src="../images/03.gif" width="5" height="5" /></td>
				</tr>
				<tr>
					<td background="../images/left.gif"></td>
					<td width="100%" valign="top">
					<table width="100%" cellspacing="1" cellpadding="2">
					<tr>
						<td width="5"><img src="../images/aji.JPG" width="125" height="300" /></td>
				
						<td valign="top">
							<CENTER>Aji P.A.</CENTER><br><br>
					
						<CENTER>21-08-1964</CENTER>
						<CENTER>05-05-2008</CENTER><br><br>
				
				<div align="justify" style="padding-left: 150px;">
						Knowledge Pro is a culmination of his vision in providing a<br> 
						user friendly web-based solution for universities and<br>
						colleges for its effective functioning. We carry over his<br>
						passion of being the best in what we do.<br><br>
					
						We Knowledge Pro team pay our homage And Gratitude<br> 
						to his Contributions.<br>
				</div>
				
					</td>
					</tr>
					</table>
					</td>
					<td height="30" background="../images/right.gif"></td>

				</tr>
				<tr>
					<td height="5"><img src="../images/04.gif" width="5"
						height="5" /></td>
					<td background="../images/05.gif"></td>
					<td><img src="../images/06.gif" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="45%" height="35">
			<div align="right"></div>
			</td>
			
			<td width="8%"><html:button property="" styleId="closeme"
				styleClass="formbutton" value="Close" onclick="closeWin()"></html:button></td>
			<td width="44%"></td>
		</tr>
	</table>

</html:form>
</body>
</html:html>