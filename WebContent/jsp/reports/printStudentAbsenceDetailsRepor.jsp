<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">

function resetFormFields(){	
	
	resetErrMsgs();
	
}
</script>
<html:form action="/attendTeacherReport">	
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td><img src="images/01.gif" width="5" height="5"></td>
		<td width="914" background="images/02.gif"></td>
		<td><img src="images/03.gif" width="5" height="5"></td>
	</tr>
	<tr>
		<td width="5" background="images/left.gif"></td>
		<td valign="top">
		<table width="100%" cellspacing="1" cellpadding="2" border="1">

			<tr>
				<td  height="25" class="row-odd">
				<div align="center">Register/Roll No.</div>
				</td>
				<td  class="row-odd">
				<div align="center">Student Name</div>
				</td>
				<td  class="row-odd">
				<div align="center">Class Name</div>
				</td>
				<td  class="row-odd">
				<div align="center">Subject Code</div>
				</td>
				<td class="row-odd">
				<div align="center">Date</div>
				</td>
				<td class="row-odd">
				<div align="center">Period</div>
				</td>
				<td class="row-odd">
				<div align="center">Faculty Name</div>
				</td>
			</tr>
			<logic:iterate id="studentAbsence" scope="session"  name="studentAbsenceDetails" indexId="count">
				<tr>
				<td  height="25" class="row-even">
				<div align="center"><bean:write name="studentAbsence" property="registerNumber"/>
				</div>
				</td>
				<td  class="row-even">
				<div align="center"><bean:write name="studentAbsence" property="studentName"/></div>
				</td>
				<td  class="row-even">
				<div align="center">
				<c:forEach var="studentAbsenceTO1" items="${studentAbsence.studentAbsenceDetailsList}" varStatus="index"> <c:out value="${studentAbsenceTO1.className}"/></c:forEach>
				</div>
				</td>
				<td  class="row-even">
				<div align="center">
				 <c:forEach var="studentAbsenceTO2" items="${studentAbsence.studentAbsenceDetailsList}" varStatus="index"> <c:out value="${studentAbsenceTO2.subjectCode}"/></c:forEach>
				</div>
				</td>
				<td class="row-even">
				<div align="center">
				<c:forEach var="studentAbsenceTO3" items="${studentAbsence.studentAbsenceDetailsList}" varStatus="index"> <c:out value="${studentAbsenceTO3.attendanceDate}" /></c:forEach>
				</div>
				</td>
				<td class="row-even">
				<div align="center">
				<c:forEach var="studentAbsenceTO4" items="${studentAbsence.studentAbsenceDetailsList}" varStatus="index"> <c:out value="${studentAbsenceTO4.periodName}" /></c:forEach>
				</div>
				</td>
				<td class="row-even">
				<div align="center">
				<c:forEach var="studentAbsenceTO5" items="${studentAbsence.studentAbsenceDetailsList}" varStatus="index"> <c:out value="${studentAbsenceTO5.facultyName}" /></c:forEach>
				</div>
				</td>
			</tr>
			</logic:iterate>
		</table>
		</td>
		<td width="5" height="30" background="images/right.gif"></td>
	</tr>
	<tr>
		<td height="5"><img src="images/04.gif" width="5"
			height="5"></td>
		<td background="images/05.gif"></td>
		<td><img src="images/06.gif"></td>
	</tr>
</table>
<script type="text/javascript">
	window.print();
</script>
</html:form>
