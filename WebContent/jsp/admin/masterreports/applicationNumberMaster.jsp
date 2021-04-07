<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="ApplicationNumber.xls"/>
	<display:setProperty name="export.csv.filename" value="ApplicationNumber.csv"/>
		<display:column style="width:10%" property="applicationNumberTO.academicYear" sortable="true" title="Academic Year" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" sortable="true" title="Course Name" class="row-even" headerClass="row-odd">				
		<c:forEach var="courseApplicationNoTO1" items="${masterreportid.applicationNumberTO.courseApplicationNoTO}" varStatus="index">  <c:out value="${courseApplicationNoTO1.courseTO.name}"/> </c:forEach> </display:column>
		<display:column style="width:10%" property="applicationNumberTO.onlineAppNoFrom" sortable="true" title="Online Application" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="applicationNumberTO.offlineAppNoFrom" sortable="true" title="Offline Application" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="applicationNumberTO.isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="applicationNumberTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="applicationNumberTO.createdDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="applicationNumberTO.modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="applicationNumberTO.lastModifiedDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
</display:table>