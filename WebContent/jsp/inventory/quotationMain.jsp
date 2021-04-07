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


<title><bean:message key="inventory.quotation.main.label"/></title>

<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">


<link href="css/styles.css" rel="stylesheet" type="text/css">



</head>
<script type="text/javascript">
function calculateTotalCost(){
	var totalCost=0;
	var totcst="0.0";
	var additionalCost=0.0;
	var additionalDiscount=0.0;
	var totalDisCost=0.0;
	var totalVatCost=0.0;
	var serviceTax=0.0;
	
	var count=document.getElementById("totalTOCount").value;
	for(i=1; i <=count; i++){
		var purCost="0.0";
		var qty="0.0";
		var disCost="0.0";
		var vatPerc="0.0";
		var vatCost="0.0";
		if(document.getElementById("purchaseCost_"+i).value !=null && document.getElementById("purchaseCost_"+i).value!=''){
			purCost=parseFloat(document.getElementById("purchaseCost_"+i).value);
		}

		if(document.getElementById("qty_"+i).value!=null && document.getElementById("qty_"+i).value!=''){
			 qty=parseFloat(document.getElementById("qty_"+i).value);
		}

		if(document.getElementById("discount_"+i).value !=null && document.getElementById("discount_"+i).value!=''){
				disCost=parseFloat(document.getElementById("discount_"+i).value);
				totalDisCost=totalDisCost+disCost;
			}
			
		document.getElementById("totalPrice_"+i).value=roundNumber(((purCost*qty)-disCost),2);

		if(document.getElementById("vatPerc_"+i).value !=null && document.getElementById("vatPerc_"+i).value!=''){
				vatPerc=parseFloat(document.getElementById("vatPerc_"+i).value);
				vatCost= parseFloat(((document.getElementById("totalPrice_"+i).value)*vatPerc)/100);
				totalVatCost=totalVatCost+roundNumber(vatCost,2);
			}
		document.getElementById("vatCost_"+i).value=roundNumber(vatCost,2);	
		totalCost=totalCost+roundNumber(((purCost*qty)-disCost),2);
	}
	
	document.getElementById("totalVatAmt").value=totalVatCost;
	document.getElementById("totalPriceExcludingVat").value=totalCost;
	
	if(document.getElementById("addnCost").value !=null && document.getElementById("addnCost").value !=''){
		additionalCost=parseFloat(document.getElementById("addnCost").value);
	}
	if(document.getElementById("addnDiscount").value !=null && document.getElementById("addnDiscount").value !=''){
		additionalDiscount=document.getElementById("addnDiscount").value;
		totalDisCost=totalDisCost+parseFloat(document.getElementById("addnDiscount").value);
	}
	document.getElementById("totalDiscount").value=totalDisCost;
	if(document.getElementById("serviceTax").value!=null && document.getElementById("serviceTax").value!=''){
		var serviceTaxPerc=parseFloat(document.getElementById("serviceTax").value);
		serviceTax=parseFloat(((totalCost+totalVatCost+additionalCost-additionalDiscount)*serviceTaxPerc)/100);
	}
	document.getElementById("serviceTaxCost").value=roundNumber(serviceTax,2);
	document.getElementById("totCost").value=roundNumber((totalCost+additionalCost-additionalDiscount+serviceTax+totalVatCost),2);
}

