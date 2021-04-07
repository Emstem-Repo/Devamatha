function submitAdmissionForm(method){
	document.admissionFormForm.method.value=method;
	document.admissionFormForm.submit();
}
function submitCancel(){
	document.admissionFormForm.method.value="cancelApplication";
	document.admissionFormForm.submit();
}

function disableTempAddress()
{
	document.getElementById("currLabel").style.display="none";
	document.getElementById("currTable").style.display="none";
}
function enableTempAddress()
{
	document.getElementById("currLabel").style.display="block";
	document.getElementById("currTable").style.display="block";
}
// ajax functions
var destID;
var destCityId;
function getStates(country,destId) { 
	destID=destId;
	getStatesByCountry("birthStateMap",country,destId,updateState);	
}
function getPermAddrStates(country,destId) { 
	destID=destId;
	getStatesByCountry("permAddrStateMap",country,destId,updateState);	
}
function getTempAddrStates(country,destId) { 
	destID=destId;
	getStatesByCountry("tempAddrStateMap",country,destId,updateState);	
}
function getParentAddrStates(country,destId) { 
	destID=destId;
	getStatesByCountry("parentAddrStateMap",country,destId,updateState);	
}
function getGuardianAddrStates(country,destId) { 
	destID=destId;
	getStatesByCountry("guardianAddrStateMap",country,destId,updateState);	
}

function updateState(req) {
	updateOptionsFromMapWithOther(req,destID,"-Select-"); 
}
function getCities(state,destcityId) {
	destCityId=destcityId;
	getCitiesByCountry("permAddrCityMap",state,destcityId,updateCities);
}
function getTempAddrCities(state,destcityId) {
	destCityId=destcityId;
	getCitiesByCountry("tempAddrCityMap",state,destcityId,updateCities);
}
function getParentAddrCities(state,destcityId) {
	destCityId=destcityId;
	getCitiesByCountry("parentAddrCityMap",state,destcityId,updateCities);
}
function updateCities(req) {
	updateOptionsFromMapWithOther(req,destCityId,"-Select-"); 
}
function getPrograms(programType,destId) {
	destID = destId;
	getProgramsByType("programMap",programType,destId,updateProgram);	
}

function getApplicationPrograms(programType,destId) {
	destID = destId;
	getApplicationProgramsByType("programApplnMap",programType,destId,updateProgram);	
}

function updateProgram(req) {
	updateOptionsFromMap(req,destID,"-Select-"); 
}



function getCourse(program,destId) {
	destID=destId;
	getCoursesByProgram("coursesMap",program,destId,updateCourse);	
}

function updateCourse(req) {
	updateOptionsFromMap(req,destID,"-Select-"); 
}

function getSubReligion(religion,destId) {
	 if(document.getElementById(destId)!=null){
	destID=destId;
	getSubReligionByReligion("subReligionMap",religion,destId,updateSubReligion);
	 }
}

function updateSubReligion(req) {
	updateOptionsFromMapWithOther(req,destID,"-Select-"); 
}
var prefDest;
function getUniquePreference(prefId,destId) {
	 if(document.getElementById(destId)!=null){
		 prefDest=destId;
	getUniquePreferenceList("preferenceMap",prefId,destId,updatePreferences);
	 }
}
function getDynaUniquePreference(propName,prefId,destId) {
	 if(document.getElementById(destId)!=null){
		 prefDest=destId;
	getUniquePreferenceList(propName,prefId,destId,updatePreferences);
	 }
}

function updatePreferences(req) {
	updateOptionsFromMap(req,prefDest,"-Select-"); 
}
// Education jsp scripts...

var globalID;
var count;


var dynarow2id;
function getColleges(propertyName,university,destId) {
	count=destId;
	globalID=destId+"Institute";
	if(university.value=="Other"){
		var colleges = document.getElementById(globalID);
		 for (x1=colleges.options.length-1; x1>0; x1--)
		 {
			 colleges.options[x1]=null;
		 }
		 colleges.options[0]=new Option("-Select-","");
		 colleges.options[1] = new Option("Other","Other");
		 
		return;
	}
	if(university.value != '0') {
		var args = "method=getCollegeByUniversity&universityId="+university.value+"&propertyName="+propertyName;
	  	var url ="AjaxRequest.do";
	  	// make an request to server passing URL need to be invoked and arguments.
		requestOperation(url,args,updateColleges);
	} else {
		 var colleges = document.getElementById(globalID);
		 for (x1=colleges.options.length-1; x1>0; x1--)
		 {
			 colleges.options[x1]=null;
		 }
	}	
}

