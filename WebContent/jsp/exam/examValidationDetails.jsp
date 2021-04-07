<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	
	function getExamName(examType) {
		document.getElementById("hideThisId").style.display = "none";
		document.getElementById("displayTotalPendingIssuedScripts").style.display = "none";
		document.getElementById("hideThisDisplayDetails").style.display = "none";
		document.getElementById("display_Details").innerHTML ="";
		document.getElementById("reviewer").innerHTML = "";
		document.getElementById("valuators").innerHTML = "";
		var year = document.getElementById("year").value;
		getExamNameByExamTypeYearWise("examMap", examType,year, "examName", updateExamName);
		resetOption("subject");
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
		resetOption("subject");
	}
	function getSCodeName(sCName) {
		examId=document.getElementById("examName").value;
		getSubjectCodeName("subjectMap", sCName, "subject",
				updateToSubject,  parseInt(examId));
		
	}

	function updateToSubject(req) {
		var subco ="subco";
		updateOptionsFromMap(req, "subject", "- Select -");
	}
	function getSubjects(){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}
		else
		{
			sCName = document.getElementById("sCodeName_2").value;
		}
		getSubjectCodeName("subjectMap", sCName, "subject",
				updateToSubject,  null);
	}
	function getSubjectsByExamName1222(examId){
		
		document.getElementById("method").value = "getDetailsForExam";
		document.examValidationDetailsForm.submit();
	}
	function getSubjectsByExamName1(examId){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}
		else
		{
			sCName = document.getElementById("sCodeName_2").value;
		}
		getSubjectCodeName("subjectMap", sCName, "subject",
				updateToSubject,  examId);
	}

	function cancelAction() {
		document.location.href = "examValidation.do?method=initExamValidationEntry";
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
			getSortedSubjectList(examID,sCName,subName,"subject",updateSubjectList);
		}else{
			getSubjects();
		}
	}
	function updateSubjectList(req){
		updateSubjectFormMap(req,"subject", "- Select -");
	}
	function editValidationDetails(id){
		document.location.href = "examValidation.do?method=editValidationDetails&id="+id;
	}
	function resetEditData(){
		document.location.href = "examValidation.do?method=editValidationDetails";
	}

	function updateDetails(){
		document.getElementById("method").value = "updateValidationDetails";
		document.examValidationDetailsForm.submit();
	}
	function getSortedValuator(valuatorName){
		getValuatorNames(valuatorName,document.getElementById("subject").value,updateTeachersMap);
	}
	function updateTeachersMap(req){
		updateValuatorsMapHere(req,"employeeId","- Select -");
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
		getSubjectListForExam(examId,examType,subCode,"subject",updateToSubject);
	}

	function getValuatorsList(subjectId){
		resetErrMsgs();
		//args =  "method=getValuatorsList&subjectId="+subjectId;
		var examName=document.getElementById("examName").value;
		args =  "method=getValuatorsListFromExamValuationDetails&subjectId="+subjectId+"&examid="+examName;
		var url = "AjaxRequest.do";
		requestOperationProgram(url, args, updateTeachersMap2);
		
	}
	function updateTeachersMap2(req){
		updateValuatorsMapHere(req,"employeeId","- Select -");
		getExamValidationDetailsBySubject();
	}
	function updateValuatorsMapHere(req, destinationOption, defaultSelectValue) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById(destinationOption);
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		var childNodes = responseObj.childNodes;
		destination.options[0] = new Option(defaultSelectValue, "");
		var items = responseObj.getElementsByTagName("option");
		var count =1;
		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i+1] = new Option(label, value);
			count = count +1;
		}
	    destination.options[count] = new Option("Other", "Other");
	}
	function allValuators(){
		getValuatorsList("");
	}
	function getExamValidationDetailsBySubject(){
		document.getElementById("display_Details").innerHTML ="";
		var examName="";
		var academicYear="";
		academicYear=document.getElementById("year").value;
		examName=document.getElementById("examName").value;
		var subjectId=document.getElementById("subject").value;
		args =  "method=getExamValidationDetailsByAjax&subjectId="+subjectId+"&examId="+examName+"&academicYear="+academicYear;
		var url = "examValidation.do";
		requestOperationProgram(url, args, updateExamNameList);
	}
	function updateExamNameList(req){
		var responseObj = req.responseXML.documentElement;
		var fields = responseObj.getElementsByTagName("option");
		var htm="<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'><tr>";
		htm=htm+"<td ><img src='images/01.gif' width='5' height='5' /></td><td width='914' background='images/02.gif'></td>";
		htm=htm+"<td><img src='images/03.gif' width='5' height='5' /></td></tr>";
		htm=htm+"<tr><td width='5' background='images/left.gif'></td><td valign='top'>";
		htm=htm+"<table width='100%' cellspacing='1' cellpadding='2'>  <tr height='25px' class='row-odd'>";
		htm=htm+"<td align='center'>"+"Sl No"+"</td>"+"<td align='center'>"+"Issue Date"+"</td>"+"<td align='center'>"+"Valuator Name"+"</td>"+"<td align='center'>"+"Valuator (OR) Reviewer"+"</td>"+"<td align='center'>"+"Number of Scripts"+"</td>"+"<td align='center'>"+"Edit"+"</td>"+"<td align='center'>"+"Delete"+"</td></tr>";
        var valuatorsName="";
        var reviewersName="";
    	if(fields!=null){
        	 var slNo = 1;
    		for ( var i = 0; i < fields.length; i++) {
    			if(fields[i].firstChild!=null && fields[i].firstChild!='' && fields[i].firstChild.length!=0){
    				if(fields[i]!=null){
    							 var issueDate=fields[i].getElementsByTagName("issueDate")[0].firstChild.nodeValue;
    							 var employeeName=fields[i].getElementsByTagName("employeeName")[0].firstChild.nodeValue;
    							 var answerScript=fields[i].getElementsByTagName("numberOfAnswereScript")[0].firstChild.nodeValue;
    							 var valuator=fields[i].getElementsByTagName("valuator")[0].firstChild.nodeValue;
    							 var id=fields[i].getElementsByTagName("id")[0].firstChild.nodeValue;
    							 var otherEmployee=fields[i].getElementsByTagName("otherEmployee")[0].firstChild.nodeValue;
                                  if(valuator.trim() == 'Reviewer'.trim()){
                                	  if(otherEmployee.trim() == 'InternalValuator'.trim()){
                                     	 if(reviewersName == ''){
                                     		 reviewersName=employeeName;
                                         	 }else{
                                         		 reviewersName=reviewersName+", "+employeeName;
                                             	 }
                                     	 }else{
                                     		 if(reviewersName == ''){
                                         		 reviewersName=employeeName+"(E)";
                                             	 }else{
                                             		 reviewersName=reviewersName+", "+employeeName+"(E)";
                                                 	 }
                                         	 }
                                     }else{
                                    	 if(otherEmployee.trim() == 'InternalValuator'.trim()){
                                             if(valuatorsName == ''){
                                            	 valuatorsName=employeeName;
                                                 }else{
                                                	 valuatorsName=valuatorsName+", "+employeeName;
                                                      }
                                             }else{
                                            	 if(valuatorsName == ''){
                                            		 valuatorsName=employeeName+"(E)";
                                                	 }else{
                                                		 valuatorsName=valuatorsName+", "+employeeName+"(E)";
                                                    	 }
                                                 }
                                         }
    							 htm = htm + "<tr class='row-even' height='25px'> ";
    						     htm=htm + "<td width='10%' height='25' align='center'>"+slNo+ "</td>"+"<td width='15%' align='center'>"+issueDate+ "</td>"+"<td width='15%' align='center'>"+employeeName+ "</td>"+"<td width='15%' align='center'>"+valuator+ "</td>"+"<td width='15%' align='center'>"+answerScript+ "</td>";
    						     htm = htm + "<td width='10%'> <div align='center'> <img src='images/edit_icon.gif' width='16' height='18' style='cursor: pointer' onclick='editNumberOfAnswerScriptDetails("+id+")' /></div></td>";
    						     htm = htm + "<td width='10%'> <div align='center'> <img src='images/delete_icon.gif' width='16' height='18' style='cursor: pointer' onclick='deleteValidationDetails("+id+")' /></div></td>";
    							 htm=htm+"</tr>";
    						     slNo++; 
    				}
    			}
    		}
    		 htm = htm + "</table> </td><td width='5' height='30'  background='images/right.gif'></td>";
    		 htm = htm + "</tr><tr><td height='5'><img src='images/04.gif' width='5' height='5' /></td>";
    		 htm = htm + " <td background='images/05.gif'></td><td><img src='images/06.gif' /></td></tr></table>";
    		    reviewersName= removeDuplicate(reviewersName);
    		    valuatorsName=removeDuplicate(valuatorsName);
				document.getElementById("display_Details").innerHTML = htm;
				document.getElementById("reviewer").innerHTML = reviewersName;
				document.getElementById("valuators").innerHTML = valuatorsName;
    	}
    	getTotalNumberOfAnswerScript();
    	getTotalNumberOfAnswerScriptForValuator2();
    	document.getElementById("hideThisDisplayDetails").style.display = "none";
	}
	function removeDuplicate(value){
		value = value.replace(/[ ]/g,"").split(",");
		var result = [];
		for(var i =0; i < value.length ; i++){
		    if(result.indexOf(value[i]) == -1) result.push(value[i]);
		}
		result=result.join(", ");
		return result;
		}
	function getTotalNumberOfAnswerScript(){
		var examName=document.getElementById("examName").value;
		var subjectId=document.getElementById("subject").value; 
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
		 $.ajax({
		      type: "post",
		      url: "examValidation.do?method=getNumberOfAnswrScriptAndIssuedScripts",
		      data: {examId:examName,subjectId:subjectId,examType:examType},
		      success:function(data) { 
                       var totalAndAlredyIssued = data.split(",");
                       var pendingScript=parseInt(totalAndAlredyIssued[0])-parseInt(totalAndAlredyIssued[1]);
                       var htm="<table width='100%' align='center' height='0'>";
                       htm=htm+"<tr><td height='25' class='row-even' width='21%' align='right' colspan='2'><b style='color: #002267;font-size: 11px;'>"+"Total Scripts:"+"</b></td>"+"<td class='row-even' width='13%' align='left'><b style='color: #002267;font-size: 11px;'>"+totalAndAlredyIssued[0]+"</b></td>";
                       htm=htm+"<td height='25' class='row-even' width='20%' align='right'><b style='color: #002267;font-size: 11px;'>"+"Issued Scripts For Evaluator1:"+"</b></td>"+"<td class='row-even' width='12%' align='left'><b style='color: #002267;font-size: 11px;'>"+totalAndAlredyIssued[1]+"</b></td>";
                       htm=htm+"<td height='25' class='row-even' width='20% align='right'><b style='color: #002267;font-size: 11px;'>"+"Pending Scripts For Evaluator1:"+"</b></td>"+"<td class='row-even' width='12%' align='left'><b style='color: #002267;font-size: 11px;'>"+pendingScript+"</b></td></tr></table>";
                       document.getElementById('displayTotalPendingIssuedScripts').innerHTML = htm;
			      }  
		});
		 document.getElementById("hideThisId").style.display = "none";
		 document.getElementById("displayTotalPendingIssuedScripts").style.display = "block";
	}
	function getTotalNumberOfAnswerScriptForValuator2(){
		var examName=document.getElementById("examName").value;
		var subjectId=document.getElementById("subject").value; 
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
		 $.ajax({
		      type: "post",
		      url: "examValidation.do?method=getNumberOfAnswrScriptAndIssuedScriptsForValuator2",
		      data: {examId:examName,subjectId:subjectId,examType:examType},
		      success:function(data) { 
                       var totalAndAlredyIssued = data.split(",");
                       var pendingScript=parseInt(totalAndAlredyIssued[0])-parseInt(totalAndAlredyIssued[1]);
                       var htm="<table width='100%' align='center' height='0'>";
                       htm=htm+"<tr><td height='25' class='row-even' width='21%' align='right' colspan='2'></td>"+"<td class='row-even' width='13%' align='left'></td>";
                       htm=htm+"<td height='25' class='row-even' width='20%' align='right'><b style='color: #002267;font-size: 11px;'>"+"Issued Scripts For Evaluator2:"+"</b></td>"+"<td class='row-even' width='12%' align='left'><b style='color: #002267;font-size: 11px;'>"+totalAndAlredyIssued[1]+"</b></td>";
                       htm=htm+"<td height='25' class='row-even' width='20% align='right'><b style='color: #002267;font-size: 11px;'>"+"Pending Scripts For Evaluator2:"+"</b></td>"+"<td class='row-even' width=12%' align='left'><b style='color: #002267;font-size: 11px;'>"+pendingScript+"</b></td></tr></table>";
                       document.getElementById('displayTotalPendingIssuedScriptsForEvl2').innerHTML = htm;
			      }  
		});
		 document.getElementById("hideThisIdForEvl2").style.display = "none";
		 document.getElementById("displayTotalPendingIssuedScriptsForEvl2").style.display = "block";
	}
	
	function deleteValidationDetails(id){
		deleteConfirm =confirm("Are you sure to delete this entry?");
		if(deleteConfirm)
		document.location.href = "examValidation.do?method=deleteValidationDetails&deleteId="+id;
	}
	function editNumberOfAnswerScriptDetails(examId){
		document.location.href = "examValidation.do?method=editNumberOfAnswerScriptDetails&id="+examId;
	}
