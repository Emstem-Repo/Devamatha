/**
 * @author balakrishna
 */
function isNumberKey(evt) {
	
	var charCode = (evt.which) ? evt.which : event.keyCode;
			//if((evt.ctrlKey || evt.keyCode == 86)  ){
				if((evt.ctrlKey )  ){
				return true;
			}
    if ((charCode > 47 && charCode < 58) || charCode == 8){ 
        return true; 
    } 
    return false;  
}

function validateEditCourse(){
	var courseid=document.getElementById("courseId").value;
	
	document.location.href = "admissionFormSubmit.do?method=getPreferences&courseId="+courseid;
	
	
}


function setParentAddress(){

	document.getElementById("parentAddressLine1").value=document.getElementById("currentAddressLine1").value;
	document.getElementById("parentAddressLine2").value=document.getElementById("currentAddressLine2").value;
	document.getElementById("parentCityName").value=document.getElementById("currentCityName").value;
	document.getElementById("parentCountryName").value=document.getElementById("currentCountryName").value;
	document.getElementById("parentState").value=document.getElementById("tempAddrstate").value;
	document.getElementById("parentAddressZipCode").value=document.getElementById("currentAddressZipCode").value;
	document.getElementById("otherParentAddrState").value=document.getElementById("otherTempAddrState").value;
	document.getElementById("parentMobile").value=document.getElementById("fatherMobile").value;
	document.getElementById("parentMobile1").value=document.getElementById("fatherMobile1").value;
	document.getElementById("otherParentAddrState").value=document.getElementById("otherTempAddrState").value;
	
	
}

function disParentAddress(){

	document.getElementById("parentAddressLine1").value="";
	document.getElementById("parentAddressLine2").value="";
	document.getElementById("parentCityName").value="";
	document.getElementById("parentCountryName").value="";
	document.getElementById("parentState").value="";
	document.getElementById("parentAddressZipCode").value="";
	document.getElementById("otherParentAddrState").value="";
	document.getElementById("parentMobile").value="";
	document.getElementById("parentMobile1").value="";
	
}


function showHostelAdmissionDescription(){
	document.getElementById("hostelcheck").checked=true;
	//alert(document.getElementById("hostelcheck").checked+'=='+document.getElementById("hostelcheck").value);
	document.getElementById("hosteladmission_description").style.display = "block";
	
}

function hideHostelAdmissionDescription(){
	document.getElementById("hostelcheck").checked=false;
	document.getElementById("hostelcheck").value="";
	//alert(document.getElementById("hostelcheck").checked+'=='+document.getElementById("hostelcheck").value);
	document.getElementById("hosteladmission_description").style.display = "none";
	
}


function showArtsParticipate(arts){
	if(arts!=""){
		document.getElementById("artsParticipate1").disabled=false;
	}else{
		document.getElementById("artsParticipate1").disabled=true;
		document.getElementById("artsParticipate1").value="";
	}
	
}

function showSportsParticipate(sports){
	if(sports!=""){
		document.getElementById("sportsParticipate1").disabled=false;
	}else{
		document.getElementById("sportsParticipate1").disabled=true;
		document.getElementById("sportsParticipate1").value="";
	}
	
}

function showNcccertificate(){
	document.getElementById("ncccertificate_description").style.display = "block";
}

function hideNcccertificate(){
	document.getElementById("ncccertificate_description").style.display = "none";
	document.getElementById("nccgarde1").value = "";
}

function showNsscertificate(){
	document.getElementById("nsscertificate_description").style.display = "block";
}

function hideNsscertificate(){
	document.getElementById("nsscertificate_description").style.display = "none";
	document.getElementById("nssgarde1").value = "";
}
function getMobileNo(){
	var currentCountryName = document.getElementById("currentCountryName").value;
	var indian=false;
	
		if(currentCountryName==1){
			indian = true;
		}
	
	if(indian){
		document.getElementById("fatherMobile").readOnly = true;
		document.getElementById("fatherMobile").value="91";
		document.getElementById("motherMobile").readOnly = true;
		document.getElementById("motherMobile").value="91";
		document.getElementById("parentMobile").readOnly = true;
		document.getElementById("parentMobile").value="91";
	}else{
		document.getElementById("fatherMobile").value="";
		document.getElementById("fatherMobile").readOnly = false;
		document.getElementById("motherMobile").value="";
		document.getElementById("motherMobile").readOnly = false;
		document.getElementById("parentMobile").value="";
		document.getElementById("parentMobile").readOnly = false;
	}			
}




