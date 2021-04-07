<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="css/calendar.css">
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" >
function resetMessages() {
	//document.location.href = "employeeAttendanceEntry.do?method=getEmployeesToMarkAttendance";
	resetFieldAndErrMsgs();
	document.getElementById("inTime").value="0";
	document.getElementById("outTime").value="0";
	document.getElementById("inTime1").value="0";
	document.getElementById("outTime1").value="0";
}

function clearField(field){
	if(field.value == "0")
		field.value = "";
}
function checkForEmpty(field){
	if(field.value.length == 0)
		field.value="0";
	if(isNaN(field.value)) {
		field.value="0";
	}
}

function addAttendance()
{
	document.getElementById("method").value="addAttendance";
}

function editAttendance(id)
{
	document.location.href = "employeeAttendanceEntry.do?method=editAttendance&id="
		+ id;
}

function deleteAttendance(id)
{
	deleteConfirm = confirm("Are you sure want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "employeeAttendanceEntry.do?method=deleteAttendance&id="
				+ id;
	}
}

function updateAttendance()
{
	document.getElementById("method").value="updateAttendance";
}
function reActivate(id)
{
	document.location.href = "employeeAttendanceEntry.do?method=reActivateAttendance&id="+id;
}
</script>
</head>
<html:form action="/employeeAttendanceEntry" method="post">
<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateAttendance" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addAttendance" />
		</c:otherwise>
	</c:choose>
<html:hidden property="pageType" value="2" />
<html:hidden property="formName" value="manualAttendanceEntryForm" />
<html:hidden property="id"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.employee.manualAttendance" /></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body"><strong class="boxheader"><bean:message key="knowledgepro.employee.manualAttendance" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
			<td colspan="2" align="left">
			<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
			</td>
	      </tr>
          <tr>
            <td><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
            	<tr>
            		<td width="34%" class="row-even">
            		<table width="100%" cellspacing="1" cellpadding="2">
		              <tr>
		                <td class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.applicationByAdmin.employeeName" />:</div></td>
		                <td class="row-even"><bean:write name="manualAttendanceEntryForm" property="employeeName"/></td>
		                <td class="row-even" colspan="2">&nbsp;</td>
		              </tr>
		              <tr>
		              	<td class="row-odd"><div align="right"><bean:message key="knowledgepro.feepays.startdate" />:</div></td>
		                <td class="row-even"><bean:write name="manualAttendanceEntryForm" property="startDate"/></td>
		                <td class="row-odd"><div align="right"><bean:message key="knowledgepro.feepays.enddate" />:</div></td>
		                <td class="row-even"><bean:write name="manualAttendanceEntryForm" property="endDate"/></td>
		              </tr>
            		</table>
            		</td>
                </tr>
            </table>
            </td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr>
                <td class="row-odd" align="right"><bean:message key="knowledgepro.employee.manualAttendanceEntry.attendanceDate" />:</td>
                <td class="row-even" align="left">
                	<nested:text property="attendanceDate"></nested:text>
                	<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'manualAttendanceEntryForm',
								// input name
								'controlname' :'attendanceDate'
							});
					</script>
                </td>	
                <td colspan="2" class="row-even"></td>
              </tr>
              <tr>
                <td class="row-odd" align="right"><bean:message key="knowledgepro.employee.manualAttendanceEntry.inTime" />:</td>
                <td class="row-even" align="left">
                	<nested:text property="inTimeHrs" styleId="inTime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this)"></nested:text>
                	&nbsp;
                	:
                	&nbsp;
                	<nested:text property="inTimeMins" styleId="inTime1" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this)"></nested:text>
                </td>
                <td class="row-odd" align="right"><bean:message key="knowledgepro.employee.manualAttendanceEntry.outTime" />:</td>
                <td class="row-even" align="left">
                	<nested:text property="outTimeHrs" styleId="outTime"  size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this)"></nested:text>
                	&nbsp;
                	:
                	&nbsp;
                	<nested:text property="outTimeMins" styleId="outTime1" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this)"></nested:text>
                </td>
              </tr>
              <tr>
            	<td colspan="2" width="49%" height="35" >
 		           <div align="right">	<c:choose>
                    	<c:when test="${operation == 'edit'}">
                        	<html:submit property="" styleClass="formbutton" onclick="updateAttendance()"><bean:message key="knowledgepro.update" /></html:submit>
                        </c:when>
                        <c:otherwise>
                           	<html:submit property="" styleClass="formbutton" onclick="addAttendance()"><bean:message key="knowledgepro.submit" /></html:submit>
                        </c:otherwise>
                    </c:choose> 
 		           </div>	
            	</td>
            	<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><c:choose>
								<c:when test="${operation == 'edit'}">
									<html:cancel styleClass="formbutton">
										<bean:message key="knowledgepro.admin.reset" />
									</html:cancel>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetMessages()">
										<bean:message key="knowledgepro.cancel" />
									</html:button>
								</c:otherwise>
							</c:choose></td>
          </tr>
              
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
       <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <nested:notEmpty property="employeesToMarkAttendanceList" name="manualAttendanceEntryForm">
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr>
                <td class="row-odd"><bean:message key="knowledgepro.slno" /></td>
                <td class="row-odd"><bean:message key="knowledgepro.employee.manualAttendanceEntry.attendanceDate" /></td>
                <td class="row-odd"><bean:message key="knowledgepro.employee.manualAttendanceEntry.inTime" /></td>
                <td class="row-odd"><bean:message key="knowledgepro.employee.manualAttendanceEntry.outTime" /></td>
                <td class="row-odd"><bean:message key="knowledgepro.edit" /></td>
				<td class="row-odd"><bean:message key="knowledgepro.delete" /></td>
              </tr>
              <nested:iterate name="manualAttendanceEntryForm" property="employeesToMarkAttendanceList" id="employeeList" indexId="count">
					<c:choose>
						<c:when test="${count%2 == 0}">
							<tr class="row-even">
						</c:when>
						<c:otherwise>
							<tr class="row-white">
						</c:otherwise>
					</c:choose>
						<td>
							<c:out value="${count+1}"></c:out>
						</td>
						<td>
							<bean:write name="employeeList" property="attendanceDate"/>
						</td>
						<td>
							<bean:write name="employeeList" property="inTime"/>
						</td>
						<td>
							<bean:write name="employeeList" property="outTime"/>
						</td>
						<td width="6%" height="25">
							<div align="center">
								<img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editAttendance('<bean:write name="employeeList" property="id" />')" />
							</div>
						</td>
						<td width="6%" height="25">
							<div align="center">
								<img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteAttendance('<bean:write name="employeeList" property="id"/>')" />
							</div>
						</td>	
					</tr>
			</nested:iterate>
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      </nested:notEmpty>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>