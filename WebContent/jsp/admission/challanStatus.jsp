<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
		<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
		<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<script language="JavaScript" >
function resetMessages(){
	resetFieldAndErrMsgs();
}
</script>
<html:form action="/ddStatus" method="POST">
	<html:hidden property="method" styleId="method" value="updateChallanStatus" />
	<html:hidden property="formName" value="dDStatusForm" />
	<html:hidden property="pageType" styleId="pageType" value="2" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.challanStatus.displayName" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message key="knowledgepro.admission.challanStatus.displayName" /></strong></div></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
						<div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg" property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr class="row-white">
			                				<td height="25" class="row-odd" width="25%"><div align="right">
			                				<span class="Mandatory">*</span><bean:message key="knowledgepro.fee.applicationno"/>:</div>
			                				</td>
			                				<td height="25" class="row-even">
			                				 <html:text property="applnNo" styleId="applnNo" maxlength="15" size="10"></html:text>
			                				</td>
			                				
											<td height="25" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span>
											<bean:message key="knowledgepro.admission.challan.status.recievedChallan"/>:</div></td>
			                				<td height="25" class="row-even">
			                					<html:text property="recievedChallanNo" styleId="recievedChallanNo" maxlength="15" size="10"></html:text>
											</td>
										</tr>
										<tr class="row-white">
			                				<td width="15%" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admission.challan.status.recievedDate" />:</div></td>
											<td width="16%" class="row-even">
												<html:text name="dDStatusForm" property="recievedChallanDate" styleId="recievedChallanDate" size="10" maxlength="16"/>
												<script
												language="JavaScript">
												new tcal( {
													// form name
													'formname' :'dDStatusForm',
													// input name
													'controlname' :'recievedChallanDate'
												});
											</script>
											</td>
											
											<td class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.year" />:</div></td>
											<td class="row-even">
											<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="dDStatusForm" property="year"/>" />
											<html:select name="dDStatusForm" property="year" styleId="appliedYear"  styleClass="combo">
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
												<cms:renderYear></cms:renderYear>
											</html:select></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="22"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5" height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" height="106" border="0" cellpadding="1" cellspacing="2">
						<tr>
							<td width="100%" height="46" class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="center"><html:submit styleClass="formbutton">
									<bean:message key="knowledgepro.submit" /></html:submit>
									<html:button property="" styleClass="formbutton" onclick="resetMessages()">
									<bean:message key="knowledgepro.admin.reset" /></html:button></div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="10"></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("appliedYear").value=year;
}
</script>