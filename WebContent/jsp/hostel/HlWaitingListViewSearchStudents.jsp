<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar.js"></script>

<script type="text/javascript">
function getRoomTypes(hostelId){
	var args = "method=getRoomTypeByHostelBYstudent&hostelId="+hostelId;
	var url ="AjaxRequest.do";
	requestOperation(url,args,updateRoomType);
}
function updateRoomType(req) {
	updateOptionsFromMap(req, "roomTypeName", "- Select -");
}

function unCheckSelectAll() {
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxOthersSelectedCount = 0;
    var checkBoxOthersCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox' && inputObj.id != "checkAll") {
                  checkBoxOthersCount++;
                  if(inputObj.checked) {
                        checkBoxOthersSelectedCount++;
                  }    
            }
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
      document.getElementById("checkAll").checked = false;
    } else {
      document.getElementById("checkAll").checked = true;
    } 
}
function Cancel(){
	document.location.href = "hostelWaitingListView.do?method=initHostelWaitingListView";
}
function sendSmsAndMailAfterConfirm()
{
	document.getElementById("method").value="sendSmsAndMailAfterConfirm";
}
function dontSendSmsAndMail()
{
	document.getElementById("method").value = "searchStudentsInHostel";
}
</script>
<html:form action="/hostelWaitingListView" method="post">
<html:hidden property="formName" value="hlAdmissionForm" />
<html:hidden property="method" styleId="method" value="sendMailAndSmsForSelectedStudents" />
<html:hidden property="id" styleId="id"/>
<html:hidden property="pageType" value="4"/>

<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel.adminmessage.hostel" />
			 <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.waitinglistview.search.students" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong class="boxheader"> <bean:message key="knowledgepro.hostel.waitinglistview.search.students" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" 	height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage" ><FONT color="red" size="1"><html:errors /></FONT>
							<FONT color="green" size="1"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						
						<tr>
							<td valign="top" class="news">
							<table width="50%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914"  background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top" class="news">
									<table width="100%" cellspacing="1" cellpadding="2" align="center">
                                      <tr>
                                     <td width="25%" height="30" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.hostel" /></div>
									</td>
									<td width="25%" height="30" class="row-odd" align="center"><font size="2" color="blue">
									<bean:write name="hlAdmissionForm" property="hostelTempName"/></font></td>
                                      </tr>
									<tr>
									<td width="25%" height="30" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.roomtype" /></div>
									</td>
									<td width="25%" height="30" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.Hostel.Available.seat" /></div>
									</td>
									</tr>
									<logic:notEmpty property="roomAndAvailableSeatsMap" name="hlAdmissionForm">
									<logic:iterate id="roomAndAvailableSeats" name="hlAdmissionForm" property="roomAndAvailableSeatsMap" >
									<tr>
										<td width="25%" height="25" class="row-even" align="center">
											<bean:write name="roomAndAvailableSeats" property="key" />
										</td>
										<td width="25%" height="25" class="row-even" align="center">
											<bean:write name="roomAndAvailableSeats" property="value"/>
										</td>
									</tr>
									</logic:iterate>
									</logic:notEmpty>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
							<tr>
							<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914"  background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top" class="news">
									<table width="100%" cellspacing="1" cellpadding="2" align="center">
                                   <tr>
                                   <td width="13%" height="30" class="row-odd"><div align="center">
                                   <bean:message key="knowledgepro.exam.reJoin.registerNo" /></div>
                                   </td>
                                   <td width="13%" height="30" class="row-odd"><div align="center">
                                   <bean:message key="knowledgepro.fee.applicationno" /></div>
                                   </td>
                                   <td width="13%" height="30" class="row-odd"><div align="center">
                                   <bean:message key="knowledgepro.exam.supplementaryImpApplication.studentName" /></div>
                                   </td>
                                   <td width="13%" height="30" class="row-odd"><div align="center">
                                   <bean:message key="knowledgepro.exam.supplementaryImpApplication.course" /></div>
                                   </td>
                                   <td width="13%" height="30" class="row-odd"><div align="center">
                                   <bean:message key="knowledgepro.roomtype" /></div>
                                   </td>
                                   <td width="13%" height="30" class="row-odd"><div align="center">
                                   <bean:message key="knowledgepro.hostel.waitinglist.student.no" /></div>
                                   </td>
                                  <td width="13%" class="row-odd">Select Students</td>
								  <td width="13%" height="30" class="row-odd" id="intimatedDateDisplay"><div align="center">
                                   <bean:message key="knowledgepro.hostel.waitinglist.intimatedate" /></div>
                                   </td>
                                   </tr>
                                   <logic:notEmpty property="hlWaitingListViewList" name="hlAdmissionForm">
                                   <logic:iterate id="hlWaitingListView" property="hlWaitingListViewList" name="hlAdmissionForm" indexId="count">
                                   <tr>
                                   <td width="13%" height="25" class="row-even"><div align="center">
                                   <bean:write name="hlWaitingListView" property="registerNo"></bean:write></div>
                                   </td>
                                   <td width="13%" height="25" class="row-even"><div align="center">
                                   <bean:write name="hlWaitingListView" property="applicationNo"></bean:write></div>
                                   </td>
                                   <td width="13%" height="25" class="row-even"><div align="center">
                                   <bean:write name="hlWaitingListView" property="studentName"></bean:write></div>
                                   </td>
                                   <td width="13%" height="25" class="row-even"><div align="center">
                                   <bean:write name="hlWaitingListView" property="courseName"></bean:write></div>
                                   </td>
                                   <td width="13%" height="25" class="row-even"><div align="center">
                                   <bean:write name="hlWaitingListView" property="roomTypeName"></bean:write></div>
                                   </td>
                                   <td width="13%" height="25" class="row-even"><div align="center">
                                   <bean:write name="hlWaitingListView" property="waitingListPriorityNo"></bean:write></div>
                                   </td>
                                   <td width="13%" height="25" class="row-even">
                                   <input type="hidden" name="hlWaitingListViewList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																value="<nested:write name='hlWaitingListView' property='tempChecked'/>" />
									<input type="hidden" name="hlWaitingListViewList[<c:out value='${count}'/>].temCheckedSelected"	id="hidden1_<c:out value='${count}'/>"
																value="<nested:write name='hlWaitingListView' property='temCheckedSelected'/>" />
                                   <input type="checkbox" name="hlWaitingListViewList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>" onclick="unCheckSelectAll()"/>
                                   	<script type="text/javascript">
																var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																document.getElementById("<c:out value='${count}'/>").disabled =true;
																		}	
																var studentId1 = document.getElementById("hidden1_<c:out value='${count}'/>").value;
																if(studentId1 == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																		}
																</script>
                                   </td>
                                    <td width="13%" height="25" class="row-even" id="intimateDate"><div align="center">
                                   <bean:write name="hlWaitingListView" property="intimateDate"></bean:write></div>
                                   </td>
                                   </tr>
                                   </logic:iterate>
                                   </logic:notEmpty>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						
						      <tr>
							<td height="25">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="35" colspan="6" align="center">
									<div align="center">
											<html:submit property="" styleClass="formbutton">
												<bean:message key="knowledgepro.submit" /></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;
												<html:button property="" styleClass="formbutton" onclick="Cancel()">
												<bean:message key="knowledgepro.cancel" /></html:button>
									</div>
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
</script>