<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="SubjectGroup.xls"/>
	<display:setProperty name="export.csv.filename" value="SubjectGroup.csv"/>
			<display:column style="width:20%"property="subjectGroupTO.name" sortable="true" title="SubjectGroup Name" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" sortable="true" title="Subjects" class="row-even" headerClass="row-odd">				
			<c:forEach var="subjectGroupSubjectsTO1" items="${masterreportid.subjectGroupTO.subjectGroupSubjectsTOList}" varStatus="index">  <c:out value="${subjectGroupSubjectsTO1.subjectTo.name}"/> </c:forEach> </display:column>
			<display:column style="width:20%" property="subjectGroupTO.courseTO.name" sortable="true" title="Course Name" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="subjectGroupTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="subjectGroupTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="subjectGroupTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="subjectGroupTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="subjectGroupTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>