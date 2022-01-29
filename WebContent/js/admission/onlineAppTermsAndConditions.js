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
        e.preventDefault();
    };

    function click(e){
    	e.preventDefault();
    };

    function keyup(e){
        // Key up Ctrl
        if ((e.which || e.keyCode) == 17) 
            ctrlKeyDown = false;
        e.preventDefault();
    };
    

	$('#submitTermsAndCndId').click(function(e){
		var size=document.getElementById("conditionChecklistsSize").value;
		var isSelected='Yes';
		if(size!=''){
			for(var i=0; i<parseInt(size);i++){
				var  isChecked=document.getElementById("selected1_"+i).checked;
				var isMandatory=document.getElementById("mandatory_"+i).value;
				if(!isChecked){
					if(isMandatory === "true"){
								isSelected='No';
								break;
 							}
						}
					
			}
		}else
		{
			var isTermsSet=document.getElementById("termsSet").value;
			if(isTermsSet=='false'){
				document.getElementById("method").value="acceptTermsConditions";
				 document.onlineApplicationForm.submit();
			}
		}
		


		
		if(isSelected !='' && isSelected == 'Yes'){
			document.getElementById("method").value="acceptTermsConditions";
		    document.onlineApplicationForm.submit();
		}else if(isSelected !='' && isSelected == 'No'){
			$.confirm({
		 		'message'	: 'Please fill in the mandatory information.',
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							for(var j=0; j<parseInt(size);j++){
								var isChecked=document.getElementById("selected1_"+i).checked;
								var isMandatory=document.getElementById("mandatory_"+i).value;
								if(!isChecked  && isMandatory === "true"){
									document.getElementById('selected1_'+j).focus();
									break;
								}
								for(var k=0; k<parseInt(size);k++){
									var isChecked=document.getElementById("selected1_"+i).checked;
									var isMandatory=document.getElementById("mandatory_"+i).value;
									if( !isChecked  &&  isMandatory === "true"){
										$('#selected1_'+k).css('border-bottom', 'solid 1px red');
									}
								}
								
									
								}
								
								$.confirm.hide();
						}
					}
				}
		 	});

			return false;
		}
	
   });
});