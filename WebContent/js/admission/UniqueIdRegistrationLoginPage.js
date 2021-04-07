

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
    
    $('#uniqueId').next('.tooltip_outer').hide();
    $('#loginDateOfBirth').next('.tooltip_outer').hide();

    
    $('#uniqueId').keyup(function(){
      	 $('#uniqueId').next('.tooltip_outer').hide();
      });
    $('#loginDateOfBirth').keyup(function(){
     	 $('#loginDateOfBirth').next('.tooltip_outer').hide();
     });

// click on login button
$('#login_validate').click(function(e) 
		{
		    var empty_count=0; // variable to store error occured status
		    $('#uniqueId').next('.tooltip_outer').hide();

		    if($('#uniqueId').val().length == 0){
		    	// $('#uniqueId').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Password is Required </div></div>").show("slow");
		         empty_count=1;
		    }
		    $('#loginDateOfBirth').next('.tooltip_outer').hide();
		    if($('#loginDateOfBirth').val().length == 0){
		    	//$('#loginDateOfBirth').after("<div class='tooltip_outer'> <div class='arrow-left'></div> <div class='tool_tip'>Date is required </div></div>").show("slow");
		        empty_count=1;
		    }
		    if(empty_count != 1){
		    	
		    	document.getElementById("method").value="loginOnlineApplication";
		    }else{
		    	document.getElementById("errorMessage1").innerHTML="Enter UserId & Password";
		    	return false;
		    }
		    
			});
	});
function register(){
	var flag= document.getElementById("offlinePage").value;
	if(flag=='true'){
		document.getElementById("method").value="initOfflineApplicationRegistration";
		document.uniqueIdRegistrationForm.submit();
	}else{
		document.getElementById("method").value="initOnlineApplicationRegistration";
		document.uniqueIdRegistrationForm.submit();
	}
	//document.location.href="onlineApplicationLogin.do?method=initOnlineApplicationRegistration";
}
function forgotUniqueId(){
	document.getElementById("method").value="initOnlineApplicationForgotUniqueId";
	document.uniqueIdRegistrationForm.submit();
	//document.location.href="onlineApplicationLogin.do?method=initOnlineApplicationForgotUniqueId";
}
function cancel(){
	document.getElementById("uniqueId").value = "";
	document.getElementById("loginDateOfBirth").value = "";
	resetErrMsgs();
}
function checkNumeric(event) {
	return false;
	}

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

$(document).ready(function(){
	$('#loginDateOfBirth').bind("cut copy paste",function(e) {
		e.preventDefault();
	});
});