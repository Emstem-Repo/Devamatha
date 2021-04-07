<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>


<script type="text/javascript">



function editSubjectSectionMaster(subId) {

	document.getElementById("messages").value="";
	var courseId=document.getElementById("courseId").value;
	document.location.href = "ExamSubjectDefinitionCourseWise.do?method=editSubjectInfo&subId="+subId+"&courseId="+courseId;
	
	
}

//SubjectSection
function addSubjectSectionMaster() {
	document.getElementById("method").value="addSubjectSectionMasterEntry";
}



function updateSubjectSectionMaster() {
	document.getElementById("method").value = "updateSubjectSectionMaster"; 
}


function cancelAction(){
	document.location.href = "ExamSubjectDefinitionCourseWise.do?method=initExamSubDefCourseWise";
}



</script>

<html:form action="/ExamSubjectDefinitionCourseWise.do" method="POST">

	<html:hidden property="formName" value="ExamSubjectDefCourseForm" />
	<html:hidden property="pageType" value="1" />
     <html:hidden property="courseId" styleId="courseId"/>
	

	<html:hidden property="pageType" value="2" />
	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.subjectDefinitionCourseWise" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.subjectSectionMaster" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				               
				<tr>
					<td height="44" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6">
							<div align="right" style="color: red"><span
								class='MandatoryMark'>* Mandatory fields</span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT></div>
							<FONT color="green" id="messages"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT>
						</tr>
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
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" />:</div>
									</td>
                                     <td width="16%" class="row-even"><bean:write
												name="ExamSubjectDefCourseForm" property="academicYear_value" />
											</td>
									<td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.course" />:</div>
                   					</td>
                                      <td width="16%" height="25" class="row-even"><bean:write
												name="ExamSubjectDefCourseForm" property="courseName" /></td>
                                  </tr>
                                   <tr>
                                    <td width="16%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admission.scheme.col" /></div>
									</td>
                                     <td width="16%" class="row-even"><bean:write
												name="ExamSubjectDefCourseForm" property="schemeName" />
										</td>	
                                     <td width="10%" class="row-even"></td>
                                       
									
									
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
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td width="100%" height="20" colspan="4">
							
							</td>
						</tr>

						<tr>
							<td height="45" colspan="4">
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
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.subjectsDefinition.subjectCode" /></td>
											<td height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.subjectsDefinition.subjectName" /></td>
											<td height="25" class="row-odd" align="left">Subject Order</td>
											<td height="25" class="row-odd" align="left">Theory Credit</td>
											<td height="25" class="row-odd" align="left">practical Credit</td>
											<td height="25" class="row-odd" align="left">Subject Section</td>
											<td class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.edit" /></div>
											</td>
											
										</tr>
										                                  
										<logic:iterate name="ExamSubjectDefCourseForm"
										   property="listSubjects" id="listOfSubjectSection"
										   type="com.kp.cms.to.exam.ExamSubDefinitionCourseWiseTO"
											indexId="count">
											<tr>
														<td height="25" class="row-even">
										                <div align="center"><c:out value="${count+1}" /></div>
										                </td>
														<td width="20%" height="25" class="row-even"
															align="left"><bean:write
															name="listOfSubjectSection" property="subjectCode" />
                                                            <html:hidden property="subjectCode" name="listOfSubjectSection" />
                                                             
                                                        </td>
														<td width="30%" height="25" class="row-even"
															align="left"><bean:write
															name="listOfSubjectSection" property="subjectName" />
                                                        <html:hidden property="subjectName" name="listOfSubjectSection" />
                                                        </td>
                                                        <td width="10%" height="25" class="row-even"
															align="left"><bean:write
															name="listOfSubjectSection" property="subjectOrder" />
                                                        </td>
                                                        <td width="10%" height="25" class="row-even"
															align="left"><bean:write
															name="listOfSubjectSection" property="theoryCredit" />
                                                        </td>
                                                        <td width="10%" height="25" class="row-even"
															align="left"><bean:write
															name="listOfSubjectSection" property="practicalCredit" />
                                                        </td>
                                                        <td width="20%" height="25" class="row-even"
															align="left"><bean:write
															name="listOfSubjectSection" property="subjectSection" />
                                                        </td>
														<td width="10%" height="25" class="row-even">
														<div align="center"><img src="images/edit_icon.gif"
															width="16" height="18" style="cursor: pointer"
															onclick="editSubjectSectionMaster('<bean:write name="listOfSubjectSection" property="subjectId"/>')"></div>
														</td>
											  </tr>	
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
							<div align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
								<tr>									
									<td   align="center"><html:button property=""
										styleClass="formbutton" onclick="cancelAction()">
										Cancel
										</html:button> 
									</td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="1"></td></tr>								
							</table>
							</div>
							</td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
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