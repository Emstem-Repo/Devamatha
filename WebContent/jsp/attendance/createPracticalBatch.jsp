<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	function getClasses(year) {
		getClassesByYear("classMap", year, "class", updateClasses);
		resetOption("subject");
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "class", "- Select -");
	}

	function getSubjects(classSchemewiseId) {
		getSubjectsByClass("subjectMap", classSchemewiseId, "subject",
				updateSubjects);
	}
	function updateSubjects(req) {
		updateOptionsFromMap(req, "subject", "- Select -");
	}
	function getPracticalBatch() {
		document.getElementById("className").value = document
				.getElementById("class").options[document
				.getElementById("class").selectedIndex].text;
		document.getElementById("subjectName").value = document
				.getElementById("subject").options[document
				.getElementById("subject").selectedIndex].text;
		document.getElementById("attTypeName").value = document
				.getElementById("attendanceTypeId").options[document
				.getElementById("attendanceTypeId").selectedIndex].text;
		document.getElementById("activityName").value = document
				.getElementById("activityId").options[document
				.getElementById("activityId").selectedIndex].text;
		document.getElementById("method").value = "getPracticalBatchDetails";
		document.createPracticalBatchForm.submit();
	}
	function getMandatory(value) {
		getMandatoryFieldsByAttendanceType("activityMap",value,updateMandatory);
	}
	function updateMandatory(req) {
		updateOptionsFromMap(req,"activityId","- Select -");	
	}
</script>
<html:form action="/CreatePracticalBatch" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="createPracticalBatchForm" />
	<html:hidden property="className" styleId="className" value="" />
	<html:hidden property="subjectName" styleId="subjectName" value="" />
	<html:hidden property="activityName" styleId="activityName" value="" />
	<html:hidden property="attTypeName" styleId="attTypeName" value="" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.attendance" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.attendance.createpracticalbatch" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.attendance.createpracticalbatch" /></td>
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
									<td width="10%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td width="20%" class="row-even"><input type="hidden" 
										id="yr" name="yr"
										value='<bean:write name="createPracticalBatchForm" property="year"/>' />
									<html:select property="year" styleClass="combo" styleId="year"
										onchange="getClasses(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td width="10%" height="25" class="row-odd"> 
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.class.col" /></div>
									</td>
									<td width="20%" class="row-even"><input type="hidden"
										name="c1" id="c1"
										value='<bean:write name="createPracticalBatchForm" property="classId"/>' /> 
									<html:select property="classSchemewiseId" styleClass="comboLarge"
										styleId="class" onchange="getSubjects(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<c:choose>
											<c:when test="${classMap != null}">
												<html:optionsCollection name="classMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${createPracticalBatchForm.classSchemewiseId != null && createPracticalBatchForm.classSchemewiseId != ''}">
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
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory"></span><bean:message
										key="knowledgepro.admin.mail.subject" /></div>
									</td>
									<td width="34%" class="row-even"><html:select style="width:275px"
										property="subjectId" styleClass="comboLarge" styleId="subject"> 
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<c:choose>
											<c:when test="${subjectMap != null}">
												<html:optionsCollection name="subjectMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${createPracticalBatchForm.subjectId != null && createPracticalBatchForm.subjectId != ''}">
													<c:set var="subjectMap"
														value="${baseActionForm.collectionMap['subjectMap']}" />
													<c:if test="${subjectMap != null}">
														<html:optionsCollection name="subjectMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>
								</tr>
								<tr>
								 <td height="25" class="row-odd">
				                  <div align="right"><span class='MandatoryMark'></span><bean:message key="knowledgepro.attendanceentry.type"/>:</div>
				                  </td>
				                  <td class="row-even" >
				                  <html:select style="width:175px" property="attendanceTypeId" styleId="attendanceTypeId" name="createPracticalBatchForm" styleClass="combo" onchange="getMandatory(this.value)">
				                  	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
				                  	<logic:notEmpty name="createPracticalBatchForm" property="attendanceTypes">
				                  	<html:optionsCollection name="createPracticalBatchForm" property="attendanceTypes" label="value" value="key"/>
				                  	</logic:notEmpty>
				                  </html:select>
				                  </td>
								 <td class="row-odd" >
                 					 <div align="right"><bean:message key="knowledgepro.attendanceentry.activitytype"/>:</div> 
				                  </td>  <!-- added div tag priyatham -->
				                  <td class="row-even" >
				                  <html:select style="width:175px" name="createPracticalBatchForm" property="activityId" styleId="activityId" styleClass="comboMediumLarge">
				                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
				                    <logic:notEmpty name="createPracticalBatchForm" property="activityMap">
				                    		<html:optionsCollection name="createPracticalBatchForm" property="activityMap" label="value" value="key"/>
				                    		</logic:notEmpty>
				                    </html:select></td>
				                  <td class="row-odd"></td>
				                  <td class="row-even"></td>
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
							<td height="35" align="center"><html:button property=""
								styleClass="formbutton" value="Next"
								onclick="getPracticalBatch()"></html:button></td>
						</tr>
					</table>
					</td>
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