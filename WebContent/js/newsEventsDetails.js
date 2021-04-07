$(function() {
	 $("a,.formbutton,img,input:checkbox,.comboLarge,.combo,.comboMediumLarge,.comboExtraLarge,.comboBig,.comboMediumBig,.comboSmall,.comboMedium,*:text,:button").each(function(){
		var method = $(this).attr("onclick"); 
		var method1 = $(this).attr("onmousedown");
		var method2 = $(this).attr("onchange");
		var method3 = $(this).attr("onblur");
		if(method != undefined){
			method ="appendMethodOnBrowserClose(),"+method;
			$(this).attr("onclick",method);
		}else if(method1!=undefined){
			method1 ="appendMethodOnBrowserClose(),"+method1;
			$(this).attr("onmousedown",method1);
		}
		else if(method2!=undefined)
		{
			method2 ="appendMethodOnBrowserClose(),"+method2;
			$(this).attr("onchange",method2);
		}
		else if(method3!=undefined)
		{
			method3 ="appendMethodOnBrowserClose(),"+method3;
			$(this).attr("onblur",method3);
		}else{
			method ="appendMethodOnBrowserClose()";
			$(this).attr("onclick",method);
		}
	 });
	});

function appendMethodOnBrowserClose(){
	hook = false;
}

var jq=$.noConflict();
	jq(document).ready(function(){
		 
	    jq("#RegistrationYes").click(function(){
	    	 jq("#Registration").show();
	    });
	    jq("#RegistrationNo").click(function(){
	   	 jq("#Registration").hide();
	   });

	    
	    jq("#InvitationYes").click(function(){
	    	 jq("#invitation").show();
	    });
	    jq("#InvitationNo").click(function(){
	   	 jq("#invitation").hide();
	   });
	    
		if(jq('#RegistrationYes').is(':checked')){
			 jq("#Registration").show();
			 
		}else if(jq('#RegistrationNo').is(':checked')){
			 jq("#Registration").hide();
		}
		
		if(jq('#InvitationYes').is(':checked')){
			 jq("#invitation").show();
			 
		}else if(jq('#InvitationNo').is(':checked')){
			 jq("#invitation").hide();
		}
		
		var flag=ShowHideDropDowns();
		if(flag=="Course"){
			jq("#courseTitle").show();
			jq("#departmentTitle").hide();
			jq("#streamTitle").hide();
			jq("#SplCenterTitle").hide();
			
		}else if(flag=="Department")
		{ 
			jq("#courseTitle").hide();
			jq("#departmentTitle").show();
			jq("#streamTitle").hide();
			jq("#SplCenterTitle").hide();
		}
		else if(flag=="Deanery")
		{
			jq("#courseTitle").hide();
			jq("#departmentTitle").hide();
			jq("#streamTitle").show();
			jq("#SplCenterTitle").hide();
		}
		else if(flag=="Special Centers")
		{
			jq("#courseTitle").hide();
			jq("#departmentTitle").hide();
			jq("#streamTitle").hide();
			jq("#SplCenterTitle").show();
			
		}else
		{
			jq("#courseTitle").hide();
			jq("#departmentTitle").hide();
			jq("#streamTitle").hide();
			jq("#SplCenterTitle").hide();
		}
	
	
	jq("#organizedBy").click(function(){
		var flag=ShowHideDropDowns();
		if(flag=="Course"){
			jq("#courseTitle").show();
			jq("#departmentTitle").hide();
			jq("#streamTitle").hide();
			jq("#SplCenterTitle").hide();
			
		}else if(flag=="Department")
		{
			jq("#courseTitle").hide();
			jq("#departmentTitle").show();
			jq("#streamTitle").hide();
			jq("#SplCenterTitle").hide();
		}
		else if(flag=="Deanery")
		{
			jq("#courseTitle").hide();
			jq("#departmentTitle").hide();
			jq("#streamTitle").show();
			jq("#SplCenterTitle").hide();
		}
		else if(flag=="Special Centers")
		{
			jq("#courseTitle").hide();
			jq("#departmentTitle").hide();
			jq("#streamTitle").hide();
			jq("#SplCenterTitle").show();
			
		}
		else
		{
			jq("#courseTitle").hide();
			jq("#departmentTitle").hide();
			jq("#streamTitle").hide();
			jq("#SplCenterTitle").hide();
		}
	});
	
	var flag=getSelectedViewFor();
	if(flag==true){
		jq("#IconDescription1").show();
	}else
	{
		jq("#IconDescription1").hide();
	}

	jq("#selectedviewFor").click(function(){
		var flag=getSelectedViewFor();
		if(flag==true){
			jq("#IconDescription1").show();
		}else
		{
			jq("#IconDescription1").hide();
		}
   });
	
   });	

