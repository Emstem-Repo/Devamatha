function getArrayValues(object) {
	array = new Array();
	for ( var i = 0; i < object.options.length; i++) {
		if (object.options[i].selected)
			array.push(object.options[i].value);
	}
	return array;
}

function resetOption(destinationOption) {
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
}

function updateOptionsFormList(req, destinationOption, defaultSelectValue) {
	var responseObj = req.responseXML.documentElement;

	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}

	destination.options[0] = new Option(defaultSelectValue, "");
	var items = responseObj.getElementsByTagName("optionvalue");

	for ( var I = 0; I < items.length; I++) {
		var temp = items[I].firstChild.nodeValue;
		destination.options[I + 1] = new Option(temp, temp);

	}
}

function updateOptionsFromMap(req, destinationOption, defaultSelectValue) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	var childNodes = responseObj.childNodes;
	destination.options[0] = new Option(defaultSelectValue, "");
	var items = responseObj.getElementsByTagName("option");

	var label, value;
	for ( var i = 0; i < items.length; i++) {
		label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		destination.options[i + 1] = new Option(label, value);
	}
}
function updateOptionsFromMap1(req, destinationOption, defaultSelectValue,
		parentNode1, parentNode2) {

	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	var childNodes = responseObj.childNodes;
	destination.options[0] = new Option(defaultSelectValue, "");
	var items = responseObj.getElementsByTagName("option");

	var label, value;
	if (items.length > 0) {
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i + 1] = new Option(label, value);

		}
		document.getElementById(parentNode1).style.display = "block";
		document.getElementById(parentNode2).style.display = "block";
		if (parentNode1 == "ast") {
			document.getElementById("validationAST").value = "yes";
		} else {
			document.getElementById("validationET").value = "yes";
		}
	} else {
		if (parentNode1 == "ast") {
			document.getElementById("validationAST").value = "no";
		} else {
			document.getElementById("validationET").value = "no";
		}
		document.getElementById(parentNode1).style.display = "none";
		document.getElementById(parentNode2).style.display = "none";
	}

}
function updateOptionsFromMapForNonDefaultSelection(req, destinationOption) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	var childNodes = responseObj.childNodes;
	var items = responseObj.getElementsByTagName("option");

	var label, value;
	for ( var i = 0; i < items.length; i++) {
		label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		destination.options[i] = new Option(label, value);
	}
}

function updateOptionsFromMapMultiselect(req, destinationOption,
		defaultSelectValue) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	var childNodes = responseObj.childNodes;
	var items = responseObj.getElementsByTagName("option");
	var label, value;
	if(items.length!=0)
	{	
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i] = new Option(label, value);
		}
	}
	else
	{
		destination.options[0] = null;
	}	
}	

function updateOptionsFromMapValues(req, elementId) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("value");
	for ( var I = 0; I < items.length; I++) {
		if(items[I].firstChild!=null){
		var temp = items[I].firstChild.nodeValue;
		document.getElementById(elementId).value = temp;
		
		if(elementId=="candidates")
			document.getElementById("noOfCandidates_orig").value = temp;
		
		if(elementId=="regNoID")
			document.getElementById("regNoID").value = temp;
		
		}else{
			if(elementId=="regNoID")
				document.getElementById("regNoID").value = "";
		}
	}

}

function populateSecondOptionsValues(sourceId, args, destinationProperty,
		callback) {
	if (sourceId.length != 0) {
		var destinationOption = document.getElementById(destinationProperty);
		destinationOption.options[0] = new Option("- Loading -", "");
		destinationOption.selectedIndex = 0;
		var url = "AjaxRequest.do";
		// make an request to server passing URL need to be invoked and
		// arguments.
		requestOperationProgram(url, args, callback);
	} else {
		var destinationOption = document.getElementById(destinationProperty);
		for (x1 = destinationOption.options.length - 1; x1 > 0; x1--) {
			destinationOption.options[x1] = null;
		}
	}
}

function populateSecondOptionsValuesForNoLoadingOption(sourceId, args,
		destinationProperty, callback) {
	if (sourceId.length != 0) {
		var destinationOption = document.getElementById(destinationProperty);
		destinationOption.selectedIndex = 0;
		var url = "AjaxRequest.do";
		// make an request to server passing URL need to be invoked and
		// arguments.
		requestOperation(url, args, callback);
	} else {
		var destinationOption = document.getElementById(destinationProperty);
		for (x1 = destinationOption.options.length - 1; x1 > 0; x1--) {
			destinationOption.options[x1] = null;
		}
	}
}
function populateManyFileds(args, callback) {
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperation(url, args, callback);
}
function populateStringResult(args, callback) {
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperation(url, args, callback);
}
// This function loads program options based on program type.
function getProgramsByType(programTypeId, destinationProperty, callback) {
	var args = "method=getProgramsByProgramType&programTypeId=" + programTypeId;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}

// This function loads course options based on program
function getCoursesByProgram(programId, destinationProperty, callback) {
	var args = "method=getCourseByProgram&programId=" + programId;
	populateSecondOptionsValues(programId, args, destinationProperty, callback);
}
// This function loads state options based on country
function getStatesByCountry(countryId, destinationProperty, callback) {
	var args = "method=getStatesByCountry&countryId=" + countryId;
	populateSecondOptionsValues(countryId, args, destinationProperty, callback);
}
// This function loads cities options based on state
function getCitiesByState(stateId, destinationProperty, callback) {
	var args = "method=getCitiesByState&stateId=" + stateId;
	populateSecondOptionsValues(stateId, args, destinationProperty, callback);
}
// This function loads cities options based on country
function getCitiesByCountry(countryId, destinationProperty, callback) {
	var args = "method=getCitiesByCountry&countryId=" + countryId;
	populateSecondOptionsValues(countryId, args, destinationProperty, callback);
}
// This function university course options based on college
function getCollegeByUniversity(universityId, destinationProperty, callback) {
	var args = "method=getCollegeByUniversity&universityId=" + universityId;
	populateSecondOptionsValues(universityId, args, destinationProperty,
			callback);
}

function getSubReligionByReligion(religionId, destinationProperty, callback) {
	var args = "method=getSubReligionByReligion&religionId=" + religionId;
	populateSecondOptionsValues(religionId, args, destinationProperty, callback);
}

function getSubjectGroupsByCourse(courseId, destinationProperty, callback) {
	var args = "method=getSubjectGroupsByCourse&courseId=" + courseId;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}
