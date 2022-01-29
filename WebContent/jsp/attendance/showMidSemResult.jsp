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

<table width="1900" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="172">&nbsp;</td>
    <td width="37">&nbsp;</td>
    <td width="269">&nbsp;</td>
    <td width="30">&nbsp;</td>
    <td width="422">&nbsp;</td>
  </tr>
  <tr>
   
    <td colspan="3" valign="top">
    <table width="100%" border="0">
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message key="knowledgepro.attendance.student.login"/></strong></td>

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
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
								<div id="errorMessage" align="left">
	                       			<FONT color="black" size="2px">
									<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
									</html:messages>
						  			</FONT></div>
							<logic:equal value="false" name="studentWiseAttendanceSummaryForm" property="markPublished">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="7%" class="studentrow-odd">
									<div align="center"><bean:message key="knowledgepro.studentlogin.attendance.slno"/></div>
									</td>
									<td width="40%" class="studentrow-odd"><bean:message key="knowledgepro.admisn.subject.Name"/> </td>
									
									<c:set var = "found" value="0"/>
									<%int examCount = 0; %>
									<%int examHead = 0; %>
									<logic:notEmpty name = "studentWiseAttendanceSummaryForm" property="subjectListWithMarks">
									<logic:iterate id="markHead" name = "studentWiseAttendanceSummaryForm" property="subjectListWithMarks" indexId="count">
										<c:if test="${found == 0}">
											<logic:notEmpty name = "markHead" property="examMarksEntryDetailsTOList">
											<logic:iterate id="markHead1" name = "markHead" property="examMarksEntryDetailsTOList" >
											<td height="25" class="studentrow-odd" width="10%">
											<table width="100%">
											<tr>
											<td>
												<div align="center"><bean:write name = "markHead1" property="examCode"/> </div>
												<c:set var = "found" value="1"/>
												<%examCount = examCount + 1; %>
												<%examHead = examHead + 1; %>
											</td>
											</tr>
											<tr>
											<td width = "50%" align="left">T</td> <td width = "50%" align="right">P</td>
											</tr>
										
											</table>
											</td>
											</logic:iterate>
											
											</logic:notEmpty>
										</c:if>
									</logic:iterate>
									</logic:notEmpty>
								</tr>
								<logic:notEmpty name="studentWiseAttendanceSummaryForm" property="subjectListWithMarks">
								<logic:iterate name="studentWiseAttendanceSummaryForm" property="subjectListWithMarks" id="id" indexId="count">
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
									
									<logic:notEmpty name="id" property="examMarksEntryDetailsTOList">
										<logic:iterate id="mark" name = "id" property="examMarksEntryDetailsTOList">
										
											<td height="25" width="10%">
											<table width="100%">
											<tr>
											<td width="50%" align="left">
											<bean:write name = "mark" property="theoryMarks"/></td>
											<td width="50%" align="right"> <bean:write 
											name = "mark" property="practicalMarks"/>
											</td>
											</tr>
											</table>
											</td>
										</logic:iterate>
									</logic:notEmpty>
										<logic:empty name="id" property="examMarksEntryDetailsTOList">
											<%if(examCount > 0){
												for(int i=1;i<=examCount; i++){%>
												<td width="10%" height="25" align="center">&nbsp;</td>	
											<%} } %>
										</logic:empty>
								</logic:iterate>
								</logic:notEmpty>
								<tr>
									<td  class="studentrow-odd"></td>
									<td  class="studentrow-odd"></td>
									<%for(int i = 0; i<examHead;i++) {%>
										<td  class="studentrow-odd"></td>
									<%}%>
								</tr>
								
							</table>
							
							<table>
								<tr>
									<td class="heading">
									<br/>
										<bean:message key="knowledgepro.marks.studentLogin.theory"/>						
									</td>
									</tr>
									<tr>
										<td class="heading">
											<bean:message key="knowledgepro.marks.studentLogin.practical"/>
										</td>
									</tr>
							</table>
							</logic:equal>	
							<table>
									<tr class="row-white">
										<td></td> <td width="80%"></td>
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
			</td>

		</tr>
	</table>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="bg_img.gif">
  <tr>
    <td height="32" align="center" class="copyright">Copyrights @ 2009 Knowledge Pro All rights reserved. </td>
  </tr>
</table>
</td>
</tr>
</table>

</html:form>
