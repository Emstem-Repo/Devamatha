<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript">
function getRoomsOfHostel(id){
	getRoomsByHostel("roomTypeMap",id,"roomtype",updateHostelRooms);
	
}


function updateHostelRooms(req) {
	updateOptionsFromMap(req,"roomtype","- Select -");
}

 function resetAttReport()
 {	
	 document.location.href = "viewRequisitions.do?method=initViewRequisitions";
	 		document.getElementById("startDateid").value ="";
		document.getElementById("endDateid").value ="";	

	 	resetErrMsgs();
 }
</script>
<html:form action="viewRequisitions" method="post">
	<html:hidden property="method" styleId="method"
		value="submitViewRequisitions" />
	<html:hidden property="formName" value="viewRequisitionsForm" />
	<html:hidden property="pageType" value="1" />

	<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">


	<link href="../css/styles.css" rel="stylesheet" type="text/css">

	<style type="text/css">
<!--
body {
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
	margin-top: 5px;
}
-->
</style>
	</head>

	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.hostel" /><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.viewRequisitions" /> &gt;&gt;</span></span></td>

		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td colspan="2" background="images/Tcenter.gif"
						class="heading_white"><bean:message
						key="knowledgepro.hostel.viewRequisitions" /></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>

					<td height="42" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
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
									<td width="17%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.hostel.entry.hostel.name" /></div>
									</td>
									<td width="17%" height="25" class="row-even"><html:select
										property="hostelId" styleClass="comboLarge" styleId="hostelId"
										onchange="getRoomsOfHostel(this.value)">

										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>

										<logic:notEmpty property="hostelList"
											name="viewRequisitionsForm">
											<html:optionsCollection property="hostelList"
												name="viewRequisitionsForm" label="name" value="id" />
										</logic:notEmpty>



									</html:select></td>
									<td width="17%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.roomtype" /></div>
									</td>
									<td width="17%" height="25" class="row-even"><html:select
										property="roomtype" styleId="roomtype" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${viewRequisitionsForm.hostelId != null && viewRequisitionsForm.hostelId != ''}">
											<c:set var="roomMap"
												value="${baseActionForm.collectionMap['roomTypeMap']}" />
											<c:if test="${roomTypeMap != null}">
												<html:optionsCollection name="roomTypeMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</html:select></td>
									<td width="17%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.viewRequisitions" /> Of:</div>
									</td>
									<td width="15%" class="row-even">
										<html:select property="viewRequisitionsOf" styleId="viewRequisitionsOf" styleClass="combo">
											<html:option value="Student">Student</html:option>	
											<html:option value="Staff">Staff</html:option>
										</html:select>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.applicationform.fromdt.label" /></div>
									</td>
									<td width="22%" class="row-even"><html:text
										name="viewRequisitionsForm" property="startDate"
										styleId="startDateid" size="16" maxlength="16" /> <script
										language="JavaScript">
							new tcal( {
								// form name
								'formname' :'viewRequisitionsForm',
								// input name
								'controlname' :'startDate'
							});
						</script></td>

									<td width="14%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.feepays.enddate" /></div>
									</td>
									<td class="row-even"><html:text
										name="viewRequisitionsForm" property="endDate"
										styleId="endDateid" size="16" maxlength="16" /> <script
										language="JavaScript">
							new tcal( {
								// form name
								'formname' :'viewRequisitionsForm',
								// input name
								'controlname' :'endDate'
							});
						</script></td>
									<td width="17%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.status" /></div>
									</td>
									<td width="15%" class="row-even"><html:select
										property="status" styleId="status" styleClass="combo">

										<html:option value="Select">Select</html:option>
										<html:option value="All">All</html:option>
										<html:option value="Approved">Approved</html:option>
										<html:option value="Pending">Pending</html:option>
										<html:option value="Rejected">Rejected</html:option>

									</html:select></td>
								</tr>

							</table>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr></tr>

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
					<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleClass="formbutton"
								onclick="getNames()">
								<bean:message key="knowledgepro.submit" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
								styleClass="formbutton" onclick="resetAttReport()">
								<bean:message key="knowledgepro.admin.reset" />
							</html:button></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td colspan="2" background="images/TcenterD.gif"></td>

					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>