function resetPersonalFormStudent() {

	//personal info
	//$('#firstNameId').val("");
	//$('#dateOfBirth').val("");
	$('#nationality').val("");
	$('#bgType').val("");
	//$('#secondLanguage').val("");
	
	$('#birthPlace').val("");
	$('#birthCountry').val("");
	$('#birthState').val("");
	$('#otherBirthState').val("");
	//$('#residentCategory').val("");
	$('#religions').val("");
	$('#castCatg').val("");
	$('#applicantphNo2').val("");
	$('#applicantphNo3').val("");
	//$('#applicantMobileNo').val("");
	//$('#applicantMobileCode').val("");
	//$('#applicantEmail').val("");
	
	
	//current addresss and permanent address
	$('#currentAddressLine1').val("");
	$('#currentAddressLine2').val("");
	$('#currentCityName').val("");
	$('#currentStreet').val("");
	$('#currentCountryName').val("");
	$('#tempAddrstate').val("");
	$('#currentAddressZipCode').val("");
	$('#otherTempAddrState').val("");
	$('#tempAddrdistrict').val("");
	$('#otherTempAddrDistrict').val("");
	
	$('#permanentAddressLine1').val("");
	$('#permanentAddressLine2').val("");
	$('#permanentCityName').val("");
	$('#permanentStreet').val("");
	$('#permanentCountryName').val("");
	$('#permAddrstate').val("");
	$('#otherPermAddrState').val("");
	$('#permanentAddressZipCode').val("");
	$('#permAddrdistrict').val("");
	$('#otherPermAddrDistrict').val("");
	
	document.getElementById('DiffAddr').checked="false";
		
	
	//parent info
	$('#fatherName').val("");
	$('#titleOfFather').val("");
	$('#fatherIncomeRange').val("");
	$('#motherName').val("");
	$('#titleOfMother').val("");
	$('#fatherMobile').val("");
	$('#fatherMobile1').val("");
	$('#motherEmail').val("");
	$('#fatherEmail').val("");
	$('#motherMobile').val("");
	$('#motherMobile1').val("");
	$('#otherOccupationMother').val("");
	$('#motherOccupation').val("");
	$('#otherOccupationFather').val("");
	$('#fatherOccupation').val("");
	
	document.getElementById('DiffParAddr').checked="false";

	$('#parentAddressLine1').val("");
	$('#parentAddressLine2').val("");
	$('#parentCountryName').val("");
	$('#parentAddressZipCode').val("");
	$('#parentState').val("");
	$('#otherParentAddrState').val("");
	$('#parentCityName').val("");
	$('#parentMobile').val("");
	$('#parentMobile1').val("");
	
	
	
	
    //other details
	//$('#subreligion').val("");
	//$('#applicantMobileCode').val("");
	$('#otherReligion').val("");
	$('#otherCastCatg').val("");
	$('#castCatg').val("");
	$('#religions').val("");
	$('#otherParish').val("");
	$('#otherDiocese').val("");
	document.getElementById("dioces_description").style.display = "none";
	document.getElementById("parish_description").style.display = "none";
	
	document.getElementById('exservice1').checked="false";
	document.getElementById('handicappedNo').checked="false";
	document.getElementById("handicapped_description").style.display = "none";
	$('#hadnicappedDescription').val("");
	
	$('#arts1').val("");
	$('#artsParticipate1').val("");
	$('#sportsParticipate1').val("");
	$('#sports1').val("");
	
	
	document.getElementById('nsscertificate1').checked="false";
	document.getElementById('ncccertificate1').checked="false";
	document.getElementById("ncccertificate_description").style.display = "none";
	document.getElementById("nsscertificate_description").style.display = "none";
	
	$('#nccgrade1').val("");
	document.getElementById('hosteladmissiondescription1').checked="false";
	document.getElementById("hosteladmission_description").style.display = "none";
	document.getElementById('hostelcheck').checked="false";
	
	

	
	
	
	document.getElementById('sportsPersonNo').checked="false";
	$('#sportsDescription').val("");
	
	
}




