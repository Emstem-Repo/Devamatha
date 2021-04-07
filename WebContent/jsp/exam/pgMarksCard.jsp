<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
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
<html:form action="/StudentLoginAction" >
	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="marksCardType" name="loginform" value="regPg"/>
<logic:equal value="true" property="isGrievance" name="loginform">
	<html:hidden property="method" styleId="method" value="submitGrievanceForm" />	
</logic:equal>
<logic:equal value="false" property="isGrievance" name="loginform">
<html:hidden property="method" styleId="method" value="printMarksCard" />	
</logic:equal>
	
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
							<td height="19" valign="top" background="images/st_left.gif"></td>
							<td valign="top" class="news"><logic:equal value="true"
								name="loginform" property="checkRevaluation">
								<div id="info">Revaluation /Re-totaling facility is available Online till:<bean:write name="loginform" property="revDate"/></div>
							</logic:equal></td>
							<td valign="top" background="images/st_right.gif"></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
									<%-- by vibin --%>
							
							<table width="100%" height="80%" cellspacing="1" cellpadding="2"  class="row-white1" >
								<bean:define id="reg" name="loginform" property="marksCardTo.regNo"></bean:define>
							<tr>
								<td width="80%" align="center">
								<img src='<%=request.getContextPath()+"/LogoServlet?count=1"%>'  height="150" width="500" />
								</td>
								<td width="20%" align="right">
								<img src='images/StudentPhotos/<c:out value="${loginform.studentId}"/>.jpg?<%=System.currentTimeMillis() %>'  height="120" width="128" />
								</td>
							</tr>
<tr height="30px"></tr>
							<tr height="21px" width="100%">
								<td class="row-print" align="center" width="100%" fontsize="30" font-family= verdana><b><bean:write name="loginform" property="marksCardTo.semString"/> SEMESTER <bean:write name="loginform" property="marksCardTo.ugorpg"/> EXAMINATION <bean:write name="loginform" property="marksCardTo.monthYear"/></b> </td>
								
							</tr>
							<logic:equal value="true" property="isGrievance" name="loginform">
							<tr height="25px">
								<td  align="center" width="80%" class="row-print"><b>GRIEVANCE FORM</b></td>
								
							</tr>
							</logic:equal>
							<logic:equal value="false" property="isGrievance" name="loginform">
							<tr height="25px">
								<td  align="center" width="80%" class="row-print"><b>STATEMENT OF MARKS</b></td>
								
							</tr>
							</logic:equal>
<tr height="30px"></tr>
							
							<tr>
								<td>
									<table width="50%">
									
									<td class="row-print"> Programme</td>
										<td class="row-print"> :</td>
										<td class="row-print"><bean:write name="loginform" property="marksCardTo.courseName"/></td>
									</tr>
<br/>
									<tr>
										<td class="row-print">Name Of Candidate</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="marksCardTo.studentName"/></td>
									</tr>
