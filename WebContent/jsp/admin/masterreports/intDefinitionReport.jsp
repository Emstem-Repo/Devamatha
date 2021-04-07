<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="InterviewDefinition.xls"/>
	<display:setProperty name="export.csv.filename" value="InterviewDefinition.csv"/>
	<display:column style="width:13%" property="interviewProgramCourseTO.program.programTypeTo.programTypeName" sortable="true" title="Program Type Name" class="row-even" headerClass="row-odd"/>
	<display:column style="width:13%" property="interviewProgramCourseTO.program.name" sortable="true" title="Program Name" class="row-even" headerClass="row-odd"/>
	<display:column style="width:13%" property="interviewProgramCourseTO.course.name" sortable="true" title="Course Name" class="row-even" headerClass="row-odd"/>
	<display:column style="width:6%" property="interviewProgramCourseTO.academicYear" sortable="true" title="Academic Year" class="row-even" headerClass="row-odd"/>
	<display:column style="width:13%" property="interviewProgramCourseTO.name" sortable="true" title="Interview Type" class="row-even" headerClass="row-odd"/>
	<display:column style="width:6%" property="interviewProgramCourseTO.sequence" sortable="true" title="Interview Type" class="row-even" headerClass="row-odd"/>
	<display:column style="width:6%" property="interviewProgramCourseTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
	<display:column style="width:8%" property="interviewProgramCourseTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
	<display:column style="width:6%" property="interviewProgramCourseTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
	<display:column style="width:8%" property="interviewProgramCourseTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
	<display:column style="width:6%" property="interviewProgramCourseTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>