function maxlength(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}

	function len_display(Object,MaxLen,element){
	    var len_remain = Object.value.length;
	   if(len_remain <=1000){
	    document.getElementById(element).value=len_remain; }
	}
	function len_displaySummary(Object,MaxLen,element){
	    var len_remain = Object.value.length;
	   if(len_remain <=500){
	    document.getElementById(element).value=len_remain; }
	}
	
	function getDetailForDownload1(formatNamejsp) {
		document.downloadFormatsForm.method.value="getStreamInfo";
		document.downloadFormatsForm.formatName.value=formatNamejsp;
		document.downloadFormatsForm.submit();
		myRef = window.open(url, "Download Formats", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
	}
	function calculateDates(){
		var result=calculateDates1();
		if(result=="true")
		{
			alert("Date From cannot be greater then Date To");
		}
		
	}
	function calculateDisplayDates(){
		var result=calculateDisplayDates1();
		if(result=="true")
		{
			alert("Display Date From cannot be greater then Display Date To");
		}
	}
	
	function calculateDates1(){
		var startDate=document.getElementById("dateFrom").value;
		var endDate =document.getElementById("dateTo").value;
		var result="false";
		if((startDate!=null || startDate!="") && (endDate!=null || endDate!=""))
		{
			d1=startDate.split('/');
		    d2=endDate.split('/');
		    var startDate1=new Date(d1[2],(d1[1]-1),d1[0]);
		    var endDate1=new Date(d2[2],(d2[1]-1),d2[0]);
		    if(startDate!="" && endDate!=""){
				if(startDate1 > endDate1){
					result="true";
				}
			}
		}
	    return result;
	}
	function calculateDisplayDates1(){
		var startDate=document.getElementById("displayFromDate").value;
		var endDate =document.getElementById("displayToDate").value;
		var result=false;
		if((startDate!=null || startDate!="") && (endDate!=null || endDate!=""))
		{
			d1=startDate.split('/');
		    d2=endDate.split('/');
		    var startDate1=new Date(d1[2],(d1[1]-1),d1[0]);
		    var endDate1=new Date(d2[2],(d2[1]-1),d2[0]);
		    if(startDate!="" && endDate!=""){
				if(startDate1 > endDate1){
					result=true;
				}
		    }
		}
	    return result;
	}
	
	function checkContactEmail(count) 
	{
	    var email = document.getElementById('email_contact_'+count);
	    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	    if(email.value!=null && email.value!=""){
		    if (!filter.test(email.value)) {
			    alert('Please provide a valid email address');
			    return false;
		    }
	 }
	}
	function checkEmail(count) 
	{
	    var email = document.getElementById('email_resourse_'+count);
	    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	    if(email.value!=null && email.value!=""){
		    if (!filter.test(email.value)) {
			    alert('Please provide a valid email address');
			    return false;
		    }
	 }
	}
	
	function checkMandatory()
	{
		var orgSub=null;
		var errorMsg=null;
		var data = document.getElementById("organizedBy").value;
		if(data!=null && data!="")
		{
			if(data=="Course")
			{
				var orgSub =document.getElementById("courseId").value;
				if(orgSub==null || orgSub =="")
				{
					errorMsg="Please select Course";
				}
			}
			else if(data=="Department")
			{
				var orgSub =document.getElementById("departmentId").value;
				if(orgSub==null || orgSub =="")
				{
					errorMsg="Please select Department";
				}
			}
			else if(data=="Deanery")
			{
				var orgSub =document.getElementById("streamId").value;
				if(orgSub==null || orgSub =="")
				{
					errorMsg="Please select Deanery";
				}
			}else if(data=="Special Centers")
			{
				var orgSub =document.getElementById("splCenterId").value;
				if(orgSub==null || orgSub =="")
				{
					errorMsg="Please select Special Centers";
				}
			}
			
		}else
		{
			errorMsg="Organized By is required";
			errorMsg=errorMsg+", ";
		}
		var errormsg2=null;
		var testData=null;
		
		
		testData =document.getElementById("academicYear").value;
		if(testData==null || testData =="")
		{
			errormsg2="AcademicYear selection is required";
			if(errorMsg!=null){
				errorMsg=errorMsg+errormsg2 +", ";
			}
			else
			{
				errorMsg=errormsg2 +", ";
			}
		}
		
		testData =document.getElementById("eventTitle").value;
		if(testData==null || testData =="")
		{
			errormsg2="Title is required";
			if(errorMsg!=null){
				errorMsg=errorMsg+errormsg2 +", ";
			}
			else
			{
				errorMsg=errormsg2 +", ";
			}
		}
		testData =document.getElementById("eventDescription").value;
		if(testData==null || testData =="")
		{
			errormsg2="Description is required";
			if(errorMsg!=null){
				errorMsg=errorMsg+errormsg2 +", ";
			}
			else
			{
				errorMsg=errormsg2 +", ";
			}
		}
		
		testData =document.getElementById("dateFrom").value;
		if(testData==null || testData =="")
		{
			errormsg2="Date From is required";
			if(errorMsg!=null){
				errorMsg=errorMsg+errormsg2 +", ";
			}
			else
			{
				errorMsg=errormsg2 +", ";
			}
		}
		

		testData =document.getElementById("dateTo").value;
		if(testData==null || testData =="")
		{
			errormsg2="Date To is required";
			if(errorMsg!=null){
				errorMsg=errorMsg+errormsg2 +", ";
			}
			else
			{
				errorMsg=errormsg2 +", ";
			}
			
		}
		var dateFrom=document.getElementById("dateFrom").value;
		var dateTo =document.getElementById("dateTo").value;
		if((dateFrom!=null || dateFrom!="") && (dateTo!=null || dateTo!="")){
		testData=calculateDates1();
		if(testData=='true'){
			errormsg2="Date From cannot be greater then Date To";
			if(errorMsg!=null){
				errorMsg=errorMsg+errormsg2 +", ";
			}
			else
			{
				errorMsg=errormsg2 +", ";
			}
		}
		}
		testData =document.getElementById("categoryId").value;
		if(testData==null || testData =="")
		{
			errormsg2="Category is required";
			if(errorMsg!=null){
				errorMsg=errorMsg+errormsg2 +", ";
			}
			else
			{
				errorMsg=errormsg2 +", ";
			}
		}
		
			
		if(document.getElementById("RegistrationYes").checked==false && document.getElementById("RegistrationNo").checked==false)
		{
			errormsg2="Please select Registration Required Yes/No";
			if(errorMsg!=null){
				errorMsg=errorMsg+errormsg2 +", ";
			}
			else
			{
				errorMsg=errormsg2 +", ";
			}
		}
		
		if(document.getElementById("InvitationYes").checked==false && document.getElementById("InvitationNo").checked==false)
		{
			errormsg2="Please select Invitation Mail Required Yes/No";
			if(errorMsg!=null){
				errorMsg=errorMsg+errormsg2 +", ";
			}
			else
			{
				errorMsg=errormsg2 +", ";
			}
		}
		
		if(errorMsg!=null){
			return errorMsg;
		}
	}
	
	
	function checkDates()
	{
		var orgSub=null;
		var errorMsg=null;
		var errormsg2=null;
		var dateFrom=document.getElementById("displayFromDate").value;
		var dateTo =document.getElementById("displayToDate").value;
		if((dateFrom!=null || dateFrom!="") && (dateTo!=null || dateTo!="")){
		testData=calculateDisplayDates1();
		if(testData=='true'){
			errormsg2="Display From Date cannot be greater then Display To Date";
			if(errorMsg!=null){
				errorMsg=errorMsg+errormsg2 +", ";
			}
			else
			{
				errorMsg=errormsg2 +", ";
			}
		}
		}

		if(errorMsg!=null){
			return errorMsg;
		}
	}
	
	function checkMandatoryPostApproval()
	{
		var orgSub=null;
		var errorMsg=null;
		testData =document.getElementById("summary").value;
		if(testData==null || testData =="")
		{
			errormsg2="Summary is required";
			if(errorMsg!=null){
				errorMsg=errorMsg+errormsg2 +", ";
			}
			else
			{
				errorMsg=errormsg2 +", ";
			}
		}
		if(errorMsg!=null){
			return errorMsg;
		}
	}
		
	function ViewPhoto(id,photoname) {
		document.getElementById("selectedPhotoId").value=id;
		document.location.href = "NewsEventsEntry.do?method=ViewPhoto&selectedPhotoId="+id;
		myRef = window.open(url,"Photo","left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
	}

	function imposeMaxLength(field, size, length) {
	    var fieldSize = field.value.length;
	    if (fieldSize > size) {
	        field.value = field.value.substring(length, size);
	    }
	}

	function submitResourseInfoAdd(method,mode){
		document.getElementById("method").value=method;
		document.getElementById("mode").value=mode;
		document.NewsEventsEntryForm.submit();
	}
	
	function addRow(){
		var size = document.getElementById("photoListSize").value;
		if(size == null || size==""){
			size=0;
		}
		var table=document.getElementById("addRowTable");
		var row=table.insertRow(parseInt(size));
		var cell1=row.insertCell(0);
		var cell2=row.insertCell(1);
		var cell3=row.insertCell(2);
		var htm = "<input id='photoFile' type='file' size='20' maxlength='20' name='photosTO["+size+"].photoFile'/>";
		cell1.innerHTML=htm;
		cell2.innerHTML="";
		cell3.innerHTML="";
		document.getElementById("photoListSize").value = parseInt(size) + 1;
	}
	function addResourseRow(){
		var size = document.getElementById("resourseListSize").value;
		if(size == null || size==""){
			size=0;
		}
		var table=document.getElementById("addResourseRowTable");
		var row=table.insertRow(parseInt(size));
		var cell1=row.insertCell(0);
		var cell2=row.insertCell(1);
		var cell3=row.insertCell(2);
		var cell4=row.insertCell(3);

		var htm="<input type='text' name='resourseTO["+size+"].resourseName' maxlength='150' size='25' value='' id='resourseName'/>";
		var htm1="<input type='text' name='resourseTO["+size+"].email' maxlength='100' size='20' value='' onkeypress='maxlength(this, 100);' id='email_resourse_"+size+"' onblur='checkEmail("+size+");'/>";
		var htm2="<input type='text' name='resourseTO["+size+"].contactNo' maxlength='20' size='20' value='' onkeypress='return isNumberKey(event)' id='contactNo'/>";
		var htm3="<textarea name='resourseTO["+size+"].otherInfo' cols='30' rows='1' onkeypress='maxlength(this, 500);' id='otherInfo'/>";
		cell1.innerHTML=htm;
		cell2.innerHTML=htm1;
		cell3.innerHTML=htm2;
		cell4.innerHTML=htm3;
		document.getElementById("resourseListSize").value = parseInt(size) + 1;
	}
	function addContactRow(){
		var size = document.getElementById("contactListSize").value;
		if(size == null || size==""){
			size=0;
		}
		var table=document.getElementById("addContactRowTable");
		var row=table.insertRow(parseInt(size));
		var cell1=row.insertCell(0);
		var cell2=row.insertCell(1);
		var cell3=row.insertCell(2);
		var cell4=row.insertCell(3);
		var htm="<input type='text' name='contactTO["+size+"].name' maxlength='150' size='25' value='' id='name'/>";
		var htm1="<input type='text' name='contactTO["+size+"].email' maxlength='100' size='20' value='' onkeypress='maxlength(this, 100);' id='email_contact_"+size+"' onblur='checkContactEmail("+size+");'/>";
		var htm2="<input type='text' name='contactTO["+size+"].contactNo' maxlength='20' size='20' value='' onkeypress='return isNumberKey(event)' id='contactNo'/>";
		var htm3="<textarea name='contactTO["+size+"].remarks' cols='30' rows='1' onkeypress='maxlength(this, 500);' id='remarks'/>";
		cell1.innerHTML=htm;
		cell2.innerHTML=htm1;
		cell3.innerHTML=htm2;
		cell4.innerHTML=htm3;
		document.getElementById("contactListSize").value = parseInt(size) + 1;
	}
	
	function addPartRow(){
		var size = document.getElementById("participantsListSize").value;
		if(size == null || size==""){
			size=0;
		}
		var table=document.getElementById("addPartRowTable");
		var row=table.insertRow(parseInt(size));
		var cell1=row.insertCell(0);
		var cell2=row.insertCell(1);
		var cell3=row.insertCell(2);
		var htm="<input type='text' name='partcipantsTO["+size+"].institutionName' maxlength='200' size='25' value='' id='institutionName'/>";
		var htm1="<input type='text' name='partcipantsTO["+size+"].noOfPeople' maxlength='10' size='20' value='' onkeypress='maxlength(this, 100);' id='noOfPeople'/>";
		var htm2="<textarea name='partcipantsTO["+size+"].remarks' cols='30' rows='1' onkeypress='maxlength(this, 500);' id='remarks'/>";
		cell1.innerHTML=htm;
		cell2.innerHTML=htm1;
		cell3.innerHTML=htm2;
		document.getElementById("participantsListSize").value = parseInt(size) + 1;
	}
	function removeResourseRow(){
		var size = document.getElementById("resourseListSize").value;
		var orgsize = document.getElementById("orgResListSize").value;
		if(parseInt(size)>parseInt(orgsize)){
			document.getElementById("addResourseRowTable").deleteRow(parseInt(size)-1);
			document.getElementById("resourseListSize").value = parseInt(size)-1;
		}
	}
	function removeContactRow(){
		var size = document.getElementById("contactListSize").value;
		var orgsize = document.getElementById("orgContactListSize").value;
		if(parseInt(size)>parseInt(orgsize)){
			document.getElementById("addContactRowTable").deleteRow(parseInt(size)-1);
			document.getElementById("contactListSize").value = parseInt(size)-1;
		}
	}
	function removeRow(){
		var size = document.getElementById("photoListSize").value;
		var orgsize = document.getElementById("orgphotoListSize").value;
		if(parseInt(size)>parseInt(orgsize)){
			document.getElementById("addRowTable").deleteRow(parseInt(size)-1);
			document.getElementById("photoListSize").value = parseInt(size)-1;
		}
	}

	function removePartRow(){
		var size = document.getElementById("participantsListSize").value;
		var orgsize = document.getElementById("orgPartListSize").value;
		if(parseInt(size)>parseInt(orgsize)){
			document.getElementById("addPartRowTable").deleteRow(parseInt(size)-1);
			document.getElementById("participantsListSize").value = parseInt(size)-1;
		}
	}
	function submitParticipantsInfoAdd(method,mode){
		document.getElementById("method").value=method;
		document.getElementById("mode").value=mode;
		document.NewsEventsEntryForm.submit();
	}

	function submitResourseInfoAdd(method,mode){
		document.getElementById("method").value=method;
		document.getElementById("mode").value=mode;
		document.NewsEventsEntryForm.submit();
	}
	

	function addMobNewsEventsDetails() 
	{
		var errorMsg=checkMandatory();
		if(errorMsg!=null){
			 var htm="<table width='100%'> <tr height='30px' class='row-white'>";
				htm=htm+"<td width=20%' align='left'>Error:- <b><FONT color='red'>"+errorMsg +"</FONT></b></td><tr>";
				htm=htm+"</table>";
				document.getElementById("errorMessage").innerHTML = htm;
				}else{
			document.getElementById("method").value="addMobNewsEventsDetails";
			document.NewsEventsEntryForm.submit();
		}
	}
		
	function editMobNewsEventsDetails(id) {
		
		document.getElementById("NEC").value=id;
		document.location.href = "NewsEventsEntry.do?method=editMobNewsEventsDetails&selectedNewsEventsId="+id;
	}
	function updateMobNewsEventsDetails() {
		var errorMsg=checkMandatory();
		if(errorMsg!=null){
			 var htm="<table width='100%'> <tr height='30px' class='row-white'>";
				htm=htm+"<td width=20%' align='left'>Error:- <b><FONT color='red'>"+errorMsg +"</FONT></b></td><tr>";
				htm=htm+"</table>";
				document.getElementById("errorMessage").innerHTML = htm;
				}else{
			document.getElementById("method").value = "updateMobNewsEventsDetails"; 
			document.NewsEventsEntryForm.submit();
		}
	}
	
	function updatePre() {
		var errorMsg=checkMandatory();
		var err2=null;
		if(err2!=null || errorMsg!=null){
			errorMsg= errorMsg+err2;
			}else if(errorMsg==null){
				errorMsg=err2;
			}
		if(errorMsg!=null){
			 var htm="<table width='100%'> <tr height='30px' class='row-white'>";
				htm=htm+"<td width=20%' align='left'>Error:- <b><FONT color='red'>"+errorMsg +"</FONT></b></td><tr>";
				htm=htm+"</table>";
				document.getElementById("errorMessage").innerHTML = htm;
				}else{
					document.getElementById("method").value="updatePrePost";
					document.NewsEventsEntryForm.submit();
				}
		
	}
	function updatePost() {
		var errorMsg=null;
		var err2=null;
		errorMsg=checkMandatory();
		err2=checkMandatoryPostApproval();
		if(err2!=null || errorMsg!=null){
		errorMsg= errorMsg+err2;
		}else if(errorMsg==null){
			errorMsg=err2;
		}
		if(errorMsg!=null){
			 var htm="<table width='100%'> <tr height='30px' class='row-white'>";
				htm=htm+"<td width=20%' align='left'>Error:- <b><FONT color='red'>"+errorMsg +"</FONT></b></td><tr>";
				htm=htm+"</table>";
				document.getElementById("errorMessage").innerHTML = htm;
				}else{
					document.getElementById("method").value="updatePrePost";
					document.NewsEventsEntryForm.submit();
				}
		}
	function updatePostDept() {
		var errorMsg=null;
		errorMsg=checkMandatoryPostApproval();
		if(errorMsg!=null){
				 var htm="<table width='100%'> <tr height='30px' class='row-white'>";
					htm=htm+"<td width=20%' align='left'>Error:- <b><FONT color='red'>"+errorMsg +"</FONT></b></td><tr>";
					htm=htm+"</table>";
					document.getElementById("errorMessage").innerHTML = htm;
		}else{
		document.getElementById("method").value="updateMobNewsEventsDetails";
		document.NewsEventsEntryForm.submit();
		}
	}
	function updateAdmin(){
		document.getElementById("method").value="updateNewsEventsAdmin";
		document.NewsEventsEntryForm.submit();
		
	}
	
	function deleteMobNewsEventsDetails(id) {
		deleteConfirm =confirm("Are you sure to delete this entry?");
		document.getElementById("NEC").value=id;
		if(deleteConfirm)
		document.location.href = "NewsEventsEntry.do?method=deleteMobNewsEventsDetails&selectedNewsEventsId="+id;
	}
	function reActivate() {
		var dupId = document.getElementById("dupId").value;
		document.location.href="NewsEventsEntry.do?method=reActivateMobNewsEventsDetails&dupId="+dupId;
	}
	function getPhoto(photoName){
		var url="NewsEventsEntry.do?method=getPhotoFromFile&photoName="+photoName;
		win2 = window.open(url, "Help", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0");
	}
	function deletePhoto(photoName){
		document.location.href="NewsEventsEntry.do?method=deletePhoto&photoId="+photoName;
	}
	function ClosePostDept() {
		document.location.href ="NewsEventsEntry.do?method=initPostDeptEntrySearch";
	}
	function CancelPre() {
		var screenName="Pre";
		document.location.href ="NewsEventsEntry.do?method=initNewsEventsSearch&screen="+screenName;
	}
	function CancelPost() {
		var screenName="Post";
		document.location.href ="NewsEventsEntry.do?method=getSearchNewsEvents&screen="+screenName;
	}
	function CancelAdmin() {
		document.location.href ="NewsEventsEntry.do?method=getSearchAdminNewsEvents";
	}
	function Cancel() {
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function getIcon(photoName){
		var url="NewsEventsEntry.do?method=getIconFromFile&iconName="+photoName;
			win2 = window.open(url, "Icon", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
	}
	function getRegistration(photoName){
		var url="NewsEventsEntry.do?method=getRegFormFromFile&regFormName="+photoName;
			win2 = window.open(url, "Registration", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
	}
	function getDownloadFile(photoName,fileType){
		var url="NewsEventsDownloads.do?method=getStreamInfo&downloadFileName="+photoName+"&fileType="+fileType;
			win2 = window.open(url, "Download File", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
	}
	function getInvitation(photoName){
		var url="NewsEventsEntry.do?method=getInvitationFromFile&invitationName="+photoName;
			win2 = window.open(url, "Invitation", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
	}
	function getMaterial(photoName){
		var url="NewsEventsEntry.do?method=getMaterialPublishedFromFile&materialName="+photoName;
			win2 = window.open(url, "Material Published", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
	}
	function getReport(photoName){
		var url="NewsEventsEntry.do?method=getReportFile&reportName="+photoName;
			win2 = window.open(url, "Report", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
	}

	function getPhoto(photoName){
		var url="NewsEventsEntry.do?method=getPhotoFromFile&photoName="+photoName;
		win2 = window.open(url, "Help", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0");
		
	}
	function deletePhoto(photoName){
		document.location.href="NewsEventsEntry.do?method=deletePhoto&photoId="+photoName;
	}
	
	function updateDetailsPrePost() {

		document.getElementById("method").value="updatePrePost";
		document.NewsEventsEntryForm.submit();
		
	}
	
	function getSelectedViewFor() {
		
		var flag=false;
		var viewFor = document.getElementById("selectedviewFor");
		if(viewFor!=null && viewFor!=""){
			var selectedArray = new Array();
			var count = 0;
			for ( var i = 0; i < viewFor.options.length; i++) {
				if (viewFor.options[i].selected) {
					selectedArray[count] = viewFor.options[i].value;
					if(selectedArray[count]=="Mobile"){
					 flag=true;
					}
					count++;
				}
			}
		}
		return flag;
	} 
	/*function getImageDescription()
	{
		 document.getElementById("IconDescription").style.display="block";
		 document.getElementById("IconDescription1").style.display="block";
	}*/
	
	function ShowHideDropDowns()
	{
	var sendResult=null;
		desig=document.getElementById("organizedBy").value;
		if(desig!=null)
		{
			if(desig=="Course")
			{ 
				sendResult="Course";

			}else if(desig=="Department")
			{
				sendResult="Department";
			}
			else if(desig=="Deanery")
			{
				sendResult="Deanery";
			}
			else if(desig=="Special Centers")
			{
				sendResult="Special Centers";
			}
		}
		return sendResult;
	}
	
	function getRegFormAdmin()
	{
		var sendResult=null;
		desig=document.getElementById("isRegistrationRequired").value;
			if(desig!=null && desig=="Yes")
			{
				sendResult="Yes";
			}else
			{
				sendResult="No";
			}
		return sendResult;
	}
	
	function getInvitationAdmin()
	{
		var sendResult=null;
		desig=document.getElementById("isInvitationMailRequired").value;
			if(desig!=null && desig=="Yes")
			{
				sendResult="Yes";
			}else
			{
				sendResult="No";
			}
		return sendResult;
	}
	function deleteResourse(resourseId){
		document.location.href="NewsEventsEntry.do?method=deleteResourse&resourseId="+resourseId;
	}
	
	function CheckRegistrationFiles()
	{
	var fup = document.getElementById('registrationForm');
			var fileName = fup.value;
			if(fileName!=null && fileName!=""){
			var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" 
				|| ext == "png" || ext == "PNG" || ext == "bmp" || ext == "BMP"
				|| ext == "doc" || ext == "docx" || ext=="pdf" || ext=="xls" || ext=="xlsx" || ext=="rtf"
				|| ext == "txt")
			{
			return true;
			} 
			else
			{
			alert("Upload Files with only the Following extensions:-'.doc' or '.txt' or '.docx' or '.pdf' or '.xls' or '.rtf'");
			fup.focus();
			return false;
			}
		}
	}
	function CheckInvitation()
	{
	var fup = document.getElementById('invitationMail');
			var fileName = fup.value;
			if(fileName!=null && fileName!=""){
			var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" 
				|| ext == "png" || ext == "PNG" || ext == "bmp" || ext == "BMP"
				|| ext == "doc" || ext == "docx" || ext=="pdf" || ext=="xls" || ext=="xlsx" || ext=="rtf"
				|| ext == "txt")
			{
			return true;
			} 
			else
			{
			alert("Upload Files with only the Following extensions:-'.doc' or '.txt' or '.docx' or '.pdf' or '.xls' or '.rtf' or '.gif' or '.jpeg' or '.jpg' or '.bmp' or '.png'");
			fup.focus();
			return false;
			}
		}
	}
	function CheckIcon()
	{
	var fup = document.getElementById('iconTmageFile');
		
			var fileName = fup.value;
			if(fileName!=null && fileName!=""){
			var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" 
				|| ext == "png" || ext == "PNG" || ext == "bmp" || ext == "BMP" )
			{
			return true;
			} 
			else
			{
			alert("Upload Files with only the Following extensions:-'.gif' or '.jpeg' or '.jpg' or '.bmp' or '.png'");
			fup.focus();
			return false;
			}
		}
	}
	function CheckMaterial()
	{
	var fup = document.getElementById('materialsPublished');
		
			var fileName = fup.value;
			if(fileName!=null && fileName!=""){
			var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" 
				|| ext == "png" || ext == "PNG" || ext == "bmp" || ext == "BMP"
				|| ext == "doc" || ext == "docx" || ext=="pdf" || ext=="xls" || ext=="xlsx" || ext=="rtf"
					|| ext == "txt")
			{
			return true;
			} 
			else
			{
			alert("Upload Files with only the Following extensions:-'.doc' or '.txt' or '.docx' or '.pdf' or '.xls' or '.rtf' or '.gif' or '.jpeg' or '.jpg' or '.bmp' or '.png'");
			fup.focus();
			return false;
			}
		}
	}
	function CheckReport()
	{
	var fup = document.getElementById('eventReport');
			var fileName = fup.value;
			if(fileName!=null && fileName!=""){
			var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" 
				|| ext == "png" || ext == "PNG" || ext == "bmp" || ext == "BMP"
				|| ext == "doc" || ext == "docx" || ext=="pdf" || ext=="xls" || ext=="xlsx" || ext=="rtf"
				|| ext == "txt"	)
			{
			return true;
			} 
			else
			{
			alert("Upload Files with only the Following extensions:-'.doc' or '.txt' or '.docx' or '.pdf' or '.xls' or '.rtf' or '.gif' or '.jpeg' or '.jpg' or '.bmp' or '.png'");
			fup.focus();
			return false;
			}
		}
	}
