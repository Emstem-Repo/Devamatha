<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">
function selectAll(obj) {
	var value = obj.checked;
	var inputs = document.getElementsByTagName("input");
	var inputObj;
	var checkBoxselectedCount = 0;
	for ( var count1 = 0; count1 < inputs.length; count1++) {
		inputObj = inputs[count1];
		var type = inputObj.getAttribute("type");
		if (type == 'checkbox') {
			inputObj.checked = value;
		}
	}
}
function unCheckSelectAll() {
	var inputs = document.getElementsByTagName("input");
	var inputObj;
	var checkBoxOthersSelectedCount = 0;
	var checkBoxOthersCount = 0;
	for ( var count1 = 0; count1 < inputs.length; count1++) {
		inputObj = inputs[count1];
		var type = inputObj.getAttribute("type");
		if (type == 'checkbox' && inputObj.id != "checkAll") {
			checkBoxOthersCount++;
			if (inputObj.checked) {
				checkBoxOthersSelectedCount++;
			}
		}
	}
	if (checkBoxOthersCount != checkBoxOthersSelectedCount) {
		document.getElementById("checkAll").checked = false;
	} else {
		document.getElementById("checkAll").checked = true;
	}
}

function cencel()
{
	document.location.href = "CocurricularLeaveApproveEntry.do?method=initAuthorizeCocurricularApplication";
}
function changeList(searchvalue)
{
	if(searchvalue!=null)
	{
		
	document.location.href = "CocurricularLeaveApproveEntry.do?method=searchCocurricularAtuthorized&searchValue="+searchvalue;
	}
	
	
}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/CocurricularLeaveApproveEntry">
<c:choose>
		<c:when test="${stateOperation != null && stateOperation == 'View Canceled'}">
			<html:hidden property="method" styleId="method" value="saveCocurricularAttendaceAndUpdateAttendace" />
		</c:when>
		<c:when test="${stateOperation != null && stateOperation == 'View Authorized'}">
			<html:hidden property="method" styleId="method" value="cancelSavedCocurricularAttendance" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="saveCocurricularAttendaceAndUpdateAttendace" />	
		</c:otherwise>
	</c:choose>	
	<html:hidden property="formName" value="cocurricularLeaveApproveForm" />
	<html:hidden property="pageType" value="4" />

	<table width="100%" border="0">
		<tr>
			<td class="Bredcrumbs">
				<bean:message key="knowledgepro.attendance" />
			 <span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.attendance.authorize.cocurricular.attendance.application.entry" /> &gt;&gt;</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.attendance.authorize.cocurricular.attendance.application.entry" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr> 
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
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
										<!--  Start Form Fields -->
										<tr>
											<td height="25" align="right" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.sec.Department" /></div>
											</td>
											<td height="25" align="left" class="row-even">
											<div align="left"><bean:write
												name="cocurricularLeaveApproveForm"
												property="departmentName" /></div>
											</td>
											<td height="25" align="right" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.selectedSubjects" /></div>
											</td>
											<td height="25" align="left" class="row-even">
											<div align="left"><bean:write
												name="cocurricularLeaveApproveForm" property="subjectNames" />
											</div>
											</td>
										</tr>
										<tr>
											<td height="25" align="right" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.mobNewsEventsDetailsDateFrom" /></div>
											</td>
											<td height="25" align="left" class="row-even">
											<div align="left"><bean:write
												name="cocurricularLeaveApproveForm" property="fromDate" /></div>
											</td>
											<td height="25" align="right" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.mobNewsEventsDetailsDateTo" /></div>
											</td>
											<td height="25" align="left" class="row-even">
											<div align="left"><bean:write
												name="cocurricularLeaveApproveForm" property="toDate" /></div>
											</td>
										</tr>
										

									</table>
										<!--  End Form Fields -->
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
							 
							</td>
						</tr>

				<tr>
					<td valign="top" class="news">
					
				 <div align="right" style="padding-right: 100px;">  
						    Category:
						    <select id="searchValue"  class="combo" onchange="changeList(this.value)">
						    <option value=""> --Select--</option>
						    <option value="View Application"> View Application</option>
						    <option value="View Authorized"> View Authorized  Application</option>
						    <option value="View Canceled"> View Canceled Application</option>
						    </select>
						    </div>
						    <div align="left"><bean:write name="cocurricularLeaveApproveForm" property="screenLabel"/></div>
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

										<tr>
											<td width="20" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="25" class="row-odd">
											<div align="center">Select All
											
											<input	type="checkbox" name="allstudent" name="checkbox2" id="checkAll"  onclick="selectAll(this)"/>
											</div>
											</td>
											<td width="" class="row-odd">
											<div align="center">Attendnace Date</div>
											</td>
											<td width="" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.attendance.classname" /></div>
											</td>
											<td width="" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.fee.studentname" /></div>
											</td>
											<td width="" class="row-odd">
											<div align="center"><bean:message
										key="knowledgepro.hostel.reservation.registerNo" /></div>
											</td>
											<td width="" class="row-odd">
											<div align="center"><bean:message
										key="knowledgepro.exam.UnvSubCode.subjectName" /></div>
											</td>
											<td width="" class="row-odd">
											
											<div align="center">Period</div>
											
											</td>
											<td width="" class="row-odd">
											<div align="center">Activity</div>
											</td>
												<td width="" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.attendance.assign.cocurricular.approver.label" /></div>
											</td>
										</tr>
										<logic:iterate id="ApplicationList" name="cocurricularLeaveApproveForm" property="list"
											indexId="count">
									<tr>
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td height="20">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td align="center" width="25">
										<!--  check box -->
										 <input
							type="hidden"
							name="list[<c:out value='${count}'/>].tempChecked"
							id="hidden_<c:out value='${count}'/>"
							value="<nested:write name='ApplicationList' property='tempChecked'/>" />
										
										
										
										<input
							type="checkbox"
							name="list[<c:out value='${count}'/>].checked"
							id="check_<c:out value='${count}'/>"  onclick="unCheckSelectAll()"/>
							
							<script type="text/javascript">
							var studentId1 = document.getElementById("hidden_<c:out value='${count}'/>").value;
							if(studentId1 == "true") {
								alert(studentId1);
								document.getElementById("check_<c:out value='${count}'/>").checked = true;
								
							}		
						</script>
										<!--  end check box -->
										</td>
											<td align="center"><bean:write name="ApplicationList"
											property="attendanceDate" /></td>
										<td align="center"><bean:write name="ApplicationList"
											property="className" /></td>
										<td align="center"><bean:write name="ApplicationList"
											property="studentName" /></td>
										<td align="center"><bean:write name="ApplicationList"
											property="registerNumber" /></td>
											<td align="center"><bean:write name="ApplicationList"
											property="subjectName" /></td>
											<td align="center"><bean:write name="ApplicationList"
											property="periodName" /></td>
											<td align="center"><bean:write name="ApplicationList"
											property="activityName" /></td>
											<td align="center"><bean:write name="ApplicationList"
											property="approveTeacherName" /></td>
									</tr>
								</logic:iterate>
										
										</table>
							</td>
						</tr>
							</table>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
								<tr>
									<td width="45%" height="35">
									<div align="right">
									<c:choose>
										<c:when
											test="${stateOperation != null && stateOperation == 'View Authorized'}">
											<html:submit property="" styleClass="formbutton"
												value="Cancel Application" styleId="submitbutton">
											</html:submit>
										</c:when>
										<c:when
											test="${stateOperation != null && stateOperation == 'View Canceled'}">
											<html:submit property="" styleClass="formbutton"
												value="Re-Authorized Application" styleId="submitbutton">
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Authorized Application" styleId="submitbutton">
											</html:submit>
										</c:otherwise>
									</c:choose></div>
									</td> 
									<td width="2%"></td>
									<td width="53%"><html:button property=""
										styleClass="formbutton" value="Reset" onclick="cencel()"></html:button></td>
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
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
              <td  valign="top" background="images/Tright_03_03.gif"></td>
              <td height="20" valign="top" ></td>
              <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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