<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function resetMessages() {
	document.getElementById("invLocation").selectedIndex = 0;
	document.getElementById("startDateid").value = "";
	document.getElementById("endDateid").value = "";
	resetErrMsgs();
}
function submitIssue(){
	document.getElementById("invLocationName").value = document.getElementById("invLocation").options[document.getElementById("invLocation").selectedIndex].text;
	document.requestVsIssueForm.submit();
}
</script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<html:form action="/RequestVsIssue">
<html:hidden property="method" styleId="method" value="RequestVsIssueDetails" />
<html:hidden property="formName" value="requestVsIssueForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="invLocationName" styleId="invLocationName" />
<table width="99%" border="0">
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"> <bean:message key="knowledgepro.inventory" /> &gt;&gt; Locationwise Request Vs Issue &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/Tcenter.gif" class="heading_white">Locationwise Request Vs Issue</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news"><div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
        <div id="errorMessage">
			<FONT color="red"><html:errors /></FONT>
			<FONT color="green"><html:messages id="msg" property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				</html:messages>
			</FONT>
		</div>
        </td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr>
                  <td width="25%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.InvLocation" />:</div></td>
                  <td width="25%" class="row-even">
                  	<html:select property="invLocationId" styleClass="comboLarge" styleId="invLocation">
						<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>									
						<html:optionsCollection property="inventoryLocationList" label="name" value="id"/>
					</html:select>
                  </td>
                  <td width="25%" class="row-odd">&nbsp;</td>
                  <td width="25%" class="row-even">&nbsp;</td>
                </tr>
                <tr>
                 <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.feepays.startdate" />:</div></td>
               	 <td height="25" class="row-even" >
					<html:text name="requestVsIssueForm" property="startDate" styleId="startDateid" size="16" maxlength="16"/>
					<script
					language="JavaScript">
					new tcal( {
						// form name
						'formname' :'requestVsIssueForm',
						// input name
						'controlname' :'startDate'
					});
					</script>
			   	 </td>
                 <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.feepays.enddate" />:</div></td>
                 <td height="25" class="row-even">
					<html:text name="requestVsIssueForm" property="endDate" styleId="endDateid" size="16" maxlength="16"/>
					<script
					language="JavaScript">
					new tcal( {
						// form name
						'formname' :'requestVsIssueForm',
						// input name
						'controlname' :'endDate'
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
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right"><html:button property="" styleClass="formbutton" onclick="submitIssue()"><bean:message key="knowledgepro.submit" /></html:button></td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left"><html:reset styleClass="formbutton" onclick="resetMessages()"><bean:message key="knowledgepro.admin.reset" /></html:reset></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" colspan="2" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>