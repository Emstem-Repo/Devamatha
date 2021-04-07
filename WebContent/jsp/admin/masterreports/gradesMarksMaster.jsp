<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="GradeMarks.xls"/>
	<display:setProperty name="export.csv.filename" value="GradeMarks.csv"/>
		<display:column style="width:15%" property="gradeTO.grade" sortable="true" title="Grade" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="gradeTO.mark" sortable="true" title="Mark" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="gradeTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="gradeTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="gradeTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="gradeTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="gradeTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>