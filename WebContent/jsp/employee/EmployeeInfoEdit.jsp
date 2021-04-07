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
								      <bean:message key="knowledgepro.employee.isPunchingExcemption"/>
								    </div>
								    </td>
								    <td  class="row-even" width="36%">
									<html:radio property="isPunchingExcemption" styleId="isPunchingExcemption" value="1" ><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
									<html:radio property="isPunchingExcemption" styleId="isPunchingExcemption"  value="0" ><bean:message key="knowledgepro.applicationform.No.label"/></html:radio></td>
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
											<html:text property="name" styleId="name" size="35" maxlength="100" style="text-transform:uppercase;"></html:text>
										</td>
										 <td class="row-odd" width="14%">
									<div align="left">
									     <bean:message key="knowledgepro.admin.uId"/>
									  </div>
									  </td>
									<td  class="row-even" width="36%">
											<html:text property="uId" styleId="uId" size="25" maxlength="15"></html:text>
									</td>
							</tr>
								<tr>
									 <td class="row-odd" width="14%">
									 <div align="left">
									    &nbsp;&nbsp;Teacher code:
									 </div>
									  </td>
							
									<td  class="row-even" width="36%">
											<html:text property="code" styleId="code" size="25" maxlength="10"></html:text>
									</td>
									<td class="row-odd" width="14%">
									 <div align="left"><span class="Mandatory">*</span>
									     <bean:message key="knowledgepro.admin.fingerprintid"/>
									 </div>
									  </td>
							
									<td  class="row-even" width="36%">
											<html:text property="fingerPrintId" styleId="fingerPrintId" size="25" maxlength="30"></html:text>
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
								
								 	<html:text property="age" styleId="age" readonly="true" size="6"></html:text>
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
							<tr>
									<td class="row-odd" width="14%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.twoWheelerNo" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="twoWheelerNo" maxlength="15"></html:text>
									</td>
								
								
									<td class="row-odd" width="14%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.smartCardNo" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="smartCardNo" maxlength="25"></html:text>
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
											<bean:message key="knowledgepro.employee.upload.photo"/>:
											</div>		
											</td> 
											<td class="row-even">
												<html:file property="empPhoto"></html:file>
											</td>
										<td class="row-odd">
									  	<div align="left">
											Extension Number:
											</div>		
											</td> 
											<td class="row-even">
												<html:text property="extensionNumber" maxlength="20"></html:text>
											</td>
								</tr>	
								<tr  class="row-odd">
									<td > 
									<bean:message key="knowledgepro.employee.Home.Telephone" />
									
							  		</td>
							  		<td > 
									<bean:message key="knowledgepro.employee.county" />
									<bean:message key="knowledgepro.employee.state.code" />
									<bean:message key="knowledgepro.employee.Telephone" />
							  	</td>
							  	<td > 
									<bean:message key="knowledgepro.employee.Work.Telephone" />
									
							  		</td>
							  		<td > 
									<bean:message key="knowledgepro.employee.county" />
									<bean:message key="knowledgepro.employee.state.code" />
									<bean:message key="knowledgepro.employee.Telephone" />
							  		</td>
							  	</tr>
							  	<tr class="row-even">
							  	<td></td>
									<td  > 
									<html:text property="homePhone1" onkeypress="return isNumberKey(event)" size="4" maxlength="6"></html:text>
									<html:text property="homePhone2" onkeypress="return isNumberKey(event)" size="4" maxlength="6"></html:text>
									<html:text property="homePhone3" styleId="homePhone3" onkeypress="return isNumberKey(event)" size="10" maxlength="15"></html:text>
									</td>
									
									<td></td>
									<td > 
									<html:text property="workPhNo1" onkeypress="return isNumberKey(event)" size="4" maxlength="6"></html:text>
									<html:text property="workPhNo2" onkeypress="return isNumberKey(event)" size="4" maxlength="6"></html:text>
									<html:text property="workPhNo3" onkeypress="return isNumberKey(event)" size="10" maxlength="15"></html:text>
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
									     	 	<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     	 	</div>
									    	</td>
										 	<td  class="row-even" width="36%">
												 <html:text property="currentAddressLine1" styleId="currentAddressLine1" size="35" maxlength="35"></html:text>
											</td>
											
											<td class="row-odd" width="14%"> 
												<div align="left">
												<bean:message key="admissionForm.studentinfo.addrs2.label"/>
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
                <td width="14%" height="25" class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.empType"/></div></td>
                <td width="36%" class="row-even">
                 <html:select property="emptypeId" styleId="emptypeId" onchange="getEmpLeaveList(this.value)">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="empTypeMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="empTypeMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
                              </td>
                <td width="14%" class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.joinedDate"/> 
                   </div></td>
                <td width="36%" class="row-even"><span class="star">
                 <html:text property="dateOfJoining" styleId="dateOfJoining"></html:text>
                 <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'dateOfJoining'
												});
					</script>
                </span> </td>
              </tr>
              <tr >
                <td height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.rejoinDate"/> </div></td>
                <td class="row-even"><span class="star">
                 <html:text property="rejoinDate" styleId="rejoinDate"></html:text>
                <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'rejoinDate'
												});
					</script>
                </span></td>
                <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.DateOfRetirement"/>  </div></td>
                <td height="25" class="row-even" > <html:text property="retirementDate" styleId="retirementDate"></html:text> 
                 <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'retirementDate'
												});
					</script>
                              </td>
              </tr>
              <tr>
                  <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.appointment.letter.date"/>  </div></td>
                <td height="25" class="row-even" > <html:text property="appointmentLetterDate" styleId="appointmentLetterDate"></html:text> 
                 <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'appointmentLetterDate'
												});
					</script>
                              </td>
                              <td class="row-odd" align="left">
									      	<bean:message key="knowledgepro.employee.referenceNumber"/>
									     </td>
										 <td  class="row-even" >
										 	<html:text property="referenceNumberForAppointment" styleId="referenceNumberForAppointment" size="30" maxlength="50"></html:text>
										 </td>
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
                <td  class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.designation"/> </div></td>
                <td class="row-even">
				<html:select property="designationPfId" styleClass="comboMediumBig">
				   <html:option value=""><bean:message key="knowledgepro.select" /></html:option>
						<logic:notEmpty property="designationMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="designationMap" label="value" value="key"/>
						</logic:notEmpty>
				</html:select>
						</td>
              </tr>
             <tr >
                   <td  class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.worklocation"/></div></td>
                <td height="25" class="row-even" ><span class="star">
                <html:select property="workLocationId" styleId="workLocationId" styleClass="comboMediumBig">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="workLocationMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="workLocationMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
                </span></td>             
				<!-- <td width="14%"  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.ReportTo"/>  </div></td>
                <td width="36%" class="row-even">
                
				<html:select property="reportToId" styleId="reportToId" styleClass="comboMediumBig">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="reportToMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="reportToMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
				</td>-->
				
				<td width="14%"  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.title"/>  </div></td>
                <td width="36%" class="row-even" >
                <html:select property="titleId" styleId="titleId" styleClass="comboMediumBig">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="titleMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="titleMap" label="value" value="key"/>
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
                <td  class="row-odd"></td>
                <td  class="row-even"></td>
              </tr>
              <tr>
              	<td height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.active"/> </div></td>
                <td class="row-even"><span class="star">
                 <html:radio property="active" value="1"/>Yes&nbsp; 
				 <html:radio property="active" value="0"/>No</span>
                </td>
               	<td height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.diplay.in.website"/> </div></td>
                <td class="row-even"><span class="star">
                 <html:radio property="displayInWebsite" value="1"/>Yes&nbsp; 
				 <html:radio property="displayInWebsite" value="0"/>No</span>
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
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.professional.exp"/>
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
									  	<td class="row-odd" width="75"> 
											<div align="left"><span class="Mandatory">*</span>
											<bean:message key="knowledgepro.employee.previous.working" /></div>
								  		</td>
										<td class="row-even" width="100" colspan="6">
											<nested:radio property="currentlyWorking" value='YES'>
												<bean:message key="knowledgepro.yes" />
												</nested:radio> 
												<nested:radio property="currentlyWorking" value='NO'>
												<bean:message key="knowledgepro.no" />
											</nested:radio>									
										</td> 
									</tr>
									
									<tr>
									        <td class="row-odd"> 
											
											<bean:message key="knowledgepro.usermanagement.userinfo.designation" />
									  		</td>
									       <td  class="row-even" colspan="2">
									       <html:text property="designation" size ="48" maxlength="50"></html:text>
											<!-- <html:select property="designationPfId">
											   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
											   	<logic:notEmpty property="designationMap" name="EmployeeInfoEditForm">
											   		<html:optionsCollection property="designationMap" label="value" value="key"/>
											   </logic:notEmpty>
										     </html:select>-->
									     </td>
										<td class="row-odd"> 
											
											<bean:message key="knowlegepro.employee.address.organization" />
									  		</td>
										<td class="row-even" colspan="3">
											<html:text property="orgAddress" styleId="orgAddress" size ="50" maxlength="100"></html:text>
										</td>
									</tr>
									
									<tr>
										<td class="row-even" width="12%" height="25"> 
										</td>
										<td class="row-odd" width="15%">
									  	 	<div align="left">
									      	<bean:message key="knowledgepro.employee.subject.area"/>
									      	</div>
									     </td>
										<td class="row-odd" width="15%" height="25" align="left"> 
											<bean:message key="knowledgepro.admission.fromdate" />
									  		</td>
										
										<td class="row-odd" width="15%" align="left"> 
											<bean:message key="knowledgepro.admission.todate"/>
										</td>
										<td class="row-odd" width="16%" align="left"> 
											<bean:message key="knowledgepro.employee.years"/>
											&nbsp;
											<bean:message key="knowledgepro.employee.months"/>
										</td>
										<td class="row-odd" width="22%" align="left"> 
											<bean:message key="knowledgepro.admin.sec.Designation"/>
										</td>
										<td class="row-odd" width="20%" align="left" colspan="1"> 
											<bean:message key="knowledgepro.institution.col"/>
										</td>
									</tr>
									<html:hidden property="teachingExpLength" name="EmployeeInfoEditForm" styleId="teachingExpLength"/>
									<html:hidden property="industryExpLength" name="EmployeeInfoEditForm" styleId="industryExpLength"/>
									<logic:notEmpty property="employeeInfoTONew.teachingExperience" name="EmployeeInfoEditForm">     
									<nested:iterate property="employeeInfoTONew.teachingExperience" name="EmployeeInfoEditForm" id="teachingExp" indexId="count">
								<tr>
									<!--<c:choose>
										<c:when test="${count==0}">
											<td class="row-odd"> 
												
												<bean:message key="knowledgepro.employee.teaching.experience" />
										  	</td>
										</c:when>
										<c:otherwise>
											<td class="row-odd"> 
										  	</td>
										</c:otherwise>
									</c:choose>-->
									<c:choose>
										<c:when test="${count==0}">
											<td class="row-odd"> 
												<bean:message key="knowledgepro.employee.teaching.experience" />
										  	</td>
										  	<td  class="row-even">
										 <html:select property="empSubjectAreaId" styleId="empSubjectAreaId" styleClass="comboMediumLarge">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="subjectAreaMap" name="EmployeeInfoEditForm">
										   		<html:optionsCollection property="subjectAreaMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select>
										 </td>
										</c:when>
										<c:otherwise>
											<td class="row-odd"> 
										  	</td>
										  	<td class="row-even"> 
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
									<td class="row-even" align="left" width="12%">
								               <nested:text property="teachingFromDate" styleId="<%=teachingFromDate%>" styleClass="TextBox" size="9"  maxlength="30" onchange="<%=method %>"></nested:text>
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'<%=teachingFromDate%>'
												});
										</script>
										</td>
									<td class="row-even" align="left" width="12%">
								               <nested:text property="teachingToDate" styleId="<%=teachingToDate%>" styleClass="TextBox" size="9"  maxlength="30" onchange="<%=method %>"></nested:text>
										<script language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'<%=teachingToDate%>'
												});
										</script>
									</td>
									<%int number=0; %>
									<%String teaching="teach_"+count;%>
			                            <td class="row-even" align="left">
								              <nested:text property="teachingExpYears" styleClass="TextBox" size="3" maxlength="30" onkeypress="return isNumberKey(event)" onkeyup="getYears(this)" styleId="<%=teaching%>"></nested:text>
									<%String teachingMonth="teach_month_"+count;%>	
								              <nested:text property="teachingExpMonths" styleClass="TextBox" size="3" maxlength="30" onkeypress="return isNumberKey(event)" onkeyup="getMonths(this)" styleId="<%=teachingMonth%>"></nested:text>
										</td>
										<td class="row-even" align="left">
								              <nested:text property="currentTeachnigDesignation" styleId="currentTeachnigDesignation" styleClass="TextBox" size="20" maxlength="100" ></nested:text>
										</td>
										
										<td class="row-even" align="left">
								               <nested:text property="currentTeachingOrganisation" styleClass="TextBox" size="20" maxlength="100" ></nested:text>
										</td>
							 </tr>
							<!--  <tr>
							 <td><div id="teachingRow_"></div></td>
							 </tr> -->
							
			                 </nested:iterate>
			       <tr>
			                         	<td  class="row-even" align="center" colspan="7">
			                         	
			                         	<!--<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="getEmployeeTeachingExpAdd('ExpAddMore')"></html:submit>-->
				                        	<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
										<c:if test="${EmployeeInfoEditForm.teachingExpLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        	</c:if>
				                        <c:if test="${EmployeeInfoEditForm.teaching == 'true'}">
				                        	<script type="text/javascript">
			                        	var count = document.getElementById("teachingExpLength").value;
			                        	calculateDates(count);
			                        	</script>
			                        	</c:if>
										</td> 
						</tr>
			                         <tr>
			                         	<td  class="row-even" align="center" colspan="7" height="20">
										</td> 
			                         </tr>
									</logic:notEmpty>
									
									
									<logic:notEmpty property="employeeInfoTONew.experiences" name="EmployeeInfoEditForm">          
									<nested:iterate property="employeeInfoTONew.experiences" name="EmployeeInfoEditForm" id="experience" indexId="counter">
									<tr>
									<c:choose>
									<c:when test="${counter==0}">
										<td class="row-odd"> 
											<bean:message key="knowledgepro.employee.industry.exp" /></td>
										<td  class="row-even">
										 <nested:text name="EmployeeInfoEditForm" styleClass="TextBox" size="20"  maxlength="50" property="industryFunctionalArea" styleId="industryFunctionalArea">
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
								               <nested:text property="industryFromDate" styleId="<%=industryFromDate%>" styleClass="TextBox" size="9"  maxlength="30" onchange="<%=method1 %>"></nested:text>
										<script
												language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'<%=industryFromDate%>'
												});
										</script>
									</td>
									<td class="row-even" align="left">
										<%
										String industryToDate = "industryToDate_" + counter;
										%>
									
								               <nested:text property="industryToDate" styleId="<%=industryToDate%>" styleClass="TextBox" size="9"  maxlength="30" onchange="<%=method1 %>"></nested:text>
										<script
												language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'<%=industryToDate%>'
												});
										</script>
									</td>
									<%String industry="industry_"+String.valueOf(counter);%>
			                            <td class="row-even" align="left">
								               <nested:text property="industryExpYears" styleClass="TextBox" size="3"  maxlength="30" onkeypress="return isNumberKey(event)" onkeyup="getYears(this)" styleId="<%=industry%>"></nested:text>
									<%String industryMonth="industry_month_"+String.valueOf(counter);%>	
								               <nested:text property="industryExpMonths" styleClass="TextBox" size="3"   maxlength="30" onkeypress="return isNumberKey(event)" onkeyup="getMonths(this)" styleId="<%=industryMonth%>"></nested:text>
										</td>
										
										<td class="row-even" align="left">
								               <nested:text property="currentDesignation" styleId="currentDesignation" styleClass="TextBox" size="20" maxlength="30" ></nested:text>
										</td>
										
										<td class="row-even" align="left">
								               <nested:text property="currentOrganisation" styleClass="TextBox" size="20" maxlength="50" ></nested:text>
										</td>
									</tr>
			                         </nested:iterate>
			                         <tr>
			                         	<td  class="row-even" align="center" colspan="7">
			                           <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('resetExperienceInfo','ExpAddMore'); return false;"></html:submit>
										 <c:if test="${EmployeeInfoEditForm.industryExpLength>0}">
				                        	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        </c:if>
				                        <c:if test="${EmployeeInfoEditForm.industry == 'true'}">
											<script type="text/javascript">
				                        	var count = document.getElementById("industryExpLength").value;
				                        	calculateDates1(count);
			                        		</script>
			                        	</c:if>
										</td> 
									</tr>
									</logic:notEmpty>
									
									
									<tr>
									  	<td class="row-odd"> 
									  		
											Previous Experience
											
									  	</td>
										<td class="row-even" colspan="2">
											 <html:text styleId="expYears" name="EmployeeInfoEditForm" property="expYears" size="3" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;&nbsp;Years&nbsp;&nbsp;
											 <html:text styleId="expMonths" name="EmployeeInfoEditForm" property="expMonths" size="3" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;&nbsp;Months
										</td> 
										<td class="row-odd"> 
									  		
											<bean:message key="knowledgepro.employee.Relevanttotal.exp"/>
											
									  	</td>
										<td class="row-even" colspan="7">
											 <html:text styleId="relevantExpYears" name="EmployeeInfoEditForm" property="relevantExpYears" size="3" maxlength="5" onkeypress="return isNumberKey(event)" onkeyup="calcRecogYearsAndMonths()"/>&nbsp;&nbsp;Years&nbsp;&nbsp;
											 <html:text styleId="relevantExpMonths" name="EmployeeInfoEditForm" property="relevantExpMonths" size="3" maxlength="5" onkeypress="return isNumberKey(event)" onkeyup="calcRecogYearsAndMonths()"/>&nbsp;&nbsp;Months
										</td> 
										
									  </tr>
									  <tr> 
									  									 
									  <td class="row-odd"> 
									  			<div align="left">
												<bean:message key="knowledgepro.employee.eligibility.test"/>
												</div>
									  		</td>
											<td class="row-even" colspan="2">
												<logic:notEmpty name="EmployeeInfoEditForm" property="eligibilityList">
													<logic:iterate id="eTest" name="EmployeeInfoEditForm" property="eligibilityList" indexId="count">
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
									     			<html:text property="otherEligibilityTestValue" styleId="otherEligibilityTestValue" name="EmployeeInfoEditForm" size="10" maxlength="50"></html:text>
									    		 </div>
												
											</td> 
											<td class="row-odd">
											<table>
											<tr>
											<td class="row-odd" height="15"> 
									  			
									  			<% if(CMSConstants.LINK_FOR_CJC){ %>
												Experience in (CJC)
												<%}else{ %> 
												Experience in (CU)
												<%} %>
												
												<br></br>
									  		</td>
									  		
									  		</tr>
									  		<tr>
									  		<td class="row-odd"> 
									  			<div align="left">
									  			Total Current Experience
												</div>
									  		</td>
											</tr>
											</table>
											</td>
											
											<td class="row-even" colspan="7">
											<table>
											<tr>
											<td class="row-even"> 
												<input type="hidden" name="yr" id="year"  value='<bean:write name="EmployeeInfoEditForm" property="currentExpYears" />'/>
												<input type="hidden" name="mon" id="month"  value='<bean:write name="EmployeeInfoEditForm" property="currentExpMonths" />'/>
												<bean:write name="EmployeeInfoEditForm" property="currentExpYears" />&nbsp;&nbsp;Years&nbsp;&nbsp;
												<bean:write name="EmployeeInfoEditForm" property="currentExpMonths"/>&nbsp;&nbsp;Months									  			
												<br></br>
									  		</td>
									  		</tr>
									  		<tr>
									  		<td class="row-even"> 
									  			<div id="totYears" >
									  	<script type="text/javascript">
									  	calcRecogYearsAndMonths();
      									</script></div>
									  		</td>
									  		
											</tr>
											</table>
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
										 <html:select property="qualificationId" styleId="qualificationId">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="qualificationMap" name="EmployeeInfoEditForm">
										   		<html:optionsCollection property="qualificationMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select>
										 </td>
										  <td width="28%"  class="row-odd"><div align="left"><bean:message key="knowledgepro.employee.HighQyalForAlbum"/></div></td>
                						<td width="22%" class="row-even" colspan="2">
               							 <html:text property="highQualifForAlbum" styleId="highQualifForAlbum" maxlength="50"></html:text>
								</td>
								 </tr>
								
									<tr>
									<td class="row-odd" width="15%">
										<bean:message key="knowledgepro.employee.education.qualification"/>
									</td>
									<td class="row-odd" width="15%">
										<bean:message key="knowledgepro.exam.course"/>
									</td>
									<td class="row-odd" width="15%">
										<bean:message key="knowledgepro.exam.specialization"/>
									</td>
									<td class="row-odd" width="15%">
										<bean:message key="knowledgepro.employee.yeat.completion"/>
									</td>
									<td class="row-odd" width="15%">
										<bean:message key="knowledgepro.employee.grade"/>
									</td>
									<td class="row-odd" width="15%">
										<bean:message key="knowledgepro.employee.institute.univetsity"/>
									</td>
									
								</tr>
								<logic:notEmpty property="employeeInfoTONew.empQualificationFixedTo" name="EmployeeInfoEditForm">
									<nested:iterate id="qualificationTo" property="employeeInfoTONew.empQualificationFixedTo" name="EmployeeInfoEditForm" indexId="yr">
									<tr>
									<td class="row-odd" width="15%">
									<div align="left">
										<bean:write name="qualificationTo"  property="qualification"/>
									</div>
									</td>
									<%
										String stylecourse = "course_" + yr;
									%>
									<td class="row-even" width="15%">
										<nested:text property="course" styleId="<%=stylecourse%>" maxlength="100"></nested:text>
									</td>
									<td class="row-even" width="15%">
										<nested:text property="specialization" maxlength="50"></nested:text>
									</td>
									<%String dynaYearId="YOP"+yr; 
									%>
									<td class="row-even" width="15%">
										<c:set var="dyopid"><%=dynaYearId %></c:set>
											<nested:select property="yearOfComp"  styleId='<%=dynaYearId %>' styleClass="comboMedium">
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
													<cms:renderEmployeeYear normalYear="true"></cms:renderEmployeeYear>
										</nested:select>
										<script type="text/javascript">
											var opid= '<nested:write property="yearOfComp"/>';
											if(opid!=0)
											document.getElementById("<c:out value='${dyopid}'/>").value = opid;
										</script>
									</td>
									<td class="row-even" width="15%">
										<nested:text property="grade" size="10" maxlength="40"></nested:text>
									</td>
									<td class="row-even" width="15%">
										<nested:text property="institute" maxlength="100"></nested:text>
									</td>
									
								</tr>
								</nested:iterate>
								</logic:notEmpty>
								
								<logic:notEmpty property="employeeInfoTONew.empQualificationLevelTos" name="EmployeeInfoEditForm">
									<nested:iterate id="levelTo" property="employeeInfoTONew.empQualificationLevelTos" name="EmployeeInfoEditForm" indexId="yrs">
									<tr>
									<td class="row-odd">
									<div align="left">
									<nested:select property="educationId" >
										<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									  	<logic:notEmpty property="qualificationLevelMap" name="EmployeeInfoEditForm">
									  		<html:optionsCollection property="qualificationLevelMap" label="value" value="key" name="EmployeeInfoEditForm"/>
									   </logic:notEmpty>
									</nested:select>
									</div>
									</td>
									<%
										String stylecourse1 = "course_" + yrs;
									%>
									<td class="row-even" >
										<nested:text property="course" styleId="<%=stylecourse1%>" maxlength="100"></nested:text>
									</td>
									<td class="row-even">
										<nested:text property="specialization" maxlength="50"></nested:text>
									</td>
															
									
									<%String dynaYrId="YEAR"+yrs;%>
									<td class="row-even">
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
									<td class="row-even">
										<nested:text property="grade" size="10" maxlength="40"></nested:text>
									</td>
									<td class="row-even">
										<nested:text property="institute" maxlength="100"></nested:text>
									</td>
									<td class="row-odd">
									</td>
								</tr>
								</nested:iterate>
								</logic:notEmpty>
								
								<tr>
			                        <td  class="row-even" align="center" colspan="8">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addQualificationLevel','ExpAddMore'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditForm.levelSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeQualificationLevel','ExpAddMore'); return false;"></html:submit>
			                         </c:if>
									
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
						<bean:message key="knowledgepro.employee.LeaveType"/>
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
											<!-- <td height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.employee.Leave.slno"/></div>
											</td>-->
											<td class="row-odd"><bean:message key="knowledgepro.employee.Leave.leaveType"/>
											</td>
											<td class="row-odd"><bean:message key="knowledgepro.employee.Leave.Allocated"/></td>
											<td class="row-odd"><bean:message key="knowledgepro.employee.Leave.leaveSanctioned"/></td>
											<td height="25" class="row-odd"><bean:message key="knowledgepro.employee.Leave.leaveRemaining"/> 	</td>
			  </tr>
			  
			   	<logic:notEmpty property="employeeInfoTONew.empLeaveToList" name="EmployeeInfoEditForm">
				<nested:iterate id="empLeaveToList" property="employeeInfoTONew.empLeaveToList" name="EmployeeInfoEditForm" indexId="count">
								<%String allocatedLeave_count="leave_Allocated_"+count;
								String sanctionedLeave_count="leave_Sanctioned_"+count;
								String remainingLeave_count="leave_Remaining_"+count;
								String avgMethod="calc("+count+")"; %>
								
								
								 <tr>
									<td class="row-even"> <nested:text  property="leaveType" styleId="leaveType" size="20" maxlength="50" readonly="true" ></nested:text></td>
									<td class="row-even"> <nested:text  property="allottedLeave"  size="10" maxlength="20" styleId="<%=allocatedLeave_count%>" onchange="<%=avgMethod%>"></nested:text></td>
									<td class="row-even"><nested:text  property="sanctionedLeave"   size="10" maxlength="10"  styleId="<%=sanctionedLeave_count%>" onchange="<%=avgMethod%>"></nested:text></td>
									<td class="row-even"><nested:text  property="remainingLeave"   size="10" maxlength="10" readonly="true" styleId="<%=remainingLeave_count%>" ></nested:text></td>
								 </tr>							
														  
					</nested:iterate>
					</logic:notEmpty>
								
                
             
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
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.PayScale"/>
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
                <td   class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.grade"/></div></td>
                <td  class="row-even" width="50%">
               
                 <html:select property="payScaleId" styleId="payScaleId" onchange="getPayScale(this.value)">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="payScaleMap" name="EmployeeInfoEditForm">
						<html:optionsCollection property="payScaleMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
               </td>
               </tr>
        <tr>
      <td   class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.Scale"/></div></td>
      <td  class="row-even"><span class="star">
     <html:text  property="scale" styleId="scale" size="15" maxlength="20" />
     </span></td>
      </tr>
             <logic:notEmpty property="employeeInfoTONew.payscaleFixedTo" name="EmployeeInfoEditForm">
				<nested:iterate id="payscaleFixedTo" property="employeeInfoTONew.payscaleFixedTo" name="EmployeeInfoEditForm" indexId="count">
                		
							<tr>
									<td class="row-odd">
									<div align="left">
										<bean:write name="payscaleFixedTo"  property="name"/>
									</div>
									</td>
									<td class="row-even">
										<nested:text property="allowanceName" styleId="allowanceName" onkeypress="return isDecimalNumberKey(this.value,event)"></nested:text>
									</td>
								</tr>
			</nested:iterate>
		</logic:notEmpty>
			<tr>
			<td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.grossPay"/></div></td>
                <td  class="row-even">
				<html:text  property="grossPay" styleId="grossPay" size="15" maxlength="20" onkeypress="return isDecimalNumberKey(this.value,event)"/>
               </td>
			</tr>						
									
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
									<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.DependantDetails"/>
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
                      
                       <td   class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.dependants.name"/></div></td>
               			<td   class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.dependants.relationship"/></div></td> 
               			<td   class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.dependants.dob"/></div></td>
             		 </tr>	
             	       		 
             		 
             		 
             		 
             		 	
             <logic:notEmpty property="employeeInfoTONew.empDependentses" name="EmployeeInfoEditForm">
					<nested:iterate property="employeeInfoTONew.empDependentses" name="EmployeeInfoEditForm" id="empDependentses" indexId="count">
                		
                		<%
					String styleDate1 = "dependantDOB_" + count;
									%>
					<%
					String styledependantName = "dependantName_" + count;
									%>
                		<tr>
                		<td  class="row-even"><nested:text  property="dependantName" styleId="<%=styledependantName%>" size="40" maxlength="100" ></nested:text></td>
                		<td  class="row-even"><nested:text  property="dependentRelationship" styleId="dependentRelationship" size="30" maxlength="50" ></nested:text></td>
                		<td  class="row-even"><nested:text  property="dependantDOB" styleId="<%=styleDate1%>" size="15" maxlength="20" ></nested:text>
               		 	 <script
									language="JavaScript">
								new tcal( {
								// form name
								'formname' :'EmployeeInfoEditForm',
								// input name
								'controlname' :'<%=styleDate1%>'
							});
						</script>
                		</td>
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="3">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('resetDependents','DependentAddMore'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditForm.dependantsListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeDependentsRow','DependentAddMore'); return false;"></html:submit>
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
					<td colspan="4" class="heading" align="left"><bean:message key="knowledgepro.employee.pfgratuity.Nominee.Details"/></td>
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
					<td width="14%" class="row-odd"> 
					<div align="left">
					<bean:message key="employee.info.lic.gratuity.no" /></div></td>
					<td width="36%" class="row-even"><html:text property="licGratuityNo" maxlength="15" size="20"></html:text></td>
					<td width="14%" class="row-odd"> 
					<div align="left">
					<bean:message key="employee.info.lic.gratuity.date" /></div></td>
					<td width="36%"  class="row-even">
									<html:text name="EmployeeInfoEditForm" property="licGratuityDate" styleId="licGratuityDate" size="10" maxlength="16" />
										<script 
												language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'licGratuityDate'
												});
										</script>
								</td> 
				</tr>
				<tr> 
					<td width="14%" class="row-odd"> 
					<div align="left">
					<bean:message key="employee.info.nominee.pf.no" /></div></td>
					<td width="36%" class="row-even"><html:text property="nomineePfNo" maxlength="15" size="20"></html:text></td>
					<td width="14%" class="row-odd"> 
					<div align="left">
					<bean:message key="employee.info.nominee.pf.date" /></div></td>
					<td width="36%"  class="row-even">
									<html:text name="EmployeeInfoEditForm" property="nomineePfDate" styleId="nomineePfDate" size="10" maxlength="16" />
										<script 
												language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'nomineePfDate'
												});
										</script>
								</td> 
			  </tr>        
                        </table></td>
                          <td width="5" height="30"  background="images/right.gif"></td>
                       </tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
      					<table width="100%" cellspacing="1" cellpadding="2">
                     <tr>
                       <td  height="20" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.name.address.nominee"/></div></td>
               			<td  height="20" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.nominee.relationship.member"/></div></td> 
               			<td  height="20" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.dependants.dob"/></div></td>
               			<td  height="20" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.share.percentage"/></div></td>
               			<td  height="20" class="row-odd"><div>If under 18 years </div><div align="left" >(<bean:message key="knowledgepro.employee.name.address.guardian"/>)</div></td>
             		 </tr>	
             <logic:notEmpty property="employeeInfoTONew.pfGratuityNominee" name="EmployeeInfoEditForm">
					<nested:iterate property="employeeInfoTONew.pfGratuityNominee" name="EmployeeInfoEditForm" id="pfGratuityNominee" indexId="count">
                		<%
					    String nameAdress = "nameAdress_" + count;
						%>
						<%
					    String relationship = "relationship_" + count;
						%>
                		<%
					   String dateOfBirth = "dateOfBirth_" + count;
						%>
					   <%
					   String share = "share_" + count;
					   %>
					   <%
					   String guardian = "guardian_" + count;
					   %>
                		<tr>
                		<td  class="row-even"><nested:textarea  property="nameAdressNominee" styleId="<%=nameAdress%>" cols="40" rows="3" onkeypress="return imposeMaxLengthpf(this,499);" onchange="onchange_isplay_pf(this,499)"></nested:textarea></td>
                		<td  class="row-even"><nested:text  property="memberRelationship" styleId="<%=relationship%>" size="30" maxlength="50" ></nested:text></td>
                		<td  class="row-even"><nested:text  property="dobMember" styleId="<%=dateOfBirth%>" size="15" maxlength="20" ></nested:text>
               		 	 <script
								 language="JavaScript">
								new tcal( {
								// form name
								'formname' :'EmployeeInfoEditForm',
								// input name
								'controlname' :'<%=dateOfBirth%>'
							});
						</script>
                		</td>
                		<td  class="row-even"><nested:text  property="share" styleId="<%=share%>" size="10" maxlength="20" ></nested:text></td>
                		<td  class="row-even"><nested:textarea  property="nameAdressGuardian" styleId="<%=guardian%>" cols="40" rows="3" onkeypress="return imposeMaxLengthpf(this,499);" onchange="onchange_isplay_pf(this,499)"></nested:textarea></td>
             		 </tr>
             </nested:iterate>
           </logic:notEmpty>
              <tr>
			     <td  class="row-even" align="center" colspan="5">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('addPfGratuityNominees','PfGratuityAddMore'); return false;"></html:submit>
									 <c:if test="${EmployeeInfoEditForm.pfGratuityListSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removePfGratuitynominees','PfGratuityAddMore'); return false;"></html:submit>
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
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.passportDetails"/>
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
			<logic:notEmpty property="employeeInfoTONew.empImmigration" name="EmployeeInfoEditForm">          
			<nested:iterate property="employeeInfoTONew.empImmigration" name="EmployeeInfoEditForm" id="empImmigration" >
              <tr>
                <td width="28%" height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.passportNo"/></div></td>
                <td width="22%" class="row-even">
						<nested:text  property="passportNo" size="15" maxlength="20" ></nested:text>
				</td>
                <td width="18%" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.IssuedDate"/> </div></td>
                <td width="32%" class="row-even">
					<nested:text  property="passportIssueDate" styleId="passportIssueDate" size="15" maxlength="20" ></nested:text>
					<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'passportIssueDate'
												});
					</script>
				
				</td>
              </tr>
              <tr >
                <td height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.Status"/> </div></td>
                <td class="row-even">
                <nested:text  property="passportStatus"  size="15" maxlength="20" ></nested:text></td>
                <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.passport.dateOfexpiry"/></div></td>
                <td height="25" class="row-even" >
					<nested:text  property="passportExpiryDate" styleId="passportExpiryDate" size="15" maxlength="20" ></nested:text>
					<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'passportExpiryDate'
												});
					</script>
 				</td>
              </tr>
               
                <tr>
                <td width="18%" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.passport.reviewStatus"/></div></td>
                <td height="25" class="row-even" > 
                 <nested:text  property="passportReviewStatus" size="15" maxlength="20" ></nested:text>
                  </td>
                  <td width="18%" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.passport.comments"/></div></td>
               <td height="25" class="row-even" >
               	<nested:text  property="passportComments"  size="30" maxlength="100" ></nested:text></td>
                </tr>
                
               
                <tr><td  width="28%" height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.passport.citizenship"/></div></td>
                  <td class="row-even" colspan="3"  >
				<nested:select property="passportCountryId"  >
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="passportCountryMap" name="EmployeeInfoEditForm">
										   		<html:optionsCollection property="passportCountryMap" label="value" value="key"/>
										   </logic:notEmpty>
				 </nested:select> 
				  </td> </tr>
				  </nested:iterate>
				  </logic:notEmpty>  
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
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.visaDetails"/>
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
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
			<logic:notEmpty property="employeeInfoTONew.empImmigration" name="EmployeeInfoEditForm">          
			<nested:iterate property="employeeInfoTONew.empImmigration" name="EmployeeInfoEditForm" id="empImmigration" >
              <tr >
                <td width="28%" height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.visaNo"/></div></td>
                <td width="22%" class="row-even">
						<nested:text  property="visaNo" styleId="visaNo" size="15" maxlength="20" ></nested:text>
				</td>
                <td width="18%" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.IssuedDate"/> </div></td>
                <td width="32%" class="row-even">
					<nested:text  property="visaIssueDate" styleId="visaIssueDate" size="15" maxlength="20" ></nested:text>
					<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'visaIssueDate'
												});
					</script>
				
				</td>
              </tr>
              <tr >
                <td height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.Status"/> </div></td>
                <td class="row-even">
                <nested:text  property="visaStatus" styleId="visaStatus" size="15" maxlength="20" ></nested:text></td>
                <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.passport.dateOfexpiry"/></div></td>
                <td height="25" class="row-even" >
					<nested:text  property="visaExpiryDate" styleId="visaExpiryDate" size="15" maxlength="20" ></nested:text>
					<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'visaExpiryDate'
												});
					</script>
 				</td>
              </tr>
               
                <tr>
                <td width="18%" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.passport.reviewStatus"/></div></td>
                <td height="25" class="row-even" > 
                 <nested:text  property="visaReviewStatus" styleId="visaReviewStatus" size="15" maxlength="20" ></nested:text>
                  </td>
                  <td width="18%" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.passport.comments"/></div></td>
               <td height="25" class="row-even" >
               	<nested:text  property="visaComments" styleId="visaComments" size="30" maxlength="100" ></nested:text></td>
                </tr>
                
               
                <tr><td  width="28%" height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.passport.citizenship"/></div></td>
                  <td class="row-even" colspan="3"  >
				<nested:select property="visaCountryId" styleId="visaCountryId" >
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="visaCountryMap" name="EmployeeInfoEditForm">
										   		<html:optionsCollection property="visaCountryMap" label="value" value="key"/>
										   </logic:notEmpty>
				 </nested:select>
				  </td> </tr> 
				  </nested:iterate>
				  </logic:notEmpty> 
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
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.WorkTimeEntry"/>
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
									<td class="row-odd" width="150"><div align="left"><bean:message key="knowledgepro.hostel.timein"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="timeIn"  styleClass="Timings"  styleId="timeIn" size="2" maxlength="2"   onkeypress="return isNumberKey(event)"/>:
										<html:text property="timeInMin"  styleClass="Timings"  styleId="timeInMin" size="2" maxlength="2"   onkeypress="return isNumberKey(event)" onmouseover="shows1(this,'Enter Work time details for Employees for whom time is different from the entry in Employee type')" onmouseout="hides1()"/>
									</td>
									<td class="row-odd" width="150"><div align="left"><bean:message key="knowledgepro.hostel.timeinends"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="timeInEnds" styleClass="Timings"  styleId="timeInEnds" size="2" maxlength="2"   onkeypress="return isNumberKey(event)"/>:
										<html:text property="timeInEndMIn" styleClass="Timings"  styleId="timeInEndMIn" size="2" maxlength="2"   onkeypress="return isNumberKey(event)"/>
									</td>
								</tr>
								
								<tr>
									<td class="row-odd" width="75"><div align="left"><bean:message key="knowledgepro.hostel.timeout"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="timeOut" styleClass="Timings"  styleId="timeOut" size="2" maxlength="2"   onkeypress="return isNumberKey(event)"/>:
										<html:text property="timeOutMin" styleClass="Timings"  styleId="timeOutMin" size="2" maxlength="2"   onkeypress="return isNumberKey(event)" onmouseover="shows1(this,'Enter Work time details for Employees for whom time is different from the entry in Employee type')" onmouseout="hides1()"/>
									 <div id="messageBox1">
	                              <div id="contents1"></div>
	                              </div>
									</td>
									<td class="row-odd" width="75"><div align="left"><bean:message key="knowledgepro.hostel.saturday.timeout"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="saturdayTimeOut" styleClass="Timings"  styleId="saturdayTimeOut" size="2" maxlength="2"   onkeypress="return isNumberKey(event)"/>:
										<html:text property="saturdayTimeOutMin" styleClass="Timings"  styleId="saturdayTimeOutMin" size="2" maxlength="2"   onkeypress="return isNumberKey(event)" onmouseover="shows1(this,'Enter Work time details for Employees for whom time is different from the entry in Employee type')" onmouseout="hides1()"/>
									 <div id="messageBox1">
	                              <div id="contents1"></div>
	                              </div>
									</td>
								</tr>
								
								<tr>
									<td class="row-odd" width="75"><div align="left"><bean:message key="knowledgepro.hostel.halfday.starttime"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="halfDayStartTime" styleClass="Timings"  styleId="halfDayStartTime" size="2" maxlength="2"   onkeypress="return isNumberKey(event)"/>:
										<html:text property="halfDayStartTimeMin" styleClass="Timings"  styleId="halfDayStartTimeMin" size="2" maxlength="2"   onkeypress="return isNumberKey(event)" onmouseover="shows1(this,'Enter Work time details for Employees for whom time is different from the entry in Employee type')" onmouseout="hides1()"/>
									 <div id="messageBox1">
	                              <div id="contents1"></div>
	                              </div>
									</td>
									<td class="row-odd" width="75"><div align="left"><bean:message key="knowledgepro.hostel.halfday.endtime"/>:</div></td>
									<td class="row-even" width="100" colspan="3">
										<html:text property="halfDayEndTime" styleClass="Timings"  styleId="halfDayEndTime" size="2" maxlength="2"   onkeypress="return isNumberKey(event)"/>:
										<html:text property="halfDayEndTimeMin" styleClass="Timings"  styleId="halfDayEndTimeMin" size="2" maxlength="2"   onkeypress="return isNumberKey(event)" onmouseover="shows1(this,'Enter Work time details for Employees for whom time is different from the entry in Employee type')" onmouseout="hides1()"/>
									 <div id="messageBox1">
	                              <div id="contents1"></div>
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
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.resignationDetails"/>
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
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
								
			<tr >
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.resignation.DateOfResignation"/></div></td>
                <td class="row-even">
					<html:text  property="dateOfResignation" styleId="dateOfResignation" size="15" maxlength="20" />
					<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'dateOfResignation'
												});
					</script>
				</td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.resignation.DateOfLeaving"/> </div></td>
                <td class="row-even">
					<html:text  property="dateOfLeaving" styleId="dateOfLeaving" size="15" maxlength="20" />
					<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'dateOfLeaving'
												});
					</script>
				
				</td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.resignation.ReasonOfLeaving"/> </div></td>
                <td class="row-even">
               		 <html:text  property="reasonOfLeaving" styleId="reasonOfLeaving" size="15" maxlength="50" /></td>
          </tr>
          <tr>
              <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.releaving.order.date"/></div></td>
                <td class="row-even">
					<html:text  property="releavingOrderDate" styleId="releavingOrderDate" size="15" maxlength="20" />
					<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'releavingOrderDate'
												});
					</script>
				</td>
				<td class="row-odd" align="left">
									      	<bean:message key="knowledgepro.employee.referenceNumber"/>
									     </td>
										 <td  class="row-even" >
										 	<html:text property="referenceNumberForReleaving" styleId="referenceNumberForReleaving" size="30" maxlength="50"></html:text>
										 </td>	
										 <td class="row-odd" align="left"></td>
										 <td class="row-even" align="left"></td>				
          </tr>
							
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
						<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.Achievements"/>
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
											<td width="50%" height="20" class="row-odd"><bean:message key="knowledgepro.employee.NameAchievements"/></td>
											<td width="50%" class="row-odd"><bean:message key="knowledgepro.employee.Description"/></td>
										 
											
											
			  </tr>
			 <logic:notEmpty property="employeeInfoTONew.empAcheivements" name="EmployeeInfoEditForm">          
			<nested:iterate property="employeeInfoTONew.empAcheivements" name="EmployeeInfoEditForm" id="empAcheivements" indexId="count">
			
            <tr>
            <%
					String styleAcheivementName = "acheivementName_" + count;
									%>
						<td width="50%" height="20" class="row-even"> <nested:text  property="acheivementName" styleId="<%=styleAcheivementName%>" size="50" maxlength="50" ></nested:text></td>
						<td width="50%" class="row-even"> <nested:textarea  property="details" styleId="details" cols="80" rows="2" onkeypress="maxlength(this, 500);" ></nested:textarea></td>
											
			  </tr>
			  </nested:iterate>
			  </logic:notEmpty>
			  
			  <tr>
			  <td class="row-even" colspan="2" align="center"><html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('resetAchievement','AchievementAddMore'); return false;"></html:submit>
			 <c:if test="${EmployeeInfoEditForm.achievementListSize>0}">
				           <html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeAchievementRow','ExpAddMore'); return false;"></html:submit>
				  </c:if>
			  </td></tr>
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
										<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.FinancialDetails"/>
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
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
             <tr class="row-odd">
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.date"/> </div></td>
                <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.amount"/></div></td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.details"/></div></td>
                
				</tr>
		<logic:notEmpty property="employeeInfoTONew.empFinancial" name="EmployeeInfoEditForm">          
			<nested:iterate property="employeeInfoTONew.empFinancial" name="EmployeeInfoEditForm" id="empFinancial" indexId="count">
				<%
					String styleDate1 = "financialDate_" + count;
									%>
				<tr>
				 <td  class="row-even">
                  <nested:text  property="financialDate" styleId="<%=styleDate1 %>" size="15" maxlength="20" ></nested:text>
                  <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'<%=styleDate1 %>'
												});
					</script>
                  </td>
				 <td  class="row-even" ><nested:text  property="financialAmount" styleId="financialAmount" size="15" maxlength="20" onkeypress="return isDecimalNumberKey(this.value,event)"></nested:text></td>
				 <td   class="row-even">
				<nested:textarea property="financialDetails" styleId="financialDetails" cols="80" rows="1" onkeypress="maxlength(this, 250);"></nested:textarea>
				</td>
				<td class="row-even" align="center">
				</td>
                
              </tr>
             </nested:iterate>
             </logic:notEmpty>
             <tr>
            <td class="row-odd" align="center" colspan="3">  <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('resetFinancial','FinancialAddMore'); return false;"></html:submit>
             <c:if test="${EmployeeInfoEditForm.financialListSize>0}">
				           <html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeFinancialRow','ExpAddMore'); return false;"></html:submit>
		     </c:if>
             </td>
             </tr>
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
										<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.loanDetails"/>
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
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
			
             <tr class="row-odd">
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.date"/> </div></td>
                <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.amount"/></div></td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.details"/></div></td>
                
				</tr>
				
			
			<logic:notEmpty property="employeeInfoTONew.empLoan" name="EmployeeInfoEditForm">          
			<nested:iterate property="employeeInfoTONew.empLoan" name="EmployeeInfoEditForm" id="empLoan" indexId="count">
				<%
					String styleDate1 = "loanDate_" + count;
									%>
				<tr>
				 <td  class="row-even">
                  <nested:text  property="loanDate" styleId="<%=styleDate1%>" size="15" maxlength="20" ></nested:text>
                  <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'<%=styleDate1%>'
												});
					</script>
                 </td>
				 <td  class="row-even" ><nested:text  property="loanAmount" styleId="loanAmount" size="15" maxlength="20" onkeypress="return isDecimalNumberKey(this.value,event)"></nested:text></td>
				 <td   class="row-even">
				<nested:textarea property="loanDetails" styleId="loanDetails" cols="80" rows="1" onkeypress="maxlength(this, 250);"></nested:textarea>
				</td>
				<td class="row-even" align="center">
				</td>
                
              </tr>
              </nested:iterate>
              </logic:notEmpty>
              <tr>
            <td class="row-odd" align="center" colspan="3"> <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('resetLoan','LoanAddMore'); return false;"></html:submit>
             <c:if test="${EmployeeInfoEditForm.loanListSize>0}">
				           <html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeLoanRow','ExpAddMore'); return false;"></html:submit>
		     </c:if>
             </td>
             </tr>
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
										<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.FeeConcession"/>
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
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr class="row-odd">
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.date"/> </div></td>
                <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.amount"/></div></td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.details"/></div></td>
               
				</tr>
      <logic:notEmpty property="employeeInfoTONew.empFeeConcession" name="EmployeeInfoEditForm">  
				<nested:iterate property="employeeInfoTONew.empFeeConcession" name="EmployeeInfoEditForm" id="empFeeConcession" indexId="count">
				 <%
					String styleDate1 = "feeConcessionDate_" + count;
									%>
				
				<tr>
				 <td  class="row-even">
                  <nested:text  property="feeConcessionDate" styleId="<%=styleDate1%>" size="15" maxlength="20" ></nested:text>
                  <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'<%=styleDate1%>'
												});
					</script>
                  </td>
				 <td  class="row-even" ><nested:text  property="feeConcessionAmount" styleId="feeConcessionAmount" size="15" maxlength="20" onkeypress="return isDecimalNumberKey(this.value,event)"></nested:text></td>
				 <td   class="row-even">
				<nested:textarea property="feeConcessionDetails" styleId="feeConcessionDetails" cols="80" rows="1" onkeypress="maxlength(this, 250);"></nested:textarea>
				</td>
				<td class="row-even" align="center">
				</td>
                
              </tr>
              </nested:iterate>
              </logic:notEmpty>
               <tr>
             <td class="row-odd" align="center" colspan="3">  <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('resetFeeConcession','FeeConcessionAddMore'); return false;"></html:submit>
             <c:if test="${EmployeeInfoEditForm.feeListSize>0}">
				           <html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeFeeConcessionRow','ExpAddMore'); return false;"></html:submit>
		     </c:if>
             </td>
             </tr>
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
										<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.incentivesDetails"/>
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
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr class="row-odd">
               <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.date"/> </div></td>
                <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.amount"/></div></td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.details"/></div></td>
               
				</tr>
			
			<logic:notEmpty property="employeeInfoTONew.empIncentives" name="EmployeeInfoEditForm">          
			<nested:iterate property="employeeInfoTONew.empIncentives" name="EmployeeInfoEditForm" id="empIncentives" indexId="count">
			 <%
					String styleDate1 = "incentivesDate_" + count;
									%>
			<tr>
				 <td  class="row-even">
                  <nested:text  property="incentivesDate" styleId="<%=styleDate1%>" size="15" maxlength="20" ></nested:text>
                  <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'<%=styleDate1%>'
												});
					</script>
                 </td>
                 
				 <td  class="row-even" ><nested:text  property="incentivesAmount" styleId="incentivesAmount" size="15" maxlength="20" onkeypress="return isDecimalNumberKey(this.value,event)"></nested:text></td>
				 <td   class="row-even">
				<nested:textarea property="incentivesDetails" styleId="incentivesDetails" cols="80" rows="1" onkeypress="maxlength(this, 250);"></nested:textarea>
				</td>
				<td class="row-even" align="center">
				</td>
                
              </tr>
           </nested:iterate>
           </logic:notEmpty>  
            <tr>
              <td class="row-odd" align="center" colspan="3">  <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('resetIncentives','IncentivesAddMore'); return false;"></html:submit>
             <c:if test="${EmployeeInfoEditForm.incentivesListSize>0}">
				           <html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeIncentivesRow','ExpAddMore'); return false;"></html:submit>
		     </c:if>
             </td>
             </tr>
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
										<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.remarksDetails"/>
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
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr class="row-odd">
              <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.date"/> </div></td>
                <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.remarks"/></div></td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.EnteredBy"/></div></td>
                
                
                
				</tr>
			<logic:notEmpty property="employeeInfoTONew.empRemarks" name="EmployeeInfoEditForm">          
			<nested:iterate property="employeeInfoTONew.empRemarks" name="EmployeeInfoEditForm" id="empRemarks" indexId="count">
			    <%
					String styleDate1 = "remarkDate_" + count;
									%>
				<tr>
				 <td  class="row-even">
                  <nested:text  property="remarkDate" styleId="<%=styleDate1%>" size="15" maxlength="20" ></nested:text>
                  <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'EmployeeInfoEditForm',
												// input name
												'controlname' :'<%=styleDate1%>'
												});
					</script>
                  </td>
				 <td  class="row-even" >
				
				 <nested:textarea property="remarkDetails"  cols="80" rows="1" onkeypress="maxlength(this, 199);" ></nested:textarea></td>
				 <td   class="row-even">
				<nested:text  property="enteredBy" styleId="enteredBy" size="30" maxlength="100" ></nested:text>
				</td>
				<td class="row-even" align="center">
				</td>
                
              </tr>
              
              </nested:iterate>
              </logic:notEmpty>
              <tr>
              <td class="row-odd" align="center" colspan="3">  <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('resetRemarks','RemarksAddMore'); return false;"></html:submit>
             <c:if test="${EmployeeInfoEditForm.remarksListSize>0}">
				           <html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeRemarksRow','ExpAddMore'); return false;"></html:submit>
		     </c:if>
             </td>
             </tr>
             
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
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
								
			         <tr> 
						<td class="row-odd" colspan="1" align="left" width="20%"> 
						<bean:message key="knowledgepro.employee.anyother"/>
					</td>
				
						<td class="row-even" width="80%">
					<html:textarea property="otherInfo" cols="80" rows="5" onkeypress="return imposeMaxLength1(this, 499);" onkeyup="len_display(this,0,'long_len')"></html:textarea>
					<input type="text" id="long_len" value="0" class="len" size="2" readonly="readonly" style="border: none; background-color: #eaeccc; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif">/500 Characters</td>
											
			
					</tr>
							
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
								<td width="100%" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
								
			         <tr> 
						<td class="row-odd" colspan="1" align="left" width="20%">
						<bean:message key="knowledgepro.employee.additionalremarksDetails"/>
					</td>
				
						<td class="row-even" width="80%">
					<html:textarea property="additionalRemarks" cols="80" rows="5" onkeypress="return imposeMaxLength1(this, 499);" onkeyup="len_display(this,0,'long_len1')"></html:textarea>
					<input type="text" id="long_len1" value="0" class="len" size="2" readonly="readonly" style="border: none; background-color: #eaeccc; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif">/500 Characters</td>
											
			
					</tr>
							
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
			
		
			
	


