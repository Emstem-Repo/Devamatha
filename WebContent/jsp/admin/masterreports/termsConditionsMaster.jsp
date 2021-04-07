<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="Terms&Conditions.xls"/>
	<display:setProperty name="export.csv.filename" value="Terms&Conditions.csv"/>
		<display:column style="width:15%" property="termsConditionTO.courseTo.name" sortable="true" title="Course Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="termsConditionTO.academicYear" sortable="true" title="Year" class="row-even" headerClass="row-odd"/>
		<display:column style="width:25%" property="termsConditionTO.description" sortable="true" title="Description" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="termsConditionTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="termsConditionTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="termsConditionTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="termsConditionTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="termsConditionTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
</display:table>