<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<html:html>

<head>
<title><bean:message key="knowledgepro.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="css/calendar.css">
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
<script type="text/javascript">
	function closeMe() {
		window.close();
	}
</script>
</head>

<body>
<html:form action="/studentEdit" method="POST">
	<html:hidden property="method" value="" />
	<html:hidden property="pageType" value="5" />

	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs">&gt;&gt;Exams
			&gt;&gt;Student Bio Data &gt;&gt;Rejoin History</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Rejoin History</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									<td class="row-odd">Date:
										</td>
										<td class="row-odd">Register No.
										</td>
										<td class="row-odd">Batch
										</td>
										<td class="row-odd">Class
										</td>
										<td class="row-odd">
										Reason
										</td>
									</tr>
								<nested:iterate property="rejoinDetHistoryList" name="studentEditForm">
									
									<tr>
										<td class="row-even"><nested:write
											property="rejoinDate" /></td>
										<td class="row-even"><nested:write
											property="regNo" /></td>
										<td class="row-even"><nested:write
											property="batch" /></td>
										<td class="row-even"><nested:write
											property="className" /></td>
										<td class="row-even"><nested:write property="rejoinReason" /></td>
									</tr>
								</nested:iterate>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>


				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="33%" height="35" align="right">&nbsp;</td>
							<td width="9%" height="35" align="center">&nbsp;</td>
							<td width="58%" height="35" align="left"><input
								name="Submit2" type="button" class="formbutton" value="Cancel"
								onclick="closeMe()" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>

</html:html>