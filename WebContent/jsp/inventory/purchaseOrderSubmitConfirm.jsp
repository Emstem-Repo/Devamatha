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

<style type="text/css"> 
.hide {display: none} 
</style> 



<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">


<link href="css/styles.css" rel="stylesheet" type="text/css">


</head>

<html:form action="/purchaseOrderSubmit">
<html:hidden property="method" value="initPurchaseOrder"/>
<html:hidden property="pageType" value="3"/>
<html:hidden property="formName" value="purchaseOrderForm"/>
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
        <td class="heading"><div align="right"> </div></td>
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
             <logic:equal value="Add" property="mode" name="purchaseOrderForm">
            <td width="100%"  valign="middle" class="heading"><bean:message key="inventory.purchaseOrder.submit.success"/>   The generated Quotation No:
            <bean:write name="purchaseOrderForm" property="purchaseOrderNo"/>        
              </td></logic:equal>
               <logic:equal value="Edit" property="mode" name="purchaseOrderForm">
            <td width="100%" valign="middle" class="heading"><bean:message key="knowledgepro.inventory.po.updated.success"/>         
              </td></logic:equal>
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
            <td width="50%" height="35" align="right"></td>
            <td width="3" height="35" align="center"><html:submit styleClass="formbutton" value="Back" /></td>
            <td width="50%" height="35" align="left"></td>
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
<script>
	var print = "<c:out value='${purchaseOrderForm.printReceipt}'/>";
	if(print.length!=0 && print=="true") {
		var url ="purchaseOrderSubmit.do?method=printpurchaseOrderAfterSave";
		window.open(url,"purchaseOrder","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
</script>
</html:form>


