<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" >

function editUserInfo() {
	document.location.href = "EditUserInfo.do?method=editUserInfo";
}

function updateUsers(){
	document.getElementById("method").value = "updateUserInfo";
	document.createUserForm.submit();
}
function addUsers(){
	document.getElementById("method").value = "addUserInfo";
	document.createUserForm.submit();
}
function shows(obj,msg){
	document.getElementById("messageBox").style.top=obj.offsetTop;
	document.getElementById("messageBox").style.left=obj.offsetLeft+obj.offsetWidth+5;
	document.getElementById("contents").innerHTML=msg;
	document.getElementById("messageBox").style.display="block";
	}
function hides(){
	document.getElementById("messageBox").style.display="none";
}

function getTeachingStaff(employeeId) {
	document.getElementById("teachingStaff_1").checked=false;
	document.getElementById("teachingStaff_2").checked=true;
	if(employeeId!=null && employeeId!='')
		checkTeachingStaff("teachingStaff_1",employeeId,"teachingStaff_2",updateTeachingStaff);
	
}

function getGuestTeachingStaff(guestId) {
	document.getElementById("teachingStaff_1").checked=false;
	document.getElementById("teachingStaff_2").checked=true;
	if(guestId!=null && guestId!='')
		checkTeachingStaff("teachingStaff_1",guestId,"teachingStaff_2",updateTeachingStaff);
	
}
function updateTeachingStaff(req) {
	updateCheckTeachingStaff(req,"teachingStaff_1","teachingStaff_2");	
}


function getEmployee(){
	var emp=document.getElementById("employee").value;
	if(emp == "employee")
	{
		document.getElementById("guestId").value=null;
		document.getElementById("ShowEmployee1").style.display="block";
		document.getElementById("ShowEmployee2").style.display="block";
		document.getElementById("ShowGuest1").style.display="none";
		document.getElementById("ShowGuest2").style.display="none";
		document.getElementById("departmentId").disabled=true;
		document.getElementById("departmentId").value=null;
	}
	
}
function getGuest(){
	var gst=document.getElementById("guest").value;
	if(gst == "guest")
	{
		document.getElementById("employeeId").value=null;
		document.getElementById("ShowEmployee1").style.display="none";
		document.getElementById("ShowEmployee2").style.display="none";
		document.getElementById("ShowGuest1").style.display="block";
		document.getElementById("ShowGuest2").style.display="block";
		document.getElementById("departmentId").disabled=true;
		document.getElementById("departmentId").value=null;
	}
	
}
function disableBoth(){
	var both=document.getElementById("others").value;
	if(both == "others")
	{
		document.getElementById("departmentId").disabled=false;
		document.getElementById("employeeId").value=null;
		document.getElementById("guestId").value=null;
		document.getElementById("ShowEmployee1").style.display="none";
		document.getElementById("ShowGuest1").style.display="none";
		document.getElementById("ShowEmployee2").style.display="none";
		document.getElementById("ShowGuest2").style.display="none";
	}
}



