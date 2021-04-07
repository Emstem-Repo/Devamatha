	$(function(){
		  $("input[type='checkbox']").change(function(e) {
		    if($(this).is(":checked")){ 
		   $(this).closest('tr').addClass("highlight");
		  }
		    else{
		      $(this).closest('tr').removeClass("highlight");
		    }
		  });
		});
	function changeColor(){
        $(':checkbox').each(function(){
             if(this.checked){
            	 $(this).closest('tr').addClass("highlight");
                 }
             else{
            	 $(this).closest('tr').removeClass("highlight");
                 }
            });
		} 

function backMenuScreen() {
	document.getElementById("method").value = "initMenuScreen";
	document.screenMasterForm.submit();
  }
	function addMenuAssign() {
		document.getElementById("method").value = "addMenuAssignAggrement";
		document.screenMasterForm.submit();
	}
	function selectAll(obj) {
		var value = obj.checked;
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox') {
	                  inputObj.checked = value;
	                  inputObj.value="on";
	            }
	    }
	}
	
	function unCheckSelectAll() {
		 var inputs = document.getElementsByTagName("input");
		 var inputObj;
		 var checkBoxOthersSelectedCount = 0;
		 var checkBoxOthersCount = 0;
		 for(var count1 = 0;count1<inputs.length;count1++) {
		          inputObj = inputs[count1];
		          var type = inputObj.getAttribute("type");
		            if (type == 'checkbox' && inputObj.id != "checkAll") {
		                  checkBoxOthersCount++;
		                  if(inputObj.checked) {
		                        checkBoxOthersSelectedCount++;
		                        inputObj.value="on";
		                  }else{
		                	  inputObj.value="off";	
		                      }   
		            }
		    }
		    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
		      document.getElementById("checkAll").checked = false;
		    } else {
		      document.getElementById("checkAll").checked = true;
		    } 
	}

	