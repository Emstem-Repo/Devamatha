<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript" language="javascript">
function setFocus()
	{
	document.getElementById("regNo").focus();
		}

	function loadPage()
	{
		document.location.href = "verificationMarksCorrection.do?method=cancelVerificationCorrection";
	}
	function saveMarks()
	{
		document.getElementById("method").value="saveMarks";
		document.examMarksVerificationCorrectionForm.submit();
	}
	function getExamsByExamTypeAndYear() {
		var year=document.getElementById("year").value;
		getExamNameByAcademicYear("examMap", year, "examNameId", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examNameId", "- Select -");
		updateCurrentExam(req, "examNameId");
	}
	function reset() {
	    
	    document.getElementById("examNameId").value="";
	    document.getElementById("regNo").value="";
	}
	function getExamsByExamTypeAndYear() {
		if(document.getElementById("examId").checked==true)
		var examType=document.getElementById("examId").value;
		else if(document.getElementById("sup").checked==true)
			var examType=document.getElementById("sup").value;
			var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examNameId", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examNameId", "- Select -");
		updateCurrentExam(req, "examNameId");
		getCourse(document.getElementById("examNameId").value);
	}
	
</script>

<html:form action="/verificationMarksCorrection" method="POST">
  
	<html:hidden property="method" styleId="method" value="getStudentMarks" />
	<html:hidden property="formName" value="examMarksVerificationCorrectionForm" styleId="formName"/>
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			Verification Marks Correction&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Verification Marks Correction</strong></td>
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
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
							<% boolean disable1=false;%>
										<logic:equal value="true" name="examMarksVerificationCorrectionForm" property="flag">
										<% disable1=true;%>
										</logic:equal>
									<tr>
									<td height="25" colspan="8" class="row-even">
									<div align="Center">
									<html:radio property="examType"
										styleId="examId" value="Regular"
										onclick="getExamsByExamTypeAndYear()" disabled='<%=disable1%>'></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamsByExamTypeAndYear()" disabled='<%=disable1%>'></html:radio>
									Supplementary
									</div>
									</td>

								</tr>
								<tr>
									<td height="25"  class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td height="25"  class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="examMarksVerificationCorrectionForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo"  disabled='<%=disable1%>' onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td  height="25"  class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.revaluationApplication.examName" /> :</div>
									</td>
									<td  height="25" class="row-even" ><html:select
										property="examId" styleClass="combo" styleId="examNameId"
										name="examMarksVerificationCorrectionForm" style="width:200px" disabled='<%=disable1%>' >
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="examMarksVerificationCorrectionForm"
											property="examMap">
											<html:optionsCollection property="examMap"
												name="examMarksVerificationCorrectionForm" label="value"
												value="key" />
										</logic:notEmpty>
									</html:select></td>
								</tr>

								<tr>
									<td height="25" width="25%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.verification.student.regNo" /></div>
									</td>
									<td  height="25" class="row-even"><html:text
										property="registerNo" styleId="regNo" maxlength="50"
										styleClass="TextBox" size="20"  disabled='<%=disable1%>'/></td>
									<td height="25" width="25%" class="row-odd">
									
									</td>
									<td height="25" class="row-even"></td>
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
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="52%" height="35">
							<div align="right">
								<input type="submit" value="Search" class="formbutton" />&nbsp;&nbsp;&nbsp;
								</div>
							</td>
							<td width="38%"><input type="Reset" class="formbutton" 
								value="Reset" onclick="reset()"/></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<logic:notEmpty name="examMarksVerificationCorrectionForm" property="verificationlist">
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
				<tr>
				<td class="row-even" width="25%">
				 <div align="right"><bean:message key="knowledgepro.exam.studentName"/> :</div>
					</td>
				<td width="25%" height="25" class="row-even" >&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="examMarksVerificationCorrectionForm" property="studentName"/>
				</td>
			     <td class="row-even" width="25%">
				 <div align="right"><bean:message key="knowledgepro.exam.className"/> :</div>
					</td>              	
				<td width="25%" height="25" class="row-even">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="examMarksVerificationCorrectionForm" property="className"/>
					</td>
				</tr>
				</table>
				</td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" height="25" class="row-odd"><bean:message
										key="knowledgepro.exam.subject" /></td>
									<td align="center" height="25" class="row-odd"><bean:message
										key="knowledgepro.exam.marks" /></td>	
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.exam.evaluator1" /></div>
									</td>
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.exam.evaluator2" /></div>
									</td>
								</tr>
								<tr>
									<nested:iterate id="to1" name="examMarksVerificationCorrectionForm"
										property="verificationlist" indexId="count">
											<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
				                   		<td width="10%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
				                   		<td width="20%" height="25" class="row-even" align="center"><bean:write name="to1" property="subjectName"/></td>
				                   		<logic:notEmpty name="to1" property="marks">
				                   		<td width="25%" height="25" class="row-even" align="center"><nested:text  property="marks"  maxlength="6" /></td>
				                   		</logic:notEmpty>
				                   		<logic:empty name="to1" property="marks">
				                   		<td width="25%" height="25" class="row-even" align="center">
				                   		</logic:empty>
				                   		<logic:notEmpty name="to1" property="evaluatorMarks1">
				                   		<td width="25%" height="25" class="row-even" align="center"><nested:text  property="evaluatorMarks1"  maxlength="6" /></td>
				                   		</logic:notEmpty>
				                   		<logic:empty name="to1" property="evaluatorMarks1">
				                   		<td width="25%" height="25" class="row-even" align="center">
				                   		</logic:empty>
				                   		<logic:notEmpty name="to1" property="evaluatorMarks2">
				                   		<td width="25%" height="25" class="row-even" align="center"><nested:text  property="evaluatorMarks2"  maxlength="6" /></td>
				                   		</logic:notEmpty>
				                   		<logic:empty name="to1" property="evaluatorMarks2">
				                   		<td width="25%" height="25" class="row-even" align="center">
				                   		</logic:empty>
									</nested:iterate>
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
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
				            <td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							<tr>
								<td width="20%" >
							<div align="right">
							 <input type="button" value="Submit" onclick="saveMarks()" class="formbutton"/>
							 &nbsp;&nbsp;&nbsp;</div>
							</td>
							<td width="20%"><input type="Reset" class="formbutton"
								value="Cancel" onclick="loadPage()" /></td>
								</tr>
						</table>
						</td>
						</tr>
						</logic:notEmpty>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
var focusField = document.getElementById("focusValue").value;
if(focusField != 'null') {
	if(document.getElementById(focusField)!=null)      
        document.getElementById("regNo").focus();
}
</script>