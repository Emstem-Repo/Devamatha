<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<html>
<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" language="JavaScript">
	function cancelAction() {
		document.location.href = "hostelCheckin.do?method=initCheckin";
	}

	function reActivate() {
		var roomId = document.getElementById("roomId").value;
		var statusId = document.getElementById("statusId").value;
		document.location.href = "hostelCheckin.do?method=reActivateCheckinDetails&statusId="+statusId+"&roomId="+roomId;
	}
</script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<body>
<html:form action="/hostelCheckin" method="post">
<html:hidden property="formName" value="hostelCheckinForm" />
<html:hidden property="method" styleId="method"	value="submitHostelCheckinDetails" />
<html:hidden property="formId" name="hostelCheckinForm"	styleId="formId" />
<html:hidden property="statusId" name="hostelCheckinForm"
		styleId="statusId" />
<html:hidden property="roomTypeId" name="hostelCheckinForm"
		styleId="roomTypeId" />
<html:hidden property="roomId" name="hostelCheckinForm"
		styleId="roomId" />
	<html:hidden property="pageType" value="2" />

	<table width="99%" border="0">
		<tr>
			<td><span class="heading"> <a href="#" class="Bredcrumbs">
			<bean:message key="knowledgepro.hostel" /> </a><span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.hostel.checkin" />&gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif"></td>
					<td colspan="2" background="images/Tcenter.gif" class="body">
					<div align="left" class="heading_white"><bean:message
						key="knowledgepro.hostel.checkin" /></div>
					</td>
					<td width="11"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" colspan="2">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="17" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="17" class="heading"><bean:message
						key="knowledgepro.hostel.checkin.hostlerDetails" /></td>
					<td width="485" height="17" class="heading"><bean:message
						key="knowledgepro.hostel.checkin" /></td>
					<td height="17" valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td rowspan="2" valign="top" background="images/Tright_03_03.gif"></td>
					<td rowspan="2" valign="top" class="heading">
					<table width="98%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="550" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" height="29" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.checkin.name" /></div>
									</td>
									<td width="50%" height="25" class="row-even"><bean:write
										name="hostelCheckinForm" property="applicantName" /></td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.checkin.roomNo" /></div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="hostelCheckinForm" property="roomName" /></td>
								</tr>
								<tr>
									<td width="50%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.checkin.roomType" /></div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="hostelCheckinForm" property="roomType" /></td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.checkin.bedNo" /></div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="hostelCheckinForm" property="bedNo" /></td>
								</tr>
							</table>
							</td>
							<td background="images/right.gif" width="10" height="29"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td height="108" valign="top" class="heading">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="10"><img src="images/01.gif" width="5"
								height="5"></td>
							<td width="462" background="images/02.gif"></td>
							<td width="9"><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="10" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="25%" class="row-odd">
									<div align="right"><span class="Mandatory">* </span><bean:message
										key="knowledgepro.hostel.checkin.checkinDate" /></div>
									</td>
									<td height="25" colspan="3" class="row-even"><html:text
										name="hostelCheckinForm" property="txnDate" styleId="txnDate"
										size="10" maxlength="10" /> 
										<script language="JavaScript" type="text/javascript">
											new tcal( {
													// form name
														'formname' :'hostelCheckinForm',
													// input name
														'controlname' :'txnDate'
														});
										</script>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.checkin.fingerPrintId" /></div>
									</td>
									<td width="29%" height="25" class="row-even"><span
										class="star"> <html:text property="fingerPrintId"
										styleClass="TextBox" styleId="fingerPrintId" size="10"
										maxlength="10" name="hostelCheckinForm" /> </span></td>
								</tr>
								<tr>
									<td height="25" valign="top" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.checkin.comments" /></div>
									</td>
									<td height="25" class="row-even"><html:textarea
										property="comments" styleClass="TextBox" styleId="commentsId"
										name="hostelCheckinForm" cols="15" rows="3"></html:textarea></td>
								</tr>
							</table>
							</td>
							<td background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td rowspan="2" valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="6"></td>
				</tr>
				<tr>
					<td height="13" valign="top" background="images/Tright_03_03.gif"></td>
					<td width="458" height="20" class="heading"><bean:message
						key="knowledgepro.hostel.checkin.fecility" /></td>
					<td height="13" class="heading"><bean:message
						key="knowledgepro.hostel.checkin.remarks" /></td>
					<td height="13" valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="52" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
					<table width="98%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="489" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" height="29" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
								<logic:notEmpty name="hostelCheckinForm" property="fecilityList">
									<nested:iterate id="facilityListId" name="hostelCheckinForm"
										property="fecilityList" indexId="count">
										<c:if test="${count%3 == 0}">
											<tr>
										</c:if>
										<td width="20%" class="row-odd" align="right"><nested:write
											name="facilityListId" property="name" /> :</td>
										<td width="7%" height="25" class="row-even">
										<div align="left">
										<input type="hidden" name="facilityList[<c:out value='${count}'/>].tempChecked"
																	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='facilityListId' property='tempChecked'/>" />
										
										<input type="checkbox" name="facilityList[<c:out value='${count}'/>].checked"
																	id="<c:out value='${count}'/>"
																	value="<nested:write name='facilityListId' property='checked'/>" />
										</div>
										<script type="text/javascript">
											var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
											if(studentId == "true") {
												document.getElementById("<c:out value='${count}'/>").checked = true;
											}else{
												document.getElementById("<c:out value='${count}'/>").checked = false;
												}		
										</script>	
										</td>
									</nested:iterate>
								</logic:notEmpty>
								</tr>
							</table>
							</td>
							<td background="images/right.gif" width="10" height="29"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top">
					<table width="100%" border="0" align="right" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td height="29" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="7%" valign="top" class="row-even"><label>
									<html:textarea property="remarks" name="hostelCheckinForm"
										styleId="remarksId" cols="35" rows="2"></html:textarea>
									<div align="center"></div>
									</label></td>
								</tr>
							</table>
							</td>
							<td background="images/right.gif" width="10" height="29"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%">
							<div align="right"><input name="button2" type="submit"
								class="formbutton" value="Submit" /></div>
							</td>
							<td width="2%"></td>
							<td width="49%">
								<html:cancel styleClass="formbutton"></html:cancel>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td colspan="2" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>
</html>
