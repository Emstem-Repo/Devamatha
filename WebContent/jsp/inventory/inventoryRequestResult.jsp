<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html:form action="/InventoryRequest">
<html:hidden property="method" value="getInventoryLocation" />
<table width="99%" border="0">
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"> <bean:message key="knowledgepro.inventory" /> &gt;&gt; <bean:message key="knowledgepro.inventory.inventoryrequest" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/Tcenter.gif" class="heading_white"> <bean:message key="knowledgepro.inventory.inventoryrequest" /></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td height="25" colspan="2" ><div align="right"></div></td>
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
                <td width="15%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.slno" /></td>
                <td width="17%" class="row-odd" align="center"><bean:message key="knowledgepro.inventory.itemname" /></td>
                <td width="17%" class="row-odd" align="center"><bean:message key="knowledgepro.inventory.requestby" /></td>
                <td width="17%" class="row-odd" align="center"><bean:message key="knowledgepro.inventory.requestedquantity" /></td>
                <td width="17%" class="row-odd" align="center"><bean:message key="knowledgepro.inventory.availablequantity" /></td>
                <td width="17%" class="row-odd" align="center"><bean:message key="knowledgepro.inventory.difference" /></td>
              </tr>
              <logic:iterate name="inventoryRequestForm" property="inventoryRequestList" id="iRequestList" indexId="count">
					<c:choose>
						<c:when test="${count%2 == 0}">
							<tr class="row-even">
						</c:when>
						<c:otherwise>
							<tr class="row-white">
						</c:otherwise>
					</c:choose>
                <td height="25" align="center"><c:out value="${count+1}" /></td>
                <td align="center"><bean:write name="iRequestList" property="itemName" /></td>
                <td align="center"><bean:write name="iRequestList" property="requestedBy" /></td>
                <td align="center"><bean:write name="iRequestList" property="requestedQuantity" /></td>
                <td align="center"><bean:write name="iRequestList" property="availableQuantity" /></td>
                <td align="center"><bean:write name="iRequestList" property="difference" /></td>
              </tr>
              </logic:iterate>
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
            <td width="49%" height="35" align="center"><html:submit styleClass="formbutton"><bean:message key="knowledgepro.admin.back" /></html:submit></td>
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