<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function searchSubject(searchValue){
	var sda = document.getElementById('itemId');
	var len = sda.length;
	var searchValueLen = searchValue.length;
	for(var m =0; m<len; m++){
		sda.options[m].selected = false;		
	}
	for(var j=0; j<len; j++)
	{
		for(var i=0; i<len; i++){
			if(((sda.options[i].text).substring(0, searchValueLen).toUpperCase())  == (searchValue.toUpperCase())){
				sda.options[i].selected = true;
				break;
			}
		}
	}
}
function prepareItemList(){
	document.getElementById("method").value = "prepareItemListForTransfer";	
	document.stockTransferForm.submit();
}
function deleteItem(itemId){
	deleteConfirm = confirm("Are you sure want to delete this entry?")
	if (deleteConfirm) {
		document.getElementById("itemId").value = itemId;	
		document.getElementById("method").value = "deleteItem";	
		document.stockTransferForm.submit();
	}
}
function submitStockTransfer(){
	document.getElementById("method").value = "submitStockTransfer";	
	document.stockTransferForm.submit();
}
function goToHomePage(){
	document.location.href = "StockTransfer.do?method=initStockTransfer";
}
</script>
<html:form action="/StockTransfer" method="post">
<html:hidden property="method" styleId="method" />
<html:hidden property="formName" value="stockTransferForm" />
<table width="99%" border="0"> 
<html:hidden property="pageType" value="2" />
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"> <bean:message key="knowledgepro.inventory"/> &gt;&gt; <bean:message key="knowledgepro.inventory.stocktransfer"/> &gt;&gt;</span></span></td>
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
        <td class="heading">       
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
                  <td height="25" width="15%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.transferno.col"/></div></td>
                  <td width="25%" class="row-even" ><bean:write name="stockTransferForm" property="transferNo"/></td>
                  <td width="15%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.transferdate.col"/></div></td>
                  <td width="15%" class="row-even" ><bean:write name="stockTransferForm" property="transferDate"/></td>
                  <td width="15%" class="row-odd" >&nbsp;</td>
                  <td width="15%" class="row-even" >&nbsp;</td>
                </tr>
                <tr>
                	<td width="15%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.from.inventory.location.col"/> </div></td>
                  	<td width="25%" class="row-even" ><bean:write name="stockTransferForm" property="fromInventoryName"/></td>
                  	<td width="15%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.to.inventory.location.col"/></div></td>
                  	<td width="15%" class="row-even" ><bean:write name="stockTransferForm" property="toInventoryName"/></td>
                    <td width="15%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.reservation.remarks"/> </div></td>
                  	<td width="15%" class="row-even" ><bean:write name="stockTransferForm" property="remarks"/></td>
                </tr>
                <tr>
                  <td  width="15%" class="row-odd" ><div align="right"> <span class="Mandatory">*</span> <bean:message key="knowledgepro.inventory.item.col"/> </div></td>
                  <td width="25%" class="row-even" >
                  <html:text property="searchItem" styleId="searchSubLeft" size="15" maxlength="15" onkeyup="searchSubject(this.value)"/>
                   </td>
                  <td width="15%" class="row-odd" ></td>
                  <td width="15%" class="row-even" ></td>
                  <td width="15%" class="row-odd" >&nbsp;</td>
                  <td width="15%" class="row-even" >&nbsp;</td>
                </tr>               
                <tr align="center">
                <td height="25" class="row-odd" >&nbsp; </td>
                <td style="width:300px">
		           <nested:select property="itemId" styleId="itemId" styleClass="body" size="4" style="width:300px;">
					<logic:notEmpty name="stockTransferForm" property="itemList">
					<nested:optionsCollection name="stockTransferForm" property="itemList" label="itemLabel" value="id" styleClass="comboBig"/>
					</logic:notEmpty>
				</nested:select>
				</td>
				<td class="row-odd" valign="top" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.quantity.issued.col"/> </div></td>
                  <td class="row-even" valign="top" >
                  <html:text property="quantityIssued" size="15" maxlength="15"></html:text>
                  </td>
                  <td class="row-odd" >&nbsp;</td>
                  <td class="row-even" >&nbsp;</td>
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
                  <td width="48" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
                  <td class="row-odd" align="center"><bean:message key="knowledgepro.inventory.item"/> </td>
                  <td width="163" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.inventory.quantity.issued.uom"/> </td>
                  <td width="154" class="row-odd" align="center"><bean:message key="knowledgepro.inventory.quantity.issued"/> </td>
                  <td width="53" class="row-odd" align="center"><bean:message key="knowledgepro.delete"/></td>
                </tr>
                <c:set var="temp" value="0" />
                <logic:notEmpty property="transferItemList" name="stockTransferForm">
                <nested:iterate id="stock" property="transferItemList" name="stockTransferForm" indexId="count">
                <c:choose>
				<c:when test="${temp == 0}">
                <tr >
                  <td height="25" class="row-even" align="center"><div align="center"><c:out value="${count + 1}" /></div></td>
                  <td width="135" class="row-even"align="center" ><nested:write name="stock" property="nameWithCode"/> </td>
                  <td height="25" class="row-even" align="center"><nested:write name="stock" property="issueUomName"/></td>
                  <td class="row-even" align="center"><nested:write name="stock" property="quantityIssued"/></td>
                  <td class="row-even" ><div align="center"><img src="images/delete_icon.gif" alt="CMS" width="16" height="16" style="cursor: pointer"
                  onclick="deleteItem('<nested:write name="stock" property="id"/>')"></div></td>
                </tr>
                <c:set var="temp" value="1" />
				</c:when>
				<c:otherwise>	
                <tr >
                  <td height="25" class="row-white"><div align="center"><c:out value="${count + 1}" /></div></td>
                  <td class="row-white" align="center" ><nested:write name="stock" property="nameWithCode"/></td>
                  <td height="25" class="row-white" align="center"><nested:write name="stock" property="issueUomName"/></td>
                  <td class="row-white" align="center"><nested:write name="stock" property="quantityIssued"/></td>
                  <td class="row-white" ><div align="center"><img src="images/delete_icon.gif" alt="CMS" width="16" height="16"
                  onclick="deleteItem('<nested:write name="stock" property="id"/>')"></div></td>
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
        </table>      
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
     <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">      
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" align="right"><html:button property="" value="Submit" styleClass="formbutton" onclick="submitStockTransfer()"/></td>
            <td width="2%" height="24" align="center">&nbsp;</td>
            <td width="49%" height="24" align="left"><html:button property="" value="Cancel" styleClass="formbutton" onclick="goToHomePage()"/></td>
          </tr>
        </table>      
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
     <tr>	
		<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">      
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