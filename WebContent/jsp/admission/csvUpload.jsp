<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
	<script language="JavaScript" src="js/admission/admissionform.js"></script>	
</head>
<html:form action="/admissionFormSubmit" enctype="multipart/form-data">
	<html:hidden property="method" value="updateCSV" />
	<html:hidden property="formName" value="admissionFormForm" />
	<html:hidden property="pageType" value="13" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="admissionForm.omrupload.main.label"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">  <bean:message key="admissionForm.omrupload.main.label"/></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
							<div id="errorMessage">
							<FONT color="red"><html:errors/></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT>
							</div>
							</td>
						</tr>
						<tr class="row-white">
			                <td width="113" height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
			                <td width="178" height="20" class="row-even">
			                <input type="hidden" id="programType" name="programType" value='<bean:write name="admissionFormForm" property="programTypeId"/>'/>
			                <html:select styleId="programTypeId"  property="programTypeId" styleClass="combo" onchange="getPrograms(this.value,'progPref1')">
												<html:option value="">- Select -</html:option>
												<cms:renderProgramTypes></cms:renderProgramTypes>
							</html:select></td>
			                <td width="100" height="20" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.program"/>:</div></td>
			                <td width="190" height="20"><html:select property="programId" styleClass="combo" styleId="progPref1" onchange="getCourse(this.value,'coursePref1')" >
												<html:option value="">- Select -</html:option>
												<c:if test="${admissionFormForm.programTypeId != null && admissionFormForm.programTypeId != ''}">
						                           					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
					                            		    	 	<c:if test="${programMap != null}">
					                            		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
					                            		    	 	</c:if> 
						                        </c:if>
										</html:select></td>
			                <td width="122" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.course"/>: </div></td>
			                <td width="206" class="row-even"><html:select property="courseId" styleClass="combo" styleId="coursePref1">
												<html:option value="">- Select -</html:option>
												<c:if test="${admissionFormForm.programId != null && admissionFormForm.programId != ''}">
			                         						<c:set var="courseMap" value="${baseActionForm.collectionMap['coursesMap']}"/>
			                         		    			<c:if test="${courseMap != null}">
			                         		    				<html:optionsCollection name="courseMap" label="value" value="key"/>
			                         		    			</c:if>	 
					                           </c:if>
										</html:select></td>
			              </tr>
						<tr>
							<td width="21%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.omrupload.csv.label"/></div>
							</td>
							<td width="29%" height="25" class="row-even"><label>
								<html:file property="csvFile"></html:file>
							</label></td>
							<td width="20%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col"/></div>
							</td>
							<td width="30%" class="row-even">
							<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="admissionFormForm" property="applicationYear"/>"/>
							<html:select
								property="applicationYear" styleId="applicationYear">
								<html:option value=" ">-Select-</html:option>
								<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
							</html:select></td>
							<td width="21%" height="25" class="row-odd">
							</td>
							<td width="29%" height="25" class="row-even"><label>
							</label></td>
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
									</html:submit><html:button property="" styleClass="formbutton"
										onclick="resetFieldAndErrMsgs()">
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
if (year.length != 0) {
	document.getElementById("applicationYear").value = year;
}
</script>