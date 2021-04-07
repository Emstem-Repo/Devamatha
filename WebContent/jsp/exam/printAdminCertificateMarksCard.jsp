<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" type="text/css" href="css/consolidateStyle.css"/>

<html:form action="/adminCertificateMarksCard" >
	<html:hidden property="method" styleId="method" value="printMarksCard" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="certificateMarksCardForm" />

	<logic:iterate id="mainTo" name="certificateMarksCardForm" property="studentList">
	
	
		<table cellspacing="1" cellpadding="2" >
								<tr>
									<td width="100%">
										<table width="100%" cellspacing="1" cellpadding="2" >
											<tr><td width="10%"></td><td colspan="2"><bean:define id="reg" name="mainTo" property="registerNo"></bean:define>
																<img src='<%=CMSConstants.barbecueLink %><%=reg %>' width="118" height="29px" alt="Image Not Found"/></td></tr>
											<tr><td width="10%" class="top-name-heading">Name:</td><td colspan="2" class="top-heading"><bean:write name="mainTo" property="name"/> </td></tr>
											<tr><td width="10%" class="top-name-heading">Course:</td><td colspan="2" class="top-heading"><bean:write name="mainTo" property="courseName"/></td></tr>
											<tr><td width="10%" valign="top" class="top-name-heading">Reg No:</td><td class="top-heading">
											<table>
											<logic:notEmpty name="mainTo" property="regNos">
											<nested:iterate id="str" name="mainTo" property="regNos" indexId="count">
												<tr><td>
												<c:out value="${str}"></c:out>
												</td>
												</tr>
											</nested:iterate>
											</logic:notEmpty>
											</table>
											</td>
											<td class="top-heading" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Years of Study : <bean:write name="mainTo" property="yearOfStudy"/></td></tr>
										</table>
									</td>
								</tr>
								<tr>
									<td width="100%">
										<table width="100%" cellspacing="1" cellpadding="2" >
										<tr  >
											<logic:iterate id="mto" name="mainTo" property="toList" indexId="count">
											<td width="50%">
											<table cellspacing="1" cellpadding="2" >
												<tr>
													<td colspan="7" align="center" class="sem-section-bold"><bean:write name="mto" property="semNo"/></td>
												</tr>
												<c:set var="slnocount" value="0" />
												<logic:iterate id="map" name="mto" property="subMap" indexId="count1">
												<tr>
													<td colspan="7" class="sem-section-bold"> 
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <bean:write name="map" property="key"/>
													</td>
												</tr>
												
												<logic:iterate id="to" name="map" property="value" indexId="count2">
												
												<c:set var="slnocount" value="${slnocount + 1 }" />
													<c:if test="${to.theory!=null && to.theory==true}">
													
													<tr class="term-no">
														<td width="18px">
															<c:choose>
														<c:when test="${slnocount<10}">
														0<c:out value="${slnocount}"></c:out>
														</c:when>
														<c:otherwise>
														<c:out value="${slnocount}"></c:out>
														</c:otherwise>
														</c:choose>
														</td>
														<td width="8px">
														<c:if test="${to.appearedTheory==true}">
																*
															</c:if>
														</td>
														<td width="215px">
															<bean:write name="to" property="name"/>
														</td>
														<td width="37px">
															<bean:write name="to" property="totalMaxMarks"/>
														</td>
														<td width="38px">
															<bean:write name="to" property="totalMarksAwarded"/>
														</td>
														<td width="20px">
															<bean:write name="to" property="grade"/>
														</td>
														<td width="12px">
															<bean:write name="to" property="credits"/>
														</td>
													</tr>
													</c:if>
													<c:if test="${to.practical!=null && to.practical==true}">
													<tr class="term-no">
														<td width="18px">
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
														<td width="8px">
														<c:if test="${to.appearedPractical==true}">
																*
															</c:if>
														</td>
														<td width="215px">
															<c:if test="${to.theory!=null && to.theory==false}">
																<i><bean:write name="to" property="name"/></i>
															</c:if>
															<c:if test="${to.theory!=null && to.theory==true}">
																<i>PRACTICAL</i>
															</c:if>
														</td>
														<td width="37px">
															<bean:write name="to" property="practicalTotalMaxMarks"/>
														</td>
														<td width="38px">
															<bean:write name="to" property="practicalTotalMarksAwarded"/>
														</td>
														<td width="20px">
															<bean:write name="to" property="practicalGrade"/>
														</td>
														<td width="12px">
															<bean:write name="to" property="practicalCredits"/>
														</td>
													</tr>
													</c:if>
													</logic:iterate>
												</logic:iterate>
												<logic:iterate id="map1" name="mto" property="addOnCoursesubMap" indexId="count1">
													<tr>
													<td colspan="7" class="sem-section-bold"> 
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="map1" property="key"/>
													</td>
												</tr>
												<logic:iterate id="to" name="map1" property="value" indexId="count2">
												<c:if test="${to.theory!=null && to.theory==true}">
													<tr class="term-no">
														<td width="18px">
															
															<c:choose>
														<c:when test="${count2+1<10}">
														0<c:out value="${count2+1}"></c:out>
														</c:when>
														<c:otherwise>
														<c:out value="${count2+1}"></c:out>
														</c:otherwise>
														</c:choose>
														</td>
														<td width="8px">
														<c:if test="${to.appearedTheory==true}">
																*
															</c:if>
														</td>
														<td width="215px">
															<bean:write name="to" property="name"/>
														</td>
														<td width="37px">
															<bean:write name="to" property="totalMaxMarks"/>
														</td>
														<td width="38px">
															<bean:write name="to" property="totalMarksAwarded"/>
														</td>
														<td width="20px">
															<bean:write name="to" property="grade"/>
														</td>
														<td width="12px">
															<bean:write name="to" property="credits"/>
														</td>
													</tr>
													</c:if>
													<c:if test="${to.practical!=null && to.practical==true}">
													<tr class="term-no">
														<td width="18px">
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
														<td width="8px"><c:if test="${to.appearedPractical==true}">
																*
															</c:if></td>
														<td width="215px">
															<c:if test="${to.theory!=null && to.theory==false}">
																<i><bean:write name="to" property="name"/></i>
															</c:if>
															<c:if test="${to.theory!=null && to.theory==true}">
																<i>PRACTICAL</i>
															</c:if>
														</td>
														<td width="37px">
															<bean:write name="to" property="practicalTotalMaxMarks"/>
														</td>
														<td width="38px">
															<bean:write name="to" property="practicalTotalMarksAwarded"/>
														</td>
														<td width="20px">
															<bean:write name="to" property="practicalGrade"/>
														</td>
														<td width="12px">
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
											<tr >
											</c:if>												
											</logic:iterate>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table width="100%" cellspacing="1" cellpadding="2" >
											<tr class="heading">
												<td align="center"><bean:write name="mainTo" property="result"/> </td>
												<td align="center"><bean:write name="mainTo" property="totalMaxMarks"/> </td>
												<td align="center"><bean:write name="mainTo" property="totalMarksAwarded"/> </td>
												<td align="center"><bean:write name="mainTo" property="totalCredits"/> </td>
												<td align="center"><bean:write name="mainTo" property="gradePointAvg"/> </td>
											</tr>											
										</table>
									</td>
								</tr>
								</table>
	</logic:iterate>
</html:form>
