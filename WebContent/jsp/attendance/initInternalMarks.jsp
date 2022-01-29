<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	function getInternalMarks() {
		document.getElementById("method").value = "getInternalMarks";
		document.attendanceDataMigrationForm.submit();
	}
	function gotoDataMigration() {
		document.location.href = "attnDataMigrationReport.do?method=initAttnDataMigrationReport";
	}
</script>
<html:form action="/attnDataMigrationReport" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="attendanceDataMigrationForm" />
	<html:hidden property="pageType" styleId="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.exam.internalMarks" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.internalMarks" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /> </span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td height="25" class="row-even"><html:select
												property="academicYear" styleId="academicYearId" >
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<cms:renderAdmBioDataAcademicYear></cms:renderAdmBioDataAcademicYear>
											</html:select></td>
									
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.blockUnblock.class" />:</div>
									</td>
									<td   class="row-even"><input type="hidden"
										name="cId" id="cId"
										value='<bean:write name="attendanceDataMigrationForm" property="className"/>' />
									<html:select property="className" styleClass="combo"
										styleId="class" >
										<html:option value="">
											-<bean:message key="knowledgepro.select" />-</html:option>
											<logic:notEmpty name="attendanceDataMigrationForm" property="classesMap">
										<html:optionsCollection name="attendanceDataMigrationForm" label="value" value="key" property="classesMap"/>
										</logic:notEmpty>
									</html:select></td>
									
									</tr>
									<tr>
									     <td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepor.attendance.marks.testident" />:</div>
									</td>
									<td   class="row-even">
									<html:select property="testIdent" styleClass="combo"
										styleId="test" >
										<html:option value="">
											-<bean:message key="knowledgepro.select" />-</html:option>
											<logic:notEmpty name="attendanceDataMigrationForm" property="testIdentMap">
										<html:optionsCollection name="attendanceDataMigrationForm" label="value" value="key" property="testIdentMap"/>
										</logic:notEmpty>
									</html:select></td>
									<td width="25%" class="row-odd"></td>
									<td width="25%" class="row-even"></td>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center"><html:button property=""
								styleClass="formbutton" value="Search" onclick="getInternalMarks()"></html:button>
								<html:button property=""
								styleClass="formbutton" value="Back" onclick="gotoDataMigration()"></html:button>
								</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
	document.getElementById("class").value = null;
	document.getElementById("test").value = null;
</script>