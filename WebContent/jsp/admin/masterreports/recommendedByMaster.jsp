<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="RecommendedBy.xls"/>
	<display:setProperty name="export.csv.filename" value="RecommendedBy.csv"/>
			<display:column style="width:6%" property="recommendedByTO.code" sortable="true" title="Recommended Code" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="recommendedByTO.name" sortable="true" title="Recommended Name" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="recommendedByTO.addressLine1" sortable="true" title="Address 1" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="recommendedByTO.addressLine2" sortable="true" title="Address 2" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="recommendedByTO.city" sortable="true" title="City" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="recommendedByTO.stateTO.name" sortable="true" title="State" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="recommendedByTO.countryTO.name" sortable="true" title="Country" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="recommendedByTO.phone" sortable="true" title="Phone" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="recommendedByTO.comments" sortable="true" title="Comments" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="recommendedByTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="recommendedByTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="recommendedByTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="recommendedByTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="recommendedByTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>