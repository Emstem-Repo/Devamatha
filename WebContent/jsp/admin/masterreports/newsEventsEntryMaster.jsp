<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="NewsEvents.xls"/>
	<display:setProperty name="export.csv.filename" value="NewsEvents.csv"/>
			<display:column style="width:70%" property="newsEventsTO.name" sortable="true" title="Description" class="row-even" headerClass="row-odd"/>
			<display:column style="width:15%" property="newsEventsTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
			<display:column style="width:15%" property="newsEventsTO.createdBy" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
</display:table>