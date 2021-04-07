<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	function getSpecialization(subjectGroupId){
		getSpecializationBySubjectGroupWithoutCommSubjectAndSecondLang("specializationMap", subjectGroupId, "specialization", updateSpecialization);
	}
	function updateSpecialization(req) {
		updateOptionsFromMap(req, "specialization", "- Select -");
	}
	function getClasses(year) {
		getClassesByYear("classMap", year, "class", updateClasses);
		resetOption("subject");
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "class", "- Select -");
	}
	function getSubjectGroups(classSchemewiseId) {
		getSubjectGroupsByYearAndCourse("subjectGroupMap", classSchemewiseId,
				"subjectGroup", updateSubjectGroups);
	}
	function updateSubjectGroups(req) {
		updateOptionsFromMap(req, "subjectGroup", "- Select -");
	}
	function getPracticalBatch() {
		document.getElementById("className").value = document
				.getElementById("class").options[document
				.getElementById("class").selectedIndex].text;
		document.getElementById("method").value = "getStudentDetails";
		document.subjectGroupDetailsForm.submit();
	}

	function getSubjectGroupDetails() {
		document.getElementById("subjectGroupName").value = document
				.getElementById("subjectGroup").options[document
				.getElementById("subjectGroup").selectedIndex].text;
		document.getElementById("method").value = "addSubjectGroups";
		document.subjectGroupDetailsForm.submit();
		}
	function getMandatory(value) {
		getMandatoryFieldsByAttendanceType("activityMap", value,
				updateMandatory);
	}
	function updateMandatory(req) {
		updateOptionsFromMap(req, "activityId", "- Select -");

	}
	function editSubjectGroup(id){
		document.location.href="SubjectGroupDetails.do?method=editSubjectGroups&subjectGroupId="+id;
	}
	function updateSubjectGroupEntry() {
		document.getElementById("subjectGroupName").value = document
		.getElementById("subjectGroup").options[document
		.getElementById("subjectGroup").selectedIndex].text;
			document.getElementById("method").value = "updateSubjectGroup";
			document.subjectGroupDetailsForm.submit();
	}
	function resetFields() {
		document.getElementById("checked").selectedIndex = 0;
		resetErrMsgs();
	}
	function selectAll(obj) {
	    var value = obj.checked;
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox') {
	                  inputObj.checked = value;
	            }
	    }
	}
	function unCheckSelectAll() {
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxOthersSelectedCount = 0;
	    var checkBoxOthersCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox' && inputObj.id != "checkAll") {
	                  checkBoxOthersCount++;
	                  if(inputObj.checked) {
	                        checkBoxOthersSelectedCount++;
	                  }     
	            }
	    }
	    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
	      document.getElementById("checkAll").checked = false;
	    } else {
	      document.getElementById("checkAll").checked = true;
	    }        
	}

	function cancelPage(){
		document.location.href="SubjectGroupDetails.do?method=initSubjectGroup";
	}
