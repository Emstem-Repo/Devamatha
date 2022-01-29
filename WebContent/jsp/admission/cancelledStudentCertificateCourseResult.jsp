<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function cancel() {
	document.location.href = "StudentCertificateCourse.do?method=initCancelledStudentCertificate";
}
</script>
<html:form action="/StudentCertificateCourse">
	<html:hidden property="method" styleId="method" value="cancelledCertificateCourse" />
	<html:hidden property="formName" value="studentCertificateCourseForm" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admin" /> <span class="Bredcrumbs">&gt;&gt;
			Cancel Certificate Course &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Cancel Certificate Course</strong></td>

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
							<table width="99%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr class="row-even">
											<td></td>
											<td>Student Name</td>
											<td>Register No</td>
											<td>Certificate Course Name</td>
										</tr>
										<logic:notEmpty name="studentCertificateCourseForm"
											property="studentCertificateCourse">
											<nested:iterate name="studentCertificateCourseForm"
												property="studentCertificateCourse" id="studentCertificateCourse"
												indexId="count">
												<tr class="row-white">
												<%
													String s1 = "mandatory_check_" + count;
												%>
												<td ><nested:checkbox property="checked" styleId="<%=s1%>" />
												<script type="text/javascript">
													document.getElementById("mandatory_check_<c:out value='${count}'/>").checked=true;
												</script>
												</td>
												<td>
												<nested:write name="studentCertificateCourse"
													property="studentName" /></td>
												<td >
												<nested:write name="studentCertificateCourse"
													property="registerNo" />
												</td>	
												<td height="25" >
												<nested:write name="studentCertificateCourse"
													property="certificateCourseName" />
												</td>
											</nested:iterate>
										</logic:notEmpty>
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
							<td height="35" align="right" width="50%">
							<div align="center"><html:submit property=""
								styleClass="formbutton" value="Cancel" styleId="submitbutton">
							</html:submit></div>
							</td>
							<td height="35" align="left" width="50%">
							<div><html:button property="" styleClass="formbutton"
								value="Back" onclick="cancel()"></html:button></div>
							</td>
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