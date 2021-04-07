<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript" src="js/jquery.js"></script>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendarinterview.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css" type="text/css">
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
</head>
<script language="JavaScript"><!--
function getPrograms(programTypeId) {
	
	if (programTypeId.length > 0) {
		getProgramsByType("programMap", programTypeId, "program",
				updatePrograms);
		resetOption("course");
} else {
	var destination = document.getElementById("interviewType");
	for (x1 = destination.options.length - 1; x1 >= 0; x1--) {
		destination.options[x1] = null;
	}resetOption("course");
	resetOption("program");
}
}

function updatePrograms(req) {
	updateOptionsFromMap(req, "program", "- Select -");
	document.getElementById("programTypeName").value = document
			.getElementById("programType").options[document
			.getElementById("programType").selectedIndex].text;
}

function getCourses(programId) {
	getCoursesByProgram("coursesMap", programId, "course", updateCourses);
//	getinterviewTypeByProgram();
	getCenterByProgram();	
}

function updateCourses(req) {
	updateOptionsFromMap(req, "course", "- Select -");
	document.getElementById("programName").value = document
			.getElementById("program").options[document
			.getElementById("program").selectedIndex].text;

}
function updateExamCenter(req) {
	updateOptionsFromMap(req, "examCenterId", "- Select -");
}

function resetErrorMsgs() {
	document.location.href = "interviewprocess.do?method=initInterviewProcess";
}




