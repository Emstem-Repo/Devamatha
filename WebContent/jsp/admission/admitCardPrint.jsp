<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top">
			<table width="100%" cellspacing="1" cellpadding="2" border="0" style="height:100%"  >
			<tr>
				<td colspan="2">
					<c:out value="${admissionStatusForm.templateDescription}" escapeXml="false" />
				</td>
			</tr>
			<tr>
				<td width="45%" height="35">
					<div align="right">
						<input type="button" class="formbutton" value="Print" onclick="window.print()"/>
					</div>
				<td width="53%"><div align="left">                  
					<input type="button" class="formbutton" value="Close" onclick="javascript:self.close();"/>
                  </div></td>
			</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>