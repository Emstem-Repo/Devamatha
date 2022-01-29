<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<title>:: CMS ::</title>
<link href="css/styles.css" rel="stylesheet" type="text/css">
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
</script>
</head>

<html:form action="interviewprocess" method="post">
<table align="center" width="98%" border="0">
  
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
       
        <td height="8" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
         
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top">
              <table> 
              	
              </table>
              
                  <table width="100%" cellspacing="1" cellpadding="2">
		           <tr bgcolor="#FFFFFF">
		            <td height="20" colspan="6" class="body" align="left">
		             <div id="errorMessage">
		            <FONT color="red"><html:errors/></FONT>
		             <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
		            </FONT>
		            </div>
		            </td>
		          </tr>
                    <tr >
                      
                      <td style="padding-left: 35px; text-align: center;"  height="25" class="row-odd" ><bean:message key="knowledgepro.pettycash.accheads.slno"/></td>
                      <td style="padding-left: 35px; text-align: center;"  class="row-odd" ><bean:message key="knowledgepro.pettycash.accheads.accCode"/></td>
                      <td style="padding-left: 35px; text-align: center;"  class="row-odd" ><bean:message key="knowledgepro.pettycash.accheads.accname"/></td>
					  <td style="padding-left: 55px; text-align: center;" class="row-odd" ><bean:message key="knowledgepro.pettycash.accheads.bankAccNo"/></td> 
					  <td style="padding-left: 35px; text-align: center;"  height="25" class="row-odd" ><bean:message key="knowledgepro.pettycash.accheads.amount"/></td>
                      <td style="padding-left: 35px; text-align: center;"  class="row-odd" ><bean:message key="knowledgepro.pettycash.accheads.fixedamount"/></td>
                      <td style="padding-left: 35px; text-align: center;"  class="row-odd" ><bean:message key="knowledgepro.pettycash.accheads.group"/></td>
                    </tr>
                    
            <logic:notEmpty name="pcAccountHeadsListTO" scope="session">
            <nested:iterate id="accHeadsListTO" name="pcAccountHeadsListTO" indexId="count">
      
             <tr>
				<td height="25" class="row-even"><div align="center"><c:out value="${count + 1}"/></div></td>
				 <td class="row-even" ><div align="center"><bean:write name="accHeadsListTO" property="accCode"/></div></td>
				 <td class="row-even" ><div align="center"><bean:write name="accHeadsListTO" property="accName"/></div></td>
				 <td class="row-even" ><div align="center"><bean:write name="accHeadsListTO" property="bankAccNo"/></div></td>
				 <td class="row-even" ><div align="center"><bean:write name="accHeadsListTO" property="amount"/></div></td>
				 <td class="row-even" ><div align="center"><bean:write name="accHeadsListTO" property="isActives"/></div></td>
				 <td class="row-even" ><div align="center"><bean:write name="accHeadsListTO" property="groupName"/></div></td>
				</tr> 

 			</nested:iterate> 
            </logic:notEmpty>
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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
    </table></td>
  </tr>
</table>

</html:form>
<script type="text/javascript">
	window.print();
</script>

