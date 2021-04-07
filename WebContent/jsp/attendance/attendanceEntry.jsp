<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript" src="js/jquery.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
     <script src="js/ajax/AjaxUtil.js"></script> 
   <script  src="js/ajax/Ajax.js"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>   
<script type="text/javascript">
function getMandatory(value) {
	if(document.getElementById("subjectId").selectedIndex == 0){
		var destination3 = document.getElementById("batchId");
		for (x1=destination3.options.length-1; x1>0; x1--) {
			destination3.options[x1]=null;
		}
		if(value.length == 0) {
			document.getElementById("classMandatry").value = "no"; 
			document.getElementById("subjectMandatory").value ="no"; 
			document.getElementById("periodMandatory").value = "no"; 
			document.getElementById("teacherMandatory").value = "no"; 
			document.getElementById("batchMandatory").value = "no"; 
			document.getElementById("activityTypeMandatory").value = "no"; 
			document.getElementById("classsdiv").innerHTML = "Class:";
			document.getElementById("subjectdiv").innerHTML = "Subject:";
			document.getElementById("perioddiv").innerHTML = "Hour:";
			//document.getElementById("teacherdiv").innerHTML = "Teacher:";
			document.getElementById("batchdiv").innerHTML = "Batch Name:";
			document.getElementById("activitydiv").innerHTML = "Activity Type:";
		} else {	
		getMandatoryFieldsByAttendanceType("activityMap",value,updateMandatory);
		}
	}else {	
		getMandatoryFieldsByAttendanceType("activityMap",value,updateMandatory);
	}
}

function updateMandatory(req) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("fields");
	for (var i = 0 ; i < items.length ; i++) {
         var label = items[i].getElementsByTagName("field")[0].firstChild.nodeValue;
	     var value = items[i].getElementsByTagName("mandatory")[0].firstChild.nodeValue;
	     if(label == "Class") {
	    	 document.getElementById("classMandatry").value = value; 
			if(value == "yes") {
				document.getElementById("classsdiv").innerHTML = "<span class='MandatoryMark'>*</span>Class:";	
			} else {
				document.getElementById("classsdiv").innerHTML = "Class:";
			}		
	     } else if(label == "Subject") {
	    	 document.getElementById("subjectMandatory").value = value;
	    	 if(value == 'yes') {
					document.getElementById("subjectdiv").innerHTML = "<span class='MandatoryMark'>*</span>Subject:";	
				} else {
					document.getElementById("subjectdiv").innerHTML = "Subject:";
				}
	     } else if(label == "Hour") {
	    	 document.getElementById("periodMandatory").value = value;
	    	 if(value == "yes") {
					document.getElementById("perioddiv").innerHTML = "<span class='MandatoryMark'>*</span>Hour:";	
				} else {
					document.getElementById("perioddiv").innerHTML = "Hour:";
				}
	     } else if(label == "Teacher") {
	    	 document.getElementById("teacherMandatory").value = value;
	    	 if(value == "yes") {
					document.getElementById("teacherdiv").innerHTML = "<span class='MandatoryMark'>*</span>Teacher:";	
				} else {
					document.getElementById("teacherdiv").innerHTML = "Teacher:";
				}
	     } else if(label == "Batch name") {
	    	 document.getElementById("batchMandatory").value = value;
	    	 if(value == "yes") {
					document.getElementById("batchdiv").innerHTML = "<span class='MandatoryMark'>*</span>Batch Name:";	
				} else {
					document.getElementById("batchdiv").innerHTML = "Batch Name:";
				}
	     } else if(label == "Activity Type") {
	    	 document.getElementById("activityTypeMandatory").value = value;
	    	 if(value == "yes") {
					document.getElementById("activitydiv").innerHTML = "<span class='MandatoryMark'>*</span>Activity Type:";	
				} else {
					document.getElementById("activitydiv").innerHTML = "Activity Type:";
				}
	     }       
	 }
	var destination = document.getElementById("activityId");
	for (x1=destination.options.length-1; x1>0; x1--) {
		destination.options[x1]=null;
	}
	destination.options[0]=new Option("- Select -","");
	var items1 = responseObj.getElementsByTagName("option");
	for (var j = 0 ; j < items1.length ; j++) {
        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination.options[j+1] = new Option(label,value);
	 }
 
}


function getClassForTeacher(){
	var date=document.getElementById("attendancedate").value;
	if(date==null || date==''){
		 $.confirm({
				'message'	: '<b align="center">Date Is Required?</b>',
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
						}
					}
				}
			});
	}else{
	var teachers =  document.getElementById("teachers");
	var teacher ="";
	for (var i=0; i<teachers.options.length; i++) {
	    if (teachers.options[i].selected) {
	    	teacher= teachers.options[i].value;
	    	break;
	    }
	 }

		
	var destination1 = document.getElementById("subjectId");
	for (x1=destination1.options.length-1; x1>0; x1--) {
		destination1.options[x1]=null;
	}
	
	var destination2 = document.getElementById("periods");
	for (x1=destination2.options.length-1; x1>=0; x1--) {
		destination2.options[x1]=null;
	}

	var destination3 = document.getElementById("batchId");
	for (x1=destination3.options.length-1; x1>0; x1--) {
		destination3.options[x1]=null;
	}
	destination1.options[0]=new Option("- Select -","");
	destination3.options[0]=new Option("- Select -","");

	if(teachers.selectedIndex != -1) {
		//alert("testing.......");
		//destination1.options[0]=new Option("- Loading -","");
		//destination2.options[0]=new Option("- Loading -","");
		//var year = document.getElementById("academicYear").value;
		//var selectedClasses = new Array();
		//var count = 0;
		//for (var i=0; i<classes.options.length; i++) {
		    //if (classes.options[i].selected) {
		    	//selectedClasses[count] = classes.options[i].value;
		      //count++;
		    //}
		 //}	
		// getSubjectsPeriodsBatchForClassSendRequest(selectedClasses,year,updateSubjcetBatchPeriod);
	//	getSubjectsPeriodsBatchForTeacherAndClassSendRequest(selectedClasses,year, selectedTeacherId, updateSubjcetBatchPeriod);

		//getClassesByTeacherInMuliSelect("classMap", teacher, "classes", updateClasses);
		getClassesByTeacherAndDateInMuliSelect("classMap", teacher, "classes",date, updateClasses);
	} 
	}
}

