<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="DocumentType.xls"/>
	<display:setProperty name="export.csv.filename" value="DocumentType.csv"/>
		<display:column style="width:15%" property="docTypeTO.name" sortable="true" title="Document Type" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="docTypeTO.docShortName" sortable="true" title="Document Short Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="docTypeTO.printName" sortable="true" title="Print Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="docTypeTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="docTypeTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="docTypeTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="docTypeTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="docTypeTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>