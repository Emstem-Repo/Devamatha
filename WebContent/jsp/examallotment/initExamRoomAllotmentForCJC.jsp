<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
   <script type="text/javascript" src="js/auditorium/jquery-1.9.1.min.js"></script>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>   
    
<script type="text/javascript">
	function isDateWiseOrNot(value){
		if(value =='Yes'){
			document.getElementById("dateWise").style.display="block";
			document.getElementById("cycleWise").style.display="none";
			resetOption("classId");
			resetOption("classId1");
			document.getElementById("cycleId").value="";
			document.getElementById("noOfStudents").value="";
			document.getElementById("fromRegNo").value="";
			document.getElementById("toRegNo").value="";
			document.getElementById("noOfStudents1").value="";
			document.getElementById("fromRegNo1").value="";
			document.getElementById("toRegNo1").value="";
		}else if(value == 'No'){
			document.getElementById("cycleWise").style.display="block";
			document.getElementById("dateWise").style.display="none";
			resetOption("subjectId");
			resetOption("subjectId1");
			document.getElementById("allottedDate").value="";
			document.getElementById("sessionId").value="";
			document.getElementById("noOfStudents2").value="";
			document.getElementById("fromRegNo2").value="";
			document.getElementById("toRegNo2").value="";
			document.getElementById("noOfStudents3").value="";
			document.getElementById("fromRegNo3").value="";
			document.getElementById("toRegNo3").value="";
		}
	}
	function getExamName() {
		var year = document.getElementById("academicYear").value;
		var examType1 =  document.getElementById("examType").value;
		var examType ;
		if(examType1 == 'M'){
			examType = "Int";
		}else if(examType1 == 'E'){
			examType = "Reg";
		}
		getExamNameByExamTypeYearWise("examMap1", examType,year, "examid", updateExamName);
		resetOption("subjectId");
		resetOption("classId");
		resetOption("classId1");
		resetOption("subjectId1");
		document.getElementById("cycleId").value="";
		document.getElementById("noOfStudents").value="";
		document.getElementById("fromRegNo").value="";
		document.getElementById("toRegNo").value="";
		document.getElementById("noOfStudents1").value="";
		document.getElementById("fromRegNo1").value="";
		document.getElementById("toRegNo1").value="";
		document.getElementById("allottedDate").value="";
		document.getElementById("sessionId").value="";
		document.getElementById("noOfStudents2").value="";
		document.getElementById("fromRegNo2").value="";
		document.getElementById("toRegNo2").value="";
		document.getElementById("noOfStudents3").value="";
		document.getElementById("fromRegNo3").value="";
		document.getElementById("toRegNo3").value="";
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examid", "-- Select --");
		updateCurrentExam(req, "examid");
		var examType =  document.getElementById("examType").value;
		getCyclesByExamType(examType);
		<%--if(document.getElementById("examid").value != null && document.getElementById("examid").value !=""){
			getSubjectNameAndCodeForExam();
		}--%>
	}
	function getSubjectNameAndCodeForExam(){
		var examId = document.getElementById("examid").value;
		var examType1 =  document.getElementById("examType").value;
		var examType = "";
		if(examType1 == 'M'){
			examType = "Int";
		}else if(examType1 == 'E'){
			examType = "Reg";
		}
		var subCode="sName";
		getSubjectListForExam(examId,examType,subCode,"subjectId",updateToSubject);
		getClasses();
		var examType =  document.getElementById("examType").value;
		getCyclesByExamType(examType);
	}
	function updateToSubject(req) {
		updateOptionsFromMap(req, "subjectId", "-- Select --");
	}
	function getClasses() {
		var examName = document.getElementById("examid").value;
		var examType =0;
		var programId =0;
		var deanaryId =0;
		var args ="method=getClassMap&examName=" + examName+"&examType="+examType+"&programId="+programId+"&deanaryName="+deanaryId;
		var url = "AjaxRequest.do";
		requestOperationProgram(url, args, updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "classId", "-- Select --");
		updateOptionsFromMap(req, "classId1", "-- Select --");
	}
	function getRoomNoByWorkLocation(workLocationId){
		resetOption("roomId");
		var args =  "method=getRoomNoByWorkLocationId&campusName="+workLocationId;
		var url = "roomAllotmentStatus.do";
		requestOperationProgram(url, args, updateRoomMap);
	}
	function updateRoomMap(req) {
		updateOptionsFromMap(req, "roomId", "--Select--");
	}
	function getCyclesByExamType(midOrEndSem) {
		args =  "method=getCyclesByExamType&midEndSem="+midOrEndSem;
		var url = "roomAllotmentStatus.do";
		requestOperationProgram(url, args, updateCycleMap);
	}
	function updateCycleMap(req){
		updateOptionsFromMap(req, "cycleId", "-- Select --");
	}
	function getRoomDetails(){
		
		var examType1 =  document.getElementById("examType").value;
		var examName = document.getElementById("examid").value;
		var roomId = document.getElementById("roomId").value;
		var year = document.getElementById("academicYear").value;
		var campusId = document.getElementById("campusId").value;
		var msg="";
		var examType
		if(examType1 == 'M'){
			examType = "Int";
		}else if(examType1 == 'E'){
			examType = "Reg";
		}
		if(year==''){
			msg= msg + "Academic Year is Required"+"<br/>";
		} 
		if(examType1==''){
			msg= msg + "Exam Type is Required"+"<br/>";
		}
		if(examName==''){
			msg= msg + "Exam is Required"+"<br/>";
		}
		 if(roomId==''){
			msg= msg + "Room is Required"+"<br/>";
		}
		if(campusId==''){
			msg= msg + "Campus is Required"+"<br/>";
		}
		var dateWiseOrNot; 
		if(document.getElementById("isDateWiseNo").checked){
			var cycleId = document.getElementById("cycleId").value;
			if( cycleId ==''){
				msg=msg+"Cycle is Required"+"<br/>";
			}
			dateWiseOrNot="No";
			if(msg == ''){
				document.location.href = "examRoomAllotmentCjc.do?method=getRoomDetailsForAllotment&propertyName=Cycle&examType="+examType+"&examid="+examName+"&campusId="+campusId+"&roomId="+roomId+"&academicYear="+year+"&cycleId="+cycleId+"&isDateWise="+dateWiseOrNot;
			}else{
				document.getElementById("errorMessages").style.display="block";
				document.getElementById("errorMessages").innerHTML=msg;
			}
		}else if(document.getElementById("isDateWiseYes").checked){
			var allottedDate = document.getElementById("allottedDate").value;
			var sessionId = document.getElementById("sessionId").value;
			if(allottedDate == ''){
				msg=msg+"Date is Required"+"<br/>";
			}else if(sessionId == ''){
				msg=msg+"Session is Required"+"<br/>";
			}
			dateWiseOrNot="Yes";
			if(msg == ''){
				document.location.href = "examRoomAllotmentCjc.do?method=getRoomDetailsForAllotment&propertyName=DateSession&examType="+examType+"&examid="+examName+"&campusId="+campusId+"&roomId="+roomId+"&academicYear="+year+"&allottedDate="+allottedDate+"&sessionId="+sessionId+"&isDateWise="+dateWiseOrNot;
			}else {
				document.getElementById("errorMessages").style.display="block";
				document.getElementById("errorMessages").innerHTML=msg;
			}
		}
	}
	function cancelStudenRoomAllotment(){
		document.location.href = "LoginAction.do?method=loginAction";
	}
