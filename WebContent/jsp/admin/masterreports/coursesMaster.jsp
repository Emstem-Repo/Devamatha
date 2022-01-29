<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="Course.xls"/>
	<display:setProperty name="export.csv.filename" value="Course.csv"/>
		<display:column style="width:10%" property="courseTO.name" sortable="true" title="Course" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="courseTO.code" sortable="true" title="Course Code" class="row-even" headerClass="row-odd"/>
		<display:column style="width:10%" property="courseTO.programTo.name" sortable="true" title="Program Name" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="courseTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="courseTO.created" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="courseTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="courseTO.modified" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
		<display:column style="width:5%" property="courseTO.active" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
		<display:column style="width:5%" property="courseTO.autonomous" sortable="true" title="Autonomous" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="courseTO.maxIntake" sortable="true" title="Max Intake" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="courseTO.payCode" sortable="true" title="Payment Code" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="courseTO.isWorkExperienceRequired" sortable="true" title="Is Work Exp. Required" class="row-even" headerClass="row-odd"/>
		<display:column style="width:7%" property="courseTO.amount" sortable="true" title="Amount" class="row-even" headerClass="row-odd"/>
</display:table>