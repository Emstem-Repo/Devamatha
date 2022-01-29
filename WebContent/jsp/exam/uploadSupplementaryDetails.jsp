<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

		function resetMessages() {
			resetFieldAndErrMsgs();
			   }
		   </script>
<html:form action="/uploadSupplementaryDetails" method="post" enctype="multipart/form-data">
<html:hidden property="method" styleId="method" value="uploadSupplementaryApllicationInfo" />
<html:hidden property="formName" value="uploadSupplementaryApllicationForm" />
<html:hidden property="thefile"/>
<html:hidden property="pageType" value="1" />
<table width="99%" border="0"> 
  <tr>
    <td><span class="heading">Exam<span class="Bredcrumbs">&gt;&gt;Upload Exam Data &gt;&gt;</span> </span></td>

  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Upload Exam Data</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="99" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%">
        <tr bgcolor="#FFFFFF">
					<td height="20" colspan="4">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					</html:messages> </FONT></div>
					</td>
			</tr>
            <tr>
				<td height="25" class="row-odd" width="25%">
					<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.upload.excelFile" />:</div></td>
				<td height="25" class="row-even"><label>
				<html:file property="thefile" styleId="thefile" size="15" maxlength="30"/></label></td>
			</tr>
        </table>
        	<div align="center">
					<table width="100%" height="106" border="0" cellpadding="1" cellspacing="2">
						<tr>
							<td width="100%" height="46" class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="center"><html:submit styleClass="formbutton">
									<bean:message key="knowledgepro.submit" /></html:submit>
									<html:button property="" styleClass="formbutton" onclick="resetMessages()">
									<bean:message key="knowledgepro.admin.reset" /></html:button></div>
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
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table>
</td>
</tr>
</table>
</html:form>
