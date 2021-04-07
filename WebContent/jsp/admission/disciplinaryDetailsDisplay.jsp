<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function activityOpens(activityId, studentId, classesAbsent,attendanceTypeName, cocurricularLeave) {
	var abscent=classesAbsent.substring(0,classesAbsent.indexOf("."));
	var url = "studentWiseAttendanceSummary.do?method=getActivityAbsencePeriodDetails&activityId="
		+ activityId
		+ "&studentID="
		+ studentId
	    + "&classesAbsent="
	    + abscent
		+ "&attendanceTypeName="
		+ attendanceTypeName
		+ "&cocurricularLeave="
		+ cocurricularLeave;
	myRef = window
			.open(url,"StudentAbsencePeriodDetails",
					"left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
}
function cancelButton() {
	document.location.href = "disciplinaryDetails.do?method=getSearchedStudents";
}
function downloadFile(documentId) {
	document.location.href = "DocumentDownloadAction.do?documentId="
			+ documentId;
}
function getSemesterMarkDetails(qualId) {
	var url  = "disciplinaryDetails.do?method=viewSemesterMarkPage&editcountID="+qualId;
	myRef = window.open(url,"ViewSemesterMarkDetails","left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
}
function getDetailsMark(qualId) {
	var url  = "disciplinaryDetails.do?method=viewDetailMarkPage&editcountID="+qualId;
	myRef = window.open(url,"ViewDetailsMark","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
}
function detailLateralSubmit()
{
	var url  = "disciplinaryDetails.do?method=viewLateralEntryPage";
	myRef = window.open(url,"ViewLateralDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
}
function detailTransferSubmit()
{
	var url  = "disciplinaryDetails.do?method=viewTransferEntryPage";
	myRef = window.open(url,"ViewTransferDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
}

function PreviousSemester(count)
{
	document.getElementById("previousSemester").value=count;
	document.DisciplinaryDetailsForm.method.value="getDisciplinaryDetailsPreviousSemesters";
	var previousAttendanceDisplay  = "disciplinaryDetails.do?method=getDisciplinaryDetailsPreviousSemesters&previousSemester="+count;
	myRef = window.open(url,"previousAttendanceDisplay","left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
}

function winOpen(subjectId, studentId, classesAbsent, cocurricularLeave) {
	var url = "disciplinaryDetails.do?method=getAbsencePeriodDetails&subjectId="
		+ subjectId
	    + "&studentID=" 
	    + studentId
	    + "&classesAbsent="
	    + classesAbsent
	    + "&cocurricularLeave="
		+ cocurricularLeave;
	myRef = window
			.open(url,"StudentAbsencePeriodDetailsAdmin",
					"left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
}	


function closetable(c){
	document.getElementById("marks_"+c).innerHTML = "";
}
function closeAttendancetable(a,c){
	document.getElementById("attendance_s"+a+"_"+c).innerHTML = "";
}
function closeSuptable(s,e){
	document.getElementById("marks_s"+e+"_"+s).innerHTML = "";
}

var c=0;
function getRegularMarks(semNo){
	c=semNo;
	var url = "disciplinaryDetails.do";
	var args = "method=getStudentMarksCard&SemesterNo="+semNo;
	requestOperationProgram(url, args, displayMarks);
}
function displayMarks(req){
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("msg");
	var isMsg = false;
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
		if (value[I].firstChild != null) {
			var temp = value[I].firstChild.nodeValue;
			document.getElementById("notValid_"+c).innerHTML = temp;
			isMsg = true;
		}
		}
	}
	if(isMsg!=true){
		var fields = responseObj.getElementsByTagName("fields");
		var htm="<table width='100%' style='border: 1px solid black;'	rules='all'>  <tr> <tr height='30px' class='row-odd'>";
		htm=htm+"<td width='3%' align='center'>Sl No</td>"+"<td width='30%' align='center'>Subject</td>"+"<td width='10%' align='center'>Type</td>"+"<td width='12%' align='center'>CIA + ATTENDANCE</td>"+"<td width='20%' align='center'>ESE</td>"+"<td width='15%' align='center'>Total</td>"+"<td width='5%' align='center'>Credits</td>"+"<td width='5%' align='center'>Grade</td></tr>";
		 
		if(fields!=null){
			for ( var i = 0; i < fields.length; i++) {
				if(fields[i].firstChild!=null && fields[i].firstChild!='' && fields[i].firstChild.length!=0){
					htm=htm+"<tr height='25px'><td></td><td  class='row-even' colspan='8'>";
					if(fields[i]!=null){
						 var value = fields[i].getElementsByTagName("value")[0].firstChild.nodeValue;
						 htm=htm+value+ "</td></tr> ";
						 var totalMark=fields[i].getElementsByTagName("totalMark");
						 if(totalMark!=null){
							 for ( var j = 0; j < totalMark.length; j++) {
								 htm=htm+"<tr class='row-white' height='25px'> ";
								 if(totalMark[j]!=null){
								 var siNo=totalMark[j].getElementsByTagName("siNo")[0].firstChild.nodeValue;
								 var subject=totalMark[j].getElementsByTagName("subject")[0].firstChild.nodeValue;
								 var type=totalMark[j].getElementsByTagName("type")[0].firstChild.nodeValue;
								 var cia=totalMark[j].getElementsByTagName("cia")[0].firstChild.nodeValue;
								 var ese=totalMark[j].getElementsByTagName("ese")[0].firstChild.nodeValue;
								 var total=totalMark[j].getElementsByTagName("total")[0].firstChild.nodeValue;
								 var credits=totalMark[j].getElementsByTagName("credits")[0].firstChild.nodeValue;
								 var grade=totalMark[j].getElementsByTagName("grade")[0].firstChild.nodeValue;
								 if(grade==null || grade=="")
								 {
									 grade="-";
								 }
								 htm=htm+"<td align='center'>"+siNo+ "</td>"+"<td align='center'>"+subject+ "</td>"+"<td align='center'>"+type+ "</td>"+"<td align='center'>"+cia+ "</td>"+"<td align='center'>"+ese+ "</td>"+"<td align='center'>"+total+ "</td>"+"<td align='center'>"+credits+ "</td>"+"<td align='center'>"+grade+ "</td>";
								 }
								 htm=htm+"</tr>";
							 } 
						 }
						 
					}
				}
			}
		}
		
		var result = responseObj.getElementsByTagName("result")[0].firstChild.nodeValue;
		
		htm=htm+"<tr class='row-white' height='25px' >"+"<td align='center' colspan='5'><b> Result</b> </td>"+"<td align='center' colspan='3'><b>"+result +"</b></td>"+"</tr>";
		htm=htm+"<tr height='30px'> <td colspan='8' align='center'> <input type='button' value='Hide Table' onclick='closetable("+c+")'/> </td></tr>";
		htm=htm+"</table>";
		document.getElementById("marks_"+c).innerHTML = htm;
		}
	
	}

var s=0;
var e=0;
function getSupplementaryMarks(semNo, examId){
s=semNo;
e=examId;
	var url = "disciplinaryDetails.do";
	var args = "method=getStudentSupMarksCard&SemesterNo="+semNo+"&examId="+examId;
	requestOperationProgram(url, args, displaySupplementaryMarks);
}

function displaySupplementaryMarks(req){
	var responseObj = req.responseXML.documentElement;
	var fields = responseObj.getElementsByTagName("fields");
	var value = responseObj.getElementsByTagName("msg");
	var isMsg = false;
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
		if (value[I].firstChild != null) {
			var temp = value[I].firstChild.nodeValue;
			document.getElementById("notValid").innerHTML = temp;
			isMsg = true;
		}
		}
	}
	if(isMsg=true){
		var htm="<table width='100%' style='border: 1px solid black;'	rules='all'>  <tr> <tr height='30px' class='row-odd'>";
		htm=htm+"<td width='3%' align='center'>Sl No</td>"+"<td width='30%' align='center'>Subject</td>"+"<td width='10%' align='center'>Type</td>"+"<td width='12%' align='center'>CIA + ATTENDANCE</td>"+"<td width='20%' align='center'>ESE</td>"+"<td width='15%' align='center'>Total</td>"+"<td width='5%' align='center'>Credits</td>"+"<td width='5%' align='center'>Grade</td></tr>";
		if(fields!=null){
			for ( var i = 0; i < fields.length; i++) {
				if(fields[i].firstChild!=null && fields[i].firstChild!='' && fields[i].firstChild.length!=0){
					htm=htm+"<tr height='25px'><td></td><td  class='row-even' colspan='8'>";
					if(fields[i]!=null){
						 var value = fields[i].getElementsByTagName("value")[0].firstChild.nodeValue;
						 htm=htm+value+ "</td></tr> ";
						 var totalMark=fields[i].getElementsByTagName("totalMark");
						 if(totalMark!=null){
							 for ( var j = 0; j < totalMark.length; j++) {
								 var theory=totalMark[j].getElementsByTagName("theory")[0].firstChild.nodeValue;
							     var practical=totalMark[j].getElementsByTagName("practical")[0].firstChild.nodeValue;
							     var appearedPractical=totalMark[j].getElementsByTagName("appearedPractical")[0].firstChild.nodeValue;
							     var appearedTheory=totalMark[j].getElementsByTagName("appearedTheory")[0].firstChild.nodeValue;
								 htm=htm+"<tr class='row-white' height='25px'> ";
								 if(totalMark[j]!=null){
								  if(theory!=null && theory=='true' && appearedTheory=='true')
								     {
									
								 var siNo=totalMark[j].getElementsByTagName("siNo")[0].firstChild.nodeValue;
								 var subject=totalMark[j].getElementsByTagName("subject")[0].firstChild.nodeValue;
								 var type=totalMark[j].getElementsByTagName("type")[0].firstChild.nodeValue;
								 var cia=totalMark[j].getElementsByTagName("cia")[0].firstChild.nodeValue;
								 var ese=totalMark[j].getElementsByTagName("ese")[0].firstChild.nodeValue;
								 var total=totalMark[j].getElementsByTagName("total")[0].firstChild.nodeValue;
								 var credits=totalMark[j].getElementsByTagName("credits")[0].firstChild.nodeValue;
								 var grade=totalMark[j].getElementsByTagName("grade")[0].firstChild.nodeValue;
								 if(grade==null || grade=="")
								 {
									 grade="-";
								 }
								 htm=htm+"<td align='center'>"+siNo+ "</td>"+"<td align='left'>&nbsp&nbsp " + subject+ "</td>"+"<td align='center'>"+type+ "</td>"+"<td align='center'>"+cia+ "</td>"+"<td align='center'>"+ese+ "</td>"+"<td align='center'>"+total+ "</td>"+"<td align='center'>"+credits+ "</td>"+"<td align='center'>"+grade+ "</td>";
								 }
								  
								
								 htm=htm+"</tr>";
								 
							     if(practical!=null && practical=='true' && appearedPractical=='true')
							     {
								 var pracsiNo=totalMark[j].getElementsByTagName("pracsiNo")[0].firstChild.nodeValue;
								 var pracsubject=totalMark[j].getElementsByTagName("pracsubject")[0].firstChild.nodeValue;
								 var practype=totalMark[j].getElementsByTagName("practype")[0].firstChild.nodeValue;
								 var praccia=totalMark[j].getElementsByTagName("praccia")[0].firstChild.nodeValue;
								 var pracese=totalMark[j].getElementsByTagName("pracese")[0].firstChild.nodeValue;
								 var practotal=totalMark[j].getElementsByTagName("practotal")[0].firstChild.nodeValue;
								 var praccredits=totalMark[j].getElementsByTagName("praccredits")[0].firstChild.nodeValue;
								 var pracgrade=totalMark[j].getElementsByTagName("pracgrade")[0].firstChild.nodeValue;
								 if(pracgrade==null || pracgrade=="")
								 {
									 pracgrade="-";
								 }
								 htm=htm+"<tr class='row-white' height='25px'> ";
								 htm=htm+"<td align='center'>"+pracsiNo+ "</td>"+"<td align='left'>&nbsp&nbsp " + pracsubject+ "</td>"+"<td align='center'>"+practype+ "</td>"+"<td align='center'>"+praccia+ "</td>"+"<td align='center'>"+pracese+ "</td>"+"<td align='center'>"+practotal+ "</td>"+"<td align='center'>"+praccredits+ "</td>"+"<td align='center'>"+pracgrade+ "</td>";
							     
							     }
								 htm=htm+"</tr>";
							     }
							 }
						 }
						
					}
				}
			}
		}
		var result = responseObj.getElementsByTagName("result")[0].firstChild.nodeValue;
		 htm=htm+"<tr class='row-white' height='25px'>"+"<td align='center' colspan='5'><b> Result</b></td>"+"<td align='center' colspan='3'><b>"+result +"</b></td>"+"</tr>";
		htm=htm+"<tr> <td colspan='8' align='center'> <input type='button' value='Hide Table' onclick='closeSuptable("+s+","+e+")'/> </td></tr>";
		htm=htm+"</table>";
		document.getElementById("marks_s"+e+"_"+s).innerHTML = htm;
		}
}

var a=0;
var c=0;
function getAttendance(semNo,classId){
a=semNo;
c=classId;
	var url = "disciplinaryDetails.do";
	var args = "method=getDisciplinaryDetailsPreviousSemesters&previousSemester="+semNo+"&ClassId="+classId;
	requestOperationProgram(url, args, displayAttendance);
}

function displayAttendance(req)
{
	var responseObj = req.responseXML.documentElement;
	var fields = responseObj.getElementsByTagName("fields");
	var fields1=responseObj.getElementsByTagName("fields1");
	var examName="";
	var SemNo=1;
	var htm="<table width='100%' style='border: 1px solid black;' rules='all'>  <tr> <tr height='30px' class='row-odd'>";
	 htm=htm+"<td width='150' align='center'>"+"Class Name"+"</td>"+"<td width='300' align='center'>"+"Subject"+"</td>"+"<td width='80' align='center'>"+"Absent"+"</td>"+"<td width='80' align='center'>"+"Attendance %"+"</td>";
	if(fields1!=null){
			for ( var i = 0; i < fields1.length; i++) {
			if(fields1[i].firstChild!=null && fields1[i].firstChild!='' && fields1[i].firstChild.length!=0){
				if(fields1[i]!=null){
					// htm=htm+ "<tr class='row-white'> ";
					 var Title=fields1[i].getElementsByTagName("Title");
					 if(Title!=null){
						 for ( var j = 0; j < Title.length; j++) {
							var examName=Title[j].getElementsByTagName("examName")[0].firstChild.nodeValue;
							if(examName!='-')
								 htm=htm+"<td>"+examName+"</td>";
                             
							 }
						 }
					 }
				}
		} 
	}
	htm=htm+"</tr>";
		if(fields!=null){
			for ( var i = 0; i < fields.length; i++) {
			if(fields[i].firstChild!=null && fields[i].firstChild!='' && fields[i].firstChild.length!=0){
				if(fields[i]!=null){
					 htm=htm+ "<tr class='row-white' height='30px'> ";
					 var totalMark=fields[i].getElementsByTagName("totalMark");
					 if(totalMark!=null){
						 for ( var j = 0; j < totalMark.length; j++) {
							 if(totalMark[j]!=null){
							
							 var className=totalMark[j].getElementsByTagName("className")[0].firstChild.nodeValue;
							 var subject=totalMark[j].getElementsByTagName("subject")[0].firstChild.nodeValue;
							 var absent=totalMark[j].getElementsByTagName("absent")[0].firstChild.nodeValue;
							 var attendence=totalMark[j].getElementsByTagName("attendence")[0].firstChild.nodeValue;
							 var mark=totalMark[j].getElementsByTagName("mark");
							 var mark1="";
								 for ( var t = 0; t < mark.length; t++) {
									 var theoryMarks="";
									 var practicalMarks="";
									 if(mark[t].getElementsByTagName("theoryMarks")!=null || mark[t].getElementsByTagName("practicalMarks")!=null)
									  theoryMarks=mark[t].getElementsByTagName("theoryMarks")[0].childNodes[0].nodeValue;
									 practicalMarks=mark[t].getElementsByTagName("practicalMarks")[0].childNodes[0].nodeValue;
								 
									if(theoryMarks.length>0 || practicalMarks.length>0 )
									{	
										
											mark1=mark1+"<td>"+theoryMarks+"     "+practicalMarks+"</td>";
																	
									}
									
								 }
							 htm=htm+"<td align='center'>"+className+ "</td>"+"<td align='left'>&nbsp&nbsp " + subject+ "</td>"+"<td align='center'>"+absent+ "</td>"+"<td align='center'>"+attendence+ "</td>"+mark1;
							 }
							 htm=htm+"</tr>";
						 }
					 }
				}
			}
		}
	}
	htm=htm+"<tr> <td  align='center' colspan='11'> <input type='button' value='Hide Table' onclick='closeAttendancetable("+a+","+c+")'/> </td></tr>";
	htm=htm+"</table>";
	document.getElementById("attendance_s"+a+"_"+c).innerHTML = htm;
}

function editRemarks(id){
	document.location.href = "disciplinaryDetails.do?method=editRemarks&remarkid="+ id;
//		document.getElementById("method").value = "editRemarks";
//		document.DisciplinaryDetailsForm.submit();
}
function winOpen1() {
	//var url = "birtFeeReport.do?method=initBirtAbsenceReportsLinkForStudentDetails";
	var url="disciplinaryDetails.do?method=getAbscentDetails";
	myRef = window.open(url, "viewPrivileges",
			'scrollbars=1,height=500,width=1050');
}
function getHostelDetails(studentId){
	var url = "disciplinaryDetails.do";
	var args = "method=getHostelDetails&studId="+studentId;
	requestOperationProgram(url, args, displayHostelDetails);
}
function displayHostelDetails(req){
	var responseObj = req.responseXML.documentElement;
	var HostelDetails = responseObj.getElementsByTagName("HostelDetails");
	var HLeaveDetails = responseObj.getElementsByTagName("LeaveDetails");
	var HStudentAttendance = responseObj.getElementsByTagName("HStudAttendance");
	var HDisciplinary = responseObj.getElementsByTagName("HDisciplinary");
	var HFine = responseObj.getElementsByTagName("HFine");
	var	htm="<table width='100%' cellspacing='1' cellpadding='2' id='report1'><tr class='data'><td colspan='2'><table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>";
		htm=htm+"<tr><td><img src='images/01.gif' width='5' height='5'></td><td width='914' background='images/02.gif'></td><td><img src='images/03.gif' width='5' height='5'></td>";
		htm=htm+"</tr><tr><td width='5' background='images/left.gif'></td><td width='100%' valign='top'><table width='99%' border='0' align='center' cellpadding='0' cellspacing='0'>";
		//start hostel details
		htm=htm+"<tr><td><img src='images/01.gif' width='5' height='5'></td><td width='914' background='images/02.gif'></td><td><img src='images/03.gif' width='5' height='5'></td>";
		htm=htm+"</tr><tr><td width='5' background='images/left.gif'></td><td width='100%' valign='top'><table width='100%' cellspacing='1' cellpadding='2'>";
		 for ( var I = 0; I < HostelDetails.length; I++) {
				if(HostelDetails[I]!=null){
					var hostelName = HostelDetails[I].getElementsByTagName("hostelName")[0].firstChild.nodeValue;
					var block= HostelDetails[I].getElementsByTagName("block")[0].firstChild.nodeValue;
					var unit = HostelDetails[I].getElementsByTagName("unit")[0].firstChild.nodeValue;
					var roomNo = HostelDetails[I].getElementsByTagName("roomNo")[0].firstChild.nodeValue;
					var bedNo = HostelDetails[I].getElementsByTagName("bedNo")[0].firstChild.nodeValue;
					htm=htm+"<tr class='row-odd'><td width='10%' height='25' class='row-odd' align='right'>Hostel Name:</td><td width='20%' class='row-even'>"+hostelName+"</td>";
					htm=htm+"<td width='10%' class='row-odd' align='right'>Block:</td><td width='20%'  height='25' class='row-even'>"+block+"</td>";
					htm=htm+"<td width='10%' class='row-odd' align='right'>Unit:</td><td width='20%'  height='25' class='row-even'>"+unit+"</td></tr>";
					htm=htm+"<tr><td width='10%' class='row-odd' align='right'>Room:</td><td width='20%'  height='25' class='row-even'>"+roomNo+"</td>";
					htm=htm+"<td width='10%' class='row-odd' align='right'>Bed:</td><td width='20%'  height='25' class='row-even'>"+bedNo+"</td><td width='10%' class='row-odd'></td><td width='20%'  height='25' class='row-even'></td></tr>";
				}
			}
		htm=htm+"</table></td><td background='images/right.gif' width='5' ></td></tr><tr><td><img src='images/04.gif' width='5'></td><td background='images/05.gif'></td><td><img src='images/06.gif'></td></tr>";
		//end hostel details
		// start leave details
		htm=htm+"<tr><td colspan='2' align='left'><span class='heading'>&nbsp;Leave Details</span></td></tr><tr><td><img src='images/01.gif' width='5' height='5'></td><td width='914' background='images/02.gif'></td>";
		htm=htm+"<td><img src='images/03.gif' width='5' height='5'></td></tr><tr><td width='5' background='images/left.gif'></td><td width='100%' valign='top'><table width='100%' cellspacing='1' cellpadding='2'>";
		htm=htm+"<tr><td width='10%' class='row-odd' align='center'>S.No</td><td width='40%' class='row-odd' align='center'>Leave From</td><td width='40%' class='row-odd' align='center'>Leave To</td>";
		htm=htm+"<td width='10%' class='row-odd' align='center'>Status</td></tr>";
		if(HLeaveDetails!=null){
		for ( var I = 0; I < HLeaveDetails.length; I++) {
			if(HLeaveDetails[I]!=null){
				var leaveFrom= HLeaveDetails[I].getElementsByTagName("leaveFrom")[0].firstChild.nodeValue;
				var leaveTo = HLeaveDetails[I].getElementsByTagName("leaveTo")[0].firstChild.nodeValue;
				var status = HLeaveDetails[I].getElementsByTagName("status")[0].firstChild.nodeValue;
				if(I%2==0){
					htm=htm+"<tr><td  height='25' class='row-even' align='center'>"+(I+1)+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='center'>"+leaveFrom+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='center'>"+leaveTo+"</td>";
					htm=htm+"<td  height='25' class='row-even' align='center'>"+status+"</td></tr>";
				}else{
					htm=htm+"<tr><td  height='25' class='row-white' align='center'>"+(I+1)+"</td>";
					htm=htm+"<td  height='25' class='row-white' align='center'>"+leaveFrom+"</td>";
					htm=htm+"<td  height='25' class='row-white' align='center'>"+leaveTo+"</td>";
					htm=htm+"<td  height='25' class='row-white' align='center'>"+status+"</td></tr>";
				}
			}
		}
		}
		htm=htm+"</table></td><td background='images/right.gif' width='5' ></td></tr><tr><td><img src='images/04.gif' width='5'></td><td background='images/05.gif'></td><td><img src='images/06.gif'></td></tr>";
		//end leave details
		// start attendance details
		htm=htm+"<tr><td colspan='2' align='left'><span class='heading'>&nbsp;Absentees Details</span></td></tr><tr><td><img src='images/01.gif' width='5' height='5'></td><td width='914' background='images/02.gif'></td>";
		htm=htm+"<td><img src='images/03.gif' width='5' height='5'></td></tr><tr><td width='5' background='images/left.gif'></td><td width='100%' valign='top'><table width='100%' cellspacing='1' cellpadding='2'>";
		htm=htm+"<tr><td width='10%' class='row-odd' align='center'>S.No</td><td width='40%' class='row-odd' align='center'>Date</td><td width='10%' class='row-odd' align='center'>Session</td></tr>";
		if(HStudentAttendance!=null){
			for ( var i = 0; i < HStudentAttendance.length; i++) {
				if(HStudentAttendance[i].firstChild!=null && HStudentAttendance[i].firstChild!='' && HStudentAttendance[i].firstChild.length!=0){
					if(HStudentAttendance[i]!=null){
										var date= HStudentAttendance[i].getElementsByTagName("date")[0].firstChild.nodeValue;
										var session = HStudentAttendance[i].getElementsByTagName("session")[0].firstChild.nodeValue;
										if(i%2==0){
											htm=htm+"<tr><td  height='25' class='row-even' align='center'>"+(i+1)+"</td>";
											htm=htm+"<td  height='25' class='row-even' align='center'>"+date+"</td>";
											htm=htm+"<td  height='25' class='row-even' align='center'>"+session+"</td>";
										}else{
											htm=htm+"<tr><td  height='25' class='row-white' align='center'>"+(i+1)+"</td>";
											htm=htm+"<td  height='25' class='row-white' align='center'>"+date+"</td>";
											htm=htm+"<td  height='25' class='row-white' align='center'>"+session+"</td>";
										}
							
					}
				}
			}
		}
		htm=htm+"</table></td><td background='images/right.gif' width='5' ></td></tr><tr><td><img src='images/04.gif' width='5'></td><td background='images/05.gif'></td><td><img src='images/06.gif'></td></tr>";
		//end attendance details
		// start disciplinary details
		htm=htm+"<tr><td colspan='2' align='left'><span class='heading'>&nbsp;Disciplinary Details</span></td></tr><tr><td><img src='images/01.gif' width='5' height='5'></td><td width='914' background='images/02.gif'></td>";
		htm=htm+"<td><img src='images/03.gif' width='5' height='5'></td></tr><tr><td width='5' background='images/left.gif'></td><td width='100%' valign='top'><table width='100%' cellspacing='1' cellpadding='2'>";
		htm=htm+"<tr><td width='10%' class='row-odd' align='center'>S.No</td><td width='40%' class='row-odd' align='center'>Disciplinary Action</td><td width='10%' class='row-odd' align='center'>Date</td>";
		htm=htm+"<td width='40%' class='row-odd' align='center'>Description</td></tr>";
		if(HDisciplinary!=null){
			for ( var i = 0; i < HDisciplinary.length; i++) {
				if(HDisciplinary[i].firstChild!=null && HDisciplinary[i].firstChild!='' && HDisciplinary[i].firstChild.length!=0){
					if(HDisciplinary[i]!=null){
										var daction = HDisciplinary[i].getElementsByTagName("disciplineTypeName")[0].firstChild.nodeValue;
										var date= HDisciplinary[i].getElementsByTagName("date")[0].firstChild.nodeValue;
										var description = HDisciplinary[i].getElementsByTagName("description")[0].firstChild.nodeValue;
										if(i%2==0){
											htm=htm+"<tr><td  height='25' class='row-even' align='center'>"+(i+1)+"</td>";
											htm=htm+"<td  height='25' class='row-even' align='center'>"+daction+"</td>";
											htm=htm+"<td  height='25' class='row-even' align='center'>"+date+"</td>";
											htm=htm+"<td  height='25' class='row-even' align='center'>"+description+"</td>";
										}else{
											htm=htm+"<tr><td  height='25' class='row-white' align='center'>"+(i+1)+"</td>";
											htm=htm+"<td  height='25' class='row-white' align='center'>"+daction+"</td>";
											htm=htm+"<td  height='25' class='row-white' align='center'>"+date+"</td>";
											htm=htm+"<td  height='25' class='row-white' align='center'>"+description+"</td>";
										}
							
					}
				}
			}
		}
		htm=htm+"</table></td><td background='images/right.gif' width='5' ></td></tr><tr><td><img src='images/04.gif' width='5'></td><td background='images/05.gif'></td><td><img src='images/06.gif'></td></tr>";
		//end disciplinary details
		// start Fine details
		htm=htm+"<tr><td colspan='2' align='left'><span class='heading'>&nbsp;Fine Details</span></td></tr><tr><td><img src='images/01.gif' width='5' height='5'></td><td width='914' background='images/02.gif'></td>";
		htm=htm+"<td><img src='images/03.gif' width='5' height='5'></td></tr><tr><td width='5' background='images/left.gif'></td><td width='100%' valign='top'><table width='100%' cellspacing='1' cellpadding='2'>";
		htm=htm+"<tr><td width='10%' class='row-odd' align='center'>S.No</td><td width='20%' class='row-odd' align='center'>Category</td><td width='10%' class='row-odd' align='center'>Date</td>";
		htm=htm+"<td width='10%' class='row-odd' align='center'>Amount</td><td width='10%' class='row-odd' align='center'>Paid</td><td width='40%' class='row-odd' align='center'>Remarks</td></tr>";
		if(HFine!=null){
			for ( var i = 0; i < HFine.length; i++) {
				if(HFine[i]!=null){
				if(HFine[i].firstChild!=null && HFine[i].firstChild!='' && HFine[i].firstChild.length!=0){
					
										var category = HFine[i].getElementsByTagName("category")[0].firstChild.nodeValue;
										var date= HFine[i].getElementsByTagName("date")[0].firstChild.nodeValue;
										var amount = HFine[i].getElementsByTagName("amount")[0].firstChild.nodeValue;
										var pay= HFine[i].getElementsByTagName("pay")[0].firstChild.nodeValue;
										var remarks=" ";
										if(HFine[i].getElementsByTagName("remarks")[0].firstChild!=null && HFine[i].getElementsByTagName("remarks")[0].firstChild!=""){
											remarks = HFine[i].getElementsByTagName("remarks")[0].firstChild.nodeValue;
										}
										
										if(i%2==0){
											htm=htm+"<tr><td  height='25' class='row-even' align='center'>"+(i+1)+"</td>";
											htm=htm+"<td  height='25' class='row-even' align='center'>"+category+"</td>";
											htm=htm+"<td  height='25' class='row-even' align='center'>"+date+"</td>";
											htm=htm+"<td  height='25' class='row-even' align='center'>"+amount+"</td>";
											htm=htm+"<td  height='25' class='row-even' align='center'>"+pay+"</td>";
											htm=htm+"<td  height='25' class='row-even' align='center'>"+remarks+"</td>";
										}else{
											htm=htm+"<tr><td  height='25' class='row-white' align='center'>"+(i+1)+"</td>";
											htm=htm+"<td  height='25' class='row-white' align='center'>"+category+"</td>";
											htm=htm+"<td  height='25' class='row-white' align='center'>"+date+"</td>";
											htm=htm+"<td  height='25' class='row-white' align='center'>"+amount+"</td>";
											htm=htm+"<td  height='25' class='row-white' align='center'>"+pay+"</td>";
											htm=htm+"<td  height='25' class='row-white' align='center'>"+remarks+"</td>";
										}
							
					}
				}
			}
		}
		
		htm=htm+"</table></td><td background='images/right.gif' width='5' ></td></tr><tr><td><img src='images/04.gif' width='5'></td><td background='images/05.gif'></td><td><img src='images/06.gif'></td></tr>";
		//end Fine details
		htm=htm+"<tr> <td  align='center' colspan='11'> <input type='button' value='Hide Hostel Details' onclick='closeHostel()'/></td></tr><tr></tr>";
		htm=htm+"</table></td><td background='images/right.gif' width='5' ></td></tr><tr><td><img src='images/04.gif' width='5'></td><td background='images/05.gif'></td>";
		htm=htm+"<td><img src='images/06.gif'></td></tr></table></td></tr></table>";
	document.getElementById("hDetails").innerHTML = htm;
}
function closeHostel(){
	document.getElementById("hDetails").innerHTML = "";
}
</script>


<script type="text/javascript">

function winOpenAm(am) {
	//alert(am);
	var url = "disciplinaryDetails.do?method=getAmAbsenceDetails&am="+am;
	myRef = window.open(url,"StudentAbsencePeriodDetailsAdmin","left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
}	

function winOpenPm(pm) {
	//alert(pm);
	var url = "disciplinaryDetails.do?method=getPmAbsenceDetails&pm="+pm;
	myRef = window.open(url,"StudentAbsencePeriodDetailsAdmin","left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
}
</script>

<html:form action="/disciplinaryDetails.do">
	<html:hidden property="method" styleId="method" value="getRemarksPage" />
	<html:hidden property="remarkid" name="DisciplinaryDetailsForm"/>
	<!-- <input type="hidden" id="Semester" name="Semester">-->

	<table width="98%" height="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs">Admission <span
				class="Bredcrumbs">&gt;&gt;Student Detail View&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td height="1451">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> Student
					Detail View </strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">
					<table width="100%" height="100%" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;Student
							Details</td>
						</tr>

						<tr>
							<td colspan="2" class="heading">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="22" valign="top">
									<table width="100%" height="22" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td height="20" class="row-odd">
											<div align="right">Register/Roll Number:</div>
											</td>
											<td height="20" align="left" class="row-even style15"><span
												class="style16">&nbsp; <span class="style27">
												<bean:write	name="DisciplinaryDetailsForm" property="objto.regRollNo" /></span></span>
											</td>
											
											<td height="20" class="row-odd">
											<div align="right">Attendance Percentage:</div></td>
											<td height="20" align="left" class="row-even style15"><span
												class="style16">&nbsp; <span class="style27">
												<logic:notEmpty name="DisciplinaryDetailsForm" property="displayPercentage">
                            	                   <nested:iterate name="DisciplinaryDetailsForm" property="studentTOList" indexId="count">
                            	                  <font color="red"><b><nested:write property="totalAggrPer"/>%</b></font>
                            	                   </nested:iterate>
                                                </logic:notEmpty></span></span>
											</td>
										</tr>
											
										<!--  <td class="row-even">
											<table>
												<tr>
													<td height="20" class="row-even" colspan="2">
													<div>SessionWise Classes Attended = <bean:write
														name="DisciplinaryDetailsForm" property="totalPresent" />
													&nbsp; &nbsp; Total Session Held = <bean:write
														name="DisciplinaryDetailsForm" property="totalConducted" />&nbsp;&nbsp;
												Attendance Percentage= <b><bean:write name="DisciplinaryDetailsForm"
														property="totalAggrPer" /></b></div>
													</td>
												</tr>
												<tr>
													<td class="row-odd" colspan="2">*Class Attended
													includes Co-curricular leave*</td>
												</tr>
											</table>
											</td>
											<td class="row-even">
											<a href="javascript:winOpen1()"><bean:message
																	key="admissionForm.student.cocurricular.or.absence.reports" /></a>
											</td> 	
										-->
										
									</table>
									</td>
									<td background="images/right.gif" width="5" height="22"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td width="100%" background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" align="left" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="22" align="left" valign="top">
									<table width="100%" height="22" border="0" cellpadding="0">
										<tr class="row-white">

											<td width="50%" height="101" align="center" class="row-even"><img
												src='<%=request.getContextPath()%>/DisplinaryPhotoServlet'
												height="150Px" width="150Px" /></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="22"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td width="100%" align="left" background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>

						<tr>
							<td colspan="2" class="heading">
							<table width="100%" height="28" border="0" align="center"
								cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" height="28" background="images/left.gif"></td>
									<td width="100%" height="28" valign="middle">
									<table width="100%" height="26" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-even">
											<td width="50%" class="row-odd">
											<div align="right">Course:</div>
											<div align="right"></div>
											</td>
											<td width="50%" height="24" align="left" class="row-even">&nbsp;<span
												class="style7"> <span class="style24"><bean:write
												name="DisciplinaryDetailsForm" property="objto.course" /></span></span></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="28"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td width="100%" background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;Personal
							Data</td>
						</tr>

						<tr>
							<td align="center" valign="top" class="heading">
							<table width="100%" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="25" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="50%" height="33" class="row-odd">
											<div align="right">Name:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style24"><bean:write
												name="DisciplinaryDetailsForm" property="objto.name" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="25" class="row-odd">
											<div align="right">Date of Birth:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style24"><bean:write
												name="DisciplinaryDetailsForm" property="objto.dateOfBirth" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="25" class="row-odd">
											<div align="right">Gender:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style24"><bean:write
												name="DisciplinaryDetailsForm" property="objto.gender" /> </span></td>
										</tr>
										<tr>
											<td width="50%" height="25" class="row-odd">
											<div align="right">Email:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style24"><bean:write
												name="DisciplinaryDetailsForm" property="objto.eMail" /> </span></td>
										</tr>
										<tr>
											<td height="4" class="row-odd">
											<div align="right">Mobile Number:</div>
											</td>
											<td height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.mobNumber" /></span></td>
										</tr>
										<tr>
											<td height="4" class="row-odd">
											<div align="right">Phone Number:</div>
											</td>
											<td height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.phoneNo" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="7" class="row-odd">
											<div align="right">Nationality:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.nationality" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="8" align="right" class="row-odd">Date
											Of Admission:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm"
												property="objto.dateOfAddmission" /></span></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td align="center" valign="top" class="heading">
							<table width="100%" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="25" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="50%" height="3" align="right" class="row-odd">Current
											Address:</td>
											<td width="50%" height="53" align="left" class="row-even">
											<p class="style26"><bean:write
												name="DisciplinaryDetailsForm"
												property="objto.currentAddress" /></p>
											</td>
										</tr>
										<tr>
											<td width="50%" height="1" align="right" class="row-odd">Permanent
											Address:</td>
											<td width="50%" height="53" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm"
												property="objto.permanentAddress" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="0" align="right" class="row-odd">Passport
											Number:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm"
												property="objto.passportNumber" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="1" align="right" class="row-odd">Issueing
											Country:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.issueCountry" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="-2" align="right" class="row-odd">Valid
											up to:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.validUpTo" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="0" align="right" class="row-odd">Resident
											Permit No:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm"
												property="objto.resedentPermitNo" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="2" align="right" class="row-odd">Obtained
											from Commissioner of Police, Dated:</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.obtainedDate" /></span></td>
										</tr>

									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="knowledgepro.admission.educationalInfo" /></td>
						</tr>
						<tr>
							<td colspan="2" valign="top">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td height="25" class="row-odd" width="10%">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td height="25" class="row-odd" width="10%" align="center"><bean:message
												key="knowledgepro.admission.qualification" /></td>
											<td class="row-odd">Exam Name</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.universityBoard" /></div>
											</td>
											<td class="row-odd" width="10%">
											<div align="center"><bean:message
												key="knowledgepro.admission.instituteName" /></div>
											</td>
											<td class="row-odd" width="10%">
											<div align="center"><bean:message
												key="admissionForm.education.State.label" /></div>
											</td>
											<td class="row-odd" width="10%" align="center"><bean:message
												key="knowledgepro.admission.passingYear" /></td>
											<td class="row-odd" align="center"><bean:message
												key="knowledgepro.applicationform.passingmonth" /></td>
											<td class="row-odd" width="10%">
											<div align="center"><bean:message
												key="knowledgepro.admission.marksObtained" /></div>
											</td>
											<td class="row-odd" width="10%">
											<div align="center"><bean:message
												key="knowledgepro.admission.maxMark" /></div>
											</td>
											<td class="row-odd" width="10%">
											<div align="center"><bean:message
												key="knowledgepro.admission.attempts" /></div>
											</td>
											<td class="row-odd" width="10%" align="center"><bean:message
												key="knowledgepro.applicationform.prevregno.label" /></td>
										</tr>
										<c:set var="temp" value="0" />
										<nested:iterate property="eduList" id="ednQualList"
											indexId="count">

											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td width="5%" height="25" class="row-even">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td width="11%" height="25" class="row-even"><bean:write
															name="ednQualList" property="docName" /></td>
														<td width="9%" class="row-even">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="examConfigured">
															<nested:write property="selectedExamName" />
														</logic:equal></div>
														</td>
														<td width="20%" class="row-even">
														<div align="center"><bean:write name="ednQualList"
															property="universityName" /></div>
														</td>
														<td width="22%" class="row-even">
														<div align="center"><bean:write name="ednQualList"
															property="institutionName" /></div>
														</td>
														<td width="22%" class="row-even">
														<div align="left"><bean:write name="ednQualList"
															property="stateName" /></div>
														</td>
														<td width="16%" class="row-even" align="center"><bean:write
															name="ednQualList" property="yearPassing" /></td>
														<td width="16%" class="row-even" align="center"><logic:equal
															name="ednQualList" property="monthPassing" value="1">JANUARY</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="2">FEBRUARY</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="3">MARCH</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="4">APRIL</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="5">MAY</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="6">JUNE</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="7">JULY</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="8">AUGUST</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="9">SEPTEMBER</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="10">OCTOBER</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="11">NOVEMBER</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="12">DECEMBER</logic:equal></td>
														<logic:equal value="true" property="semesterWise"
															name="ednQualList">
															<logic:equal value="false" property="consolidated"
																name="ednQualList">
																<td class="row-even" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getSemesterMarkDetails('<bean:write name="ednQualList" property="id"/>')"><bean:message
																	key="admissionForm.approveview.prereq.semmark.viewlink" /></a></div>
																</td>
															</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated"
															name="ednQualList">
															<logic:equal value="false" property="semesterWise"
																name="ednQualList">
																<td class="row-even" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getDetailsMark('<bean:write name="ednQualList" property="id"/>')"><bean:message
																	key="admissionForm.approveview.prereq.detmark.viewlink" /></a></div>
																</td>
															</logic:equal>
														</logic:equal>

														<logic:notEqual value="true" property="semesterWise"
															name="ednQualList">
															<logic:notEqual value="false" property="consolidated"
																name="ednQualList">
																<td class="row-even">
																<div align="center"><nested:write
																	property="marksObtained"></nested:write></div>
																</td>
																<td class="row-even">
																<div align="center"><nested:write
																	property="totalMarks"></nested:write></div>
																</td>
															</logic:notEqual>
														</logic:notEqual>

														<td width="9%" class="row-even">
														<div align="center"><bean:write name="ednQualList"
															property="noOfAttempts" /></div>
														</td>
														<td width="9%" class="row-even">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="lastExam">
															<nested:write property="previousRegNo" />
														</logic:equal></div>
														</td>
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td height="25" class="row-white">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td height="25" class="row-white"><bean:write
															name="ednQualList" property="docName" /></td>
														<td class="row-white">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="examConfigured">
															<nested:write property="selectedExamName" />
														</logic:equal></div>
														</td>
														<td class="row-white">
														<div align="center"><bean:write name="ednQualList"
															property="universityName" /></div>
														</td>
														<td class="row-white">
														<div align="center"><bean:write name="ednQualList"
															property="institutionName" /></div>
														</td>
														<td width="22%" class="row-white">
														<div align="left"><bean:write name="ednQualList"
															property="stateName" /></div>
														</td>
														<td class="row-white" align="center"><bean:write
															name="ednQualList" property="yearPassing" /></td>
														<td class="row-white"><logic:equal name="ednQualList"
															property="monthPassing" value="1">JANUARY</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="2">FEBRUARY</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="3">MARCH</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="4">APRIL</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="5">MAY</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="6">JUNE</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="7">JULY</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="8">AUGUST</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="9">SEPTEMBER</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="10">OCTOBER</logic:equal>
														<logic:equal name="ednQualList" property="monthPassing"
															value="11">NOVEMBER</logic:equal> <logic:equal
															name="ednQualList" property="monthPassing" value="12">DECEMBER</logic:equal>
														</td>

														<logic:equal value="true" property="semesterWise"
															name="ednQualList">
															<logic:equal value="false" property="consolidated"
																name="ednQualList">
																<td class="row-white" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getSemesterMarkDetails('<bean:write name="ednQualList" property="id"/>')"><bean:message
																	key="admissionForm.approveview.prereq.semmark.viewlink" /></a></div>
																</td>
															</logic:equal>
														</logic:equal>
														<logic:equal value="false" property="consolidated"
															name="ednQualList">
															<logic:equal value="false" property="semesterWise"
																name="ednQualList">
																<td class="row-white" colspan="2">
																<div align="center"><a href="javascript:void(0)"
																	onclick="getDetailsMark('<bean:write name="ednQualList" property="id"/>')"><bean:message
																	key="admissionForm.approveview.prereq.detmark.viewlink" /></a></div>
																</td>
															</logic:equal>
														</logic:equal>

														<logic:notEqual value="true" property="semesterWise"
															name="ednQualList">
															<logic:notEqual value="false" property="consolidated"
																name="ednQualList">
																<td class="row-white">
																<div align="center"><nested:write
																	property="marksObtained"></nested:write></div>
																</td>
																<td class="row-white">
																<div align="center"><nested:write
																	property="totalMarks"></nested:write></div>
																</td>
															</logic:notEqual>
														</logic:notEqual>

														<td class="row-white">
														<div align="center"><bean:write name="ednQualList"
															property="noOfAttempts" /></div>
														</td>
														<td class="row-white">
														<div align="center"><logic:equal value="true"
															name="ednQualList" property="lastExam">
															<nested:write property="previousRegNo" />
														</logic:equal></div>
														</td>
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</nested:iterate>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;Parent
							Details</td>
						</tr>
						<tr>
							<td width="50%">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="883" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="175" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">
											<div align="right">Father's Name:</div>
											</div>
											</td>
											<td width="55%" height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.fatherName" /></span></td>
										</tr>
										<tr class="row-odd">
											<td width="50%" height="35" class="row-odd">
											<div align="right">Education:</div>
											</td>
											<td width="55%" height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm"
												property="objto.fatherEducation" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Income Currency:</div>
											</td>
											<td height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm"
												property="objto.fatherIncomeCurrency" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Income:</div>
											</td>
											<td height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.fatherIncome" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Occupation:</div>
											</td>
											<td height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm"
												property="objto.fatherOccupaction" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="17" class="row-odd">
											<div align="right">e-mail:</div>
											</td>
											<td height="35" class="row-even" align="left"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.fatheremail" /></span></td>
										</tr>
										<tr>
											<td height="4" class="row-odd">
											<div align="right">Phone/Mobile Number:</div>
											</td>
											<td height="35" align="left" class="row-even" align="left"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.fatherPhone" /></span></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" height="175"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
							<td width="50%">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="883" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="175" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">
											<div align="right">Mother's Name:</div>
											</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.motherName" /></span></td>
										</tr>
										<tr class="row-odd">
											<td width="50%" height="35" class="row-odd">
											<div align="right">Education:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm"
												property="objto.motherEducation" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Income Currency:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm"
												property="objto.motherincomeCurrency" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Income:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.motherIncome" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="35" class="row-odd">
											<div align="right">Occupation:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm"
												property="objto.motheroccupaction" /></span></td>
										</tr>
										<tr>
											<td width="50%" height="17" class="row-odd">
											<div align="right">e-mail:</div>
											</td>
											<td width="50%" height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.motherEmail" /></span></td>
										</tr>
										<tr>
											<td height="4" class="row-odd">
											<div align="right">Phone/Mobile Number:</div>
											</td>
											<td height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.motherPhone" /></span></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="175"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
					</tr>
		
		
		
	<tr>
	
		<td colspan="2" align="left"><span class="heading">&nbsp;SessionWise Attendance	Details </span></td>
	</tr>
	
	<tr>
	
			<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
				<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" height="91" valign="top">
						<table width="100%" cellspacing="1" cellpadding="2" style="border: 1px solid black;" rules="all">
							<tr>
							<td width="150" height="20" align="center" class="row-odd">Class</td>
							<td width="300" height="20" align="center" class="row-odd">Session</td>
							<td width="80" height="20" align="center" class="row-odd">Absent</td>
							<td width="80" height="20" class="row-odd"  align="center">Attendance%</td>
			
			
			
			
			
				</tr>
			<tr class="row-white">
				
				
			
					
				<td><bean:write property="className" name="DisciplinaryDetailsForm" /></td>
				<td><bean:write property="am" name="DisciplinaryDetailsForm" /></td>
				<td>
				
			<A	HREF="javascript:winOpenAm('<bean:write name="DisciplinaryDetailsForm" property="am" />');">
			<bean:write name="DisciplinaryDetailsForm" property="totamattabs" /></A>
			
			</td>
				<td><bean:write property="totamattper" name="DisciplinaryDetailsForm" /></td>
				
				
				
						
			</tr>
			<tr class="row-white">
				
				
			
					
				<td><bean:write property="className" name="DisciplinaryDetailsForm" /></td>
				<td><bean:write property="pm" name="DisciplinaryDetailsForm" /></td>
				<td>
				
			<A	HREF="javascript:winOpenPm('<bean:write name="DisciplinaryDetailsForm" property="pm" />');">
			<bean:write name="DisciplinaryDetailsForm" property="totpmattabs" /></A>
			
			</td>
				<td><bean:write property="totpmattper" name="DisciplinaryDetailsForm" /></td>
				
				
				
						
			</tr>
				</table>
				
				</td>
				
				<td background="images/right.gif" width="5" height="100"></td>
				
			</tr>

			<tr>
				<td height="5"><img src="images/04.gif" width="5"
					height="5"></td>
				<td background="images/05.gif"></td>
				<td><img src="images/06.gif"></td>
			</tr>

						
							</table>
							
							</td>
						
		</tr>	
					
	<tr>
	
		<td colspan="2" align="left"><span class="heading">&nbsp;Attendance	Details </span></td>
	</tr>
	<tr>
	
			<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
					</tr>
					<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" height="91" valign="top">
						<table width="100%" cellspacing="1" cellpadding="2" style="border: 1px solid black;" rules="all">
							<tr>
							<td width="150" height="20" align="center" class="row-odd">Class</td>
							<td width="300" height="20" align="center" class="row-odd">Subject</td>
							<td width="80" height="20" align="center" class="row-odd">Absent</td>
							<td width="80" height="20" class="row-odd"  align="center">Attendance%</td>
			<c:set var="found" value="0" />
			<%int examCount = 0; %>
			<logic:notEmpty name="DisciplinaryDetailsForm"	property="listCourseDetails">
			<logic:iterate id="markHead" name="DisciplinaryDetailsForm"
				property="listCourseDetails" indexId="count">
				<c:if test="${found == 0}">
					<logic:notEmpty name="markHead"
						property="examMarksEntryDetailsTOList">
						<logic:iterate id="markHead1" name="markHead"
							property="examMarksEntryDetailsTOList">
						<logic:notEmpty name="markHead1" property="examName">
							<td height="25" class="row-odd" width="10%">
							<table >
										
								<tr>
									<td>
									
									<div align="center"><bean:write name="markHead1"
										property="examName"/></div>
										
									<c:set var="found" value="1" /> <%examCount = examCount + 1; %>
									</td>
								</tr>
								<tr>
									<td width="50%" align="left">T</td>
									<td width="50%" align="right">P</td>
								</tr>
						
							</table>
							
							</td>
							</logic:notEmpty>
						</logic:iterate>
			
					</logic:notEmpty>
				</c:if>
			</logic:iterate>
			</logic:notEmpty>
				
			</tr>
			<tr>
				<logic:iterate id="details" name="DisciplinaryDetailsForm"
				type="com.kp.cms.to.admission.CourseDetailsTO"
				property="listCourseDetails" indexId="count">
				
			<tr class="row-white">
					
				<td><bean:write property="className" name="details" /></td>
				<td><bean:write property="subject" name="details" /></td>
				<td>
				<logic:equal name="details" property="flag" value="false">
			<A
							HREF="javascript:winOpen('<bean:write name="details" property="subjectID" />',
			'<bean:write name="details" property="studentId" />',
			'<bean:write name="details" property="absent" />','<bean:write name="details" property="cocurricularLeave" />');">
				<bean:write name="details" property="absent" /></A>
			</logic:equal>
			<logic:equal name="details" property="flag" value="true">
			<A HREF="javascript:activityOpens('<bean:write name="details" property="subjectID" />',
			'<bean:write name="details" property="studentId" />','<bean:write name="details" property="absent" />',
			'<bean:write name="details" property="subject" />','<bean:write name="details" property="cocurricularLeave" />');">
			<bean:write name="details" property="absent" /></A>
			</logic:equal>
			</td>
				<td><bean:write property="attendence" name="details" /></td>
				<logic:notEmpty name="details"
					property="examMarksEntryDetailsTOList">
					<logic:iterate id="mark" name="details"
						property="examMarksEntryDetailsTOList">
					<td height="25" >
						<table width="100%">
							<tr>
								<td width="50%" align="left"><bean:write
									name="mark" property="theoryMarks" /></td>
								<td width="50%" align="right"><bean:write
									name="mark" property="practicalMarks" /></td>
							</tr>
						 </table>
					  </td>
					</logic:iterate>
				</logic:notEmpty>
				<logic:empty name="details"	property="examMarksEntryDetailsTOList">
					<%if(examCount > 0){
						for(int i=1;i<=examCount; i++){%>
							<td width="10%" height="25" align="center">&nbsp;</td>
					<%} } %>
				</logic:empty>
			</logic:iterate>
						
					</tr>
				
				</table>
				
				</td>
				
				<td background="images/right.gif" width="5" height="175"></td>
				
			</tr>

			<tr>
				<td height="5"><img src="images/04.gif" width="5"
					height="5"></td>
				<td background="images/05.gif"></td>
				<td><img src="images/06.gif"></td>
			</tr>

						
							</table>
							
							</td>
						
					</tr>
					
<!-- Code By Mary for links of previous semesters -->
			
<tr>
<td colspan="3">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>	
	<td colspan="3"><logic:notEmpty name="SEMLIST" scope="session">
	<br>
	<logic:equal value="false" name="DisciplinaryDetailsForm" property="isCjc">
	<span class="heading"><br></br>&nbsp;Attendance and Internal Mark Details (Previous Semesters)</span>
	</logic:equal>
	
													
	
		<logic:iterate id="PreviousSem" name="SEMLIST" scope="session"	type="com.kp.cms.to.exam.ExamStudentPreviousClassTo">
			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<tr>					
                   			      <%int SemNo= PreviousSem.getSchemeNo();%>
									<td width="5%" height="21" align="left"><img src="images/bullet_img.gif" width="14" height="9" /></td>
									<td height="21" class="navmenu">
										<div class="<%= SemNo%>">
										<a href="#"	class="navmenu" onclick="getAttendance(<%= PreviousSem.getSchemeNo()%>,<%= PreviousSem.getClassId() %>); return false;">
																Class Name- <%= PreviousSem.getClassName() %> </a></div>
									</td>
				</tr>
			</table>
			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
									
				<tr>					
									<td height="21" colspan="3">
									<div id="attendance_s<%= PreviousSem.getSchemeNo() %>_<%= PreviousSem.getClassId() %>"></div>
									</td>
					</tr>
				</table>
	</logic:iterate>
</logic:notEmpty>
</td>

</tr>



										<!-- Code By Mary for links of previous semesters ends -->
										<!-- Code By Mary for Exam Marks starts -->

<logic:equal value="false" name="DisciplinaryDetailsForm" property="isCjc">

										<% //Integer RegularMarkFlag = (Integer)session.getAttribute("RegularMarkFlag");  %>

										<tr height="25">
										<td colspan="3">
											<logic:notEmpty name="DisciplinaryDetailsForm"
												property="marksCardTo">
												<tr>
													<td colspan="8" align="left"><span class="heading">&nbsp;Exam
													Marks Details </span></td>
												</tr>
												

													<tr height="25px">
														<td class="row-white" width="30%">Semester No :<bean:write
															name="DisciplinaryDetailsForm"
															property="marksCardTo.semNo" /></td>
														<td class="row-white" align="center">Exam Name :<bean:write
															name="DisciplinaryDetailsForm"
															property="marksCardTo.className" /></td>

													</tr>
                                                  <tr>
                                                  <td colspan="3">
													<table width="100%"  align="center" cellpadding="0"
								cellspacing="0" style="border: 1px solid black;" rules="all">
														<tr height="25px">

															<td class="row-odd" align="center" width="3%">SI NO
															</td>
															<td class="row-odd" align="center" width="30%">
															SUBJECT</td>
															<td class="row-odd" align="center" width="10%">TYPE
															</td>
															<td colspan="1" align="center" class="row-odd"
																width="12%">CIA + ATTENDANCE</td>
															<td colspan="1" align="center" class="row-odd"
																width="20%">ESE</td>
															<td colspan="1" align="center" class="row-odd"
																width="15%">TOTAL</td>
															<td class="row-odd" align="center" width="5%">
															CREDITS</td>
															<td  class="row-odd" align="center" width="5%">
						                                    GRADE </td>  
														</tr>

														<c:set var="slnocount" value="0" />
														<logic:notEmpty name="DisciplinaryDetailsForm"
															property="marksCardTo">

															<logic:notEmpty name="DisciplinaryDetailsForm"
																property="marksCardTo.subMap">
																<logic:iterate id="map" name="DisciplinaryDetailsForm"
																	property="marksCardTo.subMap">
																	<tr height="25px">
																		<td></td>
																		<td colspan="11" class="row-even"><bean:write
																			name="map" property="key" /></td>
																	</tr>
																	<logic:notEmpty name="map" property="value">
																		<logic:iterate id="to" name="map" property="value">
																			<c:set var="slnocount" value="${slnocount + 1 }" />
																			<c:choose>
																				<c:when test="${slnocount%2==0}">
																					<tr class="row-even">
																				</c:when>
																				<c:otherwise>
																					<tr class="row-white">
																				</c:otherwise>
																			</c:choose>
																			<tr height="25px" class="row-white">

																				<td align="center"><c:out value="${slnocount}" /></td>
																				<td align="center"><bean:write name="to"
																					property="name" /></td>
																				<td align="center"><bean:write name="to"
																					property="type" /></td>
																				<td align="center"><logic:equal value="-"
																					name="to" property="ciaMaxMarks">-</logic:equal><logic:notEqual
																					value="-" name="to" property="ciaMaxMarks">
																					<bean:write name="to" property="ciaMarksAwarded" />
																				</logic:notEqual> <logic:empty name="to" property="ciaMarksAwarded">- </logic:empty>
																				</td>
																				<td align="center"><logic:equal value="-"
																					name="to" property="eseMaxMarks">-</logic:equal><logic:notEqual
																					value="-" name="to" property="eseMaxMarks">
																					<bean:write name="to" property="eseMarksAwarded" />
																				</logic:notEqual> <logic:empty name="to" property="eseMarksAwarded">- </logic:empty></td>
																				<td align="center"><logic:equal value="-"
																					name="to" property="totalMaxMarks">-</logic:equal>
																				<logic:notEqual value="-" name="to"
																					property="totalMaxMarks">
																					<bean:write name="to" property="totalMarksAwarded" />
																				</logic:notEqual> <logic:empty name="to" property="totalMarksAwarded">- </logic:empty></td>
																				<td align="center"><logic:equal value="0"
																					name="to" property="credits">-</logic:equal> <logic:notEqual
																					value="00" name="to" property="credits">
																					<bean:write name="to" property="credits" />
																				</logic:notEqual> <logic:empty name="to" property="credits">- </logic:empty></td>
																			<td align="center"><logic:equal value="0" name="to" property="grade">-</logic:equal>
																				<logic:notEqual	value="00" name="to" property="grade"><bean:write name="to" property="grade" /></logic:notEqual> 
																				<logic:empty name="to" property="grade">- </logic:empty></td>
																				
																			</tr>
																		</logic:iterate>
																	</logic:notEmpty>
																</logic:iterate>
															</logic:notEmpty>
															<c:set var="slnocount" value="0" />
															<logic:notEmpty name="DisciplinaryDetailsForm"
																property="marksCardTo">
																<logic:notEmpty name="DisciplinaryDetailsForm"
																	property="marksCardTo.addOnCoursesubMap">
																	<logic:iterate id="map" name="DisciplinaryDetailsForm"
																		property="marksCardTo.addOnCoursesubMap">
																		<tr height="25px">
																			<td></td>
																			<td colspan="11" class="row-even"><bean:write
																				name="map" property="key" /></td>
																		</tr>
																		<logic:notEmpty name="map" property="value">
																			<logic:iterate id="to" name="map" property="value">
																				<c:set var="slnocount" value="${slnocount + 1 }" />
																				<c:choose>
																					<c:when test="${slnocount%2==0}">
																						<tr class="row-even">
																					</c:when>
																					<c:otherwise>
																						<tr class="row-white">
																					</c:otherwise>
																				</c:choose>


																				<tr height="25px" class="row-white">
																					<td align="center"><c:out value="${slnocount}" /></td>
																					<td align="center"><bean:write name="to"
																						property="name" /></td>
																					<td align="center"><bean:write name="to"
																						property="type" /></td>

																					<td align="center"><logic:equal value="-"
																						name="to" property="ciaMaxMarks">-</logic:equal><logic:notEqual
																						value="-" name="to" property="ciaMaxMarks">
																						<bean:write name="to" property="ciaMarksAwarded" />
																					</logic:notEqual> <logic:empty name="to" property="ciaMarksAwarded">- </logic:empty>
																					</td>

																					<td align="center"><logic:equal value="-"
																						name="to" property="eseMaxMarks">-</logic:equal><logic:notEqual
																						value="-" name="to" property="eseMaxMarks">
																						<bean:write name="to" property="eseMarksAwarded" />
																					</logic:notEqual> <logic:empty name="to" property="eseMarksAwarded">- </logic:empty></td>

																					<td align="center"><logic:equal value="-"
																						name="to" property="totalMaxMarks">-</logic:equal>
																					<logic:notEqual value="-" name="to"
																						property="totalMaxMarks">
																						<bean:write name="to" property="totalMarksAwarded" />
																					</logic:notEqual> <logic:empty name="to"
																						property="totalMarksAwarded">- </logic:empty></td>
																					<td align="center"><logic:equal value="0"
																						name="to" property="credits">-</logic:equal> <logic:notEqual
																						value="00" name="to" property="credits">
																						<bean:write name="to" property="credits" />
																					</logic:notEqual> <logic:empty name="to" property="credits">- </logic:empty></td>
																					 <td  align="center"> 
																					 <logic:equal value="0"
																						name="to" property="grade">-</logic:equal> <logic:notEqual
																						value="" name="to" property="grade">
																					 <bean:write name="to" property="grade"/></logic:notEqual>
																					 <logic:empty name="to" property="grade">-</logic:empty>
																					  </td>
																				</tr>
																			</logic:iterate>
																		</logic:notEmpty>
																	</logic:iterate>
																</logic:notEmpty>
															</logic:notEmpty>
												<tr class="row-white">
												<td align="center" colspan="5" height="30"><b>Result</b></td>
												 <td  align="center" colspan="3"> <b>
													<logic:equal value="" name="DisciplinaryDetailsForm" property="marksCardTo.result">-</logic:equal>
													<logic:notEqual value="" name="DisciplinaryDetailsForm" property="marksCardTo.result">
													<bean:write name="DisciplinaryDetailsForm" property="marksCardTo.result"/></logic:notEqual>
													<logic:empty name="DisciplinaryDetailsForm" property="marksCardTo.result">-</logic:empty>
													 </b></td>
												</tr>
														</logic:notEmpty>
													</table>
													</td>
                                               </tr>
												
											</logic:notEmpty>
											</td>
										</tr>

										<tr>
											<td colspan="3">
											<table width="100%" border="0" align="center" cellpadding="0"
												cellspacing="0">
												<logic:notEmpty name="EXAMSEMLIST" scope="session">
													<tr>
													<td>&nbsp;</td>
													<td align="left"><span class="heading">&nbsp;Exam Mark Details (Previous semesters)</span></td>
													</tr>
													
													<logic:iterate id="marksCardSemExamList" name="EXAMSEMLIST"
														scope="session" type="com.kp.cms.to.exam.MarksCardTO">

														<tr >
														
															
															<%int SemNo= 0;
																if(marksCardSemExamList.getSemNo() != null){
																	SemNo = Integer.parseInt(marksCardSemExamList.getSemNo());
																}
															%>
															<%int semNo= 0;%>
														<logic:notEmpty name="MARKSCARDTO" scope="session">
                   										<logic:iterate id="marksCardTo" name="MARKSCARDTO" scope="session" type="com.kp.cms.to.exam.MarksCardTO">
															<%
															if(marksCardTo!= null){
																if(marksCardTo.getSemNo() != null){
																	semNo = Integer.parseInt(marksCardTo.getSemNo());
																}
															}
															%>
															</logic:iterate>
 													  </logic:notEmpty>
															<%if(semNo==0){%>
															<td width="5%" height="21" align="left"><img src="images/bullet_img.gif" width="14" height="9" /></td>
															<td width="30%" height="21" class="navmenu">
															<div class="<%= SemNo%>">
															<a href="#"	class="navmenu" onclick="getRegularMarks(<%= SemNo %>); return false;">Sem No- <%= SemNo %> Regular-<%=marksCardSemExamList.getExamName() %>
															</a></div>
															</td>
															<td>
															<div id="notValid_<%=SemNo %>" style="color: red"><FONT color="red"></FONT></div>
															</td>
															<%}else { %>
															<%if(semNo != SemNo) {%>
															<td width="5%" height="21" align="left"><img src="images/bullet_img.gif" width="14" height="9" /></td>
															<td width="30%" height="21" class="navmenu">
															<div class="<%= SemNo%>">
															<a href="#"	class="navmenu" onclick="getRegularMarks(<%= SemNo %>); return false;">Sem No- <%= SemNo %> Regular-<%=marksCardSemExamList.getExamName() %>
															</a></div>
															</td>
															<td>
															<div id="notValid_<%=SemNo %>" style="color: red"><FONT color="red"></FONT></div>
															</td>
															<%} }%>
															</tr>
															<tr>
															<td colspan="3" height="21">
															<div id="marks_<%= SemNo %>"></div>
															</td>
														
															


														</tr>
														

													</logic:iterate>
												</logic:notEmpty>
											</table>
											</td>
										</tr>
									

										<!-- Marks card ends -->

										<!-- Supplementary Marks card -->
				<tr>
					<logic:notEmpty name="DisciplinaryDetailsForm"	property="marksCardToSup">

					<tr>
						<td colspan="2" align="left"><span class="heading">&nbsp;Supplementary Exam Marks Details </span></td>

					</tr>							
					</logic:notEmpty>
				</tr>
				<tr>
					<td colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

							<logic:notEmpty name="SUPEXAMSEMLIST" scope="session">
								<logic:iterate id="SupMCSemExamList" name="SUPEXAMSEMLIST"	scope="session" type="com.kp.cms.to.exam.MarksCardTO">
								<tr>
								   <%int SemNo= Integer.parseInt(SupMCSemExamList.getSemNo());%>
									<!-- <td width="35" height="21" align="center"><img src="images/bullet_img.gif" width="14" height="9" /></td>-->
									<td width="5%" height="21" align="left"><img src="images/bullet_img.gif" width="14" height="9" /></td>
									<td width="50%" height="21" class="navmenu">
									<div class="<%= SemNo%>">
									<a href="#"	class="navmenu" style="cursor:pointer"  onclick="getSupplementaryMarks(<%= SupMCSemExamList.getSemNo()%>,<%=SupMCSemExamList.getExamId() %>); return false;">Sem No-  <%= SupMCSemExamList.getSemNo() %> -Supplementary <%=SupMCSemExamList.getExamName() %> </a></div></td>
									<td><div id="notValid" style="color: red"><FONT color="red"></FONT></div></td>
									</tr>
									<tr>
									<td colspan="3" height="21">
									<div id="marks_s<%= SupMCSemExamList.getExamId() %>_<%= SupMCSemExamList.getSemNo() %>"></div>
									</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</table>						
	               </td>
       </tr>


</logic:equal>

				<logic:equal value="yes" name="DisciplinaryDetailsForm" property="checkFinalYear">
						<tr>
							<td colspan="2" align="left"><span class="heading">&nbsp;Course Completion Details</span></td>
						</tr>
						<tr>
							<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="91" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="10%" height="25" align="center" class="row-odd"> Status</td>
											<td width="10%" height="25" align="center" class="row-odd">Last exam name </td>
											<td width="10%" height="25" align="center" class="row-odd">Month/Year of Exam</td>
										</tr>
										<tr>
											<td width="18%" height="25" align="center" class="row-even"><bean:write name="DisciplinaryDetailsForm"
												property="status" /></td>
											<td width="18%" height="25" align="center" class="row-even"><bean:write name="DisciplinaryDetailsForm"
												property="lastExamName" /></td>
											<td width="18%" height="25" align="center" class="row-even"><bean:write name="DisciplinaryDetailsForm"
												property="examMonthYear" /></td>
										</tr>
										
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
				</logic:equal>

						<tr>
							<td colspan="2" align="left"><span class="heading">&nbsp;Detention/Discontinue
							Details </span></td>
						</tr>


						<tr>
							<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="91" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="10%" height="25" align="center" class="row-odd"></td>
											<td width="18%" height="25" align="center" class="row-odd">Old Register No.</td>
											<td width="18%" height="25" align="center" class="row-odd">Detention</td>
											<td width="18%" height="25" align="center" class="row-odd">Discontinued</td>
											<td width="18%" height="25" align="center" class="row-odd">Reason</td>
											<td width="18%" height="25" align="center" class="row-odd">Rejoin</td>
										</tr>
										<tr>
											<td width="10%" height="25" align="center" class="row-odd">Date</td>
											<td width="18%" height="25" align="center" class="row-even">
											<bean:write name="DisciplinaryDetailsForm"
												property="oldRegNo" /></td>
											<td width="18%" height="25" align="center" class="row-even">
											<bean:write name="DisciplinaryDetailsForm"
												property="detentionDate" /></td>
											<td width="18%" height="25" align="center" class="row-even">
											<bean:write name="DisciplinaryDetailsForm"
												property="discontinuedDate" /></td>
											<td width="18%" height="25" align="center" class="row-even">
											<bean:write name="DisciplinaryDetailsForm"
												property="detentionReason" /></td>
											<td width="18%" height="25" align="center" class="row-even">
											<bean:write name="DisciplinaryDetailsForm"
												property="rejoinDate" /></td>
											
										</tr>
										
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<%--  <tr>
                                <td background="images/right.gif" width="5" height="5"></td>
                              </tr>--%>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>

						<tr>
							<td colspan="2" align="left"><span class="heading">&nbsp;Fees</span></td>
						</tr>
						<tr>
							<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="91" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="5%" height="25" align="center" class="row-odd">Scheme No.</td>
											<td width="15%" align="center" class="row-odd">Total Fees</td>
											<td width="15%" align="center" class="row-odd">Fees Paid</td>
											<td width="15%" align="center" class="row-odd">Concession Given</td>
											<td width="15%" align="center" class="row-odd">Installment Given</td>
											<td width="15%" align="center" class="row-odd">Installment Paid</td>
											<td width="15%" align="center" class="row-odd">Balance Amount</td>
										</tr>
										<logic:iterate id="details" name="DisciplinaryDetailsForm"
											type="com.kp.cms.to.admission.FeesTO" property="listFees"
											indexId="count">
											<c:choose>
												<c:when test="${count%2==0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td align="center"><bean:write property="schmeNo" name="details" /></td>
											<td align="center"><bean:write property="totalFee" name="details" /></td>
											<td align="center"><bean:write property="feesPaid" name="details" /></td>
											<td align="center"><bean:write property="concessionGiven" name="details" /></td>
											<td align="center"><bean:write property="installmentGiven" name="details" /></td>
											<td align="center"><bean:write property="installmentPaid" name="details" /></td>
											<td align="center"><bean:write property="balanceAmount" name="details" /></td>


										</logic:iterate>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<%-- <tr>
                                <td background="images/right.gif" width="5" height="5"></td>
                              </tr>--%>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>

						<c:if
							test="${DisciplinaryDetailsForm.recommendedBy!= null && DisciplinaryDetailsForm.recommendedBy!= ''}">
							<tr>
								<td colspan="2">
								<table width="100%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td><img src="images/01.gif" width="5" height="5"></td>
										<td width="914" background="images/02.gif"></td>
										<td><img src="images/03.gif" width="5" height="5"></td>
									</tr>
									<tr>
										<td width="5" background="images/left.gif"></td>
										<td width="100%" align="left" valign="top">
										<table width="100%" height="92" cellpadding="2"
											cellspacing="1">
											<tr>
												<td width="15%" height="25" align="center" class="row-odd">
												<div align="right">Recommended By :</div>
												</td>
												<td width="15%" height="25" align="center" class="row-even">
												<div align="left"><bean:write
													name="DisciplinaryDetailsForm" property="recommendedBy" />
												</div>
												</td>
											</tr>

										</table>
										</td>
										<td background="images/right.gif" width="5" height="5"></td>
									</tr>
									<tr>
										<td height="5"><img src="images/04.gif" width="5"
											height="5"></td>
										<td background="images/05.gif"></td>
										<td><img src="images/06.gif"></td>
									</tr>
								</table>
								</td>
							</tr>
						</c:if>

						<tr>
							<td colspan="2" align="left"><span class="heading">&nbsp;Remarks</span></td>
						</tr>
						<tr>
							<td colspan="2">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" height="91" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="30%" height="25" align="center" class="row-odd">Date</td>
											<td width="30%" align="center" class="row-odd">Details</td>
											<td width="30%" align="center" class="row-odd">Entered By</td>
											<td width="10%" align="center" class="row-odd">Edit</td>
										</tr>
										<logic:iterate id="details" name="DisciplinaryDetailsForm"
											type="com.kp.cms.to.admission.RemarcksTO"
											property="listRemarcks" indexId="count">
											<c:choose>
												<c:when test="${count%2==0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td width="130"><bean:write property="date"
												name="details" /></td>
											<td width="160"><bean:write property="details"
												name="details" /></td>
											<td width="110"><bean:write property="enteredBy"
												name="details" /></td>
											<td width="10" align="center">
											<img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer" 
												onclick="editRemarks('<bean:write name="details" property="id"/>')"></img></td>
										</logic:iterate>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
					</tr>
					<logic:equal value="yes"  name="DisciplinaryDetailsForm" property="checkHostelAdmission">
						<tr height="35px">
							<td width="100%" >
								<a href="#"	class="navmenu" onclick="getHostelDetails('<bean:write name="DisciplinaryDetailsForm" property="studentId"/>'); return false;"><b>Hostel Details</b> </a>
							</td>
						</tr>
						<tr>
							<td width="100%" id="hDetails" colspan="6"></td>
						</tr>
					</logic:equal>
					
 					<tr>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="20%" height="45" align="right">
									<div><c:if
										test="${DisciplinaryDetailsForm.isAddRemarks=='1'}">
										<input name="Submit" type="submit" class="formbutton"
											value="Add Remarks">
									</c:if></div>
									</td>
									<td width="1%"></td>
									<td width="25%" height="45" align="left"><input
										type="button" name="" value="Cancel" onClick="cancelButton()"
										class="formbutton"></td>
								</tr>
							</table>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					</table>
				
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
</div>
			</td>
		</tr>


	</table>

</td>
</tr>
</table>

</html:form>
<script type="text/javascript">

</script>