function getClasses(year) {
	var destination1 = document.getElementById("subjectId");
	for (x1=destination1.options.length-1; x1>0; x1--) {
		destination1.options[x1]=null;
	}
	
	var destination2 = document.getElementById("periods");
	for (x1=destination2.options.length-1; x1>=0; x1--) {
		destination2.options[x1]=null;
	}

	var destination3 = document.getElementById("batchId");
	for (x1=destination3.options.length-1; x1>0; x1--) {
		destination3.options[x1]=null;
	}
	destination1.options[0]=new Option("- Select -","");
	destination3.options[0]=new Option("- Select -","");
	
	getClassesByYear("classMap", year, "classes", updateClasses);
}
function updateClasses(req) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById("classes");
	for (x1=destination.options.length-1; x1>=0; x1--) {
		destination.options[x1]=null;
	}
	destination.options[0] = new Option("-Select-", "");
	var items1 = responseObj.getElementsByTagName("option");
	for (var j = 0 ; j < items1.length ; j++) {
        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination.options[j+1] = new Option(label,value);
	 }
}
function getSubjectsPeriodsBatchForTeacherAndClass() {
	var classes =  document.getElementById("classes");
	
	var destination1 = document.getElementById("subjectId");
	for (x1=destination1.options.length-1; x1>0; x1--) {
		destination1.options[x1]=null;
	}
		var destination2 = document.getElementById("periods");
	for (x1=destination2.options.length-1; x1>=0; x1--) {
		destination2.options[x1]=null;
	}
	var destination3 = document.getElementById("batchId");
	for (x1=destination3.options.length-1; x1>0; x1--) {
		destination3.options[x1]=null;
	}
	var teachers = document.getElementById("teachers");
	var selectedTeacherId = teachers.options[teachers.selectedIndex].value;
	 
	if(classes.selectedIndex != -1) {
		destination1.options[0]=new Option("- Loading -","");
		destination2.options[0]=new Option("- Loading -","");
		var year = document.getElementById("academicYear").value;
		var selectedClasses = new Array();
		var count = 0;
		for (var i=0; i<classes.options.length; i++) {
		    if (classes.options[i].selected) {
		    	selectedClasses[count] = classes.options[i].value;
		      count++;
		    }
		 }	
		// getSubjectsPeriodsBatchForClassSendRequest(selectedClasses,year,updateSubjcetBatchPeriod);
		getSubjectsPeriodsBatchForTeacherAndClassSendRequest(selectedClasses,year, selectedTeacherId, updateSubjcetBatchPeriod);
	} 
}