function getinterviewType() {
	var courseId = document.getElementById("course").options[document
			.getElementById("course").selectedIndex].value;
	var year = document.getElementById("year").options[document
			.getElementById("year").selectedIndex].value;

	if (courseId.length > 0) {
		getInterviewTypeByCourse("interviewMap", courseId, year,
				"interviewType", updateInterviewType);
		document.getElementById("courseName").value = document
				.getElementById("course").options[document
				.getElementById("course").selectedIndex].text
	}
	if (courseId.length == 0) {
		getinterviewTypeByProgram();
	}
}
function getinterviewTypeByProgram() {
	var year = document.getElementById("year").options[document
			.getElementById("year").selectedIndex].value;
	var programId = document.getElementById("program").options[document
			.getElementById("program").selectedIndex].value;
	if (programId.length > 0) {
		getInterviewTypeByProgram("interviewMap", programId, year,
				"interviewType", updateInterviewTypeProgram);
		document.getElementById("programName").value = document
				.getElementById("program").options[document
				.getElementById("program").selectedIndex].text;
	} else {
		var destination = document.getElementById("interviewType");
		for (x1 = destination.options.length - 1; x1 >= 0; x1--) {
			destination.options[x1] = null;
		}
	}
}
function updateInterviewType(req) {
	updateOptionsFromMapForMultiSelect(req, "interviewType");
}
function updateInterviewTypeProgram(req) {
	updateOptionsFromMapForMultiSelect(req, "interviewType");
}
function getInterviewTypes() {
	var interviewTypeId = document.getElementById("interviewType");
	var selectedArray = new Array();
	var count = 0;
	for ( var i = 0; i < interviewTypeId.options.length; i++) {
		if (interviewTypeId.options[i].selected) {
			selectedArray[count] = interviewTypeId.options[i].value;
			count++;
		}
	}
	return selectedArray;
}
function clearField(field) {
	if (field.value == "00")
		field.value = "";
}
function checkForEmpty(field) {
	if (field.value.length == 0)
		field.value = "00";
}

	function getCandidates() {
		var interviewTypeIds = getInterviewTypes();
		var courseId = document.getElementById("course").value;
		var programId = document.getElementById("program").value;
		var year = document.getElementById("year").value;
		var examCenterId = document.getElementById("examCenterId").value;
		if (programId != '' && year != '') {
			var args = "method=getCandidateCount&courseId="+ courseId+"&year="+year+"&interviewTypeId="+interviewTypeIds+"&programId="+programId + "&examCenterId=" + examCenterId;
			var url = "AjaxRequest.do";
			// make an request to server passing URL need to be invoked and arguments.
			requestOperation(url, args, updateCandidate);
		} else {
			var candidates = document.getElementById("candidates");

			for (x1 = candidates.options.length - 1; x1 > 0; x1--) {
				candidates.options[x1] = null;
			}

		}
	}

	function updateCandidate(req) {
		updateOptionsFromMapValues(req, "candidates");
	}
	
	
	function showStudentGroup() {
		document.getElementById("stPerGroup").style.display = "block";
		document.getElementById("noOfStudentsPerGroup").style.display = "block";
	}
	function hideStudentGroup() {
		document.getElementById("stPerGroup").style.display = "none";
		document.getElementById("noOfStudentsPerGroup").style.display = "none";
	}

	function getCenterByProgram() {
		var year = document.getElementById("year").options[document
				.getElementById("year").selectedIndex].value;
		var programId = document.getElementById("program").options[document
				.getElementById("program").selectedIndex].value;
		if (programId.length > 0) {
			getExamCenterByProgram("examCenterMap", programId, "examCenterId", updateExamCenter);
			document.getElementById("programName").value = document
					.getElementById("program").options[document
					.getElementById("program").selectedIndex].text;
		} else {
			var destination = document.getElementById("examCenterId");
			for (x1 = destination.options.length - 1; x1 >= 0; x1--) {
				destination.options[x1] = null;
			}
		}
	}	


	function getCount() {
		var interviewTypeIds = getInterviewTypes();
		var courseId = document.getElementById("course").value;
		var programId = document.getElementById("program").value;
		var year = document.getElementById("year").value;
		var interviewDate=document.getElementById("dateOfInterview").value;
		var stime=document.getElementById("shtime").value+":"+document.getElementById("smtime").value;
		var etime=document.getElementById("ehtime").value+":"+document.getElementById("emtime").value;
		var examCenterId = document.getElementById("examCenterId").value;
		if (programId != '' && year != '' && interviewTypeIds!='' && interviewDate!='' && stime!='00:00') {
			var args = "method=getCountForSelectedInput&courseId="+ courseId+"&year="+year+"&interviewTypeId="+interviewTypeIds+"&programId="+programId 
			+ "&examCenterId=" + examCenterId+"&date="+interviewDate+"&stime="+stime+"&etime="+etime;
			var url = "AjaxRequest.do";
			// make an request to server passing URL need to be invoked and arguments.
			requestOperation(url, args, updateCount);
		} 
	}

	function updateCount(req) {
		var responseObj = req.responseXML.documentElement;
		var items = responseObj.getElementsByTagName("value");
		for ( var I = 0; I < items.length; I++) {
			var temp = items[I].firstChild.nodeValue;
			document.getElementById("count").innerHTML ="No of interview Cards Generated Already  "+ temp;
		}
	}
	function showWarning(){
		var value1=document.getElementById("bhtime").value;
		var value2=document.getElementById("bhttime").value;
		var value3=document.getElementById("bhtttime").value;
		var value4=document.getElementById("bhftime").value;
		var value5=document.getElementById("bhfthree").value;
		var value6=document.getElementById("bhtthree").value;
		
		if(value1==00 && value2==0 && value3==00 && value4==0 && value5==00 && value6==0 ){
			var value=true;
			
			}
		if(value){
		
			 $.confirm({
				 
					'message'	: '<b align="center">Wish to continue without Break time?</b> <br/> Make sure the following is done correctly: <br/> 1) SMS is enabled and template is set. <br/> 2) Email template is set. <br/> 3) Missing document candidates are not present in the list. <br/>4) E-admit card template is set. <br/>',
					'buttons'	: {
						'Ok'	: {
							'class'	: 'red',
							'action': function(){
								$.confirm.hide();
								document.interviewProcessForm.method.value="submitInterviewProcess";	
								document.interviewProcessForm.submit();

								
							}
						},

					'Cancel'	: {
							'class'	: 'red',
							'action': function(){
								$.confirm.hide();
								
							}
					
					}

					}
						
						
					


				
				});
		

		
			
		}else{
			
			 $.confirm({
				 
					'message'	:'Make sure the following is done correctly: <br/> 1) SMS is enabled and template is set.<br/> 2) Email template is set. <br/> 3) Missing document candidates are not present in the list.<br/> 4) E-admit card template is set <br/>',                        
				
					'buttons'	: {
						'Ok'	: {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();




								





								
							}
						}
					}
				});
		

	}
}

	function athira(){
		 $.confirm({
			 
				'message'	:'Make sure the following is done correctly: <br/> 1) SMS is enabled and template is set.<br/> 2) Email template is set.<br/> 3) Missing document candidates are not present in the list.<br/> 4) E-admit card template is set <br/>',                        
			
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();




							document.interviewProcessForm.method.value="submitInterviewProcess";	
							document.interviewProcessForm.submit();






							
						}
					}
				}
			});}

	
	//function showWarningAndSubmit(){
		//var msg="Make sure the following is done correctly:\n "+                        
		//	"1) SMS is enabled and template is set.\n "+
		//	"2) Email template is set.\n "+
		 //   "3) Missing document candidates are not present in the list.\n "+
		  //  "4) E-admit card template is set";
		//var confirmWarning=confirm(msg);
		//if(confirmWarning){
		//document.interviewProcessForm.method.value="submitInterviewProcess";	
		//document.interviewProcessForm.submit();
		//}
			
	//}

	