// This function loads Semester year no options based on course and year.
function getSemistersOnYearAndCourse(courseId, destinationProperty, year,
		callback) {
	var args = "method=getSemistersOnYearAndCourse&courseId=" + courseId
			+ "&year=" + year;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function getSubjectGrpByYearAndCourse(courseId, destinationProperty, year,
		semisterNo, callback) {
	var args = "method=getSemistersByYearAndCourse&courseId=" + courseId
			+ "&year=" + year + "&semister=" + semisterNo;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

// This function loads program options based on program type.
function getProgramsByType(propertyName, programTypeId, destinationProperty,
		callback) {
	var args = "method=getProgramsByProgramType&programTypeId=" + programTypeId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}

// This function loads program options based on program type.
function getApplicationProgramsByType(propertyName, programTypeId,
		destinationProperty, callback) {
	var args = "method=getApplnProgramsByProgramType&programTypeId="
			+ programTypeId + "&propertyName=" + propertyName;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}

// This function loads activity options based on attendance type.
function getActivityByType(propertyName, attendanceType, destinationProperty,
		callback) {
	var args = "method=getActivityByAttendenceType&attendanceTypeids="
			+ attendanceType + "&propertyName=" + propertyName;
	populateSecondOptionsValues(attendanceType, args, destinationProperty,
			callback);
}

// This function loads course options based on program
function getCoursesByProgram(propertyName, programId, destinationProperty,
		callback) {
	var args = "method=getCourseByProgram&programId=" + programId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(programId, args, destinationProperty, callback);
}
// This function loads state options based on country
function getStatesByCountry(propertyName, countryId, destinationProperty,
		callback) {
	var args = "method=getStatesByCountry&countryId=" + countryId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(countryId, args, destinationProperty, callback);
}
// This function loads cities options based on state
function getCitiesByState(propertyName, stateId, destinationProperty, callback) {
	var args = "method=getCitiesByState&stateId=" + stateId + "&propertyName="
			+ propertyName;
	populateSecondOptionsValues(stateId, args, destinationProperty, callback);
}
// This function loads cities options based on country
function getCitiesByCountry(propertyName, countryId, destinationProperty,
		callback) {
	var args = "method=getCitiesByCountry&countryId=" + countryId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(countryId, args, destinationProperty, callback);
}
// This function university course options based on college
function getCollegeByUniversity(propertyName, universityId,
		destinationProperty, callback) {
	var args = "method=getCollegeByUniversity&universityId=" + universityId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(universityId, args, destinationProperty,
			callback);
}            

function getSubReligionByReligion(propertyName, religionId,
		destinationProperty, callback) {
	var args = "method=getSubReligionByReligion&religionId=" + religionId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(religionId, args, destinationProperty, callback);
}
function getUniquePreferenceList(propertyName, prefID, destinationProperty,
		callback) {
	var args = "method=getUpdatedPreferences&secPrefId=" + prefID
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(prefID, args, destinationProperty, callback);
}
function getSubjectGroupsByCourse(propertyName, courseId, destinationProperty,
		callback) {
	var args = "method=getSubjectGroupsByCourse&courseId=" + courseId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}
function getSemistersOnYearAndCourse(propertyName, courseId,
		destinationProperty, year, callback) {
	var args = "method=getSemistersByYearAndCourse&courseId=" + courseId
			+ "&year=" + year + "&propertyName=" + propertyName;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}
function getSemistersOnYearAndCourseScheme(propertyName, courseId,
		destinationProperty, year, callback) {
	var args = "method=getSemistersByYearAndCourseScheme&courseId=" + courseId
			+ "&year=" + year + "&propertyName=" + propertyName;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}
function getSubjectGrpByYearAndCourse(propertyName, courseId,
		destinationProperty, year, semisterNo, callback) {
	var args = "method=getSubjectGroupByYearAndCourse&courseId=" + courseId
			+ "&year=" + year + "&semister=" + semisterNo + "&propertyName="
			+ propertyName;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function getBatchesBySubject(propertyName, subjectId, selectedClasses,
		destinationProperty, callback) {
	var args = "method=getBatchesBySubjects&selectedClassesArray="
			+ selectedClasses + "&subjectId=" + subjectId + "&propertyName="
			+ propertyName;
	populateSecondOptionsValuesForNoLoadingOption(subjectId, args,
			destinationProperty, callback);
}

// added for hostelAdminMessage
function getRoomsMapByHostelId(propertyName, hostelId, destinationProperty,
		callback) {
	var args = "method=getRoomsMapByHostelId&hostelId=" + hostelId;
	populateSecondOptionsValues(hostelId, args, destinationProperty, callback);
}

// This function loads interview types based on course

function getInterviewTypeByCourse(propertyName, courseId, year,
		destinationProperty, callback) {

	var args = "method=interviewTypeByCourse&courseId=" + courseId + "&year="
			+ year + "&propertyName=" + propertyName;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperation(url, args, callback);

}
// This function loads interview types based on course

function getInterviewTypeByProgram(propertyName, programId, year,
		destinationProperty, callback) {

	var args = "method=interviewTypeByProgram&programId=" + programId
			+ "&year=" + year + "&propertyName=" + propertyName;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperationProgram(url, args, callback);

}

// This function loads interview subrounds based on interviewType.
function getInterviewTypeByProgram1(propertyName, programId, year,
		destinationProperty, callback) {
	var args = "method=interviewTypeByProgram&programId=" + programId
			+ "&year=" + year + "&propertyName=" + propertyName;

	populateSecondOptionsValues(programId, args, destinationProperty, callback);
}

// This function loads interview subrounds based on interviewType.
function getInterviewSubroundsByInterviewtype(propertyName, interviewTypeId,
		destinationProperty, callback) {
	var args = "method=interviewSubroundsByInterviewType&interviewTypeId="
			+ interviewTypeId + "&propertyName=" + propertyName;
	populateSecondOptionsValues(interviewTypeId, args, destinationProperty,
			callback);
}

function getInterviewSubroundsByApplicationId(propertyName, interviewTypeId,
		applicationId, destinationProperty, callback) {
	var args = "method=interviewSubroundsByApplicationId&interviewTypeId="
			+ interviewTypeId + "&applicationId=" + applicationId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(interviewTypeId, args, destinationProperty,
			callback);
}

function getMandatoryFieldsByAttendanceType(propertyName, attendanceTypeId,
		callback) {
	var args = "method=getMandatoryFieldsByAttendanceType&attendanceTypeId="
			+ attendanceTypeId + "&propertyName=" + propertyName;
	populateManyFileds(args, callback);
}

function updateOptionsFromMapWithOther(req, destinationOption,
		defaultSelectValue) {
	var responseObj = req.responseXML.documentElement;

	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	var childNodes = responseObj.childNodes;
	destination.options[0] = new Option("-Select-", "");
	var items = responseObj.getElementsByTagName("option");

	var label, value;
	for ( var i = 0; i < items.length; i++) {
		label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		destination.options[i + 1] = new Option(label, value);
	}
	destination.options[items.length + 1] = new Option("Other", "Other");

}

function getSubjectsByClass(propertyName, classSchemewiseId,
		destinationProperty, callback) {
	var args = "method=getSubjectsByClass&classSchemewiseId="
			+ classSchemewiseId;
	populateSecondOptionsValues(classSchemewiseId, args, destinationProperty,
			callback);
}

function getSubjectsPeriodsBatchForClassSendRequest(selectedClasses, year,
		callback) {
	var args = "method=getSubjectsPeriodsBatchForClass&selectedClassesArray="
			+ selectedClasses + "&year" + year;
	populateManyFileds(args, callback);
}
function getSubjectByType(propertyName, selectedClasses, destinationProperty,
		callback) {

	var args = "method=getSubjectsByMultipleClasses&selectedClassesArray="
			+ selectedClasses + "&propertyName=" + propertyName;

	populateSecondOptionsValues(selectedClasses, args, destinationProperty,
			callback);
}

function getPeriodsByClassSchemewiseId(propertyName, classSchemewiseId,
		destinationProperty, callback) {
	var args = "method=getPeriodByClassSchemewiseId&classSchemewiseId="
			+ classSchemewiseId;
	populateSecondOptionsValues(classSchemewiseId, args, destinationProperty,
			callback);
}
function getPeriodsByClassSchemewiseIds(propertyName, classSchemewiseId,
		destinationProperty, callback) {
	var args = "method=getPeriodByClassSchemewiseIds&classSchemewiseId="
			+ classSchemewiseId;
	populateSecondOptionsValuesForNoLoadingOption(classSchemewiseId, args,
			destinationProperty, callback);
}

// This function loads classes options based on year
function getClassesByYear(propertyName, year, destinationProperty, callback) {
	var args = "method=getClassesByYear&year=" + year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}
function getClassesByYearFromGeneratedTimeTable(propertyName, year,
		destinationProperty, callback) {
	var args = "method=getClassesByYearFromGeneratedTimeTable&year=" + year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}
// This function loads subjects options based on course
function getSubjectsByCourse(propertyName, courseId, destinationProperty,
		callback) {
	var args = "method=getSubjectsByCourse&courseId=" + courseId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

// This function loads floors based on hostel.
function getFloorsByHostel(propertyName, hostelId, destinationProperty,
		callback) {
	var args = "method=getFloorsByHostel&hostelId=" + hostelId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(hostelId, args, destinationProperty, callback);
}

// This function loads subjects options based on course
function getGroupsByHostel(propertyName, hostelId, destinationProperty,
		callback) {
	var args = "method=getHlGroupsByHostel&hostelId=" + hostelId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(hostelId, args, destinationProperty, callback);
}

// This function loads classes options based on year
function getClassesByYearInMuliSelect(propertyName, year, destinationProperty,
		callback) {
	var args = "method=getClassesByYear&year=" + year;
	populateSecondOptionsValuesForMuliSelect(year, args, destinationProperty,
			callback);
}

// This function loads subjects options based on course
function getRoomTypeByHostel(propertyName, hostelId, destinationProperty,
		callback) {
	var args = "method=getRoomTypeByHostel&hostelId=" + hostelId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(hostelId, args, destinationProperty, callback);
}
function getRoomsByHostel(propertyName, hostelId, destinationProperty, callback) {
	var args = "method=getRoomsByHostel&hostelId=" + hostelId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(hostelId, args, destinationProperty, callback);
}
// This function loads subjects options based on course
function getSubjectsByCourseForMultiSelect(propertyName, courseId,
		destinationProperty, callback) {
	var args = "method=getSubjectsByCourse&courseId=" + courseId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValuesForMuliSelect(courseId, args,
			destinationProperty, callback);
}

function updateOptionsFromMapForMultiSelect(req, destinationOption) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 >= 0; x1--) {
		destination.options[x1] = null;
	}
	var childNodes = responseObj.childNodes;
	var items = responseObj.getElementsByTagName("option");

	var label, value;
	for ( var i = 0; i < items.length; i++) {
		label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		destination.options[i] = new Option(label, value);
	}
}

function populateSecondOptionsValuesForMuliSelect(sourceId, args,
		destinationProperty, callback) {
	if (sourceId.length != 0) {
		var destinationOption = document.getElementById(destinationProperty);
		destinationOption.options[0] = new Option("- Loading -", "");
		destinationOption.selectedIndex = 0;
		var url = "AjaxRequest.do";
		// make an request to server passing URL need to be invoked and
		// arguments.
		requestOperation(url, args, callback);
	} else {
		destinationOption = document.getElementById(destinationProperty);
		for (x1 = destinationOption.options.length - 1; x1 >= 0; x1--) {
			destinationOption.options[x1] = null;
		}
	}
}
// This function loads employees options based on department
function getEmployeesByDepartment(propertyName, department,
		destinationProperty, callback) {
	var args = "method=getEmployeesByDepartment&departmentId=" + department;
	populateSecondOptionsValues(department, args, destinationProperty, callback);
}
// This function loads classes options based on year
function getClassesByProgram(propertyName, programId, destinationProperty,
		callback) {
	var args = "method=getClassesByProgram&programId=" + programId;
	populateSecondOptionsValues(programId, args, destinationProperty, callback);
}

// This function loads interview types based on course

function getInterviewTypeByCourseWithoutYear(propertyName, courseId,
		destinationProperty, callback) {

	var args = "method=interviewTypeByCourseWithoutYear&courseId=" + courseId
			+ "&propertyName=" + propertyName;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperation(url, args, callback);

}

function interviewTypeByProgramWithoutYear(propertyName, programId,
		destinationProperty, callback) {
	var args = "method=interviewTypeByProgramWithoutYear&programId="
			+ programId + "&propertyName=" + propertyName;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperation(url, args, callback);
}
// This function loads classes options based on programType
function getClassesByProgramType(propertyName, programTypeId,
		destinationProperty, callback) {
	var args = "method=getClassesByProgramType&programTypeId=" + programTypeId;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}
// This function loads no of rooms based on hostel id,roomTypeId and floor no .
function getRoomsByFloors(propertyName, hostelId, floorNo, roomTypeId,
		destinationProperty, callback) {
	var args = "method=getRoomsByFloors&hostelId=" + hostelId + "&floorNo="
			+ floorNo + "&roomId=" + roomTypeId + "&propertyName="
			+ propertyName;
	populateSecondOptionsValuesForHostelAllocation(hostelId, floorNo,
			roomTypeId, args, destinationProperty, callback);
}

function populateSecondOptionsValuesForHostelAllocation(hostelId, floorNo,
		roomTypeId, args, destinationProperty, callback) {
	if (hostelId.length != 0 && floorNo.length != 0 && roomTypeId.length != 0) {
		var destinationOption = document.getElementById(destinationProperty);
		destinationOption.options[0] = new Option("- Loading -", "");
		destinationOption.selectedIndex = 0;
		var url = "AjaxRequest.do";
		// make an request to server passing URL need to be invoked and
		// arguments.
		requestOperation(url, args, callback);
	} else {
		var destinationOption = document.getElementById(destinationProperty);
		for (x1 = destinationOption.options.length - 1; x1 > 0; x1--) {
			destinationOption.options[x1] = null;
		}
	}
}
function getBedByRoomId(propertyName, roomTypeId, destinationProperty, callback) {
	var args = "method=getBedByRoomId&roomId=" + roomTypeId;
	populateSecondOptionsValues(roomTypeId, args, destinationProperty, callback);
}
// -----------------------Exam Module Starts ----------------------------
function getCoursesByProgramType(propertyName, programTypeId,
		destinationProperty, callback) {

	var args = "method=getCoursesByProgramType&programTypeId=" + programTypeId
			+ "&propertyName=" + propertyName;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperation(url, args, callback);

}

function getCoursesByAcademicYear(propertyName, academicYear,
		destinationProperty, callback) {
	var args = "method=getCoursesByAcademicYear&academicYear=" + academicYear;
	populateSecondOptionsValues(academicYear, args, destinationProperty,
			callback);
}

function getSchemeByCourseId(propertyName, courseId, destinationProperty,
		callback) {
	var args = "method=getSchemeByCourseId&courseId=" + courseId;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function getProgramsByPType(propertyName, programTypeId, destinationProperty,
		callback) {
	var args = "method=getProgramsByPType&programTypeId=" + programTypeId;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}

// This function loads program options based on program type.
function getProgramsByPTypes(programTypeId, destinationProperty, callback) {
	var args = "method=getProgramsByPTypes&programTypeId=" + programTypeId;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}
function getInterviewSubrounds(propertyName, interviewTypeId,
		destinationProperty, callback) {
	var args = "method=getInterviewSubrounds&interviewTypeids="
			+ interviewTypeId + "&propertyName=" + propertyName;
	populateSecondOptionsValues(interviewTypeId, args, destinationProperty,
			callback);
}
function getClasesByExamName(propertyName, examName, destinationProperty,
		callback) {
	var args = "method=getClasesByExamName&examName=" + examName;
	populateSecondOptionsValues(examName, args, destinationProperty, callback);
}
function getProgramesByDepartmentId(propertyName, deptname, destinationProperty,
		callback) {
	var args = "method=getProgramesByDepartmentId&programId=" + deptname;
	populateSecondOptionsValues(deptname, args, destinationProperty, callback);
}
function getClasesByJoingBatch(propertyName, joingBatch, destinationProperty,
		callback) {
	var args = "method=getClasesByJoingBatch&joiningBatch=" + joingBatch;
	populateSecondOptionsValues(joingBatch, args, destinationProperty, callback);
}

function getSchemeNameByCourseId(propertyName, courseID, destinationProperty,
		callback) {
	var args = "method=getSchemeNameByCourseId&courseId=" + courseID;
	populateSecondOptionsValues(courseID, args, destinationProperty, callback);
}

function getCourseByExamName(propertyName, examName, destinationProperty,
		callback) {
	var args = "method=getCourseByExamName&examName=" + examName;
	populateSecondOptionsValues(examName, args, destinationProperty, callback);
}

function getRoomTypesByHostel(propertyName, hostelId, destinationProperty,
		callback) {
	var args = "method=getRoomTypesByHostel&hostelId=" + hostelId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValuesForHostelStatusInfo(hostelId, args,
			destinationProperty, callback);
}

function populateSecondOptionsValuesForHostelStatusInfo(sourceId1, args,
		destinationProperty, callback) {
	if (sourceId1.length != 0) {
		var destinationOption = document.getElementById(destinationProperty);
		destinationOption.options[0] = new Option("- Loading -", "");

		destinationOption.selectedIndex = 0;
		var url = "AjaxRequest.do";
		// make an request to server passing URL need to be invoked and
		// arguments.
		requestOperation(url, args, callback);
	} else {
		var destinationOption = document.getElementById(destinationProperty);
		for (x1 = destinationOption.options.length - 1; x1 >= 0; x1--) {
			destinationOption.options[x1] = null;
		}
	}
}
function getExamNameByExamTypeId(propertyName, examTypeId, destinationProperty,
		callback) {
	var args = "method=getExamNameByExamTypeId&examTypeId=" + examTypeId;
	// populateManyFileds(args, callback);
	populateSecondOptionsValues(examTypeId, args, destinationProperty, callback);
}

function getSchemeNoByCourseId(propertyName, courseId, destinationProperty,
		callback) {
	var args = "method=getSchemeNoByCourseId&courseId=" + courseId;
	// populateManyFileds(args, callback);
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function getSchemeValuesBySchemeId(propertyName, fromSchemeID,
		destinationProperty, callback, toSchemeId) {

	var args = "method=getSchemeValuesBySchemeId&fromScheme=" + fromSchemeID
			+ "&toScheme=" + toSchemeId;
	populateSecondOptionsValues(fromSchemeID, args, destinationProperty,
			callback);
}

function getSubjectsByCourse(propertyName, courseId, schemeId,
		destinationProperty, callback) {

	var args = "method=getSubjectsByCourse&courseId=" + courseId + "&schemeId="
			+ schemeId;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function getExamNameByExamType(propertyName, examType, destinationProperty,
		callback, toSchemeId) {
	var args = "method=getExamNameByExamType&examType=" + examType;
	populateSecondOptionsValues(examType, args, destinationProperty, callback);
}

function getCourseByExamNameRegNoRollNo(propertyName, examName, regNo, rollNo,
		destinationProperty, callback) {
	var args = "method=getCourseByExamNameRegNoRollNo&examName=" + examName
			+ "&regNo=" + regNo + "&rollNo=" + rollNo;
	populateSecondOptionsValues(examName, args, destinationProperty, callback);
}

function getSubjectsByCourseScheme(propertyName, courseId, destinationProperty,
		callback, schemeId) {

	var args = "method=getSubjectsByCourseScheme&courseId=" + courseId
			+ "&schemeId=" + schemeId;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function getSubjectsByCourseSchemeExamId(propertyName, courseId,
		destinationProperty, callback, schemeId, examId) {

	var args = "method=getSubjectsByCourseSchemeExamId&courseId=" + courseId
			+ "&schemeId=" + schemeId + "&examName=" + examId;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}
function getSubjectsByCourseSchemeExamIdJBY(propertyName, courseId,
		destinationProperty, callback, schemeId, examId, jby) {

	var args = "method=getSubjectsByCourseSchemeExamIdJBY&courseId=" + courseId
			+ "&schemeId=" + schemeId + "&examName=" + examId
			+ "&joiningBatch=" + jby;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}
function getSubjectTypeValue(subject) {

	var args = "method=getSubjectTypeValue&subject=" + subject;

}

function getSchemeByCourseIdAcademicId(propertyName, courseId, academicYear,
		destinationProperty, callback) {
	var args = "method=getSchemeNameByCourseIdAcademicYear&courseId="
			+ courseId + "&academicYear=" + academicYear;

	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}
function getSectionByCourseId(propertyName, courseId, destinationProperty,
		callback) {
	var args = "method=getSectionByCourseId&schemeId=" + courseId;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function getSectionByCourseIdSchemeId(propertyName, courseId, schemeId,
		academicYear, destinationProperty, callback) {
	var args = "method=getSectionByCourseIdSchemeId&courseId=" + courseId
			+ "&schemeId=" + schemeId + "&academicYear=" + academicYear
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(schemeId, args, destinationProperty, callback);
}

function getSchemeNo_SchemeIDByCourseIdAcademicId(propertyName, courseId,
		academicYear, destinationProperty, callback) {

	var args = "method=getSchemeNo_SchemeIDByCourseIdAcademicId&courseId="
			+ courseId + "&academicYear=" + academicYear;

	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function getProgramNameByAcademicYear(propertyName, academicYear,
		destinationProperty, callback) {
	var args = "method=getProgramNameByAcademicYear&academicYear="
			+ academicYear;
	populateSecondOptionsValues(academicYear, args, destinationProperty,
			callback);
}

function getClasesByAcademicYear(propertyName, academicYear,
		destinationProperty, callback) {
	var args = "method=getClasesByAcademicYear&academicYear=" + academicYear;
	populateSecondOptionsValues(academicYear, args, destinationProperty,
			callback);
}

function getClassCodeByExamName(propertyName, examName, destinationProperty,
		callback) {
	var args = "method=getClassCodeByExamName&examName=" + examName;
	populateSecondOptionsValues(examName, args, destinationProperty, callback);
}
// returns Program-Course for multiple program Types

function getCoursesByProgramTypes(propertyName, programTypeId,
		destinationProperty, callback) {
	var args = "method=getCoursesByProgramTypes&programTypeId=" + programTypeId;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}
// -----------------------Exam Module End ----------------------------
// This function loads course options based on program
function getCoursesByProgram(propertyName, programId, destinationProperty,
		callback) {
	var args = "method=getCourseByProgram&programId=" + programId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(programId, args, destinationProperty, callback);
}

function getExamCenterByProgram(propertyName, programId, destinationProperty,
		callback) {
	var args = "method=getExamCenterByProgram&programId=" + programId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(programId, args, destinationProperty, callback);

}
// EXAM MODULE ------ 7th may ------

function getProgramsByPTypes(propertyName, programTypeId, destinationProperty,
		callback) {
	var args = "method=getProgramsByPTypes&programTypeId=" + programTypeId;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}

function getExamNameByAcademicYear(propertyName, academicYear,
		destinationProperty, callback) {
	var args = "method=getExamNameByAcademicYear&academicYear=" + academicYear;
	populateSecondOptionsValues(academicYear, args, destinationProperty,
			callback);

}
function updateInternalComponentsByClasses(req, elementId) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("value");

	for ( var I = 0; I < items.length; I++) {
		var temp = items[I].firstChild.nodeValue;
		if (temp == 0) {
			document.getElementById("publishOverallInternalComponentsOnly").disabled = true;
			document.getElementById("publishOverallInternalComponentsOnly").checked = false;
		}
		if (temp != 0) {
			document.getElementById("publishOverallInternalComponentsOnly").disabled = false;
			document.getElementById("publishOverallInternalComponentsOnly").checked = false;
		}
	}

}
function getSchemeNoByExamIdCourseId(propertyName, examId, courseId,
		destinationProperty, callback) {
	var args = "method=getSchemeNoByExamIdCourseId&examName=" + examId
			+ "&courseId=" + courseId  + "&propertyName=" + propertyName;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function getSubjectsByCourseIdSchemeNo(propertyName, courseId, schemeId,
		destinationProperty, callback) {
	var args = "method=getSubjectsByCourseIdSchemeNo&courseId=" + courseId
			+ "&schemeId=" + schemeId;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function updateSubjectsTypeBySubjectId(req, elementId) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("value");
	for ( var I = 0; I < items.length; I++) {
		var temp = items[I].firstChild.nodeValue;
		if (temp == "t") {
			document.getElementById("subjectType").value = "T";

		}
		if (temp == "b") {
			document.getElementById("subjectType").value = "B";

		}
		if (temp == "p") {
			document.getElementById("subjectType").value = "P";

		}
	}

}

function updateCurrentExam(req, elementId) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("value");
	for ( var I = 0; I < items.length; I++) {
		var temp = items[I].firstChild.nodeValue;
		if (temp != 0) {
			document.getElementById(elementId).value = temp;
		}

	}

}
function getClassByExamNameRegNoRollNo(propertyName, examName, regNo, rollNo,
		destinationProperty, callback) {
	var args = "method=getClassByExamNameRegNoRollNo&examName=" + examName
			+ "&regNo=" + regNo + "&rollNo=" + rollNo;
	populateSecondOptionsValues(examName, args, destinationProperty, callback);
}

function getTypeByAssignmentOverall(propertyName, type, destinationProperty,
		callback) {
	var args = "method=getTypeByAssignmentOverall&assignmentOverallType="
			+ type;
	populateSecondOptionsValues(type, args, destinationProperty, callback);
}
function getRoomNoByExamName(propertyName, examName, destinationProperty,
		callback) {
	var args = "method=getRoomNoByExamName&examName=" + examName;
	populateSecondOptionsValues(examName, args, destinationProperty, callback);
}
function getAgreementNameByClassId(propertyName, classListId,
		destinationProperty, callback) {

	var args = "method=getAgreementNameByClassId&classCodeIds=" + classListId;
	populateSecondOptionsValues(classListId, args, destinationProperty,
			callback);
}

function getFooterNameByClassId(propertyName, classId, destinationProperty,
		callback) {
	var args = "method=getFooterNameByClassId&classCodeIds=" + classId;
	populateSecondOptionsValues(classId, args, destinationProperty, callback);
}

// ExamPublishHallTicket
function getAgreementNameByProgramTypeId(propertyName, programTypeId,
		destinationProperty, callback) {

	var args = "method=getAgreementNameByProgramId&programTypeId="
			+ programTypeId;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}

// ExamPublishHallTicket
function getFooterNameByProgramTypeId(propertyName, programTypeId,
		destinationProperty, callback) {

	var args = "method=getFooterNameByProgramId&programTypeId=" + programTypeId;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}
// Exam Student Marks Correction

function getMarkTypeByExamTypeId(propertyName, examTypeId, destinationProperty,
		callback) {
	var args = "method=getMarkTypeByExamTypeId&examType=" + examTypeId;
	populateSecondOptionsValues(examTypeId, args, destinationProperty, callback);
}

// Exam Assign Students To Room
function getSubjectNameByClassIds(propertyName, classId, date, time, examId,
		destinationProperty, callback) {
	var args = "method=getSubjectNameByClassIds&classId=" + classId + "&date="
			+ date + "&time=" + time + "&examName=" + examId;
	populateSecondOptionsValues(classId, args, destinationProperty, callback);
}
// Exam Assign Students To Room

function updateOptionsFromExamId(req, date) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("value");
	for ( var I = 0; I < items.length; I++) {
		var temp = items[I].firstChild.nodeValue;
		document.getElementById("errorMessage").innerHTML = " ";
		document.getElementById("date").value = "";
		document.getElementById("hr").value = "00";
		document.getElementById("min").value = "00";
		if (temp == "0") {
			if (document.getElementById("examNameId").value != "") {
				document.getElementById("errorMessage").innerHTML = "Exam time table is not Created";
			}
		} else {

			var a = new Array();
			var b = new Array();
			a = temp.split(" ");

			var date1 = a[0];
			var time1 = a[1];
			b = time1.split(":");
			var HR = b[0];
			var MIN = b[1];

			document.getElementById("date").value = date1;

			document.getElementById("hr").value = HR;

			document.getElementById("min").value = MIN;
		}
	}

}

function updateOptionsDateTime(req, date) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("dateTime");

	for ( var I = 0; I < items.length; I++) {
		var temp = items[I].firstChild.nodeValue;
		document.getElementById("errorMessage").innerHTML = " ";
		document.getElementById("date").value = "";
		document.getElementById("hr").value = "00";
		document.getElementById("min").value = "00";
		if (temp == "0") {
			if (document.getElementById("examNameId").value != "") {
				document.getElementById("errorMessage").innerHTML = "Exam time table is not Created";
			}

		} else {

			var a = new Array();
			var b = new Array();
			a = temp.split(" ");

			var date1 = a[0];
			var time1 = a[1];
			b = time1.split(":");
			var HR = b[0];
			var MIN = b[1];

			document.getElementById("date").value = date1;

			document.getElementById("hr").value = HR;

			document.getElementById("min").value = MIN;
		}
	}

}

function getTheoryMarks(req, elementV) {

	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("value");
	document.getElementById("errorMessage").innerHTML = "";

	for ( var I = 0; I < items.length; I++) {
		if (items[I].firstChild != '' && items[I].firstChild.length > 0) {
			var temp = items[I].firstChild.nodeValue;
			if (temp != "0") {
				document.getElementById("errorMessage").innerHTML = "Maximum marks is "
						+ temp;
				document.getElementById(elementV).focus;
			}
		}
	}

}

function updateStudentNameByRegNoOrRollNo(req, elementId) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("value");
	for ( var I = 0; I < items.length; I++) {
		var temp = items[I].firstChild.nodeValue;
		document.getElementById("studentName").value = temp;
	}
}
// exam secured marks entry
function updateDecryptRegNo(req, elementId) {
	var responseObj = req.responseXML.documentElement;
	var items1 = responseObj.getElementsByTagName("value");
	var temp = items1[0].firstChild.nodeValue;
	if (temp == '$$$###') {
		
		document.getElementById("error").innerHTML = "No Record Found";
		document.getElementById("marksEntryId_" + elementId).value = -1;
		document.getElementById("detailId_" + elementId).value = -1;
	} else if (temp == '$$$$$#####') {
		
		document.getElementById("error").innerHTML = "No Record Found";
		if(elementId == 0){
			document.getElementById("regNo_"+0).focus();
		}else{
			document.getElementById("regNo_"+elementId).focus();
		}
	} else if(temp == '$$$$####'){
		document.getElementById("error").innerHTML = "Student Record Already Existed.";
		if(elementId == 0){
			document.getElementById("regNo_"+0).focus();
		}else{
			document.getElementById("regNo_"+elementId).focus();
		}
	} else {
		var t = new Array();
		t = temp.split("_");
		document.getElementById("display_" + elementId).innerHTML = t[0]
				+ "   " + t[5];
		document.getElementById("theoryMarks_" + elementId).value = t[1];
		document.getElementById("practicalMarks_" + elementId).value = t[2];
		document.getElementById("theoryMarksHidden_" + elementId).value = t[1];
		document.getElementById("practicalMarksHidden_" + elementId).value = t[2];
		document.getElementById("error").innerHTML = "";

		if (t[3] == 'true') {
			document.getElementById("mistake_" + elementId).checked = t[3];
			document.getElementById("mistake_" + elementId).disabled = true;
			document.getElementById("mistakeHidden_" + elementId).value = t[3];

		}
		
		document.getElementById("retest_" + elementId).value = t[4];
		
		document.getElementById("error_" + elementId).innerHTML = "";
		
		document.getElementById("marksEntryId_" + elementId).value = t[6];
		document.getElementById("detailId_" + elementId).value = t[7];
	}
}
// exam secured marks entry
function updateMarks(req, elementId) {
	var responseObj = req.responseXML.documentElement;
	var items1 = responseObj.getElementsByTagName("value");
	var temp = items1[0].firstChild.nodeValue;
	if (temp == '$$$###') {		
		document.getElementById("evaluatorMarks_" + elementId).innerHTML = "";
	}
	else
	if (temp == 'duplicate') {
		document.getElementById("marksError_" + elementId).innerHTML = "Duplicate Entry";
		document.getElementById("marksErrorHidden_" + elementId).value = "Duplicate Entry";
	}
	else
	if(temp=='noDefinition'){
		document.getElementById("marksError_" + elementId).innerHTML = "No Subject Rule Definition Found";
		document.getElementById("marksErrorHidden_" + elementId).value = "No Subject Rule Definition Found";
	}	
	else {
		var t = new Array();
		t = temp.split("_");
		if (t[0] == 'max') {
			document.getElementById("marksError_" + elementId).innerHTML = "Max Marks Allowed For This Subject Is"
					+ t[1];
			document.getElementById("marksErrorHidden_" + elementId).value = "Max Marks Allowed "
					+ t[1];
		} else if (temp == 'spl') {
			document.getElementById("marksError_" + elementId).innerHTML = "No special Character Allowed";
			document.getElementById("marksErrorHidden_" + elementId).value = "No special Character ";
		} else {
			
			document.getElementById("marksError_" + elementId).innerHTML = "";
			document.getElementById("marksErrorHidden_" + elementId).value = "";
			document.getElementById("error_" + elementId).innerHTML = "";
			if (t[1] == 'true') {
				document.getElementById("mistake_" + elementId).checked = t[1];
				document.getElementById("mistakeHidden_" + elementId).value = t[1];
			}
			if (t[2] == 'true') {
				document.getElementById("retest_" + elementId).checked = true;
				document.getElementById("retestHidden_" + elementId).value = true;
			}
			document.getElementById("marksEntryId_" + elementId).value = t[3];
			document.getElementById("detailId_" + elementId).value = t[4];
			document.getElementById("evaluatorMarks_" + elementId).innerHTML = t[0];

		}
	}
}
// exam secured marks entry
function updateStudentDetails(req, elementId, subjectType) {
	var responseObj = req.responseXML.documentElement;
	var items1 = responseObj.getElementsByTagName("value");
	var temp = items1[0].firstChild.nodeValue;
	if (temp == '$$$###') {
		document.getElementById("error_" + elementId).innerHTML = "No Record Found";
		document.getElementById("registerNo_" + elementId).value = "";
		document.getElementById("registerError_" + elementId).value = "error";
	} else {
		document.getElementById("error_" + elementId).innerHTML ="";
		document.getElementById("registerError_" + elementId).value = "";
		if (temp == 'present') {
		} else if (subjectType == 'theory' || subjectType == 'Theory') {
			document.getElementById("theoryMarks_" + elementId).value = temp;

		} else if (subjectType == 'practical' || subjectType == 'Practical') {
			document.getElementById("practicalMarks_" + elementId).value = temp;

		}
	}
}
function updateMarksByRegNO(req, elementId) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("value");
	for ( var I = 0; I < items.length; I++) {
		var temp = items[I].firstChild.nodeValue;
		document.getElementById("marksByReg").value = temp;
	}
}
function updatePrinter(req, p) {
	var responseObj = req.responseXML.documentElement;
	
}
// exam secured marks entry
function updateSubjectsTypeBySubjectIdForMarks(req, elementId) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("value");
	var destination = document.getElementById("subjectType");
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	destination.options[1] = new Option("","Select");
	var label, value;
	for ( var I = 0; I < items.length; I++) {
		var temp = items[I].firstChild.nodeValue;
		if (temp == "t" || temp == "T") {
			label ="Theory";
			value =1;
			destination.options[1] = new Option(label, value);
			document.getElementById("subjectType").value = 1;

		}
		if (temp == "b" || temp == "B") {
			label ="Theory";
			value =1;
			destination.options[1] = new Option(label, value);
			label ="Practical";
			value =0;
			destination.options[2] = new Option(label, value);
			document.getElementById("subjectType").value = 1;

		}
		if (temp == "p" || temp == "P") {
			label ="Practical";
			value =0;
			destination.options[1] = new Option(label, value);
			document.getElementById("subjectType").value = 0;

		}
	}
}
// exam secured marks entry
function updateDecryptRegNoForSecuredEntry(req, elementId, subjectType,

evaluatorType) {
	var responseObj = req.responseXML.documentElement;
	var items1 = responseObj.getElementsByTagName("value");
	var temp = items1[0].firstChild.nodeValue;
	if (temp == '$$$###') {
		document.getElementById("display_" + elementId).innerHTML = "No Record Found";
		document.getElementById("marksEntryId_" + elementId).value = -1;
		document.getElementById("detailId_" + elementId).value = -1;
	} else {
		var t = new Array();
		t = temp.split("_");
		if (subjectType == 'theory' || subjectType == 'Theory') {
			document.getElementById("theoryMarks_" + elementId).value = t[0];
			document.getElementById("theoryMarksHidden_" + elementId).value = t[0];
		} else if (subjectType == 'practical' || subjectType == 'Practical') {
			document.getElementById("practicalMarks_" + elementId).value = t[1];
			document.getElementById("practicalMarksHidden_" + elementId).value = t[1];
		}
		if (evaluatorType != '') {
			document.getElementById("evaluatorMarks_" + elementId).value = t[2];
		}
		if (t[3] == 'true') {
			document.getElementById("mistake_" + elementId).checked = t[3];
			document.getElementById("mistake_" + elementId).disabled = true;
			document.getElementById("mistakeHidden_" + elementId).value = t[3];

		}
		if (t[4] == 'true') {
			document.getElementById("retest_" + elementId).disabled = true;
			document.getElementById("retest_" + elementId).checked = t[4];
			document.getElementById("retestHidden_" + elementId).value = t[4];
		}
		document.getElementById("marksEntryId_" + elementId).value = t[5];
		document.getElementById("detailId_" + elementId).value = t[6];
	}
	function getCheckForSubjectRuleSetting(id) {
		if (document.getElementById(id).checked) {

			document.getElementById(id).value = "on";
		} else {

			document.getElementById(id).value = null;
		}
	}

}

function updateExamDateTime(req, date) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("value");
	var a = new Array();
	var b = new Array();

	var temp = items[0].firstChild.nodeValue;
	document.getElementById("errorMessage").innerHTML = " ";
	document.getElementById("date").value = "";
	document.getElementById("hr").value = "00";
	document.getElementById("min").value = "00";

	if (temp == "0") {
		if (document.getElementById("examNameId").value != "") {
			document.getElementById("errorMessage").innerHTML = "Exam time table is not Created";
		}

	} else {

		a = temp.split(" ");

		var date1 = a[0];
		var time1 = a[1];
		b = time1.split(":");
		var HR = b[0];
		var MIN = b[1];

		document.getElementById("date").value = date1;
		document.getElementById("hr").value = HR;
		document.getElementById("min").value = MIN;
	}
}
// @author jboss
function getSubjectsPeriodsBatchForTeacherAndClassSendRequest(selectedClasses,
		year, teacherId, callback) {
	var args = "method=getSubjectsPeriodsBatchForTeacherAndClass&selectedClassesArray="
			+ selectedClasses + "&year" + year + "&teacherId=" + teacherId;
	populateManyFileds(args, callback);
}

// @author jboss
function getClassesByTeacherInMuliSelect(propertyName, teacherId,
		destinationProperty, callback) {
	var year = document.getElementById("academicYear").value;
	if (year != null && year != '') {
		var args = "method=getClassesByTeacher&teacherId=" + teacherId
				+ "&year=" + year;
		populateSecondOptionsValuesForMuliSelect(teacherId, args,
				destinationProperty, callback);
	}
}
function getClassesByTeacherAndYear(propertyName, year, teachers,
		destinationProperty, callback) {
	if (teachers.selectedIndex != -1) {
		var args = "method=getClassesByTeacher&teacherId=" + teachers
				+ "&year=" + year;
		populateSecondOptionsValuesForMuliSelect(teachers, args,
				destinationProperty, callback);
	}
}

function getClassesByYearForMuliSelect(propertyName, year, destinationProperty,
		callback) {
	var args = "method=getClassesByYearForMuliSelect&year=" + year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}

function getSubjectCodeName(propertyName, sCodeName, destinationProperty,
		callback, examId) {
	var args = "";
	if(examId!= null){
		args =  "method=getSubjectCodeName&schemeNo=" + sCodeName + "&examTypeId=" + examId;
	}else{
		args =  "method=getSubjectCodeName&schemeNo=" + sCodeName;
	}
	
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperationProgram(url, args, callback);
}
function getRoomsByClassSchemewiseIds(propertyName, classes,
		destinationProperty, callback) {
	var args = "method=getRoomsByClassSchemewiseIds&classId=" + classes;

	populateSecondOptionsValues(classes, args, destinationProperty, callback);
}

function getExamNameByProcessType(propertyName, processType,year,
		destinationProperty, callback) {
	var args = "method=getExamNameByProcessType&examType=" + processType+"&academicYear="+year;

	populateSecondOptionsValues(processType, args, destinationProperty,
			callback);
}
function getInternalExamByAcademicYear(propertyName, academicYear,
		destinationProperty, callback) {
	var args = "method=getInternalExamByAcademicYear&academicYear="
			+ academicYear;

	populateSecondOptionsValues(academicYear, args, destinationProperty,
			callback);
}
function getEducationByQualificationId(propertyName, qualificationId,
		destinationProperty, callback) {
	var args = "method=getEducationByQualificationId&qualificationId="
			+ qualificationId;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperationProgram(url, args, callback);
}

function getSubjectsByCourseTermYear(propertyName, courseId, academicYear,
		schemeId, destinationProperty, callback) {
	var args = "method=getSubjectsByCourseTermYear&courseId=" + courseId
			+ "&academicYear=" + academicYear + "&schemeId=" + schemeId;
	populateSecondOptionsValues(schemeId, args, destinationProperty, callback);
}

function getSpecializationByCourse(propertyName, courseId, destinationProperty,
		callback) {
	var args = "method=getSpecializationByCourse&courseId=" + courseId;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function getCoursesByProgramType1(propertyName, programTypeId,
		destinationProperty, callback) {

	var args = "method=getCoursesByProgramTypes1&programTypeId=" + programTypeId
			+ "&propertyName=" + propertyName;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperation(url, args, callback);

}

function populateSecondOptionsValuesExam(sourceId, args, destinationProperty,
		callback) {
	if (sourceId.length != 0) {
		var destinationOption = document.getElementById(destinationProperty);
		destinationOption.options[0] = new Option("- Loading -", "");
		destinationOption.selectedIndex = 0;
		var url = "AjaxRequest.do";
		// make an request to server passing URL need to be invoked and
		// arguments.
		requestOperation(url, args, callback);
	} else {
		var destinationOption = document.getElementById(destinationProperty);
		for (x1 = destinationOption.options.length - 1; x1 > 0; x1--) {
			destinationOption.options[x1] = null;
		}
	}
}

function getExamNameByExamTypeWithoutCurrentExam(propertyName, examType, destinationProperty,
		callback, toSchemeId) {
	var args = "method=getExamNameByExamTypeWithoutCurrentExam&examType=" + examType;
	populateSecondOptionsValues(examType, args, destinationProperty, callback);
}

function getBatchesByActivity(propertyName, activityId, selectedClasses,
		destinationProperty, callback) {
	var args = "method=getBatchesByActivity&selectedClassesArray="
			+ selectedClasses + "&activityId=" + activityId + "&propertyName="
			+ propertyName;
	populateSecondOptionsValuesForNoLoadingOption(activityId, args,
			destinationProperty, callback);
}

function getclassesByYearAndCourse(propertyName, courseId, year,
		destinationProperty, callback) {

	var args = "method=getClassesByCourseAndYear&courseId=" + courseId
			+ "&year=" + year + "&propertyName=" + propertyName;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperationProgram(url, args, callback);

}

function getSectionsByCourseAndSemester(propertyName, courseId,admittedYear,destinationProperty, callback) 
{

	var args = "method=getSectionsByCourseAndSemester&courseId=" + courseId
			+ "&propertyName=" + propertyName+"&year="+admittedYear;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperationProgram(url, args, callback);

}

//This function loads course options based on program
function getCourseByPrograminOrder(propertyName, programId, destinationProperty,
		callback) {
	var args = "method=getCourseByPrograminOrder&programId=" + programId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(programId, args, destinationProperty, callback);
}


function getSchemeNoByAcademicYear(propertyName, courseId, academicYear,
		destinationProperty, callback) {
	var args = "method=getSchemeNoByAcademicYear&courseId="
			+ courseId + "&academicYear=" + academicYear;

	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}

function getProgramsByExamName(propertyName, examName, destinationProperty,
		callback) {
	var args = "method=getProgramsByExamName&examName=" + examName;
	populateSecondOptionsValues(examName, args, destinationProperty, callback);
}

function getSubjectsByProgram(propertyName, programId, destinationProperty,
		callback) {
	var args = "method=getSubjectsByProgram&programId=" + programId;
	populateSecondOptionsValues(programId, args, destinationProperty, callback);
}

//This function loads no of rooms based on hostel id,roomTypeId and floor no .
function getRoomsByFloor(propertyName, hostelId, floorNo, destinationProperty, callback) {
	var args = "method=getRoomsByFloor&hostelId=" + hostelId + "&floorNo="
			+ floorNo +"&propertyName="
			+ propertyName;
	populateSecondOptionsValuesForHostel(hostelId, floorNo,args, destinationProperty, callback);
}

function populateSecondOptionsValuesForHostel(hostelId, floorNo,
		 args, destinationProperty, callback) {
	if (hostelId.length != 0 && floorNo.length != 0 ) {
		var destinationOption = document.getElementById(destinationProperty);
		destinationOption.options[0] = new Option("- Loading -", "");
		destinationOption.selectedIndex = 0;
		var url = "AjaxRequest.do";
		// make an request to server passing URL need to be invoked and
		// arguments.
		requestOperation(url, args, callback);
	} else {
		var destinationOption = document.getElementById(destinationProperty);
		for (x1 = destinationOption.options.length - 1; x1 > 0; x1--) {
			destinationOption.options[x1] = null;
		}
	}
}

//This function loads interview subrounds based on interviewType.
function getInterviewSubroundsByInterviewtypeForMultiSelect(propertyName, interviewTypeId,
		destinationProperty, callback) {
	var args = "method=interviewSubroundsByInterviewTypeForMultiselect&interviewTypeId="
			+ interviewTypeId + "&propertyName=" + propertyName;
	populateSecondOptionsValues(interviewTypeId, args, destinationProperty,
			callback);
}

function getSectionByExamIdCourseIdSchemeId(propertyName, courseId, schemeId,
		examId, destinationProperty, callback) {
	var args = "method=getSectionByExamIdCourseIdSchemeId&courseId=" + courseId
			+ "&schemeId=" + schemeId + "&examName=" + examId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(schemeId, args, destinationProperty, callback);
}

function updateMarksByRegNO1(req, elementId) {
	var responseObj = req.responseXML.documentElement;
	var temp = items1[0].firstChild.nodeValue;
	var temp = responseObj.getElementsByTagName("value");
	if (temp != '$$$###') {
		document.getElementById("error_" + elementId).innerHTML = "No Record Found";
		document.getElementById("theoryMarks_" + elementId).value = temp;
	}
}

function getEmployeeCodeName(propertyName, eCodeName, destinationProperty,callback) {
	var args =  "method=getEmployeeCodeName&schemeNo=" + eCodeName;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperationProgram(url, args, callback);
}

function getSubjectGroupByFeeAdditional(propertyName,feeGroupId,destinationProperty,callback){
	var args =  "method=getSubjectGroupByFeeAdditional&feeGroupId=" + feeGroupId;
	populateSecondOptionsValues(feeGroupId, args, destinationProperty, callback);
}

function getSubjectsByRegNo(propertyName, regNo,
		destinationProperty, callback) {
		var args = "method=getAttendanceSubjectsByRegisterNo&regNo=" + regNo;
		populateSecondOptionsValuesForMuliSelect(regNo, args, destinationProperty, callback);
}

function getClassCodeByExamNameWithYear(propertyName, examName, destinationProperty,
		callback) {
	var args = "method=getClassCodeByExamNameWithYear&examName=" + examName;
	populateSecondOptionsValues(examName, args, destinationProperty, callback);
}
function getCoursesByYear(propertyName, year, destinationProperty, callback) {
	var args = "method=getCoursesByYear&year=" + year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}

function updateOptionsFromMap2(req, destinationOption, defaultSelectValue,
		parentNode1, parentNode2,examType) {

	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	var childNodes = responseObj.childNodes;
	destination.options[0] = new Option(defaultSelectValue, "");
	var items = responseObj.getElementsByTagName("option");

	var label, value;
	if (items.length > 0 && examType!=1) {
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i + 1] = new Option(label, value);

		}
		document.getElementById(parentNode1).style.display = "block";
		document.getElementById(parentNode2).style.display = "block";
		if (parentNode1 == "ast") {
			document.getElementById("validationAST").value = "yes";
		} else {
			document.getElementById("validationET").value = "yes";
		}
	} else {
		if (parentNode1 == "ast") {
			document.getElementById("validationAST").value = "no";
		} else {
			document.getElementById("validationET").value = "no";
		}
		document.getElementById(parentNode1).style.display = "none";
		document.getElementById(parentNode2).style.display = "none";
	}

}

