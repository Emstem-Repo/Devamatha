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
<link rel="stylesheet" href="css/styles.css">
<script language="JavaScript" >
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}

</script>
<html:form action="/honoursCourseEntry" method="POST">
	<html:hidden property="formName" value="honoursCourseEntryForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="loginform" />

	
	<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
							<tr>
							<td align="left">
								<img src='<%=CMSConstants.LOGO_URL%>'  height="100" width="210"/></td>
							
							<td align="right">  <img src='<%=session.getAttribute("STUDENT_IMAGE")%>?time=<%=System.currentTimeMillis() %>'  height="128" width="133" /></td>
							</tr>
							<tr>
							<td align="center"  colspan="2" style='font-weight: bold; font-family: verdana; font-size:10pt;'>Application for Honours Program</td>
							
							</tr>
							<tr> 
							<td colspan="2">
							<table width="100%" style='font-family: verdana; font-size:9pt;'>
							<tr height="25px">
								<td colspan="3" align="left">Applied Honours Course: <SPAN style='font-weight: bold;'><bean:write name="honoursCourseEntryForm" property="selectedCourse"/></SPAN></td>
							</tr>
							<tr height="25px">
								<td align="center" colspan="3"><SPAN style='font-weight: bold;'>STUDENT DETAILS</SPAN></td>
							</tr>
							<tr height="25px">
								<td align="left" width="35%">Name: <bean:write name="honoursCourseEntryForm" property="studentName"/></td>
								<td align="left" width="33%">Reg.No: <bean:write name="honoursCourseEntryForm" property="regNo"/></td>
								<td align="left">Combination: <bean:write name="honoursCourseEntryForm" property="combination"/></td>
							</tr>		
							
							<tr height="25px">
								<td align="left" >Gender: <bean:write name="honoursCourseEntryForm" property="gender"/></td>
								<td align="left" >Email id: <bean:write name="honoursCourseEntryForm" property="emailId"/></td>
								<td> </td>
							</tr>	
							<tr height="25px">
								<td  colspan="3" align="left" >Permanent Address: <bean:write name="honoursCourseEntryForm" property="permanentAdd"/></td>
							</tr>	
							<tr height="25px">
								<td  colspan="3" align="left" >Present Address: <bean:write name="honoursCourseEntryForm" property="presentAdd"/></td>
							</tr>	
							<tr height="25px">
								<td align="left">Contact No-Land Line: <bean:write name="honoursCourseEntryForm" property="contactNo"/></td>
								<td align="left">Mobile: <bean:write name="honoursCourseEntryForm" property="mobileNo"/></td>
							</tr>		
										
							</table>
							<table style='border:1px solid #000000; font-family: verdana; font-size:9pt;' rules='all' width="100%">
								<tr height="25px"><td colspan="7" align="center" ><SPAN style='font-weight: bold;'>Academic Details </SPAN></td> </tr>
									<tr height="25px">
										<td align="center" width="15%">Class/Semester </td>
										<td align="center" width="10%">Year</td>
										<td colspan="2" align="center" width="28%">Aggregate </td>
										<td colspan="2" align="center" width="28%">Honours Subject</td>
										<td align="center" width="19%">Aggregate Attendance % </td>
									</tr>
									<tr height="25px">
										<td ></td>
										<td ></td>
										<td align="center" width="10%">Marks</td>
										<td align="center" width="18%">Percentage</td>
										<td align="center" width="10%">Marks</td>
										<td align="center" width="18%">Percentage</td>
										<td align="center"></td>
									</tr>
									<logic:notEmpty name="honoursCourseEntryForm" property="academicDetails">
										<logic:iterate id="details" name="honoursCourseEntryForm" property="academicDetails">
											<tr height="25px">
												<td align="center"><bean:write name="details" property="value.semester"/></td>
												<td align="center"><bean:write name="details" property="value.year"/></td>
												<td align="center"><bean:write name="details" property="value.totalMarksAwarded"/></td>
												<td align="center"><bean:write name="details" property="value.percentage"/></td>
												<td align="center"><bean:write name="details" property="value.honourSubjectMarksAwarded"/></td>
												<td align="center"><bean:write name="details" property="value.honourSubPercentage"/></td>
												<td align="center"><bean:write name="details" property="value.attPercentage"/></td>
											</tr>
										</logic:iterate>
									
									</logic:notEmpty>
									<tr height="25px">
									  <td colspan="7">Total No.of Arrears : <bean:write name="honoursCourseEntryForm" property="arrears"/> </td>
									</tr>
							</table>
							<table width="100%">
								<tr height="40px">
								</tr>
								<tr height="25px">
									<td colspan="3" align="center">UNDERTAKING </td>
								</tr>	
								<tr>
									<td align="left" colspan="3">
									I, <bean:write name="honoursCourseEntryForm" property="studentName"/> ****
									 </td>
								</tr>	
								<tr height="25px">
								</tr>	
								<tr height="25px">
									<td align="left" width="20%">Date:</td>
									<td align="left" width="40%"></td>
									<td align="left" width="40%"></td>
								</tr>
								<tr>
									<td align="left"></td>
									<td align="left">Signature of the Parent/Guardian</td>
									<td align="left">Signature of the Candidate</td>
								</tr>
							</table>
							</td>
							</tr>
							</table>
</html:form>
<script type="text/javascript">window.print();</script>