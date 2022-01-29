<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>


<script type="text/javascript">
	function cancel() {
		document.location.href = "assignSecondLanguage.do?method=initAssignSecondLanguage";
	}
</script>

<html:form action="/assignSecondLanguage">

	<html:hidden property="formName" value="assignSecondLanguageForm" />
	<html:hidden property="method" styleId="method" value="Apply" />
	<html:hidden property="pageType" value="2" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.assignSecondLanguage" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="20" colspan="6" >
					<div  id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages></FONT></div>
				</tr>
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.exam.ExamStudentSpecialization" /> </strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td width="100%" height="20">&nbsp;</td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td height="20">
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
											<td width="27%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>Course:</div>
											</td>
											<td width="20%" height="25" class="row-even"><bean:write
												name="assignSecondLanguageForm" property="courseId" /></td>
											<td width="28%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>Semester
											:</div>
											</td>
											<td width="25%" class="row-even"><bean:write
												name="assignSecondLanguageForm" property="schemeNo" /></td>
										</tr>
										<tr>

											<td width="22%" class="row-odd" height="28">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.petticash.academicYear" /></div>
											</td>
											<td width="34%" class="row-even"><bean:write
												name="assignSecondLanguageForm" property="year" />
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.castcatg.label" /></div>
											</td>
											<td class="row-even"><bean:write
												name="assignSecondLanguageForm" property="section" /></td>
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
						</tr>
						<tr>
							<td height="25" class=" heading"></td>
						</tr>
						<tr>
							<td height="25" class=" heading">
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
									<table width="100%" height="90" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-odd">
											<td  height="25" class="row-even">
											<div align="center"><strong>Select</strong></div>
											</td>
											<td  height="25" class="row-even">Roll No.</td>
											<td  class="row-even">Register No.</td>
											<td  class="row-even">Application Number</td>
											<td  height="25" class="row-even">Student
											Name</td>
											<td class="row-even">Second Language</td>
										</tr>
										<nested:iterate name="assignSecondLanguageForm" property="list" indexId="count">
											<tr>
												<td  height="25" class="row-even">
												<nested:checkbox property="checked" styleId="${count}"></nested:checkbox>
												</td>
												<td  height="25" class="row-even"><bean:write name="rollNo"/> </td>
												<td  class="row-even"><bean:write name="registerNo"/> Register No.</td>
												<td  class="row-even"><bean:write name="applnNo"/> Application Number</td>
												<td  height="25" class="row-even"><bean:write name="name"/></td>
												<td class="row-even"><bean:write name="secondLanguage"/> Second Language</td>
											</tr>
										</nested:iterate>
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
						</tr>
						<tr>
							<td height="25" class=" heading"></td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td height="20">
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

											<td width="29%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;Select
											SecondLanguage :</div>
											</td>
											<td width="27%" height="25" class="row-even">
											<html:select property="searchSecondLanguageId" styleClass="combo"
												styleId="schemeId">
												<html:option value=""><bean:message key="knowledgepro.select" /></html:option>

												<logic:notEmpty name="assignSecondLanguageForm"
													property="secondLanguageList">
													<html:optionsCollection property="secondLanguageList"
														name="assignSecondLanguageForm" label="value"
														value="key" />
												</logic:notEmpty>

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
						</tr>

						<tr>
							<td height="25" class=" heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><html:submit styleClass="formbutton"
										value="Apply" /></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><html:button property="submit"
										styleClass="formbutton" value="Cancel" onclick="cancel()" /></td>
								</tr>
							</table>
							</td>
						</tr>


					</table>
					<div align="center">
					<table width="100%" height="10  border=" 0" cellpadding="0"
						cellspacing="0">

						<tr>
							<td></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
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