<br/>
                                                                        <tr>
                                                                                <td class="row-print"> Register No</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="marksCardTo.regNo"/></td>
									</tr>
									<logic:equal value="true" property="isGrievance" name="loginform">
									 <tr>
                                                                                <td class="row-print"> Semester</td>
										<td class="row-print">:</td>
										<td class="row-print"><bean:write name="loginform" property="marksCardTo.semNo"/></td>
									</tr>
									</logic:equal>
									</table>
								</td>
							</tr>
							<logic:equal value="false" property="isGrievance" name="loginform">
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
						                                    SUB AVG
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
									<logic:notEmpty name="loginform" property="marksCardTo.subMap">
										<logic:iterate id="map" name="loginform" property="marksCardTo.subMap" indexId="count1">
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
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="avgmark">-</logic:equal> <logic:notEqual value="00" name="to" property="avgmark"><bean:write name="to" property="avgmark"/></logic:notEqual> <logic:empty name="to" property="avgmark">- </logic:empty></td>
												
												<td  class="row-print" align="center"> <bean:write name="to" property="grade"/> <logic:empty name="to" property="grade">- </logic:empty></td>
												<td  class="row-print" align="center"> <bean:write name="to" property="theoryGradePnt"/> <logic:empty name="to" property="theoryGradePnt">-</logic:empty></td>
												<td  class="row-print" align="center"> <bean:write name="to" property="creditPoint"/> <logic:empty name="to" property="creditPoint">- </logic:empty></td>
											<td  class="row-print" align="center"> <bean:write name="to" property="resultClass"/> <logic:empty name="to" property="resultClass">- </logic:empty></td>
											
											</tr>
											</c:if>
											<c:if test="${to.practical!=null && to.practical==true }">
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
												<td align="center" class="row-print"><logic:equal value="00" name="to" property="avgmark">-</logic:equal> <logic:notEqual value="00" name="to" property="avgmark"><bean:write name="to" property="avgmark"/></logic:notEqual> <logic:empty name="to" property="avgmark">- </logic:empty></td>
												
												
												<td  class="row-print" align="center"> <bean:write name="to" property="practicalGrade"/><logic:empty name="to" property="practicalGrade">- </logic:empty> </td>
												<td  class="row-print" align="center"> <bean:write name="to" property="practicalGradePnt"/><logic:empty name="to" property="practicalGradePnt">- </logic:empty></td>
													<td  class="row-print" align="center"> <bean:write name="to" property="creditPoint"/> <logic:empty name="to" property="creditPoint">- </logic:empty></td>
													<td  class="row-print" align="center"> <bean:write name="to" property="resultClass"/> <logic:empty name="to" property="resultClass">- </logic:empty></td>
											
											</tr>
											</c:if>
											</logic:iterate>
										</logic:notEmpty>
										</logic:iterate>
										</logic:notEmpty>
										<tr height="21px">
										
											<td colspan="2" align="center">SEMESTER RESULT</td>
											
											<td  align="center"  class="row-print"><bean:write name="loginform" property="marksCardTo.totalCredits"/></td>
											
											<td colspan="7" align="center">
											<c:choose>
										<c:when test="${loginform.marksCardTo.sgpa!=null }">
										<c:choose>
											
											
											<c:when test="${loginform.marksCardTo.sgpa!=' ' }">
												SCPA:<bean:write name="loginform" property="marksCardTo.sgpa"/></c:when>
												
											</c:choose></c:when></c:choose></td>
										
									
											<td align="center" class="row-print">
											<bean:write name="loginform" property="marksCardTo.totalGrade"/>
											</td>
											<td></td>
											<td align="center" class="row-print">
											<bean:write name="loginform" property="marksCardTo.totalGradePoints"/>
											</td>
											<td align="center" class="row-print">
											<bean:write name="loginform" property="marksCardTo.resultClass"/>
											</td>
											<!--<td colspan="2"></td>
										--></tr>
										</table>
								</td>
							</tr>
							</logic:equal>
							
							<logic:equal value="true" property="isGrievance" name="loginform">
							<tr>
								<td colspan="2">
									<table class="row-print" width="100%" style="border: 1px solid black; " rules="all">
										<tr height="21px">
										<td rowspan="2" class="row-print" align="center" width="3%">
						                                   Sl.No
						                        </td>

						                        <td rowspan="2" class="row-print" align="center" width="3%">
						                                   Course Code
						                        </td>
						                        <td rowspan="2" class="row-print" align="center" width="25%">
						                                    Course
						                        </td>
						                        
						                        <td rowspan="2" align="center" class="row-print" width="15%">
						                                   ESE Mark
						                        </td><td rowspan="2"  align="center" class="row-print" width="17%">
						                                    CIA Mark
						                        </td>
						                        <td rowspan="2"  align="center" class="row-print" width="10%">
						                                     TOTAL
						                        </td>
						                        
						                        <td rowspan="2" align="center" class="row-print" width="6%">
						                                   Grievance
						                        </td>
						            </tr>
						            <tr>
						            <c:set var="slnocount" value="0" />
						            <nested:iterate id="to" property="marksCardTo.subjectTOList" name="loginform"
											 indexId="count">
											<tr>
											<c:set var="slnocount" value="${slnocount + 1 }" />
											<c:if test="${to.theory!=null && to.theory==true && to.displaySubject==true}">
											<tr height="21px">
												<td align="center" class="row-print"> <c:out value="${slnocount}" /> </td>
												<td   class="row-print"> <bean:write name="to" property="code"/> </td>
												<td   class="row-print"> <bean:write name="to" property="name"/> </td>
												
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="eseMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="eseMaxMarks"><bean:write name="to" property="eseMarksAwarded"/></logic:notEqual>  <logic:empty name="to" property="eseMarksAwarded">- </logic:empty></td>
												
												
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="ciaMaxMarks">-</logic:equal> <logic:empty name="to" property="ciaMaxMarks">- </logic:empty> <logic:notEqual value="-" name="to" property="ciaMaxMarks"><logic:notEmpty name="to" property="ciaMaxMarks"> <bean:write name="to" property="ciaMarksAwarded"/></logic:notEmpty> </logic:notEqual><logic:empty name="to" property="ciaMarksAwarded">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="totalMaxMarks">-</logic:equal> <logic:notEqual value="-" name="to" property="totalMaxMarks"><bean:write name="to" property="totalMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="totalMarksAwarded">- </logic:empty></td>
												<td   class="row-print"> <nested:text
								property="grievanceRemark"  styleClass="TextBox" style="background-color:#FCF5D8;"
								></nested:text></td>
										
												
												
											
											</tr>
											</c:if>
											<c:if test="${to.practical!=null && to.practical==true }">
											<tr height="21px">
											<td align="center" class="row-print"> <c:out value="${slnocount}" /> </td>
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
												
												
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalEseMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalEseMaxMarks"> <bean:write name="to" property="practicalEseMarksAwarded"/></logic:notEqual>  <logic:empty name="to" property="practicalEseMarksAwarded">- </logic:empty></td>
												
												
												
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalCiaMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalCiaMaxMarks"> <bean:write name="to" property="practicalCiaMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalCiaMarksAwarded">- </logic:empty> </td>
												
										
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalTotalMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalTotalMaxMarks"> <bean:write name="to" property="practicalTotalMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalTotalMarksAwarded">- </logic:empty> </td>
												<td   class="row-print"> <nested:text
								property="grievanceRemark"  styleClass="TextBox" style="background-color:#FCF5D8;"
								 ></nested:text></td>
												
												
											</tr>
											</c:if>
											</tr>
										</nested:iterate>
						           
										
										<logic:notEmpty name="loginform" property="marksCardTo.nonElectivesubMap">
										<c:set var="slnocount" value="${slnocount + 1 }" />
										<logic:iterate id="map" name="loginform" property="marksCardTo.nonElectivesubMap" indexId="count2">
										
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
										
										
										
										
										<logic:notEmpty name="loginform" property="marksCardTo.subjectAddOnTOList">
										<c:set var="slnocount" value="${slnocount + 1 }" />
										<nested:iterate id="to" property="marksCardTo.subjectTOList" name="loginform"
											 indexId="count">
											<tr>
											<c:set var="slnocount" value="${slnocount + 1 }" />
											<c:if test="${to.theory!=null && to.theory==true && to.displaySubject==true}" >
											<tr height="21px">
											
												<td   class="row-print"> <bean:write name="to" property="name"/> </td>
												<td align="center" class="row-print"> <bean:write name="to" property="type"/> </td>
												
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="ciaMaxMarks">-</logic:equal> <logic:empty name="to" property="ciaMaxMarks">- </logic:empty> <logic:notEqual value="-" name="to" property="ciaMaxMarks"><logic:notEmpty name="to" property="ciaMaxMarks"> <bean:write name="to" property="ciaMarksAwarded"/></logic:notEmpty> </logic:notEqual><logic:empty name="to" property="ciaMarksAwarded">- </logic:empty> </td>


												<td align="center" class="row-print"> <bean:write name="to" property="eseMinMarks"/> <logic:empty name="to" property="eseMinMarks">- </logic:empty></td>
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="eseMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="eseMaxMarks"><bean:write name="to" property="eseMarksAwarded"/></logic:notEqual>  <logic:empty name="to" property="eseMarksAwarded">- </logic:empty></td>
												
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="totalMaxMarks">-</logic:equal> <logic:notEqual value="-" name="to" property="totalMaxMarks"><bean:write name="to" property="totalMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="totalMarksAwarded">- </logic:empty></td>
												<td   class="row-print"> <nested:text
								property="grievanceRemark"  styleClass="TextBox"
								size="20" maxlength="500" ></nested:text></td>
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
												
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalCiaMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalCiaMaxMarks"> <bean:write name="to" property="practicalCiaMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalCiaMarksAwarded">- </logic:empty> </td>

								
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalEseMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalEseMaxMarks"> <bean:write name="to" property="practicalEseMarksAwarded"/></logic:notEqual>  <logic:empty name="to" property="practicalEseMarksAwarded">- </logic:empty></td>
									
												<td align="center" class="row-print"><logic:equal value="-" name="to" property="practicalTotalMaxMarks">-</logic:equal><logic:notEqual value="-" name="to" property="practicalTotalMaxMarks"> <bean:write name="to" property="practicalTotalMarksAwarded"/></logic:notEqual> <logic:empty name="to" property="practicalTotalMarksAwarded">- </logic:empty> </td>
												<td align="center" class="row-print"><logic:equal value="0" name="to" property="practicalCredits">-</logic:equal> <logic:notEqual value="00" name="to" property="practicalCredits"><bean:write name="to" property="practicalCredits"/></logic:notEqual> <logic:empty name="to" property="practicalCredits">- </logic:empty></td>
												<td   class="row-print"> <nested:text
								property="grievanceRemark"  styleClass="TextBox"
								size="20" maxlength="500" ></nested:text></td>
											</tr>
											</c:if>
											</tr>
										</nested:iterate>
										
										
										
										
										
								
										</logic:notEmpty>
										</tr>
										<tr>
										<td colspan="6"><div>
										SEMESTER RESULT
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										SGPA:<bean:write name="loginform" property="marksCardTo.sgpa"/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										Total:<bean:write name="loginform" property="marksCardTo.totalMarksAwarded"/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										Grade:<bean:write name="loginform" property="marksCardTo.totalGrade"/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										Result:<bean:write name="loginform" property="marksCardTo.resultClass"/>
										
										
										
										</div>
										</td>
										<td></td>
										</tr>
							
										</table>
								</td>
							</tr>
							
							</logic:equal>
					  <logic:equal value="false" property="isGrievance" name="loginform">
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
									<td style="width: 60%; float: left" class="row-print">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SUBJECT
									WISE GRADE DEFINITION</td>

									<td style="width: 30%; float: left" class="row-print">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SCPA
									GRADE DEFINITION</td>
								</tr>
								<tr>
									<td style="width: 60%; float: left">
									<table width="167px" height="85px" align="center" border="1"
										rules="all">
										<tr>
											<td colspan="1" class="row-print">PERCENTAGE OF MARK</td>
											<td colspan="1" class="row-print">GRADE</td>
										</tr>


										<logic:notEmpty name="loginform"
											property="marksCardTo.examSubCoursewiseGradeDefnTOList">
											<logic:iterate id="gradeDefEntry" name="loginform"
												property="marksCardTo.examSubCoursewiseGradeDefnTOList"
												indexId="count">
												<tr>
													<td class="row-print"><bean:write
														property="startPrcntgGrade" name="gradeDefEntry" /> to <bean:write
														property="endPrcntgGrade" name="gradeDefEntry" /></td>
													<td class="row-print"><bean:write property="grade"
														name="gradeDefEntry" /></td>

												</tr>
											</logic:iterate>
										</logic:notEmpty>


									</table>
									</td>
									<td style="width: 30%; float: left">
									<table class="row-print" width="167px" height="85px"
										align="left" border="1" rules="all">
										<tr>
											<td colspan="1" class="row-print">SGPA/CGPA</td>
											<td colspan="1" class="row-print">GRADE</td>
										</tr>

										<logic:notEmpty name="loginform"
											property="marksCardTo.courseWiseGradeList">
											<logic:iterate id="gradePointDefEntry" name="loginform"
												property="marksCardTo.courseWiseGradeList" indexId="count">
												<tr>
													<td colspan="1" class="row-print"><bean:write
														property="startPercentage" name="gradePointDefEntry" /> to
													<bean:write property="endPercentage"
														name="gradePointDefEntry" /></td>
													<td colspan="1" class="row-print"><bean:write
														property="grade" name="gradePointDefEntry" /> <logic:notEmpty
														property="interpretation" name="gradePointDefEntry"> - </logic:notEmpty>
													<bean:write property="interpretation"
														name="gradePointDefEntry" /></td>

												</tr>
											</logic:iterate>
										</logic:notEmpty>

									</table>
									</td>
								</tr>
								</logic:equal>
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
					<logic:equal value="false" property="isGrievance" name="loginform">
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right">
							<logic:equal value="true" name="loginform" property="checkRevaluation">
							<html:button property="" styleClass="btnbg" onclick="submitMarksCard()">
								Revaluation/Re-totaling
							</html:button>
							</logic:equal>
							<html:button property="" styleClass="btnbg" onclick="printPass()">
								Print
							</html:button></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""  styleClass="btnbg" value="Close" onclick="goToHomePage()"></html:button> 
							</td>
						</tr>
						
					</table>
					</div>
					</logic:equal>
					<logic:equal value="true" property="isGrievance" name="loginform">
						<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="center">
							 <html:submit property="" styleClass="btnbg" value="Submit"
										styleId="submitbutton">
									</html:submit>
							
							<%--<html:button property="" styleClass="btnbg" onclick="submitGrievanceForm()">
								Submit
							</html:button>--%></div>
							</td>
							
						</tr>
					</table>
					</div>
						</logic:equal>
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