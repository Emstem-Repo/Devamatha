<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function getSubjectList(deptId)
{
	getSubjectByDepartment("subjectMap", deptId, "subjectIds", updateSubjectMap);
}
function updateSubjectMap(req)
{
	updateOptionsFromMap(req, "subjectIds", "- Select -");
}
function getTeacherForSubject(departmentID)
{
	//var subjects =  document.getElementById("subjectIds");
	//var selectedSubjects = new Array();
	//var subjIds;
	//count=0;
	//for (var i=0; i<subjects.options.length; i++) {
	//    if (subjects.options[i].selected) {
	//   	selectedSubjects[count] = subjects.options[i].value;
	//     count++;
	//    }
	// }
	//subjIds = selectedSubjects.toString();
	getTeacherBySubject("userMap", departmentID, "userId", updateUserMap);
}
function updateUserMap(req)
{
	updateOptionsFromMap(req, "userId", "- Select -");
}
function reActivate()
{
	document.location.href = "AssignCocurricularSubjectTeacher.do?method=activateCocuricularSubjectTeacher";
}
function deleteCocurricularTeacher(techerId) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm) {
		document.location.href = "AssignCocurricularSubjectTeacher.do?method=deleteCocurricularTeacher&id="+techerId;
				
	}
}
function editCocurricularTeacher(id)
{
	document.location.href = "AssignCocurricularSubjectTeacher.do?method=editCocurricularTeacher&id="+id;
}
function resetFormFields()
{
	document.location.href = "AssignCocurricularSubjectTeacher.do?method=initAssignCocurricularSubjectTeacher";
}
</script>
<html:form action="/AssignCocurricularSubjectTeacher">	
	
	<html:hidden property="formName" value="assignCocurricularSubjectTeacherForm" />
	<html:hidden property="pageType" value="1" />
    <c:choose>
		<c:when test="${stateOperation != null && stateOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateCocurricularSubjectTeacher" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="saveCocurricularSubjectTeacher" />	
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.attendance.assign.cocurricular.subject.teachers.label"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.attendance.assign.cocurricular.subject.teachers.label"/> Entry</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
							 <!--  Start Form Fields -->
								<tr>
								
								<td height="25" align="right" class="row-odd"><div align="right">
									<span class="Mandatory">*</span><bean:message  key="knowledgepro.attendance.assign.cocurricular.approver.label" />
									</div></td>
									<td height="25" align="left" class="row-even"><div align="left">
									<html:select property="teacherID" styleId="userId"
										styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection name="assignCocurricularSubjectTeacherForm"
											property="userMap" label="value" value="key" />
									</html:select>
									</div></td>
									
									
									<td height="25" align="right" class="row-odd"><div align="right">
									<span class="Mandatory">*</span><bean:message  key="knowledgepro.extra.cur.act.entry.activityname" />
									</div></td>
									<td height="25" align="left" class="row-even"><div align="left">
									<html:select property="activityIds" styleId="activityIds"  
										styleClass="" size="8" style="width:450px" multiple="multiple">
										
											<logic:notEmpty name="assignCocurricularSubjectTeacherForm"
											property="activityMap">
											<html:optionsCollection property="activityMap"
												name="assignCocurricularSubjectTeacherForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select>
										
									</div></td>
								</tr>
                                
                                
  
  
                               


								<!-- End Form Fields -->
							
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>	
						<tr>
							<td width="45%" height="35">
							<div align="right">
						
								<c:choose>
										<c:when
											test="${stateOperation != null && stateOperation == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="submitbutton">
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton">
											</html:submit>
										</c:otherwise>
									</c:choose>
							
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>							
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td width="7%" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											
											<td width="18%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.attendance.assign.cocurricular.approver.label" /></div>
											</td>
											<td width="18%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.extra.cur.act.entry.activityname" /></div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message
										key="knowledgepro.edit" /></div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<logic:iterate id="TeacherList" name="assignCocurricularSubjectTeacherForm" property="assignCocurricularSubjectTeacherTOList"
											indexId="count">
									<tr>
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td height="25">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
									
										<td align="center"><bean:write name="TeacherList"
											property="userName" /></td>
										<td align="center"><bean:write name="TeacherList"
											property="activityNames" /></td>
										<td height="25"  align="center">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor: pointer"
											onclick="editCocurricularTeacher('<bean:write name="TeacherList" property="id"/>')"></div>
										</td>

										<td height="25">
										<div align="center"><img src="images/delete_icon.gif"
											width="16" height="16"
											onclick="deleteCocurricularTeacher('<bean:write name="TeacherList" property="id" />')" /></div>
										</td>
									</tr>
								</logic:iterate>
										
										</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>			
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

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
