
function submitAdmissionForm(val){
    document.getElementById("method").value=val;
    document.onlineApplicationForm.submit();
}


function submitAdmissionFormAttachment(val){
	
	
if(document.getElementById('editDocument').files[0]!=null){
	 

	var size = document.getElementById('editDocument').files[0].size;
	
	if(size<101000){
		document.getElementById("method").value=val;
	    document.onlineApplicationForm.submit();
	}else{
		document.getElementById('editDocument').value="";
		$.confirm({
			'message'	: 'Photograph size should be less than 100kb',
			'buttons'	: {
				'Close'	: {
					'class'	: 'blue',
					'action': function(){
						$.confirm.hide();
					}
				}
			}
		});
	}

}
/*else if (document.getElementById("ismgquotaYes").checked==false && document.getElementById("ismgquotaNo").checked==false) {
	document.getElementById("radiomsg").textContent="*Apply for Management Quota Required";
	console.log("Apply for Management Quota");
}*/
else{
	document.getElementById("method").value=val;
    document.onlineApplicationForm.submit();
}
    
}

function submitFinalAdmissionForm(val){

	
	$.confirm({
		
		'message'	: 'WARNING: If you give OK. You can not do anymore editing ',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					$.confirm.hide();
					
					 document.getElementById("method").value=val;
					 document.onlineApplicationForm.submit();
				}
			},
        'Cancel'	:  {
				'class'	: 'gray',
				'action': function(){
					$.confirm.hide();
				}
			}
		}
	});


}


function submitAppForm(){
	 document.getElementById("method").value="submitGuidelinesPage";
	 document.getElementById("onlineApplicationForm").submit();
}

function submitOnlineAppForm(){
    document.getElementById("method").value="initOnlineApply";
    document.onlineApplicationForm.submit();
}

function submitAppDeclineForm(){
	document.location.href="uniqueIdRegistration.do?method=loginOnlineApplication&propertyName=back";
    
}
function submitAppFwdFirstPageForm(){
    document.getElementById("method").value="forwardOnlineFirstPage";
    document.getElementById("displayPage").value="basic";
    document.getElementById("onlineApplicationForm").submit();
}
function disableTempAddress()
{
	  $("#currLabel").hide();
	  $("#currTable").hide();
	//document.getElementById("currLabel").style.display="none";
	//document.getElementById("currTable").style.display="none";
}
function enableTempAddress()
{
	  $("#currLabel").show();
	  $("#currTable").show();
	//document.getElementById("currLabel").style.display="block";
	//document.getElementById("currTable").style.display="block";
}
// ajax functions
var destID;
var destCityId;
function getStates(country,destId) { 
	destID=destId;
	document.getElementById("otherBirthState").value="";
	$("#otherBirthState").hide();
	getStatesByCountry("birthStateMap",country,destId,updateState);	
	
}
function getPermAddrStates(country,destId) { 
	destID=destId;
	document.getElementById("otherPermAddrState").value="";
	$("#otherPermAddrState").hide();
	
	getStatesByCountry("permAddrStateMap",country,destId,updateState);	
}
function getTempAddrStates(country,destId) { 
	destID=destId;
	document.getElementById("otherTempAddrState").value="";
	$("#otherTempAddrState").hide();
	getStatesByCountry("tempAddrStateMap",country,destId,updateState);	
}
function getParentAddrStates(country,destId) { 
	destID=destId;
	document.getElementById("otherParentAddrState").value="";
	$("#otherParentAddrState").hide();
	
	getStatesByCountry("parentAddrStateMap",country,destId,updateState);	
}
function getGuardianAddrStates(country,destId) { 
	destID=destId;
	document.getElementById("otherGuardianAddrState").value="";
	$("#otherGuardianAddrState").hide();
	
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
	getApplicationProgramsByTypeNew("programApplnMap",programType,destId,updateProgram);	
}

function updateProgram(req) {
	updateOptionsFromMap(req,destID,"-Select-"); 
}



