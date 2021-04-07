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
<script language="JavaScript" >

</script>
<html:form action="/disciplinaryDetails.do" >
	<html:hidden property="method" styleId="method" value="printMarksCard" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="DisciplinaryDetailsForm" />

	
	<table width="98%" border="0">
		
		
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						
						
							<tr height="25px">
								<td  align="center" width="80%" class="row-print"> <b> STATEMENT OF MARKS</b> </td>
								
							</tr>
							<tr>
								<td colspan="2">
									<table width="100%">
									<tr height="25px">
										<td class="row-print">Degree</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="DisciplinaryDetailsForm" property="marksCardTo.courseName"/></td>
										<td class="row-print"> Date</td>
										<td class="row-print">:</td>
										<td><bean:write name="DisciplinaryDetailsForm" property="marksCardTo.date"/></td>
									</tr>
									<tr height="25px">
										<td class="row-print"><bean:write name="DisciplinaryDetailsForm" property="marksCardTo.semType"/></td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="DisciplinaryDetailsForm" property="marksCardTo.semNo"/></td>
										<td class="row-print"> Month & Year of Examination</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="DisciplinaryDetailsForm" property="marksCardTo.monthYear"/></td>
									</tr>
									<tr height="25px">
										<td class="row-print">Name Of Candidate</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="DisciplinaryDetailsForm" property="marksCardTo.studentName"/></td>
										<td class="row-print"> Register No</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="DisciplinaryDetailsForm" property="marksCardTo.regNo"/></td>
									</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table width="100%" style="border: 1px solid black; " rules="all">
									<tr height="25px">

						                        <td rowspan="2" class="row-print" align="center" width="3%">
						                                    SI NO
						                        </td>
						                        <td  class="row-print" align="center" width="30%">
						                                    SUBJECT
						                        </td>
						                        <td class="row-print" align="center" width="10%">
						                                    TYPE
						                        </td>
						                        <td  align="center" class="row-print" width="12%">
						                                    CIA
						                        </td>
						                        <td align="center" class="row-print" width="20%">
						                                    ESE
						                        </td>
						                        <td align="center" class="row-print" width="15%">
						                                    TOTAL
						                        </td>
						                        <td class="row-print" align="center" width="5%">
						                                    CREDITS
						                        </td>
						                        <td class="row-print" align="center" width="5%">
						                                    GRADE
						                        </td>
						            </tr>
						           <c:set var="slnocount" value="0" />
										<logic:notEmpty name="DisciplinaryDetailsForm" property="marksCardTo.subMap">
										<logic:iterate id="map" name="DisciplinaryDetailsForm" property="marksCardTo.subMap">
											<tr height="25px">
												<td></td>
												<td colspan="11" class="row-print"> <bean:write name="map" property="key"/> </td>
											</tr >
											<logic:notEmpty name="map" property="value">
											<logic:iterate id="to" name="map" property="value">
											<c:set var="slnocount" value="${slnocount + 1 }" />
											<tr height="25px">
												<td class="row-print" align="center"><c:out value="${slnocount}" /></td>
												<td class="row-print" align="center"> <bean:write name="to" property="name"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="type"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="ciaMarksAwarded"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="eseMarksAwarded"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="totalMarksAwarded"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="credits"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="grade"/> </td>
											</tr>
											</logic:iterate>
											</logic:notEmpty>
										</logic:iterate>
										</logic:notEmpty>
										<c:set var="slnocount" value="0" />
										<logic:notEmpty name="DisciplinaryDetailsForm" property="marksCardTo.addOnCoursesubMap">
										<logic:iterate id="map" name="DisciplinaryDetailsForm" property="marksCardTo.addOnCoursesubMap">
											<tr height="25px">
												<td></td>
												<td colspan="11" class="row-print"> <bean:write name="map" property="key"/> </td>
											</tr>
											<logic:notEmpty name="map" property="value">
											<logic:iterate id="to" name="map" property="value">
											<c:set var="slnocount" value="${slnocount + 1 }" />
											<tr height="25px">
												<td class="row-print" align="center"><c:out value="${slnocount}" /></td>
												<td class="row-print" align="center"> <bean:write name="to" property="name"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="type"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="ciaMarksAwarded"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="eseMarksAwarded"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="totalMarksAwarded"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="credits"/> </td>
												<td class="row-print" align="center"> <bean:write name="to" property="grade"/> </td>
											</tr>
											</logic:iterate>
											</logic:notEmpty>
										</logic:iterate>
										</logic:notEmpty>
										<tr height="25px" class="row-print">
											<td colspan="5" align="center" class="row-print">
												Total Marks (In Words): <bean:write name="DisciplinaryDetailsForm" property="marksCardTo.totalMarksInWord"/>
											</td>
											<td class="row-print" align="center" >
											<bean:write name="DisciplinaryDetailsForm" property="marksCardTo.totalMarksAwarded"/>
											</td>
											<td colspan="2"></td>
										</tr>
										<tr height="25px">
											<td colspan="2" align="center" class="row-print">
											 Result:<bean:write name="DisciplinaryDetailsForm" property="marksCardTo.result"/>
											</td>
											<td colspan="3" align="center" class="row-print">
											 Total Credits Awarded:<bean:write name="DisciplinaryDetailsForm" property="marksCardTo.totalCredits"/>
											</td>
											<td colspan="2" class="row-print">
												Grade Points Average :<bean:write name="DisciplinaryDetailsForm" property="marksCardTo.gradePoints"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							
							<tr>
							<td colspan="2" align="right">
								<img src="images/COEFinal.jpg" width="157px" height="72px" />
							</td>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""  styleClass="formbutton" value="Close" ></html:button> 
							</td>
						</tr>
					</table>
					</div>
					
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			
			
</html:form>
