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

function closeCjcTable(s,e){
	document.getElementById("marks_s"+e+"_"+s).innerHTML = "";
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


var s=0;
var e=0;
function getCjcMarksCard(classId, examId){
s=classId;
e=examId;
	var url = "disciplinaryDetails.do";
	var args = "method=getStudentCjcMarksCard&ClassId="+classId+"&examId="+examId;
	requestOperationProgram(url, args, displayCjcMarks);
}

function displayCjcMarks(req)
{
	var responseObj = req.responseXML.documentElement;
	var fields = responseObj.getElementsByTagName("fields");
	
	var htm="<table width='100%' style='border: 1px solid black;'	rules='all'>  <tr> <tr height='30px' class='row-odd'>";
	htm=htm+"<td width='5%' align='center'>Sl No</td>"+"<td width='30%' align='center'>Subject</td>"+"<td width='20%' align='center'>Theory</td>"+"<td width='20%' align='center'>Practical</td>"+"<td width='20%' align='center'>Total</td></tr>";
	if(fields!=null){
		var slno=1;
		for ( var i = 0; i < fields.length; i++) {
			if(fields[i].firstChild!=null && fields[i].firstChild!='' && fields[i].firstChild.length!=0){
				htm=htm+"<tr height='25px'><td></td><td  class='row-even' colspan='6'>";
				if(fields[i]!=null){
					 var value = fields[i].getElementsByTagName("value")[0].firstChild.nodeValue;
					 htm=htm+value+ "</td></tr> ";
					 var totalMark=fields[i].getElementsByTagName("totalMark");
					 if(totalMark!=null){
						 
						 for ( var j = 0; j < totalMark.length; j++) {
							 htm=htm+"<tr class='row-white' height='25px'> ";
							 if(totalMark[j]!=null){
							 var siNo=slno;
							 slno=slno+1;
							 var subject=totalMark[j].getElementsByTagName("subject")[0].firstChild.nodeValue;
							 var theory=totalMark[j].getElementsByTagName("theory")[0].firstChild.nodeValue;
							 var practical=totalMark[j].getElementsByTagName("practical")[0].firstChild.nodeValue;
							 var total=totalMark[j].getElementsByTagName("total")[0].firstChild.nodeValue;
							 htm=htm+"<td align='center'>"+siNo+ "</td>"+"<td align='left'>&nbsp&nbsp " + subject+ "</td>"+"<td align='center'>"+theory+ "</td>"+"<td align='center'>"+practical+ "</td>"+"<td align='center'>"+total+ "</td>";
							 }
							 htm=htm+"</tr>";
						 } 
					 }
				}
			}
		}
	}
	htm=htm+"<tr> <td colspan='6' align='center'> <input type='button' value='Hide Table' onclick='closeCjcTable("+s+","+e+")'/> </td></tr>";
	htm=htm+"</table>";
	document.getElementById("marks_s"+e+"_"+s).innerHTML = htm;
}
function editRemarks(id){
	document.location.href = "disciplinaryDetails.do?method=editRemarks&remarkid="+ id;
}

</script>


<html:form action="/disciplinaryDetails.do">
	<html:hidden property="method" styleId="method" value="getRemarksPage" />
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
												class="style16">&nbsp; <span class="style27"><bean:write
												name="DisciplinaryDetailsForm" property="objto.regRollNo" /></span></span></td>
											<td class="row-even">
											<table>
												<tr>
													<td height="20" class="row-even" colspan="2">
													<div>Classes Attended = <bean:write
														name="DisciplinaryDetailsForm" property="totalPresent" />
													&nbsp; &nbsp; Total Held = <bean:write
														name="DisciplinaryDetailsForm" property="totalConducted" />&nbsp;&nbsp;
													Percentage= <b><bean:write name="DisciplinaryDetailsForm"
														property="totalAggrPer" /></b></div>
													</td>
												</tr>
												<tr>
													<td class="row-odd" colspan="2">*Class Attended
													includes Co-curricular leave*</td>
												</tr>
											</table>
											</td>
										</tr>
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
											<div align="right">Mobile Number :</div>
											</td>
											<td height="35" align="left" class="row-even"><span
												class="style26"><bean:write
												name="DisciplinaryDetailsForm" property="objto.mobNumber" /></span></td>
										</tr>
										<tr>
											<td height="4" class="row-odd">
											<div align="right">Phone Number :</div>
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
											<div align="left"><bean:message
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
														<td width="22%" class="row-even">
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
											<div align="right">Phone/Mobile Number :</div>
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
											<div align="right">Phone/Mobile Number :</div>
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
<logic:equal value="true" name="DisciplinaryDetailsForm" property="isCjc">

			<tr><td>&nbsp;</td></tr>
	<tr>
	
		<td colspan="2" align="left"><span class="heading">&nbsp;Activity Attendance </span></td>
	</tr>
			<tr>
				<td><img src="images/01.gif" width="5" height="5"></td>
				<td width="914" background="images/02.gif"></td>
				<td><img src="images/03.gif" width="5" height="5"></td>
	</tr>
	
	<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" valign="top">
						<table width="100%" cellspacing="1" cellpadding="0" style="border: 1px solid black;" rules="all">
							<tr>
										
							<td width="150" height="20" align="center" class="row-odd">Class</td>
							<td width="300" height="20" align="center" class="row-odd">Subject</td>
							<td width="80" height="20" align="center" class="row-odd">Absent</td>
							<td width="80" height="20" class="row-odd"  align="center">Attendance%</td>
							<c:set var="found1" value="0" />
								<%int AdditionalexamCount = 0; %>
								<logic:notEmpty name="DisciplinaryDetailsForm"	property="listActivityDetails">
								<logic:iterate id="markHead" name="DisciplinaryDetailsForm"	property="listActivityDetails" indexId="count">
									<c:if test="${found1 == 0}">
									<logic:notEmpty name="markHead"	property="examMarksEntryDetailsTOList">
										<logic:iterate id="markHead1" name="markHead" property="examMarksEntryDetailsTOList">
											<logic:notEmpty name="markHead1" property="examName">
												<td height="25" class="row-odd" >
													<table width="100%">
														<tr>
															<td>
																<div align="center"><bean:write name="markHead1" property="examName"/></div>
																<c:set var="found1" value="1" /> <%AdditionalexamCount = AdditionalexamCount + 1; %>
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
							<logic:iterate id="details" name="DisciplinaryDetailsForm"	type="com.kp.cms.to.admission.CourseDetailsTO"
												property="listActivityDetails" indexId="count">
												<c:choose>
													<c:when test="${count%2==0}">
														<tr class="row-even">
													</c:when>
													<c:otherwise>
														<tr class="row-white">
													</c:otherwise>
												</c:choose>
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

					<td>
						<table >
							<tr>
													
								<logic:notEmpty name="details" property="examMarksEntryDetailsTOList">
									<logic:iterate id="mark" name="details"	property="examMarksEntryDetailsTOList">
										<logic:notEmpty name="mark"	property="theoryMarks">	
													<td width="50%" align="left"><bean:write name="mark" property="theoryMarks" /></td>
													
										</logic:notEmpty>
									</logic:iterate>
							</logic:notEmpty>	
							<logic:notEmpty name="details" property="examMarksEntryDetailsTOList">
									<logic:iterate id="mark" name="details"	property="examMarksEntryDetailsTOList">
										<logic:notEmpty name="mark"	property="practicalMarks">	
													<td width="50%" align="right"><bean:write name="mark" property="practicalMarks" /></td>
										</logic:notEmpty>
									</logic:iterate>
							</logic:notEmpty>		
						 </tr>																
					 </table>
				 </td>
													
			  	<logic:empty name="details"	property="examMarksEntryDetailsTOList">
					<%if(AdditionalexamCount > 0){
						for(int i=1;i<=AdditionalexamCount; i++){%>
						<td width="10%" align="center">&nbsp;</td>
						<%} } %>
				</logic:empty>
		</logic:iterate>
</tr>
		</table>
				</td>
				<td background="images/right.gif" width="5"></td>
		</tr>
				<tr>
					<td ><img src="images/04.gif" width="5"></td>
					<td background="images/05.gif"></td>
					<td><img src="images/06.gif"></td>
				</tr>
	</logic:equal>
						
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
	
	<logic:equal value="true" name="DisciplinaryDetailsForm" property="isCjc">
	<span class="heading"><br></br>&nbsp;Attendance and Internal Mark Details (Previous Class)</span>
	</logic:equal>
													
	
		<logic:iterate id="PreviousSem" name="SEMLIST" scope="session"	type="com.kp.cms.to.exam.ExamStudentPreviousClassTo">
			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<tr>					
                   			      <%int SemNo= PreviousSem.getSchemeNo();%>
									<td width="5%" height="21" align="left"><img src="images/bullet_img.gif" width="14" height="9" /></td>
									<td height="21" class="navmenu">
									<div class="<%= SemNo%>">
									<a href="#"	class="navmenu" onclick="getAttendance(<%= PreviousSem.getSchemeNo()%>,<%= PreviousSem.getClassId() %>); return false;">
															Class Name- <%= PreviousSem.getClassName() %> </a></div></td>
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



									



<!-- .................................................CJC MARKS CARD Data........................................................................... -->
	

	
	
	
						
<logic:equal value="true" name="DisciplinaryDetailsForm" property="isCjc">
								<% //Integer RegularMarkFlag = (Integer)session.getAttribute("RegularMarkFlag");  %>
			<tr>
													<td colspan="8" align="left"><span class="heading">&nbsp;Exam
													Marks Details </span></td>
												</tr>
	<tr>
					<td colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

							<logic:notEmpty name="CJCEXAMSEMLIST" scope="session">
								<logic:iterate id="CjcSemExamListTo" name="CJCEXAMSEMLIST"	scope="session" type="com.kp.cms.to.exam.MarksCardTO">
								<tr>
								   <%int ClassId= (CjcSemExamListTo.getClassesId());%>
									<td width="5%" height="21" align="left"><img src="images/bullet_img.gif" width="14" height="9" /></td>
									<td width="137" height="21" class="navmenu">
									<div class="<%= ClassId%>">
									<a href="#"	class="navmenu" style="cursor:pointer"  onclick="getCjcMarksCard(<%= CjcSemExamListTo.getClassesId()%>,<%=CjcSemExamListTo.getExamId() %>); return false;">Class :-  <%= CjcSemExamListTo.getClassName() %> Exam :- <%=CjcSemExamListTo.getExamName() %> </a></div></td>
									</tr>
									<tr>
									<td colspan="3" height="21">
									<div id="marks_s<%= CjcSemExamListTo.getExamId() %>_<%= CjcSemExamListTo.getClassesId() %>"></div>
									</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</table>						
	               </td>
       </tr>
  </logic:equal>
<!------------------------------------------------------------------------------------------------------------------------------------------------------>


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
											<td width="34%" height="25" align="center" class="row-odd">Scheme
											No.</td>
											<td width="33%" align="center" class="row-odd">Fees Paid
											</td>
											<td width="33%" align="center" class="row-odd">Pending
											Fee</td>
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
											<td><bean:write property="schmeNo" name="details" /></td>
											<td><bean:write property="feesPaid" name="details" /></td>
											<td><bean:write property="feesPending" name="details" /></td>


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