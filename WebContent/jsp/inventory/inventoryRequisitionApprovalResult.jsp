<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>


<html:html>
<head>
<title>:: CMS ::</title>
<style type="text/css"> 
.hide {display: none} 
</style> 


<script language="JavaScript">
function closeWin(){

	window.close();
}
</script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css" type="text/css">
<script language="JavaScript" src="js/calendar_us.js"></script>

<style type="text/css">
<!--
body {
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
	margin-top: 5px;
}
.hide {display: none}
.hide1 {display: none}
.hide1 {display: none}
-->
</style>
</head>

<body>
<html:form action="InvRequisitionAction" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" value="invRequisitionForm"/>
<html:hidden property="pageType" styleId="pageType" value=""/>

<table width="99%" border="0">
  
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Inventory Requisition Approval</td>
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
            <td width="5" background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td height="33" class="row-odd" ><div align="right">Required by Date:</div></td>
                  <td class="row-even" ><logic:notEmpty name="invRequisitionForm" property="invRequestTO.deliveryDate" ><bean:write name="invRequisitionForm" property="invRequestTO.deliveryDate" ></bean:write> </logic:notEmpty>  </td>
                  <td class="row-odd" ><div align="right">Status:</div></td>
                  <td class="row-even" ><logic:notEmpty name="invRequisitionForm" property="invRequestTO.currentStatus" ><bean:write name="invRequisitionForm" property="invRequestTO.currentStatus" ></bean:write> </logic:notEmpty></td>
                </tr>
                <tr >
                  <td width="18%" height="33" class="row-odd" ><div align="right">Description  :</div></td>
                  <td width="19%" class="row-even" ><logic:notEmpty name="invRequisitionForm" property="invRequestTO.description" ><bean:write name="invRequisitionForm" property="invRequestTO.description" ></bean:write> </logic:notEmpty></td>
                  <td width="13%" class="row-odd" ><div align="right">Requisition No:</div></td>
                  <td width="18%" class="row-even" ><logic:notEmpty name="invRequisitionForm" property="invRequestTO.requistionNo" ><bean:write name="invRequisitionForm" property="invRequestTO.requistionNo" ></bean:write> </logic:notEmpty></td>
                </tr>
              </table>
	</td>
            <td width="5" height="75" background="images/right.gif"></td>
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
        <td class="heading"><p>&nbsp;</p>
          <p>&nbsp;</p></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5" background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td height="25"  width="35%" class="row-odd" >Item</td>
                <td height="25"  width="32%"class="row-odd" >UOM</td>
                <td class="row-odd" >Quantity </td>
                </tr>

				<logic:notEmpty name="invRequisitionForm" property="invRequestTO.itemTOList" > 
					<logic:iterate id="itemid" name="invRequisitionForm" property="invRequestTO.itemTOList" indexId="count">
                <tr >
	                <td width="38%" height="25" class="row-even"><logic:notEmpty name="itemid" property="name" ><bean:write name="itemid" property="name" ></bean:write> </logic:notEmpty></td>
	                <td width="33%" height="25" class="row-even" ><logic:notEmpty name="itemid" property="issueUomName" ><bean:write name="itemid" property="issueUomName" ></bean:write> </logic:notEmpty></td>
	                <td width="29%" class="row-even" ><logic:notEmpty name="itemid" property="quantityIssued" ><bean:write name="itemid" property="quantityIssued" ></bean:write> </logic:notEmpty></td>
                </tr>
					</logic:iterate>	
				</logic:notEmpty>

            </table></td>
            <td width="5" height="30" background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
        </table>
          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="50%" align="right"><input name="Submit22" type="button" class="formbutton" value="Close" onclick="closeWin()"/></td>
              <td width="25%" align="right">&nbsp;</td>
              <td width="25%" height="35" align="left">&nbsp;</td>
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
</body>
</html:html>