</script>

<body>
<html:form action="interviewprocess" method="post">
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="formName" value="interviewProcessForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="programTypeName" styleId="programTypeName"
		value="" />
	<html:hidden property="programName" styleId="programName"
		value="" />
	<html:hidden property="courseName" styleId="courseName"
		value="" />

	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.interview.Workflow" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.interview.Workflow" /> </strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<div align="right"><span class='MandatoryMark'> <bean:message
							key="knowledgepro.mandatoryfields" /></span></div>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr bgcolor="#FFFFFF">
									<td height="20" colspan="6" class="body" align="left">
									<div id="count"></div>
									<div id="errorMessage"><FONT color="red"
										style="line-height: 12px"><html:errors /></FONT> <FONT
										color="green"> <html:messages id="msg"
										property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages> </FONT></div>
									</td>
								</tr>

								<tr>
									<td width="10%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.ProgramType" /></div>
									</td>
									<input type="hidden" name="programTId" id="programTId"
										value='<bean:write name="interviewProcessForm" property="programTypeId"/>' />
									<td width="20%" height="25" class="row-even"><html:select
										name="interviewProcessForm" property="programTypeId"
										styleId="programType" styleClass="combo"
										onchange="getPrograms(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId" />
									</html:select></td>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Program" /></div>
									</td>
									<td width="20%" class="row-even"><html:select
										name="interviewProcessForm" property="program"
										styleId="program" styleClass="combo"
										onchange="getCourses(this.value),getinterviewTypeByProgram(),getCount()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${interviewProcessForm.programTypeId != null && interviewProcessForm.programTypeId != ''}">
											<c:set var="programMap"
												value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select></td>
									<td width="10%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.interview.Course" /></div>
									</td>
									<td width="30%" class="row-even"><html:select
										name="interviewProcessForm" property="courseId"
										styleId="course" styleClass="combo"
										onchange="getinterviewType(),getCount()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
											<c:if test="${coursesMap != null}">
												<html:optionsCollection name="coursesMap" label="value"
													value="key" />
											</c:if>
									</html:select></td>
								</tr>
								<tr>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td width="20%" class="row-even"><input type="hidden" id="tempyear"
										name="tempyear"
										value="<bean:write name="interviewProcessForm" property="years"/>" />
									<html:select name="interviewProcessForm" property="years"
										styleId="year" styleClass="combo"
										onchange="getinterviewType(),getCount()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select></td>
									<td width="10%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.InterviewType" /></div>
									</td>
									<td width="20%" height="25" class="row-even">
									<input type="hidden" name="itId" id="itId"
										value='<bean:write name="interviewProcessForm" property="hiddenInterviewType"/>' />
									<html:select
										name="interviewProcessForm" property="interviewType"
										styleId="interviewType" styleClass="body" multiple="multiple"
										size="6" style="width:220px" onchange="getCandidates(),getCount()">

											<c:if test="${interviewMap != null}">
												<html:optionsCollection name="interviewMap" label="value"
													value="key" />
											</c:if>
									</html:select></td>
									<td width="10%" class="row-odd">
									<div align="right">Exam Center:</div>
									<br>
									<br>
									
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.NoofCandidates" /></div>
									</td>
									<td width="30%" class="row-even">
									
									
									<html:select
										name="interviewProcessForm" property="examCenterId"
										styleId="examCenterId" styleClass="combo"
										onchange="getCandidates()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${interviewProcessForm.program != null && interviewProcessForm.program != ''}">										
											<c:set var="examCenterMap"
												value="${baseActionForm.collectionMap['examCenterMap']}" />
											<c:if test="${examcenterMap != null}">
												<html:optionsCollection name="examCenterMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select>	
									<br>
									<br>
									
									<html:text
										name="interviewProcessForm" property="noOfCandidates"
										styleClass="body" styleId="candidates" size="12" maxlength="9"
										onkeypress="return isNumberKey(event)" /><html:hidden
										name="interviewProcessForm" property="noOfCandidates_orig"
										styleId="noOfCandidates_orig" /></td>
								</tr>
								<tr>
									<td width="10%" height="25" valign="top" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.DatesofInterview" /></div>
									</td>
									<td width="20%" height="25" valign="top" class="row-even"><label>
									<%--       <html:hidden name="interviewProcessForm" styleId="interviewdate" styleClass="body" property="datesOfInterview"></html:hidden> --%>
									<html:textarea name="interviewProcessForm"
										property="datesOfInterview" styleId="dateOfInterview" rows="5" onchange="getCount()"></html:textarea>
									<%--        <html:select name="interviewProcessForm" property="datesOfInterview"  styleId="interviewdate" styleClass="body">
					              	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>					              	
				              </html:select>        --%> <script
										language="JavaScript">
								new tcal( {
									// form name
									'formname' :'interviewProcessForm',
									// input name
									'controlname' :'datesOfInterview'
								});
							</script></label></td>
									<td width="10%" valign="top" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>No. of
									Panels per day <br>
									<br>
									<span class="Mandatory">*</span>Single/Group:<br>
									<br>
									<br>
									<br>
									<div id="stPerGroup"><span class="Mandatory">*</span>Students
									per Group:</div>
									</div>
									</td>
									<td width="20%" valign="top" class="row-even"><html:text
										name="interviewProcessForm" property="noOfInterviewers"
										styleClass="body" styleId="interviewers" size="3"
										maxlength="3" onkeypress="return isNumberKey(event)" /> <br>
									<br>
									<br>
									<input type="radio" id="single" name="singleGroup"
										value="single" checked="checked" onclick=hideStudentGroup();>
									Single <br>
									<html:radio property="singleGroup" value="group"
										styleId="group" onclick="showStudentGroup()">Group</html:radio>
									<br>
									<br>
									<html:text name="interviewProcessForm"
										property="noOfStudentsPerGroup" styleClass="body"
										styleId="noOfStudentsPerGroup" size="3" maxlength="3"
										onkeypress="return isNumberKey(event)" /></td>
									<td width="10%" valign="top" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Venue" /></div>
									</td>
									<td width="30%" valign="top" class="row-even"><html:textarea
										name="interviewProcessForm" styleId="venueid" property="venue"
										cols="10" rows="3"></html:textarea></td>
								</tr>
								<tr>
									<td  height="10" colspan="6" class="heading"><bean:message
										key="knowledgepro.interview.Timings" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.STime" /></div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										id="stHours" name="stHours"
										value="<bean:write name="interviewProcessForm" property="startHours"/>" />
									<input type="hidden" id="stMins" name="stMins"
										value="<bean:write name="interviewProcessForm" property="startMins"/>" />
									<html:text name="interviewProcessForm" property="startHours"
										styleClass="Timings" value="00" styleId="shtime" size="2"
										maxlength="2" onfocus="clearField(this)"
										onblur="checkForEmpty(this)"
										onkeypress="return isNumberKey(event)" onchange="getCount()"/> : <html:text
										name="interviewProcessForm" property="startMins"
										styleClass="Timings" value="00" styleId="smtime" size="2"
										maxlength="2" onfocus="clearField(this)"
										onblur="checkForEmpty(this)"
										onkeypress="return isNumberKey(event)" onchange="getCount()"/></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.EndTime" /></div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										id="enHours" name="enHours"
										value="<bean:write name="interviewProcessForm" property="endHours"/>" />
									<input type="hidden" id="enMins" name="enMins"
										value="<bean:write name="interviewProcessForm" property="endMins"/>" />
									<html:text name="interviewProcessForm" property="endHours"
										styleClass="Timings" value="00" styleId="ehtime" size="2"
										maxlength="2" onfocus="clearField(this)"
										onblur="checkForEmpty(this)"
										onkeypress="return isNumberKey(event)" onchange="getCount()"/> : <html:text
										name="interviewProcessForm" property="endMins"
										styleClass="Timings" value="00" styleId="emtime" size="2"
										maxlength="2" onfocus="clearField(this)"
										onblur="checkForEmpty(this)"
										onkeypress="return isNumberKey(event)" onchange="getCount()" /></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.TimeInterval" /></div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										id="inHours" name="inHours"
										value="<bean:write name="interviewProcessForm" property="intervalHours"/>" />
									<input type="hidden" id="inMins" name="inMins"
										value="<bean:write name="interviewProcessForm" property="intervalMins"/>" />
									<html:text name="interviewProcessForm" property="intervalHours"
										styleClass="Timings" value="00" styleId="ihtime" size="2"
										maxlength="2" onfocus="clearField(this)"
										onblur="checkForEmpty(this)"
										onkeypress="return isNumberKey(event)" /> : <html:text
										name="interviewProcessForm" property="intervalMins"
										styleClass="Timings" value="00" styleId="imtime" size="2"
										maxlength="2" onfocus="clearField(this)"
										onblur="checkForEmpty(this)"
										onkeypress="return isNumberKey(event)" /></td>
								</tr>

							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">


						<tr>
							<td width="100%" height="118">
							<div align="center">
							<table width="54%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="450" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="81">
									<table width="100%" align="center" cellpadding="2"
										cellspacing="1">
										<tr>
											<td class="row-odd"><bean:message
												key="knowledgepro.interview.BreakTime" /></td>
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.interview.From" /></div>
											</td>
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.interview.To" /></div>
											</td>
										</tr>
										<tr>
											<td width="30%" class="row-even">&nbsp;</td>
											<td width="36%" height="25" class="row-even"><label>
											</label>
											<div align="center"><input type="hidden" id="bsHours"
												name="bsHours"
												value="<bean:write name="interviewProcessForm" property="breakFromHours"/>" />
											<input type="hidden" id="bsMins" name="bsMins"
												value="<bean:write name="interviewProcessForm" property="breakFromMins"/>" />
											<html:text name="interviewProcessForm"
												property="breakFromHours" styleClass="Timings" value="00"
												styleId="bhtime" size="2" maxlength="2"
												onfocus="clearField(this)" onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /> : <html:text
												name="interviewProcessForm" property="breakFromMins"
												styleClass="Timings" value="00" styleId="bmtime" size="2"
												maxlength="2" onfocus="clearField(this)"
												onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /></div>
											</td>
											<td width="34%" height="25" class="row-even"><label>
											</label>
											<div align="center"><input type="hidden" id="beHours"
												name="beHours"
												value="<bean:write name="interviewProcessForm" property="breakToHours"/>" />
											<input type="hidden" id="beMins" name="beMins"
												value="<bean:write name="interviewProcessForm" property="breakToMins"/>" />
											<html:text name="interviewProcessForm"
												property="breakToHours" styleClass="Timings" value="00"
												styleId="bhttime" size="2" maxlength="2"
												onfocus="clearField(this)" onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /> : <html:text
												name="interviewProcessForm" property="breakToMins"
												styleClass="Timings" value="00" styleId="bmttime" size="2"
												maxlength="2" onfocus="clearField(this)"
												onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /></div>
											</td>
										</tr>
										<tr>
											<td class="row-white">&nbsp;</td>
											<td height="25" class="row-white">
											<div align="center"><span class="row-even"> <input
												type="hidden" id="btwosHours" name="btwosHours"
												value="<bean:write name="interviewProcessForm" property="breakFromHoursTwo"/>" />
											<input type="hidden" id="btwosMins" name="btwosMins"
												value="<bean:write name="interviewProcessForm" property="breakFromMinsTwo"/>" />
											<html:text name="interviewProcessForm"
												property="breakFromHoursTwo" styleClass="Timings" value="00"
												styleId="bhtttime" size="2" maxlength="2"
												onfocus="clearField(this)" onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /> : <html:text
												name="interviewProcessForm" property="breakFromMinsTwo"
												styleClass="Timings" value="00" styleId="bmtttime" size="2"
												maxlength="2" onfocus="clearField(this)"
												onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /> </span></div>
											</td>
											<td height="25" class="row-white">
											<div align="center"><span class="row-even"> <input
												type="hidden" id="btwoeHours" name="btwoeHours"
												value="<bean:write name="interviewProcessForm" property="breakToHoursTwo"/>" />
											<input type="hidden" id="btwoeMins" name="btwoeMins"
												value="<bean:write name="interviewProcessForm" property="breakToMinsTwo"/>" />
											<html:text name="interviewProcessForm"
												property="breakToHoursTwo" styleClass="Timings" value="00"
												styleId="bhftime" size="2" maxlength="2"
												onfocus="clearField(this)" onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /> : <html:text
												name="interviewProcessForm" property="breakToMinsTwo"
												styleClass="Timings" value="00" styleId="bmftime" size="2"
												maxlength="2" onfocus="clearField(this)"
												onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /> </span></div>
											</td>
										</tr>
										<tr>
											<td class="row-even">&nbsp;</td>
											<td height="25" class="row-even"><label> </label>
											<div align="center"><input type="hidden"
												id="bthreesHours" name="bthreesHours"
												value="<bean:write name="interviewProcessForm" property="breakFromHoursThree"/>" />
											<input type="hidden" id="bthreesMins" name="bthreesMins"
												value="<bean:write name="interviewProcessForm" property="breakFromMinsThree"/>" />
											<html:text name="interviewProcessForm"
												property="breakFromHoursThree" styleClass="Timings"
												value="00" styleId="bhfthree" size="2" maxlength="2"
												onfocus="clearField(this)" onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /> : <html:text
												name="interviewProcessForm" property="breakFromMinsThree"
												styleClass="Timings" value="00" styleId="bmfthree" size="2"
												maxlength="2" onfocus="clearField(this)"
												onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /></div>
											</td>
											<td height="25" class="row-even"><label> </label>
											<div align="center"><input type="hidden"
												id="bthreeeHours" name="bthreeeHours"
												value="<bean:write name="interviewProcessForm" property="breakToHoursThree"/>" />
											<input type="hidden" id="bthreeeMins" name="bthreeeMins"
												value="<bean:write name="interviewProcessForm" property="breakToMinsThree"/>" />
											<html:text name="interviewProcessForm"
												property="breakToHoursThree" styleClass="Timings" value="00"
												styleId="bhtthree" size="2" maxlength="2"
												onfocus="clearField(this)" onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /> : <html:text
												name="interviewProcessForm" property="breakToMinsThree"
												styleClass="Timings" value="00" styleId="bmtthree" size="2"
												maxlength="2" onfocus="clearField(this)"
												onblur="checkForEmpty(this)"
												onkeypress="return isNumberKey(event)" /></div>
											</td>
										</tr>
										<tr>
											<td class="row-white">&nbsp;</td>
											<td height="25" class="row-white">&nbsp;</td>
											<td height="25" class="row-white">&nbsp;</td>
										</tr>
										<tr></tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="81"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</div>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="33" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">
					<table width="100%" height="27" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
							<div align="center">
							<table width="60%" height="27" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="24%">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="45%" height="35">
											<div align="right">
											<html:button property=""
												styleClass="formbutton" value="Generate"
												onclick="showWarning()"></html:button>
											</div>
											</td>
											<td width="2%"></td>
											<td width="11%"><html:button property=""
												styleClass="formbutton" value="Reset"
												onclick="resetErrorMsgs()"></html:button></td>
											<td width="42%"></td>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</div>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>

