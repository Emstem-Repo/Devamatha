<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<SCRIPT type="text/javascript">
    function cancelAction() {
    	document.location.href = "attendTeacherReport.do?method=initTeacherReport";
    }
    function printAreport(){
    	var url ="StudentAbsenceDetails.do?method=printStudentAbsenceReport";
    	myRef = window.open(url,"AttendTeacherReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
        }
</SCRIPT>
<html:form action="StudentAbsenceDetails" method="post">
	<html:hidden property="method" styleId="method" value="initStudentAbsenceDetails" />
	<html:hidden property="pageType" value="1" />
	<table width="98%" border="0">
		<tr>
			<td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.report.studentabsencedetailsreport" /><span
				class="Bredcrumbs">&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.report.studentabsencedetailsreport" /></strong></div>
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
								<display:table export="true" uid="studentAbsence" name="sessionScope.studentAbsenceDetails" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
								<display:setProperty name="export.excel.filename" value="StudentAbsenceDetails.xls" />
								<display:setProperty name="export.xml" value="false" />
								<display:setProperty name="export.csv.filename" value="StudentAbsenceDetails.csv" />
									<display:column title="Register/Roll No." property="registerNumber" sortable="true" class="row-even" headerClass="row-odd" style="width:22%"></display:column>
									<display:column title="Student Name" property="studentName" sortable="true" class="row-even" headerClass="row-odd" style="width:22%"></display:column>
									<display:column title="Class Name" class="row-even" headerClass="row-odd" style="width:8%" sortable="true">
										 <c:forEach var="studentAbsenceTO1" items="${studentAbsence.studentAbsenceDetailsList}" varStatus="index"> <c:out value="${studentAbsenceTO1.className}"/></c:forEach>
									</display:column>
									<display:column title="Subject Code" class="row-even" headerClass="row-odd" style="width:10%" sortable="true">
										 <c:forEach var="studentAbsenceTO2" items="${studentAbsence.studentAbsenceDetailsList}" varStatus="index"> <c:out value="${studentAbsenceTO2.subjectCode}"/></c:forEach>
									</display:column>
									<display:column title="Date" class="row-even" headerClass="row-odd" style="width:12%" sortable="true">
										 <c:forEach var="studentAbsenceTO3" items="${studentAbsence.studentAbsenceDetailsList}" varStatus="index"> <c:out value="${studentAbsenceTO3.attendanceDate}" /></c:forEach>
									</display:column>
									<display:column title="Period" class="row-even" headerClass="row-odd" style="width:15%" sortable="true">
										 <c:forEach var="studentAbsenceTO4" items="${studentAbsence.studentAbsenceDetailsList}" varStatus="index"> <c:out value="${studentAbsenceTO4.periodName}" /></c:forEach>
									</display:column>
									<display:column title="Faculty Name" class="row-even" headerClass="row-odd" style="width:8%" sortable="true">
										 <c:forEach var="studentAbsenceTO5" items="${studentAbsence.studentAbsenceDetailsList}" varStatus="index"> <c:out value="${studentAbsenceTO5.facultyName}" /></c:forEach>
									</display:column>
							</display:table>
							</div>
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
</html:form>