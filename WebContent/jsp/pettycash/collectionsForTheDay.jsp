<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<script type="text/javascript">
function hideStudentGroup() {
		document.getElementById("stPerGroup").style.display = "none";
		document.getElementById("otherName").style.display = "none";
}
function resetMessages() {


	document.location.href = "collectionsDay.do?method=initcollectionsReport";
	resetErrMsgs();
	//resetFieldAndErrMsgs();
	document.getElementById("allUsers").checked=false;
	document.getElementById("currentUser").checked=false;
	document.getElementById("otherUser").checked=false;
	document.getElementById("otherName").value="";
	document.getElementById("all").checked=false;
	document.getElementById("cancel").checked=false;
	document.getElementById("receiptno").checked=false;
	document.getElementById("studentno").checked=false;
	document.getElementById("appiNo").value="";




	
	

}

		

</script>
<body>
<html:form action="/collectionsDay">
	<html:hidden property="method" styleId="method"	value="SubmitCollections" />
	<html:hidden property="formName" value="collectionsReportForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.pettycash" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.petticash.collections" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.petticash.collections" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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

								<tr>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td height="25" class="row-odd">
											<div align="left"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.attendance.leavemodify.fromdate" /></div>
											</td>
											<td width="26%" height="25" class="row-odd"><html:text
												name="collectionsReportForm" property="startDate"
												styleId="startDate" size="16" maxlength="16" /> <script
												language="JavaScript">
												new tcal( {
													// form name
													'formname' :'collectionsReportForm',
													// input name
													'controlname' :'startDate'
												});
											</script></td>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.attendance.leavemodify.todate" /></div>
											</td>
											<td width="26%" height="25" class="row-odd"><html:text
												name="collectionsReportForm" property="endDate"
												styleId="endDate" size="16" maxlength="16" /> <script
												language="JavaScript">
												new tcal( {
													// form name
													'formname' :'collectionsReportForm',
													// input name
													'controlname' :'endDate'
												});
											</script></td>
											<td class="row-odd"></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.petticash.select" /></div>
											</td>
											<td colspan="4"class="row-even"><html:radio
												styleId="all" property="allORCancel" value="false">
												<bean:message key="knowledgepro.petticash.all" />
											</html:radio> &nbsp;
											<html:radio
												property="allORCancel" value="true" styleId="cancel">
												<bean:message key="knowledgepro.petticash.cancel" />
											</html:radio>
											</td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.petticash.uType" /></div>
											</td>
											<td width="20%" height="25" class="row-even"><html:radio
												property="userType" value="currentuser" styleId="currentUser"
												onclick="hideStudentGroup()">
												<bean:message key="knowledgepro.petticash.currentUser" />
											</html:radio></td>
											<td width="20%" height="25" class="row-even"><html:radio
												property="userType" value="allusers" styleId="allUsers"
												onclick="hideStudentGroup()">
												<bean:message key="knowledgepro.petticash.allUsers" />
											</html:radio></td>
											<td width="20%" height="25" class="row-even"><html:radio
												property="userType" value="otherUser" styleId="otherUser"
												onclick="showStudentGroup()">
												<bean:message key="knowledgepro.petticash.otherUser" />
											</html:radio></td>
											<td height="25" class="row-even" >
							<div id="stPerGroup"><span class="Mandatory">*</span>Other Name:</div>
											<html:text name="collectionsReportForm" property="otherName"
												styleClass="body" styleId="otherName" size="17" /></td>
												</tr>
										<tr>

											<td class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.petticash.AccountNo" />:</div>
											</td>
											<td class="row-odd"><html:select name="collectionsReportForm" multiple="multiple"
												property="appiNo" styleId="appiNo" style="width: 120px;"
												 size="2">
												<html:optionsCollection name="collectionsReportForm"
													property="accNOList" label="bankAccNo" value="id"/>
											</html:select></td>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.petticash.orderOfReport" /></div>
											</td>
											<td width="20%" height="25" class="row-odd"><html:radio
												property="orderofReport" value="number" styleId="receiptno">
												<bean:message key="knowledgepro.petticash.receiptNumber" />
											</html:radio></td>
											<td width="20%" height="25" class="row-odd"><html:radio
												property="orderofReport" value="refNo" styleId="studentno">
												<bean:message key="knowledgepro.petticash.AppRegRollNumber" />
											</html:radio></td>
										</tr>
									</table>
									</td>
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
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit property=""
								styleClass="formbutton" value="Submit" styleId="submitbutton">
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetMessages()"></html:button></td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
</body>
<script type="text/javascript">
function showStudentGroup() {
	document.getElementById("stPerGroup").style.display = "block";
	document.getElementById("otherName").style.display = "block";
}
var stPerGroupp = document.getElementById("otherUser").checked;
if(stPerGroupp == true){	
	document.getElementById("otherName").style.display = "block";	
}else{
	document.getElementById("stPerGroup").style.display = "none";
	document.getElementById("otherName").style.display = "none";
}
function resetAttReport()	{
	document.getElementById("all").checked="";
	document.getElementById("cancel").checked="";
	document.getElementById("allUsers").checked="";
	document.getElementById("currentUser").checked="";
	document.getElementById("otherUser").checked="";
					}
</script>

