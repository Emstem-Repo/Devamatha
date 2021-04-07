<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script language="javaScript" type="text/javascript">
	function redirectControl() {
		document.location.href = "CurriculumScheme.do?method=initCurriculumScheme";
	}
	function redirectControlBack() {
		document.location.href = "CurriculumScheme.do?method=showCurriculumSchemeOnCourse";
	}
	function saveCurriculumSchme(){
		document.getElementById("method").value = "setCurriculumScheme";
	}
</script>
<html:form action="/CurriculumScheme" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="curriculumSchemeForm" />
	<html:hidden property="pageType" value="2" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.curriculumscheme" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.admission.curriculumscheme" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="1">
						<tr>
							<td height="20" colspan="6">
							<div align="right"><span class='MandatoryMark'> <bean:message
							key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="green"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td width="12%" height="25" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.admin.program.type" /></div>
							</td>
							<td width="8%" class="row-even">&nbsp; <bean:write
								name="curriculumSchemeForm"
								property="programTypeName" /></td>
							<td width="12%" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.admin.prog" /></div>
							</td>
							<td width="8%" class="row-even">&nbsp; <bean:write
								name="curriculumSchemeForm"
								property="programName" /></td>
							<td width="12%" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.interview.Course" /></div>
							</td>
							<td width="10%" class="row-even">&nbsp;<bean:write
								name="curriculumSchemeForm"
								property="courseName" /></td>
						</tr>
						<tr>
						<td width="12%" height="25" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.admitted.Year" /></div>
							</td>
							<td width="8%" class="row-even">&nbsp;<bean:write
								name="curriculumSchemeForm"
								property="academicYear" /></td>
							<td width="12%" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.admission.scheme/course.col" /></div>
							</td>
							<td width="8%" class="row-even">&nbsp;<bean:write
								name="curriculumSchemeForm"
								property="noOfSchemeName" /></td>
							<td width="12%" class="row-odd">
							<div align="right"><bean:message
								key="knowledgepro.admission.scheme.col" /></div>
							</td>
							<td width="10%" class="row-even">&nbsp;<bean:write
								name="curriculumSchemeForm"
								property="schemeName" /></td>
								</tr>
						<tr>
							<td height="25" colspan="12">
							<table width="100%" height="79" border="0" cellpadding="0"
								cellspacing="1">
								<tr class="row-odd">
									<td width="10%" height="25" class="bodytext">
									<div align="center"><strong>
									<bean:write	name="curriculumSchemeForm"	property="schemeName" />
								</strong></div>
									</td>
									<td width="20%" height="25" class="bodytext">
									<div align="center">
									<p align="center"><strong><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admission.startDate" /> </strong><strong></strong></p>
									</div>
									</td>
									<td width="20%" height="25" class="bodytext">
									<div align="center"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admission.endDate" /></div>
									</td>
									<td width="20%" height="25" class="bodytext">
									<div align="center"><strong><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.year" /></strong></div>
									</td>
									<td width="30%" height="25" class="bodytext">
									<div align="center"><strong><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admission.subjectGroup" /></strong></div>
									</td>
								</tr>
								<logic:notEmpty name="curriculumSchemeForm"
									property="curriculumSchemeDetails">
								<nested:iterate name="curriculumSchemeForm"
									property="curriculumSchemeDetails"
									type="com.kp.cms.to.admission.CurriculumSchemeTO"
								 id="curr" indexId="count">
									<%
										String styleDate1 = "datePick" + count;
													String styleDate2 = "datePicker" + count;
									%>

									<tr class="row-even">
										<td align="center" height="20" class="bodytext" id="count">
										<div align="center"><c:out value="${count+1}" /> <input
											type="hidden"
											name="curriculumSchemeDetails[<c:out value='${count}'/>].semister"
											value="<c:out value='${count + 1}' />"></div>

										</td>
										<td height="20" class="bodytext">
										<div align="center"><nested:text styleId='<%=styleDate1%>'
											property="startDate" size="10" maxlength="10" />
										<script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'curriculumSchemeForm',
								// input name
								'controlname' :'<%=styleDate1%>'
							});
						</script></div>
										</td>
										<td height="20" class="bodytext">
										<div align="center"><nested:text styleId='<%=styleDate2%>'
											property="endDate" size="10" maxlength="10" />
										<script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'curriculumSchemeForm',
								// input name
								'controlname' :'<%=styleDate2%>'
								});
							</script></div>

										</td>
										<td height="20" class="bodytext">
										<div align="center"><span
								class="star">
								
								
	            				        				 
								<nested:select property="year" styleClass="combo" >
								<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
								<cms:year>
								</cms:year>
								<script type="text/javascript">
								/*var year = document.getElementById("tempyear_<c:out value='${count}'/>").value;
									if (year.length != 0) {
										document.getElementById("year_<c:out value='${count}'/>").value = year;
									}*/
								</script>
								</nested:select>
								</span></div>
										</td>
										<td height="30" width="10%" class="bodytext">
										<div align="center"><nested:select
											property="subjectGroup" styleClass="row-even"
											multiple="multiple" size="4" style="width:250px;height:80px">
											<logic:notEmpty property="subjectGroupList" name="curriculumSchemeForm">
											<nested:optionsCollection name="curriculumSchemeForm"
												property="subjectGroupList" label="name" value="id" />
												</logic:notEmpty>
										</nested:select></div>
										</td>
									</tr>
								</nested:iterate>
								</logic:notEmpty>
							</table>
						<tr>
							<td height="25" colspan="12">
							<table width="100%" border="0" cellspacing="1" cellpadding="12">
								<tr>
									<td height="21" align="left"></td>
									<td align="left">
									<div align="center"><html:submit styleClass="formbutton"
										value="Save" onclick="saveCurriculumSchme()">
									</html:submit> <html:cancel value="Reset" styleClass="formbutton" /> 
									<html:button property="" styleClass="formbutton" value="Cancel"
										onclick="redirectControl()">
									</html:button></div>
									</td>
									<td height="45" align="left">&nbsp;&nbsp;</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<div align="center"></div>
					</td>
					<td width="16" valign="top" background="images/Tright_3_3.gif"
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