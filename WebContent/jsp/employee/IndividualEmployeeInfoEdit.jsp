<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
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
function calcRecogYearsAndMonths(){
	
	var RecogMonths = document.getElementById("relevantExpMonths").value;
	var ExpMonths = document.getElementById("month").value;
	if(RecogMonths!=""){
		var totMonths = parseInt(RecogMonths) + parseInt(ExpMonths) ;
	}else{
		var totMonths =  parseInt(ExpMonths) ;
	}
	if(totMonths>=12){
		var months = totMonths % 12;
		totYears =  totMonths / 12;
		var RecogYear = document.getElementById("relevantExpYears").value;
		var ExpYear = document.getElementById("year").value;
		if(RecogYear!="" ){
			RecogYear = parseInt(RecogYear) + parseInt(ExpYear) ;
			var totYears1=RecogYear + totYears;
			var totYears2=Math.floor(totYears1);
			document.getElementById("totYears").innerHTML = totYears2 + "&nbsp;&nbsp;Years&nbsp;&nbsp;&nbsp;" + months + "&nbsp;&nbsp;Months";
		}else{
			document.getElementById("totYears").innerHTML = ExpYear + "&nbsp;&nbsp;Years&nbsp;&nbsp;&nbsp;" + months + "&nbsp;&nbsp;Months";
		}
	}else{
		var RecogYear = document.getElementById("relevantExpYears").value;
		var ExpYear = document.getElementById("year").value;
		if(RecogYear!=""){
			RecogYear = parseInt(RecogYear) + parseInt(ExpYear) ;
			document.getElementById("totYears").innerHTML = RecogYear + "&nbsp;&nbsp;Years&nbsp;&nbsp;&nbsp;" +totMonths + "&nbsp;&nbsp;Months";
		}else{
			document.getElementById("totYears").innerHTML = ExpYear + "&nbsp;&nbsp;Years&nbsp;&nbsp;&nbsp;" +totMonths + "&nbsp;&nbsp;Months";
		}
	}
}
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
		document.getElementById("method").value="getSearchedEmployeeList";
		document.EmployeeInfoEditForm.submit();
		//document.location.href = "LoginAction.do?method=loginAction";
	}

	function resetEmpInfo(){
		document.getElementById("method").value="initEmployeeInfo";
		document.EmployeeInfoEditForm.submit();
	}

	function saveEmpDetails(){
		// code added by sudhir
		/* var size=document.getElementById("teachingExpLength").value;
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
		} */
		// code added by sudhir
		document.getElementById("method").value="saveEmpDetails";
		document.EmployeeInfoEditForm.submit();
	}
	function submitEmployeeInfoAdd(method,mode){
		document.getElementById("method").value=method;
		document.getElementById("mode").value=mode;
		document.EmployeeInfoEditForm.submit();
	}
	function getWorkTimeEntry(empType){
		
		document.getElementById("method").value="getWorkTimeEntry";
		document.EmployeeInfoEditForm.submit();
		
	}
	function getOldPayScale(payscale){
		if(payscale!=null && payscale!="")
		  {
			document.getElementById("method").value="getPayscale";
			document.EmployeeInfoEditForm.submit();
		  }
		else
		{
			document.getElementById("scale").value="";
		}
	}


	function getPayScale(payscale){
		var value = document.getElementById("payScaleId").value;
		if(value != null && value != ""){
			getPayscaleDetails(value,updateEmployeePayScale);
		}
		else
		{
			document.getElementById("scale").value="";
		}
	}

	function updateEmployeePayScale(req){
		var responseObj = req.responseXML.documentElement;
		var value = responseObj.getElementsByTagName("value")[0].firstChild.nodeValue;
		document.getElementById("scale").value=value;
	}



	