</script>
<html:form action="/SubjectGroupDetails" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="subjectGroupDetailsForm" />
	<html:hidden property="className" styleId="className" value="" />
	<html:hidden property="subjectGroupName" styleId="subjectGroupName"	value="" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.attendance" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.attendance.subjectgroupdetails" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.attendance.subjectgroupdetails" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /> </span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									<td width="13%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									
									<td width="38%" class="row-even">
									<% boolean disable=false;%>
									<logic:equal value="true" name="subjectGroupDetailsForm" property="flag">
									<% disable=true;%>
									</logic:equal>
									<input type="hidden" id="yr" name="yr"
										value='<bean:write name="subjectGroupDetailsForm" property="year"/>' />
									<html:select property="year" styleClass="combo" styleId="year" disabled='<%=disable%>'
										onchange="getClasses(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td width="13%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.class.col" /></div>
									</td>
									<td width="38%" class="row-even"><input type="hidden"
										name="c1" id="c1"
										value='<bean:write name="subjectGroupDetailsForm" property="classId"/>' />
									<html:select property="classSchemewiseId"
										styleClass="comboLarge" styleId="class"  disabled='<%=disable %>'
										onchange="getSubjectGroups(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<c:choose>
											<c:when test="${classMap != null}">
												<html:optionsCollection name="classMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${subjectGroupDetailsForm.classSchemewiseId != null && subjectGroupDetailsForm.classSchemewiseId != ' '}">
													<c:set var="classMap"
														value="${baseActionForm.collectionMap['classMap']}" />
													<c:if test="${classMap != null}">
														<html:optionsCollection name="classMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center"><html:submit property=""
								styleClass="formbutton" value="Submit"
								onclick="getPracticalBatch()"></html:submit></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				
				<logic:present name="subjectGroupDetailsForm" property="subjectList">
					<tr>
						<td valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top">
						<div><FONT color="red" size="1">
						<bean:write name="subjectGroupDetailsForm" property="displayMessage"/><br>
						<bean:write name="subjectGroupDetailsForm" property="displayMsg"/>
						<bean:write name="subjectGroupDetailsForm" property="specializationMessage"/>
						</FONT></div>
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0">
							<tr>
								
								<td valign="top">
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
												<td width="5%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendanceentry.regno" /></div>
												</td>
												<td width="15%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendance.studentName" /></div>
												</td>
												<td width="15%" class="row-odd">
												<div align="center">Specialization </div>
												</td>
												<td width="5%" class="row-odd">
												SelectAll<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> 
												
												</td>
											</tr>
											<tr>
											<nested:iterate id="studentList" property="subjectList"
												name="subjectGroupDetailsForm" indexId="count">
												
												<!-- <tr>-->
													<c:if test="${count < subjectGroupDetailsForm.halfLength}">
													 <c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
													
													<td class="row-even">
														<div align="left"><bean:write property="registerNo"
															name="studentList" /></div>
														</td>
														<td class="row-even">
														<div align="left"><bean:write property="studentName"
															name="studentList" /></div>
														</td>
														<td class="row-even">
														<div align="left"><bean:write property="specializationName"
															name="studentList" /></div>
														</td>
														<td class="row-even">
														<input type="hidden" name="subjectList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																value="<nested:write name='studentList' property='tempChecked'/>" />
														
														<input type="checkbox" name="subjectList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
																<script type="text/javascript">
																var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																</script>
																</td></c:if></nested:iterate>
																</tr> 
											</table>
										</td>
										<td width="5" background="images/right.gif"></td>
									</tr>
									<tr>
										<td height="5"><img src="images/04.gif" width="5"
											height="5" /></td>
									<td background="images/05.gif"></td>
										 <td><img src="images/06.gif" /></td>
									</tr>
								</table>
								</td>
								<td valign="top" background="images/Tright_03_03.gif"></td>
								<td valign="top">
								<table width="100%" border="0" align="left" cellpadding="0"
									cellspacing="0">
									<tr>
										<td valign="top" background="images/Tright_03_03.gif"></td>
										<td valign="top">
														
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
												<td width="5%" class="row-odd">
														<div align="center"><bean:message
															key="knowledgepro.attendanceentry.regno" /></div>
														</td>
														<td width="15%" class="row-odd">
														<div align="center"><bean:message
															key="knowledgepro.attendance.studentName" /></div>
														</td>
														<td width="15%" class="row-odd">
														<div align="center">Specialization</div>
														</td>
														 <td width="5%" class="row-odd">
															Select
														</td>
													</tr>
												
										<c:set var="c" value="0"/>
											<nested:iterate id="studentList" property="subjectList"
														name="subjectGroupDetailsForm" indexId="count">		
												<c:set var="c" value="${c + 1}"/>											
											    
													<c:if test="${count >= subjectGroupDetailsForm.halfLength}">
													    <c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
															</c:when>
														<c:otherwise>
															<tr class="row-white">
															</c:otherwise>
					 										</c:choose>
																<td class="row-even">
																<div align="left"><bean:write
																property="registerNo" name="studentList" /></div>
																</td>
																<td class="row-even">
																<div align="left"><bean:write
																	property="studentName" name="studentList" /></div>
																</td>
																<td class="row-even">
																<div align="left"><bean:write property="specializationName"
																	name="studentList" /></div>
																</td>
																<td class="row-even">
																<input type="hidden" name="subjectList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																value="<nested:write name='studentList' property='tempChecked'/>" />
																<input type="checkbox" name="subjectList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>" onclick="unCheckSelectAll()" />
																<script type="text/javascript">
																
																var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																</script>	
																</td>
															</c:if>
														<!--  </tr> -->
													</nested:iterate>
													 <c:if test="${(c % 2) != 0}" >
                      									<!--<tr  class="row-white">
                       									 <td width="193"  >&nbsp;</td>
                       									<td width="212" >&nbsp;</td>
                        								<td height="25" align="center" >&nbsp;
                      									</td>
                     								 </tr>
                      								--></c:if>
												</table>
												</td>
												<td width="5" background="images/right.gif"></td>
											</tr>
											<tr>
												<td height="5"><img src="images/04.gif" width="5"
													height="5" /></td>
												<td background="images/05.gif"></td>
												 <td> <img src="images/06.gif" /></td>
											</tr>
										</table>
										</td>
									</tr>
									</table>
								</td>
							</tr>
						</table>
						</td><td valign="top" background="images/Tright_3_3.gif"></td>
						</tr>
				</logic:present>
				<tr>
				<logic:present  name="subjectGroupDetailsForm" property="subjectGroupMap">
					<tr>
						<td valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top">
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
										<td width="13%" height="25" class="row-odd">
										<div align="center"><span class="Mandatory">*</span><bean:message
											key="knowledgepro.attendance.subjectgroup.col" /></div>
										</td>
										<td width="38%" class="row-even">
										<% boolean disable1=false;%>
										<logic:equal value="true" name="subjectGroupDetailsForm" property="flag1">
										<% disable1=true;%>
										</logic:equal>
										<input type="hidden"
											name="s1" id="s1"
											value='<bean:write name="subjectGroupDetailsForm" property="subjectGroupId"/>' />
										<html:select property="subjectGroupId" styleClass="comboLarge" disabled='<%=disable1%>' onchange="getSpecialization(this.value)"
											styleId="subjectGroup">
											<html:option value="">
												<bean:message key="knowledgepro.select" />-</html:option>
															<html:optionsCollection name="subjectGroupDetailsForm" property="subjectGroupMap"
																label="subjectGroupName" value="subjectGroupId" />
										</html:select></td>
										<td width="13%" height="25" class="row-odd">
										<div align="center"><span class="Mandatory"></span>Specialization</div>
										</td>
										<td width="38%" class="row-even">
										<input type="hidden"
											name="s2" id="s2"
											value='<bean:write name="subjectGroupDetailsForm" property="specializationId"/>' />
										<html:select property="specializationId" styleClass="comboLarge" 
											styleId="specialization">
											<html:option value="">
												<bean:message key="knowledgepro.select" />-</html:option>
															
											<c:choose>
											<c:when test="${specializationMap != null}">
												<html:optionsCollection name="specializationMap" 
																label="value" value="key" />
											</c:when>
											<c:otherwise>
													<c:if test="${subjectGroupDetailsForm.specializationMap1 != null}">
														<html:optionsCollection name="subjectGroupDetailsForm" property="specializationMap1" label="value"
															value="key" />
													</c:if>
											</c:otherwise>
											</c:choose>
										</html:select></td>
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
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							
							<tr>
							<td width="45%" height="35">
							<div align="right">
							<c:choose>
								<c:when test="${SubjectGroupEntryOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateSubjectGroupEntry()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="getSubjectGroupDetails()"></html:button>
								</c:otherwise>
							</c:choose>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53"><c:choose>
								<c:when test="${SubjectGroupEntryOperation == 'edit'}">
									<html:button	property="" styleClass="formbutton"	onclick="cancelPage()">
									<bean:message key="knowledgepro.close" />
								</html:button>
								</c:when>
								<c:otherwise>
									<html:button	property="" styleClass="formbutton"	onclick="cancelPage()">
									<bean:message key="knowledgepro.close" />
								</html:button>
								</c:otherwise>
							</c:choose></td>
							</tr>
						
						</table>
						
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
				</logic:present>
					</tr>	
				<tr>
				<logic:present  name="subjectGroupDetailsForm" property="subjectGroupList">
				
				<tr><td height="19" valign="top" background="images/Tright_03_03.gif"></td>
                   <td height="35" colspan="6" >
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top">
	                       <table width="100%" cellspacing="1" cellpadding="2">
	                       <tr >
		                      	<td width="5%" height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
		                       <td width="10%" height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.attendance.subjectgroupname"/></div></td>
		                       <td width="6%" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/> </div></td>
	                       </tr>
	                       <c:set var="temp" value="0"/>
	                       <logic:iterate id="subject" name="subjectGroupDetailsForm" property="subjectGroupList" indexId="count">
		                       <c:choose>
	                           	 <c:when test="${temp == 0}">
	                           		<tr>
				                       <td height="25" class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
				                       
				                       <td class="row-even" ><div align="center"><bean:write name="subject" property="subjectGroupName"/></div></td>

				                       <td height="25" class="row-even" ><div align="center"><img src="images/edit_icon.gif" width="16" style="cursor:pointer" height="18" onclick="editSubjectGroup('<nested:write name="subject" property="subjectGroupId"/>')"></img></div></td>
	                               </tr>
	                      		   <c:set var="temp" value="1"/>
	                   		 	</c:when>
	                    	    <c:otherwise>
			                     <tr >
			                        <td height="25" class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
			                        
				                       <td class="row-even" ><div align="center"><bean:write name="subject" property="subjectGroupName"/></div></td>
				                       <td height="25" class="row-even" ><div align="center"><img src="images/edit_icon.gif" width="16" style="cursor:pointer" height="18" onclick="editSubjectGroup('<nested:write name="subject" property="subjectGroupId"/>')"></div></td>
			                     </tr>
	                     		 <c:set var="temp" value="0"/>
					  	       </c:otherwise>
	                        </c:choose>
	                      </logic:iterate>
	                   </table>
                   </td>
                       <td width="5" height="30"  background="images/right.gif"></td>
                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table></td>
                   <td valign="top" background="images/Tright_3_3.gif"></td>
                 </tr>	
				</logic:present>
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
<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var classId = document.getElementById("c1").value;
	if (classId != null && classId.length != 0) {
		document.getElementById("class").value = classId;
	}
</script>