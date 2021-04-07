<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script language="JavaScript" >
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}
function printMarksCard(){
	var url = "StudentLoginAction.do?method=printCertificateMarksCard";
	var browserName=navigator.appName; 
		 myRef = window.open(url,"MarksCard","left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</script>
<html:form action="/StudentLoginAction" >
	<html:hidden property="method" styleId="method" value="printMarksCard" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="loginform" />

	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.studentlogin" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.publishHM.MarksCard" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.exam.publishHM.MarksCard" /></strong></div>
					</td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>
							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
								<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
								<tr>
									<td width="100%">
										<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
											<tr><td width="10%"></td><td colspan="2"><bean:define id="reg" name="loginform" property="consolidateMarksCardTO.registerNo"></bean:define>
																<img src='<%=CMSConstants.barbecueLink %><%=reg %>' height="21" width="118"   /></td></tr>
											<tr class="heading"><td width="10%">Name:</td><td colspan="2"><bean:write name="loginform" property="consolidateMarksCardTO.name"/> </td></tr>
											<tr class="heading"><td width="10%">Course:</td><td colspan="2"><bean:write name="loginform" property="consolidateMarksCardTO.courseName"/></td></tr>
											<tr><td width="10%" class="top-name-heading" valign="top">Reg No:</td><td class="top-heading" valign="top">
											<table>
											<nested:iterate id="str" name="loginform" property="consolidateMarksCardTO.regNos" indexId="count">
												<tr><td>
												<c:out value="${str}"></c:out>
												</td>
												</tr>
											</nested:iterate>
											</table>
											</td>
											<td class="top-heading" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Years of Study : <bean:write name="loginform" property="consolidateMarksCardTO.yearOfStudy"/></td></tr>
										</table>
									</td>
								</tr>
								<tr>
									<td width="100%">
										<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
										<tr class="row-white" height="30">
											<logic:iterate id="mto" name="loginform" property="consolidateMarksCardTO.toList" indexId="count">
											<td>
											<table width="100%" cellspacing="1" cellpadding="2" class="row-white" height="100%">
												<tr>
													<td colspan="7" align="center" class="heading"><bean:write name="mto" property="semNo"/></td>
												</tr>
												<c:set var="slnocount" value="0" />
												<logic:iterate id="map" name="mto" property="subMap" indexId="count1">
												<tr>
													<td colspan="7" class="heading"> 
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <bean:write name="map" property="key"/>
													</td>
												</tr>
												
												<logic:iterate id="to" name="map" property="value" indexId="count2">
												
												<c:set var="slnocount" value="${slnocount + 1 }" />
													<c:if test="${to.theory!=null && to.theory==true}">
													
													<tr height="15">
														<td width="10%">
														<c:choose>
														<c:when test="${slnocount<10}">
														0<c:out value="${slnocount}"></c:out>
														</c:when>
														<c:otherwise>
														<c:out value="${slnocount}"></c:out>
														</c:otherwise>
														</c:choose>
															
														</td>
														<td width="2%">
															<c:if test="${to.appearedTheory==true}">
																*
															</c:if>
														</td>
														<td width="58%">
															<bean:write name="to" property="name"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="totalMaxMarks"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="totalMarksAwarded"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="grade"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="credits"/>
														</td>
													</tr>
													</c:if>
													<c:if test="${to.practical!=null && to.practical==true}">
													<tr height="15">
														<td width="10%">
														<c:if test="${to.theory!=null && to.theory==false}">
															<c:choose>
														<c:when test="${slnocount<10}">
														0<c:out value="${slnocount}"></c:out>
														</c:when>
														<c:otherwise>
														<c:out value="${slnocount}"></c:out>
														</c:otherwise>
														</c:choose>
															</c:if>
														</td>
														<td width="2%">
															<c:if test="${to.appearedPractical==true}">
																*
															</c:if>
														</td>
														<td width="58%">
															<c:if test="${to.theory!=null && to.theory==false}">
																<i><bean:write name="to" property="name"/></i>
															</c:if>
															<c:if test="${to.theory!=null && to.theory==true}">
																<i>PRACTICAL</i>
															</c:if>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="practicalTotalMaxMarks"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="practicalTotalMarksAwarded"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="practicalGrade"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="practicalCredits"/>
														</td>
													</tr>
													</c:if>
													</logic:iterate>
												</logic:iterate>
												<logic:iterate id="map1" name="mto" property="addOnCoursesubMap" indexId="count1">
													<tr>
													<td colspan="7" class="heading"> 
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="map1" property="key"/>
													</td>
												</tr>
												<logic:iterate id="to" name="map1" property="value" indexId="count2">
												<c:if test="${to.theory!=null && to.theory==true}">
													<tr height="15">
														<td width="10%">
															<c:choose>
														<c:when test="${count2+1<10}">
														0<c:out value="${count2+1}"></c:out>
														</c:when>
														<c:otherwise>
														<c:out value="${count2+1}"></c:out>
														</c:otherwise>
														</c:choose>
														</td>
														<td width="2%">
															<c:if test="${to.appearedTheory==true}">
																*
															</c:if>
														</td>
														<td width="58%">
															<bean:write name="to" property="name"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="totalMaxMarks"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="totalMarksAwarded"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="grade"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="credits"/>
														</td>
													</tr>
													</c:if>
													<c:if test="${to.practical!=null && to.practical==true}">
													<tr height="15">
														<td width="10%">
														<c:if test="${to.theory!=null && to.theory==false}">
															<c:choose>
														<c:when test="${slnocount<10}">
														0<c:out value="${slnocount}"></c:out>
														</c:when>
														<c:otherwise>
														<c:out value="${slnocount}"></c:out>
														</c:otherwise>
														</c:choose>
															</c:if>
														</td>
														<td width="2%">
															<c:if test="${to.appearedPractical==true}">
																*
															</c:if>
														</td>
														<td width="58%">
															<c:if test="${to.theory!=null && to.theory==false}">
																<i><bean:write name="to" property="name"/></i>
															</c:if>
															<c:if test="${to.theory!=null && to.theory==true}">
																<i>PRACTICAL</i>
															</c:if>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="practicalTotalMaxMarks"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="practicalTotalMarksAwarded"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="practicalGrade"/>
														</td>
														<td width="7.5%">
															<bean:write name="to" property="practicalCredits"/>
														</td>
													</tr>
													</c:if>
													</logic:iterate>
												</logic:iterate>
												</table>
											</td>
											<c:if test="${(count + 1) % 2 == 0}">
											</tr>
											<tr class="row-white" height="30">
											</c:if>												
											</logic:iterate>
										</table>
									</td>
								</tr>
								<!--<tr>
									<td>
										<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
											<tr class="heading">
												<td><bean:write name="loginform" property="consolidateMarksCardTO.result"/> </td>
												<td><bean:write name="loginform" property="consolidateMarksCardTO.totalMaxMarks"/> </td>
												<td><bean:write name="loginform" property="consolidateMarksCardTO.totalMarksAwarded"/> </td>
												<td><bean:write name="loginform" property="consolidateMarksCardTO.totalCredits"/> </td>
												<td><bean:write name="loginform" property="consolidateMarksCardTO.gradePointAvg"/> </td>
											</tr>											
										</table>
									</td>
								</tr>
								--></table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:button property="" styleClass="formbutton" onclick="printMarksCard()">
								Print
							</html:button></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""  styleClass="formbutton" value="Close" onclick="goToHomePage()"></html:button> 
							</td>
						</tr>
					</table>
					</div>
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
