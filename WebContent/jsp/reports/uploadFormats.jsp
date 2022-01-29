<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">

function resetMessages() {	
	document.getElementById("uploadFormatFile").value=null;
	document.getElementById("formatType").value=null;
	resetErrMsgs();
}	
</script>
<html:form action="/uploadFormats" method="post">
	<html:hidden property="method" styleId="method" value="downloadUploadFile"/>
	<html:hidden property="formName" value="uploadFormatForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" />
	<html:hidden property="fileNo" styleId="fileNo" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.reports"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.reports.format"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.reports.format"/>Entry</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
										<div align="right">UploadFile</div>
									</td>
									<td height="25" class="row-even">
										<html:select property="uploadFormatFile" 
											styleId="uploadFormatFile" styleClass="combo">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>											
											<html:option value="mobileFormat"><bean:message key="knowledgepro.reports.format.mobile"/></html:option>
											<html:option value="secondLanguage"><bean:message key="knowledgepro.reports.format.secondLanguage"/></html:option>
											<html:option value="RegisterNoUpload"><bean:message key="knowledgepro.reports.format.registerNo"/></html:option>
											<html:option value="OMRUpload"><bean:message key="knowledgepro.reports.format.OMRUpload"/></html:option>
											<html:option value="admissionDataUpload"><bean:message key="knowledgepro.reports.format.admissionDataUpload"/></html:option>
										</html:select>

									</td>
									<td height="25" class="row-odd">
										<div align="right">Format Type</div>
									</td>
									<td height="25" class="row-even">
										<html:select property="formatType" 
											styleId="formatType" styleClass="combo">
											<html:option value="excel"><bean:message key="knowledgepro.reports.format.excel"/></html:option>
											<html:option value="csv"><bean:message key="knowledgepro.reports.format.csv"/></html:option>
										</html:select>

									</td>
										</tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
									<td width="45%" height="35">
									<div align="right">
											<html:submit property="" styleClass="formbutton">
												<bean:message key="knowledgepro.reports.submit" />
											</html:submit>
									</div>
									</td>
									<td width="2%"></td>
									<td width="53%">
											<html:button property="" styleClass="formbutton"
												 onclick="resetMessages()">
												<bean:message key="knowledgepro.cancel" />
											</html:button>
									</td>
								</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
							
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
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
	<logic:notEmpty name="uploadFormatForm" property="downloadExcel">
<logic:notEmpty name="uploadFormatForm" property="mode">
<bean:define id="downloadExcels" property="downloadExcel" name="uploadFormatForm"></bean:define>
<bean:define id="modes" property="mode" name="uploadFormatForm"></bean:define>

<logic:equal name="uploadFormatForm" property="mode" value="excel">
<logic:equal name="uploadFormatForm" property="downloadExcel" value="download">

<SCRIPT type="text/javascript">	
var fileNo = "<c:out value='${uploadFormatForm.fileNo}'/>";

var url ="DownloadExcelFormatAction.do?fileNo="+fileNo;
myRef = window.open(url,"downloadReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");

			
</SCRIPT>

</logic:equal>
</logic:equal>

</logic:notEmpty>
</logic:notEmpty>

<logic:notEmpty name="uploadFormatForm" property="downloadCSV">
<logic:notEmpty name="uploadFormatForm" property="mode">
<bean:define id="downloadCSVS" property="downloadCSV" name="uploadFormatForm"></bean:define>
<bean:define id="modess" property="mode" name="uploadFormatForm"></bean:define>

<logic:equal name="uploadFormatForm" property="mode" value="csv">
<logic:equal name="uploadFormatForm" property="downloadCSV" value="download">

<SCRIPT type="text/javascript">	
var fileNo = "<c:out value='${uploadFormatForm.fileNo}'/>";
var urll ="DownloadCSVFormatAction.do?fileNo="+fileNo;
myReff = window.open(urll,"downloadReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");

			
</SCRIPT>

</logic:equal>
</logic:equal>

</logic:notEmpty>
</logic:notEmpty>
</html:form>