function getCourse(program,destId) {
	destID=destId;
	getCoursesByProgramForOnlineNew("coursesMap",program,destId,updateCourse);	
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
 	document.location.href="onlineApplicationSubmit.do?method=forwardEducationAdmissionForm";
 }
 function submitApplicationCancelButton(){
	 	document.location.href="onlineApplicationSubmit.do?method=cancelApplication";
	 }
 function submitEditResetButton(){
	 	document.location.href="onlineApplicationSubmit.do?method=getApplicantDetailsForEdit";
	 }
  function submitEditCancelButton(){
	 	document.location.href="onlineApplicationSubmit.do?method=initApplicationEdit";
	 }
  function submitApproveCancelButton(){
	 	document.location.href="onlineApplicationSubmit.do?method=initAdmissionApproval";
	 }
  function submitModifyCancelButton(){
	 	document.location.href="onlineApplicationSubmit.do?method=initApplicationModify";
	 }
 function submitEditDetailCancelButton(){
	 	document.location.href="onlineApplicationSubmit.do?method=forwardEditApplicationPage";
	 }
 function submitModifyDetailCancelButton(){
	 	document.location.href="onlineApplicationSubmit.do?method=forwardModifyApplicationPage";
	 }
 
 function submitConfirmCancelButton(){
	     document.getElementById("method").value="forwardCancelPage";
	    document.onlineApplicationForm.submit();
	 }
 
 //raghu
 function submitConfirmCancelButtonView(){
     document.getElementById("method").value="submitPeviewApplication";
    document.onlineApplicationForm.submit();
 }
 
 function IsAlpha(e) {
	 var inputValue = e.which;
	 if(!((inputValue >= 65 && inputValue <= 90) || (inputValue >= 97 && inputValue <= 122)) && (inputValue != 32 && inputValue != 0) && (inputValue != 8 && inputValue != 0) && (inputValue != 127 && inputValue != 0)) {
		  e.preventDefault(); 
		  }
}
 
 function IsAlphaDot(e) {
	 var inputValue = e.which;
	 if(!((inputValue >= 65 && inputValue <= 90) || (inputValue >= 97 && inputValue <= 122)) && (inputValue != 46 && inputValue != 0) && (inputValue != 32 && inputValue != 0) && (inputValue != 8 && inputValue != 0) && (inputValue != 127 && inputValue != 0)) {
		  e.preventDefault(); 
		  }
}
 
 function resetForm(name) {
	 document.getElementById(name).reset();
	 $('#email').next('.tooltip_outer').hide();
	 $('#confirmEmailId').next('.tooltip_outer').hide();
}
 
 

 function disablePermAddress()
 {
 	  $("#permLabel").hide();
 	  $("#permTable").hide();
 	//document.getElementById("currLabel").style.display="none";
 	//document.getElementById("currTable").style.display="none";
 }
 
 function enablePermAddress()
 {
 	  $("#permLabel").show();
 	  $("#permTable").show();
 	//document.getElementById("currLabel").style.display="block";
 	//document.getElementById("currTable").style.display="block";
 }
 
 
