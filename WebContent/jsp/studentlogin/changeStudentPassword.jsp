<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript"> 
function backTOHome() {
	document.location.href="StudentLoginAction.do?method=studentLoginLoadData";	
}
</script>
<html:form action="/ChangePassword">
	<html:hidden property="method" styleId="method"	value="updateStudentPassword" />
	<html:hidden property="formName" value="changePasswordForm" />
	<html:hidden property="pageType" value="1" />
<table width="930" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <p>&nbsp;</p>
      <p>&nbsp;</p>
       <p>&nbsp;</p>
      <p>&nbsp;</p>
      <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td colspan="2" class="nav"><bean:message key="knowledgepro.usermanagement.change.password"/></td>
          <td width="220" class="tr">&nbsp;</td>
        </tr>
        <tr>
          <td width="13" class="le">&nbsp;</td>
          <td width="383">
          <div id="errorMessage">
             <FONT color="red"><html:errors/></FONT>
             <FONT color="green">
					<html:messages id="msg" property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages>
			 </FONT>
		</div>
		<table width="87%" border="0" align="center" cellpadding="5" cellspacing="5" style=" font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; color:#000099;">
            <tr>
              <td width="46%" height="25" >
				<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.dob"/> &nbsp;  </div>
				</td>
				<td width="54%" height="25" ><span
					class="star"> <html:text property="dob" styleId="dob" size="11" maxlength="11"></html:text>
						<script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'changePasswordForm',
								// input name
								'controlname' :'dob'
							});
						</script> </span></td>
            </tr>
            <tr>
              <td width="46%" height="25" >
				<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.userManagement.existingPwd.required"/>: &nbsp;  </div>
				</td>
				<td width="54%" height="25" ><span
					class="star"> <html:password property="existingPwd"
					styleClass="TextBox" styleId="existingPwd" size="20"
					maxlength="200" /> </span></td>
            </tr>
            <tr>
				<td width="46%" height="25" >
				<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.userManagement.newPwd.required"/>: &nbsp; </div>
				</td>
				<td width="54%" height="25" ><span
					class="star"> <html:password property="newPwd"
					styleClass="TextBox" styleId="newPwd" size="20"
					maxlength="200" /> </span></td>
			</tr>
			<tr>
				<td width="55%" height="25" >
				<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.userManagement.reTypeNewPwd.required"/>: &nbsp; </div>
				</td>
				<td width="54%" height="25" ><span
					class="star"> <html:password property="reTypeNewPwd"
					styleClass="TextBox" styleId="reTypeNewPwd" size="20"
					maxlength="200" /> </span></td>
			</tr>
            <tr>
              <td colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<c:choose>
							<c:when test="${tempPasswordLogin == false}">
								<td width="33%" height="35" align="right">
									<html:submit property="" styleClass="btnbg" value="Submit" styleId="submitbutton"/>
								</td>
								<td width="1%">&nbsp;</td>
								<td width="10%" align="center"><html:button property=""
									styleClass="btnbg" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button></td>
								<td width="1%">&nbsp;</td>
							
								<td width="32%" align="left"><html:button property=""
									styleClass="btnbg" value="Cancel" onclick="backTOHome()"></html:button></td>
							</c:when>
							<c:otherwise>
								<td width="48%" height="35" align="right">
								<html:submit property="" styleClass="btnbg" value="Submit" styleId="submitbutton"/>
								</td>
								<td width="1%">&nbsp;</td>
								<td width="48%" align="left"><html:button property=""
								styleClass="btnbg" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button></td>								
							</c:otherwise>
							</c:choose>
						</tr>
					</table>
					</td>
            </tr>
          </table></td>
          <td width="220" class="ri">&nbsp;</td>
        </tr>
        <tr>
          <td class="bl">&nbsp;</td>
          <td class="bm">&nbsp;</td>
          <td class="br">&nbsp;</td>
        </tr>
      </table>
       <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <p>&nbsp;</p></td>
  </tr>
</table>	
</html:form>