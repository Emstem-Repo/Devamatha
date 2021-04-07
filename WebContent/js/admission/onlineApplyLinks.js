
function downloadChallan() {
	var challanUrl="admissionFormSubmit.do?method=forwardChallanTemplate";
	window.open(challanUrl,"Challan","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");		
}
function disableCheckBox(count){
	if(count == 1){
		var value = document.getElementById("selected1_"+count).checked;
		if(value){
			document.getElementById("selected1_0").disabled=true;
		}else{
			document.getElementById("selected1_0").disabled=false;
		}
	}else{
		var value = document.getElementById("selected1_"+count).checked;
		if(value){
			document.getElementById("selected1_1").disabled=true;
		}else{
			document.getElementById("selected1_1").disabled=false;
		}
	}
}