function updateColleges(req) {
	updateOptionsFromMapValues(req,globalID,count,"-Select-"); 
}
function updateOptionsFromMapValues(req,destinationOption,count,defaultSelectValue) {
	var responseObj = req.responseXML.documentElement;

	var destination = document.getElementById(destinationOption);
	for (x1=destination.options.length-1; x1>0; x1--) {
		destination.options[x1]=null;
	}
	var childNodes = responseObj.childNodes;
	destination.options[0]=new Option(defaultSelectValue,"");
	 var items = responseObj.getElementsByTagName("option");
	
	 var label,value;
	 for (var i = 0 ; i < items.length ; i++) {
        label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination.options[i+1] = new Option(label,value);		
	 }
	 destination.options[items.length+1] = new Option("Other","Other");
		  
}

function FuncOtherUniversityShow(row1Id,row2Id,institueid){
        document.getElementById(row1Id).style.display = "block";
        document.getElementById(row2Id).style.display = "block";
        document.getElementById(institueid).value="Other";
      //  document.getElementById(institueid).disabled = true;
 
 }
 
function FuncOtherUniversityHide(row1Id,row2Id,institueid){
        document.getElementById(row1Id).style.display = "none";
        document.getElementById(row2Id).style.display = "none";
       // document.getElementById(institueid).disabled = false;

 }
 
 function FuncOtherInstituteShow(rowid,count){
		var instituteid="Institute"+count;
	    document.getElementById(instituteid).style.display = "block";    
 }
 function FuncOtherInstituteHide(rowid,count){
	 var instituteid="Institute"+count;
	 document.getElementById(instituteid).style.display = "none";   
 }



 function funcOtherUniversityShowHide(row1Id,id,row2Id,institueid){
	 var selectedVal=document.getElementById(id).value;
	if(selectedVal=="Other"){
		FuncOtherUniversityShow(row1Id,row2Id,institueid);
	}else{
		FuncOtherUniversityHide(row1Id,row2Id,institueid);
	}
 }

 function funcOtherInstituteShowHide(id,rowid,count){
	 var selectedVal=document.getElementById(id).value;
	if(selectedVal=="Other"){
		FuncOtherInstituteShow(rowid,count);
	}else{
		FuncOtherInstituteHide(rowid,count);
	}
 }
 
