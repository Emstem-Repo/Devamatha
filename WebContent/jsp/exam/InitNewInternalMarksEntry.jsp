<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>

<title>:: CMS ::</title>
<SCRIPT>
function viewDetails(classId,subId,examId,batchId){
	document.location.href = "newCIAMarksEntry.do?method=viewTheoryInternalStudents&classId="+classId+"&subjectId="+subId+"&examId="+examId+"&batchId="+batchId;
}
function viewDetailsPractial(classId,subId,examId,batchId){
	document.location.href = "newCIAMarksEntry.do?method=viewPracticalInternalStudents&classId="+classId+"&subjectId="+subId+"&examId="+examId+"&batchId="+batchId;
}
function cancelAction(){
	document.location.href = "LoginAction.do?method=loginAction";
}
<%--function cancelPage(){
	document.location.href = "internalMarksEntry.do?method=initMarksEntryForTeachers";
}--%>

</SCRIPT>

</head>


<html:form action="/newCIAMarksEntry" method="POST" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="newInternalMarksEntryForm" styleId="formName" />
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Internal Marks Entry&gt;&gt;</span></span></td>

		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Internal
					Marks Entry</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
								<tr>
									<td height="25" class="row-odd" align="center"> SL.NO</td>
									<td height="25" class="row-odd" align="center"> Exam</td>
									<td height="25" class="row-odd" align="center"> Batch (or) Class </td>
									<td height="25" class="row-odd" align="center"> Subject Name </td>
									<td height="25" class="row-odd" align="center">Subject Code </td>
									<td height="25" class="row-odd" align="center">Status </td>
									<td height="25" class="row-odd" align="center"></td>
								</tr>
								<logic:notEmpty property="examMap" name="newInternalMarksEntryForm">
									<tr height="25px">
										<td colspan="7" class="row-odd" align="center">Enter Theory Marks</td>
									</tr>
									<%int increment = 1; %>
									<logic:iterate id="exam" property="examMap" name="newInternalMarksEntryForm" >
										<logic:iterate id="exam1" property="value" name="exam">
											<logic:iterate id="exam2" property="value" name="exam1">
											<logic:iterate id="exam3" property="value" name="exam2">
											<bean:define id="examTO" name="exam3" property="value" type="com.kp.cms.to.exam.InternalMarksEntryTO"></bean:define>
											<%if(examTO.getSubjectType()!= null && (examTO.getSubjectType().equalsIgnoreCase("T") || examTO.getSubjectType().equalsIgnoreCase("B")) && examTO.isTheoryOpen()){ %>
											<%if(increment%2==0){ %>
													<tr class="row-even">
													<%}else{ %>
													<tr class="row-white">
													<%} %>
											<td width="10%" height="25">
											<div align="center"><%=increment %></div>
											</td>
											<td align="center" width="20%" height="25"><bean:write name="exam3"
												 property="value.examName" /></td>
											<%if(examTO.getBatchName()!=null && !examTO.getBatchName().isEmpty()){ %>
											<td align="center" width="10%" height="25"><bean:write name="exam3"
												 property="value.batchName" /></td>
											<%}else{ %>
											<td align="center" width="10%" height="25"><bean:write name="exam3"
												 property="value.className" /></td>
												 <%} %>
											<td align="center" width="25%" height="25"><bean:write name="exam3"
												 property="value.subjectName" /></td>
											<td align="center" width="10%" height="25"><bean:write name="exam3"
												 property="value.subjectCode" /></td>
											<td align="center" width="10%" height="25">
												<bean:write name="exam3" property="value.status"/>
											</td>
											<td align="center" width="10%" height="25">
											 <logic:equal name="exam3" value="Edit" property="value.finalStatus">
												<logic:equal name="exam3" value="Pending" property="value.status">
												<input type="button" class="newformbutton" value="Enter Marks" 
											   				onclick="viewDetails('<bean:write name="exam3" property="value.classId"/>','<bean:write name="exam3" property="value.subId"/>','<bean:write name="exam3" property="value.examId"/>','<bean:write name="exam3" property="value.batchId"/>')"/>
												</logic:equal>
												<logic:equal name="exam3" value="Completed" property="value.status">
												<input type="button" class="blackformbutton" value="View / Edit " 
											   				onclick="viewDetails('<bean:write name="exam3" property="value.classId"/>','<bean:write name="exam3" property="value.subId"/>','<bean:write name="exam3" property="value.examId"/>','<bean:write name="exam3" property="value.batchId"/>')"/>
												</logic:equal>
											  </logic:equal>
											   <logic:equal name="exam3" value="View" property="value.finalStatus">
											   		<input type="button" class="blackformbutton" value="View Marks" 
											   				onclick="viewDetails('<bean:write name="exam3" property="value.classId"/>','<bean:write name="exam3" property="value.subId"/>','<bean:write name="exam3" property="value.examId"/>','<bean:write name="exam3" property="value.batchId"/>')"/>
											   </logic:equal>
											</td>
											<% increment++; %>
											<%} %>
											</logic:iterate>
											</logic:iterate>
										</logic:iterate>
									</logic:iterate>
									
									
								</logic:notEmpty>
								
								
								
								<logic:notEmpty property="examMap" name="newInternalMarksEntryForm">
									<logic:notEmpty property="practicalExamMarksDetails" name="newInternalMarksEntryForm">
										<tr height="25px">
											<td colspan="7" class="row-odd" align="center">Enter Practical Marks</td>
										</tr>
									</logic:notEmpty>
									<%int increment = 1; %>
									<logic:iterate id="exam" property="examMap" name="newInternalMarksEntryForm" >
										<logic:iterate id="exam1" property="value" name="exam">
											<logic:iterate id="exam2" property="value" name="exam1">
											<logic:iterate id="exam3" property="value" name="exam2">
											<bean:define id="examTO" name="exam3" property="value" type="com.kp.cms.to.exam.InternalMarksEntryTO"></bean:define>
											<%if(examTO.getSubjectType() != null && (examTO.getSubjectType().equalsIgnoreCase("P") || examTO.getSubjectType().equalsIgnoreCase("B")) && examTO.isPracticalOpen()){ %>
											<%if(increment%2==0){ %>
													<tr class="row-even">
													<%}else{ %>
													<tr class="row-white">
													<%} %>
											<td width="10%" height="25">
											<div align="center"><%=increment %></div>
											</td>
											<td align="center" width="20%" height="25"><bean:write name="exam3"
												 property="value.examName" /></td>
											<%if(examTO.getBatchName()!=null && !examTO.getBatchName().isEmpty()){ %>
											<td align="center" width="10%" height="25"><bean:write name="exam3"
												 property="value.batchName" /></td>
											<%}else{ %>
											<td align="center" width="10%" height="25"><bean:write name="exam3"
												 property="value.className" /></td>
												 <%} %>
											<td align="center" width="25%" height="25"><bean:write name="exam3"
												 property="value.subjectName" /></td>
											<td align="center" width="10%" height="25"><bean:write name="exam3"
												 property="value.subjectCode" /></td>
											<td align="center" width="10%" height="25">
												<bean:write name="exam3" property="value.status"/>
											</td>
											<td align="center" width="10%" height="25">
											 <logic:equal name="exam3" value="Edit" property="value.finalStatus">
												<logic:equal name="exam3" value="Pending" property="value.status">
												<input type="button" class="newformbutton" value="Enter Marks" 
											   				onclick="viewDetailsPractial('<bean:write name="exam3" property="value.classId"/>','<bean:write name="exam3" property="value.subId"/>','<bean:write name="exam3" property="value.examId"/>','<bean:write name="exam3" property="value.batchId"/>')"/>
												</logic:equal>
												<logic:equal name="exam3" value="Completed" property="value.status">
												<input type="button" class="blackformbutton" value="View / Edit " 
											   				onclick="viewDetailsPractial('<bean:write name="exam3" property="value.classId"/>','<bean:write name="exam3" property="value.subId"/>','<bean:write name="exam3" property="value.examId"/>','<bean:write name="exam3" property="value.batchId"/>')"/>
												</logic:equal>
											  </logic:equal>
											   <logic:equal name="exam3" value="View" property="value.finalStatus">
											   		<input type="button" class="blackformbutton" value="View Marks" 
											   				onclick="viewDetailsPractial('<bean:write name="exam3" property="value.classId"/>','<bean:write name="exam3" property="value.subId"/>','<bean:write name="exam3" property="value.examId"/>','<bean:write name="exam3" property="value.batchId"/>')"/>
											   </logic:equal>
											</td>
											<% increment++; %>
											<%} %>
											</logic:iterate>
											</logic:iterate>
										</logic:iterate>
									</logic:iterate>
									
									
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
						<tr height="20px">
							<td align="center">
								
							</td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						<tr>
							<td align="center">
								<input type="button" class="formbutton" value="Close" onclick="cancelAction()" />
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"></td>
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
