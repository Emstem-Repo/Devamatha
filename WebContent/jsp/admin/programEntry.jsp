<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">
	function editProgram(progId) {
		document.location.href = "Program.do?method=initEdit&programId="
			+ progId;
	}

	function viewProgram(progId) {
		var url ="Program.do?method=initView&programId="+progId;
		myRef = window.open(url,"viewProgram","left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	
	function deleteProgram(progId) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "Program.do?method=deleteProgram&programId=" + progId;
		}
	}
	function resetValues() {
		resetFieldAndErrMsgs();
		if (document.getElementById("method").value == "editProgram") {
			document.getElementById("programType").value = document
					.getElementById("origprogramTypeId").value;
			document.getElementById("programCode").value = document
					.getElementById("origProgramCode").value;
			document.getElementById("progname").value = document
					.getElementById("origProgramName").value;
		}
		else
		{
            document.getElementById("motherTongue_2").checked = true;
            document.getElementById("secondLanguage_2").checked = true;
            document.getElementById("displayLanguageKnown_2").checked = true;
            document.getElementById("heightWeight_2").checked = true;
            document.getElementById("familyBackground_2").checked = true;
            document.getElementById("entranceDetails_2").checked = true;
            document.getElementById("lateralDetails_2").checked = true;
            document.getElementById("displayTrainingCourse_2").checked = true;
            document.getElementById("transferCourse_2").checked = true;
            document.getElementById("additionalInfo_2").checked = true;
            document.getElementById("extraDetails_2").checked = true;
            document.getElementById("tcDisplay_2").checked = true;
            document.getElementById("isRegistartionNo_2").checked = true;
            document.getElementById("isExamCenterRequired_2").checked = true;
            
		}
		

		
	}
	function reActivate() {
//		var id = document.getElementById("programId").value;
		document.location.href = "Program.do?method=activateProgram";
				
		document.getElementById("submitbutton").value = "Submit";
	}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/Program">
	<c:choose>
		<c:when
			test="${programOperation != null && programOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="editProgram" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addprogram" />
		</c:otherwise>
	</c:choose>
	<input type="hidden" name="programId" id="programId" value="<bean:write name="programForm" property="programId"/>"/>
	<html:hidden property="origProgramCode" styleId="origProgramCode" />
	<html:hidden property="origProgramName" styleId="origProgramName" />
	<html:hidden property="origprogramTypeId" styleId="origprogramTypeId" />
	<html:hidden property="formName" value="programForm" />
	<html:hidden property="pageType" value="2" />

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" /><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.programEntry" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.program" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>

						<tr>
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

							<td width="13%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.program.type" /></div>
							</td>
							<td width="19%" height="25" class="row-even"><html:select
								property="programTypeId" styleId="programType"
								styleClass="comboLarge">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="programTypeList"
									label="programTypeName" value="programTypeId" />
							</html:select> <span class="star"></span></td>
							<td width="22%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.program.code" /></div>
							</td>
							<td width="16%" class="row-even"><span class="star">
							<html:text property="programCode" styleClass="TextBox"
								styleId="programCode" size="16" maxlength="15" /> </span></td>
								
						
						</tr>
						


						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.prog" /></div>
							</td>
							<td width="19%" height="25" class="row-even"><html:text
								property="name" styleClass="TextBox" styleId="progname"
								size="30" maxlength="100" /><span class="star"></span></td>
							
							 <td width="16%" class="row-odd"><div align="right"><span class="Mandatory">*</span>Stream</div></td>
						 <td width="16%" class="row-even">
                         <html:select property="stream" styleClass="combo" styleId="stream">
							<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
							<html:option value="Commerce and Management">Commerce and Management</html:option>
							<html:option value="Faculty of Engineering">Faculty of Engineering</html:option>
							<html:option value="Humanities and Social Sciences">Humanities and Social Sciences</html:option>
							<html:option value="School of Law">School of Law</html:option>
							<html:option value="Sciences">Sciences</html:option>
						</html:select>
						 </td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory"></span><bean:message
								key="knowledgepro.admin.prog.name.certificate" /></div>
							</td>
							<td width="19%" height="25" class="row-even" colspan="3"><html:text
								property="programNameCertificate" styleClass="TextBox" styleId=""
								size="30" maxlength="100" /><span class="star"></span></td>
						</tr>
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
				</tr>
				<tr>
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
							<td colspan="4" height="25" class="row-odd" align="center"> <bean:message key="knowledgepro.admin.program.application.cofig"/></td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.applicationform.mothertongue.label"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<input type="radio" name="motherTongue" id="motherTongue_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="motherTongue" id="motherTongue_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
    						<script type="text/javascript">
								var motherTongue = "<bean:write name='programForm' property='motherTongue'/>";
								if(motherTongue == "true") {
				                        document.getElementById("motherTongue_1").checked = true;
								}	
							</script>
    						</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.applicationform.secLang.label"/></div>
							</td>
							<td width="16%" class="row-even">
							 <input type="radio" name="secondLanguage" id="secondLanguage_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                   			 <input type="radio" name="secondLanguage" id="secondLanguage_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
							<script type="text/javascript">
								var secondLanguage = "<bean:write name='programForm' property='secondLanguage'/>";
								if(secondLanguage == "true") {
				                        document.getElementById("secondLanguage_1").checked = true;
								}	
							</script>
							</td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.display.known.lan"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<input type="radio" name="displayLanguageKnown" id="displayLanguageKnown_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="displayLanguageKnown" id="displayLanguageKnown_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var displayLanguageKnown = "<bean:write name='programForm' property='displayLanguageKnown'/>";
								if(displayLanguageKnown == "true") {
				                        document.getElementById("displayLanguageKnown_1").checked = true;
								}	
							</script>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.height.weight"/></div>
							</td>
							<td width="16%" class="row-even">
							 <input type="radio" name="heightWeight" id="heightWeight_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                   			 <input type="radio" name="heightWeight" id="heightWeight_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
							<script type="text/javascript">
								var heightWeight = "<bean:write name='programForm' property='heightWeight'/>";
								if(heightWeight == "true") {
				                        document.getElementById("heightWeight_1").checked = true;
								}	
							</script>
							</td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.family.background"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<input type="radio" name="familyBackground" id="familyBackground_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="familyBackground" id="familyBackground_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var familyBackground = "<bean:write name='programForm' property='familyBackground'/>";
								if(familyBackground == "true") {
				                        document.getElementById("familyBackground_1").checked = true;
								}	
							</script>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.entrance.details"/></div>
							</td>
							<td width="16%" class="row-even">
							 <input type="radio" name="entranceDetails" id="entranceDetails_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                   			 <input type="radio" name="entranceDetails" id="entranceDetails_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
							<script type="text/javascript">
								var entranceDetails = "<bean:write name='programForm' property='entranceDetails'/>";
								if(entranceDetails == "true") {
				                        document.getElementById("entranceDetails_1").checked = true;
								}	
							</script>
							</td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.lateral.details"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<input type="radio" name="lateralDetails" id="lateralDetails_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="lateralDetails" id="lateralDetails_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var lateralDetails = "<bean:write name='programForm' property='lateralDetails'/>";
								if(lateralDetails == "true") {
				                        document.getElementById("lateralDetails_1").checked = true;
								}	
							</script>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.training.short.course"/></div>
							</td>
							<td width="16%" class="row-even">
							 <input type="radio" name="displayTrainingCourse" id="displayTrainingCourse_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                   			 <input type="radio" name="displayTrainingCourse" id="displayTrainingCourse_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
							<script type="text/javascript">
								var displayTrainingCourse = "<bean:write name='programForm' property='displayTrainingCourse'/>";
								if(displayTrainingCourse == "true") {
				                        document.getElementById("displayTrainingCourse_1").checked = true;
								}	
							</script>
							</td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.transfer.course"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<input type="radio" name="transferCourse" id="transferCourse_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="transferCourse" id="transferCourse_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var transferCourse = "<bean:write name='programForm' property='transferCourse'/>";
								if(transferCourse == "true") {
				                        document.getElementById("transferCourse_1").checked = true;
								}	
							</script>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.additional.info"/></div>
							</td>
							<td width="16%" class="row-even">
							<input type="radio" name="additionalInfo" id="additionalInfo_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                   			 <input type="radio" name="additionalInfo" id="additionalInfo_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
							<script type="text/javascript">
								var additionalInfo = "<bean:write name='programForm' property='additionalInfo'/>";
								if(additionalInfo == "true") {
				                        document.getElementById("additionalInfo_1").checked = true;
								}	
							</script>
							</td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.extra.curriculam"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<input type="radio" name="extraDetails" id="extraDetails_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="extraDetails" id="extraDetails_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var extraDetails = "<bean:write name='programForm' property='extraDetails'/>";
								if(extraDetails == "true") {
				                        document.getElementById("extraDetails_1").checked = true;
								}	
							</script>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.isTCDisplay"/></div>
							</td>
							<td width="16%" class="row-even">
							 <input type="radio" name="isTcDisplay" id="tcDisplay_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="isTcDisplay" id="tcDisplay_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var tcDisplay = "<bean:write name='programForm' property='isTcDisplay'/>";
								if(tcDisplay == "true") {
				                        document.getElementById("tcDisplay_1").checked = true;
								}	
							</script></td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right">Is Online Application Open</div>
							</td>
							<td width="19%" height="25" class="row-even">
							<input type="radio" name="isOpen" id="isOpen_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="isOpen" id="isOpen_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var isOpen = "<bean:write name='programForm' property='isOpen'/>";
								if(isOpen == "true") {
				                        document.getElementById("isOpen_1").checked = true;
								}	
							</script>
							</td>
							<td width="22%" class="row-odd">
							<div align="right"><span class="Mandatory">*</span>Online application for:</div>
							</td>
							<td class="row-even"><span class="star"> <input
								type="hidden" id="tempyear" name="tempyear"
								value="<bean:write name="programForm" property="year"/>" />
							<html:select name="programForm" property="year"
								styleId="year" styleClass="combo">
								<html:option value="">- Select -</html:option>
								<cms:renderYear></cms:renderYear>
							</html:select> </span></td>
						</tr>
						<tr>
							<td width="13%" height="25" class="row-odd">
							<div align="right">Seat no generation required:</div>
							</td>
							<td width="19%" height="25" class="row-even">
							<input type="radio" name="isExamCenterRequired" id="isExamCenterRequired_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="isExamCenterRequired" id="isExamCenterRequired_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var isExamCenterRequired = "<bean:write name='programForm' property='isExamCenterRequired'/>";
								if(isExamCenterRequired == "true") {
				                        document.getElementById("isExamCenterRequired_1").checked = true;
								}	
							</script>
							</td>
							<td width="22%" class="row-odd">
							<div align="right">&nbsp;</div>
							</td>
							<td width="16%" class="row-even">
							 &nbsp;</td>
						</tr>
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
				</tr>
				<tr>
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
							<td width="13%" height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.admin.program.attn.with.reg.no"/></div>
							</td>
							<td width="19%" height="25" class="row-even">
							<input type="radio" name="isRegistartionNo" id="isRegistartionNo_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                    		<input type="radio" name="isRegistartionNo" id="isRegistartionNo_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
                   			<script type="text/javascript">
								var RegistartionNo = "<bean:write name='programForm' property='isRegistartionNo'/>";
								if(RegistartionNo == "true") {
				                        document.getElementById("isRegistartionNo_1").checked = true;
								}	
							</script>
							</td>
							<td width="22%" class="row-odd">
							<div align="right">&nbsp;</div>
							</td>
							<td width="16%" class="row-even"> &nbsp;
							</script></td>
						</tr>
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
				</tr>		
				<tr>
				<td height="25" colspan="4">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><c:choose>
								<c:when
									test="${programOperation != null && programOperation == 'edit'}">
									<html:submit property="" styleClass="formbutton"
										value="Update" styleId="submitbutton">
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton"
										value="Submit" styleId="submitbutton">
									</html:submit>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>

							<td width="53%"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
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
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>

									<td height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.programtype" /></td>
									<td height="25" class="row-odd" align="center"> <bean:message
										key="knowledgepro.admin.programcode" /></td>
									<td class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.program.name" /></td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.view"/></div>
									</td>	
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:iterate name="programList" id="programlist"
									type="com.kp.cms.to.admin.ProgramTO" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="8%" height="25" class="row-even" align="center">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>

												<td width="25%" height="25" class="row-even" align="center"><bean:write
													name="programlist" property="programTypeTo.programTypeName" /></td>
												<td width="25%" class="row-even" align="center"><bean:write
													name="programlist" property="code" /></td>
												<td width="25%" class="row-even" align="center"><bean:write
													name="programlist" property="name" /></td>
												<td width="11%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/View_icon.gif"
													width="16" height="18" style="cursor:pointer" 
													onclick="viewProgram('<bean:write name="programlist" property="id"/>')">
												</div>
												</td>	
												<td width="11%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer" 
													onclick="editProgram('<bean:write name="programlist" property="id"/>')">
												</div>
												</td>
												<td width="10%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer" 
													onclick="deleteProgram('<bean:write name="programlist" property="id"/>','<bean:write name="programlist" property="name"/>')">
												</div>
												</td>
											</tr>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-white" align="center">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td height="25" class="row-white" align="center"><bean:write
													name="programlist" property="programTypeTo.programTypeName" /></td>
												<td height="25" class="row-white" align="center"><bean:write
													name="programlist" property="code" /></td>
												<td class="row-white" align="center"><bean:write name="programlist"
													property="name" /></td>
												<td height="25" class="row-white" align="center">
												<div align="center"><img src="images/View_icon.gif"
													width="16" height="18" style="cursor:pointer" 
													onclick="viewProgram('<bean:write name="programlist" property="id"/>')">
												</div>
												</td>	
												<td height="25" class="row-white" align="center">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer" 
													onclick="editProgram('<bean:write name="programlist" property="id"/>')">
												</div>
												</td>
												<td height="25" class="row-white" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer" 
													onclick="deleteProgram('<bean:write name="programlist" property="id"/>','<bean:write name="programlist" property="name"/>')"></div>
												</div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
								</logic:iterate>
							</table>
							</td>
						</tr>

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

					</table>
					</td>

					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
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