<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/calendar.js"></script>

<head>
<title><bean:message key="inventory.purchaseOrder.main.label"/></title>

<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
<script language="JavaScript" src="js/inventory/purchaseOrder.js"></script>
 <script src="jquery/development-bundle/jquery-1.7.1.js"></script>
 <script src="http://code.jquery.com/jquery-latest.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">


</head>
<script type="text/javascript">

var jq=$.noConflict();

jq(document).ready(function(){
	if(jq('#AddRadio').is(':checked') && jq('#dataExistsForEdit').val()!=true && jq('#dataExistsForEdit').val()!='true'){
		 jq(".AddPONo").show();
		 jq("#NumEntry").show();
		 jq(".QuotationRefNo").show();
		 jq(".EditPurchaseOrderNo").hide();
		 jq(".EditPurchaseOrderNoButton").show();
		 jq("#quotationNo :input").removeAttr('disabled');
		
	} else if(jq('#AddRadio').is(':checked') && (jq('#dataExistsForEdit').val()==true || jq('#dataExistsForEdit').val()=='true')){
		 jq(".AddPONo").show();
		 jq("#NumEntry").show();
		 jq(".QuotationRefNo").show();
		 jq(".EditPurchaseOrderNo").hide();
		 jq(".EditPurchaseOrderNoButton").hide();
		 jq("#quotationNo :input").attr('disabled', 'disabled');
	}
	else if(jq('#EditRadio').is(':checked') && jq('#dataExistsForEdit').val()!=true && jq('#dataExistsForEdit').val()!='true'){
		 jq("#NumEntry").show();
		 jq(".EditPurchaseOrderNo").show();
		 jq(".EditPurchaseOrderNoButton").show();
		 jq(".EditPurchaseOrderNo :input").removeAttr('disabled');
		 jq(".AddPONo").hide();
		 jq(".QuotationRefNo").hide();
	}
	else if(jq('#EditRadio').is(':checked') && (jq('#dataExistsForEdit').val()==true || jq('#dataExistsForEdit').val()=='true')){
		 jq("#NumEntry").show();
		 jq(".EditPurchaseOrderNo").show();
		 jq(".EditPurchaseOrderNoButton").hide();
		 jq(".EditPurchaseOrderNo :input").attr( 'disabled', 'disabled'); 
		 jq("#quotationNo :input").attr('disabled', 'disabled');
		 jq(".AddPONo").show();
		 jq(".QuotationRefNo").show();
	}
	else{
	jq(".AddPONo").hide();
	jq("#NumEntry").hide();
	jq(".EditPurchaseOrderNoButton").hide();
	jq(".EditPurchaseOrderNo :input").removeAttr('disabled');
	jq("#quotationNo :input").removeAttr('disabled');
	}
	
	jq("#AddRadio").click(function(){
		document.getElementById("purchaseDate").value='';
		document.getElementById("vendorId").value='';
		document.getElementById("remarks").value='';
		document.getElementById("siteDelivery").value='';
		document.getElementById("campusId").value='';
		document.getElementById("companyId").value='';
		document.getElementById("deliverySchedule").value='';
		document.getElementById("quoteNo").value='';
		
		 jq(".AddPONo").show(2000);
		 jq("#NumEntry").show(2000);
		 jq(".QuotationRefNo").show(2000);
		 jq(".EditPurchaseOrderNo").hide();
		 jq(".EditPurchaseOrderNoButton").show(2000);
		 jq("#quotationNo :input").removeAttr('disabled');
	  });

	jq("#EditRadio").click(function(){
		     jq(".AddPONo").hide();
		     jq(".QuotationRefNo").hide();
		     jq("#NumEntry").show(2000);
			 jq(".EditPurchaseOrderNo").show(2000);
			 jq(".EditPurchaseOrderNoButton").show(2000);
			 jq(".EditPurchaseOrderNo :input").removeAttr('disabled');
		  });

	});




