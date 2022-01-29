<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.utilities.CommonUtil"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="java.util.Date"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Employee Information Submission</title>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script language="JavaScript" src="js/admissionform.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">

	    </script>
<script type="text/javascript">


function setFocus(){
	var Focus=document.getElementById("focusValue").value;
	var txtBox=document.getElementById("Focus").value;
	document.all('txtBox').focus();
	return false;
	}

function maxlength(field, size) {
    if (field.value.length > size) {
        field.value = field.value.substring(0, size);
    }
}

function calc(count)
{
	var allocatedLeave= document.getElementById("leave_Allocated_"+count).value;	
	var sanctionedLeave=document.getElementById("leave_Sanctioned_"+count).value;
	var remainingLeave=0.0;
	if(allocatedLeave!=null && allocatedLeave!="" && sanctionedLeave!=null && sanctionedLeave!="")
	{
		if(allocatedLeave== 0  && sanctionedLeave>allocatedLeave)
		{
			remainingLeave=0;
			document.getElementById("leave_Remaining_"+count).value=remainingLeave;
		}
		else
		{
			remainingLeave=allocatedLeave-sanctionedLeave;
			document.getElementById("leave_Remaining_"+count).value=remainingLeave;
		}
	}
}


function calcTotalExp(count)
{
	var allocatedLeave= document.getElementById("leave_Allocated_"+count).value;	
	var sanctionedLeave=document.getElementById("leave_Sanctioned_"+count).value;
	var remainingLeave=0.0;
	if(allocatedLeave!=null && allocatedLeave!="" && sanctionedLeave!=null && sanctionedLeave!="")
	{
		if(allocatedLeave== 0  && sanctionedLeave>allocatedLeave)
		{
			remainingLeave=0;
			document.getElementById("leave_Remaining_"+count).value=remainingLeave;
		}
		else
		{
			remainingLeave=allocatedLeave-sanctionedLeave;
			document.getElementById("leave_Remaining_"+count).value=remainingLeave;
		}
	}
}


function shows1(obj,msg){
	document.getElementById("messageBox1").style.top=obj.offsetTop;
	document.getElementById("messageBox1").style.left=obj.offsetLeft+obj.offsetWidth+5;
	document.getElementById("contents1").innerHTML=msg;
	document.getElementById("messageBox1").style.display="block";
	}
