<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="Module.xls"/>
	<display:setProperty name="export.csv.filename" value="Module.csv"/>
		<display:column style="width:20%" property="moduleTO.name" sortable="true" title="Module Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="moduleTO.position" sortable="true" title="Position" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="moduleTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="moduleTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="moduleTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="moduleTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="moduleTO.tempIsActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>
