<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*,java.text.*" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}
function printPass(){
	var url = "StudentLoginAction.do?method=printHallTicket";
	myRef = window
			.open(url, "HallTicket",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");

}
</script>
<html:form action="/StudentLoginAction" >
	<html:hidden property="method" styleId="method" value="printHallTicket" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="loginform" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.studentlogin" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.publishHM.hallTicket" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.exam.publishHM.hallTicket" /></strong></div>
					</td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
						<c:if test="${isNull==false}">
							<td colspan="6" align="left">
							
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
							</c:if>
						</tr>
						<c:if test="${isNull==false}">
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>
							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						</c:if>
						<c:if test="${isNull==false}">
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
							
							<logic:equal value="false" name="loginform" property="isAttendanceShortage">
								<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
								<tr>
								<td colspan="3" width="100%" align="center">
								<img src='<%=request.getContextPath()+"/LogoServlet?count=1"%>'  height="120" width="600" /></td>
								</tr>
								<tr>
								<td colspan="3" width="100%" align="center" class="row-print">
								 <FONT size="2"><bean:write name="loginform" property="hallTicket.examName"/></font>
								</td>
								</tr>
								<tr>
								
								<td colspan="3" width="100%" align="center" class="row-print">
								 <FONT size="3">HALL TICKET </font>
								</td> 
								
								</tr>
								<tr> 
								<td colspan="3">
								<table width="100%">
								<tr>
								<td align="left" width="80%">
								<table>
								<tr>
								<td align="" class="row-print"><FONT size="2">REGISTER NUMBER</FONT></td>
								<td class="row-print"><FONT size="2">:&nbsp;<bean:write name="loginform" property="hallTicket.registerNo"/></FONT> </td>
								</tr>
								<tr>
								<td align="" class="row-print"><FONT size="2">PROGRAMME</FONT></td>
								<td class="row-print"><FONT size="2">:&nbsp;<bean:write name="loginform" property="hallTicket.courseName"/> </FONT></td>
								</tr>
								<tr>
								<td align="" class="row-print"><FONT size="2">SEMESTER</FONT></td>
								<td class="row-print"><FONT size="2">:&nbsp;<bean:write name="loginform" property="hallTicket.semesterNo"/> </FONT></td>
								</tr>
								
								<tr>
								<td align="" class="row-print"><FONT size="2">NAME OF CANDIDATE</FONT></td>
								<td class="row-print"><FONT size="2">:&nbsp;<bean:write name="loginform" property="hallTicket.studentName"/> </FONT></td>
								</tr>
								<tr>
								<td align="" class="row-print"><FONT size="2">DATE OF BIRTH</FONT></td>
								<td class="row-print"><FONT size="2">:&nbsp;<bean:write name="loginform" property="hallTicket.dateofBirth"/></FONT> </td>
								</tr>
								</table>
								</td>
								<td align="right" width="20%">
								<table>
								<tr>
								<td align="right">  <img src='<%=session.getAttribute("STUDENT_IMAGE")%>'  height="128" width="133" /></td>
								</tr>
								</table>
								</td>
								</tr>
								<tr>
								<td colspan="8">
								<table  width="100%" style="border: 1px solid black; " rules="all">
								<tr>
								<td align="center" class="row-print">Subject Code</td>
								<td align="center" class="row-print">Subject Name</td>
							<!--<logic:equal value="false" property="hallTicket.doNotDisplay" name="loginform">
								<td align="center" class="row-print">Date</td>
								<td align="center" class="row-print">Time</td>
							</logic:equal>-->
								
								</tr>
								<logic:notEmpty name="loginform" property="hallTicket.subList">
								<logic:iterate id="sub" name="loginform" property="hallTicket.subList">
								<tr height="21px">
								<td class="row-print"><bean:write name="sub" property="code" /></td>
								<td class="row-print"><bean:write name="sub" property="name" /></td>
							<!--<logic:equal value="false" property="hallTicket.doNotDisplay" name="loginform">
								<td class="row-print"><bean:write name="sub" property="startDate" /></td>
								<td class="row-print"> <bean:write name="sub" property="startTime" />-<bean:write name="sub" property="endTime" /></td>
							</logic:equal>-->
								</tr>
								</logic:iterate>
								</logic:notEmpty>
								</table>
								</td>
								</tr>
								</table>
								</td>
								</tr>
								<tr>
								<td class="row-print">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Signature of The Candidate:
								</td>
								<td class="row-print" align="right"  width="40%"><br></br><img src="images/Principal.jpg" width="130px" height="50px" /></td>
								<td  class="row-print" align="right" width="30%"><img src="images/COEFinal.jpg" width="130px" height="50px" /></td>
								</tr>
								<tr height="10px"></tr>
								<tr></tr>
								<tr height="10px">
								<td colspan="2" class="row-print-desc">
								<logic:notEmpty name="loginform" property="description1">
									<c:out value="${loginform.description1}" escapeXml="false"></c:out>
								</logic:notEmpty>
								</td>
								</tr>
								<tr><td colspan="2" align="right">
								<%
									   Date dNow = new Date( );
									   SimpleDateFormat ft = 
									   new SimpleDateFormat ("E dd/MM/yyyy 'at' hh:mm:ss a zzz");
									   out.print( "<h6 align=\"right\">" + 
								               ft.format(dNow) + 
								               "</h2>");
									%>
												</td></tr>
								</table>
							</logic:equal>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td>
										<logic:equal value="true" name="loginform" property="isAttendanceShortage">
											<font color="red"><bean:message key="knowledgepro.exam.hallticket.attendance"/></font>
										</logic:equal>
									</td>
								</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						</c:if>
						<c:if test="${isNull==true}">
					<br/><br/>Hall Ticket is not Available.Please Contact.<br/><br/>
					</c:if>
					<c:if test="${isNull==false}">
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
						</c:if>
					</table>
					
					<c:if test="${isNull==false}">
						<div align="center">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="46%" height="35">
									<logic:equal value="false" name="loginform" property="isAttendanceShortage">
									<div align="right"><html:button property="" styleClass="formbutton" onclick="printPass()">
										Print
									</html:button></div>
									</logic:equal>
									</td>
									<td width="2%"></td>
									<td width="52%" align="left"><html:button property=""  styleClass="formbutton" value="Close" onclick="goToHomePage()"></html:button> 
									</td>
								</tr>
							</table>
						</div>
					</c:if>
					
					</td>
					
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
