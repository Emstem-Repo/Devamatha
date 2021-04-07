<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");
	var deanery = document.getElementById("deaneryName").value;
	var programTypeId = document.getElementById("programtype").value;
	getClassesByProgramTypeAndAcademicYear("classMap",programTypeId,"class",updateClasses, deanery);
}

function updateClasses(req) {
	updateOptionsFromMapMultiselect(req, "class", "- Select -");
}

function getCourse(programId) {
	getCoursesByProgram("courseMap",programId,"course",updateCourse);
}

function updateCourse(req) {
	updateOptionsFromMap(req,"course","- Select -");
	var deanery = document.getElementById("deaneryName").value;
	var programId = document.getElementById("program").value;
	getClassesByProgramAndAcademicYear("classMap",programId,"class",updateClasses,deanery);
}

function getClass(courseId) {
	var deanery = document.getElementById("deaneryName").value;
	getClassesByCourseAndAcademicYear("classMap",courseId,"class",updateClasses,deanery);
}

function getClassAccSemester(semester) {
	var deanery = document.getElementById("deaneryName").value;
	var progType = document.getElementById("programtype").value;
	var prog = document.getElementById("program").value;
	var course = document.getElementById("course").value;
	getClassesBySemesterAndAcademicYear("classMap",semester,"class",updateClasses,deanery,progType,prog,course);
}

function resetAll() {
	document.location.href = "studentDetailsReport.do?method=initStudentDetailsReport";
}

$(document).ready(function() {
	  $('#Submit').click(function(){
	       var deanery = $('#deaneryName').val();
	       var progType = $('#programtype').val();
	       var prog = $('#program').val();
	       var course = $('#course').val();
	       var semester = $('#semester').val();
	       var isCurrentYear = $('#isCurrentYear').val();
	       var previousYears = $('#previousYears').val();

	       if(deanery=='' && progType=='' && prog=='' && course=='' && semester==''){
	    	   $('#errorMessage').slideDown().html("<span style='color:red; font-size: 8pt'>Any one condition is required to continue.</span>");
	          return false;
	       }
	       else{
	       		document.studentDetailsReportForm.submit();
	       }
	      });
	});

function showOldYearsDropDown(){
	document.getElementById("previousYears").style.display = "block";
	document.getElementById("previousYearsDropDown").style.display = "block";
	document.getElementById("showClasses").style.display = "none";
	document.getElementById("showClassesMultiSelect").style.display = "none";
	document.getElementById("hideClass").style.display = "none";
	document.getElementById("hideClassDropDown").style.display = "none";
}

function dontShowOldYearsDropDown(){
	document.getElementById("previousYears").style.display = "none";
	document.getElementById("previousYearsDropDown").style.display = "none";
	document.getElementById("showClasses").style.display = "block";
	document.getElementById("showClassesMultiSelect").style.display = "block";
	document.getElementById("hideClass").style.display = "block";
	document.getElementById("hideClassDropDown").style.display = "block";
}
</script>

<html:form action="/studentDetailsReport" method="post">
<html:hidden property="method" styleId="method" value="getSearchedStudents"/>
<html:hidden property="formName" value="studentDetailsReportForm" />
<html:hidden property="pageType" value="1"/>

