<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<html:form action="/AMCDueReport" method="post">
	<html:hidden property="method" styleId="method" value="getDetails" />	
	<html:hidden property="formName" value="amcDueIn30DaysForm" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
	<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.inventory.amc.details.amc.due" />
			&gt;&gt;</span></span>
		</td>
	</tr>
	<tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.inventory.amc.details.amc.due"/> </td>
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
            <td width="5" background="images/left.gif"></td>
            <td width="100%" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
				<tr>
                  <td width="50%" height="25" class="row-odd" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.date"/></td>
					<td width="50%" class="row-even" align="left">
					<html:text property="date" styleId="date" size="11" maxlength="11"></html:text>
					<script language="JavaScript">
						new tcal( {
							// form name
							'formname' :'amcDueIn30DaysForm',
							// input name
							'controlname' :'date'
						});
					</script>
					</td>
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
            <td height="35" align="center"><html:submit styleClass="formbutton" value="Search"></html:submit></td>
          </tr>
        </table>
		</td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>