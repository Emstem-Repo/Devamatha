<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<script type="text/javascript">
function getPrograms(programType,destId) {
	destID = destId;
	getProgramsByType("programMap",programType,destId,updateProgram);
	resetOption("coursePref1");
}

function updateProgram(req) {
	updateOptionsFromMap(req,destID,"-Select-"); 
}

function getCourse(program,destId) {
	destID=destId;
	getCoursesByProgram("coursesMap",program,destId,updateCourse);	
}

function updateCourse(req) {
	updateOptionsFromMap(req,destID,"-Select-"); 
}
function resetFields(){
	document.getElementById("programTypeId").selectedIndex = 0;
	document.getElementById("progPref1").selectedIndex = 0;
	document.getElementById("coursePref1").selectedIndex = 0;
	resetErrMsgs();
}
</script>	
</head>
<html:form action="/admissionDataReUpload" enctype="multipart/form-data">
	<html:hidden property="method" value="reUpdateCSV" />
	<html:hidden property="formName" value="admissionDataReUploadForm" />
	<html:hidden property="pageType" value="1" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="admissionForm.admissiondatareupload.label" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="admissionForm.admissiondatareupload.label" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage">
							<FONT color="red">
							<c:if test="${ duplicateNumberMap != null && duplicateNumberMap != '' }">
								<logic:iterate id="duplicate" name="duplicateNumberMap" scope="request">
									<logic:notEmpty name="duplicate" property="value" >
									<bean:write name="duplicate" property="key"/>
										<logic:iterate id="duplicateNumber" name="duplicate" property="value">
											<bean:write name="duplicateNumber"/>	
										</logic:iterate>
									<br/>
									</logic:notEmpty>	
								</logic:iterate>
							</c:if>
							<html:errors/>
							</FONT>
							<FONT color="green">
								<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out>
								</html:messages>
							</FONT>
							</div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
					                <td width="10%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
					                <td width="20%" class="row-even">
					                <input type="hidden" id="programType" name="programType" value='<bean:write name="admissionDataReUploadForm" property="programTypeId"/>'/>
					                <html:select styleId="programTypeId"  property="programTypeId" styleClass="combo" onchange="getPrograms(this.value,'progPref1')">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderProgramTypes></cms:renderProgramTypes>
									</html:select></td>
					                <td width="10%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.program"/>:</div></td>
					                <td width="20%" class="row-even"><html:select property="programId" styleClass="combo" styleId="progPref1" onchange="getCourse(this.value,'coursePref1')" >
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<c:if test="${admissionDataReUploadForm.programTypeId != null && admissionDataReUploadForm.programTypeId != ''}">
                           					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
	                           		    	 	<c:if test="${programMap != null}">
	                           		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
	                           		    	 	</c:if> 
				                        </c:if>
										</html:select>
									</td>
					                <td width="10%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.course"/>: </div></td>
					                <td width="30%" class="row-even"><html:select property="courseId" styleClass="combo" styleId="coursePref1">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<c:if test="${admissionDataReUploadForm.programId != null && admissionDataReUploadForm.programId != ''}">
                         				<c:set var="courseMap" value="${baseActionForm.collectionMap['coursesMap']}"/>
                         		    		<c:if test="${courseMap != null}">
                         		    			<html:optionsCollection name="courseMap" label="value" value="key"/>
                         		    		</c:if>	 
		                           </c:if>
								   </html:select>
								   </td>
					            </tr>
								<tr>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.omrupload.csv.label"/></div>
									</td>
									<td width="20%" class="row-even"><label>
										<html:file styleId="csvFile" property="csvFile"></html:file>
									</label></td>
									<td width="10%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.appliedyear"/>:</div>
									</td>
									<td width="20%" class="row-even">
									<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="admissionDataReUploadForm" property="applicationYear"/>" />
										<html:select property="applicationYear" styleId="applicationYear" styleClass="combo">
											<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
											<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
										</html:select>
									</td>
									<td width="10%" class="row-odd">
									</td>
									<td width="30%" class="row-even"><label>
									</label></td>
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
					<div align="center">
					<table width="100%" height="106" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td width="100%" height="46" class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="center"><html:submit styleClass="formbutton">
										<bean:message key="knowledgepro.submit" />
									</html:submit>&nbsp;&nbsp;<html:button property="" styleClass="formbutton"
										onclick="resetFields()">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button></div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="10"></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
var programTypeId = document.getElementById("programType").value;
if(programTypeId != null && programTypeId.length != 0) {
	document.getElementById("programTypeId").value = programTypeId;
}
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("applicationYear").value=year;
}
</script>