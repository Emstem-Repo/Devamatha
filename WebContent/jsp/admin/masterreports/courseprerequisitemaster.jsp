<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="CoursePrerequiste.xls"/>
	<display:setProperty name="export.csv.filename" value="CoursePrerequiste.csv"/>
		<display:column style="width:15%" property="coursePrerequisiteTO.courseTO.name" sortable="true" title="Course" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="coursePrerequisiteTO.prerequisiteTO.name" sortable="true" title="Prerequisite Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="coursePrerequisiteTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="coursePrerequisiteTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="coursePrerequisiteTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="coursePrerequisiteTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="coursePrerequisiteTO.weightageTO.name" sortable="true" title="Weightage Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="coursePrerequisiteTO.percentage1" sortable="true" title="Percentage" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="coursePrerequisiteTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="coursePrerequisiteTO.totalMark1" sortable="true" title="Weightage" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="coursePrerequisiteTO.totalMark2" sortable="true" title="Total Marks" class="row-even" headerClass="row-odd"/>
</display:table>