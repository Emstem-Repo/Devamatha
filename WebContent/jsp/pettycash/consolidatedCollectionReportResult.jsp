<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<%@page import="java.util.List"%>
<%@page import="com.lowagie.text.Document"%><head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendarinterview.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
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
</head>
<script type="text/javascript">
function cancelAction() {
	document.location.href = "consolidatedCollectionList.do?method=initConsolidatedCollectionList";
}
function printICard(){
	var url ="consolidatedCollectionList.do?method=printconsolidatedCollectionList";
	myRef = window.open(url,"collectionLedger","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}

</script>
<html:form action="/consolidatedCollectionList">	
		<html:hidden property="method" styleId="method" value="searchStudentList" />
		<html:hidden property="formName" value="consolidatedCollectionReportForm"/>
		<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.petticash"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.pettycash.consolidated.display"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.pettycash.consolidated.display"/></strong></td>

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
					<td width="5" background="images/Tright_03_03.gif"></td>
					<td align="center" class="heading"><bean:write property="organizationName" name="consolidatedCollectionReportForm"/></td>
					<td width="5" height="30" background="images/Tright_3_3.gif"></td>
				</tr>
				 <tr>
	              <td width="5"  background="images/Tright_03_03.gif"></td>
	      		  <td align="center" class="heading">CONSOLIDATED COLLECTION LIST FROM  &nbsp; <bean:write property="startDate" name="consolidatedCollectionReportForm"/> To  <bean:write property="endDate" name="consolidatedCollectionReportForm"/></td>
	              <td width="5" height="30"  background="images/Tright_3_3.gif"></td>
			      </tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
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
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<c:set
								var="temp" value="0" /> 
								<table>
								<tr>
									<td  height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td  class="row-odd">
									<div align="center"><bean:message
									key="knowledgepro.petticash.accountCode" /></div>
									</td>
									<td  class="row-odd">
									<div align="center"><bean:message
									key="knowledgepro.petticash.accountName" /></div>
									</td>
									<td  class="row-odd">
									<div align="center"><bean:message key="knowledgepro.pettycash.consolidated.totalNo" /></div>
									</td>
									<td  class="row-odd">
									<div align="center"><bean:message key="knowledgepro.pettycash.accheads.fixedamount" /></div>
									</td>
									<logic:notEmpty name="consolidatedCollectionReportForm" property="totalAccountList">
									<logic:iterate id="acc" name="consolidatedCollectionReportForm" property="totalAccountList">
									<td  class="row-odd" align="center"><bean:write name="acc" property="accName"/></td>
									</logic:iterate>
									</logic:notEmpty>
									<td  class="row-odd">
									<div align="center"><bean:message key="knowledgepro.feepays.totalamount" /></div>
									</td>
								</tr>
								<logic:notEmpty name="consolidatedCollectionReportForm" property="selectedData">
								<logic:iterate id="screenId" name="consolidatedCollectionReportForm" property="selectedData" indexId="count">
									<tr>
									<td width="15%" class="row-even">
									<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td width="15%" height="25" class="row-even">
									<div align="left"><bean:write name="screenId" property="accountCode"/>  </div>
									</td>
									<td width="15%" class="row-even">
									<div align="left"><bean:write name="screenId" property="accountName"/> </div>
									</td>
									<td width="15%" class="row-even">
									<div align="center"><bean:write name="screenId" property="totalNumber"/> </div>
									</td>
									<td width="15%" class="row-even">
									<div align="right"><bean:write name="screenId" property="fixedAmount"/> </div>
									</td>
									<logic:notEmpty name="screenId" property="accountList">
									<logic:iterate id="acc" name="screenId" property="accountList">
									<td class="row-even">
									<div align="right" >
									 <bean:write name="acc" property="amount"/> 
									 </div>
								 	</td>
									</logic:iterate>
									</logic:notEmpty>
									<td width="10%" class="row-even">
									<div align="right"><bean:write name="screenId" property="amount"/> </div>
									</td>
								</tr>	
								</logic:iterate>
								</logic:notEmpty>
								<tr>
								<td colspan="5" class="bold-fontAmount" align="right"><bean:message key="knowledgepro.feepays.totalamount"/>:</td>
								
									<logic:notEmpty name="consolidatedCollectionReportForm" property="amountList">
									<logic:iterate id="amount" name="consolidatedCollectionReportForm" property="amountList">
									<td class="bold-fontAmount">
									<div align="right">
									 <bean:write name="amount"/>
									 </div>
								 	</td>
									</logic:iterate>
									</logic:notEmpty>
								
									<td class="bold-fontAmount">
			      		 			 <div align="right"><bean:write property="totalAmount" name="consolidatedCollectionReportForm"/></div>
			      		 			 </td>
								</tr>
								</table>
							</td>
						<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
						<td width="5" background="images/left.gif"></td>
							<td ><font color="black" size="3px">
							<bean:write name="consolidatedCollectionReportForm" property="groupCodeValue"/>
							</font>
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
			</table>
			
	</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:button property="" styleId="printme" styleClass="formbutton" value="Print" onclick="printICard()"></html:button></div>
							</td>
							<td width="1%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" value="Cancel" onclick="cancelAction()">
								
								</html:button> 
							</td>
						</tr>
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