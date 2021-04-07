<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script type="text/javascript">

function invoke(){
	deleteConfirm =confirm("Are you sure to cancel admission for this entry?");
	if(deleteConfirm){
		document.getElementById("method").value = "cancelApplicantDetails";
		document.admissionFormForm.submit();
	}
}
</script>
</head>
<html:form action="/admissionFormCancel">
<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="admissionFormForm" />
	<html:hidden property="pageType" value="20" />
	<html:hidden property="admissionEdit" value="true" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admissionform"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message key="knowledgepro.admissionform"/></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
				<td valign="top" background="images/Tright_03_03.gif"></td>
							<td align="left">
							<div id="errorMessage">
							<FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages></FONT>
							</div>
							</td>
							<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
						</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table  width="100%" cellspacing="1" cellpadding="2">
					<tr>
					<td class="row-odd" width="15%"><bean:message key="knowledgepro.fee.studentname"/> </td>
					<td class="row-even" width="15%">
					<nested:write name="admissionFormForm" property="applicantDetails.personalData.firstName"/>
					</td>
					<td class="row-odd" width="15%"><bean:message key="knowledgepro.fee.applicationno"/></td>
					<td class="row-even" width="15%">
					<nested:write name="admissionFormForm" property="applicantDetails.applnNo"/>
					</td>
					<td class="row-odd" width="15%"><bean:message key="curriculumSchemeForm.course"/></td>
					<td class="row-even" width="15%">
					<nested:write name="admissionFormForm" property="applicantDetails.selectedCourse.name"/>
					</td>
					</tr>
					<tr>
					<td class="row-odd" width="15%"><bean:message key="knowledgepro.applicationform.secLang.label"/> </td>
					<td class="row-even" width="15%">
					<nested:write name="admissionFormForm" property="applicantDetails.personalData.secondLanguage"/>
					</td>
					<td height="25" class="row-odd">
					<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.cancellationReason" />:</div>
					</td>
					<td  height="25" class="row-even"><label>
					<html:text property="cancellationReason" styleId="canreason" size="15" maxlength="150"></html:text>
					</label></td>
					<td  height="25" class="row-odd">
					<div align="right"><span class="Mandatory">*</span><bean:message key="admissionform.studentinfo.cancelAdmission" />:</div>
					</td>
					<td  height="25" class="row-even">
					<html:text property="cancellationDate" styleId="cancellationDate" size="15" maxlength="150"></html:text>
						<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'admissionFormForm',
								// input name
								'controlname' :'cancellationDate'
							});
						</script>
					</td>
					</tr>
					<tr>
					<td class="row-odd"><bean:message key="admissionForm.studentedit.admissiondate.label"/> </td>
					<td class="row-even">
					<nested:write name="admissionFormForm" property="applicantDetails.admissionDate"/>
					</td>
					<td  height="25" class="row-odd" colspan="4">
					</td>
					</tr>
					</table>
					
					<div align="center">
					<table width="100%" height="106" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td width="100%" height="46" class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="center"><html:button property="" styleClass="formbutton"  onclick="invoke()">
										<bean:message key="knowledgepro.admission.cancel" />
									</html:button><html:button property="" styleClass="formbutton"
										onclick="resetFieldAndErrMsgs()">
										<bean:message key="knowledgepro.admin.reset" />
									</html:button></div>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="10"></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
