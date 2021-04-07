	function getPrograms(programType) {
		if(programType.value != '0') {
			var args = "method=getProgramsByProgramType&programTypeId="+programType.value;
		  	var url ="AjaxRequest.do";
		  	// make an request to server passing URL need to be invoked and arguments.
			requestOperation(url,args,updateProgram);
		} else {
			 var program = document.getElementById("program");
			 for (x1=program.options.length-1; x1>0; x1--)
			 {
				 program.options[x1]=null;
			 }
		}	
	}
	
	function updateProgram(req) {
		updateOptionsFromMap(req,"program"," Select "); 
	}
	
	function getCourse(program) {		
		if(program.value != '0') {
			var args = "method=getCourseByProgram&programId="+program.value;
		  	var url ="AjaxRequest.do";
		  	// make an request to server passing URL need to be invoked and arguments.
			requestOperation(url,args,updateCourse);
		} else {
			 var course = document.getElementById("course");
			 for (x1=course.options.length-1; x1>0; x1--)
			 {
				 course.options[x1]=null;
			 }
		}	
	}
	
	function updateCourse(req) {
		updateOptionsFromMap(req,"course"," Select "); 
	}
