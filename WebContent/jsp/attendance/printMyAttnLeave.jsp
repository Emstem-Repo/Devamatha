<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script type="text/javascript" src="js/jquery.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function printPass() {	
	window.print();
}
</script>
<html:form action="/viewMyAttendanceLeave" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="viewMyAttendanceLeaveForm" />
	<html:hidden property="id" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
	<tr>
			<td colspan="6" height="10" class="row-white">
			<div align="center"><img
				src='<%=CMSConstants.LOGO_URL%>'
				alt="Logo not available"></div>
			<br></br>
			</td>
		</tr>
						<tr align="center">
								<td style="font-size: 20px;"><b >Attendance Slip</b></td>
								</tr>
							<tr align="center">
								<td>
								<table height="50">
								<tr>
									<td align="left"><strong><bean:message key="knowledgepro.attendanceentry.date"/> </strong></td>
									<td>:</td>
									<td><bean:write name="viewMyAttendanceLeaveForm"  property="attendanceLeaveDate" /></td>
								 </tr>
								<tr>
									<td align="left"><b>Teacher</b></td>
									<td>:</td>
									<td><bean:write name="viewMyAttendanceLeaveForm"  property="teacherName" /></td>
								 </tr>
							</table>
							</td>
						</tr>
						<tr>
							<td>
							<table style='border: 1px solid #000000' rules='all' width="100%">
								<tr>
									<td align="center" width="5%" ><b>Sl.No</b></td>
									<td align="center" width="10%"><b>Class</b></td>
									<td align="center" width="35%" height="25"><b> Subject</b></td>
									<td align="center" width="25%" ><b>Period</b></td>
									<td align="center" width="30%" ><b>Absentees</b></td>
								</tr>
								
								<logic:notEmpty name="viewMyAttendanceLeaveForm"
									property="leaveTo">
									<nested:iterate name="viewMyAttendanceLeaveForm" id="to"
										property="leaveTo" indexId="count">
												<tr>
													<td width="6%" height="25"  ><div align="center">
													<c:out value="${count + 1}"/></div></td>
													<td align="center" width="170"><nested:write
														 property="className" name="to"/></td>
													<td align="center" width="170" ><nested:write
														property="subjectName" name="to" /></td>
													<td align="center" width="170" ><nested:write
														 property="periodName" name="to" /></td>
													<td align="center" width="170" ><nested:write
														property="studentRegNo" name="to" /></td>
												</tr>
										</nested:iterate>
								</logic:notEmpty>
							</table>
							</td>
					
				</tr>
			</table>
</html:form>
<script type="text/javascript">printPass();</script>