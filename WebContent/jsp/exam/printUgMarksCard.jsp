<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link rel="stylesheet" href="css/styles.css">
<script language="JavaScript" >
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}
function printPass(){
	var url = "StudentLoginAction.do?method=printHallTicket";
	myRef = window
			.open(url, "Hall Ticket",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");

}
</script>
<html:form action="/StudentLoginAction" >
	<html:hidden property="method" styleId="method" value="printHallTicket" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="loginform" />

	
	<table width="100%" height="100%" cellspacing="1" cellpadding="2"  class="row-white1" >
								<bean:define id="reg" name="loginform" property="marksCardTo.regNo"></bean:define>
							
							<tr><td><table width="100%">
							
							<tr>
								<td width="100%" align="center">
								<img src='<%=request.getContextPath()+"/LogoServlet?count=1"%>'  height="150" width="500" />
								</td>
								
							</tr>
							</table></td></tr>
							
							
<tr height="30px"></tr>
							
							<tr height="25px">
								<td  align="center" width="80%" class="mrk-head" style="padding-left: 270px;"><b>STATEMENT OF MARKS</b></td>
								
							</tr>
<tr height="30px"></tr>
							<tr>
								<td>
									<table width="100%">
									
									<tr>
									<td class="mrk-head"> Name Of Candidate</td>
										<td class="row-print"> :</td>
										<td class="mrk-head-con"><bean:write name="loginform" property="marksCardTo.studentName"/></td>
										
									</tr>
<br/>
									<tr>
										<td class="mrk-head">Permanent Register Number</td>
										<td class="row-print">:</td>
										<td class="mrk-head-con"><bean:write name="loginform" property="marksCardTo.regNo"/></td>
									</tr>
<br/>
                                 	<tr>
                                        <td class="mrk-head">Degree</td>
										<td class="row-print">:</td>
										<td class="mrk-head-con"><bean:write name="loginform" property="marksCardTo.courseName"/></td>
									</tr>
									<tr>
										<td class="mrk-head">Name Of Examination</td>
										<td class="row-print">:</td>
										<td class="mrk-head-con" style="font-size:12px "><bean:write name="loginform" property="marksCardTo.semString"/> SEMESTER <bean:write name="loginform" property="marksCardTo.ugorpg"/> EXAMINATION <bean:write name="loginform" property="marksCardTo.monthYear"/> </td>
									</tr>
									</table>
								</td>
								<td style="padding-bottom: 10px;">
								<img src='<%=request.getContextPath()+"/PhotoServlet?"%>'  height="120" width="128" style="float: right;"/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table width="100%" style="border: 1px solid black; " rules="all">
										<tr height="21px">

						                        <td rowspan="2" class="row-print" align="center" width="3%">
						                                   Course Code
						                        </td>
						                        <td rowspan="2" class="row-print" align="center" width="25%">
						                                    Course
						                        </td>
						                        <!--<td rowspan="2" class="row-print" align="center" width="5%">
						                                    TYPE
						                        </td>
						                         -->
						                          <td rowspan="2" align="center" class="row-print" width="6%">
						                                    Credit
						                        </td>
						                        <td colspan="2" align="center" class="row-print" width="15%">
						                                   ESE
						                        </td><!--
						                        <td colspan="2" align="center" class="row-print" width="15%">
						                                    ATT
						                        </td>
						                        --><td colspan="2" align="center" class="row-print" width="17%">
						                                    CIA
						                        </td>
						                        <td rowspan="2"  align="center" class="row-print" width="10%">
						                                     TOTAL
						                        </td>
						                        <td rowspan="2"align="center" class="row-print" width="6%">
						                                   MAX MARKS
						                        </td>
						                        <td rowspan="2" align="center" class="row-print" width="6%">
						                                    GRADE
						                        </td>
						                         <td rowspan="2" align="center" class="row-print" width="6%">
						                                    GP
						                        </td>
						                        <td rowspan="2" align="center" class="row-print" width="6%">
						                                    CP
						                        </td>
						                        <td rowspan="2" align="center" class="row-print" width="6%">
						                                   Result
						                        </td>
						            </tr>
						            <tr height="21px">
						                        <td align="center" class="row-print">
						                                     Awarded
						                        </td>
						                        <td align="center" class="row-print">
						                                   MAX
						                        </td><!--
						                         <td align="center" class="row-print">
						                                    MAX 
						                        </td>
						                        <td align="center" class="row-print">
						                                    MARKS AWARDED
						                        </td>
						                        --><td align="center" class="row-print">
						                                   Awarded
						                        </td>
						                        <!--<td align="center" class="row-print">
						                                    MIN MARKS
						                        </td>
						                        --><td align="center" class="row-print">
						                                   MAX
						                        </td><!--
						                       
						                        <td align="center" class="row-print">
						                                    MARKS AWARDED
						                        </td>               
						            --></tr>
						            <c:set var="slnocount" value="0" />
									<logic:notEmpty name="loginform" property="marksCardTo.subMap">
										<logic:iterate id="map" name="loginform" property="marksCardTo.subMap" indexId="count1">
											<!--<tr height="21px">
												<td></td>
												<td colspan="13" class="row-print"> <bean:write name="map" property="key"/> </td>
											</tr>-->
											<logic:notEmpty name="map" property="value">
											<logic:iterate id="to" name="map" property="value">
											<c:set var="slnocount" value="${slnocount + 1 }" />
											<c:if test="${to.theory!=null && to.theory==true && to.displaySubject==true}">
											<tr height="21px">
												<!--<td align="center" class="row-print"> <c:out value="${slnocount}" /> </td>
												--><td   class="row-print"> <bean:write name="to" property="code"/> </td>
												<td   class="row-print"> <bean:write name="to" property="name"/> </td><td align="center" class="row-print"><logic:equal value="0" name="to" property="credits">-</logic:equal> <logic:notEqual value="00" name="to" property="credits"><bean:write name="to" property="credits"/></logic:notEqual> <logic:empty name="to" property="credits">- </logic:empty></td>
												<!--<td align="center" class="row-print"> <bean:write name="to" property="type"/> </td>-->
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="eseMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="eseMaxMarks"><bean:write name="to" property="eseMarksAwarded"/></logic:notEqual>  <logic:empty name="to" property="eseMarksAwarded">- </logic:empty></td>
												
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="eseMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="eseMaxMarks"><bean:write name="to" property="eseMaxMarks"/></logic:notEqual>  <logic:empty name="to" property="eseMaxMarks">- </logic:empty></td>
												
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="ciaMaxMarks">-</logic:equal> <logic:empty name="to" property="ciaMaxMarks">- </logic:empty> <logic:notEqual value="-" name="to" property="ciaMaxMarks"><logic:notEmpty name="to" property="ciaMaxMarks"> <bean:write name="to" property="ciaMarksAwarded"/></logic:notEmpty> </logic:notEqual><logic:empty name="to" property="ciaMarksAwarded">- </logic:empty> </td>
												
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="ciaMaxMarks">-</logic:equal> <logic:notEqual value="00" name="to" property="ciaMaxMarks"> <bean:write name="to" property="ciaMaxMarks"/></logic:notEqual> 
												<logic:empty name="to" property="ciaMaxMarks">- </logic:empty>
												 </td>
												<!--<td align="center" class="row-print"><logic:equal value="00" name="to" property="attMaxMarks">-</logic:equal> <logic:notEqual value="00" name="to" property="attMaxMarks"><bean:write name="to" property="attMaxMarks"/></logic:notEqual> <logic:empty name="to" property="attMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="attMaxMarks">-</logic:equal> <logic:notEqual value="-" name="to" property="attMaxMarks"><bean:write name="to" property="attMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="attMarksAwarded">- </logic:empty></td>
												-->
												<!--<td align="center" class="row-print"> <bean:write name="to" property="eseMinMarks"/> <logic:empty name="to" property="eseMinMarks">- </logic:empty></td>
												--><td align="center" class="row-print"><logic:equal value="-" name="to" property="totalMaxMarks">-</logic:equal> <logic:notEqual value="-" name="to" property="totalMaxMarks"><bean:write name="to" property="totalMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="totalMarksAwarded">- </logic:empty></td>
												
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="totalMaxMarks">-</logic:equal> <logic:notEqual value="00" name="to" property="totalMaxMarks"><bean:write name="to" property="totalMaxMarks"/></logic:notEqual> <logic:empty name="to" property="totalMaxMarks">- </logic:empty></td>
												
												<td  class="row-print" align="center"> <bean:write name="to" property="grade"/> <logic:empty name="to" property="grade">- </logic:empty></td>
												<td  class="row-print" align="center"> <bean:write name="to" property="theoryGradePnt"/> <logic:empty name="to" property="theoryGradePnt">- </logic:empty></td>
												<td  class="row-print" align="center"> <bean:write name="to" property="creditPoint"/> <logic:empty name="to" property="creditPoint">- </logic:empty></td>
											<td  class="row-print" align="center"> <bean:write name="to" property="resultClass"/> <logic:empty name="to" property="resultClass">- </logic:empty></td>
											
											</tr>
											</c:if>
											<c:if test="${to.practical!=null && to.practical==true && to.displaySubject==true}">
											<tr height="21px">
												<td align="center" class="row-print">
												<c:if test="${to.theory!=null && to.theory==false}">
												<!--<c:out value="${slnocount}" />--><bean:write name="to" property="code"/> 
												</c:if>
												</td>
												<td class="row-print">
												<c:if test="${to.theory!=null && to.theory==false}">
												<bean:write name="to" property="name"/>
												</c:if>
												</td> 
												<td align="center" class="row-print"><logic:equal value="0" name="to" property="practicalCredits">-</logic:equal> <logic:notEqual value="00" name="to" property="practicalCredits"><bean:write name="to" property="practicalCredits"/></logic:notEqual> <logic:empty name="to" property="practicalCredits">- </logic:empty></td> 
												<!--<td   class="row-print"> <bean:write name="to" property="subjectType"/> </td>
												--><!--<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalAttMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalAttMaxMarks"> <bean:write name="to" property="practicalAttMaxMarks"/></logic:notEqual> <logic:empty name="to" property="practicalAttMaxMarks">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalAttMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalAttMaxMarks"> <bean:write name="to" property="practicalAttMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalAttMarksAwarded">- </logic:empty> </td>
												-->
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalEseMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalEseMaxMarks"> <bean:write name="to" property="practicalEseMarksAwarded"/></logic:notEqual>  <logic:empty name="to" property="practicalEseMarksAwarded">- </logic:empty></td>
												
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalEseMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalEseMaxMarks"> <bean:write name="to" property="practicalEseMaxMarks"/></logic:notEqual> <logic:empty name="to" property="practicalEseMaxMarks">- </logic:empty> </td>
												<!--<td align="center" class="row-print"><bean:write name="to" property="practicalEseMinMarks"/> <logic:empty name="to" property="practicalEseMinMarks">- </logic:empty> </td>-->
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalCiaMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalCiaMaxMarks"> <bean:write name="to" property="practicalCiaMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalCiaMarksAwarded">- </logic:empty> </td>
												
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalCiaMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalCiaMaxMarks"> <bean:write name="to" property="practicalCiaMaxMarks"/></logic:notEqual>  <logic:empty name="to" property="practicalCiaMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalTotalMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalTotalMaxMarks"> <bean:write name="to" property="practicalTotalMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalTotalMarksAwarded">- </logic:empty> </td>
												
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalTotalMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalTotalMaxMarks"> <bean:write name="to" property="practicalTotalMaxMarks"/></logic:notEqual>  <logic:empty name="to" property="practicalTotalMaxMarks">- </logic:empty></td>
												
												<td  class="row-print" align="center"> <bean:write name="to" property="practicalGrade"/><logic:empty name="to" property="practicalGrade">- </logic:empty> </td>
												<td  class="row-print" align="center"> <bean:write name="to" property="practicalGradePnt"/> <logic:empty name="to" property="practicalGradePnt">- </logic:empty></td>
													<td  class="row-print" align="center"> <bean:write name="to" property="creditPoint"/> <logic:empty name="to" property="creditPoint">- </logic:empty></td>
													<td  class="row-print" align="center"> <bean:write name="to" property="resultClass"/> <logic:empty name="to" property="resultClass">- </logic:empty></td>
											
											</tr>
											</c:if>
											</logic:iterate>
											</logic:notEmpty>
										</logic:iterate>
										</logic:notEmpty>
											
										
										
										<logic:notEmpty name="loginform" property="marksCardTo.nonElectivesubMap">
										<c:set var="slnocount" value="${slnocount + 1 }" />
										<logic:iterate id="map" name="loginform" property="marksCardTo.nonElectivesubMap" indexId="count2">
										
											<tr height="21px">
												<td class="row-print" align="center"><c:out value="${slnocount}" /></td>
												<td colspan="13" class="row-print"><bean:write name="map" property="key"/>/s </td>
											</tr>
											<logic:notEmpty name="map" property="value">
											<logic:iterate id="to" name="map" property="value">
											
											<c:if test="${to.theory!=null && to.theory==true && to.displaySubject==true}" >
											<tr height="21px">
												<td align="center" class="row-print"> </td>
												<td   class="row-print"> <bean:write name="to" property="name"/> </td>
												<td align="center" class="row-print"> <bean:write name="to" property="type"/> </td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="ciaMaxMarks">-</logic:equal> <logic:notEqual value="00" name="to" property="ciaMaxMarks"> <bean:write name="to" property="ciaMaxMarks"/></logic:notEqual> 
												<logic:empty name="to" property="ciaMaxMarks">- </logic:empty>
												 </td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="ciaMaxMarks">-</logic:equal> <logic:empty name="to" property="ciaMaxMarks">- </logic:empty> <logic:notEqual value="-" name="to" property="ciaMaxMarks"><logic:notEmpty name="to" property="ciaMaxMarks"> <bean:write name="to" property="ciaMarksAwarded"/></logic:notEmpty> </logic:notEqual><logic:empty name="to" property="ciaMarksAwarded">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="attMaxMarks">-</logic:equal> <logic:notEqual value="00" name="to" property="attMaxMarks"><bean:write name="to" property="attMaxMarks"/></logic:notEqual> <logic:empty name="to" property="attMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="attMaxMarks">-</logic:equal> <logic:notEqual value="-" name="to" property="attMaxMarks"><bean:write name="to" property="attMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="attMarksAwarded">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="eseMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="eseMaxMarks"><bean:write name="to" property="eseMaxMarks"/></logic:notEqual>  <logic:empty name="to" property="eseMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"> <bean:write name="to" property="eseMinMarks"/> <logic:empty name="to" property="eseMinMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="eseMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="eseMaxMarks"><bean:write name="to" property="eseMarksAwarded"/></logic:notEqual>  <logic:empty name="to" property="eseMarksAwarded">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="totalMaxMarks">-</logic:equal> <logic:notEqual value="00" name="to" property="totalMaxMarks"><bean:write name="to" property="totalMaxMarks"/></logic:notEqual> <logic:empty name="to" property="totalMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="totalMaxMarks">-</logic:equal> <logic:notEqual value="-" name="to" property="totalMaxMarks"><bean:write name="to" property="totalMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="totalMarksAwarded">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="0" name="to" property="credits">-</logic:equal> <logic:notEqual value="00" name="to" property="credits"><bean:write name="to" property="credits"/></logic:notEqual> <logic:empty name="to" property="credits">- </logic:empty></td>
												<td  class="row-print"> <bean:write name="to" property="grade"/> <logic:empty name="to" property="grade">- </logic:empty></td>
											</tr>
											</c:if>
											<c:if test="${to.practical!=null && to.practical==true && to.displaySubject==true}" >
											<tr height="21px">
												<td align="center" class="row-print">
												<c:if test="${to.theory!=null && to.theory==false}">
												</c:if>
												</td>
												<td class="row-print">
												<c:if test="${to.theory!=null && to.theory==false}">
												<bean:write name="to" property="name"/>
												</c:if>
												</td>  
												<td align="center" class="row-print"> <bean:write name="to" property="subjectType"/> </td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalCiaMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalCiaMaxMarks"> <bean:write name="to" property="practicalCiaMaxMarks"/></logic:notEqual>  <logic:empty name="to" property="practicalCiaMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalCiaMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalCiaMaxMarks"> <bean:write name="to" property="practicalCiaMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalCiaMarksAwarded">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalAttMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalAttMaxMarks"> <bean:write name="to" property="practicalAttMaxMarks"/></logic:notEqual> <logic:empty name="to" property="practicalAttMaxMarks">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalAttMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalAttMaxMarks"> <bean:write name="to" property="practicalAttMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalAttMarksAwarded">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalEseMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalEseMaxMarks"> <bean:write name="to" property="practicalEseMaxMarks"/></logic:notEqual> <logic:empty name="to" property="practicalEseMaxMarks">- </logic:empty> </td>
												<td align="center" class="row-print"><bean:write name="to" property="practicalEseMinMarks"/> <logic:empty name="to" property="practicalEseMinMarks">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalEseMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalEseMaxMarks"> <bean:write name="to" property="practicalEseMarksAwarded"/></logic:notEqual>  <logic:empty name="to" property="practicalEseMarksAwarded">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalTotalMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalTotalMaxMarks"> <bean:write name="to" property="practicalTotalMaxMarks"/></logic:notEqual>  <logic:empty name="to" property="practicalTotalMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalTotalMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalTotalMaxMarks"> <bean:write name="to" property="practicalTotalMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalTotalMarksAwarded">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="0" name="to" property="practicalCredits">-</logic:equal> <logic:notEqual value="00" name="to" property="practicalCredits"><bean:write name="to" property="practicalCredits"/></logic:notEqual> <logic:empty name="to" property="practicalCredits">- </logic:empty></td>
												<td   class="row-print"> <bean:write name="to" property="practicalGrade"/> <logic:empty name="to" property="practicalGrade">- </logic:empty></td>
											</tr>
											</c:if>
											</logic:iterate>
											</logic:notEmpty>
										</logic:iterate>
										</logic:notEmpty>
										
										
										
										
										<logic:notEmpty name="loginform" property="marksCardTo.addOnCoursesubMap">
										<c:set var="slnocount" value="${slnocount + 1 }" />
										<logic:iterate id="map" name="loginform" property="marksCardTo.addOnCoursesubMap" indexId="count3">
										
											<tr height="21px">
												<td class="row-print" align="center"><c:out value="${slnocount}" /></td>
												<td colspan="13" class="row-print"> <bean:write name="map" property="key"/>/s </td>
											</tr>
											<logic:notEmpty name="map" property="value">
											<logic:iterate id="to" name="map" property="value">
											
											<c:if test="${to.theory!=null && to.theory==true && to.displaySubject==true}" >
											<tr height="21px">
												<td align="center" class="row-print"> </td>
												<td   class="row-print"> <bean:write name="to" property="name"/> </td>
												<td align="center" class="row-print"> <bean:write name="to" property="type"/> </td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="ciaMaxMarks">-</logic:equal> <logic:notEqual value="00" name="to" property="ciaMaxMarks"> <bean:write name="to" property="ciaMaxMarks"/></logic:notEqual> 
												<logic:empty name="to" property="ciaMaxMarks">- </logic:empty>
												 </td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="ciaMaxMarks">-</logic:equal> <logic:empty name="to" property="ciaMaxMarks">- </logic:empty> <logic:notEqual value="-" name="to" property="ciaMaxMarks"><logic:notEmpty name="to" property="ciaMaxMarks"> <bean:write name="to" property="ciaMarksAwarded"/></logic:notEmpty> </logic:notEqual><logic:empty name="to" property="ciaMarksAwarded">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="attMaxMarks">-</logic:equal> <logic:notEqual value="00" name="to" property="attMaxMarks"><bean:write name="to" property="attMaxMarks"/></logic:notEqual> <logic:empty name="to" property="attMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="attMaxMarks">-</logic:equal> <logic:notEqual value="-" name="to" property="attMaxMarks"><bean:write name="to" property="attMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="attMarksAwarded">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="eseMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="eseMaxMarks"><bean:write name="to" property="eseMaxMarks"/></logic:notEqual>  <logic:empty name="to" property="eseMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"> <bean:write name="to" property="eseMinMarks"/> <logic:empty name="to" property="eseMinMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="eseMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="eseMaxMarks"><bean:write name="to" property="eseMarksAwarded"/></logic:notEqual>  <logic:empty name="to" property="eseMarksAwarded">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="totalMaxMarks">-</logic:equal> <logic:notEqual value="00" name="to" property="totalMaxMarks"><bean:write name="to" property="totalMaxMarks"/></logic:notEqual> <logic:empty name="to" property="totalMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="totalMaxMarks">-</logic:equal> <logic:notEqual value="-" name="to" property="totalMaxMarks"><bean:write name="to" property="totalMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="totalMarksAwarded">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="0" name="to" property="credits">-</logic:equal> <logic:notEqual value="00" name="to" property="credits"><bean:write name="to" property="credits"/></logic:notEqual> <logic:empty name="to" property="credits">- </logic:empty></td>
												<td  class="row-print"> <bean:write name="to" property="grade"/> <logic:empty name="to" property="grade">- </logic:empty></td>
											</tr>
											</c:if>
											<c:if test="${to.practical!=null && to.practical==true && to.displaySubject==true}" >
											<tr height="21px">
												<td align="center" class="row-print">
												<c:if test="${to.theory!=null && to.theory==false}">
												</c:if>
												</td>
												<td class="row-print">
												<c:if test="${to.theory!=null && to.theory==false}">
												<bean:write name="to" property="name"/>
												</c:if>
												</td>  
												<td align="center" class="row-print"> <bean:write name="to" property="subjectType"/> </td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalCiaMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalCiaMaxMarks"> <bean:write name="to" property="practicalCiaMaxMarks"/></logic:notEqual>  <logic:empty name="to" property="practicalCiaMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalCiaMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalCiaMaxMarks"> <bean:write name="to" property="practicalCiaMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalCiaMarksAwarded">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalAttMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalAttMaxMarks"> <bean:write name="to" property="practicalAttMaxMarks"/></logic:notEqual> <logic:empty name="to" property="practicalAttMaxMarks">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalAttMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalAttMaxMarks"> <bean:write name="to" property="practicalAttMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalAttMarksAwarded">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalEseMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalEseMaxMarks"> <bean:write name="to" property="practicalEseMaxMarks"/></logic:notEqual> <logic:empty name="to" property="practicalEseMaxMarks">- </logic:empty> </td>
												<td align="center" class="row-print"><bean:write name="to" property="practicalEseMinMarks"/> <logic:empty name="to" property="practicalEseMinMarks">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalEseMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalEseMaxMarks"> <bean:write name="to" property="practicalEseMarksAwarded"/></logic:notEqual>  <logic:empty name="to" property="practicalEseMarksAwarded">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalTotalMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalTotalMaxMarks"> <bean:write name="to" property="practicalTotalMaxMarks"/></logic:notEqual>  <logic:empty name="to" property="practicalTotalMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalTotalMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalTotalMaxMarks"> <bean:write name="to" property="practicalTotalMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalTotalMarksAwarded">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="0" name="to" property="practicalCredits">-</logic:equal> <logic:notEqual value="00" name="to" property="practicalCredits"><bean:write name="to" property="practicalCredits"/></logic:notEqual> <logic:empty name="to" property="practicalCredits">- </logic:empty></td>
												<td   class="row-print"> <bean:write name="to" property="practicalGrade"/> <logic:empty name="to" property="practicalGrade">- </logic:empty></td>
											</tr>
											</c:if>
											</logic:iterate>
											</logic:notEmpty>
										</logic:iterate>
										</logic:notEmpty>
										<tr height="21px">
											
											<td colspan="2" align="center">SEMESTER RESULT</td>
											
											<td  align="center"  class="row-print"><bean:write name="loginform" property="marksCardTo.totalCredits"/></td>
											
											<td colspan="4" align="center">
											<c:choose>
										<c:when test="${loginform.marksCardTo.sgpa!=null }">
										<c:choose>
											
											
											<c:when test="${loginform.marksCardTo.sgpa!=' ' }">
												SCPA:<bean:write name="loginform" property="marksCardTo.sgpa"/></c:when>
												
											</c:choose></c:when></c:choose></td>
										
											
											<td align="center" class="row-print">
											<bean:write name="loginform" property="marksCardTo.totalMarksAwarded"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="loginform" property="marksCardTo.totalMaxmarks"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="loginform" property="marksCardTo.totalGrade"/>
											</td>
											<td></td>
											<td align="center" class="row-print">
											<bean:write name="loginform" property="marksCardTo.totalGradePoints"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="loginform" property="marksCardTo.resultClass"/>
											</td>
											
										</tr>
										</table>
								</td>
							</tr>
						<tr> 
							<td colspan="2">
							<table width="100%">
							<tr>
							<td align="left" width="80%">
						<table>
								<tr>
							<td class="row-print">* ESE : </td>
							<td class="row-print">End Semester Examination</td>
							</tr>
							<tr>
							<td class="row-print">* CIA :</td>
							<td class="row-print"> Continuous Internal Assessment</td>
							</tr>
							
							</table>
							</td>
							
							<td align="right" width="20%">
							<table>
							<tr>
														
							</tr>
							</table>
							</td>
							
							</tr>
							
							
							</table>
							</td>
							</tr>


		
		



	</table>
				
		
	
</html:form>
<script type="text/javascript">window.print();</script>