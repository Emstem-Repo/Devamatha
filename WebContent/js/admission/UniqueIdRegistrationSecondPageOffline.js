	function newCourse(){
		var uniqueId = document.getElementById("onlineApplicationUniqueId").value;
		if(uniqueId!=null && uniqueId!=0){
			//document.location.href ="onlineApplicationSubmit.do?method=initOutsideSinglePageAccess";
			document.location.href ="onlineApplicationSubmit.do?method=initOfflineStartPage";
			
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
		if(uniqueId!=null && uniqueId!=0 && mode!='Online'){
			document.location.href ="onlineApplicationSubmit.do?method=initOfflinePageAccess&admApplnId="+id+"&mode=CurrentID";
	    // document.location.href ="onlineApplicationSubmit.do?method=initStartPage&uniqueId="+uniqueId+"&admApplnId="+id+"";
		}else if(uniqueId!=null && uniqueId!=0 && mode=='Online'){
			document.location.href ="onlineApplicationSubmit.do?method=initOutsideSinglePageAccess&admApplnId="+id+"&mode=CurrentID";
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
				$("#status").addClass("current");
				$("#dashBoard").removeClass("current");
				$("#mail").removeClass("current");
				$("#sms").removeClass("current");
				$("#onlinePayment").removeClass("current");
				$("#alerts").removeClass("current");
				$("#displayStatus").show();
				$("#displayMail").hide();
				$("#displaySMS").hide();
				$("#DashBoardMessage").hide();
				$("#displayOnlinePayment").hide();
				$("#displayAlerts").hide();
				$("#programmesOffered").removeClass("current");
				$("#displayProgrammesOffered").hide();
				$("#applyCourses").removeClass("current");
				$("#displayApplyCourses").hide();
			}else if(val=='dashBoard'){
				$("#dashBoard").addClass("current");
				$("#DashBoardMessage").show();
				$("#status").removeClass("current");
				$("#mail").removeClass("current");
				$("#sms").removeClass("current");
				$("#onlinePayment").removeClass("current");
				$("#displayStatus").hide();
				$("#displayMail").hide();
				$("#displaySMS").hide();
				$("#displayOnlinePayment").hide();
				$("#alerts").removeClass("current");
				$("#displayAlerts").hide();
				$("#programmesOffered").removeClass("current");
				$("#displayProgrammesOffered").hide();
				$("#applyCourses").removeClass("current");
				$("#displayApplyCourses").hide();
			}else if(val=='mail'){
				$("#mail").addClass("current");
				$("#dashBoard").removeClass("current");
				$("#status").removeClass("current");
				$("#sms").removeClass("current");
				$("#onlinePayment").removeClass("current");
				$("#displayMail").show();
				$("#displayStatus").hide();
				$("#displaySMS").hide();
				$("#DashBoardMessage").hide();
				$("#displayOnlinePayment").hide();
				$("#alerts").removeClass("current");
				$("#displayAlerts").hide();
				$("#programmesOffered").removeClass("current");
				$("#displayProgrammesOffered").hide();
				$("#applyCourses").removeClass("current");
				$("#displayApplyCourses").hide();
			}else if(val=='sms'){
				$("#sms").addClass("current");
				$("#dashBoard").removeClass("current");
				$("#status").removeClass("current");
				$("#mail").removeClass("current");
				$("#onlinePayment").removeClass("current");
				$("#displaySMS").show();
				$("#displayStatus").hide();
				$("#displayMail").hide();
				$("#DashBoardMessage").hide();
				$("#displayOnlinePayment").hide();
				$("#alerts").removeClass("current");
				$("#displayAlerts").hide();
				$("#programmesOffered").removeClass("current");
				$("#displayProgrammesOffered").hide();
				$("#applyCourses").removeClass("current");
				$("#displayApplyCourses").hide();
			}else if(val=='onlinePaymentDetails'){
				$("#onlinePayment").addClass("current");
				$("#dashBoard").removeClass("current");
				$("#status").removeClass("current");
				$("#mail").removeClass("current");
				$("#sms").removeClass("current");
				$("#displayOnlinePayment").show();
				$("#displayStatus").hide();
				$("#displayMail").hide();
				$("#DashBoardMessage").hide();
				$("#displaySMS").hide();
				$("#alerts").removeClass("current");
				$("#displayAlerts").hide();
				$("#programmesOffered").removeClass("current");
				$("#displayProgrammesOffered").hide();
				$("#applyCourses").removeClass("current");
				$("#displayApplyCourses").hide();
			}else if(val=='alerts'){
				$("#alerts").addClass("current");
				$("#displayAlerts").show();
				$("#onlinePayment").removeClass("current");
				$("#dashBoard").removeClass("current");
				$("#status").removeClass("current");
				$("#mail").removeClass("current");
				$("#sms").removeClass("current");
				$("#displayOnlinePayment").hide();
				$("#displayStatus").hide();
				$("#displayMail").hide();
				$("#DashBoardMessage").hide();
				$("#displaySMS").hide();
				$("#programmesOffered").removeClass("current");
				$("#displayProgrammesOffered").hide();
				$("#applyCourses").removeClass("current");
				$("#displayApplyCourses").hide();
		 }	else if( val== 'programmesOffered'){
			 	$("#programmesOffered").addClass("current");
				$("#displayProgrammesOffered").show();
			 	$("#alerts").removeClass("current");
				$("#displayAlerts").hide();
				$("#onlinePayment").removeClass("current");
				$("#dashBoard").removeClass("current");
				$("#status").removeClass("current");
				$("#mail").removeClass("current");
				$("#sms").removeClass("current");
				$("#displayOnlinePayment").hide();
				$("#displayStatus").hide();
				$("#displayMail").hide();
				$("#DashBoardMessage").hide();
				$("#displaySMS").hide();
				$("#applyCourses").removeClass("current");
				$("#displayApplyCourses").hide();
		 }else if( val== 'applyCourses'){
			 	$("#applyCourses").addClass("current");
				$("#displayApplyCourses").show();
			 	$("#programmesOffered").removeClass("current");
				$("#displayProgrammesOffered").hide();
			 	$("#alerts").removeClass("current");
				$("#displayAlerts").hide();
				$("#onlinePayment").removeClass("current");
				$("#dashBoard").removeClass("current");
				$("#status").removeClass("current");
				$("#mail").removeClass("current");
				$("#sms").removeClass("current");
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

	 function onlineApplicationStatus(){
			document.location.href="uniqueIdRegistration.do?method=initOfflineApplicationLogin";
		}

	function getApplicationDetails(id){
		//document.location.href ="onlineApplicationSubmit.do?method=initStartPage";
	     document.location.href ="onlineApplicationSubmit.do?method=initOfflinePageAccess&onlinePayment="+id+"&propertyName=NotRefund";
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
	document.getElementById("DashBoardMessage").style.display = "block";
	getMode(displayName);
