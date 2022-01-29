function cancelMe(method){
		document.GuestFacultyInfoForm.method.value=method;
		document.GuestFacultyInfoForm.submit();
	}
function getDetailForEdit(employeeId,method) {
	document.GuestFacultyInfoForm.method.value=method;
	document.GuestFacultyInfoForm.selectedEmployeeId.value=employeeId;
	document.GuestFacultyInfoForm.submit();
}

function submitMe(method){
	document.GuestFacultyInfoForm.method.value=method;
	document.GuestFacultyInfoForm.submit();
}



