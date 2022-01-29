<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link type="text/css" href="jquery-ui-1.10.0.custom/css/ui-lightness/jquery-ui-1.10.0.custom.min.css" rel="stylesheet" />
<script src="jquery-ui-1.10.0.custom/js/jquery-1.9.0.js" type="text/javascript"></script>
<script src="jquery-ui-1.10.0.custom/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>

<script type="text/javascript">

function getDetails(){
	document.getElementById("method").value = "ScLostCorrectionProcessedSearch";
	document.ScLostCorrectionProcessedForm.submit();
}

</script>

<html:form action="/ScLostCorrectionProcessed" method="post">
<html:hidden property="formName" value="ScLostCorrectionProcessedForm" />
<html:hidden property="id" styleId="id" />
<html:hidden property="pageType" value="1" />
<html:hidden property="method" styleId="method" value="getDetails"/>
<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.smartcard" /><span class="Bredcrumbs">&gt;&gt;
			 <bean:message key="knowledgepro.smartcard.lostcorrection.processed.smartcards" /></span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<strong class="boxheader"><bean:message key="knowledgepro.smartcard.lostcorrection.processed" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif">
					</td>
					<td valign="top" class="news">
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
									<td height="25" colspan="2" class="row-even">
									<div align="Center">
									<html:radio property="isEmployee" styleId="stu" value="Student"></html:radio>
									Student &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="isEmployee" styleId="emp" value="Employee"></html:radio>
									Employee 
									</div>
									</td>
							</tr>
                             <tr>
                             <td width="50%" height="25" class="row-odd"><div align="right">
                             <span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admission.status"/>:</div></td>
                             <td width="50%" height="25" class="row-even">
                             <html:select styleClass="combo" property="status" styleId="status">
												<html:option value="Processed">Processed</html:option>
												<html:option value="Received">Received</html:option>
												<html:option value="Issued">Issued</html:option>
												<html:option value="Applied">Applied</html:option>
												<html:option value="Approved">Approved</html:option>
												<html:option value="Rejected">Rejected</html:option>
											</html:select></td>
                             </tr>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
						</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
							<td width="50%" height="35">
							<div align="center">
									<html:button property="" styleClass="formbutton" value="Search"
										onclick="getDetails()"></html:button>
							</div>
							</td>
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
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>