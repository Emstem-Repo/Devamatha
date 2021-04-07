<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>

<html:form action="/ExamUniversityRegisterNumberEntry.do">


	<html:hidden property="formName"
		value="ExamUniversityRegisterNumberEntry" />
	<html:hidden property="pageType" value="2" />


	<html:hidden property="listStudentSize" styleId="listStudentSize" />
	<html:hidden property="academicYear" styleId="academicYear" />
	<html:hidden property="scheme" styleId="scheme" />
	<html:hidden property="programId" styleId="programId" />




	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt;University
			Register Number Entry&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<p><strong class="boxheader">University Register
					Number Entry</strong></p>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>


				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"></div>
					
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
									<td width="25%" height="25" class="row-odd">
									<div align="right">Academic Year :</div>
									</td>
									<td width="25%" class="row-even"><bean:write
										name="ExamUniversityRegisterNumberEntryForm"
										property="academicYearFull" /></td>





									<td width="21%" height="26" class="row-odd">
									<div align="right">Program:</div>
									</td>
									<td width="29%" class="row-even"><bean:write
										name="ExamUniversityRegisterNumberEntryForm"
										property="programName" /></td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right">Course:</div>
									</td>
									<td class="row-even"><bean:write
										name="ExamUniversityRegisterNumberEntryForm"
										property="courseName" /></td>

									<td class="row-odd" height="26">
									<div align="right">Scheme No:</div>
									</td>
									<td class="row-even" colspan="4"><bean:write
										name="ExamUniversityRegisterNumberEntryForm" property="scheme" /></td>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">
					<div align="right" class="mandatoryfield"></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>


				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td class="row-odd">Sl.No</td>
									<td class="row-odd">Roll No.</td>
									<td class="row-odd">Student Name</td>
									<td class="row-odd">Specialisation</td>
									<td class="row-odd">Second Language</td>
									<td class="row-odd"><span class="Mandatory">*</span>Reg No.</td>
								</tr>
								<nested:iterate name="ExamUniversityRegisterNumberEntryForm"
									property="studentDetails" indexId="count">


									<tr>
										<td height="25" class="row-even">
										<div align="center"><c:out value="${count+1}" /></div>
										</td>

										<td width="19%" class="row-even"><nested:write
											property="rollNo" /></td>
										<td width="27%" class="row-even"><nested:write
											property="studentName" /></td>
										<td width="25%" class="row-even"><nested:write
											property="specialization" /></td>
										<td width="25%" class="row-even"></td>
										<td width="25%" align="center" class="row-even"><nested:text
											property="regNo" size="15"
											styleId="regNo_<c:out value='${count}'/>"  maxlength="10"/></td>
										<nested:hidden property="id" />

									</tr>



								</nested:iterate>

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
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35" align="right"><html:submit
								property="method" styleClass="formbutton" value="Submit" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="45%" align="left"><html:reset
								styleClass="formbutton" value="Reset"/></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
					<td valign="top" background="/images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="/images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="/images/Tright_3_3.gif" class="news"></td>
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

