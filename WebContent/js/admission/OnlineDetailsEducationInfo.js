
$(document).ready(function(){
	
	 var focusValueId=document.getElementById("focusValue").value;
	 
	 if(focusValueId!=null && focusValueId!=""){
		 $("#"+focusValueId).focus();
	 }
	 
});



function AutoSave()
{
	  document.getElementById("method").value="AutoSaveApplicantCreation";
	  document.onlineApplicationForm.submit();
}

function submitSaveDraft(focusValueId)
{
	  document.getElementById("focusValue").value=focusValueId;
	  document.getElementById("method").value="AutoSaveApplicantCreation";
	  document.onlineApplicationForm.submit();
}


//raghu
function downloadFile(documentId) {
	document.location.href = "DocumentDownloadAction.do?documentId="+ documentId;
}

function submitAddMorePreferences(method){
document.getElementById("method").value=method;
document.onlineApplicationForm.submit();
}

function addCourseId(id,preNo){
	if(preNo==1){
		document.getElementById("courseId").value=id;
	}
	
}


function detailSubmitClass12(count)
{
	document.getElementById("countID").value=count;
	document.onlineApplicationForm.method.value="initDetailMarkConfirmPageClass12";
	document.onlineApplicationForm.submit();
}

function detailSubmitDegree(count)
{
	document.getElementById("countID").value=count;
	document.onlineApplicationForm.method.value="initDetailMarkConfirmPageDegree";
	document.onlineApplicationForm.submit();
}

function detailSubmitClass12View(count)
{
	document.getElementById("countID").value=count;
	document.onlineApplicationForm.method.value="initDetailMarkConfirmPageClass12View";
	document.onlineApplicationForm.submit();
}

function detailSubmitDegreeView(count)
{
	document.getElementById("countID").value=count;
	document.onlineApplicationForm.method.value="initDetailMarkConfirmPageDegreeView";
	document.onlineApplicationForm.submit();
}



function resetEducationalForm() {

	//Educational info
	
	var ednQualificationListSize=  $('#ednQualificationListSize').val();
	if( parseInt(ednQualificationListSize) > 0){
		
		for(var i=0;i<parseInt(ednQualificationListSize);i++){
			
			var isExamConfigured=  $('#isExamConfigured_'+i).val();
			var consolidated=  $('#consolidated_'+i).val();
			var lastExam=$('#lastExam_'+i).val();
			
			$('#UniversitySelect'+i).val("");
			$('#University'+i).val("");
						
			$('#'+i+'Institute').val("");
			$('#Institute'+i).val("");
				
			if(isExamConfigured!='' && isExamConfigured == 'true'){
				$('#Exam'+i).val("");
					
			}	
				
				$('#State'+i).val("");
				$('#YOP'+i).val("");
				$('#Month'+i).val("");
				$('#Attempt'+i).val("");
				
			//if(lastExam !='' && lastExam=='true'){
				$('#previousRegNo'+i).val("");
			//}
				
			//if(consolidated !='' && consolidated=='true'){
				$('#marksObtained_'+i).val("");
				$('#maxMarks_'+i).val("");
				$('#percentage_'+i).val("");
			//}
					
			
		}
		
	}//errors check over

	
	document.getElementById('backLogs1').checked="false";
	document.getElementById('isSaypass1').checked="false";
	
	$('#secondLanguage').val("");
	$('#ugcourse').val("");
	
	$('#entranceId').val("");
	$('#totalMarks').val("");
	$('#marksObtained').val("");
	$('#entranceRollNo').val("");
	$('#monthPassing').val("");
	$('#entranceyear').val("");
	
	
	//document.getElementById("dioces_description").style.display = "none";
	
}


var globalID;
var count;
var dynarow2id;



