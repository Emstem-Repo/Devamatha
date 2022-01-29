<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<SCRIPT type="text/javascript">
function cancelAction() {
	document.location.href = "ConsolidatedCollectionLedger.do?method=initConsolidatedCollectionLedger";
}
function printICard(){
	var url ="ConsolidatedCollectionLedger.do?method=printConsolidatedCollectionLedger";
	myRef = window.open(url,"collectionLedger","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
    }
function ExcelAction(){
	  document.getElementById("method").value="ExportToExcelAction";
}
</SCRIPT>
<html:form action="/ConsolidatedCollectionLedger" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="consolidatedCollectionLedgerForm" />
	<table width="98%" border="0">
		<tr>
			<td class="heading"><bean:message key="knowledgepro.petticash"/>&gt;&gt;
			<span class="Bredcrumbs"><bean:message
				key="knowledgepro.petticash.collectionLedger.displayName" /><span
				class="Bredcrumbs">&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.petticash.collectionLedger.displayName" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="8" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="8" valign="top"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="8" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="8" valign="top">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						 <tr>
			              <td width="5"  background="images/left.gif"></td>
			      		  <td align="center" class="heading"><bean:write property="organizationName" name="consolidatedCollectionLedgerForm"/> </td>
			              <td width="5" height="30"  background="images/right.gif"></td>
			            </tr>
			            <tr>
			              <td width="5"  background="images/left.gif"></td>
			      		  <td align="center" class="heading">Consolidated Collection For The Period:<bean:write property="startDate" name="consolidatedCollectionLedgerForm"/> To  <bean:write property="endDate" name="consolidatedCollectionLedgerForm"/></td>
			              <td width="5" height="30"  background="images/right.gif"></td>
			            </tr>
			            <tr>
			              <td width="5"  background="images/left.gif"></td>
			      		  <td align="center" class="heading"><bean:write property="msg" name="consolidatedCollectionLedgerForm"/></td>
			              <td width="5" height="30"  background="images/right.gif"></td>
			            </tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
								   <td height="10%" height="25" class="row-odd" >
								   <div align="center"><bean:message key="knowledgepro.slno"/></div>
								   </td>
									<td width="30%" height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.date" /></div>
									</td>
									<td width="30%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.AccountNo" /></div>
									</td>
									<td width="30%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.consolidated.netamount" /></div>
									</td>
								</tr>
								<logic:iterate id="screenId" name="SelectedData"  type="com.kp.cms.to.pettycash.ConsolidatedCollectionLedgerTO" scope="session" indexId="count">
									<tr>
									<td width="10%" height="25" class="row-even" >
									<div align="center"><c:out value="${count + 1}"/></div>
									</td>
									<td width="30%" height="25" class="row-even">
									<div align="center"><bean:write name="screenId" property="date"/>  </div>
									</td>
									<td width="30%" class="row-even">
									<div align="center"><bean:write name="screenId" property="accountNo"/> </div>
									</td>
									<td width="30%" class="row-even" align="right">
									<div align="center"><bean:write name="screenId" property="netAmount"/> </div>
									</td>
								</tr>	
								</logic:iterate>
						 <tr>
			            <td  colspan="3" height="5%" class="row-even"></td>
						<td colspan="1" height="5%" class="bold-fontAmount">&nbsp;&nbsp;<div align="left"><bean:message key="knowledgepro.feepays.totalamount"/>&nbsp;:&nbsp;<bean:write property="totalAmount" name="consolidatedCollectionLedgerForm"/></div></td>
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
					<td height="61" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center"><html:button property="" styleId="printme" styleClass="formbutton" value="Print" onclick="printICard()"></html:button>&nbsp;&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Close" onclick="cancelAction()"></html:button>&nbsp;&nbsp;&nbsp;
							<html:submit property="" styleClass="formbutton" value="Export To Excel" onclick="ExcelAction()"></html:submit></td>
						</tr>
					</table>
				</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<logic:notEmpty name="consolidatedCollectionLedgerForm" property="downloadExcel">
<logic:notEmpty name="consolidatedCollectionLedgerForm" property="mode">
<bean:define id="downloadExcels" property="downloadExcel" name="consolidatedCollectionLedgerForm"></bean:define>
<bean:define id="modes" property="mode" name="consolidatedCollectionLedgerForm"></bean:define>

<logic:equal name="consolidatedCollectionLedgerForm" property="mode" value="excel">
<logic:equal name="consolidatedCollectionLedgerForm" property="downloadExcel" value="download">

<SCRIPT type="text/javascript">	
var url ="DownloadConsolidatedCollectionLedger.do";
myRef = window.open(url,"downloadReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
			
</SCRIPT>
</logic:equal>
</logic:equal>

</logic:notEmpty>
</logic:notEmpty>
</html:form>