<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="ActivityReport.xls"/>
	<display:setProperty name="export.csv.filename" value="ActivityReport.csv"/>
		<display:column style="width:15%" property="activityTO.name" sortable="true" title="Activity" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="activityTO.attendanceTypeTO.attendanceTypeName" sortable="true" title="Attendance Type" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="activityTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="activityTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="activityTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="activityTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="activityTO.activityIsActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>
