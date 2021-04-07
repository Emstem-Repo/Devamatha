<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" >
function getEmployeesToMarkAttendance()
{
	document.getElementById("employeeName").value = document.getElementById("employeeId").options[document.getElementById("employeeId").selectedIndex].text;
	document.manualAttendanceEntryForm.submit();
}

function resetValues()
{
	document.getElementById("employeeId").value="";
	document.getElementById("startDate").value="";
	document.getElementById("endDate").value="";
}
function resetMessages() {
		resetErrMsgs();
		resetValues();
}
function getECodeName(eCodeName){

	getEmployeeCodeName("employeeList",eCodeName,"employeeId",updateToEmployee);
	
}

function updateToEmployee(req){
		updateOptionsFromMap(req, "employeeId", "- Select -");
}
</script>
<html:form action="/employeeAttendanceEntry" method="post">
<html:hidden property="pageType" value="1" />
<html:hidden property="formName" value="manualAttendanceEntryForm" />
<html:hidden property="method" value="getEmployeeAttendance" />
<html:hidden property="employeeName" styleId="employeeName"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.employee.manualAttendance" /></span></td>
  </tr>
  <tr>
    <td>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
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

									<td height="25" colspan="4" class="row-even">
									<div align="center">
									<input type="radio" name="eCodeName" id="eCodeName_1" value="eCode" checked="checked" onclick="getECodeName(this.value)" />
									
									Employee Code&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="eCodeName" id="eCodeName_2" value="eName" onclick="getECodeName(this.value)"/> 
										Employee
									Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</div>
									</td>


								</tr>
            	<tr>
	                <td width="23%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.applicationByAdmin.employeeName" />:</div></td>
	                <td width="24%" class="row-even">
	                <html:select property="employeeId" styleClass="comboExtraLarge" styleId="employeeId">
						<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
						<html:optionsCollection property="employeeList" label="value" value="key" />
					</html:select>
	                </td>
	                <td colspan="2" class="row-even">
	                	&nbsp;
	                </td>
            	</tr>
            	<tr>
		               <td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.startdate" /></div></td>
		               <td class="row-even">
							<html:text name="manualAttendanceEntryForm" property="startDate" styleId="startDate" size="16" maxlength="10"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'manualAttendanceEntryForm',
								// input name
								'controlname' :'startDate'
							});
							</script>
					   </td>
		              <td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.enddate" /></div></td>
		               <td class="row-even">
							<html:text name="manualAttendanceEntryForm" property="endDate" styleId="endDate" size="16" maxlength="10"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'manualAttendanceEntryForm',
								// input name
								'controlname' :'endDate'
							});
							</script>
					   </td>
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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right">
            	<html:button property="" styleClass="formbutton" onclick="getEmployeesToMarkAttendance()"><bean:message key="knowledgepro.admin.search" /></html:button>
            </td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left">
            	<html:button property="" styleClass="formbutton" onclick="resetMessages()"><bean:message key="knowledgepro.admin.reset" /></html:button>
            </td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
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