//Down Two Methods added by Balaji For the Change of Practical Batch (Class Schemewise Id instead of batch to BatchStudent)
function getBatchesByActivity1(propertyName, activityId, selectedClasses,
		destinationProperty, callback) {
	var args = "method=getBatchesByActivity1&selectedClassesArray="
			+ selectedClasses + "&activityId=" + activityId + "&propertyName="
			+ propertyName;
	populateSecondOptionsValuesForNoLoadingOption(activityId, args,
			destinationProperty, callback);
}

function getBatchesBySubject1(propertyName, subjectId, selectedClasses,
		destinationProperty, callback) {
	var args = "method=getBatchesBySubjects1&selectedClassesArray="
			+ selectedClasses + "&subjectId=" + subjectId + "&propertyName="
			+ propertyName;
	populateSecondOptionsValuesForNoLoadingOption(subjectId, args,
			destinationProperty, callback);
}

function getEmployeeDetails(empCode,fingerPrintId,updateEmpDetails){
	var args = "method=getEmpDetails&empCode=" + empCode + "&fingerPrintId=" + fingerPrintId;
	populateManyFileds(args, updateEmpDetails);
}
function getLeaveTypesForEmployee(empId,empTypeId,stDate,exemption,destinationProperty,updateLeaveTypeForEmployee){
	var args = "method=getLeaveTypesForEmployee&employeeId=" + empId+"&date="+stDate+"&optionNo="+exemption+"&type="+empTypeId;
	populateSecondOptionsValues(destinationProperty, args, destinationProperty, updateLeaveTypeForEmployee);
}
function getLeavesTakenInaMonth(empId,stDate,updateLeaveTaken){
	var args="method=getLeavesTakenInaMonth&employeeId="+empId+"&date="+stDate;
	populateManyFileds(args, updateLeaveTaken);
}

