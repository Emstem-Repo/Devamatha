<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<link href="../css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	

	function selectAll(totalCount) {
		var examinersSelected = "";
		var selectedcount = 0;
		for ( var i = 0; i < totalCount; i++) {
			if (document.getElementById("selectall").checked == true) {
				document.getElementById("checkbox_" + i).checked = true;
				examinersSelected = examinersSelected
						+ document.getElementById("checkbox_" + i).value + ",";
			} else if (document.getElementById("selectall").checked == false) {
				document.getElementById("checkbox_" + i).checked = false;
			}
			selectedcount++;

		}
		
		document.getElementById("examiner").value = examinersSelected;
	}
	function setChkBoxValues(value) {
		var examinersSelected = "";
		examinersSelected = examinersSelected + value + ",";
		document.getElementById("examiner").value = examinersSelected;

	}

	function getBack(){
		document.location.href = "AssignExaminerToExam.do?method=initExamAssignExaminerToExam";
		
	}
</script>
<html:form action="AssignExaminerToExam">
	<html:hidden property="method" styleId="method"
		value="addExaminerToExam" />
	<html:hidden property="formName" value="ExamAssignExaminerToExamForm" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="examiner" styleId="examiner" />
	<html:hidden property="examNameId" styleId="examNameId" />
	
	

	
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.assignExaminerToExam" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<p><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.assignExaminerToExam" /></strong></p>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
							<td></td>
							<td>
							
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="19" class="news">&nbsp;</td>
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
									<td width="23%" height="25" class="row-odd">
									<div align="right">
									<DIV align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.examDefinition.examName" /> :</DIV>
									</div>
									</td>
									<td width="23%" height="25" colspan="3" class="row-even"><bean:write
										property="examName" name="ExamAssignExaminerToExamForm" /></td>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
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
								<tr class="row-odd">
									<td width="64" class="bodytext">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<c:set var="totalCount">
										<nested:write name="ExamAssignExaminerToExamForm"
											property="examinerListSize" />
									</c:set>
									<td>
									<div align="left"><input type="checkbox"
										name='selectcheckall' id="selectall"
										onclick="selectAll('<c:out value="${totalCount}" />')"><bean:message
										key="knowledgepro.exam.assignExaminerToExam.assignTeacher" /></div>
									</td>
									<td align="left" class="bodytext">
									<div align="left"><bean:message
										key="knowledgepro.usermanagement.userinfo.department" /></div>
									</td>
									<td align="left" class="bodytext">
									<div align="left"><bean:message
										key="knowledgepro.exam.assignExaminerToExam.firstName" /></div>
									</td>
									<td align="left" class="bodytext">
									<div align="left"><bean:message
										key="knowledgepro.exam.assignExaminerToExam.lastName" /></div>
									</td>
									
								</tr>
								<nested:iterate id="parent" name="ExamAssignExaminerToExamForm"
									property="examinerList" type="com.kp.cms.to.exam.EmployeeTO"
									indexId="count">
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

									<td>
									<div align="left"><logic:equal name="parent"
										property="empId" value="0">
										<input type="checkbox"
											value='<nested:write property="id" name="parent" />'
											id="checkbox_<c:out value="${count}" />" name='parent'
											onclick="setChkBoxValues('<nested:write property="id" name="parent" />') " />
									</logic:equal> <logic:notEqual name="parent" property="empId" value="0">
										<input type="checkbox" checked
											value='<nested:write property="id" name="parent" />'
											id="checkbox_<c:out value="${count}" />" name='parent'
											onclick="setChkBoxValues('<nested:write property="id" name="parent" />') " />
									</logic:notEqual></div>
									</td>
									<td>
									<div><nested:write property="department" name="parent" />
									</div>
									</td>
									<td>
									<div><nested:write property="firstName" name="parent" /></div>
									</td>
									<td>
									<div><nested:write property="lastName" name="parent" /></div>
									</td>
									
									</tr>
								</nested:iterate>
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
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35" align="right"><html:submit
								property="Submit" styleClass="formbutton" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="45%" align="left"><html:button property="Reset"
								styleClass="formbutton" value="Cancel" onclick="getBack()" /></td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
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