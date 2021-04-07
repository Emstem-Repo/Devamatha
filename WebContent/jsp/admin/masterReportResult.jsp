<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@ page buffer = "500kb" %>
<html:html>
<head>
<SCRIPT type="text/javascript">
    function cancelAction() {
    	document.location.href = "masterReport.do?method=initMasterReport";
    }
</SCRIPT>
</head>
<body>
<html:form action="masterReport" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="masterReportForm" />
<html:hidden property="reportName" styleId="reportName" value="" />
<table width="98%" border="0">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.master.report"/><span class="Bredcrumbs">&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:write name = "masterReportForm" property="reportName"/></strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
      <td valign="top">       

	<c:set var="temp" value="0" />  
<div style="overflow: scroll; width: 914px; ">	
	<logic:equal name="masterReportForm" property="masterTable" value="Caste" >
		<jsp:include page="/jsp/admin/masterreports/castmaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Country" >
		<jsp:include page="/jsp/admin/masterreports/countrymaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="InterviewStatus" >
		<jsp:include page="/jsp/admin/masterreports/admissionstatus.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="CourseScheme" >
		<jsp:include page="/jsp/admin/masterreports/coursescheme.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Religion" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Prerequisite" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="AdmittedThrough" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="ProgramType" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Occupation" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="ResidentCategory" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Region" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="University" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Department" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Roles" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="EmployeeCategory" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="LeaveType" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="FeeDivision" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="FeePaymentMode" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Designation" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Preferences" >
		<jsp:include page="/jsp/admin/masterreports/preferencesMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Currency" >
		<jsp:include page="/jsp/admin/masterreports/currencymasterreport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="ReligionSection" >
		<jsp:include page="/jsp/admin/masterreports/religionsection.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="State" >
		<jsp:include page="/jsp/admin/masterreports/stateMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="CoursePrerequisite" >
		<jsp:include page="/jsp/admin/masterreports/courseprerequisitemaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Program" >
		<jsp:include page="/jsp/admin/masterreports/programMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Course" >
		<jsp:include page="/jsp/admin/masterreports/coursesMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="GuidelinesDoc" >
		<jsp:include page="/jsp/admin/masterreports/guidelinesMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="TermsConditions" >
		<jsp:include page="/jsp/admin/masterreports/termsConditionsMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Subject" >
		<jsp:include page="/jsp/admin/masterreports/subjectEntryMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Classes" >
		<jsp:include page="/jsp/admin/masterreports/classesMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="DocType" >
		<jsp:include page="/jsp/admin/masterreports/documentTypeEntryMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="College" >
		<jsp:include page="/jsp/admin/masterreports/instituteEntryMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Grade" >
		<jsp:include page="/jsp/admin/masterreports/gradesMarksMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Recommendor" >
		<jsp:include page="/jsp/admin/masterreports/recommendedByMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="SubjectGroup" >
		<jsp:include page="/jsp/admin/masterreports/subjectGroupEntryMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="DetailedSubjects" >
		<jsp:include page="/jsp/admin/masterreports/detailedSubjectsMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="EligibilityCriteria" >
		<jsp:include page="/jsp/admin/masterreports/eligibilityCriteriaMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="NewsEvents" >
		<jsp:include page="/jsp/admin/masterreports/newsEventsEntryMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="ExtracurricularActivity" >
		<jsp:include page="/jsp/admin/masterreports/extraCurricularMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="FeeBillNumber" >
		<jsp:include page="/jsp/admin/masterreports/feeBillNumberMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="FeeGroup" >
		<jsp:include page="/jsp/admin/masterreports/feeGroupMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="FeeHeading" >
		<jsp:include page="/jsp/admin/masterreports/feesHeadingsMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="FeeAdditional" >
		<jsp:include page="/jsp/admin/masterreports/feeAdditionalMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="FeeAccount" >
		<jsp:include page="/jsp/admin/masterreports/feeAccountMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Period" >
		<jsp:include page="/jsp/admin/masterreports/periodReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="InterviewSubRounds" >
		<jsp:include page="/jsp/admin/masterreports/interviewSubRoundMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="InterviewProgramCourse" >
		<jsp:include page="/jsp/admin/masterreports/intDefinitionReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="ApplicationNumber" >
		<jsp:include page="/jsp/admin/masterreports/applicationNumberMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Activity" >
		<jsp:include page="/jsp/admin/masterreports/activityReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="AttendanceType" >
		<jsp:include page="/jsp/admin/masterreports/attendanceTypeMaster.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Modules" >
		<jsp:include page="/jsp/admin/masterreports/moduleReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Menus" >
		<jsp:include page="/jsp/admin/masterreports/menusReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="RemarkType" >
		<jsp:include page="/jsp/admin/masterreports/remarkTypeReport.jsp" />
	</logic:equal>
	<logic:equal name="masterReportForm" property="masterTable" value="Nationality" >
		<jsp:include page="/jsp/admin/masterreports/commonReport.jsp" />
	</logic:equal>
	</div>
		</td>
              <td width="5" height="30" background="images/right.gif"></td>
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
        <td height="61" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
          
              <table width="100%" height="48"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="25"><div align="center">                  
					<html:button property="cancel" onclick="cancelAction()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button>
                  </div></td>
                </tr>
              </table>
            
        </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>