function checkTeachingStaff(styleId1,employeeId,styleId2,callBack){
	var args="method=checkTeachingStaff&employeeId="+employeeId;
	populateManyFileds(args, callBack);
}
function checkGuestTeachingStaff(styleId1,guestId,styleId2,callBack){
	var args="method=checkGuestTeachingStaff&guestId="+guestId;
	populateManyFileds(args, callBack);
}

function updateCheckTeachingStaff(req,styleId1,styleId2,destinationProperty){
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
			if(value[I].firstChild!=null){
			var temp = value[I].firstChild.nodeValue;
				if(temp=="true" || temp==true){
					document.getElementById("teachingStaff_1").checked=true;
					document.getElementById("teachingStaff_2").checked=false;
				}else{
					document.getElementById("teachingStaff_1").checked=false;
					document.getElementById("teachingStaff_2").checked=true;
				}
			}
		}
	}
}

function getFingerPrintIds(propertyName, fingerPrintId, destinationProperty,
		callback) {
	var args = "method=getDynamicFingerPrintId&fingerPrintId=" + fingerPrintId
	+ "&propertyName=" + propertyName;
	populateFingerPrintValues(fingerPrintId, args, destinationProperty,
			callback);
}
function populateFingerPrintValues(sourceId, args, destinationProperty,
		callback) {
	if (sourceId.length != 0) {
		var destinationOption = document.getElementById(destinationProperty);
		//destinationOption.options[0] = new Option("- Loading -", "");
		destinationOption.selectedIndex = 0;
		var url = "AjaxRequest.do";
		// make an request to server passing URL need to be invoked and
		// arguments.
		requestOperationProgram(url, args, callback);
	} else {
		var destinationOption = document.getElementById(destinationProperty);
		for (x1 = destinationOption.options.length - 1; x1 > 0; x1--) {
			destinationOption.options[x1] = null;
		}
	}
}
function updateShowData(req, elementId) {
	var responseObj = req.responseXML.documentElement;
	var items = responseObj.getElementsByTagName("optionvalue");
	for ( var I = 0; I < items.length; I++) {
		if(items[I].firstChild!=null){
		var temp = items[I].firstChild.nodeValue;
		document.getElementById(elementId).value = temp;
	}
}}

