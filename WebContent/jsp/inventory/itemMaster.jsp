<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function updateItem() {
	document.getElementById("method").value = "updateItem";
	document.itemForm.submit();
}
function addItem() {
	document.getElementById("method").value = "addItem";
	document.itemForm.submit();
}
function editItem(id) {
	document.location.href = "ItemMaster.do?method=editItem&id=" + id;
}
function deleteItem(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm) {
		document.location.href = "ItemMaster.do?method=deleteItem&id=" + id;
	}
}
function reActivate() {
	var id = document.getElementById("id").value;
	document.location.href = "ItemMaster.do?method=activateItem&id="+ id;
}
function imposeMaxLength(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 200;
	return (Object.value.length < MaxLen);
}
function resetMessages() {
	document.getElementById("itemCode").value = "";
	document.getElementById("itemName").value = "";
	document.getElementById("itemDescription").value = "";
	document.getElementById("purchaseUom").selectedIndex = 0;
	document.getElementById("issueUom").selectedIndex = 0;
	document.getElementById("category").selectedIndex = 0;
	document.getElementById("purchaseCost").value = "";
	document.getElementById("conversion").value = "";
	document.getElementById("itemSubCategoryId").value="";
	document.getElementById("itemTypeId").value="";
	document.getElementById("minStockQuantity").value="";
	document.getElementById("remarks").value="";
	resetErrMsgs();
}
function getSubCategoryList(categoryId){
	getSubCategoryListByCategoryId("itemSubCategoryMap",categoryId,"itemSubCategoryId",updateSubCategory);
}
function updateSubCategory(req){
	updateOptionsFromMapForSubCategory(req, "itemSubCategoryId", "- Select -");
}
function changeMandatoryForAsset(itemType){
	if(itemType=="1"){
		document.getElementById("itemCategoryDiv").innerHTML="<span class='Mandatory'>*</span>Item Category:";
		document.getElementById("itemSubCategoryDiv").innerHTML="<span class='Mandatory'>*</span>Item Sub Category:";
	}
	else {
		document.getElementById("itemCategoryDiv").innerHTML="Item Category:";
		document.getElementById("itemSubCategoryDiv").innerHTML="Item Sub Category:";
	}
}
function updateOptionsFromMapForSubCategory(req, destinationOption, defaultSelectValue) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById(destinationOption);
	for (x1 = destination.options.length - 1; x1 > 0; x1--) {
		destination.options[x1] = null;
	}
	var childNodes = responseObj.childNodes;
	destination.options[0] = new Option(defaultSelectValue, "");
	destination.options[1] = new Option("- Add New -", "AddNew");
	var items = responseObj.getElementsByTagName("option");

	var label, value;
	for ( var i = 0; i < items.length; i++) {
		label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		destination.options[i + 2] = new Option(label, value);
	}
}
</script>	
<html:form action="/ItemMaster">
<c:choose>
	<c:when test="${conditionsOperation == 'edit'}">
		<html:hidden property="method" styleId="method" value = "updateItem"/>
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addItem" />
	</c:otherwise>
