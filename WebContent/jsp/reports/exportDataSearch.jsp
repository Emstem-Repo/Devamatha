<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function getPrograms(programTypeId) {
		getProgramsByType("programMap", programTypeId, "program",updatePrograms);
		resetOption("program");
		
			var destination5 = document.getElementById("course");
			for (x1=destination5.options.length-1; x1>=0; x1--) {
				destination5.options[x1]=null;
			}
		
	}

	function updatePrograms(req) {
		updateOptionsFromMap(req, "program", "- Select -");
		
	}

	function getCourses(programId) {
		getCoursesByProgram("coursesMap", programId, "course", updateCourses);
		var destination5 = document.getElementById("course");
		for (x1=destination5.options.length-1; x1>=0; x1--) {
			destination5.options[x1]=null;
		}

	}

	function updateCourses(req) {
		updateOptionsFromMapForMultiSelect(req, "course");
		
	}
	
	function resetValues() {
		resetErrMsgs();
		document.location.href = "exportDataSearch.do?method=initExportSearch";
	}
</script>

<body>
<html:form action="/exportDataSearch" method="post">
	<html:hidden property="method" styleId="method" value="submitExportSearch" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="formName" value="dataSearchForm" />
	<table width="98%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td class="heading"><bean:message key="knowledgepro.reports" /><span
				class="Bredcrumbs">&gt;&gt; <span class="Bredcrumbs">Send Data For ID Card
				&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="5"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Send Data For ID Card</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="45" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr bgcolor="#FFFFFF">
							<div align="right"><span class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span>
							</div>
							<td colspan="6" class="body" align="left">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
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
									<td width="22%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.admin.program.type" />
									</div>
									</td>
									<td width="28%" height="25" class="row-even">
										<html:select property="programTypeId" styleId="programtype" styleClass="combo" onchange="getPrograms(this.value)">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<html:optionsCollection name="programTypeList" label="programTypeName" value="programTypeId" />
										</html:select>
									</td>

									<td width="19%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									  <bean:message key="knowledgepro.admin.prog" />
									</div>
									</td>
									<td width="26%" height="25" class="row-even">
										<html:select property="programId" styleId="program" styleClass="combo" onchange="getCourses(this.value)">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<c:if test="${dataSearchForm.programTypeId != null && dataSearchForm.programTypeId != ''}">
												<c:if test="${programMap != null}">
													<html:optionsCollection name="programMap" label="value" value="key" />
												</c:if>
											</c:if>
										</html:select>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.admin.course.with.col" />
									</div>
									</td>
									<td class="row-even" width="25%" height="25">
										<html:select property="courseNames" styleId="course" styleClass="combo" size="5" style="height: 100px;" multiple="multiple">
											<c:if test="${dataSearchForm.programId != null && dataSearchForm.programId != ''}">
												<c:if test="${courseMap != null}">
													<html:optionsCollection name="courseMap" label="value" value="key" />
												</c:if>
											</c:if>
										</html:select>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.fee.academicyear.col" />
									</div>
									</td>
									<td height="25" colspan="4" class="row-even">
										<input type="hidden" id="tempyear" name="tempyear"
											value="<bean:write name="dataSearchForm" property="academicYear"/>" />
										<html:select property="academicYear" styleId="academicYear"	styleClass="combo">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
										</html:select>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
										<div align="right"><bean:message key="knowledgepro.fee.semister" />:</div>
									</td>
									<td height="25" class="row-even">
									 <html:select property="semister" styleId="semister" styleClass="comboMedium">
					 				 	<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
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
									</html:select>
									</td>
									<td class="row-odd" align="right"> Valid Till </td>
									<td class="row-even" > 
									<html:text property="validTill" styleClass="TextBox"
										styleId="validTill" size="16" maxlength="15" name="dataSearchForm" />
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
						<tr>
						<td colspan="3">
						<div align="center" class="heading">OR</div>
						</td>
						</tr>
						   <tr>
		              <td ><img src="images/01.gif" width="5" height="5" /></td>
		              <td width="914" background="images/02.gif"></td>
		              <td><img src="images/03.gif" width="5" height="5" /></td>
		            </tr>
		            <tr>
		              <td width="5"  background="images/left.gif"></td>
		              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
		                <tr >
		                  <td width="22%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.appNo" /> </div></td>
		                  <td width="24%" height="25" class="row-even" >					
							<html:text name="dataSearchForm" property="applicationNo" styleClass="TextBox" styleId="applnnoID" size="12" maxlength="30" onkeypress="return isNumberKey(event)"/>
						  </td>
		                  <td width="7%" class="row-even" ><div align="center" class="heading"></div></td>
		                  <td width="21%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.feereport.feereport.registrationno" /></div></td>
		                  <td width="26%" height="25" colspan="2" class="row-even" >					
							<html:text name="dataSearchForm" property="regNo" styleClass="TextBox" styleId="regNoID" size="10" maxlength="10"/>
						  </td>
		                </tr>
		                <tr >
		                  <td width="22%" height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.studentedit.roll.label" /> </div></td>
		                  <td width="24%" height="25" class="row-even" >					
							<html:text name="dataSearchForm" property="rollNo" styleClass="TextBox" styleId="applnnoID" size="10" maxlength="10"/>
						  </td>
		                  <td width="7%" class="row-even" ><div align="center" class="heading"></div></td>
		                  <td width="21%" height="25" class="row-odd" ><div align="right"></div></td>
		                  <td width="26%" height="25" colspan="2" class="row-even" >					
							
						  </td>
		                </tr>
                
              </table></td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="40" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="931" background="images/TcenterD.gif"></td>
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
	document.getElementById("academicYear").value = year;
}
</script>