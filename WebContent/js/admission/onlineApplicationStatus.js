
	function downloadAdmitCard(applicationNo, courseId, interviewTypeId) {
		document.getElementById("mode").value= "downloadInterviewCard";
		var args =  "method=setDateToForm&applicationNo="+applicationNo+"&courseId="+courseId+"&interviewTypeId="+interviewTypeId;
		var url = "AdmissionStatus.do";
		requestOperationProgram(url, args, result);
		
	}
	function downloadAdmissionCard(applicationNo, courseId, interviewTypeId) {
		document.getElementById("mode").value= "downloadAdmissionCard";
		var args = "method=setDateToForm&applicationNo="+applicationNo+"&courseId="+courseId+"&interviewTypeId="+interviewTypeId;
		var url = "AdmissionStatus.do";
		requestOperationProgram(url, args, result);
		
	}	
	function downloadApplication(applnNo, appliedYear) {
		document.getElementById("mode").value= "downloadApplication";
		var args =  "method=setDateToForm&applicationNo="+applnNo +"&appliedYear="+appliedYear+"&displaySemister="+true;
		var url = "AdmissionStatus.do";
		requestOperationProgram(url, args, result);
	}	
	function downloadAcknowledgement(applnNo, appliedYear) {
		document.getElementById("mode").value= "downloadAcknowledgement";
		var args =  "method=setDateToForm&applicationNo="+applnNo +"&appliedYear="+appliedYear;
		var url = "AdmissionStatus.do";
		requestOperationProgram(url, args, result);
	}	
	function result(req){
		var responseObj = req.responseXML.documentElement;
		var childNodes = responseObj.childNodes;
		var items = responseObj.getElementsByTagName("option");
		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel") [0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue") [0].firstChild.nodeValue;
		}
		if(value=="true"){
			var mode1 = document.getElementById("mode").value;
			var url = "AdmissionStatus.do?method="+mode1;
			window .open(url, " ", "left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1, location=no");
		}
	}
	function resetMessages() {
		resetErrMsgs();
	}		