<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.reports" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.reports.student.details.report" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body"><strong class="boxheader"><bean:message key="knowledgepro.reports.student.details.report"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
			<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
			<td valign="top" class="news">
			<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
			<FONT color="green"><html:messages id="msg" property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
			<br>
			</html:messages> </FONT></div>
			</td>
			<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
		</tr>
		<tr>
			<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
			<td valign="top" class="news">
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
						<table width="100%" cellspacing="1" cellpadding="2">
						<tr>
							<td height="25" class="row-odd" width="25%"><div align="right">Academic Year:</div></td>
							<td height="25" class="row-even" width="25%">
							<html:radio property="isCurrentYear" styleId="isCurrentYear" value="current" onclick="dontShowOldYearsDropDown()" ></html:radio>Current Year&nbsp;&nbsp;
							<html:radio property="isCurrentYear" styleId="isCurrentYear" value="previous" onclick="showOldYearsDropDown()" ></html:radio>Previous Years
							</td>
							<td height="25" class="row-odd" width="25%">
							<div id="previousYears" align="right"><span class="Mandatory">*</span>&nbsp;Previous Years:</div></td>
							<td height="25" class="row-even" width="25%">
							<div id="previousYearsDropDown" align="left">
								<html:select property="previousYears" styleId="previousYears" styleClass="combo">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									<cms:renderPreviousAcademicYear normalYear="true"></cms:renderPreviousAcademicYear>
								</html:select>
	     					</div>
	     					</td>
						</tr>
						<tr>
						<td height="25" class="row-odd" width="25%"><div align="right">Deanery:</div></td>
						
						<td height="25" class="row-even" width="25%">
							<html:select name="studentDetailsReportForm" property="deaneryName" styleId="deaneryName" styleClass="comboMediumLarge">
                 				<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
	                 			<c:forEach var="deaneryId" items="${studentDetailsReportForm.deaneryList}">
	                 				<option value="<c:out value="${deaneryId}"/>">
	                 					<c:out value="${deaneryId}"/>
	                 				</option>
	                 			</c:forEach>
	     					</html:select>
                		</td>
                		<td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                		<td height="25" class="row-even" width="25%">
                    		<html:select property="programTypeId"  styleId="programtype" styleClass="comboMediumLarge" onchange="getPrograms(this.value)">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    			<html:optionsCollection name="studentDetailsReportForm" property="programTypeList" label="programTypeName" value="programTypeId"/>
	     					</html:select> 
                		</td>
						</tr>
						<tr>
                		<td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.program"/>:</div></td>
                		<td height="25" class="row-even">
			      			<html:select property="programId" styleId="program" styleClass="comboMediumLarge" onchange="getCourse(this.value)" >
          		   			<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    	<c:if test="${studentDetailsReportForm.programTypeId != null && studentDetailsReportForm.programTypeId != ''}">
            					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
            		    	 	<c:if test="${programMap != null}">
            		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
            		    	 	</c:if>	 
            		   		</c:if>
	           				</html:select>
						</td>
						<td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.course"/>:</div></td>
						<td height="25" class="row-even">
			      			<html:select property="courseId" styleId="course" styleClass="comboMediumLarge" onchange="getClass(this.value)">
          		   			<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    	<c:if test="${studentDetailsReportForm.programId != null && studentDetailsReportForm.programId != ''}">
            					<c:set var="courseMap" value="${baseActionForm.collectionMap['courseMap']}"/>
            		    	 	<c:if test="${courseMap != null}">
            		    	 		<html:optionsCollection name="courseMap" label="value" value="key"/>
            		    	 	</c:if>	 
            		   		</c:if>
	           				</html:select>
						</td>
						</tr>
              			<tr>
              			<td height="25" class="row-odd"><div id="hideClass" align="right"><bean:message key="admissionForm.detailmark.semester.label"/>:</div></td>
						<td height="25" class="row-even">
						<div id="hideClassDropDown" align="left">
                            <html:select styleClass="comboMediumLarge" property="semester" styleId="semester" onchange="getClassAccSemester(this.value)">
								<html:option value="">- Select -</html:option>
								<html:option value="1">1</html:option>
								<html:option value="2">2</html:option>
								<html:option value="3">3</html:option>
								<html:option value="4">4</html:option>
								<html:option value="5">5</html:option>
								<html:option value="6">6</html:option>
								<html:option value="7">7</html:option>
								<html:option value="8">8</html:option>
								<html:option value="9">9</html:option>
								<html:option value="10">10</html:option>
							</html:select></div></td>
							
						<td height="50" class="row-odd" rowspan="3"><div id="showClasses" align="right"><bean:message key="knowledgepro.attendance.activityattendence.class"/>:</div></td>
              			
              			<td height="50" class="row-even" rowspan="3">
	              			<div id="showClassesMultiSelect">
	                    		<nested:select property="selectedClass" styleId="class" size="5" style="width:160px" multiple="multiple">
					    		<c:if test="${studentDetailsReportForm.classId != null && studentDetailsReportForm.classId != ''}">
	            					<c:set var="classMap" value="${baseActionForm.collectionMap['classMap']}"/>
	            		    	 	<c:if test="${classMap != null}">
	            		    	 		<html:optionsCollection name="classMap" label="value" value="key"/>
	            		    	 	</c:if>
	            		   		</c:if>
					    		</nested:select>
					    	</div>
                		</td>
						</tr>
						<tr>
						<td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.status"/>:</div></td>
                		<td height="25" class="row-even">
                            <html:select styleClass="comboMediumLarge" property="status" styleId="status">
								<html:option value="">- Select -</html:option>
								<html:option value="Cancelled">Cancelled</html:option>
								<html:option value="Hidden">Hidden</html:option>
								<html:option value="Detained">Detained</html:option>
								<html:option value="Discontinued">Discontinued</html:option>
							</html:select></td>
              			</tr>
						<tr>
						<td height="25" class="row-odd"><div align="right">Only Final Year Students:</div></td>
                		<td height="25" class="row-even">
                			<html:radio property="isFinalYrStudents" styleId="no" value="No"></html:radio>
							No &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<html:radio property="isFinalYrStudents" styleId="yes" value="Yes"></html:radio>
							Yes 
                        </td>
						</tr>	
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
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
				<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
				<td><div></div></td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" height="35">
							<div align="right">
							<html:submit property="" styleClass="formbutton" value="Search"  styleId="Submit"></html:submit>
							
							</div>
							</td>
							<td width="2%"></td>
							<td width="49%">
							<html:button property="" styleClass="formbutton" value="Reset" onclick="resetAll()"></html:button>
							</td>
														
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">

document.getElementById("previousYears").style.display = "none";
document.getElementById("previousYearsDropDown").style.display = "none";
document.getElementById("showClasses").style.display = "block";
document.getElementById("showClassesMultiSelect").style.display = "block";
document.getElementById("hideClass").style.display = "block";
document.getElementById("hideClassDropDown").style.display = "block";

</script>