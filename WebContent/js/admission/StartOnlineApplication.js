

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
isCtrl = e.ctrlKey;
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


		/*if(document.getElementById("paymentSuccess").value==true || document.getElementById("paymentSuccess").value=="true"){
			 $(".mainDisplay :input").attr( 'disabled', 'disabled'); 
			 $("#mainButton").hide();
		}
		else{
			$("#mainButton").show();
			$(".mainDisplay :input").removeAttr('disabled');
		}*/
		
function resetData(){
	resetFieldAndErrMsgs();
}

var blinkLength = 3000;
function blink1(){
		document.getElementById('div3').style.display = "block";
		t1=setTimeout("blink2()",blinkLength);
}
function blink2(){
		document.getElementById('div3').style.display = "none";
		t2=setTimeout("blink1()",500);
}

/*function checkCourseOpen(courseId)
{
	   if(courseId!=null && courseId!="")
	   {
		  var url = "onlineApplicationSubmit.do";
		  var args = "method=getCourseOpenDetails&courseId="+courseId;
		   requestOperationProgram(url, args, displayHostelDetails);
	   if(msg!=null && msg!="" && msg!='true')
	   {
		   Alert(msg);
	   }
}

}*/
function displayAlert(courseId)
{
	var url = "onlineApplicationSubmit.do";
	var args = "method=getCourseOpenDetails&courseId="+courseId;
	requestOperationProgram(url, args, showAlertForSession);
/*	$.ajax({
	      type: "post",
	      url: "onlineApplicationSubmit.do?method=getCourseOpenDetails",
	      msg: {courseId:courseId},
	      success:function(msg) 
	      {
	    	  if (msg!='true' && msg!="") {
	    		  $.confirm({
						'message'	: msg,
						'buttons'	: {
						'Close'	: {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();
							}
							}
					}
				});
	    		// document.getElementById("progPref1").value="";
				 document.getElementById("coursePref1").value="";
			}
	      }
	  });*/
	
	var testIds='<%=KPPropertiesConfiguration.PGDM_LAVASA_GHAZ_COURSE_ID%>'.split(',');
	if(testIds.length>0){
		for(var i=0; i<testIds.length;i++){
			if(courseId==testIds[i]){
				 $("#lavas_ghaz").dialog({
					 	resizable: false,
				        dialogClass: "noclose",
				        modal: true,
				        height: 200,
				        title: "Alert",
				        width: 550,
				        close: function() {
		   	    	$("#lavas_ghaz").dialog("destroy");
		   	    	$("#lavas_ghaz").hide();
		            }
				 });
			}
		}
	}
	if(courseId=="340"){
		document.getElementById('340').style.display = "block";
		document.getElementById('341').style.display = "none";
		document.getElementById('342').style.display = "none";
		document.getElementById('359').style.display = "none";
		document.getElementById('20').style.display = "none";
		 $("#timer3").dialog({
		        resizable: false,
		        dialogClass: "noclose",
		        modal: true,
		        height: 200,
		        title: "Alert",
		        width: 550,
		        close: function() {
   	    	$("#timer3").dialog("destroy");
   	    	$("#timer3").hide();
            }
		    });
		}else if(courseId=="341"){
			document.getElementById('341').style.display = "block";
			document.getElementById('340').style.display = "none";
			document.getElementById('342').style.display = "none";
			document.getElementById('359').style.display = "none";
			document.getElementById('20').style.display = "none";
			 $("#timer3").dialog({
			        resizable: false,
			        dialogClass: "noclose",
			        modal: true,
			        height: 220,
			        title: "Alert",
			        width: 550,
			        close: function() {
	    	    	$("#timer3").dialog("destroy");
	    	    	$("#timer3").hide();
	             }
			    });
			
			
		}else if(courseId=="342"){
			document.getElementById('341').style.display = "none";
			document.getElementById('340').style.display = "none";
			document.getElementById('342').style.display = "block";
			document.getElementById('359').style.display = "none";
			document.getElementById('20').style.display = "none";
			 $("#timer3").dialog({
			        resizable: false,
			        dialogClass: "noclose",
			        modal: true,
			        height: 220,
			        title: "Alert",
			        width: 550,
			        close: function() {
	    	    	$("#timer3").dialog("destroy");
	    	    	$("#timer3").hide();
	             }
			    });
		}else if(courseId=="359" || courseId=="358" ||courseId=="351"
		 || courseId=="352" || courseId=="360" || courseId=="361" || courseId=="334" || courseId=="362" || courseId=="364"){	
			document.getElementById('341').style.display = "none";
			document.getElementById('340').style.display = "none";
			document.getElementById('342').style.display = "none";
			document.getElementById('359').style.display = "block";
			document.getElementById('20').style.display = "none";
			 $("#timer3").dialog({
			        resizable: false,
			        dialogClass: "noclose",
			        modal: true,
			        height: 200,
			        title: "Alert",
			        width: 550,
			        close: function() {
	    	    	$("#timer3").dialog("destroy");
	    	    	$("#timer3").hide();
	             }
			    });
		}else if(courseId=="20"){	
			document.getElementById('341').style.display = "none";
			document.getElementById('340').style.display = "none";
			document.getElementById('342').style.display = "none";
			document.getElementById('359').style.display = "none";
			document.getElementById('20').style.display = "block";
			 $("#timer3").dialog({
			        resizable: false,
			        dialogClass: "noclose",
			        modal: true,
			        height: 200,
			        title: "Alert",
			        width: 550,
			        close: function() {
	    	    	$("#timer3").dialog("destroy");
	    	    	$("#timer3").hide();
	             }
			    });
		}
	
	$.ajax({
		type:"post",
		url:"onlineApplicationSubmit.do?method=getIntrAdmissionSessionDetails",
		data:{courseId:courseId},
		success:function(resp){
			var parsedJson=jQuery.parseJSON(resp);
			 var session=parsedJson.Admission;
			 var isNotEmpty=jQuery.isEmptyObject(session);
			 if(!isNotEmpty){
				 var destination = document.getElementById("interAdmissionSession");
					for (var x1 = destination.options.length - 1; x1 > 0; x1--) {
						destination.options[x1] = null;
					}
				 destination.options[0] = new Option("- Select -", "");
				 var i=1;
				 $.each( session, function( key, value ) {
					 destination.options[i] = new Option(key, value);
					 i=i+1;
					 });
				 $("#showIntrAdmSession").show();
				 $("#isInterDisciplinary").val("Yes");
			 }else{
				 $("#showIntrAdmSession").hide();
				 $("#isInterDisciplinary").val("No");
			 }
		}
	});
}
function showAlertForSession(req) {
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
			if(value[I].firstChild!=null){
			var msg = value[I].firstChild.nodeValue;
			if (msg!='true' && msg!="") {
				 $.confirm({
						'message'	: msg,
						'buttons'	: {
						'Close'	: {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();
							}
							}
					}
				});
	    		// document.getElementById("progPref1").value="";
				 document.getElementById("coursePref1").value="";
			}
		}
	}
}
}
function cancel(){
	document.location.href="uniqueIdRegistration.do?method=loginOnlineApplication&propertyName=back";
}

