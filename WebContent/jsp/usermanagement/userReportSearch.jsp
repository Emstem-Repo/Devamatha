<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function searchUsers() {
	document.getElementById("method").value = "searchUsers";
	document.userReportForm.submit();
}


</script>
<html:form action="/UserReport" method="post">
	<html:hidden property="method" styleId="method" value="" />	
	<html:hidden property="formName" value="userReportForm" />
	<table width="99%" border="0">
	<tr>
		<td><span class="Bredcrumbs"><bean:message
			key="knowledgepro.reports" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.usermanagement.user.rep" />
			&gt;&gt;</span></span>
		</td>
	</tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.usermanagement.user.rep.search"/> </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news">
		<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td width="100%" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
				<tr >
                  <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentedit.name.label"/></div></td>
					<td width="30%" height="25" class="row-even" align="left">
					<html:text property="firstsearchName" size="4" maxlength="30" styleId="firstsearchName"></html:text>
					<html:text property="middlesearchName" size="7" maxlength="20" styleId="middlesearchName"></html:text>
					<html:text property="lastsearchName" size="4" maxlength="30" styleId="lastsearchName"></html:text>
					</td>
                  <td class="row-odd" align="right"><bean:message key="admissionForm.studentinfo.dob.label"/></td>
					<td height="25" class="row-even" align="left">
					<html:text property="searchDob" styleId="searchDob" size="11" maxlength="11"></html:text>
					<script language="JavaScript">
						new tcal( {
							// form name
							'formname' :'userReportForm',
							// input name
							'controlname' :'searchDob'
						});
					</script>
					</td>
                  </tr>
                <tr >
					<td width="25%" height="25" class="row-odd">
					<div align="right"><bean:message
						key="knowledgepro.usermanagement.userinfo.department" />:</div>
					</td>
					<td width="25%" height="25" class="row-even" align="left">&nbsp;<html:select
						property="searchDepartment" styleClass="comboExtraLarge">
						<html:option value="">
							<bean:message key="knowledgepro.admin.select" />
						</html:option>
						<html:optionsCollection property="department"
							label="value" value="key" />
					</html:select></td>
					<td width="25%" height="25" class="row-odd">&nbsp;</td>
					<td width="25%" height="25" class="row-even">&nbsp;</td>
                  
                  </tr>
              </table>                
              </td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="35" align="center"><html:button property="" styleClass="formbutton" value="Search" onclick="searchUsers()"></html:button></td>
          </tr>
        </table></td>


      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>