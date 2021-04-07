<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="Currency.xls"/>
	<display:setProperty name="export.csv.filename" value="Currency.csv"/>
		<display:column style="width:15%" property="currencyMasterTO.name" sortable="true" title="Currency" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="currencyMasterTO.currencySubdivision" sortable="true" title="Currency Sub Division" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="currencyMasterTO.currencyCode" sortable="true" title="Currency Code" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="currencyMasterTO.symbol" sortable="true" title="Symbol" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="currencyMasterTO.active" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="currencyMasterTO.createdBy" sortable="true" title="CreatedBy" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="currencyMasterTO.CDate" sortable="true" title="CreatedDate" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="currencyMasterTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="currencyMasterTO.LDate" sortable="true" title="Modified Date" class="row-even" headerClass="row-odd"/>
</display:table>