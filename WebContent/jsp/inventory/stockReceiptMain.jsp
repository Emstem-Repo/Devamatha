<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>

<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<script language="JavaScript" src="js/inventory/purchaseOrder.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="inventory.stockReceipt.main.label"/> </title>
<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
<script type="text/javascript">
function amcSubmit(count)
{
	document.getElementById("countID").value=count;
	document.stockReceiptForm.method.value="initAmcEntryPage";
	document.stockReceiptForm.submit();
}
</script>

<link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<html:form action="/stockReceiptSubmit">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="formName" value="stockReceiptForm"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.inventory"/></span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="inventory.stockReceipt.main.label"/> </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
		<tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td align="left"><div id="errorMessage"><html:errors/></div></td>
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
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="inventory.purchaseOrder.orderNo.label"/></div></td>
                  <td class="row-even" ><bean:write name="stockReceiptForm" property="purchaseOrderNo"/></td>
                  <td class="row-odd" ><div align="right"><bean:message key="inventory.purchaseOrder.vendorname.label"/> </div></td>
                  <td class="row-even" ><bean:write name="stockReceiptForm" property="purchaseOrder.vendorName"/></td>
                  </tr>
                <tr >
                  <td width="18%" height="25" class="row-odd" ><div align="right"><bean:message key="inventory.stockReceipt.vendAddr.label"/> </div></td>
                  <td width="19%" class="row-even" ><label>
                   <bean:write name="stockReceiptForm" property="purchaseOrder.vendorAddr"/></label></td>
                  <td width="13%" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="inventory.stockReceipt.receiptDt.label"/> </div></td>
                  <td width="18%" class="row-even" ><table width="82" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="60"><html:text property="receiptDate" styleId="receiptDate" size="10" maxlength="10" ></html:text></td>
                      <td width="40"><script language="JavaScript">
	new tcal ({
		// form name
		'formname': 'stockReceiptForm',
		// input name
		'controlname': 'receiptDate'
	});</script></td>
                    </tr>
                  </table></td>
                  </tr>
                <tr >
                  <td height="25" align="right" class="row-odd" ><span class="Mandatory">*</span><bean:message key="inventory.stockReceipt.invLoc.label"/> </td>
                  <td colspan="3" class="row-even" ><html:select styleClass="combo" property="inventoryId">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                    <logic:notEmpty property="invLocations" name="stockReceiptForm">
	                    <html:optionsCollection property="invLocations" name="stockReceiptForm" label="name" value="id"/>
					</logic:notEmpty>	
                    </html:select></td>
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
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="36" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
                  <td class="row-odd" ><bean:message key="inventory.purchaseOrder.itemcode.label"/> </td>
                  <td width="86" height="31" class="row-odd"><bean:message key="inventory.purchaseOrder.itemname.label"/></td>
                  <td width="71" class="row-odd"><bean:message key="inventory.purchaseOrder.uom.label"/> </td>
                  <td width="96" class="row-odd"><bean:message key="inventory.stockReceipt.ordPchPrice.label"/>  </td>
                  <td width="86" class="row-odd"><bean:message key="inventory.stockReceipt.rcvPchPrice.label"/>  </td>
                  <td width="91" class="row-odd"><bean:message key="inventory.stockReceipt.ordQty.label"/> </td>
				  <td width="91" class="row-odd">Already received</td>
                  <td width="119" class="row-odd"><bean:message key="inventory.stockReceipt.rcvQty.label"/> </td>
                  <td width="119" class="row-odd" colspan="3"><bean:message key="inventory.stockReceipt.warranty.label"/> </td>
                  
                </tr>
				<logic:notEmpty name="stockReceiptForm" property="receiptItems" >
				<nested:iterate name="stockReceiptForm" property="receiptItems" indexId="count" id="item">
				<bean:define id="countIndex" name="item" property="countId"></bean:define>
				<input type="hidden" id="countID" name="countID" >
                <tr >
                  <td height="25" class="row-even"><div align="center"><%=count+1 %></div></td>
                  <td width="77" class="row-even" ><nested:write property="invItem.code"/> </td>
                  <td height="25" class="row-even" ><nested:write property="invItem.name"/></td>
                  <td class="row-even" ><nested:write property="uom"/></td>
                  <td class="row-even" ><nested:write property="orderPrice"/></td>
                  <td class="row-even" ><nested:text property="purchasePrice" size="8" maxlength="8" /></td>
                  <td class="row-even" ><nested:write property="orderQty"/></td>
				  <td class="row-even" ><nested:write property="alreadyRcvUnit"/></td>
                  <td class="row-even" ><nested:text property="quantity" size="5" maxlength="5" /></td>
                  	<td class="row-even" colspan="3" >
                 		<logic:equal value="true" name="item" property="warranty"><div align="center">
							<a href="#" onclick="amcSubmit('<%=countIndex %>')" > Enter Warranty Info</a></div>
                 		</logic:equal>
                  	</td>
                </tr>
			</nested:iterate>
			</logic:notEmpty>
              
          
        </table>  </td>     
        <td width="5" height="30"  background="images/right.gif"></td>
      </tr>
		<tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td  background="images/05.gif"></td>
            <td><img src="images/06.gif" width="5" height="5"/></td>
          </tr>
		</table>
		
		</td>     
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        </table>
          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="45%" align="right"><html:button property="" styleClass="formbutton" value="Submit" onclick="submitStockReceipt('submitFinalStockReceipt')"/></td>
              <td width="10%" height="35" align="center">&nbsp;</td>
              <td width="45%" height="35" align="left"><html:button property="" styleClass="formbutton" value="Cancel" onclick="submitStockReceipt('initStocksReceipt')"/></td>
            </tr>
          </table></td>
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
