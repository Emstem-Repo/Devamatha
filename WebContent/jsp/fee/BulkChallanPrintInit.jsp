<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">

function resetMessages() {	
	resetFieldAndErrMsgs();
}	

function cancelAction() {
	document.location.href = "bulkChallanPrintForMain.do?method=initBulkChallanPrint";
}
</script>
<html:form action="/bulkChallanPrintForMain" method="post">
	<html:hidden property="method" styleId="method" value="printChallan" />
	<html:hidden property="formName" value="bulkChallanPrintForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.fee.bulk.challan.print"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.fee.bulk.challan.print"/></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
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
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.leavemodify.fromdate" /> :</div>
									</td>
									<td width="30%" class="row-even"><html:text styleId="fromDate" property="fromDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'bulkChallanPrintForm',
													// input name
													'controlname' :'fromDate'
												});
											</script>
		                           </td> 
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.leavemodify.todate" /> :</div>
									</td>
									<td width="30%" class="row-even"><html:text styleId="toDate" property="toDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'bulkChallanPrintForm',
													// input name
													'controlname' :'toDate'
												});
											</script></td>
										</tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
									<td width="45%" height="35">
									<div align="right">
											<html:submit property="" styleClass="formbutton"><bean:message key="knowledgepro.print" />
											</html:submit>
									</div>
									</td>
									<td width="2%"></td>
									<td width="53%">
											<html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetMessages()">
												<bean:message key="knowledgepro.admin.reset" />
											</html:button>
									</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
<script type="text/javascript">
var print = "<c:out value='${bulkChallanPrintForm.print}'/>";
if(print.length != 0 && print == "true"){
	var url = "bulkChallanPrintForMain.do?method=popUpPrint";
	myRef = window .open(url, "PrintFeeChallan", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</script>
