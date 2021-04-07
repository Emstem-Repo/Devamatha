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
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=initMarksCard";
}
function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}
function printPass(){
	var url = "StudentLoginAction.do?method=printMarksCard";
	myRef = window
			.open(url, "MarksCard",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");

}
function submitMarksCard(){
	document.getElementById("method").value="submitRevaluation";
	document.loginform.submit();
}
</script>
<body style="background-color:powderblue;">
<html:form action="/StudentLoginAction">
	<html:hidden property="formName" value="loginform" />

	<html:hidden property="method" styleId="method" value="" />
<tr><td><table width="100%">
							<tr>
								<td width="80%" align="center">
								<img style="background-color:powderblue;" src='<%=request.getContextPath()+"/LogoServlet?count=1"%>'  height="150" width="500" />
								</td>
								<td width="20%" align="right">
								<img src='images/StudentPhotos/<c:out value="${loginform.studentId}"/>.jpg?<%=System.currentTimeMillis() %>'  height="120" width="128" />
								</td>
							</tr>
							<tr>
								<td width="80%" align="center">
								<b>GRIEVANCE FORM</b>
								</td>
								
							</tr>
							</table></td></tr>
                
                         
				
							<tr>
								<td>
									<table width="100%">
									 
									<tr>
										<td class="row-print">Exam Name</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="examName"/></td>
									</tr>
<br/>
                                      <tr>
										<td class="row-print">Name</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="studentName"/></td>
									</tr>
<br/>   
									<tr>
									<td class="row-print"> Programme</td>
										<td class="row-print"> :</td>
										<td class="row-print"><bean:write name="loginform" property="courseName"/></td>
									</tr>
<br/>
									
                                                                        <tr>
                                                                                <td class="row-print"> Reg.No</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="regNo"/></td>
									</tr>
									
									 <tr>
                                                                                <td class="row-print"> Semester</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="schemeNo"/></td>
									</tr>
									
									</table>
								</td>
							</tr>

	<tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td valign="top"><table style="border: 1px solid black; " rules="all">
                          <tr >
                            <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
                            <td height="25" class="row-odd" align="center">Grievance No</td>
                            <td class="row-odd" align="center">course Code</td>
                            <td class="row-odd" align="center">Course Title</td>
                            <td class="row-odd" align="center">ESE Mark</td>
                            <td class="row-odd" align="center">CIA Mark</td>
                            <td class="row-odd" align="center">Total</td>
                            <td class="row-odd" align="center">Grievance</td>
                           
                          </tr>
                          <c:set var="temp" value="0" />
                          <logic:iterate id="slist" property="grievanceStudentToList" name="loginform"
											 indexId="count">
											<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25" width="10%">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											 <td width="13%" height="39" align="center">&nbsp;<bean:write name="slist"
												property="grievanceNo" /></td>
                            <td width="15%" align="center">&nbsp;<bean:write property="couresCode" name="slist"/></td>
                            <td width="15%" align="center" >&nbsp;<bean:write property="courseTitle" name="slist"/></td>
                            <td width="12%" align="center">&nbsp;<bean:write property="eseAwardedMarks"  name="slist" /></td>
                            <td width="12%"  align="center">&nbsp;<bean:write property="ciaAwardedMarks"  name="slist" /></td>
                               <td width="8%"  align="center">&nbsp;<bean:write property="toatlMark"  name="slist" /></td>
                                  <td width="12%"  align="center">&nbsp;<bean:write property="remark"  name="slist" /></td>
											</tr>
										</logic:iterate>
                          
                      </table></td>
                      <td width="5" height="30"  background="images/right.gif"></td>
                    </tr>
                   <br></br>
	
					<tr>
					<td>
					
					
							
									<table style="border: 1px solid black; " rules="all">

										<tr>
											<td width="7%" height="25" class="row-odd">
											<div align="center">Grievance No</div>
											</td>
											<td width="29%" class="row-odd">
											<div align="center">Hod(Approved/Reject/pending)</div>
											</td>
											<td width="29%" class="row-odd">
											<div align="center">Control of Examiner(Approved/Reject/pending)</div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center">Hod Remark</div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center">Controler Remark</div>
											</td>
											
										</tr>
										<logic:iterate id="grievance" property="grievanceStatusToList" name="loginform"
											 indexId="count">
											<tr>
											
											<td height="25">
											<div align="center"><bean:write name="grievance"
												property="grievanceSerialNo" /></div>
											</td>
											<td align="center"><bean:write name="grievance"
												property="hodStatus" /></td>
											<td align="center"><bean:write name="grievance"
												property="controlerStatus" /></td>	
												
											<td align="center"><bean:write name="grievance"
												property="hodRemark" /></td>
												<td align="center"><bean:write name="grievance"
												property="controlerRemark" /></td>
											
											</tr>
										</logic:iterate>
									</table>
						</td>
					</tr>
					
					<tr></tr>
					
					
				
			
</html:form>
</body>
<script type="text/javascript">window.print();</script>