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
function viewPendingDetails(classId,subId,examId){
	document.location.href = "internalMarksEntry.do?method=viewStudentDetails&classId="+classId+"&subjectId="+subId+"&examId="+examId;
}
function viewPendingDetailsPractial(classId,subId,examId){
	document.location.href = "internalMarksEntry.do?method=viewStudentDetailsPractical&classId="+classId+"&subjectId="+subId+"&examId="+examId;
}
function cancelAction(){
	document.location.href = "LoginAction.do?method=loginAction";
}

</SCRIPT>

</head>


<html:form action="/internalMarksEntry" method="POST" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="viewExamDetails" />
	<html:hidden property="formName" value="internalMarksEntryForm" styleId="formName" />
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
									<td height="25" class="row-odd" align="right" width="45%"><span class="Mandatory">*</span>Please Select The Teacher:</td>
									<td height="25" class="row-even" align="left" width="50%"> 
										<html:select property="teacherId" styleId="teacher" name="internalMarksEntryForm" styleClass="combo">
											<html:option value="">Select</html:option>
											<logic:notEmpty property="teachersMap" name="internalMarksEntryForm">
												<html:optionsCollection property="teachersMap" name="internalMarksEntryForm" label="value" value="key"/>
											</logic:notEmpty>
										</html:select>
									</td>
								</tr>
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
								<html:submit styleClass="formbutton" value="Submit" ></html:submit>
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