function getTearchersByDepartmentWise(departmentId,callback){
	var args = "method=searchDeptWise&departmentId="+departmentId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}

function updateTeachersFromMap(req, destinationOption) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	var childNodes = responseObj.childNodes;
	var items = responseObj.getElementsByTagName("option");

	var label, value;
	for ( var i = 0; i < items.length; i++) {
		label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		destination.options[i] = new Option(label, value);
	}
}

function getPayscaleDetails(payscale,callback){
	var args = "method=getPayScaleDetails&payscaleId="+payscale;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}

function getEmpLeaveDetails(empTypeId,callback){
	var args = "method=getEmpLeaveList&emptypeId="+empTypeId;
	var url = "EmployeeInfoDisplay.do";
	requestOperation(url, args, callback);
}

function getSubjectGroupForProgram(program,programTypeId,callback){
	var args = "method=getSubjectGroupsByProgramWise&programTypeId="+programTypeId+"&programId="+program;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}

//This function loads course options based on program
function getCoursesByProgramForOnline(propertyName, programId, destinationProperty,
		callback) {
	var args = "method=getCourseByProgramForOnline&programId=" + programId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(programId, args, destinationProperty, callback);
}
function getDepartmentByStreamWise(streamId,callback){
	var args = "method=searchStreamWise&streamId="+streamId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}

	function updateDepartmentFromMap(req,destinationOption){
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	
	var childNodes = responseObj.childNodes;
	var items = responseObj.getElementsByTagName("option");
	var label, value;
	if(items.length!=0)
	{	
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i] = new Option(label, value);
		}
	}
	else
	{
		destination.options[0] = null;
	}	
	}
	
	
	function getPayscaleTeachingStaff(teachingStaff, callback){
		var args = "method=getPayscaleTeachingStaffDetails&teachingStaff1="+teachingStaff;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	
	function getProgramsListByAcademicYear(acYear,callback){
		var args = "method=getProgramsListAcademicYear&academicYear="+acYear;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	
	/*function updateEmployeePayScale(req,destinationOption){
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById(destinationOption);
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		
		var childNodes = responseObj.childNodes;
		var items = responseObj.getElementsByTagName("option");
		var label, value;
		if(items.length!=0)
		{	
			for ( var i = 0; i < items.length; i++) {
				label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
				value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
				destination.options[i] = new Option(label, value);
			}
		}
		else
		{
			destination.options[0] = null;
		}	
	}*/
	
	function getCurrentTermNumber(regNo,callback){
		var args = "method=getCurrentTermNumber&regNo="+regNo;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	function getTeachingExpAddMore(ExpAddMore, callback){
		var args = "method=resetTeachingExperienceInfo&mode="+ExpAddMore;
		var url = "EmployeeInfoEditDisplay.do";
		requestOperation(url, args, callback);
	}
	function getSortedSubjectList(examID,scName,subName,destinationProperty,callback){
		var args = "method=getSortedSubjectList&subjectName="+subName+"&schemeNo=" + scName + "&examTypeId=" + examID;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	function updateSubjectFormMap(req,destinationOption,defaultSelectValue){
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById(destinationOption);
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		var childNodes = responseObj.childNodes;
		destination.options[0] = new Option(defaultSelectValue, "");
		var items = responseObj.getElementsByTagName("option");

		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i + 1] = new Option(label, value);
		}
	}
	
	function getExamNameByExamTypeYearWise(propertyName, examType, year, destinationProperty,
			callback, toSchemeId) {
		var args = "method=getExamNameByExamTypeYearWise&examType=" + examType+"&year="+year;
		populateSecondOptionsValues(examType, args, destinationProperty, callback);
	}
	function getExamNameByExamTypeYearWise(propertyName, examType, year, destinationProperty,
			callback, toSchemeId) {
		var args = "method=getExamNameByExamTypeYearWise&examType=" + examType+"&year="+year;
		populateSecondOptionsValues(examType, args, destinationProperty, callback);
	}
	
	function getYearByMonthWise(examMonth,callback){
		var args = "method=SearchYearByMonth&month="+examMonth;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	function getTeachingDepartment(streamId,callback){
		var args = "method=getTeachingDepartment&streamId="+streamId;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}

	function getDepartmentByStreamWiseWithTeaching(streamId,isTeaching,callback){
		var args = "method=searchStreamWise&streamId="+streamId+"&teachingStaff1="+isTeaching;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	
	function getExamNameByExamTypeAndYear(propertyName, examType,year, destinationProperty,
			callback, toSchemeId) {
		var args = "method=getExamNameByExamTypeAndYear&examType=" + examType+"&year="+year;
		populateSecondOptionsValues(examType, args, destinationProperty, callback);
	}

	function getClassByYear(userId, year, callback) {
		var args = "method=getClassByYear&year="+year+"&userId="+userId;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	//populateSecondOptionsValues(year, args, destinationProperty, callback);
	}

	function getSubjectByClassTeacherYear(userId,year,classId,callback) {
		var args = "method=getSubjectByClass&year="+year+"&userId="+userId+"&classId="+classId;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	//populateSecondOptionsValues(year, args, destinationProperty, callback);
}
	function getCourseByYear(propertyName, year, destinationProperty, callback) {
		var args = "method=getCourseByYear&year=" + year;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
		//populateSecondOptionsValues(year, args, destinationProperty, callback);
	}
	function getClassesByCourseAndYear(propertyName, courseId, destinationProperty,year, callback) {
		var args = "method=getClassesByCourse&courseId=" + courseId+"&year="+year;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}	
	function getGroupTemplate(programTypeId,programId,templateName,callback){
		var args="method=getGroupTemplate&programId="+programId+"&templateName="+templateName+"&programTypeId="+programTypeId;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	function getValuatorNames(valuatorName,subjectId,callback){
		var args = "method=getValuatorNames&valuatorName="+valuatorName+"&subjectId="+subjectId;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	function updateValuatorsMap(req, destinationOption, defaultSelectValue) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById(destinationOption);
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		var childNodes = responseObj.childNodes;
		destination.options[0] = new Option(defaultSelectValue, "");
		var items = responseObj.getElementsByTagName("option");
		var count =1;
		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i+1] = new Option(label, value);
			count = count +1;
		}
	//	commented By Manu In valuation schedule other not required
	//	destination.options[count] = new Option("Other", "Other");
	}
	
// Attendance ENtry Modification Accourding to TimeTable
	
	function getPeriodByTeacherInMuliSelect(propertyName, teacherId,
			destinationProperty,date,callback) {
		var year = document.getElementById("academicYear").value;
		if (year != null && year != '') {
			var args = "method=getPeriodsByTeacher&teacherId=" + teacherId
					+ "&year=" + year+"&date="+date;
			populateManyFileds(args, callback);
		}
	}
	
	function getSubjectsAndClassForPeriods(selectedPeriods, year,teachers,date, callback) {
		var args = "method=getSubjectsAndClassForPeriods&periodId=" + selectedPeriods + "&year=" + year+"&teacherId=" + teachers+"&date="+date;
		populateManyFileds(args, callback);
	}
	function getExternalEmployeeList(departmentId,callback){
		var args = "method=getExternalEmployeeList";
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	function updateExternalEmployeeMap(req, destinationOption, defaultSelectValue) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById(destinationOption);
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		var childNodes = responseObj.childNodes;
		destination.options[0] = new Option(defaultSelectValue, "");
		var items = responseObj.getElementsByTagName("option");

		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i+1] = new Option(label, value);
		}
	}
	function getSubCategoryListByCategoryId(propertyName,categoryId, destinationProperty,callback){
		var args = "method=getSubCategoryListByCategoryId&itemCategoryId=" + categoryId;
		populateSecondOptionsValues(categoryId, args, destinationProperty, callback);
	}
	
	// This function loads specialization options based on subjectGroupid
	function getSpecializationBySubjectGroupWithoutCommSubjectAndSecondLang(propertyName, subjectGroupId, destinationProperty,
			callback) {
		var args = "method=getSpecializationBySubjectGroupWithoutCommSubjectAndSecondLang&subjectGroupId=" + subjectGroupId
				+ "&propertyName=" + propertyName;
		populateSecondOptionsValues(subjectGroupId, args, destinationProperty, callback);
	}
	
	function getInvLocationListByCampusId(propertyName,campusId, destinationProperty,callback){
		var args = "method=getInvLocationListByCampusId&campusId=" + campusId;
		populateSecondOptionsValues(campusId, args, destinationProperty, callback);
	}
	function getNameByAppNo(appNo, destinationProperty, callback) {
		var args = "method=getNameByAppNo&appNo=" + appNo;
		populateTextBoxValue(appNo, args, destinationProperty,
				callback);
	}
	function updateTextBox(req, destinationOption) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById(destinationOption);
		destination.value = null;
		var value = responseObj.getElementsByTagName("value");
		if(value!=null){
			for ( var I = 0; I < value.length; I++) {
				if(value[I].firstChild!=null){
				var temp = value[I].firstChild.nodeValue;
				destination.value = temp;
				}
			}
		}
	}
	function populateTextBoxValue(sourceId, args, destinationProperty,
			callback) {
		if (sourceId.length != 0) {
			var destinationOption = document.getElementById(destinationProperty);
			destinationOption.Value = "";
			destinationOption.selectedIndex = 0;
			var url = "AjaxRequest.do";
			// make an request to server passing URL need to be invoked and
			// arguments.
			requestOperationProgram(url, args, callback);
		} else {
			var destinationOption = document.getElementById(destinationProperty);
			for (x1 = destinationOption.options.length - 1; x1 > 0; x1--) {
				destinationOption.options[x1] = null;
			}
		}
	}
	function checkReceivedThroughAvailability(receivedThrough, destinationProperty, callback) {
		var args = "method=checkReceivedThroughAvailability&receivedThrough=" + receivedThrough;
		populateTextBoxValue(receivedThrough, args, destinationProperty,
				callback);
	}
	function updateAvailableValue(req, destinationOption) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById(destinationOption);
		destination.value = null;
		var value = responseObj.getElementsByTagName("value");
		var slipRequired = responseObj.getElementsByTagName("slipRequired");
		if(value!=null){
			for ( var I = 0; I < value.length; I++) {
				if(value[I].firstChild!=null){
				var temp = value[I].firstChild.nodeValue;
				if(temp == "true"){
					document.getElementById("errorMessage").innerHTML="";
				}else{
					document.getElementById("errorMessage").innerHTML="Received Through is Not Available";
					document.getElementById("receivedThrough").value="";
				}
				}
			}
		}
		if(slipRequired!=null){
			for(var i = 0; i < slipRequired.length; i++){
				if(slipRequired[i].firstChild!=null){
				    var value = slipRequired[i].firstChild.nodeValue;
				    document.getElementById("slipRequred").value=value;
				    if(document.getElementById("slipRequred").value == "true"){
				    	var tdl = document.getElementById("save&print");
				    	var td2= document.getElementById("trackingNo");
				    	tdl.style.display = 'block';
				    	td2.disabled=true;
				    }else{
				    	var td1 = document.getElementById("save&print");
				    	var td2= document.getElementById("trackingNo");
				    	td1.style.display = 'none';
				    	td2.disabled=false;
				    }
				}
			}
		}
	}
	
	
	function getCoursesByMultiplePrograms(propertyName, selectedClasses, destinationProperty,
			callback) {

		var args = "method=getCoursesByMultiplePrograms&selectedClassesArray="
				+ selectedClasses + "&propertyName=" + propertyName;

		populateSecondOptionsValues(selectedClasses, args, destinationProperty,
				callback);
	}
	// This function loads classes options based on year
	function getSubjectByYear(propertyName, year, destinationProperty, callback) {
		var args = "method=getSubjectByYear&year=" + year;
		populateSecondOptionsValues(year, args, destinationProperty, callback);
	}
	/**
	 * @param propertyName
	 * @param year
	 * @param destinationProperty
	 * @param callback
	 * @return
	 */
	function getExamByYear(propertyName, year, destinationProperty, callback) {
		var args = "method=getExamByYear&year=" + year;
		populateSecondOptionsValues(year, args, destinationProperty, callback);
	}
	
	// This function loads course options based on Appln No range
	function getCoursesByApplnNo(propertyName, applnNo,year, destinationProperty,
			callback) {
		var args = "method=getCourseByApplnNo&appNo=" + applnNo+"&year="+year
				+ "&propertyName=" + propertyName;
		populateSecondOptionsValues(applnNo, args, destinationProperty, callback);
	}
	
	function getInterviewTypeByCourseNew(propertyName, courseId, year,
			destinationProperty, callback) {

		var args = "method=interviewTypeByCourseNew&courseId=" + courseId + "&year="
				+ year + "&propertyName=" + propertyName;
		var destinationOption = document.getElementById(destinationProperty);
		destinationOption.options[0] = new Option("- Loading -", "");
		destinationOption.selectedIndex = 0;
		var url = "AjaxRequest.do";
		// make an request to server passing URL need to be invoked and arguments.
		requestOperation(url, args, callback);

	}
	function updateOptionsFromMapNew(req, destinationOption, defaultSelectValue) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById(destinationOption);
		for (x1 = destination.options.length; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		var childNodes = responseObj.childNodes;
		var items = responseObj.getElementsByTagName("option");

		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i] = new Option(label, value);
		}
	}
	
	function updateOptionsFromMapNew1(req, destinationOption, defaultSelectValue) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById(destinationOption);
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		var childNodes = responseObj.childNodes;
		destination.options[0] = new Option(defaultSelectValue, "");
		var items = responseObj.getElementsByTagName("option");

		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			destination.options[i + 1] = new Option(label, value);
		}
	}
	
	function getInterviewSubroundsByInterviewtypeNew(propertyName, interviewTypeId,
			destinationProperty, callback,courseIds,year) {
		var args = "method=interviewSubroundsByInterviewTypeNew&interviewTypeId="
				+ interviewTypeId + "&propertyName=" + propertyName+"&courseId"+courseIds+"&year="+year;
		populateSecondOptionsValues(interviewTypeId, args, destinationProperty,
				callback);
	}
