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
		document.location.href = "studentWiseAttendanceSummary.do?method=initTeacherClassWiseAttendanceNotTakenSummary";
	}
	</script>
<html:form action="/studentWiseAttendanceSummary" >
<html:hidden property="formName" value="studentWiseAttendanceSummaryForm"/>
<html:hidden property="pageType" value="1"/>

<table width="100%" border="0">
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><strong
						class="boxheader">Teacher Attendance Not Taken Details</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%">
					
					<FONT color="black" size="2px">
					 <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT>
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
							<table width="100%" cellspacing="1" cellpadding="1" border="0" >
									 <logic:empty name="studentWiseAttendanceSummaryForm" property="attDateList">
									 	No Records
									 </logic:empty>
									 <logic:notEmpty name="studentWiseAttendanceSummaryForm" property="attDateList">
										 	<tr>
										 	
										 	<logic:equal name="studentWiseAttendanceSummaryForm" property="isFaculty" value="false">
										 	<td height="30" align="center" width="10%" class="row-odd">
											<div align="center">Teacher</div>
											</td>
											</logic:equal>
											
											<td height="30" align="center" width="20%" class="row-odd">
											<div align="center">Class</div>
											</td>
											
											<td height="30" align="center" width="8%" class="row-odd">
											<div align="center">Date</div>
											 </td>
											<td height="30" align="center" width="8%" class="row-odd">
											<div align="center">Day</div>
											</td>
											
											<td height="30" align="center" width="46%" class="row-odd">
											<div align="center">Subject</div>
											</td>
											<td height="30" align="center" width="8%" class="row-odd">
											<div align="center">Period</div>
											</td>
											</tr>
											
											<logic:iterate  name="studentWiseAttendanceSummaryForm" property="attDateList" id="attDateList">
												<tr>
												
												<logic:equal name="studentWiseAttendanceSummaryForm" property="isFaculty" value="false">
										 		<td height="25" width="10%" class="row-even">
												<div align="center">
													<bean:write name="attDateList" property="teacherName"/>
													
												</div>
												</td>
												</logic:equal>
												
												<td height="25" width="20%" class="row-even">
												<div align="center">
													<bean:write name="attDateList" property="className"/>
													
												</div>
												</td>
												
												<td height="25" width="8%" class="row-even">
												<div align="center">
													<bean:write name="attDateList" property="date"/>
												</div>
												</td>
												<td height="25" width="8%" class="row-even">
												<div align="center">
													<bean:write name="attDateList" property="day"/>
												</div>
												</td>
												
												<td height="25" width="46%" class="row-even">
												<div align="center">
													<bean:write name="attDateList" property="subjectName"/>
												</div>
												</td>
												<td height="25" width="8%" class="row-even">
												<div align="center">
													<bean:write name="attDateList" property="periodName"/>
												</div>
												</td>
												</tr>
											</logic:iterate>
											
										
										</logic:notEmpty>
									</table>
										
											
											
											
										
									
							<table>
								<tr class="row-white">
								<td width="80%"></td><td width="25"></td>
                   						<td colspan="2"><div align="center">
										<html:button property="" value="Cancel" styleClass="btnbg" onclick="cancelAction()"></html:button>
										</div></td>
                 					</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/Tright_3_3.gif"></td>
						</tr>
						<tr>

							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        				<td class="heading"></td>      
	       					 <td valign="top" background="images/Tright_3_3.gif" ></td>
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
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>

		</tr>
	</table>
</html:form>