<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
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
<style>
checkprint
{
table {page-break-after:always}
}
</style>
</head>
<script type="text/javascript">
function printMe()
{
	window.print();
}
function closeMe()
{
	window.close();
}
</script>
<html:form action="/consolidatedCollectionList">	
<html:hidden property="formName" value="consolidatedCollectionReportForm"/>
			
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
				<tr>
					<td width="5" background="images/01.gif"></td>
					<td align="center" class="heading"><bean:write property="organizationName" name="consolidatedCollectionReportForm"/></td>
					<td width="5" height="30" background="images/03.gif"></td>
				</tr>
				 <tr>
	              <td width="5"  background="images/01.gif"></td>
	      		  <td align="center" class="heading">CONSOLIDATED COLLECTION LIST FROM &nbsp; <bean:write property="startDate" name="consolidatedCollectionReportForm"/> To  <bean:write property="endDate" name="consolidatedCollectionReportForm"/></td>
	              <td width="5" height="30"  background="images/03.gif"></td>
			      </tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<div style="overflow: auto; width: 914px;"><c:set
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
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.pettycash.consolidated.totalNo" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.pettycash.accheads.fixedamount" /></div>
									</td>
									
									<logic:notEmpty name="consolidatedCollectionReportForm" property="totalAccountList">
									<logic:iterate id="acc" name="consolidatedCollectionReportForm" property="totalAccountList">
									<td class="row-odd" align="center"><bean:write name="acc" property="accName"/></td>
									</logic:iterate>
									</logic:notEmpty>
									
									<td width="10%" class="row-odd">
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
									<div align="right">
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
								<td colspan="5" class="bold-fontAmount"><bean:message key="knowledgepro.feepays.totalamount"/>:</td>
								
									<logic:notEmpty name="consolidatedCollectionReportForm" property="amountList">
									<logic:iterate id="amount" name="consolidatedCollectionReportForm" property="amountList">
									<td  class="bold-fontAmount">
									<div align="right">
									 <bean:write name="amount"/>
									 </div>
								 	</td>
									</logic:iterate>
									</logic:notEmpty>
									
									<td class="bold-fontAmount" align="right">
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
			
</html:form>
<script type="text/javascript">
	window.print();
</script>
