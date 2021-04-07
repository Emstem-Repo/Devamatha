<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="ReligionSection.xls"/>
	<display:setProperty name="export.csv.filename" value="ReligionSection.csv"/>
		<display:column style="width:20%" property="religionSectionTO.name" sortable="true" title="Religion Section" class="row-even" headerClass="row-odd"/>
		<display:column style="width:20%" property="religionSectionTO.religionTO.religionName" sortable="true" title="Religion Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="religionSectionTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="religionSectionTO.created" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="religionSectionTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="religionSectionTO.modified" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="religionSectionTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>