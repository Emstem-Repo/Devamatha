<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%><script type="text/javascript">
function deleteDocExam(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "SupplementaryFees.do?method=deleteOrReactivatePublish&mode=delete&id="+ id;
	}
}
function resetFormFields(){
	resetFieldAndErrMsgs();
}
function getClasses(programTypeID) {

	academicYear=document.getElementById("academicYear").value;
	getClassesByProgramTypeAndAcademicYear1("coursesMap", programTypeID,academicYear, "class",
			updateCourses);

}
function updateCourses(req) {
	updateOptionsFromMapMultiselect(req, "class", "- Select -");
}


</script>
<html:form action="/SupplementaryFees">	
	<c:choose>
		<c:when test="${operation == 'edit'}">
		<html:hidden property="mode" styleId="mode" value="update" />
		</c:when>
		<c:otherwise>
		<html:hidden property="mode" styleId="mode" value="add" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="method" styleId="method" value="addOrUpdatePublish" />
	<html:hidden property="formName" value="supplementaryFeesForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam"/>
			<span class="Bredcrumbs">&gt;&gt;
			Supplementary Exam Fees
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Supplementary Exam Fees</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'>Regular Exam Application</span></FONT></div>
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
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
								
								<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.studentEligibilityEntry.academicYear" />
									:</div>
									</td>
									<td class="row-even" width="25%"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="supplementaryFeesForm" property="academicYear"/>' />

									<html:select
										property="academicYear" styleId="academicYear"
										styleClass="combo" onclick="resetErrMsgs()">
										<html:option value="0">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>
									
									<td width="22%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;Category:</div>
									</td>

									<td width="20%" height="15" class="row-even"><html:select
										property="sectionId" styleClass="body"
										styleId="sectionId">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<html:option value="1">OEC</html:option>
										<html:option value="2">Others</html:option>
										<html:option value="3">SC</html:option>
										<html:option value="4">ST</html:option>

									</html:select></td>
								
								   	<td width="22%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>

									<td width="20%" height="15" class="row-even"><html:select
										property="programTypeId" styleClass="body"
										styleId="programType" onchange="getClasses(this.value)" onclick="resetErrMsgs()">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<html:optionsCollection name="supplementaryFeesForm"
											property="programTypeList" label="programTypeName" value="programTypeId" />

									</html:select></td>
									<td width="15%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;Class:</div>
									</td>
									<td width="16%" class="row-even"><nested:select
										property="selectedClass" styleClass="body"
										multiple="multiple" size="5" styleId="class"
										style="width:300px" >
											<c:if test="${supplementaryFeesForm.classMap != null}">
												<nested:optionsCollection property="classMap" name="supplementaryFeesForm"
													label="value" value="key" styleClass="comboBig" />
											</c:if>


									</nested:select></td>
							
								</tr>
								
								<tr>
							
							        
									<td height="25" class="row-odd">
									<div align="right">&nbsp;Registration Fee :</div>
									</td>
									<td class="row-even">
									<html:text name="supplementaryFeesForm" property="registerationFees" styleId="registerationFees" size="10" maxlength="16" onkeypress="return isNumberKey(event)"/>
									</td>
									<td height="25" class="row-odd">
									<div align="right">&nbsp;Paper Fee:</div>
									</td>
									<td class="row-even">
									<html:text name="supplementaryFeesForm" property="paperFees" styleId="paperFees" size="10" maxlength="16" onkeypress="return isNumberKey(event)"/>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.application.fees"/></div>
									</td>
									<td class="row-even">
									<html:text name="supplementaryFeesForm" property="applicationFees" styleId="applicationFees" size="10" maxlength="16" onkeypress="return isNumberKey(event)"/>
									</td>
									<!--<td height="25" class="row-odd">
									<div align="right">Exam Fee</div>
									</td>
									<td class="row-even">
									<html:text name="supplementaryFeesForm" property="examFees" styleId="examFees" size="10" maxlength="16" onkeypress="return isNumberKey(event)"/>
									</td>
								--><td height="25" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.cvcamp.fees"/></div>
									</td>
									<td class="row-even">
									<html:text name="supplementaryFeesForm" property="cvCampFees" styleId="cvCampFees" size="10" maxlength="16" onkeypress="return isNumberKey(event)"/>
									</td>
								    </tr>
								
									<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message key="knowledgepro.markslist.fees"/></div>
									</td>
									<td class="row-even">
									<html:text name="supplementaryFeesForm" property="marksListFees" styleId="marksListFees" size="10" maxlength="16" onkeypress="return isNumberKey(event)"/>
									</td>
									<td height="25" class="row-odd">
									<!--<div align="right"><bean:message key="knowledgepro.online.service.fees"/></div>
									--></td>
									<td class="row-even">
									<!--<html:text name="supplementaryFeesForm" property="onlineServiceChargeFees" styleId="onlineServiceChargeFees" size="10" maxlength="16" onkeypress="return isNumberKey(event)"/>
									--></td>
									<td height="25" class="row-odd">
									</td>
									<td class="row-even">
									</td>
									<td height="25" class="row-odd">
									</td>
									<td class="row-even">
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
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
									<c:choose>
							<c:when test="${operation == 'edit'}">
							<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:cancel value="Reset" styleClass="formbutton" ></html:cancel></td>
							</c:when>
							<c:otherwise>
								<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="submit"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button></td>
							</c:otherwise>
							</c:choose>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td width="5%" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Class</div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Academic Year</div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center">Admitted Through</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">Paper Fees</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">Registration Fees</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">Application Fees</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">CV Camp Fees</div>
											</td>
											<td width="15%" class="row-odd">
											<div align="center">Marks List Fees</div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
											</td>
										</tr>
										<logic:notEmpty name="supplementaryFeesForm" property="toList">
										<logic:iterate id="dList" name="supplementaryFeesForm" property="toList" indexId="count">
										<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center"><bean:write name="dList" property="className" /></td>
											<td align="center"><bean:write name="dList" property="academicYear" /></td>
											<td align="center"><bean:write name="dList" property="sectionName" /></td>
											<td align="center"><bean:write name="dList" property="paperFees" /></td>
											<td align="center"><bean:write name="dList" property="registrationFees" /></td>
											<td align="center"><bean:write name="dList" property="applicationFees" /></td>
											<td align="center"><bean:write name="dList" property="cvCampFees" /></td>
											<td align="center"><bean:write name="dList" property="marksListFees" /></td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" onclick="deleteDocExam('<bean:write name="dList" property="id" />')" /></div>
											</td>
											</tr>	
										</logic:iterate>
										</logic:notEmpty>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>			
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
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

<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
	</script>