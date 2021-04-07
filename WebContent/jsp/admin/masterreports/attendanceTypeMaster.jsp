<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="AttendanceType.xls"/>
	<display:setProperty name="export.csv.filename" value="AttendanceType.csv"/>
		<display:column style="width:20%" property="attendanceTypeTO.attendanceTypeName" sortable="true" title="AttendanceType" class="row-even" headerClass="row-odd"/>
		<display:column style="width:20%" property="attendanceTypeTO.defaultValue" sortable="true" title="Default Value" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="attendanceTypeTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="attendanceTypeTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="attendanceTypeTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="attendanceTypeTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="attendanceTypeTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>