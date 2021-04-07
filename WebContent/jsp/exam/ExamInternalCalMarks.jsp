<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript">

function editICMMaster(id) {
	document.location.href = "ExamInternalCalMarks.do?method=editICM&id="+id;
	//resetErrMsgs();
}

function deleteICMMaster(id,name) {
	deleteConfirm =confirm("Are you sure to delete "+ name +" this entry?");
	if(deleteConfirm)
	{
	document.location.href = "ExamInternalCalMarks.do?method=deleteICM&id="+id;
	}
}

function isValidNumber(field) {
	if (isNaN(field.value)) {
		field.value = "";
	}
}

function reActivate(id) {
	document.location.href = "ExamInternalCalMarks.do?method=reActivateICMaster&id="+id;
}

function resetMessages() {
	document.location.href = "ExamInternalCalMarks.do?method=initICMMaster";	
	
}
function resetOrgMessages() {
	resetErrMsgs();
	document.getElementById("startPercentage").value =document.getElementById("orgStartPercentage").value;
	document.getElementById("endPercentage").value =document.getElementById("orgEndPercentage").value ;
	document.getElementById("marks").value =document.getElementById("orgMarks").value ;
	document.getElementById("theoryPractical").value =document.getElementById("orgTheoryPractical").value;
	document.getElementById("selectedCourse").value =document.getElementById("orgSelectedCourse").value;		
	
}

</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<body>
<html:form action="/ExamInternalCalMarks.do" styleId="myform">

	<html:hidden property="formName"
		value="ExamInternalCalculationMarksForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
		<c:when test="${examICOperation != null && examICOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateICM" />
			<html:hidden property="id" styleId="id" />

			<html:hidden property="orgStartPercentage"
				styleId="orgStartPercentage" />
			<html:hidden property="orgEndPercentage" styleId="orgEndPercentage" />
			<html:hidden property="orgMarks" styleId="orgMarks" />
			<html:hidden property="orgTheoryPractical"
				styleId="orgTheoryPractical" />
			<html:hidden property="orgSelectedCourse" styleId="orgSelectedCourse" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addICM" />
			<html:hidden property="orgSelectedCourse" styleId="orgSelectedCourse" />
		</c:otherwise>
	</c:choose>

	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.InternalCalculationMarksRange" />
			&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.exam.InternalCalculationMarksRange" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
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
									<td width="46%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.course" /></div>
									</td>
									<td width="54%" height="25" colspan="3" class="row-even">


									<nested:select property="selectedCourse" styleClass="body"
										multiple="multiple" size="8" styleId="selectedCourse"
										style="width:500px">
										<nested:optionsCollection
											name="ExamInternalCalculationMarksForm"
											property="listExamCourseUtilTO" label="display" value="id" />
									</nested:select></td>

									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.theoryProctical" /></div>
									</td>
									<td class="row-even"><html:select
										property="theoryPractical" styleId="theoryPractical">
										<html:option value="">Select</html:option>
										<html:option value="Theory">Theory</html:option>
										<html:option value="Practical">Practical</html:option>
										<html:option value="Theory and Practical">Theory and Practical</html:option>
									</html:select></td>
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
							<table width="99%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="15%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.startPercentage" /> %:</div>
									</td>
									<td width="15%" height="25" class="row-even"><html:text
										property="startPercentage" styleId="startPercentage"
										maxlength="6" styleClass="TextBox" size="20"
										onkeypress="return isDecimalNumberKey(this.value,event)"
										onblur="isValidNumber(this)" /></td>
									<td width="18%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.endPercentage" /> %:</div>
									</td>
									<td width="14%" class="row-even"><html:text
										property="endPercentage" styleId="endPercentage" maxlength="6"
										styleClass="TextBox" size="20"
										onkeypress="return isDecimalNumberKey(this.value,event)"
										onblur="isValidNumber(this)" /></td>
									<td width="17%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.marks" /> :</div>
									</td>
									<td width="21%" class="row-even"><html:text
										property="marks" styleId="marks" maxlength="9"
										styleClass="TextBox" size="20"
										onkeypress="return isDecimalNumberKey(this.value,event)"
										onblur="isValidNumber(this)" /></td>
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
						<tr><td style="height: 10px" align="center" colspan="6"></td></tr>
						<tr>
							<c:choose>
								<c:when
									test="${examICOperation != null && examICOperation == 'edit'}">
									<td width="44%" height="35">
									<div align="right"><input name="submit" type="submit"
										class="formbutton" value="Update" /></div>
									</td>
									<td width="2%"></td>
									<td width="54%"><input type="button" class="formbutton"
										value="Reset" onclick="resetOrgMessages()" /></td>
								</c:when>
								<c:otherwise>
									<td width="44%" height="35">
									<div align="right"><input name="submit" type="submit"
										class="formbutton" value="Add" /></div>
									</td>
									<td width="2%"></td>
									<td width="54%"><input type="button" class="formbutton"
										value="Reset" onclick="resetMessages()" /></td>
								</c:otherwise>
							</c:choose>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="6"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>



				<tr>
					<td height="45" colspan="4">
					<table width="98%" border="0" align="center" cellpadding="0"
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
									<td height="25" class="row-odd" align="left"><bean:message
										key="knowledgepro.exam.course" /></td>
									<td height="25" class="row-odd" align="left"><bean:message
										key="knowledgepro.exam.startPercentage" /> %</td>
									<td height="25" class="row-odd" align="left"><bean:message
										key="knowledgepro.exam.endPercentage" /> %</td>
									<td height="25" class="row-odd" align="left"><bean:message
										key="knowledgepro.exam.marks" /></td>

									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:iterate name="ExamInternalCalculationMarksForm"
									property="examICMList" id="examICMList"
									type="com.kp.cms.to.exam.ExamInternalCalculationMarksTO"
									indexId="count">

									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="8%" height="25" class="row-even">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="40%" height="25" class="row-even" align="left"><bean:write
													name="examICMList" property="programTypeProgramCourse" /></td>
												<td width="10%" height="25" class="row-even" align="left"><bean:write
													name="examICMList" property="startPercentage" /></td>
												<td width="10%" height="25" class="row-even" align="left"><bean:write
													name="examICMList" property="endPercentage" /></td>
												<td width="10%" height="25" class="row-even" align="left"><bean:write
													name="examICMList" property="marks" /></td>
												<td width="10%" height="25" class="row-even">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor: pointer"
													onclick="editICMMaster('<bean:write name="examICMList" property="id"/>')">
												</div>
												</td>
												<td width="10%" height="25" class="row-even">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor: pointer"
													onclick="deleteICMMaster('<bean:write name="examICMList" property="id"/>','<bean:write name="examICMList" property="programTypeProgramCourse"/>')"></div>
												</td>
											</tr>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-white">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td height="25" class="row-white" align="left"><bean:write
													name="examICMList" property="programTypeProgramCourse" /></td>
												<td height="25" class="row-white" align="left"><bean:write
													name="examICMList" property="startPercentage" /></td>
												<td height="25" class="row-white" align="left"><bean:write
													name="examICMList" property="endPercentage" /></td>
												<td height="25" class="row-white" align="left"><bean:write
													name="examICMList" property="marks" /></td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor: pointer"
													onclick="editICMMaster('<bean:write name="examICMList" property="id"/>')">
												</div>
												</td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor: pointer"
													onclick="deleteICMMaster('<bean:write name="examICMList" property="id"/>','<bean:write name="examICMList" property="programTypeProgramCourse"/>')"></div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
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
</body>