// This function loads Faculty options based on Department 
	
	function getFacultyByDepartment(propertyName, departmentId, destinationProperty, callback){
		
		var args = "method=getFacultyByDepartment&departmentId=" + departmentId;
		populateSecondOptionsValues(departmentId, args, destinationProperty, callback);
	}
	function getYearWiseDocuments(year,callback){
		var args = "method=getYearWiseDocuments&year="+year;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	function getClassesForProgram(programTypeId,year,callback){
		var args = "method=getClassesForProgram&programTypeId="+programTypeId+"&year="+year;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	function getSubjectByTypeNew(propertyName, selectedClasses, destinationProperty,
			callback) {
		var args = "method=getSubjectsByMultipleClassesNew&selectedClassesArray="
				+ selectedClasses + "&propertyName=" + propertyName;

		populateSecondOptionsValues(selectedClasses, args, destinationProperty,
				callback);
	}
	function getClassesByYearNew(propertyName, year, destinationProperty, callback) {
		var args = "method=getClassesByYearNew&year=" + year;
		populateSecondOptionsValues(year, args, destinationProperty, callback);
	}
	
	function getSubjectsCodeNameByCourseSchemeExamId(propertyName, sCName, courseId,
			destinationProperty, callback, schemeId, examId) {

		var args = "method=getSubjectsCodeNameByCourseSchemeExamId&schemeNo=" + sCName + "&courseId=" + courseId
				+ "&schemeId=" + schemeId + "&examName=" + examId;
		populateSecondOptionsValues(courseId, args, destinationProperty, callback);
	}
	function getSpecializationByClass(propertyName, selectedClasses,destinationProperty , callback){
		
		var args = "method=getSpecializationByClass&&selectedClassesArray="
			+ selectedClasses + "&propertyName=" + propertyName;
		populateSecondOptionsValues(selectedClasses, args, destinationProperty, callback);
	}
	function getSubjectListForExam(examId,examType,subCode,destinationProperty ,callback){
		var args = "method=getSubjectListForExam&examName=" +examId+"&examType="+examType+"&subjectType="+subCode;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
	}
	function getBlocksByHostel(hostelId, destinationProperty,callback) {
		var args = "method=getBlocksByHostel&hostelId=" + hostelId;
		populateSecondOptionsValues(hostelId, args, destinationProperty, callback);
	}
	function getUnitsByBlocks(blockId, destinationProperty,callback) {
		var args = "method=getUnitsByBlocks&hostelId=" + blockId;
		populateSecondOptionsValues(hostelId, args, destinationProperty, callback);
	}
	function getRoomByRoomTypeId(propertyName, roomTypeId, destinationProperty,callback) {
		var args = "method=getRoomByRoomType&roomTypeId=" + roomTypeId
				+ "&propertyName=" + propertyName;
		populateSecondOptionsValues(roomTypeId, args, destinationProperty, callback);
	}
function getCorsesByPrograms(propertyName, selectedPrograms,destinationProperty , callback){
		
		var args = "method=getCourseByPrograms&&selectedProgramArray="
			+ selectedPrograms + "&propertyName=" + propertyName;
		populateSecondOptionsValues(selectedPrograms, args, destinationProperty, callback);
	}
function updateOptionsFromMapForCourses(req, destinationOption) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	var childNodes = responseObj.childNodes;
	var items = responseObj.getElementsByTagName("option");

	var label, value;
	for ( var i = 0; i < items.length; i++) {
		label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		destination.options[i] = new Option(label, value);
		destination.options[i].selected=true;
	}
}
function verifyRegisterNumberAndGetName(regNo,academicYear,callback)
{
	var args = "method=verifyRegisterNumberAndGetName&regNo="+regNo+"&academicYear="+academicYear;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
	
}
function getRooms(hostelId,academicYear,roomTypeId,blockId,unitId,floorId,callback)
{
	var args = "method=GetRoomsforHostel&hostelId="+hostelId+"&academicYear="+academicYear+"" +
			"&roomTypeId="+roomTypeId+"&blockId="+blockId+"&unitId="+unitId+"&floorId="+floorId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
	
}


function getRoomByRoomTypeId1(propertyName, roomTypeId, destinationProperty,callback) {
		var args = "method=getRoomByRoomType&roomTypeId=" + roomTypeId
				+ "&propertyName=" + propertyName;
		populateSecondOptionsValues(roomTypeId, args, destinationProperty, callback);
}



function updateOptionsFromMap3(req, destinationOption, defaultSelectValue,
		parentNode1, parentNode2,examType) {

	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	var childNodes = responseObj.childNodes;
	destination.options[0] = new Option(defaultSelectValue, "");
	var items = responseObj.getElementsByTagName("option");

	var label, value;
	if (items.length > 0 && examType!=1) {
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i + 1] = new Option(label, value);
		}
		document.getElementById(parentNode1).style.display = "block";
		document.getElementById(parentNode2).style.display = "block";
		document.getElementById("validationET").value = "yes";
	} else {
		document.getElementById(parentNode1).style.display = "none";
		document.getElementById(parentNode2).style.display = "none";
		document.getElementById("validationET").value = "no";
	}

}
function getSubjectFromRevaluationOrRetotalling(propertyName, sCodeName, destinationProperty,
		callback, examId) {
	var args = "";
	if(examId!= null){
		args =  "method=getSubjectFromRevaluationOrRetotaling&schemeNo=" + sCodeName + "&examTypeId=" + examId;
	}else{
		args =  "method=getSubjectFromRevaluationOrRetotaling&schemeNo=" + sCodeName;
	}
	
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperationProgram(url, args, callback);
}
function getTotalSestByRoomsAndHostel(hostelId,roomtypeId,callback)
{
	var args = "method=getTotalSeatsByroomIdAndHostelId&hostelId="+hostelId+"&roomId="+roomtypeId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}
