<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="EligibilityCriteria.xls"/>
	<display:setProperty name="export.csv.filename" value="EligibilityCriteria.csv"/>
		<display:column style="width:10%" property="eligibilityCriteriaTO.eligibleYear" sortable="true" title="Academic Year" class="row-even" headerClass="row-odd"/>
		<display:column style="width:15%" property="eligibilityCriteriaTO.courseTO.name" sortable="true" title="Course" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" sortable="true" title="Detailed Subjects" class="row-even" headerClass="row-odd">				
		<c:forEach var="eligibleSubjectsTO1" items="${masterreportid.eligibilityCriteriaTO.eligibleSubjectsTOList}" varStatus="index">  <c:out value="${eligibleSubjectsTO1.detailedSubjectsTO.subjectName}"/> </c:forEach> </display:column>
		<display:column style="width:10%" property="eligibilityCriteriaTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="eligibilityCriteriaTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="eligibilityCriteriaTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="eligibilityCriteriaTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="eligibilityCriteriaTO.totalPercentage" sortable="true" title="Total Percentage" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="eligibilityCriteriaTO.percentageWithoutLanguage" sortable="true" title="Percentage Without Language" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="eligibilityCriteriaTO.active" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
</display:table>