function hides1(){
	document.getElementById("messageBox1").style.display="none";
}


	var destId;
	function closeWindow(){
		document.getElementById("method").value="getSearchedEmployee";
		document.EmployeeInfoEditNewForm.submit();
		//document.location.href = "LoginAction.do?method=loginAction";
	}

	function resetEmpInfo(){
		document.getElementById("method").value="initEmployeeInfo";
		document.EmployeeInfoEditForm.submit();
	}

	function saveEmpDetails(){
		// code added by sudhir
		/*
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
		*/
		
		document.getElementById("method").value="saveEmployeeDetails";
		document.EmployeeInfoEditNewForm.submit();
	}
	function submitEmployeeInfoAdd(method,mode){
		document.getElementById("method").value=method;
		document.getElementById("mode").value=mode;
		document.EmployeeInfoEditNewForm.submit();
	}
	
	


	

	function updateEmployeePayScale(req){
		var responseObj = req.responseXML.documentElement;
		var value = responseObj.getElementsByTagName("value")[0].firstChild.nodeValue;
		document.getElementById("scale").value=value;
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
		document.getElementById("expYears").value=totalExp;
	}
	var totalMonth;
	function getMonths(field){
	//	checkForEmpty(field);
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
	
	function CalAge() {

	    var now = new Date();
	   
	    var Dob=document.getElementById("dateOfBirth").value;
	   
	    bD = Dob.split('/');
	   	    if (bD.length == 3) {
	        	   born = new Date(bD[2], bD[1] * 1 - 1, bD[0]);
	                 years = Math.floor((now.getTime() - born.getTime()) / (365.25 * 24 * 60 * 60 * 1000));
	              document.getElementById("age").value=years;
	              alert(years);
	      	    }
	}

	function CalRetirementDate()
	{
		var retirementYear=0;
		var Dob=document.getElementById("dateOfBirth").value;
		var RetirementAge=document.getElementById("empRetirementAge").value;
		var retireDate;
		var to=Dob.split("/");
		   var d = parseInt(to[0]);
		   var m = parseInt(to[1]);
		   var y = parseInt(to[2]);
			if (d<10)
				d=0+"d";
			if(m<10)
				m=0+"m";
			  retirementYear = y + parseInt(document.getElementById("empRetirementAge").value);
		   
			retireDate=(d+"/"+m+"/"+retirementYear);
			document.getElementById("retirementDate").value=retireDate;
		
	}

	function searchStreamWise(streamId){
		getDepartmentByStreamWise(streamId,updateDepartmentMap);
	}
	function updateDepartmentMap(req){
		updateOptionsFromMap(req,"departmentId","-Select-");
	}



	function getPayscalewithTeachingStaff(teachingStaff)
	{
		getPayscaleTeachingStaff(teachingStaff, updateEmployeePayScaleTeachingStaff);
		
	}

	function updateEmployeePayScaleTeachingStaff(req){
		updateOptionsFromMap(req,"payScaleId","-Select-");
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
	
	function getOtherEligibilityTest(){
		if(document.getElementById("eligibilityTestOther").checked==true)
			{
			document.getElementById("otherEligibilityTest").style.display="block";
			}
		else if(document.getElementById("eligibilityTestOther").checked==false)
			{
			document.getElementById("otherEligibilityTestValue").value="";
			 document.getElementById("otherEligibilityTest").style.display="none";
    		}
	}	
/*	function disableRemainingEligibilityTest(){
		if(document.getElementById("eligibilityTestNone").checked==true){
			document.getElementById("eligibilityTestNET").value="";
			document.getElementById("eligibilityTestSLET").value="";
			document.getElementById("eligibilityTestSET").value="";
			document.getElementById("eligibilityTestOther").value="";
		document.getElementById("eligibilityTestNET").checked=false;
		document.getElementById("eligibilityTestSLET").checked=false;
		document.getElementById("eligibilityTestSET").checked=false;
		document.getElementById("eligibilityTestOther").checked=false;
		document.getElementById("eligibilityTestNET").disabled=true;
		document.getElementById("eligibilityTestSLET").disabled=true;
		document.getElementById("eligibilityTestSET").disabled=true;
		document.getElementById("eligibilityTestOther").disabled=true;
		document.getElementById("otherEligibilityTestValue").value="";
		document.getElementById("otherEligibilityTestValue").disabled=true;
		}
		else {
			document.getElementById("eligibilityTestNET").disabled=false;
			document.getElementById("eligibilityTestSLET").disabled=false;
			document.getElementById("eligibilityTestSET").disabled=false;
			document.getElementById("eligibilityTestOther").disabled=false;
			document.getElementById("otherEligibilityTestValue").disabled=false;
		}
	}
	function disableEligibilityTestNone(property){
		if(document.getElementById(property).checked==true){
			if(property!="eligibilityTestNone"){
			document.getElementById("eligibilityTestNone").checked=false;
			document.getElementById("eligibilityTestNone").disabled=true;
			document.getElementById("eligibilityTestNone").value=null;
			}
		}
		else{
			document.getElementById("eligibilityTestNone").disabled=false;
		}
	}*/
	function disableRemainingEligibilityTest(){
		if(document.getElementById("eligibilityTestNone").checked==true){
		document.getElementById("eligibilityTestNET").checked=false;
		document.getElementById("eligibilityTestSLET").checked=false;
		document.getElementById("eligibilityTestSET").checked=false;
		document.getElementById("eligibilityTestOther").checked=false;
		document.getElementById("eligibilityTestNET").disabled=true;
		document.getElementById("eligibilityTestSLET").disabled=true;
		document.getElementById("eligibilityTestSET").disabled=true;
		document.getElementById("eligibilityTestOther").disabled=true;
		document.getElementById("otherEligibilityTestValue").value="";
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

	function imposeMaxLength12(Object, MaxLen)
	{
	  return (Object.value.length < (MaxLen));
	}

	function imposeMaxLength1(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}
		
	// to display the text areas length 
	function len_display(Object,MaxLen,element){
	    var len_remain = MaxLen+Object.value.length;
	   if(len_remain <=500){
	    document.getElementById(element).value=len_remain; }
	}
	
</script>
</head>

<script type="text/javascript">

function setFocus(){
	var Focus=document.getElementById("focusValue").value;
	var txtBox=document.getElementById("Focus");
	document.all('txtBox').focus();
	
	}

function putFocusOnField(){
	var focusField=document.getElementById("focusValue").value;
    if(focusField != null){        
        if(document.getElementById(focusField).type != 'hidden'){
            document.getElementById(focusField).focus();
        }
    }

}
//code added by sudhir //
function calculateDates(count){
	document.getElementById("count").value = count;
	var startDate= document.getElementById("teachingFromDate_" + count).value;
	var endDate= document.getElementById("teachingToDate_" + count).value;
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
function imposeMaxLengthpf(field,size) {
	 if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
}
	
// to display the text areas length 
function onchange_isplay_pf(field,size){
     if (field.value.length >= size) {
       field.value = field.value.substring(0, size);
   }
}
//

</script>
<body>
<table width="100%" border="0">

<html:form action="/EmployeeInfoEditDisplayNew" enctype="multipart/form-data" >
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="EmployeeInfoEditNewForm" />
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<input type="hidden" id="count"/>
       <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.employee.info.label" /> &gt;&gt;</span></span></td>
		</tr>
 
  <tr>
    <td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
        <td width="1271" background="images/Tcenter.gif" class="body" >
			<div align="left">
				<strong class="boxheader"><bean:message key="knowledgepro.employee.employeeInfo.label"/></strong>
			</div>
		</td>
       <td width="15" >
	   <img src="images/Tright_1_01.gif" width="9" height="29"/>	   </td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
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
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					
					
					<tr> 
					  	<td colspan="2" class="heading" align="center" >
							<FONT style="text-transform: capitalize" size="3%"><bean:message key="knowledgepro.employee.personal.details"/></FONT>
						</td>
					</tr>
					
					<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
									 <td class="row-odd" width="14%">
									 <div align="left"><span class="Mandatory">*</span>
									     <bean:message key="knowledgepro.admin.name"/>
									  </div>
									  </td>
										<td  width="36%" class="row-even" >
											<html:text property="name" styleId="name" size="35" maxlength="100" style="text-transform:uppercase;"></html:text>
										</td>
										 <td class="row-odd" width="14%">
									<div align="left"><span class="Mandatory">*</span>
									     Date of Birth
									  </div>
									  </td>
									<td   class="row-even">
									<html:text name="EmployeeInfoEditNewForm" property="dateOfBirth" styleId="dateOfBirth" size="10" maxlength="16" onchange="CalRetirementDate(), CalAge()" />
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'dateOfBirth'
												});
										</script>
								
								 
								 </td>
							</tr>
								<tr>
									 <td class="row-odd" width="14%">
									 <div align="left"><span class="Mandatory">*</span>
									     Designation
									 </div>
									  </td>
							
									<td height="25" class="row-even">
							<html:select property="designationId" styleId="designationId">
								<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									<logic:notEmpty property="designationMap" name="EmployeeInfoEditNewForm">
								  		<html:optionsCollection property="designationMap" label="value" value="key"/>
								   </logic:notEmpty>
							 </html:select> 
									
				  </td>
									<td class="row-odd" width="14%">
									 <div align="left"><span class="Mandatory">*</span>
									     Date of Joining
									 </div>
									  </td>
							
									<td   class="row-even">
									<html:text name="EmployeeInfoEditNewForm" property="dateOfJoining" styleId="dateOfJoining" size="10" maxlength="16" onchange="CalRetirementDate(), CalAge()" />
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'dateOfJoining'
												});
										</script>
								
								 	
								 </td>
								</tr>
								<tr> 
							  	 <td class="row-odd" >
							  	 <div align="left"><span class="Mandatory">*</span>
							      	Department
							     </div>
							     </td>
								<td height="25" class="row-even">
							<html:select property="departmentId" styleId="departmentId">
								<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									<logic:notEmpty property="departmentMap" name="EmployeeInfoEditNewForm">
								  		<html:optionsCollection property="departmentMap" label="value" value="key"/>
								   </logic:notEmpty>
							 </html:select> 
									
				  </td>
							  	<td class="row-odd" > 
									<div align="left"><span class="Mandatory">*</span>
									Teaching Experience</div>
							  	</td>
								<td class="row-even" colspan="2">
											 <html:text styleId="currentExpYears" name="EmployeeInfoEditNewForm" property="currentExpYears" size="3" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;&nbsp;Years&nbsp;&nbsp;
											 <html:text styleId="currentExpMonths" name="EmployeeInfoEditNewForm" property="currentExpMonths" size="3" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;&nbsp;Months
										</td>
							  </tr>
							  
							  
							  
					 
					<tr> 
							  	 <td class="row-odd"> 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="admissionFormForm.emailId" /></div>
							  	</td>
								 <td  class="row-even">
								 	<html:text property="email" size="20" maxlength="50"></html:text>
								 </td>
							  		<td class="row-odd"> 
									<div align="left">
									Any Other Experience</div>
							  		</td>
									<td class="row-even" colspan="2">
											 <html:text styleId="expYears" name="EmployeeInfoEditNewForm" property="expYears" size="3" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;&nbsp;Years&nbsp;&nbsp;
											 <html:text styleId="expMonths" name="EmployeeInfoEditNewForm" property="expMonths" size="3" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;&nbsp;Months
										</td>
				 </tr>
							  
				<tr>
									
									<td class="row-odd" width="14%"> 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.employee.mobile" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="mobileNo1" onkeypress="return isNumberKey(event)" maxlength="10"></html:text>
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
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					
					
					<tr> 
									<td colspan="2" class="heading" align="left">
						Academic Qualifications
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no</div></td>
               			<td   class="row-odd"><div align="center" >Course(Degre Onwards)</div></td> 
               			<td   class="row-odd"><div align="center" >University</div></td>
               			<td   class="row-odd"><div align="center" >Year</div></td>
               			<td   class="row-odd"><div align="center" >Grade/Rank</div></td>
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.empqualification" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.empqualification" name="EmployeeInfoEditNewForm" id="empqualification" indexId="count">
                		
                		<%
					    String coursename = "courseName" + count;
                		String university = "universityName" + count;
                		String year = "year" + count;
                		String grade = "grade" + count;
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><nested:text  property="courseName" styleId="<%=coursename%>" size="40" maxlength="100" ></nested:text></td>
                		<td  class="row-even"><nested:text  property="universityName" styleId="<%=university%>" size="30" maxlength="50" ></nested:text></td>
                		<td  class="row-even"><nested:text  property="year" styleId="<%=year%>" size="30" maxlength="50" ></nested:text></td>
                		<td  class="row-even"><nested:select  property="grade" styleId="<%=grade%>" >
								 	       		<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:option value="1">1</html:option>
												<html:option value="2">2</html:option>
												<html:option value="3">3</html:option>
												<html:option value="4">4</html:option>
												<html:option value="5">5</html:option>
												<html:option value="6">6</html:option>
												<html:option value="7">7</html:option>
												<html:option value="8">8</html:option>
												<html:option value="9">9</html:option>
												<html:option value="10">10</html:option>
												<html:option value="11">11</html:option>
												<html:option value="12">12</html:option>
												<html:option value="13">13</html:option>
												<html:option value="14">14</html:option>
												<html:option value="15">15</html:option>
												<html:option value="First class">First class</html:option>
												<html:option value="Second class">Second class</html:option>
												<html:option value="Distiction">Distiction</html:option>
												<html:option value="Awarded">Awarded</html:option>
												<html:option value="not applicable">not applicable</html:option>
							    	 </nested:select></td>
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="5">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addQualifications','QualificationsAddMore'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empQualificationListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeQualificationRow','QualificationsAddMore'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Area Of Interests
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="50%" border="0"  cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no</div></td>
               			<td   class="row-odd"><div align="left" >Topic</div></td> 
               			
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.interest" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.interest" name="EmployeeInfoEditNewForm" id="interest" indexId="count">
                		
                		<%
					    String topic = "topic" + count;
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" height="10px"><nested:text  property="topic" styleId="<%=topic%>" size="70" maxlength="100" ></nested:text></td>
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="3">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addInterests','InterestaddMore'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empIntrestListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeInterests','InterestaddMore'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Field Of Research
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="50%" border="0"  cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no</div></td>
               			<td   class="row-odd"><div align="left" >Topic</div></td> 
               			
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.research" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.research" name="EmployeeInfoEditNewForm" id="research" indexId="count">
                		
                		<%
					    String topic = "topic" + count;
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" height="10px" ><nested:text  property="topic" styleId="<%=topic%>" size="70" maxlength="100" ></nested:text></td>
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="3">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addFieldResearch','ReserachaddMore'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empResearchListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeFieldResearch','ReserachaddMore'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Guideship Details
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Name of the Ph.D Scholars</div></td>
               			<td   class="row-odd"><div align="center" >Year of Registration of the scholar</div></td> 
               			<td   class="row-odd"><div align="center" >Award Ongoing</div></td>
               			<td   class="row-odd"><div align="center" >(If Awarded) Year</div></td>
               			<td   class="row-odd"><div align="center" >(If awarded) Title of the Thesis</div></td>
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.guideship" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.guideship" name="EmployeeInfoEditNewForm" id="guideship" indexId="count">
                		
                		<%
					    String scholarname = "phdScholarName" + count;
                		String regno = "registrationYear" + count;
                		String award = "awarded" + count;
                		String year = "year" + count;
                		String thesis = "thesisName" + count;
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><nested:text  property="phdScholarName" styleId="<%=scholarname%>" size="40" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="registrationYear" styleId="<%=regno%>" size="40" maxlength="100" ></nested:text></td>
                		<td  class="row-even"><nested:text  property="awarded" styleId="<%=award%>" size="30" maxlength="50" ></nested:text></td>
                		<td  class="row-even"><nested:text  property="year" styleId="<%=year%>" size="30" maxlength="50" ></nested:text></td>
                		<td  class="row-even"><nested:text  property="thesisName" styleId="<%=thesis%>" size="40" maxlength="100"></nested:text></td>
								 	       		
							    	
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="5">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addGuideShip','guideshipAddMore'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empGuidshipListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeGuideShip','guideshipAddMore'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Duties Performed/Academic Responsibilities Undertaken
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
               			<td   class="row-odd"><div align="center" >Position Held</div></td> 
               			<td   class="row-odd"><div align="center" >Duration(From - to & Still)</div></td>
               			
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.duties" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.duties" name="EmployeeInfoEditNewForm" id="duties" indexId="count">
                		
                		<%
					    String position = "positionName" + count;
                		String fromDate = "fromDate" + count;
                		String toDate = "toDate" + count;
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><nested:text  property="positionName" styleId="<%=position%>" size="40" maxlength="100" ></nested:text></td>
                		<td  class="row-even">
                		<table align="center">
                		<tr>
                		<td class="row-even"><nested:text  property="fromDate" styleId="<%=fromDate%>" size="20" maxlength="100"></nested:text>
                		<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'<%=fromDate%>'
												});
										</script>
                		</td>
                		<td>-</td>
                		<td class="row-even" ><nested:text  property="toDate" styleId="<%=toDate%>" size="20" maxlength="100"></nested:text>
                		<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'<%=toDate%>'
												});
										</script></td>
                		</tr>
                		</table>
                		</td>
								 	       		
							    	
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="3">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addDuties','dutiesAddMore'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empDutiesPerformedListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeDuties','dutiesAddMore'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Research Projects Undertaken
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Title</div></td> 
               			<td   class="row-odd"><div align="center" >Name of Project(Minor/Major etc)</div></td> 
               			<td   class="row-odd"><div align="center" >Funding Agency</div></td> 
               			<td   class="row-odd"><div align="center" >Amount Sanctioned</div></td> 
               			<td   class="row-odd"><div align="center" >Duration(From - to & Still)</div></td>
               			
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.researchProject" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.researchProject" name="EmployeeInfoEditNewForm" id="researchProject" indexId="count">
                		
                		<%
					    String title = "title" + count;
                		String projectName = "projectName" + count;
                		String findingAgencyName = "findingAgencyName" + count;
                		String amount = "amount" + count;
                		String fromDate1 = "fromDate1" + count;
                		String toDate1 = "toDate1" + count;
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><nested:text  property="title" styleId="<%=title%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="projectName" styleId="<%=projectName%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="findingAgencyName" styleId="<%=findingAgencyName%>" size="30" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="amount" styleId="<%=amount%>" size="15" maxlength="100" ></nested:text></td>
                		<td  class="row-even">
                		<table align="center">
                		<tr>
                		<td class="row-even"><nested:text  property="fromDate1" styleId="<%=fromDate1%>" size="10" maxlength="100"></nested:text>
                		<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'<%=fromDate1%>'
												});
										</script>
                		</td>
                		<td>-</td>
                		<td class="row-even" ><nested:text  property="toDate1" styleId="<%=toDate1%>" size="10" maxlength="100"></nested:text>
                		<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'<%=toDate1%>'
												});
										</script></td>
                		</tr>
                		</table>
                		</td>
								 	       		
							    	
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="6">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addResearchProject','projectAddMore'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empResearchProjectListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeResearchProject','projectAddMore'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Research Publication
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Title of the Paper</div></td> 
               			<td   class="row-odd"><div align="center" >Name of Journal</div></td> 
               			<td   class="row-odd"><div align="center" >Year</div></td> 
               			<td   class="row-odd"><div align="center" >ISBN/ISSN No.</div></td> 
               			<td   class="row-odd"><div align="center" >UGC/Non UGC</div></td> 
               			
               			
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.researchPubliction" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.researchPubliction" name="EmployeeInfoEditNewForm" id="researchPubliction" indexId="count">
                		
                		<%
					    String paperTitle = "paperTitle" + count;
                		String journalName = "journalName" + count;
                		String year = "year" + count;
                		String ISBNISSNNo = "ISBNISSNNo" + count;
                		String UGCNonUGC = "UGCNonUGC" + count;
                		
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><nested:text  property="paperTitle" styleId="<%=paperTitle%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="journalName" styleId="<%=journalName%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="year" styleId="<%=year%>" size="30" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="ISBNISSNNo" styleId="<%=ISBNISSNNo%>" size="15" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="UGCNonUGC" styleId="<%=UGCNonUGC%>" size="15" maxlength="100" ></nested:text></td>
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="6">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addResearchProject','projectAddMore'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empResearchPublicationListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeResearchProject','projectAddMore'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Books/Book Chapters Published
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Title of the Book/Chapter</div></td> 
               			<td   class="row-odd"><div align="center" >Year of Publication</div></td> 
               			<td   class="row-odd"><div align="center" >Name of Publisher</div></td> 
               			<td   class="row-odd"><div align="center" >ISBN/ISSN No.</div></td> 
               			<td   class="row-odd"><div align="center" >Nature of Contribution</div></td> 
               			
               			
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.empBooks" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.empBooks" name="EmployeeInfoEditNewForm" id="empBooks" indexId="count">
                		
                		<%
					    String titleName = "titleName" + count;
                		String publisherName = "publisherName" + count;
                		String year = "year" + count;
                		String ISBNISSN = "ISBNISSN" + count;
                		String contibutionName = "contibutionName" + count;
                		
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><nested:text  property="titleName" styleId="<%=titleName%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="publisherName" styleId="<%=publisherName%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="year" styleId="<%=year%>" size="30" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="ISBNISSN" styleId="<%=ISBNISSN%>" size="15" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="contibutionName" styleId="<%=contibutionName%>" size="15" maxlength="100" ></nested:text></td>
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="6">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addBooks','books'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empBooksPublishListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeBooks','books'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Paper Presentation
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Title of the Paper</div></td> 
               			<td   class="row-odd"><div align="center" >Title of Paper/Seminar/Conference</div></td> 
               			<td   class="row-odd"><div align="center" >Date</div></td> 
               			<td   class="row-odd"><div align="center" >Organising Institution</div></td> 
               			<td   class="row-odd"><div align="center" >International/National/Regional</div></td> 
               			
               			
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.empPaper" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.empPaper" name="EmployeeInfoEditNewForm" id="empPaper" indexId="count">
                		
                		<%
					    String paperName = "paperName" + count;
                		String seminarName = "seminarName" + count;
                		String Date = "date1" + count;
                		String organisation = "organisation" + count;
                		String regional = "regional" + count;
                		
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><nested:text  property="paperName" styleId="<%=paperName%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="seminarName" styleId="<%=seminarName%>" size="25" maxlength="100" ></nested:text></td>
                		<td class="row-even"><nested:text  property="date1" styleId="<%=Date%>" size="10" maxlength="100"></nested:text>
                		<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'<%=Date%>'
												});
										</script>
                		</td>
                		<td  class="row-even" ><nested:text  property="organisation" styleId="<%=organisation%>" size="15" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="regional" styleId="<%=regional%>" size="15" maxlength="100" ></nested:text></td>
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="6">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addpaper','addpaper'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empPaperPrsentationListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removepaper','addpaper'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Seminar/Workshop/Conference Attended
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Seminar/Workshop/Conference</div></td> 
               			<td   class="row-odd"><div align="center" >Title</div></td> 
               			<td   class="row-odd"><div align="center" >Chair/Partcipation</div></td> 
               			<td   class="row-odd"><div align="center" >Date(from-to)</div></td> 
               			<td   class="row-odd"><div align="center" >Orgainising Institution</div></td> 
               			<td   class="row-odd"><div align="center" >International/National/Regional</div></td> 
               			
               			
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.empSeminarAttendedDetails" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.empSeminarAttendedDetails" name="EmployeeInfoEditNewForm" id="empSeminarAttendedDetails" indexId="count">
                		
                		<%
					    String seminar = "seminar" + count;
                		String seminarName = "seminarName" + count;
                		String fromDate2 = "fromDate2" + count;
                		String toDate2 = "toDate2" + count;
                		String participation = "participation" + count;
                		String organisation = "organisation" + count;
                		String interRegional = "interRegional" + count;
                		
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><nested:text  property="seminar" styleId="<%=seminar%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="seminarName" styleId="<%=seminarName%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="participation" styleId="<%=seminarName%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even">
                		<table align="center">
                		<tr>
                		<td class="row-even"><nested:text  property="fromDate2" styleId="<%=fromDate2%>" size="10" maxlength="100"></nested:text>
                		<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'<%=fromDate2%>'
												});
										</script>
                		</td>
                		<td>-</td>
                		<td class="row-even" ><nested:text  property="toDate2" styleId="<%=toDate2%>" size="10" maxlength="100"></nested:text>
                		<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'<%=toDate2%>'
												});
										</script></td>
                		</tr>
                		</table>
                		</td>
                		<td  class="row-even" ><nested:text  property="organisation" styleId="<%=organisation%>" size="15" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="interRegional" styleId="<%=interRegional%>" size="15" maxlength="100" ></nested:text></td>
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="6">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addSeminar','addsem'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empSeminarattendedListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeSeminar','addsem'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Course Attended for Professional Development
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Name of the Course</div></td> 
               			<td   class="row-odd"><div align="center" >Organising Institute</div></td> 
               			<td   class="row-odd"><div align="center" >Period(from--to)</div></td> 
               			
               			
               			
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.empPersonaldevelopment" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.empPersonaldevelopment" name="EmployeeInfoEditNewForm" id="empPersonaldevelopment" indexId="count">
                		
                		<%
					    String name = "name" + count;
                		String organisation = "organisation" + count;
                		String fromDate3 = "fromDate3" + count;
                		String toDate3 = "toDate3" + count;
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><nested:text  property="name" styleId="<%=name%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="organisation" styleId="<%=organisation%>" size="25" maxlength="100" ></nested:text></td>
                		
                		<td  class="row-even">
                		<table align="center">
                		<tr>
                		<td class="row-even"><nested:text  property="fromDate3" styleId="<%=fromDate3%>" size="15" maxlength="100"></nested:text>
                		<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'<%=fromDate3%>'
												});
										</script>
                		</td>
                		<td>-</td>
                		<td class="row-even" ><nested:text  property="toDate3" styleId="<%=toDate3%>" size="15" maxlength="100"></nested:text>
                		<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'<%=toDate3%>'
												});
										</script></td>
                		</tr>
                		</table>
                		</td>
                		
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="6">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addProDev','addprodev'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empProfessionalDevelopmentListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeProDev','addprodev'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Awards/Recognition/Patents Confered
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Name of the Recognition</div></td> 
               			<td   class="row-odd"><div align="center" >Name of the Activity</div></td> 
               			<td   class="row-odd"><div align="center" >Name of the Awards/Recognition/Patents</div></td>
               			<td   class="row-odd"><div align="center" >Name of the Awarding Body</div></td>
               			<td   class="row-odd"><div align="center" >Year</div></td>
               			
               			
               			
               			
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.empAward" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.empAward" name="EmployeeInfoEditNewForm" id="empAward" indexId="count">
                		
                		<%
					    String awardName = "awardName" + count;
                		String activityname = "activityname" + count;
                		String recognitionName = "recognitionName" + count;
                		String awardbodyName = "awardbodyName" + count;
                		String year = "year" + count;
                		
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><nested:text  property="awardName" styleId="<%=awardName%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="activityname" styleId="<%=activityname%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="recognitionName" styleId="<%=recognitionName%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="awardbodyName" styleId="<%=awardbodyName%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even" ><nested:text  property="year" styleId="<%=year%>" size="25" maxlength="100" ></nested:text></td>
                		
                		
                		
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="6">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addAward','addAwardrow'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empAwardListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeAward','addAwardrow'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
						Membership in Academic Bodies
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
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
                      
                       <td   class="row-odd"><div align="center" >Sl no.</div></td>
                       <td   class="row-odd"><div align="center" >Name of the Body</div></td> 
               			<td   class="row-odd"><div align="center" >Period(from--to)</div></td> 
               			
               			
               			
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoEditNewTO.empMemberShip" name="EmployeeInfoEditNewForm">
					<nested:iterate property="employeeInfoEditNewTO.empMemberShip" name="EmployeeInfoEditNewForm" id="empMemberShip" indexId="count">
                		
                		<%
					    String name = "name" + count;
                		
                		String fromDate4 = "fromDate4" + count;
                		String todate4 = "todate4" + count;
						%>
					
                		<tr>
                		
                		
                		<td  class="row-even" ><c:out value="${count+1}"/></td>
                		<td  class="row-even" ><nested:text  property="name" styleId="<%=name%>" size="25" maxlength="100" ></nested:text></td>
                		<td  class="row-even">
                		<table align="center">
                		<tr>
                		<td class="row-even"><nested:text  property="fromDate4" styleId="<%=fromDate4%>" size="15" maxlength="100"></nested:text>
                		<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'<%=fromDate4%>'
												});
										</script>
                		</td>
                		<td>-</td>
                		<td class="row-even" ><nested:text  property="todate4" styleId="<%=todate4%>" size="15" maxlength="100"></nested:text>
                		<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditNewForm',
												// input name
												'controlname' :'<%=todate4%>'
												});
										</script></td>
                		</tr>
                		</table>
                		</td>
                		
                		
								 	       		
							    	
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="6">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addMemField','addMembership'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditNewForm.empMembershipAcademicListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeMemField','addMembership'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>                         
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
								<td width="5" height="30" background="images/right.gif"></td>
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
							<td align="center" colspan="6"> 
								<html:button property="" styleClass="formbutton" value="Submit" onclick="saveEmpDetails()"></html:button>&nbsp;&nbsp;
								<!--<html:button property="" styleClass="formbutton" value="Reset" onclick="resetEmpInfo()"></html:button>&nbsp;&nbsp;-->
								<html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button>		
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
			        <td><img src="images/Tright_03_05.gif" width="9" height="29"/></td>
			        <td width="100%" background="images/TcenterD.gif"></td>
			        <td><img src="images/Tright_02.gif" width="9" height="29"/></td>
			        </tr>
			        
			        
	</table>		
	</table>
						</td>
					</tr> 
	</html:form>
			
			</table>
			
			</body>
			<script type="text/javascript">

			//size=document.getElementById("listSize").value;
			//var size1=size-1;
			var focusField=document.getElementById("focusValue").value;
		    if(focusField != null){  
			    if(document.getElementById(focusField)!=null)      
		            document.getElementById(focusField).focus();
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
				setTimeout("setData1()",1800); 
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
		</script>
	</html>
			
		
			
	


