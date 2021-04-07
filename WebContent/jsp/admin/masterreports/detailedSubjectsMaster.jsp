<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="DetailedSubject.xls"/>
	<display:setProperty name="export.csv.filename" value="DetailedSubject.csv"/>
		<display:column style="width:15%" property="detailedSubjectsTO.courseTo.name" sortable="true" title="Course" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="detailedSubjectsTO.subjectName" sortable="true" title="Subject" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="detailedSubjectsTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="detailedSubjectsTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="detailedSubjectsTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="detailedSubjectsTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="detailedSubjectsTO.active" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>