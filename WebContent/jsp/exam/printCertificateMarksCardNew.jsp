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
	
	<html:hidden property="formName" value="loginform" />

	
							<table width="100%" height="100%" cellspacing="1" cellpadding="2"  class="row-white1" >
							
							<tr>
								<td width="80%" align="center">
								<img src='<%=request.getContextPath()+"/LogoServlet?count=1"%>'  height="150" width="500" />
								</td>
								</tr>
							
							
							<tr height="25px">
								<td  align="center" width="80%" class="row-print"><b>SEMESTER RESULTS</b></td>
								
							</tr>
							<tr>
								<td>
									<table width="50%">
									
									
									<tr>
										<td class="row-print">Name Of Candidate</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="studentName"/></td>
									</tr>
<br/>
                                     <tr>
                                                                                <td class="row-print"> Register No</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="regNo"/></td>
									</tr>
									
									</table>
								</td>
							</tr>
							
							
<tr height="30px"></tr>

							<tr>
								<td colspan="2">
									<table class="row-print" width="100%" style="border: 1px solid black; " rules="all">
										<tr height="21px">
										<td rowspan="2" class="row-print" align="center" width="25%">
						                                   Program Part
						                        </td>

						                        <td rowspan="2" class="row-print" align="center" width="10%">
						                                   Marks Awarded
						                        </td>
						                        <td rowspan="2" class="row-print" align="center" width="10%">
						                                    Maximum Marks
						                        </td>
						                          <td rowspan="2" align="center" class="row-print" width="10%">
						                                   Credit Points
						                        </td><td rowspan="2"  align="center" class="row-print" width="10%">
						                                Credits   
						                        </td>
						                        
						                        
						                        <td rowspan="2" align="center" class="row-print" width="10%">
						                                 CGPA
						                        </td><td rowspan="2"  align="center" class="row-print" width="10%">
						                                   Grade
						                        </td>
						                        
						            </tr>
						            <tr>
						            <c:set var="slnocount" value="0" />
						            <logic:iterate id="tolist" property="consolidateMarksCardTOList" name="loginform" indexId="count">
						            
											<c:set var="slnocount" value="${slnocount + 1 }" />
											<tr height="21px">
												
												<td   class="row-print" > <bean:write name="tolist" property="courseName"/> </td>
												<td   class="row-print" align="center"> <bean:write name="tolist" property="totalMarksAwarded"/> </td>
												<td   class="row-print" align="center"> <bean:write name="tolist" property="totalMaxMarks"/> </td>
												<td   class="row-print" align="center"> <bean:write name="tolist" property="totalCredits"/> </td>
												<td   class="row-print" align="center"> <bean:write name="tolist" property="totalCredits"/> </td>
												<td   class="row-print" align="center"> <bean:write name="tolist" property="sgpa"/> </td>
												<td   class="row-print" align="center"> <bean:write name="tolist" property="grade"/> </td>
												
											</tr>
											
										</logic:iterate>	
										</tr>
										<tr height="21px">
											
											<td  align="center"> RESULT</td>
											
											<td align="center" class="row-print">
											<bean:write name="loginform" property="totalMarkAwarded"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="loginform" property="totalMaxMark"/>
											</td>
											<td></td>
											<td align="center" class="row-print" >
											<bean:write name="loginform" property="finalCredit"/>
											</td>
											<td align="center" class="row-print" >
											<bean:write name="loginform" property="finalCCPA"/>
											</td>
											
											<td align="center" class="row-print" colspan="2">
											<bean:write name="loginform" property="finalGrade"/>
											</td>
											
											
										</tr>
										</table>
								</td>
							</tr>
							</table>
		
	
</html:form>
<script type="text/javascript">window.print();</script>