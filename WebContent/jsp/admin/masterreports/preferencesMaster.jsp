<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="Preferences.xls"/>
	<display:setProperty name="export.csv.filename" value="Preferences.csv"/>
		<display:column style="width:20%" property="preferencesTO.courseTO.name" sortable="true" title="Course Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:20%" property="preferencesTO.prefCourseTO.name" sortable="true" title="Prefered Course" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="preferencesTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="preferencesTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="preferencesTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="preferencesTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="preferencesTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>