function getSubjectCodeNameYearWise(examType,examId,year,sCodeName, destinationProperty,callback) {
	args =  "method=getSubjectCodeNameNew&schemeNo="+sCodeName + "&examType=" +examType+"&year="+year+"&examName="+examId;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperationProgram(url, args, callback);
}
function getExamNameList(academicYear,examName,callback){
	var args="method=getExamNameList&programId="+examName+"&academicYear="+academicYear;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}
function getClassesBySemester(propertyName,year,semester, destinationProperty, callback) {
	var args = "method=getClassesBySemester&semister=" + semester+"&academicYear="+year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}
//@author jboss
function getClassesByTeacherAndDateInMuliSelect(propertyName, teacherId,
		destinationProperty,date, callback) {
	var year = document.getElementById("academicYear").value;
	if (year != null && year != '') {
		var args = "method=getClassesByTeacherAndDate&teacherId=" + teacherId
				+ "&year=" + year+"&date="+date;
		populateSecondOptionsValuesForMuliSelect(teacherId, args,
				destinationProperty, callback);
	}
}
/**
 * getting the student details for visitors
 * @param hostelId
 * @param year
 * @param regNo
 * @param callback
 * @return
 */
function getStudentDetailsForVisitors(year,regNo,callback)
{
	var args = "method=getStudentDetailsForVisitors&academicYear="+year+"&regNo="+regNo;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}

function verifyRegisterNumberAndGetNameAcc(regNo,academicYear,callback)
{
	var args = "method=verifyRegisterNumberAndGetNameAccDetails&regNo="+regNo+"&academicYear="+academicYear;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}
/**
 * check student register no available or not
 * @param registerNo
 * @param callback
 * @return
 */
function checkDupilcateOfRegNo(registerNo,	callback) {
	var args = "method=checkDupilcateOfRegNo&regNo=" + registerNo;
	populateManyFileds(args, callback);
}
/**
 * get amount by category
 * @param hostelId
 * @param roomtypeId
 * @param callback
 * @return
 */
function getAmountByCategory(hostelId,callback)
{
	var args = "method=getAmountByCategory&hostelId="+hostelId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}
/**
 * @param academicYear
 * @param examName
 * @param callback
 * @return
 */
function getExamNameListSchedule(academicYear,examName,callback){
	var args="method=getExamNameListSchedule&programId="+examName+"&academicYear="+academicYear;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}
/**
 * @param propertyName
 * @param id
 * @param destinationProperty
 * @param callback
 * @return
 */
function getDepartments(propertyName,id,destinationProperty, callback) {
	var args = "method=getDepartments";
	populateSecondOptionsValues(id, args, destinationProperty, callback);
}
/**
 * @param allClasses
 * @param callback
 * @return
 */
function getPeriodsByClassesvalues(allClasses,callback) {
	var args = "method=getPeriodsByClassesvalues&allClassesArray="+ allClasses ;
	populateManyFileds(args, callback);
}
/**
 * get blockMap based on hostel Id
 * @param propertyName
 * @param hostelId
 * @param destinationProperty
 * @param callback
 * @return
 */
function getBlockByHostel(propertyName, hostelId, destinationProperty, callback) {
	var args = "method=getBlockByHostel&hostelId=" + hostelId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(hostelId, args, destinationProperty, callback);
}
/**
 * get unitMap based on block Id
 * @param propertyName
 * @param hostelId
 * @param destinationProperty
 * @param callback
 * @return
 */
function getUnitByBlock(propertyName, blockId, destinationProperty, callback) {
	var args = "method=getUnitByBlock&blockId=" + blockId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(blockId, args, destinationProperty, callback);
}
function checkDupilcateOfStaffId(staffId,	callback) {
	var args = "method=checkDupilcateOfStaffId&regNo=" + staffId;
	populateManyFileds(args, callback);
}
//This function loads Blocks options based on LocationId
function getBlockByLocation(propertyName, locationId, destinationProperty, callback) {
	var args = "method=getBlockByLocation&locationId=" + locationId;
	populateSecondOptionsValues(locationId, args, destinationProperty, callback);
}
/**
 * @param propertyName
 * @param workLocationId
 * @param destinationProperty
 * @param callback
 * @return
 */
function getVenueByWorkLocation(propertyName, workLocationId, destinationProperty, callback) {
	var args = "method=getVenueByWorkLocation&workLocatId=" + workLocationId
			+ "&propertyName=" + propertyName;
	//populateSecondOptionsValues(workLocationId, args, destinationProperty, callback);
	populateManyFileds(args, callback);
}
/**
 * @param propertyName
 * @param hostelId
 * @param destinationProperty
 * @param callback
 * @return
 */
function getCoursebyHostel(propertyName, hostelId, destinationProperty, callback) {
	var args = "method=getCoursebyHostels&hostelId=" + hostelId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(hostelId, args, destinationProperty, callback);
}
/**
 * @param propertyName
 * @param hostelId
 * @param destinationProperty
 * @param callback
 * @return
 */
function getClassByHostel(propertyName, hostelId, destinationProperty, callback) {
	var args = "method=getClassByHostels&hostelId=" + hostelId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(hostelId, args, destinationProperty, callback);
}

/**
 * @param propertyName
 * @param year
 * @param destinationProperty
 * @param callback
 * @return
 */
function getExamByYearOnlyInternal(propertyName, year, destinationProperty, callback) {
	var args = "method=getExamByYearOnlyInternal&year=" + year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}

/**
 * @param propertyName
 * @param examId
 * @param deanaryName
 * @param destinationProperty
 * @param callback
 * @param toSchemeId
 * @return
 */
function getProgramBydeanaryName(examId,deanaryName,destinationProperty,callback) {
		
	var args = "method=getProgramBydeanaryNameAndExam&examid=" +examId+"&deanaryName="+deanaryName;
	populateSecondOptionsValues(examId, args, destinationProperty, callback);
}

/**
 * @param propertyName
 * @param programId
 * @param destinationProperty
 * @param callback
 * @param deaneryName
 * @return
 */
function getClassesByProgramAndAcademicYear(propertyName, programId, destinationProperty, callback, deaneryName) {
	var args = "method=getClassesByProgramAndAcademicYear&programId=" + programId +"&deanaryName="+deaneryName;
	populateSecondOptionsValuesForNoLoadingOption(programId, args, destinationProperty, callback);
}

/**
 * @param propertyName
 * @param programId
 * @param destinationProperty
 * @param callback
 * @param deaneryName
 * @return
 */
function getClassesByProgramTypeAndAcademicYear(propertyName, programTypeId, destinationProperty, callback, deaneryName) {
	var args = "method=getClassesByProgramTypeAndAcademicYear&programTypeId=" + programTypeId +"&deanaryName="+deaneryName;
	populateSecondOptionsValuesForNoLoadingOption(programTypeId, args, destinationProperty, callback);
}

function getClassesByCourseAndAcademicYear(propertyName, courseId, destinationProperty, callback, deaneryName) {
	var args = "method=getClassesByCourseAndAcademicYear&courseId=" + courseId +"&deanaryName="+deaneryName;
	populateSecondOptionsValuesForNoLoadingOption(courseId, args, destinationProperty, callback);
}
/**
 * orderNo duplicate checking by location id
 * @param hostelId
 * @param year
 * @param regNo
 * @param callback
 * @return
 */
function duplicateCheckingOfOrderNoByLocationId(orderNo,locationId,callback){
	var args = "method=duplicateCheckingOfOrderNoByLocationId&academicYear="+orderNo+"&regNo="+locationId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}
/**
 * @param propertyName
 * @param semester
 * @param destinationProperty
 * @param callback
 * @param deaneryName
 * @return
 */
function getClassesBySemesterAndAcademicYear(propertyName, semester, destinationProperty, callback, deaneryName, progType, prog, course) {
	var args = "method=getClassesBySemesterAndAcademicYear&semister=" + semester +"&deanaryName="+deaneryName+"&programTypeId="+progType+"&programId="+prog+"&courseId="+course;
	populateSecondOptionsValuesForNoLoadingOption(semester, args, destinationProperty, callback);
}
/**
 * department by deanery
 * @param propertyName
 * @param year
 * @param destinationProperty
 * @param callback
 * @return
 */
function getDeptByStream(propertyName, streamId, destinationProperty, callback) {
	var args = "method=getDeptByStream&streamId=" + streamId;
	populateSecondOptionsValues(streamId, args, destinationProperty, callback);
}
/**
 * getting the roomNos by work location id
 * @param propertyName
 * @param year
 * @param destinationProperty
 * @param callback
 * @return
 */
function roomNosByCampus(propertyName, id, destinationProperty, callback) {
	var args = "method=roomNosByCampus&locationId=" + id;
	populateSecondOptionsValues(id, args, destinationProperty, callback);
}
/**
 * getting the faculty by work Location
 * @param propertyName
 * @param year
 * @param destinationProperty
 * @param callback
 * @return
 */
function facultyByCampus(propertyName, id, destinationProperty, callback) {
	var args = "method=facultyByCampus&locationId=" + id;
	populateSecondOptionsValues(id, args, destinationProperty, callback);
}
/**
 * getting the student details for visitors
 * @param hostelId
 * @param year
 * @param regNo
 * @param callback
 * @return
 */
function checkDuplicateInvigilator(date,session,facultyId,examinarType,id,locationId,examId,callback){
	var args = "method=checkDuplicateInvigilator&date="+date+"&stime="+session+"&userId="+facultyId+"&guestId="+examinarType+"&examid="+id+"&locationId="+locationId
	+"&examName="+examId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}
/**
 * getting the student name by register no
 * @param hostelId
 * @param year
 * @param regNo
 * @param callback
 * @return
 */
function getStudentNameByRegNo(regNo,callback){
	var args = "method=getStudentNameByRegNo&regNo="+regNo;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}
/**
 * getting the faculty by work Location,dept and deanery
 * @param propertyName
 * @param year
 * @param destinationProperty
 * @param callback
 * @return
 */
function facultyByCampusDeptAndDeanery (propertyName, locId,deptId,deanaryId, destinationProperty, callback) {
	var args = "method=facultyByCampusDeptAndDeanery&locationId=" + locId+"&departmentId="+deptId+"&deanaryName="+deanaryId;
	populateSecondOptionsValues(locId, args, destinationProperty, callback);
}
/**
 * getting the student details for visitors
 * @param hostelId
 * @param year
 * @param regNo
 * @param callback
 * @return
 */
function checkDuplicateExemption(date,session,id,facId,callback){
	var args = "method=checkDuplicateExemption&date="+date+"&stime="+session+"&userId="+facId+"&examid="+id;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}
/**
 * @param propertyName
 * @param type
 * @param destinationProperty
 * @param callback
 * @return
 */
function getAccountsForApplications(propertyName, type, destinationProperty, callback) {
	var args = "method=getAccountsForApplication&type=" + type;
	populateSecondOptionsValuesForNoLoadingOption(type, args, destinationProperty, callback);
}
/**
 * getting the roomNos by work location id
 * @param propertyName
 * @param year
 * @param destinationProperty
 * @param callback
 * @return
 */
function getProgramByYear(propertyName, id, destinationProperty, callback) {
	var args = "method=getProgramByYear&academicYear=" + id;
	populateSecondOptionsValues(id, args, destinationProperty, callback);
}
/**
 * getting the roomNos by work location id
 * @param propertyName
 * @param year
 * @param destinationProperty
 * @param callback
 * @return
 */
function getClassMap(propertyName,examType,examName,programId, deanaryId, destinationProperty, callback) {
	var args = "method=getClassMap&examName=" + examName+"&examType="+examType+"&programId="+programId+"&deanaryName="+deanaryId;
	populateSecondOptionsValuesForNoLoadingOption(examType, args, destinationProperty, callback);
}
/**
 * @param propertyName
 * @param selectedClasses
 * @param destinationProperty
 * @param callback
 * @return
 */
function getSubjectByClassesIDsTypeNew(propertyName, selectedClasses, destinationProperty, callback) {
	var args = "metrequestOperationProgramhod=getSubjectsByMultipleClassesIds&selectedClassesArray=" + selectedClasses + "&propertyName=" + propertyName;
	populateSecondOptionsValues(selectedClasses, args, destinationProperty, callback);
}
/**
 * getting the student details for visitors
 * @param hostelId
 * @param year
 * @param regNo
 * @param callback
 * @return
 */
function isFacultyAvailable(facultyId,locationId,examId,callback){
	var args = "method=isFacultyAvailable&userId="+facultyId+"&examName="+examId+"&locationId="+locationId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}


function getVenueBySelectionDate(selectScheduleId, callback) 
{
	
	var args = "method=getVenueBySelectionDate&selectScheduleId=" + selectScheduleId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}
function getDateBySelectionVenueWithPref(selectScheduleId,callback,programId) 
{
	
	var args = "method=getDateByVenueSelectionPref&selectVenueId=" +selectVenueId +" &programId="+programId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}


function getTimeBySelectionDate(selectScheduleId, callback, VenueId) 
{
	var args = "method=getTimeBySelectionDate&selectScheduleId="+selectScheduleId+"&selectionProcessVenue="+VenueId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}

