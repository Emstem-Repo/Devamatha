<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<style type="text/css">
/* .row-white1 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: normal;
	color: #000000;
	background-color: #FFFFFF;
	border: 1.5px solid black;
} */
.row-white1,.row-white1 td,.row-white1 th{
	border: 1px solid black;
} 
table{
	border-collapse: collapse;
}
.row-print{
	font-size: 18px;
	font-weight: normal;
}
</style>
	
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" href="css/calendar.css">
<html:form action="/adminMarksCard" >
	<html:hidden property="method" styleId="method" value="printMarksCard" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="adminMarksCardForm" />
	<logic:equal value="false" name="adminMarksCardForm" property="pg">
	
	<table class="row-white1" width="100%" height="100%" cellspacing="1" cellpadding="2" style="text-align: center;">
	<logic:notEmpty name="adminMarksCardForm" property="progressCardList">
			<logic:iterate id="mto" name="adminMarksCardForm" property="progressCardList" indexId="count">
	<tr><td style="text-align: center" colspan="10"><h2>ACCADEMIC PERFOMANCE(<bean:write name="mto" property="academicYear"/>)</h2></td></tr>
	<c:choose>
			<c:when test="${count == 0}">
			<tr><td style="text-align: center" colspan="10"><h2>DEVAMATHA COLLEGE</h2></td></tr>
	<tr><td colspan="10" style="border: none;"><table width="100%" border="0" style="border: none;">
		<tr>
		<td style="text-align: left;font-weight: bolder;border: none;padding-left: 0px">Name: <bean:write name="mto" property="studentName"/></td>
		<td style="text-align: right;font-weight: bolder;border: none;padding-right: 15px">Programme: <bean:write name="mto" property="programeme"/></td>
		
		</tr>
		
		<tr>
		<td style="text-align: left;font-weight: bolder;border: none; padding-left: 0px">Sem: <bean:write name="mto" property="term"/></td>
		<td style="text-align: right;font-weight: bolder;border: none;padding-right: 15px">Class.No: <bean:write name="mto" property="classNo"/></td>
		</tr>
		
	</table></td></tr>
	</c:when>
	</c:choose>
	<c:if test="${adminMarksCardForm.exam1=='Regular'}">
				<tr>
					<td class="row-print" rowspan="2" style="text-align: left;">Name of Courses</td>
					<td class="row-print" colspan="2">Ist Internal </td>
					<td class="row-print" colspan="2">IInd Internal</td>
					<td class="row-print" colspan="3">Attendance</td>
					<td class="row-print" colspan="2">University Examination</td>
					
				</tr>
				<tr>
					<td class="row-print">Mark Secured</td>
					<td class="row-print">Max.Mark</td>
					<td class="row-print">Mark Secured</td>
					<td class="row-print">Max.Mark</td>
					<td class="row-print">Total Hours</td>
					<td class="row-print">Hours.Pressent</td>
					<td class="row-print">Percentage</td>
					<td class="row-print">Mark Secured</td>
					<td class="row-print">Max.Mark</td>
					
				</tr>
				<logic:notEmpty name="mto" property="subjectList">
			<logic:iterate id="pto" name="mto" property="subjectList" indexId="count">
				<tr>
					<td class="row-print" style="text-align: left;"><bean:write name="pto" property="name"/></td>
					<td class="row-print"><bean:write name="pto" property="firstInternalMarksAwarded"/></td>
					<td class="row-print"><bean:write name="pto" property="firstInternalMarksMax"/></td>
					<td class="row-print"><bean:write name="pto" property="secondInternalMarksAwarded"/></td>
					<td class="row-print"><bean:write name="pto" property="secondInternalMarksMax"/></td>
					<td class="row-print"><bean:write name="pto" property="attPressent"/></td>
					<td class="row-print"><bean:write name="pto" property="attMax"/></td>
					<td class="row-print"><bean:write name="pto" property="attPercentage"/></td>
					<td class="row-print"><bean:write name="pto" property="eseMarksAwarded"/></td>
					<td class="row-print"><bean:write name="pto" property="eseMaxMarks"/></td>
					
				</tr>
				</logic:iterate>
				</logic:notEmpty>
				<tr>
					<td class="row-print">Total Mark</td>
					<td class="row-print"><bean:write name="mto" property="firstInternalTotal"/></td>
					<td class="row-print"><bean:write name="mto" property="firstInternalMaxTotal"/></td>
					<td class="row-print"><bean:write name="mto" property="secondInternalTotal"/></td>
					<td class="row-print"><bean:write name="mto" property="secondInternalMaxTotal"/></td>
					<td class="row-print"><bean:write name="mto" property="attTotalprssesnt"/></td>
					<td class="row-print"><bean:write name="mto" property="attTotalHeld"/></td>
					<td class="row-print"><bean:write name="mto" property="totalAttPercentage"/></td>
					<td class="row-print"><bean:write name="mto" property="eseAwardedTotal"/></td>
					<td class="row-print"><bean:write name="mto" property="eseMaxTotal"/></td>
					
				</tr>
				<tr>
					<td class="row-print" colspan="4" style="text-align: left;">Signature of Teacher in charge<br><br><br></td>
					<td class="row-print" colspan="6" ></td>
				</tr>
				<tr>
					<td class="row-print"colspan="4" style="text-align: left;">Signature of Principal<br><br><br></td>
					<td  class="row-print" colspan="6" ></td>
				</tr>
				<tr>
					<td class="row-print" colspan="4" style="text-align: left;">Signature of Parent/Guardian<br><br><br></td>
					<td class="row-print" colspan="6" ></td>
				</tr>
			
	</c:if>
	</logic:iterate>
		</logic:notEmpty>
</table>


</logic:equal>		
</html:form>
