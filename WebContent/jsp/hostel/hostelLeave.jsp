<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
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
	function saveLeave() {
		document.getElementById("method").value = "submitHostelLeave";
	}
	function verifyRegisterNumber() {
		var academicYear=document.getElementById("academicYear").value;
		var regNo=document.getElementById("regid").value;
		if(academicYear!=null && academicYear!="" && regNo!=null && regNo!=""){
		document.getElementById("academicYearError").style.display = "none";
		document.getElementById("display").style.display = "none";
		document.getElementById("registerNoError").style.display = "none";
		verifyRegisterNumberAndGetName(regNo,academicYear,displayName);
			}
		else if(academicYear!=null && academicYear!="")
		{
			document.getElementById("registerNoError").innerHTML="Please Enter Register Number";
			document.getElementById("registerNoError").style.display = "block";
			document.getElementById("studentLeave").style.display = "none";
			document.getElementById("academicYearError").style.display = "none";
		}
		else if(regNo!=null && regNo!="")	{
			document.getElementById("academicYearError").innerHTML="Please Select Academic Year";
			document.getElementById("academicYearError").style.display = "block";
			document.getElementById("registerNoError").style.display = "none";
			document.getElementById("studentLeave").style.display = "none";
				}
		document.getElementById("tempAcademicYear").value=academicYear;	
		
}
	function displayName(req) {
		var responseObj = req.responseXML.documentElement;
		var value = responseObj.getElementsByTagName("value");
		var isMsg = false;
		if (value != null) {
			for ( var I = 0; I < value.length; I++) {
				if (value[I].firstChild != null) {
					var temp = value[I].firstChild.nodeValue;
					document.getElementById("notValid").innerHTML = temp;
					document.getElementById("notValid").style.display = "block";
					document.getElementById("studentLeave").style.display = "none";
					//enableFields(true);
					isMsg = true;
				}
			}
		}
		if (isMsg != true) {
			var items = responseObj.getElementsByTagName("studentDetails");

			for ( var I = 0; I < items.length; I++) {
				if(items[I]!=null){
					var studentName = items[I].getElementsByTagName("studentName")[0].firstChild.nodeValue;
					var studentRoom= items[I].getElementsByTagName("studentRoom")[0].firstChild.nodeValue;
					var studentBed = items[I].getElementsByTagName("studentBed")[0].firstChild.nodeValue;
					var studentBlock = items[I].getElementsByTagName("studentBlock")[0].firstChild.nodeValue;
					var studentUnit = items[I].getElementsByTagName("studentUnit")[0].firstChild.nodeValue;
					var studentClass = items[I].getElementsByTagName("studentClass")[0].firstChild.nodeValue;
					var studentHostel = items[I].getElementsByTagName("studentHostel")[0].firstChild.nodeValue;
					document.getElementById("studentName").innerHTML = studentName;
					document.getElementById("studentRoomNo").innerHTML = studentRoom;
					document.getElementById("studentBedNo").innerHTML = studentBed;
					document.getElementById("studentBlock").innerHTML = studentBlock;
					document.getElementById("studentUnit").innerHTML = studentUnit;
					document.getElementById("studentClass").innerHTML = studentClass;
					document.getElementById("studentHostel").innerHTML = studentHostel;
					document.getElementById("studentLeave").style.display = "block";
					document.getElementById("notValid").style.display = "none";
					//enableFields(false);
				}
			}
		}
	}
	function resetStudentLeave()
	{  
		resetFieldAndErrMsgs();
		//enableFields(false);
		document.getElementById("academicYearError").style.display = "none";
		document.getElementById("registerNoError").style.display = "none";
		document.getElementById("studentLeave").style.display = "none";
		document.getElementById("notValid").style.display = "none"; 
	}
	function resetStudentLeave1()
	{  
		document.location.href ="hostelLeave.do?method=initHostelLeave"; 
	}
	/*function enableFields(value){

		document.getElementById('leaveFromId').disabled = value;
		document.getElementById('leaveFromSessionEve').disabled = value;
		document.getElementById('leaveToId').disabled = value;
		document.getElementById('leaveToSessionMor').disabled = value;
		document.getElementById('requestTypeParental').disabled = value;
		document.getElementById('rId').disabled = value;
		document.getElementById('remarks').disabled = value;
		document.getElementById('reasons').disabled = value;
		document.getElementById('leaveFromSessionMor').disabled = value;
		document.getElementById('requestTypeSelf').disabled = value;
		document.getElementById('requestTypeBoth').disabled = value;
		document.getElementById('leaveToSessionEve').disabled = value;
		
	}*/
	function cancel() {
		
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function cancel1() {
		
		document.location.href = "HostelTransaction.do?method=transactionImages";
	}

	function getPreviousLeaves(){
		var regNo=document.getElementById("regid").value;
		var url = "hostelLeave.do?method=getPreviousLeaves&registerNo="+regNo;
		myRef = window .open(url, "ViewResume", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
	}
	
</script>
</head>
<html:form action="/hostelLeave" method="POST">
	<html:hidden property="formName" value="hostelLeaveForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method"
		value="submitHostelLeave" />
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.leave" /> &gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.hostel.leave" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<font color="red" size="2"><div id="notValid"></div></font>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>

					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="80" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">* </span> <bean:message
										key="knowledgepro.admin.year" /></div>
									</td>
									<td width="30%" height="25" class="row-even">
									<input type="hidden" id="tempAcademicYear" name="tempAcademicYear" value="<bean:write name="hostelLeaveForm" property="academicYear1"/>"/>
									<html:select
										property="academicYear1" styleClass="combo"
										styleId="academicYear" name="hostelLeaveForm" onchange="verifyRegisterNumber()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select>
									<div id="academicYearError"></div>
									</td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.hostel.student.regNo" /></div>
									</td>
									<td width="30%" height="25" class="row-even"><html:text
										name="hostelLeaveForm" property="registerNo" styleId="regid"
										size="10" maxlength="10"
										onchange="verifyRegisterNumber()"/></td>
										<div id="registerNoError"></div>
								</tr>
								<tr>
									<td colspan="4">
									<div id="studentLeave">
									<table width="80%">
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 215px; height: 25">
											Student Name:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left" >
											<div id="studentName" align="left"
												style="width: 330px; height: 25"></div>
											</td>
											<td colspan="2" class="row-even" align="center"><a href="#" onclick="getPreviousLeaves()">View Previous Leaves</a></td>
										</tr>
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 215px; height: 25">
											Class:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<div id="studentClass" align="left"
												style="width: 330px; height: 25"></div>
											</td>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 215px; height: 25">
											Hostel:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<div id="studentHostel" align="left"
												style="width: 330px; height: 25"></div>
											</td>
										</tr>
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 200px; height: 25">
											Room Number:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<div id="studentRoomNo" align="left"
												style="width: 300px; height: 25"></div>
											</td>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 200px; height: 25">
											Bed Number:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<div id="studentBedNo" align="left"
												style="width: 300px; height: 25"></div>
											</td>
										</tr>
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 200px; height: 25">
											Block:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<div id="studentBlock" align="left"
												style="width: 300px; height: 25"></div>
											</td>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 200px; height: 25">
											Unit:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<div id="studentUnit" align="left"
												style="width: 300px; height: 25"></div>
											</td>
										</tr>
									</table>
									</div>
									<div id="display">
									<logic:equal value="true" name="hostelLeaveForm" property="displayStudentDetails">
									<table width="100%">
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<bean:message key="knowledgepro.fee.studentname"/>:
											</td>
											<td width="30%" height="25" class="row-even" align="left">
												 <bean:write name="hostelLeaveForm" property="studentName"></bean:write>
											</td>
											<td colspan="2" class="row-even" align="center"><a href="#" onclick="getPreviousLeaves()">View Previous Leaves</a></td>
										</tr>
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<bean:message key="knowledgepro.attendanceentry.class"/>:
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<bean:write name="hostelLeaveForm" property="className"></bean:write>
											</td>
											<td width="20%" height="25" class="row-odd" align="right">
											<bean:message key="knowledgepro.hostel"/>:
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<bean:write name="hostelLeaveForm" property="hostelName"></bean:write>
											</td>
										</tr>
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<bean:message key="knowledgepro.hostel.student.rno"/>:
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<bean:write name="hostelLeaveForm" property="roomNo"></bean:write>
											</td>
											<td width="20%" height="25" class="row-odd" align="right">
												<bean:message key="knowledgepro.hostel.bedno"/>:
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<bean:write name="hostelLeaveForm" property="bedNo"></bean:write>
											</td>
										</tr>
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<bean:message key="knowledgepro.hostel.blocks"/>:
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<bean:write name="hostelLeaveForm" property="studentBlock"></bean:write>
											</td>
											<td width="20%" height="25" class="row-odd" align="right">
											<bean:message key="knowledgepro.hostel.units"/>:
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<bean:write name="hostelLeaveForm" property="studentUnit"></bean:write>
											</td>
										</tr>
									</table>
									</logic:equal>
									</div>
									</td>
								</tr>
								<tr>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.leave.from" /></div>
									</td>
									<td width="30%" height="25" class="row-even"><html:text
										name="hostelLeaveForm" property="leaveFrom"
										styleId="leaveFromId" size="10" maxlength="10" /> <script
										language="JavaScript">

										$(function(){
											 var pickerOpts = {
													 	            dateFormat:"dd/mm/yy"
													         };  
											  $.datepicker.setDefaults(
											    $.extend($.datepicker.regional[""])
											  );
											  $("#leaveFromId").datepicker(pickerOpts);
											});

		
</script> <nested:radio property="leaveFromSession" styleId="leaveFromSessionMor"
										value="Morning">
										<bean:message key="knowledgepro.hostel.leave.morning" />
									</nested:radio> <nested:radio property="leaveFromSession"
										styleId="leaveFromSessionEve" value="Evening">
										<bean:message key="knowledgepro.hostel.leave.evening" />
									</nested:radio></td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.leave.to" /></div>
									</td>
									<td width="30%" height="25" class="row-even"><html:text
										name="hostelLeaveForm" property="leaveTo" styleId="leaveToId"
										size="10" maxlength="10" /> <script language="JavaScript">
										$(function(){
											 var pickerOpts = {
										 	            dateFormat:"dd/mm/yy"
										         };  
											  $.datepicker.setDefaults(
											    $.extend($.datepicker.regional[""])
											  );
											  $("#leaveToId").datepicker(pickerOpts);
											});
										
</script> <nested:radio property="leaveToSession" styleId="leaveToSessionMor"
										value="Morning">
										<bean:message key="knowledgepro.hostel.leave.morning" />
									</nested:radio> <nested:radio property="leaveToSession"
										styleId="leaveToSessionEve" value="Evening">
										<bean:message key="knowledgepro.hostel.leave.evening" />
									</nested:radio></td>

								</tr>

								<tr>
									<td width="20%" height="25" class="row-odd" width="25%">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.leave.requestType" /></div>
									</td>
									<td width="30%" height="25" class="row-even" align="left"><nested:radio
										property="requestType" styleId="requestTypeParental"
										value="Parental Request">
										<bean:message
											key="knowledgepro.hostel.leave.requestType.parental" />
									</nested:radio> <nested:radio property="requestType" styleId="requestTypeSelf"
										value="Self Request">
										<bean:message key="knowledgepro.hostel.leave.requestType.self" />
									</nested:radio> <nested:radio property="requestType" styleId="requestTypeBoth"
										value="Both">
										<bean:message key="admin.generatepassword.select.mail.both" />
									</nested:radio></td>
									<td width="20%" height="25" valign="middle" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.sec.LeaveType" /></div>
									</td>
									<td width="30%" height="25" class="row-even"><html:select
										name="hostelLeaveForm" property="leaveType" styleClass="combo"
										styleId="rId">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="hostelLeaveForm"
											property="leaveTypeList">
											<html:optionsCollection name="hostelLeaveForm"
												property="leaveTypeList" label="name" value="id" />
										</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td width="20%" height="25" valign="top" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.adminmessage.remarks" /></div>
									</td>
									<td width="30%" height="25" class="row-even"><html:textarea
										property="remarks" styleId="remarks" cols="20" rows="2" /></td>
									<td width="20%" height="25" valign="top" class="row-odd">
									<div align="right"> <bean:message
										key="knowledgepro.feepays.Reason" /></div>
									</td>
									<td width="30%" height="25" class="row-even"><html:textarea
										property="reasons" styleId="reasons" cols="20" rows="2" /></td>
								</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
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
					<td height="38" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="47%" height="35">
							<div align="center"><html:submit property=""
								styleClass="formbutton" onclick="saveLeave()">
								<bean:message key="knowledgepro.submit" />
							</html:submit>&nbsp;&nbsp;&nbsp;&nbsp;
							<c:choose>
										<c:when test="${hostelLeaveForm.isHlTransaction !=null &&  hostelLeaveForm.isHlTransaction !='' && hostelLeaveForm.isHlTransaction == 'true'}">
											<html:button property=""  styleClass="formbutton" value="Reset" onclick="resetStudentLeave1()"> </html:button>&nbsp;&nbsp;&nbsp;&nbsp;
											
											<html:button property="" styleClass="formbutton" value="Close" onclick="cancel1()"> </html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton" value="Reset" onclick="resetStudentLeave()"> <bean:message key="knowledgepro.cancel" />
											</html:button>&nbsp;&nbsp;&nbsp;&nbsp;
											
											<html:button property="" styleClass="formbutton" value="Close" onclick="cancel()"> </html:button>
										</c:otherwise>
									</c:choose>
							</div>
							</td>
							<td width="3%" />
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	document.getElementById("studentLeave").style.display = "none";
	document.getElementById("notValid").style.display = "none";
	document.getElementById("academicYearError").style.display = "none";
	document.getElementById("registerNoError").style.display = "none";
	var yearId = document.getElementById("tempAcademicYear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
</script>