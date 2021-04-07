<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="Period.xls"/>
	<display:setProperty name="export.csv.filename" value="Period.csv"/>
		<display:column style="width:10%" property="periodTO.classSchemewiseTO.curriculumSchemeDurationTO.tempYear" sortable="true" title="Academic Year" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="periodTO.classSchemewiseTO.classesTo.className" sortable="true" title="Class Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="periodTO.periodName" sortable="true" title="Period Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="periodTO.startTime" sortable="true" title="From Time" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="periodTO.endTime" sortable="true" title="To Time" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="periodTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="periodTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="periodTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="periodTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:5%" property="periodTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>