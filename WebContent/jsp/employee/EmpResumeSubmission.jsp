<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<%@page import="com.kp.cms.constants.CMSConstants"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Employee Resume Submission</title>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script language="JavaScript" src="js/admissionform.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type='text/javascript' src="jquery/Zebra/javascript/zebra_dialog.js"></script>
<link rel="stylesheet" href="jquery/Zebra/css/default/zebra_dialog.css"  />
<script language = "Javascript">
	var destId;
	function resetEmpResume(){
		document.getElementById("method").value="initEmpResume";
		document.empResumeSubmissionForm.submit();
	}
	function saveResume(){
		// code added by sudhir
		var size=document.getElementById("teachingExpLength").value;
		for(i=0;i<=size;i++){
			var startDate= document.getElementById("teachingFromDate_" + i).value;
			var endDate= document.getElementById("teachingToDate_" + i).value;
			if(startDate!= ""){
				if(endDate == ""){
					alert("Please Enter the ToDate");
					document.getElementById("teachingToDate_" + i).focus();
					return false;
				}
			}
			if(endDate!= ""){
				if(startDate == ""){
					alert("Please Enter the FromDate");
					document.getElementById("teachingFromDate_" + i).focus();
					return false;
				}
			}
		}
		isize=document.getElementById("industryExpLength").value;
		for(i=0;i<=isize;i++){
			var startDate= document.getElementById("industryFromDate_" + i).value;
			var endDate= document.getElementById("industryToDate_" + i).value;
			if(startDate!= ""){
				if(endDate == ""){
					alert("Please Enter the ToDate");
					document.getElementById("industryToDate_" + i).focus();
					return false;
				}
			}
			if(endDate!= ""){
				if(startDate == ""){
					alert("Please Enter the FromDate");
					document.getElementById("industryFromDate_" + i).focus();
					return false;
				}
			}
		}
		// code added by sudhir
		document.getElementById("method").value="saveOnlineResume";
		document.empResumeSubmissionForm.submit();
	}
	function submitEmployeeInfoAdd(method,mode){
		document.getElementById("method").value=method;
		document.getElementById("mode").value=mode;
		document.empResumeSubmissionForm.submit();
	}
	
	function getStateByCountry(country,stateId) {
		destId=stateId;
		getStatesByCountry("stateMap", country, stateId,updateState);
	}

	function getCurrentStateByCountry(country,stateId) {
		destId=stateId;
		getStatesByCountry("currentStateMap", country, stateId,updateState);
	}
	function updateState(req) {
		updateOptionsFromMapWithOther(req,destId,"-Select-"); 
	}
	var totalExp;
	function getYears(field){
		alert("hai");
		//checkForEmpty(field);
		if(isNaN(field.value)) {
			document.getElementById("err").innerHTML = "please enter valid number";
			error = true;
			return;
		}
		getTotalYears();
		getTotalMonths();
		if(totalMonth>=12){
			totalExp=totalExp+(totalMonth/12);
			totalMonth=parseInt(totalMonth % 12);
			document.getElementById("expYears").value = parseInt(totalExp);
		}
		document.getElementById("expMonths").value = totalMonth;
	}

	function getTotalYears(){
		totalExp=0;
		size=document.getElementById("teachingExpLength").value;
		for(i=0;i<=size;i++){
			if(document.getElementById("teach_"+i).value!='')
			totalExp=totalExp+ parseInt(document.getElementById("teach_"+i).value);
		}
		isize=document.getElementById("industryExpLength").value;
		for(i=0;i<=isize;i++){
			if(document.getElementById("industry_"+i).value!='')
			totalExp=totalExp+ parseInt(document.getElementById("industry_"+i).value);
		}
		document.getElementById("expYears").value= totalExp;
	}
	var totalMonth;
	function getMonths(field){
		//checkForEmpty(field);
		if(isNaN(field.value)) {
			document.getElementById("err").innerHTML = "please enter valid month";
			error = true;
			return;
		}
		getTotalMonths();
		if(totalMonth>=12){
			getTotalYears();
			totalExp=totalExp+(totalMonth/12);
			totalMonth=parseInt(totalMonth % 12);
			document.getElementById("expYears").value = parseInt(totalExp);
		}
		document.getElementById("expMonths").value = totalMonth;
	}

	function getTotalMonths(){
		totalMonth=0;
		size=document.getElementById("teachingExpLength").value;
		for(i=0;i<=size;i++){
			if(document.getElementById("teach_month_"+i).value!='')
				totalMonth=totalMonth+ parseInt(document.getElementById("teach_month_"+i).value);
		}
		isize=document.getElementById("industryExpLength").value;
		for(i=0;i<=isize;i++){
			if(document.getElementById("industry_month_"+i).value!='')
				totalMonth=totalMonth+ parseInt(document.getElementById("industry_month_"+i).value);
		}
	}

	function checkForEmpty(field) {
		if (field.value.length == 0)
			field.value = "0";
		if (field.value == 0)
			field.value = "0";

	}

	
	function disableAddress()
	{
		document.getElementById("currLabel").style.display="none";
		document.getElementById("currTable").style.display="none";
	}
	function enableAddress()
	{
		document.getElementById("currLabel").style.display="block";
		document.getElementById("currTable").style.display="block";
	}
	
	function imposeMaxLength(evt, desc) {
		var keynum = (evt.which) ? evt.which : event.keyCode;
		if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
			return true;
		}
		var MaxLen = 10;
		return (desc.length < MaxLen);
	}

	function maxlength(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}

	function postApplied(){
		desig=document.getElementById("designationId").value;
		if(desig!=null)
		{
			if(desig=="Non-Teaching")
			{
				document.getElementById("emailMandatorySymbol").style.display="none";
			  document.getElementById("department").style.display="none";
			  document.getElementById("department1").style.display="none";
				document.getElementById("departmentId").disabled=true;
				document.getElementById("desiredJobHeading").style.display="block";
				document.getElementById("desiredJob").style.display="block";
			}
			else if(desig=="Teaching")
			{
				document.getElementById("emailMandatorySymbol").style.display="block";
				document.getElementById("department").style.display="block";
				 document.getElementById("department1").style.display="block";
				document.getElementById("departmentId").disabled=false;
				document.getElementById("departmentId").value="";
				document.getElementById("desiredJobHeading").style.display="block";
				document.getElementById("desiredJob").style.display="block";
				
								
			}
			else if(desig=="Guest")
			{
				
			    document.getElementById("emailMandatorySymbol").style.display="none";
			    document.getElementById("department").style.display="block";
			    document.getElementById("department1").style.display="block";
			    document.getElementById("departmentId").disabled=false;
				document.getElementById("desiredJobHeading").style.display="none";
				document.getElementById("desiredJob").style.display="none";
			}
				
		}
	}

	function getOtherEligibilityTest(){
		if(document.getElementById("eligibilityTestOther").checked==true){
			document.getElementById("otherEligibilityTest").style.display="block";
		}else{
			 document.getElementById("otherEligibilityTest").style.display="none";
    	}
	}

	function disableRemainingEligibilityTest(){
		if(document.getElementById("eligibilityTestNone").checked==true){
		document.getElementById("eligibilityTestNET").value="";
		document.getElementById("eligibilityTestSLET").value="";
		document.getElementById("eligibilityTestSET").value="";
		document.getElementById("eligibilityTestOther").value="";
		document.getElementById("otherEligibilityTestValue").value="";
		document.getElementById("eligibilityTestNET").checked=false;
		document.getElementById("eligibilityTestSLET").checked=false;
		document.getElementById("eligibilityTestSET").checked=false;
		document.getElementById("eligibilityTestOther").checked=false;
		document.getElementById("eligibilityTestNET").disabled=true;
		document.getElementById("eligibilityTestSLET").disabled=true;
		document.getElementById("eligibilityTestSET").disabled=true;
		document.getElementById("eligibilityTestOther").disabled=true;
		}
		else {
			document.getElementById("eligibilityTestNET").disabled=false;
			document.getElementById("eligibilityTestSLET").disabled=false;
			document.getElementById("eligibilityTestSET").disabled=false;
			document.getElementById("eligibilityTestOther").disabled=false;
		}
	}
	function disableEligibilityTestNone(property){
		if(document.getElementById(property).checked==true){
			document.getElementById("eligibilityTestNone").value="";
		document.getElementById("eligibilityTestNone").checked=false;
		document.getElementById("eligibilityTestNone").disabled=true;
		}
		else{
			document.getElementById("eligibilityTestNone").disabled=false;
		}
	}

	

	function getPersonDisability(){
		if(document.getElementById("personWithDisability").checked==true){
			
			document.getElementById("handicappedDescription").style.display="block";
		}else{
			 document.getElementById("handicappedDescription").style.display="none";
    	}
	}
	function showHandicappedDescription(){
		document.getElementById("handicappedDescription").style.display = "block";
	}

	function hideHandicappedDescription(){
		document.getElementById("handicappedDescription").style.display = "none";
		document.getElementById("handicappedDescription").value = "";
	}
function removeTextField(countValue,testValue){
		
		if(document.getElementById(countValue).checked==true && document.getElementById(countValue).value=="OTHER")
		{
		document.getElementById("otherEligibilityTest").style.display="block";
		}
		else if(document.getElementById(countValue).checked==false && document.getElementById(countValue).value=="OTHER")
		{
		document.getElementById("otherEligibilityTestValue").value="";
		 document.getElementById("otherEligibilityTest").style.display="none";
		}
	}
