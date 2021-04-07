<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html:html>
<head>
<title>:: CMS ::</title>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css" type="text/css">
<script language="JavaScript" src="js/calendar_us.js"></script>

<style type="text/css"> 
.hide {display: none} 
</style> 

<script type="text/javascript"> 
function saveIssuedReport() {
	document.getElementById("method").value="submitIssuedReceivedReport";
}

function itemSearch(searchValue){
	var sda = document.getElementById("itemId");
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

function resetFields() {	
	resetFieldAndErrMsgs();
}

</script> 



<style type="text/css">
<!--
body {
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
	margin-top: 5px;
}
-->
</style></head>

<html:form action="/IssuedReceivedReportAction">
<html:hidden property="method" styleId="method" value="submitIssuedReceivedReport"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="issuedReceivedReportForm"/>

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/> &gt;&gt; Location Wise,Item Wise Issue and Receipt&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Location Wise,Item Wise Issue and Receipt</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
                  <td width="50%" height="25" class="row-odd" ><bean:message key="knowledgepro.inventory.salvageitem.product.label"/></td>
                  <td width="50%" height="25" class="row-odd" ><bean:message key="knowledgepro.admin.InvLocation"/></td>
             </tr>
             <tr>
                  <td width="355" class="row-even" ><html:text property="searchItem" name="issuedReceivedReportForm" styleId="searchItem" size="20" onkeyup="itemSearch(this.value)"/>
                  <td width="25%" class="row-even" >
	                <html:select name="issuedReceivedReportForm" property="invLocationId" styleClass="comboLarge" styleId="rId">
					<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
						<logic:notEmpty name="issuedReceivedReportForm" property="inventoryList">		
							<html:optionsCollection name="issuedReceivedReportForm" property="inventoryList" label="name" value="id" />
						</logic:notEmpty>
					</html:select>
               	  </td>
             </tr>
              <tr>
                <td class="row-even">
                	<nested:select property="itemId" styleId="itemId" styleClass="body" size="4" style="width:300px;">
					<logic:notEmpty name="issuedReceivedReportForm" property="itemList">
					<nested:optionsCollection name="issuedReceivedReportForm" property="itemList" label="nameWithCode" value="id" styleClass="comboBig"/>
					</logic:notEmpty>
					</nested:select>
                </td>
                <td class="row-even">&nbsp;</td>
              </tr>
			<tr>
               <td height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.feepays.startdate" /></div></td>
               <td width="26%" height="25" class="row-even" >
					<html:text name="issuedReceivedReportForm" property="startDate" styleId="startDate" size="16" maxlength="16"/>
							<script language="JavaScript">
							new tcal ({
								// form name
								'formname': 'issuedReceivedReportForm',
								// input name
								'controlname': 'startDate'
							});</script>

			   </td>
				</tr>
				<tr>
               <td height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.feepays.enddate" /></div></td>
               <td width="26%" height="25" class="row-even" >
					<html:text name="issuedReceivedReportForm" property="endDate" styleId="endDate" size="16" maxlength="16"/>
							<script language="JavaScript">
							new tcal ({
								// form name
								'formname': 'issuedReceivedReportForm',
								// input name
								'controlname': 'endDate'
							});</script></td>

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
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
        
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">
          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="49%" align="right"><html:submit property="" styleClass="formbutton" value="Submit" /></td>
              <td width="2%" align="right">&nbsp;</td>
              <td width="49%" height="35" align="left"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetFields()"/></td>
            </tr>
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
</html:html>