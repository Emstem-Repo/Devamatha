<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>

<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<script language="JavaScript" src="js/inventory/purchaseOrder.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="inventory.stockReceipt.main.label"/> </title>
<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">


<link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<html:form action="/stockReceiptSubmit">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="3"/>
<html:hidden property="formName" value="stockReceiptForm"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/> &gt;&gt;Item Warranty Entry &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Item Warranty Entry</td>
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
        <td valign="top" >
			<table width="100%" cellspacing="1" cellpadding="2">
	                <tr >
	                  <td class="row-odd" ><div align="center"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.update.warranty.sl.no"/> </div></td>
	                  <td class="row-odd" ><div align="center"><span class="Mandatory">*</span><bean:message key="inventory.stockReceipt.stDt.label"/></div> </td>
	                  <td class="row-odd"><div align="center"><span class="Mandatory">*</span><bean:message key="inventory.stockReceipt.endDt.label"/></div></td>
	                  
	                </tr>
	            	<logic:notEmpty name="stockReceiptForm" property="itemAmcs" >
					<nested:iterate name="stockReceiptForm" property="itemAmcs" id="amc" indexId="count">
					<%
					String startDtId="StartDate"+count;
					String endDtId="endDate"+count;
					%>
					<tr >
	                  <td class="row-even" ><div align="center"><nested:text property="itemNo" size="20" maxlength="30"></nested:text> </div></td>
	                  <td class="row-even" ><div align="center"><nested:text property="warrantyStartDate" styleId="<%=startDtId %>" size="10" maxlength="10"></nested:text><script language="JavaScript">
	new tcal ({
		// form name
		'formname': 'stockReceiptForm',
		// input name
		'controlname': '<%=startDtId %>'
	});</script>
					</div>	</td>
	                  <td class="row-even"><div align="center"><nested:text property="warrantyEndDate" styleId="<%=endDtId %>" size="10" maxlength="10"></nested:text><script language="JavaScript">
	new tcal ({
		// form name
		'formname': 'stockReceiptForm',
		// input name
		'controlname': '<%=endDtId %>'
	});</script>
						</div></td>
	                  
	                </tr>
					</nested:iterate>
	            	</logic:notEmpty>         
	          
           
        </table> </td>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="50%" height="35" align="right"><html:button property="" styleClass="formbutton" value="Submit"  onclick="submitStockReceipt('addAMCEntry')"/></td>
            <td width="3" height="35" align="center"></td>
            <td width="50%" height="35" align="left"><html:button property="" styleClass="formbutton" value="Cancel"  onclick="submitStockReceipt('forwardStockReceiptMainPage')"/></td>
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


</html:form>
