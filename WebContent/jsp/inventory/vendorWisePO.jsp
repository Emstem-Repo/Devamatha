<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function submitVendorWisePO(){
	document.getElementById("method").value = "submitVendorWisePO";	
	document.vendorWisePOForm.submit();
}
</script>
<html:form action="/VendorWisePO" method="post">
<html:hidden property="method" styleId="method"/>
<html:hidden property="formName" value="vendorWisePOForm" />
<html:hidden property="pageType" value="1" />
<table width="99%" border="0"> <tr>
    <td><span class="heading"><span class="Bredcrumbs"> <bean:message key="knowledgepro.inventory"/> &gt;&gt; <bean:message key="knowledgepro.venderwise.report"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/Tcenter.gif" class="heading_white" >  <bean:message key="knowledgepro.venderwise.report"/> </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading">
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
		<FONT color="green"><html:messages id="msg"
		property="messages" message="true">
		<c:out value="${msg}" escapeXml="false"></c:out>
		<br>
		</html:messages> </FONT></div>
        <div align="right"> <span class='mandatoryfield'><bean:message
		key="knowledgepro.mandatoryfields" /></span></div></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="25%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.venderwise.report.vendor.col"/> </div></td>
                  <td width="25%" class="row-even" >
                  <html:select property="vendorId" styleClass="comboLarge" styleId="vendorId">
                  <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
                  <logic:notEmpty name="vendorWisePOForm" property="vendorList">
					<html:optionsCollection name="vendorWisePOForm" property="vendorList" label="vendorName" value="id" />
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
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right"><html:button property="" value="Submit" styleClass="formbutton" onclick="submitVendorWisePO()"/></td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left"><html:button property="" value="Reset" styleClass="formbutton" onclick="resetFieldAndErrMsgs()" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" colspan="2" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>