<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>

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
	function prePrint(){
	  document.getElementById("printme").style.visibility = "hidden";
	}
	function postPrint(){	
	  document.getElementById("printme").style.visibility = "visible";
	}	
	function printICard(){	
		prePrint();
		window.print();
		postPrint();
	}
	function closeICard(){	
	  	window.close();
	}
</script>

<html:form action="/ScoreSheet" method="post">

	<div>	
	<table>
		<tr>												
			<td style="height:25px;width:50px;align:left"></td>
			<td style="height:25px;width:200px;align:left"></td>
			<td style="height:25px;width:100px;align:left"></td>
			<td style="height:25px;width:100px;align:left"></td>
			<td style="height:25px;width:300px;align:left"></td>
			<td style="height:25px;width:100px;align:left"></td>
			<td style="height:25px;width:100px;align:left"></td>			
		</tr>
		<tr>												
			<td style="height:25px;width:50px;align:left" class="row-odd"><bean:message key="knowledgepro.slno" /></td>
			<td style="height:25px;width:200px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.name" /></td>
			<td style="height:25px;width:100px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.actionReport.type" /></td>
			<td style="height:25px;width:100px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.hostelerdatabase.regno/staffId" /></div></td>
			<td style="height:25px;width:300px;align:left" class="row-odd"><bean:message key="knowledgepro.studentlogin.name" /></td>
			<td style="height:25px;width:100px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.actionReport.noOfDaysAbsent" /></td>
			<td style="height:25px;width:100px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.actionReport.approvedNoOfDays" /></td>			
		</tr>
		<logic:notEmpty name="hostelActionReportForm" property="hostelActionReportList">												
		<nested:iterate id="actionReportTO" property="hostelPrintList" name="hostelActionReportForm" type="com.kp.cms.to.hostel.HostelActionReportTO" indexId="cnt" >
		<%String str="printId"+cnt;%>
		<tr>															
			<td style="height:25px;align:left" class="row-even"><c:out value="${cnt+1}" /></td>
			<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="hostelName" /></td>
			<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="typeId" /></td>
			<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="regNo" /></td>
			<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="name" /></td>
			<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="daysOfAbsent" /></td>
			<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="daysOfAbsent" /></td>			
		</tr>
		</nested:iterate> 
		</logic:notEmpty>	
	</table>
	</div>
	<script type="text/javascript">
		window.print();
	</script>
</html:form>