<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<SCRIPT type="text/javascript">
function downloadFile(id) {
		document.location.href = "feeAccountDownload.do?id="+ id;
	}
</SCRIPT>
<html:form action="/feeaccountEntry">
<html:hidden property="id" styleId="id" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title>View Fee Account</title>
</head>
<body>
<table width="100%" border="0">
	<tr>
		<td>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="9"><img src="images/Tright_03_01.gif" width="9"
					height="29"></td>

				<td background="images/Tcenter.gif" class="body"><strong
					class="boxheader">Fee Account</strong></td>
				<td width="10"><img src="images/Tright_1_01.gif" width="9"
					height="29"></td>
			</tr>
			<tr>
				<td height="122" valign="top" background="images/Tright_03_03.gif"></td>

				<td height="10" class="body"><br />
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td><img src="images/01.gif" width="5" height="5" /></td>
						<td width="914" background="images/02.gif"></td>
						<td><img src="images/03.gif" width="5" height="5" /></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td align="center" class="heading">Print Position</td>
						<td width="5" height="30" background="images/right.gif"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td valign="top">
						<table width="100%" cellspacing="1" cellpadding="2">
							<tr class="row-even">
								<td width="17%" height="25"><bean:write
									name="feeAccountForm" property="position" /></td>
							</tr>
						</table>
						<br>
						</td>
						<td width="5" height="30" background="images/right.gif"></td>
					</tr>
					
					
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td align="center" class="heading">Print Account Name</td>
						<td width="5" height="30" background="images/right.gif"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td valign="top">
						<table width="100%" cellspacing="1" cellpadding="2">
							<tr class="row-even">
								<td width="17%" height="25"><bean:write
									name="feeAccountForm" property="printAccName" /></td>
							</tr>
						</table>
						<br>
						</td>
						<td width="5" height="30" background="images/right.gif"></td>
					</tr>
					
					
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td align="center" class="heading">Bank Information</td>
						<td width="5" height="30" background="images/right.gif"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td valign="top">
						<table width="100%" cellspacing="1" cellpadding="2">
							<tr class="row-even">
								<td width="17%" height="25"><bean:write
									name="feeAccountForm" property="bankInfo" /></td>
							</tr>
						</table>
						<br>
						</td>
						<td width="5" height="30" background="images/right.gif"></td>
					</tr>
					
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td align="center" class="heading">Logo</td>
						<td width="5" height="30" background="images/right.gif"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td valign="top">
						<table width="100%" cellspacing="1" cellpadding="2">
							<tr class="row-even">
								<td width="17%" height="25">
										<a href="#"
											onclick="downloadFile('<bean:write name="feeAccountForm" property="id"/>')">
											<bean:message key="knowledgepro.guidelines.view"/> </a></td>
							</tr>
						</table>
						<br>
						</td>
						<td width="5" height="30" background="images/right.gif"></td>
					</tr>
					

				<tr>
                   <td width="5"  background="images/left.gif"></td>
                   <td height="35" valign="top" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
                     <tr>
                       <td colspan="3" align="center"><div align="center">
                       <input type="button" class="formbutton" value="Close" onclick="javascript:self.close();"/>
                       
                       </div></td>
                     </tr>                     
                     <tr><td style="height: 10px" align="center" colspan="1"></td></tr>   
                   </table>
                   </td>
                   <td width="5" align="right" background="images/right.gif"></td>
                 </tr>

					<tr>
						<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
						<td background="images/05.gif"></td>
						<td><img src="images/06.gif" /></td>
					</tr>
				</table>
				<br />
				</td>
				<td height="122" width="13" valign="top"
					background="images/Tright_3_3.gif" class="news"></td>
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
</body>
</html:form>