$(document).ready(function() {
	var IsOnline = document.getElementById("onlineApply").value;
	if(IsOnline!=null && IsOnline=="true"){
		var CurrentPage=getSelectedViewFor();
	}else if(IsOnline!=null && IsOnline=="false")
	{
		var CurrentPage=getSelectedViewForOffline();
	}
});


function getSelectedViewFor() {
	//var CurrentPage = document.getElementById("currentPageNo").value;
	var flag = document.getElementById("displayPage").value;
	if(flag!=null && flag!=""){
		if(flag == 'basic'){
			$("#basic_page").show(); 
			$("#guidelines_page").hide();
			$("#terms_page").hide();
			$("#payment_page").hide(); 
			$("#details_page").hide(); 
		}else if(flag=='guidelines'){
			$("#basic_page").hide(); 
			$("#guidelines_page").show();
			$("#terms_page").hide();
			$("#payment_page").hide(); 
			$("#details_page").hide();  
		}else if(flag=='terms'){
			$("#basic_page").hide(); 
			$("#guidelines_page").hide();
			$("#terms_page").show();
			$("#details_page").hide();  
			$("#payment_page").hide(); 
		}else if(flag=='details'){
			$("#basic_page").hide(); 
			$("#guidelines_page").hide();
			$("#terms_page").hide();
			$("#payment_page").hide(); 
			$("#details_page").show(); 
		}else if(flag=='payment'){
			$("#basic_page").hide(); 
			$("#guidelines_page").hide();
			$("#terms_page").hide();
			$("#details_page").hide(); 
			$("#payment_page").show(); 
		}
	}
	return flag;
} 

function getSelectedViewForOffline() {
	var flag = document.getElementById("displayPage").value;
	if(flag!=null && flag!=""){
		if(flag == 'basic'){
			$("#basic_page").show(); 
			$("#terms_page").hide();
			$("#details_page").hide(); 
		}else if(flag=='terms'){
			$("#basic_page").hide(); 
			$("#terms_page").show();
			$("#details_page").hide();  
		}else if(flag=='details'){
			$("#basic_page").hide(); 
			$("#terms_page").hide();
			$("#details_page").show(); 
		}else if(flag=='confirmPage'){
			alert(confirmPage);
			$("#basic_page").hide(); 
			$("#terms_page").hide();
			$("#details_page").hide(); 
			$("#confirm_Page").show(); 
		}
	}
	return flag;
}

//start online apply scripts//




// start only apply scripts ends here......//

