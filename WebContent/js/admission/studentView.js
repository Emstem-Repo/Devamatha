function cancelMe(method){
		document.DisciplinaryDetailsForm.method.value=method;
		document.DisciplinaryDetailsForm.submit();
	}

function getDetailsView(applicationNo,registerNo,method) {
	document.DisciplinaryDetailsForm.method.value=method;
	document.DisciplinaryDetailsForm.tempApplicationNo.value=applicationNo;
	document.DisciplinaryDetailsForm.rollRegNo.value=registerNo;
	document.DisciplinaryDetailsForm.submit();
}
function submitMe(method){
	document.DisciplinaryDetailsForm.method.value=method;
	document.DisciplinaryDetailsForm.submit();
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
		document.DisciplinaryDetailsForm.method.value=method;
		document.DisciplinaryDetailsForm.studentId.value=id;
		document.DisciplinaryDetailsForm.detailsView.value=false;
		document.DisciplinaryDetailsForm.submit();
	}
}