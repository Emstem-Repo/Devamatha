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
	font-family: Garamond;
	font-size: 11px;
	margin-left: 10px
}
</style>

<script type="text/javascript">
	function updateLeave() {
		var id=document.getElementById("id").value;
		document.getElementById("method").value = "updateHostelLeave";
	}
	
	function resetStudentLeave()
	{  
		var id=document.getElementById("id").value;
		document.location.href = "hostelLeave.do?method=editStudentLeaveDetails&id="+id;
	}
</script>
</head>
<html:form action="/hostelLeave" method="POST">
	<html:hidden property="formName" value="hostelLeaveForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method"
		value="updateHostelLeave" />
	<html:hidden property="id" styleId="id" />
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.leave.edit.studentLeave" /> &gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.hostel.leave.edit.studentLeave" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<div id="notValid"><FONT color="red"></FONT></div>
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
							<td colspan="4">
							<table width="100%">
							<tr>
							<td width="20%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.fee.studentname" />:</div>
									</td>
									<td width="30%" height="25" class="row-even" colspan="3"><bean:write
										name="hostelLeaveForm" property="studentName"/></td>
							</tr>
							
							<tr>
							<td width="20%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.student.regNo" />:</div>
									</td>
									<td width="30%" height="25" class="row-even"><bean:write
										name="hostelLeaveForm" property="registerNo"/></td>
							<td width="20%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.name.col" /></div>
									</td>
									<td width="30%" height="25" class="row-even"><bean:write
										name="hostelLeaveForm" property="hostelName"/></td>
										</tr>
										
										<tr>
										<td width="20%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.roomno" />:</div>
									</td>
									<td width="30%" height="25" class="row-even"><bean:write
										name="hostelLeaveForm" property="roomNo"/></td>
										
							<td width="20%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.checkin.bedNo" /></div>
									</td>
									<td width="30%" height="25" class="row-even"><bean:write
										name="hostelLeaveForm" property="bedNo"/></td>
										</tr>
										
										<tr>
										<td width="20%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.auditorium.block.name" />:</div>
									</td>
									<td width="30%" height="25" class="row-even"><bean:write
										name="hostelLeaveForm" property="studentBlock"/></td>
										
										<td width="20%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.leave.unit" />:</div>
									</td>
									<td width="30%" height="25" class="row-even"><bean:write
										name="hostelLeaveForm" property="studentUnit"/></td>
										</tr>
					</table>
							</td>
							</tr>
								<tr>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">* </span> <bean:message
										key="knowledgepro.admin.year" /></div>
									</td>
									<td width="30%" height="25" class="row-even" colspan="3">
									<input type="hidden" id="tempAcademicYear" name="tempAcademicYear" value="<bean:write name="hostelLeaveForm" property="academicYear1"/>" />
									<html:select
										property="academicYear1" styleClass="combo"
										styleId="academicYear" name="hostelLeaveForm">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select>
									<div id="academicYearError"></div>
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
										language="JavaScript"><!--

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
									<div align="right"><span class="Mandatory">*</span> <bean:message
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
							<div align="right"><html:submit property=""
								styleClass="formbutton" onclick="updateLeave()">
								<bean:message key="knowledgepro.update" />
							</html:submit></div>
							</td>
							<td width="3%"></td>
							<td width="50%"><html:button property=""
								styleClass="formbutton" value="Reset"
								onclick="resetStudentLeave()">
							</html:button></td>
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
	var yearId = document.getElementById("tempAcademicYear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
</script>
