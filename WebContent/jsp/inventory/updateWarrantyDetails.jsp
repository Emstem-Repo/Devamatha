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
<html:form action="/UpdateWarranty">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="formName" value="stockReceiptForm"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs">&gt;&gt; Pending Warranty Details</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" > Pending Warranty Details </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
          <tr>
       <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
       <td class="news">
	<div align="right">
		<FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
		<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
		<FONT color="green"> <html:messages id="msg"
		property="messages" message="true">
		<c:out value="${msg}" escapeXml="false"></c:out>
		<br>
		</html:messages> </FONT></div></td>
       <td valign="top" background="images/Tright_3_3.gif"></td>
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
                  <td width="77" class="row-odd" ><bean:message key="inventory.purchaseOrder.itemcode.label"/> </td>
                  <td width="86" height="31" class="row-odd"><bean:message key="inventory.purchaseOrder.itemname.label"/></td>
                  <td width="86" height="31" class="row-odd">Quantity</td>
                  <td width="119" class="row-odd" colspan="3"><bean:message key="inventory.stockReceipt.warranty.label"/> </td>
                </tr>
				<logic:notEmpty name="stockReceiptForm" property="receiptItems" >
				<nested:iterate name="stockReceiptForm" property="receiptItems" indexId="count" id="item">
				<bean:define id="countIndex" name="item" property="countId"></bean:define>
				<input type="hidden" id="countID" name="countID" >
                <tr >
                  <td height="25" class="row-even"><div align="center"><%=count+1 %></div></td>
                  <td width="77" class="row-even" ><nested:write property="invItem.code"/> </td>
                  <td width="86" height="25" class="row-even" ><nested:write property="invItem.name"/></td>
                  <td width="77" class="row-even" ><nested:write property="quantity"/> </td>
                  	<td width="119" class="row-even" colspan="3" >
						<a href="#" onclick="amcSubmit('<%=countIndex %>')" > Enter Warranty Info</a></div>
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
              <td width="45%" align="right"><html:button property="" styleClass="formbutton" value="Submit" onclick="submitStockReceipt('updateWarrantyDetails')"/></td>
              <td width="45%" height="35" align="left"><html:button property="" styleClass="formbutton" value="Cancel" onclick="submitStockReceipt('initUpdateWarrantyDetails')"/></td>
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
