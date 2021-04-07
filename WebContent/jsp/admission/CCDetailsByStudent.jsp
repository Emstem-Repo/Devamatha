<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script src="js/AC_RunActiveContent.js" type="text/javascript"></script>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<script type='text/javascript' src="jquery/Zebra/javascript/zebra_dialog.js"></script>
<link rel="stylesheet" href="jquery/Zebra/css/default/zebra_dialog.css"  />
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function cancelAction() {
		document.location.href = "conductCertificateDetails.do?method=getCandidates";
	}
	
</script>
<html:form action="/conductCertificateDetails" method="post">
	<html:hidden property="method" styleId="method" value="updateCandidateDetailsEdit"/>
	<html:hidden property="formName" value="conductCertificateDetailsForm" />
	
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;Conduct Certificate Details&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Conduct Certificate Details</td>
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
								<%-- <tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.student" /></div>
									</td>
									<td class="row-even">
									<html:text name="conductCertificateDetailsForm" property="tcDetailsTO.studentName" styleId="studentName" maxlength="50"/>									
									</td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.dob" /></div>
									</td>
									<td   class="row-even">
									<html:text name="conductCertificateDetailsForm" property="tcDetailsTO.dateOfBirth" styleId="dateOfBirth" size="10" maxlength="16"/>								
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'conductCertificateDetailsForm',
										// input name
										'controlname' :'tcDetailsTO.dateOfBirth'
									});
									</script>
									</td>
								</tr>--%>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td  class="row-even">
									<bean:write name="conductCertificateDetailsForm" property="academicYear"/></td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.class.col" /></div>
									</td>
									<td   class="row-even">
									<nested:select property="classId" styleClass="body" styleId="class" style="width:45%;height:15" >
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty property="classMap" name="conductCertificateDetailsForm">
											<html:optionsCollection name="conductCertificateDetailsForm" property="classMap" label="value" value="key" />
										</logic:notEmpty>
									</nested:select>
									</td>
								</tr>
								
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.dateofApplication" /></div>
									</td>
									<td   class="row-even">
									<html:text name="conductCertificateDetailsForm" property="dateOfApplication" styleId="dateOfApplication" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'conductCertificateDetailsForm',
										// input name
										'controlname' :'dateOfApplication'
									});
									</script>
									</td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Course</div>
									</td>
									<td   class="row-even"><html:text name="conductCertificateDetailsForm" property="courseName" styleId="courseName" /></td>
								</tr>
								
							
								<tr>
									<td height="25" class="row-odd"><div align="right">Academic Duration</div></td>
									<td height="25" class="row-even"><html:text property="academicDuration" name="conductCertificateDetailsForm" styleId="academicDuration" maxlength="50"/></td>
									<td   height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.tc.details.characterandConduct" /></div>
									</td>
									<td class="row-even">
								<html:select property="characterId" styleId="characterId"  styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="conductCertificateDetailsForm" property="list">
										<html:optionsCollection property="list" label="name" value="id" /></logic:notEmpty>
									</html:select>
									</td>
								</tr>
								<tr>
									
								<%-- 	<td height="25" class="row-odd"><div align="right">Is Kerala</div></td>
									<td height="25" class="row-even">
									<html:radio property="isKerala" value="yes" styleId="isKerala_1"> Yes</html:radio>	
					   		 	 	<html:radio property="isKerala" value="no" styleId="isKerala_2">  No</html:radio>
									</td>--%>
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
							<html:submit styleClass="formbutton" styleId="submit_tc"></html:submit> &nbsp;&nbsp;
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