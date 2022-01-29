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
<script type="text/javascript">
function submitMe(method){
	document.openingBalanceForm.method.value=method;
	document.openingBalanceForm.submit();
}

</script>

<title>Opening Balance</title>

<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">


<link href="css/styles.css" rel="stylesheet" type="text/css">



</head>

<html:form action="/openingBalance">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="openingBalanceForm"/>

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"> <bean:message key="knowledgepro.inventory"/> &gt;&gt;Opening Balance &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Opening Balance</td>
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
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td height="25" class="row-odd" ><div align="center"> <span class="Mandatory">*</span><bean:message key="inventory.stockReceipt.invLoc.label"/></div></td>
                  <td class="row-even" >
                 	<html:select styleClass="combo" property="locationId">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                    <logic:notEmpty property="locations" name="openingBalanceForm">
	                    <html:optionsCollection property="locations" name="openingBalanceForm" label="name" value="id"/>
					</logic:notEmpty>	
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
        <td class="heading">&nbsp;</td>
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
                  <td width="18%" height="25" class="row-odd" ><div align="right"> <span class="Mandatory">*</span> <bean:message key="inventory.purchaseOrder.item.label"/> </div></td>
                  <td width="19%" class="row-even" ><html:text property="searchItem" styleId="searchSubLeft" size="15" maxlength="15" onkeyup="searchSubject(this.value)"/></td>
                  <td width="13%" class="row-odd" ></td>
                  <td width="18%" class="row-even" ></td>
                  </tr>
              </table>
                
              </td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="18%" height="25" class="row-odd" ><div align="right"></div></td>
                  <td width="19%" class="row-even" ><html:select property="selectedItemId" styleClass="body" size="4" style="width:300px;" styleId="selectedItemId">
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                   <logic:notEmpty property="itemList" name="openingBalanceForm">
	                    <html:optionsCollection property="itemList" name="openingBalanceForm" label="nameWithCode" value="id"/>
					</logic:notEmpty>
                  </html:select></td>
                  <td width="13%" class="row-odd" ><div align="right">
                    <div align="right"><span class="Mandatory">*</span> <bean:message key="inventory.purchaseOrder.qty.label"/> </div>
                  </div></td>
                  <td width="18%" class="row-even" ><html:text property="selectedItemQty" styleId="selectedItemQty" size="5" maxlength="5"></html:text> </td>
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
            <td width="45%" align="right"><html:button property="" styleClass="formbutton" value="Submit" onclick="submitMe('submitOpeningBalance')"/></td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left"><html:button property="" styleClass="formbutton" value="Reset" onclick="submitMe('initOpeningBalance')" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
     
    
      <tr>
        <td height="2" valign="top" background="images/Tright_03_03.gif"></td>
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
