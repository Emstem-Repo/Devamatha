function cancelMe(method){
		document.StudentBiodataForm.method.value=method;
		document.StudentBiodataForm.submit();
	}
function getDetails(applicationNumber,appliedYear,method) {
	document.StudentBiodataForm.method.value=method;
	document.StudentBiodataForm.detailsView.value=true;
	document.StudentBiodataForm.selectedAppNo.value=applicationNumber;
	document.StudentBiodataForm.selectedYear.value=appliedYear;
	document.StudentBiodataForm.submit();
}
function getDetailForEdit(applicationNumber,appliedYear,method) {
	document.StudentBiodataForm.method.value=method;
	document.StudentBiodataForm.detailsView.value=false;
	document.StudentBiodataForm.selectedAppNo.value=applicationNumber;
	document.StudentBiodataForm.selectedYear.value=appliedYear;
	document.StudentBiodataForm.submit();
}
function submitMe(method){
	document.StudentBiodataForm.method.value=method;
	document.StudentBiodataForm.submit();
}
function cancelMarkWindow(){
	document.location.href="studentBiodata.do?method=forwardStudentBiodataPage";
}

function cancelCreateStudentMarkWindow(){
	document.location.href="studentBiodata.do?method=forwardCreateStudentDetailPage";
}

function deleteStudentDetails(id,method) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.StudentBiodataForm.method.value=method;
		document.StudentBiodataForm.studentId.value=id;
		document.StudentBiodataForm.detailsView.value=false;
		document.StudentBiodataForm.submit();
	}
}
function funcOtherOccupationShowHide(id,rowid,count){
	 var selectedVal=document.getElementById(id).value;
	if(selectedVal=="Other"){
		FuncOtherOccupationShow(rowid,count);
	}else{
		FuncOtherOccupationHide(rowid,count);
	}
}	  

function FuncOtherOccupationShow(rowid,count){
	var instituteid="occupation"+count;
   document.getElementById(instituteid).style.display = "block";    
}
function FuncOtherOccupationHide(rowid,count){
var instituteid="occupation"+count;
document.getElementById(instituteid).style.display = "none";   
}
