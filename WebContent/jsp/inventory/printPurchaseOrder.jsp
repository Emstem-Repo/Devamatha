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
<script language="JavaScript" src="calendar_us.js"></script>
<link rel="stylesheet" href="calendar.css">
<link href="css/styles.css" rel="stylesheet" type="text/css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="inventory.purchaseOrder.main.label"/></title>


</head>

<html:form action="/purchaseOrderSubmit">



<table width="100%">
	 <tr>
	   <td colspan="4" height="200"></td>
	 </tr>
	 <tr>
	 	<td></td>
	 	<td></td>
	 	<td></td>
	    <td><div align="right"><b><bean:message key="inventory.purchaseOrder.orderDt.label"/> </b><bean:write property="purchaseDate" name="purchaseOrderForm"/></div></td>
	 </tr>
	 <tr>
	    <td></td>
	 	<td></td>
	 	<td></td>
	    <td><div align="right"><b> <bean:message key="inventory.purchaseOrder.orderNo.label"/></b>  <bean:write property="purchaseOrderNo" name="purchaseOrderForm"/></div></td>
	 </tr>
	 <tr>
	 <td><div align="left"><b>To </b> </div></td>
	 <td></td>
	 <td></td>
	 <td></td>
	 </tr>
	 <tr>
	    <td width="20%"><div align="left"><b>Vendor Name : </b> </div></td>
	 	<td width="30%"><bean:write property="vendorName" name="purchaseOrderForm"/></td>
	 	<td width="25%"></td>
	 	<td width="25%"></td>
	 </tr>
	 <tr>
	  <td width="20%"><div align="left"><b>Vendor Address : </b> </div></td>
		<td width="30%"><bean:write property="vendorAddress1" name="purchaseOrderForm"/><br/>
	    <bean:write property="vendorAddress2" name="purchaseOrderForm"/></td>
	 	<td width="25%"></td>
	 	<td width="25%"></td>
	 </tr>
	  <tr>
	    <td width="15%"><div align="left"><b>Deliver To : </b> </div></td>
	    <td width="30%"><div align="left"><b>Delivery Campus & Location </b> </div></td>
	    <td width="25%"><div align="left"><b>Delivery Schedule </b> </div></td>
	    <td width="30%">
	    <logic:notEmpty name="purchaseOrderForm" property="quotationNo">
	    <div align="left"><b>Quotation Ref No/Date </b> </div></logic:notEmpty>
	    </td>
	 </tr>
	   <tr>
	    <td></td>
	    <td><div align="left"><bean:write property="campusName" name="purchaseOrderForm"/>-<bean:write property="locationName" name="purchaseOrderForm"/></div></td>
	    <td><div align="left"><bean:write property="deliverySchedule" name="purchaseOrderForm"/> </div></td>
	    <td>
	    <logic:notEmpty name="purchaseOrderForm" property="quotationNo"><div align="left">
	    <bean:write property="quotationNo" name="purchaseOrderForm"/>/<bean:write property="quotationDate" name="purchaseOrderForm"/></div>
	    </logic:notEmpty>
	    </td>
	 </tr>
	 <tr>
	   <td height="25"></td>
	 </tr>