function clearEmployee(){
	var gstId=document.getElementById("guestId").value;
	var empId=document.getElementById("employeeId").value;
	if(empId!=null && empId!="" && gstId==null || gstId=="")			
	{
	document.getElementById("departmentId").disabled=true;
	}
	else if(empId == null || empId=="" && gstId!=null && gstId!="")			
	{
		document.getElementById("departmentId").disabled=true;
	}
	else if(empId!= null && empId!="" && gstId!=null && gstId!="")			
	{
		document.getElementById("departmentId").disabled=true;
		}
	else
	{
		document.getElementById("departmentId").disabled=true;
	}
		
}
function clearDepartment(){
	var gstId=document.getElementById("guestId").value;
	var empId=document.getElementById("employeeId").value;
	if(empId!=null && empId!="" && gstId==null || gstId=="")			
	{
	document.getElementById("departmentId").disabled=true;
	}
	else if(empId == null || empId=="" && gstId!=null && gstId!="")			
	{
		document.getElementById("departmentId").disabled=true;
	}
	else if(empId!= null && empId!="" && gstId!=null && gstId!="")			
	{
		document.getElementById("departmentId").disabled=true;
		}
	else
	{
		document.getElementById("departmentId").disabled=false;
	}
}
</script>
<html:form action="/CreateUserInfo" method="post" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="addUserInfo" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="createUserForm" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.usermanagement.userinfo.usermanagement" /> <span class="Bredcrumbs">&gt;&gt;
				<bean:message key="knowledgepro.userinfo" />
				&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.userinfo" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellpadding="1" cellspacing="2">
						<tr>
							<td  align="left">
							<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td align="left">
							<div align="right">
							<html:button property="" styleClass="formbutton" value="Edit User Info"
										onclick="editUserInfo()"></html:button>
							</div>
							</td>
						</tr>
						<tr>
							<td class="heading" align="left">&nbsp;<bean:message key="knowledgepro.usermanagement.userinfo" /></td>
						</tr>
						<tr>
							<td width="100%" class="heading">
							<table width="98%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.usermanagement.userinfo.username" />:</div>
											</td>
											<td height="25" class="row-even" align="left">
											<html:text property="userName" size="15" maxlength="30"></html:text></td>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.usermanagement.userinfo.password" />:</div>
											</td>
											<td height="25" class="row-even" align="left">
											<html:password property="password" styleId="password" size="15" maxlength="10"></html:password>
											</td>
										</tr>
										
										
										<tr>
										
										<td height="25" class="row-odd" colspan="2">
											<div id="staff" align="right">Staff Type :</div>
										</td>
										<td width="28%" height="25" class="row-even"
												align="left" colspan="2">
												<html:radio property="staffType" styleId="employee" value="employee"  onchange="getEmployee()" onclick="getEmployee()">Employee</html:radio>
												<html:radio property="staffType" styleId="guest" value="guest" onchange="getGuest()" onclick="getGuest">Guest</html:radio>
												<html:radio property="staffType" styleId="others" value="others" onchange="disableBoth()" onclick="disableBoth()">Others</html:radio>
											</td>
										</tr>
										
										<tr>
										
										<td height="25" class="row-odd">
										<div id="ShowEmployee1"><div id="employeeId1" align="right">Employee :</div></div>
											<div id="ShowGuest1"><div id="guestId1" align="right">Guest :</div></div>
										</td>
										<td height="25" class="row-even" align="left">&nbsp;
										<div id="ShowEmployee2">
											<html:select property="employeeId" styleId="employeeId" styleClass="comboLarge" onchange="getTeachingStaff(this.value); clearDepartment(); clearEmployee()">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection property="employeeMap"
													label="value" value="key" />
											</html:select></div>
										
										<div id="ShowGuest2">
											<html:select property="guestId" styleId="guestId" styleClass="comboLarge" onchange="getTeachingStaff(this.value); clearDepartment(); clearEmployee()">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection property="guestMap"
													label="value" value="key" />
											</html:select></div>
										</td>
										
										<td height="25" class="row-odd">
											<div id="empl" align="right">Enable Attendance Entry :</div>
										</td>
										<td width="28%" height="25" class="row-even"
												align="left">
												<html:radio property="enableAttendance" value="true"  ><bean:message key="knowledgepro.yes" /></html:radio>
												<html:radio property="enableAttendance" value="false" ><bean:message key="knowledgepro.no" /></html:radio>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.usermanagement.userinfo.role" />:</div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;
											<html:select property="roleId" styleClass="comboLarge" >
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection property="roles" label="value" value="key" />
											</html:select></td>
										<td height="25" class="row-odd" >
										<div id="Department" align="right">Department :</div></td>
										<td height="25" class="row-even" align="left">&nbsp;
											<html:select property="departmentId" styleId="departmentId" styleClass="comboLarge" >
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:optionsCollection property="departmentMap" label="value" value="key" />
											</html:select>
										</td>
										</tr>
										<tr>
											<td width="23%" height="25" class="row-odd">
											<div align="right">&nbsp;<bean:message key="knowledgepro.admission.userinfo.teachingstaff" /></div>
											</td>
											<td width="28%" height="25" class="row-even"
												align="left">&nbsp;
												<html:radio property="teachingStaff" styleId="teachingStaff_1" value="true" ><bean:message key="knowledgepro.yes" /></html:radio>
												<html:radio property="teachingStaff" styleId="teachingStaff_2" value="false" ><bean:message key="knowledgepro.no" /></html:radio>
											</td>
											<td height="28" class="row-odd">
											<div align="right">&nbsp;<bean:message key="knowledgepro.admission.userinfo.restrictedstaff" /></div>
											</td>
											<td height="25" class="row-even">
											<html:radio property="restrictedUser" value="true" onmouseover="shows(this,'User will have Restricted access in Student Detail edit')" onmouseout="hides()"><bean:message key="knowledgepro.yes"/></html:radio>
											<html:radio property="restrictedUser" value="false" onmouseover="shows(this,'User will have Restricted access in Student Detail edit')" onmouseout="hides()"><bean:message key="knowledgepro.no"/></html:radio>
											<div id="messageBox">
	                              			<div id="contents"></div></div>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.userinfo.remark.entry"/></div>
											</td>
											<td width="28%" height="25" class="row-even"
												align="left">
												<html:radio property="isAddRemarks" value="true"  ><bean:message key="knowledgepro.yes" /></html:radio>
												<html:radio property="isAddRemarks" value="false" ><bean:message key="knowledgepro.no" /></html:radio>
											</td>

											<td height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.userinfo.view.remarks"/></div>
											</td>
											<td width="28%" height="25" class="row-even"
												align="left">
												<html:radio property="isViewRemarks" value="true"><bean:message key="knowledgepro.yes" /></html:radio>
												<html:radio property="isViewRemarks" value="false"><bean:message key="knowledgepro.no" /></html:radio>
											</td>
										</tr>
										
										<tr>
										<td height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.employee.active"/></div>
											</td>
											<td width="28%" height="25" class="row-even" align="left">
												<html:radio property="active" value="true"><bean:message key="knowledgepro.yes" /></html:radio>
												<html:radio property="active" value="false"><bean:message key="knowledgepro.no" /></html:radio>
											</td>
											<td height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.employee.multiple.login.allow"/></div>
											</td>
											<td width="28%" height="25" class="row-even" align="left" colspan="3">
												<html:radio property="multipleLoginAllow" value="true"><bean:message key="knowledgepro.yes" /></html:radio>
												<html:radio property="multipleLoginAllow" value="false"><bean:message key="knowledgepro.no" /></html:radio>
											</td>
										</tr>
										
										
										<!-- added by venkat -->
										
										<tr>
										<td class="row-odd"> 
									<div align="right">
									<bean:message key="knowledgepro.usermanagement.tilldate" /></div>
							  	</td>
								<td   class="row-even"  align="left">
									<html:text name="createUserForm" property="tillDate" styleId="tillDate" size="10" maxlength="16"/>
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'createUserForm',
												// input name
												'controlname' :'tillDate'
												});
										</script>
								</td> 
											<td class="row-odd"></td>
											<td class="row-even"></td>
										</tr>
										
										<!-- added by venkat -->
										
									<tr>
											<td colspan="4">
											<div align="center">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="47%" height="21"  align="right">
													<html:submit property="" styleClass="formbutton" value="Submit"
															onclick="addUsers()"></html:submit>
													</td>
													<td width="2%"></td>
													<td width="51%" height="45" align="left">
													<html:button property="" styleClass="formbutton" value="Reset"
																onclick="resetFieldAndErrMsgs()"></html:button>
													</td>
												</tr>
											</table></div>
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
							<td width="52%" valign="top" class="heading">
							</td>
						</tr>
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
			</td>
		</tr>
	</table>
</html:form>
<script  language="JavaScript">

 var both=document.getElementById("others").checked;
 		if(both)
 		{
 			disableBoth();
 		}
  var guest=document.getElementById("guest").checked;
 		if(guest)
 		{
 			getGuest();
 		}
 var emp=document.getElementById("employee").checked;
 		if(emp)
 		{
 			getEmployee();
 		}
 				

</script>