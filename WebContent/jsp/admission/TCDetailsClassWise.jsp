<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function cancelAction() {
		document.location.href = "tcDetailsClassWise.do?method=getCandidates";
	}
</script>
<html:form action="/tcDetailsClassWise" method="post">
	<html:hidden property="method" styleId="method" value="updateStudentTCDetails" />
	<html:hidden property="formName" value="tcDetailsClassWiseForm" />
	<html:hidden property="pageType" value="3" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.tc.details.label" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.admission.tc.details.label" /></td>
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
									<td  class="row-even"><bean:write name="tcDetailsClassWiseForm" property="academicYear"/></td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.class.col" /></div>
									</td>
									<td   class="row-even"><bean:write name="tcDetailsClassWiseForm" property="className"/></td>
								</tr>
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.dateofApplication" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsClassWiseForm" property="tcDetailsTO.dateOfApplication" styleId="tcDetailsTO.dateOfApplication" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'tcDetailsClassWiseForm',
										// input name
										'controlname' :'tcDetailsTO.dateOfApplication'
									});
									</script>
									</td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.feespaid"/></div>
									</td>
									<td class="row-even">
									<nested:radio property="tcDetailsTO.feePaid" value='yes'>
										<bean:message key="knowledgepro.yes" />
									</nested:radio> 
									<nested:radio property="tcDetailsTO.feePaid" value='no'>
										<bean:message key="knowledgepro.no" />
									</nested:radio>									
									</td>
								</tr>
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.dateOfLeaving" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsClassWiseForm" property="tcDetailsTO.dateOfLeaving" styleId="tcDetailsTO.dateOfLeaving" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'tcDetailsClassWiseForm',
										// input name
										'controlname' :'tcDetailsTO.dateOfLeaving'
									});
									</script>
									</td>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.tc.details.reasonofLeaving"/></div>
									</td>
									<td class="row-even">
									<html:text name="tcDetailsClassWiseForm" property="tcDetailsTO.reasonOfLeaving" styleId="reasonOfLeaving" size="15"/>								
									</td>
								</tr>
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.characterandConduct" /></div>
									</td>
									<td   class="row-even">
									<html:select property="tcDetailsTO.characterId" styleId="tcDetailsTO.characterId"  styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection property="list" label="name" value="id" />
									</html:select>
									</td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.particular"/></div>
									</td>
									<td class="row-even">
									<nested:radio property="tcDetailsTO.scholarship" value='yes'>
										<bean:message key="knowledgepro.yes" />
									</nested:radio> 
									<nested:radio property="tcDetailsTO.scholarship" value='no'>
										<bean:message key="knowledgepro.no" />
									</nested:radio>									
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.academicyear"/></div>
									</td>
									<td  class="row-even">
									<input type="hidden" id="yr" name="yr" value='<bean:write name="tcDetailsClassWiseForm" property="tcDetailsTO.year"/>' />
									<nested:select property="tcDetailsTO.year" styleId="tcDetailsTO.year" styleClass="comboSmall">
										<html:option value="">Select</html:option>
						              	<cms:renderYear normalYear="true"></cms:renderYear>
									</nested:select></td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.examDefinition.month" /></div>
									</td>
									<td   class="row-even">
									<nested:select property="tcDetailsTO.month" styleId="tcDetailsTO.month" styleClass="comboSmall">
										<html:option value="">Select</html:option>
										<html:option value="JAN">JAN</html:option>
						              	<html:option value="FEB">FEB</html:option>
										<html:option value="MAR">MAR</html:option>
										<html:option value="APR">APR</html:option>
										<html:option value="MAY">MAY</html:option>
										<html:option value="JUN">JUN</html:option>
										<html:option value="JUL">JUL</html:option>
										<html:option value="AUG">AUG</html:option>
										<html:option value="SEPT">SEPT</html:option>
										<html:option value="OCT">OCT</html:option>
										<html:option value="NOV">NOV</html:option>
										<html:option value="DEC">DEC</html:option>
									</nested:select>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span><bean:message
											key="knowledgepro.admission.student.tcDetails.publicExaminationName.label" />
										</div>
									</td>
									<td height="25" class="row-even">
										<nested:text property="tcDetailsTO.publicExamName" styleId="publicExamName" maxlength="50"/>
									</td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.student.tcDetails.showRegNo.label"/></div>
									</td>
									<td class="row-even">
									<nested:radio property="tcDetailsTO.showRegisterNo" value='yes'>
										<bean:message key="knowledgepro.yes" />
									</nested:radio> 
									<nested:radio property="tcDetailsTO.showRegisterNo" value='no'>
										<bean:message key="knowledgepro.no" />
									</nested:radio>									
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
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center">
							<html:submit styleClass="formbutton"></html:submit> &nbsp;&nbsp;
							&nbsp;
							<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()"></html:button>
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
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("tcDetailsTO.year").value = yearId;
	}
</script>