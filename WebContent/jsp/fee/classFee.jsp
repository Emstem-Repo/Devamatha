<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
		<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
		<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<script language="JavaScript" >
function submitExcel(){
	document.getElementById("method").value ="uploadClassFees";
	var academicYear=document.getElementById("academicYearId").value;
	document.location.href = "additionalFees.do?method=uploadClassFees&academicYear=" +academicYear;
}

function resetErrMsgs(){
	document.getElementById("academicYearId").value ="";
	document.getElementById("thefile").value ="";
	resetErrMsgs();
}
function cancel(){
	document.location.href = "admBioData.do?method=initAdmBioData";
}
</script>
<html:form action="/additionalFees" method="POST" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="uploadClassFees" />
	<html:hidden property="formName" value="additionalFeesForm" />
	<html:hidden property="pageType" styleId="pageType" value="1" />
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs">Data Migration
			<span class="Bredcrumbs">&gt;&gt;Migration ClassFee &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> Migration ClassFee</strong></div></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
						<div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg" property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
										<td height="25" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span>Academic Year</div></td>
										<td height="25" class="row-even">
											<html:select property="academicYear" styleId="academicYearId">
											<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
											</html:option>
											<cms:renderAdmBioDataAcademicYear></cms:renderAdmBioDataAcademicYear>
											</html:select>
										</td>
										<td height="25" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.excelFile" /></div></td>
											<td height="25" class="row-even"><label>
											<html:file property="theFile" styleId="thefile" size="15" maxlength="30" name="additionalFeesForm"/></label></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="22"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5" height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" height="106" border="0" cellpadding="1" cellspacing="2">
						<tr>
							<td width="100%" height="46" class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="center"><html:submit styleClass="formbutton" onclick="submitExcel()">
									<bean:message key="knowledgepro.submit" /></html:submit>
									<html:button property="" styleClass="formbutton" onclick="resetErrMsgs();">
									<bean:message key="knowledgepro.admin.reset" /></html:button>
									<html:button property="" styleClass="formbutton" onclick="cancel()">
									<bean:message key="knowledgepro.close" /></html:button>
									</div>
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
					<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
