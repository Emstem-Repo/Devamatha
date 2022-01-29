<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<title>:: CMS ::</title>
<script type="text/javascript" language="javascript">
check1=false;
function checkedAll() {
		if (document.getElementById("checkbox").checked == false) {
			check1 = false;
	} else {
		check1 = true;
	}
	var name=document.getElementById("listSize").value;
	for (var i = 0; i < name; i++) {
		var na="check_"+i;
		document.getElementById(na).checked = check1;
		}
	}
function gotoPage()
{
	document.location.href="ExamOptionalSubjectAssignmentToStudent.do?method=initExamOptionalSubjectAssignmentToStudent";
}
</script>


<html:form action="/ExamOptionalSubjectAssignmentToStudent.do"
	styleId="myform">

	<html:hidden property="formName"
		value="ExamOptionalSubjectAssignmentToStudentForm" />

	<html:hidden property="method" styleId="method" value="Apply" />
	<html:hidden property="listSize" styleId="listSize" />
	<html:hidden property="pageType" value="2" />

	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.optSubAssignmentStu" />
			&gt;&gt;</span></span></td>
		</tr>

		<tr>
			<td>
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.optSubAssignmentStu" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td width="100%" height="20">&nbsp;</td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td height="20">
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

											<td width="22%" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.interview.Year" /></div>
											</td>
											<td width="34%" class="row-even" colspan="3"><bean:write
												name="ExamOptionalSubjectAssignmentToStudentForm"
												property="academicYear" /></td>
											<td width="27%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.attendance.class.col" /></div>
											</td>
											<td width="20%" height="25" class="row-even"><bean:write
												name="ExamOptionalSubjectAssignmentToStudentForm"
												property="className" /></td>

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
						<tr>
							<td height="25" class=" heading"></td>
						</tr>
						<tr>
							<td height="25" class=" heading">
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
									<table width="100%" height="90" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-odd">
											<td width="61" height="25" class="bodytext">
											<div align="center"><strong>Select</strong> <input
												type="checkbox" name="checkbox" id="checkbox"
												onclick="checkedAll()">
											<div>
											</td>
											<td width="201" height="25" class="bodytext">
										<bean:message key="knowledgepro.exam.studentEligibilityEntry.rollNo" /></td>
								
											<td width="165" class="bodytext">
											<bean:message key="knowledgepro.exam.studentEligibilityEntry.registerNo" />	</td>
										
											<td width="165" class="bodytext">
											<bean:message key="knowledgepro.exam.studentEligibilityEntry.studentname" /></td>
											
										
											<td width="165" class="bodytext">
											<bean:message key="knowledgepro.exam.Specialisation" /></td>
											
											<td width="124" class="bodytext">
											<bean:message key="knowledgepro.exam.OptionalsubjectGroup" /></td>
										</tr>
										<nested:iterate property="list" indexId="count"
											name="ExamOptionalSubjectAssignmentToStudentForm">
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<nested:hidden property="id" />




											<td height="20" class="bodytext">
											<div align="center">
											<%    String s2 = "hidden_"+count; 
		                      						String s1 = "check_"+count; 
		                     				 %> 
		                     				
		                     				 <nested:checkbox styleId='<%=s1%>' property="isChecked"  /> 
											</td>

											<td height="20"><nested:write property="rollNo" /></td>
													
											<td height="20"><nested:write property="registerNo" /></td>
										
											<td height="23"><nested:hidden property="studentId" /><nested:write property="studentname" /></td>
									
											<td height="20"><nested:hidden property="specializationId" /> <nested:write	property="specialization" /></td>
										
											<td height="20"><nested:hidden property="optSubGroupId" /> <nested:write property="optSubGroup" /></td>
										
											</tr>

										</nested:iterate>

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
						<tr>
							<td height="25" class=" heading"></td>
						</tr>

						<tr>
							<td height="25" class=" heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><input name="submit" type="submit"
										class="formbutton" value="Apply" /></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><input name="cancel" type="button"
										class="formbutton" value="Cancel" onclick="gotoPage()" /></td>
								</tr>
							</table>
							</td>
						</tr>


					</table>
					<div align="center">
					<table width="100%" height="10  border="0">

						<tr>
							<td></td>
						</tr>

					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>
