<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function submitMarksCard(){
	document.getElementById("method").value="submitRevaluation";
	document.loginform.submit();
}
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=certificateMarksCardNew";
}
function resetErrorMsgs() {	
	resetFieldAndErrMsgs();
}
function printMarksCard(){
	var url = "StudentLoginAction.do?method=printCertificateMarksCardNew";
	var browserName=navigator.appName; 
		 myRef = window.open(url,"MarksCard","left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}

function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=certificateMarksCardNew";
}
//function submitGrievanceForm(){
//	document.getElementById("method").value="submitGrievanceForm";
//	document.loginform.submit();
//}
</script>
<html:form action="/StudentLoginAction" >
	
	
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="method" styleId="method" value="getResults" />	


	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.studentlogin" />
			<span class="Bredcrumbs">&gt;&gt; Consolidated Marks Card &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Consolidated Marks Card</strong></div>
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
							</table></td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						

						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
							
							<%-- by vibin --%>
							
							<table width="100%" height="100%" cellspacing="1" cellpadding="2"  class="row-white1" >
							
							<tr>
								<td width="80%" align="center">
								<img src='<%=request.getContextPath()+"/LogoServlet?count=1"%>'  height="150" width="500" />
								</td>
								</tr>
							
							
							<tr height="25px">
								<td  align="center" width="80%" class="row-print"><b>PROGRAMME PART RESULTS</b></td>
								
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
												<td   class="row-print" align="center"> <bean:write name="tolist" property="creditPoint"/> </td>
												<td   class="row-print" align="center"> <bean:write name="tolist" property="totalCredits"/> </td>
												<td   class="row-print" align="center"> <bean:write name="tolist" property="sgpa"/> </td>
												<td   class="row-print" align="center"> <bean:write name="tolist" property="grade"/> </td>
												
											</tr>
											
										</logic:iterate>	
										</tr>
									<%--	<tr height="21px">
											
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
											
											<td align="center" class="row-print" >
											<bean:write name="loginform" property="finalGrade"/>
											</td>
											
											
										</tr> --%>
										</table>
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
					
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right">
							
							<html:button property="" styleClass="btnbg" onclick="printMarksCard()">
								Print
							</html:button></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""  styleClass="btnbg" value="Close" onclick="goToHomePage()"></html:button> 
							</td>
						</tr>
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
<script type="text/javascript">
if(document.getElementById("info")!=null){	
	$("#info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400).fadeOut(400).fadeIn(400);
}
</script> 