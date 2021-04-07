
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript">

</script>

<html:form action="ExamSpecializationSubjectgroup.do">

	<html:hidden property="method" styleId="method"
		value="setExamSplSubGroupAdd" />
	<html:hidden property="formName"
		value="ExamSpecializationSubjectGroupForm" />

	<html:hidden property="academicYear_value" styleId="academicYear_value" />
	<html:hidden property="courseId" styleId="courseId" />
	<html:hidden property="schemeId" styleId="schemeId" />


	<html:hidden property="pageType" value="2" />

	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.exam.ExamSpecializationSubjectgroup" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.exam.ExamSpecializationSubjectgroup" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.year" /> :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										name="ExamSpecializationSubjectGroupForm"
										property="academicYear" /></td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.course" /> :</div>
									</td>
									<td width="29%" height="25" class="row-even"><bean:write
										name="ExamSpecializationSubjectGroupForm"
										property="courseName" /></td>
								</tr>
								<tr>
									<td width="28%" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.scheme.col" /> :</div>
									</td>
									<td class="row-even" colspan="3"><bean:write
										name="ExamSpecializationSubjectGroupForm"
										property="schemeName" /></td>
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
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" />
									</div>
									</td>
									<td width="45%" class="row-odd"><bean:message
										key="knowledgepro.exam.ExamSpecializationSubjectgroup.specialization" /></td>
									<td width="40%" class="row-odd"><bean:message
										key="knowledgepro.exam.ExamSpecializationSubjectgroup.subjectGroup" /></td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:iterate name="ExamSpecializationSubjectGroupForm"
									property="listSpecializations" id="listSpecializations"
									type="com.kp.cms.to.exam.ExamSpecializationSubjectGroupTO"
									indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="8%" height="25" class="row-even">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="31%" height="25" class="row-even" align="left">
												<bean:write
													name="listSpecializations" property="specialization" /></td>
												<td width="10%" height="15" class="row-even" align="left">
											
												<nested:select
													property="subjectIds" styleClass="body"
													
													multiple="multiple" size="8" styleId='id'
													style="width:200px">
													<nested:optionsCollection name="listSpecializations"
														property="listSubjects" label="display" value="value" />
												</nested:select></td>
													
											</tr>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-white">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td height="25" class="row-white" align="left"><bean:write
													name="listSpecializations" property="specialization" /></td>
												<td height="25" class="row-white" align="left"><nested:select
													property="subjectIds" styleClass="body" multiple="multiple"
													size="8" styleId="id" style="width:200px">
													<nested:optionsCollection name="listSpecializations"
														property="listSubjects" label="display" value="value" />
												</nested:select></td>
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
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><input name="button2" type="submit"
								class="formbutton" value="Submit"  /></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><input type="Reset"
								class="formbutton" value="Reset" /></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
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

