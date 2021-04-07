<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="FeeAdditional.xls"/>
	<display:setProperty name="export.csv.filename" value="FeeAdditional.csv"/>
		<display:column style="width:15%" property="feeAdditionalTO.feeDivisionTO.name" sortable="true" title="Fee Division Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="feeAdditionalTO.feeGroupTO.name" sortable="true" title="Fee Group Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="feeAdditionalTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="feeAdditionalTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="feeAdditionalTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="feeAdditionalTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="feeAdditionalTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>