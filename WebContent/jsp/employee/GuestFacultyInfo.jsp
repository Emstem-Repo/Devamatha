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
<title>Guest Faculty Information Submission</title>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script language="JavaScript" src="js/admissionform.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript"><!--


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
function Print()
{
	document.location.href = "GuestFaculty.do?method=getResumeDetails&guestId="+id;
	
}


	var destId;
	function closeWindow(){
		document.getElementById("method").value="initGuestAdd";
		document.GuestFacultyInfoForm.submit();
		//document.location.href = "LoginAction.do?method=loginAction";
	}

	function resetEmpInfo(){
		document.getElementById("method").value="initEmployeeInfo";
		document.GuestFacultyInfoForm.submit();
	}

	function saveEmpDetails(){
		document.getElementById("method").value="saveEmpDetails";
		document.GuestFacultyInfoForm.submit();
	}
	function submitEmployeeInfoAdd(method,mode){
		document.getElementById("method").value=method;
		document.getElementById("mode").value=mode;
		document.GuestFacultyInfoForm.submit();
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

	

	function searchStreamWise(streamId){
		getDepartmentByStreamWise(streamId,updateDepartmentMap);
	}
	function updateDepartmentMap(req){
		updateOptionsFromMap(req,"departmentId","-Select-");
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


	var print = "<c:out value='${GuestFacultyInfoForm.printPage}'/>";
	if(print.length != 0 && print == "true"){
		var url = "GuestFaculty.do?method=printResume";
		myRef = window
				.open(url, "ViewResume",
						"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
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
function duplicateCheck(staffId){
	checkDupilcateOfStaffId(staffId,updateStaffId);
}
function updateStaffId(req){
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
			if(value[I].firstChild!=null){
			var temp = value[I].firstChild.nodeValue;
			document.getElementById("invalidStaffId").innerHTML = temp;
			}
		}
	}
}
function departmentByStream(count){
	var strmId = document.getElementById("strmId_"+count).value;
	document.getElementById("count").value = count;
	getDepartmentByStreamWise(strmId,updateDepartmentMapByStream);
	
}
function updateDepartmentMapByStream(req){
	var count = document.getElementById("count").value;
	var deptId = "deptId_"+count;
	updateOptionsFromMap(req,deptId,"-Select-");
}
function caps(element)
{
element.value = element.value.toUpperCase();
}
function openURL(){
	var url = "http://www.ifsc4bank.com";
	myRef = window.open(url);
}

</script>
<body>
<table width="100%" border="0">

<html:form action="/GuestFaculty" enctype="multipart/form-data" >
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="GuestFacultyInfoForm" />
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<html:hidden property="listSize" styleId="listSize"/>
	<input type="hidden" id = "count"/>
	
       <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.guest.faculty" /> &gt;&gt;</span></span></td>
		</tr>
 
  <tr>
    <td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
        <td width="1271" background="images/Tcenter.gif" class="body" >
			<div align="left">
				<strong class="boxheader"><bean:message key="knowledgepro.guest.faculty"/></strong>
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
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
									<tr>
								    <td class="row-odd" width="125" colspan="1">
								    <div align="left"><span class="Mandatory">*</span>
								      <bean:message key="knowledgepro.employee.isTeachingStaff"/>
								    </div>
								    </td>
									<td class="row-even" colspan="1">
									 <html:radio property="teachingStaff" styleId="teachingStaff"  value="1"/>Yes&nbsp; 
									<html:radio property="teachingStaff" styleId="teachingStaff"  value="0"/>No
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
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							
					
					<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="22" align="left" valign="top">
									<table width="100%" height="22" border="0" cellpadding="0">
										<tr class="row-white">
											<td width="50%" height="30" align="center" class="row-even"><img
												src='<%=request.getContextPath()%>/PhotoServlet'
												height="186Px" width="144Px" /></td>
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
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									 <td class="row-odd" width="75">
									 <div align="left"><span class="Mandatory">*</span>
									     <bean:message key="knowledgepro.admin.name"/>
									  </div>
									  </td>
										<td  class="row-even" >
											<html:text property="name" styleId="name" size="35" maxlength="100" style="text-transform:uppercase;"></html:text>
										</td>
										 <td class="row-odd" width="75">
									<div align="left">
									     <bean:message key="knowledgepro.admin.uId"/>
									  </div>
									  </td>
									<td  class="row-even" >
											<html:text property="uId" styleId="uId" size="25" maxlength="10"></html:text>
									</td>
							</tr>
								<tr>
									 <td class="row-odd" width="75">
									 <div align="left">
									     <bean:message key="knowledgepro.hostel.reservation.staffId"/>
									 </div>
									  </td>
							
									<td  class="row-even" >
											<html:text property="staffId" styleId="staffId" size="25" maxlength="15" onblur="duplicateCheck(this.value)" onchange="duplicateCheck(this.value)"></html:text>
											<font color="red"><div id="invalidStaffId"></div></font>
									</td>
									<td class="row-odd" > 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.employee.gender.required" /></div>
							  	</td>
								<td height="17" class="row-even" align="left" >
									<nested:radio property="gender" value="MALE" name="GuestFacultyInfoForm"><bean:message key="admissionForm.studentinfo.sex.male.text"/></nested:radio>
									<nested:radio property="gender" value="FEMALE" ><bean:message key="admissionForm.studentinfo.sex.female.text"/></nested:radio>
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
								   	<logic:notEmpty property="nationalityMap" name="GuestFacultyInfoForm">
								   		<html:optionsCollection property="nationalityMap" label="value" value="key"/>
								   </logic:notEmpty>
							     </html:select> 
								 </td>
							  	
							 
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
						  </tr>
							 <tr> 
							  	<td class="row-odd"> 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.admin.dateofbirth" /></div>
							  	</td>
								<td   class="row-even">
									<html:text name="GuestFacultyInfoForm" property="dateOfBirth" styleId="dateOfBirth" size="10" maxlength="16" onchange="CalRetirementDate(), CalAge()" />
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'GuestFacultyInfoForm',
												// input name
												'controlname' :'dateOfBirth'
												});
										</script>
								
								 	<!--<html:text property="age" styleId="age" readonly="true" size="6"></html:text>-->
								 </td>
							
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
					</tr>
					  <tr>
							<td height="20" class="row-odd">
							<div align="left"><span class="Mandatory">*</span>
							<bean:message key="admissionForm.studentinfo.religion.label" /></div></td>
								<td height="25" class="row-even">
							<html:select property="religionId" styleId="religionId">
								<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									<logic:notEmpty property="religionMap" name="GuestFacultyInfoForm">
								  		<html:optionsCollection property="religionMap" label="value" value="key"/>
								   </logic:notEmpty>
							 </html:select> 
									
				  </td>
				  
					  	<td class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.panNo" /></div></td>
                  		<td class="row-even" ><span class="star">
						<html:text property="panno" size="10" maxlength="10"/></span></td>
						</tr>
					<tr> 
				 		 <td class="row-odd"> 
								<div align="left">
									<bean:message key="knowledgepro.employee.officialEmailId" /></div>
							  	</td>
								 <td  class="row-even">
								 	<html:text property="officialEmail" size="20" maxlength="50"></html:text>
								 </td>
				
							  	 <td class="row-odd"> 
									<div align="left">
									<bean:message key="admissionFormForm.emailId" /></div>
							  	</td>
								 <td  class="row-even">
								 	<html:text property="email" size="20" maxlength="50"></html:text>
								 </td>
					</tr>
							  
				<tr>
							  		<td class="row-odd"> 
									<div align="left">
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
				
									
									<td class="row-odd" width="14%"> 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.employee.mobile" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="mobileNo1" onkeypress="return isNumberKey(event)" maxlength="10"></html:text>
									</td>
					</tr>
					<tr>
									<td class="row-odd" width="13%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.bankAccNo" /></div>
							  		</td>
									<td class="row-even" width="37%"> 
									<html:text property="bankAccNo" maxlength="20"></html:text>
									</td>
									
									<td class="row-odd" width="14%"> 
									<div align="left">Bank
									<bean:message key="knowledgepro.hostel.reservation.branchName" /></div>
							  		</td>
							  		<td class="row-even" width="36%"> 
									<html:text property="bankBranch" maxlength="25"></html:text>
									</td>
					</tr>
					<tr>
									<td class="row-odd" width="13%"> 
									<div align="left">Bank IFSC Code:</div>
							  		</td>
									<td class="row-even" width="37%"> 
									<html:text property="bankIfscCode" maxlength="20" onkeyup="caps(this)"></html:text>
									<img align="top" src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20"
								 onclick="openURL()" title="Click here for the IFSC code list" />
									</td>			
									<td class="row-odd" width="14%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.PfAccNo" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="pfNo" maxlength="25"></html:text>
									</td>
						</tr>
						<tr>
									<td   class="row-odd">
									 <div align="left" ><bean:message key="knowledgepro.employee.EmContact.name"/></div></td>
               			 			<td  class="row-even">
                				    <html:text  property="emContactName" styleId="emContactName" size="50" maxlength="100" />
               						</td>
             					    <td   class="row-odd">
             					    <div align="left" ><bean:message key="knowledgepro.employee.EmContact.address"/></div></td>
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
                	</tr>
                	<tr>
									<td class="row-odd" width="13%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.fourWheelerNo" /></div>
							  		</td>
									<td class="row-even" width="37%"> 
									<html:text property="fourWheelerNo" maxlength="15"></html:text>
									</td>
							
									<td class="row-odd" width="14%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.twoWheelerNo" /></div>
							  		</td>
									<td class="row-even" width="36%"> 
									<html:text property="twoWheelerNo" maxlength="15"></html:text>
									</td>
								
								
									
								
							</tr>
							<tr>
										<td class="row-odd" width="250" colspan="2" >
									  	<div align="left">
											<bean:message key="knowledgepro.employee.upload.photo"/>:
											</div>		
											</td> 
											<td class="row-even" colspan="2">
												<html:file property="empPhoto"></html:file>
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
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
									<logic:equal value="true" name="GuestFacultyInfoForm" property="isCjc">
									  	<tr>
									   	 	<td class="row-odd" width="13%">
									   	 		<div align="left"><span class="Mandatory">*</span>
									     	 	<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     	 	</div>
									    	</td>
										 	<td  class="row-even" width="37%">
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
										<logic:equal value="false" name="GuestFacultyInfoForm" property="isCjc">
										<tr>
									   	 	<td class="row-odd" width="13%">
									   	 		<div align="left"><span class="Mandatory">*</span>
									     	 	<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     	 	</div>
									    	</td>
										 	<td  class="row-even" width="37%">
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
										 	<html:text property="currentCity" styleId="currentCity" size="40" maxlength="50"></html:text>
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
										   	<logic:notEmpty property="currentCountryMap" name="GuestFacultyInfoForm">
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
										   	<logic:notEmpty property="currentStateMap" name="GuestFacultyInfoForm">
										   		<html:optionsCollection property="currentStateMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select> 
									     <input type="hidden" id="tempState" name="tempState" value='<bean:write name="GuestFacultyInfoForm" property="currentState"/>' />
									    
									     <div id="otehrState">
									     	<html:text property="otherCurrentState" name="GuestFacultyInfoForm" size="20" maxlength="50"></html:text>
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
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
									<logic:equal value="true" name="GuestFacultyInfoForm" property="isCjc">
									  	<tr>
									   	 	<td class="row-odd" width="13%">
									   	 		<div align="left"><span class="Mandatory">*</span>
									      		<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     		</div>
									    	</td>
										 	<td  class="row-even" width="37%">
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
										<logic:equal value="false" name="GuestFacultyInfoForm" property="isCjc">
										 <tr>
									   	 	<td class="row-odd" width="13%">
									   	 		<div align="left"><span class="Mandatory">*</span>
									      		<bean:message key="admissionForm.studentinfo.addrs1.label"/>
									     		</div>
									    	</td>
										 	<td  class="row-even" width="37%">
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
										   	<logic:notEmpty property="countryMap" name="GuestFacultyInfoForm">
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
										   	<logic:notEmpty property="stateMap" name="GuestFacultyInfoForm">
										   		<html:optionsCollection property="stateMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select> 
									     <input type="hidden" id="tempPermanentState" name="tempPermanentState" value='<bean:write name="GuestFacultyInfoForm" property="stateId"/>' />
									    
									     <div id="otehrPermState">
									     	<html:text property="otherPermanentState" name="GuestFacultyInfoForm" size="10" maxlength="10"></html:text>
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
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
       
        				 <table width="100%" cellspacing="1" cellpadding="2">
              
              
              <tr >
                <td height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.active"/> </div></td>
                <td class="row-even"><span class="star">
                 <html:radio property="active" value="1"/>Yes&nbsp; 
				 <html:radio property="active" value="0"/>No
                 </span></td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.streamDetails"/></div></td>
                <td height="25" class="row-even" ><span class="star">
                 <html:select property="streamId" styleId="streamId" styleClass="comboMediumBig" onchange="searchStreamWise(this.value)">>
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="streamMap" name="GuestFacultyInfoForm">
						<html:optionsCollection property="streamMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
                </span>				
                </td>
              </tr>
              <tr>
                <td  class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.worklocation"/></div></td>
                <td height="25" class="row-even" ><span class="star">
                <html:select property="workLocationId" styleId="workLocationId" styleClass="comboMediumBig">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="workLocationMap" name="GuestFacultyInfoForm">
						<html:optionsCollection property="workLocationMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
                </span></td>
                <td  class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.designation"/> </div></td>
                <td class="row-even">
				<html:select property="designationPfId" styleClass="comboMediumBig">
				   <html:option value=""><bean:message key="knowledgepro.select" /></html:option>
						<logic:notEmpty property="designationMap" name="GuestFacultyInfoForm">
						<html:optionsCollection property="designationMap" label="value" value="key"/>
						</logic:notEmpty>
				</html:select>
						</td>
              </tr>
             <tr >
			    <td width="13%"  class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.Department"/>  </div></td>
                <td width="37%" class="row-even">
                <html:select property="departmentId" styleId="departmentId" styleClass="comboExtraLarge">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="departmentMap" name="GuestFacultyInfoForm">
						<html:optionsCollection property="departmentMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
				</td>
                               
				
				<td width="14%"  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.title"/>  </div></td>
                <td width="36%" class="row-even" >
                <html:select property="titleId" styleId="titleId" styleClass="comboMediumBig">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="titleMap" name="GuestFacultyInfoForm">
						<html:optionsCollection property="titleMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
				</td>
              </tr>
              
              
              
              
              <tr >
               
                <td  class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.guest.SubjectSpecilization"/></div></td>
                <td height="25" class="row-even" ><span class="star">
                 <html:text property="subjectSpecilization" styleId="subjectSpecilization" size="20" maxlength="50"></html:text>
                </span>				
                </td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.guest.referredBy"/></div></td>
                <td height="25" class="row-even" ><span class="star">
                 <html:text property="referredBy" styleId="referredBy" size="20" maxlength="100" ></html:text>
                </span>				
                </td>
                 
              </tr>
              <tr>
              <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.guest.working.hour"/></div></td>
                <td height="25" class="row-even" ><span class="star">
                 <html:text property="workingHoursPerWeek" styleId="workingHoursPerWeek" size="10" maxlength="5" onkeypress="return isNumberKey(event)"></html:text>
                </span>				
                </td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.guest.Honorarium"/></div></td>
                <td height="25" class="row-even" ><span class="star">
                 <html:text property="honorariumPerHours" styleId="honorariumPerHours" size="10" maxlength="30" ></html:text>
                </span>				
                </td>
              </tr>
              <tr>
              <td  class="row-odd"><div align="left" >Display in Website</div></td>
              <td class="row-even"><span class="star">
                 <html:radio property="displayInWebsite" value="1"/>Yes&nbsp; 
				 <html:radio property="displayInWebsite" value="0"/>No
                 </span></td>
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
										<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.guest.title.WorkDetails"/>
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
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
          <tr class="row-odd">
                <td class="row-odd" width="12%"><div align="center" ><bean:message key="knowledgepro.guest.currentdata"/> </div></td>
                <td  class="row-odd" width="12%"><div align="center" ><span class="Mandatory">*</span><bean:message key="knowledgepro.guest.startDate"/> </div></td>
                <td class="row-odd" width="12%"><div align="center" ><span class="Mandatory">*</span><bean:message key="knowledgepro.guest.EndDate"/></div></td>
                <td  class="row-odd" width="8%"><div align="center" ><bean:message key="knowledgepro.guest.Semester"/></div></td>
               	<td  class="row-odd" width="8%"><div align="center" ><bean:message key="knowledgepro.employee.streamDetails"/></div></td>
                <td class="row-odd" width="10%"><div align="center" ><span class="Mandatory"></span><bean:message key="knowledgepro.employee.worklocation"/></div></td>
                <td class="row-odd" width="10%"><div align="center" ><span class="Mandatory"></span><bean:message key="knowledgepro.employee.Department"/></div></td>
                <td  class="row-odd" width="10%"><div align="center" ><bean:message key="knowledgepro.guest.work.hour"/></div></td>
                <td  class="row-odd" width="8%"><div align="center" ><bean:message key="knowledgepro.guest.Honorarium"/></div></td>
			</tr>
			<logic:notEmpty property="employeeInfoTONew.previousworkDetails" name="GuestFacultyInfoForm">          
			<nested:iterate property="employeeInfoTONew.previousworkDetails" name="GuestFacultyInfoForm" id="prevworkDetails" indexId="count">
			    <%
					String styleDate1 = "startDate_" + count;
					String styleDate2 = "endDate_" + count;
					String radioData = "radioData_" + count;
					String deptId="deptId_"+count;
					String strmId="strmId_"+count;
					String workLocId="workLocId_"+count;
					String workHoursPerWeek="workHoursPerWeek_"+count;
					String honorarium="honorarium_"+count;
					String method =  "departmentByStream("+count+")";
									%>
				<tr>
				<td class="row-even"><span class="star">
                 <nested:radio property="isCurrentWorkingDates" styleId="<%=radioData%>" value="true"/>Yes&nbsp; 
				 <nested:radio property="isCurrentWorkingDates" styleId="<%=radioData%>" value="false"/>No
                 </span></td>
				 <td  class="row-even">
                  <nested:text  property="startDate" styleId="<%=styleDate1%>" size="10" maxlength="20" ></nested:text>
                  <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'GuestFacultyInfoForm',
												// input name
												'controlname' :'<%=styleDate1%>'
												});
					</script>
                  </td>
                   <td  class="row-even">
                  <nested:text  property="endDate" styleId="<%=styleDate2%>" size="10" maxlength="20" ></nested:text>
                  <script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'GuestFacultyInfoForm',
												// input name
												'controlname' :'<%=styleDate2%>'
												});
					</script>
                  </td>
				  <td   class="row-even">
				<nested:text  property="semester" styleId="semester" size="10"></nested:text>
				</td>
				<td   class="row-even">
					<span class="star">
                 	<nested:select property="strmId"  styleId="<%=strmId%>"  styleClass="comboMedium" onchange="<%=method%>">
					  <html:option value=""><bean:message key="knowledgepro.select" /></html:option>
						<logic:notEmpty property="strmMap" name="prevworkDetails">
						<html:optionsCollection property="strmMap" name="prevworkDetails" label="value" value="key" />
					 </logic:notEmpty>
					</nested:select>
                	</span>	
                			
				</td>
				<td   class="row-even">
					<span class="star">
               		<nested:select property="workLocId"  styleId="<%=workLocId%>" styleClass="comboMedium" >
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="workMap" name="prevworkDetails">
						<html:optionsCollection property="workMap" name="prevworkDetails" label="value" value="key"/>
					 </logic:notEmpty>
					</nested:select>
                	</span>
				</td>    
				<td   class="row-even">
				     <nested:select property="deptId"  styleId="<%=deptId%>" styleClass="comboLarge">
					  	<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
							<logic:notEmpty property="deptMap" name="prevworkDetails">
							<html:optionsCollection property="deptMap" name="prevworkDetails" label="value" value="key"/>
					 		</logic:notEmpty>
					</nested:select>
				</td>  
				<td   class="row-even">
				     <nested:text property="workHoursPerWeek"  styleId="<%=workHoursPerWeek%>" size="5" maxlength="5" onkeypress="return isNumberKey(event)"></nested:text>
				</td>  
				<td   class="row-even">
					<nested:text property="honorarium"  styleId="<%=honorarium%>" size="10" maxlength="30"></nested:text>
				</td>      
              </tr>
              <tr><td></td></tr>
              </nested:iterate>
              </logic:notEmpty>
              <tr>
              <td class="row-odd" align="center" colspan="9">  <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('resetPrevWork','PrevWorkAddMore'); return false;"></html:submit>
                <c:if test="${GuestFacultyInfoForm.prevWorkListSize>0}">
		            <html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removePrevWorkRow','ExpAddMore'); return false;"></html:submit>
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
						<bean:message key="knowledgepro.employee.professional.exp"/>
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
									  	<td class="row-odd" width="75"> 
											<div align="left"><span class="Mandatory">*</span>
											<bean:message key="knowledgepro.employee.previous.working" /></div>
								  		</td>
										<td class="row-even" width="100" colspan="5">
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
									       <html:text property="designation" size ="50" maxlength="50"></html:text>
											
									     </td>
										<td class="row-odd"> 
											
											<bean:message key="knowlegepro.employee.address.organization" />
									  		</td>
										<td class="row-even" colspan="2">
											<html:text property="orgAddress" styleId="orgAddress" size ="50" maxlength="100"></html:text>
										</td>
									</tr>
									
									<tr>
										<td class="row-even" width="12%" height="25"> 
										</td>
										<td class="row-odd" width="22%">
									  	 	<div align="left">
									      	<bean:message key="knowledgepro.employee.subject.area"/>
									      	</div>
									     </td>
										<td class="row-odd" width="10%" height="25" align="left"> 
											<bean:message key="knowledgepro.employee.years" />
									  		</td>
										
										<td class="row-odd" width="10%" align="left"> 
											<bean:message key="knowledgepro.employee.months"/>
										</td>
										<td class="row-odd" width="22%" align="left"> 
											<bean:message key="knowledgepro.admin.sec.Designation"/>
										</td>
										<td class="row-odd" width="22%" align="left" colspan="1"> 
											<bean:message key="knowledgepro.institution.col"/>
										</td>
									</tr>
									<html:hidden property="teachingExpLength" name="GuestFacultyInfoForm" styleId="teachingExpLength"/>
									<html:hidden property="industryExpLength" name="GuestFacultyInfoForm" styleId="industryExpLength"/>
									<logic:notEmpty property="employeeInfoTONew.teachingExperience" name="GuestFacultyInfoForm">     
									<nested:iterate property="employeeInfoTONew.teachingExperience" name="GuestFacultyInfoForm" id="teachingExp" indexId="count">
								<tr>
									
									<c:choose>
										<c:when test="${count==0}">
											<td class="row-odd"> 
												<bean:message key="knowledgepro.employee.teaching.experience" />
										  	</td>
										  	<td  class="row-even">
										 <html:select property="empSubjectAreaId" styleId="empSubjectAreaId">
										   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
										   	<logic:notEmpty property="subjectAreaMap" name="GuestFacultyInfoForm">
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
									<%int number=0; %>
									<%String teaching="teach_"+count;%>
			                            <td class="row-even" align="left">
								              <nested:text property="teachingExpYears" styleClass="TextBox" size="20" maxlength="30" onkeypress="return isNumberKey(event)" onkeyup="getYears(this)" styleId="<%=teaching%>"></nested:text>
										</td>
									<%String teachingMonth="teach_month_"+count;%>	
										<td class="row-even" align="left">
								              <nested:text property="teachingExpMonths" styleClass="TextBox" size="20" maxlength="30" onkeypress="return isNumberKey(event)" onkeyup="getMonths(this)" styleId="<%=teachingMonth%>"></nested:text>
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
			                         	<td  class="row-even" align="center" colspan="6">
			                         	
			                         	<!--<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="getEmployeeTeachingExpAdd('ExpAddMore')"></html:submit>-->
				                        	<html:submit property="" styleClass="formbutton" value="Add more"  styleId="addMore" onclick="submitEmployeeInfoAdd('resetTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
										<c:if test="${GuestFacultyInfoForm.teachingExpLength>0}">
				                        		<html:submit property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="submitEmployeeInfoAdd('removeTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        	</c:if>
										</td> 
						</tr>
			                         <tr>
			                         	<td  class="row-even" align="center" colspan="6" height="20">
										</td> 
			                         </tr>
									</logic:notEmpty>
									
									
									<logic:notEmpty property="employeeInfoTONew.experiences" name="GuestFacultyInfoForm">          
									<nested:iterate property="employeeInfoTONew.experiences" name="GuestFacultyInfoForm" id="experience" indexId="counter">
									<tr>
									<c:choose>
									<c:when test="${counter==0}">
										<td class="row-odd"> 
											<bean:message key="knowledgepro.employee.industry.exp" /></td>
										<td  class="row-even">
										 <nested:text name="GuestFacultyInfoForm" styleClass="TextBox" size="20"  maxlength="50" property="industryFunctionalArea" styleId="industryFunctionalArea">
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
									<%String industry="industry_"+String.valueOf(counter);%>
			                            <td class="row-even" align="left">
								               <nested:text property="industryExpYears" styleClass="TextBox" size="20"  maxlength="30" onkeypress="return isNumberKey(event)" onkeyup="getYears(this)" styleId="<%=industry%>"></nested:text>
										</td>
									<%String industryMonth="industry_month_"+String.valueOf(counter);%>	
										<td class="row-even" align="left">
								               <nested:text property="industryExpMonths" styleClass="TextBox" size="20"   maxlength="30" onkeypress="return isNumberKey(event)" onkeyup="getMonths(this)" styleId="<%=industryMonth%>"></nested:text>
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
			                         	<td  class="row-even" align="center" colspan="6">
			                           <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitEmployeeInfoAdd('resetExperienceInfo','ExpAddMore'); return false;"></html:submit>
										 <c:if test="${GuestFacultyInfoForm.industryExpLength>0}">
				                        	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        </c:if>

										</td> 
									</tr>
									</logic:notEmpty>
									
									
									<tr>
									  	<td class="row-odd"> 
									  		
											<bean:message key="knowledgepro.employee.total.exp"/>
											
									  	</td>
										<td class="row-even" colspan="2">
											 <html:text styleId="expYears" name="GuestFacultyInfoForm" property="expYears" size="3" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;&nbsp;Years&nbsp;&nbsp;
											 <html:text styleId="expMonths" name="GuestFacultyInfoForm" property="expMonths" size="3" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;&nbsp;Months
										</td> 
										<td class="row-odd"> 
									  		
											<bean:message key="knowledgepro.employee.Relevanttotal.exp"/>
											
									  	</td>
										<td class="row-even" colspan="2">
											 <html:text styleId="relevantExpYears" name="GuestFacultyInfoForm" property="relevantExpYears" size="3" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;&nbsp;Years&nbsp;&nbsp;
											 <html:text styleId="relevantExpMonths" name="GuestFacultyInfoForm" property="relevantExpMonths" size="3" maxlength="5" onkeypress="return isNumberKey(event)"/>&nbsp;&nbsp;Months
										</td> 
									  </tr>
									  <tr> 
									  									 
									  <td class="row-odd"> 
									  			<div align="left">
												<bean:message key="knowledgepro.employee.eligibility.test"/>
												</div>
									  		</td>
											<td class="row-even" colspan="5">
												<logic:notEmpty name="GuestFacultyInfoForm" property="eligibilityList">
													<logic:iterate id="eTest" name="GuestFacultyInfoForm" property="eligibilityList" indexId="count">
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
									     			<html:text property="otherEligibilityTestValue" styleId="otherEligibilityTestValue" name="GuestFacultyInfoForm" size="10" maxlength="50"></html:text>
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
						<bean:message key="knowledgepro.employye.educational.details"/>
					</td>
				</tr>
				<tr>
						<td valign="top" class="news" colspan="3">
						<table id="Education" width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
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
										   	<logic:notEmpty property="qualificationMap" name="GuestFacultyInfoForm">
										   		<html:optionsCollection property="qualificationMap" label="value" value="key"/>
										   </logic:notEmpty>
									     </html:select>
										 </td>
										  <td width="28%"  class="row-odd"><div align="left"><bean:message key="knowledgepro.employee.HighQyalForWebsite"/></div></td>
                						<td width="22%" class="row-even" colspan="2">
               							 <html:text property="highQualifForWebsite" styleId="highQualifForWebsite" maxlength="50"></html:text>
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
								<logic:notEmpty property="employeeInfoTONew.empQualificationFixedTo" name="GuestFacultyInfoForm">
									<nested:iterate id="qualificationTo" property="employeeInfoTONew.empQualificationFixedTo" name="GuestFacultyInfoForm" indexId="yr">
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
								
								<logic:notEmpty property="employeeInfoTONew.empQualificationLevelTos" name="GuestFacultyInfoForm">
									<nested:iterate id="levelTo" property="employeeInfoTONew.empQualificationLevelTos" name="GuestFacultyInfoForm" indexId="yrs">
									<tr>
									<td class="row-odd">
									<div align="left">
									<nested:select property="educationId" >
										<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									  	<logic:notEmpty property="qualificationLevelMap" name="GuestFacultyInfoForm">
									  		<html:optionsCollection property="qualificationLevelMap" label="value" value="key" name="GuestFacultyInfoForm"/>
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
									 <c:if test="${GuestFacultyInfoForm.levelSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitEmployeeInfoAdd('removeQualificationLevel','ExpAddMore'); return false;"></html:submit>
			                         </c:if>
									
									</td> 
			                    </tr>
								
								 <tr>
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
								</tr>	
								 
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
						<td class="row-odd" colspan="1" align="left">
						<bean:message key="knowledgepro.employee.anyother"/>
					</td>
				
						<td class="row-even">
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
	
	</td>
	</tr>
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
			
		
			
	


