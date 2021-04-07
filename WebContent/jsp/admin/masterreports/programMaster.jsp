<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<display:table export="true" uid="masterreportid" name="sessionScope.masterReport" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
	<display:setProperty name="export.excel.filename" value="Program.xls"/>
	<display:setProperty name="export.csv.filename" value="Program.csv"/>
			<display:column style="width:10%" property="programTO.name" sortable="true" title="Program Name" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.code" sortable="true" title="Program Code" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.CDate" sortable="true" title="Created Date" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.created" sortable="true" title="Created By" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.LDate" sortable="true" title="Last Modif. Date" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.modified" sortable="true" title="Modified By" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.active" sortable="true" title="Is Active" class="row-even" headerClass="row-odd"/>
			<display:column style="width:10%" property="programTO.programTypeTo.programTypeName" sortable="true" title="ProgramType" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.motherTongue" sortable="true" title="Is Mother Tongue" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.secondLanguage" sortable="true" title="Is Second Lang." class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.displayLanguageKnown" sortable="true" title="Is Disp. Lang. Known" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.familyBackground" sortable="true" title="Is Family Background" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.heightWeight" sortable="true" title="Is Height Weight" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.entranceDetails" sortable="true" title="Is entrance Details" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.lateralDetails" sortable="true" title="Is Lateral Details" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.displayTrainingCourse" sortable="true" title="Is Display train. Course" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.transferCourse" sortable="true" title="Is Transv. Course" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.additionalInfo" sortable="true" title="Is Additional Info" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.extraDetails" sortable="true" title="Is Extra Details" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.tcDetails" sortable="true" title="Is TC Display" class="row-even" headerClass="row-odd"/>
			<display:column style="width:5%" property="programTO.registrationNo" sortable="true" title="Is Registration No." class="row-even" headerClass="row-odd"/>
</display:table>