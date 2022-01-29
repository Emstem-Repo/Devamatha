<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.utilities.CommonUtil"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="java.util.Date"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<LINK REL=StyleSheet HREF="css/styles.css" TYPE="text/css">
<script language="JavaScript" src="js/admissionform.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">

	function addinvigilationduty(){
		document.getElementById("method").value="addInvigilationDutySetting";
		document.invigilationDutySettingForm.submit();
	}
	function closeReset(){
		document.location.href = "ExamInvigilationDutySetting.do?method=initInvigilationDuty";
	}
	function editInvigilationDuty(id) {
		document.location.href = "ExamInvigilationDutySetting.do?method=editInvigilationDuty&id="+id;
		}
	function updateInvigilationDuty() {
		document.getElementById("method").value="updateInvigilationDuty";
		document.invigilationDutySettingForm.submit();
		}
    function deleteInvigilationDuty(id) {
		  	deleteConfirm = confirm("Are you sure you want to delete this entry?");
			if (deleteConfirm) {
				document.location.href = "ExamInvigilationDutySetting.do?method=deleteInvigilationDuty&id="+id;
			}
		}

    function reActivate() {
    	document.location.href="ExamInvigilationDutySetting.do?method=reActivateInvigilationDuty";
    }
</script>
</head>
<body>
<table width="100%" border="0">
	<html:form action="/ExamInvigilationDutySetting"
		enctype="multipart/form-data">
		<html:hidden property="pageType" value="1" />
		<html:hidden property="method" value="" styleId="method" />
		<html:hidden property="formName" value="invigilationDutySettingForm" />
		<input type="hidden" id="count" />
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.invigilation.duty.setting" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29" /></td>
					<td width="1271" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.exam.invigilation.duty.setting" /></strong></div>
					</td>
					<td width="15"><img src="images/Tright_1_01.gif" width="9"
						height="29" /></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" height="50" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6" align="left">
							<div align="right" style="color: red"><span
								class='MandatoryMark'>* Mandatory fields</span></div>
							<div id="err"
								style="color: red; font-family: arial; font-size: 11px;"></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>

						<tr>
							<td valign="top" class="news" colspan="3">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.hlAdmission.location" />:</div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:select property="workLocationId"
												styleClass="comboLarge" styleId="workLocationId">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection name="locationList"
													label="empLocationName" value="empLocationId" />
											</html:select></span></td>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.invigilation.duty.endmid" />:</div>
											</td>
											<td width="25%" height="25" class="row-even"><html:select
												property="endMid" styleId="endMid" styleClass="combo">
												<html:option value="">
													<bean:message key="knowledgepro.select" />
												</html:option>
												<html:option value="E">End Sem</html:option>
												<html:option value="M">Mid Sem</html:option>
											</html:select></td>
										</tr>
										<tr>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.invigilation.noof.session" />:</div>
											</td>
											<td width="25%" height="17" class="row-even" align="left">
											<nested:radio property="noOfSessionsOnSameDay" value="1">One</nested:radio>
											<nested:radio property="noOfSessionsOnSameDay" value="2">Two</nested:radio>
											</td>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.invigilation.max.student.perteacher" />:</div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text
												property="maxNoOfStudentsPerTeacher"
												styleId="maxNoOfStudentsPerTeacher" size="20" maxlength="3"
												onkeypress="return isNumberKey(event)" /> </span></td>
										</tr>
										<tr>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.invigilation.noof.room.perreliver" />:</div>
											</td>
											<td width="25%" height="25" class="row-even"><span
												class="star"> <html:text
												property="noOfRoomsPerReliever"
												styleId="noOfRoomsPerReliever" size="20" maxlength="3"
												onkeypress="return isNumberKey(event)" /> </span></td>
											<td width="25%" height="25" class="row-odd"></td>
											<td width="25%" height="25" class="row-even"></td>
										</tr>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
								<tr>
								</tr>
							</table>
							</td>
						</tr>

					</table>
					</td>
				</tr>

				<tr>
					<td height="45" colspan="4">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td align="center" colspan="6"><c:choose>
										<c:when test="${invDuty == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" onclick="updateInvigilationDuty()"></html:submit>&nbsp;&nbsp;
					<html:button property="" styleClass="formbutton" value="Close"
												onclick="closeReset()"></html:button>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" onclick="addinvigilationduty()"></html:submit>&nbsp;&nbsp;
					<html:button property="" styleClass="formbutton" value="Reset"
												onclick="closeReset()"></html:button>
										</c:otherwise>
									</c:choose></td>
								</tr>
							</table>
							</td>
						</tr>

					</table>
					</td>
				</tr>



				<logic:notEmpty name="invigilationDutySettingForm"
					property="invigilationList">
					<tr>
						<td height="45" colspan="4">
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
										<td height="25" class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.slno" /></div>
										</td>
										<td height="25" class="row-odd" align="center"><bean:message
											key="knowledgepro.hlAdmission.location" /></td>
										<td height="25" class="row-odd" align="center"><bean:message
											key="knowledgepro.exam.invigilation.duty.endmid" /></td>
										<td height="25" class="row-odd" align="center"><bean:message
											key="knowledgepro.exam.invigilation.noof.session" /></td>
										<td height="25" class="row-odd" align="center"><bean:message
											key="knowledgepro.exam.invigilation.max.student.perteacher" /></td>
										<td height="25" class="row-odd" align="center"><bean:message
											key="knowledgepro.exam.invigilation.noof.room.perreliver" /></td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.edit" /></div>
										</td>
										<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.delete" /></div>
										</td>
									</tr>
									<c:set var="temp" value="0" />
									<logic:iterate id="CME" name="invigilationDutySettingForm"
										property="invigilationList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td width="5%" height="25" class="row-even">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td width="10%" height="25" class="row-even" align="center"><bean:write
														name="CME" property="locationName" /></td>
													<td width="5%" height="25" class="row-even" align="center"><bean:write
														name="CME" property="endMid" /></td>
													<td width="10%" height="25" class="row-even" align="center"><bean:write
														name="CME" property="noOfSessionsOnSameDay" /></td>
													<td width="10%" height="25" class="row-even" align="center"><bean:write
														name="CME" property="maxNoOfStudentsPerTeacher" /></td>
													<td width="10%" height="25" class="row-even" align="center"><bean:write
														name="CME" property="noOfRoomsPerReliever" /></td>
													<td width="5%" height="25" class="row-even">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editInvigilationDuty('<bean:write name="CME" property="id"/>')"></div>
													</td>
													<td width="5%" height="25" class="row-even">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deleteInvigilationDuty('<bean:write name="CME" property="id"/>')"></div>
													</td>
												</tr>
												<c:set var="temp" value="1" />
											</c:when>
											<c:otherwise>
												<tr>
													<td width="5%" height="25" class="row-white">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td width="10%" height="25" class="row-white"
														align="center"><bean:write name="CME"
														property="locationName" /></td>
													<td width="5%" height="25" class="row-white" align="center"><bean:write
														name="CME" property="endMid" /></td>
													<td width="10%" height="25" class="row-white"
														align="center"><bean:write name="CME"
														property="noOfSessionsOnSameDay" /></td>
													<td width="10%" height="25" class="row-white"
														align="center"><bean:write name="CME"
														property="maxNoOfStudentsPerTeacher" /></td>
													<td width="10%" height="25" class="row-white"
														align="center"><bean:write name="CME"
														property="noOfRoomsPerReliever" /></td>
													<td width="5%" height="25" class="row-white">
													<div align="center"><img src="images/edit_icon.gif"
														width="16" height="18" style="cursor: pointer"
														onclick="editInvigilationDuty('<bean:write name="CME" property="id"/>')"></div>
													</td>
													<td width="5%" height="25" class="row-white">
													<div align="center"><img src="images/delete_icon.gif"
														width="16" height="16" style="cursor: pointer"
														onclick="deleteInvigilationDuty('<bean:write name="CME" property="id"/>')"></div>
													</td>
												</tr>
												<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
									</logic:iterate>


								</table>
								</td>
								<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5"
									height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
				</logic:notEmpty>

				<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>


			</table>
	</html:form>
</table>
</body>
</html>
