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
<link rel="stylesheet" href="css/calendar.css">

<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<head>
<title><bean:message key="knowledgepro.title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="css/calendar.css">
<link href="css/styles.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>

</head>
<script type="text/javascript">

function cancelAction() {
	window.close();
}

function viewValuationDetails(examId,subjectId,evaluatorId,classId,subjectName){
	var examType=document.getElementById("typeExam").value;
	var url = "PendingExamResults.do?method=viewValuationDetails&examId="+examId+"&subjectId="+subjectId+"&evaluatorTypeId="+evaluatorId+"&classId="+classId+"&subjectName="+subjectName+"&examType="+examType;
	myRef = window
			.open(url, "ViewResume",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
}

function viewVerificationDetails(examId,subjectId,evaluatorId,classId,subjectName){
	var examType=document.getElementById("typeExam").value;
	var url = "PendingExamResults.do?method=viewVerificationDetails&examId="+examId+"&subjectId="+subjectId+"&evaluatorTypeId="+evaluatorId+"&classId="+classId+"&subjectName="+subjectName+"&examType="+examType;
	myRef = window
			.open(url, "ViewResume",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
}

function changeValue(count,check){
	var checkedValue = document.getElementById("valuationStatus_"+count).checked;
	if(checkedValue){
		document.getElementById("valuationStatus_"+count).value = "on";
		document.getElementById("hidden_"+count).value = "on";
	}else{
		document.getElementById("valuationStatus_"+count).value = "off";
		document.getElementById("hidden_"+count).value = "off";
	}
}
function changeValue1(count,check){
	var checkedValue = document.getElementById("valuationStatus1_"+count).checked;
	if(checkedValue){
		document.getElementById("valuationStatus1_"+count).value = "on";
		document.getElementById("hidden1_"+count).value = "on";
	}else{
		document.getElementById("valuationStatus1_"+count).value = "off";
		document.getElementById("hidden1_"+count).value = "off";
	}
}

function viewMismatchFounrDetails(subjectId,evaluatorId,classId,subjectName){
	var examType=document.getElementById("typeExam").value;
	var url = "PendingExamResults.do?method=viewMismatchDetails&subjectId="+subjectId+"&evaluatorTypeId="+evaluatorId+"&classId="+classId+"&subjectName="+subjectName+"&examType="+examType;
	myRef = window
			.open(url, "ViewResume",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</SCRIPT>
<html:form action="/PendingExamResults">
	<html:hidden property="formName" value="pendingExamResultsForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="pageType" value="2" styleId="pageType" />
	<input type="hidden" id="typeExam" name="type" value='<bean:write name="pendingExamResultsForm" property="examType"/>' />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Valuation Status &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Exam Valuation Status</td>
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
				                    <td height="25" class="row-odd" align="center">Subject Code</td>
				                    <td height="25" class="row-odd" align="center">Subject Name</td>
				                    <td height="25" class="row-odd" align="center">Internal Subject</td>
				                    <td height="25" class="row-odd" align="center">Exam Date</td>
				                    <td height="25" class="row-odd" align="center">Issued For Valuation To</td>
				                    <td height="25" class="row-odd" align="center">Valuator</td>
				                    <td height="25" class="row-odd" align="center">Valuation Completed</td>
				                    <td height="25" class="row-odd" align="center">Verification Completed</td>
				                    <td height="25" class="row-odd" align="center">Mismatch Found</td>
				                    <td height="25" class="row-odd" align="center">Valuation Process Completed(COE)</td>
				                    <td height="25" class="row-odd" align="center">Overall Process Completed (Exam Office)</td>
				                  </tr>
				                   <% int count1 =1; %>
				                <logic:notEmpty name="pendingExamResultsForm" property="valuationStatus">
					                <nested:iterate id="to" name="pendingExamResultsForm" property="valuationStatus" indexId="count" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					               		<% if(!to.isCertificateCourse()){ %>
					                   	<tr>
					                   		<td width="4%" height="25" class="row-even" ><div align="center"><%=count1 %></div></td>
					                   		<td width="5%" height="25" class="row-even"><bean:write name="to" property="subjectCode"/></td>
					                   		<td width="18%" height="25" class="row-even"><bean:write name="to" property="subjectName"/></td>
					                   		<td width="5%" height="25" class="row-even" align="center"><bean:write name="to" property="internalSubject"/></td>
					                   		<td width="5%" height="25" class="row-even" align="center"><bean:write name="to" property="examStratDate"/></td>
					                   		<td width="14%" height="25" class="row-even">
						                   		<logic:notEmpty name="to" property="evaluatorDetails">
						                   			<logic:iterate id="evaluator1" property="evaluatorDetails" name="to">
						                   			   <table>
					                   			   		 <tr>
												            <td width="15%" height="25" class="row-even"><bean:write name="evaluator1" property="employeeName"/></td>
											             </tr>
											           </table>
						                   			</logic:iterate>
						                   		</logic:notEmpty>
					                   		</td>
					                   		<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status2" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           		<bean:write name="status2" property="evaluatorTypeId"/>
								                   		</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   		<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           	<%if(status.getValuationCompleted().equalsIgnoreCase("No")) {%>
									                   		<a href="javascript:void(0)" onclick="viewValuationDetails('<bean:write name="to" property="examId"/>','<bean:write name="to" property="subjectId"/>','<bean:write name="status" property="evaluatorTypeId"/>','<bean:write name="to" property="classId"/>','<bean:write name="to" property="subjectName"/>')">
								                   				<bean:write name="status" property="valuationCompleted"/>
									                   		</a>
									                   <%}else if(status.getValuationCompleted().equalsIgnoreCase("Yes")){ %>
									                   			<bean:write name="status" property="valuationCompleted"/>
									                   <%} %>
								                   		</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   	<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status1" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           		<%if(status1.getVerificationCompleted().equalsIgnoreCase("No")) {%>
												           	<a href="javascript:void(0)" onclick="viewVerificationDetails('<bean:write name="to" property="examId"/>','<bean:write name="to" property="subjectId"/>','<bean:write name="status1" property="evaluatorTypeId"/>','<bean:write name="to" property="classId"/>','<bean:write name="to" property="subjectName"/>')">
												           			<bean:write name="status1" property="verificationCompleted"/>
										                   	</a>
										                   	<%}else if(status1.getVerificationCompleted().equalsIgnoreCase("Yes")){ %>
										                   			<bean:write name="status1" property="verificationCompleted"/>
										                   	 <%} %>
											           	</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   	<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status2" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           		<%if(status2.getMisMatchFound().equalsIgnoreCase("Yes")) {%>
												           	<a href="javascript:void(0)" onclick="viewMismatchFounrDetails('<bean:write name="to" property="subjectId"/>','<bean:write name="status2" property="evaluatorTypeId"/>','<bean:write name="to" property="classId"/>','<bean:write name="to" property="subjectName"/>')">
												           			<bean:write name="status2" property="misMatchFound"/>
										                   	</a>
										                   	<%}else if(status2.getMisMatchFound().equalsIgnoreCase("No")){ %>
										                   			<bean:write name="status2" property="misMatchFound"/>
										                   	 <%} %>
											           	</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   		<td width="7%" class="row-even" align="center">
					                   		<logic:equal value="on" name="to" property="valuationProcess"><bean:message key="knowledgepro.yes"/></logic:equal>
											<logic:notEqual value="on" name="to" property="valuationProcess"><bean:message key="knowledgepro.no"/></logic:notEqual>
					                   		</td>
					                   		<td width="7%" class="row-even" align="center">
					                   		<logic:equal value="on" name="to" property="overallProcess"><bean:message key="knowledgepro.yes"/></logic:equal>
											<logic:notEqual value="on" name="to" property="overallProcess"><bean:message key="knowledgepro.no"/></logic:notEqual>
					                   		</td>
					                   		</tr>
					                   		<% count1++; %>
					                   		<%} %>
					                </nested:iterate>
					                <nested:iterate id="to" name="pendingExamResultsForm" property="valuationStatus" indexId="count" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					               		<% if(to.isCertificateCourse()){ %>
					                   	<tr>
					                   		<td width="4%" height="25" class="row-even" ><div align="center"><FONT color="#A52A2A"><%=count1 %></FONT></div></td>
					                   		<td width="5%" height="25" class="row-even"><FONT color="#A52A2A"><bean:write name="to" property="subjectCode"/></FONT></td>
					                   		<td width="18%" height="25" class="row-even"><FONT color="#A52A2A"><bean:write name="to" property="subjectName"/></FONT></td>
					                   		<td width="5%" height="25" class="row-even" align="center"><FONT color="#A52A2A"><bean:write name="to" property="internalSubject"/></FONT></td>
					                   		<td width="5%" height="25" class="row-even" align="center"><FONT color="#A52A2A"><bean:write name="to" property="examStratDate"/></FONT></td>
					                   		<td width="14%" height="25" class="row-even">
						                   		<logic:notEmpty name="to" property="evaluatorDetails">
						                   			<logic:iterate id="evaluator1" property="evaluatorDetails" name="to">
						                   			   <table>
					                   			   		 <tr>
												            <td width="15%" height="25" class="row-even">
												            <FONT color="#A52A2A"><bean:write name="evaluator1" property="employeeName"/></FONT></td>
											             </tr>
											           </table>
						                   			</logic:iterate>
						                   		</logic:notEmpty>
					                   		</td>
					                   		<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status2" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           		<FONT color="#A52A2A"><bean:write name="status2" property="evaluatorTypeId"/></FONT>
								                   		</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   		<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           	<%if(status.getValuationCompleted().equalsIgnoreCase("No")) {%>
									                   		<a href="javascript:void(0)" onclick="viewValuationDetails('<bean:write name="to" property="examId"/>','<bean:write name="to" property="subjectId"/>','<bean:write name="status" property="evaluatorTypeId"/>','<bean:write name="to" property="courseId"/>','<bean:write name="to" property="subjectName"/>')">
								                   				<bean:write name="status" property="valuationCompleted"/>
									                   		</a>
									                   <%}else if(status.getValuationCompleted().equalsIgnoreCase("Yes")){ %>
									                   			<bean:write name="status" property="valuationCompleted"/>
									                   <%} %>
								                   		</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   	<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status1" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           		<%if(status1.getVerificationCompleted().equalsIgnoreCase("No")) {%>
												           	<a href="javascript:void(0)" onclick="viewVerificationDetails('<bean:write name="to" property="examId"/>','<bean:write name="to" property="subjectId"/>','<bean:write name="status1" property="evaluatorTypeId"/>','<bean:write name="to" property="courseId"/>','<bean:write name="to" property="subjectName"/>')">
												           			<bean:write name="status1" property="verificationCompleted"/>
										                   	</a>
										                   	<%}else if(status1.getVerificationCompleted().equalsIgnoreCase("Yes")){ %>
										                   			<bean:write name="status1" property="verificationCompleted"/>
										                   	 <%} %>
											           	</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   	<td width="5%" class="row-even" align="center">
					                   		<logic:notEmpty name="to" property="statusTOs">
					                   			<logic:iterate id="status2" property="statusTOs" name="to" type="com.kp.cms.to.exam.ExamValuationStatusTO">
					                   			  <table>
					                   			   <tr>
											           	<td width="5%" class="row-even" align="center">
											           		<%if(status2.getMisMatchFound().equalsIgnoreCase("Yes")) {%>
												           	<a href="javascript:void(0)" onclick="viewMismatchFounrDetails('<bean:write name="to" property="subjectId"/>','<bean:write name="status2" property="evaluatorTypeId"/>','<bean:write name="to" property="courseId"/>','<bean:write name="to" property="subjectName"/>')">
												           			<bean:write name="status2" property="misMatchFound"/>
										                   	</a>
										                   	<%}else if(status2.getMisMatchFound().equalsIgnoreCase("No")){ %>
										                   			<bean:write name="status2" property="misMatchFound"/>
										                   	 <%} %>
											           	</td>
					                   			   </tr>
					                   			  </table>
					                   			</logic:iterate>
					                   		</logic:notEmpty>
					                   	</td>
					                   		<td width="7%" class="row-even" align="center">
					                   		<logic:equal value="on" name="to" property="valuationProcess"><bean:message key="knowledgepro.yes"/></logic:equal>
											<logic:notEqual value="on" name="to" property="valuationProcess"><bean:message key="knowledgepro.no"/></logic:notEqual>
					                   		</td>
					                   		<td width="7%" class="row-even" align="center">
					                   		<logic:equal value="on" name="to" property="overallProcess"><bean:message key="knowledgepro.yes"/></logic:equal>
											<logic:notEqual value="on" name="to" property="overallProcess"><bean:message key="knowledgepro.no"/></logic:notEqual>
					                   		</td>
					                   		</tr>
					                   		<% count1++; %>
					                   		<%} %>
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
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						<tr>
							<td width="20%" height="35" align="center">							
							<input type="button" class="formbutton" value="Close" onclick="cancelAction()" />							
							</td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
					</table>
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

