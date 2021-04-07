
function proceed(){
	document.location.href="admissionFormSubmit.do?method=initOutsideSinglePageAccess";
}
function loginPage(){
	document.getElementById("method").value="initOnlineApplicationLogin";
	document.studentOnlineApplicationForm.submit();
	//document.location.href="onlineApplicationLogin.do?method=initOnlineApplicationLogin";
}
