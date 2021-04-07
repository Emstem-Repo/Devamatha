<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>


<script type="text/javascript" language="javascript">
	function resetTo() {
		document.location.href = "ExamStudentEligibilityCheck.do?method=initStudentEligibilityCheck"
	}
	


</script>




</head>
<html:form action="/ExamStudentEligibilityCheck.do" styleId="myform">

	<html:hidden property="formName"
		value="ExamStudentEligibilityCheckForm" />
	<html:hidden property="classValues" styleId="classValues" />
	<html:hidden property="method" styleId="method"
		value="updateStudentEligibilityCheck" />
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">
			<bean:message key="knowledgepro.exam.exam" /> &gt;&gt; <bean:message
				key="knowledgepro.exam.StudentEligibilityForExams" /> &gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.StudentEligibilityForExams" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
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
								<tr>
									<td width="24%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.revaluationApplication.examName" />:</div>
									</td>
									<td height="25" colspan="7" class="row-even"><bean:write
										name="ExamStudentEligibilityCheckForm" property="examName" /></td>
								</tr>
								<tr>

									<td width="24%" class="row-odd" height="28">
									<div align="right"><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.ExamType" />:</div>
									</td>
									<td width="26%" class="row-even"><bean:write
										name="ExamStudentEligibilityCheckForm" property="examType" /></td>
									<td colspan="2" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.StudentEligibilityForExams.displayFor" />:</div>
									</td>
									<td width="27%" colspan="2" class="row-even"><bean:write
										name="ExamStudentEligibilityCheckForm" property="display" /></td>
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
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td height="54" valign="top">
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.blockUnblock.rolNo" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.studentEligibilityEntry.classCode" /></td>
									<td class="row-odd">&nbsp;<bean:message
										key="knowledgepro.exam.blockUnblock.regNo" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.UpdateExcludeWithheld.studentName" /></td>
									<td class="row-odd">Course Fees</td>
									<td class="row-odd">Exam Fees</td>
									<td class="row-odd">Attendance</td>
									<logic:notEmpty property="listAddEligibility" name="ExamStudentEligibilityCheckForm">
										<nested:iterate property="listAddEligibility">
											<td class="row-odd"><nested:write property="display"></nested:write></td>
										</nested:iterate>
									</logic:notEmpty>
									<td class="row-odd">Exam elgibility</td>


									<td class="row-odd"><bean:message
										key="knowledgepro.hostel.checkin.remarks" /></td>
								</tr>

								<html:hidden property="examFeeStatus" styleId="examFeeStatus" />
								<html:hidden property="courseFeeStatus"
									styleId="courseFeeStatus" />
								<html:hidden property="attendenceStatus"
									styleId="attendenceStatus" />
								<html:hidden property="noElgibilityStatus"
									styleId="noElgibilityStatus" />

								<html:hidden property="rowCount" styleId="rowCount" />
<logic:notEmpty property="listStudent" name="ExamStudentEligibilityCheckForm">
								<nested:iterate property="listStudent" indexId="count">

									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td width="63" class="row-even"><nested:write
										property="rollNo" /></td>
									<td width="99" class="row-even"><nested:write
										property="classCode" /></td>
									<td width="127" class="row-even"><nested:write
										property="regNO" /></td>
									<td width="125" class="row-even"><nested:write
										property="studentName" /></td>
									<td width="78" class="row-even">
									<div align="center">
									<%
										String s2 = "hidden1_" + count;
													String s1 = "check1_" + count;
													String disable1="disable1_"+count;
									%>

 <nested:hidden styleId='<%=disable1%>' property="dummycourseFeeDisable" />
 <nested:hidden styleId='<%=s2%>' property="dummycourseFees" />
									<nested:checkbox styleId='<%=s1%>' property="courseFees"
										onclick="autoCheckStatus(this.id)" /> <script>
	var v = document.getElementById("hidden1_<c:out value='${count}'/>").value;
	var courseFeeStatu = document.getElementById("disable1_<c:out value='${count}'/>").value;
	var noElgibilityStatu = document.getElementById("noElgibilityStatus").value;
	if (courseFeeStatu == "true" || noElgibilityStatu == "true") {
		document.getElementById("check1_<c:out value='${count}'/>").disabled = true;
		document.getElementById("check1_<c:out value='${count}'/>").checked = true;
	}
	if (v == "true") {
		document.getElementById("check1_<c:out value='${count}'/>").checked = true;
	}
</script></div>
									</td>
									<td width="67" class="row-even">
									<div align="center">
									<%
										String s3 = "hidden2_" + count;
													String s4 = "check2_" + count;
													String disable2="disable2_"+count;
									%>
