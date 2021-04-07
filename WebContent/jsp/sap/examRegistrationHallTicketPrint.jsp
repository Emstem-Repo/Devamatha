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
<script type="text/javascript">
function printPass() {	
	window.print();
}

</script>
<html:form action="/examRegistrationDetails" >
	<html:hidden property="method" styleId="method" value="printHallTicket" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="examRegDetailsForm" />

	
	<table width="100%" cellspacing="1" cellpadding="0">
							<tr>
							<td align="left" width="10%"></td>
							<td>
							
							<img src='<%=CMSConstants.LOGO_URL%>'  height="100" width="210"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 <img src='images/StudentPhotos/<bean:write name="examRegDetailsForm" property="studentId"/>.jpg?<%=System.currentTimeMillis() %>'  height="128" width="133" /></td>
							<td width="10%"></td>
							</tr>
							<tr>
							<td width="10%"></td>
							<td width="80%" align="center" class="row-print"  >
							<font size="2" style="font-family: Verdana, Arial, Helvetica, sans-serif;">SAP Exam HallTicket</font> </td>
							<td width="10%"></td>
							</tr>
							<tr>
							<td colspan="3" align="right">
								<bean:define id="reg" name="examRegDetailsForm" property="regNo"></bean:define>
								<img src='<%=CMSConstants.barbecueLink %><%=reg%>' height="21" width="118"   />
								</td>
							</tr>
							<tr> 
							<td width="10%"></td>
								<td width="80%">
								<table width="100%" cellpadding="1" style='border: 1px solid #000000' rules='all' align="center">
								<tr height="30">
								<td align="left" class="row-print" height="10" width="15%" >
								Reg.No:</td>
								<td class="row-print" height="5" width="85%" align="left">
								<bean:write name="examRegDetailsForm" property="regNo"/> </td>
								</tr>
								<tr height="30">
								<td align="left" class="row-print" height="10" width="15%">
								Name: </td>
								<td class="row-print" height="5"  width="85%" align="left">
								<bean:write name="examRegDetailsForm" property="nameOfStudent"/> </td>
								</tr>
								<tr height="30">
								<td align="left" class="row-print" height="10" width="15%">
								Class:</td>
								<td class="row-print" height="5"  width="85%" align="left">
								<bean:write name="examRegDetailsForm" property="className"/> </td>
								</tr>
								<tr height="30">
								<td align="left" class="row-print" height="10" width="15%">
								Date of Exam:</td>
								<td class="row-print" height="5"  width="85%" align="left">
								<bean:write name="examRegDetailsForm" property="examDate"/> </td>
								</tr>
								<tr height="30">
								<td align="left" class="row-print" height="10" width="15%">
								Session:</td>
								<td class="row-print" height="5"  width="85%" align="left">
								<bean:write name="examRegDetailsForm" property="sessionName"/> </td>
								</tr>
								<tr height="30">
								<td align="left" class="row-print" height="10" width="15%">
								Venue:</td>
								<td class="row-print" height="5"  width="85%" align="left">
								<bean:write name="examRegDetailsForm" property="venueName"/> 
								</table>
								</td>
								<td width="10%"></td>
							</tr>
							<tr height="50"> 
							<td width="10%"></td>
							<td width="80%" class="row-print"  align="left">
							 Signature of The Candidate:
							 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							Signature of the Invigilator</td>
								<td width="10%"></td>
							</tr>
							<tr height="20"></tr>
							<tr></tr>
							
							<tr height="40">
							<td colspan="2" align="right">
								<img src="images/COEFinal.jpg" width="157px" height="72px" />
							</td>
							<td width="10%"></td>
							</tr>
							<tr height="40"><td colspan="2" align="right">
							<%
								   Date dNow = new Date( );
								   SimpleDateFormat ft = 
								   new SimpleDateFormat ("E dd/MM/yyyy 'at' hh:mm:ss a zzz");
								   out.print( "<h6 align=\"right\">" + 
							               ft.format(dNow) + 
							               "</h2>");
								%>
											</td>
											<td width="10%"></td>
											</tr>
							</table>
</html:form>
<script type="text/javascript">printPass();</script>