</c:choose>
<html:hidden property="formName" value="itemForm" styleId="formName" />
<html:hidden property="pageType" value="1" />
<html:hidden property="id" styleId="id" />
<html:hidden property="addNewType" />
<html:hidden property="mainPage" />
<html:hidden property="destinationMethod" />
<table width="99%" border="0">
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory" /> &gt;&gt; <bean:message key="knowledgepro.inventory.itemmaster" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.inventory.itemmaster" /></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news"><div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
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
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr>
                  <td width="20%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.inventory.itemcode" />:</div></td>
                  <td width="30%" class="row-even"><html:text property="itemCode" styleId="itemCode" styleClass="TextBox" size="16" maxlength="30"></html:text></td>
                  <td width="20%" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.inventory.itemname" />:</div></td>
                  <td width="30%" class="row-even"><html:text property="itemName" styleId="itemName" styleClass="TextBox" size="30" maxlength="100"></html:text></td>
                  </tr>
                <tr>
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.itemdescription" />:</div></td>
                  <td class="row-even">
                  	<html:text property="itemDescription" styleId="itemDescription" styleClass="TextBox" size="30" maxlength="100"></html:text>
                  </td>
                  <td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.purchaseUOM" />:</div></td>
                  <td class="row-even">
					<html:select property="purchaseUomId" styleClass="combo" styleId="purchaseUom">
						<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>									
						<html:optionsCollection property="UOMList" label="name" value="id"/>
					</html:select>&nbsp;&nbsp;
					<span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.conversion"/>(In Numbers):&nbsp;<html:text property="conversion" styleId="conversion" styleClass="TextBox" size="8" maxlength="8"></html:text>
                  </td>		
                </tr>
                <tr>
                  <td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.issueUOM" />:</div></td>
                  <td class="row-even">
					<html:select property="issueUomId" styleClass="combo" styleId="issueUom">
						<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>									
						<html:optionsCollection property="UOMList" label="name" value="id"/>
					</html:select>
                  </td>
                   <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.itemType" />:</div></td>
                  <td class="row-even">
                  <html:select property="itemTypeId" styleClass="combo" styleId="itemTypeId" onchange="changeMandatoryForAsset(this.value)">
						<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>									
						<html:optionsCollection property="itemTypeList" label="name" value="id"/>
				  </html:select>
                  </td>  
                </tr>
                <tr>
                  <td height="25" class="row-odd" ><div align="right" id="itemCategoryDiv" >
                  <logic:notEmpty name="itemForm" property="itemTypeId">
                  <logic:equal name="itemForm" value="1" property="itemTypeId"><span class="Mandatory">*</span></logic:equal>
                  </logic:notEmpty>
                  <bean:message key="knowledgepro.inventory.itemcategory" />:</div></td>
                  <td class="row-even">
                  <html:select property="itemCategoryId" styleClass="combo" styleId="category" onchange="addNewMaster(this.form,this.value,'ItemMaster','SingleFieldMasterEntry','initSingleFieldMaster&operation=InvItemCategory&displayName=ItemCategory&module=Inventory');getSubCategoryList(this.value)">
						<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
						<html:option value="AddNew"><bean:message key="knowledgepro.admin.add.new"/></html:option>									
						<html:optionsCollection property="itemCategoryList" label="name" value="id"/>
				  </html:select>
                  </td>
        	      <td class="row-odd" ><div id="itemSubCategoryDiv" align="right">
        	      <logic:notEmpty name="itemForm" property="itemTypeId">
                  <logic:equal name="itemForm" value="1" property="itemTypeId"><span class="Mandatory">*</span></logic:equal>
                  </logic:notEmpty>
        	      <bean:message key="knowledgepro.inventory.itemSubCategory" />:</div></td>
            	  <td class="row-even">
            	   <html:select property="itemSubCategoryId" styleClass="combo" styleId="itemSubCategoryId" onchange="addNewMaster(this.form,this.value,'ItemMaster','ItemSubCategory','initSubCategory')">
						<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
						<html:option value="AddNew"><bean:message key="knowledgepro.admin.add.new"/></html:option>										
						<html:optionsCollection name="itemForm" property="itemSubCategoryMap" label="value" value="key"/>
				  </html:select>
            	  </td>
                </tr>
                <tr>
                <td class="row-odd"><div align="right"><bean:message key="knowledgepro.inventory.warranty" />:</div></td>
                  <td class="row-even">
                  	<html:radio property="warranty" value="true" ><bean:message key="knowledgepro.yes" /></html:radio>
				  	<html:radio property="warranty" value="false"><bean:message key="knowledgepro.no" /></html:radio>
                  </td>
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.minStockQuantity" />:</div></td>
                  <td class="row-even">
                  	<html:text property="minStockQuantity" styleId="minStockQuantity" styleClass="TextBox" size="16" maxlength="20" onkeypress="return isNumberKey(event)"></html:text>
                  </td>
                </tr>
                   <tr>
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.reservation.remarks" /></div></td>
                  <td class="row-even">
                  	<html:text property="remarks" styleId="remarks" styleClass="TextBox" size="30" maxlength="100"></html:text>
                  </td>
                  <td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="inventory.purchaseOrder.pcost.label" /></div></td>
                  <td class="row-even">
                  <html:text property="purchaseCost" size="8" maxlength="8" styleId="purchaseCost" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" ></html:text>
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
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="45%" height="35" align="right">
            <c:choose>
				<c:when test="${conditionsOperation == 'edit'}">
					<html:button property="" styleClass="formbutton"
						value="Update" onclick="updateItem()"></html:button>
				</c:when>
				<c:otherwise>
					<html:button property="" styleClass="formbutton"
						value="Submit" onclick="addItem()"></html:button>
				</c:otherwise>
			</c:choose>
            </td>
			<td width="2%" height="35" align="center">&nbsp;</td>
			<td width="5%" height="35" align="left">
			<c:choose>
				<c:when test="${conditionsOperation == 'edit'}">
					<html:cancel styleClass="formbutton"><bean:message key="knowledgepro.admin.reset" /></html:cancel>
				</c:when>
				<c:otherwise>
					<html:button property="" styleClass="formbutton" onclick="resetMessages()"><bean:message key="knowledgepro.admin.reset" /></html:button>
				</c:otherwise>
			</c:choose>
			</td>
			<td width="2%"></td>
				<logic:notEmpty name="itemForm" property="superMainPage">
							<td><html:button property="" styleClass="formbutton"> Go To Main Page"
 							onclick="goToMainPage('<bean:write name="itemForm" property="superMainPage" scope="session"/>')"</html:button>
							</td></logic:notEmpty>
							<logic:empty name="itemForm" property="superMainPage"><td></td></logic:empty>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5" background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr>
                  <td width="4%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
                  <td width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.inventory.itemcode" /></div></td>
                  <td width="15%" class="row-odd"><div align="center"><bean:message key="knowledgepro.inventory.itemname" /></div></td>
                  <td width="15%" class="row-odd"><div align="center"><bean:message key="knowledgepro.inventory.itemType" /></div></td>
                  <td width="25%" class="row-odd"><div align="center"><bean:message key="knowledgepro.inventory.itemcategory" /></div></td>
                  <td width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.inventory.itemSubCategory" /></div></td>
                  <td width="6%" class="row-odd"><div align="center"><bean:message key="knowledgepro.inventory.warranty" /></div></td>
                  <td width="6%" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit" /></div></td>
                  <td width="6%" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete" /></div></td>
                </tr>
                <logic:iterate name="itemForm"
					property="itemList" id="iMasterList"
					indexId="count">
					<c:choose>
						<c:when test="${count%2 == 0}">
							<tr class="row-even">
						</c:when>
						<c:otherwise>
							<tr class="row-white">
						</c:otherwise>
					</c:choose>
                  <td height="25"><div align="center"><c:out value="${count+1}" /></div></td>
                  <td><div align="center"><bean:write name="iMasterList" property="code" /></div></td>
                  <td><div align="center"><bean:write name="iMasterList" property="name" /></div></td>
                  <td><div align="center"><bean:write name="iMasterList" property="itemType" /></div></td>
                  <td><div align="center"><bean:write name="iMasterList" property="itemCategoryName" /></div></td>
                  <td><div align="center"><bean:write name="iMasterList" property="itemSubCategory" /></div></td>
                  <td><div align="center">
                  <logic:equal name="iMasterList" property="isWarranty" value="true">
                  	<bean:message key="knowledgepro.yes" />
                  </logic:equal>
                  <logic:equal name="iMasterList" property="isWarranty" value="false">
                  	<bean:message key="knowledgepro.no" />
                  </logic:equal>
                  </div></td>
                  <td><div align="center"><img src="images/edit_icon.gif" style="cursor:pointer" width="16" height="16" onclick="editItem('<bean:write name="iMasterList" property="id"/>')"></div></td>
                  <td><div align="center"><img src="images/delete_icon.gif" style="cursor:pointer" width="16" height="16" onclick="deleteItem('<bean:write name="iMasterList" property="id"/>')"></div></td>
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