function getEmpLeaveList(empType)
{
	hook=false;
	if(empType != null && empType!= "")
	{
	document.getElementById("method").value="getEmpLeaveList";
	document.EmployeeInfoEditForm.submit();
	}
	else
	{
		document.getElementById("method").value="removeEmpLeaveList";
		document.EmployeeInfoEditForm.submit();
	}
	
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
function checkother(id,oId) {
	var other=document.getElementById(id);		
	var val=other.options[other.selectedIndex].value;
	if (val=='Other') {
		document.getElementById(oId).style.display="block";
	}else{
		document.getElementById(oId).style.display="none";
		document.getElementById(oId).value="";
	}
}
function checkotherquali(id,oId,dId,oDId) {
	var other=document.getElementById(id);		
	var val=other.options[other.selectedIndex].value;
	if (val=='Other') {
		document.getElementById(oId).style.display="block";
		document.getElementById(oDId).style.display="block";
		document.getElementById(dId).style.display="none";
		document.getElementById(dId).value="";
	}else{
		document.getElementById(oId).style.display="none";
		document.getElementById(oId).value="";
		document.getElementById(oDId).style.display="none";
		document.getElementById(dId).style.display="block";
		document.getElementById(oDId).value="";
	}
}
//

</script>
<body>
<table width="100%" border="0">

<html:form action="/EmployeeInfoEditDisplay" enctype="multipart/form-data" >
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="EmployeeInfoEditForm" />
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<html:hidden property="listSize" styleId="listSize"/>
	
	<html:hidden property="timeInEnds" styleClass="Timings"  styleId="timeInEnds" onkeypress="return isNumberKey(event)"/>
	<html:hidden property="timeInEndMIn" styleClass="Timings"  styleId="timeInEndMIn"    onkeypress="return isNumberKey(event)"/>
	<html:hidden property="timeIn"  styleClass="Timings"  styleId="timeIn"  onkeypress="return isNumberKey(event)"/>
	<html:hidden property="timeInMin"  styleClass="Timings"  styleId="timeInMin"    onkeypress="return isNumberKey(event)" onmouseover="shows1(this,'Enter Work time details for Employees for whom time is different from the entry in Employee type')" onmouseout="hides1()"/>
	<html:hidden property="timeOut" styleClass="Timings"  styleId="timeOut"  onkeypress="return isNumberKey(event)"/>
	<html:hidden property="timeOutMin" styleClass="Timings"  styleId="timeOutMin"  onkeypress="return isNumberKey(event)" onmouseover="shows1(this,'Enter Work time details for Employees for whom time is different from the entry in Employee type')" onmouseout="hides1()"/>
	<html:hidden property="saturdayTimeOut" styleClass="Timings"  styleId="saturdayTimeOut"  onkeypress="return isNumberKey(event)"/>
	<html:hidden property="saturdayTimeOutMin" styleClass="Timings"  styleId="saturdayTimeOutMin" onkeypress="return isNumberKey(event)" onmouseover="shows1(this,'Enter Work time details for Employees for whom time is different from the entry in Employee type')" onmouseout="hides1()"/>
	<html:hidden property="halfDayStartTime" styleClass="Timings"  styleId="halfDayStartTime" onkeypress="return isNumberKey(event)"/>
	<html:hidden property="halfDayStartTimeMin" styleClass="Timings"  styleId="halfDayStartTimeMin" onkeypress="return isNumberKey(event)" onmouseover="shows1(this,'Enter Work time details for Employees for whom time is different from the entry in Employee type')" onmouseout="hides1()"/>
	<html:hidden property="halfDayEndTime" styleClass="Timings"  styleId="halfDayEndTime"  onkeypress="return isNumberKey(event)"/>
	<html:hidden property="halfDayEndTimeMin" styleClass="Timings"  styleId="halfDayEndTimeMin" onkeypress="return isNumberKey(event)" onmouseover="shows1(this,'Enter Work time details for Employees for whom time is different from the entry in Employee type')" onmouseout="hides1()"/>
					
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
								      <bean:message key="knowledgepro.employee.isTeachingStaff"/>
								    </div>
								    </td>
									<td class="row-even" width="36%">
									 <html:radio property="teachingStaff" styleId="teachingStaff"  value="1" onclick="getPayscalewithTeachingStaff(this.value)"/>Yes&nbsp; 
									<html:radio property="teachingStaff" styleId="teachingStaff"  value="0" onclick="getPayscalewithTeachingStaff(this.value)"/>No
									</td>
									<td class="row-odd" width="14%">
									 <div align="left">
									    &nbsp;&nbsp;Teacher code:
									 </div>
									  </td>
							
									<td  class="row-even" width="36%">
											<html:text property="code" styleId="code" size="25" maxlength="10"></html:text>
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
									<td width="100%" height="22" align="left" valign="top">
									<table width="100%" height="22" border="0" cellpadding="0">
										<tr class="row-white">
											<td width="50%" height="30" align="center" class="row-even">
												<%if(CMSConstants.LINK_FOR_CJC){ %>
													<img src='<%=request.getContextPath()%>/PhotoServlet' height="186Px" width="144Px" />
												<%} else{%>
													<img src='<%=request.getAttribute("EMP_IMAGE")%>' height="186Px" width="144Px" />
												<%} %>
											</td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="22"></td>
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
											<html:text property="name" styleId="name" size="35" maxlength="100" style="text-transform:uppercase;" ></html:text>
										</td>
										 <td class="row-odd" width="14%">
									<div align="left">
									     <bean:message key="knowledgepro.employee.upload.photo"/>:
									  </div>
									  </td>
									<td  class="row-even" width="36%">
											<html:file property="empPhoto"></html:file>
									</td>
							</tr>
									
								<tr> 
							  	 <td class="row-odd" >
							  	 <div align="left"><span class="Mandatory">*</span>
							      	<bean:message key="knowledgepro.admin.nationality"/>
							     </div>
							     </td>
								 <td  class="row-even" >
								 	 <html:select property="nationalityId">
								   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
								   	<logic:notEmpty property="nationalityMap" name="EmployeeInfoEditForm">
								   		<html:optionsCollection property="nationalityMap" label="value" value="key"/>
								   </logic:notEmpty>
							     </html:select> 
								 </td>
							  	<td class="row-odd" > 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.employee.gender.required" /></div>
							  	</td>
								<td height="17" class="row-even" align="left" >
									<nested:radio property="gender" value="MALE" name="EmployeeInfoEditForm"><bean:message key="admissionForm.studentinfo.sex.male.text"/></nested:radio>
									<nested:radio property="gender" value="FEMALE" ><bean:message key="admissionForm.studentinfo.sex.female.text"/></nested:radio>
								</td> 
							  </tr>
							  
							  <tr> 
							  	 <td class="row-odd"> 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="employee.info.personal.MaritalSts" /></div>
							  	</td>
								 <td  class="row-even">
								 	 <html:select property="maritalStatus">
								 	 <html:option value="">- Select - </html:option>
								   		<html:option value="Single">Single </html:option>
								   		<html:option value="Married">Married</html:option>
										<html:option value="Divorcee">Divorcee </html:option>
								   		<html:option value="Widow">Widow</html:option>
										<html:option value="Other">Other </html:option>
							    	 </html:select>
								 </td>
							  	<td class="row-odd"> 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.admin.dateofbirth" /></div>
							  	</td>
								<td   class="row-even">
									<html:text name="EmployeeInfoEditForm" property="dateOfBirth" styleId="dateOfBirth" size="10" maxlength="16" onchange="CalRetirementDate(), CalAge()" />
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'dateOfBirth'
												});
										</script>
								
								 	<html:text property="age" styleId="age" size="6"></html:text>
								 </td>
							  </tr>
							   <tr> 
							  <td height="25" class="row-odd"><div align="left">Blood Group:</div></td>
								<td height="25" class="row-even">
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
							<td height="20" class="row-odd">
							<div align="left"><span class="Mandatory">*</span>
							<bean:message key="admissionForm.studentinfo.religion.label" /></div></td>
								<td height="25" class="row-even">
							<html:select property="religionId" styleId="religionId">
								<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									<logic:notEmpty property="religionMap" name="EmployeeInfoEditForm">
								  		<html:optionsCollection property="religionMap" label="value" value="key"/>
								   </logic:notEmpty>
							 </html:select> 
									
				  </td>
				  </tr>
					  <tr>
					  	<td class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.panNo" /></div></td>
                  		<td class="row-even" ><span class="star">
						<html:text property="panno" size="10" maxlength="10"/></span></td>
					
				 		 <td class="row-odd"> 
								<div align="left">
									<bean:message key="knowledgepro.employee.officialEmailId" /></div>
							  	</td>
								 <td  class="row-even">
								 	<html:text property="officialEmail" size="20" maxlength="50"></html:text>
								 </td>
					</tr>
					<tr> 
							  	 <td class="row-odd"> 
									<div align="left">
									<bean:message key="admissionFormForm.emailId" /></div>
							  	</td>
								 <td  class="row-even">
								 	<html:text property="email" size="20" maxlength="50"></html:text>
								 </td>
							  		<td class="row-odd"> 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.employee.reservation.category" /></div>
							  		</td>
									<td class="row-even">
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
									
									<td class="row-odd" width="14%"> 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.employee.mobile" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="mobileNo1" onkeypress="return isNumberKey(event)" maxlength="10"></html:text>
									</td>
								
									<td class="row-odd" width="14%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.bankAccNo" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="bankAccNo" maxlength="20"></html:text>
									</td>
									</tr>
					<tr>
									<td class="row-odd" width="14%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.PfAccNo" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="pfNo" maxlength="25"></html:text>
									</td>
								
									<td class="row-odd" width="14%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.fourWheelerNo" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="fourWheelerNo" maxlength="15"></html:text>
									</td>
									</tr>
							
							<!-- by venkat -->
							
							<logic:equal value="true" name="EmployeeInfoEditForm" property="isCjc">
							<tr>
							<td class="row-odd" width="14%"> 
									<div align="left">
									<bean:message key="admissionFormForm.fatherName" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="fatherName" maxlength="15"></html:text>
									</td>
									<td class="row-odd" width="14%"> 
									<div align="left">
									<bean:message key="admissionFormForm.motherName" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="motherName" maxlength="25"></html:text>
									</td>
							</tr></logic:equal>
							
							<!-- by venkat -->
							
							<tr>
										<td class="row-odd">
									  	<div align="left">
											<bean:message key="knowledgepro.employee.Home.Telephone" />
											</div>		
											</td> 
											<td class="row-even"><b>
												<bean:message key="knowledgepro.employee.state.code" />
												<bean:message key="knowledgepro.employee.Telephone" /></b>
											</td>
										<td class="row-odd">
									  	<div align="left">
											</div>		
											</td> 
											<td class="row-even"></td>
											
								</tr>	
								
							  	<tr class="row-even">
							  	<td></td>
									<td  > 
									<html:text property="homePhone2" onkeypress="return isNumberKey(event)" size="4" maxlength="6"></html:text>
									<html:text property="homePhone3" styleId="homePhone3" onkeypress="return isNumberKey(event)" size="10" maxlength="15"></html:text>
									</td><td></td><td></td>
									
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
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
									<logic:equal value="true" name="EmployeeInfoEditForm" property="isCjc">
									  	<tr>
									   	 	<td class="row-odd" width="14%">
									   	 		<div align="left"><span class="Mandatory">*</span>
									     	 	<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     	 	</div>
									    	</td>
										 	<td  class="row-even" width="36%">
												 <html:text property="currentAddressLine1" styleId="currentAddressLine1" size="35" maxlength="100"></html:text>
											</td>
											
											<td class="row-odd" width="14%"> 
												<div align="left">
												<bean:message key="admissionForm.studentinfo.addrs2.label"/>
												</div>
											</td>
											<td class="row-even" width="36%">
												 <html:text property="currentAddressLine2" styleId="currentAddressLine2" size="40" maxlength="100"></html:text>
										 	</td>
										</tr>
										</logic:equal>
										<logic:equal value="false" name="EmployeeInfoEditForm" property="isCjc">
										<tr>
									   	 	<td class="row-odd" width="14%">
									   	 		<div align="left"><span class="Mandatory">*</span>
									     	 	House Name
									     	 	</div>
									    	</td>
										 	<td  class="row-even" width="36%">
												 <html:text property="currentAddressLine1" styleId="currentAddressLine1" size="35" maxlength="35"></html:text>
											</td>
											
											<td class="row-odd" width="14%"> 
												<div align="left">
												Place
												</div>
											</td>
											<td class="row-even" width="36%">
												 <html:text property="currentAddressLine2" styleId="currentAddressLine2" size="40" maxlength="40"></html:text>
										 	</td>
										</tr>
										</logic:equal> 
										<tr>
										 
										 <td class="row-odd">
									  	 <div align="left"><span class="Mandatory">*</span>
									      	<bean:message key="employee.info.contact.City"/>
									      </div>
									     </td>
										 <td  class="row-even">
										 	<html:text property="currentCity" styleId="currentCity" size="40" maxlength="40"></html:text>
										 </td>	
									  	 <td class="row-odd" align="left">
									      	<bean:message key="knowledgepro.usermanagement.userinfo.pincode"/>
									     </td>
										 <td  class="row-even">
										 	<html:text property="currentZipCode" styleId="currentZipCode" onkeypress="return isNumberKey(event)" size="10" maxlength="10"></html:text>
										 </td>
									  	</tr>
										
									   <tr>
									   	 <td class="row-odd">
									   	 	<div align="left"><span class="Mandatory">*</span>
											  <bean:message key="knowledgepro.admin.country.report"/>
											</div>
									     </td>
									     
									     <td  class="row-even">
											<html:select property="currentCountryId" styleId="currentCountryId" onchange="getCurrentStateByCountry(this.value,'currentState')">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="currentCountryMap" name="EmployeeInfoEditForm">
										   		<html:optionsCollection property="currentCountryMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select> 
									    
										</td>
										
									   	 <td class="row-odd">
									   	 <div align="left"><span class="Mandatory">*</span>
											  <bean:message key="knowledgepro.admin.state.report"/>
										</div>
									     </td>
										 <td  class="row-even">
										 <html:select property="currentState" styleId="currentState" onchange="getOtherCurrentState()">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="currentStateMap" name="EmployeeInfoEditForm">
										   		<html:optionsCollection property="currentStateMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select> 
									     <input type="hidden" id="tempState" name="tempState" value='<bean:write name="EmployeeInfoEditForm" property="currentState"/>' />
									    
									     <div id="otehrState">
									     	<html:text property="otherCurrentState" name="EmployeeInfoEditForm" size="10" maxlength="50"></html:text>
									     </div>
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
                  	<td colspan="2" class="heading">&nbsp;&nbsp;<bean:message key="knowledgepro.applicationform.sameaddr.label"/>
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
						
						
						<td valign="top" class="news" colspan="3">
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
									<logic:equal value="true" name="EmployeeInfoEditForm" property="isCjc">
									  	<tr>
									   	 	<td class="row-odd" width="14%">
									   	 		<div align="left"><span class="Mandatory">*</span>
									      		<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     		</div>
									    	</td>
										 	<td  class="row-even" width="36%">
												 <html:text property="addressLine1" styleId="addressLine1" size="35" maxlength="100"></html:text>
											</td>
											<td class="row-odd" width="14%"> 
												<div align="left">
									      		<bean:message key="admissionForm.studentinfo.addrs2.label"/>
									     		</div>
											</td>
											<td class="row-even" width="36%">
												 <html:text property="addressLine2" styleId="addressLine2" size="40" maxlength="100"></html:text>
										 	</td>
										</tr>
										</logic:equal>
										<logic:equal value="false" name="EmployeeInfoEditForm" property="isCjc">
										 <tr>
									   	 	<td class="row-odd" width="14%">
									   	 		<div align="left"><span class="Mandatory">*</span>
									      		<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     		</div>
									    	</td>
										 	<td  class="row-even" width="36%">
												 <html:text property="addressLine1" styleId="addressLine1" size="35" maxlength="35"></html:text>
											</td>
											<td class="row-odd" width="14%"> 
												<div align="left">
									      		<bean:message key="admissionForm.studentinfo.addrs2.label"/>
									     		</div>
											</td>
											<td class="row-even" width="36%">
												 <html:text property="addressLine2" styleId="addressLine2" size="40" maxlength="40"></html:text>
										 	</td>
										</tr>
										</logic:equal>
										<tr>
										 	 <td class="row-odd">
									  	 <div align="left"><span class="Mandatory">*</span>
									      	<bean:message key="employee.info.contact.City"/>
									      </div>
									     </td>
										 <td  class="row-even">
										 	<html:text property="city" styleId="city" size="40" maxlength="40"></html:text>
										 </td>
									  	 <td class="row-odd" align="left">
									      	<bean:message key="knowledgepro.usermanagement.userinfo.pincode"/>
									     </td>
										 <td  class="row-even" >
										 	<html:text property="permanentZipCode" styleId="permanentZipCode" onkeypress="return isNumberKey(event)" size="10" maxlength="10"></html:text>
										 </td>
									  	</tr>
										
									   <tr>
									   	 <td class="row-odd">
									   	 	<div align="left"><span class="Mandatory">*</span>
											  <bean:message key="knowledgepro.admin.country.report"/>
											</div>
									     </td>
										 <td  class="row-even">
											<html:select property="countryId" styleId="countryId" onchange="getStateByCountry(this.value,'stateId')">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="countryMap" name="EmployeeInfoEditForm">
										   		<html:optionsCollection property="countryMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select> 
										</td>
										
									   	 <td class="row-odd">
									   	 <div align="left"><span class="Mandatory">*</span>
											  <bean:message key="knowledgepro.admin.state.report"/>
										</div>
									     </td>
										 <td  class="row-even">
										 <html:select property="stateId" styleId="stateId" onchange="getOtherPermanentState()">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="stateMap" name="EmployeeInfoEditForm">
										   		<html:optionsCollection property="stateMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select> 
									     <input type="hidden" id="tempPermanentState" name="tempPermanentState" value='<bean:write name="EmployeeInfoEditForm" property="stateId"/>' />
									    
									     <div id="otehrPermState">
									     	<html:text property="otherPermanentState" name="EmployeeInfoEditForm" size="10" maxlength="50"></html:text>
									     </div>
									     </td>
									     </tr></table>
									    	
								
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
						<bean:message key="knowledgepro.employee.Job"/>
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
              <tr >
               <td width="14%" class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.joinedDate"/> 
                   </div></td>
                <td width="36%" class="row-even"><span class="star">
                 <html:text property="dateOfJoining" styleId="dateOfJoining"></html:text>
                </span> </td>
                
                <td width="14%" height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.DateOfRetirement"/> </div></td>
                <td width="36%" class="row-even"><html:text property="retirementDate" styleId="retirementDate"></html:text> 
                 <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'retirementDate'
												});
					</script></td>
               
              </tr>
              <tr >
                <td width="14%" class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.designation.album"/> </div></td>
                <td width="36%" class="row-even">
				<html:select property="albumDesignation" styleClass="comboMediumBig">
				   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="designationMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="designationMap" label="value" value="key"/>
						</logic:notEmpty>
				</html:select>
						</td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.streamDetails"/></div></td>
                <td height="25" class="row-even" ><span class="star">
                 <html:select property="streamId" styleId="streamId" styleClass="comboMediumBig" onchange="searchStreamWise(this.value)">>
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="streamMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="streamMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
                </span>				
                </td>
              </tr>
               <logic:equal value="false" name="EmployeeInfoEditForm" property="isCjc">
              <tr>
              	<td width="14%"  class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.Department"/>  </div></td>
                <td width="36%" class="row-even">
                <html:select property="departmentId" styleId="departmentId" styleClass="comboExtraLarge">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="departmentMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="departmentMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
				</td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.active"/></div></td>
                <td class="row-even">
				<html:radio property="active" value="1"/>Yes&nbsp; 
				 <html:radio property="active" value="0"/>No
				</td>
              </tr>
             <tr >
				<td width="14%"  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.title"/>  </div></td>
                <td width="36%" class="row-even" >
                <html:select property="titleId" styleId="titleId" styleClass="comboMediumBig" onchange="checkother('titleId','othTitle');">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="titleMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="titleMap" label="value" value="key"/>
					 </logic:notEmpty>
					 <html:option value="Other">Other</html:option>
				</html:select>
				<br>
					<html:text property="titleOthers"  name="EmployeeInfoEditForm" maxlength="30" styleId="othTitle"
						styleClass="textbox"></html:text>
				</td>
				<td  class="row-odd"><div align="left" ></div></td>
                <td height="25" class="row-even" ><span class="star">
               </span>
                </td>   
              </tr>
              
              </logic:equal>
               <logic:equal value="true" name="EmployeeInfoEditForm" property="isCjc">
                             <tr>
                <td width="14%" class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.worklocation"/></div></td>
                <td height="36" class="row-even" ><span class="star">
                <html:select property="workLocationId" styleId="workLocationId" styleClass="comboMediumBig">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="workLocationMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="workLocationMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
                </span></td>
                <td width="14%" class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.designation"/> </div></td>
                <td width="36%" class="row-even">
				<html:select property="designationPfId" styleClass="comboMediumBig">
				   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="designationMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="designationMap" label="value" value="key"/>
						</logic:notEmpty>
				</html:select>
						</td>
              </tr>
              <tr>
              <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.deputaion.to.department"/></div></td>
                <td height="25" class="row-even" >
                <html:select property="deputationDepartmentId" styleId="deputationDepartmentId" styleClass="comboMediumBig">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="deputationDepartmentMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="deputationDepartmentMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
                </td>
                <td width="14%"  class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.Department"/>  </div></td>
                <td width="36%" class="row-even">
                <html:select property="departmentId" styleId="departmentId" styleClass="comboExtraLarge">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="departmentMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="departmentMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
				</td>   
              </tr>
             <tr >
				 <td width="14%"  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.title"/>  </div></td>
                <td width="36%" class="row-even" >
                <html:select property="titleId" styleId="titleId" styleClass="comboMediumBig">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="titleMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="titleMap" label="value" value="key"/>
					 </logic:notEmpty>
					 <html:option value="Other">Other</html:option>
				</html:select>
				</td>
				<td height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.active"/> </div></td>
                <td class="row-even"><span class="star">
                 <html:radio property="active" value="1"/>Yes&nbsp; 
				 <html:radio property="active" value="0"/>No</span>
                </td>
              </tr>
               </logic:equal>
 </table></td>
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
						<bean:message key="knowledgepro.employye.educational.details"/>
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table id="Education" width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
					
		
							<tr>
								
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<div id="Education">
								<table width="100%" cellspacing="1" cellpadding="2">
								 <tr>
								 
								  <td class="row-odd">
									  	 	<div align="left"><span class="Mandatory">*</span>
									      	<bean:message key="knowledgepro.employee.education.qualification.level"/>
									      	</div>
									     </td>
										 <td  class="row-even" colspan="2">
										 <html:select property="qualificationId" styleId="qualificationId" onchange="checkotherquali('qualificationId','otherquali','highQualifForAlbum','highquaOther');">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="qualificationMap" name="EmployeeInfoEditForm">
										   		<html:optionsCollection property="qualificationMap" label="value" value="key"/>
										   </logic:notEmpty>
										   <html:option value="Other">Other</html:option>
									     </html:select>
									     <html:text property="qualificationOthers"  name="EmployeeInfoEditForm" maxlength="30" styleId="otherquali"
												styleClass="textbox"></html:text>
										 </td>
										  <td width="28%"  class="row-odd"><div align="left"><bean:message key="knowledgepro.employee.HighQyalForAlbum"/></div></td>
                						<td width="22%" class="row-even" colspan="2">
               							 <html:text property="highQualifForAlbum" styleId="highQualifForAlbum" maxlength="50"></html:text>
               							  <html:text property="highestQualification" styleId="highquaOther" maxlength="50"></html:text>
								</td>
								 </tr>
								
									
								
								
								
								
								
								
								 <%-- <tr>
							  		<td class="row-odd"> 
							  		<div align="left">
										<bean:message key="knowledgepro.employee.publications.refered"/>:
									</div>		
									</td>
									<td class="row-even">
										<html:text property="noOfPublicationsRefered" styleId="noOfPublicationsRefered" size="10" maxlength="5" onkeypress="return isNumberKey(event)"></html:text>		
									</td>
									<td class="row-odd"> 
							  		<div align="left">
										<bean:message key="knowledgepro.employee.non.refered"/>:
									</div>		
									</td>
									<td class="row-even">
										<html:text property="noOfPublicationsNotRefered" styleId="noOfPublicationsNotRefered" size="10" maxlength="5" onkeypress="return isNumberKey(event)"></html:text>		
									</td>
									<td class="row-odd"> 
							  		<div align="left">
										<bean:message key="knowledgepro.employee.books"/>:
									</div>		
									</td>
									<td class="row-even">
										<html:text property="books" styleId="books" onkeypress="return isNumberKey(event)" size="10" maxlength="5"></html:text>		
									</td>
								</tr> --%>	
								 
								</table>			
								</div>
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
				<tr><td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					<tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.online.resume.research"/>
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
								  <td class="row-odd" height="12" width="20%"> 
									  	<div align="right">
											<bean:message key="knowledgepro.employee.online.resume.research.papers"/>:
											</div>		
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.refer"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="researchPapersRefereed" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td> 
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.non.refer"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="researchPapersNonRefereed" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.proceedings"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="researchPapersProceedings" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
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
											<html:text property="internationalPublications" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
										</td>
										<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.national"/>:
										</td>
										<td class="row-even"  align="left" width="10%">
											<html:text property="nationalPublications" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
										</td>
										<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.local"/>:
										</td>
										<td class="row-even"  align="left" width="10%">
											<html:text property="localPublications" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
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
											<html:text property="international" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.national"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="national" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
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
											<html:text property="majorProjects" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.minor"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="minorProjects" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.consultancy1"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="consultancyPrjects1" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.consultancy2"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="consultancyProjects2" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
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
											<html:text property="phdResearchGuidance" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.mphil"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="mphilResearchGuidance" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
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
											<td class="row-even"  align="right" width="10%">FDP</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="fdp1Training" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											Refresher Courses
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="refresherCourse" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even" width="10%">Orientation Courses</td>
											<td class="row-even" width="10%"><html:text property="orientationCourse" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text></td>
											<td class="row-even" width="10%"></td>
											<td class="row-even" width="10%"></td>
									  </tr>
									  <tr>
										<td class="row-odd"> 
									  	<div align="right">
											Seminar/Conference Attended:
											</div>		
											</td> 
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.international"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="internationalConference" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.national"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="nationalConference" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											<bean:message key="knowledgepro.employee.online.resume.research.regional"/>:
											</td>
											<td class="row-even"  align="left" width="10%">
											<html:text property="regionalConference" name="EmployeeInfoEditForm" size="10" style="height:20px;" maxlength="4"></html:text>
											</td>
											<td class="row-even"  align="right" width="10%">
											</td>
											<td class="row-even"  align="left" width="10%">
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
						<bean:message key="knowledgepro.employee.EmergencyContactDetails"/>
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
			<table width="100%" height="43" border="0" cellpadding="0" cellspacing="1">
                      <tr >
                      
                       <td   class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.EmContact.name"/></div></td>
               			 <td  class="row-even">
                		<html:text  property="emContactName" styleId="emContactName" size="50" maxlength="100" />
               			 </td>
             			 <td   class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.EmContact.address"/></div></td>
               			 <td  class="row-even">
                		<html:text  property="emContactAddress" styleId="emContactAddress" size="50" maxlength="100" />
               			 </td>
               			 </tr>
               			 <tr>
             			 <td   class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.EmContact.relationship"/></div></td>
              		  		<td  class="row-even">
                 			<html:text  property="emContactRelationship" styleId="emContactRelationship" size="30" maxlength="50" />
               		 	</td>
               		 	           		 	
                		<td   class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.EmContact.mobile"/></div></td>
                		<td  class="row-even">
               		 	<html:text  property="emContactMobile" styleId="emContactMobile" size="20" maxlength="20" onkeypress="return isNumberKey(event)" />
                		</td>
             		 </tr>
             		  <tr>
             		  <td   class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.EmContact.homeTelephone"/></div></td>
                		<td  class="row-even">
               		 	<html:text  property="emContactHomeTel" styleId="emContactHomeTel" size="20" maxlength="20" />
                		</td>
             		 	<td   class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.EmContact.workTelephone"/></div></td>
                		<td  class="row-even">
               		 	<html:text  property="emContactWorkTel" styleId="emContactWorkTel" size="20" maxlength="20" />
                		</td>
                     </table></td>
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
			var title=document.getElementById("othTitle").value;
			if (title!="") {
				document.getElementById("othTitle").style.display="block";
			}else{
				document.getElementById("othTitle").style.display="none";
			}
			
			var title=document.getElementById("otherquali").value; 
			if (title!="") {
				document.getElementById("otherquali").style.display="block";
				document.getElementById("highquaOther").style.display="block";
				document.getElementById("highQualifForAlbum").style.display="none";
			}else{
				document.getElementById("otherquali").style.display="none";
				document.getElementById("highquaOther").style.display="none";
			}
		</script>
	</html>
			
		
			
	


