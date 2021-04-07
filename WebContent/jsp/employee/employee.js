function cancelMe(method){
		document.EmployeeInfoEditForm.method.value=method;
		document.EmployeeInfoEditForm.submit();
	}
function getDetails(applicationNumber,appliedYear,method) {
	document.EmployeeInfoEditForm.method.value=method;
	document.EmployeeInfoEditForm.detailsView.value=true;
	document.EmployeeInfoEditForm.selectedAppNo.value=applicationNumber;
	document.EmployeeInfoEditForm.selectedYear.value=appliedYear;
	document.EmployeeInfoEditForm.submit();
}
function getDetailForEdit(employeeId,method) {
	document.EmployeeInfoEditForm.method.value=method;
	document.EmployeeInfoEditForm.selectedEmployeeId.value=employeeId;
	document.EmployeeInfoEditForm.submit();
}


function submitMe(method){
	document.EmployeeInfoEditForm.method.value=method;
	document.EmployeeInfoEditForm.submit();
}


function deleteStudentDetails(id,method) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm == true) {
		document.EmployeeInfoEditForm.method.value=method;
		document.EmployeeInfoEditForm.studentId.value=id;
		document.EmployeeInfoEditForm.detailsView.value=false;
		document.EmployeeInfoEditForm.submit();
	}
}

function getDetailForEditNew(employeeId,method) {
	document.EmployeeInfoEditNewForm.method.value=method;
	document.EmployeeInfoEditNewForm.selectedEmployeeId.value=employeeId;
	document.EmployeeInfoEditNewForm.submit();
}