var globalID;
var count;
var dynarow2id;





	setStartDate();
	setInterval ( "checkSession()", 300000 );

	
	
	
	
	
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
	    
	   
	    
	    //checking errors through jquery

		$('#SubmitPersonalDetailStudent').click(function(e){
			
			//gettting values using ids
			var mandatoryFlag = false;
				var firstNameId=  $('#firstNameId').val();
				var dateOfBirth=  $('#dateOfBirth').val();
				var nationality=  $('#nationality').val();
				
				
				
				var bgType=  $('#bgType').val();
				//var secondLanguage=  $('#secondLanguage').val();
				
				var birthPlace=  $('#birthPlace').val();
				var birthCountry=  $('#birthCountry').val();
				var birthState=  $('#birthState').val();
				var residentCategory=  $('#residentCategory').val();
				var religions=  $('#religions').val();
				var castCatg=  $('#castCatg').val();
				
				//var applicantphNo3=  $('#applicantphNo3').val();
				var applicantMobileNo=  $('#applicantMobileNo').val();
				var applicantEmail=  $('#applicantEmail').val();
				
				
				
				var currentAddressLine1=  $('#currentAddressLine1').val();
				var currentAddressLine2=  $('#currentAddressLine2').val();
				var currentCityName=  $('#currentCityName').val();
				var currentCountryName=  $('#currentCountryName').val();
				var tempAddrstate=  $('#tempAddrstate').val();
				var currentAddressZipCode=  $('#currentAddressZipCode').val();
				var currentStreet=  $('#currentStreet').val();
				
				var otherTempAddrState=  $('#otherTempAddrState').val();

				var temoAddrOtherState='';
				if(tempAddrstate == 'Other'){
					temoAddrOtherState='Other';
				}

				var permanentAddressLine1=  $('#permanentAddressLine1').val();
				var permanentAddressLine2=  $('#permanentAddressLine2').val();
				var permanentCityName=  $('#permanentCityName').val();
				var permanentCountryName=  $('#permanentCountryName').val();
				var permAddrstate=  $('#permAddrstate').val();
				var otherPermAddrState=  $('#otherPermAddrState').val();
				var permAddrOtherState='';
				if(permAddrstate == 'Other'){
					permAddrOtherState='Other';
				}
				
				var permanentAddressZipCode=  $('#permanentAddressZipCode').val();
				var permanentStreet=  $('#permanentStreet').val();
				
				
				//raghu write newotherTempAddrDistrict
				
				var tempAddrdistrict=  $('#tempAddrdistrict').val();
				var otherTempAddrDistrict=  $('#otherTempAddrDistrict').val();
				var permAddrdistrict=  $('#permAddrdistrict').val();
				var otherPermAddrDistrict=  $('#otherPermAddrDistrict').val();
				var fatherMobile=  $('#fatherMobile').val();
				var fatherMobile1=  $('#fatherMobile1').val();
				var parentAddressLine1=  $('#parentAddressLine1').val();
				var parentMobile=  $('#parentMobile').val();
				var parentMobile1=  $('#parentMobile1').val();
				var parentAddressLine2=  $('#parentAddressLine2').val();
				var parentCountryName=  $('#parentCountryName').val();
				var parentAddressZipCode=  $('#parentAddressZipCode').val();
				var parentState=  $('#parentState').val();
				var otherParentAddrState=  $('#otherParentAddrState').val();
				var parentCityName=  $('#parentCityName').val();
				var subreligion=  $('#subreligion').val();
				var applicantMobileCode=$('#applicantMobileCode').val();
				var otherReligion=$('#otherReligion').val();
				var otherCastCatg=$('#otherCastCatg').val();
				var otherOccupationFather=$('#otherOccupationFather').val();
				var fatherOccupation=$('#fatherOccupation').val();

				     
				
				var tempAddrOtherDistrict='';
				if(tempAddrdistrict == 'Other'){
					tempAddrOtherDistrict='Other';
				}
				
				var permAddrOtherDistrict='';
				if(permAddrdistrict == 'Other'){
					permAddrOtherDistrict='Other';
				}
				
				var parentAddrOtherState='';
				if(parentState == 'Other'){
					parentAddrOtherState='Other';
				}
				
				var religionOther='';
				if(religions == 'Other'){
					religionOther='Other';
				}
				
				var castCatgOther='';
				if(castCatg == 'Other'){
					castCatgOther='Other';
				}
				
				var occupationFatherOther='';
				if(fatherOccupation == 'Other'){
					occupationFatherOther='Other';
				}
				
				var isSameAddr='';
				if(document.getElementById('sameAddr').checked) {
					isSameAddr='true';
					}else if(document.getElementById('DiffAddr').checked) {
						isSameAddr='false';
					}
				
				if(isSameAddr !='' && isSameAddr=='false'){
						if(permanentAddressLine1 !=''){
							$('#permanentAddressLine1').css({ "border":"3px solid #D2D2D2" });
						}else{
							 mandatoryFlag = true;
							 $('#permanentAddressLine1').css({ "border":"2px solid red"  });
						}
						if(permanentAddressLine2 !=''){
							$('#permanentAddressLine2').css({ "border":"3px solid #D2D2D2" });
						}else{
							 mandatoryFlag = true;
							 $('#permanentAddressLine2').css({ "border":"2px solid red"  });
						}
						if(permanentCityName !=''){
							$('#permanentCityName').css({ "border": "3px solid #D2D2D2" });
						}else{
							 mandatoryFlag = true;
							 $('#permanentCityName').css({ "border":"2px solid red"  });
						}
						if(permanentCountryName !=''){
							$('#permanentCountryName').css({ "border": "3px solid #D2D2D2" });
						}else{
							 mandatoryFlag = true;
							 $('#permanentCountryName').css({ "border":"2px solid red"  });
						}
						if(permAddrstate !=''){
							$('#permAddrstate').css({ "border": "3px solid #D2D2D2" });
						}else{
							 mandatoryFlag = true;
							 $('#permAddrstate').css({ "border":"2px solid red"  });
						}
						
						if(permAddrOtherState!='' &&  permAddrOtherState =='Other' &&  otherPermAddrState ==''){
							mandatoryFlag = true;
							 $('#otherPermAddrState').css({ "border":"2px solid red"  });
						}else{
							$('#otherPermAddrState').css({ "border": "3px solid #D2D2D2" });
						}

						
						if(permanentAddressZipCode !=''){
							$('#permanentAddressZipCode').css({ "border": "3px solid #D2D2D2" });
						}else{
							 mandatoryFlag = true;
							 $('#permanentAddressZipCode').css({ "border":"2px solid red"  });
						}
						
						
						//raghu
						if(permAddrstate !=''){
							$('#permAddrdistrict').css({ "border": "3px solid #D2D2D2" });
						}else{
							 mandatoryFlag = true;
							 $('#permAddrdistrict').css({ "border":"2px solid red"  });
						}
						
						if(permAddrOtherDistrict!='' &&  permAddrOtherDistrict =='Other' &&  otherPermAddrDistrict ==''){
							mandatoryFlag = true;
							 $('#otherPermAddrDistrict').css({ "border":"2px solid red"  });
						}else{
							$('#otherPermAddrDistrict').css({ "border": "3px solid #D2D2D2" });
						}
						
						if(permanentStreet !=''){
							$('#permanentStreet').css({ "border": "3px solid #D2D2D2" });
						}else{
							 mandatoryFlag = true;
							 $('#permanentStreet').css({ "border":"2px solid red"  });
						}
						
						
				}
				
				
				
				
				var isPermAddr='';
				if(document.getElementById('sameParAddr').checked) {
					isPermAddr='true';
					}else if(document.getElementById('DiffParAddr').checked) {
						isPermAddr='false';
					}
				
				if(isPermAddr !='' && isPermAddr=='false'){
					
					
					if(parentAddressLine1 !=''){
						$('#parentAddressLine1').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#parentAddressLine1').css({ "border":"2px solid red"  });
					}
					if(parentMobile !=''){
						$('#parentMobile').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#parentMobile').css({ "border":"2px solid red"  });
					}
					
					if(parentMobile1 !=''){
						$('#parentMobile1').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#parentMobile1').css({ "border":"2px solid red"  });
					}
					if(parentAddressLine2 !=''){
						$('#parentAddressLine2').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#parentAddressLine2').css({ "border":"2px solid red"  });
					}
					
					if(parentCountryName !=''){
						$('#parentCountryName').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#parentCountryName').css({ "border":"2px solid red"  });
					}
					if(parentAddressZipCode !=''){
						$('#parentAddressZipCode').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#parentAddressZipCode').css({ "border":"2px solid red"  });
					}
					
					if(parentState !=''){
						$('#parentState').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#parentState').css({ "border":"2px solid red"  });
					}
					if(otherParentAddrState !=''){
						$('#otherParentAddrState').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#otherParentAddrState').css({ "border":"2px solid red"  });
					}
					
					if(parentCityName !=''){
						$('#parentCityName').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#parentCityName').css({ "border":"2px solid red"  });
					}
					
					
				}
				
				
				
				var fatherName=  $('#fatherName').val();
				var titleOfFather=  $('#titleOfFather').val();
				var fatherIncomeRange=  $('#fatherIncomeRange').val();
				if(titleOfFather !=''){
					if(titleOfFather == 'Mr' && fatherIncomeRange==''){
						 $('#fatherIncomeRange').css({ "border":"2px solid red"  });
						  mandatoryFlag = true;
						  document.getElementById("fatherIncomeRange").focus();
					}else if(titleOfFather == 'Late' ){
						$('#fatherIncomeRange').css({ "border": "3px solid #D2D2D2" });
					}

				}else{
					$('#fatherIncomeRange').css({ "border": "3px solid #D2D2D2" });
				}

				var motherName=  $('#motherName').val();
				var titleOfMother=  $('#titleOfMother').val();
				
				
				
				var isDateExpired = document.getElementById("dateExpired").value;

				if(isDateExpired == 'false') {
					var arts =  document.getElementById("arts1").value;
					if(arts!=""){
						var artsParticipate=document.getElementById("artsParticipate1").value;
						if(artsParticipate==""){
							$('#artsParticipate1').css({ "border": "2px solid red" });
							document.getElementById("artsParticipate1").focus();
							mandatoryFlag = true;
						}else{
							$('#artsParticipate1').css({ "border": "3px solid #D2D2D2" });
						}
					}else{
						$('#artsParticipate1').css({ "border": "3px solid #D2D2D2" });
					}
					var sports =  document.getElementById("sports1").value;
					if(sports!=""){
						var sportsParticipate=document.getElementById("sportsParticipate1").value;
						if(sportsParticipate==""){
							$('#sportsParticipate1').css({ "border": "2px solid red" });
							document.getElementById("sportsParticipate1").focus();
							mandatoryFlag = true;
						}else{
							$('#sportsParticipate1').css({ "border": "3px solid #D2D2D2" });
						}
					}else{
						$('#sportsParticipate1').css({ "border": "3px solid #D2D2D2" });
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
							$('#hadnicappedDescription').css({ "border": "3px solid #D2D2D2" });
						}
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
							$('#organization'+0).css({ "border": "3px solid #D2D2D2" });
						}

						if(expFromdate == ''){
							$('#expFromdate'+0).css({ "border":"2px solid red"  });
							mandatoryFlag = true;
						}else{
							$('#expFromdate'+0).css({ "border": "3px solid #D2D2D2" });
						}


						if(expTodate == ''){
							$('#expTodate'+0).css({ "border":"2px solid red"  });
							mandatoryFlag = true;
						}else{
							$('#expTodate'+0).css({ "border": "3px solid #D2D2D2" });
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
						$('#sportsDescription').css({ "border": "3px solid #D2D2D2" });
					}
				}
				
				var	displayExtraDetails= $('#isDisplayExtraDetails').val();
				if(displayExtraDetails !='' && displayExtraDetails=='true'){
					var	motherTongue= $('#motherTongue').val();
					if(motherTongue == ''){
						$('#motherTongue').css({ "border":"2px solid red"  });
						mandatoryFlag = true;
					}else{
						$('#motherTongue').css({ "border": "3px solid #D2D2D2" });
					}
					
				}
				
				
				
				
				//mondatory fields check start here
				
				
				
				if(firstNameId =='' || dateOfBirth=='' || nationality=='' ||  bgType=='' ||  birthPlace=='' || birthCountry=='' 
					|| birthState=='' || residentCategory =='' || religions=='' || castCatg =='' ||  applicantMobileNo=='' || applicantEmail=='' 
					||	currentAddressLine1=='' ||	currentAddressLine2==''  || currentCityName=='' || currentCountryName=='' ||  currentAddressZipCode==''  
					|| (temoAddrOtherState=='Other' && otherTempAddrState=='' )  || tempAddrstate==''  || fatherName=='' 
					|| titleOfFather=='' || titleOfMother=='' || motherName=='' || applicantMobileCode=='' ||currentStreet==''
					|| (tempAddrOtherDistrict=='Other' && otherTempAddrDistrict=='' )  || tempAddrdistrict==''	
					||	fatherMobile=='' || fatherMobile1=='' ||   subreligion=='' 
					|| (religionOther=='Other' && otherReligion=='') || religions==''	|| (castCatgOther=='Other' && otherCastCatg=='')
					|| castCatg=='' || (occupationFatherOther=='Other' && otherOccupationFather=='') || fatherOccupation==''){ 
											
					
					if(firstNameId !='' ){
						$('#firstNameId').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#firstNameId').css({ "border":"2px solid red"  });
					}
					if(dateOfBirth !=''){
						$('#dateOfBirth').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#dateOfBirth').css({ "border":"2px solid red"  });
					}
					if(nationality !=''){
						$('#nationality').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#nationality').css({ "border":"2px solid red"  });
					}

					
					if(bgType !=''){
						$('#bgType').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#bgType').css({ "border":"2px solid red"  });
					}

					//if(secondLanguage !=''){
					////	$('#secondLanguage').css({ "border": "3px solid #D2D2D2" });
					//}else{
					//	 $('#secondLanguage').css({ "border":"2px solid red"  });
					//}
					
					
					if(birthPlace !=''){
						$('#birthPlace').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#birthPlace').css({ "border":"2px solid red"  });
					}
					if(birthCountry !=''){
						$('#birthCountry').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#birthCountry').css({ "border":"2px solid red"  });
					}
					if(birthState !=''){
						$('#birthState').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#birthState').css({ "border":"2px solid red"  });
					}
					if(residentCategory !=''){
						$('#residentCategory').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#residentCategory').css({ "border":"2px solid red"  });
					}
					if(religions !=''){
						$('#religions').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#religions').css({ "border":"2px solid red"  });
					}
					if(castCatg !=''){
						$('#castCatg').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#castCatg').css({ "border":"2px solid red"  });
					}
					
					if(applicantMobileNo !=''){
						$('#applicantMobileNo').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#applicantMobileNo').css({ "border":"2px solid red"  });
					}

					if(applicantEmail !=''){
						$('#applicantEmail').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#applicantEmail').css({ "border":"2px solid red"  });
					}
					if(currentAddressLine1 !=''){
						$('#currentAddressLine1').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#currentAddressLine1').css({ "border":"2px solid red"  });
					}
					if(currentAddressLine2 !=''){
						$('#currentAddressLine2').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#currentAddressLine2').css({ "border":"2px solid red"  });
					}
					if(currentCityName !=''){
						$('#currentCityName').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#currentCityName').css({ "border":"2px solid red"  });
					}
					if(currentCountryName !=''){
						$('#currentCountryName').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#currentCountryName').css({ "border":"2px solid red"  });
					}
					if(tempAddrstate !=''){
						$('#tempAddrstate').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#tempAddrstate').css({ "border":"2px solid red"  });
					}
					if(otherTempAddrState !=''){
						$('#otherTempAddrState').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#otherTempAddrState').css({ "border":"2px solid red"  });
					}
					if(currentAddressZipCode !=''){
						$('#currentAddressZipCode').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#currentAddressZipCode').css({ "border":"2px solid red"  });
					}
					
					if(currentStreet !=''){
						$('#currentStreet').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#currentStreet').css({ "border":"2px solid red"  });
					}
					
					
					if(fatherName !=''){
						$('#fatherName').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#fatherName').css({ "border":"2px solid red"  });
					}
					if(motherName !=''){
						$('#motherName').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#motherName').css({ "border":"2px solid red"  });
					}

					if(titleOfFather !=''){
						$('#titleOfFather').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#titleOfFather').css({ "border":"2px solid red"  });
					}
					if(titleOfMother !=''){
						$('#titleOfMother').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#titleOfMother').css({ "border":"2px solid red"  });
					}

					
					
					
					//raghu
					if(tempAddrdistrict !=''){
						$('#tempAddrdistrict').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#tempAddrdistrict').css({ "border":"2px solid red"  });
					}
					if(otherTempAddrDistrict !=''){
						$('#otherTempAddrDistrict').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#otherTempAddrDistrict').css({ "border":"2px solid red"  });
					}
					
					if(fatherMobile !=''){
						$('#fatherMobile').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#fatherMobile').css({ "border":"2px solid red"  });
					}
					if(fatherMobile1 !=''){
						$('#fatherMobile1').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#fatherMobile1').css({ "border":"2px solid red"  });
					}
					
					
					if(applicantMobileCode !=''){
						$('#applicantMobileCode').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#applicantMobileCode').css({ "border":"2px solid red"  });
					}
					
					if(otherReligion !=''){
						$('#otherReligion').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#otherReligion').css({ "border":"2px solid red"  });
					}
					
					if(otherCastCatg !=''){
						$('#otherCastCatg').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#otherCastCatg').css({ "border":"2px solid red"  });
					}
					
					if(fatherOccupation !=''){
						$('#fatherOccupation').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#fatherOccupation').css({ "border":"2px solid red"  });
					}
					if(otherOccupationFather !=''){
						$('#otherOccupationFather').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#otherOccupationFather').css({ "border":"2px solid red"  });
					}
					
					if(subreligion !=''){
						$('#subreligion').css({ "border": "3px solid #D2D2D2" });
					}else{
						 $('#subreligion').css({ "border":"2px solid red"  });
					}
						 
					
					
  
				  mandatoryFlag = true;
				 } 
				
				
				//main metod of submit if no errors are there
				else if(mandatoryFlag === false){
					document.getElementById("focusValue").value=null;
					document.getElementById("method").value="savePersonalData";
					document.loginform.submit();
						
				 }
				
				//errors are there make show message on screen
					if(mandatoryFlag){
						if(firstNameId=="") {
							document.getElementById("firstNameId").focus();
						}else if(dateOfBirth=="") {
							document.getElementById("dateOfBirth").focus();
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
						
						if(applicantMobileNo=="") {
							document.getElementById("applicantMobileNo").focus();
						}
						if(applicantEmail=="") {
							document.getElementById("applicantEmail").focus();
						}
						
						
						
						
						if(currentAddressLine1=="") {
							document.getElementById("currentAddressLine1").focus();
						}
						if(currentAddressLine2=="") {
							document.getElementById("currentAddressLine2").focus();
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
						
						if(currentAddressZipCode=="") {
							document.getElementById("currentAddressZipCode").focus();
						}
						
						
						if(permanentAddressLine1=="") {
							document.getElementById("permanentAddressLine1").focus();
						}
						if(permanentAddressLine2=="") {
							document.getElementById("permanentAddressLine2").focus();
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

						
						//raghu
						
						
						if(tempAddrdistrict=="") {
							document.getElementById("tempAddrdistrict").focus();
						}
						if(otherTempAddrDistrict=="") {
							document.getElementById("otherTempAddrDistrict").focus();
						}
						if(permAddrdistrict=="") {
							document.getElementById("permAddrdistrict").focus();
						}
						if(otherPermAddrDistrict=="") {
							document.getElementById("otherPermAddrDistrict").focus();
						}
						if(fatherMobile=="") {
							document.getElementById("fatherMobile").focus();
						}
						
						if(fatherMobile1=="") {
							document.getElementById("fatherMobile1").focus();
						}
						
						
						if(parentAddressLine1=="") {
							document.getElementById("parentAddressLine1").focus();
						}
						if(parentMobile=="") {
							document.getElementById("parentMobile").focus();
						}
						if(parentMobile1=="") {
							document.getElementById("parentMobile1").focus();
						}
						if(parentAddressLine2=="") {
							document.getElementById("parentAddressLine2").focus();
						}
						if(parentCountryName=="") {
							document.getElementById("parentCountryName").focus();
						}
						if(parentAddressZipCode=="") {
							document.getElementById("parentAddressZipCode").focus();
						}

						if(parentState=="") {
							document.getElementById("parentState").focus();
						}
						if(otherParentAddrState=="") {
							document.getElementById("otherParentAddrState").focus();
						}
						
						if(parentCityName=="") {
							document.getElementById("parentCityName").focus();
						}
						
						if(applicantMobileCode=="") {
							document.getElementById("applicantMobileCode").focus();
						}
						
						if(fatherOccupation=="") {
							document.getElementById("fatherOccupation").focus();
						}
						if(otherOccupationFather=="") {
							document.getElementById("otherOccupationFather").focus();
						}
						
						
						
						//error check for parent or guardian phone
					
							
						
						
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

	

	