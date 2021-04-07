<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="FeeGroup.xls"/>
	<display:setProperty name="export.csv.filename" value="FeeGroup.csv"/>
			<display:column style="width:15%" property="feeGroupTO.name" sortable="true" title="Fee Group Name" class="row-even" headerClass="row-odd"/>
			<display:column style="width:15%" property="feeGroupTO.optional" sortable="true" title="Is Optional" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="feeGroupTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
			<display:column style="width:15%" property="feeGroupTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="feeGroupTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
			<display:column style="width:15%" property="feeGroupTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="feeGroupTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>