function getInvLocationList(campusId){
	getInvLocationListByCampusId("invLocationMap",campusId,"locationId",updateInvLocation);
}
function updateInvLocation(req){
	updateOptionsFromMapForInvLocation(req, "locationId", "- Select -");
}
function updateOptionsFromMapForInvLocation(req, destinationOption, defaultSelectValue) {
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
<html:form action="/purchaseOrderSubmit">
<html:hidden property="method" value="submitInitPurchaseOrder"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value=" "/>
<html:hidden property="superAddNewType" value=""/>
<html:hidden property="superMainPage" value=""/>
<html:hidden property="destinationMethod" value=""/>
<html:hidden property="dataExistsForEdit" styleId="dataExistsForEdit"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/> &gt;&gt; <bean:message key="inventory.purchaseOrder.main.label"/> &gt;&gt;</span></span></td>

  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="inventory.purchaseOrder.main.label"/></td>
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
            <td width="100%" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                 <td class="row-odd"><div align="right"> <span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.mode"/></div> </td>
                  <td class="row-even"  colspan="3" height="25" ><html:radio styleId="AddRadio" property="mode" value="Add">Add</html:radio> 
                  &nbsp;&nbsp;&nbsp;&nbsp;<b>OR</b>&nbsp;&nbsp;&nbsp;&nbsp;
                  <html:radio styleId="EditRadio" property="mode" value="Edit">Edit</html:radio> </td>
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
      
      <tr><td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" height="25"></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td></tr>
        
        <tr id="NumEntry" >
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td width="100%" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr class="EditPurchaseOrderNo" >
                  <td width="50%" height="25" class="row-odd" ><div align="right"> <span class="Mandatory">*</span> <bean:message key="inventory.purchaseOrder.orderNo.label"/> </div></td>
                  <td width="50%" class="row-even" colspan="3" >
                    <html:text property="purchaseOrderNo" size="10" maxlength="30" ></html:text>
				  </td>
                  </tr>
                 <tr class="QuotationRefNo" >
                  <td width="50%" height="25" class="row-odd" ><div align="right"> <bean:message key="inventory.quotation.no.col"/> </div></td>
                  <td width="50%" class="row-even" colspan="3" id="quotationNo" >
                    <html:text property="quotationNo" styleId="quoteNo" size="10" maxlength="30"  ></html:text>
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
      <tr class="EditPurchaseOrderNoButton">
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="50%" height="35" align="center"><html:button property="" styleClass="formbutton" value="Continue" onclick="submitPurchaseForm('getInitPODetailsEdit')" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
       <tr><td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" height="25"></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td></tr>
      
      
      <tr class="AddPONo">
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td width="100%" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="inventory.purchaseOrder.orderDt.label"/> </div></td>
                  <td class="row-even" ><table width="82" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="60"><html:text property="purchaseDate"  styleId="purchaseDate" size="10" maxlength="10" ></html:text></td>
                      <td width="40"><script language="JavaScript">
	new tcal ({
		// form name
		'formname': 'purchaseOrderForm',
		// input name
		'controlname': 'purchaseDate'
	});</script></td>
                    </tr>
                  </table></td>
                  <td class="row-odd"></td>
                  <td class="row-even"></td>
                  </tr>
                <tr >
                  <td width="18%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="inventory.purchaseOrder.vendorname.label"/> </div></td>
                  <td width="19%" class="row-even" ><label>
                  <html:select property="vendorId" styleId="vendorId" styleClass="combo" onchange="addNewMaster(this.form,this.value,'purchaseOrderSubmit','Vendor','initVendor')">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                    <html:option value="AddNew"><bean:message key="knowledgepro.admin.add.new"/></html:option>
					<logic:notEmpty property="vendorList" name="purchaseOrderForm">
	                    <html:optionsCollection property="vendorList" name="purchaseOrderForm" label="vendorName" value="id"/>
					</logic:notEmpty>
                  </html:select>
                  </label></td>
                  <td width="13%" class="row-odd" ><div align="right"><bean:message key="inventory.purchaseOrder.remark.label"/> </div></td>
                  <td width="18%" class="row-even" ><html:textarea property="remarks" styleId="remarks" cols="20" rows="2"></html:textarea></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="inventory.purchaseOrder.t&c.label"/> </div></td>
                  <td class="row-even" ><html:textarea property="termConditions" styleId="termConditions" cols="20" rows="2" style="width: 558px; height: 209px;">
                  </html:textarea></td>
                  <td class="row-odd" ><div align="right"><bean:message key="inventory.purchaseOrder.sitedelivery.label"/> </div></td>
                  <td class="row-even" ><html:textarea property="siteDelivery" styleId="siteDelivery" cols="20" rows="2"></html:textarea></td>
                </tr>
                 <tr>
                <td width="18%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.inventory.InvCampus"/>:</div></td>
                  <td width="19%" class="row-even" ><label>
                  <html:select property="campusId" styleId="campusId" styleClass="combo" onchange="addNewMaster(this.form,this.value,'purchaseOrderSubmit','SingleFieldMasterEntry','initSingleFieldMaster&operation=InvCampus&displayName=Inventory Campus&module=Inventory');">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                    <html:option value="AddNew"><bean:message key="knowledgepro.admin.add.new"/></html:option>	
					<logic:notEmpty property="campusList" name="purchaseOrderForm">
	                    <html:optionsCollection property="campusList" name="purchaseOrderForm" label="campusName" value="id"/>
					</logic:notEmpty>
                  </html:select>
                  </label></td>
                 <td width="18%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admin.InvCompany"/>:</div></td>
                  <td width="19%" class="row-even" ><label>
                  <html:select property="companyId" styleId="companyId" styleClass="combo" >
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					<logic:notEmpty property="invCompanyList" name="purchaseOrderForm">
	                   <html:optionsCollection name="purchaseOrderForm" property="invCompanyList" label="companyName" value="id"/>
					</logic:notEmpty>
                  </html:select>
                  </label></td>
                </tr>
                <tr>
                <td width="18%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.inventory.vendor.delivery.schedule"/>:</div></td>
                <td width="19%" class="row-even"><html:text property="deliverySchedule" styleId="deliverySchedule" size="30" maxlength="30"></html:text></td>
                <td width="18%" height="25" class="row-odd" ></td>
                <td width="19%" class="row-even"></td>
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
      <tr class="AddPONo">
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="50%" height="35" align="right"><html:submit styleClass="formbutton" value="Continue" onclick="submitPurchaseForm('submitInitPurchaseOrder')" /></td>
            <td width="3" height="35" align="center">&nbsp;</td>
            <td width="50%" height="35" align="left"><html:button property="" styleClass="formbutton" value="Reset" onclick="submitPurchaseForm('initPurchaseOrder')" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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


