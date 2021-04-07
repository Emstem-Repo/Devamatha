
function proceed(){
	var offline=document.getElementById("offlinePage").value;
	if(offline!=null && offline=='false'){
		document.location.href ="onlineApplicationSubmit.do?method=initStartPage";
	}
	else
	{
		document.location.href ="onlineApplicationSubmit.do?method=initOfflineStartPage";
	}
	//document.location.href="onlineApplicationSubmit.do?method=initOutsideSinglePageAccess";
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
	//document.location.href="onlineApplicationLogin.do?method=initOnlineApplicationLogin";
}


function downloadChallan() {
	var url = "uniqueIdRegistration.do?method=forwardChallanTemplate";
	window .open(url, " ", "left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1, location=no");
	
}

function downloadChallanTieupBank() {
	var url = "uniqueIdRegistration.do?method=forwardChallanTemplateTieupBank";
	window .open(url, " ", "left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1, location=no");
	
}

function downloadChallanOtherBank() {
	var url = "uniqueIdRegistration.do?method=forwardChallanTemplateOtherBank";
	window .open(url, " ", "left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1, location=no");
	
}

