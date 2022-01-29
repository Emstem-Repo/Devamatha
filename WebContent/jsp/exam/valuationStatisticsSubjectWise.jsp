<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<title>Knowledge Pro</title>
<link rel="stylesheet" href="css/calendar.css">
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<script type="text/javascript">
	
function cancelAction() {
	document.location.href = "valuationStatus.do?method=cancelForValuationStatus";
}

function viewValuationDetails(examId,subjectId,evaluatorId,termNumber,subjectName){
	var url = "ValuationStatistics.do?method=viewValuationDetails&examId="+examId+"&subjectId="+subjectId+"&evaluatorTypeId="+evaluatorId+"&termNumber="+termNumber+"&subjectName="+subjectName;
	myRef = window
			.open(url, "ViewResume",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
}

function viewVerificationDetails(examId,subjectId,evaluatorId,termNumber,subjectName){
	var url = "ValuationStatistics.do?method=viewVerificationDetails&examId="+examId+"&subjectId="+subjectId+"&evaluatorTypeId="+evaluatorId+"&termNumber="+termNumber+"&subjectName="+subjectName;
	myRef = window
			.open(url, "ViewResume",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
}

</SCRIPT>
<html:form action="/ValuationStatistics">
	<html:hidden property="formName" value="valuationStatisticsForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="saveDetails" />
	<html:hidden property="pageType" value="2" styleId="pageType" />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Valuation &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Valuation Statistics Subject Wise</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
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

								 <tr >
				                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
				                    <td height="25" class="row-odd" align="center">Term Number</td>
				                    <td height="25" class="row-odd" align="center">Subject Code</td>
				                    <td height="25" class="row-odd" align="center">Subject Name</td>
				                    <td height="25" class="row-odd" align="center">Exam Date</td>
				                    <td height="25" class="row-odd" align="center">Valuation started on</td>
				                    <td height="25" class="row-odd" align="center">Issued For Valuation</td>
				                    <td height="25" class="row-odd" align="center">Valuator</td>
				                    <td height="25" class="row-odd" align="center">Valuation Completed</td>
				                    <td height="25" class="row-odd" align="center">Verification Completed</td>
				                    <td height="25" class="row-odd" align="center">Valuation Completed On</td>
				                  </tr>
				                   <tr >
				                    <td height="25" class="row-odd" ></td>
				                    <td height="25" class="row-odd" ></td>
				                    <td height="25" class="row-odd" align="center" ></td>
				                    <td height="25" class="row-odd" align="center"></td>
				                    <td height="25" class="row-odd" align="center" ></td>
				                    <td height="25" class="row-odd" align="center"></td>
				                    <td height="25" class="row-odd" align="center"> Issued TO</td>
				                    <td height="25" class="row-odd" align="center"></td>
				                    <td height="25" class="row-odd" align="center"></td>
				                    <td height="25" class="row-odd" align="center"></td>
				                    <td height="25" class="row-odd" align="center"></td>
				                  </tr>
				                <logic:notEmpty name="valuationStatisticsForm" property="valuationStatusSubjectWise">
					                <nested:iterate id="to" name="valuationStatisticsForm" property="valuationStatusSubjectWise" indexId="count">
					               
					                   	<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
										</c:choose>
					                   		<td width="3%" height="25" ><div align="center"><c:out value="${count + 1}"/></div></td>
					                   		<td width="3%" height="25" align="center"><bean:write name="to" property="termNumber"/></td>
					                   		<td width="5%" height="25" ><bean:write name="to" property="subjectCode"/></td>
					                   		<td width="15%" height="25" ><bean:write name="to" property="subjectName"/></td>
					                   		<td width="5%" height="25" align="center"><bean:write name="to" property="examStratDate"/></td>
					                   		<td width="5%" height="25" align="center"><bean:write name="to" property="issueForValuationDate"/></td>
					                   		<td width="11%" height="25" >
						                   		<logic:notEmpty name="to" property="evaluatorDetails">
						                   			<logic:iterate id="evaluator1" property="evaluatorDetails" name="to">
						                   			   <table>
					                   			   		 <tr>
												            <td width="15%" height="25"><font style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 11px;font-weight: normal;">
												            	<bean:write name="evaluator1" property="employeeName"/>
												            </font> </td>
											             </tr>
											           </table>
						                   			</logic:iterate>
						                   		</logic:notEmpty>
					                   		</td>
					                   		<td width="5%" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status2" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%"  align="center">
											           		<font style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 11px;font-weight: normal;">
											           		<bean:write name="status2" property="evaluatorTypeId"/>
											           		</font>
								                   		</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   		<td width="5%" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" align="center">
											           	<font style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 11px;font-weight: normal;">
											           	<%if(status.getValuationCompleted().equalsIgnoreCase("No")) {%>
									                   		<a href="javascript:void(0)" onclick="viewValuationDetails('<bean:write name="to" property="examId"/>','<bean:write name="to" property="subjectId"/>','<bean:write name="status" property="evaluatorTypeId"/>','<bean:write name="to" property="termNumber"/>','<bean:write name="to" property="subjectName"/>')">
								                   				<bean:write name="status" property="valuationCompleted"/>
									                   		</a>
									                   <%}else if(status.getValuationCompleted().equalsIgnoreCase("Yes")){ %>
									                   			<bean:write name="status" property="valuationCompleted"/>
									                   <%} %>
									                   </font>
								                   		</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   	<td width="5%" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status1" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" align="center">
											           		<font style="font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 11px;font-weight: normal;">
											           		<%if(status1.getVerificationCompleted().equalsIgnoreCase("No")) {%>
												           	<a href="javascript:void(0)" onclick="viewVerificationDetails('<bean:write name="to" property="examId"/>','<bean:write name="to" property="subjectId"/>','<bean:write name="status1" property="evaluatorTypeId"/>','<bean:write name="to" property="termNumber"/>','<bean:write name="to" property="subjectName"/>')">
												           			<bean:write name="status1" property="verificationCompleted"/>
										                   	</a>
										                   	<%}else if(status1.getVerificationCompleted().equalsIgnoreCase("Yes")){ %>
										                   			<bean:write name="status1" property="verificationCompleted"/>
										                   	 <%} %>
										                   	</font>
											           	</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   	<td width="5%" height="25" ><bean:write name="to" property="valuationLastDate"/></td>
					                </nested:iterate>
				                </logic:notEmpty>
								
							</table>
							</td>

							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>

				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>

				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>