//All resets...
 
 function resetApplicationDetails()
 {
 	if(document.onlineApplicationForm.programTypeId!=null){
	 document.onlineApplicationForm.programTypeId.value="";
 	}
 	if(document.onlineApplicationForm.programId!=null){
 	document.onlineApplicationForm.programId.value="";
 	}
 	if(document.onlineApplicationForm.courseId!=null){
 	document.onlineApplicationForm.courseId.value="";
 	}
 	document.onlineApplicationForm.challanNo.value="";
 	document.onlineApplicationForm.journalNo.value="";
 	document.onlineApplicationForm.applicationDate.value="";
 	if(document.onlineApplicationForm.applicationAmount.disabled == false) {
 		document.onlineApplicationForm.applicationAmount.value="";
 	}
 
 	document.onlineApplicationForm.bankBranch.value="";
 	resetErrMsgs();
 }
 function resetStudentDetails(){
	
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
	 document.onlineApplicationForm.fatherName.value="";
	 document.onlineApplicationForm.fatherEducation.value="";
	 document.onlineApplicationForm.fatherIncome.value="";
	 document.onlineApplicationForm.fatherCurrency.value="";
	 document.onlineApplicationForm.fatherOccupation.value="";
	 document.onlineApplicationForm.fatherEmail.value="";
	 
	 document.onlineApplicationForm.motherName.value="";
	 document.onlineApplicationForm.motherEducation.value="";
	 document.onlineApplicationForm.motherIncome.value="";
	 document.onlineApplicationForm.motherCurrency.value="";
	 document.onlineApplicationForm.motherOccupation.value="";
	 document.onlineApplicationForm.motherEmail.value="";
	 
	 document.getElementById("parentAddraddress1").value="";
	 document.getElementById("parentAddraddress2").value="";
	 document.getElementById("parentCity").value="";
	 document.getElementById("parentState").value="";
	 document.getElementById("parentCountry").value="";
	 document.getElementById("parentAddrzip").value="";
	 
	 document.onlineApplicationForm.parentPhone1.value="";
	 document.onlineApplicationForm.parentPhone2.value="";
	 document.onlineApplicationForm.parentPhone3.value="";
	 document.onlineApplicationForm.parentMobile1.value="";
	 document.onlineApplicationForm.parentMobile2.value="";
	 document.onlineApplicationForm.parentMobile3.value="";
	 
	 document.onlineApplicationForm.guardianName.value="";
	 
	 document.getElementById("guardianAddraddress1").value="";
	 document.getElementById("guardianAddraddress2").value="";
	 document.getElementById("guardianAddraddress3").value="";
	 document.getElementById("guardianCity").value="";
	 document.getElementById("guardianState").value="";
	 document.getElementById("guardianCountry").value="";
	 document.getElementById("guardianAddrzip").value="";
	 
	 document.onlineApplicationForm.guardianPhone1.value="";
	 document.onlineApplicationForm.guardianPhone2.value="";
	 document.onlineApplicationForm.guardianPhone3.value="";
	 document.onlineApplicationForm.guardianMobile1.value="";
	 document.onlineApplicationForm.guardianMobile2.value="";
	 document.onlineApplicationForm.guardianMobile3.value="";
	 
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
		 	document.location.href="onlineApplicationSubmit.do?method=initApplicationModify&PUCEdit=true";
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
	  
	  function funcOtherOccupationShowHide(id,rowid,count){
			 var selectedVal=document.getElementById(id).value;
			if(selectedVal=="Other"){
				FuncOtherOccupationShow(rowid,count);
			}else{
				FuncOtherOccupationHide(rowid,count);
			}
		 }	  
	  
	  function FuncOtherOccupationShow(rowid,count){
			var instituteid="occupation"+count;
		    document.getElementById(instituteid).style.display = "block";    
	 }
	 function FuncOtherOccupationHide(rowid,count){
		 var instituteid="occupation"+count;
		 document.getElementById(instituteid).style.display = "none";   
	 }
	 function getCourseForOnline(program,destId) {
		    $("#showIntrAdmSession").hide();
			destID=destId;
			getCoursesByProgramForOnlineNew("coursesMap",program,destId,updateCourse);	
	 }
	 
	 
	 
	 function getProgramByType(programType,destId) {
			destID = destId;
			 var year=document.getElementById("year").value;
			getProgramsByPgmTypeNew(year,programType,destId,updateProgramS);	
		}
		
		function updateProgramS(req) {
			updateOptionsFromMap(req,destID,"-Select-"); 
		}
		
		function getProgramsTypeByYear(year,destId) {
			destID = destId;
			getProgramsTypeByYear(year,destId,updateProgramByYear);	
		}
		
		function updateProgramByYear(req) {
			updateOptionsFromMap(req,destID,"-Select-"); 
		}
		
		//detail application Scripts starts.............
		function noCopyMouse(e) {
		    var isRight = (e.button) ? (e.button == 2) : (e.which == 3);
		    if(isRight) {
		    	alert('Please write the re confimation mail');
				document.getElementById("confirmEmailId").value="";
		        return false;
		    }
		    return true;
		}

		function noCopyKey(e) {
		    var isCtrl;
		isCtrl = e.ctrlKey
		if(isCtrl) {
				document.getElementById("confirmEmailId").value="";
		    		return false;
		    		
		      	}
			
			if(e.keyCode == 17){
				document.getElementById("confirmEmailId").value="";
			return false;
			}

			//noCopyMouse(e);
		    	return true;
		}
		
		function onLoadAddrCheck()
		{
			//permanent address
			var sameAddr= document.getElementById("sameAddr").checked;
			if(sameAddr==true){
				disableTempAddress();
			}
			if(sameAddr==false){
				enableTempAddress();
			}	
			//parent address
			var sameParAddr= document.getElementById("sameParAddr").checked;
			if(sameParAddr==true){
				disablePermAddress();
			}
			if(sameParAddr==false){
				enablePermAddress();
			}	
		}
			function detailSubmit(count)
			{
				document.getElementById("countID").value=count;
				document.onlineApplicationForm.method.value="initDetailMarkConfirmPage";
				document.onlineApplicationForm.submit();
			}
			
			function detailSubmitView(count)
			{
				document.getElementById("countID").value=count;
				document.onlineApplicationForm.method.value="initDetailMarkConfirmPageView";
				document.onlineApplicationForm.submit();
			}
			
			function detailSemesterSubmit(count)
			{
				document.getElementById("countID").value=count;
				document.onlineApplicationForm.method.value="initSemesterMarkConfirmPage";
				document.onlineApplicationForm.submit();
			}

			function showSportsDescription(){
				document.getElementById("sports_description").style.display = "block";
			}

			function hideSportsDescription(){
				document.getElementById("sports_description").style.display = "none";
				document.getElementById("sportsDescription").value = "";
			}

			function showHandicappedDescription(){
				document.getElementById("handicapped_description").style.display = "block";
			}

			function hideHandicappedDescription(){
				document.getElementById("handicapped_description").style.display = "none";
				document.getElementById("hadnicappedDescription").value = "";
			}
			function detailLateralSubmit()
			{
				document.onlineApplicationForm.method.value="initlateralEntryConfirmPage";
				document.onlineApplicationForm.submit();
			}
			function detailTransferSubmit()
			{
				document.onlineApplicationForm.method.value="initTransferEntryConfirmPage";
				document.onlineApplicationForm.submit();
			}
			function getDetailForEdit(applicationNumber,appliedYear,method) {
				document.onlineApplicationForm.method.value=method;
				document.onlineApplicationForm.detailsView.value=false;
				document.onlineApplicationForm.selectedAppNo.value=applicationNumber;
				document.onlineApplicationForm.selectedYear.value=appliedYear;
				document.onlineApplicationForm.submit();
			}
			function clearApplnNOField(field){
				if(field.value == "0")
					field.value = "";
			}
			function checkForEmptyAppNO(field){
				if(field.value.length == 0)
					field.value="0";
				if(isNaN(field.value)) {
					field.value="0";
				}
			}

			function isValidNumber(field) {
				if (isNaN(field.value)) {
					field.value = "";
				}
			}

			function searchYearMonthWise(){
				var examMonth = document.getElementById("examMonth").value;
					getYearByMonthWise(examMonth, updateYearMap);
			}
			function updateYearMap(req){
					updateOptionsFromMap(req,"examYear","-Select-");
				
			}
		
		   		var startDate;
		   		var tenMinAlert=false;
		   		var oneMinAlert=false;
		   		var finalAlert=false;
		   		function setStartDate()
		   		{
					startDate=new Date();
		   		}	

		       	function checkSession()
		       	{
		       		var fourtyFiveMin = 1000 * 60*45;
					var fourtySixMin=1000*60*46;	
		       		var fiftyFourMin = 1000 *60* 54;
		       		var fiftyFive=1000*60*55;
		       		var fiftySeven=1000*60*57;
		       		var fiftyEight=1000*60*58;
		       		var sixtyMin = 1000 * 60*59;
		       		var currentDate=new Date();
		       		// Convert both dates to milliseconds
		       		var date1_ms = startDate.getTime();
		       		var date2_ms = currentDate.getTime();

		       		// Calculate the difference in milliseconds
		       		var difference_ms = Math.abs(date1_ms - date2_ms);
		       		
		       		if(difference_ms>=fourtyFiveMin && difference_ms<=fourtySixMin && !tenMinAlert)
		       		{
						alert("Your session is about to expire in 10 min");
						tenMinAlert=true;	
		       		}
		       		else
		       		if(difference_ms>=fiftyFourMin && difference_ms<=fiftyFive && !oneMinAlert)
		       		{
						alert("Your session is about to expire in 2 minute");
						oneMinAlert=true;
		       		}
		       		else
		       		if(difference_ms>=fiftySeven && difference_ms<=fiftyEight && !finalAlert)
		           	{
		       			finalAlert=true;
						alert("Your Session Expired. Click on Ok to continue within 30 seconds.");
						currentDate=new Date();
			       		date2_ms = currentDate.getTime();
		       			difference_ms = Math.abs(date1_ms - date2_ms);
		       			if(difference_ms>=sixtyMin)	
						{
		    				window.location="sessionTimeOutPageOniline.jsp";	
						}
		       			else
		       			{
		       				document.onlineApplicationForm.method.value="sessionExpired";
							document.onlineApplicationForm.submit();
		       			}		
		           	}
		       		if(difference_ms>=sixtyMin)	
					{
						window.location="sessionTimeOutPageOniline.jsp";	
					}				
		       		
		       	}	

		    function getDateByVenueselection(selectedVenue){
			        if(selectedVenue!=null && selectedVenue!=""){
			        	var courseId=document.getElementById("courseId").value;
			            var programId=document.getElementById("programId").value;
			            var programYear=document.getElementById("programYear").value;
			            var online=document.getElementById("onlineApply").value;
			       		if(online!=null && online=='true'){
			        		getDateBySelectionVenue(selectedVenue,updateDateMap, programId, programYear, courseId);
			       		}else
			       		{
			       			getDateByVenueSelectionOffline(selectedVenue,updateDateMap, programId, programYear, courseId);
			       		}
			        }	
		    	}
		    	function updateDateMap(req){
		    		updateOptionsFromMap(req,"interviewSelectionDate","-Select-");
		    	}

		    	function SubmitForm()
		    	{
		    			document.getElementById("focusValue").value=null;
			        	var isInterviewSelectionSchedule=document.getElementById("isInterviewSelectionSchedule").value;
			    		if(isInterviewSelectionSchedule!=null && isInterviewSelectionSchedule!="" && isInterviewSelectionSchedule=="true"){
			    			var result=CheckAgreed();
			    			if(result)
			        			{
			    				var courseId=document.getElementById("courseId").value;
			    				var isInterviewSelectionSchedule=document.getElementById("isInterviewSelectionSchedule").value;
			    					
			    				document.getElementById("method").value="submitApplicantCreation";
			    	    		document.onlineApplicationForm.submit();
			    				}
			    		}else
			    		{
			    			document.getElementById("method").value="submitApplicantCreation";
			        		document.onlineApplicationForm.submit();	
			    		}
		    		
		    	}	
		    	function CheckAgreed(){
		    		if (document.getElementById("acceptAll").checked) {
		        		
		        		return true;
		    		} else {
		    			alert("Please Accept Terms & Conditions in Interview/Entrance Selection");
						return false;
		        		}
		    	}
		    	
		    	function cancel(){
		    		//document.location.href="uniqueIdRegistration.do?method=loginOnlineApplication&propertyName=back";
		    		
		    		$.confirm({
		        		
		        		'message'	: 'WARNING: If you Logout, your current page changes will not be saved.',
		        		'buttons'	: {
		        			'Ok'	: {
		        				'class'	: 'blue',
		        				'action': function(){
		        					$.confirm.hide();
		        					
		        					//document.location.href="uniqueIdRegistration.do?method=initOnlineApplicationLogin";
		        					//document.location.href="onlineApplicationSubmit.do?method=logoutFromOnlineApplication";
		        					document.onlineApplicationForm.method.value="logoutFromOnlineApplication";
		        					document.onlineApplicationForm.submit();
		        				}
		        			},
		                'Cancel'	:  {
		        				'class'	: 'gray',
		        				'action': function(){
		        					$.confirm.hide();
		        					return false;
		        				}
		        			}
		        		}
		        	});


		    	
		    	
		    	}




		    	function cancel1(){
		    		//document.location.href="uniqueIdRegistration.do?method=loginOnlineApplication&propertyName=back";
		    		//document.location.href="uniqueIdRegistration.do?method=initOnlineApplicationLogin";
		    	}

		    	
		    	function cancel2(){
		    		
		    		document.onlineApplicationForm.method.value="logoutFromOnlineApplication";
		    		document.onlineApplicationForm.submit();
		    		//document.location.href="uniqueIdRegistration.do?method=loginOnlineApplication&propertyName=back";
		    		//document.location.href="uniqueIdRegistration.do?method=initOnlineApplicationLogin";
		    	}

			/* prerequisites scripts begins.....*/
		 
		 
		 function isValidNumber(field) {
			if (isNaN(field.value)) {
				field.value = "";
			}
		}

			function searchYearMonthWise(count){
				var examMonth = document.getElementById("examMonth_"+count).value;
				if(count == 0){
					getYearByMonthWise(examMonth, updateYearMap);
				}
				if(count == 1){
					getYearByMonthWise(examMonth, updateYearMap1);
				}
				if(count == 2){
					getYearByMonthWise(examMonth, updateYearMap2);
				}
			}
			function updateYearMap(req){
				
				if(document.getElementById("examYear_0") != null){
					updateOptionsFromMap(req,"examYear_0","-Select-");
				}
			}

			function updateYearMap1(req){
				if(document.getElementById("examYear_1") != null){
					updateOptionsFromMap(req,"examYear_1","-Select-");
				}
			}
			function updateYearMap2(req){
				if(document.getElementById("examYear_2") != null){
					updateOptionsFromMap(req,"examYear_2","-Select-");
				}
			}
			//Scripts for Final Confirmation page..........
			
			function openPrintPage() {
				var url="onlineApplicationSubmit.do?method=forwardPrintWindow";
				window.open(url,'PrintApplication','left=20,top=20,width=800,height=1000,toolbar=1,resizable=0,scrollbars=1');		
			}
			function fnUnloadHandler() {  
				document.location.href = "LogoutAction.do?method=logout";	
			}  

			function submitFORCANCEL(method){
				document.location.href= method;
			}

			function submitFORWindow(method){
				var url=method;
				window.open(url,'OnlineStatus','left=20,top=20,width=800,height=1000,toolbar=1,resizable=0,scrollbars=1');
			}
			function onlineApplicationStatus(){
				
				document.location.href="uniqueIdRegistration.do?method=initOnlineApplicationLogin";
			}
			
			
			
			document.onkeydown = function (event) {
				
				if (!event) { /* This will happen in IE */
					event = window.event;
				}
					
				var keyCode = event.keyCode;
				
				if (keyCode == 8 &&
					((event.target || event.srcElement).tagName != "TEXTAREA") && 
					((event.target || event.srcElement).tagName != "INPUT")) { 
					
					if (navigator.userAgent.toLowerCase().indexOf("msie") == -1) {
						event.stopPropagation();
					} else {
						alert("prevented");
						event.returnValue = false;
					}
					
					return false;
				}
			};

			$(document).ready(function() {			
				var ctrlKeyDown = false;

				$(document).on("keydown", keydown);
			    $(document).on("keyup", keyup);
			   

			    function keydown(e) { 
			        if ((e.which || e.keyCode) == 116 || ((e.which || e.keyCode) == 82 && ctrlKeyDown)) {
			            // Pressing F5 or Ctrl+R
			            e.preventDefault();
			        } else if ((e.which || e.keyCode) == 17) {
			            // Pressing  only Ctrl
			            ctrlKeyDown = true;
			        }
			    };

			    function click(e){
			    	e.preventDefault();
			    };

			    function keyup(e){
			        // Key up Ctrl
			        if ((e.which || e.keyCode) == 17) 
			            ctrlKeyDown = false;
			    };
				
		});		
			
			
			