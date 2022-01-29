<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>

<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">

function saveSalvage() {
	document.getElementById("method").value="submitSalvageReport";
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
	document.location.href = "salvageReport.do?method=initSalvageReport";
	resetFieldAndErrMsgs();
}
</script>

</head>

<html:form action="/salvageReport">
<html:hidden property="method" styleId="method" value="submitSalvageReport"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="salvageReportForm"/>

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/> &gt;&gt; <bean:message key="knowledgepro.inventory.salvagereport.label"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.inventory.salvagereport.label"/></td>
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
                  <td width="50%" height="25" class="row-odd" ><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.item"/></td>
                  <td width="50%" height="25" class="row-odd" ><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.InvLocation"/></td>
             </tr>
             <tr>
                  <td width="355" class="row-even" ><html:text property="searchItem" name="salvageReportForm" styleId="searchItem" size="20" onkeyup="itemSearch(this.value)"/>
                  <td width="25%" class="row-even" >
	                <html:select name="salvageReportForm" property="invLocationId" styleClass="comboLarge" styleId="rId">
					<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
						<logic:notEmpty name="salvageReportForm" property="inventoryList">		
							<html:optionsCollection name="salvageReportForm" property="inventoryList" label="name" value="id" />
						</logic:notEmpty>
					</html:select>
               	  </td>
             </tr>
              <tr>
                <td class="row-even">
                	<nested:select property="itemId" styleId="itemId" styleClass="body" size="4" style="width:300px;">
					<logic:notEmpty name="salvageReportForm" property="itemList">
					<nested:optionsCollection name="salvageReportForm" property="itemList" label="nameWithCode" value="id" styleClass="comboBig"/>
					</logic:notEmpty>
					</nested:select>
                </td>
                <td class="row-even">&nbsp;</td>
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
              <td width="49%" align="right"><html:submit property="" styleClass="formbutton" value="Submit" onclick="saveSalvage()"/></td>
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