function getIsExamCentreSeatNoRequired(pgmId, callback, VenueId) 
{
	var args = "method=getExamCenterDefinedInProgram&programId="+pgmId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}

function getDateBySelectionVenue(selectVenueId, callback, programId, programYear) 
{
	var args = "method=getDateByVenueSelection&selectVenueId=" +selectVenueId +" &programId="+programId +" &programYear="+programYear;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}

function getDateByVenueSelectionOffline(selectVenueId, callback, programId, programYear) 
{
	var args = "method=getDateByVenueSelectionOffline&selectVenueId=" +selectVenueId +" &programId="+programId +" &programYear="+programYear;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}

function getDateBySelectionVenueAppEdit(selectVenueId, callback, programId, programYear, applicationNo,tempselectedVenueId) 
{
	var args = "method=getDateByVenueSelectionAppEdit&selectVenueId=" +selectVenueId +" &programId="+programId +" &programYear="+programYear+" &applnNo="+applicationNo+" &oldSelectedVenue="+tempselectedVenueId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}

function getDateByVenueSelectionOfflineAppEdit(selectVenueId, callback, programId, programYear, applicationNo,tempselectedVenueId) 
{
	var args = "method=getDateByVenueSelectionOfflineAppEdit&selectVenueId=" +selectVenueId +" &programId="+programId +" &applnNo="+applicationNo+" &oldSelectedVenue="+tempselectedVenueId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}
function getExamByYearOnlyInter(propertyName, year, destinationProperty, callback) {
	var args = "method=getExamByYearOnlyInter&year=" + year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}
/**
 * @param propertyName
 * @param year
 * @param destinationProperty
 * @param callback
 * @return
 */
function getYearMapByDept(propertyName, deptId,year, destinationProperty, callback) {
	var args = "method=getYearMapByDept&unitId=" + deptId+"&year="+year;
	populateSecondOptionsValues(deptId, args, destinationProperty, callback);
}
/**
 * @param propertyName
 * @param year
 * @param destinationProperty
 * @param callback
 * @return
 */
function getsubjectMapByDeptAndYear(propertyName, deptId,year, destinationProperty, callback) {
	var args = "method=getsubjectMapByDeptAndYear&unitId=" + deptId+"&year="+year;
	populateSecondOptionsValues(deptId, args, destinationProperty, callback);
}
function getExamByYearOnly(propertyName, year, destinationProperty, callback) {
	var args = "method=getExamByYearOnly&year=" + year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}
function getClasesByExamNameNew(examName,year,destinationProperty,callback) {
	var args = "method=getClasesByExamNameNew&examName=" + examName+"&year="+year;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}

function getCentersByProgram(propertyName, programId, destinationProperty,callback) {
	var args = "method=getCentersByProgram&programId=" + programId + "&propertyName=" + propertyName;
	populateSecondOptionsValues(programId, args, destinationProperty, callback);
}

function getParishByDiose(dioid,callback){
	var args = "method=getParishByDiose&month="+dioid;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}


//This function loads district options based on state
function getDistrictByState(propertyName, countryId, destinationProperty,callback) {
	var args = "method=getDistrictByState&stateId=" + countryId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(countryId, args, destinationProperty, callback);
}




//ra get caste by religion

function getSubCasteByReligion(propertyName, religionId,
		destinationProperty, callback) {
	
	var args = "method=getSubCasteByReligion&religionId=" + religionId + "&propertyName=" + propertyName;
	populateSecondOptionsValues(religionId, args, destinationProperty, callback);
}

function getAttendanceTypeIdBySubject(subjectId,callback) {
	var args = "method=getAttendanceTypeIdBySubject&subjectId=" + subjectId;
	var url = "AjaxRequest.do";
	requestOperation(url, args, callback);
}


// Vinodha
// loads classes for faculty evaluation report 
function getClassesForFacultyEvaluationReport(propertyName, year, destinationProperty, callback) {
	var args = "method=getClassesForFacultyEvaluationReport&year=" + year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}
// subjects for faculty evaluation report
function getSubjectsForFacultyEvaluationReport(propertyName, classId, destinationProperty, callback) {
	var args = "method=getSubjectsForFacultyEvaluationReport&classId=" + classId;
	populateSecondOptionsValues(classId, args, destinationProperty, callback);
}
// loads teachers for faculty evaluation report Principal
function getTeachersForFacultyEvaluationReportPrincipal(propertyName, year, destinationProperty, callback) {
	var args = "method=getTeachersForFacultyEvaluationReportPrincipal&academicYear="+year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}
// loads classes for faculty evaluation report Principal
function getClassesForFacultyEvaluationReportPrincipal(propertyName, teachId, year, destinationProperty, callback) {	
	var args = "method=getClassesForFacultyEvaluationReportPrincipal&teacher="+teachId+"&academicYear="+year;
	populateSecondOptionsValues(teacherId, args, destinationProperty, callback);
}
// loads subjects for faculty evaluation report Principal
function getSubjectsForFacultyEvaluationReportPrincipal(propertyName, classId, year, teacherId, destinationProperty, callback) {
	var args = "method=getSubjectsForFacultyEvaluationReportPrincipal&classId="+classId+"&academicYear="+year+"&teacher="+teacherId;
	populateSecondOptionsValues(classId, args, destinationProperty, callback);
}
//loads classes for evaluate faculty feedback
function getClassesForEvaluateFacultyFeedback(propertyName, year, destinationProperty, callback) {
	var args = "method=getClassesForEvaluateFacultyFeedback&year=" + year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}
//loads departments for faculty evaluation report Principal
function getDepartmentsForFacultyEvaluationReportPrincipal(propertyName, year, destinationProperty, callback) {
	var args = "method=getDepartmentsForFacultyEvaluationReportPrincipal&academicYear="+year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}
//sms
function getclassesByYearAndCourse1(propertyName, courseId, year,
		destinationProperty, callback) {

	var args = "method=getClassesByCourseAndYear1&courseId=" + courseId
			+ "&year=" + year + "&propertyName=" + propertyName;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperationProgram(url, args, callback);

}	  


function getCoursesByProgramTypes(propertyName, programTypeId,
		destinationProperty, callback) {
	//raghu added newly to prevent cache add extra parameter to url + "&preventCache="+new Date();
	var args = "method=getCoursesByProgramTypes&programTypeId=" + programTypeId+"&preventCache="+new Date();
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}





//new for admission
function getCoursesByProgramForOnlineNew(propertyName, programId, destinationProperty,
		callback) {
	var args = "method=getCourseByProgramForOnlineNew&programId=" + programId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(programId, args, destinationProperty, callback);
}

function getProgramsByTypeNewOnline(programTypeId, destinationProperty, callback) {
	var args = "method=getProgramsByProgramType&programTypeId=" + programTypeId;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}

function getCourseOpenDetails(courseId,callback){
		var args = "method=getCourseOpenDetails&courseId="+courseId;
		var url = "AjaxRequest.do";
		requestOperation(url, args, callback);
}

function getScoreCardTemplats(propertyName, value, destinationProperty,
		callback) {
	var args = "method=getSelectionProcessScoreCardTemplatesMap";
	if (value.length != 0 && value == "No") {
		var destinationOption = document.getElementById(destinationProperty);
		destinationOption.options[0] = new Option("- Loading -", "");
		destinationOption.selectedIndex = 0;
		var url = "InterviewDefinition.do";
		requestOperationProgram(url, args, callback);
	} else if (value.length != 0 && value == "Yes") {
		var destinationOption = document.getElementById(destinationProperty);
		for (x1 = destinationOption.options.length - 1; x1 > 0; x1--) {
			destinationOption.options[x1] = null;
		}
	}
}

function getMarkEntryAvailable(propertyName, universityId,
		destinationProperty, callback) {
	var args = "method=getMarkEntryAvailableforCourse&universityId=" + universityId
			+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(universityId, args, destinationProperty,
			callback);
}     

function getClassesForAttendanceByYear(propertyName, year, evenOrOdd, 
		destinationProperty, callback) {
	var args = "method=getClassesForAttendanceByYear&year=" + year + "&evenOrOdd=" + evenOrOdd + "&propertyName=" + propertyName;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}

//This function loads program options based on program type.
function getApplicationProgramsByType(propertyName, programTypeId,
		destinationProperty, callback) {
	var args = "method=getApplnProgramsByProgramType&programTypeId="
			+ programTypeId + "&propertyName=" + propertyName;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}

function getApplicationProgramsByTypeNew(propertyName, programTypeId,
		destinationProperty, callback) {
	var args = "method=getApplnProgramsByProgramTypeNew&programTypeId="
			+ programTypeId + "&propertyName=" + propertyName;
	populateSecondOptionsValues(programTypeId, args, destinationProperty,
			callback);
}

function getPrograms(propertyName, year, destinationProperty, callback) {
	var args = "method=getPrograms&year=" + year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}

function getSemistersByYearAndProgramType(propertyName, programType, year, destinationProperty, callback) {
	var args = "method=getSemistersByYearAndProgramType&year=" + year + "&programTypeId=" + programType;
	populateSecondOptionsValues(year, args, destinationProperty, callback);	
}

function getClassesByCourseSemesterAndAcademicYear(propertyName, semester, destinationProperty, callback, academicYear, progType, prog, course) {

	var args = "method=getClassesByCourseSemesterAndAcademicYear&semister=" + semester +"&academicYear="+academicYear+"&programTypeId="+progType+"&programId="+prog+"&courseId="+course;
	populateSecondOptionsValuesForNoLoadingOption(semester, args, destinationProperty, callback);
}

function getClassesByProgramTypeAndAcademicYear1(propertyName, programTypeId,academicYear,
		destinationProperty, callback) {

	var args = "method=getClassesByProgramTypeAndAcademicYear1&programTypeId=" + programTypeId+"&academicYear="+academicYear
			+ "&propertyName=" + propertyName;
	var destinationOption = document.getElementById(destinationProperty);
	destinationOption.options[0] = new Option("- Loading -", "");
	destinationOption.selectedIndex = 0;
	var url = "AjaxRequest.do";
	// make an request to server passing URL need to be invoked and arguments.
	requestOperation(url, args, callback);

}

function getSemistersByExamName(propertyName, examName, destinationProperty,
		callback) {
	var args = "method=getSchemeNoByExamId&examName=" + examName;
	populateSecondOptionsValues(examName, args, destinationProperty, callback);
}

function getProgramesByActivityId(propertyName, activityId, destinationProperty,
		callback) {
	var args = "method=getProgramesByActivityId&activityId=" + activityId;
	populateSecondOptionsValues(activityId, args, destinationProperty, callback);
}

function getClassMapForTeacherAndDate(propertyName, userId, attendanceDate, destinationProperty, callback) {
	var args="method=getClassMapForTeacherAndDate&userId="+userId+ "&attendanceDate="+attendanceDate;
	populateSecondOptionsValues(userId,args,destinationProperty,callback);
}
function getSemistersByYear(propertyName, year, destinationProperty, callback){
	var args = "method=getSemisterByYear&year=" + year;
	populateSecondOptionsValues(year, args, destinationProperty, callback);
}
function getClassesByYearAndSem(propertyName,semisterNo, year, destinationProperty, callback){
	var args = "method=getClassesByYearAndSem&year="+ year +"&semister="+semisterNo;
	populateSecondOptionsValues(semisterNo, args, destinationProperty, callback);
}
function getClassByAcademicYear(propertyName,academicYear, destinationProperty, callback){
	var args = "method=getClassesByAcademicYear&academicYear=" +academicYear;
	populateSecondOptionsValues(academicYear, args, destinationProperty, callback);
}
function getAidedOrNot(selectedCourse,ptype, callback) {
	var args = "method=checkCourse&courseId="+selectedCourse+"&programTypeId="+ptype;
	populateStringResult(args, callback);       
}
function getSubjectsForTeacher(evenOrOdd, year,         
		callback) {
	var args = "method=getSubjectsByTermAndYear&evenOrOdd="
			+ evenOrOdd + "&year=" + year;
	populateManyFileds(args, callback);
}
function getAtType(subId, callback) {
	var args = "method=getAttendanceType&subjectId="+subId;
	populateStringResult(args, callback);
}
function getTeachersForSubjects(subId, year,         
		callback) {
	var args = "method=getTeachersForSubjects&subjectId="
			+ subId + "&year=" + year;
	populateManyFileds(args, callback);
}
function getClassesForSubjectsandUser(evenOrOdd,userId,subId,year,callback) {
	var args = "method=getClassesForAttendanceByYearAndUserIdAndSubjectId&subjectId="
		+ subId + "&academicYear=" + year+ "&userId=" + userId+ "&evenOrOdd=" + evenOrOdd;
	populateManyFileds(args, callback);
	
}
function getSubjectsCodeNameByCourseSchemeIdByTeacher(propertyName, sCName, courseId,
		destinationProperty, callback, schemeId) {

	var args = "method=getSubjectsCodeNameByCourseSchemeIdByTeacher&schemeNo=" + sCName + "&courseId=" + courseId
			+ "&schemeId=" + schemeId;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}
function getSchemeNoByCourseIdByTeacher(propertyName,courseId,year,
		destinationProperty, callback) {
	var args = "method=getSchemeNoByCourseIdByTeacher&courseId=" + courseId  +"&year=" + year+ "&propertyName=" + propertyName;
	populateSecondOptionsValues(courseId, args, destinationProperty, callback);
}