function getMarkEntryAvailable(university,count) {
	
}

	
	
	//newly added
	
	function detailSemesterSubmit(count,isClass10or12)
	{
		document.getElementById("countID").value=count;
		document.getElementById("isClass10or12").value=isClass10or12;
		document.onlineApplicationForm.method.value="initSemesterMarkConfirmPage";
		document.onlineApplicationForm.submit();
	}
	
	
	
	
	
	
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

	    function keyup(e){
	        // Key up Ctrl
	        if ((e.which || e.keyCode) == 17) 
	            ctrlKeyDown = false;
	    };
	    
	   

	    //errors check start here
		$('#SubmitEducationDetailPage').click(function(e){
			
			
			var mandatoryFlag = false;
				
				//get values using ids and checking mondatory fields
				var ednQualificationListSize=  $('#ednQualificationListSize').val();
				if( parseInt(ednQualificationListSize) > 0){
					
					for(var i=0;i<parseInt(ednQualificationListSize);i++){
						
						var universitySelect=  $('#UniversitySelect'+i).val();
						if(universitySelect==''){
							$('#UniversitySelect'+i).css({ "border":"2px solid red"  });
							mandatoryFlag = true;
						}else{
								if(universitySelect!='' && universitySelect == 'Other'){

									var universitySelect=  $('#University'+i).val();
									if(universitySelect !='' ){
										$('#University'+i).css('border', '0');
									}else{
										$('#University'+i).css({ "border":"2px solid red"  });
										mandatoryFlag = true;
									}
									
								}
							
							$('#UniversitySelect'+i).css('border', '0');
						}


						/*
						var Institute=  $('#'+i+'Institute').val();
						if(Institute==''){
							$('#'+i+'Institute').css({ "border":"2px solid red"  });
							mandatoryFlag = true;
						}else{
								if(Institute!='' && Institute == 'Other'){

									var instituteOtherName=  $('#Institute'+i).val();
									if(instituteOtherName !='' ){
										$('#Institute'+i).css('border', '0');
									}else{
										$('#Institute'+i).css({ "border":"2px solid red"  });
										mandatoryFlag = true;
									}
									
								}
							
							$('#'+i+'Institute').css('border', '0');
						}	
						
					*/	
							
							var instituteOtherName=  $('#Institute1'+i).val();
							if(instituteOtherName !='' ){
								$('#Institute1'+i).css('border', '0');
							}else{
								$('#Institute1'+i).css({ "border":"2px solid red"  });
								mandatoryFlag = true;
							}
				
			
						var isExamConfigured=  $('#isExamConfigured_'+i).val();
		
							if(isExamConfigured!='' && isExamConfigured == 'true'){
								var exam=  $('#Exam'+i).val();
								if(exam ==''){
									$('#Exam'+i).css({ "border":"2px solid red"  });
									mandatoryFlag = true;
								}else{
									$('#Exam'+i).css('border', '0');
								}
							}	
							
							var eduState=  $('#State'+i).val();
							
								if(eduState ==''){
									$('#State'+i).css({ "border":"2px solid red"  });
									mandatoryFlag = true;
								}else{
									$('#State'+i).css('border', '0');
								}
							
								var lastExam=  $('#lastExam_'+i).val();

								var consolidated=  $('#consolidated_'+i).val();
								var marksObtained=  $('#marksObtained_'+i).val();
								var maxMarks=  $('#maxMarks_'+i).val();
								var percentage=  $('#percentage_'+i).val();
								

								var YOP=  $('#YOP'+i).val();
								var Month=  $('#Month'+i).val();
								//var Attempt=  $('#Attempt'+i).val();
								var previousRegNo= $('#previousRegNo'+i).val();
								
								if(lastExam !='' && lastExam=='false'){
									if(consolidated !='' && consolidated=='true'){
										if(marksObtained==''){
											$('#marksObtained_'+i).css({ "border":"2px solid red"  });
											mandatoryFlag = true;
										}else{
											$('#marksObtained_'+i).css('border', '0');
										}
										
										if(percentage==''){
											$('#maxMarks_'+i).css({ "border":"2px solid red"  });
											mandatoryFlag = true;
										}else{
											$('#maxMarks_'+i).css('border', '0');
										}
										if(percentage==''){
											$('#percentage_'+i).css({ "border":"2px solid red"  });
											mandatoryFlag = true;
										}else{
											$('#percentage_'+i).css('border', '0');
										}
										
									}
									if(YOP==''){
										$('#YOP'+i).css({ "border":"2px solid red"  });
										mandatoryFlag = true;
									}else{
										$('#YOP'+i).css('border', '0');
									}
									if(Month=='' || parseInt(Month)==0){
										$('#Month'+i).css({ "border":"2px solid red"  });
										mandatoryFlag = true;
									}else{
										$('#Month'+i).css('border', '0');
									}
									/*if(Attempt==''){
										$('#Attempt'+i).css({ "border":"2px solid red"  });
										mandatoryFlag = true;
									}else{
										$('#Attempt'+i).css('border', '0');
									}*/
								}
								var blockedMarks=  $('#blockedMarks_'+i).val();

								if(lastExam !='' && lastExam=='true'){

									if(blockedMarks=='' &&  blockedMarks ==null || blockedMarks !='true'){
										if(consolidated !='' && consolidated=='true'){
											if(marksObtained==''){
												$('#marksObtained_'+i).css({ "border":"2px solid red"  });
												mandatoryFlag = true;
											}else{
												$('#marksObtained_'+i).css('border', '0');
											}
											
											if(maxMarks==''){
												$('#maxMarks_'+i).css({ "border":"2px solid red"  });
												mandatoryFlag = true;
											}else{
												$('#maxMarks_'+i).css('border', '0');
											}
											
											if(percentage==''){
												$('#percentage_'+i).css({ "border":"2px solid red"  });
												mandatoryFlag = true;
											}else{
												$('#percentage_'+i).css('border', '0');
											}

											if(YOP==''){
												$('#YOP'+i).css({ "border":"2px solid red"  });
												mandatoryFlag = true;
											}else{
												$('#YOP'+i).css('border', '0');
											}
											if(Month=='' || parseInt(Month)==0){
												$('#Month'+i).css({ "border":"2px solid red"  });
												mandatoryFlag = true;
											}else{
												$('#Month'+i).css('border', '0');
											}
											/*if(Attempt==''){
												$('#Attempt'+i).css({ "border":"2px solid red"  });
												mandatoryFlag = true;
											}else{
												$('#Attempt'+i).css('border', '0');
											}
											*/
										}

									}
									
								}
								
								
								
								
								//raghu

								
									if(marksObtained==''){
										$('#marksObtained_'+i).css({ "border":"2px solid red"  });
										mandatoryFlag = true;
									}else{
										$('#marksObtained_'+i).css('border', '0');
									}
									
									if(maxMarks==''){
										$('#maxMarks_'+i).css({ "border":"2px solid red"  });
										mandatoryFlag = true;
									}else{
										$('#maxMarks_'+i).css('border', '0');
									}
									
									if(percentage==''){
										$('#percentage_'+i).css({ "border":"2px solid red"  });
										mandatoryFlag = true;
									}else{
										$('#percentage_'+i).css('border', '0');
									}
								
								if(YOP==''){
									$('#YOP'+i).css({ "border":"2px solid red"  });
									mandatoryFlag = true;
								}else{
									$('#YOP'+i).css('border', '0');
								}
								if(Month=='' || parseInt(Month)==0){
									$('#Month'+i).css({ "border":"2px solid red"  });
									mandatoryFlag = true;
								}else{
									$('#Month'+i).css('border', '0');
								}
								/*if(Attempt==''){
									$('#Attempt'+i).css({ "border":"2px solid red"  });
									mandatoryFlag = true;
								}else{
									$('#Attempt'+i).css('border', '0');
								}
							*/
								if(previousRegNo==''){
									$('#previousRegNo'+i).css({ "border":"2px solid red"  });
									mandatoryFlag = true;
								}else{
									$('#previousRegNo'+i).css('border', '0');
								}
								
								
								
							}
						}//errors check over

				
				
				
				//main metod of submit if no errors are there
				if(mandatoryFlag === false){
					 		document.getElementById("focusValue").value=null;
			        	
			    			document.getElementById("method").value="submitEducationPageOnline";
			        		document.onlineApplicationForm.submit();	
			    		
					  
				 }
				
				
				
				//message display
					if(mandatoryFlag){
						
						$.confirm({
					 		'message'	: 'Please fill in the mandatory information.',
							'buttons'	: {
								'Ok'	: {
									'class'	: 'blue',
									'action': function(){
										
										$.confirm.hide();
									}
								}
							}
					 	});
						  return false;
					}
					
					
					
					
					
		  });//submit page close here
	
		});//ready function close

	
	
	
	function checkAlertMarksObtained(count){
		$.confirm({
	 		'message'	: 'Please enter the obtained marks in this field.<BR> In case if the marks are in credit or CGPA, convert it into percentage and enter.<BR> For CBSE board, please multiply the obtained CGPA with 9.5,  else the marks will be void. eg., If you have obtained 9.2 CGPA, multiply with 9.5 which is equal to 87.4',
			'buttons'	: {
				'Ok'	: {
					'class'	: 'blue',
					'action': function(){
						document.getElementById("marksObtained_"+count).focus();
						$.confirm.hide();
					}
				}
			}
	 	});
	 	
	}

	function checkAlertMaxMarks(count){
		$.confirm({
	 		'message'	: 'The Max marks of all subjects should be entered here. Eg: If there are 5 subjects and each subject is out of 100, then enter 500, else enter 100 if it is converted to percentage.',
			'buttons'	: {
				'Ok'	: {
					'class'	: 'blue',
					'action': function(){
						document.getElementById("maxMarks_"+count).focus();
						$.confirm.hide();
					}
				}
			}
	 	});
	 	
	}


	function checkAlertMarksObtainedBySemisterWise(count){
		$.confirm({
	 		'message'	: 'Please enter the obtained marks.In case if the marks are in credit or CGPA, convert it into percentage and enter',
			'buttons'	: {
				'Ok'	: {
					'class'	: 'blue',
					'action': function(){
						document.getElementById("marksObtained_"+count).focus();
						$.confirm.hide();
					}
				}
			}
	 	});
	 	
	}

	function checkAlertMaxMarksSemisterWise(count){
		$.confirm({
	 		'message'	: 'The Max marks of all subjects should be entered here. Eg: If there are 5 subjects and each subject is out of 100, then enter 500.',
			'buttons'	: {
				'Ok'	: {
					'class'	: 'blue',
					'action': function(){
						document.getElementById("maxMarks_"+count).focus();
						$.confirm.hide();
					}
				}
			}
	 	});
	 	
	}

	
	function imposeMaxLength(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(199, size);
	    }
	}

	function len_display(Object,MaxLen){
		document.getElementById("otherAddnInfoTextDisply").style.display="block";
		
	    var len_remain = MaxLen-Object.value.length;
	   if(len_remain <=200){
		    document.getElementById("otherAddnInfoTextDisply").value=len_remain;
	     }
	}
	
	