function submitDialogue(){
	$("#timer3").dialog("close");
  	$("#timer3").hide();
}
function cancelDialogue()
{
	document.getElementById("coursePref1").value="";
 	$("#timer3").dialog("close");
  	$("#timer3").hide();
}
function cancelDialogueLavasGhazia()
{
	document.getElementById("coursePref1").value="";
 	$("#lavas_ghaz").dialog("close");
  	$("#lavas_ghaz").hide();
}
$("#timer3").hide();
$("#showIntrAdmSession").hide();
var prgmTypeHidden=$.trim($("#programType").val());
if(prgmTypeHidden!=""){
	$("#programTypeId").val(prgmTypeHidden);
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
	
	if($("#submitMyMethod").length){
		$("#submitMyMethod").show();
	}	
	$('#submitMyMethod').click(function(e){
		$(this).hide();
			var programTypeId=  $('#programTypeId').val();
			var progPref1=  $('#progPref1').val();
			var coursePref1=  $('#coursePref1').val();
			var applicantName=  $('#applicantName').val();
			var dateOfBirth=  $('#dateOfBirth').val();
			var residentCategory=  $('#residentCategory').val();
			var email=  $('#email').val();
			var confirmEmailId=  $('#confirmEmailId').val();
			var mobileNo1=  $('#mobileNo1').val();
			var mobileNo2=  $('#mobileNo2').val();
			var interAdmissionSession="Not";
			if($("#isInterDisciplinary").val()=='Yes'){
				interAdmissionSession=$('#interAdmissionSession').val();
			}
			var mandatoryFlag = false;
			if(programTypeId =='' || progPref1 =='' || coursePref1 =='' || applicantName=="" || dateOfBirth =='' 
				|| residentCategory=='' || email=='' || confirmEmailId=='' || mobileNo1=='' || mobileNo2=='' || interAdmissionSession=='') {
				if(programTypeId !=''){
					$("#programTypeId").removeClass('errorClass');
				}else{
					$('#programTypeId').css({ "border":"2px solid red"  });
				}
				if(progPref1 !=''){
					$('#progPref1').css('border', '0');
				}else{
					$('#progPref1').css({ "border":"2px solid red"  });
				}
				if(coursePref1 !=''){
					$('#coursePref1').css('border', '0');
				}else{
					$('#coursePref1').css({ "border":"2px solid red"  });
				}
				if(applicantName !=''){
					$('#applicantName').css('border', '0');
				}else{
					 $('#applicantName').css({ "border":"2px solid red"  });
				}
				if(dateOfBirth !=''){
					$('#dateOfBirth').css('border', '0');
				}else{
					 $('#dateOfBirth').css({ "border":"2px solid red"  });
				}
				if(residentCategory !=''){
					$('#residentCategory').css('border', '0');
				}else{
					$('#residentCategory').css({ "border":"2px solid red"  });
				}
				if(email !=''){
					$('#email').css('border', '0');
				}else{
					$('#email').css({ "border":"2px solid red"  });
				}
				if(confirmEmailId !=''){
					$('#confirmEmailId').css('border', '0');
				}else{
					$('#confirmEmailId').css({ "border":"2px solid red"  });
				}
				if(mobileNo1 !=''){
					$('#mobileNo1').css('border', '0');
				}else{
					$('#mobileNo1').css({ "border":"2px solid red"  });
				}
				if(mobileNo2 !=''){
					$('#mobileNo2').css('border', '0');
				}else{
					$('#mobileNo2').css({ "border":"2px solid red"  });
				}
				if ($("#isInterDisciplinary").val()=='Yes') {
					if (interAdmissionSession !='') {
						$("#interAdmissionSession").css('border', '0');
					}else{
						$("#interAdmissionSession").css({ "border":"2px solid red"  });
					}
				}
			  mandatoryFlag = true;
			 } else{
				  // e.preventDefault();
				   document.getElementById("method").value="submitOnlineApply";
				   document.onlineApplicationForm.submit();
				  
			 }
				if(mandatoryFlag){
					$(this).show();
					if(programTypeId=="") {
						document.getElementById("programTypeId").focus();
					}
					if(progPref1=="") {
						document.getElementById("progPref1").focus();
					}

					if(coursePref1=="") {
						document.getElementById("coursePref1").focus();
					}
					if(applicantName=="") {
						document.getElementById("applicantName").focus();
					}
					if(dateOfBirth=="") {
						document.getElementById("dateOfBirth").focus();
					}
					if(residentCategory=="") {
						document.getElementById("residentCategory").focus();
					}
					if(email=="") {
						document.getElementById("email").focus();
					}
					if(confirmEmailId=="") {
						document.getElementById("confirmEmailId").focus();
					}
					if(mobileNo1=="") {
						document.getElementById("mobileNo1").focus();
					}
					if(mobileNo2=="") {
						document.getElementById("mobileNo2").focus();
					}
					if ($("#isInterDisciplinary").val()=='Yes') {
						if (interAdmissionSession =='') {
							document.getElementById("interAdmissionSession").focus();
						}
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
	 $("#applicantName").keyup( function (e) {
         var str = $(this).val();
         $("#applicantName").val(str.toUpperCase());
	 });
	});
var courseDraft=$("#isCourseDraft").val();
if(courseDraft=='Yes'){
	var courseName= $('#coursePref1 option:selected').text();
	$.confirm({
 		'message'	: 'You have a pending application for the course '+courseName+'<br> Please check the Status for details and complete the application.',
		'buttons'	: {
			'Ok'	: {
				'class'	: 'blue',
				'action': function(){
					$.confirm.hide();
				}
			}
		}
 	});
}
if($("#isInterDisciplinary").val()=='Yes'){
	 $("#showIntrAdmSession").show();
}else{
	$("#showIntrAdmSession").hide();
}