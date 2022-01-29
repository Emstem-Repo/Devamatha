<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<SCRIPT type="text/javascript">
function cancelAction() {
	document.location.href = "absenceInformationSummary.do?method=initMonthlyAbsenceInformationSummary";
}
function printAreport(){
	var url ="absenceInformationSummary.do?method=printMonthlyAbsenceInformationSummary";
	myRef = window.open(url,"AttendTeacherReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
    }
</SCRIPT>
<html:form action="/absenceInformationSummary" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="absenceInformationSummaryForm" />
	<html:hidden property="pageType" value="4"/>

	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.monthlyabsence.summaryreport" /><span
				class="Bredcrumbs">&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.monthlyabsence.summaryreport" /></strong></div>
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
							<div style="overflow: auto; width: 730px;"><c:set
								var="temp" value="0" /> 
								
								<display:table export="true" uid="absenceid"
								name="sessionScope.monthlyabsenceSummaryReport" requestURI=""
								defaultorder="ascending" pagesize="10">
								<display:setProperty name="export.excel.filename"
									value="Absence Information.xls" />
								<display:setProperty name="export.xml" value="false" />
								<display:setProperty name="export.csv.filename"
									value="Absence Information.csv" />
								<c:choose>
									<c:when test="${temp == 0}">
										<display:column style=" padding-right: 80px; " sortable="true"
											title="Registration Number/Roll No." class="row-even"
											headerClass="row-odd">
											<c:out value="${absenceid.registerNo}" />
										</display:column>
										<display:column style=" padding-right: 80px;"
											property="studentName" sortable="true" title="Student Name"
											class="row-even" headerClass="row-odd" />

									<logic:notEmpty name="absenceid" property="subjectMaplist">
										<logic:iterate id="index" name="absenceid"
											property="subjectMaplist"
											type=" com.kp.cms.to.reports.AbsenceInfoMapTO">
											<%String title = index.getTitle(); %>
											<display:column title="<%=title %>" headerClass="row-odd" class="row-even" sortable="true">
												<bean:write name="index" property="value" />
											</display:column>

										</logic:iterate>
									</logic:notEmpty>
										<c:set var="temp" value="1" />
									</c:when>
									<c:otherwise>

										<display:column style=" padding-right: 80px; " sortable="true"
											title="Registration Number/Roll No." class="row-white"
											headerClass="row-odd">
											<c:out value="${absenceid.registerNo}" />
										</display:column>
										<display:column style=" padding-right: 80px;"
											property="studentName" sortable="true" title="Student Name"
											class="row-white" headerClass="row-odd" />
									<logic:notEmpty name="absenceid" property="subjectMaplist">
										<logic:iterate id="index" name="absenceid"
											property="subjectMaplist"
											type=" com.kp.cms.to.reports.AbsenceInfoMapTO">
											<%String title = index.getTitle(); %>
											<display:column title="<%=title %>" headerClass="row-odd" class="row-white" sortable="true">
												<bean:write name="index" property="value" />
											</display:column>

										</logic:iterate>
										</logic:notEmpty>
										<c:set var="temp" value="0" />
									</c:otherwise>
								</c:choose>
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

					 <table width="100%" height="48"  border="0" cellpadding="0" cellspacing="0">
		                <tr>
		                <td height="25">
						<div align="right"><html:button property="" styleId="printme" styleClass="formbutton" value="Print" onclick="printAreport()"></html:button></div>
						</td>
		                  <td height="25"><div align="left">                  
							<html:button property="cancel" onclick="cancelAction()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button>
		                  </div></td>
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

