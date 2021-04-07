<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>

<script type="text/javascript">

	function getPrograms(ProgramTypeId) {
		getProgramsByType("programMap", ProgramTypeId, "programId",
				updatePrograms);
		resetOption("courseId");
		var destination = document.getElementById("selectedSubjects");
		for (x1=destination.options.length-1; x1>=0; x1--) {
			destination.options[x1]=null;
		}
		
		document.getElementById("programTypeId").value
	}
	
	function updatePrograms(req) {
		updateOptionsFromMap(req, "programId", "- Select -");
	}
	
	function getCourse(programId) {
		getCoursesByProgram("courseMap", programId, "courseId", updateCourse);
		var destination = document.getElementById("selectedSubjects");
		for (x1=destination.options.length-1; x1>=0; x1--) {
			destination.options[x1]=null;
		}		
	}
	function updateCourse(req) {
		updateOptionsFromMap(req, "courseId", " Select ");
	}

	function getSubjects(courseId) {
		getSubjectsByCourseForMultiSelect("subjectMap", courseId, "selectedSubjects", updateSubjects);
	}
	function updateSubjects(req) {
		updateOptionsFromMapForMultiSelect(req, "selectedSubjects");
	}

	function updateCriteria() {
		resetErrMsgs();
		document.getElementById("method").value = "updateCriteria";
		document.eligibilityCriteriaForm.submit();
	}

	function addCriteria() {
		document.getElementById("method").value = "addCriteria";
		document.eligibilityCriteriaForm.submit();
	}

	function loadCriteria(id) {
		document.location.href = "Eligibilitycriteria.do?method=loadCriteria&id="
				+ id;
	}

	function deleteCriteria(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "Eligibilitycriteria.do?method=deleteCriteria&id="	+ id;
		}
	}
	function resetCriteria() {
		document.getElementById("programTypeId").value = "";
		document.getElementById("programId").value = "";
		document.getElementById("courseId").value = "";
		document.getElementById("percentageWithoutLanguage").value = "";
		document.getElementById("totalPercentage").value = "";
		var destination = document.getElementById("selectedSubjects");
		for (x1=destination.options.length-1; x1>=0; x1--) {
			destination.options[x1]=null;
		}
		resetOption("programId");
		resetOption("courseId");
		resetErrMsgs();
	}	

	function clearField(field) {
		if (field.value == "0.0")
			field.value = "";
	}
	function checkForEmpty(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
	function updateMandatory(field){
		if(field.selectedIndex != -1) {
			document.getElementById("perWithoutMandatory").value = "yes";
			document.getElementById("per").innerHTML = "<span class='MandatoryMark'>*</span>Total % Without Language:";			
		}
		else
		{
		   document.getElementById("perWithoutMandatory").value = "no";
			document.getElementById("per").innerHTML = "Total % Without Language:";			
		}
	}
	
</script>


<html:form action="/Eligibilitycriteria" method="post">
	<c:choose>
		<c:when test="${criteriaOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateCriteria" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addCriteria" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="eligibilityCriteriaForm" />
	<html:hidden property="perWithoutMandatory" styleId="perWithoutMandatory" value="" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="pageType" value="1" />
	<table width="98%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.eligibility.criteria.entry"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admin.eligibility.criteria.entry"/></strong></div>
					</td>

					<td width="13"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
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
					<td height="70" valign="top" background="images/Tright_03_03.gif"></td>

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
							<td width="21%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.program.type" /></div>
							</td>
							<td width="10%" height="25" class="row-even">
							<input type="hidden"
								name="prgTypeId" id="prgTypeId"
								value='<bean:write name="eligibilityCriteriaForm" property="programTypeId"/>' />

							<label><html:select
								property="programTypeId" styleClass="comboLarge"
								styleId="programTypeId" onchange="getPrograms(this.value)">
								<html:option value="">
									<bean:message key="knowledgepro.select" />
								</html:option>
								<cms:renderProgramTypes></cms:renderProgramTypes>
							</html:select></label><span class="star"></span></td>
							<td width="25%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.prog" /></div>
							</td>
							<td width="30%" class="row-even"><span class="star">
							
							<html:select name="eligibilityCriteriaForm" property="programId"
								styleId="programId" styleClass="comboLarge"
								onchange="getCourse(this.value)">
								<html:option value="">- Select -</html:option>
								<c:choose>
									<c:when test="${criteriaOperation == 'edit'}">
										<c:if
											test="${eligibilityCriteriaForm.programTypeId != null && eligibilityCriteriaForm.programTypeId != ''}">
											<html:optionsCollection name="programMap" label="value"
												value="key" />
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if
											test="${eligibilityCriteriaForm.programTypeId != null && eligibilityCriteriaForm.programTypeId != ''}">
											<c:set var="programMap"
												value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</c:otherwise>
								</c:choose>
							</html:select> </span></td>
							<%--<td height="25" valign="top" class="row-even">
								<div align="right">&nbsp;</div>
							</td>--%>
							</tr>
						<tr>
							<td height="25" valign="top" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.course.with.col" /></div>
							</td>
							<td height="25" valign="top" class="row-even"><html:select
								name="eligibilityCriteriaForm" property="courseId" styleId="courseId"
								styleClass="comboLarge" onchange="getSubjects(this.value)">
								<html:option value="">- Select -</html:option>
								<c:choose>
									<c:when test="${criteriaOperation == 'edit'}">
										<c:if
											test="${eligibilityCriteriaForm.programId != null && eligibilityCriteriaForm.programId != ''}">
											<html:optionsCollection name="courseMap" label="value"
												value="key" />
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if
											test="${eligibilityCriteriaForm.programId != null && eligibilityCriteriaForm.programId != ''}">
											<c:set var="courseMap"
												value="${baseActionForm.collectionMap['courseMap']}" />
											<c:if test="${courseMap != null}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:if>
										</c:if>
									</c:otherwise>
								</c:choose>
							</html:select></td>
							<td class="row-odd" >
								<div align="right"><span class="Mandatory">*</span><bean:message key = "knowledgepro.fee.academicyear.col"/></div>
							</td>
							<td class="row-even"><input type="hidden" id="tempyear"
								name="tempyear"
								value="<bean:write name="eligibilityCriteriaForm" property="year"/>" /><html:select
								name="eligibilityCriteriaForm" property="year" styleId="year"
								styleClass="combo">
								<html:option value="">- Select -</html:option>
								<cms:renderYear></cms:renderYear>
							</html:select></td>
						</tr>

							<tr>
								<td height="25" valign="top" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.admin.eligibility.criteria.subjects"/></div>
								</td>
								<td>   
								<nested:select property="selectedSubjects" styleClass="body" multiple="multiple" size="15" styleId="selectedSubjects" style="width:300px" onchange="updateMandatory(this)">
									<logic:notEmpty name = "eligibilityCriteriaForm" property="subjectMap" >           
										<nested:optionsCollection name="eligibilityCriteriaForm" property="subjectMap" label="value" value="key" styleClass="comboBig"/>
									</logic:notEmpty>
								</nested:select>
								</td>
								 <td height="25" valign="top" class="row-odd">
									<div align="right">&nbsp;</div>
								</td>
								<td height="25" valign="top" class="row-even">
									<div align="right">&nbsp;</div>
								</td>
							</tr>

							</table>


							<table width="100%" cellspacing="1" cellpadding="2">

							<tr>
								<td valign="top" class="row-odd" width="14%">
									<div align="right"><bean:message key="knowledgepro.admin.eligibility.criteria.total.per.col"/></div>
								</td>
								<td width="25%" height="25" class="row-even" valign="top"><span
									class="star"> <html:text property="totalPercentage"
									styleClass="TextBox" styleId="totalPercentage" size="15"
									maxlength="5" onkeypress="return isDecimalNumberKey(this.value,event)"
									onkeyup="onlyTwoFractions(this,event)"
									onfocus="clearField(this)" onblur="checkForEmpty(this)"/> </span></td>
								
								<td valign="top" class="row-even" width="16%">
									<font class="heading">
									<div align="center"><bean:message key="knowledgepro.admin.or"/></div></font>
								</td>
								<td  valign="top" class="row-odd" width="22%">
								<c:choose>
			                  	<c:when test="${eligibilityCriteriaForm.perWithoutMandatory == 'yes'}">
			                  		<div id="per" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.admin.eligibility.criteria.total.per.wihout.lan"/></div>
			                    </c:when>
								<c:otherwise>
									<div  id="per" align="right"><bean:message key="knowledgepro.admin.eligibility.criteria.total.per.wihout.lan"/></div>
								</c:otherwise>
								</c:choose>
								</td>
								<td width="30%" height="25" class="row-even"><span
									class="star"> <html:text property="percentageWithoutLanguage"
									styleClass="TextBox" styleId="percentageWithoutLanguage" size="15"
									maxlength="5" onkeypress="return isDecimalNumberKey(this.value,event)"
									onkeyup="onlyTwoFractions(this,event)"
									onfocus="clearField(this)" onblur="checkForEmpty(this)"/> </span></td>
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

				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>

							<td width="45%" height="35">
							<div align="right"><c:choose>
								<c:when test="${criteriaOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateCriteria()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addCriteria()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><c:choose>
								<c:when test="${criteriaOperation == 'edit'}">
									<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetCriteria()"></html:button>
								</c:otherwise>
							</c:choose></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>


				<tr>
					<td height="97" valign="top" background="images/Tright_03_03.gif"></td>
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
							<table width="100%" height="86" border="0" cellpadding="0"
								cellspacing="1">
								<tr class="row-odd">
									<td width="64" class="bodytext">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td  align="center" class="bodytext"><bean:message key = "knowledgepro.admin.programtype"/></td>
									<td  align="center" width="100" height="25" class="bodytext"><strong><bean:message key = "knowledgepro.admin.program"/></strong></td>
									<td  align="center" width="163" height="25" class="bodytext">
									<div align="center"><bean:message key = "knowledgepro.admin.course"/></div>
									</td>
									<td  align="center" width="163" height="25" class="bodytext">
									<div align="center"><bean:message key = "knowledgepro.admin.academicyear"/></div>
									</td>
									<td  align="center" width="129" class="bodytext">
									<div align="center"><bean:message key = "knowledgepro.admin.eligibility.subjects"/></div>
									</td>
									<td  align="center" width="129" class="bodytext">
									<div align="center">Total %</div>
									</td>
									<td  align="center" width="129" class="bodytext">
									<div align="center"><bean:message key = "knowledgepro.admin.eligibility.tot.per.wihout.lan"/></div>
									</td>
									<td>
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td>
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>


								<logic:iterate id="criteriaList" name="criteriaList" indexId="count">
									<bean:define id="year1" property="year" name="criteriaList" type="java.lang.Integer"></bean:define>
									<% year1= year1.intValue(); %>
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
									<td width="13%" align="center" class="bodytext"><bean:write name="criteriaList"
										property="courseTO.programTo.programTypeTo.programTypeName" /> 
									</td>
									<td width="13%" align="center" class="bodytext"><bean:write name="criteriaList"
										property="courseTO.programTo.name" /> 
									</td>
									<td width="13%" align="center" class="bodytext"><bean:write name="criteriaList"
										property="courseTO.name" />
									</td>
									<td width="13%" align="center" class="bodytext"><bean:write name="criteriaList"
										property="year" /> - <%=year1+1 %>
									</td>
									<td>
										<table width="100%" cellspacing="1" cellpadding="2">
											<logic:iterate id = "eligibleSubjectsTOList" name = "criteriaList" property="eligibleSubjectsTOList">
												<tr>
												<td align="center" class="bodytext"><bean:write name="eligibleSubjectsTOList" property="detailedSubjectsTO.subjectName" /></td>
												</tr>
											</logic:iterate>
										</table>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="criteriaList"
										property="totalPercentage" /></div>
									</td>

									<td align="center" class="bodytext"><bean:write name="criteriaList"
										property="percentageWithoutLanguage" /></td>
									<td width="50" height="24">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor:pointer"
										onclick="loadCriteria('<bean:write name="criteriaList" property="id"/>')">

									</div>
									</td>
									<td width="54" height="24">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
										onclick="deleteCriteria('<bean:write name="criteriaList" property="id"/>')">
									</div>
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
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>

				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>

<script type="text/javascript">
	var programType = document.getElementById("prgTypeId").value;
	if (programType.length != 0) {
		document.getElementById("programTypeId").value = programType;
	}
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
</script>