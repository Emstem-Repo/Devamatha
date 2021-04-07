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
</head>
<html:form action="/studentCollectionReport">	
<html:hidden property="formName" value="studentCollectionReportForm"/>
			
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
				<tr>
					<td></td>
					<td align="center" class="heading"><bean:write property="organizationName" name="studentCollectionReportForm"/></td>
					<td></td>
				</tr>
				 <tr>
	              <td></td>
	      		  <td align="center" class="heading"><bean:write property="startDate" name="studentCollectionReportForm"/> To  <bean:write property="endDate" name="studentCollectionReportForm"/></td>
	              <td></td>
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
								<table width="100%">
								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.date" /></div>
									</td>
									<td width="15%" class="row-odd">
									<div align="center"><bean:message
									key="knowledgepro.petticash.time" /></div>
									</td>
									<td width="15%" class="row-odd">
									<div align="center"><bean:message
									key="knowledgepro.admission.receiptno" /></div>
									</td>
									<td width="15%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.collectionLedger.AccountCode1" /></div>
									</td>
									<td width="15%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.collectionLedger.AccountName" /></div>
									</td>
									<td width="15%" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.petticash.Amount" /></div>
									</td>
								</tr>
								<logic:notEmpty name="studentCollectionReport" scope="session">
								<logic:iterate id="screenId" name="studentCollectionReport" scope="session" type="com.kp.cms.to.pettycash.StudentCollectionReportTO">
									<tr>
									<td width="25%" class="row-even">
									<div align="center"><bean:write name="screenId" property="date"/></div>
									</td>
									<td width="15%" height="25" class="row-even">
									<div align="center"><bean:write name="screenId" property="time"/>  </div>
									</td>
									<td width="15%" class="row-even">
									<div align="center"><bean:write name="screenId" property="recNumber"/> </div>
									</td>
									<td width="15%" class="row-even">
									<div align="center"><bean:write name="screenId" property="accCode"/> </div>
									</td>
									<td width="15%" class="row-even">
									<div align="center"><bean:write name="screenId" property="accName"/> </div>
									</td>
									<td width="15%" class="row-even">
									<div align="center"><bean:write name="screenId" property="amount"/> </div>
									</td>
								</tr>	
								</logic:iterate>
								</logic:notEmpty>
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
</html:form>
<script type="text/javascript">
	window.print();
</script>
