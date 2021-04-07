<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%><script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}
function printPass(){
	var url = "StudentLoginAction.do?method=printSuppHallTicket";
	myRef = window
			.open(url, "Hall Ticket",
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
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>
							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
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
							<td width="" align="right" class="row-print" rowspan="2"><img src='<%=session.getAttribute("STUDENT_IMAGE")%>'  height="128" width="133" />
							</td>
							
							</tr>
							</table>
							</td>
							
							
							
							</tr>
							
							
							
							
							
							
							
							</table>
							</td>
							</tr>
							<tr> 
							<td colspan="3">
							<table width="100%">
							
							<tr>
							<td colspan="8">
							<table  width="100%" style="border: 1px solid black; " rules="all">
							<tr>
							<td align="center" class="row-print"><FONT size="1">Course Code</FONT></td>
							<td align="center" class="row-print"><FONT size="1">Course Title</FONT></td>
							<!-- <td align="center" class="row-print"><FONT size="1">Date</FONT></td>
							<td align="center" class="row-print"><FONT size="1">Time</FONT></td> -->
							<!--<td align="center" class="row-print">Start Time</td>
							<td align="center" class="row-print">End Time</td>
						-->
							<!--<td align="center" class="row-print">Venue</td>
							
							--><!--<td align="center" class="row-print">Signature of the Invigilator</td>
							
							--></tr>
							<logic:notEmpty name="loginform" property="hallTicket.subList">
							<logic:iterate id="sub" name="loginform" property="hallTicket.subList">
							<tr height="18px">
							<td class="row-print" align="center"><FONT size="1"><bean:write name="sub" property="code" /></FONT></td>
							
							<td class="row-print">
							<c:choose>
									<c:when test="${sub.name!=null && sub.name!='' }">
											&nbsp;&nbsp;&nbsp;<FONT size="1"><bean:write name="sub" property="name" /></FONT>
									</c:when>
							</c:choose>
							<c:choose>
									<c:when test="${sub.secName!=null && sub.secName!='' }">
										<br>&nbsp;&nbsp;&nbsp;<FONT size="1"><bean:write name="sub" property="secName" /></FONT>
									</c:when>
							</c:choose>
							<c:choose>		
									<c:when test="${sub.thirdName!=null && sub.thirdName!='' }">
										<br>&nbsp;&nbsp;&nbsp;<FONT size="1"><bean:write name="sub" property="thirdName" /></FONT>
									</c:when>
							</c:choose>
							<c:choose>		
									<c:when test="${sub.fourthName!=null && sub.fourthName!='' }">
										<br>&nbsp;&nbsp;&nbsp;<FONT size="1"><bean:write name="sub" property="fourthName" /></FONT>
									</c:when>
							</c:choose>
							<c:choose>		
									<c:when test="${sub.fifthName!=null && sub.fifthName!='' }">
										<br>&nbsp;&nbsp;&nbsp;<FONT size="1"><bean:write name="sub" property="fifthName" /></FONT>
									</c:when>
									
							</c:choose>
							</td>
							
							<!-- <td class="row-print" align="center"><FONT size="1"><bean:write name="sub" property="startDate" /></FONT></td>  -->
							<!--<td class="row-print"> <bean:write name="sub" property="startTime" /></td>
							<td class="row-print"> <bean:write name="sub" property="endTime" /></td>
							
						-->
						
						<!-- <td class="row-print" align="center"><FONT size="1"> <bean:write name="sub" property="startTime" />-<bean:write name="sub" property="endTime" /></FONT></td> -->
							<!--<td class="row-print">
							<logic:notEmpty name="sub" property="roomNo">
							 <bean:write name="sub" property="blockNo"/>- Floor:<bean:write name="sub" property="floorNo"/>
							- Room:<bean:write name="sub" property="roomNo"/>
							</logic:notEmpty>
							</td>
							
							--><!--<td class="row-print">&nbsp; </td>
							
							--></tr>
							</logic:iterate>
							</logic:notEmpty>
							</table>
							</td>
							</tr>
							</table>
							</td>
							</tr>
							</table>
							</td>
							</tr>
							<tr><td colspan="3">
							<table width="100%">
							<tr>
							
							<td class="row-print"  align="left" width="30%"><br></br>
							<FONT size="1" style="font-family:serif;"><b>Signature of The Candidate:</b></FONT><!--<br>
							(To be signed in the presence of the identifying officer)--></td>
							<!--<td class="row-print"  align="left" width="30%"></td>
						--><td class="row-print" align="right"  width="40%"><br></br><img src="images/Principal.jpg" width="130px" height="50px" /></td>
							
							<td  class="row-print" align="right" width="30%"><img src="images/COEFinal.jpg" width="130px" height="50px" /></td>
							
						</tr>
							</table>
							</td></tr>
							<tr height="10px">
							<td colspan="3" class="row-print-desc">
							<logic:notEmpty name="loginform" property="description1">
								<c:out value="${loginform.description1}" escapeXml="false"></c:out>
							</logic:notEmpty>
							</td>
							</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:button property="" styleClass="formbutton" onclick="printPass()">
								Print
							</html:button></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""  styleClass="formbutton" value="Close" onclick="goToHomePage()"></html:button> 
							</td>
						</tr>
					</table>
					</div>
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
