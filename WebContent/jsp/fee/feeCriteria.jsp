<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function deleteCriteria(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "FeeCriteria.do?method=deleteFeeCriteria&id="	+ id ;
	}
}
function getInstitute(universityId) {
	getCollegeByUniversity("instituteMap", universityId, "instituteID",
			updateInstitute);
}
function updateInstitute(req) {
	updateOptionsFromMap(req, "instituteID", "- Select -");
	
}


</script>
<html:form action="/FeeCriteria">	
	<html:hidden property="method" value="addFeeCriteria" />
	<html:hidden property="formName" value="feeCriteriaForm" />
	
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.fee"/>
			<span class="Bredcrumbs">&gt;&gt;
			Fee Criteria
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Fee Criteria </strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"> <span class='MandatoryMark'>
							<bean:message key="knowledgepro.mandatoryfields"/></span></div>
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
							<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.university" />:</div>
								</td>
								<td width="26%" height="25" class="row-even"><span
									class="star"> <html:select property="universityId"
									styleClass="comboLarge" styleId="universityId"	
									onchange="getInstitute(this.value)">
									<html:option value="">
										<bean:message key="knowledgepro.admin.select" />
									</html:option>
									<html:optionsCollection property="universityList" name = "feeCriteriaForm"
										label="name" value="id" />
								</html:select></span></td>		
							<td class="row-odd">
								<div align="right"><bean:message
									key="knowledgepro.admin.institute" />:</div>
							</td>
							<td class="row-even">
							<html:select property="instituteID"
										styleClass="combo" styleId="instituteID">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:if
											test="${feeCriteriaForm.universityId != null && feeCriteriaForm.universityId != ''}">
											<c:set var="instituteMap"
												value="${baseActionForm.collectionMap['instituteMap']}" />
											<c:if test="${instituteMap != null}">
												<html:optionsCollection name="instituteMap" label="value"
													value="key" />
											</c:if>
										</c:if>
								</html:select>
							</td>
							
												
							</tr>
							<tr>
							<td class="row-odd">
								<div align="right"><bean:message
									key="knowledgepro.admin.nationality" />:</div>
							</td>
							<td width="26%" height="25" class="row-even"><span
								class="star"> <html:select property="nationalityID"
								styleClass="combo" styleId="nationalityID"	>
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection property="nationalities" name = "feeCriteriaForm"
									label="name" value="id" />
							</html:select></span></td>								
								<td class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.studentinfo.residentcatg.label" /></div>
								</td>
								<td width="26%" height="25" class="row-even"><span
									class="star"> <html:select property="residentCategoryId"
									styleClass="combo" styleId="residentCategoryId"	>
									<html:option value="">
										<bean:message key="knowledgepro.admin.select" />
									</html:option>
									<html:optionsCollection property="residentCategoryList" name = "feeCriteriaForm"
										label="name" value="id" />
								</html:select></span></td>
							</tr>
							
							<tr>
								<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.admitted.through" /></div>
								</td>
								<td width="26%" height="25" class="row-even"><span
									class="star"> <html:select property="admittedThroghId"
									styleClass="combo" styleId="admittedThroghId"	>
									<html:option value="">
										<bean:message key="knowledgepro.admin.select" />
									</html:option>
									<html:optionsCollection property="admittedList" name = "feeCriteriaForm"
										label="name" value="id" />
								</html:select></span></td>								
								<td class="row-odd">
									<div align="right"><bean:message
										key="admissionForm.edit.qualifydetails.seclang.label" />:</div>
								</td>
							
								<td width="26%" height="25" class="row-even"><span
									class="star"> <html:select property="language"
									styleClass="combo" styleId="language"	>
									<html:option value="">
										<bean:message key="knowledgepro.admin.select" />
									</html:option>
									<html:optionsCollection property="languageMap" name = "feeCriteriaForm"
										label="value" value="key" />
								</html:select></span></td>							
								
							</tr>
							
							
							<tr>
								<td class="row-odd">
									<div align="right">Additional Fee 1:</div>
								</td>
								<td width="26%" height="25" class="row-even"><span
									class="star"> <html:select property="additionalFeeGroup1"
									styleClass="combo" styleId="additionalFeeGroup1"	>
									<html:option value="">
										<bean:message key="knowledgepro.admin.select" />
									</html:option>
									<html:optionsCollection property="feeOptionalGroupMap" name = "feeCriteriaForm"
										label="value" value="key" />
								</html:select></span></td>
								<td class="row-odd">
									<div align="right">Additional Fee 2:</div>
								</td>
								<td width="26%" height="25" class="row-even"><span
									class="star"> <html:select property="additionalFeeGroup2"
									styleClass="combo" styleId="additionalFeeGroup2"	>
									<html:option value="">
										<bean:message key="knowledgepro.admin.select" />
									</html:option>
									<html:optionsCollection property="feeOptionalGroupMap" name = "feeCriteriaForm"
										label="value" value="key" />
								</html:select></span></td>
								
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
							<td width="45%" height="35">
							<div align="right">
							<html:submit styleClass="formbutton">
										<bean:message key="knowledgepro.submit" />
							</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFieldAndErrMsgs()"></html:button>
							</td>
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
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center">Institute</td>
									<td height="25" class="row-odd" align="center">Nationality</td>
									<td height="25" class="row-odd" align="center">University</td>
									<td height="25" class="row-odd" align="center">Student Category</td>
									<td height="25" class="row-odd" align="center">Admitted Through</td>
									<td height="25" class="row-odd" align="center">Language</td>
									<td height="25" class="row-odd" align="center">Additional Fee 1</td>
									<td height="25" class="row-odd" align="center">Additional Fee 2</td>
									
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:iterate name="feeCriteriaList" id="criteria"
									type="com.kp.cms.to.fee.FeeCriteriaTO" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="9%" height="25" class="row-even" align="center">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="72%" height="25" class="row-even" align="center"><bean:write
													name="criteria" property="instiute"  /></td>
												<td width="72%" height="25" class="row-even" align="center"><bean:write
													name="criteria" property="nationality"  /></td>
												<td width="72%" height="25" class="row-even" align="center"><bean:write
													name="criteria" property="university"  /></td>
												<td width="72%" height="25" class="row-even" align="center"><bean:write
													name="criteria" property="residentCategory"  /></td>	
												<td width="72%" height="25" class="row-even" align="center"><bean:write
													name="criteria" property="admittedThrough"  /></td>
												<td width="72%" height="25" class="row-even" align="center"><bean:write
													name="criteria" property="secLannguage"  /></td>													
												<td width="72%" height="25" class="row-even" align="center"><bean:write
													name="criteria" property="feeAddlId1"  /></td>		
												<td width="72%" height="25" class="row-even" align="center"><bean:write
													name="criteria" property="feeAddlId2"  /></td>													
												<td width="9%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer" 
													onclick = "deleteCriteria('<bean:write name="criteria" property="id"/>')">
												</div>

												</td>
											</tr>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td width="9%" height="25" class="row-white" align="center">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="72%" height="25" class="row-white" align="center"><bean:write
													name="criteria" property="instiute"  /></td>
												<td width="72%" height="25" class="row-white" align="center"><bean:write
													name="criteria" property="nationality"  /></td>
												<td width="72%" height="25" class="row-white" align="center"><bean:write
													name="criteria" property="university"  /></td>
												<td width="72%" height="25" class="row-white" align="center"><bean:write
													name="criteria" property="residentCategory"  /></td>	
												<td width="72%" height="25" class="row-white" align="center"><bean:write
													name="criteria" property="admittedThrough"  /></td>
												<td width="72%" height="25" class="row-white" align="center"><bean:write
													name="criteria" property="secLannguage"  /></td>													
												<td width="72%" height="25" class="row-white" align="center"><bean:write
													name="criteria" property="feeAddlId1"  /></td>		
												<td width="72%" height="25" class="row-white" align="center"><bean:write
													name="criteria" property="feeAddlId2"  /></td>													
												<td width="9%" height="25" class="row-white" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer" 
													onclick = "deleteCriteria('<bean:write name="criteria" property="id"/>')">
												</div>

												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
								</logic:iterate>
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
