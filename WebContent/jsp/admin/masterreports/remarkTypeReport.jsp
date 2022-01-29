<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="RemarkType.xls"/>
	<display:setProperty name="export.csv.filename" value="RemarkType.csv"/>
		<display:column style="width:20%" property="remarkTypeTO.remarkType" sortable="true" title="Remark Type" class="row-even" headerClass="row-odd"/>
		<display:column style="width:20%" property="remarkTypeTO.maxOccurrences" sortable="true" title="Max. Occurrences" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="remarkTypeTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="remarkTypeTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="remarkTypeTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="remarkTypeTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="remarkTypeTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>