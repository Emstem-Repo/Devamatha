<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">
	function getExamName(examType) {
	var year = document.getElementById("year").value;
	getExamNameByExamTypeYearWise("examMap", examType,year, "examName", updateExamName);
	resetOption("subjectId");
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
		if(document.getElementById("examName").value != null && document.getElementById("examName").value !=""){
			getSubjectNameAndCodeForExam();
		}
	}
	function getExamNameByYear(year) {
		var examType = "";
		if(document.getElementById("regular").checked){
			examType = document.getElementById("regular").value;
		}
		if(document.getElementById("sup").checked){
			examType = document.getElementById("sup").value;
		}
		if(document.getElementById("int").checked){
			examType = document.getElementById("int").value;
		}
		getExamNameByExamTypeYearWise("examMap", examType,year, "examName", updateExamName);
		resetOption("subjectId");
	}
	function getSCodeName(sCName) {
		examId=document.getElementById("examName").value;
		getSubjectCodeName("subjectMap", sCName, "subjectId",
				updateToSubject,  parseInt(examId));
		
	}
	function updateToSubject(req) {
		var subco ="subco";
		updateOptionsFromMap(req, "subjectId", "- Select -");
	}
	function getSubjects(){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}
		else
		{
			sCName = document.getElementById("sCodeName_2").value;
		}
		getSubjectCodeName("subjectMap", sCName, "subjectId",
				updateToSubject,  null);
	}
	function getSubjectsByExamName1222(examId){
		
		document.getElementById("method").value = "getDetailsForExam";
		document.valuationScheduleForm.submit();
	}
	function getSubjectsByExamName(){
		document.getElementById("display_Details").innerHTML ="";
		var examName="";
		var academicYear="";
		academicYear=document.getElementById("year").value;
		examName=document.getElementById("examName").value;
		getExamNameListSchedule(academicYear,examName,updateExamNameList);
	}
	function updateExamNameList(req){
		var responseObj = req.responseXML.documentElement;
		var items = responseObj.getElementsByTagName("option");
		
		var htm="<table width='100%' cellspacing='1' cellpadding='2'>  <tr height='25px' class='row-odd'>";
		htm=htm+"<td>"+"Sl No"+"</td>"+"<td>"+"Subject"+"</td>"+"<td>"+"Valuator Name"+"</td>"+"<td align='center'>"+"Valuator (OR) Reviewer"+"</td>"+"<td align='center'>"+"Board Valuation Date"+"</td>"+"<td align='center'>"+"Valuation From"+"</td>"+"<td align='center'>"+"Valuation To"+"</td>"+"<td align='center'>"+"Edit"+"</td>"+"<td align='center'>"+"Delete"+"</td></tr>";

			if(items != null ){
				 var slNo = 1;
				for ( var i = 0; i < items.length; i++) {
					 var id = items[i].getElementsByTagName("id")[0].firstChild.nodeValue;
					 var subjectName = items[i].getElementsByTagName("subjectName")[0].firstChild.nodeValue;
				     var employeeName = items[i].getElementsByTagName("employeeName")[0].firstChild.nodeValue;
				     var valuator = items[i].getElementsByTagName("valuator")[0].firstChild.nodeValue;
				     var boardValuationDate = items[i].getElementsByTagName("boardValuationDate")[0].firstChild.nodeValue;
				     var valuationFrom = items[i].getElementsByTagName("valuationFrom")[0].firstChild.nodeValue;
				     var valuationTo = items[i].getElementsByTagName("valuationTo")[0].firstChild.nodeValue;
					 if(slNo%2==0){
					     htm = htm + "<tr class='row-white'> ";
					 }else{
						 htm = htm + "<tr class='row-even'> ";
					 }

				     htm=htm + "<td width='5%' height='25'>"+slNo+ "</td>"+"<td width='20%'>"+subjectName+ "</td>"+"<td width='17%'>"+employeeName+ "</td>"+"<td width='13%' align='center'>"+valuator+ "</td>"+"<td width='13%' align='center'>"+boardValuationDate+ "</td>"+"<td width='13%' align='center'>"+valuationFrom+ "</td>"+"<td width='13%' align='center'>"+valuationTo+ "</td>";

				     htm=htm + "<td width='5%' height='25'>"+ "<div align='center'>"+ "<img src='images/edit_icon.gif' onclick='editValuationSchedule("+id+")'>"+  "</td>";
				     htm=htm + "<td width='5%' height='25'>"+ "<div align='center'>"+ "<img src='images/delete_icon.gif' onclick='deleteValuationSchedule("+id+")'"+  "</td>";

					 htm=htm+"</tr>";
				     slNo++;
				}
			}
			htm = htm + "</table>";
			document.getElementById("display_Details").innerHTML = htm;
			getSubjectNameAndCodeForExam();
	}
	function getSubjectsByExamName1(examId){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}
		else
		{
			sCName = document.getElementById("sCodeName_2").value;
		}
		getSubjectCodeName("subjectMap", sCName, "subjectId",
				updateToSubject,  examId);
	}
	function cancelAction() {
		document.location.href = "valuationSchedule.do?method=initValuationSchedule";
	}
	function getSortedSubject(subName){
		if(subName != null && subName!=""){
			examID = document.getElementById("examName").value;
			
			if(document.getElementById("sCodeName_1").checked){
				sCName = document.getElementById("sCodeName_1").value;
			}
			else
			{
				sCName = document.getElementById("sCodeName_2").value;
			}
			getSortedSubjectList(examID,sCName,subName,"subjectId",updateSubjectList);
		}else{
			getSubjects();
		}
	}
	function updateSubjectList(req){
		updateSubjectFormMap(req,"subjectId", "- Select -");
	}
	function editValuationSchedule(id) {
		var examId = document.getElementById("examName").value;
		document.location.href = "valuationSchedule.do?method=editValuationSchedule&id="+ id+"&examId="+examId;
	}
	function updateValuationSchedule() {
		document.getElementById("method").value = "updateValuationSchedule";
		document.valuationScheduleForm.submit();
	}
	function getSortedValuator(valuatorName){
		getValuatorNames(valuatorName,document.getElementById("subjectId").value,updateTeachersMap);
	}
	function updateTeachersMap(req){
		updateValuatorsMap(req,"employeeId","- Select -");
	}
	function updateTeachersMapNew(req){
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById("otherEmpId");
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		var childNodes = responseObj.childNodes;
		destination.options[0] = new Option("- Select -", "");
		var items = responseObj.getElementsByTagName("option");
		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i+1] = new Option(label, value);
		}
	}
	function getSubjectNameAndCodeForExam(){
		var examId = document.getElementById("examName").value;
		var examType = "";
		if(document.getElementById("regular").checked){
			examType = document.getElementById("regular").value;
		}
		if(document.getElementById("sup").checked){
			examType = document.getElementById("sup").value;
		}
		if(document.getElementById("int").checked){
			examType = document.getElementById("int").value;
		}
		var subCode="";
		if(document.getElementById("sCodeName_1").checked){
			subCode = document.getElementById("sCodeName_1").value;
		}else{
			subCode = document.getElementById("sCodeName_2").value;
		}
		var args = "method=getSubjectListForScheduleExam&examName=" +examId+"&examType="+examType+"&subjectType="+subCode;
		var url = "AjaxRequest.do";
		requestOperationProgram(url, args, updateSubjectMap);
	}
	function updateSubjectMap(req){
		updateValuatorsMap(req,"subjectId","- Select -");
	}
	function getValuatorsList(subjectId){
		args =  "method=getValuatorsScheduleList&subjectId="+subjectId;
		var url = "AjaxRequest.do";
		requestOperationProgram(url, args, updateTeachersMap);
	}
	function deleteValuationSchedule(id){
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if(deleteConfirm){
			document.location.href = "valuationSchedule.do?method=deleteValuationSchedule&id="+ id;
		}
	}
	function getExamDateBySubject(){
		<%--	document.getElementById("display_Date").innerHTML ="";--%>
		var examName="";
		var subject="";
		examName=document.getElementById("examName").value;
		subName=document.getElementById("subjectId").value;

		var args ="method=getExamDateBySubject&examId="+examName+"&subjectId="+subName;
	  	var url ="AjaxRequest.do";
	  	requestOperation(url,args,updateDate);	
	}
	function updateDate(req) {
	     var pos;
		 var responseObj = req.responseText;
		 var date =responseObj.substring(0,pos);
			document.getElementById("display_Date").innerHTML =date;
	}
	function reActivate(){
		var id = document.getElementById("id").value;
		document.location.href = "valuationSchedule.do?method=reActivateValuationSchedule&name="+ id;
	}
	function allValuators(){
		args =  "method=getValuatorsAllList";
		var url = "AjaxRequest.do";
		requestOperationProgram(url, args, updateTeachersMap);
	}
	function getExternalAllScheduleList(){
		args =  "method=getExternalValuatorsScheduleAllList";
		var url = "AjaxRequest.do";
		requestOperationProgram(url, args, updateExternalMap);
	}
	function setDateToValuationForm(valu){
		if(document.getElementById("valuationFrom").value==null || document.getElementById("valuationFrom").value=='')
		  document.getElementById("valuationFrom").value =valu;
	}
	function ExternalBySubject(subjectId){
	  args =  "method=getExternalValuatorsScheduleList&subjectId="+subjectId;
	  var url = "AjaxRequest.do";
	  requestOperationProgram(url, args, updateExternalMap);
	}
	function updateExternalMap(req){
		updateValuatorsMap(req,"externalEmployeeId","- Select -");
	}
