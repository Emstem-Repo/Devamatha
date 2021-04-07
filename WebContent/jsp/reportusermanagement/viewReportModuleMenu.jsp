<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.usermanagement.assignprivilege.view.title"/></title>
</head>
<body>
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
		<table width="100%" height="79" border="0" cellpadding="0"
			cellspacing="1">
			<logic:notEmpty name="reportAssignPrivilegeForm" property="moduleMenuMap">
				<logic:iterate name="reportAssignPrivilegeForm" id="map"
					property="moduleMenuMap" indexId="moduleCount">
					<tr class="row-white">

						<td colspan="6" class="heading"><bean:write name="map"
							property="key" /></td>
					</tr>
					<tr class="row-white">
						<logic:iterate name="map" id="menu" property="value"
							indexId="menuCount">
							<td width="3%" height="25" align="right" class="row-odd">&nbsp;</td>
							<td width="30%" class="row-even"><c:out value="${menu}" /></td>
							<c:if test="${(menuCount + 1) % 3 == 0}">
					</tr>
					<tr class="row-white">
						</c:if>
				</logic:iterate>
				</tr>
				<tr class="row-white">
					<td colspan="6" class="heading">&nbsp;</td>
				</tr>
				</logic:iterate>
			</logic:notEmpty>
		</table>
		</td>
		<td width="5" height="30" background="images/right.gif"></td>
	</tr>
	<tr>
		<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
		<td background="images/05.gif"></td>
		<td><img src="images/06.gif" /></td>
	</tr>
	<table align="center">
	<tr >
	<td align="center"><input type="button" class="formbutton" value="Close"
			onclick="javascript:self.close();" /></td>
	</tr>
	</table>
</table>

</body>
</html>