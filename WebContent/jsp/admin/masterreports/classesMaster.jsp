<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="Class.xls"/>
	<display:setProperty name="export.csv.filename" value="Class.csv"/>
		<display:column style="width:15%" property="classesTO.className" sortable="true" title="Class Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="classesTO.sectionName" sortable="true" title="Section Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="classesTO.courseTo.name" sortable="true" title="Course Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="classesTO.termNo" sortable="true" title="Term No." class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="classesTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="classesTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="classesTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="classesTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="classesTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
</display:table>