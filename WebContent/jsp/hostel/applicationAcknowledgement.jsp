<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<script type="text/javascript">
function redirectToHomePage(){
	document.getElementById("method").value = "initHostelApplicationStudent";
	document.hostelApplicationForm.submit();
}
function cancelAction() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
</script>
<html:form action="/HostelApplication" method="post">
<html:hidden property="method" styleId="method" />
<html:hidden property="formName" value="hostelApplicationForm" />
<table width="99%" border="0">
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.hostel.application.form" /> </span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td  background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.hostel.application.form" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="10" valign="top"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          <tr >
            <td height="42" valign="top" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img src="images/01.gif" width="5" height="5"></td>
                  <td  background="images/02.gif"></td>
                  <td width="10" ><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="0" height="28"  background="images/left.gif"></td>
                  <td width="100%" height="28" valign="top"><table width="100%" height="30" border="0" cellpadding="2" cellspacing="1">
                      <tr class="row-white">
                        <td  height="28" align="center" class="row-even"><bean:message key="knowledgepro.your.requisition"/>&nbsp;<bean:write name="hostelApplicationForm" property="maxRequisitionNo"/><bean:message key="knowledgepro.your.requisition.no.refer"/> </td>
                        </tr>
                  </table></td>
                  <td  background="images/right.gif" width="10" height="28"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right">
            <html:button property="" value="Back" styleClass="btnbg" onclick="redirectToHomePage()"></html:button>
            </td>
            <td width="2%"></td>
            <td width="49%" align="left"> <html:button  property=""
			styleClass="btnbg" value="Cancel" onclick="cancelAction()" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>