</script>

<html:form action="/valuationSchedule">
<html:hidden property="formName" value="valuationScheduleForm" styleId="formName" />
<html:hidden property="method" styleId="method" value="saveDetails" />
<html:hidden property="pageType" value="1" styleId="pageType" />

	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Valuation Schedule Details Entry &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Valuation Schedule Details</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>

							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">

								<tr>
									<td height="25" colspan="4" class="row-even">
									<div align="Center">
									<html:radio property="examType"
										styleId="regular" value="Regular"
										onclick="getExamName(this.value)"></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamName(this.value)"></html:radio>
									Supplementary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Internal" styleId="int"
										onclick="getExamName(this.value)"></html:radio>
									Internal</div>
									</td>
								</tr>
								<tr>
									<td class="row-odd" width="25%" height="25"><div align="right">
									<span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
		                            <td class="row-even" align="left" width="25%" height="25">
		                            <html:select property="academicYear" styleId="year" styleClass="combo" onchange="getExamNameByYear(this.value)">
	                     	   					 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                     	   					 <cms:renderAcademicYear></cms:renderAcademicYear>
	                     			   		</html:select>
		                            </td>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									Exam Name:</div>
									</td>
									<td width="25%" height="25" class="row-even">
									<input type="hidden" id="exam" value='<bean:write name="valuationScheduleForm" property="examId"/>' />
									<c:choose>
								<c:when test="${editType != null && editType == 'edit'}">
									<html:select
										name="valuationScheduleForm" property="examId"
										styleId="examName" styleClass="comboLarge" onchange="getSubjectsByExamName()" disabled="true">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
											<logic:notEmpty name="valuationScheduleForm"
												property="examNameList">
												<html:optionsCollection property="examNameList"
													name="valuationScheduleForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select>
								</c:when>
								<c:otherwise>
									<html:select
										name="valuationScheduleForm" property="examId"
										styleId="examName" styleClass="comboLarge" onchange="getSubjectsByExamName()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
											<logic:notEmpty name="valuationScheduleForm"
												property="examNameList">
												<html:optionsCollection property="examNameList"
													name="valuationScheduleForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select>
								</c:otherwise>
							</c:choose>
									</td>
								</tr>
								<tr>
									<td height="25" colspan="4" class="row-even">
									<div align="center">
									<html:radio property="displaySubType" styleId="sCodeName_2" value="sName" onclick="getSubjectNameAndCodeForExam()">Subject Name</html:radio>
									 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 <html:radio property="displaySubType" styleId="sCodeName_1" value="sCode" onclick="getSubjectNameAndCodeForExam()">Subject Code</html:radio>
									</div>
									</td>
								</tr>
								
								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject:</div>
									</td>
									<td width="25%" height="12" class="row-even">
									<input type="hidden" id="subjectid" value='<bean:write name="valuationScheduleForm" property="subjectId"/>' />
									<c:choose>
								         <c:when test="${editType != null && editType == 'edit'}">
								        <html:select
										property="subjectId" styleClass="comboLarge" styleId="subjectId" disabled="true"
										name="valuationScheduleForm" style="width:300px" onchange="getExamDateBySubject(),getValuatorsList(this.value),ExternalBySubject(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="valuationScheduleForm"
											property="subjectMap">
											<html:optionsCollection property="subjectMap"
												name="valuationScheduleForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
								      </c:when>
								  <c:otherwise>
								  <html:select
										property="subjectId" styleClass="comboLarge" styleId="subjectId"
										name="valuationScheduleForm" style="width:300px" onchange="getExamDateBySubject(),getValuatorsList(this.value),ExternalBySubject(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="valuationScheduleForm"
											property="subjectMap">
											<html:optionsCollection property="subjectMap"
												name="valuationScheduleForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
								   </c:otherwise>
						    	    </c:choose>
									</td>
									<td width="25%" height="25" class="row-odd">
									<div align="right">Date of Exam:</div>
									</td>
									<td width="25%" class="row-even">
									
									<c:choose>
								<c:when test="${display_Date != null}">
									<bean:write name="valuationScheduleForm" property="examDate"/>
								</c:when>
								<c:otherwise>
									<div id="display_Date" align="left"></div>
								</c:otherwise>
							</c:choose>
									
									</td>
								</tr>
								<tr>
								
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Valuator Name:</div>
									</td>
									<td width="25%" class="row-even">
									<input type="hidden" id="employeeid" value='<bean:write name="valuationScheduleForm" property="employeeId"/>' />
									<c:choose>
								         <c:when test="${editType != null && editType == 'edit'}">
								           <html:select property="selectedEmployeeId" styleId="employeeId" styleClass="body" multiple="multiple" size="6" style="width:300px" disabled="true">
										   <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										   <c:if test="${evaluatorsMap != null}">
										   <html:optionsCollection name="evaluatorsMap" label="value" value="key" />
										  </c:if>
								         </html:select>
								      </c:when>
								  <c:otherwise>
								   <html:select property="selectedEmployeeId" styleId="employeeId" styleClass="body" multiple="multiple" size="6" style="width:300px">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<c:if test="${evaluatorsMap != null}">
										<html:optionsCollection name="evaluatorsMap" label="value" value="key" />
										</c:if>
								   </html:select>
								   </c:otherwise>
						    	 </c:choose>
										&nbsp;&nbsp;<a href="javascript:void(0)" onclick="allValuators()">Click here for all the Valuators</a>
									</td>
									
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>External Name:</div>
									</td>
									<td width="25%" class="row-even">
									<input type="hidden" id="externalid" value='<bean:write name="valuationScheduleForm" property="externalEmployeeId"/>' />
									<c:choose>
								       <c:when test="${editType != null && editType == 'edit'}">
								          <html:select property="selectedExternalId" styleId="externalEmployeeId" styleClass="body" multiple="multiple" size="6" style="width:300px" disabled="true">
										   <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										   <c:if test="${externalEvaluatorsMap != null}">
										  <html:optionsCollection name="externalEvaluatorsMap" label="value" value="key" />
										 </c:if>
										</html:select>
								      </c:when>
								  <c:otherwise>
								  	<html:select property="selectedExternalId" styleId="externalEmployeeId" styleClass="body" multiple="multiple" size="6" style="width:300px" >
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<c:if test="${externalEvaluatorsMap != null}">
										<html:optionsCollection name="externalEvaluatorsMap" label="value" value="key" />
										</c:if>
									</html:select>
								   </c:otherwise>
						    	 </c:choose>
										&nbsp;&nbsp;<a href="javascript:void(0)" onclick="getExternalAllScheduleList()">Click here for all the Externals</a>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-even" colspan="4">
										<div align="center">
											<html:radio property="isReviewer" value="Valuator"></html:radio>&nbsp;Valuator &nbsp;&nbsp;&nbsp;&nbsp;
											<html:radio property="isReviewer" value="Reviewer"></html:radio>&nbsp;Reviewer
									 	</div>	
									</td>
								</tr>
								<tr>
								<td width="25%" height="25" class="row-odd" ><div align="right">Board Valuation Date:</div></td>
								<td width="25%" height="25" class="row-even" align="left">
		                             <html:text property="date" styleId="date" size="11" maxlength="11" onchange="setDateToValuationForm(this.value)"></html:text>
											<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'valuationScheduleForm',
													// input name
													'controlname' :'date'
												});
											</script>
		                             </td>
		                             <td width="25%" height="25" class="row-odd">
									</td>
									<td width="25%" class="row-even"></td>
								
								</tr>
								<tr>
								<td width="25%" height="25" class="row-odd" ><div align="right">Valuation From:</div></td>
								<td width="25%" height="25" class="row-even" align="left">
		                             <html:text property="valuationFrom" styleId="valuationFrom" size="11" maxlength="11"></html:text>
											<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'valuationScheduleForm',
													// input name
													'controlname' :'valuationFrom'
												});
											</script>
		                             </td>
								<td width="25%" class="row-odd" ><div align="right">Valuation To:</div></td>
								<td width="25%" class="row-even" align="left">
		                             <html:text property="valuationTo" styleId="valuationTo" size="11" maxlength="11"></html:text>
											<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'valuationScheduleForm',
													// input name
													'controlname' :'valuationTo'
												});
											</script>
		                             </td>
								</tr>
								
								</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
						</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>

				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
							<td width="20%" height="35" align="center">
							<c:choose>
								<c:when test="${editType != null && editType == 'edit'}">
									<html:submit value="Update" styleClass="formbutton" onclick="updateValuationSchedule()"></html:submit>
										&nbsp;&nbsp;
										<input type="button" class="formbutton" value="Cancel"
											onclick="cancelAction()" />
								</c:when>
								<c:otherwise>
									<html:submit value="Submit" styleClass="formbutton"></html:submit>
										&nbsp;&nbsp;
										<input type="button" class="formbutton" value="Reset" onclick="cancelAction()" />
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
						<tr>
				            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				              <tr>
				                <td ><img src="images/01.gif" width="5" height="5" /></td>
				                <td width="914" background="images/02.gif"></td>
				                <td><img src="images/03.gif" width="5" height="5" /></td>
				              </tr>
				              <tr>
				                <td width="5"  background="images/left.gif"></td>
				                <td valign="top">
				                <div id="display_Details">
				                <table width="100%" cellspacing="1" cellpadding="2">
				                  <tr >
				                    <td height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.slno"/></div></td>
				                    <td height="25" class="row-odd" align="left">Subject</td>
				                    <td height="25" class="row-odd" align="left">Valuator Name</td>
				                    <td height="25" class="row-odd" align="center">Valuator (OR) Reviewer</td>
				                    <td class="row-odd"><div align="center">Board Valuation Date</div></td>
				                    <td class="row-odd"><div align="center">Valuation From</div></td>
				                    <td class="row-odd"><div align="center">Valuation To</div></td>
				                    <td class="row-odd"><div align="center">Edit</div></td>
				                    <td class="row-odd"><div align="center">Delete</div></td>
				                  </tr>
				                <c:set var="temp" value="0"/>
				                <logic:notEmpty name="valuationScheduleForm" property="valuationDetails">
				                <logic:iterate id="to" name="valuationScheduleForm" property="valuationDetails" indexId="count">
				               
				                   	<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
									</c:choose>
				                   		<td width="5%" height="25" class="row-even" ><div align="left"><c:out value="${count + 1}"/></div></td>
				                   		<td width="20%" height="25" class="row-even" align="left"><bean:write name="to" property="subjectName"/></td>
				                   		<td width="17%" height="25" class="row-even" align="left"><bean:write name="to" property="employeeName"/></td>
				                   		<td width="13%" height="25" class="row-even" align="center"><bean:write name="to" property="valuator"/></td>
				                   		<td width="13%" height="25" class="row-even" align="center"><bean:write name="to" property="boardValuationDate"/></td>
				                   		<td width="13%" height="25" class="row-even" align="center"><bean:write name="to" property="valuationFrom"/></td>
				                   		<td width="13%" height="25" class="row-even" align="center"><bean:write name="to" property="valuationTo"/></td>
				                   		<td width="5%" height="25" class="row-even" ><div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editValuationSchedule('<nested:write name="to" property="id" />')" /></div></td>
										<td width="5%" height="25" class="row-even">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteValuationSchedule('<bean:write name="to" property="id"/>')"></div>
											</td>
				                </logic:iterate>
				                
				                </logic:notEmpty>
				                </table></div></td>
				                <td width="5" height="30"  background="images/right.gif"></td>
				              </tr>
				              <tr>
				                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
				                <td background="images/05.gif"></td>
				                <td><img src="images/06.gif" /></td>
				              </tr>
				            </table></td>
			          </tr>
						</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>

				</tr>
			</table>
			</td>
		</tr>
	</table>
