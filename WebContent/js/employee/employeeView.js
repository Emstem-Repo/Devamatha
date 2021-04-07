function cancelMe(method){
		document.EmployeeInfoViewForm.method.value=method;
		document.EmployeeInfoViewForm.submit();
	}
function getDetails(applicationNumber,appliedYear,method) {
	document.EmployeeInfoViewForm.method.value=method;
	document.EmployeeInfoViewForm.detailsView.value=true;
	document.EmployeeInfoViewForm.selectedAppNo.value=applicationNumber;
	document.EmployeeInfoViewForm.selectedYear.value=appliedYear;
	document.EmployeeInfoViewForm.submit();
}
function getDetailForEdit(employeeId,method) {
	document.EmployeeInfoViewForm.method.value=method;
	document.EmployeeInfoViewForm.selectedEmployeeId.value=employeeId;
	document.EmployeeInfoViewForm.submit();
}

function submitMe(method){
	document.EmployeeInfoViewForm.method.value=method;
	document.EmployeeInfoViewForm.submit();
}

function deleteStudentDetails(id,method) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm == true) {
		document.EmployeeInfoViewForm.method.value=method;
		document.EmployeeInfoViewForm.studentId.value=id;
		document.EmployeeInfoViewForm.detailsView.value=false;
		document.EmployeeInfoViewForm.submit();
	}
}