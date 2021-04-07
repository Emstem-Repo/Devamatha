<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<html:form action="/forgotPassword">
	<html:hidden property="method" styleId="method" value="resetForgotPassword" />
	<html:hidden property="formName" value="studentForgotPasswordForm" />
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
          <td colspan="2" class="nav"><bean:message key="knowledgepro.usermanagement.forgotpass.label"/></td>
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
              <td width="40%" align="right"><bean:message key="knowledgepro.usermanagement.userinfo.username"/></td>
              <td width="60%"><html:text property="registerNo" styleId="registerNo" size="30" maxlength="50"></html:text></td>
            </tr>
            <tr>
              <td align="right"><bean:message key="knowledgepro.admin.dateofbirth"/></td>
              <td>
              <html:text name="studentForgotPasswordForm" property="dob" styleId="dob" size="10" maxlength="16"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'studentForgotPasswordForm',
								// input name
								'controlname' :'dob'
							});
						</script>
              </td>
            </tr>
            <tr>
              <td colspan="2" align="center">
              	<html:submit property="" styleClass="btnbg" value="Submit" />
		  		<html:button property="" styleClass="btnbg" value="Reset" onclick="resetFieldAndErrMsgs()"/>
              </td>
            </tr>
            <tr>
            <% String path= request.getContextPath(); %>
            <td colspan="2" align="center"> <a href='<%=path %>/StudentLogin.do'> Back To Login Page</a> </td>
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