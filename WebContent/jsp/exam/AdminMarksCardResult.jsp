<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<style type="text/css">
.row-white1 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: normal;
	color: #000000;
	background-color: #FFFFFF;
	border: 1.5px solid black;
}
</style>
	
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" href="css/calendar.css">
<html:form action="/adminMarksCard" >
	<html:hidden property="method" styleId="method" value="printMarksCard" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="adminMarksCardForm" />
	<c:if test="${adminMarksCardForm.exam1=='Regular'}">
	<logic:equal value="false" name="adminMarksCardForm" property="pg">
	<logic:notEmpty name="adminMarksCardForm" property="studentList">
								<logic:iterate id="mto" name="adminMarksCardForm" property="studentList" indexId="count">
								<c:if test="${count>0}">
		<p style="page-break-after:always;"> </p>
					
		</c:if>
									<table width="100%" height="100%" cellspacing="1" cellpadding="2"  class="row-white1" >
								<bean:define id="reg" name="mto" property="regNo"></bean:define>
							<tr>
								<td width="100%" align="center" colspan="2">
								<img src='<%=request.getContextPath()
										+ "/LogoServlet?count=1"%>'  height="100" width="500" />
								</td>
							</tr>

							<tr height="21px">
								<td colspan="2" align="center" width="100%" font-family= verdana><b> <bean:write name="mto" property="semString"/> SEMESTER <bean:write name="mto" property="ugorpg"/> EXAMINATION <bean:write name="mto" property="monthYear"/></b> </td>
								
							</tr>
							<tr height="25px">
								<td colspan="2"  align="center" width="100%" class="row-print"> <b> STATEMENT OF MARKS</b> </td>
								
							</tr>
					
							<tr>
								<td width="75%">
									<table width="50%">
									
									<tr><td class="row-print"> Programme</td>
										<td class="row-print"> :</td>
										<td class="row-print"><bean:write name="mto" property="courseName"/></td>
										
									</tr>
<br/>
									<tr>
										<td class="row-print">Name Of Candidate</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="mto" property="studentName"/></td>
									</tr>
<br/>
                                     <tr> <td class="row-print"> Register No</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="mto" property="regNo"/></td>
									</tr></table>
									
											
								</td>
								<td width="25%">
									<table width="100%">
									
								<tr>
										<td width="20%" align="right">
								<img src='images/StudentPhotos/<c:out value="${mto.studentId}"/>.jpg?<%=System.currentTimeMillis() %>'  height="120" width="128" />
								</td>
	                                </tr>            
									</table>
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
									<logic:notEmpty name="mto" property="subMap">
										<logic:iterate id="map" name="mto" property="subMap" indexId="count1">
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
											
										
										
										<logic:notEmpty name="mto" property="nonElectivesubMap">
										<c:set var="slnocount" value="${slnocount + 1 }" />
										<logic:iterate id="map" name="mto" property="nonElectivesubMap" indexId="count2">
										
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
										
										
										
										
										<logic:notEmpty name="mto" property="addOnCoursesubMap">
										<c:set var="slnocount" value="${slnocount + 1 }" />
										<logic:iterate id="map" name="mto" property="addOnCoursesubMap" indexId="count3">
										
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
											<!--<td colspan="10" align="center"  class="row-print">
												Total Marks (In Words): <bean:write name="mto" property="totalMarksInWord"/>
											</td>
											-->
											<td colspan="2" align="center">SEMESTER RESULT</td>
											
											<td  align="center"  class="row-print"><bean:write name="mto" property="totalCredits"/></td>
											
											<td colspan="4" align="center">
											<c:choose>
										<c:when test="${mto.sgpa!=null }">
										<c:choose>
											
											
											<c:when test="${mto.sgpa!=' ' }">
												SGPA:<bean:write name="mto" property="sgpa"/></c:when>
												
											</c:choose></c:when></c:choose></td>
										
											
											<td align="center" class="row-print">
											<bean:write name="mto" property="totalMarksAwarded"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="mto" property="totalMaxmarks"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="mto" property="totalGrade"/>
											</td>
											<td></td>
											<td align="center" class="row-print">
											<bean:write name="mto" property="totalGradePoints"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="mto" property="resultClass"/>
											</td>
											<!--<td colspan="2"></td>
										--></tr>
										<!--<tr height="21px">
											<td colspan="7" align="center"  class="row-print">
											 Result:<bean:write name="mto" property="result"/>
											</td>
											<td colspan="3"  class="row-print">
											 Total Credits Awarded:<bean:write name="mto" property="totalCredits"/>
											</td>
											<td colspan="4" align="center" class="row-print">
												Grade Points Average :<bean:write name="mto" property="gradePoints"/>
											</td>
										</tr>
										
										
									--></table>
								</td>
							</tr>
							<!--<tr height="21px">
							<td colspan="2"  class="row-print">
							<logic:notEmpty name="adminMarksCardForm" property="description">
								<c:out value="${adminMarksCardForm.description}" escapeXml="false"></c:out>
							</logic:notEmpty>
							</td>
							</tr>-->
						<%-- 	<tr>
							<td class="row-print">* ESA : End Semester Assessment
							<tr>
							<td class="row-print">* ISA : Internal Semester Assessment</td></tr>
							</td>
							<td rowspan="2" align="right">
								<img src="images/COEFinal.jpg" width="157px" height="72px" />
							</td>
							</tr>--%>
							
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
							<td rowspan="2" align="right">
								<img src="images/COEFinal.jpg" width="157px" height="72px" />
							</td>							
							</tr>
							</table>
							</td>
							
							</tr>
							
							
							</table>
							</td>
							</tr>
						<tr>
							<td colspan="2">

							<table width="100%">

								<tr>
									<td class="row-print" width="49%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SUBJECT
									WISE GRADE DEFINITION</td>
									<td class="row-print" width="49%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SGPA
									GRADE DEFINITION</td>
								</tr>


							</table>
							</td>
						</tr>

						<tr>
							<td style="width: 60%; float: left">
							<table width="200px" height="150px" align="center" border="1"
								rules="all">
								<tr>
									<td colspan="1" class="row-print">PERCENTAGE OF MARK</td>
									<td colspan="1" class="row-print">GRADE</td>
									<td colspan="1" class="row-print">GP</td>
								</tr>
								<logic:notEmpty name="adminMarksCardForm"
									property="examSubCoursewiseGradeDefnTOList">

									<logic:iterate id="gradeDefEntry" name="adminMarksCardForm"
										property="examSubCoursewiseGradeDefnTOList" indexId="count333">
										<tr>
											<td class="row-print"><bean:write
												property="startPrcntgGrade" name="gradeDefEntry" /> to <bean:write
												property="endPrcntgGrade" name="gradeDefEntry" /></td>
											<td class="row-print"><bean:write property="grade"
												name="gradeDefEntry" /></td>
											<td class="row-print"><bean:write property="gradePoint"
												name="gradeDefEntry" /></td>

										</tr>
									</logic:iterate>
								</logic:notEmpty>


							</table>
							</td>
							<td style="width: 30%; float: left">
							<table class="row-print" width="200%" height="150px" align="left"
								border="1" rules="all">
								<tr>
									<td colspan="1" class="row-print">SGPA/CGPA</td>
									<td colspan="1" class="row-print">GRADE</td>
								</tr>


								<logic:notEmpty name="adminMarksCardForm"
									property="courseWiseGradeList">
									<logic:iterate id="gradePointDefEntry" name="mto"
										property="courseWiseGradeList" indexId="count211">
										<tr>
											<td colspan="1" class="row-print"><bean:write
												property="startPercentage" name="gradePointDefEntry" /> to
											<bean:write property="endPercentage"
												name="gradePointDefEntry" /></td>
											<td colspan="1" class="row-print"><bean:write
												property="grade" name="gradePointDefEntry" /> <logic:notEmpty property="interpretation" name="gradePointDefEntry"> - </logic:notEmpty> <bean:write
												property="interpretation" name="gradePointDefEntry" /></td>

										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
							</td>
						</tr>





						<!-- increasing code -->
