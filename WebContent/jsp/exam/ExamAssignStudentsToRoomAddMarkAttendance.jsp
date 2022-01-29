<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript" language="javascript">
function fun()
{
	document.location.href="ExamAssignStudentsToRoom.do?method=initExamAssignStudentsToRoom";
}	
</script>
<html:form action="/ExamAssignStudentsToRoom" method="post">

	<html:hidden property="formName" value="ExamAssignStudentsToRoomForm" />
	<html:hidden property="pageType" value="4" />
	<html:hidden   property="method" value="updateMarkAttendance"/>
	<html:hidden property="examType" styleId="examType" />
	<html:hidden property="examNameId" styleId="examNameId" />
	<html:hidden property="date" styleId="date" />
	<html:hidden property="hr" styleId="hr" />
	<html:hidden property="min" styleId="min" />
	<html:hidden property="roomId" styleId="roomId" />

	<html:hidden property="nonEligible" styleId="nonEligible" />
	<html:hidden property="displayOrder" styleId="displayOrder" />



	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			>> Mark Attendance &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Mark
					Attendance</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
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
								<tr>

									<td width="10%" class="row-odd">
									<div align="right">
									<DIV align="right">Room No. :</DIV>
									</div>
									</td>
									<td width="10%" class="row-even"><bean:write
										name="ExamAssignStudentsToRoomForm" property="roomNo" /></td>
									<td width="10%" class="row-odd">
									<div align="right">
									<DIV align="right">Date :</DIV>
									</div>
									</td>
									<td width="10%" class="row-even"><bean:write
										name="ExamAssignStudentsToRoomForm" property="date" /></td>
									<td width="10%" class="row-odd">
									<div align="right">
									<DIV align="right">Time :</DIV>
									</div>
									</td>
									<td width="10%" class="row-even"><bean:write
										name="ExamAssignStudentsToRoomForm" property="time" /></td>
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
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="35" height="25" class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td width="456" class="row-odd">Name of Invigilator</td>
									<td class="row-odd">Attendance ( Mark Absence)</td>
								</tr>

								<logic:notEmpty name="ExamAssignStudentsToRoomForm"
									property="listInvigilator">
									<nested:iterate 
										property="listInvigilator" 
										indexId="count" >
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td class="bodytext">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td align="left" class="bodytext"><nested:write
											 property="invigilatorType" /></td>
										<td align="center" class="bodytext">
										<div align="center">
										<%
											String s2 = "hidden_" + count;
											String s1 = "check_" + count;
										%> 
										<nested:hidden styleId='<%=s2%>' property="isCheckedDummy"
											 /> <nested:checkbox styleId='<%=s1%>'
											property="isChecked"   />
<script type="text/javascript">
	var v = document.getElementById("hidden_<c:out value='${count}'/>").value;
	if (v=='true') {
		document.getElementById("check_<c:out value='${count}'/>").checked = true;
	}
</script></div>
										</td>
										</tr>
									</nested:iterate>
								</logic:notEmpty>
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
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="44" height="25" class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Class</td>
									<td width="195" class="row-odd">Subject</td>
									<td class="row-odd">Register No</td>
									<td class="row-odd">Roll No.</td>
									<td class="row-odd">Name</td>
									<td class="row-odd">Attendance ( Mark Absence)</td>
								</tr>

								<logic:notEmpty name="ExamAssignStudentsToRoomForm"
									property="listStudents">
									<nested:iterate 
										property="listStudents"  indexId="count" >
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td class="bodytext">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td align="center" class="bodytext"><nested:write
											property="className" /></td>
										<td align="center" class="bodytext"><nested:write
											 property="subject" /></td>
										<td align="center" class="bodytext"><nested:write
											 property="registerNo" /></td>
										<td align="center" class="bodytext"><nested:write
											 property="rollNo" /></td>
										<td align="center" class="bodytext"><nested:write
											 property="name" /></td>
										<td align="center" class="bodytext">
										<div align="center">
										<%
											String s3 = "hidde_" + count;
											String s4 = "chec_" + count;
										%> <nested:hidden styleId='<%=s3%>' property="isCheckedDummy"
											 /> <nested:checkbox styleId='<%=s4%>'
											property="isChecked"   /> 
										<script
											type="text/javascript">
											var v2 = document.getElementById("hidde_<c:out value='${count}'/>").value;
											if (v2=='true') {
												document.getElementById("chec_<c:out value='${count}'/>").checked = true;
											}
										</script></div>
										</td>
										</tr>
									</nested:iterate>
								</logic:notEmpty>


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
							<td width="49%" height="35" align="right">
					<input name="Submit" type="Submit" class="formbutton" value="Submit">
							</td>
							<td width="2%" align="center">&nbsp;</td>
							<td width="49%" align="left">
			<input name="Submit2" type="Submit" class="formbutton" value="Cancel" onclick="fun();"></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
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
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>


</html:form>


