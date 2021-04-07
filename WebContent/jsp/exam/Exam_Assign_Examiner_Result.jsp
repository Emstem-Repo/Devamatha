<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">
	checked = false;

	var examinersSelected = "";

	function selectAll(totalCount) {
		examinersSelected = "";
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
	function setChkBoxValues(id) {
		var ar = new Array;
		if (document.getElementById("id").checked == true) {
			ar.push(document.getElementById("id").value);
			examinersSelected = examinersSelected
					+ document.getElementById("id").value + ",";
		}

		document.getElementById("examiner").value = examinersSelected;

	}

	function deleteValue(id, name) {
		deleteConfirm = confirm("Are you sure to delete " + name
				+ " this entry?");
		if (deleteConfirm) {
			document.location.href = "AssignExaminer.do?method=deleteAssignExaminer&id="
					+ id;
		}
	}

	function getValues() {
		var myVal = "";
		var myValDummy = "";
		var checkTickedRadioIsUnSelected = "";
		var invDutyDetailsListSize = document
				.getElementById("invDutyDetailsListSize").value;
		var invigilatorSize = document.getElementById("invigilatorSize").value;
		var selectBol = new Boolean(false);
		for ( var i = 0; i < invDutyDetailsListSize; i++) {

			if (document.getElementById("checkbox_" + i).checked == true) {
				var flag = 0;
				for ( var j = 0; j < invigilatorSize; j++) {
					if (document.getElementById("radio_" + i + "_" + j).checked == true) {
						//	selectBol = true;
						var name = document.getElementById("hidden_" + i + "_"
								+ j).value;
						var b = new Array();
						b = name.split("_");
						var roomNo = "roomNo_" + i;
						var remarks = "remarks_" + i;
						myVal = myVal
								+ document.getElementById("checkbox_" + i).value
								+ "#" + b[1] + "#"
								+ document.getElementById(roomNo).value + "#"
								+ document.getElementById(remarks).value + "#"
								+ b[0] + "#";
						flag = 1;

					}

				}
				if (flag == 0) {
					checkTickedRadioIsUnSelected = "yes";
				}

			} else {

				for ( var j = 0; j < invigilatorSize; j++) {
					if (document.getElementById("radio_" + i + "_" + j).checked == true) {
						//	selectBol = true;
						var name = document.getElementById("hidden_" + i + "_"
								+ j).value;
						var b = new Array();
						b = name.split("_");
						var roomNo = "roomNo_" + i;
						var remarks = "remarks_" + i;
						myValDummy = myValDummy
								+ document.getElementById("checkbox_" + i).value
								+ "#" + b[1] + "#"
								+ document.getElementById(roomNo).value + "#"
								+ document.getElementById(remarks).value + "#"
								+ b[0] + "#";

					}

				}
			}
		}
		document.getElementById("listValues").value = myVal.toString();
		document.getElementById("listValuesDummy").value = myValDummy
				.toString();
		document.getElementById("checkValidation").value = checkTickedRadioIsUnSelected;
	}

	function checkedAll() {
		if (checked == false) {
			checked = true;
		} else {
			checked = false;
		}
		var sizeVal = document.getElementById("invDutyDetailsListSize").value;
		for ( var i = 0; i < sizeVal; i++) {
			document.getElementById("checkbox_" + i).checked = checked;
		}
	}

	function resetValues() {
		document.location.href = "AssignExaminer.do?method=initExamAssignExaminer";
	}
</script>

<html:form action="AssignExaminer" method="post" styleId="myform">
	<html:hidden property="formName" value="ExamAssignExaminerForm" />
	<html:hidden property="method" styleId="method"
		value="updateAssignExaminer" />
	<html:hidden property="invDutyDetailsListSize"
		styleId="invDutyDetailsListSize" />
	<html:hidden property="invigilatorSize" styleId="invigilatorSize" />

	<html:hidden property="pageType" value="2" />

	<html:hidden property="listValues" styleId="listValues" />
	<html:hidden property="checkValidation" styleId="checkValidation" />

	<html:hidden property="listValuesDummy" styleId="listValuesDummy" />

	<html:hidden property="examiner" styleId="examiner" />
	<html:hidden property="date" styleId="date" />
	<html:hidden property="examNameId" styleId="examNameId" />
	<html:hidden property="invigilatorId" styleId="invigilatorId" />
	<html:hidden property="hr" styleId="hr" />
	<html:hidden property="min" styleId="min" />

	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.assignExaminer" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.exam.assignExaminer" /></strong></div>
					</td>
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
								<tr>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.examDefinition.examName" /> :</div>
									</td>
									<td width="20%" class="row-even"><bean:write
										property="exName" name="ExamAssignExaminerForm" /></td>



									<td width="14%" height="26" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.assignExaminer.date" /> :</div>
									</td>
									<td width="15%" class="row-even"><bean:write
										property="date" name="ExamAssignExaminerForm" /></td>

									<td width="8%" height="26" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.assignExaminer.time" /> :</div>
									</td>
									<td width="11%" class="row-even"><bean:write property="hr"
										name="ExamAssignExaminerForm" />&nbsp;:&nbsp; <bean:write
										property="min" name="ExamAssignExaminerForm" /></td>

								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.assignExaminer.defaultInvDuty" /></div>
									</td>
									<td class="row-even" colspan="6"><bean:write
										property="invigilatorName" name="ExamAssignExaminerForm" /></td>
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
								<tr>
									
									<td class="row-odd">
									<div align="center">Select all <br>
									<input type='checkbox' name='selectcheckall'
										onClick=
	checkedAll();;;;;;;;;;
/></div>
									</td>
									<td class="row-odd">SI.No </td>
									<td class="row-odd"><bean:message
										key="knowledgepro.admin.sec.Department" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.assignExaminer.examiners" /></td>
									<logic:iterate name="ExamAssignExaminerForm"
										property="invigilatorList" id="invigilatorList"
										type="com.kp.cms.to.exam.KeyValueTO">
										<td class="row-odd"><bean:write name="invigilatorList"
											property="display" /></td>
									</logic:iterate>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.roomno" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.assignExaminer.remarks" /></td>



								</tr>
								<nested:iterate name="ExamAssignExaminerForm"
									property="invDutyListMain" id="invDutyListMain"
									type="com.kp.cms.to.exam.InvDutyDetailsTO" indexId="count">

									<%
										String dynamicStyle = "";
												if (count % 2 != 0) {
													dynamicStyle = "row-white";

												} else {
													dynamicStyle = "row-even";

												}
									%>
									<tr>
									
										<td class='<%=dynamicStyle%>'>
										<div align="center"><logic:greaterThan value="0"
											name="invDutyListMain" property="id">

											<logic:equal value="reset" property="mode"
												name="ExamAssignExaminerForm">
												<input type="checkbox" name="examiner"
													id="checkbox_<c:out value='${count}'/>"
													value='<nested:write name="invDutyListMain" property="id"  />' />
											</logic:equal>

											<logic:notEqual value="reset" property="mode"
												name="ExamAssignExaminerForm">
												<input type="checkbox" name="examiner"
													id="checkbox_<c:out value='${count}'/>"
													value='<nested:write name="invDutyListMain" property="id"  />'
													checked="checked" />
											</logic:notEqual>

										</logic:greaterThan> <logic:equal value="0" name="invDutyListMain" property="id">
											<input type="checkbox" name="examiner"
												id="checkbox_<c:out value='${count}'/>"
												value='<nested:write name="invDutyListMain" property="id"  />' />
										</logic:equal></div>
										</td>
										<td class='<%=dynamicStyle%>'><%=count+1 %> </td>
										<td class='<%=dynamicStyle%>'><nested:write
											name="invDutyListMain" property="departementName" /></td>
										<td class='<%=dynamicStyle%>'><nested:write
											name="invDutyListMain" property="examinerName" /></td>


										<nested:notEmpty property="listInvigilator">
											<nested:iterate id="invList" property="listInvigilator"
												name="invDutyListMain"
												type="com.kp.cms.to.exam.ExaminerDutiesTO" indexId="count1">


												<td class='<%=dynamicStyle%>'><input type="hidden"
													name="expectedVia_<nested:write name="invList" property="employeeId"  />"
													id="hidden_<c:out value='${count}'/>_<c:out value='${count1}'/>"
													value='<nested:write name="invDutyListMain" property="assignExaminerExamId"  />_<nested:write name="invList" property="invDutyTypeId"  />' />
												<logic:equal name="invList" property="display" value="on">
													<input type="radio" checked
														name="expectedVia_<nested:write name="invList" property="employeeId"  />"
														id="radio_<c:out value='${count}'/>_<c:out value='${count1}'/>" />
												</logic:equal> <logic:equal name="invList" property="display" value="off">
													<input type="radio"
														name="expectedVia_<nested:write name="invList" property="employeeId"  />"
														id="radio_<c:out value='${count}'/>_<c:out value='${count1}'/>" />
												</logic:equal></td>


											</nested:iterate>

										</nested:notEmpty>






										<td class='<%=dynamicStyle%>'><input name="textfield"
											type="text" size="8" id="roomNo_<c:out value='${count}'/>"
											value='<nested:write name="invDutyListMain" property="roomNo" />'></td>
										<td class='<%=dynamicStyle%>'><input name="textfield"
											type="text" size="8" id="remarks_<c:out value='${count}'/>"
											value='<nested:write name="invDutyListMain" property="remarks"  />'
											onblur="isAlphaNumber(this.value)"></td>
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
							<td width="45%" height="35" align="right"><input
								name="Submit2" type="submit" class="formbutton" value="Submit"
								onclick=
	getValues();;;;;;;;;;
/></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="45%" align="left"><input type="button"
								class="formbutton" value="Cancel"
								onclick=
	resetValues();;;;;;;;;;;
/></td>
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
								<tr>
									<td height="25" align="center" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" />.</div>
									</td>
									<td height="25" align="center" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.admin.sec.Department" /></div>
									</td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.assignExaminer.examiners" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.assignExaminer.invigilatorDuty" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.roomno" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.assignExaminer.remarks" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.delete" /></td>
								</tr>

								<c:set var="temp" value="0" />


								<logic:iterate name="ExamAssignExaminerForm"
									property="listAssignExaminer" id="listAssignExaminer"
									type="com.kp.cms.to.exam.ExamAssignExaminerTO" indexId="count">

									<%
										String dynamicStyle = "";
												if (count % 2 != 0) {
													dynamicStyle = "row-white";

												} else {
													dynamicStyle = "row-even";

												}
									%>
									<tr>
										<td class='<%=dynamicStyle%>'><c:out value="${count+1}" /></td>
										<td class='<%=dynamicStyle%>'><bean:write
											name="listAssignExaminer" property="departementName" /></td>
										<td class='<%=dynamicStyle%>'><bean:write
											name="listAssignExaminer" property="examinerName" /></td>
										<td class='<%=dynamicStyle%>'><bean:write
											name="listAssignExaminer" property="invigilatorType" /></td>
										<td class='<%=dynamicStyle%>'><bean:write
											name="listAssignExaminer" property="roomNo" /></td>
										<td class='<%=dynamicStyle%>'><bean:write
											name="listAssignExaminer" property="remarks" /></td>
										<td class='<%=dynamicStyle%>'>
										<div align="center"><img src="images/delete_icon.gif"
											width="16" height="16" style="cursor: pointer"
											onclick="deleteValue('<bean:write name="listAssignExaminer" property="id"/>','<bean:write name="listAssignExaminer" property="examinerName"/>')"></div>
										</td>
									</tr>

								</logic:iterate>





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