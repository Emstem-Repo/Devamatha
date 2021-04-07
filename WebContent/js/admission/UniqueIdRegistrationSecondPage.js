	
	function newCourse(){
		document.getElementById("newCourseLink").style.display = 'none';
		var uniqueId = document.getElementById("onlineApplicationUniqueId").value;
		if(uniqueId!=null && uniqueId!=0){
			document.getElementById("method").value="forwardBasicInfo";
				document.uniqueIdRegistrationForm.submit();
			//document.location.href ="onlineApplicationSubmit.do?method=initOutsideSinglePageAccess";
			//document.location.href ="onlineApplicationSubmit.do?method=initStartPage";
			
		}else{
			$.confirm({
				'message'	: 'Please register and get a Unique ID to proceed with new application',
				'buttons'	: {
					'Close'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
						}
					}
				}
			});
		}
	}


	
	function completeApplication(id,mode){
		var uniqueId = document.getElementById("onlineApplicationUniqueId").value;
		if(uniqueId!=null && uniqueId!=0 && mode=='Online'){
			document.getElementById("method").value="forwardOutsideAccess";
			document.getElementById("accoId").value=id;
			
					document.uniqueIdRegistrationForm.submit();
			//document.location.href ="onlineApplicationSubmit.do?method=initOutsideSinglePageAccess&admApplnId="+id+"&mode=CurrentID";
	    // document.location.href ="onlineApplicationSubmit.do?method=initStartPage&uniqueId="+uniqueId+"&admApplnId="+id+"";
			
		}else if(uniqueId!=null && uniqueId!=0 && mode!='Online'){
			//document.location.href ="onlineApplicationSubmit.do?method=initOfflinePageAccess&admApplnId="+id+"&mode=CurrentID";
		}else{
			$.confirm({
				'message'	: 'Please register and get a Unique ID to proceed with application',
				'buttons'	: {
					'Close'	: {
						'class'	: 'blue',
												'action': function(){
													$.confirm.hide();
						}
					}
				}
			});
		}
	}
	

	
	function getStatus() {
		document.getElementById("method").value="getStatusOfStudent";
		document.uniqueIdRegistrationForm.submit();
		//document.location.href = "AdmissionStatus.do?method=initOutsideAccessStatus";
	}


	function openPrintPage() {
		var appno=document.getElementById("applicationNo").value;
		var appyear=document.getElementById("appliedYear").value;
	
		var url="admissionFormSubmit.do?method=forwardPrintWindow&applnNo="+appno+"&applicationYear="+appyear+"&applicationNumber"+appno+"";
		window.open(url,'PrintApplication','left=20,top=20,width=800,height=1000,toolbar=1,resizable=0,scrollbars=1');		
	}
	 
	function printPDF(){
		var appno=document.getElementById("applicationNo").value;
		var appyear=document.getElementById("appliedYear").value;
		var url = "admissionFormSubmit.do?method=printApplnPDF&applnNo="+appno+"&applicationYear="+appyear+"&applicationNumber"+appno+"";
		window.open(url,'PrintApplicationPDF','left=20,top=20,width=800,height=1000,toolbar=1,resizable=0,scrollbars=1');		
		
	}

	 function onlineApplicationStatus(){
		 	document.getElementById("method").value="initOnlineApplicationLogin";
			document.uniqueIdRegistrationForm.submit();
			//document.location.href="uniqueIdRegistration.do?method=initOnlineApplicationLogin";
		}

	function getApplicationDetails(id){
		//document.location.href ="onlineApplicationSubmit.do?method=initStartPage";
	     document.location.href ="onlineApplicationSubmit.do?method=initOutsideSinglePageAccess&onlinePayment="+id+"&propertyName=NotRefund";
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
	
	
	
	function printPDFNew(){
		var url = "uniqueIdRegistration.do?method=printApplnPDF";
		window .open(url, " ", "left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1, location=no");
	
	}
	
	function openPrintPageNew() {
		var url = "uniqueIdRegistration.do?method=printAppln";
		window .open(url, " ", "left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1, location=no");
	}
	
	function redirectControl() {
		document.location.href = "AdmissionStatus.do?method=initAdmissionStatus";
	}
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
	function downloadAcknowledgement(applnNo, appliedYear) {
		
		document.getElementById("mode").value= "downloadAcknowledgement";
		var args =  "method=setDateToForm&applicationNo="+applnNo +"&appliedYear="+appliedYear+"&AJAX=true";
		var url = "AdmissionStatus.do";
		requestOperationProgram(url, args, result);
	}	
	function getDetails(val){
		
		getMode(val);
	 }
	 function getMode(val){
		 if(val=='status'){
				$("#displayStatus").show();
				$("#displayMail").hide();
				$("#displaySMS").hide();
				$("#DashBoardMessage").hide();
				$("#displayOnlinePayment").hide();
				$("#displayAlerts").hide();
				$("#displayProgrammesOffered").hide();
				$("#displayApplyCourses").hide();
			}else if(val=='dashBoard'){
				$("#DashBoardMessage").show();
				$("#displayStatus").hide();
				$("#displayMail").hide();
				$("#displaySMS").hide();
				$("#displayOnlinePayment").hide();
				$("#displayAlerts").hide();
				$("#displayProgrammesOffered").hide();
				$("#displayApplyCourses").hide();
			}else if(val=='mail'){
				$("#displayMail").show();
				$("#displayStatus").hide();
				$("#displaySMS").hide();
				$("#DashBoardMessage").hide();
				$("#displayOnlinePayment").hide();
				$("#displayAlerts").hide();
				$("#displayProgrammesOffered").hide();
				$("#displayApplyCourses").hide();
			}else if(val=='sms'){
				$("#displaySMS").show();
				$("#displayStatus").hide();
				$("#displayMail").hide();
				$("#DashBoardMessage").hide();
				$("#displayOnlinePayment").hide();
				$("#displayAlerts").hide();
				$("#displayProgrammesOffered").hide();
				$("#displayApplyCourses").hide();
			}else if(val=='onlinePaymentDetails'){
				$("#displayOnlinePayment").show();
				$("#displayStatus").hide();
				$("#displayMail").hide();
				$("#DashBoardMessage").hide();
				$("#displaySMS").hide();
				$("#displayAlerts").hide();
				$("#displayProgrammesOffered").hide();
				$("#displayApplyCourses").hide();
			}else if(val=='alerts'){
				$("#displayAlerts").show();
				$("#displayOnlinePayment").hide();
				$("#displayStatus").hide();
				$("#displayMail").hide();
				$("#DashBoardMessage").hide();
				$("#displaySMS").hide();
				$("#displayProgrammesOffered").hide();
				$("#displayApplyCourses").hide();
		 }	else if( val== 'programmesOffered'){
			 	$("#displayProgrammesOffered").show();
			 	$("#displayAlerts").hide();
				$("#displayOnlinePayment").hide();
				$("#displayStatus").hide();
				$("#displayMail").hide();
				$("#DashBoardMessage").hide();
				$("#displaySMS").hide();
				$("#displayApplyCourses").hide();
		 }else if( val== 'applyCourses'){
			 	$("#displayApplyCourses").show();
			 	$("#displayProgrammesOffered").hide();
			 	$("#displayAlerts").hide();
				$("#displayOnlinePayment").hide();
				$("#displayStatus").hide();
				$("#displayMail").hide();
				$("#DashBoardMessage").hide();
				$("#displaySMS").hide();
		 }
	 }
	 function getExpandMsg(count){
		 var  val = document.getElementById("displayMsg"+count).value ;
		 document.getElementById('showMsg').innerHTML = val;
		 dialog = $("#showMsg").dialog({
			 height: 400,
			 width: 700,
			 title:"Body Message",
			 modal: true,
			 close: function() {
				 $("#showMsg").dialog("destroy");
	             $("#showMsg").hide();
			 },
			 buttons: {
			 Cancel: function() {
			 $("#showMsg").dialog("close");
		    	$("#showMsg").hide();
			 }
			 }
			 });
	 }
	 

	
	function getOnlinePaymentReceipt(id){
		var url = "AdmissionStatus.do?method=getOnlinePaymentDetails&onlineReceiptId="+id;
		myRef = window .open(url, "viewDescription", 'left=20,top=20,width=800,height=1000,toolbar=1,resizable=0,scrollbars=1');

		document.getElementById("mode").value= "getOnlinePaymentDetails";
		var args =  "method=setDateToForm&applicationNo="+applnNo +"&appliedYear="+appliedYear;
		var url = "AdmissionStatus.do";
		requestOperationProgram(url, args, result);
	}
	var displayName = document.getElementById("displayName").value;
	//document.getElementById("DashBoardMessage").style.display = "block";
	//document.getElementById("displayApplyCourses").style.display = "block";
	getMode(displayName);