</table>
<table width="100%" cellspacing="0" cellpadding="0" border="1">
                <tr >
                  <td width="37" ><div align="center" ><b><bean:message key="knowledgepro.slno"/> </b></div></td>
                  <td width="160" ><div align="left"><b><bean:message key="knowledgepro.inventory.purchase.order.print.article.label"/></b> </div></td>
                  <td width="60" ><div align="center"><b><bean:message key="inventory.purchaseOrder.quantity.label"/></b> </div></td>
                  <td width="79" ><div align="center"><b><bean:message key="inventory.purchaseOrder.uom.label"/></b> </div></td>
                  <td width="66" ><div align="center"><b><bean:message key="knowledgepro.venderwise.report.unit.cost"/> </b></div></td>
                  <logic:equal value="true" property="discountExists" name="purchaseOrderForm">
                  <td width="66" ><div align="center"><b>Unit <bean:message key="inventory.quotationOrder.discount.label"/> </b></div></td>
                  </logic:equal>
                  <logic:equal value="true" property="vatExists" name="purchaseOrderForm">
                  <td width="66" ><div align="center"><b>VAT %</b></div></td>
                  </logic:equal>
                  <td width="66" ><div align="center"><b><bean:message key="inventory.purchaseOrder.totalprc.label"/> </b></div></td>
                </tr>
				<logic:notEmpty property="purchaseItemList" name="purchaseOrderForm">
				<nested:iterate property="purchaseItemList" name="purchaseOrderForm" id="orderItem" indexId="count">
				<tr >
                  <td height="25"  width="37" ><div align="center"><%=count+1 %></div></td>
                  <td width="160" ><div align="left"><nested:write property="invItem.name" name="orderItem"/></div></td>
                  <td width="60"  ><div align="center"><nested:write property="quantity" name="orderItem"/></div></td>
                  <td width="79"  ><div align="center"><nested:write property="invItem.invUomName" name="orderItem"/></div></td>
                  <td width="66"  ><div align="center"><nested:write property="invItem.purchaseCost" name="orderItem"/></div></td>
                   <logic:equal value="true" property="discountExists" name="purchaseOrderForm">
                  <td width="66"  ><div align="center"><nested:write property="discount" name="orderItem"/></div></td>
                  </logic:equal>
                  <logic:equal value="true" property="vatExists" name="purchaseOrderForm">
                  <td width="66"  ><div align="center"><nested:write property="vat" name="orderItem"/></div></td>
                  </logic:equal>
                  <td width="66" ><div align="center"><nested:write property="totalCostInclusiveVat" name="orderItem"/></div></td>
                 </tr>
                
				</nested:iterate>
			</logic:notEmpty>
			    <logic:notEmpty property="serviceTax" name="purchaseOrderForm" >
			    <tr>
			        <td></td>
			        <td></td>
			        <td></td>
			        <td></td>
			        <logic:equal value="true" property="discountExists" name="purchaseOrderForm">
			        <td></td></logic:equal>
			       <logic:equal value="true" property="vatExists" name="purchaseOrderForm"> <td></td></logic:equal>
			        <td><div align="center"><b>Service Tax @</b><bean:write property="serviceTax" name="purchaseOrderForm"/>%</div></td>
			        <td><div align="center"><bean:write property="serviceTaxCost" name="purchaseOrderForm"/></div></td>
			    </tr></logic:notEmpty>
			    <logic:notEmpty property="additionalCost" name="purchaseOrderForm" >
			    <tr>
			        <td></td>
			        <td></td>
			        <td></td>
			        <td></td>
			       <logic:equal value="true" property="discountExists" name="purchaseOrderForm">
			        <td></td></logic:equal>
			       <logic:equal value="true" property="vatExists" name="purchaseOrderForm"> <td></td></logic:equal>
			        <td><div align="center"><b>Additional Cost</b></div> </td>
			        <td><div align="center"><bean:write property="additionalCost" name="purchaseOrderForm"/></div></td>
			    </tr></logic:notEmpty>
			   <logic:notEmpty property="addnDiscount" name="purchaseOrderForm" >
			    <tr>
			        <td></td>
			        <td></td>
			        <td></td>
			        <td></td>
			       <logic:equal value="true" property="discountExists" name="purchaseOrderForm">
			        <td></td></logic:equal>
			       <logic:equal value="true" property="vatExists" name="purchaseOrderForm"> <td></td></logic:equal>
			        <td><div align="center"><b>Additional Discount</b></div></td>
			        <td><div align="center"><b><bean:write property="addnDiscount" name="purchaseOrderForm"/></b></div></td>
			    </tr></logic:notEmpty>
			    <tr>
			        <td></td>
			        <td></td>
			        <td></td>
			        <td></td>
			        <logic:equal value="true" property="discountExists" name="purchaseOrderForm">
			        <td></td></logic:equal>
			       <logic:equal value="true" property="vatExists" name="purchaseOrderForm"> <td></td></logic:equal>
			        <td><div align="center"><b>Total Cost</b></div></td>
			        <td><div align="center"><b><bean:write property="totalCost" name="purchaseOrderForm"/></b></div></td>
			    </tr>
              </table>
              <table width="100%">
              <tr><td colspan="2" height="25"></td></tr>
                <tr>
                <td width="20%"  valign="top"><b>Terms & Conditions:</b>  </td>
                <td  width="80%" >
                <c:out value='${purchaseOrderForm.termConditions}' escapeXml="false" />
                </tr>
                 <tr>
                <td height="50" colspan="2"></td>
                </tr>
                <tr>
                 <td width="20%" ></td>
                <td width="80%"><div align="right"><b>Authorized Signatory</b></div></td>
                </tr>
              </table>
                <table width="100%" cellspacing="0" cellpadding="0" border="1">
               	<tr><td width="100%"><table width="100%">
                <tr>
                <td colspan="4" height="25"><div align="center"><b>For Office Use</b></div></td>
                </tr>
                 <tr>
                  <td width="20%" align="left">PR Ref #</td>
               	  <td width="15%" align="left"></td>
                  <td width="35%" align="right">Date:</td>
                  <td width="30%" align="left"></td>
                </tr>
                <tr>
                  <td width="20%" align="left">Invoice #</td>
               	  <td width="15%" align="left"></td>
                  <td width="35%" align="left">and/or supporting documents attached</td>
                  <td width="30%" align="left"><img src="images/checkbox.gif" />Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="images/checkbox.gif" />No </td>
                </tr>
                 <tr>
                  <td width="20%" align="left">Date Paid</td>
               	  <td width="15%" align="left"></td>
                  <td width="35%" align="right">Cheque #</td>
                  <td width="30%" align="left"></td>
                </tr></table>
                </td></tr>
              </table> 

</html:form>
<script>
	print();
</script>