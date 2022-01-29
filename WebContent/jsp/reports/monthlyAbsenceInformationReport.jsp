<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
	<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script type="text/javascript">
function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");
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
	
	document.getElementById("programtype").selectedIndex = 0;
	resetOption("program");
	resetOption("course");
	resetOption("semister");	
	document.getElementById("startDateid").value = "";
	document.getElementById("endDateid").value = "";		
	resetErrMsgs();
}

function getNames(){	
	document.getElementById("courseNameId").value = document.getElementById("course").options[document.getElementById("course").selectedIndex].text;	
}

</script>

<html:form action="/absenceInformationSummary" method="post">
<html:hidden property="method" styleId="method" value="getMonthlyAbsenceInformationSummary"/>
<html:hidden property="courseName" styleId="courseNameId" value=""/>
	<html:hidden property="formName" value="absenceInformationSummaryForm" />
	<html:hidden property="pageType" value="3"/>
<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.monthlyabsence.summaryreport" />&gt;&gt;</span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.monthlyabsence.summaryreport" /></strong></td>
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
                <td width="22%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.program.type" /></div></td>
				     <td width="28%" height="25" class="row-even" >
                    <html:select property="programTypeId"  styleId="programtype" styleClass="combo" onchange="getPrograms(this.value)">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    			<html:optionsCollection name="programTypeList" label="programTypeName" value="programTypeId"/>
	     			</html:select> 
                </td>
                <td width="19%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.prog" /></div></td>
                <td width="26%" height="25" class="row-even" >
			      <html:select property="programId"  styleId="program" styleClass="combo" onchange="getCourses(this.value)">
          		   		<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    	<c:if test="${absenceInformationSummaryForm.programTypeId != null && absenceInformationSummaryForm.programTypeId != ''}">
            					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
            		    	 	<c:if test="${programMap != null}">
            		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
            		    	 	</c:if>	 
            		   		</c:if>
	           		</html:select>	   
				</td>
              </tr>
              <tr >
                <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.course.with.col" /></div></td>
                <td class="row-even" >
             <html:select property="courseId"  styleId="course" styleClass="combo" onchange="getSemisters(this.value)">
           				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
         				   		<c:if test="${absenceInformationSummaryForm.programId != null && absenceInformationSummaryForm.programId != ''}">
             						<c:set var="courseMap" value="${baseActionForm.collectionMap['coursesMap']}"/>
             		    			<c:if test="${courseMap != null}">
             		    				<html:optionsCollection name="courseMap" label="value" value="key"/>
             		    			</c:if>	 
             		   			</c:if>
       			   </html:select>
				</td>
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col" /></div></td>
                <td height="25" colspan="4" class="row-even" >
				<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="absenceInformationSummaryForm" property="academicYear"/>"/>
                 <html:select property="academicYear" styleId="academicYear" styleClass="combo" onchange="getSemisters(this.value)">
  	   				 <html:option value="">- Select -</html:option>
  	   				 <cms:renderYear></cms:renderYear>
   			   </html:select>
                </td>

              </tr>
              <tr >
                <td height="25" class="row-odd" ><div align="right"><bean:message key="curriculumSchemeForm.schemeId" />:</div></td>
                <td height="25" class="row-even" >
                 <html:select property="semister" styleId="semister" styleClass="combo">
         	   				 <html:option value="">- Select -</html:option>
           				    	<c:if test="${absenceInformationSummaryForm.academicYear != null && absenceInformationSummaryForm.academicYear != ''}">
              						<c:set var="semistersMap" value="${baseActionForm.collectionMap['semistersMap']}"/>
              		    			<c:if test="${semistersMap != null}">
              		    				<html:optionsCollection name="semistersMap" label="value" value="key"/>
              		    			</c:if>	 
              		   			</c:if>
         	 			 </html:select>
				</td>
             <td class="row-odd"></td>
									<td class="row-even"></td>
                </tr>
		
			<tr>
               <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.startdate" /></div></td>
               <td width="26%" height="25" class="row-even" >
					<html:text property="startDate" styleId="startDateid" size="16" maxlength="16"/>
							<script language="JavaScript">
	new tcal( {
		// form name
		'formname' :'absenceInformationSummaryForm',
		// input name
		'controlname' :'startDate'
	});
</script>

			   </td>

               <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.enddate" />:</div></td>
               <td width="26%" height="25" class="row-even" >
					<html:text  property="endDate" styleId="endDateid" size="16" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'absenceInformationSummaryForm',
								// input name
								'controlname' :'endDate'
							});
						</script>

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
							<div align="right"><html:submit styleClass="formbutton" onclick="getNames()">
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
