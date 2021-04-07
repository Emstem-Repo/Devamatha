<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" >
function getperStates(countryId) {
	getStatesByCountry("permAddrStateMap",countryId,"perstates",updateperStates);
}

function updateperStates(req) {
	updateOptionsFromMapWithOther(req,"perstates","- Select -");
}
function getcurStates(countryId) {
	getStatesByCountry("tempAddrStateMap",countryId,"curstates",updatecurStates);
}

function updatecurStates(req) {
	updateOptionsFromMapWithOther(req,"curstates","- Select -");
}

function onLoadAddrCheck()
{
	var sameAddr= document.getElementById("sameAddr").checked;

	if(sameAddr==true){
		disableTempAddress();
	}
	if(sameAddr==false){
		enableTempAddress();
	}	
}
function disableTempAddress()
{
	document.getElementById("currLabel").style.display="none";
	document.getElementById("currTable").style.display="none";
}
function enableTempAddress()
{
	document.getElementById("currLabel").style.display="block";
	document.getElementById("currTable").style.display="block";
	
}
function showOther(srcid,destid){
	 document.getElementById(destid).style.display = "block";
}
function hideOther(id,destid){
	 document.getElementById(destid).style.display = "none";
}
function funcOtherShowHide(id,destid){
	var selectedVal=document.getElementById(id).value;
	if(selectedVal=="Other"){
		showOther(id,destid);
	}else{
		hideOther(id,destid);
	}
}

function editUserInfo() {
	document.location.href = "UserInfo.do?method=editUserInfo";
}

