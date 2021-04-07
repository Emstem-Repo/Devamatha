<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<SCRIPT type="text/javascript">
function cancelAction() {
	document.location.href = "CollectionLedger.do?method=initCollectionLedger";
}
function printICard(){
	var url ="CollectionLedger.do?method=printCollectionLedgerReportResult";
	myRef = window.open(url,"collectionLedger","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
    }
function ExcelAction(){
	  document.getElementById("method").value="ExportToExcelAction";
}
</SCRIPT>
<html:form action="/CollectionLedger" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="collectionLedgerForm" />
	<table width="98%" border="0">
		<tr>
			<td class="heading"><bean:message key="knowledgepro.petticash"/>&gt;&gt;
			<span class="Bredcrumbs"><bean:message
				key="knowledgepro.petticash.collectionLedger.displayName" /><span
				class="Bredcrumbs">&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
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
			      		  <td align="center" class="heading"><bean:write property="organizationName" name="collectionLedgerForm"/> </td>
			              <td width="5" height="30"  background="images/right.gif"></td>
			            </tr>
			            <tr>
			              <td width="5"  background="images/left.gif"></td>
			      		  <td align="center" class="heading">Collection For The Period:<bean:write property="startDate" name="collectionLedgerForm"/> To  <bean:write property="endDate" name="collectionLedgerForm"/></td>
			              <td width="5" height="30"  background="images/right.gif"></td>
			            </tr>
			            <tr>
			              <td width="5"  background="images/left.gif"></td>
			      		  <td align="center" class="heading"><bean:write property="msg" name="collectionLedgerForm"/></td>
			              <td width="5" height="30"  background="images/right.gif"></td>
			            </tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<div style="overflow: auto; width: 914px;"><c:set
								var="temp" value="0" /> 
								<table>
								<tr>
									<td width="15%" height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.date" /></div>
									</td>
									<td width="15%" class="row-odd">
									<div align="center"><bean:message
									key="knowledgepro.petticash.time" /></div>
									</td>
									<td width="15%" class="row-odd">
									<div align="center"><bean:message
									key="knowledgepro.petticash.recieptNo" /></div>
									</td>
									<td width="15%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.Applno" /></div>
									</td>
									<td width="15%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.RegNo" /></div>
									</td>
									<td width="15%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.Name" /></div>
									</td>
									<td width="10%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.hostel.student.class" /></div>
									</td>
									<td width="10%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.Amount" /></div>
									</td>
								</tr>
								<logic:iterate id="screenId" name="SelectedData"  type="com.kp.cms.to.pettycash.CollectionLedgerTO" scope="session" indexId="count">
									<tr>
									<td width="15%" height="25" class="row-even">
									<div align="center"><bean:write name="screenId" property="date"/>  </div>
									</td>
									<td width="15%" class="row-even">
									<div align="center"><bean:write name="screenId" property="time"/> </div>
									</td>
									<td width="15%" class="row-even">
									<div align="center"><bean:write name="screenId" property="receiptNumber"/> </div>
									</td>
									<td width="15%" class="row-even">
									<div align="center"><bean:write name="screenId" property="applNo"/> </div>
									</td>
									<td width="15%" class="row-even">
									<div align="center"><bean:write name="screenId" property="regNo"/> </div>
									</td>
									<td width="15%" class="row-left">
									<div align="left"><bean:write name="screenId" property="name"/> </div>
									</td>
									<td width="15%" class="row-left">
									<div align="left"><bean:write name="screenId" property="className"/> </div>
									</td>
									<td width="10%" class="row-even">
									<div align="right"><bean:write name="screenId" property="amount"/> </div>
									</td>
								</tr>	
								</logic:iterate>
								<tr>
			              
			            <td  colspan="4" height="5%" class="row-even"></td>
						<td colspan="4" height="5%" class="bold-fontAmount"><bean:message key="knowledgepro.feepays.totalamount"/>:&nbsp;<bean:write property="totalAmount" name="collectionLedgerForm"/></div></td>
			             
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
							<td width="46%" height="35" align="center">
							<html:button property="" styleId="printme" styleClass="formbutton" value="Print" onclick="printICard()"></html:button>&nbsp;&nbsp;&nbsp;
						    <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()"></html:button> &nbsp;&nbsp;&nbsp;
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
<logic:notEmpty name="collectionLedgerForm" property="downloadExcel">
<logic:notEmpty name="collectionLedgerForm" property="mode">
<bean:define id="downloadExcels" property="downloadExcel" name="collectionLedgerForm"></bean:define>
<bean:define id="modes" property="mode" name="collectionLedgerForm"></bean:define>

<logic:equal name="collectionLedgerForm" property="mode" value="excel">
<logic:equal name="collectionLedgerForm" property="downloadExcel" value="download">

<SCRIPT type="text/javascript">	
var url ="DownloadCollectionLedger.do";
myRef = window.open(url,"downloadReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
			
</SCRIPT>
</logic:equal>
</logic:equal>

</logic:notEmpty>
</logic:notEmpty>
</html:form>