</script>
<script language="javascript">
// to set lenth of text area 
function imposeMaxLength1(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}
	// to display the text areas length 
	function len_display(Object,MaxLen,element){
	    var len_remain = Object.value.length;
	   if(len_remain <=500){
	    document.getElementById(element).value=len_remain; }
	}
	function calculateDates(count){
		document.getElementById("count").value = count;
		var startDate= document.getElementById("teachingFromDate_" + count).value;
		var endDate= document.getElementById("teachingToDate_" + count).value;
		if((startDate==null || startDate=="") ||(endDate==null || endDate==""))
			{
			document.getElementById("teach_"+count).value="0";
			document.getElementById("teach_month_"+count).value = "0";
			getTotalYears();
			getTotalMonths();
			if(totalMonth>=12){
				getTotalYears();
				totalExp=totalExp+(totalMonth/12);
				totalMonth=parseInt(totalMonth % 12);
				document.getElementById("expYears").value = parseInt(totalExp);
			}
			document.getElementById("expMonths").value = totalMonth;
		}else
			{  
			 var validformat=/^\d{1,2}\/\d{1,2}\/\d{4}$/;
		     if (!validformat.test(endDate)){
		     alert('Invalid Date Format. Please correct date in DD/MM/YYYY format only.');	}
		     else
		     {
					d1=startDate.split('/');
					d2=endDate.split('/');
					    var startDate1=new Date(d1[2],(d1[1]-1),d1[0]);
					    var endDate1=new Date(d2[2],(d2[1]-1),d2[0]);  
						if(startDate!="" && endDate!=""){
							if(startDate1 > endDate1){
								alert("FromDate cannot be greater than ToDate");
								document.getElementById("teach_"+count).value="0";
								document.getElementById("teach_month_"+count).value = "0";
								document.getElementById("teachingToDate_"+count).value = null;
								document.getElementById("teachingToDate_"+count).select();
								getTotalYears();
								getTotalMonths();
								if(totalMonth>=12){
									getTotalYears();
									totalExp=totalExp+(totalMonth/12);
									totalMonth=parseInt(totalMonth % 12);
									document.getElementById("expYears").value = parseInt(totalExp);
								}
								document.getElementById("expMonths").value = totalMonth;
							}else{
								var args ="method=getStartDateAndEndDateCalculations&startDate="+startDate+"&endDate="+endDate;
								var url ="AjaxRequest.do";
								requestOperation(url,args,updateStartDateAndEndDateCalculations);
							}	
						}
				}
			}
	}	
	
	function updateStartDateAndEndDateCalculations(req){
		var count1 = document.getElementById("count").value;
		var responseObj = req.responseXML.documentElement;
		var childNodes = responseObj.childNodes;
		var items = responseObj.getElementsByTagName("option");
		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel") [0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue") [0].firstChild.nodeValue;
			document.getElementById("teach_"+count1).value=value;
			document.getElementById("teach_month_"+count1).value=label;
		}
		getTotalYears();
		getTotalMonths();
		if(totalMonth>=12){
			getTotalYears();
			totalExp=totalExp+(totalMonth/12);
			totalMonth=parseInt(totalMonth % 12);
			document.getElementById("expYears").value = parseInt(totalExp);
		}
		document.getElementById("expMonths").value = totalMonth;
	}
	
	function calculateDates1(count){
		document.getElementById("count").value = count;
		var startDate= document.getElementById("industryFromDate_" + count).value;
		var endDate= document.getElementById("industryToDate_" + count).value;
		if((startDate==null || startDate=="") ||(endDate==null || endDate==""))
		{
		document.getElementById("industry_"+count).value="0";
		document.getElementById("industry_month_"+count).value = "0";
		getTotalYears();
		getTotalMonths();
		if(totalMonth>=12){
			getTotalYears();
			totalExp=totalExp+(totalMonth/12);
			totalMonth=parseInt(totalMonth % 12);
			document.getElementById("expYears").value = parseInt(totalExp);
		}
			document.getElementById("expMonths").value = totalMonth;
		}else
		{
			 var validformat=/^\d{1,2}\/\d{1,2}\/\d{4}$/;
		     if (!validformat.test(endDate)){
		     alert('Invalid Date Format. Please correct date in DD/MM/YYYY format only.');	}
		     else
		     {
					d1=startDate.split('/');
				    d2=endDate.split('/');
				    var startDate1=new Date(d1[2],(d1[1]-1),d1[0]);
				    var endDate1=new Date(d2[2],(d2[1]-1),d2[0]); 	
				   
					if(startDate!="" && endDate!=""){
						if(startDate1.valueOf() > endDate1.valueOf()){
							alert("FromDate cannot be greater than ToDate");
							document.getElementById("industry_"+count).value="0";
							document.getElementById("industry_month_"+count).value = "0";
							document.getElementById("industryToDate_"+count).value = null;
							document.getElementById("industryToDate_"+count).select();
							getTotalYears();
							getTotalMonths();
							if(totalMonth>=12){
								getTotalYears();
								totalExp=totalExp+(totalMonth/12);
								totalMonth=parseInt(totalMonth % 12);
								document.getElementById("expYears").value = parseInt(totalExp);
							}
							document.getElementById("expMonths").value = totalMonth;
						}else{
					var args ="method=getStartDateAndEndDateCalculations&startDate="+startDate+"&endDate="+endDate;
					var url ="AjaxRequest.do";
					requestOperation(url,args,updateStartDateAndEndDateCalculations1);
					}
		}
				}
		}
	}
	function updateStartDateAndEndDateCalculations1(req){
		var count1 = document.getElementById("count").value;
		var responseObj = req.responseXML.documentElement;
		var childNodes = responseObj.childNodes;
		var items = responseObj.getElementsByTagName("option");
		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel") [0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue") [0].firstChild.nodeValue;
			document.getElementById("industry_"+count1).value=value;
			document.getElementById("industry_month_"+count1).value=label;
		}
		getTotalYears();
		getTotalMonths();
		if(totalMonth>=12){
			getTotalYears();
			totalExp=totalExp+(totalMonth/12);
			totalMonth=parseInt(totalMonth % 12);
			document.getElementById("expYears").value = parseInt(totalExp);
		}
		document.getElementById("expMonths").value = totalMonth;
	}



	function validateDate(date)
	{
		 var validformat=/^\d{1,2}\/\d{1,2}\/\d{4}$/;
	        var returnval=false;
	        if (!validformat.test(date))
	        alert('Invalid Date Format. Please correct date in DD/MM/YYYY format only.');
	}    
	</script>

