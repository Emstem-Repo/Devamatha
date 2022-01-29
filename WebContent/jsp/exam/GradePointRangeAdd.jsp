<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap,java.util.List"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript">


function editGDAdd(id,courseId,appliedYear) {
	document.location.href = "GradePointRange.do?method=editGDAdd&id="+id+"&courseIds="+courseId+"&appliedYear="+appliedYear;
	document.getElementById("submit").value="Update";
	
	//resetErrMsgs();
}

function deleteGDAdd(id,courseId,appliedYear) {
	deleteConfirm =confirm("Are you sure to delete "+ name +" this entry?");
	if(deleteConfirm)
	{
	   document.location.href = "GradePointRange.do?method=deleteGDAdd&id="+id+"&appliedYear="+appliedYear+"&courseIds="+courseId;
	}
}

function isValidNumber(field) {
	if (isNaN(field.value)) {
		field.value = "";
	}
}


function resetMessages() {
	
	document.getElementById("startPercentage").value =document.getElementById("orgStartPercentage").value ;
	document.getElementById("endPercentage").value =document.getElementById("orgEndPercentage").value;
	document.getElementById("grade").value =document.getElementById("orgGrade").value ;
	document.getElementById("interpretation").value =document.getElementById("orgInterpretation").value;
	document.getElementById("resultClass").value =document.getElementById("orgResultClass").value;
	document.getElementById("gradePoint").value =document.getElementById("orgGradePoint").value;

	var name=document.getElementById("orgCourseid").value;
	name=name-1;
	
	document.getElementById("selectedCourse").selectedIndex =name;
	resetErrMsgs();
	
	
}

