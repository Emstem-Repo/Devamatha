<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="State.xls"/>
	<display:setProperty name="export.csv.filename" value="State.csv"/>
		<display:column style="width:20%" property="stateTO.name" sortable="true" title="State Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:20%" property="stateTO.countryTo.name" sortable="true" title="Country Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="stateTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="stateTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="stateTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="stateTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="stateTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>