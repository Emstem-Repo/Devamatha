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
<link rel="stylesheet" type="text/css" href="css/custom-button.css">
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
  .myButton {
        
        -moz-box-shadow: 0px 10px 14px -7px #276873;
        -webkit-box-shadow: 0px 10px 14px -7px #276873;
        box-shadow: 0px 10px 14px -7px #276873;
        
        color: #00CD00;
		background-color: #00CD00;
        -moz-border-radius:8px;
        -webkit-border-radius:8px;
        border-radius:8px;
        
        display:inline-block;
        font-family:arial;
        font-size:12px;
        font-weight:bold;
        text-decoration:none;
        text-shadow:0px 1px 0px #3d768a;
        
    }
    .myButton:hover {
       background-color: #f54952;
    }
    .myButton:active {
        position:relative;
        top:1px;
    }
</style>   
    
<script type="text/javascript"><!--
$(document).ready(function() {
	  $('#Submit').click(function(){
	       var academicYear = $('#academicYear').val();
	       var campusName = $('#campusName').val();
	       var examid = $('#examid').val();
	       if(campusName=='' && examid=='' && cycleId=='' && roomNo==''){
	    	   $('#errorMessage').slideDown().html("<span>Please Select Work Location. <br> Please Select Exam. <br> Please Select Cycle. <br> Please Select Room No.</span>");
	          return false;
	        }
	       else if(campusName== ''){
	        	 $('#errorMessage').slideDown().html("<span>Please Select Work Location.</span>");
	           return false;
	        }
	       else if(examid== ''){
	        	 $('#errorMessage').slideDown().html("<span>Please Select Exam.</span>");
	            return false;
	        }
	       else if(academicYear==''){
	        	$('#errorMessage').slideDown().html("<span>Please Select Academic Year.</span>");
		         return false;
	        }
	       else{
	    	   document.getElementById("method").value = "getRoomsDetailsAllotedForCycle";
	       }
	      });
	});
function cancelUpdateStudentsDetails(){
	document.location.href = "examRoomAllotmentStatus.do?method=initExamRoomStatus";
}
function cancelExamRoomAllotment(){
	document.location.href = "LoginAction.do?method=loginAction";
}

