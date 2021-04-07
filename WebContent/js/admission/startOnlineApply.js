var jq=$.noConflict();

function noCopyMouse(e) {
    var isRight = (e.button) ? (e.button == 2) : (e.which == 3);
    if(isRight) {
    	alert('Please write the re confimation mail');
		document.getElementById("confirmEmailId").value="";
        return false;
    }
    return true;
}

function noCopyKey(e) {
    var isCtrl;
isCtrl = e.ctrlKey
if(isCtrl) {
		document.getElementById("confirmEmailId").value="";
    		return false;
    		
      	}
	
	if(e.keyCode == 17){
		document.getElementById("confirmEmailId").value="";
	return false;
	}

	//noCopyMouse(e);
    	return true;
}

jq(document).ready(function(){
	
	if(document.getElementById("paymentSuccess").value==true || document.getElementById("paymentSuccess").value=="true"){
		 jq(".mainDisplay :input").attr( 'disabled', 'disabled'); 
		 jq("#mainButton").hide();
	}
	else{
		jq("#mainButton").show();
		jq(".mainDisplay :input").removeAttr('disabled');
	}
	});
function resetData(){
	resetFieldAndErrMsgs();
}

var blinkLength = 3000;
function blink1(){
	document.getElementById('div3').style.display = "block";
	t1=setTimeout("blink2()",blinkLength);
}
function blink2(){
	document.getElementById('div3').style.display = "none";
	t2=setTimeout("blink1()",500);
}


function displayAlert(courseId){
	//cade added by mounesh start
	var testIds='<%=KPPropertiesConfiguration.PGDM_LAVASA_GHAZ_COURSE_ID%>'.split(',');
	if(testIds.length>0){
		for(var i=0; i<testIds.length;i++){
			if(courseId==testIds[i]){
				 jq("#lavas_ghaz").dialog({
					 	resizable: false,
				        dialogClass: "noclose",
				        modal: true,
				        height: 200,
				        title: "Alert",
				        width: 550,
				        close: function() {
		   	    	jq("#lavas_ghaz").dialog("destroy");
		   	    	jq("#lavas_ghaz").hide();
		            }
				 });
			}
		}
	}
	//mounesh end
	if(courseId=="340"){
		document.getElementById('340').style.display = "block";
		document.getElementById('341').style.display = "none";
		document.getElementById('342').style.display = "none";
		document.getElementById('359').style.display = "none";
		document.getElementById('20').style.display = "none";
		//document.getElementsByName("displayStatement").value="This programme is a full time - 2 years with four semesters offered at Christ University Main Campus. This programme is different from MBA with finance specialization. This programme emphasizes on financial management in its entirety.  This programme replaces the erstwhile MFM programme (Master of Financial Management)";
		 jq("#timer3").dialog({
		        resizable: false,
		        dialogClass: "noclose",
		        modal: true,
		        height: 200,
		        title: "Alert",
		        width: 550,
		        close: function() {
   	    	jq("#timer3").dialog("destroy");
   	    	jq("#timer3").hide();
            }
           //write button code here if required
		    });
		}else if(courseId=="341"){
			document.getElementById('341').style.display = "block";
			document.getElementById('340').style.display = "none";
			document.getElementById('342').style.display = "none";
			document.getElementById('359').style.display = "none";
			document.getElementById('20').style.display = "none";
			//document.getElementsByName("displayStatement").value="This programme is a full time - 2 years with four semesters offered at Christ University Bannerghatta Road Campus. This programme is not a specialization programme like the normal MBA programme. This programme emphasizes on travel and tourism in its entirety. Classes for first year will be conducted at Christ University Main Campus and the second year at Bannerghatta Road Campus. This programme replaces the erstwhile MTA programme (Master of Tourism Administration)";
			 jq("#timer3").dialog({
			        resizable: false,
			        dialogClass: "noclose",
			        modal: true,
			        height: 220,
			        title: "Alert",
			        width: 550,
			        close: function() {
	    	    	jq("#timer3").dialog("destroy");
	    	    	jq("#timer3").hide();
	             }
	            //write button code here if required
			    });
			
			
		}else if(courseId=="342"){
			document.getElementById('341').style.display = "none";
			document.getElementById('340').style.display = "none";
			document.getElementById('342').style.display = "block";
			document.getElementById('359').style.display = "none";
			document.getElementById('20').style.display = "none";
		//	document.getElementsByName("displayStatement").value="This programme is a full time - 3 years with six semesters offered at Christ University Bannerghatta Road Campus. Classes for first two semesters will be conducted at Christ University Main Campus and thereafter at Bannerghatta Road Campus.";
			 jq("#timer3").dialog({
			        resizable: false,
			        dialogClass: "noclose",
			        modal: true,
			        height: 220,
			        title: "Alert",
			        width: 550,
			        close: function() {
	    	    	jq("#timer3").dialog("destroy");
	    	    	jq("#timer3").hide();
	             }
	            //write button code here if required
			    });
		}else if(courseId=="359" || courseId=="358" ||courseId=="351"
		 || courseId=="352" || courseId=="360" || courseId=="361" || courseId=="334" || courseId=="362" || courseId=="364"){	
			document.getElementById('341').style.display = "none";
			document.getElementById('340').style.display = "none";
			document.getElementById('342').style.display = "none";
			document.getElementById('359').style.display = "block";
			document.getElementById('20').style.display = "none";
			// document.getElementById("displayStatement").value="This programme is a 3 years/six semesters offered at Christ University Bannerghatta Road Campus. Classes for first two semesters will be conducted at Christ University Main Campus and 3rd semester onwards at Bannerghatta Road Campus.";
			 jq("#timer3").dialog({
			        resizable: false,
			        dialogClass: "noclose",
			        modal: true,
			        height: 200,
			        title: "Alert",
			        width: 550,
			        close: function() {
	    	    	jq("#timer3").dialog("destroy");
	    	    	jq("#timer3").hide();
	             }
	            //write button code here if required
			    });
		}else if(courseId=="20"){	
			document.getElementById('341').style.display = "none";
			document.getElementById('340').style.display = "none";
			document.getElementById('342').style.display = "none";
			document.getElementById('359').style.display = "none";
			document.getElementById('20').style.display = "block";
			 jq("#timer3").dialog({
			        resizable: false,
			        dialogClass: "noclose",
			        modal: true,
			        height: 200,
			        title: "Alert",
			        width: 550,
			        close: function() {
	    	    	jq("#timer3").dialog("destroy");
	    	    	jq("#timer3").hide();
	             }
	            //write button code here if required
			    });
		}
	//var args ="method=getIntrAdmissionSessionDetails&courseId="+courseId;
	//var url = "admissionFormSubmit.do";
	//requestOperationProgram(url, args, updateIntrAdmissionSessionMap);
	jq.ajax({
		type:"post",
		url:"admissionFormSubmit.do?method=getIntrAdmissionSessionDetails",
		data:{courseId:courseId},
		success:function(resp){
			var parsedJson=jQuery.parseJSON(resp);
			 var session=parsedJson.Admission;
			 var isNotEmpty=jQuery.isEmptyObject(session);
			 if(!isNotEmpty){
				 var destination = document.getElementById("interAdmissionSession");
					for (x1 = destination.options.length - 1; x1 > 0; x1--) {
						destination.options[x1] = null;
					}
				 destination.options[0] = new Option("- Select -", "");
				 var i=1;
				 $.each( session, function( key, value ) {
					 destination.options[i] = new Option(key, value);
					 i=i+1;
					 });
				 $("#showIntrAdmSession").show();
				 $("#isInterDisciplinary").val("Yes");
			 }else{
				 $("#showIntrAdmSession").hide();
				 $("#isInterDisciplinary").val("No");
			 }
		}
	});
}
function cancel(){
	document.location.href="onlineApplicationLogin.do?method=loginOnlineApplication&propertyName=back";
}

