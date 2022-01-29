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
	
	<table width="100%" border="0">
	 <br></br> <tr>
	 
		<td valign="top">
		 <table width="100%">
		 <tr>
				<td align="left"><img src='<%=CMSConstants.LOGO_URL%>'  height="100" width="250"/></td>
				<td align="center"><b><font size="6">APPLICATION FORM FOR APPEARING MID SEMESTER SUPPLEMENTARY EXAM UG/PG</font></b></td>
		</tr>
		</table>
		 
		 <br></br>
		 <table width="100%">
		 <tr>
			<td height="35" align="left" colspan="2"><font size="5" color="#07190B">Name : <bean:write name="loginform" property="midSemStudentName"></bean:write></font></td>
			<td height="35" align="left" colspan="2"><font size="5" color="#07190B">Register No : <bean:write name="loginform" property="midSemRepeatRegNo"></bean:write></font></td>
		</tr>
		<tr>
			<td height="35" align="left" colspan="2"><font size="5" color="#07190B">Program : <bean:write name="loginform" property="midSemRepeatProgram"></bean:write></font></td>
			<td height="35" align="left" colspan="2"><font size="5" color="#07190B">Class : <bean:write name="loginform" property="midSemClassName"></bean:write></font></td>
		 </tr>
		 </table>
		</td>
		</tr>
		  <tr>
			  <td valign="top">
			  
					<table width="100%" align="center">
						<tr>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="1" border="1">
								<tr>
									<td height="25" class="studentrow-odd"><div align="center"><font size="5" color="#07190B"><bean:message key="knowledgepro.slno" /></font></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><font size="5" color="#07190B"><bean:message key="knowledgepro.admisn.subject.code" /></font></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><font size="5" color="#07190B"><bean:message key="knowledgepro.admisn.subject.Name" /></font></div></td>
									<td height="25" class="studentrow-odd"><div align="center"><font size="5" color="#07190B"><bean:message key="knowledgepro.exam.midsem.Repeat.attendance.percentage" /></font></div></td>
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
					 					  <td width="10%" height="25"><div align="center"><font size="5" color="#07190B"><c:out value="${count + 1}" /></font></div></td>
										  <td width="20%" align="center"><font size="5" color="#07190B"><nested:write name="prev" property="subjectCode" /></font></td>
										  <td width="40%" align="left"><font size="5" color="#07190B"><nested:write name="prev" property="subject" /></font></td>
										  <td width="15%" align="center"><font size="5" color="#07190B"><nested:write name="prev" property="attenPersent" /></font></td>
									</nested:iterate>
									</tr>
								</logic:notEmpty>
							</table>
							</td>
						</tr>
						
					</table>
					</td>
				</tr>
				
				<tr><td valign="top">
				<table width="100%" cellspacing="8">
						<tr><td align="left" height="40"><font size="5" color="#07190B">Aggregate Attendance percentage:-<b><bean:write name="loginform" property="midSemAggreagatePrint"></bean:write></b></font></td></tr>
						<tr>
							<td align="left" height="35"><font size="5" color="#07190B">Reason for not attending regular mid semester examination:- <bean:write name="loginform" property="midSemRepeatReason"/></font></td>
						</tr>
						<tr>
							<td align="left" height="35"><font size="5" color="#07190B">(Please Submit the supporting documents along with the application Form)</font></td>
						</tr>
				</table>
				</td>
				</tr>
		<tr><td valign="top">
		
				<table width="100%">
						<tr>
							<td align="left" height="140"><font size="5" color="#07190B">Date:.........</font></td><td colspan="1" align="left"><font size="5" color="#07190B">Signature of Student:...........</font></td><td colspan="1" align="left"><font size="5" color="#07190B">Signature of Parent/Guardian:...........</font></td>
						</tr>
					<tr>
						<td colspan="5" align="left" height="40">------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td>
					</tr>
					<tr>
						<td colspan="5" align="Center" height="60"><font size="6" color="#07190B">LETTER OF UNDERTAKING BY THE STUDENT</font></td>
					</tr>
				</table>
				</td>
		</tr>
		
		<tr>
			  <td>
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td valign="top"><font size="5" color="#07190B">
								I, <bean:write name="loginform" property="midSemStudentName"></bean:write><logic:equal property="midSemGender" value="MALE" name="loginform"> S/o </logic:equal ><logic:equal property="midSemGender" value="FEMALE" name="loginform"> D/o </logic:equal ><bean:write name="loginform" property="midSemFatherName"></bean:write>
							</font></td>
						</tr>
						<tr>
							<td valign="top"><font size="5" color="#07190B">
								Seeking this request for the First/Second/Third<!--<bean:write name="loginform" property="midSemCountWords"></bean:write>--> time within the duration of my course and understand that i will have 
							</font></td>
						</tr>
						<tr>
							<td valign="top"><font size="5" color="#07190B">
								only <!--<bean:write name="loginform" property="midSemAttemptsLeft"></bean:write>-->.... more chances for appearing mid-sem supplementary examination(**)
							</font></td>
						</tr>
						</table>
			</td>
		</tr>
		<tr>
			  <td>
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
						<td align="left" height="140"><br><font size="5" color="#07190B">Date:............</font></td><td align="left"> <br><font size="5" color="#07190B">Signature of Student:.................................</font></td>
						</tr>
						<tr>
						<td align="left" height="140"><br><font size="5" color="#07190B">Name & Signature of Class Teacher</font></td><td><br><font size="5" color="#07190B">Name & Signature of HOD:.................................</font></td>
						</tr>
						<tr>
						<td colspan="5" align="left" height="45"><br><font size="4" color="#07190B">(**Program with 3 years duration 2 chances, 2 years duration 1 chance, 4 and above years 3 chances)</font></td>
						
						</tr>
					</table>
					</td>
				</tr>
	    </table>
    </html:form>

<script type="text/javascript">
window.print();
</script>