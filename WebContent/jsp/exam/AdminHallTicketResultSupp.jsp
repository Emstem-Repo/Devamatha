<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" href="css/calendar.css">
<html:form action="/adminHallTicket" >
	<html:hidden property="method" styleId="method" value="printMarksCard" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="adminHallTicketForm" />
	<logic:notEmpty name="adminHallTicketForm" property="studentList">
		<logic:iterate id="to" name="adminHallTicketForm" property="studentList" indexId="count">
		<c:if test="${count>0}">
		<p style="page-break-after:always;"> </p>
					<p style="page-break-after:always;"> </p>
		</c:if>
		
		
		
		
		
		<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
							
							<!--<tr>
							<td align="left">
							<img src='<%=request.getContextPath()+"/LogoServlet?count=1"%>'  height="100" width="210" /></td>
							<td align="right">  <bean:define id="reg" name="to" property="registerNo"></bean:define>
								<img src='<bean:write name="to" property="studentPhoto" />'  height="128" width="133" /></td>
							</tr>
							<tr>
							<td width="80%" align="center" class="row-print">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EXAMINATION ADMISSION TICKET</td>
							<td>
								
							<%--<img src='<%=CMSConstants.barbecueLink %><%=reg %>' height="21" width="118"   />
							--%>
							
							</td>
							</tr>
							
							
							
							-->
							
							<tr>
							
							<td colspan="3" width="100%" align="center">
							<img src='<%=request.getContextPath()+"/LogoServlet?count=1"%>'  height="120" width="600" /></td>
							<!--<td width="60%" class="row-print"> <FONT size="2">&nbsp;&nbsp;&nbsp;&nbsp;HALL TICKET OF <bean:write name="to" property="semesterExt"/> SEMESTER &nbsp; DEGREE EXAMINATION <bean:write name="to" property="month"/> <bean:write name="to" property="year"/></font></td>
							
							-->
							  
							
							</tr>
							
							<tr>
							
							<!--<td colspan="3" width="100%" align="center" class="row-print">
							 <FONT size="2">HALL TICKET OF <bean:write name="to" property="semesterExt"/> SEMESTER MBA EXAMINATION <bean:write name="to" property="month"/> <bean:write name="to" property="year"/></font>
							</td>
							-->
							<td colspan="3" width="100%" align="center" class="row-print">
							 <FONT size="2"><bean:write name="to" property="examName"/> </font>
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
							<td class="row-print"><FONT size="2">:&nbsp;<bean:write name="to" property="registerNo"/></FONT> </td>
							</tr>
							<tr>
							<td align="" class="row-print"><FONT size="2">PROGRAMME</FONT></td>
							<td class="row-print"><FONT size="2">:&nbsp;<bean:write name="to" property="courseName"/> </FONT></td>
							</tr>
							<tr>
							<td align="" class="row-print"><FONT size="2">SEMESTER</FONT></td>
							<td class="row-print"><FONT size="2">:&nbsp;<bean:write name="to" property="semesterNo"/> </FONT></td>
							</tr>
							
							<tr>
							<td align="" class="row-print"><FONT size="2">NAME OF CANDIDATE</FONT></td>
							<td class="row-print"><FONT size="2">:&nbsp;<bean:write name="to" property="studentName"/> </FONT></td>
							</tr>
							<tr>
							<td align="" class="row-print"><FONT size="2">DATE OF BIRTH</FONT></td>
							<td class="row-print"><FONT size="2">:&nbsp;<bean:write name="to" property="dateofBirth"/></FONT> </td>
							</tr>
							
							<!--<logic:notEmpty property="roomAlloted" name="to">
							<td align="right" class="row-print">Venue :</td>
							<td class="row-print"><bean:write name="to" property="blockNO"/> - Floor No:&nbsp;<bean:write name="to" property="floorNo"/> 
							- Room No:&nbsp;<bean:write name="to" property="roomAlloted"/>
							</td>
							</logic:notEmpty>
							-->
							
							
							
							</table>
							</td>
							
							
							
							<td align="right" width="20%">
							<table>
							<tr>
							<td width="" align="right" class="row-print" rowspan="2"><img src='<bean:write name="to" property="studentPhoto" />'  height="128" width="133" />
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
							<logic:notEmpty name="to" property="subList">
							<logic:iterate id="sub" name="to" property="subList">
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
							
							<!-- <td class="row-print" align="center"><FONT size="1"><bean:write name="sub" property="startDate" /></FONT></td> -->
							<!--<td class="row-print"> <bean:write name="sub" property="startTime" /></td>
							<td class="row-print"> <bean:write name="sub" property="endTime" /></td>-->
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
							
							<!--<tr height="10px">
							<td colspan="3"></td>
							</tr>
							
							
							-->
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
							
							<!--<tr><td colspan="3">
							<table width="100%">
							
							<tr>
							<td  class="row-print" align="left" width="50%"><FONT size="1"><br></br><br></br>
								Seal and Signature of the Head of the Department </FONT>
							</td>
							<td  class="row-print" align="center" width="20%"><br></br><br></br><font size="1">(Seal)</font></td>
							
							</tr>
							</table></td></tr>
							
							
							--><!--<tr>
							<td colspan="3" class="row-print">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Signature of The Candidate:
							</td>
							</tr>
							
							-->
							
							

							
							<tr height="10px">
							<td colspan="3" class="row-print-desc">
							<logic:notEmpty name="adminHallTicketForm" property="description">
								<c:out value="${adminHallTicketForm.description}" escapeXml="false"></c:out>
							</logic:notEmpty>
							</td>
							</tr>
							
							
							
							<%--
							<tr>
							<td colspan="2" align="right">
								<img src="images/COEFinal.jpg" width="157px" height="72px" />
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
							
							--%>
							
							</table>
		
		
		
			
		</logic:iterate>
	</logic:notEmpty>
</html:form>