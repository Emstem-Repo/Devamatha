<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<head>
		<script language="JavaScript" src="js/admission/admissionform.js"></script>
</head>
<script type="text/javascript">
function goToHomePage(){
	document.getElementById("method").value = "initSmsToStudent";
	document.sendSmsToClassForm.submit();
}
function sendMess(){
	document.getElementById("method").value = "initSmsToStudent";
	document.sendSmsToClassForm.submit();
}
function getClasses(year) {
	getClassesByYearInMuliSelect("classMap", year, "selectedClasses",
			updateClasses);
}
function updateClasses(req) {
	updateOptionsFromMapForMultiSelect(req, "selectedClasses");
}
function limitText(limitField,limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} 
}

check1=false;
function checkedAll() {
		if (document.getElementById("checkbox").checked == false) {
			check1 = false;
	} else {
		check1 = true;
	}
	var name=document.getElementById("listSize").value;
	alert(name);
	for (var i = 0; i < name; i++) {
		var na="check_"+i;
		document.getElementById(na).checked = check1;
		}
	}

function sendMessToStudent(){
	document.getElementById("method").value = "sendSmsToStudentsList";
	document.sendSmsToClassForm.submit();
}
function sendMessToSingleStudent(){
	document.getElementById("method").value = "sendSmsToSingleStudent";
	document.sendSmsToClassForm.submit();
}
</script>

