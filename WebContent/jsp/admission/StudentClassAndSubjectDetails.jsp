<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<html:form action="/studentClassSubjectDetails" >
	<html:hidden property="method" styleId="method" value="getStudentsClassSubjects" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="studentClassSubjectDetailsForm" />
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.studentClassSubjectDetails" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admission.studentClassSubjectDetails" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
                   <tr class="row-white">
                    		<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.template.student.regno"/>:</div></td>
                       		 <td width="20%" height="25" class="row-even" align="left"><span class="star">
                               <html:text property="regNo" styleId="regNo" name="studentClassSubjectDetailsForm" />
                             </span></td>      						
							<td width="10%" height="25" class="row-even" align="center">OR</td>
                             <td width="20%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.appno"/>:</div></td>
                             <td width="25%" height="25" class="row-even" align="left">
                             <span class="star">
                               <html:text property="applnNo" styleId="applnNo" name="studentClassSubjectDetailsForm" />
                             </span></td>	
                            
                  </tr>
              </table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton" value="Submit"></html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" onclick="resetFieldAndErrMsgs()">
								<bean:message key="knowledgepro.admin.reset" />
								</html:button> 
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
			<logic:notEqual value="false" name="studentClassSubjectDetailsForm" property="studentAvailable">	
				<tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
         
          <tr>
						<td height="45" colspan="4" class="heading">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Current Semester Details</td>
						</tr>	
          
          
          <tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
									
										<tr>
											<td width="50%" height="25" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.studentName" /></div></td> 
											<td width="50%" height="25" class="row-even" align="left">
											<bean:write name="studentClassSubjectDetailsForm" property="student.studentName" /></td>
										</tr>
										<tr>
										<td width="50%" height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.fee.bulk.fee.payment.current.class" /> </td>
										<td width="50%" height="25" align="left" class="row-even"><bean:write name="studentClassSubjectDetailsForm" property="student.className" /></td>
										</tr>
										<tr>
											<td width="50%" height="25" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.admission.studentClassSubjectDetails.currentSemSubjects" /></div></td> 
											<td width="50%" height="25">
											<table width="100%" cellspacing="1" cellpadding="2">
											<logic:notEmpty name="studentClassSubjectDetailsForm" property="student.subjectsMap">
											<nested:iterate name="studentClassSubjectDetailsForm" property="student.subjectsMap" id="subMap">
											<tr>
											<td width="50%" class="row-even" align="left">
											<bean:write name="subMap" property="key" />
										    </td>
										    
											<td width="50%"  align="left">
										    <table width="100%" height="100%" cellspacing="1" cellpadding="2">
										    <logic:notEmpty name="subMap" property="value">
										    <nested:iterate name="subMap" property="value" id="mapValue">
											<tr>
											<td height="100%" class="row-even"><bean:write name="mapValue" property="subjectName" /></td>
											</tr>
											</nested:iterate>
											</logic:notEmpty>
											</table>
										    </td>
											</tr>
											</nested:iterate>
											</logic:notEmpty>
											</table>
											
											</td>
										</tr>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
						
		<!--  previous class table display -->		
		<logic:notEqual value="false" name="studentClassSubjectDetailsForm" property="studentPreviousDetails">	
						<tr>
						<td height="45" colspan="4" class="heading">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Previous Semester Details</td>
						</tr>	
						  <tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
									
									<tr>
										<td width="50%" height="25" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.admission.studentClassSubjectDetails.previousDetails.class" /></div></td> 
										<td width="50%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.admission.studentClassSubjectDetails.previousDetails.subject" /></td>
									</tr>
								<logic:notEmpty name="studentClassSubjectDetailsForm" property="student.previousDetailsList">
								<nested:iterate name="studentClassSubjectDetailsForm" property="student.previousDetailsList" id="previousDetMap">
									<tr><td width="50%" height="25" class="row-even" align="center"><bean:write name="previousDetMap" property="className" /></td>
									<td width="50%" height="25" align="center">
										<table width="100%" cellspacing="1" cellpadding="2">
											<logic:notEmpty name="previousDetMap" property="previousSubjectGrpMap">
											<nested:iterate name="previousDetMap" property="previousSubjectGrpMap" id="preSubMap">
											<tr>
											<td width="50%" class="row-even" align="left">
											<bean:write name="preSubMap" property="key" />
										    </td>
										    
											<td width="50%"  align="left">
										    <table width="100%" height="100%" cellspacing="1" cellpadding="2">
										    <logic:notEmpty name="preSubMap" property="value">
										    <nested:iterate name="preSubMap" property="value" id="preSubList">
											<tr>
											<td height="100%" class="row-even"><bean:write name="preSubList" property="subjectName" /></td>
											</tr>
											</nested:iterate>
											</logic:notEmpty>
											</table>
										    </td>
											</tr>
											</nested:iterate>
											</logic:notEmpty>
											</table>
									</td>
									</tr>
								</nested:iterate>
								</logic:notEmpty>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
						</logic:notEqual>
          
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      </logic:notEqual>
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
