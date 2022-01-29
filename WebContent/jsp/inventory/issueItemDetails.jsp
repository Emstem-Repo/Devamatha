<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function submitIssueItemDetails(){
	document.getElementById("method").value = "submitIssueItemDetails";
	document.issueMaterialForm.submit();
}
function goToHome(){
	document.location.href = "IssueMaterial.do?method=initIssuematerial";
}
</script>
<html:form action="/IssueMaterial" method="post">
<html:hidden property="method" styleId="method"/>
<html:hidden property="formName" value="issueMaterialForm" />
<table width="99%" border="0">  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"> <bean:message key="knowledgepro.inventory"/> &gt;&gt; <bean:message key="knowledgepro.inventory.issue.for.the.mateiral"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/Tcenter.gif" class="heading_white" > <bean:message key="knowledgepro.inventory.issue.for.the.mateiral"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading">
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
		<FONT color="green"><html:messages id="msg"
		property="messages" message="true">
		<c:out value="${msg}" escapeXml="false"></c:out>
		<br>
		</html:messages> </FONT></div>
        <div align="right"> <span class='mandatoryfield'><bean:message
		key="knowledgepro.mandatoryfields" /></span></div></td>
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
                  <td height="25" class="row-odd" ><div align="right"><bean:message
					key="knowledgepro.inventory.raised.by.col" /></div></td>
                  <td class="row-even" ><bean:write name="issueMaterialForm" property="materialTO.raisedBy"/>
                  <bean:write name="issueMaterialForm" property="department"/>
                  </td>
                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.raised.on.col"/> </div></td>
                  <td class="row-even" ><bean:write name="issueMaterialForm" property="materialTO.raisedOn"/></td>
                </tr>
                <tr>
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.status.col"/> </div></td>
                  <td height="25" class="row-even" ><bean:write name="issueMaterialForm" property="materialTO.status"/></td>
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.InvLocation"/> </div></td>
                  <td height="25" class="row-even" ><bean:write name="issueMaterialForm" property="materialTO.inventoryName"/></td>
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
        <td colspan="2" valign="top" >&nbsp;</td>
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
                <td width="40" height="25" class="row-odd" ><bean:message key="knowledgepro.slno"/></td>
                <td class="row-odd" ><bean:message key="knowledgepro.inventory.item"/> </td>
                <td class="row-odd" ><bean:message key="knowledgepro.inventory.requested.quantity"/> </td>
                 <td class="row-odd" ><bean:message key="knowledgepro.inventory.availablequantity"/> </td>
                <td class="row-odd" ><bean:message key="knowledgepro.inventory.issued.quantity"/> </td>
                </tr>
                <c:set var="temp" value="0" />
				<logic:notEmpty name="issueMaterialForm" property="itemList">
				<nested:iterate name="issueMaterialForm" property="itemList" id="item" indexId="count">
				<c:choose>
				<c:when test="${temp == 0}">
              <tr>
                <td height="25" class="row-even"><c:out value="${count + 1}" /></td>
                <td width="147" class="row-even" ><nested:write name="item" property="nameWithCode"/> </td>
                <td width="126" class="row-even" ><nested:write name="item" property="requestedQuantity"/></td>
                 <td width="126" class="row-even" ><nested:write name="item" property="availableQuantity"/></td>
                <td width="175" class="row-even" >
                <nested:text property="quantityIssued" maxlength="9" size="9"></nested:text>
                </td>
                </tr>
                <c:set var="temp" value="1" />
				</c:when>
				<c:otherwise>
              <tr>
                <td height="25" class="row-white"><c:out value="${count + 1}" /></td>
                <td class="row-white" ><nested:write name="item" property="nameWithCode"/></td>
                <td class="row-white" ><nested:write name="item" property="requestedQuantity"/></td>
                <td class="row-white" ><nested:write name="item" property="availableQuantity"/></td>
                <td class="row-white" >
                  <nested:text property="quantityIssued" maxlength="9" size="9"></nested:text>
              </td>
                </tr>
                <c:set var="temp" value="0" />
				</c:otherwise>
				</c:choose>
				</nested:iterate>
				</logic:notEmpty>
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
        <td colspan="2" class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right"><html:button property="" value="Submit" styleClass="formbutton" onclick="submitIssueItemDetails()"/>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left"><html:cancel value="Reset" styleClass="formbutton"></html:cancel>
            &nbsp;&nbsp;&nbsp;
			<html:button property ="" styleClass="formbutton" value="Cancel"
			onclick="goToHome()"/></td>
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