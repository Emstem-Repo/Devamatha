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
function editSubjectRule(semNo,cid,sid,year){
	document.location.href = "subjectRuleSettings.do?method=editSubjectRuleSettingsForSubject&academicYear="+year+"&courseId="+cid+"&schemeNo="+semNo+"&subjectId="+sid;
}
function deletSubjectRule(semNo,cid,sid,year){
	document.location.href = "subjectRuleSettings.do?method=deleteSubjectRuleSettingsForSubject&academicYear="+year+"&courseId="+cid+"&schemeNo="+semNo+"&subjectId="+sid;
}
function reActivate(id){
	document.location.href = "subjectRuleSettings.do?method=reActivateSubjectRuleSettingsForSubject";
}
function cancelAction(){
	document.location.href ="subjectRuleSettings.do?method=initSubjectRuleSet";
}
</script>
<html:form action="/subjectRuleSettings" >
	<html:hidden property="method" styleId="method" value="editSubjectRuleSettingsForSubject" />	
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
										<bean:write name="subjectRuleSettingsForm" property="courseName"/>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>&nbsp;
									<logic:notEmpty name="subjectRuleSettingsForm" property="subjectName">
										<bean:message key="knowledgepro.admin.detailsubject.subjectname" />
									</logic:notEmpty>
									</div>
									</td>
									<td class="row-even">
										<logic:notEmpty name="subjectRuleSettingsForm" property="subjectName">
											<bean:write name="subjectRuleSettingsForm" property="subjectName"/>
										</logic:notEmpty>
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
					<br><br>
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
									<div align="center">Sl.No</div>
									</td>
									<td height="25" class="row-odd">Course</td>
									<td  class="row-odd">Scheme</td>
									<td class="row-odd">Subject Name</td>
									<td class="row-odd">
									<div align="center">Edit</div>
									</td>
									<td class="row-odd">
									<div align="center">Delete</div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<nested:notEmpty property="subList">
								<nested:iterate  property="subList" indexId="count">
								<c:choose>
							   <c:when test="${temp == 0}">
									<tr>
										<td width="6%" height="25" class="row-even"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td height="25" class="row-even" align="left"><nested:write  property="courseName" /></td>
										<td height="25" class="row-even" align="left"><nested:write  property="schemeNo" /></td>
										<td height="25" class="row-even" align="left"><nested:write  property="subjectName" /></td>
										<td height="25" class="row-even"><div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editSubjectRule('<nested:write  property="semister" />','<nested:write  property="courseId" />','<nested:write  property="id" />','<nested:write  property="academicYear" />')"></div></td>
										<td height="25" class="row-even"><div align="center"><img src="images/delete_icon.gif" width="16" height="18" style="cursor: pointer" onclick="deletSubjectRule('<nested:write  property="semister" />','<nested:write  property="courseId" />','<nested:write  property="id" />','<nested:write  property="academicYear" />')"></div></td>
									</tr>
								<c:set var="temp" value="1" />
									</c:when>
									<c:otherwise>
									<tr>
                                        <td width="6%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td height="25" class="row-white" align="left"><nested:write  property="courseName" /></td>
										<td height="25" class="row-white" align="left"><nested:write  property="schemeNo" /></td>
										<td height="25" class="row-white" align="left"><nested:write  property="subjectName" /></td>
										<td height="25" class="row-white"><div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editSubjectRule('<nested:write  property="semister" />','<nested:write  property="courseId" />','<nested:write  property="id" />','<nested:write  property="academicYear" />')"></div></td>
										<td height="25" class="row-white"><div align="center"><img src="images/delete_icon.gif" width="16" height="18" style="cursor: pointer" onclick="deletSubjectRule('<nested:write  property="semister" />','<nested:write  property="courseId" />','<nested:write  property="id" />','<nested:write  property="academicYear" />')"></div></td>
									</tr>
								  <c:set var="temp" value="0" />
							      </c:otherwise>
					      </c:choose>
				       </nested:iterate>
				       </nested:notEmpty>
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
					<table width="100%">
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						<tr>
						<td align="center">
						<html:button property="" value="Cancel" onclick="cancelAction()" styleClass="formbutton"></html:button>
						</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
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