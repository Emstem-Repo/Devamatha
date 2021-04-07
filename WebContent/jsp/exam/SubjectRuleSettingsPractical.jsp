<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function back(){
	document.location.href = "subjectRuleSettings.do?method=backTheoryEse";
}
</script>
<html:form action="/subjectRuleSettings" focus="programType">
	<html:hidden property="method" styleId="method" value="practicalESE" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="subjectRuleSettingsForm" />
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam.exam" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.subjectrulesettings" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.exam.subjectrulesettings" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>&nbsp;
									<bean:message key="knowledgepro.exam.studentEligibilityEntry.academicYear" />:
									</div>
									</td>
									<td height="25" class="row-even">
										<bean:write name="subjectRuleSettingsForm" property="academicYear"/>
									</td>
									<td   class="row-odd">
									<div align="right"><span class="Mandatory"></span>
									&nbsp;<bean:message key="knowledgepro.admin.programtype" />:
									</div>
									</td>
									<td class="row-even">
										<bean:write name="subjectRuleSettingsForm" property="programTypeName"/>
									</td>
								</tr>
								<tr>
									<td  class="row-odd">
									<div align="right"><span class="Mandatory"></span>&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td class="row-even">
									<c:choose>
										<c:when test="${subjectRuleSettingsForm.methodType=='update'}">
										<bean:write name="subjectRuleSettingsForm" property="course"/>
										</c:when>
										<c:otherwise>
										<bean:write name="subjectRuleSettingsForm" property="courseName"/>
										</c:otherwise>
									</c:choose>
									
										
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>&nbsp;
									<c:if test="${subjectRuleSettingsForm.methodType=='update'}">
										<bean:message key="knowledgepro.admin.detailsubject.subjectname" />
									</c:if>
									</div>
									</td>
									<td class="row-even">
										<c:if test="${subjectRuleSettingsForm.methodType=='update'}">
											<bean:write name="subjectRuleSettingsForm" property="subject"/>
										</c:if>
									</td>
								</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td class="heading"><img src="images/practical_internal.gif"
						alt="CMS" width="790" height="33" border="0" usemap="#Map">
					<map name="Map">
						<area shape="rect" coords="238,8,356,28"
							onclick="practicalInternal()">
						<area shape="rect" coords="372,4,472,27" onclick="practicalESE()">
						<area shape="rect" coords="128,5,228,28" onclick="theoryESE()">
						<area shape="rect" coords="10,5,115,28"
							onclick="theoryPracticalInternal()">
						<area shape="rect" coords="128,5,228,28" href="#">
						<area shape="rect" coords="11,5,111,28" href="#">
						<area shape="rect" coords="488,4,588,27" onclick="subjectFinal()">
					</map></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="heading">Practical : Internal</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td>
							<table width="100%" border="0"  cellspacing="1" cellpadding="1">
							<tr>
								<td colspan="4"> <bean:message key="knowledgepro.exam.subjectRuleSettings.subInternal.label"/> </td>
							</tr>
							<tr class="row-odd">
								<td height="25" >TYPE</td>
								<td height="25" >Minimum Marks</td>
								<td height="25">Maximum Marks</td>
								<td height="25"> (Marks entered in)Entry Maximum mark</td>
							</tr>
							<logic:notEmpty name="subjectRuleSettingsForm" property="pesto.subInternalList">
								<nested:iterate id="subint" name="subjectRuleSettingsForm" property="pesto.subInternalList" indexId="count">
									<c:choose>
										<c:when test="${count%2==0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td height="25">
										<bean:write name="subint" property="type"/>
									</td>
									<td height="25">
										<nested:text property="minimumMarks" styleId="minimumMarks" size="10" maxlength="6" onkeypress="return isDecimalNumberKey(this.value,event)"/>
									</td >
									<td height="25">
										<nested:text property="maximumMarks" styleId="maximumMarks" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/>
									</td>
									<td height="25">
										<nested:text property="entryMaximumMarks" styleId="entryMaximumMarks" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/>
									</td>
									</tr>
								</nested:iterate>
							</logic:notEmpty>
							<tr class="row-odd">
								<td height="25" >TOTAL</td>
								<td height="25" ><nested:text name="subjectRuleSettingsForm" property="pesto.totalMinimummumMarks" styleId="totalMinimummumMarks" size="10" maxlength="6" onkeypress="return isDecimalNumberKey(this.value,event)"/></td>
								<td height="25"><nested:text name="subjectRuleSettingsForm" property="pesto.totalMaximumMarks" styleId="totalMaximumMarks" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/></td>
								<td height="25"> <nested:text name="subjectRuleSettingsForm" property="pesto.totalentryMaximumMarks" styleId="totalentryMaximumMarks" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/></td>
							</tr>
							<tr>
							<td>Select The Best of</td>
							<td> <nested:text name="subjectRuleSettingsForm" property="pesto.selectTheBest" styleId="selectTheBest" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/></td>
							<td></td>
							<td></td>
							</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="heading">Attendance</td>
						</tr>
					</table>
					
					
					
					<table width="100%" border="0"  cellspacing="0" cellpadding="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr>
							<td align="right" class="row-odd">Attendance Type :</td>
							<td  class="row-odd"><nested:select name="subjectRuleSettingsForm" property="pesto.attendanceTypeId">
							<html:option value="">Select </html:option>
							<logic:notEmpty name="subjectRuleSettingsForm" property="pesto.attendanceTypeList">
								<html:optionsCollection name="subjectRuleSettingsForm" property="pesto.attendanceTypeList" label="attendanceTypeName" value="id"/>
							</logic:notEmpty>
							</nested:select> </td>
							<td  class="row-odd"></td>
							<td  class="row-odd"></td>
						</tr>
						<tr>
						<td colspan="4" class="heading">
							<font color="black"> 
							Consider the following for Attendance Marks
							</font>
						</td>
						</tr>
						<tr>
							<td class="row-odd" align="right">
								Leave:
							</td>
							<td class="row-even">
								<input type="hidden" name="pesto.tempLeave"
										id="tempLeave"
										value="<nested:write name='subjectRuleSettingsForm' property='pesto.dupLeave'/>" />
								<input type="checkbox" name="pesto.leaveAttendance"
										id="leaveAttendance" />
										<script type="text/javascript">
											var leave = document.getElementById("tempLeave").value;
											if(leave == "on") {
												document.getElementById("leaveAttendance").checked = true;
											}else{
												document.getElementById("leaveAttendance").checked = false;
												}		
								</script>
							</td>
							<td class="row-odd" align="right">
							Co-Curricular :
							</td>
							<td class="row-even">
								<input type="hidden" name="pesto.tempCoCurricular"
										id="tempCoCurricular"
										value="<nested:write name='subjectRuleSettingsForm' property='pesto.dupCoCurricular'/>" />
								<input type="checkbox" name="pesto.coCurricularAttendance"
										id="coCurricularAttendance" />
										<script type="text/javascript">
											var cocurr = document.getElementById("tempCoCurricular").value;
											if(cocurr == "on") {
												document.getElementById("coCurricularAttendance").checked = true;
											}else{
												document.getElementById("coCurricularAttendance").checked = false;
												}		
								</script>
							</td>
						</tr>
					</table>
					</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="heading">Assignment</td>
						</tr>
					</table>
					
					<table width="100%" border="0"  cellspacing="0" cellpadding="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td>
					<table width="100%" border="0"  cellspacing="1" cellpadding="1">
						<tr class="row-odd">
							<td>Assignment</td>
							<td>Minimum Marks</td>
							<td>Maximum Marks</td>
						</tr>
						<logic:notEmpty name="subjectRuleSettingsForm" property="pesto.assignmentList">
								<nested:iterate id="assList" name="subjectRuleSettingsForm" property="pesto.assignmentList" indexId="count1">
								<c:choose>
										<c:when test="${count1%2==0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
								<td height="25" ><bean:write name="assList" property="name"/> </td>
								<td height="25" ><nested:text name="assList" property="minimumAssignMarks" styleId="minimumAssignMarks" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/></td>
								<td height="25"><nested:text name="assList" property="maximumAssignMarks" styleId="maximumAssignMarks" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/></td>
								</tr>
								</nested:iterate>
						</logic:notEmpty>
					</table>
					</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="heading">Final Internal Marks</td>
						</tr>
					</table>
					
					<table width="100%" border="0"  cellspacing="0" cellpadding="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td>
					<table width="100%" border="0"  cellspacing="1" cellpadding="1">
						<tr height="25">
							<td class="row-odd" align="right">Sub Internal</td>
							<td class="row-even">
								<input type="hidden" name="pesto.dupsubInternalChecked"
										id="dupsubInternalChecked"
										value="<nested:write name='subjectRuleSettingsForm' property='pesto.dupsubInternalChecked'/>" />
								<input type="checkbox" name="pesto.subInternalChecked"
										id="subInternalChecked" />
										<script type="text/javascript">
											var sub = document.getElementById("dupsubInternalChecked").value;
											if(sub == "on") {
												document.getElementById("subInternalChecked").checked = true;
											}else{
												document.getElementById("subInternalChecked").checked = false;
												}		
								</script>
							
							</td>
							<td class="row-odd" align="right">Attendance :</td>
							<td class="row-even">
							<input type="hidden" name="pesto.dupattendanceChecked"
										id="dupattendanceChecked"
										value="<nested:write name='subjectRuleSettingsForm' property='pesto.dupattendanceChecked'/>" />
								<input type="checkbox" name="pesto.attendanceChecked"
										id="attendanceChecked" />
										<script type="text/javascript">
											var att = document.getElementById("dupattendanceChecked").value;
											if(att == "on") {
												document.getElementById("attendanceChecked").checked = true;
											}else{
												document.getElementById("attendanceChecked").checked = false;
												}		
								</script>
							</td>
							<td class="row-odd" align="right"> Assignment :</td>
							<td class="row-even">
								<input type="hidden" name="pesto.dupassignmentChecked"
										id="dupassignmentChecked"
										value="<nested:write name='subjectRuleSettingsForm' property='pesto.dupassignmentChecked'/>" />
								<input type="checkbox"  name="pesto.assignmentChecked"
										id="assignmentChecked" />
										<script type="text/javascript">
											var ass = document.getElementById("dupassignmentChecked").value;
											if(ass == "on") {
												document.getElementById("assignmentChecked").checked = true;
											}else{
												document.getElementById("assignmentChecked").checked = false;
												}		
								</script>
							</td>
						</tr>
						<tr height="25">
							<td class="row-odd" align="right">Minimum :</td>
							<td class="row-even">
							<nested:text name="subjectRuleSettingsForm" property="pesto.finalInternalMinimumMarks" styleId="finalInternalMinimumMarks" size="10" maxlength="6" onkeypress="return isDecimalNumberKey(this.value,evt)"/>
							</td>
							<td class="row-odd" align="right">(Marks entered in)Entry Maximum mark :</td>
							<td class="row-even" > 
							<nested:text name="subjectRuleSettingsForm" property="pesto.finalEntryMaximumMarks" styleId="finalEntryMaximumMarks" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/>
							</td>
							<td class="row-odd" align="right">Maximum Mark:</td>
							<td class="row-even">
							<nested:text name="subjectRuleSettingsForm" property="pesto.finalInternalMaximumMarks" styleId="finalInternalMaximumMarks" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/>
							</td>
						</tr>
					</table>
					</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="center">
								<html:submit value="Continue" styleClass="formbutton" styleId="submit"></html:submit>
								&nbsp;
								<html:button property="" value="Back" styleClass="formbutton" onclick="back()"></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
