<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function getPrograms(ProgramTypeId) {
	getProgramsByType("programMap", ProgramTypeId, "program", updatePrograms);
	resetOption("course");
}

function updatePrograms(req) {
	updateOptionsFromMap(req, "program", "- Select -");
}

function getCourses(programId) {
	getCoursesByProgram("courseMap", programId, "course", updateCourses);
}
function updateCourses(req) {
	updateOptionsFromMap(req, "course", "- Select -");
}
function editInterviewDefinition(id) {
	document.location.href = "InterviewDefinition.do?method=editInterviewDefinition&id=" + id;
}
function deleteInterviewDefinition(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm) {
		document.location.href = "InterviewDefinition.do?method=deleteInterviewDefinition&id=" + id;
	}
}
function updateInterviewDefinition() {
	document.getElementById("courseName").value = document.getElementById("course").options[document.getElementById("course").selectedIndex].text;
	document.getElementById("method").value = "updateInterviewDefinition";
	document.interviewDefinitionForm.submit();
}
function addInterviewDefinition() {
	document.getElementById("courseName").value = document.getElementById("course").options[document.getElementById("course").selectedIndex].text;
	document.getElementById("method").value = "addInterviewDefinition";
	document.interviewDefinitionForm.submit();
}
function reActivate() {
	var id = document.getElementById("id").value;
	document.location.href = "InterviewDefinition.do?method=activateInterviewDefinition&id="+ id;
}
function imposeMaxLength(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 2000;
	return (Object.value.length < MaxLen);
}
function viewInterviewCardDef(id) {
	var url = "InterviewDefinition.do?method=viewInterviewDefinition&id=" + id;
	myRef = window
			.open(url, "viewContent",
					"left=20,top=20,width=600,height=500,toolbar=1,resizable=0,scrollbars=1");
}
function resetMessages() {
	document.getElementById("programType").selectedIndex = 0;
	document.getElementById("program").selectedIndex = 0;
	document.getElementById("course").selectedIndex = 0;
	document.getElementById("interviewType").value = "";
	document.getElementById("sequence").selectedIndex = 0;
	document.getElementById("content").value = "";
	document.getElementById("inteperPanel").value = "";
	resetErrMsgs();
}