function submitDialogue(){
	jq("#timer3").dialog("close");
  	jq("#timer3").hide();
}
function cancelDialogue()
{
	document.getElementById("coursePref1").value="";
 	jq("#timer3").dialog("close");
  	jq("#timer3").hide();
}
function cancelDialogueLavasGhazia()
{
	document.getElementById("coursePref1").value="";
 	jq("#lavas_ghaz").dialog("close");
  	jq("#lavas_ghaz").hide();
}

$("#timer3").hide();
if($("#isInterDisciplinary").val()!="" && $("#isInterDisciplinary").val()=="No"){
	$("#showIntrAdmSession").hide();
	
}else{
	$("#showIntrAdmSession").show();
	
}
var programTypeId = document.getElementById("programType").value;
if(programTypeId != null && programTypeId.length != 0) {
	document.getElementById("programTypeId").value = programTypeId;
}

var browserName=navigator.appName; 
 if (browserName=="Microsoft Internet Explorer")
 {
	 document.getElementById("message").innerHTML="<b style='color:red'>Note:</b> Use Mozilla Firefox for better Performance and view  or Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling.";
	 alert(" If Your using InternetExplorer Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling  or Use Mozilla FireFox");
 }

 function getMobileNo(){
		var residentId = document.getElementById("residentCategory").value;
		var nativeCountry = document.getElementById("nativeCountry").value;
		var  nativeCountrys = nativeCountry.split(",");
		var indian=false;
		for ( var i = 0; i < nativeCountrys.length; i++) {
			if(nativeCountrys[i]==residentId){
				indian = true;
			}
		}
		if(indian){
			document.getElementById("mobileNo1").value="91";
		}else{
			document.getElementById("mobileNo1").value="";
		}			
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
 