<bean:define id="dynamic" name="adminMarksCardForm" property="dynamicSize"></bean:define>
<tr height='<%=dynamic%>'><td>&nbsp;</td></tr>							
							
							
							
							</table>
								</logic:iterate>
								</logic:notEmpty>
					</logic:equal>
					
					
					
					
					
					<logic:equal value="true" name="adminMarksCardForm" property="pg">
				<logic:notEmpty name="adminMarksCardForm" property="studentList">
					<logic:iterate id="mto" name="adminMarksCardForm" property="studentList" indexId="count">
					<c:if test="${count>0}">
		<p style="page-break-after:always;"> </p>
					
		</c:if>
					<table width="100%" height="110%" cellspacing="1" cellpadding="2"
						class="row-white1">
						<bean:define id="reg" name="mto" property="regNo"></bean:define>
						<tr>
							<td width="100%" align="center"><img
								src='<%=request.getContextPath()
										+ "/LogoServlet?count=1"%>'
								height="150" width="500" /> <img
								src='images/StudentPhotos/<c:out value="${mto.studentId}"/>.jpg?<%=System.currentTimeMillis() %>'
								height="120" width="128" /></td>
						</tr>
						<tr height="30px"></tr>
								<tr height="21px">
								<td colspan="2" align="center" width="100%" font-family= verdana><b> <bean:write name="mto" property="semString"/> SEMESTER <bean:write name="mto" property="ugorpg"/> EXAMINATION <bean:write name="mto" property="monthYear"/></b> </td>
								
							</tr>

						<tr height="25px">
							<td align="center" width="80%" class="row-print"><b>
							STATEMENT OF MARKS</b></td>

						</tr>

						<tr>


							<td colspan="2">
							<table width="100%">
								<tr>
									<td width="17%" class="row-print">Programme</td>
									<td class="row-print">:</td>
									<td class="row-print"><bean:write name="mto"
										property="courseName" /></td>
								</tr>
				
								<tr>
									<tr>
										<td width="17%" class="row-print">Name Of Candidate</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="mto"
											property="studentName" /></td>
									</tr>
									<tr>
										<td width="17%" class="row-print">Register No</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="mto"
											property="regNo" /></td>
									</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="2">
							<table width="100%" style="border: 1px solid black;" rules="all"
								style="font:5px ">
								<tr height="21px">

									<td rowspan="2" class="row-print" align="center" width="3%">
									Course Code</td>
									<td rowspan="2" class="row-print" align="center" width="25%">
									Course</td>
									<!--<td rowspan="2" class="row-print" align="center" width="5%">
						                                    TYPE
						                        </td>
						                         -->
									<td rowspan="2" align="center" class="row-print" width="6%">
									Credit</td>
									<td colspan="2" align="center" class="row-print" width="15%">
									ESE</td>
									<!--
						                        <td colspan="2" align="center" class="row-print" width="15%">
						                                    ATT
						                        </td>
						                        -->
									<td colspan="2" align="center" class="row-print" width="17%">
									CIA</td>
									<td rowspan="2" align="center" class="row-print" width="10%">
									TOTAL</td>
									<td rowspan="2" align="center" class="row-print" width="6%">
									MAX MARKS</td>
									<td rowspan="2" align="center" class="row-print" width="6%">
									SUB AVG</td>
									<td rowspan="2" align="center" class="row-print" width="6%">
									GRADE</td>
									<td rowspan="2" align="center" class="row-print" width="6%">
									GP</td>
									<td rowspan="2" align="center" class="row-print" width="6%">
									CP</td>
									<td rowspan="2" align="center" class="row-print" width="6%">
									Result</td>
								</tr>
								<tr height="21px">
									<td align="center" class="row-print">Awarded</td>
									<td align="center" class="row-print">MAX</td>
									<!--
						                         <td align="center" class="row-print">
						                                    MAX 
						                        </td>
						                        <td align="center" class="row-print">
						                                    MARKS AWARDED
						                        </td>
						                        -->
									<td align="center" class="row-print">Awarded</td>
									<!--<td align="center" class="row-print">
						                                    MIN MARKS
						                        </td>
						                        -->
									<td align="center" class="row-print">MAX</td>
									<!--
						                       
						                        <td align="center" class="row-print">
						                                    MARKS AWARDED
						                        </td>               
						            -->
								</tr>
								<c:set var="slnocount" value="0" />
								<logic:notEmpty name="mto" property="subMap">
									<logic:iterate id="map" name="mto" property="subMap"
										indexId="count1">
										<!--<tr height="21px">
												<td></td>
												<td colspan="13" class="row-print"> <bean:write name="map" property="key"/> </td>
											</tr>-->
										<logic:notEmpty name="map" property="value">
											<logic:iterate id="to" name="map" property="value">
												<c:set var="slnocount" value="${slnocount + 1 }" />
												<c:if test="${to.theory!=null && to.theory==true }">
													<tr height="21px">
														<!--<td align="center" class="row-print"> <c:out value="${slnocount}" /> </td>
												-->
														<td class="row-print"><bean:write name="to"
															property="code" /></td>
														<td class="row-print"><bean:write name="to"
															property="name" /></td>
														<td align="center" class="row-print"><logic:equal
															value="0" name="to" property="credits"></logic:equal> <logic:notEqual
															value="00" name="to" property="credits">
															<bean:write name="to" property="credits" />
														</logic:notEqual> <logic:empty name="to" property="credits">
														</logic:empty></td>
														<!--<td align="center" class="row-print"> <bean:write name="to" property="type"/> </td>-->
														<td align="center" class="row-print"><logic:equal
															value="-" name="to" property="eseMaxMarks"></logic:equal><logic:notEqual
															value="-" name="to" property="eseMaxMarks">
															<bean:write name="to" property="eseMarksAwarded" />
														</logic:notEqual> <logic:empty name="to" property="eseMarksAwarded"></logic:empty></td>

														<td align="center" class="row-print"><logic:equal
															value="00" name="to" property="eseMaxMarks"></logic:equal><logic:notEqual
															value="00" name="to" property="eseMaxMarks">
															<bean:write name="to" property="eseMaxMarks" />
														</logic:notEqual> <logic:empty name="to" property="eseMaxMarks"></logic:empty></td>

														<td align="center" class="row-print"><logic:equal
															value="-" name="to" property="ciaMaxMarks"></logic:equal>
														<logic:empty name="to" property="ciaMaxMarks"></logic:empty>
														<logic:notEqual value="-" name="to" property="ciaMaxMarks">
															<logic:notEmpty name="to" property="ciaMaxMarks">
																<bean:write name="to" property="ciaMarksAwarded" />
															</logic:notEmpty>
														</logic:notEqual><logic:empty name="to" property="ciaMarksAwarded">
														</logic:empty></td>

														<td align="center" class="row-print"><logic:equal
															value="00" name="to" property="ciaMaxMarks"></logic:equal>
														<logic:notEqual value="00" name="to"
															property="ciaMaxMarks">
															<bean:write name="to" property="ciaMaxMarks" />
														</logic:notEqual> <logic:empty name="to" property="ciaMaxMarks"></logic:empty>
														</td>
														<!--<td align="center" class="row-print"><logic:equal value="00" name="to" property="attMaxMarks">-</logic:equal> <logic:notEqual value="00" name="to" property="attMaxMarks"><bean:write name="to" property="attMaxMarks"/></logic:notEqual> <logic:empty name="to" property="attMaxMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="attMaxMarks">-</logic:equal> <logic:notEqual value="-" name="to" property="attMaxMarks"><bean:write name="to" property="attMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="attMarksAwarded">- </logic:empty></td>
												-->
														<!--<td align="center" class="row-print"> <bean:write name="to" property="eseMinMarks"/> <logic:empty name="to" property="eseMinMarks">- </logic:empty></td>
												-->
														<td align="center" class="row-print"><logic:equal
															value="-" name="to" property="totalMaxMarks"></logic:equal>
														<logic:notEqual value="-" name="to"
															property="totalMaxMarks">
															<bean:write name="to" property="totalMarksAwarded" />
														</logic:notEqual> <logic:empty name="to" property="totalMarksAwarded">
														</logic:empty></td>

														<td align="center" class="row-print"><logic:equal
															value="00" name="to" property="totalMaxMarks"></logic:equal>
														<logic:notEqual value="00" name="to"
															property="totalMaxMarks">
															<bean:write name="to" property="totalMaxMarks" />
														</logic:notEqual> <logic:empty name="to" property="totalMaxMarks"></logic:empty></td>
														<td align="center" class="row-print"><logic:equal
															value="00" name="to" property="avgmark"></logic:equal> <logic:notEqual
															value="00" name="to" property="avgmark">
															<bean:write name="to" property="avgmark" />
														</logic:notEqual> <logic:empty name="to" property="avgmark">
														</logic:empty></td>

														<td class="row-print" align="center"><bean:write
															name="to" property="grade" /> <logic:empty name="to"
															property="grade"></logic:empty></td>
														<td class="row-print" align="center"><bean:write
															name="to" property="theoryGradePnt" /> <logic:empty
															name="to" property="theoryGradePnt"></logic:empty></td>
														<td class="row-print" align="center"><bean:write
															name="to" property="creditPoint" /> <logic:empty
															name="to" property="creditPoint"></logic:empty></td>
														<td class="row-print" align="center"><bean:write
															name="to" property="resultClass" /> <logic:empty
															name="to" property="resultClass">
														</logic:empty></td>

													</tr>
												</c:if>
												<c:if test="${to.practical!=null && to.practical==true }">
													<tr height="21px">
														<td align="center" class="row-print"><c:if
															test="${to.theory!=null && to.theory==false}">
															<!--<c:out value="${slnocount}" />-->
															<bean:write name="to" property="code" />
														</c:if></td>
														<td class="row-print"><c:if
															test="${to.theory!=null && to.theory==false}">
															<bean:write name="to" property="name" />
														</c:if></td>
														<td align="center" class="row-print"><logic:equal
															value="0" name="to" property="practicalCredits"></logic:equal>
														<logic:notEqual value="00" name="to"
															property="practicalCredits">
															<bean:write name="to" property="practicalCredits" />
														</logic:notEqual> <logic:empty name="to" property="practicalCredits"></logic:empty></td>
														<!--<td   class="row-print"> <bean:write name="to" property="subjectType"/> </td>
												-->
														<!--<td align="center" class="row-print"><logic:equal value="00" name="to" property="practicalAttMaxMarks">-</logic:equal><logic:notEqual value="00" name="to" property="practicalAttMaxMarks"> <bean:write name="to" property="practicalAttMaxMarks"/></logic:notEqual> <logic:empty name="to" property="practicalAttMaxMarks">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalAttMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalAttMaxMarks"> <bean:write name="to" property="practicalAttMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalAttMarksAwarded">- </logic:empty> </td>
												-->
														<td align="center" class="row-print"><logic:equal
															value="-" name="to" property="practicalEseMaxMarks"></logic:equal><logic:notEqual
															value="-" name="to" property="practicalEseMaxMarks">
															<bean:write name="to" property="practicalEseMarksAwarded" />
														</logic:notEqual> <logic:empty name="to"
															property="practicalEseMarksAwarded"></logic:empty></td>

														<td align="center" class="row-print"><logic:equal
															value="00" name="to" property="practicalEseMaxMarks"></logic:equal><logic:notEqual
															value="00" name="to" property="practicalEseMaxMarks">
															<bean:write name="to" property="practicalEseMaxMarks" />
														</logic:notEqual> <logic:empty name="to" property="practicalEseMaxMarks"></logic:empty>
														</td>
														<!--<td align="center" class="row-print"><bean:write name="to" property="practicalEseMinMarks"/> <logic:empty name="to" property="practicalEseMinMarks">- </logic:empty> </td>-->
														<td align="center" class="row-print"><logic:equal
															value="-" name="to" property="practicalCiaMaxMarks"></logic:equal><logic:notEqual
															value="-" name="to" property="practicalCiaMaxMarks">
															<bean:write name="to" property="practicalCiaMarksAwarded" />
														</logic:notEqual> <logic:empty name="to"
															property="practicalCiaMarksAwarded"></logic:empty></td>

														<td align="center" class="row-print"><logic:equal
															value="00" name="to" property="practicalCiaMaxMarks"></logic:equal><logic:notEqual
															value="00" name="to" property="practicalCiaMaxMarks">
															<bean:write name="to" property="practicalCiaMaxMarks" />
														</logic:notEqual> <logic:empty name="to" property="practicalCiaMaxMarks"></logic:empty></td>
														<td align="center" class="row-print"><logic:equal
															value="-" name="to" property="practicalTotalMaxMarks"></logic:equal><logic:notEqual
															value="-" name="to" property="practicalTotalMaxMarks">
															<bean:write name="to"
																property="practicalTotalMarksAwarded" />
														</logic:notEqual> <logic:empty name="to"
															property="practicalTotalMarksAwarded"></logic:empty></td>

														<td align="center" class="row-print"><logic:equal
															value="00" name="to" property="practicalTotalMaxMarks"></logic:equal><logic:notEqual
															value="00" name="to" property="practicalTotalMaxMarks">
															<bean:write name="to" property="practicalTotalMaxMarks" />
														</logic:notEqual> <logic:empty name="to" property="practicalTotalMaxMarks"></logic:empty></td>
														<td align="center" class="row-print"><logic:equal
															value="00" name="to" property="avgmark"></logic:equal> <logic:notEqual
															value="00" name="to" property="avgmark">
															<bean:write name="to" property="avgmark" />
														</logic:notEqual> <logic:empty name="to" property="avgmark"></logic:empty></td>


														<td class="row-print" align="center"><bean:write
															name="to" property="practicalGrade" /><logic:empty
															name="to" property="practicalGrade">
														</logic:empty></td>
														<td class="row-print" align="center"><bean:write
															name="to" property="practicalGradePnt" /><logic:empty
															name="to" property="practicalGradePnt">
														</logic:empty></td>
														<td class="row-print" align="center"><bean:write
															name="to" property="creditPoint" /> <logic:empty
															name="to" property="creditPoint"></logic:empty></td>
														<td class="row-print" align="center"><bean:write
															name="to" property="resultClass" /> <logic:empty
															name="to" property="resultClass"></logic:empty></td>

													</tr>
												</c:if>
											</logic:iterate>
										</logic:notEmpty>
									</logic:iterate>
								</logic:notEmpty>
								<tr height="21px">
									<!--<td colspan="10" align="center"  class="row-print">
												Total Marks (In Words): <bean:write name="mto" property="totalMarksInWord"/>
											</td>
											-->
									<td colspan="2" align="center">SEMESTER RESULT</td>

									<td align="center" class="row-print"><bean:write
										name="mto" property="totalCredits" /></td>

									<td colspan="7" align="center"><c:choose>
										<c:when test="${mto.sgpa!=null }">
											<c:choose>


												<c:when test="${mto.sgpa!=' ' }">
												SGPA:<bean:write name="mto" property="sgpa" />
												</c:when>

											</c:choose>
										</c:when>
									</c:choose></td>


									<!--<td align="center" class="row-print">
											<bean:write name="mto" property="totalMarksAwarded"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="mto" property="totalMaxmarks"/>
											</td>-->
									<td align="center" class="row-print"><bean:write
										name="mto" property="totalGrade" /></td>
									<td></td>
									<td align="center" class="row-print"><bean:write
										name="mto" property="totalGradePoints" /></td>
									<td align="center" class="row-print"><bean:write
										name="mto" property="resultClass" /></td>
									<!--<td colspan="2"></td>
										-->
								</tr>
								<!--<tr height="25px" class="row-print">
											<td colspan="8" align="center" class="row-print">
												Total Marks (In Words): <bean:write name="mto" property="totalMarksInWord"/>
											</td>
											<td class="row-print" align="center" >
											<bean:write name="mto" property="totalMaxmarks"/>
											</td>
											<td class="row-print" align="center" >
											<bean:write name="mto" property="totalMarksAwarded"/>
											</td>
											<td colspan="2"></td>
										</tr>
										<tr height="25px">
											<td colspan="2" align="center" class="row-print">
											 Result:<bean:write name="mto" property="result"/>
											</td>
											<td colspan="6" align="center" class="row-print">
											 Total Credits Awarded:<bean:write name="mto" property="totalCredits"/>
											</td>
											<td colspan="4" class="row-print">
												Grade Points Average :<bean:write name="mto" property="gradePoints"/>
											</td>
										</tr>
									-->
							</table>
							</td>
						</tr>
						<!--<tr height="21px">
							<td colspan="2"  class="row-print-desc">
							<logic:notEmpty name="adminMarksCardForm" property="description">
							<font size="6px">
								<c:out value="${adminMarksCardForm.description}" escapeXml="false"></c:out>
							</font>
							</logic:notEmpty>
							</td>
							</tr>
							-->
						<tr>
							<td colspan="2">
							<table width="100%">
								<tr>
									<td align="left" width="80%">
									<table>
										<tr>
											<td class="row-print">* ESE :</td>
											<td class="row-print">End Semester Examination</td>
										</tr>
										<tr>
											<td class="row-print">* CIA :</td>
											<td class="row-print">Continuous Internal Assessment</td>
										</tr>

									</table>
									</td>

									<td align="right" width="20%">
									<table>
										<tr>
											<td rowspan="2" align="right"><img
												src="images/COEFinal.jpg" width="157px" height="72px" /></td>
										</tr>
									</table>
									</td>

								</tr>


							</table>
							</td>
						</tr>


						<tr>
							<td style="width: 60%; float: left" class="row-print">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SUBJECT
							WISE GRADE DEFINITION</td>

							<td style="width: 30%; float: left" class="row-print">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SCPA
							GRADE DEFINITION</td>
						</tr>
						<tr>
							<td style="width: 60%; float: left">
							<table width="200px" height="150px" align="center" border="1"
								rules="all">
								<tr>
									<td colspan="1" class="row-print">PERCENTAGE OF MARK</td>
									<td colspan="1" class="row-print">GRADE</td>
									<td colspan="1" class="row-print">GP</td>
								</tr>

								<logic:notEmpty name="adminMarksCardForm"
									property="examSubCoursewiseGradeDefnTOList">

									<logic:iterate id="gradeDefEntry" name="adminMarksCardForm"
										property="examSubCoursewiseGradeDefnTOList"
										indexId="count3353">
										<tr>
											<td class="row-print"><bean:write
												property="startPrcntgGrade" name="gradeDefEntry" /> to <bean:write
												property="endPrcntgGrade" name="gradeDefEntry" /></td>
											<td class="row-print"><bean:write property="grade"
												name="gradeDefEntry" /></td>
											<td class="row-print"><bean:write property="gradePoint"
												name="gradeDefEntry" /></td>

										</tr>
									</logic:iterate>
								</logic:notEmpty>



							</table>
							</td>
							<td style="width: 30%; float: left">
							<table width="200px" height="150px" align="left" border="1"
								rules="all">
								<tr>
									<td colspan="1" class="row-print">SGPA/CGPA</td>
									<td colspan="1" class="row-print">GRADE</td>
								</tr>

								<logic:notEmpty name="adminMarksCardForm"
									property="courseWiseGradeList">
									<logic:iterate id="gradePointDefEntry" name="mto"
										property="courseWiseGradeList" indexId="count2511">
										<tr>
											<td colspan="1" class="row-print"><bean:write
												property="startPercentage" name="gradePointDefEntry" /> to
											<bean:write property="endPercentage"
												name="gradePointDefEntry" /></td>
											<td colspan="1" class="row-print"><bean:write
												property="grade" name="gradePointDefEntry" /><logic:notEmpty
												property="interpretation" name="gradePointDefEntry"> - </logic:notEmpty><bean:write
												property="interpretation" name="gradePointDefEntry" /></td>

										</tr>
									</logic:iterate>
								</logic:notEmpty>

							</table>
							</td>
						</tr>
						<!-- increasing code -->
						<bean:define id="dynamic" name="adminMarksCardForm"
							property="dynamicSize"></bean:define>
						<tr height='<%=dynamic%>'>
							<td>&nbsp;</td>
						</tr>
					</table>
				</logic:iterate>
							</logic:notEmpty></logic:equal>
				</c:if>
				<c:if test="${adminMarksCardForm.exam1=='Supplementary'}">
	<logic:equal value="false" name="adminMarksCardForm" property="pg">
	<logic:notEmpty name="adminMarksCardForm" property="studentList">
								<logic:iterate id="mto" name="adminMarksCardForm" property="studentList" indexId="count">
								<c:if test="${count>0}">
		<p style="page-break-after:always;"> </p>
					<p style="page-break-after:always;"> </p>
		</c:if>
									<table width="100%" cellspacing="1" cellpadding="2" class="row-white1">
									
								<bean:define id="reg" name="mto" property="regNo"></bean:define>
							<tr>
								<td width="80%" align="center">
								<img src='<%=request.getContextPath()
										+ "/LogoServlet?count=1"%>'  height="100" width="210" />
								</td>
								<td width="20%" align="right">
								<img src="<%=request.getContextPath()%>/MarksCardPhotoServlet?studentPhoto=<%=reg%>"  height="128" width="133" />
								</td>
							</tr>
								<tr height="21px">
								<td colspan="2" align="center" width="100%" fontsize="30" font-family= verdana><b><bean:write name="mto" property="semString"/> SEMESTER  <bean:write name="mto" property="examName"/></b> </td>
								
							</tr>
							<tr height="25px">
								<td colspan="2"  align="center" width="100%" class="row-print"> <b> STATEMENT OF MARKS</b> </td>
								
							</tr>
							<tr>
								<td colspan="2">
									<table width="100%">
										<td width="75%">
									<table width="50%">
									
									<tr><td class="row-print"> Programme</td>
										<td class="row-print"> :</td>
										<td class="row-print"><bean:write name="mto" property="courseName"/></td>
										
									</tr>
