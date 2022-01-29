<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar.js"></script>
<script type="text/javascript">
function goToItemSelectionPage (){
	document.getElementById("fromInventoryName").value = document
	.getElementById("fromInventoryId").options[document
	.getElementById("fromInventoryId").selectedIndex].text;

	document.getElementById("toInventoryName").value = document
	.getElementById("fromInventoryId").options[document
	.getElementById("toInventoryId").selectedIndex].text;	
	document.getElementById("method").value = "initStockTransferItemSelection";	
}
</script>
<html:form action="/StockTransfer" method="post">
<html:hidden property="method" styleId="method"/>
<html:hidden property="formName" value="stockTransferForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="fromInventoryName" styleId="fromInventoryName" />
<html:hidden property="toInventoryName" styleId="toInventoryName"/>
<table width="99%" border="0">  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/> &gt;&gt; <bean:message key="knowledgepro.inventory.stocktransfer"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.inventory.stocktransfer"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"> <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
		<FONT color="green"><html:messages id="msg"
		property="messages" message="true">
		<c:out value="${msg}" escapeXml="false"></c:out>
		<br>
		</html:messages> </FONT></div><div align="right"> <span class='mandatoryfield'><bean:message
		key="knowledgepro.mandatoryfields" /></span></div></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
      <tr>
        <td valign="top" background="images/Tright_03_03.gif">
     </td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
        <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.transferno.col"/> </div></td>
                  <td class="row-even" ><bean:write name="stockTransferForm" property="transferNo"/> </td>
                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.inventory.transferdate.col"/> </div></td>
                  <td class="row-even" ><table width="82" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="60">
                      <html:text property="transferDate" readonly="false"  size="10" maxlength="10"
						styleId="transferDate"></html:text>
                     </td>
                      <td width="40">
                      <script language="JavaScript">
				new tcal ({
				// form name
				'formname': 'stockTransferForm',
				// input name
				'controlname': 'transferDate'
				});</script></td>
                    </tr>
                  </table></td>
          </tr>
           <tr >
                  <td width="18%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.from.inventory.location.col"/> </div></td>
                  <td width="19%" class="row-even" ><label>                 
                  <html:select property="fromInventoryId" styleId="fromInventoryId" styleClass="comboLarge">
                  <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
                    <logic:notEmpty name="stockTransferForm" property="inventoryList">
					<html:optionsCollection property="inventoryList" name="stockTransferForm" label="name" value="id" />
					</logic:notEmpty>
                  </html:select>
                  </label></td>
                  <td width="13%" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.to.inventory.location.col"/></div></td>
                  <td width="18%" class="row-even" >
                   <html:select property="toInventoryId" styleId="toInventoryId" styleClass="comboLarge">
                  <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
                    <logic:notEmpty name="stockTransferForm" property="inventoryList">
					<html:optionsCollection property="inventoryList" name="stockTransferForm" label="name" value="id" />
					</logic:notEmpty>
                  </html:select>
          </tr>
          <tr>
              <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.reservation.remarks"/> </div></td>
              <td colspan="3" class="row-even" ><html:textarea property="remarks" cols="18" rows="2" styleId="remarks"></html:textarea></td>
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
            <td width="49%" height="35" align="right">
            <html:submit property="" value="Continue" styleClass="formbutton" onclick="goToItemSelectionPage()"></html:submit>
            </td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left">
            <html:button property="" value="Reset" styleClass="formbutton" onclick="resetFieldAndErrMsgs()">
            </html:button></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        </table>
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