function setUOMNameAndPCost(itemId,countNo){
	var count=countNo.split("_")[1];
	document.getElementById("itemToCountNo").value=count;
	if(document.getElementById("UOM_"+itemId) != null && document.getElementById("UOM_"+itemId).value!=null && document.getElementById("UOM_"+itemId).value!='' && itemId!='AddNew'){
	document.getElementById("uom_"+count).value=document.getElementById("UOM_"+itemId).value;
	document.getElementById("purchaseCost_"+count).value=document.getElementById("pCost_"+itemId).value;
	}
}
function roundNumber(num, dec) {
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}
</script>
<html:form action="/quotationSubmit">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="formName" value="quotationForm"/>
<html:hidden property="deleteItemId" value=""/>
<html:hidden property="superAddNewType" value=""/>
<html:hidden property="superMainPage" value=""/>
<html:hidden property="destinationMethod" value=""/>
<html:hidden property="itemToCountNo" styleId="itemToCountNo"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"> <bean:message key="knowledgepro.inventory"/> &gt;&gt;<bean:message key="inventory.quotation.main.label"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="inventory.quotation.main.label"/></td>
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
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td width="100%" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td class="row-odd" ><div align="right"><bean:message key="inventory.quotation.quoteDt.label"/></div></td>
                  <td class="row-even" ><bean:write property="purchaseDate" name="quotationForm"/></td>
                  <td height="25" class="row-odd" >
                  <logic:equal value="Edit" property="mode" name="quotationForm">
                  <div align="right"> <bean:message key="inventory.quotation.orderNo.label"/></div></logic:equal></td>
                  <td class="row-even" ><logic:equal value="Edit" property="mode" name="quotationForm"><bean:write property="quotationNo" name="quotationForm"/></logic:equal> </td>
                 
                </tr>
                <tr >
                  <td width="18%" height="25" class="row-odd" ><div align="right"><bean:message key="inventory.purchaseOrder.vendorname.label"/></div></td>
                  <td width="19%" class="row-even" ><label> <bean:write property="vendorName" name="quotationForm"/></label></td>
                  <td width="13%" class="row-odd" ><div align="right"><bean:message key="inventory.purchaseOrder.remark.label"/></div></td>
                  <td width="18%" class="row-even" ><bean:write property="remarks" name="quotationForm"/></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right"> <bean:message key="inventory.purchaseOrder.t&c.label"/></div></td>
                  <td  class="row-even" ><c:out value='${quotationForm.termConditions}' escapeXml="false" /></td>
                  <td height="25" class="row-odd" ><div align="right"> <bean:message key="knowledgepro.inventory.InvCampus"/>:</div></td>
                  <td  class="row-even" ><bean:write property="campusName" name="quotationForm"/></td>
                  </tr>
				<tr>
				   <td height="25" class="row-odd" ><div align="right"> <bean:message key="knowledgepro.admin.InvCompany"/>:</div></td>
                  <td  class="row-even" ><bean:write property="companyName" name="quotationForm"/></td>
                  <td class="row-odd"></td>
                  <td class="row-even"></td>
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
            <td width="100%" valign="top">
				<table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
                  <td width="20%" class="row-odd"><bean:message key="inventory.purchaseOrder.itemname.label"/> </td>
                  <td width="5%" class="row-odd"><bean:message key="inventory.purchaseOrder.quantity.label"/> </td>
                  <td width="5%" class="row-odd"><bean:message key="inventory.purchaseOrder.uom.label"/> </td>
                  <td width="15%" class="row-odd"><bean:message key="inventory.purchaseOrder.pcost.label"/> </td>
                  <td width="15%" class="row-odd"><bean:message key="inventory.quotationOrder.discount.label"/> </td>
                  <td width="15%" class="row-odd"><bean:message key="inventory.purchaseOrder.totalprc.label"/> </td>
                  <td width="5%" class="row-odd"><bean:message key="inventory.purchaseOrder.vat.label"/> % </td>
                  <td width="10%" class="row-odd"><bean:message key="knowledgepro.inventory.vendor.vat.amt"/></td>
                  <td width="5%" class="row-odd"><bean:message key="knowledgepro.delete"/> </td>
                </tr>
				<logic:notEmpty property="purchaseItemList" name="quotationForm">
				<nested:iterate property="purchaseItemList" name="quotationForm" id="purchaseItemList" indexId="count">
				<%
				String dynamicStyle="";
				if(count%2!=0){
					dynamicStyle="row-white";
				}else{
					dynamicStyle="row-even";
				}
				%>
				<logic:notEmpty property="itemList" name="purchaseItemList">
				<nested:iterate property="itemList" name="purchaseItemList" id="itemList">
				<bean:define id="uomName" property="invUomName" name="itemList"></bean:define>
				<bean:define id="purCost" property="purchaseCost" name="itemList"></bean:define>
				<bean:define id="id" property="id" name="itemList"></bean:define>
				<input type="hidden" id="UOM_<%=id %>" name="UOM_<%=id %>" value="<%=uomName %>">
				<input type="hidden" id="pCost_<%=id %>" name="pCost_<%=id %>" value="<%=purCost %>">
				</nested:iterate></logic:notEmpty>
                <tr >
                  <td height="25" class="<%=dynamicStyle %>"><div align="center"><%=count+1 %></div></td>
                   <%
                  int i=count+1;
                  String itemId="selectedItemId_"+i;
                  %>
                   <td height="25"  class="<%=dynamicStyle %>" >
                  <nested:select  property="selectedItemId" styleClass="combo" style="width: 220px"  styleId="<%=itemId %>"  onchange="setUOMNameAndPCost(this.value,this.id);addNewMaster(this.form,this.value,'quotationSubmit','ItemMaster','getItemMaster');">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                    <html:option value="AddNew"><bean:message key="knowledgepro.admin.add.new"/></html:option>	
                   <nested:notEmpty property="itemList">
                   		<nested:optionsCollection  property="itemList" label="nameWithCode" value="id"/>
				  </nested:notEmpty>
                  </nested:select>
                   </td>
                   <%   int slNo=count+1;
	                  String qtyId =  "qty_"+slNo;%>
                 <td  class="<%=dynamicStyle %>" ><nested:text styleId="<%=qtyId %>" property="quantity" size="2" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)"  onblur="calculateTotalCost()" /></td>
                  <% String uomId =  "uom_"+slNo;%>
                  <td  class="<%=dynamicStyle %>" ><nested:text styleId="<%=uomId %>" property="invItem.invUomName" size="5" readonly="true"/></td>
                  <% 
                  String purchaseCostId =  "purchaseCost_"+slNo;%>
                  <td class="<%=dynamicStyle %>" ><nested:text styleId="<%=purchaseCostId %>" property="invItem.purchaseCost"  onblur="calculateTotalCost()"  
                  onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" size="8" maxlength="8" /></td>
                 <% String discountCostId =  "discount_" +slNo;%>
                  <td class="<%=dynamicStyle %>" ><nested:text styleId="<%=discountCostId %>" property="discount" 
                   onblur="calculateTotalCost()"  onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" size="8" maxlength="8" /></td>
                 <% String totalPriceId =  "totalPrice_" +slNo;%>
                  <td class="<%=dynamicStyle %>" ><nested:text styleId="<%=totalPriceId %>" property="totalCost"  size="8" maxlength="8" readonly="true"/></td>
                  <% String vatPerc =  "vatPerc_" +slNo;%>
                  <td class="<%=dynamicStyle %>" ><nested:text styleId="<%=vatPerc %>" property="vat"  size="8" maxlength="8" onkeypress="return isDecimalNumberKey(this.value,event)"  onblur="calculateTotalCost()"/></td>
                 <% String vatCost =  "vatCost_" +slNo;%>
                  <td class="<%=dynamicStyle %>" ><nested:text styleId="<%=vatCost %>" property="vatCost"  size="8" maxlength="8" readonly="true"/></td>
                  
                  <td class="<%=dynamicStyle %>" ><nested:notEqual name="quotationForm" property="countId" value="1">
                  <div align="center"><img src="images/delete_icon.gif" width="16" height="16" onclick="deleteQuotePurchaseItem('<nested:write property="countId"/>')"/></div>
                  </nested:notEqual></td>
                </tr>
				</nested:iterate>
			</logic:notEmpty>
			<tr>
			<td colspan="6"  align="right">Total Price Excluding Vat:</td>
			<td ><html:text  property="totalPriceExcludingVat" size="8" maxlength="8" styleId="totalPriceExcludingVat" readonly="true" ></html:text></td>
			<td align="right">Total VAT Amt:</td>
			<td  align="left"><html:text property="totalVatAmt" size="8" maxlength="8" styleId="totalVatAmt" readonly="true" ></html:text></td>
			<td></td>
			</tr>
			<tr> <td width="45%" align="center" colspan="8"><html:button property="" styleClass="formbutton" value="AddMore" onclick="submitQuoteAdditem()"/></td></tr>
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
        <td height="39" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">
        
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="70%" align="right"><div align="right" class="style1"> <bean:message key="inventory.purchaseOrder.additionalcost.label"/> </div></td>
            <td width="5%" class="row-white" ></td>
            <td width="25%" height="25" align="left">
              <html:text property="additionalCost" size="8" maxlength="8" styleId="addnCost" onblur="calculateTotalCost()" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" ></html:text>
            </td>
          </tr>
           <tr>
            <td  align="right"><div align="right" class="style1"> <bean:message key="inventory.quotationOrder.addn.discount.label"/>:</div></td>
            <td  class="row-white" ></td>
            <td  height="25" align="left">
              <html:text property="addnDiscount" size="8" maxlength="8" styleId="addnDiscount" onblur="calculateTotalCost()" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" ></html:text>
            </td>
          </tr>
           <tr>
            <td  align="right"><div align="right" class="style1"> <bean:message key="inventory.quotationOrder.total.discount.label"/>: </div></td>
            <td  class="row-white" ></td>
            <td  height="25" align="left">
              <html:text property="totalDiscount" size="8" maxlength="8" styleId="totalDiscount" readonly="true" ></html:text>
            </td>
          </tr>
          <tr>
            <td align="right"><div align="right" class="style1"> <bean:message key="inventory.purchaseOrder.serviceTax.label"/> % : </div></td>
            <td height="25" class="row-white" > &nbsp;&nbsp;<html:text property="serviceTax" size="3" maxlength="4" styleId="serviceTax" onblur="calculateTotalCost()" onkeypress="return isDecimalNumberKey(this.value,event)" ></html:text></td>
            <td height="25" align="left"><html:text property="serviceTaxCost" size="8" maxlength="8" styleId="serviceTaxCost" readonly="true" ></html:text></td>
          </tr>
			<logic:notEmpty  property="totalCost" name="quotationForm">
			<bean:define id="totalPurchaseCost" property="totalCost" name="quotationForm"></bean:define>
			<input type="hidden" id="totalPurCost" name="totalPurCost" value="<%=totalPurchaseCost %>">
			</logic:notEmpty>
			<logic:notEmpty  property="countId" name="quotationForm">
			<bean:define id="totalCount" property="countId" name="quotationForm"></bean:define>
			<input type="hidden" id="totalTOCount" name="totalTOCount" value="<%=totalCount %>">
			</logic:notEmpty>
          <tr>
            <td align="right" width="58%"><div align="right" class="style2"><bean:message key="inventory.purchaseOrder.totalcost.label"/></div></td>
            <td  class="row-white" ></td>
            <td height="25" align="left">
              <html:text property="totalCost" size="8" maxlength="8" readonly="true" styleId="totCost"> </html:text>
          </td>
          </tr>
        </table> </td>     
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" align="right"><html:button property=""  styleClass="formbutton" value="Submit" onclick="submitQuotationForm('submitFinalQuotation')"></html:button></td>
            <td width="2%" height="24" align="center">&nbsp;</td>
            <td width="49%" height="24" align="left"><html:button property="" styleClass="formbutton" value="Cancel" onclick="submitQuotationForm('initQuotaion')"/></td>
          </tr>
        </table> </td>     
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="2" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
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
<script>
	
</script>