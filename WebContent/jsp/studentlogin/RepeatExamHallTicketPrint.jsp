<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*,java.text.*" %>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>

<html:form action="/MidSemRepeatExamApplication">
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="method" styleId="method" value="SavePrintApplication"/>
	
	<table width="100%" border="0" cellspacing="4">
	 <tr>
			<td valign="top">
				<table width="100%" cellspacing="10" border="0">
				 <tr>
						<td align="Center"><img src='<%=CMSConstants.LOGO_URL%>'  height="100" width="250"/></td>
				</tr>
				</table>
			</td>
		</tr>
		
		<tr>
		<td valign="top" >
		<br></br>
		 <table width="100%">
		  <tr align="center"><td align="center" colspan="5" height="40"><b><font size="5">MID SEMESTER REPEAT EXAM HALL TICKET</font></b></td></tr>
		  <tr align="center"><td align="center" colspan="5"><font size="4">PRODUCE THIS SLIP ON THE DAY OF EXAMINATION</font></td></tr>
		 
		  <tr>
		  		<td align="left">
		  		<table width="100%" cellspacing="2">
		  		<tr>
		  			<td height="30" align="left" ><font size="3" color="#07190B">Name : <bean:write name="loginform" property="midSemStudentName"></bean:write></font></td>
		  		</tr>
		  		<tr>
					<td height="30" align="left" ><font size="3" color="#07190B"> Register No :<bean:write name="loginform" property="midSemRepeatRegNo"></bean:write></font></td>
				</tr>
				<tr>
					<td height="30" align="left"><font size="3" color="#07190B">Class : <bean:write name="loginform" property="midSemClassName"></bean:write></font></td>
		 		</tr>
		  		</table>
		  	</td>
		  	<td align="left" rowspan="2" height="140">
				<img src='<%=session.getAttribute("STUDENT_IMAGE")%>' width="120" height="115"/>
			</td>
			</tr>
			
		 </table>
		</td>
		</tr>
		 <tr>
			  <td>
					<table width="100%" align="center">
						<tr>
							<td valign="top">
							<table width="100%" border=1 cellspacing="0">
								<tr>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.admisn.subject.code" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.admisn.subject.Name" /></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><bean:message key="knowledgepro.admisn.signature.of.invigilitaor" /></div></td>
								</tr>
								<logic:notEmpty name="loginform" property="midSemRepeatList">
								<tr>
									<nested:iterate id="prev" name="loginform" property="midSemRepeatList" indexId="count">
									<%
                						String styleId="check_"+count;
										String flag="flag_"+count;
                					%>
										<c:choose>
										 <c:when test="${count%2 == 0}">
											<tr class="studentrow-even">
										    	</c:when>
													<c:otherwise>
														<tr class="studentrow-white">
														</c:otherwise>
					 					  </c:choose>
					 					  <td width="10%" height="25"><div align="center"><c:out value="${count + 1}" /></div></td>
										  <td width="20%" align="center"><nested:write name="prev" property="subjectCode" /></td>
										  <td width="40%" align="left"><nested:write name="prev" property="subject" /></td>
										  <td width="40%" align="left">.  </td>
									</nested:iterate>
									</tr>
								</logic:notEmpty>
							</table>
							</td>
						</tr>
						<tr>
							<td colspan="5" height="40"><u></u></td>
						</tr>
						<tr>
						<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="1">
								<tr>
									<td colspan="5" align="left"><b>Instructions to the Students</b></td>
								</tr>
								<tr>
									<td colspan="5" height="40"><u></u></td>
								</tr>
								<tr>
									<td colspan="5" align="left"> * Bring this Examinations cum hall ticket on all the days of your examination.</td>
								</tr>
								<tr>
									<td colspan="5" align="left"> * You will not be permitted to examination hall without this Hall Ticket.</td>
								</tr>
								<tr>
									<td colspan="5" align="left"> * Check the examination dates & timings from the time table displayed on examination notice board.
								</tr>
								<tr>
									<td colspan="5" align="left"> * Report atleast 10 mts before the commencement of the examination.</td>
								
								<tr>
									<td colspan="5" align="left"> * Any kind of malpractices will be dealt with very seriously as per the university rules.</td>
								</tr>
							</table>
						</td>
						</tr>
						<tr>
							<td height="40"></td>
						</tr>
						<tr>
							<td valign="top" height="60">
								<table width="100%" cellspacing="1" cellpadding="1">
								<tr>
									<td align="left"> Signature of the Candidate</td>
									<td align="left">
									<img src="images/COEFinal.jpg" width="157px" height="72px" />
									</td>
								</tr>
								
								
								</table>
							</td>
						</tr>
						<tr>
						<td height="40"></td>
						</tr>
						
					</table>
					</td>
				</tr>
	    </table>
    </html:form>

<script type="text/javascript">
window.print();r
</script>