<nested:hidden styleId='<%=disable2%>' property="dummyexamFeeDisable" /> 
									<nested:hidden styleId='<%=s3%>' property="dummyexamFees" />
									<nested:checkbox styleId='<%=s4%>' property="examFees"
										onclick="autoCheckStatus(this.id)" /> <script>
	var v1 = document.getElementById("hidden2_<c:out value='${count}'/>").value;
	var examFeeStat = document.getElementById("disable2_<c:out value='${count}'/>").value;
	if (examFeeStat == "true" || noElgibilityStatu == "true") {
		document.getElementById("check2_<c:out value='${count}'/>").disabled = true;
		document.getElementById("check2_<c:out value='${count}'/>").checked = true;
	}
	if (v1 == "true") {
		document.getElementById("check2_<c:out value='${count}'/>").checked = true;
	}
</script></div>
									</td>
									<td height="25" class="row-even">
									<div align="center">
									<%
										String s5 = "hidden3_" + count;
													String s6 = "check3_" + count;
													String disable3="disable3_"+count;
									%><nested:hidden styleId='<%=disable3%>' property="dummyattendanceDisable" />  
									<nested:hidden styleId='<%=s5%>' property="dummyattendance" />
									<nested:checkbox styleId='<%=s6%>' property="attendance"
										onclick="autoCheckStatus(this.id)" /> <script
										type="text/javascript">
	var v2 = document.getElementById("hidden3_<c:out value='${count}'/>").value;
	var attendenceStatu = document.getElementById("disable3_<c:out value='${count}'/>").value;
	if (attendenceStatu == "true" || noElgibilityStatu == "true") {
		document.getElementById("check3_<c:out value='${count}'/>").disabled = true;
		document.getElementById("check3_<c:out value='${count}'/>").checked = true;
	}
	if (v2 == "true") {
		document.getElementById("check3_<c:out value='${count}'/>").checked = true;
	}
</script></div>
									</td>

									<nested:iterate property="listOfEligibility"
										indexId="checkCount">
										<div align="center">
										<%
											String s9 = "hidden5_" + count + "_"
																		+ checkCount;
																String s13 = "hiddenDisable_" + count + "_"
																		+ checkCount;
																String s10 = "check5_" + count + "_"
																		+ checkCount;
										%> <nested:hidden styleId='<%=s9%>'
											property="dummyAdditionalFee" /> <nested:hidden
											property="dummyAdditionalDisable" styleId='<%=s13%>' />
										<td class="row-even"><nested:checkbox styleId='<%=s10%>'
											property="display" onclick="autoCheckStatus(this.id)" /></td>
										<script type="text/javascript">
	var v4 = document
			.getElementById("hidden5_<c:out value='${count}'/>_<c:out value='${checkCount}'/>").value;
	var hiddenDisableValue = document
			.getElementById("hiddenDisable_<c:out value='${count}'/>_<c:out value='${checkCount}'/>").value;
	if (hiddenDisableValue == "true") {

		document
				.getElementById("check5_<c:out value='${count}'/>_<c:out value='${checkCount}'/>").disabled = true;

		document
				.getElementById("check5_<c:out value='${count}'/>_<c:out value='${checkCount}'/>").checked = true;
	}
	if (v4 == "true") {
		document
				.getElementById("check5_<c:out value='${count}'/>_<c:out value='${checkCount}'/>").checked = true;
	}
</script>
										</div>
									</nested:iterate>
									</td>
									<td class="row-even">
									<div align="center">
									<%
										String s11 = "hidden4_" + count;
													String s12 = "check4_" + count;
									%> <nested:hidden styleId='<%=s11%>'
										property="dummyexamElgibility" /> <nested:checkbox
										styleId='<%=s12%>' property="examElgibility" /> <script
										type="text/javascript">
	var v6 = document.getElementById("hidden4_<c:out value='${count}'/>").value;
	if (v6 == "true") {
		document.getElementById("check4_<c:out value='${count}'/>").checked = true;
	}
</script></div>
									</td>

									<td class="row-even"><nested:textarea property="remarks"></nested:textarea></td>
								</nested:iterate></logic:notEmpty>
							</table>
							</td>
							<td background="images/right.gif" width="5" height="54"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="49%" height="35" align="right"><input
								name="Submit2" type="submit" class="formbutton" value="Submit" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" align="left"><input name="Submit3"
								type="reset" class="formbutton" value="Cancel"
								onclick="resetTo()" /></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
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
	<script>
	function autoCheckStatus(id) {
		var t = new Array();
		t = id.split("_");
		var c = 0;
		var count = document.getElementById("rowCount").value;
		for ( var i = 1; i <= 5; i++) {
			if (i != 4) {
				if (i == 5 && t.length > 1) {
					for ( var k = 0; k < count; k++) {
						if (document.getElementById("check5_" + t[1] + "_" + k).checked) {
							c = c + 1;

						}
					}
				}
				if (i != 5) {
					if (document.getElementById("check" + i + "_" + t[1]).checked) {
						c = c + 1;
					}
				}
			}
		}

		var d = parseInt(count, 10) + 3;
		if (c == d) {
			document.getElementById("check4_" + t[1]).checked = true;
		} else {
			document.getElementById("check4_" + t[1]).checked = false;
		}
	}
	</script>
</html:form>



