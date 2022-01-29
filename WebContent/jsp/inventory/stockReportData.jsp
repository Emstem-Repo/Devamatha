<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<head>
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

function winOpen(reqId,date,location) {
	var url = "stockReport.do?method=getDetails&txnId="+reqId+"&selectedDate="+date+"&selectedLocation="+location;
	myRef = window.open(url, "StockTransaction",
			"left=50,top=20,width=600,height=600,toolbar=1,resizable=0,scrollbars=1");
}


</script>
</head>
<body>
<html:form action="/stockReport">
<html:hidden property="method" value=""/>
<html:hidden property="txnId" value=""/>
<html:hidden property="selectedDate" value=""/>
<html:hidden property="selectedLocation" value=""/>
<html:hidden property="pageType" value="2"/>
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
                  <td align="right" class="heading" >Date&nbsp; : </td>
                  <td class="row-white" ><strong><bean:write property="date" name="stockReportForm"/></strong></td>
                  <td align="right" class="heading">Inventory Location :</td>
                  <td class="row-white" ><strong> <bean:write property="locationName" name="stockReportForm"/> </strong></td>
                  
                  </tr>
				<tr>
			<td colspan="4" align="left">
		<display:table export="true" id="reqList" name="sessionScope.itemList" requestURI="" defaultorder="descending" pagesize="10" style="width:100%" >
		
		<display:setProperty name="export.excel.filename" value="StockReport.xls"/>
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv.filename" value="StockReport.csv"/>
			
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="invItem.name" sortable="false" title="Item Name" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="openingBalance" sortable="true" title="Opening Balance" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="closingBalance" sortable="true" title="Closing Balance" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " media="excel csv" property="invLocation.name" sortable="false" title="Inventory location" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " media="csv excel" property="txDisplayDate" sortable="false" title="Date" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " title="Details" class="row-even" sortable="true" media="html" headerClass="row-odd">
			<A HREF="javascript:winOpen('<bean:write name="reqList" property="id" />','<bean:write name="reqList" property="txDisplayDate" />','<bean:write name="reqList" property="invLocation.id" />');"> Details </A>
			</display:column>
			
			
		
		</display:table></td>
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
        </table>      
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="50%" height="35" align="right">&nbsp;</td>
            <td width="3" height="35" align="center"><html:button property="" styleClass="formbutton" value="Cancel" onclick="submitStockReport('initStockReport')"/></td>
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