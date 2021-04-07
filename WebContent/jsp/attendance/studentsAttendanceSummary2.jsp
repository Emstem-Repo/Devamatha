<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
<script type="text/javascript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
<!--<script type="text/javascript" src="js/leftSwitchMenu.js"></script>
--><!--<script type="text/javascript" src="js/common.js"></script>
	--><%@page import="com.kp.cms.constants.CMSConstants"%>
<script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	function winOpen(attendanceID, attendanceTypeID, subjectId, studentId, classesAbsent,attendanceTypeName) {
		var url = "studentWiseAttendanceSummary.do?method=getAbsencePeriodDetails&attendanceID="
			+ attendanceID
			+ "&attendanceTypeId="
			+ attendanceTypeID
			+ "&subjectId="
			+ subjectId
		    + "&studentID=" 
		    + studentId
		    + "&classesAbsent="
		    + classesAbsent
			+ "&attendanceTypeName="
			+ attendanceTypeName
		    ;
		myRef = window
				.open(url,"StudentAbsencePeriodDetails",
						"left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
	}

	function activityOpens(activityId, studentId, classesAbsent,attendanceTypeName) {
		var url = "studentWiseAttendanceSummary.do?method=getActivityAbsencePeriodDetails&activityId="
			+ activityId
			+ "&studentID="
			+ studentId
		    + "&classesAbsent="
		    + classesAbsent
		    +"&attendanceTypeName="
		    + attendanceTypeName;
		myRef = window
				.open(url,"StudentAbsencePeriodDetails",
						"left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	</script>
<html:form action="/studentWiseAttendanceSummary">

	<html:hidden property="formName"
		value="studentWiseAttendanceSummaryForm" />
	<html:hidden property="pageType" value="1" />
	<!--<head>
	<style>
table { 
    table-layout:fixed;
     overflow: hidden; 
    text-overflow: ellipsis; 
    word-wrap: break-word;
}
td { 
    overflow: hidden; 
    text-overflow: ellipsis; 
    word-wrap: break-word;
}
	
	</style>
	
	
	</head>
	--><div class="pageheader">
 
 </div>
	
	<div class="panel-body">
	<div class="panel panel-primary-head">
	
	<div class="panel-heading">
	<h1 class="panel-title">Attendance</h1>
	</div>
	</div>
<div class="table-responsive">
                  <table id="basicTable" class="table table-bordered table-striped" width="100%" >
	
		
			<tr>
				<th>
				<div align="center">Sl No</div></th>

				<th><div align="center"><bean:message key="knowledgepro.admisn.subject.Name" /></div></th>
						<th><div align="center"><bean:message key="knowledgepro.attendance.type" /></div></th>
						<th>
						<div align="center"><bean:message
							key="knowledgepro.attendance.conducted" /></div>
						</th>
						<th>
						<div align="center"><bean:message
							key="knowledgepro.attendance.present" /></div>
						</th>
						<th>
						<div align="center"><bean:message
							key="knowledgepro.attendance.absent" /></div>
						</th>
						<th>
						<div align="center"><bean:message
							key="knowledgepro.admission.totalmarks" /></div>
						</th>
				</tr>	
				
<tr>
				<c:set var="temp" value="0" /> <logic:notEmpty
					name="studentWiseAttendanceSummaryForm"
					property="subjectwiseAttendanceTOList">
					<logic:iterate name="studentWiseAttendanceSummaryForm"
						property="subjectwiseAttendanceTOList" id="id" indexId="count">
						<c:choose>
							<c:when test="${temp == 0}">
								
									<td>
									<div align="center"><c:out value="${count+1}" /></div>
									</td>
									<td><div align="center"><bean:write
										name="id" property="subjectName" /></div></td>

									
									
										<logic:notEmpty name="id" property="attendanceTypeList">
											<logic:iterate name="id" id="type"
												property="attendanceTypeList" indexId="counter"><!--
												<tr>
													--><td>
													<div align="center"><bean:write name="type"
														property="attendanceTypeName" /></div></td>
													<td><div align="center"><bean:write name="type"
														property="conductedClasses" /></div></td>
													<td><div align="center"><bean:write name="type" property="classesPresent" /></div></td>
													<td><div align="center">
													<logic:equal name="type" property="flag"
														value="false">
														<A
															HREF="javascript:winOpen('<bean:write name="type" property="attendanceID" />','<bean:write name="type" property="attendanceTypeID" />','<bean:write name="type" property="subjectId" />','<bean:write name="type" property="studentId" />','<bean:write name="type" property="classesAbsent" />','<bean:write name="type" property="attendanceTypeName" />');">
														<bean:write name="type" property="classesAbsent" /></A>
													</logic:equal> 
													<logic:equal name="type" property="flag" value="true">
														<A
															HREF="javascript:activityOpens('<bean:write name="type" property="activityID" />','<bean:write name="type" property="studentId" />','<bean:write name="type" property="classesAbsent" />','<bean:write name="type" property="attendanceTypeName" />');">
														<bean:write name="type" property="classesAbsent" /></A>
													</logic:equal></div></td>
													<td><div align="center"><bean:write name="type" property="percentage" /></div></td>
												<!--</tr>
											--></logic:iterate>
										</logic:notEmpty>
									<tr>
								<c:set var="temp" value="1" />
							</c:when>
							<c:otherwise>
							
								
									<td >
									<div align="center"><c:out value="${count+1}" /></div>
									</td>
									<td><div align="center"><bean:write
										name="id" property="subjectName" /></div></td>

									
										
									
										<logic:notEmpty name="id" property="attendanceTypeList">
											<logic:iterate name="id" id="type"
												property="attendanceTypeList">
												<!--<tr>
													--><td ><div align="center">
													<bean:write name="type" property="attendanceTypeName" /></div></td>
													<td ><div align="center"><bean:write
														name="type" property="conductedClasses" /></div></td>
													<td ><div align="center"><bean:write
														name="type" property="classesPresent" /></div></td>
													<td ><div align="center">
													<logic:equal name="type" property="flag" value="false">
														<A
															HREF="javascript:winOpen('<bean:write name="type" property="attendanceID" />','<bean:write name="type" property="attendanceTypeID" />','<bean:write name="type" property="subjectId" />','<bean:write name="type" property="studentId" />','<bean:write name="type" property="classesAbsent" />','<bean:write name="type" property="attendanceTypeName" />');">
														<bean:write name="type" property="classesAbsent" /></A>
													</logic:equal> <logic:equal name="type" property="flag" value="true">
														<A
															HREF="javascript:activityOpens('<bean:write name="type" property="activityID" />','<bean:write name="type" property="studentId" />','<bean:write name="type" property="classesAbsent" />','<bean:write name="type" property="attendanceTypeName" />');">
														<bean:write name="type" property="classesAbsent" /></A>
													</logic:equal></div></td>
													<td ><div align="center">
													<bean:write name="type" property="percentage" /></div></td>
												<!--</tr>
											-->
											<tr></logic:iterate>
										</logic:notEmpty>
									
								
								
								<c:set var="temp" value="0" />
							</c:otherwise>
							
						</c:choose>
					</logic:iterate>
				</logic:notEmpty>
				</tr>
				<tr>
					<td height="25" colspan="2" class="row">
					<div align="center">Total</div>
					</td>
					<!--<td width="10%" height="25" align="center" colspan="5" class="row">
				
						
							--><td ></td>
							<td >
							<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <bean:write
								name="studentWiseAttendanceSummaryForm"
								property="totalConducted" /></div>
							</td>
							<td >
							<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <bean:write
								name="studentWiseAttendanceSummaryForm" property="totalPresent" /></div>
							</td>
							<td>
							<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write
								name="studentWiseAttendanceSummaryForm" property="totalAbscent" />
							</div>
							</td>
							<td>
							<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
							</td>
						</tr>
					<tr>
						<td height="25" colspan="2" class="row">
						<div align="center">Total Percentage</div>
						</td>
						
						
							
								<td width="10%" class="row"></td>
								<td >
								<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
								</td>
								<td>
								<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
								</td>
								<td >
								<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
								</td>
								<td>
								<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write
									name="studentWiseAttendanceSummaryForm"
									property="totalPercentage" /></div>
								</td>
							</tr>
						
						
						
	</table>
	</div>
	<div class="form-group">
	<div class="col-sm-12 text-center">
		<% if(!CMSConstants.LINK_FOR_CJC){ %>
	<div class="col-sm-10">
			<div align="left"><bean:message
				key="knowledgepro.attendance.studentLogin.percentage" /></div>
		
		<%} %>
		
			<div align="left"><br />
			<bean:message key="knowledgepro.show.attendance.message" /></div>
			<div align="left">
		<br />
			<bean:message
				key="knowledgepro.show.attendance.totalmessage" /></div>
		</div>

</div>
	</div>
	
	<div class="col-sm-12 text-center">
	<div class="btn btn-danger btn-lg"><span class="form-controler"
		onclick=cancelAction();>Cancel</span></div>
	</div>
	
	</div>
	<!--<div class="footer">
	<p>Copyrights @ 2009 Knowledge Pro All rights reserved.</p>
	</div>
--></html:form>