</SCRIPT>
<html:form action="/examValidation">
	<html:hidden property="formName" value="examValidationDetailsForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="saveDetails" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<input type="hidden" id="typeExam" name="type" value='<bean:write name="examValidationDetailsForm" property="examType"/>' />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Valuation Details Entry &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Exam Valuation Details</td>
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
									<div align="Center"><html:radio property="examType"
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
									<td class="row-odd" width="25%" height="25"><div align="right"><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
		                            <td class="row-even" align="left" width="25%" height="25">
		                            <input type="hidden" value="<bean:write property="academicYear" name="examValidationDetailsForm" />" id="tempAcademicYear">
		                            <html:select property="academicYear" styleId="year" styleClass="combo" onchange="getExamNameByYear(this.value)">
	                     	   					 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                     	   					 <cms:renderAcademicYear></cms:renderAcademicYear>
	                     			   		</html:select>
		                            </td>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									Exam Name :</div>
									</td>

									<td width="25%" height="25" class="row-even"><html:select
										name="examValidationDetailsForm" property="examId"
										styleId="examName" styleClass="comboExtraLarge" onchange="getSubjectNameAndCodeForExam()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
											<logic:notEmpty name="examValidationDetailsForm"
												property="examNameList">
												<html:optionsCollection property="examNameList"
													name="examValidationDetailsForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" colspan="2" class="row-even">
									<div align="center">
									<html:radio property="displaySubType" styleId="sCodeName_1" value="sCode" onclick="getSubjectNameAndCodeForExam()">Subject Code</html:radio>
									 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 <html:radio property="displaySubType" styleId="sCodeName_2" value="sName" onclick="getSubjectNameAndCodeForExam()">Subject Name</html:radio>
									</div>
									</td>
									<td height="25" class="row-odd" ><div id = "reFrom" align="right"><span class="Mandatory">*</span>Issue Date:</div></td>
		                             <td height="25" class="row-even" align="left">
		                             <html:text property="date" styleId="date" size="11" maxlength="11"></html:text>
											<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'examValidationDetailsForm',
													// input name
													'controlname' :'date'
												});
											</script>
		                             </td>
								</tr>
								<tr>
									<td class="row-odd" align="right">Subject Search:</td>
									<td height="12" class="row-even"><html:text property="subjectName" styleId="subjectNameId" onkeyup="getSortedSubject(this.value)"></html:text> </td>
                                     <td height="25" class="row-odd" ><div align="right">Valuator:</div></td>
		                             <td height="25" class="row-even" align="left"><div id="valuators"><bean:write property="valuatorDetails" name="examValidationDetailsForm"></bean:write></div>
		                             </td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Subject:</div>
									</td>
									<td height="12" class="row-even"><html:select
										property="subjectId" styleClass="combo" styleId="subject"
										name="examValidationDetailsForm" style="width:450px" onchange="getValuatorsList(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="examValidationDetailsForm"
											property="subjectMap">
											<html:optionsCollection property="subjectMap"
												name="examValidationDetailsForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									<td height="25" class="row-odd" ><div align="right">Reviewer:</div></td>
									<td width="25%" height="25" class="row-even"><div id="reviewer"><bean:write property="reviewerDetails" name="examValidationDetailsForm"></bean:write></div>
									</td>
								</tr>
								<tr>
								<td colspan="4" >
								<div id="hideThisId">
								<logic:equal value="true" name="examValidationDetailsForm" property="displayTotalScripts">
								<table width="100%" height="0"><tr>
								<td width="21%" height="25" class="row-even" colspan="2"><div align="right"><b  style="color: #002267;font-size: 11px;">Total Scripts:</b></div></td>
									<td width="13%" height="25" class="row-even"><b style="color: #002267;font-size: 11px;"><bean:write property="totalAnswerScripts" name="examValidationDetailsForm"></bean:write></b>
									</td>
									<td width="20%" height="25" class="row-even" ><div align="right"><b style="color: #002267;font-size: 11px;">Issued Scripts For Evaluator1:</b></div></td>
									<td width="12%" height="25" class="row-even"><b style="color: #002267;font-size: 11px;"><bean:write property="issuedAnswerScript" name="examValidationDetailsForm"></bean:write></b>
									</td>
									<td width="20%" height="25" class="row-even" ><div align="right"><b style="color: #002267;font-size: 11px;">Pending Scripts For Evaluator1:</b></div></td>
									<td width="12%" height="25" class="row-even"><b  style="color: #002267;font-size: 11px;"><bean:write property="pendingAnswerScript" name="examValidationDetailsForm"></bean:write></b></td>
								</tr></table>
								</logic:equal>
								</div><div id='displayTotalPendingIssuedScripts'></div>
								
								<div id="hideThisIdForEvl2">
								<logic:equal value="true" name="examValidationDetailsForm" property="displayTotalScriptsForEvl2">
								<table width="100%" height="0"><tr>
								<td width="21%" height="25" class="row-even" colspan="2"><div align="right"><b  style="color: #002267;font-size: 11px;"></b></div></td>
									<td width="13%" height="25" class="row-even"><b style="color: #002267;font-size: 11px;"></b>
									</td>
									<td width="20%" height="25" class="row-even" ><div align="right"><b style="color: #002267;font-size: 11px;">Issued Scripts For Evaluator2:</b></div></td>
									<td width="12%" height="25" class="row-even"><b style="color: #002267;font-size: 11px;"><bean:write property="issuedAnswerScriptForEvl2" name="examValidationDetailsForm"></bean:write></b>
									</td>
									<td width="20%" height="25" class="row-even" ><div align="right"><b style="color: #002267;font-size: 11px;">Pending Scripts For Evaluator2:</b></div></td>
									<td width="12%" height="25" class="row-even"><b  style="color: #002267;font-size: 11px;"><bean:write property="pendingAnswerScriptForEvl2" name="examValidationDetailsForm"></bean:write></b></td>
								</tr></table>
								</logic:equal>
								</div><div id='displayTotalPendingIssuedScriptsForEvl2'></div>
								
								</td>
								</tr>
								
								<tr>
									<td class="row-odd" align="right">Valuator Search:</td>
									<td height="12" class="row-even"><html:text property="valuator" styleId="valuator" onkeyup="getSortedValuator(this.value)"></html:text> 
									
										&nbsp;&nbsp;<a href="javascript:void(0)" onclick="allValuators()">Click here for all the Valuators</a>
									
									</td>
									<td width="26%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									Number Of Script :</div>
									</td>
                                    
									<td width="25%" height="25" class="row-even">
									<html:text property="answers" styleId="answers" name="examValidationDetailsForm" onkeypress="return isNumberKey(event)" size="9" maxlength="5"></html:text>
									</td>
								</tr>
								<tr>
								
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Valuator Name:</div>
									</td>
									<td class="row-even">
									
										<html:select property="employeeId" styleClass="comboLarge" styleId="employeeId" onchange="otherValuatorsMap(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection name="evaluatorsMap" label="value" value="key" />
										<html:option value="Other">Other</html:option>
										</html:select>
										&nbsp;<a href="javascript:void(0)" onclick="otherValuatorsMap('Other')">External</a>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Role:</div></td>
									<td class="row-even">
									<html:select property="isReviewer" styleId="isReviewer">
										<html:option value="Valuator1">Valuator1</html:option>
										<html:option value="Valuator2">Valuator2</html:option>
										<html:option value="Reviewer">Reviewer</html:option>
										<html:option value="Project Major">Project Evaluation Major</html:option>
										<html:option value="Project Minor">Project Evaluation Minor</html:option>
										<html:option value="Re-Valuator">Re-Valuator</html:option>
										</html:select>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									</td>
									<td class="row-even">
										<div id="displayOtherEmployee">
										<html:select property="otherEmpId" styleClass="comboLarge" styleId="otherEmpId" onchange="displayOtherField(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection name="OtherEvaluatorsMap" label="value" value="key" />
										
										</html:select>
										<a href="javascript:void(0)" onclick="allExternals()">Click here for all the Externals</a>
										</div>									
									</td>
									<td height="25" class="row-odd">
									<div id="displayOtherId"></div>
									</td>
									<td class="row-even"></td>
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
									<html:submit value="Update" styleClass="formbutton" onclick="updateDetails()"></html:submit>
										&nbsp;&nbsp;
										<input type="button" class="formbutton" value="Reset"
											onclick="resetEditData()" />
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
				            <td height="45" colspan="4" ><div id="hideThisDisplayDetails"><logic:equal value="true" name="examValidationDetailsForm" property="displayTotalScripts"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				              <tr>
				                <td ><img src="images/01.gif" width="5" height="5" /></td>
				                <td width="914" background="images/02.gif"></td>
				                <td><img src="images/03.gif" width="5" height="5" /></td>
				              </tr>
				              <tr>
				                <td width="5"  background="images/left.gif"></td>
				                <td valign="top">
				                <table width="100%" cellspacing="1" cellpadding="2">
				                  <tr >
				                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
				                    <td height="25" class="row-odd" align="center" >Issue Date</td>
				                    <td height="25" class="row-odd" align="center">Valuator Name</td>
				                    <td height="25" class="row-odd" align="center">Valuator (OR) Reviewer</td>
				                    <td height="25" class="row-odd" align="center">Number Of Scripts</td>
				                    <td class="row-odd"><div align="center">Edit</div></td>
				                    <td class="row-odd"><div align="center">Delete</div></td>
				                  </tr>
				                <c:set var="temp" value="0"/>
				                <logic:notEmpty name="examValidationDetailsForm" property="validationDetails">
				                <logic:iterate id="to" name="examValidationDetailsForm" property="validationDetails" indexId="count">
				                   	<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-even">
											</c:otherwise>
									</c:choose>
				                   		<td width="6%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
				                   		<td width="20%" height="25" class="row-even" align="center"><bean:write name="to" property="issueDate"/></td>
				                   		<td width="17%" height="25" class="row-even" align="center"><bean:write name="to" property="employeeName"/></td>
				                   		<td width="33%" height="25" class="row-even" align="center"><bean:write name="to" property="valuator"/></td>
				                   		<td width="15%" height="25" class="row-even" align="center"><bean:write name="to" property="answerScripts"/></td>
				                   		<td width="10%"> <div align="center"> <img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editNumberOfAnswerScriptDetails('<bean:write name="to" property="id"/>')" /></div></td>
								         <td width="10%"> <div align="center"> <img src="images/delete_icon.gif" width="16" height="18" style="cursor: pointer" onclick="deleteValidationDetails('<bean:write name="to" property="id"/>')" /></div></td>
				                </logic:iterate>
				                </logic:notEmpty>
				                </table></td>
				                <td width="5" height="30"  background="images/right.gif"></td>
				              </tr>
				              <tr>
				                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
				                <td background="images/05.gif"></td>
				                <td><img src="images/06.gif" /></td>
				              </tr>
				            </table></logic:equal></div><div id="display_Details"></div></td>
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
		if(displayOther == null || displayOther==""){
			displayOther = document.getElementById("employeeId").value;
		}
		document.getElementById("employeeId").value=displayOther;
		if(displayOther != null && displayOther == "Other"){
			document.getElementById("displayOtherEmployee").style.display = "block";
			//args =  "method=getExternalValuatorsList&subjectId="+document.getElementById("subject").value;
			var examName=document.getElementById("examName").value;
			var subject=document.getElementById("subject").value
			args =  "method=getExternalValuatorsListFromExamValuationDetails&subjectId="+subject+"&examid="+examName;
			var url = "AjaxRequest.do";
			requestOperationProgram(url, args, updateTeachersMapNew);
		}else{
			document.getElementById("displayOtherEmployee").style.display = "none";
		}
		document.getElementById("otherEmpId").value="";
		document.getElementById("displayOtherId").style.display = "none";
	}
	function allExternals(){
		//args =  "method=getExternalValuatorsList&subjectId=";
		var examName=document.getElementById("examName").value;
		args =  "method=getExternalValuatorsListFromExamValuationDetails&examid="+examName+"&subjectId="+"";
		var url = "AjaxRequest.do";
		requestOperationProgram(url, args, updateTeachersMapNew);
	}
	var tempYear=document.getElementById("tempAcademicYear").value;
	if(tempYear!=null && tempYear!=""){
		     document.getElementById("year").value=tempYear;
			}
	document.getElementById("hideThisId").style.display = "block";
	document.getElementById("hideThisDisplayDetails").style.display = "block";
</script>
</html:form>