<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">
	function cancelAction() {
		window.close();
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
				.open(url,"StudentAbsencePeriodDetailsAdmin",
						"left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
	}	
	function remarkEntry(studentId){
		var url = "studentWiseAttendanceSummary.do?method=addPrincipalCommnents"
		    + "&studentID=" 
		    + studentId
		myRef = window
				.open(url,"RemarkEntry",
						"left=20,top=20,width=850,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	function winOpenApproveLeavePeriods(attendanceID, attendanceTypeID, subjectId, studentId, leaveApproved) {
		var url = "studentWiseAttendanceSummary.do?method=getApprovedLeavePeriodDetails&attendanceID="
			+ attendanceID
			+ "&attendanceTypeId="
			+ attendanceTypeID
			+ "&subjectId="
			+ subjectId
		    + "&studentID=" 
		    + studentId
		    + "&leaveApproved="
		    + leaveApproved;
		myRef = window
				.open(url,"ApprovedLeavePeriodDetails",
						"left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
	}	
	
	
	</script>
<script language="JavaScript" src="js/calendarinterview.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css" type="text/css">

<html:form action="/studentWiseAttendanceSummary" >
<html:hidden property="formName" value="studentWiseAttendanceSummaryForm"/>
<html:hidden property="pageType" value="1"/>	
<c:set var="temp" value="0"/>

<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs">
			<span class="Bredcrumbs"><bean:message key="knowledgepro.attendance"/> &gt;&gt;
			<bean:message key="knowledgepro.attendance.check.student"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">  <bean:message key="knowledgepro.attendance.check.student"/> </strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>
										<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>

					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
					<tr>
						<td><img src="images/01.gif" width="5" height="5" /></td>
						<td width="914" background="images/02.gif"></td>
						<td><img src="images/03.gif" width="5" height="5" /></td>
					</tr>
					<tr>
					<td width="5" background="images/left.gif"></td>
					<td valign="top">
					<table width="100%" cellspacing="1" cellpadding="2">
					<logic:notEmpty name="studentWiseAttendanceSummaryForm" property="subjectwiseAttendanceTOList">
              <tr >
                <td width="22%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendanceentry.rollno"/> </div></td>
                <td width="35%" height="25" class="row-even" ><logic:notEmpty name="studentWiseAttendanceSummaryForm" property="student"> <bean:write  name="studentWiseAttendanceSummaryForm" property="student.rollNo" /> </logic:notEmpty></td>
                <td width="19%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendanceentry.regno"/> </div></td>
                <td width="35%" height="25" class="row-even" > <logic:notEmpty name="studentWiseAttendanceSummaryForm" property="student"><bean:write  name="studentWiseAttendanceSummaryForm" property="student.registerNo" /></logic:notEmpty>
				</td>
              </tr>
              <tr >
                <td width="22%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendance.student.name"/> </div></td>
                <td width="35%" height="25" class="row-even" ><logic:notEmpty name="studentWiseAttendanceSummaryForm" property="student"><bean:write  name="studentWiseAttendanceSummaryForm" property="student.admAppln.personalData.firstName" />   </logic:notEmpty>    </td>
                <td width="19%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendance.class.code"/> </div></td>
                <td width="35%" height="25" class="row-even" > <logic:notEmpty name="studentWiseAttendanceSummaryForm" property="student">
                <logic:notEmpty name="studentWiseAttendanceSummaryForm" property="student.classSchemewise">
                <bean:write  name="studentWiseAttendanceSummaryForm" property="student.classSchemewise.classes.name" /></logic:notEmpty> </logic:notEmpty>
				</td>
              </tr>
              <tr >
                <td width="22%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendance.father.name"/> </div></td>
                <td width="35%" height="25" class="row-even" ><logic:notEmpty name="studentWiseAttendanceSummaryForm" property="student"><bean:write  name="studentWiseAttendanceSummaryForm" property="student.admAppln.personalData.fatherName" />  </logic:notEmpty>     </td>
                <td width="19%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendance.mother.name"/> </div></td>
                <td width="35%" height="25" class="row-even" > <logic:notEmpty name="studentWiseAttendanceSummaryForm" property="student"><bean:write  name="studentWiseAttendanceSummaryForm" property="student.admAppln.personalData.motherName" /></logic:notEmpty>
				</td>
              </tr>
						
						<tr>
							<td height="25" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.slno"/></div>
							</td>
							<td height="25" class="row-odd"><bean:message key="knowledgepro.admisn.subject.Name"/> </td>
							<td class="row-odd">
							<div align="center"><bean:message key="knowledgepro.attendance.conducted"/> </div>
							</td>
							<td height="25" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.attendance.present"/> </div>
							</td>
							<td height="25" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.attendance.absent"/> </div>
							</td>							
							<td height="25" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.attendance.approved.leave"/> </div>
							</td>
							<td height="25" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.attendance.approved.cocurricularLeave"/> </div>
							</td>
							<td height="25" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.attendance.percentage.with.leave"/> </div>
							</td>
							<td height="25" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.attendance.percentage.without.leave"/> </div>
							</td>
							<td height="25" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.attendance.percentage.with.cocurricularLeave"/> </div>
							</td>
							<td height="25" class="row-odd">
								<div align="center">Total Percentage </div>
							</td>	
							<td height="25" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.attendance.exam.max.mark.label"/> </div>
							</td>
							<td height="25" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.attendance.exam.min.mark.label"/> </div>
							</td>
													
							<c:set var = "found" value="0"/>
							<%int examCount = 0; %>
							<logic:iterate id="markHead" name = "studentWiseAttendanceSummaryForm" property="subjectwiseAttendanceTOList" indexId="count">
								<c:if test="${found == 0}">
									<logic:notEmpty name = "markHead" property="examMarksEntryDetailsTOList">
									<logic:iterate id="markHead1" name = "markHead" property="examMarksEntryDetailsTOList" >
									<td height="25" class="row-odd">
										<div align="center"><bean:write name = "markHead1" property="examCode"/> </div>
										<c:set var = "found" value="1"/>
										<%examCount = examCount + 1; %>
									</td>
									</logic:iterate>
									</logic:notEmpty>
								</c:if>
							</logic:iterate>
							
						</tr>							
					<logic:iterate name="studentWiseAttendanceSummaryForm" property="subjectwiseAttendanceTOList"
							id="id" indexId="count">
                             <c:choose>
						 <c:when test="${temp == 0}">
									<tr class="row-white">
										<td width="7%" height="25" >
										<div align="center"><c:out value="${count+1}" /></div>
										</td>
										<td width="40%" height="25" ><bean:write
											name="id" property="subjectName" /></td>
										<td width="10%" height="25"  align="center"><bean:write
											name="id" property="conductedClasses" /></td>
										<td width="10%" height="25" align="center"><bean:write
											name="id" property="classesPresent" /></td>
										<td width="10%" height="25" align="center">
										<A HREF="javascript:winOpen('<bean:write name="id" property="attendanceID" />',
										'<bean:write name="id" property="attendanceTypeID" />',
										'<bean:write name="id" property="subjectID" />',
										'<bean:write name="studentWiseAttendanceSummaryForm" property="studentID" />',
										'<bean:write name="id" property="classesAbsent" />');">										
										<bean:write name="id" property="classesAbsent" /></A></td>
										<td width="10%" height="25"  align="center">
										<A HREF="javascript:winOpenApproveLeavePeriods('<bean:write name="id" property="attendanceID" />',
										'<bean:write name="id" property="attendanceTypeID" />',
										'<bean:write name="id" property="subjectID" />',
										'<bean:write name="studentWiseAttendanceSummaryForm" property="studentID" />',
										'<bean:write name="id" property="leaveApproved" />');">										
										<bean:write name="id" property="leaveApproved" /></A></td>

										<td width="10%" height="25" align="center"><bean:write
											name="id" property="cocurricularLeave" /></td>

										<td width="10%" height="25"  align="center"><bean:write
											name="id" property="percentageWithLeave" /></td>		
										<td width="10%" height="25"  align="center"><bean:write
											name="id" property="percentageWithoutLeave" /></td>
										<td width="10%" height="25" align="center"><bean:write
											name="id" property="percentageWithCocurricularLeave" /></td>
										<td width="10%" height="25"  align="center"><bean:write
											name="id" property="totalAttPercentage" /></td>			
										<td width="10%" height="25" align="center"><bean:message
											key = "knowledgepro.attendance.exam.max.mark" /></td>		
										<td width="10%" height="25"  align="center"><bean:message 
											key="knowledgepro.attendance.exam.min.mark"/></td>
											<logic:notEmpty name="id" property="examMarksEntryDetailsTOList">
											<logic:iterate id="mark" name = "id" property="examMarksEntryDetailsTOList">
											<td width="10%" height="25" align="center"><bean:write 
											name = "mark" property="theoryMarks"/></td>
											
											</logic:iterate>
											</logic:notEmpty>
											<logic:empty name="id" property="examMarksEntryDetailsTOList">
											<%if(examCount > 0){
												for(int i=1;i<=examCount; i++){%>
												<td width="10%" height="25" align="center">&nbsp;</td>	
											<%} } %>
											
											</logic:empty>
										</tr>
									<c:set var="temp" value="1"/>
                              </c:when>
							<c:otherwise>
								<tr class="row-even">
									<td width="7%" height="25" >
										<div align="center"><c:out value="${count+1}" /></div>
										</td>
										<td width="40%" height="25" align="left"><bean:write
											name="id" property="subjectName" /></td>
										<td width="10%" height="25" align="center"><bean:write
											name="id" property="conductedClasses" /></td>
										<td width="10%" height="25" align="center"><bean:write
											name="id" property="classesPresent" /></td>
										<td width="10%" height="25"  align="center">
										<A HREF="javascript:winOpen('<bean:write name="id" property="attendanceID" />',
										'<bean:write name="id" property="attendanceTypeID" />',
										'<bean:write name="id" property="subjectID" />',
										'<bean:write name="studentWiseAttendanceSummaryForm" property="studentID" />',
										'<bean:write name="id" property="classesAbsent" />');">	
										<bean:write
											name="id" property="classesAbsent" /></A></td>

										<td width="10%" height="25" align="center">
										<A HREF="javascript:winOpenApproveLeavePeriods('<bean:write name="id" property="attendanceID" />',
										'<bean:write name="id" property="attendanceTypeID" />',
										'<bean:write name="id" property="subjectID" />',
										'<bean:write name="studentWiseAttendanceSummaryForm" property="studentID" />',
										'<bean:write name="id" property="leaveApproved" />');">										
										<bean:write name="id" property="leaveApproved" /></A></td>

										<td width="10%" height="25" class="row-even" align="center"><bean:write
											name="id" property="cocurricularLeave" /></td>

											<td width="10%" height="25" align="center"><bean:write
											name="id" property="percentageWithLeave" /></td>		
											<td width="10%" height="25" align="center"><bean:write
											name="id" property="percentageWithoutLeave" /></td>
											<td width="10%" height="25" class="row-even" align="center"><bean:write
											name="id" property="percentageWithCocurricularLeave" /></td>
										<td width="10%" height="25"  align="center" class="row-even"><bean:write
											name="id" property="totalAttPercentage" /></td>												
										<td width="10%" height="25"  align="center"><bean:message
											key = "knowledgepro.attendance.exam.max.mark" /></td>		
										<td width="10%" height="25"  align="center"><bean:message 
											key="knowledgepro.attendance.exam.min.mark"/></td>											
											<logic:notEmpty name="id" property="examMarksEntryDetailsTOList">
											<logic:iterate id="mark" name = "id" property="examMarksEntryDetailsTOList">
											<td width="10%" height="25" align="center"><bean:write 
											name = "mark" property="theoryMarks"/></td>
											</logic:iterate>
											</logic:notEmpty>
											<logic:empty name="id" property="examMarksEntryDetailsTOList">
											<%if(examCount > 0){
												for(int i=1;i<=examCount; i++){%>
												<td width="10%" height="25" align="center">&nbsp;</td>	
											<%} } %>
											
											</logic:empty>
											
								</tr>
								<c:set var="temp" value="0" />
							</c:otherwise>
							</c:choose>
						</logic:iterate> 	
						</logic:notEmpty>							
						</table>
						</td>
						<td width="5" height="29" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="bottom" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td width="10%" height="25" class="row-even" align="center">
								<A HREF="javascript:remarkEntry(
								'<bean:write name="studentWiseAttendanceSummaryForm" property="studentID" />');">	
								Remark Entry</A></td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="bottom" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="2" align="center"><html:button  property=""
									styleClass="formbutton" value="Close" onclick="cancelAction()" /></td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>

		</tr>
	</table>

</html:form>