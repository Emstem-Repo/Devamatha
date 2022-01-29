<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script type="text/javascript">
	
	function resetAttReport() {
		document.getElementById("classesName").value = ""; 
			resetErrMsgs();
	}

	function getNames() {
		document.getElementById("method").value = "getTeacherClassWiseAttendanceNotTakenSummary";
		
		//document.getElementById("courseNameId").value = document
		//		.getElementById("course").options[document
		//		.getElementById("course").selectedIndex].text;
	}

	function getClasses(year) {

	
	if(document.getElementById("isFaculty").value=="true"){
		getClassesByTeacherAndYear("classMap", year, document.getElementById("userId").value,"classesName", updateClasses);
	}else{
		getClassesByYear("classMap", year, "classesName", updateClasses);
	}
	
	}
	
	function updateClasses(req) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById("classesName");
	for (x1=destination.options.length-1; x1>=0; x1--) {
		destination.options[x1]=null;
	}
	var items1 = responseObj.getElementsByTagName("option");
	for (var j = 0 ; j < items1.length ; j++) {
        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination.options[j] = new Option(label,value);
	 }
	}


	
</script>

<html:form action="/studentWiseAttendanceSummary" method="post">
	<html:hidden property="method" styleId="method"
		value="getStudentWiseAttendanceSummary" />
	<html:hidden property="courseName" styleId="courseNameId" value="" />
	<html:hidden property="formName" value="studentWiseAttendanceSummaryForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="userId" styleId="userId"/>
	<html:hidden property="isFaculty" styleId="isFaculty"/>
	
	<table width="98%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td class="heading">Teacher Attendance Not Taken Details</td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="5"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.studentwise.summaryreport" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="45" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr bgcolor="#FFFFFF">
							<div align="right"><span class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<td colspan="6" class="body" align="left">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>

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
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.fee.academicyear.col" /></div>
									</td>
									<td width="30%" height="25" colspan="1" class="row-even"><input
										type="hidden" id="tempyear" name="tempyear"
										value="<bean:write name="studentWiseAttendanceSummaryForm" property="academicYear"/>" />
									<html:select property="academicYear" styleId="academicYear"
										styleClass="combo" onchange="getClasses(this.value)">
										<html:option value="">- Select -</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									
									<td width="20%" height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span>
											<bean:message key="knowledgepro.attendanceentry.class" />
										</div>
									</td>
									<td width="30%" height="25" class="row-even">
									 	<html:select name="studentWiseAttendanceSummaryForm" styleId="classesName" property="classesName" size="10" style="width: 300px;" multiple="multiple">
											<html:optionsCollection name="studentWiseAttendanceSummaryForm" property="classMap" label="value" value="key" />
										</html:select> 
					                </td>
									

								</tr>
								
								<%-- 
								<tr>
									<td height="35" class="row-even" colspan="4"><div align="right">(OR)</div></td>
								</tr>

								
								<tr>
									
									
									<td colspan="2" width="25%" height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span>
											<bean:message key="knowledgepro.attendanceentry.teacher" />
										</div>
									</td>
									<td colspan="2" width="25%" height="25" class="row-even">
									 	<html:select name="studentWiseAttendanceSummaryForm" styleId="teacherIds" property="teacherIds" size="5" style="width: 200px;" multiple="multiple">
											<html:optionsCollection name="studentWiseAttendanceSummaryForm" property="teachersMap" label="value" value="key" />
										</html:select> 
					                </td>
									

								</tr>
									
								--%>
								
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
					<td valign="top" class="news">

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleClass="formbutton"
								onclick="getNames()">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetAttReport()"></html:button></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="931" background="images/TcenterD.gif"></td>
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
		document.getElementById("academicYear").value = year;
	}
	
	
	document.getElementById("academicYear").value=document.getElementById("year").value;
	
	
</script>