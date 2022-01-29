<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>

<%@page import="com.kp.cms.constants.CMSConstants"%><head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<script language="JavaScript" src="js/inventory/purchaseOrder.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="inventory.stockReport.main.label"/>  </title>
<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">


<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function submitStockReport(method){
	document.stockReportForm.method.value=method;
	document.stockReportForm.submit();
}

function closeWin(){

	window.close();
}

</script>
</head>
<body>
<html:form action="/stockReport">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="3"/>
<html:hidden property="formName" value="stockReportForm"/>

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.inventory"/> </span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="inventory.stockReport.main.label"/>  </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
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
                  <td align="center" class="row-even" >Item Name</td>
                  
                  <td align="center"  class="row-even">Opening Balance</td>
				  <td align="center" class="row-even" >Operation</td>
                  <td align="center" class="row-even" >Closing Balance</td>
                  
                  </tr>
					<logic:notEmpty property="txnList" name="stockReportForm">
					<nested:iterate property="txnList" name="stockReportForm" id="txn">
					<tr >
                	  <td align="center" class="row-white" ><nested:write name="txn" property="invItem.name"/> </td>
                 	  
                 	  <td align="center"  class="row-white"><nested:write name="txn" property="openingBalance"/></td>
					  <td align="center" class="row-white" >
						<logic:equal value="<%=CMSConstants.RECEIPT_TX_TYPE %>" name="txn" property="txType">RECEIVED</logic:equal>
						<logic:equal value="<%=CMSConstants.ISSUE_TX_TYPE %>"  name="txn" property="txType">ISSUED</logic:equal>
						<logic:equal value="<%=CMSConstants.RETURN_TX_TYPE %>" name="txn" property="txType">PURCHASE RETURNED</logic:equal>
						<logic:equal value="<%=CMSConstants.STOCKTRANSFER_ISSUE_TX_TYPE %>" name="txn" property="txType">TRANSFER ISSUED</logic:equal>
						<logic:equal value="<%=CMSConstants.STOCKTRANSFER_RECEIVE_TX_TYPE %>" name="txn" property="txType">TRANSFER RECEIVED</logic:equal>
						<logic:equal value="<%=CMSConstants.SALVAGE_TX_TYPE %>" name="txn" property="txType">SALVAGED</logic:equal>
					  </td>
                	  <td align="center" class="row-white" ><nested:write name="txn" property="closingBalance"/></td>
                  
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
            <td width="50%" height="35" align="right">&nbsp;</td>
            <td width="3" height="35" align="center"><html:button property="" styleClass="formbutton" value="Close" onclick="closeWin()"/></td>
            <td width="50%" height="35" align="left">&nbsp;</td>
          </tr>
        </table>      
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"> <td valign="top" background="images/Tright_3_3.gif" ></td>
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