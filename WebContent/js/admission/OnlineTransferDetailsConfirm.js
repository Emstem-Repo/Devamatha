function submitConfirmCancelButton1(){
	     document.getElementById("method").value="forwardCancelPage";
	    document.onlineApplicationForm.submit();
	 }

function submitAdmissionForm1(val){
    document.getElementById("method").value=val;
    document.onlineApplicationForm.submit();
}