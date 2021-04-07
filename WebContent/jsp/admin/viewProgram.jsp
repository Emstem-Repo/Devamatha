<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.admin.program.view.program"/> </title>
</head>
<body>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"></span></td>
  </tr>
  <tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.program" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.admin.program.type" /></div>
							</td>
							<td width="19%" height="25" class="row-even">
							 <bean:write name="programForm" property="programTypeName"/>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.admin.program.code" /></div>
							</td>
							<td width="16%" class="row-even">
							<bean:write name="programForm" property="programCode"/>
							</td>
						</tr>

						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.admin.prog" /></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<bean:write name="programForm" property="name"/>
							</td>
							<td width="22%" class="row-even">
							<div align="right">&nbsp;</div>
							</td>
							<td width="16%" class="row-even"><span class="star">
							&nbsp; </span></td>
						</tr>
						<tr>
							<td colspan="4" height="25" class="row-odd"> <bean:message key="knowledgepro.admin.program.application.cofig"/>
							</td>
						</tr>
							
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.applicationform.mothertongue.label"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<bean:write name='programForm' property='motherTongue'/>
    						</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.applicationform.secLang.label"/></div>
							</td>
							<td width="16%" class="row-even">
							 <bean:write name='programForm' property='secondLanguage'/>
							</td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.display.known.lan"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<bean:write name='programForm' property='displayLanguageKnown'/>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.height.weight"/></div>
							</td>
							<td width="16%" class="row-even">
							<bean:write name='programForm' property='heightWeight'/>
							</td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.family.background"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<bean:write name='programForm' property='familyBackground'/>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.entrance.details"/></div>
							</td>
							<td width="16%" class="row-even">
							<bean:write name='programForm' property='entranceDetails'/>
							</td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.lateral.details"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<bean:write name='programForm' property='lateralDetails'/>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.training.short.course"/></div>
							</td>
							<td width="16%" class="row-even">
							 <bean:write name='programForm' property='displayTrainingCourse'/>
							</td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.transfer.course"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<bean:write name='programForm' property='transferCourse'/>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.additional.info"/></div>
							</td>
							<td width="16%" class="row-even">
							<bean:write name='programForm' property='additionalInfo'/>
							</td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.extra.curriculam"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<bean:write name='programForm' property='extraDetails'/>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.isTCDisplay"/></div>
							</td>
							<td width="16%" class="row-even">
							<bean:write name='programForm' property='isTcDisplay'/></td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right">Is Application Open</div>
							</td>
							<td width="19%" height="25" class="row-even">
							<bean:write name='programForm' property='isOpen'/>
							</td>
							<td width="22%" class="row-odd">
							<div align="right">&nbsp;</div>
							</td>
							<td width="16%" class="row-even">
							&nbsp;</td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.attn.with.reg.no"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<bean:write name='programForm' property='isRegistartionNo'/>
							</td>
							<td width="22%" class="row-odd">
							<div align="right">&nbsp;</div>
							</td>
							<td width="16%" class="row-even">&nbsp;</td>
						</tr>
						
						<tr>
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td colspan="3" align="center">
									<input type="button" class="formbutton" value="Close" onclick="javascript:self.close();"/>
									</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>

					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
</html>