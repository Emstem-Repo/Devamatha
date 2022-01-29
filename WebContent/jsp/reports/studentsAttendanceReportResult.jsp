<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<SCRIPT type="text/javascript">
    function printAreport(){
    	var url ="StudentsAttendanceReport.do?method=printStudentReport";
    	myRef = window.open(url,"StudentsAttendanceReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
        }
    function exportExcel() { 
    	document.location.href = "StudentsAttendanceReport.do?method=submitStudentAttendanceReport&mode=Excel";
    }
    function exportCSV() {
    	document.location.href = "StudentsAttendanceReport.do?method=submitStudentAttendanceReport&mode=CSV";
    }
    
</SCRIPT>
<html:form action="StudentsAttendanceReport" method="post">
	<c:choose>
		<c:when test="${conditionsOperation == 'monthly'}">
			<html:hidden property="method" styleId="method" value="initMonthlyStudentAttendanceReport" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="initStudentAttendanceReport" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="pageType" value="1" />
	<table width="98%" border="0">
		<tr>
			<td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.report.studentattendancereport" /><span
				class="Bredcrumbs">&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.report.studentattendancereport" /></strong></div>
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
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<div style="overflow: auto; width: 914px;">
							<display:table export="false" uid="studentAttendance" name="sessionScope.studentAttendanceReport" partialList="false" requestURI="StudentsAttendanceReport.do?method=submitStudentAttendanceReport&mode1=request1" defaultorder="ascending" pagesize="20" style="width:100%" >
								<!--<display:caption class="heading" media="html csv excel"><bean:message key="knowledgepro.report.studentattendancereport" /></display:caption>
								--><!--<display:setProperty name="export.excel.filename" value="StudentsAttendance.xls" />
								<display:setProperty name="export.csv.filename" value="StudentsAttendance.csv" />
									--><display:column title="Class Name" property="className" headerClass="row-odd" class="row-even" sortable="true" group="1" style="width:10%"></display:column>
									<display:column title="Register No" property="registerNumber" headerClass="row-odd" class="row-even" sortable="true" style="width:20%"></display:column>
									<display:column title="Student Name" property="studentName" headerClass="row-odd" class="row-even" sortable="true" style="width:40%"></display:column>
									<display:column title="Classes Held" property="classesHeld" headerClass="row-odd" class="row-even" sortable="true" style="width:10%"></display:column>
									<display:column title="Classes Attended" property="classesAttended" headerClass="row-odd" class="row-even" sortable="true" style="width:10%"></display:column>
									<display:column title="Percentage" property="percentage" headerClass="row-odd" class="row-even" sortable="true" style="width:10%"></display:column>
							</display:table>
							</div>
							<font size="1">Export options:&nbsp;<a href="#" onclick="exportExcel()" >Excel</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="exportCSV()" >CSV</a></font>
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

					<table width="100%" height="48" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							 <td height="25">
							<div align="right"><html:button property="" styleId="printme" styleClass="formbutton" value="Print" onclick="printAreport()"></html:button></div>
							</td>
							<td height="25">
							<div align="left">
							<html:submit property="Cancel" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:submit>
							</div>
							</td>
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
<logic:equal name="studentsAttendanceReportForm" property="mode" value="excel">
<logic:equal name="studentsAttendanceReportForm" property="downloadExcel" value="downloadExcel">

<SCRIPT type="text/javascript">	
hook=false;
document.location.href = "DownloadEmployeeDetailsExcelAction.do?downloadExcel=Excel";
myRef = window.open(url,"downloadReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
</SCRIPT>
</logic:equal>
</logic:equal>
<logic:equal name="studentsAttendanceReportForm" property="mode" value="CSV">
<logic:equal name="studentsAttendanceReportForm" property="downloadExcel" value="downloadCSV">

<SCRIPT type="text/javascript">	
hook=false;
document.location.href = "DownloadEmployeeDetailsExcelAction.do?downloadExcel=CSV";
myRef = window.open(url,"downloadReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
</SCRIPT>
</logic:equal>
</logic:equal>
</html:form>