function updateUsers(){
	document.getElementById("method").value = "updateUserInfo";
	document.userInfoForm.submit();
}
function addUsers(){
	document.getElementById("method").value = "addUserInfo";
	document.userInfoForm.submit();
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

</script>
<html:form action="/UserInfo" method="post" enctype="multipart/form-data">
	<c:choose>
		<c:when test="${userOperation == 'update'}">
			<html:hidden property="method" styleId="method"
				value="updateUserInfo" />
			<html:hidden property="emplId"/>
			<html:hidden property="id"/>
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addUserInfo" />
		</c:otherwise>
	</c:choose>
	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="userInfoForm" />
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
					<div align="center">
					<table width="100%" border="0" cellpadding="1" cellspacing="2">
						<tr>
							<td colspan="2" align="left">
							<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="left">
							<div align="right">
							<html:button property="" styleClass="formbutton" value="Edit User Info"
										onclick="editUserInfo()"></html:button>
							</div>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message key="knowledgepro.usermanagement.userinfo" /></td>
						</tr>
						<tr>
							<td width="48%" class="heading">
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
											<td width="45%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.name" />:</div>
											</td>
											<td width="55%" height="25" class="row-even" align="left">
											<html:text property="firstName" size="4" maxlength="30"></html:text>
											<html:text property="middleName" size="7" maxlength="20"></html:text>
											<html:text property="lastName" size="4" maxlength="30"></html:text>
											</td>
										</tr>
										<tr>
											<td width="45%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message key="admissionFormForm.fatherName" />:</div>
											</td>
											<td width="55%" height="25" class="row-even" align="left">
											<html:text property="fatherName" size="15" maxlength="30"></html:text></td>
										</tr>
										<tr>
											<td width="45%" height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.usermanagement.userinfo.username" />:</div>
											</td>
											<td width="55%" height="25" class="row-even" align="left">
											<html:text property="userName" size="15" maxlength="30"></html:text></td>
										</tr>
										<tr>
											<td width="45%" height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.usermanagement.userinfo.password" />:</div>
											</td>
											<td width="55%" height="25" class="row-even" align="left">
											<html:password property="password" styleId="password" size="15" maxlength="10"></html:password>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message key="admissionFormForm.emailId" />:</div>
											</td>
											<td height="25" class="row-even" align="left">
											<html:text property="emailId" size="15" maxlength="50"></html:text><br />
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
							<table width="97%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="99%" cellspacing="1" cellpadding="2">
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionForm.studentinfo.dob.label" /></div>
											</td>
											<td height="25" class="row-even" align="left">
											<html:text property="dateOfBirth" styleId="dateOfBirth" size="11" maxlength="11"></html:text>
											<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'userInfoForm',
													// input name
													'controlname' :'dateOfBirth'
												});
											</script>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.usermanagement.userinfo.dateofjoining" />:</div>
											</td>
											<td height="25" class="row-even" align="left">
											<html:text property="dateOfJoining" styleId="dateOfJoining" size="11" maxlength="11"></html:text>
											<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'userInfoForm',
													// input name
													'controlname' :'dateOfJoining'
												});
											</script></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.bloodgroup.label" /></div>
											</td>
											<td height="25" class="row-even" align="left">
											<html:select property="bloodGroup" styleClass="combo" styleId="bloodGroup">
												<option value=""><bean:message key="knowledgepro.admin.select" /></option>
												<html:option value="O+ve"><bean:message key="knowledgepro.admission.report.opositive"/></html:option>
												<html:option value="A+ve"><bean:message key="knowledgepro.admission.report.apositive"/></html:option>
												<html:option value="B+ve"><bean:message key="knowledgepro.admission.report.bpositive"/></html:option>
												<html:option value="AB+ve"><bean:message key="knowledgepro.admission.report.abpositive"/></html:option>
												<html:option value="O-ve"><bean:message key="knowledgepro.admission.report.onegitive"/></html:option>
												<html:option value="A-ve"><bean:message key="knowledgepro.admission.report.anegitive"/></html:option>
												<html:option value="B-ve"><bean:message key="knowledgepro.admission.report.bnegitive"/></html:option>
												<html:option value="AB-ve"><bean:message key="knowledgepro.admission.report.abnegitive"/></html:option>
												<html:option value="Not Known"><bean:message key="knowledgepro.admission.report.unknown"/></html:option>
											</html:select>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.phone.label" /></div>
											</td>
											<td height="25" class="row-even" align="left">
											<html:text property="phone1" size="15" maxlength="30"></html:text>
											<!-- <html:text property="phone2" size="4" maxlength="6"></html:text>
											<html:text property="phone3" size="6" maxlength="10"></html:text> -->
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.mobile.label" /></div>
											</td>
											<td height="25" class="row-even" align="left">
											<html:text property="mobile1" size="15" maxlength="30"></html:text>
											<!--<html:text property="mobile2" size="4" maxlength="6"></html:text>
											<html:text property="mobile3" size="6" maxlength="10"></html:text> -->
											</td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="10" height="3"></td>
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
							<td colspan="2">
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
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.usermanagement.userinfo.department" />:</div>
											</td>
											<td width="25%" height="25" class="row-even" align="left">&nbsp;<html:select
												property="departmentId" styleClass="comboExtraLarge">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection property="department"
													label="value" value="key" />
											</html:select></td>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.usermanagement.userinfo.designation" />:</div>
											</td>
										 	<td width="25%" height="25" class="row-even" align="left">
										 	<html:select property="designationId" styleClass="combo">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:optionsCollection property="designation" label="value" value="key" />
											</html:select>
											</td>
											<td height="25" class="row-odd">&nbsp;</td>
											<td height="25" class="row-even">&nbsp;</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.usermanagement.userinfo.role" />:</div>
											</td>
											<td height="25" class="row-even" align="left">&nbsp;<html:select
												property="roleId" styleClass="combo">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection property="roles"
													label="value" value="key" />
											</html:select></td>
											<td width="23%" height="25" class="row-odd">
											<div align="right">&nbsp;<bean:message key="knowledgepro.admission.userinfo.teachingstaff" /></div>
											</td>
											<td width="28%" height="25" class="row-even"
												align="left">&nbsp;
												<html:radio property="teachingStaff" value="true"><bean:message key="knowledgepro.yes" /></html:radio>
												<html:radio property="teachingStaff" value="false"><bean:message key="knowledgepro.no" /></html:radio>
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
											<td height="25" class="row-odd"><div align="right">Photo</div></td>
											<td height="25">
												<nested:file property="empPhoto" styleId="empPhoto"></nested:file>
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
						<tr>
							<td colspan="2" class="heading" align="left">&nbsp;<bean:message
								key="admissionForm.studentinfo.permAddr.label" /></td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="91" valign="top">
									<table width="100%" height="90" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="25%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="25%" height="25" class="row-even" align="left">&nbsp;<html:text property="permanentaddrLine1" styleId="permanentaddrLine1" size="15" maxlength="100" /></td>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td width="25%" height="25" class="row-even" align="left">
											<html:select property="permanentCountryId" onchange="getperStates(this.value)" styleClass="combo">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection property="country" label="value" value="key" />
											</html:select>
											</td>
										</tr>
										<tr class="row-even">
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;<html:text property="permanentaddrLine2" styleId="permanentaddrLine2" size="15" maxlength="100" /></td>
											<td  height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.state" /></div>
											</td>
											<td class="row-white" align="left">
											<%String dynaStyle=""; %>
											<logic:equal value="Other" property="permanentStateId" name="userInfoForm">
												<%dynaStyle="display:block;"; %>
											</logic:equal>
											<logic:notEqual value="Other" property="permanentStateId" name="userInfoForm">
												<%dynaStyle="display:none;"; %>
											</logic:notEqual>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            					<tr>
                            						<td><html:select property="permanentStateId" styleClass="combo" styleId="perstates" onchange="funcOtherShowHide('perstates','otherPermAddrState')">
														<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<c:choose>
													<c:when test="${userOperation == 'update'}">
													<logic:notEmpty name ="permAddrStateMap" >
														<html:optionsCollection name="permAddrStateMap" label="value"
															value="key" />
													</logic:notEmpty>
													</c:when>
												<c:otherwise>
														<c:if test="${userInfoForm.permanentCountryId != null && userInfoForm.permanentCountryId != ''}">
															<c:set var="permAddrStateMap" value="${baseActionForm.collectionMap['permAddrStateMap']}" />
															<c:if test="${permAddrStateMap != null}">
																<html:optionsCollection name="permAddrStateMap" label="value" value="key" />
															</c:if>
														</c:if>

												</c:otherwise>
												</c:choose>
														<html:option value="Other"><bean:message key="knowledgepro.admin.Other" /></html:option>
														</html:select>
													</td>
												</tr>
											<tr><td><html:text property="permanentOtherState" size="10" maxlength="30" styleId="otherPermAddrState" style="<%=dynaStyle %>"></html:text></td></tr>
											</table>
											</td>
										</tr>
										<tr class="row-even">
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.city" />:</div>
											</td>
											<td class="row-even" height="25" align="left">&nbsp;<html:text property="permanentCity" styleId="permanentCity" size="15" maxlength="50" /></td>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.usermanagement.userinfo.pincode" /></div>
											</td>
											<td class="row-even" align="left">&nbsp;<html:text property="permanentPinCode" styleId="permanentPinCode" size="15" maxlength="10" /></td>
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
                  			<td colspan="2" class="heading"><span class="heading">&nbsp;&nbsp;<bean:message key="knowledgepro.usermanagement.userinfo.addresscheck" />
                      			<html:radio property="sameTempAddr" styleId="sameAddr" value="true" onclick="disableTempAddress()"><bean:message key="knowledgepro.yes" /></html:radio>
								<html:radio property="sameTempAddr" styleId="DiffAddr" value="false" onclick="enableTempAddress()"><bean:message key="knowledgepro.no" /></html:radio></span>
							</td>
                		</tr>
						<tr>
							<td colspan="2" align="left">&nbsp;<div class="heading" id="currLabel"><bean:message
								key="admissionForm.studentinfo.currAddr.label" /></div></td>
						</tr>
						<tr>
							<td colspan="2" class="heading"><div id="currTable">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0" >
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="91" valign="top">
									<table width="100%" height="90" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-white">
											<td width="25% height="25" class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.addrs1.label" /></div>
											</td>
											<td width="25%" height="25" class="row-even" align="left">&nbsp;<html:text property="currentaddrLine1" styleId="currentaddrLine1" size="15" maxlength="100" /></td>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.admin.country" /></div>
											</td>
											<td width="25%" height="25" class="row-even" align="left">
											<html:select property="currentCountryId" onchange="getcurStates(this.value)" styleClass="combo">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection property="country" label="value" value="key" />
											</html:select>
											</td>
										</tr>
										<tr class="row-even">
											<td height="25" height="25" class="row-odd">
											<div align="right"><bean:message key="admissionForm.studentinfo.addrs2.label" /></div>
											</td>
											<td class="row-white" align="left">&nbsp;<html:text property="currentaddrLine2" styleId="currentaddrLine2" size="15" maxlength="100" /></td>
											<td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.state" /></div></td>
											<td class="row-white" align="left">
											<logic:equal value="Other" property="currentStateId" name="userInfoForm">
												<%dynaStyle="display:block;"; %>
											</logic:equal>
											<logic:notEqual value="Other" property="currentStateId" name="userInfoForm">
												<%dynaStyle="display:none;"; %>
											</logic:notEqual>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            					<tr>
                            						<td><html:select property="currentStateId" styleClass="combo" styleId="curstates" onchange="funcOtherShowHide('curstates','otherTempAddrState')">
														<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<c:choose>
												<c:when test="${userOperation == 'update'}">
													<logic:notEmpty name = "tempAddrStateMap">
													<html:optionsCollection name="tempAddrStateMap" label="value" value="key" />
													</logic:notEmpty>
												</c:when>
												<c:otherwise>
														<c:if test="${userInfoForm.currentCountryId != null && userInfoForm.currentCountryId != ''}">
															<c:set var="tempAddrStateMap" value="${baseActionForm.collectionMap['tempAddrStateMap']}" />
															<c:if test="${tempAddrStateMap != null}">
																<html:optionsCollection name="tempAddrStateMap" label="value" value="key" />
															</c:if>
														</c:if>
												</c:otherwise>
												</c:choose>
														<html:option value="Other"><bean:message key="knowledgepro.admin.Other" /></html:option>
														</html:select>
													</td>
												</tr>
												<tr><td><html:text property="currentOtherState" size="10" maxlength="30" styleId="otherTempAddrState" style="<%=dynaStyle %>"></html:text></td></tr>
												</table>
											</td>
										</tr>
										<tr class="row-even">
											<td height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.admin.city" />:</div>
											</td>
											<td class="row-even" align="left">&nbsp;<html:text property="currentCity" styleId="currentCity" size="15" maxlength="50" /></td>
											<td height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.usermanagement.userinfo.pincode" />:</div>
											</td>
											<td class="row-even" align="left">&nbsp;<html:text property="currentPinCode" styleId="currentPinCode" size="15" maxlength="10" /></td>
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
							</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="47%" height="21">
									<c:choose>
									<c:when test="${userOperation == 'update'}">
										<div align="right"><html:button property="" styleClass="formbutton" value="Update"
											onclick="updateUsers()"></html:button></div>
									</c:when>
										<c:otherwise>
										<div align="right"><html:button property="" styleClass="formbutton" value="Submit"
											onclick="addUsers()"></html:button></div>
									</c:otherwise>
								</c:choose>
									</td>
									<td width="2%"></td>
									<td width="51%" height="45" align="left"><c:choose>
										<c:when test="${userOperation == 'update'}">
											<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton" value="Reset"
												onclick="resetFieldAndErrMsgs()"></html:button>
										</c:otherwise>
									</c:choose></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</table>
					</div>
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