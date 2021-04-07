<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<html:html>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendar_us.js"></script>
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
function getClasses(year) {
	if(!year.length > 0){
		document.getElementById("classesName").value = "";
	}else{
	getClassesByYear("classMap", year, "classesName", updateClasses);
		}
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classesName", " - Select -");
}
function resetAttReport()	{

	resetErrMsgs();
	document.location.href = "attendSummaryReport.do?method=initMonthlyAttendSummaryReport";
}


</script>

<body>
<html:form action="attendSummaryReport" method="post">
<html:hidden property="method" styleId="method" value="submitMonthlyAttendSummaryReport"/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="courseName" styleId="courseNameId" value=""/>

<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance.monthlysummaryreport" />&gt;&gt;</span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.attendance.monthlysummaryreport" /></strong></td>
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
				<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>	
	            <td height="20" colspan="6" class="body" align="left">
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
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              
               <tr >
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col" /></div></td>
                <td height="26%" height="25" class="row-even" >
				<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="attendanceSummaryReportForm" property="academicYear"/>"/>
                 <html:select property="academicYear" styleId="academicYear" styleClass="combo" onchange="getClasses(this.value)">
  	   				 <html:option value="">- Select -</html:option>
  	   					<cms:renderAcademicYear></cms:renderAcademicYear>
   			   </html:select>
                </td>
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attn.activity.att.type" /></div></td>
				<td height="26%" height="25" class="row-even" >
					<html:select property="attendanceType" styleId="attendanceTypeId" onchange="getActivity(this)" style="width: 120px;" multiple="multiple" size="2">						
						<html:optionsCollection name="attendanceTypeList" label="attendanceTypeName" value="id" />
					</html:select>
				</td>
                </tr>
              <tr >
               
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendanceentry.class" /></div></td>
                <td height="25" colspan="4" class="row-even" >
				<html:select name="attendanceSummaryReportForm" styleId="classesName" property="classesName" size="5" style="width: 200px;" multiple="multiple">
					<html:optionsCollection name="attendanceSummaryReportForm" property="classMap" label="value" value="key" />
				</html:select>
                </td>             
             
             </tr>
              
          
            	<tr>
               <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.startdate" /></div></td>
               <td width="26%" height="25" class="row-even" >
					<html:text name="attendanceSummaryReportForm" property="startDate" styleId="startDateid" size="16" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'attendanceSummaryReportForm',
								// input name
								'controlname' :'startDate'
							});
						</script>

			   </td>

               <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.enddate" />:</div></td>
               <td width="26%" height="25" class="row-even" >
					<html:text name="attendanceSummaryReportForm" property="endDate" styleId="endDateid" size="16" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'attendanceSummaryReportForm',
								// input name
								'controlname' :'endDate'
							});
						</script>

			   </td>

			</tr>
				<tr>
				<td height="25" class="row-odd">
						<div align="right">Separate second language column</div>
				</td>
				<td width="26%" height="25" class="row-even" >
					<html:radio styleId="secondLanNo" property="isSecondLanReq" value="false">No</html:radio>
					<html:radio property="isSecondLanReq" value="true" styleId="secondLanYes">Yes</html:radio>
				</td>
				<td height="25" class="row-odd">
					
				</td>
				<td width="26%" height="25" class="row-even" >
				</td>

			</tr>
			<tr>
				<td height="25" class="row-odd">
				<div align="right">Required Attendance %:</div>
				</td>
				<td width="26%" height="25" class="row-even"><html:text
					property="requiredPercentage" styleId="requiredPercentageid" 
					maxlength="16" /></td>

				<td height="25" class="row-odd">
				</td>
				<td width="26%" height="25" class="row-even"></td>
			</tr>
				<tr>
				<td height="25" class="row-odd">
						<div align="right">List below required % Aggregate</div>
				</td>
				<td width="26%" height="25" class="row-even" >
					<html:radio styleId="reqPercenAggregNo" property="reqPercenAggreg" value="false">No</html:radio>
					<html:radio property="reqPercenAggreg" value="true" styleId="reqPercenAggregYes">Yes</html:radio>
				</td>
				<td height="25" class="row-odd">
					<div align="right">List below required % Individual</div>
				</td>
				<td width="26%" height="25" class="row-even" >
					<html:radio styleId="reqPercenIndiviNo" property="reqPercenIndivi" value="false">No</html:radio>
					<html:radio property="reqPercenIndivi" value="true" styleId="reqPercenIndiviYes">Yes</html:radio>
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
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
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
        <td width="931" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>
<script type="text/javascript">
var programTypeId = document.getElementById("programTId").value;
if(programTypeId.length != 0){
	document.getElementById("programtype").value= programTypeId;
}
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("academicYear").value=year;
}
</script>