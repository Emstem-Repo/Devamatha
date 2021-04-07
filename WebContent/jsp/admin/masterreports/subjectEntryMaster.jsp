<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="Subject.xls"/>
	<display:setProperty name="export.csv.filename" value="Subject.csv"/>
		<display:column style="width:20%" property="subjectTO.name" sortable="true" title="Subject Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="subjectTO.code" sortable="true" title="Code" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="subjectTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="subjectTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="subjectTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="subjectTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:6%" property="subjectTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
		<display:column style="width:6%" property="subjectTO.totalmarks" sortable="true" title="Total Marks" class="row-even" headerClass="row-odd"/>
		<display:column style="width:6%" property="subjectTO.passingmarks" sortable="true" title="Passing Marks" class="row-even" headerClass="row-odd"/>
		<display:column style="width:6%" property="subjectTO.isSecondLanguage" sortable="true" title="Is Second Lang." class="row-even" headerClass="row-odd"/>
		<display:column style="width:6%" property="subjectTO.isOptionalSubject" sortable="true" title="Is Optional Subject" class="row-even" headerClass="row-odd"/>
</display:table>