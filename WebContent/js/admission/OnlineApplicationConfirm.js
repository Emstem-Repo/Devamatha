function openPrintPage() {
	var url="onlineApplicationSubmit.do?method=forwardPrintWindow";
	window.open(url,'PrintApplication','left=20,top=20,width=800,height=1000,toolbar=1,resizable=0,scrollbars=1');		
}
function fnUnloadHandler() {  
	document.location.href = "LogoutAction.do?method=logout";	
}  

function submitFORCANCEL(method){
	document.location.href= method;
}

function submitFORWindow(method){
	var url=method;
	window.open(url,'OnlineStatus','left=20,top=20,width=800,height=1000,toolbar=1,resizable=0,scrollbars=1');
}
function onlineApplicationStatus(){
	//document.location.href="uniqueIdRegistration.do?method=initOnlineApplicationLogin";
	document.location.href="onlineApplicationSubmit.do?method=logoutFromOnlineApplication";
}