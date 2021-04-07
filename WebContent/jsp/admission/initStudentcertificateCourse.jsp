<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">

function getSchemeNo(registerNo) {
	
	document.getElementById("registerNo").value = registerNo;
	document.getElementById("method").value = "getSchemeNo";
	document.studentCertificateCourseForm.submit();
}


</script>
<html:form action="/StudentCertificateCourse">	
		<html:hidden property="method" styleId="method" value="getStudentCertificateCourse" />
		<html:hidden property="formName" value="studentCertificateCourseForm"/>
		<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.certificate.course"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admin.certificate.course"/> Entry</strong></td>

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
									<td width="22%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.certificate.course.regno"/>:</div>
									</td>
								<td width="21%" class="row-even" valign="top"><html:text property="registerNO" styleId="registerNo"  onchange="getSchemeNo(this.value)"></html:text></td>																	
								
								<td width="23%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.certificate.course.Semester"/>:</div>
								</td>
									
								<td width="23%" class="row-even" valign="top"> <input type="hidden" id="schemeNo" name="schemeNo"
								value='<bean:write name="studentCertificateCourseForm" property="semester"/>' onfocus="getSchemeNo(this.value)"/>
							       <html:select property="semester" styleClass="combo" styleId="semester" >
										<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
										<html:option value="1">1</html:option>
										<html:option value="2">2</html:option>
										<html:option value="3">3</html:option>
										<html:option value="4">4</html:option>
										<html:option value="5">5</html:option>
										<html:option value="6">6</html:option>
										<html:option value="7">7</html:option>
										<html:option value="8">8</html:option>
										<html:option value="9">9</html:option>
										<html:option value="10">10</html:option>
										<html:option value="11">11</html:option>
										<html:option value="12">12</html:option>
										<html:option value="13">13</html:option>
										<html:option value="14">14</html:option>
									</html:select>
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
							<td height="35" align="center">
							<div align="center">
									<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
									</html:submit>
								</div>
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
<script type="text/javascript">
var print = "<c:out value='${studentCertificateCourseForm.printCourse}'/>";
if(print.length != 0 && print == "true") {
	var url ="StudentCertificateCourse.do?method=showStudentDetails";
	myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
</script>