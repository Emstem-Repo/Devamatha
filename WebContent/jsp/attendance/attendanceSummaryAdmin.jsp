<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">	
function resetValues()
{
	document.getElementById("regdno").value = "";
	resetErrMsgs();
}
</script>
<html:form action="/studentWiseAttendanceSummary" method="post">	
	<html:hidden property="formName" value="studentWiseAttendanceSummaryForm" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="method" styleId="method" value="getStudentAttendanceSummaryByAdminReport"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs">
			<span class="Bredcrumbs"><bean:message key="knowledgepro.attendance"/> &gt;&gt;
			<bean:message key="knowledgepro.attendance.check.student"/> 
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">  <bean:message key="knowledgepro.attendance.check.student"/>  </strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td width="46%" height="25" class="row-odd">
									<div align="right">
										<bean:message key="knowledgepro.attendance.regno"/> </div>
									</td>
										<td width="54%" height="25" class="row-even"><span
											class="star"> <html:text property="startRegisterNo"
											styleClass="TextBox" styleId="regdno" size="20"
											maxlength="20" /> </span></td>
								</tr>
								<tr>
									<td width="46%" height="25" class="row-odd">
										<div align="right">	(or) </div>
									</td>
										<td width="54%" height="25" class="row-even"> </td>
								</tr>
								<tr>
									<td width="46%" height="25" class="row-odd">
									<div align="right">
										<bean:message key="knowledgepro.fee.studentname"/> </div>
									</td>
										<td width="54%" height="25" class="row-even"><span
											class="star"> <html:text property="studentName"
											styleClass="TextBox" styleId="studentNameid" size="20"
											maxlength="20" /> </span></td>
								</tr>
								<tr>
									<td width="46%" height="25" class="row-odd">
										<div align="right">	(or) </div>
									</td>
										<td width="54%" height="25" class="row-even"> </td>
								</tr>
								<tr>
									<td width="46%" height="25" class="row-odd">
									<div align="right">
										<bean:message key="knowledgepro.attendance.rollno"/> </div>
									</td>
										<td width="54%" height="25" class="row-even"><span
											class="star"> <html:text property="startRollNo"
											styleClass="TextBox" styleId="regdno" size="20"
											maxlength="20" /> </span></td>
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
							<div align="right">
								<html:submit property="" styleClass="formbutton" value="Search" 
										styleId="submitbutton">
								</html:submit>						
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetValues()"></html:button></td>
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
