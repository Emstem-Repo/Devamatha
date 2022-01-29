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
<title><bean:message key="inventory.stockReport.main.label"/>  </title>
<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">


<link href="css/styles.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function submitStockReport(method){
	document.stockReportForm.method.value=method;
	document.stockReportForm.submit();
}
</script>
</head>
<body>
<html:form action="/stockReport">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="1"/>
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
                  <td height="25" class="row-odd" ><div align="right"> <span class="Mandatory">*  </span>From Date :</div></td>
                  <td class="row-even" ><table width="82" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="60"><html:text property="date" styleId="selectedDate" size="10" maxlength="10" /></td>
                      <td width="40"><script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'stockReportForm',
							// input name
							'controlname': 'selectedDate'
						});</script></td>
                    </tr>
                  </table></td>
                  <td height="25" class="row-odd" ><div align="right"> <span class="Mandatory">*  </span>To Date :</div></td>
                   <td width="60"><html:text property="endDate" styleId="endDate" size="10" maxlength="10" /></td>
                      <td width="40"><script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'stockReportForm',
							// input name
							'controlname': 'endDate'
						});</script></td>
                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="inventory.stockReceipt.invLoc.label"/>   :</div></td>
                  <td class="row-even" >
					<html:select property="locationId" styleClass="combo" >
	                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                    <logic:notEmpty property="locations" name="stockReportForm">
	                    <html:optionsCollection property="locations" name="stockReportForm" label="name" value="id"/>
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
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="50%" height="35" align="right"><html:button property="" styleClass="formbutton" value="View" onclick="submitStockReport('submitStockReport')"/></td>
            <td width="3" height="35" align="center">&nbsp;</td>
            <td width="50%" height="35" align="left"><html:button property="" styleClass="formbutton" value="Cancel" onclick="submitStockReport('initStockReport')"/></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
     
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"> 
        </td>
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