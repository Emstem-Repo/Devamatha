<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="FeeBillNumber.xls"/>
	<display:setProperty name="export.csv.filename" value="FeeBillNumber.csv"/>
		<display:column style="width:15%" property="feeBillNumberTO.billNo" sortable="true" title="Fee Bill No." class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="feeBillNumberTO.academicYear" sortable="true" title="Financial Year" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="feeBillNumberTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="feeBillNumberTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="feeBillNumberTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="feeBillNumberTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="feeBillNumberTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>