//Detail Mark Entry JS
 
 function updatetotalMark(count)
 {
 	var totalmark=document.getElementById("totalMark").value;
 		totalmark=0;
 	var i;
 	for(i=1;i<=count;i++){
 		var subjectmark=document.getElementById("detailMark.subject"+i+"TotalMarks").value;
 		if(subjectmark.length==0){
 			subjectmark=0;
 		}
 		if(IsNumeric(totalmark))
 		{
 			totalmark=parseInt(subjectmark)+parseInt(totalmark);
 			//if(totalmark==0){
 				//document.getElementById("totalMark").value=0;
 			//}else{
 				document.getElementById("totalMark").value=totalmark;
 			//}
 		}
 	}
 }
 function updateObtainMark(count)
 {
 	var obtainmark=document.getElementById("ObtainedMark").value;
 	obtainmark=0;

 		var i;
 		for(i=1;i<=count;i++){
 			var subjectmark=document.getElementById("detailMark.subject"+i+"ObtainedMarks").value;
 			if(subjectmark.length==0){
 				subjectmark=0;
 			}
 			if(IsNumeric(obtainmark))
 			{
 				obtainmark=parseInt(subjectmark)+parseInt(obtainmark);
 				//if(obtainmark==0){
 				//document.getElementById("ObtainedMark").value="";
 			//	}else{
 					document.getElementById("ObtainedMark").value=obtainmark;
 				//}
 			}
 		}
 }
 
 function updatelangtotalMark(count)
 {
 	var totallangmark=document.getElementById("totallangMark").value;
 		totallangmark=0;
 	var i;
 	for(i=1;i<=count;i++){
 		var subjectmark=document.getElementById("detailMark.subject"+i+"_languagewise_TotalMarks").value;
 		if(subjectmark.length==0){
 			subjectmark=0;
 		}
 		if(IsNumeric(totallangmark))
 		{
 			totallangmark=parseInt(subjectmark)+parseInt(totallangmark);
 			//if(totalmark==0){
 				//document.getElementById("totalMark").value=0;
 			//}else{
 				document.getElementById("totallangMark").value=totallangmark;
 			//}
 		}
 	}
 }
 function updatelangObtainMark(count)
 {
 	var obtainmark=document.getElementById("ObtaintedlangMark").value;
 	obtainmark=0;
 		var i;
 		for(i=1;i<=count;i++){
 			var subjectmark=document.getElementById("detailMark.subject"+i+"_languagewise_ObtainedMarks").value;
 			if(subjectmark.length==0){
 				subjectmark=0;
 			}
 			if(IsNumeric(obtainmark))
 			{
 				obtainmark=parseInt(subjectmark)+parseInt(obtainmark);
 				//if(obtainmark==0){
 				//document.getElementById("ObtainedMark").value="";
 			//	}else{
 					document.getElementById("ObtaintedlangMark").value=obtainmark;
 				//}
 			}
 		}
 }
 
 function IsNumeric(sText)
 {
    var ValidChars = "0123456789.";
    var IsNumber=true;
    var Char;
    for (i = 0; i < sText.length && IsNumber == true; i++) 
       { 
       Char = sText.charAt(i); 
       if (ValidChars.indexOf(Char) == -1) 
          {
          IsNumber = false;
          }
       }
    return IsNumber;
    
    }
 function submitCancelButton(){
 	document.location.href="admissionFormSubmit.do?method=forwardEducationAdmissionForm";
 }
 function submitApplicationCancelButton(){
	 	document.location.href="admissionFormSubmit.do?method=cancelApplication";
	 }
 function submitEditResetButton(){
	 	document.location.href="admissionFormSubmit.do?method=getApplicantDetailsForEdit";
	 }
  function submitEditCancelButton(){
	 	document.location.href="admissionFormSubmit.do?method=initApplicationEdit";
	 }
  function submitApproveCancelButton(){
	 	document.location.href="admissionFormSubmit.do?method=initAdmissionApproval";
	 }
  function submitModifyCancelButton(){
	 	document.location.href="admissionFormSubmit.do?method=initApplicationModify";
	 }
 function submitEditDetailCancelButton(){
	 	document.location.href="admissionFormSubmit.do?method=forwardEditApplicationPage";
	 }
 function submitModifyDetailCancelButton(){
	 	document.location.href="admissionFormSubmit.do?method=forwardModifyApplicationPage";
	 }
 
 function submitConfirmCancelButton(){
	 	document.location.href="admissionFormSubmit.do?method=forwardConfirmPage";
	 }
 
