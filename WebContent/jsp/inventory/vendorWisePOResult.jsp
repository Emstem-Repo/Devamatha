<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<script type="text/javascript"> 
function closeWin(){

	window.close();
}
function cancelAction() {
	document.location.href = "VendorWisePO.do?method=initVendorWisePO";
}
function winOpen(reqId) {
	var url = "VendorWisePO.do?method=getPurchaseOrderDetailsByID&purchaseOrderId=" + reqId;
	myRef = window
			.open(url, "VendorWisePOReport",
					"left=20,top=20,width=800,height=800,toolbar=1,resizable=0,scrollbars=1");
}
</script> 
<html:form action="/VendorWisePO" method="post">
<html:hidden property="method" styleId="method"/>
<html:hidden property="formName" value="vendorWisePOForm" />
<table width="99%" border="0">  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/>  &gt;&gt; <bean:message key="knowledgepro.venderwise.report"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/Tcenter.gif" class="heading_white" > <bean:message key="knowledgepro.venderwise.report"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading"><div align="right"> <span class='mandatoryfield'><bean:message
		key="knowledgepro.mandatoryfields" /></span></div></td>
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
            <td valign="top" align="center">
      <div style="overflow: auto; width: 914px; ">
        <display:table export="true" id="poList" name="sessionScope.poList" requestURI="" defaultorder="descending" pagesize="10" >
	
		<display:setProperty name="export.excel.filename" value="VendorwisePOResult.xls"/>
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv.filename" value="VendorwisePOResult.csv"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="vendorName" sortable="true" title="Vendor" class="row-even" headerClass="row-odd" group="1"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="orderNo" sortable="true" title="Order No." class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="orderDate" sortable="true" title="Order Date" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="remarks" sortable="true" title="Remarks" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="termsandconditions" sortable="true" title="Terms and Conditions" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="deliverySite" sortable="true" title="Delivery Site" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="totalCost" sortable="true" title="Total Cost" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="additionalCost" sortable="true" title="Additional Cost" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " title="Details" class="row-even" sortable="true" media="html" headerClass="row-odd">
			<A HREF="javascript:winOpen('<bean:write name="poList" property="id" />');"> <bean:message key="knowledgepro.admission.details"/> </A>
			</display:column>
		</display:table>
       </div>     
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
        <td colspan="2" class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right">&nbsp;</td>
            <td width="2%" height="35" align="center"><html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()"/></td>
            <td width="49%" height="35" align="left"></td>
          </tr>
        </table></td>
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