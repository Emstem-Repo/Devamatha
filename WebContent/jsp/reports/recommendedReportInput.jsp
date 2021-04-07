<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function submitReport() {
	document.getElementById("method").value ="submitRecommendedCandidates";
}

function resetAttReport()	{	
	document.getElementById("program").selectedIndex = 0;
	document.getElementById("recommendedBy").selectedIndex = 0;
	resetErrMsgs();
}
</script>
<html:form action="/candidateRecommended" method="post">
<html:hidden property="method" styleId="method" value="submitRecommendedCandidates"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="recommendedCandidatesForm" />

<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.reports.candidatesrecommendedby.label"/> &gt;&gt;</span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.reports.candidatesrecommendedby.label"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="45" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr bgcolor="#FFFFFF">
				<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>	
	            <td colspan="6" class="body" align="left">
	             <div id="errorMessage">
	            <FONT color="red"><html:errors/></FONT>
	             <FONT color="green">
					<html:messages id="msg" property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages>
	            </FONT>
	            </div>
	            </td>
	          </tr>

            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
          	<tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
            <tr>
                <td width="19%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.prog" /></div></td>
                <td width="26%" height="25" class="row-even" >
			      <html:select name="recommendedCandidatesForm" property="programId" styleClass="combo" styleId="program">
						<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							<logic:notEmpty name="recommendedCandidatesForm" property="programList">		
								<html:optionsCollection name="recommendedCandidatesForm" property="programList" label="name" value="id" />
							</logic:notEmpty>
				  </html:select>	   
				</td>
				
                <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.recommendedby"/>:</div></td>
                <td height="25" class="row-even" >
					<html:select name="recommendedCandidatesForm" property="recommendedBy" styleClass="combo" styleId="recommendedBy">
						<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							<logic:notEmpty name="recommendedCandidatesForm" property="recommenderList">		
								<html:optionsCollection name="recommendedCandidatesForm" property="recommenderList" label="name" value="id" />
							</logic:notEmpty>
					</html:select>
				</td>           
            </tr>
          </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="36" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="45%" height="35">
						<div align="right"><html:submit styleClass="formbutton" onclick="submitReport()">
							<bean:message key="knowledgepro.submit" /></html:submit>
						</div>
					 </td>
					<td width="2%"></td>
					<td width="53">
						<html:button property="" styleClass="formbutton" onclick="resetAttReport()">
						<bean:message key="knowledgepro.admin.reset" /></html:button>
					</td>
				</tr>
			</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="931" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>