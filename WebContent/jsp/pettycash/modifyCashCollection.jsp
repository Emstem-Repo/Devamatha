<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

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


function getAmount(accid) 
{

	alert("inside getamount" +accid);
	//var accountCodeWithName = document.getElementById("accountCodeId").value;
	//var text = document.getElementById("accountCodeId").options[document.getElementById("accountCodeId").selectedIndex].value;
	//document.getElementById("searchItem").value=accountCodeWithName;
	//var text = accountCodeWithName.options[accountCodeWithName.selectedIndex].value;
	//document.location.href = "CashCollection.do?method=getAmount&accId="+number;
	
	//document.getElementById("amount").value=amount;
	
	
}
</script>
</head>
<html:form action="modifyCashCollection">
<html:hidden property="method" styleId="method" value="getReceiptDetails"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="modifycashCollectionForm" />
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><span class="Bredcrumbs">Petty Cash &gt;&gt; <bean:message key="knowledgepro.petticash.modifyCashCollection"/>&gt;&gt;</span></span></td>
  </tr>
  
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.petticash.modifyCashCollection"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
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
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td  height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.petticash.recieptNo"/></div></td>
                    <td height="25" class="row-even" ><span class="star">
                     <html:text name="modifycashCollectionForm" property="recNumber" styleId="numberId" size="16" maxlength="20"/>
                      </span></td>
                    <td height="25" class="row-odd">
                    	<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.financialYear"/></div>
                    </td>
                    <td height="25" class="row-even">
                    	<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="modifycashCollectionForm" property="finYearId"/>" />
		                  <html:select name="modifycashCollectionForm" property="finYearId" styleId="financialYearId" style="width:165px" >
								<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
								<cms:renderPCFinancialYear></cms:renderPCFinancialYear>
						  </html:select>
                    </td>
                    
                  </tr>
              </table></td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><form name="formbutton" method="post" action="">
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="51%" height="27"><div align="right">
                  <html:submit property="" styleClass="formbutton" value="Edit"/>
                   </div></td>
                <td width="3%"></td>
                <td width="46%">&nbsp;</td>
              </tr>
            </table>
        </form></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
var print = "<c:out value='${modifycashCollectionForm.printReceipt}'/>";
if(print.length != 0 && print == "true") {
	var url ="modifyCashCollection.do?method=printReceiptAfterSave";
	myRef = window.open(url,"challan_details","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");	
}

var year = document.getElementById("tempyear").value;
if (year.length != 0) {
	document.getElementById("financialYearId").value = year;
}
</script>