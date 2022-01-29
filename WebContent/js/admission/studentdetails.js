function cancelMe(method){
	   
		document.studentEditForm.method.value=method;
		document.studentEditForm.submit();
	}
function getDetails(applicationNumber,appliedYear,method) {
	document.studentEditForm.method.value=method;
	document.studentEditForm.detailsView.value=true;
	document.studentEditForm.selectedAppNo.value=applicationNumber;
	document.studentEditForm.selectedYear.value=appliedYear;
	document.studentEditForm.submit();
}
function getDetailForEdit(applicationNumber,appliedYear,method) {
	document.studentEditForm.method.value=method;
	document.studentEditForm.detailsView.value=false;
	document.studentEditForm.selectedAppNo.value=applicationNumber;
	document.studentEditForm.selectedYear.value=appliedYear;
	document.studentEditForm.submit();
}

function getDetailsView(registerNo,method) {
	document.studentEditForm.method.value=method;
	document.studentEditForm.rollRegNo.value=registerNo;
	document.studentEditForm.submit();
}
function submitMe(method){
	document.studentEditForm.method.value=method;
	document.studentEditForm.submit();
}
function cancelMarkWindow(){
	document.location.href="studentEdit.do?method=forwardStudentEditPage";
}

function cancelCreateStudentMarkWindow(){
	document.location.href="studentEdit.do?method=forwardCreateStudentDetailPage";
}

function deleteStudentDetails(id,method) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.studentEditForm.method.value=method;
		document.studentEditForm.studentId.value=id;
		document.studentEditForm.detailsView.value=false;
		document.studentEditForm.submit();
	}
}