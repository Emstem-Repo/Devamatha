<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="ExtraCurricular.xls"/>
	<display:setProperty name="export.csv.filename" value="ExtraCurricular.csv"/>
		<display:column style="width:15%" property="extracurricularActivityTO.name" sortable="true" title="ExtracurricularActivity" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="extracurricularActivityTO.organizationTO.organizationName" sortable="true" title="Organization Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="extracurricularActivityTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="extracurricularActivityTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="extracurricularActivityTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="extracurricularActivityTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="extracurricularActivityTO.tempActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>