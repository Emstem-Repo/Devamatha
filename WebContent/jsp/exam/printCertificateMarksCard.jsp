<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<script language="JavaScript" >
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}
function printMarksCard(){
	var url = "StudentLoginAction.do?method=printMarksCard";
	var browserName=navigator.appName; 
		 myRef = window.open(url,"MarksCard","left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</script>
<html:form action="/StudentLoginAction" >
	<html:hidden property="method" styleId="method" value="printMarksCard" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="loginform" />

	
	<table width="700" cellspacing="1" cellpadding="2" class="row-white">
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
										<tr class="row-white">
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
</html:form>
