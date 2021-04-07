<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<html:html>
<head>
<script type="text/javascript">
function resetAttReport()	{
	
	document.getElementById("masterTableId").value = "";
	resetErrMsgs();
}
function showMasterReport(){
	document.getElementById("reportName").value = document.getElementById("masterTableId").options[document.getElementById("masterTableId").selectedIndex].text;	
	document.getElementById("method").value = "submitMasterReport";
	document.masterReportForm.submit();
}
</script>
</head>
<body>
<html:form action="masterReport" method="post">
<html:hidden property="method" styleId="method" value="submitMasterReport"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="masterReportForm" />
<html:hidden property="reportName" styleId="reportName" value="" />
<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.master.report"/>&gt;&gt;</span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body"><strong class="boxheader"> <bean:message key="knowledgepro.master.report"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="45" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr bgcolor="#FFFFFF">
				<td colspan="2" class="body" align="left">
				<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>		
	            <div id="errorMessage">
	            <FONT color="red"><html:errors/></FONT>
	             <FONT color="green">
						<html:messages id="msg" property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out><br>
						</html:messages>
	            </FONT>
	            </div>
	            </td>
	          </tr>
            <tr>
              <td><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
          <tr>
            <td width="5" background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
			<tr>
               <td width="50%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.master.report.select.report"/> </div></td>
               <td width="50%" height="25" class="row-even" align="left">
					<html:select name="masterReportForm" property="masterTable" styleId="masterTableId" styleClass="comboLarge" >
						<html:option value="">
							<bean:message key="knowledgepro.admin.select" />
						</html:option>						
						<html:option value="InterviewStatus"> <bean:message key="knowledgepro.admin.admstatus.report"/></html:option>
						<html:option value="AdmittedThrough"><bean:message key="knowledgepro.admin.admitted.through.report" /></html:option>
						<html:option value="AttendanceType"><bean:message key="knowledgepro.admin.attendance.type.report"  /></html:option>
						<html:option value="ApplicationNumber"><bean:message key="knowledgepro.admin.application.number.report" /></html:option>
						<html:option value="Activity"><bean:message key="knowledgepro.admin.activity.report" /></html:option>
						<html:option value="CourseScheme"> <bean:message key="knowledgepro.admin.course.scheme.report"/></html:option>
						<html:option value="Caste"> <bean:message key="knowledgepro.admin.caste.report"/></html:option>
						<html:option value="Country"><bean:message key="knowledgepro.admin.country.report" /></html:option>
						<html:option value="Currency"><bean:message key="knowledgepro.admin.currency.master.report"  /></html:option>
						<html:option value="CoursePrerequisite"><bean:message key="knowledgepro.admin.pre.requisite.definition.report" /></html:option>
						<html:option value="Classes"><bean:message key="knowledgepro.admin.class.entry.report" /></html:option>
						<html:option value="College"><bean:message key="knowledgepro.admin.institute.report"/></html:option>
						<html:option value="Course"><bean:message key="knowledgepro.admin.courses.report" /></html:option>
						<html:option value="DetailedSubjects"><bean:message key="knowledgepro.admin.detailed.subjects.report" /></html:option>
						<html:option value="DocType"><bean:message key="knowledgepro.admin.document.type.report" /></html:option>
						<html:option value="Department"><bean:message key="knowledgepro.admin.department.report" /></html:option>
						<html:option value="Designation"><bean:message key="knowledgepro.admin.designation..report" /></html:option>
						<html:option value="ExtracurricularActivity"><bean:message key="knowledgepro.admin.extra.curricular.activity.report" /></html:option>
						<html:option value="EligibilityCriteria"><bean:message key="knowledgepro.admin.elligibility.criteria.report" /></html:option>
						<html:option value="EmployeeCategory"><bean:message key="knowledgepro.admin.employee.category.report"  /></html:option>
						<html:option value="FeeDivision"><bean:message key="knowledgepro.admin.division.report"  /></html:option>
						<html:option value="FeePaymentMode"><bean:message key="knowledgepro.admin.fee.payment.mode.report" /></html:option>
						<html:option value="FeeBillNumber"><bean:message key="knowledgepro.admin.fee.bill.number.report" /></html:option>
						<html:option value="FeeGroup"><bean:message key="knowledgepro.admin.fee.group.report" /></html:option>
						<html:option value="FeeHeading"><bean:message key="knowledgepro.admin.fee.heading.report" /></html:option>
						<html:option value="FeeAdditional"><bean:message key="knowledgepro.admin.fee.additional.report" /></html:option>
						<html:option value="FeeAccount"><bean:message key="knowledgepro.admin.fee.account.report"  /></html:option>
						<html:option value="GuidelinesDoc"><bean:message key="knowledgepro.admin.guidelines.report" /></html:option>
						<html:option value="Grade"><bean:message key="knowledgepro.admin.grades.marks.report"/></html:option>
						<html:option value="InterviewSubRounds"><bean:message key="knowledgepro.admin.interview.sub.round.report" /></html:option>
						<html:option value="InterviewProgramCourse"><bean:message key="knowledgepro.admin.interview.definition.report" /></html:option>
						<html:option value="LeaveType"><bean:message key="knowledgepro.admin.leave.type.report" /></html:option>
						<html:option value="Modules"><bean:message key="knowledgepro.admin.module.report"/></html:option>
						<html:option value="Menus"><bean:message key="knowledgepro.admin.menu.report"/></html:option>
						<html:option value="NewsEvents"><bean:message key="knowledgepro.admin.news.events.report" /></html:option>
						<html:option value="Nationality"><bean:message key="knowledgepro.admin.nationality.report"/></html:option>
						<html:option value="Occupation"><bean:message key="knowledgepro.admin.occupation.report" /></html:option>
						<html:option value="Prerequisite"><bean:message key="knowledgepro.admin.pre.requisite.exam.report" /></html:option>
						<html:option value="Preferences"><bean:message key="knowledgepro.admin.preference.master.report"  /></html:option>
						<html:option value="ProgramType"><bean:message key="knowledgepro.admin.program.type.report"/></html:option>
						<html:option value="Period"><bean:message key="knowledgepro.admin.period.report" /></html:option>
						<html:option value="Program"><bean:message key="knowledgepro.admin.program.report" /></html:option>
						<html:option value="Religion"><bean:message key="knowledgepro.admin.religion.report"/></html:option>
						<html:option value="ResidentCategory"><bean:message key="knowledgepro.admin.resident.category" /></html:option>
						<html:option value="Region"><bean:message key="knowledgepro.admin.region.report"/></html:option>
						<html:option value="Roles"><bean:message key="knowledgepro.admin.roles.report" /></html:option>
						<html:option value="ReligionSection"><bean:message key="knowledgepro.admin.sub.religion.report" /></html:option>
						<html:option value="Recommendor"><bean:message key="knowledgepro.admin.recommended.by.report" /></html:option>
						<html:option value="RemarkType"><bean:message key="knowledgepro.admin.remark.type.report"/></html:option>
						<html:option value="State"><bean:message key="knowledgepro.admin.state.report"  /></html:option>
						<html:option value="Subject"><bean:message key="knowledgepro.admin.subject.entry.report" /></html:option>
						<html:option value="SubjectGroup"><bean:message key="knowledgepro.admin.subject.group.entry.report"  /></html:option>
						<html:option value="TermsConditions"><bean:message key="knowledgepro.admin.terms.conditions.report" /></html:option>
						<html:option value="University"><bean:message key="knowledgepro.admin.university.report"/></html:option>
					</html:select>
			   </td>
			</tr>
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="36" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
		
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:button property=""
										styleClass="formbutton" value="Submit"
										onclick="showMasterReport()"></html:button></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetAttReport()"></html:button></td>
						</tr>
					</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>