<script type="text/javascript" language="javascript">
	
	var itId ="<c:out value='${interviewProcessForm.hiddenInterviewType}'/>";
	var id=itId.split(",");
	var opt=document.getElementById("interviewType").options;
	
	for( i=0;i<=opt.length;i++){
	for(k=0;k<id.length;k++)
	{	
		 	var j=id[k];
		 	if(opt[i].value==j){
		        opt[i].selected = true;
		 	}
		}
	}

	
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
	var shour = document.getElementById("stHours").value;
	if (shour.length != 0) {
		document.getElementById("shtime").value = shour;
	}
	var smin = document.getElementById("stMins").value;
	if (smin.length != 0) {
		document.getElementById("smtime").value = smin;
	}
	var ehour = document.getElementById("enHours").value;
	if (ehour.length != 0) {
		document.getElementById("ehtime").value = ehour;
	}
	var emin = document.getElementById("enMins").value;
	if (emin.length != 0) {
		document.getElementById("emtime").value = emin;
	}
	var ihour = document.getElementById("inHours").value;
	if (ihour.length != 0) {
		document.getElementById("ihtime").value = ihour;
	}
	var imin = document.getElementById("inMins").value;
	if (imin.length != 0) {
		document.getElementById("imtime").value = imin;
	}
	var bshour = document.getElementById("bsHours").value;
	if (bshour.length != 0) {
		document.getElementById("bhtime").value = bshour;
	}
	var bsmin = document.getElementById("bsMins").value;
	if (bsmin.length != 0) {
		document.getElementById("bmtime").value = bsmin;
	}
	var behour = document.getElementById("beHours").value;
	if (behour.length != 0) {
		document.getElementById("bhttime").value = behour;
	}
	var bemin = document.getElementById("beMins").value;
	if (bemin.length != 0) {
		document.getElementById("bmttime").value = bemin;
	}
	var btwoshour = document.getElementById("btwosHours").value;
	if (btwoshour.length != 0) {
		document.getElementById("bhtttime").value = btwoshour;
	}
	var btwosmin = document.getElementById("btwosMins").value;
	if (btwosmin.length != 0) {
		document.getElementById("bmtttime").value = btwosmin;
	}
	var btwoehour = document.getElementById("btwoeHours").value;
	if (btwoehour.length != 0) {
		document.getElementById("bhftime").value = btwoehour;
	}
	var btwoemin = document.getElementById("btwoeMins").value;
	if (btwoemin.length != 0) {
		document.getElementById("bmftime").value = btwoemin;
	}

	var bthreeshour = document.getElementById("bthreesHours").value;
	if (bthreeshour.length != 0) {
		document.getElementById("bhfthree").value = bthreeshour;
	}
	var bthreesmin = document.getElementById("bthreesMins").value;
	if (bthreesmin.length != 0) {
		document.getElementById("bmfthree").value = bthreesmin;
	}
	var bthreeehour = document.getElementById("bthreeeHours").value;
	if (bthreeehour.length != 0) {
		document.getElementById("bhtthree").value = bthreeehour;
	}
	var bthreeemin = document.getElementById("bthreeeMins").value;
	if (bthreeemin.length != 0) {
		document.getElementById("bmtthree").value = bthreeemin;
	}

	var stPerGroupp = document.getElementById("group").checked;
	if (stPerGroupp == true) {
		document.getElementById("noOfStudentsPerGroup").style.display = "block";
	} else {
		document.getElementById("stPerGroup").style.display = "none";
		document.getElementById("noOfStudentsPerGroup").style.display = "none";
	}
</script>

