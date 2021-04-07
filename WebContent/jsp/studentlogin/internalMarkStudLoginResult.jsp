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
	document.location.href = "StudentLoginAction.do?method=initInternalMarks";
}
</script>
<html:form action="/StudentLoginAction" >
	<html:hidden property="method" styleId="method" value="" />	
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
									<td colspan="6" align="left">
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

						<logic:notEmpty  name="loginform" property="marksCardTo">
						
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
							
							<table width="100%" height="100%" cellspacing="1" cellpadding="2"  class="row-white1" >
							
						<tr>
								<td>
									<table width="50%">
									<tr>
										<td class="row-print">Name</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="marksCardTo.studentName"/></td>
                                        <td class="row-print"> Register No</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="marksCardTo.regNo"/></td>
									</tr>
									<tr><td>&nbsp;</td></tr>
                                    <tr>
                                        <td class="row-print">Subject</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="marksCardTo.subjectName"/></td>
									</tr>
									<tr><td>&nbsp;</td></tr>
									<tr><td>&nbsp;</td></tr>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table class="row-print" width="100%" style="border: 1px solid black; " rules="all">
										<tr height="21px">

						                        <td class="row-print" align="center">
						                                  &nbsp;
						                        </td>
						                        <td class="row-print" align="center">
						                                    Entry Mark/Percentage
						                        </td>						                      
						                        <td align="center" class="row-print">
						                                    Max mark /max percentage
						                        </td>
						                        <td align="center" class="row-print">
						                                    Converted Mark
						                        </td>
						                        <td align="center" class="row-print">
						                                    Max mark
						                        </td>						                       
						            </tr>						           
									<logic:notEmpty name="loginform" property="marksCardTo.mainList">
										<logic:iterate id="to" name="loginform" property="marksCardTo.mainList" indexId="count1">
											<logic:notEqual value="Attendance" name="to" property="examName">
											<tr height="21px">
												<td align="left"><bean:write name="to" property="examName"/></td>
												<td align="center"><bean:write name="to" property="ciaMarksAwarded"/></td>
												<td align="center" class="row-print"><bean:write name="to" property="ciaMaxMarks"/></td>
												<td align="center" class="row-print"><bean:write name="to" property="convertedMark"/></td>
												<td align="center" class="row-print"><bean:write name="to" property="convertedMarkMax"/></td>
											</tr>
											</logic:notEqual>
											</logic:iterate>
											</logic:notEmpty>
											<logic:notEmpty name="loginform" property="marksCardTo.attendancePercentage">
											<tr height="21px">
												<td align="left">Attendance</td>
												<td align="center">
												<bean:write name="loginform" property="marksCardTo.attendancePercentage"/>%(approx.)
												</td>
												<td align="center" class="row-print">100%</td>
												<td align="center" class="row-print"><bean:write name="loginform" property="marksCardTo.attendancePercentageCon"/></td>
												<td align="center" class="row-print"><bean:write name="loginform" property="marksCardTo.attendancePercentageConMax"/></td>
											
											</tr></logic:notEmpty>
											<tr height="30px">
											<td align="center">Total</td>
											<td align="center"></td>
											<td align="center" class="row-print"></td>
											<td align="center" class="row-print"><bean:write name="loginform" property="marksCardTo.totalMarksAwarded"/></td>
											<td align="center" class="row-print"><bean:write name="loginform" property="marksCardTo.totalMaxmarks"/></td>
											
											</tr>											
										</table>
								</td>
							</tr>
							<tr><td>&nbsp;</td></tr>
							<tr>
											<td align="center"><html:button property=""  styleClass="btnbg" value="Close" onclick="goToHomePage()"></html:button></td>
											</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						</logic:notEmpty>
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
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