</head>
<body>
<table width="98%" border="0">
<html:form action="/empResumeSubmission" enctype="multipart/form-data">
	<html:hidden property="formName" value="empResumeSubmissionForm" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<input type="hidden" id="count"/>
	<html:hidden property="serverDownMessage" styleId="serverDownMessage" name="empResumeSubmissionForm" />
  <tr>
    <td><span class="heading">
				<bean:message key="knowledgepro.admin.sec.EmployeeCategory"/>
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.employee.online.resume.submission"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
        <td width="1271" background="images/Tcenter.gif" class="body" >
			<div align="left">
				<strong class="boxheader"><bean:message key="knowledgepro.employee.online.resume.submission"/></strong>
			</div>
		</td>
       <td width="15" >
	   <img src="images/Tright_1_01.gif" width="9" height="29"/>	   </td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
	                 <tr>
	               	    <td height="20" colspan="6" align="left">
	               	    <div align="right" style="color:red"> <span class='MandatoryMark'>* Mandatory fields</span></div>
	               	    <div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
	               	    <div id="errorMessage">
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
						  </div>
	               	    </td>
	                 </tr>
	                  <tr>
						<td colspan="3">
							<div id=message>
							</div>
						</td>					
						</tr>
					   	<tr>
						<td valign="top" class="news" colspan="3">
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
								    <td class="row-odd" width="17.4%">
								    <div align="right"><span class="Mandatory">*</span>
								      <bean:message key="knowledgepro.employee.post.applied"/>
								    </div>
								    </td>
									<!-- <td  class="row-even" width="34.8%">
									 <html:select property="designationId">
									   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									   	<logic:notEmpty property="postAppliedMap" name="empResumeSubmissionForm">
									   		<html:optionsCollection property="postAppliedMap" label="value" value="key"/>
									   </logic:notEmpty>
								     </html:select></td>-->
								     
								      <td  class="row-even" width="34.8%" align="left">
								 	 <html:select property="designationId" styleId="designationId" onchange="postApplied()">
								 	 <html:option value="">- Select -</html:option>
								   		<html:option value="Teaching">Teaching</html:option>
								   		<html:option value="Non-Teaching">Non-Teaching</html:option>
								   		<logic:equal value="false" name="empResumeSubmissionForm" property="isCjc">
								   		<html:option value="Guest">Adjunct Faculty</html:option>
								   		</logic:equal>
										
								   	 </html:select>
								 </td>
								     <c:choose>
									 <c:when test="${!empResumeSubmissionForm.isCjc}">
								   	 <td class="row-odd" width="17.4%">
								   	 
								   	 <div id="department" align="right">
								   	 <span class="Mandatory">*</span>
										  <bean:message key="knowledgepro.usermanagement.userinfo.department"/>
									</div>
								   	 
								     </td>
									 <td  class="row-even" width="34.8%"  align="left">
									 
									 
									 <div id="department1">
									 <html:select property="departmentId" styleId="departmentId">
									   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									   	<logic:notEmpty property="departmentMap" name="empResumeSubmissionForm">
									   		<html:optionsCollection property="departmentMap" label="value" value="key"/>
									   </logic:notEmpty>
								     </html:select>
								     </div>
								     
								     </td>
								     </c:when>
								     <c:otherwise>
								      <td class="row-odd" width="17.4%">
								   	 
								   	 <div id="empSubject" align="right">
								   	 <span class="Mandatory">*</span>
										  <bean:message key="knowledgepro.admin.selectedSubjects"/>
									</div>
								   	 
								     </td>
								      <td  class="row-even" width="34.8%"  align="left">
								     <div id="empSubject1">
									 <html:select property="empSubjectId" styleId="empSubjectId" style="width:160px;">
									   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									   	<logic:notEmpty property="empSubjectMap" name="empResumeSubmissionForm">
									   		<html:optionsCollection property="empSubjectMap" label="value" value="key"/>
									   </logic:notEmpty>
								     </html:select>
								     </div></td>
								     </c:otherwise>
								     </c:choose>
								    
								  </tr>
								  
								  <tr>
							    	<td class="row-odd">
							    	<div align="right">
							      	<bean:message key="knowledgepro.employee.jobcode"/>
							    	</div>
							    	</td>
								 	<td  class="row-even"  align="left">
								 	<html:text property="jobCode" size="30" maxlength="10"></html:text>
									</td>
									<td class="row-odd">
							    	</td>
								 	<td  class="row-even" >
									</td>
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
					</tr>
					
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					
					
					<tr> 
					  	<td colspan="2" class="heading" align="left">
							<bean:message key="knowledgepro.employee.personal.details"/>
						</td>
					</tr>
					
					<tr>
						<td valign="top" class="news" colspan="3">
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
									<td class="row-odd">
									 <div align="right"><span class="Mandatory">*</span>
									     <bean:message key="knowledgepro.admin.name"/>
									  </div>
								    </td>
									<td  class="row-even"  align="left">
									<html:text property="name" styleId="name" size="45" maxlength="100" style="text-transform:uppercase;"></html:text>
									</td>
									<td class="row-odd" width="17.4%"> 
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.employee.gender.required" /></div>
							  	</td>
								<td height="17" class="row-even" align="left" width="34.8%">
									<nested:radio property="gender" value="MALE" name="empResumeSubmissionForm"><bean:message key="admissionForm.studentinfo.sex.male.text"/></nested:radio>
									<nested:radio property="gender" value="FEMALE" ><bean:message key="admissionForm.studentinfo.sex.female.text"/></nested:radio>
								</td> 	
								</tr>
								
								<!-- by venkat -->
								<logic:equal value="true" name="empResumeSubmissionForm" property="isCjc">
								<tr>
									<td class="row-odd">
									 <div align="right">
									     <bean:message key="knowledgepro.admission.fatherName"/>
									  </div>
								    </td>
									<td  class="row-even"  align="left">
									<html:text property="fatherName" styleId="fatherName" size="45" maxlength="100"></html:text>
									</td>
									<td class="row-odd" width="17.4%"> 
									<div align="right">
									<bean:message key="knowledgepro.admission.motherName" /></div>
							  	</td>
								<td height="17" class="row-even" align="left" width="34.8%">
									<html:text property="motherName" styleId="motherName" size="45" maxlength="100"></html:text>
								</td> 	
								</tr></logic:equal>
								<!-- by venkat -->
								
								<tr> 
							  	 <td class="row-odd" width="17.4%">
							  	 <div align="right"><span class="Mandatory">*</span>
							      	<bean:message key="knowledgepro.admin.nationality"/>
							     </div>
							     </td>
								 <td  class="row-even" width="34.8%"  align="left">
								 	 <html:select property="nationalityId">
								   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
								   	<logic:notEmpty property="nationalityMap" name="empResumeSubmissionForm">
								   		<html:optionsCollection property="nationalityMap" label="value" value="key"/>
								   </logic:notEmpty>
							     </html:select> 
								 </td>
							  	<td class="row-odd"> 
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.admin.dateofbirth" /></div>
							  	</td>
								<td   class="row-even"  align="left">
									<html:text name="empResumeSubmissionForm" property="dateOfBirth" styleId="dateOfBirth" size="10" maxlength="16"/>
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'empResumeSubmissionForm',
												// input name
												'controlname' :'dateOfBirth'
												});
										</script>
								</td> 
							  </tr>
							  
							  <tr> 
							  	 <td class="row-odd"> 
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="employee.info.personal.MaritalSts" /></div>
							  	</td>
								 <td  class="row-even"  align="left">
								 	 <html:select property="maritalStatus">
								 	 <html:option value="">- Select - </html:option>
								   		<html:option value="Single">Single </html:option>
								   		<html:option value="Married">Married</html:option>
										<html:option value="Divorcee">Divorcee </html:option>
								   		<html:option value="widowed">Widow</html:option>
										<html:option value="Other">Other </html:option>
							    	 </html:select>
								 </td>
							 <td height="20" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>
							<bean:message key="knowledgepro.admin.religion" />:</div></td>
							<td height="25" class="row-even"  align="left">
								<html:select property="religionId" styleId="religionId">
								<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									<logic:notEmpty property="religionMap" name="empResumeSubmissionForm">
								  		<html:optionsCollection property="religionMap" label="value" value="key"/>
								   </logic:notEmpty>
								 </html:select> 
							 </td>
							 </tr>
							  <tr> 
							  <td height="25" class="row-odd"><div align="right">Blood Group:</div></td>
								<td height="25" class="row-even"  align="left">
							<nested:select property="bloodGroup" styleId="bloodGroup" styleClass="combo" >
							<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							<html:option value="O+VE"><bean:message key="knowledgepro.admission.report.opositive" /></html:option>
							<html:option value="A+VE"><bean:message key="knowledgepro.admission.report.apositive" /></html:option>
							<html:option value="B+VE"><bean:message key="knowledgepro.admission.report.bpositive" /></html:option>
							<html:option value="AB+VE"><bean:message key="knowledgepro.admission.report.abpositive" /></html:option>
							<html:option value="O-VE"><bean:message key="knowledgepro.admission.report.onegitive" /></html:option>
							<html:option value="A-VE"><bean:message key="knowledgepro.admission.report.anegitive" /></html:option>
							<html:option value="B-VE"><bean:message key="knowledgepro.admission.report.bnegitive" /></html:option>
							<html:option value="AB-VE"><bean:message key="knowledgepro.admission.report.abnegitive" /></html:option>
							<html:option value="NOT KNOWN"><bean:message key="knowledgepro.admission.report.unknown" /></html:option>
							</nested:select>
							</td>
							<td class="row-odd"> 
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.employee.reservation.category" /></div>
							  		</td>
							  		
									<td class="row-even"  align="left">
									<nested:radio property="reservationCategory" value="GM" onclick="hideHandicappedDescription()">GM</nested:radio>
									<nested:radio property="reservationCategory" value="SC" onclick="hideHandicappedDescription()">SC</nested:radio>
									<nested:radio property="reservationCategory" value="ST" onclick="hideHandicappedDescription()">ST</nested:radio>
									<nested:radio property="reservationCategory" value="OBC" onclick="hideHandicappedDescription()">OBC</nested:radio>
									<nested:radio property="reservationCategory" value="Minority" onclick="hideHandicappedDescription()">Minority</nested:radio>
									<nested:radio property="reservationCategory" styleId="personWithDisability" value="Person With Disability" onclick="getPersonDisability()">Person With Disability</nested:radio>
									<div id="handicapped_description">
									<nested:text styleId="handicappedDescription" property="handicappedDescription"	maxlength="50" size="15"></nested:text></div>
									 <script type="text/javascript">
									    		 if(document.getElementById("personWithDisability").checked==true){
									    				 document.getElementById("handicappedDescription").style.display="block";
										    		 }else
											    		{
										    			 document.getElementById("handicappedDescription").style.display="none";
												    	}
									    		 
									    		 </script>
								</td> 
				 			 </tr>
							   <tr> 
							  	 <td class="row-odd"> 
									<div align="right"><span id="emailMandatorySymbol" class="Mandatory">*</span>
									<bean:message key="admissionFormForm.emailId" /></div>
							  	</td>
								 <td  class="row-even"  align="left">
								 	<html:text property="email" size="45" maxlength="50"></html:text>
								 </td>
							  	<td class="row-odd" > 
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.employee.mobile" /></div>
							  	</td>
								<td class="row-even"  align="left"> 
									<html:text property="mobileNo1" onkeypress="return isNumberKey(event)" size="45" maxlength="10"></html:text>
								</td>	
							  </tr>
							  
							  <logic:equal value="true" name="empResumeSubmissionForm" property="isCjc">
							  <tr>
							  <td class="row-odd" > 
									<div align="right">
									<bean:message key="knowledgepro.employee.onlineresume.alternatemobile" /> +91</div>
							  	</td>
								<td class="row-even"  align="left"> 
									<html:text property="alternativeMobile" onkeypress="return isNumberKey(event)" size="45" maxlength="10"></html:text>
								</td>
								<td class="row-odd" > </td>
								<td class="row-even" > </td>
							  </tr></logic:equal>
							  
								<tr  class="row-odd">
									<td > <div align="right" >
									<bean:message key="knowledgepro.employee.Home.Telephone" /></div>
							  		</td>
							  		<td  align="left" > 
									<bean:message key="knowledgepro.employee.county" />
									<bean:message key="knowledgepro.employee.state.code" />
									<bean:message key="knowledgepro.employee.Telephone" />
							  	</td>
							  	<td ><div align="right"> 
									<bean:message key="knowledgepro.employee.Work.Telephone" />
									</div>
							  		</td>
							  		<td  align="left"> 
									<bean:message key="knowledgepro.employee.county" />
									<bean:message key="knowledgepro.employee.state.code" />
									<bean:message key="knowledgepro.employee.Telephone" />
							  		</td>
							  	</tr>
							  	<tr >
							  	<td class="row-odd" ></td>
									<td class="row-even"  align="left"> 
									<html:text property="phNo1" onkeypress="return isNumberKey(event)" size="4" maxlength="6"></html:text>
									<html:text property="phNo2" onkeypress="return isNumberKey(event)" size="4" maxlength="6"></html:text>
									<html:text property="phNo3" onkeypress="return isNumberKey(event)" size="10" maxlength="10"></html:text>
									</td>
									
									<td class="row-odd"></td>
									<td class="row-even"  align="left"> 
									<html:text property="workPhNo1" onkeypress="return isNumberKey(event)" size="4" maxlength="6"></html:text>
									<html:text property="workPhNo2" onkeypress="return isNumberKey(event)" size="4" maxlength="6"></html:text>
									<html:text property="workPhNo3" onkeypress="return isNumberKey(event)" size="10" maxlength="10"></html:text>
									</td>
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
					</tr>
					
					
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					
					
					<tr> 
					  	<td colspan="2" class="heading" align="left">
							<bean:message key="admissionForm.studentinfo.currAddr.label"/>
						</td>
					</tr>
					
					
					
					<tr>
						<td valign="top" class="news" colspan="3">
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
									   	 	<td class="row-odd" width="17.4%">
									   	 		<div align="right"><span class="Mandatory">*</span>
									     	 	<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     	 	</div>
									    	</td>
										 	<td  class="row-even" width="34.8%"  align="left">
												 <html:text property="currentAddressLine1" styleId="currentAddressLine1" size="35" maxlength="35"></html:text>
											</td>
											
											<td class="row-odd" width="17.4%"> 
												<div align="right">
												<bean:message key="admissionForm.studentinfo.addrs2.label"/>
												</div>
											</td>
											<td class="row-even" width="34.8%"  align="left">
												 <html:text property="currentAddressLine2" styleId="currentAddressLine2" size="35" maxlength="40"></html:text>
										 	</td>
										</tr>
										 
										<tr>
										 <!-- 	<td class="row-odd">
										 		<div align="right"> 
										 		<bean:message key="admissionForm.studentinfo.addrs3.label"/>
										 		</div>
										 	</td>
										 	<td class="row-even"  align="left"> 
												 <html:text property="currentAddressLine3" styleId="currentAddressLine3" size="35" maxlength="50"></html:text>
											</td>-->
									  	<td class="row-odd" >
									  	 <div align="right"><span class="Mandatory">*</span>
									      	<bean:message key="employee.info.contact.City"/>
									      </div>
									     </td>
										 <td  class="row-even"  align="left">
										 	<html:text property="currentCity" styleId="currentCity" size="35" maxlength="50"></html:text>
										 </td>
									  	
									   	 <td class="row-odd">
									   	 <div align="right"><span class="Mandatory">*</span>
											  <bean:message key="knowledgepro.admin.state.report"/>
										</div>
									     </td>
										 <td  class="row-even"  align="left">
										 <table><tr><td  align="left">
										 <html:select property="currentState" styleId="currentState" onchange="getOtherCurrentState()">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="currentStateMap" name="empResumeSubmissionForm">
										   		<html:optionsCollection property="currentStateMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select> 
									     <input type="hidden" id="tempState" name="tempState" value='<bean:write name="empResumeSubmissionForm" property="currentState"/>' />
									     </td>
									     <td align="left">
									     <div id="otehrState">
									     	<html:text property="otherCurrentState" name="empResumeSubmissionForm" size="35" maxlength="50"></html:text>
									     </div>
									     </td>
									     
									     </tr></table>
									     </td>
									     </tr>
										
									   <tr>
									      <td class="row-odd">
									   	 	<div align="right"><span class="Mandatory">*</span>
											  <bean:message key="knowledgepro.admin.country.report"/>
											</div>
									     </td>
									     
									     <td  class="row-even"  align="left">
											<html:select property="currentCountryId" styleId="currentCountryId" onchange="getCurrentStateByCountry(this.value,'currentState')">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="currentCountryMap" name="empResumeSubmissionForm">
										   		<html:optionsCollection property="currentCountryMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select> 
									     
										</td>
									  
									   <td class="row-odd" align="right">
									      	<bean:message key="knowledgepro.usermanagement.userinfo.pincode"/>
									     </td>
										 <td  class="row-even"  align="left">
										 	<html:text property="currentZipCode" styleId="currentZipCode" size="15" maxlength="10"></html:text>
										 </td>
									  	 
									  	 <td class="row-odd">
									  	 
									     </td>
										 <td  class="row-even">
										 </td>
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
					</tr>
					
						
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					
					<tr>
                  	<td colspan="2" class="heading" align="left" >&nbsp;&nbsp;<bean:message key="knowledgepro.applicationform.sameaddr.label"/>
                      <html:radio property="sameAddress" styleId="sameAddr" value="true" onclick="disableAddress()"><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
						<html:radio property="sameAddress" styleId="DiffAddr" value="false" onclick="enableAddress()"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio></td>
                	</tr>
						<tr>
							<td colspan="2" class="heading" align="left" >&nbsp;
							<div id="currLabel">
							<bean:message
								key="admissionForm.studentinfo.permAddr.label" />
							</div>
							</td>
						</tr>
						
						
						<tr>
						<td valign="top" class="news" colspan="3" >
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0" id="currTable">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
								
									  	<tr>
									   	 	<td class="row-odd" width="17.4%">
									   	 		<div align="right"><span class="Mandatory">*</span>
									      		<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     		</div>
									    	</td>
										 	<td  class="row-even" width="34.8%"  align="left">
												 <html:text property="addressLine1" styleId="addressLine1" size="35" maxlength="35"></html:text>
											</td>
											<td class="row-odd" width="17.4%"> 
												<div align="right">
									      		<bean:message key="admissionForm.studentinfo.addrs2.label"/>
									     		</div>
											</td>
											<td class="row-even" width="34.8%"  align="left">
												 <html:text property="addressLine2" styleId="addressLine2" size="35" maxlength="40"></html:text>
										 	</td>
										</tr>
										 
										<tr>
										 	<!-- <td class="row-odd"> 
										 		<div align="right">
									      		<bean:message key="admissionForm.studentinfo.addrs3.label"/>
									     		</div>
										 	</td>
										 	<td class="row-even"  align="left"> 
												 <html:text property="addressLine3" styleId="addressLine3" size="35" maxlength="40"></html:text>
											</td>-->
									  	 <td class="row-odd">
									  	 <div align="right"><span class="Mandatory">*</span>
									      	<bean:message key="employee.info.contact.City"/>
									      </div>
									     </td>
										 <td  class="row-even"  align="left" >
										 	<html:text property="city" styleId="city" size="35" maxlength="50"></html:text>
										 </td>
									  	
									   	 <td class="row-odd">
									   	 <div align="right"><span class="Mandatory">*</span>
											  <bean:message key="knowledgepro.admin.state.report"/>
										</div>
									     </td>
										 <td  class="row-even" >
										 <table><tr><td  align="left">
										 <html:select property="stateId" styleId="stateId" onchange="getOtherPermanentState()">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="stateMap" name="empResumeSubmissionForm">
										   		<html:optionsCollection property="stateMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select> 
									     <input type="hidden" id="tempPermanentState" name="tempPermanentState" value='<bean:write name="empResumeSubmissionForm" property="stateId"/>' />
									     </td>
									     <td align="left">
									     <div id="otehrPermState"  >
									     	<html:text property="otherPermanentState" name="empResumeSubmissionForm" size="35" maxlength="50"></html:text>
									     </div>
									     </td>
									     </tr></table>
									     </td>
									     </tr>
										
									   <tr>
									   	 <td class="row-odd">
									   	 	<div align="right"><span class="Mandatory">*</span>
											  <bean:message key="knowledgepro.admin.country.report"/>
											</div>
									     </td>
										 <td  class="row-even"  align="left">
											<html:select property="countryId" styleId="countryId" onchange="getStateByCountry(this.value,'stateId')">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="countryMap" name="empResumeSubmissionForm">
										   		<html:optionsCollection property="countryMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select> 
										</td>
									  
									  <td class="row-odd" align="right">
									      	<bean:message key="knowledgepro.usermanagement.userinfo.pincode"/>
									     </td>
										 <td  class="row-even"  align="left">
										 	<html:text property="permanentZipCode" styleId="permanentZipCode" size="15" maxlength="10"></html:text>
										 </td>
									  	 
										 
										 <td class="row-odd">
									  	 
									     </td>
										 <td  class="row-even">
										 </td>
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
					</tr>
					
					
						
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					
					
		
				   	<tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.professional.exp"/>
					</td>
					</tr>
					
					<tr>
						<td valign="top" class="news" >
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
									<table width="100%" cellspacing="1" cellpadding="2" >
						
									
	

		<logic:equal value="true" name="empResumeSubmissionForm" property="isCjc">
				<tr>
					<td class="row-odd" width="10%" align="right"> 
						<div >
							<bean:message key="knowledgepro.employee.currently.working" /></div>
				    </td>
										<td class="row-even" width="20%" align="left">
											<nested:radio property="currentlyWorking" styleId="cyes" value='YES' onclick="radioShow()">
												<bean:message key="knowledgepro.yes" />
												</nested:radio> 
												<nested:radio property="currentlyWorking" styleId="cno" value='NO' onclick="radioHide()">
												<bean:message key="knowledgepro.no" />
											</nested:radio>									
										</td> 
					
										<td class="row-odd"  align="right"> 
									        <div id="dman"><span class="Mandatory">*</span>
											<bean:message key="knowledgepro.usermanagement.userinfo.designation" /></div>
											
											<div id="deman">
											<bean:message key="knowledgepro.usermanagement.userinfo.designation" /></div>
									  		</td>
									       <td  class="row-even"  align="left">
									       <html:text property="designationPfId" size="35" maxlength="50"></html:text>
									     </td>
									     <td class="row-odd"  align="right"> 
											<div id="orgman"><span class="Mandatory">*</span>
											<bean:message key="knowlegepro.employee.address.organization" /></div>
											
											<div id="orgsman">
											<bean:message key="knowlegepro.employee.address.organization" /></div>
									  		</td>
										<td class="row-even"  align="left">
											<html:text property="orgAddress" size="35" maxlength="100"></html:text>
										</td>
					    </tr>
	
						<tr>
										<td class="row-even"  height="25" width="10%"> 
										</td>
										<td class="row-odd"  height="25" align="left" width="20%"> 
											<bean:message key="knowledgepro.employee.FromDate" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									  		
											<bean:message key="knowledgepro.employee.ToDate"/>
										</td>
										<td class="row-odd"  height="25" align="left" > 
											<bean:message key="knowledgepro.employee.years" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="knowledgepro.employee.months"/>
									  		</td>
										
										<!--  <td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.employee.months"/>
										</td>-->
										<td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.admin.sec.Designation"/>
										</td>
										<td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.institution.col"/>
										</td>
										<td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.gross.salary"/>
										</td>
						</tr>
		</logic:equal>
										
							
		<logic:equal value="false" name="empResumeSubmissionForm" property="isCjc">
					<tr>
						<td class="row-odd" colspan="3" align="right"> 
							<div >
											<bean:message key="knowledgepro.employee.currently.working" /></div>
							</td>
										<td class="row-even" colspan="4" align="left">
											<nested:radio property="currentlyWorking" styleId="cyes" value='YES' onclick="radioShow()">
												<bean:message key="knowledgepro.yes" />
												</nested:radio> 
												<nested:radio property="currentlyWorking" styleId="cno" value='NO' onclick="radioHide()">
												<bean:message key="knowledgepro.no" />
											</nested:radio>									
									</td> 
						</tr>
						<tr>
							    <td class="row-odd" width="17.4%" align="right"> 
									        <div id="dman"><span class="Mandatory">*</span>
											<bean:message key="knowledgepro.usermanagement.userinfo.designation" /></div>
											
											<div id="deman">
											<bean:message key="knowledgepro.usermanagement.userinfo.designation" /></div>
									</td>
									       <td  class="row-even"  colspan="2" width="17.4%"  align="left">
									       <html:text property="designationPfId" size="35" maxlength="50"></html:text>
									     </td>
									     <td class="row-odd" width="10%" align="right"> 
											<div id="orgman"><span class="Mandatory">*</span>
											<bean:message key="knowlegepro.employee.address.organization" /></div>
											
											<div id="orgsman">
											<bean:message key="knowlegepro.employee.address.organization" /></div>
									  		</td>
										<td class="row-even"  colspan="3"  align="left">
											<html:text property="orgAddress" size="35" maxlength="100"></html:text>
										</td>
									</tr>
									
							<tr>
										<td class="row-odd"  height="25" > 
										</td>
										<td class="row-odd"  height="25" align="left" > 
											<bean:message key="knowledgepro.employee.Functional.area" />
									  		</td>
									  	<td class="row-odd" width="5%"  height="25" align="left" >
 											From Date 
 											</td>
										<td class="row-odd" width="5%" height="25" align="left" > 
 											To Date
 											</td>
										<td class="row-odd"  height="25" align="left" > 
											<bean:message key="knowledgepro.employee.years" />
									  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<bean:message key="knowledgepro.employee.months"/>
										</td>
										<td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.admin.sec.Designation"/>
										</td>
										<td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.institution.col"/>
										</td>
							</tr>
						</logic:equal>
										
						

				<html:hidden property="teachingExpLength" name="empResumeSubmissionForm" styleId="teachingExpLength"/>
				<html:hidden property="industryExpLength" name="empResumeSubmissionForm" styleId="industryExpLength"/>
				<logic:notEmpty property="empResumeSubmissionTo.teachingExperience" name="empResumeSubmissionForm">     
				<nested:iterate property="empResumeSubmissionTo.teachingExperience" name="empResumeSubmissionForm" id="teachingExp" indexId="count" >
					<logic:equal value="true" name="empResumeSubmissionForm" property="isCjc">
							<tr>
									<c:choose>
										<c:when test="${count==0}">
											<td class="row-odd" width="10%"> 
												<div align="right">
												<bean:message key="knowledgepro.employee.teaching.experience" /></div>
										  	</td>
										</c:when>
										<c:otherwise>
											<td class="row-odd" width="10%"> 
										  	</td>
										</c:otherwise>
									</c:choose>
									<%
										String styleDate1 = "teachingFromDate_" + count;
									%>
									<%
										String styleDate2 = "teachingToDate_" + count;
										%>
									<td class="row-even" align="left" width="20%">
								               <nested:text property="teachingFromDate" styleId="<%=styleDate1%>" styleClass="TextBox" size="10"  maxlength="30" ></nested:text>
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'empResumeSubmissionForm',
												// input name
												'controlname' :'<%=styleDate1%>'
												});
										</script>
										
								               <nested:text property="teachingToDate" styleId="<%=styleDate2%>" styleClass="TextBox" size="10"  maxlength="30" ></nested:text>
										<script
												language="JavaScript">
												new tcal( {
												// form name
												'formname' :'empResumeSubmissionForm',
												// input name
												'controlname' :'<%=styleDate2%>'
												});
										</script>
									</td>
									
									
									<%String teaching="teach_"+String.valueOf(count);%>
			                            <td class="row-even" align="left" width="17.4%">
								              <nested:text property="teachingExpYears" styleClass="TextBox" size="5" maxlength="5" onkeyup="getYears(this)"   styleId="<%=teaching%>" onkeypress="return isNumberKey(event)"></nested:text>
										
									<%String teachingMonth="teach_month_"+String.valueOf(count);%>	
										
								              <nested:text property="teachingExpMonths" styleClass="TextBox" size="5" maxlength="5" onkeyup="getMonths(this)"  styleId="<%=teachingMonth%>" onkeypress="return isNumberKey(event)"></nested:text>
										</td>
										<td class="row-even" align="left">
								              <nested:text property="currentTeachnigDesignation" styleClass="TextBox" size="20" maxlength="100"></nested:text>
										</td>
										
										<td class="row-even" align="left">
								               <nested:text property="currentTeachingOrganisation" styleClass="TextBox" size="20" maxlength="100"></nested:text>
										</td>
										<td class="row-even" align="left">
								               <nested:text property="teachingGrossSalary" styleClass="TextBox" size="20" maxlength="100"></nested:text>
										</td>
										<c:if test="${empResumeSubmissionForm.teachingExpLength!=count}">
										<td class="row-even" align="left">
										</td>
										</c:if>
							</tr>
					</logic:equal>
