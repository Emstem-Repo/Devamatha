<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<script language="JavaScript" src="js/inventory/purchaseOrder.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="inventory.purchaseReturn.main.label"/> </title>
<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">


<link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<html:form action="/purchaseReturnSubmit">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="formName" value="purchaseReturnForm"/>


<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/> &gt;&gt; <bean:message key="inventory.purchaseReturn.main.label"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="inventory.purchaseReturn.main.label"/></td>
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
                  <td width="25%" height="25" class="row-odd" ><div align="right"><bean:message key="inventory.purchaseOrder.orderNo.label"/></div></td>
                  <td width="25%" class="row-even" ><bean:write name="purchaseReturnForm" property="purchaseOrderNo"/> </td>
                  <td width="25%" class="row-odd" ><div align="right">Vendor Name and Address :</div></td>
                  <td width="25%" class="row-even" ><bean:write name="purchaseReturnForm" property="purchaseOrder.vendorName"/></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right">Purchase Order Date :</div></td>
                  <td class="row-even" ><bean:write name="purchaseReturnForm" property="purchaseOrder.orderDate"/></td>
                  <td class="row-odd" ><div align="right">Purchase Return Date :</div></td>
                  <td class="row-even" ><table width="50" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><html:text property="purchaseRtnDate" size="10" maxlength="10" styleId="rtnDt"></html:text><script language="JavaScript">
	new tcal ({
		// form name
		'formname': 'purchaseReturnForm',
		// input name
		'controlname': 'rtnDt'
	});</script> </td>
  </tr>
</table></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right">Vendor Bill No. :</div></td>
                  <td class="row-even" ><html:text property="vendorBillNo" size="10" maxlength="10"></html:text> </td>
                  <td class="row-odd" ><div align="right">Vendor Bill Date. :</div></td>
                  <td class="row-even" ><table width="50" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><html:text property="vendorBillDt" size="10" maxlength="10" styleId="vndDt"></html:text><script language="JavaScript">
	new tcal ({
		// form name
		'formname': 'purchaseReturnForm',
		// input name
		'controlname': 'vndDt'
	});</script> </td>
  </tr>
</table></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right">Reason for Return :</div></td>
                  <td colspan="3" class="row-even" ><html:textarea property="returnReason" cols="10" rows="3"></html:textarea></td>
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
        <td class="heading">      
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="44" height="25" class="row-odd" ><div align="center">Sl.No </div></td>
                  <td class="row-odd" >Item Name</td>
                  <td class="row-odd" >Received Quantity</td>
				  <td class="row-odd" >Already Returned</td>
                  <td class="row-odd" >Return Quantity</td>
                  </tr>
				<logic:notEmpty property="returnItems" name="purchaseReturnForm">
				<nested:iterate property="returnItems" name="purchaseReturnForm" id="item" indexId="count" >
               	  <tr >
                  <td height="25" class="row-even"><div align="center"><%=count+1 %></div></td>
                  <td width="554" class="row-even" ><bean:write property="invItemId.name" name="item"/> </td>
				  <td width="554" class="row-even" ><bean:write property="recievedQty" name="item"/></td>
                  <td width="454" class="row-even" ><bean:write property="alreadyRtndUnits" name="item"/></td>
                  <td width="154" class="row-even" ><nested:text property="quantity" size="5" maxlength="5"></nested:text> </td>
                  </tr>
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
          </table>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right"><html:button property="" styleClass="formbutton" value="Add" onclick="submitPurchaseReturn('submitFinalPurchaseReturn')"/></td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"/></td>
          </tr>
        </table>      
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