function updateSubjcetBatchPeriod(req) {
	var responseObj = req.responseXML.documentElement;
	var destination1 = document.getElementById("subjectId");
	
	destination1.options[0]=new Option("- Select -","");
	var items1 = responseObj.getElementsByTagName("subject");
	for (var j = 0 ; j < items1.length ; j++) {
        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination1.options[j+1] = new Option(label,value);
	 }

	var destination2 = document.getElementById("periods");
	
	var items2 = responseObj.getElementsByTagName("period");
	if(items2.length == 0) {
		var destination5 = document.getElementById("periods");
		for (x1=destination5.options.length-1; x1>=0; x1--) {
			destination5.options[x1]=null;
		}
	}	
	for (var k = 0 ; k < items2.length ; k++) {
        label = items2[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items2[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination2.options[k] = new Option(label,value);
	 }
}
function submitData() {
	document.getElementById("classes").disabled=false;
	document.getElementById("subjectId").disabled=false;
	document.getElementById("batchId").disabled=false;
	document.getElementById("attendanceTypeId").disabled=false;
	document.getElementById("activityId").disabled=false;
	var obj1= document.getElementById("classes").selectedIndex;
	var obj2= document.getElementById("teachers").selectedIndex;
	var obj3= document.getElementById("periods").selectedIndex;
	document.getElementById("classSelectedIndex").value=obj1;
	document.getElementById("teacherSelectedIndex").value=obj2;
	document.getElementById("periodSelectedIndex").value=obj3;
	document.getElementById("attenType").value = document.getElementById("attendanceTypeId").options[document.getElementById("attendanceTypeId").selectedIndex].text;
	document.getElementById("acaYear").value = document.getElementById("academicYear").options[document.getElementById("academicYear").selectedIndex].text;
	if(document.getElementById("subjectId").selectedIndex != 0) {
		document.getElementById("attendanceSubject").value = document.getElementById("subjectId").options[document.getElementById("subjectId").selectedIndex].text;
	}else {
		document.getElementById("attendanceSubject").value = "";
	}
	//if(document.getElementById("batchId").selectedIndex != 0) {
	//	document.getElementById("attendanceBatchName").value = document.getElementById("batchId").options[document.getElementById("batchId").selectedIndex].text;
	//} else {
	//	document.getElementById("attendanceBatchName").value = "";
	//}
	if(document.getElementById("activityId").selectedIndex != 0) {
		if(document.getElementById("attendanceActivity").value!=""){
		document.getElementById("attendanceActivity").value = document.getElementById("activityId").options[document.getElementById("activityId").selectedIndex].text;
		}
	} else {
		document.getElementById("attendanceActivity").value = "";
		}
	var classesString ="";
	var classes = document.getElementById("classes");
	for (var i=0; i<classes.options.length; i++) {
	    if (classes.options[i].selected) {
	    	classesString = classesString + classes.options[i].text+", ";
	    }
	 }
	var teacherString="";
	var teachers = document.getElementById("teachers");
	for (var j=0; j<teachers.options.length; j++) {
	    if (teachers.options[j].selected) {
	    	teacherString = teacherString + teachers.options[j].text+", ";
	    }
	 }
	var periodString="";
	var periods = document.getElementById("periods");
	for (var k=0; k<periods.options.length; k++) {
	    if (periods.options[k].selected) {
	    	periodString = periodString + periods.options[k].text+", ";
	    }
	 }
	//var batchString ="";
	//var batches = document.getElementById("batchId");
	//alert(batches);
	//alert("bye");
	//for (var i=0; i<batches.options.length; i++) {
	//    if (batches.options[i].selected) {
	//    	batchString = batchString + batches.options[i].text+", ";
	//    }
	// }
	classesString =  classesString.substr(0,(classesString.length - 2));
	teacherString = teacherString.substr(0,(teacherString.length - 2));
	periodString = periodString.substr(0,(periodString.length - 2));
	//batchString=batchString.substr(0,(batchString.length - 2));
	document.getElementById("attendanceClass").value = classesString;
	document.getElementById("attendanceTeacher").value = teacherString;
	document.getElementById("attendancePeriod").value = periodString;
	//document.getElementById("attendanecBatchIds").value = batchString;
	
	document.attendanceEntryForm.submit();
}
function validEnteredNumber(hoursNo) {
	var periods =  document.getElementById("periods");
	var selectedPeriods = new Array();
	count=0;
	for (var i=0; i<periods.options.length; i++) {
	    if (periods.options[i].selected) {
	    	selectedPeriods[count] = periods.options[i].value;
	      count++;
	    }
	 }
	if(hoursNo != count){
		notEqualConfirm = confirm("You have selected "+count+" periods, but hours held is "+hoursNo+". Do you want to continue");
		if(!notEqualConfirm){
			document.getElementById("hoursHeld").value = count;
		}
	}
}

function getBatches(subjectId) {
	var classes =  document.getElementById("classes");
	var selectedClasses = new Array();
	var count = 0;
	for (var i=0; i<classes.options.length; i++) {
	    if (classes.options[i].selected) {
	    	selectedClasses[count] = classes.options[i].value;
	      count++;
	    }
	 }	

	getBatchesBySubject("subjectMap",subjectId,selectedClasses,"batchId",updateBatches);
}

function updateBatches(req) {
	updateOptionsFromMap(req,"batchId","- Select -");
}
function getClassByTeacherAndYear(year){
	document.getElementById("numericCode").value='';
	var teachers =  document.getElementById("teachers").value;
	var destination1 = document.getElementById("subjectId");
	for (x1=destination1.options.length-1; x1>0; x1--) {
		destination1.options[x1]=null;
	}
	var destination2 = document.getElementById("periods");
	for (x1=destination2.options.length-1; x1>=0; x1--) {
		destination2.options[x1]=null;
	}
	var destination3 = document.getElementById("batchId");
	for (x1=destination3.options.length-1; x1>0; x1--) {
		destination3.options[x1]=null;
	}
	destination1.options[0]=new Option("- Select -","");
	destination3.options[0]=new Option("- Select -","");
	if(year!=null && year!='') {
		getClassesByTeacherAndYear("classMap", year, teachers,"classes", updateClasses);
	} 
}
function submitForm(){
	var numericCode=document.getElementById("numericCode").value;
	document.getElementById("method").value = "setDataForNumericCode";
	document.attendanceEntryForm.submit();
}
function getBatchesForClassesActivity(){
	var classes =  document.getElementById("classes");
	var selectedClasses = new Array();
			var count = 0;
			for (var i=0; i<classes.options.length; i++) {
			    if (classes.options[i].selected) {
			    	selectedClasses[count] = classes.options[i].value;
			      count++;
			    }
			 }
	var activity=  document.getElementById("activityId").value;
	if(classes.selectedIndex != -1 && activity!=null && activity!=""){
		getBatchesForActivityAndClass(selectedClasses,activity);
		}
}
function getBatchesForActivityAndClass(selectedClasses,activity){
	getBatchesByActivity("subjectMap",activity,selectedClasses,"batchId",updateBatches);
}


//Down Three Methods added by Balaji For the Change of Practical Batch (Class Schemewise Id instead of batch to BatchStudent)
function getBatches1(subjectId) {
	var classes =  document.getElementById("classes");
	var selectedClasses = new Array();
	var count = 0;
	for (var i=0; i<classes.options.length; i++) {
	    if (classes.options[i].selected) {
	    	selectedClasses[count] = classes.options[i].value;
	      count++;
	    }
	 }	

	getBatchesBySubject1("subjectMap",subjectId,selectedClasses,"batchId",updateBatches);
}
function getBatchesForClassesActivity1(){
	
	if(document.getElementById("subjectId").selectedIndex != 0){
		if(document.getElementById("batchId").options.length <= 1){
			
			var classes =  document.getElementById("classes");
			var selectedClasses = new Array();
			var count = 0;
			for (var i=0; i<classes.options.length; i++) {
			    if (classes.options[i].selected) {
			    	selectedClasses[count] = classes.options[i].value;
			      count++;
			    }
			 }
	var activity=  document.getElementById("activityId").value;
	if(classes.selectedIndex != -1 && activity!=null && activity!=""){
		getBatchesForActivityAndClass1(selectedClasses,activity);
		}
	}
	}else {
		var classes =  document.getElementById("classes");
		var selectedClasses = new Array();
		var count = 0;
		for (var i=0; i<classes.options.length; i++) {
		    if (classes.options[i].selected) {
		    	selectedClasses[count] = classes.options[i].value;
		      count++;
		    }
		 }
		var activity=  document.getElementById("activityId").value;
		if(classes.selectedIndex != -1 && activity!=null && activity!=""){
			getBatchesForActivityAndClass1(selectedClasses,activity);
		}
	}
	
}
function getBatchesForActivityAndClass1(selectedClasses,activity){
	getBatchesByActivity1("subjectMap",activity,selectedClasses,"batchId",updateBatches);
}

function searchDeptWise(deptId){
	getTearchersByDepartmentWise(deptId,updateTeachersMap);
}
function updateTeachersMap(req){
	updateTeachersFromMap(req,"teachers");
}

//New Modifications Made Accourding to timeTable
function getPeriodsForTeacher(){
	var date=document.getElementById("attendancedate").value;
	if(date==null || date==''){
		alert("Date Is Required");
	}else{
     //added by mahi
       var destination3 = document.getElementById("batchId");
		for (x1=destination3.options.length-1; x1>=0; x1--) {
			destination3.options[x1]=null;
		}
		//end
		var teachers =  document.getElementById("teachers");
		var selectedTeachers = new Array();
		var count=0;
		for (var i=0; i<teachers.options.length; i++) {
		    if (teachers.options[i].selected) {
		    	selectedTeachers[count] = teachers.options[i].value;
		      count++;
		    }
		 }
		if(selectedTeachers.length!=0){ 
			var destination1 = document.getElementById("subjectId");
			for (x1=destination1.options.length-1; x1>0; x1--) {
				destination1.options[x1]=null;
			}
				var destination2 = document.getElementById("classes");
			for (x1=destination2.options.length-1; x1>=0; x1--) {
				destination2.options[x1]=null;
			}
			var destination3 = document.getElementById("periods");
			for (x1=destination3.options.length-1; x1>=0; x1--) {
				destination3.options[x1]=null;
			}
			if(teachers.selectedIndex != -1) {
				getPeriodByTeacherInMuliSelect("periodMap", selectedTeachers, "periods",date, updatePeriods);
			} 
		}
	}
}
function updatePeriods(req) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById("periods");
	var items1 = responseObj.getElementsByTagName("option");
	for (var j = 0 ; j < items1.length ; j++) {
        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination.options[j] = new Option(label,value);
	 }
}
function getClassesAndSubjectsForPeriod(){
	var destination1 = document.getElementById("subjectId");
	for (x1=destination1.options.length-1; x1>0; x1--) {
		destination1.options[x1]=null;
	}
		var destination2 = document.getElementById("classes");
	for (x1=destination2.options.length-1; x1>=0; x1--) {
		destination2.options[x1]=null;
	}
	var destination3 = document.getElementById("activityId");
	for (x1=destination3.options.length-1; x1>=0; x1--) {
		destination3.options[x1]=null;
	}
	var destination4 = document.getElementById("batchId");
	for (x1=destination4.options.length-1; x1>=0; x1--) {
		destination4.options[x1]=null;
	}
	
	var periods =  document.getElementById("periods");
	
	if(periods.selectedIndex != -1) {
		destination1.options[0]=new Option("- Loading -","");
		destination2.options[0]=new Option("- Loading -","");
		var year = document.getElementById("academicYear").value;
		var selectedPeriods = new Array();
		var count=0;
		for (var i=0; i<periods.options.length; i++) {
		    if (periods.options[i].selected) {
		    	selectedPeriods[count] = periods.options[i].value;
		      count++;
		    }
		 }	
		var teachers =  document.getElementById("teachers");
		var selectedTeachers = new Array();
		count=0;
		for (var i=0; i<teachers.options.length; i++) {
		    if (teachers.options[i].selected) {
		    	selectedTeachers[count] = teachers.options[i].value;
		      count++;
		    }
		 }
		var date=document.getElementById("attendancedate").value;
		getSubjectsAndClassForPeriods(selectedPeriods,year,selectedTeachers,date,updateClassesAndSubjects);
	} 
}

function updateClassesAndSubjects(req) {
	var responseObj = req.responseXML.documentElement;
	var destination1 = document.getElementById("subjectId");
	
	destination1.options[0]=new Option("- Select -","");
	var itemsForError = responseObj.getElementsByTagName("ErrorOccured");
	if(itemsForError.length == 0) {
	var items1 = responseObj.getElementsByTagName("subject");
	for (var j = 0 ; j < items1.length ; j++) {
        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination1.options[j+1] = new Option(label,value);
	 }

	var destination2 = document.getElementById("classes");
	
	var items2 = responseObj.getElementsByTagName("period");
	if(items2.length == 0) {
		var destination5 = document.getElementById("classes");
		for (x1=destination5.options.length-1; x1>=0; x1--) {
			destination5.options[x1]=null;
		}
	}	
	for (var k = 0 ; k < items2.length ; k++) {
        label = items2[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items2[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination2.options[k] = new Option(label,value);
	 }

	var items3 = responseObj.getElementsByTagName("classId");
	for ( var I = 0; I < items3.length; I++) {
		if(items3[I].firstChild!=null){
			var temp = items3[I].firstChild.nodeValue;
			document.getElementById("classes").value=temp;
			if(document.getElementById("classes").value != null && document.getElementById("classes").value != '') {
				document.getElementById("classesHidden").value = document.getElementById("classes").value;
			} else {
				document.getElementById("classesHidden").value=temp;
			}			
			var temp = items3[I].firstChild.nodeValue;
			var split = temp.split(',');
			for ( var I = 0; I < split.length; I++) {
				var select = document.getElementById('classes');
				for ( var i = 0; i < select.options.length; i++ ) {
			  		o = select.options[i];
			  		if( split[I]==o.value) {
			    		o.selected = true;
			  		}
				}
			}
		}
	}
	if(responseObj.getElementsByTagName("subjectId")!=''){
	var items4 = responseObj.getElementsByTagName("subjectId");
	for ( var I = 0; I < items4.length; I++) {
		if(items4[I].firstChild!='' && items4[I].firstChild!=null){
		var temp = items4[I].firstChild.nodeValue;
		document.getElementById("subjectId").value=temp;
		document.getElementById("subjectIdHidden").value=temp;
		}
	}
	}
	
	var items5 = responseObj.getElementsByTagName("attendanceTypeId");
	for ( var I = 0; I < items5.length; I++) {
		if(items5[I].firstChild!=null && items5[I].firstChild!==''){
		var temp = items5[I].firstChild.nodeValue;
		document.getElementById("attendanceTypeId").value=temp;
		}
	}
	document.getElementById("batchId").options[0]=new Option("- Select -","");
	var items6 = responseObj.getElementsByTagName("batchId");
	for ( var I = 0; I < items6.length; I++) {
		if(items6[I].firstChild !=null && items6[I].firstChild!== ''){
			
		var temp = items6[I].firstChild.nodeValue;
		if(temp!=0){
			var destination2 = document.getElementById("batchId");
			var items7 = responseObj.getElementsByTagName("batch");
			for (var k = 0 ; k < items7.length ; k++) {
		        label = items7[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			     value = items7[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			     destination2.options[k] = new Option(label,value);
			 }
			document.getElementById("batchId").value=temp;
        //added by mehaboob start
			var split1 = temp.split(',');
			for ( var I = 0; I < split1.length; I++) {
				var select = document.getElementById('batchId');
				for ( var i = 0; i < select.options.length; i++ )
				{
				  o1 = select.options[i];
				  if ( split1[I]==o1.value)
				  {
				    o1.selected = true;
				  }
				}
			}
			//end
		}
		}
	}
	var items8 = responseObj.getElementsByTagName("activityId");
	document.getElementById("activityId").options[0]=new Option("- Select -","");
	for ( var I = 0; I < items8.length; I++) {
		if(items8[I].firstChild!='' && items8[I].firstChild!=null){
		var temp = items8[I].firstChild.nodeValue;
		if(temp!=0){
			var destination2 = document.getElementById("activityId");
			
			var items9 = responseObj.getElementsByTagName("activity");
			for (var k = 0 ; k < items9.length ; k++) {
		        label = items9[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			     value = items9[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			     destination2.options[k] = new Option(label,value);
			 }
			
			document.getElementById("activityId").value=temp;
		}
	}
	}
	getMandatoryForTimeTable();
   }else{
	   $('#errorMessage').slideDown().html("<span>Time Table Setting Error.</span>");
	}
}

function changeJavaScript(timeTable,clear){
	if(clear==true){
		var destination1 = document.getElementById("subjectId");
		for (x1=destination1.options.length-1; x1>0; x1--) {
			destination1.options[x1]=null;
		}
			var destination2 = document.getElementById("classes");
		for (x1=destination2.options.length-1; x1>=0; x1--) {
			destination2.options[x1]=null;
		}
		
		var periods =  document.getElementById("periods");
		for (x1=periods.options.length-1; x1>=0; x1--) {
			periods.options[x1]=null;
		}
		//var teachers =  document.getElementById("teachers");
		//for (x1=teachers.options.length-1; x1>=0; x1--) {
			//teachers.options[x1].selected=false;
		//}
		document.getElementById("subjectId").value="";
		//document.getElementById("batchId").value="";
		var destination3 = document.getElementById("batchId");
		for (x1=destination3.options.length-1; x1>=0; x1--) {
			destination3.options[x1]=null;
		}
		document.getElementById("attendanceTypeId").value="";
		document.getElementById("activityId").value="";
	}	
		
	if(timeTable=='no'){
		document.getElementById("teachers").removeEventListener('change',getPeriodsForTeacher,false);
		document.getElementById("attendancedate").removeEventListener('change',getPeriodsForTeacher,false);
		document.getElementById("teachers").addEventListener('change',getClassForTeacher,false);
		document.getElementById("classes").addEventListener('change',getSubjectsPeriodsBatchForTeacherAndClass,false);
		document.getElementById("periods").removeEventListener('change',getClassesAndSubjectsForPeriod,false);
		document.getElementById("periods").addEventListener('change',getPeriodsCount,false);
		$("#classDisplayId").after($("#periodDisplayId"));
		document.getElementById("classes").disabled=false;
		document.getElementById("subjectId").disabled=false;
		document.getElementById("batchId").disabled=false;
		document.getElementById("attendanceTypeId").disabled=false;
		document.getElementById("activityId").disabled=false;
		
		//raghu
		getClassForTeacher();
		//document.getElementById('attendanceTypeId1').style.display = "block";
		//document.getElementById('attendanceTypeId2').style.display = "block";
		//document.getElementById('hoursHeld1').style.display = "block";
		//document.getElementById('hoursHeld2').style.display = "block";
		
	}else{
		document.getElementById("classes").removeEventListener('change',getSubjectsPeriodsBatchForTeacherAndClass,false);
		document.getElementById("teachers").removeEventListener('change',getClassForTeacher,false);
		document.getElementById("teachers").addEventListener('change',getPeriodsForTeacher,false);
		document.getElementById("periods").addEventListener('change',getClassesAndSubjectsForPeriod,false);
		document.getElementById("attendancedate").addEventListener('change',getPeriodsForTeacher,false);
		$("#periodDisplayId").after($("#classDisplayId"));
		document.getElementById("classes").disabled=true;
		document.getElementById("subjectId").disabled=true;
		document.getElementById("batchId").disabled=true;
		document.getElementById("attendanceTypeId").disabled=true;
		document.getElementById("activityId").disabled=true;
		
		//raghu
		getPeriodsForTeacher();
		//document.getElementById('attendanceTypeId1').style.display = "none";
		//document.getElementById('attendanceTypeId2').style.display = "none";
		//document.getElementById('hoursHeld1').style.display = "none";
		//document.getElementById('hoursHeld2').style.display = "none";
	}
}

function getMandatoryForTimeTable() {
	var value=document.getElementById("attendanceTypeId").value;
	if(value.length == 0) {
		document.getElementById("classMandatry").value = "no"; 
		document.getElementById("subjectMandatory").value ="no"; 
		document.getElementById("periodMandatory").value = "no"; 
		document.getElementById("teacherMandatory").value = "no"; 
		document.getElementById("batchMandatory").value = "no"; 
		document.getElementById("activityTypeMandatory").value = "no"; 
		document.getElementById("classsdiv").innerHTML = "Class:";
		document.getElementById("subjectdiv").innerHTML = "Subject:";
		document.getElementById("perioddiv").innerHTML = "Hour:";
		document.getElementById("teacherdiv").innerHTML = "Teacher:";
		document.getElementById("batchdiv").innerHTML = "Batch Name:";
		document.getElementById("activitydiv").innerHTML = "Activity Type:";
	} else {	
	getMandatoryFieldsByAttendanceType("activityMap",value,updateMandatoryForTimeTable);
	}
}

function updateMandatoryForTimeTable(req) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("fields");
	for (var i = 0 ; i < items.length ; i++) {
         var label = items[i].getElementsByTagName("field")[0].firstChild.nodeValue;
	     var value = items[i].getElementsByTagName("mandatory")[0].firstChild.nodeValue;
	     if(label == "Class") {
	    	 document.getElementById("classMandatry").value = value; 
			if(value == "yes") {
				document.getElementById("classsdiv").innerHTML = "<span class='MandatoryMark'>*</span>Class:";	
			} else {
				document.getElementById("classsdiv").innerHTML = "Class:";
			}		
	     } else if(label == "Subject") {
	    	 document.getElementById("subjectMandatory").value = value;
	    	 if(value == 'yes') {
					document.getElementById("subjectdiv").innerHTML = "<span class='MandatoryMark'>*</span>Subject:";	
				} else {
					document.getElementById("subjectdiv").innerHTML = "Subject:";
				}
	     } else if(label == "Hour") {
	    	 document.getElementById("periodMandatory").value = value;
	    	 if(value == "yes") {
					document.getElementById("perioddiv").innerHTML = "<span class='MandatoryMark'>*</span>Hour:";	
				} else {
					document.getElementById("perioddiv").innerHTML = "Hour:";
				}
	     } else if(label == "Teacher") {
	    	 document.getElementById("teacherMandatory").value = value;
	    	 if(value == "yes") {
					//document.getElementById("teacherdiv").innerHTML = "<span class='MandatoryMark'>*</span>Teacher:";	
				} else {
					//document.getElementById("teacherdiv").innerHTML = "Teacher:";
				}
	     } else if(label == "Batch name") {
	    	 document.getElementById("batchMandatory").value = value;
	    	 if(value == "yes") {
					document.getElementById("batchdiv").innerHTML = "<span class='MandatoryMark'>*</span>Batch Name:";	
				} else {
					document.getElementById("batchdiv").innerHTML = "Batch Name:";
				}
	     } else if(label == "Activity Type") {
	    	 document.getElementById("activityTypeMandatory").value = value;
	    	 if(value == "yes") {
					document.getElementById("activitydiv").innerHTML = "<span class='MandatoryMark'>*</span>Activity Type:";	
				} else {
					document.getElementById("activitydiv").innerHTML = "Activity Type:";
				}
	     }       
	 }
	 
}
function getPeriodsCount(){
	
	var periods =  document.getElementById("periods");
	var selectedPeriods = new Array();
	count=0;
	for (var i=0; i<periods.options.length; i++) {
	    if (periods.options[i].selected) {
	    	selectedPeriods[count] = periods.options[i].value;
	      count++;
	    }
	 }
	document.getElementById("hoursHeld").value = count;
}
function continueToSecondPage(){
	var continueToSecondPage="yes";
	document.location.href = "AttendanceEntry.do?method=initAttendanceEntrySecondPage&continueToSecondPage="+continueToSecondPage;
}
function continueToFirstPage(){
	document.location.href="AttendanceEntry.do?method=loadAttendanceEntryFirstPage";
}


function getAttendanceTypeid(subjectId) {
	

	getAttendanceTypeIdBySubject(subjectId,updateAttendanceTypeid);
}

function updateAttendanceTypeid(req) {
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("option");
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
			if(value[I].firstChild!=null){
			var temp = value[I].firstChild.nodeValue;
			document.getElementById("attendanceTypeId").value = temp;
			//alert(temp);
			}
		}
		
	}
	
}

</script>
<html:form action="/AttendanceEntry" method="post">

	<html:hidden property="formName" value="attendanceEntryForm" />
	<html:hidden property="method" styleId="method" value="initAttendanceEntrySecondPage"/>
	<html:hidden property="pageType" value="1"/>
	<html:hidden  property="classMandatry" styleId="classMandatry" name="attendanceEntryForm"/>
	<html:hidden  property="subjectMandatory" styleId="subjectMandatory" name="attendanceEntryForm" />
	<html:hidden  property="periodMandatory" styleId="periodMandatory" name="attendanceEntryForm" />
	<html:hidden  property="teacherMandatory" styleId="teacherMandatory" name="attendanceEntryForm" />
	<html:hidden  property="batchMandatory" styleId="batchMandatory" name="attendanceEntryForm" />
	<html:hidden  property="activityTypeMandatory" styleId="activityTypeMandatory" name="attendanceEntryForm" />
	<html:hidden property="classSelectedIndex" styleId="classSelectedIndex"/>
	<html:hidden property="teacherSelectedIndex" styleId="teacherSelectedIndex"/>
	<html:hidden property="periodSelectedIndex" styleId="periodSelectedIndex"/>
	
	<html:hidden property="attenType" styleId="attenType"/>
	<html:hidden property="acaYear" styleId="acaYear"/>
	<html:hidden property="attendanceClass" styleId="attendanceClass"/>
	<html:hidden property="attendanceSubject" styleId="attendanceSubject"/>
	<html:hidden property="attendanceTeacher" styleId="attendanceTeacher"/>
	<html:hidden property="attendancePeriod" styleId="attendancePeriod"/>
	<html:hidden property="attendanceBatchName" styleId="attendanceBatchName"/>
	<html:hidden property="attendanceActivity" styleId="attendanceActivity"/>
	<input  type="hidden" name="ttFormat" id="ttFormat" value='<bean:write name="attendanceEntryForm" property="timeTableFormat"/>'></input> 
	<html:hidden property="year" styleId="year"/>
	<html:hidden property="classesHidden" styleId="classesHidden"/>
	<html:hidden property="subjectIdHidden" styleId="subjectIdHidden"/>
	<head>

	 <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap DatePicker</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script><!--
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    --><script src="https://unpkg.com/gijgo@1.9.11/js/gijgo.min.js" type="text/javascript"></script>
    <link href="https://unpkg.com/gijgo@1.9.11/css/gijgo.min.css" rel="stylesheet" type="text/css" />
	<style>
	img.tcalIcon {
    cursor: pointer;
    margin-left: 160px;
	margin-top: -40;
	</style>
	
	</head>
	
	  <div class="panel-body">
                        <div class="panel panel-primary">
                         <div class="panel-heading">
                         <h1 class="panel-title">Attendance Entry</h1>
                         
                         </div>
     
                         </div>
	<div class="col-sm-12"  style="border-style: solid;border-color: #29953b;">
        <div class="form-group">
                 <div class="col-sm-12">
	       <div align="right" style="color:red;" class="heading"> <span class='MandatoryMark'><b><bean:message key="knowledgepro.mandatoryfields"/></b></span></div>
	       <font color="red" size="4"><div id="errorMessage" style="font-family: verdana;font-size: 10px">
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
	  	   </div></font>
        
       </div>
       </div>
     
        <div class="form-group">
                 <div class="col-sm-12">
                   <div class="col-sm-12 text-center">
                  	
                  		<div id="teacherdiv" align="center">
                  		</div>
                  		
                 <span style="color:blue;"> <b><bean:message key="knowledgepro.attendanceentry.teacher"/></b>:</span>
                  		
                  		
                  	 <div style="display: none;">
                 		<html:select name="attendanceEntryForm" styleId="teachers" property="teachers"  multiple="multiple"  styleClass="form-control" disabled="true" >
                    	<html:optionsCollection name="attendanceEntryForm" property="teachersMap" label="value" value="key"/>
                  		</html:select>
                  	</div>
                  <font style="font-size: 12px">	<b><bean:write name="attendanceEntryForm" property="empName"/></b></font>
                  	<html:hidden property="userId" styleId="userId"/>
                  	
              	<script type="text/javascript">
				
				document.getElementById("teachers").value=document.getElementById("userId").value;
				
				</script>
               	</div>
                  	</div>
                 </div>	 	
              
                  
                <div class="form-group">
                 <div class="col-sm-12">
                 
                 <div class="col-sm-2">
                 <span class='MandatoryMark'style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.date"/></b>:</div>
                  <div class="col-sm-2"><html:text name="attendanceEntryForm" size="15" styleId="attendancedate" property="attendancedate" styleClass="form-control" style="width: 156px;"/>
                 <script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'attendanceEntryForm',
													// input name
													'controlname' :'attendancedate'
												});
											</script>
                
 
                  </div>
                <div class="col-sm-2"><span class="Mandatory" style="color:red;">*</span><b><bean:message key="knowledgepro.fee.academicyear"/></b>:</div>
		       <div class="col-sm-2">
		                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="attendanceEntryForm" property="year"/>"/>
		                           <div style="display: none;">
		                           <html:select property="year" styleId="academicYear" name="attendanceEntryForm" styleClass="form-control" onchange="getClassByTeacherAndYear(this.value)">
                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                       	   				<cms:renderAcademicYear></cms:renderAcademicYear>
                       			   </html:select>
                       			   </div>
                       			   <bean:write name="attendanceEntryForm" property="year"/>
		        	 </div>
		        	 <div class="col-sm-2"><font style="font-size: 11px"><b>Pick From Time Table:</b></font></div>
					 <div class="col-sm-2"><html:radio property="timeTableFormat" value="yes" onclick="changeJavaScript(this.value,true);getPeriodsForTeacher();"> Yes</html:radio> 
					<html:radio property="timeTableFormat" value="no" onclick="changeJavaScript(this.value,true)"> No</html:radio>
					 </div>
                </div>
               
              </div>
                 <div class="form-group">
                 <div class="col-sm-12">
                 
                  <div id="classDisplayId" >
                  <div class="col-sm-2">
			                  <c:choose>
			                  	<c:when test="${attendanceEntryForm.classMandatry == 'yes'}">
			                 		 <div id="classsdiv" ><span class='MandatoryMark'style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.class"/></b>:</div>
			                 	 </c:when>
			                 	 <c:otherwise>
			                 	 	 <div id="classsdiv" ><b><bean:message key="knowledgepro.attendanceentry.class"/></b>:</div>
			                 	 </c:otherwise>
			                  </c:choose>
			                  </div>
			                <div class="col-sm-2">
			                  <html:select  name="attendanceEntryForm" styleId="classes" property="classes" size="5" multiple="multiple" styleClass="form-control">
			       		    		<html:optionsCollection name="attendanceEntryForm" property="classMap" label="value" value="key"/>
			                  </html:select>
			                 </div>		
                  		</div>
                 
                  <div id="periodDisplayId" >
                    <div class="col-sm-2">
		                  <c:choose>
		                  	<c:when test="${attendanceEntryForm.periodMandatory == 'yes'}">
		                  		<div id="perioddiv"><span class='MandatoryMark'style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.period"/></b>:</div>
		                  	</c:when>
		                  	<c:otherwise>
		                  	    <div id="perioddiv"><b><bean:message key="knowledgepro.attendanceentry.period"/></b>:</div>
		                  	</c:otherwise>
		                  </c:choose>
		                	</div>
		                   <div class="col-sm-2">
		                   <html:select name="attendanceEntryForm" styleId="periods" property="periods" size="5"  multiple="multiple" styleClass="form-control">
		                   <html:optionsCollection name="attendanceEntryForm" property="periodMap" label="value" value="key"/>
		                  </html:select>
		                	</div>
                  	</div>
                  
                   <div class="col-sm-2">
                  <c:choose>
                  	<c:when test="${attendanceEntryForm.subjectMandatory == 'yes'}">
                  		<div id="subjectdiv" ><span class='MandatoryMark'style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.subject"/></b>:</div>
                    </c:when>
                    <c:otherwise>
                    	<div id="subjectdiv" ><b><bean:message key="knowledgepro.attendanceentry.subject"/></b>:</div>
                    </c:otherwise>
                  </c:choose>
              	</div>
                	 <div class="col-sm-2"><label>
                    <html:select property="subjectId" styleId="subjectId" styleClass="form-control" onchange="getBatches1(this.value);getAttendanceTypeid(this.value);">
                      <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                      <html:optionsCollection name="attendanceEntryForm" property="subjectMap" label="value" value="key"/>
                    </html:select>
                  </label>	</div>
                   <div style="display: none;"><div  id="hoursHeld1" ><span class='MandatoryMark' style="color:red;">*</span><bean:message key="knowledgepro.attendanceentry.hoursheld"/>:</div></div>
                  <div style="display: none;"><div id="hoursHeld2" >
                 <html:text name="attendanceEntryForm" styleId="hoursHeld" property="hoursHeld" styleClass="form-control" onkeypress="return isNumberKey(event)" maxlength="4" onblur="validEnteredNumber(this.value)" />
                 </div> </div>
                 
                <div class="col-sm-2">
                   <c:choose>
                  	<c:when test="${attendanceEntryForm.batchMandatory == 'yes'}">
                  		<div id="batchdiv" style="opacity: 0;">
                  		<%-- <span class='MandatoryMark' style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.batchname"/></b>: --%>
                  		
                  		</div>
                    </c:when>
                    <c:otherwise>
                    	<div id="batchdiv" style="opacity: 0;">
                    	<%-- <b><bean:message key="knowledgepro.attendanceentry.batchname"/></b>: --%>
                    	</div>
                    </c:otherwise>
                   </c:choose>
                  </div>
                   <div class="col-sm-2">
                  <div >
                   <html:select name="attendanceEntryForm" styleId="batchId" property="batchIdsArray" style="opacity: 0;">
                    <html:optionsCollection name="attendanceEntryForm" property="batchMap" label="value" value="key"/>
                    
                  </html:select>
                  </div>
                </div>
               
                <div style="display: none">
                
                <div style="display: none">
                  <c:choose>
                  	<c:when test="${attendanceEntryForm.activityTypeMandatory == 'yes'}">
                  		<div id="activitydiv" ><span class='MandatoryMark' style="color:red;">*</span><b><bean:message key="knowledgepro.attendanceentry.activitytype"/></b>:</div>
                    </c:when>
                    <c:otherwise>
                    	<div id="activitydiv" ><b><bean:message key="knowledgepro.attendanceentry.activitytype"/></b>:</div>
                    </c:otherwise>
                   </c:choose>
                </div>
                  <div style="display: none">
                  <html:select  name="attendanceEntryForm" property="activityId" styleId="activityId" styleClass="form-control" onchange="getBatchesForClassesActivity1()">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                    		<html:optionsCollection name="attendanceEntryForm" property="activityMap" label="value" value="key"/>
                    </html:select> </div>
                 
                <div class="col-sm-2">
                  <div  id="attendanceTypeId1" style="display: none;"><span class='MandatoryMark' style="color:red;">*</span><bean:message key="knowledgepro.attendanceentry.type"/>:</div>
                  </div>
                  <div class="col-sm-2"><div id="attendanceTypeId2" style="display: none;">
                  <html:select property="attendanceTypeId" styleId="attendanceTypeId" name="attendanceEntryForm" styleClass="combo" onchange="getMandatory(this.value)">
                  	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                  	<html:option value="3">Theory</html:option>
                  	<html:option value="2">Practical</html:option>
                  </html:select></div>
                 </div>
                  <logic:equal name="attendanceEntryForm" property="displayRequired" value="true">
                     <div class="col-sm-2">Is SMS Required
                  </div>
                    <div class="col-sm-2"> <html:radio property="isSMSRequired" value="Yes"></html:radio>Yes &nbsp; <html:radio property="isSMSRequired" value="No"></html:radio>No
                  </div>
                  </logic:equal>                 
                </div>
    </div>
    </div> 	
    <div class="form-group">
                 <div class="col-sm-11">               
                  <div class="col-sm-2" >
                    	<div align="left"><b><bean:message key="knowledgepro.attendanceentry.duty"/>:</b></div>
                  </div>
                    <div class="col-sm-2" >
                  <html:select name="attendanceEntryForm" style="padding: 5px;margin-left: 20px;" property="dutyType" styleId="duty" styleClass="comboMediumLarge" onchange="checkOtherDuty(this.value);">
                   <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                    <html:option value="Work from home">Work from home</html:option>
                    <html:option value="Work from college">Work from college</html:option>
                    <html:option value="other">Other</html:option>
                    </html:select>
                    <html:text  name="attendanceEntryForm" property="dutyTypeOther" styleId="dutyOther" style="display:none" styleClass="form-control"/>
                    </div>
                    
                 
                   
                    <div class="col-sm-2" >
                    	<div ><b><bean:message key="knowledgepro.attendanceentry.platform"/>:</b></div>
                  </div>
                    <div class="col-sm-2" >
                  <html:select name="attendanceEntryForm" style="padding: 5px;" property="platform" styleId="platform" styleClass="comboMediumLarge" onchange="checkOtherPLatform(this.value);">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                    <html:option value="Google Meet">Google Meet</html:option>
                    <html:option value="Google Classroom">Google Classroom</html:option>
                    <html:option value="Moddle">Moddle</html:option>
                    <html:option value="Zoom">Zoom</html:option>
                    <html:option value="other">Other</html:option>
                    </html:select>
                     <html:text  name="attendanceEntryForm" property="platformOther" styleId="platformOther" style="display:none" styleClass="form-control"/>
                    </div>
                    
                    <div class="col-sm-2" ><font style="font-size: 13px"><b><bean:message key="knowledgepro.attendanceentry.class.type"/>:</b></font></div>
					<div class="col-sm-2"><html:radio styleClass="rdio rdio-primary" property="classType" value="String"> Live</html:radio> 
					<html:radio styleClass="rdio rdio-primary" property="classType" value="Recorded"> Recorded</html:radio>
					 </div>
               
                   </div>
               </div>
         
         	<div class="form-group">
         	<div class="col-sm-4 text-center">
         	<div><b><bean:message key="knowledgepro.attendanceentry.topic.taught"/>:</b></div>
            </div>
            <div class="col-sm-8 text-center">
            <html:textarea  name="attendanceEntryForm" property="topic" styleClass="form-control" rows="2" style="width:400px"/>
            </div>
          </div>	
            <div class="form-group">
            <div class="col-sm-12 text-center">
           		<html:button value="  Next  >>  " styleClass="btn btn-primary" property="" onclick="submitData()"></html:button>
        </div>
         </div>
    
     </div>
       </div>

</html:form>

<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("academicYear").value = year;
	}
	
	
	document.getElementById("academicYear").value=document.getElementById("year").value;
	
	var tt=document.getElementById("ttFormat").value;
	if(tt!=null && tt!=''){
		changeJavaScript(tt,false);
	}
</script>