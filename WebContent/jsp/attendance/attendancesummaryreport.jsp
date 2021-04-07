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
	document.getElementById("classesName").value="";
	getClassesByYearNew("classMap", year, "classesName", updateClasses);
}
function updateClasses(req) {
	updateOptionsFromMapMultiselect(req, "classesName", " - Select -");
	var destination5 = document.getElementById("subjects");
	for (x1=destination5.options.length-1; x1>=0; x1--) {
		destination5.options[x1]=null;
	}
}
function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");
}

function getActivity(attendenceTypeId) {
	  var selectedArray = new Array();	  
	  var i;
	  var count = 0;
	  for (i=0; i<attendenceTypeId.options.length; i++) {
	    if (attendenceTypeId.options[i].selected) {
	      selectedArray[count] = attendenceTypeId.options[i].value;
	      count++;
	    }
	  }
		
	getActivityByType("activityMap",selectedArray,"activity",updateActivity);
}

function updateActivity(req) {
	updateOptionsFromMap(req,"activity","- Select -");
}

function getMultipleSubject(classId) {
	//alert("aslasd");
	var classes =  document.getElementById("classesName");
	  var selectedArray = new Array();	  
	  var i;
	  var count = 0;
	  for (i=0; i<classes.options.length; i++) {
	    if (classes.options[i].selected) {
	      selectedArray[count] = classes.options[i].value;
	      count++;
	    }
	  }
	 //alert(selectedArray);
	//getSubjectByTypeNew("subjectMaps",selectedArray,"subjects",updateSubject);
	  //getSubjectByClassesIDsTypeNew("subjectMaps",selectedArray,"subjects",updateSubject);
	  if(selectedArray != ''){
	  var args = "method=getSubjectsByMultipleClassesIds&selectedClassesArray=" + selectedArray;
	  var url = "AjaxRequest.do";
	  requestOperationProgram(url, args, updateSubject);
	  } else {
			var destinationOption = document.getElementById(destinationProperty);
			for (x1 = destinationOption.options.length - 1; x1 > 0; x1--) {
				destinationOption.options[x1] = null;
			}
		}
}

function updateSubject(req) {
	updateOptionsFromMapMultiselect(req,"subjects","- Select -");
}

function getCourses(programId) {
	getCoursesByProgram("coursesMap",programId,"course",updateCourses);
	
}

function updateCourses(req) {
	updateOptionsFromMap(req,"course","- Select -");
}
function getSemisters(year) {
	year = document.getElementById("academicYear").value;

	var courseId = document.getElementById("course").value;
	getSemistersOnYearAndCourse("semistersMap",courseId,"semister",year,updateSemisters);
        
}

function updateSemisters(req){
	updateOptionsFromMap(req,"semister","- Select -");
}
function resetCoursesChilds() {
	resetAcademicYear("academicYear");
	resetOption("semister");

}
function resetAttReport()	{
	
	document.getElementById("attendanceTypeId").selectedIndex = 0;
	document.getElementById("startDateid").value = "";
	document.getElementById("endDateid").value = "";	
	var destination5 = document.getElementById("subjects");
	
	var year = document.getElementById("academicYear").value;
	for (x1=destination5.options.length-1; x1>=0; x1--) {
		destination5.options[x1]=null;
	}
	resetFieldAndErrMsgs();	
	if(year.length != 0) {
	 	document.getElementById("academicYear").value=year;
	}
}

function getNames(){	
	document.getElementById("courseNameId").value = document.getElementById("course").options[document.getElementById("course").selectedIndex].text;	
}

function getSubjects(classSchemewiseId) {
	getSubjectsByClass("subjectMaps", classSchemewiseId, "subjects",
			updateSubjects);
}
function updateSubjects(req) {
	updateOptionsFromMap(req, "subjects", "- Select -");
}


</script>