<br/>
									<tr>
										<td class="row-print">Name Of Candidate</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="mto" property="studentName"/></td>
									</tr>
<br/>
                                     <tr> <td class="row-print"> Register No</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="mto" property="regNo"/></td>
									</tr></table>
									
											
								</td>
									</table>
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
									<logic:notEmpty name="mto" property="subMap">
										<logic:iterate id="map" name="mto" property="subMap" indexId="count4">
											<!--<tr height="21px">
												<td></td>
												<td colspan="11" class="row-print"> <bean:write name="map" property="key"/> </td>
											</tr>
											--><logic:notEmpty name="map" property="value">
											<logic:iterate id="to" name="map" property="value">
											<c:set var="slnocount" value="${slnocount + 1 }" />
											<c:if test="${to.theory!=null && to.theory==true && to.displaySubject==true && to.appearedTheory==true}">
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
											
										
										
										<logic:notEmpty name="mto" property="nonElectivesubMap">
										<c:set var="slnocount" value="${slnocount + 1 }" />
										<logic:iterate id="map" name="mto" property="nonElectivesubMap" indexId="count5">
										
											<!--<tr height="21px">
												<td class="row-print" align="center"><c:out value="${slnocount}" /></td>
												<td colspan="11" class="row-print"><bean:write name="map" property="key"/>/s </td>
											</tr>
											--><logic:notEmpty name="map" property="value">
											<logic:iterate id="to" name="map" property="value">
											
											<c:if test="${to.theory!=null && to.theory==true && to.displaySubject==true && to.appearedTheory==true}" >
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
										
										
										
										
										<logic:notEmpty name="mto" property="addOnCoursesubMap">
										<c:set var="slnocount" value="${slnocount + 1 }" />
										<logic:iterate id="map" name="mto" property="addOnCoursesubMap" indexId="count6">
										
											<!--<tr height="21px">
												<td class="row-print" align="center"><c:out value="${slnocount}" /></td>
												<td colspan="11" class="row-print"> <bean:write name="map" property="key"/>/s </td>
											</tr>
											--><logic:notEmpty name="map" property="value">
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
											<!--<td colspan="10" align="center"  class="row-print">
												Total Marks (In Words): <bean:write name="mto" property="totalMarksInWord"/>
											</td>
											-->
											<td colspan="2" align="center">SEMESTER RESULT</td>
											
											<td  align="center"  class="row-print"><bean:write name="mto" property="totalCredits"/></td>
											
											<td colspan="4" align="center">
											<c:choose>
										<c:when test="${mto.sgpa!=null }">
										<c:choose>
											
											
											<c:when test="${mto.sgpa!=' ' }">
												SGPA:<bean:write name="mto" property="sgpa"/></c:when>
												
											</c:choose></c:when></c:choose></td>
										
											
											<td align="center" class="row-print">
											<bean:write name="mto" property="totalMarksAwarded"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="mto" property="totalMaxmarks"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="mto" property="totalGrade"/>
											</td>
											<td></td>
											<td align="center" class="row-print" >
											<bean:write name="mto" property="totalGradePoints"/>
											</td>
											<td align="center" class="row-print" >
											<bean:write name="mto" property="resultClass"/>
											</td>
											<!--<td colspan="2"></td>
										--></tr>
										<!--<tr height="21px">
											<td colspan="5" align="center"  class="row-print">
											 Result:<bean:write name="mto" property="result"/>
											</td>
											<td colspan="3"  class="row-print">
											 Total Credits Awarded:<bean:write name="mto" property="totalCredits"/>
											</td>
											<td colspan="4" align="center" class="row-print">
												Grade Points Average :<bean:write name="mto" property="gradePoints"/>
											</td>
										</tr>
										
										
									--></table>
								</td>
							</tr>
							<tr height="21px">
							<td colspan="2"  class="row-print">
							<logic:notEmpty name="adminMarksCardForm" property="description">
								<c:out value="${adminMarksCardForm.description}" escapeXml="false"></c:out>
							</logic:notEmpty>
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
							<td rowspan="2" align="right">
								<img src="images/COEFinal.jpg" width="157px" height="72px" />
							</td>							
							</tr>
							</table>
							</td>
							
							</tr>
							
							
							</table>
							</td>
							</tr>
							
								<tr>
							<td colspan="2">

							<table width="100%">

								<tr>
									<td class="row-print" width="49%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SUBJECT
									WISE GRADE DEFINITION</td>
									<td class="row-print" width="49%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SGPA
									GRADE DEFINITION</td>
								</tr>


							</table>
							</td>
						</tr>
							
							
									<tr>
							<td style="width: 60%; float: left">
							<table width="200px" height="200px" align="center" border="1"
								rules="all">
								<tr>
									<td colspan="1" class="row-print">PERCENTAGE OF MARK</td>
									<td colspan="1" class="row-print">GRADE</td>
									<td colspan="1" class="row-print">GP</td>
								</tr>

								<logic:notEmpty name="adminMarksCardForm"
									property="examSubCoursewiseGradeDefnTOList">

									<logic:iterate id="gradeDefEntry" name="adminMarksCardForm"
										property="examSubCoursewiseGradeDefnTOList" indexId="count33">
										<tr>
											<td class="row-print"><bean:write
												property="startPrcntgGrade" name="gradeDefEntry" /> to <bean:write
												property="endPrcntgGrade" name="gradeDefEntry" /></td>
											<td class="row-print"><bean:write property="grade"
												name="gradeDefEntry" /></td>
											<td class="row-print"><bean:write property="gradePoint"
												name="gradeDefEntry" /></td>

										</tr>
									</logic:iterate>
								</logic:notEmpty>



							</table>
							</td>
							<td style="width: 30%; float: left">
							<table width="200px" height="200px" align="left" border="1"
								rules="all">
								<tr>
									<td colspan="1" class="row-print">SGPA/CGPA</td>
									<td colspan="1" class="row-print">GRADE</td>
								</tr>

								<logic:notEmpty name="adminMarksCardForm"
									property="courseWiseGradeList">
									<logic:iterate id="gradePointDefEntry" name="mto"
										property="courseWiseGradeList" indexId="count21">
										<tr>
											<td colspan="1" class="row-print"><bean:write
												property="startPercentage" name="gradePointDefEntry" /> to
											<bean:write property="endPercentage"
												name="gradePointDefEntry" /></td>
												
											<td colspan="1" class="row-print"><bean:write
												property="grade" name="gradePointDefEntry" /> <logic:notEmpty property="interpretation" name="gradePointDefEntry"> - </logic:notEmpty><bean:write
												property="interpretation" name="gradePointDefEntry" /></td>

										</tr>
									</logic:iterate>
								</logic:notEmpty>

							</table>
							</td>
						</tr>
							
							</table>
								</logic:iterate>
								</logic:notEmpty>
					</logic:equal>
				<logic:equal value="true" name="adminMarksCardForm" property="pg">
				<logic:notEmpty name="adminMarksCardForm" property="studentList">
					<logic:iterate id="mto" name="adminMarksCardForm" property="studentList" indexId="count">
					 <c:if test="${count>0}">
		<p style="page-break-after:always;"> </p>
					<p style="page-break-after:always;"> </p>
		</c:if>
						<table width="100%" cellspacing="1" cellpadding="2" class="row-white1">
						<bean:define id="reg" name="mto" property="regNo"></bean:define>
							<tr>
								<td width="80%" align="center">
								<img src='<%=request.getContextPath()
										+ "/LogoServlet?count=1"%>'  height="100" width="210" />
								</td>
								<td width="20%" align="right">
								<img src="<%=request.getContextPath()%>/MarksCardPhotoServlet?studentPhoto=<%=reg%>"  height="128" width="133" />
								</td>
							</tr>
						</tr>
								<tr height="21px">
								<td colspan="2" align="center" width="100%" fontsize="30" font-family= verdana><b><bean:write name="mto" property="semString"/> SEMESTER  <bean:write name="mto" property="examName"/></b> </td>
								
							</tr>
							
							<tr height="25px">
								<td  align="center" width="80%" class="row-print"> <b>STATEMENT OF MARKS</b> </td>
							
							</tr>
							<tr>

							<td colspan="2">
							<table width="100%">
								<tr>
									<td width="17%" class="row-print">Programme</td>
									<td class="row-print">:</td>
									<td class="row-print"><bean:write name="mto"
										property="courseName" /></td>
								</tr>
				
								<tr>
									<tr>
										<td width="17%" class="row-print">Name Of Candidate</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="mto"
											property="studentName" /></td>
									</tr>
									<tr>
										<td width="17%" class="row-print">Register No</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="mto"
											property="regNo" /></td>
									</tr>
							</table>
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
									<logic:notEmpty name="mto" property="subMap">
										<logic:iterate id="map" name="mto" property="subMap" indexId="count4">
											<!--<tr height="21px">
												<td></td>
												<td colspan="11" class="row-print"> <bean:write name="map" property="key"/> </td>
											</tr>
											--><logic:notEmpty name="map" property="value">
											<logic:iterate id="to" name="map" property="value">
											<c:set var="slnocount" value="${slnocount + 1 }" />
											<c:if test="${to.theory!=null && to.theory==true && to.displaySubject==true && to.appearedTheory==true}">
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
											
										
										
										<logic:notEmpty name="mto" property="nonElectivesubMap">
										<c:set var="slnocount" value="${slnocount + 1 }" />
										<logic:iterate id="map" name="mto" property="nonElectivesubMap" indexId="count5">
										
											<!--<tr height="21px">
												<td class="row-print" align="center"><c:out value="${slnocount}" /></td>
												<td colspan="11" class="row-print"><bean:write name="map" property="key"/>/s </td>
											</tr>
											--><logic:notEmpty name="map" property="value">
											<logic:iterate id="to" name="map" property="value">
											
											<c:if test="${to.theory!=null && to.theory==true && to.displaySubject==true && to.appearedTheory==true}" >
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
										
										
										
										
										<logic:notEmpty name="mto" property="addOnCoursesubMap">
										<c:set var="slnocount" value="${slnocount + 1 }" />
										<logic:iterate id="map" name="mto" property="addOnCoursesubMap" indexId="count6">
										
											<!--<tr height="21px">
												<td class="row-print" align="center"><c:out value="${slnocount}" /></td>
												<td colspan="11" class="row-print"> <bean:write name="map" property="key"/>/s </td>
											</tr>
											--><logic:notEmpty name="map" property="value">
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
											<!--<td colspan="10" align="center"  class="row-print">
												Total Marks (In Words): <bean:write name="mto" property="totalMarksInWord"/>
											</td>
											-->
											<td colspan="2" align="center">SEMESTER RESULT</td>
											
											<td  align="center"  class="row-print"><bean:write name="mto" property="totalCredits"/></td>
											
											<td colspan="4" align="center">
											<c:choose>
										<c:when test="${mto.sgpa!=null }">
										<c:choose>
											
											
											<c:when test="${mto.sgpa!=' ' }">
												SGPA:<bean:write name="mto" property="sgpa"/></c:when>
												
											</c:choose></c:when></c:choose></td>
										
											
											<td align="center" class="row-print">
											<bean:write name="mto" property="totalMarksAwarded"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="mto" property="totalMaxmarks"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="mto" property="totalGrade"/>
											</td>
											<td></td>
											<td align="center" class="row-print" >
											<bean:write name="mto" property="totalGradePoints"/>
											</td>
											<td align="center" class="row-print" >
											<bean:write name="mto" property="resultClass"/>
											</td>
											<!--<td colspan="2"></td>
										--></tr>
										<!--<tr height="21px">
											<td colspan="5" align="center"  class="row-print">
											 Result:<bean:write name="mto" property="result"/>
											</td>
											<td colspan="3"  class="row-print">
											 Total Credits Awarded:<bean:write name="mto" property="totalCredits"/>
											</td>
											<td colspan="4" align="center" class="row-print">
												Grade Points Average :<bean:write name="mto" property="gradePoints"/>
											</td>
										</tr>
										
										
									--></table>
								</td>
							</tr>
							
							
							<tr height="21px">
							<td colspan="2"  class="row-print-desc">
							<logic:notEmpty name="adminMarksCardForm" property="description">
							<font size="6px">
								<c:out value="${adminMarksCardForm.description}" escapeXml="false"></c:out>
							</font>
							</logic:notEmpty>
							</td>
							</tr>
					
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
							<td rowspan="2" align="right">
								<img src="images/COEFinal.jpg" width="157px" height="72px" />
							</td>							
							</tr>
							</table>
							</td>
							
							</tr>
							
							<tr>
							<td style="width: 60%; float: left" class="row-print">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SUBJECT
							WISE GRADE DEFINITION</td>

							<td style="width: 30%; float: left" class="row-print">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SCPA
							GRADE DEFINITION</td>
						</tr>
						<tr>
							<td style="width: 60%; float: left">
							<table width="200px" height="200px" align="center" border="1"
								rules="all">
								<tr>
									<td colspan="1" class="row-print">PERCENTAGE OF MARK</td>
									<td colspan="1" class="row-print">GRADE</td>
									<td colspan="1" class="row-print">GP</td>
								</tr>

								<logic:notEmpty name="adminMarksCardForm"
									property="examSubCoursewiseGradeDefnTOList">

									<logic:iterate id="gradeDefEntry" name="adminMarksCardForm"
										property="examSubCoursewiseGradeDefnTOList"
										indexId="count33643">
										<tr>
											<td class="row-print"><bean:write
												property="startPrcntgGrade" name="gradeDefEntry" /> to <bean:write
												property="endPrcntgGrade" name="gradeDefEntry" /></td>
											<td class="row-print"><bean:write property="grade"
												name="gradeDefEntry" /></td>
											<td class="row-print"><bean:write property="gradePoint"
												name="gradeDefEntry" /></td>

										</tr>
									</logic:iterate>
								</logic:notEmpty>



							</table>
							</td>
							<td style="width: 30%; float: left">
							<table width="200px" height="200px" align="left" border="1"
								rules="all">
								<tr>
									<td colspan="1" class="row-print">SGPA/CGPA</td>
									<td colspan="1" class="row-print">GRADE</td>
								</tr>

								<logic:notEmpty name="adminMarksCardForm"
									property="courseWiseGradeList">
									<logic:iterate id="gradePointDefEntry" name="mto"
										property="courseWiseGradeList" indexId="count2561">
										<tr>
											<td colspan="1" class="row-print"><bean:write
												property="startPercentage" name="gradePointDefEntry" /> to
											<bean:write property="endPercentage"
												name="gradePointDefEntry" /></td>
											<td colspan="1" class="row-print"><bean:write
												property="grade" name="gradePointDefEntry" /><logic:notEmpty
												property="interpretation" name="gradePointDefEntry"> - </logic:notEmpty><bean:write
												property="interpretation" name="gradePointDefEntry" /></td>

										</tr>
									</logic:iterate>
								</logic:notEmpty>

							</table>
							</td></tr>
							
							
							</table>
					</logic:iterate>
				</logic:notEmpty>
				</logic:equal>
				</c:if>
				
</html:form>
