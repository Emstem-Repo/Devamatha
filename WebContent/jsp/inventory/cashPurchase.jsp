<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function imposeMaxLength(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 500;
	return (Object.value.length < MaxLen);
}
function prepareItemList(){
	document.getElementById("name").value = document.getElementById("vendorId").options[document.getElementById("vendorId").selectedIndex].text;
	document.getElementById("method").value = "prepareItemListForTransfer";	
	document.cashPurchaseForm.submit();
}
function deleteItem(itemName){
	deleteConfirm = confirm("Are you sure want to delete this entry?");
	if (deleteConfirm) {
		document.getElementById("itemName").value = itemName;	
		document.getElementById("method").value = "deleteItem";	
		document.cashPurchaseForm.submit();
	}
}
function submitCashPurchase(){
	document.getElementById("method").value = "addCashPurchase";	
	document.cashPurchaseForm.submit();
}
function resetMessages() {
	document.getElementById("vendorName").value = "";
	document.getElementById("invLocation").selectedIndex = 0;
	document.getElementById("comments").value = "";
	document.getElementById("itemName").value = "";
	document.getElementById("quantity").value = "";
	document.getElementById("purchasePrice").value = "";
	document.getElementById("date").value = "";
	resetErrMsgs();
}
</script>	
<html:form action="/CashPurchase">
<html:hidden property="method" styleId="method" value="" />
<html:hidden property="formName" value="cashPurchaseForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="name" value="" styleId="name" />
<table width="99%" border="0">
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"> <bean:message key="knowledgepro.inventory" /> &gt;&gt; <bean:message key="knowledgepro.inventory.cashpurchase" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/Tcenter.gif" class="heading_white"> <bean:message key="knowledgepro.inventory.cashpurchase" /></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news"><div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
        <div id="errorMessage">
			<FONT color="red"><html:errors /></FONT>
			<FONT color="green"><html:messages id="msg" property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				</html:messages>
			</FONT>
		</div>
        </td>
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
                <tr >
                  <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.date" /></div></td>
					<td height="25" class="row-even" align="left">
					<html:text property="date" styleId="date" size="11" maxlength="11"></html:text>
					<script language="JavaScript">
						new tcal( {
							// form name
							'formname' :'cashPurchaseForm',
							// input name
							'controlname' :'date'
						});
					</script>
					</td>

									<%String dynaStyle=""; %>
									<logic:equal value="Other" property="vendorId" name="cashPurchaseForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="vendorId" name="cashPurchaseForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                  <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.vendorname" /> :</div></td>
                  <td class="row-even"> 
                  
                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          	<tr><td><html:select property="vendorId" styleId="vendorId" styleClass="combo" onchange="funcOtherShowHide('vendorId','vendorName')">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					<logic:notEmpty property="vendorList" name="cashPurchaseForm">
	                    <html:optionsCollection property="vendorList" name="cashPurchaseForm" label="vendorName" value="id"/>
					</logic:notEmpty>
					<html:option value="Other">Other</html:option>
                  </html:select></td></tr>
							<tr><td><html:text property="vendorName" size="10"
														maxlength="30" styleId="vendorName"
														style="<%=dynaStyle %>"></html:text></td></tr>
							</table>
                  
                  </td>
                </tr>
                <tr >
 				<td width="18%" height="25" class="row-odd" ><div align="right"><bean:message key="admissionFormForm.comments.required" />:</div></td>
                 <td width="19%" class="row-even"><label>
                  	<html:textarea property="comments" cols="25" rows="3" styleId="comments" onkeypress="return imposeMaxLength(event,this)"></html:textarea>
                  </label>
                  </td>
                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.InvLocation" />:</div></td>
                  <td class="row-even">
                  <html:select property="invLocationId" styleClass="comboLarge" styleId="invLocation">
						<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>									
						<html:optionsCollection property="inventoryLocationList" label="name" value="id"/>
				  </html:select>
                  </td>
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
        <td colspan="2" class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" colspan="2"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr>
                  <td width="33%" height="25" class="row-odd" align="center"><span class="Mandatory">*</span>&nbsp;<bean:message key="inventory.purchaseOrder.itemname.label"/></td>
                  <td width="33%" class="row-odd" align="center"><span class="Mandatory">*</span>&nbsp;<bean:message key="inventory.purchaseOrder.quantity.label"/></td>
                  <td width="33%" class="row-odd" align="center"><span class="Mandatory">*</span>&nbsp;<bean:message key="inventory.purchaseOrder.pcost.label"/></td>
                </tr>
                <tr class="menuMainLink" >
                  <td height="25" class="row-even" align="center">&nbsp;
	                <html:text property="itemName" styleId="itemName" size="25" maxlength="50"/>
                  </td>
                  <td class="row-even" align="center">&nbsp;<html:text property="quantity" name="cashPurchaseForm" styleId="quantity" size="20" maxlength="8"/></td>
                  <td class="row-even" align="center">&nbsp;<html:text property="purchasePrice" name="cashPurchaseForm" styleId="purchasePrice" size="20" maxlength="8"/></td>
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
        <td class="heading" colspan="2"><table width="100%"  border="0" cellspacing="0" cellpadding="0">  
          <tr>
            <td width="45%" align="right">&nbsp;</td>
            <td width="10%" height="35" align="center">
           <html:button property="" value="Add" styleClass="formbutton" onclick="prepareItemList()"></html:button>
            </td>            
            <td width="45%" height="35" align="left">&nbsp;</td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading" colspan="2"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr>
                  <td width="6%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                  <td width="25%" class="row-odd" align="center"><bean:message key="knowledgepro.interview.Date"/> </td>
                  <td width="25%" class="row-odd" align="center"><bean:message key="knowledgepro.inventory.vendorname"/> </td>
                  <td width="25%" class="row-odd" align="center"><bean:message key="inventory.purchaseOrder.itemname.label"/></td>
                  <td width="15%" class="row-odd" align="center"><bean:message key="inventory.purchaseOrder.quantity.label"/></td>
                  <td width="15%" class="row-odd" align="center"><bean:message key="inventory.purchaseOrder.pcost.label"/></td>
                  <td width="6%" class="row-odd" align="center"><bean:message key="knowledgepro.delete"/></td>
                </tr>
                <c:set var="temp" value="0" />
                <logic:notEmpty property="transferItemList" name="cashPurchaseForm">
					<logic:iterate id="transferItem" property="transferItemList" name="cashPurchaseForm" indexId="count">
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>
						<td height="25" align="center"><div align="center"><c:out value="${count + 1}" /></div></td>
						<td align="center"><bean:write name="transferItem" property="purchaseDate" /></td>
						<td align="center"><bean:write name="transferItem" property="vendorName" /></td>
						<td align="center"><bean:write name="transferItem" property="name" /></td>
						<td align="center"><bean:write name="transferItem" property="quantityIssued" /></td>
						<td align="center"><bean:write name="transferItem" property="purchaseCost" /></td>
						<td>
						<div align="center"><img src="images/delete_icon.gif"
							alt="CMS" width="16" height="16" style="cursor: pointer"
							onclick="deleteItem('<bean:write name="transferItem" property="name"/>')"></div>
						</td>
						</tr>
					</logic:iterate>
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
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right"><html:button property="" styleClass="formbutton" onclick="submitCashPurchase()"><bean:message key="knowledgepro.submit" /></html:button></td>
            <td width="49%" height="35" align="left"><html:button property="" styleClass="formbutton" onclick="resetMessages()"><bean:message key="knowledgepro.admin.reset" /></html:button></td>
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