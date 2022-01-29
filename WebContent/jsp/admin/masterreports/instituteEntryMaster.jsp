<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="InstituteEntry.xls"/>
	<display:setProperty name="export.csv.filename" value="InstituteEntry.csv"/>
		<display:column style="width:25%" property="collegeTO.name" sortable="true" title="Institute Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:25%" property="collegeTO.universityTO.name" sortable="true" title="University Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="collegeTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="collegeTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="collegeTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="collegeTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="collegeTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>