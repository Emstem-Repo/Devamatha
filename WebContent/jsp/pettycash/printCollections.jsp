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

<html:form action="/collectionsDay" method="post">
<html:hidden property="formName" value="collectionsReportForm" />

		<td valign="top">

						<table width="100%">

							<tr align="center">
								<td colspan="8" align="center">
								<div align="center" class="header"><bean:write property="organizationName" name="collectionsReportForm"/><br>
								COLLECTIONS FOR THE DAY 
										<bean:write name='collectionsReportForm' property='startDate' /> &nbsp;to &nbsp;<bean:write name='collectionsReportForm' property='endDate' />
									</div>
								</td>
							</tr>
							<tr>
								<td height="5%" class="row-odd"><div align="center">ReceiptNumber</div></td>
								<td height="5%" class="row-odd"><div align="center">Appl.No</div></td>
								<td height="5%" class="row-odd"><div align="center">Register No</div></td>
								<td  height="5%" class="row-odd"><div align="center">Name</div></td>
								<td  height="5%" class="row-odd"><div align="center">Time</div></td>
								<td height="5%" class="row-odd"><div align="center">A/c Name</div></td>
								<td height="5%" class="row-odd"><div align="center">Class Name</div></td>
								<logic:notEmpty
									name="collectionsReportForm" property="totalAccountList">
									<logic:iterate id="dailycollections1"
										name="collectionsReportForm" property="totalAccountList">
										<td style="padding-left: 10px; font-size: x-small;class=row-odd" class="row-odd">
										<bean:write name="dailycollections1"  property="accName"/>
										</td>
									</logic:iterate>
								</logic:notEmpty>
								<td  class="row-odd"><div align="center">Total Amount</div></td>
							</tr>
							<logic:notEmpty name="dailycollections">
								<logic:iterate id="dailycollections1" name="dailycollections">
									<tr>
										<td height="5%" class="row-even"><div align="center"><bean:write
											name='dailycollections1' property='receiptOrStudentnum' /></div></td>
											
										<td height="5%" class="row-even"><div align="center"><bean:write
											name='dailycollections1' property='applicationNo' /></div></td>
											
										<td  height="5%" class="row-even"><div align="center"><bean:write
											name='dailycollections1' property='regNo' /></div></td>
											
										<td height="5%" class="row-left"><bean:write
											name='dailycollections1' property='name' /></td>
											
										<td   height="5%" class="row-even"><div align="center"><bean:write
											name='dailycollections1' property='time' /></div></td>
											
										<td   height="5%" class="row-even"><div align="left"><bean:write
											name='dailycollections1' property='accountName' /></div></td>
											
								<c:choose>
									<c:when test="${dailycollections1.classname!=null}">
											<td  height="5%" class="row-even"><bean:write
											name='dailycollections1' property='classname' /></td>
									</c:when>
									<c:otherwise>
											<td  height="5%" class="row-even"><bean:write
											name='dailycollections1' property='coursecode' /></td>
									</c:otherwise>
								</c:choose>
										
									<logic:iterate id="tempid" name="dailycollections1" property="accountHeadList" indexId="count">
									<td  class="row-right" ><bean:write name='tempid' property='amount' /> </td> 
									</logic:iterate>
											
									 <td width="5%" class="row-right">
								     <logic:iterate id="tempid1" name="dailycollections1" property="accountHeadList" indexId="count">
									 <bean:write property='amount' name='tempid1'/>
									 </logic:iterate>
									 </td>		
									
									</tr>
							</logic:iterate>
							</logic:notEmpty>
							<tr><td  colspan="4" height="5%" class="row-even"></td>
								<td colspan="3" height="5%" class="bold-fontAmount"> Total Amount</td>
								
										<logic:iterate id="amountId" name="collectionsReportForm" property="totalAmountList" indexId="count">
										<td class="bold-fontAmount" >
										<bean:write name='amountId'/> </td> 
										</logic:iterate>
									
								<td  height="5%" class="bold-fontAmount">
								<bean:write name="collectionsReportForm" property="totamount"/>
								</td>
							</tr>
						</table>
						</td>
						<td width="5" height="29" background="images/right.gif"></td>
			            
</html:form>

<script type="text/javascript">
	window.print();
</script>
