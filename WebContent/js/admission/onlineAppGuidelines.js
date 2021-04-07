
function downloadChallan() {
	var challanUrl="onlineApplicationSubmit.do?method=forwardChallanTemplate";
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


$(document).ready(function() {	
	var ctrlKeyDown = false;
	$(document).on("keydown", keydown);
    $(document).on("keyup", keyup);
    

    
    function keydown(e) { 
        if ((e.which || e.keyCode) == 116 || ((e.which || e.keyCode) == 82 && ctrlKeyDown)) {
            // Pressing F5 or Ctrl+R
            e.preventDefault();
        } else if ((e.which || e.keyCode) == 17) {
            // Pressing  only Ctrl
            ctrlKeyDown = true;
        }
        
    };

    

    function keyup(e){
        // Key up Ctrl
        if ((e.which || e.keyCode) == 17) 
            ctrlKeyDown = false;
       
    };
    
    });