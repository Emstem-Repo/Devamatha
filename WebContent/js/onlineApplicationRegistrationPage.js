function isNumberKey(evt) {
	
	var charCode = (evt.which) ? evt.which : event.keyCode
			if((evt.ctrlKey )  ){
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
    //On key up in texbox's hide error messages for required fields
    $('#email').next('.tooltip_outer').hide();
    $('#mobile').next('.tooltip_outer').hide();
    $('#registerDateOfBirth').next('.tooltip_outer').hide();

    $('#email').keyup(function(){
    	 $('#email').next('.tooltip_outer').hide();
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
    	 $('#email').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Email is required </div></div>").show("slow");
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
    $('#mobile').next('.tooltip_outer').hide();
    if($('#mobile').val().length == 0){
    	$('#mobile').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Mobile is required </div></div>").show("slow");
        empty_count=1;
    }else{
            if($('#mobile').val().length != 10)
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
    	$('#registerDateOfBirth').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Date is required </div></div>").show("slow");
        empty_count=1;
    }
    if(empty_count != 1){
    	document.getElementById("method").value="registerOnlineApplication";
    }else{
		return false;
    }
	});
	});
function checkNumeric(evt) {
    return false;  
	}

function loginPage(){
	document.getElementById("method").value="initOnlineApplicationLogin";
	document.studentOnlineApplicationForm.submit();
	//document.location.href="onlineApplicationLogin.do?method=initOnlineApplicationLogin";
}
function checkMail(val){
	
	 var filter1 = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9] {1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}| [0-9]{1,3})(\]?)$/;
     if(filter1.test($('#email').val())) {
    	 	var args ="method=checkMail&emailId="+val;
 			var url ="onlineApplicationLogin.do";
    	 	//var url ="uniqueIdRegistration.do";
 			
 			requestOperationProgram(url,args,displayResult);
     }else{
    	 return false;
	}
}
function displayResult(req) {
	var responseObj = req.responseXML.documentElement;
	var childNodes = responseObj.childNodes;
	var items = responseObj.getElementsByTagName("option");
	var label, value;
	for ( var i = 0; i < items.length; i++) {
		label = items[i].getElementsByTagName("optionlabel") [0].firstChild.nodeValue;
		value = items[i].getElementsByTagName("optionvalue") [0].firstChild.nodeValue;
	}
	var msg = '';
	$('#email').next('.tooltip_outer').hide();
	if(value == 'true'){
		msg = $('#email').after('<div class="tooltip_outer"> <div class="arrow-left"></div> <div class="tool_tip">Email Id already registered </div></div>').show("slow");
	}
	
	document.getElementById("checkMail").innerHTML = msg;
}
function cancel(){
	document.getElementById("email").value = "";
	document.getElementById("mobile").value = "";
	document.getElementById("registerDateOfBirth").value = "";
}