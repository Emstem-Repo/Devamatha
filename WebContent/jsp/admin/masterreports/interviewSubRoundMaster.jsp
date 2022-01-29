<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="InterviewSubrounds.xls"/>
	<display:setProperty name="export.csv.filename" value="InterviewSubrounds.csv"/>		
		<display:column style="width:15%" property="interviewSubroundsTO.name" sortable="true" title="SubRound Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="interviewSubroundsTO.interviewProgramCourseTO.name" sortable="true" title="Interview Type" class="row-even" headerClass="row-odd"/>
		<display:column style="width:8%" property="interviewSubroundsTO.interviewProgramCourseTO.academicYear" sortable="true" title="Academic Year" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="interviewSubroundsTO.interviewProgramCourseTO.course.name" sortable="true" title="Course" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="interviewSubroundsTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="interviewSubroundsTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="interviewSubroundsTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="interviewSubroundsTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:8%" property="interviewSubroundsTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>								
</display:table>