</script>
<style type="text/css">
.CSSTableGenerator {
	margin:0px;padding:0px;
	width:100%;
	box-shadow: 10px 10px 5px #888888;
	border:1px solid #c6c652;
	
	-moz-border-radius-bottomleft:0px;
	-webkit-border-bottom-left-radius:0px;
	border-bottom-left-radius:0px;
	
	-moz-border-radius-bottomright:0px;
	-webkit-border-bottom-right-radius:0px;
	border-bottom-right-radius:0px;
	
	-moz-border-radius-topright:0px;
	-webkit-border-top-right-radius:0px;
	border-top-right-radius:0px;
	
	-moz-border-radius-topleft:0px;
	-webkit-border-top-left-radius:0px;
	border-top-left-radius:0px;
}.CSSTableGenerator table{
	width:100%;
	height:100%;
	margin:0px;padding:0px;
}
.CSSTableGenerator td{
	vertical-align:middle;
	border:1px solid #c6c652;
	border-width:0px 1px 1px 0px;
	text-align:left;
	padding:7px;
	font-size:10px;
	font-family:Arial;
	font-weight:normal;
	color:#000000;
}
.CSSTableGenerator tr:first-child td{
		background:-o-linear-gradient(bottom, #acac39 5%, #c6c652 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #acac39), color-stop(1, #c6c652) );
	background:-moz-linear-gradient( center top, #acac39 5%, #c6c652 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#acac39", endColorstr="#c6c652");	background: -o-linear-gradient(top,#acac39,c6c652);

	background-color:#acac39;
	border:0px solid #c6c652;
	text-align:center;
	border-width:0px 0px 1px 1px;
	font-size:10px;
	font-family:Arial;
	font-weight:bold;
	color:#333333;
}
.CSSTableGenerator tr:first-child:hover td{
	background:-o-linear-gradient(bottom, #acac39 5%, #c6c652 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #acac39), color-stop(1, #c6c652) );
	background:-moz-linear-gradient( center top, #acac39 5%, #c6c652 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#acac39", endColorstr="#c6c652");	background: -o-linear-gradient(top,#acac39,c6c652);
	background-color:#acac39;
}
</style>
<html:form action="/examRoomAllotmentCjc" method="post">
	<html:hidden property="formName" value="examRoomAllotmentForCJCForm" />
	<html:hidden property="pageType" value="1"/>
    <html:hidden property="method" styleId="method" value=""/>
    <html:hidden property="propertyName" styleId="propertyName" value="examRoomAllotmentForCJCForm"/>
    <input type="hidden" id="displyColumn" name="examRoomAllotmentForCJCForm"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.exam.allotment" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.allotment.room.allotment.status" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.allotment.room.allotment.status" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="200" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<font color="red" size="2"><div id="errorMessages"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div></font>
							</td>
						</tr>
						<tr>
							<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
						<tr>
						    <td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">* </span> <bean:message
										key="knowledgepro.admin.year" /></div>
									</td>
									<td width="25%" height="25" class="row-even">
									<input type="hidden" id="tempAcademicYear" name="tempAcademicYear" value="<bean:write name="examRoomAllotmentForCJCForm" property="academicYear"/>"/>
									<html:select property="academicYear" styleClass="combo" styleId="academicYear" name="examRoomAllotmentForCJCForm" onchange="getGroupClassByYear(this.value)" >
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select>
									</td>
							<td width="25%" height="25" class="row-odd">
                            	<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.exam.examDefinition.examType" />:</div>
							</td>
							<td width="25%" class="row-even" align="left" colspan="2">
								<html:select property="examType" name="examRoomAllotmentForCJCForm" styleId="examType" styleClass="combo"  onchange="getExamName()" >
                            	<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
                            	<html:option value="M">Mid Sem</html:option>
                            	<html:option value="E">End Sem</html:option>
                            	</html:select>
					        </td>
						</tr>
						<tr>
						 <td width="25%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.exam.exam" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
                            <div align="left">
                            <html:select property="examid" name="examRoomAllotmentForCJCForm" styleId="examid" styleClass="comboMediumBig" onchange="getSubjectNameAndCodeForExam()">
                            <html:option value="">-Select-</html:option>
                            <c:if test="${examRoomAllotmentForCJCForm.examMap != null}">
							<html:optionsCollection name="examRoomAllotmentForCJCForm" property="examMap" label="value" value="key" />
							</c:if>
                            </html:select>
                            </div>
							</td>
						<td width="25%" height="25" class="row-odd" >
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.ExamAllotment.Students.Class.group.Campus" />:</div>
							</td>
							<td width="25%" height="25" class="row-even" colspan="2">
                            <div align="left">
                            <html:select property="campusId" name="examRoomAllotmentForCJCForm"  styleId="campusId" styleClass="combo" onchange="getRoomNoByWorkLocation(this.value)" >
							<html:option value="">--Select--</html:option>
							<c:if test="${examRoomAllotmentForCJCForm.campusMap != null}">
							<html:optionsCollection name="examRoomAllotmentForCJCForm" property="campusMap" label="value" value="key" />
							</c:if>
						    </html:select>
                            </div>
						</td>
						</tr>
						<tr>
							<td width="25%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.hostel.roomno" />:</div>
							</td>
							<td width="25%" class="row-even" align="left" colspan="3">
							<html:select property="roomId" name="examRoomAllotmentForCJCForm" styleId="roomId" styleClass="combo" >
                             <html:option value="">--Select--</html:option> 
                             <c:if test="${examRoomAllotmentForCJCForm.roomMap != null}">
							<html:optionsCollection name="examRoomAllotmentForCJCForm" property="roomMap" label="value" value="key" />
							</c:if>
                            </html:select>
					        </td>
							
						</tr>
						<tr>
							<td width="25%" height="25" class="row-odd">
                            <div align="right">Do you want Datewise Allotment:</div>
							</td>
							<td width="25%" class="row-even" align="left" colspan="3">
							<html:hidden property="isDateWise" styleId="isDateWise" name="examRoomAllotmentForCJCForm"/>
							<html:radio property="isDateWise" styleId="isDateWiseYes" value="Yes" onclick="isDateWiseOrNot(this.value)" ></html:radio>
									Yes&nbsp;&nbsp;&nbsp;
									<html:radio property="isDateWise" value="No" styleId="isDateWiseNo" onclick="isDateWiseOrNot(this.value)" ></html:radio>
									No	
					        </td>
							
						</tr>
						<tr>
							<td  colspan="4">
								<div id="dateWise">
								<table width="100%">
							<tr>
							<td  width="25%" height="25" class="row-odd" >
                            <div align="right"><bean:message
								key="knowledgepro.exam.assignExaminer.date" />:</div>
							</td>
							<td  width="25%" height="25" class="row-even">
                            <div align="left">
                            <html:text name="examRoomAllotmentForCJCForm" property="allottedDate"
										styleId="allottedDate" size="10" maxlength="10" />
                            </div>
                            <script type="text/javascript">
                            $(function(){
								 var pickerOpts = {
										 	            dateFormat:"dd/mm/yy"
										         };  
								  $("#allottedDate").datepicker(pickerOpts);
								});
                            </script>
							</td>
							<td width="25%" height="25" class="row-odd" align="right">Session:</td>
							<td  width="25" height="25" class="row-even" >
							<html:select property="sessionId" name="examRoomAllotmentForCJCForm" styleId="sessionId" styleClass="combo" >
		                             <html:option value="">--Select--</html:option> 
		                             <c:if test="${examRoomAllotmentForCJCForm.sessionMap != null}">
									<html:optionsCollection name="examRoomAllotmentForCJCForm" property="sessionMap" label="value" value="key" />
									</c:if>
		                    </html:select>
							</td>
							</tr>
								</table>
								</div>
							
							</td>
						</tr>
						<tr >
							<td colspan="4">
								<div id="cycleWise" >
								<table width="100%">
								<tr>
								 <td  width="25%" height="25" class="row-odd" >
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.examallotment.room.cycle" />:</div>
							</td>
							<td  width="25%" height="25" class="row-even" >
                            <div align="left">
                            <html:select property="cycleId" name="examRoomAllotmentForCJCForm" styleId="cycleId" styleClass="comboMediumBig" >
                            <html:option value="">--Select--</html:option>
                            <c:if test="${examRoomAllotmentForCJCForm.cycleMap != null}">
							<html:optionsCollection name="examRoomAllotmentForCJCForm" property="cycleMap" label="value" value="key" />
							</c:if>
                            </html:select>
                            </div>
							</td>
							<td width="25%" height="25" class="row-odd" align="right"></td>
							<td  width="25" height="25" class="row-even" >
							</td>
								</tr>
								</table>
								</div>
							</td>
						
							</tr>
							</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
			
								<tr>
									<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right">
											<html:button property="" styleClass="formbutton"
												value="Submit"  styleId="Submit" onclick="getRoomDetails()"></html:button>
									</div>
									</td>
									<td width="2%"></td>
									<td width="49%">
							              <html:button property="" styleClass="formbutton"
												value="Cancel" onclick="cancelStudenRoomAllotment()" styleId="Close"></html:button>
										</td>	
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
var year = document.getElementById("tempAcademicYear").value;
if(year.length != 0) {
 	document.getElementById("academicYear").value=year;
}
var isDateWise =  document.getElementById("isDateWise").value;
if(isDateWise =='Yes'){
	document.getElementById("dateWise").style.display="block";
	document.getElementById("cycleWise").style.display="none";
}else if(isDateWise == 'No'){
	document.getElementById("cycleWise").style.display="block";
	document.getElementById("dateWise").style.display="none";
}
</script>