$(document).ready(function() {	
	$('#submitAppFormId').click(function(e){
		var size=document.getElementById("guidelineChecklistsSize").value;
		var isSelected='No';
		if(size!=''){
			for(var i=0; i<parseInt(size);i++){
			var isChecked=document.getElementById("selected1_"+i).checked;
				if(isChecked !='' && isChecked ==true){
					isSelected='Yes';
				}
			}
		}else
		{
			var isguidelinesset=document.getElementById("guidelinesSet").value;
			if(isguidelinesset=='false'){
				document.getElementById("method").value="submitGuidelinesPage";
				 document.onlineApplicationForm.submit();
			}
		}
		if(isSelected !='' && isSelected == 'Yes'){
		 document.getElementById("method").value="submitGuidelinesPage";
		 document.onlineApplicationForm.submit();
		}else if(isSelected !='' && isSelected == 'No'){
			$.confirm({
		 		'message'	: 'Please fill in the mandatory information.',
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							for(var j=0; j<parseInt(size);j++){
								$('#selected1_'+j).css('border-bottom', 'solid 1px red');
								}
								document.getElementById('selected1_'+0).focus();
								$.confirm.hide();
						}
					}
				}
		 	});

			return false;
		}
	
   });
});