<body>
<html:form action="attendSummaryReport">
<html:hidden property="method" styleId="method" value="submitAttendSummaryReport"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="courseName" styleId="courseNameId" value=""/>
<html:hidden property="formName" value="attendanceSummaryReportForm" />
<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.attendance.summaryreport" />&gt;&gt;</span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.attendance.summaryreport" /></strong></td>
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
	            <td colspan="6" class="body" align="left">
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
                 <html:select property="academicYear" styleId="academicYear" styleClass="combo" onchange="getClasses(this.value)">
  	   				 <html:option value="">- Select -</html:option>
  	   					<cms:renderAcademicYear></cms:renderAcademicYear>
   			   </html:select>
   			   <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="attendanceSummaryReportForm" property="academicYear"/>"/>
                </td>
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attn.activity.att.type" /></div></td>
				<td height="26%" height="25" class="row-even" >
					<html:select property="attendanceType" styleId="attendanceTypeId" onchange="getActivity(this)" style="width: 200px;" multiple="multiple" size="2">						
						<html:optionsCollection name="attendanceTypeList" label="attendanceTypeName" value="id" />
					</html:select>
				</td>
                </tr>
                 <tr >
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendanceentry.class" /></div></td>
                <td height="26%" height="25" class="row-even" >
				<html:select name="attendanceSummaryReportForm" styleId="classesName" property="classesName" size="5" style="width: 200px;" multiple="multiple" onchange="getMultipleSubject(this.value)">
					<html:optionsCollection name="attendanceSummaryReportForm" property="classMap" label="value" value="key" />
				</html:select>
                </td>
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendanceentry.subject"/>:</div></td>
				<td height="26%" height="25" class="row-even" >
                    <html:select property="subjects" styleId="subjects" size="5" style="width: 320px;" multiple="multiple">
						<c:if test="${subjectMaps != null}">
                		  <html:optionsCollection name="subjectMaps" label="value" value="key"/>
						</c:if>
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
						<div align="right"><bean:message key="knowledgepro.attendanceentry.leavestatus" /></div>
				</td>
				<td width="26%" height="25" class="row-even" >
					<html:radio styleId="leavewithout" property="leaveType" value="false"><bean:message key="knowledgepro.attendanceentry.leavewithout" /></html:radio>
					<html:radio property="leaveType" value="true" styleId="leavewith"><bean:message key="knowledgepro.attendanceentry.leavewith" /></html:radio>
				</td>
				<td height="25" class="row-odd">
					<div align="right">Co-curricular leave</div>
				</td>
				<td width="26%" height="25" class="row-even" >
					<html:radio styleId="withoutCurricular" property="coCurricularLeave" value="false">Without Co-curricular Leave</html:radio>
					<html:radio property="coCurricularLeave" value="true" styleId="withCurricular">With Co-curricular Leave</html:radio>
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
				<td width="26%" height="25" class="row-even"></td>

			</tr>
			<tr>
				<td height="25" class="row-odd">
				<div align="right">Required Attendance %:</div>
				</td>
				<td width="26%" height="25" class="row-even"><html:text
					property="requiredPercentage" styleId="requiredPercentageid" 
					maxlength="16" onkeypress="return isNumberKey(event)" value="85"/></td>
				<td height="25" class="row-odd">
					<div align="right">Consider Leave till Required %</div>
				</td>
				<td width="26%" height="25" class="row-even" >
					<html:radio styleId="considerLeaveNo" property="considerLeave" value="false">No</html:radio>
					<html:radio property="considerLeave" value="true" styleId="considerLeaveYes">Yes</html:radio>
				</td>

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

								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.registernofrom"/>:</div>
									</td>
									<td width="26%" height="25" class="row-even"><html:text
										property="startRegisterNo" styleId="startRegisterid" 
										maxlength="16" /></td>

									<td height="25" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.registernoto"/>:</div>
									</td>
									<td width="26%" height="25" class="row-even"><html:text
										property="endRegisterNo" styleId="endRegisterid" 
										maxlength="16" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.rollnofrom"/>:</div>
									</td>
									<td width="26%" height="25" class="row-even"><html:text
										property="startRollNo" styleId="startRollid" 
										maxlength="16" /></td>

									<td height="25" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.rollnoto"/>:</div>
									</td>
									<td width="26%" height="25" class="row-even"><html:text
										property="endRollNo" styleId="endRollid" 
										maxlength="16" /></td>
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
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("academicYear").value=year;
}
</script>