//All resets...
 
 function resetApplicationDetails()
 {
 	if(document.admissionFormForm.programTypeId!=null){
	 document.admissionFormForm.programTypeId.value="";
 	}
 	if(document.admissionFormForm.programId!=null){
 	document.admissionFormForm.programId.value="";
 	}
 	if(document.admissionFormForm.courseId!=null){
 	document.admissionFormForm.courseId.value="";
 	}
 	document.admissionFormForm.challanNo.value="";
 	document.admissionFormForm.journalNo.value="";
 	document.admissionFormForm.applicationDate.value="";
 	if(document.admissionFormForm.applicationAmount.disabled == false) {
 		document.admissionFormForm.applicationAmount.value="";
 	}
 
 	document.admissionFormForm.bankBranch.value="";
 	resetErrMsgs();
 }
 function resetStudentDetails(){
	/* if(document.admissionFormForm.applicationNumber!=null){
	 document.admissionFormForm.applicationNumber.value="";
	 }
	 document.admissionFormForm.firstName.value="";
	 if(document.admissionFormForm.middleName!=null){
	 document.admissionFormForm.middleName.value="";
	 }
	 if( document.admissionFormForm.lastName!=null){
	 document.admissionFormForm.lastName.value="";
	 }
	 document.admissionFormForm.dateOfBirth.value="";
	 document.admissionFormForm.birthPlace.value="";
	 document.admissionFormForm.birthState.value="";
	 document.admissionFormForm.country.value="";
	 
	 document.admissionFormForm.nationality.value="";
	 document.admissionFormForm.residentCategory.value="";
	 document.admissionFormForm.religionId.value="";
	 document.admissionFormForm.subReligion.value="";
	 document.admissionFormForm.castCategory.value="";
	 document.admissionFormForm.areaType.value="R";
	 document.admissionFormForm.gender.value="Male";
	 document.admissionFormForm.bloodGroup.value="";
	 
	 document.admissionFormForm.phone1.value="";
	 document.admissionFormForm.phone2.value="";
	 document.admissionFormForm.phone3.value="";
	 document.admissionFormForm.mobile1.value="";
	 document.admissionFormForm.mobile2.value="";
	 if(document.admissionFormForm.mobile3!=null){
	 document.admissionFormForm.mobile3.value="";
	 }
	 document.admissionFormForm.emailId.value="";
	 
	 document.admissionFormForm.passportNo.value="";
	 document.admissionFormForm.passportcountry.value="";
	 document.admissionFormForm.passportValidity.value="";
	 
	 if( document.getElementById("coursePref1")!=null){
	 document.getElementById("coursePref1").value="";
	 }
	
	 document.getElementById("coursePref2").value="";
	 
	 document.getElementById("coursePref3").value="";
	 
	 document.getElementById("permAddraddress1").value="";
	 document.getElementById("permAddraddress2").value="";
	 document.getElementById("permAddrcity").value="";
	 document.getElementById("permAddrstate").value="";
	 document.getElementById("permAddrcountry").value="";
	 document.getElementById("permAddrzip").value="";
	 
	 document.getElementById("sameAddr").value="false";
	 
	 document.getElementById("tempAddraddress1").value="";
	 document.getElementById("tempAddraddress2").value="";
	 document.getElementById("tempAddrcity").value="";
	 document.getElementById("tempAddrstate").value="";
	 document.getElementById("tempAddrcountry").value="";
	 document.getElementById("tempAddrzip").value="";
	 resetErrMsgs();*/
	 resetFieldAndErrMsgs();
 }
 
 function resetDetailMark(count){
	 document.getElementById("totalMark").value="";
	 document.getElementById("ObtainedMark").value="";
	 if( document.getElementById("totallangMark")!=null)
	 document.getElementById("totallangMark").value="";
	 if( document.getElementById("ObtaintedlangMark")!=null)
	 document.getElementById("ObtaintedlangMark").value="";
	var i;
	for(i=1;i<=count;i++){
		if(document.getElementById("detailMark.subject"+i)!=null){
		document.getElementById("detailMark.subject"+i).value="";
		}
		if(document.getElementById("detailMark.subject"+i+"TotalMarks")!=null)
		document.getElementById("detailMark.subject"+i+"TotalMarks").value="";
		if(document.getElementById("detailMark.subject"+i+"ObtainedMarks")!=null)
		document.getElementById("detailMark.subject"+i+"ObtainedMarks").value="";
		if(document.getElementById("detailMark.detailedSubjects"+i)!=null)
			document.getElementById("detailMark.detailedSubjects"+i).value="";
		if(document.getElementById("detailMark.subject"+i+"_languagewise_TotalMarks")!=null)
		document.getElementById("detailMark.subject"+i+"_languagewise_TotalMarks").value="";
		if(document.getElementById("detailMark.subject"+i+"_languagewise_ObtainedMarks")!=null)
		document.getElementById("detailMark.subject"+i+"_languagewise_ObtainedMarks").value="";
	}
	 resetErrMsgs();
 }
 
 function resetParentInfo(){
	 document.admissionFormForm.fatherName.value="";
	 document.admissionFormForm.fatherEducation.value="";
	 document.admissionFormForm.fatherIncome.value="";
	 document.admissionFormForm.fatherCurrency.value="";
	 document.admissionFormForm.fatherOccupation.value="";
	 document.admissionFormForm.fatherEmail.value="";
	 
	 document.admissionFormForm.motherName.value="";
	 document.admissionFormForm.motherEducation.value="";
	 document.admissionFormForm.motherIncome.value="";
	 document.admissionFormForm.motherCurrency.value="";
	 document.admissionFormForm.motherOccupation.value="";
	 document.admissionFormForm.motherEmail.value="";
	 
	 document.getElementById("parentAddraddress1").value="";
	 document.getElementById("parentAddraddress2").value="";
	 document.getElementById("parentCity").value="";
	 document.getElementById("parentState").value="";
	 document.getElementById("parentCountry").value="";
	 document.getElementById("parentAddrzip").value="";
	 
	 document.admissionFormForm.parentPhone1.value="";
	 document.admissionFormForm.parentPhone2.value="";
	 document.admissionFormForm.parentPhone3.value="";
	 document.admissionFormForm.parentMobile1.value="";
	 document.admissionFormForm.parentMobile2.value="";
	 document.admissionFormForm.parentMobile3.value="";
	 
	 document.admissionFormForm.guardianName.value="";
	 
	 document.getElementById("guardianAddraddress1").value="";
	 document.getElementById("guardianAddraddress2").value="";
	 document.getElementById("guardianAddraddress3").value="";
	 document.getElementById("guardianCity").value="";
	 document.getElementById("guardianState").value="";
	 document.getElementById("guardianCountry").value="";
	 document.getElementById("guardianAddrzip").value="";
	 
	 document.admissionFormForm.guardianPhone1.value="";
	 document.admissionFormForm.guardianPhone2.value="";
	 document.admissionFormForm.guardianPhone3.value="";
	 document.admissionFormForm.guardianMobile1.value="";
	 document.admissionFormForm.guardianMobile2.value="";
	 document.admissionFormForm.guardianMobile3.value="";
	 
	 resetErrMsgs();
 }
 function showOther(srcid,destid)
 {
	 if(document.getElementById(destid)!=null){
 	 document.getElementById(destid).style.display = "block";
	 }
 }
 function hideOther(id,destid)
 {
	 if(document.getElementById(destid)!=null){
	 document.getElementById(destid).style.display = "none";
	 }
 }
 function funcOtherShowHide(id,destid){
 	 var selectedVal=document.getElementById(id).value;
 	if(selectedVal=="Other"){
 		showOther(id,destid);
 	}else{
 		hideOther(id,destid);
 	}
 }
 
 function funcGuardianOtherShowHide(id,destid){
 	 var selectedVal=document.getElementById(id).value;
 	if(selectedVal=="Other"){
 		showOther(id,destid);
 	}else{
 		hideOther(id,destid);
 	}
 }
 
 function clearField(field){
		if(field.value == "0.0")
			field.value = "";
	}
	function checkForEmpty(field){
		if(field.value.length == 0)
			field.value="0.0";
		if(field.value == 0)
			field.value="0.0";
			
	}
	
	function calctotalobtainedMarkWithLanguage(count)
	 {
	 	var totalmark=document.getElementById("totalobtainedMarkWithLanguage").value;
	 		totalmark=0;
	 	var i;
	 	for(i=0;i<=count - 1;i++){
	 		var subjectmark=document.getElementById("marksObtained_languagewise"+i).value;
	 		if(subjectmark.length==0){
	 			subjectmark=0;
	 		}
	 		if(IsNumeric(totalmark))
	 		{
	 			totalmark=parseInt(subjectmark)+parseInt(totalmark);
	 			//if(totalmark==0){
	 				//document.getElementById("totalMark").value=0;
	 			//}else{
	 				document.getElementById("totalobtainedMarkWithLanguage").value=totalmark;
	 			//}
	 		}
	 	}
	 }
	function calctotaltotalMarkWithLanguage(count)
	 {
	 	var totalmark=document.getElementById("totalMarkWithLanguage").value;
	 		totalmark=0;
	 	var i;
	 	for(i=0;i<=count - 1;i++){
	 		var subjectmark=document.getElementById("maxMarks_languagewise"+i).value;
	 		if(subjectmark.length==0){
	 			subjectmark=0;
	 		}
	 		if(IsNumeric(totalmark))
	 		{
	 			totalmark=parseInt(subjectmark)+parseInt(totalmark);
	 			//if(totalmark==0){
	 				//document.getElementById("totalMark").value=0;
	 			//}else{
	 				document.getElementById("totalMarkWithLanguage").value=totalmark;
	 			//}
	 		}
	 	}
	 }
	function calctotalobtainedMarkWithoutLan(count)
	 {
	 	var totalmark=document.getElementById("totalobtainedMarkWithoutLan").value;
	 		totalmark=0;
	 	var i;
	 	for(i=0;i<=count - 1;i++){
	 		var subjectmark=document.getElementById("marksObtained"+i).value;
	 		if(subjectmark.length==0){
	 			subjectmark=0;
	 		}
	 		if(IsNumeric(totalmark))
	 		{
	 			totalmark=parseInt(subjectmark)+parseInt(totalmark);
	 			//if(totalmark==0){
	 				//document.getElementById("totalMark").value=0;
	 			//}else{
	 				document.getElementById("totalobtainedMarkWithoutLan").value=totalmark;
	 			//}
	 		}
	 	}
	 }
	function calctotalMarkWithoutLan(count)
	 {
	 	var totalmark=document.getElementById("totalMarkWithoutLan").value;
	 		totalmark=0;
	 	var i;
	 	for(i=0;i<=count - 1;i++){
	 		var subjectmark=document.getElementById("maxMarks"+i).value;
	 		if(subjectmark.length==0){
	 			subjectmark=0;
	 		}
	 		if(IsNumeric(totalmark))
	 		{
	 			totalmark=parseInt(subjectmark)+parseInt(totalmark);
	 			//if(totalmark==0){
	 				//document.getElementById("totalMark").value=0;
	 			//}else{
	 				document.getElementById("totalMarkWithoutLan").value=totalmark;
	 			//}
	 		}
	 	}
	 }
	
	  function submitModifyCancelButtonForPUC(){
		 	document.location.href="admissionFormSubmit.do?method=initApplicationModify&PUCEdit=true";
		 }
	

	  function checkTheFields(count){
		  var sem=document.getElementById("semisterNo_"+count).value;
		  if(sem==''){
			  for(i=0;i<12;i++){
				  document.getElementById("check_"+count+"_"+i).checked=false;
				  document.getElementById("check_"+count+"_"+i).disabled=false;
			  }
			  document.getElementById("selected1_"+count).checked=false;
		  }else{
			  sem=sem-0;
			  for(i=0;i<sem;i++){
				  document.getElementById("check_"+count+"_"+i).checked=true;
				  document.getElementById("check_"+count+"_"+i).disabled=false;
			  }
			  for(i=sem;i<12;i++){
				  document.getElementById("check_"+count+"_"+i).checked=false;
				  document.getElementById("check_"+count+"_"+i).disabled=true;
			  }
			  document.getElementById("selected1_"+count).checked=true;
		  }
	  }
	  
	  
	  function checkBoxField(count,count1)
	  {
		  var sem=document.getElementById("semisterNo_"+count).value;
		  sem=sem-0;
		  var c=count1;
		  c=c+1;
		  if(sem>=c){
			  var allChecked="";
			  if(sem!='')
			  {
				  for(i=0;i<sem;i++)
				  {
					  if(!document.getElementById("check_"+count+"_"+i).checked)
					  {
						  allChecked="false";
						  break;
					  }
					  else
					  {
						  allChecked="true";
					  }	
				  }
				  if(allChecked=="true")
					  document.getElementById("selected1_"+count).checked=true;
				  else
					  document.getElementById("selected1_"+count).checked=false;
			  }	  
			  
		  }else{
			  document.getElementById("check_"+count+"_"+count1).checked=false;
		  }
		  
	  }

	  function disableChekcBox(count){
		  if(document.getElementById("selected1_not_applicable_"+count).checked){
			  for(i=0;i<12;i++){
				  document.getElementById("check_"+count+"_"+i).checked=false;
				  document.getElementById("check_"+count+"_"+i).disabled=true;
			  }
			  document.getElementById("semisterNo_"+count).selectedIndex=0;
			  document.getElementById("semType_"+count).selectedIndex=0;
			  document.getElementById("semisterNo_"+count).disabled=true;
			  document.getElementById("semType_"+count).disabled=true;
		  }else{
			  for(i=0;i<12;i++){
				  document.getElementById("check_"+count+"_"+i).checked=false;
				  document.getElementById("check_"+count+"_"+i).disabled=false;
			  }
			  document.getElementById("semisterNo_"+count).disabled=false;
			  document.getElementById("semType_"+count).disabled=false;
		  }
	  }