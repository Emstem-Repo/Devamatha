
$(document).ready(function(){
	// setInterval(function() { AutoSave(); },300000);
	 var focusValueId=document.getElementById("focusValue").value;
	 var showMsg=document.getElementById("marksNoEntry").value;
	 if(focusValueId!=null && focusValueId!=""){
		 $("#"+focusValueId).focus();
	 }
	 if(showMsg=='false'){
		 $("#showMessage").hide(); 
	 }
	 
	 //newly added
	 $('#checkEligiblityToProceed').click(function(e){
			$(this).hide();
			 document.getElementById("method").value="submitPreRequisiteApply";
			 document.onlineApplicationForm.submit();
	 });		
	 
	
	 
	 
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
	document.location.href = "DocumentDownloadAction.do?documentId="
		+ documentId;
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




var globalID;
var count;
var dynarow2id;
function getMarkEntryAvailable(university,count) {
	var courseId=document.getElementById("courseId").value;
	if(university.value=="Other"){
		var blocked=$("#blockedMarks_"+count).val();
		if(blocked!=null && blocked!="" && blocked=='true'){
			if($("#showHideBlockMarks").length){
				$("#showHideBlockMarks").hide();
				$("#showHideExamPassYearMonth_"+count).hide();
			}
			if($("#showBlockedMarksMsg").length){
				$("#showBlockedMarksMsg").show();
			}
		}
	}else if(university.value != '0') {
		var univ=university.value;
		$.ajax({
		      type: "post",
		      url: "onlineApplicationSubmit.do?method=getMarkEntryAvailable",
		      data: {courseId:courseId,universityId:univ},
		      success:function(data) 
		      {
		    	 if (data=='Yes') {
		    		 if($("#showHideBlockMarks").length){
							$("#showHideBlockMarks").show();
							$("#showHideExamPassYearMonth_"+count).show();
						}
		    		 if($("#showBlockedMarksMsg").length){
						 $("#showBlockedMarksMsg").hide();
					 }
				}else if(data=='No'){
					 if($("#showHideBlockMarks").length){
							$("#showHideBlockMarks").hide();
							$("#showHideExamPassYearMonth_"+count).hide();
						}
					 if($("#showBlockedMarksMsg").length){
						 $("#showBlockedMarksMsg").show();
					 }
				}else{
					var blocked=$("#blockedMarks_"+count).val();
					if(blocked!=null && blocked!="" && blocked=='true'){
						if($("#showHideBlockMarks").length){
							$("#showHideBlockMarks").hide();
							$("#showHideExamPassYearMonth_"+count).hide();
						}
					 if($("#showBlockedMarksMsg").length){
						 $("#showBlockedMarksMsg").show();
					 }
					}
				}
		      }
		  });
	}/* else {
		 var colleges = document.getElementById(globalID);
		 for (x1=colleges.options.length-1; x1>0; x1--)
		 {
			 colleges.options[x1]=null;
		 }
	}*/

}


//newly copied
$(document).ready(function() {	
	var ednCountSize=$("#ednQualificationListSize").val();
	if(ednCountSize!=null && ednCountSize!=""){
		for (var i = 0; i < ednCountSize; i++) {
			var blocked=$("#blockedMarks_"+i).val();
			if(blocked!=null && blocked!="" && blocked=='true'){
				if($("#showHideBlockMarks").length){
					$("#showHideBlockMarks").hide();
					$("#showHideExamPassYearMonth_"+i).hide();
				}
				if($("#showBlockedMarksMsg").length){
					 $("#showBlockedMarksMsg").show();
				 }
			}else{
				if($("#showHideBlockMarks").length){
					$("#showHideBlockMarks").show();
					$("#showHideExamPassYearMonth_"+i).show();
				}
				if($("#showBlockedMarksMsg").length){
					 $("#showBlockedMarksMsg").hide();
				 }
			}
		}
	}
	});
	setStartDate();
	setInterval ( "checkSession()", 300000 );

	
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
	    
	   

		$('#SubmitDetailPage').click(function(e){
			
			
			var mandatoryFlag = false;
				var firstNameId=  $('#firstNameId').val();
				var dateOfBirth=  $('#dateOfBirth').val();
				var nationality=  $('#nationality').val();
				
				var gender='';
				if(document.getElementById('MALE').checked) {
					gender= $('#MALE').val();
					}else if(document.getElementById('FEMALE').checked) {
						gender= $('#FEMALE').val();
					}
				var bgType=  $('#bgType').val();
				var secondLanguage=  $('#secondLanguage').val();
				
				var birthPlace=  $('#birthPlace').val();
				var birthCountry=  $('#birthCountry').val();
				var birthState=  $('#birthState').val();
				var residentCategory=  $('#residentCategory').val();
				var religions=  $('#religions').val();
				var castCatg=  $('#castCatg').val();
				var areaType='';
				if(document.getElementById('areaTypeR').checked) {
					areaType='R';
					}else if(document.getElementById('areaTypeU').checked) {
						areaType='U';
					}
				
				var applicantphNo3=  $('#applicantphNo3').val();
				var applicantMobileNo=  $('#applicantMobileNo').val();
				var applicantEmail=  $('#applicantEmail').val();
				var confirmEmailId=  $('#confirmEmailId').val();
				var currentAddressLine1=  $('#currentAddressLine1').val();
				var currentCityName=  $('#currentCityName').val();
				var currentCountryName=  $('#currentCountryName').val();
				var tempAddrstate=  $('#tempAddrstate').val();
				var zipCode=  $('#zipCode').val();
				
				
				var otherTempAddrState=  $('#otherTempAddrState').val();

				var temoAddrOtherState='';
				if(tempAddrstate == 'Other'){
					temoAddrOtherState='Other';
				}

				var permanentAddressLine1=  $('#permanentAddressLine1').val();
				var permanentCityName=  $('#permanentCityName').val();
				var permanentCountryName=  $('#permanentCountryName').val();
				var permAddrstate=  $('#permAddrstate').val();
				var otherPermAddrState=  $('#otherPermAddrState').val();
				var permAddrOtherState='';
				if(permAddrstate == 'Other'){
					permAddrOtherState='Other';
				}
				
				var permanentAddressZipCode=  $('#permanentAddressZipCode').val();
				var isSameAddr='';
				if(document.getElementById('sameAddr').checked) {
					isSameAddr='true';
					}else if(document.getElementById('DiffAddr').checked) {
						isSameAddr='false';
					}
				
				if(isSameAddr !='' && isSameAddr=='false'){
						if(permanentAddressLine1 !=''){
							$('#permanentAddressLine1').css('border', '0');
						}else{
							 mandatoryFlag = true;
							 $('#permanentAddressLine1').css({ "border":"2px solid red"  });
						}
						if(permanentCityName !=''){
							$('#permanentCityName').css('border', '0');
						}else{
							 mandatoryFlag = true;
							 $('#permanentCityName').css({ "border":"2px solid red"  });
						}
						if(permanentCountryName !=''){
							$('#permanentCountryName').css('border', '0');
						}else{
							 mandatoryFlag = true;
							 $('#permanentCountryName').css({ "border":"2px solid red"  });
						}
						if(permAddrstate !=''){
							$('#permAddrstate').css('border', '0');
						}else{
							 mandatoryFlag = true;
							 $('#permAddrstate').css({ "border":"2px solid red"  });
						}
						
						if(permAddrOtherState!='' &&  permAddrOtherState =='Other' &&  otherPermAddrState ==''){
							mandatoryFlag = true;
							 $('#otherPermAddrState').css({ "border":"2px solid red"  });
						}else{
							$('#otherPermAddrState').css('border', '0');
						}

						
						if(permanentAddressZipCode !=''){
							$('#permanentAddressZipCode').css('border', '0');
						}else{
							 mandatoryFlag = true;
							 $('#permanentAddressZipCode').css({ "border":"2px solid red"  });
						}
				}
				var fatherName=  $('#fatherName').val();
				var titleOfFather=  $('#titleOfFather').val();
				var fatherIncomeRange=  $('#fatherIncomeRange').val();
				if(titleOfFather !=''){
					if(titleOfFather == 'Mr' && fatherIncomeRange==''){
						 $('#fatherIncomeRange').css({ "border":"2px solid red"  });
						  mandatoryFlag = true;
					}else if(titleOfFather == 'Late' ){
						$('#fatherIncomeRange').css('border', '0');
					}

				}else{
					$('#fatherIncomeRange').css('border', '0');
				}

				var motherName=  $('#motherName').val();
				var titleOfMother=  $('#titleOfMother').val();
				var motherIncomeRange=  $('#motherIncomeRange').val();

				if(titleOfMother !=''){
					if(titleOfMother == 'Mrs' && motherIncomeRange==''){
						 $('#motherIncomeRange').css({ "border":"2px solid red"  });
						  mandatoryFlag = true;
					}else if(titleOfMother == 'Late' ){
						$('#motherIncomeRange').css('border', '0');
					}

				}else{
					$('#motherIncomeRange').css('border', '0');
				}
				var guardianName=  $('#guardianName').val();
				var guardianPh3=  $('#guardianPh3').val();
				var guardianMob2=  $('#guardianMob2').val();
				var parentMob2=  $('#parentMob2').val();
				var parentPh3=  $('#parentPh3').val();
				if(guardianName!='' ){
					if(guardianPh3==''){
						$('#guardianPh3').css({ "border":"2px solid red"  });
						$('#parentPh3').css('border', '0');
						$('#parentMob2').css('border', '0');
						mandatoryFlag = true;
					}else{
						$('#guardianPh3').css('border', '0');
					}
					if(guardianMob2==''){
						$('#guardianMob2').css({ "border":"2px solid red"  });
						$('#parentPh3').css('border', '0');
						$('#parentMob2').css('border', '0');
						mandatoryFlag = true;
					}else{
						$('#guardianMob2').css('border', '0');
					}
					
				}else{
					if(parentPh3==''){
						$('#parentPh3').css({ "border":"2px solid red"  });
						$('#guardianPh3').css('border', '0');
						$('#guardianMob2').css('border', '0');
						mandatoryFlag = true;
					}else{
						$('#parentPh3').css('border', '0');
					}
					if(parentMob2==''){
						$('#parentMob2').css({ "border":"2px solid red"  });
						$('#guardianPh3').css('border', '0');
						$('#guardianMob2').css('border', '0');
						mandatoryFlag = true;
					}else{
						$('#parentMob2').css('border', '0');
					}
				}
				var applicantFeedbackId=  $('#applicantFeedbackId').val();
				var indianCandidate=  $('#indianCandidate').val();
				var passportNo=  $('#passportNo').val();
				var passportValidity=  $('#passportValidity').val();
				if(indianCandidate !='' && indianCandidate != 'true' ){
					if(passportNo==''){
						$('#passportNo').css({ "border":"2px solid red"  });
					}else{
						$('#passportNo').css('border', '0');
					}

					if(passportValidity == ''){
						$('#passportValidity').css({ "border":"2px solid red"  });
					}else{
						$('#passportValidity').css('border', '0');
					}
				}
				var isPreferenceList=  $('#isPreferenceList').val();
				var isCoursePref2Mandatory=  $('#isCoursePref2Mandatory').val();
				var coursePref2=  $('#coursePref2').val();
				var coursePref3Mandatory=  $('#coursePref3Mandatory').val();
				var coursePref3=  $('#coursePref3').val();
				 
				if(isPreferenceList !='' && isPreferenceList == 'true'){
                    if(isCoursePref2Mandatory !='' && isCoursePref2Mandatory == 'true'){
                    	if(coursePref2 == ''){
    						$('#coursePref2').css({ "border":"2px solid red"  });
    						mandatoryFlag = true;
    					}else{
    						$('#coursePref2').css('border', '0');
    					}
                    }

                    if(coursePref3Mandatory !='' && coursePref3Mandatory == 'true'){
                    	if(coursePref3 == ''){
    						$('#coursePref3').css({ "border":"2px solid red"  });
    						mandatoryFlag = true;
    					}else{
    						$('#coursePref3').css('border', '0');
    					}
                    }
					
				}
				
				var isInterviewSelectionSchedule=  $('#isInterviewSelectionScheduled').val();
				var interviewVenue=  $('#interviewVenue').val();
				var interviewSelectionDate=  $('#interviewSelectionDate').val();

				if(isInterviewSelectionSchedule !='' && isInterviewSelectionSchedule == 'true'){

					if(interviewVenue == ''){
						$('#interviewVenue').css({ "border":"2px solid red"  });
						mandatoryFlag = true;
					}else{
						$('#interviewVenue').css('border', '0');
					}
					
					if(interviewSelectionDate == ''){
						$('#interviewSelectionDate').css({ "border":"2px solid red"  });
						mandatoryFlag = true;
					}else{
						$('#interviewSelectionDate').css('border', '0');
					}
					

				}

				var workExpNeeded=  $('#workExpNeeded').val();
				var workExpListSize=  $('#workExpListSize').val();
				var workExpMandatory=  $('#workExpMandatory').val();
				
				if(workExpNeeded !='' && workExpNeeded == 'true' && parseInt(workExpListSize) > 0){
						var organization=  $('#organization'+0).val();
						var expFromdate=  $('#expFromdate'+0).val();
						var expTodate=  $('#expTodate'+0).val();
				if(workExpMandatory !='' && workExpMandatory == 'true'){		
						if(organization == ''){
							$('#organization'+0).css({ "border":"2px solid red"  });
							mandatoryFlag = true;
						}else{
							$('#organization'+0).css('border', '0');
						}

						if(expFromdate == ''){
							$('#expFromdate'+0).css({ "border":"2px solid red"  });
							mandatoryFlag = true;
						}else{
							$('#expFromdate'+0).css('border', '0');
						}


						if(expTodate == ''){
							$('#expTodate'+0).css({ "border":"2px solid red"  });
							mandatoryFlag = true;
						}else{
							$('#expTodate'+0).css('border', '0');
						}
				}		
					
				}

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


								var YOP=  $('#YOP'+i).val();
								var Month=  $('#Month'+i).val();
								var Attempt=  $('#Attempt'+i).val();

								
								if(lastExam !='' && lastExam=='false'){
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
									if(Attempt==''){
										$('#Attempt'+i).css({ "border":"2px solid red"  });
										mandatoryFlag = true;
									}else{
										$('#Attempt'+i).css('border', '0');
									}
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
											if(Attempt==''){
												$('#Attempt'+i).css({ "border":"2px solid red"  });
												mandatoryFlag = true;
											}else{
												$('#Attempt'+i).css('border', '0');
											}
											
										}

									}
									
								}
							}
						}

				var isSportsPerson='';
				if(document.getElementById('sportsPersonYes').checked) {
					isSportsPerson= $('#sportsPersonYes').val();
					}else if(document.getElementById('sportsPersonNo').checked) {
						isSportsPerson= $('#sportsPersonNo').val();
					}

				if(isSportsPerson !='' && isSportsPerson=='true'){
					var sportsDescription=  $('#sportsDescription').val();
					if(sportsDescription == ''){
						$('#sportsDescription').css({ "border":"2px solid red"  });
						mandatoryFlag = true;
					}else{
						$('#sportsDescription').css('border', '0');
					}
				}

				var isHandicapped='';
				if(document.getElementById('handicappedYes').checked) {
					isHandicapped= $('#handicappedYes').val();
					}else if(document.getElementById('handicappedNo').checked) {
						isHandicapped= $('#handicappedNo').val();
					}

				if(isHandicapped !='' && isHandicapped=='true'){
					var hadnicappedDescription=  $('#hadnicappedDescription').val();
					if(hadnicappedDescription == ''){
						$('#hadnicappedDescription').css({ "border":"2px solid red"  });
						mandatoryFlag = true;
					}else{
						$('#hadnicappedDescription').css('border', '0');
					}
				}

				var	displayExtraDetails= $('#isDisplayExtraDetails').val();
				if(displayExtraDetails !='' && displayExtraDetails=='true'){
					var	motherTongue= $('#motherTongue').val();
					if(motherTongue == ''){
						$('#motherTongue').css({ "border":"2px solid red"  });
						mandatoryFlag = true;
					}else{
						$('#motherTongue').css('border', '0');
					}
					
				}
				
				if(firstNameId =='' || dateOfBirth=='' || nationality=='' || gender=='' || bgType=='' || secondLanguage=='' || birthPlace=='' || birthCountry=='' || birthState==''
					|| residentCategory =='' || religions=='' || castCatg =='' || areaType =='' || applicantphNo3=='' || applicantMobileNo=='' || applicantEmail==''
					||	confirmEmailId=='' || currentAddressLine1=='' || currentCityName=='' || currentCountryName=='' || tempAddrstate==''   
					|| (temoAddrOtherState=='Other' && otherTempAddrState=='' )  || zipCode=='' || fatherName=='' || titleOfFather=='' || titleOfMother=='' || motherName=='' || applicantFeedbackId==''){


					if(areaType !=''){
						$('#errorareaTypeDetails').css("background", "0");
					}else{
						 $('#errorareaTypeDetails').css("background", "red");
						 
					}
					
					if(firstNameId !='' ){
						$('#firstNameId').css('border', '0');
					}else{
						 $('#firstNameId').css({ "border":"2px solid red"  });
					}
					if(dateOfBirth !=''){
						$('#dateOfBirth').css('border', '0');
					}else{
						 $('#dateOfBirth').css({ "border":"2px solid red"  });
					}
					if(nationality !=''){
						$('#nationality').css('border', '0');
					}else{
						 $('#nationality').css({ "border":"2px solid red"  });
					}

					if(gender !=''){
						$('#errorGenderDetails').css("background", "0");
					}else{
						 $('#errorGenderDetails').css("background", "red");
						 
					}
					if(bgType !=''){
						$('#bgType').css('border', '0');
					}else{
						 $('#bgType').css({ "border":"2px solid red"  });
					}

					if(secondLanguage !=''){
						$('#secondLanguage').css('border', '0');
					}else{
						 $('#secondLanguage').css({ "border":"2px solid red"  });
					}
					
					
					if(birthPlace !=''){
						$('#birthPlace').css('border', '0');
					}else{
						 $('#birthPlace').css({ "border":"2px solid red"  });
					}
					if(birthCountry !=''){
						$('#birthCountry').css('border', '0');
					}else{
						 $('#birthCountry').css({ "border":"2px solid red"  });
					}
					if(birthState !=''){
						$('#birthState').css('border', '0');
					}else{
						 $('#birthState').css({ "border":"2px solid red"  });
					}
					if(residentCategory !=''){
						$('#residentCategory').css('border', '0');
					}else{
						 $('#residentCategory').css({ "border":"2px solid red"  });
					}
					if(religions !=''){
						$('#religions').css('border', '0');
					}else{
						 $('#religions').css({ "border":"2px solid red"  });
					}
					if(castCatg !=''){
						$('#castCatg').css('border', '0');
					}else{
						 $('#castCatg').css({ "border":"2px solid red"  });
					}
					if(applicantphNo3 !=''){
						$('#applicantphNo3').css('border', '0');
					}else{
						 $('#applicantphNo3').css({ "border":"2px solid red"  });
					}
					if(applicantMobileNo !=''){
						$('#applicantMobileNo').css('border', '0');
					}else{
						 $('#applicantMobileNo').css({ "border":"2px solid red"  });
					}

					if(applicantEmail !=''){
						$('#applicantEmail').css('border', '0');
					}else{
						 $('#applicantEmail').css({ "border":"2px solid red"  });
					}
					if(confirmEmailId !=''){
						$('#confirmEmailId').css('border', '0');
					}else{
						 $('#confirmEmailId').css({ "border":"2px solid red"  });
					}
					if(currentAddressLine1 !=''){
						$('#currentAddressLine1').css('border', '0');
					}else{
						 $('#currentAddressLine1').css({ "border":"2px solid red"  });
					}
					if(currentCityName !=''){
						$('#currentCityName').css('border', '0');
					}else{
						 $('#currentCityName').css({ "border":"2px solid red"  });
					}
					if(currentCountryName !=''){
						$('#currentCountryName').css('border', '0');
					}else{
						 $('#currentCountryName').css({ "border":"2px solid red"  });
					}
					if(tempAddrstate !=''){
						$('#tempAddrstate').css('border', '0');
					}else{
						 $('#tempAddrstate').css({ "border":"2px solid red"  });
					}
					if(otherTempAddrState !=''){
						$('#otherTempAddrState').css('border', '0');
					}else{
						 $('#otherTempAddrState').css({ "border":"2px solid red"  });
					}
					if(zipCode !=''){
						$('#zipCode').css('border', '0');
					}else{
						 $('#zipCode').css({ "border":"2px solid red"  });
					}
					
					
					
					if(fatherName !=''){
						$('#fatherName').css('border', '0');
					}else{
						 $('#fatherName').css({ "border":"2px solid red"  });
					}
					if(motherName !=''){
						$('#motherName').css('border', '0');
					}else{
						 $('#motherName').css({ "border":"2px solid red"  });
					}

					if(titleOfFather !=''){
						$('#titleOfFather').css('border', '0');
					}else{
						 $('#titleOfFather').css({ "border":"2px solid red"  });
					}
					if(titleOfMother !=''){
						$('#titleOfMother').css('border', '0');
					}else{
						 $('#titleOfMother').css({ "border":"2px solid red"  });
					}


					
					if(applicantFeedbackId !=''){
						$('#applicantFeedbackId').css('border', '0');
					}else{
						 $('#applicantFeedbackId').css({ "border":"2px solid red"  });
					}


					  
				  mandatoryFlag = true;
				 } else if(mandatoryFlag === false){
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
					if(mandatoryFlag){
						if(firstNameId=="") {
							document.getElementById("firstNameId").focus();
						}else if(dateOfBirth=="") {
							ment.getElementById("dateOfBirth").focus();
						}else if(nationality=="") {
							document.getElementById("nationality").focus();
						}else if(nationality=="") {
							document.getElementById("MALE").focus();
						}
						if(birthPlace=="") {
							document.getElementById("birthPlace").focus();
						}else if(birthCountry=="") {
							document.getElementById("birthCountry").focus();
						}else if(birthState=="") {
							document.getElementById("birthState").focus();
						}else if(residentCategory=="") {
							document.getElementById("residentCategory").focus();
						}else if(religions=="") {
							document.getElementById("religions").focus();
						}
						if(castCatg=="") {
							document.getElementById("castCatg").focus();
						}
						if(applicantphNo3=="") {
							document.getElementById("applicantphNo3").focus();
						}
						if(applicantMobileNo=="") {
							document.getElementById("applicantMobileNo").focus();
						}
						if(applicantEmail=="") {
							document.getElementById("applicantEmail").focus();
						}
						if(confirmEmailId=="") {
							document.getElementById("confirmEmailId").focus();
						}
						if(currentAddressLine1=="") {
							document.getElementById("currentAddressLine1").focus();
						}
						if(currentCityName=="") {
							document.getElementById("currentCityName").focus();
						}
						if(currentCityName=="") {
							document.getElementById("currentCountryName").focus();
						}
						if(tempAddrstate=="") {
							document.getElementById("tempAddrstate").focus();
						}
						if(otherPermAddrState=="") {
							document.getElementById("otherPermAddrState").focus();
						}
						
						if(zipCode=="") {
							document.getElementById("zipCode").focus();
						}
						if(permanentAddressLine1=="") {
							document.getElementById("permanentAddressLine1").focus();
						}
						if(permanentAddressLine1=="") {
							document.getElementById("permanentAddressLine1").focus();
						}
						if(permanentCityName=="") {
							document.getElementById("permanentCityName").focus();
						}
						if(permanentCountryName=="") {
							document.getElementById("permanentCountryName").focus();
						}
						if(permAddrstate=="") {
							document.getElementById("permAddrstate").focus();
						}
						if(otherPermAddrState=="") {
							document.getElementById("otherPermAddrState").focus();
						}

						if(permanentAddressZipCode=="") {
							document.getElementById("permanentAddressZipCode").focus();
						}
						if(otherTempAddrState=="") {
							document.getElementById("otherTempAddrState").focus();
						}
						if(fatherName=="") {
							document.getElementById("fatherName").focus();
						}
						
						if(motherName=="") {
							document.getElementById("motherName").focus();
						}

						if(guardianName!='' ){
							var guardianPh3=  $('#guardianPh3').val();
							var guardianMob2=  $('#guardianMob2').val();
								if(guardianPh3!=''){
									document.getElementById("guardianPh3").focus();
								}else if(guardianMob2!='' && guardianMob2==''){
									document.getElementById("guardianMob2").focus();
								}
						}else{
							var parentMob2=  $('#parentMob2').val();
							var parentPh3=  $('#parentPh3').val();
							if(parentPh3!=''){
								document.getElementById("parentPh3").focus();
							}else if(parentPh3!='' && parentMob2 ==''){
								document.getElementById("parentMob2").focus();
							}
						}
						if(applicantFeedbackId=="") {
							document.getElementById("applicantFeedbackId").focus();
						}
						
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
		  });


				
		});

	function hideGuardenPhoneNumber(){
		
		var hideGuardenMandatorySymbols=  $('#personalDataParentPh1').val();
		var hideGuardenMandatorySymbols1=  $('#personalDataParentPh2').val();
		var hideGuardenMandatorySymbols2=  $('#parentPh3').val();
		var hideGuardenMandatorySymbols3=  $('#personalDataParentMob1').val();
		var hideGuardenMandatorySymbols4=  $('#parentMob2').val();


		


		if(hideGuardenMandatorySymbols !='' || hideGuardenMandatorySymbols1 !='' || hideGuardenMandatorySymbols2 !=''
			|| hideGuardenMandatorySymbols3!='' || hideGuardenMandatorySymbols4!='' ){

			document.getElementById("hideGuardenMandatorySymbols").innerHTML = "Country code:";
			 document.getElementById("hideGuardenMandatorySymbols1").innerHTML = "Area code:";
			 document.getElementById("hideGuardenMandatorySymbols2").innerHTML = "Phone No:";
			 document.getElementById("hideGuardenMandatorySymbols3").innerHTML = "Country code:";
			 document.getElementById("hideGuardenMandatorySymbols4").innerHTML = "Mobile No:";

		}else if(hideGuardenMandatorySymbols =='' && hideGuardenMandatorySymbols1 =='' && hideGuardenMandatorySymbols2 ==''
			&& hideGuardenMandatorySymbols3 =='' &&  hideGuardenMandatorySymbols4==''){
			
			 document.getElementById("hideGuardenMandatorySymbols").innerHTML = "<span class='Mandatory'>*</span> Country code:";
			 document.getElementById("hideGuardenMandatorySymbols1").innerHTML = " <span class='Mandatory'>*</span> Area code:";
			 document.getElementById("hideGuardenMandatorySymbols2").innerHTML = "<span class='Mandatory'>*</span> Phone No:";
			 document.getElementById("hideGuardenMandatorySymbols3").innerHTML = "<span class='Mandatory'>*</span> Country code:";
			 document.getElementById("hideGuardenMandatorySymbols4").innerHTML = "<span class='Mandatory'>*</span> Mobile No:";
		}
		 
	}


	function hideParentPhoneNumber(){

		var hideParentMandatorySymbols=  $('#personalDataGuardianPh1').val();
		var hideParentMandatorySymbols1=  $('#personalDataGuardianPh2').val();
		var hideParentMandatorySymbols2=  $('#guardianPh3').val();
		var hideParentMandatorySymbols3=  $('#guardianMob1').val();
		var hideParentMandatorySymbols4=  $('#guardianMob2').val();


		if(hideParentMandatorySymbols !='' || hideParentMandatorySymbols1 !='' || hideParentMandatorySymbols2 !=''
			|| hideParentMandatorySymbols3 !='' || hideParentMandatorySymbols4 !='' ){
		
		 document.getElementById("hideParentMandatorySymbols").innerHTML = "Country code:";
		 document.getElementById("hideParentMandatorySymbols1").innerHTML = "Area code:";
		 document.getElementById("hideParentMandatorySymbols2").innerHTML = "Phone No:";
		 document.getElementById("hideParentMandatorySymbols3").innerHTML = "Country code:";
		 document.getElementById("hideParentMandatorySymbols4").innerHTML = "Mobile No:";
		}else if(hideParentMandatorySymbols =='' && hideParentMandatorySymbols1 =='' && hideParentMandatorySymbols2 ==''
			&& hideParentMandatorySymbols3 =='' &&  hideParentMandatorySymbols4 ==''){
			
			 document.getElementById("hideParentMandatorySymbols").innerHTML = "<span class='Mandatory'>*</span> Country code:";
			 document.getElementById("hideParentMandatorySymbols").innerHTML = " <span class='Mandatory'>*</span> Area code:";
			 document.getElementById("hideParentMandatorySymbols").innerHTML = "<span class='Mandatory'>*</span> Phone No:";
			 document.getElementById("hideParentMandatorySymbols").innerHTML = "<span class='Mandatory'>*</span> Country code:";
			 document.getElementById("hideParentMandatorySymbols").innerHTML = "<span class='Mandatory'>*</span> Mobile No:";
		}
	}
	
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
	
	