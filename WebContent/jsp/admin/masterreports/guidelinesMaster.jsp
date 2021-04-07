<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="Guidelines.xls"/>
	<display:setProperty name="export.csv.filename" value="Guidelines.csv"/>
		<display:column style="width:10%" property="guidelinesEntryTO.academicYear" sortable="true" title="Academic Year" class="row-even" headerClass="row-odd"/>
		<display:column style="width:20%" property="guidelinesEntryTO.courseTO.name" sortable="true" title="Course" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="guidelinesEntryTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="guidelinesEntryTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="guidelinesEntryTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="guidelinesEntryTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="guidelinesEntryTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="guidelinesEntryTO.contentType" sortable="true" title="ContentType" class="row-even" headerClass="row-odd"/>
		<display:column style="width:20%" property="guidelinesEntryTO.fileName" sortable="true" title="File Name" class="row-even" headerClass="row-odd"/>
</display:table>