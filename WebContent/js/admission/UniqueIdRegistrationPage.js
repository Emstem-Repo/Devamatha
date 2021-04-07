function isNumberKey(evt) {
	
	var charCode = (evt.which) ? evt.which : event.keyCode;
			if((evt.ctrlKey)  ){
				return true;
			}
    if ((charCode > 47 && charCode < 58) || charCode == 8){ 
        return true; 
    } 
    return false;  
}
//Onpage load
$(document).ready(function()
{
	 var urlImage="images/LoginImages/REGISTER-FOR-APPLICATION.jpg?"+new Date().getTime();
		$('#wrapper').css("background-image", "url("+urlImage+")");
		$('#wrapper').css("background-repeat", "no-repeat");
		$('#wrapper').css("background-position", "center");
		$('#wrapper').css("background-attachment", "fixed");
		$('#wrapper').css("background-size", "cover");
		
    //On key up in texbox's hide error messages for required fields
    $('#email').next('.tooltip_outer').hide();
    $('#mobile').next('.tooltip_outer').hide();
    $('#registerDateOfBirth').next('.tooltip_outer').hide();

    $('#email').keyup(function(){
    	 $('#email').next('.tooltip_outer').hide();
    });
    
    $('#confirmEmailId').keyup(function(){
   	 $('#confirmEmailId').next('.tooltip_outer').hide();
   });
    
    //On key up for mobile number avoid non-numeric characters
    $('#mobile').keyup(function()
    {
        $('#mobile').next('.tooltip_outer').hide();
    });
    $('#registerDateOfBirth').click(function(e){
      	 $('#registerDateOfBirth').next('.tooltip_outer').hide();
      });
    
    
    
    
 // click on register button
$('#register_validate').click(function(e) 
{
    var empty_count=0; // variable to store error occured status
    
    $('#email').next('.tooltip_outer').hide();
    if($('#email').val().length == 0){
    	// $('#email').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Email is required </div></div>").show("slow");
         empty_count=1;
    }else{
             var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9] {1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}| [0-9]{1,3})(\]?)$/;
             if(filter.test($('#email').val()) === false) {
                 $('#email').after('<div class="tooltip_outer"> <div class="arrow-left"></div> <div class="tool_tip">Invalid Email </div></div>').show("slow");
                 empty_count=1;
             }
             else
             {
                 $('#email').next('.tooltip_outer').hide();
             }
    }
    
    $('#confirmEmailId').next('.tooltip_outer').hide();
    if($('#confirmEmailId').val().length == 0){
    	// $('#email').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Email is required </div></div>").show("slow");
         empty_count=1;
    }else{
             var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9] {1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}| [0-9]{1,3})(\]?)$/;
             if(filter.test($('#confirmEmailId').val()) === false) {
                 $('#confirmEmailId').after('<div class="tooltip_outer"> <div class="arrow-left"></div> <div class="tool_tip">Invalid ConfirmEmailId </div></div>').show("slow");
                 empty_count=1;
             }
             else
             {
                 $('#confirmEmailId').next('.tooltip_outer').hide();
             }
    }
    
    
    var email=  $('#email').val();
	var confirmEmailId=  $('#confirmEmailId').val();
	$('#confirmEmailId').next('.tooltip_outer').hide();
	if(email!=null && confirmEmailId!=null){
	 if(email!=confirmEmailId){
	    	 $('#confirmEmailId').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Email and ConfirmEmailId should same</div></div>").show("slow");
	         empty_count=1;
	    }
	}
	
	
	 
    $('#mobile').next('.tooltip_outer').hide();
    if($('#mobile').val().length == 0){
    	//$('#mobile').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Mobile is required </div></div>").show("slow");
        empty_count=1;
    }else{
            if($('#mobile').val().length != 10 && $('#mobilecode').val() == "91")
            {
                $('#mobile').after('<div class="tooltip_outer"> <div class="arrow-left"></div> <div class="tool_tip">Mobile should be 10 digits </div></div>').show("slow");
                empty_count=1; 
            }
            else
            {
                $('#mobile').next('.tooltip_outer').hide();
            }
    }
    $('#registerDateOfBirth').next('.tooltip_outer').hide();
    if($('#registerDateOfBirth').val().length == 0){
    	//$('#registerDateOfBirth').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Date is required </div></div>").show("slow");
        empty_count=1;
    }
    
    $('#applicantName').next('.tooltip_outer').hide();
    if($('#applicantName').val().length == 0){
    	//$('#applicantName').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Name is required </div></div>").show("slow");
        empty_count=1;
    }
    
    $('#programTypeId').next('.tooltip_outer').hide();
    if($('#programTypeId').val().length == 0){
    	//$('#programTypeId').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Program is required </div></div>").show("slow");
        empty_count=1;
    }
    
    $('#residentCategory').next('.tooltip_outer').hide();
    if($('#residentCategory').val().length == 0){
    	//$('#residentCategory').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>ResidentCategory is required </div></div>").show("slow");
        empty_count=1;
    }
    
    $('#mobilecode').next('.tooltip_outer').hide();
    if($('#mobilecode').val().length == 0){
    	//$('#mobilecode').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Mobile Code is required </div></div>").show("slow");
        empty_count=1;
    }
    
    
    if(empty_count != 1){
    	
    	$.confirm({
    		
    		'message'	: 'WARNING: Please check your email ID and mobile number as your User ID & Password will be sent to these.',
    		'buttons'	: {
    			'Ok'	: {
    				'class'	: 'blue',
    				'action': function(){
    					$.confirm.hide();
    					
    					document.getElementById("method").value="registerOnlineApplication";
    			    	
    					document.uniqueIdRegistrationForm.submit();
    					document.getElementById("buttons").style.display="none";
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

    	return false;
    	
    }else{
    	
    	
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

   /* var offlineMsg=$("#offlinePage").val();
	if(offlineMsg!=null && offlineMsg=='false'){
		$("#appMessage").html("This link is for candidates to fill and apply through the online application.");
	}else{
		$("#appMessage").html("This link is for candidates who have obtained the direct mode application from Office of Admissions to fill and submit the data online.");
	}*/
	});
function checkNumeric(evt) {
    return false;  
	}

function loginPage(){
	var offline=document.getElementById("offlinePage").value;
	if(offline!=null && offline=='false'){
		document.getElementById("method").value="initOnlineApplicationLogin";
		document.uniqueIdRegistrationForm.submit();
	}else{
		document.getElementById("method").value="initOfflineApplicationLogin";
		document.uniqueIdRegistrationForm.submit();
	}
}
function checkMail(val){
	
	 var filter1 = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9] {1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}| [0-9]{1,3})(\]?)$/;
     if(filter1.test($('#email').val())) {
    	 	var args ="method=checkMail&emailId="+val;
 			//var url ="onlineApplicationLogin.do";
    	 	var url ="uniqueIdRegistration.do";
 			
 			requestOperationProgram(url,args,displayResult);
     }else{
    	 return false;
	}
}
function displayResult(req) {
	var responseObj = req.responseXML.documentElement;
	//var childNodes = responseObj.childNodes;
	var items = responseObj.getElementsByTagName("option");
	var  value="";
	for ( var i = 0; i < items.length; i++) {
		//label = items[i].getElementsByTagName("optionlabel") [0].firstChild.nodeValue;
		value = items[i].getElementsByTagName("optionvalue") [0].firstChild.nodeValue;
	}
	var msg = '';
	$('#email').next('.tooltip_outer').hide();
	if(value == 'true'){
		msg = $('#email').after('<div class="tooltip_outer"> <div class="arrow-left"></div> <div class="tool_tip">Email Id already registered </div></div>').show("slow");
	}
	
	//document.getElementById("checkMail").innerHTML = msg;
}
function reset(){
	document.getElementById("email").value = "";
	document.getElementById("mobile").value = "";
	document.getElementById("registerDateOfBirth").value = "";
}

/*
 * 
 * 
var serverDownMessage=$('#serverDownMessage').val();
if(serverDownMessage!=null && serverDownMessage!=""){
	 $(".ZebraDialog_Title a.ZebraDialog_Close").hide();
	$.Zebra_Dialog('<strong>'+serverDownMessage+'</strong>',{
		'title':    'Alert',
		'buttons':  ['Hide'],
		'keyboard':false,
		'overlay_close':false,
		'show_close_button':false
	});
}
var offlineMsg=$("#offlinePage").val();
var htm="";
if(offlineMsg!=null && offlineMsg=='false'){
	htm="This link is for candidates to fill and apply through the online application.";
}else{
	htm="This link is for candidates who have obtained the direct mode application from Office of Admissions to fill and submit the data online.";
}
$.confirm({
	'message'	: '<b>'+htm+'</b>',
	'buttons'	: {
		'Close'	: {
			'class'	: 'blue',
			'action': function(){
				$.confirm.hide();
			}
		}
	}
});
*/

$(document).ready(function(){
	$('#registerDateOfBirth').bind("cut copy paste",function(e) {
		e.preventDefault();
	});
});

function showIsMgquota(){
	var isMgquota = document.getElementById("ismgquotaYes").checked;
	var isAided = document.getElementById("aidedYes").checked;
	if(isMgquota==true && isAided==true){
		$.confirm({
	 		'message'	: 'Those who wish to apply for Aided management quota, Collect Management quota form college & upload the filled in form.',
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
	if(isMgquota==true && isAided==false){
		$.confirm({
	 		'message'	: 'Those who wish to apply for Unaided management quota, download managemet quatoa form from prints and download menu upload the filled in form.',
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
}