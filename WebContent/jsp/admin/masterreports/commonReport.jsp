<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" id="report" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10">
	<display:setProperty name="export.excel.filename" value="Master.xls"/>
	<display:setProperty name="export.csv.filename" value="Master.csv"/>
<% String fieldName=""; %>	
<logic:iterate id="index" name="masterReport" type="com.kp.cms.to.attendance.MasterReportTO">
	<% fieldName = index.getFieldName(); %>
</logic:iterate>	
<c:choose>
	<c:when test="${temp == 0}">
			<display:column style=" padding-right: 100px;" property="name" sortable="true" title="<%=fieldName %>" class="row-even" headerClass="row-odd" />
			<display:column style=" padding-right: 100px;" property="isActive" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-right: 100px;" property="CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-right: 100px;" property="createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-right: 100px;" property="LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-right: 100px;" property="modifiedBy" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
<c:set var="temp" value="1" />
	</c:when>
	<c:otherwise>
			<display:column style=" padding-right: 100px;" property="name" sortable="true" title="<%=fieldName %>" class="row-white" headerClass="row-odd"></display:column>
			<display:column style=" padding-right: 100px;" property="isActive" sortable="true" title="Is Active" class="row-white" headerClass="row-odd"/>
			<display:column style=" padding-right: 100px;" property="CDate" sortable="true" title="Created Date" class="row-white" headerClass="row-odd"/>
			<display:column style=" padding-right: 100px;" property="createdBy" sortable="true" title="Created By" class="row-white" headerClass="row-odd"/>
			<display:column style=" padding-right: 100px;" property="LDate" sortable="true" title="Last Modif. Date" class="row-white" headerClass="row-odd"/>
			<display:column style=" padding-right: 100px;" property="modifiedBy" sortable="true" title="Modified By" class="row-white" headerClass="row-odd"/>
<c:set var="temp" value="0" />
	</c:otherwise>
</c:choose>
</display:table>