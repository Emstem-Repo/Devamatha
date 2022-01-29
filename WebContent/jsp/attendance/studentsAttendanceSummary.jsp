<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<%@page import="com.kp.cms.constants.CMSConstants"%>
	<script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	function winOpen(attendanceID, attendanceTypeID, subjectId, studentId, classesAbsent) {
		var url = "studentWiseAttendanceSummary.do?method=getAbsencePeriodDetails&attendanceID="
			+ attendanceID
			+ "&attendanceTypeId="
			+ attendanceTypeID
			+ "&subjectId="
			+ subjectId
		    + "&studentID=" 
		    + studentId
		    + "&classesAbsent="
		    + classesAbsent;
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
			+ "&attendanceTypeName="
			+ attendanceTypeName;
		myRef = window
				.open(url,"StudentAbsencePeriodDetails",
						"left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	</script>
<html:form action="/studentWiseAttendanceSummary" >

<html:hidden property="formName" value="studentWiseAttendanceSummaryForm"/>
<html:hidden property="pageType" value="1"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Attendance</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%">
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>

							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr><b><font size="3%">Subject wise  Attendance Percentage</font></b></tr>
						
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="1">
								<tr>
									<td width="7%" class="studentrow-odd">
									<div align="center"><bean:message key="knowledgepro.studentlogin.attendance.slno"/></div>
									</td>
									<td width="40%" class="studentrow-odd"><bean:message key="knowledgepro.admisn.subject.Name"/> </td>
									<td width="10%" height="25" align="center"  colspan="5" class = "studentrow-odd">
									 <table width="100%" cellspacing="1" cellpadding="1" border="0" >
										 <tr>
											<td  width="10%" class="studentrow-odd"><bean:message key="knowledgepro.attendance.type"/> </td>
											<td  class="studentrow-odd" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total Hours Conducted  </div>
											</td>
											<td  class="studentrow-odd" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Present Hours </div>
											</td>
											<td  class="studentrow-odd" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Absent Hours </div>
											</td>
											<td  class="studentrow-odd" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Subject Wise Attendence Percentage </div>
											</td>
											
										</tr>
									</table>
									</td>
									
									<c:set var = "found" value="0"/>
									<%int examCount = 0; %>
								</tr>
								<logic:notEmpty name="studentWiseAttendanceSummaryForm" property="subjectwiseAttendanceTOList">
								<logic:iterate name="studentWiseAttendanceSummaryForm" property="subjectwiseAttendanceTOList" id="id" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="studentrow-even">
										</c:when>
										<c:otherwise>
											<tr class="studentrow-white">
										</c:otherwise>
									</c:choose>
									
									
																
									<td width="7%" height="25" >
									<div align="center"><c:out value="${count+1}" /></div>
									</td>
									<td width="40%" height="25" ><bean:write
										name="id" property="subjectName" /></td>
									
									<td width="10%" height="25" align="center"  colspan="5">
									 <table width="100%" cellspacing="1" cellpadding="0" border="0">
									 <logic:notEmpty name="id" property="attendanceTypeList">	
									<logic:iterate name="id" id="type" property="attendanceTypeList">
									<tr>
									<td width="20%" height="25" align="center">
									<bean:write	name="type" property="attendanceTypeName" />
									</td>
									<td width="20%" height="25" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write
										name="type" property="conductedClassesHours" /></td>
									<td width="20%" height="25" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write
										name="type" property="classesPresentHours" /></td>
									<td width="20%" height="25" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<logic:equal name="type" property="flag" value="false">
									<bean:write	name="type" property="classesAbsentHours" />
<!--									<A HREF="javascript:winOpen('<bean:write name="type" property="attendanceID" />','<bean:write name="type" property="attendanceTypeID" />','<bean:write name="type" property="subjectId" />','<bean:write name="type" property="studentId" />','<bean:write name="type" property="classesAbsent" />');">-->
<!--									<bean:write	name="type" property="classesAbsent" /></A>-->
									</logic:equal>
									<logic:equal name="type" property="flag" value="true">
									<bean:write	name="type" property="classesAbsentHours" />
<!--									<A HREF="javascript:activityOpens('<bean:write name="type" property="activityID" />','<bean:write name="type" property="studentId" />','<bean:write name="type" property="classesAbsent" />','<bean:write name="type" property="attendanceTypeName" />');">-->
<!--											<bean:write name="type" property="classesAbsent" /></A>-->
									</logic:equal>
									</td>
									<td height="25" align="center" width="20%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write
										name="type" property="subjectHrspercentage" /></td>
									</tr>
									</logic:iterate>
									</logic:notEmpty>
									</table>												
									</td>	
									
								</logic:iterate>
								</logic:notEmpty>
<!--								<tr>-->
<!--									<td height="25%" colspan="2" class="studentrow-odd">-->
<!--									<div align="center">Total</div>-->
<!--									</td>-->
<!--									<td width="10%" height="25" align="center"  colspan="5" class = "studentrow-odd">-->
<!--									 <table width="100%" cellspacing="0" cellpadding="0" border="0" >-->
<!--										 <tr>-->
<!--											<td  width="15%" class="studentrow-odd"> </td>-->
<!--											<td  class="studentrow-odd" width="20%">-->
<!--											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <bean:write name="studentWiseAttendanceSummaryForm" property="totalConducted"/></div>-->
<!--											</td>-->
<!--											<td  class="studentrow-odd" width="20%">-->
<!--											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="studentWiseAttendanceSummaryForm" property="totalPresent"/> </div>-->
<!--											</td>-->
<!--											<td  class="studentrow-odd" width="20%">-->
<!--											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="studentWiseAttendanceSummaryForm" property="totalAbscent"/> </div>-->
<!--											</td>-->
<!--											<td  class="studentrow-odd" width="20%">-->
<!--											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>-->
<!--											</td>-->
<!--										</tr>-->
<!--									</table>-->
<!--									</td>-->
<!--									<c:set var = "found" value="0"/>-->
<!--									<%int examCount2 = 0; %>-->
<!--									-->
<!--								</tr>-->
<!--								<tr>-->
<!--									<td height="25%" colspan="2" class="studentrow-odd">-->
<!--									<div align="center">Total Percentage</div>-->
<!--									</td>-->
<!--									<td width="10%" height="25" align="center"  colspan="5" class = "studentrow-odd">-->
<!--									 <table width="100%" cellspacing="0" cellpadding="0" border="0" >-->
<!--										 <tr>-->
<!--											<td  width="15%" class="studentrow-odd"> </td>-->
<!--											<td  class="studentrow-odd" width="20%">-->
<!--											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>-->
<!--											</td>-->
<!--											<td  class="studentrow-odd" width="20%">-->
<!--											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>-->
<!--											</td>-->
<!--											<td  class="studentrow-odd" width="20%">-->
<!--											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>-->
<!--											</td>-->
<!--											<td  class="studentrow-odd" width="20%">-->
<!--											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="studentWiseAttendanceSummaryForm" property="totalPercentage"/> </div>-->
<!--											</td>-->
<!--										</tr>-->
<!--									</table>-->
<!--									</td>-->
<!--									<c:set var = "found" value="0"/>-->
<!--									<%int examCount1 = 0; %>-->
<!--									-->
<!--								</tr>-->
							</table>
							<table>
									<tr height="10px"></tr>
									</table>
							
							<table>
								<tr valign="middle"><b><font size="3%">Total Attendance Percentage</font></b></tr>	
								<tr height="10px">
								</tr>
							<tr>
							<td width="15%" height="25" class="studentrow-odd">
									<div align="center">Total No. Of Working Days</div>
									</td>
									<td width="15%" height="35"  class="studentrow-odd"><div align="center">Total No. Of Days Present </div> </td>
									<td width="15%" height="35"  class="studentrow-odd"><div align="center">Total No. Of Days Absent</div> </td>
									<td width="15%" height="35"   class="studentrow-odd"><div align="center">Total Attendence Percentage</div> </td>
							</tr>
							<tr>
							<td width="15%" height="35" class="studentrow-odd">
							<div align="center"><bean:write name="studentWiseAttendanceSummaryForm" property="totalWorkingDays"/></div>
							</td>
							<td width="15%" height="35" class="studentrow-odd">
							<div align="center"><bean:write name="studentWiseAttendanceSummaryForm" property="totalPresentDays"/></div>
							</td>
							<td width="15%" height="35" class="studentrow-odd">
							<div align="center"><bean:write name="studentWiseAttendanceSummaryForm" property="totalAbsentDays"/></div>
							</td>
							<td width="15%" height="35" class="studentrow-odd">
							<div align="center"><bean:write name="studentWiseAttendanceSummaryForm" property="totalCurrPercentage"/></div>
							</td>
							</tr>
							</table>
							
							<table>
							<% if(!CMSConstants.LINK_FOR_CJC){ %>
								<tr>
									<td class="heading">
										<bean:message key="knowledgepro.attendance.studentLogin.percentage"/>						
									</td>
									</tr>
									<%} %>
									<tr>
										<td class="heading">
										<br/>
											<bean:message key="knowledgepro.show.attendance.message"/>
										</td>
									</tr>
									<tr>
										<td class="heading">
											<bean:message key="knowledgepro.show.attendance.totalmessage"/>
										</td>
									</tr>
									<tr class="row-white">
                   						<td colspan="2"><div align="center">
										<html:button property="" value="Cancel" styleClass="btnbg" onclick="cancelAction()"></html:button>
										</div></td>
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
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>

</html:form>
