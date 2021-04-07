<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Map,java.util.HashMap"%>

<script language="JavaScript">
function closeWindow(){
	document.location.href = "LoginAction.do?method=loginAction";
	
}
	function deleteExamDefinition(examDefId_progId) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "ExamDefinition1.do?method=deleteExamDefinition&examDefId_progId="
					+ examDefId_progId;
		}
	}
	function editExamDefinition(examDefId_progId) {
		document.location.href = "ExamDefinition1.do?method=editExamDefinition&examDefId_progId="
				+ examDefId_progId;
	}
	function reActivate(id) {
		document.location.href = "ExamDefinition1.do?method=reActivateExamDefinition&id="
				+ id;
	}

	function getPrograms() {
		var programTypeList = document.getElementById('programType');
		var len=programTypeList.length;
		
		var programTypes= new Array;
		for(var j=0; j<len; j++)
		{
			if(programTypeList[j].selected)
			{
				programTypes.push(programTypeList.options[j].value);
			}
		
		}
		var pt=programTypes.toString();
	
		getProgramsByPTypes("programMap", pt, "program",
				updatePrograms);
}

	function updatePrograms(req) {
		updateOptionsFromMapMultiselect(req, "program", "- Select -");
	}

	function setProgramName(){

		document.getElementById("programName").value = document
				.getElementById("program").options[document
				.getElementById("program").selectedIndex].text;
	}
	
	function resetErrorMsgs() {

		document.location.href = "ExamDefinition1.do?method=initExamDefinition";
	}

	function getListByAcademicYear(academicYear) {
		document.getElementById("academicYear").value = academicYear;
		document.location.href="ExamDefinition1.do?method=ExamDefinitionYearSort&academicYear="+academicYear;
		}
</script>

<html:form action="ExamDefinition1.do" focus="programType">


	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="ExamDefinitionForm" />
	<html:hidden property="programTypeName" styleId="programTypeName" />
	<html:hidden property="programName" styleId="programName" />
	<html:hidden property="method" styleId="method" value="addExamDefinition" />
	<html:hidden property="year" styleId="yearName" value="" />
		
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.examDefinition" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.exam.examDefinition" /> </strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<div align="right"><span class='MandatoryMark'><bean:message
							key="knowledgepro.mandatoryfields" /></span></div>
						<tr>

							<td height="20" colspan="6" class="body" align="left">

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
							<td width="100%" valign="top">


							<table width="100%" cellspacing="1" cellpadding="2">

								<tr>
								
									<td height="25" class="row-odd" width="25%">
										<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
											key="knowledgepro.exam.examDefinition.academicYear" />:</div>
									</td>
									
									
							<td height="25" class="row-even"><span class="star"><input type="hidden" id="yr" name="yr"
									value='<bean:write name="ExamDefinitionForm" property="academicYear"/>' />
								<html:select property="academicYear" styleClass="combo" styleId="academicYear" name="ExamDefinitionForm"  onchange="getListByAcademicYear(this.value)">
									<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
									<cms:renderAcademicYear></cms:renderAcademicYear>
								</html:select></span>
							</td>
									
									
									
									
									<!-- <td height="25" class="row-even" width="15%">
									 <input type="hidden" id="ay" name="ay" value='<bean:write name="ExamDefinitionForm" property="academicYear" />' />
									<html:select property="academicYear" styleId="academicYear" styleClass="combo" onchange="getListByAcademicYear(this.value)">
                     	   					 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                     	   					 <cms:renderYear></cms:renderYear>
                     			   	</html:select></td>-->
                     			   	
									<td width="22%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>

									<td width="20%" height="15" class="row-even"><nested:select
										property="selectedProgramType" styleClass="body"
										multiple="multiple" size="6"
										onchange="getPrograms()" styleId="programType"
										style="width:300px">
										
										<nested:optionsCollection name="ExamDefinitionForm"
											property="programTypeList" label="display" value="id"
											styleClass="comboBig" />

									</nested:select></td>
									<td width="22%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td width="36%" height="15" colspan="3" class="row-even">
									<nested:select property="selectedProgram" styleClass="body"
										multiple="multiple" size="6" styleId="program"
										style="width:350px" onchange="setProgramName()">

										<c:if
											test="${ExamDefinitionForm.selectedProgramType != null && ExamDefinitionForm.selectedProgramType != ''}">
											<c:set var="programMap"
												value="${baseActionForm.collectionMap['programMap']}" />
											<c:if test="${programMap != null}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:if>

										</c:if>

									</nested:select></td>
									
									
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
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="4"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message
									key="knowledgepro.exam.examDefinition.addExamNameButton" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:reset styleClass="formbutton"
								value="Reset" onclick="resetErrorMsgs()"></html:reset></td>
							<td width="2%"></td>								
							<td><html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="4"></td></tr>						
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
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
									<td align="center" class="bodytext"><bean:message
										key="knowledgepro.admin.programtype" /></td>

									<td align="center" width="163" height="25" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.admin.program" /></div>
									</td>
									<td align="center" width="163" height="25" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examDefinition.examName" /></div>
									</td>
									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examDefinition.examType" /></div>
									</td>
									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examDefinition.month" /></div>
									</td>
									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examDefinition.year" /></div>
									</td>
									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examDefinition.academicYear" /></div>
									</td>
									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examDefinition.examCode" /></div>
									</td>
									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examDefinition.current" /></div>
									</td>
									<td align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.exam.examDefinition.internalExamName" /></div>
									</td>
									<td>
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td>
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:iterate name="ExamDefinitionForm"
									property="examDefinitionList" id="eDList" indexId="count"
									type="com.kp.cms.to.exam.ExamDefinitionTO">
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
									<td width="13%" align="center" class="bodytext"><bean:write
										name="eDList" property="programType" /></td>
									<td width="13%" align="center" class="bodytext"><bean:write
										name="eDList" property="program" /></td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eDList"
										property="examName" /></div>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eDList"
										property="examType" /></div>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eDList"
										property="month" /></div>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eDList"
										property="year" /></div>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eDList"
										property="academicYear" /></div>
									</td>

									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eDList"
										property="examCode" /></div>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eDList"
										property="isCurrent" /></div>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="eDList"
										property="internalExamNameList" /></div>
									</td>
									<td width="50" height="24">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor: pointer"
										onclick="editExamDefinition('<bean:write name="eDList" property="examDefId_progId"/>')">

									</div>
									</td>
									<td width="54" height="24">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor: pointer"
										onclick="deleteExamDefinition('<bean:write name="eDList" property="examDefId_progId"/>')">
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
	
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}

	</script>