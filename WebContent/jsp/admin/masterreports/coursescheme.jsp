<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="CourseScheme.xls"/>
	<display:setProperty name="export.csv.filename" value="CourseScheme.csv"/>
		<display:column style="width:20%" property="courseSchemeTO.courseSchemeName" sortable="true" title="CourseScheme" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="courseSchemeTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="courseSchemeTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="courseSchemeTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="courseSchemeTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="courseSchemeTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
</display:table>