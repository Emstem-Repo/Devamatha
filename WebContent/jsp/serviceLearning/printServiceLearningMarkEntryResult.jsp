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
						<%-- 		<td width="20%" align="right">
								<img src='images/StudentPhotos/<c:out value="${loginform.studentId}"/>.jpg?<%=System.currentTimeMillis() %>'  height="120" width="128" />
								</td>--%>
							</tr>
							<tr height="70%"></tr>
							<tr>
								<td width="80%" align="center">
								<b>SERVICE LEARNING MARKS ENTRY</b>
								</td>
								
							</tr>
							</table></td></tr>
							<tr><td><table width="100%">
							<tr>
								<td>
									<table width="50%">
									<tr>
									<td class="row-print"> Programme Name</td>
										<td class="row-print"> :</td>
										<td class="row-print"><bean:write
										property="programName" name="serviceLearningMarksEntryForm"/></td>
									</tr>
<br/>
									<tr>
										<td class="row-print">Date</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="serviceLearningMarksEntryForm" property="startDate"/></td>
									</tr>
									<br/>
									<tr>
										<td class="row-print">Organized By</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="serviceLearningMarksEntryForm" property="departmentName"/></td>
									</tr>
									<br/>
									<tr>
										<td class="row-print">Incharge</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="serviceLearningMarksEntryForm" property="inchargeName"/></td>
									</tr>

							</table></td></tr>
							</table></td></tr>
							
							<tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td valign="top"><table style="border: 1px solid black; " rules="all">
                <tr height="25">

									<td class="row-odd" height="25">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd" height="25">Name</td>
									<td  class="row-odd" height="25">Register No</td>
									<td class="row-odd" height="25">Class</td>
									<td class="row-odd" height="25">Hours</td>
									<td class="row-odd" height="25">Mark</td>
									<td class="row-odd" height="25">Remark</td>
									
								</tr>
								
								
								<logic:iterate id="resultlist" property="serviceLearningActivityToList" name="serviceLearningMarksEntryForm" indexId="count">
								
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
							<td width="13%" height="39" >&nbsp;<bean:write name="resultlist" property="studentName" /></td>
                            <td width="19%" >&nbsp;<bean:write property="registerNo" name="resultlist"/></td>
                            <td width="20%" >&nbsp;<bean:write property="classname" name="resultlist"/></td>
                            <td width="8%">&nbsp;<bean:write property="attendedHours"  name="resultlist" /></td>
                             
                            <td width="8%">&nbsp; <bean:write property="mark"  name="resultlist" /></td>
							 <td width="8%">&nbsp;<bean:write property="remark"  name="resultlist" /></td>
								
											</tr>
											
											
							</logic:iterate>
							
                          
                      </table></td>
                      <td width="5" height="30"  background="images/right.gif"></td>
                    </tr>
				
                   <br></br>
	
					
					
				
			
</html:form>
</body>
<script type="text/javascript">window.print();</script>