function reActivate(id) {
	document.location.href = "GradePointRange.do?method=reActivateGDAdd&id="+id;
}
function update(){
	document.getElementById("method").value="updateGDAdd";
}
function closeScreen(){
	document.location.href = "GradePointRange.do?method=initGradeDefinition";
}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/GradePointRange.do">
	<html:hidden property="formName" value="gradePointRangeForm" />
    <html:hidden property="pageType" value="2" />
    <html:hidden property="courseIds" styleId="courseIds" />
    
	<c:choose>
		<c:when
			test="${examGDOperation != null && examGDOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateGDAdd" />
			<html:hidden property="id" styleId="id" />
			<html:hidden property="orgCourseid" styleId="orgCourseid" />
			<html:hidden property="orgStartPercentage" styleId="orgStartPercentage" />
			<html:hidden property="orgEndPercentage" styleId="orgEndPercentage" />
			<html:hidden property="orgGrade" styleId="orgGrade" />
			<html:hidden property="orgInterpretation" styleId="orgInterpretation" />
			<html:hidden property="orgResultClass" styleId="orgResultClass" />
			<html:hidden property="orgGradePoint" styleId="orgGradePoint" />
			<html:hidden property="appliedYear" styleId="appliedYear" />
			
		
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addGDAdd" />
			<html:hidden property="appliedYear" styleId="appliedYear" />
		</c:otherwise>
	</c:choose>

	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><a href="main.html"
				class="Bredcrumbs"><bean:message key="knowledgepro.exam.exam" /></a>
			<span class="Bredcrumbs">&gt;&gt; GradePointRange Definition&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> GradePointRange Definition</strong></td>
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
									<td width="10%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.course" /> :</div>
									</td>
									<td width="17%" height="5" class="row-even">
									<table width="100%" >
									<nested:iterate name="gradePointRangeForm"
											property="listCourseName" id="listCourseName"
											type="com.kp.cms.to.exam.ExamCourseUtilTO">
										<tr>
									   			<nested:write name="listCourseName" property="display" />
									   			<br></br>
									</nested:iterate>
									</table>
									</td>
										<td width="10%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> Applied Year:</div>
									</td>
									<td height="24" class="row-even" align="left">
												<bean:write property="appliedYear" name="gradePointRangeForm"/>
											</td>
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
							<table width="100%" cellspacing="1" cellpadding="2">


								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Start GradePoint :</div>
									</td>
									<td width="25%" height="25" class="row-even"><html:text
										property="startPercentage" styleId="startPercentage"
										maxlength="6" styleClass="TextBox" size="20"
										onblur="isValidNumber(this)" /></td>
									<td width="28%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span></span>End GradePoint :</div>
									</td>
									<td width="26%" class="row-even"><html:text
										property="endPercentage" styleId="endPercentage" maxlength="6"
										styleClass="TextBox" size="20" onblur="isValidNumber(this)" /></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"></span><bean:message
										key="knowledgepro.exam.gradeDefinition.grade" />:</div>
									</td>
									<td height="25" class="row-even"><html:text
										property="grade" styleId="grade" maxlength="3"
										styleClass="TextBox" size="20"  /></td>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.gradeDefinition.interpretation" /></div>
									</td>
									<td class="row-even">
									<html:text
										property="interpretation" styleId="interpretation"
										maxlength="50" styleClass="TextBox" size="20"/>
									</td>
								</tr>
								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.gradeDefinition.resultClass" /> :</div>
									</td>
									<td width="25%" height="25" class="row-even"><html:text
										property="resultClass" styleId="resultClass"
										maxlength="50" styleClass="TextBox" size="20"/></td>
									<td width="28%" class="row-odd">
									<div align="right"></span><bean:message
										key="knowledgepro.exam.gradeDefinition.gradePoint" /> :</div>
									</td>
									<td width="26%" class="row-even"><html:text
										property="gradePoint" styleId="gradePoint" maxlength="20"
										styleClass="TextBox" size="20" onblur="isValidNumber(this)"/></td>
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>

							<td width="49%" height="35" align="right">
							<c:choose>
								<c:when
									test="${examGDOperation != null && examGDOperation == 'edit'}">

									<input name="submit" type="submit" class="formbutton"
										value="Update" />
								</c:when>
								<c:otherwise>
									<input name="submit" type="submit" class="formbutton"
										value="Add" />
								</c:otherwise>
							</c:choose></td>
							<td width="2%" align="center">&nbsp;</td>
							<td width="49%" align="left">
							<c:choose>
								<c:when
									test="${examGDOperation != null && examGDOperation == 'edit'}">
									<html:cancel styleClass="formbutton" onclick="update()">
											Reset
										</html:cancel>
								</c:when>
								<c:otherwise>
									<input type="Reset"
										class="formbutton" value="Reset" onclick="resetErrMsgs()"/>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
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
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td width="5%" height="25" class="row-odd" align="left">Applied Year</td>
											<td width="30%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.gradeDefinition.course" /></td>
											<td width="5%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.gradeDefinition.grade" /> </td>
											<td width="20%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.gradeDefinition.interpretation" /> </td>
												
											<td width="10%" height="25" class="row-odd" align="left" ><bean:message
												key="knowledgepro.exam.gradeDefinition.resultClass" /> </td>	
											<td width="10%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.gradeDefinition.gradePoint" /></td>
											<td width="7%" height="25" class="row-odd" align="left">Start GradePoint</td>
											<td width="6%" height="25" class="row-odd" align="left">End GradePoint </td>
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
										<logic:iterate name="gradePointRangeForm"
											property="courseListForGradeDefinition" id="listGradeDefinition"
											type="com.kp.cms.to.exam.GradePointRangeTO"
											indexId="count">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td  height="25" class="row-even">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td  height="25" class="row-even"
															align="left"><bean:write name="listGradeDefinition"
															property="appliedYear" /></td>
														<td  height="25" class="row-even"
															align="left"><bean:write name="listGradeDefinition"
															property="course" /></td>
														<td  height="25" class="row-even"
															align="left"><bean:write name="listGradeDefinition"
															property="grade" /></td>
														<td  height="25" class="row-even"
															align="left"><bean:write name="listGradeDefinition"
															property="interpretation" /></td>
															
														<td  height="25" class="row-even"
															align="left"><bean:write name="listGradeDefinition"
															property="resultClass" /></td>
														<td  height="25" class="row-even"
															align="left"><bean:write name="listGradeDefinition"
															property="gradePoint" /></td>	
														<td  height="25" class="row-even"
															align="left"><bean:write name="listGradeDefinition"
															property="startPercentage" /></td>
														<td  height="25" class="row-even"
															align="left"><bean:write name="listGradeDefinition"
															property="endPercentage" /></td>
														<td height="25" class="row-even">
														<div align="center"><img src="images/edit_icon.gif"
															width="16" height="18" style="cursor: pointer"
															onclick="editGDAdd('<bean:write name="listGradeDefinition" property="id"/>','<bean:write name="listGradeDefinition" property="courseId"/>','<bean:write name="listGradeDefinition" property="appliedYear"/>')">
														</div>
														</td>
														<td height="25" class="row-even">
														<div align="center"><img
															src="images/delete_icon.gif" width="16" height="16"
															style="cursor: pointer"
															onclick="deleteGDAdd('<bean:write name="listGradeDefinition" property="id"/>','<bean:write name="listGradeDefinition" property="courseId"/>','<bean:write name="listGradeDefinition" property="appliedYear"/>')"></div>
														</td>
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td height="25" class="row-white">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
															<td  height="25" class="row-even"
															align="left"><bean:write name="listGradeDefinition"
															property="appliedYear" /></td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="listGradeDefinition"
															property="course" /></td>

														<td  height="25" class="row-white"
															align="left"><bean:write name="listGradeDefinition"
															property="grade" /></td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="listGradeDefinition"
															property="interpretation" /></td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="listGradeDefinition"
															property="resultClass" /></td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="listGradeDefinition"
															property="gradePoint" /></td>	
														<td  height="25" class="row-white"
															align="left"><bean:write name="listGradeDefinition"
															property="startPercentage" /></td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="listGradeDefinition"
															property="endPercentage" /></td>
														<td height="25" class="row-white">
														<div align="center"><img src="images/edit_icon.gif"
															width="16" height="18" style="cursor: pointer"
															onclick="editGDAdd('<bean:write name="listGradeDefinition" property="id"/>','<bean:write name="listGradeDefinition" property="courseId"/>','<bean:write name="listGradeDefinition" property="appliedYear"/>')">
														</div>
														</td>
														<td height="25" class="row-white">
														<div align="center"><img
															src="images/delete_icon.gif" width="16" height="16"
															style="cursor: pointer"
															onclick="deleteGDAdd('<bean:write name="listGradeDefinition" property="id"/>','<bean:write name="listGradeDefinition" property="courseId"/>','<bean:write name="listGradeDefinition" property="appliedYear"/>')"></div>
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
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="51%" height="45" align="center"><html:button
										property="" styleClass="formbutton"
										onclick="closeScreen()">
										<bean:message key="knowledgepro.close" />
									</html:button></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif"></td>
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