function askWorkDetails(desig) {
	if(desig == 'Employee' || desig == 'Employer' || desig == 'Entrepreneur') {
		document.getElementById("companyNameId").style.display = 'block';
		document.getElementById("designationId").style.display = 'block';
		document.getElementById("otherJobDetailsId").style.display = 'none';
	}
	else if(desig == 'Other') {
		document.getElementById("companyNameId").style.display = 'none';
		document.getElementById("designationId").style.display = 'none';
		document.getElementById("otherJobDetailsId").style.display = 'block';
	}
	else {
		document.getElementById("companyNameId").style.display = 'none';
		document.getElementById("designationId").style.display = 'none';
		document.getElementById("otherJobDetailsId").style.display = 'none';
	}
}

function addEdnDetails() {
	document.getElementById("method").value = "addEducation";
	document.aluminiRegistrationForm.submit();
}

function removeEdnDetails() {
	document.getElementById("method").value = "removeEducation";
	document.aluminiRegistrationForm.submit();
}

function cancelRegistration() {
	document.location.href = "alumnRegistration.do?method=initAlumnRegistration";
}

var listSize = parseInt(document.getElementById("educationDetailsSize").value);

for(var i=0; i<listSize; i++) {
	var tempYearFrom = document.getElementById("tempYearFromId_" + i).value;
	if (tempYearFrom.length != 0) {
		document.getElementById("batchFrom_" + i).value = tempYearFrom;
	}
	var tempYearTo = document.getElementById("tempYearToId_" + i).value;
	if (tempYearTo.length != 0) {
		document.getElementById("batchTo_" + i).value = tempYearTo;
	}
	var tempYearPassOutYear = document.getElementById("tempYearPassOutYearId_" + i).value;
	if (tempYearPassOutYear.length != 0) {
		document.getElementById("passOutYear_" + i).value = tempYearPassOutYear;
	}
}