<!-- CJC professional Experience code ends-->								  
<!-- Christ professional Experience code -->
									<logic:equal value="false" name="empResumeSubmissionForm" property="isCjc">
									<tr>
									<c:choose>
										<c:when test="${count==0}">
											<td class="row-odd" width="12%"> 
												<div align="right">
												<bean:message key="knowledgepro.employee.teaching.experience" /></div>
										  	</td>
										  	<td  class="row-even" width="15%">
										 <html:select property="empSubjectAreaId" styleId="empSubjectAreaId">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="subjectAreaMap" name="empResumeSubmissionForm">
										   		<html:optionsCollection property="subjectAreaMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select>
										 </td>
										</c:when>
										<c:otherwise>
											<td class="row-odd" width="12%"> 
										  	</td>
										  	<td class="row-even" width="15%"> 
										  	</td>
										</c:otherwise>
									</c:choose>
									<%
										String teachingFromDate = "teachingFromDate_" + count;
									%>
									<%
										String teachingToDate = "teachingToDate_" + count;
										%>
										<% String method = "calculateDates("+count+")"; %>
									<td class="row-even" align="left" width="11%">
								               <nested:text property="teachingFromDate" styleId="<%=teachingFromDate%>" styleClass="TextBox" size="10"  maxlength="30" onchange="validateDate(this.value);" ></nested:text>
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'empResumeSubmissionForm',
												// input name
												'controlname' :'<%=teachingFromDate%>'
												});
										</script>
										</td>
									<td class="row-even" align="left" width="11%">
								               <nested:text property="teachingToDate" styleId="<%=teachingToDate%>" styleClass="TextBox" size="10"  maxlength="30" onchange="<%=method %>" ></nested:text>
										<script
												language="JavaScript">
												new tcal( {
												// form name
												'formname' :'empResumeSubmissionForm',
												// input name
												'controlname' :'<%=teachingToDate%>'
												});
										</script>
									</td>
									<%String teaching="teach_"+String.valueOf(count);%>
									
			                            <td class="row-even" align="left" width="12%">
								              <nested:text property="teachingExpYears" styleClass="TextBox" size="5" maxlength="5" styleId="<%=teaching%>" onkeypress="return false;"></nested:text>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<%String teachingMonth="teach_month_"+String.valueOf(count);%>	
								              <nested:text property="teachingExpMonths" styleClass="TextBox" size="5" maxlength="5" styleId="<%=teachingMonth%>" onkeypress="return false;"></nested:text>
										</td>
										<td class="row-even" align="left">
								              <nested:text property="currentTeachnigDesignation" styleClass="TextBox" size="25" maxlength="100"></nested:text>
										</td>
										
										<td class="row-even" align="left">
								               <nested:text property="currentTeachingOrganisation" styleClass="TextBox" size="25" maxlength="100"></nested:text>
										</td>
										<c:if test="${empResumeSubmissionForm.teachingExpLength!=count}">
										<td class="row-even" align="left">
										</td>
										</c:if>
										</tr>
									</logic:equal>
																
									
			                         </nested:iterate>
			                        
			                         <logic:equal value="true" name="empResumeSubmissionForm" property="isCjc">
			                         <tr>
			                         		<td  class="row-even" align="center" colspan="6">
				                        	<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        	<c:if test="${empResumeSubmissionForm.teachingExpLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        	</c:if>
										</td>
										</tr>
										
										<tr>
			                         	<td class="row-odd"  height="25" > 
										</td>
										<%-- <td class="row-odd"  height="25" align="left" > 
											<bean:message key="knowledgepro.employee.Functional.area" />
									  		</td> --%>
									  		<td class="row-odd"  height="25" align="left" width="20%"> 
											<bean:message key="knowledgepro.employee.FromDate" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									  		
											<bean:message key="knowledgepro.employee.ToDate"/>
										</td>
										<%-- <td class="row-odd"  height="25" align="left" > 
											<bean:message key="knowledgepro.employee.years" />
									  		</td>
										
										<td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.employee.months"/>
										</td> --%>
										
										<td class="row-odd"  height="25" align="left" > 
											<bean:message key="knowledgepro.employee.years" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="knowledgepro.employee.months"/>
									  		</td>
										
										<td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.admin.sec.Designation"/>
										</td>
										<td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.institution.col"/>
										</td> 
										<td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.gross.salary"/>
										</td>
			                         </tr>
										</logic:equal>
									<logic:equal value="false" name="empResumeSubmissionForm" property="isCjc">
			                 		<tr>
			                         	<td  class="row-even" align="center" colspan="7">
				                        	<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        	<c:if test="${empResumeSubmissionForm.teachingExpLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        	</c:if>
				                        	<c:if test="${empResumeSubmissionForm.teaching == 'true'}">
				                        	<script type="text/javascript">
				                        	var count = document.getElementById("teachingExpLength").value;
				                        	calculateDates(count);
				                        	</script>
				                        	</c:if>
										</td> 
	                         	 </tr>
			                         <tr>
			                         	<td class="row-odd"  height="25" > 
										</td>
										<td class="row-odd"  height="25" align="left" > 
											<bean:message key="knowledgepro.employee.Functional.area" />
									  		</td>
									  		<td class="row-odd" width="11%"  height="25" align="left" >
 											From Date 
											</td>
											<td class="row-odd" width="11%"  height="25" align="left" >
 											To Date
 											</td>
										<td class="row-odd"  height="25" align="left" > 
											<bean:message key="knowledgepro.employee.years" />
									  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<bean:message key="knowledgepro.employee.months"/>
										</td>
										<td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.admin.sec.Designation"/>
										</td>
										<td class="row-odd"  align="left" > 
											<bean:message key="knowledgepro.institution.col"/>
										</td> 
			                         </tr>
									</logic:equal>
									 </logic:notEmpty>
									
									<logic:notEmpty property="empResumeSubmissionTo.experiences" name="empResumeSubmissionForm">          
									<nested:iterate property="empResumeSubmissionTo.experiences" name="empResumeSubmissionForm" id="experience" indexId="counter">
									<logic:equal value="true" name="empResumeSubmissionForm" property="isCjc">
									<tr>
									<c:choose>
									<c:when test="${counter==0}">
										<td class="row-odd"> 
											<div align="right">
											<bean:message key="knowledgepro.employee.industry.exp" /></div>
									  	</td>
									</c:when>
									<c:otherwise>
										<td class="row-odd" height="20"> 
									  	</td>
									</c:otherwise>
									</c:choose>
									<%
										String styleDate1 = "industryFromDate_" + counter;
									%>
									<td class="row-even" align="left">
								               <nested:text property="industryFromDate" styleId="<%=styleDate1%>" styleClass="TextBox" size="10"  maxlength="30" ></nested:text>
										<script
												language="JavaScript">
												new tcal( {
												// form name
												'formname' :'empResumeSubmissionForm',
												// input name
												'controlname' :'<%=styleDate1%>'
												});
										</script>
										
										<%
										String styleDate2 = "industryToDate_" + counter;
										%>
									
								               <nested:text property="industryToDate" styleId="<%=styleDate2%>" styleClass="TextBox" size="10"  maxlength="30" ></nested:text>
										<script
												language="JavaScript">
												new tcal( {
												// form name
												'formname' :'empResumeSubmissionForm',
												// input name
												'controlname' :'<%=styleDate2%>'
												});
										</script>
									</td>
									<%String industry="industry_"+String.valueOf(counter);%>
			                            <td class="row-even" align="left">
								               <nested:text property="industryExpYears" styleClass="TextBox" size="5"  maxlength="5" onkeyup="getYears(this)" onblur="getYears(this)" styleId="<%=industry%>" onkeypress="return isNumberKey(event)"></nested:text>
									
									<%String industryMonth="industry_month_"+String.valueOf(counter);%>	
										
								               <nested:text property="industryExpMonths" styleClass="TextBox" size="5"   maxlength="5" onkeyup="getMonths(this)" onblur="getMonths(this)" styleId="<%=industryMonth%>" onkeypress="return isNumberKey(event)"></nested:text>
										</td>
										
										<td class="row-even" align="left">
								               <nested:text property="currentDesignation" styleClass="TextBox" size="20" maxlength="100"></nested:text>
										</td>
										
										<td class="row-even" align="left">
								               <nested:text property="currentOrganisation" styleClass="TextBox" size="20" maxlength="100"></nested:text>
										</td>
										<td class="row-even" align="left">
								               <nested:text property="industryGrossSalary" styleClass="TextBox" size="20" maxlength="20"></nested:text>
										</td>
										<c:if test="${empResumeSubmissionForm.industryExpLength!=counter}">
										<td class="row-even" align="left">
										</td>
										</c:if>
										</tr>
										</logic:equal>
								<logic:equal value="false" name="empResumeSubmissionForm" property="isCjc">
									<tr>
									<c:choose>
									<c:when test="${counter==0}">
										<td class="row-odd"> 
											<div align="right">
											<bean:message key="knowledgepro.employee.industry.exp" /></div>
									  	</td>
									  	 <td  class="row-even"  align="left">
										 <nested:text name="empResumeSubmissionForm"  property="industryFunctionalArea" styleClass="TextBox" size="20"  maxlength="50" styleId="industryFunctionalArea">
									     </nested:text>
										 </td>
									</c:when>
									<c:otherwise>
										<td class="row-odd" height="20"> 
									  	</td>
									  	<td class="row-even" height="20"> 
									  	</td>
									</c:otherwise>
									</c:choose>
									<%
										String industryFromDate = "industryFromDate_" + counter;
									%>
									<% String method1 = "calculateDates1("+counter+")"; %>
									<td class="row-even" align="left">
								               <nested:text property="industryFromDate" styleId="<%=industryFromDate%>" styleClass="TextBox" size="10"  maxlength="30" onchange="validateDate(this.value);"></nested:text>
										<script
												language="JavaScript">
												new tcal( {
												// form name
												'formname' :'empResumeSubmissionForm',
												// input name
												'controlname' :'<%=industryFromDate%>'
												});
										</script>
									</td>
									<td class="row-even" align="left">
										<%
										String industryToDate = "industryToDate_" + counter;
										%>
									
								               <nested:text property="industryToDate" styleId="<%=industryToDate%>" styleClass="TextBox" size="10"  maxlength="30" onchange="<%=method1 %>"></nested:text>
										<script
												language="JavaScript">
												new tcal( {
												// form name
												'formname' :'empResumeSubmissionForm',
												// input name
												'controlname' :'<%=industryToDate%>'
												});
										</script>
									</td>
									<%String industry="industry_"+String.valueOf(counter);%>
			                            <td class="row-even" align="left">
								               <nested:text property="industryExpYears" styleClass="TextBox" size="5"  maxlength="5"   styleId="<%=industry%>" onkeypress="return false;"></nested:text>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<%String industryMonth="industry_month_"+String.valueOf(counter);%>	
								               <nested:text property="industryExpMonths" styleClass="TextBox" size="5"   maxlength="5"   styleId="<%=industryMonth%>" onkeypress="return false;"></nested:text>
										</td>
										<td class="row-even" align="left">
								               <nested:text property="currentDesignation" styleClass="TextBox" size="25" maxlength="100"></nested:text>
										</td>
										
										<td class="row-even" align="left">
								               <nested:text property="currentOrganisation" styleClass="TextBox" size="25" maxlength="100"></nested:text>
										</td>
										<c:if test="${empResumeSubmissionForm.industryExpLength!=counter}">
										<td class="row-even" align="left">
										</td>
										</c:if>
										</tr>
										</logic:equal>
			                         </nested:iterate>
			                         <logic:equal value="true" name="empResumeSubmissionForm" property="isCjc">
			                         <tr>
			                         		<td  class="row-even" align="center" colspan="6">
				                        	<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        	<c:if test="${empResumeSubmissionForm.teachingExpLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        	</c:if>
										</td>
										</tr>
										</logic:equal>
									<logic:equal value="false" name="empResumeSubmissionForm" property="isCjc">
			                         <tr>
			                         	<td  class="row-even" align="center" colspan="7">
			                           <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('resetExperienceInfo','ExpAddMore'); return false;"></html:submit>
			                           <c:if test="${empResumeSubmissionForm.industryExpLength>0}">
				                        	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        </c:if>
				                        <c:if test="${empResumeSubmissionForm.industry == 'true'}">
				                        <script type="text/javascript">
			                        	var count = document.getElementById("industryExpLength").value;
			                        	calculateDates1(count);
			                        	</script>
			                        	</c:if>
										</td> 
										</tr>
										</logic:equal>
									</logic:notEmpty>
									
									
									<tr>
									  	<td class="row-odd"> 
									  		<div align="right">
											<bean:message key="knowledgepro.employee.total.exp"/>
											</div>
									  	</td>
										<td class="row-even" colspan="2"  align="left">
											 <html:text styleId="expYears" name="empResumeSubmissionForm" property="expYears" size="3"  maxlength="3" onkeypress="return false;"/>&nbsp;&nbsp;Years&nbsp;&nbsp;
											 <html:text styleId="expMonths" name="empResumeSubmissionForm" property="expMonths" size="3" maxlength="3" onkeypress="return false;"/>&nbsp;&nbsp;Months
										</td> 
										<td class="row-odd"></td>
										<td class="row-even" colspan="3"></td>
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
					</tr>
				<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
				</tr>	
						
				<tr >
				  <td colspan="2" class="heading" align="left">
				  	<div align="left" id="desiredJobHeading">
				  	<bean:message key="knowledgepro.employee.desired.job"/>
				  	</div>
				  </td>
			  	</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0" id="desiredJob">
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
									  	<td class="row-odd" width="17.4%"> 
									  		<div align="right"><span class="Mandatory">*</span> 
											<bean:message key="knowledgepro.employee.job.type"/>
											</div>
									  	</td>
										<td class="row-even" width="34.8%"  align="left">
											<nested:radio property="empJobTypeId" value="Permanent">Permanent</nested:radio>
											<nested:radio property="empJobTypeId" value="Temporary">Temporary</nested:radio>
											<nested:radio property="empJobTypeId" value="Either">Either</nested:radio>
										</td> 
										
									  	<td class="row-odd" width="17.4%"> 
									  		<div align="right"><span class="Mandatory">*</span> 
											<bean:message key="employee.info.job.empStatus"/>
											</div>
									  	</td>
										<td class="row-even" width="34.8%"  align="left">
											<nested:radio property="employmentStatus" value="Full Time">Full Time</nested:radio>
											<nested:radio property="employmentStatus" value="Part Time">Part Time</nested:radio>
											<nested:radio property="employmentStatus" value="Either">Either</nested:radio>
										</td> 
									  </tr>
									   
									  <tr>
									  	<td class="row-odd"> 
									  		<div align="right"><span class="Mandatory">*</span>
											<bean:message key="knowledgepro.employee.expected.salary"/>
											</div>
									  	</td>
										<td class="row-even"  align="left">
										<html:select property="expectedSalaryLakhs">
											<html:option value="">Lakhs</html:option>
											<logic:notEmpty property="lakhsAndThousands" name="empResumeSubmissionForm">
													<html:optionsCollection property="lakhsAndThousands" value="key" label="value"/>					
											</logic:notEmpty>
										</html:select>
									    <html:select property="expectedSalaryThousands">
											<html:option value="">Thousands</html:option>
											<logic:notEmpty property="lakhsAndThousands" name="empResumeSubmissionForm">
													<html:optionsCollection property="lakhsAndThousands" value="key" label="value"/>					
											</logic:notEmpty>
										</html:select>
										(per annum)
										</td> 
										<td class="row-odd"></td>
										<td class="row-even"></td>
									  		
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
					</tr>
					
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
				</tr>
				
				<tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employye.educational.details"/>
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
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
								<td class="row-odd" width="13%">
							  	 	<div align="right"><span class="Mandatory">*</span>
							      	<bean:message key="knowledgepro.employee.education.qualification.level"/>
							      	</div>
							    </td>
								<td  class="row-even" width="17.4%" colspan="2"  align="left">
									 <html:select property="qualificationId" styleId="qualificationId">
									   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									   	<logic:notEmpty property="qualificationMap" name="empResumeSubmissionForm">
									   		<html:optionsCollection property="qualificationMap" label="value" value="key"/>
									   </logic:notEmpty>
								     </html:select>
								 </td>
								
									  									 
									  <td class="row-odd"> 
									  			<div align="left">
												<bean:message key="knowledgepro.employee.eligibility.test"/>
												</div>
									  		</td>
									<td class="row-even" colspan="5"  align="left">
												<logic:notEmpty name="empResumeSubmissionForm" property="eligibilityList">
													<logic:iterate id="eTest" name="empResumeSubmissionForm" property="eligibilityList" indexId="count">
														<input	type="hidden"	name="eligibilityList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='eTest' property='tempChecked'/>" />
														<input type="checkbox" name="eligibilityList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>" value="<bean:write name="eTest" property="eligibilityTest"/>" onclick="removeTextField(<c:out value='${count}'/>,this.value)"/>
														<bean:write name="eTest" property="eligibilityTest"/>
														<script type="text/javascript">
																		var testId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																		if(testId == "on") {
																			document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
												    		 <script type="text/javascript">
												    		 if(document.getElementById("<c:out value='${count}'/>").value=="OTHER"){
																			
													    		 if(document.getElementById("<c:out value='${count}'/>").checked==true){
													    			 document.getElementById("otherEligibilityTest").style.display="block";
														    		 }else{
														    			 document.getElementById("otherEligibilityTest").style.display="none";
																    }
													    		}
												    		 
												    		 </script>
													</logic:iterate>
												</logic:notEmpty>
												<div id="otherEligibilityTest">
									     			<html:text property="otherEligibilityTestValue" styleId="otherEligibilityTestValue" name="empResumeSubmissionForm" size="10" maxlength="50"></html:text>
									    		 </div>
												
											</td> 
												
											
									 
								</tr> 
									<tr>
									<td class="row-odd" width="17.4%" align="right">
										<bean:message key="knowledgepro.employee.education.qualification"/>
									</td>
									<td class="row-odd" align="left" width="17.4%">
										<bean:message key="knowledgepro.exam.course"/>
									</td>
									<td class="row-odd" align="left" width="17.4%">
										<bean:message key="knowledgepro.exam.specialization"/>
									</td>
									<td class="row-odd" align="left" width="17.4%">
										<bean:message key="knowledgepro.employee.yeat.completion"/>
									</td>
									<td class="row-odd" align="left" width="17.4%">
										<bean:message key="knowledgepro.employee.grade.percentage"/>
									</td>
									<td class="row-odd" align="left" width="17.4%">
										<bean:message key="knowledgepro.employee.institute.univetsity"/>
									</td>
									<td class="row-odd">
									</td>
								</tr>
								<logic:notEmpty property="empResumeSubmissionTo.empQualificationFixedTo" name="empResumeSubmissionForm">
									<nested:iterate id="qualificationTo" property="empResumeSubmissionTo.empQualificationFixedTo" name="empResumeSubmissionForm" indexId="yr">
									<tr>
									<td class="row-odd">
									<div align="right">
										<bean:write name="qualificationTo"  property="qualification"/>
									</div>
									</td>
									<%
										String stylecourse = "course_" + yr;
									%>
									<td class="row-even" align="left">
										<nested:text property="course" styleId="<%=stylecourse%>" size="20" maxlength="100"></nested:text>
									</td>
									<td class="row-even" align="left">
										<nested:text property="specialization" size="20" maxlength="50"></nested:text>
									</td>
									<%String dynaYearId="YOP"+yr; 
									%>
									<td class="row-even" align="left">
										<c:set var="dyopid"><%=dynaYearId %></c:set>
											<nested:select property="yearOfComp"  styleId='<%=dynaYearId %>' styleClass="comboMedium" >
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
													<cms:renderEmployeeYear normalYear="true"></cms:renderEmployeeYear>
										</nested:select>
										<script type="text/javascript">
											var opid= '<nested:write property="yearOfComp"/>';
											if(opid!=0)
											document.getElementById("<c:out value='${dyopid}'/>").value = opid;
										</script>
									</td>
									<td class="row-even" align="left">
										<nested:text property="grade" size="10" maxlength="40"></nested:text>
									</td>
									<td class="row-even" align="left">
										<nested:text property="institute" size="20" maxlength="100"></nested:text>
									</td>
									<td class="row-odd">
									</td>
								</tr>
								</nested:iterate>
								</logic:notEmpty>
								
								<logic:notEmpty property="empResumeSubmissionTo.empQualificationLevelTos" name="empResumeSubmissionForm">
									<nested:iterate id="levelTo" property="empResumeSubmissionTo.empQualificationLevelTos" name="empResumeSubmissionForm" indexId="yrs">
									<tr>
									<td class="row-odd">
									<div align="right">
									<nested:select property="educationId" >
										<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									  	<logic:notEmpty property="qualificationLevelMap" name="empResumeSubmissionForm">
									  		<html:optionsCollection property="qualificationLevelMap" label="value" value="key" name="empResumeSubmissionForm"/>
									   </logic:notEmpty>
									</nested:select>
									</div>
									</td>
									<%
										String stylecourse = "course_" + yrs;
									%>
									<td class="row-even" align="left">
										<nested:text property="course"  styleId="<%=stylecourse%>" size="20" maxlength="100"></nested:text>
									</td>
									<td class="row-even" align="left">
										<nested:text property="specialization" size="20" maxlength="50"></nested:text>
									</td>
									<%String dynaYrId="YEAR"+yrs;%>
									<td class="row-even" align="left">
										<c:set var="yopid"><%=dynaYrId %></c:set>
											<nested:select property="year" styleId='<%=dynaYrId %>' styleClass="comboMedium" >
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
													<cms:renderEmployeeYear normalYear="true"></cms:renderEmployeeYear>
										</nested:select>
										<script type="text/javascript">
											var yid= '<nested:write property="year"/>';
											if(yid!=0)
											document.getElementById("<c:out value='${yopid}'/>").value = yid;
										</script>
									</td>
									
									<td class="row-even" align="left">
										<nested:text property="grade" size="10" maxlength="40"></nested:text>
									</td>
									<td class="row-even" align="left">
										<nested:text property="institute" size="20" maxlength="100"></nested:text>
									</td>
									<td class="row-odd">
									</td>
								</tr>
								</nested:iterate>
								</logic:notEmpty>
								
								<tr>
			                        <td  class="row-even" align="center" colspan="8">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addQualificationLevel','ExpAddMore'); return false;"></html:submit>
			                         <c:if test="${empResumeSubmissionForm.levelSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeQualificationLevel','ExpAddMore'); return false;"></html:submit>
			                         </c:if>
									</td> 
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
					</tr>
					
					<!-- by venkat -->
					<logic:equal value="false" name="empResumeSubmissionForm" property="isCjc">
								   		
					<tr><td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					   <tr>
					   <td class="heading" align="left">
						<bean:message key="knowledgepro.employee.online.resume.research"/>
					</td>
					   </tr>
					   <tr>
					   <td valign="top" class="news" colspan="1">
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
										<td class="row-odd" height="12" width="20%"> 
									  	<div align="right">
											<bean:message key="knowledgepro.employee.online.resume.research.papers"/>:
											</div>		
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.refer"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="researchPapersRefereed" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td> 
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.non.refer"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="researchPapersNonRefereed" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.proceedings"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="researchPapersProceedings" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
									  </tr>
									  <tr>
										<td class="row-odd"> 
									  	<div align="right">
											<bean:message key="knowledgepro.employee.online.resume.research.book.publications"/>:
											</div>		
											</td> 
										<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.international"/>
										</td>
										<td class="row-even"  align="left" width="10%">
											<html:text property="internationalPublications" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
										</td>
										<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.national"/>:
										</td>
										<td class="row-even"  align="left" width="10%">
											<html:text property="nationalPublications" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
										</td>
										<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.local"/>:
										</td>
										<td class="row-even"  align="left" width="10%">
											<html:text property="localPublications" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
										</td>
										<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
									  </tr>
									  
									  <tr>
										<td class="row-odd"> 
									  	<div align="right">
											<bean:message key="knowledgepro.employee.online.resume.research.editedbooks.chapters"/>:
										</div>		
										</td> 
										<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.international"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="international" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.national"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="national" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
									  </tr>
									  <tr>
										<td class="row-odd"> 
									  	<div align="right">
											<bean:message key="knowledgepro.employee.online.resume.research.projects.sponsored"/>:
											</div>		
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.major"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="majorProjects" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.minor"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="minorProjects" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.consultancy1"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="consultancyPrjects1" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.consultancy2"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="consultancyProjects2" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
									  </tr>
									  <tr>
										<td class="row-odd"> 
									  	<div align="right">
											<bean:message key="knowledgepro.employee.online.resume.research.reasearch.guide"/>:
											</div>		
											</td> 
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.phd"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="phdResearchGuidance" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.mphil"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="mphilResearchGuidance" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
									  </tr>
									  <tr>
										<td class="row-odd"> 
									  	<div align="right">
											<bean:message key="knowledgepro.employee.online.resume.research.training"/>:
											</div>		
											</td> 
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.fdp2"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="fdp1Training" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.fdp1"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="fdp2Training" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
									  </tr>
									  <tr>
										<td class="row-odd"> 
									  	<div align="right">
											<bean:message key="knowledgepro.employee.online.resume.research.conference"/>:
											</div>		
											</td> 
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.international"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="internationalConference" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.national"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="nationalConference" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.regional"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="regionalConference" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.local"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="localConference" name="empResumeSubmissionForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
									  </tr>
								</table>
							</td>
									<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
					   </tr>
					   </logic:equal>
					<!-- by venkat -->
					
					
					
					
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					
					
					<tr>
						<td valign="top" class="news" colspan="3">
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
										<td class="row-odd" width="250"> 
									  	<div align="right">
									  	<logic:equal value="true" name="empResumeSubmissionForm" property="isCjc">
									  	<span class="Mandatory">*</span>
									  	</logic:equal>
											<bean:message key="knowledgepro.employee.upload.photo"/>:
											</div>		
											</td> 
											<td class="row-even"  align="left">
												<html:file property="file"></html:file>
											</td>
									  </tr>
									<tr>
										<td class="row-odd"> 
									  	<div align="right">
											<bean:message key="knowledgepro.employee.resume.anyother"/>:
										</div>		
										</td> 
										<td class="row-even"  align="left">
											
											<html:textarea property="otherInfo" cols="80" rows="5" onkeypress="return imposeMaxLength1(this, 499);" onkeyup="len_display(this,0,'long_len')"></html:textarea>
											<input type="text" id="long_len" value="0" class="len" size="2" readonly="readonly" style="border: none; background-color: #eaeccc; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif">/500 Characters</td>
											
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
					</tr>
		
				  <tr>
					
						<td align="center" colspan="6"> 
							<html:button property="" styleId="print" styleClass="formbutton" value="Submit" onclick="saveResume()"></html:button>&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Reset" onclick="resetEmpResume()"></html:button>&nbsp;&nbsp;
								<!--<html:button property="" styleId="close" styleClass="formbutton" value="Close" onclick="javascript:self.close();"></html:button>-->
							</td>
					</tr>
				
			</table>
			        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
			      </tr>
			      <tr>
			        <td><img src="images/Tright_03_05.gif" width="9" height="29"/></td>
			        <td width="100%" background="images/TcenterD.gif"></td>
			        <td><img src="images/Tright_02.gif" width="9" height="29"/></td>
			      </tr>
			    </table>
			</td>
			</tr>
			</html:form>
			</table>
			</body>
			
		<script type="text/javascript">
		 var browserName=navigator.appName; 
			 if (browserName=="Microsoft Internet Explorer")
			 {
				 document.getElementById("message").innerHTML="<p style='font-size:12px'><b style='color:red'>Note:</b> Use Mozilla Firefox for better Performance and view Or Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling.</p>";
				 alert(" If Your using InternetExplorer Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling  or Use Mozilla FireFox");
			 }
		
		var focusField=document.getElementById("focusValue").value;
	    if(focusField != null){  
		    if(document.getElementById(focusField)!=null)      
	            document.getElementById(focusField).focus();
		}

	    desig=document.getElementById("designationId").value;
		if(desig!=null)
		{
			if(desig=="Guest")
			{
				postApplied();
			}
		}
			var sameAddr= document.getElementById("sameAddr").checked;

			if(sameAddr==true){
				disableAddress();
			}
			if(sameAddr==false){
				enableAddress();
			}
			countryId=document.getElementById("currentCountryId").value;
			if(countryId!=''){
				setTimeout("getCurrentStateByCountry(countryId,'currentState')",1000); 
				setTimeout("setData1()",1500); 
			}
			
			cId=document.getElementById("countryId").value;
			if(cId!=''){
				setTimeout("getStateByCountry(cId,'stateId')",3000); 
				setTimeout("setData2()",3500); 
			}
			function setData1(){
				stateId=document.getElementById("tempState").value;
				document.getElementById('currentState').value=stateId;
			}
			function setData2(){ 
				var stId=document.getElementById("tempPermanentState").value;
				document.getElementById('stateId').value=stId;
			}

			function radioHide(){
				document.getElementById("dman").style.display="none";
				document.getElementById("orgman").style.display="none";
				document.getElementById("deman").style.display="block";
				document.getElementById("orgsman").style.display="block";
			}
			function radioShow(){
					document.getElementById("dman").style.display="block";
					document.getElementById("orgman").style.display="block";
					document.getElementById("deman").style.display="none";
					document.getElementById("orgsman").style.display="none";
					
			}

			var flag="NO";
			if(document.getElementById("cyes").checked){
				flag=document.getElementById("cyes").value;
			}
			if(flag=='YES'){
				radioShow();
			}else if(flag='NO'){
				radioHide();
			}
			function getOtherCurrentState(){
				other=document.getElementById("currentState").value;
				if(other=="Other"){
					document.getElementById("otehrState").style.display="block";
				}else{
					document.getElementById("otehrState").style.display="none";
				}
			}

			var tempOther=document.getElementById("tempState").value;
			if(tempOther=="Other"){
				document.getElementById("otehrState").style.display="block";
			}else{
				document.getElementById("otehrState").style.display="none";
			}

			function getOtherPermanentState(){
				other=document.getElementById("stateId").value;
				if(other=="Other"){
					document.getElementById("otehrPermState").style.display="block";
				}else{
					document.getElementById("otehrPermState").style.display="none";
				}
			}

			var tempPermOther=document.getElementById("tempPermanentState").value;
			if(tempPermOther=="Other"){
				document.getElementById("otehrPermState").style.display="block";
			}else{
				document.getElementById("otehrPermState").style.display="none";
			}

			function maxlength(field, size) {
			    if (field.value.length > size) {
			        field.value = field.value.substring(0, size);
			    }
			}
			 for(var i=1;i<=5;i++){
				 if(document.getElementById(i).value != null){
		    		 if(document.getElementById(i).value=="OTHER"){
									
			    		 if(document.getElementById(i).checked==true){
			    			 document.getElementById("otherEligibilityTest").style.display="block";
				    		 }else{
				    			 document.getElementById("otherEligibilityTest").style.display="none";
					    }
			    	 }
				 }else{
					 document.getElementById("otherEligibilityTest").style.display="none";
		    	 }
    		 
			 }
</script>
<script type="text/javascript">
	 var serverDownMessage=$('#serverDownMessage').val();
	 if(serverDownMessage!=null && serverDownMessage!=""){
	 	 $(".ZebraDialog_Title a.ZebraDialog_Close").hide();
	 	$.Zebra_Dialog('<strong>'+serverDownMessage+'</strong>',{
	 		'title':    'Alert',
	 		'buttons':  ['Hide'],
	 		'keyboard':false,
	 		'overlay_close':false,
	 		'show_close_button':false
	 	});
	 }	
	 
	 var designation = document.getElementById("designationId").value;
	 if(designation == "Non-Teaching"){
		 document.getElementById("department").style.display="none";
		 document.getElementById("department1").style.display="none";
	 }
	</script>			
</html>	

