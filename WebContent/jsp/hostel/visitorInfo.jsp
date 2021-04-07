<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript">
function resetMessages() {
	document.getElementById("visitorFor").checked =false;
	document.getElementById("visitorFor1").checked =false;
	resetFieldAndErrMsgs();
	}
</script>
<html:form action="/hostelVisitorInfo">
	<html:hidden property="method" styleId="method"  value="getHostelerDetails" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="visitorInfoForm" />
	<table width="98%" border="0">
		<tr>
			<td>
				<span class="heading">
					<a href="#" class="Bredcrumbs">	<bean:message key="knowledgepro.hostel" /></a>
					<span class="Bredcrumbs">
						&gt;&gt; <bean:message key="knowledgepro.hostel.visitor.display" />&gt;&gt;
					</span>
				</span>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left" class="heading_white"><bean:message
						key="knowledgepro.hostel.visitor.display" /></div>
					</td>
					<td width="11"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="24" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="10" class="heading"><bean:message
						key="knowledgepro.hostel.visitor.occupant" /></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="10" class="heading">
					<table width="100%" border="0" align="right" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" height="29" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.visitor.student" /> :</div>
									</td>
									<td class="row-even"><html:radio name="visitorInfoForm"
										property="visitorFor" styleId="visitorFor" value="1"></html:radio>
									</td>
									<td class="row-even"><strong>OR</strong></td>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.visitor.staff" />:</div>
									</td>
									<td class="row-even"><html:radio name="visitorInfoForm"
										property="visitorFor" styleId="visitorFor1" value="2"></html:radio></td>
									<td class="row-even">&nbsp;</td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">* </span><bean:message
										key="knowledgepro.hostel.name" /></div>
									</td>
									<td class="row-even">
									<div align="left"><html:select property="hostelId"
										name="visitorInfoForm" styleClass="combo" styleId="name">
										<html:option value="">-<bean:message
												key="knowledgepro.select" />-</html:option>
										<html:optionsCollection name="hostelList" label="name"
											value="id" />
									</html:select></div>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.reservation.registerNo" /></div>
									</td>
									<td class="row-even"><html:text name="visitorInfoForm"
										property="registerNo" styleId="registerNo" size="20"
										maxlength="20" /></td>
									<td width="2%" class="row-even"><strong>OR</strong></td>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.reservation.staffId" /></div>
									</td>
									<td class="row-even"><html:text name="visitorInfoForm"
										property="staffId" styleId="staffId" size="20" maxlength="20" />
									</td>
									<td width="2%" class="row-even"><strong>OR</strong></td>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.hostelerdatabase.name" />:</div>
									</td>
									<td class="row-even"><html:text name="visitorInfoForm"
										property="name" styleId="name" size="20" maxlength="20" /></td>
								</tr>
							</table>
							</td>
							<td background="images/right.gif" width="10" height="29"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="24" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="35" align="center" class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" onclick="resetMessages()">
								<bean:message key="knowledgepro.admin.reset" />
							</html:button></td>
						</tr>
					</table>
					</td>
					<td height="17" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>