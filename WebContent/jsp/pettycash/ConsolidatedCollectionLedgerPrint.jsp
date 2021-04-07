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

<html:form action="/ConsolidatedCollectionLedger" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="consolidatedCollectionLedgerForm" />
		
						
			<td valign="top">

				<table width="100%">

					<tr align="center">
													
							<td colspan="8" align="center" class="heading"><bean:write property="organizationName" name="consolidatedCollectionLedgerForm"/></td>
						</tr>
						 <tr>
			      		  <td colspan="8" align="center" class="heading">Consolidated Collection For The Period:<bean:write property="startDate" name="consolidatedCollectionLedgerForm"/> To <bean:write property="endDate" name="consolidatedCollectionLedgerForm"/></td>
			            </tr>
			            <tr>
			               <td colspan="8" align="center" class="heading"><bean:write property="msg" name="consolidatedCollectionLedgerForm"/></td>
			             
			            </tr>
					<tr>
							<c:set
								var="temp" value="0" /> 
								
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
						
</html:form>
<script type="text/javascript">
	window.print();
</script>