<html:form action="/sendSMS">
	<html:hidden property="method" value="getStudentDetails" styleId="method" />
	<html:hidden property="formName" value="sendSmsToClassForm" />
	<html:hidden property="listSize" styleId="listSize" />
	<html:hidden property="pageType" value="2" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.sms.student"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message key="knowledgepro.sms.student"/></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
							<div id="errorMessage">
							<FONT color="red"><html:errors/></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT>
							</div>
							</td>
						</tr>
						<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
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
							<td width="21%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory"></span><bean:message
								key="knowledgepro.admission.applicationnumber" />:</div>
							</td>
							<td width="29%" height="25" class="row-even"><label>
							<html:text property="applicationNumber"
								styleId="applicationNumber" size="9" maxlength="9" value=""></html:text>
							</label></td>
							<td width="21%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory"></span><bean:message
								key="knowledgepro.hostel.reservation.registerNo" /></div>
							</td>
							<td width="29%" height="25" class="row-even"><label>
							<html:text property="registerNumber"
								styleId="registerNumber" size="12" maxlength="12" value=""></html:text>
							</label></td>
							<td width="20%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col"/></div>
							</td>
							<td width="30%" class="row-even">
							<html:select property="applicationYear" styleClass="combo"
							styleId="applicationYear" onchange="getClasses(this.value)">
										<html:option value="">
										<bean:message key="knowledgepro.select" />-</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
										</html:select>
							</td>
							</tr>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
									</tr>
									<tr>
												<td width="5" background="images/left.gif"></td>
												<td valign="top">
												<table width="100%" cellspacing="1" cellpadding="2">
													<tr>
										<td width="100%" height="25" class="row-even" align="center" colspan="4">
										(OR)
										</td>
										</tr>
												</table>
												</td>
												<td width="5" height="30" background="images/right.gif"></td>
												</tr>
												<tr>
												<td width="5" background="images/left.gif"></td>
												<td valign="top">
												<table width="100%" cellspacing="1" cellpadding="2">
													<tr>
												<td width="50%" height="25" class="row-odd" colspan="3">
												<div align="right"><span class="Mandatory"></span><bean:message
												key="knowledgepro.attendance.class.col" /></div>
												</td>
												<td class="row-even" align="left" colspan="2"><html:select
												property="classId" styleId="selectedClasses"
												styleClass="combo">
												<html:option value="">--Select-- </html:option>
												<html:optionsCollection name="sendSmsToClassForm"
													property="classMap" label="value" value="key" />
												</html:select></td>
												</tr>
												</table>
												</td>
												<td width="5" height="30" background="images/right.gif"></td>
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
					</table>
					<div align="center">
					<table width="100%" height="106" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td width="100%" height="46" class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="center"><html:submit styleClass="formbutton">
										<bean:message key="knowledgepro.submit" />
									</html:submit><html:button property="" styleClass="formbutton"
										onclick="resetFieldAndErrMsgs()">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button></div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="10"></td>
						</tr>
						<logic:equal value="true" property="showDetails" name="sendSmsToClassForm">
						<logic:notEmpty property="studentDetail" name="sendSmsToClassForm">
						<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
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
											<td colspan="4" height="25" class="row-odd" align="center">Student Details</td>
										</tr>
										<tr>
											<td width="7%" height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.attendance.teacherReport.studentName" />:</div>
											</td>
											<td width="29%" class="row-odd">
											<div align="left"><bean:write name="sendSmsToClassForm" property="studentDetail.studentName"/> </div>
											</td>
											<td width="18%" class="row-odd">
											<div align="right"><bean:message
											key="knowledgepro.attendance.teacherReport.registerNo" />:</div>
											</td>
											<td width="9%" class="row-odd">
											<div align="left"><bean:write name="sendSmsToClassForm" property="studentDetail.registerNo"/></div>
											</td>
										</tr>
										<tr>
											<td width="7%" height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.fee.applicationno" />:</div>
											</td>
											<td width="29%" class="row-even">
											<div align="left"><bean:write name="sendSmsToClassForm" property="studentDetail.applicationNo"/> </div>
											</td>
											<td width="18%" class="row-odd">
											<div align="right"><bean:message
											key="curriculumSchemeForm.course" />:</div>
											</td>
											<td width="9%" class="row-even">
											<div align="left"><bean:write name="sendSmsToClassForm" property="studentDetail.courseName"/></div>
											</td>
										</tr>
										<tr>
											<td width="7%" height="25" class="row-odd">
											<div align="right"><bean:message key="knowledgepro.admission.className" />:</div>
											</td>
											<td width="29%" class="row-even">
											<div align="left"><bean:write name="sendSmsToClassForm" property="studentDetail.className"/> </div>
											</td>
											<td width="18%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
											key="knowledgepro.admin.mail.message" />:</div>
											</td>
											<td width="9%" class="row-even">
											<html:textarea style="width:260px;height:56px" styleId="message" property="message" name="sendSmsToClassForm" 
											onkeyup="limitText(this,100)" onkeydown="limitText(this,150)" ></html:textarea>
											</td>
										</tr>
										<tr>
											<td align="center" height="46" class="heading" colspan="4">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="45%" height="35">
													<div align="center"><html:button property="" styleClass="formbutton" value="Send Message" onclick="sendMessToSingleStudent()">
													</html:button>&nbsp;&nbsp; <html:button property="" styleClass="formbutton"
														onclick="goToHomePage()" value="Cancel">
													</html:button></div>
													</td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
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
						</logic:notEmpty>	
						</logic:equal>	
						<logic:equal value="true" property="showStudentList" name="sendSmsToClassForm">
						<logic:notEmpty property="studentList" name="sendSmsToClassForm">
						<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
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
									<tr class="row-odd">
										<td colspan="3" valign="middle"><div align="center"><span class="Mandatory">*</span><bean:message
											key="knowledgepro.admin.mail.message" />:&nbsp;&nbsp; 
											<html:textarea style="width:260px;height:56px" styleId="message" property="message" name="sendSmsToClassForm" 
											onkeyup="limitText(this,100)" onkeydown="limitText(this,150)" ></html:textarea></div></td>
									</tr>
										<tr>
											<td height="25" align="center" class="row-odd" width="20%">
											<bean:message
												key="knowledgepro.exam.blockUnblock.select" /> <input
												type="checkbox" name="checkbox" id="checkbox"
												onclick="checkedAll()" />
											</td>
											<td class="row-odd" width="40%"><bean:message
												key="knowledgepro.exam.blockUnblock.regNo" /></td>
											<td class="row-odd" width="40%"><bean:message
												key="knowledgepro.attendance.teacherReport.studentName" /></td>
										</tr>
										<nested:iterate id="stuList" property="studentList" name="sendSmsToClassForm" indexId="count">
										<%     
		                      				  String s1 = "check_"+count; 
		                     			 %> 
										<tr class="row-even">
											<td align="center" width="7%" height="25">
											<div align="center">
										  <nested:checkbox styleId="<%=s1%>" property="checked" ></nested:checkbox>
										</div>
											</td>
											<td align="center">
											<div align="left"><bean:write name="stuList" property="registerNo"/></div>
											</td>
											<td align="center">
											<div align="left"><bean:write name="stuList" property="studentName"/> </div>
											</td>
										</tr>
								       </nested:iterate>
										<tr>
											<td align="center" height="46" class="heading" colspan="4">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="45%" height="35">
													<div align="center"><html:button property="" styleClass="formbutton" value="Send Message" onclick="sendMessToStudent()">
													</html:button>&nbsp;&nbsp; <html:button property="" styleClass="formbutton"
														onclick="goToHomePage()" value="Cancel">
													</html:button></div>
													</td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
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
						</logic:notEmpty>	
						</logic:equal>				
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
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if (year.length != 0) {
	document.getElementById("applicationYear").value = year;
}
</script>
