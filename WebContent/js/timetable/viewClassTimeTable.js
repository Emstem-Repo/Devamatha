
	$(document).ready(function() {
	    // Tooltip only Text
	    $('.display').tooltipster();
	    $('#SubmitData').click(function(e){
	   	 var academicYear=  $.trim($('#academicYear').val());
	   	 var classes=  $.trim($('#class').val());
	   	 if(academicYear=="" && classes=="")
	   	 {
	   		 $('#errorMessage').slideDown().html("<span>Please select Academic year and Classes.</span>");
	   		 return false;
	   	 }
	   	 else if(academicYear=="")
	   	  {
	   	  $('#errorMessage').slideDown().html("<span>Please select Academic year.</span>");
	   	  return false;
	   	  }
	   	 else if(classes=="")
	   	  {
	   	  $('#errorMessage').slideDown().html('<span>Please select Classes.</span>');
	   	  return false;
	   	  }else{
	   		  e.preventDefault();
	   		  document.getElementById("method").value="viewClassTimeTable";
			  document.viewTeacherWiseTimeTableForm.submit();
	   	  }
	    });
	});
	
	function cancel(){
		document.location.href = "LoginAction.do?method=loginAction";	
	}
	function getClasses(year) {
		var teacherId = document.getElementById("userId").value;
		getClassesByTeacherAndYear("classMap", year,teacherId, "class", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "class", "- Select -");
	}
	var year = document.getElementById("yr").value;
	if(year!=null && year!=""){
		document.getElementById("academicYear").value = year;
	}