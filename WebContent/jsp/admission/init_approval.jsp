<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
</head>
<script type="text/javascript">
function validNumber(field) {
	if(isNaN(field.value)) {
		field.value="";
	}
}

</script>

<html:form action="/admissionFormSubmit" method="post">
	<html:hidden property="method" value="submitAdmissionApprovalInit" />
	<html:hidden property="formName" value="admissionFormForm" />
	<html:hidden property="pageType" value="9" />
	<table width="100%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td valign="top">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						 <tr>
   							 <td colspan="3"><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="admissionForm.approve.init.label" /> &gt;&gt;</span></span></td>
  						</tr>
						<tr>
							<td width="9"><img src="images/Tright_03_01.gif" width="9"
								height="29"></td>
							<td background="images/Tcenter.gif" class="body">
							<div align="left"><strong class="boxheader">
							<bean:message key="admissionForm.approve.init.label" /></strong></div>
							</td>
							<td width="10"><img src="images/Tright_1_01.gif" width="9"
								height="29"></td>
						</tr>
						<tr>
							<td height="122" valign="top"
								background="images/Tright_03_03.gif"></td>
							<td valign="top" class="news">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr bgcolor="#FFFFFF">
									<td height="20" colspan="4">
									<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
									<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
									<FONT color="green"><html:messages id="msg"
										property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages> </FONT></div>
									</td>
								</tr>
								
								<tr>
									<td width="27%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admission.appNo" /></div>
									</td>
									<td width="20%" class="row-even"><html:text
										property="applicationNumber" styleId="appNo" size="9" onkeypress="return isNumberKey(event)"
										maxlength="30"></html:text>
									</td>
									<td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>
			                    		<bean:message key="admissionForm.studentedit.admyear.label" /></div></td>
									<td class="row-even">
										<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="admissionFormForm" property="applicationYear"/>"/>
										<html:select property="applicationYear" styleId="year"  styleClass="combo">
											<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
											<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
										</html:select>
									</td>
								</tr>
								<tr>
									<td height="45" colspan="4">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="49%" height="35"><div align="right">
								               <html:submit onclick="submitAdmissionForm('submitAdmissionApprovalInit')" styleClass="formbutton" value="Continue"></html:submit>
								            </div></td>
								            <td width="1%"></td>
								            <td width="51%"><div align="left"><html:button property=""  styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button></div></td>
								          </tr>
										</tr>
									</table>
									</td>
								</tr>
							</table>
							</td>
							<td width="10" valign="top" background="images/Tright_3_3.gif"
								class="news"></td>
						</tr>
						<tr>
							<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
							<td width="949" background="images/TcenterD.gif"></td>
							<td><img src="images/Tright_02.gif" width="9" height="29"></td>
						</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td valign="top">&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
</script>
