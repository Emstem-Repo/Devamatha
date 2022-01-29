<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function getPrograme(deptname) {
	getProgramesByDepartmentId("programeMap", deptname, "programes", updateProgrammes);
}
function updateProgrammes(req) {
	updateOptionsFromMap(req, "programes", "- Select -");
}
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
function printServiceLearningRecord() {
	
	var url = "StudentLoginAction.do?method=printServiceLearningRecord";
	myRef = window
	.open(url, "MarksCard",
			"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");

}


</script>
<html:form action="/StudentLoginAction">	

	<html:hidden property="formName" value="loginform" />
	<html:hidden property="method" styleId="method" value="submitServiceLearningActivity" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>
			<span class="Bredcrumbs">&gt;&gt;
		Service Learning Activity
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Service Learning Activity</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/st_Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
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
					<td height="41" valign="top" background="images/st_Tright_03_03.gif"></td>

					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							<tr>
								<td width="80%" align="center">
								<b>SERVICE LEARNING OVERALL MARKS ENTRY</b>
								</td>
								
							</tr>
							<tr>
								<td>
									<table width="50%">
									<tr>
									<td class="row-print"> Applied Year</td>
										<td class="row-print"> :</td>
										<td class="row-print"><bean:write name="loginform" property="year"/></td>
									</tr>
<br/>
									<tr>
										<td class="row-print">Course</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="courseName"/></td>
									</tr>
									<br/>
									<tr>
										<td class="row-print">Name</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="studentName"/></td>
									</tr>
									<br/>
									<tr>
										<td class="row-print">Register No</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="regNo"/></td>
									</tr>

							</table></td></tr>
							
							</table>
							</td>
							<td width="5" height="29" background="images/st_right.gif"></td>
						</tr>

						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/st_Tright_3_3.gif" class="news"></td>
				</tr>
				
				 <tr>
                      <td width="5"  background="images/st_left.gif"></td>
                      <td valign="top"><table class="row-print" width="100%" style="border: 1px solid black; " rules="all">
                          <tr >
                            <td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
                            <td height="25" class="studentrow-odd" ><div align="center">Programme Name</div></td>
                            <td class="studentrow-odd" ><div align="center">Department or club</div></td>
                            <td class="studentrow-odd" ><div align="center">Learning And Outcome</div></td>
                            <td class="studentrow-odd" ><div align="center">Credits(marks)</div></td>
                           
                           
                          </tr>
                          <c:set var="temp" value="0" />
                         <logic:iterate id="sList" property="serviceLearningMarksEntryToList" name="loginform"
											 indexId="count">
								
								<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25" width="10%">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
							<td width="13%" height="39" align="center">&nbsp;<bean:write name="sList" property="programName" /></td>
                            <td width="19%" align="center">&nbsp;<bean:write property="depttName" name="sList"/></td>
                            <td width="8%" align="center">&nbsp;<bean:write property="learningAndOutcome" name="sList"/></td>
                             <td width="8%" align="center">&nbsp;<bean:write property="credit" name="sList"/></td>
                           
                          
								
											</tr>
											</logic:iterate>
											<tr>
                    <td width="8%"  height="25" colspan="4" class="row-print" align="center" >Total Credit</td><td class="row-print" height="25"  align="center" ><bean:write property="overallMark" name="loginform"/></td>
                    </tr>
											
									                          
                      </table></td>
                      <td width="5" height="30"  background="images/st_right.gif"></td>
                    </tr>
                    <tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="center">
							
								<input type="button" class="formbutton" value="Print"
										 onclick="printServiceLearningRecord()"/ >
									
							
								</div>
							</td>
							
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/st_Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/st_Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>

		</tr>
	</table>
</html:form>
