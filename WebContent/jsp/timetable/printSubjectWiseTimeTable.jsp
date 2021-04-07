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
<script type="text/javascript">


</script>
<html:form action="/subjectWiseTimeTableView" method="post">
	<html:hidden property="formName" value="SubjectWiseTimeTableViewForm" />
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
									<td height="25" colspan="6" class="heading">Subject
									: <bean:write name="SubjectWiseTimeTableViewForm"
										property="subject" /></td>

								</tr>
								<tr>
									<td width="71" height="25" class="row-odd">Week Day</td>
									<nested:iterate id="val" name="SubjectWiseTimeTableViewForm" property="periodList">
										<td class="row-odd"><nested:write name="val" /></td>
									</nested:iterate>
								</tr>
								<nested:iterate id="object" property="subTimeList"
									indexId="count">
									<tr>
									<c:choose>
										<c:when test="${count%2==0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td height="25"><nested:write name="object"
										property="dayName" /></td>
									<nested:iterate name="object" id="object1"
										property="subMapList">
										<td width="256"><nested:write name="object1" /></td>
									</nested:iterate>

									</tr>
								</nested:iterate>
								
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
