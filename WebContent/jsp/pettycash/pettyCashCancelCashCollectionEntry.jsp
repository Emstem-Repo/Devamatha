<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<title>:: CMS ::</title>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>


<html:form action="cancelCashCollection" method="POST">
	<html:hidden property="formName" value="cashCollectionForm"
		styleId="formName" />
	<html:hidden property="pageType" value="4" styleId="pageType" />
	<html:hidden property="method" styleId="method" value="retrieveCashCollection"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.pettycash"/> &gt;&gt;<bean:message key="knowledgepro.pettycash.cancelCashCollection"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.pettycash.cancelCashCollection"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right" class="mandatoryfield">
          <div align="right" class="mandatoryfield"><FONT color="red"> <span class='MandatoryMark'>* Mandatory Fields</span></FONT></div>
           <div id="errorMessage" align="left">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
			</div>
        </div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td width="25%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.pettycash.cancelCashCollection.receiptno"/>: </div></td>
                    <td width="25%" height="25" class="row-even" ><span class="star">
                    <html:text property="number" name="cashCollectionForm"  styleClass="TextBox" styleId="name" size="16" maxlength="20"/>
                    </span></td>
                    <td width="25%" height="25" class="row-odd">
					<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.petticash.financialYear"/> </div>
					</td>
					<td width="25%" height="25" class="row-even">
					<div align="left"><span class="star"> 
					<html:select property="finYear" styleClass="combo"
						styleId="year">
						<html:option value="">-<bean:message
								key="knowledgepro.select" />-</html:option>
						<html:optionsCollection property="financilYearList" label="financialYear" value="id" />
					</html:select> </span></div>
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
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="51%" height="27"><div align="right">
                <html:submit styleClass="formbutton"
										styleId="button"><bean:message key="knowledgepro.submit"/>
							</html:submit>
                 
                </div></td>
                <td width="3%"></td>
                <td width="46%">&nbsp;</td>
              </tr>
            </table>
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
