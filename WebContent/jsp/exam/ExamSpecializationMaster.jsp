<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript" src="js/common.js"></script>

<script type="text/javascript">

function editSPLMaster(courseID, courseName, specializationId) {
	document.location.href = "ExamSpecialization.do?method=editSMaster&id="+courseID;
	document.getElementById("submitbutton").value="Update";

}


function deleteSPLMaster(id,name) {
	deleteConfirm =confirm("Are you sure to delete "+ name +" this entry?");
	if(deleteConfirm)
	{
		document.location.href = "ExamSpecialization.do?method=deleteSPLM&id="+id;
	}
}
function reActivate(id) {
		document.location.href = "ExamSpecialization.do?method=reActivateSMaster&id="+id;
}
function resetMessages() {

	
	document.getElementById("specializationName").value =document.getElementById("orgName").value;
	document.getElementById("courseId").selectedIndex =document.getElementById("orgCourseId").value;
	resetErrMsgs();
}
function resetErrMssgs(id) {
	
	document.location.href = "ExamSpecialization.do?method=initSpecializationMaster&id="+id;
}

</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamSpecialization.do">

	<html:hidden property="formName" value="ExamSpecializationMasterForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${examSMOperation != null && examSMOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateSMaster" />
			
			<html:hidden property="orgName" styleId="orgName" />
			<html:hidden property="orgCourseName" styleId="orgCourseName"  />
			<html:hidden property="orgCourseId" styleId="orgCourseId"  />
			<html:hidden property="id" styleId="id" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addSMaster" />
		</c:otherwise>
	</c:choose>

	<table width="100%" border="0">
		<tr>
			<td class="Bredcrumbs"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.specializationMaster" />
			&gt;&gt;</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.specializationMaster" /> </strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'>* Mandatory Fields</span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>

						<tr>
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

											<td width="16%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.course" /></div>
											</td>
											<td width="32%" height="25" class="row-even"><label>
											<html:select property="courseId" styleClass="combo"
												styleId="courseId" name="ExamSpecializationMasterForm"
												style="width:200px">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<logic:notEmpty name="ExamSpecializationMasterForm"
													property="courseList">
													<html:optionsCollection property="courseList"
														name="ExamSpecializationMasterForm" label="display"
														value="id" />
												</logic:notEmpty>
											</html:select> </label> <span class="star"></span></td>
											<td width="17%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.exam.specialization" /></div>
											</td>
											<td width="35%" class="row-even">
											<div align="left"><span class="star"> <html:text
												property="specializationName" styleClass="TextBox"
												styleId="specializationName" size="26" maxlength="50" /> </span></div>
											</td>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>

								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

						<tr>
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
								<tr>
									<td width="45%" height="35" align="right">
									<c:choose>
										<c:when
											test="${examSMOperation != null && examSMOperation == 'edit'}">
											<input name="submit" type="submit" class="formbutton"
												value="Update" />
										</c:when>
										<c:otherwise>
											<input name="submit" type="submit" class="formbutton"
												value="Submit" />

										</c:otherwise>
									</c:choose></td>
									<td width="2%"></td>
									<td width="53%"><c:choose>
										<c:when
											test="${examSMOperation != null && examSMOperation == 'edit'}">
											<html:button property=""
										styleClass="formbutton" styleId="reset" onclick="resetMessages()">
										<bean:message key="knowledgepro.admin.reset" /></html:button></c:when>
									<c:otherwise>
									<html:button property=""
										styleClass="formbutton" onclick="resetErrMssgs();"
										styleId="reset">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button>
									</c:otherwise></c:choose>
									</td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
							</table>
							</td>
						</tr>

						<tr>
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
											<td height="25" colspan="4">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td height="25" class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.slno" /></div>
													</td>
													<td height="25" class="row-odd" align="left"><bean:message
														key="knowledgepro.exam.course" /></td>
													<td height="25" class="row-odd">
													<div align="left"><bean:message
														key="knowledgepro.exam.specialization" /></div>
													</td>

													<td class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.edit" /></div>
													</td>
													<td class="row-odd">
													<div align="center"><bean:message
														key="knowledgepro.delete" /></div>
													</td>
												</tr>
												<c:set var="temp" value="0" />
												<logic:iterate name="ExamSpecializationMasterForm"
													property="listOfSpecialization" id="listOfSpecialization"
													type="com.kp.cms.to.exam.ExamSpecializationTO"
													indexId="count">
													<c:choose>
														<c:when test="${temp == 0}">
															<tr>
																<td width="8%" height="25" class="row-even">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td width="31%" height="25" class="row-even"
																	align="left"><bean:write
																	name="listOfSpecialization" property="course" /></td>
																<td width="41%" height="25" class="row-even"
																	align="left"><bean:write
																	name="listOfSpecialization" property="name" /></td>
																<td width="11%" height="25" class="row-even">
																<div align="center"><img
																	src="images/edit_icon.gif" width="16" height="18"
																	style="cursor: pointer"
																	onclick="editSPLMaster('<bean:write name="listOfSpecialization" property="id"/>')">
																</div>
																</td>
																<td width="9%" height="25" class="row-even">
																<div align="center"><img
																	src="images/delete_icon.gif" width="16" height="16"
																	style="cursor: pointer"
																	onclick="deleteSPLMaster('<bean:write name="listOfSpecialization" property="id"/>','<bean:write name="listOfSpecialization" property="name"/>')"></div>
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
																	name="listOfSpecialization" property="course" /></td>
																<td height="25" class="row-white" align="left"><bean:write
																	name="listOfSpecialization" property="name" /></td>
																<td height="25" class="row-white">
																<div align="center"><img
																	src="images/edit_icon.gif" width="16" height="18"
																	style="cursor: pointer"
																	onclick="editSPLMaster('<bean:write name="listOfSpecialization" property="id"/>')">
																</div>
																</td>
																<td height="25" class="row-white">
																<div align="center"><img
																	src="images/delete_icon.gif" width="16" height="16"
																	style="cursor: pointer"
																	onclick="deleteSPLMaster('<bean:write name="listOfSpecialization" property="id"/>','<bean:write name="listOfSpecialization" property="name"/>')"></div>
																</td>
															</tr>
															<c:set var="temp" value="0" />
														</c:otherwise>
													</c:choose>
												</logic:iterate>
											</table>
											</td>
										</tr>

									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>

								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

					</table>
					<div align="center"></div>
					</td>

					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>

			</table>
			</td>
		</tr>
	</table>
</html:form>