<script type="text/javascript">
	function displayOtherField(displayOther){
		alert(displayOther);
		if(displayOther == null || displayOther==""){
			displayOther = document.getElementById("otherEmpId").value;
		}
		document.getElementById("otherEmpId").value=displayOther;
		if(displayOther != null && displayOther == "Other"){
			document.getElementById("displayOtherId").style.display = "block";
		}else{
			document.getElementById("displayOtherId").style.display = "none";
		}
	}

	document.getElementById("displayOtherId").style.display = "none";
	document.getElementById("displayOtherEmployee").style.display = "none";
	
	function otherValuatorsMap(displayOther){
		alert(displayOther);
		if(displayOther == null || displayOther==""){
			displayOther = document.getElementById("employeeId").value;
		}
		document.getElementById("employeeId").value=displayOther;
		if(displayOther != null && displayOther == "Other"){
			document.getElementById("displayOtherEmployee").style.display = "block";
			args =  "method=getExternalValuatorsList&subjectId="+document.getElementById("subjectId").value;
			var url = "AjaxRequest.do";
			requestOperationProgram(url, args, updateTeachersMapNew);
		}else{
			document.getElementById("displayOtherEmployee").style.display = "none";
		}
		document.getElementById("otherEmpId").value="";
		document.getElementById("displayOtherId").style.display = "none";
	}
	
</script>	
<script type="text/javascript">
	var examId = document.getElementById("exam").value;
	if (examId != null && examId.length != 0) {
		document.getElementById("examName").value = examId;
	}
	var subjectId = document.getElementById("subjectid").value;
	if (subjectId != null && subjectId.length != 0) {
		document.getElementById("subjectId").value = subjectId;
	}
	var employeeId = document.getElementById("employeeid").value;
	if (employeeId != null && employeeId.length != 0) {
		document.getElementById("employeeId").value = employeeId;
	}
	var otherEmployeeId = document.getElementById("externalid").value;
	if (otherEmployeeId != null && otherEmployeeId.length != 0) {
		document.getElementById("externalEmployeeId").value = otherEmployeeId;
	}
	
	
</script>
</html:form>