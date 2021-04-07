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

<html:form action="/CollectionLedger" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="collectionLedgerForm" />
		
						
			<td valign="top">

				<table width="100%">

					<tr align="center">
													
							<td colspan="8" align="center" class="heading"><bean:write property="organizationName" name="collectionLedgerForm"/></td>
						</tr>
						 <tr>
			      		  <td colspan="8" align="center" class="heading">Collection For The Period:<bean:write property="startDate" name="collectionLedgerForm"/> To <bean:write property="endDate" name="collectionLedgerForm"/></td>
			            </tr>
			            <tr>
			               <td colspan="8" align="center" class="heading"><bean:write property="msg" name="collectionLedgerForm"/></td>
			             
			            </tr>
					<tr>
						<!--  <td valign="top">-->
							<c:set
								var="temp" value="0" /> 
								
								
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
									<div align="center"><bean:message key="knowledgepro.petticash.program" /></div>
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
									<div ><bean:write name="screenId" property="name"/> </div>
									</td>
									<td width="15%" class="row-left">
									<div ><bean:write name="screenId" property="className"/> </div>
									</td>
									<td width="10%" class="row-even">
									<div align="right"><bean:write name="screenId" property="amount"/> </div>
									</td>
								</tr>	
								</logic:iterate>
								 
						<tr>
			            <td  colspan="4" height="5%" class="row-even"></td>
						<td colspan="4" height="5%" class="bold-fontAmount"><bean:message key="knowledgepro.feepays.totalamount"/>:&nbsp;<bean:write property="totalAmount" name="collectionLedgerForm"/></td>
			            </tr>
			          </table>
					</td>
						 <td width="5" height="30" background="images/right.gif"></td>
						
</html:form>
<script type="text/javascript">
	window.print();
</script>