function getCyclesByExamType(midOrEndSem) {
	resetErrMsgs();
	args =  "method=getCyclesByExamType&midEndSem="+midOrEndSem;
	var url = "roomAllotmentStatus.do";
	requestOperationProgram(url, args, updateCycleMap);
}
function updateCycleMap(req){
	updateOptionsFromMap(req, "cycleId", "- Select -");
}
function getExamsByYear(year) {
	getExamNameByAcademicYear("optionMap",year, "examid", updateExamName);
}
function updateExamName(req) {
	updateOptionsFromMap(req, "examid", "- Select -");
}
function goTORoomAllotmentStatus(roomId){
	hook = false;
	var year = document.getElementById("academicYear").value;
	var examId = document.getElementById("examid").value;
	var workLocationId = document.getElementById("campusName").value;
	var examType = document.getElementById("midEndSem").value;
	var cycleId = document.getElementById("cycleId").value;
	var date  = "";
	if(document.getElementById("allottedDate").value!= null ){
		if(document.getElementById("allottedDate").value!=''){
			 date = document.getElementById("allottedDate").value;
		}
	}
	var session = "";
	if(document.getElementById("sessionId").value!= null ){
		if(document.getElementById("sessionId").value!=''){
			session = document.getElementById("sessionId").value;
		}
	}
	document.getElementById("academicYear").value=year;
	document.getElementById("examid").value=examId;
	document.getElementById("campusName").value=workLocationId;
	document.getElementById("midEndSem").value=examType;
	document.getElementById("cycleId").value=cycleId;
	document.getElementById("allottedDate").value=date;
	document.getElementById("sessionId").value=session;
	document.getElementById("roomId").value=roomId;
	document.getElementById("method").value="goToRoomAllotmentStatus";
	document.roomAllotmentStatusForm.submit();
}
function hideDateAndSession(cycleId){
	if(cycleId !='' && cycleId !=null){
		document.getElementById("dateSessionId").style.display = "none";
	}else{
		document.getElementById("dateSessionId").style.display = "block";
	}
	document.getElementById("allottedDate").value="";
	document.getElementById("sessionId").value="";
}
</script>
<html:form action="/examRoomAllotmentStatus" method="post">
	<html:hidden property="formName" value="roomAllotmentStatusForm" />
	<html:hidden property="pageType" value="1"/>
    <html:hidden property="method" styleId="method" value=""/>
    <html:hidden property="roomId" name="roomAllotmentStatusForm" styleId="roomId"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.exam.allotment" /> <span class="Bredcrumbs">&gt;&gt;
			Exam Room Allotment Status &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Exam Room Allotment Status</strong></td>
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
							<font color="red" size="2"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
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
									<input type="hidden" id="tempAcademicYear" name="tempAcademicYear" value="<bean:write name="roomAllotmentStatusForm" property="academicYear"/>"/>
									<html:select property="academicYear" styleClass="combo"
										styleId="academicYear" name="roomAllotmentStatusForm" onchange="getExamsByYear(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select>
									</td>
							<td width="25%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.ExamAllotment.Students.Class.group.Campus" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
                            <div align="left">
                            <html:select property="campusName" name="roomAllotmentStatusForm"  styleId="campusName" styleClass="combo">
							<html:option value="">--Select--</html:option>
							<c:if test="${roomAllotmentStatusForm.workLocationMap != null}">
							<html:optionsCollection name="roomAllotmentStatusForm" property="workLocationMap" label="value" value="key" />
							</c:if>
						    </html:select>
                            </div>
							</td>		
						</tr>
						<tr>
						 <td width="25%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.exam.exam" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
                            <div align="left">
                            <html:select property="examid" name="roomAllotmentStatusForm" styleId="examid" styleClass="comboMediumBig" >
                            <html:option value="">-Select-</html:option>
                            <c:if test="${roomAllotmentStatusForm.examMap != null}">
							<html:optionsCollection name="roomAllotmentStatusForm" property="examMap" label="value" value="key" />
							</c:if>
                            </html:select>
                            </div>
							</td>
							<td width="25%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.exam.examDefinition.examType" />:</div>
							</td>
							<td width="25%" class="row-even" align="left">
							<html:select property="midEndSem" name="roomAllotmentStatusForm" styleId="midEndSem" styleClass="combo"  onchange="getCyclesByExamType(this.value)" >
                            <html:option value="M">Mid Sem</html:option>
                            <html:option value="E">End Sem</html:option>
                            </html:select>
					        </td>
							
						</tr>
						<tr>
						 <td width="25%" height="25" class="row-odd">
                            <div align="right"><bean:message
								key="knowledgepro.examallotment.room.cycle" />:</div>
							</td>
							<td width="25%" height="25" class="row-even" colspan="3">
                            <div align="left">
                            <html:select property="cycleId" name="roomAllotmentStatusForm" styleId="cycleId" styleClass="combo" onchange="hideDateAndSession(this.value)" >
                            <html:option value="">--Select--</html:option>
                            <c:if test="${roomAllotmentStatusForm.cycleMap != null}">
							<html:optionsCollection name="roomAllotmentStatusForm" property="cycleMap" label="value" value="key" />
							</c:if>
                            </html:select>
                            </div>
							</td>
							
							
						</tr>
						<tr>
									<td colspan="4">
									<div id="dateSessionId">
									<table width="100%">
						<tr >
						<td width="25%" height="25" class="row-odd">
						
                            <div align="right"><bean:message
								key="knowledgepro.exam.assignExaminer.date" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
                            <div align="left">
                            <html:text name="roomAllotmentStatusForm" property="allottedDate"
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
								<td height="25" class="row-odd" align="right">Session:</td>
									<td class="row-even" width="24%" colspan="3">
									<html:select property="sessionId" name="roomAllotmentStatusForm" styleId="sessionId" styleClass="combo" >
		                             <html:option value="">--Select--</html:option> 
		                             <c:if test="${roomAllotmentStatusForm.sessionMap != null}">
									<html:optionsCollection name="roomAllotmentStatusForm" property="sessionMap" label="value" value="key" />
									</c:if>
		                            </html:select>
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
											<html:submit property="" styleClass="formbutton"
												value="Submit"  styleId="Submit"></html:submit>
									</div>
									</td>
									<td width="2%"></td>
									<td width="2%">
							              <html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetFieldAndErrMsgs()" styleId="reset"></html:button>
										</td>
										<td width="2%"></td>
									<td width="49%">
							              <html:button property="" styleClass="formbutton"
												value="Cancel" onclick="cancelExamRoomAllotment()" styleId="cancel"></html:button>
										</td>	
								</tr>
							</table>
							</td>
						</tr>
						<logic:notEmpty  property="statusDetailsToList" name="roomAllotmentStatusForm">
						<tr>
							<td height="25">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
									<td width="60%" height="35" align="right">
									<b><font size="2px" class="heading" >
									<logic:notEmpty name="roomAllotmentStatusForm" property="cycleName">
									Cycle : <bean:write name="roomAllotmentStatusForm" property="cycleName"/>
									</logic:notEmpty>
									<logic:notEmpty name="roomAllotmentStatusForm" property="allottedDate">
									Date : <bean:write name="roomAllotmentStatusForm" property="allottedDate"/> & Session : <bean:write name="roomAllotmentStatusForm" property="sessionName"/>
									</logic:notEmpty>
									</font></b>
									
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									
									<td class="myButton1" style="pointer-events: none; cursor: default; padding:10px 17px; " align="right"><font color="black" size="2px">No Available Seats</font>&nbsp;&nbsp;</td>
	            			 		<td class="myButton" style="pointer-events: none; cursor: default; padding:10px 17px; " align="right"><font color="black" size="2px">Available Seats</font></td>	
								</tr>
							</table>
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
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">
							<%int count=0; %>
								<nested:iterate id="roomAllotmentList" name="roomAllotmentStatusForm" property="statusDetailsToList" type="com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo">
								<% if(count == 0 || count ==8){%>
								<tr style="height:10px;"></tr>
								<tr>
								<%} %>
								
									<%if(roomAllotmentList.getRoomCapacity().equalsIgnoreCase("seatsNotAvailable")){%>
									<td width="3%" class="myButton1" style="cursor:pointer;font-size:10px;width: 140px; height: 65px;color:black" onclick="goTORoomAllotmentStatus('<nested:write  property="roomNo" ></nested:write>')">
									<div>
									<table width="80%" height="50" align="center">
									<tr>
									<td width="50%" align="center" style="border: none;" height="10">
									<nested:write property="blockNo"/> - <nested:write property="floorNo"/> 
									</td>
									</tr>
									<tr align="center">
									<td width="50%" align="center" style="border: none;" >
									<nested:write  property="roomNo" ></nested:write>
									</td>
									</tr>
									<tr align="center">
									<td width="50%" align="center" style="border: none;" >
									<BIG><nested:write  property="totalRoomCapacity" ></nested:write>/<nested:write  property="allotedRoomCapacity" ></nested:write></BIG>
									</td>
									</tr>
									</table>
									</div>
									</td>
									<td style="width: 3px;"></td>
									<%}else if(roomAllotmentList.getRoomCapacity().equalsIgnoreCase("seatsAvailable")){ %>
									<td width="3%" class="myButton" style="cursor:pointer;font-size:10px;width: 140px; height: 65px;color:black" onclick="goTORoomAllotmentStatus('<nested:write  property="roomNo" ></nested:write>')">
									<div>
									<table width="80%" height="50" align="center">
									<tr>
									<td width="50%" align="center" style="border: none;color:black" height="10">
									<nested:write property="blockNo"/> - <nested:write property="floorNo"/> 
									</td>
									</tr>
									<tr align="center">
									<td width="50%" align="center" style="border: none;" >
									<nested:write  property="roomNo" ></nested:write>
									</td>
									</tr>
									<tr align="center">
									<td width="50%" align="center" style="border: none;" >
									<BIG><nested:write  property="totalRoomCapacity" ></nested:write>/<nested:write  property="allotedRoomCapacity" ></nested:write></BIG>
									</td>
									</tr>
									</table>
									</div>
									</td>
									<td style="width: 3px;"></td>
									
									<%} %>
									<% if(count ==7){ %>
										<%count =0; %>
									<%} else {%>
									<% count++; %>
									<%} %>
									<% if(count == 0 || count ==8){%>
								</tr>
								<%} %>
								</nested:iterate>
							</table>
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
											
									</div>
									</td>
									<td width="2%"></td>
									<td width="53%">
							              <html:button property="" styleClass="formbutton"
												value="Close" onclick="cancelUpdateStudentsDetails()" styleId="close"></html:button>
										</td>	
								</tr>
							</table>
							</td>
						</tr>
						</logic:notEmpty>
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
var cycle = document.getElementById("cycleId").value;
if(cycle.length != 0) {
	document.getElementById("dateSessionId").style.display = "none";
}else{
	document.getElementById("dateSessionId").style.display = "block";
}
</script>