function getInterviewList(year){
	document.location.href = "InterviewDefinition.do?method=getInterviewDefinitionList&year="+ year;
}
</script>	
<html:form action="/InterviewDefinition">
	<c:choose>
		<c:when test="${conditionsOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value = "updateInterviewDefinition"/>
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addInterviewDefinition" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="interviewDefinitionForm" />
	<html:hidden property="courseName" styleId="courseName" value="" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.interviewdefinition" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.admission.interviewdefinition" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage">
						<FONT color="red"><html:errors /></FONT>
						<FONT color="green"><html:messages id="msg" property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages>
						</FONT>
					</div>
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td width="100%" height="10"></td>
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
											<td width="10%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.programtype" />:</div>
											</td>
											<td width="20%" height="25" class="row-even">
											<html:select property="programTypeId" styleClass="combo" styleId="programType" onchange="getPrograms(this.value)" >
												<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>									
           				    					<html:optionsCollection property="programTypeList" label="programTypeName" value="programTypeId"/>
											</html:select>
											</td>
											<td width="10%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.program" />:</div>
											</td>
											<td width="20%" class="row-even">
											<html:select property="programId" styleClass="combo" styleId="program" onchange="getCourses(this.value)" >
												<html:option value=" "><bean:message key="knowledgepro.select" /></html:option>
												<c:choose>
													<c:when test="${conditionsOperation == 'edit'}">
														<c:if test="${programMap != null}">
															<html:optionsCollection name="programMap" label="value" value="key" />
														</c:if>
													</c:when>
													<c:otherwise>
				                       			    	<c:if test="${interviewDefinitionForm.programTypeId != null && interviewDefinitionForm.programTypeId != ''}">
				                           					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
			                            		    	 	<c:if test="${programMap != null}">
			                            		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
			                            		    	 	</c:if>	 
				                           		   		</c:if>
				                           		   	</c:otherwise>
			                           		   	</c:choose>	
											</html:select>
											</td>
											<td width="10%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.course" />:</div>
											</td>
											<td width="30%" class="row-even">
											<html:select property="courseId" styleClass="combo" styleId="course">
												<html:option value=" "><bean:message key="knowledgepro.select" /></html:option>
													<c:choose>
														<c:when test="${conditionsOperation == 'edit'}">
															<c:if test="${courseMap != null}">
																<html:optionsCollection name="courseMap" label="value" value="key" />
															</c:if>	
														</c:when>
														<c:otherwise>
															<c:if test="${interviewDefinitionForm.programId != null && interviewDefinitionForm.programId != ''}">
																<c:set var="coursesMap" value="${baseActionForm.collectionMap['courseMap']}" />
																<c:if test="${coursesMap != null}">
																	<html:optionsCollection name="coursesMap" label="value" value="key" />
																</c:if>
															</c:if>
														</c:otherwise>
													</c:choose>
											</html:select>
											</td>
										</tr>
										<tr>
											<td width="10%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.year" />:</div>
											</td>
											<td width="20%" height="25" class="row-even">
											<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="interviewDefinitionForm" property="year"/>" />
											<html:select name="interviewDefinitionForm" property="year" styleId="year" styleClass="combo" onchange="getInterviewList(this.value)">
												<html:option value=" ">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
											</html:select></td>
											<td width="10%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admission.interviewType" />:</div></td>
											<td width="20%" class="row-even"><span
												class="star"><html:text property="interviewType" styleId="interviewType" styleClass="TextBox" size="16" maxlength="30"></html:text></span></td>
											<td width="10%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.sequence" />:</div></td>
											<td width="30%" class="row-even"><html:select property="sequence" styleId="sequence" styleClass="combo">
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:option value="1">1</html:option>
												<html:option value="2">2</html:option>
												<html:option value="3">3</html:option>
												<html:option value="4">4</html:option>
												<html:option value="5">5</html:option>
												<html:option value="6">6</html:option>
												<html:option value="7">7</html:option>
												<html:option value="8">8</html:option>
												<html:option value="9">9</html:option>
												<html:option value="10">10</html:option>
											</html:select></td>
										</tr>
										
										<tr>
											<td width="10%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.interviewersperpanel"/>:</div>
											</td>
											<td width="20%" class="row-even"><span class="star">
												<html:text property="interviewsPerPanel" styleId="inteperPanel" styleClass="TextBox" size="8" maxlength="2"/>
												</span>
											</td>
											<td width="10%" class="row-odd">&nbsp;</td>
											<td width="20%" class="row-even">&nbsp;</td>
											<td width="10%" class="row-odd">&nbsp;</td>
											<td width="30%" class="row-even">&nbsp;</td>
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
							<td height="10" class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right">
									<c:choose>
										<c:when test="${conditionsOperation == 'edit'}">
											<html:button property="" styleClass="formbutton"
												value="Update" onclick="updateInterviewDefinition()"></html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Submit" onclick="addInterviewDefinition()"></html:button>
										</c:otherwise>
									</c:choose>
									</div>
									</td>
									<td width="2%"></td>
									<td width="53%">
									<c:choose>
										<c:when test="${conditionsOperation == 'edit'}">
											<html:cancel styleClass="formbutton"><bean:message key="knowledgepro.admin.reset" /></html:cancel>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton" onclick="resetMessages()"><bean:message key="knowledgepro.admin.reset" /></html:button>
										</c:otherwise>
									</c:choose>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="99">
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
											<td width="4%" height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td width="11%" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.admin.programtype" /></div></td>
											<td  width="10%" class="row-odd"><div align="center"><bean:message
												key="knowledgepro.admin.program" /></div></td>
											<td width="10%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.admin.course" /></div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admin.year" />
											</div>	
											</td>
											<td width="12%" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.admission.interviewType" /></div>
											</td>
											<td width="12%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.admission.sequence" /></div>
											</td>
											<td width="12%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.admission.interviewersperpanel"/></div>
											</td>
											<td width="6%" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.edit" /></div>
											</td>
											<td width="6%" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<logic:iterate name="interviewDefinitionForm"
											property="interviewDefinitionList" id="iDefinitionList"
											indexId="count">
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<bean:define id="year1" property="year" name="iDefinitionList" type="java.lang.Integer"></bean:define>
											<% year1= year1.intValue(); %>
													<td height="25"><div align="center"><c:out value="${count+1}" /></div>
													</td>
													<td><div align="center"><bean:write
														name="iDefinitionList" property="programTypeName" /></div></td>
													<td><div align="center"><bean:write
														name="iDefinitionList" property="programName" /></div></td>
													<td><div align="center"><bean:write
														name="iDefinitionList" property="courseName" /></div></td>
													<td><div align="center">
														<bean:write name="iDefinitionList" property="year" />-<%=year1+1 %></div>
													</td>
													<td><div align="center"><bean:write
														name="iDefinitionList" property="name" /></div></td>
													<td><div align="center"><bean:write
														name="iDefinitionList" property="sequence" /></div>
													</td>
													<td><div align="center"><bean:write
														name="iDefinitionList" property="interviewsPerPanel" /></div>
													</td>
													<td><div align="center"><img src="images/edit_icon.gif" style="cursor:pointer"
														width="16" height="18" onclick="editInterviewDefinition('<bean:write name="iDefinitionList" property="id"/>')"></div>
													</td>
													<td><div align="center"><img src="images/delete_icon.gif" style="cursor:pointer"
													 width="16" height="16" onclick="deleteInterviewDefinition('<bean:write name="iDefinitionList" property="id"/>')"></div>
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
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
					<div align="center">
					<table width="100%" height="27" border="0" cellpadding="0"
						cellspacing="0">
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
</script>