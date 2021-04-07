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
		document.location.href = "subjectRuleSettings.do?method=backPracticalEse";
		}
</script>
<html:form action="/subjectRuleSettings">
	<html:hidden property="method" styleId="method" value="saveSubjectRules" />	
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
						<td class="heading"><img src="images/subject_final.gif"
						alt="CMS" width="790" height="33" border="0" usemap="#Map">
					<map name="Map">
						<area shape="rect" coords="243,6,361,26"
							onclick="practicalInternal()">
						<area shape="rect" coords="491,6,591,29" onclick="subjectFinal()">
						<area shape="rect" coords="374,6,474,29" onclick="practicalESE()">
						<area shape="rect" coords="283,31,383,54" href="#">
						<area shape="rect" coords="128,5,228,28" onclick="theoryESE()">
						<area shape="rect" coords="10,5,115,28"
							onclick="theoryPracticalInternal()">
						<area shape="rect" coords="491,6,591,29" href="#">

						<area shape="rect" coords="128,5,228,28" href="#">
						<area shape="rect" coords="11,5,111,28" href="#">
					</map> </td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="heading">Subject : Final</td>
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
									<td class="row-odd">Theory Exam </td>
									<td class="row-even">
									<input type="hidden" name="temptheoryExam"
										id="temptheoryExam"
										value="<nested:write name='subjectRuleSettingsForm' property='duptheoryExam'/>" />
									<input type="checkbox" name="theoryExam"
											id="theoryExam" />
											<script type="text/javascript">
												var tt = document.getElementById("temptheoryExam").value;
												if(tt == "on") {
													document.getElementById("theoryExam").checked = true;
												}else{
													document.getElementById("theoryExam").checked = false;
													}		
									</script>
									
									</td>
									<td class="row-odd">Minimum: </td>
									<td class="row-even">
									<nested:text name="subjectRuleSettingsForm" property="minimum" styleId="minimum" size="10" maxlength="6" onkeypress="return isDecimalNumberKey(this.value,event)"/>
									</td>
								</tr>
					
								<tr>
									<td class="row-odd">Practical Exam :</td>
									<td class="row-even">
									<input type="hidden" name="temppracticalExam"
										id="temppracticalExam"
										value="<nested:write name='subjectRuleSettingsForm' property='duppracticalExam'/>" />
									<input type="checkbox" name="practicalExam"
											id="practicalExam" />
											<script type="text/javascript">
												var tp = document.getElementById("temppracticalExam").value;
												if(tp == "on") {
													document.getElementById("practicalExam").checked = true;
												}else{
													document.getElementById("practicalExam").checked = false;
													}		
									</script>
									
									</td>
									<td class="row-odd">Maximum: </td>
									<td class="row-even">
									<nested:text name="subjectRuleSettingsForm" property="maximum" styleId="maximum" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/>
									</td>
								</tr>
								
								<tr>
									<td class="row-odd">Internal Exam :</td>
									<td class="row-even">
									<input type="hidden" name="tempinternalExam"
										id="tempinternalExam"
										value="<nested:write name='subjectRuleSettingsForm' property='dupinternalExam'/>" />
									<input type="checkbox" name="internalExam"
											id="internalExam" />
											<script type="text/javascript">
												var tp = document.getElementById("tempinternalExam").value;
												if(tp == "on") {
													document.getElementById("internalExam").checked = true;
												}else{
													document.getElementById("internalExam").checked = false;
													}		
									</script>
									
									</td>
									<td class="row-odd">Valuated : </td>
									<td class="row-odd">
									<nested:text name="subjectRuleSettingsForm" property="valuated" styleId="valuated" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/>
									</td>
								</tr>
								
								<tr>
									<td class="row-odd">Attendance :</td>
									<td class="row-even">
									<input type="hidden" name="tempattendance"
										id="tempattendance"
										value="<nested:write name='subjectRuleSettingsForm' property='dupattendance'/>" />
									<input type="checkbox" name="attendance"
											id="attendance" />
											<script type="text/javascript">
												var tp = document.getElementById("tempattendance").value;
												if(tp == "on") {
													document.getElementById("attendance").checked = true;
												}else{
													document.getElementById("attendance").checked = false;
													}		
									</script>
									
									</td>
									<td class="row-odd"> </td>
									<td class="row-even">
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